/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.inventory;

import com.cbs.dto.NpciFileDto;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.txn.OtherAuthorizationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class ChequeBookFileGenerator extends BaseBean {

    public ChequeBookFileGenerator() {
        try {
            npciFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            // reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherRemote = (OtherAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup("OtherAuthorizationManagementFacade");
            btnRefreshAction();
            initData();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--Select--"));
            functionList.add(new SelectItem("G", "Generate"));
            functionList.add(new SelectItem("R", "Re-Generate"));
            functionList.add(new SelectItem("S", "Show"));
            
            instTypeList = new ArrayList<>();
            instTypeList.add(new SelectItem("1", "Cheque Book File"));
            instTypeList.add(new SelectItem("2", "Payorder Book File"));

            fileTypeList = new ArrayList<SelectItem>();
            fileTypeList.add(new SelectItem("0", "--Select--"));
            setFileType("0");
            setInstType("1");
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void functionAction() {
        try {
            this.fieldDisplay = "none";
            this.setConfirmText("");
            this.setBtnValue("");
            if (this.function == null || this.function.equals("0")) {
                this.setErrorMessage("Please select function.");
                return;
            }
            if (this.function.equals("G")) {
                this.setBtnValue("Generate");
            } else if (this.function.equals("R")) {
                this.setBtnValue("Re-Generate");
            } else if (this.function.equals("S")) {
                this.setBtnValue("Show");
                this.fieldDisplay = "";
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void getGeneratedFiles() {
        try {
            setFocusId("btnHtml");
            if (function.equals("R")) {
                List<String> dataList = otherRemote.getGeneratedFilesName(instType, dt, getOrgnBrCode());
                fileTypeList = new ArrayList<SelectItem>();
                fileTypeList.add(new SelectItem("", "--Select--"));

                for (String name : dataList) {
                    fileTypeList.add(new SelectItem(name, name));
                }
                setFocusId("ddType");
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void createConfirmTxt() {
        this.setErrorMessage("");
        if (this.function == null || this.function.equals("0")) {
            this.setErrorMessage("Please select function.");
            return;
        }
        this.setConfirmText("");
        if (this.function.equals("G")) {
            this.setConfirmText("Are you sure to generate the file ?");
        }else if (this.function.equals("R")) {
            this.setConfirmText("Are you sure to Re-generate the select file ?");
        } 
        else if (this.function.equals("S")) {
            this.setConfirmText("Are you sure to show the file ?");
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
                this.setErrorMessage("Please define File Location in bank detail.");
                return;
            }
            String dirName = ele.get(8).toString().trim();
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "chbook/&fileName=" + currentItem.getFileName());

        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void processAction() {
        this.setErrorMessage("");
        try {
            if (validateField()) {
                if (this.function.equals("G") || this.function.equals("R")) {
                    
                    if(fileType.equals("0")) setFileType("");
                    
                    String result = otherRemote.generateChBookPrintingFile(instType, fileType, this.dt, getOrgnBrCode(), getUserName());
                    if (!result.equals("true")) {
                        this.setErrorMessage(result);
                        return;
                    }
                    btnRefreshAction();
                    this.setErrorMessage("File has been generated successfully.");
                } else if (this.function.equals("S")) {
                    gridDetail = otherRemote.showGeneratedFiles(instType, fileType, this.dt,getOrgnBrCode());
                    this.setErrorMessage("Now you can download a file from table.");
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public boolean validateField() {
        try {
            if (this.function == null || this.function.equals("0")) {
                this.setErrorMessage("Please select function.");
                return false;
            }
            if (this.function.equals("R") && (this.fileType == null || this.fileType.equals("0"))) {
                this.setErrorMessage("Please select file type.");
                return false;
            }
            if (this.dt == null || this.dt.equals("")) {
                this.setErrorMessage("Please select proper date.");
                return false;
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.dt) == false) {
                this.setErrorMessage("Please select proper date.");
                return false;
            }
            if (dmy.parse(this.dt).after(dmy.parse(getTodayDate()))) {
                this.setErrorMessage("Date can not be greater than Current Date.");
                return false;
            }
//            String alphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
//            if (!alphaCode.equalsIgnoreCase("HO")) {
//                this.setErrorMessage("Any work will be perform from HO.");
//                return false;
//            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        this.setFunction("0");
        this.setFileType("0");
        this.setDt(getTodayDate());
        this.setBtnValue("");
        this.setConfirmText("");
        this.fieldDisplay = "none";
        currentItem = null;
        gridDetail = new ArrayList<NpciFileDto>();
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    private NpciMgmtFacadeRemote npciFacade = null;
    //private CommonReportMethodsRemote reportRemote = null;
    private OtherAuthorizationManagementFacadeRemote otherRemote;
   // private FtsPostingMgmtFacadeRemote ftsRemote = null;

    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    private String errorMessage;
    private String function;
    private String fileType;
    private String instType;
    private String dt;
    private String btnValue;
    private String createConfirmTxt;
    private String confirmText;
    private String fieldDisplay;
    private NpciFileDto currentItem;
    private String focusId;

    private List<SelectItem> functionList;
    private List<SelectItem> fileTypeList;
    private List<SelectItem> instTypeList;
    private List<NpciFileDto> gridDetail;

    public String getInstType() {
        return instType;
    }

    public void setInstType(String instType) {
        this.instType = instType;
    }

    public List<SelectItem> getInstTypeList() {
        return instTypeList;
    }

    public void setInstTypeList(List<SelectItem> instTypeList) {
        this.instTypeList = instTypeList;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getCreateConfirmTxt() {
        return createConfirmTxt;
    }

    public void setCreateConfirmTxt(String createConfirmTxt) {
        this.createConfirmTxt = createConfirmTxt;
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

    public List<SelectItem> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(List<SelectItem> fileTypeList) {
        this.fileTypeList = fileTypeList;
    }

    public String getFieldDisplay() {
        return fieldDisplay;
    }

    public void setFieldDisplay(String fieldDisplay) {
        this.fieldDisplay = fieldDisplay;
    }

    public List<NpciFileDto> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NpciFileDto> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }
}
