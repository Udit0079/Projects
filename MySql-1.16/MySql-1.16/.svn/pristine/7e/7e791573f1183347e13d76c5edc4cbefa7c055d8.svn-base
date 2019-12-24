/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dao.master.PrizmMasterPojo;
import com.cbs.dto.NpciFileDto;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.deaf.PrizmCardHolderPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.Validator;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
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

@Stateless(mappedName = "TransferBatchDeletionFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class TransferBatchDeletionFacade implements TransferBatchDeletionFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    private CommonReportMethodsRemote reportRemote;
//    @Resource
//    private SessionContext sctx;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMddhhmmss");
    SimpleDateFormat ymdOne = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat ym = new SimpleDateFormat("yyMM");

    public List batchDetailGridLoad(String date, Float batchNo, String brCode) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("SELECT a.acno,b.CUSTNAME,a.ty,a.dramt,a.cramt,ifnull(a.details,''),a.iy,a.payby,COALESCE(a.instno,''),"
                    + "a.trandesc,a.enterby,a.recno,'',a.trsno FROM recon_trf_d a, accountmaster b WHERE a.dt = '" + date + "' AND (a.auth IS NULL OR a.auth='N') AND "
                    + "a.trantype IN (2,8) AND a.trsno = " + batchNo + " AND b.acno = a.ACNO AND a.org_brnid = '" + brCode + "' "
                    + "union "
                    + "SELECT a.acno,b.acname,a.ty,a.dramt,a.cramt,ifnull(a.details,''),a.iy,a.payby,coalesce(a.instno,''),"
                    + "a.trandesc,a.enterby,a.recno,'',a.trsno FROM recon_trf_d a, gltable b WHERE a.dt = '" + date + "' AND (a.auth IS NULL OR a.auth='N') "
                    + "AND a.trantype IN (2,8) AND a.trsno = " + batchNo + " AND b.acno = a.ACNO AND a.org_brnid = '" + brCode + "' "
                    + "union "
                    + "SELECT a.acno,b.Custname,a.ty,a.dramt,a.cramt,ifnull(a.details,''),a.iy,a.payby,coalesce(a.instno,''),"
                    + "a.trandesc,a.enterby,a.recno,ifnull(a.intflag,''),a.trsno from recon_trf_d a , td_accountmaster b WHERE b.acno = a.acno and a.dt = '" + date
                    + "' and (a.auth is null or a.auth='N') and a.trantype in (2,8) and a.trsno = " + batchNo + " and a.org_brnid = '" + brCode + "'").getResultList();

            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String deleteBatchNoProc(String date, Float batchNo, String brCode, String user) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List resultList = batchDetailGridLoad(date, batchNo, brCode);
            if (resultList.isEmpty()) {
                throw new ApplicationException("This batch has been already deleted.");
            }
            for (int i = 0; i < resultList.size(); i++) {
                Vector tempVector = (Vector) resultList.get(i);
                String acNo = tempVector.elementAt(0).toString();
                String custName = tempVector.elementAt(1).toString();
                int ty = Integer.parseInt(tempVector.elementAt(2).toString());

                double drAmt = Double.parseDouble(tempVector.elementAt(3).toString());
                double crAmt = Double.parseDouble(tempVector.elementAt(4).toString());

                String details = tempVector.elementAt(5).toString();
                int iy = Integer.parseInt(tempVector.elementAt(6).toString());
                float payBy = Float.parseFloat(tempVector.elementAt(7).toString());

                String strIntNo = tempVector.elementAt(8).toString();
                float instNo = 0.0f;
                if (strIntNo == null || strIntNo.equals("")) {
                    instNo = 0.0f;
                } else {
                    instNo = Float.parseFloat(strIntNo);
                }
                int trandesc = Integer.parseInt(tempVector.get(9).toString());
                String enterBy = (tempVector.elementAt(10).toString());
                float recNo = Float.parseFloat(tempVector.elementAt(11).toString());
                String intFlag = "";
                String acctNature = ftsRemote.getAccountNature(acNo);
                List list = em.createNativeQuery("SELECT acno from td_recon where trsno=" + batchNo + " and dt='" + date + "' and org_brnid='" + brCode + "' and auth='Y'").getResultList();
                if (!list.isEmpty()) {
                    throw new ApplicationException("You can not delete this batch because it is a system generated.");
                }
                if ((acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    intFlag = tempVector.elementAt(12).toString();
                }
                if (acNo == null || acNo.equalsIgnoreCase("") || acNo.length() == 0) {
                    throw new ApplicationException("ACCOUNT NO. COULD NOT BE BLANK !!!");
                }
                if (batchNo == null) {
                    throw new ApplicationException("PLEASE ENTER BATCH NO. FOR DELETE !!!");
                }
                if (payBy == 1 && ty == 1) {
                    if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//                        Query deleteQuery = em.createNativeQuery("Delete from reconusedcheque where acno = '" + acNo + "' and instno = " + instNo + "");
//                        deleteQuery.executeUpdate();
                        Query updateQuery = em.createNativeQuery("Update chbook_sb SET statusflag = 'F' where acno = '" + acNo + "' and chqno = " + instNo + "");
                        updateQuery.executeUpdate();
                    }
                    if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                        Query deleteQuery = em.createNativeQuery("Delete from reconusedcheque where acno = '" + acNo + "' and instno = " + instNo + "");
//                        deleteQuery.executeUpdate();
                        Query updateQuery = em.createNativeQuery("Update chbook_ca SET statusflag = 'F' where acno = '" + acNo + "' and chqno = " + instNo + "");
                        updateQuery.executeUpdate();
                    }
                    if (acctNature.equalsIgnoreCase(CbsConstant.CC_AC)) {
//                        Query deleteQuery = em.createNativeQuery("Delete from reconusedcheque where acno = '" + acNo + "' and instno = " + instNo + "");
//                        deleteQuery.executeUpdate();
                        Query updateQuery = em.createNativeQuery("Update chbook_cc SET statusflag = 'F' where acno = '" + acNo + "' and chqno = " + instNo + "");
                        updateQuery.executeUpdate();
                    }
                    if (acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                        Query deleteQuery = em.createNativeQuery("Delete from reconusedcheque where acno = '" + acNo + "' and instno = " + instNo + "");
//                        deleteQuery.executeUpdate();
                        Query updateQuery = em.createNativeQuery("Update chbook_po SET statusflag = 'F' where acno = '" + acNo + "' and chqno = " + instNo + "");
                        updateQuery.executeUpdate();
                    }
                }
                if (ty == 0) {
                    Query delete1 = em.createNativeQuery("delete from bill_sundry_dt where dt='" + date + "'"
                            + "and  recno = " + recNo + " and acno = '" + acNo + "'");
                    delete1.executeUpdate();
                }
                if (ty == 1) {
                    Query delete1 = em.createNativeQuery("delete from bill_suspense_dt where dt='" + date + "'"
                            + "and  recno = " + recNo + " and acno = '" + acNo + "'");
                    delete1.executeUpdate();
                }
                if (acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    // Sundry payment before Authorize batch delete sundry status not reserve.
                    if ((iy == 11) || (iy == 12) || (iy == 13) || (iy == 81) || (iy == 82) || (iy == 83)) {
                        if ((iy == 11) || (iy == 81)) {
                            if (ty == 1) {
                                Query updateQuery = em.createNativeQuery("Update bill_sundry set status = 'ISSUED',ty = 0,dt='" + date
                                        + "',lastupdateby=null where dt = '" + date + "' and recno = " + recNo + " and acno = '" + acNo + "'");
                                updateQuery.executeUpdate();
                            }
                        } else if ((iy == 12) || (iy == 82)) {
                            if (ty == 0) {
                                Query updateQuery = em.createNativeQuery("update bill_suspense set status = 'ISSUED',ty = 1,dt='" + date
                                        + "',lastupdateby=null where dt = '" + date + "' and recno = " + recNo + " and acno = '" + acNo + "'");
                                updateQuery.executeUpdate();
                                }
                        } else if ((iy == 13) || (iy == 83)) {
                            if (iy == 0) {
                                Query updateQuery = em.createNativeQuery("Update bill_clgadjustment set status = 'ISSUED',ty = 1,dt='" + date
                                        + "',lastupdateby=null where dt = '" + date + "' and recno = " + recNo + " and acno = '" + acNo + "'");
                                updateQuery.executeUpdate();
                            }
                        }
                    } else {
                        List billTypeList = em.createNativeQuery("select instnature,glhead from billtypemaster where glhead = SUBSTRING('" + acNo + "',3,8)").getResultList();
                        String billType = "";
                        if (!billTypeList.isEmpty()) {
                            Vector billTypeListVec = (Vector) billTypeList.get(0);
                            billType = billTypeListVec.get(0).toString();
                        }
                        if (!billType.equalsIgnoreCase("")) {
                            if (ty == 0) {
                                if (billType.equalsIgnoreCase("PO")) {
                                    Query deleteQuery = em.createNativeQuery("delete from bill_po where recno = " + recNo + " and dt = '" + date + "' and acno = '" + acNo + "'");
                                    deleteQuery.executeUpdate();
                                } else if (billType.equalsIgnoreCase("DD")) {
                                    Query deleteQuery = em.createNativeQuery("delete from bill_dd where recno = " + recNo + " and dt = '" + date + "' and acno = '" + acNo + "'");
                                    deleteQuery.executeUpdate();
                                } else {
                                    String temp = details.substring(0, 3);
                                    if (temp.equalsIgnoreCase("TPO")) {
                                        Query deleteQuery = em.createNativeQuery("delete from bill_tpo where recno = " + recNo + " and dt = '" + date + "' and acno = '" + acNo + "'");
                                        deleteQuery.executeUpdate();
                                    } else {
                                        Query deleteQuery = em.createNativeQuery("delete from bill_ad where recno = " + recNo + " and dt = '" + date + "' and acno = '" + acNo + "'");
                                        deleteQuery.executeUpdate();
                                        Query deleteQuery1 = em.createNativeQuery("delete from bill_hoothers where recno = " + recNo + " and dt = '" + date + "' and acno = '" + acNo + "'");
                                        deleteQuery1.executeUpdate();
                                    }
                                }
                            } else if (ty == 1) {
                                if (billType.equalsIgnoreCase("PO")) {
                                    Query deleteQuery = em.createNativeQuery("update bill_po set status = 'Issued',dt = origindt,lastupdateby='' "
                                            + "where recno = " + recNo + " and dt = '" + date + "' and acno = '" + acNo + "'");
                                    deleteQuery.executeUpdate();
                                } else if (billType.equalsIgnoreCase("DD")) {
                                    Query deleteQuery = em.createNativeQuery("delete from bill_dd where recno = " + recNo + " and dt = '" + date
                                            + "' and acno = '" + acNo + "'");
                                    deleteQuery.executeUpdate();
                                } else {
                                    Query deleteQuery = em.createNativeQuery("delete from bill_tpo where recno = " + recNo + " and dt = '" + date
                                            + "' and acno = '" + acNo + "'");
                                    deleteQuery.executeUpdate();
                                    Query deleteQuery2 = em.createNativeQuery("delete from bill_ad where recno = " + recNo + " and dt = '" + date
                                            + "' and acno = '" + acNo + "'");
                                    deleteQuery2.executeUpdate();
                                    Query deleteQuery1 = em.createNativeQuery("delete from bill_hoothers where recno = " + recNo + " and dt = '" + date
                                            + "' and acno = '" + acNo + "'");
                                    deleteQuery1.executeUpdate();
                                }
                            }
                        }
                    }
                }
                if (iy == 31) {
                    Query deleteQuery = em.createNativeQuery("delete from standins_transexecuted where instrno = " + instNo + " and processdate = '" + date + "' and fromacno = '" + acNo + "'");
                    deleteQuery.executeUpdate();
                }
                if (ty == 1) {
                    System.out.println("Amount to be updation in case of debit= " + drAmt + " and the balance is " + ftsRemote.ftsGetBal(acNo));
                    ftsRemote.updateBalance(acctNature, acNo, drAmt, 0, "Y", "Y");
                    System.out.println("Balance after updation = " + ftsRemote.ftsGetBal(acNo));
                } else if (ty == 0) {
                    if ((acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) && intFlag.equalsIgnoreCase("I")) {
                        double crAmt1 = crAmt * (-1);
                        System.out.println("Amount to be updation in case of debit= " + drAmt + " and the balance is " + ftsRemote.ftsGetBal(acNo));
                        ftsRemote.updateBalance(acctNature, acNo, crAmt1, 0, "Y", "Y");
                        System.out.println("Balance after updation = " + ftsRemote.ftsGetBal(acNo));
                    }
                }
                Query insertQuery = em.createNativeQuery("INSERT INTO deletetrans (acno,custname,cramt,dramt,deletedt,enterby,trantype,deletedby,"
                        + "trsno,details,trantime,org_brnId,dest_brnId)"
                        + " values ('" + acNo + "','" + custName + "'," + crAmt + "," + drAmt + ",'" + date + "','" + enterBy + "',2,'" + user + "',"
                        + batchNo + ",substring('" + details + "',1,60),CURRENT_TIMESTAMP(),'" + brCode + "','" + acNo.substring(0, 2) + "')");
                int var7 = insertQuery.executeUpdate();
                if (var7 <= 0) {
                    throw new ApplicationException("Batch could not be deleted");
                }
                Query deleteQuery = em.createNativeQuery("DELETE FROM recon_trf_d WHERE acno='" + acNo + "' AND recno = " + recNo + " and dt = '" + date + "' and org_brnid='" + brCode + "'");
                int var8 = deleteQuery.executeUpdate();
                if (var8 <= 0) {
                    throw new ApplicationException("Batch could not be deleted");
                }

                if ((acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) && (trandesc == 3)) {
                    List loanAccSec = em.createNativeQuery("select date_format(fromdt,'%Y%m%d') from cbs_loan_interest_post_ac_wise a where acno = '" + acNo + "' and flag = 'I' and date_format(postingDt,'%Y%m%d') = '" + date + "'").getResultList();
                    if (!loanAccSec.isEmpty()) {
                        Vector loanAccSecVect = (Vector) loanAccSec.get(0);
                        String fromDT = (String) loanAccSecVect.get(0);
                        Query updateLoanSec = em.createNativeQuery("update cbs_loan_interest_post_ac_wise set post_flag = 'N' where acno = '" + acNo + "' and flag = 'I' and date_format(postingDt,'%Y%m%d') = '" + date + "'");

                        updateLoanSec.executeUpdate();
                        Query updateLoanPostAcWise = em.createNativeQuery("update cbs_loan_acc_mast_sec set int_calc_upto_dt = DATE_FORMAT(DATE_ADD('" + fromDT + "', INTERVAL -1 DAY),'%Y%m%d'),next_int_calc_dt = '" + fromDT + "' where acno = '" + acNo + "' ");
                        updateLoanPostAcWise.executeUpdate();
                    }
                } else if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC) && trandesc == 3) {
                    int maxSNo = getMaxSno(acNo);
                    Query delMaxRow = em.createNativeQuery("DELETE FROM cbs_loan_interest_post_ac_wise WHERE SNO=" + maxSNo + " AND ACNO='" + acNo + "'");
                    int delMaxRowNo = delMaxRow.executeUpdate();
                    if (delMaxRowNo <= 0) {
                        throw new ApplicationException("Deletion Problem From CBS LOAN INTEREST POST AC WISE table");
                    }

                    int secondMaxSNo = getMaxSno(acNo);
                    List maxRowList = em.createNativeQuery("SELECT date_format(TODT,'%Y%m%d') FROM cbs_loan_interest_post_ac_wise WHERE SNO=" + secondMaxSNo + " AND ACNO='" + acNo + "' ").getResultList();
                    String postDt = "";
                    if (!maxRowList.isEmpty()) {
                        Vector maxRowVector = (Vector) maxRowList.get(0);
                        postDt = maxRowVector.get(0).toString();
                    }
                    String nextCalcDt = CbsUtil.dateAdd(postDt, 1);
                    Query updateQuery = em.createNativeQuery("UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '" + postDt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acNo + "'");
                    int updateNo = updateQuery.executeUpdate();
                    if (updateNo <= 0) {
                        throw new ApplicationException("Updation Problem In CBS LOAN ACC MAST SEC table");
                    }
                }
                //STR 
                List isAlertList = em.createNativeQuery("select * from cbs_str_detail where acno = '" + acNo + "' and dt= '" + date + "' and recno = " + recNo + " and auth_status = 'N'").getResultList();
                if (!isAlertList.isEmpty()) {
                    Query updateQuery = em.createNativeQuery("update cbs_str_detail set batch_no = " + batchNo + ",auth_status = 'D',auth_by = '" + enterBy + "',update_date = now() where acno = '" + acNo + "' and dt= '" + date + "' and recno = " + recNo + " and auth_status = 'N'");
                    int updatetResult = updateQuery.executeUpdate();
                    if (updatetResult <= 0) {
                        throw new ApplicationException("Problem in data updation for cbs_str_detail");
                    }
                }
            }
            ut.commit();
            return "True";
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

    public int getMaxSno(String acno) {
        int maxSNo = 0;
        List maxSnoList = em.createNativeQuery("SELECT ifnull(MAX(SNO),'0') FROM  cbs_loan_interest_post_ac_wise WHERE ACNO='" + acno + "' AND FLAG = 'I'").getResultList();
        if (!maxSnoList.isEmpty()) {
            Vector maxSnoVector = (Vector) maxSnoList.get(0);
            maxSNo = Integer.parseInt(maxSnoVector.get(0).toString());
        }
        return maxSNo;
    }

    public String updateDetailOfBatch(float batchNo, String acno, float recNo, String date, String oldDetail, String newDetail, String enterBy, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            String accountCode = ftsRemote.getAccountCode(acno);
            List resultList = batchDetailGridLoad(date, batchNo, brCode);
            if (resultList.isEmpty()) {
                throw new ApplicationException("This batch has been already deleted !!!");
            }
            if (accountCode.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                List chkLt = em.createNativeQuery("SELECT ACNAME,msgflag FROM gltable WHERE ACNO='" + acno + "'").getResultList();
                if (chkLt.isEmpty()) {
                    throw new ApplicationException("Account details does not exist");
                }
                Vector chkLtVect = (Vector) chkLt.get(0);
                String tempAcname = chkLtVect.get(0).toString();
                String tempMsg = chkLtVect.get(1).toString();
                
                if (tempMsg.equalsIgnoreCase("50")) {
                    throw new ApplicationException("Share detail could not be updated !!!");
                }

                if (tempAcname.equalsIgnoreCase("PAY ORDER")) {
                    throw new ApplicationException("Pay order detail could not be updated !!!");
                }
            }
            Query updateQuery = em.createNativeQuery("UPDATE recon_trf_d SET Details = '" + newDetail + "' WHERE trsno=" + batchNo + " AND "
                    + " ACNO='" + acno + "' AND recno=" + recNo + " AND DT='" + date + "' AND Details='" + oldDetail + "' AND EnterBy='" + enterBy + "' AND org_brnid='" + brCode + "'");
            var = updateQuery.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem occured in batch detail updation !!!");
            }
            ut.commit();
            return "Batch detail updated succesfully.";
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

    public List<PrizmMasterPojo> retrieveUnAuthData(String orgnCode, String todayDate) throws ApplicationException {
        List<PrizmMasterPojo> unAuthList = new ArrayList<PrizmMasterPojo>();
        try {
            List dataList = em.createNativeQuery("select cbs_status,acno,card_no,card_expiry_dt,"
                    + "date_format(cbs_reg_dt,'%d/%m/%Y'),min_limit,enter_by from "
                    + "prizm_card_master where substring(acno,1,2)='" + orgnCode + "' and "
                    + "date_format(enter_time,'%Y%m%d')='" + ymd.format(dmy.parse(todayDate)) + "' and auth='N'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to verify.");
            }
            for (int i = 0; i < dataList.size(); i++) {
                PrizmMasterPojo pojo = new PrizmMasterPojo();
                Vector ele = (Vector) dataList.get(i);
                String cbsStatus = ele.get(0).toString();
                if (cbsStatus.equals("A")) {
                    cbsStatus = "ADD";
                } else if (cbsStatus.equals("M")) {
                    cbsStatus = "MODIFY";
                } else if (cbsStatus.equals("E")) {
                    cbsStatus = "EXPIRY";
                } else if (cbsStatus.equals("L")) {
                    cbsStatus = "LOST";
                } else if (cbsStatus.equals("S")) {
                    cbsStatus = "STOLEN";
                } else if (cbsStatus.equals("C")) {
                    cbsStatus = "A/C CLOSE";
                } else if (cbsStatus.equals("R")) {
                    cbsStatus = "RE-PIN";
                } else if (cbsStatus.equals("N")) {
                    cbsStatus = "RE-SEND";
                }

//                String pinStatus = ele.get(4).toString();
//                if (pinStatus.equalsIgnoreCase("Y")) {
//                    pinStatus = "FRESH PIN";
//                } else if (pinStatus.equalsIgnoreCase("R")) {
//                    pinStatus = "RE-ISSUE-PIN";
//                }

                pojo.setAcStatus(cbsStatus);
                pojo.setAcno(ele.get(1).toString());
                pojo.setCardNo(ele.get(2).toString());
                pojo.setCardExpiryDate(ele.get(3).toString());
//                pojo.setPinStatus(pinStatus);
                pojo.setRegDate(ele.get(4).toString());
                pojo.setMinLimit(ele.get(5).toString());
                pojo.setEnterBy(ele.get(6).toString());

                unAuthList.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return unAuthList;
    }

    public String verifyPrizmAtmRegistration(String function, String acno, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select cbs_status,acno from prizm_card_master "
                    + "where acno='" + acno + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data to verify for A/c No:" + acno);
            }
            Vector ele = (Vector) list.get(0);
            String status = ele.get(0).toString().trim();

            String fileStatus = "";
            if (status.equals("M")) {
                fileStatus = "V";


//                list = em.createNativeQuery("select pin_status from prizm_card_master_his where "
//                        + "acno='" + acno + "' and sno in(select max(sno) from prizm_card_master_his "
//                        + "where acno='" + acno + "')").getResultList();
//                ele = (Vector) list.get(0);
//                String prePinStatus = ele.get(0).toString().trim();
//
//                list = em.createNativeQuery("select pin_status from prizm_card_master "
//                        + "where acno='" + acno + "'").getResultList();
//                ele = (Vector) list.get(0);
//                String curPinStatus = ele.get(0).toString().trim();
//                fileStatus = prePinStatus.equals(curPinStatus) ? "V" : ""; //File will go only on pin modification.
            }

            int n = em.createNativeQuery("update prizm_card_master set auth='Y',"
                    + "auth_by='" + userName + "',auth_time=current_timestamp,"
                    + "file_status='" + fileStatus + "' where acno='" + acno + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Verification !");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public List getPrizmAtmRegisterdAcToModify(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select cbs_status,card_no,card_expiry_dt,min_limit "
                    + "from prizm_card_master where acno='" + acno + "' and auth='Y'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String addAcToPrizmAtm(String acno, BigDecimal minLimit, String cbsStatus,
            String userName, String flag) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select acno from prizm_card_master "
                    + "where acno='" + acno + "'").getResultList();
            if (flag.equals("A") && !list.isEmpty()) { //New Addition
                throw new ApplicationException("This a/c is already registered !");
            } else if (flag.equals("N") && !list.isEmpty()) { //Re-Sending
                int n = em.createNativeQuery("INSERT INTO prizm_card_master_his(cbs_status,acno,card_no,card_expiry_dt,"
                        + "cbs_reg_dt,min_limit,file_status,enter_by,enter_time,auth,auth_by,auth_time,"
                        + "reject_reason,ret_update_by,ret_update_dt) select cbs_status,acno,card_no,card_expiry_dt,"
                        + "cbs_reg_dt,min_limit,file_status,enter_by,enter_time,auth,auth_by,auth_time,"
                        + "reject_reason,ret_update_by,ret_update_dt from prizm_card_master "
                        + "where acno='" + acno + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In History Insertion !");
                }

                n = em.createNativeQuery("delete from prizm_card_master where acno='" + acno + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Re-Sending Deletion !");
                }
            }

            int n = em.createNativeQuery("INSERT INTO prizm_card_master(cbs_status,acno,card_no,card_expiry_dt,"
                    + "cbs_reg_dt,min_limit,file_status,enter_by,enter_time,auth,auth_by,reject_reason,"
                    + "ret_update_by) VALUES('" + cbsStatus + "','" + acno + "','','',"
                    + "'" + ymd.format(new Date()) + "'," + minLimit + ",'','" + userName + "',"
                    + "current_timestamp,'N','','','')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In ATM Registration !");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public String expLostStolenAndCloseProcess(String acno, String cbsStatus,
            String userName, String cardNo, String expDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int n = em.createNativeQuery("INSERT INTO prizm_card_master_his(cbs_status,acno,card_no,card_expiry_dt,"
                    + "cbs_reg_dt,min_limit,file_status,enter_by,enter_time,auth,auth_by,auth_time,"
                    + "reject_reason,ret_update_by,ret_update_dt) select cbs_status,acno,card_no,card_expiry_dt,"
                    + "cbs_reg_dt,min_limit,file_status,enter_by,enter_time,auth,auth_by,auth_time,"
                    + "reject_reason,ret_update_by,ret_update_dt from prizm_card_master "
                    + "where acno='" + acno + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In History Insertion !");
            }
            n = em.createNativeQuery("update prizm_card_master set cbs_status='" + cbsStatus + "',"
                    + "enter_by='" + userName + "',enter_time=current_timestamp,auth='N',auth_by='',"
                    + "auth_time=null,card_no='" + cardNo + "',card_expiry_dt='" + expDt + "' where "
                    + "acno='" + acno + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In prizm_card_master Updation !");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public String modifyProcess(String cbsStatus, String acno, String cardNo, String expDt,
            BigDecimal minLimit, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int n = em.createNativeQuery("INSERT INTO prizm_card_master_his(cbs_status,acno,card_no,card_expiry_dt,"
                    + "cbs_reg_dt,min_limit,file_status,enter_by,enter_time,auth,auth_by,auth_time,"
                    + "reject_reason,ret_update_by,ret_update_dt) select cbs_status,acno,card_no,card_expiry_dt,"
                    + "cbs_reg_dt,min_limit,file_status,enter_by,enter_time,auth,auth_by,auth_time,"
                    + "reject_reason,ret_update_by,ret_update_dt from prizm_card_master "
                    + "where acno='" + acno + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In History Insertion !");
            }

            n = em.createNativeQuery("update prizm_card_master set cbs_status='" + cbsStatus + "',"
                    + "card_no='" + cardNo + "',card_expiry_dt='" + expDt + "',"
                    + "min_limit=" + minLimit + ",enter_by='" + userName + "',enter_time=current_timestamp,"
                    + "auth='N',auth_by='',auth_time=null where acno='" + acno + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In prizm_card_master Updation !");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    @Override
    public String generatePrizmCardInputFile(String userName, String todayDate, String orgnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select p.cbs_status,p.acno,p.card_no,p.card_expiry_dt,'',m.customerid,"
                    + "ifnull(m.CustFullName,'') as custname,ifnull(m.title,'') as title,ifnull(m.mailaddressline1,'') as "
                    + "mailaddressline1,ifnull(m.mailvillage,'') as city,ifnull(ref2.ref_desc,'') as state,"
                    + "ifnull(m.mailpostalcode,'') as mailpostalcode,ifnull(m.mailphonenumber,'') as mailphonenumber,"
                    + "ifnull(m.mobilenumber,'') as mobilenumber,ifnull(m.dateofbirth,'') as dob from prizm_card_master p,"
                    + "cbs_customer_master_detail m,accountmaster a,customerid c,cbs_ref_rec_type ref2 where "
                    + "m.customerid=c.custid and c.acno=a.acno and m.mailstatecode=ref2.ref_code and ref2.ref_rec_no='002' "
                    + "and p.acno=a.acno and p.cbs_reg_dt<='" + ymd.format(dmy.parse(todayDate)) + "' and "
                    + "p.file_status='' and p.auth='Y' and substring(p.acno,1,2)='" + orgnCode + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data to generate the file. "
                        + "Please check mandatory fields for A/c at customer level.");
            }
            //Required values
            List valList = em.createNativeQuery("select code from cbs_parameterinfo where name='ISSUER_NR'").getResultList();
            Vector ele = (Vector) valList.get(0);
            if (valList.isEmpty() || ele.get(0) == null || ele.get(0).toString().equals("")
                    || ele.get(0).toString().trim().length() != 3) {
                throw new ApplicationException("Please define value of ISSUER_NR in cbs_parameterinfo.");
            }
            String issuerNr = ele.get(0).toString().trim();

            valList = em.createNativeQuery("select code from cbs_parameterinfo where name='CARD_PROGRAM'").getResultList();
            ele = (Vector) valList.get(0);
            if (valList.isEmpty() || ele.get(0) == null || ele.get(0).toString().trim().equals("")
                    || ele.get(0).toString().trim().length() != 3) {
                throw new ApplicationException("Please define value of CARD_PROGRAM in cbs_parameterinfo.");
            }
            String cardProgram = ele.get(0).toString().trim();

            valList = em.createNativeQuery("select ifnull(max(file_seq_no)+1,1) as seq_no from "
                    + "prizm_file_seq_detail").getResultList();
            ele = (Vector) valList.get(0);
            String seqNo = ele.get(0).toString();

            //Retrieving Writing Folder.
            valList = em.createNativeQuery("select aadhar_location from mb_sms_sender_bank_detail").getResultList();
            ele = (Vector) valList.get(0);
            if (ele.get(0) == null || ele.get(0).toString().trim().equals("")) {
                throw new ApplicationException("Please define Aadhar Location.");
            }
            String aadharLocation = ele.get(0).toString().trim();


            String cardDt = dtFormat.format(new Date());
            String genFileName = "CARDFILE_" + issuerNr.toUpperCase() + "_" + cardProgram.toUpperCase()
                    + "_" + ParseFileUtil.addTrailingZeros(seqNo, 10) + "_" + cardDt + ".dat";

            int n = em.createNativeQuery("INSERT INTO prizm_file_seq_detail(file_seq_no,file_gen_date,"
                    + "file_name,gen_by,gen_time,br_code) VALUES(" + Long.parseLong(seqNo) + ","
                    + "'" + ymd.format(dmy.parse(todayDate)) + "','" + genFileName + "','" + userName + "',"
                    + "current_timestamp,'" + orgnCode + "')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in file generation detail.");
            }

            String totalRecords = String.valueOf(list.size());
            //Header Preparation.
            FileWriter fw = new FileWriter(aadharLocation + genFileName);
            String header = "HEADER" + issuerNr + cardProgram + "356" + cardDt + "\n";
            fw.write(header);
            //Data Preparation.
            for (int i = 0; i < list.size(); i++) {
                ele = (Vector) list.get(i);
                String opType = ele.get(0).toString().trim();
                String acno = ele.get(1).toString().trim();
                String cardNo = ele.get(2).toString().trim();
                String cardExpiry = ele.get(3).toString().trim();
//                String pinStatus = ele.get(4).toString().trim();
                String custId = ele.get(5).toString().trim();
                String custname = ele.get(6).toString().trim();
                String title = ele.get(7).toString().trim();
                String mailAddressLine1 = ele.get(8).toString().trim();
                String mailCity = ele.get(9).toString().trim();
                String mailState = ele.get(10).toString().trim();
                String pinNo = ele.get(11).toString().trim();
                String mobile = ele.get(13).toString().trim();
                String dob = ele.get(14).toString().trim();

                String acPriority = opType.equals("C") ? "C" : "1";
                String cardStatus = "00";
                if (opType.equals("L")) {
                    cardStatus = "41";
                } else if (opType.equals("S")) {
                    cardStatus = "43";
                } else if (opType.equals("C")) {
                    cardStatus = "45";
                }
                String cardIssueStatus = "01";
                if (opType.equals("L") || opType.equals("S")) {
                    cardIssueStatus = "02";
                } else if (opType.equals("E")) {
                    cardIssueStatus = "03";
                }

                String pinStatus = "";
                if (opType.equals("A") || opType.equals("N")) {
                    pinStatus = "Y";
                } else if (opType.equals("R")) {
                    pinStatus = "R";
                } else { //E,L,S,C
                    pinStatus = "N";
                }

                if (opType.equalsIgnoreCase("C")) {
                    opType = "D";
                } else if (opType.equalsIgnoreCase("N")) {
                    opType = "A";
                } else if (opType.equalsIgnoreCase("R") || opType.equalsIgnoreCase("E")
                        || opType.equalsIgnoreCase("L") || opType.equalsIgnoreCase("S")) {
                    opType = "M";
                }
                if ((opType.equals("M") || opType.equals("D")) && cardNo.length() != 16) {
                    throw new ApplicationException("Please fill proper card no of 16 digit for a/c:" + acno);
                }
                if (cardNo.equals("") || cardNo.length() != 16) {
                    cardNo = "";
                    cardExpiry = "";
                } else {
                    if (cardExpiry.equals("") || cardExpiry.length() != 10) {
                        throw new ApplicationException("Please fill proper card expiry for a/c:" + acno);
                    }
                    cardExpiry = ym.format(dmy.parse(cardExpiry));
                }
                if (custname.equals("")) {
                    throw new ApplicationException("Please fill proper custname for a/c:" + acno);
                }
                custname = custname.replaceAll("[\\W_]", " ");
                custname = custname.length() > 25 ? custname.substring(0, 25) : custname;

                title = title.replaceAll("[\\W_]", " ");
                title = title.length() > 10 ? title.substring(0, 10) : title;

                if (mailAddressLine1.equals("") || mailCity.equals("") || mailState.equals("")) {
                    throw new ApplicationException("Please check mail address1,mail city and mail state for a/c:" + acno + " and "
                            + "customer id is-->" + custId);
                }
                mailAddressLine1 = mailAddressLine1.length() > 45 ? mailAddressLine1.substring(0, 45) : mailAddressLine1;
                mailCity = mailCity.replaceAll("[\\W_]", " ").trim();
                mailCity = mailCity.length() > 25 ? mailCity.substring(0, 25) : mailCity;
                mailState = mailState.length() > 20 ? mailState.substring(0, 20) : mailState;

                if (pinNo.equals("") || pinNo.length() != 6 || new Validator().validateInteger(pinNo) == false) {
                    pinNo = "";
                }
                if (mobile.equals("") || mobile.length() != 10 || new Validator().validateInteger(mobile) == false) {
                    mobile = "";
                }
                if (dob.equals("")) {
                    throw new ApplicationException("Please fill date of birth of custname for a/c:" + acno);
                }
                dob = ymd.format(ymdOne.parse(dob));

                String firstAndLastName = custname.trim();
                int spaceIndex = firstAndLastName.indexOf(" ");
                if (spaceIndex != -1) {
                    firstAndLastName = firstAndLastName.substring(0, spaceIndex).trim();
                }

                //Addition on 24/11/2015
                String defaultAcType = "", acType = "";
                List acCodeDetailList = reportRemote.getAcctCodeDetails(acno.substring(2, 4));
                Vector acCodeVec = (Vector) acCodeDetailList.get(0);
                String acNature = acCodeVec.get(0).toString();
                String acCodeType = acCodeVec.get(1).toString();
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) && acCodeType.equalsIgnoreCase("CA")) {
                    defaultAcType = "20";
                    acType = "20";
                } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) && acCodeType.equalsIgnoreCase("CC")) {
                    defaultAcType = "30";
                    acType = "30";
                } else if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    defaultAcType = acno.substring(2, 4);
                    acType = acno.substring(2, 4);
                }
                //End here

                String detailStr = opType + ParseFileUtil.addSuffixSpaces(custId, 15)
                        + ParseFileUtil.addSuffixSpaces(cardNo, 19) + ParseFileUtil.addSuffixSpaces(custname, 25)
                        + ParseFileUtil.addSuffixSpaces("", 25) + ParseFileUtil.addSuffixSpaces(title, 10)
                        + ParseFileUtil.addSuffixSpaces(firstAndLastName, 25) + ParseFileUtil.addSuffixSpaces("", 10)
                        + ParseFileUtil.addSuffixSpaces(firstAndLastName, 25) + ParseFileUtil.addSuffixSpaces(mailAddressLine1, 45)
                        + ParseFileUtil.addSuffixSpaces("", 45) + ParseFileUtil.addSuffixSpaces(mailCity, 25)
                        + ParseFileUtil.addSuffixSpaces(mailState, 20) + ParseFileUtil.addSuffixSpaces("IN", 3)
                        + ParseFileUtil.addSuffixSpaces(pinNo, 6) + ParseFileUtil.addSuffixSpaces("", 15)
                        + ParseFileUtil.addSuffixSpaces(mobile, 10) + ParseFileUtil.addSuffixSpaces("", 15)
                        + ParseFileUtil.addSuffixSpaces("", 25) + ParseFileUtil.addSuffixSpaces(dob, 8)
                        + defaultAcType + ParseFileUtil.addSuffixSpaces(acno, 28) + acType
                        + acPriority + ParseFileUtil.addSuffixSpaces("", 31) + ParseFileUtil.addSuffixSpaces("", 31)
                        + ParseFileUtil.addSuffixSpaces("", 31) + ParseFileUtil.addSuffixSpaces("", 31)
                        + ParseFileUtil.addSuffixSpaces("", 31) + ParseFileUtil.addSuffixSpaces("", 31)
                        + ParseFileUtil.addSuffixSpaces("", 31) + ParseFileUtil.addSuffixSpaces("", 31)
                        + ParseFileUtil.addSuffixSpaces("", 31) + ParseFileUtil.addSuffixSpaces(cardExpiry, 4)
                        + cardStatus + cardIssueStatus + pinStatus + ParseFileUtil.addSuffixSpaces("", 16)
                        + ParseFileUtil.addSuffixSpaces("", 4) + "\n";

                fw.write(detailStr);
                //File Status Updation In prizm_card_master.
                n = em.createNativeQuery("update prizm_card_master set file_status='V' "
                        + "where acno = '" + acno + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Status Updation.");
                }
            }
            //Trailer Preparation.
            String trailer = "TRAILER" + ParseFileUtil.addTrailingZeros(totalRecords, 10);
            fw.write(trailer);
            fw.close();
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public List<NpciFileDto> showGeneratedFiles(String fileShowDt, String orgnCode) throws ApplicationException {
        List<NpciFileDto> dataList = new ArrayList<NpciFileDto>();
        try {
            List list = null;
            String alphaCode = reportRemote.getAlphacodeByBrncode(orgnCode);
            if (alphaCode.equalsIgnoreCase("HO")) {
                list = em.createNativeQuery("select file_seq_no,file_name,gen_by from prizm_file_seq_detail "
                        + "where file_gen_date='" + ymd.format(dmy.parse(fileShowDt)) + "' order "
                        + "by file_seq_no").getResultList();
            } else {
                list = em.createNativeQuery("select file_seq_no,file_name,gen_by from prizm_file_seq_detail "
                        + "where file_gen_date='" + ymd.format(dmy.parse(fileShowDt)) + "' and "
                        + "br_code = '" + orgnCode + "' order by file_seq_no").getResultList();
            }
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data to show.");
            }
            for (int i = 0; i < list.size(); i++) {
                NpciFileDto dto = new NpciFileDto();
                Vector ele = (Vector) list.get(i);

                dto.setFileNo(new BigInteger(ele.get(0).toString()));
                dto.setFileName(ele.get(1).toString());
                dto.setFileGenBy(ele.get(2).toString());

                dataList.add(dto);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public boolean checkPrizmRegisteredAc(String acno) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select * from prizm_card_master where "
                    + "acno='" + acno + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("This a/c is not registered.");
            }
            return true;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<PrizmCardHolderPojo> getPrizmAcDetails(String acno, String fromDt,
            String toDt, String flag, String brncode) throws ApplicationException {
        List<PrizmCardHolderPojo> dataList = new ArrayList<PrizmCardHolderPojo>();
        try {
            List list = new ArrayList();
            if (flag.equals("I")) {
                if (acno == null || acno.equals("")) {
                    throw new ApplicationException("A/c number can not blank.");
                }
                list = em.createNativeQuery("select case cbs_status when 'A' then 'ADD' when 'V' then 'VERIFY' when 'N' "
                        + "then 'RE-SEND' when 'M' then 'MODIFY' when 'R' then 'RE-PIN' when 'E' then 'EXPIRY' when 'L' "
                        + "then 'LOST' when 'S' then 'STOLEN' when 'C' then 'A/C CLOSE' end as cbs_status,acno,card_no,"
                        + "card_expiry_dt,date_format(cbs_reg_dt,'%d/%m/%Y') as cbs_reg_dt,min_limit,"
                        + "date_format(enter_time,'%d/%m/%Y') as update_date,enter_by,auth_by,case file_status "
                        + "when 'V' then 'Card File Generated' else '' end as card_file_status,'CURRENT' as "
                        + "ac_status,enter_time from prizm_card_master where acno='" + acno + "' "
                        + "union "
                        + "select case cbs_status when 'A' then 'ADD' when 'V' then 'VERIFY'  when 'N' "
                        + "then 'RE-SEND' when 'M' then 'MODIFY' when 'R' then 'RE-PIN' when 'E' then 'EXPIRY' "
                        + "when 'L' then 'LOST' when 'S' then 'STOLEN' when 'C' then 'A/C CLOSE' end as cbs_status,"
                        + "acno,card_no,card_expiry_dt,date_format(cbs_reg_dt,'%d/%m/%Y') as cbs_reg_dt,min_limit,"
                        + "date_format(enter_time,'%d/%m/%Y') as update_date,enter_by,auth_by,case file_status "
                        + "when 'V' then 'Card File Generated' else '' end as card_file_status,'HISTORY' as "
                        + "ac_status,enter_time from prizm_card_master_his where acno='" + acno + "' order "
                        + "by enter_time desc").getResultList();
            } else if (flag.equals("D")) {
                if (fromDt == null || toDt == null || fromDt.equals("") || toDt.equals("")) {
                    throw new ApplicationException("From date and To date should be proper.");
                }
                list = em.createNativeQuery("select case cbs_status when 'A' then 'ADD' when 'V' then 'VERIFY' "
                        + "when 'M' then 'MODIFY' when 'R' then 'RE-PIN' when 'E' then 'EXPIRY' when 'L' then "
                        + "'LOST' when 'S' then 'STOLEN' when 'N' then 'RE-SEND' when 'C' then 'A/C CLOSE' end "
                        + "as cbs_status,acno,card_no,card_expiry_dt,date_format(cbs_reg_dt,'%d/%m/%Y') as cbs_reg_dt,min_limit,"
                        + "date_format(enter_time,'%d/%m/%Y') as update_date,enter_by,auth_by,case "
                        + "file_status when 'V' then 'Card File Generated' else '' end as "
                        + "card_file_status,'CURRENT' as ac_status from prizm_card_master where "
                        + "cbs_reg_dt between '" + ymd.format(dmy.parse(fromDt)) + "' and "
                        + "'" + ymd.format(dmy.parse(toDt)) + "' and "
                        + "substring(acno,1,2)='" + brncode + "' order by enter_time").getResultList();
            }
            //List preparation
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                PrizmCardHolderPojo obj = new PrizmCardHolderPojo();
                obj.setCbsStatus(ele.get(0).toString().trim());
                obj.setAcno(ele.get(1).toString().trim());
                obj.setCardNo(ele.get(2).toString().trim());
                obj.setCardExpDt(ele.get(3).toString().trim());
                obj.setCbsRegDt(ele.get(4).toString().trim());
                obj.setMinLimit(new BigDecimal(ele.get(5).toString().trim()));
                obj.setUpdateDt(ele.get(6).toString().trim());
                obj.setEnterBy(ele.get(7).toString().trim());
                obj.setAuthBy(ele.get(8).toString().trim());
                obj.setCardFileStatus(ele.get(9).toString().trim());
                obj.setAcStatus(ele.get(10).toString().trim());

                dataList.add(obj);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }
}
