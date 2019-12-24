/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.dto.LockerRentCalTable;
import com.cbs.dto.LockerRentDetail;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sudhir
 */
@Remote
public interface LockerManagementFacadeRemote {

    List cabNo(String brCode) throws ApplicationException;

    // public List cabNo1(String brCode) throws ApplicationException;
    List acctTypeCombo(String brCode) throws ApplicationException;

    List modeCombo(String brCode) throws ApplicationException;

    List lockerType(String brCode, String cabNo) throws ApplicationException;

    List lockerTypeComboOnChange(String brCode, String cabNo, String lockerTy) throws ApplicationException;

    List lockerNoLostFocus(String brCode, String cabNo, String lockerNo, String lockerTy) throws ApplicationException;

    public List lockerNoLostFocus1(String brCode, String cabNo, String lockerNo, String lockerTy) throws ApplicationException;

    List gridload(String brCode, String cabNo, String lockerTy) throws ApplicationException;

    List custCatLostFocus(String brCode, String custCat, String lockerTy) throws ApplicationException;

    List acNoLostFocus(String brCode, String acNo) throws ApplicationException;

    // List setRentDueDt(String brCode, String acNo) throws ApplicationException;
    List setCustNameAndAdd(String brCode, String acNo) throws ApplicationException;

    List setRent(String brCode, String cabNo, String lockerTy, String lockerNo) throws ApplicationException;

    List setAdOperators(String brCode, String cabNo, String lockerTy, String lockerNo) throws ApplicationException;

    String saveBtnIssueLocker(String brCode, float cabNo, String lockerTy, float lockerNo, float keyNo, String acNo, float rent, float secDep, String custCat, String mode, String nomination, String remarks, String enterBy, String adOpr1, String adOpr2, String adOpr3, String adOpr4, String rentDueDt,String freqYear) throws ApplicationException;

    String updateLockerInfo(String brCode, float cabNo, String lockerTy, float lockerNo, float keyNo, String acNo, float rent, float secDep, String custCat, String mode, String nomination, String remarks, String enterBy, String adOpr1, String adOpr2, String adOpr3, String adOpr4, String rentDueDt,String freqYear) throws ApplicationException;

    List gridDeatil1(String lableForChange, String custName, String brCode) throws ApplicationException;

    List getLockerMasterData(String lockerType, String orgnBrCode) throws ApplicationException;

    String daybeginDate(String orgnBrCode) throws ApplicationException;

    String saveLockerMaster(String lockerType, Float lockerNo, String btnCaption, Integer lastSno, Float cabinetNo, Float keyNo, String userName, String orgnBrCode, String todayDate) throws ApplicationException;

    List glHead(String brCode) throws ApplicationException;

    List gridLoadForAllAccounts(String brCode) throws ApplicationException;

    List gridLoadForSingleAccount(String brCode, float lockerNo) throws ApplicationException;

    String lockerNoCheck(String brCode, float lockerNo) throws ApplicationException;

    String lockerRentPosting(List<LockerRentDetail> rentList, String lockerAcNo, String repType,
            String user, String brCode) throws ApplicationException;

    List gridLoad(boolean flag, String effDate) throws ApplicationException;

    String addDetail(boolean flag, String lockerType, String custCat, double rent, String effDt, String enterBy) throws ApplicationException;

    List rentDueDt(String brCode, String cabNo, String lockerNo, String lockerTy) throws ApplicationException;

    List gridLoad(String brCode, String cabNo, String lockerNo, String lockerTy, String acNo) throws ApplicationException;

    //String surrenderLocker(String brCode, String cabNo, String lockerNo, String lockerTy) throws ApplicationException;
    
    String surrenderLocker(String brCode, String cabNo, String lockerNo, String lockerTy, String enterBy) throws ApplicationException;
    
    String surrenderLockerAuth(String brCode, String cabNo, String lockerNo, String lockerTy, String authBy) throws ApplicationException;
    
    List getUnAuthSurrenderLocker(String brCode) throws ApplicationException;
    
    String deleteLocker(String brCode, String cabNo, String lockerNo, String lockerTy, String enterBy, String acNo) throws ApplicationException;
    
    String unAuthLockerExist(String brCode, String cabNo, String lockerNo, String lockerTy, String acNo) throws ApplicationException;

    public List<LockerRentCalTable> lockerRentCalculationReport(String postOption, float lockerNo, String brCode) throws ApplicationException;

    //String autoLockerRentPosting(String tmpBd) throws ApplicationException;
    
    List lockerTypeOnLoad(String brCode) throws ApplicationException;
    
    public List<String> getlockerNoDetail(String brCode, String cabNo, String lockerNo, String lockerTy) throws ApplicationException;
    
    public List getLockerDetail(String acno) throws ApplicationException;
    
    public List getLockerTimeDetail(String acno) throws ApplicationException;
    
    public String SaveData(String function,String acno, int lockerno, double cabno,String lockertype,double keyno,String custname,String inpresenceof,String intime,String outtime,String brncode) throws ApplicationException;
   
}
