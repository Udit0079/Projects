/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.LoanIntDetailsMonthWisePojo;
import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.dto.report.OverdueEmiReportSortedByAcNo;
import com.cbs.dto.report.OverdueEmiReportSortedByMoratorium;
import com.cbs.dto.report.OverdueNonEmiResultList;
import com.cbs.dto.report.ProvDetailOfLoanAccounts;
import com.cbs.dto.report.SortedByAcNo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.pojo.DateSlabPojo;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.pojo.OverDueListPojo;
import com.cbs.utils.CbsUtil;
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
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.bouncycastle.util.CollectionStore;

/**
 *
 * @author SAMY
 */
@Stateless(mappedName = "OverDueReportFacade")
public class OverDueReportFacade implements OverDueReportFacadeRemote {

    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    LoanReportFacadeRemote loanRemote;
    @EJB
    NpaReportFacadeRemote npaRemote;
    @EJB
    RbiReportFacadeRemote rbiRemote;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    @EJB
    DDSReportFacadeRemote ddsRemoteFacade;
    @PersistenceContext
    private EntityManager em;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat y_m_dFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat y_m_dhs = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private NumberFormat nft = new DecimalFormat("0.00");
    Date date = new Date();

    @Override
    public List<OverdueEmiReportPojo> getOverdueEmiReport(int repType, int codeType, String sector, String acCode, int emiPendingfrom, int emiPendingto, String tDate, String brCode, String status, Boolean chkBox, String acNo, String withOutNpa) throws ApplicationException {
        /*withoutNpa parameter is used for the OSS3 report Overdue parameter. It is by default N(For all the accounts) and Y for the Standard Account Only*/
        List<OverdueEmiReportPojo> returnList = new ArrayList<OverdueEmiReportPojo>();
        List<OverdueEmiReportPojo> returnList2 = new ArrayList<OverdueEmiReportPojo>();
        List acNoList = null;
        List secList = null;
        String sector1 = "";
        String accountNumber = "";
        accountNumber = acNo == null ? "" : acNo;
        double penalIntCharged = 0.0;
        int morOnOf = fts.getCodeForReportName("MORATORIUM-FLAG");
        String npaQuery = "", npaQuery1 = "";
        String oss3DtForOverDue = "20990101";
        if (withOutNpa.equalsIgnoreCase("Y")) {
            List oss3OD = common.getCode("OSS3_OVERDUE_DT");
            if (!oss3OD.isEmpty()) {
                if (!oss3OD.get(0).toString().equalsIgnoreCase("")) {
                    oss3DtForOverDue = oss3OD.get(0).toString();
                }
            }
        }
        try {
            String bnkIdenty = fts.getCodeFromCbsParameterInfo("GRACE_PD_APP_IN_OVERDUE");
            int overDueFlag = fts.getCodeForReportName("ActualOverdue");
            int isExceessEmi = common.isExceessEmi(tDate);
            String condQuery1 = "", condQuery2 = "", condQuery3 = "";
            if (!accountNumber.equalsIgnoreCase("")) {
                condQuery1 = "and a.acno in ( '" + accountNumber + "')";
                condQuery2 = "where substring(bb.acno,3,2) = '" + accountNumber.substring(2, 4) + "'";
                condQuery3 = "where cc.acno = '" + accountNumber + "'";
            }
            //For MORATORIUM PERIOD
            Map<String, String> mapMor = new HashMap<String, String>();
            if (morOnOf == 1) {
                List list = em.createNativeQuery("select a.acno,a.custname,a.OpeningDt,date_format(DATE_ADD(a.OpeningDt, INTERVAL MORATORIUM_PD MONTH),'%Y%m%d') moratoriumDt,\n"
                        + "b.MORATORIUM_PD,interestAmt,crAmt,interestAmt - crAmt overDue \n"
                        + "from accountmaster a,cbs_loan_acc_mast_sec b, \n"
                        + "(select acno,sum(dramt) interestAmt from loan_recon where trantype = 8 AND dt <= '" + tDate + "' group by acno) c,\n"
                        + "(select acno,sum(cramt) crAmt from loan_recon where dt <= '" + tDate + "' group by acno ) d \n"
                        + "where MORATORIUM_PD <> 0 and a.acno= b.acno and a.acno = c.acno and a.acno = d.acno\n"
                        + "and (ifnull(closingDate,'') = '' or closingDate > '" + tDate + "')\n"
                        + "and date_format(DATE_ADD(a.OpeningDt, INTERVAL MORATORIUM_PD MONTH),'%Y%m%d') > '" + tDate + "'\n"
                        + "and  interestAmt > crAmt").getResultList();
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        Vector ele1 = (Vector) list.get(i);
                        mapMor.put(ele1.get(0).toString(), ele1.get(7).toString());
                    }
                }
            }
            String emiQuery = "";
            if (chkBox == true) {
                emiQuery = " and  (dd.NoOfdueEmi-dd.paidCnt) between " + emiPendingfrom + " and " + emiPendingto + " ";
            }
            String brnQuery = "";
            if (!brCode.equalsIgnoreCase("0A")) {
                brnQuery = " and substring(dd.acno,1,2) ='" + brCode + "' ";
            }
            String acCodeQuery = "";
            if (!acCode.equalsIgnoreCase("All")) {
                acCodeQuery = " and substring(dd.acno,3,2) = '" + acCode + "' ";
            }
            if (repType == 0 && codeType == -1) {
                if (brCode.equalsIgnoreCase("0A")) {
                    acNoList = em.createNativeQuery("select distinct a.acno from accountmaster a  where (a.closingdate is null or a.closingdate ='' or a.closingdate > '" + tDate + "')  and  a.acno in "
                            + " (select acc_no from cbs_loan_borrower_details where sub_sector='" + sector + "') and  a.openingdt<='" + tDate + "' "
                            + " and a.accttype in (select acctcode from accounttypemaster where productcode = 'e' )  order by a.acno").getResultList();
                } else {
                    acNoList = em.createNativeQuery("select distinct a.acno from accountmaster a  where (a.closingdate is null or a.closingdate ='' or a.closingdate > '" + tDate + "')  and  "
                            + " a.acno in (select acc_no from cbs_loan_borrower_details where sub_sector='" + sector + "') and  a.openingdt<='" + tDate + "' "
                            + " and a.accttype in (select acctcode from accounttypemaster where productcode = 'e' ) and "
                            + " a.curbrcode='" + brCode + "'  order by a.acno").getResultList();
                }
                for (int i = 0; i < acNoList.size(); i++) {
                    Vector acVec = (Vector) acNoList.get(i);
                    if (acVec.get(0) != null || !acVec.get(0).toString().equalsIgnoreCase("")) {
                        accountNumber = acVec.get(0).toString();
                        returnList.addAll(getOverdueEmiList(sector, accountNumber, emiPendingfrom, emiPendingto, tDate, brCode, bnkIdenty, overDueFlag, isExceessEmi, mapMor, morOnOf));
                    }
                }
            } else if (repType == 1 && codeType == 1) {
                secList = em.createNativeQuery("select distinct(sub_sector) from cbs_loan_borrower_details where  acc_no in "
                        + "(select distinct(acno) from emidetails)").getResultList();
                for (int i = 0; i < secList.size(); i++) {
                    Vector secVec = (Vector) secList.get(i);
                    if (secVec.get(0) != null || !secVec.get(0).toString().equalsIgnoreCase("")) {
                        sector1 = secVec.get(0).toString();
                        if (brCode.equalsIgnoreCase("0A")) {
                            acNoList = em.createNativeQuery("select distinct a.acno from accountmaster a  where (a.closingdate is null or a.closingdate ='' or a.closingdate > '" + tDate + "')  and  a.acno in "
                                    + "(select acc_no from cbs_loan_borrower_details where sub_sector='" + sector1 + "') and  openingdt<='" + tDate
                                    + "' and a.accttype in (select acctcode from accounttypemaster where productcode = 'e' )  order by a.acno").getResultList();
                        } else {
                            acNoList = em.createNativeQuery("select distinct a.acno from accountmaster a  where (a.closingdate is null or a.closingdate ='' or a.closingdate > '" + tDate + "')  and  a.acno in "
                                    + "(select acc_no from cbs_loan_borrower_details where sub_sector='" + sector1 + "') and  openingdt<='" + tDate
                                    + "' and a.accttype in (select acctcode from accounttypemaster where productcode = 'e' ) and a.curbrcode='" + brCode + "'  order by a.acno").getResultList();
                        }
                        for (int j = 0; j < acNoList.size(); j++) {
                            Vector acVec = (Vector) acNoList.get(j);
                            if (acVec.get(0) != null || !acVec.get(0).toString().equalsIgnoreCase("")) {
                                accountNumber = acVec.get(0).toString();
                                returnList.addAll(getOverdueEmiList(sector1, accountNumber, emiPendingfrom, emiPendingto, tDate, brCode, bnkIdenty, overDueFlag, isExceessEmi, mapMor, morOnOf));
                            }
                        }
                    }
                }
            } else if (repType == 1 && codeType == 0) {
//                secList = em.createNativeQuery("select distinct(sub_sector) from cbs_loan_borrower_details where  acc_no in "
//                        + "(select distinct(acno) from emidetails where (paymentdt is null or paymentdt ='' or paymentdt > '" + tDate + "'))").getResultList();
//                for (int i = 0; i < secList.size(); i++) {
//                    Vector secVec = (Vector) secList.get(i);
//                    if (secVec.get(0) != null || !secVec.get(0).toString().equalsIgnoreCase("")) {
//                        sector1 = secVec.get(0).toString();
//                        if (brCode.equalsIgnoreCase("0A")) {
//                            if (acCode.equalsIgnoreCase("ALL")) {
//                                String acctCode = " select acctcode from accounttypemaster where acctnature in ('TL') and crdbflag in ('B','D') ";
//                                acNoList = em.createNativeQuery("select distinct a.acno from accountmaster a where (a.closingdate is null or a.closingdate ='' or a.closingdate > '" + tDate + "') and "
//                                        + " a.accttype in (" + acctCode + ") "
//                                        + " and  a.acno in(select acc_no from cbs_loan_borrower_details where sub_sector='" + sector1 + "') and  "
//                                        + " a.openingdt<='" + tDate + "' and substring(a.acno,3,2) in (select acctcode from accounttypemaster where "
//                                        + " productcode = 'e' ) order by a.acno").getResultList();
//                            } else {
//                                acNoList = em.createNativeQuery("select distinct a.acno from accountmaster a where (a.closingdate is null or a.closingdate ='' or a.closingdate > '" + tDate + "') and "
//                                        + " a.accttype in ('" + acCode + "') and  a.acno in(select acc_no from cbs_loan_borrower_details where sub_sector='" + sector1 + "') and  "
//                                        + " a.openingdt<='" + tDate + "' and substring(a.acno,3,2) in (select acctcode from accounttypemaster where "
//                                        + " productcode = 'e' ) order by a.acno").getResultList();
//                            }
//                        } else {
//                            if (acCode.equalsIgnoreCase("ALL")) {
//                                String acctCode = " select acctcode from accounttypemaster where acctnature in ('TL') and crdbflag in ('B','D') ";
//                                acNoList = em.createNativeQuery("select distinct a.acno from accountmaster a where (a.closingdate is null or a.closingdate ='' or a.closingdate > '" + tDate + "')  and "
//                                        + " a.accttype in (" + acctCode + " )"
//                                        + " and a.acno in (select acc_no from cbs_loan_borrower_details where sub_sector='" + sector1 + "') and  "
//                                        + " a.openingdt<='" + tDate + "' and substring(a.acno,3,2) in (select acctcode from accounttypemaster "
//                                        + " where productcode = 'e' ) and a.curbrcode='" + brCode + "' order by a.acno").getResultList();
//                            } else {
//                                acNoList = em.createNativeQuery("select distinct a.acno from accountmaster a where (a.closingdate is null or a.closingdate ='' or a.closingdate > '" + tDate + "')  and "
//                                        + " a.accttype in ('" + acCode + "') and a.acno in (select acc_no from cbs_loan_borrower_details where sub_sector='" + sector1 + "') and  "
//                                        + " a.openingdt<='" + tDate + "' and substring(a.acno,3,2) in (select acctcode from accounttypemaster "
//                                        + " where productcode = 'e' ) and a.curbrcode='" + brCode + "' order by a.acno").getResultList();
//                            }
//                        }
//                        for (int j = 0; j < acNoList.size(); j++) {
//                            Vector acVec = (Vector) acNoList.get(j);
//                            if (acVec.get(0) != null || !acVec.get(0).toString().equalsIgnoreCase("")) {
//                                accountNumber = acVec.get(0).toString();
//                                returnList.addAll(getOverdueEmiList(sector1, accountNumber, emiPendingfrom, emiPendingto, tDate, brCode, bnkIdenty, overDueFlag, isExceessEmi, mapMor, morOnOf));
//
//                            }
//                        }
//                    }
//                }
//                if (brCode.equalsIgnoreCase("0A")) {
//                    if (acCode.equalsIgnoreCase("ALL")) {
//                        String acctCode = " select acctcode from accounttypemaster where acctnature in ('TL') and crdbflag in ('B','D') ";
//                        acNoList = em.createNativeQuery("select distinct a.acno from emidetails a,accountmaster b "
//                                + " where (b.closingdate is null or b.closingdate ='' or b.closingdate > '" + tDate + "') "
//                                + " and a.acno not in (select distinct (acno) from emidetails  where (paymentdt is null or paymentdt ='' or paymentdt > '" + tDate + "'))"
//                                + " and b.accttype in (" + acctCode + ") and a.acno = b.acno order by a.acno").getResultList();
//                    } else {
//                        acNoList = em.createNativeQuery("select distinct a.acno from emidetails a,accountmaster b "
//                                + " where (b.closingdate is null or b.closingdate ='' or b.closingdate > '" + tDate + "') "
//                                + " and a.acno not in (select distinct (acno) from emidetails where (paymentdt is null or paymentdt ='' or paymentdt > '" + tDate + "'))"
//                                + " and b.accttype in ('" + acCode + "') and a.acno = b.acno order by a.acno").getResultList();
//                    }
//                } else {
//                    if (acCode.equalsIgnoreCase("ALL")) {
//                        acCode = " select acctcode from accounttypemaster where acctnature in ('TL') and crdbflag in ('B','D') ";
//                        acNoList = em.createNativeQuery("select distinct a.acno from emidetails a,accountmaster b "
//                                + " where (b.closingdate is null or b.closingdate ='' or b.closingdate > '" + tDate + "') "
//                                + " and a.acno not in (select distinct (acno) from emidetails where (paymentdt is null or paymentdt ='' or paymentdt > '" + tDate + "'))"
//                                + " and b.accttype in (" + acCode + ") and a.acno = b.acno and b.curbrcode='" + brCode + "' order by a.acno").getResultList();
//                    } else {
//                        acNoList = em.createNativeQuery("select distinct a.acno from emidetails a,accountmaster b "
//                                + " where (b.closingdate is null or b.closingdate ='' or b.closingdate > '" + tDate + "') "
//                                + " and a.acno not in (select distinct (acno) from emidetails where (paymentdt is null or paymentdt ='' or paymentdt > '" + tDate + "'))"
//                                + " and b.accttype in ('" + acCode + "') and a.acno = b.acno and b.curbrcode='" + brCode + "' order by a.acno").getResultList();
//                    }
//                }
//                for (int i = 0; i < acNoList.size(); i++) {
//                    Vector acVec = (Vector) acNoList.get(i);
//                    if (acVec.get(0) != null || !acVec.get(0).toString().equalsIgnoreCase("")) {
//                        accountNumber = acVec.get(0).toString();
//                        List<OverdueEmiReportPojo> overdueEmiList = getOverdueEmiList("A/Cs Whose Plan Has Finished", accountNumber, emiPendingfrom, emiPendingto, tDate, brCode, bnkIdenty, overDueFlag, isExceessEmi, mapMor, morOnOf);
//                        if (!overdueEmiList.isEmpty()) {
//                            for (OverdueEmiReportPojo pojo : overdueEmiList) {
//                                if (pojo.getBalance().intValue() < 0) {
//                                    returnList.add(pojo);
//                                }
//                            }
//                        }
//                    }
//                }
                String query = "select dd.customerid,dd.acno, dd.custname, ifnull(cm.mailStateCode,'') as custStateCode, "
                        + " ifnull(br.State,'') as brState ,concat(if(trim(ifnull(cm.MailAddressLine1,''))='', '',concat(trim(ifnull(cm.MailAddressLine1,'')),' ')),"
                        + " if(trim(ifnull(cm.MailAddressLine2,''))='', '',concat(trim(ifnull(cm.MailAddressLine2,'')),' ')),"
                        + " if(trim(ifnull(cm.MailVillage,''))='', '',concat(trim(ifnull(cm.MailVillage,'')),' ')),"
                        + " if(trim(ifnull(cm.mailblock,''))='', '',concat(trim(ifnull(cm.mailblock,'')),' ')),"
                        + " if(trim(ifnull(cm.MailDistrict,''))='', '',concat(trim(ifnull(cm.MailDistrict,'')),' ')),"
                        + " if(trim(ifnull(cm.MailStateCode,''))='', '',concat(trim(ifnull(cm.MailStateCode,'')),' ')),"
                        + " if(trim(ifnull(cm.MailPostalCode,''))='', '',concat(trim(ifnull(cm.MailPostalCode,'')),' '))) as custAdd, "
                        + " ifnull(dd.odlimit,0), ifnull(dd.sanctionlimitdt,'1900-01-01 00:00:00'), ifnull(dd.sanctionlimit,''),ifnull(dd.NoOfdueEmi,0), dd.minDueDt, dd.srNo, ifnull(dd.emiAmt,0), dd.excessTxnId, dd.excessAmt,"
                        + " dd.excessEmiSrNo,dd.excessAmtInEmi,dd.paidCnt, dd.paidDt,ifnull(dd.PAYMENTDT,dd.minDueDt),ifnull(dd.noOfEmi,0),dd.maxDueDt, ifnull(dd.bal,0), ifnull(dd.npaBal,0), ifnull(dd.totalBal,0), "
                        + " dd.flag, ifnull(dd.balance,0), if(ifnull(dd.overdueAmt,0) > ifnull(dd.balance,0),ifnull(dd.balance,0),(if(ifnull(dd.overdueAmt,0)=0,ifnull(dd.balance,0),ifnull(dd.overdueAmt,0)))) as overdueAmt,"
                        + " ifnull(dd.overdueNoOfEmi,0),ifnull(dd.crAmt,0), dd.maxdt,"
                        + " (select code from cbs_parameterinfo where name='GRACE_PD_APP_IN_OVERDUE' ) as bnkIdenty,  "
                        + " ifnull(dd.schemeCode,'CA001'),(select grace_period_for_late_fee_days from cbs_scheme_loan_interest_details  where scheme_code = dd.schemeCode) as gracePd,"
                        + " dd.npaEffDt,dd.npaSpNo,ifnull((Select c.description from accountstatus a,codebook c where acno= dd.acno  and effdt= dd.npaEffDt "
                        + " and spno=dd.npaSpNo and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                        + " AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3),'Operative') as accountStatus ,"
                        + " ifnull(dd.npaBalance,0),ifnull(dd.totalBalance,0),"
                        + " dd.prinAmt,dd.intAmt,dd.chgAmt,dd.penalAmt,dd.RecvrAmt,dd.npaRevrAmt,dd.j1Name,dd.j2Name,dd.j3Name,dd.j4Name,ifnull(dd.recover,''),dd.loanPeriod,DATE_ADD(dd.sanctionlimitdt, INTERVAL dd.loanPeriod MONTH) "
                        + " from "
                        + " (select cc.customerid,cc.acno, cc.custname, cc.odlimit, cc.sanctionlimitdt, cc.sanctionlimit,cc.NoOfdueEmi, cc.minDueDt, cc.srNo, cc.emiAmt, "
                        + " cc.excessTxnId, ifnull(cc.excessAmt, cc.excessAmtInEmi) as excessAmt,cc.excessEmiSrNo,cc.excessAmtInEmi,cc.paidCnt, cc.paidDt,cc.PAYMENTDT,"
                        + " cc.noOfEmi,cc.maxDueDt, cc.bal, cc.npaBal, cc.totalBal, if(cc.bal<0,'Dr','Cr') as flag,if(cc.bal<0,cc.bal*-1,cc.bal) as balance,   "
                        + " if(cc.npaBal<0,cc.npaBal*-1,cc.npaBal) as npaBalance,if(cc.totalBal<0,cc.totalBal*-1,cc.totalBal) as totalBalance,   "
                        + " if((cc.NoOfdueEmi>cc.paidCnt),(((cc.NoOfdueEmi-cc.paidCnt)*cc.emiAmt)-  ifnull(cc.excessAmt, cc.excessAmtInEmi)),0) as overdueAmt,"
                        + " if((cc.NoOfdueEmi>cc.paidCnt),cast((((cc.NoOfdueEmi-cc.paidCnt)*cc.emiAmt)-  ifnull(cc.excessAmt, cc.excessAmtInEmi))/cc.emiAmt as decimal(25,2)),0) as overdueNoOfEmi,"
                        + " cc.crAmt, cc.maxdt, cc.schemeCode,cc.npaEffDt, cc.npaSpNo,"
                        + " cc.prinAmt,cc.intAmt,cc.chgAmt,cc.penalAmt,cc.RecvrAmt,cc.npaRevrAmt,cc.j1Name,cc.j2Name,cc.j3Name,cc.j4Name,cc.recover,cc.loanPeriod "
                        + " from "
                        + " (select bb.customerid,bb.acno, bb.custname,  bb.odlimit, "
                        + " bb.sanctionlimitdt, bb.sanctionlimit,bb.NoOfdueEmi, bb.minDueDt, bb.srNo, bb.emiAmt, bb.excessTxnId, bb.excessAmt, bb.excessEmiSrNo,"
                        + " (select ifnull(sum(excessamt),0) as excessamt from cbs_loan_emi_excess_details where acno = bb.acno and txnid = bb.excessEmiSrNo limit 1) as excessAmtInEmi,"
                        + " bb.paidCnt, bb.paidDt, bb.PAYMENTDT,bb.noOfEmi,bb.maxDueDt, bb.bal, bb.npaBal, (bb.bal + bb.npaBal) as totalBal,bb.crAmt, bb.maxdt, bb.schemeCode, "
                        + " bb.npaEffDt, bb.npaSpNo,bb.prinAmt,bb.intAmt,bb.chgAmt,bb.penalAmt,bb.RecvrAmt,bb.npaRevrAmt,bb.j1Name,bb.j2Name,bb.j3Name,bb.j4Name,bb.recover,bb.loanPeriod "
                        + " from "
                        + " (select aa.acno, aa.custname, aa.odlimit, aa.sanctionlimitdt, aa.sanctionlimit, aa.NoOfdueEmi, aa.minDueDt, aa.srNo, "
                        + " (select ifnull(installamt,0) from emidetails where acno = aa.acno and sno =aa.srNo) as emiAmt,aa.excessTxnId,"
                        + " (select ifnull(sum(excessamt),0) as excessamt from cbs_loan_emi_excess_details where acno = aa.acno and txnid = aa.excessTxnId) as excessAmt, "
                        + " (select  (ifnull(sno,0)+1) as sno from emidetails where acno = aa.acno and duedt = aa.paidDt limit 1) as excessEmiSrNo,aa.paidCnt, aa.paidDt, "
                        + " aa.PAYMENTDT ,aa.noOfEmi,aa.maxDueDt, aa.bal, aa.npaBal, aa.crAmt, aa.maxdt,ci.CustId as customerid, aa.schemeCode, aa.npaEffDt, "
                        + " aa.npaSpNo,aa.prinAmt,aa.intAmt,aa.chgAmt,aa.penalAmt,aa.RecvrAmt,aa.npaRevrAmt,aa.j1Name,aa.j2Name,aa.j3Name,aa.j4Name,aa.recover,aa.loanPeriod "
                        + " from "
                        + " (select distinct a.acno,a.custname as custname, a.odlimit, la.sanctionlimitdt, ifnull(la.sanctionlimit,0) as sanctionlimit, "
                        + " (select count(*) from emidetails where acno = a.acno and duedt<='" + tDate + "') as NoOfdueEmi, "
                        + " (select max(duedt) from emidetails where acno =  a.acno   and duedt <= '" + tDate + "') as minDueDt,"
                        + " (select ifnull(max(sno),0) from emidetails where acno = a.acno and duedt <='" + tDate + "') as srNo, ifnull(b.excessTxnId,0) as excessTxnId,"
                        + " /*(select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = a.acno and dt <='" + tDate + "') as excessTxnId,*/ "
                        + " (select count(1)   from emidetails where acno = a.acno and paymentdt <= '" + tDate + "' and status = 'PAID') as paidCnt, "
                        + " (select max(duedt) from emidetails where acno = a.acno and paymentdt <= '" + tDate + "' and status = 'PAID') as paidDt,  "
                        + " (select max(PAYMENTDT) from emidetails where acno = a.acno and paymentdt <= '" + tDate + "' and status = 'PAID') as PAYMENTDT, "
                        + " (select max(duedt) from emidetails where acno = a.acno) as maxDueDt,  (select count(1) from emidetails where acno = a.acno) as noOfEmi, "
                        + " cast(ifnull(c.bal,0) as decimal(25,2)) as bal, cast(ifnull(d.npaBal,0) as decimal(25,2)) as npaBal, f.crAmt, f.maxdt, sc.schemeCode, np.npaEffDt, "
                        + " np.npaSpNo,"
                        + " ifnull(pa.principalAmt,0) as prinAmt,ifnull(ia.interestAmt,0) as intAmt,ifnull(ca.ChargesAmt,0) as chgAmt,ifnull(pia.penalInt,0) as penalAmt,ifnull(ra.RecoverAmt,0) as RecvrAmt,ifnull(na.NpaRecoverAmt,0) as npaRevrAmt,"
                        + " trim(ifnull(a.JtName1,'')) as j1Name,trim(ifnull(a.JtName2,'')) as j2Name,trim(ifnull(a.JtName3,'')) as j3Name,trim(ifnull(a.JtName4,'')) as j4Name,la.recover,sc.loanPeriod  "
                        + " from accountmaster a   "
                        + " left join  "
                        + " (SELECT acno, ifnull(SCHEME_CODE,'CA001') as schemeCode,LOAN_PD_MONTH as loanPeriod FROM cbs_loan_acc_mast_sec group by acno) sc on a.acno = sc.acno "
                        + " left join  "
                        + " (select acno, ifnull(max(txnid),0) as excessTxnId from cbs_loan_emi_excess_details where dt <='" + tDate + "' group by acno) b on a.acno = b.acno  "
                        + " left join  "
                        + " (select acno, ifnull(round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2),0) as bal from loan_recon where auth= 'Y' and dt <='" + tDate + "' "
                        + " group by acno) c on a.acno = c.acno  "
                        + " left join  "
                        + " (select acno, ifnull(round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2),0) as npaBal from npa_recon where auth= 'Y' and dt <='" + tDate + "' group by acno) d on a.acno = d.acno  "
                        + " left join  "
                        + " (select ln.acno, ifnull(max(cramt),0) as crAmt, e.maxdt from loan_recon ln, "
                        + " (select acno, max(dt) as maxdt from loan_recon where cramt>0 and trantype not in (8) and dt <= '" + tDate + "' group by acno) e "
                        + " where ln.acno = e.acno and ln.dt = e.maxdt group by ln.acno) f  on a.acno = f.acno  "
                        + " left join "
                        + " loan_appparameter la on a.ACNo = la.Acno   left join  ( Select a.acno, max(a.Effdt) as npaEffDt, aa.spNo as npaSpNo from accountstatus a  "
                        + " left join  "
                        + " (Select acno, max(Spno) as spNo from accountstatus where date_format(EffDt,'%Y%m%d')<='" + tDate + "'  "
                        + " and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) group by acno) aa  on a.acno = aa.acno  "
                        + " where date_format(a.EffDt,'%Y%m%d')<='" + tDate + "'  and (a.spflag in (11,12,13,14,3,6,7,8,2) or (a.spflag=1 and a.remark like 'Standard%') or "
                        + " (a.spflag=1 and a.remark like 'Operative%'))  and a.spno = aa.spNo  group by a.acno) np on a.ACNo = np.acno "
                        + "left join "
                        + "(select acno,cast(ifnull(sum(dramt),0) as decimal(25,2)) principalAmt from loan_recon where trandesc in('2','6') and dt < '" + tDate + "' group by acno) pa "
                        + "on a.acno = pa.acno "
                        + "left join "
                        + "(select acno,cast(ifnull(sum(dramt),0) as decimal(25,2)) interestAmt from loan_recon where trandesc in('3','4') and dt < '" + tDate + "' and (details not like '%EMI PENAL%' OR details not like '%OVERLIMIT PENAL%' OR details not like '%SECURITY PENAL%' OR details not like '%STOCK PENAL%') group by acno)ia "
                        + "on a.acno = ia.acno "
                        + "left join "
                        + "(select acno,cast(ifnull(sum(dramt),0) as decimal(25,2)) ChargesAmt from loan_recon where trandesc not in('2','6','3','4') and dt < '" + tDate + "' and (details not like '%EMI PENAL%' OR details not like '%OVERLIMIT PENAL%' OR details not like '%SECURITY PENAL%' OR details not like '%STOCK PENAL%') group by acno)ca "
                        + "on a.acno = ca.acno "
                        + "left join "
                        + "(select acno,cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2)) as penalInt from loan_recon where dt < '" + tDate + "' and (details like '%EMI PENAL%' OR details like '%OVERLIMIT PENAL%' OR details like '%SECURITY PENAL%' OR details like '%STOCK PENAL%') group by acno) pia "
                        + "on a.acno = pia.acno "
                        + "left join "
                        + "(select acno,cast(ifnull(sum(cramt),0) as decimal(25,2)) RecoverAmt from loan_recon where dt < '" + tDate + "' group by acno) ra "
                        + "on a.acno = ra.acno "
                        + "left join "
                        + "(select acno,cast(ifnull(sum(cramt),0) as decimal(25,2)) NpaRecoverAmt from npa_recon where dt < '" + tDate + "' group by acno) na\n"
                        + "on a.acno = na.acno  "
                        + " where (a.closingdate is null or a.closingdate ='' or a.closingdate > '" + tDate + "')  "
                        + " and a.accttype in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "') and crdbflag in ('B','D'))  and a.openingdt<='" + tDate + "'  "
                        + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where  productcode = 'e' )  " + condQuery1 + " ) aa, "
                        + " customerid ci "
                        + " where  aa.acno = ci.acno  and substring(ci.Acno,1,2) = substring(aa.acno,1,2)  "
                        + " and ( (aa.NoOfdueEmi>aa.paidCnt and (aa.bal+aa.npaBal)<0 )  or  (aa.NoOfdueEmi=aa.paidCnt and (aa.bal+aa.npaBal)<0 and aa.maxDueDt<='" + tDate + "')  "
                        + " or  (aa.noOfEmi=aa.paidCnt and aa.NoOfdueEmi<aa.noOfEmi and (aa.bal+aa.npaBal)<0 ) )  ) bb " + condQuery2 + ") cc   "
                        + "" + condQuery3 + " order by cc.acno) dd,  cbs_customer_master_detail cm, branchmaster br   where dd.flag<>'Cr' " + emiQuery + " "
                        + " and   dd.customerid = cast(cm.customerid as unsigned) and br.brncode=cast(substring(dd.acno,1,2) as unsigned) " + brnQuery + " " + acCodeQuery + " order by dd.acno ";
                List finalList = em.createNativeQuery(query).getResultList();
                String morOverDue = null;
                double leftPenalRecoverAmt = 0;
                if (!finalList.isEmpty()) {
                    for (int l = 0; l < finalList.size(); l++) {
                        Vector vect = (Vector) finalList.get(l);
                        accountNumber = vect.get(1).toString();

                        double prinAmt = Double.parseDouble(vect.get(39).toString());
                        double intAmt = Double.parseDouble(vect.get(40).toString());
                        double chgAmt = Double.parseDouble(vect.get(41).toString());
                        double penalAmt = Double.parseDouble(vect.get(42).toString());

                        double RecvrAmt = Double.parseDouble(vect.get(43).toString());
                        double npaRevrAmt = Double.parseDouble(vect.get(44).toString());
                        double totalRecoverAmt = RecvrAmt - npaRevrAmt;
                        String j1Name = vect.get(45).toString();
                        String j2Name = vect.get(46).toString();
                        String j3Name = vect.get(47).toString();
                        String j4Name = vect.get(48).toString();
                        String recover = vect.get(49).toString();
                        String joinName = "";
                        joinName = j1Name;
                        if (!j1Name.equalsIgnoreCase("") && !j2Name.equalsIgnoreCase("")) {
                            joinName = j1Name + "," + j2Name;
                        }
                        if (!j3Name.equalsIgnoreCase("")) {
                            joinName = joinName + "," + j3Name;
                        }
                        if (!j4Name.equalsIgnoreCase("")) {
                            joinName = joinName + "," + j4Name;
                        }
                        if (recover.equalsIgnoreCase("CIP")) {
                            leftPenalRecoverAmt = (totalRecoverAmt - chgAmt);
                            if (leftPenalRecoverAmt < 0) {
                                leftPenalRecoverAmt = penalAmt;
                            } else {
                                leftPenalRecoverAmt = (totalRecoverAmt - chgAmt) > penalAmt ? 0 : penalAmt - (totalRecoverAmt - chgAmt);
                            }
                        } else {
                            leftPenalRecoverAmt = (totalRecoverAmt - (prinAmt + intAmt));

                            if (leftPenalRecoverAmt < 0) {
                                leftPenalRecoverAmt = penalAmt;
                            } else {
                                leftPenalRecoverAmt = (totalRecoverAmt - (prinAmt + intAmt)) > penalAmt ? 0 : penalAmt - (totalRecoverAmt - (prinAmt + intAmt));
                            }
                        }

                        List list = new ArrayList();
                        String surety1Id = "", surety1Name = "", surety1Add = "";
                        String surety2Id = "", surety2Name = "", surety2Add = "";
                        String surety3Id = "", surety3Name = "", surety3Add = "";
                        int noOfEmiOverDue = 0;
                        noOfEmiOverDue = (int) Double.parseDouble(vect.get(28).toString());
                        String dueDt = vect.get(21).toString();
                        long dayDiff = 0;
                        int gracePeriod = vect.get(33) != null ? Integer.parseInt(vect.get(33).toString()) : Integer.parseInt("0");
                        String lastRecdt = vect.get(19) != null ? vect.get(19).toString() : "1900-01-01 00:00:00";
                        /*If No of paid EMI = Total No Of EMI
                         and Last Credit dt is after Last EMI due dt*/
                        String maxdueDt = vect.get(10) != null ? vect.get(10).toString():"1900-01-01 00:00:00";
                        if ((Integer.parseInt(vect.get(17).toString()) == Integer.parseInt(vect.get(20).toString()))) {
                            lastRecdt = lastRecdt;
                        } else {
                            if (y_m_dhs.parse(lastRecdt).after(y_m_dhs.parse(maxdueDt))) {
                                lastRecdt = maxdueDt;
                            } else {
                                if (vect.get(30) != null) {
                                    lastRecdt = vect.get(30).toString();
                                }
                            }
                        }
                        dayDiff = CbsUtil.dayDiff(y_m_dhs.parse(lastRecdt), ymdFormat.parse(tDate));
                        if (overDueFlag == 1) {
                            list = em.createNativeQuery("select name, ifnull(GAR_CUSTID,''),address from loan_guarantordetails where acno = '" + accountNumber + "'").getResultList();
                        }
                        if (!list.isEmpty()) {
                            for (int i = 0; i < list.size(); i++) {
                                Vector suretyVector = (Vector) list.get(i);
                                if (i == 0) {
                                    surety1Name = suretyVector.get(0).toString();
                                    surety1Id = suretyVector.get(1).toString();
                                    surety1Add = suretyVector.get(2).toString();
                                } else if (i == 1) {
                                    surety2Name = suretyVector.get(0).toString();
                                    surety2Id = suretyVector.get(1).toString();
                                    surety2Add = suretyVector.get(2).toString();
                                } else if (i == 2) {
                                    surety3Name = suretyVector.get(0).toString();
                                    surety3Id = suretyVector.get(1).toString();
                                    surety3Add = suretyVector.get(2).toString();
                                }
                            }
                        }

                        List list1 = new ArrayList();
                        list1 = em.createNativeQuery("select sum(a.dramt) from "
                                + " (select ifnull(sum(ifnull(dramt,0)),0) as dramt from loan_recon where acno = '" + accountNumber + "' "
                                + " and (details like '%EMI PENAL%' OR details like '%OVERLIMIT PENAL%' OR details like '%SECURITY PENAL%' OR details like '%STOCK PENAL%') "
                                + " union all "
                                + " select ifnull(sum(ifnull(dramt,0)),0) as dramt from npa_recon where  acno = '" + accountNumber + "' "
                                + " and (details like '%EMI PENAL%' OR details like '%OVERLIMIT PENAL%' OR details like '%SECURITY PENAL%' OR details like '%STOCK PENAL%')) a").getResultList();
                        if (!list1.isEmpty()) {
                            Vector ele = (Vector) list1.get(0);
                            penalIntCharged = Double.parseDouble(ele.get(0).toString());
                        }
                        if (bnkIdenty.equalsIgnoreCase("N")) {
                            OverdueEmiReportPojo pojo = new OverdueEmiReportPojo();
                            if (morOnOf == 1) {
                                if (mapMor != null) {
                                    morOverDue = mapMor.get(accountNumber);
                                }
                            }
                            pojo.setAccountNumber(accountNumber);
                            pojo.setCustState(vect.get(3).toString());
                            pojo.setBranchState(vect.get(4).toString());
                            if (morOverDue == null) {
                                if (y_m_dhs.parse(vect.get(51).toString()).before(ymdFormat.parse(tDate))) {
                                    pojo.setAmountOverdue(new BigDecimal(vect.get(26).toString()));
                                } else {
                                    pojo.setAmountOverdue(new BigDecimal(vect.get(27).toString()));
                                }
                                pojo.setMoratoriumFlag("A");
                            } else {
                                if (y_m_dhs.parse(vect.get(51).toString()).before(ymdFormat.parse(tDate))) {
                                    pojo.setAmountOverdue(new BigDecimal(vect.get(26).toString()));
                                } else {
                                    pojo.setAmountOverdue(new BigDecimal(morOverDue));
                                }
                                pojo.setMoratoriumFlag("M");
                            }
                            pojo.setBalance(new BigDecimal(vect.get(26).toString()));
                            pojo.setOutstandingBalance(new BigDecimal(vect.get(26).toString()));
                            pojo.setCustName(vect.get(2).toString());
                            pojo.setInstallmentamt(new BigDecimal(vect.get(12).toString()));
                            pojo.setLastRecDt(dmyFormat.format(y_m_dhs.parse(lastRecdt)));
                            pojo.setCurrentStatusNoOfDays(dayDiff);
                            if (overDueFlag == 0) {
                                pojo.setNoOfEmiOverdue(noOfEmiOverDue);
                            } else {
                                pojo.setNoOfActualEmiOverdue(Double.parseDouble(vect.get(28).toString()));
                            }
                            pojo.setFlag(String.valueOf(overDueFlag));
                            pojo.setRepaymentAmt(new BigDecimal(vect.get(29).toString()));
                            pojo.setReportDate(dmyFormat.format(ymdFormat.parse(tDate)));
                            pojo.setSanctionDate(dmyFormat.format(y_m_dhs.parse(vect.get(7).toString())));
                            pojo.setLoanPeriod(String.valueOf(vect.get(50).toString()));
                            pojo.setExpiryDate(dmyFormat.format(y_m_dhs.parse(vect.get(51).toString())));
                            pojo.setSanctionedamt(new BigDecimal(vect.get(8).toString()));
                            pojo.setPenalIntCharged(new BigDecimal(penalIntCharged));
                            pojo.setSector(sector);
                            pojo.setAccStatus(vect.get(36).toString());
                            pojo.setMoratoriumOnOf(String.valueOf(morOnOf));
                            pojo.setSurety1Name(surety1Name);
                            pojo.setSurety1Id(surety1Id);
                            pojo.setSurety1Add(surety1Add);
                            pojo.setSurety2Name(surety2Name);
                            pojo.setSurety2Id(surety2Id);
                            pojo.setSurety2Add(surety2Add);
                            pojo.setSurety3Name(surety3Name);
                            pojo.setSurety3Id(surety3Id);
                            pojo.setSurety3Add(surety3Add);
                            pojo.setCustAdd(vect.get(5).toString());
                            pojo.setCustomerId(vect.get(0).toString());
                            pojo.setNpaBalance(new BigDecimal(vect.get(37).toString()));
                            pojo.setPenalReceivable(new BigDecimal(leftPenalRecoverAmt));
                            pojo.setJoinName(joinName);
                            if (withOutNpa.equalsIgnoreCase("Y") && ymdFormat.parse(tDate).after(ymdFormat.parse(oss3DtForOverDue))) {
                                if (vect.get(36).toString().equalsIgnoreCase("STANDARD") || vect.get(36).toString().equalsIgnoreCase("Operative")) {
                                    returnList.add(pojo);
                                }
                            } else {
                                returnList.add(pojo);
                            }
                        } else {
                            if (dueDt.equalsIgnoreCase("") || lastRecdt.equalsIgnoreCase("")) {
                                OverdueEmiReportPojo pojo = new OverdueEmiReportPojo();
                                pojo.setAccountNumber(accountNumber);
                                pojo.setCustState(vect.get(3).toString());
                                pojo.setBranchState(vect.get(4).toString());
                                if (morOverDue == null) {
                                    if (y_m_dhs.parse(vect.get(51).toString()).before(ymdFormat.parse(tDate))) {
                                        pojo.setAmountOverdue(new BigDecimal(vect.get(26).toString()));
                                    } else {
                                        pojo.setAmountOverdue(new BigDecimal(vect.get(27).toString()));
                                    }
                                    pojo.setMoratoriumFlag("A");
                                } else {
                                    if (y_m_dhs.parse(vect.get(51).toString()).before(ymdFormat.parse(tDate))) {
                                        pojo.setAmountOverdue(new BigDecimal(vect.get(26).toString()));
                                    } else {
                                        pojo.setAmountOverdue(new BigDecimal(morOverDue));
                                    }
                                    pojo.setMoratoriumFlag("M");
                                }
                                pojo.setBalance(new BigDecimal(vect.get(26).toString()));
                                pojo.setOutstandingBalance(new BigDecimal(vect.get(26).toString()));
                                pojo.setCustName(vect.get(2).toString());
                                pojo.setInstallmentamt(new BigDecimal(vect.get(12).toString()));
                                pojo.setLastRecDt(dmyFormat.format(y_m_dhs.parse(lastRecdt)));
                                pojo.setCurrentStatusNoOfDays(dayDiff);
                                if (overDueFlag == 0) {
                                    pojo.setNoOfEmiOverdue(noOfEmiOverDue);
                                } else {
                                    pojo.setNoOfActualEmiOverdue(Double.parseDouble(vect.get(28).toString()));
                                }
                                pojo.setFlag(String.valueOf(overDueFlag));
                                pojo.setRepaymentAmt(new BigDecimal(vect.get(29).toString()));
                                pojo.setReportDate(dmyFormat.format(ymdFormat.parse(tDate)));
                                pojo.setSanctionDate(dmyFormat.format(y_m_dhs.parse(vect.get(7).toString())));
                                pojo.setLoanPeriod(String.valueOf(vect.get(50).toString()));
                                pojo.setExpiryDate(dmyFormat.format(y_m_dhs.parse(vect.get(51).toString())));
                                pojo.setSanctionedamt(new BigDecimal(vect.get(8).toString()));
                                pojo.setPenalIntCharged(new BigDecimal(penalIntCharged));
                                pojo.setAccStatus(vect.get(36).toString());
                                if (dueDt.equalsIgnoreCase("")) {
                                    pojo.setDueDate("");
                                } else {
                                    pojo.setDueDate(dmyFormat.format(ymdFormat.parse(dueDt)));
                                }
                                pojo.setMoratoriumOnOf(String.valueOf(morOnOf));
                                pojo.setSurety1Name(surety1Name);
                                pojo.setSurety1Id(surety1Id);
                                pojo.setSurety1Add(surety1Add);
                                pojo.setSurety2Name(surety2Name);
                                pojo.setSurety2Id(surety2Id);
                                pojo.setSurety2Add(surety2Add);
                                pojo.setSurety3Name(surety3Name);
                                pojo.setSurety3Id(surety3Id);
                                pojo.setSurety3Add(surety3Add);
                                pojo.setCustAdd(vect.get(5).toString());
                                pojo.setCustomerId(vect.get(0).toString());
                                pojo.setNpaBalance(new BigDecimal(vect.get(37).toString()));
                                pojo.setPenalReceivable(new BigDecimal(leftPenalRecoverAmt));
                                pojo.setJoinName(joinName);
                                if (withOutNpa.equalsIgnoreCase("Y") && ymdFormat.parse(tDate).after(ymdFormat.parse(oss3DtForOverDue))) {
                                    if (vect.get(36).toString().equalsIgnoreCase("STANDARD") || vect.get(36).toString().equalsIgnoreCase("Operative")) {
                                        returnList.add(pojo);
                                    }
                                } else {
                                    returnList.add(pojo);
                                }
                            } else {
                                String graceDt = CbsUtil.dateAdd(dueDt, gracePeriod);
                                OverdueEmiReportPojo pojo = new OverdueEmiReportPojo();
                                if (ymdFormat.parse(graceDt).getTime() <= ymdFormat.parse(lastRecdt).getTime()) {
                                    pojo.setAccountNumber(accountNumber);
                                    pojo.setCustState(vect.get(3).toString());
                                    pojo.setBranchState(vect.get(4).toString());
                                    if (morOverDue == null) {
                                        if (y_m_dhs.parse(vect.get(51).toString()).before(ymdFormat.parse(tDate))) {
                                            pojo.setAmountOverdue(new BigDecimal(vect.get(26).toString()));
                                        } else {
                                            pojo.setAmountOverdue(new BigDecimal(vect.get(27).toString()));
                                        }
                                        pojo.setMoratoriumFlag("A");
                                    } else {
                                        if (y_m_dhs.parse(vect.get(51).toString()).before(ymdFormat.parse(tDate))) {
                                            pojo.setAmountOverdue(new BigDecimal(vect.get(26).toString()));
                                        } else {
                                            pojo.setAmountOverdue(new BigDecimal(morOverDue));
                                        }
                                        pojo.setMoratoriumFlag("M");
                                    }
                                    pojo.setBalance(new BigDecimal(vect.get(26).toString()));
                                    pojo.setOutstandingBalance(new BigDecimal(vect.get(26).toString()));
                                    pojo.setCustName(vect.get(2).toString());
                                    pojo.setInstallmentamt(new BigDecimal(vect.get(12).toString()));
                                    pojo.setLastRecDt(dmyFormat.format(y_m_dhs.parse(lastRecdt)));
                                    pojo.setCurrentStatusNoOfDays(dayDiff);
                                    if (overDueFlag == 0) {
                                        pojo.setNoOfEmiOverdue(noOfEmiOverDue);
                                    } else {
                                        pojo.setNoOfActualEmiOverdue(Double.parseDouble(vect.get(28).toString()));
                                    }
                                    pojo.setFlag(String.valueOf(overDueFlag));
                                    pojo.setRepaymentAmt(new BigDecimal(vect.get(29).toString()));
                                    pojo.setReportDate(dmyFormat.format(ymdFormat.parse(tDate)));
                                    pojo.setSanctionDate(dmyFormat.format(y_m_dhs.parse(vect.get(7).toString())));
                                    pojo.setLoanPeriod(String.valueOf(vect.get(50).toString()));
                                    pojo.setExpiryDate(dmyFormat.format(y_m_dhs.parse(vect.get(51).toString())));
                                    pojo.setSanctionedamt(new BigDecimal(vect.get(8).toString()));
                                    pojo.setPenalIntCharged(new BigDecimal(penalIntCharged));
                                    pojo.setSector(sector);
                                    pojo.setAccStatus(vect.get(36).toString());
                                    if (dueDt.equalsIgnoreCase("")) {
                                        pojo.setDueDate("");
                                    } else {
                                        pojo.setDueDate(dmyFormat.format(ymdFormat.parse(dueDt)));
                                    }
                                    pojo.setMoratoriumOnOf(String.valueOf(morOnOf));
                                    pojo.setSurety1Name(surety1Name);
                                    pojo.setSurety1Id(surety1Id);
                                    pojo.setSurety1Add(surety1Add);
                                    pojo.setSurety2Name(surety2Name);
                                    pojo.setSurety2Id(surety2Id);
                                    pojo.setSurety2Add(surety2Add);
                                    pojo.setSurety3Name(surety3Name);
                                    pojo.setSurety3Id(surety3Id);
                                    pojo.setSurety3Add(surety3Add);
                                    pojo.setCustAdd(vect.get(5).toString());
                                    pojo.setCustomerId(vect.get(0).toString());
                                    pojo.setNpaBalance(new BigDecimal(vect.get(37).toString()));
                                    pojo.setPenalReceivable(new BigDecimal(leftPenalRecoverAmt));
                                    pojo.setJoinName(joinName);
                                    if (withOutNpa.equalsIgnoreCase("Y") && ymdFormat.parse(tDate).after(ymdFormat.parse(oss3DtForOverDue))) {
                                        if (vect.get(36).toString().equalsIgnoreCase("STANDARD") || vect.get(36).toString().equalsIgnoreCase("Operative")) {
                                            returnList.add(pojo);
                                        }
                                    } else {
                                        returnList.add(pojo);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        if (!returnList.isEmpty()) {
            for (OverdueEmiReportPojo pojo : returnList) {
                if (status.equalsIgnoreCase("N")) {
                    if ((pojo.getAccStatus().contains("SUB") || pojo.getAccStatus().contains("DOU") || pojo.getAccStatus().contains("LOS"))) {
                        returnList2.add(pojo);
                    }
                } else if (status.equalsIgnoreCase("S")) {
                    if (!(pojo.getAccStatus().contains("SUB") || pojo.getAccStatus().contains("DOU") || pojo.getAccStatus().contains("LOS"))) {
                        returnList2.add(pojo);
                    }
                } else {
                    returnList2.add(pojo);
                }
            }
            ComparatorChain chain = new ComparatorChain();
            if (morOnOf == 0) {
                chain.addComparator(new OverdueEmiReportSortedByAcNo());
                Collections.sort(returnList2, chain);
            } else {
                chain.addComparator(new OverdueEmiReportSortedByMoratorium());
                chain.addComparator(new OverdueEmiReportSortedByAcNo());
                Collections.sort(returnList2, chain);
            }
        }
        return returnList2;
    }

    public List<OverdueEmiReportPojo> getOverdueEmiList(String sector, String accountNumber, int emiPendingfrom, int emiPendingto, String tDate, String brCode, String overdueParameter, int overDueFlag, int isExcessEmi, Map<String, String> mapMor, int morOnOf) throws ApplicationException {
        List<OverdueEmiReportPojo> returnList = new ArrayList<OverdueEmiReportPojo>();
        try {
            List tempList;
            Vector ele;
            String custName = "", sanctionDate = "", lastRecdt = "", lastRecdt1 = "", lastEmiPaidDt = "",
                    dueDt = "", graceDt = "", brnState = "", custState = "";
            String reportDate = dmyFormat.format(ymdFormat.parse(tDate));
            double sanctionedamt = 0, installmentamt = 0, excess = 0, balance, lastRecAmt = 0,
                    emiOverdueAmt = 0, amountOverd = 0, amountOverdue = 0, actualInstallmentNo = 0;
            int IdealInstPaid = 0, ActualInstPaid = 0, installmentoverdue, gracePeriod = 0;
            long dayDiff = 0;
            String bnkName = "";
            String bnkAddress = "";
            String custAdd = "", custId = "";
            double penalIntCharged = 0.0;
            tempList = em.createNativeQuery("select a.custname as custname, ifnull(cm.mailStateCode,'') as custStateCode, ifnull(br.State,'') as brState   "
                    + ", sanctionlimitdt, ifnull(sanctionlimit,0),concat(if(trim(ifnull(cm.MailAddressLine1,''))='', '',concat(trim(ifnull(cm.MailAddressLine1,'')),' ')),\n"
                    + "if(trim(ifnull(cm.MailAddressLine2,''))='', '',concat(trim(ifnull(cm.MailAddressLine2,'')),' ')),\n"
                    + "if(trim(ifnull(cm.MailVillage,''))='', '',concat(trim(ifnull(cm.MailVillage,'')),' ')),\n"
                    + "if(trim(ifnull(cm.mailblock,''))='', '',concat(trim(ifnull(cm.mailblock,'')),' ')),\n"
                    + "if(trim(ifnull(cm.MailDistrict,''))='', '',concat(trim(ifnull(cm.MailDistrict,'')),' ')),\n"
                    + "if(trim(ifnull(cm.MailStateCode,''))='', '',concat(trim(ifnull(cm.MailStateCode,'')),' ')),\n"
                    + "if(trim(ifnull(cm.MailPostalCode,''))='', '',concat(trim(ifnull(cm.MailPostalCode,'')),' '))) as custAdd,cm.customerid "
                    + "from customerid ci, cbs_customer_master_detail cm, branchmaster br , "
                    + "accountmaster a left join loan_appparameter la on a.ACNo = la.Acno   "
                    + "where a.acno = ci.acno and ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast(substring(a.acno,1,2) as unsigned) "
                    + "and substring(ci.Acno,1,2) = substring(a.acno,1,2) and a.acno='" + accountNumber + "'").getResultList();
            if (!tempList.isEmpty()) {
                ele = (Vector) tempList.get(0);
                if (ele.get(0) != null) {
                    custName = ele.get(0).toString();
                    custState = ele.get(1).toString();
                    brnState = ele.get(2).toString();
                    if (ele.get(3) != null) {
                        sanctionDate = dmyFormat.format((Date) ele.get(3));
                    }
                    sanctionedamt = Double.parseDouble(ele.get(4).toString());
                    custAdd = ele.get(5).toString();
                    custId = ele.get(6).toString();
                }
            }
            tempList = em.createNativeQuery("select ifnull(installamt,0) from emidetails where acno = '" + accountNumber + "' and sno = (select max(sno) from emidetails where acno = '" + accountNumber + "' and duedt <='" + tDate + "')").getResultList();
            if (!tempList.isEmpty()) {
                ele = (Vector) tempList.get(0);
                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                    installmentamt = Double.parseDouble(ele.get(0).toString());
                }
            }
            tempList = em.createNativeQuery("select count(1) from emidetails where acno = '" + accountNumber + "' and duedt <= '" + tDate + "'").getResultList();
            if (!tempList.isEmpty()) {
                ele = (Vector) tempList.get(0);
                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                    IdealInstPaid = Integer.parseInt(ele.get(0).toString());
                }
            }
            tempList = em.createNativeQuery("select count(1), ifnull(max(duedt),"
                    + " ifnull((select min(duedt) from emidetails where acno = '" + accountNumber + "'  and duedt <= '" + tDate + "' and status = 'UNPAID'),"
                    + " (select date_format(OpeningDt,'%Y-%m-%d') from accountmaster where acno = '" + accountNumber + "'))) "
                    + " from emidetails where acno = '" + accountNumber + "' and paymentdt <= '" + tDate + "' and status = 'PAID'").getResultList();
            if (!tempList.isEmpty()) {
                ele = (Vector) tempList.get(0);
                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                    ActualInstPaid = Integer.parseInt(ele.get(0).toString());
                    lastEmiPaidDt = ymdFormat.format(y_m_dFormat.parse(ele.get(1).toString()));
                }
            }
            if (isExcessEmi == 0) {
                tempList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + accountNumber + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + tDate + "'  and acno = '" + accountNumber + "')) and e.acno ='" + accountNumber + "' limit 1").getResultList();
            } else {
                tempList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + accountNumber + "' and txnid in\n"
                        + "(select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + accountNumber + "' and dt <='" + tDate + "')").getResultList();
            }
            if (!tempList.isEmpty()) {
                if (!tempList.isEmpty()) {
                    ele = (Vector) tempList.get(0);
                    if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                        excess = Double.parseDouble(ele.get(0).toString());
                    }
                }
            }
            balance = common.getBalanceOnDate(accountNumber, tDate);
            if (balance < 0) {
                tempList = em.createNativeQuery("select max(dt) from loan_recon where acno='" + accountNumber + "' and  cramt>0 and trantype not in (8) and dt <= '" + tDate + "'").getResultList();
                if (!tempList.isEmpty()) {
                    ele = (Vector) tempList.get(0);
                    if (ele.get(0) != null) {
                        lastRecdt = ymdFormat.format(y_m_dFormat.parse(ele.get(0).toString()));
                        lastRecdt1 = ymdFormat.parse(lastEmiPaidDt).before(y_m_dFormat.parse(ele.get(0).toString())) ? dmyFormat.format(ymdFormat.parse(lastEmiPaidDt)) : dmyFormat.format(y_m_dFormat.parse(ele.get(0).toString()));
                        dayDiff = CbsUtil.dayDiff(ymdFormat.parse(ymdFormat.format(dmyFormat.parse(lastRecdt1))), ymdFormat.parse(tDate));
                    }
                }
                tempList = em.createNativeQuery("select ifnull(max(cramt),0) from loan_recon where acno='" + accountNumber + "' and dt='" + lastRecdt + "'").getResultList();
                if (!tempList.isEmpty()) {
                    ele = (Vector) tempList.get(0);
                    if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                        lastRecAmt = Double.parseDouble(ele.get(0).toString());
                    }
                }

                //New code as per modify army bank   bnkIdenty 
                if (overdueParameter.equalsIgnoreCase("Y")) {
                    List dueList = em.createNativeQuery("select ifnull(min(DUEDT),(select ifnull(max(DUEDT),'') from emidetails where acno = '" + accountNumber + "' and duedt<='" + tDate + "')) from emidetails where acno = '" + accountNumber + "' and duedt<='" + tDate + "' and status = 'Unpaid'").getResultList();
                    if (!dueList.isEmpty()) {
                        Vector dueVector = (Vector) dueList.get(0);
                        dueDt = dueVector.get(0).toString();
                        if (!dueDt.equalsIgnoreCase("")) {
                            dueDt = ymdFormat.format(y_m_dFormat.parse(dueDt));
                        }
                    }
                    tempList = em.createNativeQuery("select grace_period_for_late_fee_days from cbs_scheme_loan_interest_details a,"
                            + "(SELECT ifnull(SCHEME_CODE,'CA001') schemeCode FROM cbs_loan_acc_mast_sec  WHERE ACNO = '" + accountNumber + "')b "
                            + "where a.scheme_code = b.schemeCode").getResultList();
                    if (!tempList.isEmpty()) {
                        ele = (Vector) tempList.get(0);
                        if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                            gracePeriod = Integer.parseInt(ele.get(0).toString());
                        }
                    }
                }
                //New code for Account Status
                String accStatus = interBranchFacade.fnGetLoanStatusOfAccountTillDate(accountNumber, tDate);
                if (IdealInstPaid >= ActualInstPaid) {
                    tempList = em.createNativeQuery("select ifnull(sum(ifnull(installamt,0)),0) from emidetails where acno = '" + accountNumber + "' and sno <='" + IdealInstPaid + "' and sno>'" + ActualInstPaid + "'").getResultList();
                    if (!tempList.isEmpty()) {
                        ele = (Vector) tempList.get(0);
                        if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                            amountOverd = Double.parseDouble(ele.get(0).toString());
                            emiOverdueAmt = Double.parseDouble(ele.get(0).toString()) - excess;
                        } else {
                            amountOverd = 0;
                        }
                    }
                    installmentoverdue = IdealInstPaid - ActualInstPaid;
                } else {
                    installmentoverdue = 0;
                }
                if ((amountOverd - excess) > Math.abs(balance)) {
                    amountOverdue = Math.abs(balance);
                } else if (balance > 0) {
                    amountOverdue = 0;
                } else if (balance < 0 && amountOverd == 0) {
                    amountOverdue = Math.abs(balance);
                } else {
                    amountOverdue = amountOverd - excess;
                }
                actualInstallmentNo = emiOverdueAmt / installmentamt;
                boolean isTrue = false;
                if (sector.equalsIgnoreCase("A/Cs Whose Plan Has Finished")) {
                    isTrue = true;
                } else if (installmentoverdue >= emiPendingfrom && installmentoverdue <= emiPendingto) {
                    isTrue = true;
                }
                String morOverDue = null;
                if (morOnOf == 1) {
                    if (mapMor != null) {
                        morOverDue = mapMor.get(accountNumber);
                        if (morOverDue != null) {
                            isTrue = true;
                        }
                    }
                }
                if (isTrue) {
                    List list = new ArrayList();
                    String surety1Id = "", surety1Name = "", surety1Add = "";
                    String surety2Id = "", surety2Name = "", surety2Add = "";
                    String surety3Id = "", surety3Name = "", surety3Add = "";
                    if (overDueFlag == 1) {
                        list = em.createNativeQuery("select name, ifnull(GAR_CUSTID,''),address from loan_guarantordetails where acno = '" + accountNumber + "'").getResultList();
                    }
                    if (!list.isEmpty()) {
                        for (int i = 0; i < list.size(); i++) {
                            Vector suretyVector = (Vector) list.get(i);
                            if (i == 0) {
                                surety1Name = suretyVector.get(0).toString();
                                surety1Id = suretyVector.get(1).toString();
                                surety1Add = suretyVector.get(2).toString();
                            } else if (i == 1) {
                                surety2Name = suretyVector.get(0).toString();
                                surety2Id = suretyVector.get(1).toString();
                                surety2Add = suretyVector.get(2).toString();
                            } else if (i == 2) {
                                surety3Name = suretyVector.get(0).toString();
                                surety3Id = suretyVector.get(1).toString();
                                surety3Add = suretyVector.get(2).toString();
                            }
                        }
                    }
                    List list1 = new ArrayList();
                    list1 = em.createNativeQuery("select sum(a.dramt) from "
                            + " (select ifnull(sum(ifnull(dramt,0)),0) as dramt from loan_recon where acno = '" + accountNumber + "' "
                            + " and (details like '%EMI PENAL%' OR details like '%OVERLIMIT PENAL%' OR details like '%SECURITY PENAL%' OR details like '%STOCK PENAL%') "
                            + " union all "
                            + " select ifnull(sum(ifnull(dramt,0)),0) as dramt from npa_recon where  acno = '" + accountNumber + "' "
                            + " and (details like '%EMI PENAL%' OR details like '%OVERLIMIT PENAL%' OR details like '%SECURITY PENAL%' OR details like '%STOCK PENAL%')) a").getResultList();
                    if (!list1.isEmpty()) {
                        Vector ele1 = (Vector) list1.get(0);
                        penalIntCharged = Double.parseDouble(ele1.get(0).toString());
                    }
                    if (overdueParameter.equalsIgnoreCase("N")) {
                        OverdueEmiReportPojo pojo = new OverdueEmiReportPojo();
                        pojo.setAccountNumber(accountNumber);
                        pojo.setCustState(custState);
                        pojo.setBranchState(brnState);
                        if (morOverDue == null) {
                            pojo.setAmountOverdue(BigDecimal.valueOf(amountOverdue));
                            pojo.setMoratoriumFlag("A");
                        } else {
                            pojo.setAmountOverdue(new BigDecimal(morOverDue));
                            pojo.setMoratoriumFlag("M");
                        }
                        pojo.setBalance(BigDecimal.valueOf(balance));
                        pojo.setOutstandingBalance(BigDecimal.valueOf(balance));
                        pojo.setPenalIntCharged(new BigDecimal(penalIntCharged));
                        pojo.setBranchAddress(bnkAddress);
                        pojo.setBankName(bnkName);
                        pojo.setCustName(custName);
                        pojo.setInstallmentamt(BigDecimal.valueOf(installmentamt));
                        pojo.setLastRecDt(lastRecdt1);
                        pojo.setCurrentStatusNoOfDays(dayDiff);
                        if (overDueFlag == 0) {
                            pojo.setNoOfEmiOverdue(installmentoverdue);
                        } else {
                            pojo.setNoOfActualEmiOverdue(actualInstallmentNo);
                        }
                        pojo.setFlag(String.valueOf(overDueFlag));
                        pojo.setRepaymentAmt(BigDecimal.valueOf(lastRecAmt));
                        pojo.setReportDate(reportDate);
                        pojo.setSanctionDate(sanctionDate);
                        pojo.setSanctionedamt(BigDecimal.valueOf(sanctionedamt));
                        pojo.setSector(sector);
                        pojo.setAccStatus(accStatus);
                        pojo.setMoratoriumOnOf(String.valueOf(morOnOf));

                        pojo.setSurety1Name(surety1Name);
                        pojo.setSurety1Id(surety1Id);
                        pojo.setSurety1Add(surety1Add);

                        pojo.setSurety2Name(surety2Name);
                        pojo.setSurety2Id(surety2Id);
                        pojo.setSurety2Add(surety2Add);

                        pojo.setSurety3Name(surety3Name);
                        pojo.setSurety3Id(surety3Id);
                        pojo.setSurety3Add(surety3Add);
                        pojo.setCustAdd(custAdd);
                        pojo.setCustomerId(custId);

                        returnList.add(pojo);
                    } else {
                        if (dueDt.equalsIgnoreCase("") || lastRecdt.equalsIgnoreCase("")) {
                            OverdueEmiReportPojo pojo = new OverdueEmiReportPojo();
                            pojo.setAccountNumber(accountNumber);
                            pojo.setCustState(custState);
                            pojo.setBranchState(brnState);
                            if (morOverDue == null) {
                                pojo.setAmountOverdue(BigDecimal.valueOf(amountOverdue));
                                pojo.setMoratoriumFlag("A");
                            } else {
                                pojo.setAmountOverdue(new BigDecimal(morOverDue));
                                pojo.setMoratoriumFlag("M");
                            }
                            pojo.setBalance(BigDecimal.valueOf(balance));
                            pojo.setOutstandingBalance(BigDecimal.valueOf(balance));
                            pojo.setPenalIntCharged(new BigDecimal(penalIntCharged));
                            pojo.setBranchAddress(bnkAddress);
                            pojo.setBankName(bnkName);
                            pojo.setCustName(custName);
                            pojo.setInstallmentamt(BigDecimal.valueOf(installmentamt));
                            pojo.setLastRecDt(lastRecdt1);
                            pojo.setCurrentStatusNoOfDays(dayDiff);
                            if (overDueFlag == 0) {
                                pojo.setNoOfEmiOverdue(installmentoverdue);
                            } else {
                                pojo.setNoOfActualEmiOverdue(actualInstallmentNo);
                            }
                            pojo.setFlag(String.valueOf(overDueFlag));
                            pojo.setRepaymentAmt(BigDecimal.valueOf(lastRecAmt));
                            pojo.setReportDate(reportDate);
                            pojo.setSanctionDate(sanctionDate);
                            pojo.setSanctionedamt(BigDecimal.valueOf(sanctionedamt));
                            pojo.setSector(sector);
                            pojo.setAccStatus(accStatus);
                            if (dueDt.equalsIgnoreCase("")) {
                                pojo.setDueDate("");
                            } else {
                                pojo.setDueDate(dmyFormat.format(ymdFormat.parse(dueDt)));
                            }
                            pojo.setMoratoriumOnOf(String.valueOf(morOnOf));

                            pojo.setSurety1Name(surety1Name);
                            pojo.setSurety1Id(surety1Id);
                            pojo.setSurety1Add(surety1Add);

                            pojo.setSurety2Name(surety2Name);
                            pojo.setSurety2Id(surety2Id);
                            pojo.setSurety2Add(surety2Add);

                            pojo.setSurety3Name(surety3Name);
                            pojo.setSurety3Id(surety3Id);
                            pojo.setSurety3Add(surety3Add);
                            pojo.setCustAdd(custAdd);
                            pojo.setCustomerId(custId);
                            returnList.add(pojo);
                        } else {
                            graceDt = CbsUtil.dateAdd(dueDt, gracePeriod);
                            OverdueEmiReportPojo pojo = new OverdueEmiReportPojo();
                            if (ymdFormat.parse(graceDt).getTime() <= ymdFormat.parse(lastRecdt).getTime()) {
                                pojo.setAccountNumber(accountNumber);
                                pojo.setCustState(custState);
                                pojo.setBranchState(brnState);
                                if (morOverDue == null) {
                                    pojo.setAmountOverdue(BigDecimal.valueOf(amountOverdue));
                                    pojo.setMoratoriumFlag("A");
                                } else {
                                    pojo.setAmountOverdue(new BigDecimal(morOverDue));
                                    pojo.setMoratoriumFlag("M");
                                }
                                pojo.setBalance(BigDecimal.valueOf(balance));
                                pojo.setOutstandingBalance(BigDecimal.valueOf(balance));
                                pojo.setPenalIntCharged(new BigDecimal(penalIntCharged));
                                pojo.setBranchAddress(bnkAddress);
                                pojo.setBankName(bnkName);
                                pojo.setCustName(custName);
                                pojo.setInstallmentamt(BigDecimal.valueOf(installmentamt));
                                pojo.setLastRecDt(lastRecdt1);
                                pojo.setCurrentStatusNoOfDays(dayDiff);
                                if (overDueFlag == 0) {
                                    pojo.setNoOfEmiOverdue(installmentoverdue);
                                } else {
                                    pojo.setNoOfActualEmiOverdue(actualInstallmentNo);
                                }
                                pojo.setFlag(String.valueOf(overDueFlag));
                                pojo.setRepaymentAmt(BigDecimal.valueOf(lastRecAmt));
                                pojo.setReportDate(reportDate);
                                pojo.setSanctionDate(sanctionDate);
                                pojo.setSanctionedamt(BigDecimal.valueOf(sanctionedamt));
                                pojo.setSector(sector);
                                pojo.setAccStatus(accStatus);
                                if (dueDt.equalsIgnoreCase("")) {
                                    pojo.setDueDate("");
                                } else {
                                    pojo.setDueDate(dmyFormat.format(ymdFormat.parse(dueDt)));
                                }
                                pojo.setMoratoriumOnOf(String.valueOf(morOnOf));

                                pojo.setSurety1Name(surety1Name);
                                pojo.setSurety1Id(surety1Id);
                                pojo.setSurety1Add(surety1Add);

                                pojo.setSurety2Name(surety2Name);
                                pojo.setSurety2Id(surety2Id);
                                pojo.setSurety2Add(surety2Add);

                                pojo.setSurety3Name(surety3Name);
                                pojo.setSurety3Id(surety3Id);
                                pojo.setSurety3Add(surety3Add);
                                pojo.setCustAdd(custAdd);
                                pojo.setCustomerId(custId);
                                returnList.add(pojo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public List getOverDueNonEmi(String acNature, String acType, String transDt, String brCode, String withOutNpa) throws ApplicationException {
        /*withoutNpa parameter is used for the OSS3 report Overdue parameter. It is by default N(For all the accounts) and Y for the Standard Account Only*/
        List<OverdueNonEmiResultList> overDueResultLst = new ArrayList<OverdueNonEmiResultList>();
        OverdueNonEmiResultList overDueObject;
        String acno, custname, opeingDt;
        List selectBankDtl = new ArrayList();
        List getAcDtl = new ArrayList();
        List list3 = new ArrayList();
        List list4 = new ArrayList();
        List limitList = null;
        Vector limitVec = null;
        long dayDiff = 0;
        try {
            if (brCode.equalsIgnoreCase("0A")) {
                selectBankDtl = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where b.alphacode=br.alphacode and "
                        + "br.brncode= cast('90' as unsigned) ").getResultList();
            } else {
                selectBankDtl = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where b.alphacode=br.alphacode and "
                        + "br.brncode= cast('" + brCode + "' as unsigned) ").getResultList();
            }
            Vector vecBankDtl = (Vector) selectBankDtl.get(0);
            String bankName = vecBankDtl.get(0).toString();
            String bankAddress = vecBankDtl.get(1).toString();
            String npaQuery = "", npaQuery1 = "";
            String oss3DtForOverDue = "20990101";
            if (withOutNpa.equalsIgnoreCase("Y")) {
                List oss3OD = common.getCode("OSS3_OVERDUE_DT");
                if (!oss3OD.isEmpty()) {
                    if (!oss3OD.get(0).toString().equalsIgnoreCase("")) {
                        oss3DtForOverDue = oss3OD.get(0).toString();
                    }
                }
                if (ymdFormat.parse(transDt).after(ymdFormat.parse(oss3DtForOverDue))) {
                    npaQuery = " and acno not in (select a.acno from accountstatus a,(select acno,max(effdt) as edt from accountstatus where effdt <='" + transDt + "' group by acno) b,"
                            + " (select acno,max(spno) as sno from accountstatus where effdt <='" + transDt + "' group by acno) c , accountmaster ac "
                            + " where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and a.effdt = b.edt and a.spno = c.sno and a.spflag in (11,12,13) "
                            + " and a.effdt <='" + transDt + "' and (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + transDt + "') ) ";
                    npaQuery1 = " and a.acno not in (select a.acno from accountstatus a,(select acno,max(effdt) as edt from accountstatus where effdt <='" + transDt + "' group by acno) b,"
                            + " (select acno,max(spno) as sno from accountstatus where effdt <='" + transDt + "' group by acno) c , accountmaster ac "
                            + " where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and a.effdt = b.edt and a.spno = c.sno and a.spflag in (11,12,13) "
                            + " and a.effdt <='" + transDt + "' and (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + transDt + "') ) ";
                }
            }
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List acTypeQuery;
                /*This code is added for Priority Sector(Yearly) reports in case of CA-ALL*/
                if (acType.equalsIgnoreCase("ALL")) {
                    acTypeQuery = em.createNativeQuery("select Acctcode From accounttypemaster where acctNature in ('" + acNature + "') and CrDbFlag in('B','D') ").getResultList();
                } else {
                    acTypeQuery = em.createNativeQuery("select Acctcode From accounttypemaster where acctcode in ('" + acType + "') and CrDbFlag in('B','D') ").getResultList();
                }
                if (acTypeQuery.size() > 0) {
                    for (int z = 0; z < acTypeQuery.size(); z++) {
                        Vector vecAcType = (Vector) acTypeQuery.get(z);
                        acType = vecAcType.get(0).toString();
                        List selProdCode = em.createNativeQuery("select productcode from accounttypemaster where acctcode = '" + acType + "'").getResultList();
                        if (!(selProdCode.isEmpty())) {
                            Vector vecProCode = (Vector) selProdCode.get(0);
                            String prodCode = vecProCode.get(0).toString();
                            if (prodCode.equalsIgnoreCase("DP")) {
                                List getOvrDDays = em.createNativeQuery("select code from parameterinfo_report where reportname = 'CCOD_OVERDUE_DAYS'").getResultList();
                                String overDueDays = "";
                                if (getOvrDDays.isEmpty()) {
                                    overDueDays = "90";
                                } else {
                                    Vector vecOvrDDay = (Vector) getOvrDDays.get(0);
                                    overDueDays = vecOvrDDay.get(0).toString();
                                }
                                if (brCode.equalsIgnoreCase("0A")) {
                                    getAcDtl = em.createNativeQuery("select acno,custname, ifnull(date_format(OpeningDt,'%Y%m%d'),'19000101') from accountmaster where (closingdate is null or closingdate ='' or closingdate >= '" + transDt + "') and accttype='" + acType + "' " + npaQuery + " order by acno ").getResultList();
                                } else {
                                    getAcDtl = em.createNativeQuery("select acno,custname, ifnull(date_format(OpeningDt,'%Y%m%d'),'19000101') from accountmaster where (closingdate is null or closingdate ='' or closingdate >= '" + transDt + "') and accttype='" + acType + "'  " + npaQuery + " and curbrcode='" + brCode + "'order by acno ").getResultList();
                                }

                                for (int i = 0; i < getAcDtl.size(); i++) {
                                    Vector AcLst = (Vector) getAcDtl.get(i);
                                    acno = AcLst.get(0).toString();
                                    custname = AcLst.get(1).toString();
                                    opeingDt = AcLst.get(2).toString();
                                    float Limit = 0;
                                    float Balance = 0;
                                    int CountVar = 0;
                                    int Tran = 0;
                                    float Overdue = 0;
                                    List getLoanDtl = em.createNativeQuery("select ifnull(DATE_FORMAT(a.sanctionlimitdt,'%Y%m%d'),'19000101'), ifnull(DATE_FORMAT(b.renewal_date,'%Y%m%d'),'19000101'),"
                                            + "ifnull(a.odlimit,0),ifnull(a.adhoclimit,0),ifnull(DATE_FORMAT(a.adhocapplicabledt,'%Y%m%d'),'19000101'),"
                                            + "ifnull(DATE_FORMAT(a.adhocexpiry,'%Y%m%d'),'19000101') from loan_appparameter a,cbs_loan_borrower_details b where a.acno=b.acc_no and a.acno= '"
                                            + acno + "'").getResultList();
                                    if (!getLoanDtl.isEmpty()) {
                                        Vector vecLoanDtl = (Vector) getLoanDtl.get(0);
                                        String renwalDt = vecLoanDtl.get(0).toString();
                                        String nxtRenwalDt = vecLoanDtl.get(1).toString();
                                        float odLimit = Float.parseFloat(vecLoanDtl.get(2).toString());
                                        float adhocLimit = Float.parseFloat(vecLoanDtl.get(3).toString());
                                        String adHocAppDt = vecLoanDtl.get(4).toString();
                                        String adHocExp = vecLoanDtl.get(5).toString();
                                        Long nxtRenwalDtL = Long.parseLong(nxtRenwalDt);
                                        Long transDtL = Long.parseLong(transDt);
                                        Long adHocExpDtL = Long.parseLong(adHocExp);
                                        Long adHocAppDtL = Long.parseLong(adHocAppDt);
                                        List sanctionLimitDtList = em.createNativeQuery("select ifnull(DATE_FORMAT(SanctionLimitDt,'%Y%m%d'),'19000101'), acLimit,adhoclimit,ifnull(DATE_FORMAT(AdhocApplicableDt,'%Y%m%d'),'19000101'),"
                                                + "ifnull(DATE_FORMAT(adhoctilldt,'%Y%m%d'),'19000101') from loan_oldinterest "
                                                + "where acno =  '" + acno + "' and txnid = "
                                                + "(select min(TXNID) from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + transDt + "' )").getResultList();
                                        if (!sanctionLimitDtList.isEmpty()) {
                                            Vector vist = (Vector) sanctionLimitDtList.get(0);
                                            opeingDt = vist.get(0).toString();
                                            odLimit = Float.parseFloat(vist.get(1).toString());
                                            adhocLimit = 0;
                                            adHocAppDt = vist.get(3).toString();
                                            adHocExp = vist.get(4).toString();
                                            if (!ymdFormat.parse(transDt).before(ymdFormat.parse(adHocAppDt))
                                                    && !ymdFormat.parse(transDt).after(ymdFormat.parse(adHocExp))) {
                                                adhocLimit = Float.parseFloat(vist.get(2).toString());
                                            }
                                        }
                                        if (nxtRenwalDtL.compareTo(transDtL) > 0 || nxtRenwalDtL.compareTo(transDtL) == 0) {
                                            String LastDt = ymdFormat.format(date);
                                            List getBalLoan = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)-ifnull(dramt,0)),0) from ca_recon where acno = '" + acno
                                                    + "' and dt < date_add('" + transDt + "', interval " + overDueDays + " day) and auth='y'").getResultList();
                                            Vector vecBalLoan = (Vector) getBalLoan.get(0);
                                            Balance = Float.parseFloat(vecBalLoan.get(0).toString());
                                            List getReconDtl = em.createNativeQuery("select acno,dt,ifnull(sum(ifnull(cramt,0)-ifnull(dramt,0)),0) from ca_recon where acno ='" + acno
                                                    + "' and dt<='" + transDt + "' and dt >= date_add('" + transDt + "', interval " + overDueDays + " day) group by acno,dt order "
                                                    + "by acno, dt ").getResultList();
                                            for (int j = 0; j < getReconDtl.size(); j++) {
                                                Vector reconLst = (Vector) getReconDtl.get(j);
                                                String acno1 = reconLst.get(0).toString();
                                                String dt1 = ymdFormat.format(ymdFormat.parse(reconLst.get(1).toString()));
                                                float Balance1 = Float.parseFloat(reconLst.get(2).toString());
                                                Tran = Tran + 1;
                                                Long dt1L = Long.parseLong(dt1);
                                                if ((dt1L.compareTo(adHocExpDtL) < 0 || dt1L.compareTo(adHocExpDtL) == 0) && (dt1L.compareTo(adHocAppDtL) > 0 || dt1L.compareTo(adHocAppDtL) == 0)) {
                                                    if (Math.abs(Balance) > (odLimit + adhocLimit)) {
                                                        CountVar = CountVar + 1;
                                                    }
                                                } else if (Math.abs(Balance) > (odLimit)) {
                                                    CountVar = CountVar + 1;
                                                }
                                                Balance = Balance + Balance1;
                                                LastDt = dt1;
                                            }
                                            Long LastDtL = Long.parseLong(LastDt);
                                            while ((LastDtL.compareTo(transDtL) < 0) && (Tran == CountVar)) {
                                                Tran = Tran + 1;
                                                if ((LastDtL.compareTo(adHocExpDtL) < 0 || LastDtL.compareTo(adHocExpDtL) == 0) && (LastDtL.compareTo(adHocAppDtL) > 0 || LastDtL.compareTo(adHocAppDtL) == 0)) {
                                                    if (Math.abs(Balance) > (odLimit + adhocLimit)) {
                                                        CountVar = CountVar + 1;
                                                    }
                                                } else if (Math.abs(Balance) > (odLimit)) {
                                                    CountVar = CountVar + 1;
                                                }
                                                LastDt = CbsUtil.dateAdd(LastDt, 1);
                                            }
                                            if (CountVar == Tran) {
                                                if (Math.abs(Balance) > (odLimit)) {
                                                    Long DateChk = Long.parseLong(CbsUtil.dateAdd(transDt, Integer.parseInt(overDueDays)));
                                                    if ((adHocExpDtL.compareTo(transDtL) > 0 || LastDtL.compareTo(adHocExpDtL) == 0) && (adHocAppDtL.compareTo(DateChk) < 0 || adHocAppDtL.compareTo(DateChk) == 0)) {
                                                        Overdue = Math.abs(Balance) - (odLimit + adhocLimit);
                                                        Limit = (odLimit + adhocLimit);
                                                    } else {
                                                        Overdue = (Math.abs(Balance) - odLimit);
                                                        Limit = odLimit;
                                                    }
                                                }
                                            } else {
                                                Overdue = 0;
                                                Limit = odLimit;
                                            }
                                        } else {
                                            float balance2;
                                            List list1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)-ifnull(dramt,0)),0)  from ca_recon where acno = '" + acno
                                                    + "' and dt<= '" + transDt + "' and auth='Y'").getResultList();
                                            Vector element1 = (Vector) list1.get(0);
                                            balance2 = Float.parseFloat(element1.get(0).toString());
                                            Balance = balance2;
                                            Limit = odLimit;
                                            if (Math.abs(Balance) > odLimit) {
                                                if (adHocExpDtL.compareTo(transDtL) >= 0) {
                                                    Overdue = Math.abs(Balance) - (odLimit + adhocLimit);
                                                } else {
                                                    Overdue = Math.abs(Balance) - odLimit;
                                                }
                                            }
                                        }
                                        if (Overdue > 0 && Balance < 0) {
                                            String sanctionDt;
                                            List list2 = em.createNativeQuery("select ifnull(b.adhoclimit,0), ifnull(DATE_FORMAT(b.sanctionlimitdt,'%d/%m/%Y'),'01/01/1900') "
                                                    + "from loan_appparameter b where b.acno= '" + acno + "'").getResultList();
                                            Vector element2 = (Vector) list2.get(0);
                                            adhocLimit = Float.parseFloat(element2.get(0).toString());
                                            // lastBalConfirmDt1 = element2.get(1).toString();
                                            sanctionDt = element2.get(1).toString();

                                            limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                                    + "ca_recon where acno = '" + acno + "' and cramt > 0 and dt <= '" + transDt + "'").getResultList();
                                            limitVec = (Vector) limitList.get(0);
                                            String lastCrDt = limitVec.get(0).toString();

                                            dayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt.equalsIgnoreCase("19000101") ? opeingDt : lastCrDt), ymdFormat.parse(transDt));

                                            overDueObject = new OverdueNonEmiResultList();
                                            overDueObject.setBankName(bankName);
                                            overDueObject.setBankAddress(bankAddress);
                                            overDueObject.setAccountNo(acno);
                                            overDueObject.setCustName(custname);
                                            overDueObject.setLimitBal(Limit);
                                            overDueObject.setAdhocLimitBal(adhocLimit);
                                            overDueObject.setBalance(new BigDecimal(Balance));
                                            overDueObject.setOverDue(Overdue);
                                            overDueObject.setRenwalDt(dmyFormat.format(ymdFormat.parse(renwalDt)));
                                            overDueObject.setSanctionDt(sanctionDt.equalsIgnoreCase("01/01/1900") ? dmyFormat.format(ymdFormat.parse(opeingDt)) : sanctionDt);
                                            overDueObject.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                            overDueObject.setCurrentStatusNoOfDays(dayDiff);
                                            overDueResultLst.add(overDueObject);

                                        }
                                    }
                                }
                            } else {
                                float odLimit, Overdue;
                                BigDecimal Balance;
                                if (brCode.equalsIgnoreCase("0A")) {
                                    list3 = em.createNativeQuery("select a.acno, a.custname, a.odlimit,"
                                            + "round(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)),2), ifnull(date_format(a.OpeningDt,'%Y%m%d'),'19000101') from ca_recon r,accountmaster a "
                                            + "where a.acno in (select ca.acno from ca_recon ca,accountmaster am where ca.acno=am.acno and am.accttype = '" + acType + "' "
                                            + " and dt <= '" + transDt + "' and ty = 1) and a.acno=r.acno "
                                            + "and r.dt <= '" + transDt + "' and (ifnull(a.closingdate,'')='' or a.closingdate> '" + transDt + "') " + npaQuery1 + " "
                                            + "group by a.acno,a.custname,a.odlimit having "
                                            + "(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0))) < 0 order by a.acno").getResultList();
                                } else {
                                    list3 = em.createNativeQuery("select a.acno, a.custname, a.odlimit,"
                                            + "cast(round(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)),2) as decimal(25,2)), ifnull(date_format(a.OpeningDt,'%Y%m%d'),'19000101') from ca_recon r,accountmaster a "
                                            + "where a.acno in (select ca.acno from ca_recon ca,accountmaster am where ca.acno=am.acno and am.accttype = '" + acType + "' "
                                            + "and am.curbrcode='" + brCode + "' and dt <= '" + transDt + "' and ty = 1) and a.acno=r.acno "
                                            + "and r.dt <= '" + transDt + "'  and (ifnull(a.closingdate,'')='' or a.closingdate> '" + transDt + "') " + npaQuery1 + " "
                                            + "group by a.acno,a.custname,a.odlimit having "
                                            + "(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0))) < 0 order by a.acno").getResultList();
                                }

                                if (!list3.isEmpty()) {
                                    for (int i = 0; i < list3.size(); i++) {
                                        Vector element3 = (Vector) list3.get(i);
                                        acno = element3.get(0).toString();
                                        custname = element3.get(1).toString();
                                        odLimit = Float.parseFloat(nft.format(element3.get(2)).toString());
                                        Balance = new BigDecimal(nft.format(element3.get(3)).toString());
                                        opeingDt = element3.get(4).toString();
                                        //   if (Math.abs(Balance) != 0 && (Math.abs(odLimit) < Math.abs(Balance)) && odLimit != 0) {
                                        limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                                + "ca_recon where acno = '" + acno + "' and cramt > 0 and dt <= '" + transDt + "'").getResultList();
                                        limitVec = (Vector) limitList.get(0);
                                        String lastCrDt = limitVec.get(0).toString();
                                        List getLoanDtl = em.createNativeQuery("select ifnull(DATE_FORMAT(a.sanctionlimitdt,'%Y%m%d'),'19000101'), ifnull(DATE_FORMAT(b.renewal_date,'%Y%m%d'),'19000101'),"
                                                + "ifnull(a.odlimit,0),ifnull(a.adhoclimit,0),ifnull(DATE_FORMAT(a.adhocapplicabledt,'%Y%m%d'),'19000101'),"
                                                + "ifnull(DATE_FORMAT(a.adhocexpiry,'%Y%m%d'),'19000101') from loan_appparameter a,cbs_loan_borrower_details b where a.acno=b.acc_no and a.acno= '"
                                                + acno + "'").getResultList();
                                        if (!getLoanDtl.isEmpty()) {
                                            Vector vecLoanDtl = (Vector) getLoanDtl.get(0);
                                            String renwalDt = vecLoanDtl.get(0).toString();
                                            String nxtRenwalDt = vecLoanDtl.get(1).toString();
                                            float adhocLimit = Float.parseFloat(vecLoanDtl.get(3).toString());
                                            String adHocAppDt = vecLoanDtl.get(4).toString();
                                            String adHocExp = vecLoanDtl.get(5).toString();
                                            Long nxtRenwalDtL = Long.parseLong(nxtRenwalDt);
                                            String transDtL = (transDt);
                                            Long adHocExpDtL = Long.parseLong(adHocExp);
                                            Long adHocAppDtL = Long.parseLong(adHocAppDt);
                                            List sanctionLimitDtList = em.createNativeQuery("select ifnull(DATE_FORMAT(SanctionLimitDt,'%Y%m%d'),'19000101'), acLimit,adhoclimit,ifnull(DATE_FORMAT(AdhocApplicableDt,'%Y%m%d'),'19000101'),"
                                                    + "ifnull(DATE_FORMAT(adhoctilldt,'%Y%m%d'),'19000101') from loan_oldinterest "
                                                    + "where acno =  '" + acno + "' and txnid = "
                                                    + "(select min(TXNID) from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + transDt + "' )").getResultList();
                                            if (!sanctionLimitDtList.isEmpty()) {
                                                Vector vist = (Vector) sanctionLimitDtList.get(0);
                                                opeingDt = vist.get(0).toString();
                                                odLimit = Float.parseFloat(vist.get(1).toString());
                                                adhocLimit = 0;
                                                adHocAppDt = vist.get(3).toString();
                                                adHocExp = vist.get(4).toString();
                                                if (!ymdFormat.parse(transDt).before(ymdFormat.parse(adHocAppDt))
                                                        && !ymdFormat.parse(transDt).after(ymdFormat.parse(adHocExp))) {
                                                    adhocLimit = Float.parseFloat(vist.get(2).toString());
                                                }
                                            }
                                            dayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt.equalsIgnoreCase("19000101") ? opeingDt : lastCrDt), ymdFormat.parse(transDt));

                                            if (Math.abs(Float.parseFloat(Balance.toString())) != 0) {
                                                if (Math.abs(odLimit + adhocLimit) < Math.abs(Float.parseFloat(Balance.toString()))) {
                                                    Overdue = Math.abs(Float.parseFloat(Balance.toString())) - Math.abs(odLimit + adhocLimit);
                                                    overDueObject = new OverdueNonEmiResultList();
                                                    overDueObject.setBankName(bankName);
                                                    overDueObject.setBankAddress(bankAddress);
                                                    overDueObject.setAccountNo(acno);
                                                    overDueObject.setCustName(custname);
                                                    overDueObject.setLimitBal(odLimit);
                                                    overDueObject.setAdhocLimitBal(adhocLimit);
                                                    overDueObject.setBalance(Balance);
                                                    overDueObject.setOverDue(Overdue);
                                                    overDueObject.setRenwalDt(renwalDt.equalsIgnoreCase("19000101") ? "" : dmyFormat.format(ymdFormat.parse(renwalDt)));
                                                    overDueObject.setSanctionDt(opeingDt.equalsIgnoreCase("19000101") ? "" : dmyFormat.format(ymdFormat.parse(opeingDt)));
                                                    overDueObject.setLastCrDate(lastCrDt.equalsIgnoreCase("19000101") ? "" : dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                                    overDueObject.setCurrentStatusNoOfDays(dayDiff);
                                                    overDueResultLst.add(overDueObject);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            //same
                        }
                    }
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                float sanctionLimit;
                double Balance, Overdue;
                String acTypeQuery = "";
                if (acType.equalsIgnoreCase("ALL")) {
                    acTypeQuery = " (select Acctcode From accounttypemaster where acctNature in('" + acNature + "') and CrDbFlag in('B','D')) ";
                } else {
                    acTypeQuery = " (select Acctcode From accounttypemaster where acctcode in ('" + acType + "') and CrDbFlag in('B','D')) ";
                }
                if (brCode.equalsIgnoreCase("0A")) {
                    list4 = em.createNativeQuery("select la.acno,la.custname, sanctionlimit "
                            + "from loan_recon lr, loan_appparameter la, "
                            + "accountmaster a where a.accttype in " + acTypeQuery + " and la.acno = lr.acno "
                            + "and la.acno= a.acno and a.openingdt <= '" + transDt + "' "
                            + "and (ifnull(a.closingdate,'')='' or a.closingdate> '" + transDt + "') and lr.dt <= '" + transDt + "' "
                            + " and a.acno not in (select distinct acno from emidetails)  " + npaQuery1 + "  group by la.acno,la.sanctionlimit,"
                            + "la.custname order by la.acno").getResultList();
                } else {
                    list4 = em.createNativeQuery("select la.acno,la.custname, sanctionlimit "
                            + "from loan_recon lr, loan_appparameter la, "
                            + "accountmaster a where a.accttype in " + acTypeQuery + " and la.acno = lr.acno "
                            + "and la.acno= a.acno and a.openingdt <= '" + transDt + "' and a.curbrcode = '" + brCode + "' "
                            + "and (ifnull(a.closingdate,'')='' or a.closingdate> '" + transDt + "') and lr.dt <= '" + transDt + "' "
                            + " and a.acno not in (select distinct acno from emidetails)  " + npaQuery1 + "  group by la.acno,la.sanctionlimit,"
                            + "la.custname order by la.acno").getResultList();
                }

                if (!list4.isEmpty()) {
                    for (int j = 0; j < list4.size(); j++) {
                        Vector element4 = (Vector) list4.get(j);
                        acno = element4.get(0).toString();
                        custname = element4.get(1).toString();
                        sanctionLimit = Float.parseFloat(element4.get(2).toString());

                        limitList = em.createNativeQuery("select ifnull(max(date_format(a.dt,'%Y%m%d')),'19000101'), ifnull(date_format(b.OpeningDt,'%Y%m%d'),'19000101') from loan_recon a, accountmaster b "
                                + "where a.acno = b.acno and a.acno = '" + acno + "' and a.cramt > 0 and a.dt <= '" + transDt + "'").getResultList();
                        limitVec = (Vector) limitList.get(0);
                        String lastCrDt = limitVec.get(0).toString();
                        opeingDt = limitVec.get(1).toString();

                        dayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt.equalsIgnoreCase("19000101") ? opeingDt : lastCrDt), ymdFormat.parse(transDt));

                        Balance = loanRemote.fnBalosForAccountAsOn(acno, transDt);
                        if (Balance >= 0) {
                            continue;
                        } else {
                            if (Math.abs(Balance) != 0 && (Math.abs(Balance) > Math.abs(sanctionLimit)) && sanctionLimit != 0) {
                                Overdue = Math.abs(Balance) - Math.abs(sanctionLimit);
                                overDueObject = new OverdueNonEmiResultList();
                                overDueObject.setBankName(bankName);
                                overDueObject.setBankAddress(bankAddress);
                                overDueObject.setAccountNo(acno);
                                overDueObject.setCustName(custname);
                                overDueObject.setLimitBal(sanctionLimit);
                                overDueObject.setBalance(new BigDecimal(Balance));
                                overDueObject.setOverDue(Overdue);
                                overDueObject.setSanctionDt(opeingDt.equalsIgnoreCase("19000101") ? "" : dmyFormat.format(ymdFormat.parse(opeingDt)));
                                overDueObject.setLastCrDate(lastCrDt.equalsIgnoreCase("19000101") ? "" : dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                overDueObject.setCurrentStatusNoOfDays(dayDiff);
                                overDueResultLst.add(overDueObject);

                            } else if (Math.abs(Balance) < Math.abs(sanctionLimit)) {
                                Overdue = Math.abs(sanctionLimit) - Math.abs(Balance);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return overDueResultLst;
    }

    public List<NpaAccountDetailPojo> getNpaDetail(String acctNature, String acctType1, String tillDate, String brCode, String parameter) throws ApplicationException {
        NumberFormat formatter = new DecimalFormat("#.##");
        /*Parameter =Y default. in case of OSS4 Industry Wise it will be N */
        List<NpaAccountDetailPojo> dataList = new ArrayList<NpaAccountDetailPojo>();
        try {
            List result = null, acTypeList = null, acNatureList;
            Vector tempVector, tempAcNatureVect, provVect;
            List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", acctType1, "19000101", tillDate, "", brCode.equalsIgnoreCase("90") ? "0A" : brCode, "Y");
            List<ProvDetailOfLoanAccounts> provList = rbiRemote.getProvisionAccordingToLoan("SOSS3.PART-A", dmyFormat.format(ymdFormat.parse(tillDate)), brCode, new BigDecimal("1"));
//            String acctNature = fts.getAcNatureByCode(acctType);
            int isExceessEmi = common.isExceessEmi(tillDate);
            List accountList = new ArrayList();
            if (acctNature.equalsIgnoreCase("ALL")) {
                acNatureList = common.getAcctNatureOnlyDB();
                if (!acNatureList.isEmpty()) {
                    acTypeList = em.createNativeQuery("select Acctcode From accounttypemaster "
                            + " where acctNature in "
                            + "(select distinct acctNature From accounttypemaster "
                            + " where acctNature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "') "
                            + " and CrDbFlag in('B','D')) and CrDbFlag in('B','D')").getResultList();
                }
            } else {
                if (acctType1.equalsIgnoreCase("ALL")) {
//                    result = common.getAcctTypeAsAcNatureOnlyDB(acctNature);
                    acTypeList = em.createNativeQuery("select Acctcode From accounttypemaster "
                            + "where acctNature in('" + acctNature + "') and CrDbFlag in('B','D')").getResultList();
                } else {
                    acTypeList = em.createNativeQuery("select Acctcode From accounttypemaster "
                            + "where Acctcode in('" + acctType1 + "') and CrDbFlag in('B','D')").getResultList();
                }
            }
            List<OverdueEmiReportPojo> overdueTlList = new ArrayList<>();
            List<OverdueNonEmiResultList> overdueDlList = new ArrayList<>();
            List<OverdueNonEmiResultList> overdueCaList = new ArrayList<>();
            if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                overdueTlList = getOverdueEmiReport(1, 0, "", "ALL", 0, 5000, tillDate, brCode, "A", false, "", "N");
            } else if (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                overdueDlList = getOverDueNonEmi("DL", "ALL", tillDate, brCode, "N");
            } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                overdueDlList = getOverDueNonEmi("CA", "ALL", tillDate, brCode, "N");
            } else if (acctNature.equalsIgnoreCase("ALL")) {
                overdueTlList = getOverdueEmiReport(1, 0, "", "ALL", 0, 5000, tillDate, brCode, "A", false, "", "N");
                overdueDlList = getOverDueNonEmi("DL", "ALL", tillDate, brCode, "N");
                overdueCaList = getOverDueNonEmi("CA", "ALL", tillDate, brCode, "N");
            }
            for (int n = 0; n < acTypeList.size(); n++) {
                tempVector = (Vector) acTypeList.get(n);
                String acctType = tempVector.get(0).toString();
                for (int i = 0; i < resultList.size(); i++) {
                    if (acctType.equalsIgnoreCase(resultList.get(i).getAcno().substring(2, 4))) {
                        acctNature = resultList.get(i).getAcctNature();
//                        if (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
//                            /**
//                             * It is pending to be done.
//                             */
//                        } else 
                        if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();

//                if (brCode.equalsIgnoreCase("0A")) {
//                    accountList = em.createNativeQuery("select acno,custname,accstatus from accountmaster where (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + tillDate + "') and Accttype = '" + acctType + "' and OpeningDt < '" + tillDate + "' and accstatus in (11,12,13) order by AccStatus, acno").getResultList();
//                } else {
//                    accountList = em.createNativeQuery("select acno,custname,accstatus from accountmaster where (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + tillDate + "') and Accttype = '" + acctType + "' and OpeningDt < '" + tillDate + "'  and accstatus in (11,12,13) and curbrcode = '" + brCode + "' order by AccStatus, acno").getResultList();
//                }
//                if (resultList.isEmpty()) {
//                    throw new ApplicationException("There is no data to mark NPA.");
//                }
//                for (int i = 0; i < resultList.size(); i++) {
                            NpaAccountDetailPojo pojo = new NpaAccountDetailPojo();
//                    Vector element = (Vector) accountList.get(i);
                            String acno = resultList.get(i).getAcno();
                            String custname = resultList.get(i).getCustName();
                            String accStatus = resultList.get(i).getPresentStatus();
                            String secured = resultList.get(i).getSecured();
                            String retirement = resultList.get(i).getRetirementAge();
                            String acctCategory = resultList.get(i).getAcctCategory();
                            String perAdd = "", currAdd = "", contact = "", disbDt = null, fatherName = "";
                            String dob = "", pin = "";
                            BigDecimal lastCrAmt = new BigDecimal("0");
                            double amtDis = 0d;
                            if (accStatus.contains("SUB")) {
                                accStatus = "11";
                            } else if (accStatus.contains("DOU")) {
                                accStatus = "12";
                            } else if (accStatus.contains("LOS")) {
                                accStatus = "13";
                            } else {
                                accStatus = "1";
                            }
//                    BigDecimal outStandBal = new BigDecimal(formatter.format(fnBalosForAccountAsOn(acno, tillDate)*-1));
                            BigDecimal outStandBal = resultList.get(i).getBalance();
                            List limitList = null;
                            Vector limitVec = null;
//                    BigDecimal chkLimit = getCheckLimit(acno);
                            BigDecimal chkLimit = new BigDecimal("0");
                            String sancDate = "";
                            BigDecimal sancAmt = new BigDecimal("0");
                            List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, acLimit from loan_oldinterest "
                                    + "where acno =  '" + acno + "' and txnid = "
                                    + "(select min(TXNID) from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + tillDate + "' )").getResultList();
                            if (!sanctionLimitDtList.isEmpty()) {
                                Vector vist = (Vector) sanctionLimitDtList.get(0);
//                        sanctionDt = dmyFormat.format(y_m_dFormat.parse(vist.get(0).toString()));
                                chkLimit = new BigDecimal(vist.get(1).toString());
                            } else {
                                sanctionLimitDtList = em.createNativeQuery("select ifnull(odlimit,0) from loan_appparameter  where acno = '" + acno + "' ").getResultList();
                                if (!sanctionLimitDtList.isEmpty()) {
                                    Vector vist = (Vector) sanctionLimitDtList.get(0);
                                    //                        sanctionDt = dmyFormat.format(y_m_dFormat.parse(vist.get(0).toString()));
                                    chkLimit = new BigDecimal(vist.get(0).toString());
                                }
                            }
                            String table_name = "";
                            if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                table_name = "ca_recon";
                                OverDueList = overdueCaList;
                            } else {
                                table_name = "loan_recon";
                                OverDueList = overdueDlList;
                            }
                            List result5 = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0), date_format(ifnull(min(dt),'19000101'),'%d/%m/%Y')  from " + table_name + "   where dt <= '" + tillDate + "'   and trandesc in (6,0)  and acno='" + acno + "'").getResultList();
                            if (!result5.isEmpty()) {
                                Vector vtrsub = (Vector) result5.get(0);
                                amtDis = Double.parseDouble(vtrsub.get(0).toString());
                                disbDt = vtrsub.get(1).toString();
                            }

                            /**
                             * If outstanding balance is less than chkLimit and
                             * there is no credit for previous days(Ex:
                             * Substandard-90,doubtful-365 days)*
                             */
                            if (outStandBal.compareTo(chkLimit) <= 0) {
                                long dayDiff = 0;
                                if (accStatus.equals("11") || accStatus.equals("12") || accStatus.equals("13")) {
                                    if (parameter.equalsIgnoreCase("Y")) {
                                        pojo = loanRemote.npaAccountAddition(accStatus, tillDate, acno, custname, outStandBal, acctNature);
                                        if (!OverDueList.isEmpty()) {
                                            for (int l = 0; l < OverDueList.size(); l++) {
                                                OverdueNonEmiResultList vect = OverDueList.get(l);
                                                if (vect.getAccountNo().equalsIgnoreCase(acno)) {
                                                    int noOfEmiOverDue = 0;
                                                    pojo.setOverDueAmt(new BigDecimal(vect.getOverDue()));
                                                    pojo.setNoOfPendingEmi(noOfEmiOverDue);
                                                }
                                            }
                                        }
                                    } else {
                                        pojo.setAcNo(acno);
                                        pojo.setCustName(custname);
                                        pojo.setAcStatus(accStatus);
                                        pojo.setOutstdBal(outStandBal);
                                        pojo.setNature(acctNature);
                                        limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                                + common.getTableName(common.getAcNatureByAcType(acno.substring(2, 4))) + " where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
                                        limitVec = (Vector) limitList.get(0);
                                        String lastCrDt = limitVec.get(0).toString();
                                        if (lastCrDt.equalsIgnoreCase("19000101")) {
                                            limitList = em.createNativeQuery("select openingdt from  accountmaster where acno = '" + acno + "' ").getResultList();
                                            limitVec = (Vector) limitList.get(0);
                                            lastCrDt = limitVec.get(0).toString();
                                        }
                                        pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                        if (accStatus.equalsIgnoreCase("11")) {
                                            pojo.setCurrentStatus(loanRemote.getRefRecDesc("SUB"));
                                        } else if (accStatus.equalsIgnoreCase("12")) {
                                            limitList = em.createNativeQuery("select ifnull(date_format(dbtdt,'%Y%m%d'),'19000101') from loan_appparameter where acno='" + acno + "'").getResultList();
                                            limitVec = (Vector) limitList.get(0);
                                            String npaDt = limitVec.get(0).toString();
                                            limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) as days "
                                                    + "from cbs_scheme_delinquency_details where delinq_cycle in (select ref_code "
                                                    + "from cbs_ref_rec_type where ref_rec_no='207' and ref_code in('D1','D2','D3')) "
                                                    + "order by days").getResultList();
                                            if (limitList.isEmpty()) {
                                                throw new ApplicationException("Please fill data in cbs_ref_rec_type.");
                                            }
                                            dayDiff = CbsUtil.dayDiff(ymdFormat.parse(npaDt), ymdFormat.parse(tillDate));
                                            for (int j = 0; j < limitList.size(); j++) {
                                                limitVec = (Vector) limitList.get(j);
                                                if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                                                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                        pojo.setCurrentStatus(loanRemote.getRefRecDesc("D1"));
                                                        break;
                                                    }
                                                } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                                                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                        pojo.setCurrentStatus(loanRemote.getRefRecDesc("D2"));
                                                        break;
                                                    }
                                                } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                                                    pojo.setCurrentStatus(loanRemote.getRefRecDesc("D3"));
                                                    break;
                                                }
                                            }
                                        } else if (accStatus.equalsIgnoreCase("13")) {
                                            pojo.setCurrentStatus(loanRemote.getRefRecDesc("L"));
                                        }
                                    }
                                    if (!provList.isEmpty()) {
                                        for (int l = 0; l < provList.size(); l++) {
                                            ProvDetailOfLoanAccounts obj = provList.get(l);
                                            if (acno.equalsIgnoreCase(obj.getAcno())) {
                                                if (outStandBal.compareTo(new BigDecimal("0")) != 0) {
                                                    if (obj.getProvamt() != null) {
                                                        pojo.setProvAmt(new BigDecimal(obj.getProvamt().toString()));
                                                    } else {
                                                        pojo.setProvAmt(new BigDecimal("0"));
                                                    }
                                                    pojo.setProvPerc(new BigDecimal(obj.getProvper().toString()));
                                                } else {
                                                    pojo.setProvAmt(new BigDecimal("0"));
                                                    pojo.setProvPerc(new BigDecimal(obj.getProvper().toString()));
                                                }
                                            }
                                        }
                                    } else {
                                        pojo.setProvAmt(new BigDecimal("0"));
                                        pojo.setProvPerc(new BigDecimal("0"));
                                    }
                                    List sanctionDtList = em.createNativeQuery("select ifnull(Sanctionlimit,0),ifnull(Sanctionlimitdt,'') from loan_appparameter where acno =  '" + acno + "' ").getResultList();
                                    if (!sanctionDtList.isEmpty()) {
                                        Vector vist = (Vector) sanctionDtList.get(0);
                                        sancAmt = new BigDecimal(vist.get(0).toString());
                                        sancDate = vist.get(1).toString();
                                    }
                                    String natureOfSec = "";
                                    BigDecimal matValue = new BigDecimal("0");
                                    List loanSecurity = em.createNativeQuery("select ifnull(matvalue,0),ifnull(matdate,''),ifnull(lienvalue,0),ifnull(Particulars,'') from loansecurity where acno ='" + acno + "'").getResultList();
                                    if (!loanSecurity.isEmpty()) {
                                        for (int l = 0; l < loanSecurity.size(); l++) {
                                            Vector vect = (Vector) loanSecurity.get(l);
                                            natureOfSec = natureOfSec.concat(vect.get(3).toString());
                                            matValue = matValue.add(new BigDecimal(vect.get(0).toString()));
                                        }
                                    }
                                    String subDt = "";
                                    if (accStatus.equals("12") || accStatus.equals("13")) {
                                        List subDtList = em.createNativeQuery("select ifnull(date_format(max(ast.effdt),'%d/%m/%Y'),'') as npaEffDt from accountstatus ast, "
                                                + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                                + " (select acno as ano, max(effdt) as dt from accountstatus where acno = '" + acno + "' and effdt  between '19000101' and '" + tillDate + "' and SPFLAG IN (11)  group by acno) b "
                                                + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (11) "
                                                + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno "
                                                + " ").getResultList();
                                        Vector vtr = (Vector) subDtList.get(0);
                                        subDt = vtr.get(0).toString();
                                    } else {
                                        subDt = pojo.getNpaDate();
                                    }
                                    pojo.setTypeOfLoan(common.getAcctDecription(acctType));
                                    pojo.setSancAmt(sancAmt);
                                    pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                    BigDecimal intAmt = new BigDecimal("0");
                                    List npaRecon = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from npa_recon where acno ='" + acno + "' and trantype ='8' and dt<='" + tillDate + "'").getResultList();
                                    if (!npaRecon.isEmpty()) {
                                        for (int l = 0; l < npaRecon.size(); l++) {
                                            Vector vect = (Vector) npaRecon.get(0);
                                            intAmt = new BigDecimal(vect.get(0).toString());
                                        }
                                    }
                                    pojo.setIntAmt(intAmt);
                                    pojo.setPrincAmt(outStandBal.subtract(intAmt));
                                    pojo.setSecNat(natureOfSec);
                                    pojo.setSecVal(matValue);
                                    pojo.setNature(common.getRefRecDesc("187", secured));
                                    List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                            + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                            + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName, ifnull(DateOfBirth,'1900-01-01') from cbs_customer_master_detail"
                                            + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                    if (!custDetail.isEmpty()) {
                                        Vector vect = (Vector) custDetail.get(0);
                                        contact = vect.get(0).toString();
                                        perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                        currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                        fatherName = vect.get(13).toString();
                                        dob = vect.get(14).toString();
                                        pin = vect.get(12).toString();
                                    }
                                    if (acctCategory.equalsIgnoreCase("IND")) {
                                        pojo.setRetirementAge(dmyFormat.format(ymdFormat.parse(CbsUtil.yearAdd(dob, Integer.parseInt(retirement)))));
                                    }
                                    pojo.setAddress(perAdd);
                                    pojo.setCurrAddress(currAdd);
                                    pojo.setPin(pin);
                                    pojo.setContactNo(contact);
                                    List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table_name + " where acno='" + acno + "' and dt='" + ymdFormat.format(dmyFormat.parse(pojo.getLastCrDate())) + "'").getResultList();
                                    if (!tempList.isEmpty()) {
                                        Vector ele = (Vector) tempList.get(0);
                                        if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                            lastCrAmt = new BigDecimal(ele.get(0).toString());
                                        }
                                    }
                                    pojo.setLastCrAmt(lastCrAmt);
                                    pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                    pojo.setDisburseDt(disbDt);
                                    pojo.setSubDt(subDt);
                                    pojo.setFatherName(fatherName);
                                    dataList.add(pojo);
                                } else {
                                    limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                            + "ca_recon where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
                                    limitVec = (Vector) limitList.get(0);
                                    String lastCrDt = limitVec.get(0).toString();

                                    dayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt), ymdFormat.parse(tillDate));

                                    limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) from "
                                            + "cbs_scheme_delinquency_details where delinq_cycle in (select ref_code from "
                                            + "cbs_ref_rec_type where ref_rec_no='207' and ref_code in('STD'))").getResultList();
                                    limitVec = (Vector) limitList.get(0);
