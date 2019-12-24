/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ckycr;

import com.cbs.dto.ckycr.CKYCRRequestPojo;
import com.cbs.facade.ckycr.CkycrViewMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class CKYCRRequestController extends BaseBean {

    private String successMsg;
    private String errorMsg;
    private String msgStyle; // pass value for Susccess = "msg" Or error = "error"
    private String mode;
    private String type;
    private String customerIdOrCKYCRNo;
    private String dateOfBirth;
    private List<SelectItem> modeList;
    private List<SelectItem> typeList;
    private boolean modeDisable;
    private boolean typeDisable;
    private boolean customerIdDisable;
    private boolean dateOfBirthDisable;
    private boolean saveDisable;
    private boolean uploadDisable;
    private boolean downloadDisable;
    private boolean checkBoxAll;
    private boolean gridCheckBoxDisable;
    private boolean checkBoxAllDisable;
    private String ckycrCustIdLebal;
    private List<CKYCRRequestPojo> customerDetailList;
    private CkycrViewMgmtFacadeRemote ckycrRemote;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomerIdOrCKYCRNo() {
        return customerIdOrCKYCRNo;
    }

    public void setCustomerIdOrCKYCRNo(String customerIdOrCKYCRNo) {
        this.customerIdOrCKYCRNo = customerIdOrCKYCRNo;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    public boolean isModeDisable() {
        return modeDisable;
    }

    public void setModeDisable(boolean modeDisable) {
        this.modeDisable = modeDisable;
    }

    public boolean isTypeDisable() {
        return typeDisable;
    }

    public void setTypeDisable(boolean typeDisable) {
        this.typeDisable = typeDisable;
    }

    public boolean isCustomerIdDisable() {
        return customerIdDisable;
    }

    public void setCustomerIdDisable(boolean customerIdDisable) {
        this.customerIdDisable = customerIdDisable;
    }

    public boolean isDateOfBirthDisable() {
        return dateOfBirthDisable;
    }

    public void setDateOfBirthDisable(boolean dateOfBirthDisable) {
        this.dateOfBirthDisable = dateOfBirthDisable;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public boolean isUploadDisable() {
        return uploadDisable;
    }

    public void setUploadDisable(boolean uploadDisable) {
        this.uploadDisable = uploadDisable;
    }

    public boolean isDownloadDisable() {
        return downloadDisable;
    }

    public void setDownloadDisable(boolean downloadDisable) {
        this.downloadDisable = downloadDisable;
    }

    public List<CKYCRRequestPojo> getCustomerDetailList() {
        return customerDetailList;
    }

    public void setCustomerDetailList(List<CKYCRRequestPojo> customerDetailList) {
        this.customerDetailList = customerDetailList;
    }

    public boolean isCheckBoxAll() {
        return checkBoxAll;
    }

    public void setCheckBoxAll(boolean checkBoxAll) {
        this.checkBoxAll = checkBoxAll;
    }

    public boolean isGridCheckBoxDisable() {
        return gridCheckBoxDisable;
    }

    public void setGridCheckBoxDisable(boolean gridCheckBoxDisable) {
        this.gridCheckBoxDisable = gridCheckBoxDisable;
    }

    public boolean isCheckBoxAllDisable() {
        return checkBoxAllDisable;
    }

    public void setCheckBoxAllDisable(boolean checkBoxAllDisable) {
        this.checkBoxAllDisable = checkBoxAllDisable;
    }

    public String getCkycrCustIdLebal() {
        return ckycrCustIdLebal;
    }

    public void setCkycrCustIdLebal(String ckycrCustIdLebal) {
        this.ckycrCustIdLebal = ckycrCustIdLebal;
    }

    public String getMsgStyle() {
        return msgStyle;
    }

    public void setMsgStyle(String msgStyle) {
        this.msgStyle = msgStyle;
    }

    public CKYCRRequestController() {
        try {
            this.saveDisable = true;
            this.downloadDisable = true;
            this.uploadDisable = true;
            this.checkBoxAllDisable = true;
            this.ckycrCustIdLebal ="Customer Id/CKYCR No.  ";
            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("SELECT", "--Select--"));
            modeList.add(new SelectItem("UPLOAD", "UPLOAD"));
            modeList.add(new SelectItem("DOWNLOAD", "DOWNLOAD"));

            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("SELECT", "--Select--"));
            typeList.add(new SelectItem("INDIVIDUAL", "INDIVIDUAL"));
            typeList.add(new SelectItem("BULK", "BULK"));

            ckycrRemote = (CkycrViewMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CkycrViewMgmtFacade");
        } catch (Exception ex) {
            this.msgStyle="error";
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void setDisableTrue() {
        this.customerDetailList = null;
        this.saveDisable = true;
        if (this.mode.equalsIgnoreCase("UPLOAD")) {
            this.dateOfBirthDisable = true;
            typeList = null;
            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("SELECT", "--Select--"));
            typeList.add(new SelectItem("INDIVIDUAL", "INDIVIDUAL"));
            typeList.add(new SelectItem("BULK", "BULK"));
            this.ckycrCustIdLebal ="Customer Id";
        }
        if (this.mode.equalsIgnoreCase("UPLOAD") && this.type.equalsIgnoreCase("BULK")) {
            this.customerIdOrCKYCRNo = "";
            this.customerIdDisable = true;
            this.dateOfBirthDisable = true;
            this.uploadDisable = false;
            this.downloadDisable = true;
        }
        if (this.mode.equalsIgnoreCase("UPLOAD") && this.type.equalsIgnoreCase("INDIVIDUAL")) {
            this.customerIdDisable = false;
            this.dateOfBirthDisable = true;
        }
        if (this.mode.equalsIgnoreCase("UPLOAD") && this.type.equalsIgnoreCase("INDIVIDUAL") && !"".equals(this.customerIdOrCKYCRNo)) {
            this.uploadDisable = false;
            this.downloadDisable = true;
        }
        if (this.mode.equalsIgnoreCase("DOWNLOAD") && this.type.equalsIgnoreCase("INDIVIDUAL") && !"".equals(this.customerIdOrCKYCRNo) && !"".equals(this.dateOfBirth)) {
            this.uploadDisable = true;
            this.saveDisable = true;
            this.downloadDisable = false;
            this.modeDisable = true;
            this.typeDisable = true;
            return;
        }
        if (this.mode.equalsIgnoreCase("DOWNLOAD")) {
            this.customerIdDisable = false;
            this.dateOfBirth = "";
            this.dateOfBirthDisable = false;
            //typeList = null;
            //typeList = new ArrayList<SelectItem>();
            //typeList.add(new SelectItem("SELECT", "--Select--"));
            //typeList.add(new SelectItem("INDIVIDUAL", "INDIVIDUAL"));
            this.type = "INDIVIDUAL";
            this.typeDisable =true;
            this.ckycrCustIdLebal ="CKYCR No.";
        }
    }

    public void getCustomerDetail() {
        this.setErrorMsg("");
        Pattern pattern = Pattern.compile("\\d+");
        if(this.mode.equalsIgnoreCase("UPLOAD")){
            this.dateOfBirthDisable = true;
        }
        if (this.mode.equalsIgnoreCase("UPLOAD") && this.type.equalsIgnoreCase("BULK")) {
            this.customerIdOrCKYCRNo = "";
            this.customerIdDisable = true;
            this.dateOfBirthDisable = true;
            this.uploadDisable = false;
            this.downloadDisable = true;
        }
        if ((this.mode.equalsIgnoreCase("UPLOAD") && this.type.equalsIgnoreCase("INDIVIDUAL")) &&  this.customerIdOrCKYCRNo == "") {
            this.customerIdOrCKYCRNo = "";
            this.customerIdDisable = false;
            this.dateOfBirthDisable = true;
            this.uploadDisable = true;
            this.downloadDisable = true;
            return;
        }
        
        try {
            
            
            if (this.mode.equalsIgnoreCase("UPLOAD") && this.type.equalsIgnoreCase("INDIVIDUAL")) {
                if (!pattern.matcher(customerIdOrCKYCRNo).matches()) {
                    this.msgStyle="error";
                    this.setErrorMsg("Customer Id can only numeric value !");
                    return;
                }
                if (!"".equals(this.customerIdOrCKYCRNo)) {
                    this.setErrorMsg("");
                    this.customerDetailList = null;
                    customerDetailList = ckycrRemote.getCbsCustomerMasterDetailIndividual(customerIdOrCKYCRNo,this.getOrgnBrCode());
                    this.modeDisable = true;
                    this.typeDisable = true;
                    this.customerIdDisable = true;
                    this.uploadDisable = false;
                    this.setCheckBoxAll(true);
                    this.gridCheckBoxDisable = true;
                } else {
                    this.msgStyle="error";
                    this.setErrorMsg("Please enter Customer Id/CKYCR No.");
                }
            }
            if (this.mode.equalsIgnoreCase("UPLOAD") && this.type.equalsIgnoreCase("BULK")) {
                this.setErrorMsg("");
                this.customerDetailList = null;
                customerDetailList = ckycrRemote.getCbsCustomerMasterDetailBulk(this.getOrgnBrCode());
                    this.modeDisable = true;
                    this.typeDisable = true;
                    this.uploadDisable = false;
                    this.setCheckBoxAll(true);
                    checkAll();
                    this.checkBoxAllDisable = false;
            }
            if (this.mode.equalsIgnoreCase("UPLOAD") && this.type.equalsIgnoreCase("SELECT")) {
                this.msgStyle="error";
                this.setErrorMsg("Please select Type !");
            }
            if (this.mode.equalsIgnoreCase("SELECT") && (this.type.equalsIgnoreCase("BULK") || this.type.equalsIgnoreCase("INDIVIDUAL"))) {
                this.msgStyle="error";
                this.setErrorMsg("Please select Mode !");
            }

        } catch (Exception ex) {
            this.uploadDisable = true;
            this.msgStyle="error";
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void saveCKYCR() {
        try {
            String fetchMode = "Form";
            //--Checking parameterinfo_report (CKYCR-MANUAL)
            int parameter = ckycrRemote.checkPrameterInfoReport("CKYCR-MANUAL");
            //----
            if (parameter != 1) {
                int flagCount = ckycrRemote.insertCKYCRRequestDetails(customerDetailList, mode, type, this.getUserName(), this.checkBoxAll, fetchMode);
                if (flagCount > 0) {
                    refreshForm();
                    this.msgStyle = "msg";
                    this.setErrorMsg(" CKYCR Upload Request completed.");
                }
            }else{
                    this.msgStyle = "error";
                    this.setErrorMsg("This proccess is not allowed manually ! It's handle by Scheduler.");
            }
            
        } catch (Exception ex) {
            this.msgStyle="error";
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void download() {
        Pattern pattern = Pattern.compile("\\d+");
        try {
            if (!pattern.matcher(customerIdOrCKYCRNo).matches()) {
                this.msgStyle="error";
                this.setErrorMsg("Customer Id can only numeric value !");
                return;
            }
            if (this.mode.equalsIgnoreCase("DOWNLOAD") && this.type.equalsIgnoreCase("INDIVIDUAL") && !"".equals(this.customerIdOrCKYCRNo) && !"".equals(this.dateOfBirth)) {
                String dob = ymdFormat.format(dmyFormat.parse(dateOfBirth));
                String fetchMode = "Form";
                int result = ckycrRemote.downloadCbsCustomerMasterDetail(mode, type, customerIdOrCKYCRNo, dob, this.getOrgnBrCode(), this.getUserName(),fetchMode);
                if (result > 0) {
                    refreshForm();
                    this.msgStyle="msg";
                    this.setErrorMsg(" CKYCR Download Request completed.");
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }

    }
    /* ---Checked or Unchecked the Grid row--- */
    public void checkAll() {
        if (this.checkBoxAll == true) {
            for (int i = 0; i < customerDetailList.size(); i++) {
                customerDetailList.get(i).setCheckBox(true);
            }
            this.gridCheckBoxDisable = true;
        } else {
            this.gridCheckBoxDisable = false;
        }
    }

    public void checkNotAll() {
        // This method is required for grid check box. 
        // It is using by CKYCRBranchRequest.jsp, in grid.
    }

    public void refreshForm() {
        this.errorMsg = "";
        this.mode = "SELECT";
        this.dateOfBirth = "";
        this.customerIdOrCKYCRNo = "";
        this.dateOfBirthDisable = false;
        this.customerIdDisable = false;
        typeList = new ArrayList<SelectItem>();
        typeList.add(new SelectItem("SELECT", "--Select--"));
        typeList.add(new SelectItem("INDIVIDUAL", "INDIVIDUAL"));
        typeList.add(new SelectItem("BULK", "BULK"));
        this.type = "SELECT";
        this.customerDetailList = null;
        this.uploadDisable = true;
        this.saveDisable = true;
        this.downloadDisable = true;
        this.modeDisable = false;
        this.typeDisable = false;
        this.customerIdDisable = false;
        this.checkBoxAll = false;
        this.checkBoxAllDisable = true;
        this.ckycrCustIdLebal ="Customer Id/CKYCR No.  ";
    }

    public void clear() {
//        this.setSuccessMsg("");
//        this.errorMsg = "";
//        this.mode = "SELECT";
//        this.dateOfBirth = "";
//        this.customerIdOrCKYCRNo = "";
//        this.dateOfBirthDisable = false;
//        this.customerIdDisable = false;
//        typeList = new ArrayList<SelectItem>();
//        typeList.add(new SelectItem("SELECT", "--Select--"));
//        typeList.add(new SelectItem("INDIVIDUAL", "INDIVIDUAL"));
//        typeList.add(new SelectItem("BULK", "BULK"));
//        this.type = "SELECT";
//        this.customerDetailList = null;
    }

    public String exitForm() {
        return "case1";
    }
}
