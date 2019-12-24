/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.NpciFileDto;
import com.cbs.facade.misc.TransferBatchDeletionFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

public class PrizmFileGenerator extends BaseBean {

    private String errorMessage;
    private String function;
    private String disableGenDt = "none";
    private String fileGenDt;
    private String btnValue;
    private String confirmText;
    private NpciFileDto currentItem;
    private List<SelectItem> functionList;
    private List<NpciFileDto> gridDetail;
    private NpciMgmtFacadeRemote npciFacade = null;
    private TransferBatchDeletionFacadeRemote remote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public PrizmFileGenerator() {
        try {
            npciFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            remote = (TransferBatchDeletionFacadeRemote) ServiceLocator.getInstance().lookup("TransferBatchDeletionFacade");
            initData();
            btnRefreshAction();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("G", "Generate"));
            functionList.add(new SelectItem("S", "Show"));
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onChangeFunction() {
        this.setErrorMessage("");
        try {
            this.setFileGenDt(getTodayDate());
            if (this.function.equals("G")) {
                this.disableGenDt = "none";
                this.setBtnValue("Generate");
            } else if (this.function.equals("S")) {
                this.disableGenDt = "";
                this.setBtnValue("Show");
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void createConfirmTxt() {
        this.setErrorMessage("");
        try {
            if (this.function.equals("G")) {
                this.setConfirmText("Are you sure to generate the file ?");
            } else if (this.function.equals("S")) {
                this.setConfirmText("Are you sure to show the file ?");
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setErrorMessage("");
        try {
            if (validateField()) {
                if (this.function.equals("G")) {
                    String result = remote.generatePrizmCardInputFile(getUserName(), getTodayDate(), getOrgnBrCode());
                    if (result.equals("true")) {
                        btnRefreshAction();
                        this.setErrorMessage("File has been generated successfully.");
                    } else {
                        this.setErrorMessage(result);
                    }
                } else if (this.function.equals("S")) {
                    gridDetail = remote.showGeneratedFiles(this.fileGenDt, getOrgnBrCode());
                    this.setErrorMessage("Now you can download a file from table.");
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
            if (this.function.equals("S")) {
                if (this.fileGenDt == null || this.fileGenDt.equals("")) {
                    this.setErrorMessage("Please fill proper File Show Date.");
                    return false;
                }
                if (new Validator().validateDate_dd_mm_yyyy(this.fileGenDt) == false) {
                    this.setErrorMessage("Please select proper File Show Date.");
                    return false;
                }
                if (dmy.parse(this.fileGenDt).after(dmy.parse(getTodayDate()))) {
                    this.setErrorMessage("File Show Date can not be greater than Current Date.");
                    return false;
                }
            }
            result = true;
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
        return result;
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        this.setFunction("G");
        this.setFileGenDt(getTodayDate());
        this.setBtnValue("Generate");
        this.setConfirmText("");
        this.disableGenDt = "none";
        currentItem = null;
        gridDetail = new ArrayList<NpciFileDto>();
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    //Getter And Setter
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

    public String getDisableGenDt() {
        return disableGenDt;
    }

    public void setDisableGenDt(String disableGenDt) {
        this.disableGenDt = disableGenDt;
    }

    public String getFileGenDt() {
        return fileGenDt;
    }

    public void setFileGenDt(String fileGenDt) {
        this.fileGenDt = fileGenDt;
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
}
