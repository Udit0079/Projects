/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author root
 */
public class AccountOpeningRegister extends BaseBean {

    private AccountOpeningFacadeRemote acOpenFacadeRemote = null;
    private CommonReportMethodsRemote reportMethodsLocal = null;
    private String message;
    private List<SelectItem> acctTypeOption;
    private List<SelectItem> occupationOption;
    private List<SelectItem> operatingModeOption;
    private List<SelectItem> documentOption;
    Date accOpenDate;
    private String newAccType, acTypeDesc;
    private String actNature;
    private String fullIntroAcno;
    private String oldfullIntroAcno;
    private String acno;
    private String custName;
    private String description;
    private String customerId;
    Date dob;
    private String ddtitle;
    private String name;
    private String permanentAdd;
    private String correspodenceAdd;
    private String fathersName;
    private String mobileNo;
    private String penGir;
    private String flag;
    private String introduId;
    private String jointName1;
    private String jointName2;
    private String jointName3;
    private String jointName4;
    private String jointName1Id;
    private String jointName2Id;
    private String jointName3Id;
    private String jointName4Id;
    private String occupations;
    private String operatingMode;
    private String guardName;
    private String guardRelationShip;
    private String nomineeNm;
    private String nomineeRelation;
    private String rdPeriodInMonth;
    private String rdInstallmentAmt;
    private String documents;
    private String documentDetails;
    Date nomineedob;
    private String nomineeAdd;
    private String matAmt;
    private String jt1Flag;
    private String jt2Flag;
    private String jt3Flag;
    private String jt4Flag;
    private String gNm;
    private boolean fieldDisFlag;
    private boolean intCodeDisFlag;
    private boolean roiDisFlag;
    private String schemeCode;
    private List<SelectItem> schemeCodeList;
    private String intCode;
    private List<SelectItem> intCodeList;
    private String actCategory;
    private List<SelectItem> actCategoryList;
    private String roi;
    private String amtLblDisFlag = "none";
    private String amtTxtDisFlag = "none";
    private String periodLblDisFlag = "none";
    private String periodTxtDisFlag = "none";
    private String matAmtLblDisFlag = "none";
    private String matAmtTxtDisFlag = "none";
    private String rdRowFlag = "none";
    private String lastLineColor = "row1";
    private String saveFlag = "true";

    private String focusId;
    private String hufFamily;
    private String hufFlag;
    private String occupationDesc, acNoLen;
    private String chqFacility;
    private List<SelectItem> chqFacilityList;
    
    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getAcTypeDesc() {
        return acTypeDesc;
    }

    public void setAcTypeDesc(String acTypeDesc) {
        this.acTypeDesc = acTypeDesc;
    }

    public String getOldfullIntroAcno() {
        return oldfullIntroAcno;
    }

    public void setOldfullIntroAcno(String oldfullIntroAcno) {
        this.oldfullIntroAcno = oldfullIntroAcno;
    }

    public String getLastLineColor() {
        return lastLineColor;
    }

    public void setLastLineColor(String lastLineColor) {
        this.lastLineColor = lastLineColor;
    }

    public String getRdRowFlag() {
        return rdRowFlag;
    }

    public void setRdRowFlag(String rdRowFlag) {
        this.rdRowFlag = rdRowFlag;
    }

    public String getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(String saveFlag) {
        this.saveFlag = saveFlag;
    }

    public String getAmtLblDisFlag() {
        return amtLblDisFlag;
    }

    public void setAmtLblDisFlag(String amtLblDisFlag) {
        this.amtLblDisFlag = amtLblDisFlag;
    }

    public String getAmtTxtDisFlag() {
        return amtTxtDisFlag;
    }

    public void setAmtTxtDisFlag(String amtTxtDisFlag) {
        this.amtTxtDisFlag = amtTxtDisFlag;
    }

    public String getMatAmtLblDisFlag() {
        return matAmtLblDisFlag;
    }

    public void setMatAmtLblDisFlag(String matAmtLblDisFlag) {
        this.matAmtLblDisFlag = matAmtLblDisFlag;
    }

    public String getMatAmtTxtDisFlag() {
        return matAmtTxtDisFlag;
    }

    public void setMatAmtTxtDisFlag(String matAmtTxtDisFlag) {
        this.matAmtTxtDisFlag = matAmtTxtDisFlag;
    }

    public String getPeriodLblDisFlag() {
        return periodLblDisFlag;
    }

    public void setPeriodLblDisFlag(String periodLblDisFlag) {
        this.periodLblDisFlag = periodLblDisFlag;
    }

    public String getPeriodTxtDisFlag() {
        return periodTxtDisFlag;
    }

    public void setPeriodTxtDisFlag(String periodTxtDisFlag) {
        this.periodTxtDisFlag = periodTxtDisFlag;
    }

    public boolean isRoiDisFlag() {
        return roiDisFlag;
    }

    public void setRoiDisFlag(boolean roiDisFlag) {
        this.roiDisFlag = roiDisFlag;
    }

    public boolean isIntCodeDisFlag() {
        return intCodeDisFlag;
    }

