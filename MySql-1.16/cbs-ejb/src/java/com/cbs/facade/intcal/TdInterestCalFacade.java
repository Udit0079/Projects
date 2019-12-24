/*
 * CREATED BY     :   ROHIT KRISHNA GUPTA
 * CREATION DATE  :   14 DECEMBER 2010
 * Modify By      :   Dhirendra Singh
 */
package com.cbs.facade.intcal;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.TdIntDetail;
import com.cbs.dto.sms.TdInterestSmsTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.other.AutoTermDepositRenewalRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

@Stateless(mappedName = "TdInterestCalFacade")
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class TdInterestCalFacade implements TdInterestCalFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsMethods;
    @EJB
    InterBranchTxnFacadeRemote ftsRemoteMethods;
    @EJB
    TdReceiptManagementFacadeRemote orgFdInt;
    @EJB
    RbiReportFacadeRemote rbiReportFacade;
    @EJB
    AutoTermDepositRenewalRemote autoTermDepositFacade;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public List<TdIntDetail> interestCalculation(String fromdt, String todt, String intopt, String acctype, String brCode) throws ApplicationException {
        List<TdIntDetail> tdIntDetailList = new ArrayList<TdIntDetail>();
        try {
            String accno = "";
            float interest = 0.0f;
            float totIntTillToday = 0;
            float totmon = 0.0f;
            //int totquarter = 0;
            String mtdt1 = "";

            Float totdays = 0.0f;
            Float roi = 0.0f;
            String nextintpaydt = "";
            String matdt = "";

            float pamt = 0.0f;
            Float voucherno = 0.0f;
            String nextintpaydt1;
            Float matDays = 0.0f;

            int lastQuarter = 0;
            Integer year = 0;
            String inttoacno = "";
            Integer months = 0;

            String totdays1;
            Float cumuprinamt = 0.0f;
            float intAmt = 0.0f;
            Float seqno = 0.0f;

            Long quarterDays = 0l;
            String custName = "";
            long mnthdays = 0l;
            if (intopt.equalsIgnoreCase("M")) {
                mnthdays = CbsUtil.dayDiff(ymd.parse(fromdt), ymd.parse(todt)) + 1;
            }
            int mm = 0;
            int tmp = CbsUtil.datePart("M", todt);
            if (tmp > 2) {
                mm = tmp - 2;
            } else {
                mm = tmp;
            }
            String ddfrom = "";
            if (mm < 10) {
                ddfrom = String.valueOf(CbsUtil.datePart("Y", todt)) + "0" + String.valueOf(mm) + "01";
            } else {
                ddfrom = String.valueOf(CbsUtil.datePart("Y", todt)) + String.valueOf(mm) + "01";
            }
            Long dd = CbsUtil.dayDiff(ymd.parse(ddfrom), ymd.parse(todt));
            dd = dd + 1;
            Object bnkNameObj = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where "
                    + "b.alphacode=br.alphacode and br.brncode=" + Integer.parseInt(brCode)).getSingleResult();

            String bnkName = ((Vector) bnkNameObj).elementAt(0).toString();
            String bnkAddress = ((Vector) bnkNameObj).elementAt(1).toString();

            List cursorLt = em.createNativeQuery("select tm.seqno,tm.acno, substring(am.custName,1,28),"
                    + " tm.voucherno,tm.roi,date_format(tm.nextintpaydt,'%Y%m%d'),date_format(tm.matdt,'%Y%m%d'),"
                    + "tm.prinamt,ifnull(tm.years,0), ifnull(tm.months,0),ifnull(tm.days,0),tm.cumuprinamt,tm.inttoAcno,"
                    + "tm.TDSDeducted, date_format(tm.FDDT,'%Y%m%d'),tm.period From td_vouchmst tm, td_accountmaster am Where "
                    + "am.acno=tm.acno and tm.intopt='" + intopt + "' and tm.status<>'C'  and am.accttype='"
                    + acctype + "' AND tm.nextIntPayDt< tm.matdt AND tm.nextintpaydt<='" + todt + "' and tm.roi<>0 and "
                    + "am.CurBrCode='" + brCode + "' ").getResultList();
            // +"tm.acno in ('011800002401','011800002501')").getResultList();

            if (cursorLt.size() > 0) {
                if (!intopt.equalsIgnoreCase("C")) {
                    for (int i = 0; i < cursorLt.size(); i++) {
                        Vector curV = (Vector) cursorLt.get(i);

                        seqno = Float.parseFloat(curV.get(0).toString());
                        accno = curV.get(1).toString();
                        custName = curV.get(2).toString();
                        voucherno = Float.parseFloat(curV.get(3).toString());
                        roi = Float.parseFloat(curV.get(4).toString());

                        nextintpaydt = curV.get(5).toString();
                        matdt = curV.get(6).toString();
                        pamt = Float.parseFloat(curV.get(7).toString());

                        year = Integer.parseInt(curV.get(8).toString());
                        months = Integer.parseInt(curV.get(9).toString());
                        //days = Integer.parseInt(curV.get(10).toString());

                        cumuprinamt = Float.parseFloat(curV.get(11).toString());
                        inttoacno = curV.get(12).toString();
                        //tdsdeduct = Float.parseFloat(curV.get(13).toString());
                        String fdDt = (curV.get(14).toString());
                        String period = (curV.get(15).toString());

                        if (intopt.equalsIgnoreCase("M")) {
                            long dateDiff = CbsUtil.dayDiff(ymd.parse(todt), ymd.parse(matdt));
                            int dtPart = CbsUtil.datePart("D", nextintpaydt);

                            if (dtPart != 1 && dateDiff > 0) {
                                String tempDt = CbsUtil.monthAdd(nextintpaydt, 1);
                                nextintpaydt1 = CbsUtil.dateAdd(tempDt.substring(0, 4) + tempDt.substring(4, 6) + "01", -1);

                                /* calculate the difference between nextintpaydt and lastday of the month  */
                                Long totaldays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1)) + 1;
                                totmon = (totaldays.floatValue() / mnthdays) + CbsUtil.monthDiff(ymd.parse(nextintpaydt1), ymd.parse(todt));
                                totdays = totaldays.floatValue() + CbsUtil.dayDiff(ymd.parse(nextintpaydt1), ymd.parse(todt));
                            }

                            /*B-- IF FROM DT IS FIRST DATE OF THE MONTH  AND MATURITY PERIOD LIES AFTER TODATE  */
                            if (dtPart == 1 && dateDiff > 0) {
                                totmon = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                                Long tmpTotDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                                totdays = tmpTotDay.floatValue();
                            }
                            /*C--- IF FROM DT IS NOT THE FIRST DT OF THE MONTH AND MATURITY DATE LIES BEFoRE TO DATE */
                            if (dtPart != 1 && dateDiff < 0) {
                                String tempDt = CbsUtil.monthAdd(nextintpaydt, 1);
                                nextintpaydt1 = CbsUtil.dateAdd(tempDt.substring(0, 4) + tempDt.substring(4, 6) + "01", -1);
                                mtdt1 = matdt.substring(0, 4) + matdt.substring(4, 6) + "01";

                                /*calculate  the no of days between first of maturity month and maturity date */
                                Long tempMatDays = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                                matDays = tempMatDays.floatValue();

                                totdays = matDays + CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1)) + 1;
                                totmon = totdays / mnthdays + CbsUtil.monthDiff(ymd.parse(CbsUtil.monthAdd(nextintpaydt1, 1)), ymd.parse(matdt));

                                Long tmpTotDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                                totdays = tmpTotDay.floatValue();
                            }
                            if (dtPart == 1 && dateDiff < 0) {
                                mtdt1 = matdt.substring(0, 4) + matdt.substring(4, 6) + "01";
                                totmon = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(mtdt1));

                                Long tmpTotDay = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                                matDays = tmpTotDay.floatValue();
                                totdays = totdays + matDays;
                                totmon = matDays / mnthdays + totmon;

                                tmpTotDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                                totdays = tmpTotDay.floatValue();
                            }
                            interest = 0.0f;
                            interest = (pamt - (pamt * (1 / (1 + roi / 1200)))) * totmon;
                            //Code for checking round off issue.
                            if (dateDiff < 4 && dateDiff > 0) {
                                totIntTillToday = Float.parseFloat(orgFdInt.orgFDInterest(intopt, roi, fdDt, todt, pamt, period, brCode));
                                List paidIntList = em.createNativeQuery("select ifnull(sum(ifnull(interest,0)),0) from td_interesthistory where "
                                        + "acno='" + accno + "' and voucherno=" + voucherno).getResultList();
                                Vector vect = (Vector) paidIntList.get(0);
                                float intPaid = Float.parseFloat(vect.get(0).toString());
                                float balInt = totIntTillToday - intPaid;
                                if (interest > balInt) {
                                    interest = balInt;
                                }
                            }
                        }
                        if (intopt.equalsIgnoreCase("Q")) {
                            long dtDiff = CbsUtil.dayDiff(ymd.parse(ddfrom), ymd.parse(fdDt));
                            long dateDiff = CbsUtil.dayDiff(ymd.parse(todt), ymd.parse(matdt));
                            if (dateDiff > 0) {
                                Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                                totdays = tmpDays.floatValue();
                            } else if (dateDiff <= 0) {
                                Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                                totdays = tmpDays.floatValue();
                            }
                            if (dtDiff > 0) {
                                interest = 0.0f;
                                interest = (pamt * (roi / 36500)) * totdays;
                            } else {
                                interest = 0.0f;
                                Float totquarter = totdays / dd;
                                interest = (pamt * (roi / 400)) * totquarter;
                            }
                            //Code for checking round off issue.
                            if (dateDiff < 4 && dateDiff > 0) {
                                totIntTillToday = Float.parseFloat(orgFdInt.orgFDInterest(intopt, roi, fdDt, todt, pamt, period, brCode));
                                List paidIntList = em.createNativeQuery("select ifnull(sum(ifnull(interest,0)),0) from td_interesthistory where "
                                        + "acno='" + accno + "' and voucherno=" + voucherno).getResultList();
                                Vector vect = (Vector) paidIntList.get(0);
                                float intPaid = Float.parseFloat(vect.get(0).toString());
                                float balInt = totIntTillToday - intPaid;
                                if (interest > balInt) {
                                    interest = balInt;
                                }
                            }
                        }
                        if (intopt.equalsIgnoreCase("S") || intopt.equalsIgnoreCase("Y")) {
                            long dateDiff = CbsUtil.dayDiff(ymd.parse(todt), ymd.parse(matdt));
                            int dtPart = CbsUtil.datePart("D", nextintpaydt);
                            if (dtPart != 1 && dateDiff >= 0) {
                                String tempDt = CbsUtil.monthAdd(nextintpaydt, 1);
                                nextintpaydt1 = CbsUtil.dateAdd(tempDt.substring(0, 4) + tempDt.substring(4, 6) + "01", -1);

                                Long totaldays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1)) + 1;
                                totdays = totaldays.floatValue();
                                totmon = (totdays / 30) + CbsUtil.monthDiff(ymd.parse(nextintpaydt1), ymd.parse(todt));
                                totdays = totdays + CbsUtil.dayDiff(ymd.parse(nextintpaydt1), ymd.parse(todt));

                                if (dateDiff == 0) {
                                    totdays = totdays - 1;
                                }
                            }
                            if (dtPart == 1 && dateDiff >= 0) {
                                totmon = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                                Long tmpDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                                totdays = tmpDay.floatValue();
                                if (dateDiff == 0) {
                                    totdays = totdays - 1;
                                }
                            }
                            if (dtPart != 1 && dateDiff < 0) {
                                String tempDt = CbsUtil.monthAdd(nextintpaydt, 1);
                                nextintpaydt1 = CbsUtil.dateAdd(tempDt.substring(0, 4) + tempDt.substring(4, 6) + "01", -1);
                                mtdt1 = matdt.substring(0, 4) + matdt.substring(4, 6) + "01";

                                Long tmpMatDays = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                                matDays = tmpMatDays.floatValue();
                                totdays = matDays + CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1)) + 1;

                                totmon = (totdays / 30) + CbsUtil.monthDiff(ymd.parse(CbsUtil.monthAdd(nextintpaydt1, 1)), ymd.parse(matdt));
                                Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                                totdays = tmpDays.floatValue();
                            }

                            if (dtPart == 1 && dateDiff < 0) {
                                mtdt1 = matdt.substring(0, 4) + matdt.substring(4, 6) + "01";
                                totmon = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(mtdt1));

                                Long tmpTotDay = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                                matDays = tmpTotDay.floatValue();
                                totdays = totdays + matDays;
                                totmon = (matDays / 30) + totmon;

                                tmpTotDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                                totdays = tmpTotDay.floatValue();
                            }
                            interest = 0.0f;
                            if (year == 0 && Math.floor(months) == 0) {
                                interest = (pamt * roi * totdays) / 36500;
                            }
                            if (year != 0 || months != 0) {
                                interest = (pamt * roi * totmon / 1200);
                            }
                        }
                        if (interest > 0) {
                            List acWiseTdsList = em.createNativeQuery("select ifnull(sum(ifnull(tds,0)),0) from tds_reserve_history where acno = '"
                                    + accno + "' and VoucherNo = '" + voucherno + "' and recovered ='NR' and todt<='" + todt + "' and intOpt = '"
                                    + intopt + "'").getResultList();
                            double tds = 0d;
                            if (acWiseTdsList.size() > 0) {
                                Vector acWiseTdsVect = (Vector) acWiseTdsList.get(0);
                                tds = Double.parseDouble(acWiseTdsVect.get(0).toString());
                            }
                            TdIntDetail tdIntDetail = new TdIntDetail();
                            tdIntDetail.setMsg("TRUE");
                            tdIntDetail.setBnkName(bnkName);
                            tdIntDetail.setBnkAdd(bnkAddress);
                            tdIntDetail.setAcno(accno);
                            tdIntDetail.setCustName(custName);
                            tdIntDetail.setVoucherNo(Math.round(voucherno));

                            tdIntDetail.setpAmt(pamt);
                            tdIntDetail.setRoi(roi);
                            tdIntDetail.setFromDt(nextintpaydt);

                            tdIntDetail.setMatDt(matdt);
                            //tdIntDetail.setInterest(CbsUtil.round(interest, 0));
                            double tmpInt = Math.floor(interest);
                            tdIntDetail.setInterest(tmpInt);

                            tdIntDetail.setTds(tds);
                            tdIntDetail.setIntPaid(tmpInt - tds);

                            tdIntDetail.setIntToAcno(inttoacno);

                            tdIntDetail.setToDt(todt);
                            tdIntDetail.setSeqno(seqno);
                            tdIntDetail.setIntOpt(intopt);
                            tdIntDetail.setCumuprinamt(cumuprinamt);
                            tdIntDetailList.add(tdIntDetail);
                        }
                    }
                }
                if (intopt.equalsIgnoreCase("C")) {
                    for (int i = 0; i < cursorLt.size(); i++) {
                        Vector curV = (Vector) cursorLt.get(i);
                        seqno = Float.parseFloat(curV.get(0).toString());
                        accno = curV.get(1).toString();
                        custName = curV.get(2).toString();
                        voucherno = Float.parseFloat(curV.get(3).toString());
                        roi = Float.parseFloat(curV.get(4).toString());

                        nextintpaydt = curV.get(5).toString();
                        matdt = curV.get(6).toString();
                        float pamt1 = Float.parseFloat(curV.get(7).toString());

                        year = Integer.parseInt(curV.get(8).toString());
                        months = Integer.parseInt(curV.get(9).toString());
                        //days = Integer.parseInt(curV.get(10).toString());

                        cumuprinamt = Float.parseFloat(curV.get(11).toString());
                        inttoacno = curV.get(12).toString();
                        // tdsdeduct = Float.parseFloat(curV.get(12).toString());
                        pamt = cumuprinamt;

                        long dtPart = CbsUtil.datePart("D", nextintpaydt);
                        long mPart = CbsUtil.datePart("M", nextintpaydt);
                        long dtDiff = CbsUtil.dayDiff(ymd.parse(todt), ymd.parse(matdt));
                        int totquarter = 0;
                        if ((dtPart != 1 && dtDiff >= 0) || ((dtPart == 1) && (mPart != 4 && mPart != 7 && mPart != 10 && mPart != 1) && dtDiff >= 0)) {
                            interest = 0.0f;
                            totquarter = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) / 3;
                            lastQuarter = totquarter * 3;

                            totdays1 = CbsUtil.monthAdd(CbsUtil.dateAdd(todt, 1), -(lastQuarter));
                            Long tmpD = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(totdays1));
                            totdays = tmpD.floatValue();

                            if (totquarter == 0) {
                                interest = pamt * (1 + roi / 400) / (1 + roi / 36500 * (91 - totdays)) - pamt;
                            }
                            if (totquarter != 0) {
                                quarterDays = CbsUtil.dayDiff(ymd.parse(CbsUtil.monthAdd(totdays1, -3)), ymd.parse(totdays1));
                                interest = (pamt * (1 + roi / 400) / (1 + roi / 36500 * (91 - totdays))) - pamt;
                                intAmt = interest;
                                pamt = pamt + interest;

                                Double tmpInt = (Math.pow(1 + roi / 400, totquarter) - 1) * pamt;
                                interest = tmpInt.floatValue();
                                interest = interest + intAmt;
                            }
                        }
                        if (dtPart == 1 && (mPart == 1 || mPart == 4 || mPart == 7 || mPart == 10) && dtDiff >= 0) {
                            totquarter = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(CbsUtil.monthAdd(todt, 1)));
                            totquarter = totquarter / 3;
                            Double tmpInt = (Math.pow(1 + roi / 400, totquarter) - 1) * pamt;
                            interest = tmpInt.floatValue();
                        }
                        if (dtPart == 1 && (mPart == 1 || mPart == 4 || mPart == 7 || mPart == 10) && dtDiff < 0) {
                            totquarter = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                            totquarter = totquarter / 3;
                            if (totquarter == 0) {
                                Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                                totdays = tmpDays.floatValue();
                                interest = pamt * roi * totdays / 36500;
                            } else {
                                Double tmpInt = (Math.pow(1 + roi / 400, totquarter) - 1) * pamt;
                                interest = tmpInt.floatValue();
                                intAmt = interest;
                                pamt = pamt + interest;
                                totquarter = totquarter * 3;
                                mtdt1 = CbsUtil.monthAdd(nextintpaydt, totquarter);

                                Long tmpDt = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                                matDays = tmpDt.floatValue();
                                interest = pamt * roi * matDays / 36500;
                                interest = interest + intAmt;
                            }
                        }
                        if ((dtPart != 1 && dtDiff < 0) || ((dtPart == 1) && (mPart != 1 && mPart != 4 && mPart != 7 && mPart != 10) && dtDiff < 0)) {
                            nextintpaydt1 = nextintpaydt;
                            long monPart = CbsUtil.datePart("M", nextintpaydt);
                            if (monPart >= 1 && monPart <= 3) {
                                nextintpaydt1 = CbsUtil.datePart("Y", nextintpaydt) + "0401";
                            } else if (monPart >= 4 && monPart <= 6) {
                                nextintpaydt1 = CbsUtil.datePart("Y", nextintpaydt) + "0701";
                            } else if (monPart >= 7 && monPart <= 9) {
                                nextintpaydt1 = CbsUtil.datePart("Y", nextintpaydt) + "1001";
                            } else if (monPart >= 10 && monPart <= 12) {
                                nextintpaydt1 = CbsUtil.datePart("Y", CbsUtil.yearAdd(nextintpaydt, 1)) + "0101";
                            }
                            Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1));
                            totdays = tmpDays.floatValue();
                            quarterDays = CbsUtil.dayDiff(ymd.parse(CbsUtil.monthAdd(nextintpaydt1, -3)), ymd.parse(nextintpaydt1));
                            totquarter = CbsUtil.monthDiff(ymd.parse(nextintpaydt1), ymd.parse(matdt)) / 3;

                            lastQuarter = totquarter * 3;
                            nextintpaydt1 = CbsUtil.monthAdd(nextintpaydt1, lastQuarter);

                            tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt1), ymd.parse(matdt));
                            matDays = tmpDays.floatValue();
                            if ((totquarter == 0) || ((quarterDays - totdays - matDays) > 0)) {
                                interest = pamt * (1 + roi * (totdays + matDays) / 36500) / (1 + roi * matDays / 36500) - pamt;
                            } else {
                                interest = (pamt * (1 + roi / 400) / (1 + roi / 36500 * (quarterDays - totdays))) - pamt;
                            }
                            intAmt = interest;
                            pamt = pamt + interest;
                            Double tmpInt = (Math.pow(1 + roi / 400, totquarter) - 1) * pamt;
                            interest = tmpInt.floatValue();

                            intAmt = intAmt + interest;
                            pamt = pamt + interest;
                            interest = pamt * roi * matDays / 36500;
                            interest = intAmt + interest;
                        }
                        if (interest > 0) {
                            List acWiseTdsList = em.createNativeQuery("select ifnull(sum(ifnull(tds,0)),0) from tds_reserve_history where acno = '"
                                    + accno + "' and VoucherNo = '" + voucherno + "' and recovered ='NR' and todt<='" + todt + "' and intOpt = '"
                                    + intopt + "'").getResultList();
                            double tds = 0d;
                            if (acWiseTdsList.size() > 0) {
                                Vector acWiseTdsVect = (Vector) acWiseTdsList.get(0);
                                tds = Double.parseDouble(acWiseTdsVect.get(0).toString());
                            }
                            TdIntDetail tdIntDetail = new TdIntDetail();
                            tdIntDetail.setMsg("TRUE");
                            tdIntDetail.setBnkName(bnkName);
                            tdIntDetail.setBnkAdd(bnkAddress);

                            tdIntDetail.setAcno(accno);
                            tdIntDetail.setCustName(custName);
                            tdIntDetail.setVoucherNo(Math.round(voucherno));

                            tdIntDetail.setpAmt(pamt1);
                            tdIntDetail.setRoi(roi);
                            tdIntDetail.setFromDt(nextintpaydt);

                            tdIntDetail.setMatDt(matdt);
                            double tmpInt = Math.floor(interest);
                            tdIntDetail.setInterest(tmpInt);

                            tdIntDetail.setTds(tds);
                            tdIntDetail.setIntPaid(tmpInt - tds);
                            tdIntDetail.setIntToAcno(inttoacno);

                            tdIntDetail.setToDt(todt);
                            tdIntDetail.setSeqno(seqno);
                            tdIntDetail.setIntOpt(intopt);
                            tdIntDetail.setCumuprinamt(cumuprinamt);
                            tdIntDetailList.add(tdIntDetail);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return tdIntDetailList;
    }

