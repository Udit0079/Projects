/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.dto.NpciFileDto;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.MiscMgmtFacadeS1Remote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

public class AtmSeqFileBean extends BaseBean {

    private String errorMessage;
    private String mode;
    private String fileType;
    private String fileGenerationDt;
    private String btnValue;
    private String confirmText;
    private String filesLocation;
    private NpciFileDto currentItem;
    private List<SelectItem> modeList;
    private List<SelectItem> fileTypeList;
    private List<NpciFileDto> gridDetail;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private CommonReportMethodsRemote commonReportRemote;
    private MiscMgmtFacadeS1Remote miscRemoteS1;
    private NpciMgmtFacadeRemote npciFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public AtmSeqFileBean() {
        try {
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            commonReportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            miscRemoteS1 = (MiscMgmtFacadeS1Remote) ServiceLocator.getInstance().lookup("MiscMgmtFacadeS1");
            npciFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");

            btnRefreshAction();
            initData();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            modeList = new ArrayList<>();
            modeList.add(new SelectItem("0", "--Select--"));
            modeList.add(new SelectItem("G", "Generate"));
            modeList.add(new SelectItem("S", "Show"));

            fileTypeList = new ArrayList<>();
            fileTypeList.add(new SelectItem("0", "--Select--"));
            List referenceList = commonReportRemote.getRefRecList("405");
            if (referenceList.isEmpty()) {
                this.setErrorMessage("Please define initial data for code 405");
                return;
            }
            for (int i = 0; i < referenceList.size(); i++) {
                Vector ele = (Vector) referenceList.get(i);
                if (!ele.get(0).toString().equalsIgnoreCase("D")) { //Not Including The Detail Update Mode
                    fileTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
            //Files Location
            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            this.filesLocation = context.getInitParameter("cbsFiles") + "/ATM";
            File dir = new File(this.filesLocation + "/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onChangeFunction() {
        this.setErrorMessage("");
        try {
            if (this.mode == null || this.mode.equals("0") || this.mode.equals("")) {
                this.setErrorMessage("Please select mode.");
                return;
            }
            if (this.mode.equalsIgnoreCase("G")) {
                this.setBtnValue("Generate");
            } else if (this.mode.equalsIgnoreCase("S")) {
                this.setBtnValue("Show");
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void createConfirmTxt() {
        this.setErrorMessage("");
        try {
            if (this.mode.equalsIgnoreCase("G")) {
                this.setConfirmText("Are you sure to generate the files ?");
            } else if (this.mode.equalsIgnoreCase("S")) {
                this.setConfirmText("Are you sure to show the files ?");
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setErrorMessage("");
        try {
            if (this.mode == null || this.mode.equals("0") || this.mode.equals("")) {
                this.setErrorMessage("Please select mode.");
                return;
            }
            if (this.fileType == null || this.fileType.equals("0") || this.fileType.equals("")) {
                this.setErrorMessage("Please select file type.");
                return;
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.fileGenerationDt) == false) {
                this.setErrorMessage("Please fill proper date.");
                return;
            }
            if (dmy.parse(this.fileGenerationDt).after(dmy.parse(getTodayDate()))) {
                this.setErrorMessage("Date can not be greater than current date.");
                return;
            }
            if (this.mode.equalsIgnoreCase("G")) {
                String result = "";
                if (this.fileType.equalsIgnoreCase("N")) { //New Card & Add on Card
                    result = miscRemoteS1.generateNewAddOnCard(this.fileType, ymd.format(dmy.parse(this.fileGenerationDt)),
                            ymd.format(dmy.parse(getTodayDate())), getUserName(), getOrgnBrCode(), this.filesLocation);
                } else if (this.fileType.equalsIgnoreCase("S") || this.fileType.equalsIgnoreCase("C")) { //Bulk Card Status Change,Bulk Name Change
                    result = miscRemoteS1.generateBulkCardStatusAndNameChange(this.fileType, ymd.format(dmy.parse(this.fileGenerationDt)),
                            ymd.format(dmy.parse(getTodayDate())), getUserName(), getOrgnBrCode(), this.filesLocation);
                }
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrorMessage(result);
                    return;
                }
                this.setErrorMessage("File has been generated successfully.");
            } else if (this.mode.equalsIgnoreCase("S")) {
                gridDetail = miscRemoteS1.showGeneratedFiles(this.fileType, ymd.format(dmy.parse(this.fileGenerationDt)));
                this.setErrorMessage("Now you can download a file from table.");
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

//            List list = npciFacade.getBankDetails();
//            Vector ele = (Vector) list.get(0);
//            if (ele.get(8) == null) {
//                this.setErrorMessage("Please define Aadhar Location in bank detail.");
//                return;
//            }
//            String dirName = ele.get(8).toString().trim();
//            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + currentItem.getFileName());

            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + this.filesLocation + "/" + "&fileName=" + currentItem.getFileName());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        this.setMode("0");
        this.setFileType("0");
        this.setFileGenerationDt(getTodayDate());
        this.setBtnValue("Generate");
        this.setConfirmText("");
//        this.setFilesLocation("");
        currentItem = null;
        gridDetail = new ArrayList<>();
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public List<SelectItem> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(List<SelectItem> fileTypeList) {
        this.fileTypeList = fileTypeList;
    }

    public List<NpciFileDto> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NpciFileDto> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getFilesLocation() {
        return filesLocation;
    }

    public void setFilesLocation(String filesLocation) {
        this.filesLocation = filesLocation;
    }
}
