/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.clg;

import com.cbs.dto.npci.cts.reverse.ReturnDTO;
import com.cbs.facade.clg.NpciClearingMgmtFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
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

public class NpciCTSReturnBean extends BaseBean {

    private String errorMessage;
    private String fileGenerationDt;
    private String clearingType;
    private String scheduleNo;
    private String xmlFileName;
    private String xmlDoneFileName;
    private String scheduleFileName;
    private String zipFileName;
    private String txtFileName;
    private String fileType;
    private boolean xmlLink;
    private boolean zipLink;
    private boolean txtLink;
    private int ctsSponsor = 0;
    private List<ReturnDTO> gridDetail;
    private List<SelectItem> clearingTypeList;
    private List<SelectItem> scheduleNoList;
    private List<SelectItem> fileTypeList;
    private NpciClearingMgmtFacadeRemote beanRemote;
    private CommonReportMethodsRemote commonRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public NpciCTSReturnBean() {
        btnRefreshAction();
        try {
            beanRemote = (NpciClearingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciClearingMgmtFacade");
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            ctsSponsor = ftsRemote.getCodeForReportName("CTS-SPONSOR");
            onLoadData();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onLoadData() {
        clearingTypeList = new ArrayList<SelectItem>();
        scheduleNoList = new ArrayList<SelectItem>();
        fileTypeList = new ArrayList<SelectItem>();
        try {
            List list = commonRemote.getRefRecList("016");
            if (list.isEmpty()) {
                this.setErrorMessage("Please define in cbs ref rec type for 016");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                clearingTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            fileTypeList.add(new SelectItem("0", "--Select--"));
            if (ctsSponsor == 2) { //Khattri
                fileTypeList.add(new SelectItem("BULK-XML", "BULK XML"));
                fileTypeList.add(new SelectItem("BULK-ZIP", "BULK ZIP"));
            } else if (ctsSponsor == 3) { //Ramgariya
                fileTypeList.add(new SelectItem("BULK-TEXT", "BULK TEXT"));
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void retrieveScheduleNo() {
        this.setErrorMessage("");
        try {
            if (this.fileGenerationDt == null || this.fileGenerationDt.equals("")) {
                this.setErrorMessage("Please fill date to generate the return file.");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(fileGenerationDt)) {
                this.setErrorMessage("Please fill proper date.");
                return;
            }
            if (this.clearingType == null || this.clearingType.equals("")) {
                this.setErrorMessage("Please select clearing type.");
                return;
            }
            List list = beanRemote.getAllScheduleNos(ymd.format(dmy.parse(this.fileGenerationDt)), getOrgnBrCode(), this.clearingType);
            if (list.isEmpty()) {
                this.setErrorMessage("There is no schedule no to generate the return file.");
                return;
            }
            scheduleNoList = new ArrayList<SelectItem>();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                scheduleNoList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void retrieveReturnData() {
        this.setErrorMessage("");
        try {
            this.setScheduleFileName("");
            gridDetail = new ArrayList<ReturnDTO>();

            if (this.fileType == null || this.fileType.equals("0") || fileType.equals("")) {
                this.setErrorMessage("Please select file type.");
                return;
            }
            if (this.fileGenerationDt == null || this.fileGenerationDt.equals("")) {
                this.setErrorMessage("Please fill date to generate the return file.");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(fileGenerationDt)) {
                this.setErrorMessage("Please fill proper date.");
                return;
            }
            if (this.clearingType == null || this.clearingType.equals("")) {
                this.setErrorMessage("Please select clearing type.");
                return;
            }
            if (this.scheduleNo == null || this.scheduleNo.equals("")) {
                this.setErrorMessage("Please select schedule no.");
                return;
            }
            List<ReturnDTO> returnData = new ArrayList<>();
            if (ctsSponsor == 2) { //Khattri
                returnData = beanRemote.getCtsReturnData(ymd.format(dmy.parse(this.fileGenerationDt)),
                        this.clearingType, this.scheduleNo, getOrgnBrCode());
            } else if (ctsSponsor == 3) { //Ramgariya
                returnData = beanRemote.getNpciManualReturnData(ymd.format(dmy.parse(this.fileGenerationDt)),
                        this.clearingType, this.scheduleNo, getOrgnBrCode());
            }
            if (returnData.isEmpty()) {
                this.setErrorMessage("There is no data to generate the return file.");
                return;
            }
            this.scheduleFileName = returnData.get(0).getBinaryDataFileName().trim();
            gridDetail = returnData;
            this.setErrorMessage("Now you can generate the return file.");
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setErrorMessage("");
        try {
            if (this.fileType == null || this.fileType.equals("0") || fileType.equals("")) {
                this.setErrorMessage("Please select file type.");
                return;
            }
            if (this.fileGenerationDt == null || this.fileGenerationDt.equals("")) {
                this.setErrorMessage("Please fill date to generate the return file.");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(fileGenerationDt)) {
                this.setErrorMessage("Please fill proper date.");
                return;
            }
            if (this.clearingType == null || this.clearingType.equals("")) {
                this.setErrorMessage("Please select clearing type.");
                return;
            }
            if (this.scheduleNo == null || this.scheduleNo.equals("")) {
                this.setErrorMessage("Please select schedule no.");
                return;
            }
            if (this.scheduleFileName == null || this.scheduleFileName.equals("")) {
                this.setErrorMessage("XML data file name can not be blank.");
                return;
            }
            if (gridDetail == null || gridDetail.isEmpty()) {
                this.setErrorMessage("Instrument detail can not be blank.");
                return;
            }

            if (this.fileType.equals("BULK-XML")) {
                String fileName = beanRemote.generateCtsReturnXml(gridDetail);
                if (fileName.equals("")) {
                    this.setErrorMessage("File were not generated");
                    return;
                }
                xmlFileName = fileName;
                xmlDoneFileName = fileName + ".done";
                xmlLink = true;
            } else if (this.fileType.equals("BULK-ZIP")) {
                String zipFile = beanRemote.generateCtsZipReturnXml(gridDetail);
                if (zipFile.equals("")) {
                    this.setErrorMessage("File were not generated");
                    return;
                }
                zipFileName = zipFile;
                zipLink = true;
            } else if (this.fileType.equals("BULK-TEXT")) {
                String txtFile = beanRemote.generateCtsReturnTxt(gridDetail);
                if (txtFile.equals("")) {
                    this.setErrorMessage("File were not generated");
                    return;
                }
                txtFileName = txtFile;
                txtLink = true;
            }
            this.setErrorMessage("Now files are generated, you can download it by click on given link.");


            /*For XML Type Uncomment It*/

//            String fileName = beanRemote.generateCtsReturnXml(gridDetail); 
//            if (fileName.equals("")) {
//                this.setErrorMessage("File were not generated");
//                return;
//            }
//            xmlFileName = fileName;
//            xmlDoneFileName = fileName + ".done";
//            xmlLink = true;

            /*End Here*/

            /*For Zip Type Uncomment It*/

//            String zipFile = beanRemote.generateCtsZipReturnXml(gridDetail);
//            if (zipFile.equals("")) {
//                this.setErrorMessage("File were not generated");
//                return;
//            }
//            zipFileName = zipFile;
//            zipLink = true;

            /*End Here*/

        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void downloadXML(String fileType) {
        HttpServletResponse res = (HttpServletResponse) getHttpResponse();
        String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();
        try {
            String dirName = "/install/ATM/";
            if (fileType.equalsIgnoreCase("RET")) {
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + xmlFileName);
            } else if (fileType.equalsIgnoreCase("DONE")) {
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + xmlDoneFileName);
            } else if (fileType.equalsIgnoreCase("ZIP")) {
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + zipFileName);
            } else if (fileType.equalsIgnoreCase("TXT")) {
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + txtFileName);
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        this.setFileGenerationDt(getTodayDate());
        this.setXmlFileName("");
        this.setXmlDoneFileName("");
        this.setZipFileName("");
        this.setTxtFileName("");
        this.setXmlLink(false);
        this.setZipLink(false);
        this.setTxtLink(false);
        scheduleNoList = new ArrayList<SelectItem>();
        this.setScheduleFileName("");
        gridDetail = new ArrayList<ReturnDTO>();
        this.setFileType("0");
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

    public String getClearingType() {
        return clearingType;
    }

    public void setClearingType(String clearingType) {
        this.clearingType = clearingType;
    }

    public String getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(String scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getXmlFileName() {
        return xmlFileName;
    }

    public void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public boolean isXmlLink() {
        return xmlLink;
    }

    public void setXmlLink(boolean xmlLink) {
        this.xmlLink = xmlLink;
    }

    public List<SelectItem> getClearingTypeList() {
        return clearingTypeList;
    }

    public void setClearingTypeList(List<SelectItem> clearingTypeList) {
        this.clearingTypeList = clearingTypeList;
    }

    public List<SelectItem> getScheduleNoList() {
        return scheduleNoList;
    }

    public void setScheduleNoList(List<SelectItem> scheduleNoList) {
        this.scheduleNoList = scheduleNoList;
    }

    public String getScheduleFileName() {
        return scheduleFileName;
    }

    public void setScheduleFileName(String scheduleFileName) {
        this.scheduleFileName = scheduleFileName;
    }

    public List<ReturnDTO> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<ReturnDTO> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getXmlDoneFileName() {
        return xmlDoneFileName;
    }

    public void setXmlDoneFileName(String xmlDoneFileName) {
        this.xmlDoneFileName = xmlDoneFileName;
    }

    public boolean isZipLink() {
        return zipLink;
    }

    public void setZipLink(boolean zipLink) {
        this.zipLink = zipLink;
    }

    public String getZipFileName() {
        return zipFileName;
    }

    public void setZipFileName(String zipFileName) {
        this.zipFileName = zipFileName;
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

    public String getTxtFileName() {
        return txtFileName;
    }

    public void setTxtFileName(String txtFileName) {
        this.txtFileName = txtFileName;
    }

    public boolean isTxtLink() {
        return txtLink;
    }

    public void setTxtLink(boolean txtLink) {
        this.txtLink = txtLink;
    }

    public int getCtsSponsor() {
        return ctsSponsor;
    }

    public void setCtsSponsor(int ctsSponsor) {
        this.ctsSponsor = ctsSponsor;
    }
}
