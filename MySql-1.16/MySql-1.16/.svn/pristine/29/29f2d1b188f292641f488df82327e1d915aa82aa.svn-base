/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ckycr;

import com.cbs.dto.ckycr.CKYCRRequestPojo;
import com.cbs.facade.ckycr.CkycrProcessMgmtFacadeRemote;
import com.cbs.facade.ckycr.CkycrViewMgmtFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

public class ManualRequestController extends BaseBean {

    private String msg;
    private String mode;
    private String type;
    private String ckycrCustIdLebal;
    private String customerIdOrCKYCRNo;
    private String dateOfBirth;
    private String fromDate;
    private String toDate;
    private String buttonLebal;
    //Visibility
    private String custIdRowVisible;
    private String dobVisible;
    private String visibleDtField;
    private String visibleLeftAllCheckBox;
    private String awaitingGridDisplay;
    private String genUploadGridDisplay;
    private String genDownloadGridDisplay;
    private String allGridDisplay;
    //Boolean
    private boolean typeDisable;
    private boolean checkBoxAll;
    private boolean checkBoxAllDisable;
    private boolean gridCheckBoxDisable;
    //List
    private CKYCRRequestPojo currentItem;
    private List<SelectItem> modeList;
    private List<SelectItem> typeList;
    private List<CKYCRRequestPojo> customerDetailList;
    private List<CKYCRRequestPojo> downloadDetailList;
    private List<CKYCRRequestPojo> genUploadDataList;
    private List<CKYCRRequestPojo> genDownloadDataList;
    //Injection && Others
    private CkycrViewMgmtFacadeRemote ckycrRemote;
    private CkycrProcessMgmtFacadeRemote ckycrProcessMgmtRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private CommonReportMethodsRemote commonReportRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private Date date = new Date();
    Properties props = null;

    public ManualRequestController() {
        try {
            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("0", "--Select--"));
            modeList.add(new SelectItem("UPLOAD", "UPLOAD"));
            modeList.add(new SelectItem("GUF", "GENERATE UPLOAD FILE"));
            modeList.add(new SelectItem("SUF", "SHOW UPLOAD FILE"));
            modeList.add(new SelectItem("DOWNLOAD", "DOWNLOAD"));
            modeList.add(new SelectItem("GDF", "GENERATE DOWNLOAD FILE"));
            modeList.add(new SelectItem("SDF", "SHOW DOWNLOAD FILE"));

            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("0", "--Select--"));
            typeList.add(new SelectItem("INDIVIDUAL", "INDIVIDUAL"));
            typeList.add(new SelectItem("BULK", "BULK"));

