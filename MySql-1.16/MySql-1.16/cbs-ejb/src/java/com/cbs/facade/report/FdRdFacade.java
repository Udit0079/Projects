/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.AbbStatementPojo;
import com.cbs.dto.report.AccountStatementReportPojo;
import com.cbs.dto.report.InterestReportPojo;
import com.cbs.dto.report.OverdueNonEmiResultList;
import com.cbs.dto.report.RbiInspectionPojo;
import com.cbs.dto.report.TdReceiptIntDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.intcal.RDIntCalFacadeRemote;
import com.cbs.facade.intcal.SbIntCalcFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.master.TermDepositMasterFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.SavingIntRateChangePojo;
import com.cbs.pojo.VoucherPrintingPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author root
 */
@Stateless(mappedName = "FdRdFacade")
public class FdRdFacade implements FdRdFacadeRemote {

    @EJB
    CommonReportMethodsRemote common;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @EJB
    AccountOpeningFacadeRemote accountRemote;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    private PostalDetailFacadeRemote postalFacadeRemote;
    @EJB
    AdvancesInformationTrackingRemote aitr;
    @EJB
    ShareReportFacadeRemote shareRemote;
    @EJB
    NpaReportFacadeRemote npaRemote;
    @EJB
    RbiReportFacadeRemote rbiRemote;
    @EJB
    SbIntCalcFacadeRemote sbRemote;
    @EJB
    TermDepositMasterFacadeRemote tdRemote;
    @EJB
    DDSReportFacadeRemote ddsRemote;
    @EJB
    TdReceiptManagementFacadeRemote tdRcptMgmtRemote;
    @EJB
    RDIntCalFacadeRemote rdIntCalRemote;
    @PersistenceContext
    private EntityManager em;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat y_m_dFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private NumberFormat nft = new DecimalFormat("0.00");
    Date date = new Date();

