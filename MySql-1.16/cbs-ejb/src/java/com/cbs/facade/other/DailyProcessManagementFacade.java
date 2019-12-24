/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.report.DayActivityPojo;
import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.ho.DtlRegisterPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.OverDueReportFacadeRemote;
import com.cbs.facade.txn.AccountAuthorizationManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import javax.transaction.UserTransaction;

/**
 *
 * @author root
 */
@Stateless(mappedName = "DailyProcessManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class DailyProcessManagementFacade implements DailyProcessManagementRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPost43Remote;
    @EJB
    InterBranchTxnFacadeRemote interBrTxn;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    HoReportFacadeRemote hoReport;
    @EJB
    ReconcilationManagementFacadRemote autoReconsileRemote;
    @EJB
    LoanReportFacadeRemote loanReportFacade;
    @EJB
    AccountAuthorizationManagementFacadeRemote accountAuthMgmtFacade;
    @EJB
    OverDueReportFacadeRemote overDueReportFacade;
    @EJB
    OtherMgmtFacadeRemote othMgmtRemote;
    String tmpAcno, tmpIDate, tmpMDate, tdsNDate, tmpIntOpt, period, tempMinDate, tempMaxDate;
    double tmpVoucherNo, tmpPInterest, tdsAmt, tmpTDS, tmpBalInt, tmpInterest1, prevTDSAmt, tmpInt, tmpTDS1, tmpNetAmt, tmpPAmt;
    float tmpCRoi, tdsCumuPamt, recno, tmpRno;
    String custno, gPeriod, glTds;
    String glOdFdr, dbaOption, strVar;
    boolean tdsFlag, calcFlag, flg;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public List selectFromBankDays(String orgBrnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("SELECT date FROM bankdays WHERE DayEndFlag='N' AND brncode = '" + orgBrnCode + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectMinDateFromBnkDays(String orgBrnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select Min(Date) From bankdays Where DayBeginFlag='N' and DayEndFlag='Y' and Brncode='" + orgBrnCode + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getMinMonthDate(String orgBrnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select Min(date) From bankdays Where MendFlag='N' and Brncode='" + orgBrnCode + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define the month end date");
            }

            Vector v2 = (Vector) list.get(0);
            return v2.get(0).toString();

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String selectMinDateFromYearEnd(String orgBrnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select min(maxDate) from yearend Where YearEndFlag = 'N' and Brncode='" + orgBrnCode + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define the year end");
            }
            Vector v3 = (Vector) list.get(0);
            return v3.get(0).toString();

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectFromBank2Days(String orgBrnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select Date From bankdays Where DayBeginFlag='Y' and DayEndFlag1='N' and DayEndFlag='N' and Brncode='" + orgBrnCode + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectFromBank3Days(String orgBrnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select Date From bankdays Where DayBeginFlag='Y' and DayEndFlag1='Y' and DayEndFlag='N' and Brncode='" + orgBrnCode + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectFromCBSBankDays(String todayDate) throws ApplicationException {
        try {
            todayDate = todayDate.substring(6) + todayDate.substring(3, 5) + todayDate.substring(0, 2);
            List list = em.createNativeQuery("select DayBeginFlag,DayEndFlag from cbs_bankdays where Date='" + todayDate + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectMinFYearFromYearEnd(String orgBrnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select min(F_Year) from yearend where YearEndFlag='N' and Brncode='" + orgBrnCode + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectFromBank4Days(String orgBrnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select Min(Date) From bankdays Where DayBeginFlag='N' and Brncode='" + orgBrnCode + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectFromTdParameterInfo() throws ApplicationException {
        try {
            List list = em.createNativeQuery("select ifnull(ofdrflag,'') from td_parameterinfo where txnid = (select max(txnid) from td_parameterinfo)").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectMinAndMaxDate(String orgBrnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select min(mindate),min(maxdate) from yearend where yearendflag='N' and brncode='" + orgBrnCode + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private boolean checkDayEnd(String date, String brCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select DayEndFlag,DayEndFlag1 from bankdays where date='" + date + "'and Brncode='" + brCode + "'").getResultList();
            if (list.isEmpty()) {
                return false;
            }
            Vector v11 = (Vector) list.get(0);
            String dayEndFlag = v11.get(0).toString();
            if (dayEndFlag.equalsIgnoreCase("Y")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ApplicationException("Problem in getting data from Bank Days");
        }
    }

    /**
     * ***************COVER FOR ALL THE DAY BEGIN
     * TRANSACTIONS********************
     */
    public String dayBeginProcess(String orgBrnCode, String date, String userName) throws ApplicationException {
        String dt = date.substring(6) + date.substring(3, 5) + date.substring(0, 2);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String tmpFrom = "", tmpTo = "";
            List list10 = selectMinFYearFromYearEnd(orgBrnCode);
            if (!list10.isEmpty()) {
                Vector v10 = (Vector) list10.get(0);
                tmpFrom = "01/04/" + v10.get(0).toString();
                tmpTo = "31/03/" + String.valueOf(Integer.parseInt(v10.get(0).toString()) + 1);
            }
            tmpFrom = tmpFrom.substring(6) + tmpFrom.substring(3, 5) + tmpFrom.substring(0, 2);
            tmpTo = tmpTo.substring(6) + tmpTo.substring(3, 5) + tmpTo.substring(0, 2);
            List centralList = selectFromCBSBankDays(date);
            if (centralList.isEmpty()) {
                throw new ApplicationException("Data does not exist for current date in CBS Bank Days");
            }
            Vector v8 = (Vector) centralList.get(0);
            String dayBegin = v8.get(0).toString();
            if (dayBegin.equals("N")) {
                throw new ApplicationException("Please execute Central Day Begin Process first.");
            }
            String BDate = "";
            List list11 = selectFromBank4Days(orgBrnCode);
            if (!list11.isEmpty()) {
                Vector v11 = (Vector) list11.get(0);
                BDate = v11.get(0).toString();
            }

            if (!checkDayEnd(CbsUtil.dateAdd(BDate, -1), orgBrnCode)) {
                List list = em.createNativeQuery("select s.username from bankdays b, securityinfo s where b.date='" + CbsUtil.dateAdd(BDate, -1) + "' and b.brncode='" + orgBrnCode + "' and b.beginuser = s.userid").getResultList();
                Vector v11 = (Vector) list.get(0);
                throw new ApplicationException("Day Begin process has been already done by " + v11.get(0).toString());
            }

            if (ymd.parse(BDate).compareTo(ymd.parse(dt)) != 0) {
                throw new ApplicationException("Day Begin process has been already done for " + date);
            }

            List list12 = selectFromTdParameterInfo();
            Vector v12 = (Vector) list12.get(0);
            String odFdrFlag = v12.get(0).toString();

            updateBankDays(userName, BDate, orgBrnCode);

            if (odFdrFlag.equalsIgnoreCase("Y")) {
                String result = odFdrProcedure(date, orgBrnCode, tmpFrom, tmpTo, userName);
                if (!result.equalsIgnoreCase("True")) {
                    throw new ApplicationException("Problem in OD FDR Process.");
                }
            }
            insertIntoBankDaysHistory(BDate, orgBrnCode, userName);

            /**
             * ****************************** Previous Holiday Cash In Hand
             * Entry Start *********************************************
             */
            List list5 = em.createNativeQuery("select date_format(max(tdate),'%Y%m%d') from opcash where brncode='" + orgBrnCode + "'").getResultList();
            Vector vLst = (Vector) list5.get(0);
            String mDt = vLst.get(0).toString();
            String prDate = CbsUtil.dateAdd(dt, -1);

            int DtDiff = (int) CbsUtil.dayDiff(ymd.parse(mDt), ymd.parse(prDate));
            if (DtDiff != 0) {
                while (DtDiff > 0) {
                    mDt = CbsUtil.dateAdd(mDt, 1);

                    Query insert1 = em.createNativeQuery("insert into opcash (opamt,tdate,brncode) select opamt,'" + mDt + "',brncode from opcash where tdate = '" + CbsUtil.dateAdd(mDt, -1) + "' and brncode = '" + orgBrnCode + "'");
                    insert1.executeUpdate();

                    Query insert2 = em.createNativeQuery("insert into cashinhand (amt,ldate,brncode) select amt,'" + mDt + "',brncode from cashinhand where ldate = '" + CbsUtil.dateAdd(mDt, -1) + "' and brncode = '" + orgBrnCode + "'");
                    insert2.executeUpdate();

                    DtDiff = DtDiff - 1;
                }
            }

            /**
             *********************************************
             * Previous Holiday Cash In Hand Entry End *
             * ********************************************
             *
             */
            /**
             * Auto NPA Posting *
             */
            if (!(orgBrnCode.equalsIgnoreCase("90") || orgBrnCode.equalsIgnoreCase("0A"))) {
                String autoNpaMarkingOnDayBegin = "N";
                List autoNpaMarkingList = em.createNativeQuery("select ifnull(code,'N') from cbs_parameterinfo  where name ='AUTO-NPA-MARKING-DAYBEGIN'").getResultList();
                if (!autoNpaMarkingList.isEmpty()) {
                    Vector autoNpaMarkingVect = (Vector) autoNpaMarkingList.get(0);
                    autoNpaMarkingOnDayBegin = autoNpaMarkingVect.get(0).toString();
                    if (autoNpaMarkingOnDayBegin.equalsIgnoreCase("Y")) {
                        List<NpaAccountDetailPojo> probableResultlist = overDueReportFacade.getProbableNpaDetail("ALL", "ALL", dt, orgBrnCode);
                        List<NpaAccountDetailPojo> alreadyResultlist = overDueReportFacade.getNpaDetail("ALL", "ALL", dt, orgBrnCode, "Y");
                        probableResultlist.addAll(alreadyResultlist);
                        if (!probableResultlist.isEmpty()) {
                            String result = ftsPost43Remote.npaPosting(probableResultlist, dt, userName, orgBrnCode);
                            if (!result.equalsIgnoreCase("Npa marking is successfull.")) {
                                throw new ApplicationException("Problem in Auto NPA Posting:" + result);
                            }
                        }
                    }
                }
            }
            //Management of Denomination Opening
            //End here

            List denoList = em.createNativeQuery("select ifnull(date_format(max(dt),'%Y%m%d'),'') from denomination_opening where brncode='" + orgBrnCode + "'").getResultList();
            Vector denoVLst = (Vector) denoList.get(0);
            String denoMDt = denoVLst.get(0).toString();
            if (!(denoMDt.equalsIgnoreCase(""))) {
                int denoDtDiff = (int) CbsUtil.dayDiff(ymd.parse(denoMDt), ymd.parse(dt));
                if (denoDtDiff != 0) {
                    while (denoDtDiff > 0) {
                        denoMDt = CbsUtil.dateAdd(denoMDt, 1);

                        List list = em.createNativeQuery("select brncode,denomination,denomination_value,new_old_flag "
                                + "from denomination_opening where brncode='" + orgBrnCode + "' and dt "
                                + "in(select max(dt) from denomination_opening where brncode='" + orgBrnCode + "' and "
                                + "dt<'" + ymd.format(ymd.parse(denoMDt)) + "')").getResultList();
                        if (!list.isEmpty()) {
                            String denominationInsertQuery = "INSERT INTO denomination_opening(brncode,denomination,"
                                    + "denomination_value,dt,new_old_flag,trantime) VALUES";
                            String denominationDataQuery = "";
                            for (int i = 0; i < list.size(); i++) {
                                Vector denoVec = (Vector) list.get(i);
                                String denoBrnCode = denoVec.get(0).toString();
                                BigDecimal denomination = new BigDecimal(denoVec.get(1).toString());
                                int denoValue = Integer.parseInt(denoVec.get(2).toString());
                                String denoFlag = denoVec.get(3).toString();
                                if (denominationDataQuery.equals("")) {
                                    denominationDataQuery = "('" + denoBrnCode + "'," + denomination + "," + denoValue + ","
                                            + "'" + ymd.format(ymd.parse(denoMDt)) + "','" + denoFlag + "',now())";
                                } else {
                                    denominationDataQuery = denominationDataQuery + "," + "('" + denoBrnCode + "',"
                                            + "" + denomination + "," + denoValue + ",'" + ymd.format(ymd.parse(denoMDt)) + "',"
                                            + "'" + denoFlag + "',now())";
                                }
                            }
                            denominationInsertQuery = denominationInsertQuery + denominationDataQuery;
                            int n = em.createNativeQuery(denominationInsertQuery).executeUpdate();
                            if (n <= 0) {
                                throw new ApplicationException("Problem in denomination insertion.");
                            }
                        }

                        denoDtDiff = denoDtDiff - 1;
                    }
                }
            }
            ut.commit();
            return "True";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    /**
     * *
     * **************************************************************************
     */
    public String odFdrProcedure(String tempBd, String orgBrnCode, String from, String to, String authBy) throws ApplicationException {
        try {
            String fieldDisplayResult = "", oFPostingResult = "", oFTransferResult = "", acct;
            int i = 1;
            tempBd = tempBd.substring(6) + tempBd.substring(3, 5) + tempBd.substring(0, 2);
            List l = selectMinAndMaxDate(orgBrnCode);
            Vector v = (Vector) l.get(0);
            tempMinDate = v.get(0).toString();
            tempMaxDate = v.get(1).toString();
            List list1 = em.createNativeQuery("Select ifnull(td.ACNO,''),ifnull(VoucherNo,0),PrinAmt,ROI,IntOpt,FDDT,MatDt,Status,"
                    + "ReceiptNo,IntToAcno,Years,Months,Days,Period,Lien,ClDt,FinalAmt,Penalty,NetRoi,PrevVoucherNo,EnterBy,Auth,"
                    + "td.AuthBy,TranTime,NextIntPayDt,CumuPrinAmt,TD_MadeDT,OFFlag,TDSDeducted,OFAcno,AutoRenew,SeqNo From "
                    + "td_vouchmst td,td_accountmaster ta Where td.matDt< '" + tempBd + "' and td.status='A' and "
                    + "(td.Offlag='N' OR td.OFFLAG IS NULL) and td.acno=ta.acno and ta.curBrCode='" + orgBrnCode + "'").getResultList();
            for (int j = 0; j < list1.size(); j++) {
                if (i > 100) {
                    i = 1;
                }
                Vector v1 = (Vector) list1.get(j);
                tmpAcno = v1.get(0).toString();
                tmpVoucherNo = Double.parseDouble(v1.get(1).toString());

                List list2 = em.createNativeQuery("Select ifnull(max(substring(R.acno,5,6)),'0') From of_recon R,accountmaster A "
                        + "where A.ACNO=R.ACNO AND A.CurBrCode= '" + orgBrnCode + "' ").getResultList();
                Vector v2 = (Vector) list2.get(0);
                acct = v2.get(0).toString();
                custno = String.valueOf(Integer.parseInt(acct) + 1);
                while (custno.length() < 6) {
                    custno = "0" + custno;
                }
                glOdFdr = orgBrnCode + CbsAcCodeConstant.OF_AC.getAcctCode() + custno + "01";
                fieldDisplayResult = fieldDisplay(tmpAcno, tmpVoucherNo, orgBrnCode, tempMinDate, tempMaxDate, from, to);
                if (!fieldDisplayResult.equals("True")) {
                    return fieldDisplayResult;
                }
                oFPostingResult = oFPosting(tmpAcno, orgBrnCode, tmpVoucherNo, tmpIntOpt, tempBd, authBy, from);
                if (!oFPostingResult.equalsIgnoreCase("True")) {
                    return oFPostingResult;
                }
                oFTransferResult = oFTransfer(authBy, tempBd, orgBrnCode);
                if (!oFTransferResult.equalsIgnoreCase("True")) {
                    return oFTransferResult;
                }
            }
            return "TRUE";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String fieldDisplay(String TempAcno, double TempVoucherNo, String orgBrnCode, String minDate, String maxDate, String from, String to) throws ApplicationException {
        try {
            List list3 = em.createNativeQuery("select acno,DATE_FORMAT(FDDT,'%Y%m%d'),DATE_FORMAT(MatDt,'%Y%m%d'),ROI,PrinAmt,IntOpt,Lien,period,status,offlag,ofacno,cumuprinamt,DATE_FORMAT(nextintpaydt,'%Y%m%d') From td_vouchmst Where acno='" + TempAcno + "'  and voucherno = " + TempVoucherNo + "").getResultList();
            List list4 = em.createNativeQuery("Select TDSFlag From td_accountmaster Where Acno='" + TempAcno + "'").getResultList();
            Vector v4 = (Vector) list4.get(0);
            if (v4.get(0).toString().equalsIgnoreCase("N")) {
                tdsFlag = false;
            } else {
                tdsFlag = true;
            }
            Vector v3 = (Vector) list3.get(0);
            tmpPAmt = Double.parseDouble(v3.get(4).toString());
            tmpCRoi = Float.parseFloat(v3.get(3).toString());
            tmpIDate = v3.get(1).toString();
            tmpMDate = v3.get(2).toString();
            tdsCumuPamt = Float.parseFloat(v3.get(11).toString());
            tdsNDate = v3.get(12).toString();
            if (v3.get(5).toString().equalsIgnoreCase("C")) {
                tmpIntOpt = "C";
            } else if (v3.get(5).toString().equalsIgnoreCase("S")) {
                tmpIntOpt = "S";
                gPeriod = "";
                gPeriod = v3.get(7).toString();
            } else if (v3.get(5).toString().equalsIgnoreCase("Q")) {
                tmpIntOpt = "Q";

            } else if (v3.get(5).toString().equalsIgnoreCase("M")) {
                tmpIntOpt = "M";
            }
            List list5 = em.createNativeQuery("Select ifnull(Sum(ifnull(Interest,0)),0) From td_interesthistory where acno='" + TempAcno
                    + "' and voucherno=" + TempVoucherNo + "").getResultList();
            if (!list5.isEmpty()) {
                Vector v5 = (Vector) list5.get(0);
                tmpPInterest = Double.parseDouble(v5.get(0).toString());
            } else {
                tmpPInterest = 0;
            }
            tdsAmt = 0;
            List list6 = em.createNativeQuery("Select ifnull(Sum(ifnull(TDS,0)),0) From tdshistory Where Voucherno=" + TempVoucherNo
                    + " And Acno='" + TempAcno + "' and dt>='" + minDate + "' and dt<='" + maxDate + "'").getResultList();
            if (!list6.isEmpty()) {
                Vector v6 = (Vector) list6.get(0);
                tmpTDS = Double.parseDouble(v6.get(0).toString());
            } else {
                tmpTDS = 0;
            }
            if (tmpIntOpt.equalsIgnoreCase("C") && tmpTDS > 0) {
                tmpBalInt = cummulativeIntCalc(tmpCRoi, tdsNDate, tmpMDate, tdsCumuPamt);
            } else {
                tmpBalInt = Double.parseDouble(fdIntCalc(tmpIntOpt, tmpCRoi, tmpIDate, tmpMDate, tmpPAmt, gPeriod)) - tmpPInterest;
            }
            calcFlag = true;
            tmpInterest1 = tmpBalInt + tmpPInterest;
            String result = tdsProcess(TempVoucherNo, TempAcno, minDate, from, to, orgBrnCode, tmpIntOpt);
            if (result.equalsIgnoreCase("True")) {
                return "True";
            } else {
                return "False";
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * *************************************Calling To
     * TD_TDSProc*************************************
     */
    public String tdsProcess(double TempVoucherNo, String TempAcno, String minDate, String fromDate, String toDate, String orgBrnCode, String intopt) throws ApplicationException {
        try {
            double TotIntAmt, tmpTds_Amount = 0, tmpTDS_Rt = 0, tmpTDS_Sur = 0;
            prevTDSAmt = 0;

            List list7 = em.createNativeQuery("Select ifnull(Sum(ifnull(TDS,0)),0) From tdshistory Where Voucherno=" + TempVoucherNo + " And Acno='"
                    + TempAcno + "' and dt<='" + minDate + "'").getResultList();
            Vector v7 = (Vector) list7.get(0);
            prevTDSAmt = Double.parseDouble(v7.get(0).toString());

            List list9 = em.createNativeQuery("Select ifnull(Sum(ifnull(Interest,0)),0) From td_interesthistory where acno='"
                    + TempAcno + "' and  voucherno=" + TempVoucherNo + " and dt>='" + fromDate + "' and dt<='" + toDate + "'").getResultList();
            TotIntAmt = 0;
            if (!list9.isEmpty()) {
                Vector v9 = (Vector) list9.get(0);
                TotIntAmt = Double.parseDouble(v9.get(0).toString());
            }
            if (tmpBalInt >= 0) {
                TotIntAmt = TotIntAmt + tmpBalInt;
            }
            List list10 = em.createNativeQuery("select TDS_Amount,TDS_Rate,TDS_Surcharge from tdsslab where LastUpdateDt = "
                    + "(select max(LastUpdateDt) from tdsslab)").getResultList();
            if (!list10.isEmpty()) {
                Vector v10 = (Vector) list10.get(0);
                tmpTds_Amount = Double.parseDouble(v10.get(0).toString());
                tmpTDS_Rt = Double.parseDouble(v10.get(1).toString());
                tmpTDS_Sur = Double.parseDouble(v10.get(2).toString());
            }
            tmpInt = tmpInterest1;

            List list11 = em.createNativeQuery("select tdsflag from td_accountmaster where acno='" + TempAcno + "' and tdsflag='Y'").getResultList();
            if (!list11.isEmpty()) {
                if (TotIntAmt > tmpTds_Amount) {
                    tmpTDS1 = (TotIntAmt * (tmpTDS_Rt + (tmpTDS_Rt * tmpTDS_Sur / 100)) / 100) - tmpTDS;
                    if (tmpTDS1 < 0) {
                        tmpTDS1 = 0;
                    }
                } else {
                    tmpTDS1 = 0;
                }
            }
            tmpNetAmt = 0;
            if (intopt.equalsIgnoreCase("Q") || intopt.equalsIgnoreCase("M")) {
                tmpNetAmt = tmpBalInt + tmpPAmt - tmpTDS1 - prevTDSAmt;
            } else {
                tmpNetAmt = tmpPAmt + tmpInterest1 - tmpTDS - prevTDSAmt - tmpTDS1;
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * **********************************************************************************************
     */
    /**
     * **********************Code For
     * OF_Posting******************************************************
     */
    public String oFPosting(String tempAccountNo, String orgBrnCode, double voucherNo, String intOpt, String date, String authBy, String from) throws ApplicationException {
        int Param_PostFlag = 0;
        double tmpCrAmt, TDBalance;
        String tmpPreDetails, GLAcno = null, ProvGLHead = null, tmpReconBalan;
        try {
            tmpReconBalan = CbsUtil.getReconTableName(CbsConstant.PAY_ORDER);
            List list12 = em.createNativeQuery("select GLHeadInt,glheadprov from accounttypemaster where acctcode='" + tempAccountNo.substring(2, 4) + "'").getResultList();
            if (!list12.isEmpty()) {
                Vector v12 = (Vector) list12.get(0);
                if (v12.get(0).toString() != null) {
                    GLAcno = orgBrnCode + v12.get(0).toString() + "01";
                    ProvGLHead = "";
                    if (v12.get(1).toString() != null) {
                        ProvGLHead = orgBrnCode + v12.get(1).toString() + "01";
                    }
                }
            }
            tmpRno = ftsPost43Remote.getTrsNo();
            TDBalance = 0;
            if (intOpt.equalsIgnoreCase("C")) {
                tmpCrAmt = tmpPAmt + tmpPInterest;
            } else {
                tmpCrAmt = tmpPAmt;
                TDBalance = TDBalance + tmpPAmt;
            }
            tmpPreDetails = "Deposit Closed(Mature), P. Amt: " + tmpPAmt + " and Int. for remaing days is " + tmpBalInt;
            recno = ftsPost43Remote.getRecNo();
            Query insert2 = em.createNativeQuery("insert into td_recon (acno,FDDt,Dt,ValueDt,Cramt,enterBy,TranType,Ty,closeFlag,details,recno, "
                    + "trsno,trantime,auth,authby,org_brnid,dest_brnid) values('" + tempAccountNo + "','" + date + "','" + date + "','" + date + "'," + tmpCrAmt + ",'"
                    + authBy + "',2,0,'C','" + tmpPreDetails + "'," + recno + "," + tmpRno + ",now(),'Y','" + authBy + "','" + orgBrnCode + "','" + tempAccountNo.substring(0, 2) + "')");
            insert2.executeUpdate();
            if (intOpt.equalsIgnoreCase("S") && Param_PostFlag == 2) {
                if (tmpPInterest > 0) {
                    recno = ftsPost43Remote.getRecNo();
                    Query insert3 = em.createNativeQuery("insert into td_recon (acno,Dt,ValueDt,Cramt,enterBy,TranType,Ty,recno, trsno,trantime,"
                            + "details,auth,authby,org_brnid,dest_brnid) values('" + tempAccountNo + "','" + date + "','" + date + "'," + tmpPInterest + ",'" + authBy
                            + "',2,0," + recno + "," + tmpRno + ",now(),'Prov. Int. Paid to TD A/c','Y','SYSTEM','" + orgBrnCode + "','" + tempAccountNo.substring(0, 2) + "')");
                    insert3.executeUpdate();
                    Query insert4 = em.createNativeQuery("insert into gl_recon (acno,Dramt,enterBy,TranType,Ty,dt,ValueDt,details,trantime,recno, "
                            + "trsno,AUTH,authby,adviceNo,adviceBrnCode,org_brnid,dest_brnid) values('" + ProvGLHead + "'," + tmpPInterest + ",'" + authBy + "',2,1,'"
                            + date + "','" + date + "','Vch Trf To  A/c: " + tmpAcno.substring(2, 10) + "  V.No: " + tmpVoucherNo + "  IntOpt: "
                            + tmpIntOpt + "',now()," + recno + "," + tmpRno + ",'Y','SYSTEM','','','" + orgBrnCode + "','" + ProvGLHead.substring(0, 2) + "')");
                    insert4.executeUpdate();
                    reconBalanceUpdation(GLAcno, tmpReconBalan, -tmpPInterest, orgBrnCode);
                }
            }
            TDBalance = TDBalance + tmpBalInt;
            if (tmpBalInt > 0) {
                recno = ftsPost43Remote.getRecNo();

                Query insert5 = em.createNativeQuery("insert into td_recon (acno,voucherno,cramt,enterBy,TranType,Ty,dt,ValueDt,fddt,recno, trsno,"
                        + "trantime,intflag,details,AUTH,AUTHBY,org_brnid,dest_brnid) values('" + tempAccountNo + "'," + voucherNo + "," + tmpBalInt + ",'"
                        + authBy + "',2,0,'" + date + "','" + date + "','" + date + "'," + recno + "," + tmpRno + ",now(),'I',"
                        + "'Bal.Int.Paid on Acno: " + tempAccountNo + "  V.No.: " + voucherNo + "  IntOpt: " + intOpt + "','Y','SYSTEM','" + orgBrnCode + "','" + tempAccountNo.substring(0, 2) + "')");
                insert5.executeUpdate();
            } else if (tmpBalInt < 0) {
                recno = ftsPost43Remote.getRecNo();
                Query insert6 = em.createNativeQuery("insert into td_recon (acno,voucherno,dramt,enterBy,TranType,Ty,dt,ValueDt,fddt,recno, trsno,"
                        + "trantime,intflag,details,AUTH,AUTHBY,org_brnid,dest_brnid) values('" + tempAccountNo + "'," + voucherNo + "," + Math.abs(tmpBalInt) + ",'"
                        + authBy + "',2,1,'" + date + "','" + date + "','" + date + "'," + recno + "," + tmpRno + ",now(),'I',"
                        + "'Bal.Int.Paid on Acno: " + tempAccountNo.substring(2, 10) + "  V.No.: " + voucherNo + "  IntOpt: " + intOpt + "','Y','SYSTEM','" + orgBrnCode + "','" + tempAccountNo.substring(0, 2) + "')");
                insert6.executeUpdate();
            }
            if (tmpBalInt > 0) {
                Query insert7 = em.createNativeQuery("insert into gl_recon (acno,Dramt,enterBy,TranType,Ty,dt,ValueDt,details,trantime,recno, trsno,"
                        + "AUTH,AUTHBY,adviceNo,adviceBrnCode,org_brnid,dest_brnid) values('" + GLAcno + "'," + tmpBalInt + ",'" + authBy + "',2,1,'" + date + "','" + date
                        + "','Trf To " + GLAcno.substring(2, 10) + " For : " + tempAccountNo.substring(2, 10) + "/" + voucherNo + "/" + intOpt
                        + "',now()," + recno + "," + tmpRno + ",'Y','SYSTEM','','','" + orgBrnCode + "','" + GLAcno.substring(0, 2) + "')");
                insert7.executeUpdate();
                reconBalanceUpdation(GLAcno, tmpReconBalan, -tmpBalInt, orgBrnCode);
            } else if (tmpBalInt < 0) {
                Query insert8 = em.createNativeQuery("insert into gl_recon (acno,crAmt,enterBy,TranType,Ty,dt,ValueDt,details,trantime,recno, trsno,"
                        + "AUTH,AUTHBY,adviceNo,adviceBrnCode,org_brnid,dest_brnid) values('" + GLAcno + "'," + Math.abs(tmpBalInt) + ",'" + authBy + "',2,0,'" + date
                        + "','" + date + "','Trf To " + GLAcno.substring(2, 10) + " For : " + tempAccountNo.substring(2, 10) + "/" + voucherNo + "/"
                        + intOpt + "',now()," + recno + "," + tmpRno + ",'Y','SYSTEM','','','" + orgBrnCode + "','" + GLAcno.substring(0, 2) + "')");
                insert8.executeUpdate();
                reconBalanceUpdation(GLAcno, tmpReconBalan, Math.abs(tmpBalInt), orgBrnCode);
            }
            if (!intOpt.equalsIgnoreCase("C")) {
                Query update1 = em.createNativeQuery("update td_vouchmst set OFFlag='N',status='C',cldt='" + date + "',FinalAmt=" + tmpNetAmt
                        + ",penalty=0,netroi=0 Where Acno='" + tempAccountNo + "' and voucherno= " + voucherNo + "");
                update1.executeUpdate();
            } else if (intOpt.equalsIgnoreCase("C")) {
                Query update1 = em.createNativeQuery("update td_vouchmst set OFFlag='N',status='C',cldt='" + date + "',FinalAmt=" + tmpNetAmt
                        + ",penalty=0,netroi=0,CumuPrinAmt=" + tmpNetAmt + " Where Acno='" + tempAccountNo + "' and voucherno= " + voucherNo + "");
                update1.executeUpdate();
            }
            if (tmpBalInt > 0) {
                Query insert9 = em.createNativeQuery("Insert Into td_interesthistory(acno,dt,interest,voucherno,ToDt,FromDt,intOPt) values ('"
                        + tempAccountNo + "','" + date + "'," + tmpBalInt + "," + voucherNo + ",'" + tmpMDate + "','" + tmpIDate + "','" + intOpt + "')");
                insert9.executeUpdate();
            }
            List list15 = em.createNativeQuery("Select TDSFlag From td_accountmaster where acno='" + tempAccountNo + "'").getResultList();
            if (!list15.isEmpty()) {
                Vector v15 = (Vector) list15.get(0);
                if (v15.get(0).toString().equalsIgnoreCase("Y")) {
                    if (tmpTDS1 > 0) {
                        Query insert10 = em.createNativeQuery("insert into tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt) values ('"
                                + tempAccountNo + "'," + voucherNo + "," + tmpTDS1 + ",'" + date + "','" + tmpMDate + "','" + from + "','" + intOpt + "')");
                        insert10.executeUpdate();

                        TDBalance = TDBalance - tmpTDS1;
                        TDBalance = TDBalance - tmpTDS;

                        recno = ftsPost43Remote.getRecNo();
                        Query insert11 = em.createNativeQuery("insert into td_recon (acno,voucherno,drAmt,fdDt,dt,ValueDt,enterBy,TranType,Ty,details,"
                                + "intflag,trsno,recno,AUTH,AUTHBY,org_brnid,dest_brnid) values('" + tempAccountNo + "'," + voucherNo + "," + tmpTDS1 + ",'" + date + "','"
                                + date + "','" + date + "','" + authBy + "',2,1,'TDS Deducted For Acno: " + tempAccountNo.substring(2, 10) + "/"
                                + voucherNo + "/ " + intOpt + "','T'," + tmpRno + "," + recno + ",'Y','SYSTEM','" + orgBrnCode + "','" + tempAccountNo.substring(0, 2) + "')");
                        insert11.executeUpdate();
                        if (!glTds.equalsIgnoreCase("false")) {
                            Query insert12 = em.createNativeQuery("insert into gl_recon (acno,cramt,enterBy,TranType,Ty,dt,ValueDt,details,trsno,recno,"
                                    + "AUTH,AUTHBY,adviceNo,adviceBrnCode,org_brnid,dest_brnid) values('" + glTds + "'," + tmpTDS1 + ",'" + authBy + "',2,0,'" + date
                                    + "','" + date + "','TDS Deducted For Acno: " + tempAccountNo.substring(2, 10) + "/" + voucherNo + "/ " + intOpt
                                    + "'," + tmpRno + "," + recno + ",'Y','SYSTEM','','','" + orgBrnCode + "','" + glTds.substring(0, 2) + "')");
                            insert12.executeUpdate();
                            reconBalanceUpdation(glTds, tmpReconBalan, tmpTDS1, orgBrnCode);
                        }
                    }
                }
                List list16 = em.createNativeQuery("select TDSDeducted From td_vouchmst Where Acno='" + tempAccountNo + "' and voucherno="
                        + voucherNo + "").getResultList();
                if (!list16.isEmpty()) {
                    Vector v16 = (Vector) list16.get(0);
                    if (v16.get(0).toString() == null) {
                        Query update1 = em.createNativeQuery("update td_vouchmst set TDSDeducted=" + tmpTDS1 + " Where Acno='" + tempAccountNo
                                + "' and voucherno=" + voucherNo + "");
                        update1.executeUpdate();
                    } else {
                        Query update1 = em.createNativeQuery("update td_vouchmst set TDSDeducted=" + tmpTDS1 + "+" + Double.parseDouble(v16.get(0).toString())
                                + " Where Acno='" + tempAccountNo + "' and voucherno=" + voucherNo + "");
                        update1.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "TRUE";
    }

    /**
     * ************************************************************************************************
     */
    /**
     * ************************************Procedure
     * OF_Transfer**************************************
     */
    public String oFTransfer(String authBy, String tempBd, String orgBrnCode) throws ApplicationException {

        try {
            Query update1 = em.createNativeQuery("Update td_vouchmst set OFFlag='Y',OFAcno='" + glOdFdr + "' WHERE ACNO='" + tmpAcno
                    + "' AND VOUCHERNO=" + tmpVoucherNo + "");
            update1.executeUpdate();

            recno = ftsPost43Remote.getRecNo();
            Query insert1 = em.createNativeQuery("insert into td_recon(acno,drAmt,ty,trantype,details,enterBy,auth,dt,ValueDt,trsno,recno,AUTHBY,org_brnid,dest_brnid) "
                    + "values('" + tmpAcno + "', " + tmpNetAmt + ",1,2,'Transferred to ODFDR Ac No. : " + glOdFdr.substring(2, 10) + "','"
                    + authBy + "','Y','" + tempBd + "','" + tempBd + "'," + tmpRno + "," + recno + ",'SYSTEM','" + orgBrnCode + "','" + tmpAcno.substring(0, 2) + "')");
            insert1.executeUpdate();

            /**
             * *************************** OF CASE ENTRY
             * *********************************************
             */
            recno = ftsPost43Remote.getRecNo();
            Query insertOf = em.createNativeQuery("insert into of_recon(acno,tdacno,voucherno,cramt,ty,trantype,details,enterby,auth,dt,ValueDt,trsno,recno,TranTime,AUTHBY,"
                    + "org_brnid,dest_brnid) "
                    + "values('" + glOdFdr + "','" + tmpAcno + "','" + tmpVoucherNo + "', " + tmpNetAmt + ",0,2,'Transferred from Td Ac No. : " + tmpAcno.substring(2, 10) + "','"
                    + authBy + "','Y','" + tempBd + "','" + tempBd + "'," + tmpRno + "," + recno + ",now(),'SYSTEM','" + orgBrnCode + "','" + tmpAcno.substring(0, 2) + "')");
            insertOf.executeUpdate();

            /**
             * ********************************END
             * ***************************************************
             */
            List list1 = em.createNativeQuery("select acno,dt,balance from td_reconbalan where acno= '" + glOdFdr + "'").getResultList();
            if (!list1.isEmpty()) {
                Vector v1 = (Vector) list1.get(0);
                Query update2 = em.createNativeQuery("update td_reconbalan set DT='" + tempBd + "',balance=" + Float.parseFloat(v1.get(2).toString())
                        + "+" + tmpNetAmt + " where acno= '" + glOdFdr + "'");
                update2.executeUpdate();
            } else {
                recno = ftsPost43Remote.getRecNo();
                Query insert2 = em.createNativeQuery("insert into td_reconbalan(acno,dt,balance) values('" + glOdFdr + "','" + tempBd + "'," + tmpNetAmt + ")");
                insert2.executeUpdate();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "TRUE";
    }

    public String reconBalanceUpdation(String accNo, String tableName, double interest, String orgBrnCode) throws ApplicationException {
        String Tempbd, acctNature;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        Tempbd = sdf.format(date);
        try {
            acctNature = ftsPost43Remote.getAccountNature(accNo);
            if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List selectQuery = em.createNativeQuery("select acno,dt,balance from ca_reconbalan where acno='" + accNo + "'").getResultList();
                if (selectQuery.isEmpty()) {
                    Query insertQuery = em.createNativeQuery("insert into ca_reconbalan(Acno,dt,Balance)"
                            + "values (" + "'" + accNo + "'" + "," + "'" + Tempbd + "'" + "," + interest + ")");
                    insertQuery.executeUpdate();
                } else {
                    Query updateQuery1A = em.createNativeQuery("UPDATE ca_reconbalan SET Acno ='" + accNo + "',dt = '" + Tempbd + "',Balance ="
                            + interest + " where acno='" + accNo + "'");
                    updateQuery1A.executeUpdate();
                }
            } else if ((acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) || (acctNature.equalsIgnoreCase(CbsConstant.OF_AC))) {
                List selectQuery = em.createNativeQuery("select acno,dt,balance from td_reconbalan where acno='" + accNo + "'").getResultList();
                if (selectQuery.isEmpty()) {
                    Query insertQuery = em.createNativeQuery("insert into td_reconbalan(Acno,dt,Balance)"
                            + "values (" + "'" + accNo + "'" + "," + "'" + Tempbd + "'" + "," + interest + ")");
                    insertQuery.executeUpdate();
                } else {
                    Query updateQuery1A = em.createNativeQuery("UPDATE td_reconbalan SET Acno ='" + accNo + "',dt = '" + Tempbd + "',Balance =" + interest + " where acno='" + accNo + "'");
                    updateQuery1A.executeUpdate();
                }
            } else {
                List selectQuery = em.createNativeQuery("select acno,dt,balance from reconbalan where acno='" + accNo + "'").getResultList();
                if (selectQuery.isEmpty()) {
                    Query insertQuery = em.createNativeQuery("insert into reconbalan(Acno,dt,Balance)"
                            + "values (" + "'" + accNo + "'" + "," + "'" + Tempbd + "'" + "," + interest + ")");
                    insertQuery.executeUpdate();
                } else {
                    Query updateQuery1A = em.createNativeQuery("UPDATE reconbalan SET Acno ='" + accNo + "',dt = '" + Tempbd + "',Balance =" + interest + " where acno='" + accNo + "'");
                    updateQuery1A.executeUpdate();
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "True";
    }

    /**
     * ***************************cummulativeIntCalc From
     * Zeeshan************************************
     */
    public double cummulativeIntCalc(float amount, String matDtNew, String fdDtNew, float roi) throws ApplicationException {
        float msngTotalInt = 0;
        try {
            int datDiff = (int) CbsUtil.dayDiff(ymd.parse(fdDtNew), ymd.parse(matDtNew));
            if (datDiff >= 90) {
                int aQuarter = datDiff / 90;
                Calendar cal = Calendar.getInstance();
                cal.setTime(ymd.parse(fdDtNew));
                cal.add(Calendar.MONTH, (aQuarter * 3));
                int newDiff = (int) CbsUtil.dayDiff(cal.getTime(), ymd.parse(matDtNew));
                float sNo = 1;
                int i = 1;
                while (i <= aQuarter) {
                    sNo = (1 + (roi / 400)) * sNo;
                    i = i + 1;
                }
                float finalAmount = (amount * sNo) - amount;
                float newfinalAmount = amount + finalAmount;
                if (newDiff > 0) {
                    msngTotalInt = newfinalAmount * roi * (newDiff / 36500);
                    msngTotalInt = msngTotalInt + finalAmount;
                } else {
                    msngTotalInt = msngTotalInt + finalAmount;
                }
            } else {
                msngTotalInt = amount * roi * (datDiff / 36500);
            }
            return msngTotalInt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * ******************************************************************************************
     */
    /**
     * ****************************fdIntCalc From
     * NewTermDepositRecieptCreationBean****************************************************
     */
    public String fdIntCalc(String InterestOption, float RoInt, String FDDate, String MatDate, double amt, String prd) throws ApplicationException {
        try {
            int DtDiff = 0;
            Calendar cal = Calendar.getInstance();
            double msngTotalInt = 0.0d;
            if ((InterestOption.equalsIgnoreCase("M")) || (InterestOption.equalsIgnoreCase("Monthly"))) {
                DtDiff = (int) CbsUtil.dayDiff(ymd.parse(FDDate), ymd.parse(MatDate));
                if (DtDiff >= 30) {
                    int aQuater = DtDiff / 30;
                    cal.setTime(ymd.parse(FDDate));
                    cal.add(Calendar.MONTH, aQuater);
                    double NewDiffm = CbsUtil.dayDiff(cal.getTime(), ymd.parse(MatDate));
                    double CummInt = amt - (amt * (1 / (1 + (RoInt / 1200))));
                    CummInt = CummInt * aQuater;
                    if (NewDiffm > 0) {
                        msngTotalInt = amt * RoInt * (NewDiffm / 36500);
                        msngTotalInt = msngTotalInt + CummInt;
                    } else {
                        msngTotalInt = msngTotalInt + CummInt;
                    }
                } else {
                    msngTotalInt = (amt * RoInt * DtDiff) / 36500;
                }
            }
            if ((InterestOption.equalsIgnoreCase("C")) || (InterestOption.equalsIgnoreCase("Cumulative"))) {
                DtDiff = (int) CbsUtil.dayDiff(ymd.parse(FDDate), ymd.parse(MatDate));
                if (DtDiff >= 90) {
                    int aQuater = DtDiff / 91;
                    cal.setTime(ymd.parse(FDDate));
                    cal.add(Calendar.MONTH, aQuater * 3);
                    double neDiff = CbsUtil.dayDiff(cal.getTime(), ymd.parse(MatDate));
                    double ab = 36500.0d;
                    double CummIntRate = 1.0d;
                    for (int i = 1; i <= aQuater; i++) {
                        CummIntRate = (1 + (RoInt / 400)) * CummIntRate;
                    }
                    double CummInt = (amt * CummIntRate) - amt;
                    double NewPrnAmt = amt + CummInt;
                    if (neDiff > 0) {
                        msngTotalInt = NewPrnAmt * RoInt * (neDiff / ab);
                        msngTotalInt = msngTotalInt + CummInt;
                    } else {
                        msngTotalInt = msngTotalInt + CummInt;
                    }
                } else {
                    msngTotalInt = (amt * RoInt * DtDiff) / 36500;
                }
            }
            if ((InterestOption.equalsIgnoreCase("Q")) || (InterestOption.equalsIgnoreCase("Quarterly"))) {
                DtDiff = (int) CbsUtil.dayDiff(ymd.parse(FDDate), ymd.parse(MatDate));
                if (DtDiff >= 90) {
                    int aQuater = DtDiff / 90;
                    cal.setTime(ymd.parse(FDDate));
                    cal.add(Calendar.MONTH, aQuater * 3);
                    double NewDiffq = CbsUtil.dayDiff(cal.getTime(), ymd.parse(MatDate));
                    double CummInt = (amt * (RoInt / 400));
                    CummInt = CummInt * aQuater;
                    if (NewDiffq > 0) {
                        msngTotalInt = (amt * RoInt * (NewDiffq / 36500));
                        msngTotalInt = msngTotalInt + CummInt;
                    } else {
                        msngTotalInt = msngTotalInt + CummInt;
                    }
                } else {
                    msngTotalInt = (amt * RoInt * DtDiff / 36500);
                }
            }
            double TmpSIntTot = 0.0d;
            if ((InterestOption.equalsIgnoreCase("S")) || (InterestOption.equalsIgnoreCase("Simple"))) {
                msngTotalInt = 0;
                String[] values = null;
                String ddd = "";
                String mmm = "";
                String yyy = "";
                String str = prd;
                boolean dd = str.contains("Days");
                boolean mm = str.contains("Months");
                boolean yy = str.contains("Years");
                if (yy == true) {
                    str = str.replace("Years", ":");
                } else {
                    str = "0:" + str;
                }
                if (dd == true) {
                    str = str.replace("Days", ":");
                } else {
                    str = str + "0";
                }
                if (mm == true) {
                    str = str.replace("Months", ":");
                    try {
                        String spliter = ":";
                        values = str.split(spliter);
                        yyy = values[0];
                        mmm = values[1];
                        ddd = values[2];

                    } catch (Exception e) {
                    }
                } else {
                    try {
                        String spliter = ":";
                        values = str.split(spliter);
                        yyy = values[0];
                        mmm = "0";
                        ddd = values[1];

                    } catch (Exception e) {
                    }
                }
                int mon = Integer.parseInt(mmm);
                int day = Integer.parseInt(ddd);
                int yrs = Integer.parseInt(yyy);

                int Months = mon + (yrs * 12);
                TmpSIntTot = (amt * RoInt * Months / 1200);
                msngTotalInt = msngTotalInt + TmpSIntTot;
                TmpSIntTot = (amt * RoInt * day / 36500);
                msngTotalInt = msngTotalInt + TmpSIntTot;
            }
            return String.valueOf(Math.round(msngTotalInt));
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public void updateBankDays(String userName, String BDate, String orgBrnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select date from bankdays where date = '" + BDate + "' and brncode='" + orgBrnCode + "'").getResultList();
            if (list.isEmpty()) {
                Query update = em.createNativeQuery("insert into bankdays values ('" + BDate + "','','N','N','Y','','" + userName + "','N','Y','" + orgBrnCode + "',now(),''");
                int updateResult = update.executeUpdate();
                if (updateResult <= 0) {
                    throw new ApplicationException("Bank Days has not been Updated.");
                }
            } else {
                Query update = em.createNativeQuery("update bankdays set dayEndFlag='N',dayendFlag1='N', daybeginFlag='Y', beginUser='" + userName + "', SodTime = now()"
                        + " where bankdays.date='" + BDate + "' and Brncode='" + orgBrnCode + "'");
                int updateResult = update.executeUpdate();
                if (updateResult <= 0) {
                    throw new ApplicationException("Bank Days has not been Updated.");
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void insertIntoBankDaysHistory(String bDate, String orgBrnCode, String userName) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select dt from bankdays_hist where dt=DATE_FORMAT('" + bDate + "','%Y%m%d') and brncode='" + orgBrnCode + "'").getResultList();
            if (list.isEmpty()) {
                Query insert1 = em.createNativeQuery("insert into bankdays_hist(dt,Dbegn_By ,Dbegn_dt,remarks,brncode) VALUES(DATE_FORMAT('" + bDate + "','%Y%m%d'),'" + userName + "',now(),'ROUTINE','" + orgBrnCode + "')");
                int insert1Result = insert1.executeUpdate();
                if (insert1Result <= 0) {
                    throw new ApplicationException("Problem in inserting data into Bank Days History.");
                }
            } else {
                Query insert2 = em.createNativeQuery("UPDATE bankdays_hist SET Dbegn_By='" + userName + "' ,Dbegn_DT=now(),remarks='ROUTINE' WHERE DT=DATE_FORMAT('" + bDate + "','%Y%m%d') and brncode='" + orgBrnCode + "'");
                int insert2Result = insert2.executeUpdate();
                if (insert2Result <= 0) {
                    throw new ApplicationException("Problem in updating data into Bank Days History.");
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String crrSlrDeficitChecking(String dt) throws ApplicationException {
        try {
            double ndtl = 0, crr = 0, slr = 0, crrPer = 0, slrPer = 0;
            List reportList = hoReport.getCrrSlrPercentage("0A", dt);
            if (!reportList.isEmpty()) {
                Vector ele = (Vector) reportList.get(0);
                crrPer = Double.parseDouble(ele.get(0).toString());
                slrPer = Double.parseDouble(ele.get(1).toString());
            }

            List<DtlRegisterPojo> dtlRegList = hoReport.getDtlRegisterData("0A", dt, "R");
            List<DtlRegisterPojo> dtlTable = new ArrayList<DtlRegisterPojo>();//Actual
            DtlRegisterPojo dtlPojo = new DtlRegisterPojo();
            DtlRegisterPojo classPojo = null;
            for (int i = 0; i < dtlRegList.size(); i++) {
                classPojo = dtlRegList.get(i);
                List<RbiSossPojo> ndtlList = classPojo.getNdtlList();
                List<RbiSossPojo> crrList = classPojo.getCrrList();
                List<RbiSossPojo> slrList = classPojo.getSlrList();
                for (int j = 0; j < ndtlList.size(); j++) {
                    ndtl = ndtl + ndtlList.get(j).getAmt().doubleValue();
                }
                for (int k = 0; k < crrList.size(); k++) {
                    crr = crr + (crrList.get(k).getAmt() == null ? 0 : crrList.get(k).getAmt().doubleValue());
                }
                for (int l = 0; l < slrList.size(); l++) {
                    slr = slr + (slrList.get(l).getAmt() == null ? 0 : slrList.get(l).getAmt().doubleValue());
                }
            }
            double ndtlCrr = (ndtl * crrPer) / 100;
            double ndtlSlr = (ndtl * slrPer) / 100;
            if (ndtlCrr > crr) {
                return "Crr is going in Deficit.";
            }
            if (ndtlSlr > slr) {
                return "Slr is going in Deficit.";
            }
            System.out.println("crr:" + crr + "NdtlCrr:" + ndtlCrr + "; Slr:" + slr + "NdtlSlr:" + ndtlSlr);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "true";
    }

    /**
     * ****************************************************************************************************************************************
     * Stop Transaction process
     * ****************************************************************************************************************************************
     */
    public String dayEndProcess(String tempBd, String orgBrnCode, String userName) throws ApplicationException {
        try {
            long time = 3600000;
            List hourList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where upper(reportname)='DE INTERVAL'").getResultList();
            if (!hourList.isEmpty()) {
                Vector v1 = (Vector) hourList.get(0);
                time = Integer.parseInt(v1.get(0).toString()) * 60000;
            }
//            String dt = tempBd.substring(0, 4) + tempBd.substring(5, 7) + tempBd.substring(8, 10);

            String dt = tempBd;
            List timeList = em.createNativeQuery("select ifnull(sodtime,now()) from bankdays where date='" + dt + "' AND brncode='" + orgBrnCode
                    + "' and daybeginflag='Y'").getResultList();
            if (timeList.isEmpty()) {
                throw new ApplicationException("You do not execute day begin process for today.");
            }
            Vector v = (Vector) timeList.get(0);
            long sodTime = timeFormat.parse(v.get(0).toString()).getTime();
            long currentTime = new Date().getTime();
            if ((currentTime - sodTime) < time) {
                throw new ApplicationException("Please try after some time.");
            }
            String result = checkPendingTxn(tempBd, orgBrnCode);
            if (!result.equals("True")) {
                throw new ApplicationException(result);
            }

            List list = em.createNativeQuery("select ifnull(mendflag,'') from bankdays where date='" + dt + "' AND brncode='" + orgBrnCode
                    + "' and mendflag='N'").getResultList();
            if (!list.isEmpty()) {
                throw new ApplicationException("You have not completed your month end process. Please check it.");
            }
            List list1 = em.createNativeQuery("select code from parameterinfo_report where upper(reportname)='DAYBOOKFLAG'").getResultList();
            if (!list1.isEmpty()) {
                Vector v1 = (Vector) list1.get(0);
                if ((Integer.parseInt(v1.get(0).toString()) == 1) && (!isScrollMatch(tempBd, orgBrnCode))) {
                    throw new ApplicationException("Sorry, Scrolls does not match");
                }
            }

            if (orgBrnCode.equalsIgnoreCase("0A") || orgBrnCode.equalsIgnoreCase("90")) {
                List flagUpForCrrSlrChkList = em.createNativeQuery("select code from cbs_parameterinfo  where name = 'CRRSLR_DEFICIT_CHK'").getResultList();
                if (!flagUpForCrrSlrChkList.isEmpty()) {
                    Vector flagUpVect = (Vector) flagUpForCrrSlrChkList.get(0);
                    String flagUpForCrrSlrChk = flagUpVect.get(0).toString();
                    if (flagUpForCrrSlrChk.equalsIgnoreCase("Y")) {
                        String crrSlrDeficitChk = crrSlrDeficitChecking(dt);
                        if (!crrSlrDeficitChk.equalsIgnoreCase("true")) {
                            throw new ApplicationException(crrSlrDeficitChk);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            //Activity Alert
            int code = ftsPost43Remote.getCodeForReportName("ACTIVITY-ALERT");
            if (code == 1) {
                othMgmtRemote.activityAlertCheck(tempBd, orgBrnCode);
            }
            /*
             * For Tds Flag Of in td_accountmaster & accountmaster as per parameter is true
             */
            if (ftsPost43Remote.existInParameterInfoReport("tds_flag_of_as_per_bank")) {
                tdsFlagOfAction(orgBrnCode);
            }
            /*
             * Balancing Cash
             */
            cashUpdation(tempBd, orgBrnCode);

            /*
             * Clering updation from one day to two day
             */
            /*
             * This function is use less
             */
            // clgUpdation(tempBd, orgBrnCode);

            /*
             * Updation of dayendflag1 in bank days table and insert into
             * history table
             */
            stopTxnProcessCompletion(tempBd, orgBrnCode, userName);

            /*
             * Inactivation of users and deletion of old users
             */
            inactivateUsers(orgBrnCode, userName);

            /*
             * Updation of dayendflag in bank days table and insert into history table
             */
            dayEndProcessCompletion(orgBrnCode, userName);
            ut.commit();
            return "True";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public void tdsFlagOfAction(String brCode) throws ApplicationException {

        try {
            Query updateQuery = em.createNativeQuery("update accountmaster set TDSFLAG = 'N' where curbrcode = '" + brCode + "'  and TDSFLAG = 'Y'");
            updateQuery.executeUpdate();

            Query updateQuery2 = em.createNativeQuery("update td_accountmaster set TDSFLAG = 'N' where curbrcode = '" + brCode + "' and TDSFLAG = 'Y'");
            updateQuery2.executeUpdate();

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String checkPendingTxn(String tempBd, String orgBrnCode) throws ApplicationException {
        try {
            SimpleDateFormat xx = new SimpleDateFormat("yyyy-MM-dd");

            String date1, date2;
            String issueDate;
//            date1 = tempBd.substring(0, 10) + "00:00";
//            date2 = tempBd.substring(0, 10) + "23:59";
//            issueDate = tempBd.substring(0, 4) + tempBd.substring(5, 7) + tempBd.substring(8, 10);

            date1 = xx.format(ymd.parse(tempBd)) + " 00:00";
            date2 = xx.format(ymd.parse(tempBd)) + " 23:59";

            issueDate = tempBd;

            flg = true;
            List list = em.createNativeQuery("select AUTH from chbookmaster ch,accountmaster ac where  ac.acno=ch.acno and ch.issuedt='" + issueDate + "' and auth='N' and ac.curbrcode='" + orgBrnCode + "' ").getResultList();
            if (!list.isEmpty()) {
                return "Cheque Book Authorization Incomplete";
            }
            list = em.createNativeQuery("select DT from bill_po where auth='N' and dt='" + issueDate + "' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "PO Authorization Incomplete";
            }
            list = em.createNativeQuery("select DT from bill_dd where auth='N' and dt='" + issueDate + "' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "DD Authorization Incomplete";
            }
            list = em.createNativeQuery("select DT from bill_tpo where auth='N' and dt='" + issueDate + "' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "TPO Authorization Incomplete";
            }
            list = em.createNativeQuery("select acno from accountmaster where Authby Is Null and curbrcode='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Account Authorisation is not completed";
            }
            list = em.createNativeQuery("select acno from td_accountmaster where Authby Is Null and curbrcode='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Account Authorisation For Term Deposit is not completed";
            }
            list = em.createNativeQuery("select ae.acno from acedithistory ae,accountmaster ac where ae.acno=ac.acno and (ae.authby is null or ae.authby ='') and ac.curbrcode='" + orgBrnCode + "' union "
                    + "select ae.acno from acedithistory ae,td_accountmaster ac where ae.acno=ac.acno and (ae.authby is null or ae.authby ='') and ac.curbrcode='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Authorization of Edit Accounts Is Not Completed";
            }
            list = em.createNativeQuery("select lo.acno from loan_oldinterest lo,accountmaster ac where lo.acno=ac.acno and  lo.authby is null and ac.curbrcode='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Authorization For ROI and Limits Is Not Completed";
            }

//            list = em.createNativeQuery("select tdr.acno,voucherno,auth,tdr.authby,enterby from td_renewal_auth tdr,td_accountmaster tda where "
//                    + "auth='N' and date_format(trantime,'%Y%m%d')='" + issueDate + "' and tdr.ACNo = tda.ACNo and tda.CurBrCode='" + orgBrnCode + "'").getResultList();
//            if (!list.isEmpty()) {
//                return "TD renewal authorization till pending";
//            }
            list = em.createNativeQuery("select tdr.acno from td_renewal_auth tdr,td_accountmaster tda where "
                    + "auth='N' and date_format(trantime,'%Y%m%d')='" + issueDate + "' and tdr.ACNo = tda.ACNo and tda.CurBrCode='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "TD renewal authorization till pending";
            }

            list = em.createNativeQuery("select acno,auth,authBy from bill_obcbooking where authby is null and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Authorization For OBC,Local or Bill Purchase Is Not Completed";
            }
            list = em.createNativeQuery("select acno,auth,authBy from bill_ibcbooking where authby is null and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Authorization For IBC,Local or Bill Purchase Is Not Completed";
            }
            list = em.createNativeQuery("Select ACNO From clg_in_entry where substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Please Check Inward Clearing Entries";
            }
            list = em.createNativeQuery("select acno from accountstatus where auth='N' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Authorization For AccountStatus Is Not Completed";
            }
            list = em.createNativeQuery("Select ch.AUTHBY From chbookdetail ch,accountmaster ac Where ch.acno=ac.acno and ch.authBy is null And ch.Status = 1 and ac.curbrcode='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Pending Authorization For CHQ StopPayment To Operative Marking";
            }
            list = em.createNativeQuery("select tv.AUTH from td_vouchmst_duplicate tv,td_accountmaster td  where tv.auth = 'N' and tv.authby is null and td.curbrcode='" + orgBrnCode + "' and tv.acno=td.acno").getResultList();
            if (!list.isEmpty()) {
                return "Pending Authorization For TD Duplicate Receipt";
            }
            list = em.createNativeQuery("select AUTH from bill_obcbooking where (auth='N' or auth is null) and billtype in('DD','PO','CHQ') and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Pending Authorization For Local Bill Purchase";
            }
            List listForDBAOption = em.createNativeQuery("Select code,reportname from parameterinfo_report where reportname='DBAOPTION'").getResultList();
            if (!listForDBAOption.isEmpty()) {
                Vector vecForDBAOption = (Vector) listForDBAOption.get(0);
                if (Integer.parseInt(vecForDBAOption.get(0).toString()) == 0) {
                    dbaOption = "N";
                } else if (Integer.parseInt(vecForDBAOption.get(0).toString()) == 1) {
                    dbaOption = "Y";
                }
            } else {
                dbaOption = "N";
            }
            if (dbaOption.equals("Y")) {
                list = em.createNativeQuery("select DATE from bankdays where date='" + issueDate + "' and cashclose='Y' and Brncode='" + orgBrnCode + "'").getResultList();
                if (!list.isEmpty()) {
                    return "Cash is not closed";
                }
            }
            list = em.createNativeQuery("Select AUTHBY From bill_bpdgulc_master Where AuthBy is Null and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Pending Authorization For Local Bill Purchase";
            }
            list = em.createNativeQuery("select acno from bill_obcbooking where (auth='N' or auth is null) and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Pending Authorization For OBC";
            }
            list = em.createNativeQuery("select acno from bill_ibcbooking where (auth='N' or auth is null) and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Pending Authorization For IBC";
            }
            list = em.createNativeQuery("Select auth from bill_lc Where (auth='N' or auth is null) and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Pending Authorization For Letter of Credit";
            }
            list = em.createNativeQuery("Select auth from bill_bg Where (auth='N' or auth is null) and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Pending Authorization For Bank Guarantee";
            }
            list = em.createNativeQuery("Select l.auth from lockeracmaster l Where (auth='N' or auth is null) and l.brncode ='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Pending Authorization For Lockers Issued";
            }
            list = em.createNativeQuery("select DT from bill_ad where (auth ='N' or auth is null) and dt='" + issueDate + "' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Authorization Is Not Completed For AD";
            }
            list = em.createNativeQuery("select DT from bill_hoothers where (auth ='N' or auth is null) and dt='" + issueDate + "' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Authorization Is Not Completed For BC or BP (HO)";
            }
            list = em.createNativeQuery("Select NewAcno from cbs_cust_image_detail cb,accountmaster ac where cb.newacno=ac.acno and upper(cb.auth)='N' and ac.curbrcode='" + orgBrnCode + "' "
                    + " union "
                    + "   Select NewAcno from cbs_cust_image_detail cb,td_accountmaster ac where cb.newacno=ac.acno and upper(cb.auth)='N' and ac.curbrcode='" + orgBrnCode + "' ").getResultList();
            if (!list.isEmpty()) {
                return "Scaning Authorization Is Not Completed";
            }
            list = em.createNativeQuery("select ac.acno,closedby from acctclose_his ac,accountmaster am where ac.acno=am.acno and  auth<>'Y' and am.curbrcode='" + orgBrnCode + "' union "
                    + " select ac.acno,closedby from acctclose_his ac,td_accountmaster am where ac.acno=am.acno and  auth<>'Y' and am.curbrcode='" + orgBrnCode + "' ").getResultList();
            if (!list.isEmpty()) {
                return "Account Closing Authorization Is Not Completed";
            }
            List list29 = em.createNativeQuery("Select Distinct EmFlag from clg_in_history clg,accountmaster ac where clg.acno=ac.acno and TxnDate='" + issueDate + "' and ac.curbrcode='" + orgBrnCode + "' Union Select Distinct EmFlag from clg_in_returned clg,accountmaster ac where clg.acno=ac.acno and  TxnDate between '" + date1 + "' and '" + date2 + "' and ac.curbrcode='" + orgBrnCode + "'").getResultList();
            if (!list29.isEmpty()) {
                for (int i = 0; i < list29.size(); i++) {
                    Vector v29 = (Vector) list29.get(i);
                    List list30 = em.createNativeQuery("select Details from gl_recon where org_brnid ='" + orgBrnCode + "'").getResultList();
                    if (list30.isEmpty()) {
                        return "Inward Clearing Completion Pending for Circle Type " + v29.get(0).toString();
                    }
                }
            }
            /*
             * For Outward Clearing Register
             */
            List list31 = em.createNativeQuery("select STATUS,emflag from clg_ow_register where upper(status) in ('OPEN') AND ENTRYDATE=DATE_FORMAT('" + issueDate + "','%Y%m%d') and brncode='" + orgBrnCode + "'").getResultList();
            if (!list31.isEmpty()) {
                strVar = "Current Date O/W Clearing Register for circle Type ";
                for (int j = 0; j < list31.size(); j++) {
                    Vector v31 = (Vector) list31.get(j);
                    strVar = strVar + v31.get(1).toString() + " is still Open.";
                }
                return strVar;
            }
            /*
             * For POSTING DATE
             */
            List list32 = em.createNativeQuery("select STATUS,emflag from clg_ow_register where upper(status) not in ('POSTED','CLEARED','UNCLEARED','HELD') AND PostingDATE=DATE_FORMAT('" + issueDate + "','%Y%m%d') and brncode='" + orgBrnCode + "'").getResultList();
            if (!list32.isEmpty()) {
                strVar = "Current Date O/W Clearing Register for circle Type ";
                for (int k = 0; k < list32.size(); k++) {
                    Vector v32 = (Vector) list32.get(k);
                    strVar = strVar + v32.get(1).toString() + " is still not Posted.";
                }
                return strVar;
            }
            /*
             * For CLEARING DATE
             */
            List list33 = em.createNativeQuery("select STATUS,emflag from clg_ow_register where upper(status) not in ('CLEARED') AND clearingDATE=DATE_FORMAT('" + issueDate + "','%Y%m%d') and brncode='" + orgBrnCode + "'").getResultList();
            if (!list33.isEmpty()) {
                strVar = "Current Date O/W Clearing Register for circle Type ";
                for (int l = 0; l < list33.size(); l++) {
                    Vector v33 = (Vector) list33.get(l);
                    strVar = strVar + v33.get(1).toString() + " is still not CLEARED.";
                }
                return strVar;
            }

            list = em.createNativeQuery("select acno from tokentable_credit where (auth='N' OR auth is null) and dt='" + issueDate + "' and org_brnid='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Authorization Is Not Completed For Cash Credit Transactions.";
            }
            list = em.createNativeQuery("select acno from tokentable_debit where (auth='N' OR auth is null) and dt='" + issueDate + "' and org_brnid='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Authorization Is Not Completed For Cash Debit Transactions.";
            }
            list = em.createNativeQuery("select acno from recon_cash_d where (auth='N' OR auth is null) and dt='" + issueDate + "' and org_brnid='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Authorization Is Not Completed For Cash  Transactions.";
            }
            list = em.createNativeQuery("select acno from recon_clg_d where (auth='N' OR auth is null) and dt='" + issueDate + "' and org_brnid='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Authorization Is Not Completed For Clearing  Transactions.";
            }
            list = em.createNativeQuery("select acno from recon_trf_d where (auth='N' OR auth is null) and dt='" + issueDate + "' and org_brnid='" + orgBrnCode + "' and trandesc<>77").getResultList();
            if (!list.isEmpty()) {
                return "Authorization Is Not Completed For Transfer  Transactions.";
            }
            list = em.createNativeQuery("select acno from recon_ext_d where (auth='N' OR auth is null) and dt='" + issueDate + "'").getResultList();// Here I have to do brncode handling.Talk to ABU Sir
            if (!list.isEmpty()) {
                return "Authorization Is Not Completed For Extension Counter Transactions.";
            }
            list = em.createNativeQuery("select acno from recon_cash_d where (tokenpaidby is null OR tokenpaidby='') and auth = 'Y' and ty=1 and trantype=0 AND DT='" + issueDate + "' and org_brnid='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Token Paid Is Not Completed.";
            }
            list = em.createNativeQuery("select enterby from dds_auth_d where DT='" + issueDate + "' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Cash Deposit Authorization Is Not Completed For DDS. (In DDS Module)";
            }
            list = em.createNativeQuery("select customerid from cbs_customer_master_detail where auth='N' and primarybrcode='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                return "Customer Verification is not completed.";
            }
            List list50 = em.createNativeQuery("select acno from td_payment_auth where auth='N' and substring(acno,1,2)='" + orgBrnCode + "' "
                    + "and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.TD_AC + "'))").getResultList();
            if (!list50.isEmpty()) {
                return "Term Deposit Verification is not completed.";
            }

            List list50_1 = em.createNativeQuery("select acno from td_payment_auth where auth='N' and substring(acno,1,2)='" + orgBrnCode + "' "
                    + "and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.RECURRING_AC + "'))").getResultList();
            if (!list50_1.isEmpty()) {
                return "Recurring Deposit Verification is not completed.";
            }

            List list52 = em.createNativeQuery("select acno from npa_recon where auth='N' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list52.isEmpty()) {
                return "NPA Transaction Verification is not completed.";
            }

            List list61 = em.createNativeQuery("select acno from td_vouchmst_auth where (auth='N' or auth='') and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list61.isEmpty()) {
                return "New Receipt Creation Verification is not completed. ";
            }
            List list51 = em.createNativeQuery("select acno,Sno from loansecurity where (upper(auth) <> 'Y' OR  upper(auth) is null) and entryDate ='" + tempBd + "' and substring(AcNo,1,2) = '" + orgBrnCode + "' ORDER BY AcNo").getResultList();
            if (!list51.isEmpty()) {
                return "Loan Security Verification is not completed.";
            }
            list = em.createNativeQuery("select alphacode from branchmaster where brncode =" + Integer.parseInt(orgBrnCode)).getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Problem in getting Alpha Code of this branch");
            }

            Vector vect = (Vector) list.get(0);
            String alphaCode = vect.get(0).toString();
            if (alphaCode.equals("HO")) {
                list = em.createNativeQuery("select regfoliono from share_holder where authflag='N'").getResultList();
                if (!list.isEmpty()) {
                    return "Share Account open Verification is pending.";
                }
                String folioNo = orgBrnCode + CbsAcCodeConstant.SF_AC.getAcctCode() + "00000001";
                list = em.createNativeQuery("select shareno from share_capital_issue where auth='N' and foliono ='" + folioNo + "'").getResultList();
                if (!list.isEmpty()) {
                    return "Share Issue Autorization is pending.";
                }

                list = em.createNativeQuery("select certificateno from certificate_share where auth='N' and  status='A'").getResultList();
                if (!list.isEmpty()) {
                    return "Certificate Issue Autorization is pending.";
                }

                list = em.createNativeQuery("select * from investment_call_master where auth = 'N'").getResultList();
                if (!list.isEmpty()) {
                    return "Call Money Autorization is pending.";
                }
                /**
                 * ToDo remove this comment after certificate payment
                 * authorization
                 */
                /*
                 * list = em.createNativeQuery("select certificateno from
                 * certificate_share where auth='N' and
                 * status='C'").getResultList(); if (!list.isEmpty()) { return
                 * "Certificate Payment Autorization is pending.";
                 }
                 */
            }
            /**
             * Office Account Nil Checking
             */
            List checkList = ftsPost43Remote.checkOfficeAccount();
            if (!checkList.isEmpty()) {
                Vector element = (Vector) checkList.get(0);
                String fromno = element.get(0).toString();
                String tono = element.get(1).toString();
                fromno = orgBrnCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + ftsPost43Remote.lPading(fromno, 6, "0") + "01";
                tono = orgBrnCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + ftsPost43Remote.lPading(tono, 6, "0") + "01";

                List officeAcnoList = em.createNativeQuery("select distinct(acno) from gl_recon where acno>='" + fromno + "' and acno<='" + tono + "' order by acno").getResultList();
                if (!officeAcnoList.isEmpty()) {
                    for (int k = 0; k < officeAcnoList.size(); k++) {
                        Vector acnoElement = (Vector) officeAcnoList.get(k);
                        String officeAcno = acnoElement.get(k).toString();
                        list = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) as decimal) from gl_recon where acno='" + officeAcno + "' and auth='Y'").getResultList();
                        if (!list.isEmpty()) {
                            Vector amtElement = (Vector) list.get(0);
                            double amount = Double.parseDouble(amtElement.get(0).toString());
                            if (amount != 0) {
                                return "Please make sure that office a/c " + officeAcno + " has been balanced.";
                            }
                        }
                    }
                }
            }

            List list48 = em.createNativeQuery("select * from neft_ow_details where dt='" + issueDate + "' and (auth ='N' or auth is null) and orgbrnid='"
                    + orgBrnCode + "' and status='P'").getResultList();
            if (!list48.isEmpty()) {
                return "Authorization is not completed for Outward Transaction.";
            }

            list48 = em.createNativeQuery("select * from bill_lost where branchcode='" + orgBrnCode + "' and auth = 'N'").getResultList();
            if (!list48.isEmpty()) {
                return "Authorization is not completed for Pay Order/DD..";
            }

//            List list49 = em.createNativeQuery("select * from cts_clg_in_entry A WHERE date_format(A.DT,'%Y%m%d') = '" + issueDate + "' AND A.STATUS IN ('1','3') "
//                    + "AND CAST(A.DEST_BRANCH AS unsigned)=" + orgBrnCode + " AND SUBSTATUS<>'L'").getResultList();
//            if (!list49.isEmpty()) {
//                return "Authorization is not completed for CTS Inward Clearing.";
//            }
            List list49 = null;
            int ctsSponsor = ftsPost43Remote.getCodeForReportName("CTS-SPONSOR");
            if (ctsSponsor == 2 || ctsSponsor == 3) {
                list49 = em.createNativeQuery("select * from cts_clg_in_entry A WHERE date_format(A.DT,'%Y%m%d') = '" + issueDate + "' AND A.STATUS IN ('1','3') "
                        + "AND CAST(A.ORGN_BRANCH AS unsigned)=" + orgBrnCode + " AND SUBSTATUS<>'L'").getResultList();
            } else {
                list49 = em.createNativeQuery("select * from cts_clg_in_entry A WHERE date_format(A.DT,'%Y%m%d') = '" + issueDate + "' AND A.STATUS IN ('1','3') "
                        + "AND CAST(A.DEST_BRANCH AS unsigned)=" + orgBrnCode + " AND SUBSTATUS<>'L'").getResultList();
            }
            if (!list49.isEmpty()) {
                return "Authorization is not completed for CTS Inward Clearing.";
            }

            list49 = em.createNativeQuery("select count(*) from cts_clg_in_entry where date_format(dt,'%Y%m%d') ='" + issueDate + "' and dest_branch ='" + orgBrnCode + "' and "
                    + " status in(1,2,3,4) and schedule_no=0").getResultList();
            if (!list49.isEmpty()) {
                Vector element = (Vector) list49.get(0);
                Integer totalChq = Integer.parseInt(element.get(0).toString());

                if (totalChq > 0) {
                    list49 = em.createNativeQuery("select count(*) from recon_clg_d where date_format(dt,'%Y%m%d') ='" + issueDate + "' and dest_brnid ='" + orgBrnCode + "' and "
                            + " details='IW CLG Completion' and trandesc=65").getResultList();
                    if (list49.isEmpty()) {
                        return "Completion is Pending for CTS Inward Clearing.";
                    }
                }
            }

            List list54 = em.createNativeQuery("select * from cbs_id_merge_auth WHERE auth = 'N' AND orgn_br_code = '" + orgBrnCode + "'").getResultList();
            if (!list54.isEmpty()) {
                return "Authorization is not completed for Id Merging.";
            }

            list54 = em.createNativeQuery("select * from mb_subscriber_tab where substring(acno,1,2)='" + orgBrnCode + "' and auth='N'").getResultList();
            if (!list54.isEmpty()) {
                return "Authorization is not completed for SMS Module.";
            }

            list54 = em.createNativeQuery("select acno from pm_scheme_reg_details where auth = 'N' and "
                    + "txn_br_code = '" + orgBrnCode + "'").getResultList();
            if (!list54.isEmpty()) {
                return "Authorization is not completed for Social Security Schemes.";
            }

            list54 = em.createNativeQuery("select acno from prizm_card_master where auth = 'N' and "
                    + "substring(acno,1,2) = '" + orgBrnCode + "'").getResultList();
            if (!list54.isEmpty()) {
                return "Authorization is not completed for ATM registration.";
            }

            List list55 = em.createNativeQuery("select from_acno from standins_transmaster_auth where auth = 'N' and "
                    + "org_brnid = '" + orgBrnCode + "'").getResultList();
            if (!list55.isEmpty()) {
                return "Authorization is not completed for Standing Instruction.";
            }
            //Addition for internet banking.
            list55 = em.createNativeQuery("select acno from ib_request where request_status='NEW' "
                    + "and substring(acno,1,2)='" + orgBrnCode + "' and cbs_request_dt='" + issueDate + "'").getResultList();
            if (!list55.isEmpty()) {
                return "Authorization is not completed for Internet Banking Request.";
            }

            List list56 = em.createNativeQuery("select auth from tds_docdetail where (auth Is Null or auth='N')and orgBrnid = '" + orgBrnCode + "'").getResultList();
            if (!list56.isEmpty()) {
                return "Authorization is not completed for Tds documents.";
            }
            List list57 = em.createNativeQuery("select auth from money_exchange_details where (auth Is Null or auth='N')and brCode = '" + orgBrnCode + "' and entrydate='" + issueDate + "'").getResultList();
            if (!list57.isEmpty()) {
                return "Authorization is not completed for Money Exchange.";
            }
            //--------------Manish 30/12/2016
//            List list62 = em.createNativeQuery("select Header_MessageId from cpsms_batch_detail where Entry_date = '" + issueDate + "' and Cbs_Status in ('01','06')").getResultList();
            //--------------
            //--------------Daya 05/06/2017
            List list62 = em.createNativeQuery("select cb.Header_MessageId from cpsms_batch_detail cb ,cpsms_detail cd where cb.Entry_date = cd.Entry_date"
                    + " and cb.CPSMS_Batch_No=cd.CPSMS_Batch_No and cb.Header_MessageId=cd.Header_MessageId "
                    + " and cd.Account_Type='DR' and SUBSTRING(cd.C6021_Agency_Bank_Acno, 1, 2)='" + orgBrnCode + "'"
                    + " and cb.Entry_date <= '" + issueDate + "'and cb.Cbs_Status in ('01','06')").getResultList();
            //----------------
            if (!list62.isEmpty()) {
                return "Authorization is pending for CPSMS batch.";
            }

            List listSecCloseAuth = em.createNativeQuery("select auth from investment_security_close_auth where (auth Is Null or auth='N')and org_brnid = '" + orgBrnCode + "'").getResultList();
            if (!listSecCloseAuth.isEmpty()) {
                return "Authorization is not completed for Security Close.";
            }

            List listFdrCreateAuth = em.createNativeQuery("select auth from investment_fdr_creation_auth where (auth Is Null or auth='N')and org_brnid = '" + orgBrnCode + "'").getResultList();
            if (!listFdrCreateAuth.isEmpty()) {
                return "Authorization is not completed for Investment FDR Creation.";
            }

            listFdrCreateAuth = em.createNativeQuery("select acno from atm_card_master where substring(acno,1,2)='" + orgBrnCode + "' and "
                    + "verify='N' and date_format(lastUpdateDate,'%Y%m%d')='" + issueDate + "'").getResultList();
            if (!listFdrCreateAuth.isEmpty()) {
                return "Authorization is pending for ATM Card Verification.";
            }

            List list63 = em.createNativeQuery("select * from investment_fdr_close_renew_auth where (auth Is Null or auth='N')and org_brnid = '" + orgBrnCode + "'").getResultList();
            if (!list63.isEmpty()) {
                return "Authorization is pending for FDR Closing.";
            }

            String recMsg = ftsPost43Remote.receiptNotCreated(issueDate, orgBrnCode);
            if (!recMsg.equalsIgnoreCase("true")) {
                return recMsg;
            }
            List list58 = em.createNativeQuery("select Instcode,sno from chbookmaster_amtwise where  brncode = '" + orgBrnCode + "' AND auth = 'N' and  Dt='" + issueDate + "' ORDER BY sno").getResultList();
            if (!list58.isEmpty()) {
                return "Authorization is pending for PO/DD/AD Book .";
            }
            List list64 = em.createNativeQuery("select * from cheque_purchase where auth = 'N' and substring(AccountNo,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list64.isEmpty()) {
                return "Authorization is pending for Cheque Purchase.";
            }
            list = em.createNativeQuery("select acno from recon_trf_d where (auth='N' OR auth is null) and org_brnid='" + orgBrnCode + "' and trandesc = 77").getResultList();
            if (!list.isEmpty()) {
                return "Authorization is not completed for Back date Transfer  Transactions.";
            }
            List locSurAuth = em.createNativeQuery("select cast(cabno as unsigned),lockertype,lockerno,acno,enterBy from locker_surrender_auth where "
                    + " brncode = '" + orgBrnCode + "' and auth ='N'").getResultList();
            if (!locSurAuth.isEmpty()) {
                return " Authorization is pending for Locker Surrender.";
            }
            //imps
            List list53 = em.createNativeQuery("select Remitter_Acc_No from cbs_imps_ow_request where substring(Remitter_Acc_No,1,2)='" + orgBrnCode + "' and Request_Status='S' and cast(dt as date)='" + issueDate + "'").getResultList();
            if (!list53.isEmpty()) {
                return "Authorization is pending for imps request";
            }
//            List list65 =em.createNativeQuery("select MndtId from mms_upload_xml_detail where Upload_Date='"+issueDate+"' and Mandate_Status='' and Accept=''").getResultList();
//            if(!list65.isEmpty()){
//                return "Mms mandate verification is pending";
//            }
            
            List bgList = em.createNativeQuery("select * from cbs_bank_guarantee_details where auth = 'N' and action  in ('I','V','R','C') and date_format(EntryDt,'%Y%m%d') = '" + issueDate + "' and brncode='" + orgBrnCode + "'").getResultList();
            if (!bgList.isEmpty()) {
                return "Authorization is pending for Bank Guarantee";
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public boolean isScrollMatch(String tempBd, String orgBrnCode) throws ApplicationException {
        try {
            String table, issueDate;
            List list2;
            BigDecimal clgTranCr = new BigDecimal(0), trfTranCr = new BigDecimal(0), clgTranDr = new BigDecimal(0), trfTranDr = new BigDecimal(0);
//            issueDate = tempBd.substring(0, 4) + tempBd.substring(5, 7) + tempBd.substring(8, 10);
            issueDate = tempBd;

            List list1 = em.createNativeQuery("select distinct acctnature from accounttypemaster").getResultList();
            if (list1.isEmpty()) {
                throw new ApplicationException("Data does not exist in accounttypemaster");
            }
            for (int i = 0; i < list1.size(); i++) {
                Vector v1 = (Vector) list1.get(i);
                table = CbsUtil.getReconTableName(v1.get(0).toString());
                if (table.equalsIgnoreCase("")) {
                    throw new ApplicationException("Account nature does not exist");
                }
                if (v1.get(0).toString().equalsIgnoreCase(CbsConstant.FIXED_AC) || v1.get(0).toString().equalsIgnoreCase(CbsConstant.MS_AC)) {
                    list2 = em.createNativeQuery("select trantype,cast(sum(cramt) as decimal(25,2)),cast(sum(dramt)as decimal(25,2)) from "
                            + table + " where dt = '" + issueDate + "' and auth = 'Y' and closeflag is null and org_brnid='" + orgBrnCode
                            + "' and substring(acno,3,2) in (select acctcode from accounttypemaster where acctnature='" + v1.get(0).toString()
                            + "') group by trantype ").getResultList();
                } else {
                    list2 = em.createNativeQuery("select trantype,cast(sum(cramt)as decimal(25,2)),cast(sum(dramt)as decimal(25,2)) from "
                            + table + " where dt = '" + issueDate + "' and auth = 'Y' and org_brnid='" + orgBrnCode + "' and substring(acno,3,2) "
                            + " in (select acctcode from accounttypemaster where acctnature='" + v1.get(0).toString() + "')group by trantype ").getResultList();
                }
                for (int j = 0; j < list2.size(); j++) {
                    Vector v2 = (Vector) list2.get(j);
                    int tranType = Integer.parseInt(v2.get(0).toString());
                    if (tranType == 1) {
                        clgTranCr = clgTranCr.add(new BigDecimal(v2.get(1).toString()));
                        clgTranDr = clgTranDr.add(new BigDecimal(v2.get(2).toString()));
                    } else if (tranType == 2 || tranType == 8 || tranType == 6) {
                        trfTranCr = trfTranCr.add(new BigDecimal(v2.get(1).toString()));
                        trfTranDr = trfTranDr.add(new BigDecimal(v2.get(2).toString()));
                    }
                }
            }
            BigDecimal clgRs = clgTranCr.subtract(clgTranDr);
            BigDecimal tranRs = trfTranCr.subtract(trfTranDr);

            if (clgRs.abs().compareTo(new BigDecimal(0.10)) > 0) {
                throw new ApplicationException("Please Check the Clearing Scroll. There are some mismatch.");
            } else if (tranRs.abs().compareTo(new BigDecimal(0.10)) > 0) {
                throw new ApplicationException("Please Check the Transfer Scroll. There are some mismatch.");
            } else {
                return true;
            }
            /*if (clgTranCr.compareTo(clgTranDr) != 0) {
             throw new ApplicationException("Please Check the Clearing Scroll. There are some mismatch.");
             }else if(trfTranCr.compareTo(trfTranDr) != 0){
             throw new ApplicationException("Please Check the Transfer Scroll. There are some mismatch.");
             }else{
             return true;
             }*/
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /*public boolean isDayBookMatch(String tempBd, String orgBrnCode) throws ApplicationException {
     try {
     String table, issueDate;
     List list2;
     double cshTranCr = 0, clgTranCr = 0, trfTranCr = 0, cshTranDr = 0, clgTranDr = 0, trfTranDr = 0, opbal = 0;
     issueDate = tempBd.substring(0, 4) + tempBd.substring(5, 7) + tempBd.substring(8, 10);
     List list1 = em.createNativeQuery("select distinct acctnature from accounttypemaster").getResultList(); //where acctnature not in('MS','DL','NP','SS')
     if (list1.isEmpty()) {
     throw new ApplicationException("Data does not exist in accounttypemaster");
     }
     for (int i = 0; i < list1.size(); i++) {
     Vector v1 = (Vector) list1.get(i);
     table = CbsUtil.getReconTableName(v1.get(0).toString());
     if (table.equalsIgnoreCase("")) {
     throw new ApplicationException("Account nature does not exist");
     }
     if (v1.get(0).toString().equalsIgnoreCase(CbsConstant.FIXED_AC) || v1.get(0).toString().equalsIgnoreCase(CbsConstant.MS_AC)) {
     list2 = em.createNativeQuery("select trantype,isnull(sum(isnull(cramt,0)),0),isnull(sum(isnull(dramt,0)),0) from "
     + table + " where dt = '" + issueDate + "' and auth = 'Y' and closeflag is null and org_brnid='" + orgBrnCode
     + "' group by trantype ").getResultList();
     } else {
     list2 = em.createNativeQuery("select trantype,isnull(sum(isnull(cramt,0)),0),isnull(sum(isnull(dramt,0)),0) from "
     + table + " where dt = '" + issueDate + "' and auth = 'Y' and org_brnid='" + orgBrnCode + "' group by trantype ").getResultList();
     }
     for (int j = 0; j < list2.size(); j++) {
     Vector v2 = (Vector) list2.get(j);
     if (Integer.parseInt(v2.get(0).toString()) == 0) {
     cshTranCr = cshTranCr + Double.parseDouble(v2.get(1).toString());
     cshTranDr = cshTranDr + Double.parseDouble(v2.get(2).toString());
     } else if (Integer.parseInt(v2.get(0).toString()) == 1) {
     clgTranCr = clgTranCr + Double.parseDouble(v2.get(1).toString());
     clgTranDr = clgTranDr + Double.parseDouble(v2.get(2).toString());
     } else if (Integer.parseInt(v2.get(0).toString()) == 2 || Integer.parseInt(v2.get(0).toString()) == 8
     || Integer.parseInt(v2.get(0).toString()) == 6) {//  added By Shipra (Condition For 8 or 6)
     trfTranCr = trfTranCr + Double.parseDouble(v2.get(1).toString());
     trfTranDr = trfTranDr + Double.parseDouble(v2.get(2).toString());
     }
     }
     }
     double totalCr = cshTranCr + clgTranCr + trfTranCr;
     double totalDr = cshTranDr + clgTranDr + trfTranDr;
     List list3 = em.createNativeQuery("select isnull(opamt,0) from opcash where tdate = (select max(tdate) from opcash where tdate < '"
     + issueDate + "') and brncode='" + orgBrnCode + "'").getResultList();
     if (!list3.isEmpty()) {
     Vector v3 = (Vector) list3.get(0);
     opbal = Double.parseDouble(v3.get(0).toString());
     }
     double grandTotalCr = totalCr + opbal;
     double grandTotalDr = opbal + cshTranCr - cshTranDr + totalDr;
     if (grandTotalCr != grandTotalDr) {
     return false;
     }

     return true;
     } catch (Exception e) {
     throw new ApplicationException(e.getMessage());
     }
     }
     */
    public void cashUpdation(String tempBd, String orgBrnCode) throws ApplicationException {
        try {
            double MainBrCash, TotCashIn, TotCashOp, TotalCash, ExtCash;
//            String issueDate = tempBd.substring(0, 4) + tempBd.substring(5, 7) + tempBd.substring(8, 10);
            String issueDate = tempBd;

            List list1 = em.createNativeQuery("select ifnull(amt,0) From cashinhand where ldate=(select max(ldate) from cashinhand where ldate< '"
                    + issueDate + "' and brncode='" + orgBrnCode + "') and brncode='" + orgBrnCode + "'").getResultList();
            if (!list1.isEmpty()) {
                Vector v1 = (Vector) list1.get(0);
                TotCashIn = Double.parseDouble(v1.get(0).toString());
            } else {
                TotCashIn = 0;
            }
            List list2 = em.createNativeQuery("select ifnull(opamt,0) From opcash where tdate=(select max(tdate) from opcash where tdate < '"
                    + issueDate + "' and brncode='" + orgBrnCode + "') and brncode='" + orgBrnCode + "'").getResultList();
            if (!list2.isEmpty()) {
                Vector v2 = (Vector) list2.get(0);
                TotCashOp = Double.parseDouble(v2.get(0).toString());
            } else {
                TotCashOp = 0;
            }
            List list3 = em.createNativeQuery("select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from gl_recon where dt='" + issueDate + "' and "
                    + "substring(acno,3,10)='" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "00550201' and org_brnid='" + orgBrnCode + "'").getResultList();
            Vector v3 = (Vector) list3.get(0);
            ExtCash = Double.parseDouble(v3.get(0).toString());

            List list4 = em.createNativeQuery("select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from recon_cash_d where dt='" + issueDate + "' "
                    + "and trantype=0 AND substring(ACNO,3,10) NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE PURPOSE LIKE '%CASH IN HAND%') and "
                    + "org_brnid='" + orgBrnCode + "' ").getResultList();
            if (!list4.isEmpty()) {
                Vector v4 = (Vector) list4.get(0);
                MainBrCash = Double.parseDouble(v4.get(0).toString());
            } else {
                MainBrCash = 0;
            }
            TotalCash = 0;
            TotalCash = TotCashOp + MainBrCash;

//            MainBrCash_2 = 0;
//            List list5 = em.createNativeQuery("select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from tokentable_credit where auth='Y' and "
//                    + "tokenpaid='Y' and dt='" + issueDate + "' AND substring(ACNO,3,10) NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE PURPOSE LIKE "
//                    + "'%CASH IN HAND%') and org_brnid='" + orgBrnCode + "'").getResultList();
//            if (!list5.isEmpty()) {
//                Vector v5 = (Vector) list5.get(0);
//                MainBrCash_2 = Double.parseDouble(v5.get(0).toString());
//            } else {
//                MainBrCash_2 = 0;
//            }
//            TotalCash = TotalCash + MainBrCash_2;
//            MainBrCash = MainBrCash + MainBrCash_2;
            List list6 = em.createNativeQuery("select opamt from opcash where tdate='" + issueDate + "' and brncode='" + orgBrnCode + "'").getResultList();
            if (!list6.isEmpty()) {
                Query update1 = em.createNativeQuery("update opcash set opamt=" + TotalCash + " where tdate='" + issueDate + "' and brncode='" + orgBrnCode + "'");
                update1.executeUpdate();
            } else {
                Query insert1 = em.createNativeQuery("insert into opcash (opamt,tdate,brncode) values(" + TotalCash + ",'" + issueDate + "','" + orgBrnCode + "')");
                insert1.executeUpdate();
            }
            TotalCash = 0;
            TotalCash = TotCashIn + ExtCash + MainBrCash;
            List list7 = em.createNativeQuery("select amt from cashinhand where ldate='" + issueDate + "' and brncode='" + orgBrnCode + "'").getResultList();
            if (!list7.isEmpty()) {
                Query update2 = em.createNativeQuery("update cashinhand set amt=" + TotalCash + " where ldate='" + issueDate + "' and brncode='" + orgBrnCode + "'");
                update2.executeUpdate();
            } else {
                Query insert2 = em.createNativeQuery("insert into cashinhand (amt,ldate,brncode) values(" + TotalCash + ",'" + issueDate + "','" + orgBrnCode + "')");
                insert2.executeUpdate();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void clgUpdation(String tempBd, String orgBrnCode) throws ApplicationException {
        String issueDate = tempBd.substring(0, 4) + tempBd.substring(5, 7) + tempBd.substring(8, 10);
        try {
            Query insert1 = em.createNativeQuery("insert into clg_ow_2day(clg.Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,Txnstatus,TxnInstDate ,"
                    + "TxnBankName,TxnBankAddress,AreaCode,BnkCode,BranchCode,remarks,ReasonForCancel,vtot,EMFlag,EnterBy,AuthBy,BillType,AlphaCode,"
                    + "BcBpNo,Fyear,dt,trandesc) select clg.Acno,clg.TxnMode,clg.TxnType,clg.TxnDate,clg.TxnInstNo,clg.TxnInstAmt,'D',clg.TxnInstDate,"
                    + "clg.TxnBankName,clg.TxnBankAddress,clg.AreaCode,clg.BnkCode,clg.BranchCode,clg.remarks,clg.ReasonForCancel,clg.vtot,clg.EMFlag,"
                    + "clg.EnterBy,clg.AuthBy,clg.BillType,clg.AlphaCode,clg.BcBpNo,clg.Fyear,clg.dt,clg.trandesc from clg_ow_entry clg,accountmaster ac "
                    + "where ac.acno=clg.acno and txndate='" + issueDate + "' and ac.curbrcode ='" + orgBrnCode + "'");
            insert1.executeUpdate();
            Query delete1 = em.createNativeQuery("Delete clg_ow_entry from clg_ow_entry clg,accountmaster ac where ac.acno=clg.acno and txndate='"
                    + issueDate + "' and ac.curbrcode ='" + orgBrnCode + "'");
            delete1.executeUpdate();
            /*
             * for student clearing
             */
            Query insert2 = em.createNativeQuery("Insert stud_clg_ow_2day select clg.* from stud_clg_ow_entry clg,accountmaster ac where ac.acno=clg.acno "
                    + "and txndate='" + issueDate + "' and ac.curbrcode ='" + orgBrnCode + "'");
            insert2.executeUpdate();
            Query delete2 = em.createNativeQuery("Delete stud_clg_ow_entry from stud_clg_ow_entry clg,accountmaster ac where ac.acno=clg.acno and "
                    + "txndate='" + issueDate + "' and ac.curbrcode ='" + orgBrnCode + "'");
            delete2.executeUpdate();
            /*
             * Delete Qrys. For ChequeBook Tables
             */
            Query delete3 = em.createNativeQuery("delete chbook_sb from chbook_sb sb,accountmaster ac where sb.acno=ac.acno and sb.statusFlag='U' and "
                    + "sb.lastupdatedt='" + issueDate + "' and ac.curbrcode ='" + orgBrnCode + "'");
            delete3.executeUpdate();
            Query delete4 = em.createNativeQuery("delete chbook_ca from chbook_ca sb,accountmaster ac where sb.acno=ac.acno and sb.statusFlag='U' and "
                    + "sb.lastupdatedt='" + issueDate + "' and ac.curbrcode ='" + orgBrnCode + "'");
            delete4.executeUpdate();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void stopTxnProcessCompletion(String tempBd, String orgBrnCode, String userName) throws ApplicationException {
//        String issueDate = tempBd.substring(0, 4) + tempBd.substring(5, 7) + tempBd.substring(8, 10);
        String issueDate = tempBd;
        try {
            Query update1 = em.createNativeQuery("Update bankdays Set DayEndFlag1='Y' Where Date='" + issueDate + "' and Brncode='" + orgBrnCode + "'");
            int rs = update1.executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in data updation in bank days.");
            }
            List list1 = em.createNativeQuery("select dt from bankdays_hist where dt=DATE_FORMAT('" + issueDate + "','%Y%m%d') and brncode='" + orgBrnCode + "'").getResultList();
            if (list1.isEmpty()) {
                Query insert = em.createNativeQuery("insert into bankdays_hist (dt,Dend1_By ,Dend1_DT,remarks,brncode) VALUES(DATE_FORMAT('" + issueDate + "','%Y%m%d'),'" + userName + "',now(),'ROUTINE','" + orgBrnCode + "')");
                insert.executeUpdate();
            } else {
                Query update = em.createNativeQuery("UPDATE bankdays_hist SET Dend1_By='" + userName + "' ,Dend1_DT=now(),remarks='ROUTINE' WHERE DT=DATE_FORMAT('" + issueDate + "','%Y%m%d') and brncode='" + orgBrnCode + "'");
                update.executeUpdate();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * **************************************************************************************************************************************************
     ************************************************* Day end Process
     * ********************************************************************
     * **************************************************************************************************************************************************
     */
    public void dayEndProcessCompletion(String orgBrnCode, String userName) throws ApplicationException {
        String TmpDt1 = "";
        try {
            List list1 = em.createNativeQuery("select min(date) from bankdays where dayendflag='N' and Brncode='" + orgBrnCode + "'").getResultList();
            if (!list1.isEmpty()) {
                Vector v1 = (Vector) list1.get(0);
                TmpDt1 = v1.get(0).toString();
                Query update1 = em.createNativeQuery("update bankdays set dayEndflag='Y',Enduser='" + userName + "', EodTime = now() where bankdays.date='"
                        + TmpDt1 + "' and Brncode='" + orgBrnCode + "'");
                int rs = update1.executeUpdate();
                if (rs <= 0) {
                    throw new ApplicationException("Problem in data updation in bank days.");
                }
            }
            List list2 = em.createNativeQuery("select dt from bankdays_hist where dt=DATE_FORMAT('" + TmpDt1 + "','%Y%m%d') and Brncode='"
                    + orgBrnCode + "'").getResultList();
            if (list2.isEmpty()) {
                Query insert1 = em.createNativeQuery("insert into bankdays_hist (dt,Dend_By ,Dend_DT,remarks,brncode) VALUES(DATE_FORMAT('"
                        + TmpDt1 + "','%Y%m%d'),'" + userName + "',now(),'ROUTINE','" + orgBrnCode + "')");
                insert1.executeUpdate();
            } else {
                Query update2 = em.createNativeQuery("UPDATE bankdays_hist SET Dend_By='" + userName + "' ,Dend_DT=now(),remarks='ROUTINE' "
                        + "WHERE DT=DATE_FORMAT('" + TmpDt1 + "','%Y%m%d') and Brncode='" + orgBrnCode + "'");
                update2.executeUpdate();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void inactivateUsers(String orgBrnCode, String userName) throws ApplicationException {
        try {
            List list1 = em.createNativeQuery("select userid from securityinfo where userid <> '" + userName + "' and login='N' and Brncode='" + orgBrnCode + "' and levelid<>6").getResultList();
            if (!list1.isEmpty()) {
                throw new ApplicationException("Some users are already logged in. So please check and then try again.");
            }
            Query update1 = em.createNativeQuery("update securityinfo set status='I' where status='A' and levelid not in (5,6,1,2,8) and userid<>'system'"
                    + " and Brncode='" + orgBrnCode + "'");
            update1.executeUpdate();
            Query update2 = em.createNativeQuery("update securityinfo set status='C' where status not in ('C','D') and levelid not in (5,6) AND "
                    + "userid<>'system' and TIMESTAMPDIFF(day,DATE_FORMAT(lastlogindate,'%Y-%m-%d'),now())>31 and Brncode='" + orgBrnCode + "'");
            update2.executeUpdate();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * *************************************************************************************************************************************************
     */
    /**
     * ************************MonthEndProcess***************
     */
    /**
     * *************************************************************************************************************************************************
     */
    public String monthEndProcess(String monthEndName, String orgnBrCode, String tempBd, String userName) throws ApplicationException {
        tempBd = tempBd.substring(6) + tempBd.substring(3, 5) + tempBd.substring(0, 2);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String monthDate = getMinMonthDate(orgnBrCode);
            if (monthDate.equals("")) {
                throw new ApplicationException("Data does not exist in bankdays for this month" + monthEndName);
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(ymd.parse(monthDate));

            cal.set(Calendar.DATE, cal.getMinimum(Calendar.DATE));
            String fromDate = ymd.format(cal.getTime());

            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
            String toDate = ymd.format(cal.getTime());

            monthEndForRD(fromDate, toDate, orgnBrCode);
            //monthEndProcessCompletion(orgnBrCode, Months.getMonthValue(monthEndName.toUpperCase()), cal.get(Calendar.YEAR), tempBd, userName, monthEndName);
            monthEndProcessCompletion(orgnBrCode, monthDate, tempBd, userName, monthEndName);
            //Charges On Pending Loan SI
            if ((orgnBrCode.equalsIgnoreCase("90") || orgnBrCode.equalsIgnoreCase("0A"))) {
                int lnSiPending = ftsPost43Remote.getCodeForReportName("PEDING-LOAN-SI-CHARGE");
                if (lnSiPending == 1) {
                    pendingLoanSICharge(tempBd, orgnBrCode, userName);
                }
            }
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return "True";
    }

    /**
     * ************************Month End For RD*************************
     */
    public void monthEndForRD(String fromDate, String toDate, String orgBrnCode) throws ApplicationException {
        String acno, openingdt, rdmatdt;
        float balance, days, interest;
        Long cal1 = Long.parseLong(fromDate);
        Long cal2 = Long.parseLong(toDate);
        try {
//            List list1 = em.createNativeQuery("select r.acno,ifnull(sum(ifnull(r.cramt,0)),0) - ifnull(sum(ifnull(r.dramt,0)),0),"
//                    + "DATE_FORMAT(a.openingdt,'%Y%m%d'),DATE_FORMAT(a.rdmatdate,'%Y%m%d'),ifnull(result.interest,0)from rdrecon  r "
//                    + "left join accountmaster a on r.acno=a.acno left join (select acno,sum(interest) as interest from rd_interesthistory "
//                    + "where todt <= '" + toDate + "' group by acno) as result (ac_no,interest) on ac_no=r.acno where a.accstatus <> 9 and "
//                    + "r.dt<='" + toDate + "' and r.trantype <> 8 and substring(r.acno,1,2) ='" + orgBrnCode + "' group by r.acno,a.openingdt,"
//                    + "a.rdmatdate,result.interest").getResultList();
            List list1 = em.createNativeQuery("select r.acno,ifnull(sum(ifnull(r.cramt,0)),0) - ifnull(sum(ifnull(r.dramt,0)),0),"
                    + "DATE_FORMAT(a.openingdt,'%Y%m%d'),DATE_FORMAT(a.rdmatdate,'%Y%m%d'),ifnull(result.interest,0)from rdrecon  r "
                    + "left join accountmaster a on r.acno=a.acno left join (select acno,sum(interest) as interest from rd_interesthistory "
                    + "where todt <= '" + toDate + "' group by acno) as result on result.acno=r.acno where a.accstatus <> 9 and "
                    + "r.dt<='" + toDate + "' and r.trantype <> 8 and substring(r.acno,1,2) ='" + orgBrnCode + "' group by r.acno,a.openingdt,"
                    + "a.rdmatdate,result.interest").getResultList();
            if (!list1.isEmpty()) {
                for (int i = 0; i < list1.size(); i++) {
                    Vector v1 = (Vector) list1.get(i);
                    acno = v1.get(0).toString();

                    balance = Float.parseFloat(v1.get(1).toString());
                    openingdt = v1.get(2).toString();
                    rdmatdt = v1.get(3).toString();
                    interest = Float.parseFloat(v1.get(4).toString());

                    Long cal3 = Long.parseLong(openingdt);
                    Long cal4 = Long.parseLong(rdmatdt);
                    if (cal3.compareTo(cal1) < 0 || cal3.compareTo(cal1) == 0) {
                        if (cal4.compareTo(cal1) < 0 || cal4.compareTo(cal1) == 0) {
                            days = 0;
                        } else if (cal4.compareTo(cal1) > 0 && (cal4.compareTo(cal2) < 0 || cal4.compareTo(cal2) == 0)) {
                            days = CbsUtil.dayDiff(ymd.parse(fromDate), ymd.parse(rdmatdt));
                        } else if (cal4.compareTo(cal2) > 0) {
                            days = CbsUtil.datePart("D", toDate);
                        } else {
                            days = 0;
                        }
                    } else if (cal3.compareTo(cal1) > 0 && (cal3.compareTo(cal2) < 0 || cal3.compareTo(cal2) == 0)) {
                        if (cal4.compareTo(cal2) < 0 || cal4.compareTo(cal2) == 0) {
                            days = CbsUtil.dayDiff(ymd.parse(fromDate), ymd.parse(rdmatdt));
                        } else if (cal4.compareTo(cal2) > 0) {
                            days = CbsUtil.dayDiff(ymd.parse(openingdt), ymd.parse(toDate));
                        } else {
                            days = 0;
                        }
                    } else {
                        days = 0;
                    }
                    if (balance < 0) {
                        balance = 0;
                    }
                    if (days != 0) {
                        balance = balance + interest;
                    } else {
                        balance = 0;
                    }
                    Query insert1 = em.createNativeQuery("INSERT INTO rd_product (acno,dt,balance,days,monthlastdt) values ('" + acno + "',"
                            + "DATE_FORMAT('" + toDate + "','%Y%m%d')," + balance + "," + days + ",'" + toDate + "')");
                    int insert1Result = insert1.executeUpdate();
                    if (insert1Result <= 0) {
                        throw new ApplicationException("Problem In Insertion of Rd_Product for Acno :- " + acno);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * **************************QUERY FOR UPDATING Month End Flag*************
     */
    public void monthEndProcessCompletion(String orgBrnCode, String mEndFlag, String tempBd, String userName, String Mon2) throws ApplicationException {
        try {
//            Query update1 = em.createNativeQuery("update bankdays set mendflag='Y' where substring(date,5,2)='" + mon + "' and substring(date,1,4)='"
//                    + TempYear + "' and Brncode='" + orgBrnCode + "'");
            Query update1 = em.createNativeQuery("update bankdays set mendflag='Y' where date='" + mEndFlag + "' and Brncode='" + orgBrnCode + "'");
            int rs = update1.executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in data updation in bank days.");
            }
//            Query delete1 = em.createNativeQuery("delete from bankdays where date<(select max(date) from bankdays where mendflag='Y') and Brncode='"
//                    + orgBrnCode + "'");
//            delete1.executeUpdate();
            Query delete1 = em.createNativeQuery("delete from bankdays where date<'" + tempBd + "' and Brncode='" + orgBrnCode + "'");
            delete1.executeUpdate();
            List list1 = em.createNativeQuery("select DATE_FORMAT(dt,'%Y%m%d') from bankdays_hist where dt=DATE_FORMAT('" + tempBd + "','%Y%m%d')").getResultList();
            if (list1.isEmpty()) {
                Query insert1 = em.createNativeQuery("insert into bankdays_hist (dt,Mend_By ,Mend_dt,remarks,for_month,brncode) "
                        + "VALUES(DATE_FORMAT('" + tempBd + "','%Y%m%d'),'" + userName + "',now(),'ROUTINE','" + Mon2 + "','" + orgBrnCode + "')");
                insert1.executeUpdate();
            } else {
                Query update2 = em.createNativeQuery("UPDATE bankdays_hist SET Mend_By='" + userName + "' ,Mend_DT=now(),remarks='ROUTINE',"
                        + "for_month='" + Mon2 + "' WHERE DT=DATE_FORMAT('" + tempBd + "','%Y%m%d')");
                update2.executeUpdate();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * ****************************************************************************************************************************************
     */
    /*
     * Year End Code Begin
     */
    /**
     * ****************************************************************************************************************************************
     */
    public String yearEndProcess(String tempBd, String orgBrnCode, String userName) throws ApplicationException {
        String glAcno, dt1, dt2;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

//            String tempDate = tempBd.substring(0, 4) + tempBd.substring(5, 7) + tempBd.substring(8, 10);

            String tempDate = tempBd;


//            List list2 = em.createNativeQuery("SELECT ifnull(CODE,0) FROM parameterinfo_report WHERE upper(REPORTNAME) = 'YEAREND'").getResultList();
//            if (list2.isEmpty()) {
//                throw new ApplicationException("Please Enter 1 YearEnd in Reports");
//            }
//            Vector v2 = (Vector) list2.get(0);
//            int yearEndSt = Integer.parseInt(v2.get(0).toString());
//
//            if (yearEndSt != 1) {
//                throw new ApplicationException("Please Enter 1 YearEnd in Reports");
//            } 
            List list3 = em.createNativeQuery("select F_YEAR from yearend where mindate in (select min(mindate) from yearend where YearEndFlag='N' "
                    + "and brncode='" + orgBrnCode + "') and brncode='" + orgBrnCode + "'").getResultList();
            if (list3.isEmpty()) {
                throw new ApplicationException("Data does not exist in yearend");
            }
            Vector v3 = (Vector) list3.get(0);
            int prFyear = Integer.parseInt(v3.get(0).toString());

            List list4 = em.createNativeQuery("select F_YEAR from yearend where mindate in (select min(mindate) from yearend where YearEndFlag='N' "
                    + "and F_Year=" + prFyear + "+1 and brncode='" + orgBrnCode + "') and brncode='" + orgBrnCode + "'").getResultList();
            if (list4.isEmpty()) {
                dt1 = prFyear + 1 + "0401";
                dt2 = prFyear + 2 + "0331";

                Query insert1 = em.createNativeQuery("insert into yearend (Mindate,Maxdate,YearEndFlag,F_Year,brncode) values('" + dt1 + "','" + dt2
                        + "','N'," + prFyear + "+1,'" + orgBrnCode + "')");
                if (insert1.executeUpdate() <= 0) {
                    throw new ApplicationException("Problem in data insertion yearend");
                }
            }
            List list1 = em.createNativeQuery("select min(mindate),min(maxdate) from yearend where yearendflag='N' and brncode='" + orgBrnCode + "'").getResultList();
            if (list1.isEmpty()) {
                throw new ApplicationException("Data does not exist in yearend");
            }
            Vector v10 = (Vector) list1.get(0);

            String tmpMinDate = v10.get(0).toString();
            String tmpMaxDate = v10.get(1).toString();

            String fYear = tmpMinDate.substring(0, 4) + "-" + tmpMaxDate.substring(0, 4);

            list1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from gl_recon where  dt<='" + tmpMaxDate + "' and DT>='" + tmpMinDate
                    + "' and  substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue()
                    + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_END.getValue() + "' and auth='Y' and substring(acno,1,2) ='"
                    + orgBrnCode + "'").getResultList();
            Vector v11 = (Vector) list1.get(0);
            double iTot = Double.parseDouble(v11.get(0).toString());

            List list2 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from gl_recon where  dt<='" + tmpMaxDate + "' "
                    + "and DT>='" + tmpMinDate + "' and substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_ST.getValue()
                    + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_EXP_END.getValue() + "' and auth='Y' and substring(acno,1,2) ='"
                    + orgBrnCode + "'").getResultList();
            Vector v22 = (Vector) list2.get(0);
            double eTot = Double.parseDouble(v22.get(0).toString());

            double totProfit = Math.abs(iTot) - Math.abs(eTot);
            totProfit = CbsUtil.round(totProfit, 2);

            if (totProfit != 0) {
                float tmpRecno = ftsPost43Remote.getRecNo();
                float tmpTrsno = ftsPost43Remote.getTrsNo();
                if (totProfit < 0) {
                    List list35 = em.createNativeQuery("select ifnull(acno,'') from abb_parameter_info where purpose='PRE_YEAR_LOSS'").getResultList();
                    if (list35.isEmpty()) {
                        throw new ApplicationException("Please Fill Previous Year Loss Gl Head In abb_parameter_info.");
                    }
                    Vector v35 = (Vector) list35.get(0);
                    String lossAcNo = v35.get(0).toString();

                    glAcno = orgBrnCode + lossAcNo;
                    Query insert2 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,TY,TRANTYPE,TRANDESC,DRAMT,DT,VALUEDT,DETAILS,AUTH,ENTERBY,authby,"
                            + "trsno,recno,payby,iy,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('" + glAcno + "',1,2,13, "
                            + Math.abs(totProfit) + ",'" + tempDate + "','" + tempDate + "','P/L BROUGHT FORWARDED','Y','" + userName + "','SYSTEM',"
                            + tmpTrsno + "," + tmpRecno + ",3,1,'" + orgBrnCode + "','" + orgBrnCode + "','','')");
                    int rs = insert2.executeUpdate();
                    if (rs <= 0) {
                        throw new ApplicationException("Problem in data insertion");
                    }
                    String msg = ftsPost43Remote.updateBalance(CbsConstant.PAY_ORDER, glAcno, 0, Math.abs(totProfit), "Y", "Y");
                    if (!msg.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException("Problem in balance updation");
                    }
                    if (ftsPost43Remote.getCodeForReportName("PL-AUTO-XFER") == 1) {
                        List list36 = em.createNativeQuery("select ifnull(acno,'') from abb_parameter_info where purpose='PRE_YEAR_PROFIT'").getResultList();
                        if (list36.isEmpty()) {
                            throw new ApplicationException("Please Fill Previous Year Profit Gl Head In abb_parameter_info.");
                        }
                        Vector v36 = (Vector) list36.get(0);
                        String profitAcNo = v36.get(0).toString();

                        String hoBrCode = common.getBrncodeByAlphacode("HO");
                        String hoProfitAcNo = hoBrCode + profitAcNo;

                        float trsno = ftsPost43Remote.getTrsNo();
                        String details = "Loss for FY " + fYear + " transfer to HO ";
                        String result = interBrTxn.cbsPostingCx(glAcno, 0, tempDate, Math.abs(totProfit), 0, 2, details, 0f, "", "", "", 3, 0f, 0f, 0, orgBrnCode,
                                orgBrnCode, userName, "System", trsno, "", "");
                        if (!result.substring(0, 4).equalsIgnoreCase("True")) {
                            throw new ApplicationException(result.substring(4));
                        }
                        details = "Loss for FY " + fYear + " of " + common.getBranchNameByBrncode(orgBrnCode);
                        result = interBrTxn.cbsPostingSx(hoProfitAcNo, 1, tempDate, Math.abs(totProfit), 0, 2, details, 0f, "", "", "", 3, 0f, 0f, 0, hoBrCode,
                                orgBrnCode, userName, "System", trsno, "", "");
                        if (!result.substring(0, 4).equalsIgnoreCase("True")) {
                            throw new ApplicationException(result.substring(4));
                        }
                        result = ftsPost43Remote.updateBalance(CbsConstant.PAY_ORDER, hoProfitAcNo, 0, Math.abs(totProfit), "Y", "Y");
                        if (!result.equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException("Problem in balance updation");
                        }
                    }
                } else {
                    List list36 = em.createNativeQuery("select ifnull(acno,'') from abb_parameter_info where purpose='PRE_YEAR_PROFIT'").getResultList();
                    if (list36.isEmpty()) {
                        throw new ApplicationException("Please Fill Previous Year Profit Gl Head In abb_parameter_info.");
                    }
                    Vector v36 = (Vector) list36.get(0);
                    String profitAcNo = v36.get(0).toString();

                    glAcno = orgBrnCode + profitAcNo;
                    Query insert3 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,TY,TRANTYPE,TRANDESC,CRAMT,DT,VALUEDT,DETAILS,AUTH,ENTERBY,authby,"
                            + "trsno,recno,payby,iy,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('" + glAcno + "',0,2,13, " + totProfit + ",'"
                            + tempDate + "','" + tempDate + "','P/L BALANCE BROUGHT FORWARDED','Y','" + userName + "','SYSTEM'," + tmpTrsno + ","
                            + tmpRecno + ",3,1,'" + orgBrnCode + "','" + orgBrnCode + "','','')");
                    int rs = insert3.executeUpdate();
                    if (rs <= 0) {
                        throw new ApplicationException("Problem in data insertion");
                    }
                    String msg = ftsPost43Remote.updateBalance(CbsConstant.PAY_ORDER, glAcno, totProfit, 0, "Y", "Y");
                    if (!msg.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException("Problem in balance updation");
                    }
                    if (ftsPost43Remote.getCodeForReportName("PL-AUTO-XFER") == 1) {
                        String hoBrCode = common.getBrncodeByAlphacode("HO");
                        String hoProfitAcNo = hoBrCode + profitAcNo;

                        float trsno = ftsPost43Remote.getTrsNo();
                        String details = "Profit for FY " + fYear + " transfer to HO";
                        String result = interBrTxn.cbsPostingCx(glAcno, 1, tempDate, totProfit, 0, 2, details, 0f, "", "", "", 3, 0f, 0f, 0, orgBrnCode,
                                orgBrnCode, userName, "System", trsno, "", "");
                        if (!result.substring(0, 4).equalsIgnoreCase("True")) {
                            throw new ApplicationException(result.substring(4));
                        }
                        result = ftsPost43Remote.updateBalance(CbsConstant.PAY_ORDER, glAcno, 0, totProfit, "Y", "Y");
                        if (!result.equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException("Problem in balance updation");
                        }
                        details = "Profit for the Period " + fYear + " of " + common.getBranchNameByBrncode(orgBrnCode);
                        result = interBrTxn.cbsPostingSx(hoProfitAcNo, 0, tempDate, totProfit, 0, 2, details, 0f, "", "", "", 3, 0f, 0f, 0, hoBrCode,
                                orgBrnCode, userName, "System", trsno, "", "");
                        if (!result.substring(0, 4).equalsIgnoreCase("True")) {
                            throw new ApplicationException(result.substring(4));
                        }
                    }
                }

                list1 = em.createNativeQuery("select acno,sum(ifnull(cramt,0))-sum(ifnull(dramt,0)) from gl_recon where substring(acno,3,8) between '"
                        + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_END.getValue() + "' and auth='Y' and dt between '"
                        + tmpMinDate + "' and '" + tmpMaxDate + "' and substring(acno,1,2) ='" + orgBrnCode + "' group by acno order by acno").getResultList();
                for (int i = 0; i < list1.size(); i++) {
                    Vector v1 = (Vector) list1.get(i);
                    String acno = v1.get(0).toString();
                    double amt = Double.parseDouble(v1.get(1).toString());

                    String details = "Trf On " + dmy.format(ymd.parse(tempDate)) + " For Period " + dmy.format(ymd.parse(tmpMinDate)) + " To "
                            + dmy.format(ymd.parse(tmpMaxDate));
                    double cramt = 0d;
                    double dramt = 0d;
                    int ty = -5;
                    if (amt < 0) {
                        ty = 0;
                        cramt = Math.abs(amt);
                        dramt = 0;
                    } else if (amt > 0) {
                        ty = 1;
                        dramt = Math.abs(amt);
                        cramt = 0;
                    }
                    if (ty == 0 || ty == 1) {
                        float recpno = ftsPost43Remote.getRecNo();
                        Query insert1 = em.createNativeQuery("insert into gl_recon(acno,dt,valuedt,dramt,cramt,ty,trantype,recno,trsno,auth,enterby,"
                                + "authby,trandesc,details,payby,iy,org_brnid,dest_brnid,adviceNo,adviceBrnCode) values('" + acno + "','"
                                + tempDate + "','" + tempDate + "'," + dramt + "," + cramt + "," + ty + ",2 ," + recpno + ","
                                + tmpTrsno + ",'Y','" + userName + "','SYSTEM',13,'" + details + "',3,1,'" + orgBrnCode + "','" + orgBrnCode + "','','')");
                        int var1 = insert1.executeUpdate();
                        if (var1 <= 0) {
                            return "Error In Insertion for A/c No :-" + acno;
                        }
                    }
                }
                Query update2 = em.createNativeQuery("UPDATE reconbalan SET BALANCE=0  where substring(acno,3,8) between '"
                        + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_PL_ST.getValue() + "' AND '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode()
                        + SiplConstant.GL_PL_END.getValue() + "' and substring(acno,1,2)='" + orgBrnCode + "'");
                int rs = update2.executeUpdate();
                if (rs <= 0) {
                    throw new ApplicationException("Please check the PL Heads in reconBalan table");
                }
            }

            List minList = em.createNativeQuery("select min(mindate) from yearend where yearendflag='N' and brncode='" + orgBrnCode + "'").getResultList();
            if (minList.isEmpty()) {
                throw new ApplicationException("Please Check Year End Of Branch." + orgBrnCode);
            }
            Vector minVec = (Vector) minList.get(0);
            String tmpMinDt = minVec.get(0).toString();

            Query update4 = em.createNativeQuery("update yearend set yearendflag='Y' where mindate = '" + tmpMinDt + "' and brncode='" + orgBrnCode + "'");
            if (update4.executeUpdate() <= 0) {
                throw new ApplicationException("Problem in data updation");
            }

            List list6 = em.createNativeQuery("select DATE_FORMAT(dt,'%Y%m%d') from bankdays_hist where dt='" + tempDate + "' and brncode='"
                    + orgBrnCode + "'").getResultList();
            if (list6.isEmpty()) {
                Query insert4 = em.createNativeQuery("insert into bankdays_hist (dt,yend_By ,yend_dt,remarks,brncode) VALUES('" + tempDate + "','"
                        + userName + "',now(),'ROUTINE','" + orgBrnCode + "')");
                if (insert4.executeUpdate() <= 0) {
                    throw new ApplicationException("Problem in data updation");
                }
            } else {
                Query update5 = em.createNativeQuery("UPDATE bankdays_hist SET yend_By='" + userName + "' ,yend_DT=now(),remarks='ROUTINE' "
                        + "WHERE DT='" + tempDate + "' and brncode='" + orgBrnCode + "'");
                if (update5.executeUpdate() <= 0) {
                    throw new ApplicationException("Problem in data updation");
                }
            }

            /* Code To Transfer Previous Year Not Recovered TDS Into History At the End Of Financial Year */
            List delTdsList = em.createNativeQuery("select acno from tds_reserve_history where recovered = 'NR' "
                    + " and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!delTdsList.isEmpty()) {

                int ninsertTds = em.createNativeQuery("insert into tds_nr_reserve_history (Acno,VoucherNo,TDS,Dt,FromDt,ToDt,intOpt,TXNID,"
                        + "recovered,trsno,recno,tdsRecoveredDt,recoveredVch) select Acno,VoucherNo,TDS,Dt,FromDt,ToDt,intOpt,TXNID,"
                        + "recovered,trsno,recno,tdsRecoveredDt,recoveredVch from tds_reserve_history where recovered = 'NR' "
                        + "and substring(acno,1,2)='" + orgBrnCode + "'").executeUpdate();
                if (ninsertTds <= 0) {
                    throw new ApplicationException("Problem in tds_nr_reserve_history insertion");
                }

                Query delTds = em.createNativeQuery("delete from tds_reserve_history where recovered = 'NR' "
                        + " and substring(acno,1,2)='" + orgBrnCode + "'");
                if (delTds.executeUpdate() <= 0) {
                    throw new ApplicationException("Problem in tds_reserve_history deletion");
                }
            }
            /*New Code for Interest Parameter */
            if (ftsPost43Remote.getCodeForReportName("INT-PARAMETER") == 1) {

                List acTypeList = em.createNativeQuery("select acctnature,acctcode from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') and CrDbFlag in('B','D') "
                        + "union all "
                        + "select acctnature,acctcode from accounttypemaster where acctnature in('" + CbsConstant.SAVING_AC + "')").getResultList();
                for (int i = 0; i < acTypeList.size(); i++) {
                    Vector vtr = (Vector) acTypeList.get(i);
                    String nature = vtr.get(0).toString();
                    String acctType = vtr.get(1).toString();
                    String startDt = tempBd.substring(0, 4) + "0401";
                    String financialYear = tempBd.substring(0, 4);
                    String interestType = "I", intOption = "M", parametreCharge = "Parameter";
                    List dtList = new ArrayList();

                    List parametreList = em.createNativeQuery("select 'Parameter'union select 'charge'").getResultList();
                    for (int k = 0; k < parametreList.size(); k++) {
                        Vector vtr2 = (Vector) parametreList.get(k);
                        parametreCharge = vtr2.get(0).toString();
                        if (parametreCharge.equalsIgnoreCase("Parameter")) {
                            if (nature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                dtList = em.createNativeQuery("select distinct date_format(FROM_DT,'%Y%m%d'),date_format(TO_DT,'%Y%m%d') from cbs_loan_acctype_interest_parameter where BRNCODE = '" + orgBrnCode + "' and AC_TYPE = '" + acctType + "' and FLAG=1 "
                                        + "and SNO =(select max(SNO) from cbs_loan_acctype_interest_parameter where BRNCODE = '" + orgBrnCode + "' and AC_TYPE = '" + acctType + "' and FLAG=1)").getResultList();
                            } else {
                                dtList = em.createNativeQuery("select date_format(FROM_DT,'%Y%m%d'),date_format(TO_DT,'%Y%m%d'),FLAG from cbs_loan_acctype_interest_parameter where BRNCODE = '" + orgBrnCode + "' and AC_TYPE = '" + acctType + "' and FLAG = 'I' and SNO ="
                                        + "(select max(SNO) from cbs_loan_acctype_interest_parameter where BRNCODE = '" + orgBrnCode + "' and AC_TYPE = '" + acctType + "' and FLAG = 'I') "
                                        + "union "
                                        + "select date_format(FROM_DT,'%Y%m%d'),date_format(TO_DT,'%Y%m%d'),FLAG from cbs_loan_acctype_interest_parameter where BRNCODE = '" + orgBrnCode + "' and AC_TYPE = '" + acctType + "' and FLAG = 'P' "
                                        + "and SNO =(select max(SNO) from cbs_loan_acctype_interest_parameter where BRNCODE = '" + orgBrnCode + "' and AC_TYPE = '" + acctType + "'and FLAG = 'P')").getResultList();
                            }

                            if (!dtList.isEmpty()) {
                                for (int j = 0; j < dtList.size(); j++) {
                                    Vector dtVector = (Vector) dtList.get(j);
                                    String frDt = dtVector.get(0).toString();
                                    String toDt = dtVector.get(1).toString();
                                    if (!nature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                        interestType = dtVector.get(2).toString();
                                    }
                                    List intList = em.createNativeQuery("SELECT TIMESTAMPDIFF(MONTH, '" + frDt + "', DATE_ADD('" + toDt + "', INTERVAL 1 DAY))").getResultList();
                                    Vector vtr1 = (Vector) intList.get(0);
                                    int intType = Integer.parseInt(vtr1.get(0).toString());
                                    if (intType == 1) {
                                        intOption = "M";
                                    } else if (intType == 3) {
                                        intOption = "Q";
                                    } else if (intType == 6) {
                                        intOption = "H";
                                    } else if (intType > 6) {
                                        intOption = "Y";
                                    } else {
                                        intOption = "M";
                                    }
                                    String result = ftsPost43Remote.autoInterestSaveLoanInterestParameter(financialYear, acctType, interestType, intOption, userName, ymd.format(new Date()), orgBrnCode, startDt, parametreCharge, nature);
                                    if (!result.equalsIgnoreCase("True")) {
                                        throw new ApplicationException(result);
                                    }
                                }
                            }
                        } else {
                            List list = em.createNativeQuery("select distinct FLAG from cbs_loan_acctype_interest_parameter  where FLAG not in('1','2','I','P','UC','MB') and dt between '" + tmpMinDate + "' and '" + tmpMaxDate + "' ").getResultList();
                            for (int j = 0; j < list.size(); j++) {
                                Vector vtr1 = (Vector) list.get(j);
                                interestType = vtr1.get(0).toString();
                                dtList = em.createNativeQuery("select max(date_format(FROM_DT,'%Y%m%d')),max(date_format(TO_DT,'%Y%m%d')),FLAG from cbs_loan_acctype_interest_parameter where BRNCODE = '" + orgBrnCode + "' and AC_TYPE = '" + acctType + "' and SNO = "
                                        + "(select max(SNO) from cbs_loan_acctype_interest_parameter where BRNCODE = '" + orgBrnCode + "' and AC_TYPE = '" + acctType + "' and FLAG = '" + interestType + "')").getResultList();
                                if (!dtList.isEmpty()) {
                                    for (int m = 0; m < dtList.size(); m++) {
                                        Vector dtVector = (Vector) dtList.get(m);
                                        if (!(dtVector.get(0) == null || dtVector.get(1) == null)) {
                                            String frDt = dtVector.get(0).toString();
                                            String toDt = dtVector.get(1).toString();
                                            interestType = dtVector.get(2).toString();

                                            List intList = em.createNativeQuery("SELECT TIMESTAMPDIFF(MONTH, '" + frDt + "', DATE_ADD('" + toDt + "', INTERVAL 1 DAY))").getResultList();
                                            vtr1 = (Vector) intList.get(0);
                                            int intType = Integer.parseInt(vtr1.get(0).toString());
                                            if (intType == 1) {
                                                intOption = "M";
                                            } else if (intType == 3) {
                                                intOption = "Q";
                                            } else if (intType == 6) {
                                                intOption = "H";
                                            } else if (intType > 6) {
                                                intOption = "Y";
                                            } else {
                                                intOption = "M";
                                            }
                                            String result = ftsPost43Remote.autoInterestSaveLoanInterestParameter(financialYear, acctType, interestType, intOption, userName, ymd.format(new Date()), orgBrnCode, startDt, parametreCharge, nature);
                                            if (!result.equalsIgnoreCase("True")) {
                                                throw new ApplicationException(result);
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
            /*END New Code for Interest Parameter */

            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return "True";
    }

    public List<DayActivityPojo> getDayActivityReport(String orgBrnCode, String todayDate) throws ApplicationException {
        try {
            //todayDate = todayDate.substring(6) + todayDate.substring(3, 5) + todayDate.substring(0, 2);
            List list2 = em.createNativeQuery("select tl.acno Acno,ifnull(am.custname,''),ifnull(tl.enterby,''),ifnull(tl.authby,''), "
                    + "'R.T. No.:' + cast(VoucherNo as char(6)) +', Reci No.:' + cast(ReceiptNo AS char(6)) + ', Lien St:' + tl.lienstatus + ',"
                    + " Remarks:' +  tl.remarks  Details from td_lien_update tl,td_accountmaster am where tl.acno=am.acno and am.curbrcode='"
                    + orgBrnCode + "' and DATE_FORMAT(tl.txndate,'%Y%m%d')='" + todayDate + "'").getResultList();

            List<DayActivityPojo> dayActivityList = new ArrayList<DayActivityPojo>();
            if (!list2.isEmpty()) {
                for (int i = 0; i < list2.size(); i++) {
                    DayActivityPojo pojo = new DayActivityPojo();

                    Vector v2 = (Vector) list2.get(i);
                    if (v2.get(0) != null) {
                        pojo.setAcno(v2.get(0).toString());
                    }
                    if (v2.get(1) != null) {
                        pojo.setCustName(v2.get(1).toString());
                    }
                    if (v2.get(2) != null) {
                        pojo.setEnterBy(v2.get(2).toString());
                    }
                    if (v2.get(3) != null) {
                        pojo.setAuthBy(v2.get(3).toString());
                    }
                    if (v2.get(4) != null) {
                        pojo.setDetails(v2.get(4).toString());
                    }
                    pojo.setHeading("Lien Marked/Unmarked Term Deposit Accounts");
                    dayActivityList.add(pojo);
                }

            }
            List list3 = em.createNativeQuery("select ae.acno,ae.name,DATE_FORMAT(ae.updatedt,'%d-%m-%Y'),ifnull(ae.enteredby,''),ifnull(ae.authby,''), "
                    + "ae.opermode,cb.description from acedithistory ae,accountmaster ac,codebook cb where ae.acno=ac.acno and DATE_FORMAT(updatedt,'%Y%m%d')='"
                    + todayDate + "'  and ac.curbrcode='" + orgBrnCode + "' and ae.orgncode = cb.code and cb.groupcode=6 "
                    + "union all "
                    + "select ae.acno,ae.name,DATE_FORMAT(ae.updatedt,'%d-%m-%Y'),ifnull(ae.enteredby,''),ifnull(ae.authby,''), ae.opermode,"
                    + "cb.description from acedithistory ae,td_accountmaster ac, codebook cb where ae.acno=ac.acno and DATE_FORMAT(updatedt,'%Y%m%d')='"
                    + todayDate + "' and ac.curbrcode='" + orgBrnCode + "' and ae.orgncode = cb.code and cb.groupcode=40").getResultList();
            if (!list3.isEmpty()) {
                for (int j = 0; j < list3.size(); j++) {
                    DayActivityPojo pojo = new DayActivityPojo();
                    Vector v3 = (Vector) list3.get(j);
                    List list4 = em.createNativeQuery("select description From codebook where code=" + Integer.parseInt(v3.get(5).toString()) + " and groupcode=4").getResultList();
                    Vector v4 = (Vector) list4.get(0);
                    String acNature = ftsPost43Remote.getAccountNature(v3.get(0).toString());
                    String tmp3 = "Mode of Operation:" + v4.get(0).toString();
                    if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                        tmp3 = tmp3 + ", Organization Type:" + v3.get(6).toString();
                    } else {
                        tmp3 = tmp3 + ", Occupation:" + v3.get(6).toString();
                    }
                    tmp3 = tmp3 + ", Update Dt:" + v3.get(2).toString();
                    if (v3.get(0) != null) {
                        pojo.setAcno(v3.get(0).toString());
                    }
                    if (v3.get(1) != null) {
                        pojo.setCustName(v3.get(1).toString());
                    }
                    if (v3.get(3) != null) {
                        pojo.setEnterBy(v3.get(3).toString());
                    }
                    if (v3.get(4) != null) {
                        pojo.setAuthBy(v3.get(4).toString());
                    }
                    pojo.setDetails(tmp3);
                    pojo.setHeading("Edited Account Details");
                    dayActivityList.add(pojo);
                }

            }

            List list4 = em.createNativeQuery("Select cb.acno Acno,am.custname,ifnull(cb.issuedby,'') enterBy,"
                    + "ifnull(cb.authby,''),'Ch Book No.:' + cast(cb.chbookno as char(8)) + ', Ch No. From:' + cast(cb.chnofrom as char(10)) + ', "
                    + "Ch No. To:' + cast(cb.chnoto as char(10)) + ', No. of Leafs:' + cast(cb.leafs as char(20)) Details From chbookmaster cb,"
                    + "accountmaster am where cb.acno=am.acno and cb.issuedt ='" + todayDate + "' and auth='Y' and am.curbrcode='" + orgBrnCode + "'").getResultList();

            if (!list4.isEmpty()) {
                for (int k = 0; k < list4.size(); k++) {
                    DayActivityPojo pojo = new DayActivityPojo();
                    Vector v4 = (Vector) list4.get(k);
                    if (v4.get(0) != null) {
                        pojo.setAcno(v4.get(0).toString());
                    }
                    if (v4.get(1) != null) {
                        pojo.setCustName(v4.get(1).toString());
                    }
                    if (v4.get(2) != null) {
                        pojo.setEnterBy(v4.get(2).toString());
                    }
                    if (v4.get(3) != null) {
                        pojo.setAuthBy(v4.get(3).toString());
                    }
                    if (v4.get(4) != null) {
                        pojo.setDetails(v4.get(4).toString());
                    }
                    pojo.setHeading("Cheque Books Issued Today");
                    dayActivityList.add(pojo);
                }

            }

            List list5 = em.createNativeQuery("select dt.acno Acno,dt.custname,ifnull(dt.enterby,''),ifnull(dt.authby,''),"
                    + "dt.idno,dt.crAmt,dt.DrAmt,dt.deletedby,DATE_FORMAT(dt.deleteDt,'%d-%m-%Y'),ifnull(dt.SubTokenNo,'') ,ifnull(dt.SubTokenNo,'') "
                    + "from deletetrans dt,accountmaster ac where dt.acno=ac.acno and deletedt='" + todayDate + "' and ac.curbrcode='" + orgBrnCode + "' "
                    + "union "
                    + "select dt.acno Acno,dt.custname,ifnull(dt.enterby,''),ifnull(dt.authby,''),dt.idno,dt.crAmt,dt.DrAmt,"
                    + "dt.deletedby,DATE_FORMAT(dt.deleteDt,'%d-%m-%Y'),ifnull(dt.SubTokenNo,'') ,ifnull(dt.SubTokenNo,'') from deletetrans dt,"
                    + "td_accountmaster ac where dt.acno=ac.acno and deletedt='" + todayDate + "' and ac.curbrcode='" + orgBrnCode + "' "
                    + "union "
                    + "select dt.acno Acno,custname,ifnull(enterby,''),ifnull(authby,''),idno,crAmt,DrAmt,deletedby,"
                    + "DATE_FORMAT(deleteDt,'%d-%m-%Y'),ifnull(SubTokenNo,'') ,ifnull(SubTokenNo,'') from deletetrans dt,gltable gl where "
                    + "dt.acno=gl.acno and deletedt='" + todayDate + "' and substring(dt.acno,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list5.isEmpty()) {
                for (int l = 0; l < list5.size(); l++) {
                    DayActivityPojo pojo = new DayActivityPojo();
                    Vector v5 = (Vector) list5.get(l);
                    if (v5.get(0) != null) {
                        pojo.setAcno(v5.get(0).toString());
                    }
                    if (v5.get(1) != null) {
                        pojo.setCustName(v5.get(1).toString());
                    }
                    if (v5.get(2) != null) {
                        pojo.setEnterBy(v5.get(2).toString());
                    }
                    if (v5.get(3) != null) {
                        pojo.setAuthBy(v5.get(3).toString());
                    }
                    pojo.setDetails("ID No:"
                            + v5.get(4).toString() + ", CrAmt:" + v5.get(5).toString() + ", DrAmt:" + v5.get(6).toString() + ", DelBy:"
                            + v5.get(7).toString() + ", DelDt:" + v5.get(8).toString() + ", TNo: " + v5.get(9).toString() + ", STNo: "
                            + v5.get(10).toString());
                    pojo.setHeading("Deleted Transactions");
                    dayActivityList.add(pojo);
                }

            }

            List list6 = em.createNativeQuery("select lo.acno Acno,am.custName,ifnull(lo.enterBy,''),ifnull(lo.authBy,''),ROI,"
                    + "round(lo.penalROI,2),lo.aclimit,lo.AdhocLimit,date_format(lo.AdhocTillDt,'%d-%m-%Y'),lo.AdhocInterest From loan_oldinterest lo, "
                    + "accountmaster am where am.acno=lo.acno and am.curbrcode='" + orgBrnCode + "' and enterDate='" + todayDate + "'").getResultList();
            if (!list6.isEmpty()) {
                String AdDt;
                for (int m = 0; m < list6.size(); m++) {
                    DayActivityPojo pojo = new DayActivityPojo();
                    Vector v6 = (Vector) list6.get(m);
                    if (v6.get(8).toString().equalsIgnoreCase("1900-01-01 00:00:00")) {
                        AdDt = "";
                    } else {
                        AdDt = v6.get(8).toString().substring(8, 10) + "-" + v6.get(8).toString().substring(5, 7) + "-" + v6.get(8).toString().substring(0, 4);
                    }
                    if (v6.get(0) != null) {
                        pojo.setAcno(v6.get(0).toString());
                    }
                    if (v6.get(1) != null) {
                        pojo.setCustName(v6.get(1).toString());
                    }
                    if (v6.get(2) != null) {
                        pojo.setEnterBy(v6.get(2).toString());
                    }
                    if (v6.get(3) != null) {
                        pojo.setAuthBy(v6.get(3).toString());
                    }
                    pojo.setDetails("ROI:" + v6.get(4).toString() + ", Penalty:" + v6.get(5).toString() + ", Limit:" + v6.get(6).toString() + ", Adhoc Lt:" + v6.get(7).toString() + ", Adhoc Dt.:" + AdDt + ", Adhoc Int.: " + v6.get(9).toString());
                    pojo.setHeading("Old ROI / Limits ");
                    dayActivityList.add(pojo);
                }

            }

            List list7 = em.createNativeQuery("Select distinct instnature from billtypemaster").getResultList();
            if (!list7.isEmpty()) {
                for (int n = 0; n < list7.size(); n++) {
                    DayActivityPojo pojo = new DayActivityPojo();
                    Vector v7 = (Vector) list7.get(n);
                    if (v7.get(0).toString().equalsIgnoreCase("DD")) {
                        List list8 = em.createNativeQuery("select acno Acno,custName,ifnull(enterby,''),ifnull(authby,''),'DD No.:' + INSTNO + ', St:' + "
                                + "substring(status,1,1) + ', Amt:' + cast(Amount as char(15)) + ', Time Lt(days):' + cast(timelimit as char(10)) + ', Seq No:' + "
                                + "cast(seqNo as char(10)) + ', Comm. Amt:' + cast(comm as char(15)) Details from bill_dd where dt='" + todayDate
                                + "' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
                        if (!list8.isEmpty()) {
                            for (int o = 0; o < list8.size(); o++) {
                                Vector v8 = (Vector) list8.get(o);
                                if (v8.get(0) != null) {
                                    pojo.setAcno(v8.get(0).toString());
                                }
                                if (v8.get(1) != null) {
                                    pojo.setCustName(v8.get(1).toString());
                                }
                                if (v8.get(2) != null) {
                                    pojo.setEnterBy(v8.get(2).toString());
                                }
                                if (v8.get(3) != null) {
                                    pojo.setAuthBy(v8.get(3).toString());
                                }
                                if (v8.get(4) != null) {
                                    pojo.setDetails(v8.get(4).toString());
                                }
                                pojo.setHeading("DD Related Transactions Today");
                                dayActivityList.add(pojo);
                            }

                        }

                        List list9 = em.createNativeQuery("select acno Acno,custName,ifnull(enterby,''),ifnull(authby,''),'DD No.:' + INSTNO + ', St:' + "
                                + "substring(status,1,1) + ', Amt:' + cast(Amount as char(15)) + ', Time Lt(days):' + cast(timelimit as char(10)) + ', Seq No:' + "
                                + "cast(seqNo as char(10)) from bill_dd where dt='" + todayDate
                                + "' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
                        if (!list9.isEmpty()) {
                            for (int p = 0; p < list9.size(); p++) {
                                pojo = new DayActivityPojo();
                                Vector v9 = (Vector) list9.get(p);
                                if (v9.get(0) != null) {
                                    pojo.setAcno(v9.get(0).toString());
                                }
                                if (v9.get(1) != null) {
                                    pojo.setCustName(v9.get(1).toString());
                                }
                                if (v9.get(2) != null) {
                                    pojo.setEnterBy(v9.get(2).toString());
                                }
                                if (v9.get(3) != null) {
                                    pojo.setAuthBy(v9.get(3).toString());
                                }
                                if (v9.get(4) != null) {
                                    pojo.setDetails(v9.get(4).toString());
                                }
                                pojo.setHeading("Instrument Available in Stock (DD)");
                                dayActivityList.add(pojo);
                            }

                        }

                    } else if (v7.get(0).toString().equalsIgnoreCase("PO")) {
                        List list10 = em.createNativeQuery("Select acno custName,ifnull(enterby,''),ifnull(authby,''),INSTNO ,status,Amount,"
                                + "timelimit, seqNo From bill_po where dt='" + todayDate + "' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();

                        if (!list10.isEmpty()) {
                            for (int q = 0; q < list10.size(); q++) {
                                pojo = new DayActivityPojo();
                                Vector v10 = (Vector) list10.get(q);
                                if (v10.get(0) != null) {
                                    pojo.setAcno(v10.get(0).toString());
                                }
                                if (v10.get(1) != null) {
                                    pojo.setCustName(v10.get(1).toString());
                                }
                                if (v10.get(2) != null) {
                                    pojo.setEnterBy(v10.get(2).toString());
                                }
                                if (v10.get(3) != null) {
                                    pojo.setAuthBy(v10.get(3).toString());
                                }
                                pojo.setDetails("PO No:" + v10.get(4).toString() + ", St:" + v10.get(5).toString().substring(0, 1) + ", Amt:" + v10.get(6).toString() + ", Time Lt(days):" + v10.get(6).toString() + ", Seq No: " + v10.get(7).toString());
                                pojo.setHeading("PO Related Transactions Today");
                                dayActivityList.add(pojo);
                            }

                        }

                        List list11 = em.createNativeQuery("Select 'PO' Acno,'Instrument' CustName,'' EnterBy,'' AuthBy,'Total Instruments :' + cast(count(*) as char) "
                                + "details From chbook_bill where inst_Type='PO' and StatusFlag='F' and brncode='" + orgBrnCode + "'").getResultList();
                        if (!list11.isEmpty()) {
                            for (int r = 0; r < list11.size(); r++) {
                                pojo = new DayActivityPojo();
                                Vector v11 = (Vector) list11.get(r);
                                if (v11.get(0) != null) {
                                    pojo.setAcno(v11.get(0).toString());
                                }
                                if (v11.get(1) != null) {
                                    pojo.setCustName(v11.get(1).toString());
                                }
                                if (v11.get(2) != null) {
                                    pojo.setEnterBy(v11.get(2).toString());
                                }
                                if (v11.get(3) != null) {
                                    pojo.setAuthBy(v11.get(3).toString());
                                }
                                if (v11.get(4) != null) {
                                    pojo.setDetails(v11.get(4).toString());
                                }
                                pojo.setHeading("Instrument Available in Stock (PO)");
                                dayActivityList.add(pojo);
                            }

                        }
                    } else if (v7.get(0).toString().equalsIgnoreCase("TPO")) {
                        List list12 = em.createNativeQuery("Select acno Acno,custName,ifnull(enterby,''),ifnull(authby,''),'TPO No:' + INSTNO + "
                                + "', St:' + substring(status,1,1) + ', Amt:' + cast(Amount as char(15)) + ', Time Lt(days):' + cast(timelimit as char(10)) + "
                                + "', Seq No:' + cast(seqNo as char(10)) + ', Comm. Amt:' + cast(comm as char(15)) Details From bill_tpo where "
                                + "dt='" + todayDate + "' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
                        if (!list12.isEmpty()) {
                            for (int s = 0; s < list12.size(); s++) {
                                pojo = new DayActivityPojo();
                                Vector v12 = (Vector) list12.get(s);
                                if (v12.get(0) != null) {
                                    pojo.setAcno(v12.get(0).toString());
                                }
                                if (v12.get(1) != null) {
                                    pojo.setCustName(v12.get(1).toString());
                                }
                                if (v12.get(2) != null) {
                                    pojo.setEnterBy(v12.get(2).toString());
                                }
                                if (v12.get(3) != null) {
                                    pojo.setAuthBy(v12.get(3).toString());
                                }
                                if (v12.get(4) != null) {
                                    pojo.setDetails(v12.get(4).toString());
                                }
                                pojo.setHeading("Instrument Available in Stock (PO)");
                                dayActivityList.add(pojo);
                            }

                        }
                        List list13 = em.createNativeQuery("Select 'TPO' Acno,'Instrument' CustName,'' EnterBy,'' AuthBy,'Total Instruments :' + cast(count(*) as char) "
                                + "Details From chbook_bill where inst_Type='TPO' and StatusFlag='F' and brncode='" + orgBrnCode + "'").getResultList();
                        if (!list13.isEmpty()) {
                            for (int t = 0; t < list13.size(); t++) {
                                pojo = new DayActivityPojo();
                                Vector v13 = (Vector) list13.get(t);
                                if (v13.get(0) != null) {
                                    pojo.setAcno(v13.get(0).toString());
                                }
                                if (v13.get(1) != null) {
                                    pojo.setCustName(v13.get(1).toString());
                                }
                                if (v13.get(2) != null) {
                                    pojo.setEnterBy(v13.get(2).toString());
                                }
                                if (v13.get(3) != null) {
                                    pojo.setAuthBy(v13.get(3).toString());
                                }
                                if (v13.get(4) != null) {
                                    pojo.setDetails(v13.get(4).toString());
                                }
                                pojo.setHeading("Instrument Available in Stock (TPO)");
                                dayActivityList.add(pojo);
                            }

                        }

                    } else if (v7.get(0).toString().equalsIgnoreCase("AD")) {
                        List list14 = em.createNativeQuery("Select acno Acno,custName,ifnull(enterby,''),ifnull(authby,''),'AD No:' + INSTNO + ', St:' + "
                                + "substring(status,1,1) + ', Amt:' + cast(Amount as char(15)) + ', Time Lt(days):' + cast(timelimit as char(10)) + ', Comm. Amt:' + "
                                + "cast(comm as char(15)) Details,ifnull(seqNo,0) From bill_ad where dt='" + todayDate + "' and substring(acno,1,2)='"
                                + orgBrnCode + "'").getResultList();

                        if (!list14.isEmpty()) {
                            for (int u = 0; u < list14.size(); u++) {
                                pojo = new DayActivityPojo();
                                Vector v14 = (Vector) list14.get(u);
                                if (v14.get(0) != null) {
                                    pojo.setAcno(v14.get(0).toString());
                                }
                                if (v14.get(1) != null) {
                                    pojo.setCustName(v14.get(1).toString());
                                }
                                if (v14.get(2) != null) {
                                    pojo.setEnterBy(v14.get(2).toString());
                                }
                                if (v14.get(3) != null) {
                                    pojo.setAuthBy(v14.get(3).toString());
                                }
                                if (v14.get(4) != null) {
                                    pojo.setDetails(v14.get(4).toString() + " Seq No.:" + v14.get(5).toString());
                                }
                                pojo.setHeading("AD Related Transactions Today ");
                                dayActivityList.add(pojo);
                            }

                        }

                    }
                }
            }

            List list15 = em.createNativeQuery("Select Acno From bill_hoothers where dt='" + todayDate + "'").getResultList();
            if (!list15.isEmpty()) {
                List list16 = em.createNativeQuery("Select acno Acno,custName,ifnull(enterby,''),ifnull(authby,''),INSTNO ,status,Amount,timelimit,"
                        + "seqNo,comm  From bill_hoothers where dt='" + todayDate + "'").getResultList();
                if (!list16.isEmpty()) {
                    for (int v = 0; v < list16.size(); v++) {
                        Vector v16 = (Vector) list16.get(v);
                        DayActivityPojo pojo = new DayActivityPojo();
                        if (v16.get(0) != null) {
                            pojo.setAcno(v16.get(0).toString());
                        }
                        if (v16.get(1) != null) {
                            pojo.setCustName(v16.get(1).toString());
                        }
                        if (v16.get(2) != null) {
                            pojo.setEnterBy(v16.get(2).toString());
                        }
                        if (v16.get(3) != null) {
                            pojo.setAuthBy(v16.get(3).toString());
                        }
                        pojo.setDetails("HO No:" + v16.get(4).toString() + ", St:" + v16.get(5).toString().substring(0, 1) + ", Amt:" + v16.get(6).toString() + ", Time Lt(days):" + v16.get(7).toString() + ", Seq No.:" + v16.get(8).toString() + ", Comm. Amt:" + v16.get(9).toString());
                        pojo.setHeading("HO Others Related Transactions Today");
                        dayActivityList.add(pojo);
                    }
                }

            }

            List list17 = em.createNativeQuery("Select cd.acno , am.custname, ifnull(cd.enteredBy,''), ifnull(cd.authby,'') ,'Ch No:' + "
                    + "cast(chequeno as char(10)) + ', Ch Dt:' + cast(chequedt as char(8)) + ', St:Operative, Amount:' + cast(Amount as char(15)) + ', "
                    + "Favouring:' + favoring as Details From chbookdetail cd,accountmaster am  Where cd.acno=am.acno and "
                    + "DATE_FORMAT(cd.enterydate,'%Y%m%d')='" + todayDate + "' and cd.status = 1 and am.curbrcode='" + orgBrnCode
                    + "' Union Select cd.acno acno, am.custname, cd.enteredBy, cd.authby ,'Ch No:' + cast(chequeno as char(10)) + "
                    + "', Ch Dt:' + cast(chequedt as char(8)) + ', St:Stop Payment, Amt:' + cast(Amount as char(15)) + ', Favouring:' + "
                    + "favoring as Details From chbookdetail cd,accountmaster am Where cd.acno=am.acno and am.curbrcode='" + orgBrnCode
                    + "' and enterydate='" + todayDate + "' and cd.status = 9").getResultList();
            if (!list17.isEmpty()) {
                for (int w = 0; w < list17.size(); w++) {
                    DayActivityPojo pojo = new DayActivityPojo();
                    Vector v17 = (Vector) list17.get(w);
                    if (v17.get(0) != null) {
                        pojo.setAcno(v17.get(0).toString());
                    }
                    if (v17.get(1) != null) {
                        pojo.setCustName(v17.get(1).toString());
                    }
                    if (v17.get(2) != null) {
                        pojo.setEnterBy(v17.get(2).toString());
                    }
                    if (v17.get(3) != null) {
                        pojo.setAuthBy(v17.get(3).toString());
                    }
                    if (v17.get(4) != null) {
                        pojo.setDetails(v17.get(4).toString());
                    }
                    pojo.setHeading("Stop Payment Instructions Issued Against Cheques");
                    dayActivityList.add(pojo);
                }

            }
            List list18 = em.createNativeQuery("select lo.acno,am.custName,ifnull(lo.enterBy,''),ifnull(lo.authBy,''),ifnull(ROI,0),"
                    + "ifnull(lo.penalROI,0),ifnull(lo.aclimit,0),ifnull(lo.AdhocLimit,0),ifnull(DATE_FORMAT(lo.AdhocTillDt,'%d-%m-%Y'),'') ,"
                    + "ifnull(lo.AdhocInterest,0) from loan_oldinterest lo, accountmaster am where lo.acno=am.acno and DATE_FORMAT(lo.enterDate,'%Y%m%d')='"
                    + todayDate + "' and am.curbrcode='" + orgBrnCode + "'").getResultList();
            if (!list18.isEmpty()) {
                for (int x = 0; x < list18.size(); x++) {
                    DayActivityPojo pojo = new DayActivityPojo();
                    Vector v18 = (Vector) list18.get(x);
                    if (v18.get(0) != null) {
                        pojo.setAcno(v18.get(0).toString());
                    }
                    if (v18.get(1) != null) {
                        pojo.setCustName(v18.get(1).toString());
                    }
                    if (v18.get(2) != null) {
                        pojo.setEnterBy(v18.get(2).toString());
                    }
                    if (v18.get(3) != null) {
                        pojo.setAuthBy(v18.get(3).toString());
                    }
                    pojo.setDetails("Penalty:" + v18.get(5).toString() + ", Limit:" + v18.get(6).toString() + ", Adhoc Limit:" + v18.get(7).toString() + ", Adhoc Till Dt.:" + v18.get(8).toString() + ", Adhoc Int.:" + v18.get(9).toString());
                    pojo.setHeading("TD Interest Posting");
                    dayActivityList.add(pojo);
                }
            }
            List list19 = em.createNativeQuery("select am.acno Acno,am.custName,ifnull(rc.enterBy,''),ifnull(rc.authBy,''),'TDAcno:' + rc.TDAcno + "
                    + "', VouchNo.:' + cast(rc.VoucherNo as char(10)) + ', CrAmt.:' + cast(rc.CrAmt as char(15)) + ', TNo:' + "
                    + "cast(rc.TokenNo as char(10)) + ', STNo:' + cast(rc.SubTokenNo as char(10)) as Details From of_recon rc, "
                    + "td_accountmaster am where rc.acno=am.acno and rc.dt='" + todayDate + "' and rc.org_brnid='" + orgBrnCode + "'").getResultList();
            if (!list19.isEmpty()) {
                for (int y = 0; y < list19.size(); y++) {
                    DayActivityPojo pojo = new DayActivityPojo();
                    Vector v19 = (Vector) list19.get(y);
                    if (v19.get(0) != null) {
                        pojo.setAcno(v19.get(0).toString());
                    }
                    if (v19.get(1) != null) {
                        pojo.setCustName(v19.get(1).toString());
                    }
                    if (v19.get(2) != null) {
                        pojo.setEnterBy(v19.get(2).toString());
                    }
                    if (v19.get(3) != null) {
                        pojo.setAuthBy(v19.get(3).toString());
                    }
                    if (v19.get(4) != null) {
                        pojo.setDetails(v19.get(4).toString());
                    }
                    pojo.setHeading("Transferred (OF) Account");
                    dayActivityList.add(pojo);
                }

            }
            List list20 = em.createNativeQuery("Select 'FD' Acno,'Receipt' CustName,'' EnterBy,'' AuthBy,'Total Receipts :' + "
                    + "cast(count(*) as char(4)) Details From td_receiptissue Where Scheme='FD' and Status='F' and brncode='" + orgBrnCode + "'").getResultList();
            if (!list20.isEmpty()) {
                for (int z = 0; z < list20.size(); z++) {
                    DayActivityPojo pojo = new DayActivityPojo();
                    Vector v20 = (Vector) list20.get(z);
                    if (v20.get(0) != null) {
                        pojo.setAcno(v20.get(0).toString());
                    }
                    if (v20.get(1) != null) {
                        pojo.setCustName(v20.get(1).toString());
                    }
                    if (v20.get(2) != null) {
                        pojo.setEnterBy(v20.get(2).toString());
                    }
                    if (v20.get(3) != null) {
                        pojo.setAuthBy(v20.get(3).toString());
                    }
                    if (v20.get(4) != null) {
                        pojo.setDetails(v20.get(4).toString());
                    }
                    pojo.setHeading("Receipt Available in Stock (FD)");
                    dayActivityList.add(pojo);

                }
            }

            List list21 = em.createNativeQuery("Select 'MS' Acno,'Receipt' CustName,'' EnterBy,'' AuthBy,'Total Receipts :' + "
                    + "cast(count(*) as char(4)) Details From td_receiptissue Where Scheme='MS' and Status='F' and brncode='" + orgBrnCode + "'").getResultList();
            if (!list21.isEmpty()) {
                for (int a = 0; a < list21.size(); a++) {
                    DayActivityPojo pojo = new DayActivityPojo();
                    Vector v21 = (Vector) list21.get(a);
                    if (v21.get(0) != null) {
                        pojo.setAcno(v21.get(0).toString());
                    }
                    if (v21.get(1) != null) {
                        pojo.setCustName(v21.get(1).toString());
                    }
                    if (v21.get(2) != null) {
                        pojo.setEnterBy(v21.get(2).toString());
                    }
                    if (v21.get(3) != null) {
                        pojo.setAuthBy(v21.get(3).toString());
                    }
                    if (v21.get(4) != null) {
                        pojo.setDetails(v21.get(4).toString());
                    }
                    pojo.setHeading("Receipt Available in Stock (MS)");
                    dayActivityList.add(pojo);
                }
            }
            List list22 = em.createNativeQuery("select a.acno Acno,substring(ifnull(ifnull(a1.custname,t.custname),''),1,15) Custname,"
                    + "ifnull(a.enterby,'') EnterBy,ifnull(a.authby,'') AuthBy,ifnull(a.remark,'') Details From accountstatus a  "
                    + "left join accountmaster a1 on a.acno=a1.acno left join td_accountmaster t on a.acno=t.acno where a.dt='" + todayDate
                    + "' and substring(a.ACNO,1,2)='" + orgBrnCode + "'").getResultList();
            if (!list22.isEmpty()) {
                for (int b = 0; b < list22.size(); b++) {
                    DayActivityPojo pojo = new DayActivityPojo();
                    Vector v22 = (Vector) list22.get(b);
                    if (v22.get(0) != null) {
                        pojo.setAcno(v22.get(0).toString());
                    }
                    if (v22.get(1) != null) {
                        pojo.setCustName(v22.get(1).toString());
                    }
                    if (v22.get(2) != null) {
                        pojo.setEnterBy(v22.get(2).toString());
                    }
                    if (v22.get(3) != null) {
                        pojo.setAuthBy(v22.get(3).toString());
                    }
                    if (v22.get(4) != null) {
                        pojo.setDetails(v22.get(4).toString());
                    }
                    pojo.setHeading("Account Status Changed");
                    dayActivityList.add(pojo);
                }
            }
            return dayActivityList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List checkHolidayOnADate(String todayDate, String orgBrnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("SELECT DATE FROM bankdays WHERE DAYENDFLAG='Y' AND DAYBEGINFLAG='H' AND DATE='" + todayDate + "' AND BRNCODE = '" + orgBrnCode + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String maxWorkingDate(String date, String orgBrnCode) throws ApplicationException {
        try {
            String maxDt = "";
            List list = em.createNativeQuery("SELECT COALESCE(MAX(DATE),'') FROM bankdays WHERE DATE<'" + date + "' AND DAYBEGINFLAG='N' AND DAYENDFLAG='Y' AND BRNCODE='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                Vector element = (Vector) list.get(0);
                maxDt = element.get(0).toString();
            }
            return maxDt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public boolean isHolidayDate(String date, String orgBrnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("SELECT DAYBEGINFLAG FROM bankdays WHERE DATE='" + date + "' AND BRNCODE='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                String dbFlag = ele.get(0).toString();

                if (dbFlag.equalsIgnoreCase("H")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getAllBranchCodeExceptHO() throws ApplicationException {
        List branchList = new ArrayList();
        try {
            branchList = em.createNativeQuery("select brncode,a.alphacode from bnkadd a,branchmaster b where a.alphacode=b.alphacode and brncode<>'90'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return branchList;
    }

    public List getAllBranchCode() throws com.cbs.exception.ApplicationException {
        List branchList = new ArrayList();
        try {
            branchList = em.createNativeQuery("select brncode,alphacode from branchmaster where alphacode not in('CELL') order by brncode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return branchList;
    }

    /**
     * ************************LOAN SI NOT EXECUTED
     * CHARGE*************************
     */
    public void pendingLoanSICharge(String toDate, String orgBrnCode, String userName) throws ApplicationException {
        try {
            String lastDt = ymd.format(CbsUtil.getLastDateOfMonth(ymd.parse(toDate)));
            String firstDt = CbsUtil.getFirstDateOfGivenDate(ymd.parse(toDate));
            int servTaxApplyCode = ftsPost43Remote.getCodeForReportName("STAXMODULE_ACTIVE");

            if (servTaxApplyCode == 1) {
                List branchList = em.createNativeQuery("SELECT BRNCODE FROM branchmaster").getResultList();
                for (int i = 0; i < branchList.size(); i++) {
                    Vector brElement = (Vector) branchList.get(i);
                    String brncode = brElement.get(0).toString();
                    brncode = (brncode.length() < 2) ? ("0" + brncode) : brncode;
                    List acSICodeLst = em.createNativeQuery("select distinct acctcode,glheadmisc,charges from parameterinfo_miscincome "
                            + "where purpose = 'LOAN-SI-CHARGES'").getResultList();
                    if (!acSICodeLst.isEmpty()) {
                        Map<String, Double> map = new HashMap<String, Double>();
                        double totalSIChgs = 0;
                        double totalSIChgsIgst = 0;
                        for (int k = 0; k < acSICodeLst.size(); k++) {
                            Vector siVec = (Vector) acSICodeLst.get(k);
                            String acctCode = siVec.get(0).toString();
                            String plHead = brncode + siVec.get(1).toString() + "01";
                            double chg = Double.parseDouble(siVec.get(2).toString());
                            double totPlChg = 0;
                            float trsNo = ftsPost43Remote.getTrsNo();
                            float tokenNo = 0;
                            List list1 = em.createNativeQuery("select s.toacno, ifnull(cm.mailStateCode,'') as stateCode,ifnull(br.State,'') as brState "
                                    + "from standins_transpending s "
                                    + "left join accounttypemaster a on substring(s.toacno,3,2)=a.acctcode "
                                    + "left join accountmaster am on am.acno=s.toacno "
                                    + "left join branchmaster br on br.brncode=cast(substring(s.toacno,1,2) as unsigned) "
                                    + "left join customerid ci on am.acno=ci.acno "
                                    + "left join cbs_customer_master_detail cm on ci.CustId = cast(cm.customerid as unsigned) "
                                    + "where effdate between '" + firstDt + "' and '" + lastDt + "' and substring(s.toacno,3,2) = '" + acctCode + "' "
                                    + "and substring(s.toacno,1,2)='" + brncode + "' and am.accstatus not in (11,12,13) "
                                    + "and s.toacno not in (select toacno from standins_transexecuted where processdate between '" + firstDt + "' and '" + lastDt + "' "
                                    + "and substring(toacno,3,2) = '" + acctCode + "' and substring(toacno,1,2)='" + brncode + "') order by s.toacno").getResultList();
                            if (!list1.isEmpty()) {
                                for (int j = 0; j < list1.size(); j++) {
                                    Vector v1 = (Vector) list1.get(j);
                                    String acno = v1.get(0).toString();
                                    String custState = v1.get(1).toString();
                                    String branchState = v1.get(2).toString();
                                    double sTax = 0d;
                                    if (custState.equalsIgnoreCase(branchState)) {
                                        map = interBrTxn.getTaxComponent(chg, toDate);
                                        Set<Map.Entry<String, Double>> set = map.entrySet();
                                        Iterator<Map.Entry<String, Double>> it = set.iterator();
                                        while (it.hasNext()) {
                                            Map.Entry entry = it.next();
                                            sTax = sTax + Double.parseDouble(entry.getValue().toString());
                                        }
                                        totalSIChgs = totalSIChgs + chg;
                                    } else {
                                        map = interBrTxn.getIgstTaxComponent(chg, toDate);
                                        Set<Map.Entry<String, Double>> set = map.entrySet();
                                        Iterator<Map.Entry<String, Double>> it = set.iterator();
                                        while (it.hasNext()) {
                                            Map.Entry entry = it.next();
                                            sTax = sTax + Double.parseDouble(entry.getValue().toString());
                                        }
                                        totalSIChgsIgst = totalSIChgsIgst + chg;
                                    }

                                    float recNo = ftsPost43Remote.getRecNo();
                                    String acRecon = ftsPost43Remote.insertRecons(ftsPost43Remote.getAccountNature(acno), acno, 1, chg, toDate, toDate,
                                            2, "Pending SI Charges", "SYSTEM", trsNo, null, recNo, "Y", userName,
                                            8, 3, "", null, tokenNo, null, "A", 1, null, null, null, null, brncode, brncode, 0, null, "", "");
                                    if (!acRecon.equals("TRUE")) {
                                        throw new ApplicationException("Problem in Insertion in Pending SI Charges For " + acno);
                                    }

                                    String l_result = ftsPost43Remote.updateBalance(ftsPost43Remote.getAccountNature(acno), acno, 0, (chg + sTax), "Y", "N");
                                    if (!l_result.equals("TRUE")) {
                                        throw new ApplicationException("Problem in Updating balance :");
                                    }

                                    Query insertQueryTrf = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,Dramt,CrAmt,Ty,TranType,"
                                            + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                                            + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                                            + "values ('" + acno + "','" + ftsPost43Remote.getCustName(acno) + "','" + toDate + "'," + chg + ",0,1,2," + recNo + ","
                                            + trsNo + ",'',3,1,'Y','SYSTEM','" + userName + "',null,8"
                                            + "," + tokenNo + ",'A', CURRENT_TIMESTAMP,'Pending SI Charges',0,null,'" + brncode + "','"
                                            + brncode + "','" + toDate + "','','')");
                                    int varChgRet = insertQueryTrf.executeUpdate();
                                    if (varChgRet < 0) {
                                        throw new ApplicationException("Insert Problem in recon_trf_d for Ac No:" + acno);
                                    }

                                    if (sTax > 0) {
                                        recNo = ftsPost43Remote.getRecNo();
                                        String sTaxRecon = ftsPost43Remote.insertRecons(ftsPost43Remote.getAccountNature(acno), acno, 1, sTax, toDate, toDate,
                                                2, "GST for Pending SI Charges", "SYSTEM", trsNo, null, recNo, "Y", userName,
                                                71, 3, "", null, tokenNo, null, "A", 1, null, null, null, null, brncode, brncode, 0, null, "", "");

                                        if (!sTaxRecon.equals("TRUE")) {
                                            throw new ApplicationException("Problem in Insertion in recons :" + acno);
                                        }

                                        Query insertQuerySTax = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,Dramt,CrAmt,Ty,TranType,"
                                                + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                                                + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                                                + "values ('" + acno + "','" + ftsPost43Remote.getCustName(acno) + "','" + toDate + "'," + sTax + ",0,1,2," + recNo + ","
                                                + trsNo + ",'',3,1,'Y','SYSTEM','" + userName + "',null,71"
                                                + "," + tokenNo + ",'A', CURRENT_TIMESTAMP,'GST for Pending SI Charges',0,null,'" + brncode + "',"
                                                + "'" + brncode + "','" + toDate + "','','')");
                                        int varStax = insertQuerySTax.executeUpdate();
                                        if (varStax < 0) {
                                            throw new ApplicationException("Insert Problem in recon_trf_d for Ac No:" + acno);
                                        }
                                    }
                                    totPlChg = totPlChg + chg;
                                }

                                float recNo = ftsPost43Remote.getRecNo();
                                String glReconAc = ftsPost43Remote.insertRecons(ftsPost43Remote.getAccountNature(plHead), plHead, 0, totPlChg, toDate, toDate,
                                        2, "Pending SI Charges", "SYSTEM", trsNo, null, recNo, "Y", userName, 8, 3, "", null, tokenNo, null, "A", 1, null, null, null, null,
                                        brncode, brncode, 0, null, "", "");

                                if (!glReconAc.equals("TRUE")) {
                                    throw new ApplicationException("Problem in Insertion in Pending SI Charges For " + plHead);
                                }

                                String l_result = ftsPost43Remote.updateBalance(ftsPost43Remote.getAccountNature(plHead), plHead, totPlChg, 0, "Y", "N");
                                if (!l_result.equals("TRUE")) {
                                    throw new ApplicationException("Problem in Updating balance :");
                                }

                                Query insertQueryTrf = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,Dramt,CrAmt,Ty,TranType,"
                                        + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                                        + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                                        + "values ('" + plHead + "','" + ftsPost43Remote.getCustName(plHead) + "','" + toDate + "',0," + totPlChg + ",0,2," + recNo + ","
                                        + trsNo + ",'',3,1,'Y','SYSTEM','" + userName + "',null,8"
                                        + "," + tokenNo + ",'A', CURRENT_TIMESTAMP,'Pending SI Charges',0,null,'" + brncode + "','"
                                        + brncode + "','" + toDate + "','','')");
                                int varChgRet = insertQueryTrf.executeUpdate();
                                if (varChgRet < 0) {
                                    throw new ApplicationException("Insert Problem in recon_trf_d for Ac No:" + plHead);
                                }

                                map = interBrTxn.getTaxComponent(totalSIChgs, toDate);
                                Set<Map.Entry<String, Double>> set = map.entrySet();
                                Iterator<Map.Entry<String, Double>> it = set.iterator();
                                while (it.hasNext()) {
                                    Map.Entry entry = it.next();
                                    String[] keyArray = entry.getKey().toString().split(":");
                                    String description = keyArray[0];
                                    String taxHead = brncode + keyArray[1];
                                    String mainDetails = description.toUpperCase() + " for Pending SI Chg.";
                                    double taxAmount = Double.parseDouble(entry.getValue().toString());

                                    recNo = ftsPost43Remote.getRecNo();
                                    String glTRecon = ftsPost43Remote.insertRecons("PO", taxHead, 0, taxAmount, toDate, toDate, 2, mainDetails, "SYSTEM",
                                            trsNo, null, recNo, "Y", userName, 71, 3, "", null, tokenNo, null, "A", 1, null, null, null, null, brncode, brncode,
                                            0, null, "", "");

                                    if (!glTRecon.equals("TRUE")) {
                                        throw new ApplicationException("Problem in Insertion in recons :" + l_result);
                                    }

                                    String l_result1 = ftsPost43Remote.updateBalance(ftsPost43Remote.getAccountNature(taxHead), taxHead, taxAmount, 0, "Y", "N");
                                    if (!l_result1.equals("TRUE")) {
                                        throw new ApplicationException("Problem in Updating balance :");
                                    }

                                    Query insertRetChgs = em.createNativeQuery("insert into recon_trf_d( acno , custname, dt, Dramt, CrAmt, Ty, TranType,"
                                            + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                                            + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                                            + "values ('" + taxHead + "','" + ftsPost43Remote.getCustName(taxHead) + "','" + toDate + "',0," + taxAmount + ",0,2," + recNo + ","
                                            + trsNo + ",'',3,1,'Y','SYSTEM','" + userName + "',null,71 ," + tokenNo + ",'A', CURRENT_TIMESTAMP,'" + mainDetails + "',0,null,'" + brncode + "','"
                                            + brncode + "','" + toDate + "','','')");
                                    int varChgRetTax = insertRetChgs.executeUpdate();
                                    if (varChgRetTax < 0) {
                                        throw new ApplicationException("Insert Problem in recon_trf_d for Ac No:" + taxHead);
                                    }
                                }

                                if (totalSIChgsIgst > 0) {
                                    map = interBrTxn.getIgstTaxComponent(totalSIChgsIgst, toDate);
                                    set = map.entrySet();
                                    it = set.iterator();
                                    while (it.hasNext()) {
                                        Map.Entry entry = it.next();
                                        String[] keyArray = entry.getKey().toString().split(":");
                                        String description = keyArray[0];
                                        String taxHead = brncode + keyArray[1];
                                        String mainDetails = description.toUpperCase() + " for Pending SI Chg.";
                                        double taxAmount = Double.parseDouble(entry.getValue().toString());

                                        recNo = ftsPost43Remote.getRecNo();
                                        String glTRecon = ftsPost43Remote.insertRecons("PO", taxHead, 0, taxAmount, toDate, toDate, 2, mainDetails, "SYSTEM", trsNo, null, recNo, "Y", userName,
                                                71, 3, "", null, tokenNo, null, "A", 1, null, null, null, null, brncode, brncode, 0, null, "", "");

                                        if (!glTRecon.equals("TRUE")) {
                                            throw new ApplicationException("Problem in Insertion in recons :" + taxHead);
                                        }

                                        String l_result1 = ftsPost43Remote.updateBalance(ftsPost43Remote.getAccountNature(taxHead), taxHead, taxAmount, 0, "Y", "N");
                                        if (!l_result1.equals("TRUE")) {
                                            throw new ApplicationException("Problem in Updating balance :");
                                        }

                                        Query insertRetChgsIgst = em.createNativeQuery("insert into recon_trf_d( acno , custname, dt, Dramt, CrAmt, Ty, TranType,"
                                                + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                                                + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                                                + "values ('" + taxHead + "','" + ftsPost43Remote.getCustName(taxHead) + "','" + toDate + "',0," + taxAmount + ",0,2," + recNo + ","
                                                + trsNo + ",'',3,1,'Y','SYSTEM','" + userName + "',null,71 ," + tokenNo + ",'A', CURRENT_TIMESTAMP,'" + mainDetails + "',0,null,'" + brncode + "','"
                                                + brncode + "','" + toDate + "','','')");
                                        int varChgRetIgst = insertRetChgsIgst.executeUpdate();
                                        if (varChgRetIgst < 0) {
                                            throw new ApplicationException("Insert Problem in recon_trf_d for Ac No:" + taxHead);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                List branchList = em.createNativeQuery("SELECT BRNCODE FROM branchmaster").getResultList();
                for (int i = 0; i < branchList.size(); i++) {
                    Vector brElement = (Vector) branchList.get(i);
                    String brncode = brElement.get(0).toString();
                    brncode = (brncode.length() < 2) ? ("0" + brncode) : brncode;

                    List acSICodeLst = em.createNativeQuery("select distinct acctcode,glheadmisc,charges from parameterinfo_miscincome "
                            + "where purpose = 'LOAN-SI-CHARGES'").getResultList();
                    if (!acSICodeLst.isEmpty()) {
                        for (int k = 0; k < acSICodeLst.size(); k++) {
                            Vector siVec = (Vector) acSICodeLst.get(k);
                            String acctCode = siVec.get(0).toString();
                            String plHead = brncode + siVec.get(1).toString() + "01";
                            double chg = Double.parseDouble(siVec.get(2).toString());
                            double totPlChg = 0;
                            float trsNo = ftsPost43Remote.getTrsNo();
                            float tokenNo = 0;
                            List list1 = em.createNativeQuery("select s.toacno from standins_transpending s, accountmaster a where s.effdate between '" + firstDt + "' "
                                    + " and '" + lastDt + "' and substring(s.toacno,3,2) = '" + acctCode + "' and substring(s.toacno,1,2)='" + brncode + "' "
                                    + " and s.toacno = a.acno and a.accstatus not in (11,12,13) and s.toacno not in (select toacno from standins_transexecuted "
                                    + " where processdate between '" + firstDt + "' and '" + lastDt + "' and substring(toacno,3,2) = '" + acctCode + "' "
                                    + " and substring(toacno,1,2)='" + brncode + "')").getResultList();
                            if (!list1.isEmpty()) {
                                for (int j = 0; j < list1.size(); j++) {
                                    Vector v1 = (Vector) list1.get(j);
                                    String acno = v1.get(0).toString();

                                    float recNo = ftsPost43Remote.getRecNo();
                                    String acRecon = ftsPost43Remote.insertRecons(ftsPost43Remote.getAccountNature(acno), acno, 1, chg, toDate, toDate,
                                            2, "Pending SI Charges", "SYSTEM", trsNo, null, recNo, "Y", userName,
                                            8, 3, "", null, tokenNo, null, "A", 1, null, null, null, null, brncode, brncode, 0, null, "", "");
                                    if (!acRecon.equals("TRUE")) {
                                        throw new ApplicationException("Problem in Insertion in Pending SI Charges For " + acno);
                                    }

                                    String l_result = ftsPost43Remote.updateBalance(ftsPost43Remote.getAccountNature(acno), acno, 0, chg, "Y", "N");
                                    if (!l_result.equals("TRUE")) {
                                        throw new ApplicationException("Problem in Updating balance :");
                                    }

                                    Query insertQueryTrf = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,Dramt,CrAmt,Ty,TranType,"
                                            + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                                            + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                                            + "values ('" + acno + "','" + ftsPost43Remote.getCustName(acno) + "','" + toDate + "'," + chg + ",0,1,2," + recNo + ","
                                            + trsNo + ",'',3,1,'Y','SYSTEM','" + userName + "',null,8"
                                            + "," + tokenNo + ",'A', CURRENT_TIMESTAMP,'Pending SI Charges',0,null,'" + brncode + "','"
                                            + brncode + "','" + toDate + "','','')");
                                    int varChgRet = insertQueryTrf.executeUpdate();
                                    if (varChgRet < 0) {
                                        throw new ApplicationException("Insert Problem in recon_trf_d for Ac No:" + acno);
                                    }
                                    totPlChg = totPlChg + chg;
                                }

                                float recNo = ftsPost43Remote.getRecNo();
                                String glReconAc = ftsPost43Remote.insertRecons(ftsPost43Remote.getAccountNature(plHead), plHead, 0, totPlChg, toDate, toDate,
                                        2, "Pending SI Charges", "SYSTEM", trsNo, null, recNo, "Y", userName, 8, 3, "", null, tokenNo, null, "A", 1, null, null, null, null,
                                        brncode, brncode, 0, null, "", "");

                                if (!glReconAc.equals("TRUE")) {
                                    throw new ApplicationException("Problem in Insertion in Pending SI Charges For " + plHead);
                                }

                                String l_result = ftsPost43Remote.updateBalance(ftsPost43Remote.getAccountNature(plHead), plHead, totPlChg, 0, "Y", "N");
                                if (!l_result.equals("TRUE")) {
                                    throw new ApplicationException("Problem in Updating balance :");
                                }

                                Query insertQueryTrf = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,Dramt,CrAmt,Ty,TranType,"
                                        + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                                        + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                                        + "values ('" + plHead + "','" + ftsPost43Remote.getCustName(plHead) + "','" + toDate + "',0," + totPlChg + ",0,2," + recNo + ","
                                        + trsNo + ",'',3,1,'Y','SYSTEM','" + userName + "',null,8"
                                        + "," + tokenNo + ",'A', CURRENT_TIMESTAMP,'Pending SI Charges',0,null,'" + brncode + "','"
                                        + brncode + "','" + toDate + "','','')");
                                int varChgRet = insertQueryTrf.executeUpdate();
                                if (varChgRet < 0) {
                                    throw new ApplicationException("Insert Problem in recon_trf_d for Ac No:" + plHead);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public boolean isYearEndCompleted(String tDate, String orgBrnCode) throws ApplicationException {
        try {

            List list1 = em.createNativeQuery("select yearendflag from yearend where brncode='" + orgBrnCode + "' and maxdate='" + tDate + "'").getResultList();
            if (list1.isEmpty()) {
                throw new ApplicationException("Data does not exist in yearend");
            }
            Vector v10 = (Vector) list1.get(0);

            String yearEndFlag = v10.get(0).toString();
            if (yearEndFlag.equals("Y")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
