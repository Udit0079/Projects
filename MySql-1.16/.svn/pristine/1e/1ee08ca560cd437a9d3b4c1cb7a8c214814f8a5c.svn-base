/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.dto.SSSRejectDto;
import com.cbs.facade.neftrtgs.SSSFileGeneratorFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.apache.myfaces.custom.fileupload.UploadedFile;

public class SSSFileUpload extends BaseBean {

    private String errorMessage;
    private String schemeCode;
    private List<SelectItem> schemeList;
    private String vendor;
    private List<SelectItem> vendorList;
    private String fileId;
    private UploadedFile uploadedFile;
    private SSSFileGeneratorFacadeRemote remote = null;
    private CommonReportMethodsRemote reportRemote = null;
    private SSSFileGeneratorFacadeRemote sssRemote = null;

    public SSSFileUpload() {

        vendorList = new ArrayList<SelectItem>();
        try {
            this.setErrorMessage("Upload social security scheme files...");
            remote = (SSSFileGeneratorFacadeRemote) ServiceLocator.getInstance().lookup("SSSFileGeneratorFacade");
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            sssRemote = (SSSFileGeneratorFacadeRemote) ServiceLocator.getInstance().lookup("SSSFileGeneratorFacade");
            initData();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            schemeList = new ArrayList<SelectItem>();
            schemeList.add(new SelectItem("--Select--"));
            List list = reportRemote.getRefRecList("215");
            for (int i = 0; i < list.size(); i++) {
                Vector vec = (Vector) list.get(i);
                schemeList.add(new SelectItem(vec.get(0).toString(), vec.get(1).toString()));
            }
            vendorList = new ArrayList<SelectItem>();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onChangeScheme() {
        this.setErrorMessage("");
        try {
            vendorList = new ArrayList<SelectItem>();
            vendorList.add(new SelectItem("--Select--"));
            List list = sssRemote.getVendors(schemeCode);
            for (int i = 0; i < list.size(); i++) {
                Vector vec = (Vector) list.get(i);
                vendorList.add(new SelectItem(vec.get(0).toString(), vec.get(1).toString()));
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void uploadProcess() {
        this.setErrorMessage("");
        if (schemeCode == null || vendor == null || fileId == null || uploadedFile == null
                || schemeCode.equals("") || vendor.equals("") || fileId.equals("")
                || schemeCode.equalsIgnoreCase("--Select--") || vendor.equalsIgnoreCase("--Select--")) {
            this.setErrorMessage("Please fill all fields !");
            return;
        }

        String uploadedFileName = uploadedFile.getName();
        if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
            this.setErrorMessage("Please select appropriate file name to upload !");
            return;
        }

        if (!uploadedFileName.toLowerCase().contains("outerpd")) { //outerpd - PMSBY and UIC rejected file.
            this.setErrorMessage("This is not a valid file to upload.");
            return;
        }

        String fileContentType = uploadedFile.getContentType();
        System.out.println("File Content Type " + fileContentType);
        if (!(fileContentType.equals("text/csv")
                || fileContentType.equals("application/octet-stream")
                || fileContentType.equals("application/vnd.ms-excel"))) {
            this.setErrorMessage("It is not a proper file type.");
            return;
        }

        try {
            String alphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphaCode.equalsIgnoreCase("HO")) {
                this.setErrorMessage("This file will be upload only from HO.");
                return;
            }
            //Uploaded File Writing.
            ServletContext context = (ServletContext) getFacesContext().getCurrentInstance().
                    getExternalContext().getContext();
//            String directory = context.getInitParameter("cts");
            String directory = context.getInitParameter("cbsFiles");
            File dir = new File(directory + "/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File inwFile = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);
            byte[] b = uploadedFile.getBytes();
            FileOutputStream fos = new FileOutputStream(inwFile);
            fos.write(b);
            fos.close();
            //File Parsing And Processing.
            List<SSSRejectDto> pojoList = new ArrayList<SSSRejectDto>();
            if (schemeCode.equalsIgnoreCase("PMSBY") && vendor.equalsIgnoreCase("UIC")
                    && uploadedFileName.toLowerCase().contains("outerpd")) {
                pojoList = ParseFileUtil.parsePmSBYRejection(inwFile);
                String result = remote.uploadPmSBYRejection(schemeCode.trim(), vendor.trim(), fileId.trim(),
                        uploadedFileName.substring(0, uploadedFileName.indexOf(".")).trim(),
                        pojoList, getUserName(), getTodayDate());
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrorMessage(result);
                    return;
                }
                this.setErrorMessage("File has been uploaded successfully.");
            } else {
                this.setErrorMessage("Please select appropriate values and file to upload.");
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setSchemeCode("--Select--");
        this.setVendor("--Select--");
        this.setFileId("");
        this.setErrorMessage("Upload social security scheme files...");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    //Getter And Setter
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
}
