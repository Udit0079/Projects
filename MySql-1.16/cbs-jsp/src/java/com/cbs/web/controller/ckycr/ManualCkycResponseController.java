/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * This form will used to upload the response of upload/download request
 * that is provided by Manual Request form.
 */
package com.cbs.web.controller.ckycr;

import com.cbs.facade.ckycr.CkycrProcessMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Properties;
import org.apache.myfaces.custom.fileupload.UploadedFile;

public class ManualCkycResponseController extends BaseBean {

    private String errMessage;
    private UploadedFile uploadedFile;
    private CkycrProcessMgmtFacadeRemote ckycrProcessMgmt = null;
    Properties props = null;

    public ManualCkycResponseController() {
        try {
            ckycrProcessMgmt = (CkycrProcessMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CkycrProcessMgmtFacade");
            props = new Properties();
            props.load(new FileReader("/opt/conf/wslocation.properties"));
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void uploadProcess() {
        this.setErrMessage("");
        if (uploadedFile == null) {
            this.setErrMessage("Please select appropriate file to upload !");
            return;
        }
        String uploadedFileName = uploadedFile.getName();
        if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
            this.setErrMessage("Please select appropriate file to upload !");
            return;
        }
        String fileContentType = uploadedFile.getContentType();
        System.out.println("The Content Tpye of the uploded file is >>>>>>>>>>>>>>>>>>>>>>>>>> " + fileContentType);
        if (!(fileContentType.equals("application/x-zip")
                || fileContentType.equals("application/zip")
                || fileContentType.equals("application/x-zip-compressed")
                || fileContentType.equals("application/octet-stream")
                || fileContentType.equals("text/plain"))) {
            this.setErrMessage("It is not a proper file type.");
            return;
        }
        try {
            String[] arr = ckycrProcessMgmt.getCbsParameterInfoCodes();
            if (!arr[1].equalsIgnoreCase(uploadedFileName.split("\\_")[0])) {
                this.setErrMessage("Uploaded file does not match with your corressponding institution code.");
                return;
            }
            String result = "";
            if (fileContentType.equals("text/plain") || fileContentType.equals("application/octet-stream")) { //For upload response
                File uploadResponseBkpDir = new File(props.getProperty("cbsUploadResponseBackupLocation"));
                if (!uploadResponseBkpDir.exists()) {
                    uploadResponseBkpDir.mkdirs();
                }
                File responseFile = new File(uploadResponseBkpDir.getCanonicalPath() + File.separatorChar + uploadedFileName);
                byte[] b = uploadedFile.getBytes();
                FileOutputStream fos = new FileOutputStream(responseFile);
                fos.write(b);
                fos.close();

                result = ckycrProcessMgmt.parseManualUploadResponse(responseFile, getUserName());
            } else if (fileContentType.equals("application/x-zip")
                    || fileContentType.equals("application/zip")
                    || fileContentType.equals("application/x-zip-compressed")
                    || fileContentType.equals("application/octet-stream")) { //For download response

                File downloadResponseDir = new File(props.getProperty("cbsDownloadResponseLocation"));
                if (!downloadResponseDir.exists()) {
                    downloadResponseDir.mkdirs();
                }
                File responseFile = new File(downloadResponseDir.getCanonicalPath() + File.separatorChar + uploadedFileName);
                byte[] b = uploadedFile.getBytes();
                FileOutputStream fos = new FileOutputStream(responseFile);
                fos.write(b);
                fos.close();

                result = ckycrProcessMgmt.parseManualDownloadResponse();
            }
            if (!result.equalsIgnoreCase("true")) {
                this.setErrMessage("Problem in uploading the response file.");
                return;
            }
            this.setErrMessage("Response file has been uploaded successfully.");
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setErrMessage("Click on browse button to upload the file...");
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
}