//                            String cycle = limitVec.get(0).toString();
                                    long stdDays = Long.parseLong(limitVec.get(1).toString());
                                    if (dayDiff > stdDays) {
                                        // Add For Substandard
                                        pojo.setAcNo(acno);
                                        pojo.setCustName(custname);
                                        pojo.setOutstdBal(outStandBal);
                                        pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(lastCrDt, (int) stdDays))));
                                        pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
                                        pojo.setSortAcStatus("1"); //This is used for sorting 
                                        pojo.setCurrentStatusNoOfDays(dayDiff);
                                        pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                        pojo.setNoOfPendingEmi(dayDiff - stdDays);
                                        pojo.setOverDueAmt(loanRemote.getOverDueAmount(acno, tillDate));
                                        pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
                                        pojo.setMarkFlag("M");      //This account will mark as NPA.
                                        pojo.setEmiExist("N");
                                        if (!provList.isEmpty()) {
                                            for (int l = 0; l < provList.size(); l++) {
                                                ProvDetailOfLoanAccounts obj = provList.get(l);
                                                if (acno.equalsIgnoreCase(obj.getAcno())) {
                                                    if (outStandBal.compareTo(new BigDecimal("0")) != 0) {
                                                        if (obj.getProvamt() != null) {
                                                            pojo.setProvAmt(new BigDecimal(obj.getProvamt().toString()));
                                                        } else {
                                                            pojo.setProvAmt(new BigDecimal("0"));
                                                        }
                                                        pojo.setProvPerc(new BigDecimal(obj.getProvper().toString()));
                                                    } else {
                                                        pojo.setProvAmt(new BigDecimal("0"));
                                                        pojo.setProvPerc(new BigDecimal(obj.getProvper().toString()));
                                                    }
                                                }
                                            }
                                        } else {
                                            pojo.setProvAmt(new BigDecimal("0"));
                                            pojo.setNature("");
                                        }
                                        List sanctionDtList = em.createNativeQuery("select ifnull(Sanctionlimit,0),ifnull(Sanctionlimitdt,'') from loan_appparameter where acno =  '" + acno + "' ").getResultList();
                                        if (!sanctionDtList.isEmpty()) {
                                            Vector vist = (Vector) sanctionDtList.get(0);
                                            sancAmt = new BigDecimal(vist.get(0).toString());
                                            sancDate = vist.get(1).toString();
                                        }
                                        String natureOfSec = "";
                                        BigDecimal matValue = new BigDecimal("0");
                                        List loanSecurity = em.createNativeQuery("select ifnull(matvalue,0),ifnull(matdate,''),ifnull(lienvalue,0),ifnull(Particulars,'') from loansecurity where acno ='" + acno + "'").getResultList();
                                        if (!loanSecurity.isEmpty()) {
                                            for (int l = 0; l < loanSecurity.size(); l++) {
                                                Vector vect = (Vector) loanSecurity.get(l);
                                                natureOfSec = natureOfSec.concat(vect.get(3).toString());
                                                matValue = matValue.add(new BigDecimal(vect.get(0).toString()));
                                            }
                                        }
                                        BigDecimal intAmt = new BigDecimal("0");
                                        String tableName = common.getTableName(acctNature);
                                        List recon = em.createNativeQuery("select ifnull(sum(cramt-dramt),0)  from " + tableName + " where acno ='" + acno + "' and trantype ='8' and dt<='" + tillDate + "'").getResultList();
                                        if (!recon.isEmpty()) {
                                            for (int l = 0; l < recon.size(); l++) {
                                                Vector vect = (Vector) recon.get(0);
                                                intAmt = new BigDecimal(vect.get(0).toString());
                                            }
                                        }
                                        pojo.setIntAmt(intAmt);
                                        pojo.setTypeOfLoan(common.getAcctDecription(acctType));
                                        pojo.setSancAmt(sancAmt);
                                        pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                        pojo.setPrincAmt(outStandBal.subtract(intAmt));
                                        pojo.setSecNat(natureOfSec);
                                        pojo.setSecVal(matValue);
                                        pojo.setNature(common.getRefRecDesc("187", secured));
                                        List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                                + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                                + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName,DateOfBirth from cbs_customer_master_detail"
                                                + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                        if (!custDetail.isEmpty()) {
                                            Vector vect = (Vector) custDetail.get(0);
                                            contact = vect.get(0).toString();
                                            perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                            currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                            fatherName = vect.get(13).toString();
                                            dob = vect.get(14).toString();
                                            pin = vect.get(12).toString();
                                        }
                                        List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table_name + " where acno='" + acno + "' and dt='" + lastCrDt + "'").getResultList();
                                        if (!tempList.isEmpty()) {
                                            Vector ele = (Vector) tempList.get(0);
                                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                                lastCrAmt = new BigDecimal(ele.get(0).toString());
                                            }
                                        }
                                        pojo.setAddress(perAdd);
                                        pojo.setCurrAddress(currAdd);
                                        pojo.setPin(pin);
                                        pojo.setContactNo(contact);
                                        pojo.setLastCrAmt(lastCrAmt);
                                        pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                        pojo.setDisburseDt(disbDt);
                                        pojo.setFatherName(fatherName);
                                        if (acctCategory.equalsIgnoreCase("IND")) {
                                            pojo.setRetirementAge(dmyFormat.format(ymdFormat.parse(CbsUtil.yearAdd(dob, Integer.parseInt(retirement)))));
                                        }
                                        dataList.add(pojo);
                                    }
                                }
                            } /**
                             * If outstanding balance is greater than limit
                             * amount continuously for more than 90 days. while
                             * Npa account is same.
                             */
                            else if (outStandBal.compareTo(chkLimit) == 1) {
                                if (accStatus.equals("11") || accStatus.equals("12") || accStatus.equals("13")) {
                                    if (parameter.equalsIgnoreCase("Y")) {
                                        pojo = loanRemote.npaAccountAddition(accStatus, tillDate, acno, custname, outStandBal, acctNature);
                                        if (!OverDueList.isEmpty()) {
                                            for (int l = 0; l < OverDueList.size(); l++) {
                                                OverdueNonEmiResultList vect = OverDueList.get(l);
                                                if (vect.getAccountNo().equalsIgnoreCase(acno)) {
                                                    int noOfEmiOverDue = 0;
                                                    pojo.setOverDueAmt(new BigDecimal(vect.getOverDue()));
                                                    pojo.setNoOfPendingEmi(noOfEmiOverDue);
                                                }
                                            }
                                        }
                                    } else {
                                        long dayDiff = 0;
                                        pojo.setAcNo(acno);
                                        pojo.setCustName(custname);
                                        pojo.setAcStatus(accStatus);
                                        pojo.setOutstdBal(outStandBal);
                                        pojo.setNature(acctNature);
                                        limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                                + common.getTableName(common.getAcNatureByAcType(acno.substring(2, 4))) + " where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
                                        limitVec = (Vector) limitList.get(0);
                                        String lastCrDt = limitVec.get(0).toString();
                                        if (lastCrDt.equalsIgnoreCase("19000101")) {
                                            limitList = em.createNativeQuery("select openingdt from  accountmaster where acno = '" + acno + "' ").getResultList();
                                            limitVec = (Vector) limitList.get(0);
                                            lastCrDt = limitVec.get(0).toString();
                                        }
                                        pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                        if (accStatus.equalsIgnoreCase("11")) {
                                            pojo.setCurrentStatus(loanRemote.getRefRecDesc("SUB"));
                                        } else if (accStatus.equalsIgnoreCase("12")) {
                                            limitList = em.createNativeQuery("select ifnull(date_format(dbtdt,'%Y%m%d'),'19000101') from loan_appparameter where acno='" + acno + "'").getResultList();
                                            limitVec = (Vector) limitList.get(0);
                                            String npaDt = limitVec.get(0).toString();
                                            limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) as days "
                                                    + "from cbs_scheme_delinquency_details where delinq_cycle in (select ref_code "
                                                    + "from cbs_ref_rec_type where ref_rec_no='207' and ref_code in('D1','D2','D3')) "
                                                    + "order by days").getResultList();
                                            if (limitList.isEmpty()) {
                                                throw new ApplicationException("Please fill data in cbs_ref_rec_type.");
                                            }
                                            dayDiff = CbsUtil.dayDiff(ymdFormat.parse(npaDt), ymdFormat.parse(tillDate));
                                            for (int j = 0; j < limitList.size(); j++) {
                                                limitVec = (Vector) limitList.get(j);
                                                if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                                                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                        pojo.setCurrentStatus(loanRemote.getRefRecDesc("D1"));
                                                        break;
                                                    }
                                                } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                                                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                        pojo.setCurrentStatus(loanRemote.getRefRecDesc("D2"));
                                                        break;
                                                    }
                                                } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                                                    pojo.setCurrentStatus(loanRemote.getRefRecDesc("D3"));
                                                    break;
                                                }
                                            }
                                        } else if (accStatus.equalsIgnoreCase("13")) {
                                            pojo.setCurrentStatus(loanRemote.getRefRecDesc("L"));
                                        }
                                    }
                                    if (!provList.isEmpty()) {
                                        for (int l = 0; l < provList.size(); l++) {
                                            ProvDetailOfLoanAccounts obj = provList.get(l);
                                            if (acno.equalsIgnoreCase(obj.getAcno())) {
                                                if (outStandBal.compareTo(new BigDecimal("0")) != 0) {
                                                    if (obj.getProvamt() != null) {
                                                        pojo.setProvAmt(new BigDecimal(obj.getProvamt().toString()));
                                                    } else {
                                                        pojo.setProvAmt(new BigDecimal("0"));
                                                    }
                                                    pojo.setProvPerc(new BigDecimal(obj.getProvper().toString()));
                                                } else {
                                                    pojo.setProvAmt(new BigDecimal("0"));
                                                    pojo.setProvPerc(new BigDecimal(obj.getProvper().toString()));
                                                }
                                            }
                                        }
                                    } else {
                                        pojo.setProvAmt(new BigDecimal("0"));
                                        pojo.setProvPerc(new BigDecimal("0"));
                                    }
                                    List sanctionDtList = em.createNativeQuery("select ifnull(Sanctionlimit,0),ifnull(Sanctionlimitdt,'') from loan_appparameter where acno =  '" + acno + "' ").getResultList();
                                    if (!sanctionDtList.isEmpty()) {
                                        Vector vist = (Vector) sanctionDtList.get(0);
                                        sancAmt = new BigDecimal(vist.get(0).toString());
                                        sancDate = vist.get(1).toString();
                                    }
                                    String natureOfSec = "";
                                    BigDecimal matValue = new BigDecimal("0");
                                    List loanSecurity = em.createNativeQuery("select ifnull(matvalue,0),ifnull(matdate,''),ifnull(lienvalue,0),ifnull(Particulars,'') from loansecurity where acno ='" + acno + "'").getResultList();
                                    if (!loanSecurity.isEmpty()) {
                                        for (int l = 0; l < loanSecurity.size(); l++) {
                                            Vector vect = (Vector) loanSecurity.get(l);
                                            natureOfSec = natureOfSec.concat(vect.get(3).toString());
                                            matValue = matValue.add(new BigDecimal(vect.get(0).toString()));
                                        }
                                    }
                                    BigDecimal intAmt = new BigDecimal("0");
                                    List npaRecon = em.createNativeQuery("select ifnull(sum(cramt-dramt),0)  from npa_recon where acno ='" + acno + "' and trantype ='8' and dt<='" + tillDate + "'").getResultList();
                                    if (!npaRecon.isEmpty()) {
                                        for (int l = 0; l < npaRecon.size(); l++) {
                                            Vector vect = (Vector) npaRecon.get(0);
                                            intAmt = new BigDecimal(vect.get(0).toString());
                                        }
                                    }
                                    String subDt = "";
                                    if (accStatus.equals("12") || accStatus.equals("13")) {
                                        List subDtList = em.createNativeQuery("select ifnull(date_format(max(ast.effdt),'%d/%m/%Y'),'') as npaEffDt from accountstatus ast, "
                                                + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                                + " (select acno as ano, max(effdt) as dt from accountstatus where acno = '" + acno + "' and effdt  between '19000101' and '" + tillDate + "' and SPFLAG IN (11)  group by acno) b "
                                                + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (11) "
                                                + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno "
                                                + " ").getResultList();
                                        Vector vtr = (Vector) subDtList.get(0);
                                        subDt = vtr.get(0).toString();
                                    } else {
                                        subDt = pojo.getNpaDate();
                                    }
                                    pojo.setIntAmt(intAmt);
                                    pojo.setTypeOfLoan(common.getAcctDecription(acctType));
                                    pojo.setSancAmt(sancAmt);
                                    pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                    pojo.setPrincAmt(outStandBal.subtract(intAmt));
                                    pojo.setSecNat(natureOfSec);
                                    pojo.setSecVal(matValue);
                                    pojo.setNature(common.getRefRecDesc("187", secured));
                                    List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                            + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                            + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName,DateOfBirth from cbs_customer_master_detail"
                                            + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                    if (!custDetail.isEmpty()) {
                                        Vector vect = (Vector) custDetail.get(0);
                                        contact = vect.get(0).toString();
                                        perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                        currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                        fatherName = vect.get(13).toString();
                                        dob = vect.get(14).toString();
                                        pin = vect.get(12).toString();
                                    }
                                    List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table_name + " where acno='" + acno + "' and dt='" + ymdFormat.format(dmyFormat.parse(pojo.getLastCrDate())) + "'").getResultList();
                                    if (!tempList.isEmpty()) {
                                        Vector ele = (Vector) tempList.get(0);
                                        if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                            lastCrAmt = new BigDecimal(ele.get(0).toString());
                                        }
                                    }
                                    pojo.setAddress(perAdd);
                                    pojo.setCurrAddress(currAdd);
                                    pojo.setPin(pin);
                                    pojo.setContactNo(contact);
                                    pojo.setLastCrAmt(lastCrAmt);
                                    pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                    pojo.setDisburseDt(disbDt);
                                    pojo.setSubDt(subDt);
                                    pojo.setFatherName(fatherName);
                                    if (acctCategory.equalsIgnoreCase("IND")) {
                                        pojo.setRetirementAge(dmyFormat.format(ymdFormat.parse(CbsUtil.yearAdd(dob, Integer.parseInt(retirement)))));
                                    }
                                    dataList.add(pojo);
                                } else {
                                    //Only For Standard Account
                                    boolean addAccount = false;
                                    String tranDt = "19000101";
                                    limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) from "
                                            + "cbs_scheme_delinquency_details where delinq_cycle in (select ref_code from "
                                            + "cbs_ref_rec_type where ref_rec_no='207' and ref_code in('STD'))").getResultList();
                                    limitVec = (Vector) limitList.get(0);
