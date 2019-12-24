/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.DlAcctOpenReg;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.admin.AccountStatusSecurityFacadeRemote;
import com.cbs.facade.admin.TDLienMarkingFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.web.pojo.admin.TdLienMarkingGrid;
import com.cbs.web.pojo.admin.AcStatus;
import com.cbs.web.pojo.admin.SecurityDetail;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

public class DlAccountOpeningRegister extends BaseBean {

    AccountStatusSecurityFacadeRemote statusMaintenanceFacade;
    private String messageDl;
    private String customerId;
    private String phoneNo;
    private String accType;
    private String NameDl;
    private String title;
    private String permanentAdd;
    private String panGirNo;
    private String applicableRoi;
    private String Jt1CustomerId;
    private String Jt2CustomerId;
    private String jtName1;
    private String jtName2;
    private String HFName;
    private String schemeCode;
    private String operatingMode;
    private String actCategory;
    private List<SelectItem> actCategoryList;
    private String orgType;
    private Date dateOfDoc = new Date();
    private String rateCode;
    private String amtSanction;
    private String LoanPeriodMonths;
    private String LoanPeriodDays;
    private String moratoriumPeriod;
    private String corresAdd;
    private String markSecurities;
    private String interestCode;
    private String roi;
    private String intApplicableFreq;
    private String acPreferableDr;
    private String acPreferableCr;
    private String calculationMethod;
    private String disbursementType;
    private String peggingFrequency;
    private String calculationOn;
    private String compoundingFrequency;
    private String calculationLevel;
    private String introId;
    private String IntroducerAccountNo;
    private String oldintroducerAccountNo;
    FtsPostingMgmtFacadeRemote facadeRemote;
    private String IntroducerName;
    private String IntroducerAccount;
    private String IntroducerAcctStatus;
    private boolean disableTitle;
    private boolean disableName;
    private boolean disableHFName;
    private boolean disablePerAdd;
    private boolean disableCorresAdd;
    private boolean disablePhoneNo;
    private boolean disablePenNo;
    private boolean disabledob;
    private boolean disableSave;
    private boolean disableIntroAcct;
    private boolean jt1Disable;
    private boolean jt2Disable;
    private boolean jt1IdDisable;
    private boolean jt2IdDisable;
    private boolean disableFlag;
    private boolean disableCalcLevelFlag;
    private boolean disableCompFreq;
    private boolean disablePaggFreq;
    private boolean disableMarkSecurity;
    private boolean disableRoi;
    private boolean disableCalculationOn;
    boolean markSec;
    Validator validator;
    String acnoIntro;
    private String organizationDesc;
    private Date dateofBirth = new Date();
    Integer m = 0;
    private List<SelectItem> accountTypeList;
    private List<SelectItem> titleList;
    private List<SelectItem> orgTypeList;
    private List<SelectItem> operatingModeList;
    private List<SelectItem> marksSecuritiesList;
    private List<SelectItem> calculationMethodList;
    private List<SelectItem> calculationOnList;
    private List<SelectItem> disburestTypeList;
    private List<SelectItem> rateCodeList;
    private List<SelectItem> calCuLevelList;
    private List<SelectItem> intAppFreqList;
    private List<SelectItem> compFrequencyList;
    private List<SelectItem> schemeCodeList;
    private List<SelectItem> InterestCodeList;
    private List<DlAcctOpenReg> dlAcctOpen;
    AccountOpeningFacadeRemote acOpenFacadeRemote;
    CommonReportMethodsRemote reportMethodRemote;
    TDLienMarkingFacadeRemote lienMark;
    AdvancesInformationTrackingRemote aitr;
    private String hufFamily, acNoLen;
    private boolean hufFlag;
    private boolean txtAcPrefCrDisFlag;            
    private boolean txtAcPrefDrDisFlag;
    private String drCrIndFlag;
    private String OdAllowWithSlab;

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

    public boolean isDisableCalculationOn() {
        return disableCalculationOn;
    }

    public void setDisableCalculationOn(boolean disableCalculationOn) {
        this.disableCalculationOn = disableCalculationOn;
    }

    public boolean isDisableRoi() {
        return disableRoi;
    }

    public void setDisableRoi(boolean disableRoi) {
        this.disableRoi = disableRoi;
    }

    public String getNameDl() {
        return NameDl;
    }

    public void setNameDl(String NameDl) {
        this.NameDl = NameDl;
    }

    public boolean isMarkSec() {
        return markSec;
    }

    public void setMarkSec(boolean markSec) {
        this.markSec = markSec;
    }

    public List<DlAcctOpenReg> getDlAcctOpen() {
        return dlAcctOpen;
    }

    public void setDlAcctOpen(List<DlAcctOpenReg> dlAcctOpen) {
        this.dlAcctOpen = dlAcctOpen;
    }

    public boolean isDisablePaggFreq() {
        return disablePaggFreq;
    }

    public void setDisablePaggFreq(boolean disablePaggFreq) {
        this.disablePaggFreq = disablePaggFreq;
    }

    public boolean isDisableCompFreq() {
        return disableCompFreq;
    }

    public void setDisableCompFreq(boolean disableCompFreq) {
        this.disableCompFreq = disableCompFreq;
    }

    public boolean isDisableMarkSecurity() {
        return disableMarkSecurity;
    }

    public void setDisableMarkSecurity(boolean disableMarkSecurity) {
        this.disableMarkSecurity = disableMarkSecurity;
    }

    public boolean isDisabledob() {
        return disabledob;
    }

