/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.facade.misc.ATMMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.FileOutputStream;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.myfaces.custom.fileupload.UploadedFile;

public class RefundFileProcess extends BaseBean {

    private String msg;
    private String msgStyle;
    private UploadedFile uploadFile;
    private ATMMgmtFacadeRemote atmFacade;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgStyle() {
        return msgStyle;
    }

    public void setMsgStyle(String msgStyle) {
        this.msgStyle = msgStyle;
    }

    public UploadedFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadedFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public RefundFileProcess() {
        this.setMsg("Click on browse button to upload the Clearing file...");
        try {
            atmFacade = (ATMMgmtFacadeRemote) ServiceLocator.getInstance().lookup("ATMMgmtFacade");
        } catch (Exception ex) {
            this.setMsgStyle("error");
            this.setMsg(ex.getMessage());
        }
    }

    public void uploadProcess() {
        this.setMsgStyle("error");
        if (this.uploadFile == null) {
            this.setMsg("Please select appropriate file to upload");
            return;
        }
        try {
            String uploadedFileName = uploadFile.getName();    //With extension
            if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
                this.setMsg("Please select appropriate file to upload");
                return;
            }
            String fileContentType = uploadFile.getContentType();
            System.out.println("The Content Tpye of the uploded file is >>>>>>>>>>>>>>>>>>>>>>>>>> " + fileContentType);

            if (!(fileContentType.equals("text/csv")
                    || fileContentType.equals("application/octet-stream")
                    || fileContentType.equals("application/x-ns-proxy-autoconfig"))) {
                this.setMsg("Please select appropriate file to upload");
                return;
            }

            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            File directory = new File(context.getInitParameter("cbsTempLocation") + "/");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File writeFile = new File(directory.getCanonicalPath() + "/" + uploadedFileName);
            //Writing zip file into filesystem
            byte[] b = uploadFile.getBytes();
            FileOutputStream fos = new FileOutputStream(writeFile);
            fos.write(b);
            fos.close();
            
            String result = atmFacade.refundFileProcessing(uploadFile.getInputStream(), getUserName(), getOrgnBrCode(), uploadedFileName);
            setMsg(result);
            
        } catch (Exception ex) {
            this.msgStyle = "error";
            this.msg = ex.getMessage();

        }
    }

    public void refreshForm() {
        this.setMsg("Click on browse button to upload the Clearing file...");
    }

    public String exitForm() {
        return "case1";
    }
}
