/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.dto.NpciInwardDto;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.SignUtil;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.apache.myfaces.custom.fileupload.UploadedFile;

public class NpciFileUploadBean extends BaseBean {

    private String errMessage;
    private String fileType;
    private List<SelectItem> fileTypeList;
    private UploadedFile uploadedFile;
    private NpciMgmtFacadeRemote npciFacade = null;
    private CommonReportMethodsRemote reportRemote = null;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdOne = new SimpleDateFormat("ddMMyyyy");

    public NpciFileUploadBean() {
        try {
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            npciFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            fileTypeList = new ArrayList<>();
            fileTypeList.add(new SelectItem("0", "--Select--"));
            List dataList = reportRemote.getRefRecList("234"); //Npci Upload File Type
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                fileTypeList.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public boolean validations() {
        if (this.fileType == null || this.fileType.equals("0") || this.fileType.equals("")) {
            this.setErrMessage("Please select file type.");
            return false;
        }
        if (uploadedFile == null) {
            this.setErrMessage("Please select appropriate file to upload !");
            return false;
        }
        return true;
    }

    public void uploadProcess() {
        this.setErrMessage("");
        try {
            if (validations()) {
                String uploadedFileName = uploadedFile.getName();
                String fileContentType = uploadedFile.getContentType();
                System.out.println("Uploaded File Name is-->" + uploadedFileName + "::::File content type " + fileContentType);

                if (!(uploadedFileName.toLowerCase().contains("av-cbdt")
                        && uploadedFileName.toLowerCase().contains("inp.txt"))) {
                    this.setErrMessage("This is not a valid uploaded file.");
                    return;
                }
                if (!(fileContentType.equals("text/plain")
                        || fileContentType.equals("application/octet-stream")
                        || fileContentType.equals("text/xml"))) {
                    this.setErrMessage("It is not a proper file type.");
                    return;
                }
                String alphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
                if (!alphaCode.equalsIgnoreCase("HO")) {
                    this.setErrMessage("File will only upload from HO.");
                    return;
                }

                ServletContext context = (ServletContext) getFacesContext().getCurrentInstance().getExternalContext().getContext();
                String directory = context.getInitParameter("cbsFiles");
                File dir = new File(directory + "/");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File inwFile = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);
                List<NpciInwardDto> pojoList = new ArrayList<>();
                try {
                    byte[] b = uploadedFile.getBytes();
                    FileOutputStream fos = new FileOutputStream(inwFile);
                    fos.write(b);
                    fos.close();
                    if (ftsRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                        new SignUtil().parseSignedFile(dir.getCanonicalPath() + File.separatorChar + uploadedFileName, true, dir.getCanonicalPath() + File.separatorChar + uploadedFileName, false);
                    }
                    String fileDate = uploadedFileName.split("-")[5].trim();
                    String fileSeqNo = uploadedFileName.split("-")[6].trim();
                    pojoList = ParseFileUtil.parseNpciCbdtInwardTxtFile(inwFile, fileDate, fileSeqNo);
                } catch (Exception e) {
                    this.setErrMessage("Problem in parsing the file::" + e.getMessage());
                }
                //Processing of inward files.
                String result = npciFacade.npciCbdtInwardUpload(pojoList, getOrgnBrCode(), getUserName(), getTodayDate());
                if (!result.equals("true")) {
                    this.setErrMessage(result);
                    return;
                }
                this.setErrMessage("Data has been uploaded successfully.");
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setErrMessage("");
        this.setFileType("0");
        this.setErrMessage("Please select file type.");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    //Getter And Setter
    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
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
}