//    public String tdMQIntPost(List<TdIntDetail> tdIntDetailsList, String acctype, String intopt, String enterBy, String td_inthead,
//            String todate, String glHead, String orgnBrCode) throws ApplicationException {
//
//        String resultMessage = "", tdsPostEnable = "N";
//        Float recno, trsno, trsno2;
//        try {
//            double totalInterest = 0d;
//            double totalTds = 0d;
//            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
//                totalInterest = totalInterest + tdIntDetail.getInterest();
//                //  totalTds = totalTds + tdIntDetail.getTds();
//            }
//            int simplePostFlag = 0;
//            List simplePostFlagList = em.createNativeQuery("SELECT ifnull(SIMPLEPOSTFLAG,0) FROM td_parameterinfo").getResultList();
//            if (!simplePostFlagList.isEmpty()) {
//                Vector simplePostFlagVect = (Vector) simplePostFlagList.get(0);
//                simplePostFlag = Integer.parseInt(simplePostFlagVect.get(0).toString());
//            }
//
//            recno = ftsMethods.getRecNo();
//
//            trsno = ftsMethods.getTrsNo();
//            trsno2 = ftsMethods.getTrsNo();
//
//            Integer rdrecon = em.createNativeQuery("insert into gl_recon (acno,dt,valueDt,dramt,ty,trantype,details,auth,enterBy,"
//                    + "authby,trsno,payby,recno,TRANDESC,IY,org_brnid,dest_brnid) values('" + td_inthead + "',date_format(now(),'%Y%m%d')" + ",date_format(now(),'%Y%m%d'),"
//                    + "" + totalInterest + ",1,8, 'Interest Amount','Y','" + enterBy + "','System'," + trsno + ",3,"
//                    + "" + recno + ",4,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
//            if (rdrecon <= 0) {
//                return "Error in GL_Entry !!!";
//            }
//
//            List reconbalanList = em.createNativeQuery("select acno from reconbalan where acno='" + td_inthead + "'").getResultList();
//            if (!reconbalanList.isEmpty()) {
//                Integer reconbalan = em.createNativeQuery("update reconbalan set balance=ifnull(balance,0)- ifnull('" + totalInterest + "',0),"
//                        + "dt=date_format(now(),'%Y%m%d') where acno='" + td_inthead + "'").executeUpdate();
//                if (reconbalan <= 0) {
//                    return "Error in Updating GL Balance !!!";
//                }
//            } else {
//                Integer reconbalan = em.createNativeQuery("insert into reconbalan(acno,balance,dt)values('" + td_inthead + "',"
//                        + "-" + totalInterest + ",date_format(now(),'%Y%m%d'))").executeUpdate();
//                if (reconbalan <= 0) {
//                    return "Error in Updating GL Balance !!!";
//                }
//            }
//
//            List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
//            if (!tdsPostEnableList.isEmpty()) {
//                Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
//                tdsPostEnable = tdsPostEnableVect.get(0).toString();
//            }
//
//            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
//                double interest = tdIntDetail.getInterest();
//                String acno = tdIntDetail.getAcno();
//                float vchNo = tdIntDetail.getVoucherNo();
//
//                String intToAcno = tdIntDetail.getIntToAcno();
//                String fromDt = tdIntDetail.getFromDt();
//                String toDt = tdIntDetail.getToDt();
//                double tds = 0d;
//                /**
//                 * ***Getting TDS Deduction from account **************
//                 */
//                if (tdsPostEnable.equalsIgnoreCase("Y")) {
//                    List acWiseTdsList = em.createNativeQuery("select ifnull(sum(ifnull(tds,0)),0) from tds_reserve_history where acno = '" + acno + "' and VoucherNo = '" + vchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'").getResultList();
//                    if (acWiseTdsList.size() > 0) {
//                        Vector acWiseTdsVect = (Vector) acWiseTdsList.get(0);
//                        tds = Double.parseDouble(acWiseTdsVect.get(0).toString());
//                        totalTds = totalTds + Double.parseDouble(acWiseTdsVect.get(0).toString());
//                    }
//
//                    /**
//                     * Code Add On 20150624 *
//                     */
//                    List tdsCloseList = em.createNativeQuery("select tds,a.voucherno from tds_reserve_history a, td_vouchmst b where a.acno = b.acno "
//                            + " and a.recovered ='NR' and b.status ='C' "
//                            + " and a.acno = '" + acno + "' and a.voucherno = b.voucherno order by txnid").getResultList();
//                    for (int cnt = 0; cnt < tdsCloseList.size(); cnt++) {
//                        Vector tdsCloseVect = (Vector) tdsCloseList.get(cnt);
//                        double cltds = Double.parseDouble(tdsCloseVect.get(0).toString());
//                        tds = tds + cltds;
//                        if (tds < interest) {
//                            totalTds = totalTds + cltds;
//                            String vchCl = tdsCloseVect.get(1).toString();
//                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', trsno = " + trsno + ", recno = " + recno + ", recoveredVch = " + vchNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
//                                    + " where acno = '" + acno + "' and VoucherNo = '" + vchCl + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
//                            int result = updateTdsQuery.executeUpdate();
//                            if (result < 0) {
//                                totalTds = totalTds - cltds;
//                                return "Error in updating tdshistory for tds ";
//                            }
//                        } else {
//                            tds = tds - cltds;
//                            break;
//                        }
//                    }
//                }
//                /**
//                 * End Of Code Add On 20150624 *
//                 */
//                /**
//                 * ***END of Getting TDS Deduction from account **************
//                 */
////                double tds = tdIntDetail.getTds();
//                // String acNat = ftsMethods.getAccountNature(acno);
//                if ((intopt.equalsIgnoreCase("S") && simplePostFlag > 0) || intopt.equalsIgnoreCase("M") || intopt.equalsIgnoreCase("Q")) {
//                    recno = ftsMethods.getRecNo();
//
//                    Integer tdrecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,cramt,voucherno,intflag,ty,trantype,"
//                            + "details,auth,enterby,authby,recno,trsno,PAYBY,TRANDESC,IY,org_brnid,dest_brnid)values('" + acno + "',"
//                            + "date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d')," + interest + "," + vchNo + ",'I',0,8,'Int on Vch: "
//                            + vchNo + "','Y','" + enterBy + "','System'," + recno + "," + trsno + ",3,4,1,'" + orgnBrCode + "','"
//                            + orgnBrCode + "')").executeUpdate();
//                    if (tdrecon <= 0) {
//                        return "Error in TD Credit Entry !!!";
//                    }
//                    if (tds != 0 && tds < interest) {
//                        recno = ftsMethods.getRecNo();
//                        int tdRecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,dramt,voucherno,intflag,ty,trantype,details,"
//                                + "auth,authby,enterby,recno,trsno,payby,trandesc,org_brnid,dest_brnid)values ('" + acno + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
//                                + tds + "," + vchNo + ",'T',1,2,'Tds Decucted for " + fromDt + "To " + toDt + "','Y','System','"
//                                + enterBy + "'," + recno + "," + trsno + ",3,33,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
//                        if (tdRecon <= 0) {
//                            return "Error in tdRecon Insertion for TDS";
//                        }
//
//                        Query updateQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', trsno = " + trsno + ", recno = " + recno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
//                                + " where acno = '" + acno + "' and VoucherNo = '" + vchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
//                        int result = updateQuery.executeUpdate();
//                        if (result < 0) {
//                            return "Error in updating tdshistory for tds ";
//                        }
//
//                        Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
//                                + "VALUES('" + acno + "'," + vchNo + "," + tds + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "')").executeUpdate();
//                        if (TDSHistory <= 0) {
//                            throw new ApplicationException("Data Not Saved For " + acno);
//                        }
//
//                    }
//
//                    // ftsMethods.updateBalance(acNat, acno, interest, 0, "", "");
//                    if (intopt.equalsIgnoreCase("M") || intopt.equalsIgnoreCase("Q")) {
//                        if (!intToAcno.equalsIgnoreCase(acno)) {
//                            String crDate = ymd.format(new Date());
//                            double crAmount = 0;
//
//                            if (tds < interest) {
//                                crAmount = interest - tds;
//                            } else {
//                                crAmount = interest;
//                                totalTds = totalTds - tds;
//                            }
//
//                            String crDetails = "Int.on Vch : " + String.valueOf(vchNo) + " A/c No. ";
//
//                            if (!ftsMethods.getCurrentBrnCode(intToAcno).equalsIgnoreCase(orgnBrCode)) {
//                                /**
//                                 * Interbranch Transaction
//                                 */
//                                recno = ftsMethods.getRecNo();
//
//                                resultMessage = ftsRemoteMethods.cbsPostingSx(intToAcno, 0, crDate, crAmount, 0, 2, crDetails + acno, 0.0f, "", "", "", 3, 0.0f, recno, 4, ftsMethods.getCurrentBrnCode(intToAcno), orgnBrCode, enterBy, "System", trsno2, "", "");
//                                if (resultMessage.substring(0, 4).equalsIgnoreCase("true")) {
//                                    recno = ftsMethods.getRecNo();
//
//                                    resultMessage = ftsRemoteMethods.cbsPostingCx(acno, 1, crDate, crAmount, 0, 2, crDetails + intToAcno, 0.0f, "", "", "", 3, 0.0f, recno, 4, orgnBrCode, orgnBrCode, enterBy, "System", trsno2, "", "");
//                                    if (!resultMessage.substring(0, 4).equalsIgnoreCase("true")) {
//                                        // ftsMethods.updateBalance(acNat, acno, 0, crAmount, "", "");                                        
//                                        return resultMessage;
//                                    }
//                                } else {
//                                    return resultMessage;
//                                }
//                            } else if (ftsMethods.getCurrentBrnCode(intToAcno).equalsIgnoreCase(orgnBrCode)) {
//                                /**
//                                 * Local Transaction
//                                 */
//                                recno = ftsMethods.getRecNo();
//
//                                Integer recon = em.createNativeQuery("insert into td_recon(acno,dt,ValueDt,Dramt,voucherno,ty,trantype,details,"
//                                        + "auth,enterBy,authby,recno,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) values ('" + acno + "',date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d'),"
//                                        + "" + crAmount + "," + vchNo + ",1,2,'Int.on Vch : " + vchNo + " A/c No. " + intToAcno + "',"
//                                        + "'Y','" + enterBy + "','System'," + recno + "," + trsno2 + ",3,4,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
//                                if (recon <= 0) {
//                                    return "Error in TD Debit Entry !!!";
//                                }
//                                //   ftsMethods.updateBalance(acNat, acno, 0, interest-tds, "Y", "Y");
//
//                                String acctNature = ftsMethods.getAccountNature(intToAcno);
//                                recno = ftsMethods.getRecNo();
//
//                                resultMessage = ftsMethods.insertRecons(acctNature, intToAcno, 0, crAmount, crDate, crDate, 2, crDetails + acno, enterBy, trsno2, crDate, recno, "Y", "System", 4, 3, "", "", 0.0f, "", "", 1, "", 0.0f, "", null, orgnBrCode, orgnBrCode, 0, "", "", "");
//                                if (resultMessage.equalsIgnoreCase("true")) {
//                                    resultMessage = ftsMethods.updateBalance(acctNature, intToAcno, crAmount, 0, "Y", "Y");
//                                    if (!resultMessage.equalsIgnoreCase("True")) {
//                                        return resultMessage;
//                                    }
//                                } else {
//                                    return resultMessage;
//                                }
//                            }
//                        } /* end of inttoacno acno */ else {
//                            double crAmount = 0;
//                            if (tds < interest) {
//                                crAmount = interest - tds;
//                            } else {
//                                crAmount = interest;
//                            }
//                            List accounttypemasterList = em.createNativeQuery("select acno from td_reconbalan where acno='" + acno + "'").getResultList();
//                            if (!accounttypemasterList.isEmpty()) {
//                                Integer reconMQ = em.createNativeQuery(" update td_reconbalan set balance=balance+"
//                                        + crAmount + ",dt=date_format(now(),'%Y%m%d') where acno='" + acno + "'").executeUpdate();
//                                if (reconMQ <= 0) {
//                                    return "Error in Balance Updation !!!";
//                                }
//
//                            } else {
//                                Integer reconMQ = em.createNativeQuery("insert into td_reconbalan(acno,balance,dt)values('" + acno + "',"
//                                        + crAmount + ",date_format(now(),'%Y%m%d'))").executeUpdate();
//                                if (reconMQ <= 0) {
//                                    return "Error in Balance Updation !!!";
//                                }
//                            }
////                            List accounttypemasterList = em.createNativeQuery("select acno from td_reconbalan where acno='" + acno + "'").getResultList();
////                            if (!accounttypemasterList.isEmpty()) {
////                                Integer reconMQ = em.createNativeQuery(" update td_reconbalan set balance=balance-"
////                                        + tds + ",dt=date_format(now(),'%Y%m%d') where acno='" + acno + "'").executeUpdate();
////                                if (reconMQ <= 0) {
////                                    return "Error in Balance Updation !!!";
////                                }
////
////                            } else {
////                                Integer reconMQ = em.createNativeQuery("insert into td_reconbalan(acno,balance,dt)values('" + acno + "',"
////                                        + interest + "-" + tds + ",date_format(now(),'%Y%m%d'))").executeUpdate();
////                                if (reconMQ <= 0) {
////                                    return "Error in Balance Updation !!!";
////                                }
////                            }
//                        }
//                    }
//                    /**
//                     * *** End of intOpt If M,Q ****
//                     */
//                }
//
//                if (intopt.equalsIgnoreCase("S")) {
//                    if (simplePostFlag == 2) {
//                        recno = ftsMethods.getRecNo();
//
//                        Integer reconMQ = em.createNativeQuery("insert into td_recon(acno,dt,ValueDt,Dramt,voucherno,intflag,ty,"
//                                + "trantype,details,auth,enterBy,authby,recno,trsno,TRANDESC,IY,org_brnid,dest_brnid)values ('" + acno + "',"
//                                + "date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d')," + interest + "," + vchNo + ",'I',1,2,"
//                                + "'Int. on VCH :" + vchNo + "','Y','" + enterBy + "','System'," + recno + "," + trsno + ",4,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
//                        if (reconMQ <= 0) {
//                            return "Error in TD Interest Entry !!!";
//                        }
//                        // ftsMethods.updateBalance(acNat, acno, 0, interest, "", "");
//                    }
//                }
//
//                Integer interesthistory = em.createNativeQuery("insert into td_interesthistory(acno,voucherno,interest,fromdt,todt,intopt,"
//                        + "dt)values ('" + acno + "'," + vchNo + "," + interest + ",'" + fromDt + "','" + toDt + "',"
//                        + "'" + intopt + "', date_format(now(),'%Y%m%d'))").executeUpdate();
//                if (interesthistory <= 0) {
//                    return "Error in TD Interest History Entry !!!";
//                }
//                String tmpDt = CbsUtil.dateAdd(todate, 1);
//                if (tds >= interest) {
//                    tds = 0;
//                }
//                Integer vouchmst = em.createNativeQuery("update td_vouchmst set nextintpaydt='" + tmpDt + "',"
//                        + "TDSDEDUCTED=ifnull(TDSDEDUCTED,0) + ifnull(" + tds + ",0) where acno='" + acno + "' and intopt='" + intopt + "' and status='A' "
//                        + "AND VOUCHERNO = " + vchNo).executeUpdate();
//                if (vouchmst <= 0) {
//                    return "Error in TD Voucher Updation !!!";
//                }
//
//            }
//            /**
//             * ** End of For **
//             */
//            /**
//             * ****************insertion for
//             * GLHEADPROV**************************
//             */
//            if (intopt.equalsIgnoreCase("S") && (simplePostFlag == 0 || simplePostFlag == 2)) {
//                String glHeadProv = "";
//                List glheadprovList = em.createNativeQuery("SELECT glheadprov FROM  accounttypemaster where acctcode ='" + acctype + "'").getResultList();
//                if (!glheadprovList.isEmpty()) {
//                    Vector obcGlagVect = (Vector) glheadprovList.get(0);
//                    glHeadProv = obcGlagVect.get(0).toString();
//                }
//                if (glHeadProv == null || glHeadProv.equalsIgnoreCase("") || glHeadProv.length() == 0) {
//                    return "GL Head Prov. does not exist in account type master";
//                }
//                glHeadProv = orgnBrCode + glHeadProv + "01";
//
//                recno = ftsMethods.getRecNo();
//                Integer vouchmst = em.createNativeQuery("insert into gl_recon(acno,dt,ValueDt,cramt,ty,trantype,details,auth,enterBy,"
//                        + "authby,trsno,payby,TRANDESC,IY,RECNO,org_brnid,dest_brnid) values ('" + glHeadProv + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d')," + totalInterest + "-" + totalTds + ","
//                        + "0,2, 'Int. on :" + intopt + " OPTION' ,'Y','" + enterBy + "','System'," + trsno + ",3,4,1," + recno + ",'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
//                if (vouchmst <= 0) {
//                    return "Error in GL Credit Entry !!!";
//                }
//                List reconBalanList = em.createNativeQuery("select acno from reconbalan where acno='" + glHeadProv + "'").getResultList();
//                if (!reconBalanList.isEmpty()) {
//                    Integer reconBalanL = em.createNativeQuery("update reconbalan set balance=balance+'" + totalInterest + "',"
//                            + "dt=date_format(now(),'%Y%m%d') where acno='" + glHeadProv + "'").executeUpdate();
//                    if (reconBalanL <= 0) {
//                        return "Error in GL Updation !!!";
//                    }
//                } else {
//                    Integer reconBalan = em.createNativeQuery("insert into reconbalan(acno,balance,dt)values('" + glHeadProv + "','" + totalInterest + "-" + totalTds + "',date_format(now(),'%Y%m%d'))").executeUpdate();
//                    if (reconBalan <= 0) {
//                        return "Error in GL Updation !!!";
//                    }
//                }
//            }
//
//            /**
//             * ****************FOR INSERTION OF TDS GL HEAD
//             * ********************
//             */
//            if ((intopt.equalsIgnoreCase("M") || intopt.equalsIgnoreCase("Q")) || (intopt.equalsIgnoreCase("S") && simplePostFlag == 1)) {
//                Integer reconBalan = em.createNativeQuery("UPDATE reconbalan SET BALANCE=BALANCE+" + totalTds + ",DT=date_format(now(),'%Y%m%d')  WHERE ACNO='" + glHead + "'").executeUpdate();
//                if (reconBalan <= 0) {
//                    return "Error in GL Balance Updation !!!";
//                }
//                recno = ftsMethods.getRecNo();
//                if (totalTds > 0) {
//                    Integer glrecon = em.createNativeQuery("INSERT INTO gl_recon (ACNO,Dt,ValueDt,CrAmt,Ty,TranType,Details,EnterBy,"
//                            + "Auth,AuthBy,TranTime,RecNo,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) VALUES('" + glHead + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
//                            + "" + totalTds + ",0,2,'TDS POSTING TILL DT " + todate + "','" + enterBy + "','Y','System',now(),'" + recno + "',"
//                            + "'" + trsno + "',3,33,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
//                    if (glrecon <= 0) {
//                        return "Error in TDS Posting in GL !!!";
//                    }
//                }
//            }
//            Integer tdIntFlag = em.createNativeQuery("insert into td_intflag(td_tilldate,td_sysdate,intopt,acType,brncode)values('" + todate + "',"
//                    + "date_format(now(),'%Y%m%d'),'" + intopt + "','" + acctype + "','" + orgnBrCode + "')").executeUpdate();
//            if (tdIntFlag <= 0) {
//                return "Error in TD Interest Flag Updation !!!";
//            }
//            return "Yes";
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//    }
    public Map<String, Object> tdMQIntPost(List<TdIntDetail> tdIntDetailsList, String acctype, String intopt, String enterBy, String td_inthead,
            String todate, String glHead, String orgnBrCode) throws ApplicationException {
        String resultMessage = "", tdsPostEnable = "N";
        Float recno, trsno, trsno2;
        Map<String, Object> map = new HashMap<String, Object>();
        List<TdInterestSmsTo> tdIntList = new ArrayList<TdInterestSmsTo>();
        List<TdInterestSmsTo> tdIntSmslist = new ArrayList<TdInterestSmsTo>();
        try {
            double totalInterest = 0d;
            double totalTds = 0d;
            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
                totalInterest = totalInterest + tdIntDetail.getInterest();
                //  totalTds = totalTds + tdIntDetail.getTds();
            }
            int simplePostFlag = 0;
            List simplePostFlagList = em.createNativeQuery("SELECT ifnull(SIMPLEPOSTFLAG,0) FROM td_parameterinfo").getResultList();
            if (!simplePostFlagList.isEmpty()) {
                Vector simplePostFlagVect = (Vector) simplePostFlagList.get(0);
                simplePostFlag = Integer.parseInt(simplePostFlagVect.get(0).toString());
            }

            recno = ftsMethods.getRecNo();

            trsno = ftsMethods.getTrsNo();
            trsno2 = ftsMethods.getTrsNo();

            Integer rdrecon = em.createNativeQuery("insert into gl_recon (acno,dt,valueDt,dramt,ty,trantype,details,auth,enterBy,"
                    + "authby,trsno,payby,recno,TRANDESC,IY,org_brnid,dest_brnid) values('" + td_inthead + "',date_format(now(),'%Y%m%d')" + ",date_format(now(),'%Y%m%d'),"
                    + "" + totalInterest + ",1,8, 'Interest Amount','Y','" + enterBy + "','System'," + trsno + ",3,"
                    + "" + recno + ",4,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
            if (rdrecon <= 0) {
                map.put("msg", "Error in GL_Entry !!!");
                map.put("list", tdIntList);
                return map;
            }

            List reconbalanList = em.createNativeQuery("select acno from reconbalan where acno='" + td_inthead + "'").getResultList();
            if (!reconbalanList.isEmpty()) {
                Integer reconbalan = em.createNativeQuery("update reconbalan set balance=ifnull(balance,0)- ifnull('" + totalInterest + "',0),"
                        + "dt=date_format(now(),'%Y%m%d') where acno='" + td_inthead + "'").executeUpdate();
                if (reconbalan <= 0) {
                    map.put("msg", "Error in Updating GL Balance !!!");
                    map.put("list", tdIntList);
                    return map;
                }
            } else {
                Integer reconbalan = em.createNativeQuery("insert into reconbalan(acno,balance,dt)values('" + td_inthead + "',"
                        + "-" + totalInterest + ",date_format(now(),'%Y%m%d'))").executeUpdate();
                if (reconbalan <= 0) {
                    map.put("msg", "Error in Updating GL Balance !!!");
                    map.put("list", tdIntList);
                    return map;
                }
            }

            List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
            if (!tdsPostEnableList.isEmpty()) {
                Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
                tdsPostEnable = tdsPostEnableVect.get(0).toString();
            }

            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
                double interest = tdIntDetail.getInterest();
                String acno = tdIntDetail.getAcno();
                float vchNo = tdIntDetail.getVoucherNo();

                String intToAcno = tdIntDetail.getIntToAcno();
                String fromDt = tdIntDetail.getFromDt();
                String toDt = tdIntDetail.getToDt();
                double tds = 0d;
                /**
                 * ***Getting TDS Deduction from account **************
                 */
                if (tdsPostEnable.equalsIgnoreCase("Y")) {
                    List acWiseTdsList = em.createNativeQuery("select ifnull(sum(ifnull(tds,0)),0) from tds_reserve_history where acno = '" + acno + "' and VoucherNo = '" + vchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'").getResultList();
                    if (acWiseTdsList.size() > 0) {
                        Vector acWiseTdsVect = (Vector) acWiseTdsList.get(0);
                        tds = Double.parseDouble(acWiseTdsVect.get(0).toString());
                        totalTds = totalTds + Double.parseDouble(acWiseTdsVect.get(0).toString());
                    }

                    /**
                     * Code Add On 20150624 *
                     */
                    List tdsCloseList = em.createNativeQuery("select tds,a.voucherno from tds_reserve_history a, td_vouchmst b where a.acno = b.acno "
                            + " and a.recovered ='NR' and b.status ='C' "
                            + " and a.acno = '" + acno + "' and a.voucherno = b.voucherno order by txnid").getResultList();
                    for (int cnt = 0; cnt < tdsCloseList.size(); cnt++) {
                        Vector tdsCloseVect = (Vector) tdsCloseList.get(cnt);
                        double cltds = Double.parseDouble(tdsCloseVect.get(0).toString());
                        tds = tds + cltds;
                        if (tds < interest) {
                            totalTds = totalTds + cltds;
                            String vchCl = tdsCloseVect.get(1).toString();
                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', trsno = " + trsno + ", recno = " + recno + ", recoveredVch = " + vchNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                    + " where acno = '" + acno + "' and VoucherNo = '" + vchCl + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                            int result = updateTdsQuery.executeUpdate();
                            if (result < 0) {
                                totalTds = totalTds - cltds;

                                map.put("msg", "Error in updating tdshistory for tds ");
                                map.put("list", tdIntList);
                                return map;
                            }
                        } else {
                            tds = tds - cltds;
                            break;
                        }
                    }
                }
                /**
                 * End Of Code Add On 20150624 *
                 */
                /**
                 * ***END of Getting TDS Deduction from account **************
                 */
                if ((intopt.equalsIgnoreCase("S") && simplePostFlag > 0) || intopt.equalsIgnoreCase("M") || intopt.equalsIgnoreCase("Q")) {
                    recno = ftsMethods.getRecNo();

                    Integer tdrecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,cramt,voucherno,intflag,ty,trantype,"
                            + "details,auth,enterby,authby,recno,trsno,PAYBY,TRANDESC,IY,org_brnid,dest_brnid)values('" + acno + "',"
                            + "date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d')," + interest + "," + vchNo + ",'I',0,8,'Int on Vch: "
                            + vchNo + "','Y','" + enterBy + "','System'," + recno + "," + trsno + ",3,4,1,'" + orgnBrCode + "','"
                            + orgnBrCode + "')").executeUpdate();
                    if (tdrecon <= 0) {
                        map.put("msg", "Error in TD Credit Entry !!!");
                        map.put("list", tdIntList);
                        return map;
                    }
                    if (tds != 0 && tds < interest) {
                        recno = ftsMethods.getRecNo();
                        int tdRecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,dramt,voucherno,intflag,ty,trantype,details,"
                                + "auth,authby,enterby,recno,trsno,payby,trandesc,org_brnid,dest_brnid)values ('" + acno + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                                + tds + "," + vchNo + ",'T',1,2,'Tds Decucted for " + fromDt + "To " + toDt + "','Y','System','"
                                + enterBy + "'," + recno + "," + trsno + ",3,3,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                        if (tdRecon <= 0) {
                            map.put("msg", "Error in tdRecon Insertion for TDS");
                            map.put("list", tdIntList);
                            return map;
                        }

                        Query updateQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', trsno = " + trsno + ", recno = " + recno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                + " where acno = '" + acno + "' and VoucherNo = '" + vchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                        int result = updateQuery.executeUpdate();
                        if (result < 0) {
                            map.put("msg", "Error in updating tdshistory for tds ");
                            map.put("list", tdIntList);
                            return map;
                        }

                        Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                + "VALUES('" + acno + "'," + vchNo + "," + tds + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "')").executeUpdate();
                        if (TDSHistory <= 0) {
                            map.put("msg", "Data Not Saved For " + acno);
                            map.put("list", tdIntList);
                            return map;
                        }

                    } else {
                        if ((intopt.equalsIgnoreCase("S") && simplePostFlag > 0)) {
                            totalTds = totalTds - tds;
                        }
                    }

                    if (intopt.equalsIgnoreCase("M") || intopt.equalsIgnoreCase("Q")) {
                        if (!intToAcno.equalsIgnoreCase(acno)) {
                            String crDate = ymd.format(new Date());
                            double crAmount = 0;
                            if (tds < interest) {
                                crAmount = interest - tds;
                            } else {
                                crAmount = interest;
                                totalTds = totalTds - tds;
                            }

                            String crDetails = "Int.on Vch : " + String.valueOf(vchNo) + " A/c No. ";

                            if (!ftsMethods.getCurrentBrnCode(intToAcno).equalsIgnoreCase(orgnBrCode)) {
                                //Interbranch Transaction
                                recno = ftsMethods.getRecNo();

                                resultMessage = ftsRemoteMethods.cbsPostingSx(intToAcno, 0, crDate, crAmount, 0, 2, crDetails + acno, 0.0f, "", "", "", 3, 0.0f, recno, 4, ftsMethods.getCurrentBrnCode(intToAcno), orgnBrCode, enterBy, "System", trsno2, "", "");
                                if (resultMessage.substring(0, 4).equalsIgnoreCase("true")) {
                                    recno = ftsMethods.getRecNo();

                                    resultMessage = ftsRemoteMethods.cbsPostingCx(acno, 1, crDate, crAmount, 0, 2, crDetails + intToAcno, 0.0f, "", "", "", 3, 0.0f, recno, 4, orgnBrCode, orgnBrCode, enterBy, "System", trsno2, "", "");
                                    if (!resultMessage.substring(0, 4).equalsIgnoreCase("true")) {
                                        map.put("msg", resultMessage);
                                        map.put("list", tdIntList);
                                        return map;
                                    }
                                    ftsMethods.lastTxnDateUpdation(intToAcno);
                                    //Sms List Creation
                                    if (!(intToAcno == null || intToAcno.equals("") || intToAcno.length() != 12
                                            || ftsMethods.getAccountNature(intToAcno).equalsIgnoreCase(CbsConstant.FIXED_AC)
                                            || ftsMethods.getAccountNature(intToAcno).equalsIgnoreCase(CbsConstant.MS_AC))) {
                                        TdInterestSmsTo to = new TdInterestSmsTo();
                                        to.setTdAcno(acno);
                                        to.setPrimaryAcno(intToAcno);
                                        to.setInterest(new BigDecimal(crAmount));

                                        tdIntSmslist.add(to);
                                    }
                                    //End Here
                                } else {
                                    map.put("msg", resultMessage);
                                    map.put("list", tdIntList);
                                    return map;
                                }
                            } else if (ftsMethods.getCurrentBrnCode(intToAcno).equalsIgnoreCase(orgnBrCode)) {
                                //Local Transaction
                                recno = ftsMethods.getRecNo();

                                Integer recon = em.createNativeQuery("insert into td_recon(acno,dt,ValueDt,Dramt,voucherno,ty,trantype,details,"
                                        + "auth,enterBy,authby,recno,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) values ('" + acno + "',date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d'),"
                                        + "" + crAmount + "," + vchNo + ",1,2,'Int.on Vch : " + vchNo + " A/c No. " + intToAcno + "',"
                                        + "'Y','" + enterBy + "','System'," + recno + "," + trsno2 + ",3,4,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                                if (recon <= 0) {
                                    map.put("msg", "Error in TD Debit Entry !!!");
                                    map.put("list", tdIntList);
                                    return map;
                                }

                                String acctNature = ftsMethods.getAccountNature(intToAcno);
                                recno = ftsMethods.getRecNo();

                                resultMessage = ftsMethods.insertRecons(acctNature, intToAcno, 0, crAmount, crDate, crDate, 2, crDetails + acno, enterBy, trsno2, crDate, recno, "Y", "System", 4, 3, "", "", 0.0f, "", "", 1, "", 0.0f, "", null, orgnBrCode, orgnBrCode, 0, "", "", "");
                                if (resultMessage.equalsIgnoreCase("true")) {
                                    resultMessage = ftsMethods.updateBalance(acctNature, intToAcno, crAmount, 0, "Y", "Y");
                                    if (!resultMessage.equalsIgnoreCase("True")) {
                                        map.put("msg", resultMessage);
                                        map.put("list", tdIntList);
                                        return map;
                                    }
                                } else {
                                    map.put("msg", resultMessage);
                                    map.put("list", tdIntList);
                                    return map;
                                }
                                ftsMethods.lastTxnDateUpdation(intToAcno);
                                //Sms List Creation
                                if (!(intToAcno == null || intToAcno.equals("") || intToAcno.length() != 12
                                        || ftsMethods.getAccountNature(intToAcno).equalsIgnoreCase(CbsConstant.FIXED_AC)
                                        || ftsMethods.getAccountNature(intToAcno).equalsIgnoreCase(CbsConstant.MS_AC))) {
                                    TdInterestSmsTo to = new TdInterestSmsTo();
                                    to.setTdAcno(acno);
                                    to.setPrimaryAcno(intToAcno);
                                    to.setInterest(new BigDecimal(crAmount));

                                    tdIntSmslist.add(to);
                                }
                                //End Here
                            }
                        } /* end of inttoacno acno */ else {
                            double crAmount = 0;
                            if (tds < interest) {
                                crAmount = interest - tds;
                            } else {
                                totalTds = totalTds - tds;
                                crAmount = interest;
                            }
                            List accounttypemasterList = em.createNativeQuery("select acno from td_reconbalan where acno='" + acno + "'").getResultList();
                            if (!accounttypemasterList.isEmpty()) {
                                Integer reconMQ = em.createNativeQuery(" update td_reconbalan set balance=balance+"
                                        + crAmount + ",dt=date_format(now(),'%Y%m%d') where acno='" + acno + "'").executeUpdate();
                                if (reconMQ <= 0) {
                                    map.put("msg", "Error in Balance Updation !!!");
                                    map.put("list", tdIntList);
                                    return map;
                                }

                            } else {
                                Integer reconMQ = em.createNativeQuery("insert into td_reconbalan(acno,balance,dt)values('" + acno + "',"
                                        + crAmount + ",date_format(now(),'%Y%m%d'))").executeUpdate();
                                if (reconMQ <= 0) {
                                    map.put("msg", "Error in Balance Updation !!!");
                                    map.put("list", tdIntList);
                                    return map;
                                }
                            }
                        }
                    }
                    /**
                     * *** End of intOpt If M,Q ****
                     */
                }

                if (intopt.equalsIgnoreCase("S")) {
                    if (simplePostFlag == 2) {
                        recno = ftsMethods.getRecNo();

                        Integer reconMQ = em.createNativeQuery("insert into td_recon(acno,dt,ValueDt,Dramt,voucherno,intflag,ty,"
                                + "trantype,details,auth,enterBy,authby,recno,trsno,TRANDESC,IY,org_brnid,dest_brnid)values ('" + acno + "',"
                                + "date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d')," + interest + "," + vchNo + ",'I',1,2,"
                                + "'Int. on VCH :" + vchNo + "','Y','" + enterBy + "','System'," + recno + "," + trsno + ",4,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                        if (reconMQ <= 0) {
                            map.put("msg", "Error in TD Interest Entry !!!");
                            map.put("list", tdIntList);
                            return map;
                        }
                    }
                }

                Integer interesthistory = em.createNativeQuery("insert into td_interesthistory(acno,voucherno,interest,fromdt,todt,intopt,"
                        + "dt)values ('" + acno + "'," + vchNo + "," + interest + ",'" + fromDt + "','" + toDt + "',"
                        + "'" + intopt + "', date_format(now(),'%Y%m%d'))").executeUpdate();
                if (interesthistory <= 0) {
                    map.put("msg", "Error in TD Interest History Entry !!!");
                    map.put("list", tdIntList);
                    return map;
                }
                String tmpDt = CbsUtil.dateAdd(todate, 1);
                if (tds >= interest) {
                    tds = 0;
                }
                Integer vouchmst = em.createNativeQuery("update td_vouchmst set nextintpaydt='" + tmpDt + "',"
                        + "TDSDEDUCTED=ifnull(TDSDEDUCTED,0) + ifnull(" + tds + ",0) where acno='" + acno + "' and intopt='" + intopt + "' and status='A' "
                        + "AND VOUCHERNO = " + vchNo).executeUpdate();
                if (vouchmst <= 0) {
                    map.put("msg", "Error in TD Voucher Updation !!!");
                    map.put("list", tdIntList);
                    return map;
                }

            }
            //End of For
            //insertion for GLHEADPROV*************************
            if (intopt.equalsIgnoreCase("S") && (simplePostFlag == 0 || simplePostFlag == 2)) {
                String glHeadProv = "";
                List glheadprovList = em.createNativeQuery("SELECT glheadprov FROM  accounttypemaster where acctcode ='" + acctype + "'").getResultList();
                if (!glheadprovList.isEmpty()) {
                    Vector obcGlagVect = (Vector) glheadprovList.get(0);
                    glHeadProv = obcGlagVect.get(0).toString();
                }
                if (glHeadProv == null || glHeadProv.equalsIgnoreCase("") || glHeadProv.length() == 0) {
                    map.put("msg", "GL Head Prov. does not exist in account type master");
                    map.put("list", tdIntList);
                    return map;
                }
                glHeadProv = orgnBrCode + glHeadProv + "01";

                recno = ftsMethods.getRecNo();
                Integer vouchmst = em.createNativeQuery("insert into gl_recon(acno,dt,ValueDt,cramt,ty,trantype,details,auth,enterBy,"
                        + "authby,trsno,payby,TRANDESC,IY,RECNO,org_brnid,dest_brnid) values ('" + glHeadProv + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d')," + totalInterest + "-" + totalTds + ","
                        + "0,2, 'Int. on :" + intopt + " OPTION' ,'Y','" + enterBy + "','System'," + trsno + ",3,4,1," + recno + ",'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                if (vouchmst <= 0) {
                    map.put("msg", "Error in GL Credit Entry !!!");
                    map.put("list", tdIntList);
                    return map;
                }
                List reconBalanList = em.createNativeQuery("select acno from reconbalan where acno='" + glHeadProv + "'").getResultList();
                if (!reconBalanList.isEmpty()) {
                    Integer reconBalanL = em.createNativeQuery("update reconbalan set balance=balance+'" + totalInterest + "',"
                            + "dt=date_format(now(),'%Y%m%d') where acno='" + glHeadProv + "'").executeUpdate();
                    if (reconBalanL <= 0) {
                        map.put("msg", "Error in GL Updation !!!");
                        map.put("list", tdIntList);
                        return map;
                    }
                } else {
                    Integer reconBalan = em.createNativeQuery("insert into reconbalan(acno,balance,dt)values('" + glHeadProv + "','" + totalInterest + "-" + totalTds + "',date_format(now(),'%Y%m%d'))").executeUpdate();
                    if (reconBalan <= 0) {
                        map.put("msg", "Error in GL Updation !!!");
                        map.put("list", tdIntList);
                        return map;
                    }
                }
            }

            //FOR INSERTION OF TDS GL HEAD
            if ((intopt.equalsIgnoreCase("M") || intopt.equalsIgnoreCase("Q")) || (intopt.equalsIgnoreCase("S") && simplePostFlag == 1)) {
                Integer reconBalan = em.createNativeQuery("UPDATE reconbalan SET BALANCE=BALANCE+" + totalTds + ",DT=date_format(now(),'%Y%m%d')  WHERE ACNO='" + glHead + "'").executeUpdate();
                if (reconBalan <= 0) {
                    map.put("msg", "Error in GL Balance Updation !!!");
                    map.put("list", tdIntList);
                    return map;
                }
                recno = ftsMethods.getRecNo();
                if (totalTds > 0) {
                    Integer glrecon = em.createNativeQuery("INSERT INTO gl_recon (ACNO,Dt,ValueDt,CrAmt,Ty,TranType,Details,EnterBy,"
                            + "Auth,AuthBy,TranTime,RecNo,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) VALUES('" + glHead + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                            + "" + totalTds + ",0,2,'TDS POSTING TILL DT " + todate + "','" + enterBy + "','Y','System',now(),'" + recno + "',"
                            + "'" + trsno + "',3,33,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                    if (glrecon <= 0) {
                        map.put("msg", "Error in TDS Posting in GL !!!");
                        map.put("list", tdIntList);
                        return map;
                    }
                }
            }
            Integer tdIntFlag = em.createNativeQuery("insert into td_intflag(td_tilldate,td_sysdate,intopt,acType,brncode)values('" + todate + "',"
                    + "date_format(now(),'%Y%m%d'),'" + intopt + "','" + acctype + "','" + orgnBrCode + "')").executeUpdate();
            if (tdIntFlag <= 0) {
                map.put("msg", "Error in TD Interest Flag Updation !!!");
                map.put("list", tdIntList);
                return map;
            }

            map.put("msg", "Yes");
            if (tdIntSmslist.isEmpty()) {
                map.put("list", tdIntList);
            } else {
                map.put("list", tdIntSmslist);
            }
            return map;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String tdIntPost(List<TdIntDetail> tdIntDetailsList, String acctype, String intopt, String enterBy, String tdIntHead,
            String todate, String orgnBrCode) throws ApplicationException {
        Float recno;
        Float trsno;
        double totalTds = 0d;
        String glAccNo = null, tdsPostEnable = "N";
        try {
            double totalInterest = 0d;
            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
                totalInterest = totalInterest + tdIntDetail.getInterest();
            }
            recno = ftsMethods.getRecNo();
            trsno = ftsMethods.getTrsNo();

            ftsMethods.updateBalance("PO", tdIntHead, 0, totalInterest, "", "");

//            List reconBalanList = em.createNativeQuery("select Acno From reconBalan Where acno='" + tdIntHead + "'").getResultList();
//
//            if (!reconBalanList.isEmpty()) {
//                List balanceList = em.createNativeQuery("Select isnull(Balance,0) - isnull('" + totalInterest + "',0) From reconbalan "
//                        + "where acno='" + tdIntHead + "'").getResultList();
//
//                if (!balanceList.isEmpty()) {
//                    Integer reconBalanL = em.createNativeQuery("Update reconbalan set balance=isnull(balance,0) -"
//                            + " isnull('" + totalInterest + "',0),dt=convert(varchar(8),getdate(),112) Where acno='" + tdIntHead + "'").executeUpdate();
//                    if (reconBalanL <= 0) {
//                        return "Error in reconbalan Updation !!!";
//                    }
//                }
//            } else {
//                Integer reconBalanL = em.createNativeQuery("Insert into reconBalan(acno,balance,dt)Values('" + tdIntHead + "',"
//                        + "- " + totalInterest + ",convert(varchar(8),getdate(),112))").executeUpdate();
//                if (reconBalanL <= 0) {
//                    return "Error in reconbalan Insertion !!!";
//                }
//            }
            Integer reconBalanL = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,dramt,ty,trantype,details,auth,authby,enterBy,"
                    + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + tdIntHead + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                    + totalInterest + ",1,8,'Interest Amount','Y','System','" + enterBy + "'," + recno + "," + trsno + ",3,3,'" + orgnBrCode
                    + "','" + orgnBrCode + "')").executeUpdate();
            if (reconBalanL <= 0) {
                return "Error in reconbalan Insertion !!!";
            }

            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
                double interest = tdIntDetail.getInterest();
                String acno = tdIntDetail.getAcno();
                float vchNo = tdIntDetail.getVoucherNo();

                String fromDt = tdIntDetail.getFromDt();
                String toDt = tdIntDetail.getToDt();
                // String acNat = ftsMethods.getAccountNature(acno);

                recno = ftsMethods.getRecNo();

                // ftsMethods.updateBalance(acNat, acno, interest,0, "", "");
                Integer tdRecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,cramt,voucherno,intflag,ty,trantype,details,"
                        + "auth,authby,enterby,recno,trsno,payby,trandesc,org_brnid,dest_brnid)values ('" + acno + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                        + interest + "," + vchNo + ",'I',0,8,'Int.on VCH: " + vchNo + "','Y','System','" + enterBy
                        + "'," + recno + "," + trsno + ",3,3,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                if (tdRecon <= 0) {
                    return "Error in tdRecon Insertion";
                }

                /**
                 * ***TDS Deduction from account **************
                 */
                List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
                if (!tdsPostEnableList.isEmpty()) {
                    Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
                    tdsPostEnable = tdsPostEnableVect.get(0).toString();
                }
                double tdsAmt = 0;
                if (tdsPostEnable.equalsIgnoreCase("Y")) {
                    List acWiseTdsList = em.createNativeQuery("select ifnull(sum(ifnull(tds,0)),0) from tds_reserve_history where acno = '" + acno + "' and VoucherNo = '" + vchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'").getResultList();
                    if (acWiseTdsList.size() > 0) {
                        Vector acWiseTdsVect = (Vector) acWiseTdsList.get(0);
                        tdsAmt = Double.parseDouble(acWiseTdsVect.get(0).toString());
                        totalTds = totalTds + Double.parseDouble(acWiseTdsVect.get(0).toString());
                        if (tdsAmt != 0) {
                            recno = ftsMethods.getRecNo();

                            /**
                             * Code Add On 20150624 *
                             */
                            List tdsCloseList = em.createNativeQuery("select tds,a.voucherno from tds_reserve_history a, td_vouchmst b where a.acno = b.acno "
                                    + " and a.recovered ='NR' and b.status ='C' "
                                    + " and a.acno = '" + acno + "' and a.voucherno = b.voucherno order by txnid").getResultList();
                            for (int cnt = 0; cnt < tdsCloseList.size(); cnt++) {
                                Vector tdsCloseVect = (Vector) tdsCloseList.get(cnt);
                                double cltds = Double.parseDouble(tdsCloseVect.get(0).toString());
                                tdsAmt = tdsAmt + cltds;
                                if (tdsAmt < interest) {
                                    totalTds = totalTds + cltds;
                                    String vchCl = tdsCloseVect.get(1).toString();
                                    Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', trsno = " + trsno + ", recno = " + recno + ", recoveredVch = " + vchNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                            + " where acno = '" + acno + "' and VoucherNo = '" + vchCl + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                    int result = updateTdsQuery.executeUpdate();
                                    if (result < 0) {
                                        totalTds = totalTds - tdsAmt;
                                        return "Error in updating tdshistory for tds ";
                                    }
                                } else {
                                    tdsAmt = tdsAmt - cltds;
                                    break;
                                }
                            }

                            /**
                             * End Of Code Add On 20150624 *
                             */
                            tdRecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,dramt,voucherno,intflag,ty,trantype,details,"
                                    + "auth,authby,enterby,recno,trsno,payby,trandesc,org_brnid,dest_brnid)values ('" + acno + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                                    + tdsAmt + "," + vchNo + ",'T',1,2,'Tds Decucted for " + fromDt + "To " + toDt + "','Y','System','" + enterBy
                                    + "'," + recno + "," + trsno + ",3,33,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                            if (tdRecon <= 0) {
                                return "Error in tdRecon Insertion for TDS";
                            }
                            Query updateQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', trsno = " + trsno + ", recno = " + recno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                    + " where acno = '" + acno + "' and VoucherNo = '" + vchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                            int result = updateQuery.executeUpdate();
                            if (result < 0) {
                                return "Error in updating tdshistory for tds ";
                            }
                            Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                    + "VALUES('" + acno + "'," + vchNo + "," + tdsAmt + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "')").executeUpdate();
                            if (TDSHistory <= 0) {
                                throw new ApplicationException("Data Not Saved For " + acno);
                            }
                        }
                    }
                }
                /**
                 * ***END of TDS Deduction from account **************
                 */
                Integer interestHistory = em.createNativeQuery("insert into td_interesthistory(acno,voucherno,interest,fromdt,"
                        + "todt,intopt,dt) values('" + acno + "'," + vchNo + "," + interest + ",'" + fromDt + "','" + toDt + "','"
                        + intopt + "'," + "date_format(now(),'%Y%m%d'))").executeUpdate();
                if (interestHistory <= 0) {
                    return "Error in TD_InterestHistory Insertion";
                }
                /* Updation For CumuPrinAmt For All Accounts */
                double tmpInt = interest - tdsAmt;
                Query updateQuery = em.createNativeQuery("Update td_vouchmst Set  CumuPrinAmt=ifnull(CumuPrinAmt,0)+ifnull(" + tmpInt + ",0)"
                        + " where acno = '" + acno + "' and voucherno = " + vchNo + " and IntOpt='" + intopt + "' and Status='A' and  CumuPrinAmt is not null");
                int result = updateQuery.executeUpdate();
                if (result < 0) {
                    return "Error in updating principal amount";
                }
            }

            /**
             * *************Total TDS posting in GL Recon**************
             */
            if (tdsPostEnable.equalsIgnoreCase("Y")) {
                List chk3 = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab "
                        + "where TDS_Applicabledate<='" + todate + "')").getResultList();
                if (!chk3.isEmpty()) {
                    Vector chk3V = (Vector) chk3.get(0);
                    glAccNo = orgnBrCode + chk3V.get(0).toString();
                }
                Integer reconBalan = em.createNativeQuery("UPDATE reconbalan SET BALANCE=BALANCE+" + totalTds + ",DT=date_format(now(),'%Y%m%d')  WHERE ACNO='" + glAccNo + "'").executeUpdate();
                if (reconBalan <= 0) {
                    return "Error in GL Balance Updation !!!";
                }
                if (totalTds > 0) {
                    recno = ftsMethods.getRecNo();
                    Integer glrecon = em.createNativeQuery("INSERT INTO gl_recon (ACNO,Dt,ValueDt,CrAmt,Ty,TranType,Details,EnterBy,"
                            + "Auth,AuthBy,TranTime,RecNo,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) VALUES('" + glAccNo + "',date_format(now(),'%Y%m%d'),"
                            + "date_format(now(),'%Y%m%d')," + totalTds + ",0,2, CONCAT('TDS POSTING TILL DT ' , date_format('" + todate + "','%Y%m%d')),'"
                            + enterBy + "','Y','System',now(),'" + recno + "','" + trsno + "',3,33,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                    if (glrecon <= 0) {
                        return "Error in TDS Posting in GL !!!";
                    }
                }
            }
            /**
             * *************END of Total TDS posting in GL Recon**************
             */
            /* Updation For NextIntPayDt For All Accounts */
            Query updateQuery1 = em.createNativeQuery("update td_vouchmst tv inner join td_accountmaster tm on tv.acno = tm.acno "
                    + "set nextIntPayDt='" + CbsUtil.dateAdd(todate, 1) + "' where tm.accttype = '" + acctype + "' and tm.curbrcode = '" + orgnBrCode
                    + "' and intopt='" + intopt + "' and Status='A'  and nextintpaydt<='" + todate + "' and  CumuPrinAmt is not null");
            int result = updateQuery1.executeUpdate();
            if (result < 0) {
                return "Error in updating Next Interest Payable Date";
            }

            /* Updation For IntFlag */
            Query insertQuery1 = em.createNativeQuery("Insert into td_intflag (TD_tilldate,TD_sysdate,intOpt,acType,brncode)"
                    + " values('" + todate + "',date_format(now(),'%Y%m%d'),'" + intopt + "','" + acctype + "','" + orgnBrCode + "')");
            result = insertQuery1.executeUpdate();
            if (result < 0) {
                return "Error in updating Interest flag";
            }
            return "Yes";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<TdIntDetail> interestCalculationWithTds(String fromdt, String todt, String intopt, String acctype, String brCode) throws ApplicationException {
        List<TdIntDetail> tdIntDetailList = new ArrayList<TdIntDetail>();
        try {
            String accno = "";
            float interest = 0.0f;
            float totIntTillToday = 0;
            float totmon = 0.0f;
            //int totquarter = 0;
            String mtdt1 = "";

            Float totdays = 0.0f;
            Float roi = 0.0f;
            String nextintpaydt = "";
            String matdt = "";

            float pamt = 0.0f;
            Float voucherno = 0.0f;
            String nextintpaydt1;
            Float matDays = 0.0f;

            int lastQuarter = 0;
            Integer year = 0;
            String inttoacno = "";
            Integer months = 0;

            String totdays1;
            Float cumuprinamt = 0.0f;
            float intAmt = 0.0f;
            Float seqno = 0.0f;

            Long quarterDays = 0l;
            String custName = "";
            long mnthdays = 0l;
            String finStartDt = rbiReportFacade.getMinFinYear(todt);
//            if (intopt.equalsIgnoreCase("M")) {
//                mnthdays = CbsUtil.dayDiff(ymd.parse(fromdt), ymd.parse(todt)) + 1;
//            }
            int mm = 0;
            int tmp = CbsUtil.datePart("M", todt);
            if (tmp > 2) {
                mm = tmp - 2;
            } else {
                mm = tmp;
            }
            String ddfrom = "";
            if (mm < 10) {
                ddfrom = String.valueOf(CbsUtil.datePart("Y", todt)) + "0" + String.valueOf(mm) + "01";
            } else {
                ddfrom = String.valueOf(CbsUtil.datePart("Y", todt)) + String.valueOf(mm) + "01";
            }
            Long dd = CbsUtil.dayDiff(ymd.parse(ddfrom), ymd.parse(todt));
            dd = dd + 1;
            Object bnkNameObj = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where "
                    + "b.alphacode=br.alphacode and br.brncode=" + Integer.parseInt(brCode)).getSingleResult();

            String bnkName = ((Vector) bnkNameObj).elementAt(0).toString();
            String bnkAddress = ((Vector) bnkNameObj).elementAt(1).toString();

            List bnkList = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
            Vector eleBnkCd = (Vector) bnkList.get(0);
            String bnkCode = eleBnkCd.get(0).toString();
            String liveDt = "";
            if (bnkCode.equalsIgnoreCase("rcbl")) {
                List liveList = em.createNativeQuery("select ifnull(FinYearBegin,'') from parameterinfo where brncode = cast(substring('" + brCode + "',1,2) as unsigned) ").getResultList();
                Vector liveDtVet = (Vector) liveList.get(0);
                liveDt = liveDtVet.get(0).toString();
            }

//            List cursorLt = em.createNativeQuery("select tm.seqno,tm.acno, substring(am.custName,1,28), "
//                    + " tm.voucherno,tm.roi,date_format(tm.nextintpaydt,'%Y%m%d'),date_format(tm.matdt,'%Y%m%d'), "
//                    + " tm.prinamt,ifnull(tm.years,0), ifnull(tm.months,0),ifnull(tm.days,0),tm.cumuprinamt,tm.inttoAcno, "
//                    + " tm.TDSDeducted, date_format(tm.FDDT,'%Y%m%d'),tm.period, cm.CustId, ifnull(cd.minorflag,'N'), ifnull(am.TDSFLAG,'Y') "
//                    + " From td_vouchmst tm, td_accountmaster am, customerid cm, cbs_customer_master_detail cd  "
//                    + " Where am.acno = cm.acno and cm.CustId = cast(cd.customerid as unsigned) and "
//                    + " am.acno=tm.acno and tm.intopt='" + intopt + "' and tm.status<>'C'  and am.accttype='"
//                    + acctype + "' AND tm.nextIntPayDt< tm.matdt AND tm.nextintpaydt<='" + todt + "' and tm.roi<>0 and "
//                    + "am.CurBrCode='" + brCode + "' ").getResultList();
            // +"tm.acno in ('011800002401','011800002501')").getResultList();
            String acQuery = "select aa.seqno,aa.acno, aa.custName,  "
                    + " aa.voucherno, aa.roi, aa.nextintpaydt, aa.matdt,  "
                    + " aa.prinamt, aa.years, aa.months, aa.days, aa.cumuprinamt, aa.inttoAcno, aa.TDSDeducted,  "
                    + " aa.FDDT, aa.period,  aa.CustId, aa.minorflag, aa.TDSFLAG, ifnull(bb.totalInt,0), ifnull(tds.totalTds,0) "
                    + ", ifnull(gr.CustomerId,0)  as minorCustIdfrom, aa.pan, ifnull(vo.totalVouchInt,0), ifnull(tdsVouch.totalTdsVouchWise,0)"
                    + ", ifnull(pro.CustId,'') as propCustId, ifnull(nrTds.nrTds,0), ifnull(nrTdsVch.nrTdsVch,0),ifnull(tdsVouch.closeAcTds,0)   "
                    + ", ifnull(gur.majorCustId,'') as majorCustId, ifnull(gur.pan,'') as majPan, aa.custType, aa.DateOfBirth, aa.CustEntityType, aa.intopt, if(minorflag='N',aa.DateOfBirth,gur.majDob) as majDob "
                    + " from "
                    + " (select tm.seqno,tm.acno, substring(am.custName,1,28) as custName, "
                    + " tm.voucherno, tm.roi, date_format(tm.nextintpaydt,'%Y%m%d') as nextintpaydt, date_format(tm.matdt,'%Y%m%d') as matdt, "
                    + " tm.prinamt, ifnull(tm.years,0) as years, ifnull(tm.months,0) as months, ifnull(tm.days,0) as days, "
                    + " tm.cumuprinamt, tm.inttoAcno, tm.TDSDeducted, "
                    + " date_format(tm.FDDT,'%Y%m%d') as FDDT, tm.period, cm.CustId,  ifnull(cd.minorflag,'N') as minorflag, "
                    + " ifnull(am.TDSFLAG,'Y') as TDSFLAG, ifnull(cd.PAN_GIRNumber,'') as pan, ifnull(CUST_TYPE,'OT') as custType, "
                    + " date_format(cd.DateOfBirth,'%Y%m%d') as DateOfBirth, ifnull(cd.CustEntityType,'03') as CustEntityType, tm.intopt "
                    + " From td_vouchmst tm, td_accountmaster am, customerid cm, cbs_customer_master_detail cd  "
                    + " Where am.acno = cm.acno and cm.CustId = cast(cd.customerid as unsigned) and "
                    + " am.acno=tm.acno and tm.intopt in (" + intopt + ") /*and am.accttype='" + acctype + "'*/ and am.CurBrCode='" + brCode + "' "
                    + " and tm.status<>'C' AND tm.nextIntPayDt< tm.matdt AND tm.nextintpaydt<='" + todt + "' and tm.roi<>0  /* and cm.CustId in (39152, 11429, 48078)*/) aa "
                    + " left join "
                    + " (select a.CustId as custId,a.acno,a.VoucherNo, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as totalVouchInt from "
                    + " (select ci.CustId,tv.acno,tv.VoucherNo, ifnull(sum(interest),0) as interest from customerid ci,td_vouchmst tv,td_interesthistory ti where tv.acno=ci.acno "
                    + " and (tv.cldt >='" + finStartDt + "')"
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between '" + finStartDt + "' and '" + todt + "' group by ci.CustId, tv.acno, tv.VoucherNo "
                    + " union all "
                    + " select ci.CustId,tv.acno,tv.VoucherNo, ifnull(sum(interest),0) as interest from customerid ci,td_vouchmst tv,td_interesthistory ti where tv.acno=ci.acno "
                    + " and (tv.cldt is null or tv.cldt ='') "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between '" + finStartDt + "' and '" + todt + "' group by ci.CustId,tv.acno, tv.VoucherNo "
                    + " ) a group by a.CustId, a.acno, a.VoucherNo "
                    + " ) vo on aa.CustId = vo.CustId and aa.acno = vo.acno and aa.VoucherNo = vo.VoucherNo "
                    + " left join  "
                    + " (select a.CustId as custId, ifnull(cast(sum(a.interest) as decimal(25,2)),0) as totalInt from  "
                    + " (select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,td_vouchmst tv,td_interesthistory ti where tv.acno=ci.acno  "
                    + " and (tv.cldt >='" + finStartDt + "') "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between '" + finStartDt + "' and '" + todt + "' group by ci.CustId  "
                    + " union all "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,rd_interesthistory ri where ac.acno=ci.acno  "
                    + " and (ac.closingdate >='" + finStartDt + "') "
                    + " and ac.acno=ri.acno and ri.dt between '" + finStartDt + "' and '" + todt + "' group by ci.CustId  "
                    + " union all "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,dds_interesthistory ri where ac.acno=ci.acno  "
                    + " and (ac.closingdate >='" + finStartDt + "') "
                    + " and ac.acno=ri.acno and ri.dt between '" + finStartDt + "' and '" + todt + "'  group by ci.CustId  "
                    + " union all  "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,td_vouchmst tv,td_interesthistory ti where tv.acno=ci.acno  "
                    + " and (tv.cldt is null or tv.cldt ='') "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between '" + finStartDt + "' and '" + todt + "' group by ci.CustId  "
                    + " union all  "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,rd_interesthistory ri where ac.acno=ci.acno  "
                    + " and (ac.closingdate is null or ac.closingdate ='') "
                    + " and ac.acno=ri.acno and ri.dt between '" + finStartDt + "' and '" + todt + "' group by ci.CustId  "
                    + " union all  "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,dds_interesthistory ri where ac.acno=ci.acno  "
                    + " and (ac.closingdate is null or ac.closingdate ='') "
                    + " and ac.acno=ri.acno and ri.dt between '" + finStartDt + "' and '" + todt + "'  group by ci.CustId ) a group by a.CustId "
                    + " ) bb on aa.CustId = bb.CustId  "
                    + " left join  "
                    + " (select a.CustId, ifnull(cast(sum(a.tds) as decimal(25,2)),0) as totalTds from  "
                    + " (select ci.CustId, ifnull(sum(tds),0) as tds from customerid ci,td_vouchmst tv,tds_reserve_history ti where tv.acno=ci.acno  "
                    + "  and (tv.cldt >='" + finStartDt + "') "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between '" + finStartDt + "' and '" + todt + "'  "
                    + " group by  ci.CustId  "
                    + " union all   "
                    + " select ci.CustId, ifnull(sum(tds),0) as tds from customerid ci,accountmaster ac,tds_reserve_history ri where ac.acno=ci.acno  "
                    + " and (ac.closingdate >='" + finStartDt + "') "
                    + " and ac.acno=ri.acno and ri.dt between '" + finStartDt + "' and '" + todt + "'  "
                    + " group by  ci.CustId  "
                    + " union all  "
                    + " select ci.CustId, ifnull(sum(tds),0) as tds from customerid ci,td_vouchmst tv,tds_reserve_history ti where tv.acno=ci.acno  "
                    + " and (tv.cldt is null or tv.cldt ='') "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between '" + finStartDt + "' and '" + todt + "'  "
                    + " group by  ci.CustId  "
                    + " union all  "
                    + " select ci.CustId, ifnull(sum(tds),0) as tds from customerid ci,accountmaster ac,tds_reserve_history ri where ac.acno=ci.acno  "
                    + "  and (ac.closingdate is null or ac.closingdate ='') "
                    + " and ac.acno=ri.acno and ri.dt between '" + finStartDt + "' and '" + todt + "'  "
                    + " group by  ci.CustId  "
                    + " ) a group by  a.CustId ) tds on aa.CustId = tds.CustId "
                    + " left join "
                    + " (select a.CustId,a.acno, a.VoucherNo, sum(a.tds) as totalTdsVouchWise, sum(a.closeTds) as closeAcTds from "
                    + " (select ci.CustId,tv.acno, tv.VoucherNo, ifnull(sum(ti.tds),0) as tds, ifnull(sum(ti.closeAcTds),0) as closeTds from customerid ci,td_vouchmst tv,tds_reserve_history ti where tv.acno=ci.acno "
                    + "  and (tv.cldt >='" + finStartDt + "') "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between '" + finStartDt + "' and '" + todt + "' "
                    + " group by  ci.CustId, tv.acno, tv.VoucherNo "
                    + " union all  "
                    + " select ci.CustId,tv.acno, tv.VoucherNo, ifnull(sum(ti.tds),0) as tds, ifnull(sum(ti.closeAcTds),0) as closeTds from customerid ci,td_vouchmst tv,tds_reserve_history ti where tv.acno=ci.acno "
                    + "  and (tv.cldt is null or tv.cldt ='') "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between '" + finStartDt + "' and '" + todt + "' "
                    + " group by  ci.CustId, tv.acno, tv.VoucherNo  "
                    + " ) a group by  a.CustId, a.acno, a.VoucherNo ) tdsVouch on aa.CustId = tdsVouch.CustId  and aa.acno = tdsVouch.acno "
                    + " and aa.VoucherNo = tdsVouch.VoucherNo "
                    + " left join "
                    + " (select count(gr.customerid) as customerid, gr.guardiancode from  cbs_cust_minorinfo gr "
                    + " where (gr.guardiancode is not null and gr.guardiancode<> '')  group by gr.guardiancode) gr "
                    + " on gr.guardiancode = aa.CustId  "
                    + " left join "
                    + " (select a.CustId, ifnull(sum(a.tds),0) as nrTds from  "
                    + " (select ci.CustId,tv.acno, tv.VoucherNo, ifnull(sum(ti.tds),0) as tds from customerid ci,td_vouchmst tv, td_accountmaster am, tds_reserve_history ti "
                    + " where tv.acno=ci.acno and am.acno = tv.ACNO "
                    + " and ti.recovered ='NR' and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt <='" + todt + "'  "
                    + " group by  ci.CustId, tv.acno, tv.VoucherNo   "
                    + " union all "
                    + " select ci.CustId,am.acno, 0 as VoucherNo, ifnull(sum(ti.tds),0) as tds from customerid ci,accountmaster am, tds_reserve_history ti where am.acno=ci.acno   "
                    + " and ti.recovered ='NR' and am.acno=ti.acno and ti.dt <='" + todt + "'  "
                    + " group by  ci.CustId, am.acno) a group by  a.CustId) nrTds on aa.CustId = nrTds.CustId  "
                    + " left join "
                    + " (select a.CustId,a.acno, a.VoucherNo, ifnull(sum(a.tds),0) as nrTdsVch from  "
                    + " (select ci.CustId,tv.acno, tv.VoucherNo, ifnull(sum(ti.tds),0) as tds from customerid ci,td_vouchmst tv, td_accountmaster am, tds_reserve_history ti "
                    + " where tv.acno=ci.acno and am.acno = tv.ACNO "
                    + " and ti.recovered ='NR' and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt <='" + todt + "'  "
                    + " group by  ci.CustId, tv.acno, tv.VoucherNo   "
                    + " union all "
                    + " select ci.CustId,am.acno, 0 as VoucherNo, ifnull(sum(ti.tds),0) as tds from customerid ci,accountmaster am, tds_reserve_history ti where am.acno=ci.acno   "
                    + " and ti.recovered ='NR' and am.acno=ti.acno and ti.dt <='" + todt + "'  "
                    + " group by  ci.CustId, am.acno) a group by  a.CustId, a.acno, a.VoucherNo) nrTdsVch on aa.CustId = nrTdsVch.CustId and aa.acno = nrTdsVch.acno "
                    + " and aa.VoucherNo = nrTdsVch.VoucherNo "
                    + " left join "
                    + " (select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,td_accountmaster tm where opermode = 8 and "
                    + " ci.custid = tm.custid1 "
                    + " union all "
                    + " select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,accountmaster tm where tm.opermode = 8 and "
                    + " ci.custid = tm.custid1 and substring(tm.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('DS','RD')) "
                    + " ) pro on  pro.acno = aa.acno "
                    + " left join "
                    + " (select  mn.CustomerId, ifnull(mn.guardiancode,'0')  as majorCustId, cm.PAN_GIRNumber as pan, date_format(cm.DateOfBirth,'%Y%m%d') as majDob from cbs_cust_minorinfo mn, cbs_customer_master_detail cm "
                    + " where mn.guardiancode = cm.customerid and (mn.guardiancode is not null and mn.guardiancode<> '')) gur on gur.customerid = aa.custId "
                    + " order by aa.CustId, aa.acno, aa.voucherno";
            System.out.println("acQuery>>>" + acQuery);
            List cursorLt = em.createNativeQuery(acQuery).getResultList();

            if (cursorLt.size() > 0) {
                for (int i = 0; i < cursorLt.size(); i++) {
                    Vector curV = (Vector) cursorLt.get(i);
                    String intopt1 = curV.get(34).toString();
//                    if (curV.get(35) == null) {                        
//                        System.out.println("Please check the DOB of customer " + curV.get(16).toString() + " Or Major DOB " + curV.get(29).toString());
////                        throw new ApplicationException("Please check the DOB of customer " + curV.get(16).toString() + " Or Major DOB " + curV.get(29).toString()+" Or Minor Condition");
//                    }
                    String majDob = (curV.get(35) == null || curV.get(35).toString().equalsIgnoreCase("19000101"))?"":curV.get(35).toString();
//                    System.out.println("CustID:"+curV.get(16).toString());
                    if (intopt1.equalsIgnoreCase("M")) {
                        String firstDt = CbsUtil.getFirstDateOfGivenDate(ymd.parse(todt));
                        mnthdays = CbsUtil.dayDiff(ymd.parse(firstDt), ymd.parse(todt)) + 1;
                    }
                    if (!intopt1.equalsIgnoreCase("C")) {
                        
                        seqno = Float.parseFloat(curV.get(0).toString());
                        accno = curV.get(1).toString();
                        custName = curV.get(2).toString();
                        voucherno = Float.parseFloat(curV.get(3).toString());
                        roi = Float.parseFloat(curV.get(4).toString());

                        nextintpaydt = curV.get(5).toString();
                        matdt = curV.get(6).toString();
                        pamt = Float.parseFloat(curV.get(7).toString());

                        year = Integer.parseInt(curV.get(8).toString());
                        months = Integer.parseInt(curV.get(9).toString());
                        //days = Integer.parseInt(curV.get(10).toString());

                        cumuprinamt = Float.parseFloat(curV.get(11).toString());
                        inttoacno = curV.get(12).toString();
                        //tdsdeduct = Float.parseFloat(curV.get(13).toString());
                        String fdDt = (curV.get(14).toString());
                        String period = (curV.get(15).toString());
                        String custId = (curV.get(16).toString());
                        String minorFlag = (curV.get(17).toString());
                        String tdsFlag = (curV.get(18).toString());
                        double totalIntPaid = Double.parseDouble(curV.get(19).toString());
                        double totalTds = Double.parseDouble(curV.get(20).toString());
                        int noOfMinorInMajor = Integer.parseInt(curV.get(21).toString());
                        String pan = curV.get(22).toString();
                        if (ParseFileUtil.isValidPAN(pan) == true) {
                            pan = "Y";
                        } else {
                            pan = "N";
                        }
                        double intVouchWise = Double.parseDouble(curV.get(23).toString());
                        double tdsVouchWise = Double.parseDouble(curV.get(24).toString());
                        String propCustId = curV.get(25).toString();
                        double unRecTds = Double.parseDouble(curV.get(26).toString());
                        double unRecTdsVchWise = Double.parseDouble(curV.get(27).toString());
                        double closeAcTds = Double.parseDouble(curV.get(28).toString());
                        String majorCustId = curV.get(29).toString();
                        String majorPan = curV.get(30).toString();
                        String custType = curV.get(31).toString();
                        String dob = curV.get(32).toString();
                        String custEntityType = curV.get(33).toString();
                        String minorCustId = "";

                        if (intopt1.equalsIgnoreCase("M")) {
                            long dateDiff = CbsUtil.dayDiff(ymd.parse(todt), ymd.parse(matdt));
                            int dtPart = CbsUtil.datePart("D", nextintpaydt);

                            if (dtPart != 1 && dateDiff > 0) {
                                String tempDt = CbsUtil.monthAdd(nextintpaydt, 1);
                                nextintpaydt1 = CbsUtil.dateAdd(tempDt.substring(0, 4) + tempDt.substring(4, 6) + "01", -1);

                                /* calculate the difference between nextintpaydt and lastday of the month  */
                                Long totaldays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1)) + 1;
                                totmon = (totaldays.floatValue() / mnthdays) + CbsUtil.monthDiff(ymd.parse(nextintpaydt1), ymd.parse(todt));
                                totdays = totaldays.floatValue() + CbsUtil.dayDiff(ymd.parse(nextintpaydt1), ymd.parse(todt));
                            }

                            /*B-- IF FROM DT IS FIRST DATE OF THE MONTH  AND MATURITY PERIOD LIES AFTER TODATE  */
                            if (dtPart == 1 && dateDiff > 0) {
                                totmon = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                                Long tmpTotDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                                totdays = tmpTotDay.floatValue();
                                if (bnkCode.equalsIgnoreCase("rcbl") && !liveDt.equals("")) {
                                    long liveDiff = CbsUtil.dayDiff(ymd.parse(fdDt), ymd.parse(liveDt));
                                    if (liveDiff > 0) {
                                        totdays = 30f;
                                    }
                                }
                            }
                            /*C--- IF FROM DT IS NOT THE FIRST DT OF THE MONTH AND MATURITY DATE LIES BEFoRE TO DATE */
                            if (dtPart != 1 && dateDiff < 0) {
                                String tempDt = CbsUtil.monthAdd(nextintpaydt, 1);
                                nextintpaydt1 = CbsUtil.dateAdd(tempDt.substring(0, 4) + tempDt.substring(4, 6) + "01", -1);
                                mtdt1 = matdt.substring(0, 4) + matdt.substring(4, 6) + "01";

                                /*calculate  the no of days between first of maturity month and maturity date */
                                Long tempMatDays = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                                matDays = tempMatDays.floatValue();

                                totdays = matDays + CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1)) + 1;
                                totmon = totdays / mnthdays + CbsUtil.monthDiff(ymd.parse(CbsUtil.monthAdd(nextintpaydt1, 1)), ymd.parse(matdt));

                                Long tmpTotDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                                totdays = tmpTotDay.floatValue();
                            }
                            if (dtPart == 1 && dateDiff < 0) {
                                mtdt1 = matdt.substring(0, 4) + matdt.substring(4, 6) + "01";
                                totmon = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(mtdt1));

                                Long tmpTotDay = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                                matDays = tmpTotDay.floatValue();
                                totdays = totdays + matDays;
                                totmon = matDays / mnthdays + totmon;

                                tmpTotDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                                totdays = tmpTotDay.floatValue();
                            }
                            interest = 0.0f;
                            //Code for Ramgarhia
                            if (bnkCode.equalsIgnoreCase("rcbl") && !liveDt.equals("")) {
                                long liveDiff = CbsUtil.dayDiff(ymd.parse(fdDt), ymd.parse(liveDt));
                                if (liveDiff <= 0) {
                                    interest = (pamt - (pamt * (1 / (1 + roi / 1200)))) * totmon;
                                } else {
                                    interest = pamt * roi * totdays / 36000;
                                }
                            } else {
                                interest = (pamt - (pamt * (1 / (1 + roi / 1200)))) * totmon;
                            }
                            //Code for checking round off issue.
                            if (dateDiff < 4 && dateDiff > 0) {
                                totIntTillToday = Float.parseFloat(orgFdInt.orgFDInterest(intopt1, roi, fdDt, todt, pamt, period, brCode));
                                List paidIntList = em.createNativeQuery("select ifnull(sum(ifnull(interest,0)),0) from td_interesthistory where "
                                        + "acno='" + accno + "' and voucherno=" + voucherno).getResultList();
                                Vector vect = (Vector) paidIntList.get(0);
                                float intPaid = Float.parseFloat(vect.get(0).toString());
                                float balInt = totIntTillToday - intPaid;
                                if (interest > balInt) {
                                    interest = balInt;
                                }
                            }
                        }
                        if (intopt1.equalsIgnoreCase("Q")) {
                            long dtDiff = CbsUtil.dayDiff(ymd.parse(ddfrom), ymd.parse(fdDt));
                            long dateDiff = CbsUtil.dayDiff(ymd.parse(todt), ymd.parse(matdt));
                            if (dateDiff > 0) {
                                Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                                totdays = tmpDays.floatValue();
                            } else if (dateDiff <= 0) {
                                Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                                totdays = tmpDays.floatValue();
                            }
                            if (dtDiff > 0) {
                                interest = 0.0f;
                                interest = (pamt * (roi / 36500)) * totdays;
                            } else {
                                interest = 0.0f;
                                Float totquarter = totdays / dd;
                                interest = (pamt * (roi / 400)) * totquarter;
                            }
                            //Code for checking round off issue.
                            if (dateDiff < 4 && dateDiff > 0) {
                                totIntTillToday = Float.parseFloat(orgFdInt.orgFDInterest(intopt1, roi, fdDt, todt, pamt, period, brCode));
                                List paidIntList = em.createNativeQuery("select ifnull(sum(ifnull(interest,0)),0) from td_interesthistory where "
                                        + "acno='" + accno + "' and voucherno=" + voucherno).getResultList();
                                Vector vect = (Vector) paidIntList.get(0);
                                float intPaid = Float.parseFloat(vect.get(0).toString());
                                float balInt = totIntTillToday - intPaid;
                                if (interest > balInt) {
                                    interest = balInt;
                                }
                            }
                        }
                        if (intopt1.equalsIgnoreCase("S") || intopt1.equalsIgnoreCase("Y")) {
                            long dateDiff = CbsUtil.dayDiff(ymd.parse(todt), ymd.parse(matdt));
                            int dtPart = CbsUtil.datePart("D", nextintpaydt);
                            if (dtPart != 1 && dateDiff >= 0) {
                                String tempDt = CbsUtil.monthAdd(nextintpaydt, 1);
                                nextintpaydt1 = CbsUtil.dateAdd(tempDt.substring(0, 4) + tempDt.substring(4, 6) + "01", -1);

                                Long totaldays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1)) + 1;
                                totdays = totaldays.floatValue();
                                totmon = (totdays / 30) + CbsUtil.monthDiff(ymd.parse(nextintpaydt1), ymd.parse(todt));
                                totdays = totdays + CbsUtil.dayDiff(ymd.parse(nextintpaydt1), ymd.parse(todt));

                                if (dateDiff == 0) {
                                    totdays = totdays - 1;
                                }
                            }
                            if (dtPart == 1 && dateDiff >= 0) {
                                totmon = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                                Long tmpDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                                totdays = tmpDay.floatValue();
                                if (dateDiff == 0) {
                                    totdays = totdays - 1;
                                }
                            }
                            if (dtPart != 1 && dateDiff < 0) {
                                String tempDt = CbsUtil.monthAdd(nextintpaydt, 1);
                                nextintpaydt1 = CbsUtil.dateAdd(tempDt.substring(0, 4) + tempDt.substring(4, 6) + "01", -1);
                                mtdt1 = matdt.substring(0, 4) + matdt.substring(4, 6) + "01";

                                Long tmpMatDays = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                                matDays = tmpMatDays.floatValue();
                                totdays = matDays + CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1)) + 1;

                                totmon = (totdays / 30) + CbsUtil.monthDiff(ymd.parse(CbsUtil.monthAdd(nextintpaydt1, 1)), ymd.parse(matdt));
                                Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                                totdays = tmpDays.floatValue();
                            }

                            if (dtPart == 1 && dateDiff < 0) {
                                mtdt1 = matdt.substring(0, 4) + matdt.substring(4, 6) + "01";
                                totmon = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(mtdt1));

                                Long tmpTotDay = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                                matDays = tmpTotDay.floatValue();
                                totdays = totdays + matDays;
                                totmon = (matDays / 30) + totmon;

                                tmpTotDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                                totdays = tmpTotDay.floatValue();
                            }
                            interest = 0.0f;
                            if (year == 0 && Math.floor(months) == 0) {
                                interest = (pamt * roi * totdays) / 36500;
                            }
                            if (year != 0 || months != 0) {
                                interest = (pamt * roi * totmon / 1200);
                            }
                        }
                        if (interest > 0) {
//                            List acWiseTdsList = em.createNativeQuery("select ifnull(sum(ifnull(tds,0)),0) from tds_reserve_history where acno = '"
//                                    + accno + "' and VoucherNo = '" + voucherno + "' and recovered ='NR' and todt<='" + todt + "' and intOpt = '"
//                                    + intopt + "'").getResultList();
                            double /*tds = 0d,*/ minorInt = 0d, majorInt = 0d;
//                            if (acWiseTdsList.size() > 0) {
//                                Vector acWiseTdsVect = (Vector) acWiseTdsList.get(0);
//                                tds = Double.parseDouble(acWiseTdsVect.get(0).toString());
//                            }
//                            String gurCustId = custId;
                            if (minorFlag.equalsIgnoreCase("Y")) {
//                                System.out.println("MinorCustID==>" + custId + "; Flag:"+minorFlag+": MajorCustId==>" + majorCustId);
                                List mjOMinFlagLst = em.createNativeQuery("select  ifnull(guardiancode,'0') from cbs_cust_minorinfo where customerid ='" + custId + "'").getResultList();
                                if (!mjOMinFlagLst.isEmpty()) {
                                    for (int e = 0; e < mjOMinFlagLst.size(); e++) {
                                        Vector mjOMinVec = (Vector) mjOMinFlagLst.get(e);
//                                        gurCustId = mjOMinVec.get(0).toString();
                                        majorInt = majorInt + autoTermDepositFacade.getMajorOrMinorInt(accno, finStartDt, todt);
                                    }
                                    if (pan.equalsIgnoreCase("N")) {
                                        if (ParseFileUtil.isValidPAN(majorPan) == true) {
                                            pan = "Y";
                                        } else {
                                            pan = "N";
                                        }
                                    }
                                }
                            } else if (!majorCustId.equalsIgnoreCase("")) {
//                                System.out.println("MinorCustID==>" + custId + "; Flag:==>"+minorFlag+": MajorCustId==>" + majorCustId);
                                List minorIntList = em.createNativeQuery("select sum(aa.interest) from "
                                        + "(select ifnull(sum(interest),0) as interest from td_interesthistory where acno in "
                                        + " (select ci.acno from customerid ci where ci.custid =" + majorCustId + " and substring(ci.acno,3,2) in "
                                        + " (select acctcode from accounttypemaster where acctnature in ('FD','MS'))) "
                                        + " and dt between '" + finStartDt + "' and '" + todt + "' "
                                        + "union all "
                                        + "select ifnull(sum(interest),0) as interest from rd_interesthistory where acno in "
                                        + " (select ci.acno from customerid ci where ci.custid = " + majorCustId + " and substring(ci.acno,3,2) in "
                                        + " (select acctcode from accounttypemaster where acctnature in ('RD'))) "
                                        + " and dt between '" + finStartDt + "' and '" + todt + "' "
                                        + "union all "
                                        + "select ifnull(sum(interest),0) as interest from dds_interesthistory where acno in "
                                        + " (select ci.acno from customerid ci where ci.custid = " + majorCustId + " and substring(ci.acno,3,2) in "
                                        + " (select acctcode from accounttypemaster where acctnature in ('DS'))) "
                                        + " and dt between '" + finStartDt + "' and '" + todt + "') aa").getResultList();
                                majorInt = majorInt + Double.parseDouble(((Vector) minorIntList.get(0)).get(0).toString());
                                minorFlag = "Y";
                                if (ParseFileUtil.isValidPAN(majorPan) == true) {
                                    pan = "Y";
                                } else {
                                    pan = "N";
                                }
                            }
                            if (noOfMinorInMajor > 0) {
                                List majorFlagLst = em.createNativeQuery("select  ifnull(customerid,'0') from cbs_cust_minorinfo where guardiancode ='" + custId + "'").getResultList();
                                if (!majorFlagLst.isEmpty()) {
                                    for (int e = 0; e < majorFlagLst.size(); e++) {
                                        Vector mjOMinVec = (Vector) majorFlagLst.get(e);
                                        minorCustId = mjOMinVec.get(0).toString();
//                                        System.out.println("MajorCustID==>" + custId + ": minorCustId==>" + minorCustId);
                                        List minorIntList = em.createNativeQuery("select sum(aa.interest) from "
                                                + "(select ifnull(sum(interest),0) as interest from td_interesthistory where acno in "
                                                + " (select ci.acno from customerid ci where ci.custid in (" + minorCustId + ") and substring(ci.acno,3,2) in "
                                                + " (select acctcode from accounttypemaster where acctnature in ('FD','MS'))) "
                                                + " and dt between '" + finStartDt + "' and '" + todt + "' "
                                                + "union all "
                                                + "select ifnull(sum(interest),0) as interest from rd_interesthistory where acno in "
                                                + " (select ci.acno from customerid ci where ci.custid in (" + minorCustId + ") and substring(ci.acno,3,2) in "
                                                + " (select acctcode from accounttypemaster where acctnature in ('RD'))) "
                                                + " and dt between '" + finStartDt + "' and '" + todt + "' "
                                                + "union all "
                                                + "select ifnull(sum(interest),0) as interest from dds_interesthistory where acno in "
                                                + " (select ci.acno from customerid ci where ci.custid in (" + minorCustId + ") and substring(ci.acno,3,2) in "
                                                + " (select acctcode from accounttypemaster where acctnature in ('DS'))) "
                                                + " and dt between '" + finStartDt + "' and '" + todt + "') aa").getResultList();
                                        minorInt = minorInt + Double.parseDouble(((Vector) minorIntList.get(0)).get(0).toString());
                                    }
                                }
                            }
                            TdIntDetail tdIntDetail = new TdIntDetail();
                            tdIntDetail.setMsg("TRUE");
                            tdIntDetail.setBnkName(bnkName);
                            tdIntDetail.setBnkAdd(bnkAddress);
                            tdIntDetail.setAcno(accno);
                            tdIntDetail.setCustName(custName);
                            tdIntDetail.setVoucherNo(Math.round(voucherno));

                            tdIntDetail.setpAmt(pamt);
                            tdIntDetail.setRoi(roi);
                            tdIntDetail.setFromDt(nextintpaydt);

                            tdIntDetail.setMatDt(matdt);
                            //tdIntDetail.setInterest(CbsUtil.round(interest, 0));
                            double tmpInt = Math.floor(interest);
                            tdIntDetail.setInterest(tmpInt);

//                            tdIntDetail.setTds(tds);
//                            tdIntDetail.setIntPaid(tmpInt - tds);
                            tdIntDetail.setUnRecoverTds(unRecTdsVchWise);
                            tdIntDetail.setUnRecoverTdsCustId(unRecTds);
                            tdIntDetail.setCloseAcTds(closeAcTds);

                            tdIntDetail.setIntToAcno(inttoacno);

                            tdIntDetail.setToDt(todt);
                            tdIntDetail.setSeqno(seqno);
                            tdIntDetail.setIntOpt(intopt1);
                            tdIntDetail.setTdsFlag(tdsFlag);
                            tdIntDetail.setTotalInt(totalIntPaid);
                            tdIntDetail.setTotalTds(totalTds);
                            tdIntDetail.setTdsDeducted(tdsVouchWise);
                            tdIntDetail.setMinorInterest(minorInt);
                            tdIntDetail.setMajorInterest(majorInt);
                            tdIntDetail.setInterestWithMinMaj(totalIntPaid + minorInt + majorInt);
                            tdIntDetail.setCustId(custId);
                            tdIntDetail.setMinorFlag(minorFlag.equalsIgnoreCase("Y") ? minorFlag : "");
                            tdIntDetail.setPan(pan);
                            tdIntDetail.setTotalIntPaidVouchWise(intVouchWise);
                            tdIntDetail.setMajorCustId((!majorCustId.equalsIgnoreCase("")) ? majorCustId : custId);
                            tdIntDetail.setMinorCustId(minorCustId);
                            tdIntDetail.setPropCustId(propCustId);
                            tdIntDetail.setCustType(custType);
                            tdIntDetail.setDob(dob);
                            tdIntDetail.setMajDob(majDob);
                            tdIntDetail.setCustEntityType(custEntityType);
                            tdIntDetailList.add(tdIntDetail);
                        }
                    }

                    if (intopt1.equalsIgnoreCase("C")) {
//                    for (int i = 0; i < cursorLt.size(); i++) {
//                        Vector curV = (Vector) cursorLt.get(i);
                        seqno = Float.parseFloat(curV.get(0).toString());
                        accno = curV.get(1).toString();
                        custName = curV.get(2).toString();
                        voucherno = Float.parseFloat(curV.get(3).toString());
                        roi = Float.parseFloat(curV.get(4).toString());

                        nextintpaydt = curV.get(5).toString();
                        matdt = curV.get(6).toString();
                        float pamt1 = Float.parseFloat(curV.get(7).toString());

                        year = Integer.parseInt(curV.get(8).toString());
                        months = Integer.parseInt(curV.get(9).toString());
                        //days = Integer.parseInt(curV.get(10).toString());

                        cumuprinamt = Float.parseFloat(curV.get(11).toString());
                        inttoacno = curV.get(12).toString();
                        // tdsdeduct = Float.parseFloat(curV.get(12).toString());
                        pamt = cumuprinamt;
                        String custId = (curV.get(16).toString());
                        String minorFlag = (curV.get(17).toString());
                        String tdsFlag = (curV.get(18).toString());
                        double totalIntPaid = Double.parseDouble(curV.get(19).toString());
                        double totalTds = Double.parseDouble(curV.get(20).toString());
                        int noOfMinorInMajor = Integer.parseInt(curV.get(21).toString());
                        String pan = curV.get(22).toString();
                        if (ParseFileUtil.isValidPAN(pan) == true) {
                            pan = "Y";
                        } else {
                            pan = "N";
                        }
                        double intVouchWise = Double.parseDouble(curV.get(23).toString());
                        double tdsVouchWise = Double.parseDouble(curV.get(24).toString());
                        String propCustId = curV.get(25).toString();
                        double unRecTds = Double.parseDouble(curV.get(26).toString());
                        double unRecTdsVchWise = Double.parseDouble(curV.get(27).toString());
                        double closeAcTds = Double.parseDouble(curV.get(28).toString());
                        String majorCustId = curV.get(29).toString();
                        String majorPan = curV.get(30).toString();
                        String custType = curV.get(31).toString();
                        String dob = curV.get(32).toString();
                        String custEntityType = curV.get(33).toString();
                        String minorCustId = "";

                        long dtPart = CbsUtil.datePart("D", nextintpaydt);
                        long mPart = CbsUtil.datePart("M", nextintpaydt);
                        long dtDiff = CbsUtil.dayDiff(ymd.parse(todt), ymd.parse(matdt));
                        int totquarter = 0;
                        if ((dtPart != 1 && dtDiff >= 0) || ((dtPart == 1) && (mPart != 4 && mPart != 7 && mPart != 10 && mPart != 1) && dtDiff >= 0)) {
                            interest = 0.0f;
                            totquarter = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) / 3;
                            lastQuarter = totquarter * 3;

                            totdays1 = CbsUtil.monthAdd(CbsUtil.dateAdd(todt, 1), -(lastQuarter));
                            Long tmpD = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(totdays1));
                            totdays = tmpD.floatValue();

                            if (totquarter == 0) {
                                interest = pamt * (1 + roi / 400) / (1 + roi / 36500 * (91 - totdays)) - pamt;
                            }
                            if (totquarter != 0) {
                                quarterDays = CbsUtil.dayDiff(ymd.parse(CbsUtil.monthAdd(totdays1, -3)), ymd.parse(totdays1));
                                interest = (pamt * (1 + roi / 400) / (1 + roi / 36500 * (91 - totdays))) - pamt;
                                intAmt = interest;
                                pamt = pamt + interest;

                                Double tmpInt = (Math.pow(1 + roi / 400, totquarter) - 1) * pamt;
                                interest = tmpInt.floatValue();
                                interest = interest + intAmt;
                            }
                        }
                        if (dtPart == 1 && (mPart == 1 || mPart == 4 || mPart == 7 || mPart == 10) && dtDiff >= 0) {
                            totquarter = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(CbsUtil.monthAdd(todt, 1)));
                            totquarter = totquarter / 3;
                            Double tmpInt = (Math.pow(1 + roi / 400, totquarter) - 1) * pamt;
                            interest = tmpInt.floatValue();
                        }
                        if (dtPart == 1 && (mPart == 1 || mPart == 4 || mPart == 7 || mPart == 10) && dtDiff < 0) {
                            totquarter = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                            totquarter = totquarter / 3;
                            if (totquarter == 0) {
                                Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                                totdays = tmpDays.floatValue();
                                interest = pamt * roi * totdays / 36500;
                            } else {
                                Double tmpInt = (Math.pow(1 + roi / 400, totquarter) - 1) * pamt;
                                interest = tmpInt.floatValue();
                                intAmt = interest;
                                pamt = pamt + interest;
                                totquarter = totquarter * 3;
                                mtdt1 = CbsUtil.monthAdd(nextintpaydt, totquarter);

                                Long tmpDt = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                                matDays = tmpDt.floatValue();
                                interest = pamt * roi * matDays / 36500;
                                interest = interest + intAmt;
                            }
                        }
                        if ((dtPart != 1 && dtDiff < 0) || ((dtPart == 1) && (mPart != 1 && mPart != 4 && mPart != 7 && mPart != 10) && dtDiff < 0)) {
                            nextintpaydt1 = nextintpaydt;
                            long monPart = CbsUtil.datePart("M", nextintpaydt);
                            if (monPart >= 1 && monPart <= 3) {
                                nextintpaydt1 = CbsUtil.datePart("Y", nextintpaydt) + "0401";
                            } else if (monPart >= 4 && monPart <= 6) {
                                nextintpaydt1 = CbsUtil.datePart("Y", nextintpaydt) + "0701";
                            } else if (monPart >= 7 && monPart <= 9) {
                                nextintpaydt1 = CbsUtil.datePart("Y", nextintpaydt) + "1001";
                            } else if (monPart >= 10 && monPart <= 12) {
                                nextintpaydt1 = CbsUtil.datePart("Y", CbsUtil.yearAdd(nextintpaydt, 1)) + "0101";
                            }
                            Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1));
                            totdays = tmpDays.floatValue();
                            quarterDays = CbsUtil.dayDiff(ymd.parse(CbsUtil.monthAdd(nextintpaydt1, -3)), ymd.parse(nextintpaydt1));
                            totquarter = CbsUtil.monthDiff(ymd.parse(nextintpaydt1), ymd.parse(matdt)) / 3;

                            lastQuarter = totquarter * 3;
                            nextintpaydt1 = CbsUtil.monthAdd(nextintpaydt1, lastQuarter);

                            tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt1), ymd.parse(matdt));
                            matDays = tmpDays.floatValue();
                            if ((totquarter == 0) || ((quarterDays - totdays - matDays) > 0)) {
                                interest = pamt * (1 + roi * (totdays + matDays) / 36500) / (1 + roi * matDays / 36500) - pamt;
                            } else {
                                interest = (pamt * (1 + roi / 400) / (1 + roi / 36500 * (quarterDays - totdays))) - pamt;
                            }
                            intAmt = interest;
                            pamt = pamt + interest;
                            Double tmpInt = (Math.pow(1 + roi / 400, totquarter) - 1) * pamt;
                            interest = tmpInt.floatValue();

                            intAmt = intAmt + interest;
                            pamt = pamt + interest;
                            interest = pamt * roi * matDays / 36500;
                            interest = intAmt + interest;
                        }
                        if (interest > 0) {
//                            List acWiseTdsList = em.createNativeQuery("select ifnull(sum(ifnull(tds,0)),0) from tds_reserve_history where acno = '"
//                                    + accno + "' and VoucherNo = '" + voucherno + "' and recovered ='NR' and todt<='" + todt + "' and intOpt = '"
//                                    + intopt + "'").getResultList();
//                            double tds = 0d;
//                            if (acWiseTdsList.size() > 0) {
//                                Vector acWiseTdsVect = (Vector) acWiseTdsList.get(0);
//                                tds = Double.parseDouble(acWiseTdsVect.get(0).toString());
//                            }
                            double minOrMajorInt = 0d, majorInt = 0d, minorInt = 0d;
//                            String gurCustId = custId;                            
                            if (minorFlag.equalsIgnoreCase("Y")) {
                                List mjOMinFlagLst = em.createNativeQuery("select ifnull(guardiancode,'0') from cbs_cust_minorinfo where customerid ='" + custId + "'").getResultList();
                                if (!mjOMinFlagLst.isEmpty()) {
                                    Vector mjOMinVec = (Vector) mjOMinFlagLst.get(0);
//                                    gurCustId = mjOMinVec.get(0).toString();
                                    majorInt = majorInt + autoTermDepositFacade.getMajorOrMinorInt(accno, finStartDt, todt);
                                    if (pan.equalsIgnoreCase("N")) {
                                        if (ParseFileUtil.isValidPAN(majorPan) == true) {
                                            pan = "Y";
                                        } else {
                                            pan = "N";
                                        }
                                    }
                                }
                            } else if (!majorCustId.equalsIgnoreCase("")) {
//                                System.out.println("MinorCustID==>"+custId+": MajorCustId==>"+majorCustId);
                                List minorIntList = em.createNativeQuery("select sum(aa.interest) from "
                                        + "(select ifnull(sum(interest),0) as interest from td_interesthistory where acno in "
                                        + " (select ci.acno from customerid ci where ci.custid =" + majorCustId + " and substring(ci.acno,3,2) in "
                                        + " (select acctcode from accounttypemaster where acctnature in ('FD','MS'))) "
                                        + " and dt between '" + finStartDt + "' and '" + todt + "' "
                                        + "union all "
                                        + "select ifnull(sum(interest),0) as interest from rd_interesthistory where acno in "
                                        + " (select ci.acno from customerid ci where ci.custid = " + majorCustId + " and substring(ci.acno,3,2) in "
                                        + " (select acctcode from accounttypemaster where acctnature in ('RD'))) "
                                        + " and dt between '" + finStartDt + "' and '" + todt + "' "
                                        + "union all "
                                        + "select ifnull(sum(interest),0) as interest from dds_interesthistory where acno in "
                                        + " (select ci.acno from customerid ci where ci.custid = " + majorCustId + " and substring(ci.acno,3,2) in "
                                        + " (select acctcode from accounttypemaster where acctnature in ('DS'))) "
                                        + " and dt between '" + finStartDt + "' and '" + todt + "') aa").getResultList();
                                majorInt = majorInt + Double.parseDouble(((Vector) minorIntList.get(0)).get(0).toString());
                                minorFlag = "Y";
                                if (ParseFileUtil.isValidPAN(majorPan) == true) {
                                    pan = "Y";
                                } else {
                                    pan = "N";
                                }
                            }
                            if (noOfMinorInMajor > 0) {
                                List majorFlagLst = em.createNativeQuery("select  ifnull(customerid,'0') from cbs_cust_minorinfo where guardiancode ='" + custId + "'").getResultList();
                                if (!majorFlagLst.isEmpty()) {
                                    for (int e = 0; e < majorFlagLst.size(); e++) {
                                        Vector mjOMinVec = (Vector) majorFlagLst.get(e);
                                        minorCustId = minorCustId.equalsIgnoreCase("") ? mjOMinVec.get(0).toString() : minorCustId.concat(":").concat(mjOMinVec.get(0).toString());
                                        List minorIntList = em.createNativeQuery("select sum(aa.interest) from "
                                                + "(select ifnull(sum(interest),0) as interest from td_interesthistory where acno in "
                                                + " (select ci.acno from customerid ci where ci.custid ='" + minorCustId + "' and substring(ci.acno,3,2) in "
                                                + " (select acctcode from accounttypemaster where acctnature in ('FD','MS'))) "
                                                + " and dt between '" + finStartDt + "' and '" + todt + "' "
                                                + "union all "
                                                + "select ifnull(sum(interest),0) as interest from rd_interesthistory where acno in "
                                                + " (select ci.acno from customerid ci where ci.custid = '" + minorCustId + "' and substring(ci.acno,3,2) in "
                                                + " (select acctcode from accounttypemaster where acctnature in ('RD'))) "
                                                + " and dt between '" + finStartDt + "' and '" + todt + "' "
                                                + "union all "
                                                + "select ifnull(sum(interest),0) as interest from dds_interesthistory where acno in "
                                                + " (select ci.acno from customerid ci where ci.custid = '" + minorCustId + "' and substring(ci.acno,3,2) in "
                                                + " (select acctcode from accounttypemaster where acctnature in ('DS'))) "
                                                + " and dt between '" + finStartDt + "' and '" + todt + "') aa").getResultList();
                                        minorInt = minorInt + Double.parseDouble(((Vector) minorIntList.get(0)).get(0).toString());
                                    }
                                }
                            }
                            TdIntDetail tdIntDetail = new TdIntDetail();
                            tdIntDetail.setMsg("TRUE");
                            tdIntDetail.setBnkName(bnkName);
                            tdIntDetail.setBnkAdd(bnkAddress);

                            tdIntDetail.setAcno(accno);
                            tdIntDetail.setCustName(custName);
                            tdIntDetail.setVoucherNo(Math.round(voucherno));

                            tdIntDetail.setpAmt(pamt1);
                            tdIntDetail.setRoi(roi);
                            tdIntDetail.setFromDt(nextintpaydt);

                            tdIntDetail.setMatDt(matdt);
                            double tmpInt = Math.floor(interest);
                            tdIntDetail.setInterest(tmpInt);

//                            tdIntDetail.setTds(tds);
//                            tdIntDetail.setIntPaid(tmpInt - tds);
                            tdIntDetail.setIntToAcno(inttoacno);
                            tdIntDetail.setToDt(todt);
                            tdIntDetail.setSeqno(seqno);
                            tdIntDetail.setIntOpt(intopt1);
                            tdIntDetail.setTdsFlag(tdsFlag);
                            tdIntDetail.setTotalInt(totalIntPaid);
                            tdIntDetail.setTotalTds(totalTds);
                            tdIntDetail.setUnRecoverTds(unRecTdsVchWise);
                            tdIntDetail.setUnRecoverTdsCustId(unRecTds);
                            tdIntDetail.setCloseAcTds(closeAcTds);
                            tdIntDetail.setTdsDeducted(tdsVouchWise);
                            tdIntDetail.setMinorInterest(minorInt);
                            tdIntDetail.setMajorInterest(majorInt);
                            tdIntDetail.setInterestWithMinMaj(totalIntPaid + minorInt + majorInt);
                            tdIntDetail.setCustId(custId);
                            tdIntDetail.setMinorFlag(minorFlag.equalsIgnoreCase("Y") ? minorFlag : "");
                            tdIntDetail.setPan(pan);
                            tdIntDetail.setTotalIntPaidVouchWise(intVouchWise);
                            tdIntDetail.setMajorCustId((!majorCustId.equalsIgnoreCase("")) ? majorCustId : custId);
                            tdIntDetail.setMinorCustId(minorCustId);
                            tdIntDetail.setPropCustId(propCustId);
                            tdIntDetail.setCustType(custType);
                            tdIntDetail.setDob(dob);
                            tdIntDetail.setMajDob(majDob);
                            tdIntDetail.setCustEntityType(custEntityType);
                            tdIntDetailList.add(tdIntDetail);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return tdIntDetailList;
    }

    public Map<String, Object> tdMQIntPostWithTds(List<TdIntDetail> tdIntDetailsList, String acctype, String intopt, String enterBy, String td_inthead,
            String todate, String glHead, String orgnBrCode) throws ApplicationException {
        String resultMessage = "";//, tdsPostEnable = "N";
        Float recno, trsno, trsno2;
        Map<String, Object> map = new HashMap<String, Object>();
        List<TdInterestSmsTo> tdIntList = new ArrayList<TdInterestSmsTo>();
        List<TdInterestSmsTo> tdIntSmslist = new ArrayList<TdInterestSmsTo>();
        try {
            double totalInterest = 0d;
            double totalTds = 0d;
            String finStartDt = rbiReportFacade.getMinFinYear(todate);
            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
                totalInterest = totalInterest + tdIntDetail.getInterest();
                //  totalTds = totalTds + tdIntDetail.getTds();
            }
            int simplePostFlag = 0;
            List simplePostFlagList = em.createNativeQuery("SELECT ifnull(SIMPLEPOSTFLAG,0) FROM td_parameterinfo").getResultList();
            if (!simplePostFlagList.isEmpty()) {
                Vector simplePostFlagVect = (Vector) simplePostFlagList.get(0);
                simplePostFlag = Integer.parseInt(simplePostFlagVect.get(0).toString());
            }

            recno = ftsMethods.getRecNo();

            trsno = ftsMethods.getTrsNo();
            trsno2 = ftsMethods.getTrsNo();

            Integer rdrecon = em.createNativeQuery("insert into gl_recon (acno,dt,valueDt,dramt,ty,trantype,details,auth,enterBy,"
                    + "authby,trsno,payby,recno,TRANDESC,IY,org_brnid,dest_brnid) values('" + td_inthead + "',date_format(now(),'%Y%m%d')" + ",date_format(now(),'%Y%m%d'),"
                    + "" + totalInterest + ",1,8, 'Interest Amount','Y','" + enterBy + "','System'," + trsno + ",3,"
                    + "" + recno + ",4,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
            if (rdrecon <= 0) {
                map.put("msg", "Error in GL_Entry !!!");
                map.put("list", tdIntList);
                return map;
            }

            List reconbalanList = em.createNativeQuery("select acno from reconbalan where acno='" + td_inthead + "'").getResultList();
            if (!reconbalanList.isEmpty()) {
                Integer reconbalan = em.createNativeQuery("update reconbalan set balance=ifnull(balance,0)- ifnull('" + totalInterest + "',0),"
                        + "dt=date_format(now(),'%Y%m%d') where acno='" + td_inthead + "'").executeUpdate();
                if (reconbalan <= 0) {
                    map.put("msg", "Error in Updating GL Balance !!!");
                    map.put("list", tdIntList);
                    return map;
                }
            } else {
                Integer reconbalan = em.createNativeQuery("insert into reconbalan(acno,balance,dt)values('" + td_inthead + "',"
                        + "-" + totalInterest + ",date_format(now(),'%Y%m%d'))").executeUpdate();
                if (reconbalan <= 0) {
                    map.put("msg", "Error in Updating GL Balance !!!");
                    map.put("list", tdIntList);
                    return map;
                }
            }

//            List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
//            if (!tdsPostEnableList.isEmpty()) {
//                Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
//                tdsPostEnable = tdsPostEnableVect.get(0).toString();
//            }
            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
                double interest = tdIntDetail.getInterest();
                String acno = tdIntDetail.getAcno();
                float vchNo = tdIntDetail.getVoucherNo();
                String tdsFlag = tdIntDetail.getTdsFlag();
                String majorCustId = tdIntDetail.getMajorCustId();
                String minorFlag = tdIntDetail.getMinorFlag();
                String propCustId = tdIntDetail.getPropCustId();
                double propInt = tdIntDetail.getPropInt();
                double unRecoverTds = tdIntDetail.getUnRecoverTds();
                double minorInterest = tdIntDetail.getMinorInterest();
                double majorInterest = tdIntDetail.getMajorInterest();
                double interestWithMinMaj = tdIntDetail.getInterestWithMinMaj();
                double custIdWiseCurrentInt = tdIntDetail.getCustIdWiseCurrentInt();
                double totalIntPaidVouchWise = tdIntDetail.getTotalIntPaidVouchWise();
                String opt = tdIntDetail.getIntOpt();
                double closeAcInt = tdIntDetail.getCloseAcIntPaid();
                double closeAcTds = 0d;
                double tdsPer = tdIntDetail.getTdsPer();
                String intToAcno = tdIntDetail.getIntToAcno();
                String fromDt = tdIntDetail.getFromDt();
                String toDt = tdIntDetail.getToDt();
                double tds = tdIntDetail.getTds();
                double excessTdsShifted = tdIntDetail.getTdsExtraShift();
                //System.out.println("AcNo: "+acno+"; VchNo: "+vchNo);
                if (tds < 0) {
                    tds = 0;
                }
                String closeAcFlag = "";
                if (closeAcInt > 0 && tds > 0) {
                    closeAcFlag = " includes closed accounts TDS";
                    closeAcTds = Math.round((closeAcInt * tdsPer) / 100);
                }
//                }
                if ((intopt.equalsIgnoreCase("S") && simplePostFlag > 0) || intopt.equalsIgnoreCase("M") || intopt.equalsIgnoreCase("Q") || intopt.equalsIgnoreCase("Y")) {
                    recno = ftsMethods.getRecNo();
//                    recno = recno + 1;

                    Integer tdrecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,cramt,voucherno,intflag,ty,trantype,"
                            + "details,auth,enterby,authby,recno,trsno,PAYBY,TRANDESC,IY,org_brnid,dest_brnid)values('" + acno + "',"
                            + "date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d')," + interest + "," + vchNo + ",'I',0,8,'Int on Vch: "
                            + vchNo + "','Y','" + enterBy + "','System'," + recno + "," + trsno + ",3,4,1,'" + orgnBrCode + "','"
                            + orgnBrCode + "')").executeUpdate();
                    if (tdrecon <= 0) {
                        map.put("msg", "Error in TD Credit Entry !!!");
                        map.put("list", tdIntList);
                        return map;
                    }
                    if (tds > 0 && tds <= interest) {
                        totalTds = totalTds + tds;
                        Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered, trsno, recno, tdsRecoveredDt, recoveredVch, closeAcTds,excessTdsShifted)"
                                + "VALUES('" + acno + "'," + vchNo + "," + tds + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "','R'," + trsno + "," + recno + ",date_format(now(),'%Y%m%d'), " + trsno + "," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                        if (tdsHisResult <= 0) {
                            throw new ApplicationException("Data Not Saved For " + acno);
                        }
//                        Query updateQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', trsno = " + trsno + ", recno = " + recno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
//                                + " where acno = '" + acno + "' and VoucherNo = '" + vchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
//                        int result = updateQuery.executeUpdate();
//                        if (result < 0) {
//                            map.put("msg", "Error in updating tdshistory for tds ");
//                            map.put("list", tdIntList);
//                            return map;
//                        }

                        Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                + "VALUES('" + acno + "'," + vchNo + "," + tds + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "')").executeUpdate();
                        if (TDSHistory <= 0) {
                            map.put("msg", "Data Not Saved For " + acno);
                            map.put("list", tdIntList);
                            return map;
                        }

                        double remainingInt = interest - tds;
                        /*
                         *Getting TDS Deduction from Close account which are not recovered
                         */
                        if (unRecoverTds > 0 && remainingInt > 0) {
                            double unRecCloseAc = autoTermDepositFacade.getCustomerFinYearTds(acno, finStartDt, toDt, "NR", "Y");//Close Account TDS which was UN-RECOVERED
                            List nrCloseList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, finStartDt, toDt, "NR", "Y");
                            if (!nrCloseList.isEmpty()) {
//                                remainingInt = closeAcTdsRecovery(nrCloseList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                for (int cnt = 0; cnt < nrCloseList.size(); cnt++) {
                                    Vector tdsCloseVect = (Vector) nrCloseList.get(cnt);
                                    String closeAcNo = tdsCloseVect.get(0).toString();
                                    float closeVchNo = Float.parseFloat(tdsCloseVect.get(1).toString());
                                    String clActIntOpt = tdsCloseVect.get(2).toString();
                                    String frDtPost = tdsCloseVect.get(3).toString();
                                    String toDtPost = tdsCloseVect.get(4).toString();
                                    double unRecoveredTdsOnCloseAc = Double.parseDouble(tdsCloseVect.get(5).toString());
//                                    double unRecoveredTdsOnCloseAc = autoTermDepositFacade.getClActFinYearTds(closeAcNo, finStartDt, toDt, "NR", closeVchNo);
//                                    tds = tds + unRecoveredTdsOnCloseAc;
                                    if (unRecoveredTdsOnCloseAc <= remainingInt) {
                                        Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                + " where acno = '" + closeAcNo + "' and VoucherNo = '" + closeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                        int result = updateTdsQuery.executeUpdate();
                                        if (result < 0) {
                                            map.put("msg", "Error in updating tdshistory for tds ");
                                            map.put("list", tdIntList);
                                            return map;
                                        }
                                        TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + unRecoveredTdsOnCloseAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                        if (TDSHistory <= 0) {
                                            map.put("msg", "Data Not Saved For " + acno);
                                            map.put("list", tdIntList);
                                            return map;
                                        }
                                        remainingInt = remainingInt - unRecoveredTdsOnCloseAc;
                                        tds = tds + unRecoveredTdsOnCloseAc;
                                        totalTds = totalTds + unRecoveredTdsOnCloseAc;
                                    } else {
                                        break;
                                    }
                                }
                            }

                            /*
                             *Getting TDS Deduction from Running account which are not recovered
                             */
                            if (remainingInt > 0) {
                                double unRecRunningAc = autoTermDepositFacade.getCustomerFinYearTds(acno, finStartDt, toDt, "NR", "N");//Running Account TDS which was UN-RECOVERED
                                List nrActiveList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, finStartDt, toDt, "NR", "N");