//                            String cycle = limitVec.get(0).toString();
                                    Long stdDays = Long.parseLong(limitVec.get(1).toString());

                                    String fromDt = CbsUtil.dateAdd(tillDate, -(stdDays.intValue()));   //For more than 90 days for standard.
                                    String toDt = CbsUtil.dateAdd(tillDate, -1);                        //Because I have already checked the tillDate.
                                    List<DateSlabPojo> list = loanRemote.getLimitOnRangeOfDate(fromDt, toDt, acno);
                                    for (int k = 0; k < list.size(); k++) {
                                        addAccount = false;
                                        DateSlabPojo obj = list.get(k);
                                        String tmpFrDt = obj.getFromDt();
                                        String tmpToDt = obj.getToDt();
                                        BigDecimal ccodLimit = obj.getLimit();

                                        limitList = em.createNativeQuery("select distinct(date_format(dt,'%Y%m%d')) as trandt from "
                                                + common.getTableName(common.getAcNatureByAcType(acno.substring(2, 4))) + " where acno='" + acno + "' and (cramt>0 ) and "
                                                + "dt between '" + tmpFrDt + "' and '" + tmpToDt + "' order by trandt").getResultList();
                                        for (int h = 0; h < limitList.size(); h++) {
                                            limitVec = (Vector) limitList.get(h);
                                            tranDt = limitVec.get(0).toString();

                                            BigDecimal tempOutstanding = new BigDecimal(formatter.format(Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, tranDt)))).abs();

                                            if (tempOutstanding.compareTo(ccodLimit) == -1) {
                                                addAccount = false;
                                                break;  // From here it should go to the accountList For LOOP. For next account number of the same type.
                                            } else {
                                                addAccount = true;
                                            }
                                        }
