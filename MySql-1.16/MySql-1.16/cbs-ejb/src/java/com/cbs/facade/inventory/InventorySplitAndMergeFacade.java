package com.cbs.facade.inventory;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.InventoryMasterGridFile;
import com.cbs.dto.InventoryMovementGridDetail;
import com.cbs.dto.PoSearchPojo;
import com.cbs.dto.neftrtgs.AirPayResponse;
import com.cbs.exception.ApplicationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import javax.ejb.EJB;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Zeeshan waris
 */
@Stateless(mappedName = "InventorySplitAndMergeFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class InventorySplitAndMergeFacade implements InventorySplitAndMergeFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPostRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmyOne = new SimpleDateFormat("dd/MM/yyyy");

    public List gridLoadForSplitDetail(String brnCode, String invtClass, String invtType, String invtSrNo) throws ApplicationException {
        List fromNo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT ChqSrNo,ChNoFrom,ChNoTo,Status,DATE_FORMAT(Stockdt,'%d/%m/%Y') AS Stockdt,INVT_CLASS,INVT_TYPE,INVT_QUANTITY,TxnId FROM chbookmaster_stock WHERE brncode='" + brnCode + "' AND INVT_CLASS='" + invtClass + "' "
                    + " AND INVT_TYPE='" + invtType + "' AND ChqSrNo='" + invtSrNo + "' AND Status='F' AND ENT_FLAG IN ('ENT','MERGE')");
            fromNo = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return fromNo;
    }

    public String splitInventory(String brCode, String alpha, String invtClass, String invtType, String fromNo, String endNo, String itemsPerUnit, String quantity, String txnId, String enterDt, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0, var1 = 0;
            String tempFromNo = "";
            String tempToNo = "";
            if (Integer.parseInt(quantity) % Integer.parseInt(itemsPerUnit) != 0) {
                throw new ApplicationException("Please enter appropriate items per unit !!!");
            }
            int noOfBooks = Integer.parseInt(quantity) / Integer.parseInt(itemsPerUnit);
            tempFromNo = fromNo;
            tempToNo = endNo;
            for (int i = 1; i <= noOfBooks; i++) {
                if (noOfBooks == 1) {
                    if (invtClass.equalsIgnoreCase("PO")) {
                        Query updateStockQ = em.createNativeQuery("UPDATE chbookmaster_stock SET ENT_FLAG='SPLIT', MOD_BY='" + enterBy + "',MOD_DATE = now(),Status='U' "
                                + "WHERE brncode='" + brCode + "' AND INVT_CLASS='" + invtClass + "' AND INVT_TYPE='" + invtType + "' AND Status='F' AND ChNoFrom='"
                                + tempFromNo + "' AND ChNoTo='" + tempToNo + "' AND ENT_FLAG IN('ENT','MERGE')");
                        var = updateStockQ.executeUpdate();
                        if (var <= 0) {
                            throw new ApplicationException("Split Failed !!!");
                        }
                        var = em.createNativeQuery("insert into chbookmaster_amtwise(InstCode,SlabCode,Amtfrom,AmtTo,NumFrom,NumTo, Leaves,PrintLot,"
                                + "Dt,Enterby,status,issuedt,auth,ISSUEBY,brncode) values ('" + invtClass + "',5,0,1000000000," + tempFromNo + "," + tempToNo + ","
                                + itemsPerUnit + ",'" + alpha + "','" + enterDt + "','" + enterBy + "','I','" + enterDt + "','N',' ','" + brCode + "')").executeUpdate();
                        if (var <= 0) {
                            throw new ApplicationException("Problem in data insertion");
                        }
                    } else {
                        Query updateStockQ = em.createNativeQuery("UPDATE chbookmaster_stock SET ENT_FLAG='SPLIT', MOD_BY='" + enterBy + "',MOD_DATE = now() WHERE brncode='" + brCode + "' AND INVT_CLASS='" + invtClass + "' "
                                + " AND INVT_TYPE='" + invtType + "' AND Status='F' AND ChNoFrom='" + tempFromNo + "' AND ChNoTo='" + tempToNo + "' AND ENT_FLAG IN('ENT','MERGE')");
                        var = updateStockQ.executeUpdate();
                        if (var <= 0) {
                            throw new ApplicationException("Split Failed !!!");
                        }
                    }
                } else {
                    tempToNo = String.valueOf((Integer.parseInt(tempFromNo) + Integer.parseInt(itemsPerUnit)) - 1);
                    List chkLt = em.createNativeQuery("SELECT ChqSrNo,ChNoFrom,ChNoTo,Status FROM chbookmaster_stock WHERE ChqSrNo='" + alpha + "' AND ChNoFrom='" + tempFromNo + "' AND ChNoTo='" + tempToNo + "'").getResultList();
                    if (!chkLt.isEmpty()) {
                        Vector ele = (Vector) chkLt.get(0);
                        if (ele.get(3).toString().equalsIgnoreCase("U")) {
                            throw new ApplicationException("Cheque series " + tempFromNo + " to" + tempToNo + " is already used !!!");
                        } else {
                            throw new ApplicationException("Duplicate entries are not allowed in stock record.Cheque series " + alpha + " ,cheque number from " + tempFromNo + " and cheque number to " + tempToNo + " entry already exists !!!");
                        }
                    }
                    if (invtClass.equalsIgnoreCase("PO")) {
                        var = em.createNativeQuery("insert into chbookmaster_amtwise(InstCode,SlabCode,Amtfrom,AmtTo,NumFrom,NumTo, Leaves,PrintLot,"
                                + "Dt,Enterby,status,issuedt,auth,ISSUEBY,brncode) values ('" + invtClass + "',5,0,1000000000," + tempFromNo + "," + tempToNo + ","
                                + itemsPerUnit + ",'" + alpha + "','" + enterDt + "','" + enterBy + "','I','" + enterDt + "','N',' ','" + brCode + "')").executeUpdate();
                        if (var <= 0) {
                            throw new ApplicationException("Problem in data insertion");
                        }
                        tempFromNo = String.valueOf(Integer.parseInt(tempFromNo) + Integer.parseInt(itemsPerUnit));
                    } else {
                        Query insertStockQ = em.createNativeQuery("Insert into chbookmaster_stock (chqsrno,chnofrom,chnoto,status,issuedt,stockdt,"
                                + "enterby,brncode,invt_class,invt_type,invt_quantity,mod_by,mod_date,ent_flag) values('" + alpha + "','" + tempFromNo
                                + "','" + tempToNo + "','F',null,'" + enterDt + "','" + enterBy + "','" + brCode + "','" + invtClass + "','" + invtType
                                + "'," + itemsPerUnit + ",'" + enterBy + "',now(),'SPLIT')");
                        var = insertStockQ.executeUpdate();
                        tempFromNo = String.valueOf(Integer.parseInt(tempFromNo) + Integer.parseInt(itemsPerUnit));
                        if (var <= 0) {
                            throw new ApplicationException("Insertion failed in stock record !!!");
                        }
                    }
                }
            }
            if (noOfBooks != 1) {
                List chkLt = em.createNativeQuery("SELECT ChqSrNo,ChNoFrom,ChNoTo,Status FROM chbookmaster_stock WHERE ChqSrNo='" + alpha + "' AND ChNoFrom='" + fromNo + "' AND ChNoTo='" + endNo + "'").getResultList();
                if (!chkLt.isEmpty()) {
                    Vector ele = (Vector) chkLt.get(0);
                    if (ele.get(3).toString().equalsIgnoreCase("U")) {
                        throw new ApplicationException("Cheque series " + fromNo + " to" + endNo + " is already used !!!");
                    }
                }
                Query deleteQuery = em.createNativeQuery("DELETE FROM chbookmaster_stock WHERE brncode='" + brCode + "' AND INVT_CLASS='" + invtClass + "' "
                        + " AND INVT_TYPE='" + invtType + "' AND Status='F' AND ChNoFrom='" + fromNo + "' AND ChNoTo='" + endNo + "' AND ENT_FLAG IN('ENT','MERGE')");
                var1 = deleteQuery.executeUpdate();
                if (var1 <= 0) {
                    throw new ApplicationException("System error occured in deletion of old record from stock !!!");
                }
            }
            ut.commit();
            return "Record splitted succesfully.";

        } catch (NotSupportedException | SystemException | NumberFormatException | ApplicationException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List gridLoadForMergeDetail(String brnCode, String invtClass, String invtType, String invtSrNo) throws ApplicationException {
        List fromNo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT ChqSrNo,ChNoFrom,ChNoTo,Status,DATE_FORMAT(Stockdt,'%d/%m/%Y') AS Stockdt,INVT_CLASS,INVT_TYPE,INVT_QUANTITY,TxnId FROM chbookmaster_stock WHERE brncode='" + brnCode + "' AND INVT_CLASS='" + invtClass + "' "
                    + " AND INVT_TYPE='" + invtType + "' AND ChqSrNo='" + invtSrNo + "' AND Status='F' AND ENT_FLAG='SPLIT'");
            fromNo = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return fromNo;
    }

    public List chequeSeriesList(String brnCode, String invClass, String invType, String entFlag) throws ApplicationException {
        List chequeList = new ArrayList();
        try {
            String query = "";
            if (entFlag.equalsIgnoreCase("SPLIT")) {
                query = "select distinct chqsrno from chbookmaster_stock where brncode ='" + brnCode + "' and ENT_FLAG ='" + entFlag + "'and INVT_CLASS = '" + invClass + "' and INVT_TYPE = '" + invType + "' and status='F'";
            } else {
                query = "select distinct chqsrno from chbookmaster_stock where brncode ='" + brnCode + "' and ENT_FLAG in ('" + entFlag + "','MERGE') and INVT_CLASS = '" + invClass + "' and INVT_TYPE = '" + invType + "' and status='F'";
            }
            Query selectQuery = em.createNativeQuery(query);
            chequeList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return chequeList;
    }

//    public String alphaCodeList() throws ApplicationException {
//        String chequeList = "";
//        try {
//            List selectQuery = em.createNativeQuery("SELECT ifnull(MAX(CAST(INVNT_SR_ALPHA AS unsigned)),0)+1 FROM inventory_transfer").getResultList();
//            if (!selectQuery.isEmpty()) {
//                Vector ele1 = (Vector) selectQuery.get(0);
//                chequeList = ele1.get(0).toString();
//            }
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//        return chequeList;
//    }
    public String alphaCodeList(int brncode, String acType, int endNo) throws ApplicationException {
        String chequeList = "";
        try {
//            List list = em.createNativeQuery("select ifnull(ref_code,'') from cbs_ref_rec_type c, inventory_class_type_mapping i "
//                    + " where ref_rec_no = '017' and c.ref_desc = i.invt_type_desc and i.invt_type = '" + acType + "'").getResultList();
            List list = em.createNativeQuery("select ifnull(ref_desc,'') from cbs_ref_rec_type c, inventory_class_type_mapping i "
                    + "where ref_rec_no = '017' and c.ref_code = i.invt_type_desc and i.invt_type = '" + acType + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define transaction code in CBS REF REC TYPE for 015");
            } else {
                Vector ele2 = (Vector) list.get(0);
                String acCode = ele2.get(0).toString();
                int lenVal = (brncode + acCode).length();

                int fullLen = ftsPostRemote.getCodeForReportName("CHQ_SR_LEN");
                if (fullLen == 0) {
                    fullLen = 10;
                }

                List retQuery = em.createNativeQuery("select cast(ifnull(max(invnt_sr_no),0) as unsigned) from inventory_transfer where invnt_type = '" + acType + "' "
                        + " and cast(dest_brncode as unsigned) = '" + brncode + "' and INVNT_SR_ALPHA = (select max(INVNT_SR_ALPHA) from inventory_transfer "
                        + "where invnt_type = '" + acType + "' and cast(dest_brncode as unsigned) = '" + brncode + "')").getResultList();
                if (!retQuery.isEmpty()) {
                    Vector ele3 = (Vector) retQuery.get(0);
                    int mNo = Integer.parseInt(ele3.get(0).toString());

                    List dtlQuery = em.createNativeQuery("select ifnull(invnt_qty,0),ifnull(invnt_sr_alpha,0) from inventory_transfer "
                            + " where invnt_type = '" + acType + "' and invnt_sr_no = " + mNo + " and cast(dest_brncode as unsigned) = '" + brncode + "'").getResultList();
                    if (!dtlQuery.isEmpty()) {
                        Vector ele4 = (Vector) dtlQuery.get(0);
                        int qty = Integer.parseInt(ele4.get(0).toString());
                        int maxNo = (mNo + qty) - 1;
                        String series = ele4.get(1).toString();

                        if ((maxNo < 999999) && ((maxNo + qty) < 999999) && !series.equalsIgnoreCase("0")) {
                            chequeList = series;
                        } else {
                            int balLen = fullLen - lenVal;
                            List selectQuery = em.createNativeQuery("SELECT ifnull(MAX(CAST(substring(INVNT_SR_ALPHA," + (lenVal + 1) + "," + balLen + ") AS unsigned)),0)+1 FROM inventory_transfer "
                                    + " where invnt_type in(select invt_type from inventory_class_type_mapping where invt_type_desc = (select invt_type_desc "
                                    + " from inventory_class_type_mapping where invt_type ='" + acType + "')) and cast(dest_brncode as unsigned) = " + brncode + " "
                                    + " and length(INVNT_SR_ALPHA)= " + fullLen + "").getResultList();
                            if (!selectQuery.isEmpty()) {
                                Vector ele1 = (Vector) selectQuery.get(0);
                                if (ele1.get(0).toString().length() > balLen) {
                                    throw new Exception("Series Initialization Is Required For " + acCode + " Of The Branch " + brncode);
                                } else {
                                    String codeVal = String.format("%0" + balLen + "d", Integer.parseInt(ele1.get(0).toString()));
                                    chequeList = brncode + acCode + codeVal;
                                }
                            }
                        }
                    } else {
                        int balLen = fullLen - lenVal;
                        List selectQuery = em.createNativeQuery("SELECT ifnull(MAX(CAST(substring(INVNT_SR_ALPHA," + (lenVal + 1) + "," + balLen + ") AS unsigned)),0)+1 FROM inventory_transfer "
                                + " where invnt_type in(select invt_type from inventory_class_type_mapping where invt_type_desc = (select invt_type_desc "
                                + " from inventory_class_type_mapping where invt_type ='" + acType + "')) and cast(dest_brncode as unsigned) = " + brncode + " "
                                + " and length(INVNT_SR_ALPHA)= " + fullLen + "").getResultList();
                        if (!selectQuery.isEmpty()) {
                            Vector ele1 = (Vector) selectQuery.get(0);
                            if (ele1.get(0).toString().length() > balLen) {
                                throw new Exception("Series Initialization Is Required For " + acCode + " Of The Branch " + brncode);
                            } else {
                                String codeVal = String.format("%0" + balLen + "d", Integer.parseInt(ele1.get(0).toString()));
                                chequeList = brncode + acCode + codeVal;
                            }
                        }
                    }
                } else {
                    int balLen = fullLen - lenVal;
                    List selectQuery = em.createNativeQuery("SELECT ifnull(MAX(CAST(substring(INVNT_SR_ALPHA," + (lenVal + 1) + "," + balLen + ") AS unsigned)),0)+1 FROM inventory_transfer "
                            + " where invnt_type in(select invt_type from inventory_class_type_mapping where invt_type_desc = (select invt_type_desc "
                            + " from inventory_class_type_mapping where invt_type ='" + acType + "')) and cast(dest_brncode as unsigned) = " + brncode + " "
                            + " and length(INVNT_SR_ALPHA)= " + fullLen + "").getResultList();
                    if (!selectQuery.isEmpty()) {
                        Vector ele1 = (Vector) selectQuery.get(0);
                        if (ele1.get(0).toString().length() > balLen) {
                            throw new Exception("Series Initialization Is Required For " + acCode + " Of The Branch " + brncode);
                        } else {
                            String codeVal = String.format("%0" + balLen + "d", Integer.parseInt(ele1.get(0).toString()));
                            chequeList = brncode + acCode + codeVal;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return chequeList;
    }

    public String mergingInventoryDetail(String brCode, String alpha, String invtClass, String invtType, String fromNo, String endNo,
            String itemsPerUnit, String quantity, String enterDt, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0, var1 = 0;
            String tempFromNo = "";
            String tempToNo = "";
            String tmpDivVal = String.valueOf(Float.parseFloat(quantity) / Float.parseFloat(itemsPerUnit));
            String tmpValAfterDecimal = tmpDivVal.substring(tmpDivVal.indexOf(".") + 1);
            String tmpValBeforeDecimal = "";
            if (tmpValAfterDecimal.length() == 1 && tmpValAfterDecimal.equalsIgnoreCase("0")) {
                tmpValBeforeDecimal = tmpDivVal.substring(0, tmpDivVal.indexOf("."));
                tempFromNo = fromNo;
                tempToNo = endNo;
                for (int i = 1; i <= Integer.parseInt(tmpValBeforeDecimal); i++) {
                    if (Integer.parseInt(tmpValBeforeDecimal) == 1) {
                        Query updateStockQ = em.createNativeQuery("UPDATE chbookmaster_stock SET ENT_FLAG='MERGE', MOD_BY='" + enterBy + "',MOD_DATE = now() WHERE brncode='" + brCode + "' AND INVT_CLASS='" + invtClass + "' "
                                + " AND INVT_TYPE='" + invtType + "' AND Status='F' AND ChNoFrom='" + tempFromNo + "' AND ChNoTo='" + tempToNo + "' AND ENT_FLAG ='SPLIT'");
                        var = updateStockQ.executeUpdate();
                        if (var <= 0) {
                            ut.rollback();
                            return "Merge Failed !!!";
                        }
                    } else {
                        tempToNo = String.valueOf((Integer.parseInt(tempFromNo) + Integer.parseInt(itemsPerUnit)) - 1);
                        List chkLt = em.createNativeQuery("SELECT ChqSrNo,ChNoFrom,ChNoTo,Status FROM chbookmaster_stock WHERE ChqSrNo='" + alpha + "' AND ChNoFrom='" + tempFromNo + "' AND ChNoTo='" + tempToNo + "'").getResultList();
                        if (!chkLt.isEmpty()) {
                            Vector ele = (Vector) chkLt.get(0);
                            if (ele.get(3).toString().equalsIgnoreCase("U")) {
                                ut.rollback();
                                return "Cheque series " + tempFromNo + " to" + tempToNo + " is already used !!!";
                            }
                        }
                        Query insertStockQ = em.createNativeQuery("DELETE FROM chbookmaster_stock WHERE ChqSrNo='" + alpha + "' AND ChNoFrom='" + tempFromNo + "' AND ChNoTo='" + tempToNo + "' AND "
                                + " brncode='" + brCode + "' AND INVT_CLASS = '" + invtClass + "' AND INVT_TYPE='" + invtType + "' AND INVT_QUANTITY=" + itemsPerUnit + " AND "
                                + " ENT_FLAG='SPLIT' AND Status='F'");
                        var = insertStockQ.executeUpdate();
                        tempFromNo = String.valueOf(Integer.parseInt(tempFromNo) + Integer.parseInt(itemsPerUnit));
                        if (var <= 0) {
                            ut.rollback();
                            return "Merging failed in stock record,So please enter values carefully !!!";
                        }
                    }
                }
                if (Integer.parseInt(tmpValBeforeDecimal) != 1) {
                    List chkLt = em.createNativeQuery("SELECT ChqSrNo,ChNoFrom,ChNoTo,Status FROM chbookmaster_stock WHERE ChqSrNo='" + alpha + "' AND ChNoFrom='" + fromNo + "' AND ChNoTo='" + endNo + "'").getResultList();
                    if (!chkLt.isEmpty()) {
                        Vector ele = (Vector) chkLt.get(0);
                        if (ele.get(3).toString().equalsIgnoreCase("U")) {
                            ut.rollback();
                            return "Cheque series " + fromNo + " to" + endNo + " is already used !!!";
                        } else {
                            ut.rollback();
                            return "Duplicate entries are not allowed in stock record.Cheque series " + alpha + " ,cheque number from " + fromNo + " and cheque number to " + endNo + " entry already exists !!!";
                        }
                    }
                    Query insertMergeQ = em.createNativeQuery("INSERT INTO chbookmaster_stock (chqsrno,chnofrom,chnoto,status,issuedt,stockdt,enterby,"
                            + "brncode,invt_class,invt_type,invt_quantity,mod_by, mod_date,ent_flag) values('" + alpha + "','" + fromNo + "','"
                            + endNo + "','F',null, '" + enterDt + "','" + enterBy + "','" + brCode + "','" + invtClass + "','" + invtType + "',"
                            + quantity + ",'" + enterBy + "',now(),'MERGE')");
                    var1 = insertMergeQ.executeUpdate();
                    if (var1 <= 0) {
                        ut.rollback();
                        return "Merge Failed !!!";
                    }
                }
                if (var <= 0) {
                    ut.rollback();
                    return "Merging not completed !!!";
                } else {
                    ut.commit();
                    return "Merging completed succesfully.";
                }
            } else {
                ut.rollback();
                return "Please enter appropriate items per unit !!!";
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

    /**
     * ************************** InventoryTypeMaster *******************
     */
    public List inventoryClassCombo() throws ApplicationException {
        List invClass = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT REF_CODE,REF_DESC FROM cbs_ref_rec_type WHERE REF_REC_NO='014'");
            invClass = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return invClass;
    }

    public List chequeSeriesCombo(String brnCode, String invClass, String invType) throws ApplicationException {
        List chequeList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct chqsrno from chbookmaster_stock where brncode ='" + brnCode + "' and ENT_FLAG ='SPLIT' and INVT_CLASS = '" + invClass + "' and INVT_TYPE = '" + invType + "' and status='F'");
            chequeList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return chequeList;
    }

    public List inventoryClassAndDescription(String invtClass) throws ApplicationException {
        List invClass = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT REF_CODE,REF_DESC FROM cbs_ref_rec_type WHERE REF_REC_NO='014' And REF_CODE='" + invtClass + "'");
            invClass = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return invClass;
    }

    public List gridDetailOnModify(String invtClass) throws ApplicationException {
        List invClass = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT B.INVT_CLASS,A.REF_DESC AS INVT_CLASS_DESC,B.INVT_TYPE,B.INVT_TYPE_DESC,B.ENTERBY,DATE_FORMAT(B.ENTER_DATE,'%d/%m/%Y') FROM cbs_ref_rec_type A,inventory_class_type_mapping B "
                    + " WHERE B.INVT_CLASS=A.REF_CODE AND B.INVT_CLASS='" + invtClass + "' AND A.REF_REC_NO='014'");
            invClass = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return invClass;
    }

    public String saveRecord(List<InventoryMasterGridFile> gridDetail, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0, var1 = 0;
            if (gridDetail == null || gridDetail.isEmpty()) {
                ut.rollback();
                return "Record not saved !!!";
            }
            List chk1 = em.createNativeQuery("SELECT REF_CODE FROM cbs_ref_rec_type WHERE REF_CODE='" + gridDetail.get(0).getInvtClass() + "' AND REF_REC_NO='014'").getResultList();
            if (!chk1.isEmpty()) {
                ut.rollback();
                return "This inventory class already exists !!!";
            }
            Query insertRefQ = em.createNativeQuery("INSERT INTO cbs_ref_rec_type VALUES('014','" + gridDetail.get(0).getInvtClass() + "',"
                    + " '" + gridDetail.get(0).getInvtClassDesc() + "','" + gridDetail.get(0).getEnterBy() + "',now(),'',now(),0,NULL)");
            var = insertRefQ.executeUpdate();
            if (var <= 0) {
                ut.rollback();
                return "Record not saved in inventory class table !!!";
            }
            for (int i = 0; i < gridDetail.size(); i++) {
                List chk2 = em.createNativeQuery("SELECT INVT_TYPE FROM inventory_class_type_mapping WHERE INVT_CLASS='" + gridDetail.get(0).getInvtClass() + "' AND INVT_TYPE='" + gridDetail.get(i).getInvtType() + "'").getResultList();
                if (!chk2.isEmpty()) {
                    ut.rollback();
                    return "Duplicate inventory types could not be saved for same inventory class,So please enter different inventory types carefully !!!";
                }
                Query insertMapQ = em.createNativeQuery("INSERT INTO inventory_class_type_mapping VALUES('" + gridDetail.get(i).getInvtClass() + "','" + gridDetail.get(i).getInvtType() + "','" + gridDetail.get(i).getAcNature() + "',"
                        + " '" + gridDetail.get(i).getEnterBy() + "','','" + gridDetail.get(i).getEnterDate() + "','19000101',now(),'" + brCode + "')");
                var1 = insertMapQ.executeUpdate();
                if (var1 <= 0) {
                    ut.rollback();
                    return "Record not saved in inventory class map table !!!";
                }
            }
            if (var <= 0 || var1 <= 0) {
                ut.rollback();
                return "Record could not be saved !!!";
            } else {
                ut.commit();
                return "Record saved succesfully.";
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

    public String addNewInvtTypeInExistingInvtClass(List<InventoryMasterGridFile> gridDetail, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            if (gridDetail == null || gridDetail.isEmpty()) {
                ut.rollback();
                return "Record not saved !!!";
            }
            for (int i = 0; i < gridDetail.size(); i++) {
                List chk2 = em.createNativeQuery("SELECT INVT_TYPE FROM inventory_class_type_mapping WHERE INVT_CLASS='" + gridDetail.get(0).getInvtClass() + "' AND INVT_TYPE='" + gridDetail.get(i).getInvtType() + "'").getResultList();
                if (!chk2.isEmpty()) {
                    ut.rollback();
                    return "Duplicate inventory types could not be saved for same inventory class,So please enter different inventory types carefully !!!";
                }
                Query insertMapQ = em.createNativeQuery("INSERT INTO inventory_class_type_mapping VALUES('" + gridDetail.get(i).getInvtClass() + "','" + gridDetail.get(i).getInvtType() + "','" + gridDetail.get(i).getAcNature() + "',"
                        + " '" + gridDetail.get(i).getEnterBy() + "','','" + gridDetail.get(i).getEnterDate() + "','19000101',now(),'" + brCode + "')");
                var = insertMapQ.executeUpdate();
                if (var <= 0) {
                    ut.rollback();
                    return "Record not saved in inventory class map table !!!";
                }
            }
            if (var <= 0) {
                ut.rollback();
                return "Record could not be saved !!!";
            } else {
                ut.commit();
                return "Record saved succesfully.";
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

    public String updateRecord(String invtClass, String oldInvtType, String oldInvtTypeDesc, String invtType, String invtTypeDesc, String modBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            if (oldInvtType == null || oldInvtType.equalsIgnoreCase("") || oldInvtType.length() == 0) {
                ut.rollback();
                return "Please select the row again !!!";
            }
            if (invtType == null || invtType.equalsIgnoreCase("") || invtType.length() == 0) {
                ut.rollback();
                return "Please enter inventory type !!!";
            }
            if (invtTypeDesc == null || invtTypeDesc.equalsIgnoreCase("") || invtTypeDesc.length() == 0) {
                invtTypeDesc = "";
            }
            List chk2 = em.createNativeQuery("SELECT INVT_TYPE FROM inventory_class_type_mapping WHERE INVT_CLASS='" + invtClass + "' AND INVT_TYPE='" + invtType + "'").getResultList();
            if (!chk2.isEmpty()) {
                ut.rollback();
                return "This inventory type already exists for this inventory class,So please enter different inventory types carefully !!!";
            }
            Query updateQuery = em.createNativeQuery("UPDATE inventory_class_type_mapping SET modby = '" + modBy + "',INVT_TYPE='" + invtType + "',INVT_TYPE_DESC='" + invtTypeDesc + "'"
                    + " WHERE INVT_TYPE='" + oldInvtType + "' AND INVT_CLASS='" + invtClass + "'");
            var = updateQuery.executeUpdate();
            if (var <= 0) {
                ut.rollback();
                return "Record could not be updated !!!";
            } else {
                ut.commit();
                return "Record updated succesfully.";
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

    public String deleteRecord(String invtClass, String invtType, String invtTypeDesc) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            if (invtClass == null || invtClass.equalsIgnoreCase("") || invtClass.length() == 0) {
                ut.rollback();
                return "Please select the row again !!!";
            }
            if (invtType == null || invtType.equalsIgnoreCase("") || invtType.length() == 0) {
                ut.rollback();
                return "Please select the row again !!!";
            }
            if (invtType == null || invtType.equalsIgnoreCase("") || invtType.length() == 0) {
                ut.rollback();
                return "Please select the row again !!!";
            }
            if (invtTypeDesc == null || invtTypeDesc.equalsIgnoreCase("") || invtTypeDesc.length() == 0) {
                ut.rollback();
                return "Please select the row again !!!";
            }
            Query deleteQuery = em.createNativeQuery("DELETE FROM inventory_class_type_mapping WHERE INVT_CLASS='" + invtClass + "' AND INVT_TYPE='" + invtType + "'");
            var = deleteQuery.executeUpdate();
            if (var <= 0) {
                ut.rollback();
                return "Record could not be deleted !!!";
            } else {
                ut.commit();
                return "Record deleted succesfully.";
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

    /**
     * ********************* End ******************** /
     *
     *
     * /*********************************
     * IssuecheckBookRegister***********************
     */
    public List gridLoadForStockDetail(String brnCode, String invtClass, String invtType, String invtSrNo) throws ApplicationException {
        List fromNo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT ChNoFrom,ChNoTo,DATE_FORMAT(Stockdt,'%d/%m/%Y') AS Stockdt FROM chbookmaster_stock WHERE brncode='" + brnCode + "' AND INVT_CLASS='" + invtClass + "' "
                    + " AND INVT_TYPE='" + invtType + "' AND ChqSrNo='" + invtSrNo + "' AND Status='F' AND ENT_FLAG='SPLIT'");
            fromNo = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return fromNo;
    }

    public List tableDataIssue(String acctNum) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select * from chbookmaster where acno='" + acctNum + "' order by chnofrom");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List getAccountDetails(String acctNum) throws ApplicationException {
        try {
            Query selectQuery = em.createNativeQuery("select am.acno,am.custname,am.nomination,am.jtname1,am.jtname2,am.jtname3,cb.Description, "
                    + "am.accstatus From accountmaster am, codebook cb where am.acno='" + acctNum + "' and am.operMode=cb.code and cb.Groupcode=4 "
                    + "and (am.authby is not null or am.authby <>'')");
            return selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List cheqSeriesValidation(String chqSeriesNo, String orgnCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {

            Query selectQuery = em.createNativeQuery("Select chbk.chnoFrom,chbk.chnoTo From chbookmaster chbk,accountmaster am Where chbk.ChBookNo = '" + chqSeriesNo + "' AND chbk.acno=am.acno and am.curBrCode = '" + orgnCode + "'");

            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List cheqFromValidation(String acctNum, String chSrNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {

            Query selectQuery = em.createNativeQuery("Select chnoFrom,chnoTo From chbookmaster Where acno = '" + acctNum + "' and chbookno ='" + chSrNo + "'");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List cheqSeriesNum(String acctType) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select chqsrno from accounttypemaster where acctcode='" + acctType + "'");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public String ChqBookIssueSaveUpdation(String command, int chqFrom, int chqTo, String cheqSeries, int noOfLeaves, String fullAccountNo, String acctType, String remarks,
            String userName, String issueDt, String chqbookcharges, String invtClass, String invtType, String atPar, String chBookType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();

        try {
            ut.begin();
            String result = null;
            //String tmpChqseriesNo = "";
            String tmpNatCh = "";
            String brnCode;
            String acNat;
            brnCode = ftsPostRemote.getCurrentBrnCode(fullAccountNo);
            acNat = ftsPostRemote.getAccountNature(fullAccountNo);
            int leafs = (chqTo - chqFrom) + 1;
            if (leafs != noOfLeaves) {
                ut.rollback();
                return "NO OF LEAFS IS NOT CORRECT !";
            }

            List tmpNat = em.createNativeQuery("SELECT INVT_TYPE_DESC FROM inventory_class_type_mapping WHERE INVT_TYPE='" + invtType + "'").getResultList();
            if (tmpNat.size() > 0) {
                Vector eleNat = (Vector) tmpNat.get(0);
                tmpNatCh = eleNat.get(0).toString();
            }

//            if (!tmpNatCh.equalsIgnoreCase(acNat)) {
//                ut.rollback();
//                return "This Series is Not For This Account Nature!";
//            }
            int code = ftsPostRemote.getCodeForReportName("CHQ-ISSUE-ON");
            if (code == 1 && !tmpNatCh.equalsIgnoreCase(acNat)) {
                ut.rollback();
                return "This Series is Not For This Account Nature!";
            } else if (code == 0 && !tmpNatCh.equalsIgnoreCase(fullAccountNo.substring(2, 4))) {
                ut.rollback();
                return "This Series is Not For This Account Nature!";
            }

            // added by rahul jain 19-08-2019 
            String bankCode = "";
            List bankList = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
            if (!bankList.isEmpty()) {
                Vector element = (Vector) bankList.get(0);
                bankCode = element.get(0).toString();
            }
            if (bankCode.equalsIgnoreCase("KHAT")) {
                String nature = ftsPostRemote.getAccountNature(fullAccountNo);
                String tableName = "chbook_sb";
                if (nature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    tableName = "chbook_ca";
                }
                for (int i = chqFrom; i <= chqTo; i++) {
                    List chqlist = em.createNativeQuery("select * from  " + tableName + "  where Chqno =" + i + " and Acno ='" + fullAccountNo + "'").getResultList();
                    if (!chqlist.isEmpty()) {
                        ut.rollback();
                        return "This Series is Not allowed for this Account number.!";
                    }
                }
            }
       //////  
            if (command.equalsIgnoreCase("save")) {
                String san = "";
                List sanList = em.createNativeQuery("SELECT SAN FROM chbookmaster WHERE acno='" + fullAccountNo + "'").getResultList();
                if (sanList.isEmpty()) {
                    sanList = em.createNativeQuery("SELECT max(cast(ifnull(SAN,0) as unsigned))+1 FROM chbookmaster").getResultList();
                }
                Vector eleNat = (Vector) sanList.get(0);
                san = CbsUtil.lPadding(6, Integer.parseInt(eleNat.get(0).toString()));

                Query insertparameterInfo = em.createNativeQuery("insert into chbookmaster(acno,chbookno,chnofrom,chnoto,remarks,issuedby,issuedt,leafs,auth,ChargeFlag,atpar,chbooktype,san)"
                        + " values('" + fullAccountNo + "','" + cheqSeries + "'," + chqFrom + "," + chqTo + ",'" + remarks + "','" + userName + "','" + issueDt + "'," + leafs + ",'N','"
                        + chqbookcharges + "','" + atPar + "','" + chBookType + "','" + san + "')");
                Integer insertinfo = insertparameterInfo.executeUpdate();

                Query updateparameter = em.createNativeQuery("Update chbookmaster_stock set IssueDt='" + issueDt + "',status='U' Where chNoFrom=" + chqFrom + " and chNoTo=" + chqTo + " And ChqSrNo='"
                        + cheqSeries + "' AND brncode = '" + brnCode + "' AND INVT_CLASS='" + invtClass + "' AND INVT_TYPE='" + invtType + "'");
                Integer updateparameterInfo = updateparameter.executeUpdate();

                if (insertinfo > 0 || updateparameterInfo > 0) {
                    result = "Record Saved Successfully";
                } else {
                    ut.rollback();
                    result = "Record Not Saved";
                    return result;
                }

            }
            Query updateparameter = em.createNativeQuery("Update accountmaster set ChequeBook=1 where acno='" + fullAccountNo + "'");
            Integer ChBookMaster = updateparameter.executeUpdate();
            if (ChBookMaster <= 0) {
                ut.rollback();
                result = "Data Not Updated in AccountMaster .";
                return result;
            }

            ut.commit();
            return result;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String ChqBookIssueWithSlabDeatilSave(String chqFrom, String chqTo, String noOfLeaves, String cheqSeries, String instType, String cmbAmtfrm, String cmbAmtTo,
            String combCode, String invtClass, String invtType, String printlot, String brnCode, String userName, String Date) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = null;
            Query insertparameterInfo = em.createNativeQuery("insert into chbookmaster_amtwise(InstCode,SlabCode,NumFrom,NumTo,AmtFrom,AmtTo,Leaves,"
                    + "PrintLot,Status,Dt,EnterBy,IssueDt,IssueBy,Auth,AuthBy,brncode)values('" + instType + "','" + combCode + "','" + chqFrom + "','" + chqTo + "',"
                    + " '" + cmbAmtfrm + "','" + cmbAmtTo + "','" + noOfLeaves + "','" + printlot + "','I','" + ymd.format(dmyOne.parse(Date)) + "','" + userName + "','" + ymd.format(dmyOne.parse(Date)) + "','" + userName + "','N','','" + brnCode + "')");
            Integer insertinfo = insertparameterInfo.executeUpdate();

            if (insertinfo > 0) {
                result = "Record Saved Successfully";
            } else {
                ut.rollback();
                result = "Record Not Saved";
                return result;
            }
            ut.commit();
            return result;

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String ChqBookIssueDelete(String acctNo, int chqFrom, int chqTo, String acctType, String invtSrNo, String invtClass, String invtType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = null;
            String brnCode;
            //    brnCode = acctNo.substring(0, 2);
            brnCode = ftsPostRemote.getCurrentBrnCode(acctNo);
            List checkInstNo = em.createNativeQuery("Select * From chbookmaster Where Acno='" + acctNo + "' And ChnoFrom=" + chqFrom + " And chnoTo=" + chqTo + " And Auth='N'").getResultList();
            if (checkInstNo.size() > 0) {
                Integer deleteData = em.createNativeQuery("Update chbookmaster_stock Set IssueDt = now(), Status='F' Where chNoFrom=" + chqFrom + " and chNoTo=" + chqTo + " and ChqSrNo='" + invtSrNo + "' AND brncode = '" + brnCode + "' AND INVT_CLASS='" + invtClass + "' AND INVT_TYPE='" + invtType + "'").executeUpdate();
                Query deletechq = em.createNativeQuery("Delete from chbookmaster Where acno='" + acctNo + "' and chnofrom=" + chqFrom + " and chnoto=" + chqTo + "");
                Integer delete = deletechq.executeUpdate();
                if (deleteData > 0 && delete > 0) {
                    result = "Record Is Deleted Successfully";
                } else {
                    ut.rollback();
                    result = "Neither Data Updated in chBookMaster_Stock, Nor Deleted From chBookMaster";
                    return result;
                }
            } else {
                result = "Sorry,Authorized Cheque Book Cannot Be Deleted";
            }
            ut.commit();
            return result;
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

    public String introducerAcDetail(String introAcno) throws ApplicationException {
        try {
            String acNat = ftsPostRemote.getAccountNature(introAcno);
            List statusList;
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                statusList = em.createNativeQuery("select acno, custname, accstatus from td_accountmaster where acno='" + introAcno + "' and "
                        + "(authby is not null or authby <>'')").getResultList();
            } else {
                statusList = em.createNativeQuery("select acno, custname, accstatus from accountmaster where acno='" + introAcno + "' and "
                        + "(authby is not null or authby <>'')").getResultList();
            }
            if (statusList.isEmpty()) {
                throw new ApplicationException("Account does not exist or not authorized");
            }
            Vector element = (Vector) statusList.get(0);
            return element.get(2).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * ****************************************
     * End********************************
     */
    /**
     * ********************************InventoryMovementBetweenLocations********************
     */
    public List inventoryClassComboList() throws ApplicationException {
        List invClass = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT REF_CODE,REF_DESC FROM cbs_ref_rec_type WHERE REF_REC_NO='014'");
            invClass = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return invClass;
    }

    public List inventoryTypeCombo(String invtClass) throws ApplicationException {
        List invType = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT INVT_TYPE FROM inventory_class_type_mapping WHERE INVT_CLASS='" + invtClass + "' ORDER BY INVT_TYPE ");
            invType = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return invType;
    }

    public List locationClassDesc(String locClass) throws ApplicationException {
        List invType = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT REF_DESC FROM cbs_ref_rec_type WHERE REF_REC_NO='036' AND REF_CODE='" + locClass + "'");
            invType = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return invType;
    }

    public List locationClass() throws ApplicationException {
        List invType = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT REF_CODE,REF_DESC FROM cbs_ref_rec_type WHERE REF_REC_NO='036'");
            invType = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return invType;
    }

    public List branchName(String brnCode) throws ApplicationException {
        List invType = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT BranchName FROM branchmaster WHERE BrnCode=CAST('" + brnCode + "' AS unsigned)");
            invType = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return invType;
    }

    public List gridLoadOnMod(String brnCode) throws ApplicationException {
        List invType = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select org_brncode,dest_brncode,fr_invnt_loc_class,to_invnt_loc_class,invnt_class,invnt_type,"
                    + "invnt_sr_alpha,invnt_sr_no as startno,((cast(invnt_sr_no as unsigned)+invnt_qty)-1) as endno,invnt_qty,invnt_tr_desc,invnt_value,date_format(invnt_trn_dt,'%d/%m/%y') as trandt,"
                    + "invnt_trn_no,invnt_trn_sr_no from inventory_transfer where org_brncode='" + brnCode + "' and authby1='' and authby3='' and del_flg <>'Y' and auth_act_flg1<>'Y' and auth_act_flg3<>'Y'");
            invType = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return invType;
    }

    public List gridLoadOnEnquiry(String brnCode) throws ApplicationException {
        List invType = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select org_brncode,dest_brncode,fr_invnt_loc_class,to_invnt_loc_class,invnt_class,invnt_type,"
                    + "invnt_sr_alpha,invnt_sr_no as startno,((cast(invnt_sr_no as unsigned)+invnt_qty)-1) as endno,"
                    + "invnt_qty,invnt_tr_desc,invnt_value,date_format(invnt_trn_dt,'%d/%m/%y') as trandt,invnt_trn_no,invnt_trn_sr_no from "
                    + "inventory_transfer where org_brncode='" + brnCode + "' and del_flg <>'Y' and authby1='' and authby3='' and auth_act_flg1<>'Y' and auth_act_flg3<>'Y'");
            invType = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return invType;
    }

    public List gridLoadOnAcknowledge(String brnCode) throws ApplicationException {
        List invType = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select org_brncode,dest_brncode,fr_invnt_loc_class,to_invnt_loc_class,invnt_class,invnt_type,"
                    + "invnt_sr_alpha,invnt_sr_no as startno,((cast(invnt_sr_no as unsigned)+invnt_qty)-1) as endno,invnt_qty,"
                    + "invnt_tr_desc,invnt_value,date_format(invnt_trn_dt,'%d/%m/%y') as trandt,invnt_trn_no,invnt_trn_sr_no "
                    + "from inventory_transfer where org_brncode='" + brnCode + "' AND AUTHBY1 <> '' AND AUTHBY3='' AND DEL_FLG <>'Y' AND AUTH_ACT_FLG1='Y' AND AUTH_ACT_FLG3<>'Y'");
            invType = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return invType;
    }

    public String saveMovementDetail(List<InventoryMovementGridDetail> gridDetail) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String alpha = "";
        try {
            ut.begin();
            int var1 = 0;
            if (gridDetail == null || gridDetail.isEmpty()) {
                ut.rollback();
                return "Record not saved !!!";
            }
            List tranNoLt = em.createNativeQuery("SELECT COALESCE(MAX(COALESCE(CAST(IFNULL(INVNT_TRN_NO,0) AS unsigned),0)),0)+1 FROM inventory_transfer").getResultList();
            Vector ele = (Vector) tranNoLt.get(0);
            String tranNo = ele.get(0).toString();
            for (int i = 0; i < gridDetail.size(); i++) {

                Integer endNo = (Integer.parseInt(gridDetail.get(i).getStartNo()) + (Integer.parseInt(gridDetail.get(i).getQuantity()) - 1));

                alpha = alphaCodeList(Integer.parseInt(gridDetail.get(i).getToBrnId()), gridDetail.get(i).getInvtType(), endNo);
                int trnSrNo = i + 1;
                List chkLt = em.createNativeQuery("SELECT INVNT_CLASS,INVNT_TYPE FROM inventory_transfer WHERE ORG_BRNCODE='" + gridDetail.get(i).getFromBrnId() + "' AND INVNT_TRN_DT='" + gridDetail.get(i).getEnterDate() + "' AND INVNT_TRN_NO='" + tranNo + "' AND INVNT_TRN_SR_NO='" + trnSrNo + "'").getResultList();
                if (!chkLt.isEmpty()) {
                    ut.rollback();
                    return "Sorry, duplicate entries are not allowed in inventory transfer !!!";
                }

//                List chkLtD = em.createNativeQuery("SELECT T.INVNT_CLASS,T.INVNT_TYPE FROM INVENTORY_TRANSFER T,INVENTORY_CLASS_TYPE_MAPPING M WHERE "
//                        + "CAST(T.INVNT_SR_NO AS BIGINT) BETWEEN '" + gridDetail.get(i).getStartNo() + "' AND '" + endNo.toString()
//                        + "' AND T.DEST_BRNCODE='" + gridDetail.get(i).getToBrnId() + "' AND T.ENTITY_CRE_FLG<>'DEL' AND T.DEL_FLG<>'Y'"
//                        + "AND M.INVT_CLASS=T.INVNT_CLASS AND M.INVT_TYPE=T.INVNT_TYPE AND M.INVT_CLASS='"+gridDetail.get(i).getInvtClass() +"' AND M.INVT_TYPE='"+gridDetail.get(i).invtType +"' "
//                        + "AND M.INVT_TYPE_DESC IN (SELECT INVT_TYPE_DESC FROM INVENTORY_CLASS_TYPE_MAPPING WHERE INVT_CLASS='" + gridDetail.get(i).getInvtClass() + "' "
//                        + "AND INVT_TYPE = '" + gridDetail.get(i).getInvtType() + "')").getResultList();
                List chkLtD = em.createNativeQuery("SELECT T.INVNT_CLASS,T.INVNT_TYPE FROM inventory_transfer T,inventory_class_type_mapping M WHERE "
                        + "CAST(T.INVNT_SR_NO AS unsigned) BETWEEN '" + gridDetail.get(i).getStartNo() + "' AND '" + endNo.toString()
                        + "' AND T.DEST_BRNCODE='" + gridDetail.get(i).getToBrnId() + "' AND T.ENTITY_CRE_FLG<>'DEL' AND T.DEL_FLG<>'Y'"
                        + "AND M.INVT_CLASS='" + gridDetail.get(i).getInvtClass() + "' "
                        //+ "AND M.INVT_TYPE_DESC IN (SELECT INVT_TYPE_DESC FROM INVENTORY_CLASS_TYPE_MAPPING WHERE INVT_CLASS='" + gridDetail.get(i).getInvtClass() + "' "
                        + "AND SUBSTRING(INVT_TYPE,1,2) = '" + gridDetail.get(i).getInvtType().substring(0, 2) + "'"
                        + "AND INVNT_SR_ALPHA = '" + alpha + "' "
                        + "AND T.INVNT_TYPE=M.INVT_TYPE").getResultList();
                if (!chkLtD.isEmpty()) {
                    ut.rollback();
                    return "Sorry, duplicate entries between inventory start number,end number and nature are not allowed in inventory transfer !!";
                }

                chkLtD = em.createNativeQuery("SELECT T.INVT_CLASS,T.INVT_TYPE FROM chbookmaster_stock T,inventory_class_type_mapping M WHERE "
                        + "CAST(T.chNOfrom AS unsigned) = '" + gridDetail.get(i).getStartNo() + "' AND CAST(T.chNOto AS unsigned) = '" + endNo.toString() + "' "
                        + "AND T.BRNCODE='" + gridDetail.get(i).getToBrnId() + "' AND ent_flag='ENT' AND M.INVT_CLASS='" + gridDetail.get(i).getInvtClass() + "' "
                        + "AND M.INVT_TYPE = '" + gridDetail.get(i).getInvtType() + "' and M.INVT_TYPE=T.INVT_TYPE AND M.INVT_CLASS=T.INVT_CLASS AND M.INVT_TYPE_DESC IN (SELECT INVT_TYPE_DESC FROM "
                        + "inventory_class_type_mapping WHERE INVT_CLASS='" + gridDetail.get(i).getInvtClass() + "' AND INVT_TYPE = "
                        + "'" + gridDetail.get(i).getInvtType() + "')").getResultList();

                if (!chkLtD.isEmpty()) {
                    ut.rollback();
                    return "Sorry, duplicate entries between inventory start number,end number and nature are not allowed in inventory transfer !!";
                }
                Query insertTranQ = em.createNativeQuery("INSERT INTO inventory_transfer VALUES('" + gridDetail.get(i).getFromBrnId() + "','" + gridDetail.get(i).getEnterDate() + "','" + tranNo + "','" + trnSrNo + "','ENT','N','" + gridDetail.get(i).getInvtClass() + "','" + gridDetail.get(i).getInvtType() + "',"
                        + " '" + gridDetail.get(i).getStartNo() + "','" + alpha + "','" + gridDetail.get(i).getFromBrnLocClass() + "','" + gridDetail.get(i).getToBrnId() + "','" + gridDetail.get(i).getToBrnLocClass() + "'," + gridDetail.get(i).getQuantity() + "," + Float.parseFloat(gridDetail.get(i).getInvtValue()) + ",'" + gridDetail.get(i).getTrfParticular() + "',"
                        + " '','','','','N','','N','','" + gridDetail.get(i).getEnterBy() + "','" + gridDetail.get(i).getEnterDate() + "','" + gridDetail.get(i).getEnterBy() + "','" + gridDetail.get(i).getEnterDate() + "',0)");
                var1 = insertTranQ.executeUpdate();
                if (var1 <= 0) {
                    ut.rollback();
                    return "Record not saved in transaction table !!!";
                }
            }
            if (var1 <= 0) {
                ut.rollback();
                return "Record could not be saved !!!";
            } else {
                ut.commit();
                return "true" + tranNo;
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

    /**
     * This method will not be used because updation is not allowed in this
     * screen and invt transaction number has been removed from
     * chbookmaster_stock table*
     */
    public String updateMovementDetail(String tranNo, String tranSrNo, String fromBrnId, String toBrnId, String fromBrnLocClass, String toBrnLocClass,
            String invtClass, String invtType, String alpha, String fromNo, String toNo,
            String quantity, String trfParticular, String invtValue, String modBy, String modDate, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0, var1 = 0;
            Query updateStockQ = em.createNativeQuery("UPDATE chbookmaster_stock SET ChqSrNo='" + alpha + "',ChNoFrom='" + fromNo + "',"
                    + " ChNoTo='" + toNo + "',brncode='" + toBrnId + "',INVT_CLASS='" + invtClass + "',INVT_TYPE='" + invtType + "',INVT_QUANTITY='" + quantity + "',"
                    + " MOD_BY = '" + modBy + "', MOD_DATE='" + modDate + "' WHERE INVT_TRAN_NO='" + tranNo + "' AND INVT_TRAN_SRNO='" + tranSrNo + "'");
            var = updateStockQ.executeUpdate();
            if (var <= 0) {
                ut.rollback();
                return "Record not updated in stock !!!";
            }
            List chk = em.createNativeQuery("SELECT MAX(TS_CNT)+1 FROM inventory_transfer WHERE INVNT_TRN_NO='" + tranNo + "' AND INVNT_TRN_SR_NO='" + tranSrNo + "'").getResultList();
            Vector ele = (Vector) chk.get(0);
            String tsCnt = ele.get(0).toString();
            Query updateInvtQ = em.createNativeQuery("UPDATE inventory_transfer SET ORG_BRNCODE='" + fromBrnId + "',INVNT_CLASS='" + invtClass + "',"
                    + " INVNT_TYPE='" + invtType + "',INVNT_SR_NO='" + fromNo + "',INVNT_SR_ALPHA='" + alpha + "',FR_INVNT_LOC_CLASS='" + fromBrnLocClass + "',DEST_BRNCODE='" + toBrnId + "',"
                    + " TO_INVNT_LOC_CLASS='" + toBrnLocClass + "',INVNT_QTY=" + quantity + ",INVNT_VALUE=" + invtValue + ",INVNT_TR_DESC='" + trfParticular + "',"
                    + " LAST_UPDATED_BY_USER_ID='" + modBy + "',LAST_UPDATED_DATE='" + modDate + "',TS_CNT=" + tsCnt + " WHERE INVNT_TRN_NO='" + tranNo + "' AND INVNT_TRN_SR_NO='" + tranSrNo + "'");
            var1 = updateInvtQ.executeUpdate();
            if (var1 <= 0) {
                ut.rollback();
                return "Record not updated in inventory transfer !!!";
            }
            if (var <= 0 || var1 <= 0) {
                ut.rollback();
                return "Record could not be updated !!!";
            } else {
                ut.commit();
                return "Record updated succesfully.";
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

    public String verifyTranNumber(String tranNo, String authBy, String trnSrNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String tempEnterBy = "";
        try {
            ut.begin();
            List chkLt = em.createNativeQuery("SELECT CREATED_BY_USER_ID FROM inventory_transfer WHERE INVNT_TRN_NO='" + tranNo + "' AND INVNT_TRN_SR_NO='" + trnSrNo + "'").getResultList();
            if (chkLt.isEmpty()) {
                throw new ApplicationException("Enter by is not present !");
            } else {
                Vector element = (Vector) chkLt.get(0);
                tempEnterBy = element.get(0).toString();
            }
            if (tempEnterBy.equalsIgnoreCase(authBy)) {
                throw new ApplicationException("Sorry, you cannot verify your own entry !");
            }

            Query updateInvtTran = em.createNativeQuery("UPDATE inventory_transfer SET AUTHBY1='" + authBy + "',AUTH_ACT_FLG1='Y' WHERE INVNT_TRN_NO='" + tranNo + "' AND INVNT_TRN_SR_NO='" + trnSrNo + "'");
            int var = updateInvtTran.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Record not verified in inventory transfer !");
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (ApplicationException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String acknowledgeTranNumber(String alpha, int startNo, int endNo, String enterDate, String toBrnId, String invtClass, String invtType, int quantity, String invtTranNo, String userName, String invtTranSrNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        int var = 0, var1 = 0;
        String ackFlag = "", acknowledgeBy = "", authFlag = "", authBy = "";
        try {
            ut.begin();
            List chkAuth = em.createNativeQuery("SELECT ifnull(AUTHBY1,''),ifnull(AUTH_ACT_FLG1,'') FROM inventory_transfer WHERE INVNT_TRN_NO='" + invtTranNo + "' AND INVNT_TRN_SR_NO='" + invtTranSrNo + "'").getResultList();
            if (!chkAuth.isEmpty()) {
                Vector element = (Vector) chkAuth.get(0);
                authBy = element.get(0).toString();
                authFlag = element.get(1).toString();
            }
            if (authFlag.equalsIgnoreCase("N") || authBy.equalsIgnoreCase("")) {
                throw new ApplicationException("This inventory transaction number is still not authorized !");
            }

            List chkAck = em.createNativeQuery("SELECT ifnull(AUTHBY3,''),ifnull(AUTH_ACT_FLG3,'') FROM inventory_transfer WHERE INVNT_TRN_NO='" + invtTranNo + "' AND INVNT_TRN_SR_NO='" + invtTranSrNo + "'").getResultList();
            if (!chkAck.isEmpty()) {
                Vector element = (Vector) chkAck.get(0);
                acknowledgeBy = element.get(0).toString();
                ackFlag = element.get(1).toString();
            }
            if (ackFlag.equalsIgnoreCase("Y")) {
                throw new ApplicationException("This inventory transaction number is already acknowledged by " + acknowledgeBy + " !");
            }

            List chkLt = em.createNativeQuery("SELECT ChqSrNo,ChNoFrom,ChNoTo FROM chbookmaster_stock WHERE ChqSrNo='" + alpha + "' AND ChNoFrom='" + startNo + "' AND ChNoTo='" + endNo + "'").getResultList();
            if (!chkLt.isEmpty()) {
                throw new ApplicationException("Duplicate entries are not allowed in stock record.Cheque series " + alpha + " ,cheque number from " + startNo + " and cheque number to " + endNo + " entry already exists !");
            }

            String tempEnterDt = enterDate.substring(6) + enterDate.substring(3, 5) + enterDate.substring(0, 2);
//            if(invtClass.equalsIgnoreCase("PO")){
//                 var1 = em.createNativeQuery("insert into chbookmaster_amtwise(InstCode,SlabCode,Amtfrom,AmtTo,NumFrom,NumTo, Leaves,PrintLot,"
//                         + "Dt,Enterby,status,issuedt,auth,ISSUEBY,brncode) values ('" + invtClass + "',5,0,1000000000," + startNo + "," + endNo + "," 
//                         + quantity + ",'" + alpha + "','" + tempEnterDt + "','" + userName + "','I','" + tempEnterDt  + "','N',' ','" + toBrnId + "')").executeUpdate();
//            }else{
            Query insertStockQ = em.createNativeQuery("INSERT INTO chbookmaster_stock (chqsrno,chnofrom,chnoto,status,issuedt,stockdt,enterby,"
                    + "brncode,invt_class,invt_type,invt_quantity,mod_by,mod_date,ent_flag) values('" + alpha + "','" + startNo + "','" + endNo
                    + "','F',null,'" + tempEnterDt + "','" + userName + "','" + toBrnId + "','" + invtClass + "','" + invtType + "'," + quantity
                    + ",'',null,'ENT')");
            var1 = insertStockQ.executeUpdate();
            // }

            if (var1 <= 0) {
                throw new ApplicationException("Record not saved in stock table !");
            }

            Query updateInvtTran = em.createNativeQuery("UPDATE inventory_transfer SET AUTHBY3='" + userName + "',AUTH_ACT_FLG3='Y' WHERE INVNT_TRN_NO='" + invtTranNo + "' AND INVNT_TRN_SR_NO='" + invtTranSrNo + "'");
            var = updateInvtTran.executeUpdate();
            if (var <= 0 && var1 <= 0) {
                throw new ApplicationException("Record not acknowledged !");
            }
            ut.commit();
            return "true" + alpha;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (ApplicationException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String deleteInvtTransactionNumber(String tranNo, String delBy, String trnSrNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String authFlag = "", authBy = "", ackFlag = "", acknowledgeBy = "", deleteFlag = "";
            List chkAuth = em.createNativeQuery("SELECT ifnull(AUTHBY1,''),ifnull(AUTH_ACT_FLG1,''),ifnull(DEL_FLG,'') FROM inventory_transfer WHERE INVNT_TRN_NO='" + tranNo + "' AND INVNT_TRN_SR_NO='" + trnSrNo + "'").getResultList();
            if (!chkAuth.isEmpty()) {
                Vector element = (Vector) chkAuth.get(0);
                authBy = element.get(0).toString();
                authFlag = element.get(1).toString();
                deleteFlag = element.get(2).toString();
            }
            if (deleteFlag.equalsIgnoreCase("Y")) {
                throw new ApplicationException("This transaction number is already deleted!");
            }
            if (authFlag.equalsIgnoreCase("Y") || !authBy.equalsIgnoreCase("")) {
                throw new ApplicationException("This inventory transaction number is authorized by " + authBy + " ,so this cannot be deleted !");
            }

            List chkAck = em.createNativeQuery("SELECT ifnull(AUTHBY3,''),ifnull(AUTH_ACT_FLG3,'') FROM inventory_transfer WHERE INVNT_TRN_NO='" + tranNo + "' AND INVNT_TRN_SR_NO='" + trnSrNo + "'").getResultList();
            if (!chkAck.isEmpty()) {
                Vector ele = (Vector) chkAck.get(0);
                acknowledgeBy = ele.get(0).toString();
                ackFlag = ele.get(1).toString();
            }
            if (ackFlag.equalsIgnoreCase("Y")) {
                throw new ApplicationException("This inventory transaction number is already acknowledged by " + acknowledgeBy + " ,so this cannot be deleted !");
            }

            Query deleteInvtTran = em.createNativeQuery("UPDATE inventory_transfer SET ENTITY_CRE_FLG='DEL' , DEL_FLG='Y' WHERE INVNT_TRN_NO='" + tranNo + "' AND INVNT_TRN_SR_NO='" + trnSrNo + "'");
            int var = deleteInvtTran.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Record not deleted in inventory transfer !");
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (ApplicationException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List getAccountNatureList() throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT DISTINCT(ACCTNATURE) FROM accounttypemaster WHERE ACCTNATURE IN('" + CbsConstant.SAVING_AC + "','" + CbsConstant.CURRENT_AC + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String SaveupdateRecordATM(String update, String atmIdentifier, String atmBranch, String atmCashHead,
            String atmStatus, String city, String address, String userName, String todayDate, String atmName,
            String state, String locationType, String site) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        try {
            ut.begin();
            if (update.equalsIgnoreCase("Save")) {
                List list = em.createNativeQuery("select atm_branch from atm_master where atm_id='" + atmIdentifier + "'").getResultList();
                if (!list.isEmpty()) {
                    throw new ApplicationException("This identifier is already exists.");
                }
                int insertResult = em.createNativeQuery("insert into atm_master(atm_id,atm_branch,atm_cash_general_head,"
                        + "atm_active_status,atm_city,atm_location,atm_added_by,atm_addition_date,atm_name,atm_state,"
                        + "location_type,atm_site) values('" + atmIdentifier + "','" + atmBranch + "',"
                        + "'" + atmCashHead + "','" + atmStatus + "','" + city + "','" + address + "',"
                        + "'" + userName + "',now(),'" + atmName + "','" + state + "','" + locationType + "',"
                        + "'" + site + "')").executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Insertion problem in ATM MAster.");
                }
            } else if (update.equalsIgnoreCase("Update")) {
                int exeResult = em.createNativeQuery("insert into atm_master_history(atm_id,atm_branch,"
                        + "atm_cash_general_head,atm_active_status,atm_city,atm_location,atm_added_by,"
                        + "atm_addition_date,atm_modified_by,atm_modification_date,atm_name,atm_state,"
                        + "location_type,atm_site) select atm_id,atm_branch,atm_cash_general_head,atm_active_status,"
                        + "atm_city,atm_location,atm_added_by,atm_addition_date,atm_modified_by,atm_modification_date,"
                        + "atm_name,atm_state,location_type,atm_site from atm_master where atm_id='" + atmIdentifier + "'").executeUpdate();
                if (exeResult <= 0) {
                    throw new ApplicationException("Insertion problem in ATM MAster History.");
                }
                exeResult = em.createNativeQuery("update atm_master set atm_branch ='" + atmBranch + "',"
                        + "atm_cash_general_head='" + atmCashHead + "',atm_active_status='" + atmStatus + "',"
                        + "atm_city='" + city + "',atm_location='" + address + "',atm_modified_by='" + userName + "',"
                        + "atm_modification_date=now(),atm_name='" + atmName + "',atm_state='" + state + "',"
                        + "location_type='" + locationType + "',atm_site='" + site + "' where "
                        + "atm_id ='" + atmIdentifier + "'").executeUpdate();
                if (exeResult <= 0) {
                    throw new ApplicationException("Update problem in ATM MAster.");
                }
            }
            result = "true";
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return result;
    }

    public List gridDetailATMMaster() throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select ATM_ID,ATM_NAME,ATM_BRANCH,ATM_CASH_GENERAL_HEAD,ATM_ACTIVE_STATUS,"
                    + "ATM_LOCATION,ATM_CITY,ATM_STATE,LOCATION_TYPE,ATM_SITE,ATM_ADDED_BY,"
                    + "date_format(ATM_ADDITION_DATE,'%d/%m/%Y') from atm_master").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getBranchName(String brcode) throws ApplicationException {
        try {
            String brncode = "";
            List brcodeList = em.createNativeQuery("select BranchName from branchmaster WHERE BRNCODE = '" + brcode + "'").getResultList();
            if (!brcodeList.isEmpty()) {
                Vector ele1 = (Vector) brcodeList.get(0);
                brncode = ele1.get(0).toString();
            }
            return brncode;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getPoDetails(String flag, String seqNoInstNo, String issueDt, String orgBrCode) throws ApplicationException {
        List<PoSearchPojo> returnList = new ArrayList<PoSearchPojo>();
        try {
            List resultList;
            if (flag.equals("1")) {
                resultList = em.createNativeQuery("select fyear,seqno,instno,custname,date_format(dt,'%d/%m/%Y'),date_format(origindt,'%d/%m/%Y'),"
                        + "status,infavourof from bill_po where seqno='" + seqNoInstNo + "' and substring(acno,1,2) = '" + orgBrCode + "'").getResultList();
            } else if (flag.equals("2")) {
                resultList = em.createNativeQuery("select fyear,seqno,instno,custname,date_format(dt,'%d/%m/%Y'),date_format(origindt,'%d/%m/%Y'),"
                        + "status,infavourof from bill_po where instno ='" + seqNoInstNo + "' and substring(acno,1,2) = '" + orgBrCode + "'").getResultList();
            } else {
                resultList = em.createNativeQuery("select fyear,seqno,instno,custname,date_format(dt,'%d/%m/%Y'),date_format(origindt,'%d/%m/%Y'),"
                        + "Status,infavourof from bill_po where origindt ='" + issueDt + "' and substring(acno,1,2) = '" + orgBrCode + "'").getResultList();
            }
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            PoSearchPojo pojo;
            for (int j = 0; j < resultList.size(); j++) {
                pojo = new PoSearchPojo();
                Vector resVect = (Vector) resultList.get(j);
                pojo.setfYear(resVect.get(0).toString());
                pojo.setSeqNo(resVect.get(1).toString());
                pojo.setInstNo(resVect.get(2).toString());
                pojo.setCustName(resVect.get(3).toString());
                pojo.setDt(resVect.get(4).toString());
                pojo.setOriginDt(resVect.get(5).toString());
                pojo.setStatus(resVect.get(6).toString().toUpperCase());
                pojo.setInFavauroff(resVect.get(7).toString());
                returnList.add(pojo);
            }
            return returnList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String saveBiometricDeviceRecord(String function, String deviceSrlNo, String deviceModel, String deviceMake, String enterBy, String branch) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            if (function.equalsIgnoreCase("A")) {

                List list = em.createNativeQuery("select * from biometric_device_inventory where device_srl_no = '" + deviceSrlNo + "'").getResultList();
                if (!list.isEmpty()) {
                    throw new ApplicationException("Data Already exist in this Srl No. !");
                }
                int result = em.createNativeQuery("INSERT INTO biometric_device_inventory (device_srl_no, device_model, device_make, enter_by,"
                        + " enter_date, device_flag,branch) "
                        + "VALUES ('" + deviceSrlNo + "', '" + deviceModel + "', '" + deviceMake + "', '" + enterBy + "',now(),'" + function + "','DL')").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In Table Insertion");
                }
            } else if (function.equalsIgnoreCase("U")) {
                int result = em.createNativeQuery("UPDATE biometric_device_inventory SET device_model = '" + deviceModel + "', device_make = '" + deviceMake + "',device_flag = '" + function + "', device_update_dt = now(), device_updateby = '" + enterBy + "' WHERE device_srl_no = '" + deviceSrlNo + "'").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In Table Updation");
                }
            } else if (function.equalsIgnoreCase("D")) {
                int result = em.createNativeQuery("UPDATE biometric_device_inventory SET device_flag = '" + function + "', device_update_dt = now(), device_updateby = '" + enterBy + "' WHERE device_srl_no = '" + deviceSrlNo + "'").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In Table Deletion");
                }
            } else if (function.equalsIgnoreCase("M")) {
                int result = em.createNativeQuery("UPDATE biometric_device_inventory SET branch='" + branch + "',device_flag = '" + function + "', device_update_dt = now(), device_updateby = '" + enterBy + "' WHERE device_srl_no = '" + deviceSrlNo + "'").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In Table Move Out");
                }
            }
            ut.commit();

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "true";
    }

    public List gridBiometricData(String deviceSrlNo) throws ApplicationException {
        try {
            if (deviceSrlNo.equalsIgnoreCase("")) {
                return em.createNativeQuery("select device_srl_no,device_model,device_make,enter_by,date_format(enter_date,'%d/%m/%Y'),device_flag,branch from biometric_device_inventory").getResultList();
            } else {
                return em.createNativeQuery("select device_srl_no,device_model,device_make,enter_by,date_format(enter_date,'%d/%m/%Y'),device_flag,branch from biometric_device_inventory where device_srl_no = '" + deviceSrlNo + "'").getResultList();
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List getBusinessDetails(String custId) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select n.bankname,ifnull(b.ifsc_code,'') as Ifsc_Code,i.acno,c.customerid,"
                    + "ifnull(pan_girnumber,'') as pan,ifnull(gstidentificationNumber,'') as gstin,"
                    + "ifnull(c.custfullname,'') as CustName from cbs_customer_master_detail c,customerid i,accountmaster a,"
                    + "branchmaster b,bnkadd n where cast(c.customerid as unsigned)=i.custid and i.acno=a.acno and "
                    + "substring(a.acno,3,2) in(select acctcode from accounttypemaster where acctnature='CA' "
                    + "and accttype='CA') and a.accstatus=1 and cast(c.primarybrcode as unsigned)=b.brncode and "
                    + "b.alphacode=n.alphacode and c.customerid='" + custId + "'").getResultList();
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public int addCustomerBusinessData(String custId, String bankName, String bankIfsc, String bankAccoNo, String pan,
            String businessGst, String companyName, String AccountName, String firstName, String lastName, String email,
            String mobile, String username, String businessCate, String businessType, String flag) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        int result = 0;
        try {
            ut.begin();
            List list = em.createNativeQuery("select company_name from cbs_airpay_detail where customerid='" + custId + "'").getResultList();
            if (!list.isEmpty()) {
                throw new Exception("This is duplicate entry");
            }

            result = em.createNativeQuery("insert into cbs_airpay_detail values('" + custId + "', '" + companyName + "','','"
                    + firstName + "','" + lastName + "','" + email + "','" + mobile + "','','" + businessCate + "','" + businessType
                    + "','" + bankName + "', '" + bankIfsc + "','" + bankAccoNo + "','" + (firstName + " " + lastName) + "','" + pan
                    + "','" + businessGst + "','" + flag + "','" + username + "', now(),'" + username + "',now());").executeUpdate();
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (ApplicationException e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return result;
    }

    @Override
    public int updateCustomerById(String custId, String bankName, String bankIfsc, String bankAccoNo, String pan,
            String businessGst, String companyName, String AccountName, String firstName, String lastName,
            String email, String mobile, String username, String businessCate, String businessType, String flag) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        int result = 0;
        try {
            ut.begin();
            result = em.createNativeQuery("insert into cbs_airpay_detail_history(customerid,company_name,reference_id,f_name,"
                    + "l_name,e_mail,mobile,checksum,business_cat_id,business_type_id,bank_name,bank_ifsc,"
                    + "bank_account_number,bank_account_holder_name,business_pan_no,business_gstin,enter_by,"
                    + "enter_time,last_update_by,last_update_time,flag) select customerid,company_name,reference_id,"
                    + "f_name,l_name,e_mail,mobile,checksum,business_cat_id,business_type_id,bank_name,bank_ifsc,"
                    + "bank_account_number,bank_account_holder_name,business_pan_no,business_gstin,enter_by,"
                    + "enter_time,last_update_by,last_update_time,flag from cbs_airpay_detail "
                    + "where customerid='" + custId + "'").executeUpdate();
            if (result <= 0) {
                throw new Exception("Problem In History Updation.");
            }

            result = em.createNativeQuery("update cbs_airpay_detail set company_name= '" + companyName + "',"
                    + "f_name = '" + firstName + "',l_name = '" + lastName + "',e_mail = '" + email + "',"
                    + "mobile = '" + mobile + "',business_cat_id = '" + businessCate + "',business_type_id = '" + businessType + "',"
                    + "bank_name = '" + bankName + "',bank_ifsc = '" + bankIfsc + "',bank_account_number = '" + bankAccoNo + "',"
                    + "bank_account_holder_name = '" + (firstName + " " + lastName).trim() + "',business_pan_no = '" + pan + "',"
                    + "business_gstin = '" + businessGst + "',flag = '" + flag + "',last_update_by = '" + username + "',"
                    + "last_update_time = now() where customerId = '" + custId + "'").executeUpdate();
            if (result <= 0) {
                throw new Exception("Problem In Data Updation.");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return result;
    }

    @Override
    public List getAddAndModifyData() {
        return em.createNativeQuery("SELECT a.*,r1.ref_desc as cat_id,r2.ref_desc as business_type FROM "
                + "cbs_airpay_detail a,cbs_ref_rec_type r1,cbs_ref_rec_type r2 WHERE a.flag in('A','M') and "
                + "r1.ref_rec_no='450' and r2.ref_rec_no='451' and r1.ref_code=a.business_cat_id and "
                + "r2.ref_code=a.business_type_id").getResultList();
    }

    @Override
    public int verfiyCustomerBusinessData(String custId, String flag, String userName) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        int result = 0;
        try {
            ut.begin();
            List list = em.createNativeQuery("select code from cbs_parameterinfo where name in('AirPay-Id')").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define AirPay-Id");
            }
            Vector ele = (Vector) list.get(0);
            String airPayId = ele.get(0).toString().trim();

            list = em.createNativeQuery("select company_name,reference_id,f_name,l_name,e_mail,mobile,business_cat_id,"
                    + "business_type_id,bank_name,bank_ifsc,bank_account_number,bank_account_holder_name,"
                    + "business_pan_no,business_gstin,last_update_by from cbs_airpay_detail where customerid='" + custId + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data to verify.");
            }
            ele = (Vector) list.get(0);
            if (ele.get(14).toString().equalsIgnoreCase(userName)) {
                throw new Exception("You can not verify your own entry.");
            }

            //Sending the request to AirPay
            HttpClient httpclient = new DefaultHttpClient();
            BufferedReader rd = null;
            HttpPost httpPost = new HttpPost("https://leads.airpay.co.in/api/create_lead.php"); //For Live
//            HttpPost httpPost = new HttpPost("http://localhost:8081/axisneftbridge/rest/airpaytest"); //For Local Testing
//            HttpPost httpPost = new HttpPost("https://airpay.co.in/development_leads/api/create_lead.php"); //For Airpay Testing
            List<NameValuePair> nameValuePairs = new ArrayList<>();

            httpPost.addHeader("AirpayKey", "03d3de7823b4f3f906364857e2106163");  //For Live
//            httpPost.addHeader("AirpayKey", "3048e83d4839113d080ada3eb6e6f18c");    //For Airpay Testing
            nameValuePairs.add(new BasicNameValuePair("appId", airPayId));
            nameValuePairs.add(new BasicNameValuePair("companyName", ele.get(0).toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("referenceId", ele.get(1).toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("fname", ele.get(2).toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("lname", ele.get(3).toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("email", ele.get(4).toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("mobile", ele.get(5).toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("Checksum", "717b785c41a3f85c9222f9cc4def4484"));   //For Live
//            nameValuePairs.add(new BasicNameValuePair("Checksum", "717b785c41a3f85c9222f9cc4def4484"));     //For Airpay Testing
            nameValuePairs.add(new BasicNameValuePair("businessCatId", ele.get(6).toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("businessTypeId", ele.get(7).toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("bankName", ele.get(8).toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("bankifsc", ele.get(9).toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("bankaccountNumber", ele.get(10).toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("bankaccountHolderName", ele.get(11).toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("businesspanno", ele.get(12).toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("businessgstin", ele.get(13).toString().trim()));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httpPost);
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder airPayResponse = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                System.out.println("AirPay Response Is " + line);
                airPayResponse.append(line);
            }

            ObjectMapper mapper = new ObjectMapper();
            AirPayResponse owResponse = mapper.readValue(airPayResponse.toString(), AirPayResponse.class); //Might be change here
            httpclient.getConnectionManager().shutdown();
            if (!owResponse.getStatus().trim().equalsIgnoreCase("200")) {
                throw new Exception(owResponse.getError().toString());
            }
            result = em.createNativeQuery("update cbs_airpay_detail set flag='" + flag + "',last_update_by = '" + userName + "',"
                    + "last_update_time = now() where customerId = '" + custId + "'").executeUpdate();
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return result;
    }
}