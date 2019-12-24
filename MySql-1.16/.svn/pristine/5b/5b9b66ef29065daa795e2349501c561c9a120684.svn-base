/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.report.NpciOwSortedByStatus;
import com.cbs.dto.report.OwClgPojo;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
//import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
//import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
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
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author root
 */
@Stateless(mappedName = "OtherTransactionManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class OtherTransactionManagementFacade implements OtherTransactionManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPosting;
    @EJB
    PostalDetailFacadeRemote postalRemote;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private InterBranchTxnFacadeRemote ibRemote;
//    @EJB
//    private TimerServiceFacadeRemote facade;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmyOne = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dmy = new SimpleDateFormat("dd MMM yyyy");
    NumberFormat nf = new DecimalFormat("#.##");
    /*
     * Start of Signature Details
     */

    public String getSignatureDetails(String acno) {
        System.out.print("String Account no = " + acno);
        List signature = em.createNativeQuery("select image from cbs_cust_image_detail where Auth = 'Y' "
                + "And newacno='" + acno + "'").getResultList();
        if (!signature.isEmpty()) {
            Vector img = (Vector) signature.get(0);
            String image = img.get(0).toString();
            return image;
        } else {
            return "Signature not found";
        }

    }
    /*
     * End of Signature Details
     */

    /*
     * Start of Shadow Balance Clearance
     */
    public List getReasonForCancel() throws ApplicationException {
        try {
//            List checkList = em.createNativeQuery("select Description from codebook where groupcode=13 and code <>0").getResultList();
            List checkList = em.createNativeQuery("select code,Description from codebook where groupcode=13 and code <>0").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     *
     * @return
     */
    public List getClearingOption() {
        List circleType = em.createNativeQuery("select circletype, circledesc, circlemicr from parameterinfo_clg "
                + "order by circletype").getResultList();
        return circleType;
    }

    /**
     *
     * @return
     */
    public List getBankName() throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List bankNameList = em.createNativeQuery("select distinct(BankName) from clg_bankdirectory order by BankName").getResultList();
            ut.commit();
            return bankNameList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String clgOutward3day(String AuthBy, String txnDate, String emFlag, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
        List<TransferSmsRequestTo> smsRequestToList = new ArrayList<TransferSmsRequestTo>();
        Map<String, Double> map = new HashMap<String, Double>();
        Map<String, Double> mapIgst = new HashMap<String, Double>();
        try {
            ut.begin();
            float bill_trsno = 0;
            float temp_vtot = -1;
            String details = null;
            float recNo = 0;
            Double ret_amt = 0.0d;
            Double ret_amtIgst = 0.0d;
            float glclg_trsno = 0;
            float retchq_no = 0;
            float retchq_noIgst = 0;
            int var2;
            int var3;
            int var4;
            int var5;
            int var6;
            int var7;
            String Data_Flag_passed = "FALSE";
            String Data_Bill_Flag_returned = "FALSE";
            String orgnIntersoleAcNo = "";
            String destIntersoleAcNo = "";
            String l_result = "";
            float tokenNo = 0;
            float trsNo = ftsPosting.getTrsNo();
            double totalRetChgs = 0;
            double totalRetChgsIgst = 0;

            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' And Brncode  = '" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();

            List allInfo = em.createNativeQuery("select distinct right(concat('000000',ifnull(glclgret,'')),6),retchg,right(concat('000000',ifnull(glclgretchg,'')),6),"
                    + "ifnull(substring(gl1.acname,1,40),''), ifnull(substring(gl2.acname,1,40),'') from parameterinfo_clg p "
                    + "left join gltable gl1 on substring(gl1.acno,5,6)=right(concat('000000',p.glclgret),6) left join gltable gl2 on "
                    + "substring(gl2.acno,5,6)=right(concat('000000'+p.glclgretchg),6) where circletype='" + emFlag + "'").getResultList();
            Vector allInfos = (Vector) allInfo.get(0);
            String glclgretHead = allInfos.get(0).toString();
//            Double retchg = Double.parseDouble(allInfos.get(1).toString());
            //String glclgretchgHead = allInfos.get(2).toString();
            String acnames = allInfos.get(3).toString();

//            if ((glclgretHead == null) || (glclgretHead.equalsIgnoreCase(""))) {
//                throw new ApplicationException("Please Check for GL Head of Clearing and Return Charges");
////                ut.rollback();
////                return "Please Check for GL Head of Clearing and Return Charges";
//            }
            /*
             * For Service Tax
             */
            List listCode = em.createNativeQuery("Select code from parameterinfo_report where reportname like 'STAXMODULE_ACTIVE'").getResultList();
            Vector element4 = (Vector) listCode.get(0);
            Integer code = Integer.parseInt(element4.get(0).toString());
//            String glAcnoforSTax = "";
//            double totalChgToBeDed = 0d;
//            double sTax = 0d;
//            if (code == 1) {
////                List taxMasterList = em.createNativeQuery("Select rot, glhead from taxmaster where ApplicableFlag='Y'").getResultList();
////                Vector element5 = (Vector) taxMasterList.get(0);
////                Float rot = Float.parseFloat(element5.get(0).toString());
////                String glHead = element5.get(1).toString();
////                glAcnoforSTax = brCode + glHead + "01";
////                sTax = CbsUtil.round(((retchg * rot) / 100), 2);
////                if (sTax < 1) {
////                    sTax = 1;
////                }
////                totalChgToBeDed = retchg + sTax;
//                map = ibRemote.getTaxComponent(retchg,Tempbd);
//                Set<Map.Entry<String, Double>> set = map.entrySet();
//                Iterator<Map.Entry<String, Double>> it = set.iterator();
//                while (it.hasNext()) {
//                    Map.Entry entry = it.next();
//                    sTax = sTax + Double.parseDouble(entry.getValue().toString());  
//                }
//            } 
//            else {
//                totalChgToBeDed = retchg;
//            }
            String glclgret = brCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + glclgretHead + "01";
            //String glclgretchg = brCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + glclgretchgHead + "01";
            String glclCurCode = ftsPosting.getCurrentBrnCode(glclgret);

            String billAcNo = ftsPosting.getGlHeadFromParam(brCode, SiplConstant.ISO_HEAD.getValue());
            List curChkList = em.createNativeQuery("select c.acno,c.TxnInstAmt,c.reasonforcancel,c.enterby,ifnull(a.acctnature,''),"
                    + " ifnull(c.obcflag,''),c.txninstno,ifnull(c.billtype,''),c.vtot,ifnull(c.fyear,''),ifnull(c.bcbpno,''),ifnull(c.alphacode,''),"
                    + " c.txninstdate, c.dt, ifnull(ifnull(am.custname,ta.custname),gl.acname) as custname,c.trandesc,c.orgnBrcode,c.destBrcode,ifnull(c.drwName,''), "
                    + " ifnull(cm.mailStateCode,'') as stateCode,ifnull(br.State,'') as brState from "
                    + " clg_ow_shadowbal c  left join accounttypemaster a on substring(c.acno,3,2)=a.acctcode left join accountmaster am "
                    + " on am.acno=c.acno left join td_accountmaster ta on ta.acno= c.acno left join gltable gl on gl.acno=c.acno "
                    + " left join branchmaster br on br.brncode=cast(c.orgnBrcode as unsigned) "
                    + " left join customerid ci on am.acno=ci.acno "
                    + " left join cbs_customer_master_detail cm on ci.CustId = cast(cm.customerid as unsigned) where "
                    + " c.orgnBrcode='" + brCode + "' and c.txndate='" + txnDate + "' and c.emflag='" + emFlag + "' and c.txnstatus='P'  "
                    + " order by c.billtype,c.vtot").getResultList();

            // Added by Manish kumar condition (and c.CtsReturnCode is null or  c.CtsReturnCode = '')
//            List curChkList = em.createNativeQuery("select c.acno,c.TxnInstAmt,c.reasonforcancel,c.enterby,ifnull(a.acctnature,''),"
//                    + "ifnull(c.obcflag,''),c.txninstno,ifnull(c.billtype,''),c.vtot,ifnull(c.fyear,''),ifnull(c.bcbpno,''),ifnull(c.alphacode,''),"
//                    + "c.txninstdate, c.dt, ifnull(ifnull(am.custname,ta.custname),gl.acname) as custname,c.trandesc,c.orgnBrcode,c.destBrcode,ifnull(c.drwName,'') from "
//                    + "clg_ow_shadowbal c  left join accounttypemaster a on substring(c.acno,3,2)=a.acctcode left join accountmaster am "
//                    + "on am.acno=c.acno left join td_accountmaster ta on ta.acno= c.acno left join gltable gl on gl.acno=c.acno where "
//                    + "c.orgnBrcode='" + brCode + "' and c.txndate='" + txnDate + "' and c.emflag='" + emFlag + "' and c.txnstatus='P' and c.CtsReturnCode is null or  c.CtsReturnCode = ''  "
//                    + "order by c.billtype,c.vtot").getResultList();
            Map<String, List<String>> deductMap = new ConcurrentHashMap<String, List<String>>();
            for (int i = 0; i < curChkList.size(); i = i + 1) {
                Vector all = (Vector) curChkList.get(i);
                String acNo = all.get(0).toString();
                Double amt = Double.parseDouble(all.get(1).toString());
                int reasonforcancel = Integer.parseInt(all.get(2).toString());

                String enterby = all.get(3).toString();
                String actnature = all.get(4).toString();
                String obcflag = all.get(5).toString();
                String instno = all.get(6).toString();
                String billtype = all.get(7).toString();

                float vtot = Float.parseFloat(all.get(8).toString());
                String Fyear = all.get(9).toString();
                String bcbpno = all.get(10).toString();
                String alphacode = all.get(11).toString();
                String txninstdate = all.get(12).toString();
                String valueDt = all.get(13).toString();
                String custname = all.get(14).toString();

                int trandesc = Integer.parseInt(all.get(15).toString());
                String orgnBrCode = all.get(16).toString();
                String destBrCode = all.get(17).toString();
                String drwName = all.get(18).toString();
                String custState = all.get(19).toString();
                String branchState = all.get(20).toString();

                if (!orgnBrCode.equalsIgnoreCase(destBrCode)) {
                    orgnIntersoleAcNo = ftsPosting.getGlHeadFromParam(orgnBrCode, SiplConstant.ISO_HEAD.getValue());
                    destIntersoleAcNo = ftsPosting.getGlHeadFromParam(destBrCode, SiplConstant.ISO_HEAD.getValue());
                }
                String brCod = ftsPosting.getCurrentBrnCode(acNo);
                String status = ibRemote.fnGetLoanStatus(acNo, Tempbd);
                if (reasonforcancel == 0) {
                    Data_Flag_passed = "TRUE";
                    if ((billtype == null) || (billtype.equalsIgnoreCase(""))) {
                        //if ((actnature.equals(CbsConstant.PAY_ORDER)) && drwName.equalsIgnoreCase("POSTAL")) {
                        if ((actnature.equals(CbsConstant.PAY_ORDER))) {
                            List l5 = em.createNativeQuery("select postflag from gltable where acno ='" + acNo + "'").getResultList();
                            Vector v3 = (Vector) l5.get(0);
                            int postFlag = Integer.parseInt(v3.get(0).toString());
                            if (postFlag == 11) {
                                List list = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_sundry where Fyear='" + Tempbd.substring(0, 4) + "'").getResultList();
                                Vector element1 = (Vector) list.get(0);
                                float seqNo = Float.parseFloat(element1.get(0).toString());
                                Query q1 = em.createNativeQuery("Insert into bill_sundry(Fyear,AcNo,amount,enterby,dt,status,auth,"
                                        + "trantype,seqno,ty,recno,origindt,authby) values('" + Tempbd.substring(0, 4) + "','" + acNo + "',ifnull(" + amt + ",0),'" + AuthBy + "','" + Tempbd + "',"
                                        + "'ISSUED','Y',0," + seqNo + ",0," + recNo + ",'" + Tempbd + "','" + AuthBy + "')");
                                q1.executeUpdate();
                            }

                            /**
                             * **************DINESH CODE COMMENTED *
                             * **********************
                             */
                            //Checking of bill_sundry_dt table.
//                            List list = em.createNativeQuery("select acno,details,amount from bill_sundry_dt where fyear = " + Integer.parseInt(Fyear) + " and seqno=" + Double.parseDouble(bcbpno) + "").getResultList();
//                            if (list.isEmpty()) {
//                                ut.rollback();
//                                return l_result = "There is no data in bill sundry dt for:- fyear: " + Integer.parseInt(Fyear) + " And SeqNo: " + Double.parseDouble(bcbpno);
//                            }
                            //Performing the dr transaction for sundry head.
//                            l_result = performGeneralTransaction(acNo, 1, 1, "", amt, trsNo, AuthBy, brCode, "", "", "19000101", 3, AuthBy);
//                            if (!l_result.equalsIgnoreCase("true")) {
//                                ut.rollback();
//                                return l_result;
//                            }
                            //Updating the status in bill_sundry
//                            int insertResult = em.createNativeQuery("update bill_sundry set status='PAID',dt='" + ymd.format(new Date()) + "',"
//                                    + "lastupdateby='" + AuthBy + "' where fyear=" + Integer.parseInt(Fyear) + " and seqno=" + Double.parseDouble(bcbpno) + "").executeUpdate();
//                            if (insertResult <= 0) {
//                                ut.rollback();
//                                return l_result = "Problem in bill sundry updation.";
//                            }
                            //Now Cr all loan a/c and satisfy all the emis agains a/c and membe no.
//                            String branchPremiumHead = brCode + postalRemote.getAccountByPurpose("PREMIUM-HEAD");
//                            String branchWellFareHead = brCode + postalRemote.getAccountByPurpose("WELFARE-HEAD");
//                            for (int k = 0; k < list.size(); k++) {
//                                Vector element = (Vector) list.get(k);
//                                String acno = element.get(0).toString();
//                                String tdetails = element.get(1).toString();
//                                Double amount = Double.parseDouble(element.get(2).toString());
//
//                                String paidSno = "";
//                                l_result = satisfyRecovery(tdetails, acno, amount, AuthBy);
//                                if (!l_result.contains("true")) {
//                                    ut.rollback();
//                                    return l_result;
//                                }
//                                paidSno = l_result.substring(l_result.indexOf(":") + 1);
//                                String creditAccount = "";
//                                if (tdetails.equalsIgnoreCase("PINSP")) {
//                                    creditAccount = branchPremiumHead;
//                                } else if (tdetails.equalsIgnoreCase("PWELL")) {
//                                    creditAccount = branchWellFareHead;
//                                } else {
//                                    creditAccount = acno;
//                                }
//
//                                l_result = performGeneralTransaction(creditAccount, 0, 1, "", amount, trsNo, AuthBy, brCode, paidSno, acno, "19000101", 3, AuthBy);
//                                if (!l_result.toLowerCase().contains("true")) {
//                                    ut.rollback();
//                                    return l_result;
//                                }
//                            }
                            //Updating the status in bill sundry dt.
//                            insertResult = em.createNativeQuery("update bill_sundry_dt set status='PAID',dt='" + ymd.format(new Date()) + "',"
//                                    + "lastupdateby='" + AuthBy + "' where fyear=" + Integer.parseInt(Fyear) + " and seqno=" + Double.parseDouble(bcbpno) + "").executeUpdate();
//                            if (insertResult <= 0) {
//                                ut.rollback();
//                                return l_result = "Problem in bill sundry dt updation.";
//                            }
                        }

                        //POSTAL ADDITION END HERE
                        //if (!((actnature.equals(CbsConstant.PAY_ORDER)) && drwName.equalsIgnoreCase("POSTAL"))) {
//                      if (!((actnature.equals(CbsConstant.PAY_ORDER)))) {  // Code Comment On 20141212 By Mayank
                        l_result = ftsPosting.updateBalance(actnature, acNo, amt, 0, "Y", "N");
                        if (!l_result.equals("TRUE")) {
                            throw new ApplicationException("" + l_result);
//                            ut.rollback();
//                            return "" + l_result;
                        }
                        if (!((actnature.equals(CbsConstant.PAY_ORDER)))) {   // Code Added On 20141212 By Mayank
                            // insert into SMS Table in case of cheque paased
//                            if (smsFacade.isValidAcnoForSms("T", acNo, 1, 0, amt)) {
//                                String balance = ftsPosting.ftsGetBal(acNo);
//
//                                String message = "Your a/c XXXX" + acNo.substring(4, 10) + "XX is credited with Rs. " + nf.format(amt) + " on " + dmy.format(ymd.parse(Tempbd))
//                                        + " by clearing. Your available balance is Rs. " + nf.format(Double.parseDouble(balance));
//                                l_result = ftsPosting.insertSms(acNo, message);
//                                if (!l_result.equalsIgnoreCase("TRUE")) {
//                                    ut.rollback();
//                                    return "" + l_result;
//                                }
//                            }

                            //Adding Object For Sms
                            SmsToBatchTo to = new SmsToBatchTo();
                            to.setAcNo(acNo);
                            to.setCrAmt(amt);
                            to.setDrAmt(0d);
                            to.setTranType(1);
                            to.setTy(0);
                            to.setTxnDt(dmyOne.format(ymd.parse(Tempbd)));
                            to.setTemplate(SmsType.CLEARING_DEPOSIT);

                            if (drwName == null || drwName.equals("")) {
                                to.setDetails("");
                            } else {
                                if (drwName.trim().length() > 30) {
                                    to.setDetails(drwName.substring(0, 30));
                                } else {
                                    to.setDetails(drwName);
                                }
                            }

                            smsBatchList.add(to);
                            //End
                        }

                        if ((obcflag.equals("OBC")) || (obcflag.equals("DD")) || (obcflag.equals("PO")) || (obcflag.equals("CHQ"))) {
                            Query updateQuery = em.createNativeQuery("update bill set dt='" + Tempbd + "', OBCFlag = 'Cleared' from bill_obcprocessed bill inner join accountmaster am on Instno ='" + instno + "' and am.ACNo = bill.acno and am.CurBrCode = '" + brCode + "'");
                            updateQuery.executeUpdate();
                        }
                        /* Code for NPA Recovery*/

                        if (status.equals("SUB") || status.equals("DOU") || status.equals("LOS")) {
                            l_result = ftsPosting.npaRecoveryUpdation(trsNo, actnature, acNo, Tempbd, amt, orgnBrCode, brCod, AuthBy);
                            if (!l_result.equalsIgnoreCase("True")) {
                                throw new ApplicationException(l_result + " for " + acNo);
                            }
                        }
                        if ((actnature.equals(CbsConstant.TERM_LOAN)) || (actnature.equals(CbsConstant.DEMAND_LOAN))) {
                            details = "By Clearing : Inst No :- " + instno;

                            l_result = ftsPosting.loanDisbursementInstallment(acNo, amt, 0, AuthBy, Tempbd, 0, trandesc, details);
                            if (!l_result.equals("TRUE")) {
                                throw new ApplicationException(l_result + " for " + acNo);
//                                ut.rollback();
//                                return l_result + " for " + acNo;
                            }
                            details = "";
                        } else if (actnature.equals(CbsConstant.RECURRING_AC) && (trandesc == 0 || trandesc == 1)) {
                            details = "By Clearing : Inst No :- " + instno;
                            l_result = ftsPosting.loanDisbursementInstallment(acNo, amt, 0, AuthBy, Tempbd, 0, trandesc, details);
                            if (!l_result.equals("TRUE")) {
                                throw new ApplicationException(l_result + " for " + acNo);
//                                ut.rollback();
//                                return l_result + " for " + acNo;
                            }
                            details = "";
                        }
                    } else {
                        if (temp_vtot != vtot) {
                            details = alphacode + billtype + bcbpno;
                            l_result = "";
                            l_result = insertTransfer(actnature, acNo, 1, amt, Tempbd, 2, details, AuthBy, trsNo, null, 0, "N", null, 0, 3,
                                    instno, 0, null, "A", 1, null, null, null, null, null, null, 0, null, brCode, Tempbd);
                            if (!l_result.equals("TRUE")) {
                                throw new ApplicationException("" + l_result);
//                                ut.rollback();
//                                return "" + l_result;
                            }
                            l_result = "";
                            l_result = insertTransfer("PO", billAcNo, 0, amt, Tempbd, 2, details, AuthBy, trsNo, null, 0, "N", null, 0, 3,
                                    instno, 0, null, "A", 1, null, null, null, null, null, null, 0, null, brCode, Tempbd);
                            if (!l_result.equals("TRUE")) {
                                throw new ApplicationException("" + l_result);
//                                ut.rollback();
//                                return "" + l_result;
                            }

                            //recNo = ftsPosting.getRecNo(acNo.substring(0,2));
                            recNo = ftsPosting.getRecNo();
                            Query insertQuery2 = em.createNativeQuery("insert into bill_hoothers(FYEAR,SEQNO,INSTNO,BILLTYPE,ACNO,PAYABLEAT,AMOUNT,"
                                    + "DT,ORIGINDT,STATUS,enterby,ty,trantype,recno,auth) values (" + Fyear + "," + bcbpno + "," + "'" + instno + "'"
                                    + "," + "'" + billtype + "'" + "," + "'" + acNo + "'" + "," + "'" + alphacode + "'" + "," + amt + "," + "'"
                                    + Tempbd + "'" + "," + "'" + txninstdate + "'" + "," + "'Issued'" + "," + "'" + AuthBy + "'" + "," + 0 + ","
                                    + 2 + "," + recNo + "," + "'Y'" + ")");
                            var2 = insertQuery2.executeUpdate();
                            if (var2 < 0) {
                                throw new ApplicationException("Insertion Problem in Bill_HoOthers Table");
//                                ut.rollback();
//                                return "Insertion Problem in Bill_HoOthers Table";
                            }

                            if (actnature.equals(CbsConstant.PAY_ORDER)) {
                                List l5 = em.createNativeQuery("select postflag from gltable where acno ='" + acNo + "'").getResultList();
                                Vector v3 = (Vector) l5.get(0);
                                int postFlag = Integer.parseInt(v3.get(0).toString());
                                if (postFlag == 11) {
                                    List list = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_sundry where Fyear='" + Fyear + "'").getResultList();
                                    Vector element1 = (Vector) list.get(0);
                                    float seqNo = Float.parseFloat(element1.get(0).toString());
                                    Query q1 = em.createNativeQuery("Insert into bill_sundry(Fyear,AcNo,amount,enterby,dt,status,auth,"
                                            + "trantype,seqno,ty,recno,origindt,authby) values('" + Fyear + "','" + acNo + "',ifnull(" + amt + ",0),'" + AuthBy + "','" + Tempbd + "',"
                                            + "'ISSUED','Y',0," + seqNo + ",0," + recNo + ",'" + Tempbd + "','" + AuthBy + "')");
                                    q1.executeUpdate();
                                }
                            }
                        }
                    }
                } else {
                    //For Return Cheques
                    if ((billtype == null) || (billtype.equalsIgnoreCase(""))) {

                        //Code Changed To Return Charges Account Type Wise 
                        double retchg = 0;
                        String glclgretchgHead = "";
                        // List selectRetChg = ftsPosting.getCheqRetChgAndHead("OW Cheque Return Charges", ftsPosting.getAccountCode(acNo));
                        List selectRetChg = ftsPosting.getChequeReturnCharge("CHEQUE-RETURN-CHG", "OW-CHEQUE", amt, ftsPosting.getAccountCode(acNo));
                        if (!selectRetChg.isEmpty()) {
                            Vector vecRetChg = (Vector) selectRetChg.get(0);
                            retchg = Double.parseDouble(vecRetChg.get(1).toString());
                            glclgretchgHead = vecRetChg.get(0).toString();
                        }

                        if ((glclgretHead == null) || (glclgretHead.equalsIgnoreCase(""))) {
                            throw new ApplicationException("Please Check for GL Head of Clearing and Return Charges");
                        }

                        String glclgretchg = brCode + glclgretchgHead;

                        l_result = "";
                        details = "Cheque Return. Inst No :- " + instno;
                        float recno = 0;

                        //New Addition for postal
//                        if ((actnature.equals(CbsConstant.PAY_ORDER)) && drwName.equalsIgnoreCase("POSTAL")) {
//                            //Updating the status in bill_sundry
//                            int insertResult = em.createNativeQuery("update bill_sundry set status='RETURN',dt='" + ymd.format(new Date()) + "',"
//                                    + "lastupdateby='" + AuthBy + "' where fyear=" + Integer.parseInt(Fyear) + " and seqno=" + Double.parseDouble(bcbpno) + "").executeUpdate();
//                            if (insertResult <= 0) {
//                                ut.rollback();
//                                return "Problem in bill sundry updation in chq return.";
//                            }
                        //Updating the status in bill sundry dt.
//                            insertResult = em.createNativeQuery("update bill_sundry_dt set status='RETURN',dt='" + ymd.format(new Date()) + "',"
//                                    + "lastupdateby='" + AuthBy + "' where fyear=" + Integer.parseInt(Fyear) + " and seqno=" + Double.parseDouble(bcbpno) + "").executeUpdate();
//                            if (insertResult <= 0) {
//                                ut.rollback();
//                                return "Problem in bill sundry dt updation.";
//                            }
//                            return "true";
//                        }
                        //New Addition for postal end.
                        if (orgnBrCode.equalsIgnoreCase(destBrCode)) {
                            l_result = ftsPosting.insertRecons(actnature, acNo, 1, amt, Tempbd, valueDt, 1, details, enterby, trsNo, null, recno, "Y", AuthBy,
                                    0, 3, null, null, tokenNo, null, "A", 1, null, null, null, null, brCode, brCod, 0, null, "", "");

                            if (!l_result.equals("TRUE")) {
                                throw new ApplicationException("Problem in Insertion in recons :" + l_result);
                            }
                            //Deaf Updation
                            ftsPosting.lastTxnDateUpdation(acNo);

                            recNo = ftsPosting.getRecNo();
                            Query insertQuery3 = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,dramt,ty,trantype,recno,trsno,auth,"
                                    + "enterby,authby,details,org_brnid,dest_brnid,valueDt,reasonforcancel) values ('" + acNo + "','" + custname + "','"
                                    + Tempbd + "'," + amt + "," + 1 + "," + 1 + "," + recNo + "," + trsNo + ",'Y','" + enterby
                                    + "','" + AuthBy + "','" + details + "','" + brCode + "','" + brCod + "','" + valueDt + "','" + reasonforcancel + "')");
                            var3 = insertQuery3.executeUpdate();
                            if (var3 < 0) {
                                throw new ApplicationException("Insert Problem in Recon_Clg_d for Ac No:" + acNo);
                            }
                        } else {
                            //For handling Inter branch transaction Added By Dhirendra Singh
                            String rsMsg = interBranchClgTxn(orgnBrCode, details, orgnIntersoleAcNo, destIntersoleAcNo, actnature, acNo, Tempbd, valueDt,
                                    enterby, trsNo, recNo, AuthBy, tokenNo, amt, custname, brCode, brCod, 1);
                            if (!rsMsg.equalsIgnoreCase("true")) {
                                throw new ApplicationException(rsMsg);
                            }
                        }
                        //Sending SMS
                        try {
                            TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                            tSmsRequestTo.setMsgType("PAT");
                            tSmsRequestTo.setTemplate(SmsType.CHQ_RETURN_CLEARING_DEBIT);
                            tSmsRequestTo.setAcno(acNo);
                            tSmsRequestTo.setAmount(amt);
                            tSmsRequestTo.setDate(dmyOne.format(ymd.parse(Tempbd)));
                            tSmsRequestTo.setPromoMobile(smsFacade.getSubscriberDetails(acNo).getMobileNo());
                            tSmsRequestTo.setFirstCheque(instno);
                            tSmsRequestTo.setBalFlag("Y");
                            smsRequestToList.add(tSmsRequestTo);
                        } catch (Exception ex) {
                            System.out.println("A/c is not registered::" + acNo);
                        }
                        //End
                        ret_amt = ret_amt + amt;
                        details = "";
                        if (!(actnature.equals(CbsConstant.PAY_ORDER) || reasonNotAppForCharge(reasonforcancel).equalsIgnoreCase("true")
                                || status.equals("SUB") || status.equals("DOU") || status.equals("LOS"))) {
                            double sTax = 0d;
                            if (code == 1) {
                                if (custState.equalsIgnoreCase(branchState)) {
                                    map = ibRemote.getTaxComponent(retchg, Tempbd);
                                    Set<Map.Entry<String, Double>> set = map.entrySet();
                                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                                    while (it.hasNext()) {
                                        Map.Entry entry = it.next();
                                        sTax = sTax + Double.parseDouble(entry.getValue().toString());
                                    }
                                    totalRetChgs = totalRetChgs + retchg;
                                } else {
                                    map = ibRemote.getIgstTaxComponent(retchg, Tempbd);
                                    Set<Map.Entry<String, Double>> set = map.entrySet();
                                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                                    while (it.hasNext()) {
                                        Map.Entry entry = it.next();
                                        sTax = sTax + Double.parseDouble(entry.getValue().toString());
                                    }
                                    totalRetChgsIgst = totalRetChgsIgst + retchg;
                                }
                            } else {
                                totalRetChgs = totalRetChgs + retchg;
                            }
                            glclg_trsno = bill_trsno + 1;
                            l_result = "";
                            String balance = ftsPosting.checkBalance(acNo, (retchg + sTax), enterby);
                            if (balance.equals("True")) {
                                l_result = ftsPosting.updateBalance(actnature, acNo, 0, (retchg + sTax), "Y", "N");
                                if (!l_result.equals("TRUE")) {
                                    throw new ApplicationException("Problem in Updating balance :");
                                }
                                l_result = "";
                                retchq_no = retchq_no + 1;
                                //added trandesc 35 for cheque return charges.

                                // OLD Code For UnAuthorized Entry Of Charges 
//                                l_result = insertTransfer(actnature, acNo, 1, retchg, Tempbd, 2, "Cheque Return Chg.", AuthBy, trsNo,
//                                        null, 0, "N", null, 109, 3, instno, 0, null, "A", 1, null, null, null, null, null, null, 0, null, brCode, valueDt);
//                                if (!l_result.equals("TRUE")) {
//                                    throw new ApplicationException("Problem in Insertion in recons :" + l_result);
//                                }
//                                if (sTax > 0) {
//                                        l_result = insertTransfer(actnature, acNo, 1, sTax, Tempbd, 2, "GST for Cheque Return Chg.", AuthBy, trsNo,
//                                            null, 0, "N", null, 71, 3, instno, 0, null, "A", 1, null, null, null, null, null, null, 0, null, brCode, valueDt);
//                                    if (!l_result.equals("TRUE")) {
//                                        throw new ApplicationException("Problem in Insertion in recons :" + l_result);
//                                    }
//                                }
                                // New Code For Authorized Entry Of Charges 
                                //for Same Branch
                                if (orgnBrCode.equalsIgnoreCase(destBrCode)) {
                                    recNo = ftsPosting.getRecNo();
                                    String acRecon = ftsPosting.insertRecons(actnature, acNo, 1, retchg, Tempbd, valueDt, 2, "Cheque Return Chg.", AuthBy, trsNo, null, recno, "Y", "SYSTEM",
                                            109, 3, instno, null, tokenNo, null, "A", 1, null, null, null, null, brCode, brCod, 0, null, "", "");

                                    if (!acRecon.equals("TRUE")) {
                                        throw new ApplicationException("Problem in Insertion in recons :" + l_result);
                                    }

                                    Query insertQuery82 = em.createNativeQuery("insert into recon_trf_d( acno , custname, dt, Dramt, CrAmt, Ty, TranType,"
                                            + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                                            + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                                            + "values ('" + acNo + "','" + ftsPosting.getCustName(acNo) + "','" + Tempbd + "'," + retchg + ",0,1,2," + recNo + ","
                                            + trsNo + ",'" + instno + "',3,1,'Y','" + AuthBy + "','SYSTEM',null,109"
                                            + "," + tokenNo + ",'A', CURRENT_TIMESTAMP,'Cheque Return Chg.',0,null,'" + brCode + "','"
                                            + brCode + "','" + valueDt + "','','')");
                                    int varChgRet = insertQuery82.executeUpdate();
                                    if (varChgRet < 0) {
                                        throw new ApplicationException("Insert Problem in recon_trf_d for Ac No:" + acNo);
                                    }
                                    if (sTax > 0) {
                                        recNo = ftsPosting.getRecNo();
                                        String sTaxRecon = ftsPosting.insertRecons(actnature, acNo, 1, sTax, Tempbd, valueDt, 2, "GST for Cheque Return Chg.",
                                                AuthBy, trsNo, null, recno, "Y", "SYSTEM", 71, 3, instno, null, tokenNo, null, "A", 1, null, null, null, null,
                                                brCode, brCod, 0, null, "", "");

                                        if (!sTaxRecon.equals("TRUE")) {
                                            throw new ApplicationException("Problem in Insertion in recons :" + acNo);
                                        }

                                        Query insertQuerySTax = em.createNativeQuery("insert into recon_trf_d( acno , custname, dt, Dramt, CrAmt, Ty, TranType,"
                                                + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                                                + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                                                + "values ('" + acNo + "','" + ftsPosting.getCustName(acNo) + "','" + Tempbd + "'," + sTax + ",0,1,2," + recNo + ","
                                                + trsNo + ",'" + instno + "',3,1,'Y','SYSTEM','" + AuthBy + "',null,71"
                                                + "," + tokenNo + ",'A', CURRENT_TIMESTAMP,'GST for Cheque Return Chg.',0,null,'" + brCode + "','"
                                                + brCode + "','" + valueDt + "','','')");
                                        int varStax = insertQuerySTax.executeUpdate();
                                        if (varStax < 0) {
                                            throw new ApplicationException("Insert Problem in recon_trf_d for Ac No:" + acNo);
                                        }
                                    }
                                } else {
                                    details = "Cheque Return Chg.";
                                    String rsMsg = interBranchClgTxn(orgnBrCode, details, orgnIntersoleAcNo, destIntersoleAcNo, actnature, acNo, Tempbd, valueDt,
                                            AuthBy, trsNo, recNo, "System", tokenNo, retchg, custname, brCode, brCod, 2);
                                    if (!rsMsg.equalsIgnoreCase("true")) {
                                        throw new ApplicationException(rsMsg);
                                    }

                                    if (sTax > 0) {
                                        details = "GST for Cheque Return Chg.";
                                        rsMsg = interBranchClgTxn(orgnBrCode, details, orgnIntersoleAcNo, destIntersoleAcNo, actnature, acNo, Tempbd, valueDt,
                                                AuthBy, trsNo, recNo, "System", tokenNo, sTax, custname, brCode, brCod, 2);
                                        if (!rsMsg.equalsIgnoreCase("true")) {
                                            throw new ApplicationException(rsMsg);
                                        }
                                    }
                                }
                                if (deductMap.containsKey(glclgretchg)) {
                                    for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {
                                        Map.Entry entry = (Map.Entry) it.next();
                                        String sKey = entry.getKey().toString();
                                        if (glclgretchg.equalsIgnoreCase(sKey)) {
                                            List kLst = (List) entry.getValue();
                                            double nVal = Double.parseDouble(kLst.get(1).toString()) + retchg;
                                            List glVal = new ArrayList();
                                            glVal.add(sKey);
                                            glVal.add(nVal);
                                            deductMap.remove(sKey);
                                            deductMap.put(sKey, glVal);
                                        }
                                    }
                                } else {
                                    List glVal = new ArrayList();
                                    glVal.add(glclgretchg);
                                    glVal.add(retchg);
                                    deductMap.put(glclgretchg, glVal);
                                }
                            } else {
                                recNo = ftsPosting.getRecNo();
                                Query insertQuery4 = em.createNativeQuery("insert into pendingcharges(Acno,dt,amount,details,ty,trantype,recno,"
                                        + "trsno,enterby,auth,recover,TRANDESC) values (" + "'" + acNo + "'" + "," + "'" + Tempbd + "'" + "," + retchg
                                        + "," + "'" + details + "'" + "," + 1 + "," + 2 + "," + recNo + "," + trsNo + "," + "'" + AuthBy + "'" + ","
                                        + "'N'" + "," + "'N'" + ",109)");
                                insertQuery4.executeUpdate();
                                if (code == 1) {
                                    if (custState.equalsIgnoreCase(branchState)) {
                                        totalRetChgs = totalRetChgs - retchg;
                                    } else {
                                        totalRetChgsIgst = totalRetChgsIgst - retchg;
                                    }
                                } else {
                                    totalRetChgs = totalRetChgs - retchg;
                                }
                            }
                        }
                    } else {
                        Data_Bill_Flag_returned = "TRUE";
                        l_result = "";
                        float recno = 0;

                        l_result = ftsPosting.insertRecons("PO", glclgret, 0, amt, Tempbd, valueDt, 1, "Returned BP/BC Amount", AuthBy, trsNo, null, recno, "Y",
                                AuthBy, 0, 3, null, null, tokenNo, null, "A", 1, null, null, null, null, brCode, glclCurCode, 0, null, "", "");

                        if (!l_result.equals("TRUE")) {
                            throw new ApplicationException("Problem in Insertion in recons :" + l_result);
                        }
                        l_result = "";
                        recNo = ftsPosting.getRecNo();
                        Query insertQuery5 = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,cramt,ty,trantype,recno,trsno,auth,"
                                + "enterby,authby,details,org_brnid,dest_brnid,valueDt) values ('" + glclgret + "','" + acnames + "','" + Tempbd + "',"
                                + amt + "," + 0 + "," + 1 + "," + recNo + "," + trsNo + ",'Y','" + AuthBy + "','" + AuthBy + "','Returned BP/BC Amount','"
                                + brCode + "','" + glclCurCode + "','" + valueDt + "')");
                        var5 = insertQuery5.executeUpdate();
                        if (var5 < 0) {
                            throw new ApplicationException("Insert Problem in Recon_Clg_d for Ac No" + glclgret);
                        }
                        l_result = ftsPosting.insertRecons(actnature, acNo, 1, amt, Tempbd, valueDt, 1, "Returned BP/BC Amount", AuthBy, trsNo, null, recno,
                                "Y", AuthBy, 0, 3, null, null, tokenNo, null, "A", 1, null, null, null, null, brCode, brCod, 0, null, "", "");

                        if (!l_result.equals("TRUE")) {
                            throw new ApplicationException("Problem in Insertion in recons :" + l_result);
                        }
                        recNo = ftsPosting.getRecNo();

                        Query insertQuery6 = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,dramt,ty,trantype,recno,trsno,auth,"
                                + "enterby,authby,details,org_brnid,dest_brnid,valueDt) values ('" + acNo + "','" + custname + "','"
                                + Tempbd + "'," + amt + "," + 1 + "," + 1 + "," + recNo + "," + trsNo + ",'Y','" + AuthBy + "','" + AuthBy
                                + "','Returned BP/BC Amount'" + ",'" + brCode + "','" + brCod + "','" + valueDt + "')");
                        var6 = insertQuery6.executeUpdate();
                        if (var6 < 0) {
                            throw new ApplicationException("Insert Problem in Recon_Clg_d for Ac No" + acNo);
                        }
                    }
                }
            }
            if (ret_amt > 0) {
                l_result = "";
                l_result = ftsPosting.updateBalance("PO", glclgret, ret_amt, 0, "Y", "Y");
                if (!l_result.equals("TRUE")) {
                    throw new ApplicationException("Problem in Updating balance :" + l_result);
                }
                String postDate = "";
                List postingDateList = em.createNativeQuery("select date_format(PostingDate,'%Y%m%d') from clg_ow_register where EntryDate='" + txnDate + "' and EmFlag = '" + emFlag + "' and brncode = '" + brCode + "'").getResultList();
                if (!postingDateList.isEmpty()) {
                    Vector postElement = (Vector) postingDateList.get(0);
                    postDate = (String) postElement.get(0);
                }
                l_result = "";
                float recno = 0;
                l_result = ftsPosting.insertRecons("PO", glclgret, 0, ret_amt, Tempbd, postDate, 1, "Returned Cheque Amount", AuthBy, glclg_trsno, null,
                        recno, "Y", AuthBy, 0, 3, null, null, tokenNo, null, "A", 1, null, null, null, null, brCode, glclCurCode, 0, null, "", "");

                if (!l_result.equals("TRUE")) {
                    throw new ApplicationException("Problem in Insertion in recons :" + l_result);
                }
                l_result = "";
                recNo = ftsPosting.getRecNo();
                Query insertQuery7 = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,cramt,ty,trantype,recno,trsno,auth,enterby,"
                        + "authby,details,org_brnid,dest_brnid,valueDt)"
                        + "values ('" + glclgret + "','" + acnames + "','" + Tempbd + "'," + ret_amt + "," + 0 + "," + 1 + ","
                        + recNo + "," + trsNo + ",'Y','" + AuthBy + "','" + AuthBy + "','Returned Cheque Amount','" + brCode + "','"
                        + glclCurCode + "','" + postDate + "')");
                var7 = insertQuery7.executeUpdate();
                if (var7 < 0) {
                    throw new ApplicationException("Insert Problem in Recon_Clg_d for Ac No" + glclgret);
                }

                for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();
                    List kLst = (List) entry.getValue();
                    String sGl = kLst.get(0).toString();
                    double nVal = Double.parseDouble(kLst.get(1).toString());
                    if (nVal > 0) {

                        // Old UnAuthorized Copde
//                        l_result = insertTransfer("PO", sGl, 0, nVal, Tempbd, 2, "Cheque Return Chg.", AuthBy, trsNo, null, 0, "N",
//                            null, 0, 3, "", 0, null, "A", 1, null, null, null, null, null, null, 0, null, brCode, Tempbd);
//                        if (!l_result.equals("TRUE")) {
//                            throw new ApplicationException("Problem in Insertion in recons :" + l_result);
//                        }
                        // New Authorized Copde
                        recNo = ftsPosting.getRecNo();
                        String glTRecon = ftsPosting.insertRecons("PO", sGl, 0, nVal, Tempbd, Tempbd, 2, "Cheque Return Chg.", AuthBy, trsNo, null, recNo, "Y", "SYSTEM",
                                109, 3, "", null, tokenNo, null, "A", 1, null, null, null, null, brCode, brCode, 0, null, "", "");

                        if (!glTRecon.equals("TRUE")) {
                            throw new ApplicationException("Problem in Insertion in recons :" + sGl);
                        }

                        Query insertSGl = em.createNativeQuery("insert into recon_trf_d( acno , custname, dt, Dramt, CrAmt, Ty, TranType,"
                                + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                                + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                                + "values ('" + sGl + "','" + ftsPosting.getCustName(sGl) + "','" + Tempbd + "',0," + nVal + ",0,2," + recNo + ","
                                + trsNo + ",'',3,1,'Y','" + AuthBy + "','SYSTEM', null,109 ," + tokenNo + ",'A', CURRENT_TIMESTAMP,'Cheque Return Chg.',0,null,'" + brCode + "','"
                                + brCode + "','" + Tempbd + "','','')");
                        int varChgRetSGl = insertSGl.executeUpdate();
                        if (varChgRetSGl < 0) {
                            throw new ApplicationException("Insert Problem in recon_trf_d for Ac No:" + sGl);
                        }
                    }
                }

//                double totalRetChgs = retchg * retchq_no;
//                double totalServiceTax = sTax * retchq_no;
//                if (totalRetChgs > 0) {
//                    l_result = insertTransfer("PO", glclgretchg, 0, totalRetChgs, Tempbd, 2, "Cheque Return Chg.", AuthBy, trsNo, null, 0, "N",
//                            null, 0, 3, "", 0, null, "A", 1, null, null, null, null, null, null, 0, null, brCode, Tempbd);
//                    if (!l_result.equals("TRUE")) {
//                        throw new ApplicationException("Problem in Insertion in recons :" + l_result);
//                    }
//                }                
                //For Service tax 
                if (code == 1) {
//                if (totalServiceTax > 0) {
                    map = ibRemote.getTaxComponent(totalRetChgs, Tempbd);
                    Set<Map.Entry<String, Double>> set = map.entrySet();
                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = it.next();
                        String[] keyArray = entry.getKey().toString().split(":");
                        String description = keyArray[0];
                        String taxHead = brCode + keyArray[1];
                        String mainDetails = description.toUpperCase() + " for Cheque Return Chg.";
                        double taxAmount = Double.parseDouble(entry.getValue().toString());

                        // Old UnAuthorized Code
//                        l_result = insertTransfer("PO", taxHead, 0, taxAmount, Tempbd, 2, mainDetails, AuthBy, trsNo, null, 0, "N",
//                                null, 71, 3, "", 0, null, "A", 1, null, null, null, null, null, null, 0, null, brCode, Tempbd);
//                        if (!l_result.equals("TRUE")) {
//                            throw new ApplicationException("Problem in Insertion in recons :" + l_result);
//                        }
                        // New Authorized Copde
                        if (taxAmount > 0) {
                            recNo = ftsPosting.getRecNo();
                            String glTRecon = ftsPosting.insertRecons("PO", taxHead, 0, taxAmount, Tempbd, Tempbd, 2, mainDetails, AuthBy, trsNo, null, recNo, "Y", "SYSTEM",
                                    71, 3, "", null, tokenNo, null, "A", 1, null, null, null, null, brCode, brCode, 0, null, "", "");

                            if (!glTRecon.equals("TRUE")) {
                                throw new ApplicationException("Problem in Insertion in recons :" + l_result);
                            }

                            Query insertRetChgs = em.createNativeQuery("insert into recon_trf_d( acno , custname, dt, Dramt, CrAmt, Ty, TranType,"
                                    + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                                    + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                                    + "values ('" + taxHead + "','" + ftsPosting.getCustName(taxHead) + "','" + Tempbd + "',0," + taxAmount + ",0,2," + recNo + ","
                                    + trsNo + ",'',3,1,'Y','" + AuthBy + "','SYSTEM',null,71 ," + tokenNo + ",'A', CURRENT_TIMESTAMP,'" + mainDetails + "',0,null,'" + brCode + "','"
                                    + brCode + "','" + Tempbd + "','','')");
                            int varChgRet = insertRetChgs.executeUpdate();
                            if (varChgRet < 0) {
                                throw new ApplicationException("Insert Problem in recon_trf_d for Ac No:" + taxHead);
                            }
                        }
                    }
                    if (totalRetChgsIgst > 0) {
                        map = ibRemote.getIgstTaxComponent(totalRetChgsIgst, Tempbd);
                        set = map.entrySet();
                        it = set.iterator();
                        while (it.hasNext()) {
                            Map.Entry entry = it.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = brCode + keyArray[1];
                            String mainDetails = description.toUpperCase() + " for Cheque Return Chg.";
                            double taxAmount = Double.parseDouble(entry.getValue().toString());

                            //Old UnAuthorized Code 
//                            l_result = insertTransfer("PO", taxHead, 0, taxAmount, Tempbd, 2, mainDetails, AuthBy, trsNo, null, 0, "N",
//                                    null, 71, 3, "", 0, null, "A", 1, null, null, null, null, null, null, 0, null, brCode, Tempbd);
//                            if (!l_result.equals("TRUE")) {
//                                throw new ApplicationException("Problem in Insertion in recons :" + l_result);
//                            }
                            // New Authorized Copde
                            recNo = ftsPosting.getRecNo();
                            String glTRecon = ftsPosting.insertRecons("PO", taxHead, 0, taxAmount, Tempbd, Tempbd, 2, mainDetails, AuthBy, trsNo, null, recNo, "Y", "SYSTEM",
                                    71, 3, "", null, tokenNo, null, "A", 1, null, null, null, null, brCode, brCode, 0, null, "", "");

                            if (!glTRecon.equals("TRUE")) {
                                throw new ApplicationException("Problem in Insertion in recons :" + taxHead);
                            }

                            Query insertRetChgsIgst = em.createNativeQuery("insert into recon_trf_d( acno , custname, dt, Dramt, CrAmt, Ty, TranType,"
                                    + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                                    + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                                    + "values ('" + taxHead + "','" + ftsPosting.getCustName(taxHead) + "','" + Tempbd + "',0," + taxAmount + ",0,2," + recNo + ","
                                    + trsNo + ",'',3,1,'Y','" + AuthBy + "','SYSTEM',null,71 ," + tokenNo + ",'A', CURRENT_TIMESTAMP,'" + mainDetails + "',0,null,'" + brCode + "','"
                                    + brCode + "','" + Tempbd + "','','')");
                            int varChgRetIgst = insertRetChgsIgst.executeUpdate();
                            if (varChgRetIgst < 0) {
                                throw new ApplicationException("Insert Problem in recon_trf_d for Ac No:" + taxHead);
                            }
                        }
                    }
                }
            }
            Query insertQuery = em.createNativeQuery("insert into clg_ow_history(Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,Txnstatus,"
                    + "TxnInstDate,TxnBankName,TxnBankAddress,reasonforcancel,EMFlag ,BillType,AlphaCode,BcBpNo,Fyear,Sbal,obcflag,dt,vtot,"
                    + "orgnBrCode,destbrcode,AreaCode,BnkCode,BranchCode,SanNo,TransactionCode,AccountName,CtsReturnCode) select Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,'C',TxnInstDate,TxnBankName,"
                    + "TxnBankAddress,reasonforcancel,EMFlag ,BillType,AlphaCode,BcBpNo,Fyear,Sbal,obcflag,dt,vtot,orgnBrCode,destbrcode,AreaCode,BnkCode,BranchCode,SanNo,TransactionCode,AccountName,CtsReturnCode "
                    + "from clg_ow_shadowbal where txndate='" + txnDate + "' and emflag='" + emFlag + "' and txnstatus='P' and "
                    + "ReasonForCancel = 0 and orgnBrCode='" + brCode + "'");
            int var8 = insertQuery.executeUpdate();
            if ((var8 < 0) && (Data_Flag_passed.equals("TRUE"))) {
                throw new ApplicationException("Insertion Problem in History Table");
            }

            Query insertQuery1 = em.createNativeQuery("insert into clg_returnedchq (Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,"
                    + "Txnstatus,TxnAuthBy,TxnInstDate,TxnBankName,TxnBankAddress,reasonforcancel,EMFlag ,BillType,AlphaCode,BcBpNo,Fyear,"
                    + "Sbal,obcflag,vtot,orgnbrcode,destbrcode,SanNo,TransactionCode,AccountName,CtsReturnCode) select s.Acno,s.TxnMode,s.TxnType,s.TxnDate,s.TxnInstNo,s.TxnInstAmt,'R','"
                    + AuthBy + "',s.TxnInstDate,s.TxnBankName,s.TxnBankAddress,ifnull(c.description,''),s.EMFlag ,s.BillType,"
                    + "s.AlphaCode,s.BcBpNo,s.Fyear,s.Sbal,s.obcflag,s.vtot,s.orgnBrCode,s.destbrcode,s.SanNo,s.TransactionCode,s.AccountName,CtsReturnCode from clg_ow_shadowbal s left join "
                    + "codebook c on s.reasonforcancel=c.code and c.groupcode=13 where s.orgnBrCode='" + brCode + "' and s.reasonforcancel <> 0"
                    + " and s.TxnDate='" + txnDate + "' and s.billtype is null");
            int var9 = insertQuery1.executeUpdate();
            if ((var9 < 0) && (retchq_no > 0)) {
                throw new ApplicationException("Insertion Problem in Clg_RetunedChq Table");
            }

            Query delQuery = em.createNativeQuery("delete from clg_todayreturnedchq where orgnbrcode = '" + brCode + "' and txndate = '" + txnDate + "'");
            int rt = delQuery.executeUpdate();
            if (rt < 0) {
                throw new ApplicationException("Deletion problem in CLG_TODAYRETURNEDCHQ table.");
            }
            Query insertQuery2 = em.createNativeQuery("insert into bill_bpbcreturn(Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,Txnstatus,"
                    + "TxnInstDate,TxnBankName,TxnBankAddress,reasonforcancel,EMFlag,BillType,AlphaCode,BcBpNo,Fyear) select s.Acno,s.TxnMode,"
                    + "s.TxnType,s.TxnDate,s.TxnInstNo,s.TxnInstAmt,'R',s.TxnInstDate,s.TxnBankName,s.TxnBankAddress,c.description,"
                    + "s.EMFlag,s.BillType,s.AlphaCode,s.BcBpNo,s.Fyear from clg_ow_shadowbal s left join codebook c on "
                    + "s.reasonforcancel=c.code and c.groupcode=13 where s.orgnBrCode='" + brCode + "' and s.reasonforcancel <> 0 "
                    + "and billtype is not null and s.TxnDate='" + txnDate + "'");
            int var10 = insertQuery2.executeUpdate();
            if ((var10 < 0) && (Data_Bill_Flag_returned.equals("TRUE"))) {
                throw new ApplicationException("Insertion Problem in Bill_bpbcreturn Table");
            }

            Query deleteQuery = em.createNativeQuery("delete from clg_ow_shadowbal where txndate='" + txnDate + "' and emflag='" + emFlag + "' "
                    + "and txnstatus='P' and orgnBrcode='" + brCode + "'");
            int var11 = deleteQuery.executeUpdate();
            if ((var11 <= 0) && ((Data_Flag_passed.equals("TRUE")) || (Data_Bill_Flag_returned.equals("TRUE")) || (ret_amt > 0))) {
                throw new ApplicationException("deletion Problem of Clg_Ow_ShadowBal Table");
            }

            List statusList = em.createNativeQuery("select Txnstatus From clg_ow_shadowbal where Txnstatus='H' and TxnDate='" + txnDate
                    + "' and EmFlag='" + emFlag + "' and orgnBrcode='" + brCode + "'").getResultList();
            if (!statusList.isEmpty()) {
                Query updateQuery = em.createNativeQuery("UPDATE clg_ow_register set STATUS='Held'  where ENTRYDATE='" + txnDate
                        + "' AND emflag='" + emFlag + "' and brncode='" + brCode + "'");
                updateQuery.executeUpdate();
            } else {
                Query updateQuery = em.createNativeQuery("UPDATE clg_ow_register set STATUS='Cleared',Clearedby ='" + AuthBy
                        + "' where ENTRYDATE='" + txnDate + "' AND emflag='" + emFlag + "' and brncode='" + brCode + "'");
                updateQuery.executeUpdate();
            }
            if (!((Data_Flag_passed.equals("TRUE")) || (Data_Bill_Flag_returned.equals("TRUE")) || (ret_amt > 0))) {
                throw new ApplicationException("No Data Exists for Clearing");
            }
            ut.commit();
            //Sending Sms
            try {
                //Pass Instrument Sms.
                smsFacade.sendSmsToBatch(smsBatchList);
                //Returned Instrument Sms.
                if (!smsRequestToList.isEmpty()) {
                    List<MbSmsSenderBankDetailTO> bankTo = smsFacade.getBankAndSenderDetail();
                    String templateBankName = bankTo.get(0).getTemplateBankName().trim();
                    for (TransferSmsRequestTo requestTo : smsRequestToList) {
                        requestTo.setBankName(templateBankName);
                        smsFacade.sendSms(requestTo);
                    }
                }
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Transfer Authorization." + e.getMessage());
            }
            //End here.
            return "TRUE";
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                ut.rollback();
                smsBatchList = new ArrayList<SmsToBatchTo>();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public String insertTransfer(String acctNature, String acno, int ty, Double amt, String dt, int TranType, String details, String enterby,
            float trsNo, String trantime, float recNo, String auth, String authby, int trandesc, int payby, String instrNo, float TokenNo,
            String tokenPaidBy, String SubTokenNo, int iy, String tdacno, Float voucherno, String intflag, String closeflag, String org_brnid,
            String dest_brnid, int tran_id, String term_id, String brCode, String valueDt) throws ApplicationException {

        Double cramt = 0.0d;
        Double dramt = 0.0d;
        int var20 = 0;
        try {
            if (trantime == null) {
                trantime = "19000101";
                List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' And Brncode  = '" + brCode + "'").getResultList();
                Vector tempCurrent = (Vector) tempBd.get(0);
                trantime = tempCurrent.get(0).toString();
                String curCode = ftsPosting.getCurrentBrnCode(acno);
                if (ty == 0) {
                    cramt = amt;
                    dramt = 0.0d;
                } else if (ty == 1) {
                    cramt = 0.0d;
                    dramt = amt;
                } else {
                    return "Invalid Transaction Mode for Account No :-" + acno;
                }

                if (recNo == 0) {
                    //recNo = ftsPosting.getRecNo(acno.substring(0,2));
                    recNo = ftsPosting.getRecNo();
                }

                Query insertQuery20 = em.createNativeQuery("insert into recon_trf_d( acno , custname, dt, Dramt, CrAmt, Ty, TranType,"
                        + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                        + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                        + "values ('" + acno + "','" + ftsPosting.getCustName(acno) + "','" + trantime + "'," + dramt + "," + cramt + "," + ty + "," + TranType + "," + recNo + ","
                        + trsNo + ",'" + instrNo + "'," + payby + "," + iy + ",'" + auth + "','" + enterby + "','" + authby + "','" + tokenPaidBy + "',"
                        + trandesc + "," + TokenNo + ",'" + SubTokenNo + "', CURRENT_TIMESTAMP,'" + details + "'," + tran_id + ",'" + term_id + "','" + brCode + "','"
                        + curCode + "','" + valueDt + "','','')");
                var20 = insertQuery20.executeUpdate();

            }

            if (var20 > 0) {
                return "TRUE";
            } else {
                return "Insertion Problem in Recon_Trf_D for A/c No :- " + acno;
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String registerCboDateLostFocus(
            String emFlag, String registerCboDate, String registerCboDate1, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String clearingDate = "";
            String status;
            String orderBy;
            String clearDate;

            List tempBd = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG = 'N' And Brncode  = '" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            List clearingDtList = em.createNativeQuery("Select ClearingDate,Status from clg_ow_register Where EntryDate='"
                    + registerCboDate + "' AND emflag='" + emFlag + "' and brncode='" + brCode + "'").getResultList();
            if (clearingDtList.isEmpty()) {
                ut.rollback();
                return "Sorry!The Clearing Date for this Register has not been Marked.";
            } else {
                Vector recLst2 = (Vector) clearingDtList.get(0);
                clearingDate = recLst2.get(0).toString();
                clearDate = clearingDate.substring(0, 4) + "" + clearingDate.substring(5, 7) + "" + clearingDate.substring(8, 10);
                status = recLst2.get(1).toString();
            }

            if (!clearDate.equalsIgnoreCase(Tempbd)) {
                ut.rollback();
                return "This Register cannot be cleared in this Date.";
            }

            if (status.equalsIgnoreCase("CLEARED")) {
                ut.rollback();
                return "This Register is already Cleared.";
            }

            if ((!status.equalsIgnoreCase("POSTED")) && (!status.equalsIgnoreCase("HELD"))) {
                ut.rollback();
                return "This Register is not yet posted.";
            }

            if (registerCboDate1.equalsIgnoreCase("Acno")) {
                orderBy = "Acno";
            } else if (registerCboDate1.equalsIgnoreCase("Inst No")) {
                orderBy = "txninstno,txnbankname";
            } else if (registerCboDate1.equalsIgnoreCase("Voucher No")) {
                orderBy = "vtot,acno";
            } else {
                orderBy = "Acno";
            }

            ut.commit();
            return orderBy + ": " + clearDate;

        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    /**
     *
     * @param emFlag
     * @param registerCboDate
     * @param registerCboDate1
     * @param brCode
     * @return
     */
    public List getTableData(String emFlag, String registerCboDate, String registerCboDate1, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List tableResult;
        try {
            ut.begin();
//            System.out.println("Select Txnstatus,Acno,date_format(TxnInstDate,'%d/%m/%Y') TxnInstDate,TxnInstNo,"
//                    + "TxnInstAmt,ifnull(TxnBankName,''),remarks,coalesce(vtot,0),TxnBankAddress from clg_ow_shadowbal "
//                    + "where orgnBrcode='" + brCode + "' and txndate='" + registerCboDate + "' and emflag='" + emFlag
//                    + "' and reasonforcancel = 0 order by " + registerCboDate1 + "");
//            tableResult = em.createNativeQuery("Select Txnstatus,Acno,date_format(TxnInstDate,'%d/%m/%Y') TxnInstDate,TxnInstNo,"
//                    + "TxnInstAmt,ifnull(TxnBankName,''),remarks,coalesce(vtot,0),TxnBankAddress from clg_ow_shadowbal "
//                    + "where orgnBrcode='" + brCode + "' and txndate='" + registerCboDate + "' and emflag='" + emFlag
//                    + "' and reasonforcancel = 0 order by " + registerCboDate1 + "").getResultList();
            String query = "select *,ifnull((select description from codebook where groupcode =13 and t.CtsReturnCode = code),'')as returnDesc from (Select Txnstatus,Acno,date_format(TxnInstDate,'%d/%m/%Y') TxnInstDate,TxnInstNo,TxnInstAmt,ifnull(TxnBankName,''),remarks,"
                    + "coalesce(vtot,0),TxnBankAddress,ifnull(CtsReturnCode,'') as CtsReturnCode  from clg_ow_shadowbal where orgnBrcode='" + brCode + "' and txndate='" + registerCboDate + "' and emflag='" + emFlag + "'"
                    + " and reasonforcancel = 0 order by " + registerCboDate1 + ")as t";
            System.out.println("Query : " + query);
            tableResult = em.createNativeQuery(query).getResultList();
            ut.commit();
            return tableResult;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    /**
     *
     * @param registerCboDate
     * @param brCode
     * @return
     */
    public List getBankNameTableData(String registerCboDate, String brCode, String hOpt) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            if (hOpt.equalsIgnoreCase("HB")) {
                Query selectQuery = em.createNativeQuery("Select Distinct TxnBankName From clg_ow_shadowbal  Where "
                        + "Ltrim(Rtrim(Upper(Txnstatus)))='H' and TxnDate = '" + registerCboDate + "' and orgnbrcode='" + brCode + "'");
                tableResult = selectQuery.getResultList();
            } else if (hOpt.equalsIgnoreCase("BR")) {
                Query selectQuery = em.createNativeQuery("Select Distinct concat(areacode,bnkcode,BranchCode) From clg_ow_shadowbal  Where "
                        + "Ltrim(Rtrim(Upper(Txnstatus)))='H' and TxnDate = '" + registerCboDate + "' and orgnbrcode='" + brCode + "'");
                tableResult = selectQuery.getResultList();
            } else if (hOpt.equalsIgnoreCase("HC")) {
                Query selectQuery = em.createNativeQuery("Select Distinct AreaCode From clg_ow_shadowbal  Where "
                        + "Ltrim(Rtrim(Upper(Txnstatus)))='H' and TxnDate = '" + registerCboDate + "' and orgnbrcode='" + brCode + "'");
                tableResult = selectQuery.getResultList();
            } else if (hOpt.equalsIgnoreCase("CB")) {
                Query selectQuery = em.createNativeQuery("Select Distinct concat(areacode,bnkcode) From clg_ow_shadowbal  Where "
                        + "Ltrim(Rtrim(Upper(Txnstatus)))='H' and TxnDate = '" + registerCboDate + "' and orgnbrcode='" + brCode + "'");
                tableResult = selectQuery.getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }

        return tableResult;
    }

    public String gridbankDblClick(String emFlags, String registerCboDate, String gridValue, String brCode, String hOpt)
            throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            int var = 0;
            ut.begin();
            if (emFlags.equalsIgnoreCase("CIRCLE A - MICR")) {
                emFlags = "A";
            } else if (emFlags.equalsIgnoreCase("CIRCLE A - NON MICR")) {
                emFlags = "B";
            }

            if (hOpt.equalsIgnoreCase("HB")) {
                Query InsertQuery = em.createNativeQuery("Update clg_ow_shadowbal set txnstatus='P' where txndate='" + registerCboDate
                        + "' and emflag='" + emFlags + "' and TxnBankName = '" + gridValue + "' and orgnbrcode='" + brCode + "'");
                var = InsertQuery.executeUpdate();
            } else if (hOpt.equalsIgnoreCase("HC")) {
                Query InsertQuery = em.createNativeQuery("Update clg_ow_shadowbal set txnstatus='P' where txndate='" + registerCboDate
                        + "' and emflag='" + emFlags + "' and AreaCode = '" + gridValue + "' and orgnbrcode='" + brCode + "'");
                var = InsertQuery.executeUpdate();
            } else if (hOpt.equalsIgnoreCase("BR")) {
                Query InsertQuery = em.createNativeQuery("Update clg_ow_shadowbal set txnstatus='P' where txndate='" + registerCboDate
                        + "' and emflag='" + emFlags + "' and areacode = '" + gridValue.substring(0, 3) + "' and bnkcode = '" + gridValue.substring(3, 6) + "' "
                        + " and BranchCode = '" + gridValue.substring(6) + "' and orgnbrcode='" + brCode + "'");
                var = InsertQuery.executeUpdate();
            } else if (hOpt.equalsIgnoreCase("CB")) {
                Query InsertQuery = em.createNativeQuery("Update clg_ow_shadowbal set txnstatus='P' where txndate='" + registerCboDate
                        + "' and emflag='" + emFlags + "' and areacode = '" + gridValue.substring(0, 3) + "' and bnkcode = '" + gridValue.substring(3, 6) + "' "
                        + " and orgnbrcode='" + brCode + "'");
                var = InsertQuery.executeUpdate();
            }

            if (var > 0) {
                ut.commit();
                return "Release Successfully";
            } else {
                ut.rollback();
                return "";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String status() throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List desList = em.createNativeQuery("select ifnull(description,'Returned') from codebook where groupcode=13 and  code=0").getResultList();
            Vector desLists = (Vector) desList.get(0);
            String description = desLists.get(0).toString();
            ut.commit();
            return description;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String currentDate(String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List tempBd = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG = 'N' And Brncode  = '" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            ut.commit();
            return Tempbd;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String loadDate(String emFlag, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List arraylist = new ArrayList();
            List dateList = em.createNativeQuery("select distinct txndate from clg_ow_shadowbal  where emflag='" + emFlag
                    + "' and orgnBrcode='" + brCode + "' order by txndate asc").getResultList();
            if (!dateList.isEmpty()) {
                for (int i = 0; i < dateList.size(); i++) {
                    Vector dateLists = (Vector) dateList.get(i);
                    String txnDate = dateLists.get(0).toString();
                    String yy = txnDate.substring(0, 4);
                    String mm = txnDate.substring(5, 7);
                    String dd = txnDate.substring(8, 10);
                    String txnDates = dd + "/" + mm + "/" + yy;
                    arraylist.add(txnDates);
                }

            }
            if (dateList.isEmpty()) {
                ut.rollback();
                return "Register Date has not been set for this Clearing Mode.";
            } else {
                ut.commit();
                return arraylist.toString();
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String cmbBankNameValueChangeClearance(String emFlags, String registerCboDate, String bankName, String brCode, String hOpt)
            throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
            if (hOpt.equalsIgnoreCase("HB")) {
                List bankList1 = em.createNativeQuery("select txnbankname,Txnstatus  from clg_ow_shadowbal  "
                        + "where BNKCODE= '" + bankName + "' and ReasonForcancel not in (0) AND emflag='" + emFlags + "' "
                        + "and txndate='" + registerCboDate + "' and orgnbrcode='" + brCode + "'").getResultList();
                if (!bankList1.isEmpty()) {
                    return "You have been already returned a cheque of this bank so you can not held this bank.";
                }
                List bankList = em.createNativeQuery("select txnbankname,Txnstatus  from clg_ow_shadowbal  "
                        + "where BNKCODE= '" + bankName + "' and ReasonForcancel in (0) AND emflag='" + emFlags + "' "
                        + "and txndate='" + registerCboDate + "' and orgnbrcode='" + brCode + "'").getResultList();
                if (bankList.isEmpty()) {
                    return "No Cheques For clearing of this Bank";
                } else if (!bankList.isEmpty()) {
                    Vector bankLists = (Vector) bankList.get(0);
                    String Txnstatus = bankLists.get(1).toString();
                    if (Txnstatus.equals("H")) {
                        return "This Bank's Clearing is already marked Held.";
                    }
                }
                ut.begin();
                Query InsertQuery = em.createNativeQuery("update clg_ow_shadowbal set txnstatus='H' where "
                        + "bnkcode= '" + bankName + "' and txndate='" + registerCboDate + "' and orgnbrcode='" + brCode + "' AND "
                        + "emflag='" + emFlags + "'");
                int var = InsertQuery.executeUpdate();
                if (var > 0) {
                    ut.commit();
                    msg = "Held Successfully";
                } else {
                    ut.rollback();
                }
            } else if (hOpt.equalsIgnoreCase("HC")) {
                List bankList1 = em.createNativeQuery("select txnbankname,Txnstatus  from clg_ow_shadowbal  "
                        + "where AreaCode= '" + bankName + "' and ReasonForcancel not in (0) AND emflag='" + emFlags + "' "
                        + "and txndate='" + registerCboDate + "' and orgnbrcode='" + brCode + "'").getResultList();
                if (!bankList1.isEmpty()) {
                    return "You have been already returned a cheque of this City so you can not held this City.";
                }
                List bankList = em.createNativeQuery("select AreaCode,Txnstatus  from clg_ow_shadowbal  "
                        + "where AreaCode= '" + bankName + "' and ReasonForcancel in (0) AND emflag='" + emFlags + "' "
                        + "and txndate='" + registerCboDate + "' and orgnbrcode='" + brCode + "'").getResultList();
                if (bankList.isEmpty()) {
                    return "No Cheques For clearing of this City";
                } else if (!bankList.isEmpty()) {
                    Vector bankLists = (Vector) bankList.get(0);
                    String Txnstatus = bankLists.get(1).toString();
                    if (Txnstatus.equals("H")) {
                        return "This City's Clearing is already marked Held.";
                    }
                }
                ut.begin();
                Query InsertQuery = em.createNativeQuery("update clg_ow_shadowbal set txnstatus='H' where "
                        + "AreaCode= '" + bankName + "' and txndate='" + registerCboDate + "' and orgnbrcode='" + brCode + "' AND "
                        + "emflag='" + emFlags + "'");
                int var = InsertQuery.executeUpdate();
                if (var > 0) {
                    ut.commit();
                    msg = "Held Successfully";
                } else {
                    ut.rollback();
                }
            } else if (hOpt.equalsIgnoreCase("BR")) {
                List bankList1 = em.createNativeQuery("select txnbankname,Txnstatus from clg_ow_shadowbal where "
                        + "AreaCode = '" + bankName.substring(0, 3) + "' and BnkCode = '" + bankName.substring(3, 6) + "' and "
                        + "BranchCode = '" + bankName.substring(6) + "' and ReasonForcancel not in (0) AND "
                        + "emflag='" + emFlags + "' and txndate='" + registerCboDate + "' and "
                        + "orgnbrcode='" + brCode + "'").getResultList();
                if (!bankList1.isEmpty()) {
                    return "You have been already returned a cheque of this Branch so you can not held this Branch.";
                }
                List bankList = em.createNativeQuery("select BranchCode,Txnstatus from clg_ow_shadowbal where "
                        + "AreaCode = '" + bankName.substring(0, 3) + "' and BnkCode = '" + bankName.substring(3, 6) + "' and "
                        + "BranchCode = '" + bankName.substring(6) + "' and ReasonForcancel in (0) AND emflag='" + emFlags + "' and "
                        + "txndate='" + registerCboDate + "' and orgnbrcode='" + brCode + "'").getResultList();
                if (bankList.isEmpty()) {
                    return "No Cheques For clearing of this Branch";
                } else if (!bankList.isEmpty()) {
                    Vector bankLists = (Vector) bankList.get(0);
                    String Txnstatus = bankLists.get(1).toString();
                    if (Txnstatus.equals("H")) {
                        return "This Branch's Clearing is already marked Held.";
                    }
                }
                ut.begin();
                Query InsertQuery = em.createNativeQuery("update clg_ow_shadowbal set txnstatus='H' where "
                        + "AreaCode = '" + bankName.substring(0, 3) + "' and BnkCode = '" + bankName.substring(3, 6) + "' and "
                        + "BranchCode = '" + bankName.substring(6) + "' and txndate='" + registerCboDate + "' and "
                        + "orgnbrcode='" + brCode + "' AND emflag='" + emFlags + "'");
                int var = InsertQuery.executeUpdate();
                if (var > 0) {
                    ut.commit();
                    msg = "Held Successfully";
                } else {
                    ut.rollback();
                }
            } else if (hOpt.equalsIgnoreCase("CB")) {
                List bankList1 = em.createNativeQuery("select txnbankname,Txnstatus  from clg_ow_shadowbal where "
                        + "AreaCode='" + bankName.substring(0, 3).trim() + "' and BnkCode= '" + bankName.substring(3).trim() + "' and "
                        + "ReasonForcancel not in (0) AND emflag='" + emFlags + "' and txndate='" + registerCboDate + "' and "
                        + "orgnbrcode='" + brCode + "'").getResultList();
                if (!bankList1.isEmpty()) {
                    return "You have been already returned a cheque of this Branch so you can not held this Branch.";
                }
                List bankList = em.createNativeQuery("select BranchCode,Txnstatus  from clg_ow_shadowbal where "
                        + "AreaCode='" + bankName.substring(0, 3).trim() + "' and BnkCode= '" + bankName.substring(3).trim() + "' and "
                        + "ReasonForcancel in (0) AND emflag='" + emFlags + "' and txndate='" + registerCboDate + "' and "
                        + "orgnbrcode='" + brCode + "'").getResultList();
                if (bankList.isEmpty()) {
                    return "No Cheques For clearing of this Branch";
                } else if (!bankList.isEmpty()) {
                    Vector bankLists = (Vector) bankList.get(0);
                    String Txnstatus = bankLists.get(1).toString();
                    if (Txnstatus.equals("H")) {
                        return "This Bank's Clearing is already marked Held.";
                    }
                }
                ut.begin();
                Query InsertQuery = em.createNativeQuery("update clg_ow_shadowbal set txnstatus='H' where "
                        + "AreaCode='" + bankName.substring(0, 3).trim() + "' and BnkCode= '" + bankName.substring(3).trim() + "' and "
                        + "txndate='" + registerCboDate + "' and orgnbrcode='" + brCode + "' AND emflag='" + emFlags + "'");
                int var = InsertQuery.executeUpdate();
                if (var > 0) {
                    ut.commit();
                    msg = "Held Successfully";
                } else {
                    ut.rollback();
                }
            }
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String cboReasonKeyDown(String acno, String instNo, float seqNo, String bankName, String instDt, String brCode)
            throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String multipleMsg = "";
            if ((instNo == null) || (instNo.equalsIgnoreCase(""))) {
                ut.rollback();
                return "";
            }

            if ((instDt == null) || (instDt.equalsIgnoreCase(""))) {
                ut.rollback();
                return "";
            }

            if ((acno == null) || (acno.equalsIgnoreCase(""))) {
                ut.rollback();
                return "";
            }

            if ((bankName == null) || (bankName.equalsIgnoreCase(""))) {
                ut.rollback();
                return "";
            }

            List acnoList = em.createNativeQuery("select postflag from gltable where acno='" + acno
                    + "' and substring(AcNo,1,2)='" + brCode + "'").getResultList();
            if (!acnoList.isEmpty()) {
                Vector acnoLists = (Vector) acnoList.get(0);
                String clgPostflag = acnoLists.get(0).toString();
                if (clgPostflag.equals("11")) {
                    if (seqNo <= 0) {
                        ut.rollback();
                        return "Please Enter Sequence Number";
                    }

                }
            }
            if ((bankName == null) || (bankName.equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please  Bank Name";
            }

            List multipleList = em.createNativeQuery("select count(txninstno) from clg_ow_shadowbal where txninstno='" + instNo
                    + "' and txninstdate = '" + instDt + "' and txnBankname='" + bankName + "' and orgnbrcode='" + brCode + "'").getResultList();
            if (!multipleList.isEmpty()) {
                Vector multipleLists = (Vector) multipleList.get(0);
                int countInstNo = Integer.parseInt(multipleLists.get(0).toString());
                if (countInstNo > 1) {
                    multipleMsg = "There are more instance exist of this instrument. Please mark these instruments as returned (if required)";
                }
            }
            ut.commit();
            return multipleMsg;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String getTableDataReasonForReturn(String emFlag, String acno, String instNo, String instDate, String bankName,
            String cboReason, float seqNo, float instAmount, int txtYear, String brCode, int vtot)
            throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if ((emFlag == null) || (emFlag.equalsIgnoreCase(""))) {
                ut.rollback();
                return "";
            } else if (emFlag.equals("--SELECT--")) {
                ut.rollback();
                return "";
            }
            if (!String.valueOf(vtot).equals("0")) {
                List codeBookList = em.createNativeQuery("Select * from codebook where groupcode=13 and code='" + Integer.parseInt(cboReason) + "'").getResultList();
                if (codeBookList.isEmpty()) {
                    Query updateClgQuery = em.createNativeQuery("Update clg_ow_shadowbal set reasonforcancel= 13  where txninstno='"
                            + instNo + "' and txninstdate = '" + instDate + "'  and txnBankname='" + bankName + "' and orgnBrcode='"
                            + brCode + "' and vtot=" + vtot + "");
                    updateClgQuery.executeUpdate();
                } else {
                    Vector codeBookLists = (Vector) codeBookList.get(0);
                    int code = Integer.parseInt(codeBookLists.get(1).toString());
                    Query updateClgQuery = em.createNativeQuery("Update clg_ow_shadowbal set reasonforcancel=" + code
                            + "  where txninstno='" + instNo + "' and txninstdate = '" + instDate + "'  and txnBankname='" + bankName
                            + "' and orgnBrcode='" + brCode + "' and vtot=" + vtot + "");
                    updateClgQuery.executeUpdate();
                }

            } else {
                List codeBookList = em.createNativeQuery("Select * from codebook where groupcode=13 and code='" + Integer.parseInt(cboReason) + "'").getResultList();
                if (codeBookList.isEmpty()) {
                    Query updateClgQuery = em.createNativeQuery("Update clg_ow_shadowbal set reasonforcancel= 13  where txninstno='"
                            + instNo + "' and txninstdate = '" + instDate + "'  and txnBankname='" + bankName + "' and orgnBrcode='"
                            + brCode + "'");
                    updateClgQuery.executeUpdate();
                } else {
                    Vector codeBookLists = (Vector) codeBookList.get(0);
                    int code = Integer.parseInt(codeBookLists.get(1).toString());
                    Query updateClgQuery = em.createNativeQuery("Update clg_ow_shadowbal set reasonforcancel=" + code
                            + "  where txninstno='" + instNo + "' and txninstdate = '" + instDate + "'  and txnBankname='" + bankName
                            + "' and orgnBrcode='" + brCode + "'");
                    updateClgQuery.executeUpdate();
                }

            }
            List acnoList = em.createNativeQuery("select postflag from gltable where acno='" + acno + "'").getResultList();
            if (!acnoList.isEmpty()) {
                Vector acnoLists = (Vector) acnoList.get(0);
                String clgPostflag = acnoLists.get(0).toString();
                if (clgPostflag.equals("11")) {
                    Query updateClgQuery = em.createNativeQuery("update bill_sundry set dt='" + instDate + "',ty=1,status='PAID' "
                            + "WHERE ACNO='" + acno + "' AND TY=0 AND SEQNO=" + seqNo + " AND fyear='" + txtYear + "' AND AMOUNT="
                            + instAmount + "");
                    updateClgQuery.executeUpdate();

                    Query insertQuery = em.createNativeQuery("Insert into clg_todayreturnedchq(Acno,TxnMode,TxnType,TxnDate,TxnInstNo,"
                            + "TxnInstAmt,Txnstatus,txnauthby,TxnInstDate,TxnBankName,TxnBankAddress,emflag,BillType,AlphaCode,BcBpNo,Fyear,"
                            + "OBCFLAG,VTot,ReasonForCancel,iy,orgnbrcode,destbrcode,SanNo,TransactionCode,AccountName,CtsReturnCode) select Acno,TxnMode,TxnType,txndate,TxnInstNo,TxnInstAmt,"
                            + "Txnstatus,enterby,TxnInstDate,TxnBankName,TxnBankAddress,emflag,BillType,AlphaCode,BcBpNo,Fyear,obcflag,"
                            + vtot + ",'" + Integer.parseInt(cboReason) + "'," + seqNo + ",orgnbrcode,destbrcode,SanNo,TransactionCode,AccountName,CtsReturnCode from clg_ow_shadowbal where acno='" + acno
                            + "' and txninstno='" + instNo + "' and txninstdate = '" + instDate + "' and txnBankname='" + bankName
                            + "' and orgnBrcode='" + brCode + "'");
                    insertQuery.executeUpdate();
                }

            }

            Query insertQuery = em.createNativeQuery("Insert into clg_todayreturnedchq(Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,"
                    + "Txnstatus,txnauthby,TxnInstDate,TxnBankName,TxnBankAddress,emflag,BillType,AlphaCode,BcBpNo,Fyear,OBCFLAG,"
                    + "VTot,ReasonForCancel,orgnbrcode,destbrcode,SanNo,TransactionCode,AccountName,CtsReturnCode) select Acno,TxnMode,TxnType,txndate,TxnInstNo,TxnInstAmt,"
                    + "Txnstatus,enterby,TxnInstDate,TxnBankName,TxnBankAddress,emflag,BillType,AlphaCode,BcBpNo,Fyear,obcflag," + vtot
                    + ",'" + Integer.parseInt(cboReason) + "',orgnbrcode,destbrcode,SanNo,TransactionCode,AccountName,CtsReturnCode from clg_ow_shadowbal where acno='" + acno + "' and txninstno='"
                    + instNo + "' and txninstdate = '" + instDate + "' and txnBankname='" + bankName + "' and orgnBrcode='" + brCode + "'");
            int var4 = insertQuery.executeUpdate();
            if (var4 > 0) {
                ut.commit();
                return "Cheque Returned Successfully";
            } else {
                ut.rollback();
                return "";
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getTxnTableData(String emFlag, String registerCboDate, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            List tempBd = em.createNativeQuery("SELECT cast(date as datetime) FROM bankdays WHERE DAYENDFLAG = 'N' And Brncode  = '" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();

            List allDataList = em.createNativeQuery("select Acno,TxnInstNo,date_format(TxnInstDate,'%d/%m/%Y') TxnInstDate,"
                    + "TxnInstAmt,ifnull(reasonforcancel,'') as reasonforcancel,B.circleDesc,TxnBankName,TxnBankAddress,coalesce(Vtot,0) as Vtot, "
                    + "ifnull(OBCFLAG,'') as OBCFLAG from clg_todayreturnedchq A,  parameterinfo_clg B  where A.emflag = B.circletype and txndate= '"
                    + registerCboDate + "' and txnstatus='P' and emflag='" + emFlag + "' and orgnbrcode='" + brCode + "'").getResultList();
            if (allDataList.size() > 0) {
                ut.begin();
                for (int i = 0; i < allDataList.size(); i++) {
                    Vector allDataLists = (Vector) allDataList.get(i);
                    String Acno = allDataLists.get(0).toString();
                    String txninstno = allDataLists.get(1).toString();
                    String reasonforcancel = allDataLists.get(4).toString();
                    String OBCFLAG = allDataLists.get(9).toString();
                    if ((OBCFLAG.equals("OBC")) || (OBCFLAG.equals("DD")) || (OBCFLAG.equals("PO")) || (OBCFLAG.equals("CHQ"))) {
                        Query InsertQuery = em.createNativeQuery("update bill_obcprocessed set dt='" + Tempbd + "', "
                                + "OBCFlag = 'Returned',REASONFORCANCEL = '" + reasonforcancel + "',status='D' where Instno = '" + txninstno
                                + "' and acno='" + Acno + "'");
                        InsertQuery.executeUpdate();
                    }
                }
                ut.commit();
            }
            return allDataList;

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String grdTxnDblClick(
            String grdTxnInstNo, String grdTxnInstDt, String grdtxnAcno, int grdTxnVtot, float grdTxnAmount, float txtSeqNo, String brCode)
            throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String acCode = ftsPosting.getAccountCode(grdtxnAcno);
            int yyyy = Integer.parseInt(grdTxnInstDt.substring(0, 4));
            Query InsertQuery = em.createNativeQuery("update clg_ow_shadowbal set reasonforcancel=0  where txninstno='" + grdTxnInstNo
                    + "' and txninstdate = '" + grdTxnInstDt + "' and Vtot = " + grdTxnVtot + " and acno='" + grdtxnAcno + "'");
            InsertQuery.executeUpdate();
            if (acCode.equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                List acnoList = em.createNativeQuery("select (coalesce(postflag,'0')) AS postflag from gltable where acno='" + grdtxnAcno
                        + "' and substring(AcNo,1,2)='" + brCode + "'").getResultList();
                Vector acnoLists = (Vector) acnoList.get(0);
                String clgPostflag = acnoLists.get(0).toString();
                if (clgPostflag.equals("11")) {
                    Query updateClgQuery = em.createNativeQuery("Update bill_sundry set dt ='" + grdTxnInstDt + "',ty=0,status='ISSUED' WHERE "
                            + "ACNO='" + grdtxnAcno + "' AND TY=1 AND SEQNO=" + txtSeqNo + " AND fyear='" + yyyy + "' AND AMOUNT=" + grdTxnAmount + "");
                    updateClgQuery.executeUpdate();
                }

            }
            Query deleteQuery = em.createNativeQuery("delete from clg_todayreturnedchq  where txninstno='" + grdTxnInstNo + "' and txninstdate = '" + grdTxnInstDt + "' and Vtot = " + grdTxnVtot + " and orgnbrcode='" + brCode + "'");
            int var2 = deleteQuery.executeUpdate();

            ut.commit();
            return "1";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String maskEdBox1KeyDown(String txnInstNo, String txnInstDate, String emFlag, String cboDate, String Tmpac, String brCode)
            throws ApplicationException {

        try {
            String dd = txnInstDate.substring(0, 2);
            String mm = txnInstDate.substring(3, 5);
            String yy = txnInstDate.substring(6, 10);
            txnInstDate = yy + "" + mm + "" + dd;
            if ((txnInstDate == null) || (txnInstDate.equalsIgnoreCase(""))) {
                return "Please Enter A Valid Date";
            }

            List acnoList = em.createNativeQuery("select acno,txninstdate,txninstno,txninstamt,TxnBankName,TxnBankAddress,emflag,ifnull(BCBPNO,'0') from clg_ow_shadowbal where txninstno='" + txnInstNo + "' and txninstdate = '" + txnInstDate + "' and emflag='" + emFlag + "' and TxnDate='" + cboDate + "'").getResultList();
            if (acnoList.isEmpty()) {
                return "No Such Instrument Exists";
            }

            List pendList = em.createNativeQuery("SELECT * FROM clg_todayreturnedchq WHERE txninstno='" + txnInstNo + "' AND txninstdate='" + txnInstDate + "' And emflag='" + emFlag + "' And TxnDate = '" + cboDate + "' and acno='" + Tmpac + "' and orgnbrcode='" + brCode + "'").getResultList();
            if (!pendList.isEmpty()) {
                return "Instument Already Exists";
            }
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getCurrentTableData(String emFlag, String registerCboDate, String txnInstNo, String txnInstDt, String txnInstAmt, String brCode)
            throws ApplicationException {
        List tableResult;
        try {
            tableResult = em.createNativeQuery("select Txnstatus,Acno,date_format(TxnInstDate,'%d/%m/%Y') TxnInstDate,TxnInstNo,TxnInstAmt,"
                    + "ifnull(TxnBankName,''),remarks,coalesce(vtot,0),TxnBankAddress from clg_ow_shadowbal where TxnInstNo='" + txnInstNo
                    + "' and TxnInstDate='" + txnInstDt + "' and TxnInstAmt=" + txnInstAmt + " and orgnBrcode='" + brCode + "' and txndate='"
                    + registerCboDate + "' and emflag='" + emFlag + "'").getResultList();
            return tableResult;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
    /*
     * End of Shadow Balance Clearance
     */

    /*
     * Start of Shadow Balance Posting
     */
    public List getClearingMode() {
        List circleType = em.createNativeQuery("select circletype, circledesc, circlemicr from parameterinfo_clg "
                + "order by circletype").getResultList();
        return circleType;
    }

    public String clearingModeLostFocus(String emFlag, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List arraylist = new ArrayList();
            List paramInfo;
            String txnDates = null;
            emFlag = emFlag.toString().trim();
            if ((emFlag.equals("A")) || (emFlag.equals("B")) || (emFlag.equals("G"))) {
                paramInfo = em.createNativeQuery("select distinct date_format(txndate,'%d/%m/%Y') AS txndate "
                        + "from clg_ow_2day where  emflag ='" + emFlag + "' and orgnBrcode='" + brCode + "'").getResultList();
                if (!paramInfo.isEmpty()) {
                    for (int i = 0; i < paramInfo.size(); i++) {
                        Vector txndate = (Vector) paramInfo.get(i);
                        txnDates = txndate.get(0).toString();
                        arraylist.add(txnDates);
                    }
                }
            } else {
                paramInfo = em.createNativeQuery("select distinct date_format(txndate,'%d/%m/%Y') AS txndate "
                        + "from clg_localchq where  emflag ='" + emFlag + "' and orgnBrcode='" + brCode + "'").getResultList();
                if (!paramInfo.isEmpty()) {
                    for (int j = 0; j < paramInfo.size(); j++) {
                        Vector txndate = (Vector) paramInfo.get(j);
                        txnDates = txndate.get(0).toString();
                        arraylist.add(txnDates);
                    }
                }
            }
            if (paramInfo.isEmpty()) {
                ut.rollback();
                return "Register Date has not been set for this Clearing Mode.";
            } else {
                ut.commit();
                return arraylist.toString();
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String checkPostingDt(String emFlag, String registerDts1, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String postingDts;
            String postingDtss;
            String totalInst;
            String totalAmount;
            String a = "Yes";
            emFlag = emFlag.toString().trim();
            String registerDts = registerDts1.trim();
            String dd11 = registerDts.substring(0, 2);
            String mm11 = registerDts.substring(3, 5);
            String yy11 = registerDts.substring(6, 10);
            String registerDt = yy11 + "" + mm11 + "" + dd11;
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();

            List paramInfo = em.createNativeQuery("select Postingdate from clg_ow_register where emflag='" + emFlag
                    + "' and EntryDate = '" + registerDt + "' and brncode='" + brCode + "'").getResultList();
            if (paramInfo.isEmpty()) {
                ut.rollback();
                return a = "Sorry!Posting Date has not been set for this Register";
            } else {
                Vector paramInfos = (Vector) paramInfo.get(0);
                String postingDt = paramInfos.get(0).toString();
                String yy = postingDt.substring(0, 4);
                String mm = postingDt.substring(5, 7);
                String dd = postingDt.substring(8, 10);
                postingDts = yy + "" + mm + "" + dd;
                postingDtss = dd + "/" + mm + "/" + yy;
            }
            if (postingDts.equalsIgnoreCase(Tempbd)) {
                List allData = em.createNativeQuery("select count(acno),sum(txninstamt) from clg_ow_2day where "
                        + "txndate='" + registerDt + "' and reasonforcancel=0 and  emflag='" + emFlag
                        + "' and orgnBrcode='" + brCode + "'").getResultList();
                Vector allDatas = (Vector) allData.get(0);
                totalInst = allDatas.get(0).toString();
                totalAmount = allDatas.get(1).toString();
            } else {
                ut.rollback();
                return a = "Posting is not allowed in this Date";
            }
            ut.commit();
            return totalInst + ": " + totalAmount + ": " + a + ": " + postingDtss;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String clgOutward2day(String AuthBy, String txnDate, String emFlag, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            float trsNumber;
            float recno = 0;
            float tokenNo = 0;
            double clgamt = 0.0d;
            String orgnIntersoleAcNo = "";
            String destIntersoleAcNo = "";
            String orgnBrCode = "";
            String destBrCode = "";
            int var20 = 0, var4 = 0, var3 = 0;
            emFlag = emFlag.toString().trim();
            String eBy = "";

            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();

            Query InsertQuery = em.createNativeQuery("insert into clg_ow_shadowbal(Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,"
                    + "Txnstatus,TxnInstDate,TxnBankName,TxnBankAddress,AreaCode,BnkCode,BranchCode,remarks,ReasonForCancel,vtot,"
                    + "EMFlag,EnterBy,AuthBy,BillType,AlphaCode,BcBpNo,Fyear,trandesc,orgnbrcode,destbrcode,dt,drwname,SanNo,TransactionCode,AccountName) "
                    + "(select Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,'P',TxnInstDate,TxnBankName,TxnBankAddress,AreaCode,"
                    + "BnkCode,BranchCode,remarks,ReasonForCancel,vtot,EMFlag,EnterBy,AuthBy,BillType,AlphaCode,BcBpNo,Fyear,"
                    + "trandesc,orgnbrcode,destbrcode,date_format(curdate(),'%Y%m%d'),drwname,SanNo,TransactionCode,AccountName from clg_ow_2day where txndate='" + txnDate + "' and emflag='" + emFlag
                    + "' and orgnbrcode='" + brCode + "')");
            int var = InsertQuery.executeUpdate();
            if (var <= 0) {
                ut.rollback();
                return "Problem in Insertion in Clg_Ow_Shadowbal";
            }

            trsNumber = ftsPosting.getTrsNo();

            /**
             * IN THE BELOW QUERY ORGNBRCODE AND DESTBRCODE IS ADDED BY ROHIT
             * KRISHNA
             */
            List curChkList = em.createNativeQuery("select c.Acno,Sum(txninstAmt),enterby,ifnull(ifnull(a.custname,t.custname),g.acname)as custname,"
                    + "c.remarks,c.orgnBrCode,c.destBrCode,c.Vtot from clg_ow_2day c left join accountmaster a on a.acno=c.acno "
                    + "left join td_accountmaster t on t.acno=c.acno left join gltable g on g.acno=c.acno where c.orgnbrcode='" + brCode
                    + "' and txndate='" + txnDate + "' and reasonforcancel = 0 and emflag='" + emFlag
                    + "' group by c.Acno,c.Txndate,c.Vtot,c.enterby,a.custname,t.custname,g.acname,c.orgnbrcode,c.destbrcode").getResultList();
            for (int i = 0; i < curChkList.size(); i = i + 1) {
                Vector all = (Vector) curChkList.get(i);
                String acNo = all.get(0).toString();
                double cramt = Double.parseDouble(all.get(1).toString());
                String enterby = all.get(2).toString();
                String custname = all.get(3).toString();
                String tmpRemarks = all.get(4).toString();
                eBy = enterby;
                orgnBrCode = all.get(5).toString();
                destBrCode = all.get(6).toString();
                int vchNo = Integer.parseInt(all.get(7).toString());

                String orgInterCurBrnCode = "";
                String destInterCurBrnCode = "";
                String instNo = "";
                String instDate = "";
                String[] tmpArr = tmpRemarks.split(":");
                String remarks = "O/w Cheque deposit for " + tmpArr[0] + ":" + tmpArr[1];
                // Code comment by Dhirendra Singh as per the Abu Sir for the Complain of NCCBL
//                List countList = em.createNativeQuery("select count(*) from clg_ow_2day where orgnbrcode='" + brCode + "' and txndate='" + txnDate
//                        + "' and reasonforcancel = 0 and emflag='" + emFlag + "' and acno='" + acNo + "' and vtot =" + vchNo).getResultList();
//                Vector cntVect = (Vector) countList.get(0);
//                int count = Integer.parseInt(cntVect.get(0).toString());
//                String instNo = "";
//                String instDate = "";
//                if (count == 1) {
//                    List instList = em.createNativeQuery("select TxnInstNo, date_format(txninstDate,'%Y%m%d') from clg_ow_2day where orgnbrcode='" + brCode + "' and txndate='" + txnDate
//                            + "' and reasonforcancel = 0 and emflag='" + emFlag + "' and acno='" + acNo + "' and vtot =" + vchNo).getResultList();
//                    Vector instVect = (Vector) instList.get(0);
//                    instNo = instVect.get(0).toString();
//                    instDate = instVect.get(1).toString();
//                }
                /**
                 * THIS CODE IS ADDED FOR REMOTE AND LOCAL CHEQUES --ROHIT
                 * KRISHNA*
                 */
                if (!orgnBrCode.equalsIgnoreCase(destBrCode)) {
                    orgnIntersoleAcNo = ftsPosting.getGlHeadFromParam(orgnBrCode, SiplConstant.ISO_HEAD.getValue());
                    destIntersoleAcNo = ftsPosting.getGlHeadFromParam(destBrCode, SiplConstant.ISO_HEAD.getValue());
                    orgInterCurBrnCode = ftsPosting.getCurrentBrnCode(orgnIntersoleAcNo);
                    destInterCurBrnCode = ftsPosting.getCurrentBrnCode(destIntersoleAcNo);
                }

                /**
                 * * END*
                 */
                clgamt = clgamt + cramt;
                String accCode = ftsPosting.getAccountCode(acNo);
                String curBrCode = ftsPosting.getCurrentBrnCode(acNo);
                List accList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + accCode + "'").getResultList();
                Vector accLists = (Vector) accList.get(0);
                String accNature = accLists.get(0).toString();
                if (accNature == null) {
                    ut.rollback();
                    return "The Accountnature of " + acNo + "does not Exists";
                }
                if (remarks == null) {
                    remarks = "Amount Deposited by Chq.";
                }
//                float dramt = 0;
//                String clearedbalanceflag = null;
//                String updateBal = ftsPosting.updateBalance(accNature, acNo, cramt, dramt, "N", clearedbalanceflag);
//                if (!updateBal.equals("TRUE")) {
//                    ut.rollback();
//                    return "Problem in Updating balance :" + dramt;
//                }
                /**
                 * This code is Modyfied by Dhirendra Singh for handling local
                 * and inter branch transaction.*
                 */
                if (orgnBrCode.equalsIgnoreCase(destBrCode)) {
                    float RecNo = ftsPosting.getRecNo();
                    String recon = ftsPosting.insertRecons(accNature, acNo, 0, cramt, Tempbd, Tempbd, 1, remarks, enterby, trsNumber,
                            null, RecNo, "Y", AuthBy, 0, 3, instNo, instDate, tokenNo, null, "A", 1, null, null, null, null, brCode, curBrCode, 0, null, "", "");
                    if (!recon.equals("TRUE")) {
                        ut.rollback();
                        return "Problem in Insertion in recons :" + acNo;
                    }
                    //Deaf Updation
                    try {
                        ftsPosting.lastTxnDateUpdation(acNo);
                    } catch (Exception ex) {
                        ut.rollback();
                        return "Problem in last txn date updation";
                    }
                    //End
                    Query insertQuery2 = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,cramt,ty,trantype,recno,trsno,auth,enterby,"
                            + "authby,details,org_brnid,dest_brnid,valueDt)"
                            + "values ('" + acNo + "','" + custname + "','" + Tempbd + "'," + cramt + "," + 0 + "," + 1 + "," + RecNo + "," + trsNumber
                            + ",'Y'" + ",'" + enterby + "','" + AuthBy + "','" + remarks + "','" + brCode + "','" + curBrCode + "','" + Tempbd + "')");
                    int var2 = insertQuery2.executeUpdate();
                    if (var2 <= 0) {
                        ut.rollback();
                        return "Problem in Insertion in Recon_clg_d For Acno" + acNo;
                    }
                } else {
                    /*
                     * Code for handling inter branch transaction Added By
                     * Dhirendra Singh
                     */
                    String orgnAlphaCode = "";
                    List orgnAlphaCodeList = em.createNativeQuery("SELECT ALPHACODE FROM branchmaster WHERE BRNCODE = '" + Integer.parseInt(orgnBrCode) + "'").getResultList();
                    if (orgnAlphaCodeList.size() > 0) {
                        Vector orgnAlphaCodeVect = (Vector) orgnAlphaCodeList.get(0);
                        orgnAlphaCode = orgnAlphaCodeVect.get(0).toString();
                    } else {
                        return "ERROR OCCURED: - Please enter ALPHA CODE for Orign branch!";
                    }
                    remarks = "AT " + orgnAlphaCode + ": " + remarks;
                    List chkList3 = em.createNativeQuery("select acname from gltable where acno='" + orgnIntersoleAcNo + "' and substring(AcNo,1,2)='"
                            + orgInterCurBrnCode + "'").getResultList();
                    if (chkList3.isEmpty()) {
                        ut.rollback();
                        return "A/C. Name Not Found For Your Branch Intersole A/C. No.";
                    }
                    Vector recLst3 = (Vector) chkList3.get(0);
                    String acname1 = recLst3.get(0).toString();

                    List chkList4 = em.createNativeQuery("select acname from gltable where acno='" + destIntersoleAcNo + "' and substring(AcNo,1,2)='"
                            + destInterCurBrnCode + "'").getResultList();
                    if (chkList4.isEmpty()) {
                        ut.rollback();
                        return "A/C. Name Not Found For Remote Branch Intersole A/C. No.";
                    }
                    Vector recLst4 = (Vector) chkList4.get(0);
                    String acname2 = recLst4.get(0).toString();

                    float RecNo = ftsPosting.getRecNo();
                    float recNo1 = ftsPosting.getRecNo();
                    float recNo2 = ftsPosting.getRecNo();

                    String recon = ftsPosting.insertRecons(accNature, acNo, 0, cramt, Tempbd, Tempbd, 1, remarks, enterby, trsNumber, null, RecNo, "Y", AuthBy,
                            0, 3, instNo, instDate, tokenNo, null, "A", 9999, null, null, null, null, brCode, curBrCode, 0, null, "", "");
                    if (!recon.equals("TRUE")) {
                        ut.rollback();
                        return "Problem in Insertion in recons :" + acNo;
                    }

                    //Deaf Updation
                    try {
                        ftsPosting.lastTxnDateUpdation(acNo);
                    } catch (Exception ex) {
                        ut.rollback();
                        return "Problem in last txn date updation";
                    }
                    //End

                    recon = ftsPosting.insertRecons("PO", destIntersoleAcNo, 1, cramt, Tempbd, Tempbd, 1, remarks, enterby, trsNumber, null, recNo1, "Y", AuthBy,
                            0, 3, null, null, tokenNo, null, "A", 9999, null, null, null, null, brCode, curBrCode, 0, null, "", "");
                    if (!recon.equals("TRUE")) {
                        ut.rollback();
                        return "Problem in Insertion in recons :" + destIntersoleAcNo;
                    }

                    recon = ftsPosting.insertRecons("PO", orgnIntersoleAcNo, 0, cramt, Tempbd, Tempbd, 1, remarks, enterby, trsNumber, null, recNo2, "Y", AuthBy,
                            0, 3, null, null, tokenNo, null, "A", 8888, null, null, null, null, brCode, curBrCode, 0, null, "", "");
                    if (!recon.equals("TRUE")) {
                        ut.rollback();
                        return "Problem in Insertion in recons :" + orgnIntersoleAcNo;
                    }

                    Query insertQuery2 = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,cramt,ty,trantype,recno,trsno,auth,enterby,authby,details,"
                            + "org_brnid,dest_brnid, valueDt)"
                            + "values ('" + acNo + "','" + custname + "','" + Tempbd + "'," + cramt + "," + 0 + "," + 1 + "," + RecNo + "," + trsNumber + ",'Y','"
                            + enterby + "','" + AuthBy + "','" + remarks + "','" + brCode + "','" + curBrCode + "','" + Tempbd + "')");
                    int var2 = insertQuery2.executeUpdate();
                    if (var2 <= 0) {
                        ut.rollback();
                        return "Problem in Insertion in Recon_clg_d For Acno" + acNo;
                    }

                    Query insertQuery20 = em.createNativeQuery("insert into recon_clg_d(acno,ty,dramt,trantype,recno,dt,details,SCREENFLAG,enterby,"
                            + "CUSTNAME,trsno,org_brnid,dest_brnid,auth,authby,valueDt) values (" + "'" + destIntersoleAcNo + "'" + "," + 1 + "," + cramt + ","
                            + 1 + "," + recNo1 + "," + "'" + Tempbd + "'" + "," + "'" + "VCH: Today Clearing" + "'" + "," + "'" + "T" + "'" + "," + "'"
                            + AuthBy + "'" + "," + "'" + acname1 + "'" + "," + trsNumber + ",'" + brCode + "','" + curBrCode
                            + "','Y','" + AuthBy + "','" + Tempbd + "')");
                    var20 = insertQuery20.executeUpdate();
                    if (var20 <= 0) {
                        ut.rollback();
                        return "Problem in Insertion in Recon_clg_d For Acno" + destIntersoleAcNo;
                    }

                    Query insertQuery4 = em.createNativeQuery("insert into recon_clg_d(acno,ty,cramt,trantype,recno,dt,details,SCREENFLAG,enterby,"
                            + "CUSTNAME,trsno,org_brnid,dest_brnid,auth,authby,valueDt) values (" + "'" + orgnIntersoleAcNo + "'" + "," + 0 + "," + cramt + ","
                            + 1 + "," + recNo2 + "," + "'" + Tempbd + "'" + "," + "'" + "VCH: Today Clearing" + "'" + "," + "'" + "T" + "'" + "," + "'"
                            + AuthBy + "'" + "," + "'" + acname2 + "'" + "," + trsNumber + ",'" + brCode + "','" + curBrCode
                            + "','Y','" + AuthBy + "','" + Tempbd + "')");
                    var4 = insertQuery4.executeUpdate();
                    if (var4 <= 0) {
                        ut.rollback();
                        return "Problem in Insertion in Recon_clg_d For Acno" + orgnIntersoleAcNo;
                    }
                }
            }

            if (clgamt != 0) {
                List chkList1 = em.createNativeQuery("select glclg from parameterinfo_clg  where circletype='" + emFlag + "'").getResultList();
                Vector rLst = (Vector) chkList1.get(0);
                String clgHead = rLst.get(0).toString();
                if (clgHead == null) {
                    ut.rollback();
                    return "GLHEAD for Clearing Does not Exists";
                }
                for (int i = clgHead.length(); i < 6; i++) {
                    clgHead = "0" + clgHead;
                }
                String glclg = orgnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + clgHead + "01";
                String glclCurBrCode = ftsPosting.getCurrentBrnCode(glclg);
                Query updateQuery = em.createNativeQuery("update reconbalan set balance=balance-ifnull(" + CbsUtil.round(clgamt, 2) + ",0) where acno='" + glclg + "'");
                int varU = updateQuery.executeUpdate();
                if (varU <= 0) {
                    ut.rollback();
                    return "Updation problem in reconbalan for GL CLUBBED ENTRY";
                }

                //float recNo = ftsPost43Cbs.getRecNo(glclg.substring(0,2));
                float recNo = ftsPosting.getRecNo();

                List chkList2 = em.createNativeQuery("select acname from gltable where acno='" + glclg + "' and substring(AcNo,1,2)='" + brCode + "'").getResultList();
                if (chkList2.isEmpty()) {
                    ut.rollback();
                    return "A/C. Name Not Found For GLHead.";
                }
                Vector recLst2 = (Vector) chkList2.get(0);
                String acname = recLst2.get(0).toString();

//                Query insertQuery20 = em.createNativeQuery("insert into recon_clg_d(acno,ty,dramt,trantype,recno,dt,details,SCREENFLAG,enterby,"
//                        + "CUSTNAME,trsno,org_brnid,dest_brnid,valueDt)"
//                        + "values ('" + glclg + "'," + 1 + "," + clgamt + "," + 1 + "," + recNo + ",'" + Tempbd + "','" + "VCH: Today Clearing"
//                        + "','T','" + AuthBy + "','" + acname + "'," + trsNumber + ",'" + brCode + "','" + glclCurBrCode + "','" + Tempbd + "')");
//                var20 = insertQuery20.executeUpdate();
//                if (var20 <= 0) {
//                    ut.rollback();
//                    return "Insertion problem in Recon_clg_D";
//                }
                float RecNo = ftsPosting.getRecNo();
                String recon = ftsPosting.insertRecons("PO", glclg, 1, CbsUtil.round(clgamt, 2), Tempbd, Tempbd, 1, "VCH: Today Clearing", eBy, trsNumber,
                        null, RecNo, "Y", AuthBy, 0, 3, "", "", tokenNo, null, "A", 1, null, null, null, null, brCode, brCode, 0, null, "", "");
                if (!recon.equals("TRUE")) {
                    ut.rollback();
                    return "Problem in Insertion in recons :" + clgamt;
                }

                Query insertQuery2 = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,dramt,ty,trantype,recno,trsno,auth,enterby,"
                        + "authby,details,org_brnid,dest_brnid,valueDt)"
                        + "values ('" + glclg + "','" + acname + "','" + Tempbd + "'," + CbsUtil.round(clgamt, 2) + "," + 1 + "," + 1 + "," + RecNo + "," + trsNumber
                        + ",'Y'" + ",'" + eBy + "','" + AuthBy + "','VCH: Today Clearing','" + brCode + "','" + brCode + "','" + Tempbd + "')");
                int var2 = insertQuery2.executeUpdate();
                if (var2 <= 0) {
                    ut.rollback();
                    return "Problem in Insertion in Recon_clg_d For Acno" + glclg;
                }
                
                if (ftsPosting.getCodeForReportName("CLG-CONTRA-VOUCHER") == 1) {
                
                    String crGlHead = brCode + ftsPosting.getCodeFromCbsParameterInfo("CLG-CONTRA-CR-HEAD");
                    String drGlHead = brCode + ftsPosting.getCodeFromCbsParameterInfo("CLG-CONTRA-DR-HEAD");

                    float trsNo = ftsPosting.getTrsNo();

                    String rs = ftsPosting.insertRecons("PO", drGlHead, 0, clgamt, Tempbd, Tempbd, 2, "Reversal of Chqs.Sent For Coll(E)", AuthBy, trsNo, null, 0f,
                            "Y", "System", 0, 3, "", null, null, null, "A", 0, null, 0f, null, null, brCode, brCode, 0, null, null, null);
                    if (!rs.equalsIgnoreCase("True")) {
                        throw new ApplicationException(rs);
                    }
                    rs = ftsPosting.insertRecons("PO", crGlHead, 1, clgamt, Tempbd, Tempbd, 2, "Reversal of Chqs.Sent For Coll(E)", AuthBy, trsNo, null, 0f,
                            "Y", "System", 0, 3, "", null, null, null, "A", 0, null, 0f, null, null, brCode, brCode, 0, null, null, null);
                    if (!rs.equalsIgnoreCase("True")) {
                        throw new ApplicationException(rs);
                    }
                }
            }
            /**
             * IN THE BELOW QUERY ORGNBRCODE CHECKIN IS CHANGED --ROHIT KRISHNA*
             */
            Query deleteQuery = em.createNativeQuery("DELETE FROM clg_ow_2day  where txndate='" + txnDate + "' AND emflag='" + emFlag + "' and orgnbrcode='" + brCode + "'");
            int varQ = deleteQuery.executeUpdate();
            if (varQ <= 0) {
                ut.rollback();
                return "Deletion problem of Clg_Ow_2Day";
            }
            Query updateQuery = em.createNativeQuery("UPDATE clg_ow_register set STATUS='POSTED',POSTEDBY='" + AuthBy + "' where ENTRYDATE='" + txnDate + "' AND emflag='" + emFlag + "' and brncode='" + brCode + "'");
            int varV = updateQuery.executeUpdate();
            if (varV <= 0) {
                ut.rollback();
                return "Posting problem in Clg_Ow_register";
            }
            ut.commit();
            return "Data posted Successfully";
            //return "True";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    /*
     * End of Shadow Balance Posting
     */

    public String reasonNotAppForCharge(int code) throws ApplicationException {
        try {
            List reasonList = em.createNativeQuery("select description from codebook where groupcode=13 and flag='N' and code = " + code + "").getResultList();
            if (!reasonList.isEmpty()) {
                return "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "false";
    }

    private String interBranchClgTxn(String orgnBrCode, String details, String orgnIntersoleAcNo, String destIntersoleAcNo, String actnature, String acNo,
            String Tempbd, String valueDt, String enterby, float trsNo, float recNo, String AuthBy, float tokenNo, double amt, String custname,
            String brCode, String brCod, int trantype) throws ApplicationException {
        try {
            String orgnAlphaCode = "";
            List orgnAlphaCodeList = em.createNativeQuery("SELECT ALPHACODE FROM branchmaster WHERE BRNCODE = '" + Integer.parseInt(orgnBrCode) + "'").getResultList();
            if (orgnAlphaCodeList.size() > 0) {
                Vector orgnAlphaCodeVect = (Vector) orgnAlphaCodeList.get(0);
                orgnAlphaCode = orgnAlphaCodeVect.get(0).toString();
            } else {
                return "ERROR OCCURED: - Please enter ALPHA CODE for Orign branch!";
            }
            int tranDesc = 0;
            if (details.equalsIgnoreCase("Cheque Return Chg.")) {
                tranDesc = 109;
            } else if (details.equalsIgnoreCase("GST for Cheque Return Chg.")) {
                tranDesc = 71;
            }
            details = "AT " + orgnAlphaCode + ": " + details;
            String curBrCode = ftsPosting.getCurrentBrnCode(orgnIntersoleAcNo);
            String destCurBrCode = ftsPosting.getCurrentBrnCode(destIntersoleAcNo);
            List chkList3 = em.createNativeQuery("select acname from gltable where acno='" + orgnIntersoleAcNo
                    + "' and substring(AcNo,1,2)='" + curBrCode + "'").getResultList();
            if (chkList3.isEmpty()) {
                throw new ApplicationException("A/C. Name Not Found For Your Branch Intersole A/C. No.");
            }
            Vector recLst3 = (Vector) chkList3.get(0);
            String orgIsoName = recLst3.get(0).toString();

            List chkList4 = em.createNativeQuery("select acname from gltable where acno='" + destIntersoleAcNo
                    + "' and substring(AcNo,1,2)='" + destCurBrCode + "'").getResultList();
            if (chkList4.isEmpty()) {
                throw new ApplicationException("A/C. Name Not Found For Remote Branch Intersole A/C. No.");
            }
            Vector recLst4 = (Vector) chkList4.get(0);
            // String destIsoName = recLst4.get(0).toString();
            recNo = ftsPosting.getRecNo();
            String recon = ftsPosting.insertRecons(actnature, acNo, 1, amt, Tempbd, valueDt, trantype, details, enterby, trsNo, null, recNo, "Y", AuthBy,
                    tranDesc, 3, null, null, tokenNo, null, "A", 9999, null, null, null, null, brCode, brCod, 0, null, "", "");

            if (!recon.equals("TRUE")) {
                throw new ApplicationException("Problem in Insertion in recons :" + recon);
            }
            //Deaf Updation
            ftsPosting.lastTxnDateUpdation(acNo);
            float recno = ftsPosting.getRecNo();
            recon = ftsPosting.insertRecons("PO", destIntersoleAcNo, 0, amt, Tempbd, valueDt, trantype, details, enterby, trsNo, null, recno, "Y", AuthBy,
                    0, 3, null, null, tokenNo, null, "A", 9999, null, null, null, null, brCode, brCod, 0, null, "", "");
            if (!recon.equals("TRUE")) {
                throw new ApplicationException("Problem in Insertion in recons :" + amt);
            }
            recno = ftsPosting.getRecNo();
            recon = ftsPosting.insertRecons("PO", orgnIntersoleAcNo, 1, amt, Tempbd, valueDt, trantype, details, enterby, trsNo, null, recno, "Y", AuthBy,
                    0, 3, null, null, tokenNo, null, "A", 8888, null, null, null, null, brCode, brCod, 0, null, "", "");
            if (!recon.equals("TRUE")) {
                throw new ApplicationException("Problem in Insertion in recons :" + amt);
            }

            if (trantype == 1) {

                Query insertQuery3 = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,dramt,ty,trantype,recno,trsno,auth,"
                        + "enterby,authby,details,org_brnid,dest_brnid,valueDt) values ('" + acNo + "','" + custname + "','"
                        + Tempbd + "'," + amt + "," + 1 + "," + 1 + "," + recNo + "," + trsNo + ",'Y','" + enterby
                        + "','" + AuthBy + "','" + details + "','" + brCode + "','" + brCod + "','" + valueDt + "')");
                int var3 = insertQuery3.executeUpdate();
                if (var3 < 0) {
                    throw new ApplicationException("Insert Problem in Recon_Clg_d for Ac No:" + acNo);
                }

            } else {
                Query insertQuery82 = em.createNativeQuery("insert into recon_trf_d( acno , custname, dt, Dramt, CrAmt, Ty, TranType,"
                        + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                        + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                        + "values ('" + acNo + "','" + custname + "','" + Tempbd + "'," + amt + ",0,1,2," + recNo + ","
                        + trsNo + ",'',3,1,'Y','SYSTEM','" + AuthBy + "',null,109"
                        + "," + tokenNo + ",'A', CURRENT_TIMESTAMP,'" + details + "',0,null,'" + brCode + "','"
                        + brCode + "','" + valueDt + "','','')");
                int varChgRet = insertQuery82.executeUpdate();
                if (varChgRet < 0) {
                    throw new ApplicationException("Insert Problem in recon_trf_d for Ac No:" + acNo);
                }
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<String> loadRegisterDate(String clgMode, String brCode) throws Exception {
        List<String> dataList = new ArrayList();
        try {
            String query = "select distinct date_format(txndate,'%d/%m/%Y') from clg_ow_shadowbal where emflag='" + clgMode + "'";
            List list = null;
            if (brCode.equalsIgnoreCase("ALL")) {
                list = em.createNativeQuery(query + " order by txndate asc").getResultList();
            } else {
                String branchQuery = " and orgnBrcode='" + brCode + "' order by txndate asc";
                list = em.createNativeQuery(query + branchQuery).getResultList();
            }
            if (list.isEmpty()) {
                throw new Exception("Register date not found.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                dataList.add(ele.get(0).toString().trim());
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return dataList;
    }

    @Override
    public List<OwClgPojo> getOwNpciReturnData(String txnDt, String clgMode, String brCode, String status) throws Exception {
        List<OwClgPojo> dataList = new ArrayList<OwClgPojo>();
        try {
            Map<Integer, String> map = ftsPosting.clearingReturnReasonInMap();
            String query = "select acno,txninstno,txninstamt,ifnull(accountname,'') as accountname,ifnull(ctsreturncode,'') "
                    + "from clg_ow_shadowbal where txndate='" + txnDt + "' and emflag='" + clgMode + "'";
            List list = null;
            if (brCode.equalsIgnoreCase("ALL")) {
                if (status.equalsIgnoreCase("ALL")) {
                    list = em.createNativeQuery(query).getResultList();
                } else if (status.equalsIgnoreCase("PASS")) {
                    list = em.createNativeQuery(query + " and ifnull(ctsreturncode,'')=''").getResultList();
                } else if (status.equalsIgnoreCase("RETURN")) {
                    list = em.createNativeQuery(query + " and ifnull(ctsreturncode,'')<>''").getResultList();
                }
            } else {
                if (status.equalsIgnoreCase("ALL")) {
                    list = em.createNativeQuery(query + " and orgnBrcode='" + brCode + "'").getResultList();
                } else if (status.equalsIgnoreCase("PASS")) {
                    list = em.createNativeQuery(query + " and orgnBrcode='" + brCode + "' and ifnull(ctsreturncode,'')=''").getResultList();
                } else if (status.equalsIgnoreCase("RETURN")) {
                    list = em.createNativeQuery(query + " and orgnBrcode='" + brCode + "' and ifnull(ctsreturncode,'')<>''").getResultList();
                }
            }
            if (list.isEmpty()) {
                throw new Exception("There is no data to show the report.");
            }

            NumberFormat formatter = new DecimalFormat("#.##");
            for (int i = 0; i < list.size(); i++) {
                OwClgPojo obj = new OwClgPojo();
                Vector ele = (Vector) list.get(i);

                obj.setAccountNo(ele.get(0).toString());
                obj.setInstNo(ele.get(1).toString());
                obj.setAmount(new BigDecimal(formatter.format(Double.parseDouble(ele.get(2).toString()))));
                obj.setAccountName(ele.get(3).toString());
                String code = ele.get(4).toString();
                obj.setReasonCode(code);
                if (status.equalsIgnoreCase("ALL")) {
                    obj.setStatus(code.equalsIgnoreCase("") ? "Passed" : "Returned");
                    obj.setReason(code.equals("") ? "" : map.get(Integer.parseInt(code)));
                } else if (status.equalsIgnoreCase("PASS")) {
                    obj.setStatus("Passed");
                    obj.setReason("");
                } else if (status.equalsIgnoreCase("RETURN")) {
                    obj.setStatus("Returned");
                    obj.setReason(code.equals("") ? "" : map.get(Integer.parseInt(code)));
                }
                dataList.add(obj);

                ComparatorChain chain = new ComparatorChain();
                chain.addComparator(new NpciOwSortedByStatus());
                Collections.sort(dataList, chain);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dataList;
    }
}
