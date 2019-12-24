/*
 Document   : AdminManagementFacade
 Created on : 14 Apr, 2011, 10:31:56 PM
 Author     : Dhirendra Singh
 */
package com.cbs.facade.admin;

import com.cbs.constant.RoleTypes;
import com.cbs.dto.CbsVersionDetails;
import com.cbs.dto.FingerDataObj;
import com.cbs.dto.UrlItem;
import com.cbs.dto.UserDTO;
import com.cbs.dto.UserGrid;
import com.cbs.dto.other.UserInfo;
import com.cbs.entity.CbsUserBiometricTemplate;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author root
 */
@Stateless(mappedName = "AdminManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AdminManagementFacade implements AdminManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dd = new SimpleDateFormat("dd/MM/yyyy");

    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;

    public String getUserManualUrl() throws ApplicationException {
        try {
            Query selectQuery = em.createNativeQuery("SELECT url from cbs_role_url_master  where role_name ='1000' and brcode IN ('I') ");
            List urlList = selectQuery.getResultList();

            if (urlList.isEmpty()) {
                return "";
            }

            Vector vect = (Vector) urlList.get(0);
            return vect.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<UrlItem> getMenu(String UserId, String brnCode) throws ApplicationException {
        List tableResult = new ArrayList();
        String dayEndFlag = "";
        String DayBeginFlag = "";
        int levelId = 0;
        List<UrlItem> urlItemList = new ArrayList<UrlItem>();
        try {
            String alphaCode = "";
            List branchList = em.createNativeQuery("select alphacode from branchmaster where brncode = " + Integer.parseInt(brnCode)).getResultList();
            if (branchList.size() > 0) {
                Vector alphaCodeVect = (Vector) branchList.get(0);
                alphaCode = alphaCodeVect.elementAt(0).toString();
            }

            List levelList = em.createNativeQuery("SELECT LEVELID FROM securityinfo WHERE USERID = '" + UserId + "' AND BRNCODE = '" + brnCode + "'").getResultList();
            if (levelList.size() > 0) {
                Vector tdDateVect = (Vector) levelList.get(0);
                levelId = Integer.parseInt(tdDateVect.get(0).toString());
            }
            String bankDayEndFlag = "";
            String bankDayBeginFlag = "";
            List bankDayList = em.createNativeQuery("SELECT DayBeginFlag ,DayEndFlag FROM cbs_bankdays WHERE DATE = DATE_FORMAT(NOW(), '%Y%m%d')").getResultList();
            if (bankDayList.size() > 0) {
                Vector DateVect = (Vector) bankDayList.get(0);
                bankDayBeginFlag = DateVect.get(0).toString();
                bankDayEndFlag = DateVect.get(1).toString();
            }

            List dayList = em.createNativeQuery("SELECT DAYENDFLAG ,DAYBEGINFLAG FROM bankdays WHERE BRNCODE = '" + brnCode
                    + "' AND DATE = DATE_FORMAT(NOW(), '%Y%m%d')").getResultList();
            if (dayList.size() > 0) {
                Vector DateVect = (Vector) dayList.get(0);
                dayEndFlag = DateVect.get(0).toString();
                DayBeginFlag = DateVect.get(1).toString();
            }

            if (levelId == 7) {
                Query selectQuery = em.createNativeQuery("SELECT id_no, display_name,url,parentcode,subparentcode,brcode from cbs_role_url_master  WHERE "
                        + "brcode IN ('A','H') and ROLE_NAME LIKE '%"+ levelId +"%'");
                tableResult = selectQuery.getResultList();
            } else if (levelId == 5 || levelId == 8) {
                if (bankDayBeginFlag.equalsIgnoreCase("N") && bankDayEndFlag.equalsIgnoreCase("N")) {
                    Query selectQuery = em.createNativeQuery("SELECT id_no, display_name,url,parentcode,subparentcode,brcode from cbs_role_url_master  WHERE "
                            + "DISPLAY_NAME LIKE 'DAILY%' AND ROLE_NAME LIKE '%" + levelId + "%'");
                    tableResult = selectQuery.getResultList();
                } else if (bankDayBeginFlag.equalsIgnoreCase("Y") && bankDayEndFlag.equalsIgnoreCase("Y")) {
                    Query selectQuery = em.createNativeQuery("SELECT id_no, display_name,url,parentcode,subparentcode,brcode from cbs_role_url_master  WHERE "
                            + "display_name <>'DAILY PROCESS' AND ROLE_NAME LIKE '%" + levelId + "%'");
                    tableResult = selectQuery.getResultList();
                } else if (bankDayBeginFlag.equalsIgnoreCase("Y") && bankDayEndFlag.equalsIgnoreCase("N")) {
                    Query selectQuery = em.createNativeQuery("SELECT id_no, display_name,url,parentcode,subparentcode,brcode from cbs_role_url_master  WHERE "
                            + "ROLE_NAME LIKE '%" + levelId + "%'");
                    tableResult = selectQuery.getResultList();
                }
            } else if (levelId == 1 || levelId == 2) {
                if (dayEndFlag.equalsIgnoreCase("Y") && DayBeginFlag.equalsIgnoreCase("N")) {
                    if (alphaCode.equalsIgnoreCase("HO")) {
                        Query selectQuery = em.createNativeQuery("SELECT id_no, display_name,url,parentcode,subparentcode,brcode from cbs_role_url_master  WHERE "
                                + "brcode IN ('A','H') and DISPLAY_NAME LIKE 'DAILY%' AND ROLE_NAME LIKE '%" + levelId + "%'");
                        tableResult = selectQuery.getResultList();
                    } else {
                        Query selectQuery = em.createNativeQuery("SELECT id_no, display_name,url,parentcode,subparentcode,brcode from cbs_role_url_master  WHERE "
                                + "brcode IN ('A','B') and DISPLAY_NAME LIKE 'DAILY%' AND ROLE_NAME LIKE '%" + levelId + "%'");
                        tableResult = selectQuery.getResultList();
                    }
                } else if (dayEndFlag.equalsIgnoreCase("N") && DayBeginFlag.equalsIgnoreCase("Y")) {
                    if (alphaCode.equalsIgnoreCase("HO")) {
                        Query selectQuery = em.createNativeQuery("SELECT id_no, display_name,url,parentcode,subparentcode,brcode from cbs_role_url_master  where "
                                + "role_name LIKE '%" + levelId + "%' and brcode IN ('A','H') order by id_no");
                        tableResult = selectQuery.getResultList();
                    } else {
                        Query selectQuery = em.createNativeQuery("SELECT id_no, display_name,url,parentcode,subparentcode,brcode from cbs_role_url_master  where "
                                + "role_name LIKE '%" + levelId + "%' and brcode IN ('A','B') order by id_no");
                        tableResult = selectQuery.getResultList();
                    }
                }
            } else {
                if (dayEndFlag.equalsIgnoreCase("N") && DayBeginFlag.equalsIgnoreCase("Y")) {
                    if (alphaCode.equalsIgnoreCase("HO")) {
                        Query selectQuery = em.createNativeQuery("SELECT id_no, display_name,url,parentcode,subparentcode,brcode from cbs_role_url_master  where "
                                + "role_name LIKE '%" + levelId + "%' and brcode IN ('A','H') order by id_no");
                        tableResult = selectQuery.getResultList();
                    } else {
                        Query selectQuery = em.createNativeQuery("SELECT id_no, display_name,url,parentcode,subparentcode,brcode from cbs_role_url_master  where "
                                + "role_name LIKE '%" + levelId + "%' and brcode IN ('A','B') order by id_no");
                        tableResult = selectQuery.getResultList();
                    }
                }
            }
            UrlItem urlItem;
            for (int i = 0; i < tableResult.size(); i++) {
                Vector vect = (Vector) tableResult.get(i);
                urlItem = new UrlItem();
                urlItem.setIdNo(Integer.parseInt(vect.elementAt(0).toString()));
                urlItem.setDisplayName(vect.elementAt(1).toString());

                urlItem.setUrl(vect.elementAt(2).toString());
                urlItem.setParentCode(Integer.parseInt(vect.elementAt(3).toString()));
                urlItem.setSubParentCode(Integer.parseInt(vect.elementAt(4).toString()));
                urlItem.setBrCode(vect.elementAt(5).toString());

                urlItemList.add(urlItem);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return urlItemList;
    }

    public List bankAddress(String brnCode) throws ApplicationException {
        List bankAddList = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("SELECT b.bankname,d.branchname, d.city,d.ivr_br_code FROM bnkadd b,branchmaster d where b.alphacode = d.alphacode and d.brncode = '" + brnCode + "'");
            bankAddList = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return bankAddList;
    }

    public String getUserName(String userId) throws ApplicationException {
        try {
            Query selectQuery = em.createNativeQuery("SELECT username FROM securityinfo where userid= '" + userId + "'");
            List userList = selectQuery.getResultList();
            if (userList.isEmpty()) {
                throw new ApplicationException("User does not exist.");
            }
            Vector userVect = (Vector) userList.get(0);
            return userVect.elementAt(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    public String updateLoginTime(String userId) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            Query q1 = em.createNativeQuery("UPDATE securityinfo SET LOGIN ='N',LASTLOGINDATE=NOW(),FailedAttemptCount=0 WHERE userid='" + userId + "'");
            Integer int2 = q1.executeUpdate();
            if (int2 > 0) {
                ut.commit();
                return "TRUE";
            } else {
                ut.rollback();
                return "FALSE";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String logOut(String userId) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q1 = em.createNativeQuery("UPDATE securityinfo SET login='Y' WHERE userid='" + userId + "'");
            Integer int2 = q1.executeUpdate();
            if (int2 > 0) {
                ut.commit();
                return "TRUE";
            } else {
                ut.rollback();
                return "FALSE";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public boolean isUserExist(String brCode) throws ApplicationException {
        try {
            if (brCode == null) {
                return true;
            }
            List userList = em.createNativeQuery("select userid from securityinfo").getResultList();
            if (userList.size() > 0) {
                return true;
            } else {
                List branchList = em.createNativeQuery("select alphacode from branchmaster where brncode = " + Integer.parseInt(brCode)).getResultList();
                if (branchList.size() > 0) {
                    Vector alphaCodeVect = (Vector) branchList.get(0);
                    if (alphaCodeVect.get(0).toString().equalsIgnoreCase("HO")) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /* Methods for Change password*/
    public String btnChangeAction(String oldPassword, String newPassword, String reNewPassword, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Float tmpSno = 1f;
            List list = new ArrayList();
            if (oldPassword.equals("")) {
                throw new ApplicationException("Please enter the old password");
            }
            if (newPassword.equals("")) {
                throw new ApplicationException("New Password can not be blank");
            }
            if (newPassword.toUpperCase().equals(userName.toUpperCase())) {

                throw new ApplicationException("UserId and password should not be same");
            }
            if (newPassword.length() < 8) {
                throw new ApplicationException("Password length could not be less than 8");
            }
            if (newPassword.toUpperCase().equals(oldPassword.toUpperCase())) {
                throw new ApplicationException("Old and New Password cannot be same");
            }
            if (reNewPassword.equals("")) {
                throw new ApplicationException("Retype Password cannot be empty");
            }
            if (!newPassword.toUpperCase().equals(reNewPassword.toUpperCase())) {

                throw new ApplicationException("Retype Password and NewPassword should be same ");
            }

            list = em.createNativeQuery("SELECT PASSWORD FROM securityinfo WHERE USERID='" + userName + "'").getResultList();
            Vector v1 = (Vector) list.get(0);
            String existingPwd = v1.elementAt(0).toString();
            if (!CbsUtil.isPasswordValid(existingPwd, oldPassword)) {
                throw new ApplicationException("Old Password is incorrect");
            }

            List oldPwList = em.createNativeQuery("SELECT distinct a.password FROM securityinfohistory a "
                    + " INNER JOIN (SELECT distinct pwdate FROM securityinfohistory where userid ='" + userName + "' "
                    + " order by pwdate desc LIMIT 2) b ON a.pwdate = b.pwdate where a.userid ='" + userName + "'").getResultList();
            if (!oldPwList.isEmpty()) {
                for (int p = 0; p < oldPwList.size(); p++) {
                    Vector p1 = (Vector) oldPwList.get(p);
                    String oldExistPwd = p1.elementAt(0).toString();
                    if (CbsUtil.isPasswordValid(oldExistPwd, newPassword)) {
                        throw new ApplicationException("New Password can't be same as previous 3 password");
                    }
                }
            }

            List l7 = em.createNativeQuery("select coalesce(max(sno),0) from securityinfohistory where userid=UPPER('" + userName + "')").getResultList();
            if (!l7.isEmpty()) {
                Vector v5 = (Vector) l7.get(0);
                tmpSno = Float.parseFloat(v5.get(0).toString());
                tmpSno = tmpSno + 1;
            }

            Query q = em.createNativeQuery("insert securityinfohistory(sno,LevelId,UserId,Password,UserName,tocashlimit,lastLoginDate,"
                    + "Address , Status, enterby, pwDate, cashierst, login,clgdebit,trandebit,brncode,orgbrncode,todate,fromdate,deputeorxfer,"
                    + "lastupdatedt,NpciUserName,FailedAttemptCount,LastFailedLoginDate) select " + tmpSno + ",LevelId,UserId,Password,UserName,"
                    + "tocashlimit,lastLoginDate,Address , Status, enterby, pwDate, cashierst, login, clgdebit,trandebit,brncode,orgbrncode,"
                    + "todate,fromdate,deputeorxfer,CURRENT_TIMESTAMP,NpciUserName,FailedAttemptCount,LastFailedLoginDate from  securityinfo  "
                    + "where userid=UPPER('" + userName + "')");
            int int4 = q.executeUpdate();
            if (int4 <= 0) {
                throw new ApplicationException("Error! Inserting data in securityinfohistory.");
            }
            Query q1 = em.createNativeQuery("UPDATE securityinfo SET password='" + CbsUtil.encodePassword(newPassword)
                    + "', pwdate=NOW() WHERE userid='" + userName + "'");
            Integer int2 = q1.executeUpdate();
            if (int2 > 0) {
                q1 = em.createNativeQuery("UPDATE securityinfo SET login='Y',sid=NULL WHERE userid='" + userName + "' AND levelid NOT IN (5,6)");
                int var = q1.executeUpdate();
                if (var < 0) {
                    throw new ApplicationException("Password could not be changed.");
                }
            } else {
                throw new ApplicationException("Password could not be changed.");
            }
            ut.commit();
            return "Password updated successfully";

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }

        }
    }

    /*Methods for User Creation */
    public List branchCodeCombo(String brnCode, int levelId) throws ApplicationException {
        List brn = new ArrayList();
        try {
            if (brnCode.equals("90") && levelId == 5) {
                brn = em.createNativeQuery("SELECT BrnCode,BranchName FROM branchmaster ORDER BY BranchName").getResultList();
            } else {
                brn = em.createNativeQuery("SELECT BrnCode,BranchName FROM branchmaster where brnCode='" + Integer.parseInt(brnCode)
                        + "' ORDER BY BranchName").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return brn;
    }

    public String allButtonFunction(String userId, String user, String password, int authLevel, String newCashLimitSend, String newClearingLimitSend,
            String newTransfreLimitSend, String newUserAddressSend, String testflag, String userName, String orgBrnCode, String pwd, String rePwd,
            String toDate, String fromDate, String operCode, String deputeOrXfer, String npciUserName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String message = "";
        Float tmpSno = 0f;
        int sid = 0;
        String tmplogin = "";
        int count = 0;
        String dbaOption = "";
        try {
            ut.begin();
            String status = null;
            if (testflag.equalsIgnoreCase("create")) {
                int flag = 0;
                List l1 = new ArrayList();
                l1 = em.createNativeQuery("select * from securityinfo where userid=UPPER('" + userId + "')").getResultList();
                if (!l1.isEmpty()) {
                    ut.rollback();
                    message = "User ID Already Exist.";
                    return message;
                }
                if (authLevel == 7) {
                    List hoList = em.createNativeQuery("select * from branchmaster where alphacode in('HO','Head Office')").getResultList();
                    if (hoList.isEmpty()) {
                        List codeBookList = getHeadOfcBranchFromCodeBook();
                        Vector vector = (Vector) codeBookList.get(0);
                        String branchCode = vector.get(0).toString();
                        String alphaCodeWithBrnName = vector.get(1).toString();
                        String alphaCode, branchNm;
                        String array[] = null;
                        if (alphaCodeWithBrnName.contains("->")) {
                            array = alphaCodeWithBrnName.split("->");
                        }
                        alphaCode = array[0].toString();
                        branchNm = array[1].toString();
                        Query hoInsertQuery = em.createNativeQuery("insert into branchmaster(branchname,brncode,alphacode,Ex_Counter,block,taluk,office_type,opendate,closedate) "
                                + "values('" + branchNm + "','" + branchCode + "','" + alphaCode + "','N','','','','19000101','20990131')");
                        int hoInsertQueryResult = hoInsertQuery.executeUpdate();
                        if (hoInsertQueryResult <= 0) {
                            ut.rollback();
                            return "System error occured during user creation.";
                        }
                    }
                }
                if ((authLevel == 5) || (authLevel == 6) || (authLevel == 7)) {
                    status = "A";
                } else {
                    //status = "I";
                    status = "A";
                }
                if (!pwd.toUpperCase().equals(rePwd.toUpperCase())) {
                    ut.rollback();
                    return "ReTypePassword Field  and NewPassword Should Be Same ";
                }
                /**
                 * ************Code added by Rohit Krishna
                 * Gupta*****************
                 */
                password = CbsUtil.encodePassword(password);
                /**
                 * *********************************************************************
                 */
                userId = userId.toUpperCase();
                int op = 0;
                if (status.equalsIgnoreCase("A")) {
                    Query idGenQuery = em.createNativeQuery("Insert  securityinfo (levelid,userid,password,lastupdatedt,username,lastlogindate,enterby,"
                            + "address,status,brncode,orgbrncode,pwDate,npciusername)"
                            + " values ('" + authLevel + "','" + userId + "','" + password + "',DATE_FORMAT(NOW(), '%Y%m%d'),'"
                            + user.toUpperCase() + "',DATE_FORMAT(NOW(), '%Y%m%d'),'" + userName + "','" + newUserAddressSend + "','"
                            + status + "','" + orgBrnCode + "','" + orgBrnCode + "','19000101','" + npciUserName + "')");
                    op = idGenQuery.executeUpdate();
                } else {
                    Query idGenQuery = em.createNativeQuery("Insert  securityinfo (levelid,userid,password,lastupdatedt,username,lastlogindate,enterby,"
                            + "address,status,brncode,orgbrncode,pwdate,LOGIN,npciusername)"
                            + " values ('" + authLevel + "','" + userId + "','" + password + "',DATE_FORMAT(NOW(), '%Y%m%d'),'"
                            + user.toUpperCase() + "',DATE_FORMAT(NOW(), '%Y%m%d'),'" + userName + "','" + newUserAddressSend + "','" + status
                            + "','" + orgBrnCode + "','" + orgBrnCode + "','19000101','Y','" + npciUserName + "')");
                    op = idGenQuery.executeUpdate();
                }
                if (op <= 0) {
                    ut.rollback();
                    message = "User ID not created.";
                    return message;
                }
                /**
                 * ***********End******************
                 */
                int finQ = 0;
                List l2 = em.createNativeQuery("select tocashlimit,clgdebit,trandebit from securityinfo where userid=UPPER('" + userId + "') "
                        + "and levelid not in (5,6)").getResultList();
                if (!l2.isEmpty()) {
                    if (newCashLimitSend.equalsIgnoreCase("")) {
                        newCashLimitSend = "0";
                    }
                    if (newClearingLimitSend.equalsIgnoreCase("")) {
                        newClearingLimitSend = "0";
                    }
                    if (newTransfreLimitSend.equalsIgnoreCase("")) {
                        newTransfreLimitSend = "0";
                    }
                    Query q2 = em.createNativeQuery("UPDATE securityinfo SET tocashlimit ='" + newCashLimitSend + "', clgdebit ='" + newClearingLimitSend
                            + "', trandebit='" + newTransfreLimitSend + "'  where userid=UPPER('" + userId + "') and levelid not in (5,6)");
                    finQ = q2.executeUpdate();
                    if (finQ <= 0) {
                        ut.rollback();
                        message = "User ID not created.";
                        return message;
                    }
                }
                ut.commit();
                message = "User Id created successfully.";
                return message;
            } /**
             * ***********************************for update
             * ********************************************
             */
            else if (testflag.equalsIgnoreCase("update")) {
//                pwd = CbsUtil.getMessageDigest(pwd);
//                rePwd = CbsUtil.getMessageDigest(rePwd);
                if (deputeOrXfer.equalsIgnoreCase("Transfer")) {
//                    fromDate = "19000101";
                    fromDate = ymd.format(new Date());
                    toDate = "19000101";
                }
                List l9 = em.createNativeQuery("select ifnull(login,'') from securityinfo where userid=UPPER('" + userId + "') and status='A'").getResultList();
                if (!l9.isEmpty()) {

                    Vector v6 = (Vector) l9.get(0);
                    tmplogin = v6.get(0).toString();
                    /*Put "Y" in place of "N" by Nishant Kansal*/
                    if (tmplogin.toUpperCase().equalsIgnoreCase("N")) {
                        ut.rollback();
                        return "User is already logged in or check the login status.";
                    }
                    tmpSno = 1f;
                    List l7 = em.createNativeQuery("select coalesce(max(sno),0) from securityinfohistory where userid=UPPER('" + userId + "')").getResultList();
                    if (!l7.isEmpty()) {
                        Vector v5 = (Vector) l7.get(0);
                        tmpSno = Float.parseFloat(v5.get(0).toString());
                        tmpSno = tmpSno + 1;
                    }
                    Query q1 = em.createNativeQuery("insert securityinfohistory(sno,LevelId,UserId,Password,UserName,tocashlimit,lastLoginDate,"
                            + "Address , Status, enterby, pwDate, cashierst, login,clgdebit,trandebit,brncode,orgbrncode,todate,fromdate,deputeorxfer,"
                            + "lastupdatedt,NpciUserName,FailedAttemptCount,LastFailedLoginDate) select " + tmpSno + ",LevelId,UserId,Password,UserName,"
                            + "tocashlimit,lastLoginDate,Address , Status, enterby, pwDate, cashierst, login, clgdebit,trandebit,brncode,orgbrncode,"
                            + "todate,fromdate,deputeorxfer,CURRENT_TIMESTAMP,NpciUserName,FailedAttemptCount,LastFailedLoginDate from  securityinfo  "
                            + "where userid=UPPER('" + userId + "')");
                    int int4 = q1.executeUpdate();
                    if (int4 > 0) {
                        if (!pwd.equalsIgnoreCase("")) {
                            if ((authLevel != 5) || authLevel == 6) {
                                Query q2;
                                if (!deputeOrXfer.equals("0")) {
                                    q2 = em.createNativeQuery("UPDATE securityinfo SET tocashlimit ='" + newCashLimitSend + "', clgdebit ='"
                                            + newClearingLimitSend + "', trandebit='" + newTransfreLimitSend + "',Password='" + CbsUtil.encodePassword(pwd) + "'  "
                                            + ",todate='" + toDate + "',fromdate='" + fromDate + "',deputeorxfer='" + deputeOrXfer + "',brncode='" + operCode + "' "
                                            + ",npciusername='" + npciUserName + "',enterby= '" + userName + "',LastUpdateDt = date_format(now(),'%Y%m%d') where userid=UPPER('" + userId + "') ");

                                } else {
                                    q2 = em.createNativeQuery("UPDATE securityinfo SET tocashlimit ='" + newCashLimitSend + "', clgdebit ='"
                                            + newClearingLimitSend + "', trandebit='" + newTransfreLimitSend + "',Password='" + CbsUtil.encodePassword(pwd) + "'  "
                                            + ",npciusername='" + npciUserName + "',enterby= '" + userName + "',LastUpdateDt = date_format(now(),'%Y%m%d') where userid=UPPER('" + userId + "') ");
                                }

                                q2.executeUpdate();
                            }
                            Query q3;
                            if (!deputeOrXfer.equals("0")) {
                                q3 = em.createNativeQuery("UPDATE securityinfo SET username ='" + user + "', levelid =" + authLevel + ", "
                                        + "address='" + newUserAddressSend + "',Password='" + CbsUtil.encodePassword(pwd) + "',todate='" + toDate + "'"
                                        + ",fromdate='" + fromDate + "',deputeorxfer='" + deputeOrXfer + "',brncode='" + operCode + "',npciusername='" + npciUserName + "',enterby= '" + userName + "',LastUpdateDt = date_format(now(),'%Y%m%d')  where userid=UPPER('" + userId + "')");
                            } else {
                                q3 = em.createNativeQuery("UPDATE securityinfo SET username ='" + user + "', levelid =" + authLevel + ", "
                                        + "address='" + newUserAddressSend + "',Password='" + CbsUtil.encodePassword(pwd) + "',npciusername='" + npciUserName + "',enterby= '" + userName + "',LastUpdateDt = date_format(now(),'%Y%m%d') where userid=UPPER('" + userId + "')");
                            }

                            int int6 = q3.executeUpdate();
                            if (int6 > 0) {
                                ut.commit();
                                return "User id updated successfully.";
                            } else {
                                ut.rollback();
                                return "User id could not be updated.";
                            }
                        } else {
                            if ((authLevel != 5) || authLevel == 6) {
                                Query q2;
                                if (!deputeOrXfer.equals("0")) {
                                    q2 = em.createNativeQuery("UPDATE securityinfo SET tocashlimit ='" + newCashLimitSend + "', clgdebit ='"
                                            + newClearingLimitSend + "', trandebit='" + newTransfreLimitSend + "' "
                                            + ",todate= " + toDate + ",fromdate= " + fromDate + ",deputeorxfer='" + deputeOrXfer + "',brncode='" + operCode + "' "
                                            + ",npciusername='" + npciUserName + "',enterby= '" + userName + "',LastUpdateDt = date_format(now(),'%Y%m%d') where userid=UPPER('" + userId + "') ");

                                } else {
                                    q2 = em.createNativeQuery("UPDATE securityinfo SET tocashlimit ='" + newCashLimitSend + "', clgdebit ='"
                                            + newClearingLimitSend + "', trandebit='" + newTransfreLimitSend + "',npciusername='" + npciUserName + "',enterby= '" + userName + "',LastUpdateDt = date_format(now(),'%Y%m%d') where userid=UPPER('" + userId + "') ");
                                }

                                q2.executeUpdate();
                            }
                            Query q3;
                            if (!deputeOrXfer.equals("0")) {
                                q3 = em.createNativeQuery("UPDATE securityinfo SET username ='" + user + "', levelid =" + authLevel + ", "
                                        + "address='" + newUserAddressSend + "',todate= " + toDate + ""
                                        + ",fromdate= " + fromDate + ",deputeorxfer='" + deputeOrXfer + "',brncode='" + operCode + "',npciusername='" + npciUserName + "',enterby= '" + userName + "',LastUpdateDt = date_format(now(),'%Y%m%d')  where userid=UPPER('" + userId + "')");
                            } else {
                                q3 = em.createNativeQuery("UPDATE securityinfo SET username ='" + user + "', levelid =" + authLevel + ", "
                                        + "address='" + newUserAddressSend + "',npciusername='" + npciUserName + "',enterby= '" + userName + "',LastUpdateDt = date_format(now(),'%Y%m%d') where userid=UPPER('" + userId + "')");
                            }

                            int int6 = q3.executeUpdate();
                            if (int6 > 0) {
                                ut.commit();
                                return "User id updated successfully.";
                            } else {
                                ut.rollback();
                                return "User id could not be updated.";
                            }
                        }

                    } else {
                        ut.rollback();
                        return "User id could not be updated.";
                    }
                }
            } /**
             * **************************************************** for
             * deleting the user
             * ****************************************************
             */
            else if (testflag.equalsIgnoreCase("delete")) {
                List list15 = em.createNativeQuery("select login from securityinfo where userid=UPPER('" + userId + "')").getResultList();
                if (!list15.isEmpty()) {
                    Vector v5 = (Vector) list15.get(0);
                    /*Put "Y" in place of "N" by Nishant Kansal*/
                    if (v5.get(0).toString().toUpperCase().equals("N")) {
                        ut.rollback();
                        return "User is already logged in or check the login status.";
                    } else {
                        tmpSno = 1f;
                        List l7 = em.createNativeQuery("select coalesce(max(sno),0) from securityinfohistory where userid=UPPER('" + userId + "') ").getResultList();
                        if (!l7.isEmpty()) {
                            Vector v6 = (Vector) l7.get(0);
                            tmpSno = Float.parseFloat(v6.get(0).toString());
                            tmpSno = tmpSno + 1;
                        }
                        Query q4 = em.createNativeQuery("insert securityinfohistory(sno,LevelId,UserId,Password,UserName,tocashlimit,lastLoginDate,"
                                + "Address , Status, enterby, pwDate, cashierst, login,clgdebit,trandebit,brncode,orgbrncode,todate,fromdate,deputeorxfer,"
                                + "lastupdatedt,NpciUserName,FailedAttemptCount,LastFailedLoginDate) select " + tmpSno + ",LevelId,UserId,Password,UserName,"
                                + "tocashlimit,lastLoginDate,Address , Status, enterby, pwDate, cashierst, login, clgdebit,trandebit,brncode,orgbrncode,"
                                + "todate,fromdate,deputeorxfer,CURRENT_TIMESTAMP,NpciUserName,FailedAttemptCount,LastFailedLoginDate from  securityinfo  "
                                + "where userid=UPPER('" + userId + "')");
                        Integer int1 = q4.executeUpdate();
                        if (int1 > 0) {
                            Query q5 = em.createNativeQuery(" update securityinfo set status='D',enterby= '" + userName + "',LastUpdateDt = date_format(now(),'%Y%m%d') where userID=UPPER('" + userId + "')");
                            int int2 = q5.executeUpdate();
                            if (int2 > 0) {
                                ut.commit();
                                message = "User ID is Deleted Successfully";
                                return message;
                            } else {
                                ut.rollback();
                                message = "User not deleted successfully,try again";
                                return message;
                            }
                        } else {
                            ut.rollback();
                            message = "User not deleted successfully,try again";
                            return message;
                        }
                    }
                }
            } /**
             * ***************************************************** for
             * expiring the user
             * ************************************************
             */
            else if (testflag.equalsIgnoreCase("Expire")) {
                List list23 = em.createNativeQuery("select login from securityinfo where userid=UPPER('" + userId + "')").getResultList();
                if (!list23.isEmpty()) {
                    Vector v6 = (Vector) list23.get(0);
                    /*Put "Y" in place of "N" by Nishant Kansal*/
                    if (v6.get(0).toString().toUpperCase().equals("N")) {
                        ut.rollback();
                        return "User is already logged in or check the login status.";
                    } else {
                        tmpSno = 1f;
                        List list21 = em.createNativeQuery("select coalesce(max(sno),0) from securityinfohistory where userid=UPPER('" + userId + "')").getResultList();
                        if (!list21.isEmpty()) {
                            Vector v3 = (Vector) list21.get(0);
                            tmpSno = Float.parseFloat(v3.get(0).toString());
                            tmpSno = tmpSno + 1;
                        }
                        Query q1 = em.createNativeQuery("insert securityinfohistory(sno,LevelId,UserId,Password,UserName,tocashlimit,lastLoginDate,"
                                + "Address , Status, enterby, pwDate, cashierst, login,clgdebit,trandebit,brncode,orgbrncode,todate,fromdate,deputeorxfer,"
                                + "lastupdatedt,NpciUserName,FailedAttemptCount,LastFailedLoginDate) select " + tmpSno + ",LevelId,UserId,Password,UserName,"
                                + "tocashlimit,lastLoginDate,Address , Status, enterby, pwDate, cashierst, login, clgdebit,trandebit,brncode,orgbrncode,"
                                + "todate,fromdate,deputeorxfer,CURRENT_TIMESTAMP,NpciUserName,FailedAttemptCount,LastFailedLoginDate from  securityinfo  "
                                + "where userid=UPPER('" + userId + "')");
                        int int1 = q1.executeUpdate();
                        if (int1 > 0) {
                            List list25 = em.createNativeQuery("SELECT  count(sno) from  securityinfohistory where sno =" + tmpSno
                                    + " and UserId=UPPER('" + userId + "')").getResultList();
                            Vector v2 = (Vector) list25.get(0);
                            count = Integer.parseInt(v2.get(0).toString());
                            if (count == 0) {
                                ut.rollback();
                                message = "userid could not  deactivated";
                                return message;
                            } else {
                                Query q2 = em.createNativeQuery("update securityinfo set status='C',enterby= '" + userName + "',LastUpdateDt = date_format(now(),'%Y%m%d') where userID=UPPER('" + userId + "')");
                                Integer int2 = q2.executeUpdate();
                                if (int2 > 0) {
                                    ut.commit();
                                    message = "User ID is DeActivated Successfully";
                                    return message;
                                } else {
                                    ut.rollback();
                                    message = "userid could not  deactivated";
                                    return message;
                                }
                            }
                        } else {
                            ut.rollback();
                            message = "userid could not deactivated";
                            return message;
                        }
                    }
                }
            } /**
             * ************************************************ for activate
             * ************************************
             */
            else if (testflag.equalsIgnoreCase("Activate")) {
                tmpSno = 1f;
                List list21 = em.createNativeQuery("select coalesce(max(sno),0) from securityinfohistory where userid=UPPER('" + userId + "')").getResultList();
                if (!list21.isEmpty()) {
                    Vector v3 = (Vector) list21.get(0);
                    tmpSno = Float.parseFloat(v3.get(0).toString());
                    tmpSno = tmpSno + 1;
                }
                if (dbaOption.toUpperCase().equals("Y")) {
                    List l17 = em.createNativeQuery("select * from securityinfo  where userid=UPPER('" + userId + "') and login='Y'").getResultList();
                    if (!l17.isEmpty()) {
                        Query q1 = em.createNativeQuery("insert securityinfohistory(sno,LevelId,UserId,Password,UserName,tocashlimit,lastLoginDate,"
                                + "Address , Status, enterby, pwDate, cashierst, login,clgdebit,trandebit,brncode,orgbrncode,todate,fromdate,deputeorxfer,"
                                + "lastupdatedt,NpciUserName,FailedAttemptCount,LastFailedLoginDate) select " + tmpSno + ",LevelId,UserId,Password,UserName,"
                                + "tocashlimit,lastLoginDate,Address , Status, enterby, pwDate, cashierst, login, clgdebit,trandebit,brncode,orgbrncode,"
                                + "todate,fromdate,deputeorxfer,CURRENT_TIMESTAMP,NpciUserName,FailedAttemptCount,LastFailedLoginDate from  securityinfo  "
                                + "where userid=UPPER('" + userId + "')");
                        int int4 = q1.executeUpdate();
                        if (int4 > 0) {
                            Query q2 = em.createNativeQuery("update securityinfo set status='A',enterby= '" + userName + "',LastUpdateDt = date_format(now(),'%Y%m%d'),FailedAttemptCount=0 where userID=UPPER('" + userId + "')");
                            int int5 = q2.executeUpdate();
                            if (int5 > 0) {
                                ut.commit();
                                message = "UserId is activated successfully";
                                return message;
                            }
                        } else {
                            ut.rollback();
                            message = "UserId could not be activated successfully";
                            return message;
                        }
                    }
                } else {
                    List list18 = em.createNativeQuery("select * from securityinfo  where userid=UPPER('" + userId + "')").getResultList();
                    if (!list18.isEmpty()) {
                        Query q3 = em.createNativeQuery("insert securityinfohistory(sno,LevelId,UserId,Password,UserName,tocashlimit,lastLoginDate,"
                                + "Address , Status, enterby, pwDate, cashierst, login,clgdebit,trandebit,brncode,orgbrncode,todate,fromdate,deputeorxfer,"
                                + "lastupdatedt,NpciUserName,FailedAttemptCount,LastFailedLoginDate) select " + tmpSno + ",LevelId,UserId,Password,UserName,"
                                + "tocashlimit,lastLoginDate,Address , Status, enterby, pwDate, cashierst, login, clgdebit,trandebit,brncode,orgbrncode,"
                                + "todate,fromdate,deputeorxfer,CURRENT_TIMESTAMP,NpciUserName,FailedAttemptCount,LastFailedLoginDate from  securityinfo  "
                                + "where userid=UPPER('" + userId + "')");
                        int int2 = q3.executeUpdate();
                        if (int2 > 0) {
                            Query q4 = em.createNativeQuery("update securityinfo set status='A',enterby= '" + userName + "',LastUpdateDt = date_format(now(),'%Y%m%d'),FailedAttemptCount=0 where userID=UPPER('" + userId + "')");
                            int int3 = q4.executeUpdate();
                            if (int3 > 0) {
                                ut.commit();
                                message = "User ID is Activated Successfully";
                                return message;
                            } else {
                                ut.rollback();
                                message = "UserId could not be activated successfully";
                                return message;
                            }
                        } else {
                            ut.rollback();
                            message = "User ID is not  Activated Successfully";
                            return message;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return message;
    }

    public List getAccountDetails(String userId, String hostName, String orgnBranch) throws ApplicationException {
        List list7 = null;
        try {
            list7 = em.createNativeQuery("select ifnull(username,''),ifnull(userid,''),ifnull(status,''),ifnull(address,''),"
                    + "coalesce(levelid,0),coalesce(tocashlimit,''),coalesce(clgdebit,''),coalesce(trandebit,'') ,Password"
                    + ",ifnull(todate,curdate()),ifnull(fromdate,curdate()),ifnull(brncode,0),ifnull(deputeorxfer,0),ifnull(npciusername,'') from securityinfo "
                    + "where userid='" + userId + "'").getResultList();
            return list7;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String userIdCheck(String userId, String brCode) throws ApplicationException {
        try {
            if (userId == null || userId.equalsIgnoreCase("") || userId.length() == 0) {
                return "Please enter user ID.";
            }
            List chk = em.createNativeQuery("SELECT UserId,brncode FROM securityinfo WHERE USERID='" + userId + "'").getResultList();
            if (chk.isEmpty()) {
                return "true";
            } else {
                return "This user id is already exists.";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getSuperUserLevelId(String userId) throws ApplicationException {
        List list = new ArrayList();
        try {
            list = em.createNativeQuery("select levelid,brncode from securityinfo where userid = '" + userId + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getHeadOfficeBranchCode() throws ApplicationException {
        List list = new ArrayList();
        try {
            list = em.createNativeQuery("select brncode,alphacode from branchmaster where alphacode in ('HO','HEAD OFFICE')").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getHeadOfcBranchFromCodeBook() throws ApplicationException {
        List list = new ArrayList();
        try {
            list = em.createNativeQuery("select code,description from codebook where groupcode='101'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List gridLoad(String brCode, String userId) throws ApplicationException {
        List gridList = new ArrayList();
        try {
            List brnNameList = em.createNativeQuery("select alphacode from branchmaster where brncode='" + brCode + "'").getResultList();
            Vector vectorBrnName = (Vector) brnNameList.get(0);
            String brnName = (String) vectorBrnName.get(0);
            if (brnName.equalsIgnoreCase("HO") || brnName.equalsIgnoreCase("HEAD OFFICE")) {
                List levelList = em.createNativeQuery("select levelid from securityinfo where USERID = '" + userId + "'").getResultList();

                Vector vecLevelId = (Vector) levelList.get(0);
                String levelId = vecLevelId.get(0).toString();

                if (levelId.equals("5")) {
                    gridList = em.createNativeQuery("select upper(userid),upper(username),upper(status),upper(login),upper(cashierst),upper(HEADCASHIER) "
                            + " from securityinfo where userid <> '" + userId + "' and status not in('C','D') and levelid not in(7) AND USERID<>'SYSTEM' order by userid").getResultList();
                } else {
                    gridList = em.createNativeQuery("select upper(userid),upper(username),upper(status),upper(login),upper(cashierst),upper(HEADCASHIER) "
                            + " from securityinfo where userid <> '" + userId + "' and status not in('C','D') and levelid not in(5,6,7) AND USERID<>'SYSTEM' and brncode = '" + brCode + "' order by userid").getResultList();
                }
            } else {
                gridList = em.createNativeQuery("select upper(userid),upper(username),upper(status),upper(login),upper(cashierst),upper(HEADCASHIER) "
                        + " from securityinfo where userid <> '" + userId + "' and status not in('C','D') and levelid not in(5,6,7) AND USERID<>'SYSTEM' and brncode = '" + brCode + "' order by userid").getResultList();
            }
            return gridList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    public String getBankName(String brCode) {
//        try {
//            Vector ele = dividendCalculationRemote.getBranchNameandAddress(brCode);
//            if (ele.get(0) != null) {
//                return ele.get(0).toString();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }

    private String getBranchName(String brCode) throws ApplicationException {
        try {
            int brn = Integer.parseInt(brCode);
            List list = em.createNativeQuery("select branchname from branchmaster where brncode=" + brn + "").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                if (ele.get(0) != null) {
                    return ele.get(0).toString();
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return null;
    }

    public String saveActiveUserDetail(List<UserInfo> list, String enterBy, String brcode) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            Integer var = 0;
            for (UserInfo userInfo : list) {
                String usrId = userInfo.getUserId();
                String status = "I";
                if (userInfo.getStatus().equalsIgnoreCase("Active")) {
                    status = "A";
                }

                String login = userInfo.getAllowLogin();
                String crAccess = "N";
                if (userInfo.getCashierAccess().equalsIgnoreCase("Allowed")) {
                    crAccess = "Y";
                }

                String headCashierAccess = "N";
                if (userInfo.getHeadCashierAccess().equalsIgnoreCase("Allowed")) {
                    headCashierAccess = "Y";
                }

                List recList = em.createNativeQuery("select sno,status,login,cashierst from securityinfohistory where userid = '" + usrId
                        + "' and sno = (select max(sno) from securityinfohistory where userid = '" + usrId + "')").getResultList();
                float recnum = 1f;
                if (!recList.isEmpty()) {
                    Vector recLst = (Vector) recList.get(0);
                    String recListed = recLst.get(0).toString();
                    recnum = Float.parseFloat(recListed) + 1;
                }

                List recList2 = em.createNativeQuery("select status,login,cashierst,HEADCASHIER from securityinfo where userid = '" + usrId + "'").getResultList();

                Vector recLst2 = (Vector) recList2.get(0);
                String statusOld = recLst2.get(0).toString();
                String loginOld = recLst2.get(1).toString();

                String cashierOld = recLst2.get(2).toString();
                String headCashierOld = recLst2.get(3).toString();

                if ((!status.equalsIgnoreCase(statusOld)) || (!login.equalsIgnoreCase(loginOld)) || (!crAccess.equalsIgnoreCase(cashierOld))
                        || (!headCashierAccess.equalsIgnoreCase(headCashierOld))) {
                    Query insertQuery1 = em.createNativeQuery("insert into securityinfohistory (SNO,LevelId,UserId,Password,UserName,tocashlimit,"
                            + "lastLoginDate,Address,Status, enterby,pwDate,cashierst,login,HEADCASHIER,lastupdatedt,NpciUserName,"
                            + "FailedAttemptCount,LastFailedLoginDate,brncode,orgbrncode) select " + recnum + ",LevelId,UserId,Password,UserName,tocashlimit,"
                            + "lastLoginDate,Address,Status, '" + enterBy + "', pwDate, cashierst,login,HEADCASHIER,CURRENT_TIMESTAMP,"
                            + "NpciUserName,FailedAttemptCount,LastFailedLoginDate,brncode,orgbrncode from securityinfo where userid = '" + usrId + "'");
                    insertQuery1.executeUpdate();

                    Query updateQuery = em.createNativeQuery("update securityinfo set status = '" + status + "',login='" + login + "',cashierst='"
                            + crAccess + "',HEADCASHIER='" + headCashierAccess + "',FailedAttemptCount=0,LastUpdateDt = date_format(now(),'%Y%m%d') "
                            + "where userid = '" + usrId + "'");
                    var = updateQuery.executeUpdate();
                    if (var <= 0) {
                        throw new ApplicationException("Problem in data updation");
                    }

                }
            }
            ut.commit();
            return "true";
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new ApplicationException(syex.getMessage());
            }
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<UserGrid> getUserRecord(String status, String fromDate, String toDate) throws ApplicationException {
        List<UserGrid> resultList = new ArrayList<UserGrid>();
        try {
            String query = "";
            if (status.equalsIgnoreCase("New User")) {
                query = "select levelid,userid,username,status,brncode,orgbrncode,ifnull(date_format(fromdate,'%d/%m/%Y'),'') fromdate,ifnull(date_format(todate,'%d/%m/%Y'),'') todate,deputeorxfer from securityinfo where status='A' and deputeorxfer is null";
            } else if (status.equalsIgnoreCase("Deputation")) {
                query = "select levelid,userid,username,status,brncode,orgbrncode,ifnull(date_format(fromdate,'%d/%m/%Y'),'') fromdate,ifnull(date_format(todate,'%d/%m/%Y'),'') todate,deputeorxfer from securityinfo where deputeorxfer='" + status + "' and fromdate >= '" + fromDate + "' and todate <='" + toDate + "'";
            } else if (status.equalsIgnoreCase("Transfer")) {
                query = "select levelid,userid,username,status,brncode,orgbrncode,ifnull(date_format(fromdate,'%d/%m/%Y'),'') fromdate,ifnull(date_format(todate,'%d/%m/%Y'),'') todate,deputeorxfer from securityinfo where deputeorxfer='" + status + "'";
            }
            if (!query.equalsIgnoreCase("")) {
                List data = em.createNativeQuery(query).getResultList();
                for (int i = 0; i < data.size(); i++) {
                    UserGrid currentRow = new UserGrid();
                    Vector vec = (Vector) data.get(i);
                    if (vec.get(0) != null) {
                        currentRow.setLevelid(Short.parseShort(vec.get(0).toString()));
                    }
                    if (vec.get(1) != null) {
                        currentRow.setUserid(vec.get(1).toString());
                    }
                    if (vec.get(2) != null) {
                        currentRow.setUsername(vec.get(2).toString());
                    }
                    if (vec.get(3) != null) {
                        currentRow.setStatus(vec.get(3).toString());
                    }
                    if (vec.get(4) != null) {
                        currentRow.setBrncode(getBranchName(vec.get(4).toString()));
                    }
                    if (vec.get(5) != null) {
                        currentRow.setOrgbrncode(getBranchName(vec.get(5).toString()));
                    }
                    if (vec.get(6) != null) {
                        currentRow.setFromdate(vec.get(6).toString());
                    }
                    if (vec.get(7) != null) {
                        currentRow.setTodate(vec.get(7).toString());
                    }
                    if (vec.get(8) != null) {
                        currentRow.setDeputeorxfer(vec.get(8).toString());
                    }
                    resultList.add(currentRow);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return resultList;
    }

    /**
     *
     * @param userName
     * @param brCode
     * @return
     * @throws ApplicationException
     */
    public List<UserDTO> getUserByUsername(String userName, String brCode) throws ApplicationException {
        List<UserDTO> users = new ArrayList<UserDTO>();
        try {
            long pwdExpiry = 45L;
            List pwdExpiryList = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME='PWD EXPIRY'").getResultList();
            if (pwdExpiryList.size() > 0) {
                Vector pwdExpiryVect = (Vector) pwdExpiryList.get(0);
                pwdExpiry = Long.parseLong(pwdExpiryVect.elementAt(0).toString());
            }
            List userDataList = em.createNativeQuery("SELECT LEVELID, STATUS, LOGIN, PASSWORD, DATE_FORMAT(PWDATE, '%Y%m%d') FROM securityinfo WHERE "
                    + "USERID= '" + userName + "' AND brncode = '" + brCode + "'").getResultList();
            if (userDataList.isEmpty()) {
                throw new ApplicationException("User does not exist.");
            } else {
                Vector userDataVect = (Vector) userDataList.get(0);
                int levelId = Integer.parseInt(userDataVect.get(0).toString());
                String status = userDataVect.get(1).toString();

                String login = userDataVect.get(2).toString();
                String existingPwd = userDataVect.get(3).toString();
                String pwDt = userDataVect.get(4).toString();
                long days = CbsUtil.dayDiff(ymd.parse(pwDt), new Date());

                UserDTO userDTO = new UserDTO();
                userDTO.setUsername(userName);

                userDTO.setPassword(existingPwd);
                userDTO.setRole(RoleTypes.getRole(levelId));

                if (status.equalsIgnoreCase("D") || status.equalsIgnoreCase("C")) {
                    userDTO.setAccountNonExpired(false);
                    userDTO.setEnabled(true);
                } else if (status.equalsIgnoreCase("I")) {
                    userDTO.setAccountNonExpired(true);
                    userDTO.setEnabled(false);
                } else {
                    userDTO.setAccountNonExpired(true);
                    userDTO.setEnabled(true);
                }
                if (days > pwdExpiry) {
                    userDTO.setCredentialsNonExpired(false);
                } else {
                    userDTO.setCredentialsNonExpired(true);
                }
                if (login.equalsIgnoreCase("N")) {
                    userDTO.setAccountNonLocked(false);
                } else {
                    userDTO.setAccountNonLocked(true);
                }
                if (levelId == 5 || levelId == 6 || levelId == 7) {
                    userDTO.setAccountNonLocked(true);
                }
                users.add(userDTO);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return users;
    }

    public String getBankWorkingDate() throws ApplicationException {
        try {
            List bankDayList = em.createNativeQuery("SELECT MIN(DATE_FORMAT(DATE, '%Y%m%d')) FROM cbs_bankdays WHERE DAYENDFLAG='N' "
                    + "AND DAYBEGINFLAG <> 'H'").getResultList();

            if (bankDayList.isEmpty()) {
                throw new ApplicationException("Bank Level Day begin data does not exist.");
            }
            Vector bankDayVect = (Vector) bankDayList.get(0);
            return bankDayVect.elementAt(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getBranchWorkingDate(String brCode) throws ApplicationException {
        try {
            List bankDayList = em.createNativeQuery("SELECT MIN(DATE_FORMAT(DATE, '%Y%m%d')) FROM bankdays WHERE DAYENDFLAG1='N' AND "
                    + "DAYBEGINFLAG <> 'H' AND BRNCODE = '" + brCode + "'").getResultList();

            if (bankDayList.isEmpty()) {
                throw new ApplicationException("Branch Level Day begin data does not exist.");
            }
            Vector bankDayVect = (Vector) bankDayList.get(0);
            return bankDayVect.elementAt(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getDBCurDate() throws ApplicationException {
        try {
            List currentDateList = em.createNativeQuery("SELECT DATE_FORMAT(NOW(), '%Y%m%d')").getResultList();
            Vector bankDayVect = (Vector) currentDateList.get(0);
            return bankDayVect.elementAt(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public boolean isCashier(String brCode, String userName) throws ApplicationException {
        try {
            List userDataList = em.createNativeQuery("SELECT cashierst FROM securityinfo WHERE "
                    + "USERID= '" + userName + "' AND brncode = '" + brCode + "'").getResultList();
            if (userDataList.isEmpty()) {
                throw new ApplicationException("User does not exist.");
            } else {
                Vector userDataVect = (Vector) userDataList.get(0);
                String cashier = userDataVect.get(0).toString();
                if (cashier.equalsIgnoreCase("Y")) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String updateFailedLoginStatus(String userId) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int maxFailedAttempt = 3;
            List failedDataList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where reportname= 'MAX-FAILED-ATTEMPT'").getResultList();
            if (!failedDataList.isEmpty()) {
                Vector userDataVect = (Vector) failedDataList.get(0);
                if (Integer.parseInt(userDataVect.get(0).toString()) != 0) {
                    maxFailedAttempt = Integer.parseInt(userDataVect.get(0).toString());
                }
            }
            List userDataList = em.createNativeQuery("SELECT LEVELID FROM securityinfo WHERE  USERID= '" + userId + "' ").getResultList();
            if (userDataList.isEmpty()) {
                throw new ApplicationException("User does not exist.");
            }
            Vector userDataVect = (Vector) userDataList.get(0);
            int levelId = Integer.parseInt(userDataVect.get(0).toString());
            if (levelId != 5) {
                userDataList = em.createNativeQuery("select FailedAttemptCount from securityinfo where userid= '" + userId + "'").getResultList();
                if (userDataList.isEmpty()) {
                    throw new ApplicationException("User does not exist.");
                }
                userDataVect = (Vector) userDataList.get(0);
                int failedAttempt = Integer.parseInt(userDataVect.get(0).toString());
                String login = "Y";
                String status = "A";
                if (failedAttempt >= maxFailedAttempt) {
                    login = "N";
                    status = "I";
                }
                failedAttempt = failedAttempt + 1;
                Query q1 = em.createNativeQuery("update securityinfo set login ='" + login + "', status='" + status + "', FailedAttemptCount="
                        + failedAttempt + ", LastFailedLoginDate = now() where userid='" + userId + "'");
                Integer int2 = q1.executeUpdate();
                if (int2 <= 0) {
                    throw new ApplicationException("Problem in data updation for this user.");
                }
            }
            ut.commit();
            return "True";
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new ApplicationException(syex.getMessage());
            }
            throw new ApplicationException(e.getMessage());
        }
    }

    public boolean isCbsPymtMade() throws ApplicationException {
        try {
            List dataList = em.createNativeQuery("select Min(due_dt),grace_pd from cbs_pymt_details where pymt_flag = 'N'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Payment related data does not exist");
            }
            Vector dataVect = (Vector) dataList.get(0);
            String dueDt = dataVect.get(0).toString();

            int gracePd = Integer.parseInt(dataVect.get(1).toString());
            String tmpDt = CbsUtil.dateAdd(dueDt, gracePd);

            if (ymd.parse(ymd.format(new Date())).getTime() < ymd.parse(tmpDt).getTime()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new ApplicationException("From CBS Payment Made, The Error is ---------->" + e.getMessage());
        }
    }

    public List getUserDetails(String userId) throws ApplicationException {
        try {
            List userDataList = em.createNativeQuery("SELECT username,levelid,a.address, b.branchname FROM securityinfo a, branchmaster b "
                    + " WHERE cast(a.brncode as unsigned)= b.brncode and userid='" + userId + "' and status = 'A' and login='Y'").getResultList();
            if (userDataList.isEmpty()) {
                throw new ApplicationException("User does not exist.");
            }
            return userDataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public boolean isBioMetricRegistred(String userId) throws ApplicationException {
        try {
            List userDataList = em.createNativeQuery("SELECT txnid from cbs_user_biometric_template where userid='" + userId + "'").getResultList();
            if (userDataList.isEmpty()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String saveUpdateBiometricDetails(List<FingerDataObj> objList, String fingersUerId, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List userDataList = em.createNativeQuery("SELECT txnid from cbs_user_biometric_template where userid='" + fingersUerId + "'").getResultList();
            if (!userDataList.isEmpty()) {
                Query insertQuery1 = em.createNativeQuery("insert into cbs_user_biometric_template_his (userid,isotemplate,enterby,enterydate) "
                        + " select userid,isotemplate,enterby,enterydate from cbs_user_biometric_template where userid = '" + fingersUerId + "'");
                int rs = insertQuery1.executeUpdate();
                if (rs <= 0) {
                    throw new ApplicationException("Problem in data insertion");
                }
                insertQuery1 = em.createNativeQuery("delete from cbs_user_biometric_template where userid = '" + fingersUerId + "'");
                rs = insertQuery1.executeUpdate();
                if (rs <= 0) {
                    throw new ApplicationException("Problem in data deletion");
                }
            }
            for (FingerDataObj obj : objList) {
                CbsUserBiometricTemplate entity = new CbsUserBiometricTemplate();
                entity.setUserid(fingersUerId);

                entity.setIsotemplate(obj.getIsoTemplate());
                entity.setEnterby(userName);

                entity.setEnterydate(new Date());
                em.persist(entity);
            }
            ut.commit();
            return "True";
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

    public String getBiometricParameters(String userId) throws ApplicationException {
        try {
            int code = ftsRemote.getCodeForReportName("BIOMETRIC-USER-AUTH");
            if (code == 1) {
                if (isBioMetricRegistred(userId)) {
                    return "VERIFICATION";
                } else {
                    return "REGISTRATION";
                }
            }
            updateLoginTime(userId);
            return "WELCOME";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<CbsUserBiometricTemplate> getBiometricDetails(String userId) throws ApplicationException {
        try {
            Query query = em.createNamedQuery("CbsUserBiometricTemplate.findByUserid");
            query.setParameter("userid", userId);
            List<CbsUserBiometricTemplate> resulList = query.getResultList();
            return resulList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<String> getCbsVersionNo(String userId) throws ApplicationException {
        try {
            List<String> versionList = new ArrayList<>();
            List userList = em.createNativeQuery("select lastlogindate from securityinfo where userid='" + userId + "'").getResultList();
            if (userList.isEmpty()) {
                throw new ApplicationException("User does not exist.");
            }
            Vector userVect = (Vector) userList.get(0);
            String loginTime = userVect.elementAt(0).toString();

            List verList = em.createNativeQuery("SELECT distinct version_no FROM cbs_version_details where update_time > '" + loginTime + "'").getResultList();
            for (int i=0; i<verList.size(); i++) {
                Vector verVect = (Vector) verList.get(0);
                versionList.add(verVect.elementAt(0).toString());
            }
            return versionList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public List<CbsVersionDetails> getCbsVersionDetails(List<String> versionNoList)throws ApplicationException {
        try {
            List<CbsVersionDetails> versionDetails = new ArrayList<>();
            String versionNos = "";
            for(String verNo : versionNoList){
                if (versionNos.equals("")) {
                    versionNos = "\'" +verNo +"\'";
                }else{
                    versionNos = versionNos + ",\'" + verNo +"\'";
                }
            }
            List verList = em.createNativeQuery("SELECT id, version_no, module_name,version_desc FROM cbs_version_details where version_no in (" + versionNos + ")").getResultList();
            CbsVersionDetails verDetail;
            for (int i=0; i<verList.size(); i++) {
                Vector verVect = (Vector) verList.get(i);
                verDetail = new CbsVersionDetails();
                verDetail.setId(Long.parseLong(verVect.get(0).toString()));
                verDetail.setVersionNo(verVect.get(1).toString());
                
                verDetail.setModuleName(verVect.get(2).toString());
                verDetail.setVersionDesc(verVect.get(3).toString());
                
                versionDetails.add(verDetail);
            }
            return versionDetails;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
