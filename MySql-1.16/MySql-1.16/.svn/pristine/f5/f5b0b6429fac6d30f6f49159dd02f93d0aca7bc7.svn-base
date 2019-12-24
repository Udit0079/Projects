package com.cbs.facade.misc;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.CashHandlingChargeGridData;
import com.cbs.dto.MinTxnDetails;
import com.cbs.dto.ChargesObject;
import com.cbs.dto.agentCommPojo;
import com.cbs.dto.report.MinBalanceChargesPostPojo;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsBulkPostingFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.pojo.AtmChargePostingPojo;
import com.cbs.pojo.AtmReversalPostingPojo;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Zeeshan Waris
 */
@Stateless(mappedName = "MinBalanceChargesFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class MinBalanceChargesFacade implements MinBalanceChargesFacadeRemote {

    @EJB
    private FtsBulkPostingFacadeRemote fts;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    private InterBranchTxnFacadeRemote interFts;
    @EJB
    private NeftRtgsMgmtFacadeRemote neftRtgsFacade;
    NumberFormat formatter = new DecimalFormat("#0.00");

    public List accountTypeDropDown() throws ApplicationException {
        List accountype = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT ACCTCODE FROM accounttypemaster WHERE ACCTNATURE IN ('CA','SB')");
            accountype = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return accountype;
    }

    public String BDateCheck(String orgnBrCode) throws ApplicationException {
        String tabledate = new String();
        try {
            Query selectQuery = em.createNativeQuery("select date_format(min(date),'%Y%m%d') as tbldate from bankdays where dayendflag='N' and brncode = '" + orgnBrCode + "'");
            if (selectQuery.getResultList().size() > 0) {
                Vector qq = (Vector) selectQuery.getSingleResult();
                tabledate = (String) qq.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tabledate;
    }

    public String serverDateCheck() throws ApplicationException {
        String tempBd = new String();
        try {
            Query selectQuery = em.createNativeQuery("select date_format(curdate(),'%Y%m%d') as svrdate");
            if (selectQuery.getResultList().size() > 0) {
                Vector qq = (Vector) selectQuery.getSingleResult();
                tempBd = (String) qq.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tempBd;
    }

    public String exitSoft(String usr) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        String result = null;
        try {
            ut.begin();

            Query updateQuery = em.createNativeQuery("update securityinfo set login='Y',sid=null where userid='" + usr + "' and levelid not in (5,6)");
            Integer varient = updateQuery.executeUpdate();

            if ((varient > 0)) {
                ut.commit();
                result = "Exit Software";
            } else {
                ut.rollback();
                result = "Not Exit Software";
            }

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
        return result;
    }

    /**
     * Create By Dhirendra from Zeeshan Code*
     */
    public List<ChargesObject> minBalanceChargesCaculate(String acType, String fromDt, String toDt, String brnCode, String enterBy, String enteryDt) throws ApplicationException {
        String minDate;
        String maxDate;
        String dtCode = "0";
        String mName = "";
        List<ChargesObject> minimumChargesList = new ArrayList<ChargesObject>();
        try {
            Object bnkNameObj = em.createNativeQuery("select b.bankname,b.bankaddress,State from bnkadd b,branchmaster br where "
                    + "b.alphacode=br.alphacode and br.brncode=cast('" + brnCode + "' as unsigned)").getSingleResult();

            String bnkName = ((Vector) bnkNameObj).elementAt(0).toString();
            String bnkAddress = ((Vector) bnkNameObj).elementAt(1).toString();
            String brState = ((Vector) bnkNameObj).elementAt(2).toString();

            long days = CbsUtil.dayDiff(ymd.parse(fromDt), ymd.parse(toDt));
            if (days < 0) {
                //return getErrorMsgList("Please check the dates entered ");
                throw new ApplicationException("Please check the dates entered");
            }
            String acctNature = getAcNature(acType);
            List datecode = new ArrayList();

            if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                datecode = em.createNativeQuery("Select code from parameterinfo_report where reportname='MIN_CHARGE_CA'").getResultList();
                if (datecode.size() > 0) {
                    Vector rep = (Vector) datecode.get(0);
                    dtCode = rep.get(0).toString();
                } else {
                    //return getErrorMsgListInspection("There Exists No Data in parameterinfo_report Table");
                    throw new ApplicationException("No Data Exists in parameterinfo_report Table");
                }
            } else if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                datecode = em.createNativeQuery("Select code from parameterinfo_report where reportname='MIN_CHARGE_SB'").getResultList();
                if (datecode.size() > 0) {
                    Vector rep = (Vector) datecode.get(0);
                    dtCode = rep.get(0).toString();
                } else {
                    //return getErrorMsgListInspection("There Exists No Data in parameterinfo_report Table");
                    throw new ApplicationException("No Data Exists in parameterinfo_report Table");
                }
            } else {
                //return getErrorMsgListInspection("Please check the Code " + acType);
                throw new ApplicationException("Please check the Code " + acType);
            }

            if (dtCode.equalsIgnoreCase("0")) {
                List mList = em.createNativeQuery("Select monthname('" + fromDt + "')").getResultList();
                if (mList.size() > 0) {
                    Vector mRep = (Vector) mList.get(0);
                    mName = mRep.get(0).toString();
                }

                if (fromDt.substring(4, 6).equalsIgnoreCase(toDt.substring(4, 6)) && (fromDt.substring(6, 8).equalsIgnoreCase("01"))) {
                    if (!fromDt.substring(0, 5).equalsIgnoreCase(toDt.substring(0, 5))) {
                        //return getErrorMsgListInspection("Dates are not of the same month or From date and To Date is not correct");
                        throw new ApplicationException("Dates are not of the same month or From date and To Date is not correct");
                    }
                } else {
                    //return getErrorMsgListInspection("Dates are not of the same month or From date and To Date is not correct");
                    throw new ApplicationException("Dates are not of the same month or From date and To Date is not correct");
                }
            } else if (dtCode.equalsIgnoreCase("1")) {
                String frMon = "", toMon = "";
                List fList = em.createNativeQuery("Select monthname('" + fromDt + "')").getResultList();
                if (fList.size() > 0) {
                    Vector fRep = (Vector) fList.get(0);
                    frMon = fRep.get(0).toString();
                }
                List tList = em.createNativeQuery("Select monthname('" + toDt + "')").getResultList();
                if (tList.size() > 0) {
                    Vector tRep = (Vector) tList.get(0);
                    toMon = tRep.get(0).toString();
                }
                mName = frMon + " - " + toMon;

                if ((fromDt.substring(6, 8).equalsIgnoreCase("01"))) {
                    if (!(toDt.substring(4, 8).equalsIgnoreCase("0331") || (toDt.substring(4, 8).equalsIgnoreCase("0630") || (toDt.substring(4, 8).equalsIgnoreCase("0930") || (toDt.substring(4, 8).equalsIgnoreCase("1231")))))) {
                        //return getErrorMsgListInspection("Dates are not of the Quarter or From date and To Date is not correct");
                        throw new ApplicationException("Dates are not of the Quarter or From date and To Date is not correct");
                    }
                } else {
                    //return getErrorMsgListInspection("Dates are not of the Quarter or From date and To Date is not correct");
                    throw new ApplicationException("Dates are not of the Quarter or From date and To Date is not correct");
                }
            }

//            if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(ymd.parse(fromDt));
//                int fDate = calendar.get(Calendar.MONTH);
//
//                calendar.setTime(ymd.parse(toDt));
//                int tDate = calendar.get(Calendar.MONTH);
//                if (fDate != tDate) {
//                    return getErrorMsgList("To Month Should Be Same as From Month");
//                }
//            }
            List finYear = em.createNativeQuery("select min(F_Year) from yearend Where YearEndFlag='N' and brncode='" + brnCode + "'").getResultList();
            if (finYear.size() > 0) {
                Vector eleType = (Vector) finYear.get(0);
                String finacialYear = eleType.get(0).toString();

                List getFinalYearValue = em.createNativeQuery("select * FROM yearend where yearendflag='N' and f_year= cast('" + finacialYear + "' as unsigned) and brncode='" + brnCode + "'").getResultList();

                if (getFinalYearValue.size() > 0) {
                    Vector finalYearValue = (Vector) getFinalYearValue.get(0);
                    minDate = (String) finalYearValue.get(0);
                    maxDate = (String) finalYearValue.get(1);

                    if (((ymd.parse(fromDt).after(ymd.parse(minDate))) || (ymd.parse(fromDt).equals(ymd.parse(minDate)))) && ((ymd.parse(fromDt).before(ymd.parse(maxDate))) || (ymd.parse(fromDt).equals(ymd.parse(maxDate)))) && ((ymd.parse(toDt).after(ymd.parse(minDate))) || (ymd.parse(toDt).equals(ymd.parse(minDate)))) && ((ymd.parse(toDt).before(ymd.parse(maxDate))) || (ymd.parse(toDt).equals(ymd.parse(maxDate))))) {
                    } else {
                        //return getErrorMsgList("Please Check The Dates Entered");
                        throw new ApplicationException("Please Check The Dates Entered");
                    }
                } else {
                    //return getErrorMsgList("Values does not exists in Financial Year table");
                    throw new ApplicationException("Values does not exists in Financial Year table");
                }
            } else {
                //return getErrorMsgList("Please check the Finalcial Year");
                throw new ApplicationException("Please check the Finalcial Year");
            }

            long monthsDiff = CbsUtil.monthDiff(ymd.parse(fromDt), ymd.parse(toDt));
            if (monthsDiff > 2) {
                //return getErrorMsgList("Minimum balance charges can be calculated for a maximum period of three months at a time");
                throw new ApplicationException("Minimum balance charges can be calculated for a maximum period of three months at a time");
            }

            String monthName = monthEndCheck(fromDt, toDt, brnCode);
            if (!monthName.equalsIgnoreCase("TRUE")) {
                return getErrorMsgList(monthName);
            }
            //Check for minimum balance charges will be deducted or not from inoperative accounts
            //1 = deduction
            //0 = not by default system will not deduct the charges in inoperative accounts
            List inOpList = em.createNativeQuery("Select ifnull(code,0) from parameterinfo_report where reportname='MIN_CHARGE_INOP'").getResultList();
            int inOpChargeFlag = 0;
            if (inOpList.size() > 0) {
                Vector rep = (Vector) inOpList.get(0);
                inOpChargeFlag = Integer.parseInt(rep.get(0).toString());
            }

            //****************************  Code for Minimum Function ************************************************
            Date date = ymd.parse(toDt);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Integer maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            if (month == 1) {
                maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            } else if (month == 0) {
                month = 12;
            }
            String fromDate = fromDt.substring(0, 6) + "01";
            String toDate = toDt.substring(0, 6) + maxDay.toString();

            String acNature = getAcNature(acType);
            List<MinTxnDetails> milBalanceList = new ArrayList<MinTxnDetails>();
            List selectQueryvouch;
            if (inOpChargeFlag == 1) {
                selectQueryvouch = em.createNativeQuery("select distinct am.acno,am.minbal,am.custname,"
                        + " ifnull(am.chequebook,0),am.accstatus, ifnull(cm.MailStateCode,''),am.OpeningDt "
                        + " from accountmaster am, customerid ci, cbs_customer_master_detail cm "
                        + " where am.ACNo = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) "
                        + " and am.accstatus not in (9,15) and "
                        + " substring(am.acno,3,2) = '" + acType + "' and am.openingdt <= '" + toDate + "' "
                        + " and am.CurBrCode='" + brnCode + "'  order by am.acno ").getResultList();
            } else {
                selectQueryvouch = em.createNativeQuery("select distinct am.acno,am.minbal,am.custname,"
                        + " ifnull(am.chequebook,0),am.accstatus, ifnull(cm.MailStateCode,''),am.OpeningDt "
                        + " from accountmaster am, customerid ci, cbs_customer_master_detail cm "
                        + " where am.ACNo = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) "
                        + " and am.accstatus not in (9,2,15) and "
                        + " substring(am.acno,3,2) = '" + acType + "' and am.openingdt <= '" + toDate + "' "
                        + " and am.CurBrCode='" + brnCode + "'  order by am.acno ").getResultList();
            }
            // 0 for single, 1 for multiple and 2 for average balance

            int multiChargeFlag = ftsRemote.getCodeForReportName("MIN_CHARGE_MULTIPLE");
//            List multiTimeList = em.createNativeQuery("Select ifnull(code,0) from parameterinfo_report where reportname='MIN_CHARGE_MULTIPLE'").getResultList();
//            int multiChargeFlag = 0;
//            if (multiTimeList.size() > 0) {
//                Vector mulRep = (Vector) multiTimeList.get(0);
//                multiChargeFlag = Integer.parseInt(mulRep.get(0).toString());
//            }
//            List getFinalYearValueCharge = em.createNativeQuery("select MINBAL,GLHEADMISC,CHARGES,acctnature,MINBAL_CHQ,CHARGES1 from parameterinfo_miscincome P,"
//                    + "accounttypemaster A where P.acctcode='" + acType + "' AND PURPOSE='Min. Bal. Charges' AND P.ACCTCODE=A.ACCTCODE").getResultList();
            List getFinalYearValueCharge = em.createNativeQuery("select MINBAL,GLHEADMISC,CHARGES,acctnature,MINBAL_CHQ,CHARGES1,ifnull(ACCT_MIN_BALANCE,0), ifnull(MIN_BALANCE_SERVICE_CHRG,0) "
                    + " from parameterinfo_miscincome P, accounttypemaster A, cbs_scheme_currency_details c where P.acctcode='" + acType + "' "
                    + " AND PURPOSE='Min. Bal. Charges' AND P.ACCTCODE=A.ACCTCODE and c.scheme_type = A.ACCTCODE").getResultList();
            double minbal = 0d;
            double minCharges = 0d;
            double minbalCh = 0d;
            double minChargesCh = 0d;
            double minBalAtm = 0d;
            double minChargeAtm = 0d;
            if (getFinalYearValueCharge.size() > 0) {
                Vector listDiff = (Vector) getFinalYearValueCharge.get(0);
                String tmpMinbal = listDiff.get(0).toString();
                String tmpMinCharges = listDiff.get(2).toString();

                minbal = Float.parseFloat(tmpMinbal);
                minCharges = Float.parseFloat(tmpMinCharges);

                String tmpMinbalCh = listDiff.get(4).toString();
                String tmpMinChargesCh = listDiff.get(5).toString();
                String tmpMinAtm = listDiff.get(6).toString();
                String tmpMinChargesAtm = listDiff.get(7).toString();

                minbalCh = Float.parseFloat(tmpMinbalCh);
                minChargesCh = Float.parseFloat(tmpMinChargesCh);
                minBalAtm = Float.parseFloat(tmpMinAtm);
                minChargeAtm = Float.parseFloat(tmpMinChargesAtm);
            } else {
                //return getErrorMsgList("There Exists No Data in ParameterInfo_MiscIncome Table");
                throw new ApplicationException("There Exists No Data in ParameterInfo_MiscIncome Table");
            }
            if (multiChargeFlag == 1) {
                for (int j = 0; j < selectQueryvouch.size(); j++) {
                    Vector custvoucherNo = (Vector) selectQueryvouch.get(j);
                    String accountNo = custvoucherNo.get(0).toString();
                    String custStateCode = custvoucherNo.get(5).toString();
                    int minbalChl = Integer.parseInt(custvoucherNo.get(3).toString());
                    int minbalRecall = Integer.parseInt(custvoucherNo.get(1).toString());
                    double previousBal = 0d;

                    String tableName = "";
                    if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        tableName = "recon";
                    }
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        tableName = "ca_recon";
                    }

                    List balanceList1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tableName
                            + " where acno='" + accountNo + "' and date_format(Valuedt,'%Y%m%d')<'" + fromDt + "' AND AUTH='Y'").getResultList();
                    Vector balanceVect1 = (Vector) balanceList1.get(0);
                    previousBal = Double.parseDouble(balanceVect1.get(0).toString());

                    List balList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))- sum(ifnull(dramt,0)),0)  from " + tableName + " where  acno='" + accountNo + "' and date_format(Valuedt,'%Y%m%d')>=date_format('" + fromDt + "','%Y%m%d') "
                            + "AND date_format(Valuedt,'%Y%m%d')<='" + toDate + "' group by dt order by dt").getResultList();

                    List chkAtmExistList = em.createNativeQuery("select acno from atm_card_master where acno='" + accountNo + "' and del_flag in ('G','U')").getResultList();

                    double minba2 = 0d;
                    double minCharges2 = 0d;
                    double charges = 0d;
                    if (chkAtmExistList.isEmpty()) {
                        if (minbalChl == 1) {
                            minba2 = minbalCh;
                            minCharges2 = minChargesCh;
                        } else {
                            minba2 = minbal;
                            minCharges2 = minCharges;
                        }
                    } else {
                        minba2 = minBalAtm;
                        minCharges2 = minChargeAtm;
                    }

                    int cnt = 0;
                    if ((previousBal < minba2) && balList.isEmpty()) {
                        cnt = 1;
                    } else {
                        for (int k = 0; k < balList.size(); k++) {
                            double dailyBal = Double.parseDouble(((Vector) balList.get(k)).get(0).toString());
                            previousBal = previousBal + dailyBal;
                            if ((previousBal < minba2)) {
                                cnt = cnt + 1;
                            }
                        }
                    }

                    charges = cnt * minCharges2;
                    if (charges > 0) {
                        ChargesObject minChar = new ChargesObject();
                        minChar.setBnkName(bnkName);
                        minChar.setBnkAddress(bnkAddress);
                        minChar.setMsg("TRUE");
                        minChar.setStatus(custvoucherNo.get(4).toString());
                        minChar.setAcNo(accountNo);
                        minChar.setCustName(custvoucherNo.get(2).toString());
                        minChar.setMonths(cnt);

                        minChar.setPenalty(charges);
                        minChar.setMonth1(0);
                        minChar.setMonth2(0);
                        minChar.setMonth3(0);
                        minChar.setMinbal(minbalRecall);
                        minChar.setBrState(brState);
                        minChar.setCustState((custStateCode.equalsIgnoreCase("") || custStateCode.equalsIgnoreCase("0")) ? brState : custStateCode);
                        minChar.setMonName(mName);

                        minimumChargesList.add(minChar);
                        charges = 0;
                    }
                }
            } else if (multiChargeFlag == 2) {
                for (int j = 0; j < selectQueryvouch.size(); j++) {
                    Vector custvoucherNo = (Vector) selectQueryvouch.get(j);
                    String accountNo = custvoucherNo.get(0).toString();
                    String custStateCode = custvoucherNo.get(5).toString();
                    String openingDt = custvoucherNo.get(6).toString();
                    int minbalChl = Integer.parseInt(custvoucherNo.get(3).toString());
                    int minbalRecall = Integer.parseInt(custvoucherNo.get(1).toString());
                    double avgBal = 0d;

                    String tableName = "";
                    if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        tableName = "recon";
                    }

                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        tableName = "ca_recon";
                    }

                    String frDt = fromDt;
                    if (CbsUtil.dayDiff(ymd.parse(openingDt), ymd.parse(fromDt)) < 0) {
                        frDt = openingDt;
                    }

                    List balanceList1 = em.createNativeQuery("select avg(a.bal) as bal from "
                            + "(select v.selected_date, (select cast(ifnull(sum(cramt-dramt),0) as decimal(15,2))  from " + tableName + " where acno = '"
                            + accountNo + "' and dt<=v.selected_date ) as bal from (select date as selected_date from cbs_bankdays where date between '"
                            + frDt + "' and '" + toDate + "') v where v.selected_date between '" + frDt + "' and '" + toDate + "' )a").getResultList();
                    Vector balanceVect1 = (Vector) balanceList1.get(0);
                    avgBal = Double.parseDouble(balanceVect1.get(0).toString());

                    List chkAtmExistList = em.createNativeQuery("select acno from atm_card_master where acno='" + accountNo + "' and del_flag in ('G','U')").getResultList();

                    double minba2 = 0d;
                    double minCharges2 = 0d;
                    double charges = 0d;

                    if (chkAtmExistList.isEmpty()) {
                        if (minbalChl == 1) {
                            minba2 = minbalCh;
                            minCharges2 = minChargesCh;
                        } else {
                            minba2 = minbal;
                            minCharges2 = minCharges;
                        }
                    } else {
                        minba2 = minBalAtm;
                        minCharges2 = minChargeAtm;
                    }

                    if ((avgBal < minba2)) {
                        charges = minCharges2;
                    }

                    if (charges > 0) {
                        ChargesObject minChar = new ChargesObject();
                        minChar.setBnkName(bnkName);
                        minChar.setBnkAddress(bnkAddress);
                        minChar.setMsg("TRUE");
                        minChar.setStatus(custvoucherNo.get(4).toString());
                        minChar.setAcNo(accountNo);
                        minChar.setCustName(custvoucherNo.get(2).toString());
                        minChar.setMonths(1);

                        minChar.setPenalty(charges);
                        minChar.setMonth1(avgBal);
                        minChar.setMonth2(0);
                        minChar.setMonth3(0);
                        minChar.setMinbal(minbalRecall);
                        minChar.setBrState(brState);
                        minChar.setCustState((custStateCode.equalsIgnoreCase("") || custStateCode.equalsIgnoreCase("0")) ? brState : custStateCode);
                        minChar.setMonName(mName);

                        minimumChargesList.add(minChar);
                        charges = 0;
                    }
                }
            } else {
                for (int j = 0; j < selectQueryvouch.size(); j++) {
                    Vector custvoucherNo = (Vector) selectQueryvouch.get(j);
                    String accountNo = custvoucherNo.get(0).toString();
                    double previousBal = 0d;
                    double c = 0d;
                    String tableName = "";
                    if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        tableName = "recon";
                    }
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        tableName = "ca_recon";
                    }
                    List balanceList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))- sum(ifnull(dramt,0)),0) from " + tableName + ""
                            + " where acno='" + accountNo + "' and date_format(Valuedt,'%Y%m%d')< '" + fromDate + "' AND AUTH='Y'").getResultList();
                    Vector balanceVect = (Vector) balanceList.get(0);
                    previousBal = Double.parseDouble(balanceVect.get(0).toString());

                    List balanceList1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tableName
                            + " where acno='" + accountNo + "' and date_format(Valuedt,'%Y%m%d')<='" + fromDt + "' AND AUTH='Y'").getResultList();
                    Vector balanceVect1 = (Vector) balanceList1.get(0);
                    c = Double.parseDouble(balanceVect1.get(0).toString());

                    List balList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))- sum(ifnull(dramt,0)),0)  from " + tableName + " where  acno='" + accountNo + "' and date_format(Valuedt,'%Y%m%d')>=date_format('" + fromDt + "','%Y%m%d') "
                            + "AND date_format(Valuedt,'%Y%m%d')<='" + toDate + "' group by dt order by dt").getResultList();
                    if (!balList.isEmpty()) {
                        for (int k = 0; k < balList.size(); k++) {
                            double dailyBal = Double.parseDouble(((Vector) balList.get(k)).get(0).toString());
                            previousBal = previousBal + dailyBal;
                            if ((previousBal < c) || (c == 0)) {
                                c = CbsUtil.round(previousBal, 2);
                            }
                        }
                    }
                    MinTxnDetails tempMin = new MinTxnDetails();
                    tempMin.setAcNo(accountNo);
                    tempMin.setBalance(c);
                    milBalanceList.add(tempMin);
                }

                List getFinalYearCharge = em.createNativeQuery("select am.acno,am.minbal,am.custname,ifnull(am.chequebook,0),am.accstatus, "
                        + "ifnull(cm.MailStateCode,'') from accountmaster am, customerid ci, cbs_customer_master_detail cm where am.ACNo = ci.Acno and "
                        + "ci.CustId = cast(cm.customerid as unsigned) and am.accstatus<>9 and substring(am.acno,3,2)='" + acType + "' and am.CurBrCode='"
                        + brnCode + "'").getResultList();

                for (int k = 0; k < getFinalYearCharge.size(); k++) {
                    Vector custacno = (Vector) getFinalYearCharge.get(k);
                    String acctNo = custacno.get(0).toString();
                    String custStateCode = custacno.get(5).toString();
                    int minbalChl = Integer.parseInt(custacno.get(3).toString());
                    int minbalRecall = Integer.parseInt(custacno.get(1).toString());
                    int l = 0;
                    int r = 0;
                    double bal1 = 0d;
                    double bal2 = 0d;
                    double bal3 = 0d;
                    double bal = 0d;
                    double charges = 0d;
                    // while (r <= monthsDiff) {
                    bal = getBal(milBalanceList, acctNo);
                    bal = CbsUtil.round(bal, 2);
                    if (bal != 0) {
                        double minba2 = 0d;
                        double minCharges2 = 0d;
                        List chkAtmExistList = em.createNativeQuery("select acno from atm_card_master where acno='" + acctNo + "' and del_flag in ('G','U')").getResultList();
                        if (chkAtmExistList.isEmpty()) {
                            if (minbalChl == 1) {
                                minba2 = minbalCh;
                                minCharges2 = minChargesCh;
                            } else {
                                minba2 = minbal;
                                minCharges2 = minCharges;
                            }
                        } else {
                            minba2 = minBalAtm;
                            minCharges2 = minChargeAtm;
                        }

                        if (bal < minba2) {
                            l = l + 1;
                            charges = l * minCharges2;
                        }
                    }
                    r = r + 1;
                    if (r == 1) {
                        bal1 = bal;
                    } else if (r == 2) {
                        bal2 = bal;
                    } else if (r == 3) {
                        bal3 = bal;
                    }
                    if (charges > 0) {
                        ChargesObject minChar = new ChargesObject();
                        minChar.setBnkName(bnkName);
                        minChar.setBnkAddress(bnkAddress);
                        minChar.setMsg("TRUE");
                        minChar.setStatus(custacno.get(4).toString());
                        minChar.setAcNo(acctNo);
                        minChar.setCustName(custacno.get(2).toString());
                        minChar.setMonths(l);

                        minChar.setPenalty(charges);
                        minChar.setMonth1(bal1);
                        minChar.setMonth2(bal2);
                        minChar.setMonth3(bal3);
                        minChar.setMinbal(minbalRecall);
                        minChar.setBrState(brState);
                        minChar.setCustState((custStateCode.equalsIgnoreCase("") || custStateCode.equalsIgnoreCase("0")) ? brState : custStateCode);
                        minChar.setMonName(mName);

                        minimumChargesList.add(minChar);
                        charges = 0;

                    }
                }
            }
            return minimumChargesList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List accountTypeDropDownMinimumBal() throws ApplicationException {
        List accountype = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct P.acctcode from parameterinfo_miscincome P,accounttypemaster A where P.acctcode = A.acctcode and PURPOSE='Min. Bal. Charges' AND P.ACCTCODE=A.ACCTCODE");
            accountype = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return accountype;
    }

    public List<ChargesObject> minBalanceChargesCaculateAllBranch(String acType, String fromDt, String toDt, String brnCode, String enterBy, String enteryDt) throws ApplicationException {
        String minDate;
        String maxDate;
        String dtCode = "0";
        String mName = "";
        List<ChargesObject> minimumChargesList = new ArrayList<ChargesObject>();
        try {
            List brnList;
            String brCode = "";
            if (brnCode.equalsIgnoreCase("0A") || brnCode.equalsIgnoreCase("90")) {
                brnList = em.createNativeQuery("select case CHAR_LENGTH(BrnCode) when 1 then concat('0',BrnCode) else BrnCode end as BrnCode from branchmaster where (closedate is null or closedate='' or closedate>'" + toDt + "' ) order by BrnCode ").getResultList();
            } else {
                brnList = em.createNativeQuery("select case CHAR_LENGTH(BrnCode) when 1 then concat('0',BrnCode) else BrnCode end as BrnCode from branchmaster where BrnCode ='" + brnCode + "' and (closedate is null or closedate='' or closedate>'" + toDt + "') order by BrnCode ").getResultList();
            }

            for (int z = 0; z < brnList.size(); z++) {
                Vector brnVect = (Vector) brnList.get(z);
                brCode = brnVect.get(0).toString();
                brnCode = brCode;
                List resultList = accountTypeDropDownMinimumBal();
                if (resultList.isEmpty()) {
                    throw new ApplicationException("Account Type Problem");
                }
                for (int y = 0; y < resultList.size(); y++) {
                    Vector acVec = (Vector) resultList.get(y);
                    acType = acVec.get(0).toString();


                    Object bnkNameObj = em.createNativeQuery("select b.bankname,b.bankaddress,State from bnkadd b,branchmaster br where "
                            + "b.alphacode=br.alphacode and br.brncode=cast('" + brnCode + "' as unsigned)").getSingleResult();

                    String bnkName = ((Vector) bnkNameObj).elementAt(0).toString();
                    String bnkAddress = ((Vector) bnkNameObj).elementAt(1).toString();
                    String brState = ((Vector) bnkNameObj).elementAt(2).toString();

                    long days = CbsUtil.dayDiff(ymd.parse(fromDt), ymd.parse(toDt));
                    if (days < 0) {
                        //return getErrorMsgList("Please check the dates entered ");
                        throw new ApplicationException("Please check the dates entered");
                    }
                    String acctNature = getAcNature(acType);
                    List datecode = new ArrayList();

                    if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        datecode = em.createNativeQuery("Select code from parameterinfo_report where reportname='MIN_CHARGE_CA'").getResultList();
                        if (datecode.size() > 0) {
                            Vector rep = (Vector) datecode.get(0);
                            dtCode = rep.get(0).toString();
                        } else {
                            //return getErrorMsgListInspection("There Exists No Data in parameterinfo_report Table");
                            throw new ApplicationException("No Data Exists in parameterinfo_report Table");
                        }
                    } else if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        datecode = em.createNativeQuery("Select code from parameterinfo_report where reportname='MIN_CHARGE_SB'").getResultList();
                        if (datecode.size() > 0) {
                            Vector rep = (Vector) datecode.get(0);
                            dtCode = rep.get(0).toString();
                        } else {
                            //return getErrorMsgListInspection("There Exists No Data in parameterinfo_report Table");
                            throw new ApplicationException("No Data Exists in parameterinfo_report Table");
                        }
                    } else {
                        //return getErrorMsgListInspection("Please check the Code " + acType);
                        throw new ApplicationException("Please check the Code " + acType);
                    }

                    if (dtCode.equalsIgnoreCase("0")) {
                        List mList = em.createNativeQuery("Select monthname('" + fromDt + "')").getResultList();
                        if (mList.size() > 0) {
                            Vector mRep = (Vector) mList.get(0);
                            mName = mRep.get(0).toString();
                        }

                        if (fromDt.substring(4, 6).equalsIgnoreCase(toDt.substring(4, 6)) && (fromDt.substring(6, 8).equalsIgnoreCase("01"))) {
                            if (!fromDt.substring(0, 5).equalsIgnoreCase(toDt.substring(0, 5))) {
                                //return getErrorMsgListInspection("Dates are not of the same month or From date and To Date is not correct");
                                throw new ApplicationException("Dates are not of the same month or From date and To Date is not correct");
                            }
                        } else {
                            //return getErrorMsgListInspection("Dates are not of the same month or From date and To Date is not correct");
                            throw new ApplicationException("Dates are not of the same month or From date and To Date is not correct");
                        }
                    } else if (dtCode.equalsIgnoreCase("1")) {
                        String frMon = "", toMon = "";
                        List fList = em.createNativeQuery("Select monthname('" + fromDt + "')").getResultList();
                        if (fList.size() > 0) {
                            Vector fRep = (Vector) fList.get(0);
                            frMon = fRep.get(0).toString();
                        }
                        List tList = em.createNativeQuery("Select monthname('" + toDt + "')").getResultList();
                        if (tList.size() > 0) {
                            Vector tRep = (Vector) tList.get(0);
                            toMon = tRep.get(0).toString();
                        }
                        mName = frMon + " - " + toMon;

                        if ((fromDt.substring(6, 8).equalsIgnoreCase("01"))) {
                            if (!(toDt.substring(4, 8).equalsIgnoreCase("0331") || (toDt.substring(4, 8).equalsIgnoreCase("0630") || (toDt.substring(4, 8).equalsIgnoreCase("0930") || (toDt.substring(4, 8).equalsIgnoreCase("1231")))))) {
                                //return getErrorMsgListInspection("Dates are not of the Quarter or From date and To Date is not correct");
                                throw new ApplicationException("Dates are not of the Quarter or From date and To Date is not correct");
                            }
                        } else {
                            //return getErrorMsgListInspection("Dates are not of the Quarter or From date and To Date is not correct");
                            throw new ApplicationException("Dates are not of the Quarter or From date and To Date is not correct");
                        }
                    }

//            if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(ymd.parse(fromDt));
//                int fDate = calendar.get(Calendar.MONTH);
//
//                calendar.setTime(ymd.parse(toDt));
//                int tDate = calendar.get(Calendar.MONTH);
//                if (fDate != tDate) {
//                    return getErrorMsgList("To Month Should Be Same as From Month");
//                }
//            }
                    List finYear = em.createNativeQuery("select min(F_Year) from yearend Where YearEndFlag='N' and brncode='" + brnCode + "'").getResultList();
                    if (finYear.size() > 0) {
                        Vector eleType = (Vector) finYear.get(0);
                        String finacialYear = eleType.get(0).toString();

                        List getFinalYearValue = em.createNativeQuery("select * FROM yearend where yearendflag='N' and f_year= cast('" + finacialYear + "' as unsigned) and brncode='" + brnCode + "'").getResultList();

                        if (getFinalYearValue.size() > 0) {
                            Vector finalYearValue = (Vector) getFinalYearValue.get(0);
                            minDate = (String) finalYearValue.get(0);
                            maxDate = (String) finalYearValue.get(1);

                            if (((ymd.parse(fromDt).after(ymd.parse(minDate))) || (ymd.parse(fromDt).equals(ymd.parse(minDate)))) && ((ymd.parse(fromDt).before(ymd.parse(maxDate))) || (ymd.parse(fromDt).equals(ymd.parse(maxDate)))) && ((ymd.parse(toDt).after(ymd.parse(minDate))) || (ymd.parse(toDt).equals(ymd.parse(minDate)))) && ((ymd.parse(toDt).before(ymd.parse(maxDate))) || (ymd.parse(toDt).equals(ymd.parse(maxDate))))) {
                            } else {
                                //return getErrorMsgList("Please Check The Dates Entered");
                                throw new ApplicationException("Please Check The Dates Entered");
                            }
                        } else {
                            //return getErrorMsgList("Values does not exists in Financial Year table");
                            throw new ApplicationException("Values does not exists in Financial Year table");
                        }
                    } else {
                        //return getErrorMsgList("Please check the Finalcial Year");
                        throw new ApplicationException("Please check the Finalcial Year");
                    }

                    long monthsDiff = CbsUtil.monthDiff(ymd.parse(fromDt), ymd.parse(toDt));
                    if (monthsDiff > 2) {
                        //return getErrorMsgList("Minimum balance charges can be calculated for a maximum period of three months at a time");
                        throw new ApplicationException("Minimum balance charges can be calculated for a maximum period of three months at a time");
                    }

                    String monthName = monthEndCheck(fromDt, toDt, brnCode);
                    if (!monthName.equalsIgnoreCase("TRUE")) {
                        return getErrorMsgList(monthName);
                    }
                    //Check for minimum balance charges will be deducted or not from inoperative accounts
                    //1 = deduction
                    //0 = not by default system will not deduct the charges in inoperative accounts
                    List inOpList = em.createNativeQuery("Select ifnull(code,0) from parameterinfo_report where reportname='MIN_CHARGE_INOP'").getResultList();
                    int inOpChargeFlag = 0;
                    if (inOpList.size() > 0) {
                        Vector rep = (Vector) inOpList.get(0);
                        inOpChargeFlag = Integer.parseInt(rep.get(0).toString());
                    }

                    //****************************  Code for Minimum Function ************************************************
                    Date date = ymd.parse(toDt);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    Integer maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    if (month == 1) {
                        maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    } else if (month == 0) {
                        month = 12;
                    }
                    String fromDate = fromDt.substring(0, 6) + "01";
                    String toDate = toDt.substring(0, 6) + maxDay.toString();

                    String acNature = getAcNature(acType);
                    List<MinTxnDetails> milBalanceList = new ArrayList<MinTxnDetails>();
                    List selectQueryvouch;
                    if (inOpChargeFlag == 1) {
                        selectQueryvouch = em.createNativeQuery("select distinct am.acno,am.minbal,am.custname,"
                                + " ifnull(am.chequebook,0),am.accstatus, ifnull(cm.MailStateCode,''),am.OpeningDt "
                                + " from accountmaster am, customerid ci, cbs_customer_master_detail cm "
                                + " where am.ACNo = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) "
                                + " and am.accstatus not in (9,15) and "
                                + " substring(am.acno,3,2) = '" + acType + "' and am.openingdt <= '" + toDate + "' "
                                + " and am.CurBrCode='" + brnCode + "'  order by am.acno ").getResultList();
                    } else {
                        selectQueryvouch = em.createNativeQuery("select distinct am.acno,am.minbal,am.custname,"
                                + " ifnull(am.chequebook,0),am.accstatus, ifnull(cm.MailStateCode,''),am.OpeningDt "
                                + " from accountmaster am, customerid ci, cbs_customer_master_detail cm "
                                + " where am.ACNo = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) "
                                + " and am.accstatus not in (9,2,15) and "
                                + " substring(am.acno,3,2) = '" + acType + "' and am.openingdt <= '" + toDate + "' "
                                + " and am.CurBrCode='" + brnCode + "'  order by am.acno ").getResultList();
                    }
                    // 0 for single, 1 for multiple and 2 for average balance

                    int multiChargeFlag = ftsRemote.getCodeForReportName("MIN_CHARGE_MULTIPLE");
//            List multiTimeList = em.createNativeQuery("Select ifnull(code,0) from parameterinfo_report where reportname='MIN_CHARGE_MULTIPLE'").getResultList();
//            int multiChargeFlag = 0;
//            if (multiTimeList.size() > 0) {
//                Vector mulRep = (Vector) multiTimeList.get(0);
//                multiChargeFlag = Integer.parseInt(mulRep.get(0).toString());
//            }
//            List getFinalYearValueCharge = em.createNativeQuery("select MINBAL,GLHEADMISC,CHARGES,acctnature,MINBAL_CHQ,CHARGES1 from parameterinfo_miscincome P,"
//                    + "accounttypemaster A where P.acctcode='" + acType + "' AND PURPOSE='Min. Bal. Charges' AND P.ACCTCODE=A.ACCTCODE").getResultList();
                    List getFinalYearValueCharge = em.createNativeQuery("select MINBAL,GLHEADMISC,CHARGES,acctnature,MINBAL_CHQ,CHARGES1,ifnull(ACCT_MIN_BALANCE,0), ifnull(MIN_BALANCE_SERVICE_CHRG,0) "
                            + " from parameterinfo_miscincome P, accounttypemaster A, cbs_scheme_currency_details c where P.acctcode='" + acType + "' "
                            + " AND PURPOSE='Min. Bal. Charges' AND P.ACCTCODE=A.ACCTCODE and c.scheme_type = A.ACCTCODE").getResultList();
                    double minbal = 0d;
                    double minCharges = 0d;
                    double minbalCh = 0d;
                    double minChargesCh = 0d;
                    double minBalAtm = 0d;
                    double minChargeAtm = 0d;
                    if (getFinalYearValueCharge.size() > 0) {
                        Vector listDiff = (Vector) getFinalYearValueCharge.get(0);
                        String tmpMinbal = listDiff.get(0).toString();
                        String tmpMinCharges = listDiff.get(2).toString();

                        minbal = Float.parseFloat(tmpMinbal);
                        minCharges = Float.parseFloat(tmpMinCharges);

                        String tmpMinbalCh = listDiff.get(4).toString();
                        String tmpMinChargesCh = listDiff.get(5).toString();
                        String tmpMinAtm = listDiff.get(6).toString();
                        String tmpMinChargesAtm = listDiff.get(7).toString();

                        minbalCh = Float.parseFloat(tmpMinbalCh);
                        minChargesCh = Float.parseFloat(tmpMinChargesCh);
                        minBalAtm = Float.parseFloat(tmpMinAtm);
                        minChargeAtm = Float.parseFloat(tmpMinChargesAtm);
                    } else {
                        //return getErrorMsgList("There Exists No Data in ParameterInfo_MiscIncome Table");
                        throw new ApplicationException("There Exists No Data in ParameterInfo_MiscIncome Table");
                    }
                    if (multiChargeFlag == 1) {
                        for (int j = 0; j < selectQueryvouch.size(); j++) {
                            Vector custvoucherNo = (Vector) selectQueryvouch.get(j);
                            String accountNo = custvoucherNo.get(0).toString();
                            String custStateCode = custvoucherNo.get(5).toString();
                            int minbalChl = Integer.parseInt(custvoucherNo.get(3).toString());
                            int minbalRecall = Integer.parseInt(custvoucherNo.get(1).toString());
                            double previousBal = 0d;

                            String tableName = "";
                            if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                tableName = "recon";
                            }
                            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                tableName = "ca_recon";
                            }

                            List balanceList1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tableName
                                    + " where acno='" + accountNo + "' and date_format(Valuedt,'%Y%m%d')<'" + fromDt + "' AND AUTH='Y'").getResultList();
                            Vector balanceVect1 = (Vector) balanceList1.get(0);
                            previousBal = Double.parseDouble(balanceVect1.get(0).toString());

                            List balList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))- sum(ifnull(dramt,0)),0)  from " + tableName + " where  acno='" + accountNo + "' and date_format(Valuedt,'%Y%m%d')>=date_format('" + fromDt + "','%Y%m%d') "
                                    + "AND date_format(Valuedt,'%Y%m%d')<='" + toDate + "' group by dt order by dt").getResultList();

                            List chkAtmExistList = em.createNativeQuery("select acno from atm_card_master where acno='" + accountNo + "' and del_flag in ('G','U')").getResultList();

                            double minba2 = 0d;
                            double minCharges2 = 0d;
                            double charges = 0d;
                            if (chkAtmExistList.isEmpty()) {
                                if (minbalChl == 1) {
                                    minba2 = minbalCh;
                                    minCharges2 = minChargesCh;
                                } else {
                                    minba2 = minbal;
                                    minCharges2 = minCharges;
                                }
                            } else {
                                minba2 = minBalAtm;
                                minCharges2 = minChargeAtm;
                            }

                            int cnt = 0;
                            if ((previousBal < minba2) && balList.isEmpty()) {
                                cnt = 1;
                            } else {
                                for (int k = 0; k < balList.size(); k++) {
                                    double dailyBal = Double.parseDouble(((Vector) balList.get(k)).get(0).toString());
                                    previousBal = previousBal + dailyBal;
                                    if ((previousBal < minba2)) {
                                        cnt = cnt + 1;
                                    }
                                }
                            }

                            charges = cnt * minCharges2;
                            if (charges > 0) {
                                ChargesObject minChar = new ChargesObject();
                                minChar.setBnkName(bnkName);
                                minChar.setBnkAddress(bnkAddress);
                                minChar.setMsg("TRUE");
                                minChar.setStatus(custvoucherNo.get(4).toString());
                                minChar.setAcNo(accountNo);
                                minChar.setCustName(custvoucherNo.get(2).toString());
                                minChar.setMonths(cnt);

                                minChar.setPenalty(charges);
                                minChar.setMonth1(0);
                                minChar.setMonth2(0);
                                minChar.setMonth3(0);
                                minChar.setMinbal(minbalRecall);
                                minChar.setBrState(brState);
                                minChar.setCustState((custStateCode.equalsIgnoreCase("") || custStateCode.equalsIgnoreCase("0")) ? brState : custStateCode);
                                minChar.setMonName(mName);
                                minChar.setBranchCode(brCode);
                                minChar.setAccType(acType);

                                minimumChargesList.add(minChar);
                                charges = 0;
                            }
                        }
                    } else if (multiChargeFlag == 2) {
                        for (int j = 0; j < selectQueryvouch.size(); j++) {
                            Vector custvoucherNo = (Vector) selectQueryvouch.get(j);
                            String accountNo = custvoucherNo.get(0).toString();
                            String custStateCode = custvoucherNo.get(5).toString();
                            String openingDt = custvoucherNo.get(6).toString();
                            int minbalChl = Integer.parseInt(custvoucherNo.get(3).toString());
                            int minbalRecall = Integer.parseInt(custvoucherNo.get(1).toString());
                            double avgBal = 0d;

                            String tableName = "";
                            if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                tableName = "recon";
                            }

                            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                tableName = "ca_recon";
                            }

                            String frDt = fromDt;
                            if (CbsUtil.dayDiff(ymd.parse(openingDt), ymd.parse(fromDt)) < 0) {
                                frDt = openingDt;
                            }

                            List balanceList1 = em.createNativeQuery("select avg(a.bal) as bal from "
                                    + "(select v.selected_date, (select cast(ifnull(sum(cramt-dramt),0) as decimal(15,2))  from " + tableName + " where acno = '"
                                    + accountNo + "' and dt<=v.selected_date ) as bal from (select date as selected_date from cbs_bankdays where date between '"
                                    + frDt + "' and '" + toDate + "') v where v.selected_date between '" + frDt + "' and '" + toDate + "' )a").getResultList();
                            Vector balanceVect1 = (Vector) balanceList1.get(0);
                            avgBal = Double.parseDouble(balanceVect1.get(0).toString());

                            List chkAtmExistList = em.createNativeQuery("select acno from atm_card_master where acno='" + accountNo + "' and del_flag in ('G','U')").getResultList();

                            double minba2 = 0d;
                            double minCharges2 = 0d;
                            double charges = 0d;

                            if (chkAtmExistList.isEmpty()) {
                                if (minbalChl == 1) {
                                    minba2 = minbalCh;
                                    minCharges2 = minChargesCh;
                                } else {
                                    minba2 = minbal;
                                    minCharges2 = minCharges;
                                }
                            } else {
                                minba2 = minBalAtm;
                                minCharges2 = minChargeAtm;
                            }

                            if ((avgBal < minba2)) {
                                charges = minCharges2;
                            }

                            if (charges > 0) {
                                ChargesObject minChar = new ChargesObject();
                                minChar.setBnkName(bnkName);
                                minChar.setBnkAddress(bnkAddress);
                                minChar.setMsg("TRUE");
                                minChar.setStatus(custvoucherNo.get(4).toString());
                                minChar.setAcNo(accountNo);
                                minChar.setCustName(custvoucherNo.get(2).toString());
                                minChar.setMonths(1);

                                minChar.setPenalty(charges);
                                minChar.setMonth1(avgBal);
                                minChar.setMonth2(0);
                                minChar.setMonth3(0);
                                minChar.setMinbal(minbalRecall);
                                minChar.setBrState(brState);
                                minChar.setCustState((custStateCode.equalsIgnoreCase("") || custStateCode.equalsIgnoreCase("0")) ? brState : custStateCode);
                                minChar.setMonName(mName);
                                minChar.setBranchCode(brCode);
                                minChar.setAccType(acType);

                                minimumChargesList.add(minChar);
                                charges = 0;
                            }
                        }
                    } else {
                        for (int j = 0; j < selectQueryvouch.size(); j++) {
                            Vector custvoucherNo = (Vector) selectQueryvouch.get(j);
                            String accountNo = custvoucherNo.get(0).toString();
                            double previousBal = 0d;
                            double c = 0d;
                            String tableName = "";
                            if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                tableName = "recon";
                            }
                            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                tableName = "ca_recon";
                            }
                            List balanceList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))- sum(ifnull(dramt,0)),0) from " + tableName + ""
                                    + " where acno='" + accountNo + "' and date_format(Valuedt,'%Y%m%d')< '" + fromDate + "' AND AUTH='Y'").getResultList();
                            Vector balanceVect = (Vector) balanceList.get(0);
                            previousBal = Double.parseDouble(balanceVect.get(0).toString());

                            List balanceList1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0) from " + tableName
                                    + " where acno='" + accountNo + "' and date_format(Valuedt,'%Y%m%d')<='" + fromDt + "' AND AUTH='Y'").getResultList();
                            Vector balanceVect1 = (Vector) balanceList1.get(0);
                            c = Double.parseDouble(balanceVect1.get(0).toString());

                            List balList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))- sum(ifnull(dramt,0)),0)  from " + tableName + " where  acno='" + accountNo + "' and date_format(Valuedt,'%Y%m%d')>=date_format('" + fromDt + "','%Y%m%d') "
                                    + "AND date_format(Valuedt,'%Y%m%d')<='" + toDate + "' group by dt order by dt").getResultList();
                            if (!balList.isEmpty()) {
                                for (int k = 0; k < balList.size(); k++) {
                                    double dailyBal = Double.parseDouble(((Vector) balList.get(k)).get(0).toString());
                                    previousBal = previousBal + dailyBal;
                                    if ((previousBal < c) || (c == 0)) {
                                        c = CbsUtil.round(previousBal, 2);
                                    }
                                }
                            }
                            MinTxnDetails tempMin = new MinTxnDetails();
                            tempMin.setAcNo(accountNo);
                            tempMin.setBalance(c);
                            milBalanceList.add(tempMin);
                        }

                        List getFinalYearCharge = em.createNativeQuery("select am.acno,am.minbal,am.custname,ifnull(am.chequebook,0),am.accstatus, "
                                + "ifnull(cm.MailStateCode,'') from accountmaster am, customerid ci, cbs_customer_master_detail cm where am.ACNo = ci.Acno and "
                                + "ci.CustId = cast(cm.customerid as unsigned) and am.accstatus<>9 and substring(am.acno,3,2)='" + acType + "' and am.CurBrCode='"
                                + brnCode + "'").getResultList();

                        for (int k = 0; k < getFinalYearCharge.size(); k++) {
                            Vector custacno = (Vector) getFinalYearCharge.get(k);
                            String acctNo = custacno.get(0).toString();
                            String custStateCode = custacno.get(5).toString();
                            int minbalChl = Integer.parseInt(custacno.get(3).toString());
                            int minbalRecall = Integer.parseInt(custacno.get(1).toString());
                            int l = 0;
                            int r = 0;
                            double bal1 = 0d;
                            double bal2 = 0d;
                            double bal3 = 0d;
                            double bal = 0d;
                            double charges = 0d;
                            // while (r <= monthsDiff) {
                            bal = getBal(milBalanceList, acctNo);
                            bal = CbsUtil.round(bal, 2);
                            if (bal != 0) {
                                double minba2 = 0d;
                                double minCharges2 = 0d;
                                List chkAtmExistList = em.createNativeQuery("select acno from atm_card_master where acno='" + acctNo + "' and del_flag in ('G','U')").getResultList();
                                if (chkAtmExistList.isEmpty()) {
                                    if (minbalChl == 1) {
                                        minba2 = minbalCh;
                                        minCharges2 = minChargesCh;
                                    } else {
                                        minba2 = minbal;
                                        minCharges2 = minCharges;
                                    }
                                } else {
                                    minba2 = minBalAtm;
                                    minCharges2 = minChargeAtm;
                                }

                                if (bal < minba2) {
                                    l = l + 1;
                                    charges = l * minCharges2;
                                }
                            }
                            r = r + 1;
                            if (r == 1) {
                                bal1 = bal;
                            } else if (r == 2) {
                                bal2 = bal;
                            } else if (r == 3) {
                                bal3 = bal;
                            }
                            if (charges > 0) {
                                ChargesObject minChar = new ChargesObject();
                                minChar.setBnkName(bnkName);
                                minChar.setBnkAddress(bnkAddress);
                                minChar.setMsg("TRUE");
                                minChar.setStatus(custacno.get(4).toString());
                                minChar.setAcNo(acctNo);
                                minChar.setCustName(custacno.get(2).toString());
                                minChar.setMonths(l);

                                minChar.setPenalty(charges);
                                minChar.setMonth1(bal1);
                                minChar.setMonth2(bal2);
                                minChar.setMonth3(bal3);
                                minChar.setMinbal(minbalRecall);
                                minChar.setBrState(brState);
                                minChar.setCustState((custStateCode.equalsIgnoreCase("") || custStateCode.equalsIgnoreCase("0")) ? brState : custStateCode);
                                minChar.setMonName(mName);
                                minChar.setBranchCode(brCode);
                                minChar.setAccType(acType);

                                minimumChargesList.add(minChar);
                                charges = 0;

                            }
                        }
                    }
                }
            }
            return minimumChargesList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List setAcToCredit(String acType) throws ApplicationException {
        List acToCredit = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select minbal,glheadmisc,charges,acctnature from parameterinfo_miscincome P,accounttypemaster A "
                    + "where P.acctcode='" + acType + "' AND PURPOSE='Min. Bal. Charges' AND P.ACCTCODE=A.ACCTCODE");
            acToCredit = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acToCredit;
    }

    public List setAcToCreditAll(String acType) throws ApplicationException {
        List acToCredit = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct glheadmisc from parameterinfo_miscincome P,accounttypemaster A where PURPOSE='Min. Bal. Charges' AND P.ACCTCODE=A.ACCTCODE");
            acToCredit = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acToCredit;
    }

    public String minBalanceChargesPost(List<ChargesObject> chargesList, String acType, String glAcNo,
            String authBy, String orgnBrCode, double total, String curDate, String fromDt,
            String toDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        float trSNo = 0;
        String mNameDtl = "";
        try {
            ut.begin();
            List<ChargesObject> newChargesList = new ArrayList<ChargesObject>();
            if (!(chargesList.size() > 0 && chargesList.get(0).getMsg().equals("TRUE"))) {
                throw new ApplicationException(chargesList.get(0).getMsg());
            }
            for (ChargesObject chargesObj : chargesList) {
                if (chargesObj.getMinbal() != 2) {
                    newChargesList.add(chargesObj);
                }
                mNameDtl = chargesObj.getMonName();
            }
            String tempBd = daybeginDate(orgnBrCode);
            String tmpDetails = "MIN. BALANCE CHG For The Month " + mNameDtl;
            List postHistoryList = em.createNativeQuery("select date_format(fromdate,'%Y%m%d'),"
                    + "date_format(todate,'%Y%m%d') from post_history where PostFlag = 'MB' and "
                    + "actype = '" + acType + "' and brncode = '" + orgnBrCode + "'").getResultList();
            if (postHistoryList.size() > 0) {
                for (int i = 0; i < postHistoryList.size(); i++) {
                    Vector postHistoryVector = (Vector) postHistoryList.get(i);
                    Long fromDate = Long.parseLong(postHistoryVector.get(0).toString());
                    Long toDate = Long.parseLong(postHistoryVector.get(1).toString());
                    Long userFromDate = Long.parseLong(fromDt);
                    if ((userFromDate.compareTo(fromDate) > 0 || userFromDate.compareTo(fromDate) == 0)
                            && userFromDate.compareTo(toDate) < 0) {
                        throw new ApplicationException("Minimum balance charges has been already posted.");
                    }
                    if (userFromDate.compareTo(toDate) < 0 || userFromDate.compareTo(toDate) == 0) {
                        throw new ApplicationException("Minimum balance charges has been already posted.");
                    }
                }
            }
            Query insertPostHistory = em.createNativeQuery("insert into post_history values('MB','" + acType + "',"
                    + "'" + toDt + "','" + fromDt + "','" + authBy + "','Y',CURRENT_TIMESTAMP,'" + authBy + "',"
                    + "'" + orgnBrCode + "')");
            int resultPostHistory = insertPostHistory.executeUpdate();
            if (resultPostHistory <= 0) {
                throw new ApplicationException("Problem in posting the hisory of record.");
            }
            List list = em.createNativeQuery("Select ifnull(code,0),reportname from parameterinfo_report where "
                    + "reportname in ('STAXMODULE_ACTIVE')").getResultList();
            Vector v8 = (Vector) list.get(0);
            String staxModuleActive = "N";
            if (Integer.parseInt(v8.get(0).toString()) == 0) {
                staxModuleActive = "N";
            } else {
                staxModuleActive = "Y";
            }

            String procExec1 = fts.ftsPostBulkDrCr(newChargesList, glAcNo, authBy, acType, tmpDetails, 112, 1,
                    staxModuleActive, "N", tempBd, authBy, orgnBrCode);
            if (!procExec1.substring(0, 4).equalsIgnoreCase("true")) {
                throw new ApplicationException("Transaction not posted.");
            }
            trSNo = Float.parseFloat(procExec1.substring(procExec1.indexOf(":") + 1));
            ut.commit();
            //Sending SMS
            try {
                List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
                for (ChargesObject it : newChargesList) {
                    if (it.getPenalty() > 0 && it.getMinbal() != 2) {
                        SmsToBatchTo to = new SmsToBatchTo();

                        to.setAcNo(it.getAcNo());
                        to.setCrAmt(0d);
                        to.setDrAmt(it.getPenalty());
                        to.setTranType(9);
                        to.setTy(1);
                        to.setTxnDt(dmy.format(ymd.parse(tempBd)));
                        to.setTemplate(SmsType.CHARGE_WITHDRAWAL);

                        smsList.add(to);
                    }
                }
                smsFacade.sendSmsToBatch(smsList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Transfer Authorization." + e.getMessage());
            }
            //End here
            return "True" + trSNo;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String minBalanceChargesPostAllBranch(List<ChargesObject> chargesList, String acType, String glAcNo,
            String authBy, String orgnBrCode, double total, String curDate, String fromDt,
            String toDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        float trSNo = 0;
        String mNameDtl = "";
        String TotalTrsNo = "";
        try {
            ut.begin();
            //List<ChargesObject> newChargesList = new ArrayList<ChargesObject>();
            if (!(chargesList.size() > 0 && chargesList.get(0).getMsg().equals("TRUE"))) {
                throw new ApplicationException(chargesList.get(0).getMsg());
            }
            Map<String, String> mapBranch = new HashMap<String, String>();
            for (ChargesObject minChgObj : chargesList) {
                if (!mapBranch.containsKey(minChgObj.getAcNo().substring(0, 2))) { //Present Branch                       
                    mapBranch.put(minChgObj.getAcNo().substring(0, 2), minChgObj.getAcNo().substring(0, 2));
                }
            }
            Map<String, String> acTypeMap = new HashMap<String, String>();
            for (ChargesObject minChgObj : chargesList) {
                if (!acTypeMap.containsKey(minChgObj.getAcNo().substring(2, 4))) { //Present Branch                       
                    acTypeMap.put(minChgObj.getAcNo().substring(2, 4), minChgObj.getAcNo().substring(2, 4));
                }
            }
            List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
            Set<Map.Entry<String, String>> setBranch = mapBranch.entrySet();
            Iterator<Map.Entry<String, String>> itBranch = setBranch.iterator();
            while (itBranch.hasNext()) {
                Map.Entry<String, String> entryBranch = itBranch.next();
                orgnBrCode = entryBranch.getValue();


                Set<Map.Entry<String, String>> setAccType = acTypeMap.entrySet();
                Iterator<Map.Entry<String, String>> itAcType = setAccType.iterator();

                while (itAcType.hasNext()) {
                    Map.Entry<String, String> entryAcType = itAcType.next();
                    acType = entryAcType.getValue();

                    List glHeadList = setAcToCredit(acType);
                    Vector ele = (Vector) glHeadList.get(0);
                    glAcNo = ele.get(1).toString();
                    glAcNo = orgnBrCode + glAcNo + "01";

                    List<ChargesObject> minChargesAcWiseList = new ArrayList<>();
                    for (int m = 0; m < chargesList.size(); m++) {
                        if (chargesList.get(m).getAcNo().substring(0, 2).equalsIgnoreCase(orgnBrCode) && chargesList.get(m).getAcNo().substring(2, 4).equalsIgnoreCase(acType)) {
                            minChargesAcWiseList.add(chargesList.get(m));
                        }
                    }
                    List<ChargesObject> newChargesList = new ArrayList<ChargesObject>();
                    for (ChargesObject chargesObj : minChargesAcWiseList) {
                        if (chargesObj.getMinbal() != 2) {
                            newChargesList.add(chargesObj);
                        }
                        mNameDtl = chargesObj.getMonName();
                    }
                    if (!newChargesList.isEmpty()) {
                        //String tempBd = daybeginDate(orgnBrCode);
                         String tempBd = ymd.format(new Date());
                        String tmpDetails = "MIN. BALANCE CHG For The Month " + mNameDtl;
                        List postHistoryList = em.createNativeQuery("select date_format(fromdate,'%Y%m%d'),"
                                + "date_format(todate,'%Y%m%d') from post_history where PostFlag = 'MB' and "
                                + "actype = '" + acType + "' and brncode = '" + orgnBrCode + "'").getResultList();
                        if (postHistoryList.size() > 0) {
                            for (int i = 0; i < postHistoryList.size(); i++) {
                                Vector postHistoryVector = (Vector) postHistoryList.get(i);
                                Long fromDate = Long.parseLong(postHistoryVector.get(0).toString());
                                Long toDate = Long.parseLong(postHistoryVector.get(1).toString());
                                Long userFromDate = Long.parseLong(fromDt);
                                if ((userFromDate.compareTo(fromDate) > 0 || userFromDate.compareTo(fromDate) == 0)
                                        && userFromDate.compareTo(toDate) < 0) {
                                    throw new ApplicationException("Minimum balance charges has been already posted.");
                                }
                                if (userFromDate.compareTo(toDate) < 0 || userFromDate.compareTo(toDate) == 0) {
                                    throw new ApplicationException("Minimum balance charges has been already posted.");
                                }
                            }
                        }
                        Query insertPostHistory = em.createNativeQuery("insert into post_history values('MB','" + acType + "',"
                                + "'" + toDt + "','" + fromDt + "','" + authBy + "','Y',CURRENT_TIMESTAMP,'" + authBy + "',"
                                + "'" + orgnBrCode + "')");
                        int resultPostHistory = insertPostHistory.executeUpdate();
                        if (resultPostHistory <= 0) {
                            throw new ApplicationException("Problem in posting the hisory of record.");
                        }
                        List list = em.createNativeQuery("Select ifnull(code,0),reportname from parameterinfo_report where "
                                + "reportname in ('STAXMODULE_ACTIVE')").getResultList();
                        Vector v8 = (Vector) list.get(0);
                        String staxModuleActive = "N";
                        if (Integer.parseInt(v8.get(0).toString()) == 0) {
                            staxModuleActive = "N";
                        } else {
                            staxModuleActive = "Y";
                        }

                        String procExec1 = fts.ftsPostBulkDrCr(newChargesList, glAcNo, authBy, acType, tmpDetails, 112, 1,
                                staxModuleActive, "N", tempBd, authBy, orgnBrCode);
                        if (!procExec1.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException("Transaction not posted.");
                        }
                        trSNo = Float.parseFloat(procExec1.substring(procExec1.indexOf(":") + 1));

                        TotalTrsNo = TotalTrsNo.concat(",".concat(String.valueOf(trSNo)));

                        // ut.commit();
                        //Sending SMS

                        //List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
                        for (ChargesObject it : newChargesList) {
                            if (it.getPenalty() > 0 && it.getMinbal() != 2) {
                                SmsToBatchTo to = new SmsToBatchTo();

                                to.setAcNo(it.getAcNo());
                                to.setCrAmt(0d);
                                to.setDrAmt(it.getPenalty());
                                to.setTranType(9);
                                to.setTy(1);
                                to.setTxnDt(dmy.format(ymd.parse(tempBd)));
                                to.setTemplate(SmsType.CHARGE_WITHDRAWAL);

                                smsList.add(to);
                            }
                        }
                    }
                }
            }
            ut.commit();
            //End here
            try {
                smsFacade.sendSmsToBatch(smsList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Transfer Authorization." + e.getMessage());
            }
            return "True" + TotalTrsNo;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                e.printStackTrace();
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String monthEndCheck(String fromDT, String toDt, String brCode) throws ApplicationException {
        try {
            Date date = ymd.parse(toDt);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Integer maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            if (month == 1) {
                maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            } else if (month == 0) {
                month = 12;
            }
            String fromDate = fromDT.substring(0, 6) + "01";
            String toDate = toDt.substring(0, 6) + maxDay.toString();

            List monthEnd = em.createNativeQuery("select extract(month from date) from bankdays where mendflag='N'"
                    + " and brnCode='" + brCode + "'  and date between '" + fromDate + "' and '" + toDate + "'").getResultList();

            if (monthEnd.size() > 0) {
                return "Month End Process Has Not Run for : " + getMonthName(Integer.parseInt(((Vector) monthEnd.get(0)).elementAt(0).toString()));
            } else {
                return "TRUE";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ************************* fts Bulk DrCr
     * **************************************
     */
    public String daybeginDate(String orgnBrCode) throws ApplicationException {
        try {
            String tempBd;
            List dateList = em.createNativeQuery("select date from bankdays where dayendflag = 'N' and brncode = '" + orgnBrCode + "'").getResultList();
            if (dateList.size() <= 0) {
                return "Check the Day Begin/Day End";
            } else {
                Vector dateVect = (Vector) dateList.get(0);
                tempBd = (String) dateVect.get(0);
            }
            return tempBd;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getAcNature(String acType) throws ApplicationException {
        try {
            List acNatureList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + acType + "'").getResultList();
            Vector acNatureVect = (Vector) acNatureList.get(0);
            String acNature = (String) acNatureVect.get(0);
            return acNature;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private String getMonthName(int month) {
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
        return monthName[month - 1];
    }

    private double getBal(List<MinTxnDetails> txnList, String acNo) {
        for (MinTxnDetails minTxn : txnList) {
            if (minTxn.getAcNo().equalsIgnoreCase(acNo)) {
                return minTxn.getBalance();
            }
        }
        return 0.0d;
    }

    private List<ChargesObject> getErrorMsgList(String msg) {
        List<ChargesObject> minimumChargesList = new ArrayList<ChargesObject>();
        ChargesObject minCharges = new ChargesObject();
        minCharges.setMsg(msg);
        minimumChargesList.add(minCharges);
        return minimumChargesList;
    }

    /**
     * ********************************* LoanInspectionChargeBean
     * ********************
     */
    public List getAccountType() throws ApplicationException {
        List accountype = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select distinct acctCode From accounttypemaster where acctcode is not null and acctNature in ('CA','TL') and acctcode <>'" + CbsAcCodeConstant.DEMAND_LOAN.getAcctCode() + "'");
            accountype = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return accountype;
    }

    public List<ChargesObject> loanInspectionChargesCalculate(String acType, String fromDt, String toDt, String brnCode, String enterBy, String enteryDt) throws ApplicationException {
        try {
            Integer repCode = 0;
            String dtCode = "";
            long date = CbsUtil.dayDiff(ymd.parse(fromDt), ymd.parse(toDt));
            if (date < 0) {
                return getErrorMsgListInspection("Please check the dates entered ");
            }

            List repcode = em.createNativeQuery("Select ifnull(code,-1) from parameterinfo_report where reportname='INSP CHARGE FLAG'").getResultList();
            if (repcode.size() > 0) {
                Vector rep = (Vector) repcode.get(0);
                repCode = Integer.parseInt(rep.get(0).toString());
            }

            List acctType = em.createNativeQuery("Select * From yearend Where YearEndFlag='N' and brncode='" + brnCode + "' And '" + fromDt + "' between mindate and maxDate and '" + toDt + "' between mindate and maxDate and F_Year In (select min(f_year) from yearend Where YearEndFlag='N' and brncode='" + brnCode + "')").getResultList();
            if (acctType.size() <= 0) {
                return getErrorMsgListInspection("Please check the dates entered ");
            }

            List charges1 = em.createNativeQuery("select ifnull(charges,''),ifnull(glheadmisc,'') from parameterinfo_miscincome where purpose='Inspection Charges' and acctcode in ('" + acType + "')").getResultList();
            if (charges1.size() <= 0) {
                return getErrorMsgListInspection("Enter the Charges and GLHead for " + acType);
            }

            String aString = getAcNature(acType);
            List datecode = new ArrayList();

            if (aString.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                datecode = em.createNativeQuery("Select code from parameterinfo_report where reportname='INSP_CHARGE_CA'").getResultList();
                if (datecode.size() > 0) {
                    Vector rep = (Vector) datecode.get(0);
                    dtCode = rep.get(0).toString();
                } else {
                    return getErrorMsgListInspection("There Exists No Data in parameterinfo_report Table");
                }
            } else if (aString.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                datecode = em.createNativeQuery("Select code from parameterinfo_report where reportname='INSP_CHARGE_DEMAND_LOAN'").getResultList();
                if (datecode.size() > 0) {
                    Vector rep = (Vector) datecode.get(0);
                    dtCode = rep.get(0).toString();
                } else {
                    return getErrorMsgListInspection("There Exists No Data in parameterinfo_report Table");
                }
            } else if (aString.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                datecode = em.createNativeQuery("Select code from parameterinfo_report where reportname='INSP_CHARGE_TERM_LOAN'").getResultList();
                if (datecode.size() > 0) {
                    Vector rep = (Vector) datecode.get(0);
                    dtCode = rep.get(0).toString();
                } else {
                    return getErrorMsgListInspection("There Exists No Data in parameterinfo_report Table");
                }
            } else {
                return getErrorMsgListInspection("Please check the Code " + acType);
            }

            if (dtCode.equalsIgnoreCase("0")) {
                if (fromDt.substring(4, 6).equalsIgnoreCase(toDt.substring(4, 6)) && (fromDt.substring(6, 8).equalsIgnoreCase("01"))) {
                    if (!fromDt.substring(0, 5).equalsIgnoreCase(toDt.substring(0, 5))) {
                        return getErrorMsgListInspection("Dates are not of the same month or From date and To Date is not correct");
                    }
                } else {
                    return getErrorMsgListInspection("Dates are not of the same month or From date and To Date is not correct");
                }
            } else if (dtCode.equalsIgnoreCase("1")) {
                if (!(fromDt.substring(6, 8).equalsIgnoreCase("01")) && (toDt.substring(4, 8).equalsIgnoreCase("0331") || (toDt.substring(4, 8).equalsIgnoreCase("0630")
                        || (toDt.substring(4, 8).equalsIgnoreCase("0930") || (toDt.substring(4, 8).equalsIgnoreCase("1231")))))) {
                    return getErrorMsgListInspection("Dates are not of the Quarter or From date and To Date is not correct");
                }
            } else if (dtCode.equalsIgnoreCase("2")) {
                if (!(fromDt.substring(6, 8).equalsIgnoreCase("01")) && ((toDt.substring(4, 8).equalsIgnoreCase("0630") || (toDt.substring(4, 8).equalsIgnoreCase("1231"))))) {
                    return getErrorMsgListInspection("Dates are not of the Half-Year or From date and To Date is not correct");
                }
            } else if (dtCode.equalsIgnoreCase("3")) {
                if (!(fromDt.substring(4, 8).equalsIgnoreCase("0401")) && (toDt.substring(4, 8).equalsIgnoreCase("1231"))) {
                    return getErrorMsgListInspection("Dates are not of the Year or From date and To Date is not correct");
                }
            }

            if (repCode == 1) {
                List<ChargesObject> chargesList = operative(acType, enterBy, enteryDt, brnCode);
                if (chargesList.size() <= 0) {
                    return getErrorMsgListInspection("Data does not exists.");
                } else {
                    return chargesList;
                }
            } else {
                List<ChargesObject> chargesList = operativePr(acType, toDt, enterBy, enteryDt, brnCode);
                if (chargesList.size() <= 0) {
                    return getErrorMsgListInspection("Data does not exists.");
                } else {
                    return chargesList;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List yearEnd(String brncode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select min(f_year) from yearend Where YearEndFlag='N' and brncode='" + brncode + "'");
            tableResult = selectQuery.getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List setAcToCreditInspection(String acType) throws ApplicationException {
        List acToCredit = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select GlHeadMisc from parameterinfo_miscincome where acctCode='" + acType
                    + "' and purpose='Inspection Charges'");
            acToCredit = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acToCredit;
    }

    public String loanInspectionChargesPost(String acType, String glAcNo, String fromDt, String toDt, String authBy,
            String orgnBrCode, String todayDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        float trSNo = 0.0f;
        try {
            String resultHistory = checkPostHistory(acType, fromDt, toDt, orgnBrCode);
            if (!resultHistory.equalsIgnoreCase("True")) {
                return resultHistory;
            }
            String staxModuleActive = "";
            List parameterList = em.createNativeQuery("select code from parameterinfo_report where "
                    + "reportname = 'STAXMODULE_ACTIVE'").getResultList();
            if (parameterList.size() > 0) {
                Vector parameterVect = (Vector) parameterList.get(0);
                staxModuleActive = parameterVect.get(0).toString();

                if (staxModuleActive.equalsIgnoreCase("1")) {
                    staxModuleActive = "True";
                } else {
                    staxModuleActive = "False";
                }
            }
            String tempBd = daybeginDate(orgnBrCode);
            String tmpDetails = "Insp. Charges From:" + dmy.format(ymd.parse(fromDt)) + " To " + dmy.format(ymd.parse(toDt));

            List<ChargesObject> chargesList = loanInspectionChargesCalculate(acType, fromDt, toDt, orgnBrCode, authBy, todayDate);
            if (!(chargesList.size() > 0 && chargesList.get(0).getMsg().equals("TRUE"))) {
                throw new ApplicationException(chargesList.get(0).getMsg());
            }

            ut.begin();
            if (staxModuleActive.equalsIgnoreCase("True")) {
                String procExec1 = fts.ftsPostBulkDrCr(chargesList, orgnBrCode + glAcNo + "01", authBy, acType,
                        tmpDetails, 117, 1, "Y",
                        "N", tempBd, authBy, orgnBrCode);
                if (procExec1.substring(0, 4).equalsIgnoreCase("true")) {
                    msg = "True";
                    trSNo = Float.parseFloat(procExec1.substring(procExec1.indexOf(":") + 1));
                } else {
                    msg = "false";
                }
            } else {
                String var = fts.postInciCharges(chargesList, orgnBrCode + glAcNo + "01", fromDt, toDt, authBy,
                        acType, tmpDetails, 117, orgnBrCode, authBy, todayDate);
                if (var.substring(0, 4).equalsIgnoreCase("true")) {
                    msg = "True";
                    trSNo = Float.parseFloat(var.substring(var.indexOf(":") + 1));
                } else {
                    msg = "false";
                }

            }
            if (!msg.equalsIgnoreCase("TRUE")) {
                throw new ApplicationException(msg);
            }
            Integer entry = em.createNativeQuery("Insert into parameterinfo_posthistory(actype,FromDt,todt,dt,purpose,"
                    + "trandesc,enterby,status,brncode) values('" + acType + "','" + fromDt + "','" + toDt + "',"
                    + "'" + ymd.format(new Date()) + "','Inspection Charges',117,'" + authBy + "',1,"
                    + "'" + orgnBrCode + "')").executeUpdate();
            if (entry <= 0) {
                throw new ApplicationException("Error In Inserting Parameter In History Table");
            }
            ut.commit();
            //Sending Sms
            try {
                List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
                for (ChargesObject it : chargesList) {
                    if (it.getPenalty() > 0) {
                        SmsToBatchTo to = new SmsToBatchTo();

                        to.setAcNo(it.getAcNo());
                        to.setCrAmt(0d);
                        to.setDrAmt(it.getPenalty());
                        to.setTranType(9);
                        to.setTy(1);
                        to.setTxnDt(dmy.format(ymd.parse(todayDate)));
                        to.setTemplate(SmsType.CHARGE_WITHDRAWAL);

                        smsList.add(to);
                    }
                }
                smsFacade.sendSmsToBatch(smsList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Transfer Authorization." + e.getMessage());
            }
            //End here    
            return "Charegs Successfully posted. Transfer Batch No :- " + trSNo;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String acNature(String acType) throws ApplicationException {
        try {
            List acNatureList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + acType + "'").getResultList();
            Vector acNatureVect = (Vector) acNatureList.get(0);
            String acNature = (String) acNatureVect.get(0);
            return acNature;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<ChargesObject> operative(String acType, String enterBy, String entryDate, String brnCode) throws ApplicationException {
        try {
            List<ChargesObject> chargesList = new ArrayList<ChargesObject>();
            List acctTypeList = em.createNativeQuery("SELECT am.ACNO, SUBSTRING(am.CUSTNAME,1,40)CUSTNAME,ifnull(B.CHARGES,0) PENALTY,"
                    + " (ifnull(am.ODLIMIT,0)+ifnull(am.ADHOCLIMIT,0)) ODLIMIT, am.OPTSTATUS, am.ACCSTATUS, ifnull(cm.MailStateCode,''), ifnull(br.State,'')  "
                    + " FROM accountmaster am , parameterinfo_miscincome B, customerid ci, cbs_customer_master_detail cm, branchmaster br   "
                    + " where am.ACNo = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brnCode + "' as unsigned)  and "
                    + " am.ACCSTATUS NOT IN (9,2) AND B.PURPOSE ='Inspection Charges' AND SUBSTRING(am.ACNO,3,2)='" + acType + "' "
                    + " AND B.ACCTCODE='" + acType + "' AND am.CURBRCODE ='" + brnCode + "' ORDER BY am.ACNO ").getResultList();

            if (acctTypeList.size() > 0) {
                for (int j = 0; j < acctTypeList.size(); j++) {
                    Vector vect = (Vector) acctTypeList.get(j);
                    ChargesObject chargesObj = new ChargesObject();
                    chargesObj.setAcNo(vect.get(0).toString());

                    chargesObj.setCustName(vect.get(1).toString());
                    chargesObj.setPenalty(Double.parseDouble(vect.get(2).toString()));
                    chargesObj.setLimit(Float.parseFloat(vect.get(3).toString()));

                    chargesObj.setOptStatus(Integer.parseInt(vect.get(4).toString()));
                    chargesObj.setStatus(vect.get(5).toString());
                    chargesObj.setTrans(0);
                    chargesObj.setBrState(vect.get(7).toString());
                    chargesObj.setCustState(vect.get(6).toString());
                    chargesObj.setMsg("TRUE");
                    chargesList.add(chargesObj);
                }
            }
            return chargesList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List<ChargesObject> operativePr(String acType, String dateTo, String enterBy, String entryDate, String brncode) throws ApplicationException {
        try {
            List<ChargesObject> chargesList = new ArrayList<ChargesObject>();

            List isSlabList = em.createNativeQuery("select * from loan_inspection where acno = '" + acType + "'").getResultList();
            if (isSlabList.isEmpty()) {
                return getErrorMsgListInspection("Please Fill The Inspection Master");
            }

            List acctTypeList = em.createNativeQuery("SELECT am.ACNO, SUBSTRING(am.CUSTNAME,1,40)CUSTNAME,ifnull(B.CHARGES,0) PENALTY,"
                    + "ifnull(am.ODLIMIT,0)+ifnull(am.ADHOCLIMIT,0) ODLIMIT,am.OPTSTATUS,am.ACCSTATUS , ifnull(cm.MailStateCode,''), ifnull(br.State,'')  "
                    + "FROM accountmaster am , parameterinfo_miscincome B, customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                    + "where am.ACNo = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brncode + "' as unsigned)  and "
                    + "SUBSTRING(am.ACNO,3,2)='" + acType + "' and am.CurBrCode='" + brncode + "'  AND  am.ACCSTATUS NOT IN (9,2) AND "
                    + "PURPOSE ='Inspection Charges' AND B.ACCTCODE='" + acType + "' and (am.closingdate='' or ifnull(am.closingdate,'')='' or "
                    + "am.closingdate >'" + dateTo + "') ORDER BY am.ACNO").getResultList();

            if (acctTypeList.size() > 0) {
                for (int j = 0; j < acctTypeList.size(); j++) {
                    Vector vect = (Vector) acctTypeList.get(j);
                    String acNo = vect.get(0).toString();
                    float balos = 0f;
                    List sanction = em.createNativeQuery("select sanctionlimit from loan_appparameter where acno='" + acNo + "'").getResultList();
                    if (!sanction.isEmpty()) {
                        Vector balanceVect = (Vector) sanction.get(0);
                        balos = Float.parseFloat(balanceVect.get(0).toString());
                    }

                    String acctType = ftsRemote.getAccountCode(acNo);
                    List loanInspection = em.createNativeQuery("select roi from loan_inspection where acno='" + acctType + "'and " + balos
                            + " between  slab_ceil and slab_floor and  recno in (select max(recno) from loan_inspection where acno='" + acctType
                            + "')").getResultList();

                    if (loanInspection.size() > 0) {
                        Vector balanceVect1 = (Vector) loanInspection.get(0);
                        if (Double.parseDouble(balanceVect1.get(0).toString()) > 0) {
                            ChargesObject chargesObj = new ChargesObject();
                            chargesObj.setAcNo(vect.get(0).toString());

                            chargesObj.setCustName(vect.get(1).toString());
                            chargesObj.setLimit(Float.parseFloat(vect.get(3).toString()));

                            chargesObj.setOptStatus(Integer.parseInt(vect.get(4).toString()));
                            chargesObj.setStatus(vect.get(5).toString());
                            chargesObj.setTrans(0);
                            chargesObj.setPenalty(Double.parseDouble(balanceVect1.get(0).toString()));
                            chargesObj.setBrState(vect.get(7).toString());
                            chargesObj.setCustState(vect.get(6).toString());
                            chargesObj.setMsg("TRUE");
                            chargesList.add(chargesObj);
                        }
                    }
//                    else {
//                        return getErrorMsgListInspection("Please Fill The Inspection Master");
//                    }
                }
            }
            return chargesList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String checkPostHistory(String acType, String fromDt, String toDt, String orgnBrCode) throws ApplicationException {
        try {
            String checkPostHistory = "";
            SimpleDateFormat ddMMyy = new SimpleDateFormat("dd/MM/yyyy");
            List checkPosthistory = em.createNativeQuery("select ifnull(fromdt,'19000101') as fromdt,ifnull(todt,'19000101') as todt,ifnull(dt,'19000101') as dt, ifnull(enterby,'') as enterby from parameterinfo_posthistory where actype='" + acType + "' and brncode='" + orgnBrCode + "' and trandesc=117 and ((fromdt between '" + fromDt + "' and '" + toDt + "') or (todt between '" + fromDt + "' and '" + toDt + "') or (fromdt >= '" + fromDt + "') or (todt >='" + toDt + "')) and status=1").getResultList();
            if (checkPosthistory.size() > 0) {
                Vector hisVect = (Vector) checkPosthistory.get(0);
                String frmDate = hisVect.get(0).toString();
                String toDate = hisVect.get(1).toString();
                String user = hisVect.get(3).toString();
                checkPostHistory = "Transaction Has Already Been Posted For The Period: " + ddMMyy.format(ymd.parse(frmDate)) + " To " + ddMMyy.format(ymd.parse(toDate)) + " on " + ddMMyy.format(ymd.parse(toDate)) + " By " + user;
            } else {
                checkPostHistory = "true";
            }
            //checkPostHistory = "True";
            return checkPostHistory;

        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    private List<ChargesObject> getErrorMsgListInspection(String msg) {
        List<ChargesObject> minimumChargesList = new ArrayList<ChargesObject>();
        ChargesObject minCharges = new ChargesObject();
        minCharges.setMsg(msg);
        minimumChargesList.add(minCharges);
        return minimumChargesList;
    }

    /**
     * ************************************* End
     * *************************************
     */
    public List<MinBalanceChargesPostPojo> minBalanceChargesPostReport(float trsno, String acType, String enterBy, String enteryDt, String brCode) throws ApplicationException {
        List<MinBalanceChargesPostPojo> minBalanceChargesPostPojo = new ArrayList<MinBalanceChargesPostPojo>();
        try {
            String acctNature = "", table = "";
            String bnkName = null, bnkAddress = null;
            List objBan = common.getBranchNameandAddress(brCode);
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            List result = new ArrayList();
            if (!acType.equalsIgnoreCase("ALL")) {
                acctNature = common.getAcNatureByAcType(acType);
                if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    table = "recon";
                } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    table = "ca_recon";
                }
                result = em.createNativeQuery("select r.acno,r.dramt,r.details,a.custname from " + table + " r, accountmaster a where r.dt='" + enteryDt + "' and r.enterby='" + enterBy + "' and r.trsno=" + trsno + " and r.org_brnid='" + brCode + "' and r.dest_brnid='" + brCode + "' and r.acno = a.acno order by dramt,acno").getResultList();

            } else {
                result = em.createNativeQuery("select r.acno,r.dramt,r.details,a.custname from recon r, accountmaster a where r.dt='" + enteryDt + "' and r.enterby='" + enterBy + "' and r.trsno=" + trsno + " and r.org_brnid = r.dest_brnid and r.acno = a.acno "
                        + "union all "
                        + "select r.acno,r.dramt,r.details,a.custname from ca_recon r, accountmaster a where r.dt='" + enteryDt + "' and r.enterby='" + enterBy + "' and r.trsno=" + trsno + " and r.org_brnid = r.dest_brnid and r.acno = a.acno order by dramt,acno").getResultList();
            }
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    MinBalanceChargesPostPojo balCert = new MinBalanceChargesPostPojo();
                    balCert.setBnkName(bnkName);
                    balCert.setBnkAddress(bnkAddress);
                    balCert.setAcno(record.get(0).toString());
                    balCert.setDramt(Double.parseDouble(record.get(1).toString()));
                    if (record.get(2) != null) {
                        balCert.setDetails(record.get(2).toString());
                    } else {
                        balCert.setDetails("");
                    }
                    balCert.setCustName(record.get(3).toString());
                    minBalanceChargesPostPojo.add(balCert);
                }
            }
            if (!acType.equalsIgnoreCase("ALL")) {
                result = em.createNativeQuery("select a.acno,a.amount,a.details,b.CUSTNAME from pendingcharges a,accountmaster b  where a.dt='" + enteryDt + "' and a.enterby='" + enterBy + "'  and a.trsno=" + trsno + " and a.acno=b.acno  and b.CurBrCode ='" + brCode + "'").getResultList();
            } else {
                result = em.createNativeQuery("select a.acno,a.amount,a.details,b.CUSTNAME from pendingcharges a,accountmaster b  where a.dt='" + enteryDt + "' and a.enterby='" + enterBy + "'  and a.trsno=" + trsno + " and a.acno=b.acno order by b.CurBrCode ").getResultList();
            }

            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    MinBalanceChargesPostPojo balCert = new MinBalanceChargesPostPojo();
                    balCert.setBnkName(bnkName);
                    balCert.setBnkAddress(bnkAddress);
                    balCert.setAcno(record.get(0).toString());
                    balCert.setDramt(Double.parseDouble(record.get(1).toString()));
                    balCert.setDetails(record.get(2).toString());
                    balCert.setCustName(record.get(3).toString());
                    minBalanceChargesPostPojo.add(balCert);
                }
            }
            return minBalanceChargesPostPojo;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getAtmChargeCode() throws com.cbs.exception.ApplicationException {
        String atmChg = "0";
        List codeList = new ArrayList();
        try {
            codeList = em.createNativeQuery("SELECT ifnull(code,0) from parameterinfo_report where reportName ='ATM-CHARGE-HO'").getResultList();
            Vector vecAtm = (Vector) codeList.get(0);
            atmChg = vecAtm.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return atmChg;
    }

    public List getAtmChargeParam() throws com.cbs.exception.ApplicationException {
        List chgList = new ArrayList();
        try {
            chgList = em.createNativeQuery("SELECT glheadMisc,charges from parameterinfo_miscincome where purpose ='ATM CARD CHARGES' "
                    + " and effectivedt = (select max(effectivedt) from parameterinfo_miscincome where purpose = 'ATM CARD CHARGES')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return chgList;
    }

    public List getAllAtmAccount(String brnCode, String frDt, String toDt) throws com.cbs.exception.ApplicationException {
        List acNoList = new ArrayList();
        try {
            String months = "";
            int dayNo = ftsRemote.getCodeForReportName("ATM-CHARGE-DAY");
            int code = ftsRemote.getCodeForReportName("PRIZM-ATM");
            int staffCharges = ftsRemote.getCodeForReportName("ATM-STAFF-CHARGES");

            List list = em.createNativeQuery("select code from cbs_parameterinfo where name='ATM-CHG-DURATION'").getResultList();
            Vector v = (Vector) list.get(0);
            String atm_chrg_duration = v.get(0).toString();
            if (atm_chrg_duration.equalsIgnoreCase("M")) {
                Calendar cal = Calendar.getInstance();
                Calendar cal1 = Calendar.getInstance();
                cal.setTime(ymd.parse(frDt));
                cal1.setTime(ymd.parse(toDt));
                if (cal.get(Calendar.MONTH) == cal1.get(Calendar.MONTH)) {
                    int month = cal.get(Calendar.MONTH);
                    month += 1;
                    if (month < 10) {
                        months = "0" + Integer.toString(month);
                    } else {
                        months = String.valueOf(month);
                    }
                }
            }
            if (code == 0) {
                acNoList = em.createNativeQuery("select a.acn,count(*), a.stateCode, a.brState from ("
                        + " select ac.acno as acn ,datediff('" + toDt + "',ac.issue_dt) as d, ifnull(cm.MailStateCode,'') as stateCode,ifnull(br.State,'')  as brState "
                        + " from atm_card_master ac, accountmaster am, customerid ci, cbs_customer_master_detail cm, branchmaster br   "
                        + " where am.ACNo = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brnCode + "' as unsigned) and "
                        + " ac.acno = am.acno and am.accstatus <>9 and ac.issue_dt <= '" + toDt + "' "
                        + " and ac.del_flag ='A' and am.curbrcode ='" + brnCode + "' having d > " + dayNo + "  "
                        + " union all"
                        + " select ac.acno as acn ,datediff('" + toDt + "',ac.issue_dt) as d, ifnull(cm.MailStateCode,'') as stateCode,ifnull(br.State,'')  as brState "
                        + " from atm_card_master ac, accountmaster am, customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                        + " where am.ACNo = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brnCode + "' as unsigned) and "
                        + " ac.acno = am.acno and am.accstatus <>9 and ac.lastUpdatedate <= '" + toDt + "' and ac.del_flag ='I' "
                        + " and am.curbrcode='" + brnCode + "' having d > " + dayNo + ") a group by a.acn ").getResultList();
            } else if (code == 1) { //Prizm
                acNoList = em.createNativeQuery("select a.acno, 1,ifnull(cm.MailStateCode,'') as stateCode,ifnull(br.State,'') as brState "
                        + " from prizm_card_master a, customerid ci, cbs_customer_master_detail cm, branchmaster br,accountmaster am "
                        + " where a.ACNo = ci.Acno and a.acno=am.acno and am.accstatus<>9 and ci.CustId = cast(cm.customerid as unsigned) "
                        + "and br.brncode=cast('" + brnCode + "' as unsigned)  and a.cbs_status not in('S','L','C','E') and "
                        + " substring(a.acno,1,2)='" + brnCode + "'").getResultList();
            } else if (code == 2) {
                //Finacus         
                if (atm_chrg_duration.equalsIgnoreCase("Y")) {
                    if (staffCharges == 1) {
                        acNoList = em.createNativeQuery("select ac.acno as acn,1,datediff('" + toDt + "',"
                                + "date_format(ac.registration_dt,'%Y%m%d')) as d,ifnull(cm.MailStateCode,'') as "
                                + "stateCode,ifnull(br.State,'')  as brState from atm_card_master ac, accountmaster am, "
                                + "customerid ci, cbs_customer_master_detail cm, branchmaster br where am.ACNo = ci.Acno "
                                + "and ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brnCode + "' as unsigned) "
                                + "and ac.acno = am.acno and am.accstatus <>9 and am.orgncode<>16 and "
                                + "date_format(ac.registration_dt,'%Y%m%d') <= '" + toDt + "' and ac.del_flag<>'I' and "
                                + "ac.verify='Y' and am.curbrcode ='" + brnCode + "' having d > " + dayNo + " ").getResultList();
                    } else {
                        acNoList = em.createNativeQuery("select ac.acno as acn,1,datediff('" + toDt + "',"
                                + "date_format(ac.registration_dt,'%Y%m%d')) as d,ifnull(cm.MailStateCode,'') as "
                                + "stateCode,ifnull(br.State,'')  as brState from atm_card_master ac, accountmaster am, "
                                + "customerid ci, cbs_customer_master_detail cm, branchmaster br where am.ACNo = ci.Acno "
                                + "and ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brnCode + "' as unsigned) "
                                + "and ac.acno = am.acno and am.accstatus <>9 and "
                                + "date_format(ac.registration_dt,'%Y%m%d') <= '" + toDt + "' and ac.del_flag<>'I' and "
                                + "ac.verify='Y' and am.curbrcode ='" + brnCode + "' having d > " + dayNo + " ").getResultList();
                    }
                } else if (atm_chrg_duration.equalsIgnoreCase("M")) {
                    acNoList = em.createNativeQuery("select ac.acno as acn,1,ifnull(cm.MailStateCode,'') as stateCode,ifnull(br.State,'')as brState\n"
                            + "from atm_card_master ac, accountmaster am, customerid ci, cbs_customer_master_detail cm, branchmaster br where am.ACNo = ci.Acno\n"
                            + "and ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brnCode + "' as unsigned)\n"
                            + "and ac.acno = am.acno and am.accstatus <>9 and date_format(ac.registration_dt,'%m')='" + months + "'\n"
                            + "and ac.del_flag<>'I' and ac.verify='Y' and am.curbrcode ='" + brnCode + "'").getResultList();
                }
            }
        } catch (ApplicationException | ParseException e) {
            throw new ApplicationException(e.getMessage());
        }
        return acNoList;
    }

    public String getAllFromDt(String brnCode, String want) throws ApplicationException {
        try {
            String dt = "";
            List getMaxToDtList = em.createNativeQuery("select MIN(SNO) from cbs_loan_acctype_interest_parameter where "
                    + " POST_FLAG = 'N' and BRNCODE = '" + brnCode + "' and flag = 'AT'").getResultList();
            if (getMaxToDtList.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
            }
            Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
            if (getMaxToDtVect.get(0) == null) {
                throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
            }
            int sNo = Integer.parseInt(getMaxToDtVect.get(0).toString());
            if (want.equalsIgnoreCase("f")) {
                List getFrDtList = em.createNativeQuery("select date_format(FROM_DT,'%Y%m%d') from cbs_loan_acctype_interest_parameter where "
                        + "flag ='AT' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                if (getFrDtList.isEmpty()) {
                    throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
                }
                Vector getFrDtVect = (Vector) getFrDtList.get(0);
                dt = getFrDtVect.get(0).toString();
            } else if (want.equalsIgnoreCase("t")) {
                List getFrDtList = em.createNativeQuery("select date_format(TO_DT,'%Y%m%d') from cbs_loan_acctype_interest_parameter where "
                        + "flag ='AT' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                if (getFrDtList.isEmpty()) {
                    throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
                }
                Vector getFrDtVect = (Vector) getFrDtList.get(0);
                dt = getFrDtVect.get(0).toString();
            }
            return dt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String atmChargesPost(List<AtmChargePostingPojo> rList, String glAcNo, String usrName, String brnCode, String orgnBrCode, double total, String curDate, String fromDt, String toDt) throws ApplicationException {
        String msg = "";
        UserTransaction ut = context.getUserTransaction();
        float trSNo = 0;
        String staxMNoduleActive = "", taxFlag = "", taxGlAcNo = "", taxName = "GST", desc = "ATM CHARGE ";
        double taxAmt = 0d, taxAmtIgst = 0d, ttlTaxAmt = 0d, ttlTaxAmtIgst = 0d, totAmt = 0d;
        int taxRoundUpTo = 0;
        Map<String, Double> map = new HashMap<String, Double>();
        Map<String, Double> mapIgst = new HashMap<String, Double>();
        try {
            ut.begin();
            if (rList.size() > 0) {
                trSNo = ftsRemote.getTrsNo();
                List parameterList = em.createNativeQuery("select code from parameterinfo_report where reportname = 'STAXMODULE_ACTIVE'").getResultList();
                if (parameterList.size() > 0) {
                    Vector parameterVect = (Vector) parameterList.get(0);
                    staxMNoduleActive = parameterVect.get(0).toString();
                    if (staxMNoduleActive.equalsIgnoreCase("1")) {
                        taxFlag = "Y";
                    } else {
                        taxFlag = "N";
                    }
                } else {
                    taxFlag = "N";
                }

                double sPerc = 0, sPercIgst = 0;
                int rUpTo = 0, rUpToIgst = 0;
                if (taxFlag.equalsIgnoreCase("Y")) {
                    map = interFts.getTaxComponentSlab(curDate);
                    Set<Entry<String, Double>> set = map.entrySet();
                    Iterator<Entry<String, Double>> it = set.iterator();
                    while (it.hasNext()) {
                        Entry entry = it.next();
                        sPerc = sPerc + Double.parseDouble(entry.getValue().toString());
                        rUpTo = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                    }
                    mapIgst = interFts.getIgstTaxComponentSlab(curDate);
                    Set<Entry<String, Double>> setIgst = mapIgst.entrySet();
                    Iterator<Entry<String, Double>> itIgst = setIgst.iterator();
                    while (itIgst.hasNext()) {
                        Entry entry = itIgst.next();
                        sPercIgst = sPercIgst + Double.parseDouble(entry.getValue().toString());
                        rUpToIgst = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                    }
                }

                if (!brnCode.equalsIgnoreCase(glAcNo.substring(0, 2))) {
                    if (taxFlag.equalsIgnoreCase("Y")) {
                        for (AtmChargePostingPojo chargesObj : rList) {
                            String acNo = chargesObj.getAcno();
                            String custState = chargesObj.getCustSate();
                            String branchState = chargesObj.getBrState();
                            String acNature = ftsRemote.getAccountNature(acNo);
                            double chgAmt = Double.parseDouble(chargesObj.getCharges());
                            double tchAmt = chgAmt;
                            float recNo = 0f;
                            if (chgAmt >= 0) {
//                                String taxCalList = fts.taxCalculation(chgAmt, curDate, glAcNo.substring(0, 2));
//                                String[] values = null;
//                                String spliter = "~`~";
//
//                                values = taxCalList.split(spliter);
//                                taxGlAcNo = values[0];
//                                taxAmt = Double.parseDouble(values[1]);
//                                taxName = values[2];
//                                taxAmt = CbsUtil.round(taxAmt, taxRoundUpTo);
                                if (custState.equalsIgnoreCase(branchState)) {
                                    taxAmt = CbsUtil.round(((chgAmt * sPerc) / 100), rUpTo);
                                } else {
                                    taxAmt = CbsUtil.round(((chgAmt * sPercIgst) / 100), rUpToIgst);
                                    taxAmtIgst = taxAmt;
                                }
                                tchAmt = chgAmt + taxAmt;
                                msg = ftsRemote.checkBalance(acNo, tchAmt, usrName);
                                String taxDetail = desc + ":" + taxName;
                                if (msg.equalsIgnoreCase("True")) {
                                    recNo = ftsRemote.getRecNo();
                                    msg = interFts.cbsPostingSx(acNo, 1, curDate, chgAmt, 0d, 2, desc + fromDt + "To " + toDt, 0f, "A", "", "", 3, 0f, recNo, 118,
                                            ftsRemote.getCurrentBrnCode(acNo), glAcNo.substring(0, 2), usrName, usrName, trSNo, "", "");
                                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                        return msg;
                                    }

                                    recNo = ftsRemote.getRecNo();
                                    msg = interFts.cbsPostingSx(acNo, 1, curDate, taxAmt, 0d, 2, taxDetail, 0f, "A", "", "", 3, 0f, recNo, 71,
                                            ftsRemote.getCurrentBrnCode(acNo), glAcNo.substring(0, 2), usrName, usrName, trSNo, "", "");
                                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                        return msg;
                                    }
                                    ftsRemote.updateBalance(acNature, acNo, 0, tchAmt, "Y", "Y");
                                    totAmt = totAmt + chgAmt;
                                    if (custState.equalsIgnoreCase(branchState)) {
                                        ttlTaxAmt = ttlTaxAmt + taxAmt;
                                    } else {
                                        ttlTaxAmtIgst = ttlTaxAmtIgst + taxAmtIgst;
                                    }
                                } else {
                                    recNo = ftsRemote.getRecNo();
                                    Query insertChg = em.createNativeQuery("insert into pendingcharges(Acno,dt,amount,details,ty,trantype,recno,trsno,enterby,auth,authby,Trandesc,charges)"
                                            + " values('" + acNo + "',DATE_FORMAT(curdate(),'%Y%m%d')," + chgAmt + ",'INSUFFICIENT ATM CHARGE','1','2',"
                                            + "" + recNo + "," + trSNo + ",'" + usrName + "','Y','" + usrName + "','" + 118 + "','" + desc + "')");

                                    Integer insertChgVarient = insertChg.executeUpdate();
                                    if (insertChgVarient <= 0) {
                                        return "False";
                                    }
                                }
                            }
                        }
                    } else {
                        for (AtmChargePostingPojo chargesObj : rList) {
                            String acNo = chargesObj.getAcno();
                            String acNature = ftsRemote.getAccountNature(acNo);
                            double chgAmt = Double.parseDouble(chargesObj.getCharges());
                            double tchAmt = chgAmt;
                            float recNo = 0f;
                            if (chgAmt >= 0) {
                                tchAmt = chgAmt;
                                msg = ftsRemote.checkBalance(acNo, tchAmt, usrName);
                                if (msg.equalsIgnoreCase("True")) {
                                    recNo = ftsRemote.getRecNo();
                                    msg = interFts.cbsPostingSx(acNo, 1, curDate, chgAmt, 0d, 2, desc + fromDt + "To " + toDt, 0f, "A", "", "", 3, 0f, recNo, 118,
                                            ftsRemote.getCurrentBrnCode(acNo), glAcNo.substring(0, 2), usrName, usrName, trSNo, "", "");
                                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                        return msg;
                                    }
                                    ftsRemote.updateBalance(acNature, acNo, 0, tchAmt, "Y", "Y");
                                    totAmt = totAmt + chgAmt;
                                } else {
                                    recNo = ftsRemote.getRecNo();
                                    Query insertChg = em.createNativeQuery("insert into pendingcharges(Acno,dt,amount,details,ty,trantype,recno,trsno,enterby,auth,authby,Trandesc,charges)"
                                            + " values('" + acNo + "',DATE_FORMAT(curdate(),'%Y%m%d')," + chgAmt + ",'INSUFFICIENT ATM CHARGE','1','2',"
                                            + "" + recNo + "," + trSNo + ",'" + usrName + "','Y','" + usrName + "','" + 118 + "','" + desc + "')");

                                    Integer insertChgVarient = insertChg.executeUpdate();
                                    if (insertChgVarient <= 0) {
                                        return "False";
                                    }
                                }
                            }
                        }
                    }

                    if (totAmt >= 0) {
                        float recNo = 0f;
                        if (taxFlag.equalsIgnoreCase("Y")) {
                            double sTaxAmt = CbsUtil.round(((ttlTaxAmt * 100) / sPerc), rUpTo);
                            map = interFts.getTaxComponent(sTaxAmt, curDate);
                            Set<Entry<String, Double>> set1 = map.entrySet();
                            Iterator<Entry<String, Double>> it1 = set1.iterator();
                            while (it1.hasNext()) {
                                Entry entry = it1.next();
                                String[] keyArray = entry.getKey().toString().split(":");
                                String description = keyArray[0];
                                String taxHead = orgnBrCode + keyArray[1];
                                String mainDetails = description.toUpperCase() + " " + desc;
                                double taxAmount = Double.parseDouble(entry.getValue().toString());

                                recNo = ftsRemote.getRecNo();
                                String acNature = ftsRemote.getAccountNature(taxHead);
                                msg = interFts.cbsPostingCx(taxHead, 0, curDate, taxAmount, 0d, 2, mainDetails, 0f, "A", "", "", 3, 0f, recNo, 71,
                                        ftsRemote.getCurrentBrnCode(taxGlAcNo), glAcNo.substring(0, 2), usrName, usrName, trSNo, "", "");
                                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                    return msg;
                                }
                                ftsRemote.updateBalance(acNature, taxGlAcNo, taxAmount, 0, "Y", "Y");
                            }
                            if (ttlTaxAmtIgst != 0) {
                                double sTaxAmtIgst = CbsUtil.round(((ttlTaxAmtIgst * 100) / sPercIgst), rUpToIgst);
                                mapIgst = interFts.getIgstTaxComponent(sTaxAmtIgst, curDate);
                                Set<Entry<String, Double>> set1Igst = mapIgst.entrySet();
                                Iterator<Entry<String, Double>> it1Igst = set1Igst.iterator();
                                while (it1Igst.hasNext()) {
                                    Entry entry = it1Igst.next();
                                    String[] keyArray = entry.getKey().toString().split(":");
                                    String description = keyArray[0];
                                    String taxHead = orgnBrCode + keyArray[1];
                                    String mainDetails = description.toUpperCase() + " " + desc;
                                    double taxAmount = Double.parseDouble(entry.getValue().toString());

                                    recNo = ftsRemote.getRecNo();
                                    String acNature = ftsRemote.getAccountNature(taxHead);
                                    msg = interFts.cbsPostingCx(taxHead, 0, curDate, taxAmount, 0d, 2, mainDetails, 0f, "A", "", "", 3, 0f, recNo, 71,
                                            ftsRemote.getCurrentBrnCode(taxGlAcNo), glAcNo.substring(0, 2), usrName, usrName, trSNo, "", "");
                                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                        return msg;
                                    }
                                    ftsRemote.updateBalance(acNature, taxGlAcNo, taxAmount, 0, "Y", "Y");
                                }
                            }
                        }

                        recNo = ftsRemote.getRecNo();
                        String acNature = ftsRemote.getAccountNature(glAcNo);
                        msg = interFts.cbsPostingCx(glAcNo, 0, curDate, totAmt, 0d, 2, desc + fromDt + "To " + toDt, 0f, "A", "", "", 3, 0f, recNo, 118,
                                ftsRemote.getCurrentBrnCode(taxGlAcNo), glAcNo.substring(0, 2), usrName, usrName, trSNo, "", "");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            return msg;
                        }
                        ftsRemote.updateBalance(acNature, glAcNo, totAmt, 0, "Y", "Y");
                    }
                } else {
                    if (taxFlag.equalsIgnoreCase("Y")) {
                        for (AtmChargePostingPojo chargesObj : rList) {
                            String acNo = chargesObj.getAcno();
                            String acNature = ftsRemote.getAccountNature(acNo);
                            double chgAmt = Double.parseDouble(chargesObj.getCharges());
                            double tchAmt = chgAmt;
                            float recNo = 0f;
                            if (chgAmt >= 0) {
//                                String taxCalList = fts.taxCalculation(chgAmt, curDate, glAcNo.substring(0, 2));
//                                String[] values = null;
//                                String spliter = "~`~";
//
//                                values = taxCalList.split(spliter);
//                                taxGlAcNo = values[0];
//                                taxAmt = Double.parseDouble(values[1]);
//                                taxName = values[2];
//                                taxAmt = CbsUtil.round(taxAmt, taxRoundUpTo);
                                taxAmt = CbsUtil.round(((chgAmt * sPerc) / 100), rUpTo);
                                tchAmt = chgAmt + taxAmt;
                                msg = ftsRemote.checkBalance(acNo, tchAmt, usrName);
                                String taxDetail = desc + ":" + taxName;
                                if (msg.equalsIgnoreCase("True")) {
                                    recNo = ftsRemote.getRecNo();
                                    msg = ftsRemote.insertRecons(acNature, acNo, 1, chgAmt, curDate, curDate, 2, desc + fromDt + "To " + toDt, usrName, trSNo, null, recNo, "Y", usrName, 118, 3, null,
                                            null, (float) 0, null, "A", 1, null, null, null, null, glAcNo.substring(0, 2), ftsRemote.getCurrentBrnCode(acNo), 0, null, "", "");
                                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                        return msg;
                                    }

                                    ftsRemote.updateBalance(acNature, acNo, 0, chgAmt, "Y", "Y");

                                    recNo = ftsRemote.getRecNo();
                                    msg = ftsRemote.insertRecons(acNature, acNo, 1, taxAmt, curDate, curDate, 2, taxDetail, usrName, trSNo, null, recNo, "Y", usrName, 118, 3, null,
                                            null, (float) 0, null, "A", 1, null, null, null, null, glAcNo.substring(0, 2), ftsRemote.getCurrentBrnCode(acNo), 0, null, "", "");
                                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                        return msg;
                                    }

                                    ftsRemote.updateBalance(acNature, acNo, 0, taxAmt, "Y", "Y");

                                    totAmt = totAmt + chgAmt;
                                    ttlTaxAmt = ttlTaxAmt + taxAmt;
                                } else {
                                    recNo = ftsRemote.getRecNo();
                                    Query insertChg = em.createNativeQuery("insert into pendingcharges(Acno,dt,amount,details,ty,trantype,recno,trsno,enterby,auth,authby,Trandesc,charges)"
                                            + " values('" + acNo + "',DATE_FORMAT(curdate(),'%Y%m%d')," + chgAmt + ",'INSUFFICIENT ATM CHARGE','1','2',"
                                            + "" + recNo + "," + trSNo + ",'" + usrName + "','Y','" + usrName + "','" + 118 + "','" + desc + "')");

                                    Integer insertChgVarient = insertChg.executeUpdate();
                                    if (insertChgVarient <= 0) {
                                        return "False";
                                    }
                                }
                            }
                        }
                    } else {
                        for (AtmChargePostingPojo chargesObj : rList) {
                            String acNo = chargesObj.getAcno();
                            String acNature = ftsRemote.getAccountNature(acNo);
                            double chgAmt = Double.parseDouble(chargesObj.getCharges());
                            double tchAmt = chgAmt;
                            float recNo = 0f;
                            if (chgAmt >= 0) {
                                tchAmt = chgAmt;
                                msg = ftsRemote.checkBalance(acNo, tchAmt, usrName);
                                String taxDetail = desc + ":" + taxName;
                                if (msg.equalsIgnoreCase("True")) {
                                    recNo = ftsRemote.getRecNo();
                                    msg = ftsRemote.insertRecons(acNature, acNo, 1, chgAmt, curDate, curDate, 2, desc + fromDt + "To " + toDt, usrName, trSNo, null, recNo, "Y", usrName, 118, 3, null,
                                            null, (float) 0, null, "A", 1, null, null, null, null, glAcNo.substring(0, 2), ftsRemote.getCurrentBrnCode(acNo), 0, null, "", "");
                                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                        return msg;
                                    }

                                    ftsRemote.updateBalance(acNature, acNo, 0, chgAmt, "Y", "Y");

                                    totAmt = totAmt + chgAmt;
                                } else {
                                    recNo = ftsRemote.getRecNo();
                                    Query insertChg = em.createNativeQuery("insert into pendingcharges(Acno,dt,amount,details,ty,trantype,recno,trsno,enterby,auth,authby,Trandesc,charges)"
                                            + " values('" + acNo + "',DATE_FORMAT(curdate(),'%Y%m%d')," + chgAmt + ",'INSUFFICIENT ATM CHARGE','1','2',"
                                            + "" + recNo + "," + trSNo + ",'" + usrName + "','Y','" + usrName + "','" + 118 + "','" + desc + "')");

                                    Integer insertChgVarient = insertChg.executeUpdate();
                                    if (insertChgVarient <= 0) {
                                        return "False";
                                    }
                                }
                            }
                        }
                    }

                    if (totAmt >= 0) {
                        String acNature = "";
                        float recNo = 0f;
                        String taxDesc = desc + taxName;
                        if (taxFlag.equalsIgnoreCase("Y")) {
//                            acNature = acNature(ftsRemote.getAccountCode(taxGlAcNo));
//                            recNo = ftsRemote.getRecNo();
//                            msg = ftsRemote.insertRecons(acNature, taxGlAcNo, 0, ttlTaxAmt, curDate, curDate, 2, taxDesc, usrName, trSNo, null, recNo, "Y", usrName,
//                                    35, 3, null, null, (float) 0, null, "A", 1, null, null, null, null, glAcNo.substring(0, 2), glAcNo.substring(0, 2), 0, null, "", "");
//                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
//                                return msg;
//                            }
//                            ftsRemote.updateBalance(acNature, taxGlAcNo, ttlTaxAmt, 0, "Y", "Y");
                            double sTaxAmt = CbsUtil.round(((ttlTaxAmt * 100) / sPerc), rUpTo);
                            map = interFts.getTaxComponent(sTaxAmt, curDate);
                            Set<Entry<String, Double>> set1 = map.entrySet();
                            Iterator<Entry<String, Double>> it1 = set1.iterator();
                            while (it1.hasNext()) {
                                Entry entry = it1.next();
                                String[] keyArray = entry.getKey().toString().split(":");
                                String description = keyArray[0];
                                String taxHead = orgnBrCode + keyArray[1];
                                String mainDetails = description.toUpperCase() + " " + desc;
                                double taxAmount = Double.parseDouble(entry.getValue().toString());

                                acNature = acNature(ftsRemote.getAccountCode(taxHead));
                                recNo = ftsRemote.getRecNo();
                                msg = ftsRemote.insertRecons(acNature, taxHead, 0, taxAmount, curDate, curDate, 2, mainDetails, usrName, trSNo, null, recNo, "Y", usrName,
                                        71, 3, null, null, (float) 0, null, "A", 1, null, null, null, null, glAcNo.substring(0, 2), glAcNo.substring(0, 2), 0, null, "", "");
                                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                    return msg;
                                }
                                ftsRemote.updateBalance(acNature, taxHead, taxAmount, 0, "Y", "Y");
                            }
                        }

                        recNo = ftsRemote.getRecNo();
                        acNature = acNature(ftsRemote.getAccountCode(glAcNo));
                        msg = ftsRemote.insertRecons(acNature, glAcNo, 0, totAmt, curDate, curDate, 2, desc + fromDt + "To " + toDt, usrName, trSNo, null, recNo, "Y", usrName,
                                118, 3, null, null, (float) 0.0, null, "A", 1, null, null, null, null, glAcNo.substring(0, 2), glAcNo.substring(0, 2), 0, null, "", "");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            return msg;
                        }
                        ftsRemote.updateBalance(acNature, glAcNo, totAmt, 0, "Y", "Y");
                    }
                }

                Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', "
                        + "POST_DT = now(), ENTER_BY = '" + usrName + "'  WHERE flag = 'AT' AND FROM_DT = '" + fromDt
                        + "' and TO_DT = '" + toDt + "' and brncode = '" + brnCode + "'");
                Integer var = updateAccTypeIntParaQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Problem in data updation.");
                }
            }
            ut.commit();
            return msg.substring(0, 4) + trSNo;
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

    public List<MinBalanceChargesPostPojo> atmChargesPostReport(float trsno, String enterBy, String enteryDt, String orgBrCode, String destBrCode) throws ApplicationException {
        List<MinBalanceChargesPostPojo> minBalanceChargesPostPojo = new ArrayList<MinBalanceChargesPostPojo>();
        try {
            String bnkName = null, bnkAddress = null;
            List objBan = common.getBranchNameandAddress(destBrCode);
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            List result = new ArrayList();

            result = em.createNativeQuery("select r.acno,r.dramt,case when r.details like '%ATM CHARGE :%' then 'ATM CHARGE :' ELSE 'ATM CHARGE' END,a.custname from recon r, accountmaster a where r.dt='" + enteryDt + "' and r.enterby='" + enterBy + "' and r.trsno=" + trsno + " and r.org_brnid='" + orgBrCode + "' and r.dest_brnid='" + destBrCode + "' and r.acno = a.acno "
                    + " union all select r.acno,r.dramt,r.details,a.custname from ca_recon r, accountmaster a where r.dt='" + enteryDt + "' and r.enterby='" + enterBy + "' and r.trsno=" + trsno + " and r.org_brnid='" + orgBrCode + "' and r.dest_brnid='" + destBrCode + "' and r.acno = a.acno order by dramt,acno").getResultList();
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    MinBalanceChargesPostPojo balCert = new MinBalanceChargesPostPojo();
                    balCert.setBnkName(bnkName);
                    balCert.setBnkAddress(bnkAddress);
                    balCert.setAcno(record.get(0).toString());
                    balCert.setDramt(Double.parseDouble(record.get(1).toString()));
                    if (record.get(2) != null) {
                        balCert.setDetails(record.get(2).toString());
                    } else {
                        balCert.setDetails("");
                    }
                    balCert.setCustName(record.get(3).toString());
                    minBalanceChargesPostPojo.add(balCert);
                }
            }
            result = em.createNativeQuery("select a.acno,a.amount,a.details,b.CUSTNAME from pendingcharges a,accountmaster b  where a.dt='" + enteryDt + "' and a.enterby='" + enterBy + "'  and a.trsno=" + trsno + " and a.acno=b.acno  and b.CurBrCode ='" + destBrCode + "'").getResultList();
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    MinBalanceChargesPostPojo balCert = new MinBalanceChargesPostPojo();
                    balCert.setBnkName(bnkName);
                    balCert.setBnkAddress(bnkAddress);
                    balCert.setAcno(record.get(0).toString());
                    balCert.setDramt(Double.parseDouble(record.get(1).toString()));
                    balCert.setDetails(record.get(2).toString());
                    balCert.setCustName(record.get(3).toString());
                    minBalanceChargesPostPojo.add(balCert);
                }
            }
            return minBalanceChargesPostPojo;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<AtmReversalPostingPojo> getAtmRevsersalData(String frDt, String toDt) throws ApplicationException {
        try {
            return em.createNativeQuery("select reversal_indicator,amount,system_trace_number,original_system_trace_number,card_number,date_format(tran_date,'%d/%m/%Y'), date_format(in_coming_date,'%d/%m/%Y'),in_process_flag,in_process_status from atm_off_us_transaction_parameter where tran_date between '" + frDt + "' and '" + toDt + "' and in_process_flag = 'p' and in_process_status = 'in-process'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    public String getPostReversalData(AtmReversalPostingPojo obj, String userName) throws ApplicationException {
        String msg = "";
        UserTransaction ut = context.getUserTransaction();
        float trSNo = 0;

        try {
            ut.begin();
            trSNo = ftsRemote.getTrsNo();
            String stain = obj.getStNo();
            String rStain = obj.getOstNo();
            String reversalIndicator = obj.getReversalIndicater();
            String valueDate = obj.getTranDate();
            String valueDt = ymd.format(dmy.parse(valueDate));
            double incomingAmt = obj.getAmount();

            List atmCashHeadList = em.createNativeQuery("SELECT atm_cash_general_head FROM atm_master where atm_id in(select TERMINAL_ID from atm_off_us_transaction_parameter where system_trace_number = '" + stain + "')").getResultList();
            Vector atmVector = (Vector) atmCashHeadList.get(0);
            String atmCashHead = atmVector.get(0).toString();

            List nfsPaidList = em.createNativeQuery("select acno from abb_parameter_info where purpose = 'NFS PAY/REC HEAD'").getResultList();
            Vector nfsVector = (Vector) nfsPaidList.get(0);
            String nfsPaid = nfsVector.get(0).toString();
            String nfsPaidHead = "90" + nfsPaid;

            List checkList = em.createNativeQuery("select * from atm_off_us_transaction_parameter_his where system_trace_number = '" + stain + "'").getResultList();
            if (!checkList.isEmpty()) {
                throw new ApplicationException("Data alreday exist !");
            }
            int result = em.createNativeQuery("insert into atm_off_us_transaction_parameter_his(processing_code, reversal_indicator, amount, terminal_id, system_trace_number, approval_code, card_number, reserved, original_system_trace_number,tran_date, tran_time, in_coming_date, in_process_flag, in_process_status, trsno, update_by, update_date)"
                    + " select processing_code, reversal_indicator, amount, terminal_id, system_trace_number, approval_code, card_number, reserved, original_system_trace_number, tran_date, tran_time, in_coming_date, in_process_flag, in_process_status, trsno , update_by, update_date from atm_off_us_transaction_parameter where system_trace_number = '" + stain + "'").executeUpdate();

            if (result <= 0) {
                throw new ApplicationException("Problem in Atm Reversal Insertion");
            }

            /*
             Withdrawal
             Atm Cash Head credit
             Nfs Paid Head debit
             * 
             */
            String acNature = "";
            if (reversalIndicator.equalsIgnoreCase("Withdrawal")) {
                acNature = acNature(ftsRemote.getAccountCode(atmCashHead));
                msg = interFts.cbsPostingCx(atmCashHead, 0, valueDt, incomingAmt, 0, 2, "NFS ATM WITHDRAWAL", 0f, "A", "", ymd.format(new Date()),
                        3, 0f, 0f, 70, "90", "90", userName, userName, ftsRemote.getTrsNo(), "", "");

                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                ftsRemote.updateBalance(acNature, atmCashHead, incomingAmt, 0, "Y", "Y");

                acNature = acNature(ftsRemote.getAccountCode(nfsPaidHead));
                msg = interFts.cbsPostingCx(nfsPaidHead, 1, valueDt, incomingAmt, 0, 2, "NFS ATM WITHDRAWAL", 0f, "A", "", ymd.format(new Date()),
                        3, 0f, 0f, 70, "90", "90", userName, userName, ftsRemote.getTrsNo(), "", "");

                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                ftsRemote.updateBalance(acNature, nfsPaidHead, 0, incomingAmt, "Y", "Y");
            }

            /*
             Reversal
             Atm Cash Head debit
             Nfs Paid Head credit
             * 
             */
            if (reversalIndicator.equalsIgnoreCase("Reversal")) {
                List checkReversal = em.createNativeQuery("select * from atm_off_us_transaction_parameter where system_trace_number = '" + rStain + "' and (in_process_flag = 'S' or in_process_status = 'SUCCESS' or trsno is not null)").getResultList();
                if (!checkReversal.isEmpty()) {
                    throw new ApplicationException("Atm Reversal Already done!");
                }
            }

            if (reversalIndicator.equalsIgnoreCase("Reversal")) {
                double txnAmt = 0d;
                List amtList = em.createNativeQuery("select ifnull(amount,0) from atm_off_us_transaction_parameter where system_trace_number = '" + rStain + "'").getResultList();
                Vector amtv = (Vector) amtList.get(0);
                double orginalAmt = Double.parseDouble(amtv.get(0).toString());
                txnAmt = orginalAmt - incomingAmt;
                if (txnAmt == 0) {
                    txnAmt = orginalAmt;
                } else {
                    txnAmt = txnAmt;
                }
                acNature = acNature(ftsRemote.getAccountCode(atmCashHead));
                msg = interFts.cbsPostingCx(atmCashHead, 1, valueDt, txnAmt, 0, 2, "ATM OFF-US REVERSAL", 0f, "A", "", ymd.format(new Date()),
                        3, 0f, 0f, 70, "90", "90", userName, userName, ftsRemote.getTrsNo(), "", "");

                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                ftsRemote.updateBalance(acNature, atmCashHead, 0, txnAmt, "Y", "Y");

                acNature = acNature(ftsRemote.getAccountCode(nfsPaidHead));
                msg = interFts.cbsPostingCx(nfsPaidHead, 0, valueDt, txnAmt, 0, 2, "ATM OFF-US REVERSAL", 0f, "A", "", ymd.format(new Date()),
                        3, 0f, 0f, 70, "90", "90", userName, userName, ftsRemote.getTrsNo(), "", "");

                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                ftsRemote.updateBalance(acNature, nfsPaidHead, txnAmt, 0, "Y", "Y");
            }

            int result1 = em.createNativeQuery("update atm_off_us_transaction_parameter set in_process_flag = 'S',in_process_status = 'SUCCESS', trsno = " + trSNo + ", update_by = '" + userName + "' , update_date = now() where system_trace_number = '" + stain + "'").executeUpdate();

            if (result1 <= 0) {
                throw new ApplicationException("Problem in Atm Reversal Updation");
            }
            ut.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());

        }
        return msg;
    }

    public String getPostAtmMissingData(String atmBranch, String atmId, String atmGlhead, double amount, String valueDt, String userName) throws ApplicationException {
        String msg = "";
        UserTransaction ut = context.getUserTransaction();
        float trSNo = 0;
        try {
            ut.begin();
            trSNo = ftsRemote.getTrsNo();
            List nfsPaidList = em.createNativeQuery("select acno from abb_parameter_info where purpose = 'NFS PAY/REC HEAD'").getResultList();
            Vector nfsVector = (Vector) nfsPaidList.get(0);
            String nfsPaid = nfsVector.get(0).toString();
            String nfsPaidHead = "90" + nfsPaid;
            String acNature = "";

            acNature = acNature(ftsRemote.getAccountCode(atmGlhead));
            msg = interFts.cbsPostingCx(atmGlhead, 0, valueDt, amount, 0, 2, "ATM MISSING ENTRY", 0f, "A", "", ymd.format(new Date()),
                    3, 0f, 0f, 70, "90", "90", userName, userName, ftsRemote.getTrsNo(), "", "");

            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            ftsRemote.updateBalance(acNature, atmGlhead, amount, 0, "Y", "Y");

            acNature = acNature(ftsRemote.getAccountCode(nfsPaidHead));
            msg = interFts.cbsPostingCx(nfsPaidHead, 1, valueDt, amount, 0, 2, "ATM MISSING ENTRY", 0f, "A", "", ymd.format(new Date()),
                    3, 0f, 0f, 70, "90", "90", userName, userName, ftsRemote.getTrsNo(), "", "");

            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            ftsRemote.updateBalance(acNature, nfsPaidHead, 0, amount, "Y", "Y");

            int result = em.createNativeQuery("INSERT INTO atm_missing_entry (ATM_BRANCH, ATM_ID, AMOUNT, TRAN_DATE, TRAN_TIME, VALUE_DATE, ENTER_BY, TRSNO) "
                    + "VALUES ('" + atmBranch + "' ,'" + atmId + "'," + amount + " , '" + ymd.format(new Date()) + "', CURRENT_TIMESTAMP,'" + valueDt + "', '" + userName + "', " + ftsRemote.getTrsNo() + ")").executeUpdate();

            if (result <= 0) {
                throw new ApplicationException("Problem in Atm missing entry Insertion");
            }
            ut.commit();

        } catch (ApplicationException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex1) {
                throw new ApplicationException(ex1.getMessage());
            }
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public String postCashChargeData(List<CashHandlingChargeGridData> cashChgDataList, String brcode, String acNature, String acType, String dtpFrom, String dtpTo, String entryby, String entrydate, String crdaccount) throws ApplicationException {
        String msg = "", desp = "", pendingMsg = "";
        int servTaxApplyCode = 0, roundUpTo = 0, roundUpToIgst = 0;
        double totalCharge = 0.0, totalStaxCharge = 0, totalRot = 0, totalChargeIgst = 0.0, totalStaxChargeIgst = 0, totalRotIgst = 0;
        UserTransaction ut = context.getUserTransaction();

        try {
            ut.begin();
            List checkPostHistList = em.createNativeQuery("Select * From post_history Where actype='" + acType + "' and "
                    + "((fromdate between '" + dtpFrom + "' and '" + dtpTo + "') or (todate "
                    + "between '" + dtpFrom + "' and '" + dtpTo + "')) and postflag = 'CH' and "
                    + "brncode = '" + brcode + "'").getResultList();
            if (checkPostHistList.size() > 0) {
                throw new ApplicationException("Cash Handling Charges Has Been Already Posted For this Period.");
            }

            List list = em.createNativeQuery("select code from cbs_parameterinfo where name ='CASH_HANDLING_CR_LIMIT'").getResultList();
            Vector vtr = (Vector) list.get(0);
            double cashHandlingCrLimit = Double.parseDouble(vtr.get(0).toString());
            float trSNo = ftsRemote.getTrsNo();
            float recno = ftsRemote.getRecNo().floatValue();
            List crHeadList = new ArrayList();

            if (acType.equalsIgnoreCase("ALL")) {
                crHeadList = em.createNativeQuery("select AC_TYPE,cr_gl_head from cbs_charge_detail where charge_name='CASH-HANDLING-CHG' "
                        + "and " + cashHandlingCrLimit + " between from_range and to_range and eff_date=(select max(eff_date) from cbs_charge_detail where "
                        + "charge_name='CASH-HANDLING-CHG' and " + cashHandlingCrLimit + " between from_range and to_range and eff_date<= '" + dtpTo + "') order by AC_TYPE").getResultList();
            } else {
                crHeadList = em.createNativeQuery("select AC_TYPE,cr_gl_head from cbs_charge_detail where charge_name='CASH-HANDLING-CHG' and ac_type='" + acType + "' "
                        + "and " + cashHandlingCrLimit + " between from_range and to_range and eff_date=(select max(eff_date) from cbs_charge_detail where "
                        + "charge_name='CASH-HANDLING-CHG' and ac_type='" + acType + "' and " + cashHandlingCrLimit + " between from_range and to_range and eff_date<= '" + dtpTo + "') order by AC_TYPE ").getResultList();
            }
            if (crHeadList.isEmpty()) {
                throw new ApplicationException("Data does not exist cbs_charge_detail !");
            }
            servTaxApplyCode = ftsRemote.getCodeForReportName("STAXMODULE_ACTIVE");
            totalRot = 0;
            totalRotIgst = 0;
            if (servTaxApplyCode == 1) {
                Map<String, Double> slabMap = interFts.getTaxComponentSlab(ymd.format(new Date()));
                Set<Map.Entry<String, Double>> set = slabMap.entrySet();
                Iterator<Map.Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = it.next();
                    String[] keyArray = entry.getKey().toString().split(":");
                    desp = keyArray[0];
                    roundUpTo = Integer.parseInt(keyArray[3]);
                    totalRot += Double.parseDouble(entry.getValue().toString());
                }
                Map<String, Double> slabMapIgst = interFts.getIgstTaxComponentSlab(ymd.format(new Date()));
                Set<Map.Entry<String, Double>> setIgst = slabMapIgst.entrySet();
                Iterator<Map.Entry<String, Double>> itIgst = setIgst.iterator();
                while (itIgst.hasNext()) {
                    Map.Entry entry = itIgst.next();
                    String[] keyArray = entry.getKey().toString().split(":");
                    desp = keyArray[0];
                    roundUpToIgst = Integer.parseInt(keyArray[3]);
                    totalRotIgst += Double.parseDouble(entry.getValue().toString());
                }
            }
            List<CashHandlingChargeGridData> chgDataList = null;
            for (int i = 0; i < crHeadList.size(); i++) {
                Vector vec = (Vector) crHeadList.get(i);
                String acctType = vec.get(0).toString();
                String crGlAcno = brcode + vec.get(1).toString();
                chgDataList = calculateCashHandlingCharges(brcode, acNature, acctType, dtpFrom, dtpTo);

                for (CashHandlingChargeGridData obj : chgDataList) {
                    String acNo = obj.getAcNo();
                    String custState = obj.getCustState();
                    String branchState = obj.getBrState();
                    double chgAmt = obj.getChargeAmt();
                    double partySerCharge = 0.0, partySerChargeIgst = 0.0, totalDramt = 0.0;
                    if (custState.equalsIgnoreCase(branchState)) {
                        partySerCharge = CbsUtil.round(((chgAmt * totalRot) / 100), roundUpTo);
                        partySerCharge = CbsUtil.round((partySerCharge / 2), 2) * 2;
                        totalDramt = CbsUtil.round(chgAmt, roundUpTo) + partySerCharge;
                    } else {
                        partySerCharge = CbsUtil.round(((chgAmt * totalRotIgst) / 100), roundUpToIgst);
                        partySerCharge = CbsUtil.round((partySerCharge / 2), 2) * 2;
                        // partySerChargeIgst = CbsUtil.round(((chgAmt * totalRotIgst) / 100), roundUpToIgst);
                        totalDramt = CbsUtil.round(chgAmt, roundUpToIgst) + partySerCharge;
                    }
                    pendingMsg = ftsRemote.checkBalance(acNo, totalDramt, entryby);
                    if (pendingMsg.equalsIgnoreCase("true")) {
                        recno = recno + 1;
                        msg = ftsRemote.insertRecons(acNature, acNo, 1, CbsUtil.round(chgAmt, roundUpTo), entrydate,
                                entrydate, 2, "Cash Handling Charge Posting", entryby, trSNo, ymd.format(new Date()),
                                recno, "Y", entryby, 119, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "",
                                brcode, brcode, 0, "", "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }

                        if (chgAmt != 0 && servTaxApplyCode == 1) {
                            recno = recno + 1;
                            String taxDetail = (custState.equalsIgnoreCase(branchState) ? "CGST:SGST" : "IGST");
                            msg = ftsRemote.insertRecons(acNature, acNo, 1, partySerCharge, entrydate,
                                    entrydate, 2, taxDetail + " Cash Handling Charge Posting", entryby, trSNo, ymd.format(new Date()),
                                    recno, "Y", entryby, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "",
                                    "", brcode, brcode, 0, "", "", "");
                            if (!msg.equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg);
                            }
                        }
                        msg = ftsRemote.updateBalance(acNature, acNo, 0.0, totalDramt, "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }

                        if (custState.equalsIgnoreCase(branchState)) {
                            totalCharge += CbsUtil.round(chgAmt, roundUpTo);
                            totalStaxCharge += partySerCharge;
                        } else {
                            totalCharge += CbsUtil.round(chgAmt, roundUpToIgst);
                            totalStaxChargeIgst += partySerCharge;
                        }

                    } else {
                        float recNo = ftsRemote.getRecNo();
                        Query insertChg = em.createNativeQuery("insert into pendingcharges(Acno,dt,amount,details,ty,trantype,recno,trsno,enterby,auth,authby,Trandesc,charges)"
                                + " values('" + acNo + "',DATE_FORMAT(curdate(),'%Y%m%d')," + CbsUtil.round(chgAmt, roundUpTo) + ",'INSUFFICIENT CASH HANDLING CHARGE','1','2',"
                                + "" + recNo + "," + trSNo + ",'" + entryby + "','Y','" + entryby + "','" + 119 + "','CASH HANDLING CHARGE')");

                        Integer insertChgVarient = insertChg.executeUpdate();
                        if (insertChgVarient <= 0) {
                            return "False";
                        }
                    }
                }

                recno = recno + 1;
                //  Double.parseDouble(formatter.format(totalCharge))
                msg = ftsRemote.insertRecons("PO", crGlAcno, 0, CbsUtil.round(totalCharge, roundUpTo), entrydate, entrydate,
                        2, "Cash Handling Charge Posting", entryby, trSNo, ymd.format(new Date()), recno,
                        "Y", entryby, 119, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", brcode, brcode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                msg = ftsRemote.updateBalance("PO", crGlAcno, CbsUtil.round(totalCharge, roundUpTo), 0.0, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                //Service head transaction 
                if (servTaxApplyCode == 1) {
                    if (totalStaxCharge != 0) {
                        double totalChgBasedOnTotalServiceTax = CbsUtil.round(((totalStaxCharge * 100) / totalRot), roundUpTo);
                        Map<String, Double> map = interFts.getTaxComponent(totalChgBasedOnTotalServiceTax, ymd.format(new Date()));
                        Set<Map.Entry<String, Double>> staxSet = map.entrySet();
                        Iterator<Map.Entry<String, Double>> staxIt = staxSet.iterator();
                        while (staxIt.hasNext()) {
                            Map.Entry entry = staxIt.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = brcode + keyArray[1];
                            double serAmount = Double.parseDouble(entry.getValue().toString());
                            recno = recno + 1;

                            description = description + " Cash Handling Charge Posting";
                            msg = ftsRemote.insertRecons("PO", taxHead, 0, serAmount, entrydate, entrydate, 2,
                                    description, entryby, trSNo, ymd.format(new Date()), recno, "Y",
                                    entryby, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", brcode, brcode, 0,
                                    "", "", "");
                            if (!msg.equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg);
                            }

                            msg = ftsRemote.updateBalance("PO", taxHead, serAmount, 0.0, "", "");
                            if (!msg.equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg);
                            }
                        }
                    }
                    if (totalStaxChargeIgst != 0) {
                        double totalChgBasedOnTotalServiceTaxIgst = CbsUtil.round(((totalStaxChargeIgst * 100) / totalRotIgst), roundUpToIgst);
                        Map<String, Double> mapIgst = interFts.getIgstTaxComponent(totalChgBasedOnTotalServiceTaxIgst, ymd.format(new Date()));
                        Set<Map.Entry<String, Double>> staxSetIgst = mapIgst.entrySet();
                        Iterator<Map.Entry<String, Double>> staxItIgst = staxSetIgst.iterator();
                        while (staxItIgst.hasNext()) {
                            Map.Entry entry = staxItIgst.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = brcode + keyArray[1];
                            double serAmount = Double.parseDouble(entry.getValue().toString());
                            recno = recno + 1;

                            description = description + " Cash Handling Charge Posting";
                            msg = ftsRemote.insertRecons("PO", taxHead, 0, serAmount, entrydate, entrydate, 2,
                                    description, entryby, trSNo, ymd.format(new Date()), recno, "Y",
                                    entryby, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", brcode, brcode, 0,
                                    "", "", "");
                            if (!msg.equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg);
                            }

                            msg = ftsRemote.updateBalance("PO", taxHead, serAmount, 0.0, "", "");
                            if (!msg.equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg);
                            }
                        }
                    }
                }
                Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + entryby + "'  "
                        + "WHERE AC_TYPE = '" + acType + "' AND FROM_DT = '" + dtpFrom + "' and TO_DT = '" + dtpTo + "' and flag = 'CH' and brncode = '" + brcode + "'");
                Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
                if (updateAccTypeIntPara == 0) {
                    throw new ApplicationException("Value doesn't updated in  LOAN_ACCTYPE_INTEREST_PARAMETER");
                }

                Query entryPostHistory = em.createNativeQuery("Insert into post_history (POSTFLAG,acType,FRoMDate,ToDate,enterBy, auth, "
                        + "tranTime,authby,brncode) values('CH','" + acType + "','" + dtpFrom + "','" + dtpTo + "','" + entryby + "','Y',now(),"
                        + "'" + entryby + "','" + brcode + "')");
                Integer entry = entryPostHistory.executeUpdate();
                if (entry <= 0) {
                    throw new ApplicationException("Data is not inserted in post_history.");
                }
            }
            ut.commit();
            msg = "true" + trSNo;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return msg;
    }

    public List<CashHandlingChargeGridData> calculateCashHandlingCharges(String brnCode, String actNature, String acType, String dtFrom, String dtTo) throws ApplicationException {
        List<CashHandlingChargeGridData> chgDataList = new ArrayList<CashHandlingChargeGridData>();
        double amtOrPerc = 0d;
        String chargeFlag = "", accountTable = "", reconTable = "";
        try {
            accountTable = ftsRemote.getAccountTable(actNature);
            reconTable = common.getTableName(actNature);
            List list = em.createNativeQuery("select code from cbs_parameterinfo where name ='CASH_HANDLING_CR_LIMIT'").getResultList();
            Vector vtr = (Vector) list.get(0);
            double cashHandlingCrLimit = Double.parseDouble(vtr.get(0).toString());
            List chgPerList = neftRtgsFacade.getChargeApplyOnCustomer("CASH-HANDLING-CHG", cashHandlingCrLimit, acType, brnCode);
            if (!chgPerList.isEmpty()) {
                Vector element = (Vector) chgPerList.get(0);
                chargeFlag = element.get(0).toString();
                amtOrPerc = Double.parseDouble(element.get(1).toString());
            } else {
                throw new ApplicationException("Data does not exist in cbs_charge_detail table.");
            }
            String cashHandlingMinChg = ftsRemote.getCodeFromCbsParameterInfo("CASH_HANDLING_MIN_CHARGE");
            String cashHandlingMaxChg = ftsRemote.getCodeFromCbsParameterInfo("CASH_HANDLING_MAX_CHARGE");
            List result = new ArrayList();
            if (acType.equalsIgnoreCase("ALL")) {
                result = em.createNativeQuery("select a.acno,a.custname,cast(ifnull(sum(cramt),0) as decimal(25,2)),a.Accttype, ifnull(cm.MailStateCode,''),ifnull(br.State,'')  "
                        + " from " + accountTable + " a," + reconTable + " b, customerid ci, cbs_customer_master_detail cm, branchmaster br   "
                        + " where  a.ACNo = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brnCode + "' as unsigned)  and "
                        + " a.curbrcode = '" + brnCode + "' and (a.closingDate is null or a.closingDate = '' or a.closingDate > '" + dtTo + "') and tranType = 0 and ty = 0 "
                        + " and a.acno = b.acno and b.dt between '" + dtFrom + "' and '" + dtTo + "'"
                        + " group by a.acno having cast(ifnull(sum(cramt),0) as decimal(25,2))> " + cashHandlingCrLimit + " order by a.Accttype,a.acno").getResultList();
            } else {
                result = em.createNativeQuery("select a.acno,a.custname,cast(ifnull(sum(cramt),0) as decimal(25,2)),a.Accttype, ifnull(cm.MailStateCode,''),ifnull(br.State,'')  "
                        + " from " + accountTable + " a," + reconTable + " b, customerid ci, cbs_customer_master_detail cm, branchmaster br    "
                        + " where   a.ACNo = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brnCode + "' as unsigned)  and "
                        + " a.curbrcode = '" + brnCode + "' and a.accttype = '" + acType + "' and (a.closingDate is null or a.closingDate = '' or a.closingDate > '" + dtTo + "') and tranType = 0 and ty = 0 "
                        + " and a.acno = b.acno and b.dt between '" + dtFrom + "' and '" + dtTo + "'"
                        + " group by a.acno having cast(ifnull(sum(cramt),0) as decimal(25,2))> " + cashHandlingCrLimit + " order by a.Accttype,a.acno").getResultList();
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector element = (Vector) result.get(i);
                    CashHandlingChargeGridData obj = new CashHandlingChargeGridData();
                    obj.setsNo(i + 1);
                    obj.setAcNo(element.get(0).toString());
                    obj.setCustName(element.get(1).toString());
                    double crAmt = Double.parseDouble(element.get(2).toString());
                    double cashHandlingChg = (crAmt * amtOrPerc) / 100;
                    if (cashHandlingChg >= Double.parseDouble(cashHandlingMinChg)
                            && cashHandlingChg <= Double.parseDouble(cashHandlingMaxChg)) {
                        obj.setChargeAmt(CbsUtil.round(cashHandlingChg, 2));
                    } else if (cashHandlingChg < Double.parseDouble(cashHandlingMinChg)) {
                        obj.setChargeAmt(Double.parseDouble(cashHandlingMinChg));
                    } else if (cashHandlingChg > Double.parseDouble(cashHandlingMinChg)) {
                        obj.setChargeAmt(Double.parseDouble(cashHandlingMaxChg));
                    }
                    obj.setDepositAmt(crAmt);
                    obj.setChargeAmtPercet(amtOrPerc);
                    obj.setChargeMinLimit(Double.parseDouble(cashHandlingMinChg));
                    obj.setChargeMaxLimit(Double.parseDouble(cashHandlingMaxChg));
                    obj.setCustState(element.get(4).toString());
                    obj.setBrState(element.get(5).toString());
                    chgDataList.add(obj);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return chgDataList;
    }

    public String getDtForCommPost(String brnCode, String want) throws ApplicationException {
        try {
            String dt = "";
            List getMaxToDtList = em.createNativeQuery("select MIN(SNO) from cbs_loan_acctype_interest_parameter where "
                    + " POST_FLAG = 'N' and BRNCODE = '" + brnCode + "' and flag = 'AC'").getResultList();
            if (getMaxToDtList.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
            }
            Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
            if (getMaxToDtVect.get(0) == null) {
                throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
            }
            int sNo = Integer.parseInt(getMaxToDtVect.get(0).toString());
            if (want.equalsIgnoreCase("f")) {
                List getFrDtList = em.createNativeQuery("select date_format(FROM_DT,'%Y%m%d') from cbs_loan_acctype_interest_parameter where "
                        + "flag ='AC' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                if (getFrDtList.isEmpty()) {
                    throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
                }
                Vector getFrDtVect = (Vector) getFrDtList.get(0);
                dt = getFrDtVect.get(0).toString();
            } else if (want.equalsIgnoreCase("t")) {
                List getFrDtList = em.createNativeQuery("select date_format(TO_DT,'%Y%m%d') from cbs_loan_acctype_interest_parameter where "
                        + "flag ='AC' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                if (getFrDtList.isEmpty()) {
                    throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
                }
                Vector getFrDtVect = (Vector) getFrDtList.get(0);
                dt = getFrDtVect.get(0).toString();
            }
            return dt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<agentCommPojo> agentCommCalculate(String fromDt, String toDt, String brnCode) throws ApplicationException {
        String minDate;
        String maxDate;
        List<agentCommPojo> agentCommList = new ArrayList<agentCommPojo>();
        try {
            Object bnkNameObj = em.createNativeQuery("select b.bankname,b.bankaddress,State from bnkadd b,branchmaster br where "
                    + "b.alphacode=br.alphacode and br.brncode=cast('" + brnCode + "' as unsigned)").getSingleResult();

            String bnkName = ((Vector) bnkNameObj).elementAt(0).toString();
            String bnkAddress = ((Vector) bnkNameObj).elementAt(1).toString();
            List finYear = em.createNativeQuery("select min(F_Year) from yearend Where YearEndFlag='N' and brncode='" + brnCode + "'").getResultList();
            if (finYear.size() > 0) {
                Vector eleType = (Vector) finYear.get(0);
                String finacialYear = eleType.get(0).toString();

                List getFinalYearValue = em.createNativeQuery("select * FROM yearend where yearendflag='N' and f_year= cast('" + finacialYear + "' as unsigned) and brncode='" + brnCode + "'").getResultList();

                if (getFinalYearValue.size() > 0) {
                    Vector finalYearValue = (Vector) getFinalYearValue.get(0);
                    minDate = (String) finalYearValue.get(0);
                    maxDate = (String) finalYearValue.get(1);

                    if (((ymd.parse(fromDt).after(ymd.parse(minDate))) || (ymd.parse(fromDt).equals(ymd.parse(minDate))))
                            && ((ymd.parse(fromDt).before(ymd.parse(maxDate))) || (ymd.parse(fromDt).equals(ymd.parse(maxDate))))
                            && ((ymd.parse(toDt).after(ymd.parse(minDate))) || (ymd.parse(toDt).equals(ymd.parse(minDate))))
                            && ((ymd.parse(toDt).before(ymd.parse(maxDate))) || (ymd.parse(toDt).equals(ymd.parse(maxDate))))) {
                    } else {
                        throw new ApplicationException("Please Check The Dates Entered");
                    }
                } else {
                    throw new ApplicationException("Values does not exists in Financial Year table");
                }
            } else {
                throw new ApplicationException("Please check the Finalcial Year");
            }
            List getAgentCode = em.createNativeQuery("select distinct agentname,ag_acno,agent_c_code from rdagent_mast "
                    + " where brncode = '" + brnCode + "' order by agcode").getResultList();

            if (!getAgentCode.isEmpty()) {
                for (int i = 0; i < getAgentCode.size(); i++) {
                    Vector agentVec = (Vector) getAgentCode.get(i);
                    String agName = agentVec.get(0).toString();
                    String agAccount = agentVec.get(1).toString();
                    int agCCode = Integer.parseInt(agentVec.get(2).toString());
                    double oAmt = 0.0;
                    double tAmt = 0.0;
                    double grAmt = 0.0;

                    List agAccountLst = em.createNativeQuery("select acno,TIMESTAMPDIFF(YEAR, openingdt,date_format(rdmatdate,'%Y%m%d')) AS difference from "
                            + " accountmaster where acno in (select acno from agentcomm_rd where agent_c_code = " + agCCode + ") "
                            + " and (closingdate is null OR closingdate = '' OR closingdate>'" + toDt + "') order by difference").getResultList();
                    if (!agAccountLst.isEmpty()) {
                        for (int j = 0; j < agAccountLst.size(); j++) {
                            Vector agentAcVec = (Vector) agAccountLst.get(j);
                            String agRdAc = agentAcVec.get(0).toString();
                            int agPrd = Integer.parseInt(agentAcVec.get(1).toString());

                            List acTot = em.createNativeQuery("select ifnull(sum(cramt),0) from rdrecon where acno in ('" + agRdAc + "') "
                                    + "and dt between '" + fromDt + "' and '" + toDt + "' and trantype <> 8").getResultList();
                            Vector acTotVec = (Vector) acTot.get(0);
                            double acAmt = Double.parseDouble(acTotVec.get(0).toString());
                            if (agPrd == 1) {
                                oAmt = oAmt + acAmt;
                            } else if (agPrd == 2) {
                                tAmt = tAmt + acAmt;
                            } else if (agPrd >= 3) {
                                grAmt = grAmt + acAmt;
                            }
                        }
                        double totComm = oAmt + tAmt + grAmt;

                        if (totComm != 0) {

                            String taxFlag = "N";
                            List parameterList = em.createNativeQuery("select code from parameterinfo_report where reportname = 'STAXMODULE_ACTIVE'").getResultList();
                            if (parameterList.size() > 0) {
                                Vector parameterVect = (Vector) parameterList.get(0);
                                String staxModuleActive = parameterVect.get(0).toString();
                                if (staxModuleActive.equalsIgnoreCase("1")) {
                                    taxFlag = "Y";
                                } else {
                                    taxFlag = "N";
                                }
                            }
                            double sPerc = 5;
                            int rUpTo = 0;
                            if (taxFlag.equalsIgnoreCase("Y")) {
                                Map map = interFts.getTaxComponentSlab(toDt);
                                Set<Entry<String, Double>> set = map.entrySet();
                                Iterator<Entry<String, Double>> it = set.iterator();
                                while (it.hasNext()) {
                                    Entry entry = it.next();
                                    //sPerc = sPerc + Double.parseDouble(entry.getValue().toString());
                                    rUpTo = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                                }
                            }
                            double toComAmt = ((oAmt * 1) / 100.0) + ((tAmt * 2) / 100.0) + ((grAmt * 2.5) / 100.0);
                            double taxAmt = Math.round(CbsUtil.round(((toComAmt * sPerc) / 100), 0));
                            agentCommPojo comPojo = new agentCommPojo();
                            comPojo.setBankName(bnkName);
                            comPojo.setBnkAdd(bnkAddress);
                            comPojo.setAgentName(agName);
                            comPojo.setAgentSbAccount(agAccount);
                            comPojo.setTotCollAmt(totComm);

                            comPojo.setCollInOneYr(oAmt);
                            comPojo.setCollInTwoYr(tAmt);
                            comPojo.setCollInGrTwoYr(grAmt);

                            comPojo.setCommInOneYr(CbsUtil.round((oAmt * 1) / 100.0, 0));
                            comPojo.setCommInTwoYr(CbsUtil.round((tAmt * 2) / 100.0, 0));
                            comPojo.setCommInGrTwoYr(CbsUtil.round((grAmt * 2.5) / 100.0, 0));
                            comPojo.setTotCommAmt(CbsUtil.round(toComAmt, 0));
                            comPojo.setTax(taxAmt);
                            comPojo.setNetAmount(CbsUtil.round((toComAmt - taxAmt), 0));
                            agentCommList.add(comPojo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return agentCommList;
    }

    @Override
    public String agentRdCommPost(List<agentCommPojo> resultList, String acType, String glAcNo, String authBy, String orgnBrCode, double total, String curDate, String fromDt, String toDt) throws ApplicationException {
        String msg = "true";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List crList = em.createNativeQuery("select acno from abb_parameter_info where PURPOSE = 'TAX-GL-HEAD'").getResultList();
            if (crList.isEmpty()) {
                throw new ApplicationException("TAX-GL-HEAD does not exist.");
            }
            Vector crVector = (Vector) crList.get(0);
            String crGlHead = orgnBrCode + crVector.get(0).toString();
            List drList = em.createNativeQuery("select acno from abb_parameter_info where PURPOSE = 'TAX-RDNETAMT-GL-HEAD'").getResultList();
            if (drList.isEmpty()) {
                throw new ApplicationException("TAX-RDNETAMT-GL-HEAD does not exist.");
            }
            Vector drVector = (Vector) drList.get(0);
            String drGlHead = orgnBrCode + drVector.get(0).toString();
            float trsNo = ftsRemote.getTrsNo();
            //float recno = ftsRemote.getRecNo().floatValue();
            double totalTax = 0, totalNetAmt = 0, totalTaxNetAmt = 0;
            String desc = "";
            for (agentCommPojo obj : resultList) {
                String agentSbAcno = obj.getAgentSbAccount();
                double tax = obj.getTax();
                double netAmt = obj.getNetAmount();
                float recNo = 0f;


                if (!orgnBrCode.equalsIgnoreCase(agentSbAcno.substring(0, 2))) {
                    desc = "NetAmt Cr Agent Sb A/c No.";
                    recNo = ftsRemote.getRecNo();
                    //recno = recno + 1;
                    msg = interFts.cbsPostingSx(agentSbAcno, 0, curDate, netAmt, 0d, 2, desc + fromDt + " To " + toDt, 0f, "A", "", "", 3, 0f, recNo, 130,
                            ftsRemote.getCurrentBrnCode(agentSbAcno), orgnBrCode, authBy, authBy, trsNo, "", "");
                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                        return msg;
                    }
                } else {
                    desc = "NetAmt Cr Agent Sb A/c No.";
                    // recno = recno + 1;
                    recNo = ftsRemote.getRecNo();
                    msg = ftsRemote.insertRecons(ftsRemote.getAccountNature(agentSbAcno), agentSbAcno, 0, netAmt, curDate, curDate, 2, desc + fromDt + " To " + toDt, authBy, trsNo, null, recNo, "Y", authBy, 130, 3, null,
                            null, (float) 0, null, "A", 1, null, null, null, null, orgnBrCode, ftsRemote.getCurrentBrnCode(agentSbAcno), 0, null, "", "");
                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                        return msg;
                    }
                }
                ftsRemote.updateBalance(ftsRemote.getAccountNature(agentSbAcno), agentSbAcno, netAmt, 0, "Y", "Y");
                desc = "Rd @Tax Cr ";
                //recno = recno + 1;
                recNo = ftsRemote.getRecNo();
                msg = ftsRemote.insertRecons(ftsRemote.getAccountNature(crGlHead), crGlHead, 0, tax, curDate, curDate, 2, desc + fromDt + "To " + toDt, authBy, trsNo, null, recNo, "Y", authBy, 130, 3, null,
                        null, (float) 0, null, "A", 1, null, null, null, null, orgnBrCode, ftsRemote.getCurrentBrnCode(agentSbAcno), 0, null, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    return msg;
                }

                ftsRemote.updateBalance(ftsRemote.getAccountNature(crGlHead), crGlHead, tax, 0, "Y", "Y");
                totalTax = totalTax + tax;
                totalNetAmt = totalNetAmt + netAmt;

            }
            totalTaxNetAmt = totalTax + totalNetAmt;
            desc = "Rd @Tax Amt and NetAmr Dr ";
            // recno = recno + 1;
            float recNo1 = ftsRemote.getRecNo();
            msg = ftsRemote.insertRecons(ftsRemote.getAccountNature(drGlHead), drGlHead, 1, totalTaxNetAmt, curDate, curDate, 2, desc + fromDt + "To " + toDt, authBy, trsNo, null, recNo1, "Y", authBy, 130, 3, null,
                    null, (float) 0, null, "A", 1, null, null, null, null, orgnBrCode, ftsRemote.getCurrentBrnCode(drGlHead), 0, null, "", "");
            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                return msg;
            }
            msg = ftsRemote.updateBalance(ftsRemote.getAccountNature(drGlHead), drGlHead, 0.0, totalTaxNetAmt, "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', "
                    + "POST_DT = now(), ENTER_BY = '" + authBy + "'  WHERE flag = 'AC' AND FROM_DT = '" + fromDt
                    + "' and TO_DT = '" + toDt + "' and brncode = '" + orgnBrCode + "'");
            Integer var = updateAccTypeIntParaQuery.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem in data updation.");
            }
            List snoList = em.createNativeQuery("select ifnull(max(sno),0)+1 from "
                    + "cbs_loan_acctype_interest_parameter").getResultList();
            Vector snoVec = (Vector) snoList.get(0);

            String[] dtArr = getNextFreqDt(fromDt, toDt);

            Query insertAccTypeIntParaQuery = em.createNativeQuery("insert into cbs_loan_acctype_interest_parameter (sno, ac_type, from_dt, to_dt, "
                    + "post_flag, dt, post_dt, brncode, enter_by, flag) values (" + Integer.parseInt(snoVec.get(0).toString()) + ", "
                    + "'09', '" + dtArr[0] + "', '" + dtArr[1] + "', 'N', now(), null, '" + orgnBrCode + "', "
                    + "'System', 'AC')");
            Integer var1 = insertAccTypeIntParaQuery.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem In Parameter Table Insertion.");
            }

            ut.commit();
            return msg + ":" + trsNo;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String[] getNextFreqDt(String frDt, String toDt) throws Exception {
        String[] arr = new String[2];
        try {
            String fromDt = CbsUtil.dateAdd(toDt, 1);
            long noOfMonth = CbsUtil.monthDiff(ymd.parse(frDt), ymd.parse(toDt));
            noOfMonth = noOfMonth == 0 ? 1 : (noOfMonth + 1);
            String tempToDt = CbsUtil.monthAdd(toDt, Integer.parseInt(String.valueOf(noOfMonth)));

            arr[0] = fromDt; //Next Fr Dt
            arr[1] = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(tempToDt.substring(4, 6)),
                    Integer.parseInt(tempToDt.substring(0, 4)))); //Next To Dt
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return arr;
    }
}