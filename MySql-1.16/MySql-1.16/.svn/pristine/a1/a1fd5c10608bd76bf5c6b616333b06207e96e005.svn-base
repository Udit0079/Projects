/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.dto.LockerRentCalTable;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.LockerRentDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsBulkPostingFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.EJBException;
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
 * @author Sudhir
 */
@Stateless(mappedName = "LockerManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class LockerManagementFacade implements LockerManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPost;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private InterBranchTxnFacadeRemote interFts;
    @EJB
    FtsBulkPostingFacadeRemote ftsDrCr;

    public List cabNo(String brCode) throws ApplicationException {
        List cabNo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct(cast(cabno as UNSIGNED)) from lockermaster  where brnCode='" + brCode + "'");
            cabNo = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return cabNo;
    }

    public List acctTypeCombo(String brCode) throws ApplicationException {
        List actype = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctCode from accounttypemaster Where acctNature in ('" + CbsConstant.SAVING_AC + "','" + CbsConstant.CURRENT_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')");
            actype = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return actype;
    }

    public List modeCombo(String brCode) throws ApplicationException {
        List mode = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Description From codebook Where GroupCode=4 and code<>0");
            mode = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return mode;
    }

    public List lockerType(String brCode, String cabNo) throws ApplicationException {
        List lockerType = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select distinct lockerType From lockermaster Where CabNo = " + cabNo + " and brnCode='" + brCode + "' order by lockerType");
            lockerType = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return lockerType;
    }

    public List lockerTypeComboOnChange(String brCode, String cabNo, String lockerTy) throws ApplicationException {
        List lockerNo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select coalesce(Min(cast(lockerNo as UNSIGNED)),0) From lockermaster Where cabNo=" + cabNo + " and brncode='" + brCode + "' and lockertype='" + lockerTy + "' and ocflag ='N'");
            lockerNo = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return lockerNo;
    }

    public List lockerNoLostFocus(String brCode, String cabNo, String lockerNo, String lockerTy) throws ApplicationException {
        List resultList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select cast(l.cabno as UNSIGNED),l.custcat,l.mode,l.adoperator1,l.adoperator2,l.adoperator3,"
                    + "l.adoperator4,l.security,cast(l.keyno as UNSIGNED),l.lockertype,l.lockerno,l.rent,l.nomination,l.acno,l.remarks,c.custname,"
                    + "custno,craddress,phoneno from lockeracmaster l, customermaster c,accountmaster ac "
                    + " where cabno = " + cabNo + " and lockertype = '" + lockerTy + "' and lockerno= " + lockerNo + " and l.acno=ac.acno "
                    + "and substring(l.acno,5,6)=c.custno and ac.accttype=c.acType and c.brnCode=ac.curbrcode and l.brncode='" + brCode + "' ");
            resultList = selectQuery.getResultList();
            if (resultList.isEmpty()) {
                Query selectQuery1 = em.createNativeQuery("Select coalesce(cast(KeyNo as UNSIGNED),0) From lockermaster Where CabNo= " + cabNo
                        + " and LockerType='" + lockerTy + "' and LockerNo=" + lockerNo + " and brncode='" + brCode + "'");
                resultList = selectQuery1.getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return resultList;
    }

    public List gridload(String brCode, String cabNo, String lockerTy) throws ApplicationException {
        List lockerNo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select cast(la.CabNo as UNSIGNED),la.LockerType,cast(la.LockerNo as UNSIGNED),"
                    + "cast(la.KeyNo as UNSIGNED),la.Acno,la.CustCat,cast(la.SECURITY as UNSIGNED),la.nomination,la.mode,la.Auth,la.Remarks,la.rent,la.freq_year "
                    + "from lockeracmaster la,accountmaster ac "
                    + "where cabNo = " + cabNo + " and lockerType= '" + lockerTy + "' and la.acno=ac.acno and la.BrnCode='" + brCode + "' ");
            lockerNo = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return lockerNo;
    }

    public List custCatLostFocus(String brCode, String custCat, String lockerTy) throws ApplicationException {
        List rent = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select rent From lockerrentmaster Where LockerType='" + lockerTy + "' and custcat='" + custCat + "'"
                    + "and effectivedt = (select max(effectivedt) from lockerrentmaster where LockerType='" + lockerTy + "' and custcat='" + custCat + "')");
            rent = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return rent;
    }

    public List acNoLostFocus(String brCode, String acNo) throws ApplicationException {
        List resList1 = new ArrayList();
        try {
            /* String acNat = ftsPost.getAccountNature(acNo);
             if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
             resList1 = em.createNativeQuery("select am.acno,cm.custname,cm.craddress,cm.phoneno from Td_accountMaster am,Td_customerMaster cm "
             + "where substring(am.acno,5,6)=cm.custno and am.acno= '" + acNo + "' and am.curbrcode = cm.brncode and am.accttype =cm.actype "
             + "and am.accstatus<>9").getResultList();
             } else if (acNat.equals(CbsConstant.SAVING_AC)) {
             */ resList1 = em.createNativeQuery("select am.acno,cm.custname,cm.craddress,cm.phoneno from accountmaster am,customermaster cm "
                    + "where substring(am.acno,5,6)=cm.custno and am.acno= '" + acNo + "' and am.curbrcode = cm.brncode and am.accttype =cm.actype "
                    + "and am.accstatus<>9").getResultList();
            /*} else {
             throw new ApplicationException("You can issue the Lockers only for deposit accounts.");
             }*/
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return resList1;
    }

    public List setCustNameAndAdd(String brCode, String acNo) throws ApplicationException {
        List rentDtList = new ArrayList();
        try {
            // String acNat = ftsPost.getAccountNature(acNo);
           /* if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
             rentDtList = em.createNativeQuery("select cm.custname,cm.craddress,cm.phoneno from Td_accountMaster am,Td_customerMaster cm "
             + "where substring(am.acno,5,6)=cm.custno and am.acno= '" + acNo + "' and am.curbrcode ='" + brCode + "' and cm.brncode='" 
             + brCode + "'  and am.accttype =cm.actype and am.accstatus<>9").getResultList();
             } else {*/
            rentDtList = em.createNativeQuery("select cm.custname,cm.craddress,cm.phoneno from accountmaster am,customermaster cm"
                    + " where substring(am.acno,5,6)=cm.custno and am.acno= '" + acNo + "' and am.curbrcode ='" + brCode + "' and cm.brncode='"
                    + brCode + "'  and am.accttype =cm.actype and am.accstatus<>9").getResultList();
            //}
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return rentDtList;
    }

    public List setRent(String brCode, String cabNo, String lockerTy, String lockerNo) throws ApplicationException {
        List rentDtList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Rent From lockeracmaster Where cabNo=" + cabNo + " and lockerType= '" + lockerTy + "' and lockerno= " + lockerNo + " and brncode='" + brCode + "'");
            rentDtList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return rentDtList;
    }

    public List setAdOperators(String brCode, String cabNo, String lockerTy, String lockerNo) throws ApplicationException {
        List rentDtList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select rent,adoperator1,adoperator2,adoperator3,adoperator4 From lockeracmaster l Where cabNo=" + cabNo + " and lockerType= '" + lockerTy + "' and lockerno= " + lockerNo + " and l.brncode='" + brCode + "'");
            rentDtList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return rentDtList;
    }

    public String saveBtnIssueLocker(String brCode, float cabNo, String lockerTy, float lockerNo, float keyNo, String acNo, float rent, float secDep, String custCat, String mode, String nomination, String remarks, String enterBy, String adOpr1, String adOpr2, String adOpr3, String adOpr4, String rentDueDt, String freqYear) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var1 = 0;
            if ((brCode == null) || (acNo == null) || (custCat == null) || (lockerTy == null) || (mode == null) || (enterBy == null) || (lockerTy == null)) {
                ut.rollback();
                return "Please Check All Fields!!!.";
            }

            List chkIssueLocker = em.createNativeQuery("Select * from lockermaster where lockertype = '" + lockerTy + "' and cabno= " + cabNo + " "
                    + "and lockerno = " + lockerNo + " and keyno = " + keyNo + " and brncode = '" + brCode + "' and ocflag = 'Y'").getResultList();
            if (!chkIssueLocker.isEmpty()) {
                ut.rollback();
                return "Locker Already Issued, Please surrender to issue it again";
            }

            List tempBdList = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
            Vector Lst = (Vector) tempBdList.get(0);
            String tempBd = Lst.get(0).toString();

            List acStatusList = em.createNativeQuery("Select accstatus from accountmaster where acno='" + acNo + "'").getResultList();
            if (!acStatusList.isEmpty()) {
                Vector acStatusLst = (Vector) acStatusList.get(0);
                String accSt = acStatusLst.get(0).toString();
                if (accSt.equalsIgnoreCase("9")) {
                    ut.rollback();
                    return "Can't Assign Locker To Closed Acccount!!!.";
                }
            } else {
                ut.rollback();
                return "Account Does Not Exist!!!.";
            }

            Query insertQuery = em.createNativeQuery("Insert Into lockeracmaster(cabno,LockerType,LockerNo,KeyNo,Acno,Rent,SECURITY,CustCat,mode,nomination,"
                    + "Remarks,Auth,enterby,ADOPERATOR1,ADOPERATOR2,ADOPERATOR3,ADOPERATOR4,brncode,issuedt,trantime,freq_year)"
                    + " values( " + cabNo + ",'" + lockerTy + "'," + lockerNo + "," + keyNo + ",'" + acNo + "'," + rent + "," + secDep + ",'" + custCat
                    + "','" + mode + "','" + nomination + "','" + remarks + "','N','" + enterBy + "','" + adOpr1 + "','" + adOpr2 + "','" + adOpr3 + "','"
                    + adOpr4 + "','" + brCode + "',date_format(now(),'%Y-%m-%d'),now(),'" + freqYear + "')");
            var1 = insertQuery.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem in data insertion.");
            }
            Query insertQuery1 = em.createNativeQuery("Insert Into lockerrent(cabno,lockerType,lockerNo,Acno,Rent,issueDt,enterBy,RentDueDt,TxnDate,status,brncode)"
                    + " values( " + cabNo + ",'" + lockerTy + "'," + lockerNo + ",'" + acNo + "'," + rent + ",DATE_FORMAT(now(),'%Y%m%d'),'"
                    + enterBy + "','" + rentDueDt + "','" + tempBd + "','U','" + brCode + "')");
            var1 = insertQuery1.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem in data insertion.");
            }
            Query updateQuery = em.createNativeQuery("Update lockermaster Set OcFlag='Y' Where LockerType ='" + lockerTy + "' And LockerNo = " + lockerNo + " and cabno = " + cabNo + " and brncode='" + brCode + "'");
            var1 = updateQuery.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem in data insertion.");
            }
            ut.commit();
            return "Locker Issued Successfully";

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

    public String updateLockerInfo(String brCode, float cabNo, String lockerTy, float lockerNo, float keyNo, String acNo, float rent, float secDep, String custCat, String mode, String nomination, String remarks, String enterBy, String adOpr1, String adOpr2, String adOpr3, String adOpr4, String rentDueDt, String freqYear) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var1 = 0;
            if ((brCode == null) || (acNo == null) || (custCat == null) || (lockerTy == null) || (mode == null) || (enterBy == null) || (lockerTy == null)) {
                ut.rollback();
                return "Please Check All Fields!!!.";
            }
            Query insertQuery = em.createNativeQuery("Insert Into lockeracmaster_his(cabno,LockerType,LockerNo,KeyNo,Acno,Rent,SECURITY,CustCat,mode,"
                    + "nomination,Remarks,Auth,enterby,ADOPERATOR1,ADOPERATOR2,ADOPERATOR3,ADOPERATOR4,brncode,issuedt,trantime,status,freq_year) "
                    + "select cabno,LockerType,LockerNo,KeyNo,Acno,Rent,SECURITY,CustCat,mode,nomination,Remarks,Auth,enterby,ADOPERATOR1,ADOPERATOR2,"
                    + "ADOPERATOR3,ADOPERATOR4,brncode,issuedt,trantime,'M',freq_year from lockeracmaster where cabno=" + cabNo + " and lockerno=" + lockerNo
                    + " and lockertype='" + lockerTy + "' and brncode='" + brCode + "'");
            var1 = insertQuery.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem in data updation.");
            }
            Query updateQuery = em.createNativeQuery("update lockeracmaster set security= " + secDep + ",rent=" + rent + ",mode= '" + mode + "',custcat= '" + custCat
                    + "',nomination= '" + nomination + "'," + "remarks= '" + remarks + "', adoperator1= '" + adOpr1 + "',adoperator2= '" + adOpr2
                    + "',adoperator3= '" + adOpr3 + "'," + "adoperator4= '" + adOpr4 + "',auth='N',authby='',enterby='" + enterBy + "',trantime=now(),freq_year='" + freqYear + "' "
                    + "where cabno=" + cabNo + " and lockerno=" + lockerNo + " and lockertype='" + lockerTy + "' and brncode='" + brCode + "'");
            var1 = updateQuery.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem in data updation.");
            }
            Query updateQuery1 = em.createNativeQuery("Update lockerrent set rent= " + rent + ", rentduedt='" + rentDueDt + "' where cabno=" + cabNo + " and lockerno=" + lockerNo
                    + " and lockertype='" + lockerTy + "' and brncode='" + brCode + "' and status='U'");
            var1 = updateQuery1.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem in data updation.");
            }
            ut.commit();
            return "Record Updated Successfully";

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

    public List gridDeatil1(String lableForChange, String custName, String brCode) throws ApplicationException {
        List gridList = new ArrayList();
        try {
            if (lableForChange.equalsIgnoreCase("Customer Name :")) {
                gridList = em.createNativeQuery("Select lm.Cabno,lm.LockerType,lm.LockerNo,am.Acno,lr.Rent, DATE_FORMAT(lr.RentDueDt,'%d/%m/%Y'),am.CustName,lm.keyno,lm.adoperator1,lm.adoperator2,lm.adoperator3,lm.adoperator4"
                        + " From accountmaster am, lockeracmaster lm, lockerrent lr"
                        + " Where am.acno=lr.acno and lr.LockerNo=lm.LockerNo and lr.cabno=lm.cabno and lm.lockertype=lr.lockertype and lr.acno=lm.acno and lr.status='U' and lm.brncode ='" + brCode + "' /*and am.Curbrcode =lm.brncode*/ and am.CustName like '" + custName + "%'").getResultList();
            } else if (lableForChange.equalsIgnoreCase("Locker No. :")) {
                gridList = em.createNativeQuery("Select lm.Cabno,lm.LockerType,lm.LockerNo,am.Acno,lr.Rent, DATE_FORMAT(lr.RentDueDt,'%d/%m/%Y'), am.CustName,lm.keyno,lm.adoperator1,lm.adoperator2,lm.adoperator3,lm.adoperator4"
                        + " From accountmaster am, lockeracmaster lm, lockerrent lr"
                        + " Where am.acno=lr.acno and lr.LockerNo=lm.LockerNo and lr.cabno=lm.cabno and lm.lockertype=lr.lockertype and lr.acno=lm.acno and lr.status='U' and lm.brncode ='" + brCode + "' /*and am.Curbrcode =lm.brncode*/ and lm.LockerNo= " + Integer.parseInt(custName)).getResultList();
            } else if (lableForChange.equalsIgnoreCase("Key No. :")) {
                gridList = em.createNativeQuery("Select lm.Cabno,lm.LockerType,lm.LockerNo,am.Acno,lr.Rent, DATE_FORMAT(lr.RentDueDt,'%d/%m/%Y'), am.CustName,lm.keyno,lm.adoperator1,lm.adoperator2,lm.adoperator3,lm.adoperator4"
                        + " From accountmaster am, lockeracmaster lm, lockerrent lr"
                        + " Where am.acno=lr.acno and lr.LockerNo=lm.LockerNo and lr.cabno=lm.cabno and lm.lockertype=lr.lockertype and lr.acno=lm.acno and lr.status='U' and lm.brncode ='" + brCode + "' /*and am.Curbrcode =lm.brncode*/ and lm.KeyNo= " + Integer.parseInt(custName)).getResultList();
            }else if (lableForChange.equalsIgnoreCase("Joint Holder Name :")) {
                gridList = em.createNativeQuery("Select lm.Cabno,lm.LockerType,lm.LockerNo,am.Acno,lr.Rent, DATE_FORMAT(lr.RentDueDt,'%d/%m/%Y'),am.CustName,lm.keyno,lm.adoperator1,lm.adoperator2,lm.adoperator3,lm.adoperator4"
                        + " From accountmaster am, lockeracmaster lm, lockerrent lr"
                        + " Where am.acno=lr.acno and lr.LockerNo=lm.LockerNo and lr.cabno=lm.cabno and lm.lockertype=lr.lockertype and lr.acno=lm.acno and lr.status='U' and "
                        + "lm.brncode ='" + brCode + "' /*and am.Curbrcode =lm.brncode*/ and (lm.adoperator1 like '" + custName + "%' or lm.adoperator2 like "
                        + "'"+ custName +"%' or lm.adoperator3 like '"+ custName +"%' or lm.adoperator4 like '"+ custName +"%')").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return gridList;
    }

    public List getLockerMasterData(String lockerType, String orgnBrCode) throws ApplicationException {
        try {
            List resultlist = null;
            resultlist = em.createNativeQuery("Select lockerType,cast(cabno as UNSIGNED),cast(lockerno as UNSIGNED),cast(keyno as UNSIGNED),ocflag,enterby,"
                    + "entrydate,sno,brncode From lockermaster Where LockerType='" + lockerType + "' and brncode = '" + orgnBrCode
                    + "' ORDER BY LOCKERNO,cabNo").getResultList();
            return resultlist;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String daybeginDate(String orgnBrCode) throws ApplicationException {
        try {
            String tempBd;
            List dateList = em.createNativeQuery("select date from bankdays where dayendflag = 'N' and brncode = '" + orgnBrCode + "'").getResultList();
            if (dateList.size() <= 0) {
                return "Check the Day Begin/Day End";
            } else {
                Vector dateVect = (Vector) dateList.get(0);
                tempBd = (String) dateVect.get(0);
            }
            return tempBd;
        } catch (Exception e) {
            throw new EJBException("Transaction failed: " + e.getMessage());
        }
    }

    public String saveLockerMaster(String lockerType, Float lockerNo, String btnCaption, Integer lastSno, Float cabinetNo, Float keyNo, String userName, String orgnBrCode, String todayDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String tempBd = "";
        try {
            ut.begin();
            /**
             * **************CHECKING THE DAYBEGIN & DATE ACCORDING TO ISSUED
             * BRANCH CODE*****************
             */
            tempBd = daybeginDate(orgnBrCode);

            /**
             * **************COMPAIR THE DAYBEGIN DATE AND TODAY
             * DATE*************************************
             */
            if (ymd.parse(tempBd).after(ymd.parse(todayDate)) || ymd.parse(tempBd).before(ymd.parse(todayDate))) {
                ut.rollback();
                return "Check the today date you have passed";
            }

            int tmpSno = 0;
            if (btnCaption.equalsIgnoreCase("update")) {
                List resultlist = em.createNativeQuery("Select * From lockermaster where cabno= " + cabinetNo + " and lockertype= '" + lockerType + "' and lockerno =" + lockerNo + " and sno != " + lastSno + " and brncode = '" + orgnBrCode + "'").getResultList();
                if (resultlist.size() > 0) {
                    ut.rollback();
                    return "The Selected Locker No. Already Exists!";
                }
            } else {
                List resultlist = em.createNativeQuery("Select * From lockermaster where cabno= " + cabinetNo + " and lockertype= '" + lockerType + "' and lockerno =" + lockerNo + "  and brncode = '" + orgnBrCode + "'").getResultList();
                if (resultlist.size() > 0) {
                    ut.rollback();
                    return "The Selected Locker No. Already Exists!";
                }
            }
            if (btnCaption.equalsIgnoreCase("update")) {
                List resultlist = em.createNativeQuery("Select * From lockermaster where cabno= " + cabinetNo + " and lockertype= '" + lockerType + "' and keyNo = " + keyNo + " and sno != " + lastSno + " and brncode = '" + orgnBrCode + "'").getResultList();
                if (resultlist.size() > 0) {
                    ut.rollback();
                    return "The Selected Key No. Already Exists!";
                }
            } else {
                List resultlist = em.createNativeQuery("Select * From lockermaster where cabno= " + cabinetNo + " and lockertype= '" + lockerType + "' and keyNo = " + keyNo + "  and brncode = '" + orgnBrCode + "'").getResultList();
                if (resultlist.size() > 0) {
                    ut.rollback();
                    return "The Selected Key No. Already Exists!";
                }
            }
            if (btnCaption.equalsIgnoreCase("update")) {
                Integer upadteMasterList = em.createNativeQuery("Update lockermaster set lockerType = '" + lockerType + "',cabno=" + cabinetNo + " ,"
                        + "lockerno=" + lockerNo + " ,keyno= " + keyNo + " ,entrydate='" + tempBd + "' Where sno=" + lastSno + " and brncode = '" + orgnBrCode + "'").executeUpdate();
                if (upadteMasterList <= 0) {
                    ut.rollback();
                    return "Data is does get not Updated in lockerMaster";
                } else {
                    ut.commit();
                    return "Record Updated Successfully";
                }
            }
            List resultlistLocker = em.createNativeQuery("Select coalesce(max(ifnull(Sno,0)),0) From lockermaster where brncode = '" + orgnBrCode + "'").getResultList();
            if (resultlistLocker.size() > 0) {
                Vector dateVect = (Vector) resultlistLocker.get(0);
                tmpSno = Integer.parseInt(dateVect.get(0).toString()) + 1;
            } else {
                tmpSno = 0;
            }
            Integer upadteMasterList = em.createNativeQuery("insert into lockermaster(LockerType, CabNo, LockerNo, KeyNo, OCFlag, EnterBy, EntryDate, Sno,brncode) values ('" + lockerType + "', " + cabinetNo + "," + lockerNo + "," + keyNo + ",'N','" + userName + "','" + tempBd + "'," + tmpSno + ",'" + orgnBrCode + "') ").executeUpdate();
            if (upadteMasterList <= 0) {
                //   result = "Data is does get not Updated in lockerMaster";
                ut.rollback();
                return "Data is does get not Updated in lockerMaster";
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List glHead(String brCode) throws ApplicationException {
        List glHead = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select AcName,Acno From gltable where substring(acno,3,8) IN "
                    + "(select glheadmisc from parameterinfo_miscincome where purpose='Locker Rent') and substring(acno,1,2)='" + brCode + "'");
            glHead = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return glHead;
    }

    public List gridLoadForAllAccounts(String brCode) throws ApplicationException {
        List gridList = new ArrayList();
        try {
            gridList = em.createNativeQuery("Select aa.CabNo,aa.LockerType,aa.LockerNo,aa.Acno,aa.CustName,aa.Rent,"
                    + " aa.RentDueDt,aa.status,aa.curbrcode, bb.stateCode, bb.brState,aa.freq_year  from "
                    + " (Select m.CabNo,m.LockerType,m.LockerNo,l.Acno,a.CustName,l.Rent,"
                    + " date_format(l.RentDueDt,'%d/%m/%Y') as RentDueDt,l.status,a.curbrcode,m.freq_year From lockeracmaster m,lockerrent l, accountmaster a "
                    + " Where rentDueDt<='" + CbsUtil.dateAdd(ymd.format(new Date()), 15) + "' And "
                    + " l.acno=a.acno and l.acno = m.acno And m.cabno=l.cabno and m.lockerType=l.lockerType and m.lockerno = l.lockerno and "
                    + " l.rent <> 0 and l.status='U' and l.BrnCode = '" + brCode + "' and a.accstatus=1)aa,"
                    + " (select ci.Acno as acno, ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState   "
                    + " from customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                    + " where ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brCode + "' as unsigned))  bb "
                    + " where aa.acno = bb.acno order by aa.CabNo, aa.LockerNo").getResultList();

//            gridList = em.createNativeQuery("Select aa.CabNo,aa.LockerType,aa.LockerNo,aa.Acno,aa.CustName,aa.Rent,aa.RentDueDt,aa.status,aa.curbrcode, bb.stateCode, bb.brState from\n"
//                    + "(Select m.CabNo,m.LockerType,m.LockerNo,l.Acno,a.CustName,l.Rent, date_format(l.RentDueDt,'%d/%m/%Y') as RentDueDt,l.status,a.curbrcode \n"
//                    + "From lockeracmaster m,lockerrent l, accountmaster a  Where rentDueDt<='" + CbsUtil.dateAdd(ymd.format(new Date()), 15) + "' And \n"
//                    + "l.acno=a.acno and l.acno = m.acno And m.cabno=l.cabno and m.lockerType=l.lockerType and m.lockerno = l.lockerno and \n"
//                    + "l.rent <> 0 and l.status='U' and l.BrnCode = '" + brCode + "' \n"
//                    + "union all \n"
//                    + "Select m.CabNo,m.LockerType,m.LockerNo,l.Acno,a.acname,l.Rent, date_format(l.RentDueDt,'%d/%m/%Y') as RentDueDt,l.status,substring(l.acno,1,2) as curbrcode \n"
//                    + "From lockeracmaster m,lockerrent l, gltable a  Where rentDueDt<='" + CbsUtil.dateAdd(ymd.format(new Date()), 15) + "' And \n"
//                    + "l.acno=a.acno and l.acno = m.acno And m.cabno=l.cabno and m.lockerType=l.lockerType and m.lockerno = l.lockerno and \n"
//                    + "l.rent <> 0 and l.status='U' and l.BrnCode = '" + brCode + "' \n"
//                    + ") aa,\n"
//                    + "(select ci.Acno as acno, ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState \n"
//                    + " from customerid ci, cbs_customer_master_detail cm, branchmaster br where ci.CustId = cast(cm.customerid as unsigned) and \n"
//                    + " br.brncode=cast('" + brCode + "' as unsigned)\n"
//                    + " union all \n"
//                    + " select ci.Acno as acno, ifnull(br.State,'') as stateCode, ifnull(br.State,'') as brState \n"
//                    + " from gltable ci, branchmaster br where  br.brncode=cast('" + brCode + "' as unsigned)) bb \n"
//                    + "  where aa.acno = bb.acno order by aa.cabno,aa.lockerno").getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return gridList;
    }

    public List gridAutoLoadForAllAccounts(String brCode) throws ApplicationException {
        List gridList = new ArrayList();
        try {
            gridList = em.createNativeQuery("Select m.CabNo,m.LockerType,m.LockerNo,l.Acno,a.CustName,l.Rent,"
                    + "date_format(l.RentDueDt,'%d/%m/%Y'),l.status,a.curbrcode From lockeracmaster m,lockerrent l, accountmaster a "
                    + " Where rentDueDt<='" + ymd.format(new Date()) + "' And "
                    + "l.acno=a.acno and l.acno = m.acno And m.cabno=l.cabno and m.lockerType=l.lockerType and m.lockerno = l.lockerno and "
                    + "l.rent <> 0 and l.status='U' and l.BrnCode = '" + brCode + "' order by m.cabno,m.lockerno").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return gridList;
    }

    public String lockerNoCheck(String brCode, float lockerNo) throws ApplicationException {
        String msg = null;
        try {
            List chkList = em.createNativeQuery("select LockerNo from lockeracmaster where LockerNo=" + lockerNo + " and brncode='" + brCode + "'").getResultList();
            if (chkList.isEmpty()) {
                msg = "Locker Number Not Exists!!!";
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return msg;

    }

    public List gridLoadForSingleAccount(String brCode, float lockerNo) throws ApplicationException {
        List gridList = new ArrayList();
        try {
            String rentDueDt = CbsUtil.dateAdd(ymd.format(new Date()), 15);
            Query selectQuery = em.createNativeQuery("Select m.CabNo,m.LockerType,m.LockerNo,l.Acno,a.CustName,l.Rent,date_format(l.RentDueDt,'%d/%m/%Y'),"
                    + " l.status,a.curbrcode, ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState,freq_year  "
                    + " From lockeracmaster m,lockerrent l,accountmaster a, customerid ci, cbs_customer_master_detail cm, branchmaster br   "
                    + " Where rentDueDt<='" + rentDueDt + "' And l.acno=a.acno And l.acno=m.acno "
                    + " and l.brnCode ='" + brCode + "' And m.cabno=l.cabno and m.lockerType=l.lockerType and m.lockerno = l.lockerno and l.rent <> 0 "
                    + " and l.status='U' and l.lockerno=" + lockerNo + " and l.acno=a.acno and a.acno = ci.acno and ci.CustId = cast(cm.customerid as unsigned) and "
                    + " br.brncode=cast('" + brCode + "' as unsigned)"
                    + " union all   \n"
                    + " Select m.CabNo,m.LockerType,m.LockerNo,l.Acno,a.acname,l.Rent,date_format(l.RentDueDt,'%d/%m/%Y'),\n"
                    + " l.status,substring(l.acno,1,2), ifnull(br.State,'') as stateCode, ifnull(br.State,'') as brState,freq_year  \n"
                    + " From lockeracmaster m,lockerrent l,gltable a, branchmaster br   \n"
                    + " Where rentDueDt<='" + rentDueDt + "' And l.acno=a.acno And l.acno=m.acno \n"
                    + " and l.brnCode ='" + brCode + "' And m.cabno=l.cabno and m.lockerType=l.lockerType and m.lockerno = l.lockerno and l.rent <> 0 \n"
                    + " and l.status='U' and l.lockerno=" + lockerNo + " and br.brncode=cast('" + brCode + "' as unsigned)  ");
            gridList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return gridList;
    }

    public String lockerRentPosting(List<LockerRentDetail> rentList, String lockerAcNo, String repType,
            String user, String brCode) throws ApplicationException {
        String message = "true";
//        List<List> returnList = new ArrayList<List>();
//        List<String> strList = new ArrayList<String>();
//        List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
        //strList.add(message);
        Double totalChgAmt = 0d;
        UserTransaction ut = context.getUserTransaction();
        Map<String, Double> map = new HashMap<String, Double>();
        try {
            ut.begin();
            int staxModuleActive = 0;
            String dt = sdf.format(new Date());
            String tempBd = "";
            List tempBdLt = em.createNativeQuery("SELECT DATE_FORMAT(date,'%Y%m%d') FROM bankdays WHERE DAYENDFLAG='N' AND Brncode='" + brCode + "'").getResultList();
            if (!tempBdLt.isEmpty()) {
                Vector tempBdLtV = (Vector) tempBdLt.get(0);
                tempBd = tempBdLtV.get(0).toString();
            } else {
                ut.rollback();
                message = "Server date not found.";
//                strList.add(message);
//                returnList.add(strList);
//                returnList.add(smsList);
//                return returnList;
                return message;
            }

            List stTaxList = em.createNativeQuery("SELECT code FROM parameterinfo_report WHERE reportname='STAXMODULE_ACTIVE'").getResultList();
            if (!stTaxList.isEmpty()) {
                Vector stTaxListV = (Vector) stTaxList.get(0);
                staxModuleActive = Integer.parseInt(stTaxListV.get(0).toString());
            } else {
                ut.rollback();
                message = "Error in getting tax module detail.";
//                strList.add(message);
//                returnList.add(strList);
//                returnList.add(smsList);
//                return returnList;
                return message;
            }
            String taxFlag = "";
            if (repType.equalsIgnoreCase("ALL LOCKERS")) {
                if (staxModuleActive == 1) {
                    taxFlag = "Y";
                } else {
                    taxFlag = "N";
                }
                String tmpDetails = "VCH. Of Locker Rent";
//                strList.add(tmpDetails);
//                List<List> bulkResultList = ftsDrCr.postLockerRent(rentList, lockerAcNo, user, tmpDetails, 32,
//                        taxFlag, "N", brCode);
                List<List> bulkResultList = ftsDrCr.postLockerRent(rentList, lockerAcNo, user, tmpDetails, 113,
                        taxFlag, "N", brCode);
                List<String> bulkStrList = (List<String>) bulkResultList.get(0);
                //  if (!message.substring(0, 4).equalsIgnoreCase("TRUE"))
                if (!bulkStrList.get(0).substring(0, 4).equalsIgnoreCase("true")) {
                    ut.rollback();
//                    strList.add(bulkStrList.get(0));
//                    returnList.add(strList);
//                    returnList.add(smsList);
//                    return returnList;
                    return bulkStrList.get(0);
                } else {
                    ut.commit();
                    //RChauhan sms sending to post all lockers
                    try {
                        List<SmsToBatchTo> smsList = (List<SmsToBatchTo>) bulkResultList.get(1);
                        if (!smsList.isEmpty()) {
                            smsFacade.sendSmsToBatch(smsList);
                        }
                    } catch (Exception e) {
                        System.out.println("Problem In SMS Sending To Batch In "
                                + "Lockers Rent Colllection." + e.getMessage());
                    }
//                    strList.add(bulkStrList.get(0));
//                    returnList.add(strList);
//                    returnList.add(smsList);
//                    return returnList;
                    return bulkStrList.get(0);
                }
            } else {
                LockerRentDetail chargesObj = rentList.get(0);
                String gridAcNo = chargesObj.getAcno();
                Float cabNo = chargesObj.getCabno();
                String custState = chargesObj.getCustState();
                String branchState = chargesObj.getBrnchState();
                Double rent = chargesObj.getPenalty();
                totalChgAmt = rent;

                Float lockerNo = chargesObj.getLockerno();
                String lockerType = chargesObj.getLockertype();
                String rentDueDate = chargesObj.getRentduedt();
                int advPayYr = Integer.parseInt(chargesObj.getAdvPayYr());
                double taxAmt = 0f, taxAmtIgst = 0f;

                float trsNo = ftsPost.getTrsNo();
                float recNo = 0;
                String tmpDetails = "Locker Rent";
                if (brCode.equals(chargesObj.getBrCode())) {
                    recNo = ftsPost.getRecNo();
//                    message = ftsPost.ftsPosting43CBS(gridAcNo, 2, 1, rent, dt, dt, user, brCode, ftsPost.getCurrentBrnCode(gridAcNo), 32, tmpDetails,
//                            trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", null, null, null, null, null, null, null, 0.0f, "N", "", "");
                    message = ftsPost.ftsPosting43CBS(gridAcNo, 2, 1, rent, dt, dt, user, brCode, ftsPost.getCurrentBrnCode(gridAcNo), 113, tmpDetails,
                            trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", null, null, null, null, null, null, null, 0.0f, "N", "", "", "");

                    if (!message.substring(0, 4).equalsIgnoreCase("true")) {
                        ut.rollback();
//                        strList.add(message);
//                        returnList.add(strList);
//                        returnList.add(smsList);
//                        return returnList;
                        return message;
                    }
                    recNo = ftsPost.getRecNo();
                    tmpDetails = "Locker Rent for Locker No = " + lockerNo + " from account No = " + gridAcNo;
//                    message = ftsPost.ftsPosting43CBS(lockerAcNo, 2, 0, rent, dt, dt, user, brCode, ftsPost.getCurrentBrnCode(lockerAcNo), 32, tmpDetails,
//                            trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", null, null, null, null, null, null, null, 0.0f, "N", "", "");
                    message = ftsPost.ftsPosting43CBS(lockerAcNo, 2, 0, rent, dt, dt, user, brCode, ftsPost.getCurrentBrnCode(lockerAcNo), 113, tmpDetails,
                            trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", null, null, null, null, null, null, null, 0.0f, "N", "", "", "");

                    if (!message.substring(0, 4).equalsIgnoreCase("true")) {
                        ut.rollback();
//                        strList.add(message);
//                        returnList.add(strList);
//                        returnList.add(smsList);
//                        return returnList;
                        return message;
                    }
                } else {
                    tmpDetails = "Locker Rent";
                    recNo = ftsPost.getRecNo();
//                    message = interFts.cbsPostingSx(gridAcNo, 1, dt, rent, 0d, 2, tmpDetails, 0f, "A", "", "", 3, 0f, recNo, 32,
//                            ftsPost.getCurrentBrnCode(gridAcNo), brCode, user, "SYSTEM", trsNo, "", "");
                    message = interFts.cbsPostingSx(gridAcNo, 1, dt, rent, 0d, 2, tmpDetails, 0f, "A", "", "", 3, 0f, recNo, 113,
                            ftsPost.getCurrentBrnCode(gridAcNo), brCode, user, "SYSTEM", trsNo, "", "");
                    if (!message.substring(0, 4).equalsIgnoreCase("true")) {
                        ut.rollback();
//                        strList.add(message);
//                        returnList.add(strList);
//                        returnList.add(smsList);
//                        return returnList;
                        return message;
                    }

                    String acNature = ftsPost.getAccountNature(gridAcNo);
                    ftsPost.updateBalance(acNature, gridAcNo, 0, rent, "Y", "Y");

                    recNo = ftsPost.getRecNo();
                    tmpDetails = "Locker Rent for Locker No = " + lockerNo + " from account No = " + gridAcNo;
//                    message = interFts.cbsPostingCx(lockerAcNo, 0, dt, rent, 0d, 2, tmpDetails, 0f, "A", "", "", 3, 0f, recNo, 32,
//                            ftsPost.getCurrentBrnCode(lockerAcNo), brCode, user, "SYSTEM", trsNo, "", "");
                    message = interFts.cbsPostingCx(lockerAcNo, 0, dt, rent, 0d, 2, tmpDetails, 0f, "A", "", "", 3, 0f, recNo, 113,
                            ftsPost.getCurrentBrnCode(lockerAcNo), brCode, user, "SYSTEM", trsNo, "", "");
                    if (!message.substring(0, 4).equalsIgnoreCase("true")) {
                        ut.rollback();
//                        strList.add(message);
//                        returnList.add(strList);
//                        returnList.add(smsList);
//                        return returnList;
                        return message;
                    }
                }

                if (staxModuleActive == 1) {
                    /* Entry for Service tax */
//                    List resultList = findTax(rent);
//                    taxAmt = Double.parseDouble(resultList.get(0).toString());
//                    
//                    List resultList1 = ftsDrCr.fnTaxApplicableROT(tempBd);
//                    if (resultList1.isEmpty()) {
//                        throw new ApplicationException("Gl Account for tax does not exists.");
//                        
//                    }
//                    Vector resultList1V = (Vector) resultList1.get(0);
//                    String sTaxAcno = resultList1V.get(3).toString();
//                    sTaxAcno = brCode + sTaxAcno + "01";
                    if (custState.equalsIgnoreCase(branchState)) {
                        map = interFts.getTaxComponent(rent, tempBd);
                    } else {
                        map = interFts.getIgstTaxComponent(rent, tempBd);
                    }
                    Set<Entry<String, Double>> set = map.entrySet();
                    Iterator<Entry<String, Double>> it = set.iterator();
                    while (it.hasNext()) {
                        Entry entry = it.next();
                        taxAmt = taxAmt + Double.parseDouble(entry.getValue().toString());
                    }

                    tmpDetails = "GST for Locker Rent";
                    if (brCode.equals(chargesObj.getBrCode())) {
                        recNo = ftsPost.getRecNo();
//                        message = ftsPost.ftsPosting43CBS(gridAcNo, 2, 1, taxAmt, dt, dt, user, brCode, ftsPost.getCurrentBrnCode(gridAcNo), 32, tmpDetails,
//                                trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", null, null, null, null, null, null, null, 0.0f, "N", "", "");
                        message = ftsPost.ftsPosting43CBS(gridAcNo, 2, 1, taxAmt, dt, dt, user, brCode, ftsPost.getCurrentBrnCode(gridAcNo), 71, tmpDetails,
                                trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", null, null, null, null, null, null, null, 0.0f, "N", "", "", "");

                        if (!message.substring(0, 4).equalsIgnoreCase("true")) {
                            ut.rollback();
//                            strList.add(message);
//                            returnList.add(strList);
//                            returnList.add(smsList);
//                            return returnList;
                            return message;
                        }

                        Set<Entry<String, Double>> setS = map.entrySet();
                        Iterator<Entry<String, Double>> itS = set.iterator();
                        while (itS.hasNext()) {
                            Entry entry = itS.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = brCode + keyArray[1];
                            tmpDetails = description.toUpperCase() + " for Locker Rent for. " + gridAcNo;
                            double taxAmount = Double.parseDouble(entry.getValue().toString());
                            recNo = ftsPost.getRecNo();
                            message = ftsPost.ftsPosting43CBS(taxHead, 2, 0, taxAmount, dt, dt, user, brCode, ftsPost.getCurrentBrnCode(taxHead), 71, tmpDetails,
                                    trsNo, recNo, 0, "", "Y", "SYSTEM", "A", 3, "", null, null, null, null, null, null, null, 0.0f, "N", "", "", "");

                            if (!message.substring(0, 4).equalsIgnoreCase("true")) {
                                ut.rollback();
                                //                            strList.add(message);
                                //                            returnList.add(strList);
                                //                            returnList.add(smsList);
                                //                            return returnList;
                                return message;
                            }
                        }
                    } else {
                        tmpDetails = "GST for Locker Rent";
                        recNo = ftsPost.getRecNo();
//                        message = interFts.cbsPostingSx(gridAcNo, 1, dt, taxAmt, 0d, 2, tmpDetails, 0f, "A", "", "", 3, 0f, recNo, 32,
//                                ftsPost.getCurrentBrnCode(gridAcNo), brCode, user, "SYSTEM", trsNo, "", "");
                        message = interFts.cbsPostingSx(gridAcNo, 1, dt, taxAmt, 0d, 2, tmpDetails, 0f, "A", "", "", 3, 0f, recNo, 113,
                                ftsPost.getCurrentBrnCode(gridAcNo), brCode, user, "SYSTEM", trsNo, "", "");
                        if (!message.substring(0, 4).equalsIgnoreCase("true")) {
                            ut.rollback();
//                            strList.add(message);
//                            returnList.add(strList);
//                            returnList.add(smsList);
//                            return returnList;
                            return message;
                        }

                        String acNature = ftsPost.getAccountNature(gridAcNo);
                        ftsPost.updateBalance(acNature, gridAcNo, 0, taxAmt, "Y", "Y");

                        Set<Entry<String, Double>> setS = map.entrySet();
                        Iterator<Entry<String, Double>> itS = set.iterator();
                        while (itS.hasNext()) {
                            Entry entry = itS.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = brCode + keyArray[1];
                            tmpDetails = description.toUpperCase() + " for Locker Rent for. " + gridAcNo;
                            double taxAmount = Double.parseDouble(entry.getValue().toString());
                            recNo = ftsPost.getRecNo();
                            //                        message = interFts.cbsPostingCx(sTaxAcno, 0, dt, taxAmt, 0d, 2, tmpDetails, 0f, "A", "", "", 3, 0f, recNo, 32,
                            //                                ftsPost.getCurrentBrnCode(sTaxAcno), brCode, user, "SYSTEM", trsNo, "", "");
                            message = interFts.cbsPostingCx(taxHead, 0, dt, taxAmount, 0d, 2, tmpDetails, 0f, "A", "", "", 3, 0f, recNo, 71,
                                    ftsPost.getCurrentBrnCode(taxHead), brCode, user, "SYSTEM", trsNo, "", "");
                            if (!message.substring(0, 4).equalsIgnoreCase("true")) {
                                ut.rollback();
                                //                            strList.add(message);
                                //                            returnList.add(strList);
                                //                            returnList.add(smsList);
                                //                            return returnList;
                                return message;
                            }
                        }
                    }
                    totalChgAmt = totalChgAmt + taxAmt;
                }
                SimpleDateFormat ddmmyyy = new SimpleDateFormat("dd/MM/yyyy");
                rentDueDate = sdf.format(ddmmyyy.parse(rentDueDate));
                // String dueDt = CbsUtil.yearAdd(rentDueDate, 1);
                String dueDt = CbsUtil.yearAdd(rentDueDate, advPayYr);

                Query insertQuery = em.createNativeQuery("Insert Into lockerrecon(CABNO, LOCKERTYPE,LOCKERNO,ACNO,RENT,PAYDT,ENTERBY) "
                        + "Values(" + cabNo + ",'" + lockerType + "'," + lockerNo + ",'" + gridAcNo + "'," + rent + ",'" + tempBd + "','" + user + "')");
                int var = insertQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Problem in data insertion.");
                }
                List chk = em.createNativeQuery("Select lockerNo From lockerrent Where CabNo= " + cabNo + " and LockerType = '" + lockerType
                        + "' and LockerNo = " + lockerNo + " And RentDueDt='" + rentDueDate + "' and Status='U' and brnCode = '" + brCode + "'").getResultList();

                if (chk.isEmpty()) {
                    throw new ApplicationException("Data does not exist for updation.");
                }
                Query updateLockerRent = em.createNativeQuery("Update lockerrent Set Status='P',Txndate='" + tempBd + "' Where status='U' and CabNo= "
                        + cabNo + " And LockerType = '" + lockerType + "' And LockerNo = " + lockerNo + " And RentDueDt='" + rentDueDate
                        + "' and brnCode = '" + brCode + "'");
                var = updateLockerRent.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Problem in data updation.");
                }
                Query insertQuery1 = em.createNativeQuery("Insert Into lockerrent(cabno,lockerType,lockerNo,Acno,Rent,issueDt,enterBy,RentDueDt,"
                        + "TxnDate,status,brncode) select cabno,lockerType,lockerNo,Acno,Rent,issueDt,'" + user + "','" + dueDt
                        + "', TxnDate,'U',brncode from lockerrent where CabNo= " + cabNo + " And LockerType = '" + lockerType + "' And LockerNo = "
                        + lockerNo + " And RentDueDt='" + rentDueDate + "' and brnCode = '" + brCode + "'");
                var = insertQuery1.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Problem in data insertion.");
                }
                ftsPost.lastTxnDateUpdation(gridAcNo);
                ut.commit();
                // Ravendra sms sending in case of single locker
                List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
                try {
                    SmsToBatchTo to = new SmsToBatchTo();
                    to.setAcNo(chargesObj.getAcno());
                    to.setCrAmt(0d);
                    to.setDrAmt(totalChgAmt);
                    to.setTranType(2);
                    to.setTy(1);
                    to.setTxnDt(dmy.format(new Date()));
                    to.setTemplate(SmsType.TRANSFER_WITHDRAWAL);

                    smsList.add(to);
                    smsFacade.sendSmsToBatch(smsList);
                } catch (Exception e) {
                    System.out.println("Problem In SMS Sending To Batch In "
                            + "Lockers Rent Colllection." + e.getMessage());
                }
//                strList.add(message.substring(0, 4) + trsNo);
//                returnList.add(strList);
//                returnList.add(smsList);
//                return returnList;
                return message.substring(0, 4) + trsNo;
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

    /**
     *
     * @param commAmt
     * @return
     */
//    public List findTax(double commAmt) throws ApplicationException {
//        List<String> result = new ArrayList<String>();
//        Integer roundNo = 0;
//        String appType = "";
//        String message = "true";
//        double ttlTaxAmt = 0d;
//        try {
//            List resultTaxMaster = em.createNativeQuery("SELECT RoundUpto FROM taxmaster WHERE ROTApplyOn='C' AND "
//                    + "applicableFlag='Y' AND auth='Y' AND applicableDt <= (DATE_FORMAT(now(),'%Y%m%d')) LIMIT 1").getResultList();
//            if (resultTaxMaster.size() <= 0) {
//                message = "TAX NOT DEFINED!!!";
//            } else {
//                Vector resultVect = (Vector) resultTaxMaster.get(0);
//                roundNo = Integer.parseInt(resultVect.get(0).toString());
//            }
//            List taxApplicableROTList = ftsDrCr.fnTaxApplicableROT(sdf.format(new Date()));
//            if (taxApplicableROTList.size() <= 0) {
//                message = "Rate Of Tax not Found !!!";
//            } else {
//                Vector resultVect = (Vector) taxApplicableROTList.get(0);
//                appType = resultVect.get(0).toString();
//            }
//            String taxAmt = taxAmount(commAmt, appType);
//            if (taxAmt.contains("!!!")) {
//                message = taxAmt;
//            } else {
//                ttlTaxAmt = CbsUtil.round(Double.parseDouble(taxAmt), roundNo);
//            }
//            result.add(String.valueOf(ttlTaxAmt));
//            result.add(message);
//            return result;
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//        // return result;  commented by sudhir
//    }
    /**
     *
     * @param amt
     * @param type
     * @return
     */
//    public String taxAmount(double amt, String type) throws ApplicationException {
//        Float minAmt;
//        Float maxAmt;
//        Float balance = null;
//        try {
//            List resultlist = em.createNativeQuery("select minAmt,maxamt from taxmaster where type='" + type + "' and"
//                    + " ApplicableFlag='Y' and ApplicableDt in (select max(ApplicableDt) from taxmaster where type='" + type + "' "
//                    + "and ApplicableFlag='Y')").getResultList();
//            if (resultlist.size() <= 0) {
//                return "No Data in TaxMaster !!!";
//            } else {
//                Vector resultVect = (Vector) resultlist.get(0);
//                minAmt = Float.parseFloat(resultVect.get(0).toString());
//                maxAmt = Float.parseFloat(resultVect.get(1).toString());
//            }
//            List resultlistTaxMaster = em.createNativeQuery("Select ('" + amt + "'* ROT)/100  from taxmaster where type='" + type + "' and "
//                    + "ApplicableFlag='Y' and ApplicableDt in (select max(ApplicableDt) from taxmaster where type='" + type + "' and "
//                    + "ApplicableFlag='Y')").getResultList();
//            if (resultlistTaxMaster.size() <= 0) {
//                return "No Data in TaxMaster !!!";
//            } else {
//                Vector resultVect = (Vector) resultlistTaxMaster.get(0);
//                balance = Float.parseFloat(resultVect.get(0).toString());
//            }
//            
//            if (balance < minAmt) {
//                balance = minAmt;
//            } else if (balance > maxAmt) {
//                balance = maxAmt;
//            }
//            return balance.toString();
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//        // return balance.toString(); commented by sudhir 
//    }
    public List gridLoad(boolean flag, String effDate) throws ApplicationException {
        List gridList = new ArrayList();
        try {
            if (flag) {
                gridList = em.createNativeQuery("Select DATE_FORMAT(EFFECTIVEDT,'%d/%m/%Y'),LOCKERTYPE,CUSTCAT,RENT,ENTERBY From lockerrentmaster "
                        + "order by EFFECTIVEDT desc").getResultList();
            } else {
                gridList = em.createNativeQuery("Select DATE_FORMAT(EFFECTIVEDT,'%d/%m/%Y'),LOCKERTYPE,CUSTCAT,RENT,ENTERBY From lockerrentmaster "
                        + "where effectivedt='" + effDate + "' order by EFFECTIVEDT desc").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return gridList;
    }

    public String addDetail(boolean flag, String lockerType, String custCat, double rent, String effDt, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var1, var2, var3;
            if ((lockerType == null) || (custCat == null) || (effDt == null) || (enterBy == null)) {
                ut.rollback();
                return "Please Check All Fields!!!.";
            }
            List chkList = em.createNativeQuery("select * from lockerrentmaster where LockerType= '" + lockerType + "' and custcat= '" + custCat + "' and effectiveDt= '" + effDt + "'").getResultList();
            if (!chkList.isEmpty()) {
                ut.rollback();
                return "Rent For This Category Already Entered Today.";
            }

            Query insertQuery = em.createNativeQuery("insert into lockerrentmaster (lockertype,custcat,rent,effectivedt,enterby) values( '" + lockerType + "','" + custCat + "'," + rent + ",'" + effDt + "','" + enterBy + "')");
            var1 = insertQuery.executeUpdate();

            List chkList1 = em.createNativeQuery("SELECT  lockerno FROM lockeracmaster WHERE LOCKERTYPE='" + lockerType + "' AND CUSTCAT='" + custCat + "'").getResultList();
            if (chkList1.isEmpty()) {
                if ((var1 > 0)) {
                    ut.commit();
                    return "Record Inserted Successfully";
                } else {
                    ut.rollback();
                    return "Record Not Inserted Successfully.";
                }
            } else {
                // Query updateQuery = em.createNativeQuery("UPDATE  lockerrent SET RENT=" + rent + "  WHERE lockerno  IN (SELECT  lockerno FROM lockeracmaster WHERE LOCKERTYPE='" + lockerType + "' AND CUSTCAT='" + custCat + "') " + "AND RENTDUEDT>'" + effDt + "' AND STATUS='U' and LOCKERTYPE='" + lockerType + "'");

                Query updateQuery = em.createNativeQuery("update lockeracmaster a,lockerrent b set b.rent= " + rent + "   where  a.LOCKERTYPE='" + lockerType + "' AND a.CUSTCAT='" + custCat + "'"
                        + "and a.cabno = b.cabno and a.lockertype = b.lockertype and a.lockerno = a.lockerno and a.acno=b.acno "
                        + "and b.status = 'U' and rentduedt > '" + effDt + "'");
                var2 = updateQuery.executeUpdate();

                Query updateQuery1 = em.createNativeQuery("UPDATE lockeracmaster SET RENT=" + rent + " WHERE lockerno IN (SELECT DISTINCT lockerno FROM lockerrent WHERE STATUS='U' AND LOCKERTYPE='" + lockerType + "' AND CUSTCAT='" + custCat + "') " + "AND LOCKERTYPE='" + lockerType + "' AND CUSTCAT='" + custCat + "'");
                var3 = updateQuery1.executeUpdate();
                if (var2 > 0 && var3 > 0) {
                    ut.commit();
                    return "Record Inserted Successfully";
                } else {
                    ut.rollback();
                    return "Record Not Inserted Successfully.";
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List lockerNoLostFocus1(String brCode, String cabNo, String lockerNo, String lockerTy) throws ApplicationException {
        List resultList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select cast(lr.cabNo as UNSIGNED),lr.lockerType,cast(lr.LockerNo as UNSIGNED),lr.Acno,a.custName,"
                    + "lr.Auth From lockeracmaster lr, accountmaster a Where cabno= " + cabNo + " and  lockertype='" + lockerTy + "' and lockerno="
                    + lockerNo + " and a.acno=lr.acno and lr.brnCode='" + brCode + "'  "
                    + "union \n"
                    + "select cast(lr.cabNo as UNSIGNED),lr.lockerType,cast(lr.LockerNo as UNSIGNED),lr.Acno,a.acname,\n"
                    + "lr.Auth From lockeracmaster lr, gltable a Where cabno= " + cabNo + " and  lockertype='" + lockerTy + "' \n"
                    + "and lockerno= " + lockerNo + " and a.acno=lr.acno and lr.brnCode='" + brCode + "' order by 3");
            resultList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultList;
    }

    public List rentDueDt(String brCode, String cabNo, String lockerNo, String lockerTy) throws ApplicationException {
        List resultList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select DATE_FORMAT(max(RentDueDt),'%d/%m/%Y') From lockerrent l Where cabNo= "
                    + cabNo + " and  lockerNo=" + lockerNo + " and lockertype = '" + lockerTy + "' and brnCode='" + brCode
                    + "' and status ='U'");
            resultList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultList;
    }

    public List gridLoad(String brCode, String cabNo, String lockerNo, String lockerTy, String acNo) throws ApplicationException {
        List gridList = new ArrayList();
        try {

            String acNature = ftsPost.getAcNatureByCode(acNo.substring(2, 4));
            Query selectQuery;
            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                selectQuery = em.createNativeQuery("select cast(lr.cabno as UNSIGNED),lr.lockerType,cast(lr.lockerNo as UNSIGNED),lr.Acno,ac.acname,lr.rent,"
                        + " DATE_FORMAT(lr.rentduedt,'%d/%m/%Y'),lm.mode,lm.adoperator1,lm.adoperator2,lm.adoperator3,lm.adoperator4,lm.security"
                        + " from lockerrent lr,lockeracmaster lm,gltable ac where lr.acno=ac.acno  and lr.CABNO= " + cabNo + " and lm.brncode = lr.brncode"
                        + " and lm.brnCode='" + brCode + "' And lr.lockertype= '" + lockerTy + "' and lr.lockerno= " + lockerNo + "  and lr.acno ='" + acNo
                        + "' and lr.status='U'  and lr.acno = lm.acno and lr.CABNO= lm.CABNO and lr.lockertype= lm.lockertype and lr.lockerno= lm.lockerno");
            } else {
                selectQuery = em.createNativeQuery("select cast(lr.cabno as UNSIGNED),lr.lockerType,cast(lr.lockerNo as UNSIGNED),lr.Acno,ac.custName,lr.rent,"
                        + " DATE_FORMAT(lr.rentduedt,'%d/%m/%Y'),lm.mode,lm.adoperator1,lm.adoperator2,lm.adoperator3,lm.adoperator4,lm.security"
                        + " from lockerrent lr,lockeracmaster lm,accountmaster ac where lr.acno=ac.acno  and lr.CABNO= " + cabNo + " and lm.brncode = lr.brncode"
                        + " and lm.brnCode='" + brCode + "' And lr.lockertype= '" + lockerTy + "' and lr.lockerno= " + lockerNo + "  and lr.acno ='" + acNo
                        + "' and lr.status='U'  and lr.acno = lm.acno and lr.CABNO= lm.CABNO and lr.lockertype= lm.lockertype and lr.lockerno= lm.lockerno");
            }
            gridList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return gridList;
    }

//    public String surrenderLocker(String brCode, String cabNo, String lockerNo, String lockerTy) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            ut.begin();
//            int var = 0;
//
//            List chkList = em.createNativeQuery("select lockerNo From lockermaster Where  cabno = " + cabNo + " and LockerType='" + lockerTy + "' and brnCode='" + brCode
//                    + "' And LockerNo=" + Float.parseFloat(lockerNo)).getResultList();
//            if (chkList.isEmpty()) {
//                throw new ApplicationException("Data does not exist for this Locker");
//            }
//            List tempBdList = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
//            if (tempBdList.isEmpty()) {
//                throw new ApplicationException("Problem in getting bank date");
//            }
//
//            Vector Lst = (Vector) tempBdList.get(0);
//            String bankDt = Lst.get(0).toString();
////            Query updateQuery = em.createNativeQuery("Update lockermaster Set OcFlag='N' Where  cabno = " + cabNo + " and LockerType='" + lockerTy
////                    + "' and brnCode='" + brCode + "' And LockerNo=" + Float.parseFloat(lockerNo));
//            Query updateQuery = em.createNativeQuery("Update lockermaster Set OcFlag='N' Where  cabno = " + cabNo + " and LockerType='" + lockerTy
//                    + "' and brnCode='" + brCode + "' And LockerNo=" + Float.parseFloat(lockerNo));
//            var = updateQuery.executeUpdate();
//            if (var <= 0) {
//                throw new ApplicationException("Problem in data updation");
//            }
//
//            List chkList1 = em.createNativeQuery("select a.cabno,a.lockertype,a.lockerno,a.acno,a.rent,r.rentduedt,a.issuedt from lockeracmaster a,lockerrent r,"
//                    + "accountmaster ac where a.acno=r.acno and a.acno= ac.acno and a.cabno= " + cabNo + " and a.lockertype='" + lockerTy
//                    + "' and a.lockerno=" + Float.parseFloat(lockerNo) + " and a.brncode=r.brncode and a.BrnCode='" + brCode + "'  order by r.rentDueDt").getResultList();
//            if (chkList1.isEmpty()) {
//                throw new ApplicationException("Data does not exist for this Locker");
//            }
//
//            Vector recLst = (Vector) chkList1.get(0);
//            String acno = recLst.get(3).toString();
//            String rent = recLst.get(4).toString();
//            String rentDueDt = recLst.get(5).toString();
//            String issDt = recLst.get(6).toString();
//
//            Query insertQuery = em.createNativeQuery("Insert Into lockersurrender(cabno,LockerType,LockerNo,Acno,Rent,RentDueDt,surrenderdt,issuedt,brncode) "
//                    + "values( " + cabNo + ",'" + lockerTy + "'," + Float.parseFloat(lockerNo) + ",'" + acno + "'," + rent + ",'" + rentDueDt + "','" + bankDt + "','" + issDt + "','"
//                    + brCode + "')");
//            var = insertQuery.executeUpdate();
//            if (var <= 0) {
//                throw new ApplicationException("Problem in data insertion");
//            }
//            Query deleteQuery = em.createNativeQuery("Delete from lockeracmaster Where cabNo = " + cabNo + " and LockerType='" + lockerTy
//                    + "' And LockerNo=" + Float.parseFloat(lockerNo) + " and brncode='" + brCode + "' ");
//            var = deleteQuery.executeUpdate();
//            if (var <= 0) {
//                throw new ApplicationException("Problem in data deletion");
//            }
//            Query deleteQuery1 = em.createNativeQuery("Delete from lockerrent Where cabNo = " + cabNo + " and LockerType='" + lockerTy + "' And LockerNo=" + Float.parseFloat(lockerNo) + " and brncode='" + brCode + "'");
//            var = deleteQuery1.executeUpdate();
//            if (var <= 0) {
//                throw new ApplicationException("Problem in data deletion");
//            }
//            ut.commit();
//            return "true";
//
//        } catch (Exception e) {
//            try {
//                ut.rollback();
//                throw new ApplicationException(e);
//            } catch (IllegalStateException ex) {
//                throw new ApplicationException(ex.getMessage());
//            } catch (SecurityException ex) {
//                throw new ApplicationException(ex.getMessage());
//            } catch (SystemException ex) {
//                throw new ApplicationException(ex.getMessage());
//            }
//        }
//    }
    public String surrenderLocker(String brCode, String cabNo, String lockerNo, String lockerTy, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;

            List chkList = em.createNativeQuery("select lockerNo From lockermaster Where  cabno = " + cabNo + " and LockerType='" + lockerTy + "' and brnCode='" + brCode
                    + "' And LockerNo=" + Float.parseFloat(lockerNo)).getResultList();
            if (chkList.isEmpty()) {
                throw new ApplicationException("Data does not exist for this Locker");
            }
            List tempBdList = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
            if (tempBdList.isEmpty()) {
                throw new ApplicationException("Problem in getting bank date");
            }

            Vector Lst = (Vector) tempBdList.get(0);
            String bankDt = Lst.get(0).toString();

            List chkList1 = em.createNativeQuery("select a.cabno,a.lockertype,a.lockerno,a.acno,a.rent,r.rentduedt,a.issuedt from lockeracmaster a,lockerrent r,"
                    + "accountmaster ac where a.acno=r.acno and a.acno= ac.acno and a.cabno= " + cabNo + " and a.lockertype='" + lockerTy
                    + "' and a.lockerno=" + Float.parseFloat(lockerNo) + " and a.brncode=r.brncode and a.BrnCode='" + brCode + "'  "
                    + "union\n"
                    + "select a.cabno,a.lockertype,a.lockerno,a.acno,a.rent,r.rentduedt,a.issuedt from lockeracmaster a,lockerrent r,\n"
                    + "gltable ac where a.acno=r.acno and a.acno= ac.acno and a.cabno= " + cabNo + " and a.lockertype='" + lockerTy + "' \n"
                    + "and a.lockerno=" + Float.parseFloat(lockerNo) + " and a.brncode=r.brncode and a.BrnCode='" + brCode + "' and a.cabno = r.cabno and a.lockerno = r.lockerno\n"
                    + "order by 6").getResultList();
            if (chkList1.isEmpty()) {
                throw new ApplicationException("Data does not exist for this Locker");
            }

            Vector recLst = (Vector) chkList1.get(0);
            String acno = recLst.get(3).toString();
            Query insertQuery = em.createNativeQuery("Insert Into locker_surrender_auth(cabno,lockertype,lockerno,acno,enterBy,enterDate,auth,authBy,brncode) "
                    + "values( " + cabNo + ",'" + lockerTy + "'," + Float.parseFloat(lockerNo) + ",'" + acno + "','" + enterBy + "','" + bankDt + "','N','','" + brCode + "')");
            var = insertQuery.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }
            ut.commit();
            return "true";

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String surrenderLockerAuth(String brCode, String cabNo, String lockerNo, String lockerTy, String authBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;

            List chkList = em.createNativeQuery("select lockerNo From lockermaster Where  cabno = " + cabNo + " and LockerType='" + lockerTy + "' and brnCode='" + brCode
                    + "' And LockerNo=" + Float.parseFloat(lockerNo)).getResultList();
            if (chkList.isEmpty()) {
                throw new ApplicationException("Data does not exist for this Locker");
            }
            List tempBdList = em.createNativeQuery("Select Date from bankdays where dayendflag='N' and brncode='" + brCode + "'").getResultList();
            if (tempBdList.isEmpty()) {
                throw new ApplicationException("Problem in getting bank date");
            }

            Vector Lst = (Vector) tempBdList.get(0);
            String bankDt = Lst.get(0).toString();
            Query updateQuery = em.createNativeQuery("Update lockermaster Set OcFlag='N' Where  cabno = " + cabNo + " and LockerType='" + lockerTy
                    + "' and brnCode='" + brCode + "' And LockerNo=" + Float.parseFloat(lockerNo));
            var = updateQuery.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem in data updation");
            }

            List chkList1 = em.createNativeQuery("select a.cabno,a.lockertype,a.lockerno,a.acno,a.rent,r.rentduedt,a.issuedt from lockeracmaster a,lockerrent r,"
                    + "accountmaster ac where a.acno=r.acno and a.acno= ac.acno and a.cabno= " + cabNo + " and a.lockertype='" + lockerTy
                    + "' and a.lockerno=" + Float.parseFloat(lockerNo) + " and a.brncode=r.brncode and a.BrnCode='" + brCode + "'  "
                    + "union\n"
                    + "select a.cabno,a.lockertype,a.lockerno,a.acno,a.rent,r.rentduedt,a.issuedt from lockeracmaster a,lockerrent r,\n"
                    + "gltable ac where a.acno=r.acno and a.acno= ac.acno and a.cabno= " + cabNo + " and a.lockertype='" + lockerTy + "' \n"
                    + "and a.lockerno=" + Float.parseFloat(lockerNo) + " and a.brncode=r.brncode and a.BrnCode='" + brCode + "' and a.cabno = r.cabno and a.lockerno = r.lockerno\n"
                    + "order by 6").getResultList();
            if (chkList1.isEmpty()) {
                throw new ApplicationException("Data does not exist for this Locker");
            }

            Vector recLst = (Vector) chkList1.get(0);
            String acno = recLst.get(3).toString();
            String rent = recLst.get(4).toString();
            String rentDueDt = recLst.get(5).toString();
            String issDt = recLst.get(6).toString();

            Query insertQuery = em.createNativeQuery("Insert Into lockersurrender(cabno,LockerType,LockerNo,Acno,Rent,RentDueDt,surrenderdt,issuedt,brncode) "
                    + "values( " + cabNo + ",'" + lockerTy + "'," + Float.parseFloat(lockerNo) + ",'" + acno + "'," + rent + ",'" + rentDueDt + "','" + bankDt + "','" + issDt + "','"
                    + brCode + "')");
            var = insertQuery.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }
            Query deleteQuery = em.createNativeQuery("Delete from lockeracmaster Where cabNo = " + cabNo + " and LockerType='" + lockerTy
                    + "' And LockerNo=" + Float.parseFloat(lockerNo) + " and brncode='" + brCode + "' ");
            var = deleteQuery.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem in data deletion");
            }
            Query deleteQuery1 = em.createNativeQuery("Delete from lockerrent Where cabNo = " + cabNo + " and LockerType='" + lockerTy + "' And LockerNo=" + Float.parseFloat(lockerNo) + " and brncode='" + brCode + "'");
            var = deleteQuery1.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem in data deletion");
            }

            Query updateQuery1 = em.createNativeQuery("Update locker_surrender_auth Set auth='Y', authBy ='" + authBy + "' Where  cabno = " + cabNo + " and LockerType='" + lockerTy
                    + "' and brnCode='" + brCode + "' and acno = '" + acno + "' And LockerNo=" + Float.parseFloat(lockerNo));
            var = updateQuery1.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem in data updation");
            }
            ut.commit();
            return "true";

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    /**
     * conversion of CBS_REP_LOCKER_RENT_CAL_REPORT report
     */
    public List<LockerRentCalTable> lockerRentCalculationReport(String postOption, float lockerNo, String brCode) throws ApplicationException {
        List<LockerRentCalTable> lockerRentList = new ArrayList<LockerRentCalTable>();
        String tempBD = "";
        try {
            List resultList = em.createNativeQuery("SELECT DATE FROM bankdays WHERE DAYENDFLAG='N' AND BRNCODE='" + brCode + "'").getResultList();
            if (resultList.isEmpty()) {
                throw new ApplicationException("Improper data in bankdays!");
            } else {
                Vector vec = (Vector) resultList.get(0);
                tempBD = vec.get(0).toString();
            }
            if (postOption.equalsIgnoreCase("ALL LOCKERS")) {
                resultList = em.createNativeQuery("Select aa.Acno,aa.Rent,aa.CabNo,aa.LockerType,aa.LockerNo,aa.RentDueDt,aa.custname,bb.stateCode, bb.brState  from "
                        + " (Select l.Acno,l.Rent,m.CabNo,m.LockerType,m.LockerNo,DATE_FORMAT(l.RentDueDt,'%d/%m/%Y') as RentDueDt,a.custname From lockeracmaster m,"
                        + " lockerrent l,accountmaster a Where rentDueDt<=DATE_FORMAT(DATE_ADD('" + tempBD + "',INTERVAL 15 DAY),'%Y%m%d') And "
                        + " m.cabno=l.cabno and m.lockerType=l.lockerType and m.lockerno = l.lockerno and l.rent <> 0 and l.status='U' and "
                        + " m.brncode= l.brncode and m.brncode='" + brCode + "' and l.acno=a.acno)aa,"
                        + " (select ci.Acno as acno, ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState  "
                        + " from customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                        + " where ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brCode + "' as unsigned) and substring(ci.Acno,1,2) = '" + brCode + "')  bb "
                        + " where aa.acno = bb.acno order by aa.acno").getResultList();
            } else {
                resultList = em.createNativeQuery("Select l.Acno,l.Rent,m.CabNo,m.LockerType,m.LockerNo,DATE_FORMAT(l.RentDueDt,'%d/%m/%Y'),"
                        + "a.CustName, ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState  "
                        + "From lockeracmaster m, lockerrent l,accountmaster a , customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                        + "Where rentDueDt<=DATE_FORMAT(DATE_ADD('" + tempBD + "',INTERVAL 15 DAY),'%Y%m%d') And m.brncode='" + brCode
                        + "' and m.brncode=l.brncode and m.cabno=l.cabno and m.lockerType=l.lockerType and m.lockerno = l.lockerno and l.rent <> 0 and l.status='U' "
                        + "and l.lockerno=" + lockerNo + " and l.acno=a.acno and l.acno=a.acno and a.acno = ci.acno and ci.CustId = cast(cm.customerid as unsigned) "
                        + "and br.brncode=cast('" + brCode + "' as unsigned) and substring(ci.Acno,1,2) = '" + brCode + "'").getResultList();
            }
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    LockerRentCalTable lockerRentTable = new LockerRentCalTable();
                    Vector vec = (Vector) resultList.get(i);
                    if (vec.get(0) != null) {
                        lockerRentTable.setAcno(vec.get(0).toString());
                    }
                    if (vec.get(1) != null) {
                        lockerRentTable.setRent(BigDecimal.valueOf(Float.parseFloat(vec.get(1).toString())));
                    }
                    if (vec.get(2) != null) {
                        lockerRentTable.setCabno(Float.parseFloat(vec.get(2).toString()));
                    }
                    if (vec.get(3) != null) {
                        lockerRentTable.setLockerType(vec.get(3).toString());
                    }
                    if (vec.get(4) != null) {
                        lockerRentTable.setLockerNum(Float.parseFloat(vec.get(4).toString()));
                    }
                    if (vec.get(5) != null) {
                        lockerRentTable.setRentDueDT(vec.get(5).toString());
                    }
                    if (vec.get(6) != null) {
                        lockerRentTable.setCustName(vec.get(6).toString());
                    }
                    lockerRentTable.setCustState(vec.get(7).toString());
                    lockerRentTable.setBranchState(vec.get(8).toString());
                    lockerRentList.add(lockerRentTable);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return lockerRentList;
    }

//    public String autoLockerRentPosting(String tempBd) throws ApplicationException {
//        //UserTransaction ut = context.getUserTransaction();
//        String message = "true";
//        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
//        try {
//            List autoLockerList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where reportname='LOCKER-AUTO'").getResultList();
//            if (!autoLockerList.isEmpty()) {
//                Vector autoLockerVec = (Vector) autoLockerList.get(0);
//                int code = Integer.parseInt(autoLockerVec.get(0).toString());
//                if (code == 1) {
//                    List brnList = em.createNativeQuery("select BrnCode from branchmaster").getResultList();
//                    if (brnList.isEmpty()) {
//                        return "Branch code is not defined";
//                    }
//                    //ut.begin();
//                    int staxModuleActive = 0;
//                    String taxFlag = "";
//                    List stTaxList = em.createNativeQuery("SELECT ifnull(code,0) FROM parameterinfo_report WHERE reportname='STAXMODULE_ACTIVE'").getResultList();
//                    if (!stTaxList.isEmpty()) {
//                        Vector stTaxListV = (Vector) stTaxList.get(0);
//                        staxModuleActive = Integer.parseInt(stTaxListV.get(0).toString());
//                        if (staxModuleActive == 1) {
//                            taxFlag = "Y";
//                        } else {
//                            taxFlag = "N";
//                        }
//                    } else {
//                        taxFlag = "N";
//                    }
//                    String tmpDetails = "VCH. Of Locker Rent";
//                    String glAcNo = "";
//                    String glAccNo = "";
//                    List glLst = em.createNativeQuery("select glheadmisc from parameterinfo_miscincome where purpose='Locker Rent'").getResultList();
//                    if (!glLst.isEmpty()) {
//                        Vector glListVec = (Vector) glLst.get(0);
//                        glAccNo = glListVec.get(0).toString();
//                    } else {
//                        return "GL Head is not defined";
//                    }
//                    
//                    for (int a = 0; a < brnList.size(); a++) {
//                        Vector brnListVector = (Vector) brnList.get(a);
//                        String brncode = brnListVector.get(0).toString();
//                        if (brncode.length() < 2) {
//                            brncode = "0" + brncode;
//                        }
//                        System.out.println("For Branch Code-->" + brncode + "\n");
//                        //List<LockerRentDetail> rentList = gridLoadForAllAccounts(brncode);
//
//                        List returnList = gridAutoLoadForAllAccounts(brncode);
//                        List<LockerRentDetail> rentList = new ArrayList<LockerRentDetail>();
//                        glAcNo = brncode + glAccNo + "01";
//                        
//                        for (int i = 0; i < returnList.size(); i++) {
//                            Vector ele = (Vector) returnList.get(i);
//                            LockerRentDetail detail = new LockerRentDetail();
//                            detail.setCabno(ele.get(0) == null ? 0 : Float.parseFloat(ele.get(0).toString()));
//                            detail.setLockertype(ele.get(1).toString());
//                            detail.setLockerno(ele.get(2) == null ? 0 : Float.parseFloat(ele.get(2).toString()));
//                            
//                            detail.setAcno(ele.get(3).toString());
//                            detail.setCustname(ele.get(4).toString());
//                            detail.setPenalty(ele.get(5) == null ? 0 : Double.parseDouble(ele.get(5).toString()));
//                            
//                            detail.setRentduedt(ele.get(6).toString());
//                            detail.setStatus(ele.get(7).toString());
//                            detail.setBrCode(ele.get(8).toString());
//                            rentList.add(detail);
//                        }
//                        
//                        List<List> returnDataList = ftsDrCr.postLockerRent(rentList, glAcNo, "SYSTEM", tmpDetails, 35, taxFlag, "N", brncode);
////                        if (!message.substring(0, 4).equalsIgnoreCase("TRUE")) {
////                            ut.rollback();
////                            return message;
////                        }
//                        List<String> strList = (List<String>) returnDataList.get(0);
//                        if (!strList.get(0).substring(0, 4).equalsIgnoreCase("true")) {
//                            //ut.rollback();
//                            smsBatchList = new ArrayList<SmsToBatchTo>();
//                            return strList.get(0);
//                        }
//                        List<SmsToBatchTo> tempSmsList = (List<SmsToBatchTo>) returnDataList.get(1);
//                        for (SmsToBatchTo to : tempSmsList) {
//                            smsBatchList.add(to);
//                        }
//                    }
//                    message = "true";
//                    //ut.commit();
//                }
//            }
//            //Sending SMS
//            try {
//                smsFacade.sendSmsToBatch(smsBatchList);
//            } catch (Exception ex) {
//                System.out.println("Problem In Sending SMS In Auto Locker Rent-->" + ex.getMessage());
//            }
//            return message;
//        } catch (Exception e) {
//            try {
//                //ut.rollback();
//                throw new ApplicationException(e.getMessage());
//            } catch (Exception ex) {
//                throw new ApplicationException(ex.getMessage());
//            }
//        }
//    }
    public List lockerTypeOnLoad(String brCode) throws ApplicationException {
        List lockerType = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select distinct lockerType From lockermaster Where brnCode='" + brCode + "' order by lockerType");
            lockerType = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return lockerType;
    }

    public List<String> getlockerNoDetail(String brCode, String cabNo, String lockerNo, String lockerTy) throws ApplicationException {
        List<String> lockerNoDtl = new ArrayList<String>();
        try {
            Query selectQuery = em.createNativeQuery("select ac.custName,DATE_FORMAT(lm.trantime,'%d/%m/%Y'),lm.keyno,lm.mode,"
                    + " lr.Acno,DATE_FORMAT(lr.txndate,'%d/%m/%Y'),lr.rent,replace(ifnull(lm.adoperator1,''),' ',''),replace(ifnull(lm.adoperator2,''),' ',''),"
                    + " replace(ifnull(lm.adoperator3,''),' ',''),replace(ifnull(lm.adoperator4,''),' ','') from lockerrent lr,lockeracmaster lm,"
                    + " accountmaster ac where lr.acno=ac.acno  and lr.CABNO= " + cabNo + " and lm.brncode = lr.brncode and lm.brnCode='" + brCode + "' "
                    + " And lr.lockertype= '" + lockerTy + "' and lr.lockerno= " + lockerNo + " and lr.status='U' and lr.acno = lm.acno and lr.CABNO= lm.CABNO "
                    + " and lr.lockertype= lm.lockertype and lr.lockerno= lm.lockerno");
            List a = selectQuery.getResultList();
            Vector ele = (Vector) a.get(0);
            String cName = ele.get(0).toString();
            String opDT = ele.get(1).toString();
            String kNo = ele.get(2).toString();
            String mode = ele.get(3).toString();
            String acNo = ele.get(4).toString();
            String lDt = ele.get(5).toString();
            String rent = ele.get(6).toString();
            String adOp1 = ele.get(7).toString();
            String adOp2 = ele.get(8).toString();
            String adOp3 = ele.get(9).toString();
            String adOp4 = ele.get(10).toString();
            String txtOp = "";
            if (!adOp1.equalsIgnoreCase("")) {
                txtOp = adOp1;
            }
            if (!adOp2.equalsIgnoreCase("")) {
                txtOp = txtOp + " / " + adOp2;
            }
            if (!adOp3.equalsIgnoreCase("")) {
                txtOp = txtOp + " / " + adOp3;
            }
            if (!adOp4.equalsIgnoreCase("")) {
                txtOp = txtOp + " / " + adOp4;
            }

            List list = em.createNativeQuery("select ifnull(date_format(max(rentduedt),'%d/%m/%Y'),'') RentPaidUpto from lockerrent where acno ='" + acNo + "'"
                    + "And lockertype= '" + lockerTy + "' and cabno = " + cabNo + " and lockerno= " + lockerNo + " and status='P'").getResultList();
            if (!list.isEmpty()) {
                Vector vtr = (Vector) list.get(0);
                lDt = vtr.get(0).toString();
            }
            lockerNoDtl.add(cName);
            lockerNoDtl.add(opDT);
            lockerNoDtl.add(kNo);
            lockerNoDtl.add(mode);
            lockerNoDtl.add(acNo);
            lockerNoDtl.add(lDt);
            lockerNoDtl.add(rent);

            String acNat = ftsPost.getAccountNature(acNo);
            if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                Query selectRecon = em.createNativeQuery("select max(dt),sum(cramt-dramt) from ca_recon where acno = '" + acNo + "'");
                List rec = selectRecon.getResultList();
                Vector ele1 = (Vector) rec.get(0);
                String mDT = ele1.get(0).toString();
                String bal = ele1.get(1).toString();

                lockerNoDtl.add(mDT);
                lockerNoDtl.add(bal);
            } else if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                Query selectRecon = em.createNativeQuery("select DATE_FORMAT(max(dt),'%d/%m/%Y'),sum(cramt-dramt) from recon where acno = '" + acNo + "'");
                List rec = selectRecon.getResultList();
                Vector ele1 = (Vector) rec.get(0);
                String mDT = ele1.get(0).toString();
                String bal = ele1.get(1).toString();

                lockerNoDtl.add(mDT);
                lockerNoDtl.add(bal);
            }

            Query selectOpOn = em.createNativeQuery("select ifnull(DATE_FORMAT(max(opdate),'%d/%m/%Y'),'') from locker_operation where "
                    + " acno ='" + acNo + "' and lockerno = " + lockerNo + " and cabno = '" + cabNo + "'"
                    + "and lockertype = '" + lockerTy + "'");
            List lstOp = selectOpOn.getResultList();
            Vector ele2 = (Vector) lstOp.get(0);
            String lOpDt = ele2.get(0).toString();
            lockerNoDtl.add(lOpDt);

            Query selectDue = em.createNativeQuery("select ifnull(sum(rent),0) from lockerrent where acno ='" + acNo + "' "
                    + "and status ='U' and rentduedt<= '" + ymd.format(new Date()) + "'");
            List recDue = selectDue.getResultList();
            Vector ele3 = (Vector) recDue.get(0);
            String rDue = ele3.get(0).toString();
            lockerNoDtl.add(rDue);
            lockerNoDtl.add(txtOp);

            Query selectDueDt = em.createNativeQuery("select ifnull(DATE_FORMAT(max(rentduedt),'%d/%m/%Y'),'') from lockerrent where acno ='" + acNo + "' "
                    + "and status ='U' and rentduedt<= '" + ymd.format(new Date()) + "'");
            List recDueDt = selectDueDt.getResultList();
            Vector ele4 = (Vector) recDueDt.get(0);
            String rDueDt = ele4.get(0).toString();
            lockerNoDtl.add(rDueDt);

            //New Add on
            Query selectTime = em.createNativeQuery("select hour(date_format(cast(inTime as time),'%r')) as Inhours,MINUTE(date_format(cast(inTime as time),'%r')) as Inminutes,date_format(cast(inTime as time),'%r') as inTimes, "
                    + "hour(date_format(cast(outTime as time),'%r')) as Outhour,minute(date_format(cast(outTime as time),'%r')) as OutMinute,date_format(cast(outTime as time),'%r') as outTimes "
                    + "from locker_operation where acno ='" + acNo + "' and lockerno = " + lockerNo + " and cabno = " + cabNo + " "
                    + "and lockertype = '" + lockerTy + "' and date_format(opdate,'%Y%m%d') = '" + ymd.format(new Date()) + "'");
            List lstTime = selectTime.getResultList();
            if (!lstTime.isEmpty()) {
                Vector timeVector = (Vector) lstTime.get(0);
                
                String inHour = timeVector.get(0).toString();
                String inMinute = timeVector.get(1).toString();
                String inAmPm = timeVector.get(2).toString().substring(9);
                String outHour = timeVector.get(3).toString();
                String outMinute = timeVector.get(4).toString();
                String outAmPm = timeVector.get(5).toString().substring(9);

                lockerNoDtl.add(inHour);
                lockerNoDtl.add(inMinute);
                lockerNoDtl.add(inAmPm);
                
                lockerNoDtl.add(outHour);
                lockerNoDtl.add(outMinute);
                lockerNoDtl.add(outAmPm);
                
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return lockerNoDtl;
    }

    public List getLockerDetail(String acno) throws ApplicationException {
        List resultList;
        try {
            resultList = em.createNativeQuery("select c.customerid,concat(ifnull(c.custname,''),' ',ifnull(c.middle_name,''),' ',"
                    + "ifnull(c.last_name,'')) as custname,c.gender,c.PAN_GIRNumber,c.fathername from cbs_customer_master_detail c, ("
                    + "select c.custid as cid,ifnull(a.custid1,'') as cid1,ifnull(a.custid2,'') as cid2,ifnull(a.custid3,'') as cid3,"
                    + "ifnull(a.custid4,'') as cid4 from accountmaster a, customerid c where a.acno = '" + acno + "' and a.acno = c.acno) "
                    + "a where c.customerid = a.cid or c.customerid = a.cid1 or c.customerid = a.cid2 or c.customerid = a.cid3 "
                    + "or c.customerid = a.cid4").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultList;
    }

    public List getLockerTimeDetail(String acno) throws ApplicationException {
        List resultList1;
        try {
            resultList1 = em.createNativeQuery("select  date_format(cast(inTime as time),'%r') as inTimes, date_format(cast(outTime as time),'%r') as outTimes ,date_format(opdate, '%d/%m/%Y %r') as opDate from locker_operation where acno = '" + acno + "' order by cast(opdate as datetime) desc ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultList1;
    }

    public String SaveData(String function, String acno, int lockerno, double cabno, String lockertype, double keyno, String custname, String inpresenceof, String intime, String outtime, String brncode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            if (function.equalsIgnoreCase("A")) {

                List result = em.createNativeQuery("select * from locker_operation where acno = '" + acno + "' and lockerno = " + lockerno + " "
                        + "and cabno = " + cabno + " and keyno = " + keyno + " "
                        + "and intime = '" + intime + "' and outtime = '" + outtime + "' and brncode = '" + brncode + "'").getResultList();
                if (!result.isEmpty()) {
                    ut.rollback();
                    throw new ApplicationException("Locker Operation time already exists in this account !");
                }
                List result1 = em.createNativeQuery("select * from locker_operation where acno = '" + acno + "' and lockerno = " + lockerno + " "
                        + "and cabno = " + cabno + "  and keyno = " + keyno + " and brncode = '" + brncode + "'and '" + intime + "' between intime and outtime").getResultList();
                if (!result1.isEmpty()) {
                    ut.rollback();
                    throw new ApplicationException("Locker Operation time already exists in this account !");
                }
                List sNoList = em.createNativeQuery("SELECT ifnull(MAX(srno),0)+1 FROM locker_operation where acno = '" + acno + "'").getResultList();
                Vector recLst1 = (Vector) sNoList.get(0);
                String sNo = recLst1.get(0).toString();

                Integer insertOperList = em.createNativeQuery("insert into locker_operation(acno,lockerno,srno,cabno,lockertype,"
                        + "keyno,custname,inpresenceof,intime,outtime,opdate,brncode) "
                        + "values('" + acno + "'," + lockerno + "," + sNo + "," + cabno + ",'" + lockertype + "'," + keyno + ","
                        + "'" + custname + "','" + inpresenceof + "','" + intime + "','" + outtime + "',CURRENT_TIMESTAMP,"
                        + "'" + brncode + "')").executeUpdate();
                if (insertOperList <= 0) {
                    ut.rollback();
                    return "Problem In Insertion";
                } else {
                    ut.commit();
                    return "Data Saved Successfully";
                }

            } else {
                Integer updationOpr = em.createNativeQuery("update locker_operation set outtime = '" + outtime + "' where acno = '" + acno + "' and lockerno = " + lockerno + " and cabno = " + cabno + " and keyno = " + keyno + " and intime = '" + intime + "' and brncode = '" + brncode + "'").executeUpdate();
                if (updationOpr <= 0) {
                    ut.rollback();
                    return "Problem In updation";
                } else {
                    ut.commit();
                    return "Data update Successfully";
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getUnAuthSurrenderLocker(String brCode) throws ApplicationException {
        List resultList;
        try {
            resultList = em.createNativeQuery("select cast(cabno as unsigned),lockertype,lockerno,acno,enterBy from locker_surrender_auth where "
                    + "brncode = '" + brCode + "' and auth ='N'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultList;
    }

    public String deleteLocker(String brCode, String cabNo, String lockerNo, String lockerTy, String enterBy, String acNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query deleteQuery = em.createNativeQuery("update locker_surrender_auth set auth ='D', authBy = '" + enterBy + "' Where cabNo = " + cabNo + " "
                    + " and LockerType='" + lockerTy + "' And LockerNo=" + Float.parseFloat(lockerNo) + " and acno = '" + acNo + "' "
                    + " and brncode='" + brCode + "' ");
            int var = deleteQuery.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem in data deletion");
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
    }

    public String unAuthLockerExist(String brCode, String cabNo, String lockerNo, String lockerTy, String acNo) throws ApplicationException {
        String msg = "false";
        try {
            List chkList = em.createNativeQuery("select lockerno from locker_surrender_auth where cabno=" + cabNo + " "
                    + "and lockertype= '" + lockerTy + "' and lockerno='" + lockerNo + "' and acno = '" + acNo + "' and auth = 'N' and brncode='" + brCode + "'").getResultList();
            if (chkList.isEmpty()) {
                msg = "true";
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }
}
