package com.cbs.facade.master;

import com.cbs.dto.AtmCardMappGrid;
import com.cbs.dto.FidilityTablePojo;
import com.cbs.exception.ApplicationException;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface GeneralMasterFacadeRemote {

    //AccountTypeMasterRemote Methods
    public List NatureAccountTypeMaster() throws ApplicationException;

    public List glValueAccountTypeMaster() throws ApplicationException;

    public String saveAccountTypeMaster(String AcctNature, String AcctCode, String acctDesc, String GLHead, String GLHeadInt, String GLHeadProv, int MinBal, float MinBalChq, String ChqSrNo, float MinInt, float StaffInt, float Penalty, float MinBalCharge, String ProductCode, String OFAcctNature, String Enterby, String GLHeaduri, String accountType) throws ApplicationException;

    public List checkAcctCodeAccountTypeMaster(String acNature, String actCode) throws ApplicationException;

    public List checkAcctCode1AccountTypeMaster(String actCode) throws ApplicationException;

    public String UpdateAccountTypeMaster(String txtDesc, String glHead, String glheadint, String GLheadProv, Integer txtMinBal, Float txtMinBalChq, String txtChqSrno, Float txtMinInt, Float txtStaffROI, Float txtpenalty, Float txtBalChg, String txtPcode, String cmbOFNat, String uname, String lastUpdateDt, String GlHeadINC, String cmbNature, String txtAcctCode) throws ApplicationException;

    public List ReportAccountTypeMaster() throws ApplicationException;

    //HolidayMarkingRegisterBean Methods
    public List tableDataHolidayMarkingRegister(String brnCode) throws ApplicationException;

    public String saveHolidayMarkingData(String date, String description, String brnCode) throws ApplicationException;

    public String deleteHolidayMarkingData(String date, String description, String flag, String brnCode) throws ApplicationException;

    //LegalDocumentMasterBean Methods
    public List accountNatureLegalDocumentMaster() throws ApplicationException;

    public List tableDetailsLegalDocumentMaster(String acType) throws ApplicationException;

    public String saveLegalDocumentData(String acType, String legalDocument, String user) throws ApplicationException;

    public String deleteLegalDocumentData(int seqNumCode, String legalDocument, String user, String acType) throws ApplicationException;

    //MiscParametersBean Methods
    public List getMiscParametersAcctCode() throws ApplicationException;

    public List getMiscGLHead() throws ApplicationException;

    public List getExistingDetailMiscParameters(String purpose, String acctCode) throws ApplicationException;

    public String saveMiscParametersData(String purpose, String accType, String glHeadMiscTmp, String Charges, String issueDate, String userName, String Charges1) throws ApplicationException;

    //PassBookParamBean Methods
    public List dataloadPassBook() throws ApplicationException;

    public List dataload2PassBook() throws ApplicationException;

    public String okButtonPassBook(int value1, int value2, int value3, int value4, String st1, String st2, String st3, String st4, String st5, String st6, int inputvalue1, int inputvalue2, int inputvalue3, int inputvalue4, int inputvalue5, int inputvalue6, int inputvalue7, int inputvalue8, int inputvalue9, int inputvalue10, int inputvalue11, int inputvalue12) throws ApplicationException;

    //ParameterInfoReportBean Methods
    public List branchCodeDropDownParameterInfoReport() throws ApplicationException;

    public List tableDataParameterInfoReport() throws ApplicationException;

    public String parameterSaveUpdation(String command, String reportName, int code) throws ApplicationException;

    //ClearingInfoBean Methods
    public List getCircleTypesClearingInfo() throws ApplicationException;

    public List loadCircleTypeClearingInfo(String value) throws ApplicationException;

    public String updateButtonClearingInfo(String circleDescription, String circleMicr, String bankMicr, String branchMicr, String owClgHead, String owClgReturnHead, String clgReturnCharge, float owReturnCharges, String iwClgHead, String iwClgReturnHead, String iwClgReturnCharge, float iwReturnCharges, String String1) throws ApplicationException;

    public String saveButtonClearingInfo(String string2, String circleDescription, String circleMicr, String bankMicr, String branchMicr, String owClgHead, String owClgReturnHead, String clgReturnCharge, float owReturnCharges, String iwClgHead, String iwClgReturnHead, String iwClgReturnCharge, float iwReturnCharges) throws ApplicationException;

    public List getGlTableValueClearingInfo(String acNo) throws ApplicationException;

    public List getPostFlagClearingInfo(String postScreen, Integer postFlag, String acNo) throws ApplicationException;

    public List getPostFlag1ClearingInfo(String postScreen, Integer postFlag, String acNo) throws ApplicationException;

    public List getPostFlag2ClearingInfo(String postScreen, Integer postFlag, String acNo) throws ApplicationException;

    public List dataPassBookDisplay() throws ApplicationException;

    public String saveUpdateChargeMasters(String command, String chargeType, String chargeName, String acctType, String fromRange, String toRange, String fixFlag, double amount, String effDt, String crglhead, String enterBy, String creationDt, String updateby, String updateDt, String userName) throws ApplicationException;

    public List tableDataChargeMasters(String chargeType) throws ApplicationException;

    public List getRefCodeAndDescByNo(String refRecNo) throws ApplicationException;
    
    public List<FidilityTablePojo> getFidilityDetailsList(String code) throws ApplicationException;
    
    public String saveFidilityData(String opt, String DesigCd, double bondAmt, double prAmt, int FYr, String CrGlHead, String DrGlHead, String effDt, String enterBy) throws ApplicationException;
    
    public List gridDetailATMCard(String opt, String acNo) throws ApplicationException;
    
    public String SaveUpdateVerifyATMCard(String opt, String acNo, String cardNo, String issDt, String minLmt,
            String status, String userName, String oldCardNo) throws ApplicationException;
    
    public List gridOfficeDeptMapp() throws ApplicationException;
     
    public String SaveUpdateVerifyOfficeDept(String opt, int offId, String offName, int deptId, String deptName, String deptHead, 
            String addr, String userName) throws ApplicationException;

    public List gridDetailForVerifyAtmCard(String regDate,String brnCode) throws ApplicationException;

    public String verifyAtmAccountDetail(AtmCardMappGrid obj, String user, String vrfyDate) throws ApplicationException;

    public List gridDetailForDeactivateCard(String brnCode, String AcNo) throws ApplicationException;

    public String deactivateAtmAccountDetail(AtmCardMappGrid obj, String user, String vrfyDate) throws ApplicationException;

    public String getdeactivatedAccount(String AcNo) throws ApplicationException;

    public List getAccountInAtmCardMaster(String Acno) throws ApplicationException;

    public List getSecondaryAccountData(String priAcno, String cardNo) throws ApplicationException;
    
    public String saveUpdateVerifyATMKitCard(String opt, String acNo, String cardNo, String issDt, String minLmt,
            String status, String userName, String kitNo, String kitIssueDt, String oldCardNo, String embosedName) throws ApplicationException;
}