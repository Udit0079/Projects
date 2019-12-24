/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.master;

import com.cbs.exception.ApplicationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

/**
 *
 * @author admin
 */
@Stateless(mappedName = "DailyMasterFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class DailyMasterFacade implements DailyMasterFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;

    /******
     * Start Code DailyGLBMasterBean
     * 
     * 
     *************/
    public List getTableDetails() throws ApplicationException {
        List result = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctcode from accounttypemaster where acctnature not in('PO')");
            result = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List subGroupCode(String groupCode, String glbAccType) throws ApplicationException {
        try {
            Query select = em.createNativeQuery("select ifnull(max(subgroupcode),'') from ho_balance_mast where groupcode=" + Integer.parseInt(groupCode) + " and glbactype='" + glbAccType + "'");
            List list = select.getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List subSubGroupCode(String groupCode, String subGroupCode, String glbAccType) throws ApplicationException {
        try {
            Query select2 = em.createNativeQuery("select ifnull(max(subsubgroupcode),'') from ho_balance_mast where groupcode=" + Integer.parseInt(groupCode) + " and glbactype='" + glbAccType + "' and subgroupcode=" + Integer.parseInt(subGroupCode) + "");
            List list2 = select2.getResultList();
            return list2;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List subSubSubGroupCode(String groupCode, String subGroupCode, String glbAccType, String subSubGroupCode) throws ApplicationException {
        try {
            Query select4 = em.createNativeQuery("select ifnull(max(subsubsubgroupcode),'') from ho_balance_mast where groupcode=" + Integer.parseInt(groupCode) + " and glbactype='" + glbAccType + "' and subgroupcode=" + Integer.parseInt(subGroupCode) + " and SubSubGroupCode=" + Integer.parseInt(subSubGroupCode) + "");
            List list4 = select4.getResultList();
            return list4;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectdescription(String groupCode, String subGroupCode, String subSubGroupCode) throws ApplicationException {
        try {
            Query select3 = em.createNativeQuery("Select DESCRIPTION From ho_balance_mast Where groupcode=" + Float.parseFloat(groupCode) + " AND SUBGROUPCODE=" + Float.parseFloat(subGroupCode) + " and subsubgroupcode=" + Float.parseFloat(subSubGroupCode) + "");
            List list3 = select3.getResultList();
            return list3;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getSubGrpDesc(String acno) throws ApplicationException {
        try {
            List list2 = new ArrayList();
            Query q = em.createNativeQuery("select acname from gltable where acno='" + acno + "'");
            list2 = q.getResultList();
            return list2;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveData(BalanceMastGrid grid, String userName) throws ApplicationException {
        String result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query select = em.createNativeQuery("select * from ho_balance_mast where GroupCode='" + Integer.parseInt(grid.getGroupCode()) + "'and SubGroupCode='" + Integer.parseInt(grid.getSubGroupCode()) + "' and SubSubGroupCode='" + Integer.parseInt(grid.getSubSubGroupCode()) + "' and SubSubSubGroupCode='" + Integer.parseInt(grid.getSubSubSubGroupCode()) + "'");
            List list = select.getResultList();
            if (list.isEmpty()) {
                Query insert = em.createNativeQuery("insert into ho_balance_mast(GroupCode,SubGroupCode,SubSubGroupCode,Description,SubGLCode,"
                        + "SubDescription,SubSubGLCode,SubSubDescription,AcType,GlbActype,ActStatus,LastupdatedBy,Trantime,SubSubSubGroupCode,"
                        + "SubSubSubGLCode,SubSubSubDescription) values('" + Integer.parseInt(grid.getGroupCode()) + "','" + Integer.parseInt(grid.getSubGroupCode()) + "',"
                        + "'" + Integer.parseInt(grid.getSubSubGroupCode()) + "','" + grid.getDescription() + "','" + grid.getSubGLCode() + "',"
                        + "'" + grid.getSubDescription() + "','" + grid.getSubSubGLCode() + "','" + grid.getSubSubDescription() + "','" + grid.getAcType() + "',"
                        + "'" + grid.getGlbActype() + "','" + Integer.parseInt(grid.getActStatus()) + "','" + userName + "',now(),"
                        + "'" + Integer.parseInt(grid.getSubSubSubGroupCode()) + "','" + grid.getSubSubSubGLCode() + "','" + grid.getSubSubSubDescription() + "')");
                int insertResult = insert.executeUpdate();
                if (insertResult <= 0) {
                    ut.rollback();
                    return result = "Error!! Data not been saved.";
                }
            } else {
                ut.rollback();
                return result = "Error!! Data already exists.";
            }

            ut.commit();
            return result = "Data has been saved.";
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
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public List getBalanceMast() throws ApplicationException {
        List resultSet = new ArrayList();
        try {
            resultSet = em.createNativeQuery("select * from ho_balance_mast").getResultList();
        } catch (Exception e) {
            throw new ApplicationException();
        }
        return resultSet;
    }

    public String deleteBalanceMast(int groupCode, int subGroupCode, int subSubGroupCode, int subSubSubSubGroupCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            List resultList = em.createNativeQuery("select * from ho_balance_mast where groupcode=" + groupCode + " and subgroupcode =" + subGroupCode + " and subsubgroupcode=" + subSubGroupCode + " and subsubsubgroupcode=" + subSubSubSubGroupCode + "").getResultList();
            if (resultList.isEmpty()) {
                return "Error!! No such row for deletion";
            }
            ut.begin();
            int rowAffected = em.createNativeQuery("delete from ho_balance_mast where groupcode=" + groupCode + " and subgroupcode =" + subGroupCode + " and subsubgroupcode=" + subSubGroupCode + " and subsubsubgroupcode=" + subSubSubSubGroupCode + "").executeUpdate();
            if (rowAffected > 0) {
                ut.commit();
                return "Deletion successful";
            } else {
                ut.rollback();
                return "Error in deletion";
            }
        } catch (Exception e) {
            throw new ApplicationException();
        }
    }

    /*********
     * 
     * 
     * 
     *  end DailyGLBMasterBean
     * 
     * Start DailyGLBMasterUpdateBean
     ***********/
    /*public List selectGroupCode(String type) throws ApplicationException {
        try {
            List selectGroupList = em.createNativeQuery("select distinct groupcode from ho_balance_mast where GlbActype='" + type + "'").getResultList();
            return selectGroupList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectsubGroupCode(String type, String groupCode) throws ApplicationException {
        try {
            List selectSubGroupList = em.createNativeQuery("select distinct subgroupcode from ho_balance_mast where GlbActype='" + type + "' and groupcode='" + groupCode + "'").getResultList();
            return selectSubGroupList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectsubSubGroupCode(String type, String groupCode, String subGroupCode) throws ApplicationException {
        try {
            List selectSubSubGroupList = em.createNativeQuery("select distinct subsubgroupcode from ho_balance_mast where GlbActype='" + type + "' and groupcode='" + groupCode + "' and subgroupcode='" + subGroupCode + "'").getResultList();
            return selectSubSubGroupList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectsubSubSubGroupCode(String type, String groupCode, String subGroupCode, String subSubGroupCode) throws ApplicationException {
        try {
            List selectSubSubSubGroupList = em.createNativeQuery("select distinct subsubsubgroupcode from ho_balance_mast where GlbActype='" + type + "' and groupcode='" + groupCode + "' and subgroupcode='" + subGroupCode + "' and subsubgroupcode='" + subSubGroupCode + "'").getResultList();
            return selectSubSubSubGroupList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }*/

    /*public List selectDescription(String type, String groupCode) throws ApplicationException {
        try {
            List selectGroupDesc = em.createNativeQuery("select Description from ho_balance_mast where GlbActype='" + type + "' and GroupCode='" + groupCode + "'").getResultList();
            return selectGroupDesc;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List subCodeDesc(String groupCode, String subGroupCode) throws ApplicationException {
        try {
            List selectSubCodeDesc = em.createNativeQuery("select SubGLCode as SubGLCode,SubDescription from ho_balance_mast where GroupCode=" + Integer.parseInt(groupCode) + " and SubGroupCode=" + Integer.parseInt(subGroupCode) + "").getResultList();
            return selectSubCodeDesc;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List subSubCodeDesc(String groupCode, String subGroupCode, String subSubGroupCode) throws ApplicationException {
        try {
            List selectSubSubCodeDesc = em.createNativeQuery("select SubSubGLCode as SubSubGLCode,SubSubDescription from ho_balance_mast where GroupCode=" + Integer.parseInt(groupCode) + " and SubGroupCode=" + Integer.parseInt(subGroupCode) + " and SubSubGroupCode=" + subSubGroupCode + "").getResultList();
            return selectSubSubCodeDesc;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List subSubSubCodeDesc(String groupCode, String subGroupCode, String subSubGroupCode, String subSubSubGroupCode) throws ApplicationException {
        try {
            List selectSubSubSubCodeDesc = em.createNativeQuery("select SubSubSubGLCode as SubSubSubGLCode,SubSubSubDescription from ho_balance_mast where GroupCode=" + Integer.parseInt(groupCode) + " and SubGroupCode=" + Integer.parseInt(subGroupCode) + " and SubSubGroupCode=" + subSubGroupCode + " and SubSubSubGroupCode=" + Integer.parseInt(subSubSubGroupCode) + "").getResultList();
            return selectSubSubSubCodeDesc;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }*/

    public String updateRecord(List arraylist, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String result = null;
        try {
            ut.begin();
            for (int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7, i = 8, j = 9, k = 10, l = 11, m = 12, n = 13; a < arraylist.size(); a = a + 14, b = b + 14, c = c + 14, d = d + 14, e = e + 14, f = f + 14, g = g + 14, h = h + 14, i = i + 14, j = j + 14, k = k + 14, l = l + 14, m = m + 14, n = n + 14) {
                Query update = em.createNativeQuery("update ho_balance_mast set Description='" + arraylist.get(f).toString() + "',SubGLCode='" + arraylist.get(d).toString() + "',SubDescription='" + arraylist.get(j).toString() + "',SubSubGLCode='" + arraylist.get(e).toString() + "',SubSubDescription='" + arraylist.get(k).toString() + "',SubSubSubGLCode='" + arraylist.get(m).toString() + "',SubSubSubDescription='" + arraylist.get(n).toString() + "',LastupdatedBy='" + userName + "',Trantime='" + sdf.format(date) + "' where GroupCode='" + arraylist.get(a).toString() + "' and SubGroupCode='" + arraylist.get(b).toString() + "' and SubSubGroupCode='" + arraylist.get(c).toString() + "' and SubSubSubGroupCode='" + arraylist.get(l).toString() + "'");
                int updateResult = update.executeUpdate();
                if (updateResult <= 0) {
                    ut.rollback();
                    return result = "Error in updation";
                }
            }
            ut.commit();
            return result = "Data has been updated successfully.";
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
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    /*********
     * 
     * end DailyGLBMasterUpdateBean
     * Start HoGlbMasterBean 
     ***********/
    public List codeCombo() throws ApplicationException {
        List code = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctcode from accounttypemaster where acctnature  in('PO')");
            code = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return code;
    }

    public List acctTypeCombo() throws ApplicationException {
        List actype = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctcode from accounttypemaster where acctnature not in('PO')");
            actype = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return actype;
    }

    public List gridDetail(String brCode) throws ApplicationException {
        List gd = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select Sno,GroupCode,SubGroupCode,code,Description,AcType,GlbActype,ActStatus from ho_glbmast order by GroupCode,SubGroupCode");
            gd = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return gd;
    }

    public List acName(String acno) throws ApplicationException {
        List acn = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acname from gltable where acno = '" + acno + "'");
            acn = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return acn;
    }

    public List acNameForBankPurpose(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select acname from gltable where substring(acno,3,8) = '" + acno + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List acDesc(String acType) throws ApplicationException {
        List acd = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctdesc from accounttypemaster where acctcode = '" + acType + "'");
            acd = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return acd;
    }

    public List subGrCodeChk(String glbAcType, int grCode, int subGrCode) throws ApplicationException {
        List chk = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select groupcode from ho_glbmast where glbactype='" + glbAcType + "' and groupcode=" + grCode + " and subgroupcode=" + subGrCode + "");
            chk = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return chk;
    }

    public String deleteRecord(String brCode, String sno, String glbAcType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            Query deleteQuery = em.createNativeQuery("delete from ho_glbmast where sno=" + sno + " and glbactype='" + glbAcType + "'");
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
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String saveRecord(String brCode, int grCode, int subGrCode, String codeAcNo, String description, String actype, String glbAcType, int acStatus, String username) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            int sNo;
            List chkList = em.createNativeQuery("select subgroupcode from ho_glbmast where glbactype='" + glbAcType + "' and groupcode=" + grCode + " and subgroupcode=" + subGrCode + " ").getResultList();
            if (!chkList.isEmpty()) {
                ut.rollback();
                return "Sub Group Code Already Exist.";
            }
            List snoList = em.createNativeQuery("select coalesce(max(sno),0) from ho_glbmast ").getResultList();
            if (snoList.isEmpty() || snoList.isEmpty()) {
                ut.rollback();
                return "Error In Getting Serial No.";
            } else {
                Vector ele = (Vector) snoList.get(0);
                sNo = Integer.parseInt(ele.get(0).toString()) + 1;
            }
            Query insertQuery = em.createNativeQuery("insert into ho_glbmast(sno,GroupCode,SubGroupCode,code,Description,AcType,GlbActype,ActStatus,LastupdatedBy,Trantime)values(" + sNo + "," + grCode + "," + subGrCode + ",'" + codeAcNo + "','" + description + "','" + actype + "','" + glbAcType + "'," + acStatus + ",'" + username + "',now())");
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
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    /******
     * 
     * 
     * 
     * Start code HoReconcileEntryBean
     **************/
    public String getTableDetailsHoreCouncile() throws ApplicationException {
        String result = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query insertHoOriginEntry = em.createNativeQuery("insert into ho_originating_entry select OriginBranch,OriginDt,InstrumentType,Instno,Amount,Dt,RespondBranch,Entry_Type,Ty,RespondDt from ho_extract where Entry_Type='o'");
            int resultHoOriginEntry = insertHoOriginEntry.executeUpdate();
            if (resultHoOriginEntry <= 0) {
                ut.rollback();
                result = "Data Could Not inserted..";
            } else {
                ut.commit();
                result = "Data has been inserted.";
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
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
        return result;
    }

    public String getTableDetails1() throws ApplicationException {
        String result = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query insertHoOriginEntry = em.createNativeQuery("insert into ho_responding_entry select RespondBranch,RespondDt,InstrumentType,Instno,Amount,Dt,OriginBranch,Entry_Type,Ty,OriginDt from ho_extract where Entry_Type='r'");

            int resultHoOriginEntry = insertHoOriginEntry.executeUpdate();

            if (resultHoOriginEntry <= 0) {
                ut.rollback();
                result = "Data Could Not Be Saved For Responding Entry Type..";

            } else {
                ut.commit();
                result = "Data has been saved For Responding Entry Type";
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
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }

        return result;
    }

    public String getTableDetails2() throws ApplicationException {

        String result = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query insertHoOriginEntry = em.createNativeQuery("insert into ho_reconsile_entry select O.Originating_Branch,O.Origin_Dt,O.Instrument_Type,O.instrument_no,O.Instrument_Amount,O.Instrument_date,O.Responding_Branch,O.entry_type,O.ty,r.Responding_Branch,r.Responded_Dt,r.Instrument_Type,r.instrument_no,r.Instrument_Amount,r.Instrument_date,r.Originating_Branch,r.entry_type,r.ty from ho_originating_entry O,ho_responding_entry r where O.Originating_Branch=r.Responding_Branch and O.Responding_Branch=r.Originating_Branch and O.Origin_Dt=r.origin_Dt and O.Instrument_Amount=r.Instrument_Amount");
            int resultHoOriginEntry = insertHoOriginEntry.executeUpdate();

            if (resultHoOriginEntry <= 0) {
                ut.rollback();
                result = "Not Successful..";
            } else {

                List postFlag = em.createNativeQuery("select O.Originating_Branch,O.Responding_Branch,O.Instrument_Amount,O.Origin_Dt,O.Instrument_Type,O.instrument_no,O.Instrument_date,O.entry_type,O.ty,r.Responding_Branch,r.Responded_Dt,r.Instrument_Type,r.instrument_no,r.Instrument_Amount,r.Instrument_date,r.Originating_Branch,r.entry_type,r.ty from ho_originating_entry O,ho_responding_entry r where O.Originating_Branch=r.Responding_Branch and O.Responding_Branch=r.Originating_Branch and O.Origin_Dt=r.origin_Dt and O.Instrument_Amount=r.Instrument_Amount").getResultList();

                for (int i = 0; i < postFlag.size(); i++) {

                    Vector postFlags = (Vector) postFlag.get(i);

                    String ORIGINATING_BRANCH = postFlags.get(0).toString();

                    String RESPONDING_BRANCH = postFlags.get(1).toString();

                    float INSTRUMENT_AMOUNT = Float.parseFloat(postFlags.get(2).toString());

                    String ORIGIN_DT = postFlags.get(3).toString();

                    ORIGIN_DT = ORIGIN_DT.substring(0, 4) + ORIGIN_DT.substring(5, 7) + ORIGIN_DT.substring(8, 10);

                    Query delete1 = em.createNativeQuery("delete from ho_originating_entry where ORIGINATING_BRANCH='" + ORIGINATING_BRANCH + "'  AND RESPONDING_BRANCH='" + RESPONDING_BRANCH + "' AND INSTRUMENT_AMOUNT=" + INSTRUMENT_AMOUNT + " and Origin_Dt='" + ORIGIN_DT + "'");
                    int result1 = delete1.executeUpdate();

                    if (result1 <= 0) {
                        ut.rollback();
                        result = "Not Successful..";
                    } else {
                        Query delete2 = em.createNativeQuery("delete from ho_responding_entry where Responding_Branch='" + ORIGINATING_BRANCH + "'  AND Originating_Branch='" + RESPONDING_BRANCH + "' AND INSTRUMENT_AMOUNT=" + INSTRUMENT_AMOUNT + " and Origin_Dt='" + ORIGIN_DT + "'");
                        int result2 = delete2.executeUpdate();
                        if (result2 <= 0) {
                            ut.rollback();
                            result = "Not Successful..";
                        } else {
                            ut.commit();
                            result = "Successful Transaction";
                        }
                    }

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
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }

        return result;
    }
    /******
     * 
     * 
     * 
     * End code HoReconcileEntryBean
     **************/
    
    public List getGlbDtl(String type) throws ApplicationException{
        List glbLst = new ArrayList();
        try{
            Query selectQuery = em.createNativeQuery("select groupcode,description,subgroupcode,subsubgroupcode,subsubsubgroupcode,subglcode,subdescription,subsubglcode,subsubdescription,subsubsubglcode,subsubsubdescription,actype,actstatus,glbactype from ho_balance_mast where glbactype='" + type + "'");
            glbLst = selectQuery.getResultList();
        }catch(Exception e){
            e.getMessage();
        }
        return glbLst;        
    }
}
