package com.cbs.facade.master;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface GlMasterFacadeRemote {

    //GlbMasterRemote Methods
    public List glbMasterCodeCombo() throws ApplicationException;
//

    public List glbMasterAcctTypeCombo() throws ApplicationException;
//

    public List glbMasterGridDetail() throws ApplicationException;
//

    public String glbMasterDeleteRecord(String sno, String glbAcType) throws ApplicationException;

    public List glbMasterAcName(String acno) throws ApplicationException;

    public List glbMasterAcDesc(String acType) throws ApplicationException;

    public List glbMasterSubGrCodeChk(String glbAcType, int grCode, int subGrCode) throws ApplicationException;

    public String glbMasterSaveRecord(int grCode, int subGrCode, String codeAcNo, String description, String actype, String glbAcType, int acStatus, String username, String brnCode) throws ApplicationException;

    //PlMasterBean Methods
    List plMasterGrCodeLostFocus(int groupCode, String classfication) throws ApplicationException;

    List plMasterSubGrCodeLostFocus(int groupCode, int subGrCode) throws ApplicationException;

    List plMasterCodeLostFocus(String acno) throws ApplicationException;

    List plMasterGridLoad(int grCode) throws ApplicationException;

    String plMasterDeleteRecord(int sNo, String classification) throws ApplicationException;

    String plMasterSaveDetail(int groupCode, int subGroupCode, String code, String description, String classification, String lastupdatedBy, String mode) throws ApplicationException;

    //TrialBalanceMBean Methods
    List accountTypeTrialBalance() throws ApplicationException;

    List tableDataTrialBalance(int gCode) throws ApplicationException;

    String deleteDataTrialBalance(int seqNo, String type) throws ApplicationException;

    String saveDataTrialBalance(String type, int gCode, int subGroupCode, String accStatus, String tmpAcNo, String accType, String codeDesc, String user) throws ApplicationException;

    String codeDescriptionTrialBalance(String accType) throws ApplicationException;

    String codeTrialBalance(String code, int subGroupCode) throws ApplicationException;

    String groupCodeTrialBalance(String type, int gCode) throws ApplicationException;

    String subGroupCodeTrialBalance(int gCode, int subGroupCode) throws ApplicationException;

    //DayBookMasterBean Methods
    List acctTypeDayBook() throws ApplicationException;

    List grCodeLostFocusDayBook(int groupCode) throws ApplicationException;

    List subGrCodeLostFocusDayBook(int groupCode, int subGrCode) throws ApplicationException;

    List codeLostFocusDayBook(String acno) throws ApplicationException;

    List frGlHeadLostFocusDayBook(String acno) throws ApplicationException;

    List toGlHeadLostFocusDayBook(String acno) throws ApplicationException;

    List acTypeLostFocusDayBook(String acType) throws ApplicationException;

    List gridLoadDayBook(int grCode) throws ApplicationException;

    String deleteRecordDayBook(String acCode, String desc, String glHead) throws ApplicationException;

    String saveDetailDayBook(int groupCode, int subGroupCode, String code, String description, String acCode, int acStatus, String frglHead, String toGlHead, String lastupdatedBy, String codeDescTxt) throws ApplicationException;

    //hoFormMasterBean Methods
    public List hoForm9onLoad() throws ApplicationException;

    public List hoForm1Onload(String reportingFridate) throws ApplicationException;

    public List hoGetSlrFridayDate(String openingDt) throws ApplicationException;

    public List getGlCreationCode() throws ApplicationException;

    public List retrieveGroupCode(String code) throws ApplicationException;

    public List retrieveSubGroupCode(String code, String groupCode) throws ApplicationException;

    public List retrieveSSGroupCode(String code, String groupCode, String subGroupCode) throws ApplicationException;

    public String getMaxHeadNumber(String code, String groupCode, String subGroupCode, String subSubGroupCode) throws ApplicationException;

    public String createGlHead(String code, String headNumber, String headName, String headType, String tranType) throws ApplicationException;
}
