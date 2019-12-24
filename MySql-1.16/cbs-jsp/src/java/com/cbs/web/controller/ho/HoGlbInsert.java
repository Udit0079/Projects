/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho;

import com.cbs.facade.ho.BatchDeletionRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.InputStream;
import org.apache.myfaces.custom.fileupload.UploadedFile;

/**
 *
 * @author Ankit Verma
 */
public class HoGlbInsert extends BaseBean {

    private UploadedFile uploadedFile;
    private String msg;
    private Double liaAmt;
    private Double assAmt;
    private String orgnId;
    private String date1;
    BatchDeletionRemote beanRemote;

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getOrgnId() {
        return orgnId;
    }

    public void setOrgnId(String orgnId) {
        this.orgnId = orgnId;
    }

    public HoGlbInsert() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Double getAssAmt() {
        return assAmt;
    }

    public void setAssAmt(Double assAmt) {
        this.assAmt = assAmt;
    }

    public Double getLiaAmt() {
        return liaAmt;
    }

    public void setLiaAmt(Double liaAmt) {
        this.liaAmt = liaAmt;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void uploadFile() {
        this.setMsg("");
        String array[] = null;
        if (uploadedFile == null) {
            this.setMsg("Please select appropriate file to upload");
            return;
        } else if (!uploadedFile.getContentType().equalsIgnoreCase("text/plain")) {
            this.setMsg("Please Select a Appropriate Text File.");
            return;
        } else if (uploadedFile.getName().equalsIgnoreCase("")) {
            this.setMsg("Please specify file name");
            return;
        } else if (!uploadedFile.getName().substring(0, 4).equalsIgnoreCase("SEC-")) {
            this.setMsg("File name is not correct.");
            return;
        }
        try {
            InputStream input = uploadedFile.getInputStream();
            int ch;
            StringBuffer strContent = new StringBuffer("");
            while ((ch = input.read()) != -1) {
                strContent.append((char) ch);

            }
            input.close();
            if (strContent.length() == 0) {
                this.setMsg("File Is Empty.");
                return;
            }
            array = strContent.toString().split(",");
            beanRemote = (BatchDeletionRemote) ServiceLocator.getInstance().lookup("BatchDeletionBean");
            String msge = beanRemote.hoGlbInsert(array, getUserName());
            setMsg(msge);
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void refreshPage() {
        setMsg("");
    }

    public String exitAction() {
        return "case1";
    }
}