//                                System.out.println("acno:"+acno);
                                if (!nrActiveList.isEmpty()) {
//                                    remainingInt = activeAcTdsRecovery(nrActiveList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                    for (int cnt = 0; cnt < nrActiveList.size(); cnt++) {
                                        Vector tdsActiveVect = (Vector) nrActiveList.get(cnt);
                                        String activeAcNo = tdsActiveVect.get(0).toString();
                                        float activeVchNo = Float.parseFloat(tdsActiveVect.get(1).toString());
                                        String activeActIntOpt = tdsActiveVect.get(2).toString();
                                        String frDtPost = tdsActiveVect.get(3).toString();
                                        String toDtPost = tdsActiveVect.get(4).toString();
                                        double unRecoveredTdsOnActiveAc = Double.parseDouble(tdsActiveVect.get(5).toString());
//                                    double unRecoveredTdsOnActiveAc = autoTermDepositFacade.getClActFinYearTds(activeAcNo, finStartDt, toDt, "NR", activeVchNo);
//                                    tds = tds + unRecoveredTdsOnactiveAc;
                                        if (unRecoveredTdsOnActiveAc <= remainingInt) {
                                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                    + " where acno = '" + activeAcNo + "' and VoucherNo = '" + activeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                            int result = updateTdsQuery.executeUpdate();
                                            if (result < 0) {
                                                map.put("msg", "Error in updating tdshistory for tds ");
                                                map.put("list", tdIntList);
                                                return map;
                                            }
                                            TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                    + "VALUES('" + activeAcNo + "'," + activeVchNo + "," + unRecoveredTdsOnActiveAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                            if (TDSHistory <= 0) {
                                                map.put("msg", "Data Not Saved For " + acno);
                                                map.put("list", tdIntList);
                                                return map;
                                            }
                                            remainingInt = remainingInt - unRecoveredTdsOnActiveAc;
                                            tds = tds + unRecoveredTdsOnActiveAc;
                                            totalTds = totalTds + unRecoveredTdsOnActiveAc;
                                        } else {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        recno = ftsMethods.getRecNo();
//                        recno = recno + 1;
                        int tdRecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,dramt,voucherno,intflag,ty,trantype,details,"
                                + "auth,authby,enterby,recno,trsno,payby,trandesc,org_brnid,dest_brnid)values ('" + acno + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                                + tds + "," + vchNo + ",'T',1,2,'Tds Decucted for " + fromDt + " To " + toDt + closeAcFlag + "','Y','System','"
                                + enterBy + "'," + recno + "," + trsno + ",3,33,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                        if (tdRecon <= 0) {
                            map.put("msg", "Error in tdRecon Insertion for TDS");
                            map.put("list", tdIntList);
                            return map;
                        }
                    } else {
                        if (tds > 0) {
                            double unRecTds = tds - interest;
                            tds = interest;
                            totalTds = totalTds + tds;

                            recno = ftsMethods.getRecNo();
//                            recno = recno + 1;
                            int tdRecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,dramt,voucherno,intflag,ty,trantype,details,"
                                    + "auth,authby,enterby,recno,trsno,payby,trandesc,org_brnid,dest_brnid)values ('" + acno + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                                    + tds + "," + vchNo + ",'T',1,2,'Tds Decucted for " + fromDt + "To " + toDt + closeAcFlag + "','Y','System','"
                                    + enterBy + "'," + recno + "," + trsno + ",3,33,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                            if (tdRecon <= 0) {
                                map.put("msg", "Error in tdRecon Insertion for TDS");
                                map.put("list", tdIntList);
                                return map;
                            }

                            /*Start Posting of Recovered TDS*/
                            Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered, trsno, recno, tdsRecoveredDt, recoveredVch, closeAcTds, excessTdsShifted)"
                                    + "VALUES('" + acno + "'," + vchNo + "," + tds + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "','R'," + trsno + "," + recno + ",date_format(now(),'%Y%m%d'), " + trsno + "," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                            if (tdsHisResult <= 0) {
                                throw new ApplicationException("Data Not Saved For " + acno);
                            }

                            Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                    + "VALUES('" + acno + "'," + vchNo + "," + tds + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "')").executeUpdate();
                            if (TDSHistory <= 0) {
                                map.put("msg", "Data Not Saved For " + acno);
                                map.put("list", tdIntList);
                                return map;
                            }
                            /*End Posting of Recovered TDS*/

                            /*Start Posting of UnRecovered TDS*/
                            tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered)"
                                    + "VALUES('" + acno + "'," + vchNo + "," + unRecTds + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "','NR')").executeUpdate();
                            if (tdsHisResult <= 0) {
                                throw new ApplicationException("Data Not Saved For " + acno);
                            }
                            /*End Posting of UnRecovered TDS*/