//                                addAccount = true;
                                    }
                                    if (addAccount == true) {
                                        pojo.setAcNo(acno);
                                        pojo.setCustName(custname);
                                        pojo.setOutstdBal(outStandBal);
                                        pojo.setDueDt(dmyFormat.format(ymdFormat.parse(fromDt)));   // Date from where outstanding is greater than DP/OD for CC/OD limit.
                                        pojo.setNoOfPendingEmi(CbsUtil.dayDiff(ymdFormat.parse(fromDt), ymdFormat.parse(tillDate)));
                                        pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
                                        pojo.setSortAcStatus("1"); //This is used for sorting 
                                        pojo.setCurrentStatusNoOfDays(CbsUtil.dayDiff(ymdFormat.parse(tranDt), ymdFormat.parse(tillDate)));
                                        pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(tranDt)));
                                        pojo.setOverDueAmt(loanRemote.getOverDueAmount(acno, tillDate));
                                        pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
                                        pojo.setMarkFlag("M");          //This account will mark as NPA.
                                        pojo.setEmiExist("N");
                                        if (!provList.isEmpty()) {
                                            for (int l = 0; l < provList.size(); l++) {
                                                ProvDetailOfLoanAccounts obj = provList.get(l);
                                                if (acno.equalsIgnoreCase(obj.getAcno())) {
                                                    if (outStandBal.compareTo(new BigDecimal("0")) != 0) {
                                                        if (obj.getProvamt() != null) {
                                                            pojo.setProvAmt(new BigDecimal(obj.getProvamt().toString()));
                                                        } else {
                                                            pojo.setProvAmt(new BigDecimal("0"));
                                                        }
                                                        pojo.setProvPerc(new BigDecimal(obj.getProvper().toString()));
                                                    } else {
                                                        pojo.setProvAmt(new BigDecimal("0"));
                                                        pojo.setProvPerc(new BigDecimal(obj.getProvper().toString()));
                                                    }
                                                }
                                            }
                                        } else {
                                            pojo.setProvAmt(new BigDecimal("0"));
                                            pojo.setProvPerc(new BigDecimal("0"));
                                        }
                                        List sanctionDtList = em.createNativeQuery("select ifnull(Sanctionlimit,0),ifnull(Sanctionlimitdt,'') from loan_appparameter where acno =  '" + acno + "' ").getResultList();
                                        if (!sanctionDtList.isEmpty()) {
                                            Vector vist = (Vector) sanctionDtList.get(0);
                                            sancAmt = new BigDecimal(vist.get(0).toString());
                                            sancDate = vist.get(1).toString();
                                        }
                                        String natureOfSec = "";
                                        BigDecimal matValue = new BigDecimal("0");
                                        List loanSecurity = em.createNativeQuery("select ifnull(matvalue,0),ifnull(matdate,''),ifnull(lienvalue,0),ifnull(Particulars,'') from loansecurity where acno ='" + acno + "'").getResultList();
                                        if (!loanSecurity.isEmpty()) {
                                            for (int l = 0; l < loanSecurity.size(); l++) {
                                                Vector vect = (Vector) loanSecurity.get(l);
                                                natureOfSec = natureOfSec.concat(vect.get(3).toString());
                                                matValue = matValue.add(new BigDecimal(vect.get(0).toString()));
                                            }
                                        }
                                        BigDecimal intAmt = new BigDecimal("0");
                                        String tableName = common.getTableName(acctNature);
                                        List npaRecon = em.createNativeQuery("select ifnull(sum(cramt-dramt),0)  from " + tableName + " where acno ='" + acno + "' and trantype ='8' and dt<='" + tillDate + "'").getResultList();
                                        if (!npaRecon.isEmpty()) {
                                            for (int l = 0; l < npaRecon.size(); l++) {
                                                Vector vect = (Vector) npaRecon.get(0);
                                                intAmt = new BigDecimal(vect.get(0).toString());
                                            }
                                        }
                                        pojo.setIntAmt(intAmt);
                                        pojo.setTypeOfLoan(common.getAcctDecription(acctType));
                                        pojo.setSancAmt(sancAmt);
                                        pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                        pojo.setPrincAmt(outStandBal.subtract(intAmt));
                                        pojo.setSecNat(natureOfSec);
                                        pojo.setSecVal(matValue);
                                        pojo.setNature(common.getRefRecDesc("187", secured));
                                        List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                                + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                                + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName,DateOfBirth from cbs_customer_master_detail"
                                                + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                        if (!custDetail.isEmpty()) {
                                            Vector vect = (Vector) custDetail.get(0);
                                            contact = vect.get(0).toString();
                                            perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                            currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                            fatherName = vect.get(13).toString();
                                            dob = vect.get(14).toString();
                                            pin = vect.get(12).toString();
                                        }
                                        List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table_name + " where acno='" + acno + "' and dt='" + tranDt + "'").getResultList();
                                        if (!tempList.isEmpty()) {
                                            Vector ele = (Vector) tempList.get(0);
                                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                                lastCrAmt = new BigDecimal(ele.get(0).toString());
                                            }
                                        }
                                        pojo.setAddress(perAdd);
                                        pojo.setCurrAddress(currAdd);
                                        pojo.setPin(pin);
                                        pojo.setContactNo(contact);
                                        pojo.setLastCrAmt(lastCrAmt);
                                        pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                        pojo.setDisburseDt(disbDt);
                                        pojo.setFatherName(fatherName);
                                        if (acctCategory.equalsIgnoreCase("IND")) {
                                            pojo.setRetirementAge(dmyFormat.format(ymdFormat.parse(CbsUtil.yearAdd(dob, Integer.parseInt(retirement)))));
                                        }
                                        dataList.add(pojo);
                                    }
                                }
                            }
//                }
                        } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            List<OverdueEmiReportPojo> OverDueList = new ArrayList<>();
//                if (brCode.equalsIgnoreCase("0A")) {
////                   accountList = em.createNativeQuery("select a.acno,a.custname,a.accstatus from accountmaster a,emidetails e "
////                            + "where a.acno=e.acno and AND (a.CLOSINGDATE='' OR ifnull(a.CLOSINGDATE,'')='' OR a.CLOSINGDATE>'" + tillDate + "') and a.accttype = '" + acctType + "' and a.openingdt <= '" + tillDate + "' "
////                            + "and e.status = 'unpaid' and e.duedt < '" + tillDate + "' group by a.acno,a.custname,a.accstatus").getResultList();
//                    accountList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT ACNO FROM accountmaster WHERE OPENINGDT<='" + tillDate + "' AND (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + tillDate + "') AND SUBSTRING(ACNO,3,2)='" + acctType + "'  and accstatus in (11,12,13) ) ORDER BY ACNO").getResultList();
//                } else {
////                    accountList = em.createNativeQuery("select a.acno,a.custname,a.accstatus from accountmaster a,emidetails e "
////                            + "where a.acno=e.acno  AND (a.CLOSINGDATE='' OR ifnull(a.CLOSINGDATE,'')='' OR a.CLOSINGDATE>'" + tillDate + "') and a.accttype = '" + acctType + "' and a.openingdt <= '" + tillDate + "' and "
////                            + "e.status = 'unpaid' and e.duedt < '" + tillDate + "' and a.curbrcode='" + brCode + "' group by a.acno,a.custname,"
////                            + "a.accstatus").getResultList();
//                    accountList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT ACNO FROM accountmaster WHERE OPENINGDT<='" + tillDate + "' AND (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + tillDate + "') AND CURBRCODE='" + brCode + "' and accstatus in (11,12,13) ) AND SUBSTRING(ACNO,3,2)='" + acctType + "' ORDER BY ACNO").getResultList();
//                }
//                if (resultList.isEmpty()) {
//                    throw new ApplicationException("There is no data to mark NPA.");
//                }

//                for (int i = 0; i < resultList.size(); i++) {
                            NpaAccountDetailPojo pojo = new NpaAccountDetailPojo();
//                    Vector element = (Vector) accountList.get(i);
                            String acno = resultList.get(i).getAcno();
                            String custname = resultList.get(i).getCustName();
                            String accStatus = resultList.get(i).getPresentStatus();
                            String secured = resultList.get(i).getSecured();
                            String retirement = resultList.get(i).getRetirementAge();
                            String acctCategory = resultList.get(i).getAcctCategory();
                            String perAdd = "", currAdd = "", contact = "", disbDt = null, fatherName = "";
                            String dob = "", pin = "";
                            BigDecimal lastCrAmt = new BigDecimal("0");
                            double amtDis = 0d;
                            Long dayDiff = 0l;
                            Integer unPaidCount = 0;
                            if (accStatus.contains("SUB")) {
                                accStatus = "11";
                            } else if (accStatus.contains("DOU")) {
                                accStatus = "12";
                            } else if (accStatus.contains("LOS")) {
                                accStatus = "13";
                            } else {
                                accStatus = "1";
                            }
                            String sancDate = "";
                            BigDecimal sancAmt = new BigDecimal("0");
                            String table_name = "";
                            if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                table_name = "ca_recon";
                            } else {
                                table_name = "loan_recon";
                                OverDueList = overdueTlList;
                            }
                            List result5 = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0), date_format(ifnull(min(dt),'19000101'),'%d/%m/%Y')  from " + table_name + "   where dt <= '" + tillDate + "'   and trandesc in (6,0)  and acno='" + acno + "'").getResultList();
                            if (!result5.isEmpty()) {
                                Vector vtrsub = (Vector) result5.get(0);
                                amtDis = Double.parseDouble(vtrsub.get(0).toString());
                                disbDt = vtrsub.get(1).toString();
                            }
