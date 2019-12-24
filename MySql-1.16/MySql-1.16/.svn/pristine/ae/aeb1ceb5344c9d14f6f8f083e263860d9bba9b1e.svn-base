/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.pojo.AllSmsPojo;
import com.cbs.pojo.HoReconsilePojo;
import com.cbs.pojo.IndividualSmsPojo;
import com.cbs.pojo.NpaCategoryReport;
import com.cbs.pojo.NpaComparatorByAcno;
import com.cbs.pojo.NpaRecoveryPojo;
import com.cbs.pojo.NpaSummaryPojo;
import com.cbs.pojo.NpaReportPojo;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.pojo.OfficeAccountPojo;
import com.cbs.utils.CbsUtil;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(mappedName = "NpaReportFacade")
public class NpaReportFacade implements NpaReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsFacade;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private LoanReportFacadeRemote loanRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#.##");
    private Properties props = null;

    @PostConstruct
    private void loadSmsConfig() {
        try {
            props = new Properties();
            props.load(new FileReader("/opt/conf/sms.properties"));
        } catch (Exception ex) {
            System.out.println("Problem In Bean Initialization And Loading The "
                    + "SMS Properties File. " + ex.getMessage());
        }
    }

    public List getAccountType() throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT ACCTCODE FROM accounttypemaster WHERE ACCTNATURE IN ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') and CrDbFlag in('B','D') ORDER BY ACCTCODE").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<NpaRecoveryPojo> getNpaRecoveryData(String acType, String fromDt, String toDt, String orgnBrCode) throws ApplicationException {
        List<NpaRecoveryPojo> resultList = new ArrayList<NpaRecoveryPojo>();
        List dataList = null;
        try {
            if (orgnBrCode.equalsIgnoreCase("0A")) {
                if (acType.equalsIgnoreCase("ALL")) {
//                    dataList = em.createNativeQuery("SELECT A.ACNO,A.CUSTNAME NAME,C.INT_APP_FREQ, d.acctNature, d.AcctCode, d.AcctDesc   FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C, accounttypemaster d  WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND "
//                            + " d.AcctCode = SUBSTRING(A.ACNO,3,2) and  SUBSTRING(A.ACNO,3,2)IN (SELECT ACCTCODE FROM accounttypemaster "
//                            + "WHERE ACCTNATURE IN ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "')  and crdbFlag in ('D','B')) AND SUBSTRING(A.ACNO,3,2)<>''").getResultList();
                    dataList = em.createNativeQuery("Select d.ano,d.NAME,d.FREQ,substring(c.description,1,3),d.actCode,t.acctnature,t.acctdesc, e.edt, f.sno from accountstatus a,codebook c,accounttypemaster t, "
                            + " (SELECT A.ACNO as ano,A.CUSTNAME NAME,C.INT_APP_FREQ as freq,A.accttype as actCode FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C "
                            + " WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO  AND SUBSTRING(A.ACNO,3,2)IN (SELECT ACCTCODE FROM accounttypemaster "
                            + " WHERE ACCTNATURE IN ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "')  and crdbFlag in ('D','B'))) d, "
                            + " (select acno as eno,max(effdt) as edt from accountstatus where effdt <='" + toDt + "' group by acno) e, "
                            + " (select acno as fno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) f "
                            + " where a.acno=d.ano and a.effdt=e.edt and a.acno = e.eno and a.spno = f.sno and a.acno = f.fno "
                            + " and (spflag in (11,12,13,14,3,6,7,8,2,1)) AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 and d.actCode = t.acctcode ").getResultList();
                } else {
//                    dataList = em.createNativeQuery("SELECT A.ACNO,A.CUSTNAME NAME,C.INT_APP_FREQ, d.acctNature, d.AcctCode, d.AcctDesc  FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C, accounttypemaster d   WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND  d.AcctCode = SUBSTRING(A.ACNO,3,2) and SUBSTRING(A.ACNO,3,2)='" + acType + "' AND SUBSTRING(A.ACNO,3,2)<>'' ").getResultList();
                    dataList = em.createNativeQuery("Select d.ano,d.NAME,d.FREQ,substring(c.description,1,3),d.actCode,t.acctnature,t.acctdesc, e.edt, f.sno from accountstatus a,codebook c,accounttypemaster t, "
                            + " (SELECT A.ACNO as ano,A.CUSTNAME NAME,C.INT_APP_FREQ as freq,A.accttype as actCode FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C "
                            + " WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND SUBSTRING(A.ACNO,3,2)IN (SELECT ACCTCODE FROM accounttypemaster "
                            + " WHERE  ACCTCODE in ('" + acType + "') )) d, "
                            + " (select acno as eno,max(effdt) as edt from accountstatus where effdt <='" + toDt + "' group by acno) e, "
                            + " (select acno as fno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) f "
                            + " where a.acno=d.ano and a.effdt=e.edt and a.acno = e.eno and a.spno = f.sno and a.acno = f.fno "
                            + " and (spflag in (11,12,13,14,3,6,7,8,2,1)) AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 and d.actCode = t.acctcode  ").getResultList();
                }
            } else {
                if (acType.equalsIgnoreCase("ALL")) {
//                    dataList = em.createNativeQuery("SELECT A.ACNO,A.CUSTNAME NAME,C.INT_APP_FREQ, d.acctNature, d.AcctCode, d.AcctDesc FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C, accounttypemaster d   WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND "
//                            + " d.AcctCode = SUBSTRING(A.ACNO,3,2) and SUBSTRING(A.ACNO,3,2)IN (SELECT ACCTCODE FROM accounttypemaster "
//                            + "WHERE ACCTNATURE IN ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "')  and crdbFlag in ('D','B')) AND SUBSTRING(A.ACNO,3,2)<>'' AND A.CURBRCODE='" + orgnBrCode + "'").getResultList();
                    dataList = em.createNativeQuery("Select d.ano,d.NAME,d.FREQ,substring(c.description,1,3),d.actCode,t.acctnature,t.acctdesc, e.edt, f.sno from accountstatus a,codebook c,accounttypemaster t, "
                            + " (SELECT A.ACNO as ano,A.CUSTNAME NAME,C.INT_APP_FREQ as freq,A.accttype as actCode FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C "
                            + " WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND A.CURBRCODE='" + orgnBrCode + "' AND SUBSTRING(A.ACNO,3,2)IN (SELECT ACCTCODE FROM accounttypemaster "
                            + " WHERE ACCTNATURE IN ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "')  and crdbFlag in ('D','B'))) d, "
                            + " (select acno as eno,max(effdt) as edt from accountstatus where effdt <='" + toDt + "' group by acno) e, "
                            + " (select acno as fno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) f "
                            + " where a.acno=d.ano and a.effdt=e.edt and a.acno = e.eno and a.spno = f.sno and a.acno = f.fno "
                            + " and (spflag in (11,12,13,14,3,6,7,8,2,1)) AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 and d.actCode = t.acctcode ").getResultList();
                } else {
//                    dataList = em.createNativeQuery("SELECT A.ACNO,A.CUSTNAME NAME,C.INT_APP_FREQ, d.acctNature, d.AcctCode, d.AcctDesc FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C, accounttypemaster d  WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND  d.AcctCode = SUBSTRING(A.ACNO,3,2) and SUBSTRING(A.ACNO,3,2)='" + acType + "' AND SUBSTRING(A.ACNO,3,2)<>'' AND A.CURBRCODE='" + orgnBrCode + "'").getResultList();
                    dataList = em.createNativeQuery("Select d.ano,d.NAME,d.FREQ,substring(c.description,1,3),d.actCode,t.acctnature,t.acctdesc, e.edt, f.sno from accountstatus a,codebook c,accounttypemaster t, "
                            + " (SELECT A.ACNO as ano,A.CUSTNAME NAME,C.INT_APP_FREQ as freq,A.accttype as actCode FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C "
                            + " WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND A.CURBRCODE='" + orgnBrCode + "' AND SUBSTRING(A.ACNO,3,2)IN (SELECT ACCTCODE FROM accounttypemaster "
                            + " WHERE  ACCTCODE in ('" + acType + "'))) d, "
                            + " (select acno as eno,max(effdt) as edt from accountstatus where effdt <='" + toDt + "' group by acno) e, "
                            + " (select acno as fno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) f "
                            + " where a.acno=d.ano and a.effdt=e.edt and a.acno = e.eno and a.spno = f.sno and a.acno = f.fno "
                            + " and (spflag in (11,12,13,14,3,6,7,8,2,1)) AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 and d.actCode = t.acctcode ").getResultList();
                }
            }
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    String acno = element.get(0).toString();
                    String name = element.get(1).toString();
                    String freq = element.get(2).toString();
                    String loanStatus = element.get(3).toString();
                    String acnature = element.get(5).toString();
                    String actype = element.get(4).toString();
                    String acctDesc = element.get(6).toString();
                    BigDecimal rec = new BigDecimal("0");
                    BigDecimal npaRec = new BigDecimal("0");
                    BigDecimal balance = new BigDecimal("0");
                    String tempToDt = toDt;
                    String tempFromDt = fromDt;
//                    String loanStatus = interBranchFacade.fnGetLoanStatusTillDate(acno, tempToDt);
                    /* Previous history checking for existance of NPA status of that account  */
                    if (loanStatus.equalsIgnoreCase("STANDARD") || loanStatus.equalsIgnoreCase("OPE")) {
                        List tempList = em.createNativeQuery("SELECT * FROM accountstatus WHERE /*SPFLAG IN (11,12,13) AND*/ ACNO ='" + acno + "' and EFFDT between '" + tempFromDt + "' and '" + tempToDt + "'").getResultList();
                        if (tempList.size() > 0) {
                            /*Getting max(effdate) for that account */
                            tempList = em.createNativeQuery("Select a.spflag, date_format(a.effdt,'%Y%m%d'), c.description from accountstatus a,codebook c where acno='" + acno + "' "
                                    + "and effdt=(Select max(Effdt) from accountstatus where date_format(EffDt,'%Y%m%d') between '" + tempFromDt + "' and '" + tempToDt + "'  and acno='" + acno + "' "
                                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                                    + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d') between '" + tempFromDt + "' and '" + tempToDt + "'  "
                                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))) "
                                    + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d') between '" + tempFromDt + "' and '" + tempToDt + "'  and (spflag in (11,12,13,14,3,6,7,8,2) or "
                                    + "(spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%'))) "
                                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                                    + "AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 ").getResultList();
                            if (!tempList.isEmpty()) {
                                Vector tmpVect = (Vector) tempList.get(0);
                                String status = tmpVect.get(0).toString();
                                String effDt = tmpVect.get(1).toString();
                                if (status.equalsIgnoreCase("1")) {
                                    List tempList1 = em.createNativeQuery("SELECT * FROM accountstatus WHERE SPFLAG IN (11,12,13) AND ACNO ='" + acno + "' and EFFDT <'" + effDt + "'").getResultList();
                                    if (tempList1.size() > 0) {
                                        /*Kalilabad: If account status mark as NPA and same date it was mark as Active then it is consider as Active*/
                                        tempList1 = em.createNativeQuery("Select a.spflag, date_format(a.effdt,'%Y%m%d'), c.description  from accountstatus a,codebook c where acno='" + acno + "' "
                                                + "and effdt=(Select max(Effdt) from accountstatus where date_format(EffDt,'%Y%m%d')<'" + effDt + "' and acno='" + acno + "' "
                                                + "and (spflag in (11,12,13)) "
                                                + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d')<'" + effDt + "' "
                                                + "and (spflag in (11,12,13) ))) "
                                                + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d')<'" + effDt + "' and (spflag in (11,12,13))) "
                                                + "and (spflag in (11,12,13) ) "
                                                + "AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3").getResultList();
                                        tmpVect = (Vector) tempList1.get(0);
                                        status = tmpVect.get(0).toString();
                                        if (status.equalsIgnoreCase("11")) {
                                            loanStatus = "SUB";
                                        } else if (status.equalsIgnoreCase("12")) {
                                            loanStatus = "DOU";
                                        } else if (status.equalsIgnoreCase("13")) {
                                            loanStatus = "LOS";
                                        }
                                    }
                                }
                            }
                        }
                    }
                    /**
                     * ************************************************************************
                     */
                    if (loanStatus.equalsIgnoreCase("DOU") || loanStatus.equalsIgnoreCase("SUB") || loanStatus.equalsIgnoreCase("LOS")) {
                        List tempList = em.createNativeQuery("SELECT date_format(MIN(EFFDT),'%Y%m%d') FROM accountstatus WHERE SPFLAG IN (11,12,13) AND ACNO ='" + acno + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            Vector minElement = (Vector) tempList.get(0);
                            if (minElement.get(0) != null) {
                                String minDt = minElement.get(0).toString();
                                if (ymd.parse(minDt).compareTo(ymd.parse(tempFromDt)) > 0) {
                                    tempFromDt = minDt;
                                } else if (ymd.parse(tempFromDt).compareTo(ymd.parse(minDt)) > 0) {
                                    tempFromDt = tempFromDt;
                                }
                            }
                        }
                        tempList = em.createNativeQuery("SELECT date_format(MAX(EFFDT),'%Y%m%d') FROM accountstatus WHERE SPFLAG IN (1) AND ACNO ='" + acno + "' AND EFFDT between '" + fromDt + "' and  '" + toDt + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            Vector maxElement = (Vector) tempList.get(0);
                            if (maxElement.get(0) != null) {
                                String maxDt = maxElement.get(0).toString();
                                if (ymd.parse(maxDt).compareTo(ymd.parse(tempToDt)) > 0) {
                                    tempToDt = tempToDt;
                                } else if (ymd.parse(tempToDt).compareTo(ymd.parse(maxDt)) > 0) {
                                    tempToDt = maxDt;
                                }
                            }
                        }
                    }

//                    String acnature = ftsFacade.getAcNatureByCode(acno.substring(3,4));

                    if (acnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List balList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM ca_recon WHERE DT <='" + tempToDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
                        if (!balList.isEmpty()) {
                            Vector balElement = (Vector) balList.get(0);
                            balance = new BigDecimal(formatter.format(balElement.get(0)));
                        }

                        List recList = em.createNativeQuery("SELECT ifnull(SUM(CRAMT),0) FROM ca_recon WHERE DT BETWEEN '" + tempFromDt + "' AND '" + tempToDt + "' AND ACNO='" + acno + "' AND AUTH='Y'").getResultList();
                        if (!recList.isEmpty()) {
                            Vector recElement = (Vector) recList.get(0);
                            rec = new BigDecimal(formatter.format(recElement.get(0)));
                        }
                    } else {
                        List balList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM loan_recon WHERE DT <='" + tempToDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
                        if (!balList.isEmpty()) {
                            Vector balElement = (Vector) balList.get(0);
                            balance = new BigDecimal(formatter.format(balElement.get(0)));
                        }

                        List recList = em.createNativeQuery("SELECT ifnull(SUM(CRAMT),0) FROM loan_recon WHERE DT BETWEEN '" + tempFromDt + "' AND '" + tempToDt + "' AND ACNO='" + acno + "' AND AUTH='Y'").getResultList();
                        if (!recList.isEmpty()) {
                            Vector recElement = (Vector) recList.get(0);
                            rec = new BigDecimal(formatter.format(recElement.get(0)));
                        }
                    }

//                    if (balance.compareTo(new BigDecimal("0")) == -1 || balance.compareTo(new BigDecimal("0")) == 0) {
                    if (loanStatus.equalsIgnoreCase("DOU") || loanStatus.equalsIgnoreCase("SUB") || loanStatus.equalsIgnoreCase("LOS")) {
                        NpaRecoveryPojo pojo = new NpaRecoveryPojo();
                        pojo.setAcno(acno);
                        pojo.setBalance(balance);
                        List recList = em.createNativeQuery("SELECT ifnull(SUM(CRAMT),0) FROM npa_recon WHERE DT BETWEEN '" + tempFromDt + "' AND '" + tempToDt + "' AND ACNO='" + acno + "' AND AUTH='Y'").getResultList();
                        if (!recList.isEmpty()) {
                            Vector recElement = (Vector) recList.get(0);
                            npaRec = new BigDecimal(formatter.format(recElement.get(0)));
                        }

                        if (loanStatus.equalsIgnoreCase("DOU")) {
                            pojo.setSubRec(new BigDecimal("0"));
                            pojo.setDeRec(npaRec);
                            pojo.setLoanRec(rec.subtract(npaRec));
                            pojo.setLossRec(new BigDecimal("0"));
                            pojo.setNpaStatus("DOUBT FUL");
                            pojo.setTotalLoanRec(rec);
                        } else if (loanStatus.equalsIgnoreCase("SUB")) {
                            pojo.setSubRec(npaRec);
                            pojo.setDeRec(new BigDecimal("0"));
                            pojo.setLoanRec(rec.subtract(npaRec));
                            pojo.setLossRec(new BigDecimal("0"));
                            pojo.setNpaStatus("SUB STANDARD");
                            pojo.setTotalLoanRec(rec);
                        } else if (loanStatus.equalsIgnoreCase("LOS")) {
                            pojo.setSubRec(new BigDecimal("0"));
                            pojo.setDeRec(new BigDecimal("0"));
                            pojo.setLossRec(npaRec);
                            pojo.setLoanRec(rec.subtract(npaRec));
                            pojo.setNpaStatus("LOSS");
                            pojo.setTotalLoanRec(rec);
                        }
                        pojo.setTotalNpaRec(npaRec);
                        pojo.setName(name);
//                            String actype = commonReport.getAcTypeByAcNo(acno);
                        pojo.setAcType(actype);
//                            String acctDesc = commonReport.getAcctDecription(actype);
//                            if (actype.equalsIgnoreCase(CbsAcCodeConstant.CASH_CREDIT.getAcctCode())) {
//                                pojo.setAcName(acctDesc + "(" + actype + ")");
//                            } else if (actype.equalsIgnoreCase(CbsAcCodeConstant.DEMAND_LOAN.getAcctCode())) {
//                                pojo.setAcName(acctDesc + "(" + actype + ")");
//                            } else if (actype.equalsIgnoreCase(CbsAcCodeConstant.OVER_DRAFT.getAcctCode())) {
//                                pojo.setAcName(acctDesc + "(" + actype + ")");
//                            } else if (actype.equalsIgnoreCase(CbsAcCodeConstant.TERM_LOAN.getAcctCode())) {
//                                pojo.setAcName(acctDesc + "(" + actype + ")");
//                            } else if (actype.equalsIgnoreCase(CbsAcCodeConstant.DC_AC.getAcctCode())) {
//                                pojo.setAcName(acctDesc + "(" + actype + ")");
//                            } else if (actype.equalsIgnoreCase(CbsAcCodeConstant.PB_AC.getAcctCode())) {
//                                pojo.setAcName(acctDesc + "(" + actype + ")");
//                            } else if (actype.equalsIgnoreCase(CbsAcCodeConstant.PL_AC.getAcctCode())) {
//                                pojo.setAcName(acctDesc + "(" + actype + ")");
//                            } else if (actype.equalsIgnoreCase(CbsAcCodeConstant.SL_AC.getAcctCode())) {
//                                pojo.setAcName(acctDesc + "(" + actype + ")");
//                            } else{
                        pojo.setAcName(acctDesc + "(" + actype + ")");
//                            }
                        pojo.setBranchName(commonReport.getBranchNameByBrncode(ftsFacade.getCurrentBrnCode(acno)));
                        if (!(rec.compareTo(BigDecimal.ZERO) == 0)) {
                            resultList.add(pojo);
                        }
                    }
