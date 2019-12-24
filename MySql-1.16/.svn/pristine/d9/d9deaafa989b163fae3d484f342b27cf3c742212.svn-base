package com.cbs.facade.master;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
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

@Stateless(mappedName = "GlMasterFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class GlMasterFacade implements GlMasterFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

    public List glbMasterCodeCombo() throws ApplicationException {
        List code = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctcode from accounttypemaster where acctnature  in('" + CbsConstant.PAY_ORDER + "')");
            code = selectQuery.getResultList();
            return code;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List glbMasterAcctTypeCombo() throws ApplicationException {
        List actype = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctcode from accounttypemaster where acctnature not in('" + CbsConstant.PAY_ORDER + "')");
            actype = selectQuery.getResultList();
            return actype;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List glbMasterGridDetail() throws ApplicationException {
        List gd = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select  Sno,GroupCode,SubGroupCode,code,Description,AcType,GlbActype,ActStatus from glbmast order by GroupCode,SubGroupCode");
            gd = selectQuery.getResultList();
            return gd;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List glbMasterAcName(String acno) throws ApplicationException {
        List acn = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct acname from gltable where substring(acno,3,10) = '" + acno + "'");
            acn = selectQuery.getResultList();
            return acn;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List glbMasterAcDesc(String acType) throws ApplicationException {
        List acd = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctdesc from accounttypemaster where acctcode = '" + acType + "'");
            acd = selectQuery.getResultList();
            return acd;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List glbMasterSubGrCodeChk(String glbAcType, int grCode, int subGrCode) throws ApplicationException {
        List chk = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select groupcode from glbmast where glbactype='" + glbAcType + "' and groupcode=" + grCode + " and subgroupcode=" + subGrCode + "");
            chk = selectQuery.getResultList();
            return chk;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String glbMasterDeleteRecord(String sno, String glbAcType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            Query deleteQuery = em.createNativeQuery("delete from glbmast where sno=" + sno + " and glbactype='" + glbAcType + "'");
            var = deleteQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Data could not be Deleted";
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

    public String glbMasterSaveRecord(int grCode, int subGrCode, String codeAcNo, String description, String actype, String glbAcType, int acStatus, String username, String brnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            int sNo;
            List chkList = em.createNativeQuery("select subgroupcode from glbmast where glbactype='" + glbAcType + "' and groupcode=" + grCode + " and subgroupcode=" + subGrCode + "").getResultList();
            if (!chkList.isEmpty()) {
                ut.rollback();
                return "Sub Group Code Already Exist.";
            }
            List snoList = em.createNativeQuery("select coalesce(max(sno),0) from glbmast").getResultList();
            if (snoList.isEmpty()) {
                ut.rollback();
                return "Error In Getting Serial No.";
            } else {
                Vector ele = (Vector) snoList.get(0);
                sNo = Integer.parseInt(ele.get(0).toString()) + 1;
            }
            Query insertQuery = em.createNativeQuery("insert into glbmast(sno,GroupCode,SubGroupCode,code,Description,AcType,GlbActype,ActStatus,LastupdatedBy,Trantime,brncode)values(" + sNo + "," + grCode + "," + subGrCode + ",'" + codeAcNo + "','" + description + "','" + actype + "','" + glbAcType + "'," + acStatus + ",'" + username + "',now(),'" + brnCode + "')");
            var = insertQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Data could not be Saved.";
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

    public List plMasterGrCodeLostFocus(int groupCode, String classfication) throws ApplicationException {
        try {
            return em.createNativeQuery("select coalesce(max(subgroupcode),0) from plmast where groupcode=" + groupCode + " and classification='" + classfication + "'" + "").getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List plMasterCodeLostFocus(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct acname from gltable where substring(acno,3,8)='" + acno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List plMasterGridLoad(int grCode) throws ApplicationException {
        try {
            return em.createNativeQuery("Select SNO,GROUPCODE,SUBGROUPCODE,CODE,DESCRIPTION,CLASSIFICATION,LASTUPDATEDBY,MODE,date_format(TRANTIME,'%d/%m/%Y') From plmast Where groupcode=" + grCode + "" + "").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String plMasterDeleteRecord(int sNo, String classification) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            Query insertQuery1 = em.createNativeQuery("delete From plmast Where sno=" + sNo + " and "
                    + "classification='" + classification + "'");
            var = insertQuery1.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Data could not be Deleted";
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

    public String plMasterSaveDetail(int groupCode, int subGroupCode, String code, String description, String classification, String lastupdatedBy, String mode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            List date = em.createNativeQuery("select distinct DATE from bankdays where dayendflag='N'").getResultList();
            Vector recLst = (Vector) date.get(0);
            String dt = recLst.get(0).toString();
            List sNoList = em.createNativeQuery("SELECT ifnull(MAX(SNO),0)+1 FROM plmast ").getResultList();
            Vector recLst1 = (Vector) sNoList.get(0);
            String sNo = recLst1.get(0).toString();

            List subGrCodeCheck = em.createNativeQuery("select groupcode from plmast where "
                    + "classification='" + classification + "' and groupcode=" + groupCode + " "
                    + "and subgroupcode=" + subGroupCode + "" + " ").getResultList();
            if (!subGrCodeCheck.isEmpty()) {
                ut.rollback();
                return "Sub Group Code Already Exist.";
            }
            Query insertQuery = em.createNativeQuery("insert into plmast(SNO,GroupCode,SubGroupCode,code,"
                    + "Description,classification,LastupdatedBy,Trantime,mode) " + " values(" + Integer.parseInt(sNo) + ","
                    + "" + groupCode + "," + subGroupCode + ",'" + code + "'," + " '" + description + "',"
                    + "'" + classification + "','" + lastupdatedBy + "',convert(datetime,'" + dt + "'),'" + mode + "' )");
            var = insertQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
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

    public List plMasterSubGrCodeLostFocus(int groupCode, int subGrCode) throws ApplicationException {
        try {
            return em.createNativeQuery("Select DESCRIPTION From plmast Where groupcode=" + groupCode + " AND SUBGROUPCODE=" + subGrCode + "" + " ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List accountTypeTrialBalance() throws ApplicationException {
        try {
            return em.createNativeQuery("select acctcode from accounttypemaster where acctnature not in('" + CbsConstant.PAY_ORDER + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List tableDataTrialBalance(int gCode) throws ApplicationException {
        try {
            return em.createNativeQuery("Select GroupCode,SubGroupCode,code,Description,AcType,GlbAcType,ActStatus,LastupdatedBy,date_format(Trantime,'%d/%m/%Y') AS Trantime,sno From trbalmast Where groupcode='" + gCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveDataTrialBalance(String type, int gCode, int subGroupCode, String accStatus, String tmpAcNo, String accType, String codeDesc, String user) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (type.equals("L")) {
                if (gCode >= 1000) {
                    ut.rollback();
                    return "Liabilities Group Code Series Should be less than 1000";
                }
            } else {
                if (gCode <= 1000) {
                    ut.rollback();
                    return "Assests Group Code Series Should be Greater than 1000";
                }
            }
            if (subGroupCode == 0) {
                List secList = em.createNativeQuery("select groupcode from trbalmast where glbactype='" + type + "' and groupcode='" + gCode + "' and subgroupcode='" + subGroupCode + "'").getResultList();
                if (!secList.isEmpty()) {
                    ut.rollback();
                    return "Sub Group Code Already Exist";
                }
            }
            if (accStatus.equals("I")) {
                accStatus = "2";
            } else if (accStatus.equals("O")) {
                accStatus = "1";
            } else {
                accStatus = "0";
            }
            if (tmpAcNo.equals("")) {
                tmpAcNo = "0";
                accStatus = "0";
            }
            if (accType.equals("")) {
                accStatus = "0";
            }
            String currentDts = ymdFormat.format(new Date());
            Query insertQuery = em.createNativeQuery("insert into trbalmast (GroupCode,SubGroupCode,code,Description,AcType,GlbActype,ActStatus,LastupdatedBy,Trantime)"
                    + "values (" + "'" + gCode + "'" + "," + "'" + subGroupCode + "'" + "," + "'" + tmpAcNo + "'" + "," + "'" + codeDesc + "'" + "," + "'" + accType + "'" + "," + "'" + type + "'" + "," + "'" + accStatus + "'" + "," + "'" + user + "'" + "," + "'" + currentDts + "'" + ")");
            int var = insertQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "Record Save Successfully ";
            } else {
                ut.rollback();
                return "Data could not be Inserted";
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

    public String deleteDataTrialBalance(int seqNo, String type) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String sNumber = String.valueOf(seqNo);
            if (sNumber.isEmpty()) {
                ut.rollback();
                return "Seq No. Shouldn't Empty.";
            }
            Query updateQuery = em.createNativeQuery("delete From trbalmast Where sno='" + seqNo + "' and glbactype='" + type + "'");
            int var = updateQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "Record Is deleted Successfully";
            } else {
                ut.rollback();
                return "Data could not be deleted";
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

    public String codeDescriptionTrialBalance(String accType) throws ApplicationException {
        try {
            List acctDescription = em.createNativeQuery("select acctdesc from accounttypemaster where acctcode='" + accType + "'").getResultList();
            Vector acctDescriptions = (Vector) acctDescription.get(0);
            String Descriptions = acctDescriptions.get(0).toString();
            return Descriptions;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String codeTrialBalance(String code, int subGroupCode) throws ApplicationException {
        try {
            int codes = Integer.parseInt(code);
            if ((codes >= Integer.parseInt("002000")) && (codes <= Integer.parseInt("003000"))) {
                return "Does Not Allow Profit and Loss Series.";
            }
            if (subGroupCode > 0) {
                List acctName = em.createNativeQuery("select distinct acname from gltable where substring(acno,5,6)='" + code + "'").getResultList();
                if (!acctName.isEmpty()) {
                    Vector acctNames = (Vector) acctName.get(0);
                    String Names = acctNames.get(0).toString();
                    return Names;
                } else {
                    return "Account No. Does Not Exist";
                }
            } else {
                return "Account No. Does Not Exist";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String groupCodeTrialBalance(String type, int gCode) throws ApplicationException {
        try {
            String gCodes = String.valueOf(gCode);
            if (gCodes.equals("") || gCodes.equalsIgnoreCase(null)) {
                return "Group Code Shouldn't Blank.";
            }
            if (type.equals("L")) {
                if (gCode >= 1000) {
                    return "Liabilities Group Code Series Should be less than 1000";
                }
            } else {
                if (gCode <= 1000) {
                    return "Assests Group Code Series Should be Greater than 1000";
                }
            }
            List secLt = em.createNativeQuery("select ifnull(max(subgroupcode),0)+1 from trbalmast where groupcode='" + gCode + "' and glbactype='" + type + "'").getResultList();
            Vector secLts = (Vector) secLt.get(0);
            String subGCode = secLts.get(0).toString();
            return subGCode;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String subGroupCodeTrialBalance(int gCode, int subGroupCode) throws ApplicationException {
        try {
            String Descriptions;
            List acctDescription = em.createNativeQuery("Select DESCRIPTION From trbalmast Where groupcode='" + gCode + "' AND SUBGROUPCODE='" + subGroupCode + "'").getResultList();
            if (acctDescription.size() > 0) {
                Vector acctDescriptions = (Vector) acctDescription.get(0);
                Descriptions = acctDescriptions.get(0).toString();
            } else {
                Descriptions = "Data Doesn't exist.";
            }
            return Descriptions;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List acctTypeDayBook() throws ApplicationException {
        try {
            return em.createNativeQuery("select acctcode from accounttypemaster where acctnature not in('" + CbsConstant.PAY_ORDER + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List grCodeLostFocusDayBook(int groupCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select coalesce(max(subgroupcode),0) from daybookmast where groupcode=" + groupCode + "").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List subGrCodeLostFocusDayBook(int groupCode, int subGrCode) throws ApplicationException {
        try {
            return em.createNativeQuery("Select DESCRIPTION From daybookmast Where groupcode=" + groupCode + " AND SUBGROUPCODE=" + subGrCode + "" + " ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List codeLostFocusDayBook(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct acname from gltable where SUBSTRING(acno,3,8)='" + acno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List frGlHeadLostFocusDayBook(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select acname from gltable where SUBSTRING(acno,5,6)='" + acno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List toGlHeadLostFocusDayBook(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select acname from gltable where SUBSTRING(acno,5,6)='" + acno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List acTypeLostFocusDayBook(String acType) throws ApplicationException {
        try {
            return em.createNativeQuery("select acctdesc from accounttypemaster where acctcode='" + acType + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List gridLoadDayBook(int grCode) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT distinct SNO,GROUPCODE,SUBGROUPCODE,GLHEAD,DESCRIPTION,FRGLHEAD,TOGLHEAD,ACCTCODE,OPTSTATUS,LASTUPDATEDBY,ifnull(date_format(TRANTIME,'%d/%m/%Y'),'') FROM daybookmast Where groupcode=" + grCode + "" + " order by GROUPCODE,SUBGROUPCODE,GLHEAD,DESCRIPTION").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String deleteRecordDayBook(String acCode, String desc, String glHead) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            Query insertQuery1 = em.createNativeQuery("delete From daybookmast Where glhead='" + glHead + "' and acctcode= '"
                    + acCode + "' and description = '" + desc + "'");
            var = insertQuery1.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Data could not be Deleted";
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

    public String saveDetailDayBook(int groupCode, int subGroupCode, String code, String description, String acCode, int acStatus, String frglHead, String toGlHead, String lastupdatedBy, String codeDescTxt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            List glHeadCheck = em.createNativeQuery("select * from daybookmast where glhead='" + code + "' and description='" + codeDescTxt + "' and acctcode='" + acCode + "' ").getResultList();
            if (glHeadCheck.isEmpty()) {
                List date = em.createNativeQuery("select distinct DATE from bankdays where dayendflag='N' ").getResultList();
                Vector recLst = (Vector) date.get(0);
                String dt = recLst.get(0).toString();
                List sNoList = em.createNativeQuery("SELECT ifnull(MAX(SNO),0)+1 FROM daybookmast ").getResultList();
                Vector recLst1 = (Vector) sNoList.get(0);
                String sNo = recLst1.get(0).toString();
                if (subGroupCode == 0) {
                    List subGrCodeCheck = em.createNativeQuery("select groupcode from daybookmast where groupcode=" + groupCode + " and subgroupcode=" + subGroupCode + " ").getResultList();
                    if (!subGrCodeCheck.isEmpty()) {
                        ut.rollback();
                        return "Sub Group Code Already Exist.";
                    }
                }
                List brnList = em.createNativeQuery("Select brncode from branchMaster").getResultList();
                if (!brnList.isEmpty()) {
                    for (int i = 0; i < brnList.size(); i++) {
                        Vector brnVector = (Vector) brnList.get(i);
                        String brnCd = brnVector.get(0).toString();
                        int length = brnCd.length();
                        int addedZero = 2 - length;
                        for (int j = 0; j < addedZero; j++) {
                            brnCd = "0" + brnCd;
                        }
                        Query insertQuery = em.createNativeQuery("insert into daybookmast (SNo,GroupCode,SubGroupCode,GLHEAD,Description,Acctcode,"
                                + "optStatus,frglhead,toglhead,LastupdatedBy,Trantime,brncode) values(" + sNo + "," + groupCode + "," + subGroupCode
                                + ",'" + code + "','" + description + "','" + acCode + "','" + acStatus + "','" + frglHead + "','" + toGlHead + "','"
                                + lastupdatedBy + "',convert(datetime,'" + dt + "'),'" + brnCd + "' )");
                        var = insertQuery.executeUpdate();
                        if (var <= 0) {
                            ut.rollback();
                            return "System error occured.";
                        }
                    }
                }
            } else {
                ut.rollback();
                return "Entry For This A/c. No., Account Type, Description is not Possible.";
            }
            ut.commit();
            return "true";
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

    public List hoForm9onLoad() throws ApplicationException {
        try {
            String reportingFridate;
            Float amount;
            String effectiveDate;
            List returnList = new ArrayList();
            String lastDateMonth;
            String lastDateDay;
            List l2 = em.createNativeQuery("SELECT LAST_DAY(CURDATE())").getResultList();
            Vector v1 = (Vector) l2.get(0);
            lastDateMonth = v1.get(0).toString();
            List l3 = em.createNativeQuery("SELECT date_format('" + lastDateMonth + "','%W')").getResultList();
            Vector v2 = (Vector) l3.get(0);
            lastDateDay = v2.get(0).toString();
            if (lastDateDay.equals("SATURDAY") || lastDateDay.equals("Saturday")) {
                List l4 = em.createNativeQuery("select CONVERT(VARCHAR(10),DATEADD(DAY,-1,'" + lastDateMonth + "'),103)").getResultList();
                Vector v3 = (Vector) l4.get(0);
                reportingFridate = v3.get(0).toString();
            } else if (lastDateDay.equals("SUNDAY") || lastDateDay.equals("Sunday")) {
                List l4 = em.createNativeQuery("SET CONVERT(VARCHAR(10),DATEADD(DAY,-2,@LAST_DATE_MONTH),103)").getResultList();
                Vector v3 = (Vector) l4.get(0);
                reportingFridate = v3.get(0).toString();
            } else if (lastDateDay.equals("MONDAY") || lastDateDay.equals("Monday")) {
                List l4 = em.createNativeQuery("select CONVERT(VARCHAR(10),DATEADD(DAY,-3,'" + lastDateMonth + "'),103)").getResultList();
                Vector v3 = (Vector) l4.get(0);
                reportingFridate = v3.get(0).toString();
            } else if (lastDateDay.equals("TUESDAY") || lastDateDay.equals("Tuesday")) {
                List l4 = em.createNativeQuery("select CONVERT(VARCHAR(10),DATEADD(DAY,-4,'" + lastDateMonth + "'),103)").getResultList();
                Vector v3 = (Vector) l4.get(0);
                reportingFridate = v3.get(0).toString();
            } else if (lastDateDay.equals("WEDNESDAY") || lastDateDay.equals("Wednesday")) {
                List l4 = em.createNativeQuery("select CONVERT(VARCHAR(10),DATEADD(DAY,-5,'" + lastDateMonth + "'),103)").getResultList();
                Vector v3 = (Vector) l4.get(0);
                reportingFridate = v3.get(0).toString();
            } else if (lastDateDay.equals("THURSDAY") || lastDateDay.equals("Thursday")) {
                List l4 = em.createNativeQuery("select CONVERT(VARCHAR(10),DATEADD(DAY,-6,'" + lastDateMonth + "'),103)").getResultList();
                Vector v3 = (Vector) l4.get(0);
                reportingFridate = v3.get(0).toString();
            } else {
                List l4 = em.createNativeQuery("select CONVERT(VARCHAR(10),'" + lastDateMonth + "',103)").getResultList();
                Vector v3 = (Vector) l4.get(0);
                reportingFridate = v3.get(0).toString();
            }
//            String reportingFridate1 = reportingFridate.substring(6, 10) + reportingFridate.substring(3, 5) + reportingFridate.substring(0, 2);
            List l5 = em.createNativeQuery(" SELECT SBTL,CONVERT(VARCHAR(12),INTDT,103) FROM HO_CRR_SBTL WHERE INTDT=(SELECT MAX(INTDT) FROM HO_CRR_SBTL WHERE CONVERT(VARCHAR(10),INTDT,112)<= '" + reportingFridate + "')").getResultList();
            Vector v5 = (Vector) l5.get(0);
            amount = Float.parseFloat(v5.get(0).toString());
            effectiveDate = v5.get(1).toString();
            returnList.add(reportingFridate);
            returnList.add(amount);
            returnList.add(effectiveDate);
            return returnList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List hoForm1Onload(String reportingFridate) throws ApplicationException {
        try {
            Float amount;
            String effectiveDate;
            String repFriday;
            List returnnewlist = new ArrayList();
            List l4 = em.createNativeQuery("SELECT SBTL,CONVERT(VARCHAR(12),INTDT,103) FROM HO_CRR_SBTL WHERE INTDT=(SELECT MAX(INTDT) FROM HO_CRR_SBTL WHERE CONVERT(VARCHAR(10),INTDT,112)<='" + reportingFridate + "')").getResultList();
            Vector v3 = (Vector) l4.get(0);
            amount = Float.parseFloat(v3.get(0).toString());
            effectiveDate = v3.get(1).toString();
            List l5 = em.createNativeQuery("SELECT CONVERT(VARCHAR(12),CONVERT(DATETIME,'" + reportingFridate + "'),103)").getResultList();
            Vector v4 = (Vector) l5.get(0);
            repFriday = v4.get(0).toString();
            returnnewlist.add(amount);
            returnnewlist.add(effectiveDate);
            returnnewlist.add(repFriday);
            return returnnewlist;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List hoGetSlrFridayDate(String openingDt) throws ApplicationException {
        try {
            String fridayDt, bankName;
            String bname, address;
            List returnList = new ArrayList();
            List l5 = em.createNativeQuery("SELECT CONVERT(VARCHAR(12),DATEADD(DAY,14,'" + openingDt + "'),103)").getResultList();
            Vector v4 = (Vector) l5.get(0);
            fridayDt = v4.get(0).toString();
            List l6 = em.createNativeQuery("SELECT B1.BANKNAME,B2.CITY FROM BNKADD B1,BRANCHMASTER B2").getResultList();
            Vector v5 = (Vector) l6.get(0);
            bname = v5.get(0).toString();
            address = v5.get(1).toString();
            bankName = bname + "" + address;
            returnList.add(fridayDt);
            returnList.add(bankName);
            return returnList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List retrieveGroupCode(String code) throws ApplicationException {
        return em.createNativeQuery("select distinct(groupcode) from cbs_head_range_master where code='" + code + "'").getResultList();
    }

    public List retrieveSubGroupCode(String code, String groupCode) throws ApplicationException {
        return em.createNativeQuery("select distinct(subgroupcode) from cbs_head_range_master where code ='" + code + "' and "
                + "groupcode = '" + groupCode + "'").getResultList();
    }

    public List retrieveSSGroupCode(String code, String groupCode, String subGroupCode) throws ApplicationException {
        return em.createNativeQuery("select distinct(ssgroupcode) from cbs_head_range_master where code ='" + code + "' and groupcode = '" + groupCode + "' "
                + "and subgroupcode = '" + subGroupCode + "'").getResultList();
    }

    public List getGlCreationCode() throws ApplicationException {
        return em.createNativeQuery("select distinct(code) from cbs_head_range_master").getResultList();
    }

    public String getMaxHeadNumber(String code, String groupCode, String subGroupCode, String subSubGroupCode) throws ApplicationException {
        try {
                List dataList = em.createNativeQuery("select from_range,to_range from cbs_head_range_master where code ='" + code + "' "
                    + "and groupcode = '" + groupCode + "' and subgroupcode ='" + subGroupCode + "' and ssgroupcode = '" + subSubGroupCode + "'").getResultList();
            
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please fill data in CBS HEAD RANGE MASTER for these inputs.");
            }
            
            Vector element = (Vector) dataList.get(0);
            BigInteger fromRange = new BigInteger(element.get(0).toString());
            BigInteger toRange = new BigInteger(element.get(1).toString());

            dataList = em.createNativeQuery("select max(distinct(cast(substring(acno,5,6) as UNSIGNED))) from gltable where cast(substring(acno,5,6) as UNSIGNED) >= " + fromRange + " "
                    + "and cast(substring(acno,5,6) as UNSIGNED) <= " + toRange + "").getResultList();

            element = (Vector) dataList.get(0);
            if (element.get(0) == null || dataList.isEmpty()) {
                return fromRange.toString();
            } else {
//                element = (Vector) dataList.get(0);
                BigInteger maxNumber = new BigInteger(element.get(0).toString());
                if(maxNumber.equals(toRange)){
                    throw new ApplicationException("Series of GL Head is now finished. Please choose another option or Coordinate to helpdesk.");
                } else {
                    maxNumber = maxNumber.add(new BigInteger("1"));
                    return maxNumber.toString();
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List brncodeAndAlphaCodeExcludingCell() throws ApplicationException {
        try {
            return em.createNativeQuery("select brncode,alphacode from branchmaster where alphacode not in('CELL')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String createGlHead(String code, String headNumber, String headName, String headType, String tranType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List brnAndAlphaCodeList = null;
        String result = "true";
        try {
            ut.begin();
            String checkCode = code.substring(0, 3);
            brnAndAlphaCodeList = brncodeAndAlphaCodeExcludingCell();
            for (int i = 0; i < brnAndAlphaCodeList.size(); i++) {
                String tempHeadAccount = headNumber;
                Vector element = (Vector) brnAndAlphaCodeList.get(i);
                String brncode = element.get(0).toString();

                if (brncode.length() < 2) {
                    brncode = ftsRemote.lPading(brncode, 2, "0");
                }

                tempHeadAccount = brncode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + tempHeadAccount + "01";

                int insertResult = em.createNativeQuery("insert into gltable(acno,acname,postflag,msgflag,CrDrFlag) "
                        + "values('" + tempHeadAccount + "','" + headName + "',10,99,'"+ tranType +"')").executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Problem In GL Table Insertion.");
                }

                List checkList = em.createNativeQuery("select * from reconbalan where acno='" + tempHeadAccount + "'").getResultList();
                if (!checkList.isEmpty()) {
                    throw new ApplicationException("Account number is already exist in Reconbalan: " + tempHeadAccount);
                }

                insertResult = em.createNativeQuery("insert into reconbalan(acno,balance,clearedbalance,dt,m01,m02,m03,"
                        + "m04,m05,m06,m07,m08,m09,m10,m11,m12) values('" + tempHeadAccount + "',0,0,'" + ymdFormat.format(new Date()) + "',0,0,0,0,0,"
                        + "0,0,0,0,0,0,0)").executeUpdate();

                if (insertResult <= 0) {
                    throw new ApplicationException("Problem In ReconBalan Insertion.");
                }

                checkList = em.createNativeQuery("select * from cbs_acno_mapping where old_ac_no='" + tempHeadAccount + "' or new_ac_no='" + tempHeadAccount + "'").getResultList();
                if (!checkList.isEmpty()) {
                    throw new ApplicationException("Account number is already exist in CBS ACNO MAPPING: " + tempHeadAccount);
                }

                insertResult = em.createNativeQuery("insert into cbs_acno_mapping(old_ac_no,new_ac_no) values('" + tempHeadAccount + "','" + tempHeadAccount + "')").executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Problem In CBS Acno Mapping Insertion.");
                }

                /**
                 * There should be already data in daybookmast.
                 */
                if (!(checkCode.equalsIgnoreCase("EXP") || checkCode.equalsIgnoreCase("INC"))) {
                    /**
                     * Daybookmast
                     */
                    checkList = em.createNativeQuery("select * from daybookmast where brncode='" + brncode + "' and glhead='" + tempHeadAccount.substring(2, 10) + "'").getResultList();
                    if (!checkList.isEmpty()) {
                        throw new ApplicationException("GL Head is already exist in daybookmast: " + tempHeadAccount.substring(2, 10));
                    }
                    List snoList = em.createNativeQuery("select ifnull(max(groupcode),0)+1 from daybookmast where brncode='" + brncode + "'").getResultList();

                    Vector snoVector = (Vector) snoList.get(0);
                    int sno = Integer.parseInt(snoVector.get(0).toString());

                    insertResult = em.createNativeQuery("insert into daybookmast(glhead,acctcode,description,optstatus,groupcode,subgroupcode,dbactype,frglhead,toglhead,LastupdatedBy,Trantime,Sno,brncode) VALUES('" + tempHeadAccount.substring(2, 10) + "','','" + headName + "',0," + sno + ",1,'','" + tempHeadAccount.substring(2, 10) + "','" + tempHeadAccount.substring(2, 10) + "','',NOW(),0,'" + brncode + "')").executeUpdate();
                    if (insertResult <= 0) {
                        throw new ApplicationException("Problem In daybookmast Insertion.");
                    }
                }
            }

            /**
             * There should be already data in plmast, glbmast.
             */
            headNumber = "06" + headNumber;
            if (checkCode.equalsIgnoreCase("EXP") || checkCode.equalsIgnoreCase("INC")) {
                List checkList = em.createNativeQuery("select * from plmast where code='" + headNumber + "'").getResultList();
                if (!checkList.isEmpty()) {
                    throw new ApplicationException("GL Head is already exist in PL MAST: " + headNumber);
                }
                List snoList = em.createNativeQuery("select ifnull(max(sno),0)+1 from plmast").getResultList();
                Vector snoVector = (Vector) snoList.get(0);
                int sno = Integer.parseInt(snoVector.get(0).toString());

                if (checkCode.equalsIgnoreCase("EXP")) {
                    checkList = em.createNativeQuery("select ifnull(max(groupcode),0)+1, ifnull(max(subgroupcode),0)+1 from plmast where classification='E'").getResultList();

                    Vector checkVector = (Vector) checkList.get(0);
                    Integer gcode = Integer.parseInt(checkVector.get(0).toString());
                    Integer sGcode = Integer.parseInt(checkVector.get(1).toString());

                    int insertResult = em.createNativeQuery("insert into plmast(sno,groupcode,subgroupcode,code,description,classification,mode,lastupdatedby,trantime,paytype,staffcategory) values(" + sno + "," + gcode + "," + sGcode + ",'" + headNumber + "','" + headName + "','E','','MIS',NOW(),0,'')").executeUpdate();
                    if (insertResult <= 0) {
                        throw new ApplicationException("Problem In PL MAST Insertion.");
                    }
                } else if (checkCode.equalsIgnoreCase("INC")) {
                    checkList = em.createNativeQuery("select ifnull(max(groupcode),0)+1, ifnull(max(subgroupcode),0)+1 from plmast where classification='I'").getResultList();

                    Vector checkVector = (Vector) checkList.get(0);
                    Integer gcode = Integer.parseInt(checkVector.get(0).toString());
                    Integer sGcode = Integer.parseInt(checkVector.get(1).toString());

                    int insertResult = em.createNativeQuery("insert into plmast(sno,groupcode,subgroupcode,code,description,classification,mode,"
                            + "lastupdatedby,trantime,paytype,staffcategory) values(" + sno + "," + gcode + "," + sGcode + ",'" + headNumber + "','" + headName + "','I','','MIS',NOW(),0,'')").executeUpdate();
                    if (insertResult <= 0) {
                        throw new ApplicationException("Problem In PL MAST Insertion.");
                    }
                }
            } else {
                /**
                 * GLBMast
                 */
                List checkList = em.createNativeQuery("select * from glbmast where code='" + headNumber + "'").getResultList();
                if (!checkList.isEmpty()) {
                    throw new ApplicationException("GL Head is already exist in GLB MAST: " + headNumber);
                }

                List snoList = em.createNativeQuery("select ifnull(max(sno),0) + 1 from glbmast").getResultList();
                Vector snoVector = (Vector) snoList.get(0);
                int sno = Integer.parseInt(snoVector.get(0).toString());

                if (checkCode.equalsIgnoreCase("ASS")) {
                    checkList = em.createNativeQuery("select ifnull(max(groupcode),0) from glbmast where glbactype='A'").getResultList();
                    snoVector = (Vector) checkList.get(0);
                    Integer gCode = Integer.parseInt(snoVector.get(0).toString());

                    checkList = em.createNativeQuery("select ifnull(max(subgroupcode),0) + 1 from glbmast where glbactype='A' and groupcode=" + gCode + "").getResultList();
                    snoVector = (Vector) checkList.get(0);
                    Integer sGCode = Integer.parseInt(snoVector.get(0).toString());

                    int insertResult = em.createNativeQuery("insert into glbmast(sno,groupcode,subgroupcode,code,description,actype,glbactype,actstatus,lastupdatedby,trantime,status,brncode) values(" + sno + "," + gCode + "," + sGCode + ",'" + headNumber + "','" + headName + "','','A',0,'',NOW(),0,'90')").executeUpdate();
                    if (insertResult <= 0) {
                        throw new ApplicationException("Problem In glbmast Insertion.");
                    }
                } else if (checkCode.equalsIgnoreCase("LIA")) {
                    checkList = em.createNativeQuery("select ifnull(max(groupcode),0) from glbmast where glbactype='L'").getResultList();
                    snoVector = (Vector) checkList.get(0);
                    Integer gCode = Integer.parseInt(snoVector.get(0).toString());

                    checkList = em.createNativeQuery("select ifnull(max(subgroupcode),0) + 1 from glbmast where glbactype='L' and groupcode=" + gCode + "").getResultList();
                    snoVector = (Vector) checkList.get(0);
                    Integer sGCode = Integer.parseInt(snoVector.get(0).toString());

                    int insertResult = em.createNativeQuery("insert into glbmast(sno,groupcode,subgroupcode,code,description,actype,glbactype,actstatus,lastupdatedby,trantime,status,brncode) values(" + sno + "," + gCode + "," + sGCode + ",'" + headNumber + "','" + headName + "','','L',0,'',NOW(),0,'90')").executeUpdate();
                    if (insertResult <= 0) {
                        throw new ApplicationException("Problem In glbmast Insertion.");
                    }
                }
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
        return result;
    }
}