//                    String presentStatus = interBranchFacade.fnGetLoanStatusTillDate(acno, tillDate);
//                    if (presentStatus.equalsIgnoreCase("DOU")) {
//                        accStatus = "12";
//                    } else if (presentStatus.equalsIgnoreCase("SUB")) {
//                        accStatus = "11";
//                    } else if (presentStatus.equalsIgnoreCase("LOS")) {
//                        accStatus = "13";
//                    } else if (presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("STANDARD")) {
//                        accStatus = "1";
//                    } else {
//                        accStatus = "1";
//                    }
                            //accStatus = element.get(2).toString();

//                    BigDecimal outStandBal = new BigDecimal(formatter.format(Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, tillDate)))).abs();
                            BigDecimal outStandBal = resultList.get(i).getBalance();
                            if (accStatus.equals("11") || accStatus.equals("12") || accStatus.equals("13")) {
                                //For NPA Account.
                                if (parameter.equalsIgnoreCase("Y")) {
                                    pojo = loanRemote.npaAccountAddition(accStatus, tillDate, acno, custname, outStandBal, acctNature);
                                    if (!OverDueList.isEmpty()) {
                                        for (int l = 0; l < OverDueList.size(); l++) {
                                            OverdueEmiReportPojo vect = OverDueList.get(l);
                                            if (vect.getAccountNumber().equalsIgnoreCase(acno)) {
                                                int noOfEmiOverDue = 0;
                                                noOfEmiOverDue = vect.getNoOfEmiOverdue();
                                                pojo.setOverDueAmt(vect.getAmountOverdue());
                                                pojo.setNoOfPendingEmi(noOfEmiOverDue);
                                            }
                                        }
                                    }
                                } else {
                                    pojo.setAcNo(acno);
                                    pojo.setCustName(custname);
                                    pojo.setAcStatus(accStatus);
                                    pojo.setOutstdBal(outStandBal);
                                    pojo.setNature(acctNature);
                                    List limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                            + common.getTableName(common.getAcNatureByAcType(acno.substring(2, 4))) + " where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
                                    Vector limitVec = (Vector) limitList.get(0);
                                    String lastCrDt = limitVec.get(0).toString();
                                    if (lastCrDt.equalsIgnoreCase("19000101")) {
                                        limitList = em.createNativeQuery("select openingdt from  accountmaster where acno = '" + acno + "' ").getResultList();
                                        limitVec = (Vector) limitList.get(0);
                                        lastCrDt = limitVec.get(0).toString();
                                    }
                                    pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                    if (accStatus.equalsIgnoreCase("11")) {
                                        pojo.setCurrentStatus(loanRemote.getRefRecDesc("SUB"));
                                    } else if (accStatus.equalsIgnoreCase("12")) {
                                        limitList = em.createNativeQuery("select ifnull(date_format(dbtdt,'%Y%m%d'),'19000101') from loan_appparameter where acno='" + acno + "'").getResultList();
                                        limitVec = (Vector) limitList.get(0);
                                        String npaDt = limitVec.get(0).toString();
                                        limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) as days "
                                                + "from cbs_scheme_delinquency_details where delinq_cycle in (select ref_code "
                                                + "from cbs_ref_rec_type where ref_rec_no='207' and ref_code in('D1','D2','D3')) "
                                                + "order by days").getResultList();
                                        if (limitList.isEmpty()) {
                                            throw new ApplicationException("Please fill data in cbs_ref_rec_type.");
                                        }
                                        dayDiff = CbsUtil.dayDiff(ymdFormat.parse(npaDt), ymdFormat.parse(tillDate));
                                        for (int j = 0; j < limitList.size(); j++) {
                                            limitVec = (Vector) limitList.get(j);
                                            if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                                                if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                    pojo.setCurrentStatus(loanRemote.getRefRecDesc("D1"));
                                                    break;
                                                }
                                            } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                                                if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                    pojo.setCurrentStatus(loanRemote.getRefRecDesc("D2"));
                                                    break;
                                                }
                                            } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                                                pojo.setCurrentStatus(loanRemote.getRefRecDesc("D3"));
                                                break;
                                            }
                                        }
                                    } else if (accStatus.equalsIgnoreCase("13")) {
                                        pojo.setCurrentStatus(loanRemote.getRefRecDesc("L"));
                                    }
                                }
                                if (!provList.isEmpty()) {
                                    for (int l = 0; l < provList.size(); l++) {
                                        ProvDetailOfLoanAccounts obj = provList.get(l);
                                        if (acno.equalsIgnoreCase(obj.getAcno())) {
                                            if (outStandBal.compareTo(new BigDecimal("0")) != 0) {
//                                                System.out.println("prov Amt" + obj.getProvamt());
                                                if (obj.getProvamt() != null) {
                                                    pojo.setProvAmt(new BigDecimal(obj.getProvamt().toString()));
                                                } else {
                                                    pojo.setProvAmt(new BigDecimal("0"));
                                                }
                                                pojo.setProvPerc(new BigDecimal(obj.getProvper().toString()));
                                            } else {
                                                pojo.setProvAmt(new BigDecimal("0"));
                                                pojo.setProvPerc(new BigDecimal(obj.getProvper().toString()));
                                            }
                                        }
                                    }
                                } else {
                                    pojo.setProvAmt(new BigDecimal("0"));
                                    pojo.setProvPerc(new BigDecimal("0"));
                                }
                                List sanctionDtList = em.createNativeQuery("select ifnull(Sanctionlimit,0),ifnull(Sanctionlimitdt,'') from loan_appparameter where acno =  '" + acno + "' ").getResultList();
                                if (!sanctionDtList.isEmpty()) {
                                    Vector vist = (Vector) sanctionDtList.get(0);
                                    sancAmt = new BigDecimal(vist.get(0).toString());
                                    sancDate = vist.get(1).toString();
                                }
                                String natureOfSec = "";
                                BigDecimal matValue = new BigDecimal("0");
                                List loanSecurity = em.createNativeQuery("select ifnull(matvalue,0),ifnull(matdate,''),ifnull(lienvalue,0),ifnull(Particulars,'') from loansecurity where acno ='" + acno + "'").getResultList();
                                if (!loanSecurity.isEmpty()) {
                                    for (int l = 0; l < loanSecurity.size(); l++) {
                                        Vector vect = (Vector) loanSecurity.get(l);
                                        natureOfSec = natureOfSec.concat(vect.get(3).toString());
                                        matValue = matValue.add(new BigDecimal(vect.get(0).toString()));
                                    }
                                }
                                BigDecimal intAmt = new BigDecimal("0");
                                String tableName = common.getTableName(acctNature);
                                List npaRecon = em.createNativeQuery("select ifnull(sum(cramt-dramt),0)  from npa_recon where acno ='" + acno + "' and trantype ='8' and dt<='" + tillDate + "'").getResultList();
                                if (!npaRecon.isEmpty()) {
                                    for (int l = 0; l < npaRecon.size(); l++) {
                                        Vector vect = (Vector) npaRecon.get(0);
                                        intAmt = new BigDecimal(vect.get(0).toString());
                                    }
                                }
                                String subDt = "";
                                if (accStatus.equals("12") || accStatus.equals("13")) {
                                    List subDtList = em.createNativeQuery("select ifnull(date_format(max(ast.effdt),'%d/%m/%Y'),'') as npaEffDt from accountstatus ast, "
                                            + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                                            + " (select acno as ano, max(effdt) as dt from accountstatus where acno = '" + acno + "' and effdt  between '19000101' and '" + tillDate + "' and SPFLAG IN (11)  group by acno) b "
                                            + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (11) "
                                            + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno "
                                            + " ").getResultList();
                                    Vector vtr = (Vector) subDtList.get(0);
                                    subDt = vtr.get(0).toString();
//                                    System.out.println(acno + " == " + accStatus + " == " + subDt);
                                } else {
                                    subDt = pojo.getNpaDate();
                                }
                                pojo.setIntAmt(intAmt);
                                pojo.setTypeOfLoan(common.getAcctDecription(acctType));
                                pojo.setSancAmt(sancAmt);
                                pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                pojo.setPrincAmt(outStandBal.subtract(intAmt));
                                pojo.setSecNat(natureOfSec);
                                pojo.setSecVal(matValue);
                                pojo.setNature(common.getRefRecDesc("187", secured));
                                List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                        + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                        + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName,DateOfBirth from cbs_customer_master_detail"
                                        + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                if (!custDetail.isEmpty()) {
                                    Vector vect = (Vector) custDetail.get(0);
                                    contact = vect.get(0).toString();
                                    perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                    currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                    fatherName = vect.get(13).toString();
                                    dob = vect.get(14).toString();
                                    pin = vect.get(12).toString();
                                }
                                List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table_name + " where acno='" + acno + "' and dt='" + ymdFormat.format(dmyFormat.parse(pojo.getLastCrDate())) + "'").getResultList();
                                if (!tempList.isEmpty()) {
                                    Vector ele = (Vector) tempList.get(0);
                                    if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                        lastCrAmt = new BigDecimal(ele.get(0).toString());
                                    }
                                }
                                pojo.setAddress(perAdd);
                                pojo.setCurrAddress(currAdd);
                                pojo.setPin(pin);
                                pojo.setContactNo(contact);
                                pojo.setLastCrAmt(lastCrAmt);
                                pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                pojo.setDisburseDt(disbDt);
                                pojo.setFatherName(fatherName);
                                if (acctCategory.equalsIgnoreCase("IND")) {
                                    pojo.setRetirementAge(dmyFormat.format(ymdFormat.parse(CbsUtil.yearAdd(dob, Integer.parseInt(retirement)))));
                                }
                                pojo.setSubDt(subDt);
                                dataList.add(pojo);
                            } else {
                                String lastCrDt = "19000101", minUnPaidDt = "19000101";
                                //For Standard Account.
                                List list = em.createNativeQuery("select date_format(min(duedt),'%Y%m%d') min_unpaid_dt,count(status) as unpaid_count,installamt "
                                        + "from emidetails where acno = '" + acno + "' and status = 'unpaid' and "
                                        + "duedt < '" + tillDate + "' group by installamt").getResultList();
                                if (list.isEmpty()) {
                                    if (!outStandBal.equals(new BigDecimal("0"))) {
                                        List checklist = em.createNativeQuery("select * from emidetails where acno = '" + acno + "'").getResultList();
                                        if (checklist.isEmpty()) {
                                            List limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                                    + "loan_recon where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
                                            Vector limitVec = (Vector) limitList.get(0);
                                            lastCrDt = limitVec.get(0).toString();
//                                    throw new ApplicationException("There is no data in emidetails for :" + acno);
                                        } else {

                                            List limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                                    + "loan_recon where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
                                            Vector limitVec = (Vector) limitList.get(0);
                                            lastCrDt = limitVec.get(0).toString();

                                            List paidEmiList = em.createNativeQuery("select date_format(max(duedt),'%Y%m%d'), acno "
                                                    + "from emidetails where acno = '" + acno + "' and status = 'PAID' and "
                                                    + "duedt <= '" + tillDate + "' group by acno").getResultList();
                                            if (!paidEmiList.isEmpty()) {
                                                Vector emiPaidDtVect = (Vector) paidEmiList.get(0);
                                                minUnPaidDt = emiPaidDtVect.get(0).toString();
                                            }
//                                    if(ymdFormat.parse(lastCrDt).compareTo(ymdFormat.parse(minUnPaidDt))<0 ){
//                                        lastCrDt = minUnPaidDt;
//                                    }
                                        }
                                        dayDiff = (minUnPaidDt.equalsIgnoreCase("19000101") ? dayDiff : CbsUtil.dayDiff(ymdFormat.parse(minUnPaidDt), ymdFormat.parse(tillDate)));
                                    }
                                } else {
                                    Vector listVec = (Vector) list.get(0);
                                    unPaidCount = Integer.parseInt(listVec.get(1).toString());
                                    Double installment = Double.parseDouble(listVec.get(2).toString());
                                    List paidEmiList = em.createNativeQuery("select date_format(max(duedt),'%Y%m%d'), acno "
                                            + "from emidetails where acno = '" + acno + "' and status = 'PAID' and "
                                            + "duedt <= '" + tillDate + "' group by acno").getResultList();
                                    if (!paidEmiList.isEmpty()) {
                                        Vector emiPaidDtVect = (Vector) paidEmiList.get(0);
                                        minUnPaidDt = emiPaidDtVect.get(0).toString();
                                    }
                                    List limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                            + "loan_recon where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
                                    Vector limitVec = (Vector) limitList.get(0);
                                    lastCrDt = limitVec.get(0).toString();
                                    dayDiff = CbsUtil.dayDiff(ymdFormat.parse(minUnPaidDt), ymdFormat.parse(tillDate));
                                }
                                list = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) from "
                                        + "cbs_scheme_delinquency_details where delinq_cycle in (select ref_code from "
                                        + "cbs_ref_rec_type where ref_rec_no='207' and ref_code in('STD'))").getResultList();
                                Vector listVec = (Vector) list.get(0);
                                Long stdDays = Long.parseLong(listVec.get(1).toString());
                                if (dayDiff > stdDays) {
                                    pojo.setAcNo(acno);
                                    pojo.setCustName(custname);
                                    pojo.setOutstdBal(outStandBal);
                                    pojo.setDueDt(dmyFormat.format(ymdFormat.parse(minUnPaidDt)));
                                    pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
                                    pojo.setSortAcStatus("1"); //This is used for sorting 
                                    pojo.setCurrentStatusNoOfDays(CbsUtil.dayDiff(ymdFormat.parse(lastCrDt), ymdFormat.parse(tillDate)));
                                    pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                    pojo.setLastEmiPaidDt(dmyFormat.format(ymdFormat.parse(minUnPaidDt)));
                                    pojo.setNoOfPendingEmi(unPaidCount);
                                    BigDecimal ovrdueAmt = new BigDecimal("0");
                                    List acNoList1 = em.createNativeQuery("select distinct (acno) from emidetails where status = 'unpaid' and acno ='" + acno + "'").getResultList();
                                    if (acNoList1.size() == 0) {
                                        List<OverdueEmiReportPojo> overdueEmiList = getOverdueEmiList("A/Cs Whose Plan Has Finished", acno, 1, 200, tillDate, common.getBrncodeByAcno(acno), "", 0, isExceessEmi, null, 0);
                                        for (OverdueEmiReportPojo pojo1 : overdueEmiList) {
                                            ovrdueAmt = pojo1.getAmountOverdue();
                                            pojo.setNoOfPendingEmi(pojo1.getNoOfEmiOverdue());
                                        }
                                    } else {
                                        List<OverdueEmiReportPojo> overdueEmiList = getOverdueEmiList("", acno, 1, 200, tillDate, common.getBrncodeByAcno(acno), "", 0, isExceessEmi, null, 0);
                                        for (OverdueEmiReportPojo pojo1 : overdueEmiList) {
                                            ovrdueAmt = pojo1.getAmountOverdue();
                                            pojo.setNoOfPendingEmi(pojo1.getNoOfEmiOverdue());
                                        }
                                    }
                                    pojo.setOverDueAmt(ovrdueAmt); // by Alok
                                    //                                pojo.setOverDueAmt(new BigDecimal(installment * unPaidCount)); // By Dinesh

                                    pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
                                    pojo.setMarkFlag("M");      //This account will mark as NPA.
                                    pojo.setEmiExist("Y");
                                    if (!provList.isEmpty()) {
                                        for (int l = 0; l < provList.size(); l++) {
                                            ProvDetailOfLoanAccounts obj = provList.get(l);
                                            if (acno.equalsIgnoreCase(obj.getAcno())) {
                                                if (outStandBal.compareTo(new BigDecimal("0")) != 0) {
                                                    if (obj.getProvamt() != null) {
                                                        pojo.setProvAmt(new BigDecimal(obj.getProvamt().toString()));
                                                    } else {
                                                        pojo.setProvAmt(new BigDecimal("0"));
                                                    }
                                                    pojo.setProvPerc(new BigDecimal(obj.getProvper().toString()));
                                                } else {
                                                    pojo.setProvAmt(new BigDecimal("0"));
                                                    pojo.setProvPerc(new BigDecimal(obj.getProvper().toString()));
                                                }
                                            }
                                        }
                                    } else {
                                        pojo.setProvAmt(new BigDecimal("0"));
                                        pojo.setProvPerc(new BigDecimal("0"));
                                    }
                                    List sanctionDtList = em.createNativeQuery("select ifnull(Sanctionlimit,0),ifnull(Sanctionlimitdt,'') from loan_appparameter where acno =  '" + acno + "' ").getResultList();
                                    if (!sanctionDtList.isEmpty()) {
                                        Vector vist = (Vector) sanctionDtList.get(0);
                                        sancAmt = new BigDecimal(vist.get(0).toString());
                                        sancDate = vist.get(1).toString();
                                    }
                                    String natureOfSec = "";
                                    BigDecimal matValue = new BigDecimal("0");
                                    List loanSecurity = em.createNativeQuery("select ifnull(matvalue,0),ifnull(matdate,''),ifnull(lienvalue,0),ifnull(Particulars,'') from loansecurity where acno ='" + acno + "'").getResultList();
                                    if (!loanSecurity.isEmpty()) {
                                        for (int l = 0; l < loanSecurity.size(); l++) {
                                            Vector vect = (Vector) loanSecurity.get(l);
                                            natureOfSec = natureOfSec.concat(vect.get(3).toString());
                                            matValue = matValue.add(new BigDecimal(vect.get(0).toString()));
                                        }
                                    }
                                    BigDecimal intAmt = new BigDecimal("0");
                                    String tableName = common.getTableName(acctNature);
                                    List npaRecon = em.createNativeQuery("select ifnull(sum(cramt-dramt),0)  from " + tableName + " where acno ='" + acno + "' and trantype ='8' and dt<='" + tillDate + "'").getResultList();
                                    if (!npaRecon.isEmpty()) {
                                        for (int l = 0; l < npaRecon.size(); l++) {
                                            Vector vect = (Vector) npaRecon.get(0);
                                            intAmt = new BigDecimal(vect.get(0).toString());
                                        }
                                    }
                                    pojo.setIntAmt(intAmt);
                                    pojo.setTypeOfLoan(common.getAcctDecription(acctType));
                                    pojo.setSancAmt(sancAmt);
                                    pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                    pojo.setPrincAmt(outStandBal.subtract(intAmt));
                                    pojo.setSecNat(natureOfSec);
                                    pojo.setSecVal(matValue);
                                    pojo.setNature(common.getRefRecDesc("187", secured));
                                    List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                            + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                            + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName,DateOfBirth from cbs_customer_master_detail"
                                            + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                    if (!custDetail.isEmpty()) {
                                        Vector vect = (Vector) custDetail.get(0);
                                        contact = vect.get(0).toString();
                                        perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                        currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                        fatherName = vect.get(13).toString();
                                        dob = vect.get(14).toString();
                                        pin = vect.get(12).toString();
                                    }
                                    List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table_name + " where acno='" + acno + "' and dt='" + lastCrDt + "'").getResultList();
                                    if (!tempList.isEmpty()) {
                                        Vector ele = (Vector) tempList.get(0);
                                        if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                            lastCrAmt = new BigDecimal(ele.get(0).toString());
                                        }
                                    }
                                    pojo.setAddress(perAdd);
                                    pojo.setCurrAddress(currAdd);
                                    pojo.setPin(pin);
                                    pojo.setContactNo(contact);
                                    pojo.setLastCrAmt(lastCrAmt);
                                    pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                    pojo.setDisburseDt(disbDt);
                                    pojo.setFatherName(fatherName);
                                    if (acctCategory.equalsIgnoreCase("IND")) {
                                        pojo.setRetirementAge(dmyFormat.format(ymdFormat.parse(CbsUtil.yearAdd(dob, Integer.parseInt(retirement)))));
                                    }
                                    dataList.add(pojo);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<NpaAccountDetailPojo> getNpaDetailOptimized(String acctNature, String acctType1, String tillDate, String brCode, String parameter, String excelParameter) throws ApplicationException {
        NumberFormat formatter = new DecimalFormat("#.##");
        /*Parameter =Y default. in case of OSS4 Industry Wise it will be N */
        List<NpaAccountDetailPojo> dataList = new ArrayList<NpaAccountDetailPojo>();
        try {
            List result = null, acTypeList = null, acNatureList;
            Vector tempVector, tempAcNatureVect, provVect;
            List<NpaStatusReportPojo> resultList = npaRemote.getNpaStatusReportDataOptimized("0", acctType1, "19000101", tillDate, "", brCode.equalsIgnoreCase("90") ? "0A" : brCode, "Y");
            List accountList = new ArrayList();
            List parameterStatus = em.createNativeQuery("select code from cbs_parameterinfo where name = 'NPA_STATUS'").getResultList();
            String reportParameter = "";
            if (!parameterStatus.isEmpty()) {
                Vector paraStatusvect = (Vector) parameterStatus.get(0);
                reportParameter = paraStatusvect.get(0).toString();
            }
            if (acctNature.equalsIgnoreCase("ALL")) {
                acNatureList = common.getAcctNatureOnlyDB();
                if (!acNatureList.isEmpty()) {
                    acTypeList = em.createNativeQuery("select Acctcode From accounttypemaster "
                            + " where acctNature in "
                            + "(select distinct acctNature From accounttypemaster "
                            + " where acctNature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "') "
                            + " and CrDbFlag in('B','D')) and CrDbFlag in('B','D')").getResultList();
                }
            } else {
                if (acctType1.equalsIgnoreCase("ALL")) {
                    acTypeList = em.createNativeQuery("select Acctcode From accounttypemaster "
                            + "where acctNature in('" + acctNature + "') and CrDbFlag in('B','D')").getResultList();
                } else {
                    acTypeList = em.createNativeQuery("select Acctcode From accounttypemaster "
                            + "where Acctcode in('" + acctType1 + "') and CrDbFlag in('B','D')").getResultList();
                }
            }
            List<OverdueEmiReportPojo> overdueTlList = new ArrayList<>();
            List<OverdueNonEmiResultList> overdueDlList = new ArrayList<>();
            List<OverdueNonEmiResultList> overdueCaList = new ArrayList<>();
            if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                overdueTlList = getOverdueEmiReport(1, 0, "", "ALL", 0, 5000, tillDate, brCode, "A", false, "", "N");
            } else if (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                overdueDlList = getOverDueNonEmi("DL", "ALL", tillDate, brCode, "N");
            } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                overdueDlList = getOverDueNonEmi("CA", "ALL", tillDate, brCode, "N");
            } else if (acctNature.equalsIgnoreCase("ALL")) {
                overdueTlList = getOverdueEmiReport(1, 0, "", "ALL", 0, 5000, tillDate, brCode, "A", false, "", "N");
                overdueDlList = getOverDueNonEmi("DL", "ALL", tillDate, brCode, "N");
                overdueCaList = getOverDueNonEmi("CA", "ALL", tillDate, brCode, "N");
            }
            List deliqListD = null, deliqListSub = null, deliqListL = null, deliqListStd = null;
            deliqListD = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) as days "
                    + "from cbs_scheme_delinquency_details where delinq_cycle in (select ref_code "
                    + "from cbs_ref_rec_type where ref_rec_no='207' and ref_code in('D1','D2','D3')) "
                    + "order by days").getResultList();
            if (deliqListD.isEmpty()) {
                throw new ApplicationException("Please fill data in cbs_ref_rec_type D.");
            }
            String subStdDesc = loanRemote.getRefRecDesc("SUB");
            String d1Desc = loanRemote.getRefRecDesc("D1");
            String d2Desc = loanRemote.getRefRecDesc("D2");
            String d3Desc = loanRemote.getRefRecDesc("D3");
            String lDesc = loanRemote.getRefRecDesc("L");
            String stdDesc = loanRemote.getRefRecDesc("STD");
            deliqListSub = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) from "
                    + "cbs_scheme_delinquency_details where delinq_cycle in (select ref_code from "
                    + "cbs_ref_rec_type where ref_rec_no='207' and ref_code in('SUB'))").getResultList();
            if (deliqListSub.isEmpty()) {
                throw new ApplicationException("Please fill data in cbs_ref_rec_type S.");
            }
            for (int n = 0; n < acTypeList.size(); n++) {
                System.out.println("Start time Loop" + new Date());
                tempVector = (Vector) acTypeList.get(n);
                String acctType = tempVector.get(0).toString();
                for (int i = 0; i < resultList.size(); i++) {
                    if (acctType.equalsIgnoreCase(resultList.get(i).getAcno().substring(2, 4))) {
                        System.out.println("I is " + i + "  And Time is  " + new Date());
                        NpaAccountDetailPojo pojo = new NpaAccountDetailPojo();
                        acctNature = resultList.get(i).getAcctNature();
                        String npaDt = "", newNpaDt = "", npaDtQuery = "", presentStatus = "";
                        String perAdd = "", currAdd = "", contact = "", disbDt = null, fatherName = "";
                        String dob = "", pin = "";
                        long newDayDiff = 0, dayDiff = 0;
                        double amtDis = 0d;
                        BigDecimal npaInt = new BigDecimal("0"), lastCrAmt = new BigDecimal("0");
                        String minUnPaidDt = "19000101", table_name = " loan_recon ";
                        String acno = resultList.get(i).getAcno();
                        String custname = resultList.get(i).getCustName();
                        String accStatus = resultList.get(i).getPresentStatus();
                        String secured = resultList.get(i).getSecured();
                        String retirement = resultList.get(i).getRetirementAge();
                        String acctCategory = resultList.get(i).getAcctCategory();
                        String lastCrDt = resultList.get(i).getLastCrDt();
                        npaInt = resultList.get(i).getNpaAmt();
                        amtDis = Double.parseDouble(resultList.get(i).getDisbAmt().toString());
                        disbDt = resultList.get(i).getDisbDt();
                        long currentStatusDayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt), ymdFormat.parse(tillDate));
                        BigDecimal outStandBal = resultList.get(i).getBalance();
                        pojo.setProvPerc(resultList.get(i).getProvPerc());
                        if (outStandBal.compareTo(new BigDecimal("0")) < 0) {
                            pojo.setProvAmt(resultList.get(i).getProvAmt());
                        } else {
                            pojo.setProvAmt(resultList.get(i).getProvAmt().multiply(new BigDecimal("-1")));
                        }
                        dayDiff = Long.parseLong(resultList.get(i).getCurrDayDiff());
                        npaDt = resultList.get(i).getNpaDate();
                        String delinqCycle = resultList.get(i).getDelinqCycleDesc();
                        BigDecimal chkLimit = resultList.get(i).getChkLimit();
                        String sancDate = resultList.get(i).getSanctDt();
                        BigDecimal sancAmt = resultList.get(i).getSancAmt();
                        String securedDesc = resultList.get(i).getSecuredDesc();
                        BigDecimal intAmt = resultList.get(i).getNpaInt();
                        lastCrAmt = resultList.get(i).getLastCrAmt();
                        minUnPaidDt = resultList.get(i).getMaxEmiDt();
                        String subStdDt = resultList.get(i).getSubDt();

                        if (accStatus.contains("SUB")) {
                            accStatus = "11";
                            npaDtQuery = "npadt";
                            presentStatus = "SUB";
                            pojo.setCurrentStatus(subStdDesc);
                            pojo.setSortAcStatus("11"); //This is used for sorting 
                            pojo.setStatus(subStdDesc);
                            pojo.setMarkFlag("U");                  //This account will not mark as NPA.
                            pojo.setAcStatus("11");
                        } else if (accStatus.contains("DOU")) {
                            accStatus = "12";
                            npaDtQuery = "dbtdt";
                            presentStatus = "DOU";
                            pojo.setMarkFlag("U");                      //This account will not mark as NPA.
                        } else if (accStatus.contains("LOS")) {
                            accStatus = "13";
                            npaDtQuery = "dcdt";
                            presentStatus = "LOS";
                            pojo.setCurrentStatus(lDesc);
                            pojo.setSortAcStatus("13"); //This is used for sorting 
                            pojo.setStatus(lDesc);
                            pojo.setMarkFlag("U");                      //This account will not mark as NPA.
                        } else {
                            accStatus = "1";
                            npaDtQuery = "STD";
                            presentStatus = "STD";
                        }
                        pojo.setAcNo(acno);
                        pojo.setCustName(custname);
                        pojo.setAcStatus(accStatus);
                        pojo.setCurrentStatusNoOfDays(currentStatusDayDiff);
                        pojo.setOutstdBal(new BigDecimal(resultList.get(i).getBalance().toString()));
                        pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                        pojo.setNpaAmt(npaInt);
                        pojo.setEmiExist("N");
                        pojo.setStatus(delinqCycle);
                        pojo.setNpaDate(dmyFormat.format(ymdFormat.parse(npaDt)));
                        pojo.setDueDt(dmyFormat.format(ymdFormat.parse(npaDt)));
                        List limitList = null;
                        Vector limitVec = null;


                        if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            List<OverdueNonEmiResultList> OverDueList = new ArrayList<>();

                            if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                table_name = "ca_recon";
                                OverDueList = overdueCaList;
                            } else {
                                table_name = "loan_recon";
                                OverDueList = overdueDlList;
                            }
                            /**
                             * If outstanding balance is less than chkLimit and
                             * there is no credit for previous days(Ex:
                             * Substandard-90,doubtful-365 days)*
                             */
                            if (outStandBal.compareTo(chkLimit) <= 0) {
                                if (accStatus.equals("11") || accStatus.equals("12") || accStatus.equals("13")) {
                                    if (parameter.equalsIgnoreCase("Y")) {
                                        if (accStatus.equalsIgnoreCase("11")) {
//                                            List minEffList = em.createNativeQuery("SELECT date_format(MAX(EFFDT),'%d/%m/%Y') FROM accountstatus a, codebook c WHERE ACNO='" + acno + "' AND SUBSTRING(description,1,3)='" + presentStatus + "' AND GROUPCODE = 3 and a.spflag = c.code and effdt <='" + tillDate + "'").getResultList();
//                                            Vector minElement = (Vector) minEffList.get(0);
//                                            String npaDate = minElement.get(0).toString();
                                            limitVec = (Vector) deliqListSub.get(0);
                                            long subDays = Long.parseLong(limitVec.get(1).toString());
                                            if (dayDiff > subDays) {
                                                pojo.setAcStatus("12");
                                                pojo.setStatus(d1Desc);
                                                pojo.setMarkFlag("M");              //This account will mark as NPA.
                                                pojo.setTotalDayDiff(dayDiff);
                                                pojo.setCurrentStatusNoOfDays(dayDiff - subDays);
                                                pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(npaDt, (int) subDays))));
                                            }
                                        } else if (accStatus.equalsIgnoreCase("12")) {
                                            newNpaDt = npaDt;
                                            String cycle = "";
                                            for (int j = 0; j < deliqListD.size(); j++) {
                                                limitVec = (Vector) deliqListD.get(j);
                                                if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                                                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                        pojo.setCurrentStatus(d1Desc);
                                                        pojo.setSortAcStatus("12D1"); //This is used for sorting 
                                                        cycle = limitVec.get(0).toString();
                                                        newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
                                                        break;
                                                    }
                                                } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                                                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                        pojo.setCurrentStatus(d2Desc);
                                                        pojo.setSortAcStatus("12D2"); //This is used for sorting 
                                                        cycle = limitVec.get(0).toString();
                                                        newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
                                                        break;
                                                    }
                                                } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                                                    pojo.setCurrentStatus(d3Desc);
                                                    pojo.setSortAcStatus("12D3"); //This is used for sorting 
                                                    cycle = limitVec.get(0).toString();
                                                    newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
                                                    break;
                                                }
                                                newDayDiff = Long.parseLong(limitVec.get(1).toString());
                                            }