//                    }
                    Collections.sort(resultList, new NpaComparatorByAcno());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    /*
     * This report is made for Morena
     * Column:==>   AcNo    CustName    Balance     MemorendumInt       Recovered(Balance/Memorendum Bal.)
     * Pojo:==>     acno    acName      balance     subRec              deRec(for Balance)   lossRec(for Memorendum Bal.)
     */
    public List<NpaRecoveryPojo> getNpaRecoveryReportData(String acType, String fromDt, String toDt, String orgnBrCode) throws ApplicationException {
        List<NpaRecoveryPojo> resultList = new ArrayList<NpaRecoveryPojo>();
        List dataList = null;
        try {
            if (orgnBrCode.equalsIgnoreCase("0A")) {
                if (acType.equalsIgnoreCase("ALL")) {
//                    dataList = em.createNativeQuery("SELECT A.ACNO,A.CUSTNAME NAME,C.INT_APP_FREQ, d.acctNature, d.AcctCode, d.AcctDesc   FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C, accounttypemaster d  WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND "
//                            + " d.AcctCode = SUBSTRING(A.ACNO,3,2) and  SUBSTRING(A.ACNO,3,2)IN (SELECT ACCTCODE FROM accounttypemaster "
//                            + "WHERE ACCTNATURE IN ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "')  and crdbFlag in ('D','B')) AND SUBSTRING(A.ACNO,3,2)<>''").getResultList();
                    dataList = em.createNativeQuery("Select d.ano,d.NAME,d.FREQ,substring(c.description,1,3),d.actCode,t.acctnature,t.acctdesc, e.edt, f.sno from accountstatus a,codebook c,accounttypemaster t, "
                            + " (SELECT A.ACNO as ano,A.CUSTNAME NAME,C.INT_APP_FREQ as freq,A.accttype as actCode FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C "
                            + " WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO  AND SUBSTRING(A.ACNO,3,2)IN (SELECT ACCTCODE FROM accounttypemaster "
                            + " WHERE ACCTNATURE IN ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "')  and crdbFlag in ('D','B'))) d, "
                            + " (select acno as eno,max(effdt) as edt from accountstatus where effdt <='" + toDt + "' group by acno) e, "
                            + " (select acno as fno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) f "
                            + " where a.acno=d.ano and a.effdt=e.edt and a.acno = e.eno and a.spno = f.sno and a.acno = f.fno "
                            + " and (spflag in (11,12,13,14,3,6,7,8,2,1)) AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 and d.actCode = t.acctcode ").getResultList();
                } else {
//                    dataList = em.createNativeQuery("SELECT A.ACNO,A.CUSTNAME NAME,C.INT_APP_FREQ, d.acctNature, d.AcctCode, d.AcctDesc  FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C, accounttypemaster d   WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND  d.AcctCode = SUBSTRING(A.ACNO,3,2) and SUBSTRING(A.ACNO,3,2)='" + acType + "' AND SUBSTRING(A.ACNO,3,2)<>'' ").getResultList();
                    dataList = em.createNativeQuery("Select d.ano,d.NAME,d.FREQ,substring(c.description,1,3),d.actCode,t.acctnature,t.acctdesc, e.edt, f.sno from accountstatus a,codebook c,accounttypemaster t, "
                            + " (SELECT A.ACNO as ano,A.CUSTNAME NAME,C.INT_APP_FREQ as freq,A.accttype as actCode FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C "
                            + " WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND SUBSTRING(A.ACNO,3,2)IN (SELECT ACCTCODE FROM accounttypemaster "
                            + " WHERE  ACCTCODE in ('" + acType + "') )) d, "
                            + " (select acno as eno,max(effdt) as edt from accountstatus where effdt <='" + toDt + "' group by acno) e, "
                            + " (select acno as fno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) f "
                            + " where a.acno=d.ano and a.effdt=e.edt and a.acno = e.eno and a.spno = f.sno and a.acno = f.fno "
                            + " and (spflag in (11,12,13,14,3,6,7,8,2,1)) AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 and d.actCode = t.acctcode  ").getResultList();
                }
            } else {
                if (acType.equalsIgnoreCase("ALL")) {
//                    dataList = em.createNativeQuery("SELECT A.ACNO,A.CUSTNAME NAME,C.INT_APP_FREQ, d.acctNature, d.AcctCode, d.AcctDesc FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C, accounttypemaster d   WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND "
//                            + " d.AcctCode = SUBSTRING(A.ACNO,3,2) and SUBSTRING(A.ACNO,3,2)IN (SELECT ACCTCODE FROM accounttypemaster "
//                            + "WHERE ACCTNATURE IN ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "')  and crdbFlag in ('D','B')) AND SUBSTRING(A.ACNO,3,2)<>'' AND A.CURBRCODE='" + orgnBrCode + "'").getResultList();
                    dataList = em.createNativeQuery("Select d.ano,d.NAME,d.FREQ,substring(c.description,1,3),d.actCode,t.acctnature,t.acctdesc, e.edt, f.sno from accountstatus a,codebook c,accounttypemaster t, "
                            + " (SELECT A.ACNO as ano,A.CUSTNAME NAME,C.INT_APP_FREQ as freq,A.accttype as actCode FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C "
                            + " WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND A.CURBRCODE='" + orgnBrCode + "' AND SUBSTRING(A.ACNO,3,2)IN (SELECT ACCTCODE FROM accounttypemaster "
                            + " WHERE ACCTNATURE IN ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "')  and crdbFlag in ('D','B'))) d, "
                            + " (select acno as eno,max(effdt) as edt from accountstatus where effdt <='" + toDt + "' group by acno) e, "
                            + " (select acno as fno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) f "
                            + " where a.acno=d.ano and a.effdt=e.edt and a.acno = e.eno and a.spno = f.sno and a.acno = f.fno "
                            + " and (spflag in (11,12,13,14,3,6,7,8,2,1)) AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 and d.actCode = t.acctcode ").getResultList();
                } else {
//                    dataList = em.createNativeQuery("SELECT A.ACNO,A.CUSTNAME NAME,C.INT_APP_FREQ, d.acctNature, d.AcctCode, d.AcctDesc FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C, accounttypemaster d  WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND  d.AcctCode = SUBSTRING(A.ACNO,3,2) and SUBSTRING(A.ACNO,3,2)='" + acType + "' AND SUBSTRING(A.ACNO,3,2)<>'' AND A.CURBRCODE='" + orgnBrCode + "'").getResultList();
                    dataList = em.createNativeQuery("Select d.ano,d.NAME,d.FREQ,substring(c.description,1,3),d.actCode,t.acctnature,t.acctdesc, e.edt, f.sno from accountstatus a,codebook c,accounttypemaster t, "
                            + " (SELECT A.ACNO as ano,A.CUSTNAME NAME,C.INT_APP_FREQ as freq,A.accttype as actCode FROM accountmaster A,loan_appparameter B,cbs_loan_acc_mast_sec C "
                            + " WHERE A.ACNO=B.ACNO AND A.ACNO = C.ACNO AND A.CURBRCODE='" + orgnBrCode + "' AND SUBSTRING(A.ACNO,3,2)IN (SELECT ACCTCODE FROM accounttypemaster "
                            + " WHERE  ACCTCODE in ('" + acType + "'))) d, "
                            + " (select acno as eno,max(effdt) as edt from accountstatus where effdt <='" + toDt + "' group by acno) e, "
                            + " (select acno as fno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) f "
                            + " where a.acno=d.ano and a.effdt=e.edt and a.acno = e.eno and a.spno = f.sno and a.acno = f.fno "
                            + " and (spflag in (11,12,13,14,3,6,7,8,2,1)) AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 and d.actCode = t.acctcode ").getResultList();
                }
            }
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    String acno = element.get(0).toString();
                    String name = element.get(1).toString();
                    String freq = element.get(2).toString();
                    String loanStatus = element.get(3).toString();
                    String acnature = element.get(5).toString();
                    String actype = element.get(4).toString();
                    String acctDesc = element.get(6).toString();
                    BigDecimal rec = new BigDecimal("0");
                    BigDecimal balance = new BigDecimal("0");
                    BigDecimal memoBal = new BigDecimal("0");
                    BigDecimal recPrin = new BigDecimal("0");
                    BigDecimal recInt = new BigDecimal("0");
                    BigDecimal postedInt = new BigDecimal("0");
                    BigDecimal postedCrMemoInt = new BigDecimal("0");
                    String tempToDt = toDt;
                    String tempFromDt = fromDt;
//                    String loanStatus = interBranchFacade.fnGetLoanStatusTillDate(acno, tempToDt);
                    /* Previous history checking for existance of NPA status of that account  */
                    if (loanStatus.equalsIgnoreCase("STANDARD") || loanStatus.equalsIgnoreCase("OPE")) {
                        List tempList = em.createNativeQuery("SELECT * FROM accountstatus WHERE /*SPFLAG IN (11,12,13) AND*/ ACNO ='" + acno + "' and EFFDT between '" + tempFromDt + "' and '" + tempToDt + "'").getResultList();
                        if (tempList.size() > 0) {
                            /*Getting max(effdate) for that account */
                            tempList = em.createNativeQuery("Select a.spflag, date_format(a.effdt,'%Y%m%d'), c.description from accountstatus a,codebook c where acno='" + acno + "' "
                                    + "and effdt=(Select max(Effdt) from accountstatus where date_format(EffDt,'%Y%m%d') between '" + tempFromDt + "' and '" + tempToDt + "'  and acno='" + acno + "' "
                                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                                    + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d') between '" + tempFromDt + "' and '" + tempToDt + "'  "
                                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))) "
                                    + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d') between '" + tempFromDt + "' and '" + tempToDt + "'  and (spflag in (11,12,13,14,3,6,7,8,2) or "
                                    + "(spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%'))) "
                                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                                    + "AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 ").getResultList();
                            Vector tmpVect = (Vector) tempList.get(0);
                            String status = tmpVect.get(0).toString();
                            String effDt = tmpVect.get(1).toString();
                            if (status.equalsIgnoreCase("1")) {
                                List tempList1 = em.createNativeQuery("SELECT * FROM accountstatus WHERE SPFLAG IN (11,12,13) AND ACNO ='" + acno + "' and EFFDT <'" + effDt + "'").getResultList();
                                if (tempList1.size() > 0) {
                                    /*Kalilabad: If account status mark as NPA and same date it was mark as Active then it is consider as Active*/
                                    tempList1 = em.createNativeQuery("Select a.spflag, date_format(a.effdt,'%Y%m%d'), c.description  from accountstatus a,codebook c where acno='" + acno + "' "
                                            + "and effdt=(Select max(Effdt) from accountstatus where date_format(EffDt,'%Y%m%d')<'" + effDt + "' and acno='" + acno + "' "
                                            + "and (spflag in (11,12,13)) "
                                            + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d')<'" + effDt + "' "
                                            + "and (spflag in (11,12,13) ))) "
                                            + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d')<'" + effDt + "' and (spflag in (11,12,13))) "
                                            + "and (spflag in (11,12,13) ) "
                                            + "AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3").getResultList();
                                    tmpVect = (Vector) tempList1.get(0);
                                    status = tmpVect.get(0).toString();
                                    if (status.equalsIgnoreCase("11")) {
                                        loanStatus = "SUB";
                                    } else if (status.equalsIgnoreCase("12")) {
                                        loanStatus = "DOU";
                                    } else if (status.equalsIgnoreCase("13")) {
                                        loanStatus = "LOS";
                                    }
                                }
                            }
                        }
                    }
                    /**
                     * ************************************************************************
                     */
                    if (loanStatus.equalsIgnoreCase("DOU") || loanStatus.equalsIgnoreCase("SUB") || loanStatus.equalsIgnoreCase("LOS")) {
                        List tempList = em.createNativeQuery("SELECT date_format(MIN(EFFDT),'%Y%m%d') FROM accountstatus WHERE SPFLAG IN (11,12,13) AND ACNO ='" + acno + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            Vector minElement = (Vector) tempList.get(0);
                            String minDt = minElement.get(0).toString();
                            if (ymd.parse(minDt).compareTo(ymd.parse(tempFromDt)) > 0) {
                                tempFromDt = minDt;
                            } else if (ymd.parse(tempFromDt).compareTo(ymd.parse(minDt)) > 0) {
                                tempFromDt = tempFromDt;
                            }
                        }
                        tempList = em.createNativeQuery("SELECT date_format(MAX(EFFDT),'%Y%m%d') FROM accountstatus WHERE SPFLAG IN (1) AND ACNO ='" + acno + "' AND EFFDT>='" + fromDt + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            Vector maxElement = (Vector) tempList.get(0);
                            if (maxElement.get(0) != null) {
                                String maxDt = maxElement.get(0).toString();
                                if (ymd.parse(maxDt).compareTo(ymd.parse(tempToDt)) > 0) {
                                    tempToDt = tempToDt;
                                } else if (ymd.parse(tempToDt).compareTo(ymd.parse(maxDt)) > 0) {
                                    tempToDt = maxDt;
                                }
                            }
                        }
                    }

                    if (acnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List balList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM ca_recon WHERE DT <='" + tempToDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
                        if (!balList.isEmpty()) {
                            Vector balElement = (Vector) balList.get(0);
                            balance = new BigDecimal(formatter.format(balElement.get(0)));
                        }

                        List memoList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM npa_recon WHERE DT <='" + tempToDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
                        if (!memoList.isEmpty()) {
                            Vector memoElement = (Vector) memoList.get(0);
                            memoBal = new BigDecimal(formatter.format(memoElement.get(0)));
                        }

                        List recList = em.createNativeQuery("SELECT ifnull(SUM(CRAMT),0) FROM ca_recon WHERE DT BETWEEN '" + tempFromDt + "' AND '" + tempToDt + "' AND ACNO='" + acno + "' AND AUTH='Y'").getResultList();
                        if (!recList.isEmpty()) {
                            Vector recElement = (Vector) recList.get(0);
                            rec = new BigDecimal(formatter.format(recElement.get(0)));
                        }
                    } else {
                        List balList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM loan_recon WHERE DT <='" + tempToDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
                        if (!balList.isEmpty()) {
                            Vector balElement = (Vector) balList.get(0);
                            balance = new BigDecimal(formatter.format(balElement.get(0)));
                        }

                        List memoList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM npa_recon WHERE DT <='" + tempToDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
                        if (!memoList.isEmpty()) {
                            Vector memoElement = (Vector) memoList.get(0);
                            memoBal = new BigDecimal(formatter.format(memoElement.get(0)));
                        }

                        List recList = em.createNativeQuery("SELECT ifnull(SUM(CRAMT),0) FROM loan_recon WHERE DT BETWEEN '" + tempFromDt + "' AND '" + tempToDt + "' AND ACNO='" + acno + "' AND AUTH='Y'").getResultList();
                        if (!recList.isEmpty()) {
                            Vector recElement = (Vector) recList.get(0);
                            rec = new BigDecimal(formatter.format(recElement.get(0)));
                        }
                    }

                    List postedList = em.createNativeQuery("SELECT IFNULL(SUM(DRAMT),0), IFNULL(SUM(CRAMT),0) FROM npa_recon WHERE DT BETWEEN '" + tempFromDt + "' AND '" + tempToDt + "' AND ACNO='" + acno + "' AND AUTH='Y'").getResultList();
                    if (!postedList.isEmpty()) {
                        Vector recoveredElement = (Vector) postedList.get(0);
                        postedInt = new BigDecimal(formatter.format(recoveredElement.get(0)));
                        postedCrMemoInt = new BigDecimal(formatter.format(recoveredElement.get(1)));
                    }

                    if (rec.compareTo(postedInt) == -1) {
                        recPrin = recPrin;
                        recInt = rec;
                    } else {
                        recPrin = rec.subtract(postedCrMemoInt);//recPrin = rec.subtract(postedInt);
                        balance = balance; //                   balance = balance.subtract(recPrin);
                        recInt = postedCrMemoInt;//recInt = postedInt;
                    }

//                    if (balance.compareTo(new BigDecimal("0")) == -1 || balance.compareTo(new BigDecimal("0")) == 0) {
                    if (loanStatus.equalsIgnoreCase("DOU") || loanStatus.equalsIgnoreCase("SUB") || loanStatus.equalsIgnoreCase("LOS")) {
                        NpaRecoveryPojo pojo = new NpaRecoveryPojo();
                        pojo.setAcno(acno);
                        pojo.setBalance(balance);
                        pojo.setSubRec(memoBal);
                        pojo.setDeRec(recPrin);
                        pojo.setLossRec(recInt);
                        pojo.setNpaStatus(loanStatus.equalsIgnoreCase("DOU") ? "DOUBT FUL" : (loanStatus.equalsIgnoreCase("SUB") ? "SUB STD" : "LOSS"));
                        pojo.setName(name);
                        pojo.setAcType(actype);
                        pojo.setAcName(acctDesc + "(" + actype + ")");
                        pojo.setBranchName(commonReport.getBranchNameByBrncode(ftsFacade.getCurrentBrnCode(acno)));
                        if (!(rec.compareTo(BigDecimal.ZERO) == 0)) {
                            resultList.add(pojo);
                        }
                    }
