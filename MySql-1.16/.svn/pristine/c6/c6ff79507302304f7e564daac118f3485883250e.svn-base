/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import java.math.BigDecimal;
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
 * @author Ankit Verma
 */
@Stateless(mappedName = "AccountStatusSecurityFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AccountStatusSecurityFacade implements AccountStatusSecurityFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote facadeRemote;
    @EJB
    MiscReportFacadeRemote miscReportRemote;
    @EJB
    CommonReportMethodsRemote common;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * ********************AccountStatusBean's Methods ******************
     */
    public List lienAcNo() throws ApplicationException {
        List varlist = new ArrayList();
        try {
            varlist = em.createNativeQuery("Select acctCode From accounttypemaster Where AcctNature in ('CA','DL','TL','SS') Order By acctCode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return varlist;
    }

    public List getCustNameAndStatus(String Acno, String acctType) throws ApplicationException {
        List list = new ArrayList();
        try {
            String acNat = facadeRemote.getAcNatureByCode(acctType);
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                list = em.createNativeQuery("Select a.custname,a.accstatus,c.description,a.openingdt From td_accountmaster a,codebook c Where Acno ='" + Acno + "' and accStatus <> 9 and c.groupcode='3' and accStatus = c.code").getResultList();
            } else {
                list = em.createNativeQuery("Select a.custname,a.accstatus,c.description,a.openingdt From accountmaster a,codebook c Where Acno ='" + Acno + "' and accStatus <> 9 and c.groupcode='3' and accStatus = c.code").getResultList();
            }
            if (list.isEmpty()) {
                throw new ApplicationException("Account does not exist in account master");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getStatusHistory(String Acno) throws ApplicationException {
        List varlist = new ArrayList();
        try {
            varlist = em.createNativeQuery("Select Acno, Remark, c.description,date_format(Dt,'%d/%m/%Y') ,COALESCE(Amount,0), Auth, enterBy,"
                    + "date_format(Effdt,'%d/%m/%Y') From accountstatus a,codebook c Where acno ='" + Acno + "' and c.groupcode='3' "
                    + "and spFlag = c.code order by dt,effdt").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return varlist;
    }

    public List returnTableValues(String acno) throws ApplicationException {
        List varlist = new ArrayList();
        try {
            varlist = em.createNativeQuery("Select Acno,CustName,IntDeposit,ifnull(RDMatDate,''),OpeningDt From accountmaster Where acno='" + acno + "'").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return varlist;
    }

    public List getAllStatusList() throws ApplicationException {
        List varlist = new ArrayList();
        try {
            varlist = em.createNativeQuery("Select code,description From codebook where groupcode='3' and code not in (0,9)  Order By code").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return varlist;
    }

    public String cbsSaveAcctStatus(String acno, String strRemarks, String authby, String newStatus, String dt,
            float lienAmt, String lienAcNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            String currentBrnCode = facadeRemote.getCurrentBrnCode(acno);
            String accountCode = facadeRemote.getAccountCode(acno);
            String acNat = facadeRemote.getAcNatureByCode(accountCode);
            String acTable = facadeRemote.getAccountTable(acNat);
            ut.begin();
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                throw new ApplicationException("Lien marking is not allowed by this from");
            }
//            if (newStatus.equals("10")) {
//                throw new ApplicationException("Lien marking is not allowed by this from");
//            }
            List reslist1 = em.createNativeQuery("select date from bankdays where dayendflag='N' and brncode='" + currentBrnCode + "' ").getResultList();
            Vector res = (Vector) reslist1.get(0);
            String dt1 = res.get(0).toString();
            List accStatusList = em.createNativeQuery("select acno from accountstatus where acno='" + acno + "' and auth='N' and dt='" + dt1 + "'").getResultList();
            if (!accStatusList.isEmpty()) {
                throw new ApplicationException("Some Account Status Authorization are pending for the Account " + acno);
            }
            List accEffDtList = em.createNativeQuery("Select Effdt from accountstatus  where acno = '" + acno + "' and auth='Y' and date_format(EffDt,'%Y%m%d')>'" + dt + "'").getResultList();
            if (!accEffDtList.isEmpty()) {
                throw new ApplicationException("Please check the effective dt you have passed for this Acocunt No. " + acno);
            }

            List varlist = em.createNativeQuery("Select description From codebook where groupcode='3' and code='" + newStatus + "'").getResultList();
            res = (Vector) varlist.get(0);

            strRemarks = res.get(0).toString() + " " + strRemarks;
            if (newStatus.equals("1")) {
                List oldlist = em.createNativeQuery("select accstatus from " + acTable + " where acno='" + acno + "'").getResultList();
                Vector oldLst = (Vector) oldlist.get(0);
                String oldStatus = oldLst.get(0).toString();

//                if ((oldStatus.equals("11")) || (oldStatus.equals("12")) || (oldStatus.equals("13")) || (oldStatus.equals("14"))) {
                List memlist = new ArrayList();
                memlist = em.createNativeQuery("select ifnull(sum(cramt),0)- ifnull(sum(dramt),0) from npa_recon "
                        + "where acno='" + acno + "' and dt<='" + dt1 + "' and auth='Y' ").getResultList();
                if (!memlist.isEmpty()) {
                    Vector memLst = (Vector) memlist.get(0);
                    BigDecimal memBal = new BigDecimal(memLst.get(0).toString());
                    if (!(memBal.compareTo(BigDecimal.ZERO) == 0)) {
                        throw new ApplicationException("Memorandom Interest Exist Status Not Changed");
                    }
                }
//                }
            }
            if (newStatus.equals("10")) {
                double osBalance = common.getBalanceOnDate(acno, dt1);
                if (osBalance < lienAmt) {
                    throw new ApplicationException("Lien Amount can not be greater than o/s Balance.");
                }
            }
            Query insertQuery = em.createNativeQuery("Insert into accountstatus(Acno,Remark,spFlag,dt,amount,ENTERBY,auth,EffDt,BaseAcno,trantime) values ('"
                    + acno + "','" + strRemarks + "','" + newStatus + "','" + dt1 + "'," + lienAmt + ",'" + authby + "','N','" + dt + "','" + lienAcNo + "',now())");
            int var = insertQuery.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem in data inserting.");
            }
            ut.commit();
            return "Status Changed Successfully";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception syex) {
                throw new ApplicationException(syex.getMessage());
            }
        }
    }

    public List dateDiffWefDate(String wefDate) throws ApplicationException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            List dateDiff = em.createNativeQuery("select TIMESTAMPDIFF(day,'" + wefDate + "','" + sdf.format(date) + "')").getResultList();
            return dateDiff;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String lienMarked(String acno, String strRemarks, String authby, String newStatus, String dt,
            float lienAmt, String lienAcNo, String roiOnSecurity, String intTable, String margin) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String accountCode = facadeRemote.getAccountCode(acno);
            double osBalance = common.getBalanceOnDate(acno, dt);
            if (osBalance < lienAmt) {
                throw new ApplicationException("Lien Amount can not be greater than o/s Balance.");
            }
            String currentBrnCode = facadeRemote.getCurrentBrnCode(acno);
            List reslist1 = em.createNativeQuery("select date from bankdays where dayendflag='N' and brncode='" + currentBrnCode + "' ").getResultList();
            Vector res = (Vector) reslist1.get(0);
            String curDt = res.get(0).toString();

            List sNos = em.createNativeQuery("Select ifnull(max(sno),0)+1 From loansecurity Where acno='" + lienAcNo + "'").getResultList();
            Vector sNoss = (Vector) sNos.get(0);
            String sNo = sNoss.get(0).toString();

            List dataList = em.createNativeQuery(" Select COALESCE(IntDeposit,0),ifnull(RDMatDate,0),ifnull(OpeningDt,0) From accountmaster "
                    + "Where acno='" + acno + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Data does not exist in account master.");
            }
            Vector threevalues = (Vector) dataList.get(0);
            String IntDeposits = threevalues.get(0).toString();
            float IntDeposit = Float.parseFloat(IntDeposits);
            String RDMatDate = threevalues.get(1).toString();
            String OpeningDt = threevalues.get(2).toString();

            if (IntDeposits.equalsIgnoreCase("") || IntDeposits.equalsIgnoreCase(null)) {
                IntDeposits = "0";
            }
            if (roiOnSecurity.equalsIgnoreCase("") || roiOnSecurity.equalsIgnoreCase(null)) {
                roiOnSecurity = "0";
            }
            if (margin.equalsIgnoreCase("") || margin.equalsIgnoreCase(null)) {
                margin = "0";
            }
            
            Query insertQuery = em.createNativeQuery("Insert into loansecurity(Acno,Sno,Security,Particulars,MatDate,LienValue,matValue,IssueDate,"
                    + "Status,Remarks,EnteredBy,entryDate,SecurityOption,SecurityChg,lienacno, SecurityRoi,MargineROI, AppRoi,IntTableCode, Margin) Select '" + lienAcNo + "','" + sNo + "','P','"
                    + accountCode + "','" + RDMatDate + "'," + lienAmt + "," + lienAmt + ", date_format('" + OpeningDt + "','%Y-%m-%d'),"
                    + "'ACTIVE',CONCAT('DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY):', '" + acno + "' , '; ROI:' "
                    + ", cast(" + IntDeposit + " as char(10)) , '; Present Amt:' , cast(" + lienAmt + " as char(20))),UPPER('" + authby + "'),"
                    + "date_format('" + dt + "','%Y-%m-%d'),SUBSTRING('" + acno + "',3,2),'LIEN','" + acno + "','" + IntDeposits + "','" + roiOnSecurity + "','" + String.valueOf(Double.parseDouble(IntDeposits) + Double.parseDouble(roiOnSecurity)) + "','" + intTable + "','" + margin + "' From accountmaster Where Acno='" + acno + "'");
            int var = insertQuery.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem in data inserting.");
            }
            Query insertQ = em.createNativeQuery("Insert into accountstatus(Acno,Remark,spFlag,dt,amount,ENTERBY,auth,EffDt,BaseAcno,trantime) values ('"
                    + acno + "','" + strRemarks + "','" + newStatus + "','" + curDt + "'," + lienAmt + ",'" + authby + "','Y','" + dt + "','" + acno + "',now())");
            var = insertQ.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem in data inserting.");
            }
            Integer entryList = em.createNativeQuery("Update accountmaster Set AccStatus = '" + newStatus + "' where acno = '" + acno + "'").executeUpdate();
            if (entryList <= 0) {
                throw new ApplicationException("Problem in data updation");
            }
            ut.commit();
            return "Status Changed Successfully";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception syex) {
                throw new ApplicationException(syex.getMessage());
            }
        }
    }

    /**
     * ***********************SecurityDetailsBean's *
     * Method****************************************
     */
    public String saveSecurityDetail(String acno, String SecurityNature, String securityType, String status,
            String securityDesc1, String securityDesc2, String securityDesc3, String particulars, String otherAc,
            Float matValue, String matDate, String estimationDt, Float lienValue, String enteredBy, String Remarks,
            String entryDate, Float Margin, String Auth, String groupCode, String secODRoi, String secODScheme) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer code = 0;
            Integer sno = 0;
            String statusV = "";
            String remarkV = "";
            List resultlist = em.createNativeQuery("Select ifnull(max(sno),0) From loansecurity Where Acno ='" + acno + "'").getResultList();
            Vector dateVect = (Vector) resultlist.get(0);
            code = Integer.parseInt(dateVect.get(0).toString());
            if (code == 0) {
                sno = 1;
            } else {
                sno = code + 1;
            }
            if (status.equalsIgnoreCase("1")) {
                statusV = "Active";
            }
            if (securityDesc3.equalsIgnoreCase("")) {
                remarkV = securityType + ":" + securityDesc1 + ":" + securityDesc2 + ":" + particulars + ":" + otherAc;
            } else {
                remarkV = securityType + ":" + securityDesc1 + ":" + securityDesc2 + ":" + securityDesc3 + ":" + particulars + ":" + otherAc;
            }
            double STMFrequency = 0d;
            if (securityType.equalsIgnoreCase("NON-DATED")) {
                STMFrequency = 7d;
            }
            if (secODRoi.equalsIgnoreCase("") || secODRoi.equalsIgnoreCase(null)) {
                secODRoi = "0";
            }            
            Integer upadteMasterList = em.createNativeQuery("Insert Into loansecurity(Acno,Sno,security,SecurityOption,"
                    + "Particulars,MatValue,MatDate,issueDate,LienValue,Status,EnteredBy,Remarks,EntryDate,SecurityType,"
                    + "securityChg,Margin,Auth,securitysub,securitycode, AppRoi,IntTableCode, STMFrequency,ReceivedSTMDt)"
                    + "values('" + acno + "'," + sno + ",'" + SecurityNature + "','" + securityDesc2 + "','" + particulars + "',"
                    + "" + matValue + ",'" + matDate + "','" + estimationDt + "'," + lienValue + ",'" + statusV + "','" + enteredBy + "','" + remarkV + "','" + entryDate + "','" + securityType + "',"
                    + "'" + securityDesc1 + "'," + Margin + ",'" + Auth + "','" + securityDesc3 + "','" + groupCode + "','" + secODRoi + "','" + secODScheme + "'," + STMFrequency + ",now())").executeUpdate();

            if (upadteMasterList <= 0) {
                ut.rollback();
                return "Data is does not Saved in gltable";
            } else {
                ut.commit();
                return "Data Saved Successfully";
            }

        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new ApplicationException(e);
            }
            throw new ApplicationException(e);
        }
    }

    public List getSecurityTableValues(String acno) throws ApplicationException {
        List resultlist = null;

        try {
            resultlist = em.createNativeQuery("Select coalesce(Sno,0) as Sno,coalesce(matValue,0) as matValue,"
                    + "ifnull(date_format(coalesce(matDate,0),'%d/%m/%Y'),'01/01/1900') as matDate,ifnull(date_format(coalesce(issuedate,0),'%d/%m/%Y'),'01/01/1900') as "
                    + "issuedate, coalesce(lienValue,0) as lienValue,coalesce(security,'') as security,"
                    + "ifnull(date_format(coalesce(LastSTMDt,0),'%d/%m/%Y'),'01/01/1900') as LastSTMDt,ifnull(date_format(coalesce(NextSTMDt,0),'%d/%m/%Y'),'01/01/1900') "
                    + "as NextSTMDt,coalesce(STMFrequency,0) as STMFrequency,coalesce(Remarks,'') as Remarks,"
                    + "coalesce(Status,'') as Status,coalesce(Enteredby,'') as Enteredby,"
                    + "ifnull(date_format(coalesce(Entrydate,0),'%d/%m/%Y'),'01/01/1900') as Entrydate,coalesce(Particulars,'') as Particulars,"
                    + "coalesce(Margin,0) as Margin,ifnull(date_format(coalesce(ReceivedSTMDt,0),'%d/%m/%Y'),'01/01/1900') as "
                    + "ReceivedSTMDt,ifnull(SecurityRoi,0),ifnull(MargineROI,0),ifnull(AppRoi,0), ifnull(IntTableCode,''), ifnull(addRoi,0),ifnull(AuthBy,'')  From loansecurity  Where acno='" + acno + "' Order By  Sno").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public String deleteSecurityTable(String EntryDate, String todayDate, String acno, Integer sno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String auth;
        try {
            ut.begin();
            List resultlist = em.createNativeQuery("Select ifnull(auth,'0') From loansecurity Where Acno= '" + acno + "' and Sno =" + sno + "").getResultList();
            if (resultlist.size() > 0) {
                Vector resultlistVect = (Vector) resultlist.get(0);
                auth = resultlistVect.get(0).toString();
                if (auth.equalsIgnoreCase("Y")) {
                    ut.rollback();
                    return "Deletion Is Not Possible. Entry Already Authorized";
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if (sdf.parse(EntryDate).equals(sdf.parse(todayDate))) {
                Integer upadteMasterList = em.createNativeQuery("delete from  loansecurity  Where Acno= '" + acno + "' and Sno =" + sno + "").executeUpdate();
                if (upadteMasterList <= 0) {
                    ut.rollback();
                    return "Record NOt Deleted Successfully";
                }
            } else {
                ut.rollback();
                return "You Can Only Delete Particulars For Current Date Entry";
            }
            ut.commit();
            return "Record Deleted Successfully";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String acctNature(String acno) throws ApplicationException {
        List varlist = new ArrayList();
        String nature = null;
        try {
            varlist = em.createNativeQuery("select acctNature from accounttypemaster  where acctCode = '" + acno + "'").getResultList();
            if (varlist.size() > 0) {
                Vector varlistVect = (Vector) varlist.get(0);
                nature = varlistVect.get(0).toString();

            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return nature;
    }

    public List toUpdate(String acno, Integer sno) throws ApplicationException {
        List varlist = new ArrayList();

        try {
            varlist = em.createNativeQuery("Select ifnull(Particulars,''),ifnull(matvalue,0),ifnull(date_format(matdate, '%d/%m/%Y'),''),"
                    + "ifnull(lienvalue,0),ifnull(Status,''),ifnull(date_format(Issuedate, '%d/%m/%Y'),''),ifnull(Remarks,''),ifnull(security,''),ifnull(securityChg,''),ifnull(Margin,0),"
                    + "ifnull(SecurityType,''),ifnull(SecurityOption,''),ifnull(securitysub,'') From loansecurity Where Acno= '" + acno + "' and Sno =" + sno).getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return varlist;
    }

    public String UpdateSecurity(String acno, String SecurityNature, String securityType, String status,
            String securityDesc1, String securityDesc2, String securityDesc3, String particulars, String otherAc,
            Float matValue, String matDate, String estimationDt, Float lienValue, String enteredBy, String Remarks,
            String entryDate, Float Margin, Integer sno, String groupCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (securityDesc3 == null || securityDesc3.equalsIgnoreCase("")) {
                Integer upadteMasterList = em.createNativeQuery("Update loansecurity Set Acno='" + acno + "', Security='" + SecurityNature + "',"
                        + "SecurityOption='" + securityDesc2 + "',Particulars='" + particulars + "',MatValue=Case When ifnull(" + matValue + ",0)=0 "
                        + "Then 0 Else " + matValue + " End,MatDate='" + matDate + "',issueDate='" + estimationDt + "',LienValue=Case When "
                        + "ifnull(" + lienValue + ",0)=0 Then 0 Else " + lienValue + " End,EnteredBy='" + enteredBy + "',Remarks='" + Remarks + "',"
                        + "EntryDate='" + entryDate + "',securityChg='" + securityDesc1 + "',SecurityType='" + securityType + "',Margin=" + Margin + ",securitycode='" + groupCode + "' Where Acno= '" + acno + "' and Sno =" + sno + "").executeUpdate();
                if (upadteMasterList <= 0) {
                    ut.rollback();
                    return "Data is Not Updated !!!!!!!!!!";
                }
            } else {
                Integer upadteMasterList = em.createNativeQuery("Update loansecurity Set Acno='" + acno + "', Security='" + SecurityNature + "',"
                        + "SecurityOption='" + securityDesc2 + "',Particulars='" + particulars + "',MatValue=Case When ifnull(" + matValue + ",0)=0 "
                        + "Then 0 Else " + matValue + " End,MatDate='" + matDate + "',issueDate='" + estimationDt + "',LienValue=Case When "
                        + "ifnull(" + lienValue + ",0)=0 Then 0 Else " + lienValue + " End,EnteredBy='" + enteredBy + "',Remarks='" + Remarks + "',"
                        + "securitysub='" + securityDesc3 + "',EntryDate='" + entryDate + "',"
                        + "securityChg='" + securityDesc1 + "',SecurityType='" + securityType + "',Margin=" + Margin + ",securitycode='" + groupCode + "' Where Acno= '" + acno + "' and Sno =" + sno + "").executeUpdate();
                if (upadteMasterList <= 0) {
                    ut.rollback();
                    return "Data is Not Updated !!!!!!!!!!";
                }
            }
            ut.commit();
            return "Record Has Been Updated";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String UpdateSecurityTable(String expiredBy, String ExpiryDate, String acno, Integer sno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List unAuthList = em.createNativeQuery("select * from loansecurity Where Acno= '" + acno + "' and auth = 'N' and Sno =" + sno).getResultList();
            if (!unAuthList.isEmpty()) {
                ut.rollback();
                return "Please authorize the security";
            }
            Integer upadteMasterList = em.createNativeQuery("Update loansecurity Set Status = 'EXPIRED',"
                    + "ExpiredBy='" + expiredBy + "',ExpiryDate='" + ExpiryDate + "' Where Acno= '" + acno + "' and Sno =" + sno + "").executeUpdate();
            if (upadteMasterList <= 0) {
                ut.rollback();
                return "Status Has Not Been Changed As EXPIRED";
            }
            ut.commit();
            return "Status Has Been Changed As EXPIRED";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String SecurityCode(String secType, String secDesc2, String secDesc3) throws ApplicationException {
        List varlist = new ArrayList();
        String CODE = null;
        String GROUPCODE = null;
        String Groupc = null;
        String CODEc = null;
        try {
            varlist = em.createNativeQuery("SELECT CODE,GROUPCODE FROM loan_codebook WHERE DESCRIPTION='" + secType + "' ").getResultList();
            if (varlist.size() > 0) {
                Vector varlistVect = (Vector) varlist.get(0);
                CODE = varlistVect.get(0).toString();
                GROUPCODE = varlistVect.get(1).toString();

            }
            if (secDesc3 == null || secDesc3.equalsIgnoreCase("")) {

                varlist = em.createNativeQuery("SELECT GROUPCODE,CODE FROM loan_codebook WHERE DESCRIPTION='" + secDesc2 + "' AND SUBSTRING(CAST(GROUPCODE AS CHAR),4,1)='" + CODE + "' ").getResultList();
                if (varlist.size() > 0) {
                    Vector varlistVect = (Vector) varlist.get(0);
                    Groupc = varlistVect.get(0).toString();
                    CODEc = varlistVect.get(1).toString();
                } else {

                    varlist = em.createNativeQuery("SELECT GROUPCODE,CODE FROM loan_codebook WHERE DESCRIPTION='" + secDesc3 + "' AND SUBSTRING(CAST(GROUPCODE AS CHAR),4,1)='" + CODE + "' ").getResultList();
                    if (varlist.size() > 0) {
                        Vector varlistVect = (Vector) varlist.get(0);
                        Groupc = varlistVect.get(0).toString();
                        CODEc = varlistVect.get(1).toString();
                    }
                }
            }
            return Groupc + "/" + CODEc;
        } catch (Exception ex) {
        }
        return Groupc;
    }

    public List getStatus() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("Select Code,Description From codebook Where groupCode=33 and Code not in (0,2)").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }
}