////                                            pojo.setStatus(loanRemote.getRefRecDesc(cycle));
                                            pojo.setMarkFlag("U");                      //This account will not mark as NPA.
                                            pojo.setCurrentStatusNoOfDays(dayDiff - newDayDiff);
                                            pojo.setTotalDayDiff(dayDiff);
                                            pojo.setDueDt(dmyFormat.format(ymdFormat.parse(newNpaDt)));
                                            pojo.setNpaDate(dmyFormat.format(ymdFormat.parse(newNpaDt)));
                                            pojo.setAcStatus("12");
                                        } else if (accStatus.equalsIgnoreCase("13")) {
                                            pojo.setCurrentStatus(lDesc);
                                            pojo.setSortAcStatus("13"); //This is used for sorting 
//                                            pojo.setStatus(loanRemote.getRefRecDesc("L"));
                                            pojo.setMarkFlag("U");                      //This account will not mark as NPA.                                            
                                            pojo.setAcStatus("13");
                                        }
                                        if (!OverDueList.isEmpty()) {
                                            for (int l = 0; l < OverDueList.size(); l++) {
                                                OverdueNonEmiResultList vect = OverDueList.get(l);
                                                if (vect.getAccountNo().equalsIgnoreCase(acno)) {
                                                    int noOfEmiOverDue = 0;
                                                    pojo.setOverDueAmt(new BigDecimal(vect.getOverDue()));
                                                    pojo.setNoOfPendingEmi(noOfEmiOverDue);
                                                }
                                            }
                                        }
                                    } else {
                                        pojo.setAcNo(acno);
                                        pojo.setCustName(custname);
                                        pojo.setAcStatus(accStatus);
                                        pojo.setOutstdBal(outStandBal);
                                        pojo.setNature(acctNature);
                                        if (accStatus.equalsIgnoreCase("11")) {
                                            pojo.setCurrentStatus(subStdDesc);
                                        } else if (accStatus.equalsIgnoreCase("12")) {
                                            for (int j = 0; j < deliqListD.size(); j++) {
                                                limitVec = (Vector) deliqListD.get(j);
                                                if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                                                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                        pojo.setCurrentStatus(d1Desc);
                                                        break;
                                                    }
                                                } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                                                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                        pojo.setCurrentStatus(d2Desc);
                                                        break;
                                                    }
                                                } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                                                    pojo.setCurrentStatus(d3Desc);
                                                    break;
                                                }
                                            }
                                        } else if (accStatus.equalsIgnoreCase("13")) {
                                            pojo.setCurrentStatus(lDesc);
                                        }
                                    }
                                    String natureOfSec = "";
                                    BigDecimal matValue = new BigDecimal("0");
                                    if (excelParameter.equalsIgnoreCase("Y")) {
                                        List loanSecurity = em.createNativeQuery("select ifnull(matvalue,0),ifnull(matdate,''),ifnull(lienvalue,0),ifnull(Particulars,'') from loansecurity where acno ='" + acno + "'").getResultList();
                                        if (!loanSecurity.isEmpty()) {
                                            for (int l = 0; l < loanSecurity.size(); l++) {
                                                Vector vect = (Vector) loanSecurity.get(l);
                                                natureOfSec = natureOfSec.concat(vect.get(3).toString());
                                                matValue = matValue.add(new BigDecimal(vect.get(0).toString()));
                                            }
                                        }
                                    }
                                    String subDt = "";
                                    if (accStatus.equals("12") || accStatus.equals("13")) {
                                        subDt = subStdDt;
                                    } else {
                                        subDt = pojo.getNpaDate();
                                    }
                                    pojo.setTypeOfLoan(common.getAcctDecription(acctType));
                                    pojo.setSancAmt(sancAmt);
                                    pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                    pojo.setIntAmt(intAmt);
                                    pojo.setPrincAmt(outStandBal.subtract(intAmt));
                                    pojo.setSecNat(natureOfSec);
                                    pojo.setSecVal(matValue);
                                    pojo.setNature(securedDesc);
                                    if (excelParameter.equalsIgnoreCase("Y")) {
                                        List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                                + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                                + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName, ifnull(DateOfBirth,'1900-01-01') from cbs_customer_master_detail"
                                                + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                        if (!custDetail.isEmpty()) {
                                            Vector vect = (Vector) custDetail.get(0);
                                            contact = vect.get(0).toString();
                                            perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                            currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                            fatherName = vect.get(13).toString();
                                            dob = vect.get(14).toString();
                                            pin = vect.get(12).toString();
                                        }
                                        if (acctCategory.equalsIgnoreCase("IND")) {
                                            pojo.setRetirementAge(dmyFormat.format(ymdFormat.parse(CbsUtil.yearAdd(dob, Integer.parseInt(retirement)))));
                                        }
                                    }
                                    pojo.setAddress(perAdd);
                                    pojo.setCurrAddress(currAdd);
                                    pojo.setPin(pin);
                                    pojo.setContactNo(contact);
                                    pojo.setLastCrAmt(lastCrAmt);
                                    pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                    pojo.setDisburseDt(disbDt);
                                    pojo.setSubDt(subDt);
                                    pojo.setFatherName(fatherName);
                                    dataList.add(pojo);
                                }
                            } /**
                             * If outstanding balance is greater than limit
                             * amount continuously for more than 90 days. while
                             * Npa account is same.
                             */
                            else if (outStandBal.compareTo(chkLimit) == 1) {
                                if (accStatus.equals("11") || accStatus.equals("12") || accStatus.equals("13")) {
                                    if (parameter.equalsIgnoreCase("Y")) {
                                        if (accStatus.equalsIgnoreCase("11")) {
//                                            List minEffList = em.createNativeQuery("SELECT date_format(MAX(EFFDT),'%d/%m/%Y') FROM accountstatus a, codebook c WHERE ACNO='" + acno + "' AND SUBSTRING(description,1,3)='" + presentStatus + "' AND GROUPCODE = 3 and a.spflag = c.code and effdt <='" + tillDate + "'").getResultList();
//                                            Vector minElement = (Vector) minEffList.get(0);
//                                            String npaDate = minElement.get(0).toString();
                                            limitVec = (Vector) deliqListSub.get(0);
                                            long subDays = Long.parseLong(limitVec.get(1).toString());
                                            if (dayDiff > subDays) {
                                                pojo.setAcStatus("12");
                                                pojo.setStatus(d1Desc);
                                                pojo.setMarkFlag("M");              //This account will mark as NPA.
                                                pojo.setTotalDayDiff(dayDiff);
                                                pojo.setCurrentStatusNoOfDays(dayDiff - subDays);
                                                pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(npaDt, (int) subDays))));
                                            }
                                        } else if (accStatus.equalsIgnoreCase("12")) {
                                            newNpaDt = npaDt;
                                            String cycle = "";
                                            for (int j = 0; j < deliqListD.size(); j++) {
                                                limitVec = (Vector) deliqListD.get(j);
                                                if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                                                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                        pojo.setCurrentStatus(d1Desc);
                                                        pojo.setSortAcStatus("12D1"); //This is used for sorting 
                                                        cycle = limitVec.get(0).toString();
                                                        newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
                                                        break;
                                                    }
                                                } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                                                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                        pojo.setCurrentStatus(d2Desc);
                                                        pojo.setSortAcStatus("12D2"); //This is used for sorting 
                                                        cycle = limitVec.get(0).toString();
                                                        newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
                                                        break;
                                                    }
                                                } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                                                    pojo.setCurrentStatus(d3Desc);
                                                    pojo.setSortAcStatus("12D3"); //This is used for sorting 
                                                    cycle = limitVec.get(0).toString();
                                                    newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
                                                    break;
                                                }
                                                newDayDiff = Long.parseLong(limitVec.get(1).toString());
                                            }
//                                            pojo.setStatus(loanRemote.getRefRecDesc(cycle));
                                            pojo.setMarkFlag("U");                      //This account will not mark as NPA.
                                            pojo.setCurrentStatusNoOfDays(dayDiff - newDayDiff);
                                            pojo.setTotalDayDiff(dayDiff);
                                            pojo.setDueDt(dmyFormat.format(ymdFormat.parse(newNpaDt)));
                                            pojo.setNpaDate(dmyFormat.format(ymdFormat.parse(newNpaDt)));
                                            pojo.setAcStatus("12");
                                        } else if (accStatus.equalsIgnoreCase("13")) {
                                            pojo.setCurrentStatus(lDesc);
                                            pojo.setSortAcStatus("13"); //This is used for sorting 
//                                            pojo.setStatus(loanRemote.getRefRecDesc("L"));
                                            pojo.setMarkFlag("U");                      //This account will not mark as NPA.
                                            pojo.setAcStatus("13");
                                        }
                                        if (!OverDueList.isEmpty()) {
                                            for (int l = 0; l < OverDueList.size(); l++) {
                                                OverdueNonEmiResultList vect = OverDueList.get(l);
                                                if (vect.getAccountNo().equalsIgnoreCase(acno)) {
                                                    int noOfEmiOverDue = 0;
                                                    pojo.setOverDueAmt(new BigDecimal(vect.getOverDue()));
                                                    pojo.setNoOfPendingEmi(noOfEmiOverDue);
                                                }
                                            }
                                        }
                                    } else {
                                        pojo.setAcNo(acno);
                                        pojo.setCustName(custname);
                                        pojo.setAcStatus(accStatus);
                                        pojo.setOutstdBal(outStandBal);
                                        pojo.setNature(acctNature);
                                        pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                        if (accStatus.equalsIgnoreCase("11")) {
                                            pojo.setCurrentStatus(subStdDesc);
                                        } else if (accStatus.equalsIgnoreCase("12")) {
                                            for (int j = 0; j < deliqListD.size(); j++) {
                                                limitVec = (Vector) deliqListD.get(j);
                                                if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                                                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                        pojo.setCurrentStatus(d1Desc);
                                                        break;
                                                    }
                                                } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                                                    if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                        pojo.setCurrentStatus(d2Desc);
                                                        break;
                                                    }
                                                } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                                                    pojo.setCurrentStatus(d3Desc);
                                                    break;
                                                }
                                            }
                                        } else if (accStatus.equalsIgnoreCase("13")) {
                                            pojo.setCurrentStatus(lDesc);
                                        }
                                    }
                                    String natureOfSec = "";
                                    BigDecimal matValue = new BigDecimal("0");
                                    if (excelParameter.equalsIgnoreCase("Y")) {
                                        List loanSecurity = em.createNativeQuery("select ifnull(matvalue,0),ifnull(matdate,''),ifnull(lienvalue,0),ifnull(Particulars,'') from loansecurity where acno ='" + acno + "'").getResultList();
                                        if (!loanSecurity.isEmpty()) {
                                            for (int l = 0; l < loanSecurity.size(); l++) {
                                                Vector vect = (Vector) loanSecurity.get(l);
                                                natureOfSec = natureOfSec.concat(vect.get(3).toString());
                                                matValue = matValue.add(new BigDecimal(vect.get(0).toString()));
                                            }
                                        }
                                    }
                                    String subDt = "";
                                    if (accStatus.equals("12") || accStatus.equals("13")) {
                                        subDt = subStdDt;
                                    } else {
                                        subDt = pojo.getNpaDate();
                                    }
                                    pojo.setIntAmt(intAmt);
                                    pojo.setTypeOfLoan(common.getAcctDecription(acctType));
                                    pojo.setSancAmt(sancAmt);
                                    pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                    pojo.setPrincAmt(outStandBal.subtract(intAmt));
                                    pojo.setSecNat(natureOfSec);
                                    pojo.setSecVal(matValue);
                                    pojo.setNature(securedDesc);
                                    if (excelParameter.equalsIgnoreCase("Y")) {
                                        List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                                + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                                + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName,DateOfBirth from cbs_customer_master_detail"
                                                + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                        if (!custDetail.isEmpty()) {
                                            Vector vect = (Vector) custDetail.get(0);
                                            contact = vect.get(0).toString();
                                            perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                            currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                            fatherName = vect.get(13).toString();
                                            dob = vect.get(14).toString();
                                            pin = vect.get(12).toString();
                                        }
                                    }
                                    pojo.setAddress(perAdd);
                                    pojo.setCurrAddress(currAdd);
                                    pojo.setPin(pin);
                                    pojo.setContactNo(contact);
                                    pojo.setLastCrAmt(lastCrAmt);
                                    pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                    pojo.setDisburseDt(disbDt);
                                    pojo.setSubDt(subDt);
                                    pojo.setFatherName(fatherName);
                                    if (excelParameter.equalsIgnoreCase("Y")) {
                                        if (acctCategory.equalsIgnoreCase("IND")) {
                                            pojo.setRetirementAge(dmyFormat.format(ymdFormat.parse(CbsUtil.yearAdd(dob, Integer.parseInt(retirement)))));
                                        }
                                    }
                                    dataList.add(pojo);
                                }
                            }
//                }
                        } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            List<OverdueEmiReportPojo> OverDueList = new ArrayList<>();

                            Integer unPaidCount = 0;
                            OverDueList = overdueTlList;
                            if (accStatus.equals("11") || accStatus.equals("12") || accStatus.equals("13")) {
                                //For NPA Account.
                                if (parameter.equalsIgnoreCase("Y")) {
                                    pojo.setLastEmiPaidDt(minUnPaidDt.equalsIgnoreCase("19000101") ? "" : dmyFormat.format(ymdFormat.parse(minUnPaidDt)));

                                    if (accStatus.equalsIgnoreCase("11")) {
//                                        List minEffList = em.createNativeQuery("SELECT date_format(MAX(EFFDT),'%d/%m/%Y') FROM accountstatus a, codebook c WHERE ACNO='" + acno + "' AND SUBSTRING(description,1,3)='" + presentStatus + "' AND GROUPCODE = 3 and a.spflag = c.code and effdt <='" + tillDate + "'").getResultList();
//                                        Vector minElement = (Vector) minEffList.get(0);
//                                        String npaDate = minElement.get(0).toString();
                                        limitVec = (Vector) deliqListSub.get(0);
                                        long subDays = Long.parseLong(limitVec.get(1).toString());
                                        if (dayDiff > subDays) {
                                            pojo.setAcStatus("12");
                                            pojo.setStatus(d1Desc);
                                            pojo.setMarkFlag("M");              //This account will mark as NPA.
                                            pojo.setTotalDayDiff(dayDiff);
                                            pojo.setCurrentStatusNoOfDays(dayDiff - subDays);
                                            pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(npaDt, (int) subDays))));
                                        }
                                    } else if (accStatus.equalsIgnoreCase("12")) {
                                        newNpaDt = npaDt;
                                        String cycle = "";

                                        for (int j = 0; j < deliqListD.size(); j++) {
                                            limitVec = (Vector) deliqListD.get(j);
                                            if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                                                if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                    pojo.setCurrentStatus(d1Desc);
                                                    pojo.setSortAcStatus("12D1"); //This is used for sorting 
                                                    cycle = limitVec.get(0).toString();
                                                    newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
                                                    break;
                                                }
                                            } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                                                if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                    pojo.setCurrentStatus(d2Desc);
                                                    pojo.setSortAcStatus("12D2"); //This is used for sorting 
                                                    cycle = limitVec.get(0).toString();
                                                    newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
                                                    break;
                                                }
                                            } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                                                pojo.setCurrentStatus(d3Desc);
                                                pojo.setSortAcStatus("12D3"); //This is used for sorting 
                                                cycle = limitVec.get(0).toString();
                                                newNpaDt = CbsUtil.dateAdd(newNpaDt, (int) newDayDiff);
                                                break;
                                            }
                                            newDayDiff = Long.parseLong(limitVec.get(1).toString());
                                        }
//                                        pojo.setStatus(loanRemote.getRefRecDesc(cycle));
                                        pojo.setMarkFlag("U");                      //This account will not mark as NPA.
                                        pojo.setCurrentStatusNoOfDays(dayDiff - newDayDiff);
                                        pojo.setTotalDayDiff(dayDiff);
                                        pojo.setDueDt(dmyFormat.format(ymdFormat.parse(newNpaDt)));
                                        pojo.setNpaDate(dmyFormat.format(ymdFormat.parse(newNpaDt)));
                                        pojo.setAcStatus("12");
                                    } else if (accStatus.equalsIgnoreCase("13")) {
                                        pojo.setCurrentStatus(lDesc);
                                        pojo.setSortAcStatus("13"); //This is used for sorting 
//                                        pojo.setStatus(loanRemote.getRefRecDesc("L"));
                                        pojo.setMarkFlag("U");                      //This account will not mark as NPA.                                        
                                        pojo.setAcStatus("13");
                                    }
                                    if (!OverDueList.isEmpty()) {
                                        for (int l = 0; l < OverDueList.size(); l++) {
                                            OverdueEmiReportPojo vect = OverDueList.get(l);
                                            if (vect.getAccountNumber().equalsIgnoreCase(acno)) {
                                                int noOfEmiOverDue = 0;
                                                noOfEmiOverDue = vect.getNoOfEmiOverdue();
                                                pojo.setOverDueAmt(vect.getAmountOverdue());
                                                pojo.setNoOfPendingEmi(noOfEmiOverDue);
                                            }
                                        }
                                    }
                                } else {
                                    pojo.setAcNo(acno);
                                    pojo.setCustName(custname);
                                    pojo.setAcStatus(accStatus);
                                    pojo.setOutstdBal(outStandBal);
                                    pojo.setNature(acctNature);
                                    pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                    if (accStatus.equalsIgnoreCase("11")) {
                                        pojo.setCurrentStatus(subStdDesc);
                                    } else if (accStatus.equalsIgnoreCase("12")) {
                                        for (int j = 0; j < deliqListD.size(); j++) {
                                            limitVec = (Vector) deliqListD.get(j);
                                            if (limitVec.get(0).toString().equalsIgnoreCase("D1")) {
                                                if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                    pojo.setCurrentStatus(d1Desc);
                                                    break;
                                                }
                                            } else if (limitVec.get(0).toString().equalsIgnoreCase("D2")) {
                                                if (dayDiff <= Long.parseLong(limitVec.get(1).toString())) {
                                                    pojo.setCurrentStatus(d2Desc);
                                                    break;
                                                }
                                            } else if (limitVec.get(0).toString().equalsIgnoreCase("D3")) {
                                                pojo.setCurrentStatus(d3Desc);
                                                break;
                                            }
                                        }
                                    } else if (accStatus.equalsIgnoreCase("13")) {
                                        pojo.setCurrentStatus(lDesc);
                                    }
                                }
                                String natureOfSec = "";
                                BigDecimal matValue = new BigDecimal("0");
                                if (excelParameter.equalsIgnoreCase("Y")) {
                                    List loanSecurity = em.createNativeQuery("select ifnull(matvalue,0),ifnull(matdate,''),ifnull(lienvalue,0),ifnull(Particulars,'') from loansecurity where acno ='" + acno + "'").getResultList();
                                    if (!loanSecurity.isEmpty()) {
                                        for (int l = 0; l < loanSecurity.size(); l++) {
                                            Vector vect = (Vector) loanSecurity.get(l);
                                            natureOfSec = natureOfSec.concat(vect.get(3).toString());
                                            matValue = matValue.add(new BigDecimal(vect.get(0).toString()));
                                        }
                                    }
                                }
                                String subDt = "";
                                if (accStatus.equals("12") || accStatus.equals("13")) {
                                    subDt = subStdDt;
                                } else {
                                    subDt = pojo.getNpaDate();
                                }
                                pojo.setIntAmt(intAmt);
                                pojo.setTypeOfLoan(common.getAcctDecription(acctType));
                                pojo.setSancAmt(sancAmt);
                                pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                pojo.setPrincAmt(outStandBal.subtract(intAmt));
                                pojo.setSecNat(natureOfSec);
                                pojo.setSecVal(matValue);
                                pojo.setNature(securedDesc);
                                if (excelParameter.equalsIgnoreCase("Y")) {
                                    List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                            + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                            + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName,DateOfBirth from cbs_customer_master_detail"
                                            + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                    if (!custDetail.isEmpty()) {
                                        Vector vect = (Vector) custDetail.get(0);
                                        contact = vect.get(0).toString();
                                        perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                        currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                        fatherName = vect.get(13).toString();
                                        dob = vect.get(14).toString();
                                        pin = vect.get(12).toString();
                                    }
                                }
                                pojo.setAddress(perAdd);
                                pojo.setCurrAddress(currAdd);
                                pojo.setPin(pin);
                                pojo.setContactNo(contact);
                                pojo.setLastCrAmt(lastCrAmt);
                                pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                pojo.setDisburseDt(disbDt);
                                pojo.setFatherName(fatherName);
                                if (excelParameter.equalsIgnoreCase("Y")) {
                                    if (acctCategory.equalsIgnoreCase("IND")) {
                                        pojo.setRetirementAge(dmyFormat.format(ymdFormat.parse(CbsUtil.yearAdd(dob, Integer.parseInt(retirement)))));
                                    }
                                }
                                pojo.setSubDt(subDt);
                                dataList.add(pojo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<NpaAccountDetailPojo> getProbableNpaDetail(String acctNature, String acctType1, String tillDate, String brCode) throws ApplicationException {
        NumberFormat formatter = new DecimalFormat("#.##");
        List<NpaAccountDetailPojo> dataList = new ArrayList<NpaAccountDetailPojo>();
        try {
            String flag = "True";
            String bnkCode = fts.getBankCode();
            List<OverdueEmiReportPojo> OverdueTlList = getOverdueEmiReport(1, 0, "", "All", 1, 5000, tillDate, "0A", "A", false, "", "N");

            Map overDueMap = new HashMap<>();
            if (bnkCode.equalsIgnoreCase("army")) {
                flag = "false";
                List<OverDueListPojo> armyOverDueList = ddsRemoteFacade.getOverDueListData(brCode, acctType1, tillDate, "");
                for (int i = 0; i < armyOverDueList.size(); i++) {
                    overDueMap.put(armyOverDueList.get(i).getAcName(), armyOverDueList.get(i).getAcName());
                }
            }

            System.out.println("Start Date:" + new Date());
            int isExceessEmi = common.isExceessEmi(tillDate);
            List result = null, acTypeList = null;
            Vector tempVector, tempAcNatureVect;
            String query = "select a.acno from accountstatus a, "
                    + "(select acno,max(effdt) as edt from accountstatus where effdt <='" + tillDate + "' group by acno) b,"
                    + "(select acno,max(spno) as sno from accountstatus where effdt <='" + tillDate + "' group by acno) c ,"
                    + "accountmaster ac where a.acno = b.acno and a.acno = c.acno and a.acno = ac.acno and "
                    + "a.effdt = b.edt and a.spno = c.sno and a.spflag in (11,12,13) and a.effdt <='" + tillDate + "' and "
                    + "(ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + tillDate + "')";
            List chkList = em.createNativeQuery("select ifnull(CODE,'') From cbs_parameterinfo where NAME = 'PROBABLE_DL_AC_TYPE' ").getResultList();
            String dlQuery = "", dlQueryAcNat = "";
            if (!chkList.isEmpty()) {
                Vector dlVect = (Vector) chkList.get(0);
                String dlAcType = dlVect.get(0).toString();
                dlQuery = " select Acctcode From accounttypemaster where acctcode in ('" + dlAcType + "') and CrDbFlag in('B','D') ";
                dlQueryAcNat = " union  select Acctcode From accounttypemaster where acctcode in ('" + dlAcType + "') and CrDbFlag in('B','D') ";
            }
            if (acctNature.equalsIgnoreCase("ALL")) {
//                acNatureList = common.getAcctNatureOnlyDB();
                acTypeList = em.createNativeQuery("select Acctcode From accounttypemaster "
                        + " where acctNature in "
                        + "(select distinct acctNature From accounttypemaster "
                        + " where acctNature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.TERM_LOAN + "'/*,'" + CbsConstant.DEMAND_LOAN + "'*/) "
                        + " and CrDbFlag in('B','D')) and CrDbFlag in('B','D')  " + dlQueryAcNat + " ").getResultList();
            } else {
                if (!acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    if (acctType1.equalsIgnoreCase("ALL")) {
                        //                    result = common.getAcctTypeAsAcNatureOnlyDB(acctNature);
                        acTypeList = em.createNativeQuery("select Acctcode From accounttypemaster "
                                + "where acctNature in('" + acctNature + "') and CrDbFlag in('B','D')").getResultList();
                    } else {
                        acTypeList = em.createNativeQuery("select Acctcode From accounttypemaster "
                                + "where Acctcode in('" + acctType1 + "') and CrDbFlag in('B','D')").getResultList();
                    }
                } else {
                    acTypeList = em.createNativeQuery(dlQuery).getResultList();
                }
            }
            for (int n = 0; n < acTypeList.size(); n++) {
                tempVector = (Vector) acTypeList.get(n);
                String acctType = tempVector.get(0).toString();
                acctNature = fts.getAcNatureByCode(acctType);
                List accountList = new ArrayList();
                if (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    if (brCode.equalsIgnoreCase("0A")) {
                        accountList = em.createNativeQuery("select aa.acno,aa.custname,aa.accstatus, aa.openingdt,"
                                + " (select cast(ifnull((sum(ifnull(cramt,0))-sum(ifnull(dramt,0))),0) as decimal(25,2)) from loan_recon where auth= 'y' and acno = aa.acno and dt <='" + tillDate + "') as bal,"
                                + " ifnull(la.odlimit,0) , ifnull(a.loan_pd_month,0) "
                                + " from cbs_loan_acc_mast_sec a , accountmaster aa left join loan_appparameter la on la.Acno = aa.ACNo "
                                + " where (aa.CLOSINGDATE='' OR ifnull(aa.CLOSINGDATE,'')='' OR aa.CLOSINGDATE>'" + tillDate + "') and aa.Accttype = '" + acctType + "' and a.acno= aa.acno and aa.OpeningDt < '" + tillDate + "' and aa.acno not in (" + query + ") order by aa.AccStatus, aa.acno").getResultList();
                    } else {
                        accountList = em.createNativeQuery("select aa.acno,aa.custname,aa.accstatus, aa.openingdt,"
                                + " (select cast(ifnull((sum(ifnull(cramt,0))-sum(ifnull(dramt,0))),0) as decimal(25,2)) from loan_recon where auth= 'y' and acno = aa.acno and dt <='" + tillDate + "') as bal, "
                                + " ifnull(la.odlimit,0) , ifnull(a.loan_pd_month,0) "
                                + " from cbs_loan_acc_mast_sec a , accountmaster aa  left join loan_appparameter la on la.Acno = aa.ACNo "
                                + " where (aa.CLOSINGDATE='' OR ifnull(aa.CLOSINGDATE,'')='' OR aa.CLOSINGDATE>'" + tillDate + "') and aa.Accttype = '" + acctType + "' and a.acno= aa.acno and aa.OpeningDt < '" + tillDate + "' and aa.acno not in (" + query + ") and aa.curbrcode = '" + brCode + "' order by aa.AccStatus, aa.acno").getResultList();
                    }
                    if (!accountList.isEmpty()) {
                        List limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) from "
                                + "cbs_scheme_delinquency_details where delinq_cycle in (select ref_code from "
                                + "cbs_ref_rec_type where ref_rec_no='207' and ref_code in('STD'))").getResultList();
                        Vector limitVec = (Vector) limitList.get(0);
                        long stdDays = Long.parseLong(limitVec.get(1).toString());
                        for (int i = 0; i < accountList.size(); i++) {
                            NpaAccountDetailPojo pojo = new NpaAccountDetailPojo();
                            Vector element = (Vector) accountList.get(i);
                            String acno = element.get(0).toString();
                            String custname = element.get(1).toString();
                            String accStatus = element.get(2).toString();
                            String openingDt = element.get(3).toString();
                            String loanStatus = interBranchFacade.fnGetLoanStatusTillDate(acno, tillDate);
                            BigDecimal outStandBal = new BigDecimal(element.get(4).toString());
                            String period = element.get(6).toString();
                            String tenure = CbsUtil.monthAdd(openingDt, Integer.parseInt(element.get(6).toString()));
                            long dayDifference = CbsUtil.dayDiff(ymdFormat.parse(tenure), ymdFormat.parse(tillDate));
                            String perAdd = "", currAdd = "", contact = "", sancDate = "", disbDt = null, fatherName = "";
                            double amtDis = 0d;
                            BigDecimal lastCrAmt = new BigDecimal("0"), sancAmt = new BigDecimal("0"), chkLimit = new BigDecimal("0");
                            if (loanStatus.equalsIgnoreCase("DOU")) {
                                accStatus = "12";
                            } else if (loanStatus.equalsIgnoreCase("SUB")) {
                                accStatus = "11";
                            } else if (loanStatus.equalsIgnoreCase("LOS")) {
                                accStatus = "13";
                            } else if (loanStatus.equalsIgnoreCase("OPE") || loanStatus.equalsIgnoreCase("STA")) {
                                accStatus = "1";
                            } else {
                                accStatus = "1";
                            }
                            List sanctionDtList = em.createNativeQuery("select ifnull(Sanctionlimit,0),ifnull(Sanctionlimitdt,'') from loan_appparameter where acno =  '" + acno + "' ").getResultList();
                            if (!sanctionDtList.isEmpty()) {
                                Vector vist = (Vector) sanctionDtList.get(0);
                                sancAmt = new BigDecimal(vist.get(0).toString());
                                sancDate = vist.get(1).toString();
                            }
                            List result5 = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0), date_format(ifnull(min(dt),'19000101'),'%d/%m/%Y')  from loan_recon   where dt <= '" + tillDate + "'   and trandesc in (6,0)  and acno='" + acno + "'").getResultList();
                            if (!result5.isEmpty()) {
                                Vector vtrsub = (Vector) result5.get(0);
                                amtDis = Double.parseDouble(vtrsub.get(0).toString());
                                disbDt = vtrsub.get(1).toString();
                            }
                            if (accStatus.equalsIgnoreCase("1")) {
                                List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, acLimit from loan_oldinterest "
                                        + "where acno =  '" + acno + "' and txnid = "
                                        + "(select min(TXNID) from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + tillDate + "' )").getResultList();
                                if (!sanctionLimitDtList.isEmpty()) {
                                    Vector vist = (Vector) sanctionLimitDtList.get(0);
                                    chkLimit = new BigDecimal(vist.get(1).toString());
                                } else {
                                    sanctionLimitDtList = em.createNativeQuery("select ifnull(odlimit,0) from loan_appparameter  where acno = '" + acno + "' ").getResultList();
                                    if (!sanctionLimitDtList.isEmpty()) {
                                        Vector vist = (Vector) sanctionLimitDtList.get(0);
                                        chkLimit = new BigDecimal(vist.get(0).toString());
                                    }
                                }
                                if (outStandBal.compareTo(new BigDecimal("0")) < 0) {
                                    /**
                                     * If outstanding balance is less than
                                     * chkLimit and there is no credit for
                                     * previous days
                                     * (Ex:Substandard-90,doubtful-365 days)*
                                     */
                                    long dayDiff = 0;
                                    limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                            + common.getTableName(common.getAcNatureByAcType(acno.substring(2, 4))) + " where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
                                    limitVec = (Vector) limitList.get(0);
                                    String lastCrDt = limitVec.get(0).toString();
                                    List havePositiveBal = em.createNativeQuery("select ifnull(max(aa.dt),'" + lastCrDt + "')  from "
                                            + " (select a.acno, date_format( a.dt,'%Y%m%d') as dt,(select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from loan_recon where auth= 'y' and acno = '" + acno + "' and dt <=a.dt ) as bal "
                                            + " from loan_recon a where a.auth= 'y' and a.acno = '" + acno + "' and "
                                            + " a.dt between (select max(aa.dt) from "
                                            + " (select a.acno, date_format( a.dt,'%Y%m%d') as dt,(select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from loan_recon where auth= 'y' and acno = '" + acno + "' and dt <=a.dt ) as bal "
                                            + " from loan_recon a where a.auth= 'y' and a.acno = '" + acno + "' and a.dt between '" + lastCrDt + "' and '" + tillDate + "' group by dt)aa where aa.bal>=0) "
                                            + " and '" + tillDate + "' group by dt)aa where aa.bal<0 ").getResultList();
                                    if (!havePositiveBal.isEmpty()) {
                                        Vector posVect = (Vector) havePositiveBal.get(0);
                                        lastCrDt = posVect.get(0).toString();
                                    }
//                                            dayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt), ymdFormat.parse(tillDate));
                                    if (dayDifference > stdDays) {
                                        pojo.setAcNo(acno);
                                        pojo.setCustName(custname);
                                        pojo.setOutstdBal(outStandBal);
                                        pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(lastCrDt, (int) stdDays))));
                                        pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
                                        pojo.setSortAcStatus("1"); //This is used for sorting 
                                        pojo.setCurrentStatusNoOfDays(dayDifference);
                                        pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                        pojo.setNoOfPendingEmi(dayDifference - stdDays);
                                        pojo.setOverDueAmt(loanRemote.getOverDueAmount(acno, tillDate));
                                        pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
                                        pojo.setMarkFlag("M");      //This account will mark as NPA.
                                        pojo.setEmiExist("Y");
                                        pojo.setAcStatus(accStatus);
                                        List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                                + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                                + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName from cbs_customer_master_detail"
                                                + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                        if (!custDetail.isEmpty()) {
                                            Vector vect = (Vector) custDetail.get(0);
                                            contact = vect.get(0).toString();
                                            perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                            currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                            fatherName = vect.get(13).toString();
                                        }
                                        List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from loan_recon where acno='" + acno + "' and dt='" + lastCrDt + "'").getResultList();
                                        if (!tempList.isEmpty()) {
                                            Vector ele = (Vector) tempList.get(0);
                                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                                lastCrAmt = new BigDecimal(ele.get(0).toString());
                                            }
                                        }
                                        pojo.setAddress(perAdd);
                                        pojo.setCurrAddress(currAdd);
                                        pojo.setContactNo(contact);
                                        pojo.setLastCrAmt(lastCrAmt);
                                        pojo.setSancAmt(sancAmt);
                                        pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                        pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                        pojo.setDisburseDt(disbDt);
                                        pojo.setFatherName(fatherName);
                                        dataList.add(pojo);
                                    }
                                }
                            }
                        }
                    }
                    /**
                     * It is pending to be done.
                     */
                } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    if (brCode.equalsIgnoreCase("0A")) {
                        accountList = em.createNativeQuery("select aa.acno,aa.custname,aa.accstatus, aa.openingdt,"
                                /*+ " ifnull((Select c.description from accountstatus a,codebook c where acno=aa.acno "
                                 + " and effdt=(Select max(Effdt) from accountstatus where date_format(EffDt,'%Y%m%d')<='" + tillDate + "' and acno=aa.acno "
                                 + " and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                                 + " and spno=(Select max(Spno) from accountstatus where acno=aa.acno and date_format(EffDt,'%Y%m%d')<='" + tillDate + "' "
                                 + " and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))) "
                                 + " and spno=(Select max(Spno) from accountstatus where acno=aa.acno and date_format(EffDt,'%Y%m%d')<='" + tillDate + "' and (spflag in (11,12,13,14,3,6,7,8,2) or "
                                 + " (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%'))) "
                                 + " and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                                 + " AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 ),'STANDARD') as status,"*/
                                + " (select cast(ifnull((sum(ifnull(cramt,0))-sum(ifnull(dramt,0))),0) as decimal(25,2)) from ca_recon where auth= 'y' and acno = aa.acno and dt <='" + tillDate + "') as bal,"
                                + " ifnull(la.odlimit,0)  "
                                + " from accountmaster aa left join loan_appparameter la on la.Acno = aa.ACNo "
                                + " where (aa.CLOSINGDATE='' OR ifnull(aa.CLOSINGDATE,'')='' OR aa.CLOSINGDATE>'" + tillDate + "') and aa.Accttype = '" + acctType + "' and aa.OpeningDt < '" + tillDate + "' and aa.acno not in (" + query + ") order by aa.AccStatus, aa.acno").getResultList();
                    } else {
                        accountList = em.createNativeQuery("select aa.acno,aa.custname,aa.accstatus, aa.openingdt,"
                                /*+ " ifnull((Select c.description from accountstatus a,codebook c where acno=aa.acno "
                                 + " and effdt=(Select max(Effdt) from accountstatus where date_format(EffDt,'%Y%m%d')<='" + tillDate + "' and acno=aa.acno "
                                 + " and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                                 + " and spno=(Select max(Spno) from accountstatus where acno=aa.acno and date_format(EffDt,'%Y%m%d')<='" + tillDate + "' "
                                 + " and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))) "
                                 + " and spno=(Select max(Spno) from accountstatus where acno=aa.acno and date_format(EffDt,'%Y%m%d')<='" + tillDate + "' and (spflag in (11,12,13,14,3,6,7,8,2) or "
                                 + " (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%'))) "
                                 + " and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                                 + " AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 ),'STANDARD') as status, "*/
                                + " (select cast(ifnull((sum(ifnull(cramt,0))-sum(ifnull(dramt,0))),0) as decimal(25,2)) from ca_recon where auth= 'y' and acno = aa.acno and dt <='" + tillDate + "') as bal, "
                                + " ifnull(la.odlimit,0)  "
                                + " from accountmaster aa  left join loan_appparameter la on la.Acno = aa.ACNo "
                                + " where (aa.CLOSINGDATE='' OR ifnull(aa.CLOSINGDATE,'')='' OR aa.CLOSINGDATE>'" + tillDate + "') and aa.Accttype = '" + acctType + "' and aa.OpeningDt < '" + tillDate + "' and aa.acno not in (" + query + ") and aa.curbrcode = '" + brCode + "' order by aa.AccStatus, aa.acno").getResultList();
                    }
                    if (!accountList.isEmpty()) {
//                        throw new ApplicationException("There is no data to mark NPA.");
//                    }
                        List limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) from "
                                + "cbs_scheme_delinquency_details where delinq_cycle in (select ref_code from "
                                + "cbs_ref_rec_type where ref_rec_no='207' and ref_code in('STD'))").getResultList();
                        Vector limitVec = (Vector) limitList.get(0);
                        long stdDays = Long.parseLong(limitVec.get(1).toString());
                        for (int i = 0; i < accountList.size(); i++) {
                            NpaAccountDetailPojo pojo = new NpaAccountDetailPojo();
                            Vector element = (Vector) accountList.get(i);
                            String acno = element.get(0).toString();
                            if (acno.equalsIgnoreCase("013100076901")) {
                                System.out.println("acno:");
                            }
                            String custname = element.get(1).toString();
                            String accStatus = element.get(2).toString();
                            String openingDt = element.get(3).toString();
                            String loanStatus = interBranchFacade.fnGetLoanStatusTillDate(acno, tillDate);
                            BigDecimal outStandBal = new BigDecimal(element.get(4).toString());
//                            BigDecimal chkLimit = new BigDecimal(element.get(5).toString());
                            String perAdd = "", currAdd = "", contact = "", sancDate = "", disbDt = null, fatherName = "";
                            double amtDis = 0d;
                            BigDecimal lastCrAmt = new BigDecimal("0"), sancAmt = new BigDecimal("0"), chkLimit = new BigDecimal("0");
                            if (loanStatus.equalsIgnoreCase("DOU")) {
                                accStatus = "12";
                            } else if (loanStatus.equalsIgnoreCase("SUB")) {
                                accStatus = "11";
                            } else if (loanStatus.equalsIgnoreCase("LOS")) {
                                accStatus = "13";
                            } else if (loanStatus.equalsIgnoreCase("OPE") || loanStatus.equalsIgnoreCase("STA")) {
                                accStatus = "1";
                            } else {
                                accStatus = "1";
                            }
                            List sanctionDtList = em.createNativeQuery("select ifnull(Sanctionlimit,0),ifnull(Sanctionlimitdt,'') from loan_appparameter where acno =  '" + acno + "' ").getResultList();
                            if (!sanctionDtList.isEmpty()) {
                                Vector vist = (Vector) sanctionDtList.get(0);
                                sancAmt = new BigDecimal(vist.get(0).toString());
                                sancDate = vist.get(1).toString();
                            }
//                            String table_name = "";
//                            if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                                table_name = "ca_recon";
//                            } else {
//                                table_name = "loan_recon";
//                            }
                            List result5 = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0), date_format(ifnull(min(dt),'19000101'),'%d/%m/%Y')  from ca_recon   where dt <= '" + tillDate + "'   and trandesc in (6,0)  and acno='" + acno + "'").getResultList();
                            if (!result5.isEmpty()) {
                                Vector vtrsub = (Vector) result5.get(0);
                                amtDis = Double.parseDouble(vtrsub.get(0).toString());
                                disbDt = vtrsub.get(1).toString();
                            }
                            if (accStatus.equalsIgnoreCase("1")) {
//                            BigDecimal chkLimit = getCheckLimit(acno);
//                                BigDecimal chkLimit = new BigDecimal("0");
                                List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, acLimit from loan_oldinterest "
                                        + "where acno =  '" + acno + "' and txnid = "
                                        + "(select min(TXNID) from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + tillDate + "' )").getResultList();
                                if (!sanctionLimitDtList.isEmpty()) {
                                    Vector vist = (Vector) sanctionLimitDtList.get(0);
                                    chkLimit = new BigDecimal(vist.get(1).toString());
                                    sancAmt = chkLimit;
                                    sancDate = vist.get(0).toString();
                                } else {
                                    sanctionLimitDtList = em.createNativeQuery("select ifnull(odlimit,0) from loan_appparameter  where acno = '" + acno + "' ").getResultList();
                                    if (!sanctionLimitDtList.isEmpty()) {
                                        Vector vist = (Vector) sanctionLimitDtList.get(0);
                                        chkLimit = new BigDecimal(vist.get(0).toString());
                                        sancAmt = chkLimit;
                                    }
                                }
                                /**
                                 * If outstanding balance is less than chkLimit
                                 * and there is no credit for previous days
                                 * (Ex:Substandard-90,doubtful-365 days)*
                                 */
                                if (outStandBal.compareTo(new BigDecimal("0")) < 0) {
                                    if (outStandBal.abs().compareTo(chkLimit) != 1) {
                                        long dayDiff = 0;
                                        if (accStatus.equals("11") || accStatus.equals("12") || accStatus.equals("13")) {
                                            pojo = loanRemote.npaAccountAddition(accStatus, tillDate, acno, custname, outStandBal, acctNature);
                                            dataList.add(pojo);
                                        } else {
                                            limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                                    + common.getTableName(common.getAcNatureByAcType(acno.substring(2, 4))) + " where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
                                            limitVec = (Vector) limitList.get(0);
                                            String lastCrDt = limitVec.get(0).toString();
                                            if (!(ymdFormat.parse(openingDt).after(ymdFormat.parse(lastCrDt)))) {
                                                List havePositiveBal = em.createNativeQuery("select ifnull(max(aa.dt),'" + lastCrDt + "')  from "
                                                        + " (select a.acno, date_format( a.dt,'%Y%m%d') as dt,(select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from ca_recon where auth= 'y' and acno = '" + acno + "' and dt <=a.dt ) as bal "
                                                        + " from ca_recon a where a.auth= 'y' and a.acno = '" + acno + "' and "
                                                        + " a.dt between (select max(aa.dt) from "
                                                        + " (select a.acno, date_format( a.dt,'%Y%m%d') as dt,(select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from ca_recon where auth= 'y' and acno = '" + acno + "' and dt <=a.dt ) as bal "
                                                        + " from ca_recon a where a.auth= 'y' and a.acno = '" + acno + "' and a.dt between '" + lastCrDt + "' and '" + tillDate + "' group by dt)aa where aa.bal>=0) "
                                                        + " and '" + tillDate + "' group by dt)aa where aa.bal<0 ").getResultList();
                                                if (!havePositiveBal.isEmpty()) {
                                                    Vector posVect = (Vector) havePositiveBal.get(0);
                                                    lastCrDt = posVect.get(0).toString();
                                                }
                                                dayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt), ymdFormat.parse(tillDate));
                                                //                                    System.out.println("<<<acno:" + acno + "; dateDiff:" + dayDiff + "; lastCrDt:" + lastCrDt + "; openingDt:" + openingDt);
                                                if (dayDiff > stdDays) {
                                                    // Add For Substandard
                                                    /*Add on Lien Deposit Amt< OutStandBalance*/
                                                    double lienDepositAmt = 0;
                                                    List list = em.createNativeQuery("select ifnull(sum(matvalue),0) from loansecurity where lienacno is not null and (ExpiryDate is null OR ExpiryDate > '" + tillDate + "') and acno = '" + acno + "'").getResultList();
                                                    if (!list.isEmpty()) {
                                                        Vector vtr = (Vector) list.get(0);
                                                        lienDepositAmt = Double.parseDouble(vtr.get(0).toString());
                                                    }
                                                    /*END Add on Lien Deposit Amt< OutStandBalance*/
                                                    if (outStandBal.abs().doubleValue() > lienDepositAmt) {
                                                        pojo.setAcNo(acno);
                                                        pojo.setCustName(custname);
                                                        pojo.setOutstdBal(outStandBal);
                                                        pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(lastCrDt, (int) stdDays))));
                                                        pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
                                                        pojo.setSortAcStatus("1"); //This is used for sorting 
                                                        pojo.setCurrentStatusNoOfDays(dayDiff);
                                                        pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                                        pojo.setNoOfPendingEmi(dayDiff - stdDays);
                                                        pojo.setOverDueAmt(loanRemote.getOverDueAmount(acno, tillDate));
                                                        pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
                                                        pojo.setMarkFlag("M");      //This account will mark as NPA.
                                                        pojo.setEmiExist("Y");
                                                        pojo.setAcStatus(accStatus);
                                                        List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                                                + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                                                + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName from cbs_customer_master_detail"
                                                                + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                                        if (!custDetail.isEmpty()) {
                                                            Vector vect = (Vector) custDetail.get(0);
                                                            contact = vect.get(0).toString();
                                                            perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                                            currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                                            fatherName = vect.get(13).toString();
                                                        }
                                                        List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from ca_recon where acno='" + acno + "' and dt='" + lastCrDt + "'").getResultList();
                                                        if (!tempList.isEmpty()) {
                                                            Vector ele = (Vector) tempList.get(0);
                                                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                                                lastCrAmt = new BigDecimal(ele.get(0).toString());
                                                            }
                                                        }
                                                        pojo.setAddress(perAdd);
                                                        pojo.setCurrAddress(currAdd);
                                                        pojo.setContactNo(contact);
                                                        pojo.setLastCrAmt(lastCrAmt);
                                                        pojo.setSancAmt(sancAmt);
                                                        pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                                        pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                                        pojo.setDisburseDt(disbDt);
                                                        pojo.setFatherName(fatherName);
                                                        dataList.add(pojo);
                                                    }
                                                }
                                            }
                                        }
                                    } /**
                                     * If outstanding balance is greater than
                                     * limit amount continuously for more than
                                     * 90 days. while Npa account is same.
                                     */
                                    else if (outStandBal.abs().compareTo(chkLimit) == 1) {
                                        long dayDiff = 0;
                                        if (accStatus.equals("11") || accStatus.equals("12") || accStatus.equals("13")) {
                                            pojo = loanRemote.npaAccountAddition(accStatus, tillDate, acno, custname, outStandBal, acctNature);
                                            dataList.add(pojo);
                                        } else {
                                            //Only For Standard Account
                                            boolean addAccount = false;
                                            String fromDt = CbsUtil.dateAdd(tillDate, -(int) stdDays);   //For more than 90 days for standard.
                                            String toDt = CbsUtil.dateAdd(tillDate, -1);                 //Because I have already checked the tillDate.
                                            List<DateSlabPojo> list = loanRemote.getLimitOnRangeOfDate(fromDt, toDt, acno);

                                            if (list.size() > 0) {
                                                String tranDt = "";
                                                //                                    System.out.println(">>>>acno:" + acno + "; fromDt:" + fromDt + "; toDt:" + toDt + "; openingDt:" + openingDt);
                                                for (int k = 0; k < list.size(); k++) {
                                                    addAccount = false;
                                                    DateSlabPojo obj = list.get(k);
                                                    String tmpFrDt = obj.getFromDt();
                                                    String tmpToDt = obj.getToDt();
                                                    BigDecimal ccodLimit = obj.getLimit();

                                                    limitList = em.createNativeQuery("select distinct(date_format(dt,'%Y%m%d')) as trandt from "
                                                            + common.getTableName(common.getAcNatureByAcType(acno.substring(2, 4))) + " where acno='" + acno + "' and (cramt<>0 or dramt<>0) and "
                                                            + "dt between '" + tmpFrDt + "' and '" + tmpToDt + "' order by trandt").getResultList();
                                                    for (int h = 0; h < limitList.size(); h++) {
                                                        limitVec = (Vector) limitList.get(h);
                                                        tranDt = limitVec.get(0).toString();

                                                        BigDecimal tempOutstanding = new BigDecimal(formatter.format(Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, tranDt)))).abs();

                                                        if (tempOutstanding.compareTo(ccodLimit) == -1) {
                                                            addAccount = false;
                                                            break;  // From here it should go to the accountList For LOOP. For next account number of the same type.
                                                        } else {
                                                            addAccount = true;
                                                        }
                                                    }
                                                    //                                addAccount = true;
                                                }
                                                if (addAccount == true) {
                                                    /*Add on Lien Deposit Amt< OutStandBalance*/
                                                    double lienDepositAmt = 0;
                                                    List list1 = em.createNativeQuery("select ifnull(sum(matvalue),0) from loansecurity where lienacno is not null and (ExpiryDate is null OR ExpiryDate > '" + tillDate + "') and acno = '" + acno + "'").getResultList();
                                                    if (!list1.isEmpty()) {
                                                        Vector vtr = (Vector) list1.get(0);
                                                        lienDepositAmt = Double.parseDouble(vtr.get(0).toString());
                                                    }
                                                    /*END Add on Lien Deposit Amt< OutStandBalance*/
                                                    if (outStandBal.abs().doubleValue() > lienDepositAmt) {
                                                        pojo.setAcNo(acno);
                                                        pojo.setCustName(custname);
                                                        pojo.setOutstdBal(outStandBal);
                                                        pojo.setDueDt(dmyFormat.format(ymdFormat.parse(fromDt)));   // Date from where outstanding is greater than DP/OD for CC/OD limit.
                                                        pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
                                                        pojo.setSortAcStatus("1"); //This is used for sorting 
                                                        pojo.setCurrentStatusNoOfDays(CbsUtil.dayDiff(ymdFormat.parse(tranDt), ymdFormat.parse(tillDate)));
                                                        pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(tranDt)));
                                                        pojo.setNoOfPendingEmi(CbsUtil.dayDiff(ymdFormat.parse(fromDt), ymdFormat.parse(tillDate)));
                                                        pojo.setOverDueAmt(loanRemote.getOverDueAmount(acno, tillDate));
                                                        pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
                                                        pojo.setMarkFlag("M");          //This account will mark as NPA.
                                                        pojo.setEmiExist("Y");
                                                        pojo.setAcStatus(accStatus);
                                                        List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                                                + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                                                + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName from cbs_customer_master_detail"
                                                                + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                                        if (!custDetail.isEmpty()) {
                                                            Vector vect = (Vector) custDetail.get(0);
                                                            contact = vect.get(0).toString();
                                                            perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                                            currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                                            fatherName = vect.get(13).toString();
                                                        }
                                                        List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from ca_recon where acno='" + acno + "' and dt='" + tranDt + "'").getResultList();
                                                        if (!tempList.isEmpty()) {
                                                            Vector ele = (Vector) tempList.get(0);
                                                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                                                lastCrAmt = new BigDecimal(ele.get(0).toString());
                                                            }
                                                        }
                                                        pojo.setAddress(perAdd);
                                                        pojo.setCurrAddress(currAdd);
                                                        pojo.setContactNo(contact);
                                                        pojo.setLastCrAmt(lastCrAmt);
                                                        pojo.setSancAmt(sancAmt);
                                                        pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                                        pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                                        pojo.setDisburseDt(disbDt);
                                                        pojo.setFatherName(fatherName);
                                                        dataList.add(pojo);
                                                    }
                                                }
                                            } else {
                                                limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                                        + common.getTableName(common.getAcNatureByAcType(acno.substring(2, 4))) + " where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
                                                limitVec = (Vector) limitList.get(0);
                                                String lastCrDt = limitVec.get(0).toString();
                                                if (!(ymdFormat.parse(openingDt).after(ymdFormat.parse(lastCrDt)))) {
                                                    List havePositiveBal = em.createNativeQuery("select ifnull(max(aa.dt),'" + lastCrDt + "')  from "
                                                            + " (select a.acno, date_format( a.dt,'%Y%m%d') as dt,(select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from ca_recon where auth= 'y' and acno = '" + acno + "' and dt <=a.dt ) as bal "
                                                            + " from ca_recon a where a.auth= 'y' and a.acno = '" + acno + "' and "
                                                            + " a.dt between (select max(aa.dt) from "
                                                            + " (select a.acno, date_format( a.dt,'%Y%m%d') as dt,(select sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from ca_recon where auth= 'y' and acno = '" + acno + "' and dt <=a.dt ) as bal "
                                                            + " from ca_recon a where a.auth= 'y' and a.acno = '" + acno + "' and a.dt between '" + lastCrDt + "' and '" + tillDate + "' group by dt)aa where aa.bal>=0) "
                                                            + " and '" + tillDate + "' group by dt)aa where aa.bal<0 ").getResultList();
                                                    if (!havePositiveBal.isEmpty()) {
                                                        Vector posVect = (Vector) havePositiveBal.get(0);
                                                        lastCrDt = posVect.get(0).toString();
                                                    }
                                                    dayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt), ymdFormat.parse(tillDate));
                                                    //                                        System.out.println("++++acno:" + acno + "; dateDiff:" + dayDiff + "; lastCrDt:" + lastCrDt + "; openingDt:" + openingDt);
                                                    if (dayDiff > stdDays) {
                                                        // Add For Substandard
                                                         /*Add on Lien Deposit Amt< OutStandBalance*/
                                                        double lienDepositAmt = 0;
                                                        List list1 = em.createNativeQuery("select ifnull(sum(matvalue),0) from loansecurity where lienacno is not null and (ExpiryDate is null OR ExpiryDate > '" + tillDate + "') and acno = '" + acno + "'").getResultList();
                                                        if (!list1.isEmpty()) {
                                                            Vector vtr = (Vector) list1.get(0);
                                                            lienDepositAmt = Double.parseDouble(vtr.get(0).toString());
                                                        }
                                                        /*Add on Lien Deposit Amt< OutStandBalance*/
                                                        if (outStandBal.abs().doubleValue() > lienDepositAmt) {
                                                            pojo.setAcNo(acno);
                                                            pojo.setCustName(custname);
                                                            pojo.setOutstdBal(outStandBal);
                                                            pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(lastCrDt, (int) stdDays))));
                                                            pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
                                                            pojo.setSortAcStatus("1"); //This is used for sorting 
                                                            pojo.setCurrentStatusNoOfDays(CbsUtil.dayDiff(ymdFormat.parse(lastCrDt), ymdFormat.parse(tillDate)));
                                                            pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                                            pojo.setNoOfPendingEmi(dayDiff - stdDays);
                                                            pojo.setOverDueAmt(loanRemote.getOverDueAmount(acno, tillDate));
                                                            pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
                                                            pojo.setMarkFlag("M");      //This account will mark as NPA.
                                                            pojo.setEmiExist("Y");
                                                            pojo.setAcStatus(accStatus);
                                                            List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                                                    + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                                                    + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName from cbs_customer_master_detail"
                                                                    + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                                            if (!custDetail.isEmpty()) {
                                                                Vector vect = (Vector) custDetail.get(0);
                                                                contact = vect.get(0).toString();
                                                                perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                                                currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                                                fatherName = vect.get(13).toString();
                                                            }
                                                            List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from ca_recon where acno='" + acno + "' and dt='" + lastCrDt + "'").getResultList();
                                                            if (!tempList.isEmpty()) {
                                                                Vector ele = (Vector) tempList.get(0);
                                                                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                                                    lastCrAmt = new BigDecimal(ele.get(0).toString());
                                                                }
                                                            }
                                                            pojo.setAddress(perAdd);
                                                            pojo.setCurrAddress(currAdd);
                                                            pojo.setContactNo(contact);
                                                            pojo.setLastCrAmt(lastCrAmt);
                                                            pojo.setSancAmt(sancAmt);
                                                            pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                                            pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                                            pojo.setDisburseDt(disbDt);
                                                            pojo.setFatherName(fatherName);
                                                            dataList.add(pojo);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    if (brCode.equalsIgnoreCase("0A")) {
//                   accountList = em.createNativeQuery("select a.acno,a.custname,a.accstatus from accountmaster a,emidetails e "
//                            + "where a.acno=e.acno and AND (a.CLOSINGDATE='' OR ifnull(a.CLOSINGDATE,'')='' OR a.CLOSINGDATE>'" + tillDate + "') and a.accttype = '" + acctType + "' and a.openingdt <= '" + tillDate + "' "
//                            + "and e.status = 'unpaid' and e.duedt < '" + tillDate + "' group by a.acno,a.custname,a.accstatus").getResultList();
                        accountList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT ACNO FROM accountmaster WHERE OPENINGDT<='" + tillDate + "' AND (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + tillDate + "') and acno not in (" + query + ") AND SUBSTRING(ACNO,3,2)='" + acctType + "') ORDER BY ACNO").getResultList();
                    } else {
//                    accountList = em.createNativeQuery("select a.acno,a.custname,a.accstatus from accountmaster a,emidetails e "
//                            + "where a.acno=e.acno  AND (a.CLOSINGDATE='' OR ifnull(a.CLOSINGDATE,'')='' OR a.CLOSINGDATE>'" + tillDate + "') and a.accttype = '" + acctType + "' and a.openingdt <= '" + tillDate + "' and "
//                            + "e.status = 'unpaid' and e.duedt < '" + tillDate + "' and a.curbrcode='" + brCode + "' group by a.acno,a.custname,"
//                            + "a.accstatus").getResultList();
                        accountList = em.createNativeQuery("SELECT ACNO,CUSTNAME FROM loan_appparameter WHERE ACNO IN(SELECT ACNO FROM accountmaster WHERE OPENINGDT<='" + tillDate + "' AND (CLOSINGDATE='' OR ifnull(CLOSINGDATE,'')='' OR CLOSINGDATE>'" + tillDate + "')  and acno not in (" + query + ") AND CURBRCODE='" + brCode + "' AND SUBSTRING(ACNO,3,2)='" + acctType + "') ORDER BY ACNO").getResultList();
                    }
                    if (!accountList.isEmpty()) {
//                        throw new ApplicationException("There is no data to mark NPA.");
//                    }
                        List limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) from "
                                + "cbs_scheme_delinquency_details where delinq_cycle in (select ref_code from "
                                + "cbs_ref_rec_type where ref_rec_no='207' and ref_code in('STD'))").getResultList();
                        Vector limitVec = (Vector) limitList.get(0);