//                    }
                }
            }
            if (!resultList.isEmpty()) {
                Collections.sort(resultList, new NpaComparatorByAcno());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public List<HoReconsilePojo> getReconsiledEntry(String fromDt, String toDt, String orgnBrCode) throws ApplicationException {
        List<HoReconsilePojo> resultList = new ArrayList<HoReconsilePojo>();
        try {
            List dataList = em.createNativeQuery("select Org_Originating_Branch,Org_Responding_Branch,Org_Entry_Type,Org_Instrument_Amount,date_format(Org_Origin_Dt,'%d/%m/%Y') "
                    + "from ho_reconsile_entry where Org_Origin_Dt between '" + fromDt + "' and '" + toDt + "' order by Org_Origin_Dt,Org_Originating_Branch").getResultList();
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    String originBranchName = commonReport.getBranchNameByBrncode(element.get(0).toString());
                    String destinationBranchName = commonReport.getBranchNameByBrncode(element.get(1).toString());
                    String transactionType = commonReport.getTransactionType(element.get(2).toString());
                    BigDecimal amount = new BigDecimal(element.get(3).toString());
                    String transactionDate = element.get(4).toString();

                    HoReconsilePojo pojo = new HoReconsilePojo();
                    pojo.setBaseBranch(originBranchName);
                    pojo.setRespondingBranch(destinationBranchName);
                    pojo.setTranType(transactionType);
                    pojo.setAmount(amount);
                    pojo.setTranDt(transactionDate);

                    resultList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public List<NpaSummaryPojo> getNpaSummary(String brnCode, String toDt) throws ApplicationException {
        List<NpaSummaryPojo> resultList = new ArrayList<NpaSummaryPojo>();
        List dataList = null;
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        try {
            Map<String, String> acMap = new HashMap<>();
            List acDescList = em.createNativeQuery("select AcctCode,AcctDesc from accounttypemaster where acctnature in('TL','DL','CA') and CrDbFlag <> 'C'").getResultList();
            for (int i = 0; i < acDescList.size(); i++) {
                Vector vtr = (Vector) acDescList.get(i);
                acMap.put(vtr.get(0).toString(), vtr.get(1).toString());
            }

//            String query = "select a.acno from accountstatus a, "
//                    + "(select acno,max(effdt) as edt from accountstatus where effdt <='" + toDt + "' group by acno) b,"
//                    + "(select acno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) c ,"
//                    + "accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and "
//                    + "a.effdt = b.edt and a.spno = c.sno and a.spflag in(11,12,13) and a.effdt <='" + toDt + "' and "
//                    + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + toDt + "')";
//            if (brnCode.equalsIgnoreCase("0A")) {
//                dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN (" + query + ") ORDER BY ACNO").getResultList();
//
////                dataList = em.createNativeQuery("SELECT A.ACNO,sum(ifnull(A.CrAmt,0))-sum(ifnull(A.DrAmt,0)) as AMOUNT  FROM accountmaster B,loan_recon A "
////                        + " WHERE A.ACNO=B.ACNO AND A.DT<='" + toDt + "' AND (B.CLOSINGDATE='' OR B.closingDATE>'" + toDt + "' OR ifnull(B.CLOSINGDATE,'')='') AND "
////                        + " B.OPENINGDT<='" + toDt
////                        + "' GROUP BY A.ACNO "
////                        + " UNION ALL "
////                        + " SELECT A.ACNO,sum(ifnull(A.CrAmt,0))-sum(ifnull(A.DrAmt,0)) as AMOUNT  FROM accountmaster B,ca_recon A WHERE "
////                        + " A.ACNO=B.ACNO AND A.DT<='" + toDt + "'  AND (B.CLOSINGDATE='' OR B.closingDATE>'" + toDt + "' OR ifnull(B.CLOSINGDATE,'')='')"
////                        + " AND  B.OPENINGDT<='" + toDt
////                        + "' GROUP BY A.ACNO").getResultList();
//
//            } else {
//                dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN (" + query + " AND ac.CURBRCODE='" + brnCode + "') ORDER BY ACNO").getResultList();
//
////                dataList = em.createNativeQuery("SELECT A.ACNO,sum(ifnull(A.CrAmt,0))-sum(ifnull(A.DrAmt,0)) as AMOUNT  FROM accountmaster B,loan_recon A "
////                        + " WHERE A.ACNO=B.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brnCode + "' AND A.DT<='" + toDt + "'  AND (B.CLOSINGDATE='' OR B.closingDATE>'" + toDt + "' OR ifnull(B.CLOSINGDATE,'')='') AND "
////                        + " B.OPENINGDT<='" + toDt
////                        + "' GROUP BY A.ACNO "
////                        + " UNION ALL "
////                        + " SELECT A.ACNO,sum(ifnull(A.CrAmt,0))-sum(ifnull(A.DrAmt,0)) as AMOUNT  FROM accountmaster B,ca_recon A WHERE "
////                        + " A.ACNO=B.ACNO AND SUBSTRING(A.ACNO,1,2) = '" + brnCode + "' AND A.DT<='" + toDt + "'  AND (B.CLOSINGDATE='' OR B.closingDATE>'" + toDt + "' OR ifnull(B.CLOSINGDATE,'')='')"
////                        + " AND  B.OPENINGDT<='" + toDt
////                        + "' GROUP BY A.ACNO").getResultList();
//
//            }
//            List<NpaAccountDetailPojo> resultlist = loanRemote.getNpaDetail("ALL","ALL", toDt, brnCode);
            List<NpaStatusReportPojo> resultlist = getNpaStatusReportDataOptimized("0", "ALL", "19000101", toDt, "", brnCode.equalsIgnoreCase("90") ? "0A" : brnCode, "Y");
            if (!resultlist.isEmpty()) {
                for (int i = 0; i < resultlist.size(); i++) {
//                    Vector dataListVect = (Vector) dataList.get(i);
                    String acno = resultlist.get(i).getAcno();
                    double balAsOn = resultlist.get(i).getBalance().doubleValue();//loanRemote.fnBalosForAccountAsOn(acno, toDt);
                    BigDecimal balance = resultlist.get(i).getBalance();//new BigDecimal(balAsOn);
                    //BigDecimal balance = new BigDecimal(dataListVect.get(1).toString());
                    String loanStatus = resultlist.get(i).getPresentStatus();//interBranchFacade.fnGetLoanStatusTillDate(acno, toDt);

                    if (loanStatus.contains("SUB")) {
                        loanStatus = "SUB";
                    } else if (loanStatus.contains("DOU")) {
                        loanStatus = "DOU";
                    } else if (loanStatus.contains("LOS")) {
                        loanStatus = "LOS";
                    }
                    if (loanStatus.equalsIgnoreCase("DOU") || loanStatus.equalsIgnoreCase("SUB") || loanStatus.equalsIgnoreCase("LOS")) {

//                        if (balance.compareTo(new BigDecimal("0")) == -1 || balance.compareTo(new BigDecimal("0")) == 0) {
                        NpaSummaryPojo pojo = new NpaSummaryPojo();
                        long dayDiff = 0;
                        BigDecimal secAmt = new BigDecimal("0");
                        BigDecimal uSecAmt = new BigDecimal("0");
                        long daysDbt1;
                        long daysDbt2;
                        long daysDbt3;
                        Date effDate;
                        Date toDayDate = new Date();

                        if (loanStatus.equalsIgnoreCase("DOU")) {
//                            List accStatusList = em.createNativeQuery("select * from accountstatus where acno= '" + acno + "' and spflag=12").getResultList();
//
//                            if (!accStatusList.isEmpty()) {
//                                accStatusList = em.createNativeQuery("select max(effdt) from accountstatus where acno= '" + acno + "' and spflag=12").getResultList();
//                                Vector accStatusVect = (Vector) accStatusList.get(0);
//                                effDate = (Date) accStatusVect.get(0);
//                                dayDiff = CbsUtil.dayDiff(effDate, toDayDate);
//                                if (dayDiff > 0) {
                            pojo.setNpaDays(dayDiff);
                            pojo.setNpaDate(dmy.parse(resultlist.get(i).getDoubtFullDt()));
//                                }
                            pojo.setBalance(balance);
//                                List npaMastTypeList = em.createNativeQuery("select DAYS_DBT1,DAYS_DBT2,DAYS_DBT3 from npa_master_type where ACCTCODE= '" + acno.substring(2, 4) + "'").getResultList();
//                                if (!npaMastTypeList.isEmpty()) {
//                                    Vector npaMastTypeVect = (Vector) npaMastTypeList.get(0);
//                                    daysDbt1 = Long.parseLong(npaMastTypeVect.get(0).toString());
//                                    daysDbt2 = Long.parseLong(npaMastTypeVect.get(1).toString());
//                                    daysDbt3 = Long.parseLong(npaMastTypeVect.get(2).toString());
//////                            List lienValueList = em.createNativeQuery("SELECT ifnull(sum(lienvalue),0) from loansecurity where acno = '" + acno + "' and status = 'ACTIVE'").getResultList();
//////
////////                                    if (dayDiff >= daysDbt1 && dayDiff < daysDbt2) {
//////                            pojo.setNpaType(resultlist.get(i).getSortAcStatus().substring(2, 4));
//////
//////                            if (!lienValueList.isEmpty()) {
//////                                Vector lienValueVect = (Vector) lienValueList.get(0);
//////                                if (new BigDecimal(lienValueVect.get(0).toString()).compareTo(balance.abs()) == 1 || new BigDecimal(lienValueVect.get(0).toString()).compareTo(balance.abs()) == 0) {
//////                                    secAmt = secAmt.add(balance);
//////                                    pojo.setBalance(secAmt);
//////                                    pojo.setNpaTypeSecurity("Secured");
//////                                } else {
//////                                    uSecAmt = uSecAmt.add(balance);
//////                                    pojo.setBalance(uSecAmt);
//////                                    pojo.setNpaTypeSecurity("Unsecured");
//////                                }
//////                            } else {
//////                                uSecAmt = uSecAmt.add(balance);
//////                                pojo.setBalance(uSecAmt);
//////                                pojo.setNpaTypeSecurity("Unsecured");
//////                            }

//                                    } else if (dayDiff >= daysDbt2 && dayDiff < daysDbt3) {
//                                        pojo.setNpaType("D2");
//                                        if (!lienValueList.isEmpty()) {
//                                            Vector lienValueVect = (Vector) lienValueList.get(0);
//                                            if (new BigDecimal(lienValueVect.get(0).toString()).compareTo(balance.abs()) == 1 || new BigDecimal(lienValueVect.get(0).toString()).compareTo(balance.abs()) == 0) {
//                                                secAmt = secAmt.add(balance);
//                                                pojo.setBalance(secAmt);
//                                                pojo.setNpaTypeSecurity("Secured");
//                                            } else {
//                                                uSecAmt = uSecAmt.add(balance);
//                                                pojo.setBalance(uSecAmt);
//                                                pojo.setNpaTypeSecurity("Unsecured");
//                                            }
//                                        } else {
//                                            uSecAmt = uSecAmt.add(balance);
//                                            pojo.setBalance(uSecAmt);
//                                            pojo.setNpaTypeSecurity("Unsecured");
//                                        }
//                                    } else if (dayDiff >= daysDbt3) {
//                                        pojo.setNpaType("D3");
//                                        if (!lienValueList.isEmpty()) {
//                                            Vector lienValueVect = (Vector) lienValueList.get(0);
//                                            if (new BigDecimal(lienValueVect.get(0).toString()).compareTo(balance.abs()) == 1 || new BigDecimal(lienValueVect.get(0).toString()).compareTo(balance.abs()) == 0) {
//                                                secAmt = secAmt.add(balance);
//                                                pojo.setBalance(secAmt);
//                                                pojo.setNpaTypeSecurity("Secured");
//                                            } else {
//                                                uSecAmt = uSecAmt.add(balance);
//                                                pojo.setBalance(uSecAmt);
//                                                pojo.setNpaTypeSecurity("Unsecured");
//                                            }
//                                        } else {
//                                            uSecAmt = uSecAmt.add(balance);
//                                            pojo.setBalance(uSecAmt);
//                                            pojo.setNpaTypeSecurity("Unsecured");
//                                        }
//                                    }
//                                }
//                            }
                            pojo.setNpaType(resultlist.get(i).getPresentStatus());
                            pojo.setAcno(acno);
                            pojo.setNpa("2. Doubtful Assets");
                            pojo.setProductCode(acno.substring(2, 4));
                            pojo.setAccountDesc(acMap.get(acno.substring(2, 4)));
                        } else if (loanStatus.equalsIgnoreCase("SUB")) {
                            pojo.setAcno(acno);
                            pojo.setBalance(balance);
                            pojo.setNpa("1.Sub Standard Assets");
                            pojo.setProductCode(acno.substring(2, 4));
                            pojo.setAccountDesc(acMap.get(acno.substring(2, 4)));
                        } else if (loanStatus.equalsIgnoreCase("LOS")) {

                            pojo.setAcno(acno);
                            pojo.setBalance(balance);
                            pojo.setNpa("3.Loss Assets");
                            pojo.setProductCode(acno.substring(2, 4));
                            pojo.setAccountDesc(acMap.get(acno.substring(2, 4)));
                        }
                        resultList.add(pojo);
//                        }
                    }
//                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    /**
     * Addition For Npa Status Report
     */
    public List getAcStatus() throws ApplicationException {
        try {
            List stdList = loanRemote.getStdAssect();
            Vector vtr = (Vector) stdList.get(0);
            String stdAssect = vtr.get(0).toString();
            if (stdAssect.equalsIgnoreCase("Y")) {
                return em.createNativeQuery("select description from codebook where groupcode=47 and description in ('SUB STANDARD','DOUBT FUL','LOSS') union select'STANDARD'").getResultList();
            } else {
                return em.createNativeQuery("select description from codebook where groupcode=47 and description in ('SUB STANDARD','DOUBT FUL','LOSS')").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List getNpaReportAccountType() throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT DISTINCT ACCTCODE FROM accounttypemaster WHERE ACCTNATURE IN('CA','TL','DL') and CrDbFlag in('B','D') ORDER BY ACCTCODE").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<NpaStatusReportPojo> getNpaStatusReportData(String status, String acType, String asOnDt, String acStatus, String orgBrnCode) throws ApplicationException {
        List<NpaStatusReportPojo> resultList = new ArrayList<NpaStatusReportPojo>();
        List dataList = new ArrayList();
        BigDecimal balance = new BigDecimal("0");
        String npaDt = "";
        try {
            if (orgBrnCode.equalsIgnoreCase("0A")) {
                if (acType.equalsIgnoreCase("ALL")) {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT ACNO FROM accountmaster WHERE OPENINGDT<='" + asOnDt + "' AND (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + asOnDt + "')) ORDER BY ACNO").getResultList();
                } else {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT ACNO FROM accountmaster WHERE OPENINGDT<='" + asOnDt + "' AND (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + asOnDt + "') AND SUBSTRING(ACNO,3,2) in (" + acType + ")) ORDER BY ACNO").getResultList();
                }
            } else {
                if (acType.equalsIgnoreCase("ALL")) {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT ACNO FROM accountmaster WHERE OPENINGDT<='" + asOnDt + "' AND (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + asOnDt + "') AND CURBRCODE='" + orgBrnCode + "') ORDER BY ACNO").getResultList();
                } else {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT ACNO FROM accountmaster WHERE OPENINGDT<='" + asOnDt + "' AND (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + asOnDt + "') AND CURBRCODE='" + orgBrnCode + "') AND SUBSTRING(ACNO,3,2) in (" + acType + ") ORDER BY ACNO").getResultList();
                }
            }
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    String acno = element.get(0).toString();
                    String custname = element.get(1).toString();
                    List checkAcnoList = em.createNativeQuery("SELECT ACNO FROM accountstatus WHERE ACNO='" + acno + "'").getResultList();
                    if (!checkAcnoList.isEmpty()) {
                        double balAsOn = loanRemote.fnBalosForAccountAsOn(acno, asOnDt);
                        if (balAsOn != 0) {
                            String presentStatus = interBranchFacade.fnGetLoanStatusTillDate(acno, asOnDt);

                            String acnature = ftsFacade.getAccountNature(acno);
                            balance = new BigDecimal(balAsOn);
//                            if (acnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                                List balList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM ca_recon WHERE DT <='" + asOnDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
//                                if (!balList.isEmpty()) {
//                                    Vector balElement = (Vector) balList.get(0);
//                                    balance = new BigDecimal(formatter.format(balElement.get(0)));
//                                }
//                            } else {
//                                List balList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM loan_recon WHERE DT <='" + asOnDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
//                                if (!balList.isEmpty()) {
//                                    Vector balElement = (Vector) balList.get(0);
//                                    balance = new BigDecimal(formatter.format(balElement.get(0)));
//                                }
//                            }

                            if (status.equalsIgnoreCase("0")) {
                                if (presentStatus.equalsIgnoreCase("DOU") || presentStatus.equalsIgnoreCase("SUB") || presentStatus.equalsIgnoreCase("LOS")) {
                                    List minEffList = em.createNativeQuery("SELECT date_format(MAX(EFFDT),'%d/%m/%Y') FROM accountstatus a, codebook c WHERE ACNO='" + acno + "' AND SUBSTRING(description,1,3)='" + presentStatus + "' AND GROUPCODE = 3 and a.spflag = c.code and effdt <='" + asOnDt + "'").getResultList();
                                    Vector minElement = (Vector) minEffList.get(0);
                                    npaDt = minElement.get(0).toString();

                                    NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                    pojo.setAcno(acno);
                                    pojo.setCustName(custname);
                                    pojo.setBalance(balance);
                                    pojo.setStandardDt("");
                                    if (presentStatus.equalsIgnoreCase("SUB")) {
                                        pojo.setPresentStatus("SUB STANDARD");
                                        pojo.setSubStandardDt(npaDt);
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt("");
                                    } else if (presentStatus.equalsIgnoreCase("DOU")) {
                                        pojo.setPresentStatus("DOUBT FUL");
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt(npaDt);
                                        pojo.setLossDt("");
//                                        System.out.println("acNo:" + acno + "; dubtdt:" + npaDt + "; bal:" + balance);
                                    } else if (presentStatus.equalsIgnoreCase("LOS")) {
                                        pojo.setPresentStatus("LOSS");
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt(npaDt);
                                    }

                                    resultList.add(pojo);
                                }
                            } else if (status.equalsIgnoreCase("1")) {
                                if (presentStatus.equalsIgnoreCase(acStatus.substring(0, 3))) {
                                    List minEffList = em.createNativeQuery("select date_format(max(effdt),'%d/%m/%Y') from accountstatus a, codebook c where acno='" + acno + "' and substring(description,1,3)='" + presentStatus + "' and groupcode = 3 and a.spflag = c.code  and effdt <='" + asOnDt + "'").getResultList();
                                    Vector minElement = (Vector) minEffList.get(0);
                                    npaDt = minElement.get(0).toString();

                                    NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                    pojo.setAcno(acno);
                                    pojo.setCustName(custname);
                                    pojo.setBalance(balance);
                                    pojo.setPresentStatus(acStatus);
                                    pojo.setStandardDt("");
                                    if (acStatus.substring(0, 3).equalsIgnoreCase("SUB")) {
                                        pojo.setSubStandardDt(npaDt);
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt("");
                                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("DOU")) {
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt(npaDt);
                                        pojo.setLossDt("");
//                                        System.out.println("acNo:" + acno + "; dubtdt:" + npaDt + "; bal:" + balance);
                                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("LOS")) {
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt(npaDt);
                                    }

                                    resultList.add(pojo);
                                }
                            }
                        }
                    }
                }
            }
//            for(int i=0; i<resultList.size(); i++){
//                System.out.println("acno:"+resultList.get(i).getAcno()+";"+resultList.get(i).getBalance()+";"+resultList.get(i).getPresentStatus()+";"+resultList.get(i).getStandardDt()+";"+resultList.get(i).getSubStandardDt()+";"+resultList.get(i).getDoubtFullDt()+";"+resultList.get(i).getLossDt());
//            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public List<NpaStatusReportPojo> getNpaStatusReportDataOptimized(String status, String acType, String fromDt, String toDt, String acStatus, String orgBrnCode, String rbiParameter) throws ApplicationException {
        List<NpaStatusReportPojo> resultList = new ArrayList<NpaStatusReportPojo>();
        List dataList = new ArrayList();
        BigDecimal balance = new BigDecimal("0");
        String npaDt = "", query = "", npaStatusCode = "";
        try {
//            if (rbiParameter.equalsIgnoreCase("Y")) {
            if (status.equalsIgnoreCase("0")) {
                npaStatusCode = "'11','12','13'";
            } else {
                if (acStatus.substring(0, 3).equalsIgnoreCase("SUB")) {
                    npaStatusCode = "11";
                } else if (acStatus.substring(0, 3).equalsIgnoreCase("DOU")) {
                    npaStatusCode = "12";
                } else if (acStatus.substring(0, 3).equalsIgnoreCase("LOS")) {
                    npaStatusCode = "13";
                } else if (acStatus.substring(0, 3).equalsIgnoreCase("STA")) {
                    npaStatusCode = "1";
                }
            }
            String acTypeQuery = "";
            if (orgBrnCode.equalsIgnoreCase("0A")) {
                if (acType.equalsIgnoreCase("ALL")) {
                    //dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT a.ACNO FROM accountmaster a, accounttypemaster am WHERE a.accttype = am.acctcode and am.CrDbFlag in('B','D') and a.OPENINGDT /*between '" + fromDt + "' and*/<= '" + toDt + "' AND (a.CLOSINGDATE='' OR ifnull(a.CLOSINGDATE,'')='' OR a.CLOSINGDATE>'" + toDt + "')) ORDER BY ACNO").getResultList();
                    acTypeQuery = "";
                } else {
                    acTypeQuery = " AND substring(ac.acno,3,2) in (" + acType + ") ";
                }
            } else {
                if (acType.equalsIgnoreCase("ALL")) {
                    acTypeQuery = " AND ac.CURBRCODE='" + orgBrnCode + "' ";
                } else {
                    acTypeQuery = " AND ac.CURBRCODE='" + orgBrnCode + "' AND substring(ac.acno,3,2) in (" + acType + ") ";
                }
            }
//            query = "select a.acno, ac.custname, c.sno, atm.acctnature, substring(cb.description,1,3), npa.npaSpFlag, date_format(npa.npaEffDt,'%d/%m/%Y'), recon.bal, clbd.secured,clbd.retirement_age,ac.acctCategory "
//                    + " from accountstatus a, "
//                    + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
//                    + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
//                    + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '" + fromDt + "' and '" + toDt + "' and SPFLAG IN (11,12,13)  group by acno) b "
//                    + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (11,12,13) "
//                    + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) npa, "
//                    + " (select acno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) c , "
//                    + " (select acno,cast(sum(cramt-dramt) as decimal(25,2)) as bal from loan_recon where dt <='" + toDt + "' group by acno) recon, "
//                    + " accountmaster ac, loan_appparameter la, accounttypemaster atm, codebook cb, cbs_loan_borrower_details clbd  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
//                    + " and ac.acno = la.acno and ac.acno = recon.acno  and a.acno = clbd.acc_no "
//                    + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in ('11','12','13') and "
//                    + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + toDt + "' and "
//                    + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + toDt + "') " + acTypeQuery
//                    + " union all "
//                    + " select a.acno, ac.custname, c.sno, atm.acctnature, substring(cb.description,1,3), npa.npaSpFlag, date_format(npa.npaEffDt,'%d/%m/%Y'), recon.bal, clbd.secured ,clbd.retirement_age,ac.acctCategory "
//                    + " from accountstatus a, "
//                    + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
//                    + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
//                    + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '" + fromDt + "' and '" + toDt + "' and SPFLAG IN (11,12,13)  group by acno) b "
//                    + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (11,12,13) "
//                    + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) npa, "
//                    + " (select acno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) c , "
//                    + " (select acno,cast(sum(cramt-dramt) as decimal(25,2)) as bal from ca_recon where dt <='" + toDt + "' group by acno) recon, "
//                    + " accountmaster ac, loan_appparameter la, accounttypemaster atm, codebook cb, cbs_loan_borrower_details clbd  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
//                    + " and ac.acno = la.acno and ac.acno = recon.acno  and a.acno = clbd.acc_no "
//                    + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (" + npaStatusCode + ") and "
//                    + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + toDt + "' and "
//                    + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + toDt + "') " + acTypeQuery
//                    + " order by  acno, acctnature, substring(acno,3,2), substring(acno,1,2)  ";            
            query = " select a.acno,a.custname  ,a.sno ,a.acctnature ,a.descr ,a.npaSpFlag ,a.npaEffDt,a.bal ,a.secured ,a.retirement_age,a.acctCategory "
                    + " ,a.lastCrDt ,a.NpaAmt ,a.disbAmt ,a.disbDt ,a.diffDt ,a.scheme ,a.delinqcycle,a.secu , "
                    + " (select PROV_IN_PERCENT from cbs_scheme_delinquency_details where CONVERT(delinq_cycle USING utf8) =CONVERT(a.delinqcycle USING utf8) and CONVERT(scheme_code USING utf8)=CONVERT(a.scheme USING utf8) and CONVERT(flag USING utf8)=CONVERT(a.secu USING utf8) ) as provPerc,"
                    + " a.npaDt ,(select ref_desc from cbs_ref_rec_type where  ref_rec_no = '207' and ref_code = a.delinqcycle) as delinqDesc,a.chkLimit  "
                    + " ,(select ifnull(Sanctionlimit,0) from loan_appparameter where acno =  a.acno) as sancLimit "
                    + " ,(select ifnull(Sanctionlimitdt,'') from loan_appparameter where acno =  a.acno) as sancDt"
                    + " ,(select ref_desc from cbs_ref_rec_type where  ref_rec_no = '187' and ref_code = a.secured) as securedNature"
                    + " ,(select ifnull(sum(cramt-dramt),0)  from npa_recon where acno =a.acno  and trantype ='8' and dt<='" + toDt + "') as npaInt ,a.lastCrAmt,ifnull(a.emiDt,'19000101')  "
                    + " , ifnull(np.npaEffDt,'') as subSTDDt,a.chkSanDate  from ( "
                    + " select  fin.acno as acno ,fin.custname as custname ,fin.sno as sno,fin.acctnature as acctnature,fin.descr as descr,fin.npaSpFlag as npaSpFlag, "
                    + " fin.npaEffDt as npaEffDt,fin.bal as bal,fin.secured as secured,fin.retirement_age as retirement_age,fin.acctCategory as acctCategory "
                    + " ,fin.lastCrDt as lastCrDt,fin.NpaAmt as NpaAmt,fin.disbAmt as disbAmt,fin.disbDt as disbDt,fin.diffDt as diffDt,fin.scheme as scheme,fin.secu as secu,fin.npaDt as npaDt,"
                    + " case fin.descr when 'SUB' then 'SUB' when 'LOS' then 'L' when 'DOU' then (select delinq_cycle from cbs_scheme_delinquency_details where CONVERT(scheme_code USING utf8)=CONVERT(fin.scheme USING utf8) and CONVERT(flag USING utf8)=CONVERT(fin.secu USING utf8) and delinq_cycle like 'D%' and CONVERT(fin.delinq USING utf8)=cast(days_past_due as decimal )) end as delinqcycle"
                    + " ,ifnull(fin.chkLimit,(select ifnull(odlimit,0) from loan_appparameter  where acno = fin.acno)) as chkLimit , fin.lastCrAmt as lastCrAmt"
                    + " ,case fin.emicount when '0' then '19000101' else (select date_format(max(duedt),'%Y%m%d') from emidetails where acno = fin.acno and status = 'PAID' and duedt <= '" + toDt + "' group by acno) end as emiDt,"
                    + " ifnull(fin.chkSanDate,(select Sanctionlimitdt from loan_appparameter  where acno = fin.acno)) as chkSanDate"
                    + " from ( "
                    + " select a.acno as acno, ac.custname as custname, c.sno as sno, atm.acctnature as acctnature, substring(cb.description,1,3) as descr, "
                    + " npa.npaSpFlag as npaSpFlag, date_format(npa.npaEffDt,'%d/%m/%Y') as npaEffDt , recon.bal as bal, "
                    + " clbd.secured as secured,clbd.retirement_age as retirement_age,ac.acctCategory as acctCategory,"
                    + " (select ifnull(max(date_format(dt,'%Y%m%d')),ac.openingdt) from loan_recon where acno = a.acno and cramt > 0 and dt <= '" + toDt + "') as lastCrDt ,"
                    + " (select ifnull(sum(dramt-cramt),0) from npa_recon where acno = a.acno and dt <= '" + toDt + "') as NpaAmt , "
                    + " (select ifnull(sum(ifnull(dramt,0)),0)  from ca_recon   where dt <= '" + toDt + "' and trandesc in (6,0)  and acno=a.acno ) as disbAmt ,  "
                    + " (select  date_format(ifnull(min(dt),ac.openingdt),'%d/%m/%Y')  from loan_recon where dt <= '" + toDt + "' and trandesc in (6,0)  and acno=a.acno ) as disbDt  ,"
                    + " datediff('" + toDt + "',date_format(npa.npaEffDt,'%Y%m%d')) as diffDt ,"
                    + " (SELECT ifnull(SCHEME_CODE,'CA001') FROM cbs_loan_acc_mast_sec  WHERE ACNO = a.acno) as scheme, "
                    + " case clbd.secured when 'FLSEC' then 'S' when 'UNSEC' then 'U' when 'PTSEC' then 'P' end as secu  ,"
                    + " case cb.description when 'SUB STANDARD' then (select ifnull(date_format(npadt,'%Y%m%d'),'19000101')  from loan_appparameter where acno=a.acno) "
                    + " when 'LOSS' then   (select ifnull(date_format(dcdt,'%Y%m%d'),'19000101')  from loan_appparameter where acno=a.acno)  "
                    + " when 'DOUBTFUL' then (select ifnull(date_format(dbtdt,'%Y%m%d'),'19000101') from  loan_appparameter where acno=a.acno)  end as npaDt  ,"
                    + " case cb.description when 'STANDARD' then 'STD' when 'SUB STANDARD' then 'SUB' when 'LOSS' then 'L' when 'DOUBTFUL' then (select min(cast(days_past_due as decimal)) from cbs_scheme_delinquency_details where scheme_code=scheme and flag=secu and delinq_cycle like 'D%' and CONVERT(diffDt USING utf8)<=cast(days_past_due as decimal )) end as delinq "
                    + " ,(select acLimit from loan_oldinterest where acno =  a.acno and txnid = (select min(TXNID) from loan_oldinterest where acno =  a.acno and enterdate>'" + toDt + "' )) as chkLimit "
                    + " ,(select ifnull(sum(cramt),0) from loan_recon where acno=a.acno and dt= lastCrDt) as lastCrAmt"
                    + " ,(select count(*) from emidetails where acno =a.acno) as emicount,"
                    + " (select SanctionLimitDt from loan_oldinterest where acno =  a.acno and txnid = (select min(TXNID) from loan_oldinterest where acno =  a.acno and enterdate>'" + toDt + "' )) as chkSanDate"
                    + " from accountstatus a,  (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast,  "
                    + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa,  "
                    + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '" + fromDt + "' and '" + toDt + "' and SPFLAG IN ('11','12','13')  group by acno) b  "
                    + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN ('11','12','13')  "
                    + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) npa,  "
                    + " (select acno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) c ,  "
                    + " (select acno,cast(sum(cramt-dramt) as decimal(25,2)) as bal from loan_recon where dt <='" + toDt + "' group by acno) recon,  "
                    + " accountmaster ac, loan_appparameter la, accounttypemaster atm, codebook cb, cbs_loan_borrower_details clbd  "
                    + " where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  and ac.acno = la.acno and ac.acno = recon.acno  and a.acno = clbd.acc_no  "
                    + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (" + npaStatusCode + ") and  substring(a.acno,3,2) = atm.acctcode "
                    + " and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + toDt + "' and  "
                    + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + toDt + "')  " + acTypeQuery
                    + " union all  "
                    + " select a.acno as acno, ac.custname as custname, c.sno as sno, atm.acctnature as acctnature, substring(cb.description,1,3) as descr, "
                    + " npa.npaSpFlag as npaSpFlag, date_format(npa.npaEffDt,'%d/%m/%Y') as npaEffDt , recon.bal as bal, "
                    + " clbd.secured as secured,clbd.retirement_age as retirement_age,ac.acctCategory as acctCategory,"
                    + " (select ifnull(max(date_format(dt,'%Y%m%d')),ac.openingdt) from ca_recon where acno = a.acno and cramt > 0 and dt <= '" + toDt + "')  lastCrDt ,"
                    + " (select ifnull(sum(dramt-cramt),0) from npa_recon where acno = a.acno and dt <= '" + toDt + "') as NpaAmt , "
                    + " (select ifnull(sum(ifnull(dramt,0)),0)  from ca_recon where dt <= '" + toDt + "' and trandesc in (6,0) and acno=a.acno ) as disbAmt, "
                    + " (select  date_format(ifnull(min(dt),ac.openingdt),'%d/%m/%Y') from ca_recon where dt <= '" + toDt + "' and trandesc in (6,0)  and acno=a.acno ) as disbDt  ,"
                    + " datediff('" + toDt + "',date_format(npa.npaEffDt,'%Y%m%d')) as diffDt  ,"
                    + " (SELECT ifnull(SCHEME_CODE,'CA001') FROM cbs_loan_acc_mast_sec  WHERE ACNO = a.acno) as scheme  ,"
                    + " case clbd.secured when 'FLSEC' then 'S' when 'UNSEC' then 'U' when 'PTSEC' then 'P' end as secu ,"
                    + " case cb.description when 'SUB STANDARD' then (select ifnull(date_format(npadt,'%Y%m%d'),'19000101')  from loan_appparameter where acno=a.acno) "
                    + " when 'LOSS' then   (select ifnull(date_format(dcdt,'%Y%m%d'),'19000101')  from loan_appparameter where acno=a.acno)  "
                    + " when 'DOUBTFUL' then (select ifnull(date_format(dbtdt,'%Y%m%d'),'19000101') from  loan_appparameter where acno=a.acno)  end as npaDt "
                    + " ,case cb.description when 'STANDARD' then 'STD' when 'SUB STANDARD' then 'SUB' when 'LOSS' then 'L' when 'DOUBTFUL'  then (select min(cast(days_past_due as decimal)) from cbs_scheme_delinquency_details where scheme_code=scheme and flag=secu and delinq_cycle like 'D%' and CONVERT(diffDt USING utf8)<=cast(days_past_due as decimal )) end as delinq"
                    + " ,(select acLimit from loan_oldinterest where acno =  a.acno and txnid = (select min(TXNID) from loan_oldinterest where acno =  a.acno and enterdate>'" + toDt + "' )) as chkLimit "
                    + " ,(select ifnull(sum(cramt),0) from ca_recon where acno=a.acno and dt= lastCrDt) as lastCrAmt,0 as emicount,"
                    + " (select SanctionLimitDt from loan_oldinterest where acno =  a.acno and txnid = (select min(TXNID) from loan_oldinterest where acno =  a.acno and enterdate>'" + toDt + "' )) as chkSanDate"
                    + " from accountstatus a,  "
                    + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast,  "
                    + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa,  "
                    + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '" + fromDt + "' and '" + toDt + "' and SPFLAG IN ('11','12','13')  group by acno) b  "
                    + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN ('11','12','13')  group by aa.acno,aa.effdt) c "
                    + " where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) npa,  "
                    + " (select acno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) c ,  "
                    + " (select acno,cast(sum(cramt-dramt) as decimal(25,2)) as bal from ca_recon where dt <='" + toDt + "'  group by acno) recon,  "
                    + " accountmaster ac, loan_appparameter la, accounttypemaster atm, codebook cb, cbs_loan_borrower_details clbd  "
                    + " where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  and ac.acno = la.acno and ac.acno = recon.acno  "
                    + " and a.acno = clbd.acc_no  and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (" + npaStatusCode + ") and  substring(a.acno,3,2) = atm.acctcode "
                    + " and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + toDt + "' and  (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + toDt + "')  " + acTypeQuery
                    + " order by  acno, acctnature, substring(acno,3,2), substring(acno,1,2) "
                    + " ) fin "
                    + " ) a "
                    + " left join "
                    + " (select ast.acno, ifnull(date_format(max(ast.effdt),'%d/%m/%Y'),'') as npaEffDt from accountstatus ast, "
                    + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                    + " (select acno as ano, max(effdt) as dt from accountstatus where "
                    + " effdt  between '19000101' and '" + toDt + "' and SPFLAG IN ('11')  group by acno) b "
                    + " where aa.acno = b.ano and   CONVERT(aa.effdt USING utf8) = CONVERT(b.dt USING utf8) and aa.SPFLAG IN ('11') "
                    + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and CONVERT(ast.effdt USING utf8) = CONVERT(c.aadt USING utf8) and ast.spno = c.sno GROUP BY ast.acno) np on np.acno = a.acno";

            if (status.equalsIgnoreCase("1") && npaStatusCode.equalsIgnoreCase("1")) {
                query = "select aa.npaAcno, aa.custname, aa.sno, aa.acctnature,   aa.description, aa.npaSpflag,date_format(aa.npaEffDt,'%d/%m/%Y') as npaEffDt, "
                        + "if(aa.acctnature ='CA', (select cast(sum(cramt-dramt) as decimal(25,2)) as bal from ca_recon where dt <=' " + toDt + "' and acno = aa.npaAcno), "
                        + "(select cast(sum(cramt-dramt) as decimal(25,2)) as bal from loan_recon where dt <=' " + toDt + "' and acno = aa.npaAcno)) as bal, aa.secured,aa.retirement_age,aa.acctCategory  from "
                        + "(select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag, atm.acctnature , c.sno, am.custname,  "
                        + "substring(cb.description,1,3) as description,clbd.secured,clbd.retirement_age,am.acctCategory  "
                        + "from accountstatus ast, accounttypemaster atm, accountmaster am, codebook cb, cbs_loan_borrower_details clbd,  "
                        + "(select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa,  "
                        + "(select acno as ano, max(effdt) as dt from accountstatus where effdt  <=' " + toDt + "' and SPFLAG  IN (1)  group by acno) b  "
                        + "where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG  IN (1)  "
                        + "group by aa.acno,aa.effdt) c  "
                        + "where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno and substring(ast.acno,3,2) = atm.acctcode and ast.acno = am.acno and am.acno = clbd.acc_no  "
                        + "and ast.spflag = cb.code and  cb.groupcode = 3  "
                        + "and c.aano in  "
                        + "(select a.acno from accountstatus a,  "
                        + "(select ast.acno as npaAcno, ast.effdt as npaEffDt, ast.spflag as npaSpflag  from accountstatus ast,  "
                        + "(select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa,  "
                        + "(select acno as ano, max(effdt) as dt from accountstatus where effdt  <=' " + toDt + "' and SPFLAG IN (11,12,13)  group by acno) b  "
                        + "where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (11,12,13)  "
                        + "group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno) b where  a.acno =  b.npaAcno and a.spflag = '1'  "
                        + "and a.EFFDT>b.npaEffDt AND b.npaEffDt between ' " + fromDt + "' and ' " + toDt + "')) aa  ";
            }
//                        "select a.acno from accountstatus a, "
//                        + "(select acno,max(effdt) as edt from accountstatus where effdt <='" + toDt + "' group by acno) b,"
//                        + "(select acno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) c ,"
//                        + "accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and "
//                        + "a.effdt = b.edt and a.spno = c.sno and a.spflag in (" + npaStatusCode + ") and a.effdt <='" + toDt + "' and "
//                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + toDt + "')";
//            } else {
//                query = "SELECT ac.ACNO FROM accountmaster ac, accounttypemaster am WHERE ac.accttype = am.acctcode and am.CrDbFlag in('B','D') and ac.OPENINGDT /*between '" + fromDt + "' and*/<= '" + toDt + "' AND (ac.CLOSINGDATE='' OR ifnull(ac.CLOSINGDATE,'')='' OR ac.CLOSINGDATE>'" + toDt + "')";
//            }
//            System.out.println("npa query>>>>"+query);
            dataList = em.createNativeQuery(query).getResultList();

            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    String acno = element.get(0).toString();
                    String custname = element.get(1).toString();
                    String spNo = element.get(2).toString();
                    String acNature = element.get(3).toString();
                    String acStatusDesc = element.get(4).toString().equalsIgnoreCase("OPE") ? "STA" : element.get(4).toString();
                    String spFlag = element.get(5).toString();
                    String npaEffDt = element.get(6).toString();
                    double balAsOn = Double.parseDouble(element.get(7).toString());
                    String secured = element.get(8).toString();
                    String retirementAge = element.get(9).toString();
                    String acctCategory = element.get(10).toString();
                    String lastCrDt = element.get(11).toString();
                    BigDecimal npaAmt = new BigDecimal(element.get(12).toString());
                    BigDecimal disbAmt = new BigDecimal(element.get(13).toString());
                    String dibDt = element.get(14).toString();
                    String currentDiff = element.get(15).toString();
                    String scheme = element.get(16).toString();
                    String delinqCycle = element.get(17).toString();
                    String securedType = element.get(18).toString();
                    BigDecimal provPerc = new BigDecimal(element.get(19).toString());
                    BigDecimal provAmt = new BigDecimal(balAsOn).multiply(provPerc).divide(new BigDecimal("100"));
                    // String npaDate = element.get(20).toString(); // IF a/c mark npa then operative again npa handling
                    String npaDate = ymd.format(dmy.parse(element.get(6).toString())); // IF a/c mark npa then operative again npa handling
                    String delinqDesc = element.get(21).toString();
                    BigDecimal chkLimit = new BigDecimal(element.get(22).toString());
                    BigDecimal sancLimit = new BigDecimal(element.get(23).toString());
                    String sancDt = element.get(24).toString();
                    String securedDesc = element.get(25).toString();
                    BigDecimal npaInt = new BigDecimal(element.get(26).toString());
                    BigDecimal lastCrAmt = new BigDecimal(element.get(27).toString());
                    String maxEmiDt = element.get(28).toString();
                    String subStdDt = element.get(29).toString();
                    sancDt = element.get(30).toString(); // Software Bug #38283

//                    List checkAcnoList;
//                    if (stdAsset.equalsIgnoreCase("Y")) {
//                        checkAcnoList = em.createNativeQuery("SELECT ACNO FROM accountstatus WHERE ACNO='" + acno + "' union select acno from accountmaster where acno = '" + acno + "' and (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + toDt + "') ").getResultList();
//                    } else {
//                        checkAcnoList = em.createNativeQuery("SELECT ACNO FROM accountstatus WHERE ACNO='" + acno + "'").getResultList();
//                    }

////                    if (!checkAcnoList.isEmpty()) {
//                        double balAsOn = loanRemote.fnBalosForAccountAsOn(acno, toDt);
////                        if (balAsOn != 0) {
//                        String presentStatus = interBranchFacade.fnGetLoanStatusBetweenDate(acno, fromDt, toDt);                        
//                        System.out.println("i:"+i+"; Acno: "+acno+"; presentStatus:"+presentStatus);
                        /* Previous history checking for existance of NPA status of that account  */
//                        if(presentStatus.equalsIgnoreCase("STANDARD")||presentStatus.equalsIgnoreCase("OPE")){                        
//                            List tempList = em.createNativeQuery("SELECT date_format(MAX(EFFDT),'%Y%m%d') FROM accountstatus WHERE SPFLAG IN (11,12,13) AND ACNO ='" + acno + "' and EFFDT <='"+toDt+"'").getResultList();
//                            if(tempList.size()>0){
//                                tempList = em.createNativeQuery("SELECT spflag, date_format(MAX(EFFDT),'%Y%m%d') FROM accountstatus WHERE SPFLAG IN (11,12,13) AND ACNO ='" + acno + "' and EFFDT <='"+toDt+"'").getResultList();
//                                Vector tmpVect = (Vector) tempList.get(0);
//                                String status1 = tmpVect.get(0).toString();
//                                if(status1.equalsIgnoreCase("11")){
//                                    presentStatus = "SUB";
//                                } else if(status1.equalsIgnoreCase("12")){
//                                    presentStatus = "DOU";
//                                } else if(status1.equalsIgnoreCase("13")){
//                                    presentStatus = "LOS";
//                                }
//                            }
//                        }
                    /**
                     * ************************************************************************
                     */
                    balance = new BigDecimal(balAsOn);
//                            String acnature = ftsFacade.getAccountNature(acno);
//
//                            if (acnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                                List balList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM ca_recon WHERE DT <='" + toDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
//                                if (!balList.isEmpty()) {
//                                    Vector balElement = (Vector) balList.get(0);
//                                    balance = new BigDecimal(formatter.format(balElement.get(0)));
//                                }
//                            } else {
//                                List balList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM loan_recon WHERE DT <='" + toDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
//                                if (!balList.isEmpty()) {
//                                    Vector balElement = (Vector) balList.get(0);
//                                    balance = new BigDecimal(formatter.format(balElement.get(0)));
//                                }
//                            }
                    String npaClassification = "", npaDesc = "";
//                        List npaClassList = em.createNativeQuery("select npa_classification,ref_desc from cbs_loan_borrower_details c,cbs_ref_rec_type r where  acc_no = '" + acno + "' and r.ref_code = c.npa_classification and r.ref_rec_no='195'").getResultList();
//                        if (!npaClassList.isEmpty()) {
//                            Vector vtr = (Vector) npaClassList.get(0);
//                            npaClassification = vtr.get(0).toString();
//                            npaDesc = vtr.get(1).toString();
//                        }

                    if (status.equalsIgnoreCase("0")) {
                        if (acStatusDesc.equalsIgnoreCase("DOU") || acStatusDesc.equalsIgnoreCase("SUB") || acStatusDesc.equalsIgnoreCase("LOS")) {
//                                List minEffList = em.createNativeQuery("SELECT date_format(MAX(EFFDT),'%d/%m/%Y') FROM accountstatus a, codebook c WHERE ACNO='" + acno + "' AND SUBSTRING(description,1,3)='" + presentStatus + "' AND GROUPCODE = 3 and a.spflag = c.code and effdt <='" + toDt + "'").getResultList();
//                                Vector minElement = (Vector) minEffList.get(0);
//                                npaDt = minElement.get(0).toString();
                            npaDt = npaEffDt;

                            NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                            pojo.setAcno(acno);
                            pojo.setCustName(custname);
                            if (balance.doubleValue() < 0) {
                                pojo.setFlag("Dr");
                            } else {
                                pojo.setFlag("Cr");
                            }
                            pojo.setBalance(balance);
                            pojo.setStandardDt("");
                            pojo.setSecured(secured);
                            pojo.setAcctNature(acNature);
                            pojo.setLastCrDt(lastCrDt);
                            pojo.setNpaAmt(npaAmt);
                            pojo.setDisbAmt(disbAmt);
                            pojo.setDisbDt(dibDt);
                            pojo.setCurrDayDiff(currentDiff);
                            pojo.setSchemeCode(scheme);
                            pojo.setDelinqCycle(delinqCycle);
                            pojo.setSecuredType(securedType);
                            pojo.setProvPerc(provPerc);
                            pojo.setProvAmt(provAmt.abs());
                            pojo.setNpaDate(npaDate);
                            pojo.setDelinqCycleDesc(delinqDesc);
                            pojo.setChkLimit(chkLimit);
                            pojo.setSancAmt(sancLimit);
                            pojo.setSanctDt(sancDt);
                            pojo.setSecuredDesc(securedDesc);
                            pojo.setNpaInt(npaInt);
                            pojo.setLastCrAmt(lastCrAmt);
                            pojo.setMaxEmiDt(maxEmiDt);
                            pojo.setSubDt(subStdDt);
                            if (acStatusDesc.equalsIgnoreCase("SUB")) {
//                                if (!(npaClassification.equalsIgnoreCase("NPA") || npaClassification.equalsIgnoreCase("0") || npaClassification.equalsIgnoreCase(""))) {
//                                    pojo.setPresentStatus("SUB STANDARD" + "-" + npaDesc);
//                                } else {
                                pojo.setPresentStatus("SUB STANDARD");
//                                }
                                pojo.setSubStandardDt(npaDt);
                                pojo.setDoubtFullDt("");
                                pojo.setLossDt("");
                            } else if (acStatusDesc.contains("DOU")) {
//                                if (!(npaClassification.equalsIgnoreCase("NPA") || npaClassification.equalsIgnoreCase("0") || npaClassification.equalsIgnoreCase(""))) {
//                                    pojo.setPresentStatus("DOUBT FUL" + "-" + npaDesc);
//                                    if (acStatus.contains("DOU")) {
                                long dayDiff = 0;
                                List limitList = em.createNativeQuery("select ifnull(date_format(dbtdt,'%Y%m%d'),'19000101') from loan_appparameter where acno='" + acno + "'").getResultList();
                                Vector limitVec = (Vector) limitList.get(0);
                                String npaDtforStatus = limitVec.get(0).toString();
                                limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) as days "
                                        + "from cbs_scheme_delinquency_details where delinq_cycle in (select ref_code "
                                        + "from cbs_ref_rec_type where ref_rec_no='207' and ref_code in('D1','D2','D3')) "
                                        + "order by days").getResultList();
                                if (!limitList.isEmpty()) {
                                    dayDiff = CbsUtil.dayDiff(ymd.parse(npaDtforStatus), ymd.parse(toDt));
                                    for (int t = 0; t < limitList.size(); t++) {
                                        limitVec = (Vector) limitList.get(t);
                                        if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                                            if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                pojo.setPresentStatus(acStatusDesc.concat("-" + loanRemote.getRefRecDesc("D1")));;
                                                break;
                                            }
                                        } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                                            if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                pojo.setPresentStatus(acStatusDesc.concat("-" + loanRemote.getRefRecDesc("D2")));;
                                                break;

                                            }
                                        } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                                            pojo.setPresentStatus(acStatusDesc.concat("-" + loanRemote.getRefRecDesc("D3")));;
                                            break;
                                        }
                                    }
                                }