    /**
     * ********************* DATED SECURITY *************
     */
    /**
     *
     * @param dt1
     * @param days
     * @param security_type
     * @param brnCode
     * @return
     */
    @Override
    public List getFdRdNonEmi(String acNature, String acType, String transDt, String brCode) throws ApplicationException {
        List<OverdueNonEmiResultList> overDueResultLst = new ArrayList<OverdueNonEmiResultList>();
        OverdueNonEmiResultList overDueObject;
        try {
            List datalist = new ArrayList();
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                double intrest = 0.0;
                List intrestlist = new ArrayList();
                if (brCode.equalsIgnoreCase("0A")) {
                    if (acType.equalsIgnoreCase("ALL")) {
                        datalist = em.createNativeQuery("select  a.acno,v.voucherno,upper(a.custname),v.prinamt,date_format(v.fddt,'%d/%m/%Y'),date_format(v.matdt,'%d/%m/%Y'),substring(a.Acno, 3, 2) ,v.receiptNo "
                                + "from td_vouchmst v,td_accountmaster a,accounttypemaster b  where matdt<= '" + transDt + "' and a.accstatus<>15 and acctnature = '" + acNature + "' and a.accttype = b.acctcode "
                                + "and status = 'A' and v.acno = a.acno Group by a.acno,v.voucherno order by v.matdt,a.acno,v.voucherno").getResultList();
                    } else {
                        datalist = em.createNativeQuery("select  a.acno,v.voucherno,upper(a.custname),v.prinamt,date_format(v.fddt,'%d/%m/%Y'),date_format(v.matdt,'%d/%m/%Y'),substring(a.Acno, 3, 2) ,v.receiptNo "
                                + "from td_vouchmst v,td_accountmaster a  where matdt<= '" + transDt + "' and a.accttype = '" + acType + "' and a.accstatus<>15 "
                                + "and status = 'A' and v.acno = a.acno Group by a.acno,v.voucherno order by v.matdt,a.acno,v.voucherno").getResultList();
                    }
                } else {
                    if (acType.equalsIgnoreCase("ALL")) {
                        datalist = em.createNativeQuery("select  a.acno,v.voucherno,upper(a.custname),v.prinamt,date_format(v.fddt,'%d/%m/%Y'),date_format(v.matdt,'%d/%m/%Y'),substring(a.Acno, 3, 2) ,v.receiptNo "
                                + "from td_vouchmst v,td_accountmaster a,accounttypemaster b  where a.curbrcode = '" + brCode + "' and  matdt<= '" + transDt + "' and a.accstatus<>15 and acctnature = '" + acNature + "' and a.accttype = b.acctcode "
                                + "and status = 'A' and v.acno = a.acno Group by a.acno,v.voucherno order by a.acno,v.voucherno").getResultList();
                    } else {
                        datalist = em.createNativeQuery("select  a.acno,v.voucherno,upper(a.custname),v.prinamt,date_format(v.fddt,'%d/%m/%Y'),date_format(v.matdt,'%d/%m/%Y'),substring(a.Acno, 3, 2) ,v.receiptNo "
                                + "from td_vouchmst v,td_accountmaster a  where a.curbrcode = '" + brCode + "' and  matdt<= '" + transDt + "' and a.accttype = '" + acType + "' and a.accstatus<>15 "
                                + "and status = 'A' and v.acno = a.acno Group by a.acno,v.voucherno order by a.acno,v.voucherno").getResultList();
                    }
                }
                if (!datalist.isEmpty()) {
                    for (int i = 0; i < datalist.size(); i++) {
                        Vector elt4 = (Vector) datalist.get(i);
                        String acNo = elt4.get(0).toString();
                        String voucherno = elt4.get(1).toString();
                        String customername = elt4.get(2).toString();
                        String prinamt = elt4.get(3).toString();
                        String fdDate = elt4.get(4).toString();
                        String matdt = elt4.get(5).toString();
                        String AcType = elt4.get(6).toString();
                        String recieptNo = elt4.get(7).toString();
                        intrest = 0.0;
                        intrestlist = em.createNativeQuery("select ifnull(sum(Interest),0) from td_interesthistory where Acno='" + acNo + "' and VoucherNo = " + voucherno + " and DT>='" + ymdFormat.format(dmyFormat.parse(matdt)) + "'").getResultList();
                        if (!intrestlist.isEmpty()) {
                            Vector vtr = (Vector) intrestlist.get(0);
                            intrest = Double.parseDouble(vtr.get(0).toString());
                        }
                        overDueObject = new OverdueNonEmiResultList();
                        overDueObject.setAcNo(acNo);
                        overDueObject.setVoucherno(voucherno);
                        overDueObject.setCustomername(customername);
                        overDueObject.setPrinamt(new BigDecimal(prinamt));
                        overDueObject.setFdDate(fdDate);
                        overDueObject.setMatdt(matdt);
                        overDueObject.setAcType(AcType);
                        overDueObject.setRecieptNo(recieptNo);
                        overDueObject.setIntrest(intrest);

                        overDueResultLst.add(overDueObject);
                    }
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                double intrest = 0.0;
                List intrestlist = new ArrayList();
                if (brCode.equalsIgnoreCase("0A")) {
                    if (acType.equalsIgnoreCase("All")) {
                        datalist = em.createNativeQuery("select a.ACNo,custname,date_format(OpeningDt,'%d/%m/%Y'),date_format(Rdmatdate,'%d/%m/%Y'),RdInstal,accstatus "
                                + "from accountmaster a,rdrecon r where a.acno = r.acno and Rdmatdate<='" + transDt + "' and accstatus = 1 group by acno").getResultList();
                    } else {
                        datalist = em.createNativeQuery("select a.ACNo,custname,date_format(OpeningDt,'%d/%m/%Y'),date_format(Rdmatdate,'%d/%m/%Y'),RdInstal,accstatus "
                                + "from accountmaster a,rdrecon r where a.acno = r.acno and Rdmatdate<='" + transDt + "' and Accttype='" + acType + "' and accstatus = 1 group by acno").getResultList();
                    }
                } else {
                    if (acType.equalsIgnoreCase("All")) {
                        datalist = em.createNativeQuery("select a.ACNo,custname,date_format(OpeningDt,'%d/%m/%Y'),date_format(Rdmatdate,'%d/%m/%Y'),RdInstal,accstatus "
                                + "from accountmaster a,rdrecon r where curbrcode = '" + brCode + "' and a.acno = r.acno and Rdmatdate<='" + transDt + "' and accstatus = 1 group by acno").getResultList();
                    } else {
                        datalist = em.createNativeQuery("select a.ACNo,custname,date_format(OpeningDt,'%d/%m/%Y'),date_format(Rdmatdate,'%d/%m/%Y'),RdInstal,accstatus "
                                + "from accountmaster a,rdrecon r where curbrcode = '" + brCode + "' and a.acno = r.acno and Rdmatdate<='" + transDt + "' and Accttype='" + acType + "' and accstatus = 1 group by acno").getResultList();
                    }
                }
                if (!datalist.isEmpty()) {
                    for (int i = 0; i < datalist.size(); i++) {
                        Vector elt4 = (Vector) datalist.get(i);
                        String acNo = elt4.get(0).toString();
                        String customername = elt4.get(1).toString();
                        String OpeningDt = elt4.get(2).toString();
                        String RdmaturityDt = elt4.get(3).toString();
                        String RdInstallment = elt4.get(4).toString();

                        String rdMat = ymdFormat.format(dmyFormat.parse(RdmaturityDt));
                        double bal = common.getBalanceOnDate(acNo, ymdFormat.format(dmyFormat.parse(RdmaturityDt)));

                        String sbIntTabCode = "";
                        double TmpTdSIntTot = 0;
                        List setIntTableCode = em.createNativeQuery("SELECT ifnull(Code,'') FROM cbs_parameterinfo where name ='SAVING_INTTABLE_CODE'").getResultList();
                        if (setIntTableCode.size() > 0) {
                            Vector intTabCodeVec = (Vector) setIntTableCode.get(0);
                            sbIntTabCode = intTabCodeVec.get(0).toString();
                        }

                        List<SavingIntRateChangePojo> dataList = sbRemote.getSavingRoiChangeDetail(sbIntTabCode, rdMat, ymdFormat.format(new Date()));
                        if (dataList.isEmpty()) {
                            throw new ApplicationException("There is no slab for saving interest calculation.");
                        }
                        for (int k = 0; k < dataList.size(); k++) {
                            double rateOfInt = 0;
                            Long dDiff = null;
                            SavingIntRateChangePojo obj = dataList.get(k);
                            String slabFrDt = obj.getFrDt();
                            String slabToDt = obj.getToDt();
                            rateOfInt = obj.getRoi();
                            dDiff = CbsUtil.dayDiff(ymdFormat.parse(slabFrDt), ymdFormat.parse(slabToDt));
                            double sInt = (bal * rateOfInt * dDiff / 36500);
                            //fnlTmpInt = fnlTmpInt + sInt;
                            TmpTdSIntTot = TmpTdSIntTot + sInt;
                        }
//                        intrest = 0.0;
//                        intrestlist = em.createNativeQuery("select ifnull(sum(Interest),0) from rd_interesthistory where Acno='042500176701' and DT>='" + ymdFormat.format(dmyFormat.parse(RdmaturityDt)) + "'").getResultList();
//                        if (!intrestlist.isEmpty()) {
//                            Vector vtr = (Vector) intrestlist.get(0);
//                            intrest = Double.parseDouble(vtr.get(0).toString());
//                        }
                        overDueObject = new OverdueNonEmiResultList();
                        overDueObject.setAcNo(acNo);
                        overDueObject.setCustomername(customername);
                        overDueObject.setRdDate(OpeningDt);
                        overDueObject.setMatdt(RdmaturityDt);
                        overDueObject.setInstallment(RdInstallment);
                        //overDueObject.setIntrest(intrest);
                        overDueObject.setBalanceOnMatDt(bal);
                        overDueObject.setIntrest(CbsUtil.round(TmpTdSIntTot, 0));
                        overDueResultLst.add(overDueObject);
                    }
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {

                if (brCode.equalsIgnoreCase("0A")) {
                    if (acType.equalsIgnoreCase("All")) {
                        datalist = em.createNativeQuery("select a.ACNo,custname,date_format(OpeningDt,'%d/%m/%Y'),date_format(Rdmatdate,'%d/%m/%Y'),RdInstal,accstatus \n"
                                + "from accountmaster a,ddstransaction r where a.acno = r.acno and Rdmatdate<='" + transDt + "' and RdInstal is not null\n"
                                + "and accstatus = 1 group by acno").getResultList();
                    } else {
                        datalist = em.createNativeQuery("select a.ACNo,custname,date_format(OpeningDt,'%d/%m/%Y'),date_format(Rdmatdate,'%d/%m/%Y'),RdInstal,accstatus \n"
                                + "from accountmaster a,ddstransaction r where a.acno = r.acno and Rdmatdate<='" + transDt + "' and RdInstal is not null\n"
                                + "and accstatus = 1 and Accttype = '" + acType + "' group by acno").getResultList();
                    }
                } else {
                    if (acType.equalsIgnoreCase("All")) {
                        datalist = em.createNativeQuery("select a.ACNo,custname,date_format(OpeningDt,'%d/%m/%Y'),date_format(Rdmatdate,'%d/%m/%Y'),RdInstal,accstatus \n"
                                + "from accountmaster a,ddstransaction r where curbrcode = '" + brCode + "' and a.acno = r.acno and Rdmatdate<='" + transDt + "' and RdInstal is not null\n"
                                + "and accstatus = 1 group by acno").getResultList();
                    } else {
                        datalist = em.createNativeQuery("select a.ACNo,custname,date_format(OpeningDt,'%d/%m/%Y'),date_format(Rdmatdate,'%d/%m/%Y'),RdInstal,accstatus \n"
                                + "from accountmaster a,ddstransaction r where curbrcode = '" + brCode + "' and a.acno = r.acno and Rdmatdate<='" + transDt + "' and RdInstal is not null\n"
                                + "and accstatus = 1 and Accttype = '" + acType + "' group by acno").getResultList();
                    }
                }
                if (!datalist.isEmpty()) {
                    for (int i = 0; i < datalist.size(); i++) {
                        Vector elt4 = (Vector) datalist.get(i);
                        String acNo = elt4.get(0).toString();
                        String customername = elt4.get(1).toString();
                        String OpeningDt = elt4.get(2).toString();
                        String ddsmaturityDt = elt4.get(3).toString();
                        String ddsInstallment = elt4.get(4).toString();

                        String ddsMat = ymdFormat.format(dmyFormat.parse(ddsmaturityDt));
                        double bal = common.getBalanceOnDate(acNo, ymdFormat.format(dmyFormat.parse(ddsmaturityDt)));

                        String sbIntTabCode = "";
                        double TmpTdSIntTot = 0;
                        List setIntTableCode = em.createNativeQuery("SELECT ifnull(Code,'') FROM cbs_parameterinfo where name ='SAVING_INTTABLE_CODE'").getResultList();
                        if (setIntTableCode.size() > 0) {
                            Vector intTabCodeVec = (Vector) setIntTableCode.get(0);
                            sbIntTabCode = intTabCodeVec.get(0).toString();
                        }

                        List<SavingIntRateChangePojo> dataList = sbRemote.getSavingRoiChangeDetail(sbIntTabCode, ddsMat, ymdFormat.format(new Date()));
                        if (dataList.isEmpty()) {
                            throw new ApplicationException("There is no slab for saving interest calculation.");
                        }
                        for (int k = 0; k < dataList.size(); k++) {
                            double rateOfInt = 0;
                            Long dDiff = null;
                            SavingIntRateChangePojo obj = dataList.get(k);
                            String slabFrDt = obj.getFrDt();
                            String slabToDt = obj.getToDt();
                            rateOfInt = obj.getRoi();
                            dDiff = CbsUtil.dayDiff(ymdFormat.parse(slabFrDt), ymdFormat.parse(slabToDt));
                            double sInt = (bal * rateOfInt * dDiff / 36500);
                            //fnlTmpInt = fnlTmpInt + sInt;
                            TmpTdSIntTot = TmpTdSIntTot + sInt;
                        }
                        overDueObject = new OverdueNonEmiResultList();
                        overDueObject.setAcNo(acNo);
                        overDueObject.setCustomername(customername);
                        overDueObject.setRdDate(OpeningDt);
                        overDueObject.setMatdt(ddsmaturityDt);
                        overDueObject.setInstallment(ddsInstallment);
                        //overDueObject.setIntrest(intrest);
                        overDueObject.setBalanceOnMatDt(bal);
                        overDueObject.setIntrest(CbsUtil.round(TmpTdSIntTot, 0));
                        overDueResultLst.add(overDueObject);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return overDueResultLst;
    }

    public List getDistinctAcCodeByAcNature() throws ApplicationException {
        List tableResult = null;
        try {
            tableResult = em.createNativeQuery("select distinct(acctcode) from accounttypemaster where acctnature in ('FD','RD') And CrDbFlag in('B','D','C') order by acctCode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableResult;
    }

    public List getAcNaturetldlcaList() throws ApplicationException {
        List list = null;
        try {
            list = em.createNativeQuery("select distinct(acctNature) from accounttypemaster where acctNature in('" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "') order by acctNature").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list;
    }

    @Override
    public List<RbiInspectionPojo> rbiInspectionData(String brnCode, String acNatue, String acType, String asOnDt) throws ApplicationException {
        List<RbiInspectionPojo> finalList = new ArrayList<RbiInspectionPojo>();
        try {
            String brnQuery = "";
            if (brnCode.equalsIgnoreCase("0A") || brnCode.equalsIgnoreCase("90")) {
                brnQuery = "";
            } else {
                brnQuery = " and substring(a.acno,1,2)= '" + brnCode + "'";
            }
            String acCodeQuery = "";
            if (acNatue.equalsIgnoreCase("ALL")) {
                acCodeQuery = " and  b.acctNature in ('CA','DL','TL') ";
            } else {
                if (acType.equalsIgnoreCase("All")) {
                    acCodeQuery = " and  b.acctNature in ('" + acNatue + "') ";
                } else {
                    acCodeQuery = " and  b.acctNature in ('" + acNatue + "') and substring(a.acno,3,2) ='" + acType + "' ";
                }
            }
            String acCode = fts.getValueFromCbsparameterInfo("THRIFT-BAL-CODE");
//            if (acType.equalsIgnoreCase("All")) {
//                acCodeQuery = " substring(a.acno,3,2) in (select Acctcode from accounttypemaster where acctnature ='" + acNatue + "') ";
//            } else {
//                acCodeQuery = " substring(a.acno,3,2) ='" + acType + "' ";
//            }

            List accountList = null;
//            if (acNatue.equalsIgnoreCase("All")) {
            accountList = em.createNativeQuery("SELECT aa.acctNature, aa.AcctCode, aa.AcctDesc, aa.acno,ifnull(aa.custname,''),ifnull(aa.openingdt,''),ifnull(aa.Sanctionlimit,''), ifnull(aa.sancDt,''), ifnull(aa.closingdate,''),ifnull(aa.odlimit,''), aa.RENEWAL_DATE,"
                    + " ifnull(aa.bal,''),ifnull(aa.npaBal,0), ifnull(aa.pre1Quarter,''),ifnull(aa.pre1QuarBal,0),ifnull(aa.pre1QuarNpaBal,0),ifnull(aa.pre2Quarter,''),ifnull(aa.pre2QuarBal,0), ifnull(aa.pre2QuarNpaBal,0), aa.nextQuarter,ifnull(aa.nextQuarBal,0),ifnull(aa.nextQuarNpaBal,0),"
                    + " ifnull(bb.noOfDueEmi,'0'),ifnull(bb.minDueDt,'') as EmiStartDt,ifnull(bb.emiDueAmt,0),"
                    + "ifnull(cc.noOfPaidEmi,0),ifnull(emiPaidAmt,0), ifnull(dd.security,'') as security, ifnull(dd.secAmt,0) as TotalSecurityAmt, IFNULL(cbk.description,'OPERATIVE' ) as presentstatus,"
                    + "aa.CUST_ID ,ifnull(sha.foliono,''),ifnull(sha.shareBal,0),ifnull(sha.thriftBalance,0),ifnull(sha.menberDt,'') "
                    + "from "
                    + "(SELECT b.acctNature, b.AcctCode, b.AcctDesc, a.acno, a.custname, a.openingdt, cast(c.Sanctionlimit as decimal(25,2)) as Sanctionlimit,"
                    + "date_format(c.Sanctionlimitdt,'%d/%m/%Y') as sancDt, a.closingdate, cast(c.odlimit as decimal(25,2)) as odlimit, date_format(d.RENEWAL_DATE,'%d/%m/%Y') as RENEWAL_DATE,"
                    + "if(b.acctNature = '" + CbsConstant.CURRENT_AC + "',(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from ca_recon where acno = a.acno and dt<='" + asOnDt + "'),"
                    + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from loan_recon where acno = a.acno and dt<='" + asOnDt + "')) as bal,"
                    + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from npa_recon where acno = a.acno and dt<='" + asOnDt + "') as npaBal,"
                    + "(select date_add(last_day(concat(year('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'),'-',ceil(month('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "')/3)*3,'-',day('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'))),interval -3 month)) as pre1Quarter,"
                    + "if(b.acctNature = 'CA',"
                    + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from ca_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'),'-',ceil(month('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "')/3)*3,'-',day('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'))),interval -3 month))),"
                    + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from loan_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'),'-',ceil(month('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "')/3)*3,'-',day('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'))),interval -3 month)))) as pre1QuarBal,"
                    + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from npa_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'),'-',ceil(month('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "')/3)*3,'-',day('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'))),interval -3 month))) as pre1QuarNpaBal,"
                    + "(select date_add(last_day(concat(year('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'),'-',ceil(month('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "')/3)*3,'-',day('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'))),interval -6 month)) as pre2Quarter,"
                    + "if(b.acctNature = 'CA',"
                    + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from ca_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'),'-',ceil(month('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "')/3)*3,'-',day('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'))),interval -6 month))),"
                    + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from loan_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'),'-',ceil(month('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "')/3)*3,'-',day('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'))),interval -6 month)))) as pre2QuarBal,"
                    + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from npa_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'),'-',ceil(month('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "')/3)*3,'-',day('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'))),interval -6 month))) as pre2QuarNpaBal,"
                    + "(select date_add(last_day(concat(year('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'),'-',ceil(month('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "')/3)*3,'-',day('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'))),interval 3 month)) as nextQuarter,"
                    + "if(b.acctNature = 'CA',"
                    + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from ca_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'),'-',ceil(month('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "')/3)*3,'-',day('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'))),interval 3 month))),"
                    + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from loan_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'),'-',ceil(month('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "')/3)*3,'-',day('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'))),interval 3 month)))) as nextQuarBal,"
                    + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from npa_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'),'-',ceil(month('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "')/3)*3,'-',day('" + y_m_dFormat.format(ymdFormat.parse(asOnDt)) + "'))),interval 3 month))) as nextQuarNpaBal,d.CUST_ID "
                    + "FROM  accountmaster a, loan_appparameter c, accounttypemaster b, cbs_loan_borrower_details d where a.acno =c.acno and b.AcctCode = substring(a.acno,3,2)  "
                    + "and a.ACNo = d.ACC_NO " + acCodeQuery + " and b.CrDbFlag in ('D','B')  "
                    + "and (a.closingdate is null or a.closingdate ='' or a.closingdate >='" + asOnDt + "')  )aa "
                    + "left join "
                    + "(select acno, count(*) as noOfDueEmi,date_format(min(duedt),'%d/%m/%Y') as minDueDt ,sum(INSTALLAMT) as emiDueAmt from emidetails where  DUEDT <='" + asOnDt + "' group by acno )bb on aa.acno = bb.acno "
                    + "left join "
                    + "(select acno,count(*) as noOfPaidEmi,sum(INSTALLAMT) as emiPaidAmt from emidetails where PAYMENTDT <='" + asOnDt + "' and status = 'PAID' group by acno)cc on aa.acno = cc.acno "
                    + "left join "
                    + "(select zz.acno, group_concat(zz.security) as security, sum(zz.secAmt) as secAmt from "
                    + "(select acno,"
                    + "concat(SecurityOption, \": Rs.\",(if(sum(lienvalue)=0,cast(sum(matvalue) as decimal(25,2)),cast(sum(lienvalue) as decimal(25,2))))) as security,"
                    + "(if(sum(lienvalue)=0,cast(sum(matvalue) as decimal(25,2)),cast(sum(lienvalue) as decimal(25,2)))) as secAmt from loansecurity "
                    + "where  (ExpiryDate is null or ExpiryDate ='' or ExpiryDate >='" + asOnDt + "') "
                    + "and Entrydate<='" + asOnDt + "' group by acno, SecurityOption ) zz group by zz.acno) dd on aa.acno = dd.acno "
                    + "left join "
                    + "(select ast.acno as acno,ast.effdt as npaEffDt, cast(ast.spflag as decimal) as npaSpflag  from accountstatus ast, "
                    + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                    + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  <='" + asOnDt + "'  group by acno) b "
                    + " where aa.acno = b.ano and aa.effdt = b.dt "
                    + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno) npa "
                    + " on npa.acno = aa.acno "
                    + "left join "
                    + " (select aa.foliono,aa.customerid,aa.shareBal,ifnull(bb.thriftbal,0) as thriftBalance,ifnull(bb.acno,'') as accountNo,date_format(aa.Regdate,'%d/%m/%Y') as menberDt from "
                    + "(select a.foliono,a.customerid,sum(a.shval) as shareBal,Regdate from "
                    + "(select distinct foliono,cc.customerid,sc.sharecertno,count(shareno)* "
                    + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,sh.Regdate "
                    + "from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc "
                    + "where cs.issueDt<='" + asOnDt + "' and sh.custid = cc.customerid and sc.foliono=sh.regfoliono "
                    + "and cs.certificateno=sc.sharecertno and (cs.paymentdt is null or cs.paymentdt ='' or cs.paymentdt > '" + asOnDt + "') "
                    + "group by foliono,sc.sharecertno order by foliono) a group by a.foliono)aa "
                    + "left join "
                    + "(select b.custid,a.acno,cast(sum(cramt-dramt) as decimal(25,2)) as thriftbal from recon a,customerid b "
                    + "where substring(a.acno,3,2) in(" + acCode + ") and a.acno = b.acno and dt<='" + asOnDt + "' group by a.acno)bb on cast(aa.customerid as unsigned) = bb.custid)sha "
                    + "on aa.CUST_ID = sha.customerid"
                    + " left join "
                    + " codebook cbk on npa.npaSpflag = cbk.code and cbk.groupcode = 3  "
                    + " order by aa.acctNature, aa.AcctCode, aa.AcctDesc, aa.acno").getResultList();

//                  } else {
//                
//                accountList = em.createNativeQuery("SELECT aa.acctNature, aa.AcctCode, aa.AcctDesc, aa.acno, aa.custname, aa.openingdt, aa.Sanctionlimit, aa.sancDt, aa.closingdate, aa.odlimit, aa.RENEWAL_DATE,"
//                        + " aa.bal, aa.npaBal, aa.pre1Quarter, aa.pre1QuarBal,  aa.pre1QuarNpaBal, aa.pre2Quarter, aa.pre2QuarBal,  aa.pre2QuarNpaBal, aa.nextQuarter, aa.nextQuarBal,  aa.nextQuarNpaBal,"
//                        + " bb.noOfDueEmi,bb.minDueDt as EmiStartDt,bb.emiDueAmt,"
//                        + "cc.noOfPaidEmi,emiPaidAmt, ifnull(dd.security,'') as security, ifnull(dd.secAmt,0) as TotalSecurityAmt "
//                        + "from "
//                        + "(SELECT b.acctNature, b.AcctCode, b.AcctDesc, a.acno, a.custname, a.openingdt, cast(c.Sanctionlimit as decimal(25,2)) as Sanctionlimit,"
//                        + "date_format(c.Sanctionlimitdt,'%d/%m/%Y') as sancDt, a.closingdate, cast(c.odlimit as decimal(25,2)) as odlimit, date_format(d.RENEWAL_DATE,'%d/%m/%Y') as RENEWAL_DATE,"
//                        + "if(b.acctNature = 'CA',(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from ca_recon where acno = a.acno and dt<='"+ asOnDt +"'),"
//                        + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from loan_recon where acno = a.acno and dt<='"+ asOnDt +"')) as bal,"
//                        + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from npa_recon where acno = a.acno and dt<='"+ asOnDt +"') as npaBal,"
//                        + "(select date_add(last_day(concat(year('"+y_m_dFormat.format(ymdFormat.parse(asOnDt))+"'),'-',ceil(month('"+y_m_dFormat.format(ymdFormat.parse(asOnDt))+"')/3)*3,'-',day('"+y_m_dFormat.format(ymdFormat.parse(asOnDt))+"'))),interval -3 month)) as pre1Quarter,"
//                        + "if(b.acctNature = 'CA',"
//                        + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from ca_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('"+y_m_dFormat.format(ymdFormat.parse(asOnDt))+"'),'-',ceil(month('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"')/3)*3,'-',day('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'))),interval -3 month))),"
//                        + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from loan_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'),'-',ceil(month('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"')/3)*3,'-',day('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'))),interval -3 month)))) as pre1QuarBal,"
//                        + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from npa_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'),'-',ceil(month('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"')/3)*3,'-',day('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'))),interval -3 month))) as pre1QuarNpaBal,"
//                        + "(select date_add(last_day(concat(year('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'),'-',ceil(month('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"')/3)*3,'-',day('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'))),interval -6 month)) as pre2Quarter,"
//                        + "if(b.acctNature = 'CA',"
//                        + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from ca_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'),'-',ceil(month('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"')/3)*3,'-',day('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'))),interval -6 month))),"
//                        + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from loan_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'),'-',ceil(month('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"')/3)*3,'-',day('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'))),interval -6 month)))) as pre2QuarBal,"
//                        + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from npa_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'),'-',ceil(month('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"')/3)*3,'-',day('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'))),interval -6 month))) as pre2QuarNpaBal,"
//                        + "(select date_add(last_day(concat(year('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'),'-',ceil(month('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"')/3)*3,'-',day('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'))),interval 3 month)) as nextQuarter,"
//                        + "if(b.acctNature = 'CA',"
//                        + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from ca_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'),'-',ceil(month('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"')/3)*3,'-',day('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'))),interval 3 month))),"
//                        + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from loan_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'),'-',ceil(month('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"')/3)*3,'-',day('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'))),interval 3 month)))) as nextQuarBal,"
//                        + "(select ifnull(cast(sum(cramt-dramt) as decimal(15,2)),0) from npa_recon where acno = a.acno and dt<=(select date_add(last_day(concat(year('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'),'-',ceil(month('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"')/3)*3,'-',day('"+ y_m_dFormat.format(ymdFormat.parse(asOnDt)) +"'))),interval 3 month))) as nextQuarNpaBal "
//                        + "FROM  accountmaster a, loan_appparameter c, accounttypemaster b, cbs_loan_borrower_details d where substring(a.acno,3,2) = b.AcctCode and a.acno =c.acno "
//                        + "and a.ACNo = d.ACC_NO and  b.acctNature ='"+ acNatue +"' and b.CrDbFlag in ('D','B') "
//                        + "and (a.closingdate is null or a.closingdate ='' or a.closingdate >='"+asOnDt+"')  )aa "
//                        + "left join "
//                        + "(select acno, count(*) as noOfDueEmi,date_format(min(duedt),'%d/%m/%Y') as minDueDt ,sum(INSTALLAMT) as emiDueAmt from emidetails where  DUEDT <='"+asOnDt+"' group by acno )bb on aa.acno = bb.acno "
//                        + "left join "
//                        + "(select acno,count(*) as noOfPaidEmi,sum(INSTALLAMT) as emiPaidAmt from emidetails where PAYMENTDT <='"+asOnDt+"' and status = 'PAID' group by acno)cc on aa.acno = cc.acno "
//                        + "left join "
//                        + "(select zz.acno, group_concat(zz.security) as security, sum(zz.secAmt) as secAmt from "
//                        + "(select acno,"
//                        + "concat(SecurityOption, \": Rs.\",(if(sum(lienvalue)=0,cast(sum(matvalue) as decimal(25,2)),cast(sum(lienvalue) as decimal(25,2))))) as security,"
//                        + "(if(sum(lienvalue)=0,cast(sum(matvalue) as decimal(25,2)),cast(sum(lienvalue) as decimal(25,2)))) as secAmt from loansecurity "
//                        + "where  (ExpiryDate is null or ExpiryDate ='' or ExpiryDate >='"+asOnDt+"') "
//                        + "and Entrydate<='"+asOnDt+"' group by acno, SecurityOption ) zz group by zz.acno) dd on aa.acno = dd.acno "
//                        + "order by aa.acctNature, aa.AcctCode, aa.AcctDesc, aa.acno").getResultList();
//            }
            int j = 1;
            if (!accountList.isEmpty()) {
                for (int i = 0; i < accountList.size(); i++) {

                    Vector vtr = (Vector) accountList.get(i);
                    RbiInspectionPojo pojo = new RbiInspectionPojo();
                    pojo.setSrno(j++);
                    pojo.setAcNo(vtr.get(3).toString());
                    pojo.setName(vtr.get(4).toString());
                    pojo.setSancDt(vtr.get(7).toString());
                    pojo.setLoanType(vtr.get(2).toString());
                    pojo.setSancAmt(new BigDecimal(vtr.get(9).toString()));
                    pojo.setDisbAmt(new BigDecimal(vtr.get(9).toString()));
                    pojo.setDisbDt(vtr.get(10).toString());
                    if (!(vtr.get(0).toString().equalsIgnoreCase(CbsConstant.CURRENT_AC) || vtr.get(0).toString().equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                        pojo.setEmiAmt(new BigDecimal(vtr.get(11).toString()));
                        pojo.setEmiStrtDt(vtr.get(23).toString());
                        pojo.setTotEmiPaid(new BigDecimal(vtr.get(26).toString()));
                        pojo.setNoOfEmiPaid(vtr.get(25).toString());
                        pojo.setDueEmi(new BigDecimal(vtr.get(24).toString()));
                        pojo.setNoOfemiDue(vtr.get(22).toString());
                    } else {
                        List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, acLimit from loan_oldinterest "
                                + "where acno =  '" + vtr.get(3).toString() + "' and txnid = "
                                + "(select min(TXNID) from loan_oldinterest where acno =  '" + vtr.get(3).toString() + "' and enterdate>'" + asOnDt + "' )").getResultList();
                        if (!sanctionLimitDtList.isEmpty()) {
                            Vector vist = (Vector) sanctionLimitDtList.get(0);
//                                odSanctionChangeDt = vist.get(0).toString();
//                                odLimit = Double.parseDouble(vist.get(1).toString());
                            pojo.setSancDt(vist.get(0).toString());
                            pojo.setLoanType(vtr.get(2).toString());
                            pojo.setSancAmt(new BigDecimal(vist.get(1).toString()));
                            pojo.setDisbAmt(new BigDecimal(vist.get(1).toString()));
                        }
                    }
                    pojo.setOutsAmt1(new BigDecimal(vtr.get(11).toString()));
                    pojo.setOutsAmt2(new BigDecimal(vtr.get(14).toString()));
                    pojo.setOutsAmt3(new BigDecimal(vtr.get(17).toString()));
                    pojo.setOutsAmt4(new BigDecimal(vtr.get(20).toString()));
                    pojo.setSecurity(vtr.get(27).toString());
                    pojo.setTotalSecurityValue(new BigDecimal(vtr.get(28).toString()));
                    pojo.setAccountStatus(vtr.get(29).toString());
                    pojo.setLastDateReview(vtr.get(10).toString());
                    pojo.setDp("");
                    pojo.setCustId(vtr.get(30).toString());
                    pojo.setFolioNo(vtr.get(31).toString());
                    pojo.setShareBal(new BigDecimal(vtr.get(32).toString()));
                    pojo.setThriftBal(new BigDecimal(vtr.get(33).toString()));
                    pojo.setMembershipDate(vtr.get(34).toString());


                    finalList.add(pojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
        return finalList;
    }

    @Override
    public List<AccountStatementReportPojo> getAccountStatementRecieptWiseList(String acNo, String frDt, String toDt, String status, String voucherNo) throws ApplicationException {
        List<AccountStatementReportPojo> returnList = new ArrayList<AccountStatementReportPojo>();
        //  String custName = "", acctDesc = "";
        String date = "", particulars = "", chequeno = "", acNat, valdt = "", custName = "", acctDesc = "", jtName1 = null, jtName2 = null, crAdd = null, prAdd = null, jtName3 = null, jtName4 = null, nomName = null, tableName = "", gstn = "";
        double withdrawl = 0, deposit = 0, balance = 0, pendBal;
        double openBal = 0, receiptWiseBal = 0, balance1 = 0, bal = 0;
        try {

            Calendar calFromDt = Calendar.getInstance();
            calFromDt.setTime(ymdFormat.parse(frDt));
            calFromDt.add(Calendar.DATE, -1);
            String dtB = ymdFormat.format(calFromDt.getTime());
            openBal = common.getBalanceOnDate(acNo, dtB);
            List tempList = null;
            tempList = em.createNativeQuery("Select a.custname, b.acctdesc, a.jtname1, a.jtname2, c.craddress, c.praddress,a.jtname3,a.jtname4,a.nomination"
                    + " from td_accountmaster a, accounttypemaster b,td_customermaster c where a.acno='" + acNo + "' and substring(a.acno,3,2)=b.acctcode and "
                    + "substring(a.acno,5,6)=c.custno and c.brncode = a.curbrcode and substring(a.acno,3,2)=c.actype").getResultList();
            if (tempList.isEmpty()) {
                return null;
            } else {
                Vector ele = (Vector) tempList.get(0);
                custName = ele.get(0).toString();
                acctDesc = ele.get(1).toString();
            }
            List custList = em.createNativeQuery(" select ifnull(name,'')  from acedithistory where acno = '" + acNo + "' "
                    + " and txnid=(select min(txnid) from acedithistory where acno = '" + acNo + "'  and date_format(UpdateDt,'%Y%m%d') >'" + toDt + "')").getResultList();
            if (!custList.isEmpty()) {
                Vector custVect = (Vector) custList.get(0);
                custName = !custVect.get(0).toString().equalsIgnoreCase("") ? custVect.get(0).toString() : "";
            }
            double avibleBal = 0;
            List list = null;
            if (status.equalsIgnoreCase("ALL")) {
                if (voucherNo.equalsIgnoreCase("ALL")) {
                    list = em.createNativeQuery("select distinct cast(VoucherNo as unsigned),PrinAmt,ifnull(IntToAcno,''),status from td_vouchmst where acno = '" + acNo + "' and FDDT<='" + toDt + "'").getResultList();
                } else {
                    list = em.createNativeQuery("select distinct cast(VoucherNo as unsigned),PrinAmt,ifnull(IntToAcno,''),status from td_vouchmst where acno = '" + acNo + "' and FDDT<='" + toDt + "' and VoucherNo= " + voucherNo + "").getResultList();
                }
            } else if (status.equalsIgnoreCase("A")) {
                if (voucherNo.equalsIgnoreCase("ALL")) {
                    list = em.createNativeQuery("select distinct cast(VoucherNo as unsigned),PrinAmt,ifnull(IntToAcno,''),status from td_vouchmst where acno = '" + acNo + "' and FDDT<='" + toDt + "' and (ClDt is null OR ClDt >='" + toDt + "') ").getResultList();
                } else {
                    list = em.createNativeQuery("select distinct cast(VoucherNo as unsigned),PrinAmt,ifnull(IntToAcno,''),status from td_vouchmst where acno = '" + acNo + "' and FDDT<='" + toDt + "' and (ClDt is null OR ClDt >='" + toDt + "') and VoucherNo= " + voucherNo + "").getResultList();
                }
            } else if (status.equalsIgnoreCase("C")) {
                if (voucherNo.equalsIgnoreCase("ALL")) {
                    list = em.createNativeQuery("select distinct cast(VoucherNo as unsigned),PrinAmt,ifnull(IntToAcno,''),status from td_vouchmst where acno = '" + acNo + "' and FDDT<='" + toDt + "' and  ClDt <='" + toDt + "' order by cldt ").getResultList();
                } else {
                    list = em.createNativeQuery("select distinct cast(VoucherNo as unsigned),PrinAmt,ifnull(IntToAcno,''),status from td_vouchmst where acno = '" + acNo + "' and FDDT<='" + toDt + "' and  ClDt <='" + toDt + "' and VoucherNo= " + voucherNo + " order by cldt").getResultList();
                }
            }

            if (!list.isEmpty()) {
                double vouchOpBal = 0;
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    String vouchNo = vtr.get(0).toString();
                    double prinAmt = Double.parseDouble(vtr.get(1).toString());
                    String intToAcno = vtr.get(2).toString();
                    String acStatus = vtr.get(3).toString();
                    String Query = "";
                    if (!status.equalsIgnoreCase("C")) {
                        Query = "and (ClDt is null OR ClDt >='" + toDt + "')";
                    }
                    List voucOpBalList = em.createNativeQuery("select prinAmt,intAmt,tdsAmt,(prinAmt+intAmt)-tdsAmt vouchOpBal from "
                            + "(select ifnull(PrinAmt,0) prinAmt from td_vouchmst where acno = '" + acNo + "' and VoucherNo= " + vouchNo + " /*and FDDT<='" + frDt + "'*/ " + Query + ")a,\n"
                            + "(select ifnull(sum(Interest),0) intAmt from td_interesthistory where acno = '" + acNo + "' and VoucherNo= " + vouchNo + " and dt<='" + dtB + "')b,\n"
                            + "(select ifnull(sum(tds),0) tdsAmt from tdshistory where acno = '" + acNo + "' and VoucherNo= " + vouchNo + " and dt<='" + dtB + "')c").getResultList();
                    if (!voucOpBalList.isEmpty()) {
                        Vector opVector = (Vector) voucOpBalList.get(0);
                        vouchOpBal = Double.parseDouble(opVector.get(3).toString());
                    }
                    String option = "ASR :" + vouchNo + ":" + "A";
                    List<AbbStatementPojo> abbStatement = common.getAbbStatement(acNo, frDt, toDt, option);
                    String receiptNo = "";
                    for (AbbStatementPojo obj : abbStatement) {
                        date = obj.getDate1();
                        particulars = obj.getParticulars();
                        if (particulars.contains("CHQ DT")) {
                            String[] strArr = particulars.split("CHQ DT");
                            particulars = strArr[0];
                        }
                        chequeno = obj.getChequeNo();
                        withdrawl = obj.getWithdrawl();
                        deposit = obj.getDeposit();
                        balance = obj.getBalance();
                        valdt = obj.getValueDate();
                        openBal = vouchOpBal;
                        if (withdrawl == 0 && deposit == 0) {  // For Closed Receipt balance 0 as per discus himant sir
                            openBal = 0;
                        }
                        if (acStatus.equalsIgnoreCase("C")) {
                            openBal = 0;
                        }
                        if (obj.getVoucherNo().equalsIgnoreCase(receiptNo)) {
                            receiptWiseBal = balance1 + deposit - withdrawl;
                            balance1 = receiptWiseBal;
                        } else {
                            receiptNo = obj.getVoucherNo();
                            receiptWiseBal = openBal + deposit - withdrawl;
                            balance1 = receiptWiseBal;
                        }
                        AccountStatementReportPojo pojo = new AccountStatementReportPojo();
                        if (!(withdrawl == 0 && deposit == 0)) {
                            // if (receiptWiseBal != 0 || status.equalsIgnoreCase("C")) {
                            pojo.setAcNo(acNo);
                            pojo.setAcType(acctDesc);
                            // pojo.setBalance(balance);
                            pojo.setBalance(receiptWiseBal);
                            pojo.setCustName(custName);
                            pojo.setDate(date);
                            pojo.setDeposit(deposit);
                            // pojo.setOpeningBal(openBal);
                            pojo.setOpeningBal(openBal);
                            pojo.setParticulars(particulars);
                            //pojo.setPendingBal(pendBal);
                            pojo.setValueDate(valdt);
                            pojo.setWithdrawl(withdrawl);
                            pojo.setGstrn(gstn);
                            pojo.setVoucherNo(obj.getVoucherNo());
                            returnList.add(pojo);
                        }
                    }
                    if (!returnList.isEmpty()) {
                        AccountStatementReportPojo p = returnList.get(returnList.size() - 1);
                        avibleBal = avibleBal + p.getBalance();
                    }
                }
            }
            AccountStatementReportPojo obj = new AccountStatementReportPojo();
            obj.setPendingBal(avibleBal);
            returnList.add(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    @Override
    public List getVoucherNoByAcNo(String acNo, String status, String toDt) throws ApplicationException {
        List list = null;

        if (status.equalsIgnoreCase("ALL")) {
            list = em.createNativeQuery("select distinct cast(VoucherNo as unsigned),PrinAmt from td_vouchmst where acno = '" + acNo + "'and FDDT<='" + toDt + "'").getResultList();
        } else if (status.equalsIgnoreCase("A")) {
            list = em.createNativeQuery("select distinct cast(VoucherNo as unsigned),PrinAmt from td_vouchmst where acno = '" + acNo + "' and FDDT<='" + toDt + "' and (ClDt is null OR ClDt >='" + toDt + "')").getResultList();
        } else if (status.equalsIgnoreCase("C")) {
            list = em.createNativeQuery("select distinct cast(VoucherNo as unsigned),PrinAmt from td_vouchmst where acno = '" + acNo + "' and FDDT<='" + toDt + "' and  ClDt <='" + toDt + "'").getResultList();
        }
        return list;
    }

    @Override
    public List<TdReceiptIntDetailPojo> getProjectedInterestTdsCalData(String brCode, String frDt, String toDt, double frAmt, double toAmt, String reportOption, String custId, String isSenior) throws ApplicationException {
        List<TdReceiptIntDetailPojo> dataList = new ArrayList<>();
        List<TdReceiptIntDetailPojo> dataList1 = new ArrayList<>();
        List result = new ArrayList();
        try {
            String customerId = "", panCustomerId = "";
            if (reportOption.equalsIgnoreCase("ALL Id")) {
                panCustomerId = "";
                customerId = "";
            } else {
                panCustomerId = "and b.guardianId = " + custId + "";
                customerId = "where guardianId = " + custId + "";
            }
            double dblBenifitAge = fts.getCodeForReportName("SR-CITIZEN-AGE").doubleValue();
            //For Get Pan Number
            Map<String, String> panMap = new HashMap<>();
            List panList = em.createNativeQuery("select cast(a.customerid as unsigned),a.PAN_GIRNumber from cbs_customer_master_detail a,( \n"
                    + "SELECT intopt,date_format(fddt,'%Y%m%d') FdDate,date_format(matdt,'%Y%m%d') matDate,roi,prinamt,\n"
                    + "date_format(NextIntPayDt,'%Y%m%d')NextDt, CumuPrinAmt,period,voucherno,a.acno,b.guardianId,b.minorId,b.acctnature,b.custfullname,b.PAN_GIRNumber FROM td_vouchmst a,\n"
                    + "(select cast(c.guardiancode as unsigned) guardianId,b.acno,c.customerid minorId,acctnature,custfullname,PAN_GIRNumber from customerid a,td_accountmaster b,cbs_cust_minorinfo c,cbs_customer_master_detail d,accounttypemaster e  \n"
                    + "where a.custid = cast(c.customerid as unsigned) and c.customerid = d.customerid and minorflag = 'Y' and b.accttype = e.acctcode \n"
                    + "and a.acno = b.acno and (closingdate is null OR closingdate = '' OR closingdate > '" + frDt + "') \n"
                    + "union \n"
                    + "select custid guardianId,b.acno,''minorId,acctnature,custfullname,PAN_GIRNumber from customerid a,td_accountmaster b,cbs_customer_master_detail c,accounttypemaster e where a.acno = b.acno \n"
                    + "and a.custid = cast(c.customerid as unsigned) and(closingdate is null OR closingdate = '' OR closingdate > '" + frDt + "')and b.accttype = e.acctcode \n"
                    + ")b WHERE a.acno= b.acno and(ClDt is null OR ClDt = '' OR ClDt > '" + frDt + "') \n"
                    + "union /* RD & DDS*/\n"
                    + "select''intopt,b.openingdt FdDate,date_format(b.RDmatdate,'%Y%m%d') matDate,b.intDeposit roi,b.rdinstal prinamt,''NextDt,'0'CumuPrinAmt,'' period,''voucherno,b.acno,custid guardianId,''minorId,acctnature,c.custfullname,c.PAN_GIRNumber \n"
                    + "from customerid a,accountmaster b,cbs_customer_master_detail c,accounttypemaster e  where a.acno = b.acno \n"
                    + "and a.custid = cast(c.customerid as unsigned) and (closingdate is null OR closingdate = '' OR closingdate > '" + frDt + "')  and b.accstatus <> 15 and b.accttype = e.acctcode and acctnature in('RD','DDS') \n"
                    + "union \n"
                    + "select ''intopt,b.openingdt FdDate,date_format(b.RDmatdate,'%Y%m%d') matDate,b.intDeposit roi,b.rdinstal prinamt,''NextDt,'0'CumuPrinAmt,'' period,''voucherno,b.acno,cast(c.guardiancode as unsigned) guardianId,c.customerid minorId,acctnature,d.custfullname,d.PAN_GIRNumber \n"
                    + "from customerid a,accountmaster b,cbs_cust_minorinfo c,cbs_customer_master_detail d,accounttypemaster e \n"
                    + "where a.custid = cast(c.customerid as unsigned) and c.customerid = d.customerid and minorflag = 'Y' and b.accttype = e.acctcode and acctnature in('RD','DDS')\n"
                    + "and a.acno = b.acno and (closingdate is null OR closingdate = '' OR closingdate > '" + frDt + "')  and b.accstatus <> 15\n"
                    + "order by 11,10,9)b where cast(a.customerid as unsigned) =b.guardianId " + panCustomerId + " group by cast(a.customerid as unsigned)").getResultList();
            for (int j = 0; j < panList.size(); j++) {
                Vector panVector = (Vector) panList.get(j);
                panMap.put(panVector.get(0).toString(), panVector.get(1).toString());
            }

            //Closed Voucher Interest
            Map<String, Double> closedIntMap = new HashMap<>();
            List closeVoucherList = em.createNativeQuery("select concat(a.ACNO,cast(a.VoucherNo as unsigned)) AcnoVouchNo,sum(Interest),dt,status from td_vouchmst a,td_interesthistory b where ClDt > '" + frDt + "'  and a.acno = b.acno and a.VoucherNo = b.VoucherNo "
                    + "and dt > '" + frDt + "' group by a.ACNO,a.VoucherNo order by a.ACNO,a.VoucherNo").getResultList();
            for (int j = 0; j < closeVoucherList.size(); j++) {
                Vector closeVector = (Vector) closeVoucherList.get(j);
                closedIntMap.put(closeVector.get(0).toString(), Double.parseDouble(closeVector.get(1).toString()));
            }

            result = em.createNativeQuery("select intopt,FdDate,matDate,roi,prinamt,NextDt,CumuPrinAmt,period,voucherno,acno,guardianId,minorId,acctnature,custfullname,PAN_GIRNumber,TDSFLAG,ifnull(CUST_TYPE,'OT'),dob,CustEntityType from("
                    + "SELECT intopt,date_format(fddt,'%Y%m%d') FdDate,date_format(matdt,'%Y%m%d') matDate,roi,prinamt,"
                    + "date_format(NextIntPayDt,'%Y%m%d')NextDt, CumuPrinAmt,period,voucherno,a.acno,b.guardianId,b.minorId,b.acctnature,b.custfullname,b.PAN_GIRNumber,b.TDSFLAG,b.CUST_TYPE,dob,CustEntityType FROM td_vouchmst a,"
                    + "(select cast(c.guardiancode as unsigned) guardianId,b.acno,c.customerid minorId,acctnature,custfullname,PAN_GIRNumber,TDSFLAG,CUST_TYPE,date_format(d.DateOfBirth,'%Y%m%d') dob,CustEntityType from customerid a,td_accountmaster b,cbs_cust_minorinfo c,cbs_customer_master_detail d,accounttypemaster e  "
                    + "where a.custid = cast(c.customerid as unsigned) and c.customerid = d.customerid and minorflag = 'Y' and b.accttype = e.acctcode "
                    + "and a.acno = b.acno and (closingdate is null OR closingdate = '' OR closingdate > '" + frDt + "') "
                    + "union "
                    + "select custid guardianId,b.acno,''minorId,acctnature,custfullname,PAN_GIRNumber,TDSFLAG,CUST_TYPE,date_format(c.DateOfBirth,'%Y%m%d') dob,CustEntityType from customerid a,td_accountmaster b,cbs_customer_master_detail c,accounttypemaster e where a.acno = b.acno "
                    + "and a.custid = cast(c.customerid as unsigned) and(closingdate is null OR closingdate = '' OR closingdate > '" + frDt + "')and b.accttype = e.acctcode "
                    + ")b WHERE a.acno= b.acno and(ClDt is null OR ClDt = '' OR ClDt > '" + frDt + "') "
                    + "union /* RD & DDS*/"
                    + "select''intopt,b.openingdt FdDate,date_format(b.RDmatdate,'%Y%m%d') matDate,b.intDeposit roi,b.rdinstal prinamt,''NextDt,'0'CumuPrinAmt,'' period,''voucherno,b.acno,custid guardianId,''minorId,acctnature,c.custfullname,c.PAN_GIRNumber,b.TDSFLAG,b.CUST_TYPE,date_format(c.DateOfBirth,'%Y%m%d') dob,CustEntityType "
                    + "from customerid a,accountmaster b,cbs_customer_master_detail c,accounttypemaster e  where a.acno = b.acno "
                    + "and a.custid = cast(c.customerid as unsigned) and (closingdate is null OR closingdate = '' OR closingdate > '" + frDt + "') and b.accstatus <> 15 and b.accttype = e.acctcode and acctnature in('RD','DDS') "
                    + "union "
                    + "select ''intopt,b.openingdt FdDate,date_format(b.RDmatdate,'%Y%m%d') matDate,b.intDeposit roi,b.rdinstal prinamt,''NextDt,'0'CumuPrinAmt,'' period,''voucherno,b.acno,cast(c.guardiancode as unsigned) guardianId,c.customerid minorId,acctnature,d.custfullname,d.PAN_GIRNumber,b.TDSFLAG,b.CUST_TYPE,date_format(d.DateOfBirth,'%Y%m%d') dob,CustEntityType  "
                    + "from customerid a,accountmaster b,cbs_cust_minorinfo c,cbs_customer_master_detail d,accounttypemaster e  "
                    + "where a.custid = cast(c.customerid as unsigned) and c.customerid = d.customerid and minorflag = 'Y' and b.accttype = e.acctcode and acctnature in('RD','DDS')"
                    + "and a.acno = b.acno and (closingdate is null OR closingdate = '' OR closingdate > '" + frDt + "') and b.accstatus <> 15 "
                    + "order by 11,10,9)"
                    + "a " + customerId + " order by 11,10,9").getResultList();
            // FD & MS
//            result = em.createNativeQuery("SELECT intopt,date_format(fddt,'%Y%m%d') FdDate,date_format(matdt,'%Y%m%d') matDate,roi,prinamt,\n"
//                    + "date_format(NextIntPayDt,'%Y%m%d')NextDt, CumuPrinAmt,period,voucherno,a.acno,b.guardianId,b.minorId,b.acctnature FROM td_vouchmst a,\n"
//                    + "(select c.guardiancode guardianId,b.acno,c.customerid minorId,acctnature,custfullname,PAN_GIRNumber from customerid a,td_accountmaster b,cbs_cust_minorinfo c,cbs_customer_master_detail d,accounttypemaster e  \n"
//                    + "where a.custid = cast(c.customerid as unsigned) and c.customerid = d.customerid and minorflag = 'Y' and b.accttype = e.acctcode\n"
//                    + "and a.acno = b.acno and (closingdate is null OR closingdate = '' OR closingdate > '" + toDt + "')\n"
//                    + "union\n"
//                    + "select custid guardianId,b.acno,''minorId,acctnature,custfullname,PAN_GIRNumber from customerid a,td_accountmaster b,cbs_customer_master_detail c,accounttypemaster e where a.acno = b.acno \n"
//                    + "and a.custid = cast(c.customerid as unsigned) and(closingdate is null OR closingdate = '' OR closingdate > '" + toDt + "')and b.accttype = e.acctcode \n"
//                    + ")b WHERE a.acno= b.acno and status<>'C' and substring(a.acno,1,2) = '02' order by 11,10,9").getResultList();
            String gId = "";
            double intAmt = 0, intAmt1 = 0;
            int cni = 0;
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    TdReceiptIntDetailPojo pojo = new TdReceiptIntDetailPojo();
                    String intopt = vtr.get(0).toString();
                    String fddt = vtr.get(1).toString();
                    String matdt = vtr.get(2).toString();
                    Float roi = Float.parseFloat(vtr.get(3).toString());
                    double prinAmt = Double.parseDouble(vtr.get(4).toString());
                    String nextIntPayDT = vtr.get(5).toString();
                    double cumuPrinAmt = Double.parseDouble(vtr.get(6).toString());
                    String pr = vtr.get(7).toString();
                    String voucherNo = vtr.get(8).toString();
                    String acno = vtr.get(9).toString();
                    String guardianId = vtr.get(10).toString();
                    String minorId = vtr.get(11).toString();
                    String acctnature = vtr.get(12).toString();
                    String custfullname = vtr.get(13).toString();
                    String panNo = vtr.get(14).toString();
                    String tdsFlag = vtr.get(15).toString();
                    String isSeniorCitizen = vtr.get(16).toString();
                    String dob = vtr.get(17).toString();
                    String custType = vtr.get(18).toString();
                    String srCiznFlag = "N";
                    if (isSeniorCitizen.equalsIgnoreCase("SC") && custType.equalsIgnoreCase("01")) {
                        srCiznFlag = "Y";
                    } else {
                        /*Cust Type (01)= INDIVIDUAL && AGE>=60*/
                        // if ((custType.equalsIgnoreCase("01")) && (CbsUtil.yearDiff(ymdFormat.parse(dob), new Date()) >= dblBenifitAge)) {
                        if ((custType.equalsIgnoreCase("01")) && (ymdFormat.parse(CbsUtil.yearAdd(dob, (int) dblBenifitAge)).before(new Date()) || ymdFormat.parse(CbsUtil.yearAdd(dob, (int) dblBenifitAge)).equals(new Date()))) {
                            srCiznFlag = "Y";
                        } else {
                            srCiznFlag = "N";
                        }
                    }
                    String fromDate = frDt;
                    String toDate = toDt;
                    String period = "";
                    String acNoVouchNo = acno + voucherNo;
                    // System.out.println(acno);                    
                    if (isSenior.equalsIgnoreCase(srCiznFlag)) {
                        if (acctnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctnature.equalsIgnoreCase(CbsConstant.MS_AC)) {

                            if (ymdFormat.parse(fromDate).before(ymdFormat.parse(fddt))) {
                                fromDate = fddt;
                            } else {
                                fromDate = fromDate;
                            }
                            if (ymdFormat.parse(toDate).after(ymdFormat.parse(matdt))) {
                                toDate = matdt;
                            } else {
                                toDate = toDate;
                            }
                            //System.out.println("AAA : " + fddt + " to " + matdt + " ==== PPP " + fromDate + " to " + toDate);

                            long dtDiff = CbsUtil.dayDiff(ymdFormat.parse(fromDate), ymdFormat.parse(toDate));
                            List aRes = CbsUtil.getYrMonDayDiff(fromDate, toDate);
                            int prdY = Integer.parseInt(aRes.get(0).toString());
                            int prdM = Integer.parseInt(aRes.get(1).toString());
                            int prdD = Integer.parseInt(aRes.get(2).toString());
                            // period = prdY + "Years" + prdM + "Months" + prdD + "Days";
                            period = 0 + "Years" + 0 + "Months" + dtDiff + "Days";
                        }


                        String bAInt = "0";
                        double interestAmt = 0;
                        if (closedIntMap.get(acNoVouchNo) == null) {
                            if (acctnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                if ((intopt.equalsIgnoreCase("S")) || (intopt.equalsIgnoreCase("Simple")) || (intopt.equalsIgnoreCase("Y"))) {
                                    bAInt = tdRcptMgmtRemote.orgFDInterestSimple15gh(intopt, roi, fromDate, toDate, prinAmt, period, acno.substring(0, 2));
                                } else {
                                    bAInt = tdRcptMgmtRemote.orgFDInterest(intopt, roi, fromDate, toDate, cumuPrinAmt, period, acno.substring(0, 2));
                                }
                                interestAmt = Double.parseDouble(bAInt);
                            } else {
                                interestAmt = tdRemote.getRdProjectedInt(acno, frDt, toDt);
//                        Float rdinstal = Float.parseFloat(vtr.get(4).toString());
//                        List rdIntList = rdIntCalRemote.rdIntCal(fromDate, toDate, roi, rdinstal);
//                        interestAmt = Double.parseDouble(rdIntList.get(1).toString());
                            }
                        } else {
                            interestAmt = closedIntMap.get(acNoVouchNo);
                        }
                        double tds = 0;
                        if (panMap.get(guardianId).length() == 10 && ParseFileUtil.isAlphabet(panMap.get(guardianId).substring(0, 5))) {
                            tds = (interestAmt * 10) / 100;
                        } else {
                            tds = (interestAmt * 20) / 100;
                        }

                        pojo.setMajorCustId(guardianId);
                        pojo.setCustId(minorId);
                        pojo.setName(custfullname);
                        pojo.setPanNo(panMap.get(guardianId));
                        pojo.setAcNo(acno);
                        pojo.setReceiptNo(voucherNo);
                        pojo.setInterest(interestAmt);
                        pojo.setTds(CbsUtil.round(tds, 0));
                        pojo.setRecoveredFlag(tdsFlag);
                        dataList.add(pojo);
                    }
                }
            }
            Map<String, BigDecimal> mapIdWiseInt = new HashMap<String, BigDecimal>();
            for (TdReceiptIntDetailPojo obj : dataList) {
                if (mapIdWiseInt.containsKey(obj.getMajorCustId())) {
                    mapIdWiseInt.put(obj.getMajorCustId(), new BigDecimal(obj.getInterest()).add(mapIdWiseInt.get(obj.getMajorCustId())));
                } else {
                    mapIdWiseInt.put(obj.getMajorCustId(), new BigDecimal(obj.getInterest()));
                }
            }
            for (TdReceiptIntDetailPojo obj : dataList) {
                TdReceiptIntDetailPojo pojo = new TdReceiptIntDetailPojo();
                // System.out.println(mapIdWiseInt.get(obj.getMajorCustId()));
                if (mapIdWiseInt.get(obj.getMajorCustId()).doubleValue() >= frAmt && mapIdWiseInt.get(obj.getMajorCustId()).doubleValue() <= toAmt) {
                    pojo.setMajorCustId(obj.getMajorCustId());
                    pojo.setCustId(obj.getCustId());
                    pojo.setName(obj.getName());
                    pojo.setPanNo(obj.getPanNo());
                    pojo.setAcNo(obj.getAcNo());
                    pojo.setReceiptNo(obj.getReceiptNo());
                    pojo.setInterest(obj.getInterest());
                    pojo.setTds(obj.getTds());
                    pojo.setRecoveredFlag(obj.getRecoveredFlag());
                    dataList1.add(pojo);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList1;

    }

    @Override
    public List getVoucherPrintinALLgData(String branch, String reportOption, String date) throws ApplicationException {
        List result = new ArrayList();
        try {

            if (reportOption.equalsIgnoreCase("1")) {

                result = em.createNativeQuery(" "
                        + "select custname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from recon a,accountmaster b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and (a.enterby='system' or a.authby ='system') "
                        + "union all "
                        + "select custname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from ca_recon a,accountmaster b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and (a.enterby='system' or a.authby ='system') "
                        + "union all "
                        + "select custname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from td_recon a,td_accountmaster b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and (a.enterby='system' or a.authby ='system') "
                        + "union all "
                        + "select custname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from rdrecon a,accountmaster b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and (a.enterby='system' or a.authby ='system') "
                        + "union all "
                        + "select custname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from loan_recon a,accountmaster b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and (a.enterby='system' or a.authby ='system') "
                        + "union all "
                        + "select custname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from ddstransaction a,accountmaster b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and (a.enterby='system' or a.authby ='system') "
                        + "union all "
                        + "select acname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from gl_recon a,gltable b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and (a.enterby='system' or a.authby ='system') "
                        + "").getResultList();
            } else {

                result = em.createNativeQuery(""
                        + "select custname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from recon a,accountmaster b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and (a.enterby <> 'system' and a.authby <>'system') "
                        + "union all "
                        + "select custname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from ca_recon a,accountmaster b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and (a.enterby <> 'system' and a.authby <>'system') "
                        + "union all "
                        + "select custname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from td_recon a,td_accountmaster b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and (a.enterby <> 'system' and a.authby <>'system') "
                        + "union all "
                        + "select custname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from rdrecon a,accountmaster b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and (a.enterby <> 'system' and a.authby <>'system') "
                        + "union all "
                        + "select custname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from loan_recon a,accountmaster b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and a.enterby <> 'system' and a.authby <>'system' "
                        + "union all "
                        + "select custname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from ddstransaction a,accountmaster b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and (a.enterby <> 'system' and a.authby <>'system') "
                        + "union all "
                        + "select acname,a.acno,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,trantype,cast(recno as unsigned),trsno,cast(payby as unsigned),details,trandesc,org_brnid,dest_brnid,ifnull(instno,'') from gl_recon a,gltable b where a.acno = b.acno and dt='" + date + "' and trantype in (2,8) and org_brnid='" + branch + "' "
                        + "and (a.enterby <> 'system' and a.authby <>'system' ) "
                        + " ").getResultList();
            }
            return result;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    @Override
    public double getSeniorCitizenAmt(String date) throws ApplicationException {
        try {
            double tdsApplicableAmtForSrCtzen = 0;

            List selectQuer65 = em.createNativeQuery("SELECT ifnull(TDS_AMOUNT,-1),ifnull(TDS_RATE *(1 + TDS_SURCHARGE /100.0),-1),tdsRate_pan, ifnull(Srctzn_Tds_Amount,0) FROM tdsslab "
                    + "WHERE TYPE=1 AND TDS_APPLICABLEDATE IN(SELECT MAX(TDS_APPLICABLEDATE) FROM tdsslab WHERE TDS_APPLICABLEDATE<='" + date
                    + "' AND TYPE=1)").getResultList();
            if (!selectQuer65.isEmpty()) {
                Vector v65 = (Vector) selectQuer65.get(0);
                tdsApplicableAmtForSrCtzen = Double.parseDouble(v65.get(3).toString());
            }
            return tdsApplicableAmtForSrCtzen;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List<InterestReportPojo> getThriftInterestData(String brnCode, String area, String frDt, String toDt) throws ApplicationException {
        List<InterestReportPojo> dataList = new ArrayList<>();
        String brnQuery = "", brnQuery1 = "", areaQuery = "";
        if (!brnCode.equalsIgnoreCase("0A")) {
            brnQuery = "and substring(acno,1,2) = '" + brnCode + "'";
            brnQuery1 = "and a.curbrcode = '" + brnCode + "'";
        }

        if (!area.equalsIgnoreCase("AL")) {
            areaQuery = "where b.area = '" + area + "'";
        }

        try {
            List reportList = em.createNativeQuery("select a.acno,ifnull(b.custname,''),a.intAmt,ifnull(b.area,'') from "
                    + "(select acno,sum(CrAmt) intAmt from recon  where substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "' and accttype = 'TF') " + brnQuery + " "
                    + "and dt between '" + frDt + "' and '" + toDt + "' and trantype = 8 and ty = 0 group by acno)a left join "
                    + "(select a.acno,custname,s.area from share_holder s,customerid c ,accountmaster a where a.accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "' and accttype = 'TF') " + brnQuery1 + " "
                    + "and a.acno = c.acno  and s.custid = c.custid and (a.closingdate is null OR a.closingdate = '' OR a.closingdate >'" + toDt + "'))b on a.acno = b.acno " + areaQuery + " "
                    + "order by b.area,a.acno,a.intAmt").getResultList();

            if (!reportList.isEmpty()) {
                for (int i = 0; i < reportList.size(); i++) {
                    Vector vtr = (Vector) reportList.get(i);
                    InterestReportPojo pojo = new InterestReportPojo();
                    pojo.setAcNo(vtr.get(0).toString());
                    if (vtr.get(1).toString().equalsIgnoreCase("")) {
                        pojo.setCustName(fts.getCustName(vtr.get(0).toString()));
                    } else {
                        pojo.setCustName(vtr.get(1).toString());
                    }
                    pojo.setIntAmt(new BigDecimal(vtr.get(2).toString()));
                    pojo.setPerAddr(vtr.get(3).toString());
                    dataList.add(pojo);
                }
            }
            return dataList;

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }
}