//                String cycle = limitVec.get(0).toString();
                        long stdDays = Long.parseLong(limitVec.get(1).toString());

                        for (int i = 0; i < accountList.size(); i++) {
                            NpaAccountDetailPojo pojo = new NpaAccountDetailPojo();
                            Vector element = (Vector) accountList.get(i);
                            String acno = element.get(0).toString();
                            String custname = element.get(1).toString();
                            BigDecimal ovrdueAmt = new BigDecimal("0");
                            int unPaidCount = 0;
                            String accStatus = null;
                            String presentStatus = interBranchFacade.fnGetLoanStatusTillDate(acno, tillDate);
                            String perAdd = "", currAdd = "", contact = "", sancDate = "", disbDt = null, fatherName = "";
                            double amtDis = 0d;
                            BigDecimal lastCrAmt = new BigDecimal("0"), sancAmt = new BigDecimal("0");
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
                            List sanctionDtList = em.createNativeQuery("select ifnull(Sanctionlimit,0),ifnull(Sanctionlimitdt,'') from loan_appparameter where acno =  '" + acno + "' ").getResultList();
                            if (!sanctionDtList.isEmpty()) {
                                Vector vist = (Vector) sanctionDtList.get(0);
                                sancAmt = new BigDecimal(vist.get(0).toString());
                                sancDate = vist.get(1).toString();
                            }
                            List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, acLimit from loan_oldinterest "
                                    + "where acno =  '" + acno + "' and txnid = "
                                    + "(select min(TXNID) from loan_oldinterest where acno =  '" + acno + "' and enterdate>'" + tillDate + "' )").getResultList();
                            if (!sanctionLimitDtList.isEmpty()) {
                                Vector vist = (Vector) sanctionLimitDtList.get(0);
                                sancAmt = new BigDecimal(vist.get(1).toString());
                                sancDate = vist.get(0).toString();
                            } else {
                                sanctionLimitDtList = em.createNativeQuery("select ifnull(odlimit,0) from loan_appparameter  where acno = '" + acno + "' ").getResultList();
                                if (!sanctionLimitDtList.isEmpty()) {
                                    Vector vist = (Vector) sanctionLimitDtList.get(0);
                                    sancAmt = new BigDecimal(vist.get(0).toString());
                                }
                            }
                            String table_name = "loan_recon";
//                            if(acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)){
//                                table_name = "ca_recon";
//                            } else {
//                                table_name ="loan_recon";
//                            }
                            List result5 = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0), date_format(ifnull(min(dt),'19000101'),'%d/%m/%Y')  from " + table_name + "   where dt <= '" + tillDate + "'   and trandesc in (6,0)  and acno='" + acno + "'").getResultList();
                            if (!result5.isEmpty()) {
                                Vector vtrsub = (Vector) result5.get(0);
                                amtDis = Double.parseDouble(vtrsub.get(0).toString());
                                disbDt = vtrsub.get(1).toString();
                            }
                            //accStatus = element.get(2).toString();
                            if (accStatus.equals("1")) {
                                BigDecimal outStandBal = new BigDecimal(formatter.format(loanRemote.fnBalosForAccountAsOn(acno, tillDate)));
//                    if (accStatus.equals("11") || accStatus.equals("12") || accStatus.equals("13")) {
//                        //For NPA Account.
//                        pojo = npaAccountAddition(accStatus, tillDate, acno, custname, outStandBal);
//                        dataList.add(pojo);
//                    } else {
                                //For Standard Account.
                                if (outStandBal.compareTo(new BigDecimal("0")) < 0) {
                                    List list = em.createNativeQuery("select date_format(min(duedt),'%Y%m%d') min_unpaid_dt,count(status) as unpaid_count,installamt "
                                            + "from emidetails where acno = '" + acno + "' and status = 'unpaid' and "
                                            + "duedt < '" + tillDate + "' group by installamt").getResultList();
                                    if (list.isEmpty()) {
                                        /*
                                         * ************If-Any-EMI-doesn't-Exist-with-UNPAID**************
                                         */
                                        List checklist = em.createNativeQuery("select * from emidetails where acno = '" + acno + "' ").getResultList();
                                        if (checklist.isEmpty()) {
                                            /*
                                             * ************If-EMI-Doesn't-Exist**************
                                             */
//                                    throw new ApplicationException("There is no data in emidetails for :" + acno);
                                            List<OverdueEmiReportPojo> overdueEmiList = getOverdueEmiList("A/Cs Whose Plan Has Finished", acno, 1, 200, tillDate, common.getBrncodeByAcno(acno), "", 0, isExceessEmi, null, 0);
                                            for (OverdueEmiReportPojo pojo1 : overdueEmiList) {
                                                ovrdueAmt = pojo1.getAmountOverdue();
                                                unPaidCount = pojo1.getNoOfEmiOverdue();
                                            }

                                            limitList = em.createNativeQuery("select  ifnull(min(date_format(dt,'%Y%m%d')),'19000101') from " + common.getTableName(common.getAcNatureByAcType(acno.substring(2, 4))) + " where acno = '" + acno + "' and  ty= 1 and trantype = '8' and trandesc in (3,4) and "
                                                    + " dt>=(select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
                                                    + common.getTableName(common.getAcNatureByAcType(acno.substring(2, 4))) + " where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "')").getResultList();
                                            if (!limitList.isEmpty()) {
                                                /*
                                                 * ************If-Any Interest posted after CR amount date-exist**************
                                                 */
                                                limitVec = (Vector) limitList.get(0);
                                                String lastCrDt = limitVec.get(0).toString();
                                                if (!lastCrDt.equalsIgnoreCase("19000101")) {
                                                    /*
                                                     * ************If-Any-CR-amount-date-Exist**************
                                                     */
                                                    long dayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt), ymdFormat.parse(tillDate));
//                                            System.out.println("<<<<++++acno:" + acno + "; dateDiff:" + dayDiff + "; lastCrDt:" + lastCrDt + "; accstatus:" + accStatus);
                                                    if (overDueMap.containsKey(acno) || flag.equalsIgnoreCase("True")) {
                                                        if (dayDiff > stdDays) {
                                                            // Add For Substandard
                                                            pojo.setAcNo(acno);
                                                            pojo.setCustName(custname);
                                                            pojo.setOutstdBal(outStandBal);
                                                            pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(lastCrDt, (int) stdDays))));
                                                            pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
                                                            pojo.setSortAcStatus("1"); //This is used for sorting 
//                                                pojo.setCurrentStatusNoOfDays(dayDiff);
                                                            pojo.setCurrentStatusNoOfDays(dayDiff - stdDays);
                                                            pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                                            pojo.setNoOfPendingEmi(unPaidCount);
                                                            pojo.setOverDueAmt(ovrdueAmt.compareTo(new BigDecimal("0")) != 0 ? ovrdueAmt : loanRemote.getOverDueAmount(acno, tillDate));
                                                            pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
                                                            pojo.setMarkFlag("M");      //This account will mark as NPA.
                                                            pojo.setEmiExist("N");
                                                            pojo.setAcStatus(accStatus);
                                                            List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                                                    + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                                                    + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName from cbs_customer_master_detail"
                                                                    + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                                            if (!custDetail.isEmpty()) {
                                                                Vector vect = (Vector) custDetail.get(0);
                                                                contact = vect.get(0).toString();
                                                                perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                                                currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                                                fatherName = vect.get(13).toString();
                                                            }
                                                            List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table_name + " where acno='" + acno + "' and dt='" + lastCrDt + "'").getResultList();
                                                            if (!tempList.isEmpty()) {
                                                                Vector ele = (Vector) tempList.get(0);
                                                                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                                                    lastCrAmt = new BigDecimal(ele.get(0).toString());
                                                                }
                                                            }
                                                            pojo.setAddress(perAdd);
                                                            pojo.setCurrAddress(currAdd);
                                                            pojo.setContactNo(contact);
                                                            pojo.setLastCrAmt(lastCrAmt);
                                                            pojo.setSancAmt(sancAmt);
                                                            pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                                            pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                                            pojo.setDisburseDt(disbDt);
                                                            pojo.setFatherName(fatherName);
                                                            dataList.add(pojo);
                                                        }
                                                    }
                                                } else {
                                                    /*
                                                     * ************If-Any-CR-amount-doesn't-Exist***************
                                                     */
                                                    limitList = em.createNativeQuery("select openingdt from  accountmaster where acno = '" + acno + "' ").getResultList();
                                                    limitVec = (Vector) limitList.get(0);
                                                    String openingDt = limitVec.get(0).toString();
                                                    long dayDiff = CbsUtil.dayDiff(ymdFormat.parse(CbsUtil.monthAdd(openingDt, (int) 1)), ymdFormat.parse(tillDate));
                                                    //                                        System.out.println("aaaaaaaaaaaacno:" + acno + "; dateDiff:" + dayDiff + "; openingDt:" + openingDt + "; accstatus:" + accStatus);
                                                    if (overDueMap.containsKey(acno) || flag.equalsIgnoreCase("True")) {
                                                        if (dayDiff > stdDays) {
                                                            // Add For Substandard
                                                            pojo.setAcNo(acno);
                                                            pojo.setCustName(custname);
                                                            pojo.setOutstdBal(outStandBal);
                                                            pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(CbsUtil.monthAdd(openingDt, (int) 1), (int) stdDays))));
                                                            pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
                                                            pojo.setSortAcStatus("1"); //This is used for sorting 
//                                                pojo.setCurrentStatusNoOfDays(dayDiff);
                                                            pojo.setCurrentStatusNoOfDays(dayDiff - stdDays);
                                                            pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(openingDt)));
                                                            pojo.setNoOfPendingEmi(unPaidCount);
                                                            pojo.setOverDueAmt(ovrdueAmt.compareTo(new BigDecimal("0")) != 0 ? ovrdueAmt : loanRemote.getOverDueAmount(acno, tillDate));
                                                            pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
                                                            pojo.setMarkFlag("M");      //This account will mark as NPA.
                                                            pojo.setEmiExist("N");    //EMI doesn't Exist
                                                            pojo.setAcStatus(accStatus);
                                                            List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                                                    + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                                                    + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName from cbs_customer_master_detail"
                                                                    + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                                            if (!custDetail.isEmpty()) {
                                                                Vector vect = (Vector) custDetail.get(0);
                                                                contact = vect.get(0).toString();
                                                                perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                                                currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                                                fatherName = vect.get(13).toString();
                                                            }
                                                            List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table_name + " where acno='" + acno + "' and dt='" + lastCrDt + "'").getResultList();
                                                            if (!tempList.isEmpty()) {
                                                                Vector ele = (Vector) tempList.get(0);
                                                                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                                                    lastCrAmt = new BigDecimal(ele.get(0).toString());
                                                                }
                                                            }
                                                            pojo.setAddress(perAdd);
                                                            pojo.setCurrAddress(currAdd);
                                                            pojo.setContactNo(contact);
                                                            pojo.setLastCrAmt(lastCrAmt);
                                                            pojo.setSancAmt(sancAmt);
                                                            pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                                            pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                                            pojo.setDisburseDt(disbDt);
                                                            pojo.setFatherName(fatherName);
                                                            dataList.add(pojo);
                                                        }
                                                    }
                                                }
                                            } else {
                                                /*
                                                 * ************If-Any-CR-amount-doesn't-exist
                                                 * ***************then-Handling-with-OPENING-DATE**************
                                                 */
                                                limitList = em.createNativeQuery("select openingdt from  accountmaster where acno = '" + acno + "' ").getResultList();
                                                limitVec = (Vector) limitList.get(0);
                                                String openingDt = limitVec.get(0).toString();
                                                long dayDiff = CbsUtil.dayDiff(ymdFormat.parse(CbsUtil.monthAdd(openingDt, (int) 1)), ymdFormat.parse(tillDate));
//                                        System.out.println("aaaaaaaaaaaacno:" + acno + "; dateDiff:" + dayDiff + "; openingDt:" + openingDt + "; accstatus:" + accStatus);
                                                if (overDueMap.containsKey(acno) || flag.equalsIgnoreCase("True")) {
                                                    if (dayDiff > stdDays) {
                                                        // Add For Substandard
                                                        pojo.setAcNo(acno);
                                                        pojo.setCustName(custname);
                                                        pojo.setOutstdBal(outStandBal);
                                                        pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(CbsUtil.monthAdd(openingDt, (int) 1), (int) stdDays))));
                                                        pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
                                                        pojo.setSortAcStatus("1"); //This is used for sorting 
