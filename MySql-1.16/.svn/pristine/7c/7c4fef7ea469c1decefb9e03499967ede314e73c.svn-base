/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.bill;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author admin
 */
@Stateless(mappedName = "OutwardFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class OutwardFacade implements OutwardFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote fts;

    public List acctTypeCombo(String brCode) throws ApplicationException {
        List actype = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select AcctCode from accounttypemaster  where acctnature='" + CbsConstant.CURRENT_AC + "'");
            actype = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return actype;
    }

    public List limitFlagChk(String brCode) throws ApplicationException {
        List limitFlag = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select code from parameterinfo_report where reportname='LCBGLimit'");
            limitFlag = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return limitFlag;
    }

    public List alphaCodeCombo(String brCode) throws ApplicationException {
        List alphaCode = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct(alphacode) from branchmaster");
            alphaCode = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return alphaCode;
    }

    public List gridLoad(String brCode) throws ApplicationException {
        List gridDetail = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select bill.billtype, bill.acno, bill.instno, bill.InstAmount, bill.comm, bill.Postage,date_format(bill.Instdt,'%d/%m/%Y'),bill.coll_bankname, bill.coll_bankaddr, bill.enterby from bill_obcbooking bill, accountmaster am where billtype='BP' and Auth='N' and bill.acno = am.acno and am.curBrCode ='" + brCode + "'");
            gridDetail = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return gridDetail;
    }

    public List alphaCodeLostFocus(String brCode, String alphaCode) throws ApplicationException {
        List resList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select BranchName,Address From branchmaster Where Alphacode='" + alphaCode + "'");
            resList = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resList;
    }

    public List setbillDetainDate(int period, String instDate) throws ApplicationException {
        List dt = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select date_format(DATE_ADD('" + instDate + "', INTERVAL " + period + " DAY),'%d/%m/%Y')");
            dt = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return dt;
    }

    public List setvaluesInFielde(String brCode, String billType, String acno, String instNo, double instAmt) throws ApplicationException {
        List resList = new ArrayList();
        try {
            resList = em.createNativeQuery("select acno,billtype,instno,InstAmount,comm,Postage,date_format(Instdt,'%Y%m%d'),coll_bankname,coll_bankaddr,coll_alphacode,InFavourOf,billdetainperiod,invno,date_format(billdetainDT,'%d/%m/%Y') from bill_obcbooking where acno= '" + acno + "' and Billtype='" + billType + "'and Instno='" + instNo + "' and Instamount=" + instAmt + "").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resList;
    }

    public List setCustName(String brCode, String billType, String acno, String instNo, double instAmt) throws ApplicationException {
        List resList = new ArrayList();
        try {
            List chkList = em.createNativeQuery("select * from bill_obcbooking where acno= '" + acno + "' and Billtype='" + billType + "'and Instno='" + instNo + "' and Instamount=" + instAmt + "").getResultList();
            if (!chkList.isEmpty()) {
                String acNat = fts.getAccountNature(acno);
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    resList = em.createNativeQuery("select custname,jtname1,jtname2 from td_accountmaster where acno= '" + acno + "'").getResultList();
                } else if (acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    resList = em.createNativeQuery("select acName from gltable where acno= '" + acno + "'").getResultList();
                } else {
                    resList = em.createNativeQuery("select custname,jtname1,jtname2 from accountmaster where acno= '" + acno + "'").getResultList();
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resList;

    }

    public String deleteRecord(String brCode, String billType, String acno, String instNo, double instAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            Query deleteQuery = em.createNativeQuery("Delete from bill_obcbooking where acno= '" + acno + "' and Billtype='" + billType + "' and Instno='" + instNo + "' and Instamount=" + instAmt + "");
            var = deleteQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Data could not be Deleted";
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new EJBException("Rollback failed: " + syex.getMessage());
            }
            throw new EJBException("Transaction failed : " + ex.getMessage());
        }
    }

    public String acNoChk(String brCode, String acNo) throws ApplicationException {
        List resList2 = new ArrayList();
        List resList1 = new ArrayList();
        String PostFlagMast = null;
        try {
            resList1 = em.createNativeQuery("SELECT  code FROM parameterinfo_report WHERE reportname='MSGMODULE_ACTIVE'").getResultList();
            Vector recLst1 = (Vector) resList1.get(0);
            int code = Integer.parseInt(recLst1.get(0).toString());
            if (code == 1) {
                resList2 = em.createNativeQuery("Select ifnull(MsgFlag,0) Msgflag from gltable where acno = '" + acNo + "'").getResultList();
            }

            if (resList2.isEmpty()) {
                PostFlagMast = "true";
            } else {
                Vector recLst = (Vector) resList2.get(0);
                int msgFlag = Integer.parseInt(recLst.get(0).toString());
                if (msgFlag == 0) {
                    PostFlagMast = "true";
                } else if (msgFlag > 0) {
                    PostFlagMast = "false";
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return PostFlagMast;
    }

    public List acNoLostFocus(String brCode, String acNo) throws ApplicationException {
        List resList1 = new ArrayList();
        int TmpAcstatus = 1;
        try {
            String acNat = fts.getAccountNature(acNo);
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                resList1 = em.createNativeQuery("select custname,JtName1,JtName2,AccStatus from td_accountmaster where acno='" + acNo + "'").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                resList1 = em.createNativeQuery("select AcName,postflag From gltable where acno='" + acNo + "'").getResultList();
                String PostFlagMast = acNoChk(brCode, acNo);
                if (PostFlagMast.equalsIgnoreCase("true")) {
                    resList1.clear();
                }
            } else {
                resList1 = em.createNativeQuery("select custname,JtName1,JtName2,AccStatus from accountmaster where acno='" + acNo + "'").getResultList();
            }

            if (!resList1.isEmpty()) {

                if (!acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    for (int i = 0; i < resList1.size(); i++) {
                        Vector recLst1 = (Vector) resList1.get(i);
                        int acstat = Integer.parseInt(recLst1.get(3).toString());
                        TmpAcstatus = IntAccStatus(acstat);

                    }
                }
                if (TmpAcstatus == 1) {
                    return resList1;
                } else {
                    resList1.clear();
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resList1;
    }

    public int IntAccStatus(int accStatus) {
        int TmpAcstatus = 1;
        String intAcSt = null;
        switch (accStatus) {
            case 1:
                TmpAcstatus = 1;
                break;
            case 2:
                intAcSt = "Account Has been marked Inoperative";
                break;
            case 3:
                intAcSt = "Account Has been marked Suit Filed";
                break;
            case 4:
                TmpAcstatus = 9;
                intAcSt = "Account Has been marked Frozen";
                break;
            case 5:
                TmpAcstatus = 9;
                intAcSt = "Account Has been marked Recalled";
                break;
            case 6:
                intAcSt = "Account Has been marked Decreed";
                break;
            case 7:
                TmpAcstatus = 7;
                intAcSt = "Withdrawal is not Allowed in this Account";
                break;
            case 8:
                TmpAcstatus = 9;
                intAcSt = "Account Has been marked Operation Stopped";
                break;
            case 9:
                TmpAcstatus = 9;
                intAcSt = "Account Has been Closed";
                break;
            case 10:
                TmpAcstatus = 10;
                break;
            case 11:
                intAcSt = "This Account is SUB STANDARD Account";
                break;
            case 12:
                intAcSt = "This Account is DOUBT FUL Account";
                break;
            case 13:
                intAcSt = "This Account is LOSS Account";
                break;
            case 14:
                intAcSt = "This Account is PROTESTED BILL Account";
                break;
        }
        return TmpAcstatus;
    }

    public String saveBtnOutWardBill(String brCode, String acNo, String billType, String alpha, String instNo, String instAmt, String instDate, String comm, String postage, String inFavOf, String user, String invNo, String colBankName, String colBankAdd, String billDetainPeriod, String billDetainDt, boolean limitFlag, String collectBankType) throws ApplicationException {
        Float tmpAmt;
        String auth = "";
        String dt = "";
        Float billLimit = 0f;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            if (limitFlag == true) {
                List chk1 = em.createNativeQuery("select ifnull(Bill_limit,0) Bill_limit,Bill_Limit_ExpDt, Auth From bill_bpdgulc_master" + " Where acno='" + acNo + "'").getResultList();
                if (chk1.isEmpty()) {
                    ut.rollback();
                    return "BP Limit for this account is not set.";
                } else {
                    for (int i = 0; i < chk1.size(); i++) {
                        Vector recLst = (Vector) chk1.get(i);
                        billLimit = Float.parseFloat(recLst.get(0).toString());
                        auth = recLst.get(2).toString();
                        dt = recLst.get(1).toString();
                    }
                }
                if (!auth.equalsIgnoreCase("Y")) {
                    ut.rollback();
                    return "BP Limit for this account is not Authorized.";
                } else {
                    List dateVal = em.createNativeQuery("select TIMESTAMPDIFF(DAY,CURDATE(),'" + dt + "')").getResultList();
                    Vector dt1 = (Vector) dateVal.get(0);
                    int n = Integer.parseInt(dt1.get(0).toString());
                    if (n < 0) {
                        ut.rollback();
                        return "BP Limit for this account has Expired.";
                    }
                }
                if (billLimit == 0) {
                    ut.rollback();
                    return "BP Limit for this account needs to be revised.";
                }

                List chk2 = em.createNativeQuery("select ifnull(sum(InstAmount),0) TAmt from bill_obcbooking where BillType='BP' and acno='" + acNo + "'").getResultList();
                if (chk2.isEmpty()) {
                    tmpAmt = 0f;
                } else {
                    Vector recLst = (Vector) chk2.get(0);
                    tmpAmt = Float.parseFloat(recLst.get(0).toString());
                }
                float n = tmpAmt + Float.parseFloat(instAmt);
                if (n > billLimit) {
                    ut.rollback();
                    return "Sorry!Bill Amount Exceeds the BP Limit in this account.There may be other Bills in the Account Pending for realization.";
                }
            }

            if (brCode == null || acNo == null || billType == null || alpha == null || instNo == null || instAmt == null || instDate == null || comm == null || postage == null || inFavOf == null || user == null || invNo == null || colBankName == null || colBankAdd == null || billDetainPeriod == null || billDetainDt == null) {
                ut.rollback();
                return "Data Cannot Be Null,Not Saved!!!";
            }
            if (acNo.equalsIgnoreCase("") || acNo.length() == 0 || acNo == null) {
                ut.rollback();
                return "Please Enter Account No.";
            }


            if (instNo == null) {
                ut.rollback();
                return "Please Enter LR/RR/PP/AW No.";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (instAmt.equalsIgnoreCase("") || instAmt.length() == 0) {
                ut.rollback();
                return "Please Enter Bill Amount.";
            }
            Matcher instAmtCheck = p.matcher(instAmt);
            if (!instAmtCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Bill Amount.";
            }
            if (instAmt.contains(".")) {
                if (instAmt.indexOf(".") != instAmt.lastIndexOf(".")) {
                    ut.rollback();
                    return "Please Enter Valid Bill Amount.";
                } else if (instAmt.substring(instAmt.indexOf(".") + 1).length() > 2) {
                    ut.rollback();
                    return "Please Fill The Bill Amount Upto Two Decimal Places.";
                }
            }
            if (Float.parseFloat(instAmt) < 0) {
                ut.rollback();
                return "Bill Amount Cannot Be Less Than Zero.";
            }
            if (alpha.equalsIgnoreCase("") || alpha.length() == 0 || alpha == null) {
                ut.rollback();
                return "Please Select Collecting Alpha Code.";
            }
            if (alpha.equalsIgnoreCase("--Select--")) {
                ut.rollback();
                return "Please Select Collecting Alpha Code.";
            }
            if (billType.equalsIgnoreCase("--Select--")) {
                ut.rollback();
                return "Please Select Bill Type.";
            }
            if (colBankName.equalsIgnoreCase("") || colBankName.length() == 0 || colBankName == null) {
                ut.rollback();
                return "Please Enter Collecting Bank Name.";
            }
            if (colBankAdd.equalsIgnoreCase("") || colBankAdd.length() == 0 || colBankAdd == null) {

                return "Please Enter Collecting Bank Address.";
            }
            if (comm.equalsIgnoreCase("") || comm.length() == 0 || comm == null) {
                ut.rollback();
                return "Please Enter Commission.";
            }
            Matcher commCheck = p.matcher(comm);
            if (!commCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid (Numeric) Commission.";
            }
            if (comm.contains(".")) {
                if (comm.indexOf(".") != comm.lastIndexOf(".")) {
                    ut.rollback();
                    return "Please Enter Valid Commission.";
                } else if (comm.substring(comm.indexOf(".") + 1).length() > 2) {
                    ut.rollback();
                    return "Please Fill The Commission Upto Two Decimal Places.";
                }
            }
            if (Float.parseFloat(comm) < 0) {
                ut.rollback();
                return "Commission Cannot Be Less Than Zero.";
            }
            if (postage.equalsIgnoreCase("") || postage.length() == 0 || postage == null) {
                ut.rollback();
                return "Please Enter Postage.";
            }
            Matcher postageCheck = p.matcher(postage);
            if (!postageCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid(Numeric Value) Postage.";
            }
            if (postage.contains(".")) {
                if (postage.indexOf(".") != postage.lastIndexOf(".")) {
                    ut.rollback();
                    return "Please Enter Valid Postage.";
                } else if (postage.substring(postage.indexOf(".") + 1).length() > 2) {
                    ut.rollback();
                    return "Please Fill The Postage Upto Two Decimal Places.";
                }
            }
            if (Float.parseFloat(postage) < 0) {
                ut.rollback();
                return "Postage Cannot Be Less Than Zero.";
            }
            if (inFavOf.equalsIgnoreCase("") || inFavOf.length() == 0 || inFavOf == null) {
                ut.rollback();
                return "Please Enter In Favour Of.";
            }
            if (collectBankType.equalsIgnoreCase("--Select--")) {
                ut.rollback();
                return "Please Select Collect Bank Type.";
            }

            float a = Float.parseFloat(comm) + Float.parseFloat(postage);
            if (a >= Float.parseFloat(instAmt)) {
                ut.rollback();
                return "Please Check Commission and Postage Amount.";
            }
            Matcher invNoCheck = p.matcher(invNo);
            if (!invNoCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Carrier/INV No.";
            }
            Matcher billDetainPeriodCheck = p.matcher(billDetainPeriod);
            if (!billDetainPeriodCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Bill Detain Period.";
            }
            if (billDetainDt.equalsIgnoreCase("") || billDetainDt.length() == 0) {
                ut.rollback();
                return "Bill Detain Date Cannot Be Null.";
            }

            List chkList = em.createNativeQuery("select *  from bill_obcBooking where acno= '" + acNo + "' and Billtype='" + billType + "' and instamount=" + Float.parseFloat(instAmt) + " and Instno='" + instNo + "'").getResultList();
            if (chkList.isEmpty()) {
                List dateVal = em.createNativeQuery("select TIMESTAMPDIFF(DAY,CURDATE(),'" + instDate + "')").getResultList();
                Vector dt2 = (Vector) dateVal.get(0);
                int m = Integer.parseInt(dt2.get(0).toString());
                if (m > 0) {
                    ut.rollback();
                    return "Please Select Valid Date of Instrument.";
                }
                List temp = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
                Vector recLst = (Vector) temp.get(0);
                String tempbd = recLst.get(0).toString();
                if (collectBankType.equalsIgnoreCase("Direct Party")) {
                    Query insertQuery1 = em.createNativeQuery("insert into bill_obcBooking (Acno,Billtype,Instno,InstAmount,Instdt,Dt,Comm,Postage,InFavourOf,Status,Auth,EnterBy,invno,billDetainPeriod,billdetaindt, Coll_AlphaCode,Coll_BankName,Coll_BankAddr,alphacode,bankname,bankaddr) values" + "('" + acNo + "','" + billType + "','" + instNo + "','" + Float.parseFloat(instAmt) + "', '" + instDate + "','" + tempbd + "', " + Float.parseFloat(comm) + ", " + Float.parseFloat(postage) + ",'" + inFavOf + " : Direct Party','E','N'," + "upper('" + user + "')" + ",'" + invNo + "'," + Integer.parseInt(billDetainPeriod) + ",'" + billDetainDt + "','" + alpha + "', '" + colBankName + "','" + colBankAdd + "',0,'','' )");
                    var = insertQuery1.executeUpdate();
                } else {
                    Query insertQuery1 = em.createNativeQuery("insert into bill_obcBooking (Acno,Billtype,Instno,InstAmount,Instdt,Dt,Comm,Postage,InFavourOf,Status,Auth,EnterBy,invno,billDetainPeriod,billdetaindt, Coll_AlphaCode,Coll_BankName,Coll_BankAddr,alphacode,bankname,bankaddr) values" + "('" + acNo + "','" + billType + "','" + instNo + "','" + Float.parseFloat(instAmt) + "', '" + instDate + "','" + tempbd + "', " + Float.parseFloat(comm) + ", " + Float.parseFloat(postage) + ",'" + inFavOf + "','E','N'," + "upper('" + user + "')" + ",'" + invNo + "'," + Integer.parseInt(billDetainPeriod) + ",'" + billDetainDt + "','" + alpha + "', '" + colBankName + "','" + colBankAdd + "',0,'','' )");
                    var = insertQuery1.executeUpdate();
                }
            } else {
                ut.rollback();
                return "This Record Already Exist.";
            }
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Record Not Saved.";
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new EJBException("Rollback failed: " + syex.getMessage());
            }
            throw new EJBException("Transaction failed : " + ex.getMessage());
        }


    }

    public String updateBtnOutWardBill(String brCode, String acNo, String billType, String alpha, String instNo, String instAmt, String instDate, String comm, String postage, String inFavOf, String user, String invNo, String colBankName, String colBankAdd, String billDetainPeriod, String billDetainDt, String collectBankType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            if (brCode == null || acNo == null || billType == null || alpha == null || instNo == null || instAmt == null || instDate == null || comm == null || postage == null || inFavOf == null || user == null || invNo == null || colBankName == null || colBankAdd == null || billDetainPeriod == null || billDetainDt == null) {
                ut.rollback();
                return "Data Cannot Be Null,Not Saved!!!";
            }
            if (acNo.equalsIgnoreCase("") || acNo.length() == 0 || acNo == null) {
                ut.rollback();
                return "Please Enter Account No.";
            }
            if (instNo == null) {
                ut.rollback();
                return "Please Enter LR/RR/PP/AW No.";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (instAmt.equalsIgnoreCase("") || instAmt.length() == 0) {
                ut.rollback();
                return "Please Enter Bill Amount.";
            }
            Matcher instAmtCheck = p.matcher(instAmt);
            if (!instAmtCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Bill Amount.";
            }
            if (instAmt.contains(".")) {
                if (instAmt.indexOf(".") != instAmt.lastIndexOf(".")) {
                    ut.rollback();
                    return "Please Enter Valid Bill Amount.";
                } else if (instAmt.substring(instAmt.indexOf(".") + 1).length() > 2) {
                    ut.rollback();
                    return "Please Fill The Bill Amount Upto Two Decimal Places.";
                }
            }
            if (Float.parseFloat(instAmt) < 0) {
                ut.rollback();
                return "Bill Amount Cannot Be Less Than Zero.";
            }
            if (alpha.equalsIgnoreCase("") || alpha.length() == 0 || alpha == null) {
                ut.rollback();
                return "Please Select Collecting Alpha Code.";
            }

            if (alpha.equalsIgnoreCase("--Select--")) {
                ut.rollback();
                return "Please Select Collecting Alpha Code.";
            }

            if (billType.equalsIgnoreCase("--Select--")) {
                ut.rollback();
                return "Please Select Bill Type.";
            }
            if (colBankName.equalsIgnoreCase("") || colBankName.length() == 0 || colBankName == null) {
                ut.rollback();
                return "Please Enter Collecting Bank Name.";
            }
            if (colBankAdd.equalsIgnoreCase("") || colBankAdd.length() == 0 || colBankAdd == null) {

                return "Please Enter Collecting Bank Address.";
            }
            Matcher commCheck = p.matcher(comm);
            if (!commCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Commission.";
            }
            if (comm.contains(".")) {
                if (comm.indexOf(".") != comm.lastIndexOf(".")) {
                    ut.rollback();
                    return "Please Enter Valid Commission.";
                } else if (comm.substring(comm.indexOf(".") + 1).length() > 2) {
                    ut.rollback();
                    return "Please Fill The Commission Upto Two Decimal Places.";
                }
            }
            if (Float.parseFloat(comm) < 0) {
                ut.rollback();
                return "Commission Cannot Be Less Than Zero.";
            }
            Matcher postageCheck = p.matcher(postage);
            if (!postageCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Postage.";
            }
            if (postage.contains(".")) {
                if (postage.indexOf(".") != postage.lastIndexOf(".")) {
                    ut.rollback();
                    return "Please Enter Valid Postage.";
                } else if (postage.substring(postage.indexOf(".") + 1).length() > 2) {
                    ut.rollback();
                    return "Please Fill The Postage Upto Two Decimal Places.";
                }
            }
            if (Float.parseFloat(postage) < 0) {
                ut.rollback();
                return "Postage Cannot Be Less Than Zero.";
            }

            if (inFavOf.equalsIgnoreCase("") || inFavOf.length() == 0 || inFavOf == null) {
                ut.rollback();
                return "Please Enter In Favour Of.";
            }

            if (collectBankType.equalsIgnoreCase("--Select--")) {
                ut.rollback();
                return "Please Select Collect Bank Type.";
            }

            float a = Float.parseFloat(comm) + Float.parseFloat(postage);
            if (a >= Float.parseFloat(instAmt)) {
                ut.rollback();
                return "Please Check Commission and Postage Amount.";
            }
            Matcher invNoCheck = p.matcher(invNo);
            if (!invNoCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Carrier/INV No.";
            }
            Matcher billDetainPeriodCheck = p.matcher(billDetainPeriod);
            if (!billDetainPeriodCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Bill Detain Period.";
            }
            if (billDetainDt.equalsIgnoreCase("") || billDetainDt.length() == 0) {
                ut.rollback();
                return "Bill Detain Date Cannot Be Null.";
            }


            List dateVal = em.createNativeQuery("select TIMESTAMPDIFF(DAY,CURDATE(),'" + instDate + "')").getResultList();
            Vector dt2 = (Vector) dateVal.get(0);
            int m = Integer.parseInt(dt2.get(0).toString());
            if (m > 0) {
                ut.rollback();
                return "Please Select Valid Date of Instrument.";
            }
            List temp = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
            Vector recLst = (Vector) temp.get(0);
            String tempbd = recLst.get(0).toString();
            if (collectBankType.equalsIgnoreCase("Direct Party")) {
                Query updateQuery = em.createNativeQuery("Update bill_obcBooking set Billtype='" + billType + "', Comm=" + Float.parseFloat(comm) + ",Coll_Bankname='" + colBankName + "',billdetainperiod='" + Integer.parseInt(billDetainPeriod) + "',billdetaindt='" + billDetainDt + "',"
                        + "Coll_Bankaddr='" + colBankAdd + "',instAmount=" + Float.parseFloat(instAmt) + ",Instno='" + instNo + "',InstDt='" + instDate + " ',Dt='" + tempbd + " '," + "LastupdateBy=" + "upper('" + user + "')" + ",Coll_AlphaCode='" + alpha + "',Postage=" + Float.parseFloat(postage) + ",infavourof ='" + inFavOf + " : Direct Party' where acno= '" + acNo + "'and Instno='" + instNo + "'");
                var = updateQuery.executeUpdate();
            } else {
                Query updateQuery = em.createNativeQuery("Update bill_obcBooking set Billtype='" + billType + "', Comm=" + Float.parseFloat(comm) + ",Coll_Bankname='" + colBankName + "',billdetainperiod='" + Integer.parseInt(billDetainPeriod) + "',billdetaindt='" + billDetainDt + "',"
                        + "Coll_Bankaddr='" + colBankAdd + "',instAmount=" + Float.parseFloat(instAmt) + ",Instno='" + instNo + "',InstDt='" + instDate + " ',Dt='" + tempbd + " '," + "LastupdateBy=" + "upper('" + user + "')" + ",Coll_AlphaCode='" + alpha + "',Postage=" + Float.parseFloat(postage) + ",infavourof ='" + inFavOf + "' where acno= '" + acNo + "'and Instno='" + instNo + "'");
                var = updateQuery.executeUpdate();
            }
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Record Not Updated.";
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new EJBException("Rollback failed: " + syex.getMessage());
            }
            throw new EJBException("Transaction failed : " + ex.getMessage());
        }
    }

    /*********
     * OutwardChequesBean start code
     ************/
    public List acctTypeComboOutwardCheque(String brCode) throws ApplicationException {
        List actype = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select AcctCode from AccountTypeMaster");
            actype = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return actype;
    }

    public List gridLoadOutwardCheque(String brCode) throws ApplicationException {
        List gridDetail = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select bill.acno,bill.Instno,bill.instamount,date_format(bill.InstDt,'%d/%m/%Y'),bill.InFavourOf,bill.coll_bankname,bill.coll_bankaddr,bill.BillType,bill.enterby from bill_obcbooking bill, accountmaster am where  bill.billtype='BC' and bill.Auth='N' and bill.acno = am.acno and am.curBrCode ='" + brCode + "'");
            gridDetail = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return gridDetail;
    }

    public List setvaluesInFieldeOutCheque(String brCode, String billType, String acno, String instNo, double instAmt) {
        List resList = new ArrayList();
        try {
            resList = em.createNativeQuery("select acno,billtype,alphacode,infavourof,bankname,bankaddr,instno,instamount,comm,postage,date_format(instdt,'%Y%m%d'),coll_Alphacode,coll_bankname,coll_bankaddr  from bill_obcbooking where acno= '" + acno + "' and Billtype='" + billType + "'and Instno='" + instNo + "' and Instamount=" + instAmt + "").getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resList;
    }
    Pattern pm = Pattern.compile("[a-zA-z0-9,-/]+([ '-][a-zA-Z0-9,-/]+)*");

    public String saveBtnOutWardCheque(String brCode, String acNo, String billType, String alpha1, String instBankName, String instBankAdd, String instNo, String instAmt, String instDate, String comm, String postage, String inFavOf, String user, String alpha2, String colBankName, String colBankAdd) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            if (brCode == null || acNo == null || billType == null || alpha1 == null || instBankName == null || instBankAdd == null || instNo == null || instAmt == null || instDate == null || comm == null || postage == null || inFavOf == null || user == null || alpha2 == null || colBankName == null || colBankAdd == null) {
                ut.rollback();
                return "Data Cannot Be Null,Not Saved!!!";
            }
            if (acNo.equalsIgnoreCase("") || acNo.length() == 0 || acNo == null) {
                ut.rollback();
                return "Please Enter Account No.";
            }
            if (billType.equalsIgnoreCase("--Select--")) {
                ut.rollback();
                return "Please Select Bill Type.";
            }
            if (alpha1.equalsIgnoreCase("") || alpha1.length() == 0 || alpha1 == null) {
                ut.rollback();
                return "Please Select Inst. Alpha Code.";
            }

            if (alpha1.equalsIgnoreCase("--Select--")) {
                ut.rollback();
                return "Please Select Inst. Alpha Code.";
            }

            if (instBankName.equalsIgnoreCase("") || instBankName.length() == 0 || instBankName == null) {
                ut.rollback();
                return "Please Enter Inst. Bank Name.";
            }
            Matcher instBankNameCheck = pm.matcher(instBankName);
            if (!instBankNameCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Inst. Bank Name.";
            }
            if (instBankAdd.equalsIgnoreCase("") || instBankAdd.length() == 0 || instBankAdd == null) {
                ut.rollback();
                return "Please Enter Inst. Bank Address.";
            }
            Matcher instBankAddCheck = pm.matcher(instBankAdd);
            if (!instBankAddCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Inst. Bank Address.";
            }
            if (instNo == null) {
                ut.rollback();
                return "Please Enter Inst. No.";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (instAmt.equalsIgnoreCase("") || instAmt.length() == 0) {
                ut.rollback();
                return "Please Enter Inst. Amount.";
            }
            Matcher instAmtCheck = p.matcher(instAmt);
            if (!instAmtCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Inst Amount.";
            }
            if (instAmt.contains(".")) {
                if (instAmt.indexOf(".") != instAmt.lastIndexOf(".")) {
                    ut.rollback();
                    return "Please Enter Valid Inst Amount.";
                } else if (instAmt.substring(instAmt.indexOf(".") + 1).length() > 2) {
                    ut.rollback();
                    return "Please Fill The Inst Amount Upto Two Decimal Places.";
                }
            }
            if (Float.parseFloat(instAmt) < 0) {
                ut.rollback();
                return "Inst. Amount Cannot Be Less Than Zero.";
            }
            Matcher commCheck = p.matcher(comm);
            if (!commCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Commission.";
            }
            if (comm.contains(".")) {
                if (comm.indexOf(".") != comm.lastIndexOf(".")) {
                    ut.rollback();
                    return "Please Enter Valid Commission.";
                } else if (comm.substring(comm.indexOf(".") + 1).length() > 2) {
                    ut.rollback();
                    return "Please Fill The Commission Upto Two Decimal Places.";
                }
            }
            if (Float.parseFloat(comm) < 0) {
                ut.rollback();
                return "Commission Cannot Be Less Than Zero.";
            }
            Matcher postageCheck = p.matcher(postage);
            if (!postageCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Postage.";
            }
            if (postage.contains(".")) {
                if (postage.indexOf(".") != postage.lastIndexOf(".")) {
                    ut.rollback();
                    return "Please Enter Valid Postage.";
                } else if (postage.substring(postage.indexOf(".") + 1).length() > 2) {
                    ut.rollback();
                    return "Please Fill The Postage Upto Two Decimal Places.";
                }
            }
            if (Float.parseFloat(postage) < 0) {
                ut.rollback();
                return "Postage Cannot Be Less Than Zero.";
            }
            if (inFavOf.equalsIgnoreCase("") || inFavOf.length() == 0 || inFavOf == null) {
                ut.rollback();
                return "Please Enter In Favour Of.";
            }
            Matcher inFavOfCheck = pm.matcher(inFavOf);
            if (!inFavOfCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid In Favour Of.";
            }
            if (alpha2.equalsIgnoreCase("") || alpha2.length() == 0 || alpha2 == null) {
                ut.rollback();
                return "Please Select Collecting Alpha Code.";
            }
            if (alpha2.equalsIgnoreCase("--Select--")) {
                ut.rollback();
                return "Please Select Collecting Alpha Code.";
            }

            if (colBankName.equalsIgnoreCase("") || colBankName.length() == 0 || colBankName == null) {
                ut.rollback();
                return "Please Enter Collecting Bank Name.";
            }
            Matcher colBankNameCheck = pm.matcher(colBankName);
            if (!colBankNameCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Collecting Bank Name.";
            }
            if (colBankAdd.equalsIgnoreCase("") || colBankAdd.length() == 0 || colBankAdd == null) {
                ut.rollback();
                return "Please Enter Collecting Bank Address.";
            }
            Matcher colBankAddCheck = pm.matcher(colBankAdd);
            if (!colBankAddCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Collecting Bank Address.";
            }
            float a = Float.parseFloat(comm) + Float.parseFloat(postage);
            if (a >= Float.parseFloat(instAmt)) {
                ut.rollback();
                return "Please Check Commission and Postage Amount.";

            }
            List chkList = em.createNativeQuery("select *  from bill_obcbooking where acno= '" + acNo + "' and Billtype='" + billType + "' and instamount=" + Float.parseFloat(instAmt) + " and Instno=" + instNo + "").getResultList();
            if (chkList.isEmpty()) {
                List dateVal = em.createNativeQuery("select TIMESTAMPDIFF(DAY,curdate(),'" + instDate + "')").getResultList();
                Vector dt = (Vector) dateVal.get(0);
                int n = Integer.parseInt(dt.get(0).toString());
                if (n > 0) {
                    ut.rollback();
                    return "Please Select Valid Date of Instrument.";
                }
                List temp = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
                Vector recLst = (Vector) temp.get(0);
                String tempbd = recLst.get(0).toString();
                Query insertQuery1 = em.createNativeQuery("insert into bill_obcbooking (Acno,Billtype,Alphacode,Bankname,Bankaddr,Instno,InstAmount,Instdt,Dt,Comm,Postage,InFavourOf,Status,Auth,EnterBy, Coll_AlphaCode,Coll_BankName,Coll_BankAddr) values" + "('" + acNo + "','" + billType + "','" + alpha1 + "','" + instBankName + "', '" + instBankAdd + "','" + instNo + "', " + Float.parseFloat(instAmt) + ", '" + instDate + "','" + tempbd + "'," + " " + Float.parseFloat(comm) + "," + Float.parseFloat(postage) + ",'" + inFavOf + "','E','N'," + "upper('" + user + "')" + ",'" + alpha2 + "', '" + colBankName + "','" + colBankAdd + "' )");
                var = insertQuery1.executeUpdate();
            } else {
                ut.rollback();
                return "This Record Already Exist.";
            }
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Record Not Saved.";
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new EJBException("Rollback failed: " + syex.getMessage());
            }
            throw new EJBException("Transaction failed : " + ex.getMessage());
        }
    }

    public String updateBtnOutWardCheque(String brCode, String acNo, String billType, String alpha1, String instBankName, String instBankAdd, String instNo, String instAmt, String instDate, String comm, String postage, String inFavOf, String user, String alpha2, String colBankName, String colBankAdd) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            if (brCode == null || acNo == null || billType == null || alpha1 == null || instBankName == null || instBankAdd == null || instNo == null || instAmt == null || instDate == null || comm == null || postage == null || inFavOf == null || user == null || alpha2 == null || colBankName == null || colBankAdd == null) {
                ut.rollback();
                return "Data Cannot Be Null,Not Saved!!!";
            }
            if (acNo.equalsIgnoreCase("") || acNo.length() == 0 || acNo == null) {
                ut.rollback();
                return "Please Enter Account No.";
            }
            if (billType.equalsIgnoreCase("--Select--")) {
                ut.rollback();
                return "Please Select Bill Type.";
            }
            if (alpha1.equalsIgnoreCase("") || alpha1.length() == 0 || alpha1 == null) {
                ut.rollback();
                return "Please Select Inst. Alpha Code.";
            }

            if (alpha1.equalsIgnoreCase("--Select--")) {
                ut.rollback();
                return "Please Select Inst. Alpha Code.";
            }

            if (instBankName.equalsIgnoreCase("") || instBankName.length() == 0 || instBankName == null) {
                ut.rollback();
                return "Please Enter Inst. Bank Name.";
            }
            Matcher instBankNameCheck = pm.matcher(instBankName);
            if (!instBankNameCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Inst. Bank Name.";
            }
            if (instBankAdd.equalsIgnoreCase("") || instBankAdd.length() == 0 || instBankAdd == null) {
                ut.rollback();
                return "Please Enter Inst. Bank Address.";
            }
            Matcher instBankAddCheck = pm.matcher(instBankAdd);
            if (!instBankAddCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Inst. Bank Address.";
            }
            if (instNo == null) {
                ut.rollback();
                return "Please Enter Inst. No.";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (instAmt.equalsIgnoreCase("") || instAmt.length() == 0) {
                ut.rollback();
                return "Please Enter Inst. Amount.";
            }
            Matcher instAmtCheck = p.matcher(instAmt);
            if (!instAmtCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Inst Amount.";
            }
            Matcher commCheck = p.matcher(comm);
            if (!commCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Commission.";
            }
            Matcher postageCheck = p.matcher(postage);
            if (!postageCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Postage.";
            }
            if (inFavOf.equalsIgnoreCase("") || inFavOf.length() == 0 || inFavOf == null) {
                ut.rollback();
                return "Please Enter In Favour Of.";
            }
            Matcher inFavOfCheck = pm.matcher(inFavOf);
            if (!inFavOfCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid In Favour Of.";
            }
            if (alpha2.equalsIgnoreCase("") || alpha2.length() == 0 || alpha2 == null) {
                ut.rollback();
                return "Please Select Collecting Alpha Code.";
            }
            if (alpha2.equalsIgnoreCase("--Select--")) {
                ut.rollback();
                return "Please Select Collecting Alpha Code.";
            }

            if (colBankName.equalsIgnoreCase("") || colBankName.length() == 0 || colBankName == null) {
                ut.rollback();
                return "Please Enter Collecting Bank Name.";
            }
            Matcher colBankNameCheck = pm.matcher(colBankName);
            if (!colBankNameCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Collecting Bank Name.";
            }
            if (colBankAdd.equalsIgnoreCase("") || colBankAdd.length() == 0 || colBankAdd == null) {
                ut.rollback();
                return "Please Enter Collecting Bank Address.";
            }
            Matcher colBankAddCheck = pm.matcher(colBankAdd);
            if (!colBankAddCheck.matches()) {
                ut.rollback();
                return "Please Enter Valid Collecting Bank Address.";
            }
            float a = Float.parseFloat(comm) + Float.parseFloat(postage);
            if (a >= Float.parseFloat(instAmt)) {
                ut.rollback();
                return "Please Check Commission and Postage Amount.";

            }
            List dateVal = em.createNativeQuery("select TIMESTAMPDIFF(DAY,curdate(),'" + instDate + "')").getResultList();
            Vector dt = (Vector) dateVal.get(0);
            int n = Integer.parseInt(dt.get(0).toString());
            if (n > 0) {
                ut.rollback();
                return "Please Select Valid Date of Instrument";
            }
            List temp = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
            Vector recLst = (Vector) temp.get(0);
            String tempbd = recLst.get(0).toString();
            Query updateQuery = em.createNativeQuery("Update bill_obcbooking set Billtype='" + billType + "', Comm=" + Float.parseFloat(comm) + ",Bankname='" + instBankName + "',Bankaddr='" + instBankAdd + "',Coll_Bankname='" + colBankName + "'," + "Coll_Bankaddr='" + colBankAdd + "',instAmount=" + Float.parseFloat(instAmt) + ",Instno='" + instNo + "',InstDt='" + instDate + " ',Dt='" + tempbd + " '," + "LastupdateBy=" + "upper('" + user + "')" + ",Coll_AlphaCode='" + alpha2 + "',AlphaCode='" + alpha1 + "',Postage=" + Float.parseFloat(postage) + ",InFavourOf='" + inFavOf + "' where substring(acno,3,8)= '" + acNo.substring(2, 10) + "'and Instno='" + instNo + "'");
            var = updateQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Record Not Updated.";
            }

        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new EJBException("Rollback failed: " + syex.getMessage());
            }
            throw new EJBException("Transaction failed : " + ex.getMessage());
        }
    }
}