//                            if ((intopt.equalsIgnoreCase("S") && simplePostFlag > 0)) {
//                                totalTds = totalTds - tds;
//                            }
                        } else if (tds <= 0 && unRecoverTds > 0) {
                            double remainingInt = interest;
                            tds = 0;
                            /*
                             *Getting TDS Deduction from Close account which are not recovered
                             */
                            if (unRecoverTds > 0 && remainingInt > 0) {
                                double unRecCloseAc = autoTermDepositFacade.getCustomerFinYearTds(acno, finStartDt, toDt, "NR", "Y");//Close Account TDS which was UN-RECOVERED
                                List nrCloseList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, finStartDt, toDt, "NR", "Y");
                                if (!nrCloseList.isEmpty()) {
//                                remainingInt = closeAcTdsRecovery(nrCloseList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                    for (int cnt = 0; cnt < nrCloseList.size(); cnt++) {
                                        Vector tdsCloseVect = (Vector) nrCloseList.get(cnt);
                                        String closeAcNo = tdsCloseVect.get(0).toString();
                                        float closeVchNo = Float.parseFloat(tdsCloseVect.get(1).toString());
                                        String clActIntOpt = tdsCloseVect.get(2).toString();
                                        String frDtPost = tdsCloseVect.get(3).toString();
                                        String toDtPost = tdsCloseVect.get(4).toString();
                                        double unRecoveredTdsOnCloseAc = Double.parseDouble(tdsCloseVect.get(5).toString());
//                                    double unRecoveredTdsOnCloseAc = autoTermDepositFacade.getClActFinYearTds(closeAcNo, finStartDt, toDt, "NR", closeVchNo);
//                                    tds = tds + unRecoveredTdsOnCloseAc;
                                        if (unRecoveredTdsOnCloseAc <= remainingInt) {
                                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                    + " where acno = '" + closeAcNo + "' and VoucherNo = '" + closeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                            int result = updateTdsQuery.executeUpdate();
                                            if (result < 0) {
                                                map.put("msg", "Error in updating tdshistory for tds ");
                                                map.put("list", tdIntList);
                                                return map;
                                            }
                                            int TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                    + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + unRecoveredTdsOnCloseAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                            if (TDSHistory <= 0) {
                                                map.put("msg", "Data Not Saved For " + acno);
                                                map.put("list", tdIntList);
                                                return map;
                                            }
                                            remainingInt = remainingInt - unRecoveredTdsOnCloseAc;
                                            tds = tds + unRecoveredTdsOnCloseAc;
                                            totalTds = totalTds + unRecoveredTdsOnCloseAc;
                                        } else {
                                            break;
                                        }
                                    }
                                }

                                /*
                                 *Getting TDS Deduction from Running account which are not recovered
                                 */
                                if (remainingInt > 0) {
                                    double unRecRunningAc = autoTermDepositFacade.getCustomerFinYearTds(acno, finStartDt, toDt, "NR", "N");//Running Account TDS which was UN-RECOVERED
                                    List nrActiveList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, finStartDt, toDt, "NR", "N");
