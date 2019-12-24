/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.dto.NpciFileDto;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

public class CtsReturnFile extends BaseBean {

    private String errorMessage;
    private String function;
    private String branch;
    private String fileDt;
    private String btnValue;
    private String confirmText;
    private NpciFileDto currentItem;
    private List<SelectItem> functionList;
    private List<SelectItem> branchList;
    private List<NpciFileDto> gridDetail;
    private String directory;
    private CommonReportMethodsRemote reportRemote = null;
    private CtsManagementFacadeRemote ctsTxnRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private static final String RETURN_FILE_LOCATION = "HDFC-CTS-RETURN";

    public CtsReturnFile() {
        try {
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ctsTxnRemote = (CtsManagementFacadeRemote) ServiceLocator.getInstance().lookup("CtsManagementFacade");
            btnRefreshAction();
            initData();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            functionList = new ArrayList<>();
            functionList.add(new SelectItem("0", "--Select--"));
            functionList.add(new SelectItem("G", "Generate File"));
            functionList.add(new SelectItem("S", "Show"));

            branchList = new ArrayList<>();
            branchList.add(new SelectItem("0", "--Select--"));

            String alphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (alphaCode.equalsIgnoreCase("HO")) {
                branchList.add(new SelectItem("ALL"));
            }
            List brList = reportRemote.getAlphacodeAllAndBranch(getOrgnBrCode());
            for (int i = 0; i < brList.size(); i++) {
                Vector brnVector = (Vector) brList.get(i);
                branchList.add(new SelectItem(brnVector.get(1).toString().length() < 2
                        ? "0" + brnVector.get(1).toString() : brnVector.get(1).toString(), brnVector.get(0).toString()));
            }

            ServletContext context = (ServletContext) getFacesContext().getExternalContext().getContext();
//            this.directory = context.getInitParameter("cts") + "/" + RETURN_FILE_LOCATION + "/";
            this.directory = context.getInitParameter("cbsFiles") + "/" + RETURN_FILE_LOCATION + "/";
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onChangeFunction() {
        this.setErrorMessage("");
        this.setBtnValue("");
        this.btnValue = this.function.equals("G") ? "Generate File" : "Show";
    }

    public void createConfirmTxt() {
        this.setErrorMessage("");
        this.setConfirmText("");
        try {
            if (this.function.equals("G") && this.btnValue.equalsIgnoreCase("Generate File")) {
                this.setConfirmText("Are you sure to generate the CTS return file ?");
            } else if (this.function.equals("S") && this.btnValue.equalsIgnoreCase("Show")) {
                this.setConfirmText("Are you sure to show the CTS return file ?");
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setErrorMessage("");
        try {
            if (validateField()) {
                File dir = new File(directory + "/");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                if (this.function.equalsIgnoreCase("G")) {
                    String result = ctsTxnRemote.generateCtsReturnFile(this.branch, this.fileDt,
                            this.directory, getUserName(), getOrgnBrCode(), getTodayDate());
                    if (result.equals("true")) {
                        btnRefreshAction();
                        this.setErrorMessage("File has been generated successfully.");
                    }
                } else if (this.function.equalsIgnoreCase("S")) {
                    gridDetail = new ArrayList<>();
                    gridDetail = ctsTxnRemote.showCtsReturnFiles(getOrgnBrCode(), ymd.format(dmy.parse(this.fileDt)));
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public boolean validateField() {
        this.setErrorMessage("");
        try {
            if (this.function == null || this.function.equalsIgnoreCase("0") || this.function.equals("")) {
                this.setErrorMessage("Please select Function.");
                return false;
            }
            if (this.branch == null || this.branch.equalsIgnoreCase("0") || this.branch.equals("")) {
                this.setErrorMessage("Please select Branch.");
                return false;
            }
            if (this.fileDt == null || this.fileDt.equals("")) {
                this.setErrorMessage("Please fill Date.");
                return false;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.fileDt)) {
                this.setErrorMessage("Please fill proper Date.");
                return false;
            }
            if (dmy.parse(this.fileDt).compareTo(dmy.parse(dmy.format(new Date()))) > 0) {
                this.setErrorMessage("Date can not be greater than currrent date.");
                return false;
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void downloadFile() {
        try {
            if (currentItem == null) {
                this.setErrorMessage("Please download one file from table.");
                return;
            }
            HttpServletResponse res = (HttpServletResponse) getHttpResponse();
            String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + directory + "&fileName=" + currentItem.getFileName());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        this.setFunction("0");
        this.setBranch("0");
        this.setFileDt(getTodayDate());
        this.setBtnValue("");
        currentItem = null;
        gridDetail = new ArrayList<>();
        this.setConfirmText("");
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFileDt() {
        return fileDt;
    }

    public void setFileDt(String fileDt) {
        this.fileDt = fileDt;
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

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public List<NpciFileDto> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NpciFileDto> gridDetail) {
        this.gridDetail = gridDetail;
    }
}
