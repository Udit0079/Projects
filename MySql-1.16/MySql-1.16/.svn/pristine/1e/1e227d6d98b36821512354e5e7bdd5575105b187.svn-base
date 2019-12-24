/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.facade.clg.NpciClearingMgmtFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.PrintFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.myfaces.custom.fileupload.UploadedFile;

public class UploadClearingFile extends BaseBean {

    private String msg;
    private String msgStyle;
    private UploadedFile uploadFile;
    private String chequeNo;
    private String sortCode;
    private String sanNo;
    private String transactionCode;
    private double amount;
    private String returnCode;
    private int ctsSponsor = 0;
    private PrintFacadeRemote beanRemote = null;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private NpciClearingMgmtFacadeRemote npciClgRemote = null;

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

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getSanNo() {
        return sanNo;
    }

    public void setSanNo(String sanNo) {
        this.sanNo = sanNo;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public UploadClearingFile() {
        this.setMsg("Click on browse button to upload the Clearing file...");
        try {
            beanRemote = (PrintFacadeRemote) ServiceLocator.getInstance().lookup("PrintManagementFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            npciClgRemote = (NpciClearingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciClearingMgmtFacade");
            ctsSponsor = ftsRemote.getCodeForReportName("CTS-SPONSOR");
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
            if (ctsSponsor == 2) { //Khatri
                if (!(fileContentType.equals("text/xml")
                        || fileContentType.equals("application/xml"))) {
                    this.setMsg("Please select appropriate file to upload");
                    return;
                }
            } else { //Ammco
                if (!(fileContentType.equals("text/plain")
                        || fileContentType.equals("application/octet-stream")
                        || fileContentType.equals("application/x-ns-proxy-autoconfig"))) {
                    this.setMsg("Please select appropriate file to upload");
                    return;
                }
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
            //Now return file processing
            if (ctsSponsor == 2) {
                JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.dto.npci.cts.ow.reverse.FileHeader.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                com.cbs.dto.npci.cts.ow.reverse.FileHeader doc = (com.cbs.dto.npci.cts.ow.reverse.FileHeader) jaxbUnmarshaller.unmarshal(writeFile);
                List<com.cbs.dto.npci.cts.ow.reverse.FileHeader.Item> items = doc.getItem();
                List<String> resultList = npciClgRemote.updateOutwardClgReturn(items, getUserName(), getTodayDate(), "NPCI-MODE");
                String returnResult = "";
                if (resultList.isEmpty()) {
                    this.setMsg("Outward return marking has been completed.");
                    return;
                } else {
                    for (int i = 0; i < resultList.size(); i++) {
                        if (returnResult.equals("")) {
                            returnResult = resultList.get(i);
                        } else {
                            returnResult = returnResult + "," + resultList.get(i);
                        }
                    }
                }
                this.setMsg("Return marking has been completed and Non-Returned Item Seq Nos are->" + returnResult);
            } else {
                int res = beanRemote.uploadClearingFile(writeFile);
                if (res > 0) {
                    this.msg = "Successfully uploaded.";
                }
            }
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
