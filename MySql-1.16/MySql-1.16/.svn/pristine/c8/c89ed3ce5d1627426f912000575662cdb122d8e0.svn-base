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
@Stateless(mappedName = "IbcObcFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class IbcObcFacade implements IbcObcFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsMethods;
    /*
     * Satrt the code IBCOBCEnquirySessionBean
     * 
     */

    public String billEnquiry(String bill, String billType, Float billNo, Integer year, String brCode) throws ApplicationException {
        try {
            List checkList;
            String secLisACNO = "";
            String acctcode = "";
            String CustName = "";
            String InstNo = "";
            String InstAmount = "";
            String InstDt = "";
            String Comm = "";
            String Postage = "";
            String status = "";
            String Dt = "";
            String obcFlag = "";
            String LblStatus = "";
            if ((bill == null) || (bill.equalsIgnoreCase(""))) {

                return "Bills Should Not Be Blank !!!.";
            }
            if ((billType == null) || (billType.equalsIgnoreCase(""))) {

                return "Bill Type Should Not Be Blank !!!.";
            }
            if ((billNo.isNaN() == true) || (year == null) || (billNo == null)) {

                return "Year OR Bill No Should Not Be Blank. And Bill No Should be Numeric.";
            }

            if (bill.equalsIgnoreCase("IBC")) {


                checkList = em.createNativeQuery("Select bill.Acno,bill.InstNo,bill.InstAmount,date_format(bill.InstDt,'%d/%m/%Y'),"
                        + "bill.Comm,bill.Postage,bill.status,date_format(bill.Dt,'%d/%m/%Y'),null obcFlag from bill_ibcbooking bill,"
                        + "accountmaster am where bill.billType='" + billType + "' and bill.BillNo=" + billNo + " and bill.FYear=" + year + " and CurBrCode='" + brCode + "' and  "
                        + "bill.acno = am.acno "
                        + "Union "
                        + "Select bill.Acno,bill.InstNo,bill.InstAmount,date_format(bill.InstDt,'%d/%m/%Y'),bill.Comm,bill.Postage,"
                        + "bill.status,date_format(bill.Dt,'%d/%m/%Y'),null obcFlag from bill_ibcprocessed bill,accountmaster am "
                        + "where bill.billType='" + billType + "' and bill.BillNo=" + billNo + " and bill.FYear=" + year + " and CurBrCode='" + brCode + "' and  bill.acno = am.acno "
                        + "Union "
                        + "Select bill.Acno,bill.InstNo,bill.InstAmount,date_format(bill.InstDt,'%d/%m/%Y'),bill.Comm,bill.Postage,"
                        + "bill.status,date_format(bill.Dt,'%d/%m/%Y'),null obcFlag from bill_ibcprocessedold bill,accountmaster am "
                        + "where bill.billType='" + billType + "' and bill.BillNo=" + billNo + " and bill.FYear=" + year + " and CurBrCode='" + brCode + "' and  bill.acno = am.acno").getResultList();


                if (checkList.isEmpty()) {

                    return "Bill No. " + billNo + "/" + year + " Does Not Exist For " + billType + " Type";
                }
                Vector secLst = (Vector) checkList.get(0);
                secLisACNO = secLst.get(0).toString();
                // acctcode = secLisACNO.substring(2, 4);
                acctcode = ftsMethods.getAccountCode(secLisACNO);
                InstNo = secLst.get(1).toString();
                InstAmount = secLst.get(2).toString();
                InstDt = secLst.get(3).toString();
                Comm = secLst.get(4).toString();
                Postage = secLst.get(5).toString();
                status = secLst.get(6).toString();
                Dt = secLst.get(7).toString();
                if (status.equalsIgnoreCase("E")) {
                    LblStatus = "Booked On " + Dt;
                } else if (status.equalsIgnoreCase("R")) {
                    LblStatus = "Realized On " + Dt;
                } else if (status.equalsIgnoreCase("D")) {
                    LblStatus = "Dishonor On " + Dt;
                } else if (obcFlag.equalsIgnoreCase("Pending")) {
                    LblStatus = "Pending On " + Dt;
                } else if (obcFlag.equalsIgnoreCase("Cleared")) {
                    LblStatus = "Cleared On " + Dt;
                } else if (obcFlag.equalsIgnoreCase("Returned")) {
                    LblStatus = "Returned On " + Dt;
                }

                String acctNat = ftsMethods.getAccountNature(secLisACNO);
                if (acctNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || (acctNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    List checkList2 = em.createNativeQuery("Select CustName from td_accountmaster where acno='" + secLisACNO + "'").getResultList();
                    if (checkList2.isEmpty()) {
                        return "Customer Name does not exists !!!.";
                    }
                    Vector secLst2 = (Vector) checkList2.get(0);
                    CustName = secLst2.get(0).toString();
                } else if (acctNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    List checkList2 = em.createNativeQuery("Select AcName from gltable where acno='" + secLisACNO + "'").getResultList();
                    if (checkList2.isEmpty()) {
                        return "Customer Name does not exists !!!.";
                    }
                    Vector secLst2 = (Vector) checkList2.get(0);
                    CustName = secLst2.get(0).toString();
                } else {
                    List checkList2 = em.createNativeQuery("Select CustName from accountmaster where acno='" + secLisACNO + "'").getResultList();
                    if (checkList2.isEmpty()) {
                        return "Customer Name does not exists !!!.";
                    }
                    Vector secLst2 = (Vector) checkList2.get(0);
                    CustName = secLst2.get(0).toString();
                }
            } else if (bill.equalsIgnoreCase("OBC")) {
                checkList = em.createNativeQuery("Select bill.Acno,bill.InstNo,bill.InstAmount,date_format(bill.InstDt,'%d/%m/%Y'),bill.Comm,bill.Postage,bill.status,date_format(bill.Dt,'%d/%m/%Y'),null obcFlag from bill_obcbooking bill,accountmaster am  where billType='" + billType + "' and bill.BillNo=" + billNo + " and bill.FYear=" + year + " and CurBrCode='" + brCode + "' and  bill.acno = am.acno "
                        + "Union "
                        + "Select bill.Acno,bill.InstNo,bill.InstAmount,date_format(bill.InstDt,'%d/%m/%Y'),bill.Comm,bill.Postage,bill.status,date_format(bill.Dt,'%d/%m/%Y'),bill.obcFlag from bill_obcprocessed bill,accountmaster am where bill.billType='" + billType + "' and bill.BillNo=" + billNo + " and bill.FYear=" + year + " and CurBrCode='" + brCode + "' and  bill.acno = am.acno "
                        + "Union "
                        + "Select bill.Acno,bill.InstNo,bill.InstAmount,date_format(bill.InstDt,'%d/%m/%Y'),bill.Comm,bill.Postage,bill.status,date_format(bill.Dt,'%d/%m/%Y'),bill.obcFlag from bill_obcprocessedold bill,accountmaster am where bill.billType='" + billType + "' and bill.BillNo=" + billNo + " and bill.FYear=" + year + " and CurBrCode='" + brCode + "' and  bill.acno = am.acno").getResultList();
                if (checkList.isEmpty()) {
                    return "Bill No. " + billNo + "/" + year + " Does Not Exist For " + billType + " Type";
                }
                Vector secLst = (Vector) checkList.get(0);
                secLisACNO = secLst.get(0).toString();
                acctcode = ftsMethods.getAccountCode(secLisACNO);
                InstNo = secLst.get(1).toString();
                InstAmount = secLst.get(2).toString();
                InstDt = secLst.get(3).toString();
                Comm = secLst.get(4).toString();
                Postage = secLst.get(5).toString();
                status = secLst.get(6).toString();
                Dt = secLst.get(7).toString();
                if (status.equalsIgnoreCase("E")) {
                    LblStatus = "Booked On " + Dt;
                } else if (status.equalsIgnoreCase("R")) {
                    LblStatus = "Realized On " + Dt;
                } else if (status.equalsIgnoreCase("D")) {
                    LblStatus = "Dishonor On " + Dt;
                } else if (obcFlag.equalsIgnoreCase("Pending")) {
                    LblStatus = "Pending On " + Dt;
                } else if (obcFlag.equalsIgnoreCase("Cleared")) {
                    LblStatus = "Cleared On " + Dt;
                } else if (obcFlag.equalsIgnoreCase("Returned")) {
                    LblStatus = "Returned On " + Dt;
                }

                String acctNat = ftsMethods.getAccountNature(secLisACNO);
                if (acctNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || (acctNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    List checkList2 = em.createNativeQuery("Select CustName from td_accountmaster where acno='" + secLisACNO + "'").getResultList();
                    if (checkList2.isEmpty()) {
                        return "Customer Name does not exists !!!.";
                    }
                    Vector secLst2 = (Vector) checkList2.get(0);
                    CustName = secLst2.get(0).toString();
                } else if (acctNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    List checkList2 = em.createNativeQuery("Select AcName from gltable where acno='" + secLisACNO + "'").getResultList();
                    if (checkList2.isEmpty()) {
                        return "Customer Name does not exists !!!.";
                    }
                    Vector secLst2 = (Vector) checkList2.get(0);
                    CustName = secLst2.get(0).toString();
                } else {
                    List checkList2 = em.createNativeQuery("Select CustName from accountmaster where acno='" + secLisACNO + "'").getResultList();
                    if (checkList2.isEmpty()) {
                        return "Customer Name does not exists !!!.";
                    }
                    Vector secLst2 = (Vector) checkList2.get(0);
                    CustName = secLst2.get(0).toString();
                }
            }
            return secLisACNO + ":" + CustName + ":" + InstAmount + ":" + InstNo + ":" + InstDt + ":" + Comm + ":" + Postage + ":" + LblStatus + "";

        } catch (Exception e) {

            throw new ApplicationException(e);
        }
    }

    public List FYear(String brCode, String billType) throws ApplicationException {
        List checkList = new ArrayList();
        try {
            if (billType.equalsIgnoreCase("OBC")) {
                checkList = em.createNativeQuery("Select Distinct FYear From bill_obcbooking where substring(acno,1,2)='" + brCode + "' and FYear is not null").getResultList();
            } else if (billType.equalsIgnoreCase("IBC")) {
                checkList = em.createNativeQuery("Select Distinct FYear From bill_ibcbooking where substring(acno,1,2)='" + brCode + "' and FYear is not null").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return checkList;
    }

    /**
     *
     * end the code IBCOBCRealInwardChqSessionBean
     *
     */
    public List RealInwardChqOnLoad(String brCode) throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("Select BillNo,Fyear,acno,BillType,InstNo,InstAmount,date_format(InstDt,'%d/%m/%Y'),BankName,bankAddr,EnterBy From bill_ibcbooking where billtype='BC' and substring(acno,1,2)='" + brCode + "' and billno is not null order by billType,billno,fyear").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List chkInstrument(String acno, String instNo) throws ApplicationException {
        List resList1 = new ArrayList();
        try {
            String acNat = ftsMethods.getAccountNature(acno);
            if ((acNat.equalsIgnoreCase("CA")) || (acNat.equalsIgnoreCase("SB"))) {
                resList1 = em.createNativeQuery("select statusflag,acno,chqno,issuedt,authby from chbook_" + acNat + "  where acno = '" + acno + "' and Chqno = " + instNo + "").getResultList();
            }
            return resList1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String RealOutBillFyearProcess(String BRCODE, String billType, Float billNo, Integer year) throws ApplicationException {
        try {
            List checkList;
            String secLisACNO = "";
            String acctcode = "";
            String CustName = "";
            String InstNo = "";
            String InstAmount = "";
            String InstDt = "";
            String Comm = "";
            String Postage = "";
            String Dt = "";
            String alpha = "";
            String remType = "";
            String Expired = "";
            String GLHO = " ";
            if ((billType == null) || (billType.equalsIgnoreCase(""))) {
                return "Bill Type Should Not Be Blank !!!.";
            }
            if ((billNo.isNaN() == true) || (year == null) || (billNo == null)) {
                return "Year OR Bill No Should Not Be Blank. And Bill No Should be Numeric.";
            }
            checkList = em.createNativeQuery("Select Acno,InstNo,InstAmount,date_format(InstDt,'%d/%m/%Y'),Comm,Postage,alphacode,RemType From bill_ibcbooking Where BillNo = " + billNo + " And FYear= " + year + " and billtype='" + billType + "' and substring(acno,1,2)='" + BRCODE + "'").getResultList();
            if (checkList.isEmpty()) {
                return "Bill No. Does Not Exist !!!.";
            }
            Vector secLst = (Vector) checkList.get(0);
            secLisACNO = secLst.get(0).toString();
            acctcode = secLisACNO.substring(2, 4);
            InstNo = secLst.get(1).toString();
            InstAmount = secLst.get(2).toString();
            InstDt = secLst.get(3).toString();
            Comm = secLst.get(4).toString();
            Postage = secLst.get(5).toString();
            alpha = secLst.get(6).toString();
            remType = secLst.get(7).toString();

            String acctNat = ftsMethods.getAccountNature(secLisACNO);
            if (acctNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || (acctNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                List checkList2 = em.createNativeQuery("Select CustName from td_accountmaster where acno='" + secLisACNO + "'").getResultList();
                if (checkList2.isEmpty()) {
                    return "Customer Name does not exists !!!.";
                }
                Vector secLst2 = (Vector) checkList2.get(0);
                CustName = secLst2.get(0).toString();
            } else if (acctNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List checkList2 = em.createNativeQuery("Select AcName from gltable where acno='" + secLisACNO + "'").getResultList();
                if (checkList2.isEmpty()) {
                    return "Customer Name does not exists !!!.";
                }
                Vector secLst2 = (Vector) checkList2.get(0);
                CustName = secLst2.get(0).toString();
            } else {
                List checkList2 = em.createNativeQuery("Select CustName from accountmaster where acno='" + secLisACNO + "'").getResultList();
                if (checkList2.isEmpty()) {
                    return "Customer Name does not exists !!!.";
                }
                Vector secLst2 = (Vector) checkList2.get(0);
                CustName = secLst2.get(0).toString();
            }
            if (alpha.equalsIgnoreCase("0")) {
            } else {
//                checkList = em.createNativeQuery("select name from sysobjects where name='Bill_IBCOBCGLMast' and xtype='u'").getResultList();
//                if (checkList.isEmpty()) {
//                    GLHO = BRCODE + "GL00070001" + "                     Head Office";
//                } 
                checkList = em.createNativeQuery("select GLHO, GLBPIntRec, GLBCComm, GLBillPay, GLDraftPay, GLCASEPAY, GLHOPAY, GLBillCol, GLBillLodg,GLChequeRet, GLSundry, BnkGuarCr, BnkGuarDr, GLService, GLLcComm, GLBgComm from bill_ibcobcglmast").getResultList();
                if (checkList.isEmpty()) {
                    GLHO = BRCODE + "GL00070001" + "                 Head Office";
                } else {
                    Vector secLst2 = (Vector) checkList.get(0);
                    GLHO = secLst2.get(0).toString() + "           Head Office";
                }
            }
            return "" + secLisACNO + ":" + CustName + ":" + InstAmount + ":" + InstNo + ":" + InstDt + ":" + Comm + ":" + Postage + ":" + GLHO + "";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List RealOutBillFormLoad(String brCode) throws ApplicationException {
        try {
            List checkList1 = em.createNativeQuery("Select Distinct FYear From bill_ibcbooking where FYear is not null  and substring(acno,1,2)='" + brCode + "'").getResultList();
            return checkList1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List RealOutBillFormLoad1() throws ApplicationException {
        try {
            List checkList1 = em.createNativeQuery("Select Distinct Description From codebook Where GroupCode=13 and code<>0").getResultList();
            return checkList1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List RealOutBillFormLoad2() throws ApplicationException {
        try {
            List checkList1 = em.createNativeQuery("Select Distinct AlphaCode From branchmaster where BranchName is not null").getResultList();
            return checkList1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String RealOutBillDishonor(String BRCODE, String enterby, String billType, Float billNo, Integer year, String acno, String instno,
            double instamount, String instdt, double RetCharges, String Reason) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List checkList;
            String tmpAlpha;
            String CHK_MSG = null;
            String MSG = null;
            float TOKENNO = 0.0f;
            String tempbd = "";
            int exec = 0;
            int exec1 = 0;
            int exec2 = 0;
            int exec3 = 0;
            int exec4 = 0;
            int exec5 = 0;
            List bank = em.createNativeQuery("select date from bankdays WHERE dayendflag = 'N' and brncode='" + BRCODE + "'").getResultList();
            if (bank.isEmpty()) {
                ut.rollback();
                return "Date Does Not Exists !!!.";
            }
            Vector bankLst2 = (Vector) bank.get(0);
            tempbd = bankLst2.get(0).toString();
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
//                GLChequeRet = secLst1.get(9).toString();
//            }
////            }

            if ((instno == null) || (instdt == null) || (instno.equalsIgnoreCase("")) || (instdt.equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Check Instruement details";
            }
            List checkList1 = em.createNativeQuery("Select InstNo,InstAmount,InstDt,Acno,Comm,Postage From bill_ibcbooking Where BillNo = " + billNo + " and substring(ACNO,1,2)='" + BRCODE + "' And FYear=" + year + " and billtype='" + billType + "'").getResultList();
            if (checkList1.isEmpty()) {
                ut.rollback();
                return "Please Check Instruement details !!!.";
            }
            /**
             * ********** Call FieldCheck ******************
             */
            if (billType.equalsIgnoreCase("")) {
                ut.rollback();
                return "Bill Type Should Not Be Blank";
            }
            if ((billNo.isNaN() == true) || (billNo < 0)) {
                ut.rollback();
                return "Please Enter Valid Bill No.!!";
            }
            if (year.equals("")) {
                ut.rollback();
                return "Year Should Not Be Blank.!!";
            }
            if ((RetCharges < 0)) {
                ut.rollback();
                return "Please Enter Valid Charges.";
            }
            if (Reason.equalsIgnoreCase("")) {
                ut.rollback();
                return "Reason For Cancel Should Not Be Blank";
            }
            /**
             * ********** Call FieldCheck ENDS ******************
             */
            float dtrSNo = ftsMethods.getTrsNo();

            List checkList2 = em.createNativeQuery("Select AlphaCode From bill_ibcbooking where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "' and substring(ACNO,1,2)='" + BRCODE + "'").getResultList();
            Vector AlphaCodeLst = (Vector) checkList2.get(0);
            if (checkList2.isEmpty()) {
                tmpAlpha = "0";
            } else {
                tmpAlpha = AlphaCodeLst.get(0).toString();
            }

            String acNat = ftsMethods.getAccountCode(acno);
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List checkList3 = em.createNativeQuery("Select Balance From td_reconbalan where acno='" + acno + "' and balance>=" + RetCharges + "").getResultList();
                if (checkList3.isEmpty()) {
                    ut.rollback();
                    return "Insufficient Fund in Acno : " + acno;
                }
            } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List checkList3 = em.createNativeQuery("Select Balance From ca_reconbalan where acno='" + acno + "' and balance>=" + RetCharges + "").getResultList();
                if (checkList3.isEmpty()) {
                    ut.rollback();
                    return "Insufficient Fund in Acno : " + acno;
                }
            } else {
                List checkList3 = em.createNativeQuery("Select Balance From reconbalan where acno='" + acno + "' and balance>=" + RetCharges + "").getResultList();
                if (checkList3.isEmpty()) {
                    ut.rollback();
                    return "Insufficient Fund in Acno : " + acno;
                }
            }

            String Detail = "VCH : To BCNO: " + billNo + "/" + year + " For Acno " + acno + " Realized";

            String FTSMsg = "";
            FTSMsg = ftsMethods.ftsPosting43CBS(GLBillCol, 2, 1, instamount, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "N", null, "A", 3, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
            if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                ut.rollback();
                return FTSMsg;
            }

            Detail = "VCH : To BCNO: " + billNo + "/" + year + " For Acno " + acno + " Realized";
            FTSMsg = ftsMethods.ftsPosting43CBS(GLBillLodg, 2, 0, instamount, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "N", null, "A", 3, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
            if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                ut.rollback();
                return FTSMsg;
            }

            if (RetCharges > 0) {
                Detail = "VCH : Charges For BCNo: " + billNo + "/" + year + "";

                FTSMsg = ftsMethods.ftsPosting43CBS(acno, 2, 1, RetCharges, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "N", null, "A", 3, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    ut.rollback();
                    return FTSMsg;
                }
                Detail = "VCH : Charges For BCNo: " + billNo + "/" + year + " For Acno: " + acno;
                FTSMsg = ftsMethods.ftsPosting43CBS(GLChequeRet, 2, 0, RetCharges, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "N", null, "A", 3, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    ut.rollback();
                    return FTSMsg;
                }
            }

            float recnum = ftsMethods.getRecNo();
            Query insertQuery = em.createNativeQuery("Insert bill_ibcprocessed(billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage,RetCharges,ReasonforCancel,InFavourOf,InstDt,InstEntryDt,Dt,status,EnterBy,TrsNo,RecNo,refno) Select billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage," + RetCharges + ",'" + Reason + "',InFavourOf,InstDt,Dt,'" + tempbd + "','D','" + enterby + "'," + dtrSNo + "," + recnum + ",refno from bill_ibcbooking where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "'");
            exec4 = insertQuery.executeUpdate();
            Query delQuery = em.createNativeQuery("Delete From bill_ibcbooking Where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "' and substring(acno,1,2)='" + BRCODE + "'");
            exec5 = delQuery.executeUpdate();
            if (RetCharges > 0) {
                if ((exec4 > 0) && (exec5 > 0)) {
                    ut.commit();
                    return "Dishonoured Successfully  " + dtrSNo;
                } else {
                    ut.rollback();
                    return "Problem In Charges Posting";
                }
            } else {
                if ((exec4 > 0) && (exec5 > 0)) {
                    ut.commit();
                    return "Dishonoured Successfully  " + dtrSNo;
                } else {
                    ut.rollback();
                    return "Problem In Charges Posting";
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
    }

    public String RealInwardChqPass(String BRCODE, String enterby, String billType, Float billNo, Integer year, String acno,
            String instno, double instamount, String instdt, double OtherCharges, double AmtDebited, String CustName, Integer VarPayBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List checkList;
            List checkList22;
            String tmpAlpha;
            String RefNo = "";

            String tempbd = "";

            int exec5 = 0;
            int exec6 = 0;
            int exec7 = 0;

            List bank = em.createNativeQuery("select date from bankdays WHERE dayendflag = 'N' and brncode='" + BRCODE + "'").getResultList();
            if (bank.isEmpty()) {
                ut.rollback();
                return "Date Does Not Exists !!!.";
            }
            Vector bankLst2 = (Vector) bank.get(0);
            tempbd = bankLst2.get(0).toString();


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

            checkList = em.createNativeQuery("select name from sysobjects where name='Bill_IBCOBCGLMast' and xtype='u'").getResultList();
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
            checkList22 = em.createNativeQuery("select GLHO, GLBPIntRec, GLBCComm, GLBillPay, GLDraftPay, GLCASEPAY, GLHOPAY, GLBillCol, GLBillLodg,GLChequeRet, GLSundry, BnkGuarCr, BnkGuarDr, GLService, GLLcComm, GLBgComm from Bill_IBCOBCGLMast").getResultList();
//            if (checkList22.isEmpty()) {
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
//                Vector secLst1 = (Vector) checkList22.get(0);
//                GLBPIntRec = secLst1.get(1).toString();
//
//                GLBillPay = secLst1.get(3).toString();
//
//                GLBillCol = secLst1.get(7).toString();
//
//                GLBillLodg = secLst1.get(8).toString();
//
//                GLChequeRet = secLst1.get(9).toString();
//
//            }
//            }

            if ((instno == null) || (instdt == null) || (instno.equalsIgnoreCase("")) || (instdt.equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Check Instruement details";
            }
            List checkList1 = em.createNativeQuery("Select InstNo,InstAmount,InstDt,Acno,Comm,Postage From bill_ibcbooking Where BillNo = " + billNo + " And FYear=" + year + " and billtype='" + billType + "' and substring(ACNO,1,2)='" + BRCODE + "'").getResultList();
            if (checkList1.isEmpty()) {
                ut.rollback();
                return "Please Check Instruement details !!!.";
            }

            /**
             * ********** Call FieldCheck ******************
             */
            if (billType.equalsIgnoreCase("")) {
                ut.rollback();
                return "Bill Type Should Not Be Blank";
            }
            if ((billNo.isNaN() == true) || (billNo < 0)) {
                ut.rollback();
                return "Please Enter Valid Bill No.!!";
            }
            if (year.equals("")) {
                ut.rollback();
                return "Year Should Not Be Blank.!!";
            }
            if (OtherCharges < 0) {
                ut.rollback();
                return "Please Enter Valid Charges";
            }
            if ((AmtDebited < 0) || ((instamount - OtherCharges) < AmtDebited)) {
                ut.rollback();
                return "Please Enter Valid Amount";
            }
            String acNat = ftsMethods.getAccountNature(acno);
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List checkList3 = em.createNativeQuery("Select Balance From td_reconbalan where acno='" + acno + "' and balance>=" + AmtDebited + "").getResultList();
                if (checkList3.isEmpty()) {
                    ut.rollback();
                    return "Insufficient Fund in Acno : " + acno;
                }
            } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List checkList3 = em.createNativeQuery("Select Balance From ca_reconbalan where acno='" + acno + "' and balance>=" + AmtDebited + "").getResultList();
                if (checkList3.isEmpty()) {
                    ut.rollback();
                    return "Insufficient Fund in Acno : " + acno;
                }
            } else {
                List checkList3 = em.createNativeQuery("Select Balance From reconbalan where acno='" + acno + "' and balance>=" + AmtDebited + "").getResultList();
                if (checkList3.isEmpty()) {
                    ut.rollback();
                    return "Insufficient Fund in Acno : " + acno;
                }
            }

            List accStatus;
            Integer optStatus = null;
            Integer accSts;
            Float amounts;
            List balance;
            String a = null;
            Integer varPayBy;
            Integer tmpOptStatus = 1;
            String acNature = ftsMethods.getAccountNature(acno);
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || (acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                accStatus = em.createNativeQuery("select accstatus from td_accountmaster where acno = '" + acno + "'").getResultList();
                Vector acStatus = (Vector) accStatus.get(0);
                accSts = Integer.parseInt(acStatus.get(0).toString());
            } else {
                accStatus = em.createNativeQuery("select accstatus,coalesce(optstatus,'1') from accountmaster where acno ='" + acno + "'").getResultList();
                Vector acStatus = (Vector) accStatus.get(0);
                accSts = Integer.parseInt(acStatus.get(0).toString());
                String otStatus = acStatus.get(1).toString();
                optStatus = Integer.parseInt(otStatus);
            }
            if (!accStatus.isEmpty()) {
                if (!acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) && (!acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    tmpOptStatus = optStatus;
                }

                if ((accSts == 1) && (tmpOptStatus == 2)) {
                    ut.rollback();
                    return "Account Is Inoperative And Kindly Dishonor This Inst.";
                } else if ((accSts != 1) && (accSts != 10)) {

                    List discription = em.createNativeQuery("select description from codebook where groupcode = 3 and code =" + accSts + " ").getResultList();
                    if (!discription.isEmpty()) {
                        ut.rollback();
                        Vector discriptionVec = (Vector) discription.get(0);
                        String temp = discriptionVec.get(0).toString();
                        return "Account is Marked As " + temp + " And Kindly Dishonor This Inst.";
                    }
                } else if (accSts == 10) {
                    List amount = em.createNativeQuery("select (ifnull(amount,0)) AS amount from accountstatus where acno ='" + acno + "' and spno = (select max(spno) from accountstatus a where a.acno ='" + acno + "' and  auth = 'Y')").getResultList();
                    Vector amt = (Vector) amount.get(0);
                    amounts = Float.parseFloat(amt.get(0).toString());
                    String ABC = "Account Has been Lien Marked for amount of Rs." + amounts;
                    if ((acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {
                        balance = em.createNativeQuery("select balance from reconbalan  where acno='" + acno + "'").getResultList();
                    } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || (acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                        balance = em.createNativeQuery("select balance from td_reconbalan where acno='" + acno + "'").getResultList();
                    } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        balance = em.createNativeQuery("select balance from ca_reconbalan where acno='" + acno + "'").getResultList();
                    } else {
                        balance = em.createNativeQuery("select balance from reconbalan where acno='" + acno + "'").getResultList();
                    }
                    Vector bl = (Vector) balance.get(0);
                    Float balances = Float.parseFloat(bl.get(0).toString());
                    if (balance.isEmpty()) {
                        if (amounts >= (balances - instamount)) {
                            ut.rollback();
                            return "Please Dishonor This Instrument " + ABC;
                        }
                    }
                }
            }

            /**
             * ************************* Accountvalidation Emds
             * *********************
             */
            float dtrSNo = ftsMethods.getTrsNo();

            List checkList2 = em.createNativeQuery("Select AlphaCode,RefNo From bill_ibcbooking where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "' and substring(acno,1,2)='" + BRCODE + "'").getResultList();
            Vector AlphaCodeLst = (Vector) checkList2.get(0);
            if (checkList2.isEmpty()) {
                tmpAlpha = "0";
            } else {
                tmpAlpha = AlphaCodeLst.get(0).toString();
                RefNo = AlphaCodeLst.get(1).toString();
            }

            String FTSMsg = "";
            if (tmpAlpha.equalsIgnoreCase("0")) {
            } else {
                if ((OtherCharges) > 0) {
                    String Detail = "VCH:Comm For:" + billType + "No:" + billNo + "/" + year + "For Acno:" + acno;

                    FTSMsg = ftsMethods.ftsPosting43CBS(GLBCComm, 2, 0, OtherCharges, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "N", null, "A", VarPayBy, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                    if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                        ut.rollback();
                        return FTSMsg;
                    }

                }
                String Detail = billType + " No: " + billNo + "/" + year + "  For Acno: " + acno;

                FTSMsg = ftsMethods.ftsPosting43CBS(GLHO, 2, 0, AmtDebited, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "N", null, "A", VarPayBy, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    ut.rollback();
                    return FTSMsg;
                }

                Detail = "VCH : BCNo: " + billNo + "/" + year;
                FTSMsg = ftsMethods.ftsPosting43CBS(acno, 2, 1, instamount, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "N", null, "A", VarPayBy, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    ut.rollback();
                    return FTSMsg;
                }

                if (billType.equalsIgnoreCase("BC")) {
                    Detail = "VCH : To BCNO: " + billNo + "/" + year + "  For Acno: " + acno + " Realized";
                    FTSMsg = ftsMethods.ftsPosting43CBS(GLBillCol, 2, 1, instamount, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "N", null, "A", VarPayBy, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                    if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                        ut.rollback();
                        return FTSMsg;
                    }

                    Detail = "VCH : For BCNO: " + billNo + "/" + year + "  For Acno: " + acno + " Realized";

                    FTSMsg = ftsMethods.ftsPosting43CBS(GLBillLodg, 2, 0, instamount, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "N", null, "A", VarPayBy, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                    if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                        ut.rollback();
                        return FTSMsg;
                    }

                }
            }
            float ecnum = ftsMethods.getRecNo();
            Query insertQuery = em.createNativeQuery("insert bill_hoothers (fyear,instno,acno,custname,infavourof,payableat,amount,enterby,Auth,AuthBy,dt,status,trantype, seqno,comm,billtype,ty,recno,origindt) select FYear,instno,Acno,'" + CustName + "',InFavourOf,Alphacode," + AmtDebited + ",'" + enterby + "','Y','" + enterby + "','" + tempbd + "','Issued', 2, '" + RefNo + "', " + OtherCharges + ",'" + billType + "',0," + ecnum + ",instdt from bill_ibcbooking where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "'");
            exec5 = insertQuery.executeUpdate();
            float recnum = ftsMethods.getRecNo();

            Query insertQuery2 = em.createNativeQuery("Insert bill_ibcprocessed(billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage,OtherCharges,CreditedAmount,InFavourOf,InstDt,instentrydt,dt,status,EnterBy,TrsNo,RecNo,Refno,PayableAt,RemType) select billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage," + OtherCharges + "," + AmtDebited + ",InFavourOf,InstDt,Dt,'" + tempbd + "','R','" + enterby + "'," + dtrSNo + "," + recnum + ",refno,PayableAt,RemType from bill_ibcbooking where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "'");
            exec6 = insertQuery2.executeUpdate();

            Query delQuery1 = em.createNativeQuery("Delete From bill_ibcbooking where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "' and substring(acno,1,2)='" + BRCODE + "'");
            exec7 = delQuery1.executeUpdate();

            if (OtherCharges > 0) {
                if ((exec5 > 0) && (exec6 > 0) && (exec7 > 0)) {
                    ut.commit();
                    return "Value Passed Generated Batch No.  " + dtrSNo;
                } else {
                    ut.rollback();
                    return "Problem in Posting";
                }
            } else {
                if ((exec5 > 0) && (exec6 > 0) && (exec7 > 0)) {
                    ut.commit();
                    return "Value Passed Generated Batch No.  " + dtrSNo;
                } else {
                    ut.rollback();
                    return "Problem in Posting";
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
    }

    public List RealOutBillOnLoad(String brCode) throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("Select BillNo,BillType,Fyear,acno,InstNo,InstAmount,date_format(InstDt,'%d/%m/%Y'),Coll_BankName,Coll_BankAddr,EnterBy From bill_obcbooking where billno is not null and billType in ('BP') and substring(acno,1,2)='" + brCode + "' order by billType,billno,fyear").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String RealOutBillFyearProcess(String billType, Float billNo, Integer year, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List checkList;
            String secLisACNO = "";
            String acctcode = "";
            String CustName = "";
            String InstNo = "";
            String InstAmount = "";
            String InstDt = "";
            String Comm = "";
            String Postage = "";
            String Dt = "";
            String Expired = "";
            if ((billType == null) || (billType.equalsIgnoreCase("")) || (billType.equalsIgnoreCase("--Select--"))) {
                ut.rollback();
                return "Bill Type Should Not Be Blank !!!.";
            }
            if ((billNo.isNaN() == true) || (year == null) || (billNo == null)) {
                ut.rollback();
                return "Year OR Bill No Should Not Be Blank. And Bill No Should be Numeric.";
            }
            checkList = em.createNativeQuery("Select Acno,InstNo,InstAmount,date_format(InstDt,'%d/%m/%Y'),Comm,Postage,billdetainDT From bill_obcbooking Where BillNo = " + billNo + " And FYear= " + year + " and billtype='" + billType + "' and substring(acno,1,2)='" + brCode + "'").getResultList();
            if (checkList.isEmpty()) {
                ut.rollback();
                return "Bill No. Does Not Exist !!!.";
            }
            Vector secLst = (Vector) checkList.get(0);
            secLisACNO = secLst.get(0).toString();
            acctcode = secLisACNO.substring(2, 4);
            InstNo = secLst.get(1).toString();
            InstAmount = secLst.get(2).toString();
            InstDt = secLst.get(3).toString();
            Comm = secLst.get(4).toString();
            Postage = secLst.get(5).toString();
            Dt = secLst.get(6).toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmdd");
            List checkLst = em.createNativeQuery("select date from bankdays WHERE dayendflag = 'N' and BRNCODE='" + brCode + "'").getResultList();
            if (checkLst.isEmpty()) {
                ut.rollback();
                return "Date Does Not Exists !!!.";
            }
            Vector secLt = (Vector) checkLst.get(0);
            String date = secLt.get(0).toString();
            Date convertedBillDetainDT = dateFormat.parse(Dt);
            Date convertedTempbd = dateFormat.parse(date);
            if (convertedBillDetainDT.after(convertedTempbd)) {
                Expired = "Detain Period for Bill has Expired";
            }

            String acctNat = ftsMethods.getAccountNature(secLisACNO);
            if (acctNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || (acctNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                List checkList2 = em.createNativeQuery("Select CustName from td_accountmaster where acno='" + secLisACNO + "'").getResultList();
                if (checkList2.isEmpty()) {
                    ut.rollback();
                    return "Customer Name does not exists !!!.";
                }
                Vector secLst2 = (Vector) checkList2.get(0);
                CustName = secLst2.get(0).toString();
            } else if (acctNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List checkList2 = em.createNativeQuery("Select AcName from gltable where acno='" + secLisACNO + "'").getResultList();
                if (checkList2.isEmpty()) {
                    ut.rollback();
                    return "Customer Name does not exists !!!.";
                }
                Vector secLst2 = (Vector) checkList2.get(0);
                CustName = secLst2.get(0).toString();
            } else {
                List checkList2 = em.createNativeQuery("Select CustName from accountmaster where acno='" + secLisACNO + "'").getResultList();
                if (checkList2.isEmpty()) {
                    ut.rollback();
                    return "Customer Name does not exists !!!.";
                }
                Vector secLst2 = (Vector) checkList2.get(0);
                CustName = secLst2.get(0).toString();
            }
            ut.commit();
            return "" + Expired + ":" + secLisACNO + ":" + CustName + ":" + InstAmount + ":" + InstNo + ":" + InstDt + ":" + Comm + ":" + Postage + "";
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

    public String RealOutBillDishonorOutBill(String BRCODE, String enterby, String billType, Float billNo, Integer year, String acno, String instno,
            double instamount, String instdt, double RetCharges, String Reason) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List checkList;
            String tmpAlpha;
            String CHK_MSG = null;
            String MSG = null;
            float TOKENNO = 0.0f;
            String tempbd = "";
            int exec = 0;
            int exec1 = 0;
            int exec2 = 0;
            int exec3 = 0;
            int exec4 = 0;
            List bank = em.createNativeQuery("select date from bankdays WHERE dayendflag = 'N' and brncode='" + BRCODE + "'").getResultList();
            if (bank.isEmpty()) {
                ut.rollback();
                return "Date Does Not Exists !!!.";
            }
            Vector bankLst2 = (Vector) bank.get(0);
            tempbd = bankLst2.get(0).toString();
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
//                GLChequeRet = secLst1.get(9).toString();
//            }
//            }
            if ((instno == null) || (instdt == null) || (instno.equalsIgnoreCase("")) || (instdt.equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Check Instruement details";
            }
            List checkList1 = em.createNativeQuery("Select InstNo,InstAmount,InstDt,Acno,Comm,Postage From bill_obcbooking Where BillNo = " + billNo + " And FYear=" + year + " and billtype='" + billType + "' and substring(acno,1,2)='" + BRCODE + "'").getResultList();
            if (checkList1.isEmpty()) {
                ut.rollback();
                return "Please Check Instruement details !!!.";
            }
            /**
             * ********** Call FieldCheck ******************
             */
            if (billType.equalsIgnoreCase("")) {
                ut.rollback();
                return "Bill Type Should Not Be Blank";
            }
            if ((billNo.isNaN() == true) || (billNo < 0)) {
                ut.rollback();
                return "Please Enter Valid Bill No.!!";
            }
            if (year.equals("")) {
                ut.rollback();
                return "Year Should Not Be Blank.!!";
            }
            if ((RetCharges < 0)) {
                ut.rollback();
                return "Please Enter Valid Charges.";
            }
            if (Reason.equalsIgnoreCase("")) {
                ut.rollback();
                return "Reason For Cancel Should Not Be Blank";
            }
            /**
             * ********** Call FieldCheck ENDS ******************
             */
            float dtrSNo = ftsMethods.getTrsNo();

            List checkList2 = em.createNativeQuery("Select Coll_AlphaCode From bill_obcbooking where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "' AND substring(acno,1,2)='" + BRCODE + "'").getResultList();
            Vector AlphaCodeLst = (Vector) checkList2.get(0);
            if (checkList2.isEmpty()) {
                tmpAlpha = "0";
            } else {
                tmpAlpha = AlphaCodeLst.get(0).toString();
            }

            if (billType.equalsIgnoreCase("BP")) {
                String Detail = "VCH : Dishonour For BPNo: " + billNo + "/" + year;

                double amt = instamount + RetCharges;
                String FTSMsg = "";
                FTSMsg = ftsMethods.ftsPosting43CBS(acno, 2, 1, amt, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "N", null, "A", 3, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    ut.rollback();
                    return FTSMsg;
                }
                Detail = "VCH : Dishonour Amt For BPNo: " + billNo + "/" + year + " For Acno: " + acno + "";
                amt = instamount;
                FTSMsg = ftsMethods.ftsPosting43CBS(GLBillPay, 2, 0, amt, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "N", null, "A", 3, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    ut.rollback();
                    return FTSMsg;
                }
                if (RetCharges > 0) {
                    Detail = "VCH : Dishonour Chg For BPNo: " + billNo + "/" + year + " For Acno: " + acno + "";

                    amt = RetCharges;
                    FTSMsg = ftsMethods.ftsPosting43CBS(GLChequeRet, 2, 0, amt, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, null, "N", null, "A", 3, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                    if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                        ut.rollback();
                        return FTSMsg;
                    }
                }
            }
            float recnum = ftsMethods.getRecNo();
            Query insertQuery = em.createNativeQuery("Insert bill_obcprocessed(billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage,RetCharges,ReasonforCancel,InFavourOf,InstDt,InstEntryDt,Dt,status,EnterBy,TrsNo,RecNo,Invno,BillDetainPeriod,BillDetainDt,Coll_AlphaCode,Coll_BankName,Coll_BankAddr) Select billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage," + RetCharges + ",'" + Reason + "',InFavourOf,InstDt,Dt,'" + tempbd + "','D','" + enterby + "'," + dtrSNo + "," + recnum + ",Invno,billdetainperiod,billdetaindt,Coll_AlphaCode,Coll_BankName,Coll_BankAddr From bill_obcbooking where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "'");
            exec3 = insertQuery.executeUpdate();
            Query delQuery = em.createNativeQuery("Delete From bill_obcbooking where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "' and substring(acno,1,2)='" + BRCODE + "'");
            exec4 = delQuery.executeUpdate();
            if (RetCharges > 0) {
                //if ((exec > 0) && (exec1 > 0) && (exec2 > 0) && (exec3 > 0) && (exec4 > 0)) {
                if ((exec3 > 0) && (exec4 > 0)) {
                    ut.commit();
                    return "Dishonoured Successfully";
                } else {
                    ut.rollback();
                    return "Problem In Charges Posting";
                }
            } else {
                //if ((exec > 0) && (exec1 > 0) && (exec3 > 0) && (exec4 > 0)) {
                if ((exec3 > 0) && (exec4 > 0)) {
                    ut.commit();
                    return "Dishonoured Successfully";
                } else {
                    ut.rollback();
                    return "Problem In Charges Posting";
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
    }

    public String RealOutBillRealized(String BRCODE, String enterby, String billType, Float billNo, Integer year, String acno, String instno,
            double instamount, String instdt, double OtherCharges, double OtherBankCharges, double AmtDebited, String CustName, String DTRealize) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List checkList;
            List checkList22;
            String tmpAlpha;
            String CHK_MSG = null;
            String MSG = null;
            float TOKENNO = 0.0f;
            String tempbd = "";
            int exec = 0;
            int exec1 = 0;
            int exec2 = 0;
            int exec3 = 0;
            int exec4 = 0;
            int exec5 = 0;
            int exec6 = 0;
            String Expired = "";
            List bank = em.createNativeQuery("select date from bankdays WHERE dayendflag = 'N' and brncode='" + BRCODE + "'").getResultList();
            if (bank.isEmpty()) {
                ut.rollback();
                return "Date Does Not Exists !!!.";
            }
            Vector bankLst2 = (Vector) bank.get(0);
            tempbd = bankLst2.get(0).toString();

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
            checkList22 = em.createNativeQuery("select GLHO, GLBPIntRec, GLBCComm, GLBillPay, GLDraftPay, GLCASEPAY, GLHOPAY, GLBillCol, GLBillLodg,GLChequeRet, GLSundry, BnkGuarCr, BnkGuarDr, GLService, GLLcComm, GLBgComm from Bill_IBCOBCGLMast").getResultList();
//            if (checkList22.isEmpty()) {
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
//                Vector secLst1 = (Vector) checkList22.get(0);
//                GLBPIntRec = secLst1.get(1).toString();
//                GLBillPay = secLst1.get(3).toString();
//                GLBillCol = secLst1.get(7).toString();
//                GLBillLodg = secLst1.get(8).toString();
//                GLChequeRet = secLst1.get(9).toString();
//            }
//            }
            if ((instno == null) || (instdt == null) || (instno.equalsIgnoreCase("")) || (instdt.equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Check Instruement details";
            }
            List checkList1 = em.createNativeQuery("Select InstNo,InstAmount,InstDt,Acno,Comm,Postage,billdetainDT From bill_obcbooking Where BillNo = " + billNo + " And FYear=" + year + " and billtype='" + billType + "' and substring(acno,1,2)='" + BRCODE + "'").getResultList();
            if (checkList1.isEmpty()) {
                ut.rollback();
                return "Please Check Instruement details !!!.";
            }
            Vector secLs = (Vector) checkList1.get(0);
            String Dt = secLs.get(6).toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmdd");
            Date convertedBillDetainDT = dateFormat.parse(Dt);
            Date convertedTempbd = dateFormat.parse(tempbd);
            if (convertedBillDetainDT.after(convertedTempbd)) {
                Expired = "Detain period has expired Do you want to deduct extra charges ?  YES / NO";
            }
            /**
             * ********** Call FieldCheck ******************
             */
            if (billType.equalsIgnoreCase("")) {
                ut.rollback();
                return "Bill Type Should Not Be Blank";
            }
            if ((billNo.isNaN() == true) || (billNo < 0)) {
                ut.rollback();
                return "Please Enter Valid Bill No.!!";
            }
            if (year.equals("")) {
                ut.rollback();
                return "Year Should Not Be Blank.!!";
            }
            if (OtherCharges < 0) {
                ut.rollback();
                return "Please Enter Valid Charges";
            }
            if ((AmtDebited < 0) || (!((AmtDebited + OtherBankCharges) == instamount))) {
                ut.rollback();
                return "Please Enter Valid Amount";
            }
            /**
             * ********** Call FieldCheck ENDS ******************
             */
            List checkList2 = em.createNativeQuery("Select Coll_AlphaCode From bill_obcbooking where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "' and substring(acno,1,2)='" + BRCODE + "'").getResultList();
            Vector AlphaCodeLst = (Vector) checkList2.get(0);
            if (checkList2.isEmpty()) {
                tmpAlpha = "0";
            } else {
                tmpAlpha = AlphaCodeLst.get(0).toString();
            }

            float dtrSNo = ftsMethods.getTrsNo();
            String FTSMsg = "";
            if (tmpAlpha.equalsIgnoreCase("0")) {
                ut.rollback();
                return "Direct Party / Other Bank Entry Cannot Be Realized.";
            } else {
                if ((OtherBankCharges + OtherCharges) > 0) {
                    String Detail = "VCH : Charges For BPNo: " + billNo + "/" + year;
                    double amt = OtherBankCharges + OtherCharges;
                    FTSMsg = ftsMethods.ftsPosting43CBS(acno, 2, 1, amt, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, "", "N", "", "A", 3, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                    if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                        ut.rollback();
                        return FTSMsg;
                    }
                }
                String Detail = "VCH : Realized BPNo: " + billNo + "/" + year + "  For Acno: " + acno;
                FTSMsg = ftsMethods.ftsPosting43CBS(GLBillPay, 2, 0, instamount, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, "", "N", "", "A", 3, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    ut.rollback();
                    return FTSMsg;
                }
                Detail = "VCH : Realized BPNo: " + billNo + "/" + year + "  For Acno: " + acno;

                FTSMsg = ftsMethods.ftsPosting43CBS(GLHO, 2, 1, AmtDebited, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, "", "N", "", "A", 3, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    ut.rollback();
                    return FTSMsg;
                }

                if (OtherCharges > 0) {
                    Detail = "VCH : Comm For BPNo: " + billNo + "/" + year + "  For Acno: " + acno;

                    FTSMsg = ftsMethods.ftsPosting43CBS(GLBPIntRec, 2, 0, OtherCharges, tempbd, tempbd, enterby, BRCODE, BRCODE, 0, Detail, dtrSNo, 0.0f, 0, "", "N", "", "A", 3, instno, instdt, null, null, null, null, null, null, 0.0f, "N", "", "", "");
                    if (!FTSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                        ut.rollback();
                        return FTSMsg;
                    }
                }

                float ecnum = ftsMethods.getRecNo();

                Query insertQuery = em.createNativeQuery("insert bill_hoothers (FYear,instNo,Acno,CustName,InfavourOf,PayableAt,amount,enterby,Auth,AuthBy,dt,status,trantype, seqno,billtype,ty,recno,origindt) Select FYear,'" + instno + "',Acno,'" + CustName + "',InFavourOf,Coll_Alphacode," + instamount + ",'" + enterby + "','Y','" + enterby + "','" + tempbd + "','Paid', 2, billno, billtype,1," + ecnum + ",'" + DTRealize + "' From bill_obcbooking Where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "'");
                exec4 = insertQuery.executeUpdate();
            }
            //float recnum =ftsMethods.getRecNo(BRCODE);
            float recnum = ftsMethods.getRecNo();

            Query insertQuery2 = em.createNativeQuery("Insert bill_obcprocessed(billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage,Coll_BnkCharge,CreditedAmount,InFavourOf,InstDt,Dt,instEntryDt,Status,EnterBy,TrsNo,RecNo,Coll_AlphaCode,Coll_BankName,Coll_BankAddr,INVNO,BillDetainPeriod,BillDetainDt) Select billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage," + OtherBankCharges + "," + AmtDebited + ",InFavourOf,InstDt,'" + tempbd + "',Dt,'R','" + enterby + "'," + dtrSNo + "," + recnum + ",Coll_AlphaCode,Coll_BankName,Coll_BankAddr,INVNO,BillDetainPeriod,BillDetainDt From bill_obcbooking where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "'");
            exec5 = insertQuery2.executeUpdate();
            Query delQuery1 = em.createNativeQuery("Delete From bill_obcbooking Where Billno=" + billNo + " and Fyear=" + year + " and billType='" + billType + "' and substring(acno,1,2)='" + BRCODE + "'");
            exec6 = delQuery1.executeUpdate();
            //System.out.println("exec4,exec5,exec6:======="+exec4+"-"+exec5+"-"+exec6);
            if (((OtherBankCharges + OtherCharges) > 0) && (OtherCharges > 0)) {
                //if ((exec > 0) && (exec1 > 0) && (exec2 > 0) && (exec3 > 0) && (exec4 > 0) && (exec5 > 0) && (exec6 > 0)) {
                if ((exec5 > 0) && (exec6 > 0)) {
                    ut.commit();
                    return "Generated Batch No.  " + dtrSNo;
                } else {
                    ut.rollback();
                    return "Problem in Posting";
                }
            }
            if (((OtherBankCharges + OtherCharges) > 0) && (!(OtherCharges > 0))) {
                //if ((exec > 0) && (exec1 > 0) && (exec2 > 0) && (exec4 > 0) && (exec5 > 0) && (exec6 > 0)) {
                if ((exec5 > 0) && (exec6 > 0)) {
                    ut.commit();
                    return "Generated Batch No.  " + dtrSNo;
                } else {
                    ut.rollback();
                    return "Problem in Posting";
                }
            }
            if ((!((OtherBankCharges + OtherCharges) > 0)) && (!(OtherCharges > 0))) {
                //if ((exec1 > 0) && (exec2 > 0) && (exec4 > 0) && (exec5 > 0) && (exec6 > 0)) {
                if ((exec5 > 0) && (exec6 > 0)) {
                    ut.commit();
                    return "Generated Batch No.  " + dtrSNo;
                } else {
                    ut.rollback();
                    return "Problem in Posting";
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
        return null;
    }
}
