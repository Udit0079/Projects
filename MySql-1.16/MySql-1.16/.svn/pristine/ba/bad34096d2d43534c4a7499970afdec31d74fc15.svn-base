/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.ChargesObject;
import com.cbs.dto.Table;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsBulkPostingFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Shipra Gupta Modify By Dhirendra Singh Date: 19/05/2011
 */
@Stateless(mappedName = "InoperativeChargesFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class InoperativeChargesFacade implements InoperativeChargesFacadeRemote {

    @EJB
    private FtsBulkPostingFacadeRemote fts;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    NpciMgmtFacadeRemote npciRemote;
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public List getDropdownList() throws ApplicationException {
        List acctNo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Distinct AcctCode From accounttypemaster where acctNature In ('SB','CA')");
            acctNo = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acctNo;
    }

    public List<ChargesObject> inoperativeChargesCaculate(String acType, String fromDt, String toDt, String orgnBrCode, String enterBy, String entryDate) throws ApplicationException {
        List<ChargesObject> resultList = new ArrayList<ChargesObject>();
        try {
            List finYear = em.createNativeQuery("select min(F_Year) from yearend Where YearEndFlag='N' and brncode='" + orgnBrCode + "'").getResultList();
            if (finYear.size() > 0) {
                List getFinalYearValue = em.createNativeQuery("select mindate,maxdate,yearendflag,f_year From yearend Where YearEndFlag='N' and "
                        + "F_Year in (select min(F_Year) from yearend Where YearEndFlag='N' and brncode='" + orgnBrCode + "') and brncode = '"
                        + orgnBrCode + "'").getResultList();

                if (getFinalYearValue.size() > 0) {
                    Vector finalYearValue = (Vector) getFinalYearValue.get(0);
                    String minDate = (String) finalYearValue.get(0);

                    String maxDate = (String) finalYearValue.get(1);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    if (((sdf.parse(fromDt).after(sdf.parse(minDate))) || (sdf.parse(fromDt).equals(sdf.parse(minDate))))
                            && ((sdf.parse(fromDt).before(sdf.parse(maxDate))) || (sdf.parse(fromDt).equals(sdf.parse(maxDate))))
                            && ((sdf.parse(toDt).after(sdf.parse(minDate))) || (sdf.parse(toDt).equals(sdf.parse(minDate))))
                            && ((sdf.parse(toDt).before(sdf.parse(maxDate))) || (sdf.parse(toDt).equals(sdf.parse(maxDate))))) {

                        long tmpMon = (CbsUtil.monthDiff(sdf.parse(fromDt), sdf.parse(toDt)));
                        if (tmpMon > 6) {
                            return setErrorMsg(resultList, "Inoperative Charges Can Be Calculated For A Maximum Period Of SIX Months At A Time");
                        } else {
                            List checkList = em.createNativeQuery("SELECT charges,charges1 FROM parameterinfo_miscincome WHERE PURPOSE LIKE '%InOperative%' "
                                    + "AND ACCTCODE ='" + acType + "'").getResultList();
                            float inopCharges = 0f;
                            if ((checkList.size() > 0)) {
                                Vector checkVect = (Vector) checkList.get(0);
                                inopCharges = Float.parseFloat(checkVect.get(0).toString());
                            } else {
                                return setErrorMsg(resultList, "Gl Head is not define in ParameterInfo_MiscIncome");
                            }
                            List checkList1 = em.createNativeQuery("SELECT am.ACNO,am.CUSTNAME,am.OPTSTATUS," + inopCharges + ", ifnull(cm.MailStateCode,''),br.State  "
                                    + " FROM accountmaster am, customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                                    + " where am.ACNo = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + orgnBrCode + "' as unsigned)  and "
                                    + " am.ACCSTATUS = 2 AND am.OPTSTATUS = 2 and am.accttype = '" + acType + "' and am.CurBrCode = '" + orgnBrCode + "'").getResultList();
                            float total = 0f;
                            for (int i = 0; i < checkList1.size(); i++) {
                                Vector choose = (Vector) checkList1.get(i);
                                ChargesObject ipOpChargesObj = new ChargesObject();

                                ipOpChargesObj.setAcNo(choose.get(0).toString());
                                ipOpChargesObj.setCustName(choose.get(1).toString());
                                ipOpChargesObj.setOptStatus(Integer.parseInt(choose.get(2).toString()));

                                ipOpChargesObj.setPenalty(Double.parseDouble(choose.get(3).toString()));
                                ipOpChargesObj.setMsg("TRUE");
                                ipOpChargesObj.setStatus("2");
                                ipOpChargesObj.setBrState(choose.get(5).toString());
                                ipOpChargesObj.setCustState(choose.get(4).toString());
                                total = Float.parseFloat(choose.get(3).toString()) + total;
                                resultList.add(ipOpChargesObj);
                            }
                            return resultList;
                        }
                    } else {
                        return setErrorMsg(resultList, "Please Check The Dates Entered");
                    }
                } else {
                    return setErrorMsg(resultList, "Values does not exists in Financial Year table");
                }
            } else {
                return setErrorMsg(resultList, "Please check the Finalcial Year");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    private List<ChargesObject> setErrorMsg(List<ChargesObject> resultList, String msg) throws ApplicationException {
        ChargesObject ipOpChargesObj = new ChargesObject();
        ipOpChargesObj.setMsg(msg);
        resultList.add(ipOpChargesObj);
        return resultList;
    }

    public List setAcToCredit(String acType) throws ApplicationException {
        List acToCredit = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select  ifnull(glheadmisc,''),charges,charges1 from parameterinfo_miscincome where acctCode='"
                    + acType + "' and purpose like '%InOperative%'");
            acToCredit = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acToCredit;
    }

    public List setDate(String acType, String date, String orgnBrCode) throws ApplicationException {
        List dateCheck = new ArrayList();
        List toFromDt = new ArrayList();

        try {
            Query selectQuery = em.createNativeQuery("SELECT ACTYPE FROM post_history WHERE ACTYPE ='" + acType
                    + "' AND POSTFLAG ='IO' and brncode = '" + orgnBrCode + "'");
            dateCheck = selectQuery.getResultList();
            if (dateCheck.size() > 0) {
                Query selectdate = em.createNativeQuery("select ifnull(date_format(max(todate),'%Y%m%d'),DATE_FORMAT(DATE_ADD('" + date + "', INTERVAL -1 MONTH),'%Y%m%d')) from post_history where actype='"
                        + acType + "' and postflag='IO' and brncode = '" + orgnBrCode + "'");
                dateCheck = selectdate.getResultList();
                if (dateCheck.size() > 0) {
                    Vector element = (Vector) dateCheck.get(0);
                    String newFrDate = CbsUtil.dateAdd(element.get(0).toString(), 1);
                    toFromDt.add(newFrDate.substring(6) + "/" + newFrDate.substring(4, 6) + "/" + newFrDate.substring(0, 4));
                    String newToDate = ymd.format(CbsUtil.getLastDateOfMonth(ymd.parse(CbsUtil.monthAdd(element.get(0).toString(), 6))));
                    toFromDt.add(newToDate.substring(6) + "/" + newToDate.substring(4, 6) + "/" + newToDate.substring(0, 4));
                }
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(ymd.parse(date));
                int fDate = calendar.get(Calendar.MONTH);
                String fnameValue = "";
                String tnameValue = "";

                if (fDate >= 2) {
                    int year = calendar.get(Calendar.YEAR);
                    fnameValue = "01/04/" + String.valueOf(year - 1);
                    toFromDt.add(fnameValue);

                    tnameValue = "30/09/" + String.valueOf(year - 1);
                    toFromDt.add(tnameValue);
                } else {
                    int year = calendar.get(Calendar.YEAR);
                    fnameValue = "01/10/" + String.valueOf(year - 1);
                    toFromDt.add(fnameValue);

                    tnameValue = "31/03/" + String.valueOf(year);
                    toFromDt.add(tnameValue);
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return toFromDt;
    }

    public String inoperativeChargesPost(String acType, String glAcNo, String fromDt, String toDt, String authBy,
            String orgnBrCode, String todayDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "", staxMNoduleActive = "", taxFlag;
        float trSNo = 0.0f;
        try {
            ut.begin();
            List<ChargesObject> chargesList = inoperativeChargesCaculate(acType, fromDt, toDt, orgnBrCode, authBy, todayDate);
            if (chargesList.size() == 1 && !chargesList.get(0).getMsg().equals("TRUE")) {
                throw new ApplicationException(chargesList.get(0).getMsg());
            }

            List parameterList = em.createNativeQuery("select code from parameterinfo_report where "
                    + "reportname = 'STAXMODULE_ACTIVE'").getResultList();
            if (parameterList.size() > 0) {
                Vector parameterVect = (Vector) parameterList.get(0);
                staxMNoduleActive = parameterVect.get(0).toString();
                if (staxMNoduleActive.equalsIgnoreCase("1")) {
                    staxMNoduleActive = "true";
                } else {
                    staxMNoduleActive = "false";
                }
            }
            List checkPostHistList = em.createNativeQuery("Select * From post_history Where actype='" + acType + "' and "
                    + "((fromdate between '" + fromDt + "' and '" + toDt + "') or (todate "
                    + "between '" + fromDt + "' and '" + toDt + "')) and postflag = 'IO' and "
                    + "brncode = '" + orgnBrCode + "'").getResultList();
            if (checkPostHistList.size() > 0) {
                throw new ApplicationException("Inoperative Charges Has Been Already Posted For this Period.");
            }

            String tempBd = daybeginDate(orgnBrCode);
            String tmpDetails = "Inoperative Chg:" + dmy.format(ymd.parse(fromDt)) + " To " + dmy.format(ymd.parse(toDt));
            if (staxMNoduleActive.equalsIgnoreCase("True")) {
                taxFlag = "Y";
                String procExec1 = fts.ftsPostBulkDrCr(chargesList, orgnBrCode + glAcNo + "01", authBy, acType, tmpDetails, 114, 1, taxFlag,
                        "N", tempBd, authBy, orgnBrCode);

                if (procExec1.substring(0, 4).equalsIgnoreCase("true")) {
                    msg = "True";
                    trSNo = Float.parseFloat(procExec1.substring(procExec1.indexOf(":") + 1));
                } else {
                    msg = "false";
                }
            } else {
                taxFlag = "N";
                String var = fts.postInciCharges(chargesList, orgnBrCode + glAcNo + "01", fromDt, toDt, authBy, acType, tmpDetails,
                        114, orgnBrCode, authBy, todayDate);
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
            Query entryPostHistory = em.createNativeQuery("Insert into post_history (POSTFLAG,acType,FRoMDate,ToDate,enterBy, auth, "
                    + "tranTime,authby,brncode) values('IO','" + acType + "','" + fromDt + "','" + toDt + "','" + authBy + "','Y','" + tempBd + "',"
                    + "'" + authBy + "','" + orgnBrCode + "')");
            Integer entry = entryPostHistory.executeUpdate();
            if (entry <= 0) {
                throw new ApplicationException("Data is not inserted in post_history.");
            }
            ut.commit();
            //Sending Sms
//            try {
//                List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
//                for (ChargesObject it : chargesList) {
//                    if (it.getPenalty() > 0) {
//                        SmsToBatchTo to = new SmsToBatchTo();
//
//                        to.setAcNo(it.getAcNo());
//                        to.setCrAmt(0d);
//                        to.setDrAmt(it.getPenalty());
//                        to.setTranType(9);
//                        to.setTy(1);
//                        to.setTxnDt(dmy.format(ymd.parse(todayDate)));
//                        to.setTemplate(SmsType.CHARGE_WITHDRAWAL);
//
//                        smsList.add(to);
//                    }
//                }
//                smsFacade.sendSmsToBatch(smsList);
//            } catch (Exception e) {
//                System.out.println("Problem In SMS Sending To Batch In "
//                        + "Transfer Authorization." + e.getMessage());
//            }
            //End here
            return "TRANSACTION PASSED SUCCESSFULLY. " + "Transfer Batch No :- " + trSNo;
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

    /**
     * ******************************InoperativeMarkingBean
     * ******************************
     */
    public List getDropdownListInoperative() throws ApplicationException {
        List acctNo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select Acctcode from accounttypemaster where acctnature in('SB','CA')");
            acctNo = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acctNo;
    }

//    public List inoperativeMarking(String orgBrnCode, String acType, int days, String date) throws ApplicationException {
//        //UserTransaction ut = context.getUserTransaction();
//        try {
//            String dt = date;
//            List checkList;
//            List<Table> listTable = new ArrayList<Table>();
//            List secList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acType + "'").getResultList();
//            Vector accNatures = (Vector) secList.get(0);
//            String accNature = accNatures.get(0).toString();
//            if (accNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//
//                checkList = em.createNativeQuery("select distinct r.acno,date_format(max(Valuedt),'%d/%m/%Y'),custname,TIMESTAMPDIFF(DAY,date_format(max(Valuedt),'%Y%m%d'),'" + dt + "') from ca_recon r,accountmaster a"
//                        + " where SUBSTRING(a.ACNO,3,2)='" + acType + "' AND r.acno = a.acno and trantype <> 8  AND trandesc<>33 and a.accstatus <> 9 and optstatus <> 2 and Valuedt <= '" + dt + "' and a.acno "
//                        + " in( select acno from ca_recon where trantype <> 8 group by acno having (TIMESTAMPDIFF(DAY,date_format(max(Valuedt),'%Y%m%d'),'" + dt + "') > " + days + "))"
//                        + " AND a.CurBrCode ='" + orgBrnCode + "' group by r.acno,a.custname order by r.acno").getResultList();
//            } else {
//
//                checkList = em.createNativeQuery("select distinct r.acno,date_format(max(Valuedt),'%d/%m/%Y'),custname,TIMESTAMPDIFF(DAY,date_format(max(Valuedt),'%Y%m%d'),'"+dt+"') from recon r,accountmaster a"
//                        + " where SUBSTRING(a.ACNO,3,2)='" + acType + "' AND r.acno = a.acno and trantype <> 8 AND trandesc<>33 and a.accstatus <> 9 and optstatus <> 2 and Valuedt <= '" + dt + "' and a.acno "
//                        + " in( select acno from recon where trantype <> 8 group by acno having (TIMESTAMPDIFF(DAY,date_format(max(Valuedt),'%Y%m%d'),'" + dt + "') > " + days + "))"
//                        + " AND a.CurBrCode ='" + orgBrnCode + "' group by r.acno,a.custname order by r.acno").getResultList();
//            }
//
//            for (int i = 0; i < checkList.size(); i++) {
//                List listAccNo = new ArrayList();
//                Vector vecCheckList = (Vector) checkList.get(i);
//                String accountNo = vecCheckList.get(0).toString();
//                List listAcctNature = em.createNativeQuery("select acctNature from accounttypemaster where AcctCode=(select accttype from accountmaster where acno='" + accountNo + "')").getResultList();
//                Vector vecAcctNature = (Vector) listAcctNature.get(0);
//                String acctNature = vecAcctNature.get(0).toString();
//                if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                    listAccNo = em.createNativeQuery("SELECT ROUND(SUM(CRAMT-DRAMT),2) FROM ca_recon WHERE ACNO ='" + accountNo + "' AND ValueDT <='" + dt + "'").getResultList();
//                } else {
//                    listAccNo = em.createNativeQuery("SELECT ROUND(SUM(CRAMT-DRAMT),2) FROM recon WHERE ACNO ='" + accountNo + "' AND ValueDT <='" + dt + "'").getResultList();
//                }
//
//                Vector vecAccNo = (Vector) listAccNo.get(0);
//                Float amount = Float.parseFloat(vecAccNo.get(0).toString());
//                Table objTable = new Table();
//                objTable.setAcNo(vecCheckList.get(0).toString());
//                objTable.setBalance(amount);
//                objTable.setCustomerName(vecCheckList.get(2).toString());
//                objTable.setDayDiff(Integer.parseInt(vecCheckList.get(3).toString()));
//                objTable.setLastTrctionDt(vecCheckList.get(1).toString());
//                objTable.setsNo(i + 1);
//                listTable.add(objTable);
//            }
//
//            return listTable;
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//    }
    public String inoperativeMark(String orgBrnCode, String acType, List<Table> dataList, String authby, String date) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String busnessDt = fts.daybeginDate(orgBrnCode);
            for (Table obj : dataList) {
                Query updateMark = em.createNativeQuery("update accountmaster set ACCSTATUS=2,optstatus = 2,lastupdatedt = '" + busnessDt + "' where acno = '" + obj.getAcNo() + "'");
                int rs = updateMark.executeUpdate();
                if (rs <= 0) {
                    throw new ApplicationException("Problem in Marked Inoperative for the " + obj.getAcNo());
                }
                int spNo = 0;
                List maxSpNoList = em.createNativeQuery("Select max(spNo) From accountstatus").getResultList();
                if (maxSpNoList.size() > 0) {
                    Vector maxSpNoVector = (Vector) maxSpNoList.get(0);
                    spNo = Integer.parseInt(maxSpNoVector.get(0).toString());
                }
                spNo = spNo + 1;

                Query insertMark = em.createNativeQuery("INSERT INTO accountstatus (Acno,REMARK,SPFlag,DT,Effdt,enterby,Auth,authby,TranTime,SUBSTATUS)"
                        + " SELECT ACNo,'Inoperative Marked',2,'" + busnessDt + "','" + date + "','" + authby + "','Y','" + authby + "',CURRENT_TIMESTAMP,999 "
                        + " FROM accountmaster where ACNO='" + obj.getAcNo() + "' and optstatus = 2 and LASTUPDATEDT= '" + busnessDt + "'");
                int var = insertMark.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Problem in Marked Inoperative for the " + obj.getAcNo());
                }

                List listacNature = em.createNativeQuery("select acctNature from accounttypemaster where AcctCode='" + acType + "'").getResultList();
                Vector vecac = (Vector) listacNature.get(0);
                String acctNature = vecac.get(0).toString();
                if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                        || acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List list = em.createNativeQuery("select customerid,ifnull(aadhaar_no,'') as aadharNo,"
                            + "ifnull(aadhaar_lpg_acno,'') as aadharAcno from cbs_customer_master_detail cu,"
                            + "customerid id where cu.customerid = id.custid and id.acno = '" + obj.getAcNo() + "'").getResultList();
                    if (!list.isEmpty()) {
                        Vector ele = (Vector) list.get(0);
                        String custid = ele.get(0).toString();
                        String aadharNo = ele.get(1).toString();
                        String aadharMapAcno = ele.get(2).toString();

                        list = em.createNativeQuery("select cust_id from cbs_aadhar_registration where reg_type='AD' and "
                                + "status='R' and cust_id='" + custid + "' and aadhar_no='" + aadharNo + "'").getResultList();
                        if (!list.isEmpty() && aadharMapAcno.equalsIgnoreCase(obj.getAcNo())) {
                            npciRemote.aadharDeactivation(custid, aadharNo, "CA", "I", "AD", authby);
                        }
                    }
                }

                //SMS registered account inoperative /operative Marking
                List list = em.createNativeQuery("select acno from mb_subscriber_tab where acno='" + obj.getAcNo() + "' and status='1' and auth='Y' and "
                        + "auth_status='V'").getResultList();
                if (!list.isEmpty()) {
                    int res = em.createNativeQuery("insert into mb_subscriber_tab_his select (select ifnull(max(txn_id),0)+1 from mb_subscriber_tab_his where "
                            + "acno='" + obj.getAcNo() + "'), ACNO,ACNO_TYPE,MOBILE_NO,STATUS,BILL_DEDUCTION_ACNO,CASH_CR_LIMIT,CASH_DR_LIMIT,TRF_CR_LIMIT,"
                            + "TRF_DR_LIMIT,CLG_CR_LIMIT,CLG_DR_LIMIT,PIN_NO,CREATED_DATE,ENTER_BY,AUTH,AUTH_BY,AUTH_STATUS,INTEREST_LIMIT,"
                            + "CHARGE_LIMIT,UPDATE_BY,UPDATE_DT from mb_subscriber_tab where acno='" + obj.getAcNo() + "'").executeUpdate();
                    if (res <= 0) {
                        throw new ApplicationException("Problem in data insertion");
                    }

                    res = em.createNativeQuery("update mb_subscriber_tab set STATUS = '2', update_by='" + authby + "', update_dt=now() where acno='" + obj.getAcNo() + "'and auth='Y' and auth_status='V'").executeUpdate();
                    if (res <= 0) {
                        throw new ApplicationException("Problem in data updation");
                    }
                }
            }
            ut.commit();
            return "Accounts Marked Inoperative Successfully";
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
    }

    /**
     * ************************************ End
     * *******************************************
     */
    /**
     * **********************************IncidentalChargesBean
     * *************************
     */
    public List accType() throws ApplicationException {
        List varlist = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select distinct acctCode From accounttypemaster where acctcode is not "
                    + "null and acctNature in ('CA','SB')");
            varlist = selectQuery.getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return varlist;
    }

    public String getMinimumIncidentalCharges() throws ApplicationException {
        String minCharge = "0";
        try {
            List result = em.createNativeQuery("select code from parameterinfo_report where reportname='Min Incidental Charges'").getResultList();
            Vector vect = (Vector) result.get(0);
            minCharge = vect.elementAt(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return minCharge;
    }

    public String fYearData(String brCode) throws ApplicationException {
        String fYearResult = null;
        try {
            Query selectQuery = em.createNativeQuery("select min(F_Year) from yearend where yearendflag='N' and brncode='" + brCode + "'");
            List dateCheck = selectQuery.getResultList();
            if (dateCheck.size() > 0) {
                Vector element = (Vector) dateCheck.get(0);
                fYearResult = element.get(0).toString();
            }
            //fYearResult = selectQuery.getResultList().toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return fYearResult;
    }

    public List<ChargesObject> calculateCharges(String acType, int minChg, String dtFrom, String dtTo, String status, String brCode, String enterBy, String enterDate) throws ApplicationException {
        List<ChargesObject> tempTable = new ArrayList<ChargesObject>();
        try {

            long diff = CbsUtil.dayDiff(ymd.parse(dtFrom), ymd.parse(dtTo));
            if (diff < 0) {
                ChargesObject temp = new ChargesObject();
                temp.setMsg("Please Check The Dates Entered !!!");
                tempTable.add(temp);
                return tempTable;
            }
            List result = em.createNativeQuery("Select * From yearend Where YearEndFlag='N' and brncode='" + brCode + "' And '" + dtFrom
                    + "' between mindate and maxDate and '" + dtTo + "'between mindate and maxDate and F_Year In (select min(f_year) "
                    + "from yearend Where YearEndFlag='N' and brnCode='" + brCode + "')").getResultList();

            if (result.isEmpty()) {
                ChargesObject temp = new ChargesObject();
                temp.setMsg("Please Check The Dates Entered !!!");
                tempTable.add(temp);
                return tempTable;
            }

            if (status.equalsIgnoreCase("Operative")) {
                tempTable = loadOperative(acType, minChg, dtFrom, dtTo, brCode, enterBy, enterDate);

            } else if (status.equalsIgnoreCase("InOperative")) {
                tempTable = loadInOperative(acType, minChg, dtFrom, dtTo, brCode, enterBy, enterDate);
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tempTable;
    }

    public List acTypeIncidentalCharge() throws ApplicationException {
        List accountype = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct P.acctcode from parameterinfo_miscincome P,accounttypemaster A where P.acctcode = A.acctcode and PURPOSE='Incidental Charges' AND P.ACCTCODE=A.ACCTCODE");
            accountype = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return accountype;
    }

    public List<ChargesObject> calculateChargesAllBranch(String acType, int minChg, String dtFrom, String dtTo, String status, String brCode, String enterBy, String enterDate) throws ApplicationException {
        //List<ChargesObject> tempTable = new ArrayList<ChargesObject>();

        List<ChargesObject> tempTableFinal = new ArrayList<ChargesObject>();
        try {

            long diff = CbsUtil.dayDiff(ymd.parse(dtFrom), ymd.parse(dtTo));
            if (diff < 0) {
                ChargesObject temp = new ChargesObject();
                temp.setMsg("Please Check The Dates Entered !!!");
                tempTableFinal.add(temp);
                return tempTableFinal;
            }
            List brnList;
            String brnCode = "";
            if (brCode.equalsIgnoreCase("0A") || brCode.equalsIgnoreCase("90")) {
                brnList = em.createNativeQuery("select case CHAR_LENGTH(BrnCode) when 1 then concat('0',BrnCode) else BrnCode end as BrnCode from branchmaster where (closedate is null or closedate='' or closedate>'" + dtTo + "' ) order by BrnCode ").getResultList();
            } else {
                brnList = em.createNativeQuery("select case CHAR_LENGTH(BrnCode) when 1 then concat('0',BrnCode) else BrnCode end as BrnCode from branchmaster where BrnCode ='" + brCode + "' and (closedate is null or closedate='' or closedate>'" + dtTo + "') order by BrnCode ").getResultList();
            }

            for (int z = 0; z < brnList.size(); z++) {
                Vector brnVect = (Vector) brnList.get(z);
                brnCode = brnVect.get(0).toString();
                brCode = brnCode;
                List resultList = acTypeIncidentalCharge();
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    acType = ele.get(0).toString();

                    List result = em.createNativeQuery("Select * From yearend Where YearEndFlag='N' and brncode='" + brCode + "' And '" + dtFrom
                            + "' between mindate and maxDate and '" + dtTo + "'between mindate and maxDate and F_Year In (select min(f_year) "
                            + "from yearend Where YearEndFlag='N' and brnCode='" + brCode + "')").getResultList();

                    if (result.isEmpty()) {
                        ChargesObject temp = new ChargesObject();
                        temp.setMsg("Please Check The Dates Entered !!!");
                        tempTableFinal.add(temp);
                        return tempTableFinal;
                    }
                    List<ChargesObject> tempTable = new ArrayList<ChargesObject>();
                    if (status.equalsIgnoreCase("Operative")) {
                        tempTable = loadOperative(acType, minChg, dtFrom, dtTo, brCode, enterBy, enterDate);
                        tempTableFinal.addAll(tempTable);

                    } else if (status.equalsIgnoreCase("InOperative")) {
                        tempTable = loadInOperative(acType, minChg, dtFrom, dtTo, brCode, enterBy, enterDate);
                        tempTableFinal.addAll(tempTable);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tempTableFinal;
    }

    public List<ChargesObject> loadOperative(String acType, int minChg, String dtFrom, String dtTo, String brCode, String enterBy, String entryDate) throws ApplicationException {
        List<ChargesObject> tempTable = new ArrayList<ChargesObject>();
        try {
            int returnReportCode = -1;
            List code = em.createNativeQuery("Select code from parameterinfo_report where reportname='Incidental Charges' ").getResultList();
            if (!code.isEmpty()) {
                Vector recLst = (Vector) code.get(0);
                returnReportCode = Integer.parseInt(recLst.get(0).toString());
            } else {
                returnReportCode = -1;
            }

            int stCode = 0;
            List incStCode = em.createNativeQuery("Select ifnull(code,'0') from parameterinfo_report where reportname='Incidental_Staff_Charges' ").getResultList();
            if (!incStCode.isEmpty()) {
                Vector recStLst = (Vector) incStCode.get(0);
                stCode = Integer.parseInt(recStLst.get(0).toString());
            }

            Object bnkNameObj = em.createNativeQuery("select b.bankname,b.bankaddress,br.State from bnkadd b,branchmaster br where "
                    + "b.alphacode=br.alphacode and br.brncode=cast('" + brCode + "' as unsigned)").getSingleResult();

            String bnkName = ((Vector) bnkNameObj).elementAt(0).toString();
            String bnkAddress = ((Vector) bnkNameObj).elementAt(1).toString();
            String brState = ((Vector) bnkNameObj).elementAt(2).toString();

            List reslist = em.createNativeQuery("SELECT am.ACNO, SUBSTRING(am.CUSTNAME,1,40)CUSTNAME,0 PENALTY,ifnull(am.ODLIMIT,0)+ifnull(am.ADHOCLIMIT,0) odlimit,"
                    + " am.OPTSTATUS,am.ACCSTATUS, ifnull(cm.MailStateCode,''), ifnull(am.OrgnCode,'0') FROM accountmaster am, customerid ci, cbs_customer_master_detail cm where am.ACNo = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) and "
                    + " SUBSTRING(am.ACNO,3,2)='" + acType + "' AND am.CurBrCode = '" + brCode + "' "
                    + " AND am.OPTSTATUS<>2 AND am.ACCSTATUS NOT IN (9,2,15)  ORDER BY am.ACNO").getResultList();

            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acType + "'").getResultList();
            Vector recLst = (Vector) acNatList.get(0);
            String acNat = recLst.get(0).toString();

            String tableName = "";
            if ((acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) || (acNat.equalsIgnoreCase(CbsConstant.CC_AC))) {
                tableName = "ca_recon";
            } else if ((acNat.equalsIgnoreCase(CbsConstant.SAVING_AC))) {
                tableName = "recon";
            } else if ((acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) || (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (acNat.equalsIgnoreCase(CbsConstant.SS_AC)) || (acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC))) {
                tableName = "loan_recon";
            } else if ((acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || (acNat.equalsIgnoreCase(CbsConstant.MS_AC)))) {
                tableName = "td_recon";
            } else if ((acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {
                tableName = "gl_recon";
            } else if ((acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC))) {
                tableName = "rdrecon";
            } else {
                tableName = "recon";
            }
            String acctNo;
            if (!reslist.isEmpty()) {
                for (int i = 0; i < reslist.size(); i++) {
                    Vector ele = (Vector) reslist.get(i);
                    acctNo = ele.get(0).toString();
                    String custState = ele.get(6).toString();
                    int orgCode = Integer.parseInt(ele.get(7).toString());

                    if (!((stCode == 1) && (orgCode == 16))) {
                        List resList1 = new ArrayList();
                        if (returnReportCode == 99) {
                            float chg = 0.0f;
                            float penalty = 0.0f;
                            List res = em.createNativeQuery("Select Charges from parameterinfo_miscincome Where Purpose='Incidental Charges' "
                                    + "and acctCode='" + acType + "'").getResultList();

                            if (!res.isEmpty()) {
                                Vector ele1 = (Vector) res.get(0);
                                chg = Float.parseFloat(ele1.get(0).toString());
                            } else {
                                chg = 0;
                            }
                            penalty = chg;

                            ChargesObject temp = new ChargesObject();
                            temp.setMsg("True");
                            temp.setAcNo(ele.get(0).toString());
                            temp.setCustName(ele.get(1).toString());
                            temp.setPenalty(penalty);

                            temp.setLimit(Float.parseFloat(ele.get(3).toString()));
                            temp.setOptStatus(Integer.parseInt(ele.get(4).toString()));
                            temp.setStatus(ele.get(5).toString());
                            temp.setTrans(0);
                            temp.setBnkName(bnkName);
                            temp.setBnkAddress(bnkAddress);
                            temp.setBrState(brState);
                            temp.setCustState(custState);
                            tempTable.add(temp);

                        } else {
                            if ((acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                                resList1 = em.createNativeQuery("SELECT (SELECT ifnull(COUNT(*),0) FROM " + tableName + " WHERE ACNO='" + acctNo
                                        + "' AND DT >='" + dtFrom + "' AND DT<='" + dtTo + "' AND AUTH = 'Y')" + "- "
                                        + "(SELECT ifnull(COUNT(*),0)  FROM " + tableName + " WHERE ACNO='" + acctNo + "' AND DT >='" + dtFrom + "' AND DT<='" + dtTo + "' AND AUTH = 'Y' AND   TRANTYPE=8 AND TY=1)").getResultList();
                            } else {
                                resList1 = em.createNativeQuery("SELECT (SELECT ifnull(COUNT(*),0) FROM " + tableName + " WHERE ACNO='"
                                        + acctNo + "' AND DT >='" + dtFrom + "' AND DT<='" + dtTo + "' AND AUTH = 'Y')" + "- "
                                        + "(SELECT ifnull(COUNT(*),0)  FROM " + tableName + " WHERE ACNO='" + acctNo + "' AND DT >='" + dtFrom
                                        + "' AND DT<='" + dtTo + "' AND AUTH = 'Y' AND   TRANTYPE=8 AND TY=0)").getResultList();
                            }
                            Vector recLst1 = (Vector) resList1.get(0);
                            int tran = Integer.parseInt(recLst1.get(0).toString());
                            if (tran < 0) {
                                tran = 0;
                            }
                            try {
                                float chg = 0.0f;
                                float penalty = 0.0f;
                                List res = em.createNativeQuery("Select Charges from parameterinfo_miscincome Where Purpose='Incidental Charges' "
                                        + "and acctCode='" + acType + "'").getResultList();
                                if (!res.isEmpty()) {
                                    Vector ele1 = (Vector) res.get(0);
                                    chg = Float.parseFloat(ele1.get(0).toString());
                                } else {
                                    chg = 0;
                                }

                                switch (returnReportCode) {
                                    case 1:
                                        if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            penalty = ((float) tran / minChg) * minChg * chg;
                                            ChargesObject temp = new ChargesObject();
                                            temp.setMsg("True");
                                            temp.setAcNo(ele.get(0).toString());
                                            temp.setCustName(ele.get(1).toString());
                                            temp.setPenalty(penalty);

                                            temp.setLimit(Float.parseFloat(ele.get(3).toString()));
                                            temp.setOptStatus(Integer.parseInt(ele.get(4).toString()));
                                            temp.setStatus(ele.get(5).toString());
                                            temp.setTrans(tran);
                                            temp.setBnkName(bnkName);
                                            temp.setBnkAddress(bnkAddress);
                                            temp.setBrState(brState);
                                            temp.setCustState(custState);
                                            tempTable.add(temp);
                                        } else {
                                            penalty = (tran - minChg) * chg;
                                            if (penalty > 0 && tran > minChg) {
                                                ChargesObject temp = new ChargesObject();
                                                temp.setMsg("True");
                                                temp.setAcNo(ele.get(0).toString());
                                                temp.setCustName(ele.get(1).toString());
                                                if (tran > minChg) {
                                                    temp.setPenalty(penalty);
                                                } else {
                                                    temp.setPenalty(Float.parseFloat(ele.get(2).toString()));
                                                }
                                                temp.setLimit(Float.parseFloat(ele.get(3).toString()));
                                                temp.setOptStatus(Integer.parseInt(ele.get(4).toString()));
                                                temp.setStatus(ele.get(5).toString());
                                                temp.setTrans(tran);
                                                temp.setBnkName(bnkName);
                                                temp.setBnkAddress(bnkAddress);
                                                temp.setBrState(brState);
                                                temp.setCustState(custState);
                                                tempTable.add(temp);
                                            }
                                        }
                                        break;
                                    case 2:
                                        if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            penalty = (tran * chg);
                                            ChargesObject temp = new ChargesObject();
                                            temp.setMsg("True");
                                            temp.setAcNo(ele.get(0).toString());
                                            temp.setCustName(ele.get(1).toString());
                                            if (penalty < minChg) {
                                                temp.setPenalty(minChg);
                                            } else {
                                                temp.setPenalty(penalty);
                                            }
                                            temp.setLimit(Float.parseFloat(ele.get(3).toString()));
                                            temp.setOptStatus(Integer.parseInt(ele.get(4).toString()));
                                            temp.setStatus(ele.get(5).toString());
                                            temp.setTrans(tran);
                                            temp.setBnkName(bnkName);
                                            temp.setBnkAddress(bnkAddress);
                                            temp.setBrState(brState);
                                            temp.setCustState(custState);
                                            tempTable.add(temp);
                                        } else {
                                            penalty = (tran - minChg) * chg;
                                            if (penalty > 0 && tran > minChg) {
                                                ChargesObject temp = new ChargesObject();
                                                temp.setMsg("True");
                                                temp.setAcNo(ele.get(0).toString());
                                                temp.setCustName(ele.get(1).toString());
                                                if (tran > minChg) {
                                                    temp.setPenalty(penalty);
                                                } else {
                                                    temp.setPenalty(Float.parseFloat(ele.get(2).toString()));
                                                }
                                                temp.setLimit(Float.parseFloat(ele.get(3).toString()));
                                                temp.setOptStatus(Integer.parseInt(ele.get(4).toString()));
                                                temp.setStatus(ele.get(5).toString());
                                                temp.setTrans(tran);
                                                temp.setBnkName(bnkName);
                                                temp.setBnkAddress(bnkAddress);
                                                temp.setBrState(brState);
                                                temp.setCustState(custState);
                                                tempTable.add(temp);
                                            }
                                        }
                                        break;
                                    case 3:
                                        if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            penalty = chg;
                                            ChargesObject temp = new ChargesObject();
                                            temp.setMsg("True");
                                            temp.setAcNo(ele.get(0).toString());
                                            temp.setCustName(ele.get(1).toString());
                                            temp.setPenalty(penalty);
                                            temp.setLimit(Float.parseFloat(ele.get(3).toString()));
                                            temp.setOptStatus(Integer.parseInt(ele.get(4).toString()));
                                            temp.setStatus(ele.get(5).toString());
                                            temp.setTrans(tran);
                                            temp.setBnkName(bnkName);
                                            temp.setBnkAddress(bnkAddress);
                                            temp.setBrState(brState);
                                            temp.setCustState(custState);
                                            tempTable.add(temp);
                                        } else {
                                            penalty = chg;
                                            //if (penalty > 0 && tran > minChg) {
                                            ChargesObject temp = new ChargesObject();
                                            temp.setMsg("True");
                                            temp.setAcNo(ele.get(0).toString());
                                            temp.setCustName(ele.get(1).toString());
                                            //if (tran > minChg) {
                                            temp.setPenalty(penalty);
                                                //} else {
                                            //    temp.setPenalty(Float.parseFloat(ele.get(2).toString()));
                                            //}
                                            temp.setLimit(Float.parseFloat(ele.get(3).toString()));
                                            temp.setOptStatus(Integer.parseInt(ele.get(4).toString()));
                                            temp.setStatus(ele.get(5).toString());
                                            temp.setTrans(tran);
                                            temp.setBnkName(bnkName);
                                            temp.setBnkAddress(bnkAddress);
                                            temp.setBrState(brState);
                                            temp.setCustState(custState);
                                            tempTable.add(temp);
                                            //}
                                        }
                                        break;
                                    case 4:
                                        if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            List list = em.createNativeQuery("select (a.bal) as bal from (select v.selected_date, (select cast(ifnull(sum(cramt-dramt),0) as decimal(15,2)) "
                                                    + "from "+ tableName +" where acno = '"+ acctNo+"' and dt<=v.selected_date ) as bal from (select date as selected_date from cbs_bankdays where "
                                                    + "date between '"+dtFrom +"' and '"+ dtTo+"') v where v.selected_date between '"+ dtFrom+"' and '" + dtTo +"' )a where bal < 100000").getResultList();
                                            
                                            if (!list.isEmpty()) {
                                                penalty = (tran * chg);
                                                ChargesObject temp = new ChargesObject();
                                                temp.setMsg("True");
                                                temp.setAcNo(ele.get(0).toString());
                                                temp.setCustName(ele.get(1).toString());
                                                if (penalty < minChg) {
                                                    temp.setPenalty(minChg);
                                                } else {
                                                    temp.setPenalty(penalty);
                                                }
                                                temp.setLimit(Float.parseFloat(ele.get(3).toString()));
                                                temp.setOptStatus(Integer.parseInt(ele.get(4).toString()));
                                                temp.setStatus(ele.get(5).toString());
                                                temp.setTrans(tran);
                                                temp.setBnkName(bnkName);
                                                temp.setBnkAddress(bnkAddress);
                                                temp.setBrState(brState);
                                                temp.setCustState(custState);
                                                tempTable.add(temp);
                                            }
                                        } else {
                                            penalty = (tran - minChg) * chg;
                                            if (penalty > 0 && tran > minChg) {
                                                ChargesObject temp = new ChargesObject();
                                                temp.setMsg("True");
                                                temp.setAcNo(ele.get(0).toString());
                                                temp.setCustName(ele.get(1).toString());
                                                if (tran > minChg) {
                                                    temp.setPenalty(penalty);
                                                } else {
                                                    temp.setPenalty(Float.parseFloat(ele.get(2).toString()));
                                                }
                                                temp.setLimit(Float.parseFloat(ele.get(3).toString()));
                                                temp.setOptStatus(Integer.parseInt(ele.get(4).toString()));
                                                temp.setStatus(ele.get(5).toString());
                                                temp.setTrans(tran);
                                                temp.setBnkName(bnkName);
                                                temp.setBnkAddress(bnkAddress);
                                                temp.setBrState(brState);
                                                temp.setCustState(custState);
                                                tempTable.add(temp);
                                            }
                                        }
                                        break;
                                    default:
                                        ChargesObject temp = new ChargesObject();
                                        temp.setMsg("Invalid Report Code !!!");
                                        tempTable.add(temp);
                                        return tempTable;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return tempTable;
    }

    public List<ChargesObject> loadInOperative(String acType, int minChg, String dtFrom, String dtTo, String brCode, String enterBy, String entryDate) throws ApplicationException {
        List<ChargesObject> tempTable = new ArrayList<ChargesObject>();

        try {

            int stCode = 0;
            List incStCode = em.createNativeQuery("Select ifnull(code,'0') from parameterinfo_report where reportname='Incidental_Staff_Charges' ").getResultList();
            if (!incStCode.isEmpty()) {
                Vector recStLst = (Vector) incStCode.get(0);
                stCode = Integer.parseInt(recStLst.get(0).toString());
            }

            Object bnkNameObj = em.createNativeQuery("select b.bankname,b.bankaddress,ifnull(br.State,'') from bnkadd b,branchmaster br where "
                    + "b.alphacode=br.alphacode and br.brncode=cast('" + brCode + "' as unsigned)").getSingleResult();

            String bnkName = ((Vector) bnkNameObj).elementAt(0).toString();
            String bnkAddress = ((Vector) bnkNameObj).elementAt(1).toString();
            String brState = ((Vector) bnkNameObj).elementAt(2).toString();
            List reslist = em.createNativeQuery("SELECT am.ACNO, SUBSTRING(am.CUSTNAME,1,40)CUSTNAME,0 PENALTY,ifnull(am.ODLIMIT,0)+ifnull(am.ADHOCLIMIT,0) odlimit,am.OPTSTATUS,am.ACCSTATUS, ifnull(cm.MailStateCode,''),ifnull(am.OrgnCode,'0') "
                    + " FROM accountmaster am, customerid ci, cbs_customer_master_detail cm where am.ACNo = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) and "
                    + " SUBSTRING(am.ACNO,3,2)='" + acType + "' AND am.CurBrCode = '" + brCode + "' AND am.OPTSTATUS=2 AND am.ACCSTATUS=2  ORDER BY am.ACNO").getResultList();
            String acctNo, custState;
            if (!reslist.isEmpty()) {
                String tableName = "";
                for (int i = 0; i < reslist.size(); i++) {
                    Vector ele = (Vector) reslist.get(i);
                    acctNo = ele.get(0).toString();
                    custState = ele.get(6).toString();
                    int orgCode = Integer.parseInt(ele.get(7).toString());
                    if (!((stCode == 1) && (orgCode == 16))) {
                        List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acType + "'").getResultList();
                        Vector recLst = (Vector) acNatList.get(0);
                        String acNat = recLst.get(0).toString();
                        if ((acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) || (acNat.equalsIgnoreCase(CbsConstant.CC_AC))) {
                            tableName = "ca_recon";
                        } else if ((acNat.equalsIgnoreCase(CbsConstant.SAVING_AC))) {
                            tableName = "recon";
                        } else if ((acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) || (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (acNat.equalsIgnoreCase(CbsConstant.SS_AC)) || (acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC))) {
                            tableName = "loan_recon";
                        } else if ((acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || (acNat.equalsIgnoreCase(CbsConstant.MS_AC)))) {
                            tableName = "td_recon";
                        } else if ((acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {
                            tableName = "gl_recon";
                        } else if ((acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC))) {
                            tableName = "rdrecon";
                        } else {
                            tableName = "recon";
                        }
                        List resList1 = new ArrayList();
                        if ((acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                            resList1 = em.createNativeQuery("SELECT (SELECT ifnull(COUNT(*),0) FROM " + tableName + " WHERE ACNO='" + acctNo
                                    + "' AND DT >='" + dtFrom + "' AND DT<='" + dtTo + "' AND AUTH = 'Y')" + "- " + "(SELECT ifnull(COUNT(*),0)  FROM "
                                    + tableName + " WHERE ACNO='" + acctNo + "' AND DT >='" + dtFrom + "' AND DT<='" + dtTo + "' AND AUTH = 'Y' AND   "
                                    + "TRANTYPE=8 AND TY=1)").getResultList();
                        } else {
                            resList1 = em.createNativeQuery("SELECT (SELECT ifnull(COUNT(*),0) FROM " + tableName + " WHERE ACNO='" + acctNo
                                    + "' AND DT >='" + dtFrom + "' AND DT<='" + dtTo + "' AND AUTH = 'Y')" + "- " + "(SELECT ifnull(COUNT(*),0)  FROM "
                                    + tableName + " WHERE ACNO='" + acctNo + "' AND DT >='" + dtFrom + "' AND DT<='" + dtTo + "' AND AUTH = 'Y' AND  "
                                    + "TRANTYPE=8 AND TY=0)").getResultList();
                        }
                        Vector recLst1 = (Vector) resList1.get(0);
                        int tran = Integer.parseInt(recLst1.get(0).toString());
                        if (tran < 0) {
                            tran = 0;
                        }

                        try {
                            float chg = 0.0f;
                            List res = em.createNativeQuery("Select Charges from parameterinfo_miscincome "
                                    + " Where Purpose='Incidental Charges' and acctCode='" + acType + "'").getResultList();
                            if (!res.isEmpty()) {
                                Vector ele1 = (Vector) res.get(0);
                                chg = Float.parseFloat(ele1.get(0).toString());
                            } else {
                                chg = 0;
                            }

                            ChargesObject temp = new ChargesObject();
                            temp.setAcNo(ele.get(0).toString());
                            temp.setCustName(ele.get(1).toString());
                            temp.setPenalty(chg);
                            temp.setMsg("True");
                            temp.setLimit(Float.parseFloat(ele.get(3).toString()));
                            temp.setOptStatus(Integer.parseInt(ele.get(4).toString()));
                            temp.setStatus(ele.get(5).toString());
                            temp.setTrans(tran);
                            temp.setBnkName(bnkName);
                            temp.setBnkAddress(bnkAddress);
                            temp.setBrState(brState);
                            temp.setCustState(custState);
                            tempTable.add(temp);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                ChargesObject temp = new ChargesObject();
                temp.setMsg("Transaction does not exist.");
                tempTable.add(temp);
                return tempTable;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tempTable;
    }

    public String crAccount(String acctype) throws ApplicationException {
        String secListed = "";
        try {
            List secList = em.createNativeQuery("Select GlHeadMisc from parameterinfo_miscincome where acctCode='" + acctype
                    + "' and purpose='Incidental Charges'").getResultList();
            if (secList == null || secList.isEmpty()) {
                secListed = "";
            } else {
                Vector secLst = (Vector) secList.get(0);
                secListed = secLst.get(0).toString();
            }
            return secListed;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String crAccountAll(String acctype) throws ApplicationException {
        String secListed = "";
        try {
            List secList = em.createNativeQuery("Select distinct GlHeadMisc from parameterinfo_miscincome where purpose='Incidental Charges'").getResultList();
            if (secList == null || secList.isEmpty()) {
                secListed = "";
            } else {
                Vector secLst = (Vector) secList.get(0);
                secListed = secLst.get(0).toString();
            }
            return secListed;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String postData(String brcode, String entryby, String entrydate, String crdaccount, String acType,
            String dtpFrom, String dtpTo, int minChg, String status) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
            String TmpDetails = "Inci Chg. From: " + dtpFrom + " To:" + dtpTo;
            List<ChargesObject> chargesList = calculateCharges(acType, minChg, dtpFrom, dtpTo, status, brcode,
                    entryby, entrydate);
            if (!(chargesList.size() > 0 && chargesList.get(0).getMsg().equalsIgnoreCase("TRUE"))) {
                return chargesList.get(0).getMsg();
            }

            ut.begin();
            if (crdaccount == null || crdaccount.equalsIgnoreCase("")) {
                throw new ApplicationException("Please Fill Gl Parameter In ParameterInfo_MiscIncome");
            }
            String postflag = "3";
            if (status.equals("InOperative")) {
                postflag = "4";
            }

            List postHistoryList = em.createNativeQuery("select date_format(fromdate,'%Y%m%d'),date_format(todate,'%Y%m%d') "
                    + "from post_history where PostFlag ='" + postflag + "'  and actype = '" + acType + "' and "
                    + "brncode = '" + brcode + "'").getResultList();
            if (postHistoryList.size() > 0) {
                for (int i = 0; i < postHistoryList.size(); i++) {
                    Vector postHistoryVector = (Vector) postHistoryList.get(i);
                    Long fromDate = Long.parseLong(postHistoryVector.get(0).toString());
                    Long toDate = Long.parseLong(postHistoryVector.get(1).toString());
                    Long userFromDate = Long.parseLong(dtpFrom);
                    if ((userFromDate.compareTo(fromDate) > 0 || userFromDate.compareTo(fromDate) == 0)
                            && userFromDate.compareTo(toDate) < 0) {
                        throw new ApplicationException("Incidental charges has been already posted.");
                    }
                    if (userFromDate.compareTo(toDate) < 0 || userFromDate.compareTo(toDate) == 0) {
                        throw new ApplicationException("Incidental charges has been already posted.");
                    }
                }
            }

            Query insertPostHistory = em.createNativeQuery("insert into post_history "
                    + "values('" + postflag + "','" + acType + "','" + dtpTo + "','" + dtpFrom + "','" + entryby + "','Y',NOW(),"
                    + "'" + entryby + "','" + brcode + "')");
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

            String procExec = fts.ftsPostBulkDrCr(chargesList, brcode + crdaccount + "01", entryby, acType,
                    TmpDetails, 115, 1, staxModuleActive, "N", entrydate, entryby, brcode);
            String proc = procExec.substring(0, procExec.indexOf(":"));
            if (!proc.equalsIgnoreCase("TRUE")) {
                throw new ApplicationException("Problem in posting-->" + procExec);
            }

            if (procExec.contains(":")) {
                String[] values = null;
                String spliter = ":";

                values = procExec.split(spliter);
                String Trsno = values[1].toString();
                msg = "Transaction posted successfully and transfer batch no. :-" + Trsno;
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
                            to.setTxnDt(dmy.format(ymd.parse(entrydate)));
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
//                return msg;
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return msg;
    }

    public String postDataAllBranch(String brcode, String entryby, String entrydate, String crdaccount, String acType,
            String dtpFrom, String dtpTo, int minChg, String status) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
            String TmpDetails = "Inci Chg. From: " + dtpFrom + " To:" + dtpTo;
            List<ChargesObject> chargesList = calculateChargesAllBranch(acType, minChg, dtpFrom, dtpTo, status, brcode,
                    entryby, entrydate);
            if (!(chargesList.size() > 0 && chargesList.get(0).getMsg().equalsIgnoreCase("TRUE"))) {
                return chargesList.get(0).getMsg();
            }

            ut.begin();
            String postflag = "3";
            if (status.equals("InOperative")) {
                postflag = "4";
            }

            Map<String, String> mapBranch = new HashMap<String, String>();
            for (ChargesObject incChgObj : chargesList) {
                if (incChgObj.getAcNo() != null) {
                    if (!mapBranch.containsKey(incChgObj.getAcNo().substring(0, 2))) { //Present Branch                       
                        mapBranch.put(incChgObj.getAcNo().substring(0, 2), incChgObj.getAcNo().substring(0, 2));
                    }
                }
            }

            Map<String, String> acTypeMap = new HashMap<String, String>();
            for (ChargesObject incChgObj : chargesList) {
                if (incChgObj.getAcNo() != null) {
                    if (!acTypeMap.containsKey(incChgObj.getAcNo().substring(2, 4))) { //Present Branch                       
                        acTypeMap.put(incChgObj.getAcNo().substring(2, 4), incChgObj.getAcNo().substring(2, 4));
                    }
                }
            }

            Set<Map.Entry<String, String>> setBranch = mapBranch.entrySet();
            Iterator<Map.Entry<String, String>> itBranch = setBranch.iterator();
            List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
            String TotalTrsNo = "";
            while (itBranch.hasNext()) {
                Map.Entry<String, String> entryBranch = itBranch.next();
                brcode = entryBranch.getValue();

                Set<Map.Entry<String, String>> setAcType = acTypeMap.entrySet();
                Iterator<Map.Entry<String, String>> itAcType = setAcType.iterator();
                while (itAcType.hasNext()) {
                    Map.Entry<String, String> entryAcType = itAcType.next();
                    acType = entryAcType.getValue();

                    crdaccount = crAccount(acType);
                    if (crdaccount == null || crdaccount.equalsIgnoreCase("")) {
                        throw new ApplicationException("Please Fill Gl Parameter In ParameterInfo_MiscIncome");
                    }

                    List<ChargesObject> acTypeChargesList = new ArrayList<>();
                    for (int m = 0; m < chargesList.size(); m++) {
                        if (chargesList.get(m).getAcNo() != null) {
                            if (chargesList.get(m).getAcNo().substring(0, 2).equalsIgnoreCase(brcode) && chargesList.get(m).getAcNo().substring(2, 4).equalsIgnoreCase(acType)) {
                                acTypeChargesList.add(chargesList.get(m));
                            }
                        }
                    }
                    // System.out.println(acTypeChargesList.size());
                    if (acTypeChargesList.size() > 0) {
                        List postHistoryList = em.createNativeQuery("select date_format(fromdate,'%Y%m%d'),date_format(todate,'%Y%m%d') "
                                + "from post_history where PostFlag ='" + postflag + "'  and actype = '" + acType + "' and "
                                + "brncode = '" + brcode + "'").getResultList();
                        if (postHistoryList.size() > 0) {
                            for (int i = 0; i < postHistoryList.size(); i++) {
                                Vector postHistoryVector = (Vector) postHistoryList.get(i);
                                Long fromDate = Long.parseLong(postHistoryVector.get(0).toString());
                                Long toDate = Long.parseLong(postHistoryVector.get(1).toString());
                                Long userFromDate = Long.parseLong(dtpFrom);
                                if ((userFromDate.compareTo(fromDate) > 0 || userFromDate.compareTo(fromDate) == 0)
                                        && userFromDate.compareTo(toDate) < 0) {
                                    throw new ApplicationException("Incidental charges has been already posted.");
                                }
                                if (userFromDate.compareTo(toDate) < 0 || userFromDate.compareTo(toDate) == 0) {
                                    throw new ApplicationException("Incidental charges has been already posted.");
                                }
                            }
                        }

                        Query insertPostHistory = em.createNativeQuery("insert into post_history "
                                + "values('" + postflag + "','" + acType + "','" + dtpTo + "','" + dtpFrom + "','" + entryby + "','Y',NOW(),"
                                + "'" + entryby + "','" + brcode + "')");
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

                        String procExec = fts.ftsPostBulkDrCr(acTypeChargesList, brcode + crdaccount + "01", entryby, acType,
                                TmpDetails, 115, 1, staxModuleActive, "N", entrydate, entryby, brcode);
                        String proc = procExec.substring(0, procExec.indexOf(":"));
                        if (!proc.equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException("Problem in posting-->" + procExec);
                        }

                        if (procExec.contains(":")) {
                            String[] values = null;
                            String spliter = ":";

                            values = procExec.split(spliter);
                            String Trsno = values[1].toString();
                            TotalTrsNo = TotalTrsNo.concat(",".concat(String.valueOf(Trsno)));
                            msg = "Transaction posted successfully and transfer batch no. :-" + TotalTrsNo;

                            //Sending Sms
                            for (ChargesObject it : acTypeChargesList) {
                                if (it.getPenalty() > 0) {
                                    SmsToBatchTo to = new SmsToBatchTo();

                                    to.setAcNo(it.getAcNo());
                                    to.setCrAmt(0d);
                                    to.setDrAmt(it.getPenalty());
                                    to.setTranType(9);
                                    to.setTy(1);
                                    to.setTxnDt(dmy.format(ymd.parse(entrydate)));
                                    to.setTemplate(SmsType.CHARGE_WITHDRAWAL);

                                    smsList.add(to);
                                }
                            }
                        }
                    }
                }
            }
            ut.commit();
            try {
                smsFacade.sendSmsToBatch(smsList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Transfer Authorization." + e.getMessage());
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return msg;
    }

    public List<ChargesObject> reportPrintDetail(float trsno, String acType, String enterBy, String enterDt, String brCode) throws ApplicationException {
        List resultLt, resultLt1 = new ArrayList();
        List<ChargesObject> tempTable = new ArrayList<ChargesObject>();
        try {
            String acNat = fts.acNature(acType);
            Object bnkNameObj = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where "
                    + "b.alphacode=br.alphacode and br.brncode=cast('" + brCode + "' as unsigned)").getSingleResult();

            String bnkName = ((Vector) bnkNameObj).elementAt(0).toString();
            String bnkAddress = ((Vector) bnkNameObj).elementAt(1).toString();

            if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                resultLt = em.createNativeQuery("select r.acno,r.dramt,r.details,a.custname from recon r, accountmaster a where r.dt='" + enterDt + "' "
                        + " and r.enterby='" + enterBy + "' and r.trsno=" + trsno + " and r.org_brnid='" + brCode + "' and r.dest_brnid='" + brCode + "' "
                        + " and a.acno = r.acno order by r.dramt,r.ACNO").getResultList();
                if (!resultLt.isEmpty()) {
                    for (int i = 0; i < resultLt.size(); i++) {
                        Vector ele = (Vector) resultLt.get(i);
                        ChargesObject temp = new ChargesObject();
                        temp.setBnkName(bnkName);
                        temp.setBnkAddress(bnkAddress);
                        temp.setAcNo(ele.get(0).toString());
                        temp.setCustName(ele.get(3).toString());
                        temp.setPenalty(Double.parseDouble(ele.get(1).toString()));
                        if (ele.get(2).toString().equalsIgnoreCase("INSUFFICIENT FUND")) {
                            temp.setStatus(ele.get(2).toString());
                        } else if (ele.get(2).toString().length() > 36) {
                            temp.setStatus(ele.get(2).toString().substring(36));
                        } else {
                            temp.setStatus("POSTED");
                        }
                        temp.setMsg("TRUE");
                        tempTable.add(temp);
                    }

                    resultLt1 = em.createNativeQuery("select r.acno,r.amount,r.details,a.custname from pendingcharges r, accountmaster a "
                            + " where r.dt='" + enterDt + "' and r.enterby='" + enterBy + "' and r.trsno=" + trsno + " and substring(r.acno,1,2)='" + brCode + "' "
                            + " and a.acno = r.acno").getResultList();
                    if (!resultLt1.isEmpty()) {
                        for (int i = 0; i < resultLt1.size(); i++) {
                            Vector ele = (Vector) resultLt1.get(i);
                            ChargesObject temp1 = new ChargesObject();
                            temp1.setBnkName(bnkName);
                            temp1.setBnkAddress(bnkAddress);
                            temp1.setAcNo(ele.get(0).toString());
                            temp1.setCustName(ele.get(3).toString());
                            temp1.setPenalty(Double.parseDouble(ele.get(1).toString()));
                            if (ele.get(2).toString().equalsIgnoreCase("INSUFFICIENT FUND")) {
                                temp1.setStatus(ele.get(2).toString());
                            } else if (ele.get(2).toString().length() > 36) {
                                temp1.setStatus(ele.get(2).toString().substring(36));
                            } else {
                                temp1.setStatus("POSTED");
                            }
                            temp1.setMsg("TRUE");
                            tempTable.add(temp1);
                        }
                    }
                } else {
                    ChargesObject temp = new ChargesObject();
                    temp.setMsg("Report could not be printed.");
                    tempTable.add(temp);
                    return tempTable;
                }
            } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                resultLt = em.createNativeQuery("select r.acno,r.dramt,r.details,a.custname from ca_recon r, accountmaster a where r.dt='" + enterDt + "' "
                        + " and r.enterby='" + enterBy + "' and r.trsno=" + trsno + " and r.org_brnid='" + brCode + "' and r.dest_brnid='" + brCode + "' "
                        + " and a.acno = r.acno order by r.dramt,r.ACNO").getResultList();
                if (!resultLt.isEmpty()) {
                    for (int i = 0; i < resultLt.size(); i++) {
                        Vector ele = (Vector) resultLt.get(i);
                        ChargesObject temp = new ChargesObject();
                        temp.setBnkName(bnkName);
                        temp.setBnkAddress(bnkAddress);
                        temp.setAcNo(ele.get(0).toString());
                        temp.setCustName(ele.get(3).toString());
                        temp.setPenalty(Double.parseDouble(ele.get(1).toString()));
                        if (ele.get(2).toString().equalsIgnoreCase("INSUFFICIENT FUND")) {
                            temp.setStatus(ele.get(2).toString());
                        } else if (ele.get(2).toString().length() > 36) {
                            temp.setStatus(ele.get(2).toString().substring(36));
                        } else {
                            temp.setStatus("POSTED");
                        }
                        temp.setMsg("TRUE");
                        tempTable.add(temp);
                    }

                    resultLt1 = em.createNativeQuery("select r.acno,r.amount,r.details,a.custname from pendingcharges r, accountmaster a "
                            + " where r.dt='" + enterDt + "' and r.enterby='" + enterBy + "' and r.trsno=" + trsno + " and substring(r.acno,1,2)='" + brCode + "' "
                            + " and a.acno = r.acno").getResultList();
                    if (!resultLt1.isEmpty()) {
                        for (int i = 0; i < resultLt1.size(); i++) {
                            Vector ele = (Vector) resultLt1.get(i);
                            ChargesObject temp1 = new ChargesObject();
                            temp1.setBnkName(bnkName);
                            temp1.setBnkAddress(bnkAddress);
                            temp1.setAcNo(ele.get(0).toString());
                            temp1.setCustName(ele.get(3).toString());
                            temp1.setPenalty(Double.parseDouble(ele.get(1).toString()));
                            if (ele.get(2).toString().equalsIgnoreCase("INSUFFICIENT FUND")) {
                                temp1.setStatus(ele.get(2).toString());
                            } else if (ele.get(2).toString().length() > 36) {
                                temp1.setStatus(ele.get(2).toString().substring(36));
                            } else {
                                temp1.setStatus("POSTED");
                            }
                            temp1.setMsg("TRUE");
                            tempTable.add(temp1);
                        }
                    }
                } else {
                    ChargesObject temp = new ChargesObject();
                    temp.setMsg("Report could not be printed.");
                    tempTable.add(temp);
                    return tempTable;
                }
            }
        } catch (Exception e) {

            throw new ApplicationException(e);
        }
        return tempTable;
    }

    public List<ChargesObject> reportPrintDetailAllBranch(float trsno, String acType, String enterBy, String enterDt, String brCode) throws ApplicationException {
        List resultLt, resultLt1 = new ArrayList();
        List<ChargesObject> tempTable = new ArrayList<ChargesObject>();
        try {
            // String acNat = fts.acNature(acType);
            Object bnkNameObj = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where "
                    + "b.alphacode=br.alphacode and br.brncode=cast('" + brCode + "' as unsigned)").getSingleResult();

            String bnkName = ((Vector) bnkNameObj).elementAt(0).toString();
            String bnkAddress = ((Vector) bnkNameObj).elementAt(1).toString();

            resultLt = em.createNativeQuery("select r.acno,r.dramt,r.details,a.custname from recon r, accountmaster a where r.dt='" + enterDt + "' "
                    + " and r.enterby='" + enterBy + "' and r.trsno=" + trsno + " and r.org_brnid = r.dest_brnid "
                    + " and a.acno = r.acno union all "
                    + " select r.acno,r.dramt,r.details,a.custname from ca_recon r, accountmaster a where r.dt='" + enterDt + "' "
                    + " and r.enterby='" + enterBy + "' and r.trsno=" + trsno + " and r.org_brnid = r.dest_brnid "
                    + " and a.acno = r.acno order by 2,1").getResultList();
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    ChargesObject temp = new ChargesObject();
                    temp.setBnkName(bnkName);
                    temp.setBnkAddress(bnkAddress);
                    temp.setAcNo(ele.get(0).toString());
                    temp.setCustName(ele.get(3).toString());
                    temp.setPenalty(Double.parseDouble(ele.get(1).toString()));
                    if (ele.get(2).toString().equalsIgnoreCase("INSUFFICIENT FUND")) {
                        temp.setStatus(ele.get(2).toString());
                    } else if (ele.get(2).toString().length() > 36) {
                        temp.setStatus(ele.get(2).toString().substring(36));
                    } else {
                        temp.setStatus("POSTED");
                    }
                    temp.setMsg("TRUE");
                    tempTable.add(temp);
                }

                resultLt1 = em.createNativeQuery("select r.acno,r.amount,r.details,a.custname from pendingcharges r, accountmaster a "
                        + " where r.dt='" + enterDt + "' and r.enterby='" + enterBy + "' and r.trsno=" + trsno + " "
                        + " and a.acno = r.acno").getResultList();
                if (!resultLt1.isEmpty()) {
                    for (int i = 0; i < resultLt1.size(); i++) {
                        Vector ele = (Vector) resultLt1.get(i);
                        ChargesObject temp1 = new ChargesObject();
                        temp1.setBnkName(bnkName);
                        temp1.setBnkAddress(bnkAddress);
                        temp1.setAcNo(ele.get(0).toString());
                        temp1.setCustName(ele.get(3).toString());
                        temp1.setPenalty(Double.parseDouble(ele.get(1).toString()));
                        if (ele.get(2).toString().equalsIgnoreCase("INSUFFICIENT FUND")) {
                            temp1.setStatus(ele.get(2).toString());
                        } else if (ele.get(2).toString().length() > 36) {
                            temp1.setStatus(ele.get(2).toString().substring(36));
                        } else {
                            temp1.setStatus("POSTED");
                        }
                        temp1.setMsg("TRUE");
                        tempTable.add(temp1);
                    }
                }
            } else {
                ChargesObject temp = new ChargesObject();
                temp.setMsg("Report could not be printed.");
                tempTable.add(temp);
                return tempTable;
            }

        } catch (Exception e) {

            throw new ApplicationException(e);
        }
        return tempTable;
    }

    public List getInoperativeAccounts(String orgBrnCode, String acType, int days, String date) throws ApplicationException {
        try {
            String dt = date;
            List checkList;
            List<Table> listTable = new ArrayList<Table>();
            //  List secList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acType + "'").getResultList();
            // Vector accNatures = (Vector) secList.get(0);
            // String accNature = accNatures.get(0).toString();
//            if (accNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//
////                checkList = em.createNativeQuery("select distinct r.acno,date_format(max(Valuedt),'%d/%m/%Y'),custname,TIMESTAMPDIFF(DAY,date_format(max(Valuedt),'%Y%m%d'),'" + dt + "') from ca_recon r,accountmaster a"
////                        + " where SUBSTRING(a.ACNO,3,2)='" + acType + "' AND r.acno = a.acno and trantype <> 8  AND trandesc<>33 and a.accstatus <> 9 and optstatus <> 2 and Valuedt <= '" + dt + "' and a.acno "
////                        + " in( select acno from ca_recon where trantype <> 8 group by acno having (TIMESTAMPDIFF(DAY,date_format(max(Valuedt),'%Y%m%d'),'" + dt + "') > " + days + "))"
////                        + " AND a.CurBrCode ='" + orgBrnCode + "' group by r.acno,a.custname order by r.acno").getResultList();
//                checkList = em.createNativeQuery("select distinct r.acno,date_format(max(Valuedt),'%d/%m/%Y'),custname,TIMESTAMPDIFF(DAY,date_format(max(Valuedt),'%Y%m%d'),'" + dt + "') from ca_recon r,"
//                        + " accountmaster a where SUBSTRING(a.ACNO,3,2)='" + acType + "' AND r.acno = a.acno and trantype <> 8 AND trandesc<>33 and a.accstatus not in (4,9,15) and optstatus <> 2 "
//                        + " AND a.CurBrCode ='" + orgBrnCode + "' and Valuedt <= '" + dt + "' group by r.acno,a.custname having (TIMESTAMPDIFF(DAY,date_format(max(Valuedt),'%Y%m%d'),'" + dt + "') > " + days + ") "
//                        + " order by r.acno").getResultList();
//            } else {
//
////                checkList = em.createNativeQuery("select distinct r.acno,date_format(max(Valuedt),'%d/%m/%Y'),custname,TIMESTAMPDIFF(DAY,date_format(max(Valuedt),'%Y%m%d'),'" + dt + "') from recon r,accountmaster a"
////                        + " where SUBSTRING(a.ACNO,3,2)='" + acType + "' AND r.acno = a.acno and trantype <> 8 AND trandesc<>33 and a.accstatus <> 9 and optstatus <> 2 and Valuedt <= '" + dt + "' and a.acno "
////                        + " in( select acno from recon where trantype <> 8 group by acno having (TIMESTAMPDIFF(DAY,date_format(max(Valuedt),'%Y%m%d'),'" + dt + "') > " + days + "))"
////                        + " AND a.CurBrCode ='" + orgBrnCode + "' group by r.acno,a.custname order by r.acno").getResultList();
//                checkList = em.createNativeQuery("select distinct r.acno,date_format(max(Valuedt),'%d/%m/%Y'),custname,TIMESTAMPDIFF(DAY,date_format(max(Valuedt),'%Y%m%d'),'" + dt + "') from recon r,"
//                        + " accountmaster a where SUBSTRING(a.ACNO,3,2)='" + acType + "' AND r.acno = a.acno and trantype <> 8 AND trandesc<>33 and a.accstatus not in (4,9,15) and optstatus <> 2 "
//                        + " AND a.CurBrCode ='" + orgBrnCode + "' and Valuedt <= '" + dt + "' group by r.acno,a.custname having (TIMESTAMPDIFF(DAY,date_format(max(Valuedt),'%Y%m%d'),'" + dt + "') > " + days + ") "
//                        + " order by r.acno").getResultList();
//            }

            checkList = em.createNativeQuery("select acno,date_format((Last_Txn_Date),'%d/%m/%Y'), custname,TIMESTAMPDIFF(DAY,date_format(Last_Txn_Date,'%Y%m%d'),'" + dt + "'),"
                    + "Last_Txn_Date from accountmaster  where substring(acno,3,2)='" + acType + "' and accstatus not in (4,9,15) and optstatus <> 2 AND "
                    + "CurBrCode ='" + orgBrnCode + "' group by acno,custname having (TIMESTAMPDIFF(DAY,date_format(Last_Txn_Date,'%Y%m%d'),'" + dt + "') > "
                    + days + ") order by acno").getResultList();

            for (int i = 0; i < checkList.size(); i++) {
                List listAccNo = new ArrayList();
                Vector vecCheckList = (Vector) checkList.get(i);
                String accountNo = vecCheckList.get(0).toString();
                List listAcctNature = em.createNativeQuery("select acctNature from accounttypemaster where AcctCode=(select accttype from accountmaster where acno='" + accountNo + "')").getResultList();
                Vector vecAcctNature = (Vector) listAcctNature.get(0);
                String acctNature = vecAcctNature.get(0).toString();
                if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    listAccNo = em.createNativeQuery("SELECT ifnull(ROUND(SUM(CRAMT-DRAMT),2),0) FROM ca_recon WHERE ACNO ='" + accountNo + "' AND ValueDT <='" + dt + "'").getResultList();
                } else {
                    listAccNo = em.createNativeQuery("SELECT ifnull(ROUND(SUM(CRAMT-DRAMT),2),0) FROM recon WHERE ACNO ='" + accountNo + "' AND ValueDT <='" + dt + "'").getResultList();
                }

                Vector vecAccNo = (Vector) listAccNo.get(0);
                Float amount = Float.parseFloat(vecAccNo.get(0).toString());
                Table objTable = new Table();
                objTable.setAcNo(vecCheckList.get(0).toString());
                objTable.setBalance(amount);
                objTable.setCustomerName(vecCheckList.get(2).toString());
                objTable.setDayDiff(Integer.parseInt(vecCheckList.get(3).toString()));
                objTable.setLastTrctionDt(vecCheckList.get(1).toString());
                objTable.setsNo(i + 1);
                listTable.add(objTable);
            }

            return listTable;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