//                                    System.out.println("acno:" + acno);
                                    if (!nrActiveList.isEmpty()) {
//                                    remainingInt = activeAcTdsRecovery(nrActiveList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                        for (int cnt = 0; cnt < nrActiveList.size(); cnt++) {
                                            Vector tdsActiveVect = (Vector) nrActiveList.get(cnt);
                                            String activeAcNo = tdsActiveVect.get(0).toString();
                                            float activeVchNo = Float.parseFloat(tdsActiveVect.get(1).toString());
                                            String activeActIntOpt = tdsActiveVect.get(2).toString();
                                            String frDtPost = tdsActiveVect.get(3).toString();
                                            String toDtPost = tdsActiveVect.get(4).toString();
                                            double unRecoveredTdsOnActiveAc = Double.parseDouble(tdsActiveVect.get(5).toString());
//                                    double unRecoveredTdsOnActiveAc = autoTermDepositFacade.getClActFinYearTds(activeAcNo, finStartDt, toDt, "NR", activeVchNo);
//                                    tds = tds + unRecoveredTdsOnactiveAc;
                                            if (unRecoveredTdsOnActiveAc <= remainingInt) {
                                                Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                        + " where acno = '" + activeAcNo + "' and VoucherNo = '" + activeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                                int result = updateTdsQuery.executeUpdate();
                                                if (result < 0) {
                                                    map.put("msg", "Error in updating tdshistory for tds ");
                                                    map.put("list", tdIntList);
                                                    return map;
                                                }
                                                int TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                        + "VALUES('" + activeAcNo + "'," + activeVchNo + "," + unRecoveredTdsOnActiveAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                                if (TDSHistory <= 0) {
                                                    map.put("msg", "Data Not Saved For " + acno);
                                                    map.put("list", tdIntList);
                                                    return map;
                                                }
                                                remainingInt = remainingInt - unRecoveredTdsOnActiveAc;
                                                tds = tds + unRecoveredTdsOnActiveAc;
                                                totalTds = totalTds + unRecoveredTdsOnActiveAc;
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            recno = ftsMethods.getRecNo();
//                            recno = recno + 1;
                            int tdRecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,dramt,voucherno,intflag,ty,trantype,details,"
                                    + "auth,authby,enterby,recno,trsno,payby,trandesc,org_brnid,dest_brnid)values ('" + acno + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                                    + tds + "," + vchNo + ",'T',1,2,'Tds Decucted for " + fromDt + " To " + toDt + closeAcFlag + "','Y','System','"
                                    + enterBy + "'," + recno + "," + trsno + ",3,33,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                            if (tdRecon <= 0) {
                                map.put("msg", "Error in tdRecon Insertion for TDS");
                                map.put("list", tdIntList);
                                return map;
                            }
                        }
                    }

                    if (tds == 0 && excessTdsShifted > 0) {
                        Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered, trsno, recno, tdsRecoveredDt, recoveredVch, closeAcTds,excessTdsShifted)"
                                + "VALUES('" + acno + "'," + vchNo + "," + tds + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "','R'," + trsno + "," + recno + ",date_format(now(),'%Y%m%d'), " + trsno + "," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                        if (tdsHisResult <= 0) {
                            throw new ApplicationException("Data Not Saved For " + acno);
                        }
                    }

                    if (intopt.equalsIgnoreCase("M") || intopt.equalsIgnoreCase("Q") || intopt.equalsIgnoreCase("Y")) {
                        if (!intToAcno.equalsIgnoreCase(acno)) {
                            String crDate = ymd.format(new Date());
                            double crAmount = 0;
                            if (tds <= interest) {
                                crAmount = interest - tds;
                            } else {
                                crAmount = interest;
//                                totalTds = totalTds - tds;
                            }

                            if (crAmount != 0) {
                                String crDetails = "Int.on Vch : " + String.valueOf(vchNo) + " A/c No. ";

                                if (!ftsMethods.getCurrentBrnCode(intToAcno).equalsIgnoreCase(orgnBrCode)) {
                                    //Interbranch Transaction
                                    recno = ftsMethods.getRecNo();
//                                    recno = recno + 1;

                                    resultMessage = ftsRemoteMethods.cbsPostingSx(intToAcno, 0, crDate, crAmount, 0, 2, crDetails + acno, 0.0f, "", "", "", 3, 0.0f, recno, 4, ftsMethods.getCurrentBrnCode(intToAcno), orgnBrCode, enterBy, "System", trsno2, "", "");
                                    if (resultMessage.substring(0, 4).equalsIgnoreCase("true")) {
                                        recno = ftsMethods.getRecNo();
//                                        recno = recno + 1;

                                        resultMessage = ftsRemoteMethods.cbsPostingCx(acno, 1, crDate, crAmount, 0, 2, crDetails + intToAcno, 0.0f, "", "", "", 3, 0.0f, recno, 4, orgnBrCode, orgnBrCode, enterBy, "System", trsno2, "", "");
                                        if (!resultMessage.substring(0, 4).equalsIgnoreCase("true")) {
                                            map.put("msg", resultMessage);
                                            map.put("list", tdIntList);
                                            return map;
                                        }
                                        ftsMethods.lastTxnDateUpdation(intToAcno);
                                        //Sms List Creation
                                        if (!(intToAcno == null || intToAcno.equals("") || intToAcno.length() != 12
                                                || ftsMethods.getAccountNature(intToAcno).equalsIgnoreCase(CbsConstant.FIXED_AC)
                                                || ftsMethods.getAccountNature(intToAcno).equalsIgnoreCase(CbsConstant.MS_AC))) {
                                            TdInterestSmsTo to = new TdInterestSmsTo();
                                            to.setTdAcno(acno);
                                            to.setPrimaryAcno(intToAcno);
                                            to.setInterest(new BigDecimal(crAmount));

                                            tdIntSmslist.add(to);
                                        }
                                        //End Here
                                    } else {
                                        map.put("msg", resultMessage);
                                        map.put("list", tdIntList);
                                        return map;
                                    }
                                } else if (ftsMethods.getCurrentBrnCode(intToAcno).equalsIgnoreCase(orgnBrCode)) {
                                    //Local Transaction
                                    recno = ftsMethods.getRecNo();
//                                    recno = recno + 1;;

                                    Integer recon = em.createNativeQuery("insert into td_recon(acno,dt,ValueDt,Dramt,voucherno,ty,trantype,details,"
                                            + "auth,enterBy,authby,recno,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) values ('" + acno + "',date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d'),"
                                            + "" + crAmount + "," + vchNo + ",1,2,'Int.on Vch : " + vchNo + " A/c No. " + intToAcno + "',"
                                            + "'Y','" + enterBy + "','System'," + recno + "," + trsno2 + ",3,4,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                                    if (recon <= 0) {
                                        map.put("msg", "Error in TD Debit Entry !!!");
                                        map.put("list", tdIntList);
                                        return map;
                                    }

                                    String acctNature = ftsMethods.getAccountNature(intToAcno);
                                    recno = ftsMethods.getRecNo();
//                                    recno = recno + 1;

                                    resultMessage = ftsMethods.insertRecons(acctNature, intToAcno, 0, crAmount, crDate, crDate, 2, crDetails + acno, enterBy, trsno2, crDate, recno, "Y", "System", 4, 3, "", "", 0.0f, "", "", 1, "", 0.0f, "", null, orgnBrCode, orgnBrCode, 0, "", "", "");
                                    if (resultMessage.equalsIgnoreCase("true")) {
                                        resultMessage = ftsMethods.updateBalance(acctNature, intToAcno, crAmount, 0, "Y", "Y");
                                        if (!resultMessage.equalsIgnoreCase("True")) {
                                            map.put("msg", resultMessage);
                                            map.put("list", tdIntList);
                                            return map;
                                        }
                                    } else {
                                        map.put("msg", resultMessage);
                                        map.put("list", tdIntList);
                                        return map;
                                    }
                                    ftsMethods.lastTxnDateUpdation(intToAcno);
                                    //Sms List Creation
                                    if (!(intToAcno == null || intToAcno.equals("") || intToAcno.length() != 12
                                            || ftsMethods.getAccountNature(intToAcno).equalsIgnoreCase(CbsConstant.FIXED_AC)
                                            || ftsMethods.getAccountNature(intToAcno).equalsIgnoreCase(CbsConstant.MS_AC))) {
                                        TdInterestSmsTo to = new TdInterestSmsTo();
                                        to.setTdAcno(acno);
                                        to.setPrimaryAcno(intToAcno);
                                        to.setInterest(new BigDecimal(crAmount));

                                        tdIntSmslist.add(to);
                                    }
                                    //End Here
                                }
                            }
                        } /* end of inttoacno acno */ else {
                            double crAmount = 0;
                            if (tds <= interest) {
                                crAmount = interest - tds;
                            } else {
//                                totalTds = totalTds - tds;
                                crAmount = interest;
                            }
                            List accounttypemasterList = em.createNativeQuery("select acno from td_reconbalan where acno='" + acno + "'").getResultList();
                            if (!accounttypemasterList.isEmpty()) {
                                Integer reconMQ = em.createNativeQuery(" update td_reconbalan set balance=balance+"
                                        + crAmount + ",dt=date_format(now(),'%Y%m%d') where acno='" + acno + "'").executeUpdate();
                                if (reconMQ <= 0) {
                                    map.put("msg", "Error in Balance Updation !!!");
                                    map.put("list", tdIntList);
                                    return map;
                                }

                            } else {
                                Integer reconMQ = em.createNativeQuery("insert into td_reconbalan(acno,balance,dt)values('" + acno + "',"
                                        + crAmount + ",date_format(now(),'%Y%m%d'))").executeUpdate();
                                if (reconMQ <= 0) {
                                    map.put("msg", "Error in Balance Updation !!!");
                                    map.put("list", tdIntList);
                                    return map;
                                }
                            }
                        }
                    }
                    /**
                     * *** End of intOpt If M,Q,Y ****
                     */
                }

                if (intopt.equalsIgnoreCase("S")) {
                    if (simplePostFlag == 2) {
                        recno = ftsMethods.getRecNo();
//                        recno = recno + 1;
                        double crAmount = 0;
                        if (tds <= interest) {
                            crAmount = interest - tds;
                        } else {
                            crAmount = interest;
//                            totalTds = totalTds - tds;
                        }
                        Integer reconMQ = em.createNativeQuery("insert into td_recon(acno,dt,ValueDt,Dramt,voucherno,intflag,ty,"
                                + "trantype,details,auth,enterBy,authby,recno,trsno,TRANDESC,IY,org_brnid,dest_brnid)values ('" + acno + "',"
                                + "date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d')," + crAmount + "," + vchNo + ",'I',1,2,"
                                + "'Int. on VCH :" + vchNo + "','Y','" + enterBy + "','System'," + recno + "," + trsno + ",4,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                        if (reconMQ <= 0) {
                            map.put("msg", "Error in TD Interest Entry !!!");
                            map.put("list", tdIntList);
                            return map;
                        }
                        List accounttypemasterList = em.createNativeQuery("select acno from td_reconbalan where acno='" + acno + "'").getResultList();
                        if (!accounttypemasterList.isEmpty()) {
                            Integer reconS = em.createNativeQuery(" update td_reconbalan set balance=balance+"
                                    + crAmount + ",dt=date_format(now(),'%Y%m%d') where acno='" + acno + "'").executeUpdate();
                            if (reconS <= 0) {
                                map.put("msg", "Error in Balance Updation !!!");
                                map.put("list", tdIntList);
                                return map;
                            }

                        } else {
                            Integer reconS = em.createNativeQuery("insert into td_reconbalan(acno,balance,dt)values('" + acno + "',"
                                    + crAmount + ",date_format(now(),'%Y%m%d'))").executeUpdate();
                            if (reconS <= 0) {
                                map.put("msg", "Error in Balance Updation !!!");
                                map.put("list", tdIntList);
                                return map;
                            }
                        }
                    } else if (simplePostFlag == 0) {
                        if (tds > 0 && tds <= interest) {
                            totalTds = totalTds + tds;
                            Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered, trsno, recno, tdsRecoveredDt, recoveredVch, closeAcTds, excessTdsShifted)"
                                    + "VALUES('" + acno + "'," + vchNo + "," + tds + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "','R'," + trsno + "," + recno + ",date_format(now(),'%Y%m%d'), " + trsno + "," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                            if (tdsHisResult <= 0) {
                                throw new ApplicationException("Data Not Saved For " + acno);
                            }
                            Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                    + "VALUES('" + acno + "'," + vchNo + "," + tds + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "')").executeUpdate();
                            if (TDSHistory <= 0) {
                                map.put("msg", "Data Not Saved For " + acno);
                                map.put("list", tdIntList);
                                return map;
                            }
                            double remainingInt = interest - tds;
                            /*
                             *Getting TDS Deduction from Close account which are not recovered
                             */
                            if (unRecoverTds > 0 && remainingInt > 0) {
                                double unRecCloseAc = autoTermDepositFacade.getCustomerFinYearTds(acno, finStartDt, toDt, "NR", "Y");//Close Account TDS which was UN-RECOVERED
                                List nrCloseList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, finStartDt, toDt, "NR", "Y");
                                if (!nrCloseList.isEmpty()) {
//                                remainingInt = closeAcTdsRecovery(nrCloseList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                    for (int cnt = 0; cnt < nrCloseList.size(); cnt++) {
                                        Vector tdsCloseVect = (Vector) nrCloseList.get(cnt);
                                        String closeAcNo = tdsCloseVect.get(0).toString();
                                        float closeVchNo = Float.parseFloat(tdsCloseVect.get(1).toString());
                                        String clActIntOpt = tdsCloseVect.get(2).toString();
                                        String frDtPost = tdsCloseVect.get(3).toString();
                                        String toDtPost = tdsCloseVect.get(4).toString();
                                        double unRecoveredTdsOnCloseAc = Double.parseDouble(tdsCloseVect.get(5).toString());
//                                    double unRecoveredTdsOnCloseAc = autoTermDepositFacade.getClActFinYearTds(closeAcNo, finStartDt, toDt, "NR", closeVchNo);
//                                    tds = tds + unRecoveredTdsOnCloseAc;
                                        if (unRecoveredTdsOnCloseAc <= remainingInt) {
                                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                    + " where acno = '" + closeAcNo + "' and VoucherNo = '" + closeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                            int result = updateTdsQuery.executeUpdate();
                                            if (result < 0) {
                                                map.put("msg", "Error in updating tdshistory for tds ");
                                                map.put("list", tdIntList);
                                                return map;
                                            }
                                            TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                    + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + unRecoveredTdsOnCloseAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                            if (TDSHistory <= 0) {
                                                map.put("msg", "Data Not Saved For " + acno);
                                                map.put("list", tdIntList);
                                                return map;
                                            }
                                            remainingInt = remainingInt - unRecoveredTdsOnCloseAc;
                                            tds = tds + unRecoveredTdsOnCloseAc;
                                            totalTds = totalTds + unRecoveredTdsOnCloseAc;
                                        } else {
                                            break;
                                        }
                                    }
                                }

                                /*
                                 *Getting TDS Deduction from Running account which are not recovered
                                 */
                                if (remainingInt > 0) {
                                    double unRecRunningAc = autoTermDepositFacade.getCustomerFinYearTds(acno, finStartDt, toDt, "NR", "N");//Running Account TDS which was UN-RECOVERED
                                    List nrActiveList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, finStartDt, toDt, "NR", "N");
                                    if (!nrActiveList.isEmpty()) {
//                                    remainingInt = activeAcTdsRecovery(nrActiveList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                        for (int cnt = 0; cnt < nrActiveList.size(); cnt++) {
                                            Vector tdsActiveVect = (Vector) nrActiveList.get(cnt);
                                            String activeAcNo = tdsActiveVect.get(0).toString();
                                            float activeVchNo = Float.parseFloat(tdsActiveVect.get(1).toString());
                                            String activeActIntOpt = tdsActiveVect.get(2).toString();
                                            String frDtPost = tdsActiveVect.get(3).toString();
                                            String toDtPost = tdsActiveVect.get(4).toString();
                                            double unRecoveredTdsOnActiveAc = Double.parseDouble(tdsActiveVect.get(5).toString());
//                                    double unRecoveredTdsOnActiveAc = autoTermDepositFacade.getClActFinYearTds(activeAcNo, finStartDt, toDt, "NR", activeVchNo);
//                                    tds = tds + unRecoveredTdsOnactiveAc;
                                            if (unRecoveredTdsOnActiveAc <= remainingInt) {
                                                Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                        + " where acno = '" + activeAcNo + "' and VoucherNo = '" + activeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                                int result = updateTdsQuery.executeUpdate();
                                                if (result < 0) {
                                                    map.put("msg", "Error in updating tdshistory for tds ");
                                                    map.put("list", tdIntList);
                                                    return map;
                                                }
                                                TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                        + "VALUES('" + activeAcNo + "'," + activeVchNo + "," + unRecoveredTdsOnActiveAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                                if (TDSHistory <= 0) {
                                                    map.put("msg", "Data Not Saved For " + acno);
                                                    map.put("list", tdIntList);
                                                    return map;
                                                }
                                                remainingInt = remainingInt - unRecoveredTdsOnActiveAc;
                                                tds = tds + unRecoveredTdsOnActiveAc;
                                                totalTds = totalTds + unRecoveredTdsOnActiveAc;
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (tds > 0) {
                                double unRecTds = tds - interest;
                                tds = interest;
                                totalTds = totalTds + tds;

                                recno = ftsMethods.getRecNo();
//                                recno = recno + 1;
                                /*Start Posting of Recovered TDS*/
                                Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered, trsno, recno, tdsRecoveredDt, recoveredVch, closeAcTds, excessTdsShifted)"
                                        + "VALUES('" + acno + "'," + vchNo + "," + tds + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "','R'," + trsno + "," + recno + ",date_format(now(),'%Y%m%d'), " + trsno + "," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                                if (tdsHisResult <= 0) {
                                    throw new ApplicationException("Data Not Saved For " + acno);
                                }
                                Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                        + "VALUES('" + acno + "'," + vchNo + "," + tds + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "')").executeUpdate();
                                if (TDSHistory <= 0) {
                                    map.put("msg", "Data Not Saved For " + acno);
                                    map.put("list", tdIntList);
                                    return map;
                                }
                                /*End Posting of Recovered TDS*/

                                /*Start Posting of UnRecovered TDS*/
                                tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered)"
                                        + "VALUES('" + acno + "'," + vchNo + "," + unRecTds + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "','NR')").executeUpdate();
                                if (tdsHisResult <= 0) {
                                    throw new ApplicationException("Data Not Saved For " + acno);
                                }
                                /*End Posting of UnRecovered TDS*/
//                                if ((intopt.equalsIgnoreCase("S") && simplePostFlag > 0)) {
//                                    totalTds = totalTds - tds;
//                                }
                            } else if (tds <= 0 && unRecoverTds > 0) {
                                double remainingInt = interest;
                                tds = 0;
                                /*
                                 *Getting TDS Deduction from Close account which are not recovered
                                 */
                                if (unRecoverTds > 0 && remainingInt > 0) {
                                    double unRecCloseAc = autoTermDepositFacade.getCustomerFinYearTds(acno, finStartDt, toDt, "NR", "Y");//Close Account TDS which was UN-RECOVERED
                                    List nrCloseList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, finStartDt, toDt, "NR", "Y");
                                    if (!nrCloseList.isEmpty()) {
//                                remainingInt = closeAcTdsRecovery(nrCloseList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                        for (int cnt = 0; cnt < nrCloseList.size(); cnt++) {
                                            Vector tdsCloseVect = (Vector) nrCloseList.get(cnt);
                                            String closeAcNo = tdsCloseVect.get(0).toString();
                                            float closeVchNo = Float.parseFloat(tdsCloseVect.get(1).toString());
                                            String clActIntOpt = tdsCloseVect.get(2).toString();
                                            String frDtPost = tdsCloseVect.get(3).toString();
                                            String toDtPost = tdsCloseVect.get(4).toString();
                                            double unRecoveredTdsOnCloseAc = Double.parseDouble(tdsCloseVect.get(5).toString());
//                                    double unRecoveredTdsOnCloseAc = autoTermDepositFacade.getClActFinYearTds(closeAcNo, finStartDt, toDt, "NR", closeVchNo);
//                                    tds = tds + unRecoveredTdsOnCloseAc;
                                            if (unRecoveredTdsOnCloseAc <= remainingInt) {
                                                Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                        + " where acno = '" + closeAcNo + "' and VoucherNo = '" + closeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                                int result = updateTdsQuery.executeUpdate();
                                                if (result < 0) {
                                                    map.put("msg", "Error in updating tdshistory for tds ");
                                                    map.put("list", tdIntList);
                                                    return map;
                                                }
                                                int TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                        + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + unRecoveredTdsOnCloseAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                                if (TDSHistory <= 0) {
                                                    map.put("msg", "Data Not Saved For " + acno);
                                                    map.put("list", tdIntList);
                                                    return map;
                                                }
                                                remainingInt = remainingInt - unRecoveredTdsOnCloseAc;
                                                tds = tds + unRecoveredTdsOnCloseAc;
                                                totalTds = totalTds + unRecoveredTdsOnCloseAc;
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                    /*
                                     *Getting TDS Deduction from Running account which are not recovered
                                     */
                                    if (remainingInt > 0) {
                                        double unRecRunningAc = autoTermDepositFacade.getCustomerFinYearTds(acno, finStartDt, toDt, "NR", "N");//Running Account TDS which was UN-RECOVERED
                                        List nrActiveList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, finStartDt, toDt, "NR", "N");
//                                        System.out.println("acno:" + acno);
                                        if (!nrActiveList.isEmpty()) {
//                                    remainingInt = activeAcTdsRecovery(nrActiveList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                            for (int cnt = 0; cnt < nrActiveList.size(); cnt++) {
                                                Vector tdsActiveVect = (Vector) nrActiveList.get(cnt);
                                                String activeAcNo = tdsActiveVect.get(0).toString();
                                                float activeVchNo = Float.parseFloat(tdsActiveVect.get(1).toString());
                                                String activeActIntOpt = tdsActiveVect.get(2).toString();
                                                String frDtPost = tdsActiveVect.get(3).toString();
                                                String toDtPost = tdsActiveVect.get(4).toString();
                                                double unRecoveredTdsOnActiveAc = Double.parseDouble(tdsActiveVect.get(5).toString());
//                                    double unRecoveredTdsOnActiveAc = autoTermDepositFacade.getClActFinYearTds(activeAcNo, finStartDt, toDt, "NR", activeVchNo);
//                                    tds = tds + unRecoveredTdsOnactiveAc;
                                                if (unRecoveredTdsOnActiveAc <= remainingInt) {
                                                    Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                            + " where acno = '" + activeAcNo + "' and VoucherNo = '" + activeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                                    int result = updateTdsQuery.executeUpdate();
                                                    if (result < 0) {
                                                        map.put("msg", "Error in updating tdshistory for tds ");
                                                        map.put("list", tdIntList);
                                                        return map;
                                                    }
                                                    int TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                            + "VALUES('" + activeAcNo + "'," + activeVchNo + "," + unRecoveredTdsOnActiveAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                                    if (TDSHistory <= 0) {
                                                        map.put("msg", "Data Not Saved For " + acno);
                                                        map.put("list", tdIntList);
                                                        return map;
                                                    }
                                                    remainingInt = remainingInt - unRecoveredTdsOnActiveAc;
                                                    tds = tds + unRecoveredTdsOnActiveAc;
                                                    totalTds = totalTds + unRecoveredTdsOnActiveAc;
                                                } else {
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (tds == 0 && excessTdsShifted > 0) {
                            Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered, trsno, recno, tdsRecoveredDt, recoveredVch, closeAcTds, excessTdsShifted)"
                                    + "VALUES('" + acno + "'," + vchNo + "," + tds + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "','R'," + trsno + "," + recno + ",date_format(now(),'%Y%m%d'), " + trsno + "," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                            if (tdsHisResult <= 0) {
                                throw new ApplicationException("Data Not Saved For " + acno);
                            }
                        }
                    }
                }

                Integer interesthistory = em.createNativeQuery("insert into td_interesthistory(acno,voucherno,interest,fromdt,todt,intopt,"
                        + "dt)values ('" + acno + "'," + vchNo + "," + interest + ",'" + fromDt + "','" + toDt + "',"
                        + "'" + intopt + "', date_format(now(),'%Y%m%d'))").executeUpdate();
                if (interesthistory <= 0) {
                    map.put("msg", "Error in TD Interest History Entry !!!");
                    map.put("list", tdIntList);
                    return map;
                }
                String tmpDt = CbsUtil.dateAdd(todate, 1);
                if (tds >= interest) {
                    tds = 0;
                }
                Integer vouchmst = em.createNativeQuery("update td_vouchmst set nextintpaydt='" + tmpDt + "',"
                        + "TDSDEDUCTED=ifnull(TDSDEDUCTED,0) + ifnull(" + tds + ",0) where acno='" + acno + "' and intopt='" + intopt + "' and status='A' "
                        + "AND VOUCHERNO = " + vchNo).executeUpdate();
                if (vouchmst <= 0) {
                    map.put("msg", "Error in TD Voucher Updation !!!");
                    map.put("list", tdIntList);
                    return map;
                }
            }
            //End of For
            //insertion for GLHEADPROV*************************
            if (intopt.equalsIgnoreCase("S") && (simplePostFlag == 0 || simplePostFlag == 2)) {
                String glHeadProv = "";
                List glheadprovList = em.createNativeQuery("SELECT glheadprov FROM  accounttypemaster where acctcode ='" + acctype + "'").getResultList();
                if (!glheadprovList.isEmpty()) {
                    Vector obcGlagVect = (Vector) glheadprovList.get(0);
                    glHeadProv = obcGlagVect.get(0).toString();
                }
                if (glHeadProv == null || glHeadProv.equalsIgnoreCase("") || glHeadProv.length() == 0) {
                    map.put("msg", "GL Head Prov. does not exist in account type master");
                    map.put("list", tdIntList);
                    return map;
                }
                glHeadProv = orgnBrCode + glHeadProv + "01";

                recno = ftsMethods.getRecNo();
//                recno = recno + 1;
                Integer vouchmst = em.createNativeQuery("insert into gl_recon(acno,dt,ValueDt,cramt,ty,trantype,details,auth,enterBy,"
                        + "authby,trsno,payby,TRANDESC,IY,RECNO,org_brnid,dest_brnid) values ('" + glHeadProv + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d')," + totalInterest + "-" + totalTds + ","
                        + "0,2, 'Int. on :" + intopt + " OPTION' ,'Y','" + enterBy + "','System'," + trsno + ",3,4,1," + recno + ",'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                if (vouchmst <= 0) {
                    map.put("msg", "Error in GL Credit Entry !!!");
                    map.put("list", tdIntList);
                    return map;
                }
                List reconBalanList = em.createNativeQuery("select acno from reconbalan where acno='" + glHeadProv + "'").getResultList();
                if (!reconBalanList.isEmpty()) {
                    Integer reconBalanL = em.createNativeQuery("update reconbalan set balance=balance+'" + totalInterest + "',"
                            + "dt=date_format(now(),'%Y%m%d') where acno='" + glHeadProv + "'").executeUpdate();
                    if (reconBalanL <= 0) {
                        map.put("msg", "Error in GL Updation !!!");
                        map.put("list", tdIntList);
                        return map;
                    }
                } else {
                    Integer reconBalan = em.createNativeQuery("insert into reconbalan(acno,balance,dt)values('" + glHeadProv + "','" + totalInterest + "-" + totalTds + "',date_format(now(),'%Y%m%d'))").executeUpdate();
                    if (reconBalan <= 0) {
                        map.put("msg", "Error in GL Updation !!!");
                        map.put("list", tdIntList);
                        return map;
                    }
                }
            }

            //FOR INSERTION OF TDS GL HEAD
//            if ((intopt.equalsIgnoreCase("M") || intopt.equalsIgnoreCase("Q")) || (intopt.equalsIgnoreCase("S") && simplePostFlag == 1)) {
            if (totalTds > 0) {
                Integer reconBalan = em.createNativeQuery("UPDATE reconbalan SET BALANCE=BALANCE+" + totalTds + ",DT=date_format(now(),'%Y%m%d')  WHERE ACNO='" + glHead + "'").executeUpdate();
                if (reconBalan <= 0) {
                    map.put("msg", "Error in GL Balance Updation !!!");
                    map.put("list", tdIntList);
                    return map;
                }
                recno = ftsMethods.getRecNo();
//                recno = recno + 1;

                Integer glrecon = em.createNativeQuery("INSERT INTO gl_recon (ACNO,Dt,ValueDt,CrAmt,Ty,TranType,Details,EnterBy,"
                        + "Auth,AuthBy,TranTime,RecNo,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) VALUES('" + glHead + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                        + "" + totalTds + ",0,2,'TDS POSTING TILL DT " + todate + "','" + enterBy + "','Y','System',now(),'" + recno + "',"
                        + "'" + trsno + "',3,33,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                if (glrecon <= 0) {
                    map.put("msg", "Error in TDS Posting in GL !!!");
                    map.put("list", tdIntList);
                    return map;
                }
            }
//            }
            Integer tdIntFlag = em.createNativeQuery("insert into td_intflag(td_tilldate,td_sysdate,intopt,acType,brncode)values('" + todate + "',"
                    + "date_format(now(),'%Y%m%d'),'" + intopt + "','" + acctype + "','" + orgnBrCode + "')").executeUpdate();
            if (tdIntFlag <= 0) {
                map.put("msg", "Error in TD Interest Flag Updation !!!");
                map.put("list", tdIntList);
                return map;
            }

            map.put("msg", "Yes");
            if (tdIntSmslist.isEmpty()) {
                map.put("list", tdIntList);
            } else {
                map.put("list", tdIntSmslist);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
            return map;
            //
//            
        }
    }

    public double closeAcTdsRecovery(List closeTdsUnRecList, String finStartDt, String toDt, float trsNo,
            String tempBd, double remainingInt, String acno, String intOpt) throws ApplicationException {
        try {
//            double tdsAmt = 0;
//            for (int cnt = 0; cnt < intCloseList.size(); cnt++) {
//                Vector intCloseVect = (Vector) intCloseList.get(cnt);
//
//                //double clFinYearInt = Double.parseDouble(intCloseVect.get(0).toString());
//                float closeVchNo = Float.parseFloat(intCloseVect.get(0).toString());
//                String closeAcNo = intCloseVect.get(1).toString();
//                String clActIntOpt = intCloseVect.get(2).toString();
//
//                //double vchCalTds = clFinYearInt * tdsRate / 100;
//                // double recoveredTds = getClActFinYearTds(closeAcNo, frmDT, toDT, "R", closeVchNo);
//                double unRecoveredTds = autoTermDepositFacade.getClActFinYearTds(closeAcNo, frmDT, toDT, "NR", closeVchNo);
//                // double insertedTds = 0;
////                if (vchCalTds < 0) {
////                    tdsAmt = tdsAmt + vchCalTds;
////                }
////                if (Math.round(vchCalTds) > recoveredTds + unRecoveredTds) {
////                    insertedTds = vchCalTds - recoveredTds + unRecoveredTds;
////
////                    tdsAmt = tdsAmt + insertedTds;
////                    Query insertTdsQuery = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,"
////                            + "recovered, trsno, recno, recoveredVch, tdsRecoveredDt)"
////                            + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + Math.round(insertedTds) + ",'" + tempBd + "','" + toDT + "','" + frmDT + "','"
////                            + clActIntOpt + "','R'," + trsNumber + "," + recNo + "," + rtNumber + ",date_format(now(),'%Y%m%d'))");
////                    int result = insertTdsQuery.executeUpdate();
////                    if (result < 0) {
////                        tdsAmt = tdsAmt - insertedTds;
////                        throw new ApplicationException("Error in updating tdshistory for tds ");
////                    }
////                }
//                if (unRecoveredTds > 0) {
//                    //insertedTds = unRecoveredTds;
//                    tdsAmt = tdsAmt + unRecoveredTds;
//                    Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recno = " + recNo + ", "
//                            + "recoveredVch = " + trsNumber + ", tdsRecoveredDt = " + tempBd + " where acno = '" + closeAcNo + "' and "
//                            + "VoucherNo = " + closeVchNo + " and recovered ='NR' and intOpt = '" + clActIntOpt + "'");
//                    int result = updateTdsQuery.executeUpdate();
//                    if (result < 0) {
//                        throw new ApplicationException("Error in updating tdshistory for tds ");
//                    }
//                    Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
//                            + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + unRecoveredTds + ",'" + tempBd + "','" + tempBd + "','"
//                            + frmDT + "','" + clActIntOpt + "')").executeUpdate();
//                    if (TDSHistory <= 0) {
//                        throw new ApplicationException("Data Not Saved For " + closeAcNo);
//                    }
//                }
//            }
//            double remainTds = calculatedTds - tdsAmt;
//            if (remainTds > 0) {
//                Query insertTdsQuery = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,"
//                        + "recovered, trsno, recno, recoveredVch, tdsRecoveredDt)"
//                        + "VALUES('" + acno + "'," + rtNumber + "," + remainTds + ",'" + tempBd + "','" + tempBd + "','" + frmDT + "','"
//                        + intOpt + "','R'," + trsNumber + "," + recNo + ",0,'" + tempBd + "')");
//                int result = insertTdsQuery.executeUpdate();
//                if (result < 0) {
//                    throw new ApplicationException("Error in updating tdshistory for tds ");
//                }
//                Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
//                        + "VALUES('" + acno + "'," + rtNumber + "," + remainTds + ",'" + tempBd + "','" + tempBd + "','"
//                        + frmDT + "','" + intOpt + "')").executeUpdate();
//                if (TDSHistory <= 0) {
//                    throw new ApplicationException("Data Not Saved For " + acno);
//                }
//            }

            for (int cnt = 0; cnt < closeTdsUnRecList.size(); cnt++) {
                Vector tdsCloseVect = (Vector) closeTdsUnRecList.get(cnt);
                String closeAcNo = tdsCloseVect.get(0).toString();
                float closeVchNo = Float.parseFloat(tdsCloseVect.get(1).toString());
                String clActIntOpt = tdsCloseVect.get(2).toString();
                String frDtPost = tdsCloseVect.get(3).toString();
                String toDtPost = tdsCloseVect.get(4).toString();
                double unRecoveredTdsOnCloseAc = Double.parseDouble(tdsCloseVect.get(5).toString());
//                double unRecoveredTdsOnCloseAc = autoTermDepositFacade.getClActFinYearTds(closeAcNo, finStartDt, toDt, "NR", closeVchNo);
//                                    tds = tds + unRecoveredTdsOnCloseAc;
                if (unRecoveredTdsOnCloseAc <= remainingInt) {
                    Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                            + " where acno = '" + closeAcNo + "' and VoucherNo = '" + closeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intOpt + "'");
                    int result = updateTdsQuery.executeUpdate();
                    if (result != 1) {
                        throw new ApplicationException("Error in Tds Reserve History updation");
                    }
                    int tdsHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                            + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + unRecoveredTdsOnCloseAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intOpt + "')").executeUpdate();
                    if (tdsHistory != 1) {
                        throw new ApplicationException("Error in Tds History insertion");
                    }
                    remainingInt = remainingInt - unRecoveredTdsOnCloseAc;
//                    tds = tds + unRecoveredTdsOnCloseAc;
//                    totalTds = totalTds + unRecoveredTdsOnCloseAc;
                } else {
                    break;
                }
            }
            return remainingInt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public double activeAcTdsRecovery(List activeTdsUnRecList, String finStartDt, String toDt, float trsNo,
            String tempBd, double remainingInt, String acno, String intOpt) throws ApplicationException {
        try {
            for (int cnt = 0; cnt < activeTdsUnRecList.size(); cnt++) {
                Vector tdsActiveVect = (Vector) activeTdsUnRecList.get(cnt);
                String activeAcNo = tdsActiveVect.get(0).toString();
                float activeVchNo = Float.parseFloat(tdsActiveVect.get(1).toString());
                String activeActIntOpt = tdsActiveVect.get(2).toString();
                String frDtPost = tdsActiveVect.get(3).toString();
                String toDtPost = tdsActiveVect.get(4).toString();
                double unRecoveredTdsOnActiveAc = Double.parseDouble(tdsActiveVect.get(5).toString());
//                                    double unRecoveredTdsOnActiveAc = autoTermDepositFacade.getClActFinYearTds(activeAcNo, finStartDt, toDt, "NR", activeVchNo);
//                                    tds = tds + unRecoveredTdsOnactiveAc;
                if (unRecoveredTdsOnActiveAc <= remainingInt) {
                    Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                            + " where acno = '" + activeAcNo + "' and VoucherNo = '" + activeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intOpt + "'");
                    int result = updateTdsQuery.executeUpdate();
                    if (result < 0) {
                        throw new ApplicationException("Error in Tds Reserve History updation");
                    }
                    int tdsHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                            + "VALUES('" + activeAcNo + "'," + activeVchNo + "," + unRecoveredTdsOnActiveAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intOpt + "')").executeUpdate();
                    if (tdsHistory <= 0) {
                        throw new ApplicationException("Error in Tds History insertion");
                    }
                    remainingInt = remainingInt - unRecoveredTdsOnActiveAc;
//                    tds = tds + unRecoveredTdsOnActiveAc;
//                    totalTds = totalTds + unRecoveredTdsOnActiveAc;
                } else {
                    break;
                }
            }
            return remainingInt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String tdIntPostWithTds(List<TdIntDetail> tdIntDetailsList, String acctype, String intopt, String enterBy, String tdIntHead,
            String todate, String orgnBrCode) throws ApplicationException {
        Float recno;
        Float trsno;
        double totalTds = 0d;
        String glAccNo = null;
        String finStartDt = rbiReportFacade.getMinFinYear(todate);
        try {
            double totalInterest = 0d;
            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
                totalInterest = totalInterest + tdIntDetail.getInterest();
            }
            recno = ftsMethods.getRecNo();
            trsno = ftsMethods.getTrsNo();

            ftsMethods.updateBalance("PO", tdIntHead, 0, totalInterest, "", "");

//            List reconBalanList = em.createNativeQuery("select Acno From reconBalan Where acno='" + tdIntHead + "'").getResultList();
//
//            if (!reconBalanList.isEmpty()) {
//                List balanceList = em.createNativeQuery("Select isnull(Balance,0) - isnull('" + totalInterest + "',0) From reconbalan "
//                        + "where acno='" + tdIntHead + "'").getResultList();
//
//                if (!balanceList.isEmpty()) {
//                    Integer reconBalanL = em.createNativeQuery("Update reconbalan set balance=isnull(balance,0) -"
//                            + " isnull('" + totalInterest + "',0),dt=convert(varchar(8),getdate(),112) Where acno='" + tdIntHead + "'").executeUpdate();
//                    if (reconBalanL <= 0) {
//                        return "Error in reconbalan Updation !!!";
//                    }
//                }
//            } else {
//                Integer reconBalanL = em.createNativeQuery("Insert into reconBalan(acno,balance,dt)Values('" + tdIntHead + "',"
//                        + "- " + totalInterest + ",convert(varchar(8),getdate(),112))").executeUpdate();
//                if (reconBalanL <= 0) {
//                    return "Error in reconbalan Insertion !!!";
//                }
//            }
            Integer reconBalanL = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,dramt,ty,trantype,details,auth,authby,enterBy,"
                    + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + tdIntHead + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                    + totalInterest + ",1,8,'Interest Amount','Y','System','" + enterBy + "'," + recno + "," + trsno + ",3,3,'" + orgnBrCode
                    + "','" + orgnBrCode + "')").executeUpdate();
            if (reconBalanL <= 0) {
                return "Error in reconbalan Insertion !!!";
            }

            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
                double interest = tdIntDetail.getInterest();
                String acno = tdIntDetail.getAcno();
                float vchNo = tdIntDetail.getVoucherNo();
                String tdsFlag = tdIntDetail.getTdsFlag();
                String majorCustId = tdIntDetail.getMajorCustId();
                String minorFlag = tdIntDetail.getMinorFlag();
                String propCustId = tdIntDetail.getPropCustId();
                double propInt = tdIntDetail.getPropInt();
                double unRecoverTds = tdIntDetail.getUnRecoverTds();
                double minorInterest = tdIntDetail.getMinorInterest();
                double majorInterest = tdIntDetail.getMajorInterest();
                double interestWithMinMaj = tdIntDetail.getInterestWithMinMaj();
                double custIdWiseCurrentInt = tdIntDetail.getCustIdWiseCurrentInt();
                double totalIntPaidVouchWise = tdIntDetail.getTotalIntPaidVouchWise();
                String opt = tdIntDetail.getIntOpt();
                double closeAcInt = tdIntDetail.getCloseAcIntPaid();
                double closeAcTds = 0d;
                double tdsPer = tdIntDetail.getTdsPer();
                String fromDt = tdIntDetail.getFromDt();
                String toDt = tdIntDetail.getToDt();
                double excessTdsShifted = tdIntDetail.getTdsExtraShift();
                // String acNat = ftsMethods.getAccountNature(acno);

                recno = ftsMethods.getRecNo();
