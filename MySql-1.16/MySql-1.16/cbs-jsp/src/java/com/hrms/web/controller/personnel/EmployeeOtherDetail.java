/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.personnel;

import com.cbs.web.controller.BaseBean;
import com.hrms.common.to.HrPersonnelDetailsPKTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.web.controller.Validator;
import com.hrms.web.delegate.PersonnelDelegate;
import java.text.SimpleDateFormat;

/**
 *
 * @author Ankit Verma
 */
public class EmployeeOtherDetail extends BaseBean {

    Validator validator;
    private String fatherName_husbandName, fatherName_husbandOcc, fatherName_husbandDesg, fatherName_husbandOrg, fatherName_husbandPhone, message;
    private String birthCity, birthState, height, weight, bloodGroup, health, weddingDate, emailId, religion, chest, nation, visaDetail, children;
    private Character maritalStatus;
    private String adolescentNo, adolescentTokenNo, adolescentDate, adolescentReferenceNo, adolescentRelay, adolescentRelayDate;
    private boolean disableWeddingDate, disableSaveButton, disableDeleteButton, disableCancelButton;
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    protected PersonnelDelegate personnelDelegate;

    /** Creates a new instance of EmployeeOtherDetail */
    public EmployeeOtherDetail() {
        validator = new Validator();
        try {
            personnelDelegate = new PersonnelDelegate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAdolescentDate() {
        return adolescentDate;
    }

    public void setAdolescentDate(String adolescentDate) {
        this.adolescentDate = adolescentDate;
    }

    public String getAdolescentNo() {
        return adolescentNo;
    }

    public void setAdolescentNo(String adolescentNo) {
        this.adolescentNo = adolescentNo;
    }

    public String getAdolescentReferenceNo() {
        return adolescentReferenceNo;
    }

    public void setAdolescentReferenceNo(String adolescentReferenceNo) {
        this.adolescentReferenceNo = adolescentReferenceNo;
    }

    public String getAdolescentRelay() {
        return adolescentRelay;
    }

    public void setAdolescentRelay(String adolescentRelay) {
        this.adolescentRelay = adolescentRelay;
    }

    public String getAdolescentRelayDate() {
        return adolescentRelayDate;
    }

    public void setAdolescentRelayDate(String adolescentRelayDate) {
        this.adolescentRelayDate = adolescentRelayDate;
    }

    public String getAdolescentTokenNo() {
        return adolescentTokenNo;
    }

    public void setAdolescentTokenNo(String adolescentTokenNo) {
        this.adolescentTokenNo = adolescentTokenNo;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public String getBirthState() {
        return birthState;
    }

    public void setBirthState(String birthState) {
        this.birthState = birthState;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFatherName_husbandDesg() {
        return fatherName_husbandDesg;
    }

    public void setFatherName_husbandDesg(String fatherName_husbandDesg) {
        this.fatherName_husbandDesg = fatherName_husbandDesg;
    }

    public String getFatherName_husbandName() {
        return fatherName_husbandName;
    }

    public void setFatherName_husbandName(String fatherName_husbandName) {
        this.fatherName_husbandName = fatherName_husbandName;
    }

    public String getFatherName_husbandOcc() {
        return fatherName_husbandOcc;
    }

    public void setFatherName_husbandOcc(String fatherName_husbandOcc) {
        this.fatherName_husbandOcc = fatherName_husbandOcc;
    }

    public String getFatherName_husbandOrg() {
        return fatherName_husbandOrg;
    }

    public void setFatherName_husbandOrg(String fatherName_husbandOrg) {
        this.fatherName_husbandOrg = fatherName_husbandOrg;
    }

    public String getFatherName_husbandPhone() {
        return fatherName_husbandPhone;
    }

    public void setFatherName_husbandPhone(String fatherName_husbandPhone) {
        this.fatherName_husbandPhone = fatherName_husbandPhone;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Character getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Character maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public String getVisaDetail() {
        return visaDetail;
    }

    public void setVisaDetail(String visaDetail) {
        this.visaDetail = visaDetail;
    }

    public String getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(String weddingDate) {
        this.weddingDate = weddingDate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public boolean isDisableCancelButton() {
        return disableCancelButton;
    }

    public void setDisableCancelButton(boolean disableCancelButton) {
        this.disableCancelButton = disableCancelButton;
    }

    public boolean isDisableDeleteButton() {
        return disableDeleteButton;
    }

    public void setDisableDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
    }

    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }

    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    public boolean isDisableWeddingDate() {
        return disableWeddingDate;
    }

    public void setDisableWeddingDate(boolean disableWeddingDate) {
        this.disableWeddingDate = disableWeddingDate;
    }

    public boolean validateData() {
        if (!validator.validateInteger(children)) {
            setMessage("Children Can Have Numeric Value Only !!");
            return false;
        }
        if (!validator.validatePhone(fatherName_husbandPhone)) {
            setMessage("Father/Husband Phone Can Have Numeric Value Only!!");
            return false;
        }
        if (!validator.validateDoublePositive(height)) {
            setMessage("Height Can Have Numeric Value Only  !!");
            return false;
        }

        if (!validator.validateDoublePositive(weight)) {
            setMessage("Weight Can Have Numeric Value Only  !!");
            return false;
        }
        if (!validator.validateDoublePositive(chest)) {
            setMessage("Chest Can Have Numeric Value Only  !!");
            return false;
        }
        if (!validator.validateDate_dd_mm_yyyy(weddingDate)) {
            setMessage("Wedding Date Is In Incorrect Format. It Should Be In dd/mm/yyyy Format!!");
            return false;
        }
        if (!validator.validateDate_dd_mm_yyyy(adolescentDate)) {
            setMessage("Adolescent Date Is In Incorrect Format. It Should Be In dd/mm/yyyy Format!!");
            return false;
        }
        if (!validator.validateDate_dd_mm_yyyy(adolescentRelayDate)) {
            setMessage("Adolescent Relay FDate Is In Incorrect Format. It Should Be In dd/mm/yyyy Format!!");
            return false;
        }
        if (!emailId.equalsIgnoreCase("")) {
            if (!validator.validateEmail(emailId)) {
                setMessage("Please enter valid email id.");
                return false;
            }
        }

        return true;
    }

    public void onChangeMaritalStatus() {
        try {
            if (maritalStatus == 'M') {
                setWeddingDate("");
                setDisableWeddingDate(false);
            } else {
                setDisableWeddingDate(true);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void saveEmpOtherDetails() {
        if (bloodGroup.equalsIgnoreCase("0")) {
            bloodGroup = "NA";
        }
        if (children == null) {
            children = "0";
        }
        if (maritalStatus == '0') {
            maritalStatus = 'U';
        }
        if (weddingDate == null || weddingDate.equalsIgnoreCase("")) {
            weddingDate = "01/01/1900";
        }
        if (adolescentDate == null || adolescentDate.equalsIgnoreCase("")) {
            adolescentDate = "01/01/1900";
        }
        if (adolescentRelayDate == null || adolescentRelayDate.equalsIgnoreCase("")) {
            adolescentRelayDate = "01/01/1900";
        }
        if (children == null || children.equalsIgnoreCase("")) {
            setChildren("0");
        }
        if (height == null || height.equalsIgnoreCase("")) {
            setHeight("0");
        }
        if (weight == null || weight.equalsIgnoreCase("")) {
            setWeight("0");
        }
        if (chest == null || chest.equalsIgnoreCase("")) {
            setChest("0");
        }
        try {
            if (validateData()) {
                Long empCode = (Long) getHttpSession().getAttribute("EMP_CODE");
                int compCode = (Integer) getHttpSession().getAttribute("COMP_CODE");
                String empId = (String) getHttpSession().getAttribute("EMP_ID");

                HrPersonnelDetailsTO hrPersonnelDetailsTO = new HrPersonnelDetailsTO();
                HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO = new HrPersonnelDetailsPKTO();
                hrPersonnelDetailsPKTO.setCompCode(compCode);
                hrPersonnelDetailsPKTO.setEmpCode(empCode);
                hrPersonnelDetailsTO.setHrPersonnelDetailsPKTO(hrPersonnelDetailsPKTO);
                hrPersonnelDetailsTO.setEmpId(empId);
                hrPersonnelDetailsTO.setHeight(height);
                hrPersonnelDetailsTO.setWeight(weight);
                hrPersonnelDetailsTO.setHealth(health);
                hrPersonnelDetailsTO.setBloodGrp(bloodGroup);
                hrPersonnelDetailsTO.setBirthCity(birthCity);
                hrPersonnelDetailsTO.setBirthState(birthState);
                hrPersonnelDetailsTO.setWeddingDate(dmyFormat.parse(weddingDate));
                hrPersonnelDetailsTO.setEmailId(emailId);
                hrPersonnelDetailsTO.setCertAdosNo(adolescentNo);
                hrPersonnelDetailsTO.setCertAdosDate(dmyFormat.parse(adolescentDate));
                hrPersonnelDetailsTO.setCertTokNo(adolescentTokenNo);
                hrPersonnelDetailsTO.setCertRef(adolescentReferenceNo);
                hrPersonnelDetailsTO.setRelay(adolescentRelay);
                hrPersonnelDetailsTO.setRelayDate(dmyFormat.parse(adolescentRelayDate));
                hrPersonnelDetailsTO.setNation(nation);
                hrPersonnelDetailsTO.setChest(chest);
                hrPersonnelDetailsTO.setReligion(religion);
                hrPersonnelDetailsTO.setMaritalStatus(maritalStatus);
                hrPersonnelDetailsTO.setVisaDet(visaDetail);
                hrPersonnelDetailsTO.setChildren(Integer.parseInt(children));
                hrPersonnelDetailsTO.setFathHusName(fatherName_husbandName);
                hrPersonnelDetailsTO.setFatherHusOcc(fatherName_husbandOcc);
                hrPersonnelDetailsTO.setFatherHusDesig(fatherName_husbandDesg);
                hrPersonnelDetailsTO.setFatherHusOrg(fatherName_husbandOrg);
                hrPersonnelDetailsTO.setFatherHusPhone(fatherName_husbandPhone);
                String result = personnelDelegate.saveHrPersonnelEmployeeDetails(hrPersonnelDetailsTO, "updateEmpOtherDetails");
                refreshEmpOtherDtlTab();
                setMessage(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshEmpOtherDtlTab() {
        setFatherName_husbandDesg("");
        setFatherName_husbandName("");
        setFatherName_husbandOcc("");
        setFatherName_husbandOrg("");
        setFatherName_husbandPhone("");
        setBirthCity("");
        setBirthState("");
        setNation("");
        setHeight("");
        setWeight("");
        setHealth("");
        setChest("");
        setBloodGroup("0");
        setChildren("");
        setMaritalStatus('0');
        setWeddingDate("");
        setReligion("");
        setEmailId("");
        setVisaDetail("");
        setAdolescentDate("");
        setAdolescentNo("");
        setAdolescentReferenceNo("");
        setAdolescentRelay("");
        setAdolescentRelayDate("");
        setAdolescentTokenNo("");
        setMessage("");
    }
}
