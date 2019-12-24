/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.dto.NpciFileDto;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.MiscMgmtFacadeS1Remote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.OnlineAadharRegistrationPojo;
import com.cbs.pojo.neftrtgs.ExcelReaderPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

public class ManualNeftFileGeneration extends BaseBean {

    private String errorMessage;
    private String mode;
    private String fileType;
    private String fileGenerationDt;
    private String btnValue;
    private String confirmText;
    private String filesLocation;
    private String neftDetailFlag = "none";
    private String neftFileFlag = "none";
    private NpciFileDto fileCurrentItem;
    private List<SelectItem> modeList;
    private List<SelectItem> fileTypeList;
    private List<NpciFileDto> fileGridDetail;
    private List<ExcelReaderPojo> gridDetail;
    private boolean allSelected;
   // private FtsPostingMgmtFacadeRemote ftsRemote;
    //private CommonReportMethodsRemote commonReportRemote;
    private MiscMgmtFacadeS1Remote miscRemoteS1;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public ManualNeftFileGeneration() {
        try {
           // ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
           // commonReportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            miscRemoteS1 = (MiscMgmtFacadeS1Remote) ServiceLocator.getInstance().lookup("MiscMgmtFacadeS1");
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
            fileTypeList.add(new SelectItem("NIRET", "Neft Inward Return"));
            fileTypeList.add(new SelectItem("OIRET", "Outward Inward Return"));
            fileTypeList.add(new SelectItem("ONEFT", "Outward Neft"));
            fileTypeList.add(new SelectItem("ORTGS", "Outward Rtgs"));
            //Files Location
            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            filesLocation = context.getInitParameter("cbsFiles") + "/NEFT";
            File dir = new File(filesLocation + "/");
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
            this.neftDetailFlag = "none";
            this.neftFileFlag = "none";
            this.fileCurrentItem = null;
            this.fileGridDetail = new ArrayList<>();
            this.gridDetail = new ArrayList<>();
            if (this.mode.equalsIgnoreCase("G")) {
                this.setBtnValue("Generate");
                this.neftDetailFlag = "";
            } else if (this.mode.equalsIgnoreCase("S")) {
                this.setBtnValue("Show");
                this.neftFileFlag = "";
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onChangeDate() {
        try {
            if (this.mode == null || this.mode.equals("0") || this.mode.equals("")) {
                this.setErrorMessage("Please select mode.");
                return;
            }
            if (this.fileType == null || this.fileType.equals("0") || this.fileType.equals("")) {
                this.setErrorMessage("Please select file type.");
                return;
            }
            if (this.fileGenerationDt == null || this.fileGenerationDt.equals("")) {
                this.setErrorMessage("Please fill date.");
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
                gridDetail = new ArrayList<>();
                if (this.fileType.equalsIgnoreCase("NIRET")
                        || this.fileType.equalsIgnoreCase("OIRET")) { //Inward Return Generation
                    gridDetail = miscRemoteS1.getIdbiNeftReturnData(this.fileType.trim().equalsIgnoreCase("NIRET") ? "N02" : "R41",
                            ymd.format(dmy.parse(this.fileGenerationDt)));
                } else if (this.fileType.equalsIgnoreCase("ONEFT")
                        || this.fileType.equalsIgnoreCase("ORTGS")) {
                    gridDetail = miscRemoteS1.getIdbiOutwardData(this.fileType.trim().equalsIgnoreCase("ONEFT") ? "N" : "R",
                            ymd.format(dmy.parse(this.fileGenerationDt)));
                }
                if (gridDetail.isEmpty()) {
                    this.setErrorMessage("There is no data to generate the return file.");
                    return;
                }
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

    
    public void setAllBoxSelected() {
        try {
            System.out.println("In All Selected CheckBox.");
            if (allSelected) {
                for (ExcelReaderPojo pojo : gridDetail) {
                    pojo.setSelected(true);
                }
            } else {
                for (ExcelReaderPojo pojo : gridDetail) {
                    pojo.setSelected(false);
                }
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }
    
    public void processAction() {
        try {
            if (this.mode == null || this.mode.equals("0") || this.mode.equals("")) {
                this.setErrorMessage("Please select mode.");
                return;
            }
            if (this.fileType == null || this.fileType.equals("0") || this.fileType.equals("")) {
                this.setErrorMessage("Please select file type.");
                return;
            }
            if (this.fileGenerationDt == null || this.fileGenerationDt.equals("")) {
                this.setErrorMessage("Please fill date.");
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
                if (gridDetail.isEmpty()) {
                    this.setErrorMessage("There is no data to generate the return file.");
                    return;
                }
                List<ExcelReaderPojo> selectedList = new ArrayList<>();
                for(ExcelReaderPojo pojo: gridDetail){
                    if(pojo.isSelected()) selectedList.add(pojo);
                }
                if (selectedList.isEmpty()) {
                    this.setErrorMessage("There is no data to generate the return file.");
                    return;
                }
                String result = "";
                if (this.fileType.equalsIgnoreCase("NIRET") || this.fileType.equalsIgnoreCase("OIRET")) {
                    result = miscRemoteS1.generateIdbiIwReturn(this.fileType, selectedList, ymd.format(dmy.parse(getTodayDate())),
                            getUserName(), getOrgnBrCode(), getFilesLocation());
                } else if (this.fileType.equalsIgnoreCase("ONEFT") || this.fileType.equalsIgnoreCase("ORTGS")) {
                    result = miscRemoteS1.generateIdbiOutward(this.fileType, selectedList, ymd.format(dmy.parse(getTodayDate())),
                            getUserName(), getOrgnBrCode(), getFilesLocation());
                }
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrorMessage(result);
                    return;
                }
                this.setErrorMessage("File has been generated successfully.");
                for(ExcelReaderPojo pojo: selectedList){
                    gridDetail.remove(pojo);
                }
                //gridDetail = new ArrayList<>();
                setAllSelected(false);
            } else if (this.mode.equalsIgnoreCase("S")) {
                fileGridDetail = miscRemoteS1.showGeneratedFiles(this.fileType, ymd.format(dmy.parse(getTodayDate())));
                this.setErrorMessage("Now you can download a file from table.");
                this.neftDetailFlag = "none";
                this.neftFileFlag = "";
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void downloadFile() {
        try {
            if (fileCurrentItem == null) {
                this.setErrorMessage("Please download one file from table.");
                return;
            }
            HttpServletResponse res = (HttpServletResponse) getHttpResponse();
            String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + getFilesLocation() + "/" + "&fileName=" + fileCurrentItem.getFileName());
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
        fileCurrentItem = null;
        fileGridDetail = new ArrayList<>();
        gridDetail = new ArrayList<>();
        this.neftDetailFlag = "";
        this.neftFileFlag = "none";
        setAllSelected(false);
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

    public String getFilesLocation() {
        return filesLocation;
    }

    public void setFilesLocation(String filesLocation) {
        this.filesLocation = filesLocation;
    }

    public NpciFileDto getFileCurrentItem() {
        return fileCurrentItem;
    }

    public void setFileCurrentItem(NpciFileDto fileCurrentItem) {
        this.fileCurrentItem = fileCurrentItem;
    }

    public List<NpciFileDto> getFileGridDetail() {
        return fileGridDetail;
    }

    public void setFileGridDetail(List<NpciFileDto> fileGridDetail) {
        this.fileGridDetail = fileGridDetail;
    }

    public List<ExcelReaderPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<ExcelReaderPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getNeftDetailFlag() {
        return neftDetailFlag;
    }

    public void setNeftDetailFlag(String neftDetailFlag) {
        this.neftDetailFlag = neftDetailFlag;
    }

    public String getNeftFileFlag() {
        return neftFileFlag;
    }

    public void setNeftFileFlag(String neftFileFlag) {
        this.neftFileFlag = neftFileFlag;
    }

    public boolean isAllSelected() {
        return allSelected;
    }

    public void setAllSelected(boolean allSelected) {
        this.allSelected = allSelected;
    }
}
