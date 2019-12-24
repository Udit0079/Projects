/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.dto.NpciFileDto;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.neftrtgs.SSSFileGeneratorFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

public class SSSFileController extends BaseBean {

    public SSSFileController() {
        try {
            sssRemote = (SSSFileGeneratorFacadeRemote) ServiceLocator.getInstance().lookup("SSSFileGeneratorFacade");
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            npciFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            btnRefreshAction();
            initData();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("", "--Select--"));
            functionList.add(new SelectItem("CTP", "Calculate Total Premium"));
            functionList.add(new SelectItem("GSF", "Generate SSS Files"));
            functionList.add(new SelectItem("SSF", "Show SSS Files"));
            functionList.add(new SelectItem("RSF", "Generate SSS Files Return"));
            functionList.add(new SelectItem("SRF", "Show Return Files"));

            schemeList = new ArrayList<SelectItem>();
            List list = reportRemote.getRefRecList("215");
            for (int i = 0; i < list.size(); i++) {
                Vector vec = (Vector) list.get(i);
                schemeList.add(new SelectItem(vec.get(0).toString(), vec.get(1).toString()));
            }
            vendorList = new ArrayList<SelectItem>();
            ctrlFileList = new ArrayList<SelectItem>();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onChangeFunction() {
        this.setErrorMessage("");
        try {
            if (this.function == null || this.function.equals("")) {
                this.setErrorMessage("Please select function.");
                return;
            }
            if (this.function.equals("GSF")) {
                this.setBtnValue("Generate File");
                this.setDisabled(false);
            } else if (this.function.equals("SSF")) {
                this.setBtnValue("Show File");
                this.setDisabled(true);
            } else if (this.function.equals("CTP")) {
                this.setBtnValue("Calculate Premium");
                this.setDisabled(true);
            } else if (this.function.equals("RSF")) {
                this.setBtnValue("Generate Return Files");
                this.setDisabled(true);
            } else if (this.function.equals("SRF")) {
                this.setBtnValue("Show Return Files");
                this.setDisabled(true);
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onChangeScheme() {
        this.setErrorMessage("");
        try {
            vendorList = new ArrayList<SelectItem>();
            List list = sssRemote.getVendors(schemeCode);
            for (int i = 0; i < list.size(); i++) {
                Vector vec = (Vector) list.get(i);
                vendorList.add(new SelectItem(vec.get(0).toString(), vec.get(1).toString()));
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onChangeVendor() {
        this.setErrorMessage("");
        try {
            if (this.function == null || this.function.equals("")) {
                this.setErrorMessage("Please select function.");
                return;
            }
            if (this.function.equals("GSF") && (this.vendor.equals("LIC") || this.vendor.equals("UIC"))) {
                this.setDisplay("");
                this.setControlFileDisplay("none");
            } else {
                this.setDisplay("none");
                this.setControlFileDisplay("none");
            }
            if (this.function.equals("GSF") && this.schemeCode.equals("APY") && this.vendor.equals("NSDL")) {
                this.setDisabled(true);
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onChangeFileGenDt() {
        this.setErrorMessage("");
        try {
            if (validateField()) {
                if (this.function.equals("RSF") && this.schemeCode.equals("PMSBY") && this.vendor.equals("UIC")) {
                    this.setControlFileDisplay("");
                    retriveActualFile();
                    this.setDisplay("none");
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void retriveActualFile() {
        try {
            ctrlFileList = new ArrayList<SelectItem>();
            ctrlFileList.add(new SelectItem("--Select--"));
            List list = sssRemote.getActualFile(schemeCode, vendor, ymd.format(dmy.parse(fileGenerationDt)));
            if(list.isEmpty()){
                throw new Exception("There is no file to generate the return.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector vec = (Vector) list.get(i);
                ctrlFileList.add(new SelectItem(vec.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void createConfirmTxt() {
        this.setErrorMessage("");
        try {
            if (this.function.equals("GSF") && this.btnValue.equalsIgnoreCase("Generate File")) {
                this.setConfirmText("Are you sure to generate the files ?");
            } else if (this.function.equals("SSF") && this.btnValue.equalsIgnoreCase("Show File")) {
                this.setConfirmText("Are you sure to show the files ?");
            } else if (this.function.equals("CTP") && this.btnValue.equalsIgnoreCase("Calculate Premium")) {
                this.setConfirmText("Are you sure to calculate the premium ?");
            } else if (this.function.equals("RSF") && this.btnValue.equalsIgnoreCase("Generate Return Files")) {
                this.setConfirmText("Are you sure to Generate Return Files ?");
            } else if (this.function.equals("SRF") && this.btnValue.equalsIgnoreCase("Show Return Files")) {
                this.setConfirmText("Are you sure to Show Return Files ?");
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setErrorMessage("");
        try {
            if (validateField()) {
                if (this.function.equals("GSF") && this.btnValue.equalsIgnoreCase("Generate File")) {
                    if (!schemeCode.equals("APY")) {
                        if (premiumAmt.equals("") || premiumAmt.equals("0") || Double.parseDouble(premiumAmt) < 0) {
                            throw new ApplicationException("Please fill correct value in Premium Amount");
                        }
                    }
                    String result = sssRemote.generateSSSFiles(this.schemeCode, this.vendor, this.fileGenerationDt,
                            getTodayDate(), getOrgnBrCode(), getUserName(), premiumAmt, utrNo, utrDate);
                    if (result.equals("true")) {
                        btnRefreshAction();
                        this.setErrorMessage("File has been generated successfully.");
                    } else {
                        this.setErrorMessage(result);
                    }
                } else if (this.function.equals("SSF") && this.btnValue.equalsIgnoreCase("Show File")) {
                    gridDetail = sssRemote.showGeneratedFiles(this.schemeCode, this.fileGenerationDt);
                    this.setErrorMessage("Now you can download a file from table.");
                } else if (this.function.equals("RSF") && this.btnValue.equalsIgnoreCase("Generate Return Files")) {
                    if (this.schemeCode.equalsIgnoreCase("PMSBY") && this.vendor.equalsIgnoreCase("UIC")) {
                        if (this.ctrlFile == null || this.ctrlFile.equalsIgnoreCase("--Select--") || this.ctrlFile.equals("")) {
                            throw new ApplicationException("Please select control file name.");
                        }
                        String result = sssRemote.generateSSSReturnFiles(this.schemeCode, this.vendor,
                                this.fileGenerationDt, this.ctrlFile, getTodayDate(), getUserName(), getOrgnBrCode());
                        if (!result.equalsIgnoreCase("true")) {
                            throw new Exception(result);
                        }
                        btnRefreshAction();
                        this.setErrorMessage("File has been generated successfully.");
                    }
                } else if (this.function.equals("SRF") && this.btnValue.equalsIgnoreCase("Show Return Files")) {
                    String retSchemeCode = "";
                    if (this.schemeCode.equalsIgnoreCase("PMSBY") && this.vendor.equalsIgnoreCase("UIC")) {
                        retSchemeCode = "PMSBYR";
                    }
                    gridDetail = sssRemote.showGeneratedFiles(retSchemeCode, this.fileGenerationDt);
                    this.setErrorMessage("Now you can download a file from table.");
                } else {
                    String result = sssRemote.calculatePremium(this.schemeCode, this.vendor, this.fileGenerationDt);
                    String[] tmpArr = result.split("@#");
                    this.setPremiumAmt(tmpArr[0]);
                    this.setTotalRecord(Integer.parseInt(tmpArr[1]));
                    this.setErrorMessage("Premium has been calculated successfully.");
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void downloadFile() {
        try {
            if (currentItem == null) {
                this.setErrorMessage("Please download one file from table.");
                return;
            }
            HttpServletResponse res = (HttpServletResponse) getHttpResponse();
            String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();

            List list = npciFacade.getBankDetails();
            Vector ele = (Vector) list.get(0);
            if (ele.get(8) == null) {
                this.setErrorMessage("Please define Aadhar Location in bank detail.");
                return;
            }
            String dirName = ele.get(8).toString().trim();
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + currentItem.getFileName());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public boolean validateField() {
        boolean result = false;
        try {
            if (this.function == null || this.function.equals("")) {
                this.setErrorMessage("Please select Function.");
                return false;
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.fileGenerationDt) == false) {
                this.setErrorMessage("Please select proper File Generation Date.");
                return false;
            }
            if (dmy.parse(this.fileGenerationDt).after(dmy.parse(getTodayDate()))) {
                this.setErrorMessage("File Generation Date can not be greater than Current Date.");
                return false;
            }
            String alphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphaCode.equalsIgnoreCase("HO")) {
                this.setErrorMessage("This work will perform only from HO.");
                return false;
            }
            result = true;
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
        return result;
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        this.setFunction("");
        this.setFileGenerationDt(getTodayDate());
        this.setBtnValue("Calculate Premium");
        currentItem = null;
        gridDetail = new ArrayList<NpciFileDto>();
        this.setConfirmText("");
        setDisplay("none");
        setControlFileDisplay("none");
        setDisabled(false);
        setPremiumAmt("");
        setTotalRecord(0);
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    public String getCtrlFile() {
        return ctrlFile;
    }

    public void setCtrlFile(String ctrlFile) {
        this.ctrlFile = ctrlFile;
    }

    public List<SelectItem> getCtrlFileList() {
        return ctrlFileList;
    }

    public void setCtrlFileList(List<SelectItem> ctrlFileList) {
        this.ctrlFileList = ctrlFileList;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFileGenerationDt() {
        return fileGenerationDt;
    }

    public void setFileGenerationDt(String fileGenerationDt) {
        this.fileGenerationDt = fileGenerationDt;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getConfirmText() {
        return confirmText;
    }

    public void setConfirmText(String confirmText) {
        this.confirmText = confirmText;
    }

    public NpciFileDto getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(NpciFileDto currentItem) {
        this.currentItem = currentItem;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public List<NpciFileDto> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NpciFileDto> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public List<SelectItem> getSchemeList() {
        return schemeList;
    }

    public void setSchemeList(List<SelectItem> schemeList) {
        this.schemeList = schemeList;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public List<SelectItem> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<SelectItem> vendorList) {
        this.vendorList = vendorList;
    }

    public String getPremiumAmt() {
        return premiumAmt;
    }

    public void setPremiumAmt(String premiumAmt) {
        this.premiumAmt = premiumAmt;
    }

    public String getUtrNo() {
        return utrNo;
    }

    public void setUtrNo(String utrNo) {
        this.utrNo = utrNo;
    }

    public String getUtrDate() {
        return utrDate;
    }

    public void setUtrDate(String utrDate) {
        this.utrDate = utrDate;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getControlFileDisplay() {
        return controlFileDisplay;
    }

    public void setControlFileDisplay(String controlFileDisplay) {
        this.controlFileDisplay = controlFileDisplay;
    }
    private String function;
    private String schemeCode;
    private String vendor;
    private String premiumAmt;
    private int totalRecord;
    private String utrNo;
    private String utrDate;
    private String errorMessage;
    private String fileGenerationDt;
    private String btnValue;
    private String confirmText;
    private String display = "none";
    private String controlFileDisplay = "none";
    private boolean disabled = false;
    private NpciFileDto currentItem;
    private List<SelectItem> functionList;
    private List<SelectItem> schemeList;
    private List<SelectItem> vendorList;
    private List<NpciFileDto> gridDetail;
    private CommonReportMethodsRemote reportRemote = null;
    private SSSFileGeneratorFacadeRemote sssRemote = null;
    private NpciMgmtFacadeRemote npciFacade = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String ctrlFile;
    private List<SelectItem> ctrlFileList;
}
