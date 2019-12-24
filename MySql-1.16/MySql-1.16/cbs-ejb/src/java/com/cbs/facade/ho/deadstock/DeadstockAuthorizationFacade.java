package com.cbs.facade.ho.deadstock;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
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

@Stateless(mappedName = "DeadstockAuthorizationFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class DeadstockAuthorizationFacade implements DeadstockAuthorizationFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    TxnAuthorizationManagementFacadeRemote xferAuthorizationRemote;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting43;
    private UserTransaction ut;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    public List viewDataInGrid() throws ApplicationException {
        try {
            String date = ymd.format(new Date());
            List selectList = em.createNativeQuery("select t.trsno,t.ACNO,g.acName,t.Cr_amt,t.Dr_Amt,t.instno,t.Enter_By,t.auth,t.Details,"
                    + "t.recno,t.tran_date,t.DEST_BRN_ID,t.ORG_BRN_ID,t.ty,t.ITEM_code FROM deadstock_tran t, gltable g "
                    + "where t.acno=g.acno and t.auth='N' AND tran_date='" + date + "' and t.tran_desc in (80,81) order by t.trsno").getResultList();
            return selectList;
        } catch (Exception e) {
            throw new ApplicationException(e);
// return null;
        }
    }

    public String authorizeRecords(int trSNo, String AuthBy, String dt, String orgnBrnCode, String itemName) throws ApplicationException {
        String msg = "";
        ut = context.getUserTransaction();
        double totalDrAmt = 0.0;
        try {
            ut.begin();
            List userAuthLimit = em.createNativeQuery("SELECT COALESCE(TRANDEBIT,0.0) FROM securityinfo WHERE USERID='" + AuthBy + "' AND BRNCODE='" + orgnBrnCode + "'").getResultList();
            if (userAuthLimit.size() > 0) {
                for (int t = 0; t < userAuthLimit.size(); t++) {
                    Vector element = (Vector) userAuthLimit.get(t);
                    double userLimit = Double.parseDouble(element.get(0).toString());
                    if (userLimit == 0.0) {
                        ut.rollback();
                        return msg = "Your passing limit is less than withdrawal amount. You can not authorize this transaction";
                    } else if (totalDrAmt > userLimit) {
                        ut.rollback();
                        return msg = "Your passing limit is less than withdrawal amount. You can not authorize this transaction";
                    }
                }
            } else {
                ut.rollback();
                return msg = "Your passing limit is less than withdrawal amount. You can not authorize this transaction";
            }
            List enterByList = em.createNativeQuery("SELECT ENTER_BY FROM deadstock_tran WHERE TRSNO =" + trSNo).getResultList();
            if (enterByList.size() > 0) {
                Vector enterByVect = (Vector) enterByList.get(0);
                String enterBy = enterByVect.get(0).toString();
                if (enterBy.toUpperCase().equals(AuthBy.toUpperCase())) {
                    ut.rollback();
                    return msg = "You can not Pass Your Own Entry.";
                }
            }
            long itemDistinctSno = 0;
            Vector maxRecord = (Vector) em.createNativeQuery("select max(ITEM_DISTINCTIVE_SNO) from deadstock_items").getSingleResult();
            if (maxRecord == null || maxRecord.get(0) == null) {
                itemDistinctSno = 0;
            } else {
                itemDistinctSno = Integer.parseInt(maxRecord.get(0).toString());
            }
            List tranTempRecords = em.createNativeQuery("select TRAN_DATE,ITEM_CODE,ORG_BRN_ID,"
                    + "DEST_BRN_ID,ENTER_BY,TRAN_DESC,ITEM_QTY,ITEM_RATE,tran_mode from deadstock_tran where trsno=" + trSNo + " and auth='N' order by recno desc").getResultList();
            int itemCode = 0;
            double rate = 0.0;
            String purchaseDate = "";
            String enterBy = "";
            String branch = "";
            String forBranch = "";
            int tranDesc = 0;
            String resBrn = "";
            int qty = 0;
            int tranMode = 0; //Purchase-1 or Transfer-2
            String currentDate = ymd.format(new Date());
            if (!tranTempRecords.isEmpty()) {
                for (int i = 0; i < tranTempRecords.size(); i++) {
                    Vector ele = (Vector) tranTempRecords.get(i);
                    if (ele.get(0) != null) {
                        purchaseDate = ymd.format((Date) ele.get(0));
                    }
                    if (ele.get(1) != null) {
                        itemCode = Integer.parseInt(ele.get(1).toString());
                    }
                    if (ele.get(2) != null) {
                                                                                                                                                                              branch = ele.get(2).toString();
                    }
                     if (ele.get(3) != null) {
                        forBranch = ele.get(3).toString();
                        if (!branch.equalsIgnoreCase(forBranch)) {
                            resBrn = forBranch;
                        }
                    }
                    if (ele.get(4) != null) {
                        enterBy = ele.get(4).toString();
                    }
                    if (ele.get(5) != null) {
                        tranDesc = Integer.parseInt(ele.get(5).toString());
                    }
                    if (ele.get(6) != null) {
                        qty = Integer.parseInt(ele.get(6).toString());
                    }
                    if (ele.get(7) != null) {
                        rate = Double.parseDouble(ele.get(7).toString());
                    }
                    if (ele.get(8) != null) {
                        tranMode = Integer.parseInt(ele.get(8).toString());
                    }
                }
            }
            if (tranDesc == 80 && tranMode == 1) {
                Query insertQuery2 = em.createNativeQuery("insert into recon_trf_d (Acno,Dt,Dramt,Cramt,Ty,Trantype,Recno,Trsno,Instno,Payby,iy,TranDesc,Details,org_brnid,dest_brnid, Trantime,Auth,EnterBy,AuthBy,valuedt,SubTokenNo,Tran_id,Term_id,adviceNo,adviceBrnCode)"
                        + "(select Acno,tran_date,Dr_amt,Cr_amt,Ty,Tran_type,Recno,Trsno,Instno,Pay_by,0 as iy,Tran_Desc,Details,org_brn_id,DEST_BRN_ID,Tran_time,Auth,Enter_By,Auth_By,tran_date,'A' as SubTokenNo,0 as Tran_id,'' as Term_id,'','' from deadstock_tran where trsno=" + trSNo + ")");
                int insertRecord2 = insertQuery2.executeUpdate();
                System.out.println("insertRecord2 ---->" + insertRecord2);
                if (insertRecord2 <= 0) {
                    ut.rollback();
                    return msg = "Some error has occured";
                }
                ut.commit();
                ut.begin();
                int updateRecon = em.createNativeQuery("update deadstock_tran set auth='Y', auth_by='" + AuthBy + "' where trsno=" + trSNo + " and auth='N' and tran_date='" + currentDate + "'").executeUpdate();
                if (updateRecon <= 0) {
                    ut.rollback();
                    return msg = "No record to authorize for current date";
                }
                for (int i = 0; i < qty; i++) {
                    long sno = ++itemDistinctSno;
                    Query purchaseQuery = em.createNativeQuery("insert into deadstock_items (TRSNO,TRAN_DATE,ITEM_CODE,ORG_BRN_ID,DEST_BRN_ID,ITEM_DISTINCTIVE_SNO,ORIGINAL_COST,ITEM_DEPRECIATION,ITEM_BOOK_VALUE,ENTER_BY,N0_OF_MOVEMENTS) values (?,?,?,?,?,?,?,?,?,?,?)");
                    purchaseQuery.setParameter(1, trSNo);
                    purchaseQuery.setParameter(2, purchaseDate);
                    purchaseQuery.setParameter(3, itemCode);
                    purchaseQuery.setParameter(4, branch);
                    purchaseQuery.setParameter(5, forBranch);
                    purchaseQuery.setParameter(6, sno);
                    purchaseQuery.setParameter(7, rate);
                    purchaseQuery.setParameter(8, 0.0);
                    purchaseQuery.setParameter(9, rate);
                    purchaseQuery.setParameter(10, AuthBy);
                    purchaseQuery.setParameter(11, 1);
                    int insertIntoPurchase = purchaseQuery.executeUpdate();
                    if (insertIntoPurchase <= 0) {
                        ut.rollback();
                        System.out.println("error in insert deadstock_items in case of p");
                        msg = "Some error has occured";
                        break;
                    }
                    Query movementInsertQuery = em.createNativeQuery("insert into deadstock_movement (trsno,tran_date,item_code,ORG_BRN_ID,DEST_BRN_ID,ITEM_DISTINCTIVE_SNO,MOVEMENT_TYPE,ENTER_BY) values(?,?,?,?,?,?,?,?)");
                    movementInsertQuery.setParameter(1, trSNo);
                    movementInsertQuery.setParameter(2, purchaseDate);
                    movementInsertQuery.setParameter(3, itemCode);
                    movementInsertQuery.setParameter(4, branch);
                    movementInsertQuery.setParameter(5, forBranch);
                    movementInsertQuery.setParameter(6, sno);
                    movementInsertQuery.setParameter(7, "P"); // for Purchasing
                    movementInsertQuery.setParameter(8, AuthBy);
                    int insertIntoMovement = movementInsertQuery.executeUpdate();
                    if (insertIntoMovement <= 0) {
                        ut.rollback();
                        System.out.println("error in insert deadstock_movement in case of p");
                        msg = "Some error has occured";
                        break;
                    }
                }
                try {
                    msg = authorizeTran(trSNo, AuthBy, dt, orgnBrnCode);
                    if (!msg.equalsIgnoreCase("TRUE")) {
                        ut.rollback();
                        ut.begin();
                        int x = em.createNativeQuery("delete from recon_trf_d where trsno=" + trSNo).executeUpdate();
                        System.out.println("delted entry " + x);
                        ut.commit();
                        return msg;
                    } else {
                        ut.commit();
                        return msg;
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
            } else if (tranDesc == 80 && tranMode == 2) {
                Query insertQuery2 = em.createNativeQuery("insert into recon_trf_d (Acno,Dt,Dramt,Cramt,Ty,Trantype,Recno,Trsno,Instno,Payby,iy,TranDesc,Details,org_brnid,dest_brnid, Trantime,Auth,EnterBy,AuthBy,valuedt,SubTokenNo,Tran_id,Term_id,adviceNo,adviceBrnCode)"
                        + "(select Acno,tran_date,Dr_amt,Cr_amt,Ty,Tran_type,Recno,Trsno,Instno,Pay_by,0 as iy,Tran_Desc,Details,org_brn_id,DEST_BRN_ID,Tran_time,Auth,Enter_By,Auth_By,tran_date,'A' as SubTokenNo,0 as Tran_id,'' as Term_id,'','' from deadstock_tran where trsno=" + trSNo + ")");
                int insertRecord2 = insertQuery2.executeUpdate();
                ut.commit();
                ut.begin();
                if (insertRecord2 <= 0) {
                    ut.rollback();
                    return msg = "Some error has occured";
                }
                int updateRecon = em.createNativeQuery("update deadstock_tran set auth='Y', auth_by='" + AuthBy + "' where trsno=" + trSNo + " and auth='N' and tran_date='" + currentDate + "'").executeUpdate();
                if (updateRecon <= 0) {
                    ut.rollback();
                    System.out.println("error in update deadstock_tran in case of deadstock transfer");
                    return msg = "No record to authorize for current date";
                }
                try {
                    List distinctiveSnoList = em.createNativeQuery("select ITEM_DISTINCTIVE_SNO from deadstock_transfer_temp where trsno=" + trSNo + " and item_code=" + itemCode).getResultList();
                    if (distinctiveSnoList.size() > 0) {
                        int updateFlag = 0;
                        for (int i = 0; i < distinctiveSnoList.size(); i++) {
                            Vector vec = (Vector) distinctiveSnoList.get(i);
                            int sno = Integer.parseInt(vec.get(0).toString());
                            updateFlag = em.createNativeQuery("update deadstock_items set del_flag='Y',ORG_BRN_ID='" + branch + "', DEST_BRN_ID='" + resBrn + "',N0_OF_MOVEMENTS=2 where item_code=" + itemCode + " and ITEM_DISTINCTIVE_SNO=" + sno).executeUpdate();
                            if (updateFlag <= 0) {
                                msg = "Entry not authorized.";
                                break;
                            }
                            Query movementInsertQuery = em.createNativeQuery("insert into deadstock_movement (trsno,tran_date,item_code,ORG_BRN_ID,DEST_BRN_ID,ITEM_DISTINCTIVE_SNO,MOVEMENT_TYPE,ENTER_BY) values(?,?,?,?,?,?,?,?)");
                            movementInsertQuery.setParameter(1, trSNo);
                            movementInsertQuery.setParameter(2, purchaseDate);
                            movementInsertQuery.setParameter(3, itemCode);
                            movementInsertQuery.setParameter(4, branch);
                            movementInsertQuery.setParameter(5, resBrn);
                            movementInsertQuery.setParameter(6, sno);
                            movementInsertQuery.setParameter(7, "T"); // for Transfer
                            movementInsertQuery.setParameter(8, AuthBy);
                            int insertIntoMovement = movementInsertQuery.executeUpdate();
                            if (insertIntoMovement <= 0) {
                                ut.rollback();
                                System.out.println("error in insert deadstock_movement in case of T");
                                msg = "Some error has occured";
                                break;
                            }
                        }
                    }
                    try {
                        msg = authorizeTran(trSNo, AuthBy, dt, orgnBrnCode);
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            ut.rollback();
                            ut.begin();
                            int x = em.createNativeQuery("delete from recon_trf_d where trsno=" + trSNo).executeUpdate();
                            System.out.println("delted entry " + x);
                            ut.commit();
                            return msg;
                        } else {
                            ut.commit();
                            return "true";
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
            } else if (tranDesc == 80 && tranMode == 3)//Write Off Case
            {
                Query insertQuery2 = em.createNativeQuery("insert into recon_trf_d (Acno,Dt,Dramt,Cramt,Ty,Trantype,Recno,Trsno,Instno,Payby,iy,TranDesc,Details,org_brnid,dest_brnid, Trantime,Auth,EnterBy,AuthBy,valuedt,SubTokenNo,Tran_id,Term_id,adviceNo,adviceBrnCode)"
                        + "(select Acno,tran_date,Dr_amt,Cr_amt,Ty,Tran_type,Recno,Trsno,Instno,Pay_by,0 as iy,Tran_Desc,Details,org_brn_id,DEST_BRN_ID,Tran_time,Auth,Enter_By,Auth_By,tran_date,'A' as SubTokenNo,0 as Tran_id,'' as Term_id,'','' from deadstock_tran where trsno=" + trSNo + ")");
                int insertRecord2 = insertQuery2.executeUpdate();
                ut.commit();
                ut.begin();
                if (insertRecord2 <= 0) {
                    ut.rollback();
                    return msg = "Some error has occured";
                }
                int updateRecon = em.createNativeQuery("update deadstock_tran set auth='Y', auth_by='" + AuthBy + "' where trsno=" + trSNo + " and auth='N' and tran_date='" + currentDate + "'").executeUpdate();
                if (updateRecon <= 0) {
                    ut.rollback();
                    System.out.println("error in update deadstock_tran in case of deadstock transfer");
                    return msg = "No record to authorize for current date";
                }
                try {
                    List distinctiveSnoList = em.createNativeQuery("select ITEM_DISTINCTIVE_SNO,ITEM_PROFIT,ITEM_LOSS from deadstock_written_off where trsno=" + trSNo + " and item_code=" + itemCode).getResultList();
                    if (distinctiveSnoList.size() > 0) {
                        int delFlag1 = 0;
                        int delFlag2 = 0;
                        for (int i = 0; i < distinctiveSnoList.size(); i++) {
                            Vector vec = (Vector) distinctiveSnoList.get(i);
                            int sno = Integer.parseInt(vec.get(0).toString());
                            delFlag1 = em.createNativeQuery("delete from deadstock_items where item_code = " + itemCode + " and ITEM_DISTINCTIVE_SNO=" + sno).executeUpdate();
                            delFlag2 = em.createNativeQuery("delete from deadstock_movement where item_code = " + itemCode + " and ITEM_DISTINCTIVE_SNO=" + sno).executeUpdate();
                            if (delFlag1 <= 0 || delFlag2 <= 0) {
                                msg = "Entry not authorized.";
                                break;
                            }
                            int countUpdate = em.createNativeQuery("update deadstock_written_off set auth='Y', auth_by='" + AuthBy + "' where ITEM_DISTINCTIVE_SNO=" + sno).executeUpdate();
                            if (countUpdate <= 0) {
                                msg = "Entry not authorized.";
                                break;
                            }
                        }
                    }
                    try {
                        msg = authorizeTran(trSNo, AuthBy, dt, orgnBrnCode);
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            ut.rollback();
                            ut.begin();
                            int x = em.createNativeQuery("delete from recon_trf_d where trsno=" + trSNo).executeUpdate();
                            System.out.println("delted entry " + x);
                            ut.commit();
                            return msg;
                        } else {
                            ut.commit();
                            return "true";
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
            return "true" + msg;
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
//        catch (Exception e) {
//              return null;
//        }
    }

    private String authorizeTran(int trSNo, String AuthBy, String dt, String orgnBrnCode) throws ApplicationException {
        float trsno = (float) trSNo;
        String auth = null;
        String msgTr = "";
        int trsNumber = 0;
        String enterBy = "";
        String msg = "";
        try {
            String msg1 = cbsDestEntryIdentification(trsno);
            if (msg1.equalsIgnoreCase("TRUE")) {
                try {
                    msg = xferAuthorizationRemote.cbsCoreTrfAuth(trsno, AuthBy);
                    System.out.println("msg " + msg);
                    return msg;
                } catch (Exception e) {
                    throw new ApplicationException(e);
                }
            } else {
                /*When the Orgn Branch Code & Destination Branch both are same*/
                List enterByList1 = em.createNativeQuery("SELECT ENTERBY,VALUEDT,INSTDT FROM recon_trf_d WHERE TRSNO =" + trSNo).getResultList();
                if (enterByList1.size() > 0) {
                    Vector enterByVect = (Vector) enterByList1.get(0);
                    String enterBy1 = enterByVect.get(0).toString();
                    String valDt = enterByVect.get(1).toString();
                    String instrDt = null;
                    if ((enterByVect.get(2) == null) || (enterByVect.get(2).toString().equals(""))) {
                        instrDt = null;
                    } else {
                        instrDt = enterByVect.get(2).toString();
                    }
                    if (enterBy1.toUpperCase().equals(AuthBy.toUpperCase())) {
                        ut.rollback();
                        return "You can not Pass Your Own Entry.";
                    }
                    List getDataList = em.createNativeQuery("SELECT ACNO,CUSTNAME,TRANTYPE,INSTNO,CRAMT,AUTH,ENTERBY,RECNO,TY,DRAMT,TRSNO,"
                            + "0 as BALANCE,PAYBY,DETAILS,0 AS CLGREASON,IY,SUBTOKENNO,'' AS CHECKBY,ORG_BRNID,DEST_BRNID FROM recon_trf_d WHERE TRSNO = " + trSNo).getResultList();
                    for (int i = 0; i < getDataList.size(); i++) {
                        Vector getDateVect = (Vector) getDataList.get(i);
                        String acNo = getDateVect.get(0).toString();
                        String instNo = getDateVect.get(3).toString();
                        double crAmt = Double.parseDouble(getDateVect.get(4).toString());
                        auth = getDateVect.get(5).toString();
                        enterBy = getDateVect.get(6).toString();
                        float recNo = Float.parseFloat(getDateVect.get(7).toString());
                        double drAmt = Double.parseDouble(getDateVect.get(9).toString());
                        trsNumber = Integer.parseInt(getDateVect.get(10).toString());
                        String details = getDateVect.get(13).toString();
                        int clgReason = Integer.parseInt(getDateVect.get(14).toString());
                        int iy = Integer.parseInt(getDateVect.get(15).toString());
                        String destBrnid = getDateVect.get(19).toString();
                        String acNature = "";
                        String acctCode = ftsPosting43.getAccountCode(acNo);
                        if (!acctCode.equalsIgnoreCase("A/C No. does not exist")) {
                            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE ='" + acctCode + "'").getResultList();
                            if (acNatureList.isEmpty()) {
                                ut.rollback();
                                return "ACCOUNT TYPE DOES NOT EXIST";
                            } else {
                                Vector acNatureVect = (Vector) acNatureList.get(0);
                                acNature = acNatureVect.get(0).toString();
                            }
                        } else {
                            ut.rollback();
                            return "ACCOUNT CODE DOES NOT EXIST";
                        }
                        int ty = 0;
                        double total;
                        String dt1 = "";
                        if (trsNumber == trSNo) {
                                if ((crAmt != 0)) {
                                    ty = 0;
                                    drAmt = 0d;
                                } else {
                                    ty = 1;
                                    crAmt = 0d;
                                }
                            List authList = em.createNativeQuery("SELECT AUTH FROM recon_trf_d WHERE ACNO = '" + acNo
                                    + "' AND RECNO = '" + recNo + "' AND TRSNO =" + trsNumber + " AND "
                                    + "(AUTH IS NULL OR AUTH = 'N') AND TY = '" + ty + "' AND DT = DATE_FORMAT('"
                                    + dt + "','%Y-%m-%d')").getResultList();
                                Vector authvect = (Vector) authList.get(0);
                                auth = authvect.get(0).toString();
                                if (auth.equalsIgnoreCase("n")) {
                                    auth = "Y";
                                    Integer updateQry = em.createNativeQuery("UPDATE recon_trf_d SET AUTH = '" + auth + "', AUTHBY = '"
                                            + AuthBy + "' WHERE ACNO = '" + acNo + "' AND RECNO = " + recNo + " "
                                            + "AND TRSNO =" + trsNumber + " AND (AUTH IS NULL OR AUTH = 'N') AND TY = '" + ty + "' "
                                            + "AND DT = DATE_FORMAT('" + dt + "','%Y-%m-%d')").executeUpdate();
                                    if (updateQry > 0) {
                                        msgTr = authTransferTrans(trSNo, ty, recNo, orgnBrnCode);
                                        if (!msgTr.equalsIgnoreCase("yes")) {
                                            ut.rollback();
                                            return msgTr;
                                        } else {
                                            msg = "TRUE";
                                        }
                                    }
//Code for handling PO /DD /AD / TPO Code added by Dhirendra Singh
// if (details.indexOf("~`") > -1) {
// String flag = insertIntoBillPO(details, auth, orgnBrnCode);
// if (!flag.equalsIgnoreCase("true")) {
// ut.rollback();
// return flag;
// }
// }
//*************************************************************************/
                                    List acNameList = em.createNativeQuery("Select acname from gltable where acno='" + acNo + "'").getResultList();
                                    if (acNameList.size() > 0) {
                                        Vector vec = (Vector) acNameList.get(0);
                                        int fyear = 0;
                                        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
                                        String time = ymdhms.format(new Date());
                                        String favourOf = "";
                                        String custName = "";
                                        double comm = 0.0;
                                        double crAmount = 0.0;
                                        String payBillAt = "00";
                                        if (vec.get(0).toString().trim().toUpperCase().contains("PAYORDER")) {
                                            float recNo1 = 0.0f;
                                            List selectList1 = em.createNativeQuery("select comm_amt,FAVOUR_OF,CUST_NAME_PO,CR_AMT,ORG_BRN_ID,recno from deadstock_tran where acno='" + acNo + "' and trsno=" + trSNo).getResultList();
                                            if (selectList1.size() > 0) {
                                                Vector ele = (Vector) selectList1.get(0);
                                                if (ele.get(0) != null) {
                                                    comm = Double.parseDouble(ele.get(0).toString());
                                                }
                                                if (ele.get(1) != null) {
                                                    favourOf = ele.get(1).toString();
                                                }
                                                if (ele.get(2) != null) {
                                                    custName = ele.get(2).toString();
                                                }
                                                if (ele.get(3) != null) {
                                                    crAmount = Double.parseDouble(ele.get(3).toString());
                                                }
                                                if (ele.get(4) != null) {
                                                    payBillAt = ele.get(4).toString();
                                                }
                                                if (ele.get(5) != null) {
                                                    recNo1 = Float.parseFloat(ele.get(5).toString());
                                                }
                                            }
                                            List fyeearList = em.createNativeQuery("select f_year from yearend where yearendflag='N' and brncode='" + orgnBrnCode + "'").getResultList();
                                            if (fyeearList.size() > 0) {
                                                Vector vect = (Vector) fyeearList.get(0);
                                                fyear = Integer.parseInt(vect.get(0).toString());
                                            }
                                            int brncd = Integer.parseInt(payBillAt);
                                            List selectList = em.createNativeQuery("select alphacode from branchmaster where brncode=" + brncd).getResultList();
                                            if (selectList.size() > 0) {
                                                Vector vect = (Vector) selectList.get(0);
                                                payBillAt = vect.get(0).toString();
                                            }
                                            Query insertIntoBiilPo = em.createNativeQuery("insert into bill_po(fyear,billtype,acno,custName,payableat,amount,dt,origindt,status,timelimit,comm,trantype,ty,infavourof,enterby,auth,trantime,recno,printflag,validationdt,SEQNO) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                            insertIntoBiilPo.setParameter(1, fyear);
                                            insertIntoBiilPo.setParameter(2, "PO");
                                            insertIntoBiilPo.setParameter(3, acNo);
                                            insertIntoBiilPo.setParameter(4, custName);
                                            insertIntoBiilPo.setParameter(5, payBillAt);
                                            insertIntoBiilPo.setParameter(6, crAmount);
                                            insertIntoBiilPo.setParameter(7, dt);
                                            insertIntoBiilPo.setParameter(8, dt);
                                            insertIntoBiilPo.setParameter(9, "ISSUED");
                                            insertIntoBiilPo.setParameter(10, 180);
                                            insertIntoBiilPo.setParameter(11, comm);
                                            insertIntoBiilPo.setParameter(12, 2);
                                            insertIntoBiilPo.setParameter(13, 0);
                                            insertIntoBiilPo.setParameter(14, favourOf);
                                            insertIntoBiilPo.setParameter(15, AuthBy);
                                            insertIntoBiilPo.setParameter(16, "N");
                                            insertIntoBiilPo.setParameter(17, time);
                                            insertIntoBiilPo.setParameter(18, recNo1);
                                            insertIntoBiilPo.setParameter(19, 0);
                                            insertIntoBiilPo.setParameter(20, dt);
                                            insertIntoBiilPo.setParameter(21, 0.0);
                                            int cnt = insertIntoBiilPo.executeUpdate();
                                            if (cnt <= 0) {
                                                ut.rollback();
                                                msg = "error in isseuing PO";
                                                return msg;
                                            }
                                        }
                                    }
                                    if ((!instNo.equalsIgnoreCase("VOUCHER")) && (drAmt != 0)) {
                                        if (clgReason == 10 || clgReason == 33) {
                                            List intOptList = em.createNativeQuery("SELECT UPPER(INTOPT) FROM td_vouchmst WHERE ACNO='"
                                                    + acNo + "' AND VOUCHERNO = CAST(" + instNo + " as decimal)").getResultList();
                                            String tdsIntOpt = "";
                                            if (intOptList.size() > 0) {
                                                Vector intOptVect = (Vector) intOptList.get(0);
                                                tdsIntOpt = intOptVect.get(0).toString();
                                            }
                                            if (tdsIntOpt.equals("C")) {
                                                em.createNativeQuery("UPDATE td_vouchmst SET CUMUPRINAMT=IFNULL(CUMUPRINAMT,0)-" + drAmt
                                                        + ", TDSDEDUCTED=IFNULL(TDSDEDUCTED,0)+" + drAmt + " WHERE ACNO='" + acNo
                                                        + "' AND VOUCHERNO = CAST(" + instNo + " as decimal)").executeUpdate();
                                            } else {
                                                em.createNativeQuery("UPDATE td_vouchmst SET TDSDEDUCTED=IFNULL(TDSDEDUCTED,0)+" + drAmt
                                                        + " WHERE ACNO='" + acNo + "' AND VOUCHERNO = CAST(" + instNo
                                                        + " as decimal)").executeUpdate();
                                            }
                                            em.createNativeQuery("INSERT INTO tdshistory(ACNO,VOUCHERNO,TDS,DT,FROMDT,TODT,INTOPT) "
                                                    + "VALUES('" + acNo + "',CAST(" + instNo + " as decimal)," + drAmt + ","
                                                    + "DATE_FORMAT('" + dt + "','%Y-%m-%d'),"
                                                    + "DATE_FORMAT('" + dt + "','%Y-%m-%d'),"
                                                    + "DATE_FORMAT('" + dt + "','%Y-%m-%d'),'" + tdsIntOpt
                                                    + "')").executeUpdate();
                                        }
                                    }
                                } else {
                                    ut.rollback();
                                    msg = "BATCH HAS ALREADY BEEN AUTHORIZED.";
                                    return msg;
                                }
                            if (iy == 11 || iy == 12 || iy == 13) {
                                float seqNo = 0f;
                                if (clgReason != 9) {
                                    if (iy == 11) {
                                        List seqNoList = em.createNativeQuery("SELECT IFNULL(MAX(SEQNO),0) FROM bill_sundry WHERE FYEAR = YEAR(DATE_FORMAT(DATE_FORMAT('" + dt + "','%Y-%m-%d'),'%Y-%m-%d'))").getResultList();
                                        Vector seqNoVect = (Vector) seqNoList.get(0);
                                        seqNo = Float.parseFloat(seqNoVect.get(0).toString());
                                    }
                                    if (iy == 12) {
                                        List seqNoList = em.createNativeQuery("SELECT IFNULL(MAX(SEQNO),0) FROM bill_suspense WHERE FYEAR = YEAR(DATE_FORMAT(DATE_FORMAT('" + dt + "','%Y-%m-%d'),'%Y-%m-%d'))").getResultList();
                                        Vector seqNoVect = (Vector) seqNoList.get(0);
                                        seqNo = Float.parseFloat(seqNoVect.get(0).toString());
                                    }
                                    if (iy == 13) {
                                        List seqNoList = em.createNativeQuery("SELECT IFNULL(MAX(SEQNO),0) FROM bill_clgadjustment WHERE ACNO='" + acNo + "'AND FYEAR = YEAR(DATE_FORMAT(DATE_FORMAT('" + dt + "','%Y-%m-%d'),'%Y-%m-%d'))").getResultList();
                                        Vector seqNoVect = (Vector) seqNoList.get(0);
                                        seqNo = Float.parseFloat(seqNoVect.get(0).toString());
                                    }
                                    if (seqNo == 0f) {
                                        seqNo = 1f;
                                    } else {
                                        seqNo = seqNo + 1;
                                    }
                                    double stmAmt = 0d;
                                    if (crAmt != 0) {
                                        stmAmt = crAmt;
                                    }
                                    if (drAmt != 0) {
                                        stmAmt = drAmt;
                                    }
                                    if (stmAmt > 0) {
                                        if (iy == 11) {
                                            em.createNativeQuery("INSERT bill_sundry (FYEAR,ACNO,AMOUNT,ENTERBY,DT,STATUS,AUTH,TRANTYPE,"
                                                    + "SEQNO,TY,RECNO,ORIGINDT,AUTHBY,DETAILS) VALUES( YEAR(DATE_FORMAT(DATE_FORMAT('"
                                                    + dt + "','%Y-%m-%d'),'%Y-%m-%d')),'" + acNo + "','" + stmAmt + "','" + AuthBy
                                                    + "',DATE_FORMAT(DATE_FORMAT('" + dt + "','%Y-%m-%d'),'%Y-%m-%d'), 'ISSUED','Y',2," + seqNo + ","
                                                    + ty + "," + recNo + ",DATE_FORMAT(DATE_FORMAT('" + dt + "','%Y-%m-%d'),'%Y-%m-%d'),'" + AuthBy
                                                    + "','" + details + "')").executeUpdate();
                                        }
                                        if (iy == 12) {
                                            em.createNativeQuery("INSERT bill_suspense (FYEAR,ACNO,AMOUNT,ENTERBY,DT,STATUS,AUTH,TRANTYPE,SEQNO,TY,"
                                                    + "RECNO,ORIGINDT,AUTHBY,DETAILS) VALUES( YEAR(DATE_FORMAT(DATE_FORMAT('" + dt
                                                    + "','%Y-%m-%d'),'%Y-%m-%d')),'" + acNo + "','" + stmAmt + "','" + AuthBy + "',"
                                                    + "DATE_FORMAT(DATE_FORMAT('" + dt + "','%Y-%m-%d'),'%Y-%m-%d'), 'ISSUED','Y',2," + seqNo + "," + ty
                                                    + "," + recNo + ",DATE_FORMAT(DATE_FORMAT('" + dt + "','%Y-%m-%d'),'%Y-%m-%d'),'" + AuthBy
                                                    + "','" + details + "')").executeUpdate();
                                        }
                                        if (iy == 13) {
                                            em.createNativeQuery("INSERT bill_clgadjustment (FYEAR,ACNO,AMOUNT,ENTERBY,DT,STATUS,AUTH,TRANTYPE,SEQNO,"
                                                    + "TY,RECNO,ORIGINDT,AUTHBY,DETAILS) VALUES( YEAR(DATE_FORMAT(DATE_FORMAT('" + dt
                                                    + "','%Y-%m-%d'),'%Y-%m-%d')),'" + acNo + "','" + stmAmt + "','" + AuthBy + "',DATE_FORMAT(DATE_FORMAT('" + dt
                                                    + "','%Y-%m-%d'),'%Y-%m-%d'), 'ISSUED','Y',2," + seqNo + "," + ty + "," + recNo + ",DATE_FORMAT(DATE_FORMAT('" + dt
                                                    + "','%Y-%m-%d'),'%Y-%m-%d'),'" + AuthBy + "','" + details + "')").executeUpdate();
                                        }
                                        msg = "SEQUENCE NO. IS " + seqNo + " FOR " + acNo + " ACCOUNT";
                                    }
                                }
                            }

                        }
                    }
                }
            }
            return msg;
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

    private String cbsDestEntryIdentification(Float trSNo) throws ApplicationException {
        try {
            String msg = null;
            List getDataList = em.createNativeQuery("SELECT ACNO,ORG_BRNID,DEST_BRNID FROM recon_trf_d WHERE TRSNO = CAST(" + trSNo + " AS unsigned)").getResultList();
            if (getDataList.size() > 0) {
                for (int i = 0; i < getDataList.size(); i++) {
                    Vector getDateVect = (Vector) getDataList.get(i);
                    String orgnBrnId = getDateVect.get(1).toString();
                    String destBrnId = getDateVect.get(2).toString();
                    if (!orgnBrnId.equalsIgnoreCase(destBrnId)) {
                        msg = "TRUE";
                        return msg;
                    } else {
                        msg = "FALSE";
                    }
                }
            } else {
                return "Please check the Bach No, You have passed.";
            }
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private String authTransferTrans(int trsNo, Integer ty, Float recno, String orgnBrCode) throws ApplicationException {
        String successFlag = "";
        String acNature = "";
        double balance = 0f;
        List balancList;
        List balanceList;
        try {
            List reconTrfList = em.createNativeQuery("SELECT ifnull(R.ACNO,''),R.CRAMT,R.DRAMT,R.RECNO,R.AUTHBY,R.DT,R.TRANDESC,R.DETAILS,R.IY,"
                    + "R.ENTERBY, R.VALUEDT FROM recon_trf_d R WHERE AUTH='Y' AND TRSNO=CAST(" + trsNo + " AS unsigned) AND TY = " + ty + " AND RECNO = " + recno + " and org_brnid = '" + orgnBrCode + "'").getResultList();
            successFlag = "YES";
            if (reconTrfList.isEmpty()) {
                successFlag = "FALSE";
            } else if (reconTrfList.size() > 0) {
                //for (int i = 0; i < reconTrfList.size(); i++) {
                Vector reconTrfVect = (Vector) reconTrfList.get(0);
                String acno = reconTrfVect.get(0).toString();
                double cramt = Double.parseDouble(reconTrfVect.get(1).toString());
                double dramt = Double.parseDouble(reconTrfVect.get(2).toString());
                recno = Float.parseFloat(reconTrfVect.get(3).toString());
                String authBy = reconTrfVect.get(4).toString();
                // String dt = reconTrfVect.get(5).toString();
                int tranDesc = Integer.parseInt(reconTrfVect.get(6).toString());
                // String remarks = reconTrfVect.get(7).toString();
                int iy = Integer.parseInt(reconTrfVect.get(8).toString());
                String enterBy = reconTrfVect.get(9).toString();
                String valueDt = reconTrfVect.get(10).toString();
                String acctCode = ftsPosting43.getAccountCode(acno);
                if (!acctCode.equalsIgnoreCase("A/C No. does not exist")) {
                    List acNatureList = em.createNativeQuery("SELECT ifnull(ACCTNATURE,'') FROM accounttypemaster WHERE ACCTCODE='" + acctCode + "'").getResultList();
                    if (!acNatureList.isEmpty()) {
                        Vector acNatureVect = (Vector) acNatureList.get(0);
                        acNature = acNatureVect.get(0).toString();
                    }
                }
                if (acno.equalsIgnoreCase("") || acNature.equalsIgnoreCase("")) {
                    successFlag = "PLEASE CHECK FOR RECNO,TOKENNO,SUBTOKENNO";
                    return "PLEASE CHECK FOR RECNO,TOKENNO,SUBTOKENNO";
                }
                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || acNature.equalsIgnoreCase(CbsConstant.OF_AC)
                        || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)
                        || acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acNature.equalsIgnoreCase(CbsConstant.SS_AC) || acNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                    balanceList = em.createNativeQuery("SELECT BALANCE FROM reconbalan WHERE ACNO='" + acno + "'").getResultList();
                    if (!balanceList.isEmpty()) {
                        Vector balanceVect = (Vector) balanceList.get(0);
                        balance = Double.parseDouble(balanceVect.get(0).toString());
                    }
                    if (ty == 0) {
                        List acnoList = em.createNativeQuery("SELECT ACNO FROM reconbalan WHERE ACNO='" + acno + "'").getResultList();
                        if (!acnoList.isEmpty()) {
                            List cbalanceList = em.createNativeQuery("SELECT CLEAREDBALANCE FROM reconbalan WHERE ACNO='" + acno + "'").getResultList();
                            if (!cbalanceList.isEmpty()) {
                                Vector balanceVect = (Vector) cbalanceList.get(0);
                                balance = Double.parseDouble(balanceVect.get(0).toString());
                            }
                            Integer reconBalan = em.createNativeQuery("UPDATE reconbalan SET CLEAREDBALANCE = IFNULL(CLEAREDBALANCE,0) + "
                                    + "IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0),BALANCE =IFNULL(BALANCE,0) + IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0)"
                                    + " WHERE ACNO='" + acno + "'").executeUpdate();
                            if (reconBalan < 0) {
                                //// ut.rollback();
                                return "Data is not updated into RECONBALAN";
                            }
                        } else {
                            Integer reconBalan = em.createNativeQuery("INSERT INTO reconbalan(ACNO,dt,BALANCE,CLEAREDBALANCE)VALUES"
                                    + "('" + acno + "',CURRENT_TIMESTAMP,IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0),IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0))").executeUpdate();
                            if (reconBalan < 0) {
                                //// ut.rollback();
                                return "Data is not inserted into RECONBALAN";
                            }
                            balancList = em.createNativeQuery("SELECT IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0)").getResultList();
                            if (!balancList.isEmpty()) {
                                Vector balanceVect = (Vector) balancList.get(0);
                                balance = Double.parseDouble(balanceVect.get(0).toString());
                            }
                        }
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    balanceList = em.createNativeQuery("SELECT BALANCE FROM ca_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                    if (!balanceList.isEmpty()) {
                        Vector balanceVect = (Vector) balanceList.get(0);
                        balance = Double.parseDouble(balanceVect.get(0).toString());
                    }
                    if (ty == 0) {
                        List acnoList = em.createNativeQuery("SELECT ACNO FROM ca_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                        if (!acnoList.isEmpty()) {
                            List cbalanceList = em.createNativeQuery("SELECT CLEAREDBALANCE FROM ca_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                            if (!cbalanceList.isEmpty()) {
                                Vector balanceVect = (Vector) cbalanceList.get(0);
                                balance = Double.parseDouble(balanceVect.get(0).toString());
                            }
                            Integer reconBalan = em.createNativeQuery("UPDATE ca_reconbalan SET CLEAREDBALANCE = IFNULL(CLEAREDBALANCE,0) + "
                                    + "IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0),BALANCE =IFNULL(BALANCE,0) + IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0)"
                                    + " WHERE ACNO='" + acno + "'").executeUpdate();
                            if (reconBalan < 0) {
                                ut.rollback();
                                return "Data is not updated into CA_RECONBALAN";
                            }
                        } else {
                            Integer reconBalan = em.createNativeQuery("INSERT INTO ca_reconbalan(ACNO,dt,BALANCE,CLEAREDBALANCE)VALUES"
                                    + "('" + acno + "',CURRENT_TIMESTAMP,IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0),IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0))").executeUpdate();
                            if (reconBalan < 0) {
                                ut.rollback();
                                return "Data is not inserted into CA_RECONBALAN";
                            }
                            balancList = em.createNativeQuery("SELECT IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0)").getResultList();
                            if (!balancList.isEmpty()) {
                                Vector balanceVect = (Vector) balancList.get(0);
                                balance = Double.parseDouble(balanceVect.get(0).toString());
                            }
                        }
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    balanceList = em.createNativeQuery("SELECT BALANCE FROM td_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                    if (!balanceList.isEmpty()) {
                        Vector balanceVect = (Vector) balanceList.get(0);
                        balance = Double.parseDouble(balanceVect.get(0).toString());
                    }
                    if (ty == 0) {
                        List acnoList = em.createNativeQuery("SELECT ACNO FROM td_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                        if (!acnoList.isEmpty()) {
                            List cbalanceList = em.createNativeQuery("SELECT CLEAREDBALANCE FROM td_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                            if (!cbalanceList.isEmpty()) {
                                Vector balanceVect = (Vector) cbalanceList.get(0);
                                balance = Double.parseDouble(balanceVect.get(0).toString());
                            }
                            Integer reconBalan = em.createNativeQuery("UPDATE td_reconbalan SET CLEAREDBALANCE = IFNULL(CLEAREDBALANCE,0) + "
                                    + "IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0),BALANCE =IFNULL(BALANCE,0) + IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0)"
                                    + " WHERE ACNO='" + acno + "'").executeUpdate();
                            if (reconBalan < 0) {
                                ut.rollback();
                                return "Data is not updated into TD_RECONBALAN";
                            }
                        } else {
                            Integer reconBalan = em.createNativeQuery("INSERT INTO td_reconbalan(ACNO,dt,BALANCE,CLEAREDBALANCE)VALUES"
                                    + "('" + acno + "',CURRENT_TIMESTAMP,IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0),IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0))").executeUpdate();
                            if (reconBalan < 0) {
                                ut.rollback();
                                return "Data is not inserted into TD_RECONBALAN";
                            }
                            balancList = em.createNativeQuery("SELECT IFNULL(" + cramt + ",0) - IFNULL(" + dramt + ",0)").getResultList();
                            if (!balancList.isEmpty()) {
                                Vector balanceVect = (Vector) balancList.get(0);
                                balance = Double.parseDouble(balanceVect.get(0).toString());
                            }
                        }
                    }
                }
                if (ty == 0) {
                    balancList = em.createNativeQuery("SELECT IFNULL(" + balance + ",0) +IFNULL(" + cramt + ",0) -IFNULL(" + dramt + ",0)").getResultList();
                    if (!balancList.isEmpty()) {
                        Vector balanceVect = (Vector) balancList.get(0);
                        balance = Double.parseDouble(balanceVect.get(0).toString());
                    }
                }
                //}
                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    Integer sbRecon = em.createNativeQuery("INSERT INTO recon(ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE, DETAILS, IY, "
                            + "INSTNO, ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, SUBTOKENNO,ORG_BRNID,"
                            + "DEST_BRNID,instdt,valuedt) SELECT R.ACNO , R.TY, R.DT, R.DRAMT, R.CRAMT," + balance + ",R.TRANTYPE , R.DETAILS, R.IY, R.INSTNO, R.ENTERBY, "
                            + "R.AUTH, R.RECNO,R.PAYBY,R.AUTHBY, R.TRSNO, R.TRANTIME, R.TRANDESC, R.TOKENNO,R.TOKENPAIDBY, R.SUBTOKENNO,"
                            + "R.ORG_BRNID,R.DEST_BRNID,R.instdt,R.valuedt FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND AUTH = 'Y' AND TY = " + ty + "").executeUpdate();
                    if (sbRecon <= 0) {
                        return "Data is not inserted into RECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    Integer ddsTran = em.createNativeQuery("INSERT INTO ddstransaction ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE,"
                            + " DETAILS, IY, RECEIPTNO, ENTERBY, AUTH, RECNO,PAYBY, AUTHBY, TRSNO, TRANTIME, TOKENNO,TOKENPAIDBY,"
                            + " SUBTOKENNO,tran_id,term_id,org_brnid,dest_brnid,trandesc,tokenpaid,favorof,INSTNO,CheckBy,instdt,valuedt) SELECT "
                            + "R.ACNO , R.TY, R.DT, R.DRAMT, R.CRAMT, " + balance + ",R.TRANTYPE , R.DETAILS, R.IY, R.INSTNO, R.ENTERBY, "
                            + "R.AUTH, R.RECNO,R.PAYBY, R.AUTHBY, R.TRSNO, R.TRANTIME, R.TOKENNO,R.TOKENPAIDBY, R.SUBTOKENNO,"
                            + "R.tran_id,R.TERM_ID,R.ORG_BRNID,R.dest_brnid,R.trandesc,'','',R.INSTNO,'',R.instdt,R.valuedt FROM recon_trf_d R WHERE "
                            + "R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND AUTH = 'Y' AND TY = " + ty + "").executeUpdate();
                    if (ddsTran <= 0) {
                        return "Data is not inserted into DDSTRANSACTION";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                    Integer ofRecon = em.createNativeQuery("INSERT INTO of_recon ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE, "
                            + "DETAILS, ENTERBY, AUTH, RECNO,AUTHBY, TRSNO,TRANTIME, TRANDESC, TOKENNO, SUBTOKENNO,ORG_BRNID,"
                            + "DEST_BRNID, valuedt) SELECT R.ACNO , R.TY, R.DT, R.DRAMT, R.CRAMT, " + balance + ",R.TRANTYPE , R.DETAILS,R.ENTERBY, "
                            + "R.AUTH, R.RECNO,R.AUTHBY, R.TRSNO,R.TRANTIME, R.TRANDESC, R.TOKENNO, R.SUBTOKENNO, R.ORG_BRNID, "
                            + "R.DEST_BRNID,R.instdt,R.valuedt FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND AUTH = 'Y' AND TY =" + ty + "").executeUpdate();
                    if (ofRecon <= 0) {
                        return "Data is not inserted into OF_RECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.OF_AC)
                        || acNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                    Integer loanRecon = em.createNativeQuery("INSERT INTO loan_recon ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE,"
                            + " DETAILS, IY, INSTNO, ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO, SUBTOKENNO,"
                            + "ORG_BRNID,DEST_BRNID,instdt,valuedt) SELECT R.ACNO , R.TY, R.DT, R.DRAMT, R.CRAMT, " + balance + ",R.TRANTYPE , R.DETAILS, "
                            + "R.IY, R.INSTNO, R.ENTERBY, R.AUTH, R.RECNO, R.PAYBY,R.AUTHBY, R.TRSNO, R.TRANTIME, R.TRANDESC,"
                            + " R.TOKENNO, R.SUBTOKENNO, R.ORG_BRNID,R.DEST_BRNID,R.instdt,R.valuedt FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND "
                            + "AUTH = 'Y' AND TY = " + ty + "").executeUpdate();
                    if (loanRecon <= 0) {
                        return "Data is not inserted into LOAN_RECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    Integer glRecon = em.createNativeQuery("INSERT INTO gl_recon ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE, DETAILS, IY, "
                            + "INSTNO, ENTERBY, AUTH, RECNO, PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, SUBTOKENNO,ORG_BRNID,"
                            + "DEST_BRNID,instdt,valuedt)SELECT R.ACNO , R.TY, R.DT, R.DRAMT, R.CRAMT," + balance + ",R.TRANTYPE , R.DETAILS, R.IY, R.INSTNO, R.ENTERBY,"
                            + " R.AUTH, R.RECNO, R.PAYBY,R.AUTHBY, R.TRSNO, R.TRANTIME, R.TRANDESC, R.TOKENNO,R.TOKENPAIDBY, R.SUBTOKENNO , "
                            + "R.ORG_BRNID, R.DEST_BRNID,R.instdt,R.valuedt FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND AUTH = 'Y' AND TY = " + ty + "").executeUpdate();
                    if (glRecon <= 0) {
                        return "Data is not inserted into GL_RECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    Integer rdRecon = em.createNativeQuery("INSERT INTO rdrecon ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE, DETAILS, IY, "
                            + "INSTNO, ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, SUBTOKENNO,ORG_BRNID,"
                            + "DEST_BRNID,instdt,valuedt)SELECT R.ACNO , R.TY, R.DT, R.DRAMT, R.CRAMT," + balance + ",R.TRANTYPE , R.DETAILS, R.IY, R.INSTNO, R.ENTERBY,"
                            + " R.AUTH, R.RECNO, R.PAYBY,R.AUTHBY, R.TRSNO, R.TRANTIME, R.TRANDESC, R.TOKENNO,R.TOKENPAIDBY, R.SUBTOKENNO ,R.ORG_BRNID,"
                            + " R.DEST_BRNID,R.instdt,R.valuedt FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND AUTH = 'Y' AND TY = " + ty + "").executeUpdate();
                    if (rdRecon <= 0) {
                        return "Data is not inserted into RDRECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    Integer caRecon = em.createNativeQuery("INSERT INTO ca_recon ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE, TRANTYPE, DETAILS, IY,"
                            + " INSTNO, ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, SUBTOKENNO, ORG_BRNID,"
                            + " DEST_BRNID,instdt,valuedt) SELECT R.ACNO , R.TY, R.DT, R.DRAMT, R.CRAMT, " + balance + ",R.TRANTYPE , R.DETAILS, R.IY, R.INSTNO,"
                            + " R.ENTERBY, R.AUTH, R.RECNO, R.PAYBY,R.AUTHBY, R.TRSNO, R.TRANTIME, R.TRANDESC, R.TOKENNO,R.TOKENPAIDBY,"
                            + " R.SUBTOKENNO ,R.ORG_BRNID, R.DEST_BRNID, R.instdt,R.valuedt FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND AUTH = 'Y'"
                            + " AND TY = " + ty + "").executeUpdate();
                    if (caRecon <= 0) {
                        return "Data is not inserted into CA_RECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    Integer tdRecon = em.createNativeQuery("INSERT INTO td_recon ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE, DETAILS, ENTERBY,"
                            + " AUTH, RECNO,AUTHBY,TRSNO, TRANTIME, TRANDESC, TOKENNO, SUBTOKENNO,intflag,ORG_BRNID,DEST_BRNID,valuedt) SELECT R.ACNO , R.TY,"
                            + " R.DT, R.DRAMT, R.CRAMT," + balance + ",R.TRANTYPE , R.DETAILS, R.ENTERBY, R.AUTH, R.RECNO,R.AUTHBY,R.TRSNO, R.TRANTIME,"
                            + " R.TRANDESC, R.TOKENNO, R.SUBTOKENNO,R.intflag ,R.ORG_BRNID,R.DEST_BRNID, R.valuedt FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND "
                            + "AUTH = 'Y' AND TY = " + ty + "").executeUpdate();
                    if (tdRecon <= 0) {
                        return "Data is not inserted into TD_RECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                    Integer npaRecon = em.createNativeQuery("INSERT INTO npa_recon(ACNO,TY,DT,DRAMT,CRAMT,TRANTYPE,ENTERBY,AUTH,RECNO,PAYBY,AUTHBY,"
                            + "TRANDESC,IY,INTAMT,STATUS,ORG_BRNID,DEST_BRNID,VALUEDT) VALUES('" + acno + "',1,now(),0," + cramt + ",0,'" + enterBy
                            + "','Y'," + recno + ",3,'" + authBy + "','" + tranDesc + "'," + iy + ",0,'','" + orgnBrCode + "','" + orgnBrCode
                            + "','" + valueDt + "')").executeUpdate();
                    if (npaRecon <= 0) {
                        return "Data is not inserted into NPA_RECON";
                    }
                }
            }
            return successFlag;
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
//        catch (Exception e) {
//           return "false";
//        }
    }

    private String fnAcNature(String acctCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + acctCode + "'").getResultList();
            Vector element = (Vector) list.get(0);
            String accNature = element.get(0).toString();
            return accNature;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