//                                } else {
//                                    pojo.setPresentStatus(acStatus);
//                                }
                                pojo.setSubStandardDt("");
                                pojo.setDoubtFullDt(npaDt);
                                pojo.setLossDt("");
                            } else if (acStatusDesc.equalsIgnoreCase("LOS")) {
//                                if (!(npaClassification.equalsIgnoreCase("NPA") || npaClassification.equalsIgnoreCase("0") || npaClassification.equalsIgnoreCase(""))) {
//                                    pojo.setPresentStatus("LOSS" + "-" + npaDesc);
//                                } else {
                                pojo.setPresentStatus("LOSS");
//                                }
                                pojo.setSubStandardDt("");
                                pojo.setDoubtFullDt("");
                                pojo.setLossDt(npaDt);
                            }
                            pojo.setRetirementAge(retirementAge);
                            pojo.setAcctCategory(acctCategory);

                            resultList.add(pojo);
                        }
//                            else {
//                                if (stdAsset.equalsIgnoreCase("Y")) {
//                                    if (presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("STANDARD")) {
//                                        List minEffList = em.createNativeQuery("select date_format(OpeningDt,'%d/%m/%Y') from accountmaster where acno = '" + acno + "'").getResultList();
//                                        Vector minElement = (Vector) minEffList.get(0);
//                                        npaDt = minElement.get(0).toString();
//
//                                        NpaStatusReportPojo pojo = new NpaStatusReportPojo();
//                                        pojo.setAcno(acno);
//                                        pojo.setCustName(custname);
//                                        if (balance.doubleValue() < 0) {
//                                            pojo.setFlag("Dr");
//                                        } else {
//                                            pojo.setFlag("Cr");
//                                        }
//                                        pojo.setBalance(balance);
//                                        pojo.setStandardDt("");
//                                        if (presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("STANDARD")) {
//                                            pojo.setPresentStatus("STANDARD");
//                                            pojo.setDoubtFullDt("");
//                                            pojo.setLossDt("");
//                                            pojo.setStandardDt(npaDt);
//                                            pojo.setSubStandardDt("");
//                                        }
//                                        resultList.add(pojo);
//                                    }
//                                }
//                            }
                    } else if (status.equalsIgnoreCase("1")) {
//                            if (acStatus.equalsIgnoreCase("STANDARD")) {
//                                if (presentStatus.equalsIgnoreCase("STANDARD") || presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("WIT") || presentStatus.equalsIgnoreCase("INO")) {
//                                    List minEffList = em.createNativeQuery("select date_format(OpeningDt,'%d/%m/%Y') from accountmaster where acno = '" + acno + "'").getResultList();
//                                    Vector minElement = (Vector) minEffList.get(0);
//                                    npaDt = minElement.get(0).toString();
//
//                                    NpaStatusReportPojo pojo = new NpaStatusReportPojo();
//                                    pojo.setAcno(acno);
//                                    pojo.setCustName(custname);
//                                    if (balance.doubleValue() < 0) {
//                                        pojo.setFlag("Dr");
//                                    } else {
//                                        pojo.setFlag("Cr");
//                                    }
//                                    pojo.setBalance(balance);
//                                    pojo.setPresentStatus(acStatus);
//                                    pojo.setStandardDt("");
//                                    if (acStatus.substring(0, 3).equalsIgnoreCase("SUB")) {
//                                        pojo.setSubStandardDt(npaDt);
//                                        pojo.setDoubtFullDt("");
//                                        pojo.setLossDt("");
//                                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("DOU")) {
//                                        pojo.setSubStandardDt("");
//                                        pojo.setDoubtFullDt(npaDt);
//                                        pojo.setLossDt("");
//                                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("LOS")) {
//                                        pojo.setSubStandardDt("");
//                                        pojo.setDoubtFullDt("");
//                                        pojo.setLossDt(npaDt);
//                                    } else if (acStatus.equalsIgnoreCase("STANDARD")) {
////                                            if(presentStatus.equalsIgnoreCase("WIT")){
////                                                pojo.setPresentStatus("WITHDRAWAL STOPPED");
////                                            }
//                                        String newAcStatus = interBranchFacade.fnGetLoanStatusOfAccountTillDate(acno, toDt);
//                                        pojo.setPresentStatus(newAcStatus.equalsIgnoreCase("OPERATIVE") ? "STANDARD" : newAcStatus);
//                                        pojo.setDoubtFullDt("");
//                                        pojo.setLossDt("");
//                                        pojo.setStandardDt(npaDt);
//                                        pojo.setSubStandardDt("");
//                                    }
//                                    resultList.add(pojo);
//                                }
//                            } else {
                        if (acStatusDesc.equalsIgnoreCase(acStatus.substring(0, 3))) {
//                                    List minEffList = em.createNativeQuery("select date_format(max(effdt),'%d/%m/%Y') from accountstatus a, codebook c where acno='" + acno + "' and substring(description,1,3)='" + presentStatus + "' and groupcode = 3 and a.spflag = c.code  and effdt <='" + toDt + "'").getResultList();
//                                    Vector minElement = (Vector) minEffList.get(0);
//                                    npaDt = minElement.get(0).toString();
                            npaDt = npaEffDt;

                            NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                            pojo.setAcno(acno);
                            pojo.setCustName(custname);
                            pojo.setSecured(secured);
                            pojo.setAcctNature(acNature);
                            if (balance.doubleValue() < 0) {
                                pojo.setFlag("Dr");
                            } else {
                                pojo.setFlag("Cr");
                            }
                            pojo.setBalance(balance);
                            pojo.setLastCrDt(lastCrDt);
                            pojo.setNpaAmt(npaAmt);
                            pojo.setDisbAmt(disbAmt);
                            pojo.setDisbDt(dibDt);
                            pojo.setCurrDayDiff(currentDiff);
                            pojo.setSchemeCode(scheme);
                            pojo.setDelinqCycle(delinqCycle);
                            pojo.setSecuredType(securedType);
                            pojo.setProvPerc(provPerc);
                            pojo.setProvAmt(provAmt.abs());
                            pojo.setNpaDate(npaDate);
                            pojo.setDelinqCycleDesc(delinqDesc);
                            pojo.setChkLimit(chkLimit);
                            pojo.setSancAmt(sancLimit);
                            pojo.setSanctDt(sancDt);
                            pojo.setSecuredDesc(securedDesc);
                            pojo.setNpaInt(npaInt);
                            pojo.setLastCrAmt(lastCrAmt);
                            pojo.setMaxEmiDt(maxEmiDt);
                            pojo.setSubDt(subStdDt);
//                            if (!(npaClassification.equalsIgnoreCase("NPA") || npaClassification.equalsIgnoreCase("0") || npaClassification.equalsIgnoreCase(""))) {
//                                pojo.setPresentStatus(acStatus + "-" + npaDesc);
//                            } else {
                            if (acStatus.contains("DOU")) {
                                long dayDiff = 0;
                                List limitList = em.createNativeQuery("select ifnull(date_format(dbtdt,'%Y%m%d'),'19000101') from loan_appparameter where acno='" + acno + "'").getResultList();
                                Vector limitVec = (Vector) limitList.get(0);
                                String npaDtforStatus = limitVec.get(0).toString();
                                limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) as days "
                                        + "from cbs_scheme_delinquency_details where delinq_cycle in (select ref_code "
                                        + "from cbs_ref_rec_type where ref_rec_no='207' and ref_code in('D1','D2','D3')) "
                                        + "order by days").getResultList();
                                if (!limitList.isEmpty()) {
                                    dayDiff = CbsUtil.dayDiff(ymd.parse(npaDtforStatus), ymd.parse(toDt));
                                    for (int t = 0; t < limitList.size(); t++) {
                                        limitVec = (Vector) limitList.get(t);
                                        if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                                            if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                pojo.setPresentStatus(acStatus.concat("-" + loanRemote.getRefRecDesc("D1")));;
                                                break;
                                            }
                                        } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                                            if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                pojo.setPresentStatus(acStatus.concat("-" + loanRemote.getRefRecDesc("D2")));;
                                                break;

                                            }
                                        } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                                            pojo.setPresentStatus(acStatus.concat("-" + loanRemote.getRefRecDesc("D3")));;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                pojo.setPresentStatus(acStatus);
                            }
