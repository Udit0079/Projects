/*
    Document   : WelcomeBean
    Created on : 08 feb, 2011, 10:31:56 PM
    Author     : Zeeshan Waris
 */

package com.cbs.facade.admin;

import com.cbs.dto.CbsVersionDetails;
import com.cbs.dto.FingerDataObj;
import com.cbs.dto.UrlItem;
import com.cbs.dto.UserDTO;
import com.cbs.dto.UserGrid;
import com.cbs.dto.other.UserInfo;
import com.cbs.entity.CbsUserBiometricTemplate;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface AdminManagementFacadeRemote {

    /* Methods for Welcome Page, Index and Session filter*/
   
    /**
     * 
     * @return
     * @throws ApplicationException 
     */
   public String getUserManualUrl()throws ApplicationException;
    
    /**
     * 
     * @param UserId
     * @param brnCode
     * @return
     * @throws ApplicationException 
     */
    public List<UrlItem> getMenu(String UserId, String brnCode) throws ApplicationException;

    /**
     * 
     * @param brnCode
     * @return
     * @throws ApplicationException 
     */
    public List bankAddress(String brnCode) throws ApplicationException;
    
    
    /**
     * 
     * @param userId
     * @return
     * @throws ApplicationException 
     */
    public String getUserName(String userId) throws ApplicationException;

    /**
     * 
     * @param userId
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
    public String updateLoginTime(String userId) throws ApplicationException;

    /**
     * 
     * @param UserId
     * @param brnCode
     * @return
     * @throws ApplicationException 
     */
    public String logOut(String UserId) throws ApplicationException;

    /**
     * 
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
    public boolean isUserExist(String brCode) throws ApplicationException;
    
    /* Method for Change password*/
    
    /**
     * 
     * @param oldPassword
     * @param newPassword
     * @param reNewPassword
     * @param userName
     * @return
     * @throws ApplicationException 
     */
    public String btnChangeAction(String oldPassword,String newPassword,String reNewPassword,String userName)throws ApplicationException;
    
    /* Methods for User creation*/
    
    public String allButtonFunction(String userId, String user, String password, int authLevel, String newCashLimitSend, String newClearingLimitSend,
            String newTransfreLimitSend, String newUserAddressSend, String testflag, String userName, String orgBrnCode,String pwd,String rePwd,
            String toDate,String fromDate,String operCode,String deputeOrXfer, String npciUserName) throws ApplicationException;

    /**
     * 
     * @param userId
     * @param hostName
     * @param orgnBranch
     * @return
     * @throws ApplicationException 
     */
    public List getAccountDetails(String userId, String hostName, String orgnBranch)throws ApplicationException;

    /**
     * 
     * @return
     * @throws ApplicationException 
     */
    public List branchCodeCombo(String brCode,int levelId) throws ApplicationException;

    /**
     * 
     * @param userId
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
    public String userIdCheck(String userId,String brCode) throws ApplicationException;

    /**
     * 
     * @param userId
     * @return
     * @throws ApplicationException 
     */
    public List getSuperUserLevelId(String userId) throws ApplicationException;

    /**
     * 
     * @return
     * @throws ApplicationException 
     */
    public List getHeadOfficeBranchCode() throws ApplicationException;

    /**
     * 
     * @return
     * @throws ApplicationException 
     */
    public List getHeadOfcBranchFromCodeBook() throws ApplicationException;
    
    /* Methods for User activation*/
    /**
     * 
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
    List gridLoad(String brCode,String userId)throws ApplicationException;

    /**
     * 
     * @param list
     * @param enterBy
     * @param brcode
     * @return
     * @throws ApplicationException 
     */
    String saveActiveUserDetail(List<UserInfo> list, String enterBy, String brcode)throws ApplicationException;

    /**
     * 
     * @param status
     * @param fromDate
     * @param toDate
     * @return
     * @throws ApplicationException 
     */
    public List<UserGrid> getUserRecord(String status, String fromDate, String toDate)throws ApplicationException;

    /**
     * 
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
    public String getBankName(String brCode)throws ApplicationException;
    
    /**
     * 
     * @param userName
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
    public List<UserDTO> getUserByUsername(String userName, String brCode) throws ApplicationException;
    
    /**
     * 
     * @return
     * @throws ApplicationException 
     */
    public String getBankWorkingDate() throws ApplicationException;
    
    /**
     * 
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
    public String getBranchWorkingDate(String brCode) throws ApplicationException;
    
    /**
     * 
     * @return
     * @throws ApplicationException 
     */
    public String getDBCurDate() throws ApplicationException;
    
    /**
     * 
     * @param brCode
     * @param userName
     * @return
     * @throws ApplicationException 
     */
    public boolean isCashier(String brCode, String userName)throws ApplicationException;
    /**
     * 
     * @param userId
     * @return
     * @throws ApplicationException 
     */
    public String updateFailedLoginStatus(String userId) throws ApplicationException;
    
    /**
     * 
     * @return
     * @throws ApplicationException 
     */
    public boolean isCbsPymtMade()throws ApplicationException;
    /**
     * 
     * @param userId
     * @return
     * @throws ApplicationException 
     */
    public List getUserDetails(String userId)throws ApplicationException;
    /**
     * 
     * @param userId
     * @return
     * @throws ApplicationException 
     */
    public boolean isBioMetricRegistred(String userId)throws ApplicationException;
    
    /**
     * 
     * @param objList
     * @param fingersUerId
     * @param userName
     * @return
     * @throws ApplicationException 
     */
    public String saveUpdateBiometricDetails(List<FingerDataObj> objList, String fingersUerId, String userName)throws ApplicationException;
    
    /**
     * 
     * @param userId
     * @return
     * @throws ApplicationException 
     */
    public String getBiometricParameters(String userId) throws ApplicationException;
    
    /**
     * 
     * @param userId
     * @return
     * @throws ApplicationException 
     */
    public List<CbsUserBiometricTemplate> getBiometricDetails(String userId) throws ApplicationException;
    
    /**
     * 
     * @param userId
     * @return
     * @throws ApplicationException 
     */
    public List<String> getCbsVersionNo(String userId) throws ApplicationException;
    
    /**
     * 
     * @param versionNoList
     * @return
     * @throws ApplicationException 
     */
    public List<CbsVersionDetails> getCbsVersionDetails(List<String> versionNoList)throws ApplicationException;
    
}
