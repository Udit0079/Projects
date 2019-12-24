package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.GLDetailsTable;
import com.cbs.dto.report.AgentCollectionPojo;
import com.cbs.dto.report.AggregateDepositTable;
import com.cbs.dto.report.BalanceBookReportPojo;
import com.cbs.dto.report.CashClosingReportPojo;
import com.cbs.dto.report.CashDepositAggTable;
import com.cbs.dto.report.CashInBankPojo;
import com.cbs.dto.report.CashReserveReportPojo;
import com.cbs.dto.report.CbsGeneralLedgerBookByAcname;
import com.cbs.dto.report.CbsGeneralLedgerBookDebitBalPojo;
import com.cbs.dto.report.CbsGeneralLedgerBookPojo;
import com.cbs.dto.report.ConsolidateProfitLossPojo;
import com.cbs.dto.report.FinalBalanceReportPojo;
import com.cbs.dto.report.GenReportLedgerTable;
import com.cbs.dto.report.GlbComperativePojo;
import com.cbs.dto.report.GlbTempActypeEntryPojo;
import com.cbs.dto.report.ProfitAndLossPojo;
import com.cbs.dto.report.SortGeneralLedgerBookByActype;
import com.cbs.dto.report.SortGeneralLedgerBookByGno;
import com.cbs.dto.report.SortProfitLossByType;
import com.cbs.dto.report.SortedFinalBalanceReport;
import com.cbs.dto.report.SortedTrialBalanceReport;
import com.cbs.dto.report.SundrySuspenseDetailPojo;
import com.cbs.dto.report.SuspenseGeneralPojo;
import com.cbs.dto.report.Td15HEntryCertificationPojo;
import com.cbs.dto.report.TrialBalancePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.pojo.ExpenditureBalDayWisePojo;
import com.cbs.pojo.GlHeadPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.collections.comparators.ComparatorChain;

@Stateless(mappedName = "GLReportFacade")
public class GLReportFacade implements GLReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting;
    @EJB
    private HoReportFacadeRemote hoReport;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<GenReportLedgerTable> genRepAllLedger(String glType, String acc, String accAll, String fromDate, String toDate, String brcode, String txnMode) throws ApplicationException {
        String findate, tmpAcno = null, accName, acNat, acCode = null;
        String brnCode = null;
        Double opbal1, opbal2 = 0.0, openingBal, bal;
        List<GenReportLedgerTable> genReportLedgerTableList = new ArrayList<GenReportLedgerTable>();
        try {

            String tranTypeQuery = "";
            if (txnMode.equalsIgnoreCase("ALL")) {
                tranTypeQuery = "";
            } else {
                tranTypeQuery = "and TranType = " + txnMode + "";
            }

            String month = fromDate.substring(4, 6);
            String year = fromDate.substring(0, 4);
            String frmDtLastYr = String.valueOf(Integer.parseInt(year) - 1);
            if (Integer.parseInt(month) >= 4) {
                findate = year + "0401";
            } else {
                findate = frmDtLastYr + "0401";
            }
            if (glType == null) {
                glType = "";
            }
            if (glType.equalsIgnoreCase("ACTYPE")) {
                List acDetailsList = new ArrayList();
                if (brcode.equalsIgnoreCase("0A")) {
                    acDetailsList = em.createNativeQuery("select distinct substring(acno,3,8),acname from gltable where substring(acno,3,8) = '" + acc.substring(2) + "'").getResultList();
                } else {
                    acDetailsList = em.createNativeQuery("select acno,acname from gltable where acno = substring('" + acc + "',3,12)").getResultList();
                }

                if (!acDetailsList.isEmpty()) {
                    Vector vec = (Vector) acDetailsList.get(0);
                    tmpAcno = vec.get(0).toString();
                    accName = vec.get(1).toString();
                }

                if (brcode.equalsIgnoreCase("0A")) {
                    acDetailsList = em.createNativeQuery("select acctnature,acctcode from accounttypemaster where acctcode = substring('" + acc + "',1,2)").getResultList();
                } else {
                    acDetailsList = em.createNativeQuery("select acctnature,acctcode from accounttypemaster where acctcode = substring('" + acc + "',1,2)").getResultList();
                }

                if (!acDetailsList.isEmpty()) {
                    Vector vec = (Vector) acDetailsList.get(0);
                    acNat = vec.get(0).toString();
                    acCode = vec.get(1).toString();
                } else {
                    acNat = "";
                }
            } else {
                List acDetailsList;
                if (brcode.equalsIgnoreCase("0A")) {
                    acDetailsList = em.createNativeQuery("select distinct substring(acno,3,8),acname from gltable where substring(acno,3,8) = '" + accAll + "'").getResultList();
                } else {
                    acDetailsList = em.createNativeQuery("select acno,acname from gltable where acno = '" + accAll + "'").getResultList();
                }

                if (!acDetailsList.isEmpty()) {
                    Vector vec = (Vector) acDetailsList.get(0);
                    tmpAcno = vec.get(0).toString();
                    accName = vec.get(1).toString();
                }
                if (brcode.equalsIgnoreCase("0A")) {
                    acDetailsList = em.createNativeQuery("select acctnature,acctcode from accounttypemaster where glhead = '" + accAll + "'").getResultList();
                } else {
                    acDetailsList = em.createNativeQuery("select acctnature,acctcode from accounttypemaster where glhead = substring('" + tmpAcno + "',3,8)").getResultList();
                }

                if (!acDetailsList.isEmpty()) {
                    Vector vec = (Vector) acDetailsList.get(0);
                    acNat = vec.get(0).toString();
                    acCode = vec.get(1).toString();
                } else {
                    acNat = "";
                }

            }
            List l1 = new ArrayList();
            if (brcode.equalsIgnoreCase("0A")) {
                l1 = em.createNativeQuery("Select sum(ifnull(cramt,0)),sum(ifnull(dramt,0)),date_format(dt,'%d/%m/%Y'),date_format(ValueDt,'%d/%m/%Y') from gl_recon where substring(acno,3,8) = '" + tmpAcno + "'  and"
                        + " dt between '" + fromDate + "' and '" + toDate + "' and auth = 'Y' " + tranTypeQuery + " Group by dt order by dt").getResultList();
            } else {
                l1 = em.createNativeQuery("Select sum(ifnull(cramt,0)),sum(ifnull(dramt,0)),date_format(dt,'%d/%m/%Y'),date_format(ValueDt,'%d/%m/%Y') from gl_recon where acno = '" + tmpAcno + "'  and"
                        + " dt between '" + fromDate + "' and '" + toDate + "' and auth = 'Y' " + tranTypeQuery + " Group by dt order by dt").getResultList();
            }

            if (!l1.isEmpty()) {
                for (int i = 0; i < l1.size(); i++) {
                    GenReportLedgerTable genReportLedgerTable = new GenReportLedgerTable();
                    Vector vec = (Vector) l1.get(i);
                    genReportLedgerTable.setCredit(BigDecimal.valueOf(Double.parseDouble(vec.get(0).toString())));
                    genReportLedgerTable.setDebit(BigDecimal.valueOf(Double.parseDouble(vec.get(1).toString())));
                    genReportLedgerTable.setDate(vec.get(2).toString() + " [ " + vec.get(3).toString() + " ] ");
                    genReportLedgerTableList.add(genReportLedgerTable);
                }
            } else {
                if (acNat.equalsIgnoreCase("")) {
                    GenReportLedgerTable genReportLedgerTable = new GenReportLedgerTable();
                    genReportLedgerTable.setCredit(BigDecimal.valueOf(0));
                    genReportLedgerTable.setDebit(BigDecimal.valueOf(0));
                    genReportLedgerTable.setDate(dmyFormat.format(ymdFormat.parse(fromDate)));
                    genReportLedgerTableList.add(genReportLedgerTable);
                }
            }
            String tempAcno1 = "";
            if (brcode.equalsIgnoreCase("0A")) {
                tempAcno1 = tmpAcno.substring(2, 8);
            } else {
                tempAcno1 = tmpAcno.substring(4, 10);
            }

            if (Long.parseLong(tempAcno1) >= Long.parseLong(SiplConstant.GL_PL_ST.getValue()) && Long.parseLong(tempAcno1) <= Long.parseLong(SiplConstant.GL_PL_END.getValue())) {
                List l2 = new ArrayList();
                if (brcode.equalsIgnoreCase("0A")) {
                    l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM gl_recon "
                            + "WHERE substring(acno,3,8) = '" + tmpAcno + "'  AND DT < '" + fromDate + "'  AND DT >= '" + findate + "' AND auth = 'Y' " + tranTypeQuery + "").getResultList();
                } else {
                    l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM gl_recon "
                            + "WHERE acno = '" + tmpAcno + "'  AND DT < '" + fromDate + "'  AND DT >= '" + findate + "' AND auth = 'Y' " + tranTypeQuery + "").getResultList();
                }
                Vector vec = (Vector) l2.get(0);
                opbal1 = Double.parseDouble(vec.get(0).toString());
            } else {
                List l2 = new ArrayList();
                if (brcode.equalsIgnoreCase("0A")) {
                    l2 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from gl_recon "
                            + "WHERE substring(acno,3,8) = '" + tmpAcno + "' AND DT < '" + fromDate + "' AND auth = 'Y' " + tranTypeQuery + "").getResultList();
                } else {
                    l2 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from gl_recon "
                            + "WHERE acno = '" + tmpAcno + "' AND DT < '" + fromDate + "' AND auth = 'Y' " + tranTypeQuery + "").getResultList();
                }
                Vector vec = (Vector) l2.get(0);
                opbal1 = Double.parseDouble(vec.get(0).toString());
            }
            if (!acNat.equalsIgnoreCase("")) {
                List l2 = new ArrayList();
                // brnCode = tmpAcno.substring(0, 2);
                brnCode = brcode;
                if (brcode.equalsIgnoreCase("0A")) {
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(recon.CRAMT,0))-SUM(ifnull(recon.DRAMT,0)),0) FROM td_recon recon , "
                                + "td_accountmaster am WHERE recon.DT < '" + fromDate + "' AND recon.auth = 'Y' AND recon.trantype <> 27 AND "
                                + "recon.closeflag IS NULL AND SUBSTRING(recon.ACNO,3,2) = '" + acCode + "' and recon.acno = am.acno " + tranTypeQuery + "").getResultList();
                    } else if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM recon r,accountmaster am  WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " ").getResultList();
                    } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM rdrecon r,accountmaster am WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno"
                                + " " + tranTypeQuery + " ").getResultList();
                    } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM loan_recon r,accountmaster am WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " ").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM ca_recon r,accountmaster am WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " ").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM of_recon r,accountmaster am WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " ").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM ddstransaction r,accountmaster am "
                                + "WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND  am.acno = r.acno " + tranTypeQuery + " ").getResultList();

                    }
                    Vector vec = (Vector) l2.get(0);
                    opbal2 = Double.parseDouble(vec.get(0).toString());
                } else {
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(recon.CRAMT,0))-SUM(ifnull(recon.DRAMT,0)),0) FROM td_recon recon , "
                                + "td_accountmaster am WHERE recon.DT < '" + fromDate + "' AND recon.auth = 'Y' AND recon.trantype <> 27 AND "
                                + "recon.closeflag IS NULL AND SUBSTRING(recon.ACNO,3,2) = '" + acCode + "' and recon.acno = am.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + "").getResultList();
                    } else if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM recon r,accountmaster am  WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno and "
                                + "am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + "").getResultList();
                    } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM rdrecon r,accountmaster am WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno"
                                + " and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + "").getResultList();
                    } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM loan_recon r,accountmaster am WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + "").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM ca_recon r,accountmaster am WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + "").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM of_recon r,accountmaster am WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + "").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM ddstransaction r,accountmaster am "
                                + "WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND  am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + "").getResultList();

                    }
                    Vector vec = (Vector) l2.get(0);
                    opbal2 = Double.parseDouble(vec.get(0).toString());
                }

            }
            if (!acNat.equalsIgnoreCase("")) {
                List l2 = new ArrayList();
                if (brcode.equalsIgnoreCase("0A")) {
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM "
                                + "td_recon r,td_accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT BETWEEN '" + fromDate + "' AND '" + toDate + "' "
                                + "and r.auth = 'Y' and r.trantype <> 27 and r.closeflag is null AND am.acno = r.acno " + tranTypeQuery + "  "
                                + "group by r.dt order by r.dt").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM recon"
                                + " r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' and "
                                + "r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " group by r.dt order by r.dt").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM "
                                + "rdrecon r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' "
                                + "and r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " group by r.dt order by r.dt").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM "
                                + "loan_recon r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' "
                                + "and r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " group by r.dt order by r.dt").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM "
                                + "ca_recon r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' "
                                + "and r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " group by r.dt order by r.dt").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM "
                                + "of_recon r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' and"
                                + " r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " group by r.dt order by r.dt").getResultList();
                    } else if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM "
                                + "ddstransaction r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between "
                                + "'" + fromDate + "' AND '" + toDate + "' and r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " group by r.dt order by r.dt").getResultList();
                    }
                    if (!l2.isEmpty()) {
                        for (int i = 0; i < l2.size(); i++) {
                            GenReportLedgerTable genReportLedgerTable = new GenReportLedgerTable();
                            Vector vec = (Vector) l2.get(i);
                            genReportLedgerTable.setCredit(BigDecimal.valueOf(Double.parseDouble(vec.get(0).toString())));
                            genReportLedgerTable.setDebit(BigDecimal.valueOf(Double.parseDouble(vec.get(1).toString())));
                            genReportLedgerTable.setDate(vec.get(2).toString());
                            genReportLedgerTableList.add(genReportLedgerTable);
                        }
                    }
                } else {
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM "
                                + "td_recon r,td_accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT BETWEEN '" + fromDate + "' AND '" + toDate + "' "
                                + "and r.auth = 'Y' and r.trantype <> 27 and r.closeflag is null AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " "
                                + "group by r.dt order by r.dt").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM recon"
                                + " r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' and "
                                + "r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " group by r.dt order by r.dt").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM "
                                + "rdrecon r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' "
                                + "and r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " group by r.dt order by r.dt").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM "
                                + "loan_recon r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' "
                                + "and r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " group by r.dt order by r.dt").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM "
                                + "ca_recon r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' "
                                + "and r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " group by r.dt order by r.dt").getResultList();

                    } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM "
                                + "of_recon r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' and"
                                + " r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " group by r.dt order by r.dt").getResultList();
                    } else if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0)),SUM(ifnull(r.DRAMT,0)),date_format(r.dt,'%d/%m/%Y') FROM "
                                + "ddstransaction r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between "
                                + "'" + fromDate + "' AND '" + toDate + "' and r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " group by r.dt order by r.dt").getResultList();
                    }
                    if (!l2.isEmpty()) {
                        for (int i = 0; i < l2.size(); i++) {
                            GenReportLedgerTable genReportLedgerTable = new GenReportLedgerTable();
                            Vector vec = (Vector) l2.get(i);
                            genReportLedgerTable.setCredit(BigDecimal.valueOf(Double.parseDouble(vec.get(0).toString())));
                            genReportLedgerTable.setDebit(BigDecimal.valueOf(Double.parseDouble(vec.get(1).toString())));
                            genReportLedgerTable.setDate(vec.get(2).toString());
                            genReportLedgerTableList.add(genReportLedgerTable);
                        }
                    }
                }
            }
            openingBal = opbal1 + opbal2;
            for (int i = 0; i < genReportLedgerTableList.size(); i++) {
                genReportLedgerTableList.get(i).setOpBal(BigDecimal.valueOf(openingBal));
            }
            bal = openingBal;
            for (int i = 0; i < genReportLedgerTableList.size(); i++) {
                bal = (genReportLedgerTableList.get(i).getCredit().doubleValue() - genReportLedgerTableList.get(i).getDebit().doubleValue()) + bal;
                genReportLedgerTableList.get(i).setBalance(BigDecimal.valueOf(bal));

            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return genReportLedgerTableList;
    }

    /**
     * conversion of CBS_REP_ALLLED_ACC .
     *
     * @param glType
     * @param brnCode
     * @return
     */
    @Override
    public List<GLDetailsTable> getGLtypeHeads(String glType, String brnCode) throws ApplicationException {
        List<GLDetailsTable> glheadlist = new ArrayList<GLDetailsTable>();
        List glheads = new ArrayList();
        try {
            if (brnCode.equalsIgnoreCase("0A")) {
                if (glType.equalsIgnoreCase("1")) {
                    glheads = em.createNativeQuery("SELECT distinct substring(acno,3,8),concat(acname,'   ',substring(acno,3,8)) FROM gltable WHERE SUBSTRING(acno,5,6)>='" + SiplConstant.GL_INCOME_ST.getValue() + "' AND SUBSTRING(acno,5,6)<='" + SiplConstant.GL_INCOME_END.getValue() + "' ORDER BY acname").getResultList();
                }
                if (glType.equalsIgnoreCase("2")) {
                    //glheads = em.createNativeQuery("SELECT acno,acname+'   '+acno FROM gltable WHERE SUBSTRING(acno,5,4)IN('0057') AND SUBSTRING(acno,1,2) = '" + brnCode + "' ORDER BY acname").getResultList();
                    glheads = em.createNativeQuery("SELECT distinct substring(acno,3,8),concat(acname,'   ',substring(acno,3,8)) FROM gltable WHERE SUBSTRING(acno,5,6) BETWEEN '" + SiplConstant.GL_BANKER_CA_ST.getValue() + "' AND '" + SiplConstant.GL_BANKER_CA_END.getValue() + "' ORDER BY acname").getResultList();
                }
                if (glType.equalsIgnoreCase("3")) {
                    //glheads = em.createNativeQuery("SELECT acno,acname+'   '+acno FROM gltable WHERE SUBSTRING(acno,5,4)IN('0059') AND SUBSTRING(acno,1,2) = '" + brnCode + "' ORDER BY acname").getResultList();
                    glheads = em.createNativeQuery("SELECT distinct substring(acno,3,8),concat(acname,'   ',substring(acno,3,8)) FROM gltable WHERE SUBSTRING(acno,5,6) BETWEEN '" + SiplConstant.GL_BANKER_FD_ST.getValue() + "' AND '" + SiplConstant.GL_BANKER_FD_END.getValue() + "' ORDER BY acname").getResultList();
                }
                if (glType.equalsIgnoreCase("4")) {
//                glheads = em.createNativeQuery("SELECT acno,acname+'   '+acno FROM gltable WHERE SUBSTRING(acno,5,4)NOT IN ('0059') "
//                        + "AND SUBSTRING(acno,5,4) NOT IN ('0057') AND (SUBSTRING(acno,7,4)<'2001' OR SUBSTRING(acno,7,4)>'3000') AND"
//                        + " SUBSTRING(acno,3,8) NOT IN (SELECT glhead FROM accounttypemaster WHERE glhead IS NOT NULL) AND "
//                        + "SUBSTRING(acno,1,2) = '" + brnCode + "' ORDER BY acname").getResultList();
                    glheads = em.createNativeQuery("SELECT distinct substring(acno,3,8),concat(acname,'   ',substring(acno,3,8)) FROM gltable WHERE SUBSTRING(acno,5,6) NOT BETWEEN '" + SiplConstant.GL_BANKER_FD_ST.getValue() + "' AND '" + SiplConstant.GL_BANKER_FD_END.getValue() + "'"
                            + "AND SUBSTRING(acno,5,6) NOT BETWEEN '" + SiplConstant.GL_BANKER_CA_ST.getValue() + "' AND '" + SiplConstant.GL_BANKER_CA_END.getValue() + "' AND (SUBSTRING(acno,5,6)<'" + SiplConstant.GL_PL_ST.getValue() + "' OR SUBSTRING(acno,3,8)>'" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "') AND"
                            + " SUBSTRING(acno,3,8) NOT IN (SELECT glhead FROM accounttypemaster WHERE glhead IS NOT NULL) ORDER BY acname").getResultList();
                }
                if (glType.equalsIgnoreCase("5")) {
//                glheads = em.createNativeQuery("SELECT acno,acname+'   '+acno FROM gltable WHERE SUBSTRING(acno,7,4)>'2000' AND "
//                        + "SUBSTRING(acno,7,4)<'2501' AND SUBSTRING(acno,1,2) = '" + brnCode + "' ORDER BY acname").getResultList();
                    glheads = em.createNativeQuery("SELECT distinct substring(acno,3,8),concat(acname,'   ',substring(acno,3,8)) FROM gltable WHERE SUBSTRING(acno,5,6) BETWEEN '" + SiplConstant.GL_EXP_ST.getValue() + "' AND '" + SiplConstant.GL_EXP_END.getValue() + "' ORDER BY acname").getResultList();
                }
                if (glType.equalsIgnoreCase("ACTYPE")) {

                    glheads = em.createNativeQuery("SELECT concat(acctcode,glhead),concat(acctcode,'  ',acctdesc,'  ',glhead) FROM accounttypemaster WHERE glhead IS NOT NULL and AcctCode <> '06' ORDER BY acctcode").getResultList();
                }
            } else {
                if (glType.equalsIgnoreCase("1")) {
                    glheads = em.createNativeQuery("SELECT acno,concat(acname,'   ',acno) FROM gltable WHERE SUBSTRING(acno,5,6)>='" + SiplConstant.GL_INCOME_ST.getValue() + "' AND SUBSTRING(acno,5,6)<='" + SiplConstant.GL_INCOME_END.getValue() + "' AND SUBSTRING(acno,1,2) = '" + brnCode + "' ORDER BY acname").getResultList();
                }
                if (glType.equalsIgnoreCase("2")) {
                    //glheads = em.createNativeQuery("SELECT acno,acname+'   '+acno FROM gltable WHERE SUBSTRING(acno,5,4)IN('0057') AND SUBSTRING(acno,1,2) = '" + brnCode + "' ORDER BY acname").getResultList();
                    glheads = em.createNativeQuery("SELECT acno,concat(acname,'   ',acno) FROM gltable WHERE SUBSTRING(acno,5,6) BETWEEN '" + SiplConstant.GL_BANKER_CA_ST.getValue() + "' AND '" + SiplConstant.GL_BANKER_CA_END.getValue() + "' AND SUBSTRING(acno,1,2) = '" + brnCode + "' ORDER BY acname").getResultList();
                }
                if (glType.equalsIgnoreCase("3")) {
                    //glheads = em.createNativeQuery("SELECT acno,acname+'   '+acno FROM gltable WHERE SUBSTRING(acno,5,4)IN('0059') AND SUBSTRING(acno,1,2) = '" + brnCode + "' ORDER BY acname").getResultList();
                    glheads = em.createNativeQuery("SELECT acno,concat(acname,'   ',acno) FROM gltable WHERE SUBSTRING(acno,5,6) BETWEEN '" + SiplConstant.GL_BANKER_FD_ST.getValue() + "' AND '" + SiplConstant.GL_BANKER_FD_END.getValue() + "' AND SUBSTRING(acno,1,2) = '" + brnCode + "' ORDER BY acname").getResultList();
                }
                if (glType.equalsIgnoreCase("4")) {
//                glheads = em.createNativeQuery("SELECT acno,acname+'   '+acno FROM gltable WHERE SUBSTRING(acno,5,4)NOT IN ('0059') "
//                        + "AND SUBSTRING(acno,5,4) NOT IN ('0057') AND (SUBSTRING(acno,7,4)<'2001' OR SUBSTRING(acno,7,4)>'3000') AND"
//                        + " SUBSTRING(acno,3,8) NOT IN (SELECT glhead FROM accounttypemaster WHERE glhead IS NOT NULL) AND "
//                        + "SUBSTRING(acno,1,2) = '" + brnCode + "' ORDER BY acname").getResultList();
                    glheads = em.createNativeQuery("SELECT acno,concat(acname,'   ',acno) FROM gltable WHERE SUBSTRING(acno,5,6) NOT BETWEEN '" + SiplConstant.GL_BANKER_FD_ST.getValue() + "' AND '" + SiplConstant.GL_BANKER_FD_END.getValue() + "'"
                            + "AND SUBSTRING(acno,5,6) NOT BETWEEN '" + SiplConstant.GL_BANKER_CA_ST.getValue() + "' AND '" + SiplConstant.GL_BANKER_CA_END.getValue() + "' AND (SUBSTRING(acno,5,6)<'" + SiplConstant.GL_PL_ST.getValue() + "' OR SUBSTRING(acno,3,8)>'" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "') AND"
                            + " SUBSTRING(acno,3,8) NOT IN (SELECT glhead FROM accounttypemaster WHERE glhead IS NOT NULL) AND "
                            + "SUBSTRING(acno,1,2) = '" + brnCode + "' ORDER BY acname").getResultList();
                }
                if (glType.equalsIgnoreCase("5")) {
//                glheads = em.createNativeQuery("SELECT acno,acname+'   '+acno FROM gltable WHERE SUBSTRING(acno,7,4)>'2000' AND "
//                        + "SUBSTRING(acno,7,4)<'2501' AND SUBSTRING(acno,1,2) = '" + brnCode + "' ORDER BY acname").getResultList();
                    glheads = em.createNativeQuery("SELECT acno,concat(acname,'   ',acno) FROM gltable WHERE SUBSTRING(acno,5,6) BETWEEN '" + SiplConstant.GL_EXP_ST.getValue() + "' AND '" + SiplConstant.GL_EXP_END.getValue() + "'"
                            + "AND SUBSTRING(acno,1,2) = '" + brnCode + "' ORDER BY acname").getResultList();
                }
                if (glType.equalsIgnoreCase("ACTYPE")) {
                    glheads = em.createNativeQuery("SELECT concat(acctcode,'" + brnCode + "',glhead,'01'),concat(acctcode,'  ',acctdesc,'  ',glhead) FROM accounttypemaster WHERE glhead IS NOT NULL and AcctCode <> '06' ORDER BY acctcode").getResultList();
                }
            }

            if (!glheads.isEmpty()) {
                for (int i = 0; i < glheads.size(); i++) {
                    Vector vec = (Vector) glheads.get(i);
                    GLDetailsTable gLDetailsTable = new GLDetailsTable();
                    gLDetailsTable.setAcno(vec.get(0).toString());
                    gLDetailsTable.setAcname(vec.get(1).toString());
                    glheadlist.add(gLDetailsTable);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return glheadlist;
    }

    /**
     * Conversion of CBS_REP_SUB_BOOK_STM function getAbb_bill_ddRowSet25
     *
     * @param glType
     * @param acc
     * @param accAll
     * @param fromDate
     * @param toDate
     * @return
     */
    @Override
    public List<GenReportLedgerTable> subsidiaryBookStatement(String glType, String acc, String accAll, String fromDate, String toDate, String brcode, String txnMode) throws ApplicationException {
        String findate, tmpAcno = null, accName, acNat, acCode = null;
        String brnCode = null;
        double opbal1, opbal2 = 0, openingBal, bal;
        List<GenReportLedgerTable> genReportLedgerTableList = new ArrayList<GenReportLedgerTable>();
        try {
            String tranTypeQuery = "";
            if (txnMode.equalsIgnoreCase("ALL")) {
                tranTypeQuery = "";
            } else {
                tranTypeQuery = "and TranType = " + txnMode + "";
            }
            String month = fromDate.substring(4, 6);
            String year = fromDate.substring(0, 4);
            String frmDtLastYr = String.valueOf(Integer.parseInt(year) - 1);
            if (Integer.parseInt(month) >= 4) {
                findate = year + "0401";
            } else {
                findate = frmDtLastYr + "0401";
            }
            if (glType == null) {
                glType = "";
            }
            if (glType.equalsIgnoreCase("ACTYPE")) {
                List acDetailsList = new ArrayList();
                if (brcode.equalsIgnoreCase("0A")) {
                    acDetailsList = em.createNativeQuery("select distinct substring(acno,3,8),acname from gltable where substring(acno,3,8) = '" + acc.substring(2) + "'").getResultList();
                } else {
                    acDetailsList = em.createNativeQuery("select acno,acname from gltable where acno = substring('" + acc + "',3,12)").getResultList();
                }
                if (!acDetailsList.isEmpty()) {
                    Vector vec = (Vector) acDetailsList.get(0);
                    tmpAcno = vec.get(0).toString();
                    accName = vec.get(1).toString();
                }
                acDetailsList = em.createNativeQuery("select acctnature,acctcode from accounttypemaster where acctcode = substring('" + acc + "',1,2)").getResultList();
                if (!acDetailsList.isEmpty()) {
                    Vector vec = (Vector) acDetailsList.get(0);
                    acNat = vec.get(0).toString();
                    acCode = vec.get(1).toString();
                } else {
                    acNat = "";
                    acCode = "";
                }

            } else {
                List acDetailsList = new ArrayList();
                if (brcode.equalsIgnoreCase("0A")) {
                    acDetailsList = em.createNativeQuery("SELECT distinct substring(acno,3,8),acname from gltable where substring(acno,3,8) = '" + accAll + "'").getResultList();
                } else {
                    acDetailsList = em.createNativeQuery("SELECT acno,acname from gltable where acno = '" + accAll + "'").getResultList();
                }

                if (!acDetailsList.isEmpty()) {
                    Vector vec = (Vector) acDetailsList.get(0);
                    tmpAcno = vec.get(0).toString();
                    accName = vec.get(1).toString();
                }
                if (brcode.equalsIgnoreCase("0A")) {
                    acDetailsList = em.createNativeQuery("SELECT acCTnature,ACCTCODE FROM accounttypemaster WHERE glhead = '" + accAll + "'").getResultList();
                } else {
                    acDetailsList = em.createNativeQuery("SELECT acCTnature,ACCTCODE FROM accounttypemaster WHERE glhead = SUBSTRING('" + tmpAcno + "',3,8)").getResultList();
                }

                if (!acDetailsList.isEmpty()) {
                    Vector vec = (Vector) acDetailsList.get(0);
                    acNat = vec.get(0).toString();
                    acCode = vec.get(1).toString();
                } else {
                    acNat = "";
                    acCode = "";
                }
            }
            if (acNat.equalsIgnoreCase("")) {
                List l1 = new ArrayList();
                if (brcode.equalsIgnoreCase("0A")) {
                    l1 = em.createNativeQuery("SELECT CRAMT,DRAMT,date_format(dt,'%d/%m/%Y'),trantime,details,date_format(ValueDt,'%d/%m/%Y'),org_brnid,dest_brnid FROM gl_recon WHERE substring(acno,3,8) = '" + tmpAcno + "' AND "
                            + "DT BETWEEN '" + fromDate + "' AND '" + toDate + "' AND auth = 'Y' " + tranTypeQuery + " ORDER BY dt").getResultList();
                } else {
                    l1 = em.createNativeQuery("SELECT CRAMT,DRAMT,date_format(dt,'%d/%m/%Y'),trantime,details,date_format(ValueDt,'%d/%m/%Y'),org_brnid,dest_brnid FROM gl_recon WHERE acno = '" + tmpAcno + "' AND "
                            + "DT BETWEEN '" + fromDate + "' AND '" + toDate + "' AND auth = 'Y' " + tranTypeQuery + " ORDER BY dt").getResultList();
                }

                if (!l1.isEmpty()) {
                    for (int i = 0; i < l1.size(); i++) {
                        GenReportLedgerTable genReportLedgerTable = new GenReportLedgerTable();
                        Vector vec = (Vector) l1.get(i);
                        genReportLedgerTable.setCredit(BigDecimal.valueOf(Double.parseDouble(vec.get(0).toString())));
                        genReportLedgerTable.setDebit(BigDecimal.valueOf(Double.parseDouble(vec.get(1).toString())));
                        genReportLedgerTable.setDate(vec.get(2).toString() + " [ " + vec.get(5).toString() + " ] ");
                        genReportLedgerTable.setTranTime(vec.get(3).toString());
                        String particulars = vec.get(4) == null ? "" : vec.get(4).toString();
                        if (particulars.indexOf("~`") != -1) {
                            String[] arr = particulars.split("~`");
                            particulars = arr[0];
                        }
                        genReportLedgerTable.setParticular(particulars);
                        genReportLedgerTable.setOriginBranch(common.getAlphacodeByBrncode(vec.get(6).toString()));
                        genReportLedgerTable.setDestBranch(common.getAlphacodeByBrncode(vec.get(7).toString()));
                        genReportLedgerTableList.add(genReportLedgerTable);

                    }
                } else {
                    GenReportLedgerTable genReportLedgerTable = new GenReportLedgerTable();
                    genReportLedgerTable.setCredit(BigDecimal.valueOf(0));
                    genReportLedgerTable.setDebit(BigDecimal.valueOf(0));
                    genReportLedgerTable.setDate(dmyFormat.format(ymdFormat.parse(fromDate)));
                    genReportLedgerTable.setTranTime("");
                    genReportLedgerTable.setParticular("");
                    genReportLedgerTableList.add(genReportLedgerTable);
                }
            } else {
                List l1 = new ArrayList();
                if (brcode.equalsIgnoreCase("0A")) {
                    l1 = em.createNativeQuery("select CRAMT,DRAMT,date_format(dt,'%d/%m/%Y'),trantime,date_format(ValueDt,'%d/%m/%Y'),org_brnid,dest_brnid from gl_recon where substring(acno,3,8) = '" + tmpAcno + "' and DT between "
                            + "'" + fromDate + "' AND '" + toDate + "' and auth = 'Y' " + tranTypeQuery + " order by dt").getResultList();
                } else {
                    l1 = em.createNativeQuery("select CRAMT,DRAMT,date_format(dt,'%d/%m/%Y'),trantime,date_format(ValueDt,'%d/%m/%Y'),org_brnid,dest_brnid from gl_recon where acno = '" + tmpAcno + "' and DT between "
                            + "'" + fromDate + "' AND '" + toDate + "' and auth = 'Y' " + tranTypeQuery + " order by dt").getResultList();
                }

                if (!l1.isEmpty()) {
                    for (int i = 0; i < l1.size(); i++) {
                        GenReportLedgerTable genReportLedgerTable = new GenReportLedgerTable();
                        Vector vec = (Vector) l1.get(i);
                        genReportLedgerTable.setCredit(BigDecimal.valueOf(Double.parseDouble(vec.get(0).toString())));
                        genReportLedgerTable.setDebit(BigDecimal.valueOf(Double.parseDouble(vec.get(1).toString())));
                        genReportLedgerTable.setDate(vec.get(2).toString() + " [ " + vec.get(4).toString() + " ] ");
                        genReportLedgerTable.setTranTime(vec.get(3).toString());
                        genReportLedgerTable.setOriginBranch(common.getAlphacodeByBrncode(vec.get(5).toString()));
                        genReportLedgerTable.setDestBranch(common.getAlphacodeByBrncode(vec.get(6).toString()));
                        genReportLedgerTableList.add(genReportLedgerTable);
                    }
                }
            }
            String tempAcno1 = "";
            if (brcode.equalsIgnoreCase("0A")) {
                tempAcno1 = tmpAcno.substring(2, 8);
            } else {
                tempAcno1 = tmpAcno.substring(4, 10);
            }
            if (Long.parseLong(tempAcno1) >= Long.parseLong(SiplConstant.GL_PL_ST.getValue()) && Long.parseLong(tempAcno1) <= Long.parseLong(SiplConstant.GL_PL_END.getValue())) {
                List l2 = new ArrayList();
                if (brcode.equalsIgnoreCase("0A")) {
                    l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM gl_recon WHERE substring(acno,3,8) = '" + tmpAcno + "'  AND "
                            + "DT < '" + fromDate + "' AND DT >= '" + findate + "' AND auth = 'Y' " + tranTypeQuery + "").getResultList();
                } else {
                    l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM gl_recon WHERE acno = '" + tmpAcno + "'  AND "
                            + "DT < '" + fromDate + "' AND DT >= '" + findate + "' AND auth = 'Y' " + tranTypeQuery + "").getResultList();
                }

                Vector vec = (Vector) l2.get(0);
                opbal1 = Double.parseDouble(vec.get(0).toString());
            } else {
                List l2 = new ArrayList();
                if (brcode.equalsIgnoreCase("0A")) {
                    l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM "
                            + "gl_recon WHERE substring(acno,3,8) = '" + tmpAcno + "'  AND DT < '" + fromDate + "' AND auth = 'Y' " + tranTypeQuery + " ").getResultList();
                } else {
                    l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM "
                            + "gl_recon WHERE acno = '" + tmpAcno + "'  AND DT < '" + fromDate + "' AND auth = 'Y' " + tranTypeQuery + " ").getResultList();
                }

                Vector vec = (Vector) l2.get(0);
                opbal1 = Double.parseDouble(vec.get(0).toString());
            }
            if (!acNat.equalsIgnoreCase("")) {
                List l2 = new ArrayList();
                //     brnCode = ftsPosting.getCurrentBrnCode(tmpAcno);
                brnCode = brcode;
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM td_recon r,"
                                + "td_accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND "
                                + "r.trantype <> 27 AND r.closeflag IS NULL AND am.acno = r.acno " + tranTypeQuery + " ").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)),0) FROM td_recon r,"
                                + "td_accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND "
                                + "r.trantype <> 27 AND r.closeflag IS NULL AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " ").getResultList();
                    }
                } else if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)) FROM recon r,accountmaster am "
                                + "WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " ").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)) FROM recon r,accountmaster am "
                                + "WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno and "
                                + "am.CurBrCode = '" + brnCode + "'" + tranTypeQuery + "").getResultList();
                    }
                } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)) FROM rdrecon r,accountmaster am "
                                + "WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " ").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)) FROM rdrecon r,accountmaster am "
                                + "WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno and "
                                + "am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + "").getResultList();
                    }
                } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)) FROM loan_recon r,accountmaster am "
                                + "WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + "").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)) FROM loan_recon r,accountmaster am "
                                + "WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno and "
                                + "am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " ").getResultList();
                    }
                } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)) FROM ca_recon r,accountmaster am WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND "
                                + "am.acno = r.acno " + tranTypeQuery + " ").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)) FROM ca_recon r,accountmaster am WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND "
                                + "am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " ").getResultList();
                    }
                } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)) FROM of_recon r,accountmaster am WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " ").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)) FROM of_recon r,accountmaster am WHERE "
                                + "SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno"
                                + " and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " ").getResultList();
                    }

                } else if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)) FROM ddstransaction  r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' "
                                + "AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " ").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT SUM(ifnull(r.CRAMT,0))-SUM(ifnull(r.DRAMT,0)) FROM ddstransaction  r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' "
                                + "AND r.DT < '" + fromDate + "' AND r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " ").getResultList();
                    }
                }
                Vector vec = (Vector) l2.get(0);
                opbal2 = Double.parseDouble(vec.get(0).toString());
            }
            if (!acNat.equalsIgnoreCase("")) {
                List l2 = new ArrayList();
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),trantime FROM td_recon r,"
                                + "td_accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "'  and "
                                + "r.auth = 'Y' and r.trantype <> 27 and r.closeflag is null AND am.acno = r.acno " + tranTypeQuery + " ").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),trantime FROM td_recon r,"
                                + "td_accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "'  and "
                                + "r.auth = 'Y' and r.trantype <> 27 and r.closeflag is null AND am.acno = r.acno and "
                                + "am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " ").getResultList();
                    }
                } else if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),r.trantime FROM recon r,accountmaster"
                                + " am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' and r.auth = 'Y' "
                                + "AND am.acno = r.acno " + tranTypeQuery + "  ORDER BY r.DT").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),r.trantime FROM recon r,accountmaster"
                                + " am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' and r.auth = 'Y' "
                                + "AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "'" + tranTypeQuery + " ORDER BY r.DT").getResultList();
                    }
                } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),trantime FROM rdrecon r,accountmaster am "
                                + "WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' and "
                                + "r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + "  ORDER BY r.DT").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),trantime FROM rdrecon r,accountmaster am "
                                + "WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND '" + toDate + "' and "
                                + "r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " ORDER BY r.DT").getResultList();
                    }
                } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),r.trantime FROM loan_recon r,"
                                + "accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND DT between '" + fromDate + "' AND '" + toDate + "' and r.auth = 'Y'"
                                + " AND am.acno = r.acno " + tranTypeQuery + " ORDER BY r.DT").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),r.trantime FROM loan_recon r,"
                                + "accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND DT between '" + fromDate + "' AND '" + toDate + "' and r.auth = 'Y'"
                                + " AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "'" + tranTypeQuery + " ORDER BY r.DT").getResultList();
                    }
                } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),r.trantime FROM ca_recon r,"
                                + "accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND "
                                + "'" + toDate + "' and r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " ORDER BY r.DT").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),r.trantime FROM ca_recon r,"
                                + "accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT between '" + fromDate + "' AND "
                                + "'" + toDate + "' and r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " ORDER BY r.DT").getResultList();
                    }
                } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),r.trantime FROM of_recon r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT "
                                + "between '" + fromDate + "' AND '" + toDate + "' and r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " ORDER BY DT").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),r.trantime FROM of_recon r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT "
                                + "between '" + fromDate + "' AND '" + toDate + "' and r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "' " + tranTypeQuery + " ORDER BY DT").getResultList();
                    }
                } else if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    if (brcode.equalsIgnoreCase("0A")) {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),r.trantime FROM ddstransaction r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT "
                                + "between '" + fromDate + "' AND '" + toDate + "' and r.auth = 'Y' AND am.acno = r.acno " + tranTypeQuery + " ORDER BY r.DT").getResultList();
                    } else {
                        l2 = em.createNativeQuery("SELECT r.CRAMT,r.DRAMT,date_format(r.dt,'%d/%m/%Y'),r.trantime FROM ddstransaction r,accountmaster am WHERE SUBSTRING(r.ACNO,3,2) = '" + acCode + "' AND r.DT "
                                + "between '" + fromDate + "' AND '" + toDate + "' and r.auth = 'Y' AND am.acno = r.acno and am.CurBrCode = '" + brnCode + "'" + tranTypeQuery + " ORDER BY r.DT").getResultList();
                    }
                }

                if (!l2.isEmpty()) {
                    for (int i = 0; i < l2.size(); i++) {
                        GenReportLedgerTable genReportLedgerTable = new GenReportLedgerTable();
                        Vector vec = (Vector) l2.get(i);
                        genReportLedgerTable.setCredit(BigDecimal.valueOf(Double.parseDouble(vec.get(0).toString())));
                        genReportLedgerTable.setDebit(BigDecimal.valueOf(Double.parseDouble(vec.get(1).toString())));
                        genReportLedgerTable.setDate(vec.get(2).toString());
                        genReportLedgerTable.setTranTime(vec.get(3).toString());
                        genReportLedgerTableList.add(genReportLedgerTable);
                    }
                }
            }
            openingBal = opbal1 + opbal2;
            for (int i = 0; i < genReportLedgerTableList.size(); i++) {
                genReportLedgerTableList.get(i).setOpBal(BigDecimal.valueOf(openingBal));
            }
            bal = openingBal;
            for (int i = 0; i < genReportLedgerTableList.size(); i++) {
                bal = (genReportLedgerTableList.get(i).getCredit().doubleValue() - genReportLedgerTableList.get(i).getDebit().doubleValue()) + bal;
                genReportLedgerTableList.get(i).setBalance(BigDecimal.valueOf(bal));
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return genReportLedgerTableList;
    }

    /**
     * ******************************************************************
     * END OF FUNCTIONS ALL LEDGER AND SUBSIDIARY REPORTS
     * /*******************************************************************
     */
    /**
     * CONVERSION OF CBS_REP_AGGRIGATE_DEPOSIT_REPORT function GET THE AGGREGATE
     * DEPOSIT REPORT FOR DIFFERENT ACCOUNT NATURE
     *
     * @param tillDate
     * @param brncode
     * @return
     */
    @Override
    public List<AggregateDepositTable> getAggregateDepositReport(String tillDate, String brncode) throws ApplicationException {
        List<AggregateDepositTable> aggregateDepositTableList = new ArrayList<AggregateDepositTable>();
        String acctNature, acType, acctDesc, glHead;
        NumberFormat formatter = new DecimalFormat("#.##");
        List sumList = new ArrayList();
        try {
            List<GlHeadPojo> osBlancelist = hoReport.getAsOnDateBalanceList(brncode, tillDate);
            List acctCodeList = em.createNativeQuery("Select acctNature,acctCode,acctDesc,GlHead From accounttypemaster Where acctNature"
                    + " in ('" + CbsConstant.SAVING_AC + "','" + CbsConstant.CURRENT_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "',"
                    + "'" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.RECURRING_AC + "') and"
                    + " acctnature is not null /*and acctcode not in ('" + CbsAcCodeConstant.CASH_CREDIT.getAcctCode() + "',"
                    + "'" + CbsAcCodeConstant.OVER_DRAFT.getAcctCode() + "')*/ order by acctCode").getResultList();
            if (!acctCodeList.isEmpty()) {
                for (int i = 0; i < acctCodeList.size(); i++) {
                    Vector vec = (Vector) acctCodeList.get(i);
                    acctNature = vec.get(0).toString();
                    acType = vec.get(1).toString();
                    acctDesc = vec.get(2).toString();
                    glHead = vec.get(3).toString();
                    // ********************************* This is previous code *****************************************

//                    if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
//                        sumList = em.createNativeQuery("Select ifnull((sum(ifnull(r.CrAmt,0))-sum(ifnull(r.DrAmt,0)))/100000,0) From "
//                                + "td_recon r, td_accountmaster am where r.dt<='" + tillDate + "'  and "
//                                + "substring(r.acno,3,2)='" + acType + "' and r.auth='Y' and am.acno = r.acno and am.CurBrCode = '" + brncode + "' and "
//                                + "r.trantype<>27 and r.closeflag is null").getResultList();
//                    } else if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//                        sumList = em.createNativeQuery("Select ifnull((sum(ifnull(r.CrAmt,0))-sum(ifnull(r.DrAmt,0)))/100000,0) From recon r,"
//                                + " accountmaster am where r.dt<='" + tillDate + "' and substring(r.acno,3,2)='" + acType + "' and "
//                                + "am.acno = r.acno and am.CurBrCode = '" + brncode + "' and r.auth='Y'").getResultList();
//                    } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                        sumList = em.createNativeQuery("Select ifnull((sum(ifnull(r.CrAmt,0))-sum(ifnull(r.DrAmt,0)))/100000,0) From "
//                                + "ca_recon r, accountmaster am where r.dt<='" + tillDate + "' and substring(r.acno,3,2)='" + acType + "' "
//                                + "and am.acno = r.acno and am.CurBrCode = '" + brncode + "'  and r.auth='Y' ").getResultList();
//                    } else if (acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
//                        sumList = em.createNativeQuery("Select ifnull((sum(ifnull(r.CrAmt,0))-sum(ifnull(r.DrAmt,0)))/100000,0) From "
//                                + "ddstransaction r, accountmaster am where r.dt<='" + tillDate + "' and "
//                                + "substring(r.acno,3,2)='" + acType + "' and am.acno = r.acno and am.CurBrCode = '" + brncode + "' and r.auth='Y'").getResultList();
//                    } else if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
//                        sumList = em.createNativeQuery("Select ifnull((sum(ifnull(r.CrAmt,0))-sum(ifnull(r.DrAmt,0)))/100000,0) From rdrecon "
//                                + "r, accountmaster am where r.dt <='" + tillDate + "' and "
//                                + "substring(r.acno,3,2)='" + acType + "' and am.acno = r.acno and am.CurBrCode = '" + brncode + "' and r.auth='Y'").getResultList();
//                    }
                    // ********************************* This is New code *****************************************
                    AggregateDepositTable aggregateDepositTable = new AggregateDepositTable();
                    double tmpBal = 0;
                    GlHeadPojo pojo = new GlHeadPojo();
                    /* AcType value */
                    pojo = hoReport.getOSBalance(osBlancelist, acType, "L");
                    if (pojo != null) {
                        tmpBal = Double.valueOf(formatter.format(pojo.getBalance()));
                    } else {
                        tmpBal = 0;
                    }
                    aggregateDepositTable.setAcctDesc(acctDesc);
                    aggregateDepositTable.setTotalBalance(new BigDecimal(tmpBal));
                    aggregateDepositTableList.add(aggregateDepositTable);
                }
            }
            return aggregateDepositTableList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /*
     * conversion of CBS_REP_CASH_DEPOSIT_AGGRIGATE_REPORT function
     * @param acCode
     * @param tranType
     * @param amount
     * @param fromDate
     * @param toDate
     * @param brCode
     * @return
     */
    @Override
    public List<CashDepositAggTable> cashDepositAggregateReport(String repTypeOption, String actNature, String acCode, String tranType, String amountType, String fromDate, String toDate, String brCode, String optType, double fromAmt, double toAmt) throws ApplicationException {
        List<CashDepositAggTable> cashDepositAggregateList = new ArrayList<CashDepositAggTable>();
        List l1 = new ArrayList();
        List bankDetailsList = new ArrayList();
        NumberFormat formatter = new DecimalFormat("#0.00");
        String bnkName = null, bnkAddress = null, acNature = "", branchCode1 = "", branchCode2 = "";
        try {
            String amountTypeQuery = "";
            if (amountType.equalsIgnoreCase("0")) {
                amountTypeQuery = "<=" + fromAmt + "";
            } else if (amountType.equalsIgnoreCase("1")) {
                amountTypeQuery = ">" + fromAmt + "";
            } else if (amountType.equalsIgnoreCase("2")) {
                amountTypeQuery = " between " + fromAmt + " and " + toAmt + "";
            }
            String acTypeQuery = "";
            if (actNature.equalsIgnoreCase("ALL")) {
                acTypeQuery = " substring(r.acno,3,2) in (select AcctCode from accounttypemaster where acctnature in ('CA','SB'))  ";
            } else {
                if (acCode.equalsIgnoreCase("All")) {
                    acTypeQuery = "substring(r.acno,3,2) in (select AcctCode from accounttypemaster where acctnature in ('" + actNature + "'))";
                } else {
                    acTypeQuery = "substring(r.acno,3,2) in ('" + acCode + "')";
                }
            }

            if (brCode.equalsIgnoreCase("0A")) {
                branchCode1 = "";
                branchCode2 = "";
                bankDetailsList = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where b.alphacode=br.alphacode and br.brncode=" + Integer.parseInt("90")).getResultList();
            } else {
                branchCode1 = "and a.brncode= '" + brCode + "'";
                branchCode2 = "and substring(r.acno,1,2)='" + brCode + "'";
                bankDetailsList = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where b.alphacode=br.alphacode and br.brncode=" + Integer.parseInt(brCode)).getResultList();
            }

            if (!bankDetailsList.isEmpty()) {
                Vector vec = (Vector) bankDetailsList.get(0);
                bnkName = vec.get(0).toString();
                bnkAddress = vec.get(1).toString();
            }
            if (repTypeOption.equalsIgnoreCase("0")) {
                if (tranType.equalsIgnoreCase("ALL")) {
                    if (optType.equalsIgnoreCase("0")) {
                        l1 = em.createNativeQuery("select sum(r.cramt),r.acno,a.custname,a.praddress ,c.CustId  from ca_recon r, customermaster a,accountmaster s,customerid c where " + acTypeQuery + " and "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1 + " and "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (0) and c.Acno= s.ACNo  "
                                + "" + branchCode2 + " group by r.acno,c.CustId,a.custname,a.praddress  having sum(r.cramt) " + amountTypeQuery + " "
                                + " union all "
                                + " select sum(r.cramt),r.acno,a.custname,a.praddress ,c.CustId  from recon r, customermaster a,accountmaster s ,customerid c "
                                + "where " + acTypeQuery + " and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' AND '" + toDate + "' AND TRANTYPE  in (0) and c.Acno= s.ACNo "
                                + " " + branchCode2 + " group by r.acno,c.CustId,a.custname,a.praddress  having sum(r.cramt) " + amountTypeQuery + " "
                                + " union all  "
                                + "  select sum(r.cramt),r.acno,a.custname,a.praddress ,c.CustId  from ca_recon r, customermaster a,accountmaster s ,customerid c where " + acTypeQuery + " and "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1 + " and "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (1)  and c.Acno= s.ACNo "
                                + "" + branchCode2 + " group by r.acno,c.CustId,a.custname,a.praddress  having sum(r.cramt) " + amountTypeQuery + " "
                                + " union all "
                                + " select sum(r.cramt),r.acno,a.custname,a.praddress,c.CustId  from recon r, customermaster a,accountmaster s ,customerid c "
                                + "where " + acTypeQuery + " and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' AND '" + toDate + "' AND TRANTYPE  in (1) and c.Acno= s.ACNo  "
                                + " " + branchCode2 + " group by r.acno,c.CustId,a.custname,a.praddress  having sum(r.cramt) " + amountTypeQuery + "  "
                                + " union all "
                                + " select sum(r.cramt),r.acno,a.custname,a.praddress,c.CustId  from ca_recon r, customermaster a,accountmaster s ,customerid c where " + acTypeQuery + " and "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1 + " and "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (2) and c.Acno= s.ACNo  "
                                + "" + branchCode2 + " group by r.acno,c.CustId,a.custname,a.praddress  having sum(r.cramt) " + amountTypeQuery + " "
                                + " union all "
                                + " select sum(r.cramt),r.acno,a.custname,a.praddress,c.CustId  from recon r, customermaster a,accountmaster s ,customerid c "
                                + "where " + acTypeQuery + " and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' AND '" + toDate + "' AND TRANTYPE  in (2) and c.Acno= s.ACNo "
                                + " " + branchCode2 + " group by r.acno,c.CustId,a.custname,a.praddress  having sum(r.cramt) " + amountTypeQuery + " ").getResultList();
                    } else {
                        l1 = em.createNativeQuery("select sum(r.dramt),r.acno,a.custname,a.praddress,c.CustId  from ca_recon r, customermaster a,accountmaster s ,customerid c where " + acTypeQuery + "  AND "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2)  " + branchCode1 + " AND "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (0) and c.Acno= s.ACNo  "
                                + "" + branchCode2 + " group by r.acno,c.CustId,a.custname,a.praddress  having sum(r.dramt) " + amountTypeQuery + " "
                                + " union all  "
                                + " select sum(r.dramt),r.acno,a.custname,a.praddress,c.CustId  from recon r, customermaster a,accountmaster s ,customerid c  "
                                + "where " + acTypeQuery + "  and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (0)  and c.Acno= s.ACNo "
                                + " " + branchCode2 + " group by r.acno,c.CustId,a.custname,a.praddress  having sum(r.dramt) " + amountTypeQuery + " "
                                + " union all "
                                + " select sum(r.dramt),r.acno,a.custname,a.praddress,c.CustId  from ca_recon r, customermaster a,accountmaster s ,customerid c  where " + acTypeQuery + "  AND "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2)  " + branchCode1 + " AND "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (1)  and c.Acno= s.ACNo "
                                + "" + branchCode2 + " group by r.acno,c.CustId,a.custname,a.praddress  having sum(r.dramt) " + amountTypeQuery + " "
                                + " union all  "
                                + " select sum(r.dramt),r.acno,a.custname,a.praddress,c.CustId  from recon r, customermaster a,accountmaster s ,customerid c  "
                                + "where " + acTypeQuery + "  and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (1) and c.Acno= s.ACNo "
                                + " " + branchCode2 + " group by r.acno,c.CustId,a.custname,a.praddress  having sum(r.dramt) " + amountTypeQuery + " "
                                + " union all "
                                + "select sum(r.dramt),r.acno,a.custname,a.praddress,c.CustId  from ca_recon r, customermaster a,accountmaster s ,customerid c  where " + acTypeQuery + "  AND "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2)  " + branchCode1 + " AND "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (2)and c.Acno= s.ACNo  "
                                + "" + branchCode2 + " group by r.acno,c.CustId,a.custname,a.praddress  having sum(r.dramt) " + amountTypeQuery + " "
                                + " union all  "
                                + " select sum(r.dramt),r.acno,a.custname,a.praddress,c.CustId  from recon r, customermaster a,accountmaster s ,customerid c  "
                                + "where " + acTypeQuery + "  and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (2) and c.Acno= s.ACNo "
                                + " " + branchCode2 + " group by r.acno,c.CustId,a.custname,a.praddress  having sum(r.dramt) " + amountTypeQuery + " ").getResultList();
                    }
                } else {
                    if (optType.equalsIgnoreCase("0")) {
                        l1 = em.createNativeQuery("select sum(r.cramt),r.acno,a.custname,a.praddress,c.CustId  from ca_recon r, customermaster a,accountmaster s,customerid c  where " + acTypeQuery + " and "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1 + " and "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (" + Integer.parseInt(tranType) + ")  and c.Acno= s.ACNo "
                                + "" + branchCode2 + " group by r.acno,a.custname,a.praddress,c.CustId  having sum(r.cramt) " + amountTypeQuery + " "
                                + "union all "
                                + " select sum(r.cramt),r.acno,a.custname,a.praddress,c.CustId  from recon r, customermaster a,accountmaster s ,customerid c  "
                                + "where " + acTypeQuery + " and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' AND '" + toDate + "' AND TRANTYPE  in (" + Integer.parseInt(tranType) + ") and c.Acno= s.ACNo  "
                                + " " + branchCode2 + " group by r.acno,a.custname,a.praddress,c.CustId  having sum(r.cramt) " + amountTypeQuery + " ").getResultList();
                    } else {
                        l1 = em.createNativeQuery("select sum(r.dramt),r.acno,a.custname,a.praddress,c.CustId  from ca_recon r, customermaster a,accountmaster s ,customerid c  where " + acTypeQuery + "  AND "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2)  " + branchCode1 + " AND "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (" + Integer.parseInt(tranType) + ")  and c.Acno= s.ACNo "
                                + "" + branchCode2 + " group by r.acno,a.custname,a.praddress,c.CustId  having sum(r.dramt) " + amountTypeQuery + " "
                                + "union all  "
                                + " select sum(r.dramt),r.acno,a.custname,a.praddress,c.CustId  from recon r, customermaster a,accountmaster s ,customerid c   "
                                + "where " + acTypeQuery + "  and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (" + Integer.parseInt(tranType) + ") and c.Acno= s.ACNo  "
                                + " " + branchCode2 + " group by r.acno,a.custname,a.praddress,c.CustId  having sum(r.dramt) " + amountTypeQuery + " ").getResultList();
                    }
                }
            } else {
                if (tranType.equalsIgnoreCase("ALL")) {
                    if (optType.equalsIgnoreCase("0")) {
                        l1 = em.createNativeQuery("select sum(r.cramt),r.acno,a.custname,a.praddress from ca_recon r, customermaster a,accountmaster s where " + acTypeQuery + " and "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1 + " and "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (0)  "
                                + "" + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.cramt) " + amountTypeQuery + " "
                                + " union all "
                                + " select sum(r.cramt),r.acno,a.custname,a.praddress from recon r, customermaster a,accountmaster s "
                                + "where " + acTypeQuery + " and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' AND '" + toDate + "' AND TRANTYPE  in (0) "
                                + " " + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.cramt) " + amountTypeQuery + " "
                                + " union all  "
                                + "  select sum(r.cramt),r.acno,a.custname,a.praddress from ca_recon r, customermaster a,accountmaster s where " + acTypeQuery + " and "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1 + " and "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (1)  "
                                + "" + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.cramt) " + amountTypeQuery + " "
                                + " union all "
                                + " select sum(r.cramt),r.acno,a.custname,a.praddress from recon r, customermaster a,accountmaster s "
                                + "where " + acTypeQuery + " and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' AND '" + toDate + "' AND TRANTYPE  in (1) "
                                + " " + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.cramt) " + amountTypeQuery + "  "
                                + " union all "
                                + " select sum(r.cramt),r.acno,a.custname,a.praddress from ca_recon r, customermaster a,accountmaster s where " + acTypeQuery + " and "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1 + " and "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (2)  "
                                + "" + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.cramt) " + amountTypeQuery + " "
                                + " union all "
                                + " select sum(r.cramt),r.acno,a.custname,a.praddress from recon r, customermaster a,accountmaster s "
                                + "where " + acTypeQuery + " and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' AND '" + toDate + "' AND TRANTYPE  in (2) "
                                + " " + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.cramt) " + amountTypeQuery + " ").getResultList();
                    } else {
                        l1 = em.createNativeQuery("select sum(r.dramt),r.acno,a.custname,a.praddress from ca_recon r, customermaster a,accountmaster s where " + acTypeQuery + "  AND "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2)  " + branchCode1 + " AND "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (0)  "
                                + "" + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.dramt) " + amountTypeQuery + " "
                                + " union all  "
                                + " select sum(r.dramt),r.acno,a.custname,a.praddress from recon r, customermaster a,accountmaster s "
                                + "where " + acTypeQuery + "  and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (0) "
                                + " " + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.dramt) " + amountTypeQuery + " "
                                + " union all "
                                + " select sum(r.dramt),r.acno,a.custname,a.praddress from ca_recon r, customermaster a,accountmaster s where " + acTypeQuery + "  AND "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2)  " + branchCode1 + " AND "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (1)  "
                                + "" + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.dramt) " + amountTypeQuery + " "
                                + " union all  "
                                + " select sum(r.dramt),r.acno,a.custname,a.praddress from recon r, customermaster a,accountmaster s "
                                + "where " + acTypeQuery + "  and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (1) "
                                + " " + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.dramt) " + amountTypeQuery + " "
                                + " union all "
                                + "select sum(r.dramt),r.acno,a.custname,a.praddress from ca_recon r, customermaster a,accountmaster s where " + acTypeQuery + "  AND "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2)  " + branchCode1 + " AND "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (2)  "
                                + "" + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.dramt) " + amountTypeQuery + " "
                                + " union all  "
                                + " select sum(r.dramt),r.acno,a.custname,a.praddress from recon r, customermaster a,accountmaster s "
                                + "where " + acTypeQuery + "  and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (2) "
                                + " " + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.dramt) " + amountTypeQuery + " ").getResultList();
                    }
                } else {
                    if (optType.equalsIgnoreCase("0")) {
                        l1 = em.createNativeQuery("select sum(r.cramt),r.acno,a.custname,a.praddress from ca_recon r, customermaster a,accountmaster s where " + acTypeQuery + " and "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1 + " and "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (" + Integer.parseInt(tranType) + ")  "
                                + "" + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.cramt) " + amountTypeQuery + " "
                                + "union all "
                                + " select sum(r.cramt),r.acno,a.custname,a.praddress from recon r, customermaster a,accountmaster s "
                                + "where " + acTypeQuery + " and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' AND '" + toDate + "' AND TRANTYPE  in (" + Integer.parseInt(tranType) + ") "
                                + " " + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.cramt) " + amountTypeQuery + " ").getResultList();
                    } else {
                        l1 = em.createNativeQuery("select sum(r.dramt),r.acno,a.custname,a.praddress from ca_recon r, customermaster a,accountmaster s where " + acTypeQuery + "  AND "
                                + "substring(r.acno,5,6) = a.custno  and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2)  " + branchCode1 + " AND "
                                + "r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (" + Integer.parseInt(tranType) + ")  "
                                + "" + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.dramt) " + amountTypeQuery + " "
                                + "union all  "
                                + " select sum(r.dramt),r.acno,a.custname,a.praddress from recon r, customermaster a,accountmaster s "
                                + "where " + acTypeQuery + "  and substring(r.acno,5,6) = a.custno and a.actype = substring(r.acno,3,2) and a.brncode = substring(r.acno,1,2) " + branchCode1
                                + " and r.acno = s.acno and dt between '" + fromDate + "' and '" + toDate + "' and TRANTYPE  in (" + Integer.parseInt(tranType) + ") "
                                + " " + branchCode2 + " group by r.acno,a.custname,a.praddress having sum(r.dramt) " + amountTypeQuery + " ").getResultList();
                    }
                }
            }

            if (!l1.isEmpty()) {
                for (int i = 0; i < l1.size(); i++) {
                    Vector vec = (Vector) l1.get(i);
                    double totalBalance = Double.parseDouble(vec.get(0).toString());
                    String accountNo = vec.get(1).toString();
                    String custName = vec.get(2).toString();
                    String address = vec.get(3).toString();
                    CashDepositAggTable cashDepositAggTable = new CashDepositAggTable();
                    cashDepositAggTable.setBnkName(bnkName);
                    cashDepositAggTable.setBnkAddress(bnkAddress);
                    cashDepositAggTable.setAccountNo(accountNo);
                    cashDepositAggTable.setCustName(custName);
                    cashDepositAggTable.setAddress(address);
                    if (repTypeOption.equalsIgnoreCase("0")) {
                        cashDepositAggTable.setCustId(vec.get(4).toString());
                    }
                    cashDepositAggTable.setTotalBalance(BigDecimal.valueOf(totalBalance));
                    cashDepositAggregateList.add(cashDepositAggTable);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return cashDepositAggregateList;
    }

    @Override
    public List<CashClosingReportPojo> getCashClosingReport(String fromDate, String toDate, String brCode) throws ApplicationException {
        List<CashClosingReportPojo> returnList = new ArrayList<CashClosingReportPojo>();
        List resultList = new ArrayList();
        try {
            int datedifference = (int) CbsUtil.dayDiff(ymdFormat.parse(fromDate), ymdFormat.parse(toDate));
            for (int j = 0; j <= datedifference; j++) {
                String date = CbsUtil.dateAdd(fromDate, j);
                if (brCode.equalsIgnoreCase("0A")) {
                    resultList = em.createNativeQuery("select cast(opamt as decimal (25,2)),brncode from opcash where tdate ='" + date + "'").getResultList();
                } else {
                    resultList = em.createNativeQuery("select cast(opamt as decimal (25,2)),brncode from opcash where tdate ='" + date + "' and brncode='" + brCode + "'").getResultList();
                }
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        if (ele != null && ele.get(0) != null) {
                            CashClosingReportPojo pojo = new CashClosingReportPojo();
                            String brnCode = ele.get(1).toString();
                            String brName = common.getBranchNameByBrncode(brnCode);
                            pojo.setOpeningAmount(Double.parseDouble(ele.get(0).toString()));
                            pojo.setBrName(brName);
                            pojo.setDate(dmyFormat.format(ymdFormat.parse(date)));
                            returnList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    @Override
    public List<BalanceBookReportPojo> getBalanceBookReport(String fromDate, String toDate, String brCode, String exCounterInclude) throws ApplicationException {
        String acctCode = "", acctDesc = "", acctNature = "", glHead = "";
        double totalBalance, balanceOfGL, difference;
        try {
            String brCodes = "";
            brCode = brCode.equals("0A") ? "90" : brCode;
            if (exCounterInclude.equals("Y")) {
                List exBrCodeLst = em.createNativeQuery("select brnCode from branchmaster where ex_counter = 'Y' and parent_brncode = "
                        + Integer.parseInt(brCode)).getResultList();
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
                brCodes = brCodes + ",\'" + brCode + "\'";
            } else {
                brCodes = brCode;
            }

            List<BalanceBookReportPojo> returnList = new ArrayList<BalanceBookReportPojo>();
            List resultList1 = em.createNativeQuery("SELECT acctCode,acctDesc,acctNature,glHead FROM accounttypemaster WHERE ACCTNATURE<>'" + CbsConstant.PAY_ORDER + "' AND ACCTNATURE IS NOT NULL ORDER BY ACCTCODE").getResultList();
            for (int i = 0; i < resultList1.size(); i++) {
                Vector ele1 = (Vector) resultList1.get(i);
                if (ele1.get(0) != null) {
                    acctCode = ele1.get(0).toString();
                }
                if (ele1.get(1) != null) {
                    acctDesc = ele1.get(1).toString();
                }
                if (ele1.get(2) != null) {
                    acctNature = ele1.get(2).toString();
                }
                if (ele1.get(3) != null) {
                    glHead = ele1.get(3).toString();
                }
                totalBalance = 0.0;
                balanceOfGL = 0.0;
                difference = 0.0;
                double glSum = 0;
                String tableName = common.getTableName(acctNature);
                if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                        || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                        || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {

                    totalBalance = common.getDoubleValueFromVector((Vector) em.createNativeQuery("select sum(r.CrAmt)-sum(r.DrAmt) From " + tableName + " r,accountmaster am where r.dt between '" + fromDate + "' and '" + toDate + "' and substring(r.acno,3,2)='" + acctCode + "' and am.acno = r.acno and am.CurBrCode in(" + brCodes + ") and auth='Y'").getSingleResult());
                } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    totalBalance = common.getDoubleValueFromVector((Vector) em.createNativeQuery("select sum(r.CrAmt)-sum(r.DrAmt) From td_recon r,td_accountmaster am where r.dt between '" + fromDate + "' and '" + toDate + "' and substring(r.acno,3,2)='" + acctCode + "' and am.acno = r.acno and am.CurBrCode in(" + brCodes + ") and r.auth='Y' and r.trantype<>27 and r.closeflag is null").getSingleResult());
                }
                glSum = common.getDoubleValueFromVector((Vector) em.createNativeQuery("Select sum(CrAmt)-sum(DrAmt) From gl_recon Where dt between '" + fromDate + "' and '" + toDate + "' and substring(acno,3,8)='" + glHead + "' and auth='Y' AND substring(acno,1,2) in(" + brCodes + ")").getSingleResult());
                balanceOfGL = totalBalance + glSum;
                difference = balanceOfGL - totalBalance;
                BalanceBookReportPojo pojo = new BalanceBookReportPojo();
                pojo.setAcctDesc(acctDesc);
                pojo.setBalanceOfGL(balanceOfGL);
                pojo.setDifference(difference);
                pojo.setTotalBalance(totalBalance);
                returnList.add(pojo);
            }
            return returnList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    @Override
    public List<CashReserveReportPojo> getCashReserveReport(String date, String brCode) throws ApplicationException {
        List<CashReserveReportPojo> returnList = new ArrayList<CashReserveReportPojo>();
        String acctNature = null, acctDesc = null, glHead = null, acctCode = null;
        double loanAgainstFD = 0.0, crAmount = 0.0, drAmount = 0.0, amt1 = 0.0, amt2 = 0.0, amt3 = 0.0, amt4 = 0.0, amt5 = 0.0, amt6 = 0.0, crTotal = 0.0, drTotal = 0.0, demandTotal = 0.0, balOp = 0.0, glTotal = 0.0, extTotal = 0.0, grantTotalInCash = 0.0, balanceTotal = 0.0, intTotal = 0.0, cashTotal = 0.0, balTimetotal = 0.0;
        int crTotalVouch = 0, drTotalVouch = 0, count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0, count6 = 0;
        List tempList = null;
        try {
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Cramt),Count(r.ty) From td_recon r, "
                        + "td_accountmaster am where r.dt='" + date + "' and r.ty=0 and r.trantype not in (5,27) and r.auth='Y' and r.Closeflag is null "
                        + "and am.acno = r.acno ").getResultList());
            } else {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Cramt),Count(r.ty) From td_recon r, "
                        + "td_accountmaster am where r.dt='" + date + "' and r.ty=0 and r.trantype not in (5,27) and r.auth='Y' and r.Closeflag is null "
                        + "and am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getResultList());
            }
            if (tempList != null) {
                amt1 = (Double) tempList.get(0);
                count1 = (Integer) tempList.get(1);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Cramt),Count(r.ty) From recon r, accountmaster "
                        + "am where r.dt='" + date + "' and r.ty=0 and r.auth='Y' and am.acno = r.acno ").getResultList());
            } else {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Cramt),Count(r.ty) From recon r, accountmaster "
                        + "am where r.dt='" + date + "' and r.ty=0 and r.auth='Y' and am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getResultList());
            }

            if (tempList != null) {
                amt2 = (Double) tempList.get(0);
                count2 = (Integer) tempList.get(1);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Cramt),Count(r.ty) From ca_recon r, "
                        + "accountmaster am where r.dt='" + date + "' and r.ty=0 and r.auth='Y' and am.acno = r.acno ").getResultList());
            } else {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Cramt),Count(r.ty) From ca_recon r, "
                        + "accountmaster am where r.dt='" + date + "' and r.ty=0 and r.auth='Y' and am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getResultList());
            }

            if (tempList != null) {
                amt3 = (Double) tempList.get(0);
                count3 = (Integer) tempList.get(1);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Cramt),Count(r.ty) From loan_recon r, "
                        + "accountmaster am where r.dt='" + date + "' and r.ty=0 and r.auth='Y' and am.acno = r.acno ").getResultList());
            } else {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Cramt),Count(r.ty) From loan_recon r, "
                        + "accountmaster am where r.dt='" + date + "' and r.ty=0 and r.auth='Y' and am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getResultList());
            }

            if (tempList != null) {
                amt4 = (Double) tempList.get(0);
                count4 = (Integer) tempList.get(1);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Cramt),Count(r.ty) From rdrecon r,accountmaster am "
                        + "where r.dt='" + date + "' and r.ty=0 and r.auth='Y' and am.acno = r.acno ").getResultList());
            } else {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Cramt),Count(r.ty) From rdrecon r,accountmaster am "
                        + "where r.dt='" + date + "' and r.ty=0 and r.auth='Y' and am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getResultList());
            }

            if (tempList != null) {
                amt5 = (Double) tempList.get(0);
                count5 = (Integer) tempList.get(1);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(Cramt),Count(ty) From gl_recon where dt='" + date + "' and ty=0 and auth='Y' ").getResultList());
            } else {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(Cramt),Count(ty) From gl_recon where dt='" + date + "' and ty=0 and auth='Y' and substring(acno,1,2)='" + brCode + "'").getResultList());
            }

            if (tempList != null) {
                amt6 = (Double) tempList.get(0);
                count6 = (Integer) tempList.get(1);
            }
            //Credit
            //No of vouchers
            crTotalVouch = count1 + count2 + count3 + count4 + count5 + count6;
            //Amount
            crAmount = amt1 + amt2 + amt3 + amt4 + amt5 + amt6;
            count1 = 0;
            count2 = 0;
            count3 = 0;
            count4 = 0;
            count5 = 0;
            count6 = 0;
            amt1 = 0;
            amt2 = 0;
            amt3 = 0;
            amt4 = 0;
            amt5 = 0;
            amt6 = 0;
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Dramt),Count(r.ty) From td_recon r, "
                        + "td_accountmaster am where r.dt='" + date + "' and r.ty=1 and r.trantype not in (5,27) and r.auth='Y' and r.Closeflag is "
                        + "null and am.acno = r.acno ").getResultList());
            } else {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Dramt),Count(r.ty) From td_recon r, "
                        + "td_accountmaster am where r.dt='" + date + "' and r.ty=1 and r.trantype not in (5,27) and r.auth='Y' and r.Closeflag is "
                        + "null and am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getResultList());
            }

            if (tempList != null) {
                amt1 = (Double) tempList.get(0);
                count1 = (Integer) tempList.get(1);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Dramt),Count(r.ty) From recon r, accountmaster "
                        + "am where r.dt='" + date + "' and r.ty=1 and r.auth='Y' and am.acno = r.acno ").getResultList());
            } else {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Dramt),Count(r.ty) From recon r, accountmaster "
                        + "am where r.dt='" + date + "' and r.ty=1 and r.auth='Y' and am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getResultList());
            }
            if (tempList != null) {
                amt2 = (Double) tempList.get(0);
                count2 = (Integer) tempList.get(1);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Dramt),Count(r.ty) From ca_recon r, "
                        + "accountmaster am where r.dt='" + date + "' and r.ty=1 and r.auth='Y' and am.acno = r.acno ").getResultList());
            } else {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Dramt),Count(r.ty) From ca_recon r, "
                        + "accountmaster am where r.dt='" + date + "' and r.ty=1 and r.auth='Y' and am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getResultList());
            }

            if (tempList != null) {
                amt3 = (Double) tempList.get(0);
                count3 = (Integer) tempList.get(1);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Dramt),Count(r.ty) From loan_recon r, "
                        + "accountmaster am where r.dt='" + date + "' and r.ty=1 and r.auth='Y' and am.acno = r.acno").getResultList());
            } else {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Dramt),Count(r.ty) From loan_recon r, "
                        + "accountmaster am where r.dt='" + date + "' and r.ty=1 and r.auth='Y' and am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getResultList());
            }

            if (tempList != null) {
                amt4 = (Double) tempList.get(0);
                count4 = (Integer) tempList.get(1);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Dramt),Count(r.ty) From rdrecon r, "
                        + "accountmaster am where r.dt='" + date + "' and r.ty=1 and r.auth='Y' and am.acno = r.acno ").getResultList());
            } else {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(r.Dramt),Count(r.ty) From rdrecon r, "
                        + "accountmaster am where r.dt='" + date + "' and r.ty=1 and r.auth='Y' and am.acno = r.acno and am.CurBrCode='" + brCode + "'").getResultList());
            }

            if (tempList != null) {
                amt5 = (Double) tempList.get(0);
                count5 = (Integer) tempList.get(1);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(Dramt),Count(ty) From gl_recon where dt='"
                        + date + "' and ty=1 and auth='Y'").getResultList());
            } else {
                tempList = common.getDoubleAndIntegerElementsFromList(em.createNativeQuery("Select Sum(Dramt),Count(ty) From gl_recon where dt='"
                        + date + "' and ty=1 and auth='Y' and substring(acno,1,2)='" + brCode + "'").getResultList());
            }

            if (tempList != null) {
                amt6 = (Double) tempList.get(0);
                count6 = (Integer) tempList.get(1);
            }

            // DebitNo of vouchers
            drTotalVouch = count1 + count2 + count3 + count4 + count5 + count6;
            //Debit Amount
            drAmount = amt1 + amt2 + amt3 + amt4 + amt5 + amt6;

            //cashTotal
            cashTotal = 0;
            List resultList4 = em.createNativeQuery("select distinct acctNature from accounttypemaster where acctnature NOT IN ('" + CbsConstant.MS_AC + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.NON_PERFORM_AC + "','" + CbsConstant.SS_AC + "')").getResultList();
            for (int i = 0; i < resultList4.size(); i++) {
                Vector ele1 = (Vector) resultList4.get(i);
                if (ele1.get(0) != null) {
                    if (brCode.equalsIgnoreCase("0A")) {
                        cashTotal = cashTotal + common.getDoubleValueFromVector((Vector) em.createNativeQuery("select sum(r.cramt)-sum(r.dramt) from " + common.getTableName(ele1.get(0).toString()) + " r, accountmaster am where r.dt='" + date + "' and r.trantype=0 and r.auth='Y' and am.acno = r.acno ").getSingleResult());
                    } else {
                        cashTotal = cashTotal + common.getDoubleValueFromVector((Vector) em.createNativeQuery("select sum(r.cramt)-sum(r.dramt) from " + common.getTableName(ele1.get(0).toString()) + " r, accountmaster am where r.dt='" + date + "' and r.trantype=0 and r.auth='Y' and am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getSingleResult());
                    }

                    if (ele1.get(0).toString().equalsIgnoreCase(CbsConstant.FIXED_AC) || ele1.get(0).toString().equalsIgnoreCase(CbsConstant.MS_AC)) {
                        if (brCode.equalsIgnoreCase("0A")) {
                            cashTotal = cashTotal + common.getDoubleValueFromVector((Vector) em.createNativeQuery("select sum(r.cramt)-sum(r.dramt) from " + common.getTableName(ele1.get(0).toString()) + " r, td_accountmaster am where r.dt='" + date + "' and r.trantype=0 and r.auth='Y' and am.acno = r.acno ").getSingleResult());
                        } else {
                            cashTotal = cashTotal + common.getDoubleValueFromVector((Vector) em.createNativeQuery("select sum(r.cramt)-sum(r.dramt) from " + common.getTableName(ele1.get(0).toString()) + " r, td_accountmaster am where r.dt='" + date + "' and r.trantype=0 and r.auth='Y' and am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getSingleResult());
                        }

                    }
                    if (ele1.get(0).toString().equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        if (brCode.equalsIgnoreCase("0A")) {
                            cashTotal = cashTotal + common.getDoubleValueFromVector((Vector) em.createNativeQuery("select sum(cramt)-sum(dramt) from gl_recon where dt='" + date + "' and trantype=0 and auth='Y'").getSingleResult());
                        } else {
                            cashTotal = cashTotal + common.getDoubleValueFromVector((Vector) em.createNativeQuery("select sum(cramt)-sum(dramt) from gl_recon where dt='" + date + "' and trantype=0 and auth='Y' and substring(acno,1,2)='" + brCode + "'").getSingleResult());
                        }

                    }
                }
            }

            //balOp
            balOp = 0;
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = em.createNativeQuery("Select sum(amt) from cashinhand where ldate=(select max(ldate) from cashinhand where ldate < '" + date + "') ").getResultList();
            } else {
                tempList = em.createNativeQuery("Select sum(amt) from cashinhand where ldate=(select max(ldate) from cashinhand where ldate < '" + date + "') and brncode='" + brCode + "'").getResultList();
            }
            if (!tempList.isEmpty()) {
                Vector ele4 = (Vector) tempList.get(0);
                if (ele4 != null) {
                    balOp = Double.parseDouble(ele4.get(0).toString());
                }
            }

            //extTotal
            if (brCode.equalsIgnoreCase("0A")) {
                extTotal = common.getDoubleValueFromVector((Vector) em.createNativeQuery("select sum(cramt)-sum(dramt) from gl_recon where substring(acno,3,8)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_OTH.getValue() + "' and dt='" + date + "' and auth='Y' ").getSingleResult());
            } else {
                extTotal = common.getDoubleValueFromVector((Vector) em.createNativeQuery("select sum(cramt)-sum(dramt) from gl_recon where substring(acno,3,8)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_OTH.getValue() + "' and dt='" + date + "' and auth='Y' and substring(acno,1,2)='" + brCode + "'").getSingleResult());
            }
            //Grand total in Cash
            grantTotalInCash = (balOp + cashTotal + extTotal) / 1000;

            if (brCode.equalsIgnoreCase("0A")) {
                loanAgainstFD = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("Select sum(r.cramt)-sum(r.dramt) From loan_recon r,accountmaster am where r.dt<= '" + date + "' and substring(r.acno,3,2)='" + CbsAcCodeConstant.DEMAND_LOAN.getAcctCode() + "' and r.auth='Y' and r.acno = am.acno ").getSingleResult())) / 1000;
            } else {
                loanAgainstFD = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("Select sum(r.cramt)-sum(r.dramt) From loan_recon r,accountmaster am where r.dt<= '" + date + "' and substring(r.acno,3,2)='" + CbsAcCodeConstant.DEMAND_LOAN.getAcctCode() + "' and r.auth='Y' and r.acno = am.acno and am.CurBrCode = '" + brCode + "'").getSingleResult())) / 1000;
            }
            balanceTotal = 0;
            demandTotal = 0;
            List resultList3 = em.createNativeQuery("select acctNature,acctCode from accounttypemaster where acctnature IS NOT NULL AND ACCTNATURE NOT IN ('" + CbsConstant.PAY_ORDER + "','" + CbsConstant.OF_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.TERM_LOAN + "') ORDER BY ACCTCODE").getResultList();
            for (int j = 0; j < resultList3.size(); j++) {
                Vector ele1 = (Vector) resultList3.get(j);
                acctNature = ele1.get(0).toString();
                acctCode = ele1.get(1).toString();
                if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    if (acctCode.equalsIgnoreCase(CbsAcCodeConstant.CASH_CREDIT.getAcctCode())) {
                        continue;
                    } else if (acctCode.equalsIgnoreCase(CbsAcCodeConstant.OVER_DRAFT.getAcctCode())) {
                        continue;
                    }
                    if (brCode.equalsIgnoreCase("0A")) {
                        balanceTotal = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("select sum(r.cramt)-sum(r.dramt) From " + common.getTableName(acctNature) + " r, accountmaster am where r.dt <= '" + date + "' and substring(r.acno,3,2) = '" + acctCode + "' and r.auth='Y' and r.acno = am.acno ").getSingleResult())) / 1000;
                    } else {
                        balanceTotal = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("select sum(r.cramt)-sum(r.dramt) From " + common.getTableName(acctNature) + " r, accountmaster am where r.dt <= '" + date + "' and substring(r.acno,3,2) = '" + acctCode + "' and r.auth='Y' and r.acno = am.acno and am.CurBrCode = '" + brCode + "'").getSingleResult())) / 1000;
                    }
                    demandTotal += balanceTotal;
                }
            }
            List resultList1 = em.createNativeQuery("select acctNature,acctCode,acctDesc,glHead from accounttypemaster where "
                    + "acctnature IS NOT NULL AND ACCTNATURE IN ('" + CbsConstant.SAVING_AC + "','" + CbsConstant.CURRENT_AC + "',"
                    + "'" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "',"
                    + "'" + CbsConstant.DEPOSIT_SC + "')").getResultList();
            for (int i = 0; i < resultList1.size(); i++) {
                CashReserveReportPojo pojo = new CashReserveReportPojo();
                Vector ele1 = (Vector) resultList1.get(i);
                acctNature = ele1.get(0).toString();
                acctCode = ele1.get(1).toString();
                acctDesc = ele1.get(2).toString();
                glHead = ele1.get(3).toString();
                if (acctCode.equalsIgnoreCase(CbsAcCodeConstant.CASH_CREDIT.getAcctCode())) {
                    continue;
                } else if (acctCode.equalsIgnoreCase(CbsAcCodeConstant.OVER_DRAFT.getAcctCode())) {
                    continue;
                }
                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    if (brCode.equalsIgnoreCase("0A")) {
                        balanceTotal = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("select (sum(r.cramt)-sum(r.dramt)) from " + common.getTableName(acctNature) + " r,td_accountmaster am where r.dt<= '" + date + "' and substring(r.acno,3,2)='" + acctCode + "' and r.auth='Y' and r.closeflag is null and am.acno = r.acno ").getSingleResult())) / 1000;
                    } else {
                        balanceTotal = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("select (sum(r.cramt)-sum(r.dramt)) from " + common.getTableName(acctNature) + " r,td_accountmaster am where r.dt<= '" + date + "' and substring(r.acno,3,2)='" + acctCode + "' and r.auth='Y' and r.closeflag is null and am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getSingleResult())) / 1000;
                    }

                    balTimetotal += balanceTotal;
                } else if (acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    if (brCode.equalsIgnoreCase("0A")) {
                        balanceTotal = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("select (sum(r.cramt)-sum(r.dramt)) from " + common.getTableName(acctNature) + " r,accountmaster am where r.dt<='" + date + "' and substring(r.acno,3,2)='" + acctCode + "' and auth='Y' and am.acno = r.acno ").getSingleResult())) / 1000;
                    } else {
                        balanceTotal = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("select (sum(r.cramt)-sum(r.dramt)) from " + common.getTableName(acctNature) + " r,accountmaster am where r.dt<='" + date + "' and substring(r.acno,3,2)='" + acctCode + "' and auth='Y' and am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getSingleResult())) / 1000;
                    }

                    balTimetotal += balanceTotal;
                } else if (!acctCode.equalsIgnoreCase(CbsAcCodeConstant.CASH_CREDIT.getAcctCode()) || !acctCode.equalsIgnoreCase(CbsAcCodeConstant.OVER_DRAFT.getAcctCode())) {
                    if (brCode.equalsIgnoreCase("0A")) {
                        balanceTotal = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("select (sum(r.cramt)-sum(r.dramt)) from " + common.getTableName(acctNature) + " r, accountmaster am where r.dt<='" + date + "' and substring(r.acno,3,2)='" + acctCode + "' and auth='Y' and  am.acno = r.acno ").getSingleResult())) / 1000;
                    } else {
                        balanceTotal = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("select (sum(r.cramt)-sum(r.dramt)) from " + common.getTableName(acctNature) + " r, accountmaster am where r.dt<='" + date + "' and substring(r.acno,3,2)='" + acctCode + "' and auth='Y' and  am.acno = r.acno and am.CurBrCode = '" + brCode + "'").getSingleResult())) / 1000;
                    }

                }
                pojo.setAcctDesc(acctDesc);
                pojo.setCrAmount(crAmount);
                pojo.setCrTotalVouch(crTotalVouch);
                pojo.setDrAmount(drAmount);
                pojo.setDrTotalVouch(drTotalVouch);
                pojo.setGlHead(glHead);
                pojo.setLoanAgainstFD(loanAgainstFD);
                pojo.setDemandTotal(demandTotal);
                pojo.setBalTimetotal(balTimetotal);
                pojo.setGrantTotalInCash(grantTotalInCash);
                pojo.setTotalBalance(balanceTotal);
                returnList.add(pojo);
            }

            //Total interest amount
            if (brCode.equalsIgnoreCase("0A")) {
                //intTotal = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("Select (sum(CrAmt)-sum(DrAmt)) From GL_Recon Where Substring(ACNO,7,2)='93' AND dt <= '" + date + "' and auth = 'Y' ").getSingleResult())) / 1000;
                intTotal = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("Select (sum(CrAmt)-sum(DrAmt)) From gl_recon Where Substring(ACNO,5,6) BETWEEN '" + SiplConstant.GL_INT_PAY_ST.getValue() + "' AND '" + SiplConstant.GL_INT_PAY_END.getValue() + "' AND dt <= '" + date + "' and auth = 'Y' ").getSingleResult())) / 1000;
            } else {
                //intTotal = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("Select (sum(CrAmt)-sum(DrAmt)) From GL_Recon Where Substring(ACNO,7,2)='93' AND dt <= '" + date + "' and auth = 'Y' and substring(acno,1,2)='" + brCode + "'").getSingleResult())) / 1000;
                intTotal = (common.getDoubleValueFromVector((Vector) em.createNativeQuery("Select (sum(CrAmt)-sum(DrAmt)) From gl_recon Where Substring(ACNO,5,6) BETWEEN '" + SiplConstant.GL_INT_PAY_ST.getValue() + "' AND '" + SiplConstant.GL_INT_PAY_END.getValue() + "' AND dt <= '" + date + "' and auth = 'Y' and substring(acno,1,2)='" + brCode + "'").getSingleResult())) / 1000;
            }

            CashReserveReportPojo pojo = new CashReserveReportPojo();
            pojo.setAcctDesc("INTEREST PAYABLE");
            pojo.setBalTimetotal(balTimetotal);
            pojo.setCrAmount(crAmount);
            pojo.setCrTotalVouch(crTotalVouch);
            pojo.setDemandTotal(demandTotal);
            pojo.setDrAmount(drAmount);
            pojo.setDrTotalVouch(drTotalVouch);
            //pojo.setGlHead("GL009300");
            //pojo.setGlHead(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "009300");
            pojo.setGlHead(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_PAY_ST.getValue());
            pojo.setGrantTotalInCash(grantTotalInCash);
            pojo.setLoanAgainstFD(loanAgainstFD);
            pojo.setTotalBalance(intTotal);
            returnList.add(pojo);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

        return returnList;
    }

    public List<Td15HEntryCertificationPojo> getTd15HEntryCertification(String acNo, int fYear, String brCode) throws ApplicationException {
        List<Td15HEntryCertificationPojo> returnList = new ArrayList<Td15HEntryCertificationPojo>();
        try {
            String panNo = "";
            String dt = "";
            double interest = 0;
            double totalTaxD = 0;
            double rate = 0;
            String tdsCircle = "";
            String taxAcNo = "";
            String panNoDeDu = "";
            String name = "";
            String address = "";
            String period = fYear + "-" + (fYear + 1);
            String fromDate = fYear + "0401";
            String toDate = (fYear + 1) + "0331";
            List tempList = em.createNativeQuery("select c.panno,a.dt,a.interest,g.tdscircle,g.taxacno,g.panno,c.custname,c.craddress "
                    + "from td_interesthistory a,td_customermaster c,tdshistory e,tdsslab f,parameterinfo g where e.acno='" + acNo
                    + "' and  (e.todt  between '" + fromDate + "' AND '" + toDate + "') and c.brncode='" + brCode + "' AND c.custno like substring('"
                    + acNo + "',5,6)  and c.actype = SUBSTRING('" + acNo + "',3,2) and  a.ACNO='" + acNo + "' and a.fromdt>='" + fromDate
                    + "' and a.todt<='" + toDate + "' and f.type=1 and f.TDS_Applicabledate in (select max(TDS_Applicabledate) from tdsslab where "
                    + "TDS_Applicabledate<='" + fromDate + "') AND g.brncode='" + brCode + "' order by a.dt,e.dt").getResultList();
            Vector ele = null;
            for (int i = 0; i < tempList.size(); i++) {
                ele = (Vector) tempList.get(i);
                if (ele.get(0) != null) {
                    panNo = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    dt = dmyFormat.format((Date) ele.get(1));
                }
                if (ele.get(2) != null || !ele.get(2).toString().equalsIgnoreCase("")) {
                    interest = Double.parseDouble(ele.get(2).toString());
                }
                if (ele.get(3) != null) {
                    tdsCircle = ele.get(3).toString();
                }
                if (ele.get(4) != null) {
                    taxAcNo = ele.get(4).toString();
                }
                if (ele.get(5) != null) {
                    panNoDeDu = ele.get(5).toString();
                }
                if (ele.get(6) != null) {
                    name = ele.get(6).toString();
                }
                if (ele.get(7) != null) {
                    address = ele.get(7).toString();
                }
                Td15HEntryCertificationPojo pojo = new Td15HEntryCertificationPojo();
                pojo.setCustName(name + "  " + address);
                pojo.setDt(dt);
                pojo.setInterest(interest);
                pojo.setPanNo(panNo);
                pojo.setPanNoDeDu(panNoDeDu);
                pojo.setRate(0);
                pojo.setTaxAcNo(taxAcNo);
                pojo.setTdsCircle(tdsCircle);
                pojo.setTotalTaxD(0);
                pojo.setfYear(period);
                returnList.add(pojo);
            }
            tempList = em.createNativeQuery("SELECT e.dt, e.tds as totaltaxd,f.tds_rate as rate FROM tdshistory e,tdsslab f,parameterinfo g,"
                    + "td_customermaster c where e.ACNO='" + acNo + "' and  (e.todt  between '" + fromDate + "' AND '" + toDate + "')  and f.type=1 "
                    + "and f.TDS_Applicabledate in (select max(TDS_Applicabledate) from tdsslab where TDS_Applicabledate<='" + fromDate
                    + "') AND g.brncode='" + brCode + "' AND c.brncode='" + brCode + "' AND c.custno like SUBSTRING('" + acNo + "',5,6) and "
                    + "c.actype = SUBSTRING('" + acNo + "',3,2) order by e.dt").getResultList();
            if (!tempList.isEmpty()) {
                ele = (Vector) tempList.get(0);
                if (ele.get(0) != null) {
                    dt = dmyFormat.format((Date) ele.get(0));
                }
                if (ele.get(1) != null || !ele.get(1).toString().equalsIgnoreCase("")) {
                    totalTaxD = Double.parseDouble(ele.get(1).toString());
                }
                if (ele.get(2) != null || !ele.get(2).toString().equalsIgnoreCase("")) {
                    rate = Double.parseDouble(ele.get(2).toString());
                }
                Td15HEntryCertificationPojo pojo = new Td15HEntryCertificationPojo();
                pojo.setCustName(name + "  " + address);
                pojo.setDt(dt);
                pojo.setInterest(interest);
                pojo.setPanNo(panNo);
                pojo.setPanNoDeDu(panNoDeDu);
                pojo.setRate(rate);
                pojo.setTaxAcNo(taxAcNo);
                pojo.setTdsCircle(tdsCircle);
                pojo.setTotalTaxD(totalTaxD);
                pojo.setfYear(period);
                returnList.add(pojo);
                return returnList;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        System.out.println(returnList.size());
        return new ArrayList<Td15HEntryCertificationPojo>();
    }

    public List<CbsGeneralLedgerBookPojo> getGeneralLedgerBookData(String userDate, String orgBrnCode) throws ApplicationException {

        List<CbsGeneralLedgerBookPojo> glbDataList = new ArrayList<CbsGeneralLedgerBookPojo>();
        NumberFormat formatter = new DecimalFormat("#.##");
        try {
            String accType, date1 = "", actNature, acNo, acName = "", tempMinDate1 = null, tempMinDate = null, acCode, acctDesc, glHead, odDesc, bankName = "", bankAddress = "";
            double amt = 0, balOp = 0, crTotal, drTotal, glTotal = 0, cashTotal = 0, grandTotal = 0, extTotal = 0, cashInHand,
                    glCrTotal = 0, glDrTotal = 0, total, incomeTot, expTot, profit, odBalTotal = 0, crTotal1 = 0, drTotal1 = 0, odBal1;
            CbsGeneralLedgerBookPojo generalLedgerBookObject;
            List<CbsGeneralLedgerBookDebitBalPojo> debitBalDataList = new ArrayList<CbsGeneralLedgerBookDebitBalPojo>();
            List dataList1;

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("SHARE CAPITAL");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("RESERVE");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("STATUTORY RESERVE");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("BAD AND DOUBTFUL DEBTS");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("SPECIAL BAD DEBTS RESERVE");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("BUILDING FUND");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("GRATUITY FUND");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("STAFF WELFARE FUND");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("NATIONAL DEFENCE FUND");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("VEHICLE FUND");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("PROVISION ON OVERDUE INTEREST");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("RISK FUND");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("DEPRICIATION ON DEAD STOCK");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            dataList1 = em.createNativeQuery("SELECT date_format(MAX(ldate),'%Y%m%d') FROM cashinhand WHERE ldate<'" + userDate + "' "
                    + "AND brncode = '" + orgBrnCode + "'").getResultList();
            for (Object object : dataList1) {
                Vector element1 = (Vector) object;
                date1 = (String) element1.get(0);
            }

            dataList1 = em.createNativeQuery("SELECT amt FROM cashinhand WHERE ldate='" + date1 + "'  AND brncode = '" + orgBrnCode + "'").getResultList();
            for (Object object : dataList1) {
                Vector element1 = (Vector) object;
                amt = Double.parseDouble(element1.get(0).toString());
            }
            balOp = amt;
            dataList1 = em.createNativeQuery("select distinct acctNature from accounttypemaster where acctnature not in "
                    + "('" + CbsConstant.MS_AC + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.SS_AC + "','" + CbsConstant.NON_PERFORM_AC + "')  ORDER BY acctNature").getResultList();
            List dataList2 = new ArrayList();
            String tableName;
            for (Object object : dataList1) {
                Vector element1 = (Vector) object;
                actNature = (String) element1.get(0);
                crTotal = 0;
                drTotal = 0;
                if (actNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    dataList2 = em.createNativeQuery("SELECT ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
                            + "where  dt='" + userDate + "' and trantype=0 and auth='Y'AND IY NOT IN(9999) "
                            + "AND SUBSTRING(ACNO,1,2)= '" + orgBrnCode + "' and substring(ACNO,3,10) not in "
                            + "(select distinct acno from abb_parameter_info where purpose like '%CASH IN HAND%') "
                            + "AND IY NOT IN(9999)").getResultList();
                } else {
                    tableName = getTableName(actNature);
                    if (tableName.equalsIgnoreCase("td_recon")) {
                        dataList2 = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from td_recon r,"
                                + "td_accountmaster am  where r.dt='" + userDate + "' and r.trantype=0 and r.auth='Y' AND r.IY NOT IN(9999) "
                                + "AND  am.acno = r.acno and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
                    } else {
                        dataList2 = em.createNativeQuery(" SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from " + tableName + " r,"
                                + "accountmaster am  where r.dt='" + userDate + "' and r.trantype=0 and r.auth='Y' AND r.IY NOT IN(9999) "
                                + "AND  am.acno = r.acno and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
                    }
                }
                if (dataList2.size() > 0) {
                    Vector element2 = (Vector) dataList2.get(0);
                    crTotal = Double.parseDouble(element2.get(0).toString());
                    drTotal = Double.parseDouble(element2.get(1).toString());
                }
                glTotal = crTotal - drTotal;
                cashTotal = cashTotal + glTotal;

            }

            crTotal = 0;
            drTotal = 0;
            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
                    + "where substring(acno,3,8)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_OTH.getValue() + "' and dt='" + userDate + "' and auth='Y' "
                    + "AND SUBSTRING(ACNO,1,2)= '" + orgBrnCode + "'").getResultList();
            for (Object object : dataList1) {
                Vector element1 = (Vector) object;
                crTotal = Double.parseDouble(element1.get(0).toString());
                drTotal = Double.parseDouble(element1.get(1).toString());
            }
            extTotal = crTotal - drTotal;
            grandTotal = balOp + cashTotal + extTotal;
            cashInHand = grandTotal;

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("CASH IN HAND");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(formatter.format(grandTotal)));
            generalLedgerBookObject.setGNO(9);
            generalLedgerBookObject.setACTYPE("A");
            glbDataList.add(generalLedgerBookObject);

//            dataList1 = em.createNativeQuery("Select distinct(gl.acno) from gl_recon gl where (substring(acno,3,8) "
//                    + "between concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','005701') and concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','005799') or substring(acno,3,8) between concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','005901') and concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','005999')) "
//                    + "AND dt<='" + userDate + "' and auth='Y' AND SUBSTRING(gl.acno,1,2)= '" + orgBrnCode + "' "
//                    + "order by acno").getResultList();
            dataList1 = em.createNativeQuery("Select distinct(gl.acno) from gl_recon gl where (substring(acno,3,8) "
                    + "between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_BANKER_CA_ST.getValue() + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_BANKER_CA_END.getValue() + "' or substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_BANKER_FD_ST.getValue() + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_BANKER_FD_END.getValue() + "') "
                    + "AND dt<='" + userDate + "' and auth='Y' AND SUBSTRING(gl.acno,1,2)= '" + orgBrnCode + "' "
                    + "order by acno").getResultList();
            for (Object object : dataList1) {
                Vector element1 = (Vector) object;
                acNo = (String) element1.get(0);
                glCrTotal = 0;
                glDrTotal = 0;
                glTotal = 0;
                dataList2 = em.createNativeQuery("SELECT ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
                        + "where acno='" + acNo + "' AND dt<='" + userDate + "' and auth='Y' ").getResultList();
                if (dataList2.size() > 0) {
                    Vector element2 = (Vector) dataList2.get(0);
                    glCrTotal = Double.parseDouble(element2.get(0).toString());
                    glDrTotal = Double.parseDouble(element2.get(1).toString());
                }
                dataList2 = em.createNativeQuery("SELECT acname FROM gltable where acno='" + acNo + "'").getResultList();
                if (dataList2.size() > 0) {
                    Vector element2 = (Vector) dataList2.get(0);
                    acName = (String) element2.get(0);
                }

                glTotal = glCrTotal - glDrTotal;

                if (glTotal < 0) {
                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                    generalLedgerBookObject.setACNAME(acName);
                    generalLedgerBookObject.setAMOUNT(new BigDecimal(formatter.format(Math.abs(glTotal))));
                    generalLedgerBookObject.setGNO(9);
                    generalLedgerBookObject.setACTYPE("A");
                    glbDataList.add(generalLedgerBookObject);
                } else if (glTotal > 0) {
                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                    generalLedgerBookObject.setACNAME(acName);
                    generalLedgerBookObject.setAMOUNT(new BigDecimal(formatter.format(Math.abs(glTotal))));
                    generalLedgerBookObject.setGNO(9);
                    generalLedgerBookObject.setACTYPE("L");
                    glbDataList.add(generalLedgerBookObject);
                }
            }
            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(10);
            generalLedgerBookObject.setACTYPE("A");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("INVESTMENTS");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(10);
            generalLedgerBookObject.setACTYPE("A");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(10);
            generalLedgerBookObject.setACTYPE("A");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("LOANS AND ADVANCES");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(11);
            generalLedgerBookObject.setACTYPE("A");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(11);
            generalLedgerBookObject.setACTYPE("A");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("DEPOSITS");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(11);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACNAME("");
            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
            generalLedgerBookObject.setGNO(11);
            generalLedgerBookObject.setACTYPE("L");
            glbDataList.add(generalLedgerBookObject);

            dataList1 = em.createNativeQuery("SELECT acCtnature,accTCode,acctdesc,GLHEAD FROM accounttypemaster WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND acCtnature IS NOT NULL").getResultList();
            for (Object object : dataList1) {
                Vector element1 = (Vector) object;
                actNature = (String) element1.get(0);
                acCode = (String) element1.get(1);
                acctDesc = (String) element1.get(2);
                glHead = (String) element1.get(3);
                crTotal = 0;
                drTotal = 0;
                total = 0;
                grandTotal = 0;
                if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    dataList2 = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) FROM td_recon r,td_accountmaster am WHERE "
                            + "SUBSTRING(r.acno,3,2)= '" + acCode + "' AND r.dt<='" + userDate + "'  AND r.auth='Y'  AND r.closeflag IS NULL AND "
                            + "am.acno = r.acno and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
                    if (dataList2.size() > 0) {
                        Vector element2 = (Vector) dataList2.get(0);
                        crTotal = Double.parseDouble(element2.get(0).toString());
                        drTotal = Double.parseDouble(element2.get(1).toString());
                    }
                } else {
                    if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        debitBalDataList = getDebitBal(acCode, userDate, orgBrnCode);
                        if (debitBalDataList.size() > 0) {
                            crTotal = debitBalDataList.get(0).getTot();
                            odBal1 = debitBalDataList.get(0).getAcBal();
                            odBalTotal = odBal1;
                        }
                    } else {
                        tableName = getTableName(actNature);
                        dataList2 = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from " + tableName + " r,"
                                + "accountmaster am where substring(r.acno,3,2)='" + acCode + "' AND r.dt<='" + userDate + "' and r.auth='Y' AND "
                                + "am.acno = r.acno and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
                        if (dataList2.size() > 0) {
                            Vector element2 = (Vector) dataList2.get(0);
                            crTotal = Double.parseDouble(element2.get(0).toString());
                            drTotal = Double.parseDouble(element2.get(1).toString());
                        }
                    }
                }

                total = crTotal - drTotal;
                glTotal = 0;
                dataList2 = em.createNativeQuery("SELECT ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
                        + "where substring(acno,3,8)='" + glHead + "'  AND dt<='" + userDate + "' and auth='Y' "
                        + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
                if (dataList2.size() > 0) {
                    Vector element2 = (Vector) dataList2.get(0);
                    crTotal1 = Double.parseDouble(element2.get(0).toString());
                    drTotal1 = Double.parseDouble(element2.get(1).toString());
                }
                glTotal = crTotal1 - drTotal1;
                if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    if (glTotal < 0) {
                        odBalTotal = Math.abs(odBalTotal) + Math.abs(glTotal);
                        glTotal = 0;
                    }
                    odDesc = "DEBIT BALANCE IN " + acctDesc;

                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                    generalLedgerBookObject.setACNAME(odDesc);
                    generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(odBalTotal)));
                    generalLedgerBookObject.setGNO(11);
                    generalLedgerBookObject.setACTYPE("A");
                    glbDataList.add(generalLedgerBookObject);
                }

                grandTotal = glTotal + total;
                double roundGrandTotal = Double.valueOf(formatter.format(grandTotal));
                if (roundGrandTotal < 0) {
                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                    generalLedgerBookObject.setACNAME(acctDesc);
                    generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(grandTotal)));
                    generalLedgerBookObject.setGNO(11);
                    generalLedgerBookObject.setACTYPE("A");
                    glbDataList.add(generalLedgerBookObject);
                } else if (roundGrandTotal > 0) {
                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                    generalLedgerBookObject.setACNAME(acctDesc);
                    generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(grandTotal)));
                    generalLedgerBookObject.setGNO(11);
                    generalLedgerBookObject.setACTYPE("L");
                    glbDataList.add(generalLedgerBookObject);
                }
            }
//            dataList1 = em.createNativeQuery("Select distinct(acno) from gl_recon where substring(acno,3,8) "
//                    + "between concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','005201') and concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','005299')  and dt<='" + userDate + "' and auth='Y' "
//                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "' order by acno").getResultList();
            dataList1 = em.createNativeQuery("Select distinct(acno) from gl_recon where substring(acno,3,8) "
                    + "between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PRO_PREV_ST.getValue() + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PRO_PREV_END.getValue() + "' and dt<='" + userDate + "' and auth='Y' "
                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "' order by acno").getResultList();
            for (Object object : dataList1) {
                Vector element1 = (Vector) object;
                acNo = (String) element1.get(0);
                dataList2 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
                        + "where acno='" + acNo + "' AND dt<='" + userDate + "' and auth='Y' ").getResultList();
                if (dataList2.size() > 0) {
                    Vector element2 = (Vector) dataList2.get(0);
                    glCrTotal = Double.parseDouble(element2.get(0).toString());
                    glDrTotal = Double.parseDouble(element2.get(1).toString());
                }
                dataList2 = em.createNativeQuery("SELECT acname FROM gltable where acno='" + acNo + "'").getResultList();
                if (dataList2.size() > 0) {
                    Vector element2 = (Vector) dataList2.get(0);
                    acName = (String) element2.get(0);
                }
                glTotal = glCrTotal - glDrTotal;
                if (glTotal < 0) {
                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                    generalLedgerBookObject.setACNAME(acName);
                    generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(glTotal)));
                    generalLedgerBookObject.setGNO(11);
                    generalLedgerBookObject.setACTYPE("A");
                    glbDataList.add(generalLedgerBookObject);
                } else {
                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                    generalLedgerBookObject.setACNAME(acName);
                    generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(glTotal)));
                    generalLedgerBookObject.setGNO(11);
                    generalLedgerBookObject.setACTYPE("L");
                    glbDataList.add(generalLedgerBookObject);
                }
            }

//            dataList1 = em.createNativeQuery("SELECT ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon where " //ifnull is inserted by Ankit for removing nullPointerException
//                    + "substring(acno,3,6)=concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','0093') and dt<='" + userDate + "' and auth='Y' "
//                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            dataList1 = em.createNativeQuery("SELECT ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon where " //isnull is inserted by Ankit for removing nullPointerException
                    + "substring(acno,3,8) BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_PAY_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_PAY_END.getValue() + "' and dt<='" + userDate + "' and auth='Y' "
                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                crTotal = Double.parseDouble(element1.get(0).toString());
                drTotal = Double.parseDouble(element1.get(1).toString());
            }
            total = crTotal - drTotal;
            grandTotal = total;
            if (grandTotal < 0) {
                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                generalLedgerBookObject.setACNAME("INTEREST PAYABLE");
                generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(grandTotal)));
                generalLedgerBookObject.setGNO(12);
                generalLedgerBookObject.setACTYPE("A");
                glbDataList.add(generalLedgerBookObject);
            } else {
                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                generalLedgerBookObject.setACNAME("INTEREST PAYABLE");
                generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(grandTotal)));
                generalLedgerBookObject.setGNO(12);
                generalLedgerBookObject.setACTYPE("L");
                glbDataList.add(generalLedgerBookObject);
            }

//            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                    + "where substring(acno,3,6)=concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','0092')  AND dt<='" + userDate + "'and auth='Y' "
//                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
                    + "where substring(acno,3,8) BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_CR_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_CR_END.getValue() + "' AND dt<='" + userDate + "'and auth='Y' "
                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                crTotal = Double.parseDouble(element1.get(0).toString());
                drTotal = Double.parseDouble(element1.get(1).toString());
            }
            total = crTotal - drTotal;
            grandTotal = total;
            if (grandTotal < 0) {
                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                generalLedgerBookObject.setACNAME("SUNDRY CREDITORS");
                generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(grandTotal)));
                generalLedgerBookObject.setGNO(12);
                generalLedgerBookObject.setACTYPE("A");
                glbDataList.add(generalLedgerBookObject);
            } else {
                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                generalLedgerBookObject.setACNAME("SUNDRY CREDITORS");
                generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(grandTotal)));
                generalLedgerBookObject.setGNO(12);
                generalLedgerBookObject.setACTYPE("L");
                glbDataList.add(generalLedgerBookObject);
            }

//            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                    + "where SUBSTRING(acno,3,6)= concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','0091') AND dt<='" + userDate + "' and auth='Y' "
//                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            dataList1 = em.createNativeQuery("select IFNULL(sum(cramt),0),IFNULL(sum(dramt),0) from gl_recon "
                    + "where SUBSTRING(acno,3,8) BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_DR_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_DR_END.getValue() + "' AND dt<='" + userDate + "' and auth='Y' "
                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                crTotal = Double.parseDouble(element1.get(0).toString());
                drTotal = Double.parseDouble(element1.get(1).toString());
            }
            total = crTotal - drTotal;
            grandTotal = total;
            if (grandTotal < 0) {
                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                generalLedgerBookObject.setACNAME("SUNDRY DEBTORS");
                generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(grandTotal)));
                generalLedgerBookObject.setGNO(12);
                generalLedgerBookObject.setACTYPE("A");
                glbDataList.add(generalLedgerBookObject);
            } else {
                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                generalLedgerBookObject.setACNAME("SUNDRY DEBTORS");
                generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(grandTotal)));
                generalLedgerBookObject.setGNO(12);
                generalLedgerBookObject.setACTYPE("L");
                glbDataList.add(generalLedgerBookObject);
            }

//            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                    + "where SUBSTRING(acno,3,6)= concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','0094') AND dt<='" + userDate + "' and auth='Y' "
//                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            dataList1 = em.createNativeQuery("select IFNULL(sum(cramt),0),IFNULL(sum(dramt),0) from gl_recon "
                    + "where SUBSTRING(acno,3,8) BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_REC_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_REC_END.getValue() + "' AND dt<='" + userDate + "' and auth='Y' "
                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                crTotal = Double.parseDouble(element1.get(0).toString());
                drTotal = Double.parseDouble(element1.get(1).toString());
            }
            total = crTotal - drTotal;
            grandTotal = total;
            if (grandTotal < 0) {
                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                generalLedgerBookObject.setACNAME("BILLS RECIEVABLE");
                generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(grandTotal)));
                generalLedgerBookObject.setGNO(12);
                generalLedgerBookObject.setACTYPE("A");
                glbDataList.add(generalLedgerBookObject);
            } else {
                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                generalLedgerBookObject.setACNAME("BILLS RECIEVABLE");
                generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(grandTotal)));
                generalLedgerBookObject.setGNO(12);
                generalLedgerBookObject.setACTYPE("L");
                glbDataList.add(generalLedgerBookObject);
            }

            dataList1 = em.createNativeQuery("select min(mindate) from yearend where yearendflag='N' "
                    + "AND brncode = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                tempMinDate = (String) element1.get(0);
            }
            dataList1 = em.createNativeQuery("SELECT ifnull(MINDATE,'') FROM yearend WHERE MINDATE <= '" + userDate + "' AND MAXDATE >= '" + userDate + "' "
                    + "AND brncode = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                tempMinDate1 = (String) element1.get(0);
            }
            if (tempMinDate1.equals("")) {
                tempMinDate1 = tempMinDate;
            } else if (tempMinDate1 == null) {
                tempMinDate1 = tempMinDate;
            }

//            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                    + "where substring(acno,3,8) between concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','002501') and concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','003000') AND "
//                    + "dt<='" + userDate + "'  AND dt>='" + tempMinDate1 + "' "
//                    + "and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
                    + "where substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_END.getValue() + "' AND "
                    + "dt<='" + userDate + "'  AND dt>='" + tempMinDate1 + "' "
                    + "and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                glCrTotal = Double.parseDouble(element1.get(0).toString());
                glDrTotal = Double.parseDouble(element1.get(1).toString());
            }
            glTotal = glCrTotal - glDrTotal;
            incomeTot = glTotal;

//            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from gl_recon "
//                    + "where substring(acno,3,8) between concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','002000') and concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','002500') AND "
//                    + "dt<='" + userDate + "'  AND dt>='" + tempMinDate1 + "' "
//                    + "and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from gl_recon "
                    + "where substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue() + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' AND "
                    + "dt<='" + userDate + "'  AND dt>='" + tempMinDate1 + "' "
                    + "and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                glTotal = Double.parseDouble(element1.get(0).toString());
            }
            expTot = glTotal;

            if (expTot > 0) {
                profit = incomeTot + expTot;
            } else {
                profit = incomeTot - Math.abs(expTot);
            }

            if (profit < 0) {
                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                generalLedgerBookObject.setACNAME("PROFIT & LOSS");
                generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(profit)));
                generalLedgerBookObject.setGNO(12);
                generalLedgerBookObject.setACTYPE("A");
                glbDataList.add(generalLedgerBookObject);
            } else {
                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                generalLedgerBookObject.setACNAME("PROFIT & LOSS");
                generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(profit)));
                generalLedgerBookObject.setGNO(12);
                generalLedgerBookObject.setACTYPE("L");
                glbDataList.add(generalLedgerBookObject);
            }
//            dataList1 = em.createNativeQuery("select distinct(gl.acno) from gl_recon gl where "
//                    + "substring(acno,3,8) not between concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','002000') and concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','003000') and subString(acno,5,4) "
//                    + "NOT IN ('0052','0055','0057','0059','0091','0092','0093','0094') "
//                    + "and substring(acno,3,8) not in (SELECT GLHEAD FROM  accounttypemaster where glhead is not null) "
//                    + "and dt<='" + userDate + "' and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "' order by acno").getResultList();
            dataList1 = em.createNativeQuery("select distinct(gl.acno) from gl_recon gl where "
                    + "substring(acno,3,8) not between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_ST.getValue() + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "' and subString(acno,3,8) "
                    + "NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PRO_PREV_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PRO_PREV_END.getValue() + "' and subString(acno,3,6) "
                    + "NOT IN ('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CASH.getValue() + "') and subString(acno,3,8) "
                    + "NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_BANKER_CA_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_BANKER_CA_END.getValue() + "' and subString(acno,3,8) "
                    + "NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_BANKER_FD_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_BANKER_FD_END.getValue() + "' and subString(acno,3,8) "
                    + "NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_DR_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_DR_END.getValue() + "' and subString(acno,3,8) "
                    + "NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_CR_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_CR_END.getValue() + "' and subString(acno,3,8) "
                    + "NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_PAY_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_PAY_END.getValue() + "' and subString(acno,3,8) "
                    + "NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_REC_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_REC_END.getValue() + "'"
                    + "and substring(acno,3,8) not in (SELECT GLHEAD FROM  accounttypemaster where glhead is not null) "
                    + "and dt<='" + userDate + "' and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "' order by acno").getResultList();
            if (dataList1.size() > 0) {
                for (Object object : dataList1) {
                    Vector element1 = (Vector) object;
                    acNo = (String) element1.get(0);
                    glCrTotal = 0;
                    glDrTotal = 0;
                    glTotal = 0;

                    dataList2 = em.createNativeQuery("SELECT ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
                            + "where acno='" + acNo + "' AND dt<='" + userDate + "' and auth='Y' ").getResultList();
                    if (dataList2.size() > 0) {
                        Vector element2 = (Vector) dataList2.get(0);
                        glCrTotal = Double.parseDouble(element2.get(0).toString());
                        glDrTotal = Double.parseDouble(element2.get(1).toString());
                    }
                    glTotal = glCrTotal - glDrTotal;

                    dataList2 = em.createNativeQuery("SELECT ifnull(acname,'') FROM gltable where acno='" + acNo + "'").getResultList();
                    if (dataList2.size() > 0) {
                        Vector element2 = (Vector) dataList2.get(0);
                        acName = (String) element2.get(0);
                    }

                    if (glTotal < 0) {
                        generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                        generalLedgerBookObject.setACNAME(acName);
                        generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(glTotal)));
                        generalLedgerBookObject.setGNO(12);
                        generalLedgerBookObject.setACTYPE("A");
                        glbDataList.add(generalLedgerBookObject);
                    } else if (glTotal > 0) {
                        generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
                        generalLedgerBookObject.setACNAME(acName);
                        generalLedgerBookObject.setAMOUNT(new BigDecimal(Math.abs(glTotal)));
                        generalLedgerBookObject.setGNO(12);
                        generalLedgerBookObject.setACTYPE("L");
                        glbDataList.add(generalLedgerBookObject);
                    }
                }
            }

            List<CbsGeneralLedgerBookPojo> glbNewList = new ArrayList<CbsGeneralLedgerBookPojo>();
            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACTYPE("L");
            generalLedgerBookObject.setGNO(9);
            glbNewList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACTYPE("A");
            generalLedgerBookObject.setGNO(9);
            glbNewList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACTYPE("A");
            generalLedgerBookObject.setGNO(10);
            glbNewList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACTYPE("L");
            generalLedgerBookObject.setGNO(11);
            glbNewList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACTYPE("A");
            generalLedgerBookObject.setGNO(11);
            glbNewList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACTYPE("L");
            generalLedgerBookObject.setGNO(12);
            glbNewList.add(generalLedgerBookObject);

            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
            generalLedgerBookObject.setACTYPE("A");
            generalLedgerBookObject.setGNO(12);
            glbNewList.add(generalLedgerBookObject);
            dataList1 = common.getBranchNameandAddress(orgBrnCode);
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            if (glbDataList.size() > 0) {
                ComparatorChain chain = new ComparatorChain();
                chain.addComparator(new SortGeneralLedgerBookByActype());
                chain.addComparator(new SortGeneralLedgerBookByGno());
                chain.addComparator(new CbsGeneralLedgerBookByAcname());
                Collections.sort(glbDataList, chain);

                for (CbsGeneralLedgerBookPojo object : glbDataList) {
                    object.setBankname(bankName);
                    object.setBankaddress(bankAddress);
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return glbDataList;
    }

//    public List<CbsGeneralLedgerBookPojo> getGeneralLedgerBookDataOne(String userDate, String orgBrnCode) throws ApplicationException {
//
//        List<CbsGeneralLedgerBookPojo> glbDataList = new ArrayList<CbsGeneralLedgerBookPojo>();
//        NumberFormat formatter = new DecimalFormat("#.##");
//        try {
//            String accType, date1 = "", actNature, acNo, acName = "", tempMinDate1 = null, tempMinDate = null, acCode, acctDesc, glHead, odDesc, bankName = "", bankAddress = "";
//            BigDecimal amt = new BigDecimal("0");
//            BigDecimal balOp = new BigDecimal("0");
//            BigDecimal crTotal = new BigDecimal("0");
//            BigDecimal drTotal = new BigDecimal("0");
//            BigDecimal glTotal = new BigDecimal("0");
//            BigDecimal cashTotal = new BigDecimal("0");
//            BigDecimal grandTotal = new BigDecimal("0");
//            BigDecimal extTotal = new BigDecimal("0");
//            BigDecimal cashInHand = new BigDecimal("0");
//            BigDecimal glCrTotal = new BigDecimal("0");
//            BigDecimal glDrTotal = new BigDecimal("0");
//            BigDecimal total = new BigDecimal("0");
//            BigDecimal incomeTot = new BigDecimal("0");
//            BigDecimal expTot = new BigDecimal("0");
//            BigDecimal profit = new BigDecimal("0");
//            BigDecimal odBalTotal = new BigDecimal("0");
//            BigDecimal crTotal1 = new BigDecimal("0");
//            BigDecimal drTotal1 = new BigDecimal("0");
//            BigDecimal odBal1 = new BigDecimal("0");
//
//
//
//            CbsGeneralLedgerBookPojo generalLedgerBookObject;
//            List<CbsGeneralLedgerBookDebitBalPojo> debitBalDataList = new ArrayList<CbsGeneralLedgerBookDebitBalPojo>();
//            List dataList1;
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("SHARE CAPITAL");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("RESERVE");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("STATUTORY RESERVE");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("BAD AND DOUBTFUL DEBTS");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("SPECIAL BAD DEBTS RESERVE");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("BUILDING FUND");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("GRATUITY FUND");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("STAFF WELFARE FUND");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("NATIONAL DEFENCE FUND");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("VEHICLE FUND");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("PROVISION ON OVERDUE INTEREST");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("RISK FUND");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("DEPRICIATION ON DEAD STOCK");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            dataList1 = em.createNativeQuery("SELECT date_format(MAX(ldate),'%Y%m%d') FROM cashinhand WHERE ldate<'" + userDate + "' "
//                    + "AND brncode = '" + orgBrnCode + "'").getResultList();
//            for (Object object : dataList1) {
//                Vector element1 = (Vector) object;
//                date1 = (String) element1.get(0);
//            }
//
//            dataList1 = em.createNativeQuery("SELECT amt FROM cashinhand WHERE ldate='" + date1 + "'  AND brncode = '" + orgBrnCode + "'").getResultList();
//            for (Object object : dataList1) {
//                Vector element1 = (Vector) object;
//                amt = new BigDecimal(element1.get(0).toString());
//            }
//            balOp = amt;
//            dataList1 = em.createNativeQuery("select distinct acctNature from accounttypemaster where acctnature not in "
//                    + "('" + CbsConstant.MS_AC + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.SS_AC + "','" + CbsConstant.NON_PERFORM_AC + "')  ORDER BY acctNature").getResultList();
//            List dataList2 = new ArrayList();
//            String tableName;
//            for (Object object : dataList1) {
//                Vector element1 = (Vector) object;
//                actNature = (String) element1.get(0);
//                crTotal = new BigDecimal("0");
//                drTotal = new BigDecimal("0");
//                if (actNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                    dataList2 = em.createNativeQuery("SELECT ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                            + "where  dt='" + userDate + "' and trantype=0 and auth='Y'AND IY NOT IN(9999) "
//                            + "AND SUBSTRING(ACNO,1,2)= '" + orgBrnCode + "' and substring(ACNO,3,10) not in "
//                            + "(select distinct acno from abb_parameter_info where purpose like '%CASH IN HAND%') "
//                            + "AND IY NOT IN(9999)").getResultList();
//                } else {
//                    tableName = getTableName(actNature);
//                    if (tableName.equalsIgnoreCase("td_recon")) {
//                        dataList2 = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from td_recon r,"
//                                + "td_accountmaster am  where r.dt='" + userDate + "' and r.trantype=0 and r.auth='Y' AND r.IY NOT IN(9999) "
//                                + "AND  am.acno = r.acno and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
//                    } else {
//                        dataList2 = em.createNativeQuery(" SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from " + tableName + " r,"
//                                + "accountmaster am  where r.dt='" + userDate + "' and r.trantype=0 and r.auth='Y' AND r.IY NOT IN(9999) "
//                                + "AND  am.acno = r.acno and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
//                    }
//                }
//                if (dataList2.size() > 0) {
//                    Vector element2 = (Vector) dataList2.get(0);
//                    crTotal = new BigDecimal(element2.get(0).toString());
//                    drTotal = new BigDecimal(element2.get(1).toString());
//                }
//                glTotal = crTotal.subtract(drTotal);
//                cashTotal = cashTotal.add(glTotal);
//
//            }
//
//            crTotal = new BigDecimal("0");
//            drTotal = new BigDecimal("0");
//            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                    + "where substring(acno,3,8)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "005502' and dt='" + userDate + "' and auth='Y' "
//                    + "AND SUBSTRING(ACNO,1,2)= '" + orgBrnCode + "'").getResultList();
//            for (Object object : dataList1) {
//                Vector element1 = (Vector) object;
//                crTotal = new BigDecimal(element1.get(0).toString());
//                drTotal = new BigDecimal(element1.get(1).toString());
//            }
//            extTotal = crTotal.subtract(drTotal);
//            grandTotal = balOp.add(cashTotal).add(extTotal);
//            cashInHand = grandTotal;
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("CASH IN HAND");
//            //generalLedgerBookObject.setAMOUNT(new BigDecimal(formatter.format(grandTotal)));
//            generalLedgerBookObject.setAMOUNT(grandTotal);
//            generalLedgerBookObject.setGNO(9);
//            generalLedgerBookObject.setACTYPE("A");
//            glbDataList.add(generalLedgerBookObject);
//
//            dataList1 = em.createNativeQuery("Select distinct(gl.acno) from gl_recon gl where (substring(acno,3,8) "
//                    + "between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "'+'005701' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "005799' or substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "005901' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "005999') "
//                    + "AND dt<='" + userDate + "' and auth='Y' AND SUBSTRING(gl.acno,1,2)= '" + orgBrnCode + "' "
//                    + "order by acno").getResultList();
//            for (Object object : dataList1) {
//                Vector element1 = (Vector) object;
//                acNo = (String) element1.get(0);
//                glCrTotal = new BigDecimal("0");
//                glDrTotal = new BigDecimal("0");
//                glTotal = new BigDecimal("0");
//                dataList2 = em.createNativeQuery("SELECT ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                        + "where acno='" + acNo + "' AND dt<='" + userDate + "' and auth='Y' ").getResultList();
//                if (dataList2.size() > 0) {
//                    Vector element2 = (Vector) dataList2.get(0);
//                    glCrTotal = new BigDecimal(element2.get(0).toString());
//                    glDrTotal = new BigDecimal(element2.get(1).toString());
//                }
//                dataList2 = em.createNativeQuery("SELECT acname FROM gltable where acno='" + acNo + "'").getResultList();
//                if (dataList2.size() > 0) {
//                    Vector element2 = (Vector) dataList2.get(0);
//                    acName = (String) element2.get(0);
//                }
//
//                glTotal = glCrTotal.subtract(glDrTotal);
//
//                if (glTotal.compareTo(new BigDecimal("0")) == -1) {
//                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                    generalLedgerBookObject.setACNAME(acName);
//                    //generalLedgerBookObject.setAMOUNT(new BigDecimal(formatter.format(glTotal.abs())));
//                    generalLedgerBookObject.setAMOUNT(glTotal.abs());
//                    generalLedgerBookObject.setGNO(9);
//                    generalLedgerBookObject.setACTYPE("A");
//                    glbDataList.add(generalLedgerBookObject);
//                } else if (glTotal.compareTo(new BigDecimal("0")) == 1) {
//                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                    generalLedgerBookObject.setACNAME(acName);
//                    //generalLedgerBookObject.setAMOUNT(new BigDecimal(formatter.format(glTotal.abs())));
//                    generalLedgerBookObject.setAMOUNT(glTotal.abs());
//                    generalLedgerBookObject.setGNO(9);
//                    generalLedgerBookObject.setACTYPE("L");
//                    glbDataList.add(generalLedgerBookObject);
//                }
//            }
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(10);
//            generalLedgerBookObject.setACTYPE("A");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("INVESTMENTS");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(10);
//            generalLedgerBookObject.setACTYPE("A");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(10);
//            generalLedgerBookObject.setACTYPE("A");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("LOANS AND ADVANCES");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(11);
//            generalLedgerBookObject.setACTYPE("A");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(11);
//            generalLedgerBookObject.setACTYPE("A");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("DEPOSITS");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(11);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACNAME("");
//            generalLedgerBookObject.setAMOUNT(new BigDecimal(0));
//            generalLedgerBookObject.setGNO(11);
//            generalLedgerBookObject.setACTYPE("L");
//            glbDataList.add(generalLedgerBookObject);
//
//            dataList1 = em.createNativeQuery("SELECT acCtnature,accTCode,acctdesc,GLHEAD FROM accounttypemaster WHERE ACCTNATURE<> '" + CbsConstant.PAY_ORDER + "' AND acCtnature IS NOT NULL").getResultList();
//            for (Object object : dataList1) {
//                Vector element1 = (Vector) object;
//                actNature = (String) element1.get(0);
//                acCode = (String) element1.get(1);
//                acctDesc = (String) element1.get(2);
//                glHead = (String) element1.get(3);
//                crTotal = new BigDecimal("0");
//                drTotal = new BigDecimal("0");
//                total = new BigDecimal("0");
//                grandTotal = new BigDecimal("0");
//                if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
//                    dataList2 = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) FROM td_recon r,td_accountmaster am WHERE "
//                            + "SUBSTRING(r.acno,3,2)= '" + acCode + "' AND r.dt<='" + userDate + "'  AND r.auth='Y'  AND r.closeflag IS NULL AND "
//                            + "am.acno = r.acno and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
//                    if (dataList2.size() > 0) {
//                        Vector element2 = (Vector) dataList2.get(0);
//                        crTotal = new BigDecimal(element2.get(0).toString());
//                        drTotal = new BigDecimal(element2.get(1).toString());
//                    }
//                } else {
//                    if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                        debitBalDataList = getDebitBal(acCode, userDate, orgBrnCode);
//                        if (debitBalDataList.size() > 0) {
//                            crTotal = new BigDecimal(debitBalDataList.get(0).getTot());
//                            odBal1 = new BigDecimal(debitBalDataList.get(0).getAcBal());
//                            odBalTotal = odBal1;
//                        }
//                    } else {
//                        tableName = getTableName(actNature);
//                        dataList2 = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from " + tableName + " r,"
//                                + "accountmaster am where substring(r.acno,3,2)='" + acCode + "' AND r.dt<='" + userDate + "' and r.auth='Y' AND "
//                                + "am.acno = r.acno and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
//                        if (dataList2.size() > 0) {
//                            Vector element2 = (Vector) dataList2.get(0);
//                            crTotal = new BigDecimal(element2.get(0).toString());
//                            drTotal = new BigDecimal(element2.get(1).toString());
//                        }
//                    }
//                }
//
//                total = crTotal.subtract(drTotal);
//                glTotal = new BigDecimal("0");
//                dataList2 = em.createNativeQuery("SELECT ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                        + "where substring(acno,3,8)='" + glHead + "'  AND dt<='" + userDate + "' and auth='Y' "
//                        + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
//                if (dataList2.size() > 0) {
//                    Vector element2 = (Vector) dataList2.get(0);
//                    crTotal1 = new BigDecimal(element2.get(0).toString());
//                    drTotal1 = new BigDecimal(element2.get(1).toString());
//                }
//                glTotal = crTotal1.subtract(drTotal1);
//                if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                    if (glTotal.compareTo(new BigDecimal("0")) == -1) {
//                        odBalTotal = odBalTotal.abs().add(glTotal.abs());
//                        glTotal = new BigDecimal("0");
//                    }
//                    odDesc = "DEBIT BALANCE IN " + acctDesc;
//
//                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                    generalLedgerBookObject.setACNAME(odDesc);
//                    generalLedgerBookObject.setAMOUNT(odBalTotal.abs());
//                    generalLedgerBookObject.setGNO(11);
//                    generalLedgerBookObject.setACTYPE("A");
//                    glbDataList.add(generalLedgerBookObject);
//                }
//
//                grandTotal = glTotal.add(total);
//                BigDecimal roundGrandTotal = grandTotal;
//                if (roundGrandTotal.compareTo(new BigDecimal("0")) == -1) {
//                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                    generalLedgerBookObject.setACNAME(acctDesc);
//                    generalLedgerBookObject.setAMOUNT(grandTotal.abs());
//                    generalLedgerBookObject.setGNO(11);
//                    generalLedgerBookObject.setACTYPE("A");
//                    glbDataList.add(generalLedgerBookObject);
//                } else if (roundGrandTotal.compareTo(new BigDecimal("0")) == 1) {
//                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                    generalLedgerBookObject.setACNAME(acctDesc);
//                    generalLedgerBookObject.setAMOUNT(grandTotal.abs());
//                    generalLedgerBookObject.setGNO(11);
//                    generalLedgerBookObject.setACTYPE("L");
//                    glbDataList.add(generalLedgerBookObject);
//                }
//            }
//            dataList1 = em.createNativeQuery("Select distinct(acno) from gl_recon where substring(acno,3,8) "
//                    + "between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "005201' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "005299'  and dt<='" + userDate + "' and auth='Y' "
//                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "' order by acno").getResultList();
//            for (Object object : dataList1) {
//                Vector element1 = (Vector) object;
//                acNo = (String) element1.get(0);
//                dataList2 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                        + "where acno='" + acNo + "' AND dt<='" + userDate + "' and auth='Y' ").getResultList();
//                if (dataList2.size() > 0) {
//                    Vector element2 = (Vector) dataList2.get(0);
//                    glCrTotal = new BigDecimal(element2.get(0).toString());
//                    glDrTotal = new BigDecimal(element2.get(1).toString());
//                }
//                dataList2 = em.createNativeQuery("SELECT acname FROM gltable where acno='" + acNo + "'").getResultList();
//                if (dataList2.size() > 0) {
//                    Vector element2 = (Vector) dataList2.get(0);
//                    acName = (String) element2.get(0);
//                }
//                glTotal = glCrTotal.subtract(glDrTotal);
//                if (glTotal.compareTo(new BigDecimal("0")) == -1) {
//                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                    generalLedgerBookObject.setACNAME(acName);
//                    generalLedgerBookObject.setAMOUNT(glTotal.abs());
//                    generalLedgerBookObject.setGNO(11);
//                    generalLedgerBookObject.setACTYPE("A");
//                    glbDataList.add(generalLedgerBookObject);
//                } else {
//                    generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                    generalLedgerBookObject.setACNAME(acName);
//                    generalLedgerBookObject.setAMOUNT(glTotal.abs());
//                    generalLedgerBookObject.setGNO(11);
//                    generalLedgerBookObject.setACTYPE("L");
//                    glbDataList.add(generalLedgerBookObject);
//                }
//            }
//
//            dataList1 = em.createNativeQuery("SELECT ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon where " //ifnull is inserted by Ankit for removing nullPointerException
//                    + "substring(acno,3,6)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "0093' and dt<='" + userDate + "' and auth='Y' "
//                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
//            if (dataList1.size() > 0) {
//                Vector element1 = (Vector) dataList1.get(0);
//                crTotal = new BigDecimal(element1.get(0).toString());
//                drTotal = new BigDecimal(element1.get(1).toString());
//            }
//            total = crTotal.subtract(drTotal);
//            grandTotal = total;
//            if (grandTotal.compareTo(new BigDecimal("0")) == -1) {
//                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                generalLedgerBookObject.setACNAME("INTEREST PAYABLE");
//                generalLedgerBookObject.setAMOUNT(grandTotal.abs());
//                generalLedgerBookObject.setGNO(12);
//                generalLedgerBookObject.setACTYPE("A");
//                glbDataList.add(generalLedgerBookObject);
//            } else {
//                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                generalLedgerBookObject.setACNAME("INTEREST PAYABLE");
//                generalLedgerBookObject.setAMOUNT(grandTotal.abs());
//                generalLedgerBookObject.setGNO(12);
//                generalLedgerBookObject.setACTYPE("L");
//                glbDataList.add(generalLedgerBookObject);
//            }
//
//            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                    + "where substring(acno,3,6)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "0092'  AND dt<='" + userDate + "'and auth='Y' "
//                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
//            if (dataList1.size() > 0) {
//                Vector element1 = (Vector) dataList1.get(0);
//                crTotal = new BigDecimal(element1.get(0).toString());
//                drTotal = new BigDecimal(element1.get(1).toString());
//            }
//            total = crTotal.subtract(drTotal);
//            grandTotal = total;
//            if (grandTotal.compareTo(new BigDecimal("0")) == -1) {
//                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                generalLedgerBookObject.setACNAME("SUNDRY CREDITORS");
//                generalLedgerBookObject.setAMOUNT(grandTotal.abs());
//                generalLedgerBookObject.setGNO(12);
//                generalLedgerBookObject.setACTYPE("A");
//                glbDataList.add(generalLedgerBookObject);
//            } else {
//                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                generalLedgerBookObject.setACNAME("SUNDRY CREDITORS");
//                generalLedgerBookObject.setAMOUNT(grandTotal.abs());
//                generalLedgerBookObject.setGNO(12);
//                generalLedgerBookObject.setACTYPE("L");
//                glbDataList.add(generalLedgerBookObject);
//            }
//
//            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                    + "where SUBSTRING(acno,3,6)= '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "0091' AND dt<='" + userDate + "' and auth='Y' "
//                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
//            if (dataList1.size() > 0) {
//                Vector element1 = (Vector) dataList1.get(0);
//                crTotal = new BigDecimal(element1.get(0).toString());
//                drTotal = new BigDecimal(element1.get(1).toString());
//            }
//            total = crTotal.subtract(drTotal);
//            grandTotal = total;
//            if (grandTotal.compareTo(new BigDecimal("0")) == -1) {
//                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                generalLedgerBookObject.setACNAME("SUNDRY DEBTORS");
//                generalLedgerBookObject.setAMOUNT(grandTotal.abs());
//                generalLedgerBookObject.setGNO(12);
//                generalLedgerBookObject.setACTYPE("A");
//                glbDataList.add(generalLedgerBookObject);
//            } else {
//                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                generalLedgerBookObject.setACNAME("SUNDRY DEBTORS");
//                generalLedgerBookObject.setAMOUNT(grandTotal.abs());
//                generalLedgerBookObject.setGNO(12);
//                generalLedgerBookObject.setACTYPE("L");
//                glbDataList.add(generalLedgerBookObject);
//            }
//
//            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                    + "where SUBSTRING(acno,3,6)= '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "0094' AND dt<='" + userDate + "' and auth='Y' "
//                    + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
//            if (dataList1.size() > 0) {
//                Vector element1 = (Vector) dataList1.get(0);
//                crTotal = new BigDecimal(element1.get(0).toString());
//                drTotal = new BigDecimal(element1.get(1).toString());
//            }
//            total = crTotal.subtract(drTotal);
//            grandTotal = total;
//            if (grandTotal.compareTo(new BigDecimal("0")) == -1) {
//                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                generalLedgerBookObject.setACNAME("BILLS RECIEVABLE");
//                generalLedgerBookObject.setAMOUNT(grandTotal.abs());
//                generalLedgerBookObject.setGNO(12);
//                generalLedgerBookObject.setACTYPE("A");
//                glbDataList.add(generalLedgerBookObject);
//            } else {
//                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                generalLedgerBookObject.setACNAME("BILLS RECIEVABLE");
//                generalLedgerBookObject.setAMOUNT(grandTotal.abs());
//                generalLedgerBookObject.setGNO(12);
//                generalLedgerBookObject.setACTYPE("L");
//                glbDataList.add(generalLedgerBookObject);
//            }
//
//            dataList1 = em.createNativeQuery("select min(mindate) from yearend where yearendflag='N' "
//                    + "AND brncode = '" + orgBrnCode + "'").getResultList();
//            if (dataList1.size() > 0) {
//                Vector element1 = (Vector) dataList1.get(0);
//                tempMinDate = (String) element1.get(0);
//            }
//            dataList1 = em.createNativeQuery("SELECT ifnull(MINDATE,'') FROM yearend WHERE "
//                    + "MINDATE <= '" + userDate + "' AND MAXDATE >= '" + userDate + "' "
//                    + "AND brncode = '" + orgBrnCode + "'").getResultList();
//            if (dataList1.size() > 0) {
//                Vector element1 = (Vector) dataList1.get(0);
//                tempMinDate1 = (String) element1.get(0);
//            }
//            if (tempMinDate1.equals("")) {
//                tempMinDate1 = tempMinDate;
//            } else if (tempMinDate1 == null) {
//                tempMinDate1 = tempMinDate;
//            }
//
//            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                    + "where substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "002501' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "003000' AND "
//                    + "dt<='" + userDate + "'  AND dt>='" + tempMinDate1 + "' "
//                    + "and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
//            if (dataList1.size() > 0) {
//                Vector element1 = (Vector) dataList1.get(0);
//                glCrTotal = new BigDecimal(element1.get(0).toString());
//                glDrTotal = new BigDecimal(element1.get(1).toString());
//            }
//            glTotal = glCrTotal.subtract(glDrTotal);
//            incomeTot = glTotal;
//
//            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from gl_recon "
//                    + "where substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "002000' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "002500' AND "
//                    + "dt<='" + userDate + "'  AND dt>='" + tempMinDate1 + "' "
//                    + "and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
//            if (dataList1.size() > 0) {
//                Vector element1 = (Vector) dataList1.get(0);
//                glTotal = new BigDecimal(element1.get(0).toString());
//            }
//            expTot = glTotal;
//
//            if (expTot.compareTo(new BigDecimal("0")) == 1) {
//                profit = incomeTot.add(expTot);
//            } else {
//                profit = incomeTot.subtract(expTot.abs());
//            }
//
//            if (profit.compareTo(new BigDecimal("0")) == -1) {
//                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                generalLedgerBookObject.setACNAME("PROFIT & LOSS");
//                generalLedgerBookObject.setAMOUNT(profit.abs());
//                generalLedgerBookObject.setGNO(12);
//                generalLedgerBookObject.setACTYPE("A");
//                glbDataList.add(generalLedgerBookObject);
//            } else {
//                generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                generalLedgerBookObject.setACNAME("PROFIT & LOSS");
//                generalLedgerBookObject.setAMOUNT(profit.abs());
//                generalLedgerBookObject.setGNO(12);
//                generalLedgerBookObject.setACTYPE("L");
//                glbDataList.add(generalLedgerBookObject);
//            }
//            dataList1 = em.createNativeQuery("select distinct(gl.acno) from gl_recon gl where "
//                    + "substring(acno,3,8) not between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "'+'002000' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "'+'003000' and subString(acno,3,6) "
//                    + "NOT IN ('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "'+'0052','" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "'+'0055',"
//                    + "'" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "'+'0057','" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "'+'0059',"
//                    + "'" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "'+'0091','" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "'+'0092',"
//                    + "'" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "'+'0093','" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "'+'0094') "
//                    + "and substring(acno,3,8) not in (SELECT GLHEAD FROM  accounttypemaster where glhead is not null) "
//                    + "and dt<='" + userDate + "' and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "' order by acno").getResultList();
//            if (dataList1.size() > 0) {
//                for (Object object : dataList1) {
//                    Vector element1 = (Vector) object;
//                    acNo = (String) element1.get(0);
//                    glCrTotal = new BigDecimal("0");
//                    glDrTotal = new BigDecimal("0");
//                    glTotal = new BigDecimal("0");
//
//                    dataList2 = em.createNativeQuery("SELECT ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                            + "where acno='" + acNo + "' AND dt<='" + userDate + "' and auth='Y' ").getResultList();
//                    if (dataList2.size() > 0) {
//                        Vector element2 = (Vector) dataList2.get(0);
//                        glCrTotal = new BigDecimal(element2.get(0).toString());
//                        glDrTotal = new BigDecimal(element2.get(1).toString());
//                    }
//                    glTotal = glCrTotal.subtract(glDrTotal);
//
//                    dataList2 = em.createNativeQuery("SELECT ifnull(acname,'') FROM gltable where acno='" + acNo + "'").getResultList();
//                    if (dataList2.size() > 0) {
//                        Vector element2 = (Vector) dataList2.get(0);
//                        acName = (String) element2.get(0);
//                    }
//
//                    if (glTotal.compareTo(new BigDecimal("0")) == -1) {
//                        generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                        generalLedgerBookObject.setACNAME(acName);
//                        generalLedgerBookObject.setAMOUNT(glTotal.abs());
//                        generalLedgerBookObject.setGNO(12);
//                        generalLedgerBookObject.setACTYPE("A");
//                        glbDataList.add(generalLedgerBookObject);
//                    } else if (glTotal.compareTo(new BigDecimal("0")) == 1) {
//                        generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//                        generalLedgerBookObject.setACNAME(acName);
//                        generalLedgerBookObject.setAMOUNT(glTotal);
//                        generalLedgerBookObject.setGNO(12);
//                        generalLedgerBookObject.setACTYPE("L");
//                        glbDataList.add(generalLedgerBookObject);
//                    }
//                }
//            }
//
//            List<CbsGeneralLedgerBookPojo> glbNewList = new ArrayList<CbsGeneralLedgerBookPojo>();
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACTYPE("L");
//            generalLedgerBookObject.setGNO(9);
//            glbNewList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACTYPE("A");
//            generalLedgerBookObject.setGNO(9);
//            glbNewList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACTYPE("A");
//            generalLedgerBookObject.setGNO(10);
//            glbNewList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACTYPE("L");
//            generalLedgerBookObject.setGNO(11);
//            glbNewList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACTYPE("A");
//            generalLedgerBookObject.setGNO(11);
//            glbNewList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACTYPE("L");
//            generalLedgerBookObject.setGNO(12);
//            glbNewList.add(generalLedgerBookObject);
//
//            generalLedgerBookObject = new CbsGeneralLedgerBookPojo();
//            generalLedgerBookObject.setACTYPE("A");
//            generalLedgerBookObject.setGNO(12);
//            glbNewList.add(generalLedgerBookObject);
//            dataList1 = common.getBranchNameandAddress(orgBrnCode);
//            if (dataList1.size() > 0) {
//                bankName = (String) dataList1.get(0);
//                bankAddress = (String) dataList1.get(1);
//            }
//
//            if (glbDataList.size() > 0) {
//                ComparatorChain chain = new ComparatorChain();
//                chain.addComparator(new SortGeneralLedgerBookByActype());
//                chain.addComparator(new SortGeneralLedgerBookByGno());
//                chain.addComparator(new CbsGeneralLedgerBookByAcname());
//                Collections.sort(glbDataList, chain);
//
//                for (CbsGeneralLedgerBookPojo object : glbDataList) {
//                    object.setBankname(bankName);
//                    object.setBankaddress(bankAddress);
//                }
//            }
//
//        } catch (Exception ex) {
//            throw new ApplicationException(ex);
//        }
//        return glbDataList;
//    }
    public String getTableName(String acctNature) throws ApplicationException {
        String tableName = "";
        try {

            if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                return "recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                return "td_recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                return "rdrecon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                return "loan_recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                return "ca_recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                return "ddstransaction";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.SS_AC) || acctNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                return "loan_recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                return "of_recon";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableName;
    }

    public List<CbsGeneralLedgerBookDebitBalPojo> getDebitBal(String acctCode, String userDate, String orgBrnCode) throws ApplicationException {
        List<CbsGeneralLedgerBookDebitBalPojo> debitBalDataList = new ArrayList<CbsGeneralLedgerBookDebitBalPojo>();
        try {
            double tot = 0, acBal = 0;
            CbsGeneralLedgerBookDebitBalPojo dataObject;
            List dataList = em.createNativeQuery("SELECT SUM(ifnull(r.cramt,0))-SUM(ifnull(r.dramt,0)),r.acno FROM ca_recon  r,"
                    + "accountmaster am WHERE r.dt<='" + userDate + "' AND SUBSTRING(r.acno,3,2) ='" + acctCode + "'  AND r.auth = 'Y' AND "
                    + "r.acno = am.acno and am.CurBrCode = '" + orgBrnCode + "' GROUP BY r.acno").getResultList();
            for (Object object : dataList) {
                Vector element = (Vector) object;
                dataObject = new CbsGeneralLedgerBookDebitBalPojo();
                dataObject.setBal(Double.parseDouble(element.get(0).toString()));
                debitBalDataList.add(dataObject);
            }
            for (CbsGeneralLedgerBookDebitBalPojo object : debitBalDataList) {
                if (object.getBal() > 0) {
                    tot = tot + object.getBal();
                } else if (object.getBal() < 0) {
                    acBal = acBal + object.getBal();
                }
            }
            debitBalDataList = new ArrayList<CbsGeneralLedgerBookDebitBalPojo>();
            dataObject = new CbsGeneralLedgerBookDebitBalPojo();
            dataObject.setAcBal(acBal);
            dataObject.setTot(tot);
            debitBalDataList.add(dataObject);

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return debitBalDataList;
    }

    /**
     * ***** ********
     */
    public List<FinalBalanceReportPojo> getFinalBalanceReport(String status, String tmpAcNat, String fromAccNo, String toAccNo, String userDate, String orgBrnCode, String agentCode, String type, String exCounterInclude) throws ApplicationException {

        List<FinalBalanceReportPojo> finalBalanceReportList = new ArrayList<FinalBalanceReportPojo>();
        List<FinalBalanceReportPojo> finalBalanceReportDataList = new ArrayList<FinalBalanceReportPojo>();
        List dataList1 = new ArrayList();
        List dataList2 = new ArrayList();
        List dataList3 = new ArrayList();
        List dataList4 = new ArrayList();
        NumberFormat formatter = new DecimalFormat("#.##");
        List codeQuery = em.createNativeQuery("select ifnull(code,'COS') from cbs_parameterinfo where name ='SOCIETY_IN'").getResultList();
        String code = "";
        if (!codeQuery.isEmpty()) {
            Vector codeVect = (Vector) codeQuery.get(0);
            code = codeVect.get(0).toString();
        } else {
            code = "'COS','RS'";//For Co-Operative Societies Only
        }
        String typeQuery = "", typeQuery1 = "";
        if (type.equalsIgnoreCase("2")) {
            typeQuery = "and a.acctCategory not in (" + code + ")";
            typeQuery1 = "and b.acctCategory not in (" + code + ")";
        } else if (type.equalsIgnoreCase("3")) {
            typeQuery = "and a.acctCategory in (" + code + ")";
            typeQuery1 = "and b.acctCategory in (" + code + ")";
        }
        String brCodes = "";
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

        try {
            String tmpNat, gTot, bankName = "", bankAddress = "";
            List accNatureList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + tmpAcNat + "'").getResultList();
            Vector vectorAccNature = (Vector) accNatureList.get(0);
            tmpNat = (String) vectorAccNature.get(0);
            if (status.equalsIgnoreCase("PROTESTEDBILL") || status.equalsIgnoreCase("SUITFILED")) {
                if (orgBrnCode.equalsIgnoreCase("0A")) {
                    dataList1 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),"
                            + "sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from loan_recon r,accountmaster a "
                            + "where  a.acno=r.acno and r.dt<='" + userDate + "' and r.auth ='Y' "
                            + "and substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                            + "and  substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                            + "and a.acno in (select acno from loan_appparameter where presentstatus='" + tmpAcNat + "') "
                            + " " + typeQuery + " AND a.acno = r.acno group by r.acno,a.custname").getResultList();
                } else {
                    dataList1 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),"
                            + "sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from loan_recon r,accountmaster a "
                            + "where  a.acno=r.acno and r.dt<='" + userDate + "' and r.auth ='Y' "
                            + "and substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                            + "and  substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                            + "and a.acno in (select acno from loan_appparameter where presentstatus='" + tmpAcNat + "') "
                            + " " + typeQuery + " AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ") group by r.acno,a.custname").getResultList();
                }

                if (dataList1.size() > 0) {
                    for (int i = 0; i < dataList1.size(); i++) {
                        FinalBalanceReportPojo reportObject1 = new FinalBalanceReportPojo();
                        Vector vectorObject1 = (Vector) dataList1.get(i);
                        reportObject1.setAccNo((String) vectorObject1.get(0));
                        reportObject1.setCustName((String) vectorObject1.get(1));
                        reportObject1.setBalance(new BigDecimal(formatter.format(vectorObject1.get(2))));
                        finalBalanceReportList.add(reportObject1);
                    }
                }

                if (orgBrnCode.equalsIgnoreCase("0A")) {
                    dataList2 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),"
                            + "sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from ca_recon r,accountmaster a "
                            + "where  a.acno=r.acno and r.dt<='" + userDate + "' and r.auth ='Y' "
                            + "and substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                            + "and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                            + "(select acno from loan_appparameter where presentstatus='" + tmpAcNat + "') "
                            + " " + typeQuery + " AND a.acno = r.acno group by r.acno,a.custname").getResultList();
                } else {
                    dataList2 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),"
                            + "sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from ca_recon r,accountmaster a "
                            + "where  a.acno=r.acno and r.dt<='" + userDate + "' and r.auth ='Y' "
                            + "and substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                            + "and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                            + "and a.acno in (select acno from loan_appparameter where presentstatus='" + tmpAcNat + "') "
                            + " " + typeQuery + " AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ") group by r.acno,a.custname").getResultList();
                }

                if (dataList2.size() > 0) {
                    for (int i = 0; i < dataList2.size(); i++) {
                        FinalBalanceReportPojo reportObject2 = new FinalBalanceReportPojo();
                        Vector vectorObject2 = (Vector) dataList2.get(i);
                        reportObject2.setAccNo((String) vectorObject2.get(0));
                        reportObject2.setCustName((String) vectorObject2.get(1));
                        reportObject2.setBalance(new BigDecimal(formatter.format(vectorObject2.get(2))));
                        finalBalanceReportList.add(reportObject2);
                    }
                }
            } else {
                if (tmpNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || tmpNat.equalsIgnoreCase(CbsConstant.MS_AC) || tmpNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                    if (tmpNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || tmpNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        if (!status.equalsIgnoreCase("INOPERATIVE")) {

                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select aj.acno,c.custname,"
                                        + "round(sum(ifnull(aj.cramt,0))-sum(ifnull(aj.dramt,0)),2) "
                                        + "from td_recon aj,td_customermaster c,td_accountmaster a "
                                        + "Where a.acno = aj.acno and substring(aj.acno, 5, 6) = c.custno "
                                        + "and upper(substring(aj.acno,3,2))='" + tmpAcNat + "' "
                                        + "and upper(c.actype)='" + tmpAcNat + "' and c.brncode = a.curbrcode and dt<='" + userDate + "' and aj.Trantype<>27 "
                                        + "and aj.closeflag is Null AND aj.auth = 'Y' "
                                        + "and substring(aj.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                        + "and substring(aj.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + " " + typeQuery + " group by aj.acno,c.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select aj.acno,c.custname,"
                                        + "round(sum(ifnull(aj.cramt,0))-sum(ifnull(aj.dramt,0)),2) "
                                        + "from td_recon aj,td_customermaster c,td_accountmaster a "
                                        + "Where a.acno = aj.acno and substring(aj.acno, 5, 6) = c.custno "
                                        + "and c.brncode in(" + brCodes + ") and upper(substring(aj.acno,3,2))='" + tmpAcNat + "' "
                                        + "and upper(c.actype)='" + tmpAcNat + "' and c.brncode = a.curbrcode and dt<='" + userDate + "' and aj.Trantype<>27 "
                                        + "and aj.closeflag is Null AND aj.auth = 'Y' "
                                        + "and substring(aj.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                        + "and substring(aj.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + "AND SUBSTRING(aj.acno,1,2) = '" + orgBrnCode + "' "
                                        + " " + typeQuery + " group by aj.acno,c.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }

                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(aj.cramt,0))-sum(ifnull(aj.dramt,0)),2) "
                                        + "from td_recon aj,td_customermaster c,td_accountmaster a "
                                        + "Where a.acno = aj.acno and substring(aj.acno, 5, 6) = c.custno "
                                        + "and upper(substring(aj.acno,3,2))='" + tmpAcNat + "'  "
                                        + "and upper(c.actype)='" + tmpAcNat + "' and  aj.auth = 'Y' and  "
                                        + "dt<='" + userDate + "' and aj.Trantype<>27 and aj.closeflag is Null  " + typeQuery + "  ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(aj.cramt,0))-sum(ifnull(aj.dramt,0)),2) "
                                        + "from td_recon aj,td_customermaster c,td_accountmaster a "
                                        + "Where a.acno = aj.acno and substring(aj.acno, 5, 6) = c.custno "
                                        + "and c.brncode in(" + brCodes + ") and upper(substring(aj.acno,3,2))='" + tmpAcNat + "'  "
                                        + "and upper(c.actype)='" + tmpAcNat + "' and  aj.auth = 'Y' and  "
                                        + "dt<='" + userDate + "' and aj.Trantype<>27 and aj.closeflag is Null AND "
                                        + "SUBSTRING(aj.acno,1,2)in(" + brCodes + ")  " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    //String acNo = finalBalanceReportList.get(i).getAccNo();
                                    //if (acNo != null) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                    //}
                                }
                            }
                        }
                    } else {
                        if (status.equalsIgnoreCase("OPERATIVE")) {
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select a.acno,b.custname,"
                                        + "round(sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0)),2) "
                                        + "from of_recon a,TD_accountmaster b,td_vouchmst c where "
                                        + "c.ofacno = a.acno and b.acno=c.acno and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "dt<='" + userDate + "'  "
                                        + "and a.auth = 'Y'  " + typeQuery1 + " "
                                        + "group by a.acno,b.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select a.acno,b.custname,"
                                        + "round(sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0)),2) "
                                        + "from of_recon a,TD_accountmaster b,td_vouchmst c where "
                                        + "c.ofacno = a.acno and b.acno=c.acno and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "dt<='" + userDate + "' and a.auth = 'Y'  AND SUBSTRING(a.acno,1,2) in(" + brCodes + ") "
                                        + " " + typeQuery1 + " group by a.acno,b.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0)) "
                                        + "from of_recon a,td_accountmaster b,td_vouchmst c where c.ofacno = a.acno and b.acno=c.acno "
                                        + "and dt<='" + userDate + "'  and a.auth = 'Y'  " + typeQuery1 + " ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0)) "
                                        + "from of_recon a,td_accountmaster b,td_vouchmst c where c.ofacno = a.acno and b.acno=c.acno  " + typeQuery1 + "  "
                                        + "and dt<='" + userDate + "'  and a.auth = 'Y' AND SUBSTRING(a.acno,1,2) in(" + brCodes + ") ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        } else if (status.equalsIgnoreCase("ALL")) {
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select a.acno,b.custname,round(sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0)),2) "
                                        + "from of_recon a,td_accountmaster b,td_vouchmst c "
                                        + "where c.ofacno = a.acno and b.acno=c.acno and dt<='" + userDate + "' "
                                        + "and a.auth = 'Y'  " + typeQuery1 + " "
                                        + "group by a.acno,b.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select a.acno,b.custname,round(sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0)),2) "
                                        + "from of_recon a,td_accountmaster b,td_vouchmst c "
                                        + "where c.ofacno = a.acno and b.acno=c.acno and dt<='" + userDate + "' "
                                        + "and a.auth = 'Y' AND SUBSTRING(a.acno,1,2) in(" + brCodes + ")  " + typeQuery1 + " "
                                        + "group by a.acno,b.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0)) "
                                        + "from of_recon a,td_accountmaster b,td_vouchmst c where "
                                        + "c.ofacno = a.acno and b.acno=c.acno and dt<='" + userDate + "'   " + typeQuery1 + " "
                                        + "and a.auth = 'Y' ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select sum(ifnull(a.cramt,0))-sum(ifnull(a.dramt,0)) "
                                        + "from of_recon a,td_accountmaster b,td_vouchmst c where "
                                        + "c.ofacno = a.acno and b.acno=c.acno and dt<='" + userDate + "'   " + typeQuery1 + " "
                                        + "and a.auth = 'Y' AND SUBSTRING(a.acno,1,2) in(" + brCodes + ")").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        }
                    }
                } else {
                    if (tmpNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        if (status.equalsIgnoreCase("OPERATIVE")) {
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),"
                                        + "round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2)  from recon r,"
                                        + "accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno "
                                        + "and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + "AND a.acno = r.acno  and a.acno not in "
                                        + "(select acno as accno from accountstatus an, ( "
                                        + "select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                        + "where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + "where an.acno = c.anno and an.spno = c.aspno and spflag = 2  and substring(an.acno,3,2)='" + tmpAcNat + "')"
                                        + "" + typeQuery + "  group by r.acno,a.custname").getResultList();
                            } else {
//                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),"
//                                        + "round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2)  from recon r,"
//                                        + "accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno "
//                                        + "and r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                        + "a.optstatus <> 2 AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + "  group by r.acno,a.custname").getResultList();

                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),"
                                        + "round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2)  from recon r,"
                                        + "accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno "
                                        + "and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + "AND a.acno = r.acno and a.acno not in "
                                        + "(select acno as accno from accountstatus an, ( "
                                        + "select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                        + "where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + "where an.acno = c.anno and an.spno = c.aspno and spflag = 2 and substring(an.acno,1,2)='" + brCodes + "' and substring(an.acno,3,2)='" + tmpAcNat + "')"
                                        + "and a.CurBrCode in(" + brCodes + ")  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) from recon r,"
                                        + "accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + userDate + "' and "
                                        + "r.auth ='Y' and a.optstatus <> 2 AND a.acno = r.acno  " + typeQuery + " ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) from recon r,"
                                        + "accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + userDate + "' and "
                                        + "r.auth ='Y' and a.optstatus <> 2 AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        } else if (status.equalsIgnoreCase("INOPERATIVE")) {
//                            if (orgBrnCode.equalsIgnoreCase("0A")) {
//                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                        + "from recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
//                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                        + "a.optstatus = 2 AND a.acno = r.acno group by r.acno,a.custname").getResultList();
//                            } else {
//                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                        + "from recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
//                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                        + "a.optstatus = 2 AND a.acno = r.acno and a.CurBrCode =  '" + orgBrnCode + "' group by r.acno,a.custname").getResultList();
//                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + " from recon r,accountmaster a, (select acno as accno,spflag as sflag from accountstatus an, ( "
                                        + " select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                        + " where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + " where an.acno = c.anno and an.spno = c.aspno) d where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and d.accno = a.acno and d.sflag = 2 and "
                                        + " substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                        + " and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + " /*and a.optstatus = 2*/ AND a.acno = r.acno  " + typeQuery + " group by r.acno,a.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + " from recon r,accountmaster a, (select acno as accno,spflag as sflag from accountstatus an, ( "
                                        + " select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                        + " where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + " where an.acno = c.anno and an.spno = c.aspno) d where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and d.accno = a.acno and d.sflag = 2 and "
                                        + " substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                        + " and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + " /*and a.optstatus = 2 */AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + " group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "a.openingdt <='" + userDate + "' and (ifnull(a.closingdate,'')='' "
                                        + "or a.closingdate>'" + userDate + "') and a.optstatus = 2 AND "
                                        + "a.acno = r.acno  " + typeQuery + " ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "a.openingdt <='" + userDate + "' and (ifnull(a.closingdate,'')='' "
                                        + "or a.closingdate>'" + userDate + "') and a.optstatus = 2 AND "
                                        + "a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        } else {
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.acno = r.acno  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and "
                                        + "r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' AND "
                                        + "a.acno = r.acno ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and "
                                        + "r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' AND "
                                        + "a.acno = r.acno and a.CurBrCode in(" + brCodes + ") " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        }
                    } else if (tmpNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        if (status.equalsIgnoreCase("OPERATIVE")) {
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
//                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                        + "from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
//                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                        + "a.optstatus <> 2 AND a.acno = r.acno  " + typeQuery + "  group by r.acno,a.custname").getResultList();

                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + "AND a.acno = r.acno and a.acno not in "
                                        + "(select acno as accno from accountstatus an, ( "
                                        + "select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus  "
                                        + "where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + "where an.acno = c.anno and an.spno = c.aspno and spflag = 2 and substring(an.acno,3,2)='" + tmpAcNat + "') "
                                        + "" + typeQuery + "  group by r.acno,a.custname").getResultList();

                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.optstatus <> 2 AND a.acno = r.acno and a.acno not in "
                                        + "(select acno as accno from accountstatus an, ( "
                                        + "select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus  "
                                        + "where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + "where an.acno = c.anno and an.spno = c.aspno and spflag = 2 and substring(an.acno,1,2)='" + brCodes + "' and substring(an.acno,3,2)='" + tmpAcNat + "')"
                                        + "and a.CurBrCode in(" + brCodes + ") " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and a.optstatus <> 2 "
                                        + "AND a.acno = r.acno  " + typeQuery + "  ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and a.optstatus <> 2 "
                                        + "AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + "  ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        } else if (status.equalsIgnoreCase("INOPERATIVE")) {
//                            if (orgBrnCode.equalsIgnoreCase("0A")) {
//                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                        + "from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
//                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                        + "a.optstatus = 2 AND a.acno = r.acno group by r.acno,a.custname").getResultList();
//                            } else {
//                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                        + "from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
//                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                        + "a.optstatus = 2 "
//                                        + "AND a.acno = r.acno and a.CurBrCode = '" + orgBrnCode + "' group by r.acno,a.custname").getResultList();
//                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + " from ca_recon r,accountmaster a,(select acno as accno,spflag as sflag from accountstatus an, ("
                                        + " select acno as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                        + " where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + " where an.acno = c.anno and an.spno = c.aspno) d where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno "
                                        + " and r.dt<='" + userDate + "' and r.auth ='Y' and d.accno = a.acno and d.sflag = 2 and "
                                        + " substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                        + " and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + " AND a.acno = r.acno  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + " from ca_recon r,accountmaster a,(select acno as accno,spflag as sflag from accountstatus an, ("
                                        + " select acno as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                        + " where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + " where an.acno = c.anno and an.spno = c.aspno) d where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno "
                                        + " and r.dt<='" + userDate + "' and r.auth ='Y' and d.accno = a.acno and d.sflag = 2 and "
                                        + " substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                        + " and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + " AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "a.openingdt <='" + userDate + "' and (ifnull(a.closingdate,'')='' "
                                        + "or a.closingdate>'" + userDate + "') and a.optstatus = 2 AND "
                                        + "a.acno = r.acno  " + typeQuery + " ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "a.openingdt <='" + userDate + "' and (ifnull(a.closingdate,'')='' "
                                        + "or a.closingdate>'" + userDate + "') and a.optstatus = 2 AND "
                                        + "a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        } else {
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.acno = r.acno  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select ifnull(round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2),0 )"
                                        + "from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and "
                                        + "r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' AND "
                                        + "a.acno = r.acno  " + typeQuery + "  ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and "
                                        + "r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' AND "
                                        + "a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        }
                    } else if (tmpNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || tmpNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || tmpNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                        if (status.equalsIgnoreCase("OPERATIVE")) {
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
//                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                        + "from loan_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
//                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                        + "a.optstatus <> 2 AND a.acno = r.acno  " + typeQuery + "  group by r.acno,a.custname").getResultList();

                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from loan_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + "AND a.acno = r.acno  and a.acno not in "
                                        + "(select acno as accno from accountstatus an, ( "
                                        + "select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                        + "where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + "where an.acno = c.anno and an.spno = c.aspno and spflag = 2 and substring(an.acno,3,2)='" + tmpAcNat + "')"
                                        + "" + typeQuery + "  group by r.acno,a.custname").getResultList();


                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from loan_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + "AND a.acno = r.acno and a.acno not in "
                                        + "(select acno as accno from accountstatus an, ( "
                                        + "select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus  "
                                        + "where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c  "
                                        + "where an.acno = c.anno and an.spno = c.aspno and spflag = 2 and substring(an.acno,3,2)='" + tmpAcNat + "')"
                                        + "and a.CurBrCode in(" + brCodes + ") " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from loan_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and a.optstatus <> 2 "
                                        + "AND a.acno = r.acno  " + typeQuery + " ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from loan_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and a.optstatus <> 2 "
                                        + "AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ") " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        } else if (status.equalsIgnoreCase("INOPERATIVE")) {
//                            if (orgBrnCode.equalsIgnoreCase("0A")) {
//                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                        + "from loan_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
//                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                        + "a.optstatus = 2 AND a.acno = r.acno group by r.acno,a.custname").getResultList();
//                            } else {
//                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                        + "from loan_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
//                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                        + "a.optstatus = 2 AND a.acno = r.acno and a.CurBrCode = '" + orgBrnCode + "' group by r.acno,a.custname").getResultList();
//                            }

                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + " from loan_recon r,accountmaster a, (select acno as accno,spflag as sflag from accountstatus an, ("
                                        + " select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                        + " where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + " where an.acno = c.anno and an.spno = c.aspno) d where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno "
                                        + " and r.dt<='" + userDate + "' and r.auth ='Y' and d.accno = a.acno and d.sflag = 2 and "
                                        + " substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                        + " and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + " /*and a.optstatus = 2*/ AND a.acno = r.acno " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + " from loan_recon r,accountmaster a, (select acno as accno,spflag as sflag from accountstatus an, ("
                                        + " select acno as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                        + " where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + " where an.acno = c.anno and an.spno = c.aspno) d where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno "
                                        + " and r.dt<='" + userDate + "' and r.auth ='Y' and d.accno = a.acno and d.sflag = 2 and "
                                        + " substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                        + " and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + " /*and a.optstatus = 2 */AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from loan_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "a.openingdt <='" + userDate + "' and (ifnull(a.closingdate,'')='' "
                                        + "or a.closingdate>'" + userDate + "') and a.optstatus = 2 AND "
                                        + "a.acno = r.acno  " + typeQuery + " ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from loan_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "a.openingdt <='" + userDate + "' and (ifnull(a.closingdate,'')='' "
                                        + "or a.closingdate>'" + userDate + "') and a.optstatus = 2 AND "
                                        + "a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        } else {
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from loan_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.acno = r.acno  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from loan_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from loan_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and "
                                        + "r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' AND "
                                        + "a.acno = r.acno  " + typeQuery + "  ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from loan_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and "
                                        + "r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' AND "
                                        + "a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        }
                    } else if (tmpNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        if (status.equalsIgnoreCase("OPERATIVE")) {
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from gl_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.optstatus <> 2  " + typeQuery + " group by r.acno,a.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from gl_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.optstatus <> 2  " + typeQuery + "  AND SUBSTRING(r.acno,1,2) in(" + brCodes + ") group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from gl_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and a.optstatus <> 2  " + typeQuery + " ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from gl_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and a.optstatus <> 2 "
                                        + "AND SUBSTRING(r.acno,1,2) in(" + brCodes + ")  " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        } else if (status.equalsIgnoreCase("INOPERATIVE")) {
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from gl_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' "
                                        + "and substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.optstatus = 2  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from gl_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.optstatus = 2 AND SUBSTRING(r.acno,1,2) in(" + brCodes + ") " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }

                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from gl_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "a.openingdt <='" + userDate + "' and (ifnull(a.closingdate,'')='' "
                                        + "or a.closingdate>'" + userDate + "') and a.optstatus = 2  " + typeQuery + " ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from gl_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "a.openingdt <='" + userDate + "' and (ifnull(a.closingdate,'')='' "
                                        + "or a.closingdate>'" + userDate + "') and a.optstatus = 2 AND "
                                        + "SUBSTRING(r.acno,1,2) in(" + brCodes + ")  " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        } else {
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from gl_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + "  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from gl_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "SUBSTRING(r.acno,1,2) in(" + brCodes + ")  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from gl_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and "
                                        + "r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y'").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from gl_recon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and "
                                        + "r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' AND "
                                        + "SUBSTRING(r.acno,1,2) in(" + brCodes + ") ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        }
                    } else if (tmpNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        if (status.equalsIgnoreCase("OPERATIVE")) {
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
//                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                        + "from rdrecon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
//                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                        + "a.optstatus <> 2 AND a.acno = r.acno  " + typeQuery + " group by r.acno,a.custname").getResultList();

                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from rdrecon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.optstatus <> 2 AND a.acno = r.acno and a.acno not in "
                                        + "(select acno as accno from accountstatus an, ( "
                                        + "select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                        + "where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + "where an.acno = c.anno and an.spno = c.aspno and spflag = 2 and substring(an.acno,3,2)='" + tmpAcNat + "')"
                                        + "" + typeQuery + " group by r.acno,a.custname").getResultList();

                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from rdrecon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + "AND a.acno = r.acno and a.acno not in "
                                        + "(select acno as accno from accountstatus an, ( "
                                        + "select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                        + "where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + "where an.acno = c.anno and an.spno = c.aspno and spflag = 2 and substring(an.acno,1,2)='" + brCodes + "' and substring(an.acno,3,2)='" + tmpAcNat + "')"
                                        + "and a.CurBrCode in(" + brCodes + ")  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from rdrecon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and a.optstatus <> 2 "
                                        + "AND a.acno = r.acno ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from rdrecon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and a.optstatus <> 2 "
                                        + "AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ")").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        } else if (status.equalsIgnoreCase("INOPERATIVE")) {
//                            if (orgBrnCode.equalsIgnoreCase("0A")) {
//                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                        + "from rdrecon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
//                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                        + "a.optstatus = 2 AND a.acno = r.acno group by r.acno,a.custname").getResultList();
//                            } else {
//                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                        + "from rdrecon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
//                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                        + "a.optstatus = 2 AND a.acno = r.acno and a.CurBrCode = '" + orgBrnCode + "' group by r.acno,a.custname").getResultList();
//                            }

                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + " from rdrecon r,accountmaster a, (select acno as accno,spflag as sflag from accountstatus an, ("
                                        + " select acno as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                        + " where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + " where an.acno = c.anno and an.spno = c.aspno) d where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno "
                                        + " and r.dt<='" + userDate + "' and r.auth ='Y' and d.accno = a.acno and d.sflag = 2 and "
                                        + " substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                        + " and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + " /*and a.optstatus = 2 */AND a.acno = r.acno " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + " from rdrecon r,accountmaster a, (select acno as accno,spflag as sflag from accountstatus an, ("
                                        + " select acno as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                        + " where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                        + " where an.acno = c.anno and an.spno = c.aspno) d where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno "
                                        + " and r.dt<='" + userDate + "' and r.auth ='Y' and d.accno = a.acno and d.sflag = 2 and "
                                        + " substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                        + " and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                        + " /*and a.optstatus = 2 */AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ") " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from rdrecon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "a.openingdt <='" + userDate + "' and (ifnull(a.closingdate,'')='' "
                                        + "or a.closingdate>'" + userDate + "') and a.optstatus = 2 AND "
                                        + "a.acno = r.acno ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from rdrecon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "a.openingdt <='" + userDate + "' and (ifnull(a.closingdate,'')='' "
                                        + "or a.closingdate>'" + userDate + "') and a.optstatus = 2 AND "
                                        + "a.acno = r.acno and a.CurBrCode in(" + brCodes + ") ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        } else {
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from rdrecon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.acno = r.acno  " + typeQuery + "  group by r.acno,a.custname").getResultList();
                            } else {
                                dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from rdrecon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' "
                                        + "and r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' and "
                                        + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                        + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                        + "a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + " group by r.acno,a.custname").getResultList();
                            }

                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from rdrecon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and "
                                        + "r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' AND "
                                        + "a.acno = r.acno  " + typeQuery + "").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                        + "from rdrecon r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and "
                                        + "r.acno=a.acno and r.dt<='" + userDate + "' and r.auth ='Y' AND "
                                        + "a.acno = r.acno and a.CurBrCode in(" + brCodes + ") " + typeQuery + "").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        }
                    } else if (tmpNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        if (status.equalsIgnoreCase("OPERATIVE")) {
                            // List dataList3;
                            if (agentCode.equals("0")) {
                                if (orgBrnCode.equalsIgnoreCase("0A")) {
//                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
//                                            + "r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                            + "a.optstatus <> 2 AND a.acno = r.acno " + typeQuery + " group by r.acno,a.custname").getResultList();

                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                            + "r.dt<='" + userDate + "' and r.auth ='Y' and "
                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                            + "a.acno = r.acno and a.acno not in "
                                            + "(select acno as accno from accountstatus an, ( "
                                            + "select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                            + "where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                            + "where an.acno = c.anno and an.spno = c.aspno and spflag = 2 and substring(an.acno,3,2)='" + tmpAcNat + "')"
                                            + typeQuery + " group by r.acno,a.custname").getResultList();


                                } else {
                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                            + "r.dt<='" + userDate + "' and r.auth ='Y' and "
                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                            + "AND a.acno = r.acno and a.acno not in "
                                            + "(select acno as accno from accountstatus an, ( "
                                            + "select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                            + "where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c  "
                                            + "where an.acno = c.anno and an.spno = c.aspno and spflag = 2 and substring(an.acno,1,2)='" + brCodes + "' and substring(an.acno,3,2)='" + tmpAcNat + "')"
                                            + "and a.CurBrCode in(" + brCodes + ")  " + typeQuery + " group by r.acno,a.custname").getResultList();
                                }

                            } else {
                                if (orgBrnCode.equalsIgnoreCase("0A")) {
//                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
//                                            + "r.dt<='" + userDate + "' and r.auth ='Y' and "
//                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                            + "a.optstatus <> 2 AND a.acno = r.acno and substring(a.acno,11,2)='" + agentCode + "'  " + typeQuery + " group by r.acno,a.custname").getResultList();

                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                            + "r.dt<='" + userDate + "' and r.auth ='Y' and "
                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                            + "AND a.acno = r.acno and a.acno not in "
                                            + "(select acno as accno from accountstatus an, ( "
                                            + "select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                            + "where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                            + "where an.acno = c.anno and an.spno = c.aspno and spflag = 2 and substring(an.acno,3,2)='" + tmpAcNat + "')"
                                            + "and substring(a.acno,11,2)='" + agentCode + "'  " + typeQuery + " group by r.acno,a.custname").getResultList();
                                } else {
                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                            + "r.dt<='" + userDate + "' and r.auth ='Y' and "
                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                            + "AND a.acno = r.acno and a.acno not in "
                                            + "(select acno as accno from accountstatus an, ( "
                                            + "select acno  as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                            + "where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                            + "where an.acno = c.anno and an.spno = c.aspno and spflag = 2 and substring(an.acno,1,2)='"+brCodes+"' and substring(an.acno,3,2)='"+tmpAcNat+"') and "
                                            + "a.CurBrCode in(" + brCodes + ") and substring(a.acno,11,2)='" + agentCode + "'  " + typeQuery + " group by r.acno,a.custname").getResultList();
                                }
                            }
                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) from ddstransaction r, "
                                        + "accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + userDate
                                        + "' and r.auth ='Y' and a.optstatus <> 2 AND a.acno = r.acno  " + typeQuery + " ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) from ddstransaction r, "
                                        + "accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + userDate
                                        + "' and r.auth ='Y' and a.optstatus <> 2 AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        } else if (status.equalsIgnoreCase("INOPERATIVE")) {
                            // List dataList3;
                            if (agentCode.equals("0")) {
//                                if (orgBrnCode.equalsIgnoreCase("0A")) {
//                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='"
//                                            + userDate + "' and r.auth ='Y' and "
//                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                            + "a.optstatus = 2 AND a.acno = r.acno "
//                                            + " group by r.acno,a.custname").getResultList();
//                                } else {
//                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='"
//                                            + userDate + "' and r.auth ='Y' and "
//                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                            + "a.optstatus = 2 AND a.acno = r.acno "
//                                            + "and a.CurBrCode = '" + orgBrnCode + "' group by r.acno,a.custname").getResultList();
//                                }
                                if (orgBrnCode.equalsIgnoreCase("0A")) {
                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                            + " from ddstransaction r,accountmaster a, (select acno as accno,spflag as sflag from accountstatus an, ("
                                            + " select acno as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                            + " where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                            + " where an.acno = c.anno and an.spno = c.aspno) d where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno "
                                            + " and r.dt<='" + userDate + "' and r.auth ='Y' and d.accno = a.acno and d.sflag = 2 and "
                                            + " substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                            + " and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                            + " /*and a.optstatus = 2*/ AND a.acno = r.acno  " + typeQuery + "group by r.acno,a.custname").getResultList();
                                } else {
                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                            + " from ddstransaction r,accountmaster a, (select acno as accno,spflag as sflag from accountstatus an, ("
                                            + " select acno as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                            + " where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                            + " where an.acno = c.anno and an.spno = c.aspno) d where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno "
                                            + " and r.dt<='" + userDate + "' and r.auth ='Y' and d.accno = a.acno and d.sflag = 2 and "
                                            + " substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                            + " and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                            + " /*and a.optstatus = 2*/ AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ") " + typeQuery + " group by r.acno,a.custname").getResultList();
                                }

                            } else {
//                                if (orgBrnCode.equalsIgnoreCase("0A")) {
//                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='"
//                                            + userDate + "' and r.auth ='Y' and "
//                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                            + "a.optstatus = 2 and substring(a.acno,11,2)='" + agentCode + "'"
//                                            + "AND a.acno = r.acno group by r.acno,a.custname").getResultList();
//                                } else {
//                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
//                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='"
//                                            + userDate + "' and r.auth ='Y' and "
//                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
//                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
//                                            + "a.optstatus = 2 and substring(a.acno,11,2)='" + agentCode + "'"
//                                            + "AND a.acno = r.acno and a.CurBrCode = '" + orgBrnCode + "' group by r.acno,a.custname").getResultList();
//                                }
                                if (orgBrnCode.equalsIgnoreCase("0A")) {
                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                            + " from ddstransaction r,accountmaster a, (select acno as accno,spflag as sflag from accountstatus an, ("
                                            + " select acno as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                            + " where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                            + " where an.acno = c.anno and an.spno = c.aspno) d where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno "
                                            + " and r.dt<='" + userDate + "' and r.auth ='Y' and d.accno = a.acno and d.sflag = 2 and "
                                            + " substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                            + " and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                            + " and a.optstatus = 2 and substring(a.acno,11,2)='" + agentCode + "' AND a.acno = r.acno  " + typeQuery + ""
                                            + " group by r.acno,a.custname").getResultList();
                                } else {
                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                            + " from ddstransaction r,accountmaster a, (select acno as accno,spflag as sflag from accountstatus an, ("
                                            + " select acno as anno, max(spno) aspno from accountstatus a, (select acno as ano,max(dt) as adt from accountstatus "
                                            + " where dt <='" + userDate + "' group by acno) b where a.acno = b.ano and a.dt = b.adt group by acno) c "
                                            + " where an.acno = c.anno and an.spno = c.aspno) d where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno "
                                            + " and r.dt<='" + userDate + "' and r.auth ='Y' and d.accno = a.acno and d.sflag = 2 and "
                                            + " substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' "
                                            + " and substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' "
                                            + " and a.optstatus = 2 and substring(a.acno,11,2)='" + agentCode + "' AND a.acno = r.acno  " + typeQuery + ""
                                            + " and a.CurBrCode in(" + brCodes + ") group by r.acno,a.custname").getResultList();
                                }

                            }
                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) from ddstransaction r,"
                                        + "accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + userDate + "' "
                                        + "and r.auth ='Y' and a.openingdt <='" + userDate + "' and (ifnull(a.closingdate,'')='' or a.closingdate>'" + userDate + "') "
                                        + "and a.optstatus = 2 AND a.acno = r.acno  " + typeQuery + " ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) from ddstransaction r,"
                                        + "accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + userDate + "' "
                                        + "and r.auth ='Y' and a.openingdt <='" + userDate + "' and (ifnull(a.closingdate,'')='' or a.closingdate>'" + userDate + "') "
                                        + "and a.optstatus = 2 AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        } else {
                            // List dataList3;
                            if (agentCode.equals("0")) {
                                if (orgBrnCode.equalsIgnoreCase("0A")) {
                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                            + "r.dt<='" + userDate + "' and r.auth ='Y' and "
                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                            + "a.acno = r.acno  " + typeQuery + " "
                                            + " group by r.acno,a.custname").getResultList();
                                } else {
                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                            + "r.dt<='" + userDate + "' and r.auth ='Y' and "
                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                            + "a.acno = r.acno   " + typeQuery + " "
                                            + "and a.CurBrCode in(" + brCodes + ") group by r.acno,a.custname").getResultList();
                                }

                            } else {
                                if (orgBrnCode.equalsIgnoreCase("0A")) {
                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                            + "r.dt<='" + userDate + "' and r.auth ='Y' and "
                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                            + "a.acno = r.acno and substring(a.acno,11,2)='" + agentCode + "'"
                                            + " group by r.acno,a.custname").getResultList();
                                } else {
                                    dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,40),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) "
                                            + "from ddstransaction r,accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                            + "r.dt<='" + userDate + "' and r.auth ='Y' and "
                                            + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                            + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                            + "a.acno = r.acno and substring(a.acno,11,2)='" + agentCode + "'  " + typeQuery + " "
                                            + "and a.CurBrCode in(" + brCodes + ") group by r.acno,a.custname").getResultList();
                                }
                            }
                            if (dataList3.size() > 0) {
                                for (int i = 0; i < dataList3.size(); i++) {
                                    FinalBalanceReportPojo reportObject3 = new FinalBalanceReportPojo();
                                    Vector vectorObject3 = (Vector) dataList3.get(i);
                                    reportObject3.setAccNo((String) vectorObject3.get(0));
                                    reportObject3.setCustName((String) vectorObject3.get(1));
                                    reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                                    finalBalanceReportList.add(reportObject3);
                                }
                            }
                            if (orgBrnCode.equalsIgnoreCase("0A")) {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) from ddstransaction r,"
                                        + "accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + userDate
                                        + "' and r.auth ='Y' AND a.acno = r.acno  " + typeQuery + " ").getResultList();
                            } else {
                                dataList4 = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) from ddstransaction r,"
                                        + "accountmaster a where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + userDate
                                        + "' and r.auth ='Y' AND a.acno = r.acno and a.CurBrCode in(" + brCodes + ")  " + typeQuery + " ").getResultList();
                            }

                            if (dataList4.size() > 0) {
                                Vector vectorObject4 = (Vector) dataList4.get(0);
                                gTot = formatter.format(vectorObject4.get(0));
                                for (int i = 0; i < finalBalanceReportList.size(); i++) {
                                    finalBalanceReportList.get(i).setGrandTotal(new BigDecimal(gTot));
                                }
                            }
                        }
                    }
                }
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

            if (finalBalanceReportList.size() > 0) {
                SortedFinalBalanceReport orderByAccNo = new SortedFinalBalanceReport();
                Collections.sort(finalBalanceReportList, orderByAccNo);

                for (FinalBalanceReportPojo object : finalBalanceReportList) {
                    if (Double.parseDouble(object.getBalance().toString()) == 0) {
                        continue;
                    } else {
                        if (object.getBalance().doubleValue() < 0) {
                            object.setCrDrFlag("0");
                        } else {
                            object.setCrDrFlag("1");
                        }
                        object.setBankName(bankName);
                        object.setBankAddress(bankAddress);
                        finalBalanceReportDataList.add(object);
                    }
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return finalBalanceReportDataList;

    }

    /**
     * *
     */
    public List<ProfitAndLossPojo> getProfitAndLossDetails(String userDate, String orgBrnCode, String selectOption) throws ApplicationException {
        List<ProfitAndLossPojo> dataList = new ArrayList<ProfitAndLossPojo>();
        String bankName = "", bankAddress = "";
        try {
            NumberFormat formatter = new DecimalFormat("#.##");
            String minDate = null, maxDate, accountNo;
            double plAmt = 0;
            List yearEndList = em.createNativeQuery("SELECT mindate,maxdate from yearend where mindate<='" + userDate + "' and maxdate>='" + userDate + "'").getResultList();
            if (yearEndList.size() > 0) {
                Vector vectorYearEnd = (Vector) yearEndList.get(0);
                minDate = (String) vectorYearEnd.get(0);
                maxDate = (String) vectorYearEnd.get(1);
            }
            if (minDate != null) {
                List profitLossList1 = new ArrayList();
                if (selectOption.equalsIgnoreCase("0")) {
                    profitLossList1 = em.createNativeQuery("SELECT r.acno,acname,SUM(ifnull(cramt,0))-SUM(ifnull(dramt,0)) "
                            + "FROM gl_recon r,gltable g WHERE SUBSTRING(r.acno,5,6)>='" + SiplConstant.GL_INCOME_ST.getValue() + "' AND SUBSTRING(r.acno,5,6)<='" + SiplConstant.GL_INCOME_END.getValue() + "' "
                            + "AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND r.acno=g.acno AND auth='Y' "
                            + "AND r.trantype <> 27 AND r.trandesc<>13 AND Substring(r.acno,1,2) = '" + orgBrnCode + "' "
                            + "GROUP BY r.acno,acname ORDER BY r.acno").getResultList();
                } else {
                    profitLossList1 = em.createNativeQuery("SELECT r.acno,acname,SUM(ifnull(cramt,0))-SUM(ifnull(dramt,0)) "
                            + "FROM gl_recon r,gltable g WHERE SUBSTRING(r.acno,5,6)>='" + SiplConstant.GL_INCOME_ST.getValue() + "' AND SUBSTRING(r.acno,5,6)<='" + SiplConstant.GL_INCOME_END.getValue() + "' "
                            + "AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND r.acno=g.acno AND auth='Y' "
                            + "AND r.trantype <> 27 AND Substring(r.acno,1,2) = '" + orgBrnCode + "' "
                            + "GROUP BY r.acno,acname ORDER BY r.acno").getResultList();
                }
                if (profitLossList1.size() > 0) {

                    for (int i = 0; i < profitLossList1.size(); i++) {
                        Vector vectorProfitLoss = (Vector) profitLossList1.get(i);
                        ProfitAndLossPojo profitLossObject = new ProfitAndLossPojo();
                        profitLossObject.setAcno((String) vectorProfitLoss.get(0));
                        profitLossObject.setAcName((String) vectorProfitLoss.get(1));
                        Double doubleValueOfAmount = (Double) vectorProfitLoss.get(2);
                        profitLossObject.setAmount(new BigDecimal(formatter.format(doubleValueOfAmount)));
                        profitLossObject.setType("I");
                        dataList.add(profitLossObject);
                    }

                }
                List profitLossList2 = new ArrayList();
                if (selectOption.equalsIgnoreCase("0")) {
                    profitLossList2 = em.createNativeQuery("SELECT r.acno,acname,SUM(ifnull(cramt,0))-SUM(ifnull(dramt,0)) "
                            + "FROM gl_recon r,gltable g WHERE SUBSTRING(r.acno,5,6)>='" + SiplConstant.GL_EXP_ST.getValue() + "' AND SUBSTRING(r.acno,5,6)<='" + SiplConstant.GL_EXP_END.getValue() + "' "
                            + "AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND r.acno=g.acno AND auth='Y' "
                            + "AND r.trantype <> 27 AND r.trandesc<>13 AND Substring(r.acno,1,2) = '" + orgBrnCode + "' "
                            + "GROUP BY r.acno,acname ORDER BY r.acno").getResultList();
                } else {
                    profitLossList2 = em.createNativeQuery("SELECT r.acno,acname,SUM(ifnull(cramt,0))-SUM(ifnull(dramt,0)) "
                            + "FROM gl_recon r,gltable g WHERE SUBSTRING(r.acno,5,6)>='" + SiplConstant.GL_EXP_ST.getValue() + "' AND SUBSTRING(r.acno,5,6)<='" + SiplConstant.GL_EXP_END.getValue() + "' "
                            + "AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND r.acno=g.acno AND auth='Y' "
                            + "AND r.trantype <> 27 AND Substring(r.acno,1,2) = '" + orgBrnCode + "' "
                            + "GROUP BY r.acno,acname ORDER BY r.acno").getResultList();
                }
                if (profitLossList2.size() > 0) {

                    for (int i = 0; i < profitLossList2.size(); i++) {
                        Vector vectorProfitLoss2 = (Vector) profitLossList2.get(i);
                        ProfitAndLossPojo profitLossObject = new ProfitAndLossPojo();
                        profitLossObject.setAcno((String) vectorProfitLoss2.get(0));
                        profitLossObject.setAcName((String) vectorProfitLoss2.get(1));
                        Double doubleValueOfAmount = (Double) vectorProfitLoss2.get(2);
                        profitLossObject.setAmount(new BigDecimal(formatter.format(doubleValueOfAmount)));
                        profitLossObject.setType("E");
                        dataList.add(profitLossObject);
                    }

                }
                List profitLossList3 = new ArrayList();
                if (selectOption.equalsIgnoreCase("0")) {
                    profitLossList3 = em.createNativeQuery("SELECT coalesce((SUM(ifnull(cramt,0))-SUM(ifnull(dramt,0))),0) FROM gl_recon "
                            + "WHERE SUBSTRING(acno,5,6)>='" + SiplConstant.GL_PL_ST.getValue() + "' AND SUBSTRING(acno,5,6)<='" + SiplConstant.GL_PL_END.getValue() + "' AND dt>='" + minDate + "' AND dt<='" + userDate
                            + "' AND auth='Y' AND trantype <> 27 AND trandesc<>13 AND Substring(acno,1,2) = '" + orgBrnCode + "'").getResultList();
                } else {
                    profitLossList3 = em.createNativeQuery("SELECT coalesce((SUM(ifnull(cramt,0))-SUM(ifnull(dramt,0))),0) FROM gl_recon WHERE "
                            + "SUBSTRING(acno,5,6)>='" + SiplConstant.GL_PL_ST.getValue() + "' AND SUBSTRING(acno,5,6)<='" + SiplConstant.GL_PL_END.getValue() + "' AND dt>='" + minDate + "' AND dt<='" + userDate
                            + "' AND auth='Y' AND trantype <> 27 AND Substring(acno,1,2) = '" + orgBrnCode + "'").getResultList();
                }
                if (profitLossList3.size() > 0) {
                    Vector vectorPLAmount = (Vector) profitLossList3.get(0);
                    plAmt = (Double) vectorPLAmount.get(0);
                }
                if (plAmt != 0) {
                    if (plAmt < 0) {
                        ProfitAndLossPojo profitLossObject = new ProfitAndLossPojo();
                        profitLossObject.setAcno("");
                        profitLossObject.setAcName("LOSS");
                        profitLossObject.setAmount(new BigDecimal(formatter.format(Math.abs(plAmt))));
                        profitLossObject.setType("I");
                        dataList.add(profitLossObject);
                    } else if (plAmt > 0) {
                        ProfitAndLossPojo profitLossObject = new ProfitAndLossPojo();
                        profitLossObject.setAcno("");
                        profitLossObject.setAcName("PROFIT");
                        profitLossObject.setAmount(new BigDecimal(formatter.format(plAmt)));
                        profitLossObject.setType("E");
                        dataList.add(profitLossObject);
                    }
                }
            }

            List dataList1 = common.getBranchNameandAddress(orgBrnCode);
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            if (dataList.size() > 0) {
                Collections.sort(dataList, new SortProfitLossByType());

                for (ProfitAndLossPojo object : dataList) {
                    object.setBankName(bankName);
                    object.setBankAddress(bankAddress);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return dataList;
    }

    public List<TrialBalancePojo> getNewTrialBalanceReport(String userDate, String orgBrnCode, String exCounterInclude) throws ApplicationException {
        List<TrialBalancePojo> trialBalanceDataList = new ArrayList<>();
        try {
            String brCodes = "";
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
            List<GlHeadPojo> osBlancelist = getAsOnDateBalanceListForTrail(brCodes, userDate);
            List<GlHeadPojo> incomeExpList = getIncomeExpListForTrail(brCodes, userDate);
            String brnCode = orgBrnCode.equals("0A") ? "90" : orgBrnCode;
            List bankList = common.getBranchNameandAddress(brnCode);
            if (bankList.isEmpty()) {
                throw new ApplicationException("Bank Name does not defined");
            }

            String bankName = (String) bankList.get(0);
            String bankAddress = (String) bankList.get(1);

            List dataList = em.createNativeQuery("SELECT DISTINCT(substring(acno,3,8)),acname,'' as acctcode FROM gltable where SUBSTRING(acno,3,8) "
                    + "NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_ST.getValue() + "'AND '"
                    + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "' AND SUBSTRING(acno,3,8) "
                    + "NOT IN (SELECT GLHEAD FROM  accounttypemaster WHERE glhead IS NOT NULL) "
                    + "union all "
                    + "SELECT glhead,acctdesc,acctcode FROM accounttypemaster where acctcode<>'" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "'").getResultList();
            TrialBalancePojo object;
            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                object = new TrialBalancePojo();

                String accNo = element.get(0).toString();
                String acctCode = element.get(2).toString();

                BigDecimal balance = getOSBalance(osBlancelist, accNo);
                if (!acctCode.equals("")) {
                    balance = balance.add(getOSBalance(osBlancelist, acctCode));
                }

                if (balance.compareTo(BigDecimal.ZERO) != 0) {
                    object.setAcName(element.get(1).toString());
                    object.setBankName(bankName);
                    object.setBankAddress(bankAddress);

                    if (balance.compareTo(BigDecimal.ZERO) < 0) {
                        object.setCrBalance(new BigDecimal("0"));
                        object.setDrBalance(balance.abs());
                    } else if (balance.compareTo(BigDecimal.ZERO) > 0) {
                        object.setCrBalance(balance.abs());
                        object.setDrBalance(new BigDecimal("0"));
                    }
                    trialBalanceDataList.add(object);
                }
            }
            BigDecimal income = new BigDecimal(BigInteger.ZERO);
            BigDecimal exp = new BigDecimal(BigInteger.ZERO);
            for (GlHeadPojo pojo : incomeExpList) {
                if (Long.parseLong(pojo.getGlHead()) >= Long.parseLong(SiplConstant.GL_INCOME_ST.getValue())
                        && Long.parseLong(pojo.getGlHead()) <= Long.parseLong(SiplConstant.GL_INCOME_END.getValue())) {
                    income = income.add(pojo.getBalance());
                }
                if (Long.parseLong(pojo.getGlHead()) >= Long.parseLong(SiplConstant.GL_EXP_ST.getValue())
                        && Long.parseLong(pojo.getGlHead()) <= Long.parseLong(SiplConstant.GL_EXP_END.getValue())) {
                    exp = exp.add(pojo.getBalance());
                }
            }
            if (trialBalanceDataList.size() > 0) {
                SortedTrialBalanceReport sortByAcName = new SortedTrialBalanceReport();
                Collections.sort(trialBalanceDataList, sortByAcName);
            }
            object = new TrialBalancePojo();
            object.setAcName("INCOME");
            object.setBankName(bankName);
            object.setBankAddress(bankAddress);

            if (income.compareTo(BigDecimal.ZERO) < 0) {
                object.setCrBalance(new BigDecimal("0"));
                object.setDrBalance(income.abs());

            } else if (income.compareTo(BigDecimal.ZERO) > 0) {
                object.setCrBalance(income.abs());
                object.setDrBalance(new BigDecimal("0"));
            }
            trialBalanceDataList.add(object);
            object = new TrialBalancePojo();
            object.setAcName("EXPENDITURE");
            object.setBankName(bankName);
            object.setBankAddress(bankAddress);

            if (exp.compareTo(BigDecimal.ZERO) < 0) {
                object.setCrBalance(new BigDecimal("0"));
                object.setDrBalance(exp.abs());

            } else if (exp.compareTo(BigDecimal.ZERO) > 0) {
                object.setCrBalance(exp.abs());
                object.setDrBalance(new BigDecimal("0"));
            }
            trialBalanceDataList.add(object);


            return trialBalanceDataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<GlHeadPojo> getIncomeExpListForTrail(String brCode, String date) throws ApplicationException {
        try {
            String brCodeQuery = "";
            String brnCode = brCode.equals("0A") ? "90" : brCode;
            if (!brCode.equals("0A")) {
                if (brCode.contains(",")) {
                    brCodeQuery = " and brncode in(" + brCode + ")";
                } else {
                    brCodeQuery = " and brncode = '" + brCode + "'";
                }
            } else {
                brCodeQuery = " and brncode = '" + brnCode + "'";
            }

            List dataList = em.createNativeQuery("select ifnull(mindate,'') from yearend where mindate <= '" + date + "' and maxdate >= '"
                    + date + "' " + brCodeQuery + "").getResultList();
            String minDt = "";
            if (dataList.size() > 0) {
                Vector element1 = (Vector) dataList.get(0);
                minDt = (String) (element1.get(0) != null ? element1.get(0) : "");
            }
            String query = CbsUtil.getBrWisePlQuery(brCode, minDt, date);
            System.out.println(query);
            List glResultList = em.createNativeQuery(query).getResultList();
            List<GlHeadPojo> balanceList = new ArrayList<>();
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

    public List<GlHeadPojo> getAsOnDateBalanceListForTrail(String brCode, String date) throws ApplicationException {
        try {
            String query = CbsUtil.getBranchWiseQueryForTrail(brCode, date);
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

    public BigDecimal getOSBalance(List<GlHeadPojo> balanceList, String acno) throws ApplicationException {
        try {
            for (GlHeadPojo pojo : balanceList) {
                if (pojo.getGlHead().equals(acno)) {
                    return pojo.getBalance();
                }
            }
            return new BigDecimal(BigInteger.ZERO);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * * **
     */
    public List<TrialBalancePojo> getTrialBalanceReportData(String userDate, String orgBrnCode) throws ApplicationException {

        NumberFormat formatter = new DecimalFormat("#.##");
        List<TrialBalancePojo> trialBalanceDataList = new ArrayList<TrialBalancePojo>();
        try {
            String accName, accNo, tempMinDate, acctDesc, glAccno, maxDate, bankName = "", bankAddress = "";
            double glTotal, acTotal, grandTotal, amt, cashTotal, extTotal, cashVar, cashInHand;

            if (orgBrnCode.equalsIgnoreCase("0A")) {
//                List dataList1 = em.createNativeQuery("SELECT DISTINCT(substring(acno,3,8)) FROM gl_recon WHERE dt<='" + userDate + "' "
//                        + "AND SUBSTRING(acno,3,8) NOT BETWEEN concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','002000') "
//                        + "AND concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','003000') AND SUBSTRING(ACNO,5,4) NOT IN ('0091','0092','0093','0094') "
//                        + "AND SUBSTRING(acno,3,8) <> concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','005502') AND SUBSTRING(acno,3,8) "
//                        + "NOT IN (SELECT glhead FROM  accounttypemaster WHERE glhead IS NOT NULL) AND auth='Y' "
//                        + "AND SUBSTRING(ACNO,3,10) NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info "
//                        + "WHERE PURPOSE LIKE '%CASH IN HAND%')").getResultList();
                List dataList1 = em.createNativeQuery("SELECT DISTINCT(substring(acno,3,8)) FROM gl_recon WHERE dt<='" + userDate + "' "
                        + "AND SUBSTRING(acno,3,8) NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_ST.getValue() + "'"
                        + "AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "' AND SUBSTRING(acno,3,8) NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_DR_ST.getValue() + "'"
                        + "AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_DR_END.getValue() + "' AND SUBSTRING(acno,3,8) NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_CR_ST.getValue() + "'"
                        + "AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_CR_END.getValue() + "' AND SUBSTRING(acno,3,8) NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_PAY_ST.getValue() + "'"
                        + "AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_PAY_END.getValue() + "' AND SUBSTRING(acno,3,8) NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_REC_ST.getValue() + "'"
                        + "AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_REC_END.getValue() + "'"
                        + "AND SUBSTRING(acno,3,8) <> '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_OTH.getValue() + "' AND SUBSTRING(acno,3,8) "
                        + "NOT IN (SELECT GLHEAD FROM  accounttypemaster WHERE glhead IS NOT NULL) AND auth='Y' "
                        + "AND SUBSTRING(ACNO,3,10) NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info "
                        + "WHERE PURPOSE LIKE '%CASH IN HAND%')").getResultList();
                if (dataList1.size() > 0) {
                    for (int i = 0; i < dataList1.size(); i++) {
                        Vector element1 = (Vector) dataList1.get(i);
                        TrialBalancePojo object1 = new TrialBalancePojo();
                        accNo = (String) element1.get(0);
                        List dataList2 = em.createNativeQuery("SELECT DISTINCT acname FROM gltable WHERE substring(acno,3,8)='" + accNo + "'").getResultList();
                        if (!dataList2.isEmpty()) {
                            Vector element2 = (Vector) dataList2.get(0);
                            accName = (String) element2.get(0);
                            List dataList3 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0)"
                                    + ",ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon "
                                    + "WHERE dt<='" + userDate + "' AND substring(acno,3,8)= '" + accNo + "' AND auth='Y'").getResultList();
                            Vector element3 = (Vector) dataList3.get(0);
                            glTotal = Double.parseDouble(element3.get(2).toString());
                            if (glTotal < 0) {
                                object1.setAcName(accName);
                                object1.setCrBalance(new BigDecimal("0"));
                                object1.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                                trialBalanceDataList.add(object1);
                            } else if (glTotal > 0) {
                                object1.setAcName(accName);
                                object1.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                                object1.setDrBalance(new BigDecimal("0"));
                                trialBalanceDataList.add(object1);
                            }
                        }
                    }
                }
//                List dataList4 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0),"
//                        + "ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon "
//                        + "WHERE dt<='" + userDate + "' AND SUBSTRING(acno,5,4) LIKE '0094' "
//                        + "AND auth='Y'").getResultList();
                List dataList4 = em.createNativeQuery("SELECT IfNull(SUM(cramt),0),IfNull(SUM(dramt),0),"
                        + "IfNull(SUM(cramt),0)-IfNull(SUM(dramt),0) FROM gl_recon "
                        + "WHERE dt<='" + userDate + "' AND SUBSTRING(acno,3,8) BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_REC_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_REC_END.getValue() + "' "
                        + "AND auth='Y'").getResultList();
                if (dataList4.size() > 0) {
                    TrialBalancePojo object1 = new TrialBalancePojo();
                    Vector element4 = (Vector) dataList4.get(0);
                    glTotal = Double.parseDouble(element4.get(2).toString());
                    if (glTotal < 0) {
                        object1.setAcName("INTEREST RECEIVABLE");
                        object1.setCrBalance(new BigDecimal("0"));
                        object1.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        trialBalanceDataList.add(object1);
                    } else if (glTotal > 0) {
                        object1.setAcName("INTEREST RECEIVABLE");
                        object1.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        object1.setDrBalance(new BigDecimal("0"));
                        trialBalanceDataList.add(object1);
                    }
                }
//                List dataList5 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0),"
//                        + "ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon "
//                        + "WHERE dt<='" + userDate + "' AND SUBSTRING(acno,5,4) LIKE '0093' AND auth='Y'").getResultList();
                List dataList5 = em.createNativeQuery("SELECT IfNull(SUM(cramt),0),IfNull(SUM(dramt),0),"
                        + "IfNull(SUM(cramt),0)-IfNull(SUM(dramt),0) FROM gl_recon "
                        + "WHERE dt<='" + userDate + "' AND SUBSTRING(acno,3,8) BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_PAY_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_PAY_END.getValue() + "' AND auth='Y'").getResultList();
                if (dataList5.size() > 0) {
                    TrialBalancePojo object1 = new TrialBalancePojo();
                    Vector element5 = (Vector) dataList5.get(0);
                    glTotal = Double.parseDouble(element5.get(2).toString());
                    if (glTotal < 0) {
                        object1.setAcName("INTEREST PAYABLE");
                        object1.setCrBalance(new BigDecimal("0"));
                        object1.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        trialBalanceDataList.add(object1);
                    } else if (glTotal > 0) {
                        object1.setAcName("INTEREST PAYABLE");
                        object1.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        object1.setDrBalance(new BigDecimal("0"));
                        trialBalanceDataList.add(object1);
                    }
                }
//                List dataList6 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0),"
//                        + "ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon WHERE "
//                        + "dt<='" + userDate + "' AND SUBSTRING(acno,5,4) LIKE '0092' AND auth='Y'").getResultList();
                List dataList6 = em.createNativeQuery("SELECT IFNull(SUM(cramt),0),IfNull(SUM(dramt),0),"
                        + "IfNull(SUM(cramt),0)-IfNull(SUM(dramt),0) FROM gl_recon WHERE "
                        + "dt<='" + userDate + "' AND SUBSTRING(acno,3,8) BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_CR_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_CR_END.getValue() + "' AND auth='Y'").getResultList();
                if (dataList6.size() > 0) {
                    TrialBalancePojo object1 = new TrialBalancePojo();
                    Vector element6 = (Vector) dataList6.get(0);
                    glTotal = Double.parseDouble(element6.get(2).toString());
                    if (glTotal < 0) {
                        object1.setAcName("SUNDRY CREDITORS");
                        object1.setCrBalance(new BigDecimal("0"));
                        object1.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        trialBalanceDataList.add(object1);
                    } else if (glTotal > 0) {
                        object1.setAcName("SUNDRY CREDITORS");
                        object1.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        object1.setDrBalance(new BigDecimal("0"));
                        trialBalanceDataList.add(object1);
                    }
                }
//                List dataList7 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0),"
//                        + "ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon "
//                        + "WHERE dt<='" + userDate + "' AND SUBSTRING(acno,5,4) LIKE '0091' AND auth='Y'").getResultList();
                List dataList7 = em.createNativeQuery("SELECT IfNull(SUM(cramt),0),IfNull(SUM(dramt),0),"
                        + "IfNull(SUM(cramt),0)-IfNull(SUM(dramt),0) FROM gl_recon "
                        + "WHERE dt<='" + userDate + "' AND SUBSTRING(acno,3,8) BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_DR_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_DR_END.getValue() + "' AND auth='Y'").getResultList();
                if (dataList7.size() > 0) {
                    TrialBalancePojo object1 = new TrialBalancePojo();
                    Vector element7 = (Vector) dataList7.get(0);
                    glTotal = Double.parseDouble(element7.get(2).toString());
                    if (glTotal < 0) {
                        object1.setAcName("SUNDRY DEBTORS");
                        object1.setCrBalance(new BigDecimal("0"));
                        object1.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        trialBalanceDataList.add(object1);
                    } else if (glTotal > 0) {
                        object1.setAcName("SUNDRY DEBTORS");
                        object1.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        object1.setDrBalance(new BigDecimal("0"));
                        trialBalanceDataList.add(object1);
                    }
                }

                TrialBalancePojo object1 = new TrialBalancePojo();
                glTotal = IncomeTr(userDate, orgBrnCode);
                if (glTotal < 0) {
                    object1.setAcName("INCOME");
                    object1.setCrBalance(new BigDecimal("0"));
                    object1.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                    trialBalanceDataList.add(object1);
                } else if (glTotal > 0) {
                    object1.setAcName("INCOME");
                    object1.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                    object1.setDrBalance(new BigDecimal("0"));
                    trialBalanceDataList.add(object1);
                }

                TrialBalancePojo object2 = new TrialBalancePojo();
                glTotal = ExpTr(userDate, orgBrnCode);
                if (glTotal < 0) {
                    object2.setAcName("EXPENDITURE");
                    object2.setCrBalance(new BigDecimal("0"));
                    object2.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                    trialBalanceDataList.add(object2);
                } else if (glTotal > 0) {
                    object2.setAcName("EXPENDITURE");
                    object2.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                    object2.setDrBalance(new BigDecimal("0"));
                    trialBalanceDataList.add(object2);
                }

                List acctCodeList = em.createNativeQuery("select distinct AcctCode from accounttypemaster where acctNature<>'" + CbsConstant.PAY_ORDER + "'").getResultList();
                if (acctCodeList.size() > 0) {
                    for (int i = 0; i < acctCodeList.size(); i++) {
                        TrialBalancePojo object3 = new TrialBalancePojo();
                        Vector element = (Vector) acctCodeList.get(i);
                        String accCode = (String) element.get(0);
                        String tableName = getTableNam(accCode);
                        String acNat = common.getAcNatureByAcType(accCode);
                        if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            List dataList10 = em.createNativeQuery("SELECT ifnull(SUM(r.cramt),0)-ifnull(SUM(r.dramt),0) FROM " + tableName + " r,"
                                    + " td_accountmaster am WHERE  r.dt<='" + userDate + "' AND (SUBSTRING(r.acno,3,2)='" + accCode + "') AND r.auth='Y' AND "
                                    + "r.closeflag IS NULL AND r.TRANTYPE<>27 AND r.acno = am.acno").getResultList();
                            Vector element10 = (Vector) dataList10.get(0);
                            acTotal = Double.parseDouble(element10.get(0).toString());

                            List dataList11 = em.createNativeQuery("SELECT GLHEAD,acctdesc from accounttypemaster"
                                    + " where acctcode='" + accCode + "'").getResultList();
                            Vector element11 = (Vector) dataList11.get(0);
                            glAccno = (String) element11.get(0);
                            acctDesc = (String) element11.get(1);

                            List dataList12 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon WHERE  dt<='" + userDate + "' AND SUBSTRING(acno,3,8)= '" + glAccno + "' AND auth='Y'").getResultList();
                            Vector element12 = (Vector) dataList12.get(0);
                            glTotal = Double.parseDouble(element12.get(0).toString());

                            grandTotal = glTotal + acTotal;
                            if (grandTotal < 0) {
                                object3.setAcName(acctDesc);
                                object3.setCrBalance(new BigDecimal("0"));
                                object3.setDrBalance(new BigDecimal(formatter.format(Math.abs(grandTotal))));
                                trialBalanceDataList.add(object3);
                            } else if (grandTotal > 0) {
                                object3.setAcName(acctDesc);
                                object3.setCrBalance(new BigDecimal(formatter.format(Math.abs(grandTotal))));
                                object3.setDrBalance(new BigDecimal("0"));
                                trialBalanceDataList.add(object3);
                            }
                        } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                            List dataList10 = em.createNativeQuery("SELECT ifnull(SUM(r.cramt),0)-ifnull(SUM(r.dramt),0) FROM " + tableName + " r,"
                                    + " td_accountmaster am WHERE  r.dt<='" + userDate + "' AND (SUBSTRING(r.acno,3,2)='" + accCode + "') AND r.auth='Y' AND "
                                    + "r.closeflag IS NULL AND r.TRANTYPE<>27 AND r.tdacno = am.acno").getResultList();
                            Vector element10 = (Vector) dataList10.get(0);
                            acTotal = Double.parseDouble(element10.get(0).toString());

                            List dataList11 = em.createNativeQuery("SELECT GLHEAD,acctdesc from accounttypemaster"
                                    + " where acctcode='" + accCode + "'").getResultList();
                            Vector element11 = (Vector) dataList11.get(0);
                            glAccno = (String) element11.get(0);
                            acctDesc = (String) element11.get(1);

                            List dataList12 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon WHERE  dt<='" + userDate + "' AND SUBSTRING(acno,3,8)= '" + glAccno + "' AND auth='Y'").getResultList();
                            Vector element12 = (Vector) dataList12.get(0);
                            glTotal = Double.parseDouble(element12.get(0).toString());

                            grandTotal = glTotal + acTotal;
                            if (grandTotal < 0) {
                                object3.setAcName(acctDesc);
                                object3.setCrBalance(new BigDecimal("0"));
                                object3.setDrBalance(new BigDecimal(formatter.format(Math.abs(grandTotal))));
                                trialBalanceDataList.add(object3);
                            } else if (grandTotal > 0) {
                                object3.setAcName(acctDesc);
                                object3.setCrBalance(new BigDecimal(formatter.format(Math.abs(grandTotal))));
                                object3.setDrBalance(new BigDecimal("0"));
                                trialBalanceDataList.add(object3);
                            }
                        } else {
                            List dataList10 = em.createNativeQuery("SELECT ifnull(SUM(r.cramt),0)-ifnull(SUM(r.dramt),0) FROM " + tableName + " r,"
                                    + " accountmaster am WHERE  r.dt<='" + userDate + "' AND SUBSTRING(r.acno,3,2)='" + accCode + "' and r.auth='Y' AND "
                                    + "r.acno = am.acno").getResultList();
                            Vector element10 = (Vector) dataList10.get(0);
                            acTotal = Double.parseDouble(element10.get(0).toString());

                            List dataList11 = em.createNativeQuery("SELECT GLHEAD,acctdesc from accounttypemaster"
                                    + " where acctcode='" + accCode + "'").getResultList();
                            Vector element11 = (Vector) dataList11.get(0);
                            glAccno = (String) element11.get(0);
                            acctDesc = (String) element11.get(1);

                            List dataList12 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon WHERE  dt<='" + userDate + "' AND SUBSTRING(acno,3,8)= '" + glAccno + "' AND auth='Y'").getResultList();
                            Vector element12 = (Vector) dataList12.get(0);
                            glTotal = Double.parseDouble(element12.get(0).toString());

                            grandTotal = glTotal + acTotal;
                            if (grandTotal < 0) {
                                object3.setAcName(acctDesc);
                                object3.setCrBalance(new BigDecimal("0"));
                                object3.setDrBalance(new BigDecimal(formatter.format(Math.abs(grandTotal))));
                                trialBalanceDataList.add(object3);
                            } else if (grandTotal > 0) {
                                object3.setAcName(acctDesc);
                                object3.setCrBalance(new BigDecimal(formatter.format(Math.abs(grandTotal))));
                                object3.setDrBalance(new BigDecimal("0"));
                                trialBalanceDataList.add(object3);
                            }
                        }
                    }
                }

                cashInHand = CshInHandM(userDate, orgBrnCode, orgBrnCode);

                TrialBalancePojo object4 = new TrialBalancePojo();
                object4.setAcName("CASH IN HAND");
                object4.setCrBalance(new BigDecimal("0"));
                object4.setDrBalance(new BigDecimal(formatter.format(Math.abs(cashInHand))));
                trialBalanceDataList.add(object4);

                dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            } else {
//                List dataList1 = em.createNativeQuery("SELECT DISTINCT(acno) FROM gl_recon WHERE dt<='" + userDate + "' "
//                        + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "' AND SUBSTRING(acno,3,8) NOT BETWEEN concat('"+CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"', '002000') "
//                        + "AND  concat('"+CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','003000') AND SUBSTRING(ACNO,5,4) NOT IN ('0091','0092','0093','0094') "
//                        + "AND SUBSTRING(acno,3,8) <> concat('" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','005502') AND SUBSTRING(acno,3,8) "
//                        + "NOT IN (SELECT glhead FROM  accounttypemaster WHERE glhead IS NOT NULL) AND auth='Y' "
//                        + "AND SUBSTRING(ACNO,3,10) NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info "
//                        + "WHERE PURPOSE LIKE '%CASH IN HAND%')").getResultList();
                List dataList1 = em.createNativeQuery("SELECT DISTINCT(acno) FROM gl_recon WHERE dt<='" + userDate + "' "
                        + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "' AND SUBSTRING(acno,3,8) NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_ST.getValue() + "'"
                        + "AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "' AND SUBSTRING(acno,3,8) NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_DR_ST.getValue() + "'"
                        + "AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_DR_END.getValue() + "' AND SUBSTRING(acno,3,8) NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_CR_ST.getValue() + "'"
                        + "AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_CR_END.getValue() + "' AND SUBSTRING(acno,3,8) NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_PAY_ST.getValue() + "'"
                        + "AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_PAY_END.getValue() + "' AND SUBSTRING(acno,3,8) NOT BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_REC_ST.getValue() + "'"
                        + "AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_REC_END.getValue() + "'"
                        + "AND SUBSTRING(acno,3,8) <> '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_OTH.getValue() + "' AND SUBSTRING(acno,3,8) "
                        + "NOT IN (SELECT GLHEAD FROM  accounttypemaster WHERE glhead IS NOT NULL) AND auth='Y' "
                        + "AND SUBSTRING(ACNO,3,10) NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info "
                        + "WHERE PURPOSE LIKE '%CASH IN HAND%')").getResultList();
                if (dataList1.size() > 0) {
                    for (int i = 0; i < dataList1.size(); i++) {
                        Vector element1 = (Vector) dataList1.get(i);
                        TrialBalancePojo object1 = new TrialBalancePojo();
                        accNo = (String) element1.get(0);
                        List dataList2 = em.createNativeQuery("SELECT acname FROM gltable WHERE acno='" + accNo + "'").getResultList();
                        if (!dataList2.isEmpty()) {
                            Vector element2 = (Vector) dataList2.get(0);
                            accName = (String) element2.get(0);
                            List dataList3 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0)"
                                    + ",ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon "
                                    + "WHERE dt<='" + userDate + "' AND acno= '" + accNo + "' AND auth='Y'").getResultList();
                            Vector element3 = (Vector) dataList3.get(0);
                            glTotal = Double.parseDouble(element3.get(2).toString());
                            if (glTotal < 0) {
                                object1.setAcName(accName);
                                object1.setCrBalance(new BigDecimal("0"));
                                object1.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                                trialBalanceDataList.add(object1);
                            } else if (glTotal > 0) {
                                object1.setAcName(accName);
                                object1.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                                object1.setDrBalance(new BigDecimal("0"));
                                trialBalanceDataList.add(object1);
                            }
                        }
                    }
                }
//                List dataList4 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0),"
//                        + "ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon "
//                        + "WHERE dt<='" + userDate + "' AND SUBSTRING(acno,5,4) LIKE '0094' "
//                        + "AND auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
                List dataList4 = em.createNativeQuery("SELECT IfNull(SUM(cramt),0),IfNull(SUM(dramt),0),"
                        + "IfNull(SUM(cramt),0)-IfNull(SUM(dramt),0) FROM gl_recon "
                        + "WHERE dt<='" + userDate + "' AND SUBSTRING(acno,3,8) BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_REC_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_REC_END.getValue() + "' "
                        + "AND auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
                if (dataList4.size() > 0) {
                    TrialBalancePojo object1 = new TrialBalancePojo();
                    Vector element4 = (Vector) dataList4.get(0);
                    glTotal = Double.parseDouble(element4.get(2).toString());
                    if (glTotal < 0) {
                        object1.setAcName("INTEREST RECEIVABLE");
                        object1.setCrBalance(new BigDecimal("0"));
                        object1.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        trialBalanceDataList.add(object1);
                    } else if (glTotal > 0) {
                        object1.setAcName("INTEREST RECEIVABLE");
                        object1.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        object1.setDrBalance(new BigDecimal("0"));
                        trialBalanceDataList.add(object1);
                    }
                }
//                List dataList5 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0),"
//                        + "ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon "
//                        + "WHERE dt<='" + userDate + "' AND SUBSTRING(acno,5,4) LIKE '0093' AND auth='Y' "
//                        + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
                List dataList5 = em.createNativeQuery("SELECT IfNull(SUM(cramt),0),IfNull(SUM(dramt),0),"
                        + "IfNull(SUM(cramt),0)-IfNull(SUM(dramt),0) FROM gl_recon "
                        + "WHERE dt<='" + userDate + "' AND SUBSTRING(acno,3,8) BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_PAY_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INT_PAY_END.getValue() + "' AND auth='Y' "
                        + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
                if (dataList5.size() > 0) {
                    TrialBalancePojo object1 = new TrialBalancePojo();
                    Vector element5 = (Vector) dataList5.get(0);
                    glTotal = Double.parseDouble(element5.get(2).toString());
                    if (glTotal < 0) {
                        object1.setAcName("INTEREST PAYABLE");
                        object1.setCrBalance(new BigDecimal("0"));
                        object1.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        trialBalanceDataList.add(object1);
                    } else if (glTotal > 0) {
                        object1.setAcName("INTEREST PAYABLE");
                        object1.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        object1.setDrBalance(new BigDecimal("0"));
                        trialBalanceDataList.add(object1);
                    }
                }
//                List dataList6 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0),"
//                        + "ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon WHERE "
//                        + "dt<='" + userDate + "' AND SUBSTRING(acno,5,4) LIKE '0092' AND auth='Y' "
//                        + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
                List dataList6 = em.createNativeQuery("SELECT IfNull(SUM(cramt),0),IfNull(SUM(dramt),0),"
                        + "IfNull(SUM(cramt),0)-IfNull(SUM(dramt),0) FROM gl_recon WHERE "
                        + "dt<='" + userDate + "' AND SUBSTRING(acno,3,8) BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_CR_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_CR_END.getValue() + "' AND auth='Y' "
                        + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
                if (dataList6.size() > 0) {
                    TrialBalancePojo object1 = new TrialBalancePojo();
                    Vector element6 = (Vector) dataList6.get(0);
                    glTotal = Double.parseDouble(element6.get(2).toString());
                    if (glTotal < 0) {
                        object1.setAcName("SUNDRY CREDITORS");
                        object1.setCrBalance(new BigDecimal("0"));
                        object1.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        trialBalanceDataList.add(object1);
                    } else if (glTotal > 0) {
                        object1.setAcName("SUNDRY CREDITORS");
                        object1.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        object1.setDrBalance(new BigDecimal("0"));
                        trialBalanceDataList.add(object1);
                    }
                }
//                List dataList7 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0),ifnull(SUM(dramt),0),"
//                        + "ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon "
//                        + "WHERE dt<='" + userDate + "' AND SUBSTRING(acno,5,4) LIKE '0091' AND auth='Y' "
//                        + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
                List dataList7 = em.createNativeQuery("SELECT IfNull(SUM(cramt),0),IfNull(SUM(dramt),0),"
                        + "IfNull(SUM(cramt),0)-IfNull(SUM(dramt),0) FROM gl_recon "
                        + "WHERE dt<='" + userDate + "' AND SUBSTRING(acno,3,8) BETWEEN '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_DR_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_SUN_DR_END.getValue() + "' AND auth='Y' "
                        + "AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
                if (dataList7.size() > 0) {
                    TrialBalancePojo object1 = new TrialBalancePojo();
                    Vector element7 = (Vector) dataList7.get(0);
                    glTotal = Double.parseDouble(element7.get(2).toString());
                    if (glTotal < 0) {
                        object1.setAcName("SUNDRY DEBTORS");
                        object1.setCrBalance(new BigDecimal("0"));
                        object1.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        trialBalanceDataList.add(object1);
                    } else if (glTotal > 0) {
                        object1.setAcName("SUNDRY DEBTORS");
                        object1.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                        object1.setDrBalance(new BigDecimal("0"));
                        trialBalanceDataList.add(object1);
                    }
                }
                TrialBalancePojo object1 = new TrialBalancePojo();
                glTotal = IncomeTr(userDate, orgBrnCode);
                if (glTotal < 0) {
                    object1.setAcName("INCOME");
                    object1.setCrBalance(new BigDecimal("0"));
                    object1.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                    trialBalanceDataList.add(object1);
                } else if (glTotal > 0) {
                    object1.setAcName("INCOME");
                    object1.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                    object1.setDrBalance(new BigDecimal("0"));
                    trialBalanceDataList.add(object1);
                }

                TrialBalancePojo object2 = new TrialBalancePojo();
                glTotal = ExpTr(userDate, orgBrnCode);
                if (glTotal < 0) {
                    object2.setAcName("EXPENDITURE");
                    object2.setCrBalance(new BigDecimal("0"));
                    object2.setDrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                    trialBalanceDataList.add(object2);
                } else if (glTotal > 0) {
                    object2.setAcName("EXPENDITURE");
                    object2.setCrBalance(new BigDecimal(formatter.format(Math.abs(glTotal))));
                    object2.setDrBalance(new BigDecimal("0"));
                    trialBalanceDataList.add(object2);
                }

                List acctCodeList = em.createNativeQuery("select distinct AcctCode from accounttypemaster where acctNature<>'" + CbsConstant.PAY_ORDER + "'").getResultList();
                if (acctCodeList.size() > 0) {
                    for (int i = 0; i < acctCodeList.size(); i++) {
                        TrialBalancePojo object3 = new TrialBalancePojo();
                        Vector element = (Vector) acctCodeList.get(i);
                        String accCode = (String) element.get(0);
                        String tableName = getTableNam(accCode);
                        String acNat = common.getAcNatureByAcType(accCode);
                        if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            List dataList10 = em.createNativeQuery("SELECT ifnull(SUM(r.cramt),0)-ifnull(SUM(r.dramt),0) FROM " + tableName + " r,"
                                    + " td_accountmaster am WHERE  r.dt<='" + userDate + "' AND (SUBSTRING(r.acno,3,2)='" + accCode + "') AND r.auth='Y' AND "
                                    + "r.closeflag IS NULL AND r.TRANTYPE<>27 AND r.acno = am.acno and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
                            Vector element10 = (Vector) dataList10.get(0);
                            acTotal = Double.parseDouble(element10.get(0).toString());

                            List dataList11 = em.createNativeQuery("SELECT GLHEAD,acctdesc from accounttypemaster"
                                    + " where acctcode='" + accCode + "'").getResultList();
                            Vector element11 = (Vector) dataList11.get(0);
                            glAccno = (String) element11.get(0);
                            acctDesc = (String) element11.get(1);

                            List dataList12 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon WHERE  dt<='" + userDate + "' AND SUBSTRING(acno,3,8)= '" + glAccno + "' AND auth='Y' AND SUBSTRING(acno,1,2) = '" + orgBrnCode + "'").getResultList();
                            Vector element12 = (Vector) dataList12.get(0);
                            glTotal = Double.parseDouble(element12.get(0).toString());

                            grandTotal = glTotal + acTotal;
                            if (grandTotal < 0) {
                                object3.setAcName(acctDesc);
                                object3.setCrBalance(new BigDecimal("0"));
                                object3.setDrBalance(new BigDecimal(formatter.format(Math.abs(grandTotal))));
                                trialBalanceDataList.add(object3);
                            } else if (grandTotal > 0) {
                                object3.setAcName(acctDesc);
                                object3.setCrBalance(new BigDecimal(formatter.format(Math.abs(grandTotal))));
                                object3.setDrBalance(new BigDecimal("0"));
                                trialBalanceDataList.add(object3);
                            }
                        } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                            List dataList10 = em.createNativeQuery("SELECT ifnull(SUM(r.cramt),0)-ifnull(SUM(r.dramt),0) FROM " + tableName + " r,"
                                    + " td_accountmaster am WHERE  r.dt<='" + userDate + "' AND (SUBSTRING(r.acno,3,2)='" + accCode + "') AND r.auth='Y' AND "
                                    + "r.closeflag IS NULL AND r.TRANTYPE<>27 AND r.tdacno = am.acno and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
                            Vector element10 = (Vector) dataList10.get(0);
                            acTotal = Double.parseDouble(element10.get(0).toString());

                            List dataList11 = em.createNativeQuery("SELECT GLHEAD,acctdesc from accounttypemaster"
                                    + " where acctcode='" + accCode + "'").getResultList();
                            Vector element11 = (Vector) dataList11.get(0);
                            glAccno = (String) element11.get(0);
                            acctDesc = (String) element11.get(1);

                            List dataList12 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon WHERE  dt<='" + userDate + "' AND SUBSTRING(acno,3,8)= '" + glAccno + "' AND auth='Y' AND SUBSTRING(acno,1,2) = '" + orgBrnCode + "'").getResultList();
                            Vector element12 = (Vector) dataList12.get(0);
                            glTotal = Double.parseDouble(element12.get(0).toString());

                            grandTotal = glTotal + acTotal;
                            if (grandTotal < 0) {
                                object3.setAcName(acctDesc);
                                object3.setCrBalance(new BigDecimal("0"));
                                object3.setDrBalance(new BigDecimal(formatter.format(Math.abs(grandTotal))));
                                trialBalanceDataList.add(object3);
                            } else if (grandTotal > 0) {
                                object3.setAcName(acctDesc);
                                object3.setCrBalance(new BigDecimal(formatter.format(Math.abs(grandTotal))));
                                object3.setDrBalance(new BigDecimal("0"));
                                trialBalanceDataList.add(object3);
                            }
                        } else {
                            List dataList10 = em.createNativeQuery("SELECT ifnull(SUM(r.cramt),0)-ifnull(SUM(r.dramt),0) FROM " + tableName + " r,"
                                    + " accountmaster am WHERE  r.dt<='" + userDate + "' AND SUBSTRING(r.acno,3,2)='" + accCode + "' and r.auth='Y' AND "
                                    + "r.acno = am.acno and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
                            Vector element10 = (Vector) dataList10.get(0);
                            acTotal = Double.parseDouble(element10.get(0).toString());

                            List dataList11 = em.createNativeQuery("SELECT GLHEAD,acctdesc from accounttypemaster"
                                    + " where acctcode='" + accCode + "'").getResultList();
                            Vector element11 = (Vector) dataList11.get(0);
                            glAccno = (String) element11.get(0);
                            acctDesc = (String) element11.get(1);

                            List dataList12 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0) FROM gl_recon WHERE  dt<='" + userDate + "' AND SUBSTRING(acno,3,8)= '" + glAccno + "' AND auth='Y' AND SUBSTRING(acno,1,2) = '" + orgBrnCode + "'").getResultList();
                            Vector element12 = (Vector) dataList12.get(0);
                            glTotal = Double.parseDouble(element12.get(0).toString());

                            grandTotal = glTotal + acTotal;
                            if (grandTotal < 0) {
                                object3.setAcName(acctDesc);
                                object3.setCrBalance(new BigDecimal("0"));
                                object3.setDrBalance(new BigDecimal(formatter.format(Math.abs(grandTotal))));
                                trialBalanceDataList.add(object3);
                            } else if (grandTotal > 0) {
                                object3.setAcName(acctDesc);
                                object3.setCrBalance(new BigDecimal(formatter.format(Math.abs(grandTotal))));
                                object3.setDrBalance(new BigDecimal("0"));
                                trialBalanceDataList.add(object3);
                            }
                        }
                    }
                }

                cashInHand = CshInHandM(userDate, orgBrnCode, orgBrnCode);

                TrialBalancePojo object4 = new TrialBalancePojo();
                object4.setAcName("CASH IN HAND");
                object4.setCrBalance(new BigDecimal("0"));
                object4.setDrBalance(new BigDecimal(formatter.format(Math.abs(cashInHand))));
                trialBalanceDataList.add(object4);

                dataList1 = common.getBranchNameandAddress(orgBrnCode);
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            }
            if (trialBalanceDataList.size() > 0) {
                SortedTrialBalanceReport sortByAcName = new SortedTrialBalanceReport();
                Collections.sort(trialBalanceDataList, sortByAcName);

                for (TrialBalancePojo object : trialBalanceDataList) {
                    object.setBankName(bankName);
                    object.setBankAddress(bankAddress);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
        return trialBalanceDataList;
    }

    public String getTableNam(String acctCode) throws ApplicationException {
        String tableName = "";
        try {
            List acctNature = em.createNativeQuery("select acctNature from accounttypemaster where AcctCode='" + acctCode + "'").getResultList();
            Vector element = (Vector) acctNature.get(0);
            String accNat = (String) element.get(0);

            if (accNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                return "recon";
            } else if (accNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || accNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                return "td_recon";
            } else if (accNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                return "rdrecon";
            } else if (accNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || accNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || accNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                return "loan_recon";
            } else if (accNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                return "ca_recon";
            } else if (accNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                return "of_recon";
            } else if (accNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                return "ddstransaction";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableName;
    }

    public List<GlbTempActypeEntryPojo> getconsolidateGeneralLedgerBookData(String userDate, String orgBrnCode, String nBrCode) throws ApplicationException {
        String tmpAcType, tmpReconTab, tmpAcNat, tmpGLHead = "", tmpSGCode, tmpDesc, tmpGCode, tmpglbactype, tmpAcstatus, brLtotal;
        NumberFormat formatter = new DecimalFormat("#.##");
        int tmpLiabAsst;
        List amtLst = new ArrayList();
        List acGlSum = new ArrayList();
        List brLst = new ArrayList();
        List<GlbTempActypeEntryPojo> glbLst = new ArrayList<GlbTempActypeEntryPojo>();
        try {
            List acctNatLst = em.createNativeQuery("SELECT Sno,GroupCode,SubGroupCode,Code,Description,AcType,GlbActype,ActStatus,status,brncode from "
                    + "glbmast WHERE  ifnull(ACTYPE,'')<>''").getResultList();
            if (!acctNatLst.isEmpty()) {
                for (int i = 0; i < acctNatLst.size(); i++) {
                    GlbTempActypeEntryPojo glbEntryPojo = new GlbTempActypeEntryPojo();
                    Vector element = (Vector) acctNatLst.get(i);

                    tmpAcType = element.get(5).toString();
                    tmpReconTab = getTableNam(tmpAcType);
                    tmpAcNat = common.getAcNatureByAcType(tmpAcType);
                    tmpGLHead = (element.get(3) != null ? element.get(3).toString() : "");
                    tmpGCode = (element.get(1) != null ? element.get(1).toString() : "0");
                    tmpSGCode = (element.get(2) != null ? element.get(2).toString() : "0");
                    tmpDesc = (element.get(4) != null ? element.get(4).toString() : "");
                    tmpglbactype = element.get(6).toString();
                    tmpAcstatus = (element.get(7) != null ? element.get(7).toString() : "0");

                    if (tmpglbactype.equalsIgnoreCase("A")) {
                        tmpLiabAsst = -1;
                    } else {
                        tmpLiabAsst = 1;
                    }

                    double tmpBal = 0;
                    if (orgBrnCode.equalsIgnoreCase("0A")) {
                        if (tmpAcNat.equalsIgnoreCase("CA")) {
                            if (tmpAcstatus.equalsIgnoreCase("0")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " AND  r.DT <= '" + userDate + "' and r.auth = 'Y' ").getResultList();
                            } else if (tmpAcstatus.equalsIgnoreCase("2")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " AND (OPTSTATUS = " + tmpAcstatus + ") AND r.DT <= '" + userDate + "' and r.auth = 'Y' ").getResultList();
                            } else {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate + "') AND r.DT <= '" + userDate + "' and r.auth = 'Y'").getResultList();
                            }
                        }
                        if ((tmpAcNat.equalsIgnoreCase("SB")) || (tmpAcNat.equalsIgnoreCase("RD")) || (tmpAcNat.equalsIgnoreCase("DS"))) {
                            if (tmpAcstatus.equalsIgnoreCase("0")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " AND  r.DT <= '" + userDate + "' and r.auth = 'Y' ").getResultList();
                            } else if (tmpAcstatus.equalsIgnoreCase("2")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " AND (OPTSTATUS = " + tmpAcstatus + ") AND r.DT <= '" + userDate + "' and r.auth = 'Y' ").getResultList();
                            } else {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate + "') AND r.DT <= '" + userDate + "' and r.auth = 'Y'").getResultList();
                            }
                        }
                        if ((tmpAcNat.equalsIgnoreCase("TL")) || (tmpAcNat.equalsIgnoreCase("DL"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                    + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                    + " from " + tmpReconTab + " WHERE DT <= '" + userDate + "' and r.acno = a.acno "
                                    + " AND  r.DT <= '" + userDate + "' and r.auth = 'Y')").getResultList();
                        }

                        if ((tmpAcNat.equalsIgnoreCase("FD")) || (tmpAcNat.equalsIgnoreCase("MS"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate + "' and auth = 'Y' and CLOSEFLAG IS NULL").getResultList();
                        }

                        if ((tmpAcNat.equalsIgnoreCase("OF"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate + "' and auth = 'Y'").getResultList();
                        }

                        Vector element1 = (Vector) amtLst.get(0);
                        tmpBal = Double.valueOf(formatter.format(Double.parseDouble(element1.get(0).toString())));

                    } else if (orgBrnCode.equalsIgnoreCase("0B")) {
                        if (tmpAcNat.equalsIgnoreCase("CA")) {
                            if (tmpAcstatus.equalsIgnoreCase("0")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate + "' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) and  r.dt <= '" + userDate + "' and r.auth = 'Y' ").getResultList();
                            } else if (tmpAcstatus.equalsIgnoreCase("2")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate + "' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND (optstatus = " + tmpAcstatus + ") and r.dt <= '" + userDate + "' and r.auth = 'Y' ").getResultList();
                            } else {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate + "' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate + "') and r.dt <= '" + userDate + "' and r.auth = 'Y'").getResultList();
                            }
                        }
                        if ((tmpAcNat.equalsIgnoreCase("SB")) || (tmpAcNat.equalsIgnoreCase("RD")) || (tmpAcNat.equalsIgnoreCase("DS"))) {
                            if (tmpAcstatus.equalsIgnoreCase("0")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(R.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND  r.dt <= '" + userDate + "' and r.auth = 'Y' ").getResultList();
                            } else if (tmpAcstatus.equalsIgnoreCase("2")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(R.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND (OPTSTATUS = " + tmpAcstatus + ") AND r.dt <= '" + userDate + "' and r.auth = 'Y' ").getResultList();
                            } else {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(R.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate + "') and r.dt <= '" + userDate + "' and r.auth = 'Y'").getResultList();
                            }
                        }
                        if ((tmpAcNat.equalsIgnoreCase("TL")) || (tmpAcNat.equalsIgnoreCase("DL"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                    + "accountmaster a where substring(R.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                    + " from " + tmpReconTab + " WHERE DT <= '" + userDate + "' and r.acno = a.acno and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                    + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                    + " branchmaster where brncode = cast('" + nBrCode + "' as int)) "
                                    + " AND  r.dt <= '" + userDate + "' and r.auth = 'Y')").getResultList();
                        }

                        if ((tmpAcNat.equalsIgnoreCase("FD")) || (tmpAcNat.equalsIgnoreCase("MS"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate + "' and auth = 'Y' and CLOSEFLAG IS NULL and substring(acno,1,2) "
                                    + " in ( select right(concat('00',cast(brncode as char(2))),2) "
                                    + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                    + " branchmaster where brncode = cast('" + nBrCode + "' as int))").getResultList();
                        }

                        if ((tmpAcNat.equalsIgnoreCase("OF"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate + "' and auth = 'Y' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                    + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                    + " branchmaster where brncode = cast('" + nBrCode + "' as int)) ").getResultList();
                        }

                        Vector element1 = (Vector) amtLst.get(0);
                        //tmpBal = Double.parseDouble(element1.get(0).toString());
                        tmpBal = Double.valueOf(formatter.format(Double.parseDouble(element1.get(0).toString())));
                    } else {
                        if (tmpAcNat.equalsIgnoreCase("CA")) {
                            if (tmpAcstatus.equalsIgnoreCase("0")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " where dt <= '" + userDate + "' and substring(acno,1,2)= '" + orgBrnCode + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' and  r.dt <= '" + userDate + "' and r.auth = 'Y' ").getResultList();
                            } else if (tmpAcstatus.equalsIgnoreCase("2")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + orgBrnCode + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + orgBrnCode + " where dt <= '" + userDate + "' and substring(acno,1,2)= '" + orgBrnCode + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' and (optstatus = " + tmpLiabAsst + ") and r.dt <= '" + userDate + "' and r.auth = 'Y' ").getResultList();
                            } else {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + orgBrnCode + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and R.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate + "' and substring(acno,1,2)= '" + orgBrnCode + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' and (optstatus not in (2) or openingdt >= '" + userDate + "') and r.dt <= '" + userDate + "' and r.auth = 'Y'").getResultList();
                            }
                        }
                        if ((tmpAcNat.equalsIgnoreCase("SB")) || (tmpAcNat.equalsIgnoreCase("RD")) || (tmpAcNat.equalsIgnoreCase("DS"))) {
                            if (tmpAcstatus.equalsIgnoreCase("0")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' AND  r.dt <= '" + userDate + "' and r.auth = 'Y' ").getResultList();
                            } else if (tmpAcstatus.equalsIgnoreCase("2")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' AND (OPTSTATUS = " + tmpAcstatus + ") AND r.DT <= '" + userDate + "' and r.auth = 'Y' ").getResultList();
                            } else {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substringrR.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate + "') AND r.DT <= '" + userDate + "' and r.auth = 'Y'").getResultList();
                            }
                        }
                        if ((tmpAcNat.equalsIgnoreCase("TL")) || (tmpAcNat.equalsIgnoreCase("DL"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                    + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                    + " from " + tmpReconTab + " WHERE DT <= '" + userDate + "' and r.acno = a.acno and a.curbrcode = '" + orgBrnCode + "' "
                                    + " AND  r.DT <= '" + userDate + "' and r.auth = 'Y')").getResultList();
                        }

                        if ((tmpAcNat.equalsIgnoreCase("FD")) || (tmpAcNat.equalsIgnoreCase("MS"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate + "' and auth = 'Y' and closeflag is null and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
                        }

                        if ((tmpAcNat.equalsIgnoreCase("OF"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate + "' and auth = 'Y' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
                        }

                        Vector element1 = (Vector) amtLst.get(0);
                        //tmpBal = Double.parseDouble(element1.get(0).toString());
                        tmpBal = Double.valueOf(formatter.format(Double.parseDouble(element1.get(0).toString())));
                    }

                    glbEntryPojo.setAcType(tmpAcType);
                    glbEntryPojo.setGlHead(tmpGLHead);
                    if (tmpglbactype.equalsIgnoreCase("L")) {
                        glbEntryPojo.setCramt(new BigDecimal(tmpBal));
                        glbEntryPojo.setDramt(new BigDecimal(0));
                    } else {
                        glbEntryPojo.setCramt(new BigDecimal(0));
                        glbEntryPojo.setDramt(new BigDecimal(tmpBal));
                    }
                    glbEntryPojo.setAcStatus(Integer.parseInt(tmpAcstatus));
                    glbEntryPojo.setType(tmpglbactype);
                    glbEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                    glbEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                    glbEntryPojo.setDescription(tmpDesc);
                    glbLst.add(glbEntryPojo);
                }
            }

            List acGlLst = em.createNativeQuery("SELECT Sno,GroupCode,SubGroupCode,Code,Description,AcType,GlbActype,ActStatus,status,brncode from "
                    + "glbmast order by groupcode").getResultList();
            if (!acGlLst.isEmpty()) {
                for (int j = 0; j < acGlLst.size(); j++) {
                    double tmpGlBal = 0, tmpTot = 0;
                    BigDecimal nCr = new BigDecimal(0);
                    BigDecimal nDr = new BigDecimal(0);
                    BigDecimal crTot = new BigDecimal(0);
                    BigDecimal drTot = new BigDecimal(0);
                    GlbTempActypeEntryPojo glbEntryPojo = new GlbTempActypeEntryPojo();
                    Vector element = (Vector) acGlLst.get(j);

                    tmpAcType = element.get(5).toString();
                    tmpGLHead = (element.get(3) != null ? element.get(3).toString() : "");
                    tmpAcstatus = (element.get(7) != null ? element.get(7).toString() : "0");
                    tmpglbactype = element.get(6).toString();
                    tmpGCode = (element.get(1) != null ? element.get(1).toString() : "0");
                    tmpSGCode = (element.get(2) != null ? element.get(2).toString() : "0");
                    tmpDesc = (element.get(4) != null ? element.get(4).toString() : "");

                    if (orgBrnCode.equalsIgnoreCase("0A")) {
                        acGlSum = em.createNativeQuery("select ifnull(sum(cramt - dramt),0) from gl_recon where SUBSTRING(acno,3,8)='" + tmpGLHead + "' and auth='Y' and DT <= '" + userDate + "'").getResultList();
                    } else if (orgBrnCode.equalsIgnoreCase("0B")) {
                        acGlSum = em.createNativeQuery("select ifnull(sum(cramt - dramt),0) from gl_recon where SUBSTRING(acno,3,8)='" + tmpGLHead
                                + "' and auth='Y' and DT <= '" + userDate + "' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                + " from branchmaster where parent_brncode = " + Integer.parseInt(nBrCode) + " union select right(concat('00',cast(brncode as char(2))),2) from "
                                + " branchmaster where brncode = " + Integer.parseInt(nBrCode)).getResultList();
                    } else {
                        acGlSum = em.createNativeQuery("select ifnull(sum(cramt - dramt),0) from gl_recon where SUBSTRING(acno,3,8)='" + tmpGLHead + "' and auth='Y' and DT <= '" + userDate + "' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
                    }

                    Vector element1 = (Vector) acGlSum.get(0);
                    //tmpGlBal = Double.parseDouble(element1.get(0).toString());
                    tmpGlBal = Double.valueOf(formatter.format(Double.parseDouble(element1.get(0).toString())));

                    for (int k = 0; k < glbLst.size(); k++) {
                        GlbTempActypeEntryPojo tmpPojo = glbLst.get(k);
                        String glH = tmpPojo.getGlHead();
                        String acTp = (tmpPojo.getAcType() != null ? tmpPojo.getAcType() : "");
                        String tp = (tmpPojo.getType() != null ? tmpPojo.getType() : "");
                        if ((glH.equalsIgnoreCase(tmpGLHead)) && (acTp.equalsIgnoreCase(tmpAcType)) && (tp.equalsIgnoreCase(tmpglbactype))) {
                            nCr = tmpPojo.getCramt();
                            nDr = tmpPojo.getDramt();

                            crTot = crTot.add(nCr);
                            drTot = drTot.add(nDr);
                        }
                    }

                    tmpTot = Double.valueOf(crTot.add(drTot).toString());
                    tmpGlBal = tmpGlBal + tmpTot;

                    if ((tmpAcType.length()) == 2) {
                        for (int k = 0; k < glbLst.size(); k++) {
                            GlbTempActypeEntryPojo tmpPojo = glbLst.get(k);
                            String glH = tmpPojo.getGlHead();
                            String acTp = (tmpPojo.getAcType() != null ? tmpPojo.getAcType() : "");
                            String tp = (tmpPojo.getType() != null ? tmpPojo.getType() : "");

                            if ((glH.equalsIgnoreCase(tmpGLHead)) && (acTp.equalsIgnoreCase(tmpAcType)) && (tp.equalsIgnoreCase(tmpglbactype))) {
                                if (tmpglbactype.equalsIgnoreCase("L")) {
                                    tmpPojo.setCramt(new BigDecimal(tmpGlBal));
                                } else if (tmpglbactype.equalsIgnoreCase("A")) {
                                    tmpPojo.setDramt(new BigDecimal(tmpGlBal));
                                }
                            }
                        }
                    } else {
                        if (tmpglbactype.equalsIgnoreCase("L")) {
                            if (tmpGLHead.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue())) {
                                tmpGlBal = IncomeExp(userDate, orgBrnCode, nBrCode);
                                if (tmpGlBal >= 0) {
                                    glbEntryPojo.setGlHead(tmpGLHead);
                                    glbEntryPojo.setCramt(new BigDecimal(tmpGlBal));
                                    glbEntryPojo.setDramt(new BigDecimal(0));
                                    glbEntryPojo.setAcStatus(Integer.parseInt(tmpAcstatus));
                                    glbEntryPojo.setType(tmpglbactype);
                                    glbEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                                    glbEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                                    glbEntryPojo.setDescription(tmpDesc);
                                    glbLst.add(glbEntryPojo);
                                }
                            } else {
                                glbEntryPojo.setGlHead(tmpGLHead);
                                glbEntryPojo.setAcStatus(Integer.parseInt(tmpAcstatus));
                                glbEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                                glbEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                                glbEntryPojo.setDescription(tmpDesc);
                                if (tmpGlBal < 0) {
                                    glbEntryPojo.setCramt(new BigDecimal(0));
                                    glbEntryPojo.setDramt(new BigDecimal(tmpGlBal));
                                    glbEntryPojo.setType("A");
                                    glbLst.add(glbEntryPojo);
                                } else if (tmpGlBal > 0) {
                                    glbEntryPojo.setCramt(new BigDecimal(tmpGlBal));
                                    glbEntryPojo.setDramt(new BigDecimal(0));
                                    glbEntryPojo.setType("L");
                                    glbLst.add(glbEntryPojo);
                                }

                            }
                        } else if (tmpglbactype.equalsIgnoreCase("A")) {
                            if (tmpGLHead.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue())) {
                                tmpGlBal = IncomeExp(userDate, orgBrnCode, nBrCode);
                                if (tmpGlBal < 0) {
                                    glbEntryPojo.setGlHead(tmpGLHead);
                                    glbEntryPojo.setCramt(new BigDecimal(0));
                                    glbEntryPojo.setDramt(new BigDecimal(tmpGlBal));
                                    glbEntryPojo.setAcStatus(Integer.parseInt(tmpAcstatus));
                                    glbEntryPojo.setType(tmpglbactype);
                                    glbEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                                    glbEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                                    glbEntryPojo.setDescription(tmpDesc);
                                    glbLst.add(glbEntryPojo);
                                }
                            } else if (tmpGLHead.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                tmpGlBal = CshInHandM(userDate, orgBrnCode, nBrCode);;

                                if (tmpGlBal < 0) {
                                    tmpGlBal = Math.abs(tmpGlBal);
                                } else {
                                    tmpGlBal = (tmpGlBal * -1);
                                }
                                glbEntryPojo.setGlHead(tmpGLHead);
                                glbEntryPojo.setCramt(new BigDecimal(0));
                                glbEntryPojo.setDramt(new BigDecimal(tmpGlBal));
                                glbEntryPojo.setAcStatus(Integer.parseInt(tmpAcstatus));
                                glbEntryPojo.setType(tmpglbactype);
                                glbEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                                glbEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                                glbEntryPojo.setDescription(tmpDesc);
                                glbLst.add(glbEntryPojo);
                            } else {
                                glbEntryPojo.setGlHead(tmpGLHead);
                                glbEntryPojo.setAcStatus(Integer.parseInt(tmpAcstatus));
                                glbEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                                glbEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                                glbEntryPojo.setDescription(tmpDesc);
                                if (tmpGlBal < 0) {
                                    glbEntryPojo.setCramt(new BigDecimal(0));
                                    glbEntryPojo.setDramt(new BigDecimal(tmpGlBal));
                                    glbEntryPojo.setType("A");
                                    glbLst.add(glbEntryPojo);
                                } else if (tmpGlBal > 0) {
                                    glbEntryPojo.setCramt(new BigDecimal(tmpGlBal));
                                    glbEntryPojo.setDramt(new BigDecimal(0));
                                    glbEntryPojo.setType("L");
                                    glbLst.add(glbEntryPojo);
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
        return glbLst;
    }

    public List<GlbTempActypeEntryPojo> getNewConsolidateGLBData(String userDate, String orgBrnCode, String nBrCode, String exCounterInclude) throws ApplicationException {
        String tmpAcType, tmpGLHead = "", tmpSGCode, tmpDesc, tmpGCode, tmpglbactype, tmpAcstatus, brLtotal;
        NumberFormat formatter = new DecimalFormat("#.##");

        List<GlbTempActypeEntryPojo> glbLst = new ArrayList<>();
        try {
            String brCodes = "";
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

            List<GlHeadPojo> osBlancelist = hoReport.getAsOnDateBalanceList(brCodes, userDate);
            double plBal = getPLBalance(brCodes, userDate, orgBrnCode);
            List acctNatLst = em.createNativeQuery("SELECT Sno,GroupCode,SubGroupCode,Code,Description,AcType,GlbActype,ActStatus,status,brncode from "
                    + "glbmast /*WHERE  ifnull(ACTYPE,'')<>''*/ order by groupcode").getResultList();

            if (!acctNatLst.isEmpty()) {
                for (int i = 0; i < acctNatLst.size(); i++) {
                    GlbTempActypeEntryPojo glbEntryPojo = new GlbTempActypeEntryPojo();
                    Vector element = (Vector) acctNatLst.get(i);

                    tmpAcType = element.get(5).toString();

                    tmpGLHead = (element.get(3) != null ? element.get(3).toString() : "").trim();
                    tmpGCode = (element.get(1) != null ? element.get(1).toString() : "0");
                    tmpSGCode = (element.get(2) != null ? element.get(2).toString() : "0");
                    tmpDesc = (element.get(4) != null ? element.get(4).toString() : "");
                    tmpglbactype = element.get(6).toString().trim();
                    tmpAcstatus = (element.get(7) != null ? element.get(7).toString() : "0");

                    double tmpBal = 0;

                    GlHeadPojo pojo = new GlHeadPojo();
                    /* AcType value */
                    if (!tmpAcType.equalsIgnoreCase("")) {
                        pojo = hoReport.getOSBalance(osBlancelist, tmpAcType, tmpglbactype);
                        if (pojo != null) {
                            tmpBal = Double.valueOf(formatter.format(pojo.getBalance()));
                        } else {
                            tmpBal = 0;
                        }
                    }

                    if (tmpGLHead.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue())) {
//                        tmpBal = tmpBal + IncomeExp(userDate, orgBrnCode, nBrCode);
                        if (plBal >= 0) {
                            tmpBal = plBal;
                        } else {
                            tmpBal = 0;
                        }
                    } else if (tmpGLHead.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue())) {
                        /* Profit & Loss */
//                        tmpBal = tmpBal + IncomeExp(userDate, orgBrnCode, nBrCode);
                        if (plBal < 0) {
                            tmpBal = plBal;
                        } else {
                            tmpBal = 0;
                        }
                    } else if (tmpGLHead.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                        /* Cast in hand */
                        pojo = hoReport.getOSBalance(osBlancelist, tmpGLHead, tmpglbactype);
                        if (pojo != null) {
                            tmpBal = tmpBal + Double.valueOf(formatter.format(pojo.getBalance()));
                        }
//                        tmpBal = tmpBal + CshInHandM(userDate, orgBrnCode, nBrCode);
//                        if (tmpBal < 0) {
//                            tmpBal = Math.abs(tmpBal);
//                        } else {
//                            tmpBal = (tmpBal * -1);
//                        }
                    } else {
                        /* GL head value */
                        pojo = hoReport.getOSBalance(osBlancelist, tmpGLHead, tmpglbactype);
                        if (pojo != null) {
                            tmpBal = tmpBal + Double.valueOf(formatter.format(pojo.getBalance()));
                        } else {
                            if (tmpglbactype.equalsIgnoreCase("L")) {
                                pojo = hoReport.getOSBalance(osBlancelist, tmpGLHead, "A");
                                if (pojo != null) {
                                    tmpBal = tmpBal + Double.valueOf(formatter.format(pojo.getBalance()));
                                } else {
                                    tmpBal = tmpBal + 0;
                                }
                            } else {
                                pojo = hoReport.getOSBalance(osBlancelist, tmpGLHead, "L");
                                if (pojo != null) {
                                    tmpBal = tmpBal + Double.valueOf(formatter.format(pojo.getBalance()));
                                } else {
                                    tmpBal = tmpBal + 0;
                                }
                            }
                        }
                    }
                    glbEntryPojo.setAcType(tmpAcType);
                    glbEntryPojo.setGlHead(tmpGLHead);
                    if (tmpglbactype.equalsIgnoreCase("L")) {
                        if (tmpBal >= 0) {
                            glbEntryPojo.setCramt(new BigDecimal(tmpBal));
                            glbEntryPojo.setDramt(new BigDecimal(0));
                            glbEntryPojo.setType(tmpglbactype);
                        } else {
                            glbEntryPojo.setCramt(new BigDecimal(0));
                            glbEntryPojo.setDramt(new BigDecimal(tmpBal));
                            glbEntryPojo.setType("A");
                        }
                    } else {
                        if (tmpBal > 0) {
                            glbEntryPojo.setCramt(new BigDecimal(tmpBal));
                            glbEntryPojo.setDramt(new BigDecimal(0));
                            glbEntryPojo.setType("L");
                        } else {
                            glbEntryPojo.setCramt(new BigDecimal(0));
                            glbEntryPojo.setDramt(new BigDecimal(tmpBal));
                            glbEntryPojo.setType(tmpglbactype);
                        }
                    }
                    glbEntryPojo.setAcStatus(Integer.parseInt(tmpAcstatus));
                    glbEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                    glbEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                    glbEntryPojo.setDescription(tmpDesc);
                    glbLst.add(glbEntryPojo);
//                     System.out.println("*****AcType is =" + tmpAcType+"; value:"+ tmpBal+"; A/L:"+tmpglbactype);
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return glbLst;
    }

    public double getPLBalance(String brCodes, String userDate, String mainBr) throws ApplicationException {
        try {
            String brnCode = mainBr.equals("0A") ? "90" : mainBr;
            List dataList = em.createNativeQuery("select ifnull(mindate,'') from yearend where mindate <= '" + userDate + "' and maxdate >= '"
                    + userDate + "' and brncode = '" + brnCode + "'").getResultList();
            String minDt = "";
            if (dataList.size() > 0) {
                Vector element1 = (Vector) dataList.get(0);
                minDt = (String) (element1.get(0) != null ? element1.get(0) : "");
            }
            String query = CbsUtil.getBrWisePLBalQuery(brCodes, minDt, userDate);
            System.out.println(query);
            List glResultList = em.createNativeQuery(query).getResultList();

            Vector vect = (Vector) glResultList.get(0);

            return Double.parseDouble(vect.get(0).toString());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public double IncomeExp(String userDate, String orgBrnCode, String nBrCode) throws ApplicationException {
        double profit = 0;
        try {
            if (orgBrnCode.equalsIgnoreCase("0A")) {
                List allBrn = em.createNativeQuery("SELECT right(concat('00',cast(brncode as char(2))),2) FROM branchmaster").getResultList();
                if (allBrn.isEmpty()) {
                    throw new ApplicationException("Data does not exist in Branch Master");
                }
                for (int i = 0; i < allBrn.size(); i++) {
                    Vector ele = (Vector) allBrn.get(i);
                    profit = profit + brWiseIncomeExp(userDate, ele.get(0).toString());

                }
            } else if (orgBrnCode.equalsIgnoreCase("0B")) {
                List allBrn = em.createNativeQuery("select right(concat('00',cast(brncode as char(2))),2) from branchmaster where parent_brncode = "
                        + " cast('" + nBrCode + "' as unsigned) union select right(concat('00',cast(brncode as char(2))),2) from branchmaster where brncode = cast('" + nBrCode + "' "
                        + " as unsigned) ").getResultList();
                if (allBrn.isEmpty()) {
                    throw new ApplicationException("Data does not exist in Branch Master");
                }
                for (int i = 0; i < allBrn.size(); i++) {
                    Vector ele = (Vector) allBrn.get(i);
                    profit = profit + brWiseIncomeExp(userDate, ele.get(0).toString());

                }
            } else {
                profit = brWiseIncomeExp(userDate, orgBrnCode);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return profit;
    }

    public double brWiseIncomeExp(String userDate, String orgBrnCode) throws ApplicationException {
        List dataList1;
        String tempMinDate1 = "", tempMinDate = "";
        double glCrTotal = 0, glDrTotal = 0, glTotal = 0, incomeTot, expTot, profit = 0;
        try {
            dataList1 = em.createNativeQuery("select min(mindate) from yearend where yearendflag='N' AND brncode = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                tempMinDate = (String) (element1.get(0) != null ? element1.get(0) : "");
            }
            dataList1 = em.createNativeQuery("select ifnull(mindate,'') from yearend where mindate <= '" + userDate + "' and maxdate >= '"
                    + userDate + "' and brncode = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                tempMinDate1 = (String) (element1.get(0) != null ? element1.get(0) : "");
            }
            if (tempMinDate1.equals("")) {
                tempMinDate1 = tempMinDate;
            } else if (tempMinDate1 == null) {
                tempMinDate1 = tempMinDate;
            }
            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
                    + "where substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_END.getValue() + "' AND "
                    + "dt<='" + userDate + "'  AND dt>='" + tempMinDate1 + "' "
                    + "and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                glCrTotal = Double.parseDouble(element1.get(0).toString());
                glDrTotal = Double.parseDouble(element1.get(1).toString());
            }
            glTotal = glCrTotal - glDrTotal;
            incomeTot = glTotal;

            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from gl_recon "
                    + "where substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue() + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' AND "
                    + "dt<='" + userDate + "'  AND dt>='" + tempMinDate1 + "' "
                    + "and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                glTotal = Double.parseDouble(element1.get(0).toString());
            }
            expTot = glTotal;

            if (expTot > 0) {
                profit = incomeTot + expTot;
            } else {
                profit = incomeTot - Math.abs(expTot);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return profit;
    }

    public double CshInHandM(String userDate, String orgBrnCode, String nBrCode) throws ApplicationException {
        double cashInHand = 0;
        try {
            if (orgBrnCode.equalsIgnoreCase("0A")) {
                List allBrn = em.createNativeQuery("SELECT right(concat('00',cast(brncode as char(2))),2) FROM branchmaster").getResultList();
                if (allBrn.isEmpty()) {
                    throw new ApplicationException("Data does not exist in Branch Master");
                }
                for (int i = 0; i < allBrn.size(); i++) {
                    Vector ele = (Vector) allBrn.get(i);
                    cashInHand = cashInHand + brWiseCashInHand(userDate, ele.get(0).toString());
                }
            } else if (orgBrnCode.equalsIgnoreCase("0B")) {
                List allBrn = em.createNativeQuery("select right(concat('00',cast(brncode as char(2))),2) from branchmaster where parent_brncode = "
                        + " cast('" + nBrCode + "' as unsigned) union select right(concat('00',cast(brncode as char(2))),2) from branchmaster where brncode = cast('" + nBrCode + "' "
                        + " as unsigned) ").getResultList();
                if (allBrn.isEmpty()) {
                    throw new ApplicationException("Data does not exist in Branch Master");
                }
                for (int i = 0; i < allBrn.size(); i++) {
                    Vector ele = (Vector) allBrn.get(i);
                    cashInHand = cashInHand + brWiseCashInHand(userDate, ele.get(0).toString());
                }
            } else {
                cashInHand = brWiseCashInHand(userDate, orgBrnCode);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return cashInHand;
    }

    private double brWiseCashInHand(String userDate, String orgBrnCode) throws ApplicationException {
        double balOp = 0, crTotal, drTotal, glTotal = 0, cashTotal = 0, extTotal = 0, grandTotal = 0, cashInHand = 0;
        String date1 = "", actNature;
        List dataList1;
        try {
            dataList1 = em.createNativeQuery("SELECT date_format(MAX(ldate),'%Y%m%d') FROM cashinhand WHERE ldate<'" + userDate + "' "
                    + "AND brncode = '" + orgBrnCode + "'").getResultList();
            for (Object object : dataList1) {
                Vector element1 = (Vector) object;
                date1 = (String) (element1.get(0) != null ? element1.get(0) : "");
            }

            dataList1 = em.createNativeQuery("SELECT amt FROM cashinhand WHERE ldate='" + date1 + "'  AND brncode = '" + orgBrnCode + "'").getResultList();
            for (Object object : dataList1) {
                Vector element1 = (Vector) object;
                balOp = Double.parseDouble(element1.get(0).toString());
            }
            dataList1 = em.createNativeQuery("select distinct acctNature from accounttypemaster where acctnature not in "
                    + "('" + CbsConstant.MS_AC + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.SS_AC + "','" + CbsConstant.NON_PERFORM_AC + "')  ORDER BY acctNature").getResultList();
            List dataList2 = new ArrayList();
            String tableName;
            for (Object object : dataList1) {
                Vector element1 = (Vector) object;
                actNature = (String) element1.get(0);
                crTotal = 0;
                drTotal = 0;
                if (actNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    dataList2 = em.createNativeQuery("SELECT ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
                            + "where  dt='" + userDate + "' and trantype=0 and auth='Y'AND IY NOT IN(9999) "
                            + "AND SUBSTRING(ACNO,1,2)= '" + orgBrnCode + "' and substring(ACNO,3,10) not in "
                            + "(select distinct acno from abb_parameter_info where purpose like '%CASH IN HAND%') "
                            + "AND IY NOT IN(9999)").getResultList();
                } else {
                    tableName = getTableName(actNature);
                    if (tableName.equalsIgnoreCase("td_recon")) {
                        dataList2 = em.createNativeQuery("SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from td_recon r,"
                                + "td_accountmaster am  where r.dt='" + userDate + "' and r.trantype=0 and r.auth='Y' AND r.IY NOT IN(9999) "
                                + "AND  am.acno = r.acno and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
                    } else {
                        dataList2 = em.createNativeQuery(" SELECT ifnull(sum(r.cramt),0),ifnull(sum(r.dramt),0) from " + tableName + " r,"
                                + "accountmaster am  where r.dt='" + userDate + "' and r.trantype=0 and r.auth='Y' AND r.IY NOT IN(9999) "
                                + "AND  am.acno = r.acno and am.CurBrCode = '" + orgBrnCode + "'").getResultList();
                    }
                }
                if (dataList2.size() > 0) {
                    Vector element2 = (Vector) dataList2.get(0);
                    crTotal = Double.parseDouble(element2.get(0).toString());
                    drTotal = Double.parseDouble(element2.get(1).toString());
                }
                glTotal = crTotal - drTotal;
                cashTotal = cashTotal + glTotal;

            }

            crTotal = 0;
            drTotal = 0;
//            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                    + "where substring(acno,3,8)='" + SiplConstant.CASH_IN_EXT.getValue()+ "' and dt='" + userDate + "' and auth='Y' "
//                    + "AND SUBSTRING(ACNO,1,2)= '" + orgBrnCode + "'").getResultList();
            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
                    + "where substring(acno,3,8)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_OTH.getValue() + "' and dt='" + userDate + "' and auth='Y' "
                    + "AND SUBSTRING(ACNO,1,2)= '" + orgBrnCode + "'").getResultList();
            for (Object object : dataList1) {
                Vector element1 = (Vector) object;
                crTotal = Double.parseDouble(element1.get(0).toString());
                drTotal = Double.parseDouble(element1.get(1).toString());
            }
            extTotal = crTotal - drTotal;
            grandTotal = balOp + cashTotal + extTotal;
            cashInHand = grandTotal;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return cashInHand;
    }

    public List<ConsolidateProfitLossPojo> getConsolidateProfitLoss(String userDate, String orgBrnCode, String selectOption, String flag, String exCounterInclude) throws ApplicationException {
        List<ConsolidateProfitLossPojo> dataList = new ArrayList<ConsolidateProfitLossPojo>();
        List profitLossList1 = new ArrayList();
        List plList = new ArrayList();
        String hfDt = "20131001";
        try {
            String brCodes = "";
            orgBrnCode = orgBrnCode.equals("0A") ? "A" : orgBrnCode;
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
            if (orgBrnCode.equalsIgnoreCase("A")) {
                if (flag.equals("0")) {
                    if (selectOption.equalsIgnoreCase("0")) {
                        profitLossList1 = em.createNativeQuery("select alldata.bal, alldata.branchname,substring(alldata.acno,3,8)  from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,acno,b.BranchName "
                                + " FROM gl_recon r,branchmaster b,cbs_yearend c WHERE auth='Y'  AND r.trantype <> 27 AND r.trandesc<>13 "
                                + " and Substring(r.acno,1,2) = b.brncode and r.dt >=c.mindate and r.dt<='" + userDate + "' and c.mindate<='"
                                + userDate + "' and c.maxdate >='" + userDate + "' "
                                + " and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by r.acno,b.BranchName) alldata order by substring(alldata.acno,3,8)").getResultList();
                    } else {
                        profitLossList1 = em.createNativeQuery("select alldata.bal, alldata.branchname,substring(alldata.acno,3,8)  from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,acno,"
                                + " b.BranchName FROM gl_recon r,branchmaster b,cbs_yearend c WHERE auth='Y' AND r.trantype <> 27 "
                                + " and Substring(r.acno,1,2) = b.brncode and r.dt >=c.mindate and r.dt<='" + userDate + "' and c.mindate<='"
                                + userDate + "' and c.maxdate >='" + userDate + "' and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by r.acno,b.BranchName) alldata order by substring(alldata.acno,3,8) ").getResultList();
                    }
                } else {
                    if (selectOption.equalsIgnoreCase("2")) {
                        profitLossList1 = em.createNativeQuery("select alldata.bal, alldata.branchname,substring(alldata.acno,3,8)  from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,acno,b.BranchName "
                                + " FROM gl_recon r,branchmaster b,cbs_yearend c WHERE auth='Y'  AND r.trantype <> 27 AND r.trandesc<>13 "
                                + " and Substring(r.acno,1,2) = b.brncode and r.dt >=c.mindate and r.dt<='" + userDate + "' and c.mindate<='"
                                + userDate + "' and c.maxdate >='" + userDate + "' "
                                + " and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by r.acno,b.BranchName) alldata order by substring(alldata.acno,3,8)").getResultList();
                    } else if (selectOption.equalsIgnoreCase("3")) {
                        profitLossList1 = em.createNativeQuery("select alldata.bal,alldata.branchname,substring(alldata.acno,3,8) from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,r.acno,b.BranchName "
                                + " FROM gl_recon r,branchmaster b WHERE auth='Y' AND r.trantype <> 27 AND Substring(r.acno,1,2) = b.brncode "
                                + " and dt>='" + hfDt + "' AND dt<='" + userDate + "' "
                                + " and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by r.acno,b.BranchName) alldata order by substring(alldata.acno,3,8)").getResultList();
                    } else if (selectOption.equalsIgnoreCase("0")) {
                        profitLossList1 = em.createNativeQuery("select alldata.bal,alldata.branchname,substring(alldata.acno,3,8) from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,r.acno,b.BranchName "
                                + " FROM gl_recon r,branchmaster b WHERE auth='Y' AND r.trantype <> 27 AND r.trandesc<>13 AND Substring(r.acno,1,2) = b.brncode "
                                + " and dt>='" + hfDt + "' AND dt<='" + userDate + "' "
                                + " and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by r.acno,b.BranchName) alldata order by substring(alldata.acno,3,8)").getResultList();
                    } else {
                        profitLossList1 = em.createNativeQuery("select alldata.bal, alldata.branchname,substring(alldata.acno,3,8)  from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,acno,"
                                + " b.BranchName FROM gl_recon r,branchmaster b,cbs_yearend c WHERE auth='Y' AND r.trantype <> 27 "
                                + " and Substring(r.acno,1,2) = b.brncode and r.dt >=c.mindate and r.dt<='" + userDate + "' and c.mindate<='"
                                + userDate + "' and c.maxdate >='" + userDate + "' and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by r.acno,b.BranchName) alldata order by substring(alldata.acno,3,8)").getResultList();
                    }
                }
                plList = em.createNativeQuery("SELECT Sno,GroupCode,SubGroupCode,Code,Description,Classification,MODE from plmast order by groupcode ").getResultList();
                if (!plList.isEmpty()) {
                    for (int j = 0; j < plList.size(); j++) {
                        String tmpClass, tmpGLHead = "", tmpSGCode, tmpDesc, tmpGCode, tmpMODE;
                        double tmpGlBal = 0;
                        Vector element = (Vector) plList.get(j);
                        tmpGCode = (element.get(1) != null ? element.get(1).toString() : "0");
                        tmpSGCode = (element.get(2) != null ? element.get(2).toString() : "0");
                        tmpGLHead = (element.get(3) != null ? element.get(3).toString() : "");
                        tmpDesc = (element.get(4) != null ? element.get(4).toString() : "");
                        tmpClass = element.get(5).toString();
                        tmpMODE = element.get(6).toString();
                        for (int k = 0; k < profitLossList1.size(); k++) {
                            Vector element1 = (Vector) profitLossList1.get(k);
                            tmpGlBal = Double.parseDouble(element1.get(0).toString());

                            if (element1.get(2).toString().equals(tmpGLHead) && tmpGlBal != 0) {
                                String brName = element1.get(1).toString();

                                ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
                                consEntryPojo.setAcno(tmpGLHead);
                                consEntryPojo.setAcName(tmpDesc);

                                consEntryPojo.setAmount(new BigDecimal(tmpGlBal));
                                consEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                                consEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                                consEntryPojo.setType(tmpClass);
                                consEntryPojo.setBankName(brName);
                                dataList.add(consEntryPojo);
                            }
                        }
                    }
                }

                List<String> tmpLst = new ArrayList<String>();
                for (int l = 0; l < dataList.size(); l++) {
                    String bName = dataList.get(l).getBankName();
                    if (!tmpLst.contains(bName)) {
                        tmpLst.add(bName);
                    }
                }

                for (int k = 0; k < tmpLst.size(); k++) {
                    String bName = tmpLst.get(k).toString();
                    double totGlBal = 0;
                    for (int m = 0; m < dataList.size(); m++) {
                        if (bName.equals(dataList.get(m).getBankName())) {
                            double tmpGlBal = Double.parseDouble(dataList.get(m).getAmount().toString());
                            totGlBal = totGlBal + tmpGlBal;
                        }
                    }

                    if (totGlBal != 0) {
                        if (totGlBal < 0) {
                            ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
                            consEntryPojo.setAcno("");
                            consEntryPojo.setAcName("LOSS");
                            consEntryPojo.setAmount(new BigDecimal(totGlBal));
                            consEntryPojo.setGroupCode(Integer.parseInt("9999"));
                            consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
                            consEntryPojo.setType("I");
                            consEntryPojo.setBankName(bName);
                            dataList.add(consEntryPojo);
                        } else if (totGlBal > 0) {
                            ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
                            consEntryPojo.setAcno("");
                            consEntryPojo.setAcName("PROFIT");
                            consEntryPojo.setAmount(new BigDecimal(totGlBal));
                            consEntryPojo.setGroupCode(Integer.parseInt("9999"));
                            consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
                            consEntryPojo.setType("E");
                            consEntryPojo.setBankName(bName);
                            dataList.add(consEntryPojo);
                        }
                        if (totGlBal < 0) {
                            ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
                            consEntryPojo.setAcno("");
                            consEntryPojo.setAcName("PROFIT");
                            consEntryPojo.setAmount(new BigDecimal(0));
                            consEntryPojo.setGroupCode(Integer.parseInt("9999"));
                            consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
                            consEntryPojo.setType("E");
                            consEntryPojo.setBankName(bName);
                            dataList.add(consEntryPojo);
                        } else if (totGlBal > 0) {
                            ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
                            consEntryPojo.setAcno("");
                            consEntryPojo.setAcName("LOSS");
                            consEntryPojo.setAmount(new BigDecimal(0));
                            consEntryPojo.setGroupCode(Integer.parseInt("9999"));
                            consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
                            consEntryPojo.setType("I");
                            consEntryPojo.setBankName(bName);
                            dataList.add(consEntryPojo);
                        }
                    }
                }
            } else if (orgBrnCode.equalsIgnoreCase("CA")) {
                if (flag.equals("0")) {
                    if (selectOption.equalsIgnoreCase("0")) {
                        profitLossList1 = em.createNativeQuery("select alldata.bal, alldata.acno from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,substring(r.acno,3,8) as acno "
                                + " FROM gl_recon r,cbs_yearend c WHERE auth='Y'  AND r.trantype <> 27 AND r.trandesc<>13 "
                                + " and r.dt >=c.mindate and r.dt<='" + userDate + "' and c.mindate<='"
                                + userDate + "' and c.maxdate >='" + userDate + "' "
                                + " and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by substring(r.acno,3,8)) alldata order by alldata.acno").getResultList();
                    } else {
                        profitLossList1 = em.createNativeQuery("select alldata.bal, alldata.acno from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,substring(r.acno,3,8) as acno"
                                + " FROM gl_recon r,cbs_yearend c WHERE auth='Y' AND r.trantype <> 27 "
                                + " and r.dt >=c.mindate and r.dt<='" + userDate + "' and c.mindate<='"
                                + userDate + "' and c.maxdate >='" + userDate + "' and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by substring(r.acno,3,8)) alldata order by alldata.acno").getResultList();
                    }
                } else {
                    if (selectOption.equalsIgnoreCase("2")) {
                        profitLossList1 = em.createNativeQuery("select alldata.bal, alldata.acno from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,substring(r.acno,3,8) as acno "
                                + " FROM gl_recon r,cbs_yearend c WHERE auth='Y'  AND r.trantype <> 27 AND r.trandesc<>13 "
                                + " and r.dt >=c.mindate and r.dt<='" + userDate + "' and c.mindate<='"
                                + userDate + "' and c.maxdate >='" + userDate + "' "
                                + " and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by substring(r.acno,3,8)) alldata order by alldata.acno").getResultList();
                    } else if (selectOption.equalsIgnoreCase("3")) {
                        profitLossList1 = em.createNativeQuery("select alldata.bal,alldata.acno from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,substring(r.acno,3,8) as acno  "
                                + " FROM gl_recon r WHERE auth='Y' AND r.trantype <> 27 "
                                + " and dt>='" + hfDt + "' AND dt<='" + userDate + "' "
                                + " and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by substring(r.acno,3,8)) alldata order by alldata.acno").getResultList();
                    } else if (selectOption.equalsIgnoreCase("0")) {
                        profitLossList1 = em.createNativeQuery("select alldata.bal,alldata.acno from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,substring(r.acno,3,8) as acno  "
                                + " FROM gl_recon r WHERE auth='Y' AND r.trantype <> 27 AND r.trandesc<>13 "
                                + " and dt>='" + hfDt + "' AND dt<='" + userDate + "' "
                                + " and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by substring(r.acno,3,8)) alldata order by alldata.acno").getResultList();
                    } else {
                        profitLossList1 = em.createNativeQuery("select alldata.bal, alldata.acno from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,substring(r.acno,3,8) as acno "
                                + " FROM gl_recon r,cbs_yearend c WHERE auth='Y' AND r.trantype <> 27 "
                                + " and r.dt >=c.mindate and r.dt<='" + userDate + "' and c.mindate<='"
                                + userDate + "' and c.maxdate >='" + userDate + "' and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by substring(r.acno,3,8)) alldata order by alldata.acno").getResultList();
                    }
                }
                double totGlBal = 0;
                if (!profitLossList1.isEmpty()) {
                    plList = em.createNativeQuery("SELECT Sno,GroupCode,SubGroupCode,Code,Description,Classification,MODE from plmast order by groupcode ").getResultList();
                    if (!plList.isEmpty()) {
                        for (int j = 0; j < plList.size(); j++) {
                            double tmpGlBal = 0;
                            String tmpClass, tmpGLHead = "", tmpSGCode, tmpDesc, tmpGCode, tmpMODE;
                            Vector element = (Vector) plList.get(j);

                            tmpGCode = (element.get(1) != null ? element.get(1).toString() : "0");
                            tmpSGCode = (element.get(2) != null ? element.get(2).toString() : "0");
                            tmpGLHead = (element.get(3) != null ? element.get(3).toString() : "");
                            tmpDesc = (element.get(4) != null ? element.get(4).toString() : "");
                            tmpClass = element.get(5).toString();
                            tmpMODE = element.get(6).toString();
                            for (int k = 0; k < profitLossList1.size(); k++) {
                                Vector element1 = (Vector) profitLossList1.get(k);
                                tmpGlBal = Double.parseDouble(element1.get(0).toString());

                                if (element1.get(1).toString().equals(tmpGLHead) && tmpGlBal != 0) {
                                    totGlBal = totGlBal + tmpGlBal;

                                    ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
                                    consEntryPojo.setAcno(tmpGLHead);
                                    consEntryPojo.setAcName(tmpDesc);

                                    consEntryPojo.setAmount(new BigDecimal(tmpGlBal));
                                    consEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                                    consEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                                    consEntryPojo.setType(tmpClass);
                                    dataList.add(consEntryPojo);
                                }
                            }
                        }

                        if (totGlBal != 0) {
                            if (totGlBal < 0) {
                                ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
                                consEntryPojo.setAcno("");
                                consEntryPojo.setAcName("LOSS");
                                consEntryPojo.setAmount(new BigDecimal(totGlBal));
                                consEntryPojo.setGroupCode(Integer.parseInt("9999"));
                                consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
                                consEntryPojo.setType("I");
                                dataList.add(consEntryPojo);
                            } else if (totGlBal > 0) {
                                ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
                                consEntryPojo.setAcno("");
                                consEntryPojo.setAcName("PROFIT");
                                consEntryPojo.setAmount(new BigDecimal(totGlBal));
                                consEntryPojo.setGroupCode(Integer.parseInt("9999"));
                                consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
                                consEntryPojo.setType("E");
                                dataList.add(consEntryPojo);
                            }
                        }
                    }
                }
            } else {
                double totGlBal = 0d;
                orgBrnCode = CbsUtil.lPadding(2, Integer.parseInt(orgBrnCode));
                if (flag.equals("0")) {
                    if (selectOption.equalsIgnoreCase("0")) {
                        profitLossList1 = em.createNativeQuery("select alldata.bal, alldata.branchname,substring(alldata.acno,3,8)  from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,acno,b.BranchName "
                                + " FROM gl_recon r,branchmaster b,cbs_yearend c WHERE auth='Y'  AND r.trantype <> 27 AND r.trandesc<>13 "
                                + " AND Substring(r.acno,1,2) in(" + brCodes + ") and Substring(r.acno,1,2) = b.brncode and r.dt >=c.mindate and r.dt<='" + userDate + "' and c.mindate<='"
                                + userDate + "' and c.maxdate >='" + userDate + "' "
                                + " and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by r.acno,b.BranchName) alldata order by substring(alldata.acno,3,8)").getResultList();
                    } else {
                        profitLossList1 = em.createNativeQuery("select alldata.bal, alldata.branchname,substring(alldata.acno,3,8)  from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,acno,"
                                + " b.BranchName FROM gl_recon r,branchmaster b,cbs_yearend c WHERE auth='Y' AND r.trantype <> 27 "
                                + " AND Substring(r.acno,1,2) in(" + brCodes + ") and Substring(r.acno,1,2) = b.brncode and r.dt >=c.mindate and r.dt<='" + userDate + "' and c.mindate<='"
                                + userDate + "' and c.maxdate >='" + userDate + "' and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by r.acno,b.BranchName) alldata order by substring(alldata.acno,3,8) ").getResultList();
                    }
                } else {
                    if (selectOption.equalsIgnoreCase("2")) {
                        profitLossList1 = em.createNativeQuery("select alldata.bal, alldata.branchname,substring(alldata.acno,3,8)  from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,acno,b.BranchName "
                                + " FROM gl_recon r,branchmaster b,cbs_yearend c WHERE auth='Y'  AND r.trantype <> 27 AND r.trandesc<>13 "
                                + " AND Substring(r.acno,1,2) in(" + brCodes + ") and Substring(r.acno,1,2) = b.brncode and r.dt >=c.mindate and r.dt<='" + userDate + "' and c.mindate<='"
                                + userDate + "' and c.maxdate >='" + userDate + "' "
                                + " and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by r.acno,b.BranchName) alldata order by substring(alldata.acno,3,8)").getResultList();
                    } else if (selectOption.equalsIgnoreCase("3")) {
                        profitLossList1 = em.createNativeQuery("select alldata.bal,alldata.branchname,substring(alldata.acno,3,8) from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,r.acno,b.BranchName "
                                + " FROM gl_recon r,branchmaster b WHERE auth='Y' AND r.trantype <> 27 AND Substring(r.acno,1,2) in(" + brCodes + ") AND Substring(r.acno,1,2) = b.brncode "
                                + " and dt>='" + hfDt + "' AND dt<='" + userDate + "' "
                                + " and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by r.acno,b.BranchName) alldata order by substring(alldata.acno,3,8)").getResultList();
                    } else if (selectOption.equalsIgnoreCase("0")) {
                        profitLossList1 = em.createNativeQuery("select alldata.bal,alldata.branchname,substring(alldata.acno,3,8) from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,r.acno,b.BranchName "
                                + " FROM gl_recon r,branchmaster b WHERE auth='Y' AND r.trantype <> 27 AND r.trandesc<>13 AND Substring(r.acno,1,2) in(" + brCodes + ") AND Substring(r.acno,1,2) = b.brncode "
                                + " and dt>='" + hfDt + "' AND dt<='" + userDate + "' "
                                + " and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by r.acno,b.BranchName) alldata order by substring(alldata.acno,3,8)").getResultList();
                    } else {
                        profitLossList1 = em.createNativeQuery("select alldata.bal, alldata.branchname,substring(alldata.acno,3,8)  from "
                                + " (SELECT ifnull((SUM(cramt)-SUM(dramt)),0) as bal,acno,"
                                + " b.BranchName FROM gl_recon r,branchmaster b,cbs_yearend c WHERE auth='Y' AND r.trantype <> 27 "
                                + " AND Substring(r.acno,1,2) in(" + brCodes + ") and Substring(r.acno,1,2) = b.brncode and r.dt >=c.mindate and r.dt<='" + userDate + "' and c.mindate<='"
                                + userDate + "' and c.maxdate >='" + userDate + "' and substring(r.acno,3,8) >='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' "
                                + " and substring(r.acno,3,8) <='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' "
                                + " group by r.acno,b.BranchName) alldata order by substring(alldata.acno,3,8)").getResultList();
                    }
                }

                String brName = "";
                if (!profitLossList1.isEmpty()) {
                    plList = em.createNativeQuery("SELECT Sno,GroupCode,SubGroupCode,Code,Description,Classification,MODE from plmast order by groupcode ").getResultList();
                    if (!plList.isEmpty()) {
                        for (int j = 0; j < plList.size(); j++) {
                            double tmpGlBal = 0;
                            String tmpClass, tmpGLHead = "", tmpSGCode, tmpDesc, tmpGCode, tmpMODE;
                            Vector element = (Vector) plList.get(j);

                            tmpGCode = (element.get(1) != null ? element.get(1).toString() : "0");
                            tmpSGCode = (element.get(2) != null ? element.get(2).toString() : "0");
                            tmpGLHead = (element.get(3) != null ? element.get(3).toString() : "");
                            tmpDesc = (element.get(4) != null ? element.get(4).toString() : "");
                            tmpClass = element.get(5).toString();
                            tmpMODE = element.get(6).toString();
                            for (int k = 0; k < profitLossList1.size(); k++) {
                                Vector element1 = (Vector) profitLossList1.get(k);
                                tmpGlBal = Double.parseDouble(element1.get(0).toString());

                                if (element1.get(2).toString().equals(tmpGLHead) && tmpGlBal != 0) {
                                    totGlBal = totGlBal + tmpGlBal;
                                    brName = element1.get(1).toString();

                                    ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
                                    consEntryPojo.setAcno(tmpGLHead);
                                    consEntryPojo.setAcName(tmpDesc);

                                    consEntryPojo.setAmount(new BigDecimal(tmpGlBal));
                                    consEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                                    consEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                                    consEntryPojo.setType(tmpClass);
                                    consEntryPojo.setBankName(brName);
                                    dataList.add(consEntryPojo);
                                }
                            }
                        }
                        if (totGlBal != 0) {
                            if (totGlBal < 0) {
                                ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
                                consEntryPojo.setAcno("");
                                consEntryPojo.setAcName("LOSS");
                                consEntryPojo.setAmount(new BigDecimal(totGlBal));
                                consEntryPojo.setGroupCode(Integer.parseInt("9999"));
                                consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
                                consEntryPojo.setType("I");
                                consEntryPojo.setBankName(brName);
                                dataList.add(consEntryPojo);
                            } else if (totGlBal > 0) {
                                ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
                                consEntryPojo.setAcno("");
                                consEntryPojo.setAcName("PROFIT");
                                consEntryPojo.setAmount(new BigDecimal(totGlBal));
                                consEntryPojo.setGroupCode(Integer.parseInt("9999"));
                                consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
                                consEntryPojo.setType("E");
                                consEntryPojo.setBankName(brName);
                                dataList.add(consEntryPojo);
                            }
                        }

                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return dataList;
    }
//    public List<ConsolidateProfitLossPojo> getConsolidateProfitLoss(String userDate, String orgBrnCode, String selectOption, String flag) throws ApplicationException {
//        List<ConsolidateProfitLossPojo> dataList = new ArrayList<ConsolidateProfitLossPojo>();
//        List profitLossList1 = new ArrayList();
//        List plList = new ArrayList();
//        String hfDt = "20131001";
//        try {
//            if (orgBrnCode.equalsIgnoreCase("A")) {
//                List allBrn = em.createNativeQuery("SELECT right(concat('00',cast(brncode as char(2))),2) AS BRNCODE,BranchName FROM branchmaster").getResultList();
//                if (allBrn.isEmpty()) {
//                    throw new ApplicationException("Data does not exist in Branch Master");
//                }
//                for (int i = 0; i < allBrn.size(); i++) {
//                    double totGlBal = 0;
//                    String minDate = "", maxDate;
//                    Vector ele = (Vector) allBrn.get(i);
//                    String brCode = ele.get(0).toString();
//                    String brName = ele.get(1).toString();
//                    List yearEndList = em.createNativeQuery("SELECT mindate,maxdate from cbs_yearend where mindate<='" + userDate + "' and maxdate>='" + userDate + "'").getResultList();
//                    if (yearEndList.size() > 0) {
//                        Vector vectorYearEnd = (Vector) yearEndList.get(0);
//                        minDate = (String) vectorYearEnd.get(0);
//                        maxDate = (String) vectorYearEnd.get(1);
//                    }
//
//                    plList = em.createNativeQuery("SELECT Sno,GroupCode,SubGroupCode,Code,Description,Classification,MODE from plmast order by groupcode ").getResultList();
//                    if (!plList.isEmpty()) {
//                        for (int j = 0; j < plList.size(); j++) {
//                            String tmpClass, tmpGLHead = "", tmpSGCode, tmpDesc, tmpGCode, tmpMODE;
//                            double tmpGlBal = 0;
//                            Vector element = (Vector) plList.get(j);
//                            tmpGCode = (element.get(1) != null ? element.get(1).toString() : "0");
//                            tmpSGCode = (element.get(2) != null ? element.get(2).toString() : "0");
//                            tmpGLHead = (element.get(3) != null ? element.get(3).toString() : "");
//                            tmpDesc = (element.get(4) != null ? element.get(4).toString() : "");
//                            tmpClass = element.get(5).toString();
//                            tmpMODE = element.get(6).toString();
//                            if (flag.equals("0")) {
//                                if (selectOption.equalsIgnoreCase("0")) {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt)-SUM(dramt)),0) "
//                                            + "FROM gl_recon r WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND r.trandesc<>13 AND Substring(r.acno,1,2) = '" + brCode + "'").getResultList();
//                                } else {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt))-SUM((dramt)),0) "
//                                            + "FROM gl_recon r,gltable g WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND Substring(r.acno,1,2) = '" + brCode + "'").getResultList();
//                                }
//                            } else {
//                                if (selectOption.equalsIgnoreCase("2")) {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt)-SUM(dramt)),0) "
//                                            + "FROM gl_recon r WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND r.trandesc<>13 AND Substring(r.acno,1,2) = '" + brCode + "'").getResultList();
//                                } else if (selectOption.equalsIgnoreCase("3")) {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt))-SUM((dramt)),0) "
//                                            + "FROM gl_recon r,gltable g WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + hfDt + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND Substring(r.acno,1,2) = '" + brCode + "'").getResultList();
//                                } else if (selectOption.equalsIgnoreCase("0")) {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt)-SUM(dramt)),0) "
//                                            + "FROM gl_recon r WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + hfDt + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND r.trandesc<>13 AND Substring(r.acno,1,2) = '" + brCode + "'").getResultList();
//                                } else {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt))-SUM((dramt)),0) "
//                                            + "FROM gl_recon r,gltable g WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND Substring(r.acno,1,2) = '" + brCode + "'").getResultList();
//                                }
//                            }
//
//                            Vector element1 = (Vector) profitLossList1.get(0);
//                            tmpGlBal = Double.parseDouble(element1.get(0).toString());
//
//                            totGlBal = totGlBal + tmpGlBal;
//
//                            if (profitLossList1.size() > 0 && tmpGlBal != 0) {
//                                ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
//                                consEntryPojo.setAcno(tmpGLHead);
//                                consEntryPojo.setAcName(tmpDesc);
//                                consEntryPojo.setAmount(new BigDecimal(tmpGlBal));
//                                consEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
//                                consEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
//                                consEntryPojo.setType(tmpClass);
//                                consEntryPojo.setBankName(brName);
//                                dataList.add(consEntryPojo);
//                            }
//                        }
//                    }
//
//                    if (totGlBal != 0) {
//                        if (totGlBal < 0) {
//                            ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
//                            consEntryPojo.setAcno("");
//                            consEntryPojo.setAcName("LOSS");
//                            consEntryPojo.setAmount(new BigDecimal(totGlBal));
//                            consEntryPojo.setGroupCode(Integer.parseInt("9999"));
//                            consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
//                            consEntryPojo.setType("I");
//                            consEntryPojo.setBankName(brName);
//                            dataList.add(consEntryPojo);
//                        } else if (totGlBal > 0) {
//                            ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
//                            consEntryPojo.setAcno("");
//                            consEntryPojo.setAcName("PROFIT");
//                            consEntryPojo.setAmount(new BigDecimal(totGlBal));
//                            consEntryPojo.setGroupCode(Integer.parseInt("9999"));
//                            consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
//                            consEntryPojo.setType("E");
//                            consEntryPojo.setBankName(brName);
//                            dataList.add(consEntryPojo);
//                        }
//                        if (totGlBal < 0) {
//                            ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
//                            consEntryPojo.setAcno("");
//                            consEntryPojo.setAcName("PROFIT");
//                            consEntryPojo.setAmount(new BigDecimal(0));
//                            consEntryPojo.setGroupCode(Integer.parseInt("9999"));
//                            consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
//                            consEntryPojo.setType("E");
//                            consEntryPojo.setBankName(brName);
//                            dataList.add(consEntryPojo);
//                        } else if (totGlBal > 0) {
//                            ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
//                            consEntryPojo.setAcno("");
//                            consEntryPojo.setAcName("LOSS");
//                            consEntryPojo.setAmount(new BigDecimal(0));
//                            consEntryPojo.setGroupCode(Integer.parseInt("9999"));
//                            consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
//                            consEntryPojo.setType("I");
//                            consEntryPojo.setBankName(brName);
//                            dataList.add(consEntryPojo);
//                        }
//                    }
//                }
//            } else if (orgBrnCode.equalsIgnoreCase("CA")) {
//                String minDate = "", maxDate;
//                double totGlBal = 0;
//                List yearEndList = em.createNativeQuery("SELECT mindate,maxdate from cbs_yearend where mindate<='" + userDate + "' and maxdate>='" + userDate + "'").getResultList();
//                if (yearEndList.size() > 0) {
//                    Vector vectorYearEnd = (Vector) yearEndList.get(0);
//                    minDate = (String) vectorYearEnd.get(0);
//                    maxDate = (String) vectorYearEnd.get(1);
//                }
//
//                if (minDate != null) {
//                    plList = em.createNativeQuery("SELECT Sno,GroupCode,SubGroupCode,Code,Description,Classification,MODE from plmast order by groupcode ").getResultList();
//                    if (!plList.isEmpty()) {
//                        for (int j = 0; j < plList.size(); j++) {
//                            double tmpGlBal = 0;
//                            String tmpClass, tmpGLHead = "", tmpSGCode, tmpDesc, tmpGCode, tmpMODE;
//                            ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
//                            Vector element = (Vector) plList.get(j);
//
//                            tmpGCode = (element.get(1) != null ? element.get(1).toString() : "0");
//                            tmpSGCode = (element.get(2) != null ? element.get(2).toString() : "0");
//                            tmpGLHead = (element.get(3) != null ? element.get(3).toString() : "");
//                            tmpDesc = (element.get(4) != null ? element.get(4).toString() : "");
//                            tmpClass = element.get(5).toString();
//                            tmpMODE = element.get(6).toString();
//                            if (flag.equals("0")) {
//                                if (selectOption.equalsIgnoreCase("0")) {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull(((SUM(cramt))-SUM(dramt)),0) "
//                                            + "FROM gl_recon r WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND r.trandesc<>13").getResultList();
//                                } else {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull(((SUM(cramt))-SUM(dramt)),0) "
//                                            + "FROM gl_recon r,gltable g WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27").getResultList();
//                                }
//                            } else {
//                                if (selectOption.equalsIgnoreCase("2")) {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt)-SUM(dramt)),0) "
//                                            + "FROM gl_recon r WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND r.trandesc<>13 ").getResultList();
//                                } else if (selectOption.equalsIgnoreCase("3")) {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt))-SUM((dramt)),0) "
//                                            + "FROM gl_recon r,gltable g WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + hfDt + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 ").getResultList();
//                                } else if (selectOption.equalsIgnoreCase("0")) {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt)-SUM(dramt)),0) "
//                                            + "FROM gl_recon r WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + hfDt + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND r.trandesc<>13 ").getResultList();
//                                } else {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt))-SUM((dramt)),0) "
//                                            + "FROM gl_recon r,gltable g WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 ").getResultList();
//                                }
//                            }
//
//
//                            Vector element1 = (Vector) profitLossList1.get(0);
//                            tmpGlBal = Double.parseDouble(element1.get(0).toString());
//
//                            totGlBal = totGlBal + tmpGlBal;
//
//                            if (profitLossList1.size() > 0 && tmpGlBal != 0) {
//
//                                consEntryPojo.setAcno(tmpGLHead);
//                                consEntryPojo.setAcName(tmpDesc);
//                                consEntryPojo.setAmount(new BigDecimal(tmpGlBal));
//                                consEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
//                                consEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
//                                consEntryPojo.setType(tmpClass);
//                                dataList.add(consEntryPojo);
//                            }
//                        }
//
//                        if (totGlBal != 0) {
//                            if (totGlBal < 0) {
//                                ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
//                                consEntryPojo.setAcno("");
//                                consEntryPojo.setAcName("LOSS");
//                                consEntryPojo.setAmount(new BigDecimal(totGlBal));
//                                consEntryPojo.setGroupCode(Integer.parseInt("9999"));
//                                consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
//                                consEntryPojo.setType("I");
//                                dataList.add(consEntryPojo);
//                            } else if (totGlBal > 0) {
//                                ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
//                                consEntryPojo.setAcno("");
//                                consEntryPojo.setAcName("PROFIT");
//                                consEntryPojo.setAmount(new BigDecimal(totGlBal));
//                                consEntryPojo.setGroupCode(Integer.parseInt("9999"));
//                                consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
//                                consEntryPojo.setType("E");
//                                dataList.add(consEntryPojo);
//                            }
//                        }
//                    }
//                }
//            } else {
//                String minDate = "", maxDate;
//                double totGlBal = 0;
//                orgBrnCode = CbsUtil.lPadding(2, Integer.parseInt(orgBrnCode));
//                List yearEndList = em.createNativeQuery("SELECT mindate,maxdate from yearend where mindate<='" + userDate + "' and maxdate>='" + userDate + "' and brncode = '" + orgBrnCode + "'").getResultList();
//                if (yearEndList.size() > 0) {
//                    Vector vectorYearEnd = (Vector) yearEndList.get(0);
//                    minDate = (String) vectorYearEnd.get(0);
//                    maxDate = (String) vectorYearEnd.get(1);
//                }
//                List allBrn = em.createNativeQuery("SELECT BranchName FROM branchmaster WHERE BRNCODE = '" + orgBrnCode + "'").getResultList();
//                Vector ele = (Vector) allBrn.get(0);
//                String brName = ele.get(0).toString();
//
//                if (minDate != null) {
//                    plList = em.createNativeQuery("SELECT Sno,GroupCode,SubGroupCode,Code,Description,Classification,MODE from plmast order by groupcode ").getResultList();
//                    if (!plList.isEmpty()) {
//                        for (int j = 0; j < plList.size(); j++) {
//                            double tmpGlBal = 0;
//                            String tmpClass, tmpGLHead = "", tmpSGCode, tmpDesc, tmpGCode, tmpMODE;
//                            ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
//                            Vector element = (Vector) plList.get(j);
//
//                            tmpGCode = (element.get(1) != null ? element.get(1).toString() : "0");
//                            tmpSGCode = (element.get(2) != null ? element.get(2).toString() : "0");
//                            tmpGLHead = (element.get(3) != null ? element.get(3).toString() : "");
//                            tmpDesc = (element.get(4) != null ? element.get(4).toString() : "");
//                            tmpClass = element.get(5).toString();
//                            tmpMODE = element.get(6).toString();
//                            if (flag.equals("0")) {
//                                if (selectOption.equalsIgnoreCase("0")) {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull(((SUM(cramt))-SUM(dramt)),0) "
//                                            + "FROM gl_recon r WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND r.trandesc<>13 AND Substring(r.acno,1,2) = '" + orgBrnCode + "'").getResultList();
//                                } else {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull(((SUM(cramt))-SUM(dramt)),0) "
//                                            + "FROM gl_recon r,gltable g WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND Substring(r.acno,1,2) = '" + orgBrnCode + "'").getResultList();
//                                }
//                            } else {
//                                if (selectOption.equalsIgnoreCase("2")) {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt)-SUM(dramt)),0) "
//                                            + "FROM gl_recon r WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND r.trandesc<>13 AND Substring(r.acno,1,2) = '" + orgBrnCode + "'").getResultList();
//                                } else if (selectOption.equalsIgnoreCase("3")) {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt))-SUM((dramt)),0) "
//                                            + "FROM gl_recon r,gltable g WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + hfDt + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND Substring(r.acno,1,2) = '" + orgBrnCode + "'").getResultList();
//                                } else if (selectOption.equalsIgnoreCase("0")) {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt)-SUM(dramt)),0) "
//                                            + "FROM gl_recon r WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + hfDt + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND r.trandesc<>13 AND Substring(r.acno,1,2) = '" + orgBrnCode + "'").getResultList();
//                                } else {
//                                    profitLossList1 = em.createNativeQuery("SELECT ifnull((SUM(cramt))-SUM((dramt)),0) "
//                                            + "FROM gl_recon r,gltable g WHERE SUBSTRING(r.acno,3,8)='" + tmpGLHead + "' AND dt>='" + minDate + "' AND dt<='" + userDate + "' AND auth='Y' "
//                                            + "AND r.trantype <> 27 AND Substring(r.acno,1,2) = '" + orgBrnCode + "'").getResultList();
//                                }
//                            }
//
//
//                            Vector element1 = (Vector) profitLossList1.get(0);
//                            tmpGlBal = Double.parseDouble(element1.get(0).toString());
//
//                            totGlBal = totGlBal + tmpGlBal;
//
//                            if (profitLossList1.size() > 0 && tmpGlBal != 0) {
//
//                                consEntryPojo.setAcno(tmpGLHead);
//                                consEntryPojo.setAcName(tmpDesc);
//                                consEntryPojo.setAmount(new BigDecimal(tmpGlBal));
//                                consEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
//                                consEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
//                                consEntryPojo.setType(tmpClass);
//                                consEntryPojo.setBankName(brName);
//                                dataList.add(consEntryPojo);
//                            }
//                        }
//
//                        if (totGlBal != 0) {
//                            if (totGlBal < 0) {
//                                ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
//                                consEntryPojo.setAcno("");
//                                consEntryPojo.setAcName("LOSS");
//                                consEntryPojo.setAmount(new BigDecimal(totGlBal));
//                                consEntryPojo.setGroupCode(Integer.parseInt("9999"));
//                                consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
//                                consEntryPojo.setType("I");
//                                consEntryPojo.setBankName(brName);
//                                dataList.add(consEntryPojo);
//                            } else if (totGlBal > 0) {
//                                ConsolidateProfitLossPojo consEntryPojo = new ConsolidateProfitLossPojo();
//                                consEntryPojo.setAcno("");
//                                consEntryPojo.setAcName("PROFIT");
//                                consEntryPojo.setAmount(new BigDecimal(totGlBal));
//                                consEntryPojo.setGroupCode(Integer.parseInt("9999"));
//                                consEntryPojo.setSubGroupCode(Integer.parseInt("0"));
//                                consEntryPojo.setType("E");
//                                consEntryPojo.setBankName(brName);
//                                dataList.add(consEntryPojo);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex);
//        }
//        return dataList;
//    }

    public double IncomeTr(String userDate, String orgBrnCode) throws ApplicationException {
        double profit = 0;
        try {
            if (orgBrnCode.equalsIgnoreCase("0A")) {
                List allBrn = em.createNativeQuery("SELECT right(concat('00',cast(brncode as char(2))),2) FROM branchmaster").getResultList();
                if (allBrn.isEmpty()) {
                    throw new ApplicationException("Data does not exist in Branch Master");
                }
                for (int i = 0; i < allBrn.size(); i++) {
                    Vector ele = (Vector) allBrn.get(i);
                    profit = profit + brWiseIncomeTr(userDate, ele.get(0).toString());

                }
            } else {
                profit = brWiseIncomeTr(userDate, orgBrnCode);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return profit;
    }

    public double ExpTr(String userDate, String orgBrnCode) throws ApplicationException {
        double profit = 0;
        try {
            if (orgBrnCode.equalsIgnoreCase("0A")) {
                List allBrn = em.createNativeQuery("SELECT right(concat('00',cast(brncode as char(2))),2) FROM branchmaster").getResultList();
                if (allBrn.isEmpty()) {
                    throw new ApplicationException("Data does not exist in Branch Master");
                }
                for (int i = 0; i < allBrn.size(); i++) {
                    Vector ele = (Vector) allBrn.get(i);
                    profit = profit + brWiseExpTr(userDate, ele.get(0).toString());

                }
            } else {
                profit = brWiseExpTr(userDate, orgBrnCode);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return profit;
    }

    private double brWiseIncomeTr(String userDate, String orgBrnCode) throws ApplicationException {
        List dataList1;
        String tempMinDate1 = "", tempMinDate = "";
        double glCrTotal = 0, glDrTotal = 0, glTotal = 0, profit = 0;
        try {
            dataList1 = em.createNativeQuery("select min(mindate) from yearend where yearendflag='N' AND brncode = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                tempMinDate = (String) (element1.get(0) != null ? element1.get(0) : "");
            }
            dataList1 = em.createNativeQuery("SELECT ifnull(MINDATE,'') FROM yearend WHERE MINDATE <= '" + userDate + "' AND MAXDATE >= '"
                    + userDate + "' AND brncode = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                tempMinDate1 = (String) (element1.get(0) != null ? element1.get(0) : "");
            }
            if (tempMinDate1.equals("")) {
                tempMinDate1 = tempMinDate;
            } else if (tempMinDate1 == null) {
                tempMinDate1 = tempMinDate;
            }
//            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
//                    + "where substring(acno,3,8) between '" + SiplConstant.GL_INC_ST.getValue() + "' and '" + SiplConstant.GL_INC_END.getValue() 
//                    + "' AND dt<='" + userDate + "'  AND dt>='" + tempMinDate1 + "' "
//                    + "and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from gl_recon "
                    + "where substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_END.getValue() + "' AND "
                    + "dt<='" + userDate + "'  AND dt>='" + tempMinDate1 + "' "
                    + "and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                glCrTotal = Double.parseDouble(element1.get(0).toString());
                glDrTotal = Double.parseDouble(element1.get(1).toString());
            }
            glTotal = glCrTotal - glDrTotal;
            profit = glTotal;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return profit;
    }

    private double brWiseExpTr(String userDate, String orgBrnCode) throws ApplicationException {
        List dataList1;
        String tempMinDate1 = "", tempMinDate = "";
        double glTotal = 0, profit = 0;
        try {
            dataList1 = em.createNativeQuery("select min(mindate) from yearend where yearendflag='N' AND brncode = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                tempMinDate = (String) (element1.get(0) != null ? element1.get(0) : "");
            }
            dataList1 = em.createNativeQuery("SELECT ifnull(MINDATE,'') FROM yearend WHERE MINDATE <= '" + userDate + "' AND MAXDATE >= '"
                    + userDate + "' AND brncode = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                tempMinDate1 = (String) (element1.get(0) != null ? element1.get(0) : "");
            }
            if (tempMinDate1.equals("")) {
                tempMinDate1 = tempMinDate;
            } else if (tempMinDate1 == null) {
                tempMinDate1 = tempMinDate;
            }

            dataList1 = em.createNativeQuery("select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from gl_recon "
                    + "where substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue() + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' AND "
                    + "dt<='" + userDate + "'  AND dt>='" + tempMinDate1 + "' "
                    + "and auth='Y' AND SUBSTRING(ACNO,1,2) = '" + orgBrnCode + "'").getResultList();
            if (dataList1.size() > 0) {
                Vector element1 = (Vector) dataList1.get(0);
                glTotal = Double.parseDouble(element1.get(0).toString());
            }
            profit = glTotal;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return profit;
    }

    public String getHalfYearEndFlag() throws ApplicationException {
        try {
            List dataList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where reportname='HALF YEAR END'").getResultList();
            if (dataList.isEmpty()) {
                return "0";
            }
            Vector ele = (Vector) dataList.get(0);
            return ele.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getBranchCodeListExt(String orgnCode) throws ApplicationException {
        List result = new ArrayList();
        try {
            List list1 = em.createNativeQuery("select alphacode from branchmaster where brncode = " + Integer.parseInt(orgnCode)).getResultList();
            Vector v1 = (Vector) list1.get(0);
            String aCode = v1.get(0).toString();
            if (aCode.equalsIgnoreCase("HO")) {
                result = em.createNativeQuery("SELECT 'A','ALL' union select cast(brncode as char(2)),alphacode from branchmaster").getResultList();
            } else {
                result = em.createNativeQuery("select cast(brncode as char(2)),alphacode from branchmaster where parent_brncode = " + Integer.parseInt(orgnCode)).getResultList();
                if (result.isEmpty()) {
                    result = em.createNativeQuery("select cast(brncode as char(2)) ,alphacode from branchmaster where brncode = " + Integer.parseInt(orgnCode)).getResultList();
                } else {
                    result = em.createNativeQuery("select 'B','MERGED ALL' union select cast(brncode as char(2)), concat('EXT - ', alphacode) from branchmaster where parent_brncode = " + Integer.parseInt(orgnCode)
                            + " union select cast(brncode as char(2)), alphacode from branchmaster where brncode = " + Integer.parseInt(orgnCode)).getResultList();
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List<CashInBankPojo> getCashInBankData(String userDate, String orgBrnCode) throws ApplicationException {
        List<CashInBankPojo> dataList = new ArrayList<CashInBankPojo>();
        double cashInHand = 0;
        List result = new ArrayList();
        List balList = new ArrayList();
        try {
            if (orgBrnCode.equalsIgnoreCase("0A")) {
                // result = em.createNativeQuery("select acno,acname from gltable where substring(acno,5,3)='201'").getResultList();
                result = em.createNativeQuery("select distinct (acno),acname from gltable where substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_BANKER_CA_ST.getValue() + "' "
                        + "and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_BANKER_CA_END.getValue() + "'").getResultList();
            } else {
                result = em.createNativeQuery("select distinct (acno),acname from gltable where substring(acno,1,2)='" + orgBrnCode + "' "
                        + "and  substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_BANKER_CA_ST.getValue() + "' "
                        + "and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_BANKER_CA_END.getValue() + "'").getResultList();
            }
            CashInBankPojo pojo1 = new CashInBankPojo();
            cashInHand = CshInHandM(userDate, orgBrnCode, orgBrnCode);
            pojo1.setAcName("CASH IN HAND");
            pojo1.setBalance(new BigDecimal(cashInHand).negate());
            dataList.add(pojo1);

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector element = (Vector) result.get(i);
                    CashInBankPojo pojo = new CashInBankPojo();

                    balList = em.createNativeQuery("SELECT cast(ifnull(SUM(cramt),0)-ifnull(SUM(dramt),0)as decimal(25,2)) FROM gl_recon WHERE dt<='" + userDate + "' "
                            + "AND acno= '" + element.get(0).toString() + "' AND auth='Y'").getResultList();

                    Vector balv = (Vector) balList.get(0);
                    double balance = Double.parseDouble(balv.get(0).toString());
                    if (balance != 0.00) {
                        pojo.setAcNo(element.get(0).toString());
                        pojo.setAcName(element.get(1).toString());
                        pojo.setBalance(new BigDecimal(balance));
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return dataList;
    }

    public List<GlbComperativePojo> getComperativeGlbData(String userDate1, String userDate2, String orgBrnCode, String nBrCode) throws ApplicationException {
        String tmpAcType, tmpReconTab, tmpAcNat, tmpGLHead = "", tmpSGCode, tmpDesc, tmpGCode, tmpglbactype, tmpAcstatus;
        NumberFormat formatter = new DecimalFormat("#.##");
        int tmpLiabAsst;
        List amtLst = new ArrayList();
        List amtLst1 = new ArrayList();
        List acGlSum = new ArrayList();
        List acGlSum1 = new ArrayList();
        List<GlbComperativePojo> glbLst = new ArrayList<GlbComperativePojo>();
        try {
            List acctNatLst = em.createNativeQuery("SELECT Sno,GroupCode,SubGroupCode,Code,Description,AcType,GlbActype,ActStatus,status,brncode from "
                    + "glbmast WHERE  ifnull(ACTYPE,'')<>''").getResultList();
            if (!acctNatLst.isEmpty()) {
                for (int i = 0; i < acctNatLst.size(); i++) {
                    GlbComperativePojo glbEntryPojo = new GlbComperativePojo();
                    Vector element = (Vector) acctNatLst.get(i);

                    tmpAcType = element.get(5).toString();
                    tmpReconTab = getTableNam(tmpAcType);
                    tmpAcNat = common.getAcNatureByAcType(tmpAcType);
                    tmpGLHead = (element.get(3) != null ? element.get(3).toString() : "");
                    tmpGCode = (element.get(1) != null ? element.get(1).toString() : "0");
                    tmpSGCode = (element.get(2) != null ? element.get(2).toString() : "0");
                    tmpDesc = (element.get(4) != null ? element.get(4).toString() : "");
                    tmpglbactype = element.get(6).toString();
                    tmpAcstatus = (element.get(7) != null ? element.get(7).toString() : "0");

                    if (tmpglbactype.equalsIgnoreCase("A")) {
                        tmpLiabAsst = -1;
                    } else {
                        tmpLiabAsst = 1;
                    }

                    double tmpBal = 0;
                    double tmpBal1 = 0;
                    if (orgBrnCode.equalsIgnoreCase("0A")) {
                        if (tmpAcNat.equalsIgnoreCase("CA")) {
                            if (tmpAcstatus.equalsIgnoreCase("0")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate1 + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " AND  r.DT <= '" + userDate1 + "' and r.auth = 'Y' ").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate2 + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " AND  r.DT <= '" + userDate2 + "' and r.auth = 'Y' ").getResultList();
                            } else if (tmpAcstatus.equalsIgnoreCase("2")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate1 + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " AND (OPTSTATUS = " + tmpAcstatus + ") AND r.DT <= '" + userDate1 + "' and r.auth = 'Y' ").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate2 + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " AND (OPTSTATUS = " + tmpAcstatus + ") AND r.DT <= '" + userDate2 + "' and r.auth = 'Y' ").getResultList();
                            } else {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate1 + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate1 + "') AND r.DT <= '" + userDate1 + "' and r.auth = 'Y'").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate2 + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate2 + "') AND r.DT <= '" + userDate2 + "' and r.auth = 'Y'").getResultList();
                            }
                        }
                        if ((tmpAcNat.equalsIgnoreCase("SB")) || (tmpAcNat.equalsIgnoreCase("RD")) || (tmpAcNat.equalsIgnoreCase("DS"))) {
                            if (tmpAcstatus.equalsIgnoreCase("0")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " AND  r.DT <= '" + userDate1 + "' and r.auth = 'Y' ").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " AND  r.DT <= '" + userDate2 + "' and r.auth = 'Y' ").getResultList();
                            } else if (tmpAcstatus.equalsIgnoreCase("2")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " AND (OPTSTATUS = " + tmpAcstatus + ") AND r.DT <= '" + userDate1 + "' and r.auth = 'Y' ").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " AND (OPTSTATUS = " + tmpAcstatus + ") AND r.DT <= '" + userDate2 + "' and r.auth = 'Y' ").getResultList();
                            } else {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate1 + "') AND r.DT <= '" + userDate1 + "' and r.auth = 'Y'").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate2 + "') AND r.DT <= '" + userDate2 + "' and r.auth = 'Y'").getResultList();
                            }
                        }
                        if ((tmpAcNat.equalsIgnoreCase("TL")) || (tmpAcNat.equalsIgnoreCase("DL"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                    + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                    + " from " + tmpReconTab + " WHERE DT <= '" + userDate1 + "' and r.acno = a.acno "
                                    + " AND  r.DT <= '" + userDate1 + "' and r.auth = 'Y')").getResultList();

                            amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                    + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                    + " from " + tmpReconTab + " WHERE DT <= '" + userDate2 + "' and r.acno = a.acno "
                                    + " AND  r.DT <= '" + userDate2 + "' and r.auth = 'Y')").getResultList();
                        }

                        if ((tmpAcNat.equalsIgnoreCase("FD")) || (tmpAcNat.equalsIgnoreCase("MS"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate1 + "' and auth = 'Y' and CLOSEFLAG IS NULL").getResultList();

                            amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate2 + "' and auth = 'Y' and CLOSEFLAG IS NULL").getResultList();
                        }

                        if ((tmpAcNat.equalsIgnoreCase("OF"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate1 + "' and auth = 'Y'").getResultList();

                            amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate2 + "' and auth = 'Y'").getResultList();
                        }

                        Vector element1 = (Vector) amtLst.get(0);
                        tmpBal = Double.valueOf(formatter.format(Double.parseDouble(element1.get(0).toString())));

                        Vector element2 = (Vector) amtLst1.get(0);
                        tmpBal1 = Double.valueOf(formatter.format(Double.parseDouble(element2.get(0).toString())));

                    } else if (orgBrnCode.equalsIgnoreCase("0B")) {
                        if (tmpAcNat.equalsIgnoreCase("CA")) {
                            if (tmpAcstatus.equalsIgnoreCase("0")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate1 + "' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) and  r.dt <= '" + userDate1 + "' and r.auth = 'Y' ").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate2 + "' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) and  r.dt <= '" + userDate2 + "' and r.auth = 'Y' ").getResultList();
                            } else if (tmpAcstatus.equalsIgnoreCase("2")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate1 + "' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND (optstatus = " + tmpAcstatus + ") and r.dt <= '" + userDate1 + "' and r.auth = 'Y' ").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate2 + "' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND (optstatus = " + tmpAcstatus + ") and r.dt <= '" + userDate2 + "' and r.auth = 'Y' ").getResultList();
                            } else {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate1 + "' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate1 + "') and r.dt <= '" + userDate1 + "' and r.auth = 'Y'").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate2 + "' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate2 + "') and r.dt <= '" + userDate1 + "' and r.auth = 'Y'").getResultList();
                            }
                        }
                        if ((tmpAcNat.equalsIgnoreCase("SB")) || (tmpAcNat.equalsIgnoreCase("RD")) || (tmpAcNat.equalsIgnoreCase("DS"))) {
                            if (tmpAcstatus.equalsIgnoreCase("0")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(R.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND  r.dt <= '" + userDate1 + "' and r.auth = 'Y' ").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(R.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND  r.dt <= '" + userDate2 + "' and r.auth = 'Y' ").getResultList();
                            } else if (tmpAcstatus.equalsIgnoreCase("2")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(R.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND (OPTSTATUS = " + tmpAcstatus + ") AND r.dt <= '" + userDate1 + "' and r.auth = 'Y' ").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(R.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND (OPTSTATUS = " + tmpAcstatus + ") AND r.dt <= '" + userDate2 + "' and r.auth = 'Y' ").getResultList();
                            } else {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(R.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate1 + "') and r.dt <= '" + userDate1 + "' and r.auth = 'Y'").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(R.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                        + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                        + " branchmaster where brncode = cast('" + nBrCode + "' as int)) AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate2 + "') and r.dt <= '" + userDate2 + "' and r.auth = 'Y'").getResultList();
                            }
                        }
                        if ((tmpAcNat.equalsIgnoreCase("TL")) || (tmpAcNat.equalsIgnoreCase("DL"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                    + "accountmaster a where substring(R.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                    + " from " + tmpReconTab + " WHERE DT <= '" + userDate1 + "' and r.acno = a.acno and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                    + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                    + " branchmaster where brncode = cast('" + nBrCode + "' as int)) "
                                    + " AND  r.dt <= '" + userDate1 + "' and r.auth = 'Y')").getResultList();

                            amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                    + "accountmaster a where substring(R.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                    + " from " + tmpReconTab + " WHERE DT <= '" + userDate2 + "' and r.acno = a.acno and a.curbrcode in ( select right(concat('00',cast(brncode as char(2))),2) "
                                    + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                    + " branchmaster where brncode = cast('" + nBrCode + "' as int)) "
                                    + " AND  r.dt <= '" + userDate2 + "' and r.auth = 'Y')").getResultList();
                        }

                        if ((tmpAcNat.equalsIgnoreCase("FD")) || (tmpAcNat.equalsIgnoreCase("MS"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate1 + "' and auth = 'Y' and CLOSEFLAG IS NULL and substring(acno,1,2) "
                                    + " in ( select right(concat('00',cast(brncode as char(2))),2) "
                                    + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                    + " branchmaster where brncode = cast('" + nBrCode + "' as int))").getResultList();

                            amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate2 + "' and auth = 'Y' and CLOSEFLAG IS NULL and substring(acno,1,2) "
                                    + " in ( select right(concat('00',cast(brncode as char(2))),2) "
                                    + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                    + " branchmaster where brncode = cast('" + nBrCode + "' as int))").getResultList();
                        }

                        if ((tmpAcNat.equalsIgnoreCase("OF"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate1 + "' and auth = 'Y' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                    + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                    + " branchmaster where brncode = cast('" + nBrCode + "' as int)) ").getResultList();

                            amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate2 + "' and auth = 'Y' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                    + " from branchmaster where parent_brncode = cast('" + nBrCode + "' as int) union select right(concat('00',cast(brncode as char(2))),2) from "
                                    + " branchmaster where brncode = cast('" + nBrCode + "' as int)) ").getResultList();
                        }

                        Vector element1 = (Vector) amtLst.get(0);
                        tmpBal = Double.valueOf(formatter.format(Double.parseDouble(element1.get(0).toString())));

                        Vector element2 = (Vector) amtLst1.get(0);
                        tmpBal1 = Double.valueOf(formatter.format(Double.parseDouble(element2.get(0).toString())));
                    } else {
                        if (tmpAcNat.equalsIgnoreCase("CA")) {
                            if (tmpAcstatus.equalsIgnoreCase("0")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " where dt <= '" + userDate1 + "' and substring(acno,1,2)= '" + orgBrnCode + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' and  r.dt <= '" + userDate1 + "' and r.auth = 'Y' ").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + tmpReconTab + " where dt <= '" + userDate2 + "' and substring(acno,1,2)= '" + orgBrnCode + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' and  r.dt <= '" + userDate2 + "' and r.auth = 'Y' ").getResultList();
                            } else if (tmpAcstatus.equalsIgnoreCase("2")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + orgBrnCode + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + orgBrnCode + " where dt <= '" + userDate1 + "' and substring(acno,1,2)= '" + orgBrnCode + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' and (optstatus = " + tmpLiabAsst + ") and r.dt <= '" + userDate1 + "' and r.auth = 'Y' ").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + orgBrnCode + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                        + " from " + orgBrnCode + " where dt <= '" + userDate2 + "' and substring(acno,1,2)= '" + orgBrnCode + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' and (optstatus = " + tmpLiabAsst + ") and r.dt <= '" + userDate2 + "' and r.auth = 'Y' ").getResultList();
                            } else {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + orgBrnCode + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and R.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate1 + "' and substring(acno,1,2)= '" + orgBrnCode + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' and (optstatus not in (2) or openingdt >= '" + userDate1 + "') and r.dt <= '" + userDate1 + "' and r.auth = 'Y'").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + orgBrnCode + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and R.acno in (select acno "
                                        + " from " + tmpReconTab + " WHERE DT <= '" + userDate2 + "' and substring(acno,1,2)= '" + orgBrnCode + "' group by acno "
                                        + " having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = " + tmpLiabAsst + ") and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' and (optstatus not in (2) or openingdt >= '" + userDate2 + "') and r.dt <= '" + userDate2 + "' and r.auth = 'Y'").getResultList();
                            }
                        }
                        if ((tmpAcNat.equalsIgnoreCase("SB")) || (tmpAcNat.equalsIgnoreCase("RD")) || (tmpAcNat.equalsIgnoreCase("DS"))) {
                            if (tmpAcstatus.equalsIgnoreCase("0")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' AND  r.dt <= '" + userDate1 + "' and r.auth = 'Y' ").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' AND  r.dt <= '" + userDate2 + "' and r.auth = 'Y' ").getResultList();
                            } else if (tmpAcstatus.equalsIgnoreCase("2")) {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' AND (OPTSTATUS = " + tmpAcstatus + ") AND r.DT <= '" + userDate1 + "' and r.auth = 'Y' ").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' AND (OPTSTATUS = " + tmpAcstatus + ") AND r.DT <= '" + userDate2 + "' and r.auth = 'Y' ").getResultList();
                            } else {
                                amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substringrR.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate1 + "') AND r.DT <= '" + userDate1 + "' and r.auth = 'Y'").getResultList();

                                amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                        + "accountmaster a where substringrR.acno,3,2) ='" + tmpAcType + "' and r.acno = a.acno "
                                        + " and a.curbrcode = '" + orgBrnCode + "' AND (OPTSTATUS NOT IN (2) or openingdt >= '" + userDate2 + "') AND r.DT <= '" + userDate2 + "' and r.auth = 'Y'").getResultList();
                            }
                        }
                        if ((tmpAcNat.equalsIgnoreCase("TL")) || (tmpAcNat.equalsIgnoreCase("DL"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                    + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                    + " from " + tmpReconTab + " WHERE DT <= '" + userDate1 + "' and r.acno = a.acno and a.curbrcode = '" + orgBrnCode + "' "
                                    + " AND  r.DT <= '" + userDate1 + "' and r.auth = 'Y')").getResultList();

                            amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " r,"
                                    + "accountmaster a where substring(r.acno,3,2) ='" + tmpAcType + "' and r.acno in (select acno "
                                    + " from " + tmpReconTab + " WHERE DT <= '" + userDate2 + "' and r.acno = a.acno and a.curbrcode = '" + orgBrnCode + "' "
                                    + " AND  r.DT <= '" + userDate2 + "' and r.auth = 'Y')").getResultList();
                        }

                        if ((tmpAcNat.equalsIgnoreCase("FD")) || (tmpAcNat.equalsIgnoreCase("MS"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate1 + "' and auth = 'Y' and closeflag is null and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();

                            amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate2 + "' and auth = 'Y' and closeflag is null and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
                        }

                        if ((tmpAcNat.equalsIgnoreCase("OF"))) {
                            amtLst = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate1 + "' and auth = 'Y' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();

                            amtLst1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tmpReconTab + " "
                                    + " where substring(acno,3,2) ='" + tmpAcType + "' and DT <= '" + userDate2 + "' and auth = 'Y' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
                        }

                        Vector element1 = (Vector) amtLst.get(0);
                        tmpBal = Double.valueOf(formatter.format(Double.parseDouble(element1.get(0).toString())));

                        Vector element2 = (Vector) amtLst1.get(0);
                        tmpBal1 = Double.valueOf(formatter.format(Double.parseDouble(element2.get(0).toString())));
                    }

                    glbEntryPojo.setAcType(tmpAcType);
                    glbEntryPojo.setGlHead(tmpGLHead);
                    if (tmpglbactype.equalsIgnoreCase("L")) {
                        glbEntryPojo.setCramt(new BigDecimal(tmpBal));
                        glbEntryPojo.setDramt(new BigDecimal(0));
                        glbEntryPojo.setCramt1(new BigDecimal(tmpBal1));
                        glbEntryPojo.setDramt1(new BigDecimal(0));
                    } else {
                        glbEntryPojo.setCramt(new BigDecimal(0));
                        glbEntryPojo.setDramt(new BigDecimal(tmpBal));
                        glbEntryPojo.setCramt1(new BigDecimal(0));
                        glbEntryPojo.setDramt1(new BigDecimal(tmpBal1));
                    }
                    glbEntryPojo.setAcStatus(Integer.parseInt(tmpAcstatus));
                    glbEntryPojo.setType(tmpglbactype);
                    glbEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                    glbEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                    glbEntryPojo.setDescription(tmpDesc);
                    glbLst.add(glbEntryPojo);
                }
            }

            List acGlLst = em.createNativeQuery("SELECT Sno,GroupCode,SubGroupCode,Code,Description,AcType,GlbActype,ActStatus,status,brncode from "
                    + "glbmast order by groupcode").getResultList();
            if (!acGlLst.isEmpty()) {
                for (int j = 0; j < acGlLst.size(); j++) {
                    double tmpGlBal = 0, tmpTot = 0, tmpGlBal1 = 0, tmpTot1 = 0;
                    BigDecimal nCr = new BigDecimal(0);
                    BigDecimal nDr = new BigDecimal(0);
                    BigDecimal crTot = new BigDecimal(0);
                    BigDecimal drTot = new BigDecimal(0);
                    BigDecimal nCr1 = new BigDecimal(0);
                    BigDecimal nDr1 = new BigDecimal(0);
                    BigDecimal crTot1 = new BigDecimal(0);
                    BigDecimal drTot1 = new BigDecimal(0);
                    GlbComperativePojo glbEntryPojo = new GlbComperativePojo();
                    Vector element = (Vector) acGlLst.get(j);

                    tmpAcType = element.get(5).toString();
                    tmpGLHead = (element.get(3) != null ? element.get(3).toString() : "");
                    tmpAcstatus = (element.get(7) != null ? element.get(7).toString() : "0");
                    tmpglbactype = element.get(6).toString();
                    tmpGCode = (element.get(1) != null ? element.get(1).toString() : "0");
                    tmpSGCode = (element.get(2) != null ? element.get(2).toString() : "0");
                    tmpDesc = (element.get(4) != null ? element.get(4).toString() : "");

                    if (orgBrnCode.equalsIgnoreCase("0A")) {
                        acGlSum = em.createNativeQuery("select ifnull(sum(cramt - dramt),0) from gl_recon where SUBSTRING(acno,3,8)='" + tmpGLHead + "' and auth='Y' and DT <= '" + userDate1 + "'").getResultList();
                        acGlSum1 = em.createNativeQuery("select ifnull(sum(cramt - dramt),0) from gl_recon where SUBSTRING(acno,3,8)='" + tmpGLHead + "' and auth='Y' and DT <= '" + userDate2 + "'").getResultList();
                    } else if (orgBrnCode.equalsIgnoreCase("0B")) {
                        acGlSum = em.createNativeQuery("select ifnull(sum(cramt - dramt),0) from gl_recon where SUBSTRING(acno,3,8)='" + tmpGLHead
                                + "' and auth='Y' and DT <= '" + userDate1 + "' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                + " from branchmaster where parent_brncode = " + Integer.parseInt(nBrCode) + " union select right(concat('00',cast(brncode as char(2))),2) from "
                                + " branchmaster where brncode = " + Integer.parseInt(nBrCode)).getResultList();

                        acGlSum1 = em.createNativeQuery("select ifnull(sum(cramt - dramt),0) from gl_recon where SUBSTRING(acno,3,8)='" + tmpGLHead
                                + "' and auth='Y' and DT <= '" + userDate2 + "' and substring(acno,1,2) in ( select right(concat('00',cast(brncode as char(2))),2) "
                                + " from branchmaster where parent_brncode = " + Integer.parseInt(nBrCode) + " union select right(concat('00',cast(brncode as char(2))),2) from "
                                + " branchmaster where brncode = " + Integer.parseInt(nBrCode)).getResultList();
                    } else {
                        acGlSum = em.createNativeQuery("select ifnull(sum(cramt - dramt),0) from gl_recon where SUBSTRING(acno,3,8)='" + tmpGLHead + "' and auth='Y' and DT <= '" + userDate1 + "' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();

                        acGlSum1 = em.createNativeQuery("select ifnull(sum(cramt - dramt),0) from gl_recon where SUBSTRING(acno,3,8)='" + tmpGLHead + "' and auth='Y' and DT <= '" + userDate2 + "' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
                    }

                    Vector element1 = (Vector) acGlSum.get(0);
                    tmpGlBal = Double.valueOf(formatter.format(Double.parseDouble(element1.get(0).toString())));

                    Vector element2 = (Vector) acGlSum1.get(0);
                    tmpGlBal1 = Double.valueOf(formatter.format(Double.parseDouble(element2.get(0).toString())));

                    for (int k = 0; k < glbLst.size(); k++) {
                        GlbComperativePojo tmpPojo = glbLst.get(k);
                        String glH = tmpPojo.getGlHead();
                        String acTp = (tmpPojo.getAcType() != null ? tmpPojo.getAcType() : "");
                        String tp = (tmpPojo.getType() != null ? tmpPojo.getType() : "");
                        if ((glH.equalsIgnoreCase(tmpGLHead)) && (acTp.equalsIgnoreCase(tmpAcType)) && (tp.equalsIgnoreCase(tmpglbactype))) {
                            nCr = tmpPojo.getCramt();
                            nDr = tmpPojo.getDramt();
                            nCr1 = tmpPojo.getCramt1();
                            nDr1 = tmpPojo.getDramt1();

                            crTot = crTot.add(nCr);
                            drTot = drTot.add(nDr);
                            crTot1 = crTot1.add(nCr1);
                            drTot1 = drTot1.add(nDr1);
                        }
                    }

                    tmpTot = Double.valueOf(crTot.add(drTot).toString());
                    tmpGlBal = tmpGlBal + tmpTot;

                    tmpTot1 = Double.valueOf(crTot1.add(drTot1).toString());
                    tmpGlBal1 = tmpGlBal1 + tmpTot1;

                    if ((tmpAcType.length()) == 2) {
                        for (int k = 0; k < glbLst.size(); k++) {
                            GlbComperativePojo tmpPojo = glbLst.get(k);
                            String glH = tmpPojo.getGlHead();
                            String acTp = (tmpPojo.getAcType() != null ? tmpPojo.getAcType() : "");
                            String tp = (tmpPojo.getType() != null ? tmpPojo.getType() : "");

                            if ((glH.equalsIgnoreCase(tmpGLHead)) && (acTp.equalsIgnoreCase(tmpAcType)) && (tp.equalsIgnoreCase(tmpglbactype))) {
                                if (tmpglbactype.equalsIgnoreCase("L")) {
                                    tmpPojo.setCramt(new BigDecimal(tmpGlBal));
                                    tmpPojo.setCramt1(new BigDecimal(tmpGlBal1));
                                } else if (tmpglbactype.equalsIgnoreCase("A")) {
                                    tmpPojo.setDramt(new BigDecimal(tmpGlBal));
                                    tmpPojo.setDramt1(new BigDecimal(tmpGlBal1));
                                }
                            }
                        }
                    } else {
                        if (tmpglbactype.equalsIgnoreCase("L")) {
                            if (tmpGLHead.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue())) {
                                tmpGlBal = IncomeExp(userDate1, orgBrnCode, nBrCode);
                                tmpGlBal1 = IncomeExp(userDate2, orgBrnCode, nBrCode);
                                if ((tmpGlBal >= 0) || (tmpGlBal1 >= 0)) {
                                    glbEntryPojo.setGlHead(tmpGLHead);
                                    glbEntryPojo.setCramt(new BigDecimal(tmpGlBal));
                                    glbEntryPojo.setDramt(new BigDecimal(0));
                                    glbEntryPojo.setCramt1(new BigDecimal(tmpGlBal1));
                                    glbEntryPojo.setDramt1(new BigDecimal(0));
                                    glbEntryPojo.setAcStatus(Integer.parseInt(tmpAcstatus));
                                    glbEntryPojo.setType(tmpglbactype);
                                    glbEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                                    glbEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                                    glbEntryPojo.setDescription(tmpDesc);
                                    glbLst.add(glbEntryPojo);
                                }
                            } else {
                                glbEntryPojo.setGlHead(tmpGLHead);
                                glbEntryPojo.setAcStatus(Integer.parseInt(tmpAcstatus));
                                glbEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                                glbEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                                glbEntryPojo.setDescription(tmpDesc);
                                if (tmpGlBal < 0) {
                                    if (tmpGlBal1 < 0) {
                                        glbEntryPojo.setCramt(new BigDecimal(0));
                                        glbEntryPojo.setDramt(new BigDecimal(tmpGlBal));
                                        glbEntryPojo.setCramt1(new BigDecimal(0));
                                        glbEntryPojo.setDramt1(new BigDecimal(tmpGlBal1));
                                        glbEntryPojo.setType("A");
                                        glbLst.add(glbEntryPojo);
                                    } else {
                                        glbEntryPojo.setCramt(new BigDecimal(0));
                                        glbEntryPojo.setDramt(new BigDecimal(tmpGlBal));
                                        glbEntryPojo.setCramt1(new BigDecimal(0));
                                        glbEntryPojo.setDramt1(new BigDecimal(0));
                                        glbEntryPojo.setType("A");
                                        glbLst.add(glbEntryPojo);

                                        glbEntryPojo.setCramt1(new BigDecimal(tmpGlBal1));
                                        glbEntryPojo.setDramt1(new BigDecimal(0));
                                        glbEntryPojo.setCramt(new BigDecimal(0));
                                        glbEntryPojo.setDramt(new BigDecimal(0));
                                        glbEntryPojo.setType("L");
                                        glbLst.add(glbEntryPojo);
                                    }
                                } else if (tmpGlBal > 0) {
                                    if (tmpGlBal1 > 0) {
                                        glbEntryPojo.setCramt(new BigDecimal(tmpGlBal));
                                        glbEntryPojo.setDramt(new BigDecimal(0));
                                        glbEntryPojo.setCramt1(new BigDecimal(tmpGlBal1));
                                        glbEntryPojo.setDramt1(new BigDecimal(0));
                                        glbEntryPojo.setType("L");
                                        glbLst.add(glbEntryPojo);
                                    } else {
                                        glbEntryPojo.setCramt(new BigDecimal(tmpGlBal));
                                        glbEntryPojo.setDramt(new BigDecimal(0));
                                        glbEntryPojo.setCramt1(new BigDecimal(0));
                                        glbEntryPojo.setDramt1(new BigDecimal(0));
                                        glbEntryPojo.setType("L");
                                        glbLst.add(glbEntryPojo);

                                        glbEntryPojo.setCramt1(new BigDecimal(0));
                                        glbEntryPojo.setDramt1(new BigDecimal(tmpGlBal1));
                                        glbEntryPojo.setCramt(new BigDecimal(0));
                                        glbEntryPojo.setDramt(new BigDecimal(0));
                                        glbEntryPojo.setType("A");
                                        glbLst.add(glbEntryPojo);
                                    }
                                }
                            }
                        } else if (tmpglbactype.equalsIgnoreCase("A")) {
                            if (tmpGLHead.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue())) {
                                tmpGlBal = IncomeExp(userDate1, orgBrnCode, nBrCode);
                                tmpGlBal1 = IncomeExp(userDate2, orgBrnCode, nBrCode);
                                if ((tmpGlBal < 0) || (tmpGlBal1 < 0)) {
                                    glbEntryPojo.setGlHead(tmpGLHead);
                                    glbEntryPojo.setCramt(new BigDecimal(0));
                                    glbEntryPojo.setDramt(new BigDecimal(tmpGlBal));
                                    glbEntryPojo.setCramt1(new BigDecimal(0));
                                    glbEntryPojo.setDramt1(new BigDecimal(tmpGlBal1));
                                    glbEntryPojo.setAcStatus(Integer.parseInt(tmpAcstatus));
                                    glbEntryPojo.setType(tmpglbactype);
                                    glbEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                                    glbEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                                    glbEntryPojo.setDescription(tmpDesc);
                                    glbLst.add(glbEntryPojo);
                                }
                            } else if (tmpGLHead.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_CSH_HEAD.getValue())) {
                                tmpGlBal = CshInHandM(userDate1, orgBrnCode, nBrCode);
                                tmpGlBal1 = CshInHandM(userDate2, orgBrnCode, nBrCode);
                                if (tmpGlBal < 0) {
                                    tmpGlBal = Math.abs(tmpGlBal);
                                } else {
                                    tmpGlBal = (tmpGlBal * -1);
                                }

                                if (tmpGlBal1 < 0) {
                                    tmpGlBal1 = Math.abs(tmpGlBal1);
                                } else {
                                    tmpGlBal1 = (tmpGlBal1 * -1);
                                }

                                glbEntryPojo.setGlHead(tmpGLHead);
                                glbEntryPojo.setCramt(new BigDecimal(0));
                                glbEntryPojo.setDramt(new BigDecimal(tmpGlBal));
                                glbEntryPojo.setCramt1(new BigDecimal(0));
                                glbEntryPojo.setDramt1(new BigDecimal(tmpGlBal1));
                                glbEntryPojo.setAcStatus(Integer.parseInt(tmpAcstatus));
                                glbEntryPojo.setType(tmpglbactype);
                                glbEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                                glbEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                                glbEntryPojo.setDescription(tmpDesc);
                                glbLst.add(glbEntryPojo);
                            } else {
                                glbEntryPojo.setGlHead(tmpGLHead);
                                glbEntryPojo.setAcStatus(Integer.parseInt(tmpAcstatus));
                                glbEntryPojo.setGroupCode(Integer.parseInt(tmpGCode));
                                glbEntryPojo.setSubGroupCode(Integer.parseInt(tmpSGCode));
                                glbEntryPojo.setDescription(tmpDesc);
                                if (tmpGlBal < 0) {
                                    if (tmpGlBal1 < 0) {
                                        glbEntryPojo.setCramt(new BigDecimal(0));
                                        glbEntryPojo.setDramt(new BigDecimal(tmpGlBal));
                                        glbEntryPojo.setCramt1(new BigDecimal(0));
                                        glbEntryPojo.setDramt1(new BigDecimal(tmpGlBal1));
                                        glbEntryPojo.setType("A");
                                        glbLst.add(glbEntryPojo);
                                    } else {
                                        glbEntryPojo.setCramt(new BigDecimal(0));
                                        glbEntryPojo.setDramt(new BigDecimal(tmpGlBal));
                                        glbEntryPojo.setCramt1(new BigDecimal(0));
                                        glbEntryPojo.setDramt1(new BigDecimal(0));
                                        glbEntryPojo.setType("A");
                                        glbLst.add(glbEntryPojo);

                                        glbEntryPojo.setCramt1(new BigDecimal(tmpGlBal1));
                                        glbEntryPojo.setDramt1(new BigDecimal(0));
                                        glbEntryPojo.setCramt(new BigDecimal(0));
                                        glbEntryPojo.setDramt(new BigDecimal(0));
                                        glbEntryPojo.setType("L");
                                        glbLst.add(glbEntryPojo);
                                    }
                                } else if (tmpGlBal > 0) {
                                    if (tmpGlBal1 > 0) {
                                        glbEntryPojo.setCramt(new BigDecimal(tmpGlBal));
                                        glbEntryPojo.setDramt(new BigDecimal(0));
                                        glbEntryPojo.setCramt1(new BigDecimal(tmpGlBal1));
                                        glbEntryPojo.setDramt1(new BigDecimal(0));
                                        glbEntryPojo.setType("L");
                                        glbLst.add(glbEntryPojo);
                                    } else {
                                        glbEntryPojo.setCramt(new BigDecimal(tmpGlBal));
                                        glbEntryPojo.setDramt(new BigDecimal(0));
                                        glbEntryPojo.setCramt1(new BigDecimal(0));
                                        glbEntryPojo.setDramt1(new BigDecimal(0));
                                        glbEntryPojo.setType("L");
                                        glbLst.add(glbEntryPojo);

                                        glbEntryPojo.setCramt1(new BigDecimal(0));
                                        glbEntryPojo.setDramt1(new BigDecimal(tmpGlBal1));
                                        glbEntryPojo.setCramt(new BigDecimal(0));
                                        glbEntryPojo.setDramt(new BigDecimal(0));
                                        glbEntryPojo.setType("A");
                                        glbLst.add(glbEntryPojo);
                                    }
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
        return glbLst;
    }

    public List<Td15HEntryCertificationPojo> getTdsCertificateData(String repType, String acNo, String fYear) throws ApplicationException {
        List<Td15HEntryCertificationPojo> dataList = new ArrayList<Td15HEntryCertificationPojo>();
        try {

            String startFyear = fYear + "0401";
            String lastFyear = (Integer.parseInt(fYear) + 1) + "0331";
            double interestAmt = 0d;
            double tdsAmt = 0d;
            String cPan = "";
            String OfficeAdd = common.getTdsOfficeAdd();
            if (repType.equalsIgnoreCase("C")) {
                List result = em.createNativeQuery("select distinct a.acno from td_interesthistory a,customerid b, td_customermaster c where a.acno = b.acno and b.CustId = '" + acNo + "'and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode and a.dt between '" + startFyear + "' and '" + lastFyear + "' "
                        + "union all "
                        + "select distinct a.acno from rd_interesthistory a,customerid b, customermaster c where a.acno = b.acno and b.CustId = '" + acNo + "'and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode and a.dt between '" + startFyear + "' and '" + lastFyear + "'").getResultList();
                if (!result.isEmpty()) {
                    for (int c = 0; c < result.size(); c++) {
                        Vector acv = (Vector) result.get(c);
                        String acno = acv.get(0).toString();
                        String brncode = ftsPosting.getCurrentBrnCode(acno);
                        List list1 = em.createNativeQuery("select Interest,date_format(dt,'%d/%m/%Y') from td_interesthistory where acno = '" + acno + "' and dt between '" + startFyear + "' and '" + lastFyear + "' "
                                + "union all "
                                + "select Interest,date_format(dt,'%d/%m/%Y') from rd_interesthistory where acno = '" + acno + "' and dt between '" + startFyear + "' and '" + lastFyear + "'").getResultList();
                        if (!list1.isEmpty()) {
                            for (int i = 0; i < list1.size(); i++) {
                                Vector ele = (Vector) list1.get(i);
                                Td15HEntryCertificationPojo pojo = new Td15HEntryCertificationPojo();
                                pojo.setInterest(Double.parseDouble(ele.get(0).toString()));
                                pojo.setDt(ele.get(1).toString());
                                pojo.setTotalTaxD(0);
                                pojo.setTdsOfficeAdd(OfficeAdd);
                                List list = em.createNativeQuery("select b.bankname,p.Address,c.custname,c.craddress,c.panno'Pan deductee',p.PANno'Pan deductor',p.TAXAcno'Tan deductor'from bnkadd b,parameterinfo p,td_customermaster c,branchmaster br where p.brncode = '" + brncode + "' and b.alphacode = br.alphacode and p.brncode = br.brncode and c.brncode='" + brncode + "' AND c.custno = substring('" + acno + "',5,6) and c.actype = SUBSTRING('" + acno + "',3,2) "
                                        + "union "
                                        + "select b.bankname,p.Address,c.custname,c.craddress,c.panno'Pan deductee',p.PANno'Pan deductor',p.TAXAcno'Tan deductor'from bnkadd b,parameterinfo p,customermaster c,branchmaster br where p.brncode = '" + brncode + "' and b.alphacode = br.alphacode and p.brncode = br.brncode and c.brncode='" + brncode + "' AND c.custno = substring('" + acno + "',5,6) and c.actype = SUBSTRING('" + acno + "',3,2)").getResultList();
                                Vector vtr = (Vector) list.get(0);
                                pojo.setBankNameAdd(vtr.get(0).toString() + "   " + vtr.get(1).toString());
                                pojo.setCustNameAdd(vtr.get(2).toString() + "   " + vtr.get(3).toString());
                                cPan = vtr.get(4).toString();
                                pojo.setPanNo(cPan);
                                pojo.setPanNoDeDu(vtr.get(5).toString());
                                pojo.setTaxAcNo(vtr.get(6).toString());
                                pojo.setfYear(String.valueOf((Integer.parseInt(fYear) + 1)) + "-" + String.valueOf((Integer.parseInt(fYear) + 2)));
                                pojo.setFrDt("From" + "\n" + startFyear.substring(6, 8) + "/" + startFyear.substring(4, 6) + "/" + startFyear.substring(0, 4));
                                pojo.setToDt("To" + "\n" + lastFyear.substring(6, 8) + "/" + lastFyear.substring(4, 6) + "/" + lastFyear.substring(0, 4));
                                dataList.add(pojo);
                            }
                        }
                        List tdsList;
                        List list2 = em.createNativeQuery("select tds,date_format(dt,'%d/%m/%Y') from tdshistory where acno = '" + acno + "' and dt between '" + startFyear + "' and '" + lastFyear + "'").getResultList();
                        if (!list2.isEmpty()) {
                            for (int j = 0; j < list2.size(); j++) {
                                Vector ele1 = (Vector) list2.get(j);
                                Td15HEntryCertificationPojo pojo1 = new Td15HEntryCertificationPojo();
                                pojo1.setTotalTaxD(Double.parseDouble(ele1.get(0).toString()));
                                pojo1.setDt(ele1.get(1).toString());
                                pojo1.setInterest(0);

                                if (cPan.length() == 10 && ParseFileUtil.isAlphabet(cPan.substring(0, 5))) {
                                    tdsList = em.createNativeQuery("select distinct Tds_Rate from tdsslab").getResultList();
                                } else {
                                    tdsList = em.createNativeQuery("select distinct tdsRate_pan from tdsslab").getResultList();
                                }
                                Vector tdsv = (Vector) tdsList.get(0);
                                pojo1.setRate(Double.parseDouble(tdsv.get(0).toString()));
                                dataList.add(pojo1);
                            }
                        }
                    }
                }

            } else {
                List list1 = em.createNativeQuery("select Interest,date_format(dt,'%d/%m/%Y') from td_interesthistory where acno = '" + acNo + "' and dt between '" + startFyear + "' and '" + lastFyear + "' "
                        + "union all "
                        + "select Interest,date_format(dt,'%d/%m/%Y') from rd_interesthistory where acno = '" + acNo + "' and dt between '" + startFyear + "' and '" + lastFyear + "'").getResultList();
                if (!list1.isEmpty()) {
                    for (int i = 0; i < list1.size(); i++) {
                        Vector ele = (Vector) list1.get(i);
                        Td15HEntryCertificationPojo pojo = new Td15HEntryCertificationPojo();
                        pojo.setInterest(Double.parseDouble(ele.get(0).toString()));
                        pojo.setDt(ele.get(1).toString());
                        pojo.setTotalTaxD(0);
                        pojo.setTdsOfficeAdd(OfficeAdd);
                        String brncode = ftsPosting.getCurrentBrnCode(acNo);
                        List list = em.createNativeQuery("select b.bankname,p.Address,c.custname,c.craddress,c.panno'Pan deductee',p.PANno'Pan deductor',p.TAXAcno'Tan deductor'from bnkadd b,parameterinfo p,td_customermaster c,branchmaster br where p.brncode = '" + brncode + "' and b.alphacode = br.alphacode and p.brncode = br.brncode and c.brncode='" + brncode + "' AND c.custno = substring('" + acNo + "',5,6) and c.actype = SUBSTRING('" + acNo + "',3,2) "
                                + "union "
                                + "select b.bankname,p.Address,c.custname,c.craddress,c.panno'Pan deductee',p.PANno'Pan deductor',p.TAXAcno'Tan deductor'from bnkadd b,parameterinfo p,customermaster c,branchmaster br where p.brncode = '" + brncode + "' and b.alphacode = br.alphacode and p.brncode = br.brncode and c.brncode='" + brncode + "' AND c.custno = substring('" + acNo + "',5,6) and c.actype = SUBSTRING('" + acNo + "',3,2)").getResultList();
                        Vector vtr = (Vector) list.get(0);
                        pojo.setBankNameAdd(vtr.get(0).toString() + "   " + vtr.get(1).toString());
                        pojo.setCustNameAdd(vtr.get(2).toString() + "   " + vtr.get(3).toString());
                        cPan = vtr.get(4).toString();
                        pojo.setPanNo(cPan);
                        pojo.setPanNoDeDu(vtr.get(5).toString());
                        pojo.setTaxAcNo(vtr.get(6).toString());
                        pojo.setfYear(String.valueOf((Integer.parseInt(fYear) + 1)) + "-" + String.valueOf((Integer.parseInt(fYear) + 2)));
                        pojo.setFrDt("From" + "\n" + startFyear.substring(6, 8) + "/" + startFyear.substring(4, 6) + "/" + startFyear.substring(0, 4));
                        pojo.setToDt("To" + "\n" + lastFyear.substring(6, 8) + "/" + lastFyear.substring(4, 6) + "/" + lastFyear.substring(0, 4));
                        dataList.add(pojo);
                    }
                }
                List tdsList;
                List list2 = em.createNativeQuery("select tds,date_format(dt,'%d/%m/%Y') from tdshistory where acno = '" + acNo + "' and dt between '" + startFyear + "' and '" + lastFyear + "'").getResultList();
                if (!list2.isEmpty()) {
                    for (int j = 0; j < list2.size(); j++) {
                        Vector ele1 = (Vector) list2.get(j);
                        Td15HEntryCertificationPojo pojo1 = new Td15HEntryCertificationPojo();
                        pojo1.setTotalTaxD(Double.parseDouble(ele1.get(0).toString()));
                        pojo1.setDt(ele1.get(1).toString());
                        pojo1.setInterest(0);

                        if (cPan.length() == 10 && ParseFileUtil.isAlphabet(cPan.substring(0, 5))) {
                            tdsList = em.createNativeQuery("select distinct Tds_Rate from tdsslab").getResultList();
                        } else {
                            tdsList = em.createNativeQuery("select distinct tdsRate_pan from tdsslab").getResultList();
                        }
                        Vector tdsv = (Vector) tdsList.get(0);
                        pojo1.setRate(Double.parseDouble(tdsv.get(0).toString()));
                        dataList.add(pojo1);
                    }
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<SuspenseGeneralPojo> getSuspenseGeneralData(String userDate, String orgBrnCode, String acNo) throws ApplicationException {
        List<SuspenseGeneralPojo> dataList = new ArrayList<SuspenseGeneralPojo>();

        try {
            List result = em.createNativeQuery("select seqno,date_format(origindt,'%d/%m/%Y'),details,amount as CrAmount from bill_sundry where acno = '" + acNo + "' and origindt <='" + userDate + "'order by origindt,seqno").getResultList();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vector = (Vector) result.get(i);
                    SuspenseGeneralPojo pojo = new SuspenseGeneralPojo();
                    String seqNo = vector.get(0).toString();
                    String orginDt = vector.get(1).toString();
                    String detail = vector.get(2).toString();
                    double susCrAmt = Double.parseDouble(vector.get(3).toString());
                    String susId = seqNo.replace(".0", "");
                    double susDrAmt = 0d;
                    String lastOrginDt = "";
                    List list = em.createNativeQuery("select seqno,date_format(origindt,'%d/%m/%Y'),details,amount as DrAmount from bill_sundry_dt where  acno = '" + acNo + "' and seqno = " + seqNo + " ").getResultList();
                    if (!list.isEmpty()) {
                        Vector vtr = (Vector) list.get(0);
                        lastOrginDt = vtr.get(1).toString();
                        susDrAmt = Double.parseDouble(vtr.get(3).toString());
                    }
                    int chkCeDr = Double.compare(susCrAmt, susDrAmt);
                    if (chkCeDr == 1) {
                        pojo.setSuspenseId(CbsUtil.lPadding(7, Integer.parseInt(susId)));
                        pojo.setSusCreationDt(orginDt);
                        pojo.setParticularOfSus(detail);
                        pojo.setSusDrAmt(0);
                        pojo.setSusCrAmt(susCrAmt);

                        if (susDrAmt == 0) {
                            pojo.setUnadjCrAmt(susCrAmt);
                            pojo.setLastAdjDt(orginDt);
                        } else {
                            pojo.setUnadjCrAmt(susCrAmt - susDrAmt);
                            pojo.setUnadjDrAmt(susDrAmt);
                            pojo.setLastAdjDt(lastOrginDt);
                        }
//                        if ((susCrAmt - susDrAmt) != susCrAmt) {
//                            pojo.setUnadjDrAmt(susCrAmt - susDrAmt);
//                        }
//                        pojo.setUnadjCrAmt(susCrAmt);
//                        pojo.setLastAdjDt(orginDt);
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

    public List<AgentCollectionPojo> getAgentCollectionReport(String status, String tmpAcNat, String fromAccNo, String toAccNo, String fromDate, String toDate, String orgBrnCode, String agentCode) throws ApplicationException {
        List<AgentCollectionPojo> finalBalanceReportList = new ArrayList<AgentCollectionPojo>();
//        List<AgentCollectionPojo> finalBalanceReportDataList = new ArrayList<AgentCollectionPojo>();
        List dataList1 = new ArrayList();
        List dataList2 = new ArrayList();
        List dataList3 = new ArrayList();
        List dataList4 = new ArrayList();
        NumberFormat formatter = new DecimalFormat("#.##");
        try {
            String tmpNat, gTot, bankName = "", bankAddress = "";
            List accNatureList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + tmpAcNat + "'").getResultList();
            Vector vectorAccNature = (Vector) accNatureList.get(0);
            tmpNat = (String) vectorAccNature.get(0);
            if (tmpNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                if (status.equalsIgnoreCase("OPERATIVE")) {
                    if (agentCode.equals("0")) {
                        if (orgBrnCode.equalsIgnoreCase("0A")) {
                            dataList3 = em.createNativeQuery("select  r.acno,substring(a.custname,1,24),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2),ifnull(RdInstal,0),OpeningDt, d.Agcode, d.name,sum(ifnull(cramt,0)) "
                                    + "from ddstransaction r,accountmaster a, ddsagent d  where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                    + "r.dt<='" + toDate + "' and r.auth ='Y' and (ifnull(a.closingdate,'')=''or a.closingdate>'" + toDate + "') and "
                                    + "substring(r.acno,11,2) = d.Agcode and substring(r.acno,1,2) = d.brncode and "
                                    + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                    + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                    + "a.optstatus <> 2 AND a.acno = r.acno group by r.acno,a.custname").getResultList();
                        } else {
                            dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,24),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2),ifnull(RdInstal,0),OpeningDt, d.Agcode, d.name,sum(ifnull(cramt,0)) "
                                    + "from ddstransaction r,accountmaster a, ddsagent d  where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                    + "r.dt<='" + toDate + "' and r.auth ='Y' and (ifnull(a.closingdate,'')=''or a.closingdate>'" + toDate + "') and "
                                    + "substring(r.acno,11,2) = d.Agcode and substring(r.acno,1,2) = d.brncode and "
                                    + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                    + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                    + "a.optstatus <> 2 AND a.acno = r.acno and "
                                    + "a.CurBrCode = '" + orgBrnCode + "' group by r.acno,a.custname").getResultList();
                        }
                    } else {
                        if (orgBrnCode.equalsIgnoreCase("0A")) {
                            dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,24),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) ,ifnull(RdInstal,0),OpeningDt, d.Agcode, d.name,sum(ifnull(cramt,0)) "
                                    + "from ddstransaction r,accountmaster a, ddsagent d  where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                    + "r.dt<='" + toDate + "' and r.auth ='Y' and (ifnull(a.closingdate,'')=''or a.closingdate>'" + toDate + "') and "
                                    + "substring(r.acno,11,2) = d.Agcode and substring(r.acno,1,2) = d.brncode and "
                                    + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                    + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                    + "a.optstatus <> 2 AND a.acno = r.acno and substring(a.acno,11,2)='" + agentCode + "' group by r.acno,a.custname").getResultList();
                        } else {
                            dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,24),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) ,ifnull(RdInstal,0),OpeningDt, d.Agcode, d.name,sum(ifnull(cramt,0)) "
                                    + "from ddstransaction r,accountmaster a, ddsagent d  where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                    + "r.dt<='" + toDate + "' and r.auth ='Y' and (ifnull(a.closingdate,'')=''or a.closingdate>'" + toDate + "') and "
                                    + "substring(r.acno,11,2) = d.Agcode and substring(r.acno,1,2) = d.brncode and "
                                    + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                    + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                    + "a.optstatus <> 2 AND a.acno = r.acno and "
                                    + "a.CurBrCode = '" + orgBrnCode + "' and substring(a.acno,11,2)='" + agentCode + "' group by r.acno,a.custname").getResultList();
                        }
                    }
                    if (dataList3.size() > 0) {
                        for (int i = 0; i < dataList3.size(); i++) {
                            AgentCollectionPojo reportObject3 = new AgentCollectionPojo();
                            Vector vectorObject3 = (Vector) dataList3.get(i);
                            reportObject3.setAccNo(String.valueOf(Integer.parseInt(vectorObject3.get(0).toString().substring(4, 10))));
                            reportObject3.setCustName(vectorObject3.get(1).toString().length() > 24 ? vectorObject3.get(1).toString().substring(0, 24) : vectorObject3.get(1).toString());
                            reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                            reportObject3.setEmi(new BigDecimal(formatter.format(vectorObject3.get(3))));
                            reportObject3.setOpenDt(dmyFormat.format(ymdFormat.parse(vectorObject3.get(4).toString())));
                            reportObject3.setDeposit(vectorObject3.get(7).toString());
                            reportObject3.setAgentCode(vectorObject3.get(5).toString());
                            reportObject3.setAgentName(vectorObject3.get(6).toString());
                            finalBalanceReportList.add(reportObject3);
                        }
                    }
                } else if (status.equalsIgnoreCase("INOPERATIVE")) {
                    if (agentCode.equals("0")) {
                        if (orgBrnCode.equalsIgnoreCase("0A")) {
                            dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,24),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) ,ifnull(RdInstal,0),OpeningDt, d.Agcode, d.name,sum(ifnull(cramt,0)) "
                                    + "from ddstransaction r,accountmaster a, ddsagent d  where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + toDate + "' "
                                    + " and r.auth ='Y' and (ifnull(a.closingdate,'')=''or a.closingdate>'" + toDate + "') and "
                                    + "substring(r.acno,11,2) = d.Agcode and substring(r.acno,1,2) = d.brncode and "
                                    + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                    + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                    + "a.optstatus = 2 AND a.acno = r.acno "
                                    + " group by d.name,r.acno,a.custname").getResultList();
                        } else {
                            dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,24),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2),ifnull(RdInstal,0),OpeningDt, d.Agcode, d.name,sum(ifnull(cramt,0)) "
                                    + "from ddstransaction r,accountmaster a, ddsagent d  where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + toDate + "' "
                                    + " and r.auth ='Y' and (ifnull(a.closingdate,'')=''or a.closingdate>'" + toDate + "') and "
                                    + "substring(r.acno,11,2) = d.Agcode and substring(r.acno,1,2) = d.brncode and "
                                    + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                    + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                    + "a.optstatus = 2 AND a.acno = r.acno "
                                    + "and a.CurBrCode = '" + orgBrnCode + "' group by d.name,r.acno,a.custname").getResultList();
                        }
                    } else {
                        if (orgBrnCode.equalsIgnoreCase("0A")) {
                            dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,24),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2),ifnull(RdInstal,0),OpeningDt, d.Agcode, d.name,sum(ifnull(cramt,0)) "
                                    + "from ddstransaction r,accountmaster a, ddsagent d  where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + toDate + "' "
                                    + " and r.auth ='Y' and (ifnull(a.closingdate,'')=''or a.closingdate>'" + toDate + "') and "
                                    + "substring(r.acno,11,2) = d.Agcode and substring(r.acno,1,2) = d.brncode and "
                                    + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                    + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                    + "a.optstatus = 2 and substring(a.acno,11,2)='" + agentCode + "'"
                                    + "AND a.acno = r.acno group by d.name,r.acno,a.custname").getResultList();
                        } else {
                            dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,24),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2),ifnull(RdInstal,0),OpeningDt, d.Agcode, d.name,sum(ifnull(cramt,0)) "
                                    + "from ddstransaction r,accountmaster a, ddsagent d  where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and r.dt<='" + toDate + "' "
                                    + " and r.auth ='Y' and (ifnull(a.closingdate,'')=''or a.closingdate>'" + toDate + "') and "
                                    + "substring(r.acno,11,2) = d.Agcode and substring(r.acno,1,2) = d.brncode and "
                                    + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                    + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                    + "a.optstatus = 2 and substring(a.acno,11,2)='" + agentCode + "'"
                                    + "AND a.acno = r.acno and a.CurBrCode = '" + orgBrnCode + "' group by d.name,r.acno,a.custname").getResultList();
                        }
                    }
                    if (dataList3.size() > 0) {
                        for (int i = 0; i < dataList3.size(); i++) {
                            AgentCollectionPojo reportObject3 = new AgentCollectionPojo();
                            Vector vectorObject3 = (Vector) dataList3.get(i);
                            reportObject3.setAccNo(String.valueOf(Integer.parseInt(vectorObject3.get(0).toString().substring(4, 10))));
                            reportObject3.setCustName(vectorObject3.get(1).toString().length() > 24 ? vectorObject3.get(1).toString().substring(0, 24) : vectorObject3.get(1).toString());
                            reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                            reportObject3.setEmi(new BigDecimal(formatter.format(vectorObject3.get(3))));
                            reportObject3.setOpenDt(dmyFormat.format(ymdFormat.parse(vectorObject3.get(4).toString())));
                            reportObject3.setDeposit(vectorObject3.get(7).toString());
                            reportObject3.setAgentCode(vectorObject3.get(5).toString());
                            reportObject3.setAgentName(vectorObject3.get(6).toString());
                            finalBalanceReportList.add(reportObject3);
                        }
                    }
                } else {
                    if (agentCode.equals("0")) {
                        if (orgBrnCode.equalsIgnoreCase("0A")) {
                            dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,24),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2),ifnull(RdInstal,0),OpeningDt, d.Agcode, d.name,sum(ifnull(cramt,0)) "
                                    + "from ddstransaction r,accountmaster a, ddsagent d  where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                    + "r.dt<='" + toDate + "' and r.auth ='Y' and (ifnull(a.closingdate,'')=''or a.closingdate>'" + toDate + "') and "
                                    + "substring(r.acno,11,2) = d.Agcode and substring(r.acno,1,2) = d.brncode and "
                                    + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                    + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                    + "a.acno = r.acno "
                                    + " group by d.name,r.acno,a.custname").getResultList();
                        } else {
                            dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,24),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2),ifnull(RdInstal,0),OpeningDt, d.Agcode, d.name,sum(ifnull(cramt,0)) "
                                    + "from ddstransaction r,accountmaster a, ddsagent d  where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                    + "r.dt<='" + toDate + "' and r.auth ='Y' and (ifnull(a.closingdate,'')=''or a.closingdate>'" + toDate + "') and "
                                    + "substring(r.acno,11,2) = d.Agcode and substring(r.acno,1,2) = d.brncode and "
                                    + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                    + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                    + "a.acno = r.acno "
                                    + "and a.CurBrCode = '" + orgBrnCode + "' group by d.name,r.acno,a.custname").getResultList();
                        }
                    } else {
                        if (orgBrnCode.equalsIgnoreCase("0A")) {
                            dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,24),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2),ifnull(RdInstal,0),OpeningDt, d.Agcode, d.name,sum(ifnull(cramt,0)) "
                                    + "from ddstransaction r,accountmaster a, ddsagent d  where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                    + "r.dt<='" + toDate + "' and r.auth ='Y' and (ifnull(a.closingdate,'')=''or a.closingdate>'" + toDate + "') and "
                                    + "substring(r.acno,11,2) = d.Agcode and substring(r.acno,1,2) = d.brncode and "
                                    + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                    + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                    + "a.acno = r.acno and substring(a.acno,11,2)='" + agentCode + "'"
                                    + " group by d.name,r.acno,a.custname").getResultList();
                        } else {
                            dataList3 = em.createNativeQuery("select r.acno,substring(a.custname,1,24),round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2),ifnull(RdInstal,0),OpeningDt, d.Agcode, d.name,sum(ifnull(cramt,0)) "
                                    + "from ddstransaction r,accountmaster a, ddsagent d  where substring(r.acno,3,2)='" + tmpAcNat + "' and r.acno=a.acno and "
                                    + "r.dt<='" + toDate + "' and r.auth ='Y' and (ifnull(a.closingdate,'')=''or a.closingdate>'" + toDate + "') and "
                                    + "substring(r.acno,11,2) = d.Agcode and substring(r.acno,1,2) = d.brncode and "
                                    + "substring(r.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAccNo)) + "' and "
                                    + "substring(r.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAccNo)) + "' and "
                                    + "a.acno = r.acno and substring(a.acno,11,2)='" + agentCode + "'"
                                    + "and a.CurBrCode = '" + orgBrnCode + "' group by d.name,r.acno,a.custname").getResultList();
                        }
                    }
                    if (dataList3.size() > 0) {
                        for (int i = 0; i < dataList3.size(); i++) {
                            AgentCollectionPojo reportObject3 = new AgentCollectionPojo();
                            Vector vectorObject3 = (Vector) dataList3.get(i);
                            reportObject3.setAccNo(String.valueOf(Integer.parseInt(vectorObject3.get(0).toString().substring(4, 10))));
                            reportObject3.setCustName(vectorObject3.get(1).toString().length() > 24 ? vectorObject3.get(1).toString().substring(0, 24) : vectorObject3.get(1).toString());
                            reportObject3.setBalance(new BigDecimal(formatter.format(vectorObject3.get(2))));
                            reportObject3.setEmi(new BigDecimal(formatter.format(vectorObject3.get(3))));
                            reportObject3.setOpenDt(dmyFormat.format(ymdFormat.parse(vectorObject3.get(4).toString())));
                            reportObject3.setDeposit(vectorObject3.get(7).toString());
                            reportObject3.setAgentCode(vectorObject3.get(5).toString());
                            reportObject3.setAgentName(vectorObject3.get(6).toString());
                            finalBalanceReportList.add(reportObject3);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception is " + ex);
            throw new ApplicationException(ex);
        }
        return finalBalanceReportList;
    }

    public List<ExpenditureBalDayWisePojo> getExpenditureData(String orgBrnCode, String frDt, String toDt, String amt, String amtReqOption, String reportType) throws ApplicationException {
        List<ExpenditureBalDayWisePojo> dataList = new ArrayList<ExpenditureBalDayWisePojo>();
        String query = "", query2 = "";
        if (amtReqOption.equalsIgnoreCase("G")) {
            query = ">";
        } else if (amtReqOption.equalsIgnoreCase("L")) {
            query = "<";
        } else {
            query = "=";
        }

        if (reportType.equalsIgnoreCase("2")) {
            query2 = ",a.trsno,a.recno";
        } else {
            query2 = "";
        }
        List result = new ArrayList();
        try {
            if (orgBrnCode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),substring(a.acno,3,8),a.acno,sum(a.dramt-a.cramt),b.acname "
                        + "from gl_recon a,gltable b where a.dt between '" + frDt + "' and '" + toDt + "' and substring(a.acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue() + "' "
                        + "and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "'and a.acno = b.acno and a.trandesc<>13 group by substring(a.acno,3,8),a.dt" + query2 + " having(abs(sum(a.dramt-a.cramt)) " + query + "'" + amt + "') "
                        + "order by a.dt").getResultList();
            } else {
                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),substring(a.acno,3,8),a.acno,sum(a.dramt-a.cramt),b.acname "
                        + "from gl_recon a,gltable b where a.dt between '" + frDt + "' and '" + toDt + "' and substring(a.acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue() + "' "
                        + "and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "'and a.acno = b.acno and a.trandesc<>13 and substring(a.acno,1,2)='" + orgBrnCode + "'"
                        + "group by a.acno,a.dt" + query2 + " having(abs(sum(a.dramt-a.cramt))" + query + "'" + amt + "') order by a.dt").getResultList();
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    ExpenditureBalDayWisePojo pojo = new ExpenditureBalDayWisePojo();
                    String txnDt = vtr.get(0).toString();
                    String glhead = vtr.get(1).toString();
                    String glAcno = vtr.get(2).toString();
                    String glBalance = vtr.get(3).toString();
                    String glName = vtr.get(4).toString();

                    pojo.setTxnDate(txnDt);
                    pojo.setGlName(glName);
                    pojo.setExpsBal(new BigDecimal(glBalance));
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    @Override
    public List<GenReportLedgerTable> expenditureReportAmtWise(String brcode, String glType, String acc, String fromDate, String toDate, String reportAmt, String txnMode) throws ApplicationException {
        String findate, tmpAcno = null, accName, acNat, acCode = null;
        String brnCode = null;
        Double opbal1, opbal2 = 0.0, openingBal, bal;
        List<GenReportLedgerTable> genReportLedgerTableList = new ArrayList<GenReportLedgerTable>();
        try {
            String tranTypeQuery = "";
            String amtType = "";
            if (glType.equalsIgnoreCase("5")) {//For Expenditure Heads
                amtType = " dramt ";
            } else if (glType.equalsIgnoreCase("1")) {//For Income Heads
                amtType = " cramt ";
            }
            if (txnMode.equalsIgnoreCase("0")) {
                tranTypeQuery = " and (" + amtType + " =" + reportAmt + ") ";
            } else if (txnMode.equalsIgnoreCase("1")) {
                tranTypeQuery = " and ( " + amtType + " < " + reportAmt + " and " + amtType + " > 0 ) ";
            } else if (txnMode.equalsIgnoreCase("2")) {
                tranTypeQuery = "  and (" + amtType + " > " + reportAmt + " )  ";
            }
            String accountQuery = "";
            String brnQuery = "";
            if (!brcode.equalsIgnoreCase("0A")) {
                brnQuery = " and substring(acno,1,2)= '" + brcode + "' ";
            }
            if (acc.equalsIgnoreCase("A")) {
                accountQuery = " where substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue() + "'  and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' " + brnQuery + " ";
            } else {
                if (brcode.equalsIgnoreCase("0A")) {
                    accountQuery = " where substring(acno,3,8) = '" + acc + "' ";
                } else {
                    accountQuery = " where substring(acno,3,8) = '" + acc + "' " + brnQuery + " ";
                }

            }
            String month = fromDate.substring(4, 6);
            String year = fromDate.substring(0, 4);
            String frmDtLastYr = String.valueOf(Integer.parseInt(year) - 1);
            if (Integer.parseInt(month) >= 4) {
                findate = year + "0401";
            } else {
                findate = frmDtLastYr + "0401";
            }
            if (glType == null) {
                glType = "";
            }
            List acDetailsList;
            if (brcode.equalsIgnoreCase("0A")) {
                acDetailsList = em.createNativeQuery("select distinct substring(acno,3,8),acname from gltable  " + accountQuery + "").getResultList();
            } else {
                acDetailsList = em.createNativeQuery("select acno,acname from gltable " + accountQuery + " ").getResultList();
            }
            if (!acDetailsList.isEmpty()) {
                for (int l = 0; l < acDetailsList.size(); l++) {
                    Vector vect = (Vector) acDetailsList.get(l);
                    tmpAcno = vect.get(0).toString();
                    accName = vect.get(1).toString();
                    List l1 = new ArrayList();
                    opbal1 = 0d;
                    if (brcode.equalsIgnoreCase("0A")) {
                        l1 = em.createNativeQuery("Select (ifnull(cramt,0)),(ifnull(dramt,0)),date_format(dt,'%d/%m/%Y'),date_format(ValueDt,'%d/%m/%Y') from gl_recon where substring(acno,3,8) = '" + tmpAcno + "'  and"
                                + " dt between '" + fromDate + "' and '" + toDate + "' and auth = 'Y' " + tranTypeQuery + " /*Group by dt*/ order by dt,recno").getResultList();
                    } else {
                        l1 = em.createNativeQuery("Select (ifnull(cramt,0)),(ifnull(dramt,0)),date_format(dt,'%d/%m/%Y'),date_format(ValueDt,'%d/%m/%Y') from gl_recon where acno = '" + tmpAcno + "'  and "
                                + " dt between '" + fromDate + "' and '" + toDate + "' and auth = 'Y' " + tranTypeQuery + " /*Group by dt*/ order by  dt,recno").getResultList();
                    }
                    if (!l1.isEmpty()) {
                        for (int i = 0; i < l1.size(); i++) {
                            GenReportLedgerTable genReportLedgerTable = new GenReportLedgerTable();
                            Vector vec = (Vector) l1.get(i);
                            genReportLedgerTable.setCredit(BigDecimal.valueOf(Double.parseDouble(vec.get(0).toString())));
                            genReportLedgerTable.setDebit(BigDecimal.valueOf(Double.parseDouble(vec.get(1).toString())));
                            genReportLedgerTable.setDate(vec.get(2).toString() + " [ " + vec.get(3).toString() + " ] ");
                            genReportLedgerTable.setAcName(accName);
                            genReportLedgerTable.setAcno(tmpAcno);
                            genReportLedgerTableList.add(genReportLedgerTable);
                        }
                    }
                    String tempAcno1 = "";
                    if (brcode.equalsIgnoreCase("0A")) {
                        tempAcno1 = tmpAcno.substring(2, 8);
                    } else {
                        tempAcno1 = tmpAcno.substring(4, 10);
                    }
                    if (Long.parseLong(tempAcno1) >= Long.parseLong(SiplConstant.GL_PL_ST.getValue()) && Long.parseLong(tempAcno1) <= Long.parseLong(SiplConstant.GL_PL_END.getValue())) {
                        List l2 = new ArrayList();
                        if (brcode.equalsIgnoreCase("0A")) {
                            l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM gl_recon "
                                    + "WHERE substring(acno,3,8) = '" + tmpAcno + "'  AND DT < '" + fromDate + "'  AND DT >= '" + findate + "' AND auth = 'Y' " + tranTypeQuery + "").getResultList();
                        } else {
                            l2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM gl_recon "
                                    + "WHERE acno = '" + tmpAcno + "'  AND DT < '" + fromDate + "'  AND DT >= '" + findate + "' AND auth = 'Y' " + tranTypeQuery + "").getResultList();
                        }
                        Vector vec = (Vector) l2.get(0);
                        opbal1 = Double.parseDouble(vec.get(0).toString());
                    }
                    openingBal = opbal1 + opbal2;
                    for (int i = 0; i < genReportLedgerTableList.size(); i++) {
                        genReportLedgerTableList.get(i).setOpBal(BigDecimal.valueOf(openingBal));
                    }
                    bal = openingBal;
                    for (int i = 0; i < genReportLedgerTableList.size(); i++) {
                        bal = (genReportLedgerTableList.get(i).getCredit().doubleValue() - genReportLedgerTableList.get(i).getDebit().doubleValue()) + bal;
                        genReportLedgerTableList.get(i).setBalance(BigDecimal.valueOf(bal));
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        ComparatorChain chain = new ComparatorChain();
        chain.addComparator(new ExpndtrReportSortedByAcNo());
        Collections.sort(genReportLedgerTableList, chain);
        return genReportLedgerTableList;
    }

    public class ExpndtrReportSortedByAcNo implements Comparator<GenReportLedgerTable> {

        public int compare(GenReportLedgerTable obj1, GenReportLedgerTable obj2) {
            return obj1.getAcno().compareTo(obj2.getAcno());
        }
    }

    public List glHeadList(String reportType) throws ApplicationException {
        List result = new ArrayList();
        try {
            result = em.createNativeQuery("select distinct substring(acno,3,8),AcName from gltable where postflag ='" + reportType + "'").getResultList();

        } catch (Exception e) {
            throw new ApplicationException();
        }
        return result;
    }

    @Override
    public List<SundrySuspenseDetailPojo> sundrySuspenseReportDetail(String brcode, String fromDate, String toDate, String type, String glHead, String reportOption, String partialReconcilType) throws ApplicationException {
        List<SundrySuspenseDetailPojo> sundrySupensesList = new ArrayList<SundrySuspenseDetailPojo>();
        try {
            String batchCode;
            List sundryList, SuspenseList;
            String acNo = "";
            if (!brcode.equalsIgnoreCase("0A")) {
                acNo = brcode + glHead + "01";
            }
            String acnoCondition = "";
            if (brcode.equalsIgnoreCase("0A")) {
                acnoCondition = "and substring(a.acno,3,8) = '" + glHead + "'";
            } else {
                acnoCondition = "and a.acno = '" + acNo + "'";
            }
            List prevList = em.createNativeQuery("select distinct F_YEAR  from yearend where yearendflag='N'").getResultList();
            Vector yVector = (Vector) prevList.get(0);
            String finYear = yVector.get(0).toString();

            double reconciledDebit, reconciledCredit, unreconciledCredit, unreconciledDebit = 0, orginatingCredit, orginatingDebit;
            Map<String, Double> yAmtMap = null;
            if (type.equalsIgnoreCase("11")) {
//                sundryList = em.createNativeQuery("select concat(a.fyear,' ',a.acno) as refNo,ifnull(a.seqno,0.0) as setNo,ifnull(a.details,'') as detail,"
//                        + "ifnull(a.CrAmt,0.0)as creditAmt,ifnull(a.origindt,'') as date,ifnull(b.dt,'') as repDate ,"
//                        + "ifnull(b.DrAmt,0.0)as debitAmt,ifnull(a.trantype,'') as batchNo ,ifnull(a.instno,'') as instNo from "
//                        + "(select fyear,acno,seqno,details,amount CrAmt,dt,origindt,trantype,instno from bill_sundry where origindt between '" + fromDate + "' and '" + toDate + "' and substring(acno,1,2)='" + brcode + "' and acno = '" + acNo + "' group by seqno,fyear,origindt)a "
//                        + "left join "
//                        + "(select fyear,acno,seqno,details,sum(amount) DrAmt,max(dt) dt,origindt,trantype,instno from bill_sundry_dt where origindt between '" + fromDate + "' and '" + toDate + "' /*and substring(acno,1,2)='" + brcode + "'*/ group by seqno,fyear,origindt)b "
//                        + "on a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt and a.acno = '" + acNo + "' group by a.seqno,a.fyear,a.origindt order by 5").getResultList();

                double grTotalUnrCr = 0d;
                if (reportOption.equalsIgnoreCase("R")) {
                    yAmtMap = new HashMap<>();
                    if (partialReconcilType.equalsIgnoreCase("ALL")) {
                        List yAmtList = em.createNativeQuery("select aa.fyear,crAmt,DrAmt,cast((crAmt-drAmt) as decimal(25,2)) unrCrAmt from "
                                + "(select a.fyear,sum(a.amount) crAmt from bill_sundry a,(select a.seqno,cast(sum(b.amount) as decimal(25,2)) DrAmt,a.fyear "
                                + "from bill_sundry a,bill_sundry_dt b "
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt "
                                + "and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " group by a.fyear,a.seqno order by a.fyear,a.seqno "
                                + ")b where a.fyear = b.fyear and a.seqno = b.seqno group by a.fyear)aa, "
                                + "(select a.fyear,cast(sum(b.amount) as decimal(25,2)) DrAmt "
                                + "from bill_sundry a,bill_sundry_dt b "
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt "
                                + "and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " group by a.fyear)bb "
                                + "where aa.fyear=bb.fyear").getResultList();
                        for (int i = 0; i < yAmtList.size(); i++) {
                            Vector vtr = (Vector) yAmtList.get(i);
                            yAmtMap.put(vtr.get(0).toString(), Double.parseDouble(vtr.get(3).toString()));
                        }

                        List grList = em.createNativeQuery("select sum(crAmt),sum(DrAmt),cast((sum(crAmt)-sum(DrAmt)) as decimal(25,2)) unrCrAmt from "
                                + "(select a.fyear,sum(a.amount) crAmt from bill_sundry a,(select a.seqno,cast(sum(b.amount) as decimal(25,2)) DrAmt,a.fyear "
                                + "from bill_sundry a,bill_sundry_dt b "
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt "
                                + "and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " group by a.fyear,a.seqno order by a.fyear,a.seqno "
                                + ")b where a.fyear = b.fyear and a.seqno = b.seqno group by a.fyear)aa, "
                                + "(select a.fyear,cast(sum(b.amount) as decimal(25,2)) DrAmt "
                                + "from bill_sundry a,bill_sundry_dt b "
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt  "
                                + "and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " group by a.fyear)bb "
                                + "where aa.fyear=bb.fyear").getResultList();

                        if (!grList.isEmpty()) {
                            Vector vtr1 = (Vector) grList.get(0);
                            grTotalUnrCr = Double.parseDouble(vtr1.get(2).toString());
                        }
                    }

                    if (partialReconcilType.equalsIgnoreCase("ALL")) {
                        sundryList = em.createNativeQuery("select concat(a.fyear,' ',a.acno) as refNo,a.seqno,ifnull(b.details,''),a.amount CrAmt,a.origindt,b.dt,b.amount DrAmt,a.trantype,ifnull(a.instno,''),a.fyear from bill_sundry a,bill_sundry_dt b\n"
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " order by a.fyear,a.seqno,b.dt").getResultList();
                    } else {
                        sundryList = em.createNativeQuery("select aa.refNo,aa.seqno,ifnull(aa.details,''),aa.CrAmt,aa.origindt,aa.dt,aa.DrAmt,aa.trantype,ifnull(aa.instno,''),aa.fyear from\n"
                                + "(select concat(a.fyear,' ',a.acno) as refNo,a.seqno,b.details,a.amount CrAmt,a.origindt,b.dt,b.amount DrAmt,a.trantype,a.instno,a.fyear from bill_sundry a,bill_sundry_dt b\n"
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " order by a.seqno,b.dt)aa, \n"
                                + "(select concat(a.fyear,' ',a.acno) as refNo,a.seqno,b.details,a.amount CrAmt,a.origindt,b.dt,sum(b.amount) DrAmt,a.trantype,a.instno,b.fyear from bill_sundry a,bill_sundry_dt b\n"
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " \n"
                                + "group by a.fyear,a.seqno having((CrAmt-DrAmt)" + partialReconcilType + ") order by a.seqno,b.dt)bb\n"
                                + "where aa.fyear = bb.fyear and aa.seqno = bb.seqno").getResultList();
                    }
                } else {
//                    sundryList = em.createNativeQuery("select concat(a.fyear,' ',a.acno) as refNo,a.seqno,ifnull(a.details,''),a.amount CrAmt,a.origindt,a.dt,0 DrAmt,a.trantype,ifnull(a.instno,'') from bill_sundry a\n"
//                            + "where  a.origindt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " and a.seqno not in(select distinct seqno from bill_sundry_dt where fyear = '" + finYear + "')").getResultList();
                    sundryList = em.createNativeQuery("select concat(a.fyear,' ',a.acno) as refNo,a.seqno,ifnull(a.details,''),a.amount CrAmt,a.origindt,a.dt,0 DrAmt,a.trantype,ifnull(a.instno,''),a.fyear "
                            + "from bill_sundry a where status = 'ISSUED' " + acnoCondition + " and a.origindt between '" + fromDate + "' and '" + toDate + "'").getResultList();
                }
                double t2Seqno = 0, unreconciledCredit1 = 0;
                double tmpseqNo = 0;
                if (!sundryList.isEmpty()) {
                    for (int i = 0; i < sundryList.size(); i++) {
                        SundrySuspenseDetailPojo pojo = new SundrySuspenseDetailPojo();
                        Vector vec = (Vector) sundryList.get(i);
                        if (reportOption.equalsIgnoreCase("U")) {
                            orginatingCredit = Double.parseDouble(vec.get(3).toString() != null ? vec.get(3).toString() : "0.0");
                            reconciledCredit = Double.parseDouble(vec.get(6).toString());
                            String financeYear = vec.get(9).toString();
                            double seqNo = Double.parseDouble(vec.get(1).toString() != null ? vec.get(1).toString() : "0.0");
                            List list = em.createNativeQuery("select ifnull(sum(amount),0) from bill_sundry_dt where fyear = '" + financeYear + "' and seqno = " + seqNo + "").getResultList();
                            Vector vtr = (Vector) list.get(0);
                            reconciledCredit = Double.parseDouble(vtr.get(0).toString());
                            unreconciledCredit = orginatingCredit - reconciledCredit;

                            pojo.setReferenceNo(vec.get(0).toString() != null ? vec.get(0).toString() : " ");
                            pojo.setSetNo(Double.parseDouble(vec.get(1).toString() != null ? vec.get(1).toString() : "0.0"));
                            pojo.setDetails(vec.get(2).toString() != null ? vec.get(2).toString() : " ");
                            if (reconciledCredit > 0) {
                                pojo.setRespDate(dmyFormat.format(ymmd.parse(vec.get(5).toString())));
                            } else {
                                pojo.setRespDate("");
                            }
                            pojo.setOrginatingCredit(orginatingCredit);
                            pojo.setDate(dmyFormat.format(ymmd.parse(vec.get(4).toString())));
                            pojo.setUnreconciledCredit(unreconciledCredit);
                            pojo.setReconciledCredit(reconciledCredit);
                            pojo.setOrginatingDebit(0.0);
                            pojo.setUnreconciledDebit(0.0);
                            pojo.setReconciledDebit(0.0);
                            if (vec.get(7).toString().equalsIgnoreCase("2")) {
                                batchCode = "Tranfer";
                            } else if (vec.get(7).toString().equalsIgnoreCase("1")) {
                                batchCode = "Cash";
                            } else if (vec.get(7).toString().equalsIgnoreCase("0")) {
                                batchCode = "Clearing";
                            } else {
                                batchCode = "";
                            }
                            pojo.setBatchCode(batchCode);
                            pojo.setInstrumentNo(vec.get(8).toString() != null ? vec.get(8).toString() : " ");
                            sundrySupensesList.add(pojo);
                        } else if (reportOption.equalsIgnoreCase("R")) {

                            double seqno = Double.parseDouble(vec.get(1).toString());
                            orginatingCredit = Double.parseDouble(vec.get(3).toString() != null ? vec.get(3).toString() : "0.0");
                            reconciledCredit = Double.parseDouble(vec.get(6).toString());
                            //unreconciledCredit = orginatingCredit - reconciledCredit;


                            if (t2Seqno == 0) {
                                t2Seqno = seqno;
                                unreconciledCredit1 = orginatingCredit;
                                unreconciledCredit1 = orginatingCredit - reconciledCredit;
                            } else {
                                if (seqno == t2Seqno) {
                                    unreconciledCredit1 = unreconciledCredit1 - reconciledCredit;
                                } else {
                                    unreconciledCredit1 = orginatingCredit;
                                    unreconciledCredit1 = unreconciledCredit1 - reconciledCredit;
                                    t2Seqno = seqno;
                                }
                            }

                            if (tmpseqNo == 0) {
                                orginatingCredit = orginatingCredit;
                                tmpseqNo = seqno;
                            } else {
                                if (tmpseqNo == seqno) {
                                    orginatingCredit = 0;
                                    tmpseqNo = seqno;
                                } else {
                                    orginatingCredit = orginatingCredit;
                                    tmpseqNo = seqno;
                                }
                            }

                            pojo.setReferenceNo(vec.get(0).toString() != null ? vec.get(0).toString() : " ");
                            pojo.setSetNo(Double.parseDouble(vec.get(1).toString() != null ? vec.get(1).toString() : "0.0"));
                            pojo.setDetails(vec.get(2).toString() != null ? vec.get(2).toString() : " ");
                            if (reconciledCredit > 0) {
                                pojo.setRespDate(dmyFormat.format(ymmd.parse(vec.get(5).toString())));
                            } else {
                                pojo.setRespDate("");
                            }
                            pojo.setOrginatingCredit(orginatingCredit);
                            pojo.setDate(dmyFormat.format(ymmd.parse(vec.get(4).toString())));
                            pojo.setUnreconciledCredit(unreconciledCredit1);
                            pojo.setReconciledCredit(reconciledCredit);
                            pojo.setOrginatingDebit(0.0);
                            pojo.setUnreconciledDebit(0.0);
                            pojo.setReconciledDebit(0.0);
                            if (vec.get(7).toString().equalsIgnoreCase("2")) {
                                batchCode = "Tranfer";
                            } else if (vec.get(7).toString().equalsIgnoreCase("1")) {
                                batchCode = "Cash";
                            } else if (vec.get(7).toString().equalsIgnoreCase("0")) {
                                batchCode = "Clearing";
                            } else {
                                batchCode = "";
                            }
                            pojo.setBatchCode(batchCode);
                            pojo.setInstrumentNo(vec.get(8).toString() != null ? vec.get(8).toString() : " ");
                            pojo.setYear(vec.get(9).toString());
                            pojo.setUnrCrByYear(yAmtMap.get(pojo.getYear()) != null ? yAmtMap.get(pojo.getYear()) : 0);
                            pojo.setGrTotalUnrCr(grTotalUnrCr);
                            sundrySupensesList.add(pojo);
                        }
                    }
                }
            } else {
                double grTotalUnrDr = 0d;
                double tmpseqNo = 0;
//                SuspenseList = em.createNativeQuery("select concat(a.fyear,' ',a.acno) as refNo,ifnull(a.seqno,0.0) as setNo,ifnull(a.details,'') as detail,ifnull(a.CrAmt,0.0)as creditAmt,"
//                        + "ifnull(a.origindt,'') as date,ifnull(b.dt,'') as repDate ,"
//                        + "ifnull(b.DrAmt,0.0)as debitAmt,ifnull(a.trantype,'') as batchNo ,ifnull(a.instno,'') as instNo from "
//                        + "(select fyear,acno,seqno,details,amount CrAmt,dt,origindt,trantype,instno from bill_suspense where origindt between '" + fromDate + "' and '" + toDate + "' and substring(acno,1,2)='" + brcode + "' and acno = '" + acNo + "' group by seqno,fyear,origindt)a "
//                        + "left join "
//                        + "(select fyear,acno,seqno,details,sum(amount) DrAmt,max(dt) dt,origindt,trantype,instno from bill_suspense_dt where origindt between '" + fromDate + "' and '" + toDate + "' /*and substring(acno,1,2)='" + brcode + "'*/ group by seqno,fyear,origindt)b"
//                        + " on a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt and a.acno = '" + acNo + "' group by a.seqno,a.fyear,a.origindt order by 5").getResultList();
                if (reportOption.equalsIgnoreCase("R")) {

                    yAmtMap = new HashMap<>();
                    if (partialReconcilType.equalsIgnoreCase("ALL")) {
                        List yAmtList = em.createNativeQuery("select aa.fyear,crAmt,DrAmt,cast((crAmt-drAmt) as decimal(25,2)) unrCrAmt from "
                                + "(select a.fyear,sum(a.amount) crAmt from bill_suspense a,"
                                + "(select a.seqno,cast(sum(b.amount) as decimal(25,2)) DrAmt,a.fyear "
                                + "from bill_suspense a,bill_suspense_dt b "
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt "
                                + "and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " group by a.fyear,a.seqno order by a.fyear,a.seqno "
                                + ")b where a.fyear = b.fyear and a.seqno = b.seqno group by a.fyear)aa, "
                                + "(select a.fyear,cast(sum(b.amount) as decimal(25,2)) DrAmt "
                                + "from bill_suspense a,bill_suspense_dt b "
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt "
                                + "and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " group by a.fyear)bb "
                                + "where aa.fyear=bb.fyear").getResultList();
                        for (int i = 0; i < yAmtList.size(); i++) {
                            Vector vtr = (Vector) yAmtList.get(i);
                            yAmtMap.put(vtr.get(0).toString(), Double.parseDouble(vtr.get(3).toString()));
                        }

                        List grList = em.createNativeQuery("select sum(crAmt),sum(DrAmt),cast((sum(crAmt)-sum(DrAmt)) as decimal(25,2)) unrCrAmt from "
                                + "(select a.fyear,sum(a.amount) crAmt from bill_suspense a,"
                                + "(select a.seqno,cast(sum(b.amount) as decimal(25,2)) DrAmt,a.fyear "
                                + "from bill_suspense a,bill_suspense_dt b "
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt "
                                + "and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " group by a.fyear,a.seqno order by a.fyear,a.seqno "
                                + ")b where a.fyear = b.fyear and a.seqno = b.seqno group by a.fyear)aa, "
                                + "(select a.fyear,cast(sum(b.amount) as decimal(25,2)) DrAmt "
                                + "from bill_suspense a,bill_suspense_dt b "
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt  "
                                + "and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " group by a.fyear)bb "
                                + "where aa.fyear=bb.fyear").getResultList();

                        if (!grList.isEmpty()) {
                            Vector vtr1 = (Vector) grList.get(0);
                            grTotalUnrDr = Double.parseDouble(vtr1.get(2).toString());
                        }
                    }

                    if (partialReconcilType.equalsIgnoreCase("ALL")) {
                        SuspenseList = em.createNativeQuery("select concat(a.fyear,' ',a.acno) as refNo,a.seqno,b.details,a.amount CrAmt,a.origindt,b.dt,b.amount DrAmt,a.trantype,ifnull(a.instno,''),a.fyear from bill_suspense a,bill_suspense_dt b\n"
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " order by a.seqno,b.dt").getResultList();
                    } else {
                        SuspenseList = em.createNativeQuery("select aa.refNo,aa.seqno,aa.details,aa.CrAmt,aa.origindt,aa.dt,aa.DrAmt,aa.trantype,ifnull(aa.instno,''),aa.fyear from\n"
                                + "(select concat(a.fyear,' ',a.acno) as refNo,a.seqno,b.details,a.amount CrAmt,a.origindt,b.dt,b.amount DrAmt,a.trantype,a.instno,a.fyear from bill_suspense a,bill_suspense_dt b\n"
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " order by a.seqno,b.dt)aa, \n"
                                + "(select concat(a.fyear,' ',a.acno) as refNo,a.seqno,b.details,a.amount CrAmt,a.origindt,b.dt,sum(b.amount) DrAmt,a.trantype,a.instno,b.fyear from bill_suspense a,bill_suspense_dt b\n"
                                + "where a.fyear = b.fyear and a.seqno = b.seqno and a.origindt = a.origindt and b.dt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " \n"
                                + "group by a.fyear,a.seqno having((CrAmt-DrAmt)" + partialReconcilType + ") order by a.seqno,b.dt)bb\n"
                                + "where aa.fyear = bb.fyear and aa.seqno = bb.seqno").getResultList();
                    }
                } else {
//                    SuspenseList = em.createNativeQuery("select concat(a.fyear,' ',a.acno) as refNo,a.seqno,a.details,a.amount CrAmt,a.origindt,a.dt,0 DrAmt,a.trantype,ifnull(a.instno,'') from bill_suspense a"
//                            + "where  a.origindt between '" + fromDate + "' and '" + toDate + "' " + acnoCondition + " and a.seqno not in(select distinct seqno from bill_suspense_dt where fyear = '" + finYear + "')").getResultList();
                    SuspenseList = em.createNativeQuery("select concat(a.fyear,' ',a.acno) as refNo,a.seqno,ifnull(a.details,''),a.amount CrAmt,a.origindt,a.dt,0 DrAmt,a.trantype,ifnull(a.instno,''),a.fyear "
                            + "from bill_suspense a where status = 'ISSUED' " + acnoCondition + " and a.origindt between '" + fromDate + "' and '" + toDate + "'").getResultList();
                }
                double t1Seqno = 0, unreconciledDebit1 = 0;
                if (!SuspenseList.isEmpty()) {
                    for (int i = 0; i < SuspenseList.size(); i++) {
                        SundrySuspenseDetailPojo pojo = new SundrySuspenseDetailPojo();
                        Vector vec = (Vector) SuspenseList.get(i);
//                        orginatingDebit = Double.parseDouble(vec.get(3).toString() != null ? vec.get(3).toString() : "0.0");
//                        reconciledDebit = Double.parseDouble(vec.get(6).toString());
//                        unreconciledDebit = orginatingDebit - reconciledDebit;
//                        unreconciledDebit = Double.parseDouble(vec.get(3).toString() != null ? vec.get(3).toString() : "0.0");
//                        reconciledDebit = Double.parseDouble(vec.get(6).toString() != null ? vec.get(6).toString() : "0.0");
//                        orginatingDebit = reconciledDebit + unreconciledDebit;
                        if (reportOption.equalsIgnoreCase("U")) {
                            orginatingDebit = Double.parseDouble(vec.get(3).toString() != null ? vec.get(3).toString() : "0.0");
                            reconciledDebit = Double.parseDouble(vec.get(6).toString());
                            double seqNo = Double.parseDouble(vec.get(1).toString() != null ? vec.get(1).toString() : "0.0");
                            String financeYear = vec.get(9).toString();
                            List list = em.createNativeQuery("select ifnull(sum(amount),0) from bill_suspense_dt where fyear = '" + financeYear + "' and seqno = " + seqNo + "").getResultList();
                            Vector vtr = (Vector) list.get(0);
                            reconciledDebit = Double.parseDouble(vtr.get(0).toString());
                            unreconciledDebit = orginatingDebit - reconciledDebit;

                            pojo.setReferenceNo(vec.get(0).toString() != null ? vec.get(0).toString() : " ");
                            pojo.setSetNo(Double.parseDouble(vec.get(1).toString() != null ? vec.get(1).toString() : "0.0"));
                            pojo.setDetails(vec.get(2).toString() != null ? vec.get(2).toString() : " ");
                            if (reconciledDebit > 0) {
                                pojo.setRespDate(dmyFormat.format(ymmd.parse(vec.get(5).toString())));
                            } else {
                                pojo.setRespDate("");
                            }
                            pojo.setOrginatingDebit(orginatingDebit);
                            pojo.setUnreconciledDebit(unreconciledDebit);
                            pojo.setOrginatingCredit(0.0);
                            pojo.setUnreconciledCredit(0.0);
                            pojo.setReconciledCredit(0.0);
                            pojo.setDate(dmyFormat.format(ymmd.parse(vec.get(4).toString())));
                            if (vec.get(7).toString().equalsIgnoreCase("2")) {
                                batchCode = "Tranfer";
                            } else if (vec.get(7).toString().equalsIgnoreCase("1")) {
                                batchCode = "Cash";
                            } else if (vec.get(7).toString().equalsIgnoreCase("0")) {
                                batchCode = "Clearing";
                            } else {
                                batchCode = "";
                            }
                            pojo.setBatchCode(batchCode);
                            pojo.setInstrumentNo(vec.get(8).toString() != null ? vec.get(8).toString() : " ");
                            pojo.setReconciledDebit(reconciledDebit);
                            sundrySupensesList.add(pojo);

                        } else if (reportOption.equalsIgnoreCase("R")) {


                            double seqno = Double.parseDouble(vec.get(1).toString());
                            orginatingDebit = Double.parseDouble(vec.get(3).toString() != null ? vec.get(3).toString() : "0.0");
                            reconciledDebit = Double.parseDouble(vec.get(6).toString());

                            if (t1Seqno == 0) {
                                t1Seqno = seqno;
                                unreconciledDebit1 = orginatingDebit;
                                unreconciledDebit1 = orginatingDebit - reconciledDebit;
                            } else {
                                if (seqno == t1Seqno) {
                                    unreconciledDebit1 = unreconciledDebit1 - reconciledDebit;
                                } else {
                                    unreconciledDebit1 = orginatingDebit;
                                    unreconciledDebit1 = unreconciledDebit1 - reconciledDebit;
                                    t1Seqno = seqno;
                                }
                            }

                            if (tmpseqNo == 0) {
                                orginatingDebit = orginatingDebit;
                                tmpseqNo = seqno;
                            } else {
                                if (tmpseqNo == seqno) {
                                    orginatingDebit = 0;
                                    tmpseqNo = seqno;
                                } else {
                                    orginatingDebit = orginatingDebit;
                                    tmpseqNo = seqno;
                                }
                            }


//                            orginatingDebit = Double.parseDouble(vec.get(3).toString() != null ? vec.get(3).toString() : "0.0");
//                            reconciledDebit = Double.parseDouble(vec.get(6).toString());
//                            unreconciledDebit = orginatingDebit - reconciledDebit;
                            pojo.setReferenceNo(vec.get(0).toString() != null ? vec.get(0).toString() : " ");
                            pojo.setSetNo(Double.parseDouble(vec.get(1).toString() != null ? vec.get(1).toString() : "0.0"));
                            pojo.setDetails(vec.get(2).toString() != null ? vec.get(2).toString() : " ");
                            if (reconciledDebit > 0) {
                                pojo.setRespDate(dmyFormat.format(ymmd.parse(vec.get(5).toString())));
                            } else {
                                pojo.setRespDate("");
                            }
                            pojo.setOrginatingDebit(orginatingDebit);
                            pojo.setUnreconciledDebit(unreconciledDebit1);
                            pojo.setOrginatingCredit(0.0);
                            pojo.setUnreconciledCredit(0.0);
                            pojo.setReconciledCredit(0.0);
                            pojo.setDate(dmyFormat.format(ymmd.parse(vec.get(4).toString())));
                            if (vec.get(7).toString().equalsIgnoreCase("2")) {
                                batchCode = "Tranfer";
                            } else if (vec.get(7).toString().equalsIgnoreCase("1")) {
                                batchCode = "Cash";
                            } else if (vec.get(7).toString().equalsIgnoreCase("0")) {
                                batchCode = "Clearing";
                            } else {
                                batchCode = "";
                            }
                            pojo.setBatchCode(batchCode);
                            pojo.setInstrumentNo(vec.get(8).toString() != null ? vec.get(8).toString() : " ");
                            pojo.setReconciledDebit(reconciledDebit);
                            pojo.setYear(vec.get(9).toString());
                            pojo.setUnrDrByYear(yAmtMap.get(pojo.getYear()) != null ? yAmtMap.get(pojo.getYear()) : 0);
                            pojo.setGrTotalUnrDr(grTotalUnrDr);
                            sundrySupensesList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return sundrySupensesList;
    }
}
