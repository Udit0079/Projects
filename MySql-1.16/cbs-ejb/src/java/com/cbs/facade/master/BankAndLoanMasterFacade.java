package com.cbs.facade.master;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.ReferenceCodeMasterTable;
import com.cbs.dto.YearEndDatePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.pojo.ChbookDetailPojo;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

@Stateless(mappedName = "BankAndLoanMasterFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class BankAndLoanMasterFacade implements BankAndLoanMasterFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote fTSPosting43CBSBean;
    @EJB
    private RbiReportFacadeRemote rbiReportFacade;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public List finYearDropDownLoanInterestParameter() throws ApplicationException {
        List brcode = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select min(F_Year) from yearend Where YearEndFlag='N'");
            brcode = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return brcode;
    }

    @Override
    public List getBankDirectoryTable(String micr, String code) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select code,codeno,bankName,Branch,phone,fax,offday from clg_bankdirectory where micr = '" + micr + "' and  code = '" + code + "' ORDER BY CODENO").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    @Override
    public String updateBankDirectory(String code, String codeno, String bankname, String branch, String phone, String fax, String weekDays) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer upadteBankDirectoryList = em.createNativeQuery("update clg_bankdirectory set code = '" + code + "',"
                    + "codeno = '" + codeno + "', bankname = '" + bankname + "',branch ='" + branch + "',phone = '" + phone + "',fax ='" + fax + "'"
                    + ",OFFDAY = '" + weekDays + "' where code = '" + code + "' and codeno ='" + codeno + "'").executeUpdate();
            if (upadteBankDirectoryList <= 0) {
                ut.rollback();
                return "Data is does not Updated(Bank Code OR Branch No can Not be Changed)";
            } else {
                ut.commit();
                return "Data Updated Successfully";
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
    public String saveBankDirectory(String micr, String bankCode, String branchNo, String branchName, String branch, String phoneNo, String fax, String weekDays) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            List resultlist = em.createNativeQuery("select * from clg_bankdirectory where micr = '" + micr + "' and "
                    + "code = '" + bankCode + "' and codeno = '" + branchNo + "'").getResultList();
            if (resultlist.size() > 0) {
                ut.rollback();
                return "This Code No. Is Already Exist";
            } else {
                Integer upadteBankDirectoryList = em.createNativeQuery("INSERT INTO clg_bankdirectory VALUES('" + micr + "','" + bankCode + "',"
                        + "'" + branchNo + "','" + branchName + "','" + branch + "'," + phoneNo + "," + fax + ",'" + weekDays + "')").executeUpdate();
                if (upadteBankDirectoryList <= 0) {
                    ut.rollback();
                    return "Data is does not Saved in clg_bankdirectory";
                } else {
                    ut.commit();
                    return "Data Saved Successfully";
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

    @Override
    public List gridDetailBankHolidayMarkingRegister() throws ApplicationException {
        List resultList = new ArrayList();
        try {
            List year = em.createNativeQuery("select ifnull(min(F_Year),'') from cbs_yearend where yearendflag='N'").getResultList();
            Vector yearLst = (Vector) year.get(0);
            String years = yearLst.get(0).toString();
            String date = years + "01" + "01";
            resultList = em.createNativeQuery("SELECT ifnull(Date_format(HOLIDAYDATE,'%d/%m/%Y'),''),ifnull(HOLIDAYDESC,'') FROM cbs_lstholidays Where HolidayDate >='" + date + "' order by HolidayDate").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultList;
    }

    @Override
    public String saveRecordBankHolidayMarkingRegister(String holidayDate, String holidayDesc) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String message = "";
        try {
            ut.begin();
            int var = 0, var1 = 0, var2 = 0, var3 = 0;
            List dateChk = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY,'" + holidayDate + "',CURDATE())").getResultList();
            int dayDiff = Integer.parseInt(((Vector) dateChk.get(0)).get(0).toString());
            if (dayDiff > 0) {
                ut.rollback();
                message = "Holiday date cannot be less than current date !!!";
                return message;
            }
            List dayBeginChk = em.createNativeQuery("SELECT DayBeginFlag,DayEndFlag FROM cbs_bankdays WHERE DATE='" + holidayDate + "'").getResultList();
            if (!dayBeginChk.isEmpty()) {
                String beginFlagChk = ((Vector) dayBeginChk.get(0)).get(0).toString();
                String endFlagChk = ((Vector) dayBeginChk.get(0)).get(1).toString();
                if (beginFlagChk.equalsIgnoreCase("Y")) {
                    ut.rollback();
                    message = "Day begin has been done for this date so this date cannot be marked as holiday !!!";
                    return message;
                }
                if (endFlagChk.equalsIgnoreCase("Y")) {
                    ut.rollback();
                    message = "Day end has been done for this date so this date cannot be marked as holiday !!!";
                    return message;
                }
            }
            List chkholiday = em.createNativeQuery("SELECT HOLIDAYDATE FROM cbs_lstholidays WHERE HOLIDAYDATE='" + holidayDate + "'").getResultList();
//            if (!chkholiday.isEmpty()) {
//                ut.rollback();
//                message = "This date has already been marked as holiday !!!";
//                return message;
//            }
            List chkdate = em.createNativeQuery("SELECT HOLIDAYDATE FROM cbs_lstholidays WHERE HOLIDAYDATE='" + holidayDate + "' and holidaydesc='" + holidayDesc + "'").getResultList();
            if (!chkdate.isEmpty()) {
                int n = em.createNativeQuery("delete from cbs_lstholidays WHERE HOLIDAYDATE='" + holidayDate + "' and holidaydesc='" + holidayDesc + "'").executeUpdate();
            } 
                Query insertQuery = em.createNativeQuery("INSERT INTO cbs_lstholidays VALUES('" + holidayDate + "','" + holidayDesc + "')");
                var = insertQuery.executeUpdate();

                Query updateQuery = em.createNativeQuery("UPDATE cbs_bankdays SET DayEndFlag ='Y',Daybeginflag='H' WHERE DATE ='" + holidayDate + "'");
                var1 = updateQuery.executeUpdate();
                /**
                 * This code is for automatically mark holiday for branches when
                 * bank level holiday is marked*
                 */
                List allBrnHolMark = em.createNativeQuery("SELECT cast(BRNCODE as char) AS BRNCODE FROM branchmaster WHERE alphacode IN (select ALPHACODE from bnkadd)").getResultList(); //WHERE BRNCODE IN (6,3,2) removed by Ankit according to Ashish sir
                if (!allBrnHolMark.isEmpty()) {
                    int flagForInsert = 0, flagForUpdate = 0;
                    for (int i = 0; i < allBrnHolMark.size(); i++) {
                        Vector ele = (Vector) allBrnHolMark.get(i);
                        String tempBrnCode = ele.get(0).toString();
                        if (Integer.parseInt(tempBrnCode) < 10) {
                            tempBrnCode = "0" + tempBrnCode;
                        }
                        List selectList = em.createNativeQuery("select * from lstholidays  where HOLIDAYDATE='" + holidayDate + "' and brncode='" + tempBrnCode + "'").getResultList();
                        if (selectList.isEmpty()) {
                            Query insertQuery2 = em.createNativeQuery("insert into lstholidays(HOLIDAYDESC,HOLIDAYDATE,brncode)"
                                    + "values (" + "'" + holidayDesc + "'" + "," + "'" + holidayDate + "'" + "," + "'" + tempBrnCode + "'" + ")");
                            var2 = insertQuery2.executeUpdate();
                        } else {
                            var2 = em.createNativeQuery("update lstholidays set HOLIDAYDESC='" + holidayDesc + "' where HOLIDAYDATE='" + holidayDate + "' and brncode='" + tempBrnCode + "'").executeUpdate();
                        }

                        Query updateQueryBnkDays = em.createNativeQuery("Update bankdays Set DayEndFlag ='Y',Daybeginflag='H',Dayendflag1='N',Cashclose='Y' Where Brncode = '" + tempBrnCode + "' AND Date='" + holidayDate + "'");
                        var3 = updateQueryBnkDays.executeUpdate();
                        if (var2 > 0) {
                            ++flagForInsert;
                        }
                        if (var3 > 0) {
                            ++flagForUpdate;
                        }
                    }
                    if (flagForInsert <= 0 || flagForUpdate <= 0) {
                        message = "Problem occured in holiday marking !!!";
                        ut.rollback();
                        return message;
                    }
                }
                /**
                 * End*
                 */
          
            List deteConvertion = em.createNativeQuery("SELECT date_format('" + holidayDate + "','%d/%m/%Y')").getResultList();
            String showingDt = ((Vector) deteConvertion.get(0)).get(0).toString();
            if (var <= 0 || var1 <= 0) {
                ut.rollback();
                message = "false";
                return message;
            } else {
                ut.commit();
                message = "Date " + showingDt + " succesfully marked as holiday for " + holidayDesc;
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

    @Override
    public String deleteDataBankHolidayMarkingRegister(String holidayDate, String holidayDesc, String flag) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0, var1 = 0, var2 = 0, var3 = 0, var4 = 0;
            String message = "";
            if (flag.equalsIgnoreCase("d")) {
                List dateChk = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY,'" + holidayDate + "',CURDATE())").getResultList();
                int dayDiff = Integer.parseInt(((Vector) dateChk.get(0)).get(0).toString());
                if (dayDiff > 0) {
                    ut.rollback();
                    message = "Previous date holiday record cannot be deleted !!!";
                    return message;
                }
                List chkdate = em.createNativeQuery("SELECT HOLIDAYDATE FROM cbs_lstholidays WHERE HOLIDAYDATE='" + holidayDate + "' and holidaydesc='" + holidayDesc + "'").getResultList();
                if (!chkdate.isEmpty()){ 
                Query deleteQuery = em.createNativeQuery("DELETE FROM cbs_lstholidays WHERE HOLIDAYDESC='" + holidayDesc + "' and HOLIDAYDATE='" + holidayDate + "'");
                var = deleteQuery.executeUpdate();

                var2 = em.createNativeQuery("UPDATE cbs_bankdays SET DayEndFlag ='N',Daybeginflag='N' WHERE DATE ='" + holidayDate + "'").executeUpdate();
                }
                /**
                 * This code is for case of deletion of entry from bank level
                 * then update the record of branches*
                 */
                List allBrnHolMark = em.createNativeQuery("SELECT CAST(BRNCODE as CHAR(2)) AS BRNCODE FROM branchmaster WHERE alphacode IN (select ALPHACODE from bnkadd)").getResultList();
                if (!allBrnHolMark.isEmpty()) {
                    for (int i = 0; i < allBrnHolMark.size(); i++) {
                        Vector ele = (Vector) allBrnHolMark.get(i);
                        String tempBrnCode = ele.get(0).toString();
                        if (Integer.parseInt(tempBrnCode) < 10) {
                            tempBrnCode = "0" + tempBrnCode;
                        }
                        List isHolidayExits = em.createNativeQuery("select * from lstholidays where HOLIDAYDATE='" + holidayDate + "' and brncode = '" + tempBrnCode + "'").getResultList();
                        if (isHolidayExits.isEmpty()) {
                            ut.rollback();
                            message = "This holiday not mark for all branch. please Unmark the holiday individually.";
                            return message;
                        }
                    }
                }
           if (!allBrnHolMark.isEmpty()) {
                    for (int i = 0; i < allBrnHolMark.size(); i++) {
                        Vector ele = (Vector) allBrnHolMark.get(i);
                        String tempBrnCode = ele.get(0).toString();
                        if (Integer.parseInt(tempBrnCode) < 10) {
                            tempBrnCode = "0" + tempBrnCode;
                        }
                        Query deleteQueryLstHol = em.createNativeQuery("DELETE FROM lstholidays WHERE HOLIDAYDESC='" + holidayDesc + "' and HOLIDAYDATE='" + holidayDate + "' and brncode='" + tempBrnCode + "'");
                        var3 = deleteQueryLstHol.executeUpdate();
                        Query updateQuery = em.createNativeQuery("Update bankdays Set DayEndFlag ='Y',Daybeginflag='N',Dayendflag1='N',Cashclose='Y' Where Brncode = '" + tempBrnCode + "' AND Date='" + holidayDate + "'");
                        var4 = updateQuery.executeUpdate();
                        if (var3 <= 0 || var4 <= 0) {
                            ut.rollback();
                            message = "Problem occured in deleting record of holiday marking for branch " + tempBrnCode + " !!!";
                            return message;
                        }
                    }
                }
                /**
                 * End*
                 */
            }
            if (flag.equalsIgnoreCase("u")) {
                List dateChk = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY,'" + holidayDate + "',CURDATE())").getResultList();
                int dayDiff = Integer.parseInt(((Vector) dateChk.get(0)).get(0).toString());
                if (dayDiff > 0) {
                    ut.rollback();
                    message = "Previous date holiday record cannot be updated !!!";
                    return message;
                }
                Query updateQuery = em.createNativeQuery("UPDATE cbs_lstholidays SET HOLIDAYDESC='" + holidayDesc + "',HOLIDAYDATE='" + holidayDate + "' WHERE HOLIDAYDATE='" + holidayDate + "'");
                var1 = updateQuery.executeUpdate();
                /**
                 * This code is for case of update of entry from bank level then
                 * update the record of branches*
                 */
                List allBrnHolMark = em.createNativeQuery("SELECT CONVERT(CHAR(2),BRNCODE) AS BRNCODE FROM branchmaster WHERE BRNCODE IN (6,3,2)").getResultList();
                if (!allBrnHolMark.isEmpty()) {
                    for (int i = 0; i < allBrnHolMark.size(); i++) {
                        Vector ele = (Vector) allBrnHolMark.get(i);
                        String tempBrnCode = ele.get(0).toString();
                        if (Integer.parseInt(tempBrnCode) < 10) {
                            tempBrnCode = "0" + tempBrnCode;
                        }
                        Query updateQueryForLstHol = em.createNativeQuery("UPDATE lstholidays SET HOLIDAYDESC='" + holidayDesc + "',HOLIDAYDATE='" + holidayDate + "' WHERE HOLIDAYDATE='" + holidayDate + "' and brncode='" + tempBrnCode + "'");
                        var4 = updateQueryForLstHol.executeUpdate();
                        if (var4 <= 0) {
                            ut.rollback();
                            message = "Problem occured in updating record of holiday marking for branch " + tempBrnCode + " !!!";
                            return message;
                        }
                    }
                }
                /**
                 * End*
                 */
            }
            if (flag.equalsIgnoreCase("d")) {
                if (var <= 0 || var2 <= 0 || var3 <= 0 || var4 <= 0) {
                    ut.rollback();
                    message = "falseDelete";
                    return message;
                } else {
                    ut.commit();
                    message = "Record deleted succesfully.";
                    return message;
                }
            }
            if (flag.equalsIgnoreCase("u")) {
                if (var1 <= 0 || var4 <= 0) {
                    ut.rollback();
                    message = "falseUpdate";
                    return message;
                } else {
                    ut.commit();
                    message = "Record updated succesfully.";
                    return message;
                }
            }
            return message;
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
    public List dataLoadBranchStatus(String asOnDt) throws ApplicationException {
        List gridList = new ArrayList();
        try {
            gridList = em.createNativeQuery("select a.dayendflag,ifnull(a.mendflag,'N') as mendflag, a.daybeginflag, ifnull(a.beginuser,'') as beginuser,"
                    + "b.branchname,ifnull(a.enduser,'') as enduser,a.SodTime as sodtime, a.EodTime as eodtime "
                    + "from bankdays a, branchmaster b where date = '" + asOnDt + "' and cast(a.brncode as SIGNED) = b.brncode "
                    + "order by b.branchname").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return gridList;
    }

    @Override
    public String updatePreviousEMI(String acno, String sno, String status, String payDt, String excessAmt,String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var1 = 0;
            if (payDt == null || payDt.equalsIgnoreCase("") || payDt.length() == 0) {
                List unpaidList = em.createNativeQuery("select * from emidetails where acno ='"+acno+"' and status ='PAID' and sno >'"+sno+"'").getResultList();
                if(!unpaidList.isEmpty()) {
                    ut.rollback();
                    return "You can not update Previous EMI Paid/Unpaid!!";
                }
                Query updateQuery = em.createNativeQuery("update emidetails  set PAYMENTDT = null ,  Status ='" + status + "',excessamt=" + excessAmt + ", ENTERBY ='"+userName+"',LASTUPDATE = now() where  AcNo= '" + acno + "' and sno<=" + sno + "");
                var1 = updateQuery.executeUpdate();
                Query insertQuery = em.createNativeQuery("insert into cbs_loan_emi_excess_details (acno, dt, excessamt, remark)"
                        + " values('" + acno + "',now()," + excessAmt + ",'One Time Feed of EMI Paid before " + payDt + "')");
                Integer insertQry = insertQuery.executeUpdate();
            } else {
                List unpaidList = em.createNativeQuery("select * from emidetails where acno ='"+acno+"' and status ='PAID' and sno >'"+sno+"'").getResultList();
                if(!unpaidList.isEmpty()) {
                    ut.rollback();
                    return "You can not update Previous EMI Paid/Unpaid!!";
                }
                Query updateQuery = em.createNativeQuery("update emidetails  set PAYMENTDT ='" + payDt + "',  Status ='" + status + "',excessamt=" + excessAmt + ",remarks='One Time Feed of EMI Paid before " + payDt + "', ENTERBY ='"+userName+"',LASTUPDATE = now() where  AcNo= '" + acno + "' and sno<=" + sno + " and status<> 'Paid'");
                var1 = updateQuery.executeUpdate();
                Query insertQuery = em.createNativeQuery("insert into cbs_loan_emi_excess_details (acno, dt, excessamt, remark)"
                        + " values('" + acno + "','" + payDt + "'," + excessAmt + ",'One Time Feed of EMI Paid before " + payDt + "')");
                Integer insertQry = insertQuery.executeUpdate();
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

    @Override
    public List gridDetailLoanEmiMaster(String acno) throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select SNO,date_format(DUEDT,'%d/%m/%Y'),INSTALLAMT,PRINAMT,INTERESTAMT,STATUS,coalesce(date_format(PAYMENTDT,'%d/%m/%Y'),''),EXCESSAMT from emidetails where acno ='" + acno + "' order by sno").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String saveDetailLoanEmiMaster(String acno, String sno, String status, String payDt, String excessAmt,String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var1 = 0;
            if (payDt == null || payDt.equalsIgnoreCase("") || payDt.length() == 0) {
                List unpaidList = em.createNativeQuery("select * from emidetails where acno ='"+acno+"' and status ='PAID' and sno >'"+sno+"'").getResultList();
                if(!unpaidList.isEmpty()) {
                    ut.rollback();
                    return "You can not update Previous EMI Paid/Unpaid!!";
                }
                Query updateQuery = em.createNativeQuery("update emidetails  set PAYMENTDT = null ,  Status ='" + status + "',excessamt=" + excessAmt + " , ENTERBY ='"+userName+"',LASTUPDATE = now() where  AcNo= '" + acno + "' and sno=" + sno + "");
                var1 = updateQuery.executeUpdate();
                Query insertQuery = em.createNativeQuery("insert into cbs_loan_emi_excess_details (acno, dt, excessamt, remark)"
                        + " values('" + acno + "',now()," + excessAmt + ",'One Time Feed of EMI Paid before " + payDt + "')");
                Integer insertQry = insertQuery.executeUpdate();
            } else {
                List unpaidList = em.createNativeQuery("select * from emidetails where acno ='"+acno+"' and status ='PAID' and sno >'"+sno+"'").getResultList();
                if(!unpaidList.isEmpty()) {
                    ut.rollback();
                    return "You can not update Previous EMI Paid/Unpaid!!";
                }
                List snoList = em.createNativeQuery("SELECT SNO FROM emidetails where  AcNo= '" + acno + "' and sno<" + sno + " AND STATUS='UNPAID'").getResultList();
                if (snoList.isEmpty()) {
                    Query updateQuery = em.createNativeQuery("update emidetails  set PAYMENTDT ='" + payDt + "',  Status ='" + status + "',excessamt=" + excessAmt + ", ENTERBY ='"+userName+"',LASTUPDATE = now() where  AcNo= '" + acno + "' and sno=" + sno + "");
                    var1 = updateQuery.executeUpdate();
                    Query insertQuery = em.createNativeQuery("insert into cbs_loan_emi_excess_details (acno, dt, excessamt, remark)"
                            + " values('" + acno + "','" + payDt + "'," + excessAmt + ",'One Time Feed of EMI Paid before " + payDt + "')");
                    Integer insertQry = insertQuery.executeUpdate();
                } else {
                    ut.rollback();
                    return "Please Select The Proper SNo To Mark Paid.";
                }
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

    @Override
    public String customerDetailLoanEmiMaster(String acno) throws ApplicationException {
        String message = "";
        try {
            String acNat = "";
//            List acNatLt = em.createNativeQuery("SELECT AcctNature from accounttypemaster where AcctCode = SubString('" + acno + "',3,2)").getResultList();
//            if (acNatLt.isEmpty()) {
//                message = "Account Nature Not Found For This Account No. Please Fill Valid Account No.!!!";
//                return message;
//            } else {
//                Vector acNatLtVec = (Vector) acNatLt.get(0);
//                acNat = acNatLtVec.get(0).toString();
//            }
            acNat = fTSPosting43CBSBean.getAccountNature(acno);
//            if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
//            } else {
//                message = "Please Enter Only Loan Account Nature Account Numbers !!!";
//                return message;
//            }
            List chk = em.createNativeQuery("select * from accountmaster where acno='" + acno + "'").getResultList();
            if (chk.isEmpty()) {
                message = "This Account No Does Not Exist!!!";
                return message;
            } else {
                List chk1 = em.createNativeQuery("select custName from accountmaster where acno='" + acno + "' and accstatus=9").getResultList();
                if (!chk1.isEmpty()) {
                    message = "This Account Has Been Closed !!!";
                    return message;
                }
            }
            List chk2 = em.createNativeQuery("SELECT CUSTNAME FROM accountmaster WHERE ACNO='" + acno + "'").getResultList();
            Vector chk2Vec = (Vector) chk2.get(0);
            message = "true" + chk2Vec.get(0).toString();
            return message;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String saveInspectionData(List table, String insmntCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int a = 0;
            Integer recNo = 0;
            List codeCheck = em.createNativeQuery("select RECNO from loan_inspection where ACNO='" + insmntCode + "' and RECNO=(select max(RECNO) from loan_inspection where ACNO='" + insmntCode + "')").getResultList();
            if (codeCheck.size() <= 0) {
                recNo = 1;
            } else {
                Vector elem = (Vector) codeCheck.get(0);
                recNo = Integer.parseInt(elem.get(0).toString());
                recNo += 1;
            }
            for (int i = 0, j = 1, k = 2, l = 3, m = 4, n = 5, p = 6; i < table.size(); i = i + 7, j = j + 7, k = k + 7, l = l + 7, m = m + 7, n = n + 7, p = p + 7) {

                Query insertBillLostHist = em.createNativeQuery("insert into loan_inspection(RECNO,ACNO,ROI,SLAB_FLOOR,SLAB_CEIL,FROM_DT,ENTERBY,"
                        + "TRANTIME) values(" + recNo + ",'" + table.get(i) + "'," + table.get(j) + "," + table.get(k) + "," + table.get(l) + ",'"
                        + ymdFormat.format(dmyFormat.parse(table.get(m).toString())) + "','" + table.get(n) + "','" + ymdFormat.format(dmyFormat.parse(table.get(p).toString())) + "')");
                Integer varinsertBillLostHist = insertBillLostHist.executeUpdate();
                if (varinsertBillLostHist <= 0) {
                    ut.rollback();
                    return "Data Not Saved";
                } else {
                    a++;
                }
            }
            if (a > 0) {
                ut.commit();
                return "Data Saved Successfully";
            }
            return "Data Saved Successfully";
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
    public List amountSlabTableLoanInspectionCharges(String instcode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select RECNO,ACNO,cast(ROI as SIGNED),cast(SLAB_FLOOR as SIGNED),cast(SLAB_CEIL as SIGNED),date_format(FROM_DT ,'%d/%m/%Y'),ENTERBY,date_format(TRANTIME ,'%d/%m/%Y') from loan_inspection where ACNO='" + instcode + "' and RECNO=(select max(RECNO) from loan_inspection where ACNO='" + instcode + "')");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    @Override
    public List getTLDLCAAcTypeDropDown() throws ApplicationException {
        List brcode = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select distinct acctCode From accounttypemaster where acctcode is not null and "
                    + "acctNature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "')");
            brcode = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return brcode;
    }

    @Override
    public List getTLDLCASBAcTypeDropDownLoanInterestParameter() throws ApplicationException {
        List acNatureList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select distinct acctCode From accounttypemaster where acctcode is not null and "
                    + "acctNature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "','"
                    + CbsConstant.SAVING_AC + "')");
            acNatureList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return acNatureList;
    }

    @Override
    public List getAcctType(String nature) throws ApplicationException {
        List acList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select acctCode,acctdesc From accounttypemaster where acctcode is not null and acctNature = '"
                    + nature + "'");
            acList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return acList;
    }

    @Override
    public List AcctNatureLoanInterestParameter(String accountNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            String acnature = "";
            acnature = fTSPosting43CBSBean.getAccountNature(accountNo);
            if (acnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                Query selectQuery = em.createNativeQuery("select openingdt from td_accountmaster where Acno = '" + accountNo + "'");

                tableResult = selectQuery.getResultList();
            } else {
                Query selectQuery = em.createNativeQuery("select openingdt from accountmaster where Acno = '" + accountNo + "'");
                tableResult = selectQuery.getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    @Override
    public String interestSaveLoanInterestParameter(String financialYear, String acctType, String interestType, String intOption, String userName, String todayDate, String brnCode, String frDt, String chgParameter, String acctNature) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int noOfMonths = 0;
            int valueOfMonths = 0;
            String mode = "", mode1 = "", brCodeQuery = "", brnCodeQuery = "";
            YearEndDatePojo yDate = new YearEndDatePojo();
            yDate = (YearEndDatePojo) rbiReportFacade.getYearEndDataAccordingFinYear(brnCode.equalsIgnoreCase("0A") ? "90" : brnCode, financialYear);
            String finStartDt = yDate.getMinDate();
            String finEndDt = yDate.getMaxDate();
            String toDt = finEndDt;
            String frStDt = frDt;
            String toStDt = toDt;

//            String fromDt = financialYear + "04" + "01";
//            String toDt = "";
            if (intOption.equalsIgnoreCase("M")) {
                noOfMonths = 12;
                valueOfMonths = 1;
            }
            if (intOption.equalsIgnoreCase("Q")) {
                noOfMonths = 4;
                valueOfMonths = 3;
            }
            if (intOption.equalsIgnoreCase("H")) {
                noOfMonths = 2;
                valueOfMonths = 6;
            }
            if (intOption.equalsIgnoreCase("Y")) {
                noOfMonths = 1;
                valueOfMonths = 12;
            }
            if (intOption.equalsIgnoreCase("M")) {
                mode = "Monthly";
            }
            if (intOption.equalsIgnoreCase("Q")) {
                mode = "Quarterly";
            }
            if (intOption.equalsIgnoreCase("H")) {
                mode = "Half Yearly";
            }
            if (intOption.equalsIgnoreCase("Y")) {
                mode = "Yearly";
            }
            if (interestType.equalsIgnoreCase("I")) {
                mode1 = "Interest";
            }
            if (interestType.equalsIgnoreCase("D")) {
                mode1 = "Deposit";
            }
            if (interestType.equalsIgnoreCase("P")) {
                mode1 = "Penal";
            }
            if (interestType.equalsIgnoreCase("NA")) {
                mode1 = "Not Applicable";
            }
            if (interestType.equalsIgnoreCase("MB")) {
                mode1 = "Msg Charge";
            }
            if (brnCode.equalsIgnoreCase("0A") || brnCode.equalsIgnoreCase("90")) {
                brCodeQuery = "";
                brnCodeQuery = "";
            } else {
                brCodeQuery = "  where brncode = '" + brnCode + "' ";
                brnCodeQuery = " and brncode = '" + brnCode + "' ";
            }
//            int finYear = Integer.parseInt(financialYear) + 1;
//            String finYearCheck = Integer.toString(finYear);
//            String toDateCheck = finYearCheck + "03" + "31";

            if (chgParameter.equalsIgnoreCase("Charge")) {
                if (ymdFormat.parse(frDt).before(ymdFormat.parse(finStartDt))) {
                    throw new ApplicationException("Start Date should not be less than Financial year Start Date");
                }
                if (ymdFormat.parse(frDt).after(ymdFormat.parse(finEndDt))) {
                    throw new ApplicationException("Start Date should not be greater than Financial year End Date");
                }
                if (!acctNature.equalsIgnoreCase("A") && acctType.equalsIgnoreCase("A")) {
                    acctType = acctNature;
                }
                List chkListCheck = em.createNativeQuery("SELECT DISTINCT(COUNT(*)) FROM cbs_loan_acctype_interest_parameter WHERE AC_TYPE = '" + acctType + "' " + brnCodeQuery
                        + " and Flag = '" + interestType + "' and FROM_DT BETWEEN '" + frDt + "' AND '" + toDt
                        + "' GROUP BY BRNCODE HAVING COUNT(*)>1").getResultList();
                if (!chkListCheck.isEmpty()) {
                    Vector Lst = (Vector) chkListCheck.get(0);
                    int count = Integer.parseInt(Lst.get(0).toString());
                    //if (count == noOfMonths) {
                    throw new ApplicationException("Data is already posted for the A/C type " + acctType
                            + " as Interest Option " + mode + " and Interest Type " + mode1);
                    // }
                }
            } else {
                String acNature = fTSPosting43CBSBean.getAcNatureByCode(acctType);
                if (acNature.equals(CbsConstant.SAVING_AC)) {
                    List chkListCheck = em.createNativeQuery("SELECT DISTINCT(COUNT(*)) FROM cbs_loan_acctype_interest_parameter WHERE AC_TYPE = '" + acctType + "' " + brnCodeQuery
                            + " and FROM_DT BETWEEN '" + frDt + "' AND '" + toDt + "' and flag in (1,2) GROUP BY BRNCODE HAVING COUNT(*)>1").getResultList();
                    if (!chkListCheck.isEmpty()) {
                        Vector Lst = (Vector) chkListCheck.get(0);
                        int count = Integer.parseInt(Lst.get(0).toString());
                        //  if (count == noOfMonths * 2) {
                        throw new ApplicationException("Data is already posted for the A/C type " + acctType
                                + " as Interest Option " + mode + " and Interest Type " + mode1);
                        // }
                    }
                } else {
                    if (ymdFormat.parse(frDt).before(ymdFormat.parse(finStartDt))) {
                        throw new ApplicationException("Start Date should not be less than Financial year Start Date");
                    }
                    if (ymdFormat.parse(frDt).after(ymdFormat.parse(finEndDt))) {
                        throw new ApplicationException("Start Date should not be greater than Financial year End Date");
                    }
                    List chkListCheck = em.createNativeQuery("SELECT DISTINCT(COUNT(*)) FROM cbs_loan_acctype_interest_parameter WHERE AC_TYPE = '" + acctType + "' " + brnCodeQuery
                            + " and Flag = '" + interestType + "' and FROM_DT BETWEEN '" + frDt + "' AND '" + toDt
                            + "' GROUP BY BRNCODE HAVING COUNT(*)>1").getResultList();
                    if (!chkListCheck.isEmpty()) {
                        Vector Lst = (Vector) chkListCheck.get(0);
                        int count = Integer.parseInt(Lst.get(0).toString());
                        //if (count == noOfMonths) {
                        throw new ApplicationException("Data is already posted for the A/C type " + acctType
                                + " as Interest Option " + mode + " and Interest Type " + mode1);
                        // }
                    }
                }
            }
            int recNo = 0;
            List chkList = em.createNativeQuery(" select brncode from branchmaster " + brCodeQuery).getResultList();
            if (!chkList.isEmpty()) {
                for (int j = 0; j < chkList.size(); j++) {
//                    fromDt = financialYear + "04" + "01";
                    frDt = frStDt;
                    toDt = toStDt;
                    for (int i = 0; i < noOfMonths; i++) {
                        Vector Lst = (Vector) chkList.get(j);

                        brnCode = CbsUtil.lPadding(2, Integer.parseInt(Lst.get(0).toString()));
                        toDt = CbsUtil.dateAdd(CbsUtil.monthAdd(frDt, valueOfMonths), -1);

                        List codeCheck = em.createNativeQuery("select ifnull(max(SNO),0)+1 from cbs_loan_acctype_interest_parameter").getResultList();
                        if (codeCheck.size() <= 0) {
                            recNo = 1;
                        } else {
                            Vector elem = (Vector) codeCheck.get(0);
                            recNo = Integer.parseInt(elem.get(0).toString());
                        }
                        if (chgParameter.equalsIgnoreCase("Charge")) {
                            if (ymdFormat.parse(toDt).after(ymdFormat.parse(finEndDt))) {
                                break;
                            }
                            if (!acctNature.equalsIgnoreCase("A") && acctType.equalsIgnoreCase("A")) {
                                acctType = acctNature;
                            }
                            Integer minChargeStatusList = em.createNativeQuery("insert into cbs_loan_acctype_interest_parameter(SNO,AC_TYPE,FROM_DT,TO_DT,POST_FLAG,DT,BRNCODE,ENTER_BY,FLAG)"
                                    + "VALUES(" + recNo + ",'" + acctType + "','" + frDt + "','" + toDt + "','N','" + todayDate + "','" + brnCode + "','" + userName + "','" + interestType + "')").executeUpdate();
                            if (minChargeStatusList <= 0) {
                                throw new ApplicationException("Data Not Saved");
                            }
                            frDt = CbsUtil.dateAdd(toDt, 1);
                            if (ymdFormat.parse(frDt).after(ymdFormat.parse(finEndDt))) {
                                break;
                            }
                        } else {
                            String acNature = fTSPosting43CBSBean.getAcNatureByCode(acctType);
                            if (acNature.equals(CbsConstant.SAVING_AC)) {
                                Integer minChargeStatusList = em.createNativeQuery("insert into cbs_loan_acctype_interest_parameter(SNO,AC_TYPE,FROM_DT,TO_DT,POST_FLAG,DT,BRNCODE,ENTER_BY,FLAG)"
                                        + "VALUES(" + recNo + ",'" + acctType + "','" + frDt + "','" + toDt + "','N','" + todayDate + "','" + brnCode + "','" + userName + "','" + 1 + "')").executeUpdate();
                                if (minChargeStatusList <= 0) {
                                    throw new ApplicationException("Data Not Saved");
                                }
                                recNo = recNo + 1;
                                minChargeStatusList = em.createNativeQuery("insert into cbs_loan_acctype_interest_parameter(SNO,AC_TYPE,FROM_DT,TO_DT,POST_FLAG,DT,BRNCODE,ENTER_BY,FLAG)"
                                        + "VALUES(" + recNo + ",'" + acctType + "','" + frDt + "','" + toDt + "','N','" + todayDate + "','" + brnCode + "','" + userName + "','" + 2 + "')").executeUpdate();
                                if (minChargeStatusList <= 0) {
                                    throw new ApplicationException("Data Not Saved");
                                }
                                frDt = CbsUtil.dateAdd(toDt, 1);
                            } else {
                                if (ymdFormat.parse(toDt).after(ymdFormat.parse(finEndDt))) {
                                    break;
                                }
                                Integer minChargeStatusList = em.createNativeQuery("insert into cbs_loan_acctype_interest_parameter(SNO,AC_TYPE,FROM_DT,TO_DT,POST_FLAG,DT,BRNCODE,ENTER_BY,FLAG)"
                                        + "VALUES(" + recNo + ",'" + acctType + "','" + frDt + "','" + toDt + "','N','" + todayDate + "','" + brnCode + "','" + userName + "','" + interestType + "')").executeUpdate();
                                if (minChargeStatusList <= 0) {
                                    throw new ApplicationException("Data Not Saved");
                                }
                                frDt = CbsUtil.dateAdd(toDt, 1);
                                if (ymdFormat.parse(frDt).after(ymdFormat.parse(finEndDt))) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            ut.commit();
            return "Data Saved Successfully";
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

    @Override
    public String daybeginDate() throws ApplicationException {
        try {
            String tempBd;

            List dateList = em.createNativeQuery("select date_format(date,'%Y%m%d') from cbs_bankdays where "
                    + "DATE=date_format(CURDATE(),'%Y-%m-%d')").getResultList();
            if (dateList.size() <= 0) {
                return "Check the Day Begin/Day End";
            } else {
                Vector dateVect = (Vector) dateList.get(0);
                tempBd = (String) dateVect.get(0);
            }
            return tempBd;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public String saveReferenceCodeMaster(List<ReferenceCodeMasterTable> referenceMasterList, String todayDate, String preRefRecNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        int modification = 0;
        try {
            ut.begin();
            String tempBd = daybeginDate();
            if (ymdFormat.parse(tempBd).after(ymdFormat.parse(todayDate)) || ymdFormat.parse(tempBd).before(ymdFormat.parse(todayDate))) {
                throw new ApplicationException("Check the today date you have passed");
            }
            for (ReferenceCodeMasterTable obj : referenceMasterList) {
                String refRecNo = obj.getRefRecNoTab();
                String refCode = obj.getRefCodeTab();
                String refDesc = obj.getRefDescTab();                
                String createdUserid = obj.getCreateByUseridTab();
                String lastUpdatedBy = obj.getLastUpdatedByTab();
                String saveUpdateFlag = obj.getCounterSaveUpdate();
                
                if (saveUpdateFlag.equalsIgnoreCase("SAVE")) {
                    List checkRefCodeList = em.createNativeQuery("select * from cbs_ref_rec_type where REF_REC_NO='" + refRecNo + "' and REF_CODE='" + refCode + "'").getResultList();
                    if (checkRefCodeList.size() > 0) {
                        throw new ApplicationException("This code is Already Exist");
                    }
                    Integer entry = em.createNativeQuery("insert into cbs_ref_rec_type(REF_REC_NO,REF_CODE,REF_DESC,CREATED_BY_USER_ID,CREATION_DATE,LAST_UPDATED_BY_USER_ID,LAST_UPDATED_DATE,TS_CNT)"
                            + "VALUES('" + refRecNo + "','" + refCode + "','" + refDesc + "','" + createdUserid + "',now(),'" + lastUpdatedBy + "',now()," + modification + ") ").executeUpdate();
                    if (entry <= 0) {
                        throw new ApplicationException("Data is not Saved");
                    }
                    result = "Data Saved Succesfully";
                }
                if (saveUpdateFlag.equalsIgnoreCase("Update")) {
                    List modificationList = em.createNativeQuery("select ifnull(max(TS_CNT),0) from cbs_ref_rec_type where REF_REC_NO='" + refRecNo + "' and REF_CODE='" + refCode + "'").getResultList();
                    if (modificationList.size() > 0) {
                        Vector ele = (Vector) modificationList.get(0);
                        modification = Integer.parseInt(ele.get(0).toString());
                        modification = modification + 1;
                    } else {
                        modification = 0;
                    }
                    Integer UpdateEntry = em.createNativeQuery("update cbs_ref_rec_type set REF_REC_NO='" + refRecNo + "',REF_CODE='" + refCode + "', REF_DESC='" + refDesc + "',CREATED_BY_USER_ID='" + createdUserid + "',CREATION_DATE=now(),LAST_UPDATED_BY_USER_ID='" + lastUpdatedBy + "',LAST_UPDATED_DATE=now(),TS_CNT=" + modification + " where REF_REC_NO='" + refRecNo + "' and REF_CODE='" + refCode + "'").executeUpdate();
                    if (UpdateEntry <= 0) {
                        throw new ApplicationException("Data is not Updated ");
                    } 
                    result = "Data updated succesfully";
                }
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

    @Override
    public List showDataCodeMasterTable(String refRecNo) throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select REF_REC_NO,REF_CODE,REF_DESC,CREATED_BY_USER_ID,date_format(CREATION_DATE,'%Y%m%d'),LAST_UPDATED_BY_USER_ID,Date_format(LAST_UPDATED_DATE,'%Y%m%d'),TS_CNT from cbs_ref_rec_type where REF_REC_NO='" + refRecNo + "' ").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String verifyPDCDetail(String refNo, List<ChbookDetailPojo> pdcList, String acnoCr, String custName, String acnoDr, String bankName,
            String branchName, String ifscCode, String amount, String enterBy, String authBy, String brnCode,
            String areaCode, String bnkCode, String branchCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "false";
        int a = 0;
        try {
            ut.begin();

            Integer updateQuery = em.createNativeQuery("update pdc_details_temp set auth ='Y',authBy ='" + authBy + "' where refNo = '" + refNo + "'").executeUpdate();
            if (updateQuery <= 0) {
                ut.rollback();
                result = "Data is not Verified";
            } else {
                for (int i = 0; i < pdcList.size(); i++) {
                    Integer entry = em.createNativeQuery("insert into pdc_details(acno_credit,cust_name,acno_debit,bank_name,"
                            + "branch_name,ifsc_code,chequeNo,chequeDt,amount,chqStatus,enterBy,enterDate,auth,authBy,org_brnid,AreaCode,BnkCode,BranchCode) "
                            + "VALUES('" + acnoCr + "','" + custName + "','" + acnoDr + "','" + bankName + "',"
                            + "'" + branchName + "','" + ifscCode + "','" + pdcList.get(i).chqNo + "','" + ymdFormat.format(dmyFormat.parse(pdcList.get(i).chqDate)) + "',"
                            + "'" + amount + "','F','" + enterBy + "',now(),'Y','" + authBy + "','" + brnCode + "','" + areaCode + "','" + bnkCode + "','" + branchCode + "')").executeUpdate();
                    if (entry <= 0) {
                        ut.rollback();
                        result = "Data is not Saved";
                        return result;
                    } else {
                        a++;
                    }
                }
                if (a > 0) {
                    ut.commit();
                    result = "true";
                }
            }
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String savePDCDetail(String acnoCr, String custName, String acnoDr, String bankName,
            String branchName, String ifscCode, String amount, String freq, String date, String chNoFrm, String chNoTo, String enterBy,
            String brnCode, String areaCode, String bnkCode, String branchCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "false";
        int a = 0;
        try {
            ut.begin();
            int refNo;
            List refNoCheck = em.createNativeQuery("select ifnull(max(refNo),0)+1 from pdc_details_temp").getResultList();
            if (refNoCheck.size() <= 0) {
                refNo = 1;
            } else {
                Vector elem = (Vector) refNoCheck.get(0);
                refNo = Integer.parseInt(elem.get(0).toString());
            }

            Integer entry = em.createNativeQuery("insert into pdc_details_temp(refNo,acno_credit,cust_name,acno_debit,bank_name,"
                    + "branch_name,ifsc_code,chequeNoFrm,chequeNoTo,Freq,chequeDt,amount,enterBy,enterDate,auth,org_brnid,AreaCode,BnkCode,BranchCode) "
                    + "VALUES('" + refNo + "','" + acnoCr + "','" + custName + "','" + acnoDr + "','" + bankName + "',"
                    + "'" + branchName + "','" + ifscCode + "','" + chNoFrm + "','" + chNoTo + "','" + freq + "','" + ymdFormat.format(dmyFormat.parse(date)) + "',"
                    + "'" + amount + "','" + enterBy + "',now(),'N','" + brnCode + "','" + areaCode + "','" + bnkCode + "','" + branchCode + "')").executeUpdate();
            if (entry <= 0) {
                ut.rollback();
                result = "Data is not Saved";
                return result;
            } else {
                ut.commit();
                result = "true" + refNo;
            }
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getPendingRefLst(String brncCode) throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select refNo from pdc_details_temp where org_brnid='" + brncCode + "' and auth ='N'").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getRefDetailList(String refNo) throws ApplicationException {
        try {
            List checkList;
            checkList = em.createNativeQuery("select acno_credit,cust_name,acno_debit,bank_name,branch_name,ifsc_code,"
                    + "chequeNoFrm,chequeNoTo,Freq,date_format(chequeDt,'%d/%m/%Y'),amount,enterBy,org_brnid,AreaCode,BnkCode,BranchCode from pdc_details_temp where refNo='" + refNo + "'").getResultList();
            return checkList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String deleteReference(String refNo, String delBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var1 = 0;
            Query updateQuery = em.createNativeQuery("update pdc_details_temp set auth ='D',authBy ='" + delBy + "' where refNo = '" + refNo + "'");
            var1 = updateQuery.executeUpdate();
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
    
    
    public String getUserName(String userId) throws ApplicationException {
        String userName="";
        List l1 = em.createNativeQuery("select UserName from securityinfo where UserId ='"+userId+"' ").getResultList();
         if(!l1.isEmpty()){
           Vector vec = (Vector)l1.get(0);  
           userName = vec.get(0).toString();
         }else if(l1.isEmpty()){
         List l2 = em.createNativeQuery("select distinct UserName from securityinfohistory where UserId ='"+userId+"' ").getResultList();
          if(!l2.isEmpty()){
           Vector vec1 = (Vector)l2.get(0);
           userName = vec1.get(0).toString();           
          }
         }else{
           userName ="";  
         } 
         
        return userName;
    }
}
