/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.bill;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author admin
 */
@Stateless(mappedName = "InwardFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class InwardFacade implements InwardFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote fTSPosting43CBSBean;

    /**
     * InwardBillBookingBean Start
     *
     */
    public List acctTypeCombo(String brCode) throws ApplicationException {
        List actype = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.SAVING_AC + "','PO','FD','MS') order by acctnature desc");
            actype = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return actype;
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

    public List remTypeCombo(String brCode) throws ApplicationException {
        List remType = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select instCode from billtypemaster order by InstCode");
            remType = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return remType;
    }

    public List gridLoad(String brCode) throws ApplicationException {
        List gridDetail = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select bill.acno,bill.Instno,bill.instamount,date_format(bill.InstDt,'%d/%m/%Y'),bill.payableat,bill.infavourof,bill.BankName,bill.BankAddr,bill.Billtype,"
                    + "bill.remtype,bill.enterby from bill_ibcbooking  bill, accountmaster am where  bill.billtype='BP'"
                    + " and bill.status='E' and bill.Auth='N' and  bill.acno = am.acno and am.curBrCode ='" + brCode + "'");
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

    public List acNoLostFocus(String brCode, String acNo) throws ApplicationException {
        List resList1 = new ArrayList();
        int TmpAcstatus = 1;
        try {
            String acNat = fTSPosting43CBSBean.getAccountNature(acNo);
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

    public String deleteRecord(String brCode, String acno, String billtype, String instNo, String instAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();

        try {
            ut.begin();
            Integer var = 0;
            Query deleteQuery = em.createNativeQuery("Delete from bill_ibcbooking where acno= '" + acno + "' and Billtype='" + billtype + "' and Instno='" + instNo + "' and instamount=" + Double.parseDouble(instAmt) + "");
            System.out.println("select * from bill_ibcbooking where acno= '" + acno + "' and Billtype='" + billtype + "' and Instno='" + instNo + "' and instamount=" + Double.parseDouble(instAmt) + "");
            var = deleteQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Data could not be Deleted";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List setCustName(String brCode, String acno) throws ApplicationException {
        List resList = new ArrayList();
        try {
            String acNat = fTSPosting43CBSBean.getAccountNature(acno);
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                resList = em.createNativeQuery("select custname,jtname1,jtname2 from td_accountmaster where acno= '" + acno + "'").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                resList = em.createNativeQuery("select acName from gltable where acno= '" + acno + "'").getResultList();
            } else {
                resList = em.createNativeQuery("select custname,jtname1,jtname2 from accountmaster where acno= '" + acno + "'").getResultList();
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resList;

    }

    public List setvaluesInFielde(String brCode, String acno, String billType, String instNo) throws ApplicationException {
        List resList = new ArrayList();
        try {
            resList = em.createNativeQuery("select acno,refno,billtype,alphacode,payableat,infavourof,bankname,bankaddr,instno,instamount,comm,postage,remtype,date_format(instdt,'%Y%m%d') from bill_ibcbooking where acno= '" + acno + "' and Billtype='" + billType + "'and Instno='" + instNo + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resList;
    }

    public String saveBtnInwardBillBooking(String brCode, String acNo, String refNo, String billType, String alphaCode, String payableAt, String inFavOf, String bankName, String bankAdd, String instNo, Double instAmt, float comm, String remType, String instDate, String user, float postage) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            List dateVal = em.createNativeQuery("select TIMESTAMPDIFF(DAY,CURDATE(),'" + instDate + "')").getResultList();
            Vector dt = (Vector) dateVal.get(0);
            int n = Integer.parseInt(dt.get(0).toString());
            if (n > 0) {
                ut.rollback();
                return "Please Select Valid Date of Instrument.";
            }
            List temp = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
            Vector recLst = (Vector) temp.get(0);
            String tempbd = recLst.get(0).toString();
            Query insertQuery1 = em.createNativeQuery("insert into bill_ibcbooking (Acno,RefNo,Billtype,alphacode,payableat,infavourof,Bankname,Bankaddr,Instno,InstAmount,Comm,Remtype,InstDt,Dt,Status,Auth,EnterBy,postage) values" + "('" + acNo + "','" + refNo + "','" + billType + "','" + alphaCode + "', '" + payableAt + "','" + inFavOf + "', '" + bankName + "', '" + bankAdd + "', '" + instNo + "', " + instAmt + ", " + comm + ",'" + remType + "', '" + instDate + "', '" + tempbd + "','E','N','" + user + "'," + postage + ")");
            var = insertQuery1.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Record Not Saved.";
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
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String updateBtn(String brCode, String acNo, String refNo, String billType, String alphaCode, String payableAt, String inFavOf, String bankName, String bankAdd, String instNo, Double instAmt, float comm, String remType, String instDate, String user, float postage) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            List dateVal = em.createNativeQuery("select TIMESTAMPDIFF(DAY,CURDATE(),'" + instDate + "')").getResultList();
            Vector dt = (Vector) dateVal.get(0);
            int n = Integer.parseInt(dt.get(0).toString());
            if (n > 0) {
                ut.rollback();
                return "Please Select Valid Date of Instrument";
            }
            List temp = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
            Vector recLst = (Vector) temp.get(0);
            String tempbd = recLst.get(0).toString();
            Query updateQuery = em.createNativeQuery("update bill_ibcbooking set alphacode='" + alphaCode + "',Billtype='" + billType + "', Comm=" + comm + ",Bankname='" + bankName + "',Bankaddr='" + bankAdd + "',instAmount=" + instAmt + ",Instno='" + instNo + "',InstDt='" + instDate + "', " + " Dt='" + tempbd + "',LastupdateBy='" + user + "',payableAt='" + payableAt + "',Postage=" + postage + ",Refno='" + refNo + "',infavourof='" + inFavOf + "',remType='" + remType + "' where acno= '" + acNo + "'and Instno='" + instNo + "'");
            var = updateQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Record Not Updated.";
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
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List gridLoadInwardcheque(String brCode) throws ApplicationException {
        List gridDetail = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select bill.acno,bill.Instno,bill.instamount,date_format(bill.InstDt,'%d/%m/%Y'),bill.payableat,bill.infavourof,bill.BankName,bill.BankAddr,bill.Billtype,bill.remtype,bill.enterby from bill_ibcbooking  bill, accountmaster am where  billtype='BC' and status='E' and Auth='N' and bill.acno = am.acno and am.curBrCode ='" + brCode + "'");
            gridDetail = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return gridDetail;
    }

    public String saveBtnInwardBillBookingCheque(String brCode, String acNo, String refNo, String billType, String alphaCode, String payableAt, String inFavOf, String bankName, String bankAdd, String instNo, double instAmt, float comm, String remType, String instDate, String user, float postage) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            List dateVal = em.createNativeQuery("select TIMESTAMPDIFF(DAY,CURDATE(),'" + instDate + "')").getResultList();
            Vector dt = (Vector) dateVal.get(0);
            int n = Integer.parseInt(dt.get(0).toString());
            if (n > 0) {
                ut.rollback();
                return "Please Select Valid Date of Instrument.";
            }

            String acNat = fTSPosting43CBSBean.getAccountNature(acNo);
            List chk = em.createNativeQuery("select statusflag,acno,chqno,issuedt,authby from chbook_" + acNat + "  where acno = '" + acNo + "' and Chqno = " + instNo + "").getResultList();
            if (chk.isEmpty() || chk.isEmpty()) {
                ut.rollback();
                return "SORRY THIS INSTRUMENT NO DOES NOT EXISTS FOR THIS ACCOUNT NO !!!";
            }

            List temp = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
            Vector recLst = (Vector) temp.get(0);
            String tempbd = recLst.get(0).toString();
            Query insertQuery1 = em.createNativeQuery("insert into bill_ibcbooking(Acno,RefNo,Billtype,alphacode,payableat,infavourof,Bankname,Bankaddr,Instno,InstAmount,Comm,Remtype,InstDt,Dt,Status,Auth,EnterBy,postage) values" + "('" + acNo + "','" + refNo + "','" + billType + "','" + alphaCode + "', '" + payableAt + "','" + inFavOf + "', '" + bankName + "', '" + bankAdd + "', '" + instNo + "', " + instAmt + ", " + comm + ",'" + remType + "', '" + instDate + "', '" + tempbd + "','E','N','" + user + "'," + postage + ")");
            var = insertQuery1.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Record Not Saved.";
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
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String updateBtn(String brCode, String acNo, String refNo, String billType, String alphaCode, String payableAt, String inFavOf, String bankName, String bankAdd, String instNo, double instAmt, float comm, String remType, String instDate, String user, float postage) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            List dateVal = em.createNativeQuery("select TIMESTAMPDIFF(DAY,CURDATE(),'" + instDate + "')").getResultList();
            Vector dt = (Vector) dateVal.get(0);
            int n = Integer.parseInt(dt.get(0).toString());
            if (n > 0) {
                ut.rollback();
                return "Please Select Valid Date of Instrument";
            }
            List temp = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
            Vector recLst = (Vector) temp.get(0);
            String tempbd = recLst.get(0).toString();
            Query updateQuery = em.createNativeQuery("update bill_ibcbooking set alphacode='" + alphaCode + "',Billtype='" + billType + "', Comm=" + comm + ",Bankname='" + bankName + "',Bankaddr='" + bankAdd + "',instAmount=" + instAmt + ",Instno='" + instNo + "',InstDt='" + instDate + "', " + " Dt='" + tempbd + "',LastupdateBy='" + user + "',payableAt='" + payableAt + "',Postage=" + postage + ",Refno='" + refNo + "',infavourof='" + inFavOf + "',remType='" + remType + "' where acno= '" + acNo + "'and Instno='" + instNo + "'");
            var = updateQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Record Not Updated.";
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
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List dateDiff(String dt) throws ApplicationException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            List dateDiff = em.createNativeQuery("select TIMESTAMPDIFF(DAY,'" + dt + "','" + sdf.format(date) + "')").getResultList();
            return dateDiff;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }


    }

    public List fYear(String brCode) throws ApplicationException {
        List varlist = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Distinct FYear From bill_obcbooking bill, accountmaster am  Where bill.acno = am.acno and am.curBrCode = '" + brCode + "' and BillNo Is Not Null");
            varlist = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return varlist;
    }

    public List descriptionCodeBook() throws ApplicationException {
        List varlist = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Distinct description from codebook where GroupCode=13 and code<>0");
            varlist = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return varlist;
    }
    int billNo;
    int fyear;
    String billType;

    public List tabDataObcRealization(float billNo, int fyear, String billType, String brCode) throws ApplicationException {
        List varlist = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select bill.InstNo,bill.InstAmount,date_format(bill.InstDt,'%d/%m/%Y') AS InstDt,"
                    + "bill.Acno,bill.Comm,bill.Postage From bill_obcbooking  bill, accountmaster am Where BillNo =" + billNo + " And "
                    + "FYear=" + fyear + " and billtype='" + billType + "' and bill.acno = am.acno and am.curBrCode = '" + brCode + "'");
            varlist = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return varlist;
    }

    public String custName(float billNo, int fyear, String billType, String brCode) throws ApplicationException {
        List list = new ArrayList();
        String custNames = "";
        List custName = new ArrayList();
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            list = em.createNativeQuery("Select bill.InstNo,bill.InstAmount,bill.InstDt,bill.Acno,bill.Comm,bill.Postage From bill_obcbooking  bill, accountmaster am  "
                    + "Where bill.BillNo =" + billNo + " And bill.FYear=" + fyear + " and bill.billtype='" + billType + "'  and bill.acno = am.acno and "
                    + "am.curBrCode ='" + brCode + "'").getResultList();
            if (!list.isEmpty()) {
                Vector selectAlphas = (Vector) list.get(0);
                String accNo = selectAlphas.get(3).toString();
                String accNature = fTSPosting43CBSBean.getAccountNature(accNo);
                if ((accNature.equals(CbsConstant.FIXED_AC)) || (accNature.equals(CbsConstant.MS_AC))) {
                    custName = em.createNativeQuery("Select CustName from td_accountmaster Where acno='" + accNo + "'").getResultList();
                    Vector custumerName = (Vector) custName.get(0);
                    custNames = custumerName.get(0).toString();
                } else if (accNature.equals(CbsConstant.PAY_ORDER)) {
                    custName = em.createNativeQuery("Select AcName from gltable Where acno='" + accNo + "'").getResultList();
                    Vector custumerName = (Vector) custName.get(0);
                    custNames = custumerName.get(0).toString();
                } else {
                    custName = em.createNativeQuery("Select CustName from accountmaster Where acno='" + accNo + "'").getResultList();
                    Vector custumerName = (Vector) custName.get(0);
                    custNames = custumerName.get(0).toString();
                }
            }
            ut.commit();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return custNames;
    }
    float TxtOtherCharges;
    float instAmt;
    float comm;
    float postage;
    float TxtAmtCredited;
    List checkList = null;
    String GLHO = "";
    String GLBPIntRec = "";
    String GLBCComm = "";
    String GLBillCol = "";
    String GLBillPay = "";
    String GLDraftPay = "";
    String GLCASEPAY = "";
    String GLHOPAY = "";
    String GLBillLodg = "";
    String GLChequeRet = "";
    String GLSundry = "";
    String BnkGuarCr = "";
    String BnkGuarDr = "";
    String GLSundryName = "";
    String GLService = "";
    String GLLcComm = "";
    String GLBgComm = "";

    public List grdDataObcRealization(String BRCODE) throws ApplicationException {
        List varlist = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select bill.acno,bill.InstNo,bill.InstAmount,date_format(bill.InstDt,'%d/%m/%Y') "
                    + "AS InstDt,bill.BankName,bill.bankAddr,bill.BillType,bill.EnterBy,bill.BillNo,bill.Fyear,bill.Coll_AlphaCode,"
                    + "bill.Coll_BankName,bill.Coll_BankAddr From bill_obcbooking bill,accountmaster am where bill.acno = am.acno "
                    + "and am.curBrCode = '" + BRCODE + "' and bill.billno is not null and billType in ('BC') and bill.bcrealizationdt is null order by bill.billType,bill.billno,bill.fyear");
            varlist = selectQuery.getResultList();

//            checkList = em.createNativeQuery("select name from sysobjects where name='Bill_IBCOBCGLMast' and xtype='u'").getResultList();
//            if (checkList.isEmpty()) {
//                GLHO = BRCODE + GlConstant.GLHO;
//                GLBPIntRec = BRCODE + GlConstant.GLBPIntRec;
//
//                GLBCComm = BRCODE + GlConstant.GLBCComm;
//                GLBillPay = BRCODE + GlConstant.GLBillPay;
//
//                GLDraftPay = BRCODE + GlConstant.GLBPIntRec;
//                GLCASEPAY = BRCODE + GlConstant.GLDraftPay;
//                GLHOPAY = BRCODE + GlConstant.GLHOPAY;
//                GLBillCol = BRCODE + GlConstant.GLBillCol;
//
//                GLBillLodg = BRCODE + GlConstant.GLBillLodg;
//
//                GLChequeRet = BRCODE + GlConstant.GLChequeRet;
//                GLSundry = BRCODE + GlConstant.GLSundry;
//                BnkGuarCr = BRCODE + GlConstant.BnkGuarCr;
//                BnkGuarDr = BRCODE + GlConstant.BnkGuarDr;
//                GLService = BRCODE + GlConstant.GLService;
//                GLLcComm = BRCODE + GlConstant.GLLcComm;
//                GLBgComm = BRCODE + GlConstant.GLBgComm;
//            } 
//            else {
            checkList = em.createNativeQuery("select GLHO, GLBPIntRec, GLBCComm, GLBillPay, GLDraftPay, GLCASEPAY, GLHOPAY, GLBillCol, GLBillLodg,GLChequeRet, GLSundry, BnkGuarCr, BnkGuarDr, GLService, GLLcComm, GLBgComm from bill_ibcobcglmast").getResultList();
//            if (checkList.isEmpty()) {
//                GLHO = BRCODE + GlConstant.GLHO;
//                GLBPIntRec = BRCODE + GlConstant.GLBPIntRec;
//
//                GLBCComm = BRCODE + GlConstant.GLBCComm;
//                GLBillPay = BRCODE + GlConstant.GLBillPay;
//
//                GLDraftPay = BRCODE + GlConstant.GLBPIntRec;
//                GLCASEPAY = BRCODE + GlConstant.GLDraftPay;
//                GLHOPAY = BRCODE + GlConstant.GLHOPAY;
//                GLBillCol = BRCODE + GlConstant.GLBillCol;
//
//                GLBillLodg = BRCODE + GlConstant.GLBillLodg;
//
//                GLChequeRet = BRCODE + GlConstant.GLChequeRet;
//                GLSundry = BRCODE + GlConstant.GLSundry;
//                BnkGuarCr = BRCODE + GlConstant.BnkGuarCr;
//                BnkGuarDr = BRCODE + GlConstant.BnkGuarDr;
//                GLService = BRCODE + GlConstant.GLService;
//                GLLcComm = BRCODE + GlConstant.GLLcComm;
//                GLBgComm = BRCODE + GlConstant.GLBgComm;
//            } else {
//                Vector secLst1 = (Vector) checkList.get(0);
//                GLBPIntRec = secLst1.get(1).toString();
//                GLBillPay = secLst1.get(3).toString();
//                GLBillCol = secLst1.get(7).toString();
//                GLBillLodg = secLst1.get(8).toString();
//
//            }
//            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return varlist;
    }

    public float OtherCharges(float billNo, int fyear, String billType, float TxtOtherCharges, float instAmt, float comm, float postage, String brCode) throws ApplicationException {
        List resultList;
        String tmpAlpha;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            resultList = em.createNativeQuery("Select Coll_AlphaCode From bill_obcbooking bill,accountmaster am where Billno=" + billNo + " and Fyear=" + fyear + " and billType='" + billType + "'and bill.acno = am.acno and am.curBrCode = '" + brCode + "'").getResultList();
            if (!resultList.isEmpty()) {
                Vector v = (Vector) resultList.get(0);
                tmpAlpha = v.get(0).toString();
            } else {
                tmpAlpha = "0";
            }
            if (TxtOtherCharges >= (instAmt) - (comm + postage)) {
            } else {
                TxtAmtCredited = (instAmt) - (TxtOtherCharges + comm + postage);
            }
            ut.commit();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return TxtAmtCredited;

    }

    public String passClickODBCRealization(float billNo, int fyear, String billType, float TxtOtherCharges, float instAmt, float comm, float postage, float TxtAmtCredited, String accountNo, String InstNum, String realiseDate, String user, String BRCODE, String instDt, String PanelReason) throws ApplicationException {
        List resultList;
        float TmpTrsno;
        List resultList1 = new ArrayList();
        String tmpAlpha = "";
        float retCarges = 0;
        String reason = "";
        String error = "";

        UserTransaction ut = context.getUserTransaction();
        try {
            TmpTrsno = fTSPosting43CBSBean.getTrsNo();
            ut.begin();
            String field = FieldCheck(billType, billNo, fyear, TxtOtherCharges, instAmt, comm, postage, TxtAmtCredited, reason, retCarges, PanelReason);
            if (!field.isEmpty() && !field.equals("True")) {
                ut.rollback();
                return field;
            }

            if ((InstNum == null) || (instDt == null) || (instAmt <= 0)) {
                ut.rollback();
                return "Please Check Instruement details";
            }
            resultList1 = em.createNativeQuery("Select bill.InstNo,bill.InstAmount,date_format(bill.InstDt,'%d/%m/%Y') AS InstDt,bill.Acno,"
                    + "bill.Comm,bill.Postage From bill_obcbooking  bill, accountmaster am Where bill.BillNo =" + billNo + " And"
                    + " bill.FYear=" + fyear + " and bill.billtype='" + billType + "' and bill.acno = am.acno and am.curBrCode ='" + BRCODE + "'").getResultList();
            if (resultList1.isEmpty()) {
                ut.rollback();
                return "Please Check Instruement details";
            }

            resultList = em.createNativeQuery("Select ifnull(max(Coll_AlphaCode),0) From bill_obcbooking  bill, accountmaster am where Billno=" + billNo + " and "
                    + "Fyear=" + fyear + " and billType='" + billType + "' and bill.acno = am.acno and am.curBrCode ='" + BRCODE + "'").getResultList();
            if (!resultList.isEmpty()) {
                Vector v = (Vector) resultList.get(0);
                tmpAlpha = v.get(0).toString();
            }
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + BRCODE + "' ").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            String passbc;
            if (billType.equalsIgnoreCase("BC")) {
                passbc = passBc(billNo, fyear, tmpAlpha, accountNo, TxtAmtCredited, InstNum, TmpTrsno, Tempbd, user, BRCODE, comm, postage, accountNo, accountNo, instAmt, TxtOtherCharges, accountNo, accountNo, billType, realiseDate, instDt);
                if (!(passbc.equals("true"))) {
                    ut.rollback();
                    error = passbc;
                    return error;
                } else {
                    error = "Generated Batch No." + TmpTrsno;
                    ut.commit();
                    return error;
                }
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
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
        return error;
    }

    public String FieldCheck(String billType, float billNo, int fyear, float TxtOtherCharges, float instAmt, float comm, float postage, float TxtAmtCredited, String reason, float retCarges, String PanelReason) {
        if (billType == null) {
            return "Bill Type Should Not Be Blank";
        }
        if ((billType.equalsIgnoreCase("--SELECT--")) || (billType.trim().equalsIgnoreCase("")) || (billType == null)) {
            return "Bill Type Should Not Be Blank";
        }
        if ((billNo < 0)) {
            return "Please Enter Valid Bill No.";
        }
        if (fyear <= 0) {
            return "Year Should Not Be Blank";
        }

        if (PanelReason.equals("True")) {
            if ((TxtOtherCharges + comm + postage) < 0) {
                return "Please Enter Valid Charges";
            }
            if (TxtAmtCredited < 0 || (TxtAmtCredited != instAmt - (TxtOtherCharges + comm + postage))) {
                return "Please Enter Valid Amount";
            }
        } else if (PanelReason.equals("False")) {
            if (retCarges < 0) {
                return "Return Charges Should Not Be Blank";
            }

            if ((reason.equalsIgnoreCase("--SELECT--")) || (reason.trim().equalsIgnoreCase("")) || (reason == null)) {
                return "Reason For Cancel Should Not Be Blank";
            }
        }
        return "True";
    }

    public String passBc(float billNo, int fyear, String tmpAlpha, String accountNo, double TxtAmtCredited, String InstNum,
            float TmpTrsno, String Tempbd, String user, String BRCODE, double comm, double postage, String GLBCComm, String GLBillCol,
            double instAmt, float TxtOtherCharges, String GLHO, String GLBillLodg, String billType, String realiseDate, String instDt) throws ApplicationException {
        try {
            String Details = "BCNO:" + billNo + "/" + fyear;
            String TmpDetails = "BCNO:" + billNo + "/" + fyear + "/" + tmpAlpha + " For Acno:" + "" + accountNo;
            float RECNO = 0;
            float TOKENNO_Dr = 0;
            String BRCODE1 = fTSPosting43CBSBean.getCurrentBrnCode(accountNo);
            String str1 = fTSPosting43CBSBean.ftsPosting43CBS(accountNo, 2, 0, TxtAmtCredited, Tempbd, Tempbd, user, BRCODE, BRCODE1, 1, Details, TmpTrsno, RECNO, 0, null, "N", null, "20", 3, InstNum, instDt, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
            if (!(str1.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                String msg = str1.substring(0, str1.indexOf("----"));
                return msg;
            }

            if ((comm + postage) > 0) {
                double AMT1 = comm + postage;
                String str2 = fTSPosting43CBSBean.ftsPosting43CBS(GLBCComm, 2, 0, AMT1, Tempbd, Tempbd, user, BRCODE, BRCODE1, 1, TmpDetails, TmpTrsno, RECNO, 0, null, "N", null, "20", 3, "", null, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
                if (!(str2.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    String msg = str2.substring(0, str2.indexOf("----"));
                    return msg;
                }
            }

            double amt3 = instAmt - TxtOtherCharges;

            String str3 = fTSPosting43CBSBean.ftsPosting43CBS(GLHO, 2, 1, amt3, Tempbd, Tempbd, user, BRCODE, BRCODE1, 1, TmpDetails, TmpTrsno, RECNO, 0, null, "N", null, "20", 3, "", null, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
            if (!(str3.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                String msg = str3.substring(0, str3.indexOf("----"));
                return msg;
            }


            String details = "VCH : For BCNO:" + billNo + "/" + fyear + " For Acno:" + "" + accountNo;

            String str4 = fTSPosting43CBSBean.ftsPosting43CBS(GLBillCol, 2, 1, instAmt, Tempbd, Tempbd, user, BRCODE, BRCODE1, 1, details, TmpTrsno, RECNO, 0, null, "N", null, "20", 3, "", null, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
            if (!(str4.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                String msg = str4.substring(0, str4.indexOf("----"));
                return msg;
            }


            String str5 = fTSPosting43CBSBean.ftsPosting43CBS(GLBillLodg, 2, 0, instAmt, Tempbd, Tempbd, user, BRCODE, BRCODE1, 1, TmpDetails, TmpTrsno, RECNO, 0, null, "N", null, "20", 3, "", null, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
            if (!(str5.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                String msg = str5.substring(0, str5.indexOf("----"));
                return msg;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return "true";
    }

    public String dishonorClickOBCRealization(String instNo, String instDt, float instAmt, String billType, float billNo, int fYear, String panelReason, Float ourCharges, Float retCharges, String resonCancel, String accountNo, float cumu, float postage, float amtCredited, String user, String BRCODE) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            Float TrsNo = fTSPosting43CBSBean.getTrsNo();
            ut.begin();
            String alphaCode;
            float tmpDpLimit;
            int exec;
            String msg = null;
            List balance;
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + BRCODE + "' ").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();

            if ((instNo == null) || (instDt == null) || (instAmt <= 0)) {
                ut.rollback();
                return "Please Check Instruement details";
            }
            List select = em.createNativeQuery("Select bill.InstNo,bill.InstAmount,bill.InstDt,bill.Acno,bill.Comm,bill.Postage From "
                    + "bill_obcbooking bill,accountmaster am  Where BillNo = " + billNo + " And FYear=" + fYear + " and "
                    + "billtype='" + billType + "' and bill.acno = am.acno and am.curBrCode = '" + BRCODE + "'").getResultList();
            if (select.isEmpty()) {
                ut.rollback();
                return "Please Check Instruement details";
            }
            String fd = FieldCheck(billType, billNo, fYear, ourCharges, instAmt, cumu, postage, amtCredited, resonCancel, retCharges, panelReason);
            if (!fd.equals("True")) {
                ut.rollback();
                return "" + fd;
            }
            List alphaCde = em.createNativeQuery("Select ifnull(max(Coll_AlphaCode),0) From bill_obcbooking bill,accountmaster am where "
                    + "Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "' and bill.acno = am.acno and "
                    + "am.curBrCode = '" + BRCODE + "'").getResultList();
            Vector alphaCd = (Vector) alphaCde.get(0);
            String alphaCodes = alphaCd.get(0).toString();

            if (billType.equals("BC")) {
                String disBc = dishonorBc(retCharges, accountNo, instNo, ourCharges, instAmt, postage, billNo, fYear, alphaCodes, BRCODE, user, instDt);
                if (!(disBc.equals("true"))) {
                    ut.rollback();
                    return disBc;
                }
            }

            float RecNo = fTSPosting43CBSBean.getRecNo();

            Query insertQuery = em.createNativeQuery("Insert bill_obcprocessed(billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage,RetCharges,ReasonforCancel,InFavourOf,InstDt,InstEntryDt,Dt,status,EnterBy,TrsNo,RecNo,Invno,BillDetainPeriod,BillDetainDt,Coll_AlphaCode,Coll_BankName,Coll_BankAddr) Select billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage," + retCharges + ",'" + resonCancel + "',InFavourOf,InstDt,Dt,'" + Tempbd + "','D','" + user + "'," + TrsNo + "," + RecNo + ",Invno,billdetainperiod,billdetaindt,Coll_AlphaCode,Coll_BankName,Coll_BankAddr From bill_obcbooking where Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "'");
            Integer varint = insertQuery.executeUpdate();

            Query updateQuery = em.createNativeQuery("delete bill_obcbooking from bill_obcbooking bill inner join accountmaster am"
                    + " on bill.acno = am.acno and bill.Billno=" + billNo + " and bill.Fyear=" + fYear + " and "
                    + "bill.billType='" + billType + "' and am.curBrCode = '" + BRCODE + "'");
            int var = updateQuery.executeUpdate();

            if ((varint > 0) || (var > 0)) {
                ut.commit();
                return "Dishonor Is Successfull";
            } else {
                ut.rollback();
                return "Dishonor could not be Successfull";
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
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String dishonorBc(Float retCharges, String accountNo, String instNo, double ourCharges,
            double instAmt, double postage, float billNo, int fYear, String alphaCodes, String BRCODE, String user, String instDt) throws ApplicationException {
        try {
            float RECNO = 0;
            float TOKENNO_Dr = 0;
            String detail = "VCH : Comm For BCNO: " + "" + billNo + " / " + " " + fYear;
            String details = "VCH : Comm For BCNO: " + "" + billNo + " / " + " " + fYear + " For Acno: " + accountNo;
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + BRCODE + "' ").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            String BRCODE1 = fTSPosting43CBSBean.getCurrentBrnCode(accountNo);
            Float TrsNo = fTSPosting43CBSBean.getTrsNo();
            double allAmount = (ourCharges + postage + retCharges);
            if (allAmount != 0) {
                String str1 = fTSPosting43CBSBean.ftsPosting43CBS(accountNo, 2, 1, allAmount, Tempbd, Tempbd, user, BRCODE, BRCODE1, 0, detail, TrsNo, RECNO, 0, null, "N", null, "20", 3, instNo, instDt, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
                if (!(str1.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    String msg = str1.substring(0, str1.indexOf("----"));
                    return msg;
                }
                String str2 = fTSPosting43CBSBean.ftsPosting43CBS(GLBCComm, 2, 0, allAmount, Tempbd, Tempbd, user, BRCODE, BRCODE1, 0, details, TrsNo, RECNO, 0, null, "N", null, "20", 3, "", null, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
                if (!(str2.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    String msg = str2.substring(0, str2.indexOf("----"));
                    return msg;
                }
            }
            String gdetail = "BCNO: " + "" + billNo + " / " + " " + fYear + " " + alphaCodes + " For Acno: " + accountNo;
            String str3 = fTSPosting43CBSBean.ftsPosting43CBS(GLBCComm, 2, 1, instAmt, Tempbd, Tempbd, user, BRCODE, BRCODE1, 0, gdetail, TrsNo, RECNO, 0, null, "N", null, "20", 3, "", null, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
            if (!(str3.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                String msg = str3.substring(0, str3.indexOf("----"));
                return msg;
            }
            String str4 = fTSPosting43CBSBean.ftsPosting43CBS(GLBCComm, 2, 0, instAmt, Tempbd, Tempbd, user, BRCODE, BRCODE1, 0, "", TrsNo, RECNO, 0, null, "N", null, "20", 3, "", null, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
            if (!(str4.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                String msg = str4.substring(0, str4.indexOf("----"));
                return msg;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return "true";
    }

    public List fYearInward(String brCode) throws ApplicationException {
        List yearResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Distinct FYear From bill_ibcbooking bill, accountmaster am where  bill.acno = am.acno and am.curBrCode='" + brCode + "' and FYear is not null");
            yearResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return yearResult;
    }

    public List alphaCode(String brCode) throws ApplicationException {
        List alphaCodeResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Distinct AlphaCode From branchmaster where BranchName is not null");
            alphaCodeResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return alphaCodeResult;
    }

    public List reasonForCancel() throws ApplicationException {
        List reasonResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Distinct Description From codebook Where GroupCode=13 and code<>0");
            reasonResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return reasonResult;
    }

    public List LoadGridBookedData(String brCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select BillNo,BillType,RemType,Fyear,subString (Acno,3,8) As Acno,InstNo,InstAmount,Comm,Postage,date_format(InstDt,'%d/%m/%Y') AS InstDt,BankName,BankAddr,EnterBy From bill_ibcbooking bill, accountmaster am where bill.acno = am.acno and am.curBrCode='" + brCode + "' and billtype='BP' and billno is not null order by billType,billno,fyear");
            tableResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableResult;
    }

    public String dishonorClick(String instNo, String instDt, double instAmt, String billType, float billNo, int fYear, String panelReason, double ourCharges, double amtDebited, double retCharges, String resonCancel, String accountNo, String user, String BRCODE) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String s = null;
        try {
            ut.begin();
            Float TrsNo;
            String alphaCode;
            float tmpDpLimit;
            int exec;
            String msg = null;
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE brncode='" + BRCODE + "'and DAYENDFLAG='N'  ").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            int Tempbds = Integer.parseInt(Tempbd);
            if ((instNo == null) || (instDt == null) || (instAmt <= 0)) {
                ut.rollback();
                return s = "Please Check Instruement details";
            }
            List select = em.createNativeQuery("Select bill.InstNo,bill.InstAmount,bill.InstDt,bill.Acno,bill.Comm,bill.Postage From bill_ibcbooking bill, accountmaster am Where bill.acno = am.acno and am.curBrCode='" + BRCODE + "' and bill.BillNo =" + billNo + " And bill.FYear=" + fYear + " and bill.billtype='" + billType + "'").getResultList();
            if (select.isEmpty()) {
                ut.rollback();
                return s = "Please Check Instruement details";
            }
            String fd = fieldCheck(billType, billNo, fYear, panelReason, ourCharges, amtDebited, instAmt, retCharges, resonCancel);
            if (!fd.equals("False")) {
                String[] values = null;
                String spliter = ": ";
                values = fd.split(spliter);
                String b = (values[0]);
                String a = (values[1]);
                if (a.equals("True")) {
                    ut.rollback();
                    return s = "" + b;
                }
            }
            TrsNo = fTSPosting43CBSBean.getTrsNo();

            List alphaCde = em.createNativeQuery("Select bill.AlphaCode From bill_ibcbooking bill, accountmaster am where bill.acno = am.acno and am.curBrCode='" + BRCODE + "' and Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "'").getResultList();
            Vector alphaCd = (Vector) alphaCde.get(0);
            String alphaCodes = alphaCd.get(0).toString();

            if (alphaCde.isEmpty()) {
                alphaCode = "0";
            } else {
                alphaCode = alphaCodes;
            }

            tmpDpLimit = 0;
            List odLimit = em.createNativeQuery("Select coalesce(odlimit,0) From loan_appparameter where BrCode='" + BRCODE + "' and acno='" + accountNo + "'").getResultList();
            if (!odLimit.isEmpty()) {
                Vector oLimits = (Vector) odLimit.get(0);
                float odLimits = Float.parseFloat(oLimits.get(0).toString());
                if (odLimit.isEmpty()) {
                    tmpDpLimit = odLimits;
                }
            }
            String acNature = fTSPosting43CBSBean.getAccountNature(accountNo);
            float RecNo = fTSPosting43CBSBean.getRecNo();
            String ab = fTSPosting43CBSBean.checkBalance(accountNo, ourCharges, user);
            if (!ab.equals("True")) {
                ut.rollback();
                return s = "" + ab;
            }
            String pro = procDishonor(retCharges, accountNo, instNo, billNo, fYear, BRCODE, user, instDt);
            if (!pro.equals("True")) {
                ut.rollback();
                return s = pro;
            }
            Query insertQuery = em.createNativeQuery("Insert bill_ibcprocessed(billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage,RetCharges,ReasonforCancel,InFavourOf,InstDt,InstEntryDt,Dt,status,EnterBy,TrsNo,RecNo,refno) Select bill.billNo,bill.Acno,bill.FYear,bill.BillType,bill.Alphacode,bill.BankName,bill.BankAddr,bill.InstNo,bill.InstAmount,bill.Comm,bill.Postage," + retCharges + ",'" + resonCancel + "',bill.InFavourOf,bill.InstDt,bill.Dt,'" + Tempbd + "','D','" + user + "'," + TrsNo + "," + RecNo + ",bill.refno from bill_ibcbooking bill, accountmaster am where bill.acno = am.acno and am.curBrCode='" + BRCODE + "' and bill.Billno=" + billNo + " and bill.Fyear=" + fYear + " and bill.billType='" + billType + "'");
            Integer varint = insertQuery.executeUpdate();

            Query updateQuery = em.createNativeQuery("Delete bill_ibcbooking From bill_ibcbooking bill inner join accountmaster am on bill.acno = am.acno and Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "' and am.curBrCode = '" + BRCODE + "'");
            int var = updateQuery.executeUpdate();

            if ((varint > 0) || (var > 0)) {
                ut.commit();
                return s = "Dishonor Is Successfull";
            } else {
                ut.rollback();
                return s = "Dishonor could not be Successfull";
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String procDishonor(double retCharges, String accountNo, String instNo, float billNo, int fYear, String BRCODE, String user, String instDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {

            int exec = 0;
            int exec1 = 0;
            String CHK_MSG = null;
            String MSG = null;
            float trsNumber;
            float TOKENNO = 0.0f;
            float TRSNO1 = 0.0f;
            String detail = "VCH : Charges For BPNo: " + "" + billNo + " / " + " " + fYear;
            String details = "VCH : Charges For BCNo: " + "" + billNo + " / " + " " + fYear + " For Acno: " + accountNo;
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and brncode='" + BRCODE + "' ").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            int Tempbds = Integer.parseInt(Tempbd);

            trsNumber = fTSPosting43CBSBean.getTrsNo();
            List checkList = null;
            String GLChequeRet = "";

//            checkList = em.createNativeQuery("select name from sysobjects where name='Bill_IBCOBCGLMast' and xtype='u'").getResultList();
//            if (checkList.isEmpty()) {
//                GLChequeRet = BRCODE + "GL00261501";
//            } 

//            else {
            checkList = em.createNativeQuery("select GLHO, GLBPIntRec, GLBCComm, GLBillPay, GLDraftPay, GLCASEPAY, GLHOPAY, GLBillCol, GLBillLodg,GLChequeRet, GLSundry, BnkGuarCr, BnkGuarDr, GLService, GLLcComm, GLBgComm from bill_ibcobcglmast").getResultList();
            if (checkList.isEmpty()) {
                GLChequeRet = BRCODE + "GL00261501";
            }
//            }
            if (retCharges > 0) {
                float RECNO = 0;
                float TOKENNO_Dr = 0;
                String checkListD1 = fTSPosting43CBSBean.ftsPosting43CBS(accountNo, 2, 1, retCharges, Tempbd, Tempbd, user, BRCODE, BRCODE, 0, detail, trsNumber, RECNO, 0, null, "N", null, "20", 3, instNo, instDt, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
                if (!(checkListD1.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    return checkListD1;
                }
                String checkListD2 = fTSPosting43CBSBean.ftsPosting43CBS(GLChequeRet, 2, 0, retCharges, Tempbd, Tempbd, user, BRCODE, BRCODE, 0, detail, trsNumber, RECNO, 0, null, "N", null, "20", 3, instNo, instDt, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
                if (!(checkListD2.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    return checkListD2;
                }
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String accountValidation(String accountNo, float instAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String accountFlag = "False";
            List accStatus;
            Integer optStatus = null;
            Integer accSts;
            Float amounts;
            List balance;
            String a = "False";
            Integer tmpOptStatus = 1;
            String BRCODE = fTSPosting43CBSBean.getCurrentBrnCode(accountNo);
            String acNature = fTSPosting43CBSBean.getAccountNature(accountNo);
            if (acNature.equals(CbsConstant.FIXED_AC) || (acNature.equals(CbsConstant.MS_AC))) {
                accStatus = em.createNativeQuery("select accstatus from td_accountmaster where CurBrCode ='" + BRCODE + "' and acno = '" + accountNo + "'").getResultList();
                Vector acStatus = (Vector) accStatus.get(0);
                accSts = Integer.parseInt(acStatus.get(0).toString());
            } else {
                accStatus = em.createNativeQuery("select accstatus,coalesce(optstatus,'1') from accountmaster where CurBrCode='" + BRCODE + "' and acno ='" + accountNo + "'").getResultList();
                Vector acStatus = (Vector) accStatus.get(0);
                accSts = Integer.parseInt(acStatus.get(0).toString());
                String otStatus = acStatus.get(1).toString();
                optStatus = Integer.parseInt(otStatus);
            }
            if (!accStatus.isEmpty()) {
                if (!acNature.equals(CbsConstant.FIXED_AC) && (!acNature.equals(CbsConstant.MS_AC))) {
                    tmpOptStatus = optStatus;
                }

                if ((accSts == 1) && (tmpOptStatus == 2)) {
                    ut.rollback();
                    accountFlag = "True";
                    return a = "Account Is Inoperative And Kindly Dishonor This Inst." + ": " + accountFlag;
                } else if ((accSts != 1) && (accSts != 10)) {
                    List discription = em.createNativeQuery("select description from codebook where groupcode = 3 and code =" + accSts + " ").getResultList();
                    if (!discription.isEmpty()) {
                        ut.rollback();
                        accountFlag = "True";
                        return a = "Account is Marked as And Kindly Dishonor This Inst." + ": " + accountFlag;
                    }
                } else if (accSts == 10) {
                    List amount = em.createNativeQuery("select (ifnull(a.amount,0)) AS amount from accountstatus a ,accountmaster am where CurBrCode='" + BRCODE + "' and a.acno ='" + accountNo + "' and a.acno = am.acno and a.spno = (select max(spno) from accountstatus a ,accountmaster am where a.acno = am.acno and CurBrCode='" + BRCODE + "' and  a.acno ='" + accountNo + "' and  auth = 'Y')").getResultList();
                    Vector amt = (Vector) amount.get(0);
                    amounts = Float.parseFloat(amt.get(0).toString());

                    String ABC = "Account Has been Lien Marked for amount of Rs. " + amounts;

                    if ((acNature.equals(CbsConstant.PAY_ORDER))) {
                        balance = em.createNativeQuery("select rb.balance from reconbalan rb, accountmaster am where am.curBrCode='" + BRCODE + "' and rb.acno = am.acno and rb.acno='" + accountNo + "'").getResultList();
                        //  balance = em.createNativeQuery("select balance from RECONBALAN  where substring(acno,1,2)='" + BRCODE + "' and acno='" + accountNo + "'").getResultList();
                    } else if (acNature.equals(CbsConstant.FIXED_AC) || (acNature.equals(CbsConstant.MS_AC))) {
                        balance = em.createNativeQuery("select rb.balance from td_reconbalan rb, td_accountmaster am where am.curBrCode='" + BRCODE + "' and rb.acno = am.acno and rb.acno='" + accountNo + "'").getResultList();
                        // balance = em.createNativeQuery("select balance from TD_RECONBALAN where substring(acno,1,2)='" + BRCODE + "' and acno='" + accountNo + "'").getResultList();
                        //   } else if (acNature.equals("CA") || (acNature.equals("CC")) || (acNature.equals("OD"))) {
                    } else if (acNature.equals(CbsConstant.CURRENT_AC)) {
                        // balance = em.createNativeQuery("select balance from CA_RECONBALAN where substring(acno,1,2)='" + BRCODE + "' and acno='" + accountNo + "'").getResultList();
                        balance = em.createNativeQuery("select rb.balance from ca_reconbalan rb,accountmaster am where am.curBrCode='" + BRCODE + "' and rb.acno = am.acno and rb.acno='" + accountNo + "'").getResultList();
                    } else {
                        // balance = em.createNativeQuery("select balance from RECONBALAN where substring(acno,1,2)='" + BRCODE + "' and acno='" + accountNo + "'").getResultList();
                        balance = em.createNativeQuery("select rb.balance from reconbalan rb, accountmaster am where am.curBrCode='" + BRCODE + "' and rb.acno = am.acno and rb.acno='" + accountNo + "'").getResultList();
                    }

                    Vector bl = (Vector) balance.get(0);
                    Float balances = Float.parseFloat(bl.get(0).toString());

                    if (balance.isEmpty()) {
                        if (amounts >= (balances - instAmt)) {
                            ut.rollback();
                            accountFlag = "True";
                            return a = "Please Dishonor This Instrument " + ": " + ABC + ": " + accountFlag;
                        }
                    }
                }
            }
            ut.commit();
            return "" + a;
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new EJBException("Rollback failed: " + syex.getMessage());
            }
            throw new EJBException("Transaction failed: " + e.getMessage());
        }
    }

    public String accountValidationResult(String accountNo, String instNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String BRCODE = fTSPosting43CBSBean.getCurrentBrnCode(accountNo);
            String acNature = fTSPosting43CBSBean.getAccountNature(accountNo);
            if (acNature.equals(CbsConstant.SAVING_AC) || (acNature.equals(CbsConstant.CURRENT_AC))) {
                List allResult = em.createNativeQuery("select ch.Acno,ch.statusflag from chbook_sb ch,accountmaster am where am.curBrCode='" + BRCODE + "' and ch.acno = am.acno and ch.acno = '" + accountNo + "' and ch.Chqno ='" + instNo + "'").getResultList();
                if (allResult.isEmpty()) {
                    ut.rollback();
                    return "Instrument is Not Issued";
                } else {

                    if (!allResult.isEmpty()) {
                        Vector alR = (Vector) allResult.get(0);
                        String statusFlag = alR.get(1).toString();
                        if (statusFlag.equals("S")) {
                            ut.rollback();
                            return "Cheque is Marked as Stop Payment And Kindly Dishonor This Inst. ";
                        }
                    }
                }
            }
            ut.commit();
            return "";
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new EJBException("Rollback failed: " + syex.getMessage());
            }
            throw new EJBException("Transaction failed: " + e.getMessage());
        }
    }

    public String procPass(String billType, double ourCharges, String accountNo, String instNo, float billNo, int fYear,
            double AmtToDebited, int payBy, double instAmt, String BRCODE, String user, String instDt) throws ApplicationException {
        try {

            int execX = 0;
            int execY = 0;
            int execZ = 0;
            String CHK_MSG = null;
            String MSG = null;
            float trsNumber;
            float TOKENNO = 0.0f;
            float TRSNO1 = 0.0f;
            String pFlag = "False";


            List checkList = null;
            String GLHO = "";
            String GLBPIntRec = "";
            String GLBCComm = "";
            String GLBillCol = "";
            String GLBillPay = "";
            String GLDraftPay = "";
            String GLCASEPAY = "";
            String GLHOPAY = "";
            String GLBillLodg = "";
            String GLChequeRet = "";
            String GLSundry = "";
            String BnkGuarCr = "";
            String BnkGuarDr = "";
            String GLSundryName = "";
            String GLService = "";
            String GLLcComm = "";
            String GLBgComm = "";

//            checkList = em.createNativeQuery("select name from sysobjects where name='Bill_IBCOBCGLMast' and xtype='u'").getResultList();
//            if (checkList.isEmpty()) {
//                GLHO = BRCODE + GlConstant.GLHO;
//                GLBPIntRec = BRCODE + GlConstant.GLBPIntRec;
//
//                GLBCComm = BRCODE + GlConstant.GLBCComm;
//                GLBillPay = BRCODE + GlConstant.GLBillPay;
//
//                GLDraftPay = BRCODE + GlConstant.GLBPIntRec;
//                GLCASEPAY = BRCODE + GlConstant.GLDraftPay;
//                GLHOPAY = BRCODE + GlConstant.GLHOPAY;
//                GLBillCol = BRCODE + GlConstant.GLBillCol;
//
//                GLBillLodg = BRCODE + GlConstant.GLBillLodg;
//
//                GLChequeRet = BRCODE + GlConstant.GLChequeRet;
//                GLSundry = BRCODE + GlConstant.GLSundry;
//                BnkGuarCr = BRCODE + GlConstant.BnkGuarCr;
//                BnkGuarDr = BRCODE + GlConstant.BnkGuarDr;
//                GLService = BRCODE + GlConstant.GLService;
//                GLLcComm = BRCODE + GlConstant.GLLcComm;
//                GLBgComm = BRCODE + GlConstant.GLBgComm;
//            } 
//            else {
            checkList = em.createNativeQuery("select GLHO, GLBPIntRec, GLBCComm, GLBillPay, GLDraftPay, GLCASEPAY, GLHOPAY, GLBillCol, GLBillLodg,GLChequeRet, GLSundry, BnkGuarCr, BnkGuarDr, GLService, GLLcComm, GLBgComm from bill_ibcobcglmast").getResultList();
//            if (checkList.isEmpty()) {
//                GLHO = BRCODE + GlConstant.GLHO;
//                GLBPIntRec = BRCODE + GlConstant.GLBPIntRec;
//
//                GLBCComm = BRCODE + GlConstant.GLBCComm;
//                GLBillPay = BRCODE + GlConstant.GLBillPay;
//
//                GLDraftPay = BRCODE + GlConstant.GLBPIntRec;
//                GLCASEPAY = BRCODE + GlConstant.GLDraftPay;
//                GLHOPAY = BRCODE + GlConstant.GLHOPAY;
//                GLBillCol = BRCODE + GlConstant.GLBillCol;
//
//                GLBillLodg = BRCODE + GlConstant.GLBillLodg;
//
//                GLChequeRet = BRCODE + GlConstant.GLChequeRet;
//                GLSundry = BRCODE + GlConstant.GLSundry;
//                BnkGuarCr = BRCODE + GlConstant.BnkGuarCr;
//                BnkGuarDr = BRCODE + GlConstant.BnkGuarDr;
//                GLService = BRCODE + GlConstant.GLService;
//                GLLcComm = BRCODE + GlConstant.GLLcComm;
//                GLBgComm = BRCODE + GlConstant.GLBgComm;
//            } else {
//                Vector secLst1 = (Vector) checkList.get(0);
//                GLBPIntRec = secLst1.get(1).toString();
//                GLBillPay = secLst1.get(3).toString();
//                GLBillCol = secLst1.get(7).toString();
//                GLBillLodg = secLst1.get(8).toString();
//
//            }
//            }
            String detail = billType + " No: " + billNo + "/" + fYear + " From Acno: " + accountNo;
            String details = "VCH : Comm For " + billType + "No: " + billNo + "/" + fYear + "For Acno: " + " " + accountNo;
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + BRCODE + "'  ").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();

            int Tempbds = Integer.parseInt(Tempbd);
            trsNumber = fTSPosting43CBSBean.getTrsNo();
            float RECNO = 0;
            float TOKENNO_Dr = 0;
            String checkListD1 = fTSPosting43CBSBean.ftsPosting43CBS(GLHO, 2, 0, AmtToDebited, Tempbd, Tempbd, user, BRCODE, BRCODE, 0, detail, trsNumber, RECNO, 0, null, "N", null, "20", payBy, instNo, instDt, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
            if (!(checkListD1.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                return checkListD1;
            }
            if (ourCharges > 0) {
                String checkListD2 = fTSPosting43CBSBean.ftsPosting43CBS(GLBCComm, 2, 0, ourCharges, Tempbd, Tempbd, user, BRCODE, BRCODE, 0, detail, trsNumber, RECNO, 0, null, "N", null, "20", payBy, instNo, instDt, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
                if (!(checkListD2.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    return checkListD2;
                }

            }
            String detail1 = "VCH : BCNo: " + " " + billNo + " / " + " " + fYear;
            String checkListD3 = fTSPosting43CBSBean.ftsPosting43CBS(accountNo, 2, 1, instAmt, Tempbd, Tempbd, user, BRCODE, BRCODE, 0, detail, trsNumber, RECNO, 0, null, "N", null, "20", payBy, instNo, instDt, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
            if (!(checkListD3.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                return checkListD3;
            }
            return "True";
        } catch (Exception e) {
            throw new EJBException("Transaction failed: " + e.getMessage());
        }
    }

    public String passClick(String instNo, String instDt, double instAmt, String billType, float billNo, int fYear, String panelReason, double ourCharges, double amtDebited, double retCharges, String resonCancel, String accountNo, String name, int payBy, String user, String BRCODE) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Float TrsNo;
            Integer var4 = null;
            Integer var5 = null;
            Integer var6 = null;
            float recnum = 0.0f;
            List balance;

            if (resonCancel == null) {
                resonCancel = "A/C FROZEN";
            }
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + BRCODE + "' ").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            int Tempbds = Integer.parseInt(Tempbd);
            if ((instNo == null) || (instDt == null) || (instAmt <= 0)) {
                ut.rollback();
                return "Please Check Instruement details";
            }
            List select = em.createNativeQuery("Select bill.InstNo,bill.InstAmount,bill.InstDt,bill.Acno,bill.Comm,bill.Postage From bill_ibcbooking bill,accountmaster am Where am.curBrCode='" + BRCODE + "' and bill.acno = am.acno and  bill.BillNo = " + billNo + " And bill.FYear=" + fYear + " and bill.billtype='" + billType + "'").getResultList();
            if (select.isEmpty()) {
                ut.rollback();
                return "Please Check Instruement details";
            }

            String fd = fieldCheck(billType, billNo, fYear, panelReason, ourCharges, amtDebited, instAmt, retCharges, resonCancel);
            if (!fd.equals("False")) {
                String[] values = null;
                String spliter = ": ";
                values = fd.split(spliter);
                String b = (values[0]);
                String a = (values[1]);
                if (a.equals("True")) {
                    ut.rollback();
                    return "" + b;
                }
            }
            String acNature = fTSPosting43CBSBean.getAccountNature(accountNo);
            if ((acNature.equals(CbsConstant.SAVING_AC))) {
                balance = em.createNativeQuery("Select rb.Balance From reconbalan rb,accountmaster am Where am.curBrCode='" + BRCODE + "' and rb.acno = am.acno and rb.Acno='" + accountNo + "' And rb.Balance>=" + amtDebited + "").getResultList();
                if (balance.isEmpty()) {
                    ut.rollback();
                    return "Insufficient Fund in Acno :" + accountNo;
                }
            }
            TrsNo = fTSPosting43CBSBean.getTrsNo();
            recnum = fTSPosting43CBSBean.getRecNo();

            List billBooking = em.createNativeQuery("Select bill.BillNo,bill.Acno,bill.Fyear,bill.Refno,bill.BillType,bill.AlphaCode,"
                    + "bill.PayableAt,bill.Infavourof,bill.BankName,bill.BankAddr,bill.Instno,bill.InstAmount,bill.Comm,bill.Postage,"
                    + "bill.RemType,bill.InstDt,bill.DT,bill.Status,bill.Auth,bill.AuthBy,bill.EnterBy,bill.LastUpdateBy,bill.Trantime "
                    + "From bill_ibcbooking bill,accountmaster am where am.curBrCode='" + BRCODE + "' and bill.acno = am.acno and bill.Billno=" + billNo + " and bill.Fyear=" + fYear + " and bill.billType='" + billType + "'").getResultList();
            if (!billBooking.isEmpty()) {
                Vector billBooks = (Vector) billBooking.get(0);
                float billNumber = Float.parseFloat(billBooks.get(0).toString());
                int fYr = Integer.parseInt(billBooks.get(2).toString());
                String refNo = billBooks.get(3).toString();
                String alphaCod = billBooks.get(5).toString();

                if (alphaCod.equals("0")) {
                    ut.rollback();
                    return "alpha Code is Zero :";
                } else {
                    String prPass = procPass(billType, ourCharges, accountNo, instNo, billNo, fYear, amtDebited, payBy, instAmt, BRCODE, user, instDt);
                    if (!prPass.equals("True")) {
                        ut.rollback();
                        return prPass;
                    }
                    Query insertQueries = em.createNativeQuery("insert bill_hoothers(fyear,instno,acno,custname,infavourof,payableat,amount,enterby,Auth,AuthBy,dt,status,trantype, seqno,comm,billtype,ty,recno,origindt) select FYear,instno,Acno," + "'" + name + "'" + ",InFavourOf,Alphacode," + amtDebited + "," + "'" + user + "'" + ",'Y'," + "'" + user + "'" + ",'" + Tempbd + "','Issued', 2," + refNo + "," + ourCharges + ",'BT',0," + recnum + ",'" + instDt + "' from bill_ibcbooking where  Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "'");
                    var4 = insertQueries.executeUpdate();
                    Query insertQ = em.createNativeQuery("Insert bill_ibcprocessed(billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage,OtherCharges,CreditedAmount,InFavourOf,InstDt,instentrydt,dt,status,EnterBy,TrsNo,RecNo,Refno,PayableAt,RemType) select billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage," + ourCharges + "," + amtDebited + ",InFavourOf,'" + instDt + "','" + Tempbd + "','" + Tempbd + "','R','" + user + "'," + TrsNo + "," + recnum + ",refno,PayableAt,RemType from bill_ibcbooking where substring(acno,1,2)='" + BRCODE + "' and  Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "'");
                    var5 = insertQ.executeUpdate();
                    Query updateQuery = em.createNativeQuery("Delete bill_ibcbooking From bill_ibcbooking bill inner join accountmaster am on bill.acno = am.acno and  Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "' and am.curBrCode = '" + BRCODE + "'");
                    var6 = updateQuery.executeUpdate();

                }

            }
            if ((var4 > 0) && (var5 > 0) && (var6 > 0)) {
                ut.commit();
                //ut.rollback();
                return "Instruction Pass Successfully";
            } else {
                ut.rollback();
                return "Problem in Instruction Pass";
            }

        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new EJBException("Rollback failed: " + syex.getMessage());
            }
            throw new EJBException("Transaction failed: " + e.getMessage());
        }
    }

    public String fieldCheck(String billType, Float billNo, Integer fYear, String panelReason, double ourCharges, double amtDebited, double instAmt, double retCharges, String resonCancel) {

        String CFlag = "False";
        String a = "False";
        if ((billType == null) || (billType.trim().equalsIgnoreCase(""))) {
            CFlag = "True";
            //ut.rollback();
            return "Bill Type Should Not Be Blank" + ": " + CFlag;
        }
        if (billType.equals("--SELECT--")) {
            CFlag = "True";
            //ut.rollback();
            return "Bill Type Should Not Be Blank" + ": " + CFlag;
        }
        if (billNo < 0) {
            CFlag = "True";
            //ut.rollback();
            return "Please Enter Valid Bill No." + ": " + CFlag;
        }
        if (fYear < 0) {
            CFlag = "True";
            //ut.rollback();
            return "Year Should Not Be Blank" + ": " + CFlag;
        }
        if (panelReason.equals("True")) {
            if (ourCharges < 0) {
                CFlag = "True";
                //ut.rollback();
                return "Please Enter Valid Our Charges" + ": " + CFlag;
            }
            if ((amtDebited < 0) || (amtDebited > (instAmt - ourCharges))) {
                CFlag = "True";
                //ut.rollback();
                return "Please Enter Valid Amount" + ": " + CFlag;
            }
        } else {
            if (retCharges < 0) {
                CFlag = "True";
                //ut.rollback();
                return "Please Enter Valid Return Charges" + ": " + CFlag;
            }
            if ((resonCancel == null) || (resonCancel.trim().equalsIgnoreCase(""))) {
                CFlag = "True";
                //ut.rollback();
                return "Please Select Reason For Cancel" + ": " + CFlag;
            }
        }
        return "False";
    }

    public String fieldDisplay(float billNo, Integer fYear, String billType, String BRCODE) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List custName;
            String custNames = null;
            String instNos = null;
            float instAmount = 0;
            String instDts = null;
            String accNo;
            float comm = 0;
            float postage = 0;
            String alphaCodes = null;
            String panelAlpha = null;
            String lblHo = null;
            String number = null;
            if (billType.equals("--SELECT--")) {
                ut.rollback();
                return "Please Select bill Type";
            }
            if (billNo < 0) {
                ut.rollback();
                return "Please Select big Bill Number";
            }
            List selectAlpha = em.createNativeQuery("Select bill.alphacode,bill.instno,bill.InstAmount,bill.InstDt,bill.Acno,bill.Comm,bill.Postage,bill.RemType From bill_ibcbooking bill, accountmaster am Where bill.acno = am.acno and  bill.BillNo =" + billNo + " And bill.FYear=" + fYear + " and bill.billtype='" + billType + "' and am.curBrCode = '" + BRCODE + "'").getResultList();
            if (!selectAlpha.isEmpty()) {
                Vector selectAlphas = (Vector) selectAlpha.get(0);
                accNo = selectAlphas.get(4).toString();
                String accNature = fTSPosting43CBSBean.getAccountNature(accNo);
                if ((accNature.equals(CbsConstant.FIXED_AC)) || (accNature.equals(CbsConstant.MS_AC))) {
                    custName = em.createNativeQuery("Select CustName from td_accountmaster Where curBrCode='" + BRCODE + "' and acno='" + accNo + "'").getResultList();
                    Vector custumerName = (Vector) custName.get(0);
                    custNames = custumerName.get(0).toString();
                } else if (accNature.equals(CbsConstant.PAY_ORDER)) {
                    custName = em.createNativeQuery("Select AcName from gltable Where substring(acno,1,2)='" + BRCODE + "' and acno='" + accNo + "'").getResultList();
                } else {
                    custName = em.createNativeQuery("Select CustName from accountmaster Where curBrCode='" + BRCODE + "' and acno='" + accNo + "'").getResultList();
                    Vector custumerName = (Vector) custName.get(0);
                    custNames = custumerName.get(0).toString();

                }
                if (!custName.isEmpty()) {
                    String alphaCode = selectAlphas.get(0).toString();
                    instNos = selectAlphas.get(1).toString();
                    String instAmt = selectAlphas.get(2).toString();
                    instAmount = Float.parseFloat(instAmt);
                    String instD = selectAlphas.get(3).toString();
                    String yy = instD.substring(0, 4);
                    String mm = instD.substring(5, 7);
                    String dd = instD.substring(8, 10);
                    instDts = dd + "/" + mm + "/" + yy;
                    String co = selectAlphas.get(5).toString();
                    comm = Float.parseFloat(co);
                    String ptg = selectAlphas.get(6).toString();
                    postage = Float.parseFloat(ptg);
                    String remType = selectAlphas.get(7).toString();
                    if (alphaCode.equals("0")) {
                        panelAlpha = "True";
                        if (remType.equals("PO")) {
                            List alpha = em.createNativeQuery("select alphacode from branchmaster where brncode= cast ('" + BRCODE + "' as unsigned)").getResultList();
                            if (!alpha.isEmpty()) {
                                Vector alphas = (Vector) alpha.get(0);
                                alphaCodes = alphas.get(0).toString();
                            }
                        }
                    } else {
                        panelAlpha = "False";
                        lblHo = "Head Office";
                        List acNumber = em.createNativeQuery("select AcNo from gltable where substring(acno,1,2)='" + BRCODE + "' and AcName='HEAD OFFICE'").getResultList();
                        Vector acNumbers = (Vector) acNumber.get(0);
                        number = acNumbers.get(0).toString();
                    }
                }
            } else {
                ut.rollback();
                return "Bill No. Does Not Exist";
            }
            ut.commit();
            return instNos + ": " + instAmount + ": " + instDts + ": " + accNo + ": " + custNames + ": " + comm + ": " + postage + ": " + alphaCodes + ": " + panelAlpha + ": " + lblHo + ": " + number;
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new EJBException("Rollback failed: " + syex.getMessage());
            }
            throw new EJBException("Transaction failed: " + e.getMessage());
        }
    }

    public String findTax(Float commAmt) throws ApplicationException {
        List result = new ArrayList();
        String currentDate = "";
        Integer roundNo = 0;
        String appType = "";
        Float appRot;
        String appOn = "";
        String glAcct = "";
        String message = "true";
        Float ttlTaxAmt = null;
        try {
            List resultDate = em.createNativeQuery("SELECT date_format(curdate(),'%Y%m%d')").getResultList();
            if (resultDate.size() <= 0) {
            } else {
                Vector resultVect = (Vector) resultDate.get(0);
                currentDate = resultVect.get(0).toString();
            }
            List resultTaxMaster = em.createNativeQuery("SELECT RoundUpto FROM taxmaster WHERE ROTApplyOn='C' AND applicableFlag='Y' AND auth='Y' AND applicableDt <= date_format(curdate(),'%Y%m%d') limit 1").getResultList();
            if (resultTaxMaster.size() <= 0) {
                message = "TAX NOT DEFINED!!!";
            } else {
                List resultTax = em.createNativeQuery("SELECT RoundUpto FROM taxmaster WHERE ROTApplyOn='C' AND applicableFlag='Y' AND auth='Y' AND applicableDt <= date_format(curdate(),'%Y%m%d') limit 1").getResultList();
                if (resultTax.size() <= 0) {
                } else {
                    Vector resultVect = (Vector) resultTax.get(0);
                    roundNo = Integer.parseInt(resultVect.get(0).toString());
                }
            }
            List taxApplicableROTList = fnTaxApplicableROT(currentDate);
            if (taxApplicableROTList.size() <= 0) {
                message = "Rate Of Tax not Found!!!";
            } else {
                Vector resultVect = (Vector) taxApplicableROTList.get(0);
                appType = resultVect.get(0).toString();
                appRot = Float.parseFloat(resultVect.get(1).toString());
                appOn = resultVect.get(2).toString();
                glAcct = resultVect.get(3).toString();
            }
            String taxamount = taxAmount(commAmt, appType);
            List ttlTax = em.createNativeQuery("SELECT ROUND(" + taxamount + "," + roundNo + ")").getResultList();
            if (ttlTax.size() <= 0) {
            } else {
                Vector resultVect = (Vector) ttlTax.get(0);
                ttlTaxAmt = Float.parseFloat(resultVect.get(0).toString());
            }
            result.add(ttlTaxAmt);
            return result.toString();
        } catch (Exception ex) {
        }
        return result.toString();

    }

    public List fnTaxApplicableROT(String appDT) throws ApplicationException {
        List resultList = null;
        try {
            resultList = em.createNativeQuery("select TYPE,ROT,ROTApplyOn,glhead from taxmaster where ApplicableDt<='" + appDT + "' and applicableFlag='Y' and Auth='Y'").getResultList();
            if (resultList.size() <= 0) {
            }
            return resultList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String taxAmount(Float amt, String type) throws ApplicationException {
        Float minAmt;
        Float maxAmt;
        Float balance = null;
        try {
            List resultlist = em.createNativeQuery("select minAmt,maxamt from taxmaster where type='" + type + "' and"
                    + " ApplicableFlag='Y' and ApplicableDt in (select max(ApplicableDt) from taxmaster where type='" + type + "' "
                    + "and ApplicableFlag='Y')").getResultList();
            if (resultlist.size() <= 0) {
                return "No Data in TaxMaster";
            } else {
                Vector resultVect = (Vector) resultlist.get(0);
                minAmt = Float.parseFloat(resultVect.get(0).toString());
                maxAmt = Float.parseFloat(resultVect.get(1).toString());
            }
            List resultlistTaxMaster = em.createNativeQuery("Select ('" + amt + "'* ROT)/100  from taxmaster where type='" + type + "' and "
                    + "ApplicableFlag='Y' and ApplicableDt in (select max(ApplicableDt) from taxmaster where type='" + type + "' and "
                    + "ApplicableFlag='Y')").getResultList();
            if (resultlistTaxMaster.size() <= 0) {
                return "No Data in TaxMaster";
            } else {
                Vector resultVect = (Vector) resultlistTaxMaster.get(0);
                balance = Float.parseFloat(resultVect.get(0).toString());
            }

            if (balance < minAmt) {
                balance = minAmt;
            } else if (balance > maxAmt) {
                balance = maxAmt;
            }
            return balance.toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
}