            ckycrRemote = (CkycrViewMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CkycrViewMgmtFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            ckycrProcessMgmtRemote = (CkycrProcessMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CkycrProcessMgmtFacade");
            commonReportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            props = new Properties();
            props.load(new FileReader("/opt/conf/wslocation.properties"));
            refreshForm();
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void modeFieldAction() {
        this.setMsg("");
        try {
            if (this.mode == null || this.mode.equals("0") || this.mode.equals("")) {
                this.setMsg("Please select mode.");
                return;
            }
            modeTimeRefreshForm();
            if (this.mode.equalsIgnoreCase("upload")) {
                this.setCkycrCustIdLebal("Customer Id");
                this.custIdRowVisible = "";
                this.setButtonLebal("Upload");
            } else if (this.mode.equalsIgnoreCase("download")) {
                this.setType("INDIVIDUAL");
                this.setTypeDisable(true);
                this.setCkycrCustIdLebal("CKYC No.");
                this.custIdRowVisible = "";
                this.dobVisible = "";
                this.setButtonLebal("Download");
            } else if (this.mode.equalsIgnoreCase("guf")) {
                this.setTypeDisable(true);
                this.visibleDtField = "";
                this.genUploadGridDisplay = "";
                this.setButtonLebal("Generate Upload File");
            } else if (this.mode.equalsIgnoreCase("suf")) {
                this.setTypeDisable(true);
                this.visibleDtField = "";
                this.allGridDisplay = "";
                this.setButtonLebal("Show Upload File");
            } else if (this.mode.equalsIgnoreCase("gdf")) {
                this.setTypeDisable(true);
                this.visibleDtField = "";
                this.genDownloadGridDisplay = "";
                this.setButtonLebal("Generate Download File");
            } else if (this.mode.equalsIgnoreCase("sdf")) {
                this.setTypeDisable(true);
                this.visibleDtField = "";
                this.allGridDisplay = "";
                this.setButtonLebal("Show Download File");
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void typeFieldAction() {
        this.setMsg("");
        try {
            if (this.mode == null || this.mode.equals("0") || this.mode.equals("")) {
                this.setMsg("Please select mode.");
                return;
            }
            if (this.type == null || this.type.equals("0") || this.type.equals("")) {
                this.setMsg("Please select type.");
                return;
            }
            if (this.mode.equalsIgnoreCase("upload") && this.type.equalsIgnoreCase("bulk")) {
                this.custIdRowVisible = "none";
                customerDetailList = new ArrayList<CKYCRRequestPojo>();
                customerDetailList = ckycrRemote.getCbsCustomerMasterDetailBulk(this.getOrgnBrCode());
                this.visibleLeftAllCheckBox = "";
                this.setCheckBoxAll(true);
                checkAll();
                this.checkBoxAllDisable = false;
                this.awaitingGridDisplay = "";
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void checkAll() {
        if (this.checkBoxAll == true) {
            if (mode.equalsIgnoreCase("GUF")) {
                for (int i = 0; i < genUploadDataList.size(); i++) {
                    genUploadDataList.get(i).setCheckBox(true);
                }
            } else if (this.mode.equalsIgnoreCase("GDF")) {
                for (int i = 0; i < genDownloadDataList.size(); i++) {
                    genDownloadDataList.get(i).setCheckBox(true);
                }
            } else {
                for (int i = 0; i < customerDetailList.size(); i++) {
                    customerDetailList.get(i).setCheckBox(true);
                }
            }
            this.gridCheckBoxDisable = true;
        } else {
            this.gridCheckBoxDisable = false;
        }
    }

    public void customerCkycNoAction() {
        this.setMsg("");
        try {
            if (this.mode == null || this.mode.equals("0") || this.mode.equals("")) {
                this.setMsg("Please select mode.");
                return;
            }
            if (this.type == null || this.type.equals("0") || this.type.equals("")) {
                this.setMsg("Please select type.");
                return;
            }
            if (this.customerIdOrCKYCRNo == null || this.customerIdOrCKYCRNo.equals("")) {
                this.setMsg("Please fill Customer Id/Ckyc No.");
                return;
            }
            Pattern pattern = Pattern.compile("\\d+");
            if (!pattern.matcher(customerIdOrCKYCRNo).matches()) {
                this.setMsg("Customer Id/Ckyc No can only be numeric.");
                return;
            }
            if (this.mode.equalsIgnoreCase("upload")) {
                if (this.customerIdOrCKYCRNo.trim().length() > 10) {
                    this.setMsg("Customer Id can not be greater than 10 digit.");
                    return;
                }
                customerDetailList = new ArrayList<CKYCRRequestPojo>();
                customerDetailList = ckycrRemote.getCbsCustomerMasterDetailIndividual(customerIdOrCKYCRNo, this.getOrgnBrCode());
                this.setCheckBoxAll(true);
                this.gridCheckBoxDisable = true;
                this.awaitingGridDisplay = "";
            } else if (this.mode.equalsIgnoreCase("download")) {
                if (this.customerIdOrCKYCRNo.trim().length() != 14) {
                    this.setMsg("Ckyc No can not be less than 14 digit.");
                }
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void dobFieldAction() {
        this.setMsg("");
        try {
            if (this.mode == null || this.mode.equals("0") || this.mode.equals("")) {
                this.setMsg("Please select mode.");
                return;
            }
            if (this.type == null || this.type.equals("0") || this.type.equals("")) {
                this.setMsg("Please select type.");
                return;
            }
            if (this.customerIdOrCKYCRNo == null || this.customerIdOrCKYCRNo.equals("")) {
                this.setMsg("Please fill Customer Id/Ckyc No.");
                return;
            }
            if (this.dateOfBirth == null || this.dateOfBirth.equals("")) {
                this.setMsg("Please fill date of birth.");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.dateOfBirth)) {
                this.setMsg("Please fill proper date of birth.");
                return;
            }
            if (dmy.parse(this.dateOfBirth).compareTo(dmy.parse(dmy.format(new Date()))) >= 0) {
                this.setMsg("Date of birth should be less than current date.");
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void toDateFieldAction() {
        this.setMsg("");
        try {
            if (this.mode == null || this.mode.equals("0") || this.mode.equals("")) {
                this.setMsg("Please select mode.");
                return;
            }
            if (!validateFromAndToDate()) {
                return;
            }
            if (this.mode.equalsIgnoreCase("guf")) {
                genUploadDataList = new ArrayList<>();
                genUploadDataList = ckycrProcessMgmtRemote.getGenUploadData(this.getOrgnBrCode(),
                        ymd.format(dmy.parse(fromDate)), ymd.format(dmy.parse(toDate)));
            } else if (this.mode.equalsIgnoreCase("suf")) {
                this.setButtonLebal("");
                downloadDetailList = new ArrayList<>();
                downloadDetailList = ckycrRemote.generateDownloadFile("upload", ymd.format(dmy.parse(this.fromDate)),
                        ymd.format(dmy.parse(this.toDate)), getOrgnBrCode());

                this.setMsg("Now you can download files.");
            } else if (this.mode.equalsIgnoreCase("gdf")) {
                genDownloadDataList = new ArrayList<>();
                genDownloadDataList = ckycrProcessMgmtRemote.getGenDownloadManualData(getOrgnBrCode(),
                        ymd.format(dmy.parse(fromDate)), ymd.format(dmy.parse(toDate)));
            } else if (this.mode.equalsIgnoreCase("sdf")) {
                this.setButtonLebal("");
                downloadDetailList = new ArrayList<>();
                downloadDetailList = ckycrRemote.generateDownloadFile("download", ymd.format(dmy.parse(this.fromDate)),
                        ymd.format(dmy.parse(this.toDate)), getOrgnBrCode());

                this.setMsg("Now you can download files.");
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void processBtnAction() {
        this.setMsg("");
        try {
            String fetchMode = "Manual";
            Integer code = ftsRemote.getCodeForReportName("CKYCR-MANUAL");
            if (code != 1) {
                this.setMsg("Manual request is not allowed.");
                return;
            }
            //Upload
            if (this.mode.equalsIgnoreCase("upload")) {
                if (!validateUploadRequest()) {
                    return;
                }
                if (customerDetailList == null || customerDetailList.isEmpty()) {
                    this.setMsg("There is no data to upload the request.");
                    return;
                }
                int flagCount = ckycrRemote.insertCKYCRRequestDetails(customerDetailList, mode, type,
                        this.getUserName(), this.checkBoxAll, fetchMode);
                if (flagCount == 0) {
                    this.setMsg("Problem in upload request.");
                    return;
                }
                refreshForm();
                this.setMsg("Upload request has been completed.");
            }
            //Generate Upload File
            if (this.mode.equalsIgnoreCase("guf")) {
                if (!validateGenerateAndShowProcess()) {
                    return;
                }
                if (genUploadDataList == null || genUploadDataList.isEmpty()) {
                    this.setMsg("There is no data to generate the upload file.");
                    return;
                }
                List<CKYCRRequestPojo> actualRequest = new ArrayList<CKYCRRequestPojo>();
                for (CKYCRRequestPojo pojo : genUploadDataList) {
                    if (pojo.isSelected()) {
                        actualRequest.add(pojo);
                    }
                }
                if (actualRequest.isEmpty()) {
                    this.setMsg("There is no data to generate the upload file.");
                    return;
                }

                String alphaCode = commonReportRemote.getAlphacodeByBrncode(getOrgnBrCode());
                String result = ckycrProcessMgmtRemote.generateManualUploadFile(actualRequest, alphaCode);
                if (!result.equalsIgnoreCase("true")) {
                    this.setMsg("Problem in upload file generation.");
                    return;
                }
                refreshForm();
                this.setMsg("Upload file has been generated successfully.");
            }
            //Show Upload File
            if (this.mode.equalsIgnoreCase("suf")) {
                if (!validateGenerateAndShowProcess()) {
                    return;
                }
                downloadDetailList = ckycrRemote.generateDownloadFile("upload", ymd.format(dmy.parse(fromDate)),
                        ymd.format(dmy.parse(toDate)), getOrgnBrCode());
            }
            //Download
            if (this.mode.equalsIgnoreCase("download")) {
                if (!validateDownloadRequest()) {
                    return;
                }
                int result = ckycrRemote.downloadCbsCustomerMasterDetail(mode, type, customerIdOrCKYCRNo,
                        ymd.format(dmy.parse(dateOfBirth)), this.getOrgnBrCode(), this.getUserName(), fetchMode);
                if (result > 0) {
                    refreshForm();
                    this.setMsg(" CKYCR Download Request completed.");
                }
            }
            //Generate Download file
            if (this.mode.equalsIgnoreCase("gdf")) {
                if (!validateGenerateAndShowProcess()) {
                    return;
                }
                if (genDownloadDataList == null || genDownloadDataList.isEmpty()) {
                    this.setMsg("There is no data to generate the download file.");
                    return;
                }
                List<CKYCRRequestPojo> actualRequest = new ArrayList<CKYCRRequestPojo>();
                for (CKYCRRequestPojo pojo : genDownloadDataList) {
                    if (pojo.isSelected()) {
                        actualRequest.add(pojo);
                    }
                }
                if (actualRequest.isEmpty()) {
                    this.setMsg("There is no data to generate the download file.");
                    return;
                }

                String alphaCode = commonReportRemote.getAlphacodeByBrncode(getOrgnBrCode());
                ckycrProcessMgmtRemote.generateManualDownloadFile(actualRequest, alphaCode);
                refreshForm();
                this.setMsg("Download file has been generated successfully.");
            }
            //Show Download File
            if (this.mode.equalsIgnoreCase("sdf")) {
                if (!validateGenerateAndShowProcess()) {
                    return;
                }
                downloadDetailList = ckycrRemote.generateDownloadFile("download", ymd.format(dmy.parse(fromDate)),
                        ymd.format(dmy.parse(toDate)), getOrgnBrCode());
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public boolean validateGenerateAndShowProcess() {
        try {
            if (this.mode == null || this.mode.equals("0") || this.mode.equals("")) {
                this.setMsg("Please select mode.");
                return false;
            }
            return validateFromAndToDate();
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
            return false;
        }
    }

    public boolean validateFromAndToDate() {
        try {
            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMsg("Please fill from date.");
                return false;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.fromDate)) {
                this.setMsg("Please fill proper from date.");
                return false;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMsg("Please fill to date.");
                return false;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.toDate)) {
                this.setMsg("Please fill proper to date.");
                return false;
            }
            if (dmy.parse(this.fromDate).compareTo(dmy.parse(this.toDate)) > 0) {
                this.setMsg("From date can not be greater than to date.");
                return false;
            }
            if (dmy.parse(this.fromDate).compareTo(dmy.parse(dmy.format(new Date()))) > 0) {
                this.setMsg("From date can not be greater than current date.");
                return false;
            }
            if (dmy.parse(this.toDate).compareTo(dmy.parse(dmy.format(new Date()))) > 0) {
                this.setMsg("To date can not be greater than current date.");
                return false;
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean validateDownloadRequest() {
        try {
            if (this.mode == null || this.mode.equals("0") || this.mode.equals("")) {
                this.setMsg("Please select mode.");
                return false;
            }
            if (this.type == null || this.type.equals("0") || this.type.equals("")) {
                this.setMsg("Please select type.");
                return false;
            }
            if (this.customerIdOrCKYCRNo == null || this.customerIdOrCKYCRNo.equals("")) {
                this.setMsg("Please fill Ckyc No.");
                return false;
            }
            Pattern pattern = Pattern.compile("\\d+");
            if (!pattern.matcher(this.customerIdOrCKYCRNo).matches()) {
                this.setMsg("Ckycr No. can only numeric value !");
                return false;
            }
            if (this.customerIdOrCKYCRNo.length() != 14) {
                this.setMsg("Ckycr No. can not be less than 14 digits !");
                return false;
            }
            if (this.dateOfBirth == null || this.dateOfBirth.equals("")) {
                this.setMsg("Please fill date of birth.");
                return false;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.dateOfBirth)) {
                this.setMsg("Please fill proper date of birth.");
                return false;
            }
            if (dmy.parse(this.dateOfBirth).compareTo(dmy.parse(dmy.format(new Date()))) >= 0) {
                this.setMsg("Date of birth should be less than current date.");
                return false;
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean validateUploadRequest() {
        try {
            if (this.mode == null || this.mode.equals("0") || this.mode.equals("")) {
                this.setMsg("Please select mode.");
                return false;
            }
            if (this.type == null || this.type.equals("0") || this.type.equals("")) {
                this.setMsg("Please select type.");
                return false;
            }
            if (this.type.equalsIgnoreCase("Individual")) {
                if (this.customerIdOrCKYCRNo == null || this.customerIdOrCKYCRNo.equals("")) {
                    this.setMsg("Please fill Customer Id/Ckyc No.");
                    return false;
                }
                Pattern pattern = Pattern.compile("\\d+");
                if (!pattern.matcher(customerIdOrCKYCRNo).matches()) {
                    this.setMsg("Customer Id/Ckyc No can only be numeric.");
                    return false;
                }
                if (this.customerIdOrCKYCRNo.trim().length() > 10) {
                    this.setMsg("Customer Id can not be greater than 10 digit.");
                    return false;
                }
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
            return false;
        }
        return true;
    }

    public void downloadFile() {
        try {
            HttpServletResponse res = (HttpServletResponse) getHttpResponse();
            String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();
            String dirName = "";
            if (this.mode.equalsIgnoreCase("suf")) {
                dirName = props.getProperty("cbsUploadLocationBackup") + ymd.format(dmy.parse(currentItem.getUploadedGenDate())) + "/";
            } else if (this.mode.equalsIgnoreCase("sdf")) {
                dirName = props.getProperty("cbsDownloadRequestBackupLocation");
            }
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + currentItem.getUploadedFileName());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void modeTimeRefreshForm() {
        this.setMsg("");
        this.setType("0");
        this.setTypeDisable(false);
        this.setCkycrCustIdLebal("");
        this.setCustomerIdOrCKYCRNo("");
        this.setDateOfBirth(getTodayDate());
        this.setFromDate(getTodayDate());
        this.setToDate(getTodayDate());
        this.custIdRowVisible = "none";
        this.dobVisible = "none";
        this.visibleDtField = "none";
        this.visibleLeftAllCheckBox = "none";
        this.checkBoxAll = false; //
        this.checkBoxAllDisable = true;
        this.awaitingGridDisplay = "none";
        this.gridCheckBoxDisable = true;
        this.genUploadGridDisplay = "none";
        this.genDownloadGridDisplay = "none";
        this.allGridDisplay = "none";
        this.setButtonLebal("");
        customerDetailList = new ArrayList<CKYCRRequestPojo>();
        genUploadDataList = new ArrayList<CKYCRRequestPojo>();
        genDownloadDataList = new ArrayList<CKYCRRequestPojo>();
        downloadDetailList = new ArrayList<CKYCRRequestPojo>();
        this.setMsg("Please select mode.");
    }

    public void refreshForm() {
        this.setMode("0");
        modeTimeRefreshForm();
    }

    public String exitForm() {
        return "case1";
    }

    //Getter And Setter
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
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

    public List<CKYCRRequestPojo> getCustomerDetailList() {
        return customerDetailList;
    }

    public void setCustomerDetailList(List<CKYCRRequestPojo> customerDetailList) {
        this.customerDetailList = customerDetailList;
    }

    public String getButtonLebal() {
        return buttonLebal;
    }

    public void setButtonLebal(String buttonLebal) {
        this.buttonLebal = buttonLebal;
    }

    public String getAllGridDisplay() {
        return allGridDisplay;
    }

    public void setAllGridDisplay(String allGridDisplay) {
        this.allGridDisplay = allGridDisplay;
    }

    public String getAwaitingGridDisplay() {
        return awaitingGridDisplay;
    }

    public void setAwaitingGridDisplay(String awaitingGridDisplay) {
        this.awaitingGridDisplay = awaitingGridDisplay;
    }

    public CKYCRRequestPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CKYCRRequestPojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<CKYCRRequestPojo> getDownloadDetailList() {
        return downloadDetailList;
    }

    public void setDownloadDetailList(List<CKYCRRequestPojo> downloadDetailList) {
        this.downloadDetailList = downloadDetailList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGenUploadGridDisplay() {
        return genUploadGridDisplay;
    }

    public void setGenUploadGridDisplay(String genUploadGridDisplay) {
        this.genUploadGridDisplay = genUploadGridDisplay;
    }

    public List<CKYCRRequestPojo> getGenUploadDataList() {
        return genUploadDataList;
    }

    public void setGenUploadDataList(List<CKYCRRequestPojo> genUploadDataList) {
        this.genUploadDataList = genUploadDataList;
    }

    public String getGenDownloadGridDisplay() {
        return genDownloadGridDisplay;
    }

    public void setGenDownloadGridDisplay(String genDownloadGridDisplay) {
        this.genDownloadGridDisplay = genDownloadGridDisplay;
    }

    public List<CKYCRRequestPojo> getGenDownloadDataList() {
        return genDownloadDataList;
    }

    public void setGenDownloadDataList(List<CKYCRRequestPojo> genDownloadDataList) {
        this.genDownloadDataList = genDownloadDataList;
    }

    public String getVisibleLeftAllCheckBox() {
        return visibleLeftAllCheckBox;
    }

    public void setVisibleLeftAllCheckBox(String visibleLeftAllCheckBox) {
        this.visibleLeftAllCheckBox = visibleLeftAllCheckBox;
    }

    public String getCustIdRowVisible() {
        return custIdRowVisible;
    }

    public void setCustIdRowVisible(String custIdRowVisible) {
        this.custIdRowVisible = custIdRowVisible;
    }

    public String getDobVisible() {
        return dobVisible;
    }

    public void setDobVisible(String dobVisible) {
        this.dobVisible = dobVisible;
    }

    public String getVisibleDtField() {
        return visibleDtField;
    }

    public void setVisibleDtField(String visibleDtField) {
        this.visibleDtField = visibleDtField;
    }

    public boolean isTypeDisable() {
        return typeDisable;
    }

    public void setTypeDisable(boolean typeDisable) {
        this.typeDisable = typeDisable;
    }
}
