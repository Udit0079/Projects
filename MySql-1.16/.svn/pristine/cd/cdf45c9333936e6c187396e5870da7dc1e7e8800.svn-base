package com.cbs.facade.master;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.BucketParameterGrid;
import com.cbs.pojo.GstReportPojo;
import com.cbs.pojo.MinorToMajorPojo;
import com.cbs.pojo.NpciInwardNonaadhaarPojo;
import com.cbs.pojo.OnlinePigmeInfoPojo;
import com.cbs.pojo.ThresoldTransactionInfoPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "OtherMasterFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class OtherMasterFacade implements OtherMasterFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting;
    @EJB
    private CommonReportMethodsRemote commonRemote;
    @EJB
    private TermDepositMasterFacadeRemote tdRemote;
    NumberFormat formatter = new DecimalFormat("#");
    NumberFormat formatterdec = new DecimalFormat("#.##");
    SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat txnIdDateFormat = new SimpleDateFormat("yyyyMMddhhmmssms");
    SimpleDateFormat dmyOne = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdone = new SimpleDateFormat("yyyy-MM-dd");

    public List headOfAccountsOutFlow() throws ApplicationException {
        try {
            return em.createNativeQuery("select code,description from codebook where groupcode=95").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List headOfAccountsInFlow() throws ApplicationException {
        try {
            return em.createNativeQuery("select code,description from codebook where groupcode=96").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List bucketDescCombo() throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT BUCKET_NO,BUCKET_DESC FROM cbs_alm_bucket_master where RECORD_STATUS='A'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String headAcDescForOF(String code) throws ApplicationException {
        try {
            String desc = null;
            Query selectQuery = em.createNativeQuery("select description from codebook where code =" + code + " and groupcode=95");
            List chk1 = selectQuery.getResultList();
            if (!chk1.isEmpty()) {
                Vector recLst = (Vector) chk1.get(0);
                desc = recLst.get(0).toString();
            }
            return desc;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String headAcDescForIF(String code) throws ApplicationException {
        try {
            String desc = "";
            Query selectQuery = em.createNativeQuery("select description from codebook where code =" + code + " and groupcode=96");
            List chk1 = selectQuery.getResultList();
            if (!chk1.isEmpty()) {
                Vector recLst = (Vector) chk1.get(0);
                desc = recLst.get(0).toString();
            }
            return desc;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List almActClassGridLoad() throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT FLOW,HEADS_OF_ACC_NO,BUCKET_NO,PERCENT_AMT,REMARKS FROM cbs_alm_acc_class").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String bucketDescriptionGrid(String bucketNo) throws ApplicationException {
        try {
            String desc = null;
            Query selectQuery = em.createNativeQuery("SELECT BUCKET_DESC FROM cbs_alm_bucket_master WHERE BUCKET_NO=" + bucketNo + " AND RECORD_STATUS='A'");
            List chk1 = selectQuery.getResultList();
            if (!chk1.isEmpty()) {
                Vector recLst = (Vector) chk1.get(0);
                desc = recLst.get(0).toString();
            }
            return desc;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String bucketDescriptionFromGrid(String bucketdesc) throws ApplicationException {
        try {
            String desc = null;
            Query selectQuery = em.createNativeQuery("SELECT BUCKET_NO FROM cbs_alm_bucket_master WHERE BUCKET_DESC='" + bucketdesc + "' AND RECORD_STATUS='A'");
            List chk1 = selectQuery.getResultList();
            if (!chk1.isEmpty()) {
                Vector recLst = (Vector) chk1.get(0);
                desc = recLst.get(0).toString();
            }
            return desc;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String hoDescriptionFromGrid(String flow, String description) throws ApplicationException {
        try {
            String desc = null;
            List chk1 = null;
            if (flow.equalsIgnoreCase("OUTFLOW")) {
                Query selectQuery = em.createNativeQuery("SELECT CODE FROM codebook WHERE DESCRIPTION='" + description + "' AND GROUPCODE=95");
                chk1 = selectQuery.getResultList();
            } else {
                Query selectQuery = em.createNativeQuery("SELECT CODE FROM codebook WHERE DESCRIPTION='" + description + "' AND GROUPCODE=96");
                chk1 = selectQuery.getResultList();
            }

            if (!chk1.isEmpty()) {
                Vector recLst = (Vector) chk1.get(0);
                desc = recLst.get(0).toString();
            }
            return desc;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveALMActClassification(List list, String enterBy, String dt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String message = "";
            int var = 0;
            if ((dt == null) || (enterBy == null) || (list.isEmpty()) || (list == null)) {
                message = "DATA COULD NOT BE NULL.";
                return message;
            }
            for (int a = 0, b = 1, c = 2, d = 3, e = 4; a < list.size(); a = a + 5, b = b + 5, c = c + 5, d = d + 5, e = e + 5) {
                List chk1 = null;
                Integer code = null;
                Integer bucketNo = null;
                if (list.get(a).equals("OUTFLOW")) {
                    chk1 = em.createNativeQuery("SELECT CODE FROM codebook WHERE DESCRIPTION='" + list.get(b) + "' AND GROUPCODE=95").getResultList();
                } else {
                    chk1 = em.createNativeQuery("SELECT CODE FROM codebook WHERE DESCRIPTION='" + list.get(b) + "' AND GROUPCODE=96").getResultList();
                }
                if (!chk1.isEmpty()) {
                    Vector recLst = (Vector) chk1.get(0);
                    code = Integer.parseInt(recLst.get(0).toString());
                }
                List chk2 = em.createNativeQuery("SELECT BUCKET_NO FROM cbs_alm_bucket_master WHERE BUCKET_DESC='" + list.get(c) + "' AND RECORD_STATUS='A'").getResultList();
                if (!chk2.isEmpty()) {
                    Vector recLst1 = (Vector) chk2.get(0);
                    bucketNo = Integer.parseInt(recLst1.get(0).toString());
                }
                List checkList = em.createNativeQuery("select * from cbs_alm_acc_class where heads_of_acc_no=" + code + " and bucket_no=" + bucketNo + "").getResultList();
                if (!checkList.isEmpty()) {
                    ut.rollback();
                    return message = "This bucket is already exists.";
                }
                Query insertQuery = em.createNativeQuery("insert into cbs_alm_acc_class "
                        + " (flow,heads_of_acc_no,bucket_no,percent_amt,remarks,enter_by,enter_dt)"
                        + " values ('" + list.get(a).toString().substring(0, 1) + "'," + code + "," + bucketNo + "," + Double.parseDouble(list.get(d).toString()) + ",'" + list.get(e) + "','" + enterBy + "','" + dt.substring(6) + dt.substring(3, 5) + dt.substring(0, 2) + "')");
                var = insertQuery.executeUpdate();
            }
            if ((var > 0)) {
                ut.commit();
                message = "true";
                return message;
            } else {
                ut.rollback();
                message = "DATA COULD NOT BE SAVED !!!";
                return message;
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

    public String updateALMActClassification(String modBy, String modDt, String flowOld, String hoActOld, String bucketNoOld, Float amtOld, String remarksOld, String flowNew, String hoActNew, String bucketNoNew, Float amtNew, String remarksNew) throws ApplicationException {
        String message = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0, var1 = 0, var2 = 0;
            if ((modBy == null) || (modDt == null) || (flowOld == null) || (hoActOld == null) || (bucketNoOld == null) || (amtOld == null) || (remarksOld == null) || (flowNew == null) || (hoActNew == null) || (bucketNoNew == null) || (amtNew == null) || (remarksNew == null)) {
                ut.rollback();
                message = "DATA COULD NOT BE NULL.";
                return message;
            }
            List chk1 = null;
            Integer code = null;
            Integer bucketNo = null;
            if (flowOld.equals("O")) {
                chk1 = em.createNativeQuery("SELECT CODE FROM codebook WHERE DESCRIPTION='" + hoActOld + "' AND GROUPCODE=95").getResultList();
            } else {
                chk1 = em.createNativeQuery("SELECT CODE FROM codebook WHERE DESCRIPTION='" + hoActOld + "' AND GROUPCODE=96").getResultList();
            }
            if (!chk1.isEmpty()) {
                Vector recLst = (Vector) chk1.get(0);
                code = Integer.parseInt(recLst.get(0).toString());
            }
            List chk2 = em.createNativeQuery("SELECT BUCKET_NO FROM cbs_alm_bucket_master WHERE BUCKET_DESC='" + bucketNoOld + "' AND RECORD_STATUS='A'").getResultList();
            if (!chk2.isEmpty()) {
                Vector recLst1 = (Vector) chk2.get(0);
                bucketNo = Integer.parseInt(recLst1.get(0).toString());
            }
            Query insertQuery = em.createNativeQuery("INSERT INTO cbs_alm_acc_class_his(flow,heads_of_acc_no,bucket_no,percent_amt,remarks,enter_by,enter_dt,last_mod_by,last_mod_dt,profile_parameter) SELECT * FROM cbs_alm_acc_class WHERE FLOW='" + flowOld + "' AND HEADS_OF_ACC_NO=" + code + " AND BUCKET_NO=" + bucketNo + " AND PERCENT_AMT=" + amtOld + " AND REMARKS='" + remarksOld + "'");
            var = insertQuery.executeUpdate();
            Query updateQuery = em.createNativeQuery("UPDATE cbs_alm_acc_class_his SET LAST_MOD_BY='" + modBy + "',LAST_MOD_DT='" + modDt.substring(6) + modDt.substring(3, 5) + modDt.substring(0, 2) + "' WHERE FLOW='" + flowOld + "' AND HEADS_OF_ACC_NO=" + code + " AND BUCKET_NO=" + bucketNo + " AND PERCENT_AMT=" + amtOld + " AND REMARKS='" + remarksOld + "'");
            var1 = updateQuery.executeUpdate();
            Query updateQuery1 = em.createNativeQuery("UPDATE cbs_alm_acc_class SET LAST_MOD_BY='" + modBy + "',LAST_MOD_DT='" + modDt.substring(6) + modDt.substring(3, 5) + modDt.substring(0, 2) + "',FLOW='" + flowNew + "',HEADS_OF_ACC_NO=" + hoActNew + ",BUCKET_NO=" + bucketNoNew + ",PERCENT_AMT=" + amtNew + ",REMARKS='" + remarksNew + "' WHERE FLOW='" + flowOld + "' AND HEADS_OF_ACC_NO=" + code + " AND BUCKET_NO=" + bucketNo + " AND PERCENT_AMT=" + amtOld + " AND REMARKS='" + remarksOld + "'");
            var2 = updateQuery1.executeUpdate();
            if ((var > 0) || (var1 > 0) || (var2 > 0)) {
                ut.commit();
                message = "true";
                return message;
            } else {
                ut.rollback();
                message = "DATA COULD NOT BE UPDATED !!!";
                return message;
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

    public List bucketGridLoad() throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT BUCKET_NO,BUCKET_DESC,BUCKET_START_DAY,BUCKET_END_DAY,PROFILE_PARAMETER from cbs_alm_bucket_master WHERE RECORD_STATUS='A'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveBucketParameter(List<BucketParameterGrid> list, String dt, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int versionNo = 0;
            if (dt == null || list == null || enterBy == null || list.isEmpty() || dt.equals("") || enterBy.equals("")) {
                throw new ApplicationException("Data could not be null.");
            }
            List fetchList = em.createNativeQuery("SELECT * FROM cbs_alm_bucket_master").getResultList();
            if (fetchList.isEmpty()) {
                versionNo = 1;
            } else {
                fetchList = em.createNativeQuery("select max(version_no) from cbs_alm_bucket_master").getResultList();
                Vector ele = (Vector) fetchList.get(0);
                versionNo = Integer.parseInt(ele.get(0).toString()) + 1;
                Query updateQuery = em.createNativeQuery("update cbs_alm_bucket_master set record_status='I' where record_status='A'");
                int updateResult = updateQuery.executeUpdate();
                if (updateResult <= 0) {
                    throw new ApplicationException("Problem in cbs_alm_bucket_master updation.");
                }
            }
            for (int i = 0; i < list.size(); i++) {
                BucketParameterGrid pojo = list.get(i);
                fetchList = em.createNativeQuery("select * from cbs_alm_bucket_master where version_no=" + versionNo + " and bucket_no=" + Integer.parseInt(pojo.getBucketNo()) + " and profile_parameter='" + pojo.getProfileParameter() + "'").getResultList();
                if (!fetchList.isEmpty()) {
                    throw new ApplicationException("Data already exists: Version No->" + versionNo + " :Bucket No->" + pojo.getBucketNo() + " :Parameter->" + pojo.getProfileParameter());
                }
                Query insertQuery = em.createNativeQuery("insert into cbs_alm_bucket_master "
                        + " (version_no,record_status,bucket_no,bucket_desc,bucket_start_day,bucket_end_day,record_mod_cnt,enter_by,enter_dt,profile_parameter)"
                        + " values (" + versionNo + ",'A'," + Integer.parseInt(pojo.getBucketNo()) + ",'" + pojo.getBucketDesc() + "'," + Integer.parseInt(pojo.getStartDay()) + "," + Integer.parseInt(pojo.getEndDay()) + ",0,'" + enterBy + "','" + dt + "','" + pojo.getProfileParameter() + "')");
                int insertResult = insertQuery.executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Problem in data insertion in cbs_alm_bucket_master.");
                }
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String updateBucketParameter(String modDt, String updateBy, Integer oldBucketNo, Integer bucketNo, String bucketDesc, Integer startDay, Integer endDay, String parameter) throws ApplicationException {
        String message = "";
        UserTransaction ut = context.getUserTransaction();
        Integer versionNo = null;
        try {
            ut.begin();
            if ((modDt == null) || (updateBy == null) || (bucketDesc == null) || (oldBucketNo == null) || (bucketNo == null)
                    || (startDay == null) || (endDay == null)) {
                ut.rollback();
                message = "DATA COULD NOT BE NULL.";
                return message;
            }
            int var1 = 0, var2 = 0, var3 = 0;
            List chk1 = em.createNativeQuery("Select version_no from cbs_alm_bucket_master where record_status ='A'").getResultList();
            Vector recLst = (Vector) chk1.get(0);
            versionNo = Integer.parseInt(recLst.get(0).toString());
            if (oldBucketNo != bucketNo) {
                List chk2 = em.createNativeQuery("Select bucket_no from cbs_alm_bucket_master where version_no=" + versionNo
                        + " and record_status ='A'").getResultList();
                if (!chk2.isEmpty()) {
                    for (int i = 0; i < chk2.size(); i++) {
                        Vector recLst1 = (Vector) chk2.get(i);
                        if (Integer.parseInt(recLst1.get(0).toString()) == bucketNo) {
                            ut.rollback();
                            message = "SORRY ,THIS BUCKET NO IS ALREADY EXISTS , SELECT ANOTHER BUCKET NO. !!!";
                            return message;
                        }
                    }
                }
            }
            Integer recordModCnt = null;
            List chk3 = em.createNativeQuery("Select ifnull(max(record_mod_cnt),0) from cbs_alm_bucket_master_his where version_no=" + versionNo
                    + " and bucket_No=" + oldBucketNo + "").getResultList();
            if (!chk3.isEmpty()) {
                Vector recLst2 = (Vector) chk3.get(0);
                recordModCnt = Integer.parseInt(recLst2.get(0).toString()) + 1;
            }
            Query insertQuery = em.createNativeQuery("Insert Into cbs_alm_bucket_master_his(version_no,record_status,bucket_no,bucket_desc,bucket_start_day,bucket_end_day,record_mod_cnt,enter_by,enter_dt,last_mod_by,last_mod_dt,profile_parameter)"
                    + " select version_no,record_status,bucket_no,bucket_desc,bucket_start_day,bucket_end_day," + recordModCnt + " as record_mod_cnt,enter_by,enter_dt,last_mod_by,last_mod_dt,profile_parameter from cbs_alm_bucket_master where version_no="
                    + versionNo + " and bucket_No=" + oldBucketNo + " and profile_parameter ='" + parameter + "'");
            var1 = insertQuery.executeUpdate();
            Query updateQuery1 = em.createNativeQuery("update cbs_alm_bucket_master set bucket_no=" + bucketNo + " ,bucket_desc='" + bucketDesc
                    + "' ,bucket_Start_day=" + startDay + ",bucket_end_day=" + endDay + " ,last_mod_by='" + updateBy + "' ,last_mod_dt='"
                    + modDt.substring(6) + modDt.substring(3, 5) + modDt.substring(0, 2) + "',record_mod_cnt=" + recordModCnt + ",profile_parameter='" + parameter + "' where version_no="
                    + versionNo + " and bucket_no=" + oldBucketNo + " and profile_parameter ='" + parameter + "'");
            var3 = updateQuery1.executeUpdate();
            if ((var1 > 0) || (var3 > 0)) {
                ut.commit();
                message = "true";
                return message;
            } else {
                ut.rollback();
                message = "DATA COULD NOT BE UPDATED !!!";
                return message;
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

    public List loadTable() throws ApplicationException {
        try {
            return em.createNativeQuery("select des,date_format(Applicabledt,'%d/%m/%Y'),glhead,rot,rotapplyon,minamt,maxamt,applicableflag  from taxmaster ORDER BY APPLICABLEDT").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List loadValue() throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT TYPE,ROTAPPLYON,APPLICABLEDT,GLHEAD,ROT,COMMFLAG,MINAMT,MAXAMT,(SELECT T2.DES FROM taxmaster T2 WHERE T2.TYPE=T1.ROTAPPLYON GROUP BY T2.DES) APPON FROM taxmaster T1 WHERE DES='SERVICE TAX AND EDUCATION CESS' AND APPLICABLEFLAG='Y'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List taxNature(String acNo) throws ApplicationException {
        try {
            Query q2 = em.createNativeQuery("SELECT DISTINCT ACNAME FROM gltable WHERE SUBSTRING(ACNO,5,6)='" + acNo + "'");
            List result = q2.getResultList();
            if (result.isEmpty()) {
                result = null;

            }
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List loadApply(String st1) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT DES FROM taxmaster WHERE DES<>'" + st1 + "' AND APPLICABLEFLAG='Y' AND ROTAPPLYON NOT IN (SELECT TYPE FROM taxmaster WHERE DES='" + st1 + "' AND APPLICABLEFLAG='Y')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String update(String apf) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
//            Integer int2 = 0;
//            Query q2 = em.createNativeQuery("UPDATE taxmaster SET APPLICABLEFLAG='N' WHERE ROTAPPLYON IN (SELECT TYPE FROM taxmaster WHERE DES='" + apf + "' AND APPLICABLEFLAG='Y')");
//            Integer int1 = q2.executeUpdate();
//            if (int1 > 0) {
//                Query q3 = em.createNativeQuery("UPDATE taxmaster SET APPLICABLEFLAG='N' WHERE  DES='" + apf + "' AND APPLICABLEFLAG='Y'");
//                int2 = q3.executeUpdate();
//            }
            Query q3 = em.createNativeQuery("UPDATE taxmaster SET APPLICABLEFLAG='N' WHERE DES='" + apf + "' AND APPLICABLEFLAG='Y'");
            Integer int2 = q3.executeUpdate();
            if (int2 > 0) {
                ut.commit();
                return "transaction successful";
            } else {
                ut.rollback();
                return "Transaction is not Successful";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public List save(String labelTax) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT TYPE FROM taxmaster WHERE DES='" + labelTax + "' AND APPLICABLEFLAG='Y'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List save2() throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT coalesce(MAX(SUBSTRING(TYPE,2,LENGTH(TYPE)-1)),0) FROM taxmaster WHERE APPLICABLEFLAG='Y'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List save3(String cboApplyOn) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT TYPE FROM taxmaster WHERE DES='" + cboApplyOn + "' AND APPLICABLEFLAG='Y'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String update2(String labelTax, String taxType, String date, double rot, String rotAppOn, String glHead, double minTax, double maxTax, String enterBy, String system, String nature, String listItem, String auth) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String message = "";
            date = date.substring(6, 10) + date.substring(3, 5) + date.substring(0, 2);
            Query q4 = em.createNativeQuery("UPDATE taxmaster SET APPLICABLEFLAG='N' WHERE DES='" + labelTax + "'");
            Integer int2 = q4.executeUpdate();
            if (int2 > 0) {
                Query q5 = em.createNativeQuery("INSERT INTO taxmaster(TYPE,DES,APPLICABLEDT,ROT,ROTAPPLYON,GLHEAD,MINAMT,MAXAMT,ENTERBY,authby,COMMFLAG,ROUNDUPTO,AUTH,DT) values ('" + taxType + "','" + labelTax + "','" + date + "'," + rot + ",'" + rotAppOn + "','" + glHead + "'," + minTax + "," + maxTax + ",'" + enterBy + "','" + system + "','" + nature + "','" + listItem + "','" + auth + "',date_format(curdate(),'%Y%m%d'))");
                Integer int3 = q5.executeUpdate();
                if (int3 > 0) {
                    ut.commit();
                    message = "Transaction Successful";
                } else {
                    ut.rollback();
                    message = "Transaction Not Successful";
                }
            } else {
                ut.rollback();
                message = "Transaction is not successfull,It may be due to either absence of tax or the updation has been already done for tax.";
            }
            return message;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }

    }

    public List getData() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct acctNature From accounttypemaster where acctNature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List allReceiptTableData(String scheme, String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("Select Sno,Scheme, BookNo,Series,ReceiptNo,date_format(EntryDt,'%d/%m/%Y') AS EntryDt,Status From td_receiptissue Where brncode='" + brCode + "' and  Status = 'F' and scheme ='" + scheme + "' order by bookno,ReceiptNo").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List backTableData(String scheme, String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("Select Scheme, BookNo,Series,RecFrom,RecTo,Leaf,Sno,date_format(IssueDt,'%d/%m/%Y') As IssueDt From td_receiptmaster Where brncode='" + brCode + "' and  scheme ='" + scheme + "' and (auth is null or auth='N') order by bookno").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String deleteData(float seqNo, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            Query deleteQuery = em.createNativeQuery("Delete from td_receiptmaster Where Sno =" + seqNo + " and brncode='" + brCode + "'");
            var = deleteQuery.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem in data deletion");
            }
            ut.commit();
            return "Data deleted Successfully.";

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

    public String saveData(List issueGrid, String scheme, String txtBookNo, String txtRFrom, String txtRTo, String showTableData, String tmpFlag, String txtNo, String txtSeries, String issueDt, String user, Float seqNumber, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var1 = 0;
            int var2 = 0;
            int var3 = 0;
            int var4 = 0;
            int var5 = 0;
            int var6 = 0;
            float maxSeqNos = 1;
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            List maxSnoLst = em.createNativeQuery("Select (ifnull(max(sno),0)+1) From td_receiptmaster where brncode='" + brCode + "'").getResultList();
            if (!maxSnoLst.isEmpty()) {
                Vector maxSnoLsts = (Vector) maxSnoLst.get(0);
                String maxSeqNo = maxSnoLsts.get(0).toString();
                maxSeqNos = Float.parseFloat(maxSeqNo);
            }
            if ((showTableData.equals("ALL RECEIPT")) && (tmpFlag.equals("U"))) {
                for (int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6; a < issueGrid.size(); a = a + 7, b = b + 7, c = c + 7, d = d + 7, e = e + 7, f = f + 7, g = g + 7) {
                    Float sNumber = (Float.parseFloat(issueGrid.get(a).toString()));
                    String status = (issueGrid.get(g).toString());
                    if (status.equals("D")) {
                        Query updateQuery = em.createNativeQuery("Update td_receiptissue Set Status='D' where sno=" + sNumber + " and brncode='" + brCode + "'");
                        var1 = updateQuery.executeUpdate();
                    }
                }
                if (var1 > 0) {
                    ut.commit();
                    return "Data Updated Successfully.";
                }
            }
            float txtFroms = Float.parseFloat(txtRFrom);
            float txtTos = Float.parseFloat(txtRTo);
            if ((showTableData.equals("BACK")) && (tmpFlag.equals("U"))) {
                String from;
                String to;
                if (txtRFrom.contains(".")) {
                    from = txtRFrom.substring(0, txtRFrom.indexOf("."));
                } else {
                    from = txtRFrom;
                }
                if (txtRTo.contains(".")) {
                    to = txtRTo.substring(0, txtRTo.indexOf("."));
                } else {
                    to = txtRTo;
                }
                Query updateQuery = em.createNativeQuery("Update td_receiptmaster Set Scheme='" + scheme + "', Bookno='" + txtBookNo + "', RecFrom=" + txtFroms + ",Recto=" + txtTos + ",leaf=" + txtNo + ",series='" + txtSeries + "',issuedt='" + issueDt + "' Where sno=" + seqNumber + " and brncode='" + brCode + "'");
                var2 = updateQuery.executeUpdate();
                Query updateQuery12 = em.createNativeQuery("Update chbookmaster_stock_other Set IssuedBy=null, IssueDt=null, Status='F' Where chNoFrom=" + Integer.parseInt(from) + " and chNoTo=" + Integer.parseInt(to) + "");
                var3 = updateQuery12.executeUpdate();
                Query updateQuery2 = em.createNativeQuery("Update chbookmaster_stock_other Set IssuedBy='" + user + "',IssueDt='" + Tempbd + "',status='U' Where chNoFrom=" + Integer.parseInt(from) + " and chNoTo=" + Integer.parseInt(to) + "");
                var4 = updateQuery2.executeUpdate();
                if ((var2 > 0) && (var3 > 0) && (var4 > 0)) {
                    ut.commit();
                    return "Data Updated Successfully.";
                } else if ((var2 > 0) && (var3 > 0)) {
                    ut.commit();
                    return "Data Updated Successfully.";
                } else if ((var3 > 0) && (var4 > 0)) {
                    ut.commit();
                    return "Data Updated Successfully.";
                } else if ((var2 > 0) && (var4 > 0)) {
                    ut.commit();
                    return "Data Updated Successfully.";
                }
                if ((var2 > 0)) {
                    ut.commit();
                    return "Data Updated Successfully.";
                } else {
                    ut.rollback();
                    return "Data could not be upDated";
                }
            }
            if (tmpFlag.equals("S")) {
                List holidayDate = em.createNativeQuery("select * from td_receiptmaster where scheme ='" + scheme + "' and series = '" + txtSeries + "' and (" + txtFroms + " between recfrom and recto or " + txtTos + " between recfrom and recto)").getResultList();
                if (!holidayDate.isEmpty()) {
                    ut.rollback();
                    return "This Series Already Issued";
                }
                Query insertQuery5 = em.createNativeQuery("insert into td_receiptmaster(Sno,Scheme,BookNo,Series,RecFrom,RecTo,Leaf,IssueDt,EnterBy,Dt,brncode)"
                        + "values (" + maxSeqNos + "," + "'" + scheme + "'" + "," + "'" + txtBookNo + "'" + "," + "'" + txtSeries + "'" + "," + txtFroms + "," + txtTos + "," + txtNo + "," + "'" + issueDt + "'" + "," + "'" + user + "'" + "," + "'" + Tempbd + "'" + "," + "'" + brCode + "'" + ")");
                var5 = insertQuery5.executeUpdate();

                Query updateQuery6 = em.createNativeQuery("Update chbookmaster_stock_other Set IssuedBy='" + user + "', IssueDt='" + Tempbd + "',Status='U' Where chNoFrom=" + txtRFrom + " and chNoTo=" + txtRTo + "");
                var6 = updateQuery6.executeUpdate();
            }
            if ((var5 > 0)) {
                ut.commit();
                return "Data Saved Successfully.";
            } else if ((var5 > 0) && (var6 > 0)) {
                ut.commit();
                return "Data Saved Successfully.";
            } else {
                ut.rollback();
                return "Data could not be saved";
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

    public String getCode(String scheme) throws ApplicationException {
        String code = null;
        try {
            Query q2 = em.createNativeQuery("select acctcode from accounttypemaster where acctNature = '" + scheme + "'");
            List result = q2.getResultList();
            if (!result.isEmpty()) {
                Vector Vecq2 = (Vector) result.get(0);
                code = Vecq2.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return code;
    }

    public List dateDiffStatementFreqDate(String statementFreqDate) throws ApplicationException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            return em.createNativeQuery("select TIMESTAMPDIFF(DAY,'" + statementFreqDate + "','" + sdf.format(date) + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List actTypeCombo() throws ApplicationException {
        try {
            return em.createNativeQuery("select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.CURRENT_AC + "') order by acctcode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List acDetail(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("Select am.CustName,SanctionLimit,la.ODLimit,MAXLIMIT,am.accstatus from loan_appparameter la, accountmaster am "
                    + "where la.acno = am.acno and am.Acno='" + acno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List addMonth() throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 1 MONTH),'%d/%m/%Y')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List gridLoad(String acno) throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("Select StmNo,Sno,SecurityOption so, Particulars, LienValue lv,Margin m,date_format(ifnull(nextstmdt,''),'%d/%m/%Y'),a.AccStatus ,a.odlimit from loan_stockstm l, accountmaster a where l.acno=a.acno and l.acno='" + acno + "' and Status ='ACTIVE' and security='P' order by sno").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List chkGrid(String nDt) throws ApplicationException {
        try {
            String dt = nDt.substring(6) + nDt.substring(3, 5) + nDt.substring(0, 2);
            /**
             * Only it is changed--- TIMESTAMPDIFF(DAY
             */
            return em.createNativeQuery("select TIMESTAMPDIFF(DAY,'" + dt + "',CURRENT_TIMESTAMP)").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getNetProposedDp(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("Select LienValue lv , Margin m, stmNo from loan_stockstm l, accountmaster a  where l.acno=a.acno and l.acno='" + acno + "'" + " and a.accStatus <>9 and stmno =(Select max(stmNo) from loan_stockstm where acno='" + acno + "' and Status='ACTIVE')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveStockStatement(String acno, String enterBy, List list, String gracePeriod, String stockStatDueDt, String stockStatSubDt, String acStatSubDt, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var1 = 0, var2 = 0, var3 = 0, var4 = 0;
            if ((acno == null) || (enterBy == null) || (gracePeriod == null) || (stockStatDueDt == null) || (stockStatSubDt == null) || (acStatSubDt == null)) {
                ut.rollback();
                return "PLEASE CHECK ALL FIELDS !!!";
            }
            double totVal = 0.0d;
            double totDP = 0.0d;
            double totMarg = 0.0d;
            List dtDiffLt = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY,'" + stockStatDueDt + "','" + stockStatSubDt + "')").getResultList();
            Vector dtDiffLtV = (Vector) dtDiffLt.get(0);
            Integer dayDiff = Integer.parseInt(dtDiffLtV.get(0).toString());
            if (dayDiff > 0) {
                ut.rollback();
                return "STOCK STATEMENT DUE DATE CANNOT BE LESS THAN STOCK STATEMENT SUBMISSION DATE !!!";
            }
            List seqNo = em.createNativeQuery("Select coalesce(max(sno),0)+1 from loansecurity where acno='" + acno + "'").getResultList();
            Vector Lst = (Vector) seqNo.get(0);
            long stmNo = Long.parseLong(Lst.get(0).toString());
            List tempBdList = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brnCode='" + brCode + "'").getResultList();
            Vector Lst1 = (Vector) tempBdList.get(0);
            String tempBd = Lst1.get(0).toString();

            List chk1 = em.createNativeQuery("Select * from loan_stockstm where acno='" + acno + "' and status='ACTIVE'" + " and stmNo < " + stmNo + " and Security='P'").getResultList();
            if (!chk1.isEmpty()) {
                Query updateQuery = em.createNativeQuery("Update loan_stockstm set Status='EXPIRED', ExpiryDate='" + tempBd + "', expiredBy='" + enterBy + "'" + " where acno='" + acno + "' and Status='ACTIVE' and stmno< " + stmNo + " and Security='P'");
                var1 = updateQuery.executeUpdate();
            }
            List chk2 = em.createNativeQuery("Select * from loansecurity where acno='" + acno + "' and status='ACTIVE'" + " and sNo < " + stmNo + " and Security='P'").getResultList();
            if (!chk2.isEmpty()) {
                Query updateQuery = em.createNativeQuery("Update loansecurity set Status='EXPIRED', ExpiryDate='" + tempBd + "', expiredBy='" + enterBy + "'" + " where acno='" + acno + "' and Status='ACTIVE' and sno< " + stmNo + " and Security='P'");
                var2 = updateQuery.executeUpdate();
            }
            String remarks = "";

            List sNoLst = em.createNativeQuery("Select coalesce(max(sno),0)+1 from loan_stockstm where acno='" + acno + "'").getResultList();
            Vector sLst = (Vector) sNoLst.get(0);
            long sNo = Long.parseLong(sLst.get(0).toString());


            for (int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7, i = 8, j = 9, k = 10; a < list.size(); a = a + 12, b = b + 12, c = c + 12, d = d + 12, e = e + 12, f = f + 12, g = g + 12, h = h + 12, i = i + 12, j = j + 12, k = k + 12) {
                String security = list.get(d).toString();
                String desc = list.get(e).toString();
                double value = Double.parseDouble(list.get(f).toString());
                double margin = Double.parseDouble(list.get(g).toString());
                double dppart = Double.parseDouble(list.get(h).toString());
                String statusToday = list.get(k).toString();
                remarks = "HYPO:" + security + ":" + desc;
                if ((!statusToday.equalsIgnoreCase("EXPIRED")) && (!statusToday.equalsIgnoreCase("ACTIVE"))) {
                    Query insertQuery = em.createNativeQuery("Insert into loan_stockstm(Acno,StmNo,sno,Particulars,matvalue,LienValue,Status,Remarks,Enteredby,Entrydate,security,securityoption,securityChg,STMRequired,Margin,STMGracePd,LastSTMDt,NextSTMDt,ReceivedSTMDt,SecurityType,Auth,Authby)"
                            + " values ('" + acno + "'," + stmNo + "," + sNo + ",'" + desc + "'," + value + "," + value + ",'ACTIVE','" + remarks + "','"
                            + enterBy + "','" + tempBd + "','P','" + security + "'," + "'HYPOTHECATION'" + ",'Y'," + margin + "," + Float.parseFloat(gracePeriod)
                            + ",'" + acStatSubDt + "'" + ",'" + stockStatDueDt + "','" + stockStatSubDt + "','NON-DATED','Y','SYSTEM')");
                    var3 = insertQuery.executeUpdate();
                    sNo = sNo + 1;
                    totVal = totVal + value;
                    totDP = totDP + dppart;
                }
//                totVal = totVal + value;
//                totDP = totDP + dppart;
            }
            totMarg = 100 - ((totDP * 100) / totVal);
            Query insertQuery = em.createNativeQuery("Insert into loansecurity(Acno,sno,Particulars,matvalue,LienValue,Status,Remarks,Enteredby,Entrydate,security,securityoption,securityChg,STMRequired,Margin,STMGracePd,LastSTMDt,NextSTMDt,ReceivedSTMDt,SecurityType,Auth,Authby)"
                    + "values ('" + acno + "'," + stmNo + ",'HYPO OF STOCK'," + totVal + "," + totVal + ",'ACTIVE','" + remarks + "','" + enterBy + "','" + tempBd + "','P','STOCK','HYPOTHECATION','Y'," + totMarg + "," + Float.parseFloat(gracePeriod) + ",'" + acStatSubDt + "','" + stockStatDueDt + "','" + stockStatSubDt + "','DATED','N','SYSTEM')");
            var4 = insertQuery.executeUpdate();
            if ((var3 > 0) && (var4 > 0)) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "NOT SAVED PLEASE CHECK SAVE STOCK STATEMENT !!!";
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

    public String setDpLimit(String ACNO, Double lblNewDP) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List checkList = null;
            checkList = em.createNativeQuery("Select ACNO from loan_oldinterest where acno='" + ACNO + "' and authby is null").getResultList();
            if (checkList.size() > 0) {
                ut.rollback();
                return "YOU CAN'T UPDATE DP LIMIT, AUTHORIZATION IS PENDING FOR THIS A/C. !!!";
            }
            Query InsertQuery = em.createNativeQuery("insert loan_oldinterest(Acno,Roi,PenalRoi,acLimit,enterBy,adhocLimit,adhocTillDt,adhocInterest,AdhocApplicableDt,MaxLimit,SanctionLimitDt,enterdate) (Select acno,ROi,penalroi,Odlimit,ifnull(EnterBy,'SYSTEM') ENTERBY,adhocLimit,adhocexpiry,adhocroi,adhocApplicableDt,MaxLimit,SanctionLimitDt,date_format(curdate(),'%Y%m%d') from loan_appparameter where acno='" + ACNO + "')");
            int exec = InsertQuery.executeUpdate();

            Query updateQuery = em.createNativeQuery("update loan_appparameter set odlimit=" + lblNewDP + " where acno='" + ACNO + "'");
            int exec1 = updateQuery.executeUpdate();

            Query updateQuery1 = em.createNativeQuery("update accountmaster set odlimit=" + lblNewDP + " where acno='" + ACNO + "'");
            int exec2 = updateQuery1.executeUpdate();
            if ((exec > 0) && (exec1 > 0) && (exec2 > 0)) {
                ut.commit();
                return "DRAWING POWER UPDATED FOR A/C. NO. : " + ACNO;
            } else {
                ut.rollback();
                return "DRAWING POWER IS NOT UPDATED FOR A/C. NO. : " + ACNO + " !!!";
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

    public List loadTdPeriod() throws ApplicationException {
        try {
            return em.createNativeQuery("select from_days,to_days from td_peroid_temp order by from_days").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveTdPeriodData(String fromDays, String toDays, String enterBy) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q = em.createNativeQuery("INSERT INTO td_peroid_temp(FROM_dAYS,TO_DAYS,enterby,trantime) VALUES(" + fromDays + "," + toDays + ",'" + enterBy + "',now())");
            Integer uu = q.executeUpdate();
            if (uu > 0) {
                ut.commit();
                return "Data Save successfully!!!";
            } else {
                ut.rollback();
                return "Some Problem in Data Save!!!";
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

    public List<MinorToMajorPojo> getMinorToMajorData(String branchCode, String frDt, String toDt) throws ApplicationException {
        List<MinorToMajorPojo> dataList = new ArrayList<MinorToMajorPojo>();
        String branch = "";
        try {
            if (branchCode.equalsIgnoreCase("0A")) {
                branchCode = "90";
                branch = "";
            } else {
                branchCode = branchCode;
                branch = "and primarybrcode = '" + branchCode + "'";
            }
            List list1 = em.createNativeQuery(" select b.CustomerId,b.custfullname,concat(ifnull(b.fathername,''),' ',ifnull(b.FatherMiddleName,''),' ',ifnull(b.FatherLastName,'')) as FatherName,"
                    + "concat(b.perAddressLine1,' ',ifnull(b.perAddressLine2,'')),date_format(MajorityDate,'%d/%m/%Y') MajorDate,primarybrcode  "
                    + "from cbs_cust_minorinfo a,cbs_customer_master_detail b "
                    + "where date_format(MajorityDate,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and guardiancode=''"
                    + "and a.CustomerId = b.CustomerId " + branch + " order by MajorityDate").getResultList();
            if (!list1.isEmpty()) {
                for (int i = 0; i < list1.size(); i++) {
                    MinorToMajorPojo pojo = new MinorToMajorPojo();
                    Vector vtr = (Vector) list1.get(i);
                    int srNo = i + 1;
                    String customerId = vtr.get(0).toString();
                    String custFullName = vtr.get(1).toString();
                    String FatherName = vtr.get(2).toString();
                    String Address = vtr.get(3).toString();
                    String MajorDate = vtr.get(4).toString();
                    pojo.setCustId(customerId);
                    pojo.setCustName(custFullName);
                    pojo.setFatherName(FatherName);
                    pojo.setAddress(Address);
                    pojo.setMajorDt(MajorDate);
                    pojo.setSrNo(srNo);
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<GstReportPojo> getGstrData(String repType, String optionType, String frDt, String toDt, String brnCode, String acno, String gstNo) throws ApplicationException {
        List<GstReportPojo> dataList = new ArrayList<GstReportPojo>();
        String branch = "", brCode = "", acnoQuery = "";
        try {

            if (brnCode.equalsIgnoreCase("0A")) {
                brCode = "90";
                branch = "";
            } else {
                brCode = brnCode;
                branch = "and b.org_brnid = '" + brnCode + "'";
            }

            if (!acno.equalsIgnoreCase("")) {
                acnoQuery = "and b.acno = '" + acno + "'";
            }

            int invoiceNoShow = ftsPosting.getCodeForReportName("GST-REPORT-SHOW");
            Map<String, String> invoiceNoMap = new HashMap<>();
            List invoiceNoList = em.createNativeQuery("select account_no,invoice_no from invoice_no_generate_master where date_format(generate_dt,'%Y%m%d') between '" + frDt + "' and '" + toDt + "'").getResultList();
            for (int i = 0; i < invoiceNoList.size(); i++) {
                Vector ele2 = (Vector) invoiceNoList.get(i);
                invoiceNoMap.put(ele2.get(0).toString(), ele2.get(1).toString());
            }

            Map<String, Double> gstMap = new HashMap<String, Double>();
            List list1 = em.createNativeQuery("SELECT CHARGE_NAME,AMT from cbs_charge_detail where CHARGE_TYPE like '%SERVICE-TAX%'").getResultList();
            for (int i = 0; i < list1.size(); i++) {
                Vector ele1 = (Vector) list1.get(i);
                gstMap.put(ele1.get(0).toString(), Double.parseDouble(ele1.get(1).toString()));
            }
            List list2 = em.createNativeQuery("select State from branchmaster where brncode = " + Integer.parseInt(brCode) + "").getResultList();
            Vector brnVector = (Vector) list2.get(0);
            String branchState = brnVector.get(0).toString();
            List result = new ArrayList();

            if (optionType.equalsIgnoreCase("Summary")) {
                if (repType.equalsIgnoreCase("GSTR-B")) {
                    result = em.createNativeQuery("select b.acno,date_format(max(b.dt),'%Y%m%d'),b.recno,b.trsno,b.txnid,b.TranDesc,sum(b.DrAmt),sum(b.CrAmt),b.Details,CustFullName,gstin,MailStateCode,REF_DESC from ("
                            + "(select b.acno,a.CustFullName,gstin,MailStateCode,REF_DESC from cbs_customer_master_detail a,customerid b,cbs_ref_rec_type c,((select cast(customerid as unsigned) cId,max(updateDt) maxUpdateDt,gstin from ("
                            + "select customerid,ifnull(gstIdentificationNumber,'') gstin,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail "
                            + "where date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + ymd.format(new Date()) + "'and length(trim(gstIdentificationNumber)) =15 "
                            + "union "
                            + "select customerid,ifnull(gstIdentificationNumber,'') gstin,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail_his "
                            + "where date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + toDt + "'and length(gstIdentificationNumber) =15 "
                            + ")a group by cId) d) "
                            + "where cast(a.customerid as unsigned) = d.cId and cast(a.customerid as unsigned) = b.custid and a.MailStateCode = c.REF_CODE and c.ref_rec_no = '002') a,"
                            + "(select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "'"
                            + "group by dt,trsno,recno,acno,txnid "
                            + "union all "
                            + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from ca_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                            + "group by dt,trsno,recno,acno,txnid "
                            + "union all "
                            + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from rdrecon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                            + "group by dt,trsno,recno,acno,txnid "
                            + "union all "
                            + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from td_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                            + "group by dt,trsno,recno,acno,txnid "
                            + "union all "
                            + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from ddstransaction a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                            + "group by dt,trsno,recno,acno,txnid "
                            + "union all "
                            + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from loan_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                            + "group by dt,trsno,recno,acno,txnid) b "
                            + ") where a.acno = b.acno " + branch + " and dt between '" + frDt + "' and '" + toDt + "' group by acno order by 1,2").getResultList();
                } else {
//                
                    result = em.createNativeQuery("select b.acno,date_format(max(b.dt),'%Y%m%d'),b.recno,b.trsno,b.txnid,b.TranDesc,sum(b.DrAmt),sum(b.CrAmt),b.Details,CustFullName,ifnull(gstIdentificationNumber,''),MailStateCode,REF_DESC from ("
                            + "(select b.acno,a.CustFullName,a.gstIdentificationNumber,MailStateCode,REF_DESC from cbs_customer_master_detail a,customerid b,cbs_ref_rec_type c "
                            + "where cast(a.customerid as unsigned) = b.custid and a.MailStateCode = c.REF_CODE and c.ref_rec_no = '002' "
                            + "and date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + ymd.format(new Date()) + "' and length(trim(a.gstIdentificationNumber)) <> 15) a, "
                            + "(select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                            + "group by dt,trsno,recno,acno,txnid "
                            + "union all "
                            + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from ca_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                            + "group by dt,trsno,recno,acno,txnid "
                            + "union all "
                            + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from rdrecon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                            + "group by dt,trsno,recno,acno,txnid "
                            + "union all "
                            + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from td_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                            + "group by dt,trsno,recno,acno,txnid "
                            + "union all "
                            + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from ddstransaction a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                            + "group by dt,trsno,recno,acno,txnid "
                            + "union all "
                            + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from loan_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                            + "group by dt,trsno,recno,acno,txnid) b "
                            + ") where a.acno = b.acno " + branch + " and a.acno not in(select b.acno from cbs_customer_master_detail a,customerid b,(select cast(customerid as unsigned) cId,max(updateDt) maxUpdateDt,gstin from ("
                            + "select customerid,ifnull(gstIdentificationNumber,'') gstin,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail "
                            + "where date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + toDt + "'and length(trim(gstIdentificationNumber)) =15 "
                            + "union "
                            + "select customerid,ifnull(gstIdentificationNumber,'') gstin,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail_his "
                            + "where date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + toDt + "'and length(trim(gstIdentificationNumber)) =15"
                            + ")a group by cId) d "
                            + "where cast(a.customerid as unsigned) = d.cId and cast(a.customerid as unsigned) = b.custid) "
                            + "and dt between '" + frDt + "' and '" + toDt + "' group by acno order by 1,2").getResultList();
                }

            } else {
                if (!optionType.equalsIgnoreCase("Individual Gst")) {
                    if (repType.equalsIgnoreCase("GSTR-B")) {
                        result = em.createNativeQuery("select b.acno,date_format(b.dt,'%Y%m%d'),b.recno,b.trsno,b.txnid,b.TranDesc,b.DrAmt,b.CrAmt,b.Details,CustFullName,gstin,MailStateCode,REF_DESC from ( "
                                + "(select b.acno,a.CustFullName,gstin,MailStateCode,REF_DESC from cbs_customer_master_detail a,customerid b,cbs_ref_rec_type c,((select cast(customerid as unsigned) cId,max(updateDt) maxUpdateDt,gstin from (\n"
                                + "select customerid,ifnull(gstIdentificationNumber,'') gstin,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail\n"
                                + "where date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + ymd.format(new Date()) + "'and length(trim(gstIdentificationNumber)) =15\n"
                                + "union\n"
                                + "select customerid,ifnull(gstIdentificationNumber,'') gstin,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail_his \n"
                                + "where date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + toDt + "'and length(gstIdentificationNumber) =15\n"
                                + ")a group by cId) d)  "
                                + "where cast(a.customerid as unsigned) = d.cId and cast(a.customerid as unsigned) = b.custid and a.MailStateCode = c.REF_CODE and c.ref_rec_no = '002') a, "
                                + "(select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by dt,trsno,recno,acno,txnid "
                                + "union all "
                                + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from ca_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by dt,trsno,recno,acno,txnid "
                                + "union all "
                                + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from rdrecon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by dt,trsno,recno,acno,txnid "
                                + "union all "
                                + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from td_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by dt,trsno,recno,acno,txnid "
                                + "union all "
                                + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from ddstransaction a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by dt,trsno,recno,acno,txnid "
                                + "union all "
                                + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from loan_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by dt,trsno,recno,acno,txnid) b "
                                + ") where a.acno = b.acno " + branch + " " + acnoQuery + " and dt between '" + frDt + "' and '" + toDt + "' order by 1,2").getResultList();
                    } else {
                        result = em.createNativeQuery("select b.acno,date_format(b.dt,'%Y%m%d'),b.recno,b.trsno,b.txnid,b.TranDesc,b.DrAmt,b.CrAmt,b.Details,CustFullName,ifnull(gstIdentificationNumber,''),MailStateCode,REF_DESC from ( "
                                + "(select b.acno,a.CustFullName,a.gstIdentificationNumber,MailStateCode,REF_DESC from cbs_customer_master_detail a,customerid b,cbs_ref_rec_type c  "
                                + "where cast(a.customerid as unsigned) = b.custid and a.MailStateCode = c.REF_CODE and c.ref_rec_no = '002' "
                                + "and date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + ymd.format(new Date()) + "' and length(trim(a.gstIdentificationNumber)) <> 15) a, "
                                + "(select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by dt,trsno,recno,acno,txnid "
                                + "union all "
                                + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from ca_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by dt,trsno,recno,acno,txnid "
                                + "union all "
                                + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from rdrecon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by dt,trsno,recno,acno,txnid "
                                + "union all "
                                + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from td_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by dt,trsno,recno,acno,txnid "
                                + "union all "
                                + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from ddstransaction a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                                + "group by dt,trsno,recno,acno,txnid "
                                + "union all "
                                + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from loan_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "'"
                                + "group by dt,trsno,recno,acno,txnid) b "
                                + ") where a.acno = b.acno " + branch + " " + acnoQuery + " and a.acno not in(select b.acno from cbs_customer_master_detail a,customerid b,(select cast(customerid as unsigned) cId,max(updateDt) maxUpdateDt,gstin from (\n"
                                + "select customerid,ifnull(gstIdentificationNumber,'') gstin,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail\n"
                                + "where date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + toDt + "'and length(trim(gstIdentificationNumber)) =15\n"
                                + "union\n"
                                + "select customerid,ifnull(gstIdentificationNumber,'') gstin,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail_his \n"
                                + "where date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + toDt + "'and length(trim(gstIdentificationNumber)) =15\n"
                                + ")a group by cId) d  \n"
                                + "where cast(a.customerid as unsigned) = d.cId and cast(a.customerid as unsigned) = b.custid) "
                                + "and dt between '" + frDt + "' and '" + toDt + "' order by 1,2").getResultList();
                    }
                }
            }


            double totalTaxValue = 0d, totalinvoiceValue = 0d, totalIgstAmt = 0d, totalCgstAmt = 0d, totalSgstAmt = 0d, totalCessAmt = 0d;

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    GstReportPojo pojo = new GstReportPojo();

                    Vector vtr = (Vector) result.get(i);
                    String acNo = vtr.get(0).toString();
                    String date = vtr.get(1).toString();
                    double recNo = Double.parseDouble(vtr.get(2).toString());
                    String trsNo = vtr.get(3).toString();
                    String txnid = vtr.get(4).toString();
                    String tranDesc = vtr.get(5).toString();
                    double drAmt = Double.parseDouble(vtr.get(6).toString());
                    double crAmt = Double.parseDouble(vtr.get(7).toString());
                    String details = vtr.get(8).toString();
                    String custName = vtr.get(9).toString();
                    String gstIdentificationNumber = vtr.get(10).toString();
                    String mailStateCode = vtr.get(11).toString();
                    String state = vtr.get(12).toString();

                    pojo.setNameofreceipient(custName.trim());
                    pojo.setGstinUin(gstIdentificationNumber.trim());
                    pojo.setTxnid(txnid);
                    pojo.setStateName(state);
                    pojo.setPos("");
                    pojo.setAcno(acNo);
                    String invoiceNo = invoiceNoMap.get(acNo) == null ? "0" : invoiceNoMap.get(acNo);
                    if (invoiceNoShow == 1) {
                        pojo.setNo(date + "-" + formatter.format(recNo));
                    } else {
                        pojo.setNo(CbsUtil.lPadding(6, Integer.parseInt(invoiceNo)));
                    }
//                    if (optionType.equalsIgnoreCase("Summary")) {
//                        pojo.setNo("0000");
//                    } else {
//                        pojo.setNo(date + "-" + formatter.format(recNo));
//                    }
                    if (!acno.equalsIgnoreCase("")) {
                        pojo.setDate(dmyOne.format(ymd.parse(date)));
                    } else {
                        pojo.setDate(date);
                    }
                    if (tranDesc.equalsIgnoreCase("75") || tranDesc.equalsIgnoreCase("76")) {
                        drAmt = CbsUtil.round((drAmt * 100) / (100 + gstMap.get("IGST")), 2);
                    }
                    double invoiceValue = drAmt + (drAmt * gstMap.get("IGST") / 100);
                    pojo.setInvoiceValue(new BigDecimal(CbsUtil.round(invoiceValue, 2)));
                    pojo.setHsnSac("");
                    if (optionType.equalsIgnoreCase("Summary")) {
                        pojo.setGoodsServicedescription("Bank Charges");
                    } else {
                        pojo.setGoodsServicedescription(details.trim());
                    }

                    pojo.setTaxableValue(new BigDecimal(drAmt));
                    pojo.setQty("");
                    pojo.setUnit("");

                    totalTaxValue = totalTaxValue + drAmt;
                    totalinvoiceValue = totalinvoiceValue + invoiceValue;

                    if (!mailStateCode.equalsIgnoreCase(branchState)) {
                        pojo.setIgstRate(gstMap.get("IGST"));
                        pojo.setIgstAmt(CbsUtil.round(drAmt * gstMap.get("IGST") / 100, 2));
                        totalIgstAmt = totalIgstAmt + (drAmt * gstMap.get("IGST") / 100);
                    } else {
                        double tmpIgstAmt = CbsUtil.round(drAmt * gstMap.get("IGST") / 100, 2);
                        double tmpCgstAmt = CbsUtil.round((tmpIgstAmt / 2), 2);

                        pojo.setCgstRate(gstMap.get("CGST"));
                        pojo.setCgstAmt(tmpCgstAmt);
                        totalCgstAmt = totalCgstAmt + tmpCgstAmt;
                        double tmpSgstAmt = tmpIgstAmt - tmpCgstAmt;
                        pojo.setSgstRate(gstMap.get("SGST"));
                        pojo.setSgstAmt(tmpSgstAmt);
                        totalSgstAmt = totalSgstAmt + tmpSgstAmt;


//                        pojo.setCgstRate(gstMap.get("CGST"));
//                        pojo.setCgstAmt(CbsUtil.round(drAmt * gstMap.get("CGST") / 100, 2));
//                        totalCgstAmt = totalCgstAmt + (drAmt * gstMap.get("CGST") / 100);
//                        pojo.setSgstRate(gstMap.get("SGST"));
//                        pojo.setSgstAmt(CbsUtil.round(drAmt * gstMap.get("SGST") / 100, 2));
//                        totalSgstAmt = totalSgstAmt + (drAmt * gstMap.get("SGST") / 100);
                        pojo.setCessRate(0);
                        pojo.setCessAmt(0);
                        totalCessAmt = totalCessAmt + 0;
                    }
                    pojo.setSupplyAttractsReverseCharge("");
                    pojo.setTaxonassessment("");
                    pojo.setAssessmentOrderDetailsNo("");
                    pojo.setAssessmentOrderDetailsDt("");
                    pojo.setNameofecommerceoperator("");
                    pojo.setgSTINofecommerceOperator("");
                    pojo.setExportType("");
                    pojo.setShNo("");
                    pojo.setShDate("");
                    pojo.setShPortCode("");
                    pojo.setReceipientCategory("");
                    pojo.setItemType("");
                    dataList.add(pojo);
                }
                //Total
                if (acno.equalsIgnoreCase("")) {
                    GstReportPojo pojo1 = new GstReportPojo();
                    pojo1.setNameofreceipient("");
                    pojo1.setGstinUin("");
                    pojo1.setStateName("");
                    pojo1.setPos("");
                    pojo1.setNo("999999");
                    pojo1.setDate("Total");
                    pojo1.setTxnid(" ");
                    pojo1.setInvoiceValue(new BigDecimal(CbsUtil.round(totalinvoiceValue, 2)));
                    pojo1.setHsnSac("");
                    pojo1.setGoodsServicedescription("");
                    pojo1.setTaxableValue(new BigDecimal(totalTaxValue));
                    pojo1.setQty("");
                    pojo1.setUnit("");
                    pojo1.setIgstRate(gstMap.get("IGST"));
                    pojo1.setIgstAmt(CbsUtil.round(totalIgstAmt, 2));
                    pojo1.setCgstRate(gstMap.get("CGST"));
                    pojo1.setCgstAmt(CbsUtil.round(totalCgstAmt, 2));
                    pojo1.setSgstRate(gstMap.get("SGST"));
                    pojo1.setSgstAmt(CbsUtil.round(totalSgstAmt, 2));
                    pojo1.setCessRate(0);
                    pojo1.setCessAmt(totalCessAmt);
                    pojo1.setSupplyAttractsReverseCharge("");
                    pojo1.setTaxonassessment("");
                    pojo1.setAssessmentOrderDetailsNo("");
                    pojo1.setAssessmentOrderDetailsDt("");
                    pojo1.setNameofecommerceoperator("");
                    pojo1.setgSTINofecommerceOperator("");
                    pojo1.setExportType("");
                    pojo1.setShNo("");
                    pojo1.setShDate("");
                    pojo1.setShPortCode("");
                    pojo1.setReceipientCategory("");
                    pojo1.setItemType("");
                    dataList.add(pojo1);
                }
            }
            List venderGstlist = new ArrayList<>();
            if (optionType.equalsIgnoreCase("Summary")) {
                venderGstlist = em.createNativeQuery("select vi.gst_in_no,vi.invoice_no,vi.invoice_amt,vi.cgst,vi.sgst,vi.igst,"
                        + "vd.vender_name,vd.vender_state,vi.enter_by,vi.enter_dt from vender_invoice_master vi,vender_master vd where vi.auth_flag='Y' and vi.gst_in_no = vd.gst_in_no "
                        + "and enter_dt between '" + frDt + "' and '" + toDt + "'").getResultList();
            } else if (optionType.equalsIgnoreCase("Individual Gst")) {
                venderGstlist = em.createNativeQuery("select vi.gst_in_no,vi.invoice_no,vi.invoice_amt,vi.cgst,vi.sgst,vi.igst,"
                        + "vd.vender_name,vd.vender_state,vi.enter_by,vi.enter_dt from vender_invoice_master vi,vender_master vd where vi.auth_flag='Y' and vi.gst_in_no = vd.gst_in_no "
                        + "and vi.gst_in_no='" + gstNo + "' ").getResultList();
            }
            if (!venderGstlist.isEmpty()) {
                for (int t = 0; t < venderGstlist.size(); t++) {
                    Vector vec = (Vector) venderGstlist.get(t);
                    GstReportPojo pojo2 = new GstReportPojo();
                    pojo2.setNameofreceipient(vec.get(6).toString());
                    pojo2.setGstinUin(vec.get(0).toString());
                    String stateDesc = commonRemote.getRefRecDesc("002", vec.get(7).toString());
                    pojo2.setStateName(stateDesc);
                    pojo2.setPos("");
                    pojo2.setNo(vec.get(1).toString());
                    pojo2.setDate(ymd.format(ymdone.parse(vec.get(9).toString())));
                    pojo2.setTxnid(" ");
                    pojo2.setHsnSac("");
                    pojo2.setGoodsServicedescription("");
                    pojo2.setTaxableValue(new BigDecimal(vec.get(2).toString()));
                    pojo2.setQty("");
                    pojo2.setUnit("");
                    pojo2.setIgstRate(gstMap.get("IGST"));
                    pojo2.setIgstAmt(new BigDecimal(vec.get(5).toString()).doubleValue());
                    pojo2.setCgstRate(gstMap.get("CGST"));
                    pojo2.setCgstAmt(new BigDecimal(vec.get(3).toString()).doubleValue());
                    pojo2.setSgstRate(gstMap.get("SGST"));
                    pojo2.setSgstAmt(new BigDecimal(vec.get(4).toString()).doubleValue());
                    pojo2.setCessRate(0);
                    BigDecimal totalInvoiceValue = new BigDecimal(pojo2.getCgstAmt()).add(new BigDecimal(pojo2.getSgstAmt())).add(new BigDecimal(pojo2.getIgstAmt())).add(pojo2.getTaxableValue());
                    pojo2.setInvoiceValue(totalInvoiceValue);
                    pojo2.setCessAmt(0);
                    pojo2.setSupplyAttractsReverseCharge("");
                    pojo2.setTaxonassessment("");
                    pojo2.setAssessmentOrderDetailsNo("");
                    pojo2.setAssessmentOrderDetailsDt("");
                    pojo2.setNameofecommerceoperator("");
                    pojo2.setgSTINofecommerceOperator("");
                    pojo2.setExportType("");
                    pojo2.setShNo("");
                    pojo2.setShDate("");
                    pojo2.setShPortCode("");
                    pojo2.setReceipientCategory("");
                    pojo2.setItemType("");

                    dataList.add(pojo2);

                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<ThresoldTransactionInfoPojo> getOlineAadharRegistrationData(String frDt, String toDt, String brnCode, String repOption) throws ApplicationException {
        List<ThresoldTransactionInfoPojo> dataList = new ArrayList<ThresoldTransactionInfoPojo>();
        List result = new ArrayList();
        try {

            String subCondition = "", status = "";
            if (brnCode.equalsIgnoreCase("0A")) {
                subCondition = "";
            } else {
                subCondition = "and d.curbrcode = '" + brnCode + "'";
            }

            if (repOption.equalsIgnoreCase("R")) {
                status = "2";
            } else if (repOption.equalsIgnoreCase("S")) {
                status = "3";
            } else if (repOption.equalsIgnoreCase("P")) {
                status = "3";
            }
//            result = em.createNativeQuery("select customerid,d.acno,CustFullName,mobileno,aadharNo from cbs_customer_master_detail a,(select max(request_no),id_no mobileno,request aadharNo from cbs_third_party_request  where date_format(received_time,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and status=2\n"
//                    + "group by date_format(received_time,'%Y%m%d'),id_no) b,customerid c,accountmaster d where a.SuspensionFlg not in ('S','Y') and a.mobilenumber = b.mobileno \n"
//                    + "and cast(a.customerid as unsigned) = c.custid and c.acno = d.acno and (ifnull(d.closingdate,'')='' or d.closingdate>'" + toDt + "') " + subCondition + " "
//                    + "union "
//                    + "select customerid,d.acno,CustFullName,mobileno,aadharNo from cbs_customer_master_detail a,(select max(request_no),id_no mobileno,request aadharNo from cbs_third_party_request  where date_format(received_time,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and status=2\n"
//                    + "group by date_format(received_time,'%Y%m%d'),id_no) b,customerid c,td_accountmaster d where a.SuspensionFlg not in ('S','Y') and a.mobilenumber = b.mobileno \n"
//                    + "and cast(a.customerid as unsigned) = c.custid and c.acno = d.acno and (ifnull(d.closingdate,'')='' or d.closingdate>'" + toDt + "') " + subCondition + ""
//                    + "union\n"
//                    + "select customerid,d.acno,CustFullName,mobileno,aadharNo from cbs_customer_master_detail a,(select max(request_no),id_no mobileno,request aadharNo from cbs_third_party_request  where date_format(received_time,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and status=2\n"
//                    + "group by date_format(received_time,'%Y%m%d'),id_no) b,customerid c,accountmaster d where a.SuspensionFlg not in ('S','Y') and a.mobilenumber = substring(b.mobileno,3,10) \n"
//                    + "and cast(a.customerid as unsigned) = c.custid and c.acno = d.acno and (ifnull(d.closingdate,'')='' or d.closingdate>'" + toDt + "') " + subCondition + " "
//                    + "union "
//                    + "select customerid,d.acno,CustFullName,mobileno,aadharNo from cbs_customer_master_detail a,(select max(request_no),id_no mobileno,request aadharNo from cbs_third_party_request  where date_format(received_time,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and status=2\n"
//                    + "group by date_format(received_time,'%Y%m%d'),id_no) b,customerid c,td_accountmaster d where a.SuspensionFlg not in ('S','Y') and a.mobilenumber = substring(b.mobileno,3,10) \n"
//                    + "and cast(a.customerid as unsigned) = c.custid and c.acno = d.acno and (ifnull(d.closingdate,'')='' or d.closingdate>'" + toDt + "') " + subCondition + "").getResultList();
            if (!repOption.equalsIgnoreCase("P")) {
                result = em.createNativeQuery("select customerid,d.acno,CustFullName,mobileno,aadharNo from cbs_customer_master_detail a,(select max(request_no),id_no mobileno,request aadharNo from cbs_third_party_request  where date_format(received_time,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and status=" + status + "\n"
                        + "group by date_format(received_time,'%Y%m%d'),id_no) b,customerid c,accountmaster d where a.SuspensionFlg not in ('S','Y') \n"
                        + "and cast(a.customerid as unsigned) = c.custid and c.acno = d.acno and substring(b.aadharNo,14,12) = d.acno \n"
                        + "and (ifnull(d.closingdate,'')='' or d.closingdate>'" + toDt + "') " + subCondition + " \n"
                        + "union \n"
                        + "select customerid,d.acno,CustFullName,mobileno,aadharNo from cbs_customer_master_detail a,(select max(request_no),id_no mobileno,request aadharNo from cbs_third_party_request  where date_format(received_time,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and status=" + status + "\n"
                        + "group by date_format(received_time,'%Y%m%d'),id_no) b,customerid c,td_accountmaster d where a.SuspensionFlg not in ('S','Y')  \n"
                        + "and cast(a.customerid as unsigned) = c.custid and c.acno = d.acno and substring(b.aadharNo,14,12) = d.acno \n"
                        + "and (ifnull(d.closingdate,'')='' or d.closingdate>'" + toDt + "') " + subCondition + "").getResultList();

            } else {
                result = em.createNativeQuery("select customerid,d.acno,CustFullName,mobileno,aadharNo,a.IdentityNo,date_format(a.LastChangeTime,'%d/%m/%Y') from cbs_customer_master_detail a,(select max(request_no),id_no mobileno,request aadharNo \n"
                        + "from cbs_third_party_request  where date_format(received_time,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and status= " + status + "\n"
                        + "group by date_format(received_time,'%Y%m%d'),id_no) b,customerid c,accountmaster d where a.SuspensionFlg not in ('S','Y') \n"
                        + "and cast(a.customerid as unsigned) = c.custid and  a.legal_document = 'E'\n"
                        + "and c.acno = d.acno and substring(b.aadharNo,14,12) = d.acno \n"
                        + "and (ifnull(d.closingdate,'')='' or d.closingdate>'" + toDt + "') \n"
                        + "union\n"
                        + "select customerid,d.acno,CustFullName,mobileno,aadharNo,a.IdentityNo,date_format(a.LastChangeTime,'%d/%m/%Y') from cbs_customer_master_detail a,(select max(request_no),id_no mobileno,request aadharNo from cbs_third_party_request  \n"
                        + "where date_format(received_time,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and status= " + status + "\n"
                        + "group by date_format(received_time,'%Y%m%d'),id_no) b,customerid c,td_accountmaster d where a.SuspensionFlg not in ('S','Y')  \n"
                        + "and cast(a.customerid as unsigned) = c.custid and  a.legal_document = 'E'\n"
                        + "and c.acno = d.acno and substring(b.aadharNo,14,12) = d.acno \n"
                        + "and (ifnull(d.closingdate,'')='' or d.closingdate>'" + toDt + "')").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    ThresoldTransactionInfoPojo pojo = new ThresoldTransactionInfoPojo();
                    pojo.setCustId(vtr.get(0).toString());
                    pojo.setAcNo(vtr.get(1).toString());
                    pojo.setCustName(vtr.get(2).toString());
                    pojo.setRiskCategory(vtr.get(3).toString());
                    if (repOption.equalsIgnoreCase("P")) {
                        pojo.setThresoldUpdateDt(vtr.get(5).toString());
                    } else {
                        pojo.setThresoldUpdateDt(vtr.get(4).toString().substring(0, 12));
                    }
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<OnlinePigmeInfoPojo> getOlinePigmeInfoData(String frDt, String toDt, String status, String brnCode, String ddagentCode) throws ApplicationException {
        List<OnlinePigmeInfoPojo> dataList = new ArrayList<OnlinePigmeInfoPojo>();
        try {
            //   String ddAgentCode1 = (Integer.valueOf(ddagentCode) < 10 ? "" : "") + Integer.valueOf(ddagentCode);
            String agName = "";
            List agentNameList = em.createNativeQuery("select name from ddsagent where brncode = '" + brnCode + "' and Agcode = '" + ddagentCode + "'").getResultList();
            if (!agentNameList.isEmpty()) {
                Vector ele = (Vector) agentNameList.get(0);
                agName = ele.get(0).toString();
            }

            int ddAgentCode = Integer.valueOf(ddagentCode);
            String statusCondition = "";
            if (status.equalsIgnoreCase("All")) {
                statusCondition = "";
            } else {
                statusCondition = "and cbs_return_code = '" + status + "'";
            }
            String brnCodeQuery = "";
            if (!brnCode.equalsIgnoreCase("0A")) {
                brnCodeQuery = " and branch_code = '" + String.valueOf(Integer.parseInt(brnCode)) + "' and b.brncode = '" + brnCode + "'  ";
            }
            List result = em.createNativeQuery("select b.AlphaCode,branch_code,agent_code,acno,receipt_amt,cbs_return_code,cbs_return_reason,ifnull(receipt_no,''),"
                    + "receipt_dt,receipt_time,cbs_received_time from online_pigme_request a,branchmaster b  "
                    + "where date_format(cbs_received_time,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and a.agent_code=" + ddAgentCode + " and  a.branch_code = b.BrnCode " + statusCondition + "  " + brnCodeQuery + " order by branch_code").getResultList();

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    OnlinePigmeInfoPojo pojo = new OnlinePigmeInfoPojo();

                    String alphaCode = vtr.get(0).toString();
                    String branchCode = vtr.get(1).toString();
                    String agentCode = vtr.get(2).toString();
                    String acNo = vtr.get(3).toString();

                    if (branchCode.length() < 2) {
                        branchCode = "0" + branchCode;
                    }
                    if (agentCode.length() < 2) {
                        agentCode = "0" + agentCode;
                    }
                    String fullAcno = branchCode + CbsAcCodeConstant.DS_AC.getAcctCode() + acNo + agentCode;
                    String amt = vtr.get(4).toString();
                    String returnCode = vtr.get(5).toString();
                    String returnReason = vtr.get(6).toString();

                    pojo.setAlphaCode(alphaCode);
                    pojo.setAgentCode(agentCode);
                    pojo.setAcno(fullAcno);
                    pojo.setCustName(ftsPosting.ftsGetCustName(fullAcno));
                    pojo.setAmt(Double.parseDouble(amt));
                    pojo.setReason(returnReason);
                    pojo.setReceiptNo(vtr.get(7).toString());
                    pojo.setReceivedDate(vtr.get(10).toString());
                    pojo.setReceipDate(vtr.get(8).toString());
                    pojo.setReceipTime(vtr.get(9).toString());
                    pojo.setAgentName(agName);
                    dataList.add(pojo);
                }
            }

        } catch (Exception ex) {
        }
        return dataList;
    }

    public List<NpciInwardNonaadhaarPojo> getNpciInwardNonAadharDetails(String frdt, String todt, String status) throws ApplicationException {
        List<NpciInwardNonaadhaarPojo> resultlist = new ArrayList<NpciInwardNonaadhaarPojo>();
        try {

            if (status.equalsIgnoreCase("S")) {
                List result = em.createNativeQuery("select ORIGINATOR_CODE,DEST_BANK_IFSC,DEST_BANK_ACNO,BEN_NAME_ORGN,LPG_ID,"
                        + "REASON,DATE_FORMAT(TRAN_DATE,'%d/%m/%Y') from cbs_npci_inward_non_aadhaar where TRAN_DATE between"
                        + " '" + ymd.format(dmyOne.parse(frdt)) + "' and '" + ymd.format(dmyOne.parse(todt)) + "' and STATUS='S'").getResultList();
                if (result.isEmpty()) {
                    throw new ApplicationException("data does not exits.");
                } else {
                    for (int i = 0; i < result.size(); i++) {
                        Vector vtr = (Vector) result.get(i);
                        NpciInwardNonaadhaarPojo pojo = new NpciInwardNonaadhaarPojo();
                        String orignatorCode = vtr.get(0).toString();
                        String destbankIfsc = vtr.get(1).toString();
                        String destBankAcno = vtr.get(2).toString();
                        String benNameOrgn = vtr.get(3).toString();
                        String lpgId = vtr.get(4).toString();
                        String reason = vtr.get(5).toString();
                        String tranDate = vtr.get(6).toString();

                        pojo.setOrignatorCode(orignatorCode);
                        pojo.setDestBankIfsc(destbankIfsc);
                        pojo.setDestBankAcno(destBankAcno);
                        pojo.setBenNameOrgn(benNameOrgn);
                        pojo.setLpgId(lpgId);
                        pojo.setStatus(status);
                        pojo.setReason(reason);
                        pojo.setTranDate(tranDate);
                        resultlist.add(pojo);
                    }
                }
            } else if (status.equalsIgnoreCase("U")) {
                List result = em.createNativeQuery("select ORIGINATOR_CODE,DEST_BANK_IFSC,DEST_BANK_ACNO,BEN_NAME_ORGN,LPG_ID,"
                        + "REASON,DATE_FORMAT(TRAN_DATE,'%d/%m/%Y') from cbs_npci_inward_non_aadhaar where TRAN_DATE between"
                        + " '" + ymd.format(dmyOne.parse(frdt)) + "' and '" + ymd.format(dmyOne.parse(todt)) + "' and STATUS='U'").getResultList();
                if (result.isEmpty()) {
                    throw new ApplicationException("data does not exits.");
                } else {
                    for (int i = 0; i < result.size(); i++) {
                        Vector vtr = (Vector) result.get(i);
                        NpciInwardNonaadhaarPojo pojo = new NpciInwardNonaadhaarPojo();
                        String orignatorCode = vtr.get(0).toString();
                        String destbankIfsc = vtr.get(1).toString();
                        String destBankAcno = vtr.get(2).toString();
                        String benNameOrgn = vtr.get(3).toString();
                        String lpgId = vtr.get(4).toString();
                        String reason = vtr.get(5).toString();
                        String tranDate = vtr.get(6).toString();

                        pojo.setOrignatorCode(orignatorCode);
                        pojo.setDestBankIfsc(destbankIfsc);
                        pojo.setDestBankAcno(destBankAcno);
                        pojo.setBenNameOrgn(benNameOrgn);
                        pojo.setLpgId(lpgId);
                        pojo.setStatus(status);
                        pojo.setReason(reason);
                        pojo.setTranDate(tranDate);
                        resultlist.add(pojo);
                    }
                }

            } else if (status.equalsIgnoreCase("ALL")) {
                List result = em.createNativeQuery("select ORIGINATOR_CODE,DEST_BANK_IFSC,DEST_BANK_ACNO,BEN_NAME_ORGN,LPG_ID,"
                        + "REASON,DATE_FORMAT(TRAN_DATE,'%d/%m/%Y') from cbs_npci_inward_non_aadhaar where TRAN_DATE between"
                        + " '" + ymd.format(dmyOne.parse(frdt)) + "' and '" + ymd.format(dmyOne.parse(todt)) + "'").getResultList();
                if (result.isEmpty()) {
                    throw new ApplicationException("data does not exits.");
                } else {
                    for (int i = 0; i < result.size(); i++) {
                        Vector vtr = (Vector) result.get(i);
                        NpciInwardNonaadhaarPojo pojo = new NpciInwardNonaadhaarPojo();
                        String orignatorCode = vtr.get(0).toString();
                        String destbankIfsc = vtr.get(1).toString();
                        String destBankAcno = vtr.get(2).toString();
                        String benNameOrgn = vtr.get(3).toString();
                        String lpgId = vtr.get(4).toString();
                        String reason = vtr.get(5).toString();
                        String tranDate = vtr.get(6).toString();

                        pojo.setOrignatorCode(orignatorCode);
                        pojo.setDestBankIfsc(destbankIfsc);
                        pojo.setDestBankAcno(destBankAcno);
                        pojo.setBenNameOrgn(benNameOrgn);
                        pojo.setLpgId(lpgId);
                        pojo.setStatus(status);
                        pojo.setReason(reason);
                        pojo.setTranDate(tranDate);
                        resultlist.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultlist;
    }
//     public List<NpciInwardNonaadhaarPojo> getNpciInwardNonAadharDetails(String frdt,String todt,String status)throws ApplicationException{
//        List<NpciInwardNonaadhaarPojo> resultlist = new ArrayList<NpciInwardNonaadhaarPojo>();
//        try{
//           
//            if(status.equalsIgnoreCase("S")){
//           List result = em.createNativeQuery("select ORIGINATOR_CODE,DEST_BANK_IFSC,DEST_BANK_ACNO,BEN_NAME_ORGN,LPG_ID,"
//                   + "REASON,TRAN_DATE from cbs_npci_inward_non_aadhaar where date_format(TRAN_DATE,'%Y%m%d') between"
//                   + " '" + frdt + "' and '" + todt + "' and STATUS='S'").getResultList();
//           if(result.isEmpty()){
//               throw new ApplicationException("data does not exits.");
//           }else{
//                for (int i = 0; i < result.size(); i++){
//                  Vector vtr = (Vector) result.get(i);
//                  NpciInwardNonaadhaarPojo pojo= new NpciInwardNonaadhaarPojo();
//                  String orignatorCode= vtr.get(0).toString();
//                  String destbankIfsc= vtr.get(1).toString();
//                  String destBankAcno= vtr.get(2).toString();
//                  String benNameOrgn = vtr.get(3).toString();
//                  String lpgId = vtr.get(4).toString();
//                  String reason =vtr.get(5).toString();
//                  String tranDate=vtr.get(6).toString();
//           }}
//            }else if(status.equalsIgnoreCase("U")){
//                 List result = em.createNativeQuery("select ORIGINATOR_CODE,DEST_BANK_IFSC,DEST_BANK_ACNO,BEN_NAME_ORGN,LPG_ID,"
//                   + "REASON,TRAN_DATE from cbs_npci_inward_non_aadhaar where TRAN_DATE between"
//                   + " '" + ymd.format(dmyOne.parse(frdt))+ "' and '" + ymd.format(dmyOne.parse(todt)) + "' and STATUS='U'").getResultList();
//              if(result.isEmpty()){
//               throw new ApplicationException("data does not exits.");
//           }else{
//                   for (int i = 0; i < result.size(); i++){
//                  Vector vtr = (Vector) result.get(i);
//                  NpciInwardNonaadhaarPojo pojo= new NpciInwardNonaadhaarPojo();
//                  String orignatorCode= vtr.get(0).toString();
//                  String destbankIfsc= vtr.get(1).toString();
//                  String destBankAcno= vtr.get(2).toString();
//                  String benNameOrgn = vtr.get(3).toString();
//                  String lpgId = vtr.get(4).toString();
//                  String reason =vtr.get(5).toString();
//                  String tranDate=vtr.get(6).toString();
//              }}
//            }else if(status.equalsIgnoreCase("ALL")){
//                 List result = em.createNativeQuery("select ORIGINATOR_CODE,DEST_BANK_IFSC,DEST_BANK_ACNO,BEN_NAME_ORGN,LPG_ID,"
//                   + "REASON,TRAN_DATE from cbs_npci_inward_non_aadhaar where date_format(TRAN_DATE,'%Y%m%d') between"
//                   + " '" + frdt + "' and '" + todt + "'").getResultList();
//               if(result.isEmpty()){
//               throw new ApplicationException("data does not exits.");
//           }else{
//                  for (int i = 0; i < result.size(); i++){
//                  Vector vtr = (Vector) result.get(i);
//                  NpciInwardNonaadhaarPojo pojo= new NpciInwardNonaadhaarPojo();
//                  String orignatorCode= vtr.get(0).toString();
//                  String destbankIfsc= vtr.get(1).toString();
//                  String destBankAcno= vtr.get(2).toString();
//                  String benNameOrgn = vtr.get(3).toString();
//                  String lpgId = vtr.get(4).toString();
//                  String reason =vtr.get(5).toString();
//                  String tranDate=vtr.get(6).toString();
//               }
//            }
//            
//             for (int i = 0; i < result.size(); i++){
//                  Vector vtr = (Vector) result.get(i);
//                  NpciInwardNonaadhaarPojo pojo= new NpciInwardNonaadhaarPojo();
//                  String orignatorCode= vtr.get(0).toString();
//                  String destbankIfsc= vtr.get(1).toString();
//                  String destBankAcno= vtr.get(2).toString();
//                  String benNameOrgn = vtr.get(3).toString();
//                  String lpgId = vtr.get(4).toString();
//                  String reason =vtr.get(5).toString();
//                  String tranDate=vtr.get(6).toString();
//                  
//                  
//                  pojo.setOrignatorCode(orignatorCode);
//                  pojo.setDestBankIfsc(destbankIfsc);
//                  pojo.setDestBankAcno(destBankAcno);
//                  pojo.setBenNameOrgn(benNameOrgn);
//                  pojo.setLpgId(lpgId);
//                  pojo.setStatus(status);
//                  pojo.setReason(reason);
//                  pojo.setTranDate(tranDate);
//                  resultlist.add(pojo);
//     } 
//         }   
//            }catch(Exception ex){
//            throw new ApplicationException(ex.getMessage());
//        }
//        return resultlist;
//    }

    @Override
    public List getBranchInfoByBrCode(String branch) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct  bn.bankname,bn.bankaddress,bm.BranchName,pi.BrPhone,bm.Pincode,bm.gst_in ,bm.State from bnkadd bn,branchmaster bm, parameterinfo pi \n"
                    + "where bn.alphacode = bm.alphacode and\n"
                    + "bm.brncode = pi.brncode and bm.BrnCode = " + Integer.parseInt(branch) + "\n"
                    + "group by bn.bankname,bn.bankaddress").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List getCustomerInfoByCustNo(String customerNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select custfullname,trim(MailAddressLine1),trim(concat(ifnull(MailAddressLine2,''),' ',ifnull(MailVillage,''),' ',ifnull(MailBlock,''))) Address1,\n"
                    + "MailPostalCode,ifnull(mailCityCode,''),mobilenumber \n"
                    + "from cbs_customer_master_detail where customerid =(select CustId from customerid where acno = '" + customerNo + "')\n"
                    + "").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getCustomerInfoBygstNo(String gstNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select custfullname,trim(MailAddressLine1),trim(concat(ifnull(MailAddressLine2,''),' ',ifnull(MailVillage,''),' ',ifnull(MailBlock,''))) Address1,\n"
                    + "MailPostalCode,ifnull(mailCityCode,''),mobilenumber \n"
                    + "from cbs_customer_master_detail where gstIdentificationNumber = '" + gstNo + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public String savedGenerateInvoiceData(String acNo, String finYear, String branch, String userName, String option, String frDt, String toDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        int invoiceNo = 0;
        try {
            ut.begin();
//            String firstDt = CbsUtil.getFirstDateOfGivenDate(new Date());
//            Date monthEndDt = CbsUtil.getLastDateOfMonth(new Date());
            List fYearList = tdRemote.fYearData(branch);
            Vector fVector = (Vector) fYearList.get(0);
            finYear = fVector.get(0).toString();
            List result = new ArrayList();
            if (option.equalsIgnoreCase("B")) {
                result = em.createNativeQuery("select * from invoice_no_generate_master where gen_flag = '" + option + "' and date_format(generate_dt,'%Y%m%d') between '" + frDt + "' and '" + toDt + "'").getResultList();
            } else {
                result = em.createNativeQuery("select * from invoice_no_generate_master where account_no = '" + acNo + "' "
                        + "and date_format(generate_dt,'%Y%m%d') between '" + frDt + "' and '" + toDt + "'").getResultList();
            }
            if (!result.isEmpty()) {
                ut.rollback();
                throw new ApplicationException("Invoice No. alredy Generate in this month of Account No. !!!");
            }
            List list1 = em.createNativeQuery("select cast(ifnull(max(invoice_no),0) as unsigned)InvoiceNo from invoice_no_generate_master").getResultList();
            Vector vtr = (Vector) list1.get(0);
            invoiceNo = Integer.parseInt(vtr.get(0).toString());

//            List gstRegAcnoList1 = em.createNativeQuery("select b.acno,a.CustFullName,gstin,cast(a.customerid as unsigned) custid from cbs_customer_master_detail a,customerid b,accountmaster c,accounttypemaster d, "
//                    + "((select cast(customerid as unsigned) cId,max(updateDt) maxUpdateDt,gstin from ( "
//                    + "select customerid,ifnull(gstIdentificationNumber,'') gstin,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail "
//                    + "where date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + ymd.format(new Date()) + "'and length(gstIdentificationNumber) =15 "
//                    + "union "
//                    + "select customerid,ifnull(gstIdentificationNumber,'') gstin,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail_his "
//                    + "where date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + ymd.format(new Date()) + "'and length(gstIdentificationNumber) =15 "
//                    + ")a group by cId) d) "
//                    + "where cast(a.customerid as unsigned) = d.cId and cast(a.customerid as unsigned) = b.custid and b.acno = c.acno and (ClosingDate is null or ClosingDate = '' or ClosingDate > '" + frDt + "') "
//                    + "and d.acctnature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.SAVING_AC + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "') and c.accttype = d.acctcode and c.acno not in"
//                    + "(select account_no from invoice_no_generate_master where fin_year = '" + finYear + "' and generate_dt between '" + frDt + "' and '" + toDt + "') order by custid,b.acno").getResultList();

            List gstRegAcnoList = em.createNativeQuery("select b.acno,CustFullName,gstin from ("
                    + "(select b.acno,a.CustFullName,gstin,MailStateCode,REF_DESC from cbs_customer_master_detail a,customerid b,cbs_ref_rec_type c,((select cast(customerid as unsigned) cId,max(updateDt) maxUpdateDt,gstin from ("
                    + "select customerid,ifnull(gstIdentificationNumber,'') gstin,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail "
                    + "where date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + ymd.format(new Date()) + "'and length(gstIdentificationNumber) =15 "
                    + "union "
                    + "select customerid,ifnull(gstIdentificationNumber,'') gstin,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail_his "
                    + "where date_format(LastChangeTime,'%Y%m%d') between '19000101' and '" + ymd.format(new Date()) + "'and length(gstIdentificationNumber) =15 "
                    + ")a group by cId) d) "
                    + "where cast(a.customerid as unsigned) = d.cId and cast(a.customerid as unsigned) = b.custid and a.MailStateCode = c.REF_CODE and c.ref_rec_no = '002') a,"
                    + "(select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                    + "group by dt,trsno,recno,acno,txnid "
                    + "union all "
                    + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from ca_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                    + "group by dt,trsno,recno,acno,txnid "
                    + "union all "
                    + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from rdrecon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                    + "group by dt,trsno,recno,acno,txnid "
                    + "union all "
                    + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from td_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                    + "group by dt,trsno,recno,acno,txnid "
                    + "union all "
                    + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from ddstransaction a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                    + "group by dt,trsno,recno,acno,txnid "
                    + "union all "
                    + "select acno,trsno,txnid,dt,recno,DrAmt,CrAmt,Details,TranDesc,org_brnid from loan_recon a,cbs_ref_rec_type b where b.REF_REC_NO = '454' and a.TranDesc =  cast(REF_CODE as unsigned) and dt between '" + frDt + "' and '" + toDt + "' "
                    + "group by dt,trsno,recno,acno,txnid) b "
                    + ") where a.acno = b.acno and dt between '" + frDt + "' and '" + toDt + "' "
                    + "and b.acno not in(select account_no from invoice_no_generate_master where date_format(generate_dt,'%Y%m%d') between '" + frDt + "' and '" + toDt + "') "
                    + "group by acno order by 1").getResultList();

            if (!gstRegAcnoList.isEmpty()) {
                for (int i = 0; i < gstRegAcnoList.size(); i++) {
                    Vector ele = (Vector) gstRegAcnoList.get(i);
                    String acno = ele.get(0).toString();
                    String custName = ele.get(1).toString();
                    String gstin = ele.get(2).toString();
                    invoiceNo = invoiceNo + 1;
//                    List list1 = em.createNativeQuery("select cast(ifnull(max(invoice_no),0)+1 as unsigned)InvoiceNo from invoice_no_generate_master").getResultList();
//                    Vector vtr = (Vector) list1.get(0);
//                    invoiceNo = Integer.parseInt(vtr.get(0).toString());
                    int insertQuery = em.createNativeQuery("INSERT INTO invoice_no_generate_master (account_no, generate_dt, invoice_no,fin_year,orgin_branch,enter_by,enter_dt,gen_flag) "
                            + "VALUES ('" + acno + "', '" + toDt + "', '" + invoiceNo + "', '" + finYear + "','" + branch + "','" + userName + "',now(),'" + option + "')").executeUpdate();

                    if (insertQuery <= 0) {
                        throw new ApplicationException("Insertion Problem !!!");
                    }
                }
            }
            ut.commit();
            return "true : " + invoiceNo;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }


    }

    @Override
    public List isVoucherGen(String option, String frDt, String toDt) throws ApplicationException {
        try {
            return em.createNativeQuery("select * from invoice_no_generate_master where gen_flag = '" + option + "' and date_format(generate_dt,'%Y%m%d') between '" + frDt + "' and '" + toDt + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getddAgent(String brnCode) throws ApplicationException {
        List ddAgentList = new ArrayList<>();
        try {
            ddAgentList = em.createNativeQuery("select Agcode,name from ddsagent where brncode='" + brnCode + "'").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return ddAgentList;
    }

    public String getCodeCbsparameterInfo(String name) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select code from cbs_parameterinfo where name='" + name + "'").getResultList();
            if (list.isEmpty()) {
                return "";
            }
            Vector element = (Vector) list.get(0);
            return element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
}
