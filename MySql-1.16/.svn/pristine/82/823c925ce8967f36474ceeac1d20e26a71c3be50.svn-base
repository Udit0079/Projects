/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.facade.ho.share.CertIssueFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class BankProfile extends BaseBean {

    private String message;
    private String function;
    private String bankName;
    private String regOfficeAddress;
    private String regOfficePin;
    private String hoOfficeAddress;
    private String hoOfficePin;
    private String status;
    private String category;
    private String rbiRating;
    private String subCategory;
    private String licenceDate;
    private String licenceNo;
    private String lastInspectionDate;
    private String agmDate;
    private String totRegularMembers;
    private String totNominalMembers;
    private String internalAuditDate;
    private String majorIrRegNo;
    private String minorIrRegNo;
    private String parasFullyNo;
    private String parasOutstandNo;
    private String bankRegion;
    private String btnValue;
    private String confirmationMessage;
    private List<SelectItem> functionList;
    private List<SelectItem> statusList;        //221
    private List<SelectItem> categoryList;      //222
    private List<SelectItem> rbiRatingList;     //223
    private List<SelectItem> subCategoryList;   //224
    private List<SelectItem> bankRegionList;    //225
    private CertIssueFacadeRemote certRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Pattern numericPattern = Pattern.compile("[0-9]*");
    Pattern amtPattern = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

    public BankProfile() {
        this.setMessage("");
        try {
            certRemote = (CertIssueFacadeRemote) ServiceLocator.getInstance().lookup("CertIssueFacade");
            onLoadData();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onLoadData() {
        functionList = new ArrayList<SelectItem>();
        statusList = new ArrayList<SelectItem>();
        categoryList = new ArrayList<SelectItem>();
        rbiRatingList = new ArrayList<SelectItem>();
        subCategoryList = new ArrayList<SelectItem>();
        bankRegionList = new ArrayList<SelectItem>();
        try {
            /*Setting function list*/
            functionList.add(new SelectItem("A", "ADD"));
            functionList.add(new SelectItem("M", "MODIFY"));
            /*Setting status list*/
            List result = certRemote.getAllRefRecNoData("221");
            if (result.isEmpty()) {
                this.setMessage("There should be data into Cbs Ref Rec Type for 221.");
                return;
            }
            for (int i = 0; i < result.size(); i++) {
                Vector vtr = (Vector) result.get(i);
                statusList.add(new SelectItem(vtr.get(1).toString(), vtr.get(1).toString()));
            }
            /*Setting category list*/
            result = certRemote.getAllRefRecNoData("222");
            if (result.isEmpty()) {
                this.setMessage("There should be data into Cbs Ref Rec Type for 222.");
                return;
            }
            for (int i = 0; i < result.size(); i++) {
                Vector vtr = (Vector) result.get(i);
                categoryList.add(new SelectItem(vtr.get(1).toString(), vtr.get(1).toString()));
            }
            /*Setting rbi rating list*/
            result = certRemote.getAllRefRecNoData("223");
            if (result.isEmpty()) {
                this.setMessage("There should be data into Cbs Ref Rec Type for 223.");
                return;
            }
            for (int i = 0; i < result.size(); i++) {
                Vector vtr = (Vector) result.get(i);
                rbiRatingList.add(new SelectItem(vtr.get(1).toString(), vtr.get(1).toString()));
            }
            /*Setting sub category list*/
            result = certRemote.getAllRefRecNoData("224");
            if (result.isEmpty()) {
                this.setMessage("There should be data into Cbs Ref Rec Type for 224.");
                return;
            }
            for (int i = 0; i < result.size(); i++) {
                Vector vtr = (Vector) result.get(i);
                subCategoryList.add(new SelectItem(vtr.get(1).toString(), vtr.get(1).toString()));
            }
            /*Setting bank region list*/
            result = certRemote.getAllRefRecNoData("225");
            if (result.isEmpty()) {
                this.setMessage("There should be data into Cbs Ref Rec Type for 225.");
                return;
            }
            for (int i = 0; i < result.size(); i++) {
                Vector vtr = (Vector) result.get(i);
                bankRegionList.add(new SelectItem(vtr.get(1).toString(), vtr.get(1).toString()));
            }
            btnRefreshAction();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void functionProcess() {
        this.setMessage("");
        try {
            clearField();
            if (this.function.equals("A")) {
                this.btnValue = "Save";
            } else if (this.function.equals("M")) {
                this.btnValue = "Modify";
                /*Get data to modify and set on the form*/
                List list = certRemote.getBankProfile(Integer.parseInt(getOrgnBrCode()));
                if (list.isEmpty()) {
                    this.setMessage("There is no data to modify.");
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    Vector element = (Vector) list.get(i);
                    this.bankName = element.get(0) == null ? "" : element.get(0).toString();
                    this.regOfficeAddress = element.get(1) == null ? "" : element.get(1).toString();
                    this.regOfficePin = element.get(2) == null ? "" : element.get(2).toString();
                    this.hoOfficeAddress = element.get(3) == null ? "" : element.get(3).toString();
                    this.hoOfficePin = element.get(4) == null ? "" : element.get(4).toString();
                    this.status = element.get(5) == null ? "" : element.get(5).toString();
                    this.category = element.get(6) == null ? "" : element.get(6).toString();
                    this.rbiRating = element.get(7) == null ? "" : element.get(7).toString();
                    this.subCategory = element.get(8) == null ? "" : element.get(8).toString();
                    this.bankRegion = element.get(9) == null ? "" : element.get(9).toString();
                    this.licenceDate = element.get(10) == null ? "" : element.get(10).toString();
                    this.licenceNo = element.get(11) == null ? "" : element.get(11).toString();
                    this.lastInspectionDate = element.get(12).toString();
                    this.agmDate = element.get(13).toString();
                    this.totRegularMembers = element.get(14) == null ? "0" : element.get(14).toString();
                    this.totNominalMembers = element.get(15) == null ? "0" : element.get(15).toString();
                    this.internalAuditDate = element.get(16) == null ? "" : element.get(16).toString();
                    this.majorIrRegNo = element.get(17) == null ? "0.00" : element.get(17).toString();
                    this.minorIrRegNo = element.get(18) == null ? "0.00" : element.get(18).toString();
                    this.parasFullyNo = element.get(19) == null ? "0.00" : element.get(19).toString();
                    this.parasOutstandNo = element.get(20) == null ? "0.00" : element.get(20).toString();
                }
                this.setMessage("Now you can modify the entries.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void alertMsgAction() {
        this.setMessage("");
        this.setConfirmationMessage("");
        try {
            if (this.function.equals("A")) {
                this.confirmationMessage = "Are you sure to save the entry ?";
            } else if (this.function.equals("M")) {
                this.confirmationMessage = "Are you sure to modify the entry ?";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnSaveAction() {
        this.setMessage("");
        try {
            String val = validateField();
            if (!val.equals("true")) {
                this.setMessage(val);
                return;
            }
            String result = certRemote.saveAndUpdateBankProfile(bankName, regOfficeAddress, regOfficePin, hoOfficeAddress,
                    hoOfficePin, status, category, rbiRating, subCategory, bankRegion, ymd.format(dmy.parse(licenceDate)),
                    licenceNo, lastInspectionDate, agmDate, new BigInteger(totRegularMembers), new BigInteger(totNominalMembers),
                    ymd.format(dmy.parse(internalAuditDate)), new BigDecimal(majorIrRegNo), new BigDecimal(minorIrRegNo),
                    new BigDecimal(parasFullyNo), new BigDecimal(parasOutstandNo), Integer.parseInt(getOrgnBrCode()),
                    getUserName(), getTodayDate(), function);
            if (!result.equals("true")) {
                this.setMessage(result);
                return;
            }
            clearField();
            if (this.function.equals("A")) {
                this.setMessage("Data has been saved successfully.");
            } else if (this.function.equals("M")) {
                this.setMessage("Data has been modified successfully.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String validateField() {
        this.setMessage("");
        try {
            if (this.bankName == null || this.bankName.equals("")) {
                return "Please fill Bank Name.";
            } else if (this.regOfficeAddress == null || this.regOfficeAddress.equals("")) {
                return "Please fill Registered Office Address.";
            } else if (this.regOfficePin == null || this.regOfficePin.equals("")) {
                return "Please fill Registered Office Pin.";
            } else if (this.hoOfficeAddress == null || this.hoOfficeAddress.equals("")) {
                return "Please fill HO Office Address.";
            } else if (this.hoOfficePin == null || this.hoOfficePin.equals("")) {
                return "Please fill HO Office Pin.";
            } else if (this.licenceDate == null || this.licenceDate.equals("")) {
                return "Please fill Licence Date.";
            } else if (this.licenceNo == null || this.licenceNo.equals("")) {
                return "Please fill Licence No.";
            } else if (this.lastInspectionDate == null || this.lastInspectionDate.equals("")) {
                return "Please fill Last Inspection Date.";
            } else if (this.agmDate == null || this.agmDate.equals("")) {
                return "Please fill Last AGM Date.";
            } else if (this.totRegularMembers == null || this.totRegularMembers.equals("")) {
                return "Please fill Total Regular Members.";
            } else if (this.totNominalMembers == null || this.totNominalMembers.equals("")) {
                return "Please fill Total Nominal Members.";
            } else if (this.majorIrRegNo == null || this.majorIrRegNo.equals("")) {
                return "Please fill Major Irregular No.";
            } else if (this.minorIrRegNo == null || this.minorIrRegNo.equals("")) {
                return "Please fill Minor Irregular No.";
            } else if (this.parasFullyNo == null || this.parasFullyNo.equals("")) {
                return "Please fill Paras Fully No.";
            } else if (this.parasOutstandNo == null || this.parasOutstandNo.equals("")) {
                return "Please fill Paras Outstanding No.";
            }
            Matcher m = numericPattern.matcher(this.regOfficePin);
            if (!m.matches()) {
                return "Only numeric values are alowed for Registered Office Pin.";
            }
            m = numericPattern.matcher(this.hoOfficePin);
            if (!m.matches()) {
                return "Only numeric values are alowed for HO Office Pin.";
            }
            m = numericPattern.matcher(this.totRegularMembers);
            if (!m.matches()) {
                return "Only numeric values are alowed for Total Regular Members.";
            }
            m = numericPattern.matcher(this.totNominalMembers);
            if (!m.matches()) {
                return "Only numeric values are alowed for Total Nominal Members.";
            }
            m = amtPattern.matcher(this.majorIrRegNo);
            if (!m.matches()) {
                return "There should be an amount like value for Major Irregular No.";
            }
            m = amtPattern.matcher(this.minorIrRegNo);
            if (!m.matches()) {
                return "There should be an amount like value for Minor Irregular No.";
            }
            m = amtPattern.matcher(this.parasFullyNo);
            if (!m.matches()) {
                return "There should be an amount like value for Paras Fully No.";
            }
            m = amtPattern.matcher(this.parasOutstandNo);
            if (!m.matches()) {
                return "There should be an amount like value for Paras Outstanding No.";
            }
            if (this.majorIrRegNo.contains(".")) {
                if (this.majorIrRegNo.indexOf(".") != this.majorIrRegNo.lastIndexOf(".")) {
                    return "Invalid Major Irregular No. Please Fill Upto Two Decimal.";
                } else if (this.majorIrRegNo.substring(majorIrRegNo.indexOf(".") + 1).length() > 2) {
                    return "Invalid Major Irregular No. Please Fill Upto Two Decimal.";
                }
            }
            if (this.minorIrRegNo.contains(".")) {
                if (this.minorIrRegNo.indexOf(".") != this.minorIrRegNo.lastIndexOf(".")) {
                    return "Invalid Minor Irregular No. Please Fill Upto Two Decimal.";
                } else if (this.minorIrRegNo.substring(minorIrRegNo.indexOf(".") + 1).length() > 2) {
                    return "Invalid Minor Irregular No. Please Fill Upto Two Decimal.";
                }
            }
            if (this.parasFullyNo.contains(".")) {
                if (this.parasFullyNo.indexOf(".") != this.parasFullyNo.lastIndexOf(".")) {
                    return "Invalid Paras Fully No. Please Fill Upto Two Decimal.";
                } else if (this.parasFullyNo.substring(parasFullyNo.indexOf(".") + 1).length() > 2) {
                    return "Invalid Paras Fully No. Please Fill Upto Two Decimal.";
                }
            }
            if (this.parasOutstandNo.contains(".")) {
                if (this.parasOutstandNo.indexOf(".") != this.parasOutstandNo.lastIndexOf(".")) {
                    return "Invalid Paras Outstanding No. Please Fill Upto Two Decimal.";
                } else if (this.parasOutstandNo.substring(parasOutstandNo.indexOf(".") + 1).length() > 2) {
                    return "Invalid Paras Outstanding No. Please Fill Upto Two Decimal.";
                }
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.licenceDate)) {
                return "Please fill proper Licence Date.";
            }
            if (dmy.parse(this.licenceDate).compareTo(dmy.parse(getTodayDate())) > 0) {
                return "Licence date should not be greater than current date";
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.lastInspectionDate)) {
                return "Please fill proper Last Inspection Date.";
            }
            if (dmy.parse(this.lastInspectionDate).compareTo(dmy.parse(getTodayDate())) > 0) {
                return "Last Inspection date should not be greater than current date";
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.agmDate)) {
                return "Please fill proper Last AGM Date.";
            }
            if (dmy.parse(this.agmDate).compareTo(dmy.parse(getTodayDate())) > 0) {
                return "Last AGM date should not be greater than current date";
            }
            if (!(this.internalAuditDate == null || this.internalAuditDate.equals(""))) {
                if (!new Validator().validateDate_dd_mm_yyyy(this.internalAuditDate)) {
                    return "Please fill proper Last Int.Audit Date.";
                }
                if (dmy.parse(this.internalAuditDate).compareTo(dmy.parse(getTodayDate())) > 0) {
                    return "Last Int.Audit date should not be greater than current date";
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return "true";
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setFunction("M");
        this.setBankName("");
        this.setRegOfficeAddress("");
        this.setRegOfficePin("");
        this.setHoOfficeAddress("");
        this.setHoOfficePin("");
        this.setLicenceDate("");
        this.setLicenceNo("");
        this.setLastInspectionDate("");
        this.setAgmDate("");
        this.setTotRegularMembers("");
        this.setTotNominalMembers("");
        this.setInternalAuditDate("");
        this.setMajorIrRegNo("");
        this.setMinorIrRegNo("");
        this.setParasFullyNo("");
        this.setParasOutstandNo("");
        this.btnValue = "Modify";
    }

    public void clearField() {
        this.setMessage("");
        this.setBankName("");
        this.setRegOfficeAddress("");
        this.setRegOfficePin("");
        this.setHoOfficeAddress("");
        this.setHoOfficePin("");
        this.setLicenceDate("");
        this.setLicenceNo("");
        this.setLastInspectionDate("");
        this.setAgmDate("");
        this.setTotRegularMembers("");
        this.setTotNominalMembers("");
        this.setInternalAuditDate("");
        this.setMajorIrRegNo("");
        this.setMinorIrRegNo("");
        this.setParasFullyNo("");
        this.setParasOutstandNo("");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    /*Getter And Setter*/
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getRegOfficeAddress() {
        return regOfficeAddress;
    }

    public void setRegOfficeAddress(String regOfficeAddress) {
        this.regOfficeAddress = regOfficeAddress;
    }

    public String getRegOfficePin() {
        return regOfficePin;
    }

    public void setRegOfficePin(String regOfficePin) {
        this.regOfficePin = regOfficePin;
    }

    public String getHoOfficeAddress() {
        return hoOfficeAddress;
    }

    public void setHoOfficeAddress(String hoOfficeAddress) {
        this.hoOfficeAddress = hoOfficeAddress;
    }

    public String getHoOfficePin() {
        return hoOfficePin;
    }

    public void setHoOfficePin(String hoOfficePin) {
        this.hoOfficePin = hoOfficePin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public List<SelectItem> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<SelectItem> categoryList) {
        this.categoryList = categoryList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRbiRating() {
        return rbiRating;
    }

    public void setRbiRating(String rbiRating) {
        this.rbiRating = rbiRating;
    }

    public List<SelectItem> getRbiRatingList() {
        return rbiRatingList;
    }

    public void setRbiRatingList(List<SelectItem> rbiRatingList) {
        this.rbiRatingList = rbiRatingList;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public List<SelectItem> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SelectItem> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public String getLicenceDate() {
        return licenceDate;
    }

    public void setLicenceDate(String licenceDate) {
        this.licenceDate = licenceDate;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getLastInspectionDate() {
        return lastInspectionDate;
    }

    public void setLastInspectionDate(String lastInspectionDate) {
        this.lastInspectionDate = lastInspectionDate;
    }

    public String getAgmDate() {
        return agmDate;
    }

    public void setAgmDate(String agmDate) {
        this.agmDate = agmDate;
    }

    public String getTotRegularMembers() {
        return totRegularMembers;
    }

    public void setTotRegularMembers(String totRegularMembers) {
        this.totRegularMembers = totRegularMembers;
    }

    public String getTotNominalMembers() {
        return totNominalMembers;
    }

    public void setTotNominalMembers(String totNominalMembers) {
        this.totNominalMembers = totNominalMembers;
    }

    public String getInternalAuditDate() {
        return internalAuditDate;
    }

    public void setInternalAuditDate(String internalAuditDate) {
        this.internalAuditDate = internalAuditDate;
    }

    public String getMajorIrRegNo() {
        return majorIrRegNo;
    }

    public void setMajorIrRegNo(String majorIrRegNo) {
        this.majorIrRegNo = majorIrRegNo;
    }

    public String getMinorIrRegNo() {
        return minorIrRegNo;
    }

    public void setMinorIrRegNo(String minorIrRegNo) {
        this.minorIrRegNo = minorIrRegNo;
    }

    public String getParasFullyNo() {
        return parasFullyNo;
    }

    public void setParasFullyNo(String parasFullyNo) {
        this.parasFullyNo = parasFullyNo;
    }

    public String getParasOutstandNo() {
        return parasOutstandNo;
    }

    public void setParasOutstandNo(String parasOutstandNo) {
        this.parasOutstandNo = parasOutstandNo;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public void setConfirmationMessage(String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
    }

    public String getBankRegion() {
        return bankRegion;
    }

    public void setBankRegion(String bankRegion) {
        this.bankRegion = bankRegion;
    }

    public List<SelectItem> getBankRegionList() {
        return bankRegionList;
    }

    public void setBankRegionList(List<SelectItem> bankRegionList) {
        this.bankRegionList = bankRegionList;
    }
}
