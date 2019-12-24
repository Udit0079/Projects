/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.dto.neftrtgs.NeftOwDownloadDTO;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

public class DownloadOwFileController extends BaseBean {

    private String errorMessage;
    private String fileGenerationDt;
    private String fileType;
    private List<SelectItem> fileTypeList;
    private String allGridDisplay;
    private String awaitingGridDisplay;
    private NeftOwDownloadDTO currentItem;
    private List<NeftOwDownloadDTO> gridDetail;
    private NeftOwDownloadDTO awaitingCurrentItem;
    private List<NeftOwDownloadDTO> awaitingGridDetail;
    private NeftRtgsMgmtFacadeRemote neftRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public DownloadOwFileController() {
        try {
            neftRemote = (NeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NeftRtgsMgmtFacade");
            fileTypeList = new ArrayList<SelectItem>();
            fileTypeList.add(new SelectItem("0", "--Select--"));
            fileTypeList.add(new SelectItem("A", "ALL"));
            fileTypeList.add(new SelectItem("W", "Response Awaiting"));

            btnRefreshAction();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void dateAction() {
        this.setErrorMessage("");
        try {
            if (this.fileGenerationDt == null || this.fileGenerationDt.equals("")) {
                this.setErrorMessage("Please fill proper File Generation Date.");
                return;
            }
            if (this.fileType == null || this.fileType.equals("0")) {
                this.setErrorMessage("Please select File Type.");
                return;
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.fileGenerationDt) == false) {
                this.setErrorMessage("Please fill proper File Generation Date.");
                return;
            }
            if (dmy.parse(this.fileGenerationDt).after(dmy.parse(getTodayDate()))) {
                this.setErrorMessage("File Generation Date can not be greater than Current Date.");
                return;
            }
            if (this.fileType.equalsIgnoreCase("A")) { //For All Files
                gridDetail = neftRemote.getGeneratedFiles(this.fileType, getOrgnBrCode(), ymd.format(dmy.parse(this.fileGenerationDt)));
                this.awaitingGridDisplay = "none";
                this.allGridDisplay = "";
            } else if (this.fileType.equalsIgnoreCase("W")) { //For Only Awaiting Response
                awaitingGridDetail = neftRemote.getGeneratedFiles(this.fileType, getOrgnBrCode(), ymd.format(dmy.parse(this.fileGenerationDt)));
                this.awaitingGridDisplay = "";
                this.allGridDisplay = "none";
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void downloadFile() {
        try {
            HttpServletResponse res = (HttpServletResponse) getHttpResponse();
            String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();
            String dirName = neftRemote.getNeftOutwardFileBkpLocation("BT");

            if (this.fileType.equalsIgnoreCase("A")) {
                if (currentItem == null) {
                    this.setErrorMessage("Please download one file from table.");
                    return;
                }
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + currentItem.getFileName());
            } else if (this.fileType.equalsIgnoreCase("W")) {
                if (awaitingCurrentItem == null) {
                    this.setErrorMessage("Please download one file from table.");
                    return;
                }
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + awaitingCurrentItem.getFileName());
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        this.setFileGenerationDt(getTodayDate());
        this.setFileType("0");
        this.allGridDisplay = "none";
        gridDetail = new ArrayList<NeftOwDownloadDTO>();
        this.currentItem = null;
        this.awaitingGridDisplay = "none";
        awaitingGridDetail = new ArrayList<NeftOwDownloadDTO>();
        awaitingCurrentItem = null;
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

    public String getFileGenerationDt() {
        return fileGenerationDt;
    }

    public void setFileGenerationDt(String fileGenerationDt) {
        this.fileGenerationDt = fileGenerationDt;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public List<SelectItem> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(List<SelectItem> fileTypeList) {
        this.fileTypeList = fileTypeList;
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

    public NeftOwDownloadDTO getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(NeftOwDownloadDTO currentItem) {
        this.currentItem = currentItem;
    }

    public List<NeftOwDownloadDTO> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NeftOwDownloadDTO> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public NeftOwDownloadDTO getAwaitingCurrentItem() {
        return awaitingCurrentItem;
    }

    public void setAwaitingCurrentItem(NeftOwDownloadDTO awaitingCurrentItem) {
        this.awaitingCurrentItem = awaitingCurrentItem;
    }

    public List<NeftOwDownloadDTO> getAwaitingGridDetail() {
        return awaitingGridDetail;
    }

    public void setAwaitingGridDetail(List<NeftOwDownloadDTO> awaitingGridDetail) {
        this.awaitingGridDetail = awaitingGridDetail;
    }
}
