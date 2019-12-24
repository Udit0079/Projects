/*
 Document   : DDSAccountOpeningRegister
 Created on : 25 Nov, 2010, 2:28:55 PM
 Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]
 */
package com.cbs.web.controller.admin;

import com.cbs.exception.ApplicationException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.admin.FdDdsAccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;

/**
 *
 * @author root
 */
public class DDSAccountOpeningRegister extends BaseBean {

    private String message;
    private String customerId;
    private String accType;
    private String name;
    private String title;
    private String permanentAdd;
    private String panGirNo;
    private String guardianName;
    private String Jt1CustomerId;
    private String Jt2CustomerId;
    private String Jt3CustomerId;
    private String Jt4CustomerId;
    private String jtName1;
    private String jtName2;
    private String jtName3;
    private String jtName4;
    private String document;
    private String IntroducerAccountType;
    private String IntroducerAccountNo;
    private String IntroducerAccountCode;
    private String IntroducerName;
    private String IntroducerAccount;
    private String IntroducerAcctStatus;
    private String husFatherName;
    private String corresAdd;
    private Date dateofBirth;
    private String relationship;
    private String docDetail;
    private String remark;
    private String phoneNo;
    private String orgType;
    private Date acctOpeningDt = new Date();
    private String operatingMode;
    private String actCategory;
    private List<SelectItem> actCategoryList;
    private String nominee;
    private String nomineeRelationship;
    private String customerType;
    private String applyTds;
    private String tdsDetails;
    private String nomineeAdd;
    private String introId;
    private String amount;
    private String day;
    private String roi;
    private Date nomineeDob = new Date();
    private boolean disableFlag;
    private boolean disableTitle;
    private boolean disableName;
    private boolean disableHFName;
    private boolean disablePerAdd;
    private boolean disableCorresAdd;
    private boolean disablePhoneNo;
    private boolean disablePenNo;
    private boolean disabledob;
    private boolean disableSave;
    private boolean disableAcctOpenDate;
    private boolean tdsDetailDisable;
    private boolean jt1Disable;
    private boolean jt2Disable;
    private boolean jt3Disable;
    private boolean jt4Disable;
    int custAge;
    private String acnoIntro;
    private List<SelectItem> accountTypeList;
    private List<SelectItem> titleList;
    private List<SelectItem> documentList;
    private List<SelectItem> introTypeList;
    private List<SelectItem> orgTypeList;
    private List<SelectItem> operatingModeList;
    private List<SelectItem> customerTypeList;
    private List<SelectItem> applyTdsList;
    private List<SelectItem> tdsDetailsList;
    private String intrdAcNo;
    private String intrdDetail;
    private String intrdStatus;
    private String introducerAcctNo;
    private String oldintroducerAcctNo;
    private boolean diasbleintrdId;
    private String agentCode;
    private boolean roiCheck;
    private boolean amountCheck;
    private String grdnName;
    private String jt1Flag;
    private String jt2Flag;
    private String jt3Flag;
    private String jt4Flag;
    private String message1;
    private String focusId;
    private String occupationDesc, acNoLen;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote reportMethodsRemote = null;
    private AccountOpeningFacadeRemote acOpenFacadeRemote = null;
    FdDdsAccountOpeningFacadeRemote fdDdsAccountOpeningFacadeRemote;

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getGrdnName() {
        return grdnName;
    }

    public void setGrdnName(String grdnName) {
        this.grdnName = grdnName;
    }

    public String getJt1Flag() {
        return jt1Flag;
    }

    public void setJt1Flag(String jt1Flag) {
        this.jt1Flag = jt1Flag;
    }

    public String getJt2Flag() {
        return jt2Flag;
    }

    public void setJt2Flag(String jt2Flag) {
        this.jt2Flag = jt2Flag;
    }

    public String getJt3Flag() {
        return jt3Flag;
    }

    public void setJt3Flag(String jt3Flag) {
        this.jt3Flag = jt3Flag;
    }

    public String getJt4Flag() {
        return jt4Flag;
    }

    public void setJt4Flag(String jt4Flag) {
        this.jt4Flag = jt4Flag;
    }

    public boolean isAmountCheck() {
        return amountCheck;
    }

    public void setAmountCheck(boolean amountCheck) {
        this.amountCheck = amountCheck;
    }

    public boolean isRoiCheck() {
        return roiCheck;
    }

    public void setRoiCheck(boolean roiCheck) {
        this.roiCheck = roiCheck;
    }

    public String getAcnoIntro() {
        return acnoIntro;
    }