//                recno = recno + 1;

                // ftsMethods.updateBalance(acNat, acno, interest,0, "", "");
                Integer tdRecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,cramt,voucherno,intflag,ty,trantype,details,"
                        + "auth,authby,enterby,recno,trsno,payby,trandesc,org_brnid,dest_brnid)values ('" + acno + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                        + interest + "," + vchNo + ",'I',0,8,'Int.on VCH: " + vchNo + "','Y','System','" + enterBy
                        + "'," + recno + "," + trsno + ",3,3,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                if (tdRecon <= 0) {
                    return "Error in tdRecon Insertion";
                }

                /**
                 * ***TDS Deduction from account **************
                 */
//                List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
//                if (!tdsPostEnableList.iand tv.Status<>'C' sEmpty()) {
//                    Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
//                    tdsPostEnable = tdsPostEnableVect.get(0).toString();
//                }
                double tdsAmt = tdIntDetail.getTds();
                String closeAcFlag = "";
                if (closeAcInt > 0 && tdsAmt > 0) {
                    closeAcFlag = " includes closed accounts TDS";
                    closeAcTds = Math.round((closeAcInt * tdsPer) / 100);
                }
//                if (tdsPostEnable.equalsIgnoreCase("Y")) {
//                    List acWiseTdsList = em.createNativeQuery("select ifnull(sum(ifnull(tds,0)),0) from tds_reserve_history where acno = '" + acno + "' and VoucherNo = '" + vchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'").getResultList();
//                    if (acWiseTdsList.size() > 0) {
//                        Vector acWiseTdsVect = (Vector) acWiseTdsList.get(0);
//                        tdsAmt = Double.parseDouble(acWiseTdsVect.get(0).toString());
//                        totalTds = totalTds + Double.parseDouble(acWiseTdsVect.get(0).toString());

                if (tdsAmt > 0 && tdsAmt <= interest) {
                    totalTds = totalTds + tdsAmt;
                    recno = ftsMethods.getRecNo();
//                    recno = recno + 1;

                    /**
                     * Code Add On 20150624 *
                     */
//                    List tdsCloseList = em.createNativeQuery("select tds,a.voucherno, TXNID from tds_reserve_history a, td_vouchmst b where a.acno = b.acno "
//                            + " and a.recovered ='NR' and b.status ='C' "
//                            + " and a.acno = '" + acno + "' and a.voucherno = b.voucherno order by txnid").getResultList();
//                    for (int cnt = 0; cnt < tdsCloseList.size(); cnt++) {
//                        Vector tdsCloseVect = (Vector) tdsCloseList.get(cnt);
//                        double cltds = Double.parseDouble(tdsCloseVect.get(0).toString());
//                        tdsAmt = tdsAmt + cltds;
//                        if (tdsAmt <= interest) {
//                            totalTds = totalTds + cltds;
//                            String vchCl = tdsCloseVect.get(1).toString();
//                            Integer txnId = Integer.parseInt(tdsCloseVect.get(2).toString());
//                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', trsno = " + trsno + ", recno = " + recno + ", recoveredVch = " + vchNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
//                                    + " where acno = '" + acno + "' and VoucherNo = '" + vchCl + "'  and TXNID = '" + txnId + "' and recovered ='NR' ");
//                            int result = updateTdsQuery.executeUpdate();
//                            if (result < 0) {
//                                tdsAmt = tdsAmt - cltds;
//                                totalTds = totalTds - cltds;
//                                return "Error in updating tdshistory for tds ";
//                            }
//                        } else {
//                            tdsAmt = tdsAmt - cltds;
//                            break;
//                        }
//                    }
                    /**
                     * End Of Code Add On 20150624 *
                     */
                    Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered,trsno,tdsRecoveredDt,recoveredVch, closeAcTds, excessTdsShifted)"
                            + "VALUES('" + acno + "'," + vchNo + "," + tdsAmt + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "','R'," + trsno + ", date_format(now(),'%Y%m%d')," + vchNo + "," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                    if (tdsHisResult <= 0) {
                        throw new ApplicationException("Data Not Saved For " + acno);
                    }
                    Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                            + "VALUES('" + acno + "'," + vchNo + "," + tdsAmt + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "')").executeUpdate();
                    if (TDSHistory <= 0) {
                        throw new ApplicationException("Data Not Saved For " + acno);
                    }
                    double remainingInt = interest - tdsAmt;
                    /*
                     *Getting TDS Deduction from Close account which are not recovered
                     */
                    if (unRecoverTds > 0 && remainingInt > 0) {
                        double unRecCloseAc = autoTermDepositFacade.getCustomerFinYearTds(acno, finStartDt, toDt, "NR", "Y");//Close Account TDS which was UN-RECOVERED
                        List nrCloseList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, finStartDt, toDt, "NR", "Y");
                        if (!nrCloseList.isEmpty()) {
//                                remainingInt = closeAcTdsRecovery(nrCloseList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                            for (int cnt = 0; cnt < nrCloseList.size(); cnt++) {
                                Vector tdsCloseVect = (Vector) nrCloseList.get(cnt);
                                String closeAcNo = tdsCloseVect.get(0).toString();
                                float closeVchNo = Float.parseFloat(tdsCloseVect.get(1).toString());
                                String clActIntOpt = tdsCloseVect.get(2).toString();
                                String frDtPost = tdsCloseVect.get(3).toString();
                                String toDtPost = tdsCloseVect.get(4).toString();
                                double unRecoveredTdsOnCloseAc = Double.parseDouble(tdsCloseVect.get(5).toString());
//                                    double unRecoveredTdsOnCloseAc = autoTermDepositFacade.getClActFinYearTds(closeAcNo, finStartDt, toDt, "NR", closeVchNo);
//                                    tds = tds + unRecoveredTdsOnCloseAc;
                                if (unRecoveredTdsOnCloseAc <= remainingInt) {
                                    Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                            + " where acno = '" + closeAcNo + "' and VoucherNo = '" + closeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                    int result = updateTdsQuery.executeUpdate();
                                    if (result < 0) {
                                        throw new ApplicationException("Error in updating tdshistory for tds ");
                                    }
                                    TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                            + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + unRecoveredTdsOnCloseAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                    if (TDSHistory <= 0) {
                                        throw new ApplicationException("Data Not Saved For " + acno);
                                    }
                                    remainingInt = remainingInt - unRecoveredTdsOnCloseAc;
                                    tdsAmt = tdsAmt + unRecoveredTdsOnCloseAc;
                                    totalTds = totalTds + unRecoveredTdsOnCloseAc;
                                } else {
                                    break;
                                }
                            }
                        }

                        /*
                         *Getting TDS Deduction from Running account which are not recovered
                         */
                        if (remainingInt > 0) {
                            double unRecRunningAc = autoTermDepositFacade.getCustomerFinYearTds(acno, finStartDt, toDt, "NR", "N");//Running Account TDS which was UN-RECOVERED
                            List nrActiveList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, finStartDt, toDt, "NR", "N");
                            if (!nrActiveList.isEmpty()) {
//                                    remainingInt = activeAcTdsRecovery(nrActiveList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                for (int cnt = 0; cnt < nrActiveList.size(); cnt++) {
                                    Vector tdsActiveVect = (Vector) nrActiveList.get(cnt);
                                    String activeAcNo = tdsActiveVect.get(0).toString();
                                    float activeVchNo = Float.parseFloat(tdsActiveVect.get(1).toString());
                                    String activeActIntOpt = tdsActiveVect.get(2).toString();
                                    String frDtPost = tdsActiveVect.get(3).toString();
                                    String toDtPost = tdsActiveVect.get(4).toString();
                                    double unRecoveredTdsOnActiveAc = Double.parseDouble(tdsActiveVect.get(5).toString());
//                                    double unRecoveredTdsOnActiveAc = autoTermDepositFacade.getClActFinYearTds(activeAcNo, finStartDt, toDt, "NR", activeVchNo);
//                                    tds = tds + unRecoveredTdsOnactiveAc;
                                    if (unRecoveredTdsOnActiveAc <= remainingInt) {
                                        Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                + " where acno = '" + activeAcNo + "' and VoucherNo = '" + activeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                        int result = updateTdsQuery.executeUpdate();
                                        if (result < 0) {
                                            throw new ApplicationException("Error in updating tdshistory for tds ");
                                        }
                                        TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                + "VALUES('" + activeAcNo + "'," + activeVchNo + "," + unRecoveredTdsOnActiveAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                        if (TDSHistory <= 0) {
                                            throw new ApplicationException("Data Not Saved For " + acno);
                                        }
                                        remainingInt = remainingInt - unRecoveredTdsOnActiveAc;
                                        tdsAmt = tdsAmt + unRecoveredTdsOnActiveAc;
                                        totalTds = totalTds + unRecoveredTdsOnActiveAc;
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    tdRecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,dramt,voucherno,intflag,ty,trantype,details,"
                            + "auth,authby,enterby,recno,trsno,payby,trandesc,org_brnid,dest_brnid)values ('" + acno + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                            + tdsAmt + "," + vchNo + ",'T',1,2,'Tds Decucted for " + fromDt + " To " + toDt + closeAcFlag + "','Y','System','" + enterBy
                            + "'," + recno + "," + trsno + ",3,33,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                    if (tdRecon <= 0) {
                        return "Error in tdRecon Insertion for TDS";
                    }
                } else {
                    if (tdsAmt > 0) {
                        double unRecTds = tdsAmt - interest;
                        tdsAmt = interest;
                        totalTds = totalTds + tdsAmt;

                        recno = ftsMethods.getRecNo();
//                        recno = recno + 1;
                        tdRecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,dramt,voucherno,intflag,ty,trantype,details,"
                                + "auth,authby,enterby,recno,trsno,payby,trandesc,org_brnid,dest_brnid)values ('" + acno + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                                + tdsAmt + "," + vchNo + ",'T',1,2,'Tds Decucted for " + fromDt + " To " + toDt + closeAcFlag + "','Y','System','" + enterBy
                                + "'," + recno + "," + trsno + ",3,33,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                        if (tdRecon <= 0) {
                            return "Error in tdRecon Insertion for TDS";
                        }
                        Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered,trsno,tdsRecoveredDt,recoveredVch, closeAcTds, excessTdsShifted)"
                                + "VALUES('" + acno + "'," + vchNo + "," + tdsAmt + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "','R'," + trsno + ", date_format(now(),'%Y%m%d')," + vchNo + "," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                        if (tdsHisResult <= 0) {
                            throw new ApplicationException("Data Not Saved For " + acno);
                        }
                        Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                + "VALUES('" + acno + "'," + vchNo + "," + tdsAmt + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "')").executeUpdate();
                        if (TDSHistory <= 0) {
                            throw new ApplicationException("Data Not Saved For " + acno);
                        }
                        Integer unRectdsResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered)"
                                + "VALUES('" + acno + "'," + vchNo + "," + unRecTds + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + opt + "','NR')").executeUpdate();
                        if (unRectdsResult <= 0) {
                            throw new ApplicationException("Data Not Saved For " + acno);
                        }
                    } else if (tdsAmt <= 0 && unRecoverTds > 0) {
                        double remainingInt = interest;
                        tdsAmt = 0;
                        /*
                         *Getting TDS Deduction from Close account which are not recovered
                         */
                        if (unRecoverTds > 0 && remainingInt > 0) {
                            double unRecCloseAc = autoTermDepositFacade.getCustomerFinYearTds(acno, finStartDt, toDt, "NR", "Y");//Close Account TDS which was UN-RECOVERED
                            List nrCloseList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, finStartDt, toDt, "NR", "Y");
                            if (!nrCloseList.isEmpty()) {
//                                remainingInt = closeAcTdsRecovery(nrCloseList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                for (int cnt = 0; cnt < nrCloseList.size(); cnt++) {
                                    Vector tdsCloseVect = (Vector) nrCloseList.get(cnt);
                                    String closeAcNo = tdsCloseVect.get(0).toString();
                                    float closeVchNo = Float.parseFloat(tdsCloseVect.get(1).toString());
                                    String clActIntOpt = tdsCloseVect.get(2).toString();
                                    String frDtPost = tdsCloseVect.get(3).toString();
                                    String toDtPost = tdsCloseVect.get(4).toString();
                                    double unRecoveredTdsOnCloseAc = Double.parseDouble(tdsCloseVect.get(5).toString());
//                                    double unRecoveredTdsOnCloseAc = autoTermDepositFacade.getClActFinYearTds(closeAcNo, finStartDt, toDt, "NR", closeVchNo);
//                                    tds = tds + unRecoveredTdsOnCloseAc;
                                    if (unRecoveredTdsOnCloseAc <= remainingInt) {
                                        Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                + " where acno = '" + closeAcNo + "' and VoucherNo = '" + closeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                        int result = updateTdsQuery.executeUpdate();
                                        if (result < 0) {
                                            throw new ApplicationException("Error in updating tdshistory for tds ");
                                        }
                                        int TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + unRecoveredTdsOnCloseAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                        if (TDSHistory <= 0) {
                                            throw new ApplicationException("Data Not Saved For " + acno);
                                        }
                                        remainingInt = remainingInt - unRecoveredTdsOnCloseAc;
                                        tdsAmt = tdsAmt + unRecoveredTdsOnCloseAc;
                                        totalTds = totalTds + unRecoveredTdsOnCloseAc;
                                    } else {
                                        break;
                                    }
                                }
                            }

                            /*
                             *Getting TDS Deduction from Running account which are not recovered
                             */
                            if (remainingInt > 0) {
                                double unRecRunningAc = autoTermDepositFacade.getCustomerFinYearTds(acno, finStartDt, toDt, "NR", "N");//Running Account TDS which was UN-RECOVERED
                                List nrActiveList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acno, finStartDt, toDt, "NR", "N");
//                                System.out.println("acno:" + acno);
                                if (!nrActiveList.isEmpty()) {
//                                    remainingInt = activeAcTdsRecovery(nrActiveList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                    for (int cnt = 0; cnt < nrActiveList.size(); cnt++) {
                                        Vector tdsActiveVect = (Vector) nrActiveList.get(cnt);
                                        String activeAcNo = tdsActiveVect.get(0).toString();
                                        float activeVchNo = Float.parseFloat(tdsActiveVect.get(1).toString());
                                        String activeActIntOpt = tdsActiveVect.get(2).toString();
                                        String frDtPost = tdsActiveVect.get(3).toString();
                                        String toDtPost = tdsActiveVect.get(4).toString();
                                        double unRecoveredTdsOnActiveAc = Double.parseDouble(tdsActiveVect.get(5).toString());
//                                    double unRecoveredTdsOnActiveAc = autoTermDepositFacade.getClActFinYearTds(activeAcNo, finStartDt, toDt, "NR", activeVchNo);
//                                    tds = tds + unRecoveredTdsOnactiveAc;
                                        if (unRecoveredTdsOnActiveAc <= remainingInt) {
                                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsno + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                    + " where acno = '" + activeAcNo + "' and VoucherNo = '" + activeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                            int result = updateTdsQuery.executeUpdate();
                                            if (result < 0) {
                                                throw new ApplicationException("Error in updating tdshistory for tds ");
                                            }
                                            int TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                    + "VALUES('" + activeAcNo + "'," + activeVchNo + "," + unRecoveredTdsOnActiveAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                            if (TDSHistory <= 0) {
                                                throw new ApplicationException("Data Not Saved For " + acno);
                                            }
                                            remainingInt = remainingInt - unRecoveredTdsOnActiveAc;
                                            tdsAmt = tdsAmt + unRecoveredTdsOnActiveAc;
                                            totalTds = totalTds + unRecoveredTdsOnActiveAc;
                                        } else {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        recno = ftsMethods.getRecNo();
//                        recno = recno + 1;
                        tdRecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,dramt,voucherno,intflag,ty,trantype,details,"
                                + "auth,authby,enterby,recno,trsno,payby,trandesc,org_brnid,dest_brnid)values ('" + acno + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                                + tdsAmt + "," + vchNo + ",'T',1,2,'Tds Decucted for " + fromDt + " To " + toDt + closeAcFlag + "','Y','System','"
                                + enterBy + "'," + recno + "," + trsno + ",3,33,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                        if (tdRecon <= 0) {
                            throw new ApplicationException("Error in tdRecon Insertion for TDS");
                        }
                    }
                }
                if (tdsAmt == 0 && excessTdsShifted > 0) {
                    Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered,trsno,tdsRecoveredDt,recoveredVch, closeAcTds, excessTdsShifted)"
                            + "VALUES('" + acno + "'," + vchNo + "," + tdsAmt + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "','R'," + trsno + ", date_format(now(),'%Y%m%d')," + vchNo + "," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                    if (tdsHisResult <= 0) {
                        throw new ApplicationException("Data Not Saved For " + acno);
                    }
                }
//                    }
//                }
                /**
                 * ***END of TDS Deduction from account **************
                 */
                Integer interestHistory = em.createNativeQuery("insert into td_interesthistory(acno,voucherno,interest,fromdt,"
                        + "todt,intopt,dt) values('" + acno + "'," + vchNo + "," + interest + ",'" + fromDt + "','" + toDt + "','"
                        + intopt + "'," + "date_format(now(),'%Y%m%d'))").executeUpdate();
                if (interestHistory <= 0) {
                    return "Error in TD_InterestHistory Insertion";
                }
                /* Updation For CumuPrinAmt For All Accounts */
                double tmpInt = interest - tdsAmt;
                Query updateQuery = em.createNativeQuery("Update td_vouchmst Set  CumuPrinAmt=ifnull(CumuPrinAmt,0)+ifnull(" + tmpInt + ",0)"
                        + " where acno = '" + acno + "' and voucherno = " + vchNo + " and IntOpt='" + intopt + "' and Status='A' and  CumuPrinAmt is not null");
                int result = updateQuery.executeUpdate();
                if (result < 0) {
                    return "Error in updating principal amount";
                }
            }

            /**
             * *************Total TDS posting in GL Recon**************
             */
//            if (tdsPostEnable.equalsIgnoreCase("Y")) {
            List chk3 = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab "
                    + "where TDS_Applicabledate<='" + todate + "')").getResultList();
            if (!chk3.isEmpty()) {
                Vector chk3V = (Vector) chk3.get(0);
                glAccNo = orgnBrCode + chk3V.get(0).toString();
            }

            if (totalTds > 0) {
                Integer reconBalan = em.createNativeQuery("UPDATE reconbalan SET BALANCE=BALANCE+" + totalTds + ",DT=date_format(now(),'%Y%m%d')  WHERE ACNO='" + glAccNo + "'").executeUpdate();
                if (reconBalan <= 0) {
                    return "Error in GL Balance Updation !!!";
                }

                recno = ftsMethods.getRecNo();
//                recno = recno + 1;
                Integer glrecon = em.createNativeQuery("INSERT INTO gl_recon (ACNO,Dt,ValueDt,CrAmt,Ty,TranType,Details,EnterBy,"
                        + "Auth,AuthBy,TranTime,RecNo,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) VALUES('" + glAccNo + "',date_format(now(),'%Y%m%d'),"
                        + "date_format(now(),'%Y%m%d')," + totalTds + ",0,2, CONCAT('TDS POSTING TILL DT ' , date_format('" + todate + "','%Y%m%d')),'"
                        + enterBy + "','Y','System',now(),'" + recno + "','" + trsno + "',3,33,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                if (glrecon <= 0) {
                    return "Error in TDS Posting in GL !!!";
                }
            }
//            }
            /**
             * *************END of Total TDS posting in GL Recon**************
             */
            /* Updation For NextIntPayDt For All Accounts */
            Query updateQuery1 = em.createNativeQuery("update td_vouchmst tv inner join td_accountmaster tm on tv.acno = tm.acno "
                    + "set nextIntPayDt='" + CbsUtil.dateAdd(todate, 1) + "' where tm.accttype = '" + acctype + "' and tm.curbrcode = '" + orgnBrCode
                    + "' and intopt='" + intopt + "' and Status='A'  and nextintpaydt<='" + todate + "' and  CumuPrinAmt is not null");
            int result = updateQuery1.executeUpdate();
            if (result < 0) {
                return "Error in updating Next Interest Payable Date";
            }

            /* Updation For IntFlag */
            Query insertQuery1 = em.createNativeQuery("Insert into td_intflag (TD_tilldate,TD_sysdate,intOpt,acType,brncode)"
                    + " values('" + todate + "',date_format(now(),'%Y%m%d'),'" + intopt + "','" + acctype + "','" + orgnBrCode + "')");
            result = insertQuery1.executeUpdate();
            if (result < 0) {
                return "Error in updating Interest flag";
            }
            return "Yes";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
//            e.printStackTrace();
//            throw new ApplicationException(e);
        }
    }