    public void setDisabledob(boolean disabledob) {
        this.disabledob = disabledob;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public boolean isJt1IdDisable() {
        return jt1IdDisable;
    }

    public void setJt1IdDisable(boolean jt1IdDisable) {
        this.jt1IdDisable = jt1IdDisable;
    }

    public boolean isJt2IdDisable() {
        return jt2IdDisable;
    }

    public void setJt2IdDisable(boolean jt2IdDisable) {
        this.jt2IdDisable = jt2IdDisable;
    }

    public boolean isJt1Disable() {
        return jt1Disable;
    }

    public void setJt1Disable(boolean jt1Disable) {
        this.jt1Disable = jt1Disable;
    }

    public boolean isJt2Disable() {
        return jt2Disable;
    }

    public void setJt2Disable(boolean jt2Disable) {
        this.jt2Disable = jt2Disable;
    }

    public String getIntroducerAccount() {
        return IntroducerAccount;
    }

    public void setIntroducerAccount(String IntroducerAccount) {
        this.IntroducerAccount = IntroducerAccount;
    }

    public String getIntroducerAccountNo() {
        return IntroducerAccountNo;
    }

    public void setIntroducerAccountNo(String IntroducerAccountNo) {
        this.IntroducerAccountNo = IntroducerAccountNo;
    }

    public String getIntroducerAcctStatus() {
        return IntroducerAcctStatus;
    }

    public void setIntroducerAcctStatus(String IntroducerAcctStatus) {
        this.IntroducerAcctStatus = IntroducerAcctStatus;
    }

    public String getIntroducerName() {
        return IntroducerName;
    }

    public void setIntroducerName(String IntroducerName) {
        this.IntroducerName = IntroducerName;
    }

    public boolean isDisableIntroAcct() {
        return disableIntroAcct;
    }

    public void setDisableIntroAcct(boolean disableIntroAcct) {
        this.disableIntroAcct = disableIntroAcct;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public String getIntroId() {
        return introId;
    }

    public void setIntroId(String introId) {
        this.introId = introId;
    }

    public boolean isDisableCorresAdd() {
        return disableCorresAdd;
    }

    public void setDisableCorresAdd(boolean disableCorresAdd) {
        this.disableCorresAdd = disableCorresAdd;
    }

    public boolean isDisableHFName() {
        return disableHFName;
    }

    public void setDisableHFName(boolean disableHFName) {
        this.disableHFName = disableHFName;
    }

    public boolean isDisableName() {
        return disableName;
    }

    public void setDisableName(boolean disableName) {
        this.disableName = disableName;
    }

    public boolean isDisablePenNo() {
        return disablePenNo;
    }

    public void setDisablePenNo(boolean disablePenNo) {
        this.disablePenNo = disablePenNo;
    }

    public boolean isDisablePerAdd() {
        return disablePerAdd;
    }

    public void setDisablePerAdd(boolean disablePerAdd) {
        this.disablePerAdd = disablePerAdd;
    }

    public boolean isDisablePhoneNo() {
        return disablePhoneNo;
    }

    public void setDisablePhoneNo(boolean disablePhoneNo) {
        this.disablePhoneNo = disablePhoneNo;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public boolean isDisableTitle() {
        return disableTitle;
    }

    public void setDisableTitle(boolean disableTitle) {
        this.disableTitle = disableTitle;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAcPreferableCr() {
        return acPreferableCr;
    }

    public void setAcPreferableCr(String acPreferableCr) {
        this.acPreferableCr = acPreferableCr;
    }

    public String getAcPreferableDr() {
        return acPreferableDr;
    }

    public void setAcPreferableDr(String acPreferableDr) {
        this.acPreferableDr = acPreferableDr;
    }

    public String getCalculationLevel() {
        return calculationLevel;
    }

    public void setCalculationLevel(String calculationLevel) {
        this.calculationLevel = calculationLevel;
    }

    public String getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(String calculationMethod) {
        this.calculationMethod = calculationMethod;
    }

    public String getCalculationOn() {
        return calculationOn;
    }

    public void setCalculationOn(String calculationOn) {
        this.calculationOn = calculationOn;
    }

    public String getCompoundingFrequency() {
        return compoundingFrequency;
    }

    public void setCompoundingFrequency(String compoundingFrequency) {
        this.compoundingFrequency = compoundingFrequency;
    }

    public String getDisbursementType() {
        return disbursementType;
    }

    public void setDisbursementType(String disbursementType) {
        this.disbursementType = disbursementType;
    }

    public String getIntApplicableFreq() {
        return intApplicableFreq;
    }

    public void setIntApplicableFreq(String intApplicableFreq) {
        this.intApplicableFreq = intApplicableFreq;
    }

    public String getInterestCode() {
        return interestCode;
    }

    public void setInterestCode(String interestCode) {
        this.interestCode = interestCode;
    }

    public String getMarkSecurities() {
        return markSecurities;
    }

    public void setMarkSecurities(String markSecurities) {
        this.markSecurities = markSecurities;
    }

    public String getMoratoriumPeriod() {
        return moratoriumPeriod;
    }

    public void setMoratoriumPeriod(String moratoriumPeriod) {
        this.moratoriumPeriod = moratoriumPeriod;
    }

    public String getPeggingFrequency() {
        return peggingFrequency;
    }

    public void setPeggingFrequency(String peggingFrequency) {
        this.peggingFrequency = peggingFrequency;
    }

    public String getLoanPeriodDays() {
        return LoanPeriodDays;
    }

    public void setLoanPeriodDays(String LoanPeriodDays) {
        this.LoanPeriodDays = LoanPeriodDays;
    }

    public String getLoanPeriodMonths() {
        return LoanPeriodMonths;
    }

    public void setLoanPeriodMonths(String LoanPeriodMonths) {
        this.LoanPeriodMonths = LoanPeriodMonths;
    }

    public String getAmtSanction() {
        return amtSanction;
    }

    public void setAmtSanction(String amtSanction) {
        this.amtSanction = amtSanction;
    }

    public Date getDateOfDoc() {
        return dateOfDoc;
    }

    public void setDateOfDoc(Date dateOfDoc) {
        this.dateOfDoc = dateOfDoc;
    }

    public String getOperatingMode() {
        return operatingMode;
    }

    public void setOperatingMode(String operatingMode) {
        this.operatingMode = operatingMode;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public List<SelectItem> getInterestCodeList() {
        return InterestCodeList;
    }

    public void setInterestCodeList(List<SelectItem> InterestCodeList) {
        this.InterestCodeList = InterestCodeList;
    }

    public List<SelectItem> getSchemeCodeList() {
        return schemeCodeList;
    }

    public void setSchemeCodeList(List<SelectItem> schemeCodeList) {
        this.schemeCodeList = schemeCodeList;
    }

    public boolean isDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(boolean disableFlag) {
        this.disableFlag = disableFlag;
    }

    public boolean isDisableCalcLevelFlag() {
        return disableCalcLevelFlag;
    }

    public void setDisableCalcLevelFlag(boolean disableCalcLevelFlag) {
        this.disableCalcLevelFlag = disableCalcLevelFlag;
    }

    public List<SelectItem> getCalCuLevelList() {
        return calCuLevelList;
    }

    public void setCalCuLevelList(List<SelectItem> calCuLevelList) {
        this.calCuLevelList = calCuLevelList;
    }

    public List<SelectItem> getCalculationMethodList() {
        return calculationMethodList;
    }

    public void setCalculationMethodList(List<SelectItem> calculationMethodList) {
        this.calculationMethodList = calculationMethodList;
    }

    public List<SelectItem> getCalculationOnList() {
        return calculationOnList;
    }

    public void setCalculationOnList(List<SelectItem> calculationOnList) {
        this.calculationOnList = calculationOnList;
    }

    public List<SelectItem> getCompFrequencyList() {
        return compFrequencyList;
    }

    public void setCompFrequencyList(List<SelectItem> compFrequencyList) {
        this.compFrequencyList = compFrequencyList;
    }

    public List<SelectItem> getDisburestTypeList() {
        return disburestTypeList;
    }

    public void setDisburestTypeList(List<SelectItem> disburestTypeList) {
        this.disburestTypeList = disburestTypeList;
    }

    public List<SelectItem> getIntAppFreqList() {
        return intAppFreqList;
    }

    public void setIntAppFreqList(List<SelectItem> intAppFreqList) {
        this.intAppFreqList = intAppFreqList;
    }

    public List<SelectItem> getMarksSecuritiesList() {
        return marksSecuritiesList;
    }

    public void setMarksSecuritiesList(List<SelectItem> marksSecuritiesList) {
        this.marksSecuritiesList = marksSecuritiesList;
    }

    public List<SelectItem> getRateCodeList() {
        return rateCodeList;
    }

    public void setRateCodeList(List<SelectItem> rateCodeList) {
        this.rateCodeList = rateCodeList;
    }

    public List<SelectItem> getOperatingModeList() {
        return operatingModeList;
    }

    public void setOperatingModeList(List<SelectItem> operatingModeList) {
        this.operatingModeList = operatingModeList;
    }

    public List<SelectItem> getOrgTypeList() {
        return orgTypeList;
    }

    public void setOrgTypeList(List<SelectItem> orgTypeList) {
        this.orgTypeList = orgTypeList;
    }

    public List<SelectItem> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<SelectItem> titleList) {
        this.titleList = titleList;
    }

    public String getHFName() {
        return HFName;
    }

    public void setHFName(String HFName) {
        this.HFName = HFName;
    }

    public String getJt1CustomerId() {
        return Jt1CustomerId;
    }

    public void setJt1CustomerId(String Jt1CustomerId) {
        this.Jt1CustomerId = Jt1CustomerId;
    }

    public String getJt2CustomerId() {
        return Jt2CustomerId;
    }

    public void setJt2CustomerId(String Jt2CustomerId) {
        this.Jt2CustomerId = Jt2CustomerId;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getCorresAdd() {
        return corresAdd;
    }

    public void setCorresAdd(String corresAdd) {
        this.corresAdd = corresAdd;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(Date dateofBirth) {
        this.dateofBirth = dateofBirth;
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

    public String getMessageDl() {
        return messageDl;
    }

    public void setMessageDl(String messageDl) {
        this.messageDl = messageDl;
    }

    public String getPanGirNo() {
        return panGirNo;
    }

    public void setPanGirNo(String panGirNo) {
        this.panGirNo = panGirNo;
    }

    public String getPermanentAdd() {
        return permanentAdd;
    }

    public void setPermanentAdd(String permanentAdd) {
        this.permanentAdd = permanentAdd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOldintroducerAccountNo() {
        return oldintroducerAccountNo;
    }

    public void setOldintroducerAccountNo(String oldintroducerAccountNo) {
        this.oldintroducerAccountNo = oldintroducerAccountNo;
    }

    public String getApplicableRoi() {
        return applicableRoi;
    }

    public void setApplicableRoi(String applicableRoi) {
        this.applicableRoi = applicableRoi;
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

    public String getOrganizationDesc() {
        return organizationDesc;
    }

    public void setOrganizationDesc(String organizationDesc) {
        this.organizationDesc = organizationDesc;
    }
    
    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat y_m_d_h_m_sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public DlAccountOpeningRegister() {

        try {
            acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            reportMethodRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            statusMaintenanceFacade = (AccountStatusSecurityFacadeRemote) ServiceLocator.getInstance().lookup("AccountStatusSecurityFacade");
            facadeRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            lienMark = (TDLienMarkingFacadeRemote) ServiceLocator.getInstance().lookup("TDLienMarkingFacade");
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            this.setAcNoLen(getAcNoLength());
            setTodayDate(sdf.format(date));
            setMessageDl("");
            dlAcctOpen = new ArrayList<DlAcctOpenReg>();
            accountTypeList = new ArrayList<SelectItem>();
            accountTypeList.add(new SelectItem("0", ""));
            schemeCodeList = new ArrayList<SelectItem>();
            schemeCodeList.add(new SelectItem("0", ""));

            getDDValues();
            resetPage();
            resetValueDl();

            setDisableFlag(true);
            setDisableCorresAdd(true);
            setDisableCalcLevelFlag(true);
            setDisableHFName(true);
            setDisableName(true);
            setDisablePenNo(true);
            setDisablePerAdd(true);
            setDisablePhoneNo(true);
            setDisableSave(true);
            setDisableTitle(true);
            setDisabledob(true);
            setJt1Disable(true);
            setJt2Disable(true);
            setDisableIntroAcct(true);
            setDisableMarkSecurity(true);
            setDisableCompFreq(true);
            setDisablePaggFreq(true);
            setDisableRoi(true);
            setTxtAcPrefCrDisFlag(true);
            setTxtAcPrefDrDisFlag(true);
            setDisableCalculationOn(true);
            /************** security Details ***********************/
            setMessageDl("");
            getTableValues();
            dlAcctOpen = new ArrayList<DlAcctOpenReg>();
            setEstimationDateLbl("Estimation Date");
            setValuationAmtLbl("Valuation Amt");
            setRevalutionDateLbl("Revalution Date");
            securityDesc1List = new ArrayList<SelectItem>();
            securityDesc1List.add(new SelectItem("0", " "));
            securityDesc2List = new ArrayList<SelectItem>();
            securityDesc2List.add(new SelectItem("0", " "));
            securityDesc3List = new ArrayList<SelectItem>();
            securityDesc3List.add(new SelectItem("0", " "));
            setSavedisableSec(true);
            /**FOR TD LIEN MARKING**/
            this.setErrorMessage("");
            this.setMessage("");
            lienMarkOptionList = new ArrayList<SelectItem>();
            lienMarkOptionList.add(new SelectItem("--Select--"));
            lienMarkOptionList.add(new SelectItem("Yes"));
            lienMarkOptionList.add(new SelectItem("No"));
            validator = new Validator();
            this.setFlag1(true);
            /** FOR ACCOUNT STATUS **/
            setReportDate(sdf.parse(getTodayDate()));
            reFresh();
            setWefDate(sdf.parse(getTodayDate()));
            setWefDate1(ymd.format(wefDate));
            this.setHufFlag(true);
        } catch (ApplicationException e) {
            setErrorMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }
    List schemeCoderesultList;

    public void setScheme() {
        try {
            schemeCoderesultList = acOpenFacadeRemote.schemeCodeCombo(accType);
            /**Added by Rohit Krishna Gupta on 09/05/2011**/
            this.setAmtSanction("0");
            this.setLoanPeriodDays("0");
            this.setLoanPeriodMonths("0");
            this.setMoratoriumPeriod("0");
            this.setAcPreferableCr("0");
            this.setAcPreferableDr("0");
            this.setPeggingFrequency("0");
            /****/
            if (schemeCoderesultList.size() < 0) {
                schemeCodeList = new ArrayList<SelectItem>();
                schemeCodeList.add(new SelectItem("0", ""));
                return;
            } else {
                schemeCodeList = new ArrayList<SelectItem>();
                schemeCodeList.add(new SelectItem("0", ""));
                for (int i = 0; i < schemeCoderesultList.size(); i++) {
                    Vector ele = (Vector) schemeCoderesultList.get(i);
                    schemeCodeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void getDDValues() {
        try {
            titleList = new ArrayList<SelectItem>();
            titleList.add(new SelectItem("0", ""));
            titleList.add(new SelectItem("Mr.", "Mr."));
            titleList.add(new SelectItem("Mrs.", "Mrs."));
            titleList.add(new SelectItem("Miss", "Miss"));
            titleList.add(new SelectItem("Kumari", "Kumari"));
            titleList.add(new SelectItem("Master", "Master"));
            titleList.add(new SelectItem("M/s", "M/s"));
            titleList.add(new SelectItem("Other", "Other"));

            marksSecuritiesList = new ArrayList<SelectItem>();
            marksSecuritiesList.add(new SelectItem("1", "Recurring Deposite/Mini Deposite"));
            marksSecuritiesList.add(new SelectItem("2", "Term Deposite"));
            marksSecuritiesList.add(new SelectItem("3", "Other"));


            calculationMethodList = new ArrayList<SelectItem>();
            calculationMethodList.add(new SelectItem("0", ""));
            calculationMethodList.add(new SelectItem("D", "Daily"));
            calculationMethodList.add(new SelectItem("M", "Monthly"));
            calculationMethodList.add(new SelectItem("Q", "Quarterly"));
            calculationMethodList.add(new SelectItem("H", "Half Yearly"));
            calculationMethodList.add(new SelectItem("Y", "Yearly"));

            calculationOnList = new ArrayList<SelectItem>();
            calculationOnList.add(new SelectItem("0", ""));
            calculationOnList.add(new SelectItem("Min", "Minimum"));
            calculationOnList.add(new SelectItem("Max", "Maximum"));
            calculationOnList.add(new SelectItem("Avg", "Average"));
            calculationOnList.add(new SelectItem("End", "End"));

            disburestTypeList = new ArrayList<SelectItem>();
            disburestTypeList.add(new SelectItem("0", ""));
            disburestTypeList.add(new SelectItem("S", "Single"));
            disburestTypeList.add(new SelectItem("M", "Multiple"));

            rateCodeList = new ArrayList<SelectItem>();
//            rateCodeList.add(new SelectItem("0", ""));
//            rateCodeList.add(new SelectItem("Ab", "Absolute Fix"));
//            rateCodeList.add(new SelectItem("Fl", "Floating"));
//            rateCodeList.add(new SelectItem("Fi", "Fixed"));
            List resultList1 = reportMethodRemote.getRefRecList("202");
            if (!resultList1.isEmpty()) {
                for (int i = 0; i < resultList1.size(); i++) {
                    Vector ele1 = (Vector) resultList1.get(i);
                    rateCodeList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }

            calCuLevelList = new ArrayList<SelectItem>();
            calCuLevelList.add(new SelectItem("0", ""));
            calCuLevelList.add(new SelectItem("L", "Limit/Outstanding"));
            calCuLevelList.add(new SelectItem("S", "Sanction Limit"));

            intAppFreqList = new ArrayList<SelectItem>();
            intAppFreqList.add(new SelectItem("0", ""));
            intAppFreqList.add(new SelectItem("S", "Simple"));
            intAppFreqList.add(new SelectItem("C", "Compound"));

            compFrequencyList = new ArrayList<SelectItem>();
            compFrequencyList.add(new SelectItem("0", ""));
            compFrequencyList.add(new SelectItem("M", "Monthly"));
            compFrequencyList.add(new SelectItem("Q", "Quarterly"));
            compFrequencyList.add(new SelectItem("H", "Half Yearly"));
            compFrequencyList.add(new SelectItem("H", "Yearly"));


            List resultList = new ArrayList();
            resultList = acOpenFacadeRemote.getOrganizationType();
            if (resultList.size() < 0) {
                orgTypeList = new ArrayList<SelectItem>();
                orgTypeList.add(new SelectItem("0", ""));
                return;
            } else {
                orgTypeList = new ArrayList<SelectItem>();
                orgTypeList.add(new SelectItem("0", ""));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    orgTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

            List operatingModeresultList = reportMethodRemote.getOperatingModeDetails();
            if (operatingModeresultList.size() < 0) {
                operatingModeList = new ArrayList<SelectItem>();
                operatingModeList.add(new SelectItem("0", ""));
                return;
            } else {
                operatingModeList = new ArrayList<SelectItem>();
                operatingModeList.add(new SelectItem("0", ""));
                for (int i = 0; i < operatingModeresultList.size(); i++) {
                    Vector ele = (Vector) operatingModeresultList.get(i);
                    operatingModeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }



            List accountTyperesultList = reportMethodRemote.getAcctTypebyDLNature();
            if (accountTyperesultList.size() < 0) {
                accountTypeList = new ArrayList<SelectItem>();
                accountTypeList.add(new SelectItem("0", ""));
                return;
            } else {

                accountTypeList = new ArrayList<SelectItem>();
                accountTypeList.add(new SelectItem("0", ""));
                for (int i = 0; i < accountTyperesultList.size(); i++) {
                    Vector ele = (Vector) accountTyperesultList.get(i);
                    accountTypeList.add(new SelectItem(ele.get(0).toString(),"["+ele.get(0).toString()+"] " +ele.get(1).toString()));
                }
            }
            
          List accounttCategList = reportMethodRemote.getActCategoryDetails();
            if (accounttCategList.size() < 0) {
                actCategoryList = new ArrayList<SelectItem>();
                actCategoryList.add(new SelectItem("0", ""));
                return;
            } else {
                actCategoryList = new ArrayList<SelectItem>();
                actCategoryList.add(new SelectItem("0", ""));
                for (int i = 0; i < accounttCategList.size(); i++) {
                    Vector ele = (Vector) accounttCategList.get(i);
                     actCategoryList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
            
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void getAccountDetails() {

        String p1;
        String p2;
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            setMessageDl("");
            if (customerId.equalsIgnoreCase("") || customerId == null) {
                setMessageDl("Please Enter the Customer ID.");
                return;
            } else {
                Matcher customerIdCheck = p.matcher(customerId);
                if (!customerIdCheck.matches()) {
                    setCustomerId("");
                    setMessageDl("Please Enter Numeric Value for Customer Id.");
                    return;
                }
            }
            List resultList = new ArrayList();
            resultList = acOpenFacadeRemote.getExistingCustDetail(customerId);
            if (resultList.isEmpty()) {
                resetValueDl();
                setMessageDl("This Customer Id has been suspended or does not exists or unauthorized");
                setCustomerId("");
                setDisableFlag(true);
                setDisableCorresAdd(true);
                setDisableCalcLevelFlag(true);
                setDisableHFName(true);
                setDisableName(true);
                setDisablePenNo(true);
                setDisablePerAdd(true);
                setDisablePhoneNo(true);
                setDisableSave(true);
                setDisableTitle(true);
                setDisabledob(true);
                setDisableCompFreq(true);
                setDisableMarkSecurity(true);
                setDisableRoi(true);
                setTxtAcPrefCrDisFlag(true);
                setTxtAcPrefDrDisFlag(true);
                setDisableCalculationOn(true);
                return;
            } else {
                String temp = customerId;
                resetValueDl();
                setCustomerId(temp);
                setDisableFlag(false);
                setDisableCalcLevelFlag(false);
                setDisableSave(false);
                setDisableMarkSecurity(false);
                Vector ele = (Vector) resultList.get(0);
                if (!ele.get(0).toString().equalsIgnoreCase("") || ele.get(0).toString().length() != 0) {
                    setTitle(ele.get(0).toString());
                } else {
                    setDisableTitle(false);
                }
                if (ele.get(1).toString().length() != 0 || !ele.get(1).toString().equalsIgnoreCase("") || ele.get(1) != null) {
//                    setNameDl(ele.get(1).toString());
                    String custName = ele.get(1).toString().trim() + " " + ele.get(11).toString().trim();
                    custName = custName.trim() + " " + ele.get(12).toString().trim();
                    setNameDl(custName.trim());
                } else {
                    setDisableName(false);
                }

                p1 = ele.get(2).toString();
                p2 = ele.get(3).toString();
                if (p1.equalsIgnoreCase("") || p1.length() == 0) {
                    p1 = "";
                }

                if (p2.equalsIgnoreCase("") || p2.length() == 0) {
                    p2 = "";
                }
                String p3 = p1 + p2;
                if (p3.equalsIgnoreCase("")) {
                    setPermanentAdd("");
                    setDisablePerAdd(false);
                } else {
                    setPermanentAdd(p3);
                }

                String cr1 = ele.get(4).toString();
                String cr2 = ele.get(5).toString();
                if (cr1.equalsIgnoreCase("") || cr1.length() == 0) {
                    cr1 = "";
                }

                if (cr2.equalsIgnoreCase("") || cr2.length() == 0) {
                    cr2 = "";
                }
                String c3 = cr1 + cr2;
                if (c3.equalsIgnoreCase("")) {
                    setCorresAdd("");
                    setDisableCorresAdd(false);
                } else {
                    setCorresAdd(c3);
                }

                if (!ele.get(6).toString().equalsIgnoreCase("") || ele.get(6).toString().length() != 0) {
                    //setHFName(ele.get(6).toString());
                    String FatherHusName = ele.get(6).toString().trim() + " " + ele.get(13).toString().trim();
                    FatherHusName = FatherHusName.trim() + " " + ele.get(14).toString().trim();
                    setHFName(FatherHusName.trim());
                } else {
                    setDisableHFName(true);
                }
                if (!ele.get(7).toString().equalsIgnoreCase("") || ele.get(7).toString().length() != 0) {
                    setPhoneNo(ele.get(7).toString());
                } else {
                    setDisablePhoneNo(false);
                }
                if (!ele.get(8).toString().equalsIgnoreCase("") || ele.get(8).toString().length() != 0) {
                    setPanGirNo(ele.get(8).toString());
                } else {
                    setDisablePenNo(false);
                }
                if (!ele.get(9).toString().equalsIgnoreCase("") || ele.get(9).toString().length() != 0) {
                    setDateofBirth(sdf.parse(ele.get(9).toString()));
                } else {
                    setDisabledob(false);
                }
                if (!ele.get(10).toString().equalsIgnoreCase("") || ele.get(10).toString().length() != 0) {
                    setIntroId(ele.get(10).toString());
                }
                setIntroId(ele.get(10).toString());
                setAccType("DL");
                //---- Added by Manish Kumar
                this.orgType = "";
                this.organizationDesc = "";
                List selectList = acOpenFacadeRemote.getOccupation(this.customerId);
                if(!selectList.isEmpty()){
                    Vector vec = (Vector) selectList.get(0);
                    this.orgType = vec.get(0).toString();
                    this.organizationDesc = vec.get(1).toString();
                }else{
                    this.orgType = "26";
                }
                //-----

            }
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void setCorressAdd() {
        setCorresAdd(permanentAdd);
    }

    public void SetValueAcctToScheme() {
        try {
            List resultList;

            resultList = acOpenFacadeRemote.SetValueAcctToScheme(schemeCode);

            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                if (ele.get(1).toString().equalsIgnoreCase("E")) {
                    setCalculationOn("End");
                    setDisableCalculationOn(true);
                } else {
                    setDisableCalculationOn(false);
                }
                setCalculationMethod(ele.get(0).toString());
                if (ele.get(2).toString().equalsIgnoreCase("0") || ele.get(2).toString().equalsIgnoreCase("") || ele.get(2).toString().length() == 0
                        || ele.get(2).toString() == null) {
                    setIntApplicableFreq("S");
                } else {
                    setCompoundingFrequency(ele.get(2).toString());
                    setIntApplicableFreq("C");
                }
                List InterestCoderesultList = aitr.interestTableCode(schemeCode);
                if (InterestCoderesultList.size() < 0) {
                    InterestCodeList = new ArrayList<SelectItem>();
                    InterestCodeList.add(new SelectItem("0", ""));
                    return;
                } else {
                    InterestCodeList = new ArrayList<SelectItem>();
                    InterestCodeList.add(new SelectItem("0", ""));
                    for (int i = 0; i < InterestCoderesultList.size(); i++) {
                        Vector ele1 = (Vector) InterestCoderesultList.get(i);
                        InterestCodeList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                    }
                }
                setInterestCode(ele.get(3).toString());
                setCalculationLevel(ele.get(4).toString());
                this.OdAllowWithSlab = ele.get(5).toString();
                if(OdAllowWithSlab.equalsIgnoreCase("Y")){
                    setDisableMarkSecurity(true);                    
                } else{
                    setDisableMarkSecurity(false);
                }
                drCrIndFlag = ele.get(7).toString();
                if(drCrIndFlag.equalsIgnoreCase("N")) {
                    this.setTxtAcPrefCrDisFlag(true);
                    this.setTxtAcPrefDrDisFlag(true);
                }
                setDisableCalcLevelFlag(true);
            }

        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    /*************  added By Zeeshan, modified by Ankit ************/
    public void getIntroducerAcctDetails() {
        try {

            setMessageDl("");
            if (oldintroducerAccountNo.equalsIgnoreCase("") || oldintroducerAccountNo == null) {
                setMessageDl("Please Enter Introducer Account No");
                return;
            } else {
                //if (oldintroducerAccountNo.length() < 12) {
                if (!this.oldintroducerAccountNo.equalsIgnoreCase("") && ((this.oldintroducerAccountNo.length() != 12) && (this.oldintroducerAccountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {    
                    setMessageDl("Please insert a valid Introducer account no.");
                    return;
                } else if (!validator.validateStringAllNoSpace(oldintroducerAccountNo)) {
                    setMessageDl("Please insert a valid Introducer account no.");
                    return;
                }
                String[] values = null;
                String resultList;
                IntroducerAccountNo = facadeRemote.getNewAccountNumber(oldintroducerAccountNo);
                if (IntroducerAccountNo.equalsIgnoreCase("A/c number does not exist")) {
                    setMessageDl("this introducer a/c number does not exist");
                    return;
                }
                resultList = acOpenFacadeRemote.introducerAcDetailForDlFDAc(introId, IntroducerAccountNo);
                if (resultList.equalsIgnoreCase("false")) {
                    setMessageDl("");
                    return;
                }
                if (resultList.substring(0, 4).equalsIgnoreCase("true")) {
                    setIntroducerAccount(resultList.substring(4, 16));
                    String spliter = ":";
                    values = resultList.substring(16).split(spliter);
                    setIntroducerName(values[1]);
                    setIntroducerAcctStatus(values[2]);
                    if (values[2].equalsIgnoreCase("CLOSED")) {
                        setMessageDl("Account has been Closed");
                        setIntroducerAccountNo("");
                        setOldintroducerAccountNo("");
                        setIntroducerName("");
                        setIntroducerAccount("");
                        setIntroducerAcctStatus("");
                    } else {
                        acnoIntro = resultList.substring(4, 16);

                    }
                } else {
                    setMessageDl(resultList);
                    setIntroducerName("");
                    setIntroducerAccount("");
                    setIntroducerAcctStatus("");
                }
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    /********** End************************/
    public void setJtName1() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        setMessageDl("");
        if (Jt1CustomerId == null || Jt1CustomerId.equalsIgnoreCase("")) {
            return;
        } else {
            Matcher Jt1CustomerIdCheck = p.matcher(Jt1CustomerId);
            if (!Jt1CustomerIdCheck.matches()) {
                setJtName1("");
                setMessageDl("Enter Numeric Value for JTName1 ID.");
                return;
            }
            if (customerId.equalsIgnoreCase(Jt1CustomerId)) {
                setJtName1("");
                setMessageDl("JtName1 ID And Customer Id can not be Same");
                return;
            }
        }
        try {
            List resultList;
            resultList = acOpenFacadeRemote.getJtName(Integer.parseInt(Jt1CustomerId));
            if (resultList.isEmpty()) {
                setJtName1("");
                setJt1CustomerId("");
                setMessageDl("This Customer Id has been suspended or does not exists or unauthorized");
            } else {
                Vector ele = (Vector) resultList.get(0);
                if (!ele.get(0).toString().equalsIgnoreCase("")) {
                    setJtName1(ele.get(0).toString().trim() + " " + ele.get(1).toString().trim() + " " + ele.get(2).toString().trim());
                } else {
                    setJtName1("");
                    setJt1CustomerId("");
                    setMessageDl("This Customer Id has been suspended or does not exists or unauthorized");
                }
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void setJtName2() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        setMessageDl("");
        if (Jt2CustomerId == null || Jt2CustomerId.equalsIgnoreCase("")) {
            return;
        } else {
            Matcher Jt2CustomerIdCheck = p.matcher(Jt2CustomerId);
            if (!Jt2CustomerIdCheck.matches()) {
                setJtName2("");
                setMessageDl("Enter Numeric Value for JTName2 ID.");
            }
            if (customerId.equalsIgnoreCase(Jt2CustomerId)) {
                setJtName2("");
                setMessageDl("JtName2 ID And Customer Id can not be Same");
                return;
            }
            if (Jt2CustomerId.equalsIgnoreCase(Jt1CustomerId)) {
                setJtName2("");
                setMessageDl("JtName2 ID And JtName1 Id can not be Same");
                return;
            }
        }
        try {
            List resultList;
            resultList = acOpenFacadeRemote.getJtName(Integer.parseInt(Jt2CustomerId));
            if (resultList.isEmpty()) {
                setJtName2("");
                setJt2CustomerId("");
                setMessageDl("This Customer Id has been suspended or does not exists or unauthorized");
            } else {
                Vector ele = (Vector) resultList.get(0);
                if (!ele.get(0).toString().equalsIgnoreCase("")) {
                    setJtName2(ele.get(0).toString().trim() + " " + ele.get(1).toString().trim() + " " + ele.get(2).toString().trim());
                } else {
                    setJtName2("");
                    setJt2CustomerId("");
                    setMessageDl("This Customer Id has been suspended or does not exists or unauthorized");
                }
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void setRoi() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        setMessageDl("");
        if (amtSanction == null || amtSanction.equalsIgnoreCase("")) {
            setMessageDl("Please Enter the Amount Sanctioned");
            return;
        } else {
            Matcher amtSanctionCheck = p.matcher(amtSanction);
            if (!amtSanctionCheck.matches()) {
                setAmtSanction("");
                setMessageDl("Enter Numeric Value For Amount Sanctioned");
                return;
            }
            if (Float.parseFloat(amtSanction) < 0) {
                setAmtSanction("");
                setMessageDl("Enter Valid Value For Amount Sanctioned");
                return;
            }
        }
        if (interestCode == null || interestCode.equalsIgnoreCase("0")) {
            setMessageDl("Please Select the Interest Code");
            return;
        }
        try {
            String resultList;
            resultList = acOpenFacadeRemote.GetROIForLoanDLAcOpen(interestCode, Float.parseFloat(amtSanction), ymd.format(sdf.parse(getTodayDate())));
            if (resultList.equalsIgnoreCase("Data does not exists")) {
                setMessageDl(resultList);
                setDisableRoi(false);
            } else {
                setRoi(resultList);
                setDisableRoi(true);
                if(drCrIndFlag.equalsIgnoreCase("N")){
                    this.setTxtAcPrefCrDisFlag(true);
                    this.setTxtAcPrefDrDisFlag(true);
                } else {
                    this.setTxtAcPrefCrDisFlag(false);
                    this.setTxtAcPrefDrDisFlag(false);
                }
                setRoi(resultList);
            }

        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void ddOperationModeDisableJtName() {
        this.setHufFamily("");
        this.setHufFlag(true);
        try {
            if (operatingMode.equalsIgnoreCase("0")) {
                this.setMessageDl("Please select operating mode !!!");
                return;
            }
            
            if(operatingMode.equalsIgnoreCase("18")){
                setJt1IdDisable(false);
                setJt2IdDisable(true);
                this.setHufFlag(false);
            }else{
                setJt1IdDisable(false);
                setJt2IdDisable(false);
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void disableCompFreq() {
        if (intApplicableFreq.equalsIgnoreCase("0")) {
            setDisableCompFreq(true);
        } else if (intApplicableFreq.equalsIgnoreCase("S")) {
            setDisableCompFreq(true);
        } else if (intApplicableFreq.equalsIgnoreCase("C")) {
            setDisableCompFreq(false);
        }
    }

    public void disablePaggingFreq() {
        if (rateCode.equalsIgnoreCase("0")) {
            setDisablePaggFreq(true);
        } else if (rateCode.equalsIgnoreCase("Ab")) {
            setDisablePaggFreq(true);
        } else if (rateCode.equalsIgnoreCase("Fl")) {
            setDisablePaggFreq(true);
        } else if (rateCode.equalsIgnoreCase("Fi")) {
            setDisablePaggFreq(false);
        }
    }

    public void saveAction() throws ParseException {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (valiadtions().equalsIgnoreCase("false")) {
            return;
        }
        if(!OdAllowWithSlab.equalsIgnoreCase("Y")){
            if (dlAcctOpen.isEmpty()) {
                setMessageDl("DL Table is Blank.");
                return;
            }
        }
        if (LoanPeriodMonths != null || !LoanPeriodMonths.equalsIgnoreCase("")) {
            if (LoanPeriodDays == null || LoanPeriodDays.equalsIgnoreCase("")) {
                LoanPeriodDays = "0";
            } else {
                if (!LoanPeriodDays.matches("[0-9]*")) {
                    setLoanPeriodDays("");
                    setMessageDl("Enter Numeric Value for Loan Period Days.");
                    return;
                }
            }
        }
        if (intApplicableFreq.equalsIgnoreCase("S")) {
            compoundingFrequency = "0";
        }
        if (rateCode.equalsIgnoreCase("Ab")) {
            peggingFrequency = "0";
        } else if (rateCode.equalsIgnoreCase("Fl")) {
            peggingFrequency = "0";
        } else if (rateCode.equalsIgnoreCase("Fi")) {
            if(peggingFrequency.equalsIgnoreCase("0")){
                setMessageDl("Please fill the Pegging Frequency(in MM)");
                return;
            }
        }
        if (acPreferableDr == null || acPreferableDr.equalsIgnoreCase("")) {
            acPreferableDr = "0";
        } else {
            Matcher amtSanctionCheck = p.matcher(acPreferableDr);
            if (!amtSanctionCheck.matches()) {
                setMessageDl("Enter Numeric Value For A/ c Preferable Dr.");
                return;
            }
            if (this.acPreferableDr.contains(".")) {
                if (this.acPreferableDr.indexOf(".") != this.acPreferableDr.lastIndexOf(".")) {
                    this.setMessageDl("Invalid A/ c Preferable Dr.Please Fill The A/ c Preferable Dr. Correctly.");
                    return;
                }
            }

            if (Float.parseFloat(acPreferableDr) < 0) {
                setMessageDl("Enter Valid Value For A/ c Preferable Dr.");
                return;
            }
        }
        if (acPreferableCr == null || acPreferableCr.equalsIgnoreCase("")) {
            acPreferableCr = "0";
        } else {
            Matcher amtSanctionCheck = p.matcher(acPreferableCr);
            if (!amtSanctionCheck.matches()) {
                setMessageDl("Enter Numeric Value For A/ c Preferable Cr.");
                return;
            }
            if (this.acPreferableCr.contains(".")) {
                if (this.acPreferableCr.indexOf(".") != this.acPreferableCr.lastIndexOf(".")) {
                    this.setMessageDl("Invalid A/ c Preferable Cr.Please Fill A/ c Preferable Cr. Correctly.");
                    return;
                }
            }
            if (Float.parseFloat(acPreferableCr) < 0) {
                setMessageDl("Enter Valid Value For A/ c Preferable Cr.");
                return;
            }

        }
        if (moratoriumPeriod == null || moratoriumPeriod.equalsIgnoreCase("")) {
            moratoriumPeriod = "0";
        }
        
        if(actCategory.equalsIgnoreCase("HUF") || operatingMode.equalsIgnoreCase("18")){
            if(!(actCategory.equalsIgnoreCase("HUF") && operatingMode.equalsIgnoreCase("18"))){
                setMessageDl("Please Select Proper Account Category And Operation Mode");
                return;
            }
        }
        
        if(operatingMode.equalsIgnoreCase("18")){
            if ((hufFamily.equals("")) || (hufFamily == null)) {                
                setMessageDl("Huf Family Detail Can't Be Blank In Case Of HUF Account.");
                return;
            }
        }
        
        Integer age;
        try {
            setMessageDl("");
            String custAge = sdf.format(dateofBirth);
            int yearDOB = Integer.parseInt(custAge.substring(6));

            DateFormat dateFormat = new SimpleDateFormat("yyyy");
            java.util.Date date1 = new java.util.Date();
            int thisYear = Integer.parseInt(dateFormat.format(date1));
            age = thisYear - yearDOB;
            String agCode = "01";
            String resultList;
            String catvalue = "";
            if (jtName1 == null || jtName1.equalsIgnoreCase("")) {
                jtName1 = "";

            }
            if (jtName2 == null || jtName2.equalsIgnoreCase("")) {
                jtName2 = "";
            }
            float rateofInterest = 0;
            if (Float.parseFloat(acPreferableCr) > 0) {
                rateofInterest = Float.parseFloat(roi) - Float.parseFloat(acPreferableCr);
                roi = Float.toString(rateofInterest);
            }
            if (Float.parseFloat(acPreferableDr) > 0) {
                rateofInterest = Float.parseFloat(roi) + Float.parseFloat(acPreferableDr);
                roi = Float.toString(rateofInterest);
            }            
            resultList = acOpenFacadeRemote.saveDlAcctOpenRegister(accType, orgType, operatingMode, getOrgnBrCode(), agCode,
                    Float.parseFloat(amtSanction), Float.parseFloat(roi), title, NameDl, acOpenFacadeRemote.removeSomeSpecialChar(corresAdd), acOpenFacadeRemote.removeSomeSpecialChar(permanentAdd), phoneNo,
                    getUserName(), panGirNo, HFName, age.toString(), jtName1, jtName2, acnoIntro, catvalue,
                    customerId, schemeCode, interestCode, Integer.parseInt(moratoriumPeriod), Float.parseFloat(acPreferableDr),
                    Float.parseFloat(acPreferableCr), rateCode, disbursementType, calculationMethod, calculationOn, calculationLevel,
                    compoundingFrequency, Integer.parseInt(peggingFrequency), intApplicableFreq, Integer.parseInt(LoanPeriodMonths),
                    Integer.parseInt(LoanPeriodDays), dlAcctOpen, ymd.format(dateofBirth), Jt1CustomerId, Jt2CustomerId,actCategory,"", hufFamily);            
            if (resultList.substring(0, 4).equalsIgnoreCase("true")) {
                this.setMessageDl("GENERATED ACCOUNT NO.: " + resultList.substring(5, 17));
                resetFields();
            } else if (resultList.contains("Customer Verification")){
                setMessageDl("Customer Verification is not completed.");
            } else if (resultList.substring(0, 5).equalsIgnoreCase("false")) {
                this.setMessageDl("DATA IS NOT SAVED");
            } else {
                this.setMessageDl("DATA IS NOT SAVED");
            }
            
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public String valiadtions() throws ParseException {
        try {
            if (customerId == null || customerId.equalsIgnoreCase("")) {
                setMessageDl("Please Enter the customerID.");
                return "false";
            }
            if (schemeCode == null || schemeCode.equalsIgnoreCase("0")) {
                setMessageDl("Please Select the scheme Code");
                return "false";
            }
            if (accType == null || accType.equalsIgnoreCase("0")) {
                setMessageDl("Please Select Account Type.");
                return "false";
            }
            if (title == null || title.equalsIgnoreCase("0")) {
                setMessageDl("Please Select the Title.");
                return "false";
            }
            if (NameDl == null || NameDl.equalsIgnoreCase("")) {
                setMessageDl("Please Enter the Customer Name.");
                return "false";
            } else {
                if (NameDl.length() < 3) {
                    setMessageDl("Name should be Minimum Three character.");
                    return "false";
                }
            }

            if (phoneNo == null || phoneNo.equalsIgnoreCase("")) {
            } else {
                if (!phoneNo.matches("[0-9+,-]*")) {
                    setMessageDl("PhoneNo should be Numerical.");
                    return "false";
                }
            }

            if (permanentAdd == null || permanentAdd.equalsIgnoreCase("")) {
                setMessageDl("Please Enter Permanent Address.");
                return "false";
            } else {
//                if (permanentAdd.length() < 10) {
//                    setMessageDl("Permanent Address should be Minimum 10 character.");
//                    return "false";
//                }
                }
            if (corresAdd == null || corresAdd.equalsIgnoreCase("")) {
                setMessageDl("Please Enter Correspondence Address.");
                return "false";
            } else {
//                if (corresAdd.length() < 10) {
//                    setMessageDl("Correspondence Address should be Minimum 10 character.");
//                    return "false";
//                }
                }


//            if (orgType == null || orgType.equalsIgnoreCase("0")) {
//                setMessageDl("Please Select the Organization Type.");
//                return "false";
//            }
            if(actCategory == null || actCategory.equalsIgnoreCase("0")){
                setMessageDl("Please Select the Account Category.");
                return "false"; 
            }
            if (operatingMode == null || operatingMode.equalsIgnoreCase("0")) {
                setMessageDl("Please Select the Operating Mode.");
                return "false";
            } else {
                if (operatingMode.equalsIgnoreCase("2") || operatingMode.equalsIgnoreCase("3") || operatingMode.equalsIgnoreCase("9")
                        || operatingMode.equalsIgnoreCase("7") || operatingMode.equalsIgnoreCase("4") || operatingMode.equalsIgnoreCase("18")) {
                    if (jtName1 == null || jtName1.equalsIgnoreCase("")) {
                        setMessageDl("Joint Name 1 cannot be empty.Please Enter Customer Id for JTName");
                        return "false";
                    }
                } else if (operatingMode.equalsIgnoreCase("5") || operatingMode.equalsIgnoreCase("12")) {
                    if (jtName1 == null || jtName1.equalsIgnoreCase("")) {
                        setMessageDl("Both Joint names cannot be empty.Please Enter Customer Id for JTName");
                        return "false";
                    } else if (jtName2 == null || jtName2.equalsIgnoreCase("")) {
                        setMessageDl("Both Joint names cannot be empty.Please Enter Customer Id for JTName");
                        return "false";
                    }
                }
            }

            if (dateOfDoc.after(sdf.parse(getTodayDate()))) {
                setMessageDl("Date Of Document should not be greater than Current Date.");
                return "false";
            }

//            if (acnoIntro == null || acnoIntro.equals("")) {
//                setMessageDl("Please Enter the Introducer Account No");
//                return "false";
//            }
            if (rateCode == null || rateCode.equals("0")) {
                setMessageDl("Please Select the Rate Code.");
                return "false";
            } else {
                if (rateCode.equalsIgnoreCase("Fi")) {
                    if (peggingFrequency.equalsIgnoreCase("") || peggingFrequency == null) {
                        setMessageDl("Please Enter the Pegging Frequency.");
                        return "false";
                    } else {
                        if (!peggingFrequency.matches("[0-9]*")) {
                            setMessageDl("Please Enter Valid  Pegging Frequency.");
                            return "false";
                        }
                    }
                }
            }
            if (amtSanction == null || amtSanction.equals("")) {
                setMessageDl("Please Enter the Sanction Amount.");
                return "false";
            } else {
                if (!amtSanction.matches("[0-9.]*")) {
                    setMessageDl("Enter Numeric Value for Sanction Amount.");
                    return "false";
                }
                if (this.amtSanction.contains(".")) {
                    if (this.amtSanction.indexOf(".") != this.amtSanction.lastIndexOf(".")) {
                        this.setMessageDl("Invalid Sanction Amount.Please Fill The Sanction Amount Correctly.");
                        return "false";
                    }
                }
                if (Float.parseFloat(amtSanction) < 0) {
                    setMessageDl("Please Enter Valid  Sanction Amount.");
                    return "false";
                }
            }
            if (LoanPeriodMonths == null || LoanPeriodMonths.equalsIgnoreCase("") || LoanPeriodMonths.length() == 0) {
                setMessageDl("Please Enter the Loan Period Months(MM).");
                return "false";
            } else {
                if (!LoanPeriodMonths.matches("[0-9]*")) {
                    setMessageDl("Enter Numeric Value for Loan Period(MM).");
                    return "false";
                }

            }
            if (LoanPeriodDays == null || LoanPeriodDays.equalsIgnoreCase("") || LoanPeriodDays.length() == 0) {
                setMessageDl("Please Enter the Loan Period Days(DD).");
                return "false";
            } else {
                if (!LoanPeriodMonths.matches("[0-9]*")) {
                    setMessageDl("Enter Numeric Value for Loan Period(DD).");
                    return "false";
                }

            }
            if (moratoriumPeriod == null || moratoriumPeriod.equals("")) {
                setMessageDl("Please Enter the Moratorium Period");
                return "false";
            } else {
                if (!moratoriumPeriod.matches("[0-9]*")) {
                    setMessageDl("Enter Numeric Value for Moratorium Period .");
                    return "false";
                }
            }

            if(!OdAllowWithSlab.equalsIgnoreCase("Y")){
                if (dlAcctOpen.size() == 0) {
                    setMessageDl("Please Select the Mark Securities");
                    return "false";
                }
            }

            if (interestCode == null || interestCode.equals("0")) {
                setMessageDl("Please Select the Interest Code.");
                return "false";
            }
            if (roi == null || roi.equals("") || Float.parseFloat(roi) == 0) {
                setMessageDl("ROI Cannot Be Blank or Zero.");
                return "false";
            } else {
                if (!roi.matches("[0-9.]*")) {
                    setMessageDl("Enter Numeric Value for ROI.");
                    return "false";
                } else if (Float.parseFloat(roi) > 25) {
                    setMessageDl("ROI Can not be greater than 25.");
                    return "false";
                }
            }
            if (intApplicableFreq == null || intApplicableFreq.equals("0")) {
                setMessageDl("Please Select the Int Applicable Freq.");
                return "false";
            } else {
                if (intApplicableFreq.equalsIgnoreCase("C")) {
                    if (compoundingFrequency.equalsIgnoreCase("0")) {
                        setMessageDl("Please Select the Compounding Frequency.");
                        return "false";
                    }
                }
            }
            if (calculationMethod == null || calculationMethod.equals("0")) {
                setMessageDl("Please Select the Calculation Method.");
                return "false";
            }
            if (disbursementType == null || disbursementType.equals("0")) {
                setMessageDl("Please Select the Disburestment Type.");
                return "false";
            }
            if (calculationOn == null || calculationOn.equals("0")) {
                setMessageDl("Please Select the Calculation On.");
                return "false";
            }
            if (calculationLevel == null || calculationLevel.equals("0")) {
                setMessageDl("Please Select the Calculation Level.");
                return "false";
            }
            /**********************added by zeeshan*******************************/
            if (interestCode == null || interestCode.equalsIgnoreCase("0")) {
                setMessageDl("Please Select the Interest Code");
                return "false";
            }
            if (Integer.parseInt(LoanPeriodMonths) < 0) {
                setMessageDl("Please Fill Proper	Loan Period(MM)");
                return "false";
            } else {
                if (Integer.parseInt(LoanPeriodDays) < 0) {
                    setMessageDl("Please Fill Proper Loan Period(DD)");
                    return "false";
                }
            }
            if (Integer.parseInt(LoanPeriodDays) < 0) {
                setMessageDl("Please Fill Proper Loan Period(DD)");
                return "false";
            } else {
                if (Integer.parseInt(LoanPeriodMonths) < 0) {
                    setMessageDl("Please Fill Proper Loan Period(MM)");
                    return "false";
                }
            }

            if (Integer.parseInt(moratoriumPeriod) < 0) {
                setMessageDl("Please Fill Proper Moratorium Period (in MM)");
                return "false";
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
        return "true";
    }

    public void resetFields() throws ParseException {
        setAcPreferableCr("");
        setAcPreferableDr("");
        setAccType("");
        setAmtSanction("");
        setCalculationLevel("");
        setCalculationMethod("");
        setCalculationOn("");
        setCompoundingFrequency("");
        setCorresAdd("");
        setCustomerId("");
        setDateOfDoc(sdf.parse(getTodayDate()));
        setDateofBirth(sdf.parse(getTodayDate()));
        setDisbursementType("");
        setHFName("");
        setIntApplicableFreq("");
        setInterestCode("");
        setIntroId("");
        setIntroducerAccount("");
        setIntroducerAccountNo("");
        setOldintroducerAccountNo("");
        setIntroducerAcctStatus("");
        setIntroducerName("");
        setJt1CustomerId("");
        setJt2CustomerId("");
        setJtName1("");
        setJtName2("");
        setLoanPeriodDays("");
        setLoanPeriodMonths("");
        setMarkSecurities("");
        setMoratoriumPeriod("");
        setNameDl("");
        setOperatingMode("");
        setOrgType("");
        setPanGirNo("");
        setPeggingFrequency("");
        setPermanentAdd("");
        setPhoneNo("");
        setRateCode("");
        setSchemeCode("");
        setTitle("");
        setRoi("");
        this.setApplicableRoi("");
        setActCategory("0");
        dlAcctOpen.clear();
        setHufFamily("");        
        setHufFlag(true);
        this.organizationDesc ="";
        this.OdAllowWithSlab = "N";
        this.drCrIndFlag = "Y";
    }

    public void resetValueDl() throws ParseException {
        setMessageDl("");
        resetFields();
        dlAcctOpen = new ArrayList<DlAcctOpenReg>();

    }
    /*******************************************  SECURITY DETAILS    ***********************************************************/
    private String acno;
    private String name = NameDl;
    private String estimationDateLbl;
    private String revalutionDateLbl;
    private String ValuationAmtLbl;
    private Date estimationDate = new Date();
    private String secNature;
    private String secType;
    private Date revalutionDate = new Date();
    private String securityDesc1;
    private String securityDesc2;
    private String securityDesc3;
    private String particular;
    private String valuationAmt;
    private String lienValue;
    private String margin;
    private String remark;
    private String status;
    private String otherAc;
    private String securityflag;
    private String messageSec;
    private boolean disableSecDec3;
    private boolean savedisableSec;
    private List<SelectItem> statusList;
    private List<SelectItem> typeOfSecurityList;
    private List<SelectItem> securityNatureList;
    private List<SelectItem> securityDesc1List;
    private List<SelectItem> securityDesc2List;
    private List<SelectItem> securityDesc3List;
    private List<SecurityDetail> securityDetail;

    public boolean isSavedisableSec() {
        return savedisableSec;
    }

    public void setSavedisableSec(boolean savedisableSec) {
        this.savedisableSec = savedisableSec;
    }

    public String getSecurityflag() {
        return securityflag;
    }

    public void setSecurityflag(String securityflag) {
        this.securityflag = securityflag;
    }

    public String getMessageSec() {
        return messageSec;
    }

    public void setMessageSec(String messageSec) {
        this.messageSec = messageSec;
    }

    public String getSecNature() {
        return secNature;
    }

    public void setSecNature(String secNature) {
        this.secNature = secNature;
    }

    public boolean isDisableSecDec3() {
        return disableSecDec3;
    }

    public void setDisableSecDec3(boolean disableSecDec3) {
        this.disableSecDec3 = disableSecDec3;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SecurityDetail> getSecurityDetail() {
        return securityDetail;
    }

    public void setSecurityDetail(List<SecurityDetail> securityDetail) {
        this.securityDetail = securityDetail;
    }

    public List<SelectItem> getSecurityDesc1List() {
        return securityDesc1List;
    }

    public void setSecurityDesc1List(List<SelectItem> securityDesc1List) {
        this.securityDesc1List = securityDesc1List;
    }

    public List<SelectItem> getSecurityDesc2List() {
        return securityDesc2List;
    }

    public void setSecurityDesc2List(List<SelectItem> securityDesc2List) {
        this.securityDesc2List = securityDesc2List;
    }

    public List<SelectItem> getSecurityDesc3List() {
        return securityDesc3List;
    }

    public void setSecurityDesc3List(List<SelectItem> securityDesc3List) {
        this.securityDesc3List = securityDesc3List;
    }

    public String getValuationAmtLbl() {
        return ValuationAmtLbl;
    }

    public void setValuationAmtLbl(String ValuationAmtLbl) {
        this.ValuationAmtLbl = ValuationAmtLbl;
    }

    public String getRevalutionDateLbl() {
        return revalutionDateLbl;
    }

    public void setRevalutionDateLbl(String revalutionDateLbl) {
        this.revalutionDateLbl = revalutionDateLbl;
    }

    public Date getEstimationDate() {
        return estimationDate;
    }

    public void setEstimationDate(Date estimationDate) {
        this.estimationDate = estimationDate;
    }

    public String getEstimationDateLbl() {
        return estimationDateLbl;
    }

    public void setEstimationDateLbl(String estimationDateLbl) {
        this.estimationDateLbl = estimationDateLbl;
    }

    public String getLienValue() {
        return lienValue;
    }

    public void setLienValue(String lienValue) {
        this.lienValue = lienValue;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getOtherAc() {
        return otherAc;
    }

    public void setOtherAc(String otherAc) {
        this.otherAc = otherAc;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRevalutionDate() {
        return revalutionDate;
    }

    public void setRevalutionDate(Date revalutionDate) {
        this.revalutionDate = revalutionDate;
    }

    public String getSecurityDesc1() {
        return securityDesc1;
    }

    public void setSecurityDesc1(String securityDesc1) {
        this.securityDesc1 = securityDesc1;
    }

    public String getSecurityDesc2() {
        return securityDesc2;
    }

    public void setSecurityDesc2(String securityDesc2) {
        this.securityDesc2 = securityDesc2;
    }

    public String getSecurityDesc3() {
        return securityDesc3;
    }

    public void setSecurityDesc3(String securityDesc3) {
        this.securityDesc3 = securityDesc3;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValuationAmt() {
        return valuationAmt;
    }

    public void setValuationAmt(String valuationAmt) {
        this.valuationAmt = valuationAmt;
    }

    public List<SelectItem> getSecurityNatureList() {
        return securityNatureList;
    }

    public void setSecurityNatureList(List<SelectItem> securityNatureList) {
        this.securityNatureList = securityNatureList;
    }

    public List<SelectItem> getTypeOfSecurityList() {
        return typeOfSecurityList;
    }

    public void setTypeOfSecurityList(List<SelectItem> typeOfSecurityList) {
        this.typeOfSecurityList = typeOfSecurityList;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public void getTableValues() {

        try {
            List resultList = new ArrayList();
            resultList = acOpenFacadeRemote.getStatus();
            if (resultList.size() <= 0) {
                return;
            } else {
                statusList = new ArrayList<SelectItem>();
                statusList.add(new SelectItem("0", ""));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    statusList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
            typeOfSecurityList = new ArrayList<SelectItem>();
            typeOfSecurityList.add(new SelectItem("0", ""));
            typeOfSecurityList.add(new SelectItem("DATED", "DATED"));
            typeOfSecurityList.add(new SelectItem("NON-DATED", "NON-DATED"));

            securityNatureList = new ArrayList<SelectItem>();
            securityNatureList.add(new SelectItem("0", ""));
            securityNatureList.add(new SelectItem("P", "PRIMARY"));
            securityNatureList.add(new SelectItem("C", "COLLATERAL"));


        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void changeLabel() {
        if (setSecurityDescription1().equalsIgnoreCase("false")) {
            return;
        }
        if (secType.equalsIgnoreCase("DATED")) {
            setEstimationDateLbl("Issue Date");
            setValuationAmtLbl("Maturity Value");
            setRevalutionDateLbl("Maturity Date");
            securityDesc2List = new ArrayList<SelectItem>();
            securityDesc3List = new ArrayList<SelectItem>();
            setDisableSecDec3(true);
        } else if (secType.equalsIgnoreCase("NON-DATED")) {
            setEstimationDateLbl("Estimation Date");
            setValuationAmtLbl("Valuation Amt");
            setRevalutionDateLbl("Revalution Date");
            setDisableSecDec3(false);
            securityDesc2List = new ArrayList<SelectItem>();
            securityDesc3List = new ArrayList<SelectItem>();
        } else {
            securityDesc2List = new ArrayList<SelectItem>();
            securityDesc3List = new ArrayList<SelectItem>();
            securityDesc1List = new ArrayList<SelectItem>();
        }
    }

    public void onChangeOFSecurityDesc1() {
        if (setSecurityDescription2().equalsIgnoreCase("false")) {
            return;
        }
        if (securityDesc2 != null) {
            if (securityDesc1.equalsIgnoreCase("secured advances")) {
                setDisableSecDec3(false);
            }
            if (securityDesc1.equalsIgnoreCase("bill purchased/discount")) {
                setDisableSecDec3(true);
            }
            if (securityDesc1.equalsIgnoreCase("unsecured advances")) {
                setDisableSecDec3(true);
            }
        }
    }

    public void onChangeOFSecurityDesc2() {
        if (setSecurityDescription3().equalsIgnoreCase("false")) {
            return;
        }
        if (securityDesc2 != null) {
            if (securityDesc2.equalsIgnoreCase("PLEDGE")) {
                setDisableSecDec3(false);
                securityflag = "true";
            } else if (securityDesc2.equalsIgnoreCase("merchandise")) {
                setDisableSecDec3(false);
                securityflag = "true";
            }

        }
    }

    public String setSecurityDescription1() {
        if (secType == null || secType.equals("0") || secType.equalsIgnoreCase("")) {
            setMessageSec("Please Select the Security Type.");
            return "false";
        }
        try {
            setSavedisableSec(false);
            List resultList = new ArrayList();
            resultList = acOpenFacadeRemote.getSecurityDesc1(secType);
            if (resultList.size() <= 0) {
                securityDesc1List.clear();
                return "false";
            } else {
                securityDesc1List = new ArrayList<SelectItem>();
                securityDesc1List.add(new SelectItem("0", ""));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    securityDesc1List.add(new SelectItem(ele.get(0).toString()));
                }
            }

            return "true";
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
        return "true";
    }

    public String setSecurityDescription2() {
        if (secType == null || secType.equals("0") || secType.equalsIgnoreCase("")) {
            setMessageSec("Please Select the Security Type.");
            return "false";
        }
        if (securityDesc1 == null || securityDesc1.equals("0") || securityDesc1.equalsIgnoreCase("")) {
            setMessageSec("Please Select the securityDesc1.");
            return "false";
        }
        try {
            List resultList = new ArrayList();
            resultList = acOpenFacadeRemote.getSecurityDesc2(secType, securityDesc1);
            if (resultList.size() <= 0) {
                return "false";
            } else {
                securityDesc2List = new ArrayList<SelectItem>();
                securityDesc2List.add(new SelectItem("0", " "));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    securityDesc2List.add(new SelectItem(ele.get(0).toString()));
                }
            }
            return "true";
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
        return "true";
    }

    public String setSecurityDescription3() {
        if (secType == null || secType.equals("0") || secType.equalsIgnoreCase("")) {
            setMessageSec("Please Select the Security Type.");
            return "false";
        }
        if (securityDesc1 == null || securityDesc1.equals("0") || securityDesc1.equalsIgnoreCase("")) {
            setMessageSec("Please Select the securityDesc1.");
            return "false";
        }
        if (securityDesc2 == null || securityDesc2.equals("0") || securityDesc2.equalsIgnoreCase("")) {
            setMessageSec("Please Select the securityDesc2.");
            return "false";
        }
        try {
            List resultList = new ArrayList();
            resultList = acOpenFacadeRemote.getSecurityDesc3(secType, securityDesc1, securityDesc2);
            if (resultList.size() <= 0) {
                return "false";
            } else {
                securityDesc3List = new ArrayList<SelectItem>();
                securityDesc3List.add(new SelectItem("0", " "));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    securityDesc3List.add(new SelectItem(ele.get(0).toString()));
                }
            }
            return "true";

        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
        return "true";
    }

    public void saveSecurityDetails() throws ParseException {
        setMessageSec("");
        this.flag2 = false;
        if (validation().equalsIgnoreCase("False")) {
            return;
        }
        if (securityDesc1 == null || securityDesc1.equalsIgnoreCase("0")) {
            setSecurityDesc1("");
        }
        if (securityDesc2 == null || securityDesc2.equalsIgnoreCase("0")) {
            setSecurityDesc2("");
        }
        if (securityDesc3 == null || securityDesc3.equalsIgnoreCase("0")) {
            setSecurityDesc3("");
        }
        if (valuationAmt == null || valuationAmt.equalsIgnoreCase("")) {
            setValuationAmt("0");
        }
        if (lienValue == null || lienValue.equalsIgnoreCase("")) {
            setLienValue("0");
        }
        if (margin == null || margin.equalsIgnoreCase("")) {
            setMargin("0");
        }
        try {
            dlTableValues();
            resetPage();
            setSavedisableSec(true);
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void resetPage() throws ParseException {
        setMessageSec("");
        resetValues();
    }

    public void resetValues() throws ParseException {
        setLienValue("");
        setMargin("");
        setOtherAc("");
        setParticular("");
        setRemark("");
        setSecurityDesc1("");
        setSecurityDesc2("");
        setSecurityDesc3("");
        setSecNature("0");
        setSecType("");
        setStatus("0");
        setValuationAmt("");
        setEstimationDate(sdf.parse(this.getTodayDate()));
        setRevalutionDate(sdf.parse(this.getTodayDate()));
    }

    public void setRemarks() {
        if (secType == null || secType.equals("0") || secType.equalsIgnoreCase("")) {
            setMessageSec("Please Select the Security Type.");
            return;
        }
        if (securityDesc1 == null || securityDesc1.equals("0") || securityDesc1.equalsIgnoreCase("")) {
            setMessageSec("Please Select the securityDesc1.");
            return;
        }
        if (securityDesc1.equalsIgnoreCase("0")) {
            setRemark(secType);
        } else {
            setRemark(secType + ":" + securityDesc1);
        }
    }

    public String validation() throws ParseException {
        String msg = "";
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (secType == null || secType.equals("0") || secType.equalsIgnoreCase("")) {
            setMessageSec("Please Select the Security Type.");
            return "false";
        }
        if (secNature == null || secNature.equals("0") || secNature.equalsIgnoreCase("")) {
            setMessageSec("Please Select Security Nature.");
            return "false";
        }
        if (securityDesc1 == null || securityDesc1.equals("0") || securityDesc1.equalsIgnoreCase("")) {
            setMessageSec("Please Select the securityDesc1.");
            return "false";
        }
        if (particular == null || particular.equals("")) {
            setMessageSec("Please Enter Particulars.");
            return "false";
        }
        if (securityDesc2 == null || securityDesc2.equals("0") || securityDesc2.equalsIgnoreCase("")) {
            setMessageSec("Please Select the securityDesc2.");
            return "false";
        }
        if (!(valuationAmt == null || valuationAmt.equals(""))) {
            Matcher valuationAmtCheck = p.matcher(valuationAmt);
            if (!valuationAmtCheck.matches()) {
                setValuationAmt("");
                setMessageSec("Enter Numeric Value for Valuation Amount/Maturity Value.");
                return "false";
            }
            if (!valuationAmt.matches("[0-9.]*")) {
                setMessageSec("Enter Numeric Value for Valuation Amount/Maturity Value.");
                return "false";
            }

            if (this.valuationAmt.contains(".")) {
                if (this.valuationAmt.indexOf(".") != this.valuationAmt.lastIndexOf(".")) {
                    setMessageSec("Invalid Valuation Amount/Maturity Value.Please Fill The Valuation Amount/Maturity Value Correctly.");
                    return "false";
                }
            }
            if (Float.parseFloat(valuationAmt) < 0) {
                setMessageSec("Please Enter Valid  Valuation Amount/Maturity Value.");
                return "false";
            }
        }
        if (!(lienValue == null || lienValue.equals(""))) {
            Matcher lienValueChec = p.matcher(lienValue);
            if (!lienValueChec.matches()) {
                setMessageSec("Enter Numeric Value for Lien Value.");
                return "false";
            }
            if (!lienValue.matches("[0-9.]*")) {
                setMessageSec("Enter Numeric Value for Lien Value.");
                return "false";
            }
            if (this.lienValue.contains(".")) {
                if (this.lienValue.indexOf(".") != this.lienValue.lastIndexOf(".")) {
                    setMessageSec("Invalid  Lien Value.Please Fill The Lien Value Correctly.");
                    return "false";
                }
            }
            if (Float.parseFloat(lienValue) < 0) {
                setMessageSec("Please Enter Valid  Lien Value.");
                return "false";
            }
        }
        if (!(margin != null || margin.equals(""))) {
            Matcher marginCheck = p.matcher(margin);
            if (!marginCheck.matches()) {
                setMargin("");
                setMessageSec("Enter Numeric Value for Margin.");
                return "false";
            } else {
                if (this.margin.contains(".")) {
                    if (this.margin.indexOf(".") != this.margin.lastIndexOf(".")) {
                        setMessageSec("Invalid  Margin.Please Fill The Margin Correctly.");
                        return "false";
                    }
                }
                if ((Float.parseFloat(margin)) < 1 || (Float.parseFloat(margin)) > 99.9) {
                    setMargin("");
                    setMessageSec("Please Enter Valid Margin(1 to 99.9).");
                    return "false";
                }
            }
        }
        if (secType.equalsIgnoreCase("DATED") || secType.equalsIgnoreCase("NON-DATED")) {
            if (!securityDesc1.equalsIgnoreCase("UNSECURED ADVANCES")) {
                if (valuationAmt == null || valuationAmt.equalsIgnoreCase("")) {
                    setMessageSec("Please Enter Valid Valuation Amount / Maturity Value.");
                    return "false";
                } else {
                    Matcher valuationAmtCheck = p.matcher(valuationAmt);
                    if (!valuationAmtCheck.matches()) {
                        setValuationAmt("");
                        setMessageSec("Enter Numeric Value for Valuation Amount/Maturity Value.");
                        return "false";
                    }
                    if (!valuationAmt.matches("[0-9.]*")) {
                        setMessageSec("Enter Numeric Value for Valuation Amount/Maturity Value.");
                        return "false";
                    }
                    if (this.valuationAmt.contains(".")) {
                        if (this.valuationAmt.indexOf(".") != this.valuationAmt.lastIndexOf(".")) {
                            setMessageSec("Invalid  Valuation Amount/Maturity Value.Please Fill The Valuation Amount/Maturity Value Correctly.");
                            return "false";
                        }
                    }
                    if (Float.parseFloat(valuationAmt) < 0) {
                        setMessageSec("Please Enter Valid  Valuation Amount/Maturity Value.");
                        return "false";
                    }
                }
            }
        }

        if (!securityDesc1.equalsIgnoreCase("UNSECURED ADVANCES")) {
            if (lienValue == null || lienValue.equalsIgnoreCase("")) {
                setLienValue("");
                setMessageSec("Please Enter Valid Lien Amount.");
                return "false";
            } else {
                Matcher lienValueCheck = p.matcher(lienValue);
                if (!lienValueCheck.matches()) {
                    setLienValue("");
                    setMessageSec("Enter Numeric Value for Lien.");
                    return "false";
                }
                if (!lienValue.matches("[0-9.]*")) {
                    setMessageSec("Enter Numeric Value for Lien Value.");
                    return "false";
                }
                if (this.lienValue.contains(".")) {
                    if (this.lienValue.indexOf(".") != this.lienValue.lastIndexOf(".")) {
                        setMessageSec("Invalid  Lien Value.Please Fill The Lien Value Correctly.");
                        return "false";
                    }
                }
                if (Float.parseFloat(lienValue) < 0) {
                    setMessageSec("Please Enter Valid  Lien Value.");
                    return "false";
                }
                if (margin == null || margin.equalsIgnoreCase("")) {
                    setMessageSec("Please Enter Margin.");
                    return "false";
                } else {
                    Matcher marginCheck = p.matcher(margin);
                    if (!marginCheck.matches()) {
                        setMargin("");
                        setMessageSec("Enter Numeric Value for Margin.");
                        return "false";
                    } else {
                        if (this.margin.contains(".")) {
                            if (this.margin.indexOf(".") != this.margin.lastIndexOf(".")) {
                                setMessageSec("Invalid  Margin.Please Fill The Margin Correctly.");
                                return "false";
                            }
                        }

                        if ((Float.parseFloat(margin)) < 1 || (Float.parseFloat(margin)) > 99.9) {
                            setMargin("");
                            setMessageSec("Please Enter Valid Margin(1 to 99.9).");
                            return "false";
                        }
                    }
                }
            }
        }

        if (!(lienValue.equalsIgnoreCase("") || lienValue == null) && !(valuationAmt.equalsIgnoreCase("")
                || valuationAmt == null)) {
            Matcher valuationAmtCheck = p.matcher(valuationAmt);
            Matcher lienValueCheck = p.matcher(lienValue);
            if (!valuationAmtCheck.matches()) {
                setMessageSec("Please Enter Numeric Value for Valuation Amount / Maturity Value.");
                return "false";
            } else if (!lienValueCheck.matches()) {
                setMessageSec("Enter Numeric Value for Lien.");
                return "false";
            } else {
                if (!lienValue.matches("[0-9]*")) {
                    setMessageSec("Enter Numeric Value for Lien.");
                    return "false";
                }
                if (!lienValue.matches("[0-9.]*")) {
                    setMessageSec("Enter Numeric Value for Lien Value.");
                    return "false";
                }
                if (this.lienValue.contains(".")) {
                    if (this.lienValue.indexOf(".") != this.lienValue.lastIndexOf(".")) {
                        setMessageSec("Invalid  Lien Value.Please Fill The Lien Value Correctly.");
                        return "false";
                    }
                }
                if (Float.parseFloat(lienValue) < 0) {
                    setMessageSec("Please Enter Valid  Lien Value.");
                    return "false";
                }
                if (!valuationAmt.matches("[0-9]*")) {
                    setMessageSec("Please Enter Numeric Value for Valuation Amount / Maturity Value.");
                    return "false";
                }
                if (!valuationAmt.matches("[0-9.]*")) {
                    setMessageSec("Enter Numeric Value for Valuation Amount/Maturity Value.");
                    return "false";
                }
                if (this.valuationAmt.contains(".")) {
                    if (this.valuationAmt.indexOf(".") != this.valuationAmt.lastIndexOf(".")) {
                        setMessageSec("Invalid  Valuation Amount/Maturity Value.Please Fill The Valuation Amount/Maturity Value Correctly.");
                        return "false";
                    }
                }
                if (Float.parseFloat(valuationAmt) < 0) {
                    setMessageSec("Please Enter Valid  Valuation Amount/Maturity Value.");
                    return "false";
                }
                if (Float.parseFloat(lienValue) > Float.parseFloat(valuationAmt)) {
                    if (secType.equalsIgnoreCase("DATED")) {
                        setMessageSec("Maturity Value Must Be Greater Then Or Equal To Lien.");
                        return "false";
                    }
                    if (secType.equalsIgnoreCase("NON-DATED")) {
                        setMessageSec("Valuation Amt. Must Be Greater Then Or Equal To Lien.");
                        return "false";
                    }
                }
            }
        }

        /********************  check this **********************/
        if (estimationDate.after(sdf.parse(this.getTodayDate()))) {
            setMessageSec(" Estimation or Issue Date cannot be greater Than Present Date.");
            return "false";
        }

        if (estimationDate.after(revalutionDate)) {
            if (secType.equalsIgnoreCase("DATED")) {
                setMessageSec("Maturity date should be greater than Issue date.");
                return "false";
            }
            if (secType.equalsIgnoreCase("NON-DATED")) {
                setMessageSec("Estimation Date should be greater than Revalution date.");
                return "false";
            }
        }

        if (status == null || status.equals("0")) {
            setMessageSec("Please Select the Status.");
            return "false";
        }

        return "true";
    }
    /*******************************************THIS CODE IS FOR TD LIEN MARKING FORM*********************************/
    private String errorMessage;
    private String message;
    private String acctNo;
    private String oldacctNo;
    private String custName;
    private String jtName;
    private String oprMode;
    private String lienMarkOption;
    private List<SelectItem> lienMarkOptionList;
    int currentRow;
    private List<TdLienMarkingGrid> gridDetail;
    private TdLienMarkingGrid currentItem = new TdLienMarkingGrid();
    private String recieptNo;
    private String controlNo;
    private String prinAmount;
    private String detail;
    private String statusLien;
    private String auth;
    private boolean flag1;
    private boolean flag2;

    public String getOldacctNo() {
        return oldacctNo;
    }

    public void setOldacctNo(String oldacctNo) {
        this.oldacctNo = oldacctNo;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
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

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getOprMode() {
        return oprMode;
    }

    public void setOprMode(String oprMode) {
        this.oprMode = oprMode;
    }

    public String getLienMarkOption() {
        return lienMarkOption;
    }

    public void setLienMarkOption(String lienMarkOption) {
        this.lienMarkOption = lienMarkOption;
    }

    public List<SelectItem> getLienMarkOptionList() {
        return lienMarkOptionList;
    }

    public void setLienMarkOptionList(List<SelectItem> lienMarkOptionList) {
        this.lienMarkOptionList = lienMarkOptionList;
    }

    public TdLienMarkingGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TdLienMarkingGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<TdLienMarkingGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<TdLienMarkingGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }

    public String getPrinAmount() {
        return prinAmount;
    }

    public void setPrinAmount(String prinAmount) {
        this.prinAmount = prinAmount;
    }

    public String getRecieptNo() {
        return recieptNo;
    }

    public void setRecieptNo(String recieptNo) {
        this.recieptNo = recieptNo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatusLien() {
        return statusLien;
    }

    public void setStatusLien(String statusLien) {
        this.statusLien = statusLien;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public void customerDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setCustName("");
        this.setOprMode("");
        this.setJtName("");
        this.setPrinAmount("");
        this.setControlNo("");
        this.setRecieptNo("");
        this.setStatusLien("");
        this.setFlag1(true);
        gridDetail = new ArrayList<TdLienMarkingGrid>();
        try {
            if (this.oldacctNo == null || this.oldacctNo.equalsIgnoreCase("") || this.oldacctNo.length() == 0) {
                this.setErrorMessage("Please Enter Account No.");
                return;
            }
            //if (this.oldacctNo.length() < 12) {
            if (!this.oldacctNo.equalsIgnoreCase("") && ((this.oldacctNo.length() != 12) && (this.oldacctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setOldacctNo("");
                this.setErrorMessage("Please Enter Account No Carefully.");
                return;
            }
            acctNo = facadeRemote.getNewAccountNumber(oldacctNo);
            String nature = facadeRemote.getAccountNature(acctNo);
            if (!nature.equalsIgnoreCase(CbsConstant.FIXED_AC) && !nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                setOldacctNo("");
                setAcctNo("");
                this.setErrorMessage("Please Enter Account No. Of 'FD' Nature.");
                return;
            }
            List resultList = new ArrayList();
            List resultList1 = new ArrayList();
            resultList = lienMark.customerDetail(this.acctNo);
            resultList1 = lienMark.gridDetailLoad(this.acctNo, nature);
            String authResult = lienMark.auth(this.getUserName(), this.getOrgnBrCode());
            this.setAuth(authResult);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    this.setCustName(ele.get(0).toString());
                    this.setOprMode(ele.get(1).toString());
                    this.setJtName(ele.get(2).toString());
                }
            } else {
                this.setErrorMessage("Account No. Does Not Exists !!!");
                return;
            }
            if (!resultList1.isEmpty()) {
                for (int i = 0; i < resultList1.size(); i++) {
                    Vector ele = (Vector) resultList1.get(i);
                    TdLienMarkingGrid dt = new TdLienMarkingGrid();
                    dt.setAcNo(ele.get(0).toString());
                    dt.setVoucherNo(ele.get(1).toString());
                    dt.setReciept(ele.get(2).toString());
                    dt.setPrintAmt(ele.get(3).toString());
                    dt.setFddt(ele.get(4).toString());
                    dt.setMatDt(ele.get(5).toString());
                    dt.setTdmatDt(ele.get(6).toString());
                    dt.setIntOpt(ele.get(7).toString());
                    dt.setRoi(ele.get(8).toString());
                    dt.setStatus(ele.get(9).toString());
                    dt.setSeqNo(ele.get(10).toString());
                    if (ele.get(11).toString().equalsIgnoreCase("Y")) {
                        dt.setLien("Yes");
                    } else {
                        dt.setLien("No");
                    }

                    gridDetail.add(dt);
                }
            } else {
                this.setErrorMessage("No Voucher Exists in This Account No.");
                return;
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void fillValuesofGridInFields() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setPrinAmount("");
        this.setControlNo("");
        this.setRecieptNo("");
        this.setStatusLien("");
        this.setDetail("");
        this.setLienMarkOption("--Select--");
        try {
            /**FOR CHECKING DUPLICATE ENTRY**/
            List<DlAcctOpenReg> tmpTable = dlAcctOpen;
            for (int i = 0; i < tmpTable.size(); i++) {
                String tmpAcno = tmpTable.get(i).getAcno();
                String tmpVouchNo = tmpTable.get(i).getVoucherNo();
                if (tmpAcno.equalsIgnoreCase(currentItem.getAcNo()) && tmpVouchNo.equalsIgnoreCase(currentItem.getVoucherNo())) {
                    this.setErrorMessage(currentItem.getAcNo() + "/" + currentItem.getVoucherNo() + " Already Selected For Lien.");
                    this.setFlag1(true);
                    return;
                }
            }
            /**END**/
            this.setControlNo(currentItem.getSeqNo());
            this.setRecieptNo(currentItem.getReciept());
            this.setStatusLien(currentItem.getLien());
            if (currentItem.getLien().equalsIgnoreCase("Yes")) {
                this.setLienMarkOption("No");
            } else {
                this.setLienMarkOption("Yes");
            }
            String result = lienMark.tdLienPresentAmount(this.acctNo, Float.parseFloat(currentItem.getVoucherNo()), Float.parseFloat(currentItem.getPrintAmt()));
            if (result == null) {
                this.setErrorMessage("PROBLEM IN GETTING PRESENT AMOUNT !!!");
                this.setFlag1(true);
                return;
            } else {
                int n = result.indexOf("*");
                this.setDetail(result.substring(0, n));
                this.setPrinAmount(result.substring(n + 1));
                this.setFlag1(false);
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void dlTableValues() {
        try {
            DlAcctOpenReg dl = new DlAcctOpenReg();
            if (dlAcctOpen.isEmpty()) {
                m = 1;
            } else {
                m = Integer.parseInt(dlAcctOpen.get(dlAcctOpen.size() - 1).getSno()) + 1;
            }

            dl.setSno(Integer.toString(m));
            if (flag2 == true) {
                dl.setAcno(currentItem.getAcNo());
                dl.setIssueDate(currentItem.getFddt());
                dl.setLien(this.prinAmount);
                dl.setMatDate(currentItem.getMatDt());
                dl.setMatValue(this.prinAmount);
                dl.setParticular(facadeRemote.getAccountCode(currentItem.getAcNo()));
                dl.setSecDes1("LIEN");
                dl.setSecDesc2(descriptionMsg);
                dl.setSecNature("PRIMARY");
                dl.setTypeOfSec("DATED");
                dl.setEnterBy(this.getUserName());
                dl.setEnterDate(this.getTodayDate());
                dl.setDetails(details);
                dl.setVoucherNo(currentItem.getVoucherNo());
            } else {
                dl.setAcno("");
                dl.setEnterBy(getUserName());
                dl.setIssueDate(sdf.format(estimationDate));
                dl.setLien(lienValue);
                dl.setMargin(margin);
                dl.setMatDate(sdf.format(revalutionDate));
                dl.setMatValue(valuationAmt);
                dl.setParticular(particular);
                dl.setSecDes1(securityDesc1);
                dl.setSecDesc2(securityDesc2);
                dl.setSecDesc3(securityDesc3);
                dl.setSecNature(secNature);
                if (status.equalsIgnoreCase("1")) {
                    dl.setStatus("Active");
                }
                dl.setTypeOfSec(secType);
                dl.setEnterBy(getUserName());
                dl.setEnterDate(getTodayDate());
                dl.setDetails(remark);
                dl.setVoucherNo(currentItem.getVoucherNo());
            }

            dlAcctOpen.add(dl);

        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }
    String detailOfDlAccOpFrm = "";
    String lienValOfDlAccOpFrm = "";
    String Details = "";
    String descriptionMsg = "";

    public void dlAccCustIdOpen() {
        try {
            if (this.lienMarkOption.equalsIgnoreCase("YES")) {
                String flag11 = null;
                /**FOR CHECKING DUPLICATE ENTRY**/
                List<DlAcctOpenReg> tmpTable = dlAcctOpen;

                for (int i = 0; i < tmpTable.size(); i++) {
                    String tmpAcno = tmpTable.get(i).getAcno();
                    String tmpVouchNo = tmpTable.get(i).getVoucherNo();
                    if (tmpAcno.equalsIgnoreCase(currentItem.getAcNo()) && tmpVouchNo.equalsIgnoreCase(currentItem.getVoucherNo())) {
                        this.setErrorMessage(currentItem.getAcNo() + "/" + currentItem.getVoucherNo() + " Already Selected For Lien.");
                        flag11 = "false";
                    } else {
                        flag11 = "true";
                    }
                }

                /**END**/
                if (flag11 == null) {
                    valuesForDlForm();
                } else if (flag11.equalsIgnoreCase("true")) {
                    valuesForDlForm();
                }

            }
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }
    String details;

    public void valuesForDlForm() {

        try {
            String result = lienMark.dlAcOpen();
            if (result == null) {
                descriptionMsg = "";
            } else {
                descriptionMsg = result;
            }
            /******These are the values which are send to DL account form grid*******/
            Details = currentItem.getAcNo() + "/" + currentItem.getVoucherNo() + "; ROI:" + currentItem.getRoi() + " ; Present Amount:" + this.prinAmount;
            details = "DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY):" + Details;
            dlTableValues();
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    /* public void bilLcBg() {
    try {
    if (this.lienMarkOption.equalsIgnoreCase("Yes")) {
    Details = currentItem.getAcNo() + "/" + currentItem.getVoucherNo() + " ; ROI:" + currentItem.getRoi() + " ; Present Amount:" + this.prinAmount;
    String finalString1 = " PRIMARY" + "," + " Lien Marking Against TD" + "," + "TD" + "," + this.prinAmount + "," + this.prinAmount + "," + currentItem.getMatDt() + "," + currentItem.getFddt() + "," + detail + "," + this.getUserName() + "," + this.getTodayDate();
    }
    } catch (Exception ex) {
    setErrorMessage(ex.getLocalizedMessage());
    }
    }*/
    String DLAccOpen_Lien = "";
    String BillLcBg_Lien = "";
    String loanLienCall = "";

    public void saveDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        this.flag2 = true;
        String lAcNO = "";
        try {
            DLAccOpen_Lien = "True";
            String tmpSecType = "";
            dlAccCustIdOpen();

            if (this.lienMarkOption.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Lien Marking.");
                return;
            }
            String accountCode = facadeRemote.getAccountCode(currentItem.getAcNo());
            String result = lienMark.saveLienMarkingDetail(Float.parseFloat(this.recieptNo), Float.parseFloat(currentItem.getVoucherNo()), accountCode, currentItem.getAcNo(), lAcNO, this.lienMarkOption, this.auth, this.getUserName(), this.detail, this.loanLienCall, tmpSecType, DLAccOpen_Lien, BillLcBg_Lien, getOrgnBrCode());
            if (result == null) {
                this.setErrorMessage("PROBLEM IN SAVE THE RECORD !!!");
                return;
            } else {
                if (result.contains("!")) {
                    this.setErrorMessage(result);
                } else {
                    this.setMessage(result);
                }
            }

            this.setFlag1(true);
            this.setRecieptNo("");
            this.setControlNo("");
            this.setPrinAmount("");
            this.setStatusLien("");
            this.setLienMarkOption("--Select--");
            this.setDetail("");
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void resetForm() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setCustName("");
        this.setAcctNo("");
        setOldacctNo("");
        this.setJtName("");
        this.setOprMode("");
        this.setRecieptNo("");
        this.setControlNo("");
        this.setPrinAmount("");
        this.setStatusLien("");
        this.setLienMarkOption("--Select--");
        this.setDetail("");
        this.setFlag1(true);
        gridDetail = new ArrayList<TdLienMarkingGrid>();

    }
    /**END OF TD LIEN MARKING FORM CODE**/
    /**START OF ACCOUNT STATUS FORM CODE**/
    private String messageaAcct;
    private List<SelectItem> acctOption;
    private String acnostatus;
    private String code;
    private String oldcode;
    private String code1;
    private String remarks;
    private String nameAcctStatus;
    private String pStatus;
    private String nStatus;
    private Date wefDate;
    private Date reportDate;
    private List<AcStatus> incis;
    private String customer;
    //private String statusAcct;     commented by dinesh, because of not used
    private String lienAmt;
    private String lienAcNo;
    private String liencode;
    private String oldliencode;
    private String lienacctype;
    private List<SelectItem> lienacctOption;
    private String flag = "false";
    private String fflag = "false";
    private Date todate;
    private String wefDate1;
    private boolean lienflag = false;

    public boolean isLienflag() {
        return lienflag;
    }

    public String getOldliencode() {
        return oldliencode;
    }

    public void setOldliencode(String oldliencode) {
        this.oldliencode = oldliencode;
    }

    public void setLienflag(boolean lienflag) {
        this.lienflag = lienflag;
    }

    public String getAcnostatus() {
        return acnostatus;
    }

    public void setAcnostatus(String acnostatus) {
        this.acnostatus = acnostatus;
    }

    public String getWefDate1() {
        return wefDate1;
    }

    public void setWefDate1(String wefDate1) {
        this.wefDate1 = wefDate1;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    public String getFflag() {
        return fflag;
    }

    public void setFflag(String fflag) {
        this.fflag = fflag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<SelectItem> getLienacctOption() {
        return lienacctOption;
    }

    public void setLienacctOption(List<SelectItem> lienacctOption) {
        this.lienacctOption = lienacctOption;
    }

    public String getLienacctype() {
        return lienacctype;
    }

    public void setLienacctype(String lienacctype) {
        this.lienacctype = lienacctype;
    }

    public String getLiencode() {
        return liencode;
    }

    public void setLiencode(String liencode) {
        this.liencode = liencode;
    }

    public String getLienAcNo() {
        return lienAcNo;
    }

    public void setLienAcNo(String lienAcNo) {
        this.lienAcNo = lienAcNo;
    }

    public String getLienAmt() {
        return lienAmt;
    }

    public void setLienAmt(String lienAmt) {
        this.lienAmt = lienAmt;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getNameAcctStatus() {
        return nameAcctStatus;
    }

    public void setNameAcctStatus(String nameAcctStatus) {
        this.nameAcctStatus = nameAcctStatus;
    }

    public String getMessageaAcct() {
        return messageaAcct;
    }

    public void setMessageaAcct(String messageaAcct) {
        this.messageaAcct = messageaAcct;
    }

    public List<SelectItem> getAcctOption() {
        return acctOption;
    }

    public void setAcctOption(List<SelectItem> acctOption) {
        this.acctOption = acctOption;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getnStatus() {
        return nStatus;
    }

    public void setnStatus(String nStatus) {
        this.nStatus = nStatus;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getWefDate() {
        return wefDate;
    }

    public void setWefDate(Date wefDate) {
        this.wefDate = wefDate;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public List<AcStatus> getIncis() {
        return incis;
    }

    public void setIncis(List<AcStatus> incis) {
        this.incis = incis;
    }

    public String getOldcode() {
        return oldcode;
    }

    public void setOldcode(String oldcode) {
        this.oldcode = oldcode;
    }

    /** Creates a new instance of AccountStatus */
    public void custName() {

        try {
            lienAmt = "0";
            lienAcNo = "";
            setMessage("");
            if ((oldcode.equals("")) || (oldcode == null)) {
                this.setMessage("Please Enter Account Number");
                setName("");
                setpStatus("");
                incis.clear();
                return;
            }
            if (!validator.validateString(oldcode)) {
                this.setMessage("Please Enter Valid Account Number.");
                this.setCode("");
                setOldcode("");
                setName("");
                setpStatus("");
                incis.clear();
                return;
            }
            acnostatus = facadeRemote.getNewAccountNumber(oldcode);
            String accNature = facadeRemote.getAccountNature(acnostatus);
            String[] accNatures = {CbsConstant.DEPOSIT_SC, CbsConstant.RECURRING_AC};
            int flag = 0;
            for (int i = 0; i < accNatures.length; i++) {
                if ((!accNatures[i].equalsIgnoreCase(accNature))) {
                    flag = 1;
                } else {
                    flag = 0;
                    break;
                }
            }
            if (flag == 1) {
                setMessage("Please insert account no. of DS, RD nature only");
                return;
            }
            if ((oldcode.equals("")) || (oldcode == null)) {
                incis.clear();
                return;
            }
            List list = statusMaintenanceFacade.getCustNameAndStatus(acnostatus, facadeRemote.getAccountCode(acnostatus));
            if (list.isEmpty()) {
                setNameAcctStatus("");
                setpStatus("");
                throw new ApplicationException("No Table Details Exist for this Account");
                
            }
            fflag = "true";
            Vector values = (Vector) list.get(0);
            this.setCustomer(values.get(0).toString());
            this.setStatus(values.get(1).toString());
            this.setpStatus(values.get(2).toString());
            setMessage("");
            setnStatus("Lien Marked");
            setNameAcctStatus(customer);
            setRemarks(pStatus);
            griddata(acnostatus);
            getNewLienAcNo();
            lienflag = true;
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void griddata(String acnostatus) {
        incis = new ArrayList<AcStatus>();
        try {
            List list = new ArrayList();
            list = statusMaintenanceFacade.getStatusHistory(acnostatus);
            for (int i = 0; i < list.size(); i++) {
                Vector v = (Vector) list.get(i);
                AcStatus tab = new AcStatus();
                tab.setAcctNo(v.get(0).toString());
                tab.setRemark(v.get(1).toString());
                tab.setSpFlag(v.get(2).toString());
                tab.setDate(v.get(3).toString());
                tab.setAmount(v.get(4).toString());
                tab.setAuth(v.get(5).toString());
                tab.setEnterBy(v.get(6).toString());
                tab.setEfftDate(v.get(7).toString());
                incis.add(tab);
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void save() {
        try {
            setMessage("");
            if ((oldcode.equals("")) || (oldcode == null)) {
                this.setMessage("Please Enter Account Number");
                setName("");
                setpStatus("");
                incis.clear();
                return;
            }
            if (!validator.validateString(oldcode)) {
                this.setMessage("Please Enter Valid Account Number.");
                this.setCode("");
                setOldcode("");
                setName("");
                setpStatus("");
                incis.clear();
                return;
            }
            acnostatus = facadeRemote.getNewAccountNumber(oldcode);
            String acctype = facadeRemote.getAccountNature(acnostatus);
            String[] accttype = {CbsConstant.DEPOSIT_SC, CbsConstant.RECURRING_AC};
            int flag = 0;
            for (int i = 0; i < accttype.length; i++) {
                if ((!accttype[i].equalsIgnoreCase(acctype))) {
                    flag = 1;
                } else {
                    flag = 0;
                    break;
                }
            }
            if (flag == 1) {
                setMessage("Please insert account no. of DS, RD nature only");
                return;
            }
            if (this.nStatus.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select New Status");
                setNameAcctStatus("");
                setpStatus("");
                incis.clear();
                return;
            }

            if ((remarks.equals("")) || (remarks == null)) {
                this.setMessage("Please Enter Remarks");
                setNameAcctStatus("");
                setpStatus("");
                incis.clear();
                return;
            }
            if (pStatus.equalsIgnoreCase(nStatus)) {
                setMessage("Current Status and New Status Can'nt be Same!");
                return;
            } else {
                dlTableValuesAcctStatus();
                reFresh();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void reFresh() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            incis = new ArrayList<AcStatus>();
            setMessage("");
            setNameAcctStatus("");
            setpStatus("");
            setCode("");
            setRemarks("");
            setOldcode("");
            setnStatus("--Select--");
            setLienAcNo("");
            setLienAmt("");
            setOldliencode("");
            setLiencode("");
            fflag = "false";
            flag = "false";
            acnostatus = "";
            setReportDate(sdf.parse(getTodayDate()));
            setWefDate(sdf.parse(getTodayDate()));
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void dlTableValuesAcctStatus() {
        try {
            if (dlAcctOpen.isEmpty()) {
                m = 1;
            } else {
                m = Integer.parseInt(dlAcctOpen.get(dlAcctOpen.size() - 1).getSno()) + 1;
            }
            DlAcctOpenReg dl = new DlAcctOpenReg();
            dl.setSno(Integer.toString(m));

            List resultList = statusMaintenanceFacade.returnTableValues(acnostatus);
            if (resultList.size() > 0) {
                Vector ele = (Vector) resultList.get(0);
                dl.setAcno(ele.get(0).toString());
                Details = ele.get(0).toString().substring(2, 10).toString() + "; ROI: " + ele.get(2).toString() + "; PresentAmt: " + lienAmt;
                dl.setDetails("DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY):" + Details);
                dl.setIssueDate(sdf.format(ymd.parse(ele.get(4).toString())));
                if (!ele.get(3).toString().equalsIgnoreCase("null")) {
                    dl.setMatDate(sdf.format(y_m_d_h_m_sFormat.parse(ele.get(3).toString())));
                }
            }
            dl.setTypeOfSec("DATED");
            dl.setSecNature("PRIMARY");
            dl.setSecDes1("SECURED ADVANCES");
            dl.setParticular(facadeRemote.getAccountCode(acnostatus));
            dl.setLien(lienAmt);
            dl.setMatValue(lienAmt);
            dl.setSecDesc2("FIXED AND OTHER DEPOSITS(SPECIFY)");
            dl.setAcno(acnostatus);
            dl.setEnterBy(getUserName());
            dl.setEnterBy(getUserName());
            dl.setEnterDate(getTodayDate());
            dl.setVoucherNo(currentItem.getVoucherNo());
            dlAcctOpen.add(dl);
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    /**END OF ACCOUNT STATUS FORM CODE**/
    public void clearModelPanel() throws ParseException {
        resetForm();
        resetPage();
        setMarkSecurities("");
        setMessageDl("");
        reFresh();
    }

    public String exitForm() {
        try {
            resetValueDl();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public void getNewLienAcNo() {
        if (status.equals("10")) {
            flag = "true";
            if ((oldliencode.equals("")) || (oldliencode == null)) {
                this.setMessage("Please Enter Lien Account Number");
                setNameAcctStatus("");
                setpStatus("");
                incis.clear();
                return;
            } else if (!this.oldliencode.equalsIgnoreCase("") && ((this.oldliencode.length() != 12) && (this.oldliencode.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please Enter valid Lien Account Number.");
                this.setCode("");
                setOldcode("");
                setNameAcctStatus("");
                setpStatus("");
                incis.clear();
                return;
            } else if ((!validator.validateString(oldliencode))) {
                this.setMessage("Please Enter valid Lien Account Number.");
                this.setCode("");
                setOldcode("");
                setNameAcctStatus("");
                setpStatus("");
                incis.clear();
                return;
            }
            try {
                lienAcNo = facadeRemote.getNewAccountNumber(oldliencode);
                String acNature = facadeRemote.getAccountNature(lienAcNo);
                String[] accttype = {CbsConstant.CURRENT_AC, CbsConstant.DEMAND_LOAN, CbsConstant.TERM_LOAN};
                int flag = 0;
                for (int i = 0; i < accttype.length; i++) {
                    if ((!accttype[i].equalsIgnoreCase(acNature))) {
                        flag = 1;
                    } else {
                        flag = 0;
                        break;
                    }
                }
                if (flag == 1) {
                    setMessage("Please insert CA,CC,OD,DL,TL account only");
                    return;
                }
            } catch (Exception e) {
                setMessageaAcct(e.getLocalizedMessage());
            }

        }

    }

    public void printApplicableRoi() {
        this.setMessageDl("");

        if (this.roi == null || this.roi.equals("")) {
            this.setMessageDl("There is no roi defined for this account type.");
            return;
        }
        if (this.acPreferableDr == null || this.acPreferableDr.equals("")) {
            this.setMessageDl("Please define A/C Preferable Dr.");
            return;
        }
        if (this.acPreferableCr == null || this.acPreferableCr.equals("")) {
            this.setMessageDl("Please define A/C Preferable Cr.");
            return;
        }

        this.applicableRoi = String.valueOf(Double.parseDouble(roi) + Double.parseDouble(acPreferableDr) - Double.parseDouble(acPreferableCr));
    }
}