    public void setAcnoIntro(String acnoIntro) {
        this.acnoIntro = acnoIntro;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public boolean isDiasbleintrdId() {
        return diasbleintrdId;
    }

    public void setDiasbleintrdId(boolean diasbleintrdId) {
        this.diasbleintrdId = diasbleintrdId;
    }

    public String getIntroducerAcctNo() {
        return introducerAcctNo;
    }

    public void setIntroducerAcctNo(String introducerAcctNo) {
        this.introducerAcctNo = introducerAcctNo;
    }

    public String getOldintroducerAcctNo() {
        return oldintroducerAcctNo;
    }

    public void setOldintroducerAcctNo(String oldintroducerAcctNo) {
        this.oldintroducerAcctNo = oldintroducerAcctNo;
    }

    public String getIntrdAcNo() {
        return intrdAcNo;
    }

    public void setIntrdAcNo(String intrdAcNo) {
        this.intrdAcNo = intrdAcNo;
    }

    public String getIntrdDetail() {
        return intrdDetail;
    }

    public void setIntrdDetail(String intrdDetail) {
        this.intrdDetail = intrdDetail;
    }

    public String getIntrdStatus() {
        return intrdStatus;
    }

    public void setIntrdStatus(String intrdStatus) {
        this.intrdStatus = intrdStatus;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getIntroId() {
        return introId;
    }

    public void setIntroId(String introId) {
        this.introId = introId;
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

    public boolean isJt3Disable() {
        return jt3Disable;
    }

    public void setJt3Disable(boolean jt3Disable) {
        this.jt3Disable = jt3Disable;
    }

    public boolean isJt4Disable() {
        return jt4Disable;
    }

    public void setJt4Disable(boolean jt4Disable) {
        this.jt4Disable = jt4Disable;
    }

    public boolean isTdsDetailDisable() {
        return tdsDetailDisable;
    }

    public void setTdsDetailDisable(boolean tdsDetailDisable) {
        this.tdsDetailDisable = tdsDetailDisable;
    }

    public String getIntroducerAccount() {
        return IntroducerAccount;
    }

    public void setIntroducerAccount(String IntroducerAccount) {
        this.IntroducerAccount = IntroducerAccount;
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

    public boolean isDisableAcctOpenDate() {
        return disableAcctOpenDate;
    }

    public void setDisableAcctOpenDate(boolean disableAcctOpenDate) {
        this.disableAcctOpenDate = disableAcctOpenDate;
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

    public boolean isDisabledob() {
        return disabledob;
    }

    public void setDisabledob(boolean disabledob) {
        this.disabledob = disabledob;
    }

    public boolean isDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(boolean disableFlag) {
        this.disableFlag = disableFlag;
    }

    public Date getAcctOpeningDt() {
        return acctOpeningDt;
    }

    public void setAcctOpeningDt(Date acctOpeningDt) {
        this.acctOpeningDt = acctOpeningDt;
    }

    public String getApplyTds() {
        return applyTds;
    }

    public void setApplyTds(String applyTds) {
        this.applyTds = applyTds;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getNomineeRelationship() {
        return nomineeRelationship;
    }

    public void setNomineeRelationship(String nomineeRelationship) {
        this.nomineeRelationship = nomineeRelationship;
    }

    public String getOperatingMode() {
        return operatingMode;
    }

    public void setOperatingMode(String operatingMode) {
        this.operatingMode = operatingMode;
    }

    public List<SelectItem> getOperatingModeList() {
        return operatingModeList;
    }

    public void setOperatingModeList(List<SelectItem> operatingModeList) {
        this.operatingModeList = operatingModeList;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getTdsDetails() {
        return tdsDetails;
    }

    public void setTdsDetails(String tdsDetails) {
        this.tdsDetails = tdsDetails;
    }

    public String getHusFatherName() {
        return husFatherName;
    }

    public void setHusFatherName(String husFatherName) {
        this.husFatherName = husFatherName;
    }

    public String getIntroducerAccountCode() {
        return IntroducerAccountCode;
    }

    public void setIntroducerAccountCode(String IntroducerAccountCode) {
        this.IntroducerAccountCode = IntroducerAccountCode;
    }

    public String getIntroducerAccountNo() {
        return IntroducerAccountNo;
    }

    public void setIntroducerAccountNo(String IntroducerAccountNo) {
        this.IntroducerAccountNo = IntroducerAccountNo;
    }

    public String getIntroducerAccountType() {
        return IntroducerAccountType;
    }

    public void setIntroducerAccountType(String IntroducerAccountType) {
        this.IntroducerAccountType = IntroducerAccountType;
    }

    public String getCorresAdd() {
        return corresAdd;
    }

    public void setCorresAdd(String corresAdd) {
        this.corresAdd = corresAdd;
    }

    public Date getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(Date dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getDocDetail() {
        return docDetail;
    }

    public void setDocDetail(String docDetail) {
        this.docDetail = docDetail;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getJt3CustomerId() {
        return Jt3CustomerId;
    }

    public void setJt3CustomerId(String Jt3CustomerId) {
        this.Jt3CustomerId = Jt3CustomerId;
    }

    public String getJt4CustomerId() {
        return Jt4CustomerId;
    }

    public void setJt4CustomerId(String Jt4CustomerId) {
        this.Jt4CustomerId = Jt4CustomerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
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

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public List<SelectItem> getApplyTdsList() {
        return applyTdsList;
    }

    public void setApplyTdsList(List<SelectItem> applyTdsList) {
        this.applyTdsList = applyTdsList;
    }

    public List<SelectItem> getCustomerTypeList() {
        return customerTypeList;
    }

    public void setCustomerTypeList(List<SelectItem> customerTypeList) {
        this.customerTypeList = customerTypeList;
    }

    public List<SelectItem> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<SelectItem> documentList) {
        this.documentList = documentList;
    }

    public List<SelectItem> getIntroTypeList() {
        return introTypeList;
    }

    public void setIntroTypeList(List<SelectItem> introTypeList) {
        this.introTypeList = introTypeList;
    }

    public List<SelectItem> getOrgTypeList() {
        return orgTypeList;
    }

    public void setOrgTypeList(List<SelectItem> orgTypeList) {
        this.orgTypeList = orgTypeList;
    }

    public List<SelectItem> getTdsDetailsList() {
        return tdsDetailsList;
    }

    public void setTdsDetailsList(List<SelectItem> tdsDetailsList) {
        this.tdsDetailsList = tdsDetailsList;
    }

    public List<SelectItem> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<SelectItem> titleList) {
        this.titleList = titleList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
    
    public DDSAccountOpeningRegister() {
        try {
            fdDdsAccountOpeningFacadeRemote = (FdDdsAccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("FdDdsAccountOpeningFacade");
            reportMethodsRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            this.setAcNoLen(getAcNoLength());
            setTodayDate(sdf.format(date));
            this.setMessage("");
            getTableValues();
            setDisableFlag(true);
            setDisableAcctOpenDate(true);
            setDisableCorresAdd(true);
            setDisableFlag(true);
            setDisableHFName(true);
            setDisableName(true);
            setDisablePenNo(true);
            setDisablePerAdd(true);
            setDisablePhoneNo(true);
            setDisableSave(true);
            setDisableTitle(true);
            setDisabledob(true);
            setTdsDetailDisable(true);
            setJt1Disable(true);
            setJt2Disable(true);
            setJt3Disable(true);
            setJt4Disable(true);
            setDiasbleintrdId(true);
            setJt1Flag("true");
            setJt2Flag("true");
            setJt3Flag("true");
            setJt4Flag("true");
            setGrdnName("true");
            resetValues();
            setFocusId("txtCustomerID");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getTableValues() {
        try {
            titleList = new ArrayList<SelectItem>();
            titleList.add(new SelectItem("0", "Select"));
            titleList.add(new SelectItem("Mr.", "Mr."));
            titleList.add(new SelectItem("Mrs.", "Mrs."));
            titleList.add(new SelectItem("Miss", "Miss"));
            titleList.add(new SelectItem("Kumari", "Kumari"));
            titleList.add(new SelectItem("Master", "Master"));
            titleList.add(new SelectItem("M/s", "M/s"));
            titleList.add(new SelectItem("Other", "Other"));
            List resultList = new ArrayList();
            resultList = acOpenFacadeRemote.getOrganizationType();
            if (resultList.size() < 0) {
                orgTypeList = new ArrayList<SelectItem>();
                orgTypeList.add(new SelectItem("0", " "));
                return;
            } else {
                orgTypeList = new ArrayList<SelectItem>();
                orgTypeList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    orgTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
            
            List acCateList = reportMethodsRemote.getActCategoryDetails();
            if (acCateList.size() < 0) {
                actCategoryList = new ArrayList<SelectItem>();
                actCategoryList.add(new SelectItem("0", " "));
                return;
            } else {
                actCategoryList = new ArrayList<SelectItem>();
                actCategoryList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < acCateList.size(); i++) {
                    Vector ele = (Vector) acCateList.get(i);
                    actCategoryList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
            

            List accountTyperesultList = reportMethodsRemote.getAcctTypeForDs();
            if (accountTyperesultList.size() < 0) {
                accountTypeList = new ArrayList<SelectItem>();
                accountTypeList.add(new SelectItem("0", " "));
                return;
            } else {
                accountTypeList = new ArrayList<SelectItem>();
                accountTypeList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < accountTyperesultList.size(); i++) {
                    Vector ele = (Vector) accountTyperesultList.get(i);
                    accountTypeList.add(new SelectItem(ele.get(0).toString(),"[" +ele.get(0).toString() +"] " + ele.get(1).toString()));
                }
            }

            List operatingModeresultList = reportMethodsRemote.getOperatingModeDetails();
            if (operatingModeresultList.size() < 0) {
                operatingModeList = new ArrayList<SelectItem>();
                operatingModeList.add(new SelectItem("0", ""));
                return;
            } else {
                operatingModeList = new ArrayList<SelectItem>();
                operatingModeList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < operatingModeresultList.size(); i++) {
                    Vector ele = (Vector) operatingModeresultList.get(i);
                    operatingModeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

            List documentresultList = reportMethodsRemote.getDocumentDetails();
            if (documentresultList.size() < 0) {
                documentList = new ArrayList<SelectItem>();
                documentList.add(new SelectItem("0", " "));
                return;
            } else {
                documentList = new ArrayList<SelectItem>();
                documentList.add(new SelectItem("0", "--Select--"));

                for (int i = 0; i < documentresultList.size(); i++) {
                    Vector ele = (Vector) documentresultList.get(i);
                    documentList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

            List introresultList = fdDdsAccountOpeningFacadeRemote.getAcctTypeIntro(getOrgnBrCode());
            if (introresultList.size() < 0) {
                introTypeList = new ArrayList<SelectItem>();
                introTypeList.add(new SelectItem("0", " "));
                return;
            } else {
                introTypeList = new ArrayList<SelectItem>();
                introTypeList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < introresultList.size(); i++) {
                    Vector ele = (Vector) introresultList.get(i);
                    introTypeList.add(new SelectItem(ele.get(0).toString(),"["+ele.get(0).toString() +"] " + ele.get(1).toString()));
                }
            }

        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void getAccontDetails() {
        resetValues();
        //resetValue();
        String p1;
        String p2;
        setMessage1("");
        setMessage("");
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher custIdCheck = p.matcher(this.customerId);
            if (!custIdCheck.matches()) {
                this.setMessage("Please Enter Numeric Customer ID.");
                return;
            } else {
                this.setMessage("");
            }
            
            List resultList = acOpenFacadeRemote.getExistingCustDetail(customerId);
            if (resultList.isEmpty()) {
                resetValues();
                setMessage("This Customer Id has been suspended or does not exists or unauthorized");
                setDisableFlag(true);
                setDisableAcctOpenDate(true);
                setDisableCorresAdd(true);
                setDisableFlag(true);
                setDisableHFName(true);
                setDisableName(true);
                setDisablePenNo(true);
                setDisablePerAdd(true);
                setDisablePhoneNo(true);
                setDisableSave(true);
                setDisableTitle(true);
                setDisabledob(true);
                setTdsDetailDisable(true);
                return;
            } else {
                setDisableFlag(false);
                setDisableSave(false);
                Vector ele = (Vector) resultList.get(0);
                if (!ele.get(0).toString().equalsIgnoreCase("") || ele.get(0).toString().length() != 0) {
                    setTitle(ele.get(0).toString());
                } else {
                    setDisableTitle(false);
                }
                if (ele.get(1).toString().length() != 0 || !ele.get(1).toString().equalsIgnoreCase("")) {
//                    setName(ele.get(1).toString());
                    String custName = ele.get(1).toString().trim() + " " + ele.get(11).toString().trim();
                    custName = custName.trim() + " " + ele.get(12).toString().trim();
                    setName(custName.trim());
                } else {
                    setDisableName(false);
                }

                p1 = ele.get(2).toString();
                p2 = ele.get(3).toString();
                if (p1.equalsIgnoreCase("") || p1 == null) {
                    p1 = "";
                }

                if (p2.equalsIgnoreCase("") || p2 == null) {
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
                if (cr1.equalsIgnoreCase("") || cr1 == null) {
                    cr1 = "";
                }

                if (cr2.equalsIgnoreCase("") || cr2 == null) {
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
                    //setHusFatherName(ele.get(6).toString());
                    String FatherHusName = ele.get(6).toString().trim() + " " + ele.get(13).toString().trim();
                    FatherHusName = FatherHusName.trim() + " " + ele.get(14).toString().trim();
                    setHusFatherName(FatherHusName.trim());
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
                if (ele.get(10) != null) {
                    setIntroId(ele.get(10).toString());
                }
                // setIntroId(ele.get(10).toString());
                String dobAge = sdf.format(dateofBirth);
                int yearDOB = Integer.parseInt(dobAge.substring(6));

                DateFormat dateFormat = new SimpleDateFormat("yyyy");
                java.util.Date date1 = new java.util.Date();
                int thisYear = Integer.parseInt(dateFormat.format(date1));
                custAge = thisYear - yearDOB;
                //---- Added by Manish Kumar
                this.orgType = "";
                this.occupationDesc = "";
                List selectList = acOpenFacadeRemote.getOccupation(this.customerId);
                if(!selectList.isEmpty()){
                    Vector vec = (Vector) selectList.get(0);
                    this.orgType = vec.get(0).toString();
                    this.occupationDesc = vec.get(1).toString();
                }else{
                    this.orgType = "26";
                }
                //-----
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void setJtName1() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        setMessage("");
        if (operatingMode.equals("4") || operatingMode.equals("5") || operatingMode.equals("6") || operatingMode.equals("12")
                || operatingMode.equals("14") || operatingMode.equals("15") || operatingMode.equals("16")) {
            setFocusId("txtCustIdJtName2");
        } else {
            setFocusId("txtNominee");
        }
        if (Jt1CustomerId == null || Jt1CustomerId.equalsIgnoreCase("")) {
            return;
        } else {
            Matcher Jt1CustomerIdCheck = p.matcher(Jt1CustomerId);
            if (!Jt1CustomerIdCheck.matches()) {
                setMessage("JTName1 ID  should be Numeric.");
                setFocusId("txtCustIdJtName1");
                return;
            }
            if (customerId.equalsIgnoreCase(Jt1CustomerId)) {
                setMessage("JtName1 ID And Customer Id can not be Same");
                setFocusId("txtCustIdJtName1");
                return;
            }
        }
        try {
            List resultList;
            resultList = acOpenFacadeRemote.getJtName(Integer.parseInt(Jt1CustomerId));
            if (resultList.isEmpty()) {
                setMessage("This Customer Id has been suspended or does not exists or unauthorized");
            } else {
                Vector ele = (Vector) resultList.get(0);
                if (!ele.get(0).toString().equalsIgnoreCase("")) {
                    setJtName1(ele.get(0).toString().trim() + " " + ele.get(1).toString().trim() + " " + ele.get(2).toString().trim());
                } else {
                    setMessage("This Customer Id has been suspended or does not exists or unauthorized");
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void setJtName2() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        setMessage("");
        if (operatingMode.equals("4") || operatingMode.equals("6") || operatingMode.equals("14")
                || operatingMode.equals("15") || operatingMode.equals("16")) {
            setFocusId("txtCustIdJtName3");
        } else {
            setFocusId("txtNominee");
        }
        if (Jt2CustomerId == null || Jt2CustomerId.equalsIgnoreCase("")) {
            return;
        } else {
            Matcher Jt2CustomerIdCheck = p.matcher(Jt2CustomerId);
            if (!Jt2CustomerIdCheck.matches()) {
                setMessage("JTName2 ID  should be Numeric.");
                setFocusId("txtCustIdJtName2");
                return;
            }
            if (customerId.equalsIgnoreCase(Jt2CustomerId)) {
                setMessage("JtName2 ID And Customer Id can not be Same");
                setFocusId("txtCustIdJtName2");
                return;
            }

            if (Jt1CustomerId.equalsIgnoreCase(Jt2CustomerId)) {
                setMessage("JtName1 ID and JtName2 ID can not be Same");
                setFocusId("txtCustIdJtName2");
                return;
            }
        }
        try {
            List resultList;
            resultList = acOpenFacadeRemote.getJtName(Integer.parseInt(Jt2CustomerId));
            if (resultList.isEmpty()) {
                setMessage("This Customer Id has been suspended or does not exists or unauthorized");
            } else {
                Vector ele = (Vector) resultList.get(0);
                if (!ele.get(0).toString().equalsIgnoreCase("")) {
                    setJtName2(ele.get(0).toString().trim() + " " + ele.get(1).toString().trim() + " " + ele.get(2).toString().trim());
                } else {
                    setMessage("This Customer Id has been suspended or does not exists or unauthorized");
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void setJtName3() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        setMessage("");
        if (operatingMode.equals("4") || operatingMode.equals("14") || operatingMode.equals("15") || operatingMode.equals("16")) {
            setFocusId("txtCustIdJtName4");
        } else {
            setFocusId("txtNominee");
        }
        if (Jt3CustomerId == null || Jt3CustomerId.equalsIgnoreCase("")) {
            return;
        } else {
            Matcher Jt3CustomerIdCheck = p.matcher(Jt3CustomerId);
            if (!Jt3CustomerIdCheck.matches()) {
                setMessage("JTName3 ID  should be Numeric.");
                setFocusId("txtCustIdJtName3");
                return;
            }
            if (customerId.equalsIgnoreCase(Jt3CustomerId)) {
                setMessage("JTName3 ID And Customer Id can not be Same");
                setFocusId("txtCustIdJtName3");
                return;
            }
            if (Jt1CustomerId.equalsIgnoreCase(Jt3CustomerId)) {
                setMessage("JtName1 ID and JtName3 ID can not be Same");
                setFocusId("txtCustIdJtName3");
                return;
            }

            if (Jt2CustomerId.equalsIgnoreCase(Jt3CustomerId)) {
                setMessage("JtName2 ID and JtName3 ID can not be Same");
                setFocusId("txtCustIdJtName3");
                return;
            }
        }
        try {
            List resultList;
            resultList = acOpenFacadeRemote.getJtName(Integer.parseInt(Jt3CustomerId));
            if (resultList.isEmpty()) {
                setMessage("This Customer Id has been suspended or does not exists or unauthorized");
            } else {
                Vector ele = (Vector) resultList.get(0);
                if (!ele.get(0).toString().equalsIgnoreCase("")) {
                    setJtName3(ele.get(0).toString().trim() + " " + ele.get(1).toString().trim() + " " + ele.get(2).toString().trim());
                } else {
                    setMessage("This Customer Id has been suspended or does not exists or unauthorized");
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void setJtName4() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        setMessage("");
        setFocusId("txtNominee");
        if (Jt4CustomerId == null || Jt4CustomerId.equalsIgnoreCase("")) {
            return;
        } else {
            Matcher Jt4CustomerIdCheck = p.matcher(Jt4CustomerId);
            if (!Jt4CustomerIdCheck.matches()) {
                setMessage("JTName 4 ID Id should be Numeric.");
                setFocusId("txtCustIdJtName4");
                return;
            }
            if (customerId.equalsIgnoreCase(Jt4CustomerId)) {
                setMessage("JTName 4 ID And Customer Id can not be Same");
                setFocusId("txtCustIdJtName4");
                return;
            }
            if (Jt1CustomerId.equalsIgnoreCase(Jt4CustomerId)) {
                setMessage("JtName1 ID and JtName4 ID can not be Same");
                setFocusId("txtCustIdJtName4");
                return;
            }

            if (Jt2CustomerId.equalsIgnoreCase(Jt4CustomerId)) {
                setMessage("JtName2 ID and JtName4 ID can not be Same");
                setFocusId("txtCustIdJtName4");
                return;
            }
            if (Jt3CustomerId.equalsIgnoreCase(Jt4CustomerId)) {
                setMessage("JtName3 ID and JtName4 ID can not be Same");
                setFocusId("txtCustIdJtName4");
                return;
            }
        }
        try {
            List resultList;
            resultList = acOpenFacadeRemote.getJtName(Integer.parseInt(Jt4CustomerId));
            if (resultList.isEmpty()) {
                setMessage("This Customer Id has been suspended or does not exists or unauthorized");
            } else {
                Vector ele = (Vector) resultList.get(0);
                if (!ele.get(0).toString().equalsIgnoreCase("")) {
                    setJtName4(ele.get(0).toString().trim() + " " + ele.get(1).toString().trim() + " " + ele.get(2).toString().trim());
                } else {
                    setMessage("This Customer Id has been suspended or does not exists or unauthorized");
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void saveAction() throws ParseException {
        if (valiadtions().equalsIgnoreCase("false")) {
            return;
        }
        if (acctOpeningDt == null) {
            this.setMessage("Please fill the Account Opening Date");
            return;
        }
        if (nomineeDob == null) {
            this.setMessage("Please fill the Nominee DOB");
            return;
        }
        if (dateofBirth == null) {
            this.setMessage("Please fill the Date of Birth");
            return;
        }
        String statusCust = null;
        String statusNom = null;
        int nomAge;
        try {
            String maxVal = fdDdsAccountOpeningFacadeRemote.getMaxToMonForDDS(this.getAccType());
            if(Integer.parseInt(day) > Integer.parseInt(maxVal)){
                this.setMessage("Period(In Months) is Greater than the Defined Slab");
                return;
            }
            
            String nomineeAge = sdf.format(nomineeDob);
            int yearDOB = Integer.parseInt(nomineeAge.substring(6));
            DateFormat dateFormat = new SimpleDateFormat("yyyy");
            java.util.Date date1 = new java.util.Date();
            int thisYear = Integer.parseInt(dateFormat.format(date1));
            nomAge = thisYear - yearDOB;
            if (nomineeDob.equals(sdf.parse(getTodayDate()))) {
                statusNom = "";
            } else if (nomAge >= 18) {
                statusNom = "N";
            } else if (nomAge < 18) {
                statusNom = "Y";
            }
            if (dateofBirth.equals(sdf.parse(getTodayDate()))) {
                statusCust = "";
            } else if (custAge >= 18) {
                statusCust = "MJ";
            } else if (custAge < 18) {
                statusCust = "MN";
            }
            if (nominee.equalsIgnoreCase("") || nominee == null || nominee.equalsIgnoreCase("")) {
                setNominee("");
            }
            String cust = "",alertMsg="";
            String resultList;
            
            String docMsg = acOpenFacadeRemote.getCustAcTdsDocDtl(customerId, "", "C");
            if(docMsg.equalsIgnoreCase("true")){
                alertMsg = "Please Collect TDS Doc From This Customer";
            }
            
            resultList = fdDdsAccountOpeningFacadeRemote.saveAccountOpenDDS(cust, customerId, accType, title, name, acOpenFacadeRemote.removeSomeSpecialChar(corresAdd), acOpenFacadeRemote.removeSomeSpecialChar(permanentAdd), phoneNo,
                    ymd.format(dateofBirth), Integer.parseInt(orgType), Integer.parseInt(operatingMode), statusCust, panGirNo,
                    guardianName, relationship, agentCode, ymd.format(sdf.parse(getTodayDate())), getUserName(), husFatherName, acnoIntro, jtName1,
                    jtName2, jtName3, jtName4, getOrgnBrCode(), nominee, nomineeRelationship, Integer.parseInt(document), docDetail, Float.parseFloat(day), Float.parseFloat(amount), Float.parseFloat(roi),
                    nominee, nomineeAdd, nomineeRelationship, statusNom,
                    ymd.format(nomineeDob), nomAge, Jt1CustomerId, Jt2CustomerId, Jt3CustomerId, Jt4CustomerId,actCategory);
            if (resultList.substring(0, 4).equalsIgnoreCase("true")) {
                String acno = resultList.substring(4, 16);
                resetValues();
                setCustomerId("");
                setMessage1("DATA SAVED SUCCESSFULLY. Generated Customer Account No is --> " + acno + " " + alertMsg);
            } else if (resultList.contains("Customer Verification")) {
                setMessage1("Customer Verification is not completed.");
            } else {
                setMessage1("DATA IS NOT SAVED");
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public String valiadtions() throws ParseException {
        if (customerId == null || customerId.equalsIgnoreCase("")) {
            setMessage("Please Enter the customerID.");
            return "false";
        }
        if (accType == null || accType.equalsIgnoreCase("0")) {
            setMessage("Please Select Account Type.");
            return "false";
        }
        if (agentCode == null || agentCode.equalsIgnoreCase("0")) {
            setMessage("Please Select the agCode.");
            return "false";
        }
        if (title == null || title.equalsIgnoreCase("0")) {
            setMessage("Please Select the Title.");
            return "false";
        }
        if (name == null || name.equalsIgnoreCase("")) {
            setMessage("Please Enter the Customer Name.");
            return "false";
        } else {
            if (name.length() < 3) {
                setMessage("Name should be Minimum Three character.");
                return "false";
            }
        }
//        if (husFatherName == null || husFatherName.equalsIgnoreCase("")) {
//            setMessage("Please Enter Father's or Husband's Name.");
//            return "false";
//        } else {
//            if (husFatherName.length() < 3) {
//                setMessage("Father's or Husband's Name should be Minimum Three character.");
//                return "false";
//            } else {
//                if (!husFatherName.matches("[a-zA-Z ]*")) {
//                    setMessage("Father's or Husband's should be alpha numeric.");
//                    return "false";
//                }
//            }
//        }
        if (phoneNo == null || phoneNo.equalsIgnoreCase("")) {
        } else {
            if (!phoneNo.matches("[0-9,-]*")) {
                setMessage("PhoneNo should be Numerical.");
                return "false";
            }
        }
        if (permanentAdd == null || permanentAdd.equalsIgnoreCase("")) {
            setMessage("Please Enter Permanent Address.");
            return "false";
        } else {
//            if (permanentAdd.length() < 10) {
//                setMessage("Permanent Address should be Minimum 10 character.");
//                return "false";
//            }
        }
        if (corresAdd == null || corresAdd.equalsIgnoreCase("")) {
            setMessage("Please Enter Correspondence Address.");
            return "false";
        } else {
//            if (corresAdd.length() < 10) {
//                setMessage("Correspondence Address should be Minimum 10 character.");
//                return "false";
//            }
        }
        if (dateofBirth == null) {
            setMessage("Please Enter Date Of Birth.");
            return "false";
        } else {
            if (dateofBirth.after(acctOpeningDt)) {
                setMessage("'DOB' must not be greater than 'Account Opening Date");
                return "false";
            }
        }
//        if (orgType == null || orgType.equalsIgnoreCase("0")) {
//            setMessage("Please Select the Occupation.");
//            return "false";
//        }
        
        if (actCategory == null || actCategory.equalsIgnoreCase("0")) {
            setMessage("Please Select the account category.");
            return "false";
        }
        if (operatingMode == null || operatingMode.equalsIgnoreCase("0")) {
            setMessage("Please Select the Operation Mode.");
            return "false";
        } else {
            if (operatingMode.equalsIgnoreCase("2") || operatingMode.equalsIgnoreCase("3") || operatingMode.equalsIgnoreCase("9") || operatingMode.equalsIgnoreCase("7") || operatingMode.equalsIgnoreCase("4")) {
                if (jtName1 == null || jtName1.equalsIgnoreCase("")) {
                    setMessage("Joint Name 1 cannot be empty.Please Enter Customer Id for JTName");
                    return "false";
                }
            } else if (operatingMode.equalsIgnoreCase("5") || operatingMode.equalsIgnoreCase("12")) {
                if (jtName1 == null || jtName1.equalsIgnoreCase("")) {
                    setMessage("Both Joint names cannot be empty.Please Enter Customer Id for JTName");
                    return "false";
                } else if (jtName2 == null || jtName2.equalsIgnoreCase("")) {
                    setMessage("Both Joint names cannot be empty.Please Enter Customer Id for JTName");
                    return "false";
                }
            } else if (operatingMode.equalsIgnoreCase("6")) {
                if (jtName1 == null || jtName1.equalsIgnoreCase("")) {
                    setMessage("Three Joint names cannot be empty.Please Enter Customer Id for JTName");
                    return "false";
                } else if (jtName2 == null || jtName2.equalsIgnoreCase("")) {
                    setMessage("Three Joint names cannot be empty.Please Enter Customer Id for JTName");
                    return "false";
                } else if (jtName3 == null || jtName3.equalsIgnoreCase("")) {
                    setMessage("Three Joint names cannot be empty.Please Enter Customer Id for JTName");
                    return "false";
                }
            } else if (operatingMode.equalsIgnoreCase("14") || operatingMode.equalsIgnoreCase("15") || operatingMode.equalsIgnoreCase("16")) {
                if (jtName1 == null || jtName1.equalsIgnoreCase("")) {
                    setMessage("Four Joint names cannot be empty.Please Enter Customer Id for JTName");
                    return "false";
                } else if (jtName2 == null || jtName2.equalsIgnoreCase("")) {
                    setMessage("Four Joint names cannot be empty.Please Enter Customer Id for JTName");
                    return "false";
                } else if (jtName3 == null || jtName3.equalsIgnoreCase("")) {
                    setMessage("Four Joint names cannot be empty.Please Enter Customer Id for JTName");
                    return "false";
                } else if (jtName4 == null || jtName4.equalsIgnoreCase("")) {
                    setMessage("Four Joint names cannot be empty.Please Enter Customer Id for JTName");
                    return "false";
                }
            } else if (operatingMode.equalsIgnoreCase("11") || operatingMode.equalsIgnoreCase("13")) {
                if (guardianName == null || guardianName.equalsIgnoreCase("")) {
                    setMessage("Please Enter Guardian Name");
                    return "false";
                } else {
                    if (!guardianName.matches("[a-zA-Z ]*")) {
                        this.setMessage("Guardian Name Not Numeric Please Fill String Value ");
                        return "false";
                    }
                    if (relationship == null || relationship.equalsIgnoreCase("")) {
                        setMessage("Please enter Guardian Relation");
                        return "false";
                    } else {
                        if (!relationship.matches("[a-zA-Z ]*")) {
                            setMessage("Guardian Relationship Not Numeric Please Fill String Value ");
                            return "false";
                        }
                    }
                }
            }
        }
        if ((nominee == null) || (nominee.equalsIgnoreCase("")) && (!nomineeAdd.equalsIgnoreCase("") || !nomineeRelationship.equalsIgnoreCase(""))) {
            this.setMessage("First Select Nominee.");
            return "false";
        } else if (!nominee.equalsIgnoreCase("")) {
            this.setMessage("");
            if (!nominee.matches("[a-zA-Z ]*")) {
                setMessage("Nominee  should be non numeric.");
                return "false";
            }
            if (!nomineeRelationship.matches("[a-zA-Z ]*")) {
                setMessage("Nominee Relationship should be non numeric.");
                return "false";
            }
            if (nomineeDob.after(sdf.parse(getTodayDate()))) {
                setMessage("Nominee Dob Not Greater Than Current Date Please Enter the Correct Nominee Dob");
                return "false";
            }
            if ((nomineeAdd == null) || (nomineeAdd.equalsIgnoreCase(""))) {
                this.setMessage("Nominee Address cann't be empty.");
                return "false";
            }
            if (nomineeDob == null) {
                this.setMessage("Nominee Date Of Birth cann't be empty.");
                return "false";
            }
            if ((nomineeRelationship == null) || (nomineeRelationship.equalsIgnoreCase(""))) {
                this.setMessage("Nominee Relationship cann't be empty.");
                return "false";
            }
        }
        if (!guardianName.matches("[a-zA-Z ]*")) {
            this.setMessage("Guardian Name Not Numeric Please Fill String Value ");
            return "false";
        }
        if (!relationship.matches("[a-zA-Z ]*")) {
            setMessage("Guardian Relationship Not Numeric Please Fill String Value ");
            return "false";
        }
        if (document.equalsIgnoreCase("0") && intrdAcNo.equalsIgnoreCase("")) {
            this.setMessage("Please Select The Either Document or Introducer Account No.");
            return "false";
        }
        if (!document.equalsIgnoreCase("0") && ((docDetail == null) || (docDetail.equalsIgnoreCase("")))) {
            this.setMessage("Please Enter the Document Details.");
            return "false";
        }
        if (intrdStatus.equals("CLOSED")) {
            setMessage("Please fill the Right Intoducer Account . This Account is Closed");
            return "false";
        }
        if (amount.equalsIgnoreCase("")) {
            setMessage("Please fill the Installment Amount !! And it Should Be Numeric ");
            return "false";
        }
        if (day.equalsIgnoreCase("")) {
            setMessage("Please fill the period(in months) !! And it Should Be Numeric");
            return "false";
        }
        if (roi.equalsIgnoreCase("")) {
            setMessage("Please fill the ROI !! And it Should Be Numeric");
            return "false";
        }
        if (!amount.matches("[0-9.]*")) {
            setMessage("Please fill the positive value in Installment Amount");
            return "false";
        }
        if (!day.matches("[0-9]*")) {
            setMessage("Please fill positive value in period(in months)");
            return "false";
        }
        if (!roi.matches("[0-9.]*")) {
            setMessage("Please fill positive value in value in ROI ");
            return "false";
        }
        return "true";
    }

    public void setCorressAdd() {
        setCorresAdd(permanentAdd);
    }

    public void resetValues() {
        try {
            setAccType("");
            setAcctOpeningDt(sdf.parse(getTodayDate()));
            setCorresAdd("");
//            setCustomerId("");
            setCustomerType("0");
            setDateofBirth(sdf.parse(getTodayDate()));
            setDocDetail("");
            setDocument("");
            setGuardianName("");
            setHusFatherName("");
            setIntroducerAccountCode("");
            setIntroducerAccountNo("");
            setIntroducerAccountType("");
            setJt1CustomerId("");
            setJt2CustomerId("");
            setJt3CustomerId("");
            setJt4CustomerId("");
            setJtName1("");
            setJtName2("");
            setJtName3("");
            setJtName4("");
            setMessage("");
            setName("");
            setNominee("");
            setNomineeRelationship("");
            setTitle("0");
            setRelationship("");
            setPhoneNo("");
            setPermanentAdd("");
            setPanGirNo("");
            setOrgType("0");
            setActCategory("0");
            setOperatingMode("0");
            setAgentCode("0");
            setRoi("");
            setAmount("");
            setDay("");
            setIntrdAcNo("");
            setIntrdDetail("");
            setIntrdStatus("");
            setIntroducerAcctNo("");
            setOldintroducerAcctNo("");
            setNomineeAdd("");
            this.setNomineeDob(sdf.parse(getTodayDate()));
            setMessage1("");
            this.occupationDesc="";
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void ROI() {
        setMessage("");
        try {
            roiCheck = true;
            amountCheck = true;
            if (!amount.matches("[0-9.]*")) {
                setMessage("Please fill the positive value in Installment Amount");
                amountCheck = false;
                return;
            } else if (day.equalsIgnoreCase("")) {
                setMessage("Please fill the period(in months)");
                roiCheck = false;
                return;
            } else if (!day.matches("[0-9]*")) {
                setMessage("Please fill positive value in period(in months)");
                return;
            } else {
                String maxVal = fdDdsAccountOpeningFacadeRemote.getMaxToMonForDDS(this.getAccType());
                if(Integer.parseInt(day) > Integer.parseInt(maxVal)){
                    this.setMessage("Period(In Months) is Greater than the Defined Slab");
                    roiCheck = false;
                    return;
                }
                
                List resultList;
                resultList = fdDdsAccountOpeningFacadeRemote.getROIForDDS(Double.parseDouble(day), Double.parseDouble(amount), this.getAccType());
                if (resultList != null && resultList.size() == 1) {
                    Vector ele = (Vector) resultList.get(0);
                    setRoi(ele.get(0).toString());
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void introducerDetail() {
        setIntrdAcNo("");
        setIntrdDetail("");
        setIntrdStatus("");
        try {
            amountCheck = true;
            setMessage("");
            if (document == null || document.equalsIgnoreCase("0")) {
                if (oldintroducerAcctNo.equalsIgnoreCase("") || oldintroducerAcctNo == null) {
                    setMessage("Please Enter Introducer Account No");
                    amountCheck = false;
                    return;
                }
            }
            if (!oldintroducerAcctNo.equalsIgnoreCase("")) {
                //if (oldintroducerAcctNo.length() < 12) {
                if (!this.oldintroducerAcctNo.equalsIgnoreCase("") && ((this.oldintroducerAcctNo.length() != 12) && (this.oldintroducerAcctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    setMessage("Please enter valid introducer account no.");
                    amountCheck = false;
                    return;
                } else if (!new Validator().validateStringAllNoSpace(oldintroducerAcctNo)) {
                    setMessage("Please enter valid introducer account no.");
                    amountCheck = false;
                    return;
                }
                String[] values = null;
                String introducerAcDetail;
                FtsPostingMgmtFacadeRemote facadeRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
                introducerAcctNo = facadeRemote.getNewAccountNumber(oldintroducerAcctNo);
                if (introducerAcctNo.equalsIgnoreCase("A/c number does not exist")) {
                    setMessage("This introducer a/c number does not exist");
                    amountCheck = false;
                    return;
                }
                introducerAcDetail = fdDdsAccountOpeningFacadeRemote.introducerAcDetailForDDS(introducerAcctNo);
                if (introducerAcDetail.contains("true")) {
                    setIntrdAcNo(introducerAcDetail.substring(4, 16));
                    String spliter = ":";
                    values = introducerAcDetail.substring(16).split(spliter);
                    setIntrdDetail(values[1]);
                    setIntrdStatus(values[2]);
                    if (values[2].equalsIgnoreCase("CLOSED")) {
                        setMessage("Account has been Closed");
                    } else {
                        acnoIntro = introducerAcDetail.substring(4, 16);
                    }
                } else {
                    setMessage(introducerAcDetail);
                    setIntrdDetail("");
                    setIntrdAcNo("");
                    setIntrdStatus("");
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public String btnExit() {
        try {
            resetValues();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "case1";
    }

    public void getOperatingModeInformation() {
        try {
            setJt1CustomerId("");
            setJt2CustomerId("");
            setJt3CustomerId("");
            setJt4CustomerId("");
            setJtName1("");
            setJtName2("");
            setJtName3("");
            setJtName4("");
            if ((operatingMode.equals("1")) || (operatingMode.equals("8"))) {
                setJt1Flag("true");
                setJt2Flag("true");
                setJt3Flag("true");
                setJt4Flag("true");
                setFocusId("txtNominee");
            } else if ((operatingMode.equals("2")) || (operatingMode.equals("3"))
                    || (operatingMode.equals("9")) || (operatingMode.equals("7"))) {
                setJt1Flag("false");
                setJt2Flag("true");
                setJt3Flag("true");
                setJt4Flag("true");
                setFocusId("txtCustIdJtName1");
            } else if ((operatingMode.equals("5")) || (operatingMode.equals("12"))) {
                setJt1Flag("false");
                setJt2Flag("false");
                setJt3Flag("true");
                setJt4Flag("true");
                setFocusId("txtCustIdJtName1");
            } else if (operatingMode.equals("6")) {
                setJt1Flag("false");
                setJt2Flag("false");
                setJt3Flag("false");
                setJt4Flag("true");
                setFocusId("txtCustIdJtName1");
            } else if ((operatingMode.equals("14")) || (operatingMode.equals("15"))
                    || (operatingMode.equals("16")) || (operatingMode.equals("4"))) {
                setJt1Flag("false");
                setJt2Flag("false");
                setJt3Flag("false");
                setJt4Flag("false");
                setFocusId("txtCustIdJtName1");
            } else if ((operatingMode.equals("11")) || (operatingMode.equals("13"))) {
                setGrdnName("false");
                setFocusId("txtGuardianName");
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void check() {
    }
}