//    public String tdInterestPosting(List<TdIntDetail> tdIntDetailsList, String toDt, String date, String acctType, String tmpAcno,
//            String intopt, String user, String brCode) throws ApplicationException {
//        try {
//            String msg = "true";
//            String glAccNo = "";
//            if (tdIntDetailsList.isEmpty()) {
//                return "Transaction does not exist.";
//            }
//            List chk3 = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab "
//                    + "where TDS_Applicabledate<='" + toDt + "')").getResultList();
//            if (!chk3.isEmpty()) {
//                Vector chk3V = (Vector) chk3.get(0);
//                glAccNo = brCode + chk3V.get(0).toString();
//            }
//
//            String maxTillDate = "";
//            List intFlag = em.createNativeQuery("Select ifnull(date_format(max(TD_TillDate),'%Y%m%d'),'') From td_intflag Where IntOpt='" + intopt + "' and "
//                    + "actype= '" + acctType + "' and brnCode = '" + brCode + "'").getResultList();
//            if (!intFlag.isEmpty()) {
//                Vector intFlagVect = (Vector) intFlag.get(0);
//                maxTillDate = intFlagVect.get(0).toString();
//            }
//            //check this 
//            long dateDiff = CbsUtil.dayDiff(ymd.parse(maxTillDate), ymd.parse(toDt));
//            if (dateDiff < 0) {
//                msg = "Interest already posted.";
//                return msg;
//            }
//
//            String chkProvApp = "";
//            List chkProv = em.createNativeQuery("Select ifnull(ProvAppOn,'') From accounttypemaster Where AcctCode = '" + acctType + "'").getResultList();
//            if (!chkProv.isEmpty()) {
//                Vector chk3V = (Vector) chkProv.get(0);
//                chkProvApp = chk3V.get(0).toString();
//            }
//
//            if (intopt.equalsIgnoreCase("C")) {
//                if (!chkProvApp.equalsIgnoreCase("") && (chkProvApp.toUpperCase().contains(intopt.toUpperCase()))) {
//                    msg = tdIntPostProv(tdIntDetailsList, acctType, intopt, user, tmpAcno, toDt, brCode);
//                    if (!msg.equalsIgnoreCase("Yes")) {
//                        return msg;
//                    }
//                } else {
//                    msg = tdIntPost(tdIntDetailsList, acctType, intopt, user, tmpAcno, toDt, brCode);
//                    if (!msg.equalsIgnoreCase("Yes")) {
//                        return msg;
//                    }
//                }
//            } else {
//                if (!chkProvApp.equalsIgnoreCase("") && chkProvApp.toUpperCase().contains(intopt.toUpperCase()) && intopt.equalsIgnoreCase("Q")) {
//                    msg = tdIntPostProv(tdIntDetailsList, acctType, intopt, user, tmpAcno, toDt, brCode);
//                    if (!msg.equalsIgnoreCase("Yes")) {
//                        return msg;
//                    }
//                } else {
//                    msg = tdMQIntPost(tdIntDetailsList, acctType, intopt, user, tmpAcno, toDt, glAccNo, brCode);
//                    if (!msg.equalsIgnoreCase("Yes")) {
//                        return msg;
//                    }
//                }
//            }
//            return "Yes";
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//    }
    public Map<String, Object> tdInterestPosting(List<TdIntDetail> tdIntDetailsList, String toDt, String date, String acctType, String tmpAcno,
            String intopt, String user, String brCode) throws ApplicationException {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            List<TdInterestSmsTo> tdIntList = new ArrayList<TdInterestSmsTo>();
            List<TdInterestSmsTo> tdIntSmslist = new ArrayList<TdInterestSmsTo>();

            String msg = "true";
            String glAccNo = "";
            if (tdIntDetailsList.isEmpty()) {
                map.put("msg", "Transaction does not exist.");
                map.put("list", tdIntList);
                return map;
            }
            List chk3 = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab "
                    + "where TDS_Applicabledate<='" + toDt + "')").getResultList();
            if (!chk3.isEmpty()) {
                Vector chk3V = (Vector) chk3.get(0);
                glAccNo = brCode + chk3V.get(0).toString();
            }

            String maxTillDate = "";
            List intFlag = em.createNativeQuery("Select ifnull(date_format(max(TD_TillDate),'%Y%m%d'),'') From td_intflag Where IntOpt='" + intopt + "' and "
                    + "actype= '" + acctType + "' and brnCode = '" + brCode + "'").getResultList();
            if (!intFlag.isEmpty()) {
                Vector intFlagVect = (Vector) intFlag.get(0);
                maxTillDate = intFlagVect.get(0).toString();
            }
            //check this 
            long dateDiff = CbsUtil.dayDiff(ymd.parse(maxTillDate), ymd.parse(toDt));
            if (dateDiff < 0) {
                map.put("msg", "Interest already posted.");
                map.put("list", tdIntList);
                return map;
            }

            String chkProvApp = "";
            List chkProv = em.createNativeQuery("Select ifnull(ProvAppOn,'') From accounttypemaster Where AcctCode = '" + acctType + "'").getResultList();
            if (!chkProv.isEmpty()) {
                Vector chk3V = (Vector) chkProv.get(0);
                chkProvApp = chk3V.get(0).toString();
            }

            if (intopt.equalsIgnoreCase("C")) {
                if (!chkProvApp.equalsIgnoreCase("") && (chkProvApp.toUpperCase().contains(intopt.toUpperCase()))) {
                    msg = tdIntPostProv(tdIntDetailsList, acctType, intopt, user, tmpAcno, toDt, brCode);
                    if (!msg.equalsIgnoreCase("Yes")) {
                        map.put("msg", msg);
                        map.put("list", tdIntList);
                        return map;
                    }
                } else {
                    msg = tdIntPost(tdIntDetailsList, acctType, intopt, user, tmpAcno, toDt, brCode);
                    if (!msg.equalsIgnoreCase("Yes")) {
                        map.put("msg", msg);
                        map.put("list", tdIntList);
                        return map;
                    }
                }
            } else {
                if (!chkProvApp.equalsIgnoreCase("") && chkProvApp.toUpperCase().contains(intopt.toUpperCase()) && intopt.equalsIgnoreCase("Q")) {
                    msg = tdIntPostProv(tdIntDetailsList, acctType, intopt, user, tmpAcno, toDt, brCode);
                    if (!msg.equalsIgnoreCase("Yes")) {
                        map.put("msg", msg);
                        map.put("list", tdIntList);
                        return map;
                    }
                } else {
                    Map<String, Object> mapObj = tdMQIntPost(tdIntDetailsList, acctType, intopt, user, tmpAcno, toDt, glAccNo, brCode);
                    msg = mapObj.get("msg").toString();
                    tdIntSmslist = (List<TdInterestSmsTo>) mapObj.get("list");
                    if (!msg.equalsIgnoreCase("Yes")) {
                        map.put("msg", msg);
                        map.put("list", tdIntList);
                        return map;
                    }
                }
            }
            map.put("msg", "Yes");
            if (tdIntSmslist.isEmpty()) {
                map.put("list", tdIntList);
            } else {
                map.put("list", tdIntSmslist);
            }
            return map;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public Map<String, Object> tdInterestPostingWithTds(List<TdIntDetail> tdIntDetailsList, String toDt, String fromDt, String acctType, String tmpAcno,
            String intopt, String user, String brCode) throws ApplicationException {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            List<TdInterestSmsTo> tdIntList = new ArrayList<TdInterestSmsTo>();
            List<TdInterestSmsTo> tdIntSmslist = new ArrayList<TdInterestSmsTo>();

            String msg = "true";
            String glAccNo = "";
            if (tdIntDetailsList.isEmpty()) {
                map.put("msg", "Transaction does not exist.");
                map.put("list", tdIntList);
                return map;
            }
            List chk3 = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab "
                    + "where TDS_Applicabledate<='" + toDt + "')").getResultList();
            if (!chk3.isEmpty()) {
                Vector chk3V = (Vector) chk3.get(0);
                glAccNo = brCode + chk3V.get(0).toString();
            }

            String maxTillDate = "";
            List intFlag = em.createNativeQuery("Select ifnull(date_format(max(TD_TillDate),'%Y%m%d'),'') From td_intflag Where IntOpt='" + intopt + "' and "
                    + "actype= '" + acctType + "' and brnCode = '" + brCode + "'").getResultList();
            if (!intFlag.isEmpty()) {
                Vector intFlagVect = (Vector) intFlag.get(0);
                maxTillDate = intFlagVect.get(0).toString();
            }
            //check this 
            long dateDiff = CbsUtil.dayDiff(ymd.parse(maxTillDate), ymd.parse(toDt));
            if (dateDiff < 0) {
                map.put("msg", "Interest already posted.");
                map.put("list", tdIntList);
                return map;
            }

            String chkProvApp = "";
            List chkProv = em.createNativeQuery("Select ifnull(ProvAppOn,'') From accounttypemaster Where AcctCode = '" + acctType + "'").getResultList();
            if (!chkProv.isEmpty()) {
                Vector chk3V = (Vector) chkProv.get(0);
                chkProvApp = chk3V.get(0).toString();
            }

            if (intopt.equalsIgnoreCase("C")) {
                if (!chkProvApp.equalsIgnoreCase("") && (chkProvApp.toUpperCase().contains(intopt.toUpperCase()))) {
                    msg = tdIntPostProv(tdIntDetailsList, acctType, intopt, user, tmpAcno, toDt, brCode);
                    if (!msg.equalsIgnoreCase("Yes")) {
                        map.put("msg", msg);
                        map.put("list", tdIntList);
                        return map;
                    }
                } else {
                    msg = tdIntPostWithTds(tdIntDetailsList, acctType, intopt, user, tmpAcno, toDt, brCode);
                    if (!msg.equalsIgnoreCase("Yes")) {
                        map.put("msg", msg);
                        map.put("list", tdIntList);
                        return map;
                    }
                }
            } else {
                if (!chkProvApp.equalsIgnoreCase("") && chkProvApp.toUpperCase().contains(intopt.toUpperCase()) && intopt.equalsIgnoreCase("Q")) {
                    msg = tdIntPostProv(tdIntDetailsList, acctType, intopt, user, tmpAcno, toDt, brCode);
                    if (!msg.equalsIgnoreCase("Yes")) {
                        map.put("msg", msg);
                        map.put("list", tdIntList);
                        return map;
                    }
                } else {
                    Map<String, Object> mapObj = tdMQIntPostWithTds(tdIntDetailsList, acctType, intopt, user, tmpAcno, toDt, glAccNo, brCode);
                    msg = mapObj.get("msg").toString();
                    tdIntSmslist = (List<TdInterestSmsTo>) mapObj.get("list");
                    if (!msg.equalsIgnoreCase("Yes")) {
                        map.put("msg", msg);
                        map.put("list", tdIntList);
                        return map;
                    }
                }
            }
            map.put("msg", "Yes");
            if (tdIntSmslist.isEmpty()) {
                map.put("list", tdIntList);
            } else {
                map.put("list", tdIntSmslist);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public String rdInterestPostingWithTds(List<TdIntDetail> tdIntDetailsList, String acType, String AuthBy, String fromdt, String todt, String brcode, String intOption) throws ApplicationException {
        try {
            float trsNo = 0.0f;

            int trantype;
            double totalTds = 0d;

            String finStartDt = rbiReportFacade.getMinFinYear(todt);
            List chkList1 = em.createNativeQuery("select rd_simplepostflag from td_parameterinfo").getResultList();
            if (chkList1.isEmpty()) {
                throw new ApplicationException("Please Check for SimplePostFlag In td_parameterinfo.");
            }
            Vector Lst1 = (Vector) chkList1.get(0);
            int intPostFlag = Integer.parseInt(Lst1.get(0).toString());

            List chkList2 = em.createNativeQuery("select GlHeadInt,GlHeadProv from accounttypemaster where acctcode='" + acType + "'").getResultList();
            if (chkList2.isEmpty()) {
                throw new ApplicationException("Please Check for GlHeadInt or GlHeadProv In accounttypemaster for RD.");
            }

            Vector list2 = (Vector) chkList2.get(0);
            String glHeadInt = list2.get(0).toString();
            String glHeadProv = list2.get(1).toString();

            if ((glHeadInt == null) || (glHeadProv == null) || (glHeadInt.equalsIgnoreCase("")) || (glHeadProv.equalsIgnoreCase(""))) {
                throw new ApplicationException("Please Check for GlHeadInt or GlHeadProv In accounttypemaster for RD");
            }
            glHeadInt = brcode + glHeadInt + "01";
            glHeadProv = brcode + glHeadProv + "01";

            trsNo = ftsMethods.getTrsNo();
//            List<RdInterestDTO> rdIntDetails = interestCalculate(acType, fromdt, todt, brcode);
            double totalInt = 0d;
            for (TdIntDetail TdIntList : tdIntDetailsList) {
                totalInt = totalInt + TdIntList.getInterest();
                Query InsertQuery = em.createNativeQuery("insert into rd_interesthistory(acno,Interest,Todt,dt,Enterby) values('"
                        + TdIntList.getAcno() + "'," + TdIntList.getInterest() + ",'" + todt + "',(DATE_FORMAT(CURDATE(),'%Y%m%d')),'" + AuthBy + "')");
                int var = InsertQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Problem in data insertion into Rd_InterestHistory Table");
                }
            }
            float recNo = ftsMethods.getRecNo();
            String details = "Interest from " + fromdt + " to " + todt;
            if ((intPostFlag == 0) || (intPostFlag == 1) || (intPostFlag == 2)) {
                if ((intPostFlag == 0) || (intPostFlag == 2)) {
                    trantype = 2;
                } else {
                    trantype = 8;
                }
                recNo = ftsMethods.getRecNo();
//                recNo = recNo + 1;
                Query insertQuery2 = em.createNativeQuery("insert into gl_recon(acno,dt,ValueDt,Dramt,ty,trantype,PayBy,iy,details,TranDesc,"
                        + "auth,authby,enterby,recno,trsno,org_brnid,dest_brnid)"
                        + "values ('" + glHeadInt + "',DATE_FORMAT(CURDATE(),'%Y%m%d'),DATE_FORMAT(CURDATE(),'%Y%m%d')," + totalInt + ",1,"
                        + trantype + ",3,1,'" + details + "',3,'Y','" + AuthBy + "','" + AuthBy + "'," + recNo + "," + trsNo + ",'" + brcode + "','"
                        + brcode + "')");
                int var2 = insertQuery2.executeUpdate();
                if (var2 <= 0) {
                    throw new ApplicationException("Problem in data insertion in Gl_Recon for AcNo:- '" + glHeadInt + "'");
                }

                List chkList4 = em.createNativeQuery("Select Acno from reconbalan where acno='" + glHeadInt + "' and substring(acno,1,2) = '" + brcode + "'").getResultList();
                if (chkList4.size() > 0) {
                    Query updateQuery4 = em.createNativeQuery("update reconbalan Set balance=ifnull(balance,0)-" + totalInt
                            + ",dt=DATE_FORMAT(CURDATE(),'%Y%m%d') where acno='" + glHeadInt + "' and substring(acno,1,2) = '" + brcode + "'");
                    int var3 = updateQuery4.executeUpdate();
                    if (var3 <= 0) {
                        throw new ApplicationException("Problem in data updation Recon Balan in for AcNo:- '" + glHeadInt + "'");
                    }
                } else {
                    Query insertQuery3 = em.createNativeQuery("Insert reconbalan (Acno,Dt,balance) values ('" + glHeadInt
                            + "',DATE_FORMAT(CURDATE(),'%Y%m%d')" + "," + (-totalInt) + ")");
                    int var4 = insertQuery3.executeUpdate();
                    if (var4 <= 0) {
                        throw new ApplicationException("Problem in data insertion Recon Balan in for AcNo:- '" + glHeadInt + "'");
                    }
                }
            }

            if ((intPostFlag == 0) || (intPostFlag == 2)) {
                recNo = ftsMethods.getRecNo();
//                recNo = recNo + 1;
                Query insertQuery2 = em.createNativeQuery("insert into gl_recon(acno,dt,ValueDt,Cramt,ty,trantype,PayBy,iy,details,TranDesc,auth,authby,"
                        + "enterby,recno,trsno,org_brnid,dest_brnid) values ('" + glHeadProv + "',DATE_FORMAT(CURDATE(),'%Y%m%d'),"
                        + "DATE_FORMAT(CURDATE(),'%Y%m%d')," + totalInt + ",0,2,3,1,'" + details + "',3,'Y','" + AuthBy + "','" + AuthBy + "'," + recNo
                        + "," + trsNo + ",'" + brcode + "','" + brcode + "')");
                int var2 = insertQuery2.executeUpdate();
                if (var2 <= 0) {
                    throw new ApplicationException("Problem In Insertion In gl_recon for AcNo:- '" + glHeadProv + "'");
                }
                List chkList4 = em.createNativeQuery("Select Acno from reconbalan where acno='" + glHeadProv + "' and substring(acno,1,2) = '" + brcode + "'").getResultList();
                if (chkList4.size() > 0) {
                    Query updateQuery4 = em.createNativeQuery("update reconbalan Set balance=ifnull(balance,0)+" + totalInt + ",dt=DATE_FORMAT(CURDATE(),'%Y%m%d') where acno='" + glHeadProv + "' and substring(acno,1,2) = '" + brcode + "'");
                    int var3 = updateQuery4.executeUpdate();
                    if (var3 <= 0) {
                        throw new ApplicationException("Problem in data updation Recon Balan in for AcNo:- '" + glHeadInt + "'");
                    }
                } else {
                    Query insertQuery3 = em.createNativeQuery("Insert reconbalan (Acno,Dt,balance)"
                            + "values (" + "'" + glHeadProv + "'" + "," + "DATE_FORMAT(CURDATE(),'%Y%m%d')" + "," + totalInt + ")");
                    int var4 = insertQuery3.executeUpdate();
                    if (var4 <= 0) {
                        throw new ApplicationException("Problem in data insertion Recon Balan in for AcNo:- '" + glHeadInt + "'");
                    }
                }
            }

            if ((intPostFlag == 1) || (intPostFlag == 2)) {
                for (TdIntDetail TdIntList : tdIntDetailsList) {
                    recNo = ftsMethods.getRecNo();
//                    recNo = recNo + 1;
                    double unRecoverTds = TdIntList.getUnRecoverTds();
                    double closeAcInt = TdIntList.getCloseAcIntPaid();
                    double tdsPer = TdIntList.getTdsPer();
                    double excessTdsShifted = TdIntList.getTdsExtraShift();
                    double closeAcTds = 0d;
                    Query insertQuery2 = em.createNativeQuery("insert into rdrecon(acno,dt,ValueDt,cramt,ty,trantype,PayBy,iy,details,TranDesc,auth,authby,enterby,recno,trsno,org_brnid,dest_brnid)"
                            + "values ('" + TdIntList.getAcno() + "',DATE_FORMAT(CURDATE(),'%Y%m%d'),DATE_FORMAT(CURDATE(),'%Y%m%d'),"
                            + TdIntList.getInterest() + ",0,8,3,1,'" + details + "',3,'Y','" + AuthBy + "','" + AuthBy + "'," + recNo + "," + trsNo
                            + ",'" + brcode + "','" + brcode + "')");
                    int var = insertQuery2.executeUpdate();
                    if (var <= 0) {
                        throw new ApplicationException("Problem In Insertion In rdrecon of Cr Type for Ac No:- '" + TdIntList.getAcno() + "'");
                    }
                    /**
                     * ************ TDS Deduction from account **************
                     */
                    double tdsAmt = TdIntList.getTds();
                    String closeAcFlag = "";
                    if (closeAcInt > 0 && tdsAmt > 0) {
                        closeAcFlag = " includes closed accounts TDS";
                        closeAcTds = Math.round((closeAcInt * tdsPer) / 100);
                    }
                    //if (tdsPostEnable.equalsIgnoreCase("Y")) {
                    if (tdsAmt > 0 && tdsAmt <= TdIntList.getInterest()) {
                        totalTds = totalTds + tdsAmt;
//                        List tdsCloseList = em.createNativeQuery(" select TDS, TXNID from tds_reserve_history a, accountmaster b, customerid c "
//                                + " where a.acno = b.acno and b.ACNo = c.Acno "
//                                + " and a.recovered ='NR' and b.accstatus ='9' and substring(a.acno,3,2) ='" + acType + "' and c.CustId = '" + TdIntList.getCustId() + "' order by txnid").getResultList();
//                        for (int cnt = 0; cnt < tdsCloseList.size(); cnt++) {
//                            Vector tdsCloseVect = (Vector) tdsCloseList.get(cnt);
//                            double cltds = Double.parseDouble(tdsCloseVect.get(0).toString());
//                            Integer txnId = Integer.parseInt(tdsCloseVect.get(1).toString());
//                            tdsAmt = tdsAmt + cltds;
//                            if (tdsAmt <= TdIntList.getInterest()) {
//                                totalTds = totalTds + cltds;
//                                recNo = ftsMethods.getRecNo();
//                                Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', trsno = " + trsNo + ", recno = " + recNo + ", recoveredVch = " + trsNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
//                                        + " where acno = '" + TdIntList.getAcno() + "' and TXNID = '" + txnId + "' and recovered ='NR' ");
//                                int result = updateTdsQuery.executeUpdate();
//                                if (result < 0) {
//                                    totalTds = totalTds - cltds;
//                                    return "Error in updating tdshistory for tds ";
//                                }
//                            } else {
//                                tdsAmt = tdsAmt - cltds;
//                                break;
//                            }
//                        }

                        Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered,trsno,tdsRecoveredDt,closeAcTds, excessTdsShifted)"
                                + "VALUES('" + TdIntList.getAcno() + "','0'," + tdsAmt + ",date_format(now(),'%Y%m%d'),'" + todt + "','" + fromdt + "','" + intOption + "','R'," + trsNo + ",date_format(now(),'%Y%m%d')," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                        if (tdsHisResult <= 0) {
                            throw new ApplicationException("Data Not Saved For " + TdIntList.getAcno());
                        }

                        Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                + "VALUES('" + TdIntList.getAcno() + "'," + 0 + "," + tdsAmt + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + todt + "','"
                                + fromdt + "','" + intOption + "')").executeUpdate();
                        if (TDSHistory <= 0) {
                            throw new ApplicationException("Data Not Saved For " + TdIntList.getAcno());
                        }
                        double remainingInt = TdIntList.getInterest() - tdsAmt;
                        /*
                         *Getting TDS Deduction from Close account which are not recovered
                         */
                        if (unRecoverTds > 0 && remainingInt > 0) {
                            double unRecCloseAc = autoTermDepositFacade.getCustomerFinYearTds(TdIntList.getAcno(), finStartDt, todt, "NR", "Y");//Close Account TDS which was UN-RECOVERED
                            List nrCloseList = autoTermDepositFacade.getUnRecoverdTdsAccounts(TdIntList.getAcno(), finStartDt, todt, "NR", "Y");
                            if (!nrCloseList.isEmpty()) {
//                                remainingInt = closeAcTdsRecovery(nrCloseList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                for (int cnt = 0; cnt < nrCloseList.size(); cnt++) {
                                    Vector tdsCloseVect = (Vector) nrCloseList.get(cnt);
                                    String closeAcNo = tdsCloseVect.get(0).toString();
                                    float closeVchNo = Float.parseFloat(tdsCloseVect.get(1).toString());
                                    String clActIntOpt = tdsCloseVect.get(2).toString();
                                    String frDtPost = tdsCloseVect.get(3).toString();
                                    String toDtPost = tdsCloseVect.get(4).toString();
                                    double unRecoveredTdsOnCloseAc = Double.parseDouble(tdsCloseVect.get(5).toString());
//                                    double unRecoveredTdsOnCloseAc = autoTermDepositFacade.getClActFinYearTds(closeAcNo, finStartDt, toDt, "NR", closeVchNo);
//                                    tds = tds + unRecoveredTdsOnCloseAc;
                                    if (unRecoveredTdsOnCloseAc <= remainingInt) {
                                        Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                + " where acno = '" + closeAcNo + "' and VoucherNo = '" + closeVchNo + "' and recovered ='NR' and todt<='" + todt + "' and intOpt = '" + intOption + "'");
                                        int result = updateTdsQuery.executeUpdate();
                                        if (result < 0) {
                                            throw new ApplicationException("Error in updating tdshistory for tds ");
                                        }
                                        TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + unRecoveredTdsOnCloseAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intOption + "')").executeUpdate();
                                        if (TDSHistory <= 0) {
                                            throw new ApplicationException("Data Not Saved For " + TdIntList.getAcno());
                                        }
                                        remainingInt = remainingInt - unRecoveredTdsOnCloseAc;
                                        tdsAmt = tdsAmt + unRecoveredTdsOnCloseAc;
                                        totalTds = totalTds + unRecoveredTdsOnCloseAc;
                                    } else {
                                        break;
                                    }
                                }
                            }

                            /*
                             *Getting TDS Deduction from Running account which are not recovered
                             */
                            if (remainingInt > 0) {
                                double unRecRunningAc = autoTermDepositFacade.getCustomerFinYearTds(TdIntList.getAcno(), finStartDt, todt, "NR", "N");//Running Account TDS which was UN-RECOVERED
                                List nrActiveList = autoTermDepositFacade.getUnRecoverdTdsAccounts(TdIntList.getAcno(), finStartDt, todt, "NR", "N");
                                if (!nrActiveList.isEmpty()) {
//                                    remainingInt = activeAcTdsRecovery(nrActiveList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                    for (int cnt = 0; cnt < nrActiveList.size(); cnt++) {
                                        Vector tdsActiveVect = (Vector) nrActiveList.get(cnt);
                                        String activeAcNo = tdsActiveVect.get(0).toString();
                                        float activeVchNo = Float.parseFloat(tdsActiveVect.get(1).toString());
                                        String activeActIntOpt = tdsActiveVect.get(2).toString();
                                        String frDtPost = tdsActiveVect.get(3).toString();
                                        String toDtPost = tdsActiveVect.get(4).toString();
                                        double unRecoveredTdsOnActiveAc = Double.parseDouble(tdsActiveVect.get(5).toString());
//                                    double unRecoveredTdsOnActiveAc = autoTermDepositFacade.getClActFinYearTds(activeAcNo, finStartDt, toDt, "NR", activeVchNo);
//                                    tds = tds + unRecoveredTdsOnactiveAc;
                                        if (unRecoveredTdsOnActiveAc <= remainingInt) {
                                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                    + " where acno = '" + activeAcNo + "' and VoucherNo = '" + activeVchNo + "' and recovered ='NR' and todt<='" + todt + "' and intOpt = '" + intOption + "'");
                                            int result = updateTdsQuery.executeUpdate();
                                            if (result < 0) {
                                                throw new ApplicationException("Error in updating tdshistory for tds ");
                                            }
                                            TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                    + "VALUES('" + activeAcNo + "'," + activeVchNo + "," + unRecoveredTdsOnActiveAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intOption + "')").executeUpdate();
                                            if (TDSHistory <= 0) {
                                                throw new ApplicationException("Data Not Saved For " + TdIntList.getAcno());
                                            }
                                            remainingInt = remainingInt - unRecoveredTdsOnActiveAc;
                                            tdsAmt = tdsAmt + unRecoveredTdsOnActiveAc;
                                            totalTds = totalTds + unRecoveredTdsOnActiveAc;
                                        } else {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        recNo = ftsMethods.getRecNo();
//                        recNo = recNo + 1;
                        int tdRecon = em.createNativeQuery("insert into rdrecon(acno,dt,ValueDt,dramt,ty,trantype,PayBy,iy,details,TranDesc,auth,authby,enterby,recno,trsno,org_brnid,dest_brnid)"
                                + "values ('" + TdIntList.getAcno() + "',DATE_FORMAT(CURDATE(),'%Y%m%d'),DATE_FORMAT(CURDATE(),'%Y%m%d'),"
                                + tdsAmt + ",1,2,3,1,'Tds Decucted for " + fromdt + "To " + todt + "',33,'Y','SYSTEM','" + AuthBy + "'," + recNo + "," + trsNo
                                + ",'" + brcode + "','" + brcode + "')").executeUpdate();
                        if (tdRecon <= 0) {
                            return "Error in tdRecon Insertion for TDS";
                        }
                    } else {
//                        if (tdsAmt > 0) {
//                            Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered,trsno)"
//                                    + "VALUES('" + TdIntList.getAcno() + "','0'," + tdsAmt + ",date_format(now(),'%Y%m%d'),'" + todt + "','" + fromdt + "','" + intOption + "','NR'," + trsNo + ")").executeUpdate();
//                            if (tdsHisResult <= 0) {
//                                throw new ApplicationException("Data Not Saved For " + TdIntList.getAcno());
//                            }
//                        }
                        if (tdsAmt > 0) {
                            double unRecTds = tdsAmt - TdIntList.getInterest();
                            tdsAmt = TdIntList.getInterest();
                            totalTds = totalTds + tdsAmt;

                            recNo = ftsMethods.getRecNo();
//                            recNo = recNo + 1;
                            int tdRecon = em.createNativeQuery("insert into rdrecon(acno,dt,ValueDt,dramt,ty,trantype,PayBy,iy,details,TranDesc,auth,authby,enterby,recno,trsno,org_brnid,dest_brnid)"
                                    + "values ('" + TdIntList.getAcno() + "',DATE_FORMAT(CURDATE(),'%Y%m%d'),DATE_FORMAT(CURDATE(),'%Y%m%d'),"
                                    + tdsAmt + ",1,2,3,1,'Tds Decucted for " + fromdt + "To " + todt + " " + closeAcFlag + "',33,'Y','SYSTEM','" + AuthBy + "'," + recNo + "," + trsNo
                                    + ",'" + brcode + "','" + brcode + "')").executeUpdate();
                            if (tdRecon <= 0) {
                                return "Error in tdRecon Insertion for TDS";
                            }

                            /*Start Posting of Recovered TDS*/
                            Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered,trsno,tdsRecoveredDt,closeAcTds, excessTdsShifted )"
                                    + "VALUES('" + TdIntList.getAcno() + "','0'," + tdsAmt + ",date_format(now(),'%Y%m%d'),'" + todt + "','" + fromdt + "','" + intOption + "','R'," + trsNo + ",date_format(now(),'%Y%m%d')," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                            if (tdsHisResult <= 0) {
                                throw new ApplicationException("Data Not Saved For " + TdIntList.getAcno());
                            }

                            Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                    + "VALUES('" + TdIntList.getAcno() + "'," + 0 + "," + tdsAmt + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + todt + "','"
                                    + fromdt + "','" + intOption + "')").executeUpdate();
                            if (TDSHistory <= 0) {
                                throw new ApplicationException("Data Not Saved For " + TdIntList.getAcno());
                            }
                            double remainingInt = TdIntList.getInterest() - tdsAmt;
                            /*End Posting of Recovered TDS*/

                            /*Start Posting of UnRecovered TDS*/
                            tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered,trsno)"
                                    + "VALUES('" + TdIntList.getAcno() + "','0'," + unRecTds + ",date_format(now(),'%Y%m%d'),'" + todt + "','" + fromdt + "','" + intOption + "','NR'," + trsNo + ")").executeUpdate();
                            if (tdsHisResult <= 0) {
                                throw new ApplicationException("Data Not Saved For " + TdIntList.getAcno());
                            }
                            /*End Posting of UnRecovered TDS*/