//                                            pojo.setCurrentStatusNoOfDays(dayDiff);
                                                        pojo.setCurrentStatusNoOfDays(dayDiff - stdDays);
                                                        pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(openingDt)));
                                                        pojo.setNoOfPendingEmi(unPaidCount);
                                                        pojo.setOverDueAmt(ovrdueAmt.compareTo(new BigDecimal("0")) != 0 ? ovrdueAmt : loanRemote.getOverDueAmount(acno, tillDate));
                                                        pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
                                                        pojo.setMarkFlag("M");      //This account will mark as NPA.
                                                        pojo.setEmiExist("N");    //EMI doesn't Exist
                                                        pojo.setAcStatus(accStatus);
                                                        List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                                                + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                                                + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName from cbs_customer_master_detail"
                                                                + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                                        if (!custDetail.isEmpty()) {
                                                            Vector vect = (Vector) custDetail.get(0);
                                                            contact = vect.get(0).toString();
                                                            perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                                            currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                                            fatherName = vect.get(13).toString();
                                                        }
                                                        List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table_name + " where acno='" + acno + "' and dt='" + openingDt + "'").getResultList();
                                                        if (!tempList.isEmpty()) {
                                                            Vector ele = (Vector) tempList.get(0);
                                                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                                                lastCrAmt = new BigDecimal(ele.get(0).toString());
                                                            }
                                                        }
                                                        pojo.setAddress(perAdd);
                                                        pojo.setCurrAddress(currAdd);
                                                        pojo.setContactNo(contact);
                                                        pojo.setLastCrAmt(lastCrAmt);
                                                        pojo.setSancAmt(sancAmt);
                                                        pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                                        pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                                        pojo.setDisburseDt(disbDt);
                                                        pojo.setFatherName(fatherName);
                                                        dataList.add(pojo);
                                                    }
                                                }
                                            }
                                        } else {
                                            /*
                                             *THIS CODE IS OPEN as per Basti Software Bug #14575
                                             * ************If-EMI-exist**************
                                             * All EMI paid, But outstanding is contineously remain in debit
                                             Then Last Due date of EMI will be considered as a Last Credit Date */
                                            List checklistEmi = em.createNativeQuery("select * from emidetails where acno = '" + acno + "' and status = 'unpaid'  ").getResultList();
                                            if (checklistEmi.isEmpty()) {
                                                List<OverdueEmiReportPojo> OverDueTLList = new ArrayList<>();
                                                OverDueTLList = OverdueTlList;
                                                if (!OverDueTLList.isEmpty()) {
                                                    for (int k = 0; k < OverDueTLList.size(); k++) {
                                                        OverdueEmiReportPojo vect = OverDueTLList.get(k);
                                                        if (vect.getAccountNumber().equalsIgnoreCase(acno)) {
                                                            ovrdueAmt = vect.getAmountOverdue();
                                                            unPaidCount = vect.getNoOfEmiOverdue();
                                                            String lastCrDt = ymdFormat.format(dmyFormat.parse(vect.getLastRecDt()));

                                                            long dayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt), ymdFormat.parse(tillDate));
//                                            System.out.println("<<<<++++acno:" + acno + "; dateDiff:" + dayDiff + "; lastCrDt:" + lastCrDt + "; accstatus:" + accStatus);
                                                            if (overDueMap.containsKey(acno) || flag.equalsIgnoreCase("True")) {
                                                                if (dayDiff > stdDays) {
                                                                    // Add For Substandard
                                                                    pojo.setAcNo(acno);
                                                                    pojo.setCustName(custname);
                                                                    pojo.setOutstdBal(outStandBal);
                                                                    pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(lastCrDt, (int) stdDays))));
                                                                    pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
                                                                    pojo.setSortAcStatus("1"); //This is used for sorting 
                                                                    pojo.setCurrentStatusNoOfDays(dayDiff - stdDays);
                                                                    pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
                                                                    pojo.setNoOfPendingEmi(unPaidCount);
                                                                    pojo.setOverDueAmt(ovrdueAmt.compareTo(new BigDecimal("0")) != 0 ? ovrdueAmt : loanRemote.getOverDueAmount(acno, tillDate));
                                                                    pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
                                                                    pojo.setMarkFlag("M");      //This account will mark as NPA.
                                                                    pojo.setEmiExist("Y");
                                                                    pojo.setAcStatus(accStatus);
                                                                    List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                                                            + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                                                            + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName from cbs_customer_master_detail"
                                                                            + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                                                    if (!custDetail.isEmpty()) {
                                                                        Vector vect1 = (Vector) custDetail.get(0);
                                                                        contact = vect1.get(0).toString();
                                                                        perAdd = vect1.get(1).toString().concat(vect1.get(2).toString()).concat(vect1.get(3).toString()).concat(vect1.get(4).toString()).concat(vect1.get(5).toString()).concat(vect1.get(6).toString());
                                                                        currAdd = vect1.get(7).toString().concat(vect1.get(8).toString()).concat(vect1.get(9).toString()).concat(vect1.get(10).toString()).concat(vect1.get(11).toString()).concat(vect1.get(12).toString());
                                                                        fatherName = vect1.get(13).toString();
                                                                    }
                                                                    List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table_name + " where acno='" + acno + "' and dt='" + lastCrDt + "'").getResultList();
                                                                    if (!tempList.isEmpty()) {
                                                                        Vector ele = (Vector) tempList.get(0);
                                                                        if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                                                            lastCrAmt = new BigDecimal(ele.get(0).toString());
                                                                        }
                                                                    }
                                                                    pojo.setAddress(perAdd);
                                                                    pojo.setCurrAddress(currAdd);
                                                                    pojo.setContactNo(contact);
                                                                    pojo.setLastCrAmt(lastCrAmt);
                                                                    pojo.setSancAmt(sancAmt);
                                                                    pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                                                    pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                                                    pojo.setDisburseDt(disbDt);
                                                                    pojo.setFatherName(fatherName);
                                                                    dataList.add(pojo);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
//                                            List<OverdueEmiReportPojo> overdueEmiList = getOverdueEmiList("", acno, 1, 200, tillDate, 
//                                                    common.getBrncodeByAcno(acno), "", 0, isExceessEmi, null, 0);
//                                            for (OverdueEmiReportPojo pojo1 : overdueEmiList) {
//                                                ovrdueAmt = pojo1.getAmountOverdue();
//                                                unPaidCount = pojo1.getNoOfEmiOverdue();
//                                            }
//                                            limitList = em.createNativeQuery("select  ifnull(min(date_format(dt,'%Y%m%d')),'19000101') from " + common.getTableName(common.getAcNatureByAcType(acno.substring(2, 4))) + " where acno = '" + acno + "' and  ty= 1 and trantype = '8' and trandesc in (3,4) and "
//                                                    + " dt>=(select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
//                                                    + "loan_recon where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "')").getResultList();
////                                            limitList = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from "
////                                                    + "loan_recon where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
//                                            if (!limitList.isEmpty()) {
//                                                /*
//                                                 * ************If Any CR amount exist**************
//                                                 */
//                                                limitVec = (Vector) limitList.get(0);
//                                                String lastCrDt = limitVec.get(0).toString();
//                                                if (!lastCrDt.equalsIgnoreCase("19000101")) {
//                                                    double outStandBalOnLastCrDt = loanRemote.fnBalosForAccountAsOn(acno, lastCrDt);
//                                                    if (outStandBalOnLastCrDt >= 0) {
//                                                        /*
//                                                         * Checking added for POSTALUP
//                                                         * Checking the ouustanding is 0 or hace credit balance
//                                                         * Then Check the last debit dt will be treated as Starting Dt of loan
//                                                         */
//                                                        limitList = em.createNativeQuery("select ifnull(min(date_format(dt,'%Y%m%d')),'19000101') from "
//                                                                + "loan_recon where acno = '" + acno + "' and dramt > 0 and dt >= '" + lastCrDt + "'  and trandesc = 6").getResultList();
//                                                        if (!limitList.isEmpty()) {
//                                                            limitVec = (Vector) limitList.get(0);
//                                                            lastCrDt = limitVec.get(0).toString();
//                                                            if (!lastCrDt.equalsIgnoreCase("19000101")) {
//                                                                long dayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt), ymdFormat.parse(tillDate));
//                                                                //                                            System.out.println("<<<<++++acno:" + acno + "; dateDiff:" + dayDiff + "; lastCrDt:" + lastCrDt + "; accstatus:" + accStatus);
//                                                                if (dayDiff > stdDays) {
//                                                                    // Add For Substandard
//                                                                    pojo.setAcNo(acno);
//                                                                    pojo.setCustName(custname);
//                                                                    pojo.setOutstdBal(outStandBal);
//                                                                    pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(lastCrDt, (int) stdDays))));
//                                                                    pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
//                                                                    pojo.setSortAcStatus("1"); //This is used for sorting 
//                                                                    pojo.setCurrentStatusNoOfDays(dayDiff - stdDays);
//                                                                    pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
//                                                                    pojo.setNoOfPendingEmi(unPaidCount);
//                                                                    pojo.setOverDueAmt(ovrdueAmt.compareTo(new BigDecimal("0")) != 0 ? ovrdueAmt : loanRemote.getOverDueAmount(acno, tillDate));
//                                                                    pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
//                                                                    pojo.setMarkFlag("M");      //This account will mark as NPA.
//                                                                    pojo.setEmiExist("Y");
//                                                                    pojo.setAcStatus(accStatus);
//                                                                    List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
//                                                                            + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,'')))," 
//                                                                            + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName from cbs_customer_master_detail"
//                                                                            + "  where customerid = (select CustId from customerid where acno ='"+acno+"');").getResultList();
//                                                                    if(!custDetail.isEmpty()){
//                                                                        Vector vect = (Vector) custDetail.get(0);
//                                                                        contact= vect.get(0).toString();
//                                                                        perAdd= vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
//                                                                        currAdd= vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
//                                                                        fatherName = vect.get(13).toString();
//                                                                    }
//                                                                    List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from "+table_name+" where acno='" + acno + "' and dt='" + lastCrDt + "'").getResultList();
//                                                                    if (!tempList.isEmpty()) {
//                                                                        Vector ele = (Vector) tempList.get(0);
//                                                                        if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                                            lastCrAmt = new BigDecimal(ele.get(0).toString());
//                                                                        }
//                                                                    }
//                                                                    pojo.setAddress(perAdd);
//                                                                    pojo.setCurrAddress(currAdd);
//                                                                    pojo.setContactNo(contact);
//                                                                    pojo.setLastCrAmt(lastCrAmt);
//                                                                    pojo.setSancAmt(sancAmt);
//                                                                    pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
//                                                                    pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
//                                                                    pojo.setDisburseDt(disbDt);
//                                                                    pojo.setFatherName(fatherName);
//                                                                    dataList.add(pojo);
//                                                                }
//                                                            }
//                                                        }
//                                                    } else {
//                                                        /*
//                                                         * ************ If Any CR amount date Exist **************
//                                                         */
//                                                        long dayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt), ymdFormat.parse(tillDate));
//                                                        //                                            System.out.println("<<<<++++acno:" + acno + "; dateDiff:" + dayDiff + "; lastCrDt:" + lastCrDt + "; accstatus:" + accStatus);
//                                                        if (dayDiff > stdDays) {
//                                                            // Add For Substandard
//                                                            pojo.setAcNo(acno);
//                                                            pojo.setCustName(custname);
//                                                            pojo.setOutstdBal(outStandBal);
//                                                            pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(lastCrDt, (int) stdDays))));
//                                                            pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
//                                                            pojo.setSortAcStatus("1"); //This is used for sorting 
//                                                            pojo.setCurrentStatusNoOfDays(dayDiff - stdDays);
//                                                            pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(lastCrDt)));
//                                                            pojo.setNoOfPendingEmi(unPaidCount);
//                                                            pojo.setOverDueAmt(ovrdueAmt.compareTo(new BigDecimal("0")) != 0 ? ovrdueAmt : loanRemote.getOverDueAmount(acno, tillDate));
//                                                            pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
//                                                            pojo.setMarkFlag("M");      //This account will mark as NPA.
//                                                            pojo.setEmiExist("Y");
//                                                            pojo.setAcStatus(accStatus);
//                                                            List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
//                                                                    + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,'')))," 
//                                                                    + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName from cbs_customer_master_detail"
//                                                                    + "  where customerid = (select CustId from customerid where acno ='"+acno+"');").getResultList();
//                                                            if(!custDetail.isEmpty()){
//                                                                Vector vect = (Vector) custDetail.get(0);
//                                                                contact= vect.get(0).toString();
//                                                                perAdd= vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
//                                                                currAdd= vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
//                                                                fatherName = vect.get(13).toString();
//                                                            }
//                                                            List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from "+table_name+" where acno='" + acno + "' and dt='" + lastCrDt + "'").getResultList();
//                                                            if (!tempList.isEmpty()) {
//                                                                Vector ele = (Vector) tempList.get(0);
//                                                                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                                    lastCrAmt = new BigDecimal(ele.get(0).toString());
//                                                                }
//                                                            }
//                                                            pojo.setAddress(perAdd);
//                                                            pojo.setCurrAddress(currAdd);
//                                                            pojo.setContactNo(contact);
//                                                            pojo.setLastCrAmt(lastCrAmt);
//                                                            pojo.setSancAmt(sancAmt);
//                                                            pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
//                                                            pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
//                                                            pojo.setDisburseDt(disbDt);
//                                                            pojo.setFatherName(fatherName);
//                                                            dataList.add(pojo);
//                                                        }
//                                                    }
//                                                } else {
//                                                    /*
//                                                     * ************ If Any CR amount doesn't Exist ***************
//                                                     */
//                                                    limitList = em.createNativeQuery("select openingdt from  accountmaster where acno = '" + acno + "' ").getResultList();
//                                                    limitVec = (Vector) limitList.get(0);
//                                                    String openingDt = limitVec.get(0).toString();
//                                                    long dayDiff = CbsUtil.dayDiff(ymdFormat.parse(CbsUtil.monthAdd(openingDt, (int) 1)), ymdFormat.parse(tillDate));
//                                                    //                                        System.out.println("aaaaaaaaaaaacno:" + acno + "; dateDiff:" + dayDiff + "; openingDt:" + openingDt + "; accstatus:" + accStatus);
//                                                    if (dayDiff > stdDays) {
//                                                        // Add For Substandard
//                                                        pojo.setAcNo(acno);
//                                                        pojo.setCustName(custname);
//                                                        pojo.setOutstdBal(outStandBal);
//                                                        pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(CbsUtil.monthAdd(openingDt, (int) 1), (int) stdDays))));
//                                                        pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
//                                                        pojo.setSortAcStatus("1"); //This is used for sorting 
//                                                        pojo.setCurrentStatusNoOfDays(dayDiff - stdDays);
//                                                        pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(openingDt)));
//                                                        pojo.setNoOfPendingEmi(unPaidCount);
//                                                        pojo.setOverDueAmt(ovrdueAmt.compareTo(new BigDecimal("0")) != 0 ? ovrdueAmt : loanRemote.getOverDueAmount(acno, tillDate));
//                                                        pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
//                                                        pojo.setMarkFlag("M");      //This account will mark as NPA.
//                                                        pojo.setEmiExist("Y");
//                                                        pojo.setAcStatus(accStatus);
//                                                        List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
//                                                                + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,'')))," 
//                                                                + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName from cbs_customer_master_detail"
//                                                                + "  where customerid = (select CustId from customerid where acno ='"+acno+"');").getResultList();
//                                                        if(!custDetail.isEmpty()){
//                                                            Vector vect = (Vector) custDetail.get(0);
//                                                            contact= vect.get(0).toString();
//                                                            perAdd= vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
//                                                            currAdd= vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
//                                                            fatherName = vect.get(13).toString();
//                                                        }
//                                                        List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from "+table_name+" where acno='" + acno + "' and dt='" + openingDt + "'").getResultList();
//                                                        if (!tempList.isEmpty()) {
//                                                            Vector ele = (Vector) tempList.get(0);
//                                                            if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
//                                                                lastCrAmt = new BigDecimal(ele.get(0).toString());
//                                                            }
//                                                        }
//                                                        pojo.setAddress(perAdd);
//                                                        pojo.setCurrAddress(currAdd);
//                                                        pojo.setContactNo(contact);
//                                                        pojo.setLastCrAmt(lastCrAmt);
//                                                        pojo.setSancAmt(sancAmt);
//                                                        pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
//                                                        pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
//                                                        pojo.setDisburseDt(disbDt);
//                                                        pojo.setFatherName(fatherName);
//                                                        dataList.add(pojo);
//                                                    }
//                                                }
//                                            }
                                        }

                                    } else {
                                        /**
                                         * ************If-Any-EMI-exist-with-UNPAID**************
                                         */
                                        Vector listVec = (Vector) list.get(0);
                                        String minUnPaidDt = listVec.get(0).toString();
                                        String minUnPaidDt2 = listVec.get(0).toString();
                                        String minPaidDt = minUnPaidDt;
                                        String pymtDt = null;
                                        double excessAmt = 0, postedInt = 0;
                                        List paidEmiList = em.createNativeQuery("select date_format(max(duedt),'%Y%m%d'), acno, date_format(max(PAYMENTDT),'%Y%m%d') "
                                                + "from emidetails where acno = '" + acno + "' and status = 'PAID' and "
                                                + "duedt <= '" + tillDate + "' group by acno").getResultList();
                                        if (!paidEmiList.isEmpty()) {
                                            Vector emiPaidDtVect = (Vector) paidEmiList.get(0);
                                            minPaidDt = emiPaidDtVect.get(0).toString();
                                            pymtDt = emiPaidDtVect.get(2).toString();
                                        }

                                        List excessAmtList = em.createNativeQuery("select ifnull(excessamt,0) from cbs_loan_emi_excess_details where "
                                                + "acno = '" + acno + "' and txnid= "
                                                + "(select max(txnid) from cbs_loan_emi_excess_details where acno = '" + acno + "' and dt= '" + pymtDt + "')").getResultList();
                                        if (!excessAmtList.isEmpty()) {
                                            Vector excessAmtVect = (Vector) excessAmtList.get(0);
                                            excessAmt = Double.parseDouble(excessAmtVect.get(0).toString());
                                        }
                                        paidEmiList = em.createNativeQuery("select ifnull(date_format(valuedt,'%Y%m%d'),'" + minUnPaidDt + "') as dt, ifnull(sum(ifnull(dramt,0)),0) "
                                                + " from loan_recon where acno = '" + acno + "' and trandesc in (3,4) and details not like '%INTT. REC FOR MEM%' "
                                                + " and dt between '" + minPaidDt + "' and '" + minUnPaidDt + "' group by valuedt").getResultList();
                                        if (!paidEmiList.isEmpty()) {
                                            for (int s = 0; s < paidEmiList.size(); s++) {
                                                Vector emiPaidDtVect = (Vector) paidEmiList.get(s);
                                                minUnPaidDt = emiPaidDtVect.get(0).toString();
                                                postedInt = Double.parseDouble(emiPaidDtVect.get(1).toString());
                                                if (excessAmt >= postedInt) {
                                                    minUnPaidDt = minUnPaidDt; //minUnPaidDt2;
                                                    excessAmt = excessAmt - postedInt;
                                                } else {
                                                    break;
                                                }
                                            }
                                        }
                                        String minUnPaidDt1 = CbsUtil.dateAdd(minUnPaidDt, 1);
                                        unPaidCount = Integer.parseInt(listVec.get(1).toString());
                                        Double installment = Double.parseDouble(listVec.get(2).toString());

                                        Long dayDiff = CbsUtil.dayDiff(ymdFormat.parse(minUnPaidDt1), ymdFormat.parse(tillDate));
//                                      System.out.println("====acno:" + acno + "; dateDiff:" + dayDiff + "; minUnPaidDt:" + minUnPaidDt + "; accstatus:" + accStatus);
                                        if (overDueMap.containsKey(acno) || flag.equalsIgnoreCase("True")) {
                                            if (bnkCode.equalsIgnoreCase("army")) {
                                                List lastCrDtList1 = em.createNativeQuery("select ifnull(max(date_format(dt,'%Y%m%d')),'19000101') from loan_recon where acno = '" + acno + "' and cramt > 0 and dt <= '" + tillDate + "'").getResultList();
                                                Vector vtr = (Vector) lastCrDtList1.get(0);
                                                String lastCrDt1 = vtr.get(0).toString();
                                                dayDiff = CbsUtil.dayDiff(ymdFormat.parse(lastCrDt1), ymdFormat.parse(tillDate));
                                            }
                                            if (dayDiff >= stdDays) {
                                                pojo.setAcNo(acno);
                                                pojo.setCustName(custname);
                                                pojo.setOutstdBal(outStandBal);
                                                pojo.setDueDt(dmyFormat.format(ymdFormat.parse(CbsUtil.dateAdd(minUnPaidDt1, (int) stdDays))));
                                                pojo.setCurrentStatus(loanRemote.getRefRecDesc("STD"));
                                                pojo.setSortAcStatus("1"); //This is used for sorting 
                                                pojo.setCurrentStatusNoOfDays(dayDiff - stdDays);
                                                pojo.setLastCrDate(dmyFormat.format(ymdFormat.parse(minUnPaidDt1)));

                                                List acNoList1 = em.createNativeQuery("select distinct (acno) from emidetails where status = 'unpaid' and acno ='" + acno + "'").getResultList();
                                                if (acNoList1.isEmpty()) {
                                                    List<OverdueEmiReportPojo> overdueEmiList = getOverdueEmiList("A/Cs Whose Plan Has Finished", acno, 1, 200, tillDate, common.getBrncodeByAcno(acno), "", 0, isExceessEmi, null, 0);
                                                    for (OverdueEmiReportPojo pojo1 : overdueEmiList) {
                                                        ovrdueAmt = pojo1.getAmountOverdue();
                                                        unPaidCount = pojo1.getNoOfEmiOverdue();
                                                    }
                                                } else {
                                                    List<OverdueEmiReportPojo> overdueEmiList = getOverdueEmiList("", acno, 1, 200, tillDate, common.getBrncodeByAcno(acno), "", 0, isExceessEmi, null, 0);
                                                    for (OverdueEmiReportPojo pojo1 : overdueEmiList) {
                                                        ovrdueAmt = pojo1.getAmountOverdue();
                                                        unPaidCount = pojo1.getNoOfEmiOverdue();
                                                    }
                                                }
                                                pojo.setOverDueAmt(ovrdueAmt); // by Alok
                                                pojo.setNoOfPendingEmi(unPaidCount);
//                                          pojo.setOverDueAmt(new BigDecimal(installment * unPaidCount)); // By Dinesh
                                                pojo.setStatus(loanRemote.getRefRecDesc("SUB"));
                                                pojo.setMarkFlag("M");      //This account will mark as NPA.
                                                pojo.setEmiExist("Y");      //EMI Exist
                                                pojo.setAcStatus(accStatus);
                                                List custDetail = em.createNativeQuery("select ifnull(mobilenumber,''),ifnull(PerAddressLine1,''),ifnull(PerAddressLine2,''),ifnull(PerVillage,''),ifnull(PerBlock,''),ifnull(PerStateCode,''),ifnull(PerPostalCode,'')"
                                                        + " ,ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,''),ifnull(MailVillage,''),ifnull(MailBlock,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),concat(ifnull(fathername,''),if(ifnull(FatherMiddleName,'')= '',ifnull(FatherMiddleName,''),concat(' ', ifnull(FatherMiddleName,''))),"
                                                        + " if(ifnull(FatherLastName,'')= '', ifnull(FatherLastName,''),concat(' ', ifnull(FatherLastName,'')))) as FatherName from cbs_customer_master_detail"
                                                        + "  where customerid = (select CustId from customerid where acno ='" + acno + "');").getResultList();
                                                if (!custDetail.isEmpty()) {
                                                    Vector vect = (Vector) custDetail.get(0);
                                                    contact = vect.get(0).toString();
                                                    perAdd = vect.get(1).toString().concat(vect.get(2).toString()).concat(vect.get(3).toString()).concat(vect.get(4).toString()).concat(vect.get(5).toString()).concat(vect.get(6).toString());
                                                    currAdd = vect.get(7).toString().concat(vect.get(8).toString()).concat(vect.get(9).toString()).concat(vect.get(10).toString()).concat(vect.get(11).toString()).concat(vect.get(12).toString());
                                                    fatherName = vect.get(13).toString();
                                                }
                                                List tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table_name + " where acno='" + acno + "' and dt='" + minUnPaidDt + "'").getResultList();
                                                if (!tempList.isEmpty()) {
                                                    Vector ele = (Vector) tempList.get(0);
                                                    if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                                                        lastCrAmt = new BigDecimal(ele.get(0).toString());
                                                    }
                                                }
                                                pojo.setAddress(perAdd);
                                                pojo.setCurrAddress(currAdd);
                                                pojo.setContactNo(contact);
                                                pojo.setLastCrAmt(lastCrAmt);
                                                pojo.setSancAmt(sancAmt);
                                                pojo.setSancDate(dmyFormat.format(y_m_dFormat.parse(sancDate)));
                                                pojo.setDisburseAmt(BigDecimal.valueOf(amtDis));
                                                pojo.setDisburseDt(disbDt);
                                                pojo.setFatherName(fatherName);
                                                dataList.add(pojo);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<LoanIntDetailsMonthWisePojo> getAccountDetailsMonthWise(String brnCode, String acType, String frDt, String toDt, String schemeType) throws ApplicationException {
        List<LoanIntDetailsMonthWisePojo> resultList = new ArrayList<>();
        LoanIntDetailsMonthWisePojo pojo = new LoanIntDetailsMonthWisePojo();
        try {
            String branchCodeQuery = "";
            if (!(brnCode.equalsIgnoreCase("0A") || brnCode.equalsIgnoreCase("90"))) {
                branchCodeQuery = " and substring(am.acno,1,2) ='" + brnCode + "'";
            } else {
                Integer bankCode = fts.getCodeForReportName("LOAN_AT_HO");
                if (bankCode == 1 && brnCode.equalsIgnoreCase("90")) {
                    branchCodeQuery = " and substring(am.acno,1,2) = '" + brnCode + "'  ";
                } else {
                    branchCodeQuery = " ";
                }
            }
            String schemeQuery = "";
            if (!schemeType.equalsIgnoreCase("ALL")) {
                schemeQuery = " and acm.SCHEME_CODE='" + schemeType + "' ";
            }
            List acList = new ArrayList();
            if (acType.equalsIgnoreCase("All")) {
                acList = em.createNativeQuery("select zz.acno, zz.Sanctionlimit,zz.custname,zz.ODLimit,zz.sanctionlimitdt, zz.branchcode,zz.outstanding,zz.actype,zz.scheme_code,(select SCHEME_DESCRIPTION from cbs_scheme_general_scheme_parameter_master where  SCHEME_CODE=zz.scheme_code) as schemeDesc from "
                        + " ( select r.acno ,ifnull(r.sanctionlimit,0) as sanctionlimit,r.custname,ifnull(r.ODLimit,0) as ODLimit, "
                        + " ifnull(date_format(r.sanctionlimitdt,'%Y-%m-%d'), '1900-01-01') as sanctionlimitdt, substring(r.acno,1,2) as branchcode,"
                        + " cast(ifnull(r.outstanding,0) as decimal) as outstanding,substring(r.acno,3,2) as actype,r.scheme_code from ( "
                        + " select r.acno ,ifnull(l.sanctionlimit,0) as sanctionlimit,am.custname,ifnull(l.ODLimit,0) as ODLimit, "
                        + " ifnull(date_format(l.sanctionlimitdt,'%Y-%m-%d'), '1900-01-01') as sanctionlimitdt, substring(r.acno,1,2) as branchcode,"
                        + " cast(ifnull(sum(r.cramt-r.dramt),0) as decimal) as outstanding,substring(am.acno,3,2) as actype,acm.SCHEME_CODE as scheme_code  from ca_recon r,loan_appparameter l,accountmaster am ,cbs_loan_acc_mast_sec acm where r.acno=l.acno "
                        + " and r.acno=am.acno and acm.acno =am.acno " + schemeQuery + " and dt <= '" + toDt + "' and am.accttype in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') and crdbflag in ('B','D')) and (am.OpeningDt <= '" + frDt + "' or (am.Openingdt>'" + frDt + "' and am.openingdt<='" + toDt + "')) and"
                        + " (am.closingdate is null or am.closingdate ='' or am.closingdate >'" + frDt + "') " + branchCodeQuery + " "
                        + " "
                        + " union all"
                        + " select r.acno ,ifnull(l.sanctionlimit,0) as sanctionlimit,am.custname,ifnull(l.ODLimit,0) as ODLimit, "
                        + " ifnull(date_format(l.sanctionlimitdt,'%Y-%m-%d'), '1900-01-01') as sanctionlimitdt, substring(r.acno,1,2) as branchcode,"
                        + " cast(ifnull(sum(r.cramt-r.dramt),0) as decimal) as outstanding,substring(am.acno,3,2) as actype,acm.SCHEME_CODE as scheme_code from loan_recon r,loan_appparameter l,accountmaster am ,cbs_loan_acc_mast_sec acm where r.acno=l.acno "
                        + " and r.acno=am.acno and acm.acno =am.acno " + schemeQuery + " and dt <= '" + toDt + "' and am.accttype in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') and crdbflag in ('B','D')) and (am.OpeningDt <= '" + frDt + "' or (am.Openingdt>'" + frDt + "' and am.openingdt<='" + toDt + "')) and"
                        + " (am.closingdate is null or am.closingdate ='' or am.closingdate >'" + frDt + "') " + branchCodeQuery + " "
                        + " group by r.acno ,l.sanctionlimit,l.sanctionlimitdt, l.presentstatus, am.custname ,l.ODLimit) r  group by  r.acno ,r.sanctionlimit,r.sanctionlimitdt,r.custname ,r.ODLimit ) zz "
                        + " left join "
                        + " (select ast.acno as acno,ast.effdt as npaEffDt, cast(ast.spflag as decimal) as npaSpflag  from accountstatus ast, "
                        + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                        + " (select acno as ano, max(effdt) as dt from accountstatus where (effdt  <='" + toDt + "' or effdt>'" + frDt + "')  group by acno) b where aa.acno = b.ano "
                        + " and aa.effdt = b.dt group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) npa "
                        + " on npa.acno = zz.acno "
                        + " left join "
                        + " codebook cbk on npa.npaSpflag = cbk.code and cbk.groupcode = 3 order by zz.acno").getResultList();
            } else {
                String tableName = common.getTableName(common.getAcNatureByAcType(acType));
                acList = em.createNativeQuery("select zz.acno, zz.Sanctionlimit,zz.custname,zz.ODLimit,zz.sanctionlimitdt, zz.branchcode,zz.outstanding,zz.actype,zz.scheme_code,(select SCHEME_DESCRIPTION from cbs_scheme_general_scheme_parameter_master where  SCHEME_CODE=zz.scheme_code) as schemeDesc from "
                        + " ( select r.acno ,ifnull(l.sanctionlimit,0) as sanctionlimit,am.custname,ifnull(l.ODLimit,0) as ODLimit, "
                        + " ifnull(date_format(l.sanctionlimitdt,'%Y-%m-%d'), '1900-01-01') as sanctionlimitdt, substring(r.acno,1,2) as branchcode,"
                        + " cast(ifnull(sum(r.cramt-r.dramt),0) as decimal) as outstanding,substring(am.acno,3,2) as actype ,acm.SCHEME_CODE  from " + tableName + " r,loan_appparameter l,accountmaster am,cbs_loan_acc_mast_sec acm  where r.acno=l.acno "
                        + " and r.acno=am.acno  and acm.acno =am.acno " + schemeQuery + " and dt <= '" + toDt + "' and am.accttype='" + acType + "' and (am.OpeningDt <= '" + frDt + "' or (am.Openingdt>'" + frDt + "' and am.openingdt<='" + toDt + "')) and"
                        + " (am.closingdate is null or am.closingdate ='' or am.closingdate >'" + frDt + "') " + branchCodeQuery + " "
                        + " group by r.acno ,l.sanctionlimit,l.sanctionlimitdt, l.presentstatus, am.custname ,l.ODLimit ) zz "
                        + " left join "
                        + " (select ast.acno as acno,ast.effdt as npaEffDt, cast(ast.spflag as decimal) as npaSpflag  from accountstatus ast, "
                        + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                        + " (select acno as ano, max(effdt) as dt from accountstatus where (effdt  <='" + toDt + "' or effdt>'" + frDt + "')  group by acno) b where aa.acno = b.ano "
                        + " and aa.effdt = b.dt group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno ) npa "
                        + " on npa.acno = zz.acno "
                        + " left join "
                        + " codebook cbk on npa.npaSpflag = cbk.code and cbk.groupcode = 3 order by zz.acno").getResultList();
            }
            if (!acList.isEmpty()) {
                for (int i = 0; i < acList.size(); i++) {
                    Vector vect = (Vector) acList.get(i);
                    String acno = vect.get(0).toString();
                    BigDecimal sancLimit = new BigDecimal(vect.get(1).toString());
                    String custName = vect.get(2).toString();
                    BigDecimal odLimit = new BigDecimal(vect.get(3).toString());
                    String sancDt = vect.get(4).toString();
                    String branchCode = vect.get(5).toString();
                    BigDecimal outStanding = new BigDecimal(vect.get(6).toString());
                    String flag = outStanding.doubleValue() > 0 ? "Cr" : "Dr";
                    List dateList = em.createNativeQuery("select distinct valuedt,ifnull(sum(dramt),0) from " + common.getTableName(common.getAcNatureByAcNo(acno)) + " where acno =" + acno + " and valuedt between '" + frDt + "' and '" + toDt + "' and TranType ='8' group by valuedt").getResultList();
                    for (int j = 0; j < dateList.size(); j++) {
                        Vector intVect = (Vector) dateList.get(j);
                        String intdt = intVect.get(0).toString();
                        BigDecimal intAmt = new BigDecimal(intVect.get(1).toString());
                        BigDecimal crAmt = new BigDecimal("0");
                        List crList = em.createNativeQuery("select ifnull(sum(cramt),0) from " + common.getTableName(common.getAcNatureByAcNo(acno)) + " where acno =" + acno + " "
                                + " and valuedt between '" + CbsUtil.dateAdd(ymdFormat.format(CbsUtil.getLastDateOfMonth(y_m_dFormat.parse(intdt))), 1) + "' and '" + ymdFormat.format(CbsUtil.getLastDateOfMonth(ymdFormat.parse(CbsUtil.dateAdd(ymdFormat.format(CbsUtil.getLastDateOfMonth(y_m_dFormat.parse(intdt))), 1)))) + "' ").getResultList();
                        if (!crList.isEmpty()) {
                            Vector crVect = (Vector) crList.get(0);
                            crAmt = new BigDecimal(crVect.get(0).toString());
                        }
                        pojo = new LoanIntDetailsMonthWisePojo();
                        pojo.setAcType(vect.get(7).toString());
                        pojo.setAcNo(acno);
                        pojo.setCustName(custName);
                        pojo.setSanctionAmt(sancLimit);
                        pojo.setSanctionDate(dmyFormat.format(y_m_dFormat.parse(sancDt)));
                        pojo.setDpLimit(odLimit);
                        pojo.setOutstanding(outStanding.abs());
                        pojo.setDt(dmyFormat.format(y_m_dhs.parse(intdt)));
                        pojo.setIntCrAmt(crAmt);
                        pojo.setIntDrAmt(intAmt);
                        double diffAmt = crAmt.doubleValue() - intAmt.doubleValue();
                        String difference = diffAmt > 0 ? "0" : String.valueOf(diffAmt);
                        pojo.setDiffAmt(new BigDecimal(difference));
                        pojo.setDrCrInd(flag);
                        pojo.setSchemeType(vect.get(8).toString());
                        resultList.add(pojo);
                    }
                }
                SortedByAcNo orderByAcNo = new SortedByAcNo();
                Collections.sort(resultList, orderByAcNo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return resultList;
    }
}
