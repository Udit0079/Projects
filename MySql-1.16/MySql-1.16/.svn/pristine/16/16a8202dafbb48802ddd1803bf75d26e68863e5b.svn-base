package com.cbs.facade.master;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.AtmCardMappGrid;
import com.cbs.dto.FidilityTablePojo;
import com.cbs.exception.ApplicationException;
import java.text.SimpleDateFormat;
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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "GeneralMasterFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class GeneralMasterFacade implements GeneralMasterFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public List NatureAccountTypeMaster() throws ApplicationException {
        try {
            return em.createNativeQuery("Select REF_CODE,REF_DESC from cbs_ref_rec_type where REF_REC_NO='212' and ref_code not in ('PO','OF') order by ref_code").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List glValueAccountTypeMaster() throws ApplicationException {
        try {
            return em.createNativeQuery("Select Distinct AcctCode From accounttypemaster Where AcctNature='PO'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List checkAcctCodeAccountTypeMaster(String acNature, String actCode) throws ApplicationException {
        try {
            List l1 = em.createNativeQuery("Select * From accounttypemaster Where AcctCode='" + actCode + "' and  AcctNature='" + acNature + "'").getResultList();
            if (l1.isEmpty()) {
                l1 = null;
            }
            return l1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List checkAcctCode1AccountTypeMaster(String actCode) throws ApplicationException {
        try {
            List l2 = em.createNativeQuery("Select * From accounttypemaster Where AcctCode='" + actCode + "'").getResultList();
            return l2;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String saveAccountTypeMaster(String AcctNature, String AcctCode, String acctDesc, String GLHead, String GLHeadInt, String GLHeadProv, int MinBal, float MinBalChq, String ChqSrNo, float MinInt, float StaffInt, float Penalty, float MinBalCharge, String ProductCode, String OFAcctNature, String Enterby, String GLHeaduri, String accountType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query insertQuery = em.createNativeQuery("Insert Into accounttypemaster (AcctNature,AcctCode,acctDesc,GLHead,GLHeadInt,GLHeadProv,MinBal,MinBal_Chq,ChqSrNo,MinInt,StaffInt,Penalty,MinBalCharge,ProductCode,OFAcctNature,Enterby,GLHeaduri,AcctType,ProvAppOn)"
                    + "values ('" + AcctNature + "','" + AcctCode + "','" + acctDesc + "','" + GLHead + "','" + GLHeadInt + "','" + GLHeadProv + "',"
                    + MinBal + "," + MinBalChq + ",'" + ChqSrNo + "'," + MinInt + "," + StaffInt + "," + Penalty + "," + MinBalCharge + ",'" + ProductCode
                    + "','" + OFAcctNature + "','" + Enterby + "','" + GLHeaduri + "','" + accountType + "','')");
            int var = insertQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "Data Saved Successfully";
            } else {
                ut.rollback();
                return "Some Problem In Saving Data";
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

    @Override
    public String UpdateAccountTypeMaster(String txtDesc, String glHead, String glheadint, String GLheadProv, Integer txtMinBal, Float txtMinBalChq, String txtChqSrno, Float txtMinInt, Float txtStaffROI, Float txtpenalty, Float txtBalChg, String txtPcode, String cmbOFNat, String uname, String lastUpdateDt, String GlHeadINC, String cmbNature, String txtAcctCode) throws ApplicationException {
        String message = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q1 = em.createNativeQuery("insert into accounttypemaster_hist(AcctCode,AcctDesc,LOANAUTH,MinBal,MinInt,MaxLendInt,LastUpdateDt,LastUpdateBy,ChequeBounceAmt,penalty,"
                    + "minbalcharge,chqsrno,enterby,acctNature,GLHead,GLHeadInt,ofAcctnature,staffint,minbal_chq,GLHeadProv,ProductCode,SNO,trantime,GLHeaduri,acctType) "
                    + "select AcctCode,AcctDesc,LOANAUTH,MinBal,MinInt,MaxLendInt,LastUpdateDt,LastUpdateBy,ChequeBounceAmt,penalty,cast(minbalcharge as SIGNED),chqsrno,"
                    + "enterby,acctNature,GLHead,GLHeadInt,ofAcctnature,staffint,minbal_chq,GLHeadProv,ProductCode,SNO,now(),GLHeaduri,acctType from  accounttypemaster Where "
                    + "AcctNature='" + cmbNature + "' and AcctCode='" + txtAcctCode + "'");
            int int1 = q1.executeUpdate();
            if (int1 > 0) {
                Query q2 = em.createNativeQuery("Update accounttypemaster Set acctDesc='" + txtDesc + "',GLHead='" + glHead + "',GLHeadInt='"
                        + glheadint + "',GLHeadProv='" + GLheadProv + "',MinBal ='" + txtMinBal + "',MinBal_Chq ='" + txtMinBalChq + "', ChqSrNo='"
                        + txtChqSrno + "',MinInt =" + txtMinInt + ",StaffInt ='" + txtStaffROI + "' , Penalty=" + txtpenalty + ",MinBalCharge ='"
                        + txtBalChg + "',ProductCode ='" + txtPcode + "', OFAcctNature ='" + cmbOFNat + "' ,LastUpdateBy='" + uname + "',LastUpdateDt='"
                        + lastUpdateDt + "',GLHeaduri='" + GlHeadINC + "' Where AcctNature='" + cmbNature + "' and AcctCode='" + txtAcctCode + "'");
                int1 = q2.executeUpdate();
                if (int1 > 0) {
                    ut.commit();
                    message = "Transaction Has Been Successfully Updated...";
                } else {
                    ut.rollback();
                    message = "Sorry,Updation Not Successful..";
                }
            } else {
                ut.rollback();
                message = "Sorry,Updation Not Successful..";
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
        return message;
    }

    @Override
    public List ReportAccountTypeMaster() throws ApplicationException {
        try {
            List q1 = em.createNativeQuery("Select * From accounttypemaster Order By sno,AcctNature, AcctCode").getResultList();
            if (q1.isEmpty()) {
                q1 = null;
            }
            return q1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List tableDataHolidayMarkingRegister(String brnCode) throws ApplicationException {
        try {
            List year = em.createNativeQuery("select min(F_Year) from yearend where yearendflag='N' and brncode='" + brnCode + "'").getResultList();
            Vector yearLst = (Vector) year.get(0);
            String years = yearLst.get(0).toString();
            String date = years + "01" + "01";
            return em.createNativeQuery("SELECT Date_format(HOLIDAYDATE,'%d/%m/%Y'),HOLIDAYDESC FROM lstholidays Where HolidayDate >='" + date + "' and brncode='" + brnCode + "' order by HolidayDate").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String saveHolidayMarkingData(String date, String description, String brnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var2 = 0;
            int updateQuery = 0;
            List holidayDate = em.createNativeQuery("SELECT HOLIDAYDATE FROM lstholidays WHERE HOLIDAYDATE='" + date + "' and brncode='" + brnCode + "'").getResultList();
            if (!holidayDate.isEmpty()) {
                ut.rollback();
                return "This Holiday Date Has been already Marked On This Date and on this branch.";
            }
            List instrNo = em.createNativeQuery("SELECT HOLIDAYDATE FROM lstholidays WHERE HOLIDAYDATE='" + date + "' and holidaydesc='" + description + "' and brncode='" + brnCode + "'").getResultList();
            if (!holidayDate.isEmpty()) {
                ut.rollback();
                return "This holiday has been already Marked on this date and on this branch.";
            }

            // Add By Athar
            List dayBeginFlagList = em.createNativeQuery("select DayEndFlag,Daybeginflag from bankdays where date = '" + date + "' and brncode = '" + brnCode + "'").getResultList();
            if (!dayBeginFlagList.isEmpty()) {
                Vector dayVec = (Vector) dayBeginFlagList.get(0);
                String dayEndFlag = dayVec.get(0).toString();
                String dayBeginFlag = dayVec.get(1).toString();
                if (dayEndFlag.equalsIgnoreCase("N") && dayBeginFlag.equalsIgnoreCase("Y")) {
                    ut.rollback();
                    return "Holiday marking is not possible after Day Begin !";
                }
            }

            List dateChk = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY,'" + date + "',CURDATE())").getResultList();
            int dayDiff = Integer.parseInt(((Vector) dateChk.get(0)).get(0).toString());
            if (dayDiff > 0) {
                List yearEndList = em.createNativeQuery("select Daybeginflag,Dayendflag1 from bankdays where Date='" + date + "' and Brncode='" + brnCode + "'").getResultList();
                Vector yearLst = (Vector) yearEndList.get(0);
                String dayEndFlag = yearLst.get(0).toString();
                String dayEndFlag1 = yearLst.get(1).toString();
                if ((dayEndFlag.equalsIgnoreCase("N")) && (dayEndFlag1.equalsIgnoreCase("N"))) {
                    Query insertQuery2 = em.createNativeQuery("insert into lstholidays(HOLIDAYDESC,HOLIDAYDATE,brncode)"
                            + "values (" + "'" + description + "'"
                            + "," + "'" + date + "'"
                            + "," + "'" + brnCode + "'"
                            + ")");
                    var2 = insertQuery2.executeUpdate();
                    updateQuery = em.createNativeQuery("Update bankdays Set DayEndFlag ='Y',Daybeginflag='H',Dayendflag1='N',Cashclose='Y' Where Brncode = '" + brnCode + "' AND Date='" + date + "'").executeUpdate();
                } else {
                    ut.rollback();
                    return "Holidays could not be Marked";
                }
            } else {
                Query insertQuery2 = em.createNativeQuery("insert into lstholidays(HOLIDAYDESC,HOLIDAYDATE,brncode)"
                        + "values (" + "'" + description + "'"
                        + "," + "'" + date + "'"
                        + "," + "'" + brnCode + "'"
                        + ")");
                var2 = insertQuery2.executeUpdate();
                updateQuery = em.createNativeQuery("Update bankdays Set DayEndFlag ='Y',Daybeginflag='H',Dayendflag1='N',Cashclose='Y' Where Brncode = '" + brnCode + "' AND Date='" + date + "'").executeUpdate();
            }
            if ((var2 > 0) && (updateQuery > 0)) {
                ut.commit();
                return "Holidays Marked Successfully.";
            } else {
                ut.rollback();
                return "Holidays could not be Marked";
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

    @Override
    public String deleteHolidayMarkingData(String date, String description, String flag, String brnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var3 = 0;
            int var4 = 0;
            int deleteQ = 0;
            if (flag.equals("d")) {
                List isholidayExits = em.createNativeQuery("SELECT HOLIDAYDATE FROM lstholidays WHERE HOLIDAYDATE='" + date + "' and brncode='" + brnCode + "'").getResultList();
                if (isholidayExits.isEmpty()) {
                    ut.rollback();
                    return "This holiday date already Unmarked for this branch.";
                }
                Query deleteQuery = em.createNativeQuery("DELETE FROM lstholidays WHERE HOLIDAYDESC='" + description + "' and HOLIDAYDATE='" + date + "' and brncode='" + brnCode + "'");
                var3 = deleteQuery.executeUpdate();

                int var5 = em.createNativeQuery("UPDATE cbs_bankdays SET DayEndFlag ='N',Daybeginflag='N' WHERE DATE ='" + date + "'").executeUpdate();

                deleteQ = em.createNativeQuery("Update bankdays Set DayEndFlag ='Y',Daybeginflag='N',Dayendflag1='N',Cashclose='Y' Where Brncode = '" + brnCode + "' AND Date='" + date + "'").executeUpdate();
            }
            if (flag.equals("u")) {
                Query updateQuery = em.createNativeQuery("UPDATE lstholidays SET HOLIDAYDESC='" + description + "',HOLIDAYDATE='" + date + "' WHERE HOLIDAYDATE='" + date + "' and brncode='" + brnCode + "'");
                var4 = updateQuery.executeUpdate();
            }
            if ((var3 > 0) && (deleteQ > 0)) {
                ut.commit();
                return "Data deleted Successfully for this branch.";
            } else if (var4 > 0) {
                ut.commit();
                return "Data Updated Successfully for this branch.";
            } else {
                ut.rollback();
                return "Please Fill Correct Data";
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

    @Override
    public List accountNatureLegalDocumentMaster() throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT DISTINCT ACCTNATURE FROM accounttypemaster WHERE ACCTNATURE IN ('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.TERM_LOAN + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List tableDetailsLegalDocumentMaster(String acType) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT Actype,Legaldocument,enterby,code,date_format(trantime,'%d/%m/%Y') AS trantime FROM legaldocuments_actype_master where Actype='" + acType + "'");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    @Override
    public String saveLegalDocumentData(String acType, String legalDocument, String user) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List lastUpdate = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N'").getResultList();
            Vector ltUpdate = (Vector) lastUpdate.get(0);
            String currentDate = ltUpdate.get(0).toString();
            List codes = em.createNativeQuery("SELECT coalesce(max(code),0)+1 FROM legaldocuments_actype_master WHERE ACTYPE ='" + acType + "'").getResultList();
            Vector seqNo = (Vector) codes.get(0);
            String seqNos = seqNo.get(0).toString();
            int seqNumCode = Integer.parseInt(seqNos);
            Query insertQuery2 = em.createNativeQuery("insert into legaldocuments_actype_master(code,Legaldocument,enterby,Actype,trantime)"
                    + "values (" + seqNumCode
                    + "," + "'" + legalDocument + "'"
                    + "," + "'" + user + "'"
                    + "," + "'" + acType + "'"
                    + "," + "'" + currentDate + "'"
                    + ")");
            int var2 = insertQuery2.executeUpdate();
            if (var2 > 0) {
                ut.commit();
                return "Data Saved Successfully For -> " + acType;
            } else {
                ut.rollback();
                return "Data could not be Saved";
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

    @Override
    public String deleteLegalDocumentData(int seqNumCode, String legalDocument, String user, String acType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List allData = em.createNativeQuery("select Actype,trantime FROM legaldocuments_actype_master WHERE code=" + seqNumCode + "").getResultList();
            Vector allDatas = (Vector) allData.get(0);
            String tranTime = allDatas.get(1).toString();
            Query insertQuery1 = em.createNativeQuery("insert into legaldocuments_actype_master_his(code,Legaldocument,enterby,Actype,trantime)"
                    + "values (" + seqNumCode
                    + "," + "'" + legalDocument + "'"
                    + "," + "'" + user + "'"
                    + "," + "'" + acType + "'"
                    + "," + "'" + tranTime + "'"
                    + ")");
            int var1 = insertQuery1.executeUpdate();
            Query updateQuery = em.createNativeQuery("Delete From legaldocuments_actype_master where code=" + seqNumCode + " and Actype='" + acType + "' ");
            int var = updateQuery.executeUpdate();

            if ((var1 > 0) && (var > 0)) {
                ut.commit();
                return "Data Deleted Successfully For -> " + seqNumCode;
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

    @Override
    public List getMiscParametersAcctCode() throws ApplicationException {
        try {
            return em.createNativeQuery("select acctCode from accounttypemaster where acctNature not in('" + CbsConstant.PAY_ORDER + "','" + CbsConstant.OF_AC + "')  order by acctCode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List getMiscGLHead() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct substring(acno,5,6) as acno,acname from gltable where substring(acno,3,8) between '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_ST.getValue() + "' and '" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + SiplConstant.GL_INCOME_END.getValue() + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List getExistingDetailMiscParameters(String purpose, String acctCode) throws ApplicationException {
        try {
            return em.createNativeQuery("Select charges,charges1,effectivedt,glheadmisc  from parameterinfo_miscincome  where purpose= '" + purpose + "' and acctcode = '" + acctCode + "' and effectivedt=(select max(effectivedt) from parameterinfo_miscincome where purpose= '" + purpose + "' and acctcode = '" + acctCode + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String saveMiscParametersData(String purpose, String accType, String miscGLHead, String Charges, String issueDate, String userName, String Charges1) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        int var = 0;
        try {
            ut.begin();
            if (purpose == null || purpose.equalsIgnoreCase("")) {
                ut.rollback();
                return "Purpose field is blank!";
            }
            if (accType == null || accType.equalsIgnoreCase("")) {
                ut.rollback();
                return "Account type filed is blank!";
            }
            if (issueDate == null || issueDate.equalsIgnoreCase("")) {
                ut.rollback();
                return "Effective date field is blank!";
            }
            if (userName == null || userName.equalsIgnoreCase("")) {
                ut.rollback();
                return "Entered by is blank!";
            }
            List secList = em.createNativeQuery("select date_format(date,'%Y-%m-%d') from cbs_bankdays where "
                    + "DATE=date_format(CURDATE(),'%Y-%m-%d')").getResultList();
            if (secList.size() <= 0) {
                ut.rollback();
                return "Date denied.";
            }
            Vector secLst = (Vector) secList.get(0);
            String secListed = secLst.get(0).toString();
            List existList = em.createNativeQuery("Select * from parameterinfo_miscincome WHERE acctcode='" + accType + "' and purpose='" + purpose
                    + "'").getResultList();
            if (existList.isEmpty()) {
                Query insertQuery = em.createNativeQuery("insert into parameterinfo_miscincome(Purpose,acctcode,glheadmisc,charges,effectivedt,dt,"
                        + "enterby,charges1)" + "values ('" + purpose + "','" + accType + "','" + miscGLHead + "'," + Charges + ",'"
                        + issueDate + "','" + secListed + "','" + userName + "'," + Charges1 + ")");
                var = insertQuery.executeUpdate();
                if (var <= 0) {
                    ut.rollback();
                    return "Insertion failed.";
                } else {
                    ut.commit();
                    return "Record Saved Successfully.";
                }
            } else {
                Query insertHisQuery = em.createNativeQuery("insert into parameterinfo_miscincome_his(Purpose,acctcode,glheadmisc,charges,effectivedt,dt,"
                        + "enterby,charges1) Select Purpose,acctcode,glheadmisc,charges,effectivedt,dt,enterby,charges1 from parameterinfo_miscincome WHERE acctcode='" + accType + "' and purpose='" + purpose + "'");
                var = insertHisQuery.executeUpdate();
                if (var <= 0) {
                    ut.rollback();
                    return "Insertion In History failed.";
                } else {
                    Query delQuery = em.createNativeQuery("delete from parameterinfo_miscincome WHERE acctcode='" + accType + "' and purpose='" + purpose + "'");
                    var = delQuery.executeUpdate();
                    if (var <= 0) {
                        ut.rollback();
                        return "Delete failed.";
                    } else {
                        Query insertQuery = em.createNativeQuery("insert into parameterinfo_miscincome(Purpose,acctcode,glheadmisc,charges,effectivedt,dt,"
                                + "enterby,charges1)" + "values ('" + purpose + "','" + accType + "','" + miscGLHead + "'," + Charges + ",'"
                                + issueDate + "','" + secListed + "','" + userName + "'," + Charges1 + ")");
                        var = insertQuery.executeUpdate();
                        if (var <= 0) {
                            ut.rollback();
                            return "Insertion failed.";
                        } else {
                            ut.commit();
                            return "Record Saved Successfully.";
                        }
                    }
                }

//                Query updateQuery = em.createNativeQuery("UPDATE parameterinfo_miscincome SET charges='" + Charges + "',charges1='" + Charges1
//                        + "',glheadmisc='" + miscGLHead + "',effectivedt='" + issueDate + "' WHERE acctcode='" + accType + "' and purpose='"
//                        + purpose + "'");
//                var = updateQuery.executeUpdate();
//                if (var <= 0) {
//                    ut.rollback();
//                    throw new ApplicationException("Updation failed.");
//                } else {
//                    ut.commit();
//                    return "Record Updated Successfully.";
//                }
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    @Override
    public List dataPassBookDisplay() throws ApplicationException {
        try {
            return em.createNativeQuery("select * from passbook_values order by ord").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List dataloadPassBook() throws ApplicationException {
        try {
            return em.createNativeQuery("select name from passbook_values order by ord").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List dataload2PassBook() throws ApplicationException {
        try {
            return em.createNativeQuery("select parameter,value from passbook_parameters").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String okButtonPassBook(int value1, int value2, int value3, int value4, String st1, String st2, String st3, String st4, String st5, String st6, int inputvalue1, int inputvalue2, int inputvalue3, int inputvalue4, int inputvalue5, int inputvalue6, int inputvalue7, int inputvalue8, int inputvalue9, int inputvalue10, int inputvalue11, int inputvalue12) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q = em.createNativeQuery("update passbook_parameters set value=" + value1 + " where parameter='PassLine'");
            Integer uu = q.executeUpdate();
            Query q1 = em.createNativeQuery("update passbook_parameters set value=" + value2 + " where parameter='halfline'");
            uu = q1.executeUpdate();
            Query q2 = em.createNativeQuery("update passbook_parameters set value=" + value3 + " where parameter='skipline'");
            uu = q2.executeUpdate();
            Query q3 = em.createNativeQuery("update passbook_parameters set value=" + value4 + " where parameter='beginlines'");
            uu = q3.executeUpdate();
            Query q4 = em.createNativeQuery("update passbook_values set width=" + inputvalue2 + ",align=" + inputvalue1 + " where name='" + st1 + "'");
            uu = q4.executeUpdate();
            Query q5 = em.createNativeQuery("update passbook_values set width=" + inputvalue4 + ",align=" + inputvalue3 + " where name='" + st2 + "'");
            uu = q5.executeUpdate();
            Query q6 = em.createNativeQuery("update passbook_values set width=" + inputvalue6 + ",align=" + inputvalue5 + " where name='" + st3 + "'");
            uu = q6.executeUpdate();
            Query q7 = em.createNativeQuery("update passbook_values set width=" + inputvalue8 + ",align=" + inputvalue7 + " where name='" + st4 + "'");
            uu = q7.executeUpdate();
            Query q8 = em.createNativeQuery("update passbook_values set width=" + inputvalue10 + ",align=" + inputvalue9 + " where name='" + st5 + "'");
            uu = q8.executeUpdate();
            Query q9 = em.createNativeQuery("update passbook_values set width=" + inputvalue12 + ",align=" + inputvalue11 + " where name='" + st6 + "'");
            uu = q9.executeUpdate();
            if (uu > 0) {
                ut.commit();
                return "transaction successful";
            } else {
                ut.rollback();
                return "Transaction is not Successful";
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

    @Override
    public List branchCodeDropDownParameterInfoReport() throws ApplicationException {
        try {
            return em.createNativeQuery("select reportname from parameterinfo_report").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List tableDataParameterInfoReport() throws ApplicationException {
        try {
            return em.createNativeQuery("select reportname, code from parameterinfo_report order by reportname").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String parameterSaveUpdation(String command, String reportName, int code) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = "";
            if (command.equalsIgnoreCase("save")) {
                List checkList = em.createNativeQuery("select Reportname from parameterinfo_report where reportname='" + reportName + "'").getResultList();
                if (checkList.size() > 0) {
                    throw new ApplicationException("This parameter is already exists");
                }
                Query insertparameterInfo = em.createNativeQuery("insert into parameterinfo_report(reportname,code)"
                        + " values('" + reportName + "'," + code + ")");
                Integer insertinfo = insertparameterInfo.executeUpdate();
                if (insertinfo <= 0) {
                    throw new ApplicationException("Record does not save.");
                }
                result = "Record saved successfully";
                ut.commit();
            } else if (command.equalsIgnoreCase("update")) {
                List checkList = em.createNativeQuery("select Reportname from parameterinfo_report where reportname='" + reportName + "' AND CODE=" + code + "").getResultList();
                if (checkList.size() > 0) {
                    throw new ApplicationException("This parameter is already exists with same code.");
                }
                Query updateparameter = em.createNativeQuery("Update parameterinfo_report set code=" + code + " WHERE reportname = '" + reportName + "'");
                Integer updateparameterInfo = updateparameter.executeUpdate();
                if (updateparameterInfo <= 0) {
                    throw new ApplicationException("Record does not updated.");
                }
                result = "Data updated successfully.";
                ut.commit();
            }
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

    @Override
    public List getCircleTypesClearingInfo() throws ApplicationException {
        try {
            return em.createNativeQuery("Select code,description from codebook Where groupcode=101").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List loadCircleTypeClearingInfo(String value) throws ApplicationException {
        try {
            return em.createNativeQuery("Select * from parameterinfo_clg Where CircleType='" + value + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String updateButtonClearingInfo(String circleDescription, String circleMicr, String bankMicr, String branchMicr, String owClgHead, String owClgReturnHead, String clgReturnCharge, float owReturnCharges, String iwClgHead, String iwClgReturnHead, String iwClgReturnCharge, float iwReturnCharges, String String1) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q = em.createNativeQuery("update parameterinfo_clg set CircleDesc='" + circleDescription + "',circleMicr='" + circleMicr
                    + "',BankMicr='" + bankMicr + "',BranchMicr='" + branchMicr + "',GlClg='" + owClgHead + "',glClgRet='" + owClgReturnHead
                    + "',GlClgRetChg='" + clgReturnCharge + "',RetChg='" + owReturnCharges + "',GLInClg='" + iwClgHead + "',GLInClgRet='"
                    + iwClgReturnHead + "',GLInClgRetChg='" + iwClgReturnCharge + "',InRetChg='" + iwReturnCharges + "' Where CircleType='"
                    + String1 + "'");
            Integer uu = q.executeUpdate();
            if (uu > 0) {
                ut.commit();
                return "transaction successful";
            } else {
                ut.rollback();
                return "Transaction is not Successful";
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

    @Override
    public List getGlTableValueClearingInfo(String acNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct postflag from gltable where substring(acno,3,10)='" + acNo + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List getPostFlagClearingInfo(String postScreen, Integer postFlag, String acNo) throws ApplicationException {
        try {
            List list2 = new ArrayList();
            if (postScreen.toUpperCase().equals("FRMENTRY")) {
                list2 = em.createNativeQuery("select screen from postflagmast where screen='" + postScreen + "' and postflag IN "
                        + "(SELECT POSTFLAG FROM gltable WHERE ACNO='" + acNo + "' AND MSGFLAG IN (0,99))").getResultList();
            } else {
                list2 = em.createNativeQuery("select screen from postflagmast where screen='" + postScreen + "' and postflag=" + postFlag).getResultList();
            }
            return list2;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List getPostFlag1ClearingInfo(String postScreen, Integer postFlag, String acNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select screen from postflagmast where screen='" + postScreen + "' and postflag=" + postFlag).getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List getPostFlag2ClearingInfo(String postScreen, Integer postFlag, String acNo) throws ApplicationException {
        try {
            return em.createNativeQuery("Select ifnull(MsgFlag,0) Msgflag from gltable where substring(acno,3,10) = '" + acNo + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String saveButtonClearingInfo(String string2, String circleDescription, String circleMicr, String bankMicr, String branchMicr, String owClgHead, String owClgReturnHead, String clgReturnCharge, float owReturnCharges, String iwClgHead, String iwClgReturnHead, String iwClgReturnCharge, float iwReturnCharges) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q = em.createNativeQuery("insert into parameterinfo_clg (CircleType, CircleDesc,circleMicr,BankMicr,BranchMicr,GlClg,glClgRet,"
                    + "GlClgRetChg,RetChg,GLInClg,GLInClgRet,GLInClgRetChg,InRetChg) values ('" + string2 + "','" + circleDescription + "','"
                    + circleMicr + "','" + bankMicr + "','" + branchMicr + "','" + owClgHead + "','" + owClgReturnHead + "','" + clgReturnCharge + "','"
                    + owReturnCharges + "','" + iwClgHead + "','" + iwClgReturnHead + "','" + iwClgReturnCharge + "','" + iwReturnCharges + "')");
            Integer uu = q.executeUpdate();
            if (uu > 0) {
                ut.commit();
                return "transaction successful";
            } else {
                ut.rollback();
                return "Transaction is not Successful";
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

    public String saveUpdateChargeMasters(String command, String chargeType, String chargeName, String acctType, String fromRange, String toRange,
            String fixFlag, double amount, String effDt, String crglhead, String enterBy, String creationDt, String updateby, String updateDt,
            String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List exitList = em.createNativeQuery("select * from cbs_charge_detail where charge_Type='" + chargeType + "' and charge_Name='" + chargeName
                    + "' and ac_Type='" + acctType + "' and from_Range = " + fromRange + " and to_Range = " + toRange + " and eff_Date = '" + effDt + "'").getResultList();
            if (exitList.size() > 0) {
                Query deleteQuery = em.createNativeQuery("delete from cbs_charge_detail where charge_Type='" + chargeType + "' and charge_Name='" + chargeName
                        + "' and ac_Type='" + acctType + "' and from_Range = " + fromRange + " and to_Range = " + toRange + " and eff_Date = '" + effDt + "'");
                int var = deleteQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Some Problem In Saving Data");
                }
            }
            //if (command.equalsIgnoreCase("save")) {

            Query insertQuery = em.createNativeQuery("insert into cbs_charge_detail (charge_Type,charge_Name,ac_Type,from_Range,to_Range,fix_perc_flag,"
                    + "amt,eff_Date,cr_gl_head,enter_by,Creation_Date)" + " values('" + chargeType + "','" + chargeName + "','" + acctType + "','"
                    + fromRange + "','" + toRange + "','" + fixFlag + "','" + amount + "','" + effDt + "','" + crglhead + "','" + userName + "',now())");
            int var = insertQuery.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Some Problem In Saving Data");
            }
            ut.commit();
            return "Data Saved Successfully";

//            } else {
//                Query insertQuery = em.createNativeQuery("insert into cbs_charge_detail (charge_Type,charge_Name,ac_Type,from_Range,to_Range,fix_perc_flag,"
//                        + "amt,eff_Date,cr_gl_head,enter_by,Creation_Date)" + " values('" + chargeType + "','" + chargeName + "','" + acctType + "','"
//                        + fromRange + "','" + toRange + "','" + fixFlag + "','" + amount + "','" + effDt + "','" + crglhead + "','" + userName + "',now())");
//                int var = insertQuery.executeUpdate();
//                if (var <= 0) {
//                    throw new ApplicationException("Some Problem In updating Data");
//                }
//                ut.commit();
//                return "Data Update Successfully.";
//            }
        } catch (ApplicationException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public List tableDataChargeMasters(String chargeType) throws ApplicationException {
        try {
            return em.createNativeQuery("select b.charge_Type,b.charge_Name,b.ac_Type,b.from_Range,b.to_Range,b.fix_perc_flag,b.amt,date_format(b.eff_Date,'%d/%m/%Y') as eff_Date,"
                    + "b.cr_gl_head,b.enter_by,b.Creation_Date,b.updated_by,b.update_date from (select charge_Type as chtp,charge_Name as chname,ac_Type as atp,from_Range as "
                    + "frrange,to_Range as trange,fix_perc_flag as flg,max(eff_Date) as edt from cbs_charge_detail where charge_type ='" + chargeType + "' group by charge_Type,"
                    + "charge_Name, ac_Type,from_Range,to_Range,fix_perc_flag) a,cbs_charge_detail b where a.chtp = b.charge_Type and a.chname = b.charge_Name and a.atp = b.ac_Type "
                    + "and a.frrange = b.from_Range and a.trange = b.to_Range and a.flg = b.fix_perc_flag and a.edt = b.eff_Date").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getRefCodeAndDescByNo(String refRecNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='" + refRecNo + "' order by ref_code").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<FidilityTablePojo> getFidilityDetailsList(String code) throws ApplicationException {
        List<FidilityTablePojo> resultList = new ArrayList<FidilityTablePojo>();
        try {
            List exitList = em.createNativeQuery("select Desig_Code,Desig_Name,BondAmt,PremiumAmt,ForYear,CrGlHead,DrGlHead,DATE_FORMAT(EffDt,'%d/%m/%Y') from fidility_premium_slab where Desig_Code='" + code + "' and EffDt = (select max(EffDt) from fidility_premium_slab where Desig_Code='" + code + "')").getResultList();
            if (!exitList.isEmpty()) {
                for (int i = 0; i < exitList.size(); i++) {
                    FidilityTablePojo pojo = new FidilityTablePojo();
                    Vector extVec = (Vector) exitList.get(i);
                    pojo.setDsgCode(extVec.get(0).toString());
                    pojo.setDsgDesc(extVec.get(1).toString());
                    pojo.setBondAmount(extVec.get(2).toString());
                    pojo.setPrAmount(extVec.get(3).toString());
                    pojo.setPeriod(extVec.get(4).toString());
                    pojo.setCrGl(extVec.get(5).toString());
                    pojo.setDrGl(extVec.get(6).toString());
                    pojo.setEffDt(extVec.get(7).toString());

                    resultList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultList;
    }

    public String saveFidilityData(String opt, String DesigCd, double bondAmt, double prAmt, int FYr, String CrGlHead, String DrGlHead, String effDt, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        try {
            ut.begin();
            if (opt.equalsIgnoreCase("save")) {
                List exitList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where REF_CODE ='" + DesigCd + "' and REF_REC_NO='020'").getResultList();
                if (exitList.isEmpty()) {
                    ut.rollback();
                    return "Data Not Exist For " + DesigCd;
                } else {
                    Vector extVec = (Vector) exitList.get(0);
                    String dDesc = extVec.get(0).toString();

                    Query insertQuery = em.createNativeQuery("insert into fidility_premium_slab (Desig_Code,Desig_Name,BondAmt,PremiumAmt,"
                            + " ForYear,CrGlHead,DrGlHead,EnterBy,UpdateBy,EffDt,Dt)" + " values('" + DesigCd + "','" + dDesc + "'," + bondAmt + "," + prAmt + "," + FYr + ",'" + CrGlHead + "','" + DrGlHead + "','" + enterBy + "','','" + effDt + "',now())");
                    int var = insertQuery.executeUpdate();
                    if (var > 0) {
                        ut.commit();
                        result = "Data Saved Successfully";
                    } else {
                        ut.rollback();
                        result = "Some Problem In Saving Data";
                        return result;
                    }
                }
            } else if (opt.equalsIgnoreCase("update")) {
                Query updateQuery = em.createNativeQuery("update fidility_premium_slab set BondAmt=" + bondAmt + ",PremiumAmt=" + prAmt + ",ForYear=" + FYr + ",CrGlHead='" + CrGlHead + "',DrGlHead='" + DrGlHead + "',UpdateBy='" + enterBy + "',Dt=now() where Desig_Code='" + DesigCd + "' and EffDt='" + effDt + "'");
                int var = updateQuery.executeUpdate();
                if (var > 0) {
                    ut.commit();
                    result = "Data Update Successfully.";
                } else {
                    ut.rollback();
                    result = "Data could not be Update.";
                    return result;
                }
            }
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

    public List gridDetailATMCard(String opt, String acNo) throws ApplicationException {
        try {
            List checkList = new ArrayList();
            if (opt.equalsIgnoreCase("2")) {
                checkList = em.createNativeQuery("select ACNO,CARD_NO,date_format(ISSUE_DT,'%d/%m/%Y'),MIN_LIMIT,DEL_FLAG,ENTER_BY,kit_no, date_format(ISSUE_DT,'%d/%m/%Y'),embossing_name"
                        + " from atm_card_master where acno ='" + acNo + "' and del_flag ='A'").getResultList();
            } else if (opt.equalsIgnoreCase("3")) {
                checkList = em.createNativeQuery("select ACNO,CARD_NO,date_format(ISSUE_DT,'%d/%m/%Y'),MIN_LIMIT,DEL_FLAG,ENTER_BY,kit_no, date_format(ISSUE_DT,'%d/%m/%Y'),embossing_name "
                        + " from atm_card_master where del_flag ='A' AND verify ='N'").getResultList();
            }
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List gridDetailForVerifyAtmCard(String regDate, String brnCode) throws ApplicationException {
        List detailList = new ArrayList();
        try {
            detailList = em.createNativeQuery("select acno,card_no,issue_dt,min_limit,"
                    + "verify,enter_by,del_flag,trantime,file_type,card_status,txn_limit_type,withdrawal_limit_amount,"
                    + "withdrawal_limit_count,purchase_limit_amount,purchase_limit_count,trf_limit_amount,trf_limit_count,"
                    + "encoding_name,embossing_name,registration_dt,card_type,txn_limit_card_type,card_relationship,"
                    + "service_code,spouse_name,kcc_emv_type,chn,institution_id from atm_card_master where verify='N' and "
                    + " date_format(lastUpdateDate,'%Y%m%d') = '" + ymd.format(dmy.parse(regDate)) + "' and "
                    + "substring(acno,1,2)='" + brnCode + "' ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return detailList;
    }

    public List getSecondaryAccountData(String priAcno, String cardNo) throws ApplicationException {
        List detailList = new ArrayList();
        try {
            detailList = em.createNativeQuery("select primary_acno,card_no,secondary_acno,txn_limit_type,withdrawal_limit_amount,"
                    + "withdrawal_limit_count,purchase_limit_amount,purchase_limit_count from atm_secondary_card_master "
                    + "where primary_acno ='" + priAcno + "' and card_no ='" + cardNo + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return detailList;
    }

    public String getdeactivatedAccount(String AcNo) throws ApplicationException {
        List data = new ArrayList();
        try {
            data = em.createNativeQuery("select del_flag from atm_card_master where acno='" + AcNo + "'").getResultList();
            Vector ele = (Vector) data.get(0);
            String delFlag = ele.get(0).toString();
            if (delFlag.equals("I")) {
                return "This account already has been deactivated.";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return "true";
    }

    public List getAccountInAtmCardMaster(String Acno) throws ApplicationException {
        List datalist = new ArrayList();
        try {
            datalist = em.createNativeQuery("select acno from atm_card_master  where acno='" + Acno + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return datalist;
    }

    @Override
    public List gridDetailForDeactivateCard(String brnCode, String AcNo) throws ApplicationException {
        List detailList = new ArrayList();
        try {
            detailList = em.createNativeQuery("select acno,card_no,issue_dt,min_limit,"
                    + "verify,enter_by,del_flag,trantime,file_type,card_status,txn_limit_type,withdrawal_limit_amount,"
                    + "withdrawal_limit_count,purchase_limit_amount,purchase_limit_count,trf_limit_amount,trf_limit_count,"
                    + "encoding_name,embossing_name,cast(registration_dt as date) as reg_dt,card_type,txn_limit_card_type,card_relationship,"
                    + "service_code,spouse_name,kcc_emv_type,chn,institution_id from atm_card_master where acno = '" + AcNo + "' and verify='Y' and "
                    + "del_flag ='G' and  substring(acno,1,2)='" + brnCode + "' ").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return detailList;
    }

    public String SaveUpdateVerifyATMCard(String opt, String acNo, String cardNo, String issDt, String minLmt,
            String status, String userName, String oldCardNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        try {
            ut.begin();
            if (opt.equalsIgnoreCase("Save")) {
                int insertResult = em.createNativeQuery("insert into atm_card_master(acno,card_no,issue_dt,min_limit,"
                        + "verify,enter_by,del_flag,trantime,file_type,card_status,txn_limit_type,withdrawal_limit_amount,"
                        + "withdrawal_limit_count,purchase_limit_amount,purchase_limit_count,trf_limit_amount,trf_limit_count,"
                        + "encoding_name,embossing_name,registration_dt,card_type,txn_limit_card_type,card_relationship,"
                        + "service_code,spouse_name,kcc_emv_type,chn,institution_id) "
                        + "values('" + acNo + "','" + cardNo + "',date_format('" + issDt + "','%Y%m%d'),'" + minLmt + "',"
                        + "'N','" + userName + "','" + status + "', now(),'','','',0,0,0,0,0,0,'','',current_timestamp,'',"
                        + "'','','','','','','')").executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Insertion problem in ATM Card MAster.");
                }
            } else if (opt.equalsIgnoreCase("Update")) {
                List list = em.createNativeQuery("select ifnull(max(sno),0)+1 from atm_card_master_his").getResultList();
                Vector vec = (Vector) list.get(0);
                String sNo = vec.get(0).toString();

                int exeResult = em.createNativeQuery("insert into atm_card_master_his(sno,acno,card_no,issue_dt,min_limit,"
                        + " verify,del_flag,enter_by,lastUpdateBy,lastUpdateDate,trantime,verify_by,file_type,card_status,txn_limit_type,"
                        + "withdrawal_limit_amount,withdrawal_limit_count,purchase_limit_amount,purchase_limit_count,trf_limit_amount,"
                        + "trf_limit_count,encoding_name,embossing_name,registration_dt,card_type,txn_limit_card_type,card_relationship,"
                        + "service_code,spouse_name,kcc_emv_type,chn,institution_id) select '" + sNo + "',acno,card_no,issue_dt,"
                        + "min_limit,verify,'" + status + "', enter_by,'" + userName + "',now(),now(),verify_by,file_type,"
                        + "card_status,txn_limit_type,withdrawal_limit_amount,withdrawal_limit_count,purchase_limit_amount,"
                        + "purchase_limit_count,trf_limit_amount,trf_limit_count,encoding_name,embossing_name,registration_dt,"
                        + "card_type,txn_limit_card_type,card_relationship,service_code,spouse_name,kcc_emv_type,chn,"
                        + "institution_id from atm_card_master where acno='" + acNo + "' and "
                        + "card_no = '" + oldCardNo + "'").executeUpdate();
                if (exeResult <= 0) {
                    throw new ApplicationException("Insertion problem in ATM Card MAster History.");
                }
                exeResult = em.createNativeQuery("update atm_card_master set acno ='" + acNo + "',"
                        + "card_no='" + cardNo + "',issue_dt='" + issDt + "',"
                        + "min_limit=" + minLmt + ",lastUpdateBy='" + userName + "',del_flag='" + status + "',"
                        + "lastUpdateDate=now(),trantime=now() where acno ='" + acNo + "' and card_no = '" + oldCardNo + "'").executeUpdate();
                if (exeResult <= 0) {
                    throw new ApplicationException("Update problem in ATM Card MAster.");
                }
            } else {
                int verResult = em.createNativeQuery("update atm_card_master set verify ='Y',"
                        + "verify_by='" + userName + "' where acno ='" + acNo + "' and card_no = '" + cardNo + "'").executeUpdate();
                if (verResult <= 0) {
                    throw new ApplicationException("Update problem in ATM Card MAster.");
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
    
     public String saveUpdateVerifyATMKitCard(String opt, String acNo, String cardNo, String issDt, String minLmt,
            String status, String userName, String kitNo, String kitIssueDt, String oldCardNo, String embosedName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        try {
            ut.begin();
            if (opt.equalsIgnoreCase("Save")) {
                List list = em.createNativeQuery("select acno from atm_card_master where acno='"+ acNo+"' and card_no = '"+ cardNo +"' and del_flag = 'A'").getResultList();
               
                if(!list.isEmpty()) throw new ApplicationException("Either Card has already issued to this Account or only kit has been issued to this account");
               
                int insertResult = em.createNativeQuery("insert into atm_card_master(acno,card_no,issue_dt,min_limit,"
                        + "verify,enter_by,del_flag,trantime,file_type,card_status,txn_limit_type,withdrawal_limit_amount,"
                        + "withdrawal_limit_count,purchase_limit_amount,purchase_limit_count,trf_limit_amount,trf_limit_count,"
                        + "encoding_name,embossing_name,registration_dt,card_type,txn_limit_card_type,card_relationship,"
                        + "service_code,spouse_name,kcc_emv_type,chn,institution_id,kit_no,kit_issue_dt) "
                        + "values('" + acNo + "','" + cardNo + "',date_format('" + issDt + "','%Y%m%d'),'" + minLmt + "',"
                        + "'N','" + userName + "','" + status + "', now(),'','','',0,0,0,0,0,0,'','"+ embosedName +"',current_timestamp,'',"
                        + "'','','','','','','','"+ kitNo +"',date_format('" + kitIssueDt + "','%Y%m%d'))").executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Insertion problem in ATM Card MAster.");
                }
            } else if (opt.equalsIgnoreCase("Update")) {
                List list = em.createNativeQuery("select ifnull(max(sno),0)+1 from atm_card_master_his").getResultList();
                Vector vec = (Vector) list.get(0);
                String sNo = vec.get(0).toString();

                int exeResult = em.createNativeQuery("insert into atm_card_master_his(sno,acno,card_no,issue_dt,min_limit,"
                        + " verify,del_flag,enter_by,lastUpdateBy,lastUpdateDate,trantime,verify_by,file_type,card_status,txn_limit_type,"
                        + "withdrawal_limit_amount,withdrawal_limit_count,purchase_limit_amount,purchase_limit_count,trf_limit_amount,"
                        + "trf_limit_count,encoding_name,embossing_name,registration_dt,card_type,txn_limit_card_type,card_relationship,"
                        + "service_code,spouse_name,kcc_emv_type,chn,institution_id,kit_no,kit_issue_dt) select '" + sNo + "',acno,card_no,issue_dt,"
                        + "min_limit,verify,'" + status + "', enter_by,'" + userName + "',now(),now(),verify_by,file_type,"
                        + "card_status,txn_limit_type,withdrawal_limit_amount,withdrawal_limit_count,purchase_limit_amount,"
                        + "purchase_limit_count,trf_limit_amount,trf_limit_count,encoding_name,embossing_name,registration_dt,"
                        + "card_type,txn_limit_card_type,card_relationship,service_code,spouse_name,kcc_emv_type,chn,"
                        + "institution_id,kit_no,kit_issue_dt from atm_card_master where acno='" + acNo + "' and "
                        + "card_no = '" + oldCardNo + "'").executeUpdate();
                if (exeResult <= 0) {
                    throw new ApplicationException("Insertion problem in ATM Card MAster History.");
                }
                exeResult = em.createNativeQuery("update atm_card_master set acno ='" + acNo + "',embossing_name = '"+ embosedName +"',"
                        + "card_no='" + cardNo + "',issue_dt='" + issDt + "',"
                        + "min_limit=" + minLmt + ",lastUpdateBy='" + userName + "',del_flag='" + status + "',"
                        + "lastUpdateDate=now(),trantime=now() where acno ='" + acNo + "' and kit_no = '" + kitNo + "'").executeUpdate();
                if (exeResult <= 0) {
                    throw new ApplicationException("Update problem in ATM Card MAster.");
                }
            } else {
                int verResult = em.createNativeQuery("update atm_card_master set verify ='Y',"
                        + "verify_by='" + userName + "' where acno ='" + acNo + "' and kit_no = '" + kitNo + "'").executeUpdate();
                if (verResult <= 0) {
                    throw new ApplicationException("Update problem in ATM Card MAster.");
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

    public List gridOfficeDeptMapp() throws ApplicationException {
        try {
            List checkList = new ArrayList();
            checkList = em.createNativeQuery("select a.office_id, (select REF_DESC from cbs_ref_rec_type where REF_REC_NO = '233' and REF_CODE = a.office_id), "
                    + " a.dept_id, (select REF_DESC from cbs_ref_rec_type where REF_REC_NO = '234' and REF_CODE = a.dept_id), "
                    + " a.dept_head,a.address from office_dept_master a order by a.office_id, a.dept_id ").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String SaveUpdateVerifyOfficeDept(String opt, int offId, String offName, int deptId, String deptName, String deptHead,
            String addr, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        try {
            ut.begin();
            if (opt.equalsIgnoreCase("Save")) {
                int insertResult = em.createNativeQuery("insert into office_dept_master(office_id,office_name,dept,dept_head,address,dept_id,enter_by,enter_date) "
                        + " values(" + offId + ",'" + offName + "','" + deptName + "','" + deptHead + "','" + addr + "'"
                        + "," + deptId + ",'" + userName + "',now())").executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Insertion problem in office_dept_master.");
                }
            } else if (opt.equalsIgnoreCase("Update")) {
                int exeResult = em.createNativeQuery("update office_dept_master set dept_head ='" + deptHead + "',"
                        + "address='" + addr + "' ,enter_by='" + userName + "', enter_date=now() where office_id ='" + offId + "' and dept_id = '" + deptId + "'").executeUpdate();
                if (exeResult <= 0) {
                    throw new ApplicationException("Update problem in office_dept_master.");
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

    public String verifyAtmAccountDetail(AtmCardMappGrid obj, String user, String vrfyDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List exitList = em.createNativeQuery("select lastUpdateBy from atm_card_master where acno='" + obj.getAcNo() + "' and card_no = '" + obj.getCardNo() + "' ").getResultList();
            Vector ele = (Vector) exitList.get(0);
            String lastUser = ele.get(0).toString();
            if (lastUser.equals(user)) {
                ut.rollback();
                return "You can not verify your own entry.";
            } else {
                int n = em.createNativeQuery("UPDATE atm_card_master set min_limit=" + obj.getMinLmt() + ",verify='Y',verify_by='" + user + "', "
                        + "lastUpdateBy='" + user + "',lastUpdateDate=current_timestamp,"
                        + "txn_limit_type='" + obj.getTxnLimitType() + "',withdrawal_limit_amount=" + obj.getWithdrawalLimitAmount() + ","
                        + "withdrawal_limit_count=" + obj.getWithdrawalLimitCount() + ","
                        + "purchase_limit_amount=" + obj.getPurchaseLimitAmount() + ","
                        + "purchase_limit_count=" + obj.getPurchaseLimitCount() + ","
                        + "trf_limit_amount='0',trf_limit_count='0',card_no='" + obj.getCardNo() + "'"
                        + " WHERE acno ='" + obj.getAcNo() + "' and  card_no= '" + obj.getCardNo() + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In Card Detail Updation.");
                }
                ut.commit();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return "true";
    }

    @Override
    public String deactivateAtmAccountDetail(AtmCardMappGrid obj, String user, String vrfyDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int n = em.createNativeQuery("UPDATE atm_card_master set verify='N',verify_by='', "
                    + "lastUpdateBy='" + user + "',lastUpdateDate=current_timestamp,del_flag= 'I',"
                    + "file_type='D' WHERE acno ='" + obj.getAcNo() + "' and "
                    + "card_no='" + obj.getCardNo() + "'").executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem In Card Detail Updation.");
            }
            ut.commit();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return "true";
    }
}