//                            if ((intopt.equalsIgnoreCase("S") && simplePostFlag > 0)) {
//                                totalTds = totalTds - tds;
//                            }
                        } else if (tdsAmt <= 0 && unRecoverTds > 0) {
                            double remainingInt = TdIntList.getInterest();
                            tdsAmt = 0;
                            /*
                             *Getting TDS Deduction from Close account which are not recovered
                             */
                            if (unRecoverTds > 0 && remainingInt > 0) {
                                double unRecCloseAc = autoTermDepositFacade.getCustomerFinYearTds(TdIntList.getAcno(), finStartDt, todt, "NR", "Y");//Close Account TDS which was UN-RECOVERED
                                List nrCloseList = autoTermDepositFacade.getUnRecoverdTdsAccounts(TdIntList.getAcno(), finStartDt, todt, "NR", "Y");
                                if (!nrCloseList.isEmpty()) {
//                                remainingInt = closeAcTdsRecovery(nrCloseList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                    for (int cnt = 0; cnt < nrCloseList.size(); cnt++) {
                                        Vector tdsCloseVect = (Vector) nrCloseList.get(cnt);
                                        String closeAcNo = tdsCloseVect.get(0).toString();
                                        float closeVchNo = Float.parseFloat(tdsCloseVect.get(1).toString());
                                        String clActIntOpt = tdsCloseVect.get(2).toString();
                                        String frDtPost = tdsCloseVect.get(3).toString();
                                        String toDtPost = tdsCloseVect.get(4).toString();
                                        double unRecoveredTdsOnCloseAc = Double.parseDouble(tdsCloseVect.get(5).toString());
//                                    double unRecoveredTdsOnCloseAc = autoTermDepositFacade.getClActFinYearTds(closeAcNo, finStartDt, toDt, "NR", closeVchNo);
//                                    tds = tds + unRecoveredTdsOnCloseAc;
                                        if (unRecoveredTdsOnCloseAc <= remainingInt) {
                                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                    + " where acno = '" + closeAcNo + "' and VoucherNo = '" + closeVchNo + "' and recovered ='NR' and todt<='" + todt + "' and intOpt = '" + intOption + "'");
                                            int result = updateTdsQuery.executeUpdate();
                                            if (result < 0) {
                                                throw new ApplicationException("Error in updating tdshistory for tds ");
                                            }
                                            int TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                    + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + unRecoveredTdsOnCloseAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intOption + "')").executeUpdate();
                                            if (TDSHistory <= 0) {
                                                throw new ApplicationException("Data Not Saved For " + TdIntList.getAcno());
                                            }
                                            remainingInt = remainingInt - unRecoveredTdsOnCloseAc;
                                            tdsAmt = tdsAmt + unRecoveredTdsOnCloseAc;
                                            totalTds = totalTds + unRecoveredTdsOnCloseAc;
                                        } else {
                                            break;
                                        }
                                    }
                                }

                                /*
                                 *Getting TDS Deduction from Running account which are not recovered
                                 */
                                if (remainingInt > 0) {
                                    double unRecRunningAc = autoTermDepositFacade.getCustomerFinYearTds(TdIntList.getAcno(), finStartDt, todt, "NR", "N");//Running Account TDS which was UN-RECOVERED
                                    List nrActiveList = autoTermDepositFacade.getUnRecoverdTdsAccounts(TdIntList.getAcno(), finStartDt, todt, "NR", "N");
//                                    System.out.println("acno:" + TdIntList.getAcno());
                                    if (!nrActiveList.isEmpty()) {
//                                    remainingInt = activeAcTdsRecovery(nrActiveList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                        for (int cnt = 0; cnt < nrActiveList.size(); cnt++) {
                                            Vector tdsActiveVect = (Vector) nrActiveList.get(cnt);
                                            String activeAcNo = tdsActiveVect.get(0).toString();
                                            float activeVchNo = Float.parseFloat(tdsActiveVect.get(1).toString());
                                            String activeActIntOpt = tdsActiveVect.get(2).toString();
                                            String frDtPost = tdsActiveVect.get(3).toString();
                                            String toDtPost = tdsActiveVect.get(4).toString();
                                            double unRecoveredTdsOnActiveAc = Double.parseDouble(tdsActiveVect.get(5).toString());
//                                    double unRecoveredTdsOnActiveAc = autoTermDepositFacade.getClActFinYearTds(activeAcNo, finStartDt, toDt, "NR", activeVchNo);
//                                    tds = tds + unRecoveredTdsOnactiveAc;
                                            if (unRecoveredTdsOnActiveAc <= remainingInt) {
                                                Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                        + " where acno = '" + activeAcNo + "' and VoucherNo = '" + activeVchNo + "' and recovered ='NR' and todt<='" + todt + "' and intOpt = '" + intOption + "'");
                                                int result = updateTdsQuery.executeUpdate();
                                                if (result < 0) {
                                                    throw new ApplicationException("Error in updating tdshistory for tds ");
                                                }
                                                int TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                        + "VALUES('" + activeAcNo + "'," + activeVchNo + "," + unRecoveredTdsOnActiveAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intOption + "')").executeUpdate();
                                                if (TDSHistory <= 0) {
                                                    throw new ApplicationException("Data Not Saved For " + TdIntList.getAcno());
                                                }
                                                remainingInt = remainingInt - unRecoveredTdsOnActiveAc;
                                                tdsAmt = tdsAmt + unRecoveredTdsOnActiveAc;
                                                totalTds = totalTds + unRecoveredTdsOnActiveAc;
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (tdsAmt > 0) {
                                recNo = ftsMethods.getRecNo();
//                                recNo = recNo + 1;
                                int tdRecon = em.createNativeQuery("insert into rdrecon(acno,dt,ValueDt,dramt,ty,trantype,PayBy,iy,details,TranDesc,auth,authby,enterby,recno,trsno,org_brnid,dest_brnid)"
                                        + "values ('" + TdIntList.getAcno() + "',DATE_FORMAT(CURDATE(),'%Y%m%d'),DATE_FORMAT(CURDATE(),'%Y%m%d'),"
                                        + tdsAmt + ",1,2,3,1,'Tds Decucted for " + fromdt + "To " + todt + " " + closeAcFlag + "',33,'Y','SYSTEM','" + AuthBy + "'," + recNo + "," + trsNo
                                        + ",'" + brcode + "','" + brcode + "')").executeUpdate();
                                if (tdRecon <= 0) {
                                    return "Error in Recon Insertion for TDS";
                                }
                            }
                        }
                    }
                    if (tdsAmt == 0 && excessTdsShifted > 0) {
                        Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered,trsno,tdsRecoveredDt,closeAcTds, excessTdsShifted )"
                                + "VALUES('" + TdIntList.getAcno() + "','0'," + tdsAmt + ",date_format(now(),'%Y%m%d'),'" + todt + "','" + fromdt + "','" + intOption + "','R'," + trsNo + ",date_format(now(),'%Y%m%d')," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                        if (tdsHisResult <= 0) {
                            throw new ApplicationException("Data Not Saved For " + TdIntList.getAcno());
                        }
                    }
                    /**
                     * ***END of TDS Deduction from account **************
                     */
                    if (intPostFlag == 1) {
                        List chkList4 = em.createNativeQuery("Select Acno from reconbalan where acno='" + TdIntList.getAcno() + "' and substring(acno,1,2) = '" + brcode + "'").getResultList();
                        if (chkList4.size() > 0) {
                            Query updateQuery4 = em.createNativeQuery("update reconbalan Set balance=ifnull(balance,0)+" + TdIntList.getInterest() + "-" + tdsAmt
                                    + ",dt=DATE_FORMAT(CURDATE(),'%Y%m%d') where acno='" + TdIntList.getAcno() + "' and substring(acno,1,2) = '" + brcode + "'");
                            int var3 = updateQuery4.executeUpdate();
                            if (var3 <= 0) {
                                throw new ApplicationException("Problem in data updation Recon Balan in for AcNo:- '" + glHeadInt + "'");
                            }
                        } else {
                            Query insertQuery3 = em.createNativeQuery("Insert reconbalan (Acno,Dt,balance)"
                                    + "values (" + "'" + TdIntList.getAcno() + "'" + "," + "DATE_FORMAT(CURDATE(),'%Y%m%d')" + "," + TdIntList.getInterest() + "-" + tdsAmt + ")");
                            int var4 = insertQuery3.executeUpdate();
                            if (var4 <= 0) {
                                throw new ApplicationException("Problem in data insertion Recon Balan in for AcNo:- '" + glHeadInt + "'");
                            }
                        }
                    } else {
                        recNo = ftsMethods.getRecNo();
//                        recNo = recNo + 1;
                        Query insertQuery20 = em.createNativeQuery("insert into rdrecon(acno,dt,ValueDt,Dramt,ty,trantype,PayBy,iy,details,TranDesc,"
                                + "auth,authby,enterby,recno,trsno,org_brnid,dest_brnid)"
                                + "values ('" + TdIntList.getAcno() + "',DATE_FORMAT(CURDATE(),'%Y%m%d'),DATE_FORMAT(CURDATE(),'%Y%m%d')," + TdIntList.getInterest() + "-" + tdsAmt + ",1,8,3,1,'"
                                + details + "',3,'Y','" + AuthBy + "','" + AuthBy + "'," + recNo + "," + trsNo + ",'" + brcode + "','" + brcode + "')");
                        int var20 = insertQuery20.executeUpdate();
                        if (var20 <= 0) {
                            throw new ApplicationException("Problem In Insertion In rdrecon of Dr Type for Ac No:- '" + TdIntList.getAcno() + "'");
                        }
                    }
                }
                String glAccNo = null;
                /**
                 * *************Total TDS posting in GL Recon**************
                 */
                if (totalTds > 0) {
                    List chk3 = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab "
                            + "where TDS_Applicabledate<='" + todt + "')").getResultList();
                    if (!chk3.isEmpty()) {
                        Vector chk3V = (Vector) chk3.get(0);
                        glAccNo = brcode + chk3V.get(0).toString();
                    }
                    Integer reconBalan = em.createNativeQuery("UPDATE reconbalan SET BALANCE=BALANCE+" + totalTds + ",DT=date_format(now(),'%Y%m%d')  WHERE ACNO='" + glAccNo + "'").executeUpdate();
                    if (reconBalan <= 0) {
                        return "Error in GL Balance Updation !!!";
                    }
                    if (totalTds > 0) {
                        recNo = ftsMethods.getRecNo();
//                        recNo = recNo + 1;
                        Integer glrecon = em.createNativeQuery("INSERT INTO gl_recon (ACNO,Dt,ValueDt,CrAmt,Ty,TranType,Details,EnterBy,"
                                + "Auth,AuthBy,TranTime,RecNo,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) VALUES('" + glAccNo + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                                + "" + totalTds + ",0,2, CONCAT('TDS POSTING TILL DT ' , date_format('" + todt + "','%Y%m%d')),'" + AuthBy + "','Y','" + AuthBy + "',now(),'" + recNo + "',"
                                + "'" + trsNo + "',3,33,1,'" + brcode + "','" + brcode + "')").executeUpdate();
                        if (glrecon <= 0) {
                            return "Error in TDS Posting in GL !!!";
                        }
                    }
                }
            }
            return "true" + Float.toString(trsNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public String ddsInterestPostingWithTds(List<TdIntDetail> list, String type, String fromDate, String toDate, String brncode, String userName) throws ApplicationException {
        String todayDate = "";

        List query = em.createNativeQuery("Select glheadint,glheadprov from accounttypemaster where acctcode='" + type + "'").getResultList();
        if (query.isEmpty()) {
            throw new ApplicationException("Provision and interest Head does not defined");
        }
        String finStartDt = rbiReportFacade.getMinFinYear(toDate);
        Vector vec1 = (Vector) query.get(0);
        String drglhead = brncode + (vec1.get(0).toString()) + "01";
        String crglhead = brncode + (vec1.get(1).toString()) + "01";
        if ((drglhead == null) || (crglhead == null) || (drglhead.equalsIgnoreCase("")) || (crglhead.equalsIgnoreCase(""))) {
            throw new ApplicationException("Please Check for GlHeadInt or GlHeadProv In accounttypemaster for DS Nature " + type);
        }
        String intopt = "Q";
        try {
            float trsNo = ftsMethods.getTrsNo();
            float recNo = ftsMethods.getRecNo();
            double totalTds = 0d;
            String glAccNo = null, tdsPostEnable = "N";
            double totalInterest = 0d;
            for (TdIntDetail ddsIntDetail : list) {
                totalInterest = totalInterest + ddsIntDetail.getInterest();
            }

            ftsMethods.updateBalance("PO", drglhead, 0, totalInterest, "", "");

            Integer reconBalanL = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,dramt,ty,trantype,details,auth,authby,enterBy,"
                    + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + drglhead + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                    + totalInterest + ",1,8,'Interest Amount','Y','" + userName + "','" + userName + "'," + recNo + "," + trsNo + ",3,3,'" + brncode
                    + "','" + brncode + "')").executeUpdate();
            if (reconBalanL <= 0) {
                return "Error in reconbalan Insertion !!!";
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            todayDate = (sdf.format(new Date()));
            for (TdIntDetail pojo : list) {
                double interest = pojo.getInterest();
                double tdsAmt = pojo.getTds();
                double tdsAmtUnRec = pojo.getUnRecoverTds();
                String acNo = pojo.getAcno();
                String fromDt = pojo.getFromDt();
                String toDt = pojo.getToDt();
                double closeAcInt = pojo.getCloseAcIntPaid();
                double tdsPer = pojo.getTdsPer();
                double closeAcTds = 0d;
                double excessTdsShifted = pojo.getTdsExtraShift();
                if (tdsAmt < 0) {
                    tdsAmt = 0;
                }
                String closeAcFlag = "";
                if (closeAcInt > 0 && tdsAmt > 0) {
                    closeAcFlag = " includes closed accounts TDS";
                    closeAcTds = Math.round((closeAcInt * tdsPer) / 100);
                }

                recNo = ftsMethods.getRecNo();
//                recNo = recNo + 1;

                Integer ddsTran = em.createNativeQuery("insert into ddstransaction( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                        + " details, iy, EnterBy,Auth, recno, payby, Authby, trsno, org_brnid,dest_brnid,"
                        + "trandesc,tokenpaid,favorof,CheckBy,RECEIPTNO)values('" + acNo + "', 0,'" + todayDate + "','" + todayDate + "',0,"
                        + "" + interest + ",0,8,'Int Posted for " + fromDt + "To " + toDt + " " + closeAcFlag + " " + closeAcFlag + "',1,'" + userName + "','Y', "
                        + "" + recNo + ", 3,'" + userName + "'," + trsNo + ","
                        + "'" + brncode + "','" + brncode + "',3,'','','','')").executeUpdate();
                if (ddsTran <= 0) {
                    return "Error in dds Recon Insertion";
                }

                /**
                 * ***TDS Deduction from account **************
                 */
                if (tdsAmt > 0 && interest >= tdsAmt) {
                    totalTds = totalTds + tdsAmt;

                    /**
                     * Code Add On 20150624 *
                     */
//                    List tdsCloseList = em.createNativeQuery("select tds from tds_reserve_history a, accountmaster b where a.acno = b.acno "
//                            + " and a.recovered ='NR' and b.accstatus =9 "
//                            + " and a.acno = '" + acNo + "' order by txnid").getResultList();
//                    for (int cnt = 0; cnt < tdsCloseList.size(); cnt++) {
//                        Vector tdsCloseVect = (Vector) tdsCloseList.get(cnt);
//                        double cltds = Double.parseDouble(tdsCloseVect.get(0).toString());
//                        tdsAmt = tdsAmt + cltds;
//                        if (tdsAmt < interest) {
//                            totalTds = totalTds + cltds;
//                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', trsno = " + trsNo + ", recno = " + recNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
//                                    + " where acno = '" + acNo + "' and recovered ='NR' and todt<='" + toDt + "'");
//                            int result = updateTdsQuery.executeUpdate();
//                            if (result < 0) {
//                                totalTds = totalTds - tdsAmt;
//                                return "Error in updating tdshistory for tds ";
//                            }
//                            Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
//                                    + "VALUES('" + acNo + "',0," + cltds + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','')").executeUpdate();
//                            if (TDSHistory <= 0) {
//                                throw new ApplicationException("Data Not Saved For " + acNo);
//                            }
//                        } else {
//                            tdsAmt = tdsAmt - cltds;
//                            break;
//                        }
//                    }
                    /**
                     * End Of Code Add On 20150624 *
                     */
                    Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered, trsno, recno, tdsRecoveredDt, recoveredVch, closeAcTds, excessTdsShifted  )"
                            + "VALUES('" + acNo + "',0," + tdsAmt + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','Q','R'," + trsNo + "," + recNo + ",date_format(now(),'%Y%m%d'), " + trsNo + "," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                    if (tdsHisResult <= 0) {
                        throw new ApplicationException("Data Not Saved For " + acNo);
                    }

                    Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                            + "VALUES('" + acNo + "',0," + tdsAmt + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','')").executeUpdate();
                    if (TDSHistory <= 0) {
                        throw new ApplicationException("Data Not Saved For " + acNo);
                    }
                    double remainingInt = interest - tdsAmt;
                    /*
                     *Getting TDS Deduction from Close account which are not recovered
                     */
                    if (tdsAmtUnRec > 0 && remainingInt > 0) {
                        double unRecCloseAc = autoTermDepositFacade.getCustomerFinYearTds(acNo, finStartDt, toDt, "NR", "Y");//Close Account TDS which was UN-RECOVERED
                        List nrCloseList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acNo, finStartDt, toDt, "NR", "Y");
                        if (!nrCloseList.isEmpty()) {
//                                remainingInt = closeAcTdsRecovery(nrCloseList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                            for (int cnt = 0; cnt < nrCloseList.size(); cnt++) {
                                Vector tdsCloseVect = (Vector) nrCloseList.get(cnt);
                                String closeAcNo = tdsCloseVect.get(0).toString();
                                float closeVchNo = Float.parseFloat(tdsCloseVect.get(1).toString());
                                String clActIntOpt = tdsCloseVect.get(2).toString();
                                String frDtPost = tdsCloseVect.get(3).toString();
                                String toDtPost = tdsCloseVect.get(4).toString();
                                double unRecoveredTdsOnCloseAc = Double.parseDouble(tdsCloseVect.get(5).toString());
//                                    double unRecoveredTdsOnCloseAc = autoTermDepositFacade.getClActFinYearTds(closeAcNo, finStartDt, toDt, "NR", closeVchNo);
//                                    tds = tds + unRecoveredTdsOnCloseAc;
                                if (unRecoveredTdsOnCloseAc <= remainingInt) {
                                    Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                            + " where acno = '" + closeAcNo + "' and VoucherNo = '" + closeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                    int result = updateTdsQuery.executeUpdate();
                                    if (result <= 0) {
                                        throw new ApplicationException("Error in updating tdshistory for tds ");
                                    }
                                    TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                            + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + unRecoveredTdsOnCloseAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                    if (TDSHistory <= 0) {
                                        throw new ApplicationException("Data Not Saved For " + acNo);
                                    }
                                    remainingInt = remainingInt - unRecoveredTdsOnCloseAc;
                                    tdsAmt = tdsAmt + unRecoveredTdsOnCloseAc;
                                    totalTds = totalTds + unRecoveredTdsOnCloseAc;
                                } else {
                                    break;
                                }
                            }
                        }

                        /*
                         *Getting TDS Deduction from Running account which are not recovered
                         */
                        if (remainingInt > 0) {
                            double unRecRunningAc = autoTermDepositFacade.getCustomerFinYearTds(acNo, finStartDt, toDt, "NR", "N");//Running Account TDS which was UN-RECOVERED
                            List nrActiveList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acNo, finStartDt, toDt, "NR", "N");
                            if (!nrActiveList.isEmpty()) {
//                                    remainingInt = activeAcTdsRecovery(nrActiveList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                for (int cnt = 0; cnt < nrActiveList.size(); cnt++) {
                                    Vector tdsActiveVect = (Vector) nrActiveList.get(cnt);
                                    String activeAcNo = tdsActiveVect.get(0).toString();
                                    float activeVchNo = Float.parseFloat(tdsActiveVect.get(1).toString());
                                    String activeActIntOpt = tdsActiveVect.get(2).toString();
                                    String frDtPost = tdsActiveVect.get(3).toString();
                                    String toDtPost = tdsActiveVect.get(4).toString();
                                    double unRecoveredTdsOnActiveAc = Double.parseDouble(tdsActiveVect.get(5).toString());
//                                    double unRecoveredTdsOnActiveAc = autoTermDepositFacade.getClActFinYearTds(activeAcNo, finStartDt, toDt, "NR", activeVchNo);
//                                    tds = tds + unRecoveredTdsOnactiveAc;
                                    if (unRecoveredTdsOnActiveAc <= remainingInt) {
                                        Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                + " where acno = '" + activeAcNo + "' and VoucherNo = '" + activeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                        int result = updateTdsQuery.executeUpdate();
                                        if (result <= 0) {
                                            throw new ApplicationException("Error in updating tdshistory for tds ");
                                        }
                                        TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                + "VALUES('" + activeAcNo + "'," + activeVchNo + "," + unRecoveredTdsOnActiveAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                        if (TDSHistory <= 0) {
                                            throw new ApplicationException("Data Not Saved For " + acNo);
                                        }
                                        remainingInt = remainingInt - unRecoveredTdsOnActiveAc;
                                        tdsAmt = tdsAmt + unRecoveredTdsOnActiveAc;
                                        totalTds = totalTds + unRecoveredTdsOnActiveAc;
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    recNo = ftsMethods.getRecNo();
//                    recNo = recNo + 1;
                    ddsTran = em.createNativeQuery("insert into ddstransaction( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                            + " details, iy, InstNo, instDt, EnterBy,Auth, recno, payby, Authby, trsno, org_brnid,dest_brnid,"
                            + "trandesc,tokenpaid,favorof,CheckBy,RECEIPTNO)values('" + acNo + "', 1,'" + todayDate + "','" + todayDate + "'," + tdsAmt + ","
                            + "0,0,2,'Tds Decucted for " + fromDt + " To " + toDt + " " + closeAcFlag + "',1,'',null,'" + userName + "','Y', "
                            + "" + recNo + ", 3,'" + userName + "'," + trsNo + ","
                            + "'" + brncode + "','" + brncode + "',33,'','','','')").executeUpdate();
                    if (ddsTran <= 0) {
                        return "Error in tdRecon Insertion";
                    }

                } else {
                    if (tdsAmt > 0) {
                        double unRecTds = tdsAmt - interest;
                        tdsAmt = interest;
                        totalTds = totalTds + tdsAmt;

                        recNo = ftsMethods.getRecNo();
//                        recNo = recNo + 1;
                        ddsTran = em.createNativeQuery("insert into ddstransaction( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                                + " details, iy, InstNo, instDt, EnterBy,Auth, recno, payby, Authby, trsno, org_brnid,dest_brnid,"
                                + "trandesc,tokenpaid,favorof,CheckBy,RECEIPTNO)values('" + acNo + "', 1,'" + todayDate + "','" + todayDate + "'," + tdsAmt + ","
                                + "0,0,2,'Tds Decucted for " + fromDt + " To " + toDt + " " + closeAcFlag + "',1,'',null,'" + userName + "','Y', "
                                + "" + recNo + ", 3,'" + userName + "'," + trsNo + ","
                                + "'" + brncode + "','" + brncode + "',33,'','','','')").executeUpdate();
                        if (ddsTran <= 0) {
                            return "Error in tdRecon Insertion";
                        }

                        Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered, trsno, recno, tdsRecoveredDt, recoveredVch, closeAcTds, excessTdsShifted )"
                                + "VALUES('" + acNo + "',0," + tdsAmt + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','Q','R'," + trsNo + "," + recNo + ",date_format(now(),'%Y%m%d'), " + trsNo + "," + closeAcTds + "," + excessTdsShifted + ")").executeUpdate();
                        if (tdsHisResult <= 0) {
                            throw new ApplicationException("Data Not Saved For " + acNo);
                        }

                        Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                + "VALUES('" + acNo + "',0," + tdsAmt + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','')").executeUpdate();
                        if (TDSHistory <= 0) {
                            throw new ApplicationException("Data Not Saved For " + acNo);
                        }
                        tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered)"
                                + "VALUES('" + acNo + "',0," + unRecTds + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','Q','NR')").executeUpdate();
                        if (tdsHisResult <= 0) {
                            throw new ApplicationException("Data Not Saved For " + acNo);
                        }
                    } else if (tdsAmt <= 0 && tdsAmtUnRec > 0) {
                        double remainingInt = interest;
                        tdsAmt = 0;
                        /*
                         *Getting TDS Deduction from Close account which are not recovered
                         */
                        if (tdsAmtUnRec > 0 && remainingInt > 0) {
                            double unRecCloseAc = autoTermDepositFacade.getCustomerFinYearTds(acNo, finStartDt, toDt, "NR", "Y");//Close Account TDS which was UN-RECOVERED
                            List nrCloseList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acNo, finStartDt, toDt, "NR", "Y");
                            if (!nrCloseList.isEmpty()) {
//                                remainingInt = closeAcTdsRecovery(nrCloseList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                for (int cnt = 0; cnt < nrCloseList.size(); cnt++) {
                                    Vector tdsCloseVect = (Vector) nrCloseList.get(cnt);
                                    String closeAcNo = tdsCloseVect.get(0).toString();
                                    float closeVchNo = Float.parseFloat(tdsCloseVect.get(1).toString());
                                    String clActIntOpt = tdsCloseVect.get(2).toString();
                                    String frDtPost = tdsCloseVect.get(3).toString();
                                    String toDtPost = tdsCloseVect.get(4).toString();
                                    double unRecoveredTdsOnCloseAc = Double.parseDouble(tdsCloseVect.get(5).toString());
//                                    double unRecoveredTdsOnCloseAc = autoTermDepositFacade.getClActFinYearTds(closeAcNo, finStartDt, toDt, "NR", closeVchNo);
//                                    tds = tds + unRecoveredTdsOnCloseAc;
                                    if (unRecoveredTdsOnCloseAc <= remainingInt) {
                                        Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                + " where acno = '" + closeAcNo + "' and VoucherNo = '" + closeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                        int result = updateTdsQuery.executeUpdate();
                                        if (result <= 0) {
                                            throw new ApplicationException("Error in updating tdshistory for tds ");
                                        }
                                        int TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + unRecoveredTdsOnCloseAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                        if (TDSHistory <= 0) {
                                            throw new ApplicationException("Data Not Saved For " + acNo);
                                        }
                                        remainingInt = remainingInt - unRecoveredTdsOnCloseAc;
                                        tdsAmt = tdsAmt + unRecoveredTdsOnCloseAc;
                                        totalTds = totalTds + unRecoveredTdsOnCloseAc;
                                    } else {
                                        break;
                                    }
                                }
                            }

                            /*
                             *Getting TDS Deduction from Running account which are not recovered
                             */
                            if (remainingInt > 0) {
                                double unRecRunningAc = autoTermDepositFacade.getCustomerFinYearTds(acNo, finStartDt, toDt, "NR", "N");//Running Account TDS which was UN-RECOVERED
                                List nrActiveList = autoTermDepositFacade.getUnRecoverdTdsAccounts(acNo, finStartDt, toDt, "NR", "N");
//                                System.out.println("acno:" + acNo);
                                if (!nrActiveList.isEmpty()) {
//                                    remainingInt = activeAcTdsRecovery(nrActiveList, finStartDt, toDt, trsno, todate, remainingInt, acno, intopt);
                                    for (int cnt = 0; cnt < nrActiveList.size(); cnt++) {
                                        Vector tdsActiveVect = (Vector) nrActiveList.get(cnt);
                                        String activeAcNo = tdsActiveVect.get(0).toString();
                                        float activeVchNo = Float.parseFloat(tdsActiveVect.get(1).toString());
                                        String activeActIntOpt = tdsActiveVect.get(2).toString();
                                        String frDtPost = tdsActiveVect.get(3).toString();
                                        String toDtPost = tdsActiveVect.get(4).toString();
                                        double unRecoveredTdsOnActiveAc = Double.parseDouble(tdsActiveVect.get(5).toString());
//                                    double unRecoveredTdsOnActiveAc = autoTermDepositFacade.getClActFinYearTds(activeAcNo, finStartDt, toDt, "NR", activeVchNo);
//                                    tds = tds + unRecoveredTdsOnactiveAc;
                                        if (unRecoveredTdsOnActiveAc <= remainingInt) {
                                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trsNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                    + " where acno = '" + activeAcNo + "' and VoucherNo = '" + activeVchNo + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                                            int result = updateTdsQuery.executeUpdate();
                                            if (result <= 0) {
                                                throw new ApplicationException("Error in updating tdshistory for tds ");
                                            }
                                            int TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                                    + "VALUES('" + activeAcNo + "'," + activeVchNo + "," + unRecoveredTdsOnActiveAc + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDtPost + "','" + frDtPost + "','" + intopt + "')").executeUpdate();
                                            if (TDSHistory <= 0) {
                                                throw new ApplicationException("Data Not Saved For " + acNo);
                                            }
                                            remainingInt = remainingInt - unRecoveredTdsOnActiveAc;
                                            tdsAmt = tdsAmt + unRecoveredTdsOnActiveAc;
                                            totalTds = totalTds + unRecoveredTdsOnActiveAc;
                                        } else {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (tdsAmt > 0) {
                            recNo = ftsMethods.getRecNo();
//                            recNo = recNo + 1;
                            ddsTran = em.createNativeQuery("insert into ddstransaction( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                                    + " details, iy, InstNo, instDt, EnterBy,Auth, recno, payby, Authby, trsno, org_brnid,dest_brnid,"
                                    + "trandesc,tokenpaid,favorof,CheckBy,RECEIPTNO)values('" + acNo + "', 1,'" + todayDate + "','" + todayDate + "'," + tdsAmt + ","
                                    + "0,0,2,'Tds Decucted for " + fromDt + " To " + toDt + " " + closeAcFlag + "',1,'',null,'" + userName + "','Y', "
                                    + "" + recNo + ", 3,'" + userName + "'," + trsNo + ","
                                    + "'" + brncode + "','" + brncode + "',33,'','','','')").executeUpdate();
                        }
                    }
                }

                /**
                 * ***END of TDS Deduction from account **************
                 */
                Integer interestHistory = em.createNativeQuery("Insert into dds_interesthistory values('" + acNo + "','" + interest + "','" + todayDate + "','" + fromDate
                        + "','" + toDate + "', now(),'" + userName + "')").executeUpdate();
                if (interestHistory <= 0) {
                    return "Error in TD_InterestHistory Insertion";
                }

                Query updateQuery1 = em.createNativeQuery("update cbs_loan_acc_mast_sec set int_calc_upto_dt='" + toDate + "', "
                        + " next_int_calc_dt='" + CbsUtil.dateAdd(toDate, 1) + "' where acno = '" + acNo + "'");
                int result = updateQuery1.executeUpdate();
                if (result <= 0) {
                    return "Error in updating Next Interest Payable Date";
                }
            }

            /**
             * *************Total TDS posting in GL Recon**************
             */
//            if (tdsPostEnable.equalsIgnoreCase("Y")) {
            List chk3 = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab "
                    + "where TDS_Applicabledate<='" + toDate + "')").getResultList();
            if (!chk3.isEmpty()) {
                Vector chk3V = (Vector) chk3.get(0);
                glAccNo = brncode + chk3V.get(0).toString();
            }
            Integer reconBalan = em.createNativeQuery("UPDATE reconbalan SET BALANCE=BALANCE+" + totalTds + ",DT=date_format(now(),'%Y%m%d')  WHERE ACNO='" + glAccNo + "'").executeUpdate();
            if (reconBalan <= 0) {
                return "Error in GL Balance Updation !!!";
            }

            if (totalTds > 0) {
                recNo = ftsMethods.getRecNo();
//                    recNo = recNo + 1;
                Integer glrecon = em.createNativeQuery("INSERT INTO gl_recon (ACNO,Dt,ValueDt,CrAmt,Ty,TranType,Details,EnterBy,"
                        + "Auth,AuthBy,TranTime,RecNo,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) VALUES('" + glAccNo + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                        + "" + totalTds + ",0,2, CONCAT('TDS POSTING TILL DT ' , date_format('" + toDate + "','%Y%m%d')),'" + userName + "','Y','" + userName + "',now(),'" + recNo + "',"
                        + "'" + trsNo + "',3,33,1,'" + brncode + "','" + brncode + "')").executeUpdate();
                if (glrecon <= 0) {
                    return "Error in TDS Posting in GL !!!";
                }
            }
//            }

            /**
             * *************END of Total TDS posting in GL Recon**************
             */
            /* Updation For NextIntPayDt For All Accounts */
//            Query updateQuery1 = em.createNativeQuery("update cbs_loan_acc_mast_sec tv inner join accountmaster tm on tv.acno = tm.acno "
//                    + "set int_calc_upto_dt='" + ymd.format(toDate) + "', next_int_calc_dt='" + CbsUtil.dateAdd(ymd.format(toDate), 1) + "' where tm.accttype = '" + type + "' and tm.curbrcode = '" + brncode
//                    + "' and next_int_calc_dt <='" + ymd.format(toDate) + "'");
//            int result = updateQuery1.executeUpdate();
//            if (result < 0) {
//                return "Error in updating Next Interest Payable Date";
//            }
            Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + userName + "'  "
                    + "WHERE AC_TYPE = '" + type + "' AND FROM_DT = '" + fromDate + "' and TO_DT = '" + toDate + "' and flag = 'I' and brncode = '" + brncode + "'");
            Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
            if (updateAccTypeIntPara <= 0) {
                return "Error in updating cbs_loan_acctype_interest_parameter";
            }
            return "true:Interest Posting completed successfully ! Generated Batch No. is " + trsNo;
        } catch (Exception e) {
            try {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ApplicationException(ex.getMessage());
            }
            throw new ApplicationException(e.getMessage());
        }

    }

    public String tdIntPostProv(List<TdIntDetail> tdIntDetailsList, String acctype, String intopt, String enterBy, String tdIntHead,
            String todate, String orgnBrCode) throws ApplicationException {
        Float recno;
        Float trsno;
        String glHeadProv = "";
        try {
            double totalInterest = 0d;
            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
                totalInterest = totalInterest + tdIntDetail.getInterest();
            }
            recno = ftsMethods.getRecNo();
            trsno = ftsMethods.getTrsNo();

            List glheadprovList = em.createNativeQuery("SELECT glheadprov FROM  accounttypemaster where acctcode ='" + acctype + "'").getResultList();
            if (!glheadprovList.isEmpty()) {
                Vector obcGlagVect = (Vector) glheadprovList.get(0);
                glHeadProv = obcGlagVect.get(0).toString();
            }
            if (glHeadProv == null || glHeadProv.equalsIgnoreCase("") || glHeadProv.length() == 0) {
                return "GL Head Prov. does not exist in account type master";
            }
            glHeadProv = orgnBrCode + glHeadProv + "01";

            ftsMethods.updateBalance("PO", tdIntHead, 0, totalInterest, "", "");

            Integer reconBalanL = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,dramt,ty,trantype,details,auth,authby,enterBy,"
                    + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + tdIntHead + "',date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d'),"
                    + totalInterest + ",1,8,'Interest Amount','Y','System','" + enterBy + "'," + recno + "," + trsno + ",3,3,'" + orgnBrCode
                    + "','" + orgnBrCode + "')").executeUpdate();
            if (reconBalanL <= 0) {
                return "Error in reconbalan Insertion !!!";
            }

            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
                double interest = tdIntDetail.getInterest();
                String acno = tdIntDetail.getAcno();
                float vchNo = tdIntDetail.getVoucherNo();

                String fromDt = tdIntDetail.getFromDt();
                String toDt = tdIntDetail.getToDt();

                recno = ftsMethods.getRecNo();
//                recno = recno + 1;
                Integer interestHistory = em.createNativeQuery("insert into td_interesthistory(acno,voucherno,interest,fromdt,"
                        + "todt,intopt,dt) values('" + acno + "'," + vchNo + "," + interest + ",'" + fromDt + "','" + toDt + "','"
                        + intopt + "'," + "date_format(now(),'%Y%m%d'))").executeUpdate();
                if (interestHistory <= 0) {
                    return "Error in TD_InterestHistory Insertion";
                }
                /* Updation For CumuPrinAmt For All Accounts */
                if (intopt.equalsIgnoreCase("C")) {
                    Query updateQuery = em.createNativeQuery("Update td_vouchmst Set  CumuPrinAmt=IFNULL(CumuPrinAmt,0)+IFNULL(" + interest + ",0)"
                            + " where acno = '" + acno + "' and voucherno = " + vchNo + " and IntOpt='" + intopt + "' and Status='A' and  CumuPrinAmt is not null");
                    int result = updateQuery.executeUpdate();
                    if (result < 0) {
                        return "Error in updating principal amount";
                    }
                }
            }

            /* Updation For NextIntPayDt For All Accounts */
            Query updateQuery1 = em.createNativeQuery("Update td_vouchmst tv, td_accountmaster tm  set tv.nextIntPayDt='" + CbsUtil.dateAdd(todate, 1) + "' "
                    + " where tv.acno = tm.acno and tm.accttype = '" + acctype
                    + "' and tm.curbrcode = '" + orgnBrCode + "' and intopt='" + intopt
                    + "' and Status='A'  and nextintpaydt<='" + todate + "' and  CumuPrinAmt is not null");
            int result = updateQuery1.executeUpdate();
            if (result < 0) {
                return "Error in updating Next Interest Payable Date";
            }

            ftsMethods.updateBalance("PO", glHeadProv, totalInterest, 0, "", "");

            Integer reconBalanP = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,cramt,ty,trantype,details,auth,authby,enterBy,"
                    + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + glHeadProv + "',date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d'),"
                    + totalInterest + ",0,8,'Interest Amount','Y','System','" + enterBy + "'," + recno + "," + trsno + ",3,3,'" + orgnBrCode
                    + "','" + orgnBrCode + "')").executeUpdate();
            if (reconBalanP <= 0) {
                return "Error in reconbalan Insertion !!!";
            }

            /* Updation For IntFlag */
            Query insertQuery1 = em.createNativeQuery("Insert into td_intflag (TD_tilldate,TD_sysdate,intOpt,acType,brncode)"
                    + " values('" + todate + "',date_format(now(),'%Y%m%d'),'" + intopt + "','" + acctype + "','" + orgnBrCode + "')");
            result = insertQuery1.executeUpdate();
            if (result < 0) {
                return "Error in updating Interest flag";
            }
            return "Yes";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String tdIntPostProvWithTds(List<TdIntDetail> tdIntDetailsList, String acctype, String intopt, String enterBy, String tdIntHead,
            String todate, String orgnBrCode) throws ApplicationException {
        Float recno;
        Float trsno;
        String glHeadProv = "", glAccNo = null;
        try {
            double totalInterest = 0d, totalTds = 0d;
            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
                totalInterest = totalInterest + tdIntDetail.getInterest();
            }
            recno = ftsMethods.getRecNo();
            trsno = ftsMethods.getTrsNo();

            List glheadprovList = em.createNativeQuery("SELECT glheadprov FROM  accounttypemaster where acctcode ='" + acctype + "'").getResultList();
            if (!glheadprovList.isEmpty()) {
                Vector obcGlagVect = (Vector) glheadprovList.get(0);
                glHeadProv = obcGlagVect.get(0).toString();
            }
            if (glHeadProv == null || glHeadProv.equalsIgnoreCase("") || glHeadProv.length() == 0) {
                return "GL Head Prov. does not exist in account type master";
            }
            glHeadProv = orgnBrCode + glHeadProv + "01";

            ftsMethods.updateBalance("PO", tdIntHead, 0, totalInterest, "", "");

            Integer reconBalanL = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,dramt,ty,trantype,details,auth,authby,enterBy,"
                    + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + tdIntHead + "',date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d'),"
                    + totalInterest + ",1,8,'Interest Amount','Y','System','" + enterBy + "'," + recno + "," + trsno + ",3,3,'" + orgnBrCode
                    + "','" + orgnBrCode + "')").executeUpdate();
            if (reconBalanL <= 0) {
                return "Error in reconbalan Insertion !!!";
            }

            for (TdIntDetail tdIntDetail : tdIntDetailsList) {
                double interest = tdIntDetail.getInterest();
                String acno = tdIntDetail.getAcno();
                float vchNo = tdIntDetail.getVoucherNo();

                String fromDt = tdIntDetail.getFromDt();
                String toDt = tdIntDetail.getToDt();
                double tdsAmt = tdIntDetail.getTds() + tdIntDetail.getUnRecoverTds();

                recno = ftsMethods.getRecNo();
                Integer interestHistory = em.createNativeQuery("insert into td_interesthistory(acno,voucherno,interest,fromdt,"
                        + "todt,intopt,dt) values('" + acno + "'," + vchNo + "," + interest + ",'" + fromDt + "','" + toDt + "','"
                        + intopt + "'," + "date_format(now(),'%Y%m%d'))").executeUpdate();
                if (interestHistory <= 0) {
                    return "Error in TD_InterestHistory Insertion";
                }
                if (tdsAmt > 0 && tdsAmt <= interest) {
                    totalTds = totalTds + tdsAmt;
                    recno = ftsMethods.getRecNo();
                    /**
                     * Code Add On 20150624 *
                     */
                    List tdsCloseList = em.createNativeQuery("select tds,a.voucherno from tds_reserve_history a, td_vouchmst b where a.acno = b.acno "
                            + " and a.recovered ='NR' and b.status ='C' "
                            + " and a.acno = '" + acno + "' and a.voucherno = b.voucherno order by txnid").getResultList();
                    for (int cnt = 0; cnt < tdsCloseList.size(); cnt++) {
                        Vector tdsCloseVect = (Vector) tdsCloseList.get(cnt);
                        double cltds = Double.parseDouble(tdsCloseVect.get(0).toString());
                        tdsAmt = tdsAmt + cltds;
                        if (tdsAmt < interest) {
                            totalTds = totalTds + cltds;
                            String vchCl = tdsCloseVect.get(1).toString();
                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', trsno = " + trsno + ", recno = " + recno + ", recoveredVch = " + vchNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                    + " where acno = '" + acno + "' and VoucherNo = '" + vchCl + "' and recovered ='NR' and todt<='" + toDt + "' and intOpt = '" + intopt + "'");
                            int result = updateTdsQuery.executeUpdate();
                            if (result < 0) {
                                totalTds = totalTds - cltds;
                                return "Error in updating tdshistory for tds ";
                            }
                        } else {
                            tdsAmt = tdsAmt - cltds;
                            break;
                        }
                    }

                    /**
                     * End Of Code Add On 20150624 *
                     */
                    Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered)"
                            + "VALUES('" + acno + "'," + vchNo + "," + tdsAmt + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "','R')").executeUpdate();
                    if (tdsHisResult <= 0) {
                        throw new ApplicationException("Data Not Saved For " + acno);
                    }
                    Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                            + "VALUES('" + acno + "'," + vchNo + "," + tdsAmt + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "')").executeUpdate();
                    if (TDSHistory <= 0) {
                        throw new ApplicationException("Data Not Saved For " + acno);
                    }
                } else {
                    if (tdsAmt > 0) {
                        Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered)"
                                + "VALUES('" + acno + "'," + vchNo + "," + tdsAmt + ",date_format(now(),'%Y%m%d'),'" + toDt + "','" + fromDt + "','" + intopt + "','NR')").executeUpdate();
                        if (tdsHisResult <= 0) {
                            throw new ApplicationException("Data Not Saved For " + acno);
                        }
                    }
                }
                /* Updation For CumuPrinAmt For All Accounts */
                if (intopt.equalsIgnoreCase("C")) {
                    Query updateQuery = em.createNativeQuery("Update td_vouchmst Set  CumuPrinAmt=IFNULL(CumuPrinAmt,0)+IFNULL(" + interest + ",0)-IFNULL(" + tdsAmt + ",0)"
                            + " where acno = '" + acno + "' and voucherno = " + vchNo + " and IntOpt='" + intopt + "' and Status='A' and  CumuPrinAmt is not null");
                    int result = updateQuery.executeUpdate();
                    if (result < 0) {
                        return "Error in updating principal amount";
                    }
                }
            }

            /* Updation For NextIntPayDt For All Accounts */
            Query updateQuery1 = em.createNativeQuery("Update td_vouchmst tv, td_accountmaster tm  set tv.nextIntPayDt='" + CbsUtil.dateAdd(todate, 1) + "' "
                    + " where tv.acno = tm.acno and tm.accttype = '" + acctype
                    + "' and tm.curbrcode = '" + orgnBrCode + "' and intopt='" + intopt
                    + "' and Status='A'  and nextintpaydt<='" + todate + "' and  CumuPrinAmt is not null");
            int result = updateQuery1.executeUpdate();
            if (result < 0) {
                return "Error in updating Next Interest Payable Date";
            }

            ftsMethods.updateBalance("PO", glHeadProv, totalInterest, 0, "", "");

            Integer reconBalanP = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,cramt,ty,trantype,details,auth,authby,enterBy,"
                    + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + glHeadProv + "',date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d'),"
                    + totalInterest + ",0,8,'Interest Amount','Y','System','" + enterBy + "'," + recno + "," + trsno + ",3,3,'" + orgnBrCode
                    + "','" + orgnBrCode + "')").executeUpdate();
            if (reconBalanP <= 0) {
                return "Error in reconbalan Insertion !!!";
            }
            List chk3 = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab "
                    + "where TDS_Applicabledate<='" + todate + "')").getResultList();
            if (!chk3.isEmpty()) {
                Vector chk3V = (Vector) chk3.get(0);
                glAccNo = orgnBrCode + chk3V.get(0).toString();
            }
            if (totalTds > 0) {
                Integer reconBalan = em.createNativeQuery("UPDATE reconbalan SET BALANCE=BALANCE+" + totalTds + ",DT=date_format(now(),'%Y%m%d')  WHERE ACNO='" + glAccNo + "'").executeUpdate();
                if (reconBalan <= 0) {
                    return "Error in GL Balance Updation !!!";
                }

                recno = ftsMethods.getRecNo();
                Integer glrecon = em.createNativeQuery("INSERT INTO gl_recon (ACNO,Dt,ValueDt,CrAmt,Ty,TranType,Details,EnterBy,"
                        + "Auth,AuthBy,TranTime,RecNo,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) VALUES('" + glAccNo + "',date_format(now(),'%Y%m%d'),"
                        + "date_format(now(),'%Y%m%d')," + totalTds + ",0,2, CONCAT('TDS POSTING TILL DT ' , date_format('" + todate + "','%Y%m%d')),'"
                        + enterBy + "','Y','System',now(),'" + recno + "','" + trsno + "',3,33,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                if (glrecon <= 0) {
                    return "Error in TDS Posting in GL !!!";
                }
            }

            /* Updation For IntFlag */
            Query insertQuery1 = em.createNativeQuery("Insert into td_intflag (TD_tilldate,TD_sysdate,intOpt,acType,brncode)"
                    + " values('" + todate + "',date_format(now(),'%Y%m%d'),'" + intopt + "','" + acctype + "','" + orgnBrCode + "')");
            result = insertQuery1.executeUpdate();
            if (result < 0) {
                return "Error in updating Interest flag";
            }
            return "Yes";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<TdIntDetail> provInterestCalculation(String fromdt, String todt, String intopt, String acctype, String brCode) throws ApplicationException {
        List<TdIntDetail> tdIntDetailList = new ArrayList<TdIntDetail>();
        try {
            String accno = "", nextintpaydt = "", matdt = "", inttoacno = "", custName = "";
            float interest = 0.0f, pamt = 0.0f, product = 0.0f;
            Float roi = 0.0f, voucherno = 0.0f, seqno = 0.0f;

            Object bnkNameObj = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where "
                    + "b.alphacode=br.alphacode and br.brncode=" + Integer.parseInt(brCode)).getSingleResult();

            String bnkName = ((Vector) bnkNameObj).elementAt(0).toString();
            String bnkAddress = ((Vector) bnkNameObj).elementAt(1).toString();

            List selectQueryTRoi = em.createNativeQuery("SELECT Code FROM cbs_parameterinfo where name ='TD_SB_INT'").getResultList();
            if (selectQueryTRoi.size() > 0) {
                Vector last = (Vector) selectQueryTRoi.get(0);
                roi = Float.parseFloat(last.get(0).toString());
            }

            List cursorLt = em.createNativeQuery("select tm.seqno,tm.acno, substring(am.custName,1,28),"
                    + " tm.voucherno,tm.roi,date_format(tm.nextintpaydt,'%Y%m%d'),date_format(tm.matdt,'%Y%m%d'),"
                    + "tm.prinamt,ifnull(tm.years,0), ifnull(tm.months,0),ifnull(tm.days,0),tm.cumuprinamt,tm.inttoAcno,"
                    + "tm.TDSDeducted, date_format(tm.FDDT,'%Y%m%d'),tm.period From td_vouchmst tm, td_accountmaster am Where "
                    + "am.acno=tm.acno and tm.intopt='" + intopt + "' and tm.status<>'C'  and am.accttype='"
                    + acctype + "' AND tm.matdt<='" + todt + "' and tm.roi<>0 and "
                    + "am.CurBrCode='" + brCode + "' ").getResultList();

            if (cursorLt.size() > 0) {
                for (int i = 0; i < cursorLt.size(); i++) {
                    Vector curV = (Vector) cursorLt.get(i);

                    seqno = Float.parseFloat(curV.get(0).toString());
                    accno = curV.get(1).toString();
                    custName = curV.get(2).toString();
                    voucherno = Float.parseFloat(curV.get(3).toString());
                    nextintpaydt = curV.get(5).toString();
                    matdt = curV.get(6).toString();
                    pamt = Float.parseFloat(curV.get(7).toString());

                    long dateDiff = CbsUtil.dayDiff(ymd.parse(matdt), ymd.parse(todt));

                    if ((intopt.equalsIgnoreCase("S")) || (intopt.equalsIgnoreCase("C"))) {
                        List paidIntList = em.createNativeQuery("select ifnull(sum(ifnull(interest,0)),0) from td_interesthistory where "
                                + "acno='" + accno + "' and voucherno=" + voucherno).getResultList();
                        Vector vect = (Vector) paidIntList.get(0);
                        float intPaid = Float.parseFloat(vect.get(0).toString());

                        List dedTdsList = em.createNativeQuery("select ifnull(sum(ifnull(tds,0)),0) from tdshistory where "
                                + "acno='" + accno + "' and voucherno=" + voucherno).getResultList();
                        Vector tdsVect = (Vector) dedTdsList.get(0);
                        float dedTds = Float.parseFloat(tdsVect.get(0).toString());

                        product = ((pamt + intPaid) - dedTds);
                        interest = 0.0f;
                        interest = (((pamt + intPaid) - dedTds) * roi * dateDiff) / 36500;
                    }

                    if ((intopt.equalsIgnoreCase("M")) || intopt.equalsIgnoreCase("Q")) {
                        product = pamt;
                        interest = 0.0f;
                        interest = (pamt * roi * dateDiff) / 36500;
                    }
                    if (interest > 0) {
                        TdIntDetail tdIntDetail = new TdIntDetail();
                        tdIntDetail.setMsg("TRUE");
                        tdIntDetail.setBnkName(bnkName);
                        tdIntDetail.setBnkAdd(bnkAddress);
                        tdIntDetail.setAcno(accno);
                        tdIntDetail.setCustName(custName);
                        tdIntDetail.setVoucherNo(Math.round(voucherno));
                        tdIntDetail.setpAmt(pamt);
                        tdIntDetail.setRoi(roi);
                        tdIntDetail.setFromDt(nextintpaydt);
                        tdIntDetail.setMatDt(matdt);
                        double tmpInt = Math.floor(interest);
                        tdIntDetail.setInterest(tmpInt);
                        tdIntDetail.setTds(product);
                        tdIntDetail.setIntPaid(0);
                        tdIntDetail.setIntToAcno(inttoacno);
                        tdIntDetail.setToDt(todt);
                        tdIntDetail.setSeqno(dateDiff);
                        tdIntDetail.setIntOpt(intopt);
                        tdIntDetailList.add(tdIntDetail);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return tdIntDetailList;
    }
}