//                            }
                            pojo.setStandardDt("");
                            if (acStatus.substring(0, 3).equalsIgnoreCase("SUB")) {
                                pojo.setSubStandardDt(npaDt);
                                pojo.setDoubtFullDt("");
                                pojo.setLossDt("");
                            } else if (acStatus.substring(0, 3).equalsIgnoreCase("DOU")) {
                                pojo.setSubStandardDt("");
                                pojo.setDoubtFullDt(npaDt);
                                pojo.setLossDt("");
                            } else if (acStatus.substring(0, 3).equalsIgnoreCase("LOS")) {
                                pojo.setSubStandardDt("");
                                pojo.setDoubtFullDt("");
                                pojo.setLossDt(npaDt);
                            } else if (acStatus.substring(0, 3).equalsIgnoreCase("OPE") || acStatus.substring(0, 3).equalsIgnoreCase("STA")) {
                                pojo.setSubStandardDt("");
                                pojo.setDoubtFullDt("");
                                pojo.setLossDt("");
                                pojo.setStandardDt(npaDt);
                            }
                            pojo.setRetirementAge(retirementAge);
                            pojo.setAcctCategory(acctCategory);
                            resultList.add(pojo);
                        }
//                            }
                    }
//                        }
//                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public List<NpaStatusReportPojo> getNpaStatusReportData1(String status, String acType, String fromDt, String toDt, String acStatus, String orgBrnCode, String rbiParameter) throws ApplicationException {
        List<NpaStatusReportPojo> resultList = new ArrayList<NpaStatusReportPojo>();
        List dataList = new ArrayList();
        BigDecimal balance = new BigDecimal("0");
        String npaDt = "", query = "", npaStatusCode = "";
        try {
//            System.out.println("NPA Start NEW>>>>>"+new Date());
            if (rbiParameter.equalsIgnoreCase("Y")) {
                if (status.equalsIgnoreCase("0")) {
                    npaStatusCode = "11,12,13";
                } else {
                    if (acStatus.substring(0, 3).equalsIgnoreCase("SUB")) {
                        npaStatusCode = "11";
                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("DOU")) {
                        npaStatusCode = "12";
                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("LOS")) {
                        npaStatusCode = "13";
                    }
                }
                query = "select a.acno from accountstatus a, "
                        + "(select acno,max(effdt) as edt from accountstatus where effdt <='" + toDt + "' group by acno) b,"
                        + "(select acno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) c ,"
                        + "accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and "
                        + "a.effdt = b.edt and a.spno = c.sno and a.spflag in (" + npaStatusCode + ") and a.effdt <='" + toDt + "' and "
                        + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + toDt + "')";
            } else {
                query = "SELECT ac.ACNO FROM accountmaster ac, accounttypemaster am WHERE ac.accttype = am.acctcode and am.CrDbFlag in('B','D') and ac.OPENINGDT /*between '" + fromDt + "' and*/<= '" + toDt + "' AND (ac.CLOSINGDATE='' OR ifnull(ac.CLOSINGDATE,'')='' OR ac.CLOSINGDATE>'" + toDt + "')";
            }
            if (orgBrnCode.equalsIgnoreCase("0A")) {
                if (acType.equalsIgnoreCase("ALL")) {
                    //dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT a.ACNO FROM accountmaster a, accounttypemaster am WHERE a.accttype = am.acctcode and am.CrDbFlag in('B','D') and a.OPENINGDT /*between '" + fromDt + "' and*/<= '" + toDt + "' AND (a.CLOSINGDATE='' OR ifnull(a.CLOSINGDATE,'')='' OR a.CLOSINGDATE>'" + toDt + "')) ORDER BY ACNO").getResultList();
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN (" + query + ") ORDER BY ACNO").getResultList();
                } else {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN (" + query + ")  AND SUBSTRING(ACNO,3,2) in (" + acType + ") ORDER BY ACNO").getResultList();
                }
            } else {
                if (acType.equalsIgnoreCase("ALL")) {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN (" + query + " AND ac.CURBRCODE='" + orgBrnCode + "') ORDER BY ACNO").getResultList();
                } else {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN (" + query + " AND ac.CURBRCODE='" + orgBrnCode + "') AND SUBSTRING(ACNO,3,2) in (" + acType + ") ORDER BY ACNO").getResultList();
                }
            }

            String stdAsset = "N";
            if (rbiParameter.equalsIgnoreCase("N")) {
                List stdList = loanRemote.getStdAssect();
                Vector vtr = (Vector) stdList.get(0);
                stdAsset = vtr.get(0).toString();
            }
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    String acno = element.get(0).toString();
                    String custname = element.get(1).toString();
                    List checkAcnoList;
                    if (stdAsset.equalsIgnoreCase("Y")) {
                        checkAcnoList = em.createNativeQuery("SELECT ACNO FROM accountstatus WHERE ACNO='" + acno + "' union select acno from accountmaster where acno = '" + acno + "' and (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + toDt + "') ").getResultList();
                    } else {
                        checkAcnoList = em.createNativeQuery("SELECT ACNO FROM accountstatus WHERE ACNO='" + acno + "'").getResultList();
                    }

                    if (!checkAcnoList.isEmpty()) {
                        double balAsOn = loanRemote.fnBalosForAccountAsOn(acno, toDt);
//                        if (balAsOn != 0) {
                        String presentStatus = interBranchFacade.fnGetLoanStatusBetweenDate(acno, fromDt, toDt);
//                        System.out.println("i:"+i+"; Acno: "+acno+"; presentStatus:"+presentStatus);
                        /* Previous history checking for existance of NPA status of that account  */
                        if (presentStatus.equalsIgnoreCase("STANDARD") || presentStatus.equalsIgnoreCase("OPE")) {
                            List tempList = em.createNativeQuery("SELECT * FROM accountstatus WHERE SPFLAG IN (11,12,13) AND ACNO ='" + acno + "' and EFFDT <='" + toDt + "'").getResultList();
                            if (tempList.size() > 0) {
                                tempList = em.createNativeQuery("SELECT spflag, date_format(MAX(EFFDT),'%Y%m%d') FROM accountstatus WHERE SPFLAG IN (11,12,13) AND ACNO ='" + acno + "' and EFFDT <='" + toDt + "'").getResultList();
                                Vector tmpVect = (Vector) tempList.get(0);
                                String status1 = tmpVect.get(0).toString();
                                if (status1.equalsIgnoreCase("11")) {
                                    presentStatus = "SUB";
                                } else if (status1.equalsIgnoreCase("12")) {
                                    presentStatus = "DOU";
                                } else if (status1.equalsIgnoreCase("13")) {
                                    presentStatus = "LOS";
                                }
                            }
                        }
                        /**
                         * ************************************************************************
                         */
                        balance = new BigDecimal(balAsOn);
//                            String acnature = ftsFacade.getAccountNature(acno);
//
//                            if (acnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                                List balList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM ca_recon WHERE DT <='" + toDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
//                                if (!balList.isEmpty()) {
//                                    Vector balElement = (Vector) balList.get(0);
//                                    balance = new BigDecimal(formatter.format(balElement.get(0)));
//                                }
//                            } else {
//                                List balList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM loan_recon WHERE DT <='" + toDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
//                                if (!balList.isEmpty()) {
//                                    Vector balElement = (Vector) balList.get(0);
//                                    balance = new BigDecimal(formatter.format(balElement.get(0)));
//                                }
//                            }
                        String npaClassification = "", npaDesc = "";
                        List npaClassList = em.createNativeQuery("select npa_classification,ref_desc from cbs_loan_borrower_details c,cbs_ref_rec_type r where  acc_no = '" + acno + "' and r.ref_code = c.npa_classification and r.ref_rec_no='195'").getResultList();
                        if (!npaClassList.isEmpty()) {
                            Vector vtr = (Vector) npaClassList.get(0);
                            npaClassification = vtr.get(0).toString();
                            npaDesc = vtr.get(1).toString();
                        }

                        if (status.equalsIgnoreCase("0")) {
                            if (presentStatus.equalsIgnoreCase("DOU") || presentStatus.equalsIgnoreCase("SUB") || presentStatus.equalsIgnoreCase("LOS")) {
                                List minEffList = em.createNativeQuery("SELECT date_format(MAX(EFFDT),'%d/%m/%Y') FROM accountstatus a, codebook c WHERE ACNO='" + acno + "' AND SUBSTRING(description,1,3)='" + presentStatus + "' AND GROUPCODE = 3 and a.spflag = c.code and effdt <='" + toDt + "'").getResultList();
                                Vector minElement = (Vector) minEffList.get(0);
                                npaDt = minElement.get(0).toString();

                                NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                pojo.setAcno(acno);
                                pojo.setCustName(custname);
                                if (balance.doubleValue() < 0) {
                                    pojo.setFlag("Dr");
                                } else {
                                    pojo.setFlag("Cr");
                                }
                                pojo.setBalance(balance);
                                pojo.setStandardDt("");
                                if (presentStatus.equalsIgnoreCase("SUB")) {
                                    if (!(npaClassification.equalsIgnoreCase("NPA") || npaClassification.equalsIgnoreCase("0") || npaClassification.equalsIgnoreCase(""))) {
                                        pojo.setPresentStatus("SUB STANDARD" + "-" + npaDesc);
                                    } else {
                                        pojo.setPresentStatus("SUB STANDARD");
                                    }
                                    pojo.setSubStandardDt(npaDt);
                                    pojo.setDoubtFullDt("");
                                    pojo.setLossDt("");
                                } else if (presentStatus.equalsIgnoreCase("DOU")) {
                                    if (!(npaClassification.equalsIgnoreCase("NPA") || npaClassification.equalsIgnoreCase("0") || npaClassification.equalsIgnoreCase(""))) {
                                        pojo.setPresentStatus("DOUBT FUL" + "-" + npaDesc);
                                    } else {
                                        pojo.setPresentStatus("DOUBT FUL");
                                    }
                                    pojo.setSubStandardDt("");
                                    pojo.setDoubtFullDt(npaDt);
                                    pojo.setLossDt("");
                                } else if (presentStatus.equalsIgnoreCase("LOS")) {
                                    if (!(npaClassification.equalsIgnoreCase("NPA") || npaClassification.equalsIgnoreCase("0") || npaClassification.equalsIgnoreCase(""))) {
                                        pojo.setPresentStatus("LOSS" + "-" + npaDesc);
                                    } else {
                                        pojo.setPresentStatus("LOSS");
                                    }
                                    pojo.setSubStandardDt("");
                                    pojo.setDoubtFullDt("");
                                    pojo.setLossDt(npaDt);
                                }

                                resultList.add(pojo);
                            } else {
                                if (stdAsset.equalsIgnoreCase("Y")) {
                                    if (presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("STANDARD")) {
                                        List minEffList = em.createNativeQuery("select date_format(OpeningDt,'%d/%m/%Y') from accountmaster where acno = '" + acno + "'").getResultList();
                                        Vector minElement = (Vector) minEffList.get(0);
                                        npaDt = minElement.get(0).toString();

                                        NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                        pojo.setAcno(acno);
                                        pojo.setCustName(custname);
                                        if (balance.doubleValue() < 0) {
                                            pojo.setFlag("Dr");
                                        } else {
                                            pojo.setFlag("Cr");
                                        }
                                        pojo.setBalance(balance);
                                        pojo.setStandardDt("");
                                        if (presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("STANDARD")) {
                                            pojo.setPresentStatus("STANDARD");
                                            pojo.setDoubtFullDt("");
                                            pojo.setLossDt("");
                                            pojo.setStandardDt(npaDt);
                                            pojo.setSubStandardDt("");
                                        }
                                        resultList.add(pojo);
                                    }
                                }
                            }
                        } else if (status.equalsIgnoreCase("1")) {
                            if (acStatus.equalsIgnoreCase("STANDARD")) {
                                if (presentStatus.equalsIgnoreCase("STANDARD") || presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("WIT") || presentStatus.equalsIgnoreCase("INO")) {
                                    List minEffList = em.createNativeQuery("select date_format(OpeningDt,'%d/%m/%Y') from accountmaster where acno = '" + acno + "'").getResultList();
                                    Vector minElement = (Vector) minEffList.get(0);
                                    npaDt = minElement.get(0).toString();

                                    NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                    pojo.setAcno(acno);
                                    pojo.setCustName(custname);
                                    if (balance.doubleValue() < 0) {
                                        pojo.setFlag("Dr");
                                    } else {
                                        pojo.setFlag("Cr");
                                    }
                                    pojo.setBalance(balance);
                                    pojo.setPresentStatus(acStatus);
                                    pojo.setStandardDt("");
                                    if (acStatus.substring(0, 3).equalsIgnoreCase("SUB")) {
                                        pojo.setSubStandardDt(npaDt);
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt("");
                                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("DOU")) {
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt(npaDt);
                                        pojo.setLossDt("");
                                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("LOS")) {
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt(npaDt);
                                    } else if (acStatus.equalsIgnoreCase("STANDARD")) {
//                                            if(presentStatus.equalsIgnoreCase("WIT")){
//                                                pojo.setPresentStatus("WITHDRAWAL STOPPED");
//                                            }
                                        String newAcStatus = interBranchFacade.fnGetLoanStatusOfAccountTillDate(acno, toDt);
                                        pojo.setPresentStatus(newAcStatus.equalsIgnoreCase("OPERATIVE") ? "STANDARD" : newAcStatus);
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt("");
                                        pojo.setStandardDt(npaDt);
                                        pojo.setSubStandardDt("");
                                    }
                                    resultList.add(pojo);
                                }
                            } else {
                                if (presentStatus.equalsIgnoreCase(acStatus.substring(0, 3))) {
                                    List minEffList = em.createNativeQuery("select date_format(max(effdt),'%d/%m/%Y') from accountstatus a, codebook c where acno='" + acno + "' and substring(description,1,3)='" + presentStatus + "' and groupcode = 3 and a.spflag = c.code  and effdt <='" + toDt + "'").getResultList();
                                    Vector minElement = (Vector) minEffList.get(0);
                                    npaDt = minElement.get(0).toString();

                                    NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                    pojo.setAcno(acno);
                                    pojo.setCustName(custname);
                                    if (balance.doubleValue() < 0) {
                                        pojo.setFlag("Dr");
                                    } else {
                                        pojo.setFlag("Cr");
                                    }
                                    pojo.setBalance(balance);
                                    if (!(npaClassification.equalsIgnoreCase("NPA") || npaClassification.equalsIgnoreCase("0") || npaClassification.equalsIgnoreCase(""))) {
                                        pojo.setPresentStatus(acStatus + "-" + npaDesc);
                                    } else {
                                        pojo.setPresentStatus(acStatus);
                                    }
                                    pojo.setStandardDt("");
                                    if (acStatus.substring(0, 3).equalsIgnoreCase("SUB")) {
                                        pojo.setSubStandardDt(npaDt);
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt("");
                                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("DOU")) {
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt(npaDt);
                                        pojo.setLossDt("");
                                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("LOS")) {
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt(npaDt);
                                    }
                                    resultList.add(pojo);
                                }
                            }
                        }
//                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
//        System.out.println("NPA END NEW>>>>>"+new Date());
        return resultList;
    }

    public List<NpaStatusReportPojo> getNpaStatusReportData2(String status, String acType, String fromDt, String toDt, String acStatus, String orgBrnCode, String rbiParameter) throws ApplicationException {
        List<NpaStatusReportPojo> resultList = new ArrayList<NpaStatusReportPojo>();
        List dataList = new ArrayList();
        BigDecimal balance = new BigDecimal("0");
        String npaDt = "";
        try {
            if (orgBrnCode.equalsIgnoreCase("0A")) {
                if (acType.equalsIgnoreCase("ALL")) {
//                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT a.ACNO FROM accountmaster a, accounttypemaster am WHERE a.accttype = am.acctcode and am.CrDbFlag in('B','D') and a.OPENINGDT /*between '" + fromDt + "' and*/<= '" + toDt + "' AND (a.CLOSINGDATE='' OR ifnull(a.CLOSINGDATE,'')='' OR a.CLOSINGDATE>'" + toDt + "')) ORDER BY ACNO").getResultList();
                    dataList = em.createNativeQuery("SELECT a.ACNO,a.CUSTNAME, cast(sum(cramt-dramt) as decimal(25,2)) FROM loan_appparameter lp, "
                            + "accountmaster a, accounttypemaster am, loan_recon lr WHERE lp.acno = a.acno and lr.acno = a.acno and  "
                            + "a.accttype = am.acctcode and lr.dt<='" + toDt + "' and am.CrDbFlag in('B','D') and a.OPENINGDT <= '" + toDt + "' "
                            + "AND (a.CLOSINGDATE='' OR ifnull(a.CLOSINGDATE,'')='' OR a.CLOSINGDATE>'" + toDt + "') group by acno  "
                            + "union all "
                            + "SELECT a.ACNO,a.CUSTNAME, cast(sum(cramt-dramt) as decimal(25,2)) FROM loan_appparameter lp, "
                            + "accountmaster a, accounttypemaster am, ca_recon lr  WHERE lp.acno = a.acno and lr.acno = a.acno and  "
                            + " a.accttype = am.acctcode and lr.dt<='" + toDt + "' and am.CrDbFlag in('B','D') and a.OPENINGDT <= '" + toDt + "' "
                            + "AND (a.CLOSINGDATE='' OR ifnull(a.CLOSINGDATE,'')='' OR a.CLOSINGDATE>'" + toDt + "') group by acno ORDER BY ACNO").getResultList();

                } else {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT ACNO FROM accountmaster WHERE OPENINGDT /*between '" + fromDt + "' and*/<= '" + toDt + "' AND (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + toDt + "') AND SUBSTRING(ACNO,3,2) in (" + acType + ")) ORDER BY ACNO").getResultList();
                }
            } else {
                if (acType.equalsIgnoreCase("ALL")) {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT a.ACNO FROM accountmaster a, accounttypemaster am WHERE a.accttype = am.acctcode and am.CrDbFlag in('B','D') and a.OPENINGDT /*between '" + fromDt + "' and*/<= '" + toDt + "' AND (a.CLOSINGDATE='' OR ifnull(a.CLOSINGDATE,'')='' OR a.CLOSINGDATE>'" + toDt + "') AND a.CURBRCODE='" + orgBrnCode + "') ORDER BY ACNO").getResultList();
                } else {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT ACNO FROM accountmaster WHERE OPENINGDT /*between '" + fromDt + "' and*/<= '" + toDt + "' AND (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + toDt + "') AND CURBRCODE='" + orgBrnCode + "') AND SUBSTRING(ACNO,3,2) in (" + acType + ") ORDER BY ACNO").getResultList();
                }
            }
            String stdAsset = "N";
            if (rbiParameter.equalsIgnoreCase("N")) {
                List stdList = loanRemote.getStdAssect();
                Vector vtr = (Vector) stdList.get(0);
                stdAsset = vtr.get(0).toString();
            }
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    String acno = element.get(0).toString();
                    String custname = element.get(1).toString();
                    double balAsOn = Double.parseDouble(element.get(2).toString());
//                    List checkAcnoList;
//                    if (stdAsset.equalsIgnoreCase("Y")) {
//                        checkAcnoList = em.createNativeQuery("SELECT ACNO FROM accountstatus WHERE ACNO='" + acno + "' union select acno from accountmaster where acno = '" + acno + "' and AccStatus = 1").getResultList();
//                    } else {
//                        checkAcnoList = em.createNativeQuery("SELECT ACNO FROM accountstatus WHERE ACNO='" + acno + "'").getResultList();
//                    }

//                    if (!checkAcnoList.isEmpty()) {
//                        double balAsOn = loanRemote.fnBalosForAccountAsOn(acno, toDt);
                    if (balAsOn != 0) {
                        String presentStatus = interBranchFacade.fnGetLoanStatusBetweenDate(acno, fromDt, toDt);
                        balance = new BigDecimal(balAsOn);

                        if (status.equalsIgnoreCase("0")) {
                            if (presentStatus.equalsIgnoreCase("DOU") || presentStatus.equalsIgnoreCase("SUB") || presentStatus.equalsIgnoreCase("LOS")) {
                                List minEffList = em.createNativeQuery("SELECT date_format(MAX(EFFDT),'%d/%m/%Y') FROM accountstatus a, codebook c WHERE ACNO='" + acno + "' AND SUBSTRING(description,1,3)='" + presentStatus + "' AND GROUPCODE = 3 and a.spflag = c.code and effdt <='" + toDt + "'").getResultList();
                                Vector minElement = (Vector) minEffList.get(0);
                                npaDt = minElement.get(0).toString();

                                NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                pojo.setAcno(acno);
                                pojo.setCustName(custname);
                                pojo.setBalance(balance);
                                pojo.setStandardDt("");
                                if (presentStatus.equalsIgnoreCase("SUB")) {
                                    pojo.setPresentStatus("SUB STANDARD");
                                    pojo.setSubStandardDt(npaDt);
                                    pojo.setDoubtFullDt("");
                                    pojo.setLossDt("");
                                } else if (presentStatus.equalsIgnoreCase("DOU")) {
                                    pojo.setPresentStatus("DOUBT FUL");
                                    pojo.setSubStandardDt("");
                                    pojo.setDoubtFullDt(npaDt);
                                    pojo.setLossDt("");
                                } else if (presentStatus.equalsIgnoreCase("LOS")) {
                                    pojo.setPresentStatus("LOSS");
                                    pojo.setSubStandardDt("");
                                    pojo.setDoubtFullDt("");
                                    pojo.setLossDt(npaDt);
                                }

                                resultList.add(pojo);
                            } else {
                                if (stdAsset.equalsIgnoreCase("Y")) {
                                    if (presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("STANDARD")) {
                                        List minEffList = em.createNativeQuery("select date_format(OpeningDt,'%d/%m/%Y') from accountmaster where acno = '" + acno + "'").getResultList();
                                        Vector minElement = (Vector) minEffList.get(0);
                                        npaDt = minElement.get(0).toString();

                                        NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                        pojo.setAcno(acno);
                                        pojo.setCustName(custname);
                                        pojo.setBalance(balance);
                                        pojo.setStandardDt("");
                                        if (presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("STANDARD")) {
                                            pojo.setPresentStatus("STANDARD");
                                            pojo.setDoubtFullDt("");
                                            pojo.setLossDt("");
                                            pojo.setStandardDt(npaDt);
                                            pojo.setSubStandardDt("");
                                        }
                                        resultList.add(pojo);
                                    }
                                }
                            }
                        } else if (status.equalsIgnoreCase("1")) {
                            if (acStatus.equalsIgnoreCase("STANDARD")) {
                                if (presentStatus.equalsIgnoreCase("STANDARD") || presentStatus.equalsIgnoreCase("OPE")) {
                                    List minEffList = em.createNativeQuery("select date_format(OpeningDt,'%d/%m/%Y') from accountmaster where acno = '" + acno + "'").getResultList();
                                    Vector minElement = (Vector) minEffList.get(0);
                                    npaDt = minElement.get(0).toString();

                                    NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                    pojo.setAcno(acno);
                                    pojo.setCustName(custname);
                                    pojo.setBalance(balance);
                                    pojo.setPresentStatus(acStatus);
                                    pojo.setStandardDt("");
                                    if (acStatus.substring(0, 3).equalsIgnoreCase("SUB")) {
                                        pojo.setSubStandardDt(npaDt);
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt("");
                                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("DOU")) {
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt(npaDt);
                                        pojo.setLossDt("");
                                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("LOS")) {
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt(npaDt);
                                    } else if (acStatus.equalsIgnoreCase("STANDARD")) {
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt("");
                                        pojo.setStandardDt(npaDt);
                                        pojo.setSubStandardDt("");
                                    }
                                    resultList.add(pojo);
                                }
                            } else {
                                if (presentStatus.equalsIgnoreCase(acStatus.substring(0, 3))) {
                                    List minEffList = em.createNativeQuery("select date_format(max(effdt),'%d/%m/%Y') from accountstatus a, codebook c where acno='" + acno + "' and substring(description,1,3)='" + presentStatus + "' and groupcode = 3 and a.spflag = c.code  and effdt <='" + toDt + "'").getResultList();
                                    Vector minElement = (Vector) minEffList.get(0);
                                    npaDt = minElement.get(0).toString();

                                    NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                    pojo.setAcno(acno);
                                    pojo.setCustName(custname);
                                    pojo.setBalance(balance);
                                    pojo.setPresentStatus(acStatus);
                                    pojo.setStandardDt("");
                                    if (acStatus.substring(0, 3).equalsIgnoreCase("SUB")) {
                                        pojo.setSubStandardDt(npaDt);
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt("");
                                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("DOU")) {
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt(npaDt);
                                        pojo.setLossDt("");
                                    } else if (acStatus.substring(0, 3).equalsIgnoreCase("LOS")) {
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt(npaDt);
                                    }
                                    resultList.add(pojo);
                                }
                            }
                        }
                    }
//                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public List<NpaStatusReportPojo> getNpaStatusReportDataNew(String status, String acType, String fromDt, String toDt, String acStatus, String orgBrnCode) throws ApplicationException {
        List<NpaStatusReportPojo> resultList = new ArrayList<NpaStatusReportPojo>();
        List dataList = new ArrayList();
        BigDecimal balance = new BigDecimal("0");
        String npaDt = "";
        try {
            if (orgBrnCode.equalsIgnoreCase("0A")) {
                if (acType.equalsIgnoreCase("ALL")) {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT a.ACNO FROM accountmaster a, accounttypemaster am WHERE a.accttype = am.acctcode and am.CrDbFlag in('B','D') and a.OPENINGDT /*between '" + fromDt + "' and*/<= '" + toDt + "' AND (a.CLOSINGDATE='' OR ifnull(a.CLOSINGDATE,'')='' OR a.CLOSINGDATE>'" + toDt + "')) ORDER BY ACNO").getResultList();
                } else {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT ACNO FROM accountmaster WHERE OPENINGDT /*between '" + fromDt + "' and*/<= '" + toDt + "' AND (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + toDt + "') AND SUBSTRING(ACNO,3,2) in (" + acType + ")) ORDER BY ACNO").getResultList();
                }
            } else {
                if (acType.equalsIgnoreCase("ALL")) {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT a.ACNO FROM accountmaster a, accounttypemaster am WHERE a.accttype = am.acctcode and am.CrDbFlag in('B','D') and a.OPENINGDT /*between '" + fromDt + "' and*/<= '" + toDt + "' AND (a.CLOSINGDATE='' OR ifnull(a.CLOSINGDATE,'')='' OR a.CLOSINGDATE>'" + toDt + "') AND a.CURBRCODE='" + orgBrnCode + "') ORDER BY ACNO").getResultList();
                } else {
                    dataList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT ACNO FROM accountmaster WHERE OPENINGDT /*between '" + fromDt + "' and*/<= '" + toDt + "' AND (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + toDt + "') AND CURBRCODE='" + orgBrnCode + "') AND SUBSTRING(ACNO,3,2) in (" + acType + ") ORDER BY ACNO").getResultList();
                }
            }
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    String acno = element.get(0).toString();
                    String custname = element.get(1).toString();

                    List stdList = loanRemote.getStdAssect();
                    Vector vtr = (Vector) stdList.get(0);
                    String stdAssect = vtr.get(0).toString();
                    List checkAcnoList;
                    if (stdAssect.equalsIgnoreCase("Y")) {
                        checkAcnoList = em.createNativeQuery("SELECT ACNO FROM accountstatus WHERE ACNO='" + acno + "' union select acno from accountmaster where acno = '" + acno + "' and AccStatus = 1").getResultList();
                    } else {
                        checkAcnoList = em.createNativeQuery("SELECT ACNO FROM accountstatus WHERE ACNO='" + acno + "'").getResultList();
                    }

                    if (!checkAcnoList.isEmpty()) {
                        double balAsOn = loanRemote.fnBalosForAccountAsOn(acno, toDt);
                        if (balAsOn != 0) {
                            String presentStatus = interBranchFacade.fnGetLoanStatusBetweenDate(acno, fromDt, toDt);

                            String acnature = ftsFacade.getAccountNature(acno);
                            balance = new BigDecimal(balAsOn);
//                            if (acnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                                List balList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM ca_recon WHERE DT <='" + toDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
//                                if (!balList.isEmpty()) {
//                                    Vector balElement = (Vector) balList.get(0);
//                                    balance = new BigDecimal(formatter.format(balElement.get(0)));
//                                }
//                            } else {
//                                List balList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM loan_recon WHERE DT <='" + toDt + "' AND AUTH='Y' AND ACNO='" + acno + "' ").getResultList();
//                                if (!balList.isEmpty()) {
//                                    Vector balElement = (Vector) balList.get(0);
//                                    balance = new BigDecimal(formatter.format(balElement.get(0)));
//                                }
//                            }

                            if (status.equalsIgnoreCase("0")) {
                                if (presentStatus.equalsIgnoreCase("DOU") || presentStatus.equalsIgnoreCase("SUB") || presentStatus.equalsIgnoreCase("LOS")) {
                                    List minEffList = em.createNativeQuery("SELECT date_format(MAX(EFFDT),'%d/%m/%Y') FROM accountstatus a, codebook c WHERE ACNO='" + acno + "' AND SUBSTRING(description,1,3)='" + presentStatus + "' AND GROUPCODE = 3 and a.spflag = c.code and effdt /*<=*/ between '" + fromDt + "' and '" + toDt + "'").getResultList();
                                    Vector minElement = (Vector) minEffList.get(0);
                                    npaDt = minElement.get(0).toString();

                                    NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                    pojo.setAcno(acno);
                                    pojo.setCustName(custname);
                                    pojo.setBalance(balance);
                                    pojo.setStandardDt("");
                                    if (presentStatus.equalsIgnoreCase("SUB")) {
                                        pojo.setPresentStatus("SUB STANDARD");
                                        pojo.setSubStandardDt(npaDt);
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt("");
                                    } else if (presentStatus.equalsIgnoreCase("DOU")) {
                                        pojo.setPresentStatus("DOUBT FUL");
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt(npaDt);
                                        pojo.setLossDt("");
                                    } else if (presentStatus.equalsIgnoreCase("LOS")) {
                                        pojo.setPresentStatus("LOSS");
                                        pojo.setSubStandardDt("");
                                        pojo.setDoubtFullDt("");
                                        pojo.setLossDt(npaDt);
                                    }

                                    resultList.add(pojo);
                                } else {
                                    if (stdAssect.equalsIgnoreCase("Y")) {
                                        if (presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("STANDARD")) {
                                            List minEffList = em.createNativeQuery("select date_format(OpeningDt,'%d/%m/%Y') from accountmaster where acno = '" + acno + "'").getResultList();
                                            Vector minElement = (Vector) minEffList.get(0);
                                            npaDt = minElement.get(0).toString();

                                            NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                            pojo.setAcno(acno);
                                            pojo.setCustName(custname);
                                            pojo.setBalance(balance);
                                            pojo.setStandardDt("");
                                            if (presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("STANDARD")) {
                                                pojo.setPresentStatus("STANDARD");
                                                pojo.setDoubtFullDt("");
                                                pojo.setLossDt("");
                                                pojo.setStandardDt(npaDt);
                                                pojo.setSubStandardDt("");
                                            }
                                            resultList.add(pojo);
                                        }
                                    }
                                }
                            } else if (status.equalsIgnoreCase("1")) {
                                if (acStatus.equalsIgnoreCase("STANDARD")) {
                                    if (presentStatus.equalsIgnoreCase("STANDARD") || presentStatus.equalsIgnoreCase("OPE")) {
                                        List minEffList = em.createNativeQuery("select date_format(OpeningDt,'%d/%m/%Y') from accountmaster where acno = '" + acno + "'").getResultList();
                                        Vector minElement = (Vector) minEffList.get(0);
                                        npaDt = minElement.get(0).toString();

                                        NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                        pojo.setAcno(acno);
                                        pojo.setCustName(custname);
                                        pojo.setBalance(balance);
                                        pojo.setPresentStatus(acStatus);
                                        pojo.setStandardDt("");
                                        if (acStatus.substring(0, 3).equalsIgnoreCase("SUB")) {
                                            pojo.setSubStandardDt(npaDt);
                                            pojo.setDoubtFullDt("");
                                            pojo.setLossDt("");
                                        } else if (acStatus.substring(0, 3).equalsIgnoreCase("DOU")) {
                                            pojo.setSubStandardDt("");
                                            pojo.setDoubtFullDt(npaDt);
                                            pojo.setLossDt("");
                                        } else if (acStatus.substring(0, 3).equalsIgnoreCase("LOS")) {
                                            pojo.setSubStandardDt("");
                                            pojo.setDoubtFullDt("");
                                            pojo.setLossDt(npaDt);
                                        } else if (acStatus.equalsIgnoreCase("STANDARD")) {
                                            pojo.setDoubtFullDt("");
                                            pojo.setLossDt("");
                                            pojo.setStandardDt(npaDt);
                                            pojo.setSubStandardDt("");
                                        }
                                        resultList.add(pojo);
                                    }
                                } else {
                                    if (presentStatus.equalsIgnoreCase(acStatus.substring(0, 3))) {
                                        List minEffList = em.createNativeQuery("select date_format(max(effdt),'%d/%m/%Y') from accountstatus a, codebook c where acno='" + acno + "' and substring(description,1,3)='" + presentStatus + "' and groupcode = 3 and a.spflag = c.code  and effdt /*<=*/ between '" + fromDt + "' and '" + toDt + "'").getResultList();
                                        Vector minElement = (Vector) minEffList.get(0);
                                        npaDt = minElement.get(0).toString();

                                        NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                                        pojo.setAcno(acno);
                                        pojo.setCustName(custname);
                                        pojo.setBalance(balance);
                                        pojo.setPresentStatus(acStatus);
                                        pojo.setStandardDt("");
                                        if (acStatus.substring(0, 3).equalsIgnoreCase("SUB")) {
                                            pojo.setSubStandardDt(npaDt);
                                            pojo.setDoubtFullDt("");
                                            pojo.setLossDt("");
                                        } else if (acStatus.substring(0, 3).equalsIgnoreCase("DOU")) {
                                            pojo.setSubStandardDt("");
                                            pojo.setDoubtFullDt(npaDt);
                                            pojo.setLossDt("");
                                        } else if (acStatus.substring(0, 3).equalsIgnoreCase("LOS")) {
                                            pojo.setSubStandardDt("");
                                            pojo.setDoubtFullDt("");
                                            pojo.setLossDt(npaDt);
                                        }
                                        resultList.add(pojo);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public List<NpaReportPojo> getNpaReportData(String acType, String tillDt, String orgBrnCode) throws ApplicationException {
        List<NpaReportPojo> resultList = new ArrayList<NpaReportPojo>();
        Integer subStDays = 0, doubtDays = 0, irrDays = 0, doubtDays2 = 0, doubtDays3 = 0;
        String acno = "", custname = "", status = "", reason = "", maxDt = "";
        BigDecimal sumInterest = new BigDecimal("0");
        BigDecimal sumPaid = new BigDecimal("0");
        BigDecimal odlimit = new BigDecimal("0");
        BigDecimal balance = new BigDecimal("0");
        try {
            List acTypeList = em.createNativeQuery("select acctcode from npa_master_type where acctcode = '" + acType + "'").getResultList();
            if (acTypeList.isEmpty()) {
                throw new ApplicationException("Please Fill The NPA Master for Account Type -->" + acType);
            }
            List acNatureList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acType + "'").getResultList();
            Vector element = (Vector) acNatureList.get(0);
            String acNature = element.get(0).toString();
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List daysList = em.createNativeQuery("select days_substd,days_dbt1,days_irreg,days_dbt2,days_dbt3 from npa_master_type where acctcode='" + acType + "'").getResultList();
                Vector dayElement = (Vector) daysList.get(0);
                subStDays = Integer.parseInt(dayElement.get(0).toString());
                doubtDays = Integer.parseInt(dayElement.get(1).toString());
                irrDays = Integer.parseInt(dayElement.get(2).toString());
                doubtDays2 = Integer.parseInt(dayElement.get(3).toString());
                doubtDays3 = Integer.parseInt(dayElement.get(4).toString());
                List acnoList = new ArrayList();
                if (orgBrnCode.equalsIgnoreCase("0A")) {
                    acnoList = em.createNativeQuery("select acno,odlimit,custname from loan_appparameter l where acno in(select r.acno from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + acType + "' and r.dt<='" + tillDt + "' and r.acno=a.acno group by r.acno having(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)))<0)").getResultList();
                } else {
                    acnoList = em.createNativeQuery("select acno,odlimit,custname from loan_appparameter l where acno in(select r.acno from ca_recon r,accountmaster a where substring(r.acno,3,2)='" + acType + "' and r.dt<='" + tillDt + "' and r.acno=a.acno and a.curbrcode='" + orgBrnCode + "' group by r.acno having(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)))<0)").getResultList();
                }
                if (!acnoList.isEmpty()) {
                    for (int i = 0; i < acnoList.size(); i++) {
                        Vector acnoElement = (Vector) acnoList.get(i);
                        acno = acnoElement.get(0).toString();
                        odlimit = new BigDecimal(acnoElement.get(1).toString());
                        custname = acnoElement.get(2).toString();
                        List balList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from ca_recon where dt <='" + tillDt + "' and acno='" + acno + "' ").getResultList();
                        Vector balElement = (Vector) balList.get(0);
                        balance = new BigDecimal(balElement.get(0).toString());
                        if ((balance.compareTo(new BigDecimal("0")) == -1) || (balance.compareTo(new BigDecimal("0")) == 1)) {
                            if (balance.abs().compareTo(odlimit) == 1) {
                                maxDt = prcFillDailyBal(acno, tillDt);
                                long dayDiff = CbsUtil.dayDiff(ymd.parse(maxDt), ymd.parse(tillDt));
                                if (dayDiff > subStDays) {
                                    if (dayDiff < doubtDays && dayDiff >= subStDays) {
                                        status = "SUB STANDARD";
                                    } else if (dayDiff >= doubtDays) {
                                        status = "DOUBT FUL";
                                    }
                                    Long a = (dayDiff / 90);
                                    reason = "Bal exceed from  Limit from " + Long.toString(a) + " Quarters";
                                }
                            } else if (balance.abs().compareTo(odlimit) == -1) {
                                List maxDtList = em.createNativeQuery("select date_format(max(dt),'%Y%m%d') from ca_recon where acno='" + acno + "' and dt<='" + tillDt + "'").getResultList();
                                Vector maxDtElement = (Vector) maxDtList.get(0);
                                maxDt = maxDtElement.get(0).toString();

                                String addDt = CbsUtil.dateAdd(tillDt, -subStDays);

                                List sumIntList = em.createNativeQuery("select ifnull(sum(dramt),0) from ca_recon where trantype=8 and dt > '" + addDt + "' and dt <= '" + tillDt + "' and acno='" + acno + "'").getResultList();
                                Vector sumIntElement = (Vector) sumIntList.get(0);
                                sumInterest = new BigDecimal(sumIntElement.get(0).toString());

                                List sumPaidList = em.createNativeQuery("select ifnull(sum(cramt),0) from ca_recon where dt > '" + addDt + "' and dt <= '" + tillDt + "' and acno='" + acno + "'").getResultList();
                                Vector sumPaidElement = (Vector) sumPaidList.get(0);
                                sumPaid = new BigDecimal(sumPaidElement.get(0).toString());
                                if ((sumInterest.compareTo(sumPaid) == 1) || (sumPaid.compareTo(new BigDecimal("0")) == 0)) {
                                    long secondDayDiff = CbsUtil.dayDiff(ymd.parse(maxDt), ymd.parse(tillDt));
                                    if (secondDayDiff > subStDays) {
                                        if (secondDayDiff < doubtDays && secondDayDiff >= subStDays) {
                                            status = "SUB STANDARD";
                                        } else if (secondDayDiff >= doubtDays) {
                                            status = "DOUBT FUL";
                                        }
                                        reason = "Amt. Cr. to A/C, not enough to service Int. Amt.";
                                    }
                                }
                            }
                        }
                    }

                }
                NpaReportPojo pojo = new NpaReportPojo();
                pojo.setAcno(acno);
                pojo.setCustName(custname);
                pojo.setBalance(balance);
                pojo.setLimit(odlimit);
                pojo.setDueDate(maxDt);
                pojo.setStatus(status);
                pojo.setReason(reason);

                resultList.add(pojo);
            } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                List loanAcnoList = new ArrayList();
                if (orgBrnCode.equalsIgnoreCase("0A")) {
                    loanAcnoList = em.createNativeQuery("select e.acno,date_format(min(e.duedt),'%Y%m%d') duedt,a.custname from emidetails e,accountmaster a where e.acno=a.acno "
                            + " and e.duedt<'" + tillDt + "' and upper(e.status)<>'paid' and substring(e.acno,3,2)='" + acType + "' group by e.acno,a.custname").getResultList();
                } else {
                    loanAcnoList = em.createNativeQuery("select e.acno,date_format(min(e.duedt),'%Y%m%d') duedt,a.custname from emidetails e,accountmaster a where e.acno=a.acno and a.curbrcode='" + orgBrnCode + "' "
                            + "and e.duedt<'" + tillDt + "' and upper(e.status)<>'paid' and substring(e.acno,3,2)='" + acType + "' group by e.acno,a.custname").getResultList();
                }
                if (!loanAcnoList.isEmpty()) {
                    for (int i = 0; i < loanAcnoList.size(); i++) {
                        Vector loanAcnoElement = (Vector) loanAcnoList.get(i);
                        acno = loanAcnoElement.get(0).toString();
                        maxDt = loanAcnoElement.get(1).toString();
                        custname = loanAcnoElement.get(2).toString();

                        List balList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from loan_recon where acno='" + acno + "' ").getResultList();
                        Vector balElement = (Vector) balList.get(0);
                        BigDecimal amtBal = new BigDecimal(balElement.get(0).toString());
                        if (amtBal.compareTo(new BigDecimal("0")) == 0) {
                            Query updateQuery = em.createNativeQuery("update emidetails set status='paid' where acno='" + acno + "'");
                            int updateResult = updateQuery.executeUpdate();
                            if (updateResult <= 0) {
                                throw new ApplicationException("Update problem in emidetails !");
                            }
                        }
                        List tmpDateList = em.createNativeQuery("(select date_format(min(duedt),'%Y%m%d') from emidetails where acno='" + acno + "' and status<>'paid')").getResultList();
                        Vector tmpDateElement = (Vector) tmpDateList.get(0);
                        String tmpDate = tmpDateElement.get(0).toString();

                        long tmpDateDiff = CbsUtil.dayDiff(ymd.parse(tmpDate), ymd.parse(tillDt));
                        if (tmpDateDiff > 0) {
                            if (tmpDateDiff <= irrDays && tmpDateDiff > 0) {
                                status = "IRREGULAR";
                            } else if (tmpDateDiff <= subStDays && tmpDateDiff > irrDays) {
                                status = "SUB STANDARD";
                            } else if (tmpDateDiff <= doubtDays && tmpDateDiff > subStDays) {
                                status = "DOUBTFUL I";
                            } else if (tmpDateDiff <= doubtDays2 && tmpDateDiff > doubtDays) {
                                status = "DOUBTFUL II";
                            } else if (tmpDateDiff <= doubtDays3 && tmpDateDiff > doubtDays2) {
                                status = "DOUBTFUL III";
                            } else if (tmpDateDiff > doubtDays3) {
                                status = "LOSS";
                            }
                        }
                        List instBalList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from loan_recon where acno='" + acno + "' and auth='Y' and dt<='" + tillDt + "'").getResultList();
                        Vector instBalElement = (Vector) instBalList.get(0);
                        balance = new BigDecimal(instBalElement.get(0).toString());
                        if (balance.compareTo(new BigDecimal("0")) == -1) {

                            reason = "Last EMI due since : " + dmy.format(ymd.parse(maxDt));

                            NpaReportPojo pojo = new NpaReportPojo();
                            pojo.setAcno(acno);
                            pojo.setCustName(custname);
                            pojo.setBalance(balance);
                            pojo.setLimit(new BigDecimal("0"));
                            pojo.setDueDate(maxDt);
                            pojo.setStatus(status);
                            pojo.setReason(reason);

                            resultList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public List<NpaCategoryReport> getNpaCategoryReportData(String forQuarter, String year, String orgBrnCode) throws ApplicationException {
        List<NpaCategoryReport> resultList = new ArrayList<NpaCategoryReport>();
        String remarks = "", secUnSec = "", dt = "", type = "", description = "";
        BigDecimal amount = new BigDecimal("0");
        BigDecimal matValue = new BigDecimal("0");
        BigDecimal installment = new BigDecimal("0");
        BigDecimal unSecAmt = new BigDecimal("0");
        BigDecimal unSecSecAmt = new BigDecimal("0");
        try {
            String todt = CbsUtil.getQuarterFirstAndLastDate(forQuarter, year, "L");
            List dataList = new ArrayList();
            if (orgBrnCode.equalsIgnoreCase("0A")) {
                dataList = em.createNativeQuery("select a.acno,ifnull(date_format(b.sanctionlimitdt,'%d/%m/%Y'),''),sanctionlimit,a.custname "
                        + "from accountmaster a,loan_appparameter b where a.acno=b.acno and a.openingdt<='" + todt + "' "
                        + "and ( closingdate='' or closingdate>'" + todt + "' or ifnull(closingdate,'')='') and substring (a.acno,3,2) "
                        + "in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') AND ACCTCODE<>'" + CbsAcCodeConstant.CURRENT_AC.getAcctCode() + "') ").getResultList();
            } else {
                dataList = em.createNativeQuery("select a.acno,ifnull(date_format(b.sanctionlimitdt,'%d/%m/%Y'),''),sanctionlimit,a.custname "
                        + "from accountmaster a,loan_appparameter b where a.acno=b.acno and a.curbrcode='" + orgBrnCode + "' and a.openingdt<='" + todt + "' "
                        + "and ( closingdate='' or closingdate>'" + todt + "' or ifnull(closingdate,'')='') and substring (a.acno,3,2) "
                        + "in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') AND ACCTCODE<>'" + CbsAcCodeConstant.CURRENT_AC.getAcctCode() + "') ").getResultList();
            }
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    String acno = element.get(0).toString();
                    String sanctionlimitdt = element.get(1).toString();
                    BigDecimal sanctionlimit = new BigDecimal(element.get(2).toString());
                    String custname = element.get(3).toString();

                    String loanStatus = interBranchFacade.fnGetLoanStatusTillDate(acno, todt);
                    if (loanStatus.equalsIgnoreCase("DOU") || loanStatus.equalsIgnoreCase("SUB") || loanStatus.equalsIgnoreCase("LOS")) {
                        String acnature = ftsFacade.getAccountNature(acno);
                        if (acnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            List amountList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM ca_recon WHERE DT <='" + todt + "' AND ACNO='" + acno + "' ").getResultList();
                            if (!amountList.isEmpty()) {
                                Vector amountElement = (Vector) amountList.get(0);
                                amount = new BigDecimal(formatter.format(amountElement.get(0)));
                            }
                        } else {
                            List amountList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM loan_recon WHERE DT <='" + todt + "' AND ACNO='" + acno + "' ").getResultList();
                            if (!amountList.isEmpty()) {
                                Vector amountElement = (Vector) amountList.get(0);
                                amount = new BigDecimal(formatter.format(amountElement.get(0)));
                            }
                        }
                        if (amount.compareTo(new BigDecimal("0")) == -1) {
                            List dtList = em.createNativeQuery("select ifnull(date_format(min(effdt),'%d/%m/%Y'),'') from accountstatus where acno='" + acno + "' and spflag in (11,12,13)").getResultList();
                            Vector dtElement = (Vector) dtList.get(0);
                            dt = dtElement.get(0).toString();

//                            List typeList = em.createNativeQuery("select ifnull(classification,'') from vw_applicantgeneral where acno='" + acno + "'").getResultList();
//                            Vector typeElement = (Vector) typeList.get(0);
//                            type = typeElement.get(0).toString();
                            List typeList = em.createNativeQuery("select ifnull(sector,'') from cbs_loan_borrower_details where acc_no='" + acno + "'").getResultList();
                            Vector typeElement = (Vector) typeList.get(0);
                            type = typeElement.get(0).toString();

                            List installAmtList = em.createNativeQuery("select ifnull(max(installamt),0) from emidetails where acno = '" + acno + "'").getResultList();
                            Vector installAmtElement = (Vector) installAmtList.get(0);
                            installment = new BigDecimal(installAmtElement.get(0).toString());

                            List matValueList = em.createNativeQuery("select ifnull(sum(lienvalue),0) from loansecurity where acno='" + acno + "' and (ifnull(expirydate,'')='' or expirydate='' or expirydate>='" + todt + "')").getResultList();
                            Vector matValueElement = (Vector) matValueList.get(0);
                            matValue = new BigDecimal(matValueElement.get(0).toString());

                            if ((matValue.compareTo(amount.abs()) == 1) && (matValue != new BigDecimal("0"))) {
                                secUnSec = "SECURE";
                                unSecAmt = amount.abs();
                            } else if ((matValue.compareTo(amount.abs()) == -1 || matValue.compareTo(amount.abs()) == 0) && (matValue != new BigDecimal("0"))) {
                                secUnSec = "UNSECURE";
                                unSecAmt = amount.abs().subtract(matValue);
                                unSecSecAmt = matValue;
                            } else if (matValue.compareTo(new BigDecimal("0")) == 0) {
                                secUnSec = "UNSECURE";
                                unSecAmt = amount.abs();
                            }

                            long dayDiff = CbsUtil.dayDiff(dmy.parse(dt), ymd.parse(todt));
                            if ((dayDiff >= 365 && dayDiff <= 730) && (secUnSec.equalsIgnoreCase("SECURE") || secUnSec.equalsIgnoreCase("UNSECURE")) && loanStatus.equalsIgnoreCase("DOU")) {
                                description = "UPTO ONE YEAR";
                            } else if ((dayDiff >= 731 && dayDiff <= 1095) && (secUnSec.equalsIgnoreCase("SECURE") || secUnSec.equalsIgnoreCase("UNSECURE")) && loanStatus.equalsIgnoreCase("DOU")) {
                                description = "ONE YEAR TO THREE YEARS";
                            } else if ((dayDiff > 1095) && (secUnSec.equalsIgnoreCase("SECURE") || secUnSec.equalsIgnoreCase("UNSECURE")) && loanStatus.equalsIgnoreCase("DOU")) {
                                description = "ABOVE THREE YEARS";
                            }
                        }
                        NpaCategoryReport pojo = new NpaCategoryReport();
                        pojo.setAcno(acno);
                        pojo.setCustName(custname);
                        pojo.setType(type);
                        pojo.setSanctionLimitDt(sanctionlimitdt);
                        pojo.setSanctionLimit(sanctionlimit);
                        pojo.setAmount(amount);
                        pojo.setInstallAmt(installment);
                        pojo.setDt(dt);
                        pojo.setRemarks(remarks);
                        pojo.setMatValue(matValue);
                        if (loanStatus.equalsIgnoreCase("SUB")) {
                            pojo.setStatus("SUB STANDARD");
                        } else if (loanStatus.equalsIgnoreCase("DOU")) {
                            pojo.setStatus("DOUBT FUL");
                        } else if (loanStatus.equalsIgnoreCase("LOS")) {
                            pojo.setStatus("LOSS");
                        }
                        pojo.setSecUnSec(secUnSec);
                        pojo.setUnSecAmt(unSecAmt);
                        pojo.setUnSecSecAmt(unSecSecAmt);
                        pojo.setDescription(description);

                        resultList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public String prcFillDailyBal(String acno, String tillDt) throws ApplicationException {
        String dt = "", flagInsert = "false";
        try {
            List dtList = em.createNativeQuery("select date_format(min(a.dt),'%Y%m%d') from (select dt from ca_recon where acno='" + acno + "' and "
                    + "dt < '" + tillDt + "' group by dt having sign(sum(ifnull(Cramt,0))- sum(ifnull(DrAmt,0)))=-1) a").getResultList();
            Vector element = (Vector) dtList.get(0);
            dt = element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return dt;
    }

    public List<OfficeAccountPojo> getOfficeAccountTransaction(String officeAcno, String reportDt, String orgBrnCode) throws ApplicationException {
        List<OfficeAccountPojo> resultList = new ArrayList<OfficeAccountPojo>();
        try {
            List officeAcnoList = em.createNativeQuery("select acno from gl_recon where acno='" + officeAcno + "'").getResultList();
            if (!officeAcnoList.isEmpty()) {
                List dataList = em.createNativeQuery("select org_brnid,trantype,cast(ifnull(cramt,0) as decimal(25,2)),ifnull(details,''),date_format(dt,'%d/%m/%Y') from gl_recon where dt<='" + reportDt + "' and acno='" + officeAcno + "' and dest_brnid='" + orgBrnCode + "' and org_brnid<>'" + orgBrnCode + "'").getResultList();
                if (!dataList.isEmpty()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        Vector dataElement = (Vector) dataList.get(i);
                        String baseBranch = commonReport.getBranchNameByBrncode(dataElement.get(0).toString());
                        String tranType = commonReport.getTransactionType(dataElement.get(1).toString());
                        BigDecimal amount = new BigDecimal(dataElement.get(2).toString());
                        String particulars = dataElement.get(3).toString();
                        String tranDt = dataElement.get(4).toString();

                        OfficeAccountPojo pojo = new OfficeAccountPojo();
                        pojo.setBaseBranch(baseBranch);
                        pojo.setTranType(tranType);
                        pojo.setTranDt(tranDt);
                        pojo.setAmount(amount);
                        pojo.setParticulars(particulars);
                        pojo.setGlhead(officeAcno);

                        resultList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultList;
    }

    public List<AllSmsPojo> getAllMessageAtHo(String fromdt, String todt, String reportType) throws ApplicationException {
        List<AllSmsPojo> resultList = new ArrayList<AllSmsPojo>();
        String messageType = "";
        BigDecimal chargePerMessage = new BigDecimal("0");
        Integer totalMessage = 0;
        try {
            if (reportType.equalsIgnoreCase("Head-Office")) {
                List messageTypeList = getMessageTypeAndChargePerMessage();
                String branchname = commonReport.getBranchNameByBrncode("90");
                for (int j = 0; j < messageTypeList.size(); j++) {
                    Vector messageElement = (Vector) messageTypeList.get(j);
                    messageType = messageElement.get(0).toString();
                    chargePerMessage = new BigDecimal(messageElement.get(1).toString());
                    totalMessage = getTotalSendMessageByType("", messageType, fromdt, todt);

                    AllSmsPojo pojo = new AllSmsPojo();
                    pojo.setMessageType(messageType);
                    pojo.setTotalMessage(totalMessage);
                    pojo.setChargePerMessage(chargePerMessage);
                    pojo.setTotalMessageAmount(chargePerMessage.multiply(new BigDecimal(totalMessage)));
                    pojo.setBranchName(branchname);
                    resultList.add(pojo);
                }
            } else {
                List branchList = commonReport.getAlphacodeExcludingHo();
                if (!branchList.isEmpty()) {
                    List messageTypeList = getMessageTypeAndChargePerMessage();
                    if (!messageTypeList.isEmpty()) {
                        for (int i = 0; i < branchList.size(); i++) {
                            Vector branchElement = (Vector) branchList.get(i);
                            String brncode = branchElement.get(0).toString();
                            if (brncode.length() == 1) {
                                brncode = "0" + brncode;
                            }

                            String branchname = commonReport.getBranchNameByBrncode(brncode);
                            for (int j = 0; j < messageTypeList.size(); j++) {
                                Vector messageElement = (Vector) messageTypeList.get(j);
                                messageType = messageElement.get(0).toString();
                                chargePerMessage = new BigDecimal(messageElement.get(1).toString());
                                totalMessage = getTotalSendMessageByType(brncode, messageType, fromdt, todt);

                                AllSmsPojo pojo = new AllSmsPojo();
                                pojo.setMessageType(messageType);
                                pojo.setTotalMessage(totalMessage);
                                pojo.setChargePerMessage(chargePerMessage);
                                pojo.setTotalMessageAmount(chargePerMessage.multiply(new BigDecimal(totalMessage)));
                                pojo.setBranchName(branchname);

                                resultList.add(pojo);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public List getMessageTypeAndChargePerMessage() throws ApplicationException {
        List list = new ArrayList();
        try {
            list = em.createNativeQuery("select message_type,charge_per_message from mb_charge_master where status='A'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }

    public Integer getTotalSendMessageByType(String brncode, String messageType, String fromdt, String todt) throws ApplicationException {
        Integer totalMessage = 0;
        try {
            if (props.getProperty("sms.template").equals("en")) {
                List totalMessageList = new ArrayList();
                if (brncode.equalsIgnoreCase("")) {
                    totalMessageList = em.createNativeQuery("select message from mb_push_msg_tab where message_type='" + messageType + "' AND DATE_FORMAT(dt,'%Y%m%d') BETWEEN '" + fromdt + "' AND '" + todt + "' "
                            + "and message_status in (2,3,4)").getResultList();
                } else {
                    totalMessageList = em.createNativeQuery("select message from mb_push_msg_tab where "
                            + "substring(acno,1,2)='" + brncode + "' and message_type='" + messageType + "' AND DATE_FORMAT(dt,'%Y%m%d') BETWEEN '" + fromdt + "' AND '" + todt + "' "
                            + "and message_status in (2,3,4)").getResultList();
                }
                for (int i = 0; i < totalMessageList.size(); i++) {
                    Vector element = (Vector) totalMessageList.get(i);
                    Integer smsLength = element.get(0).toString().length();
                    if (smsLength <= 160) {
                        totalMessage = totalMessage + 1;
                    } else if (smsLength > 160 && smsLength <= 320) {
                        totalMessage = totalMessage + 2;
                    } else if (smsLength > 320) {
                        totalMessage = totalMessage + 3;
                    }
                }
            } else if (props.getProperty("sms.template").equals("hi")) {
                List totalMessageList = em.createNativeQuery("select length(message) from mb_push_msg_tab where "
                        + "substring(acno,1,2)='" + brncode + "' and message_type='" + messageType + "' and "
                        + "dt>='" + fromdt + "' and dt<'" + todt + "' and message_status in (2,3,4)").getResultList();
                for (int i = 0; i < totalMessageList.size(); i++) {
                    Vector element = (Vector) totalMessageList.get(i);
                    Integer smsLength = Integer.parseInt(element.get(0).toString());
                    if (smsLength <= 160) {
                        totalMessage = totalMessage + 1;
                    } else if (smsLength > 160 && smsLength <= 320) {
                        totalMessage = totalMessage + 2;
                    } else if (smsLength > 320) {
                        totalMessage = totalMessage + 3;
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return totalMessage;
    }

    public List<IndividualSmsPojo> getIndividualMessageAtHoByAcno(String acno, String fromdt, String todt) throws ApplicationException {
        List<IndividualSmsPojo> resultList = new ArrayList<IndividualSmsPojo>();
        String messageType = "";
        BigDecimal chargePerMessage = new BigDecimal("0");
        Integer totalMessage = 0;
        try {
            List messageTypeList = getMessageTypeAndChargePerMessage();
            if (!messageTypeList.isEmpty()) {
                for (int j = 0; j < messageTypeList.size(); j++) {
                    Vector messageElement = (Vector) messageTypeList.get(j);
                    messageType = messageElement.get(0).toString();
                    chargePerMessage = new BigDecimal(messageElement.get(1).toString());
                    totalMessage = getTotalSendMessageByAcno(acno, messageType, fromdt, todt);

                    IndividualSmsPojo pojo = new IndividualSmsPojo();
                    pojo.setAccountNo(acno);
                    pojo.setMessageType(messageType);
                    pojo.setTotalMessage(totalMessage);
                    pojo.setChargePerMessage(chargePerMessage);
                    pojo.setTotalMessageAmount(chargePerMessage.multiply(new BigDecimal(totalMessage)));

                    resultList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public Integer getTotalSendMessageByAcno(String acno, String messageType, String fromdt, String todt) throws ApplicationException {
        Integer totalMessage = 0;
        try {
            List totalMessageList = em.createNativeQuery("select message from mb_push_msg_tab where acno='" + acno + "' and "
                    + "message_type='" + messageType + "' and dt>='" + fromdt + "' and dt<'" + todt + "' and "
                    + "message_status in(2,3,4)").getResultList();
            if (!totalMessageList.isEmpty()) {
                for (int i = 0; i < totalMessageList.size(); i++) {
                    Vector element = (Vector) totalMessageList.get(i);
                    Integer smsLength = element.get(0).toString().length();
                    if (smsLength <= 160) {
                        totalMessage = totalMessage + 1;
                    } else if (smsLength > 160 && smsLength <= 320) {
                        totalMessage = totalMessage + 2;
                    } else if (smsLength > 320) {
                        totalMessage = totalMessage + 3;
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return totalMessage;
    }

    public List<AllSmsPojo> getIndividualMessageAtHoByBranch(String brncode, String fromdt, String todt) throws ApplicationException {
        List<AllSmsPojo> resultList = new ArrayList<AllSmsPojo>();
        String messageType = "";
        BigDecimal chargePerMessage = new BigDecimal("0");
        Integer totalMessage = 0;
        try {
            List messageTypeList = getMessageTypeAndChargePerMessage();
            if (!messageTypeList.isEmpty()) {
                String branchname = commonReport.getBranchNameByBrncode(brncode);
                for (int j = 0; j < messageTypeList.size(); j++) {
                    Vector messageElement = (Vector) messageTypeList.get(j);
                    messageType = messageElement.get(0).toString();
                    chargePerMessage = new BigDecimal(messageElement.get(1).toString());
                    totalMessage = getTotalSendMessageByType(brncode, messageType, fromdt, todt);

                    AllSmsPojo pojo = new AllSmsPojo();
                    pojo.setMessageType(messageType);
                    pojo.setTotalMessage(totalMessage);
                    pojo.setChargePerMessage(chargePerMessage);
                    pojo.setTotalMessageAmount(chargePerMessage.multiply(new BigDecimal(totalMessage)));
                    pojo.setBranchName(branchname);

                    resultList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public List<NpaRecoveryPojo> getNpaAutoMarkingData(String acType, String fromDt, String toDt, String orgnBrCode) throws ApplicationException {

        List<NpaRecoveryPojo> dataList = new ArrayList<NpaRecoveryPojo>();
        List result = new ArrayList();
        try {
            if (orgnBrCode.equalsIgnoreCase("0A")) {
                if (acType.equalsIgnoreCase("ALL")) {
                    result = em.createNativeQuery("select a.acno,b.custname,b.accStatus,a.spflag,date_format(effdt,'%Y%m%d'),c.Description,ifnull(b.ClosingDate,'') from accountstatus a,accountmaster b,codebook c where REMARK like '%OPERATIVE, AUTO MARKING%' "
                            + "and a.acno=b.acno and b.accStatus = c.Code  and c.GroupCode = 3 and c.Code <> 0 and a.effdt between '" + fromDt + "' and '" + toDt + "' order by a.acno").getResultList();
                } else {
                    result = em.createNativeQuery("select a.acno,b.custname,b.accStatus,a.spflag,date_format(effdt,'%Y%m%d'),c.Description,ifnull(b.ClosingDate,'') from accountstatus a,accountmaster b,codebook c where REMARK like '%OPERATIVE, AUTO MARKING%' "
                            + "and a.acno=b.acno and b.accttype='" + acType + "' and b.accStatus = c.Code  and c.GroupCode = 3 and c.Code <> 0 and a.effdt between '" + fromDt + "' and '" + toDt + "' order by a.acno").getResultList();
                }

            } else {
                if (acType.equalsIgnoreCase("ALL")) {
                    result = em.createNativeQuery("select a.acno,b.custname,b.accStatus,a.spflag,date_format(effdt,'%Y%m%d'),c.Description,ifnull(b.ClosingDate,'') from accountstatus a,accountmaster b,codebook c where REMARK like '%OPERATIVE, AUTO MARKING%' "
                            + "and a.acno=b.acno and curbrcode = '" + orgnBrCode + "' and b.accStatus = c.Code  and c.GroupCode = 3 and c.Code <> 0 and a.effdt between '" + fromDt + "' and '" + toDt + "' order by a.acno").getResultList();
                } else {
                    result = em.createNativeQuery("select a.acno,b.custname,b.accStatus,a.spflag,date_format(effdt,'%Y%m%d'),c.Description,ifnull(b.ClosingDate,'') from accountstatus a,accountmaster b,codebook c where REMARK like '%OPERATIVE, AUTO MARKING%' "
                            + "and a.acno=b.acno and curbrcode = '" + orgnBrCode + "' and b.accttype='" + acType + "' and b.accStatus = c.Code  and c.GroupCode = 3 and c.Code <> 0 and a.effdt between '" + fromDt + "' and '" + toDt + "' order by a.acno").getResultList();
                }

            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    String acNo = ele.get(0).toString();
                    String effDt = ele.get(4).toString();
                    String closingDt = ele.get(6).toString();
                    String acSpFlag = ele.get(3).toString();
                    NpaRecoveryPojo pojo = new NpaRecoveryPojo();
                    double balAsOn = loanRemote.fnBalosForAccountAsOn(acNo, toDt);

                    List amtList = em.createNativeQuery("select a.RecAmt,a.RecDt,b.NpaRecAmt,c.spflag,c.remark,d.NpaBal from ("
                            + "(select sum(cramt) as RecAmt,date_format(max(dt),'%d/%m/%Y') as RecDt from loan_recon where acno = '" + acNo + "' "
                            + "and dt between '" + fromDt + "' and '" + toDt + "')a,"
                            + "(select ifnull(sum(cramt),0) as NpaRecAmt from npa_recon where acno = '" + acNo + "' "
                            + "and dt between '" + fromDt + "' and '" + toDt + "')b,"
                            + "(select spflag,b.Description as remark  from accountstatus a,codebook b where a.spno = (select max(spno) from accountstatus where acno = '" + acNo + "' and effdt < '" + effDt + "')and b.GroupCode = 3 and b.Code <> 0 and a.spflag = b.Code)c,"
                            + "(select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) as NpaBal from npa_recon where acno = '" + acNo + "' and dt <= '" + toDt + "')d)").getResultList();

                    Vector amtVector = (Vector) amtList.get(0);
                    double recoveryAmt = Double.parseDouble(amtVector.get(0).toString());
                    String recoveryDt = amtVector.get(1).toString();
                    double NpaIntRecoveryAmt = Double.parseDouble(amtVector.get(2).toString());
                    String spflag = amtVector.get(4).toString();

                    if (!closingDt.equalsIgnoreCase("")) {
                        if (ymd.parse(closingDt).after(ymd.parse(toDt))) {
                            pojo.setAcType(acSpFlag.equalsIgnoreCase("1") ? "OPERATIVE" : acSpFlag); // Current Status
                        } else {
                            pojo.setAcType(ele.get(5).toString()); // Current Status
                        }
                    } else {
                        pojo.setAcType(ele.get(5).toString()); // Current Status  
                    }

                    pojo.setAcno(acNo);
                    pojo.setAcName(ele.get(1).toString());
                    pojo.setNpaStatus(spflag);
                    // pojo.setAcType(ele.get(5).toString()); // Current Status
                    pojo.setLossRec(new BigDecimal(NpaIntRecoveryAmt));
                    pojo.setSubRec(new BigDecimal(recoveryAmt));
                    pojo.setBranchName(recoveryDt);
                    pojo.setBalance(new BigDecimal(Math.abs(balAsOn)));
                    pojo.setDeRec(new BigDecimal(amtVector.get(5).toString()));
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return dataList;
    }
}
