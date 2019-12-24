/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.master;

import com.cbs.exception.ApplicationException;
import com.cbs.utils.CbsUtil;
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
import javax.transaction.UserTransaction;

/**
 *
 * @author admin
 */
@Stateless(mappedName = "ReportingFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ReportingFacade implements ReportingFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;

    /**
     * 
     * 
     * Start Code ReportingFridayBean
     */
    public List formLoad() throws ApplicationException {
        try {
            return em.createNativeQuery("Select DATE_FORMAT(RepFriDate,'%d/%m/%Y') From ho_reportingfriday Where Sno = (Select Min(sno) From ho_reportingfriday) and balance=0").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List minRepDate() throws ApplicationException {
        try {
            return em.createNativeQuery("Select DATE_FORMAT(RepFriDate,'%Y/%m/%d'),balance,enterby From ho_reportingfriday Where repFriDate in (select min(repfridate) from ho_reportingfriday)").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String updateSingleRecord(String s1, String balance, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Double balance1 = Double.parseDouble(balance);
            Query q1 = em.createNativeQuery("update ho_reportingfriday set balance=" + balance1 + ", enterby='" + enterBy + "' where repfridate='" + s1 + "'");
            int int1 = q1.executeUpdate();
            if (int1 > 0) {
                ut.commit();
                return "Data has been updated successfully !";
            } else {
                throw new ApplicationException("Data has not been updated successfully !");
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List gridLoad() throws ApplicationException {
        try {
            return em.createNativeQuery("Select * From ho_reportingfriday Where RepFriDate<=now() Order By repfridate desc").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getReportDate(String fDate, String Tdate) throws ApplicationException {
        try {
            return em.createNativeQuery("Select DATE_FORMAT(RepFriDate,'%Y%m%d') From ho_reportingfriday Where RepFriDate between '" + fDate + "' and '" + Tdate + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String aftergetReportDate(String repfridate, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            float balance = 0f;
            String calcRepFriNew = calcRepFriNEW(repfridate);
            if (calcRepFriNew.endsWith("Balance")) {
                throw new ApplicationException(calcRepFriNew);
            }
            balance = Float.parseFloat(calcRepFriNew);
            Query q1 = em.createNativeQuery("Update ho_reportingfriday Set Balance=" + balance + " ,enterby='" + userName + "' Where RepFriDate='" + repfridate + "'");
            Integer int1 = q1.executeUpdate();
            if (int1 > 0) {
                ut.commit();
                return "Record updated successfully !";
            } else {
                throw new ApplicationException("Data could not be updated !");
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String insertHistory(String fDate, String Tdate, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q2 = em.createNativeQuery("insert into ho_reportingfriday_his(Repfridate,Balance,Active,EnterBy,Authby,TranTime,Auth,UpDateby) Select RepFriDate,Balance,Active,Enterby,AuthBy,TranTime,Auth,'" + userName + "' from ho_reportingfriday  where repFriDate between '" + fDate + "' and '" + Tdate + "'");
            Integer int1 = q2.executeUpdate();
            if (int1 > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "false";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String calcRepFriNEW(String myDate) throws ApplicationException {
        double balance0 = 0.0, balance1 = 0.0, balance2 = 0.0, balance3 = 0.0, calcRepFriNew = 0.0;
        try {
            myDate = CbsUtil.dateAdd(myDate, -14);

            List result = em.createNativeQuery("SELECT IFNULL(SBTL,0) FROM ho_crr_sbtl WHERE INTDT=(SELECT MAX(INTDT) FROM ho_crr_sbtl WHERE INTDT<='" + myDate + "')").getResultList();
            if (result.isEmpty() || result == null) {
                balance3 = 0.0;
            } else {
                Vector element = (Vector) result.get(0);
                balance3 = Float.parseFloat(element.get(0).toString());
            }

            List netBalList = em.createNativeQuery("SELECT * FROM ho_repfridaymaster WHERE BALTYPE='NET BALANCE'").getResultList();
            if (netBalList.isEmpty() || netBalList == null) {
                return "Thereis no entry regarding Net Balance";
            }

            for (int i = 0; i < netBalList.size(); i++) {
                Vector vector = (Vector) netBalList.get(i);
                String acno = vector.get(1).toString();

                List consolidateList = em.createNativeQuery("SELECT IFNULL(SUM(CASH_CR_AMT),0)+IFNULL(SUM(TRF_CR_AMT),0)+IFNULL(SUM(CLG_CR_AMT),0),IFNULL(SUM(CASH_DR_AMT),0)+IFNULL(SUM(TRF_DR_AMT),0) + IFNULL(SUM(CLG_DR_AMT),0) FROM ho_consolidate_entry WHERE DT ='" + myDate + "' AND SUBSTRING(ACNO,3,8) ='" + acno + "'").getResultList();
                Vector consVector = (Vector) consolidateList.get(0);
                Double sumCr = Double.parseDouble(consVector.get(0).toString());
                Double sumDr = Double.parseDouble(consVector.get(1).toString());

                balance0 = balance0 + (sumCr - sumDr);
            }

            List debitBalList = em.createNativeQuery("SELECT * FROM ho_repfridaymaster WHERE BALTYPE='DEBIT BALANCE'").getResultList();
            if (debitBalList.isEmpty() || debitBalList == null) {
                return "There is no entry regarding Debit Balance";
            }

            for (int j = 0; j < debitBalList.size(); j++) {
                Vector vector = (Vector) debitBalList.get(j);
                String acno = vector.get(1).toString();

                List consolidateList = em.createNativeQuery("SELECT IFNULL(SUM(CASH_CR_AMT),0)+IFNULL(SUM(TRF_CR_AMT),0)+IFNULL(SUM(CLG_CR_AMT),0),IFNULL(SUM(CASH_DR_AMT),0)+IFNULL(SUM(TRF_DR_AMT),0) + IFNULL(SUM(CLG_DR_AMT),0) FROM ho_consolidate_entry WHERE DT ='" + myDate + "' AND SUBSTRING(ACNO,3,8) ='" + acno + "'").getResultList();
                Vector consVector = (Vector) consolidateList.get(0);
                Double sumCr = Double.parseDouble(consVector.get(0).toString());
                Double sumDr = Double.parseDouble(consVector.get(1).toString());

                balance1 = balance1 + (sumCr - sumDr);
            }

            List crBalList = em.createNativeQuery("SELECT * FROM ho_repfridaymaster WHERE BALTYPE='CREDIT BALANCE'").getResultList();
            if (crBalList.isEmpty() || crBalList == null) {
                return "There is no entry regarding Credit Balance";
            }
            for (int k = 0; k < crBalList.size(); k++) {
                Vector vector = (Vector) crBalList.get(k);
                String acno3 = vector.get(1).toString();

                List consolidateList = em.createNativeQuery("SELECT IFNULL(SUM(CASH_CR_AMT),0)+IFNULL(SUM(TRF_CR_AMT),0)+IFNULL(SUM(CLG_CR_AMT),0),IFNULL(SUM(CASH_DR_AMT),0)+IFNULL(SUM(TRF_DR_AMT),0) + IFNULL(SUM(CLG_DR_AMT),0) FROM ho_consolidate_entry WHERE DT ='" + myDate + "' AND SUBSTRING(ACNO,3,8) ='" + acno3 + "'").getResultList();
                Vector consVector = (Vector) consolidateList.get(0);
                Double sumCr = Double.parseDouble(consVector.get(0).toString());
                Double sumDr = Double.parseDouble(consVector.get(1).toString());

                balance2 = balance2 + (sumCr - sumDr);
            }

            balance2 = balance2 - balance3;
            calcRepFriNew = balance0 + balance1 + balance2 + balance3;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return String.valueOf(calcRepFriNew);
    }

    public List getTableDetails() throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT DISTINCT(ACNAME),SUBSTRING(ACNO,3,10) FROM gltable ORDER BY ACNAME,SUBSTRING(ACNO,3,10)").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveRecord(String acDescription, String acNo, String BalTp) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List resultSet = em.createNativeQuery("select * from ho_repfridaymaster where acdesc ='" + acDescription + "' and acno='" + acNo + "' and BALTYPE ='" + BalTp + "'").getResultList();
            if (!resultSet.isEmpty()) {
                throw new ApplicationException("Duplicate entry can not be save !");
            }

            Query insertRecon = em.createNativeQuery("insert into ho_repfridaymaster(acdesc,acno,BALTYPE) values('" + acDescription + "','" + acNo + "','" + BalTp + "')");
            int result = insertRecon.executeUpdate();
            if (result > 0) {
                ut.commit();
                return "Data has been successfully saved !";
            } else {
                throw new ApplicationException("Data is not saved !");
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String deleteRecord(String acNo, String BalTp) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List resultSet = em.createNativeQuery("select * from ho_repfridaymaster where acno='" + acNo + "' AND BALTYPE = '" + BalTp + "'").getResultList();
            if (resultSet.isEmpty()) {
                throw new ApplicationException("Data corresponding to selected parameters do not exist for deletion !");
            }

            Query insertRecon = em.createNativeQuery("delete from ho_repfridaymaster where acno='" + acNo + "' AND BALTYPE = '" + BalTp + "'");
            int result = insertRecon.executeUpdate();
            if (result < 1) {
                throw new ApplicationException("Problem in deleteion !");
            } else {
                ut.commit();
                return "Data has been successfully deleted !";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }
}
