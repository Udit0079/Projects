/*
 * CREATED BY   :  ROHIT KRISHNA GUPTA
 * CREATION DATE:  24 NOV 2010
 */
package com.cbs.web.controller.admin;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.web.pojo.admin.GeneratedCustIdGridForAcOpen;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public final class LoanAccountOpen extends BaseBean {

    private String errorMessage;
    private String message;
    private String custId;
    private String appSeqNo;
    private List<SelectItem> appSeqNoList;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private String schemeCode;
    private List<SelectItem> schemeCodeList;
    private String title;
    private List<SelectItem> titleList;
    private String custName;
    private String fatherHusName, acTypeDesc;
    private Date acOpenDt;
    private String perAdd;
    private String corAdd;
    private String phNo;
    private String panNo;
    private Date dob;
    private String occupation;
    private List<SelectItem> occupationList;
    private String grName;
    private String grRelationship;
    private String oprMode;
    private List<SelectItem> oprModeList;
    private String jtName1;
    private String jtName2;
    private String jtName3;
    private String jtName4;
    private String nominee;
    private String nomineeAdd;
    private Date nomineeDob;
    private String nomRelationship;
    private String moratoriumPeriod;
    private String rateCode;
    private List<SelectItem> rateCodeList;
    private String paggingFrequence;
    private String introAcType;
    private List<SelectItem> introAcTypeList;
    private String introAcNo;
    private String oldintroAcNo;
    private String introAcNoAgCode = "01";
    private String introducerName;
    private String introducerAcNo;
    private String introducerAcStatus;
    private String docName;
    private List<SelectItem> docNameList;
    private String docDetail;
    private String specialInst;
    private String disburseType;
    private List<SelectItem> disburseTypeList;
    private String calMethod;
    private List<SelectItem> calMethodList;
    private String interestAppFreq1;
    private List<SelectItem> interestAppFreqList1;
    private String interestAppFreq2;
    private List<SelectItem> interestAppFreqList2;
    private String calLevel;
    private List<SelectItem> calLevelList;
    private String limit;
    private String loanPeriodDd;
    private String loanPeriodMm;
    private String intCode;
    private List<SelectItem> intCodeList;
    private String roi;
    private String acPreferCrDr1;
    private String acPreferCrDr2;
    private Date sancDate;
    private String intOpt;
    private List<SelectItem> intOptList;
    private String subsidyAmt;
    private String calOn;
    private List<SelectItem> calOnList;
    private HttpServletRequest req;
    private String paramCode;
    private String acNature;
    private String jtLabel1;
    private String jtLabel2;
    private String jtLabel3;
    private String jtLabel4;
    private String amtSancLable;
    private boolean fieldDisFlag;
    private boolean fieldCalLevelDisFlag;
    private boolean flag1;
    private boolean flag2;
    private String introCustId;
    private String minorFlag;
    private String jtName1CustId;
    private String jtName2CustId;
    private String jtName3CustId;
    private String jtName4CustId;
    private boolean titleFlag;
    private boolean custNameFlag;
    private boolean fatherNameFlag;
    private boolean perAddFlag;
    private boolean corAddFlag;
    private boolean phoneFlag;
    private boolean panFlag;
    private boolean dobFlag;
    private boolean introCustIdFlag;
    private boolean sancDtDisFlag;
    private boolean roiDisFlag;
    private boolean intAppFrqDisFlag;
    private boolean jtName1DisFlag;
    private boolean jtName2DisFlag;
    private boolean jtName3DisFlag;
    private boolean jtName4DisFlag;
    private boolean nomDisFlag;
    private boolean pagFreqDisFlag;
    private boolean disableOnSchemeCode;
    private String fYear;
    private String loanApp;
    private String applicableRoi;
    private String actCategory;
    private List<SelectItem> actCategoryList;
    private List<GeneratedCustIdGridForAcOpen> gridDetail;
    AccountOpeningFacadeRemote acOpenFacadeLocal;
    CommonReportMethodsRemote reportMethodsLocal;
    AdvancesInformationTrackingRemote aitr;
    private String hufFamily;
    private boolean hufFlag;
    private String occupationDesc, acNoLen;
    private String OdAllowWithSlab;
    private String drCrIndFlag;
    private boolean txtAcPrefCrDisFlag;            
    private boolean txtAcPrefDrDisFlag;
    private String chqFacility;
    private List<SelectItem> chqFacilityList;

    public boolean isTxtAcPrefCrDisFlag() {
        return txtAcPrefCrDisFlag;
    }

    public void setTxtAcPrefCrDisFlag(boolean txtAcPrefCrDisFlag) {
        this.txtAcPrefCrDisFlag = txtAcPrefCrDisFlag;
    }

    public boolean isTxtAcPrefDrDisFlag() {
        return txtAcPrefDrDisFlag;
    }

    public void setTxtAcPrefDrDisFlag(boolean txtAcPrefDrDisFlag) {
        this.txtAcPrefDrDisFlag = txtAcPrefDrDisFlag;
    }    
    
    public String getAcTypeDesc() {
        return acTypeDesc;
    }

    public void setAcTypeDesc(String acTypeDesc) {
        this.acTypeDesc = acTypeDesc;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Date getAcOpenDt() {
        return acOpenDt;
    }

    public void setAcOpenDt(Date acOpenDt) {
        this.acOpenDt = acOpenDt;
    }

    public String getAcPreferCrDr1() {
        return acPreferCrDr1;
    }

    public void setAcPreferCrDr1(String acPreferCrDr1) {
        this.acPreferCrDr1 = acPreferCrDr1;
    }

    public String getAcPreferCrDr2() {
        return acPreferCrDr2;
    }

    public void setAcPreferCrDr2(String acPreferCrDr2) {
        this.acPreferCrDr2 = acPreferCrDr2;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public String getAppSeqNo() {
        return appSeqNo;
    }

    public void setAppSeqNo(String appSeqNo) {
        this.appSeqNo = appSeqNo;
    }

    public List<SelectItem> getAppSeqNoList() {
        return appSeqNoList;
    }

    public void setAppSeqNoList(List<SelectItem> appSeqNoList) {
        this.appSeqNoList = appSeqNoList;
    }

    public String getCalLevel() {
        return calLevel;
    }

    public void setCalLevel(String calLevel) {
        this.calLevel = calLevel;
    }

    public List<SelectItem> getCalLevelList() {
        return calLevelList;
    }

    public void setCalLevelList(List<SelectItem> calLevelList) {
        this.calLevelList = calLevelList;
    }

    public String getCalMethod() {
        return calMethod;
    }

    public void setCalMethod(String calMethod) {
        this.calMethod = calMethod;
    }

    public String getCalOn() {
        return calOn;
    }

    public void setCalOn(String calOn) {
        this.calOn = calOn;
    }

    public List<SelectItem> getCalOnList() {
        return calOnList;
    }

    public void setCalOnList(List<SelectItem> calOnList) {
        this.calOnList = calOnList;
    }

    public String getCorAdd() {
        return corAdd;
    }

    public void setCorAdd(String corAdd) {
        this.corAdd = corAdd;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDisburseType() {
        return disburseType;
    }

    public void setDisburseType(String disburseType) {
        this.disburseType = disburseType;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getDocDetail() {
        return docDetail;
    }

    public void setDocDetail(String docDetail) {
        this.docDetail = docDetail;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public List<SelectItem> getDocNameList() {
        return docNameList;
    }

    public void setDocNameList(List<SelectItem> docNameList) {
        this.docNameList = docNameList;
    }

    public String getFatherHusName() {
        return fatherHusName;
    }

    public void setFatherHusName(String fatherHusName) {
        this.fatherHusName = fatherHusName;
    }

    public String getGrName() {
        return grName;
    }

    public void setGrName(String grName) {
        this.grName = grName;
    }

    public String getGrRelationship() {
        return grRelationship;
    }

    public void setGrRelationship(String grRelationship) {
        this.grRelationship = grRelationship;
    }

    public String getIntCode() {
        return intCode;
    }

    public void setIntCode(String intCode) {
        this.intCode = intCode;
    }

    public List<SelectItem> getIntCodeList() {
        return intCodeList;
    }

    public void setIntCodeList(List<SelectItem> intCodeList) {
        this.intCodeList = intCodeList;
    }

    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    public List<SelectItem> getIntOptList() {
        return intOptList;
    }

    public void setIntOptList(List<SelectItem> intOptList) {
        this.intOptList = intOptList;
    }

    public String getInterestAppFreq1() {
        return interestAppFreq1;
    }

    public void setInterestAppFreq1(String interestAppFreq1) {
        this.interestAppFreq1 = interestAppFreq1;
    }

    public String getInterestAppFreq2() {
        return interestAppFreq2;
    }

    public void setInterestAppFreq2(String interestAppFreq2) {
        this.interestAppFreq2 = interestAppFreq2;
    }

    public List<SelectItem> getInterestAppFreqList1() {
        return interestAppFreqList1;
    }

    public void setInterestAppFreqList1(List<SelectItem> interestAppFreqList1) {
        this.interestAppFreqList1 = interestAppFreqList1;
    }

    public List<SelectItem> getInterestAppFreqList2() {
        return interestAppFreqList2;
    }

    public void setInterestAppFreqList2(List<SelectItem> interestAppFreqList2) {
        this.interestAppFreqList2 = interestAppFreqList2;
    }

    public String getIntroAcNo() {
        return introAcNo;
    }

    public void setIntroAcNo(String introAcNo) {
        this.introAcNo = introAcNo;
    }

    public String getOldintroAcNo() {
        return oldintroAcNo;
    }

    public void setOldintroAcNo(String oldintroAcNo) {
        this.oldintroAcNo = oldintroAcNo;
    }

    public String getIntroAcNoAgCode() {
        return introAcNoAgCode;
    }

    public void setIntroAcNoAgCode(String introAcNoAgCode) {
        this.introAcNoAgCode = introAcNoAgCode;
    }

    public String getIntroAcType() {
        return introAcType;
    }

    public void setIntroAcType(String introAcType) {
        this.introAcType = introAcType;
    }

    public String getIntroducerAcNo() {
        return introducerAcNo;
    }

    public void setIntroducerAcNo(String introducerAcNo) {
        this.introducerAcNo = introducerAcNo;
    }

    public String getIntroducerAcStatus() {
        return introducerAcStatus;
    }

    public void setIntroducerAcStatus(String introducerAcStatus) {
        this.introducerAcStatus = introducerAcStatus;
    }

    public String getIntroducerName() {
        return introducerName;
    }

    public void setIntroducerName(String introducerName) {
        this.introducerName = introducerName;
    }

    public String getJtName1() {
        return jtName1;
    }

    public void setJtName1(String jtName1) {
        this.jtName1 = jtName1;
    }

    public String getJtName2() {
        return jtName2;
    }

    public void setJtName2(String jtName2) {
        this.jtName2 = jtName2;
    }

    public String getJtName3() {
        return jtName3;
    }

    public void setJtName3(String jtName3) {
        this.jtName3 = jtName3;
    }

    public String getJtName4() {
        return jtName4;
    }

    public void setJtName4(String jtName4) {
        this.jtName4 = jtName4;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getLoanPeriodDd() {
        return loanPeriodDd;
    }

    public void setLoanPeriodDd(String loanPeriodDd) {
        this.loanPeriodDd = loanPeriodDd;
    }

    public String getLoanPeriodMm() {
        return loanPeriodMm;
    }

    public void setLoanPeriodMm(String loanPeriodMm) {
        this.loanPeriodMm = loanPeriodMm;
    }

    public String getMoratoriumPeriod() {
        return moratoriumPeriod;
    }

    public void setMoratoriumPeriod(String moratoriumPeriod) {
        this.moratoriumPeriod = moratoriumPeriod;
    }

    public String getNomRelationship() {
        return nomRelationship;
    }

    public void setNomRelationship(String nomRelationship) {
        this.nomRelationship = nomRelationship;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public List<SelectItem> getOccupationList() {
        return occupationList;
    }

    public void setOccupationList(List<SelectItem> occupationList) {
        this.occupationList = occupationList;
    }

    public String getOprMode() {
        return oprMode;
    }

    public void setOprMode(String oprMode) {
        this.oprMode = oprMode;
    }

    public List<SelectItem> getOprModeList() {
        return oprModeList;
    }

    public void setOprModeList(List<SelectItem> oprModeList) {
        this.oprModeList = oprModeList;
    }

    public String getPaggingFrequence() {
        return paggingFrequence;
    }

    public void setPaggingFrequence(String paggingFrequence) {
        this.paggingFrequence = paggingFrequence;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getPerAdd() {
        return perAdd;
    }

    public void setPerAdd(String perAdd) {
        this.perAdd = perAdd;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public List<SelectItem> getRateCodeList() {
        return rateCodeList;
    }

    public void setRateCodeList(List<SelectItem> rateCodeList) {
        this.rateCodeList = rateCodeList;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public Date getSancDate() {
        return sancDate;
    }

    public void setSancDate(Date sancDate) {
        this.sancDate = sancDate;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public List<SelectItem> getSchemeCodeList() {
        return schemeCodeList;
    }

    public void setSchemeCodeList(List<SelectItem> schemeCodeList) {
        this.schemeCodeList = schemeCodeList;
    }

    public String getSpecialInst() {
        return specialInst;
    }

    public void setSpecialInst(String specialInst) {
        this.specialInst = specialInst;
    }

    public String getSubsidyAmt() {
        return subsidyAmt;
    }

    public void setSubsidyAmt(String subsidyAmt) {
        this.subsidyAmt = subsidyAmt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SelectItem> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<SelectItem> titleList) {
        this.titleList = titleList;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public List<SelectItem> getCalMethodList() {
        return calMethodList;
    }

    public void setCalMethodList(List<SelectItem> calMethodList) {
        this.calMethodList = calMethodList;
    }

    public List<SelectItem> getDisburseTypeList() {
        return disburseTypeList;
    }

    public void setDisburseTypeList(List<SelectItem> disburseTypeList) {
        this.disburseTypeList = disburseTypeList;
    }

    public List<SelectItem> getIntroAcTypeList() {
        return introAcTypeList;
    }

    public void setIntroAcTypeList(List<SelectItem> introAcTypeList) {
        this.introAcTypeList = introAcTypeList;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getJtLabel1() {
        return jtLabel1;
    }

    public void setJtLabel1(String jtLabel1) {
        this.jtLabel1 = jtLabel1;
    }

    public String getJtLabel2() {
        return jtLabel2;
    }

    public void setJtLabel2(String jtLabel2) {
        this.jtLabel2 = jtLabel2;
    }

    public String getJtLabel3() {
        return jtLabel3;
    }

    public void setJtLabel3(String jtLabel3) {
        this.jtLabel3 = jtLabel3;
    }

    public String getJtLabel4() {
        return jtLabel4;
    }

    public void setJtLabel4(String jtLabel4) {
        this.jtLabel4 = jtLabel4;
    }

    public String getNomineeAdd() {
        return nomineeAdd;
    }

    public void setNomineeAdd(String nomineeAdd) {
        this.nomineeAdd = nomineeAdd;
    }

    public Date getNomineeDob() {
        return nomineeDob;
    }

    public void setNomineeDob(Date nomineeDob) {
        this.nomineeDob = nomineeDob;
    }

    public String getAmtSancLable() {
        return amtSancLable;
    }

    public void setAmtSancLable(String amtSancLable) {
        this.amtSancLable = amtSancLable;
    }

    public boolean isFieldDisFlag() {
        return fieldDisFlag;
    }

    public void setFieldDisFlag(boolean fieldDisFlag) {
        this.fieldDisFlag = fieldDisFlag;
    }

    public boolean isFieldCalLevelDisFlag() {
        return fieldCalLevelDisFlag;
    }

    public void setFieldCalLevelDisFlag(boolean fieldCalLevelDisFlag) {
        this.fieldCalLevelDisFlag = fieldCalLevelDisFlag;
    }

    public String getIntroCustId() {
        return introCustId;
    }

    public void setIntroCustId(String introCustId) {
        this.introCustId = introCustId;
    }

    public String getMinorFlag() {
        return minorFlag;
    }

    public void setMinorFlag(String minorFlag) {
        this.minorFlag = minorFlag;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public String getJtName1CustId() {
        return jtName1CustId;
    }

    public void setJtName1CustId(String jtName1CustId) {
        this.jtName1CustId = jtName1CustId;
    }

    public String getJtName2CustId() {
        return jtName2CustId;
    }

    public void setJtName2CustId(String jtName2CustId) {
        this.jtName2CustId = jtName2CustId;
    }

    public String getJtName3CustId() {
        return jtName3CustId;
    }

    public void setJtName3CustId(String jtName3CustId) {
        this.jtName3CustId = jtName3CustId;
    }

    public String getJtName4CustId() {
        return jtName4CustId;
    }

    public void setJtName4CustId(String jtName4CustId) {
        this.jtName4CustId = jtName4CustId;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public boolean isCorAddFlag() {
        return corAddFlag;
    }

    public void setCorAddFlag(boolean corAddFlag) {
        this.corAddFlag = corAddFlag;
    }

    public boolean isCustNameFlag() {
        return custNameFlag;
    }

    public void setCustNameFlag(boolean custNameFlag) {
        this.custNameFlag = custNameFlag;
    }

    public boolean isDobFlag() {
        return dobFlag;
    }

    public void setDobFlag(boolean dobFlag) {
        this.dobFlag = dobFlag;
    }

    public boolean isFatherNameFlag() {
        return fatherNameFlag;
    }

    public void setFatherNameFlag(boolean fatherNameFlag) {
        this.fatherNameFlag = fatherNameFlag;
    }

    public boolean isPanFlag() {
        return panFlag;
    }

    public void setPanFlag(boolean panFlag) {
        this.panFlag = panFlag;
    }

    public boolean isPerAddFlag() {
        return perAddFlag;
    }

    public void setPerAddFlag(boolean perAddFlag) {
        this.perAddFlag = perAddFlag;
    }

    public boolean isPhoneFlag() {
        return phoneFlag;
    }

    public void setPhoneFlag(boolean phoneFlag) {
        this.phoneFlag = phoneFlag;
    }

    public boolean isTitleFlag() {
        return titleFlag;
    }

    public void setTitleFlag(boolean titleFlag) {
        this.titleFlag = titleFlag;
    }

    public boolean isIntroCustIdFlag() {
        return introCustIdFlag;
    }

    public void setIntroCustIdFlag(boolean introCustIdFlag) {
        this.introCustIdFlag = introCustIdFlag;
    }

    public String getfYear() {
        return fYear;
    }

    public void setfYear(String fYear) {
        this.fYear = fYear;
    }

    public boolean isSancDtDisFlag() {
        return sancDtDisFlag;
    }

    public void setSancDtDisFlag(boolean sancDtDisFlag) {
        this.sancDtDisFlag = sancDtDisFlag;
    }

    public boolean isRoiDisFlag() {
        return roiDisFlag;
    }

    public void setRoiDisFlag(boolean roiDisFlag) {
        this.roiDisFlag = roiDisFlag;
    }

    public boolean isIntAppFrqDisFlag() {
        return intAppFrqDisFlag;
    }

    public void setIntAppFrqDisFlag(boolean intAppFrqDisFlag) {
        this.intAppFrqDisFlag = intAppFrqDisFlag;
    }

    public boolean isJtName1DisFlag() {
        return jtName1DisFlag;
    }

    public void setJtName1DisFlag(boolean jtName1DisFlag) {
        this.jtName1DisFlag = jtName1DisFlag;
    }

    public boolean isJtName2DisFlag() {
        return jtName2DisFlag;
    }

    public void setJtName2DisFlag(boolean jtName2DisFlag) {
        this.jtName2DisFlag = jtName2DisFlag;
    }

    public boolean isJtName3DisFlag() {
        return jtName3DisFlag;
    }

    public void setJtName3DisFlag(boolean jtName3DisFlag) {
        this.jtName3DisFlag = jtName3DisFlag;
    }

    public boolean isJtName4DisFlag() {
        return jtName4DisFlag;
    }

    public void setJtName4DisFlag(boolean jtName4DisFlag) {
        this.jtName4DisFlag = jtName4DisFlag;
    }

    public boolean isNomDisFlag() {
        return nomDisFlag;
    }

    public void setNomDisFlag(boolean nomDisFlag) {
        this.nomDisFlag = nomDisFlag;
    }

    public boolean isPagFreqDisFlag() {
        return pagFreqDisFlag;
    }

    public void setPagFreqDisFlag(boolean pagFreqDisFlag) {
        this.pagFreqDisFlag = pagFreqDisFlag;
    }

    public String getLoanApp() {
        return loanApp;
    }

    public void setLoanApp(String loanApp) {
        this.loanApp = loanApp;
    }

    public boolean isDisableOnSchemeCode() {
        return disableOnSchemeCode;
    }

    public void setDisableOnSchemeCode(boolean disableOnSchemeCode) {
        this.disableOnSchemeCode = disableOnSchemeCode;
    }

    public List<GeneratedCustIdGridForAcOpen> getGridDetail() {
        return gridDetail;
    }

    public String getApplicableRoi() {
        return applicableRoi;
    }

    public void setApplicableRoi(String applicableRoi) {
        this.applicableRoi = applicableRoi;
    }

    public void setGridDetail(List<GeneratedCustIdGridForAcOpen> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getActCategory() {
        return actCategory;
    }

    public void setActCategory(String actCategory) {
        this.actCategory = actCategory;
    }

    public List<SelectItem> getActCategoryList() {
        return actCategoryList;
    }

    public void setActCategoryList(List<SelectItem> actCategoryList) {
        this.actCategoryList = actCategoryList;
    }

    public String getHufFamily() {
        return hufFamily;
    }

    public void setHufFamily(String hufFamily) {
        this.hufFamily = hufFamily;
    }

    public boolean isHufFlag() {
        return hufFlag;
    }

    public void setHufFlag(boolean hufFlag) {
        this.hufFlag = hufFlag;
    }
    
    //----Added by Manish Kumar
    public String getOccupationDesc() {
        return occupationDesc;
    }

    public void setOccupationDesc(String occupationDesc) {
        this.occupationDesc = occupationDesc;
    }

    //---------
    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
    Date date = new Date();

    public String getChqFacility() {
        return chqFacility;
    }

    public void setChqFacility(String chqFacility) {
        this.chqFacility = chqFacility;
    }

    public List<SelectItem> getChqFacilityList() {
        return chqFacilityList;
    }

    public void setChqFacilityList(List<SelectItem> chqFacilityList) {
        this.chqFacilityList = chqFacilityList;
    }
    
    /**
     * Creates a new instance of LoanAccountOpen
     */
    public LoanAccountOpen() {
        try {
            acOpenFacadeLocal = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            reportMethodsLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            this.setAcNoLen(getAcNoLength());
            this.setDob(date);
            this.setAcOpenDt(date);
            this.setErrorMessage("");
            this.setMessage("");
            this.setNomineeDob(date);

            titleList = new ArrayList<SelectItem>();
            titleList.add(new SelectItem("--Select--"));
            titleList.add(new SelectItem("Mr."));
            titleList.add(new SelectItem("Mrs."));
            titleList.add(new SelectItem("Miss"));
            titleList.add(new SelectItem("Kumari"));
            titleList.add(new SelectItem("Master"));
            titleList.add(new SelectItem("Smt."));
            titleList.add(new SelectItem("M/S"));
            titleList.add(new SelectItem("Shri"));

            calOnList = new ArrayList<SelectItem>();
            calOnList.add(new SelectItem("--Select--"));
            calOnList.add(new SelectItem("MIN", "MINIMUM"));
            calOnList.add(new SelectItem("MAX", "MAXIMUM"));
            calOnList.add(new SelectItem("AVG", "AVERAGE"));
            calOnList.add(new SelectItem("END", "END"));

            intOptList = new ArrayList<SelectItem>();
            intOptList.add(new SelectItem("C", "CUMULATIVE"));

            rateCodeList = new ArrayList<SelectItem>();
//            rateCodeList.add(new SelectItem("--Select--"));
//            rateCodeList.add(new SelectItem("Fl", "FLOATING"));
//            rateCodeList.add(new SelectItem("Fi", "FIXED"));
            List resultList1 = reportMethodsLocal.getRefRecList("202");
            if (!resultList1.isEmpty()) {
                for (int i = 0; i < resultList1.size(); i++) {
                    Vector ele1 = (Vector) resultList1.get(i);
                    rateCodeList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }

            calLevelList = new ArrayList<SelectItem>();
            calLevelList.add(new SelectItem("--Select--"));
            calLevelList.add(new SelectItem("L", "LIMIT/OUTSTANDING"));
            calLevelList.add(new SelectItem("S", "SANCTION LEVEL"));

            interestAppFreqList1 = new ArrayList<SelectItem>();
            interestAppFreqList1.add(new SelectItem("--Select--"));
            interestAppFreqList1.add(new SelectItem("S", "SIMPLE"));
            interestAppFreqList1.add(new SelectItem("C", "COMPOUND"));

            interestAppFreqList2 = new ArrayList<SelectItem>();
            interestAppFreqList2.add(new SelectItem(" "));
            interestAppFreqList2.add(new SelectItem("M", "MONTHLY"));
            interestAppFreqList2.add(new SelectItem("Q", "QUARTERLY"));
            interestAppFreqList2.add(new SelectItem("H", "HALF YEARLY"));
            interestAppFreqList2.add(new SelectItem("Y", "YEARLY"));

            calMethodList = new ArrayList<SelectItem>();
            calMethodList.add(new SelectItem("--Select--"));
            calMethodList.add(new SelectItem("D", "DAILY"));
            calMethodList.add(new SelectItem("M", "MONTHLY"));
            calMethodList.add(new SelectItem("Q", "QUARTERLY"));
            calMethodList.add(new SelectItem("H", "HALF YEARLY"));
            calMethodList.add(new SelectItem("Y", "YEARLY"));

            disburseTypeList = new ArrayList<SelectItem>();
            disburseTypeList.add(new SelectItem("--Select--"));
            disburseTypeList.add(new SelectItem("S", "SINGLE"));
            disburseTypeList.add(new SelectItem("M", "MULTIPLE"));

            schemeCodeList = new ArrayList<SelectItem>();
            schemeCodeList.add(new SelectItem("--Select--"));

            appSeqNoList = new ArrayList<SelectItem>();
            appSeqNoList.add(new SelectItem("--Select--"));

            this.setJtLabel1("Jt Name 1 :");
            this.setJtLabel2("Jt Name 2 :");
            this.setJtLabel3("Jt Name 3 :");
            this.setJtLabel4("Jt Name 4 :");

            this.setAmtSancLable("Amount Sanctioned :");
            this.setFieldDisFlag(true);
            this.setFieldCalLevelDisFlag(true);
            this.setFlag1(true);
            this.setFlag2(true);

            this.setTitleFlag(true);
            this.setCustNameFlag(true);
            this.setFatherNameFlag(true);
            this.setCorAddFlag(true);

            this.setPerAddFlag(true);
            this.setPhoneFlag(true);
            this.setPanFlag(true);
            this.setDobFlag(true);

            this.setIntroCustIdFlag(true);
            this.setSancDtDisFlag(true);
            this.setRoiDisFlag(true);
            this.setTxtAcPrefCrDisFlag(true);
            this.setTxtAcPrefDrDisFlag(true);
            this.setIntAppFrqDisFlag(true);
            this.OdAllowWithSlab = "N";
            this.drCrIndFlag = "Y";

            this.setJtName1DisFlag(true);
            this.setJtName2DisFlag(true);
            this.setJtName3DisFlag(true);
            this.setJtName4DisFlag(true);

            this.setNomDisFlag(true);
            this.setPagFreqDisFlag(true);
            this.setDisableOnSchemeCode(true);
            this.setLoanApp("false");
            this.setHufFlag(true);
            
            onloadDropDowns();
        } catch (ApplicationException e) {

            setErrorMessage(e.getMessage());
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void getAccountTypeDesc() {
        setAcTypeDesc("");
        try {
            List acTypeDescription = acOpenFacadeLocal.getAcTypeDescription(this.acctType);
            if (!acTypeDescription.isEmpty()) {
                Vector vect = (Vector)acTypeDescription.get(0);
                setAcTypeDesc("["+vect.get(0).toString()+"]");
            }
            chqFacilityList = new ArrayList<SelectItem>();
            if(acOpenFacadeLocal.getChqFacilityTrue(this.acctType).equalsIgnoreCase("true")){
                chqFacilityList.add(new SelectItem("0", "No"));
                chqFacilityList.add(new SelectItem("1", "Yes"));
            }else{
                chqFacilityList.add(new SelectItem("0", "No"));
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void setScheme() {
        try {
            List resultList7 = acOpenFacadeLocal.schemeCodeCombo(acctType);
            schemeCodeList = new ArrayList<SelectItem>();
            schemeCodeList.add(new SelectItem("--Select--"));
            if (!resultList7.isEmpty()) {
                for (int i = 0; i < resultList7.size(); i++) {
                    Vector ele7 = (Vector) resultList7.get(i);
                    schemeCodeList.add(new SelectItem(ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void onloadDropDowns() {
        try {
            List resultList1 = new ArrayList();
            List resultList2 = new ArrayList();
            List resultList3 = new ArrayList();
            List resultList4 = new ArrayList();
            List resultList5 = new ArrayList();
            List resultList6 = new ArrayList();
            List resultList8 = new ArrayList();
            List resultList9 = new ArrayList();
            resultList1 = acOpenFacadeLocal.acctTypeCombo();
            resultList2 = acOpenFacadeLocal.IntroducerAcctTypeCombo();
            resultList3 = reportMethodsLocal.getOcupationDetails();
            resultList4 = reportMethodsLocal.getOperatingModeDetails();
            resultList5 = reportMethodsLocal.getDocumentDetails();
            resultList6 = acOpenFacadeLocal.interestCodeCombo();
            resultList8 = acOpenFacadeLocal.parameterCode();
            resultList9 = reportMethodsLocal.getActCategoryDetails();

            acctTypeList = new ArrayList<SelectItem>();
            acctTypeList.add(new SelectItem("--Select--"));

            introAcTypeList = new ArrayList<SelectItem>();
            introAcTypeList.add(new SelectItem("--Select--"));

            oprModeList = new ArrayList<SelectItem>();
            oprModeList.add(new SelectItem("--Select--"));

            occupationList = new ArrayList<SelectItem>();
            occupationList.add(new SelectItem("--Select--"));
            
            actCategoryList = new ArrayList<SelectItem>();
            actCategoryList.add(new SelectItem("--Select--"));

            docNameList = new ArrayList<SelectItem>();
            docNameList.add(new SelectItem("--Select--"));



            if (!resultList1.isEmpty()) {
                for (int i = 0; i < resultList1.size(); i++) {
                    Vector ele1 = (Vector) resultList1.get(i);
                    acctTypeList.add(new SelectItem(ele1.get(0).toString(),"[" + ele1.get(0).toString() + "] "+ ele1.get(1).toString()));
                }
            }
            if (!resultList2.isEmpty()) {
                for (int i = 0; i < resultList2.size(); i++) {
                    Vector ele2 = (Vector) resultList2.get(i);
                    introAcTypeList.add(new SelectItem(ele2.get(0).toString()));
                }
            }
            if (!resultList3.isEmpty()) {
                for (int i = 0; i < resultList3.size(); i++) {
                    Vector ele3 = (Vector) resultList3.get(i);
                    occupationList.add(new SelectItem(ele3.get(0).toString(), ele3.get(1).toString()));
                }
            }
            if (!resultList4.isEmpty()) {
                for (int i = 0; i < resultList4.size(); i++) {
                    Vector ele4 = (Vector) resultList4.get(i);
                    oprModeList.add(new SelectItem(ele4.get(0).toString(), ele4.get(1).toString()));
                }
            }
            if (!resultList5.isEmpty()) {
                for (int i = 0; i < resultList5.size(); i++) {
                    Vector ele5 = (Vector) resultList5.get(i);
                    docNameList.add(new SelectItem(ele5.get(0).toString(), ele5.get(1).toString()));
                }
            }
//            if (!resultList6.isEmpty()) {
//            }

            if (!resultList8.isEmpty()) {
                for (int i = 0; i < resultList8.size(); i++) {
                    Vector ele8 = (Vector) resultList8.get(i);
                    this.setParamCode(ele8.get(0).toString());
                }
            }
            
            if (!resultList9.isEmpty()) {
                for (int i = 0; i < resultList9.size(); i++) {
                    Vector ele3 = (Vector) resultList9.get(i);
                    actCategoryList.add(new SelectItem(ele3.get(0).toString(), ele3.get(1).toString()));
                }
            }

        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void acTypeLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        setScheme();
        if (this.acctType.equalsIgnoreCase("--Select--")) {
            this.setErrorMessage("Please Select Account Type.");
            return;
        }
        try {
            getAccountTypeDesc();
            List resultList = new ArrayList();
            List resultList2 = new ArrayList();
            if (this.paramCode.equalsIgnoreCase("0") || this.paramCode == null) {
                this.setLoanApp("false");
            } else {
                resultList = acOpenFacadeLocal.appSeqNoCombo(this.acctType);
                this.setLoanApp("true");
            }
            String acNature = reportMethodsLocal.getAcNatureByAcType(this.acctType);
            appSeqNoList = new ArrayList<SelectItem>();
            appSeqNoList.add(new SelectItem("--Select--"));
            if (resultList.isEmpty()) {
                appSeqNoList = new ArrayList<SelectItem>();
                appSeqNoList.add(new SelectItem("--Select--"));
                this.setFlag1(true);
            } else {
                this.setFlag1(false);
                appSeqNoList = new ArrayList<SelectItem>();
                appSeqNoList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    appSeqNoList.add(new SelectItem(ele.get(0).toString()));
                    this.setfYear(ele.get(1).toString());
                }
            }
            if (acNature != null) {
                this.setAcNature(acNature);
            } else {
                this.setErrorMessage("ACCOUNT NATURE NOT FOUND !!!");
                return;
            }
//            if ((!this.acctType.equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) && (this.acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || this.acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || this.acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
//                resultList2 = acOpenFacadeLocal.acctTypeComboLostFocus(this.acctType);
//                if (resultList2.isEmpty()) {
//                    this.setErrorMessage("Please Check for this Account Type in Loan Defaults.");
//                }
//            }


            if (this.acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                this.setJtLabel1("Propritor Name :");
                this.setJtLabel2("Name 2 :");
                this.setJtLabel3("Name 3 :");
                this.setJtLabel4("Name 4 :");
                this.setAmtSancLable("Limit :");
                this.setSancDate(date);
                this.setMoratoriumPeriod("0");
                this.setLoanPeriodMm("0");
                this.setLoanPeriodDd("0");
                this.setAcPreferCrDr1("0");
                this.setAcPreferCrDr2("0");
            } else {
                this.setJtLabel1("Jt Name 1 :");
                this.setJtLabel2("Jt Name 2 :");
                this.setJtLabel3("Jt Name 3 :");
                this.setJtLabel4("Jt Name 4 :");
                this.setAmtSancLable("Amount Sanctioned :");
                this.setSancDate(date);
                this.setMoratoriumPeriod("0");
                this.setLoanPeriodMm("0");
                this.setLoanPeriodDd("0");
                this.setAcPreferCrDr1("0");
                this.setAcPreferCrDr2("0");
            }
            if (this.acctType.equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                this.setSancDtDisFlag(true);
                this.setSancDate(date);
            } else {
                this.setSancDtDisFlag(false);
                this.setSancDate(null);
            }
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void appSeqNoLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.acctType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select A/C. Type.");
                return;
            }
            if (this.appSeqNo.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Application Sequence No.");
                return;
            }
            if (this.fYear == null || this.fYear.length() == 0 || this.fYear.equalsIgnoreCase("")) {
                this.setErrorMessage("FYear Cannot Be Null.Select Another A/C. Type And Application Sequence No.");
                return;
            }
            List resultList = new ArrayList();
            resultList = acOpenFacadeLocal.appSeqNoComboLostFocus(this.acctType, this.appSeqNo, this.fYear);
            if (resultList.isEmpty()) {
                this.setErrorMessage("NO RECORD FOUND FOR THIS APPLICATION NO.");
                return;
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    this.setCustName(ele.get(0).toString());
                    this.setLimit(ele.get(1).toString());
                    this.setSancDate(sdf.parse(ele.get(2).toString()));
                }
            }
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void corrAddressLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.perAdd == null || this.perAdd.equalsIgnoreCase("") || this.perAdd.length() == 0) {
                this.setErrorMessage("Please Enter Permanent Address.");
                return;
            }
            if ((this.perAdd != null) || (!this.perAdd.equalsIgnoreCase("")) || (this.perAdd.length() != 0)) {
                if (this.corAdd == null || this.corAdd.equalsIgnoreCase("") || this.corAdd.length() == 0) {
                    this.setCorAdd(this.perAdd);
                    return;
                }
            }
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void customerDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setTitle("");
        this.setCustName("");
        this.setFatherHusName("");
        this.setPerAdd("");
        this.setCorAdd("");
        this.setPhNo("");
        this.setPanNo("");
        this.setDob(date);
        this.setIntroCustId("");
        this.setMinorFlag("");
        clearFields();
        try {
            List resultList = new ArrayList();
            if (this.custId == null || this.custId.equals("")) {
                return;
            }
            resultList = acOpenFacadeLocal.customerDetail(this.custId);
            if (resultList.isEmpty()) {
                this.setErrorMessage("This Customer Id has been suspended or does not exists or unauthorized");
                this.setCustId("");
                this.setFieldDisFlag(true);
                this.setFieldCalLevelDisFlag(true);
                return;
            } else {
                this.setFieldDisFlag(false);
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0).toString().equalsIgnoreCase("") || ele.get(0).toString().length() == 0 || ele.get(0).toString() == null) {
                        this.setTitleFlag(false);
                    } else {
                        this.setTitleFlag(true);
                        this.setTitle(ele.get(0).toString());
                    }
                    if (ele.get(1).toString().equalsIgnoreCase("") || ele.get(1).toString().length() == 0 || ele.get(1).toString() == null) {
                        this.setCustNameFlag(false);
                    } else {
                        this.setCustNameFlag(true);
//                        this.setCustName(ele.get(1).toString());
                        String custName = ele.get(1).toString().trim() + " " + ele.get(12).toString().trim();
                        custName = custName.trim() + " " + ele.get(13).toString().trim();
                        this.setCustName(custName.trim());
                    }
                    if (ele.get(2).toString().equalsIgnoreCase("") || ele.get(2).toString().length() == 0 || ele.get(2).toString() == null) {
                        this.setFatherNameFlag(true);
                    } else {
                        this.setFatherNameFlag(true);
                        //this.setFatherHusName(ele.get(2).toString()+ " "+ele.get(14).toString().trim()+" "+ele.get(15).toString().trim());
                        String fatherHusName =  ele.get(2).toString()+ " "+ele.get(14).toString().trim();
                        fatherHusName = fatherHusName.trim()+ " "+ele.get(15).toString().trim();
                        fatherHusName = fatherHusName.trim();
                        this.setFatherHusName(fatherHusName);
                    }
                    if ((ele.get(3).toString().equalsIgnoreCase("") || ele.get(3).toString().length() == 0 || ele.get(3).toString() == null) && (ele.get(4).toString().equalsIgnoreCase("") || ele.get(4).toString().length() == 0 || ele.get(4).toString() == null)) {
                        this.setPerAddFlag(false);
                    } else {
                        this.setPerAddFlag(true);
                        this.setPerAdd(ele.get(3).toString() + ele.get(4).toString());
                    }
                    if ((ele.get(5).toString().equalsIgnoreCase("") || ele.get(5).toString().length() == 0 || ele.get(5).toString() == null) && (ele.get(6).toString().equalsIgnoreCase("") || ele.get(6).toString().length() == 0 || ele.get(6).toString() == null)) {
                        this.setCorAddFlag(false);
                    } else {
                        this.setCorAddFlag(true);
                        this.setCorAdd(ele.get(5).toString() + ele.get(6).toString());
                    }
                    if (ele.get(7).toString().equalsIgnoreCase("") || ele.get(7).toString().length() == 0 || ele.get(7).toString() == null) {
                        this.setPhoneFlag(false);
                    } else {
                        this.setPhoneFlag(true);
                        this.setPhNo(ele.get(7).toString());
                    }
                    if (ele.get(8).toString().equalsIgnoreCase("") || ele.get(8).toString().length() == 0 || ele.get(8).toString() == null) {
                        this.setPanFlag(false);
                    } else {
                        this.setPanFlag(true);
                        this.setPanNo(ele.get(8).toString());
                    }
                    if (ele.get(9).toString().equalsIgnoreCase("") || ele.get(9).toString().length() == 0 || ele.get(9).toString() == null) {
                        this.setDobFlag(false);
                    } else {
                        this.setDobFlag(true);
                        this.setDob(sdf.parse(ele.get(9).toString()));
                    }
                    if (ele.get(10).toString().equalsIgnoreCase("") || ele.get(10).toString().length() == 0 || ele.get(10).toString() == null) {
                        this.setIntroCustIdFlag(true);
                    } else {
                        this.setIntroCustIdFlag(true);
                        this.setIntroCustId(ele.get(10).toString());
                    }
                    if (ele.get(11).toString().equalsIgnoreCase("") || ele.get(11).toString().length() == 0 || ele.get(11).toString() == null) {
                        this.setMinorFlag(null);
                    } else {
                        this.setMinorFlag(ele.get(11).toString());
                    }
                }
                
                //---- Added by Manish Kumar
                this.occupation = "";
                this.occupationDesc = "";
                List selectList = acOpenFacadeLocal.getOccupation(this.custId);
                if(!selectList.isEmpty()){
                    Vector vec = (Vector) selectList.get(0);
                    this.occupation = vec.get(0).toString();
                    this.occupationDesc = vec.get(1).toString();
                }else{
                    this.occupation = "26";
                }
                //-----
            }
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void jtName1Detail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.jtName1CustId == null || this.jtName1CustId.equalsIgnoreCase("") || this.jtName1CustId.length() == 0) {
                this.setErrorMessage("PLEASE ENTER CUST ID FOR JOINT NAME 1");
                return;
            }
            if (this.custId.equalsIgnoreCase(this.jtName1CustId)) {
                this.setJtName1CustId("");
                this.setErrorMessage("JOINT NAME CUSTOMER ID CANNOT BE SAME AS CUSTOMER ID FOR WHICH YOU ARE OPENING ACCOUNT !!!");
                return;
            }

            String jointHolderName = acOpenFacadeLocal.getJointHolderDetails(this.jtName1CustId);
            this.setJtName1(jointHolderName);
            
        } catch (Exception e) {
            this.setJtName1CustId("");
            setErrorMessage(e.getMessage());
        }
    }

    public void jtName2Detail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.jtName2CustId == null || this.jtName2CustId.equalsIgnoreCase("") || this.jtName2CustId.length() == 0) {
                this.setErrorMessage("PLEASE ENTER CUST ID FOR JOINT NAME 2");
                return;
            }
            if (this.custId.equalsIgnoreCase(this.jtName2CustId)) {
                this.setJtName2CustId("");
                this.setErrorMessage("JOINT NAME CUSTOMER ID CANNOT BE SAME AS CUSTOMER ID FOR WHICH YOU ARE OPENING ACCOUNT !!!");
                return;
            }
            if (this.jtName1CustId.equalsIgnoreCase(this.jtName2CustId)) {
                this.setJtName2CustId("");
                this.setErrorMessage("TWO JOINT HOLDERS NAME CANNOT BE SAME !!!");
                return;
            }
            String jointHolderName = acOpenFacadeLocal.getJointHolderDetails(this.jtName2CustId);
            this.setJtName2(jointHolderName);
            
        } catch (Exception e) {
            this.setJtName2CustId("");
            setErrorMessage(e.getMessage());
        }
    }

    public void jtName3Detail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.jtName3CustId == null || this.jtName3CustId.equalsIgnoreCase("") || this.jtName3CustId.length() == 0) {
                this.setErrorMessage("PLEASE ENTER CUST ID FOR JOINT NAME 3");
                return;
            }
            if (this.custId.equalsIgnoreCase(this.jtName3CustId)) {
                this.setJtName3CustId("");
                this.setErrorMessage("JOINT NAME CUSTOMER ID CANNOT BE SAME AS CUSTOMER ID FOR WHICH YOU ARE OPENING ACCOUNT !!!");
                return;
            }
            if (this.jtName1CustId.equalsIgnoreCase(this.jtName3CustId) || this.jtName2CustId.equalsIgnoreCase(this.jtName3CustId)) {
                this.setJtName3CustId("");
                this.setErrorMessage("TWO JOINT HOLDERS NAME CANNOT BE SAME !!!");
                return;
            }
            String jointHolderName = acOpenFacadeLocal.getJointHolderDetails(this.jtName3CustId);
            this.setJtName3(jointHolderName);
            
        } catch (Exception e) {
            this.setJtName3CustId("");
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void jtName4Detail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.jtName4CustId == null || this.jtName4CustId.equalsIgnoreCase("") || this.jtName4CustId.length() == 0) {
                this.setErrorMessage("PLEASE ENTER CUST ID FOR JOINT NAME 4");
                return;
            }
            if (this.custId.equalsIgnoreCase(this.jtName4CustId)) {
                this.setJtName4CustId("");
                this.setErrorMessage("JOINT NAME CUSTOMER ID CANNOT BE SAME AS CUSTOMER ID FOR WHICH YOU ARE OPENING ACCOUNT !!!");
                return;
            }
            if (this.jtName1CustId.equalsIgnoreCase(this.jtName4CustId) || this.jtName2CustId.equalsIgnoreCase(this.jtName4CustId) || this.jtName3CustId.equalsIgnoreCase(this.jtName4CustId)) {
                this.setJtName4CustId("");
                this.setErrorMessage("TWO JOINT HOLDERS NAME CANNOT BE SAME !!!");
                return;
            }
            String jointHolderName = acOpenFacadeLocal.getJointHolderDetails(this.jtName4CustId);
            this.setJtName4(jointHolderName);
            
        } catch (Exception e) {
            this.setJtName4CustId("");
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void introducerAccountDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setIntroducerAcNo("");
        this.setIntroducerName("");
        this.setIntroducerAcStatus("");
        try {
            if (this.custId == null || this.custId.equalsIgnoreCase("") || this.custId.length() == 0) {
                this.setErrorMessage("PLEASE FIRST ENTER CUSTOMER ID FOR WHICH YOU ARE OPENING A/C.");
                return;
            }
            if (oldintroAcNo == null || oldintroAcNo.equalsIgnoreCase("")) {
                this.setErrorMessage("PLEASE ENTER PROPER INTRODUCER A/C. NO.");
                return;
            } else if (this.oldintroAcNo.length() != 0) {
                //if (this.oldintroAcNo.length() < 12) {
                if (!this.oldintroAcNo.equalsIgnoreCase("") && ((this.oldintroAcNo.length() != 12) && (this.oldintroAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    this.setErrorMessage("PLEASE ENTER PROPER INTRODUCER A/C. NO.");
                    return;
                }
            }
            FtsPostingMgmtFacadeRemote facadeRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            introAcNo = facadeRemote.getNewAccountNumber(oldintroAcNo);
            String result = acOpenFacadeLocal.introducerAcDetail(this.custId, this.introCustId, this.introAcNo);
            if (result == null) {
                this.setErrorMessage("INTRODUCER RECORD NOT FOUND !!!");
                return;
            } else {
                if (result.substring(0, 4).equalsIgnoreCase("true")) {
                    result = result.substring(4);
                    int n = result.indexOf("#");
                    int m = result.indexOf("*");
                    this.setIntroducerAcNo(result.substring(0, n));
                    this.setIntroducerName(result.substring(n + 1, m));
                    this.setIntroducerAcStatus(result.substring(m + 1));
                } else {
                    this.setErrorMessage(result);
                    return;
                }
            }
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void getLoanRoi() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setRoi("");
        this.setRoiDisFlag(true);
        try {
            if (this.limit == null || this.limit.equalsIgnoreCase("") || this.limit.length() == 0) {
                this.setErrorMessage("PLEASE ENTER LIMIT / AMOUNT SANCTIONED.");
                return;
            }
            Matcher limitCheck = p.matcher(this.limit);
            if (!limitCheck.matches()) {
                this.setLimit(null);
                this.setErrorMessage("PLEASE ENTER VALID LIMIT / AMOUNT SANCTIONED.");
                return;
            }
            if (this.limit.contains(".")) {
                if (this.limit.indexOf(".") != this.limit.lastIndexOf(".")) {
                    this.setErrorMessage("INVALID LIMIT / AMOUNT SANCTIONED.PLEASE FILL THE LIMIT CORRECTLY.");
                    return;
                } else if (this.limit.substring(limit.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("PLEASE FILL THE LIMIT UPTO TWO DECIMAL PLACES.");
                    return;
                }
            }
            if (Float.parseFloat(this.limit) < 0) {
                this.setLimit(null);
                this.setErrorMessage("LIMIT / AMOUNT SANCTIONED CANNOT BE NEGATIVE !!!");
                return;
            }

            if (this.intCode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT INTEREST CODE.");
                return;
            }


            /**
             * ****************************************************************************
             */
            /*     List schemeCodeLst = loanAcOpen.schemeCodeCheck(schemeCode);
             Vector ele = (Vector) schemeCodeLst.get(0);                
             float loanamountmin    =  Float.parseFloat(ele.get(4).toString());
             float loanamountmax   =  Float.parseFloat(ele.get(5).toString());
             NumberFormat formatter = new DecimalFormat("#0.00");
            
             if (this.limit != null || !this.limit.equalsIgnoreCase("") || this.limit.length() != 0) {
             if(Float.parseFloat(limit) <loanamountmin ||
             Float.parseFloat(limit) > loanamountmax){
             this.setErrorMessage("AMOUNT SANCTIONED COULD NOT BE LESS THAN "+formatter.format(loanamountmin)+" AND COULD NOT BE GREATER " +
             "THAN "+formatter.format(loanamountmax));
             return;
             }
             }*/
            /**
             * ****************************************************************************
             */
            String result = acOpenFacadeLocal.GetROIForLoanDLAcOpen(this.intCode, Float.parseFloat(this.limit), ymd.format(this.acOpenDt));
            
            if (result == null) {
                this.setErrorMessage("ROI NOT FOUND!!!");
                this.setRoiDisFlag(false);
                this.setTxtAcPrefCrDisFlag(false);
                this.setTxtAcPrefDrDisFlag(false);
                return;
            } else {
                if (result.substring(0, 3).equalsIgnoreCase("ROI")) {
                    this.setErrorMessage(result);
                    this.setRoiDisFlag(false);
                    this.setTxtAcPrefCrDisFlag(false);
                    this.setTxtAcPrefDrDisFlag(false);
                    return;
                } else {
                    this.setRoi(result);
                    this.setRoiDisFlag(true);
                }
            }
            if(OdAllowWithSlab.equalsIgnoreCase("Y")){
                this.setRoiDisFlag(true);
                this.setRoi("0");    
            } else {
                this.setRoiDisFlag(false);                
            }
            if(drCrIndFlag.equalsIgnoreCase("N")) {
                this.setTxtAcPrefCrDisFlag(true);
                this.setTxtAcPrefDrDisFlag(true);
                printApplicableRoi();
            } else {
                this.setTxtAcPrefCrDisFlag(false);
                this.setTxtAcPrefDrDisFlag(false);
            }
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void intAppFrequency1LostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setInterestAppFreq2(" ");
        try {
            if (this.interestAppFreq1.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT INTEREST APPLICATION FREQUENCY 1");
                return;
            }
            if (this.interestAppFreq1.equalsIgnoreCase("S")) {
                this.setIntAppFrqDisFlag(true);
            } else if (this.interestAppFreq1.equalsIgnoreCase("C")) {
                this.setIntAppFrqDisFlag(false);
            }
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void operationModeLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setJtName1("");
        this.setJtName2("");
        this.setJtName3("");
        this.setJtName4("");
        this.setJtName1CustId("");
        this.setJtName2CustId("");
        this.setJtName3CustId("");
        this.setJtName4CustId("");
        this.setHufFamily("");
        this.setHufFlag(true);
        try {
            if (this.oprMode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT OPERATION MODE.");
                return;
            }
            List resultList = acOpenFacadeLocal.operationModeComboLostFocus(this.oprMode);
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                if (ele.get(0).toString().equalsIgnoreCase("SELF") || ele.get(0).toString().equalsIgnoreCase("PROPRIETOR") || ele.get(0).toString().equalsIgnoreCase("UNDER POWER OF ATTOR") || ele.get(0).toString().equalsIgnoreCase("M.D.") || ele.get(0).toString().equalsIgnoreCase("UNDER GUARDIANSHIP") || ele.get(0).toString().equalsIgnoreCase("MINOR")) {
                    this.setJtName1DisFlag(false);
                    this.setJtName2DisFlag(true);
                    this.setJtName3DisFlag(true);
                    this.setJtName4DisFlag(true);
                } else if (ele.get(0).toString().equalsIgnoreCase("EITHER OR SURVIVOR") || ele.get(0).toString().equalsIgnoreCase("FORMER OR SURVIVOR") || ele.get(0).toString().equalsIgnoreCase("AUTHORIZED SIGNATORY") || ele.get(0).toString().equalsIgnoreCase("ANY ONE OR SURVIVOR")) {
                    this.setJtName1DisFlag(false);
                    this.setJtName2DisFlag(false);
                    this.setJtName3DisFlag(false);
                    this.setJtName4DisFlag(false);
                } else if (ele.get(0).toString().equalsIgnoreCase("ANY TWO JOINTLY")) {
                    this.setJtName1DisFlag(false);
                    this.setJtName2DisFlag(false);
                    this.setJtName3DisFlag(false);
                    this.setJtName4DisFlag(false);
                } else if (ele.get(0).toString().equalsIgnoreCase("BOTH OF TWO JOINTLY")) {
                    this.setJtName1DisFlag(false);
                    this.setJtName2DisFlag(false);
                    this.setJtName3DisFlag(true);
                    this.setJtName4DisFlag(true);
                } else if (ele.get(0).toString().equalsIgnoreCase("ANY THREE JOINTLY")) {
                    this.setJtName1DisFlag(false);
                    this.setJtName2DisFlag(false);
                    this.setJtName3DisFlag(false);
                    this.setJtName4DisFlag(true);
                } else if (ele.get(0).toString().equalsIgnoreCase("ANY FOUR JOINTLY") || ele.get(0).toString().equalsIgnoreCase("ANY FIVE JOINTLY") || ele.get(0).toString().equalsIgnoreCase("ALL JOINTLY")) {
                    this.setJtName1DisFlag(false);
                    this.setJtName2DisFlag(false);
                    this.setJtName3DisFlag(false);
                    this.setJtName4DisFlag(false);
                }else if(ele.get(0).toString().equalsIgnoreCase("KARTA OF HUF")){
                    this.setJtName1DisFlag(false);
                    this.setJtName2DisFlag(true);
                    this.setJtName3DisFlag(true);
                    this.setJtName4DisFlag(true);
                    this.setHufFlag(false);
                }
            }
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void nomineeDetailValidation() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if ((this.nominee == null) || (this.nominee.equalsIgnoreCase("")) || (this.nominee.length() == 0)) {
                this.setNomDisFlag(true);
                this.setNomineeAdd("");
                this.setNomRelationship("");
                this.setNomineeDob(date);
            } else if ((this.nominee != null)) {
                this.setNomDisFlag(false);
                this.setNomRelationship("");
                this.setNomineeAdd("");
                this.setNomineeDob(date);
                this.setErrorMessage("IF YOU HAVE ENTERED NOMINEE NAME THEN PLEASE FILL NOMINEE RELATIONSHIP, ADDRESS, DOB OF NOMINEE.");
                return;
            }
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void rateCodeComboLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setPaggingFrequence("");
        try {
            if (this.rateCode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT RATE CODE.");
                return;
            } else if (this.rateCode.equalsIgnoreCase("Fl")) {
                this.setPagFreqDisFlag(true);
            } else if (this.rateCode.equalsIgnoreCase("Fi")) {
                this.setPagFreqDisFlag(false);
            }
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void saveDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.custId.equalsIgnoreCase("") || this.custId == null || this.custId.length() == 0) {
                this.setErrorMessage("PLEASE ENTER CUSTOMER ID.");
                return;
            }
            if (this.acctType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT A/C.TYPE.");
                return;
            }
            if (this.schemeCode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT SCHEME CODE.");
                return;
            }
            if (this.loanApp.equalsIgnoreCase("true")) {
                if (this.appSeqNo.equalsIgnoreCase("--Select--")) {
                    this.setErrorMessage("PLEASE SELECT APPLICATION SEQUENCE NO.");
                    return;
                }
            }
            if (this.title.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT TITLE FOR CUSTOMER NAME.");
                return;
            }
            if (this.custName.equalsIgnoreCase("") || this.custName == null || this.custName.length() == 0) {
                this.setErrorMessage("PLEASE ENTER CUSTOMER NAME.");
                return;
            }

            if (this.perAdd.equalsIgnoreCase("") || this.perAdd == null || this.perAdd.length() == 0) {
                this.setErrorMessage("PLEASE ENTER PERMANENT ADDRESS.");
                return;
            }
            if (this.corAdd.equalsIgnoreCase("") || this.corAdd == null || this.corAdd.length() == 0) {
                this.setErrorMessage("PLEASE ENTER CORRESPONDENCE ADDRESS.");
                return;
            }
            if (this.dob == null) {
                this.setErrorMessage("PLEASE ENTER DATE OF BIRTH.");
                return;
            }
//            if (this.occupation.equalsIgnoreCase("--Select--")) {
//                this.setErrorMessage("PLEASE SELECT OCCUPATION.");
//                return;
//            }
            if (this.oprMode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT OPERATION MODE.");
                return;
            }
            
            if(actCategory.equalsIgnoreCase("HUF") || oprMode.equalsIgnoreCase("18")){
                if(!(actCategory.equalsIgnoreCase("HUF") && oprMode.equalsIgnoreCase("18"))){
                    this.setMessage("Please Select Proper Account Category And Operation Mode");
                    return;
                }
            }
            
            if(oprMode.equalsIgnoreCase("18")){
                if (this.jtName1CustId.equalsIgnoreCase("") || this.jtName1CustId == null || this.jtName1CustId.length() == 0) {
                    this.setErrorMessage("PLEASE ENTER PROPRITOR NAME");
                    return;
                }
                
                if ((hufFamily.equals("")) || (hufFamily == null)) {                
                    this.setMessage("Huf Family Detail Can't Be Blank In Case Of HUF Account.");
                    return;
                }
            }

            List resultList = acOpenFacadeLocal.operationModeComboLostFocus(this.oprMode);
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                if ((ele.get(0).toString().equalsIgnoreCase("KARTA OF HUF") || ele.get(0).toString().equalsIgnoreCase("SELF") || ele.get(0).toString().equalsIgnoreCase("PROPRIETOR") || ele.get(0).toString().equalsIgnoreCase("UNDER POWER OF ATTOR") || ele.get(0).toString().equalsIgnoreCase("M.D.") || ele.get(0).toString().equalsIgnoreCase("UNDER GUARDIANSHIP") || ele.get(0).toString().equalsIgnoreCase("MINOR")) && (this.acctType.equalsIgnoreCase("CA"))) {
                    if (this.jtName1CustId.equalsIgnoreCase("") || this.jtName1CustId == null || this.jtName1CustId.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER JOINT NAME 1.");
                        return;
                    }
                } else if (ele.get(0).toString().equalsIgnoreCase("EITHER OR SURVIVOR") || ele.get(0).toString().equalsIgnoreCase("FORMER OR SURVIVOR") || ele.get(0).toString().equalsIgnoreCase("AUTHORIZED SIGNATORY") || ele.get(0).toString().equalsIgnoreCase("ANY ONE OR SURVIVOR") || ele.get(0).toString().equalsIgnoreCase("UNDER POWER OF ATTOR")) {
                    if (this.jtName1CustId.equalsIgnoreCase("") || this.jtName1CustId == null || this.jtName1CustId.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER JOINT NAME 1.");
                        return;
                    }
                } else if (ele.get(0).toString().equalsIgnoreCase("ANY TWO JOINTLY")) {
                    if (this.jtName1CustId.equalsIgnoreCase("") || this.jtName1CustId == null || this.jtName1CustId.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER JOINT NAME 1.");
                        return;
                    }
                    if (this.jtName2CustId.equalsIgnoreCase("") || this.jtName2CustId == null || this.jtName2CustId.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER JOINT NAME 2.");
                        return;
                    }
                } else if (ele.get(0).toString().equalsIgnoreCase("BOTH OF TWO JOINTLY")) {
                    if (this.jtName1CustId.equalsIgnoreCase("") || this.jtName1CustId == null || this.jtName1CustId.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER JOINT NAME 1.");
                        return;
                    }
                    if (this.jtName2CustId.equalsIgnoreCase("") || this.jtName2CustId == null || this.jtName2CustId.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER JOINT NAME 2.");
                        return;
                    }
                } else if (ele.get(0).toString().equalsIgnoreCase("ANY THREE JOINTLY")) {
                    if (this.jtName1CustId.equalsIgnoreCase("") || this.jtName1CustId == null || this.jtName1CustId.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER JOINT NAME 1.");
                        return;
                    }
                    if (this.jtName2CustId.equalsIgnoreCase("") || this.jtName2CustId == null || this.jtName2CustId.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER JOINT NAME 2.");
                        return;
                    }
                    if (this.jtName3CustId.equalsIgnoreCase("") || this.jtName3CustId == null || this.jtName3CustId.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER JOINT NAME 3.");
                        return;
                    }
                } else if (ele.get(0).toString().equalsIgnoreCase("ANY FOUR JOINTLY") || ele.get(0).toString().equalsIgnoreCase("ANY FIVE JOINTLY") || ele.get(0).toString().equalsIgnoreCase("ALL JOINTLY")) {
                    if (this.jtName1CustId.equalsIgnoreCase("") || this.jtName1CustId == null || this.jtName1CustId.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER JOINT NAME 1.");
                        return;
                    }
                    if (this.jtName2CustId.equalsIgnoreCase("") || this.jtName2CustId == null || this.jtName2CustId.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER JOINT NAME 2.");
                        return;
                    }
                    if (this.jtName3CustId.equalsIgnoreCase("") || this.jtName3CustId == null || this.jtName3CustId.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER JOINT NAME 3.");
                        return;
                    }
                    if (this.jtName4CustId.equalsIgnoreCase("") || this.jtName4CustId == null || this.jtName4CustId.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER JOINT NAME 4.");
                        return;
                    }
                } else if (ele.get(0).toString().equalsIgnoreCase("MINOR")) {
                    if (this.grName.equalsIgnoreCase("") || this.grName == null || this.grName.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER GUARDIAN NAME.");
                        return;
                    }
                    if (this.grRelationship.equalsIgnoreCase("") || this.grRelationship == null || this.grRelationship.length() == 0) {
                        this.setErrorMessage("PLEASE ENTER GUARDIAN RELATIONSHIP.");
                        return;
                    }
                }
            }
            /**
             * ****************************************************************************
             */
            /*    List schemeCodeLst = loanAcOpen.schemeCodeCheck(schemeCode);
             Vector ele = (Vector) schemeCodeLst.get(0);
             String loanperiodminimonths   =  ele.get(0).toString();
             String loanperiodminidays   =  ele.get(1).toString();
             String loanperiodmaxmonths   =  ele.get(2).toString();
             String loanperiodmaxdays   =  ele.get(3).toString();
             float loanamountmin    =  Float.parseFloat(ele.get(4).toString());
             float loanamountmax   =  Float.parseFloat(ele.get(5).toString());
             NumberFormat formatter = new DecimalFormat("#0.00");*/
            /**
             * ****************************************************************************
             */
            if ((this.nominee == null) || (this.nominee.equalsIgnoreCase("")) || (this.nominee.length() == 0)) {
            } else {

                if (this.nominee.equalsIgnoreCase("") || this.nominee == null || this.nominee.length() == 0) {
                    this.setErrorMessage("PLEASE ENTER NOMINEE NAME");
                    return;
                }
                if (this.nomRelationship.equalsIgnoreCase("") || this.nomRelationship == null || this.nomRelationship.length() == 0) {
                    this.setErrorMessage("PLEASE ENTER NOMINEE RELATIONSHIP.");
                    return;
                }
                if (this.nomineeAdd.equalsIgnoreCase("") || this.nomineeAdd == null || this.nomineeAdd.length() == 0) {
                    this.setErrorMessage("PLEASE ENTER NOMINEE ADDRESS.");
                    return;
                }
                if (this.nomineeDob == null) {
                    this.setErrorMessage("PLEASE ENTER NOMINEE DATE OF BIRTH.");
                    return;
                }
            }
            
            if (this.actCategory.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT ACCOUNT CATEGORY.");
                return;
            }
            if (this.moratoriumPeriod.equalsIgnoreCase("") || this.moratoriumPeriod == null || this.moratoriumPeriod.length() == 0) {
                this.setErrorMessage("PLEASE ENTER MORATORIUM PERIOD.");
                return;
            }
            Matcher morCheck = p.matcher(this.moratoriumPeriod);
            if (!morCheck.matches()) {
                this.setErrorMessage("PLEASE ENTER VALID MORATORIUM PERIOD.");
                return;
            }
            if (this.moratoriumPeriod.contains(".")) {
                this.setErrorMessage("PLEASE ENTER VALID MORATORIUM PERIOD.");
                return;
            }
            if (this.moratoriumPeriod.contains(".")) {
                if (this.moratoriumPeriod.indexOf(".") != this.moratoriumPeriod.lastIndexOf(".")) {
                    this.setErrorMessage("INVALID MORATORIUM PERIOD.PLEASE FILL THE MORATORIUM PERIOD CORRECTLY.");
                    return;
                } else if (this.moratoriumPeriod.substring(moratoriumPeriod.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("INVALID MORATORIUM PERIOD.PLEASE FILL THE MORATORIUM PERIOD CORRECTLY.");
                    return;
                }
            }
            if (Integer.parseInt(this.moratoriumPeriod) < 0) {
                this.setErrorMessage("MORATORIUM PERIOD COULD NOT BE LESS THAN ZERO !!!");
                return;
            }

            if (this.rateCode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT RATE CODE.");
                return;
            }
            if (this.rateCode.equalsIgnoreCase("Fi")) {
                if (this.paggingFrequence.equalsIgnoreCase("") || this.paggingFrequence == null || this.paggingFrequence.length() == 0) {
                    this.setErrorMessage("PLEASE ENTER PAGGING FREQUENCY.");
                    return;
                }
                Matcher paggingFrequenceCheck = p.matcher(this.paggingFrequence);
                if (!paggingFrequenceCheck.matches()) {
                    this.setErrorMessage("PLEASE ENTER VALID PAGGING FREQUENCY (NUMBERS ONLY).");
                    return;
                }
                if (this.paggingFrequence.contains(".")) {
                    this.setErrorMessage("PLEASE ENTER VALID PAGGING FREQUENCY (NUMBERS ONLY).");
                    return;
                }
                if (this.paggingFrequence.contains(".")) {
                    if (this.paggingFrequence.indexOf(".") != this.paggingFrequence.lastIndexOf(".")) {
                        this.setErrorMessage("INVALID PAGGING FREQUENCY.PAGGING FREQUENCY COULD NOT BE IN DECIMALS !!!");
                        return;
                    } else if (this.paggingFrequence.substring(paggingFrequence.indexOf(".") + 1).length() > 2) {
                        this.setErrorMessage("INVALID PAGGING FREQUENCY.PAGGING FREQUENCY COULD NOT BE IN DECIMALS !!!");
                        return;
                    }
                }
                if (Integer.parseInt(this.paggingFrequence) <= 0) {
                    this.setErrorMessage("PAGGING FREQUENCY COULD NOT BE ZERO OR LESS THAN ZERO !!!.");
                    return;
                }
            } else {
                this.paggingFrequence = "0";
            }
            if ((this.docName.equalsIgnoreCase("--Select--")) && (this.oldintroAcNo.equalsIgnoreCase("")
                    || this.oldintroAcNo == null || this.oldintroAcNo.length() == 0)) {
                this.setErrorMessage("PLEASE ENTER EITHER INTRODUCER ACCOUNT NO OR DOCUMENT.");
                return;
            }
            if (this.oldintroAcNo.equalsIgnoreCase("")) {
                if (this.docName.equalsIgnoreCase("--Select--")) {
                    this.setErrorMessage("PLEASE SELECT DOCUMENT NAME.");
                    return;
                }
            }
            if (!this.docName.equalsIgnoreCase("--Select--")) {
                if (this.docDetail.equalsIgnoreCase("")) {
                    this.setErrorMessage("PLEASE SELECT DOCUMENT DETAILS.");
                    return;
                }
            }
            if (this.docName.equalsIgnoreCase("--Select--")) {
                setDocName("0");
                if (this.oldintroAcNo.equalsIgnoreCase("") || this.oldintroAcNo == null || this.oldintroAcNo.length() == 0) {
                    this.setErrorMessage("PLEASE ENTER INTRODUCER ACCOUNT NO.");
                    return;
                }
                //if (this.oldintroAcNo.length() < 12) {
                if (!this.oldintroAcNo.equalsIgnoreCase("") && ((this.oldintroAcNo.length() != 12) && (this.oldintroAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    this.setErrorMessage("PLEASE ENTER PROPER INTRODUCER ACCOUNT NO.");
                    return;
                }
            }
            if (this.disburseType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT DISBURSEMENT TYPE.");
                return;
            }
            if (this.calMethod.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT INTEREST CALCULATION METHOD.");
                return;
            }
            if (this.interestAppFreq1.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT INTEREST APPLICATION FREQUENCY.");
                return;
            }
            if (this.interestAppFreq1.equalsIgnoreCase("C")) {
                if (this.interestAppFreq2.equalsIgnoreCase(" ")) {
                    this.setErrorMessage("PLEASE SELECT INTEREST APPLICATION FREQUENCY FOR COMPOUND.");
                    return;
                }
            }
            if (this.limit == null || this.limit.equalsIgnoreCase("") || this.limit.length() == 0) {
                this.setErrorMessage("PLEASE ENTER AMOUNT SANCTIONED / LIMIT.");
                return;
            }
            Matcher limitCheck = p.matcher(this.limit);
            if (!limitCheck.matches()) {
                this.setErrorMessage("PLEASE ENTER VALID AMOUNT SANCTIONED / LIMIT(NUMBERS ONLY).");
                return;
            }
            if (this.limit.contains(".")) {
                if (this.limit.indexOf(".") != this.limit.lastIndexOf(".")) {
                    this.setErrorMessage("INVALID LIMIT / AMOUNT SANCTIONED.PLEASE FILL THE LIMIT CORRECTLY.");
                    return;
                } else if (this.limit.substring(limit.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("PLEASE FILL THE LIMIT UPTO TWO DECIMAL PLACES.");
                    return;
                }
            }
            if (this.acctType.equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                if (Float.parseFloat(this.limit) < 0) {
                    this.setErrorMessage("LIMIT / AMOUNT SANCTIONED COULD NOT BE ZERO OR LESS THAN ZERO !!!");
                    return;
                }
            } else {
                if (Float.parseFloat(this.limit) <= 0) {
                    this.setErrorMessage("LIMIT / AMOUNT SANCTIONED COULD NOT BE ZERO OR LESS THAN ZERO !!!");
                    return;
                }
            }
            /*   if (this.limit != null || !this.limit.equalsIgnoreCase("") || this.limit.length() != 0) {
             if(Float.parseFloat(limit) <loanamountmin ||
             Float.parseFloat(limit) > loanamountmax){
             this.setErrorMessage("AMOUNT SANCTIONED COULD NOT BE LESS THAN "+formatter.format(loanamountmin)+" AND COULD NOT BE GREATER " +
             "THAN "+formatter.format(loanamountmax));
             return;
             }
             }*/

            if (!this.acctType.equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                if (sancDate == null) {
                    this.setErrorMessage("PLEASE ENTER SANCTIONED DATE.");
                    return;
                }
            }
            if (this.acctType.equalsIgnoreCase(CbsAcCodeConstant.TERM_LOAN.getAcctCode())) {
                if (this.subsidyAmt.equalsIgnoreCase("") || this.subsidyAmt == null || this.subsidyAmt.length() == 0) {
                    this.setErrorMessage("PLEASE ENTER SUBSIDY AMOUNT.");
                    return;
                }
                Matcher subsidyAmtCheck = p.matcher(this.subsidyAmt);
                if (!subsidyAmtCheck.matches()) {
                    this.setErrorMessage("PLEASE ENTER VALID(NUMERIC) SUBSIDY AMOUNT.");
                    return;
                }
                if (this.subsidyAmt.contains(".")) {
                    if (this.subsidyAmt.indexOf(".") != this.subsidyAmt.lastIndexOf(".")) {
                        this.setErrorMessage("INVALID SUBSIDY AMOUNT.PLEASE FILL THE SUBSIDY AMOUNT CORRECTLY.");
                        return;
                    } else if (this.subsidyAmt.substring(subsidyAmt.indexOf(".") + 1).length() > 2) {
                        this.setErrorMessage("PLEASE FILL THE SUBSIDY AMOUNT UPTO TWO DECIMAL PLACES.");
                        return;
                    }
                }
                if (Float.parseFloat(this.subsidyAmt) < 0) {
                    this.setErrorMessage("SUBSIDY AMOUNT COULD NOT BE LESS THAN ZERO !!!");
                    return;
                }
                if (Float.parseFloat(this.limit) <= Float.parseFloat(this.subsidyAmt)) {
                    this.setErrorMessage("LIMIT / AMOUNT SANCTIONED COULD NOT BE LESS THAN OR EQUALS TO SUBSIDY AMOUNT !!!");
                    return;
                }
            } else {
                this.setSubsidyAmt("0");
            }
            if (this.loanPeriodMm.equalsIgnoreCase("") || this.loanPeriodMm == null || this.loanPeriodMm.length() == 0) {
                this.setErrorMessage("PLEASE ENTER LOAN PERIOD MM.");
                return;
            }
            Matcher loanPeriodMmCheck = p.matcher(this.loanPeriodMm);
            if (!loanPeriodMmCheck.matches()) {
                this.setErrorMessage("PLEASE ENTER VALID(NUMERIC) LOAN PERIOD MM.");
                return;
            }
            if (this.loanPeriodMm.contains(".")) {
                this.setErrorMessage("PLEASE ENTER VALID(NUMERIC) LOAN PERIOD MM.");
                return;
            }
            if (this.loanPeriodMm.contains(".")) {
                if (this.loanPeriodMm.indexOf(".") != this.loanPeriodMm.lastIndexOf(".")) {
                    this.setErrorMessage("INVALID LOAN PERIOD MM.PLEASE FILL THE LOAN PERIOD MM CORRECTLY.");
                    return;
                } else if (this.loanPeriodMm.substring(loanPeriodMm.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("INVALID LOAN PERIOD MM.PLEASE FILL THE LOAN PERIOD MM CORRECTLY.");
                    return;
                }
            }
            if (this.acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                if (Integer.parseInt(this.loanPeriodMm) < 0) {
                    this.setErrorMessage("LOAN PERIOD MM COULD NOT BE LESS THAN OR EQUALS TO ZERO.");
                    return;
                }
            } else {
                if (Integer.parseInt(this.loanPeriodMm) <= 0) {
                    this.setErrorMessage("LOAN PERIOD MM COULD NOT BE LESS THAN OR EQUALS TO ZERO.");
                    return;
                }
            }



            /*    if (!this.loanPeriodMm.equalsIgnoreCase("") || this.loanPeriodMm != null || this.loanPeriodMm.length() != 0) {
             if(Float.parseFloat(loanPeriodMm) < Float.parseFloat(loanperiodminimonths) ||
             Float.parseFloat(loanPeriodMm) > Float.parseFloat(loanperiodmaxmonths)){
             this.setErrorMessage("LOAN PERIOD IN MM COULD NOT BE LESS THAN "+loanperiodminimonths+" AND COULD NOT BE GREATER " +
             "THAN "+loanperiodmaxmonths);
             return;
             }
             }*/

            if (this.loanPeriodDd.equalsIgnoreCase("") || this.loanPeriodDd == null || this.loanPeriodDd.length() == 0) {
                this.setErrorMessage("PLEASE ENTER LOAN PERIOD DD.");
                return;
            }
            Matcher loanPeriodDdCheck = p.matcher(this.loanPeriodDd);
            if (!loanPeriodDdCheck.matches()) {
                this.setErrorMessage("PLEASE ENTER VALID(NUMERIC) LOAN PERIOD DD.");
                return;
            }
            if (this.loanPeriodDd.contains(".")) {
                this.setErrorMessage("PLEASE ENTER VALID(NUMERIC) LOAN PERIOD DD.");
                return;
            }
            if (this.loanPeriodDd.contains(".")) {
                if (this.loanPeriodDd.indexOf(".") != this.loanPeriodDd.lastIndexOf(".")) {
                    this.setErrorMessage("INVALID LOAN PERIOD DD.PLEASE FILL THE LOAN PERIOD DD CORRECTLY.");
                    return;
                } else if (this.loanPeriodDd.substring(loanPeriodDd.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("INVALID LOAN PERIOD DD.PLEASE FILL THE LOAN PERIOD DD CORRECTLY.");
                    return;
                }
            }
            if (Integer.parseInt(this.loanPeriodDd) < 0) {
                this.setErrorMessage("LOAN PERIOD DD COULD NOT BE LESS THAN ZERO.");
                return;
            }
            if (Integer.parseInt(this.loanPeriodDd) > 365 || Integer.parseInt(this.loanPeriodDd) > 366) {
                this.setErrorMessage("LOAN PERIOD DD COULD NOT BE GREATOR THAN 365 OR 366 !!!");
                return;
            }

            /**
             * ***************************************************************************************
             */
            /*    if (!this.loanPeriodDd.equalsIgnoreCase("") || this.loanPeriodDd != null || this.loanPeriodDd.length() != 0) {
             if(Float.parseFloat(loanPeriodDd) < Float.parseFloat(loanperiodminidays) ||
             Float.parseFloat(loanPeriodDd) > Float.parseFloat(loanperiodmaxdays)){
             this.setErrorMessage("LOAN PERIOD IN DD COULD NOT BE LESS THAN "+loanperiodminidays+" AND COULD NOT BE GREATER " +
             "THAN "+loanperiodmaxdays);
             return;
             }
             }*/
            /**
             * ****************************************************************************************
             */
            if (Integer.parseInt(this.loanPeriodMm) < Integer.parseInt(this.moratoriumPeriod)) {
                this.setErrorMessage("MORATORIUM PERIOD MUST BE LESS THAN LOAN PERIOD MM !!!");
                return;
            }
            if (this.intCode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT INTEREST CODE.");
                return;
            }
            if (this.roi.equalsIgnoreCase("") || this.roi == null || this.roi.length() == 0) {
                this.setErrorMessage("PLEASE ENTER ROI.");
                return;
            }
            Matcher roiCheck = p.matcher(this.roi);
            if (!roiCheck.matches()) {
                this.setErrorMessage("PLEASE ENTER VALID(NUMERIC) ROI.");
                return;
            }
            if (this.roi.contains(".")) {
                if (this.roi.indexOf(".") != this.roi.lastIndexOf(".")) {
                    this.setErrorMessage("INVALID ROI.PLEASE FILL THE ROI CORRECTLY.");
                    return;
                } else if (this.roi.substring(roi.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("PLEASE FILL THE ROI UPTO TWO DECIMAL PLACES.");
                    return;
                }
            }
            if (this.acPreferCrDr1.equalsIgnoreCase("") || this.acPreferCrDr1 == null || this.acPreferCrDr1.length() == 0) {
                this.setErrorMessage("PLEASE ENTER ACCOUNT PREFERABLE DR");
                return;
            }
            Matcher acPreferCrDr1Check = p.matcher(this.acPreferCrDr1);
            if (!acPreferCrDr1Check.matches()) {
                this.setErrorMessage("PLEASE ENTER VALID(NUMERIC) ACCOUNT PREFERABLE DR.");
                return;
            }
            if (this.acPreferCrDr1.contains(".")) {
                if (this.acPreferCrDr1.indexOf(".") != this.acPreferCrDr1.lastIndexOf(".")) {
                    this.setErrorMessage("INVALID ACCOUNT PREFERABLE DR.PLEASE FILL THE ACCOUNT PREFERABLE DR CORRECTLY.");
                    return;
                } else if (this.acPreferCrDr1.substring(acPreferCrDr1.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("PLEASE FILL THE ACCOUNT PREFERABLE DR UPTO TWO DECIMAL PLACES.");
                    return;
                }
            }
            if (this.acPreferCrDr2.equalsIgnoreCase("") || this.acPreferCrDr2 == null || this.acPreferCrDr2.length() == 0) {
                this.setErrorMessage("PLEASE ENTER ACCOUNT PREFERABLE CR");
                return;
            }
            Matcher acPreferCrDr2Check = p.matcher(this.acPreferCrDr2);
            if (!acPreferCrDr2Check.matches()) {
                this.setErrorMessage("PLEASE ENTER VALID(NUMERIC) ACCOUNT PREFERABLE CR.");
                return;
            }
            if (this.acPreferCrDr2.contains(".")) {
                if (this.acPreferCrDr2.indexOf(".") != this.acPreferCrDr2.lastIndexOf(".")) {
                    this.setErrorMessage("INVALID ACCOUNT PREFERABLE CR.PLEASE FILL THE ACCOUNT PREFERABLE CR CORRECTLY.");
                    return;
                } else if (this.acPreferCrDr2.substring(acPreferCrDr2.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("PLEASE FILL THE ACCOUNT PREFERABLE CR UPTO TWO DECIMAL PLACES.");
                    return;
                }
            }
            if (this.calLevel.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT CALCULATION LEVEL.");
                return;
            }
            if (this.calOn.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT CALCULATION ON.");
                return;
            }
            float rateofInterest = 0;
            if (Float.parseFloat(acPreferCrDr2) > 0) {
                rateofInterest = Float.parseFloat(roi) - Float.parseFloat(acPreferCrDr2);
                roi = Float.toString(rateofInterest);
            }
            if (Float.parseFloat(acPreferCrDr1) > 0) {
                rateofInterest = Float.parseFloat(roi) + Float.parseFloat(acPreferCrDr1);
                roi = Float.toString(rateofInterest);
            }
            if(interestAppFreq1.equalsIgnoreCase("S") ){
                if(interestAppFreq2.equalsIgnoreCase("--Select--")){
                    interestAppFreq2 = "0";
                }
            }
            String result = acOpenFacadeLocal.saveTLAcOpenDetail(this.custId, this.acNature, this.loanApp, this.appSeqNo, this.fYear, this.acctType,
                    this.getOrgnBrCode(), "01", ymd.format(acOpenDt), this.title, this.custName, this.fatherHusName, acOpenFacadeLocal.removeSomeSpecialChar(this.perAdd),
                    acOpenFacadeLocal.removeSomeSpecialChar(this.corAdd), this.phNo, this.panNo, ymd.format(dob), Integer.parseInt(this.occupation), this.grName, this.grRelationship,
                    Integer.parseInt(this.oprMode), this.jtName1, this.jtName2, this.jtName3, this.jtName4, this.nominee, this.nomRelationship,
                    this.nomineeAdd, ymd.format(this.nomineeDob), this.introAcNo, Float.parseFloat(this.limit), Float.parseFloat(this.roi),
                    ymd.format(this.sancDate), this.intOpt.substring(0, 1), this.subsidyAmt, Integer.parseInt(this.docName),
                    this.docDetail, this.specialInst, this.getUserName(), this.schemeCode, Integer.parseInt(this.moratoriumPeriod), Float.parseFloat(this.acPreferCrDr1),
                    Float.parseFloat(this.acPreferCrDr2), this.rateCode, this.calMethod, this.calOn, this.interestAppFreq1, this.calLevel, this.interestAppFreq2, this.disburseType, this.intCode,
                    this.paggingFrequence, Integer.parseInt(this.loanPeriodMm), Integer.parseInt(this.loanPeriodDd), this.jtName1CustId, this.jtName2CustId, this.jtName3CustId, this.jtName4CustId,this.actCategory, hufFamily, Integer.parseInt(this.chqFacility));

            if (result == null) {
                this.setErrorMessage("DATA NOT SAVED , SOME PROBLEM OCCURED !!!");
                return;
            } else {
                if (result.contains("!")) {
                    this.setErrorMessage(result);
                } else {
                    this.setMessage("GENERATED ACCOUNT NO. IS : " + result);
                    clearFieldsAfterSave();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            setErrorMessage(e.getMessage());
        }
    }

    public void resetForm() {
        this.setActCategory("--Select--");
        setAcTypeDesc("");
        this.setErrorMessage("");
        this.setMessage("");
        this.setCustId("");
        this.setAcctType("--Select--");
        this.setSchemeCode("--Select--");
        this.setAppSeqNo("--Select--");
        this.setTitle("--Select--");
        this.setCustName("");
        this.setFatherHusName("");
        this.setPerAdd("");
        this.setCorAdd("");
        this.setPhNo("");
        this.setPanNo("");
        this.setDob(null);
        this.setOccupation("--Select--");
        this.setJtName1("");
        this.setJtName1CustId("");
        this.setJtName2("");
        this.setJtName2CustId("");
        this.setJtName3("");
        this.setJtName3CustId("");
        this.setJtName4("");
        this.setJtName4CustId("");
        this.setNominee("");
        this.setNomRelationship("");
        this.setNomineeAdd("");
        this.setNomineeDob(date);
        this.setMoratoriumPeriod("");
        this.setRateCode("--Select--");
        this.setPaggingFrequence("");
        this.setIntroAcNo("");
        setOldintroAcNo("");
        this.setIntroCustId("");
        this.setGrName("");
        this.setGrRelationship("");
        this.setOprMode("--Select--");
        this.setIntroducerAcNo("");
        this.setIntroducerAcStatus("");
        this.setIntroducerName("");
        this.setDocName("--Select--");
        this.setDocDetail("");
        this.setSpecialInst("");
        this.setDisburseType("--Select--");
        this.setCalMethod("--Select--");
        this.setInterestAppFreq1("--Select--");
        this.setInterestAppFreq2(" ");
        this.setSancDate(null);
        this.setLimit("");
        this.setSubsidyAmt("");
        this.setLoanPeriodDd("");
        this.setLoanPeriodMm("");
        this.setIntCode("--Select--");
        this.setRoi("");
        this.setAcPreferCrDr1("");
        this.setAcPreferCrDr2("");
        this.setCalLevel("--Select--");
        this.setCalOn("--Select--");
        this.setFieldDisFlag(true);
        this.setFieldCalLevelDisFlag(true);
        this.setFlag1(true);
        this.setFlag2(true);
        this.setTitleFlag(true);
        this.setCustNameFlag(true);
        this.setFatherNameFlag(true);
        this.setCorAddFlag(true);
        this.setPerAddFlag(true);
        this.setPhoneFlag(true);
        this.setPanFlag(true);
        this.setDobFlag(true);
        this.setIntroCustIdFlag(true);
        this.setSancDtDisFlag(true);
        this.setRoiDisFlag(true);
        this.setTxtAcPrefCrDisFlag(true);
        this.setTxtAcPrefDrDisFlag(true);
        this.setIntAppFrqDisFlag(true);
        this.setJtName1DisFlag(true);
        this.setJtName2DisFlag(true);
        this.setJtName3DisFlag(true);
        this.setJtName4DisFlag(true);
        this.setNomDisFlag(true);
        this.setPagFreqDisFlag(true);
        this.setApplicableRoi("");
        this.setHufFamily("");
        this.setHufFlag(true);
        this.occupationDesc = "";
    }

    public void clearFields() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setAcctType("--Select--");
        this.setSchemeCode("--Select--");
        this.setAppSeqNo("--Select--");
        this.setTitle("--Select--");
        this.setCustName("");
        this.setFatherHusName("");
        this.setPerAdd("");
        this.setCorAdd("");
        this.setPhNo("");
        this.setPanNo("");
        this.setDob(null);
        this.setOccupation("--Select--");
        this.setJtName1("");
        this.setJtName1CustId("");
        this.setJtName2("");
        this.setJtName2CustId("");
        this.setJtName3("");
        this.setJtName3CustId("");
        this.setJtName4("");
        this.setJtName4CustId("");
        this.setNominee("");
        this.setNomRelationship("");
        this.setNomineeAdd("");
        this.setNomineeDob(date);
        this.setMoratoriumPeriod("");
        this.setRateCode("--Select--");
        this.setPaggingFrequence("");
        this.setIntroAcNo("");
        setOldintroAcNo("");
        this.setIntroCustId("");
        this.setIntroducerAcNo("");
        this.setIntroducerAcStatus("");
        this.setIntroducerName("");
        this.setDocName("--Select--");
        this.setDocDetail("");
        this.setSpecialInst("");
        this.setDisburseType("--Select--");
        this.setCalMethod("--Select--");
        this.setInterestAppFreq1("--Select--");
        this.setInterestAppFreq2(" ");
        this.setSancDate(null);
        this.setLimit("");
        this.setGrName("");
        this.setGrRelationship("");
        this.setOprMode("--Select--");
        this.setSubsidyAmt("");
        this.setLoanPeriodDd("");
        this.setLoanPeriodMm("");
        this.setIntCode("--Select--");
        this.setRoi("");
        this.setAcPreferCrDr1("");
        this.setAcPreferCrDr2("");
        this.setCalLevel("--Select--");
        this.setCalOn("--Select--");
        this.setFieldDisFlag(true);
        this.setFieldCalLevelDisFlag(true);
        this.setFlag1(true);
        this.setFlag2(true);
        this.setTitleFlag(true);
        this.setCustNameFlag(true);
        this.setFatherNameFlag(true);
        this.setCorAddFlag(true);
        this.setPerAddFlag(true);
        this.setPhoneFlag(true);
        this.setPanFlag(true);
        this.setDobFlag(true);
        this.setIntroCustIdFlag(true);
        this.setSancDtDisFlag(true);
        this.setRoiDisFlag(true);
        this.setTxtAcPrefCrDisFlag(true);
        this.setTxtAcPrefDrDisFlag(true);
        this.setIntAppFrqDisFlag(true);
        this.setJtName1DisFlag(true);
        this.setJtName2DisFlag(true);
        this.setJtName3DisFlag(true);
        this.setJtName4DisFlag(true);
        this.setNomDisFlag(true);
        this.setPagFreqDisFlag(true);
        this.setApplicableRoi("");
    }

    public void clearFieldsAfterSave() {
        this.setActCategory("--Select--");
        this.setCustId("");
        this.setAcctType("--Select--");
        this.setSchemeCode("--Select--");
        this.setAppSeqNo("--Select--");
        this.setTitle("--Select--");
        this.setCustName("");
        this.setFatherHusName("");
        this.setPerAdd("");
        this.setCorAdd("");
        this.setPhNo("");
        this.setPanNo("");
        this.setGrName("");
        this.setGrRelationship("");
        this.setOprMode("--Select--");
        this.setDob(null);
        this.setOccupation("--Select--");
        this.setJtName1("");
        this.setJtName1CustId("");
        this.setJtName2("");
        this.setJtName2CustId("");
        this.setJtName3("");
        this.setJtName3CustId("");
        this.setJtName4("");
        this.setJtName4CustId("");
        this.setNominee("");
        this.setNomRelationship("");
        this.setNomineeAdd("");
        this.setNomineeDob(date);
        this.setMoratoriumPeriod("");
        this.setRateCode("--Select--");
        this.setPaggingFrequence("");
        this.setIntroAcNo("");
        setOldintroAcNo("");
        this.setIntroCustId("");
        this.setIntroducerAcNo("");
        this.setIntroducerAcStatus("");
        this.setIntroducerName("");
        this.setDocName("--Select--");
        this.setDocDetail("");
        this.setSpecialInst("");
        this.setDisburseType("--Select--");
        this.setCalMethod("--Select--");
        this.setInterestAppFreq1("--Select--");
        this.setInterestAppFreq2(" ");
        this.setSancDate(null);
        this.setLimit("");
        this.setSubsidyAmt("");
        this.setLoanPeriodDd("");
        this.setLoanPeriodMm("");
        this.setIntCode("--Select--");
        this.setRoi("");
        this.setAcPreferCrDr1("");
        this.setAcPreferCrDr2("");
        this.setCalLevel("--Select--");
        this.setCalOn("--Select--");
        this.setFieldDisFlag(true);
        this.setFieldCalLevelDisFlag(true);
        this.setFlag1(true);
        this.setFlag2(true);
        this.setTitleFlag(true);
        this.setCustNameFlag(true);
        this.setFatherNameFlag(true);
        this.setCorAddFlag(true);
        this.setPerAddFlag(true);
        this.setPhoneFlag(true);
        this.setPanFlag(true);
        this.setDobFlag(true);
        this.setIntroCustIdFlag(true);
        this.setSancDtDisFlag(true);
        this.setRoiDisFlag(true);
        this.setTxtAcPrefCrDisFlag(true);
        this.setTxtAcPrefDrDisFlag(true);
        this.setIntAppFrqDisFlag(true);
        this.setJtName1DisFlag(true);
        this.setJtName2DisFlag(true);
        this.setJtName3DisFlag(true);
        this.setJtName4DisFlag(true);
        this.setNomDisFlag(true);
        this.setPagFreqDisFlag(true);
        this.setApplicableRoi("");
        this.occupationDesc = "";
    }

    public void SetValueAcctToScheme() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setCalOn("--Select--");
        this.setCalMethod("--Select--");
        this.setInterestAppFreq1("--Select--");
        this.setInterestAppFreq2("--Select--");
        this.setIntCode("--Select--");
        try {
            if (this.schemeCode.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT SCHEME CODE !!!");
                return;
            }
            List resultList;
            resultList = acOpenFacadeLocal.SetValueAcctToScheme(schemeCode);
            if (!resultList.isEmpty()) {
                this.setDisableOnSchemeCode(true);
                Vector ele = (Vector) resultList.get(0);
                this.OdAllowWithSlab = ele.get(5).toString();
                if(OdAllowWithSlab.equalsIgnoreCase("Y")){
                    this.setRoiDisFlag(true);                    
                } 
                drCrIndFlag = ele.get(7).toString();
                if(drCrIndFlag.equalsIgnoreCase("N")) {
                    this.setTxtAcPrefCrDisFlag(true);
                    this.setTxtAcPrefDrDisFlag(true);
                } 
                if (ele.get(1).toString().equalsIgnoreCase("E")) {
                    this.setCalOn("END");
                } else {
                    this.setCalOn("--Select--");
                }
                this.setCalMethod(ele.get(0).toString());
                if (ele.get(2).toString().equalsIgnoreCase("0") || ele.get(2).toString().equalsIgnoreCase("") || ele.get(2).toString().length() == 0
                        || ele.get(2).toString() == null) {
                    this.setInterestAppFreq1("S");
                } else {
                    this.setInterestAppFreq2(ele.get(2).toString());
                    this.setInterestAppFreq1("C");
                }

                List listForIntTable = new ArrayList();
                listForIntTable = aitr.interestTableCode(schemeCode);
                intCodeList = new ArrayList<SelectItem>();
                intCodeList.add(new SelectItem("--Select--"));
                for (int i = 0; i < listForIntTable.size(); i++) {
                    Vector ele6 = (Vector) listForIntTable.get(i);
                    intCodeList.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }
                this.setIntCode(ele.get(3).toString());
                this.setCalLevel(ele.get(4).toString());
            } else {
                this.setDisableOnSchemeCode(false);
                this.setCalOn("--Select--");
                this.setCalMethod("--Select--");
                this.setInterestAppFreq1("--Select--");
                this.setInterestAppFreq2("--Select--");
                this.setIntCode("--Select--");
                this.setCalLevel("--Select--");
            }
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
    }

    public void docDetail() {
        if (!docName.equalsIgnoreCase("--Select--") && (docDetail.length() != 0)) {
            this.setErrorMessage("");
            return;
        } else if ((!docName.equalsIgnoreCase("--Select--")) && (docDetail.length() == 0)) {
            this.setErrorMessage("PLEASE ENTER DOCUMENT DETAILS.");
            return;
        } else {
            if ((this.docName.equalsIgnoreCase("--Select--")) && (this.oldintroAcNo.equalsIgnoreCase("")
                    || this.oldintroAcNo == null || this.oldintroAcNo.length() == 0)) {
                this.setErrorMessage("PLEASE ENTER EITHER INTRODUCER ACCOUNT NO OR DOCUMENT.");
                return;
            }
        }
    }

    public void printApplicableRoi() {
        this.setErrorMessage("");
        this.setMessage("");

        if (this.roi == null || this.roi.equals("")) {
            this.setErrorMessage("There is no roi defined for this account type.");
            return;
        }
        if (this.acPreferCrDr1 == null || this.acPreferCrDr1.equals("")) {
            this.setErrorMessage("Please define A/C Preferable Dr.");
            return;
        }
        if (this.acPreferCrDr2 == null || this.acPreferCrDr2.equals("")) {
            this.setErrorMessage("Please define A/C Preferable Cr.");
            return;
        }

        this.applicableRoi = String.valueOf(Double.parseDouble(roi) + Double.parseDouble(acPreferCrDr1) - Double.parseDouble(acPreferCrDr2));
    }
    
     public void checkActCatg() {
        if (this.actCategory.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please select account category");
            return;
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {

            setErrorMessage(e.getMessage());
        }
        return "case1";
    }
}
