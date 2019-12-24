/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountEditCloseFacadeRemote;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.web.pojo.admin.AccountEditTdGrid;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class AccountEditTD extends BaseBean {

    String tdsApplicable;
    List<SelectItem> tdsApplicableList;
    String receiptNo;
    List<SelectItem> receiptNoList = new ArrayList<SelectItem>();
    String supportDoc;
    List<SelectItem> supportDocList;
    String opMode;
    List<SelectItem> opModeList;
    String occupation;
    List<SelectItem> occupationList;
    String accOpenDate;
    String docDetails;
    String docName;
    List<SelectItem> docNameList;
    String custNature;
    List<SelectItem> custNatureList;
    String errorMsg;
    String custAccNo;
    private String custOldAccNo;
    String custName;
    String fatherName;
    String mailAddress;
    String perAddress;
    String phone;
    String panNo;
    String nomination;
    String relation;
    String jtName1;
    String jtName1Code;
    String jtName2;
    String jtName2Code;
    String jtName3;
    String jtName3Code;
    String jtName4;
    String jtName4Code;
    String instruction;
    String accountOpenDate;
    String guardianName;
    String introAccNo;
    String introOldAccNo;
    String operationMode;
    String orgCode;
    //  String custType;
    String tdsFlag;
    String tdsDetails;
    private boolean tdsApplicableDisable = true;
    String nomiName;
    String nomiAddress;
    String nomiRelation;
    String nomiMinor;
    List<SelectItem> nomiMinorList;
    Date nomiDob;
    String nomiAge;
    AccountEditTdGrid accEditGrid;
    List<AccountEditTdGrid> accEditGridList = new ArrayList<AccountEditTdGrid>();
    String intToAccNo;
    String intToOldAccNo;
    String accountNumber;
    String flag1;
    String flag2;
    String flag3;
    boolean flag4;
    boolean flag5;
    boolean flag6;
    boolean flag7;
    String acNoFlag;
    Validator validator;
    AccountEditCloseFacadeRemote editCloseFacadeRemote;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    String customerId = "";
    //Addition on 03/12/2015
    private String autoRenew;
    private List<SelectItem> autoRenewList;
    private String autoPay;
    private List<SelectItem> autoPayList;
    private String paidAcno;
    private String paidAcnoName;
    private String autoRenewVar = "none";
    private String autoPaymentVar = "none";
    private boolean intToAccNoDisable = true;
    private String actCategory;
    private List<SelectItem> actCategoryList;
    CommonReportMethodsRemote reportMethodsLocal;
    private String occupationDesc, acNoLen;
    private AccountOpeningFacadeRemote acOpenFacadeRemote = null;

    public String getIntroOldAccNo() {
        return introOldAccNo;
    }

    public void setIntroOldAccNo(String introOldAccNo) {
        this.introOldAccNo = introOldAccNo;
    }

    public String getIntToOldAccNo() {
        return intToOldAccNo;
    }

    public void setIntToOldAccNo(String intToOldAccNo) {
        this.intToOldAccNo = intToOldAccNo;
    }

    public String getCustOldAccNo() {
        return custOldAccNo;
    }

    public void setCustOldAccNo(String custOldAccNo) {
        this.custOldAccNo = custOldAccNo;
    }

    public String getTdsApplicable() {
        return tdsApplicable;
    }

    public void setTdsApplicable(String tdsApplicable) {
        this.tdsApplicable = tdsApplicable;
    }

    public List<SelectItem> getTdsApplicableList() {
        return tdsApplicableList;
    }

    public void setTdsApplicableList(List<SelectItem> tdsApplicableList) {
        this.tdsApplicableList = tdsApplicableList;
    }

    public String getSupportDoc() {
        return supportDoc;
    }

    public void setSupportDoc(String supportDoc) {
        this.supportDoc = supportDoc;
    }

    public List<SelectItem> getSupportDocList() {
        return supportDocList;
    }

    public void setSupportDocList(List<SelectItem> supportDocList) {
        this.supportDocList = supportDocList;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public List<SelectItem> getOpModeList() {
        return opModeList;
    }

    public void setOpModeList(List<SelectItem> opModeList) {
        this.opModeList = opModeList;
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

    public String getAccOpenDate() {
        return accOpenDate;
    }

    public void setAccOpenDate(String accOpenDate) {
        this.accOpenDate = accOpenDate;
    }

    public String getDocDetails() {
        return docDetails;
    }

    public void setDocDetails(String docDetails) {
        this.docDetails = docDetails;
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

    public String getCustNature() {
        return custNature;
    }

    public void setCustNature(String custNature) {
        this.custNature = custNature;
    }

    public List<SelectItem> getCustNatureList() {
        return custNatureList;
    }

    public void setCustNatureList(List<SelectItem> custNatureList) {
        this.custNatureList = custNatureList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCustAccNo() {
        return custAccNo;
    }

    public void setCustAccNo(String custAccNo) {
        this.custAccNo = custAccNo;
    }

    public String getAccountOpenDate() {
        return accountOpenDate;
    }

    public void setAccountOpenDate(String accountOpenDate) {
        this.accountOpenDate = accountOpenDate;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getIntroAccNo() {
        return introAccNo;
    }

    public void setIntroAccNo(String introAccNo) {
        this.introAccNo = introAccNo;
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

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getNomination() {
        return nomination;
    }

    public void setNomination(String nomination) {
        this.nomination = nomination;
    }

    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getPerAddress() {
        return perAddress;
    }

    public void setPerAddress(String perAddress) {
        this.perAddress = perAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getTdsDetails() {
        return tdsDetails;
    }

    public void setTdsDetails(String tdsDetails) {
        this.tdsDetails = tdsDetails;
    }

    public String getTdsFlag() {
        return tdsFlag;
    }

    public void setTdsFlag(String tdsFlag) {
        this.tdsFlag = tdsFlag;
    }

    public AccountEditTdGrid getAccEditGrid() {
        return accEditGrid;
    }

    public void setAccEditGrid(AccountEditTdGrid accEditGrid) {
        this.accEditGrid = accEditGrid;
    }

    public List<AccountEditTdGrid> getAccEditGridList() {
        return accEditGridList;
    }

    public void setAccEditGridList(List<AccountEditTdGrid> accEditGridList) {
        this.accEditGridList = accEditGridList;
    }

    public String getIntToAccNo() {
        return intToAccNo;
    }

    public void setIntToAccNo(String intToAccNo) {
        this.intToAccNo = intToAccNo;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public List<SelectItem> getReceiptNoList() {
        return receiptNoList;
    }

    public void setReceiptNoList(List<SelectItem> receiptNoList) {
        this.receiptNoList = receiptNoList;
    }

    public String getNomiAddress() {
        return nomiAddress;
    }

    public void setNomiAddress(String nomiAddress) {
        this.nomiAddress = nomiAddress;
    }

    public String getNomiAge() {
        return nomiAge;
    }

    public void setNomiAge(String nomiAge) {
        this.nomiAge = nomiAge;
    }

    public Date getNomiDob() {
        return nomiDob;
    }

    public void setNomiDob(Date nomiDob) {
        this.nomiDob = nomiDob;
    }

    public String getNomiMinor() {
        return nomiMinor;
    }

    public void setNomiMinor(String nomiMinor) {
        this.nomiMinor = nomiMinor;
    }

    public List<SelectItem> getNomiMinorList() {
        return nomiMinorList;
    }

    public void setNomiMinorList(List<SelectItem> nomiMinorList) {
        this.nomiMinorList = nomiMinorList;
    }

    public String getNomiName() {
        return nomiName;
    }

    public void setNomiName(String nomiName) {
        this.nomiName = nomiName;
    }

    public String getNomiRelation() {
        return nomiRelation;
    }

    public void setNomiRelation(String nomiRelation) {
        this.nomiRelation = nomiRelation;
    }

    public String getJtName1Code() {
        return jtName1Code;
    }

    public void setJtName1Code(String jtName1Code) {
        this.jtName1Code = jtName1Code;
    }

    public String getJtName2Code() {
        return jtName2Code;
    }

    public void setJtName2Code(String jtName2Code) {
        this.jtName2Code = jtName2Code;
    }

    public String getJtName3Code() {
        return jtName3Code;
    }

    public void setJtName3Code(String jtName3Code) {
        this.jtName3Code = jtName3Code;
    }

    public String getJtName4Code() {
        return jtName4Code;
    }

    public void setJtName4Code(String jtName4Code) {
        this.jtName4Code = jtName4Code;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getFlag3() {
        return flag3;
    }

    public void setFlag3(String flag3) {
        this.flag3 = flag3;
    }

    public boolean isFlag4() {
        return flag4;
    }

    public void setFlag4(boolean flag4) {
        this.flag4 = flag4;
    }

    public boolean isFlag5() {
        return flag5;
    }

    public void setFlag5(boolean flag5) {
        this.flag5 = flag5;
    }

    public boolean isFlag6() {
        return flag6;
    }

    public void setFlag6(boolean flag6) {
        this.flag6 = flag6;
    }

    public boolean isFlag7() {
        return flag7;
    }

    public void setFlag7(boolean flag7) {
        this.flag7 = flag7;
    }

    public String getAcNoFlag() {
        return acNoFlag;
    }

    public void setAcNoFlag(String acNoFlag) {
        this.acNoFlag = acNoFlag;
    }

    public String getAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(String autoRenew) {
        this.autoRenew = autoRenew;
    }

    public List<SelectItem> getAutoRenewList() {
        return autoRenewList;
    }

    public void setAutoRenewList(List<SelectItem> autoRenewList) {
        this.autoRenewList = autoRenewList;
    }

    public String getAutoPay() {
        return autoPay;
    }

    public void setAutoPay(String autoPay) {
        this.autoPay = autoPay;
    }

    public List<SelectItem> getAutoPayList() {
        return autoPayList;
    }

    public void setAutoPayList(List<SelectItem> autoPayList) {
        this.autoPayList = autoPayList;
    }

    public String getPaidAcno() {
        return paidAcno;
    }

    public void setPaidAcno(String paidAcno) {
        this.paidAcno = paidAcno;
    }

    public String getPaidAcnoName() {
        return paidAcnoName;
    }

    public void setPaidAcnoName(String paidAcnoName) {
        this.paidAcnoName = paidAcnoName;
    }

    public String getAutoPaymentVar() {
        return autoPaymentVar;
    }

    public void setAutoPaymentVar(String autoPaymentVar) {
        this.autoPaymentVar = autoPaymentVar;
    }

    public boolean isIntToAccNoDisable() {
        return intToAccNoDisable;
    }

    public void setIntToAccNoDisable(boolean intToAccNoDisable) {
        this.intToAccNoDisable = intToAccNoDisable;
    }

    public String getAutoRenewVar() {
        return autoRenewVar;
    }

    public void setAutoRenewVar(String autoRenewVar) {
        this.autoRenewVar = autoRenewVar;
    }

    public boolean isTdsApplicableDisable() {
        return tdsApplicableDisable;
    }

    public void setTdsApplicableDisable(boolean tdsApplicableDisable) {
        this.tdsApplicableDisable = tdsApplicableDisable;
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

    public String getOccupationDesc() {
        return occupationDesc;
    }

    public void setOccupationDesc(String occupationDesc) {
        this.occupationDesc = occupationDesc;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public AccountEditTD() {
        try {
          
            editCloseFacadeRemote = (AccountEditCloseFacadeRemote) ServiceLocator.getInstance().lookup("AccountEditCloseFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            reportMethodsLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            this.setAcNoLen(getAcNoLength());
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(dt));
            intToAccNo = "";
            introAccNo = "";
            validator = new Validator();
            getDropDownDetails();
            accEditGridList = new ArrayList<AccountEditTdGrid>();
            setAutoValues();

            List resultList4 = reportMethodsLocal.getActCategoryDetails();
            actCategoryList = new ArrayList<SelectItem>();
            actCategoryList.add(new SelectItem("0", "-- Select--"));
            for (int k = 0; k < resultList4.size(); k++) {
                Vector ele = (Vector) resultList4.get(k);
                actCategoryList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

        } catch (ApplicationException e) {
            setErrorMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void setAutoValues() {
        try {
            autoRenew = "0";
            autoPay = "0";
            autoRenewVar = "none";
            autoPaymentVar = "none";
            Integer activeCode = ftsPostRemote.getCodeForReportName("TD-RENEWAL");
            if (activeCode == 1) {
                autoRenewVar = "";
            }
            activeCode = ftsPostRemote.getCodeForReportName("TD-PAYMENT");
            if (activeCode == 1) {
                autoPaymentVar = "";
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void getDropDownDetails() {
        try {
            List l1 = editCloseFacadeRemote.codeDesc();
            List l2 = editCloseFacadeRemote.occupationCode();
            List l3 = editCloseFacadeRemote.accOpenDate(getOrgnBrCode());
            List l4 = editCloseFacadeRemote.docName();
            tdsApplicableList = new ArrayList<SelectItem>();
            //  tdsApplicableList.add(new SelectItem("0", "--Select--"));
            tdsApplicableList.add(new SelectItem("Y", "Yes"));
            tdsApplicableList.add(new SelectItem("N", "No"));
            // tdsApplicableList.add(new SelectItem("C", "Compulsory"));
            supportDocList = new ArrayList<SelectItem>();
            supportDocList.add(new SelectItem("0", "--Select--"));
            supportDocList.add(new SelectItem("Form 15H", "Form 15H"));
            supportDocList.add(new SelectItem("Form 15G", "Form 15G"));
            //supportDocList.add(new SelectItem("Member", "Member"));
            supportDocList.add(new SelectItem("Exemption certificate", "Exemption certificate"));
            supportDocList.add(new SelectItem("Cooperative Society", "Cooperative Society"));
            opModeList = new ArrayList<SelectItem>();
            opModeList.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < l1.size(); i++) {
                Vector v1 = (Vector) l1.get(i);
                opModeList.add(new SelectItem(v1.get(0).toString(), v1.get(1).toString()));
            }
            occupationList = new ArrayList<SelectItem>();
            occupationList.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < l2.size(); i++) {
                Vector v2 = (Vector) l2.get(i);
                occupationList.add(new SelectItem(v2.get(0).toString(), v2.get(1).toString()));
            }
            if (!l3.isEmpty()) {
                Vector v3 = (Vector) l3.get(0);
                String date = v3.get(0).toString();
                date = date.substring(6) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4);
                this.setAccOpenDate(date);
            }

            docNameList = new ArrayList<SelectItem>();
            docNameList.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < l4.size(); i++) {
                Vector v4 = (Vector) l4.get(i);
                docNameList.add(new SelectItem(v4.get(0).toString(), v4.get(1).toString()));
            }
            
            List cuTypeList = new ArrayList();
            cuTypeList = reportMethodsLocal.getRefRecList("235");
            if (cuTypeList.size() < 0) {
                custNatureList = new ArrayList<SelectItem>();
                custNatureList.add(new SelectItem("0", "--SELECT--"));
                return;
            } else {
                custNatureList = new ArrayList<SelectItem>();
                custNatureList.add(new SelectItem("0", "--SELECT--"));
                for (int i = 0; i < cuTypeList.size(); i++) {
                    Vector ele = (Vector) cuTypeList.get(i);
                    custNatureList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
            
//            custNatureList = new ArrayList<SelectItem>();
//            custNatureList.add(new SelectItem("0", "--Select--"));
//            custNatureList.add(new SelectItem("SC", "Senior Citizen"));
//            custNatureList.add(new SelectItem("ST", "Staff"));
//            custNatureList.add(new SelectItem("OT", "Others"));
            
            receiptNoList.add(new SelectItem(""));
            ////////////////////////////////////////////////////////////////////////////////////
            nomiMinorList = new ArrayList<SelectItem>();
            nomiMinorList.add(new SelectItem(" ", "--Select--"));
            nomiMinorList.add(new SelectItem("Y", "Yes"));
            nomiMinorList.add(new SelectItem("N", "No"));
            setNomiAge("0");
            //Addition on 03/12/2015
            setAutoRenewList(new ArrayList<SelectItem>());
            autoRenewList.add(new SelectItem("0", "--Select--"));
            autoRenewList.add(new SelectItem("N", "No"));
            autoRenewList.add(new SelectItem("Y", "Yes"));

            setAutoPayList(new ArrayList<SelectItem>());
            autoPayList.add(new SelectItem("0", "--Select--"));
            autoPayList.add(new SelectItem("N", "No"));
            autoPayList.add(new SelectItem("Y", "Yes"));
            setTdsApplicable("Y");
            //End here
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void getCustDetails() {
       
         if(!custOldAccNo.substring(0,2).equalsIgnoreCase(this.getOrgnBrCode()) && !this.getOrgnBrCode().substring(0,2).equalsIgnoreCase("90")){
            this.setErrorMsg("You Can Edit Account Number Of Same Branch ");
            return;
         }
        
        String[] array = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        accEditGridList = new ArrayList<AccountEditTdGrid>();
        receiptNoList = new ArrayList<SelectItem>();
        List listForRecptNo;
        //Addition On 04/12/215
        this.autoRenew = "0";
        this.autoPay = "0";
        this.intToAccNoDisable = true;
        this.intToOldAccNo = "";
        this.intToAccNo = "";
        this.paidAcno = "";
        this.paidAcnoName = "";
        this.tdsApplicableDisable = true;
        //End Here
        try {
            custAccNo = ftsPostRemote.getNewAccountNumber(custOldAccNo);
            String result = editCloseFacadeRemote.custInfoEditTd(this.custAccNo);
            listForRecptNo = editCloseFacadeRemote.selectReceiptNo(this.custAccNo);
            if (result.equalsIgnoreCase("This Account No. Does Not Exist.")
                    || result.equalsIgnoreCase("Customer Id does not exists corresponding to this Account No.")) {
                acNoFlag = "accNo";
                this.setErrorMsg(result);
                refreshForm();
                accEditGridList.clear();
                return;
            } else {
                if (result.contains(":")) {
                    String spliter = ":";
                    array = result.split(spliter);
                }
                if (array[33].equalsIgnoreCase("This Account Has Been Closed.")) {
                    this.setErrorMsg(array[33]);
                    acNoFlag = "accNo";
                    refreshForm();
                    accEditGridList.clear();
                    this.setAccountNumber(this.custAccNo);
                    this.setFatherName(array[0]);
                    this.setCustName(array[1]);
                    this.setMailAddress(array[2]);
                    this.setPerAddress(array[3]);
                    this.setPhone(array[4].trim());
                    this.setPanNo(array[5].trim());
                    this.setNomination(array[6].trim());
                    this.setRelation(array[7].trim());
                    this.setJtName1(array[8]);
                    this.setJtName2(array[9]);
                    this.setJtName3(array[10]);
                    this.setJtName4(array[11]);
                    this.setInstruction(array[12].trim());
                    this.setAccOpenDate(array[13]);
                    this.setGuardianName(array[14].trim());
                    this.setIntroOldAccNo(array[15].trim());
                    this.setOpMode(array[16]);
                    this.setOccupation(array[17]);
//                this.setCustType(array[18]);
                    this.setCustNature(array[18]);
                    this.setTdsFlag(array[19]);
                    this.setTdsApplicable(array[19]);
                    this.setTdsDetails(array[20]);
//                    this.setIntToOldAccNo(array[21].trim());
//                    this.setIntToAccNo(array[21].trim());
                    this.setNomiName(array[22].trim());
                    this.setNomiAddress(array[23].trim());
                    this.setNomiRelation(array[24].trim());
                    this.setNomiMinor(array[25]);
                    if (array[26].equalsIgnoreCase("")) {
                        this.setNomiDob(null);
                    } else {
                        if (array[26].equalsIgnoreCase(" ")) {
                            this.setNomiDob(null);
                        } else {
                            this.setNomiDob(sdf.parse(array[26]));
                        }

                    }
                    this.setNomiAge(array[27].trim());
                    this.setJtName1Code(array[28].trim());
                    this.setJtName2Code(array[29].trim());
                    this.setJtName3Code(array[30].trim());
                    this.setJtName4Code(array[31].trim());
                    //  customerId = array[31].trim();
                    this.setActCategory(array[32]);
                    if (!listForRecptNo.isEmpty()) {
                        for (int i = 0; i < listForRecptNo.size(); i++) {
                            Vector vecForRecptNo = (Vector) listForRecptNo.get(i);
                            receiptNoList.add(new SelectItem(vecForRecptNo.get(1).toString(),vecForRecptNo.get(1).toString()+" ("+vecForRecptNo.get(0).toString()+")"));
                        }
                    }
                    if (array[19].equalsIgnoreCase("N")) {
                        flag1 = "true";
                    } else {
                        flag1 = "true";
                    }
                    List listForGridData = editCloseFacadeRemote.getDocDtlDocDescDocCode(this.custAccNo);
                    if (!listForGridData.isEmpty()) {
//                accEditGridList = new ArrayList<AccountEditTdGrid>();
                        for (int i = 0; i < listForGridData.size(); i++) {
                            accEditGrid = new AccountEditTdGrid();
                            Vector vecGrid = (Vector) listForGridData.get(i);
                            accEditGrid.setCode(vecGrid.get(2).toString());
                            accEditGrid.setDocDesc(vecGrid.get(1).toString());
                            accEditGrid.setDocDetal(vecGrid.get(0).toString());
                            accEditGridList.add(accEditGrid);
                        }
                    }
                } else {
//                    acNoFlag = "ddTDSApplicable";
                    acNoFlag = "ddRcptNo";
                    this.setErrorMsg("");
                    this.setAccountNumber(this.custAccNo);
                    this.setFatherName(array[0]);
                    this.setCustName(array[1]);
                    this.setMailAddress(array[2]);
                    this.setPerAddress(array[3]);
                    this.setPhone(array[4].trim());
                    this.setPanNo(array[5].trim());
                    this.setNomination(array[6].trim());
                    this.setRelation(array[7].trim());
                    this.setJtName1(array[8]);
                    this.setJtName2(array[9]);
                    this.setJtName3(array[10]);
                    this.setJtName4(array[11]);
                    this.setInstruction(array[12].trim());
                    this.setAccOpenDate(array[13]);
                    this.setGuardianName(array[14].trim());
                    this.setIntroOldAccNo(array[15].trim());
                    this.setOpMode(array[16]);
                    this.setOccupation(array[17]);
//                this.setCustType(array[18]);
                    this.setCustNature(array[18]);

                    this.setTdsFlag(array[19]);
                    this.setTdsApplicable(array[19]);
                    this.setTdsDetails(array[20]);
//                    this.setIntToOldAccNo(array[21].trim());
//                    this.setIntToAccNo(array[21].trim());
                    this.setNomiName(array[22].trim());
                    this.setNomiAddress(array[23].trim());
                    this.setNomiRelation(array[24].trim());
                    this.setNomiMinor(array[25]);
                    if (array[26].equalsIgnoreCase("")) {
                        this.setNomiDob(null);
                    } else {
                        if (array[26].equalsIgnoreCase(" ")) {
                            this.setNomiDob(null);
                        } else {
                            this.setNomiDob(sdf.parse(array[26]));
                        }

                    }
                    this.setNomiAge(array[27].trim());
                    this.setJtName1Code(array[28].trim());
                    this.setJtName2Code(array[29].trim());
                    this.setJtName3Code(array[30].trim());
                    this.setJtName4Code(array[31].trim());
                    //  customerId = array[31].trim();
                    this.setActCategory(array[32]);
                    if (!listForRecptNo.isEmpty()) {
                        for (int i = 0; i < listForRecptNo.size(); i++) {
                            Vector vecForRecptNo = (Vector) listForRecptNo.get(i);
                            receiptNoList.add(new SelectItem(vecForRecptNo.get(1).toString(),vecForRecptNo.get(1).toString()+" ("+vecForRecptNo.get(0).toString()+")"));
                        }
                    }
                    if (array[19].equalsIgnoreCase("N")) {
                        flag1 = "true";
                    } else {
                        flag1 = "true";
                    }
                }
                List listForGridData = editCloseFacadeRemote.getDocDtlDocDescDocCode(this.custAccNo);
                if (!listForGridData.isEmpty()) {
//                accEditGridList = new ArrayList<AccountEditTdGrid>();
                    for (int i = 0; i < listForGridData.size(); i++) {
                        accEditGrid = new AccountEditTdGrid();
                        Vector vecGrid = (Vector) listForGridData.get(i);
                        accEditGrid.setCode(vecGrid.get(2).toString());
                        accEditGrid.setDocDesc(vecGrid.get(1).toString());
                        accEditGrid.setDocDetal(vecGrid.get(0).toString());
                        accEditGridList.add(accEditGrid);
                    }
                }
                //---- Added by Manish Kumar
                this.occupation = "";
                this.occupationDesc = "";
                String custId = acOpenFacadeRemote.customerId(this.custAccNo);
                List selectList = acOpenFacadeRemote.getOccupation(custId);
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
            setErrorMsg(e.getLocalizedMessage());
        }
    }

//    public boolean onblurPhoneNo() {
//        if (!this.phone.equalsIgnoreCase("")) {
//            if (!this.phone.matches("[0-9 +,-]*")) {
//                this.setErrorMsg("Please Enter Numeric Value in Phone No.");
//                return false;
//            } else {
//                this.setErrorMsg("");
//            }
//        }
//        return true;
//    }
    public void updateAcctEditTd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            if (this.custOldAccNo.equalsIgnoreCase("") || this.custOldAccNo.equalsIgnoreCase("0") || this.custOldAccNo == null || this.custOldAccNo.equalsIgnoreCase("null")) {
                this.setErrorMsg("First Fill Account Number.");
                return;
            }
//            if (!onblurPhoneNo()) {
//                return;
//            }
            if (!jointName1OnBlur()) {
                return;
            }
            if (!jointName2OnBlur()) {
                return;
            }
            if (!jointName3OnBlur()) {
                return;
            }
            if (!jointName4OnBlur()) {
                return;
            }
            if (!this.docName.equalsIgnoreCase("0") && this.docDetails.equalsIgnoreCase("")) {
                this.setErrorMsg("Please Enter Document Details.");
                return;
            } else {
                this.setErrorMsg("");
            }

            if (this.docName.equalsIgnoreCase("0") && !this.docDetails.equalsIgnoreCase("")) {
                this.setErrorMsg("First Fill Document Name.");
                return;
            } else {
                this.setErrorMsg("");
            }
            if (!(this.accEditGridList.size() > 0) && this.docName.equalsIgnoreCase("0")
                    && this.docDetails.equalsIgnoreCase("")) {
                if (this.introOldAccNo.equalsIgnoreCase("")) {
                    this.setErrorMsg("Fill Either Document Details or Introducer Account No.");
                    return;
                }
            } else {
                this.setErrorMsg("");
            }
            if (!onblurGuardianName()) {
                return;
            }
            if (!this.jtName1Code.equalsIgnoreCase("")) {
                if (!this.jtName1Code.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                    this.setErrorMsg("Please Fill Joint Name1 Code as Numeric.");
                    return;
                } else {
                    this.setErrorMsg("");
                }
            } else {
                this.setErrorMsg("");
            }
            if (!this.jtName2Code.equalsIgnoreCase("")) {
                if (!this.jtName2Code.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                    this.setErrorMsg("Please Fill Joint Name2 Code as Numeric.");
                    return;
                } else {
                    this.setErrorMsg("");
                }
            } else {
                this.setErrorMsg("");
            }
            if (!this.jtName3Code.equalsIgnoreCase("")) {
                if (!this.jtName3Code.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                    this.setErrorMsg("Please Fill Joint Name3 Code as Numeric.");
                    return;
                } else {
                    this.setErrorMsg("");
                }
            } else {
                this.setErrorMsg("");
            }
            if (!this.jtName4Code.equalsIgnoreCase("")) {
                if (!this.jtName4Code.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                    this.setErrorMsg("Please Fill Joint Name4 Code as Numeric.");
                    return;
                } else {
                    this.setErrorMsg("");
                }
            } else {
                this.setErrorMsg("");
            }
            if (!this.nomiName.equalsIgnoreCase("") || this.nomiName != null || this.nomiName.length() != 0) {
                if (!this.nomiName.equalsIgnoreCase("")) {
                    if (!this.nomiAge.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                        this.setErrorMsg("Please Fill Nominee Age as Numeric.");
                        return;
                    } else {
                        this.setErrorMsg("");
                    }
                }
            } else {
                this.setNomiAge("0");
            }


            if (!this.nomiName.equalsIgnoreCase("")) {
                if (!this.nomiName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                    this.setErrorMsg("Please Fill Nominee Name as Non Numeric.");
                    return;
                } else {
                    this.setErrorMsg("");
                }
            } else {
                this.setErrorMsg("");
            }
            if (!this.nomiRelation.equalsIgnoreCase("")) {
                if (!this.nomiRelation.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                    this.setErrorMsg("Please Fill Nominee Relation as Non Numeric.");
                    return;
                } else {
                    this.setErrorMsg("");
                }
            } else {
                this.setErrorMsg("");
            }
              if (!this.instruction.equalsIgnoreCase("")) {
                if (!this.instruction.matches("[a-zA-Z0-9,-/ ]*")) {
                    this.setErrorMsg("Please Fill Proper A/c Instruction");
                    return;
                } else {
                    this.setErrorMsg("");
                }
            } else {
                this.setErrorMsg("");
            }
            if (nomiMinor.equalsIgnoreCase("Y")) {
                if (this.nomiDob == null) {
                    this.setErrorMsg("Nominee is minor.Please fill Nominee Date of Birth.");
                    return;
                } else {
                    this.setErrorMsg("");
                }
            }
            if (nomiMinor.equalsIgnoreCase("N")) {
                if (this.nomiAge.equalsIgnoreCase("0")) {
                    this.setErrorMsg("Nominee is not minor.Please fill Nominee age.");
                    return;
                } else {
                    this.setErrorMsg("");
                }
            }
            if (this.nomiDob != null) {
                String s1 = onblurNomDob();
                if (!s1.equalsIgnoreCase("true")) {
                    this.setErrorMsg(s1);
                    return;
                } else {
                    this.setErrorMsg("");
                }
            } else {
                this.nomiDob = sdf.parse("19000101");
                this.setErrorMsg("");
            }
            if (receiptNo == null || receiptNo.equalsIgnoreCase("")) {
                receiptNo = "";
            }

            if (this.tdsApplicable.equalsIgnoreCase("N")) {
                if (this.tdsDetails.equalsIgnoreCase("0")) {
                    this.setErrorMsg("Please select supporting doc.");
                    return;
                }
            }

            //Addition On 04/12/2015
            if (validateAutoPay() == false) {
                return;
            }
            //End Here

            String updateResult = editCloseFacadeRemote.acctEditTdUpdate(this.custAccNo, custName, acOpenFacadeRemote.removeSomeSpecialChar(this.mailAddress), acOpenFacadeRemote.removeSomeSpecialChar(perAddress), phone,
                    occupation, this.opMode, docName, docDetails, panNo, this.guardianName, fatherName, this.introAccNo, this.jtName1,
                    this.jtName2, nomination, this.relation, this.jtName3, this.jtName4, this.instruction, tdsApplicable, tdsDetails,
                    custNature, this.receiptNo, this.intToAccNo, this.accOpenDate, getUserName(), getOrgnBrCode(), this.nomiName,
                    this.nomiAddress, this.nomiRelation, this.nomiMinor, sdf.format(this.nomiDob), this.nomiAge,
                    this.jtName1Code, this.jtName2Code, this.jtName3Code, this.jtName4Code, this.autoRenew, this.autoPay, this.paidAcno, this.actCategory);
            if (updateResult.equalsIgnoreCase("true")) {
                this.setErrorMsg("Account Details successfully updated");
                accEditGridList.clear();
                refreshForm();
            } else {
                this.setErrorMsg(updateResult);
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public String onblurNomDob() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String msg1 = "";
        try {
            List dateDif = editCloseFacadeRemote.dateDiffNomDob(sdf.format(this.nomiDob).toString());
            Vector vecDateDiff = (Vector) dateDif.get(0);
            String strDateDiff = vecDateDiff.get(0).toString();
            if (Integer.parseInt(strDateDiff) <= 0) {
                this.setErrorMsg("You can't select current date and date after the current date.");
                return msg1 = "You can't select current date and date after the current date.";
            } else {
                this.setErrorMsg("");
                return msg1 = "true";
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }

        return msg1;
    }

    public String getTdsFlagCustType() {
        String[] array = null;
        try {
            String result = editCloseFacadeRemote.custInfoEditTd(this.custAccNo);
            if (result.contains(":")) {
                String spliter = ":";
                array = result.split(spliter);
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }

        return array[18] + ":" + array[19];
    }

    public void supportingDocVisibility() {
        if (this.tdsApplicable.equalsIgnoreCase("N")) {
            flag1 = "true";
        } else {
            flag1 = "true";
        }

    }

    public boolean jointName1OnBlur() {
        try {

            if (!this.jtName1Code.equalsIgnoreCase("")) {
                List list1 = editCloseFacadeRemote.selectJointName1(this.jtName1Code, this.getOperationMode());
                if (list1.isEmpty()) {
                    this.setErrorMsg("Invalid Code for Joint Name1.");
                    this.setJtName1("");
                    return false;
                } else {
                    Vector v1 = (Vector) list1.get(0);
                    this.setErrorMsg("");
                    this.setJtName1(v1.get(0).toString().trim() + " " + v1.get(1).toString().trim() + " " + v1.get(2).toString().trim());
                }
            }else{
                this.setJtName1("");
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }

        return true;
    }

    public boolean jointName2OnBlur() {
        try {
            if (!this.jtName2Code.equalsIgnoreCase("")) {
                List list2 = editCloseFacadeRemote.selectJointName1(this.jtName2Code, this.getOrgnBrCode());
                if (list2.isEmpty()) {
                    this.setErrorMsg("Invalid Code for Joint Name2.");
                    this.setJtName2("");
                    return false;
                } else {
                    Vector v2 = (Vector) list2.get(0);
                    this.setErrorMsg("");
                    this.setJtName2(v2.get(0).toString().trim() + " " + v2.get(1).toString().trim() + " " + v2.get(2).toString().trim());
                }
            }else{
               this.setJtName2(""); 
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }

        return true;
    }

    public boolean jointName3OnBlur() {
        try {
            if (!this.jtName3Code.equalsIgnoreCase("")) {
                List list3 = editCloseFacadeRemote.selectJointName1(this.jtName3Code, this.getOrgnBrCode());
                if (list3.isEmpty()) {
                    this.setErrorMsg("Invalid Code for Joint Name3.");
                    this.setJtName3("");
                    return false;
                } else {
                    Vector v3 = (Vector) list3.get(0);
                    this.setErrorMsg("");
                    this.setJtName3(v3.get(0).toString().trim() + " " + v3.get(1).toString().trim() + " " + v3.get(2).toString().trim());
                }
            }else{
                this.setJtName3("");  
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }

        return true;
    }

    public boolean jointName4OnBlur() {
        try {

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            this.setErrorMsg("");
            if (!this.jtName4Code.equalsIgnoreCase("")) {
                List list4 = editCloseFacadeRemote.selectJointName1(this.jtName4Code, this.getOrgnBrCode());
                if (list4.isEmpty()) {
                    this.setErrorMsg("Invalid Code for Joint Name4.");
                    this.setJtName4("");
                    return false;
                } else {
                    Vector v4 = (Vector) list4.get(0);
                    this.setErrorMsg("");
                    this.setJtName4(v4.get(0).toString().trim() + " " + v4.get(1).toString().trim() + " " + v4.get(2).toString().trim());
                }
            }else{
              this.setJtName4("");     
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }

        return true;
    }

    public boolean onblurGuardianName() {
        if (!this.guardianName.equalsIgnoreCase("")) {
            if (!this.guardianName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                this.setErrorMsg("Please Fill Guardian Name as Non Numeric.");
                return false;
            } else {
                this.setErrorMsg("");
                return true;
            }

        }
        return true;
    }

    public void onBlurNomineeMinor() {
        if (this.nomiMinor.equalsIgnoreCase(" ")) {
            flag2 = "true";
            flag3 =
                    "true";
            this.setNomiAge("0");
            this.setNomiDob(null);
        } else if (this.nomiMinor.equalsIgnoreCase("Y")) {
            flag2 = "true";
            flag3 =
                    "false";
            this.setNomiAge("0");
        } else {
            flag2 = "false";
            flag3 =
                    "true";
            this.setNomiDob(null);
        }

    }

    public void refreshForm() {
        Date date = new Date();
        this.setCustOldAccNo("");
        this.setTdsApplicable("Y");
        this.tdsApplicableDisable = true;
        this.setSupportDoc("");
        this.setTdsDetails("");
        this.setCustName("");
        this.setFatherName("");
        this.setMailAddress("");
        this.setPerAddress("");
        this.setPhone("");
        this.setPanNo("");
        this.setDocDetails("");
        this.setDocName("");
        this.setIntroOldAccNo("");
        this.setIntToOldAccNo("");
        this.setAccountNumber("");
        this.setOpMode("");
        this.setNomination("");
        this.setRelation("");
        this.setJtName1("");
        this.setJtName2("");
        this.setJtName3("");
        this.setJtName4("");
        this.setCustNature("");
        this.setOccupation("");
        this.setInstruction("");
        this.setGuardianName("");
        this.setAccOpenDate("");
        this.setReceiptNo("");
        this.setJtName1Code("");
        this.setJtName2Code("");
        this.setJtName3Code("");
        this.setJtName4Code("");
        this.setNomiName("");
        this.setNomiAddress("");
        this.setNomiRelation("");
        this.setNomiMinor("");
        this.setNomiDob(null);
        this.setNomiAge("0");
        intToAccNo = "";
        introAccNo = "";
        receiptNoList = new ArrayList<SelectItem>();
        receiptNoList.add(new SelectItem(""));
        //Addition On 03/12/2015
        setAutoRenew("0");
        setAutoPay("0");
        setPaidAcno("");
        setPaidAcnoName("");
        intToAccNoDisable = true;
        setAutoValues();
        this.setActCategory("0");
        this.occupationDesc="";
        //End here
    }

    public void onblurOperMode() {
        if (this.opMode.equalsIgnoreCase("0")) {
            flag4 = true;
            flag5 = true;
            flag6 = true;
            flag7 = true;
        } else if (this.opMode.equalsIgnoreCase("1")) {
            flag4 = true;
            flag5 = true;
            flag6 = true;
            flag7 = true;
        } else if (this.opMode.equalsIgnoreCase("4")) {
            flag4 = false;
            flag5 = false;
            flag6 = false;
            flag7 = false;
        } else if (this.opMode.equalsIgnoreCase("5")) {
            flag4 = false;
            flag5 = false;
            flag6 = true;
            flag7 = true;
        } else if (this.opMode.equalsIgnoreCase("6")) {
            flag4 = false;
            flag5 = false;
            flag6 = false;
            flag7 = true;
        } else if (this.opMode.equalsIgnoreCase("14")) {
            flag4 = false;
            flag5 = false;
            flag6 = false;
            flag7 = false;
        } else if (this.opMode.equalsIgnoreCase("15")) {
            flag4 = false;
            flag5 = false;
            flag6 = false;
            flag7 = false;
        } else if (this.opMode.equalsIgnoreCase("16")) {
            flag4 = false;
            flag5 = false;
            flag6 = false;
            flag7 = false;
        } else {
            flag4 = false;
            flag5 = false;
            flag6 = false;
            flag7 = false;
        }
    }

    public void refreshButton() {
        refreshForm();
        this.setErrorMsg("");
        this.occupationDesc="";
        accEditGridList.clear();
    }

    public String exitBtnAction() {
        refreshForm();
        this.setErrorMsg("");
        accEditGridList.clear();
        return "case1";
    }

    public void intoToAcnoOnBlur() {
        this.setErrorMsg("");
        try {
            if (this.intToOldAccNo == null || this.intToOldAccNo.equalsIgnoreCase("") || this.intToOldAccNo.length() == 0) {
                this.setErrorMsg("PLEASE ENTER INT TO ACNO ACCOUNT NUMBER !!!");
                return;

            }


            //if (this.intToOldAccNo.length() != 12) {
            if (!this.intToOldAccNo.equalsIgnoreCase("") && ((this.intToOldAccNo.length() != 12) && (this.intToOldAccNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrorMsg("PLEASE ENTER PROPER ACCOUNT NUMBER !!!");
                return;

            }
            intToAccNo = ftsPostRemote.getNewAccountNumber(intToOldAccNo);
            String result = editCloseFacadeRemote.actNoValidation(this.intToAccNo);
            if (result == null) {
                this.setErrorMsg("ACCOUNT DETAIL NOT FOUND !!!");
                return;
            } else {
                if (result.contains("!")) {
                    this.setErrorMsg(result);
                    this.setCustOldAccNo("");
                    return;
                }
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getLocalizedMessage());
        }
    }

    public void getNewIntroAc() {
        try {
            setErrorMsg("");
            if (!validator.validateString(introOldAccNo)) {
                setErrorMsg("Please inset a valid introducer account no.");;
            }
            if (!introOldAccNo.equalsIgnoreCase("")) {
                introAccNo = ftsPostRemote.getNewAccountNumber(introOldAccNo);
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getLocalizedMessage());
        }
    }

    public void getCustomerName() {
        paidAcnoName = "";
        try {
            if (autoPay.equals("Y")) {
                setPaidAcnoName(ftsPostRemote.getCustName(paidAcno));
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void receiptProcess() {
        try {
            this.autoRenew = "0";
            this.autoPay = "0";
            this.intToAccNoDisable = true;
            this.intToOldAccNo = "";
            this.intToAccNo = "";
            if (this.receiptNo == null || this.receiptNo.equals("")) {
                this.setErrorMsg("Please select receipt no.");
                return;
            }
            List list = editCloseFacadeRemote.retrieveReceiptDetails(accountNumber, Double.parseDouble(receiptNo));
            Vector ele = (Vector) list.get(0);
            String intToAcno = ele.get(0).toString().trim();
            String intOpt = ele.get(1).toString().trim();
            String autoRenewal = ele.get(2).toString().trim();
            String autoPayment = ele.get(3).toString().trim();
            String autoPaymentAcno = ele.get(4).toString().trim();
            if (intOpt.equalsIgnoreCase("Q") || intOpt.equalsIgnoreCase("M") || intOpt.equalsIgnoreCase("Y")) {
                this.intToAccNoDisable = false;
                this.intToOldAccNo = intToAcno;
                this.intToAccNo = intToAcno;
            }
            this.setAutoRenew(autoRenewal);
            this.setAutoPay(autoPayment);
            this.setPaidAcno(autoPaymentAcno);
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public boolean validateAutoPay() {
        try {
            if (!receiptNoList.isEmpty()) {
                if (autoRenew == null || autoPay == null || autoRenew.equals("0")
                        || autoPay.equals("0") || autoRenew.equals("") || autoPay.equals("")) {
                    setErrorMsg("Auto renew and/or auto payment can not be blank.");
                    return false;
                }
            }
            if (autoPay.equals("Y")) {
                if (autoRenew.equals("Y")) {
                    setErrorMsg("Both auto renew and auto payment can not be applicable together.");
                    return false;
                }
                if (paidAcno == null || paidAcno.equals("") || paidAcno.trim().length() != 12) {
                    setErrorMsg("In case when auto payment is applicable then auto payment a/c is mandatory.");
                    return false;
                }

                String result = ftsPostRemote.ftsAcnoValidate(paidAcno, 0, "");
                if (!result.equalsIgnoreCase("true")) {
                    setErrorMsg(result);
                    return false;
                }

                List list = ftsPostRemote.autoPayLienStatus(accountNumber, Double.parseDouble(receiptNo));
                if (!list.isEmpty()) {
                    Vector lienVec = (Vector) list.get(0);
                    String lien = lienVec.get(0).toString();
                    if (lien.equalsIgnoreCase("Y")) {
                        setErrorMsg("This Receipt is Lien Marked, So Auto Payment can not be applicable.");
                        return false;
                    }
                }

                String paidAcNature = ftsPostRemote.getAccountNature(paidAcno);
                if (!(paidAcNature.equals(CbsConstant.SAVING_AC)
                        || (paidAcNature.equals(CbsConstant.CURRENT_AC)
                        && ftsPostRemote.getAcctTypeByCode(paidAcno.substring(2, 4)).equals(CbsConstant.CURRENT_AC)))) {
                    setErrorMsg("Only saving and current a/c is required in Auto Payment A/c.");
                    return false;
                }
            } else {
                autoPay = "N";
                paidAcno = "";
                paidAcnoName = "";
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
            return false;
        }
        return true;
    }
}
