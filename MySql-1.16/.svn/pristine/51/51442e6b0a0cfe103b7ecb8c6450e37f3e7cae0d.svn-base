/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.td;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.AccountDetail;
import com.cbs.dto.RdInterestDTO;
import com.cbs.dto.report.TdPeriodMaturityPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.RDIntCalFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 *
 * @author root
 */
@Stateless(mappedName = "TdReceiptManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class TdReceiptManagementFacade implements TdReceiptManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPostMgmtRepote;
    @EJB
    AccountOpeningFacadeRemote openingFacadeRemote;
    @EJB
    RDIntCalFacadeRemote rdIntCalFacade;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    /*Start of TD Duplicate Receipt Issue*/
    public List getData() throws ApplicationException {
        try {
            List checkList = em.createNativeQuery("Select Acctcode From accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getProvApplyFlag(String acNo) throws ApplicationException {
        try {
            String acType = ftsPostMgmtRepote.getAccountCode(acNo);
            List checkList = em.createNativeQuery("Select ifnull(ProvAppOn,'') From accounttypemaster where acctcode='" + acType + "'").getResultList();
            Vector ele = (Vector) checkList.get(0);
            return ele.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List tableData(String accountNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acno,seqno,VoucherNo,receiptno,ROI,DATE_FORMAT(TD_MadeDt, '%d/%m/%Y') AS TD_MadeDt,"
                    + "DATE_FORMAT(FDDt, '%d/%m/%Y') AS FDDt,DATE_FORMAT(MatDt, '%d/%m/%Y') AS MatDt,PrinAmt,IntOpt,inttoacno,period,status,ofacno "
                    + "from td_vouchmst where  Acno = '" + accountNo + "'  order by voucherno");
            tableResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return tableResult;
    }

    public String getRtNumberInformation(String acno, String rtNumbers) throws ApplicationException {
        try {
            String acnos = null;
            String rtNumber = null;
            String receiptNo = null;
            String primAmt = null;
            List rtNumberList = em.createNativeQuery("select ACNO,VoucherNo,receiptno,PrinAmt from td_vouchmst where  Acno = '" + acno + "' and VoucherNo='" + rtNumbers + "'").getResultList();
            if (!rtNumberList.isEmpty()) {
                Vector rtNumberLists = (Vector) rtNumberList.get(0);
                acnos = rtNumberLists.get(0).toString();
                rtNumber = rtNumberLists.get(1).toString();
                receiptNo = rtNumberLists.get(2).toString();
                primAmt = rtNumberLists.get(3).toString();
            }

            return acnos + ": " + rtNumber + ": " + receiptNo + ": " + primAmt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List accountStatus(String accountNo) throws ApplicationException {
        List accountResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select acno,accstatus,custname from td_accountmaster where acno = '" + accountNo + "'");
            accountResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return accountResult;
    }

    public String delete(String accountNo, float receiptNo, float rtNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List secList = em.createNativeQuery("select acno from td_vouchmst_duplicate where acno = '" + accountNo + "' and receiptno = '" + receiptNo + "' and  VOUCHERNO='" + rtNo + "' and AUTH='N' AND AUTHBY IS NULL").getResultList();
            if (secList.isEmpty()) {
                ut.commit();
                return "Duplicate Details Does Not Exist";
            }
            Query updateQuery = em.createNativeQuery("update td_vouchmst set status = 'A' where acno = '" + accountNo + "' and receiptno = '" + receiptNo + "' and voucherno='" + rtNo + "'");
            int var1 = updateQuery.executeUpdate();

            Query deleteQuery = em.createNativeQuery("delete from td_vouchmst_duplicate where acno = '" + accountNo + "' and receiptno = '" + receiptNo + "' and voucherno='" + rtNo + "' and auth='N'");
            int var2 = deleteQuery.executeUpdate();

            if ((var1 > 0) && (var2 > 0)) {
                ut.commit();
                return "Issued Receipt Has Been Deleted";
            } else {
                ut.rollback();
                return "Data could not be Deleted";
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String receiptIssue(String accountNo, float receiptNo, float rtNo, String authBy,
            String accType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List secList = em.createNativeQuery("select acno from td_vouchmst where acno ='" + accountNo + "' and receiptno ='" + receiptNo + "' and  VOUCHERNO='" + rtNo + "' and status='C'").getResultList();
            if (!secList.isEmpty()) {
                throw new ApplicationException("Please Verify Receipt Details");
            }
            List recList = em.createNativeQuery("select * from td_vouchmst_duplicate where acno='" + accountNo + "' and receiptno='" + receiptNo + "' and  VOUCHERNO='" + rtNo + "'  AND auth = 'N' ").getResultList();
            if (!recList.isEmpty()) {
                throw new ApplicationException("Duplicate Receipt is Already Issued");
            }
            Query insertQuery = em.createNativeQuery("insert into td_vouchmst_duplicate(ACNO,VoucherNo,PrinAmt,ROI,IntOpt,FDDT,MatDt,status,ReceiptNo,"
                    + "IntToAcno,Years,Months,Days,Period,Lien,ClDt,FinalAmt,Penalty,NetRoi,PrevVoucherNo,EnterBy,auth,NextIntPayDt,CumuPrinAmt,"
                    + "TD_MadeDT,OFFlag,TDSDeducted,OFAcno,AutoRenew,SeqNo,TranTime) select ACNO,VoucherNo,PrinAmt,ROI,IntOpt,FDDT,MatDt,'A',ReceiptNo,"
                    + "IntToAcno,Years,Months,Days,Period,Lien,ClDt,FinalAmt,Penalty,NetRoi,PrevVoucherNo,'" + authBy + "','N',NextIntPayDt,"
                    + "CumuPrinAmt,TD_MadeDT,OFFlag,TDSDeducted,OFAcno,AutoRenew,SeqNo,now() from td_vouchmst where acno = '" + accountNo
                    + "' and receiptno = '" + receiptNo + "' and voucherno='" + rtNo + "' and status='A'");
            int var = insertQuery.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Receipt could not be Issued");
            }
            Query updateQuery = em.createNativeQuery("update td_vouchmst set status = 'C' where acno = '" + accountNo + "' and receiptno = '" + receiptNo + "' and voucherno='" + rtNo + "' and status='A'");
            int var1 = updateQuery.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Receipt could not be Issued");
            }
            ut.commit();
            return "Receipt Has Been Issued";

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String tdDupReceiptIssueClickGrid(String acno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String lblTDS;
            String lblBalance;
            String lblInt;
            if ((acno == null) || (acno.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "A/c no. should not be blank";
            }
            List list = em.createNativeQuery("select coalesce(sum(ifnull(tds,0)),0) from tdshistory where acno='" + acno + "'").getResultList();
            if (list.size() <= 0) {
                lblTDS = "0";
            } else {
                Vector Lst = (Vector) list.get(0);
                lblTDS = Lst.get(0).toString();
            }
            List balList = em.createNativeQuery("select coalesce(balance,0) from td_reconbalan where acno='" + acno + "'").getResultList();
            if (balList.size() <= 0) {
                lblBalance = "0";
            } else {
                Vector Lst = (Vector) balList.get(0);
                lblBalance = Lst.get(0).toString();
            }
            List intList = em.createNativeQuery("select coalesce(sum(a.interest),0) from td_interesthistory a,td_vouchmst b where b.acno='" + acno + "' and b.acno=a.acno  and a.voucherno=b.voucherno and b.status='A'").getResultList();
            Vector Lst = (Vector) intList.get(0);
            lblInt = Lst.get(0).toString();

            ut.commit();
            return lblTDS + ": " + lblBalance + ": " + lblInt;

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String orgFDInterest(String InterestOption, float roInt, String fdDate, String matDate,
            double amt, String prd, String brCode) throws ApplicationException {
        try {
            double msngTotalInt = 0.0d;
            if ((InterestOption.equalsIgnoreCase("M")) || (InterestOption.equalsIgnoreCase("Monthly"))) {

                double ab = 36500.0d;
                List bnkList = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
                Vector eleBnkCd = (Vector) bnkList.get(0);
                String bnkCode = eleBnkCd.get(0).toString();
                long dtDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
                if (dtDiff >= 30) {
                    if (bnkCode.equalsIgnoreCase("rcbl") || bnkCode.equalsIgnoreCase("citz")) {
                        List liveList = em.createNativeQuery("select ifnull(FinYearBegin,'" + fdDate + "') from parameterinfo where brncode = cast(substring('" + brCode + "',1,2) as unsigned) ").getResultList();
                        Vector liveDt = (Vector) liveList.get(0);
                        String lDt = liveDt.get(0).toString();

                        int lenDt = fdDate.length();
                        long dDiff = 0;
                        if (lenDt == 10) {
                            dDiff = CbsUtil.dayDiff(dmy.parse(fdDate), ymd.parse(lDt));
                        } else {
                            dDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(lDt));
                        }
                        if (dDiff <= 0) {
                            //Long aQuater = dtDiff / 30;
                            //String cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(aQuater.toString()));
                            //long newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));

                            Period pd = getFdPeriod(ymd.parse(fdDate), ymd.parse(matDate));

                            int aQuater = pd.getMonths() + pd.getYears() * 12;
                            int newDiff = pd.getDays() + pd.getWeeks() * 7;

                            double cummInt = amt - (amt * (1 / (1 + (roInt / 1200))));
                            cummInt = cummInt * aQuater;
                            if (newDiff > 0) {
                                msngTotalInt = amt * roInt * ((double) newDiff / ab);
                                msngTotalInt = msngTotalInt + cummInt;
                            } else {
                                msngTotalInt = msngTotalInt + cummInt;
                            }
                        } else {
                            ab = 36000.0d;
                            String period = getYMDPeriod(prd);
                            String[] values = period.split(":");
                            int mon = Integer.parseInt(values[1]);
                            int yrs = Integer.parseInt(values[0]);
                            int dd = Integer.parseInt(values[2]);
                            if (mon != 0 && (dd == 0 && yrs == 0)) {
                                Long aQuater = dtDiff / 30;
                                msngTotalInt = (amt * roInt * (aQuater * 30)) / ab;
                            } else {
                                msngTotalInt = (amt * roInt * dtDiff) / ab;
                            }
                        }
                    } else {
                        //Long aQuater = dtDiff / 30;
                        //String cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(aQuater.toString()));
                        //long newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));

                        Period pd = getFdPeriod(ymd.parse(fdDate), ymd.parse(matDate));

                        int aQuater = pd.getMonths() + pd.getYears() * 12;
                        int newDiff = pd.getDays() + pd.getWeeks() * 7;

                        double cummInt = amt - (amt * (1 / (1 + (roInt / 1200))));
                        cummInt = cummInt * aQuater;
                        if (newDiff > 0) {
                            msngTotalInt = amt * roInt * ((double) newDiff / ab);
                            msngTotalInt = msngTotalInt + cummInt;
                        } else {
                            msngTotalInt = msngTotalInt + cummInt;
                        }
                    }
                } else {
                    msngTotalInt = (amt * roInt * dtDiff) / ab;
                }
            }
            if ((InterestOption.equalsIgnoreCase("C")) || (InterestOption.equalsIgnoreCase("Cumulative"))) {
                long dtDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
                double ab = 36500.0d;
                List bnkList = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
                Vector eleBnkCd = (Vector) bnkList.get(0);
                String bnkCode = eleBnkCd.get(0).toString();
                if (dtDiff >= 90) {
                    String period = getYMDPeriod(prd);
                    String[] values = period.split(":");

                    int mon = Integer.parseInt(values[1]);
                    int yrs = Integer.parseInt(values[0]);
                    int daysInQuarter = 91;
                    long dDiff = 0;
                    if (bnkCode.equalsIgnoreCase("nccb") || bnkCode.equalsIgnoreCase("rcbl") || bnkCode.equalsIgnoreCase("citz")) {
                        List liveList = em.createNativeQuery("select ifnull(FinYearBegin,'" + fdDate + "') from parameterinfo where brncode = cast(substring('" + brCode + "',1,2) as unsigned) ").getResultList();
                        Vector liveDt = (Vector) liveList.get(0);
                        String lDt = liveDt.get(0).toString();

                        int lenDt = fdDate.length();

                        if (lenDt == 10) {
                            dDiff = CbsUtil.dayDiff(dmy.parse(fdDate), ymd.parse(lDt));
                        } else {
                            dDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(lDt));
                        }
                        if (dDiff <= 0) {
                            daysInQuarter = 91;
                        } else {
                            if (bnkCode.equalsIgnoreCase("rcbl") || bnkCode.equalsIgnoreCase("citz")) {
                                ab = 36000.0d;
                            }
                            daysInQuarter = 90;
                        }
                    }
                    Long activeQuater = (dtDiff / daysInQuarter);
                    long newDiff = 0;
                    if (yrs == 0 && mon == 0) {
                        newDiff = dtDiff - (activeQuater * daysInQuarter);
                    } else {
                        String cNewDate;
                        if ((bnkCode.equalsIgnoreCase("rcbl") || bnkCode.equalsIgnoreCase("citz")) && dtDiff > 0) {
                            int dd = Integer.parseInt(values[2]);
                            if (mon != 0 && dd == 0) {
                                if(dDiff<=0){
                                    long d = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse("20180418"));
                                    if(d<=0){
                                        newDiff = (mon - (Integer.parseInt(activeQuater.toString()) * 3)) * 30;
                                    }else{
                                        cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(activeQuater.toString()) * 3);
                                        newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));
                                    }                                    
                                }else{
                                    newDiff = (mon - (Integer.parseInt(activeQuater.toString()) * 3)) * 30;
                                }
                            } else {
                                if (dDiff <= 0) {
                                    cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(activeQuater.toString()) * 3);
                                } else {
                                    cNewDate = CbsUtil.dateAdd(fdDate, Integer.parseInt(activeQuater.toString()) * daysInQuarter);
                                }
                                newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));
//                                if ((bnkCode.equalsIgnoreCase("rcbl")) && dtDiff > 0) {
//                                    if(newDiff > 30){
//                                        newDiff = 30;
//                                    }
//                                }
                            }
                        } else {
                            cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(activeQuater.toString()) * 3);
                            newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));
                        }
                    }

                    double cummIntRate = 1.0d;
                    for (int i = 1; i <= activeQuater; i++) {
                        cummIntRate = (1 + (roInt / 400.0)) * cummIntRate;
                    }
                    double cummInt = (amt * cummIntRate) - amt;
                    double newPrnAmt = amt + cummInt;
                    if (newDiff > 0) {
                        msngTotalInt = newPrnAmt * roInt * ((double) newDiff / ab);
                        msngTotalInt = msngTotalInt + cummInt;
                    } else {
                        msngTotalInt = msngTotalInt + cummInt;
                    }
                } else {
                    if (bnkCode.equalsIgnoreCase("nccb") || bnkCode.equalsIgnoreCase("rcbl") || bnkCode.equalsIgnoreCase("citz")) {
                        List liveList = em.createNativeQuery("select ifnull(FinYearBegin,'" + fdDate + "') from parameterinfo where brncode = cast(substring('" + brCode + "',1,2) as unsigned) ").getResultList();
                        Vector liveDt = (Vector) liveList.get(0);
                        String lDt = liveDt.get(0).toString();

                        int lenDt = fdDate.length();
                        long dDiff = 0;
                        if (lenDt == 10) {
                            dDiff = CbsUtil.dayDiff(dmy.parse(fdDate), ymd.parse(lDt));
                        } else {
                            dDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(lDt));
                        }
                        if ((bnkCode.equalsIgnoreCase("rcbl") || bnkCode.equalsIgnoreCase("citz")) && dDiff > 0) {
                            ab = 36000.0d;
//                            if (dtDiff > 30) {
//                                dtDiff = 30;
//                            }
                        }
                    }
                    msngTotalInt = (amt * roInt * dtDiff) / ab;
                }
            }

            if ((InterestOption.equalsIgnoreCase("Q")) || (InterestOption.equalsIgnoreCase("Quarterly"))) {
                long dtDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
                if (dtDiff >= 90) {
                    Long aQuater = dtDiff / 90;
                    String cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(aQuater.toString()) * 3);

                    long newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));

                    double cummInt = (amt * (roInt / 400));
                    cummInt = cummInt * aQuater;
                    if (newDiff > 0) {
                        msngTotalInt = (amt * roInt * ((double) newDiff / 36500));
                        msngTotalInt = msngTotalInt + cummInt;
                    } else {
                        msngTotalInt = msngTotalInt + cummInt;
                    }

                } else {
                    msngTotalInt = (amt * roInt * dtDiff / 36500);
                }
            }

            if ((InterestOption.equalsIgnoreCase("Y")) || (InterestOption.equalsIgnoreCase("Yearly"))) {

                List bnkList = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
                Vector eleBnkCd = (Vector) bnkList.get(0);
                String bnkCode = eleBnkCd.get(0).toString();

                String period = getYMDPeriod(prd);
                String[] values = period.split(":");

                int dd = Integer.parseInt(values[2]);
                int mon = Integer.parseInt(values[1]);
                int yrs = Integer.parseInt(values[0]);

                if (bnkCode.equalsIgnoreCase("kccl") && roInt == 9.05f && dd == 0 && mon == 96 && yrs == 0) {
                    msngTotalInt = amt;
                } else {

                    // New Code Added For Simple Yearly Option 
                    double TmpSIntTot = 0.0d;
                    msngTotalInt = 0;
                    int Months = mon + (yrs * 12);
                    TmpSIntTot = (amt * roInt * Months / 1200);
                    msngTotalInt = msngTotalInt + TmpSIntTot;
                    TmpSIntTot = (amt * roInt * dd / 36500);
                    msngTotalInt = msngTotalInt + TmpSIntTot;

                    // Old Code Commented For Cumulative Yearly Option 
//                    long dtDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
//                    if (dtDiff >= 365) {
//
//                        int daysInYear = 365;
//
//                        Long activeYear = (dtDiff / daysInYear);
//                        long newDiff = 0;
//                        if (yrs == 0 && mon == 0) {
//                            newDiff = dtDiff - (activeYear * daysInYear);
//                        } else {
//                            String cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(activeYear.toString()) * 12);
//                            newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));
//                        }
//                        double ab = 36500.0d;
//                        double cummIntRate = 1.0d;
//                        for (int i = 1; i <= activeYear; i++) {
//                            cummIntRate = (1 + (roInt / 100.0)) * cummIntRate;
//                        }
//                        double cummInt = (amt * cummIntRate) - amt;
//                        double newPrnAmt = amt + cummInt;
//                        if (newDiff > 0) {
//                            msngTotalInt = newPrnAmt * roInt * ((double) newDiff / ab);
//                            msngTotalInt = msngTotalInt + cummInt;
//                        } else {
//                            msngTotalInt = msngTotalInt + cummInt;
//                        }
//                    } else {
//                        msngTotalInt = (amt * roInt * dtDiff) / 36500;
//                    }
                }
            }

            double TmpSIntTot = 0.0d;
            if ((InterestOption.equalsIgnoreCase("S")) || (InterestOption.equalsIgnoreCase("Simple"))) {
                msngTotalInt = 0;
                String period = getYMDPeriod(prd);
                String[] values = period.split(":");

                int mon = Integer.parseInt(values[1]);
                int day = Integer.parseInt(values[2]);
                int yrs = Integer.parseInt(values[0]);

                int Months = mon + (yrs * 12);
                TmpSIntTot = (amt * roInt * Months / 1200);
                msngTotalInt = msngTotalInt + TmpSIntTot;
                TmpSIntTot = (amt * roInt * day / 36500);
                msngTotalInt = msngTotalInt + TmpSIntTot;

            }
            return String.valueOf(Math.round(msngTotalInt));
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String orgFDInterestSimple15gh(String InterestOption, float roInt, String fdDate, String matDate,
            double amt, String prd, String brCode) throws ApplicationException {
        try {
            double TmpSIntTot = 0.0d, msngTotalInt = 0.0d;
            long fdDays = 0;
            long dtDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
            //long dtDiff = 365;
            if ((InterestOption.equalsIgnoreCase("S")) || (InterestOption.equalsIgnoreCase("Simple")) || (InterestOption.equalsIgnoreCase("Y"))) {
                msngTotalInt = 0;
                String period = getYMDPeriod(prd);
                String[] values = period.split(":");

                int mon = Integer.parseInt(values[1]);
                int day = Integer.parseInt(values[2]);
                int yrs = Integer.parseInt(values[0]);
                int Months = mon + (yrs * 12);
                fdDays = (Months * 30) + day;

                TmpSIntTot = (amt * roInt * (dtDiff + 1) / 36500);
                msngTotalInt = msngTotalInt + TmpSIntTot;

//                if (fdDays < dtDiff) {
//                    TmpSIntTot = (amt * roInt * Months / 1200);
//                    msngTotalInt = msngTotalInt + TmpSIntTot;
//                    TmpSIntTot = (amt * roInt * day / 36500);
//                    msngTotalInt = msngTotalInt + TmpSIntTot;
//                } else {
//                    day = (int) dtDiff;
//                    // TmpSIntTot = (amt * roInt * Months / 1200);
//                    msngTotalInt = msngTotalInt + TmpSIntTot;
//                    TmpSIntTot = (amt * roInt * day / 36500);
//                    msngTotalInt = msngTotalInt + TmpSIntTot;
//                }
            }
            return String.valueOf(Math.round(msngTotalInt));

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    private String getYMDPeriod(String period) throws ApplicationException {
        try {

            boolean dd = period.contains("Days");
            boolean mm = period.contains("Months");
            boolean yy = period.contains("Years");
            if (yy == true) {
                period = period.replace("Years", ":");
            } else {
                period = "0:" + period;
            }
            if (dd == true) {
                period = period.replace("Days", ":");
            } else {
                period = period + "0";
            }
            if (mm == true) {
                period = period.replace("Months", ":");
                return period;
            } else {
                String[] values = period.split(":");
                return values[0] + ":0:" + values[1];
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    /*End of TD Duplicate Receipt Issue*/

    /*Start of New FD Receipt Creation*/
    public List getCustFdInfo(String acno) throws ApplicationException {
        try {
            List accountMasterList = em.createNativeQuery("SELECT accstatus FROM td_accountmaster WHERE acno= '" + acno + "'").getResultList();
            if (accountMasterList.size() <= 0) {
                throw new ApplicationException("This Account No. Does not exist");
            }
            Vector acVect = (Vector) accountMasterList.get(0);
            int accStatus = Integer.parseInt(acVect.get(0).toString());
            if (accStatus == 9) {
                throw new ApplicationException("This Account Has Been Closed");
            }
            List tdList = em.createNativeQuery("SELECT am.acno,am.custname,am.cust_Type,am.TDSFlag,am.jtname1,am.tdsdetails,tr.balance "
                    + "FROM td_accountmaster am,td_customermaster cm, td_reconbalan tr WHERE am.acno = '" + acno + "' AND "
                    + "SUBSTRING(am.acno,5,6)=cm.CustNo and am.acno=tr.acno").getResultList();
            return tdList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List fnAcnat(String acType) throws ApplicationException {
        List accounttypemaster = new ArrayList();
        try {
            accounttypemaster = em.createNativeQuery("Select AcctNature,glhead from accounttypemaster where AcctCode = '" + acType + "'").getResultList();
            return accounttypemaster;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getAcctType() throws ApplicationException {
        List accountTypeMaster = new ArrayList();
        try {
            accountTypeMaster = em.createNativeQuery("Select distinct acctCode,productcode,glheadprov from accounttypemaster Where acctCode is not null and AcctNature In ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "') order by acctCode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return accountTypeMaster;
    }

    public List getAcctTypeToBeCredit() throws ApplicationException {
        List accountTypeMaster = new ArrayList();
        try {
            accountTypeMaster = em.createNativeQuery("select distinct acctcode from accounttypemaster where acctNature not in ('" + CbsConstant.OF_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.PAY_ORDER + "') and acctCode is not null order by acctCode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return accountTypeMaster;
    }

    public List getTdRecieptSeq() throws ApplicationException {
        List tdparameterinfo = new ArrayList();
        try {
            tdparameterinfo = em.createNativeQuery("select coalesce(receiptno_seq,'') as receiptno_seq from td_parameterinfo").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return tdparameterinfo;
    }

    public List getAutoRenew() throws ApplicationException {
        List tdparameterinfo = new ArrayList();
        try {
            tdparameterinfo = em.createNativeQuery("select count(code) as code from parameterinfo_report where reportname ='TD AUTO RENEWAL' and code = 1").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return tdparameterinfo;
    }

    public List getBookSeries(String acctNature, String receipt, String brCode) throws ApplicationException {
        try {
            List tdreceiptissue = new ArrayList();
            if (receipt.equalsIgnoreCase("C")) {
                tdreceiptissue = em.createNativeQuery("select distinct series from td_receiptissue where scheme='FD' and status='F' And brncode = '" + brCode + "'").getResultList();
            } else if (receipt.equalsIgnoreCase("T")) {
                tdreceiptissue = em.createNativeQuery("select distinct series from td_receiptissue where scheme='" + acctNature + "' and status='F' And brncode = '" + brCode + "'").getResultList();
            } else if (receipt.equalsIgnoreCase("N")) {
                tdreceiptissue = em.createNativeQuery("select distinct series from td_receiptissue where scheme='" + acctNature + "' and status='F' And brncode = '" + brCode + "'").getResultList();
            } else {
                tdreceiptissue = em.createNativeQuery("select distinct series from td_receiptissue where scheme='" + acctNature + "' and status='F' And brncode = '" + brCode + "'").getResultList();
            }
            return tdreceiptissue;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public float tdApplicableROI(String acno, String custCat, double tOTAMT, String matDt, String wefDt, String presentDt, String acNat) throws ApplicationException {
        try {
            long tmpFromDays = CbsUtil.dayDiff(ymd.parse(wefDt), ymd.parse(matDt));
            if (tmpFromDays <= 0) {
                tmpFromDays = 1;
            }
            List tdSlabList = em.createNativeQuery("select Interest_rate,Applicable_Date,ST,SC,MG From td_slab where fromDays <=" + tmpFromDays
                    + " and toDays >=" + tmpFromDays + " and  fromAmount <= " + tOTAMT + " and toAmount >= " + tOTAMT + " and acctNature='" + acNat + "' and Applicable_Date in "
                    + "(select max(applicable_Date) from td_slab where applicable_date<='" + wefDt + "' and acctNature='" + acNat + "')").getResultList();
//            if (tdSlabList.isEmpty()) {
//                throw new ApplicationException("Please define the Interest slab accordingly");
//            }
            float wefROI = 0, wefSt = 0, wefSc = 0, wefMg = 0;

            if (!tdSlabList.isEmpty()) {
                Vector descVect = (Vector) tdSlabList.get(0);
                wefROI = Float.parseFloat(descVect.get(0).toString());
                wefSt = Float.parseFloat(descVect.get(2).toString());
                wefSc = Float.parseFloat(descVect.get(3).toString());
                wefMg = Float.parseFloat(descVect.get(4).toString());
            }
            /*else {

             List tDSlabList = em.createNativeQuery("select Interest_rate, Applicable_Date,ST,SC From td_slab where fromDays <=" + tmpFromDays
             + " and toDays >=" + tmpFromDays + " and fromAmount <= " + tOTAMT + " and toAmount >= " + tOTAMT + " and acctNature='" + acNat + "' and Applicable_Date "
             + "in (select max(applicable_Date) from td_slab where applicable_date<='" + wefDt + "' and acctNature='" + acNat + "')").getResultList();
             if (tDSlabList.size() > 0) {
             Vector descVect = (Vector) tDSlabList.get(0);
             interestRate = Float.parseFloat(descVect.get(0).toString());
             st = Float.parseFloat(descVect.get(2).toString());
             sc = Float.parseFloat(descVect.get(3).toString());
             wefROI = interestRate;
             wefSc = sc;
             wefSt = st;
             }
             }*/
            List slabList = em.createNativeQuery("select Interest_rate,Applicable_Date,ST,SC,MG From td_slab where fromDays <=" + tmpFromDays
                    + " and toDays >= " + tmpFromDays + " and fromAmount <= " + tOTAMT + " and toAmount >= " + tOTAMT + " and acctNature='"
                    + acNat + "' and Applicable_Date in (select max(applicable_Date) from td_slab where applicable_date<='" + presentDt
                    + "' and fromDays <= " + tmpFromDays + " and toDays >= " + tmpFromDays + " and acctNature='" + acNat + "' )").getResultList();
            if (slabList.isEmpty()) {
                throw new ApplicationException("Please define the Interest slab accordingly");
            }
            float preROI = 0, preSt = 0, preSc = 0, preMg = 0;
            if (!slabList.isEmpty()) {
                Vector presVect = (Vector) slabList.get(0);
                preROI = Float.parseFloat(presVect.get(0).toString());
                preSt = Float.parseFloat(presVect.get(2).toString());
                preSc = Float.parseFloat(presVect.get(3).toString());
                preMg = Float.parseFloat(presVect.get(4).toString());
            }

//            else {
//                List tdList = em.createNativeQuery("select Interest_rate,Applicable_Date,ST,SC From td_slab where fromDays <=" + tmpFromDays
//                        + " and toDays >= " + tmpFromDays + " and fromAmount <= " + tOTAMT + " and toAmount >= " + tOTAMT + " and acctNature='"
//                        + acNat + "' and Applicable_Date in (select max(applicable_Date) from td_slab where applicable_date<='" + presentDt
//                        + "' and fromDays <=" + tmpFromDays + " and toDays >=" + tmpFromDays + " and acctNature='" + acNat + "')").getResultList();
//                if (tdList.size() > 0) {
//                    Vector descVect = (Vector) tdList.get(0);
//                    interestRate = Float.parseFloat(descVect.get(0).toString());
//                    st = Float.parseFloat(descVect.get(2).toString());
//                    sc = Float.parseFloat(descVect.get(3).toString());
//                    preROI = interestRate;
//                    preSc = sc;
//                    preSt = st;
//                }
//            }
            int dblBenifitAge = 0;
            boolean staffSc = false;
            if (!acno.equals("") && custCat.equalsIgnoreCase("ST")) {
                dblBenifitAge = ftsPostMgmtRepote.getCodeForReportName("DOUBLE-BENIFIT-AGE");
                if (dblBenifitAge > 50) {
                    staffSc = isStaffSeniorCitizen(acno, dblBenifitAge, wefDt);
                }
            }
            float extraRoi = 0;
            if (wefROI <= preROI) {
                if (custCat.equalsIgnoreCase("SC")) {
                    extraRoi = wefSc;
                } else if (custCat.equalsIgnoreCase("MG")) {
                    extraRoi = wefMg;
                } else if (custCat.equalsIgnoreCase("ST")) {
                    if (staffSc) {
                        extraRoi = wefSt + wefSc;
                    } else {
                        extraRoi = wefSt;
                    }
                }
            } else {
                if (custCat.equalsIgnoreCase("SC")) {
                    extraRoi = preSc;
                } else if (custCat.equalsIgnoreCase("MG")) {
                    extraRoi = preMg;
                } else if (custCat.equalsIgnoreCase("ST")) {
                    if (staffSc) {
                        extraRoi = preSt + preSc;
                    } else {
                        extraRoi = preSt;
                    }
                }
            }
            return wefROI + extraRoi;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getGlobalFdCondition() throws ApplicationException {
        String maxDate = ymd.format(new Date());
        try {
            List resultlist = em.createNativeQuery("SELECT MAX(Applicable_Date) FROM tdcondition WHERE status = 'N'").getResultList();
            if (resultlist.size() > 0) {
                Vector resultlistVect = (Vector) resultlist.get(0);
                maxDate = resultlistVect.get(0).toString();
            }
            List listValue = em.createNativeQuery("SELECT TdAmount,TDDayMth,TDDayCum FROM tdcondition WHERE Applicable_Date = '" + maxDate + "'").getResultList();
            return listValue;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String newTdReciptCreation(String acctNature, String acNo, String vDate, String vDayBegin, String vDepositeDate,
            String vMatDate, double amount, String bookSeries, String enteredBy, String optInterest, String gLHeadProv,
            String acnoToCredit, float roi, int years, int months, int days, String autoRenew, String brCode,
            String autoPay, String paidAcno) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (amount == 0) {
                throw new ApplicationException("FD can not be created on zero or less then zero amount.");
            }
            List amtList = em.createNativeQuery("select round(ifnull(Balance,0),2) from td_reconbalan where acno = '" + acNo + "'").getResultList();
            Vector vectorAmt = (Vector) amtList.get(0);
            double checkAmount = Double.parseDouble(vectorAmt.get(0).toString());
            if (checkAmount < 0) {
                throw new ApplicationException("Insufficient balance.");
            } else if (amount > checkAmount) {
                throw new ApplicationException("Balance exceeds");
            }

            List tdReconList0 = em.createNativeQuery("SELECT round(ifnull((SUM(ifnull(cramt,0))-SUM(ifnull(Dramt,0))),0),2) FROM td_recon "
                    + "WHERE ACNO ='" + acNo + "' AND TRANTYPE<>27 AND CLOSEFLAG IS NULL AND DT<='" + vDayBegin + "' ").getResultList();
            double tdReconBal = 0;
            if (tdReconList0.size() > 0) {
                Vector tdReconVec = (Vector) tdReconList0.get(0);
                tdReconBal = Double.parseDouble(tdReconVec.get(0).toString());
            }
            List tdVouchmstList12 = em.createNativeQuery("SELECT ifnull(round(SUM(PRINAMT),2),0) FROM td_vouchmst WHERE ACNO ='" + acNo
                    + "' AND STATUS='A'").getResultList();
            double prinAmt = 0;
            if (tdVouchmstList12.size() > 0) {
                Vector tdVouchmstVec = (Vector) tdVouchmstList12.get(0);
                prinAmt = Double.parseDouble(tdVouchmstVec.get(0).toString());
            }
            tdReconBal = (tdReconBal - prinAmt);
            if ((tdReconBal - amount) < 0) {
                throw new ApplicationException("Please Check the Statement Balance");
            }

            if (optInterest.equalsIgnoreCase("S")) {
                if (!gLHeadProv.equals("")) {
                    acnoToCredit = brCode + gLHeadProv + "01";
                } else {
                    acnoToCredit = acNo;
                }
            }
            Integer tdInsert = em.createNativeQuery("INSERT INTO td_vouchmst_auth (ACNO, PrinAmt, ROI, IntOpt, FDDT, MatDt, IntToAcno, Years, "
                    + "Months, Days, EnterBy, Auth, AuthBy, TranTime, TD_MadeDT, AutoRenew,bookseries,auto_pay,auto_paid_acno) VALUES ('" + acNo + "'," + amount + ", " + roi
                    + ", '" + optInterest + "', '" + vDepositeDate + "', '" + vMatDate + "', '" + acnoToCredit + "'," + years + " , " + months + ","
                    + days + ", '" + enteredBy + "', 'N', '', now(), '" + vDate + "', '" + autoRenew + "','" + bookSeries + "','" + autoPay + "','" + paidAcno + "')").executeUpdate();
            if (tdInsert <= 0) {
                throw new ApplicationException("Data does not save");
            }
            List list = em.createNativeQuery("SELECT LAST_INSERT_ID()").getResultList();
            Vector ele = (Vector) list.get(0);

            String rs = ftsPostMgmtRepote.updateBalance(acctNature, acNo, 0, amount, "Y", "Y");
            if (!rs.equalsIgnoreCase("TRUE")) {
                throw new ApplicationException("Problem in Balance updation");
            }
            ut.commit();
            return "Data has been successfully saved and your Txn Ref no is " + Long.parseLong(ele.get(0).toString());
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }

    }

    public String newTdReciptAuthorization(String acctNature, String acNo, String vDate, String vDayBegin, String vDepositeDate,
            String vMatDate, double amount, String tdReceipt, String bookSeries, String brCode, String acctType,
            String enteredBy, String optInterest, String gLHeadProv, String acnoToCredit, float roi, String strPeriod,
            int years, int months, int days, String autoRenew, String authBy, long txnId,
            String autoPay, String paidAcno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String vStartDate = vDate.substring(0, 4) + "0101";
            String vEndDate = vDate.substring(0, 4) + "1231";

            if (amount == 0) {
                throw new ApplicationException("FD can not be created on zero or less then zero amount.");
            }
            List tdReconList0 = em.createNativeQuery("SELECT round(ifnull((SUM(ifnull(cramt,0))-SUM(ifnull(Dramt,0))),0),2) FROM td_recon "
                    + "WHERE ACNO ='" + acNo + "' AND TRANTYPE<>27 AND CLOSEFLAG IS NULL AND DT<='" + vDayBegin + "' ").getResultList();
            double tdReconBal = 0;
            if (tdReconList0.size() > 0) {
                Vector tdReconVec = (Vector) tdReconList0.get(0);
                tdReconBal = Double.parseDouble(tdReconVec.get(0).toString());
            }
            List tdVouchmstList12 = em.createNativeQuery("SELECT ifnull(round(SUM(PRINAMT),2),0) FROM td_vouchmst WHERE ACNO ='" + acNo
                    + "' AND STATUS='A'").getResultList();
            double prinAmt = 0;
            if (tdVouchmstList12.size() > 0) {
                Vector tdVouchmstVec = (Vector) tdVouchmstList12.get(0);
                prinAmt = Double.parseDouble(tdVouchmstVec.get(0).toString());
            }
            tdReconBal = (tdReconBal - prinAmt);
            if ((tdReconBal - amount) < 0) {
                throw new ApplicationException("Please Check the Statement Balance");
            }
            String receiptNo = "";
            if (tdReceipt.equalsIgnoreCase("C")) {
                List tdReceiptList = em.createNativeQuery("SELECT ifnull(MIN(ReceiptNo),'') FROM td_receiptissue WHERE Sno IN (SELECT MIN(sno) FROM td_receiptissue "
                        + "WHERE Status = 'F' AND Scheme ='FD' AND series = '" + bookSeries + "' And brncode = '" + brCode + "') AND Scheme ='FD' AND Status = 'F' AND series = '" + bookSeries + "' And brncode ='" + brCode + "'").getResultList();
                if (tdReceiptList.size() > 0) {
                    Vector tdReceiptVec = (Vector) tdReceiptList.get(0);
                    receiptNo = tdReceiptVec.get(0).toString();
                }

            } else if (tdReceipt.equalsIgnoreCase("N")) {
                List tdReceiptList = em.createNativeQuery(" SELECT ifnull(MIN(ReceiptNo),'') FROM td_receiptissue WHERE Sno IN (SELECT MIN(sno) From"
                        + " td_receiptissue WHERE Status = 'F' AND Scheme ='" + acctNature + "' AND series='" + bookSeries + "' And brncode = '" + brCode + "') AND Scheme ='" + acctNature + "' "
                        + "AND Status = 'F' AND series = '" + bookSeries + "' And brncode = '" + brCode + "'").getResultList();
                if (tdReceiptList.size() > 0) {
                    Vector tdReceiptVec = (Vector) tdReceiptList.get(0);
                    receiptNo = tdReceiptVec.get(0).toString();
                }

            } else if (tdReceipt.equalsIgnoreCase("T")) {
                List tdReceiptList = em.createNativeQuery("SELECT ifnull(MIN(ReceiptNo),'') FROM td_receiptissue WHERE Sno IN (SELECT MIN(sno) "
                        + "From td_receiptissue WHERE Status = 'F' AND Scheme ='" + acctType + "' AND series='" + bookSeries + "' And brncode = '" + brCode + "') AND Scheme ='" + acctType + "' "
                        + "AND Status = 'F' AND series='" + bookSeries + "' And brncode = '" + brCode + "'").getResultList();
                if (tdReceiptList.size() > 0) {
                    Vector tdReceiptVec = (Vector) tdReceiptList.get(0);
                    receiptNo = tdReceiptVec.get(0).toString();
                }
            } else {
                List tdReceiptList = em.createNativeQuery("SELECT ifnull(MIN(ReceiptNo),'') FROM td_receiptissue WHERE Sno IN (SELECT MIN(sno) "
                        + "From td_receiptissue WHERE Status = 'F' AND Scheme ='" + acctNature + "' AND series='" + bookSeries + "' And brncode = '" + brCode + "') AND Scheme ='" + acctNature + "' "
                        + "AND Status = 'F' AND series='" + bookSeries + "' And brncode = '" + brCode + "'").getResultList();

                if (tdReceiptList.size() > 0) {
                    Vector tdReceiptVec = (Vector) tdReceiptList.get(0);
                    receiptNo = tdReceiptVec.get(0).toString();
                }
            }
            if (receiptNo == null || receiptNo.equalsIgnoreCase("") || receiptNo.equalsIgnoreCase("0.0")) {
                throw new ApplicationException("Receipt No.Does Not Exist");
            }
            Float tmpRno = Float.parseFloat(receiptNo);
            Float maxVouchNo = 1f;
            List tdVouchList = em.createNativeQuery("SELECT ifnull(MAX(voucherno),0) FROM td_vouchmst WHERE SUBSTRING(acno,3,2)=SUBSTRING('" + acNo + "',3,2)").getResultList();
            if (tdVouchList.size() > 0) {
                Vector tdVouchVec = (Vector) tdVouchList.get(0);
                maxVouchNo = Float.parseFloat(tdVouchVec.get(0).toString()) + 1;
            }
            if (tdReceipt.equalsIgnoreCase("C")) {
                Integer tdReceiptUpdate = em.createNativeQuery("UPDATE td_receiptissue SET status='U' ,LastUpdateBy='" + enteredBy + "', LastUpdateDt='" + vDayBegin + "'"
                        + " WHERE sno IN (SELECT id from (SELECT MIN(sno) as id FROM td_receiptissue WHERE receiptno=" + tmpRno + " AND status = 'F' AND "
                        + "scheme ='FD' and brncode ='" + brCode + "') as temp) AND scheme ='FD' AND status = 'F' And brncode = '" + brCode + "' ").executeUpdate();
                if (tdReceiptUpdate <= 0) {
                    throw new ApplicationException("Data does not update");
                }
            } else if (tdReceipt.equalsIgnoreCase("N")) {
                Integer tdReceiptUpdate = em.createNativeQuery(" UPDATE td_receiptissue SET status='U' ,LastUpdateBy='" + enteredBy + "', LastUpdateDt='" + vDayBegin + "' "
                        + "WHERE sno IN (SELECT id from (SELECT MIN(sno) as id FROM td_receiptissue WHERE receiptno= " + tmpRno + " AND status = 'F' AND"
                        + " scheme ='" + acctType + "' and brncode = '" + brCode + "') as temp) AND scheme ='" + acctType + "' AND status = 'F' and brncode = '" + brCode + "'").executeUpdate();
                if (tdReceiptUpdate <= 0) {
                    throw new ApplicationException("Data does not update");
                }
            } else if (tdReceipt.equalsIgnoreCase("T")) {
                Integer tdReceiptUpdate = em.createNativeQuery("UPDATE td_receiptissue SET status='U' ,LastUpdateBy='" + enteredBy + "', LastUpdateDt='" + vDayBegin + "'"
                        + " WHERE sno IN (SELECT id from (SELECT MIN(sno) as id FROM td_receiptissue WHERE receiptno= " + tmpRno + " AND status = 'F' AND"
                        + " scheme ='" + acctNature + "' and brncode = '" + brCode + "') as temp) AND scheme ='" + acctNature + "' AND status = 'F' and brncode = '" + brCode + "'").executeUpdate();
                if (tdReceiptUpdate <= 0) {
                    throw new ApplicationException("Data does not update");
                }
            } else {
                Integer tdReceiptUpdate = em.createNativeQuery("UPDATE td_receiptissue SET status='U' ,LastUpdateBy='" + enteredBy + "', LastUpdateDt='" + vDayBegin + "' "
                        + "WHERE sno IN (SELECT id from (SELECT MIN(sno) as id FROM td_receiptissue WHERE receiptno= " + tmpRno + " AND status = 'F' AND scheme ='" + acctNature + "' "
                        + "and brncode = '" + brCode + "') as temp) AND scheme ='" + acctNature + "' AND status = 'F' and brncode = '" + brCode + "'").executeUpdate();
                if (tdReceiptUpdate <= 0) {
                    throw new ApplicationException("Data does not update");
                }
            }
            String detail = " Trf To " + acNo + " " + maxVouchNo; //acNo.substring(3, 8)
            float recno = ftsPostMgmtRepote.getRecNo();
            String currBrnCode = ftsPostMgmtRepote.getCurrentBrnCode(acNo);
            float trSno = ftsPostMgmtRepote.getTrsNo();
            Integer tdReconInsert = em.createNativeQuery("INSERT INTO td_recon (acno,ty,dt,ValueDt,VOUCHERNO,fddt,trantype,dramt,cramt,iy,"
                    + "instno,recno,DETAILS,payby,trsno,trandesc,tokenno,subtokenno,tokenpaid,enterby,auth,authby,org_brnid,dest_brnid)"
                    + " VALUES ('" + acNo + "',0,'" + vDate + "','" + vDepositeDate + "'," + maxVouchNo + ",'" + vDepositeDate + "',27,0," + amount + ",1,NULL," + recno + ",'" + detail + "',3,"
                    + "" + trSno + ",2,0,0,'N','" + enteredBy + "','Y','" + enteredBy + "','" + brCode + "','" + currBrnCode + "')").executeUpdate();
            if (tdReconInsert <= 0) {
                throw new ApplicationException("Data does not update");
            }
            recno = ftsPostMgmtRepote.getRecNo();
            trSno = ftsPostMgmtRepote.getTrsNo();

            Integer tdReconInsert1 = em.createNativeQuery("INSERT INTO td_recon (acno,ty,dt,ValueDt,VOUCHERNO,fddt,trantype,"
                    + "dramt,cramt,iy,instno,recno,DETAILS,payby,trsno,trandesc,tokenno,subtokenno,tokenpaid,enterby,auth,authby,org_brnid,dest_brnid)"
                    + "VALUES ('" + acNo + "',1,'" + vDate + "','" + vDepositeDate + "'," + maxVouchNo + ",'" + vDepositeDate + "',27," + amount + ",0,1,NULL," + recno + ",'" + detail + "',3," + trSno + ",2,0,0,'N',"
                    + "'" + enteredBy + "','Y','" + enteredBy + "','" + brCode + "','" + currBrnCode + "')").executeUpdate();
            if (tdReconInsert1 <= 0) {
                throw new ApplicationException("Data does not update");
            }

            Float tmpVSeqNo = 1f;
            String seqNo = "";
            List controlTypeList = em.createNativeQuery("SELECT ifnull(ControlType,'') FROM td_parameterinfo").getResultList();
            if (controlTypeList.size() > 0) {
                Vector controlTypeVec = (Vector) controlTypeList.get(0);
                String controlType = controlTypeVec.get(0).toString();

                if (controlType.equalsIgnoreCase("T")) {
                    String query = "SELECT ifnull(MAX(SeqNo),0) FROM td_vouchmst WHERE TD_MadeDt "
                            + "BETWEEN '" + vStartDate + "' AND '" + vEndDate + "' AND SUBSTRING(acno,3,2)  = SUBSTRING('" + acNo + "',3,2)";
                    List seqNoList = em.createNativeQuery(query).getResultList();
                    if (seqNoList.size() > 0) {
                        Vector seqNoVec = (Vector) seqNoList.get(0);
                        seqNo = seqNoVec.get(0).toString();
                    }
                } else if (controlType.equalsIgnoreCase("N")) {
                    String query = "SELECT ifnull(MAX(SeqNo),0) FROM td_vouchmst Where TD_MadeDt BETWEEN '" + vStartDate + "' AND '" + vEndDate
                            + "' AND SUBSTRING(acno,3,2) IN (SELECT acctCode FROM accounttypemaster WHERE acctNature = '" + acctNature + "')";
                    List seqNoList = em.createNativeQuery(query).getResultList();
                    if (seqNoList.size() > 0) {
                        Vector seqNoVec = (Vector) seqNoList.get(0);
                        seqNo = seqNoVec.get(0).toString();
                    }
                }
            }
            if (seqNo == null || seqNo.equalsIgnoreCase("") || seqNo.equalsIgnoreCase("0.0")) {
                tmpVSeqNo = 1f;
            } else {
                tmpVSeqNo = Float.parseFloat(seqNo) + 1;
            }

            if (optInterest.equalsIgnoreCase("S")) {
                if (!gLHeadProv.equals("")) {
                    acnoToCredit = brCode + gLHeadProv + "01";
                } else {
                    acnoToCredit = acNo;
                }
            }
            Integer tdInsert = em.createNativeQuery("INSERT INTO td_vouchmst (Acno,Voucherno,ReceiptNo,Prinamt,Fddt,matDt,roi,intOpt,"
                    + "autoRenew,SeqNo,Auth,Authby,enterBy,Status,period,years,months,days,NextIntPayDt,cumuprinamt,TD_MadeDt,"
                    + "intToAcno,trantime,offlag,auto_pay,auto_paid_acno) VALUES('" + acNo + "'," + maxVouchNo + "," + tmpRno + "," + amount + ",'" + vDepositeDate
                    + "','" + vMatDate + "'," + roi + ",'" + optInterest + "','" + autoRenew + "'," + tmpVSeqNo + ",'Y','" + authBy + "','"
                    + enteredBy + "','A','" + strPeriod + "'," + years + "," + months + "," + days + ",'" + vDepositeDate + "'," + amount + ",'"
                    + vDate + "','" + acnoToCredit + "',now(),'N','" + autoPay + "','" + paidAcno + "')").executeUpdate();
            if (tdInsert <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }
            String query = "update td_vouchmst_auth set auth = 'Y', authby= '" + authBy + "' where auth='N' and acno = '" + acNo + "' and txnid = " + txnId;
            int result = em.createNativeQuery(query).executeUpdate();
            if (result <= 0) {
                throw new ApplicationException("Problem in Receipt Details updation");
            }

            List cuIdList = em.createNativeQuery("select custid from customerid where acno = '" + acNo + " '").getResultList();
            Vector cuIdLst = (Vector) cuIdList.get(0);
            String custId = cuIdLst.get(0).toString();

            String alertMsg = "";
            String docMsg = openingFacadeRemote.getCustAcTdsDocDtl(custId, "", "C");
            if (docMsg.equalsIgnoreCase("true")) {
                alertMsg = " Please Collect TDS Doc From This Customer";

                List cuList = em.createNativeQuery("select distinct doc_details,fyear,uniqueIdentificationNo,customerid from tds_docdetail where customerid = '" + custId + " ' ").getResultList();
                if(cuList.isEmpty()){
                    cuList = em.createNativeQuery("select distinct doc_details,fyear,uniqueIdentificationNo,customerid from tds_docdetail where customerid in " +
                            "(select ifnull(guardiancode,'') from cbs_cust_minorinfo where customerid = '" + custId + "' and ifnull(guardiancode,'') <> '')").getResultList();
                }
                Vector cuLst = (Vector) cuList.get(0);
                String docDtl = cuLst.get(0).toString();
                String fYear = cuLst.get(1).toString();
                String uin = cuLst.get(2).toString();
                String custMjrId = cuLst.get(3).toString();

                List secList = em.createNativeQuery("select coalesce(max(seqno),0)+1 from tds_docdetail where customerid = '" + custMjrId + " '").getResultList();
                Vector secLst = (Vector) secList.get(0);
                String secListed = secLst.get(0).toString();
                int secnum = Integer.parseInt(secListed);

                Query insertQuery = em.createNativeQuery("insert into tds_docdetail(customerid,acno,seqNo,submission_date,"
                        + "fyear,receiptNo,doc_details,docFlag,orgBrnid,tranTime,enterBy,auth,uniqueIdentificationNo)"
                        + "values ('" + custMjrId + "','" + acNo + "'," + secnum + ",now()," + fYear + ",'" + maxVouchNo + "','" + docDtl + "','Y','" + brCode + "',now(),'" + enteredBy + "','N','" + uin + "')");
                int var = insertQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Data could not be Inserted");
                }
            }

            ut.commit();
            return "Generated Receipt No is -> " + tmpRno.longValue() + ", Generated RT No is -> " + maxVouchNo.longValue()
                    + " and Generated Control No is ->" + tmpVSeqNo.longValue() + "  " + alertMsg;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }

    }
    /*End of New FD Receipt Creation*/

    /*Start of TD Receipt Search*/
    public List getTdReceiptSearchAcType() throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List checkList = em.createNativeQuery("select AcctCode from accounttypemaster where acctNature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')").getResultList();
            ut.commit();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List tableData(Float receiptNo, String acType) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select seqno, VoucherNo,acno,PrinAmt,ROI,DATE_FORMAT(TD_MadeDt, '%d/%m/%Y') AS TD_MadeDt,"
                    + "DATE_FORMAT(FDDt, '%d/%m/%Y') AS fdDt,DATE_FORMAT(MatDt, '%d/%m/%Y') AS MatDt,IntOpt,inttoacno,period from td_vouchmst "
                    + "where status='A' and receiptno = '" + receiptNo + "' and substring(acno,3,2)='" + acType + "'");
            tableResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableResult;
    }
    /*End of TD Receipt Search*/

    /*Start of TD Enquiry*/
    public String currentDate(String selectDuration, String duration) throws ApplicationException {
        try {
            String matDate = "";
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String businessDt = tempCurrent.get(0).toString();
            if (selectDuration.equals("Years")) {
                matDate = CbsUtil.yearAdd(businessDt, Integer.parseInt(duration));
            }
            if (selectDuration.equals("Months")) {
                matDate = CbsUtil.monthAdd(businessDt, Integer.parseInt(duration));
            }
            if (selectDuration.equals("Days")) {
                matDate = CbsUtil.dateAdd(businessDt, Integer.parseInt(duration));
            }
            return businessDt + ": " + matDate;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String tdEnquiryCalculation(String reviewByAmount, String enterAmount, String selectDuration,
            String strDuration, String interestOption, String rateOfInterest, String brncode)
            throws ApplicationException {
        String matDate = null;
        String fdDate;
        Float n = 0.0f;
        double amt = 0.0f;
        try {
            if (((interestOption.equals("Monthly")) || (interestOption.equals("Quarterly")) || (interestOption.equals("Cumulative"))) && (selectDuration.equals("Days"))) {
                return "Please select the duration in Months or Years for this Interest Option";
            } else {
                if (selectDuration.equals("Years")) {
                    matDate = CbsUtil.yearAdd(ymd.format(new Date()), Integer.parseInt(strDuration));
                } else if (selectDuration.equals("Months")) {
                    matDate = CbsUtil.monthAdd(ymd.format(new Date()), Integer.parseInt(strDuration));
                } else if (selectDuration.equals("Days")) {
                    matDate = CbsUtil.dateAdd(ymd.format(new Date()), Integer.parseInt(strDuration));
                }

                if (reviewByAmount.equals("Review By Amount")) {
                    fdDate = ymd.format(new Date());
                    String amt1 = orgFDInterest(interestOption, Float.parseFloat(rateOfInterest), matDate, fdDate, Double.parseDouble(enterAmount), strDuration, brncode);
                    amt = Double.parseDouble(amt1) - Double.parseDouble(enterAmount);
                } else {

                    double tdamt = Double.parseDouble(enterAmount);
                    Float rot = Float.parseFloat(rateOfInterest);

                    if (interestOption.equals("Cumulative")) {
                        if (selectDuration.equals("Months")) {
                            n = (Float.parseFloat(strDuration) / 3);
                        } else if (selectDuration.equals("Years")) {
                            n = (Float.parseFloat(strDuration) / 4);
                        }
                        amt = tdamt / ((Math.pow((1 + rot / 400), n)) - 1);

                    } else if (interestOption.equals("Quarterly")) {
                        if (selectDuration.equals("Months")) {
                            n = (Float.parseFloat(strDuration) / 3);
                        } else if (selectDuration.equals("Years")) {
                            n = (Float.parseFloat(strDuration) * 4);
                        }
                        amt = ((tdamt * 400) / (rot * n));

                    } else if (interestOption.equals("Monthly")) {
                        if (selectDuration.equals("Months")) {
                            n = Float.parseFloat(strDuration);
                        }

                        if (selectDuration.equals("Years")) {
                            n = Float.parseFloat(strDuration) * 12;
                        }
                        amt = ((tdamt * (1200 + rot)) / (n * rot));
                    } else if (interestOption.equals("Simple")) {
                        if (selectDuration.equals("Days")) {
                            n = Float.parseFloat(strDuration);
                            amt = ((tdamt * 36500) / (rot * n));
                        } else if (selectDuration.equals("Months")) {
                            n = Float.parseFloat(strDuration);
                            amt = ((tdamt * 1200) / (rot * n));
                        } else if (selectDuration.equals("Years")) {
                            n = (Float.parseFloat(strDuration) * 12);
                            amt = ((tdamt * 1200) / (rot * n));

                        }
                    } else if (interestOption.equals("Yearly")) {
                        if (selectDuration.equals("Years")) {
                            n = (Float.parseFloat(strDuration));
                        }
                        amt = tdamt / ((Math.pow((1 + rot / 100), n)) - 1);
                    }
                }
            }
            return Double.toString(Math.round(amt));
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
    /*End of TD Enquiry*/

    /*Start of TD Listing*/
    public List getTableDetails(String actNo) throws ApplicationException {
        String acc = actNo;
        List result = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select voucherno,roi,DATE_FORMAT(TD_MadeDt, '%d/%m/%Y'),DATE_FORMAT(FDDt, '%d/%m/%Y'),"
                    + "DATE_FORMAT(MatDt, '%d/%m/%Y'),prinamt,intopt,receiptno,inttoacno,period,coalesce(ofacno,'') as ofacno,seqno,status,"
                    + "ifnull(finalamt,0)as finalamt,ifnull(netroi,0)as netroi from td_vouchmst where  Acno ='" + acc + "' order by voucherno");
            result = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public String getTotalIntPaid(String acNo, float vchNo) throws ApplicationException {
        try {
            double totInt = 0;
            Query selectQuery = em.createNativeQuery("select ifnull(sum(interest),0) from td_interesthistory "
                    + "where acno = '" + acNo + "' and voucherno =" + vchNo);
            List result = selectQuery.getResultList();
            if (result.size() > 0) {
                Vector seqNoVec = (Vector) result.get(0);
                totInt = Double.parseDouble(seqNoVec.get(0).toString());
            }
            double tds = 0;
            Query tdsQuery = em.createNativeQuery("select ifnull(sum(tds),0) from tdshistory where acno = '" + acNo + "' and voucherno =" + vchNo);
            List resultList = tdsQuery.getResultList();
            if (resultList.size() > 0) {
                Vector seqNoVec = (Vector) resultList.get(0);
                tds = Double.parseDouble(seqNoVec.get(0).toString());
            }
            return String.valueOf(totInt - tds);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getAcctDetail(String accNo) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("Select am.acno,am.accstatus,am.custname,ifnull(am.Jtname1,''),ifnull(am.jtname2,''),"
                    + "ifnull(cb.Description,'') from td_accountmaster am,codebook cb where acno='" + accNo + "' and am.OperMode=cb.code "
                    + "and cb.groupcode = 4").getResultList();
            if (resultList.isEmpty()) {
                throw new ApplicationException("Account does not exist");
            }
            Vector v1 = (Vector) resultList.get(0);
            String result = v1.get(0).toString() + ":" + v1.get(1).toString() + ":" + v1.get(2).toString() + ":" + v1.get(3).toString() + ":" + v1.get(4).toString();
            result = result + ":" + v1.get(5).toString();

            resultList = em.createNativeQuery("select balance from td_reconbalan where acno='" + accNo + "' ").getResultList();
            Vector v2 = (Vector) resultList.get(0);
            result = result + ":" + v2.get(0).toString();

            resultList = em.createNativeQuery("select ifnull(sum(tds),0) as tds from tdshistory where acno='" + accNo + "' ").getResultList();
            Vector v3 = (Vector) resultList.get(0);
            result = result + ":" + v3.get(0).toString();
            resultList = em.createNativeQuery("select ifnull(sum(a.interest),0) as interest from td_interesthistory a,td_vouchmst  "
                    + "b where b.acno='" + accNo + "' and b.acno=a.acno  and a.voucherno=b.voucherno and b.status='A' ").getResultList();
            Vector v4 = (Vector) resultList.get(0);
            result = result + ":" + v4.get(0).toString();

            resultList = em.createNativeQuery("select ifnull(sum(prinamt),0) as prinamt from td_vouchmst where acno='" + accNo + "' and status='A' ").getResultList();
            Vector v5 = (Vector) resultList.get(0);
            result = result + ":" + v5.get(0).toString();
            return result;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    public List<AccountDetail> getDetailOnClick(String actNo, String accType) throws ApplicationException {
        List<AccountDetail> accDtlList = new ArrayList<AccountDetail>();
        AccountDetail accountDetail;
        List result1 = new ArrayList();
        List result2 = new ArrayList();
        try {
            result1 = em.createNativeQuery("select a.acno,a.voucherno,dramt,cramt, date_format(a.Dt,'%d/%m/%Y'),"
                    + "coalesce(date_format(a.FDDt,'%d/%m/%Y'),'')as fddt,details from td_recon a,td_vouchmst b where a.acno='"
                    + actNo + "' and substring(a.acno,3,2)='" + accType + "' and a.voucherno=b.voucherno and a.acno=b.acno "
                    + "and b.status='A' and a.voucherno is not null  order by a.voucherno").getResultList();

            result2 = em.createNativeQuery("select sum(cramt) as totCramt,sum(dramt) as totDramt,a.voucherno "
                    + "from td_recon a,td_vouchmst b where a.acno='" + actNo + "'  and substring(a.acno,3,2)='" + accType + "' "
                    + "and a.voucherno=b.voucherno and a.acno=b.acno and b.status='A' and a.voucherno is not null "
                    + "group by a.voucherno order by a.voucherno").getResultList();
            if (!result2.isEmpty()) {
                for (int j = 0; j < result2.size(); j++) {
                    Vector v1 = (Vector) result2.get(j);
                    Double vouchNumJ = Double.parseDouble(v1.get(2).toString());
                    for (int k = 0; k < result1.size(); k++) {
                        Vector v2 = (Vector) result1.get(k);
                        Double vouchNumK = Double.parseDouble(v2.get(1).toString());
                        if (vouchNumJ.compareTo(vouchNumK) == 0) {
                            accountDetail = new AccountDetail();

                            accountDetail.setAccountNo(v2.get(0).toString());
                            accountDetail.setVoucherNo(v2.get(1).toString());

                            accountDetail.setDrAmt(Double.parseDouble(v2.get(2).toString()));
                            accountDetail.setCrAmt(Double.parseDouble(v2.get(3).toString()));

                            accountDetail.setIsuDate(v2.get(4).toString());
                            accountDetail.setFdDate(v2.get(5).toString());

                            accountDetail.setDetails(v2.get(6).toString());
                            accDtlList.add(accountDetail);
                        }
                    }
                    accountDetail = new AccountDetail();
                    accountDetail.setVoucherNo("TOTAL:-");
                    accountDetail.setDrAmt(Double.parseDouble(v1.get(1).toString()));
                    accountDetail.setCrAmt(Double.parseDouble(v1.get(0).toString()));
                    accDtlList.add(accountDetail);
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return accDtlList;
    }

    public List getSumOfDrCrOnDetailClick(String actNo, String accType) throws ApplicationException {
        List result = new ArrayList();
        try {
            result = em.createNativeQuery("select sum(cramt) as totCramt,sum(dramt) as totDramt,a.voucherno "
                    + "from td_recon a,td_vouchmst b where a.acno='" + actNo + "'  and substring(a.acno,3,2)='" + accType + "' "
                    + "and a.voucherno=b.voucherno and a.acno=b.acno and b.status='A' and a.voucherno is not null "
                    + "group by a.voucherno order by a.voucherno").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return result;
    }

    public List selectFromTdRecon(float voucherNo, String accNo) {
        String s = String.valueOf(voucherNo);
        s = s.substring(0, s.indexOf("."));
        List list = em.createNativeQuery("select distinct(IntFlag) from td_recon where voucherno =" + Float.parseFloat(s) + " and td_recon.acno ='" + accNo + "'  and IntFlag='I'").getResultList();
        return list;
    }

    public List selectFromTdVouchMast(float voucherNo, String accNo) {
        String s = String.valueOf(voucherNo);
        s = s.substring(0, s.indexOf("."));
        List list = em.createNativeQuery("select ifnull(Years,0),ifnull(months,0),ifnull(days,0) from td_vouchmst where voucherno =" + Float.parseFloat(s) + " and acno ='" + accNo + "' ").getResultList();
        return list;
    }

    public List selectFromSecurityInfo(String userName) {
        List list = em.createNativeQuery("Select * From securityinfo where userid= '" + userName + "' and levelid=1").getResultList();
        return list;
    }

    public List selectFromParameterInforeport() {
        List list = em.createNativeQuery("Select CODE From parameterinfo_report Where upper(Ltrim(RTrim(ReportName)))='TDMODIFICATION' AND CODE=1").getResultList();
        return list;
    }

    public String updateTdVouchMaster(float roi, String matDate, String period, String years, String months, String days, String accNo, float voucherNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String s = String.valueOf(voucherNo);
        s = s.substring(0, s.indexOf("."));
        try {
            ut.begin();
            Query update = em.createNativeQuery("update td_vouchmst set roi='" + roi + "',matDt='" + ymd.format(dmy.parse(matDate)) + "',period='" + period + "',Years='" + years + "',Months='" + months + "',DAYS='" + days + "' where acno = '" + accNo + "' and voucherno= " + Float.parseFloat(s) + "");
            int updateResult = update.executeUpdate();
            if (updateResult <= 0) {
                throw new ApplicationException("Error in updation.");
            }
            ut.commit();
            return "Updation Successfull.";

        } catch (Exception e) {
            e.printStackTrace();
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    /*End of TD Listing*/
    public List getBranchCodeList(String orgnCode) throws ApplicationException {
        List result = new ArrayList();
        try {
            List list1 = em.createNativeQuery("select alphacode from branchmaster where brncode = cast('" + orgnCode + "' as unsigned)").getResultList();
            Vector v1 = (Vector) list1.get(0);
            String aCode = v1.get(0).toString();
            if (aCode.equalsIgnoreCase("HO")) {
                result = em.createNativeQuery("SELECT 'A','ALL' union select cast(brncode as char(2)),alphacode from branchmaster").getResultList();
            } else {
                result = em.createNativeQuery("select cast(brncode as char),alphacode from branchmaster where brncode = cast('" + orgnCode + "' as unsigned)").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List<TdPeriodMaturityPojo> gettdPeriodMaturityList(String tdDate, String orgnCode, String acctNature) throws ApplicationException {
        List<TdPeriodMaturityPojo> repList = new ArrayList<TdPeriodMaturityPojo>();
        try {
            List custtempList, tdMstList = null;
            if (orgnCode.equalsIgnoreCase("A")) {
                custtempList = em.createNativeQuery("select acno,voucherno,prinamt,roi,intopt,DATE_FORMAT(fddt,'%Y%m%d'),DATE_FORMAT(matdt,'%Y%m%d'),"
                        + "receiptno,period,DATE_FORMAT(td_madeDt,'%Y%m%d') from td_vouchmst where td_madeDt <= '" + tdDate + "' and ((cldt is null) "
                        + "or (cldt='') or (cldt>'" + tdDate + "')) and matdt< '" + tdDate + "'and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature = '" + acctNature + "' ) order by acno,matdt").getResultList();
            } else {
                orgnCode = CbsUtil.lPadding(2, Integer.parseInt(orgnCode));
                custtempList = em.createNativeQuery("select acno,voucherno,prinamt,roi,intopt,DATE_FORMAT(fddt,'%Y%m%d'),DATE_FORMAT(matdt,'%Y%m%d'),"
                        + "receiptno,period,DATE_FORMAT(td_madeDt,'%Y%m%d') from td_vouchmst where td_madeDt <= '" + tdDate + "' and ((cldt is null) "
                        + "or (cldt='') or (cldt>'" + tdDate + "')) and matdt<='" + tdDate + "' and substring(acno,1,2) = '" + orgnCode
                        + "' and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature = '" + acctNature + "' )order by matdt").getResultList();
            }

            if (!custtempList.isEmpty()) {
                for (int i = 0; i < custtempList.size(); i++) {
                    TdPeriodMaturityPojo pojo = new TdPeriodMaturityPojo();
                    Vector vector = (Vector) custtempList.get(i);

                    String accno = vector.get(0).toString();
                    float vchno = new Float(vector.get(1).toString());
                    double pramt = Double.parseDouble(vector.get(2).toString());
                    float roi = new Float(vector.get(3).toString());
                    String intopt = vector.get(4).toString();
                    String fddt = vector.get(5).toString();
                    String matdt = vector.get(6).toString();
                    String receiptno = vector.get(7).toString();
                    String period = vector.get(8).toString();
                    String td_madeDt = vector.get(9).toString();

                    double matAmt = 0;
                    matAmt = Double.parseDouble(orgFDInterest(intopt, roi, fddt, matdt, pramt, period, orgnCode));
                    tdMstList = em.createNativeQuery("select custname from td_accountmaster where acno='" + accno + "'").getResultList();
                    if (tdMstList.size() > 0) {
                        Vector vector1 = (Vector) tdMstList.get(0);
                        String custname = vector1.get(0).toString();

                        String lienSt = "";
                        String lienAcno = "";

                        List lienLst = em.createNativeQuery("select lienacno,Acno from loansecurity where lienacno ='" + accno + "'").getResultList();
                        if (lienLst.isEmpty()) {
                            lienSt = "No";
                            lienAcno = "";
                        } else {
                            List lienLst1 = em.createNativeQuery("select lienacno,Acno from loansecurity where lienacno ='" + accno + "' and (expiryDate is null or expirydate > '" + tdDate + "')").getResultList();
                            if (lienLst1.isEmpty()) {
                                lienSt = "No";
                                lienAcno = "";
                            } else {
                                lienSt = "Yes";
                                Vector vtr = (Vector) lienLst1.get(0);
                                lienAcno = vtr.get(1).toString();
                            }
                        }

                        if (lienSt.equalsIgnoreCase("No")) {
                            List tdLienList = em.createNativeQuery("select lienstatus from td_lien_update where txndate in(select  max(txndate) from td_lien_update "
                                    + "where acno = '" + accno + "' and receiptno = " + receiptno + " and date_format(txndate,'%Y%m%d') <= '" + tdDate + "') "
                                    + "and receiptno = " + receiptno + " and lienstatus = 'Y'").getResultList();
                            System.out.println(tdLienList.size());
                            if (tdLienList.isEmpty()) {
                                lienSt = "No";
                            } else {
                                lienSt = "Yes";
                            }
                        }

                        pojo.setAccountNumber(accno);
                        pojo.setVoucherNo(vchno);
                        pojo.setReceiptNo(receiptno);
                        pojo.setRoi(roi);
                        pojo.setTdMadeDt(td_madeDt.substring(6) + "/" + td_madeDt.substring(4, 6) + "/" + td_madeDt.substring(0, 4));
                        pojo.setFdDt(fddt.substring(6) + "/" + fddt.substring(4, 6) + "/" + fddt.substring(0, 4));
                        pojo.setMatDt(matdt.substring(6) + "/" + matdt.substring(4, 6) + "/" + matdt.substring(0, 4));
                        pojo.setPrinAmt(pramt);
                        pojo.setIntOpt(intopt);
                        pojo.setPeriod(period);
                        pojo.setCustName(custname);
                        pojo.setIntAtMat(matAmt);
                        pojo.setTotTdAmt(pramt + matAmt);
                        pojo.setStatus(lienSt);
                        pojo.setLienAcno(lienAcno);

                        repList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return repList;
    }

    public List<RdInterestDTO> getRdperiodmaturityList(String matDtAsOn, String orgnCode) throws ApplicationException {
        List<RdInterestDTO> repList = new ArrayList<RdInterestDTO>();
        try {
            int n = 1;
            List intialDatalist = null;
            if (orgnCode.equalsIgnoreCase("A")) {
                intialDatalist = em.createNativeQuery("Select AM.Acno, AM.CustName, AM.IntDeposit,AM.OpeningDt, AM.RdInstal,date_format(AM.Rdmatdate,'%d/%m/%Y') From accountmaster AM "
                        + "Where (AM.ClosingDate is null or AM.ClosingDate='' or AM.ClosingDate>='" + matDtAsOn + "') and  AM.Rdmatdate<= '" + matDtAsOn + "' and "
                        + "accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.RECURRING_AC + "') and AccStatus<>15 group by AM.Acno, AM.CustName, "
                        + "AM.IntDeposit,AM.OpeningDt,AM.RdInstal order by AM.acno").getResultList();
            } else {
                orgnCode = CbsUtil.lPadding(2, Integer.parseInt(orgnCode));
                intialDatalist = em.createNativeQuery("Select AM.Acno, AM.CustName, AM.IntDeposit,AM.OpeningDt, AM.RdInstal,date_format(AM.Rdmatdate,'%d/%m/%Y') From accountmaster AM "
                        + "Where (AM.ClosingDate is null or AM.ClosingDate='' or AM.ClosingDate>='" + matDtAsOn + "') and  AM.Rdmatdate<= '" + matDtAsOn + "' and "
                        + "accttype in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.RECURRING_AC + "') and AccStatus<>15  and substring( AM.Acno,1,2) ='" + orgnCode + "' group by AM.Acno, AM.CustName, "
                        + "AM.IntDeposit,AM.OpeningDt,AM.RdInstal order by AM.acno").getResultList();
            }

            if (!intialDatalist.isEmpty()) {
                for (int i = 0; i < intialDatalist.size(); i++) {
                    RdInterestDTO pojo = new RdInterestDTO();
                    int srNo = n++;
                    Vector vector = (Vector) intialDatalist.get(i);
                    String acNo = vector.get(0).toString().trim();
                    String CustmerName = vector.get(1).toString().trim();
                    double roi = Double.valueOf(vector.get(2).toString().trim());
                    String openingDt = vector.get(3).toString().trim();
                    double rdInstall = Double.valueOf(vector.get(4).toString());
                    String matDate = vector.get(5).toString().trim();
                    double maturityAmt = 0;
                    double totalint = 0;
                    String period = "";
                    List maturity = rdIntCalFacade.rdIntCal(openingDt, ymd.format(dmy.parse(matDate)),
                            Float.parseFloat(vector.get(2).toString().trim()), Float.parseFloat(vector.get(4).toString().trim()));
                    if (!maturity.isEmpty()) {
                        maturityAmt = Double.parseDouble((maturity.get(0).toString()));
                        maturityAmt = Math.round(maturityAmt);
                        totalint = Double.parseDouble(maturity.get(1).toString());
                        period = maturity.get(2).toString().trim();
                    }
                    String lienSt = "";
                    String lienAcno = "";
                    List lienLst = em.createNativeQuery("select lienacno from loansecurity where lienacno ='" + acNo + "'").getResultList();
                    if (lienLst.isEmpty()) {
                        lienSt = "No";
                        lienAcno = "";
                    } else {
                        List lienLst1 = em.createNativeQuery("select lienacno from loansecurity where lienacno ='" + acNo + "' and (expiryDate is null or expirydate > '" + matDtAsOn + "')").getResultList();
                        if (lienLst1.isEmpty()) {
                            lienSt = "No";
                            lienAcno = "";
                        } else {
                            lienSt = "Yes";
                            Vector vtr = (Vector) lienLst1.get(0);
                            lienAcno = vtr.get(0).toString();
                        }
                    }

                    if (lienSt.equalsIgnoreCase("No")) {
                        List tdLienList = em.createNativeQuery("select lienstatus from td_lien_update where txndate in(select  max(txndate) from td_lien_update "
                                + "where acno = '" + acNo + "' and date_format(txndate,'%Y%m%d') <= '" + matDtAsOn + "') and lienstatus = 'Y'").getResultList();
                        System.out.println(tdLienList.size());
                        if (tdLienList.isEmpty()) {
                            lienSt = "No";
                        } else {
                            lienSt = "Yes";
                        }
                    }
                    pojo.setSrNo(srNo);
                    pojo.setAcNo(acNo);
                    pojo.setCustName(CustmerName);
                    pojo.setOpeningDt(dmy.format(ymd.parse(openingDt)));
                    pojo.setInterest(CbsUtil.round(totalint, 0));
                    pojo.setInstallment(rdInstall);
                    pojo.setBalance(0);
                    pojo.setRdMatDt(matDate);
                    pojo.setRoi(roi);
                    pojo.setLienStatus(lienSt);
                    pojo.setLienAcno(lienAcno);
                    pojo.setPeriod(period);
                    pojo.setMonthIntrest(0);
                    pojo.setTotMatAmt(maturityAmt);

                    repList.add(pojo);

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return repList;
    }

    public String getProductCode(String acno) throws ApplicationException {
        try {
            String actCode = ftsPostMgmtRepote.getAccountCode(acno);
            List list = em.createNativeQuery("select productCode from accounttypemaster where acctcode='" + actCode + "'").getResultList();
            Vector v1 = (Vector) list.get(0);
            return v1.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List getAllBranchCodeList(String orgnCode) throws ApplicationException {
        List result = new ArrayList();
        try {
            List list1 = em.createNativeQuery("select alphacode from branchmaster where brncode = cast('" + orgnCode + "' as unsigned)").getResultList();
            Vector v1 = (Vector) list1.get(0);
            String aCode = v1.get(0).toString();
            if (aCode.equalsIgnoreCase("HO")) {
                result = em.createNativeQuery("select cast(brncode as char),alphacode from branchmaster order by brncode desc").getResultList();
            } else {
                result = em.createNativeQuery("select cast(brncode as char),alphacode from branchmaster where brncode = cast('" + orgnCode + "' as unsigned)").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List cbsIntroInfo(String acNo, String acctNature) throws ApplicationException {
        try {
            List tdMstList = new ArrayList();
            if ((acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (acctNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                tdMstList = em.createNativeQuery("select acno,custname,accstatus from td_accountmaster where acno='" + acNo + "'").getResultList();
            } else {
                tdMstList = em.createNativeQuery("select acno,custname,accstatus from accountmaster where acno='" + acNo + "'").getResultList();
            }
            if (tdMstList.isEmpty()) {
                throw new ApplicationException("Account does not exist");
            }
            Vector statusVec = (Vector) tdMstList.get(0);
            int accStatus = Integer.parseInt(statusVec.get(2).toString());
            if (accStatus == 9) {
                throw new ApplicationException("Account has been Closed");
            }
            if (accStatus == 2) {
                throw new ApplicationException("Account has been Inoperative");
            }
            if (accStatus == 15) {
                throw new ApplicationException("Account has been DEAF");
            }
            return tdMstList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getUnAuthAcNo(String orgBrCode, String curDt) throws ApplicationException {
        try {
            String query = "select distinct acno from td_vouchmst_auth where auth='N' and substring(acno,1,2) = '" + orgBrCode + "' and td_madedt='" + curDt + "'";
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no pending authorization");
            }
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getUnAuthTxnId(String acno, String curDt) throws ApplicationException {
        try {
            String query = "select TxnId from td_vouchmst_auth where auth='N' and acno = '" + acno + "' and td_madedt='" + curDt + "'";
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no pending authorization");
            }
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getRtDetails(String acNo, float txnNo) throws ApplicationException {
        try {
            String query = "select prinamt,date_format(fddt,'%d/%m/%Y'),intopt,inttoacno,autorenew,years,months,days,roi,date_format(matdt,'%d/%m/%Y'),"
                    + "bookseries,EnterBy,ifnull(auto_pay,'N'),ifnull(auto_paid_acno,'') from td_vouchmst_auth where auth='N' and acno = '" + acNo + "' and txnid = " + txnNo;
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no pending authorization");
            }
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String modifyReceiptDetails(String acctNature, String function, String acno, String txnNo, String intOpt, String inttoAcno, String autoRenew,
            int day, int month, int year, float roi, String matDt, String strPd, String userId, String brCode, String autoPay, String paidAcno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            String message = "";
            ut.begin();
            String query = "select  enterby,prinamt from td_vouchmst_auth where auth='N' and acno = '" + acno + "' and txnid = " + txnNo;
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("This receipt has been already authorized");
            }
            Vector vect = (Vector) dataList.get(0);
            String enterBy = vect.get(0).toString();
            double prinAmt = Double.parseDouble(vect.get(1).toString());
//            if (!enterBy.equals(userId)) {
//                throw new ApplicationException("You can not modify or delete this Receipt because you did not made this Receipt");
//            }
            if (function.equalsIgnoreCase("M")) {
                query = "update td_vouchmst_auth set intopt = '" + intOpt + "', inttoacno = '" + inttoAcno + "',autorenew = '" + autoRenew + "', days = "
                        + day + ",months = " + month + ", years = " + year + ",roi = " + roi + ", matdt = '" + matDt + "',"
                        + "auto_pay = '" + autoPay + "',auto_paid_acno='" + paidAcno + "' where "
                        + "auth='N' and acno = '" + acno + "' and txnid = " + txnNo;
                int result = em.createNativeQuery(query).executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem in Receipt Details updation");
                }
                message = "Receipt details successfully updated";
            } else {
                query = "delete from td_vouchmst_auth where auth='N' and acno = '" + acno + "' and txnid = " + txnNo;
                int result = em.createNativeQuery(query).executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem in Receipt Details deletion");
                }
                String msg = ftsPostMgmtRepote.updateBalance(acctNature, acno, prinAmt, 0, "Y", "Y");
                if (!msg.equalsIgnoreCase("TRUE")) {
                    throw new ApplicationException("Problem in Balance updation");
                }
                message = "Receipt details successfully deleted";
            }
            ut.commit();
            return message;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public boolean isStaffSeniorCitizen(String acno, int dblBenifitAge, String wefDt) throws ApplicationException {
        try {
            String query = "select date_format(cm.DateOfBirth,'%Y%m%d') from customerid ci, cbs_customer_master_detail cm where "
                    + "ci.acno='" + acno + "' and ci.CustId = cm.customerid";
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Data does not exist in Customer Master");
            }
            Vector vect = (Vector) dataList.get(0);
            String dob = vect.get(0).toString();
            long stAge = CbsUtil.yearDiff(ymd.parse(dob), ymd.parse(wefDt));
            if (stAge >= dblBenifitAge) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getValidateBookSeries(String acNat, String acType, String recpt, String brCode, String bookSr) throws ApplicationException {
        String msg = "true";
        try {
            int recCnt = 0;
            if (recpt.equalsIgnoreCase("C")) {
                List tdReceiptList = em.createNativeQuery("SELECT count(ReceiptNo) FROM td_receiptissue WHERE "
                        + " Scheme ='FD' AND Status = 'F' AND series = '" + bookSr + "' And brncode ='" + brCode + "'").getResultList();
                if (tdReceiptList.size() > 0) {
                    Vector tdReceiptVec = (Vector) tdReceiptList.get(0);
                    recCnt = Integer.parseInt(tdReceiptVec.get(0).toString());
                }
            } else if (recpt.equalsIgnoreCase("N")) {
                List tdReceiptList = em.createNativeQuery(" SELECT count(ReceiptNo) FROM td_receiptissue WHERE Scheme ='" + acNat + "' "
                        + "AND Status = 'F' AND series = '" + bookSr + "' And brncode = '" + brCode + "'").getResultList();
                if (tdReceiptList.size() > 0) {
                    Vector tdReceiptVec = (Vector) tdReceiptList.get(0);
                    recCnt = Integer.parseInt(tdReceiptVec.get(0).toString());
                }

            } else if (recpt.equalsIgnoreCase("T")) {
                List tdReceiptList = em.createNativeQuery("SELECT count(ReceiptNo) FROM td_receiptissue WHERE Scheme ='" + acType + "' "
                        + "AND Status = 'F' AND series='" + bookSr + "' And brncode = '" + brCode + "'").getResultList();
                if (tdReceiptList.size() > 0) {
                    Vector tdReceiptVec = (Vector) tdReceiptList.get(0);
                    recCnt = Integer.parseInt(tdReceiptVec.get(0).toString());
                }
            } else {
                List tdReceiptList = em.createNativeQuery("SELECT count(ReceiptNo) FROM td_receiptissue WHERE Scheme ='" + acNat + "' "
                        + "AND Status = 'F' AND series='" + bookSr + "' And brncode = '" + brCode + "'").getResultList();
                if (tdReceiptList.size() > 0) {
                    Vector tdReceiptVec = (Vector) tdReceiptList.get(0);
                    recCnt = Integer.parseInt(tdReceiptVec.get(0).toString());
                }
            }

            int totInt = 0;
            Query selectQuery = em.createNativeQuery("select count(*) from td_vouchmst_auth where auth = 'N' and bookseries = '" + bookSr + "' and substring(acno,1,2) =" + brCode);
            List result = selectQuery.getResultList();
            if (result.size() > 0) {
                Vector seqNoVec = (Vector) result.get(0);
                totInt = Integer.parseInt(seqNoVec.get(0).toString());
            }

            if (totInt >= recCnt) {
                msg = "false";
            }
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String orgFDInterestGoi(String InterestOption, float roInt, String fdDate, String matDate,
            double amt, String prd, String brCode) throws ApplicationException {
        try {
            double msngTotalInt = 0.0d;
            if ((InterestOption.equalsIgnoreCase("M")) || (InterestOption.equalsIgnoreCase("Monthly"))) {
                long dtDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
                if (dtDiff >= 30) {
//                    Long aQuater = dtDiff / 30;
//
//                    String cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(aQuater.toString()));
//
//                    long newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));

                    Period pd = getFdPeriod(ymd.parse(fdDate), ymd.parse(matDate));

                    int aQuater = pd.getMonths() + pd.getYears() * 12;
                    int newDiff = pd.getDays() + pd.getWeeks() * 7;

                    double cummInt = amt - (amt * (1 / (1 + (roInt / 1200))));

                    cummInt = cummInt * aQuater;
                    if (newDiff > 0) {
                        msngTotalInt = amt * roInt * ((double) newDiff / 36000);
                        msngTotalInt = msngTotalInt + cummInt;
                    } else {
                        msngTotalInt = msngTotalInt + cummInt;
                    }
                } else {
                    msngTotalInt = (amt * roInt * dtDiff) / 36000;
                }
            }
            if ((InterestOption.equalsIgnoreCase("C")) || (InterestOption.equalsIgnoreCase("Cumulative"))) {
                long dtDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
                if (dtDiff >= 90) {
                    String period = getYMDPeriod(prd);
                    String[] values = period.split(":");

                    int mon = Integer.parseInt(values[1]);
                    int yrs = Integer.parseInt(values[0]);

                    int daysInQuarter = 91;
                    List bnkList = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
                    Vector eleBnkCd = (Vector) bnkList.get(0);
                    String bnkCode = eleBnkCd.get(0).toString();

                    if (bnkCode.equalsIgnoreCase("nccb")) {
                        List liveList = em.createNativeQuery("select ifnull(FinYearBegin,'" + fdDate + "') from parameterinfo where brncode = cast(substring('" + brCode + "',1,2) as unsigned) ").getResultList();
                        Vector liveDt = (Vector) liveList.get(0);
                        String lDt = liveDt.get(0).toString();

                        int lenDt = fdDate.length();
                        long dDiff = 0;
                        if (lenDt == 10) {
                            dDiff = CbsUtil.dayDiff(dmy.parse(fdDate), ymd.parse(lDt));
                        } else {
                            dDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(lDt));
                        }
                        if (dDiff <= 0) {
                            daysInQuarter = 91;
                        } else {
                            daysInQuarter = 90;
                        }
                    }
                    Long activeQuater = (dtDiff / daysInQuarter);
                    long newDiff = 0;
                    if (yrs == 0 && mon == 0) {
                        newDiff = dtDiff - (activeQuater * daysInQuarter);
                    } else {
                        String cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(activeQuater.toString()) * 3);
                        newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));
                    }
                    double ab = 36000.0d;
                    double cummIntRate = 1.0d;
                    for (int i = 1; i <= activeQuater; i++) {
                        cummIntRate = (1 + (roInt / 400.0)) * cummIntRate;
                    }
                    double cummInt = (amt * cummIntRate) - amt;
                    double newPrnAmt = amt + cummInt;
                    if (newDiff > 0) {
                        msngTotalInt = newPrnAmt * roInt * ((double) newDiff / ab);
                        msngTotalInt = msngTotalInt + cummInt;
                    } else {
                        msngTotalInt = msngTotalInt + cummInt;
                    }
                } else {
                    msngTotalInt = (amt * roInt * dtDiff) / 36000;
                }
            }

            if ((InterestOption.equalsIgnoreCase("Q")) || (InterestOption.equalsIgnoreCase("Quarterly"))) {
                long dtDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
                if (dtDiff >= 90) {
                    Long aQuater = dtDiff / 90;
                    String cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(aQuater.toString()) * 3);

                    long newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));

                    double cummInt = (amt * (roInt / 400));
                    cummInt = cummInt * aQuater;
                    if (newDiff > 0) {
                        msngTotalInt = (amt * roInt * ((double) newDiff / 36000));
                        msngTotalInt = msngTotalInt + cummInt;
                    } else {
                        msngTotalInt = msngTotalInt + cummInt;
                    }
                } else {
                    msngTotalInt = (amt * roInt * dtDiff / 36000);
                }
            }

            if ((InterestOption.equalsIgnoreCase("Y")) || (InterestOption.equalsIgnoreCase("Yearly"))) {

                List bnkList = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
                Vector eleBnkCd = (Vector) bnkList.get(0);
                String bnkCode = eleBnkCd.get(0).toString();

                String period = getYMDPeriod(prd);
                String[] values = period.split(":");

                int dd = Integer.parseInt(values[2]);
                int mon = Integer.parseInt(values[1]);
                int yrs = Integer.parseInt(values[0]);

                if (bnkCode.equalsIgnoreCase("kccl") && roInt == 9.05f && dd == 0 && mon == 96 && yrs == 0) {
                    msngTotalInt = amt;
                } else {
                    // New Code Added For Simple Yearly Option 
                    double TmpSIntTot = 0.0d;
                    msngTotalInt = 0;
                    int Months = mon + (yrs * 12);
                    TmpSIntTot = (amt * roInt * Months / 1200);
                    msngTotalInt = msngTotalInt + TmpSIntTot;
                    TmpSIntTot = (amt * roInt * dd / 36000);
                    msngTotalInt = msngTotalInt + TmpSIntTot;
                }
            }

            double TmpSIntTot = 0.0d;
            if ((InterestOption.equalsIgnoreCase("S")) || (InterestOption.equalsIgnoreCase("Simple"))) {
                msngTotalInt = 0;
                String period = getYMDPeriod(prd);
                String[] values = period.split(":");

                int mon = Integer.parseInt(values[1]);
                int day = Integer.parseInt(values[2]);
                int yrs = Integer.parseInt(values[0]);

                int Months = mon + (yrs * 12);
                TmpSIntTot = (amt * roInt * Months / 1200);
                msngTotalInt = msngTotalInt + TmpSIntTot;
                TmpSIntTot = (amt * roInt * day / 36000);
                msngTotalInt = msngTotalInt + TmpSIntTot;
            }
            return String.valueOf(Math.round(msngTotalInt));
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public Period getFdPeriod(Date dateFrom, Date dateTo) throws ApplicationException {
        try {
            DateTime dateTimeFrom = new DateTime(dateFrom);
            DateTime dateTimeTo = new DateTime(dateTo);

            Period period = new Period(dateTimeFrom, dateTimeTo);

            return period;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
    
    public String orgFDInterestNew(String InterestOption, float roInt, String fdDate, String matDate,
            double amt, String prd, String brCode, String fdDateCrDate) throws ApplicationException {
        try {
            double msngTotalInt = 0.0d;
            if ((InterestOption.equalsIgnoreCase("M")) || (InterestOption.equalsIgnoreCase("Monthly"))) {

                double ab = 36500.0d;
                List bnkList = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
                Vector eleBnkCd = (Vector) bnkList.get(0);
                String bnkCode = eleBnkCd.get(0).toString();
                long dtDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
                if (dtDiff >= 30) {
                    if (bnkCode.equalsIgnoreCase("rcbl") || bnkCode.equalsIgnoreCase("citz")) {
                        List liveList = em.createNativeQuery("select ifnull(FinYearBegin,'" + fdDateCrDate + "') from parameterinfo where brncode = cast(substring('" + brCode + "',1,2) as unsigned) ").getResultList();
                        Vector liveDt = (Vector) liveList.get(0);
                        String lDt = liveDt.get(0).toString();

                        int lenDt = fdDate.length();
                        long dDiff = 0;
                        if (lenDt == 10) {
                            dDiff = CbsUtil.dayDiff(dmy.parse(fdDateCrDate), ymd.parse(lDt));
                        } else {
                            dDiff = CbsUtil.dayDiff(ymd.parse(fdDateCrDate), ymd.parse(lDt));
                        }
                        if (dDiff <= 0) {
                            Period pd = getFdPeriod(ymd.parse(fdDate), ymd.parse(matDate));

                            int aQuater = pd.getMonths() + pd.getYears() * 12;
                            int newDiff = pd.getDays() + pd.getWeeks() * 7;

                            double cummInt = amt - (amt * (1 / (1 + (roInt / 1200))));
                            cummInt = cummInt * aQuater;
                            if (newDiff > 0) {
                                msngTotalInt = amt * roInt * ((double) newDiff / ab);
                                msngTotalInt = msngTotalInt + cummInt;
                            } else {
                                msngTotalInt = msngTotalInt + cummInt;
                            }
                        } else {
                            ab = 36000.0d;
                            String period = getYMDPeriod(prd);
                            String[] values = period.split(":");
                            int mon = Integer.parseInt(values[1]);
                            int yrs = Integer.parseInt(values[0]);
                            int dd = Integer.parseInt(values[2]);
                            if (mon != 0 && (dd == 0 && yrs == 0)) {
                                Long aQuater = dtDiff / 30;
                                msngTotalInt = (amt * roInt * (aQuater * 30)) / ab;
                            } else {
                                msngTotalInt = (amt * roInt * dtDiff) / ab;
                            }
                        }
                    } else {
                        Period pd = getFdPeriod(ymd.parse(fdDate), ymd.parse(matDate));

                        int aQuater = pd.getMonths() + pd.getYears() * 12;
                        int newDiff = pd.getDays() + pd.getWeeks() * 7;

                        double cummInt = amt - (amt * (1 / (1 + (roInt / 1200))));
                        cummInt = cummInt * aQuater;
                        if (newDiff > 0) {
                            msngTotalInt = amt * roInt * ((double) newDiff / ab);
                            msngTotalInt = msngTotalInt + cummInt;
                        } else {
                            msngTotalInt = msngTotalInt + cummInt;
                        }
                    }
                } else {
                    msngTotalInt = (amt * roInt * dtDiff) / ab;
                }
            }
            if ((InterestOption.equalsIgnoreCase("C")) || (InterestOption.equalsIgnoreCase("Cumulative"))) {
                long dtDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
                double ab = 36500.0d;
                List bnkList = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
                Vector eleBnkCd = (Vector) bnkList.get(0);
                String bnkCode = eleBnkCd.get(0).toString();
                if (dtDiff >= 90) {
                    String period = getYMDPeriod(prd);
                    String[] values = period.split(":");

                    int mon = Integer.parseInt(values[1]);
                    int yrs = Integer.parseInt(values[0]);
                    int daysInQuarter = 91;
                    long dDiff = 0;
                    if (bnkCode.equalsIgnoreCase("nccb") || bnkCode.equalsIgnoreCase("rcbl") || bnkCode.equalsIgnoreCase("citz")) {
                        List liveList = em.createNativeQuery("select ifnull(FinYearBegin,'" + fdDateCrDate + "') from parameterinfo where brncode = cast(substring('" + brCode + "',1,2) as unsigned) ").getResultList();
                        Vector liveDt = (Vector) liveList.get(0);
                        String lDt = liveDt.get(0).toString();

                        int lenDt = fdDate.length();

                        if (lenDt == 10) {
                            dDiff = CbsUtil.dayDiff(dmy.parse(fdDateCrDate), ymd.parse(lDt));
                        } else {
                            dDiff = CbsUtil.dayDiff(ymd.parse(fdDateCrDate), ymd.parse(lDt));
                        }
                        if (dDiff <= 0) {
                            daysInQuarter = 91;
                        } else {
                            if (bnkCode.equalsIgnoreCase("rcbl") || bnkCode.equalsIgnoreCase("citz")) {
                                ab = 36000.0d;
                            }
                            daysInQuarter = 90;
                        }
                    }
                    Long activeQuater = (dtDiff / daysInQuarter);
                    long newDiff = 0;
                    if (yrs == 0 && mon == 0) {
                        newDiff = dtDiff - (activeQuater * daysInQuarter);
                    } else {
                        String cNewDate;
                        if ((bnkCode.equalsIgnoreCase("rcbl") || bnkCode.equalsIgnoreCase("citz")) && dtDiff > 0) {
                            int dd = Integer.parseInt(values[2]);
                            if (mon != 0 && dd == 0) {
                                if(dDiff>0){
                                    long d = CbsUtil.dayDiff(ymd.parse(fdDateCrDate), ymd.parse("20180418"));
                                    if(d<=0){
                                        newDiff = (mon - (Integer.parseInt(activeQuater.toString()) * 3)) * 30;
                                    }else{
                                        cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(activeQuater.toString()) * 3);
                                        newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));
                                    }                                    
                                }else{
                                    newDiff = (mon - (Integer.parseInt(activeQuater.toString()) * 3)) * 30;
                                }
                            } else {
                                if (dDiff > 0) {
                                    cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(activeQuater.toString()) * 3);
                                } else {
                                    cNewDate = CbsUtil.dateAdd(fdDate, Integer.parseInt(activeQuater.toString()) * daysInQuarter);
                                }
                                newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));
                            }
                        } else {
                            cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(activeQuater.toString()) * 3);
                            newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));
                        }
                    }

                    double cummIntRate = 1.0d;
                    for (int i = 1; i <= activeQuater; i++) {
                        cummIntRate = (1 + (roInt / 400.0)) * cummIntRate;
                    }
                    double cummInt = (amt * cummIntRate) - amt;
                    double newPrnAmt = amt + cummInt;
                    if (newDiff > 0) {
                        msngTotalInt = newPrnAmt * roInt * ((double) newDiff / ab);
                        msngTotalInt = msngTotalInt + cummInt;
                    } else {
                        msngTotalInt = msngTotalInt + cummInt;
                    }
                } else {
                    if (bnkCode.equalsIgnoreCase("nccb") || bnkCode.equalsIgnoreCase("rcbl") || bnkCode.equalsIgnoreCase("citz")) {
                        List liveList = em.createNativeQuery("select ifnull(FinYearBegin,'" + fdDateCrDate + "') from parameterinfo where brncode = cast(substring('" + brCode + "',1,2) as unsigned) ").getResultList();
                        Vector liveDt = (Vector) liveList.get(0);
                        String lDt = liveDt.get(0).toString();

                        int lenDt = fdDate.length();
                        long dDiff = 0;
                        if (lenDt == 10) {
                            dDiff = CbsUtil.dayDiff(dmy.parse(fdDateCrDate), ymd.parse(lDt));
                        } else {
                            dDiff = CbsUtil.dayDiff(ymd.parse(fdDateCrDate), ymd.parse(lDt));
                        }
                        if ((bnkCode.equalsIgnoreCase("rcbl") || bnkCode.equalsIgnoreCase("citz")) && dDiff > 0) {
                            ab = 36000.0d;
                        }
                    }
                    msngTotalInt = (amt * roInt * dtDiff) / ab;
                }
            }

            if ((InterestOption.equalsIgnoreCase("Q")) || (InterestOption.equalsIgnoreCase("Quarterly"))) {
                long dtDiff = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
                if (dtDiff >= 90) {
                    Long aQuater = dtDiff / 90;
                    String cNewDate = CbsUtil.monthAdd(fdDate, Integer.parseInt(aQuater.toString()) * 3);

                    long newDiff = CbsUtil.dayDiff(ymd.parse(cNewDate), ymd.parse(matDate));

                    double cummInt = (amt * (roInt / 400));
                    cummInt = cummInt * aQuater;
                    if (newDiff > 0) {
                        msngTotalInt = (amt * roInt * ((double) newDiff / 36500));
                        msngTotalInt = msngTotalInt + cummInt;
                    } else {
                        msngTotalInt = msngTotalInt + cummInt;
                    }

                } else {
                    msngTotalInt = (amt * roInt * dtDiff / 36500);
                }
            }

            if ((InterestOption.equalsIgnoreCase("Y")) || (InterestOption.equalsIgnoreCase("Yearly"))) {

                List bnkList = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
                Vector eleBnkCd = (Vector) bnkList.get(0);
                String bnkCode = eleBnkCd.get(0).toString();

                String period = getYMDPeriod(prd);
                String[] values = period.split(":");

                int dd = Integer.parseInt(values[2]);
                int mon = Integer.parseInt(values[1]);
                int yrs = Integer.parseInt(values[0]);

                if (bnkCode.equalsIgnoreCase("kccl") && roInt == 9.05f && dd == 0 && mon == 96 && yrs == 0) {
                    msngTotalInt = amt;
                } else {

                    // New Code Added For Simple Yearly Option 
                    double TmpSIntTot = 0.0d;
                    msngTotalInt = 0;
                    int Months = mon + (yrs * 12);
                    TmpSIntTot = (amt * roInt * Months / 1200);
                    msngTotalInt = msngTotalInt + TmpSIntTot;
                    TmpSIntTot = (amt * roInt * dd / 36500);
                    msngTotalInt = msngTotalInt + TmpSIntTot;                   
                }
            }

            double TmpSIntTot = 0.0d;
            if ((InterestOption.equalsIgnoreCase("S")) || (InterestOption.equalsIgnoreCase("Simple"))) {
                msngTotalInt = 0;
                String period = getYMDPeriod(prd);
                String[] values = period.split(":");

                int mon = Integer.parseInt(values[1]);
                int day = Integer.parseInt(values[2]);
                int yrs = Integer.parseInt(values[0]);

                int Months = mon + (yrs * 12);
                TmpSIntTot = (amt * roInt * Months / 1200);
                msngTotalInt = msngTotalInt + TmpSIntTot;
                TmpSIntTot = (amt * roInt * day / 36500);
                msngTotalInt = msngTotalInt + TmpSIntTot;

            }
            return String.valueOf(Math.round(msngTotalInt));
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
}