    public void setIntCodeDisFlag(boolean intCodeDisFlag) {
        this.intCodeDisFlag = intCodeDisFlag;
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

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public boolean isFieldDisFlag() {
        return fieldDisFlag;
    }

    public void setFieldDisFlag(boolean fieldDisFlag) {
        this.fieldDisFlag = fieldDisFlag;
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

    public String getgNm() {
        return gNm;
    }

    public void setgNm(String gNm) {
        this.gNm = gNm;
    }

    public String getJt3Flag() {
        return jt3Flag;
    }

    public void setJt3Flag(String jt3Flag) {
        this.jt3Flag = jt3Flag;
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

    public String getJt4Flag() {
        return jt4Flag;
    }

    public void setJt4Flag(String jt4Flag) {
        this.jt4Flag = jt4Flag;
    }

    public String getMatAmt() {
        return matAmt;
    }

    public void setMatAmt(String matAmt) {
        this.matAmt = matAmt;
    }

    public String getNomineeAdd() {
        return nomineeAdd;
    }

    public void setNomineeAdd(String nomineeAdd) {
        this.nomineeAdd = nomineeAdd;
    }

    public Date getNomineedob() {
        return nomineedob;
    }

    public void setNomineedob(Date nomineedob) {
        this.nomineedob = nomineedob;
    }

    public String getDocumentDetails() {
        return documentDetails;
    }

    public void setDocumentDetails(String documentDetails) {
        this.documentDetails = documentDetails;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getRdInstallmentAmt() {
        return rdInstallmentAmt;
    }

    public void setRdInstallmentAmt(String rdInstallmentAmt) {
        this.rdInstallmentAmt = rdInstallmentAmt;
    }

    public String getRdPeriodInMonth() {
        return rdPeriodInMonth;
    }

    public void setRdPeriodInMonth(String rdPeriodInMonth) {
        this.rdPeriodInMonth = rdPeriodInMonth;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public String getNomineeNm() {
        return nomineeNm;
    }

    public void setNomineeNm(String nomineeNm) {
        this.nomineeNm = nomineeNm;
    }

    public String getGuardRelationShip() {
        return guardRelationShip;
    }

    public void setGuardRelationShip(String guardRelationShip) {
        this.guardRelationShip = guardRelationShip;
    }

    public String getGuardName() {
        return guardName;
    }

    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    public String getOperatingMode() {
        return operatingMode;
    }

    public void setOperatingMode(String operatingMode) {
        this.operatingMode = operatingMode;
    }

    public String getOccupations() {
        return occupations;
    }

    public void setOccupations(String occupations) {
        this.occupations = occupations;
    }

    public String getJointName1Id() {
        return jointName1Id;
    }

    public void setJointName1Id(String jointName1Id) {
        this.jointName1Id = jointName1Id;
    }

    public String getJointName2Id() {
        return jointName2Id;
    }

    public void setJointName2Id(String jointName2Id) {
        this.jointName2Id = jointName2Id;
    }

    public String getJointName3Id() {
        return jointName3Id;
    }

    public void setJointName3Id(String jointName3Id) {
        this.jointName3Id = jointName3Id;
    }

    public String getJointName4Id() {
        return jointName4Id;
    }

    public void setJointName4Id(String jointName4Id) {
        this.jointName4Id = jointName4Id;
    }

    public String getJointName1() {
        return jointName1;
    }

    public void setJointName1(String jointName1) {
        this.jointName1 = jointName1;
    }

    public String getJointName2() {
        return jointName2;
    }

    public void setJointName2(String jointName2) {
        this.jointName2 = jointName2;
    }

    public String getJointName3() {
        return jointName3;
    }

    public void setJointName3(String jointName3) {
        this.jointName3 = jointName3;
    }

    public String getJointName4() {
        return jointName4;
    }

    public void setJointName4(String jointName4) {
        this.jointName4 = jointName4;
    }

    public String getIntroduId() {
        return introduId;
    }

    public void setIntroduId(String introduId) {
        this.introduId = introduId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCorrespodenceAdd() {
        return correspodenceAdd;
    }

    public void setCorrespodenceAdd(String correspodenceAdd) {
        this.correspodenceAdd = correspodenceAdd;
    }

    public String getDdtitle() {
        return ddtitle;
    }

    public void setDdtitle(String ddtitle) {
        this.ddtitle = ddtitle;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPenGir() {
        return penGir;
    }

    public void setPenGir(String penGir) {
        this.penGir = penGir;
    }

    public String getPermanentAdd() {
        return permanentAdd;
    }

    public void setPermanentAdd(String permanentAdd) {
        this.permanentAdd = permanentAdd;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getFullIntroAcno() {
        return fullIntroAcno;
    }

    public void setFullIntroAcno(String fullIntroAcno) {
        this.fullIntroAcno = fullIntroAcno;
    }

    public String getNewAccType() {
        return newAccType;
    }

    public void setNewAccType(String newAccType) {
        this.newAccType = newAccType;
    }

    public List<SelectItem> getOperatingModeOption() {
        return operatingModeOption;
    }

    public void setOperatingModeOption(List<SelectItem> operatingModeOption) {
        this.operatingModeOption = operatingModeOption;
    }

    public List<SelectItem> getDocumentOption() {
        return documentOption;
    }

    public void setDocumentOption(List<SelectItem> documentOption) {
        this.documentOption = documentOption;
    }

    public List<SelectItem> getOccupationOption() {
        return occupationOption;
    }

    public void setOccupationOption(List<SelectItem> occupationOption) {
        this.occupationOption = occupationOption;
    }

    public Date getAccOpenDate() {
        return accOpenDate;
    }

    public void setAccOpenDate(Date accOpenDate) {
        this.accOpenDate = accOpenDate;
    }

    public List<SelectItem> getAcctTypeOption() {
        return acctTypeOption;
    }

    public void setAcctTypeOption(List<SelectItem> acctTypeOption) {
        this.acctTypeOption = acctTypeOption;
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

    public String getHufFamily() {
        return hufFamily;
    }

    public void setHufFamily(String hufFamily) {
        this.hufFamily = hufFamily;
    }

    public String getHufFlag() {
        return hufFlag;
    }

    public void setHufFlag(String hufFlag) {
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
     * Creates a new instance of AccountOpeningRegister
     */
    public AccountOpeningRegister() {
        try {
            acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            this.setAcNoLen(getAcNoLength());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            acctTypeOption = new ArrayList<SelectItem>();
            occupationOption = new ArrayList<SelectItem>();
            actCategoryList = new ArrayList<SelectItem>();
            operatingModeOption = new ArrayList<SelectItem>();
            documentOption = new ArrayList<SelectItem>();
            getOnLoadAllDroupDownValue();
            this.setMessage("");
            flag = "true";
            setJt1Flag("true");
            setJt2Flag("true");
            setJt3Flag("true");
            setJt4Flag("true");
            setHufFlag("true");
            setgNm("true");
            setNomineedob(new Date());
            fieldDisFlag = true;
            intCodeDisFlag = true;
            roiDisFlag = true;
            schemeCodeList = new ArrayList<SelectItem>();
            SetIntAcctToScheme();
            setAcTypeDesc("");
            setFocusId("btnSave");
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getOnLoadAllDroupDownValue() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            reportMethodsLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List acnoNatureList = reportMethodsLocal.getSBRDAcTypeList();
            String cerrentDate = reportMethodsLocal.currentDateByBrnCode(getOrgnBrCode());
            if ((cerrentDate == null) || (cerrentDate.equalsIgnoreCase(""))) {
                this.setMessage("");
                return;
            }
            setAccOpenDate(sdf.parse(cerrentDate));
            List occupationList = reportMethodsLocal.getOcupationDetails();
            List actCagtList = reportMethodsLocal.getActCategoryDetails();
            List operatingModeList = reportMethodsLocal.getOperatingModeDetails();
            List documentList = reportMethodsLocal.getDocumentDetails();
            if (!acnoNatureList.isEmpty()) {
                for (int i = 0; i < acnoNatureList.size(); i++) {
                    Vector ele = (Vector) acnoNatureList.get(i);
                    acctTypeOption.add(new SelectItem(ele.get(0).toString(), "[" + ele.get(0).toString() + "] "+ele.get(1).toString()));
                    
                }
            }

            if (!actCagtList.isEmpty()) {
                for (int k = 0; k < actCagtList.size(); k++) {
                    Vector ele2 = (Vector) actCagtList.get(k);
                    actCategoryList.add(new SelectItem(ele2.get(0).toString(), ele2.get(1).toString()));
                }
            }

            if (!occupationList.isEmpty()) {
                for (int k = 0; k < occupationList.size(); k++) {
                    Vector ele2 = (Vector) occupationList.get(k);
                    occupationOption.add(new SelectItem(ele2.get(0).toString(), ele2.get(1).toString()));
                }
            }
            if (!operatingModeList.isEmpty()) {
                for (int l = 0; l < operatingModeList.size(); l++) {
                    Vector ele3 = (Vector) operatingModeList.get(l);
                    operatingModeOption.add(new SelectItem(ele3.get(0).toString(), ele3.get(1).toString()));
                }
            }
            if (!documentList.isEmpty()) {
                documentOption = new ArrayList<SelectItem>();
                documentOption.add(new SelectItem("0", "--Select--"));
                for (int m = 0; m < documentList.size(); m++) {
                    Vector ele4 = (Vector) documentList.get(m);
                    documentOption.add(new SelectItem(ele4.get(0).toString(), ele4.get(1).toString()));
                }
            }

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getIntroducerInfo() {
        this.setMessage("");
        setAcno("");
        setDescription("");
        setCustName("");
        if (documents == null || documents.equalsIgnoreCase("0")) {
            if (this.oldfullIntroAcno == null || this.oldfullIntroAcno.equalsIgnoreCase("") || this.oldfullIntroAcno.length() == 0) {
                this.setMessage("PLEASE ENTER INTRODUCER A/C. NO.");
                return;
            }
        }
        if (this.oldfullIntroAcno.length() != 0) {
            //if (this.oldfullIntroAcno.length() < 12) {
            if (!this.oldfullIntroAcno.equalsIgnoreCase("") && ((this.oldfullIntroAcno.length() != 12) && (this.oldfullIntroAcno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("PLEASE ENTER PROPER INTRODUCER A/C. NO.");
                return;
            }
        }
        if (this.oldfullIntroAcno.length() == 0) {
            return;
        }

        //String nature = fullIntroAcno.substring(2, 4);
        try {
            FtsPostingMgmtFacadeRemote facadeRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            fullIntroAcno = facadeRemote.getNewAccountNumber(oldfullIntroAcno);
            String nature = facadeRemote.getAccountNature(fullIntroAcno);
            String info = acOpenFacadeRemote.cbsIntroInfo(introduId, fullIntroAcno, nature);
            if (info.equals("This Account No Does Not Exists")) {
                this.setMessage(info);
            } else if (info.equals("THIS CUSTOMER ID AND A/C. NO. ARE NOT CORRECT , SO PLEASE ENTER CORRECT ID AND A/C. NO")) {
                this.setMessage(info);
            } else {
                String[] values = null;
                String spliter = ": ";
                values = info.split(spliter);
                String acnos = values[0];
                String custNames = values[1];
                String descriptions = values[2];
                setAcno(acnos);
                setDescription(descriptions);
                setCustName(custNames);
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getCustomerIdInformation() {
        resetAll();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher custIdCheck = p.matcher(this.customerId);
        if (!custIdCheck.matches()) {
            this.setMessage("Please Enter Numeric Customer ID.");
            return;
        } else {
            this.setMessage("");
        }
        try {
            String custinfo = acOpenFacadeRemote.custIdInformation(customerId);
            flag = "false";
            setJt1Flag("false");
            setJt2Flag("false");
            setJt3Flag("false");
            setJt4Flag("false");
            setgNm("false");

            String[] values = null;
            String spliter = ": ";
            values = custinfo.split(spliter);
            String title = values[0];
            String custNames = values[1];

            String permanentAddress = values[2];
            String corrAddress = values[3];
            String fatherName = values[4];
            String mobileNos = values[5];
            String penGirNumber = values[6];

            String dobs = values[7];
            String intro = values[8];
            setDob(sdf.parse(dobs));
            setDdtitle(title);
            setName(custNames);

            setPermanentAdd(permanentAddress);
            setCorrespodenceAdd(corrAddress);
            setFathersName(fatherName);

            setMobileNo(mobileNos);
            setPenGir(penGirNumber);
            setIntroduId(intro);
            //---- Added by Manish Kumar
            this.occupations="";
            this.occupationDesc="";
            List selectList = acOpenFacadeRemote.getOccupation(this.customerId);
            if(!selectList.isEmpty()){
                Vector vec =(Vector)selectList.get(0);
                this.occupations =  vec.get(0).toString();   
                this.occupationDesc = vec.get(1).toString();
            }else{
                    this.occupations = "26";
                }    
            //-----
        } catch (Exception ex) {
            flag = "true";
            setJt1Flag("true");
            setJt2Flag("true");
            setJt3Flag("true");
            setJt4Flag("true");
            setgNm("true");
            setCustomerId("");
            setMessage(ex.getMessage());
        }
    }

    public void getJointHolder1formation() {
        setJointName1("");
        this.setMessage("");
        if (jointName1Id.equalsIgnoreCase(customerId)) {
            this.setMessage("Joint Holder1 Id And Customer Id Can Not Be Same");
            return;
        }
        try {
            String jointInfo = acOpenFacadeRemote.getJointHolderDetails(jointName1Id);
            setJointName1(jointInfo);

        } catch (Exception ex) {
            setJointName1Id("");
            setMessage(ex.getMessage());
        }
    }

    public void getJointHolder2formation() {
        setJointName2("");
        this.setMessage("");
        if (jointName2Id.equalsIgnoreCase(customerId)) {
            this.setMessage("Joint Holder2 Id And Customer Id Can Not Be Same");
            return;
        }
        if (this.jointName1Id.equalsIgnoreCase(this.jointName2Id)) {
            this.setMessage("Two Joint Holders Name Can not Be Same !!!");
            return;
        }
        try {
            String jointInfo = acOpenFacadeRemote.getJointHolderDetails(jointName2Id);
            setJointName2(jointInfo);

        } catch (Exception ex) {
            setJointName2Id("");
            setMessage(ex.getMessage());
        }
    }

    public void getJointHolder3formation() {
        setJointName3("");
        this.setMessage("");
        if (jointName3Id.equalsIgnoreCase(customerId)) {
            this.setMessage("Joint Holder3 Id And Customer Id Can Not Be Same");
            return;
        }
        if (this.jointName1Id.equalsIgnoreCase(this.jointName3Id) || this.jointName2Id.equalsIgnoreCase(this.jointName3Id)) {
            this.setMessage("Two Joint Holders Name Can not Be Same !!!");
            return;
        }
        try {
            String jointInfo = acOpenFacadeRemote.getJointHolderDetails(jointName3Id);
            setJointName3(jointInfo);
        } catch (Exception ex) {
            setJointName3Id("");
            setMessage(ex.getMessage());
        }
    }

    public void getJointHolder4formation() {
        setJointName4("");
        this.setMessage("");
        if (jointName4Id.equalsIgnoreCase(customerId)) {
            this.setMessage("Joint Holder4 Id And Customer Id Can Not Be Same");
            return;
        }
        if (this.jointName1Id.equalsIgnoreCase(this.jointName4Id) || this.jointName2Id.equalsIgnoreCase(this.jointName4Id) || this.jointName3Id.equalsIgnoreCase(this.jointName4Id)) {
            this.setMessage("TWO JOINT HOLDERS NAME CANNOT BE SAME !!!");
            return;
        }
        try {
            String jointInfo = acOpenFacadeRemote.getJointHolderDetails(jointName4Id);
            setJointName4(jointInfo);

        } catch (Exception ex) {
            setJointName4Id("");
            setMessage(ex.getMessage());
        }
    }

    public void saveData() {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        String cust_type = "";
        String nomDob = null;
        if ((customerId == null) || (customerId.equalsIgnoreCase(""))) {
            this.setMessage("Please Enter Customer Id");
            return;
        }
        String s1 = onblurNominee();
        if (!s1.equalsIgnoreCase("true")) {
            this.setMessage(s1);
            return;
        } else {
            this.setMessage("");
        }
        String s2 = onblurNomineerelation();
        if (!s2.equalsIgnoreCase("true")) {
            this.setMessage(s2);
            return;
        } else {
            this.setMessage("");
        }
        String s3 = onblurGuardianName();
        if (!s3.equalsIgnoreCase("true")) {
            this.setMessage(s3);
            return;
        } else {
            this.setMessage("");
        }
        String s4 = onblurrelationship();
        if (!s4.equalsIgnoreCase("true")) {
            this.setMessage(s4);
            return;
        } else {
            this.setMessage("");
        }
        if (newAccType.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please Select New Account Type");
            return;
        }

        if ((this.schemeCode.equalsIgnoreCase("")) || (this.schemeCode.equalsIgnoreCase("--Select--"))) {
            this.setMessage("Scheme code can not be blank");
            return;
        }
        
        if(actCategory.equalsIgnoreCase("HUF") || operatingMode.equalsIgnoreCase("18")){
            if(!(actCategory.equalsIgnoreCase("HUF") && operatingMode.equalsIgnoreCase("18"))){
                this.setMessage("Please Select Proper Account Category And Operation Mode");
                return;
            }
        }
        
        if (actCategory.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please Select The Account Category");
            return;
        }
//        if (occupations.equalsIgnoreCase("--Select--")) {
//            this.setMessage("Please Select The Occupation");
//            return;
//        }
        if (operatingMode.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please Select The Operation Mode");
            return;
        }
        if (documents.equalsIgnoreCase("0") && oldfullIntroAcno.equalsIgnoreCase("")) {
            this.setMessage("Please Select The Either Document or Introducer Account No.");
            return;
        }
        if (oldfullIntroAcno == null || oldfullIntroAcno.equals("")) {
            if ((documents == null || documents.equals("0"))) {
                setMessage("Please Select the Document");
                return;
            }
        }
        if (!documents.equals("0")) {
            if (documentDetails == null || documentDetails.equals("")) {
                this.setMessage("Please enter the document details");
                return;
            }
        }
        if ((operatingMode.equals("2")) || (operatingMode.equals("3")) || (operatingMode.equals("9"))
                || (operatingMode.equals("7")) || (operatingMode.equals("4")) || (operatingMode.equals("17")) || (operatingMode.equals("18"))) {
            if ((jointName1 == null) || (jointName1.equalsIgnoreCase(""))) {
                this.setMessage("Joint name1 Can not Empty");
                return;
            }
        } else if ((operatingMode.equals("5")) || (operatingMode.equals("12"))) {
            if ((jointName1 == null) || (jointName1.equalsIgnoreCase(""))) {
                this.setMessage("Joint name1 Can not Empty");
                return;
            }
            if ((jointName2 == null) || (jointName2.equalsIgnoreCase(""))) {
                this.setMessage("Joint name2 Can not Empty");
                return;
            }
        } else if (operatingMode.equals("6")) {
            if ((jointName1 == null) || (jointName1.equalsIgnoreCase(""))) {
                this.setMessage("Joint name1 Can not Empty");
                return;
            }
            if ((jointName2 == null) || (jointName2.equalsIgnoreCase(""))) {
                this.setMessage("Joint name2 Can not Empty");
                return;
            }
            if ((jointName3 == null) || (jointName3.equalsIgnoreCase(""))) {
                this.setMessage("Joint name3 Can not Empty");
                return;
            }
        }
        if ((operatingMode.equals("15")) || (operatingMode.equals("16"))) {
            if ((jointName1 == null) || (jointName1.equalsIgnoreCase(""))) {
                this.setMessage("Joint name1 Can not Empty");
                return;
            }
            if ((jointName2 == null) || (jointName2.equalsIgnoreCase(""))) {
                this.setMessage("Joint name2 Can not Empty");
                return;
            }
            if ((jointName3 == null) || (jointName3.equalsIgnoreCase(""))) {
                this.setMessage("Joint name3 Can not Empty");
                return;
            }
            if ((jointName4 == null) || (jointName4.equalsIgnoreCase(""))) {
                this.setMessage("Joint name4 Can not Empty");
                return;
            }
        } else if ((operatingMode.equals("11")) || (operatingMode.equals("13"))) {
            if ((guardName == null) || (guardName.equalsIgnoreCase(""))) {
                this.setMessage("Please enter Guardian name cannot be empty.");
                return;
            } else if ((guardRelationShip == null) || (guardRelationShip.equalsIgnoreCase(""))) {
                this.setMessage("Please enter Guardian Relation.");
                return;
            }
        }

        if ((nomineeNm == null) || (nomineeNm.equalsIgnoreCase("")) && (!nomineeAdd.equalsIgnoreCase("") || !nomineeRelation.equalsIgnoreCase(""))) {
            this.setMessage("First Select Nominee.");
            return;
        } else if (!nomineeNm.equalsIgnoreCase("")) {
            this.setMessage("");
            if ((nomineeAdd == null) || (nomineeAdd.equalsIgnoreCase(""))) {
                this.setMessage("Nominee Address cann't be empty.");
                return;
            }
            if (nomineedob == null) {
                this.setMessage("Nominee Date Of Birth cann't be empty.");
                return;
            } else {
                nomDob = ymd.format(this.nomineedob);
            }
            if ((nomineeRelation == null) || (nomineeRelation.equalsIgnoreCase(""))) {
                this.setMessage("Nominee Relationship cann't be empty.");
                return;
            }
            String s5 = onblurNomineeDate();
            if (!s5.equalsIgnoreCase("true")) {
                this.setMessage(s5);
                return;
            } else {
                this.setMessage("");
            }
        }
        if ((description.equals("CLOSED"))) {
            this.setMessage("Please Introducer Operating Mode is CLOSED.");
            return;
        }

        if ((this.intCode.equalsIgnoreCase("")) || (this.intCode.equalsIgnoreCase("--Select--"))) {
            this.setMessage("Int. code can not be blank");
            return;
        }
        //end
        
        if(operatingMode.equalsIgnoreCase("18")){
            if ((hufFamily.equals("")) || (hufFamily == null)) {                
                this.setMessage("Huf Family Detail Can't Be Blank In Case Of HUF Account.");
                return;
            }
        }
        
        if (actNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
            if (rdInstallmentAmt.contains(".")) {
                rdInstallmentAmt = rdInstallmentAmt.substring(0, rdInstallmentAmt.indexOf("."));
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher rdInstallmentAmtCheck = p.matcher(rdInstallmentAmt);
            if (!rdInstallmentAmtCheck.matches()) {
                this.setMessage("Please Enter Valid  RD Installment Amt.");
                return;
            }
            if ((rdInstallmentAmt == null) || (rdInstallmentAmt.equalsIgnoreCase(""))) {
                setRoi("");
                setMatAmt("");
                setRdInstallmentAmt("");
                this.setMessage("Please Fill Inst Amt And Then Period");
                return;
            }
            if (Integer.parseInt(rdInstallmentAmt) < 0) {
                this.setMessage("Please Enter Correct Installment Amount");
                return;
            }
            if ((rdPeriodInMonth == null) || (rdPeriodInMonth.equalsIgnoreCase(""))) {
                setRoi("");
                setMatAmt("");
                this.setMessage("Please fill period in month !!");
                return;
            }
            Matcher rdPeriodInMonthCheck = p.matcher(rdPeriodInMonth);
            if (!rdPeriodInMonthCheck.matches()) {
                this.setMessage("Please Enter Valid  Period in Month.");
                return;
            }
            if (Integer.parseInt(rdPeriodInMonth) < 0) {
                this.setMessage("Please Enter Correct Period In month");
                return;
            }
            String periodInMnths = onblurPeriodInMonth();
            if (!periodInMnths.equalsIgnoreCase("true")) {
                this.setMessage(periodInMnths);
                return;
            } else {
                this.setMessage("");
            }

            //Added for Scheme Code handling
            if ((this.rdInstallmentAmt.equalsIgnoreCase("")) || (this.rdInstallmentAmt == null)) {
                this.setMessage("Inst. Amt can not be blank");
                return;
            }            
        }
        try {
            String dobs = ymd.format(this.dob);
            String accOpenDates = ymd.format(this.accOpenDate);
            if (!actNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                rdPeriodInMonth = "0";
                rdInstallmentAmt = "0";
                roi = "0";
            }
            if (actNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) && Float.parseFloat(roi) <= 0) {
                this.setMessage("ROI Can not Be Zero.Please Fill Corresponding amount in Slab");
                return;
            }
            String save = acOpenFacadeRemote.saveAccountOpenSbRd(cust_type, customerId, newAccType, ddtitle, name, acOpenFacadeRemote.removeSomeSpecialChar(correspodenceAdd), acOpenFacadeRemote.removeSomeSpecialChar(permanentAdd), mobileNo, dobs, Integer.parseInt(occupations), Integer.parseInt(operatingMode), penGir, guardName, guardRelationShip, "01", accOpenDates, getUserName(), fathersName, acno, jointName1, jointName2, getOrgnBrCode(), nomineeNm, nomineeRelation, jointName3, jointName4, Integer.parseInt(rdPeriodInMonth), Float.parseFloat(rdInstallmentAmt), Float.parseFloat(roi), Integer.parseInt(documents), documentDetails, nomineeAdd, nomDob, jointName1Id, jointName2Id, jointName3Id, jointName4Id, schemeCode, intCode,actCategory, hufFamily, Integer.parseInt(this.chqFacility));            
            this.setCustomerId("");
            resetAll();
            this.setMessage(save);
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getOperatingModeInformation() {
        this.setHufFamily("");
        this.setHufFlag("true");
        try {
            if ((operatingMode.equals("1")) || (operatingMode.equals("8"))) {
                setJt1Flag("true");
                setJt2Flag("true");
                setJt3Flag("true");
                setJt4Flag("true");
            } else if ((operatingMode.equals("2")) || (operatingMode.equals("3")) || (operatingMode.equals("7"))
                    || (operatingMode.equals("9")) || (operatingMode.equals("17")) || (operatingMode.equals("18"))) {
                setJt1Flag("false");
                setJt2Flag("true");
                setJt3Flag("true");
                setJt4Flag("true");
                setHufFlag("false");
            } else if (operatingMode.equals("4")) {
                setJt1Flag("false");
                setJt2Flag("false");
                setJt3Flag("false");
                setJt4Flag("false");
            } else if ((operatingMode.equals("5")) || (operatingMode.equals("12"))) {
                setJt1Flag("false");
                setJt2Flag("false");
                setJt3Flag("true");
                setJt4Flag("true");
            } else if (operatingMode.equals("6")) {
                setJt1Flag("false");
                setJt2Flag("false");
                setJt3Flag("false");
                setJt4Flag("true");
            } else if ((operatingMode.equals("14")) || (operatingMode.equals("15")) || (operatingMode.equals("16"))) {
                setJt1Flag("false");
                setJt2Flag("false");
                setJt3Flag("false");
                setJt4Flag("false");
            }
            if ((operatingMode.equals("11")) || (operatingMode.equals("13"))) {
                setgNm("false");
            } else {
                setgNm("false");
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public String onblurNomineeDate() {
        this.setMessage("");
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        String str = "";
        try {
            if (nomineedob == null) {
                this.setMessage("Please Enter Nominee date.");
                return str = "Please Enter Nominee date..";
            }

            List dateDif = acOpenFacadeRemote.dateDiffStatementFreqDate(sd.format(this.nomineedob));
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

    public void resetAllField() {
        try {
            setCustomerId("");
            setAcTypeDesc("");
            this.occupationDesc="";
            resetAll();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void resetAll() {
        try {
            Date date = new Date();
            setAcTypeDesc("");
            setNewAccType("--Select--");
            setFathersName("");
            setMobileNo("");
            setDdtitle("");
            setName("");
            setCorrespodenceAdd("");
            setActCategory("--Select--");
            setOccupations("--Select--");
            setPermanentAdd("");
            setOperatingMode("--Select--");
            setPenGir("");
            setGuardRelationShip("");
            setNomineeNm("");
            setNomineeAdd("");
            setGuardName("");
            setNomineeRelation("");
            setJointName1Id("");
            setJointName2Id("");
            setJointName3Id("");
            setJointName4Id("");
            setJointName1("");
            setJointName2("");
            setJointName3("");
            setJointName4("");
            setDocumentDetails("");
            setIntroduId("");
            setFullIntroAcno("");
            setOldfullIntroAcno("");
            setOldfullIntroAcno("");
            setDocuments("0");
            setCustName("");
            setDescription("");
            setAcno("");
            setRoi("");
            setRdPeriodInMonth("");
            setRdInstallmentAmt("");
            setMatAmt("");
            this.setDob(date);
            this.setNomineedob(date);
            this.setMessage("");
            this.setSchemeCode("--Select--");
            this.setIntCode("--Select--");
            schemeCodeList = new ArrayList<SelectItem>();
            fieldDisFlag = true;
            intCodeDisFlag = true;
            roiDisFlag = true;
            this.setRoi("");
            this.setMatAmt("");
            this.setRdInstallmentAmt("");
            this.setRdPeriodInMonth("");
            amtLblDisFlag = "none";
            amtTxtDisFlag = "none";
            periodLblDisFlag = "none";
            periodTxtDisFlag = "none";
            matAmtLblDisFlag = "none";
            matAmtTxtDisFlag = "none";
            rdRowFlag = "none";
            lastLineColor = "row1";
            setJt1Flag("true");
            setJt2Flag("true");
            setJt3Flag("true");
            setJt4Flag("true");
            setHufFlag("true");
            setHufFamily("");
            this.occupationDesc = "";
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        setCustomerId("");
        this.setMessage("");
        resetAll();
        return "case1";
    }

    public String onblurNominee() {
        if (!this.nomineeNm.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher nomineeNameCheck = p.matcher(this.nomineeNm);
            if (!nomineeNameCheck.matches()) {
                this.setMessage("Please Enter Nominee Name Correctly.");
                return "Please Enter Nominee Name Correctly.";
            } else {
                this.setMessage("");
                return "true";
            }
        } else {
            this.setMessage("");
            return "true";
        }
    }

    public String onblurNomineerelation() {
        if (!this.nomineeRelation.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher nomineeRelationCheck = p.matcher(this.nomineeRelation);
            if (!nomineeRelationCheck.matches()) {
                this.setMessage("Please Enter Relationship With Nominee Correctly.");
                return "Please Enter Relationship With Nominee Correctly.";
            } else {
                this.setMessage("");
                return "true";
            }
        } else {
            this.setMessage("");
            return "true";
        }
    }

    public String onblurGuardianName() {
        if (!this.guardName.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher guardNameCheck = p.matcher(this.guardName);
            if (!guardNameCheck.matches()) {
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

    public String onblurrelationship() {
        if (!this.guardRelationShip.equalsIgnoreCase("")) {
            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher guardRelationShipCheck = p.matcher(this.guardRelationShip);
            if (!guardRelationShipCheck.matches()) {
                this.setMessage("Please Enter Relationship Correctly.");
                return "Please Enter Relationship Correctly.";
            } else {
                this.setMessage("");
                return "true";
            }
        } else {
            this.setMessage("");
            return "true";
        }
    }

    public String onblurInstAmount() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher rdInstallmentAmtCheck = p.matcher(rdInstallmentAmt);
        if (!rdInstallmentAmtCheck.matches()) {
            this.setMessage("Please Enter Valid  RD Installment Amt.");
            return "Please Enter Valid  RD Installment Amt";
        }
        if ((rdInstallmentAmt == null) || (rdInstallmentAmt.equalsIgnoreCase(""))) {
            setRoi("");
            setMatAmt("");
            setRdInstallmentAmt("");
            this.setMessage("Please Fill Inst Amt And Then Period");
            return "Please Fill Inst Amt And Then Period.";
        }
        if (Float.parseFloat(rdInstallmentAmt) < 0.0) {
            this.setMessage("Please Enter Valid Installment Amount.");
            return "Please Enter Valid Installment Amount.";
        } else {
            this.setMessage("");
            return "true";
        }
    }

    public String onblurPeriodInMonth() {

        if (Float.parseFloat(rdPeriodInMonth) < 0.0) {
            this.setMessage("Please Enter  Valid Period(In Months).");
            return "Please Enter  Valid Period(In Months).";
        } else {
            this.setMessage("");
            return "true";
        }
    }

    public void SetIntAcctToScheme() {
        try {
            intCodeList = new ArrayList<SelectItem>();
            List resultList = acOpenFacadeRemote.interestCodeCombo();
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele6 = (Vector) resultList.get(i);
                    intCodeList.add(new SelectItem(ele6.get(0).toString(), ele6.get(1).toString()));
                }
            }
            intCodeDisFlag = true;
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void setInterestCode() {
        this.setMessage("");
        this.setIntCode("--Select--");
        try {
            if (this.schemeCode.equalsIgnoreCase("--Select--")) {
                this.setMessage("PLEASE SELECT SCHEME CODE !!!");
                return;
            }
            List resultList = acOpenFacadeRemote.SetValueAcctToScheme(schemeCode);
            if (resultList.size() > 0) {
                Vector element = (Vector) resultList.get(0);
                this.setIntCode(element.get(3).toString());
                getLoanRoi();
            } else {
                this.setIntCode("--Select--");
            }
            //intCodeDisFlag = false;
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void acTypeLostFocus() {
        if (this.newAccType.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please select account type.");
            return;
        }
        setRoi("");
        setRdPeriodInMonth("");
        setRdInstallmentAmt("");
        setMatAmt("");
        this.setIntCode("--Select--");
        getAccountTypeDesc();
        if (actNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
            amtLblDisFlag = "";
            amtTxtDisFlag = "";
            periodLblDisFlag = "";
            periodTxtDisFlag = "";
            matAmtLblDisFlag = "";
            matAmtTxtDisFlag = "";
            rdRowFlag = "";
            lastLineColor = "row2";
            fieldDisFlag = false;
            setScheme();

        } else {
            amtLblDisFlag = "none";
            amtTxtDisFlag = "none";
            periodLblDisFlag = "none";
            periodTxtDisFlag = "none";
            matAmtLblDisFlag = "none";
            matAmtTxtDisFlag = "none";
            rdRowFlag = "none";
            lastLineColor = "row1";
            fieldDisFlag = false;
            setScheme();
        }
        this.setMessage("");
    }

    public void getAccountTypeDesc() {
        setAcTypeDesc("");
        try {
            List acTypeDescription = acOpenFacadeRemote.getAcTypeDescription(this.newAccType);
            if (!acTypeDescription.isEmpty()) {
                Vector vect = (Vector) acTypeDescription.get(0);
                setAcTypeDesc("[" + vect.get(0).toString() + "]");
                actNature = vect.get(1).toString();
            }            
            chqFacilityList = new ArrayList<SelectItem>();
            if(acOpenFacadeRemote.getChqFacilityTrue(this.newAccType).equalsIgnoreCase("true")){
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
            List resultList7 = acOpenFacadeRemote.schemeCodeCombo(this.newAccType);
            schemeCodeList = new ArrayList<SelectItem>();
            if (!resultList7.isEmpty()) {
                for (int i = 0; i < resultList7.size(); i++) {
                    Vector ele7 = (Vector) resultList7.get(i);
                    schemeCodeList.add(new SelectItem(ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getLoanRoi() {
        this.setMessage("");
        saveFlag = "true";
        this.setRoi("");
        long rdDays = 0;
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            if (this.newAccType.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select Account Type.");
                return;
            }

            if (actNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                this.setRoi("");
                setMatAmt("");
                if (this.rdInstallmentAmt == null || this.rdInstallmentAmt.equalsIgnoreCase("") || this.rdInstallmentAmt.length() == 0) {
                    this.setMessage("PLEASE ENTER AMOUNT.");
                    return;
                }
                Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                Matcher limitCheck = p.matcher(this.rdInstallmentAmt);
                if (!limitCheck.matches()) {
                    this.setRdInstallmentAmt(null);
                    this.setMessage("PLEASE ENTER AMOUNT.");
                    return;
                }
                if (this.rdInstallmentAmt.contains(".")) {
                    if (this.rdInstallmentAmt.indexOf(".") != this.rdInstallmentAmt.lastIndexOf(".")) {
                        this.setMessage("INVALID AMOUNT .PLEASE FILL THE LIMIT CORRECTLY.");
                        return;
                    } else if (this.rdInstallmentAmt.substring(rdInstallmentAmt.indexOf(".") + 1).length() > 2) {
                        this.setMessage("PLEASE FILL THE AMOUNT UPTO TWO DECIMAL PLACES.");
                        return;
                    }
                }
                if (Float.parseFloat(this.rdInstallmentAmt) < 0) {
                    this.setRdInstallmentAmt(null);
                    this.setMessage("AMOUNT CANNOT BE NEGATIVE !!!");
                    return;
                }

                //Added on 12/07/2011
                if ((this.rdPeriodInMonth.equalsIgnoreCase("")) || (this.rdPeriodInMonth == null)) {
                    this.setMessage("Please fill Period");
                    return;
                }
                int code = acOpenFacadeRemote.getRdEnableCode();
                if (code == 1) {
                    setRoiDisFlag(false);
                    setFocusId("intTxtRoi");
                } else {
                    setRoiDisFlag(true);
                    setFocusId("btnSave");
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                int period = Integer.parseInt(rdPeriodInMonth);

                List resD = acOpenFacadeRemote.getRDDaysLst(this.getIntCode());
                int minDay = 0;
                int maxDay = 0;
                if (resD.size() <= 0) {
                    this.setMessage("Roi not found!!!");
                    return;
                } else {
                    Vector element = (Vector) resD.get(0);
                    minDay = Integer.parseInt(element.get(0).toString());
                    maxDay = Integer.parseInt(element.get(1).toString());
                }

                if ((period < minDay) || (period > maxDay)) {
                    this.setMessage("Period is Greater or Less For Passed Scheme Slab!!");
                    return;
                }
                String result = acOpenFacadeRemote.GetROIAcOpen(this.getIntCode(), Double.parseDouble(rdInstallmentAmt), period, ymd.format(this.accOpenDate));
                if (result == null) {
                    this.setMessage("Roi not found!!!");
                    return;
                } else {
                    getRoisAccountOpen(result);
                    this.setRoi(result);
                }
                saveFlag = "false";
            } else {
                this.setRdInstallmentAmt("0");
                if (this.intCode.equalsIgnoreCase("--Select--")) {
                    this.setMessage("PLEASE SELECT INTEREST CODE.");
                    return;
                }
                saveFlag = "false";
                String result = acOpenFacadeRemote.getROI(this.intCode, Float.parseFloat(this.rdInstallmentAmt), ymd.format(this.accOpenDate));
                if (result == null) {
                    this.setMessage("Roi not found!!!");
                    return;
                } else {
                    this.setRoi(result);
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getMaturityAmt() {
        this.setMessage("");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher roiCheck = p.matcher(roi);
        if ((roi == null) || (roi.equalsIgnoreCase(""))) {
            setRoi("");
            setMatAmt("");
            this.setMessage("Please fill Roi");
            return;
        }
        if (Float.parseFloat(roi) <= 0) {
            setMatAmt("");
            this.setMessage("Please enter valid  Roi.");
            return;
        }
        if (!roiCheck.matches()) {
            setMatAmt("");
            this.setMessage("Please enter valid  Roi.");
            return;
        }

        getRoisAccountOpen(getRoi());
    }

    public void getRoisAccountOpen(String roi) {
        this.setMessage("");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher rdInstallmentAmtCheck = p.matcher(rdInstallmentAmt);
        if (!rdInstallmentAmtCheck.matches()) {
            this.setMessage("Please Enter Valid  RD Installment Amt.");
            return;
        }
        if ((rdInstallmentAmt == null) || (rdInstallmentAmt.equalsIgnoreCase(""))) {
            setRoi("");
            setMatAmt("");
            setRdInstallmentAmt("");
            this.setMessage("Please Fill Inst Amt And Then Period");
            return;
        }

        if ((rdPeriodInMonth == null) || (rdPeriodInMonth.equalsIgnoreCase(""))) {
            setRoi("");
            setMatAmt("");
            setRdInstallmentAmt("");
            this.setMessage("Please Fill Then Period in Month");
            return;
        }
        Matcher rdPeriodInMonthCheck = p.matcher(rdPeriodInMonth);
        if (!rdPeriodInMonthCheck.matches()) {
            this.setMessage("Please Enter Valid  Period in Month.");
            return;
        }
        if (Float.parseFloat(rdPeriodInMonth) < 0.0) {
            this.setMessage("Please Enter  Valid Period(In Months).");
            return;
        }
        try {
            String matAmts = acOpenFacadeRemote.cbsRdCalculation(Float.parseFloat(rdInstallmentAmt), Integer.parseInt(rdPeriodInMonth), Float.parseFloat(roi));
            setMatAmt(matAmts);
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void checkOccupation() {
        if (this.occupations.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please select occupation");
            return;
        }
    }

    public void checkActCatg() {
        if (this.actCategory.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please select account category");
            return;
        }
    }
}
