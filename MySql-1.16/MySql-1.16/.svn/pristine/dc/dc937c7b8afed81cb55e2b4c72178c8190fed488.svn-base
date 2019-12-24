/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.admin.FdDdsAccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;

import com.cbs.utils.ServiceLocator;
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
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class FDAccountOpening extends BaseBean {

    private String message, acNoLen;
    private String customerId;
    private String accType;
    private String Name;
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
    private String introducerAccountNo;
    private String oldIntroducerAccountNo;
    private String IntroducerName;
    private String IntroducerAccount;
    private String IntroducerAcctStatus;
    private String HFName;
    private String corresAdd;
    private Date dateofBirth;
    private String relationship;
    private String docDetail;
    private String remark;
    private String phoneNo;
    private String orgType;
    private Date acctOpeningDt = new Date();
    private String operatingMode;
    private String nominee;
    private String nomineeRelationship;
    private String customerType;
    //private String applyTds;
    //private String tdsDetails;
    private String nomineeAdd;
    private String introId;
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
    private boolean disableIntroAcct;
    //private boolean tdsDetailDisable;
    private boolean jt1Disable;
    private boolean jt2Disable;
    private boolean jt3Disable;
    private boolean jt4Disable;
    private boolean jt1IDDisable;
    private boolean jt2IDDisable;
    private boolean jt3IDDisable;
    private boolean jt4IDDisable;
    int custAge;
    String acnoIntro;
    private String organizationDesc;
    private List<SelectItem> accountTypeList;
    private List<SelectItem> titleList;
    private List<SelectItem> documentList;
    private List<SelectItem> introTypeList;
    private List<SelectItem> orgTypeList;
    private List<SelectItem> operatingModeList;
    private List<SelectItem> customerTypeList;
    //private List<SelectItem> applyTdsList;
    //private List<SelectItem> tdsDetailsList;
    private String actCategory;
    private List<SelectItem> actCategoryList;
    private CommonReportMethodsRemote reportMethodsRemote = null;
    private AccountOpeningFacadeRemote acOpenFacadeRemote = null;
    FdDdsAccountOpeningFacadeRemote fdDdsAccountOpeningFacadeRemote;

    public String getIntroducerAccountNo() {
        return introducerAccountNo;
    }

    public void setIntroducerAccountNo(String introducerAccountNo) {
        this.introducerAccountNo = introducerAccountNo;
    }

    public String getOldIntroducerAccountNo() {
        return oldIntroducerAccountNo;
    }

    public void setOldIntroducerAccountNo(String oldIntroducerAccountNo) {
        this.oldIntroducerAccountNo = oldIntroducerAccountNo;
    }

    public boolean isJt1IDDisable() {
        return jt1IDDisable;
    }

    public void setJt1IDDisable(boolean jt1IDDisable) {
        this.jt1IDDisable = jt1IDDisable;
    }

    public boolean isJt2IDDisable() {
        return jt2IDDisable;
    }

    public void setJt2IDDisable(boolean jt2IDDisable) {
        this.jt2IDDisable = jt2IDDisable;
    }

    public boolean isJt3IDDisable() {
        return jt3IDDisable;
    }

    public void setJt3IDDisable(boolean jt3IDDisable) {
        this.jt3IDDisable = jt3IDDisable;
    }

    public boolean isJt4IDDisable() {
        return jt4IDDisable;
    }

    public void setJt4IDDisable(boolean jt4IDDisable) {
        this.jt4IDDisable = jt4IDDisable;
    }

    public boolean isDisableIntroAcct() {
        return disableIntroAcct;
    }

    public void setDisableIntroAcct(boolean disableIntroAcct) {
        this.disableIntroAcct = disableIntroAcct;
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

//    public boolean isTdsDetailDisable() {
//        return tdsDetailDisable;
//    }
//
//    public void setTdsDetailDisable(boolean tdsDetailDisable) {
//        this.tdsDetailDisable = tdsDetailDisable;
//    }
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

//    public String getApplyTds() {
//        return applyTds;
//    }
//
//    public void setApplyTds(String applyTds) {
//        this.applyTds = applyTds;
//    }
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

//    public String getTdsDetails() {
//        return tdsDetails;
//    }
//
//    public void setTdsDetails(String tdsDetails) {
//        this.tdsDetails = tdsDetails;
//    }
    public String getHFName() {
        return HFName;
    }

    public void setHFName(String HFName) {
        this.HFName = HFName;
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
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
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

//    public List<SelectItem> getApplyTdsList() {
//        return applyTdsList;
//    }
//
//    public void setApplyTdsList(List<SelectItem> applyTdsList) {
//        this.applyTdsList = applyTdsList;
//    }
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

//    public List<SelectItem> getTdsDetailsList() {
//        return tdsDetailsList;
//    }
//
//    public void setTdsDetailsList(List<SelectItem> tdsDetailsList) {
//        this.tdsDetailsList = tdsDetailsList;
//    }
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

    public FDAccountOpening() {
        try {
            fdDdsAccountOpeningFacadeRemote = (FdDdsAccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("FdDdsAccountOpeningFacade");
            reportMethodsRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            this.setAcNoLen(getAcNoLength());
            setTodayDate(sdf.format(date));
            setMessage("");
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
            //setTdsDetailDisable(true);
            setJt1Disable(true);
            setJt2Disable(true);
            setJt3Disable(true);
            setJt4Disable(true);
            setJt1IDDisable(true);
            setJt2IDDisable(true);
            setJt3IDDisable(true);
            setJt4IDDisable(true);
            setDisableIntroAcct(true);
            resetValues();
            actCategoryList = new ArrayList<SelectItem>();
            actCategoryList.add(new SelectItem("0", "--SELECT--"));
            List actCagtList = reportMethodsRemote.getActCategoryDetails();
            if (!actCagtList.isEmpty()) {
                for (int k = 0; k < actCagtList.size(); k++) {
                    Vector ele2 = (Vector) actCagtList.get(k);
                    actCategoryList.add(new SelectItem(ele2.get(0).toString(), ele2.get(1).toString()));
                }
            }

        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getTableValues() {
        try {
            titleList = new ArrayList<SelectItem>();
            titleList.add(new SelectItem("0", "--SELECT--"));
            titleList.add(new SelectItem("Mr.", "Mr."));
            titleList.add(new SelectItem("Mrs.", "Mrs."));
            titleList.add(new SelectItem("Miss", "Miss"));
            titleList.add(new SelectItem("Kumari", "Kumari"));
            titleList.add(new SelectItem("Master", "Master"));
            titleList.add(new SelectItem("M/s", "M/s"));
            titleList.add(new SelectItem("Other", "Other"));

            
            List cuTypeList = new ArrayList();
            cuTypeList = reportMethodsRemote.getRefRecList("235");
            if (cuTypeList.size() < 0) {
                customerTypeList = new ArrayList<SelectItem>();
                customerTypeList.add(new SelectItem("0", "--SELECT--"));
                return;
            } else {
                customerTypeList = new ArrayList<SelectItem>();
                customerTypeList.add(new SelectItem("0", "--SELECT--"));
                for (int i = 0; i < cuTypeList.size(); i++) {
                    Vector ele = (Vector) cuTypeList.get(i);
                    customerTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
            
//            customerTypeList = new ArrayList<SelectItem>();
//            customerTypeList.add(new SelectItem("0", "--SELECT--"));
//            customerTypeList.add(new SelectItem("SC", "Senior Citizen"));
//            customerTypeList.add(new SelectItem("ST", "Staff"));
//            customerTypeList.add(new SelectItem("OT", "Others"));

//            applyTdsList = new ArrayList<SelectItem>();
//            applyTdsList.add(new SelectItem("0", "--SELECT--"));
//            applyTdsList.add(new SelectItem("Y", "Yes"));
//            applyTdsList.add(new SelectItem("N", "No"));
            //applyTdsList.add(new SelectItem("C", "Compulsery"));

//            tdsDetailsList = new ArrayList<SelectItem>();
//            tdsDetailsList.add(new SelectItem("0", "--SELECT--"));
//            tdsDetailsList.add(new SelectItem("Form 15H", "Form 15H"));
//            tdsDetailsList.add(new SelectItem("Form 15G", "Form 15G"));
//            tdsDetailsList.add(new SelectItem("Exemption certificate", "Exemption certificate"));
            //tdsDetailsList.add(new SelectItem("Member", "Member"));
            List resultList = new ArrayList();
            resultList = acOpenFacadeRemote.getOrganizationType();
            if (resultList.size() < 0) {
                orgTypeList = new ArrayList<SelectItem>();
                orgTypeList.add(new SelectItem("0", "--SELECT--"));
                return;
            } else {
                orgTypeList = new ArrayList<SelectItem>();
                orgTypeList.add(new SelectItem("0", "--SELECT--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    orgTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

            List accountTyperesultList = reportMethodsRemote.getFDMSAcTypeList();
            if (accountTyperesultList.size() < 0) {
                accountTypeList = new ArrayList<SelectItem>();
                accountTypeList.add(new SelectItem("0", "--SELECT--"));
                return;
            } else {
                accountTypeList = new ArrayList<SelectItem>();
                accountTypeList.add(new SelectItem("0", "--SELECT--"));
                for (int i = 0; i < accountTyperesultList.size(); i++) {
                    Vector ele = (Vector) accountTyperesultList.get(i);
                    accountTypeList.add(new SelectItem(ele.get(0).toString(),"[" + ele.get(0).toString() + "] "+ele.get(1).toString()));
                }
            }

            List operatingModeresultList = reportMethodsRemote.getOperatingModeDetails();
            if (operatingModeresultList.size() < 0) {
                operatingModeList = new ArrayList<SelectItem>();
                operatingModeList.add(new SelectItem("0", "--SELECT--"));
                return;
            } else {
                operatingModeList = new ArrayList<SelectItem>();
                operatingModeList.add(new SelectItem("0", "--SELECT--"));
                for (int i = 0; i < operatingModeresultList.size(); i++) {
                    Vector ele = (Vector) operatingModeresultList.get(i);
                    operatingModeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

            List documentresultList = reportMethodsRemote.getDocumentDetails();
            if (documentresultList.size() < 0) {
                documentList = new ArrayList<SelectItem>();
                documentList.add(new SelectItem("0", "--SELECT--"));
                return;
            } else {
                documentList = new ArrayList<SelectItem>();
                documentList.add(new SelectItem("0", "--SELECT--"));

                for (int i = 0; i < documentresultList.size(); i++) {
                    Vector ele = (Vector) documentresultList.get(i);
                    documentList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getAccountDetails() {
        String p1;
        String p2;
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            setMessage("");
            if (customerId.equalsIgnoreCase("") || customerId == null) {
                setCustomerId("");
                setMessage("Please Enter the Customer ID.");
                return;
            } else {
                Matcher customerIdCheck = p.matcher(customerId);
                if (!customerIdCheck.matches()) {
                    setCustomerId("");
                    setMessage("Please Enter the Numeric Value For Customer Id.");
                    return;
                }
            }

            List resultList = acOpenFacadeRemote.getExistingCustDetail(customerId);
            if (resultList.isEmpty()) {
                resetValues();
                setMessage("This Customer Id has been suspended or does not exists or unauthorized");
                setCustomerId("");
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
                //setTdsDetailDisable(true);
                return;
            } else {
                String temp = customerId;
                resetValues();
                setCustomerId(temp);
                setDisableFlag(false);
                setDisableSave(false);
                Vector ele = (Vector) resultList.get(0);
                if (!ele.get(0).toString().equalsIgnoreCase("") || ele.get(0).toString().length() != 0) {
                    setTitle(ele.get(0).toString());
                } else {
                    setDisableTitle(false);
                }
                if (ele.get(1).toString().length() != 0 || !ele.get(1).toString().equalsIgnoreCase("") || ele.get(1) != null) {
                    //setName(ele.get(1).toString());
                    String custName = ele.get(1).toString().trim() + " " + ele.get(11).toString().trim();
                    custName = custName.trim() + " " + ele.get(12).toString().trim();
                    setName(custName.trim());
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
                String dobAge = sdf.format(dateofBirth);
                int yearDOB = Integer.parseInt(dobAge.substring(6));

                DateFormat dateFormat = new SimpleDateFormat("yyyy");
                java.util.Date date1 = new java.util.Date();
                int thisYear = Integer.parseInt(dateFormat.format(date1));
                custAge = thisYear - yearDOB;
                if (custAge > 60) {
                    setCustomerType("SC");
                } else {
                    setCustomerType("OT");
                }
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
            setMessage(ex.getMessage());
        }
    }

    public void getIntroducerAcctDetails() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            setMessage("");
            if (document == null || document.equalsIgnoreCase("0")) {
                if (oldIntroducerAccountNo.equalsIgnoreCase("") || oldIntroducerAccountNo == null) {
                    setMessage("Please Enter Introducer Account No");
                    return;
                }
            }
            if (oldIntroducerAccountNo.length() == 0) {
                return;
            }
            String[] values = null;
            String resultList;
            FtsPostingMgmtFacadeRemote facadeRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            introducerAccountNo = facadeRemote.getNewAccountNumber(oldIntroducerAccountNo);
            resultList = acOpenFacadeRemote.introducerAcDetailForDlFDAc(introId, introducerAccountNo);
            if (resultList.substring(0, 4).equalsIgnoreCase("true")) {
                setIntroducerAccount(resultList.substring(4, 16));
                String spliter = ":";
                values = resultList.substring(16).split(spliter);
                setIntroducerName(values[1]);
                setIntroducerAcctStatus(values[2]);
                if (values[2].equalsIgnoreCase("CLOSED")) {
                    setMessage("Account has been Closed");
                    setIntroducerAccountNo("");
                    setOldIntroducerAccountNo("");
                    setIntroducerName("");
                    setIntroducerAcctStatus("");
                    setIntroducerAccount("");
                } else {
                    acnoIntro = resultList.substring(4, 16);

                }
            } else {
                setIntroducerAccountNo("");
                setOldIntroducerAccountNo("");
                setMessage(resultList);
                setIntroducerName("");
                setIntroducerAccount("");
                setIntroducerAcctStatus("");
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void setJtName1() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        setMessage("");
        if (Jt1CustomerId == null || Jt1CustomerId.equalsIgnoreCase("")) {
            setMessage("Please Enter Customer Id For JT Name 1.");
            return;
        } else {
            Matcher Jt1CustomerIdCheck = p.matcher(Jt1CustomerId);
            if (!Jt1CustomerIdCheck.matches()) {
                setJt1CustomerId("");
                setMessage("JTName1 ID  should be Numerical.");
                return;
            }
            if (customerId.equalsIgnoreCase(Jt1CustomerId)) {
                setJt1CustomerId("");
                setMessage("JtName1 ID And Customer Id can not be Same");
                return;
            }
        }
        try {
            List resultList;
            resultList = acOpenFacadeRemote.getJtName(Integer.parseInt(Jt1CustomerId));
            if (resultList.isEmpty()) {
                setJt1CustomerId("");
                setMessage("This Customer Id has been suspended or does not exists or unauthorized");
            } else {
                Vector ele = (Vector) resultList.get(0);
                if (!ele.get(0).toString().equalsIgnoreCase("")) {
                    setJtName1(ele.get(0).toString().trim() + " " + ele.get(1).toString().trim() + " " + ele.get(2).toString().trim());
                } else {
                    setJt1CustomerId("");
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
        if (Jt2CustomerId == null || Jt2CustomerId.equalsIgnoreCase("")) {
            if (operatingMode.equals("4")) {
                return;
            } else {
                setMessage("Please Enter Customer Id For JT Name 2.");
                return;
            }
        } else {
            Matcher Jt2CustomerIdCheck = p.matcher(Jt2CustomerId);
            if (!Jt2CustomerIdCheck.matches()) {
                setJt2CustomerId("");
                setMessage("JTName2 ID  should be Numerical.");
                return;
            }
            if (customerId.equalsIgnoreCase(Jt2CustomerId)) {
                setJt2CustomerId("");
                setMessage("JtName2 ID And Customer Id can not be Same");
                return;
            }
            if (this.Jt1CustomerId.equalsIgnoreCase(this.Jt2CustomerId)) {
                this.setJt2CustomerId("");
                this.setMessage("TWO JOINT HOLDERS NAME CANNOT BE SAME !!!");
                return;
            }
        }
        try {
            List resultList;
            resultList = acOpenFacadeRemote.getJtName(Integer.parseInt(Jt2CustomerId));
            if (resultList.isEmpty()) {
                setJt2CustomerId("");
                setMessage("This Customer Id has been suspended or does not exists or unauthorized");
            } else {
                Vector ele = (Vector) resultList.get(0);
                if (!ele.get(0).toString().equalsIgnoreCase("")) {
                    setJtName2(ele.get(0).toString().trim() + " " + ele.get(1).toString().trim() + " " + ele.get(2).toString().trim());
                } else {
                    setJt2CustomerId("");
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
        if (Jt3CustomerId == null || Jt3CustomerId.equalsIgnoreCase("")) {
            if (operatingMode.equals("4")) {
                return;
            } else {
                this.setMessage("Please Enter Customer Id For JT Name 3.");
                return;
            }
        } else {
            Matcher Jt3CustomerIdCheck = p.matcher(Jt3CustomerId);
            if (!Jt3CustomerIdCheck.matches()) {
                setJt3CustomerId("");
                setMessage("JTName3 ID  should be Numerical.");
                return;
            }
            if (customerId.equalsIgnoreCase(Jt3CustomerId)) {
                setJt3CustomerId("");
                setMessage("JTName3 ID And Customer Id can not be Same");
                return;
            }
            if (this.Jt1CustomerId.equalsIgnoreCase(this.Jt3CustomerId) || this.Jt2CustomerId.equalsIgnoreCase(this.Jt3CustomerId)) {
                this.setJt3CustomerId("");
                this.setMessage("JOINT HOLDERS NAME CANNOT BE SAME !!!");
                return;
            }
        }
        try {
            List resultList;
            resultList = acOpenFacadeRemote.getJtName(Integer.parseInt(Jt3CustomerId));
            if (resultList.isEmpty()) {
                setJt3CustomerId("");
                setMessage("This Customer Id has been suspended or does not exists or unauthorized");
            } else {
                Vector ele = (Vector) resultList.get(0);
                if (!ele.get(0).toString().equalsIgnoreCase("")) {
                    setJtName3(ele.get(0).toString().trim() + " " + ele.get(1).toString().trim() + " " + ele.get(2).toString().trim());
                } else {
                    setJt3CustomerId("");
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
        if (Jt4CustomerId == null || Jt4CustomerId.equalsIgnoreCase("")) {
            if (operatingMode.equals("4")) {
                return;
            } else {
                this.setMessage("Please Enter Customer Id For JT Name 4.");
                return;
            }
        } else {
            Matcher Jt4CustomerIdCheck = p.matcher(Jt4CustomerId);
            if (!Jt4CustomerIdCheck.matches()) {
                setJt4CustomerId("");
                setMessage("JTName 4 ID Id should be Numerical.");
                return;
            }
            if (customerId.equalsIgnoreCase(Jt4CustomerId)) {
                setJt4CustomerId("");
                setMessage("JTName 4 ID And Customer Id can not be Same");
                return;
            }
            if (this.Jt1CustomerId.equalsIgnoreCase(this.Jt4CustomerId) || this.Jt2CustomerId.equalsIgnoreCase(this.Jt4CustomerId) || this.Jt3CustomerId.equalsIgnoreCase(this.Jt4CustomerId)) {
                this.setJt4CustomerId("");
                this.setMessage("JOINT HOLDERS NAME CANNOT BE SAME !!!");
                return;
            }
        }
        try {
            List resultList;
            resultList = acOpenFacadeRemote.getJtName(Integer.parseInt(Jt4CustomerId));
            if (resultList.isEmpty()) {
                setJt4CustomerId("");
                setMessage("This Customer Id has been suspended or does not exists or unauthorized");
            } else {
                Vector ele = (Vector) resultList.get(0);
                if (!ele.get(0).toString().equalsIgnoreCase("")) {
                    setJtName4(ele.get(0).toString().trim() + " " + ele.get(1).toString().trim() + " " + ele.get(2).toString().trim());
                } else {
                    setJt4CustomerId("");
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
        String statusCust = null;
        String statusNom = null;
        int nomAge;
        try {
            String s1 = onblurGuardianName();
            if (!s1.equalsIgnoreCase("true")) {
                this.setMessage(s1);
                return;
            } else {
                this.setMessage("");
            }
            String s2 = onblurRelationshipWithGuardian();
            if (!s2.equalsIgnoreCase("true")) {
                this.setMessage(s2);
                return;
            } else {
                this.setMessage("");
            }
            String s3 = onblurNominee();
            if (!s3.equalsIgnoreCase("true")) {
                this.setMessage(s3);
                return;
            } else {
                this.setMessage("");
            }
            String s4 = onblurNomineeRelation();
            if (!s4.equalsIgnoreCase("true")) {
                this.setMessage(s4);
                return;
            } else {
                this.setMessage("");
            }
            String s5 = onblurNomineeDateofBirth();
            if (!s5.equalsIgnoreCase("true")) {
                this.setMessage(s5);
                return;
            } else {
                this.setMessage("");
            }
            String nomineeAge = sdf.format(nomineeDob);
            int yearDOB = Integer.parseInt(nomineeAge.substring(6));

            DateFormat dateFormat = new SimpleDateFormat("yyyy");
            java.util.Date date1 = new java.util.Date();
            int thisYear = Integer.parseInt(dateFormat.format(date1));
            nomAge = thisYear - yearDOB;
            if (nomAge > 60) {
                setCustomerType("SC");
            }

            if (sdf.format(nomineeDob).equals(getTodayDate())) {
                statusNom = "";
            } else if (nomAge >= 18) {
                statusNom = "N";
            } else if (nomAge < 18) {
                statusNom = "Y";
            }

            if (sdf.format(dateofBirth).equals(getTodayDate())) {
                statusCust = "";
            } else if (custAge >= 18) {
                statusCust = "MJ";
            } else if (custAge < 18) {
                statusCust = "MN";
            }

            if (nominee.equalsIgnoreCase("") || nominee == null || nominee.equalsIgnoreCase("")) {
                setNominee("");
            }

            String cust = "", alertMsg = "";
            String resultList;

            String docMsg = acOpenFacadeRemote.getCustAcTdsDocDtl(customerId, "", "C");
            if (docMsg.equalsIgnoreCase("true")) {
                alertMsg = "Please Collect TDS Doc From This Customer";
            }

            if (actCategory == null || actCategory.equalsIgnoreCase("0")) {
                setMessage("Please Select Account Category");
                return;
            }

            resultList = fdDdsAccountOpeningFacadeRemote.saveAccountOpenFd(cust, customerId, accType, title, Name,acOpenFacadeRemote.removeSomeSpecialChar(corresAdd), acOpenFacadeRemote.removeSomeSpecialChar(permanentAdd), phoneNo,
                    ymd.format(dateofBirth), Integer.parseInt(orgType), Integer.parseInt(operatingMode), statusCust, panGirNo,
                    guardianName, relationship, "01", ymd.format(sdf.parse(getTodayDate())), getUserName(), HFName, acnoIntro, jtName1,
                    jtName2, jtName3, jtName4, getOrgnBrCode(), nominee, nomineeRelationship, Integer.parseInt(document), docDetail,
                    remark, customerType, "Y", "", nominee, nomineeAdd, nomineeRelationship, statusNom,
                    ymd.format(nomineeDob), nomAge, Jt1CustomerId, Jt2CustomerId, Jt3CustomerId, Jt4CustomerId, actCategory);
            if (resultList.substring(0, 4).equalsIgnoreCase("true")) {
                resetValues();
                setMessage("Data Saved Successfully. Generated Customer Account No :" + resultList.substring(4, 16) + "." + " " + alertMsg);
            } else {
                setMessage(resultList);
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public String valiadtions() throws ParseException {
        try {
            if (customerId == null || customerId.equalsIgnoreCase("")) {
                setMessage("Please Enter the customerID.");
                return "false";
            }
            if (accType == null || accType.equalsIgnoreCase("0")) {
                setMessage("Please Select Account Type.");
                return "false";
            }
            if (title == null || title.equalsIgnoreCase("0")) {
                setMessage("Please Select the Title.");
                return "false";
            }
            if (Name == null || Name.equalsIgnoreCase("")) {
                setMessage("Please Enter the Customer Name.");
                return "false";
            } else {
                if (Name.length() < 3) {
                    setName("");
                    setMessage("Name should be Minimum Three character.");
                    return "false";
                }
            }

//            if (HFName == null || HFName.equalsIgnoreCase("")) {
//                setMessage("Please Enter Father's or Husband's Name.");
//                return "false";
//            } else {
//                if (HFName.length() < 3) {
//                    setMessage("Father's or Husband's Name should be Minimum Three character.");
//                    return "false";
//                } else {
//                    if (!HFName.matches("[a-zA-Z ]*")) {
//                        setHFName("");
//                        setMessage("Please Enter alpha Numeric Value for Father's or Husband's.");
//                        return "false";
//                    }
//                }
//            }
            if (phoneNo == null || phoneNo.equalsIgnoreCase("")) {
            } else {
                if (!phoneNo.matches("[0-9+,-]*")) {
                    setPhoneNo("");
                    setMessage("Please Enter Numeric Value for Phone No.");
                    return "false";
                }
            }

            if (permanentAdd == null || permanentAdd.equalsIgnoreCase("")) {
                setMessage("Please Enter Permanent Address.");
                return "false";
            } else {
//                if (permanentAdd.length() < 10) {
//                    setMessage("Permanent Address should be Minimum 10 character.");
//                    return "false";
//                }
            }
            if (corresAdd == null || corresAdd.equalsIgnoreCase("")) {
                setMessage("Please Enter Correspondence Address.");
                return "false";
            } else {
//                if (corresAdd.length() < 10) {
//                    setCorresAdd("");
//                    setMessage("Correspondence Address should be Minimum 10 character.");
//                    return "false";
//                }
            }

            if (dateofBirth == null) {
                setMessage("Please Enter Date Of Birth.");
                return "false";
            } else {
                if (dateofBirth.after(acctOpeningDt)) {
                    setMessage("'DOB' must not be greater than Account Opening Date");
                    return "false";
                }
            }

//            if (orgType == null || orgType.equalsIgnoreCase("0")) {
//                setMessage("Please Select the Organization Type.");
//                return "false";
//            }

            if (operatingMode == null || operatingMode.equalsIgnoreCase("0")) {
                setMessage("Please Select the Operating Mode.");
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
                            setGuardianName("");
                            setMessage("Please Enter alpha numeric Value for Guardian Name.");
                            return "false";
                        }
                        if (relationship == null || relationship.equalsIgnoreCase("")) {
                            setMessage("Please enter Guardian Relation.");
                            return "false";
                        } else {
                            if (!relationship.matches("[a-zA-Z ]*")) {
                                setRelationship("");
                                setMessage("Please Enter alpha numeric Value for Guardian Relation.");
                                return "false";
                            }
                        }
                    }
                }
            }
            if (nominee == null || nominee.equalsIgnoreCase("")) {
            } else {

                if (!nominee.matches("[a-zA-Z ]*")) {
                    setNominee("");
                    setMessage("Please Enter alpha numeric Value for Nominee Name.");
                    return "false";
                }
                if (nomineeAdd == null || nomineeAdd.equalsIgnoreCase("")) {
                    setMessage("Please Enter Nominee Address");
                    return "false";
                } else {
                    if (!nomineeAdd.matches("[a-zA-Z0-9,-/ ]*")) {
                        setNomineeAdd("");
                        setMessage("Please Enter alpha numeric Value for Nominee Address.");
                        return "false";
                    }
                }
                if (nomineeRelationship == null || nomineeRelationship.equalsIgnoreCase("")) {
                    setMessage("Please Enter Nominee Relationship");
                    return "false";
                } else {
                    if (!nomineeRelationship.matches("[a-zA-Z ]*")) {
                        setNomineeRelationship("");
                        setMessage("Please Enter alpha numeric Value for Nominee Relationship.");
                        return "false";
                    }
                }
                if (nomineeDob == null) {
                    setMessage("Please Enter the Nominee Dob");
                    return "false";
                }
            }
            if (customerType == null || customerType.equalsIgnoreCase("0")) {
                setMessage("Please Select the Customer Type.");
                return "false";
            }
//            if (applyTds == null || applyTds.equalsIgnoreCase("0")) {
//                setMessage("Please Select the Apply TDS.");
//                return "false";
//            } else {
//                if (applyTds.equalsIgnoreCase("N")) {
//                    if (tdsDetails.equalsIgnoreCase("0")) {
//                        setMessage("Please Select the TDS Details.");
//                        return "false";
//                    }
//                }
//            }
            if ((document == null || document.equals("0")) && (acnoIntro == null || acnoIntro.equals("") || acnoIntro.length() == 0)) {
                setMessage("Please Enter Either Introducer Account No or Document.");
                return "false";
            }

            if (acnoIntro == null || acnoIntro.equals("")) {
                if ((document == null || document.equals("0"))/* && (docDetail == null || docDetail.equals(""))*/) {
                    setMessage("Please Select the Document");
                    return "false";
                }
            }
            if (!document.equals("0")) {
                if (docDetail == null || docDetail.equals("")) {
                    setMessage("Please enter the document details");
                    return "false";
                }
            }


        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
        return "true";
    }

//    public void setDisableTdsDetails() {
//        if (applyTds.equalsIgnoreCase("0")) {
//            setTdsDetailDisable(true);
//        } else if (applyTds.equalsIgnoreCase("Y")) {
//            setTdsDetailDisable(true);
//            setTdsDetails("0");
//        } else if (applyTds.equalsIgnoreCase("N")) {
//            setTdsDetailDisable(false);
//        } else if (applyTds.equalsIgnoreCase("C")) {
//            setTdsDetailDisable(true);
//            setTdsDetails("0");
//        }
//    }
    public void setCorressAdd() {
        setCorresAdd(permanentAdd);
    }

    public void resetValues() throws ParseException {
        setAccType("");
        setAcctOpeningDt(sdf.parse(getTodayDate()));
        //setApplyTds("0");
        setCorresAdd("");
        setCustomerType("0");
        setDateofBirth(sdf.parse(getTodayDate()));
        setDocDetail("");
        setDocument("");
        setGuardianName("");
        setHFName("");
        setIntroducerAccountNo("");
        setOldIntroducerAccountNo("");
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
        //setTdsDetails("");
        setRemark("");
        setRelationship("");
        setPhoneNo("");
        setPermanentAdd("");
        setPanGirNo("");
        setOrgType("0");
        setOperatingMode("0");
        setActCategory("0");
        setCustomerId("");
        this.setNomineeAdd("");
        this.setNomineeDob(sdf.parse(getTodayDate()));
        this.setIntroducerAccount("");
        this.setIntroducerAcctStatus("");
        this.setIntroducerName("");
        this.organizationDesc ="";
    }

    public String exitBtnAction() {
        try {
            resetValues();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public String onblurGuardianName() {
        if (!this.guardianName.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher guardianNameCheck = p.matcher(this.guardianName);
            if (!guardianNameCheck.matches()) {
                setGuardianName("");
                this.setMessage("Please Enter Guardian Name Correctly.");
                return "Please Enter Guardian Name Correctly.";
            } else {
                this.setMessage("");
                return "true";
            }
        } else {
            this.setMessage("");
            return "true";
        }
    }

    public String onblurRelationshipWithGuardian() {
        if (!this.relationship.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher relationshipCheck = p.matcher(this.relationship);
            if (!relationshipCheck.matches()) {
                setRelationship("");
                this.setMessage("Please Enter Relationship with Guardian Correctly.");
                return "Please Enter Relationship with Guardian Correctly.";
            } else {
                this.setMessage("");
                return "true";
            }
        } else {
            this.setMessage("");
            return "true";
        }
    }

    public String onblurNominee() {
        if (!this.nominee.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher nomineeCheck = p.matcher(this.nominee);
            if (!nomineeCheck.matches()) {
                setNominee("");
                this.setMessage("Please Enter Nominee Correctly.");
                return "Please Enter Nominee Correctly.";
            } else {
                this.setMessage("");
                return "true";
            }
        } else {
            this.setMessage("");
            return "true";
        }
    }

    public String onblurNomineeRelation() {
        if (!this.nomineeRelationship.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher nomineeRelationshipCheck = p.matcher(this.nomineeRelationship);
            if (!nomineeRelationshipCheck.matches()) {
                setNomineeRelationship("");
                this.setMessage("Please Enter Nominee Relationship Correctly.");
                return "Please Enter Nominee Relationship Correctly.";
            } else {
                this.setMessage("");
                return "true";
            }
        } else {
            this.setMessage("");
            return "true";
        }
    }

    public String onblurNomineeDateofBirth() {
        this.setMessage("");
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        String str = "";
        try {
            List dateDif = acOpenFacadeRemote.dateDiffStatementFreqDate(sd.format(this.nomineeDob));
            Vector vecDateDiff = (Vector) dateDif.get(0);
            String strDateDiff = vecDateDiff.get(0).toString();
            if (Integer.parseInt(strDateDiff) < 0) {
                this.setMessage("You can't select the date after the current date.");
                return str = "You can't select the date after the current date.";
            } else {
                this.setMessage("");
                return str = "true";
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return str;
    }

    public void ddOperationModeDisableJtName() {
        setMessage("");
        setJt1CustomerId("");
        setJt2CustomerId("");
        setJt3CustomerId("");
        setJt4CustomerId("");
        setJtName1("");
        setJtName2("");
        setJtName3("");
        setJtName4("");
        if (operatingMode.equalsIgnoreCase("1")) {
            setJt1IDDisable(true);
            setJt2IDDisable(true);
            setJt3IDDisable(true);
            setJt4IDDisable(true);
        }
        if (operatingMode.equalsIgnoreCase("2") || operatingMode.equalsIgnoreCase("3")
                || operatingMode.equalsIgnoreCase("9") || operatingMode.equalsIgnoreCase("7")) {
            setJt1IDDisable(false);
            setJt2IDDisable(true);
            setJt3IDDisable(true);
            setJt4IDDisable(true);
        } else if (operatingMode.equalsIgnoreCase("4")) {
            setJt1IDDisable(false);
            setJt2IDDisable(false);
            setJt3IDDisable(false);
            setJt4IDDisable(false);
        } else if (operatingMode.equalsIgnoreCase("5") || operatingMode.equalsIgnoreCase("12")) {
            setJt1IDDisable(false);
            setJt2IDDisable(false);
            setJt3IDDisable(true);
            setJt4IDDisable(true);
        } else if (operatingMode.equalsIgnoreCase("6")) {
            setJt1IDDisable(false);
            setJt2IDDisable(false);
            setJt3IDDisable(false);
            setJt4IDDisable(true);
        } else if (operatingMode.equalsIgnoreCase("14") || operatingMode.equalsIgnoreCase("15")
                || operatingMode.equalsIgnoreCase("16")) {
            setJt1IDDisable(false);
            setJt2IDDisable(false);
            setJt3IDDisable(false);
            setJt4IDDisable(false);
        }
    }

    public void documents() {
        setIntroducerAccountNo("");
        setOldIntroducerAccountNo("");
        acnoIntro = "";
    }
}
