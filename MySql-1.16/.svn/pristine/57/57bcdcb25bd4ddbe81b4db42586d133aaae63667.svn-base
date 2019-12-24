/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.facade.other.PrintFacadeRemote;
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

public class ATMFileController extends BaseBean {

    private String errorMessage;
    private String fileGenerationDt;
    private String issuerFileName;
    private boolean issuerLink;
    private String onUsFileName;
    private boolean onUsLink;
    private String acquireFileName;
    private boolean acquireLink;
    private String reconFileType;
    private String switchVendor;
    private List reconFileTypeList;
    private PrintFacadeRemote beanRemote = null;
    private final String jndiHomeName = "PrintManagementFacade";
    private CommonReportMethodsRemote commonReport;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String dirPath;

    public String getReconFileType() {
        return reconFileType;
    }

    public void setReconFileType(String reconFileType) {
        this.reconFileType = reconFileType;
    }

    public List getReconFileTypeList() {
        return reconFileTypeList;
    }

    public void setReconFileTypeList(List reconFileTypeList) {
        this.reconFileTypeList = reconFileTypeList;
    }

    public ATMFileController() {
        btnRefreshAction();
        try {
            beanRemote = (PrintFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List list = beanRemote.getReconFileType();
            if (!list.isEmpty()) {
                reconFileTypeList = new ArrayList<SelectItem>();
                reconFileTypeList.add(new SelectItem("SELECT", "--Select--"));
                for (int i = 0; i < list.size(); i++) {
                    Vector ele1 = (Vector) list.get(i);
                    reconFileTypeList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                }
            }
            this.switchVendor = commonReport.getCodeFromCbsParameterInfo("ATM-SWITCH-VENDOR");
            String osName = System.getProperty("os.name");
            if (osName.equalsIgnoreCase("Linux")) {
                dirPath = "/install/ATM/";
            } else {
                dirPath = "C:\\ATM\\";
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void downloadIssuer() {
        HttpServletResponse res = (HttpServletResponse) getHttpResponse();
        String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();
        try {
            String osName = System.getProperty("os.name");
            if (this.switchVendor.equalsIgnoreCase("SARVATRA") && reconFileType.equalsIgnoreCase("IMPS")) {
                if (osName.equalsIgnoreCase("Linux")) {
                    dirPath = "/install/IMPS/";
                } else {
                    dirPath = "C:\\IMPS\\";
                }
            }else{
                if (osName.equalsIgnoreCase("Linux")) {
                    dirPath = "/install/ATM/";
                } else {
                    dirPath = "C:\\ATM\\";
                }
            }
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirPath + "&fileName=" + issuerFileName);
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void downloadOnUs() {
        HttpServletResponse res = (HttpServletResponse) getHttpResponse();
        String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();
        try {
            String osName = System.getProperty("os.name");
            if (this.switchVendor.equalsIgnoreCase("SARVATRA") && reconFileType.equalsIgnoreCase("IMPS")) {
                if (osName.equalsIgnoreCase("Linux")) {
                    dirPath = "/install/IMPS/";
                } else {
                    dirPath = "C:\\IMPS\\";
                }
            }else{
                if (osName.equalsIgnoreCase("Linux")) {
                    dirPath = "/install/ATM/";
                } else {
                    dirPath = "C:\\ATM\\";
                }
            }
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirPath + "&fileName=" + onUsFileName);
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void downloadAcquire() {
        HttpServletResponse res = (HttpServletResponse) getHttpResponse();
        String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();
        try {
            String osName = System.getProperty("os.name");
             if (this.switchVendor.equalsIgnoreCase("SARVATRA") && reconFileType.equalsIgnoreCase("IMPS")) {
                if (osName.equalsIgnoreCase("Linux")) {
                    dirPath = "/install/IMPS/";
                } else {
                    dirPath = "C:\\IMPS\\";
                }
            }else{
                if (osName.equalsIgnoreCase("Linux")) {
                    dirPath = "/install/ATM/";
                } else {
                    dirPath = "C:\\ATM\\";
                }
            }
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirPath + "&fileName=" + acquireFileName);
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void processAction() {
        this.setErrorMessage("");
        try {
            if (this.reconFileType.equalsIgnoreCase("SELECT")) {
                this.setErrorMessage("Please select Recon File Type !");
                return;
            }
            if (this.fileGenerationDt == null || this.fileGenerationDt.equals("")) {
                this.setErrorMessage("Please fill File Generation Date.");
                return;
            }

            if (!new Validator().validateDate_dd_mm_yyyy(fileGenerationDt)) {
                this.setErrorMessage("Please fill proper date.");
                return;
            }
            if (this.switchVendor.equalsIgnoreCase("FINACUS")) {
                if (this.reconFileType.equalsIgnoreCase("RECON")) {   //Total Length is 576
                    List<String> files = beanRemote.generateFinacusReconFiles(ymd.format(dmy.parse(this.fileGenerationDt)), getUserName());
                    if (files.isEmpty()) {
                        this.setErrorMessage("Files were not generated.");
                        handleFileGrid();
                    } else {
                        issuerFileName = files.get(0);
                        issuerLink = true;
                        if (files.size() == 2) {
                            onUsFileName = files.get(1);
                            onUsLink = true;
                        }
                        if (files.size() == 3) {
                            onUsFileName = files.get(1);
                            onUsLink = true;
                            acquireFileName = files.get(2);
                            acquireLink = true;
                        }
                        this.setErrorMessage("Now files are generated, you can download it by click on given link.");
                        this.setFileGenerationDt("");
                    }
                } else if (this.reconFileType.equalsIgnoreCase("POS")) {
                    List<String> files = beanRemote.generatePosAtmFinacusFiles(ymd.format(dmy.parse(this.fileGenerationDt)), getUserName());
                    if (files.isEmpty()) {
                        this.setErrorMessage("Files were not generated.");
                        handleFileGrid();
                    } else {
                        issuerFileName = files.get(0);
                        issuerLink = true;
                        this.setErrorMessage("Now files are generated, you can download it by click on given link.");
                        this.setFileGenerationDt("");
                    }
                } else if (this.reconFileType.equalsIgnoreCase("IMPS")) {
                    List<String> files = beanRemote.generateImpsOwTxnFile(ymd.format(dmy.parse(this.fileGenerationDt)), getUserName());
                    if (files.isEmpty()) {
                        this.setErrorMessage("Files were not generated.");
                        handleFileGrid();
                    } else {
                        issuerFileName = files.get(0);
                        issuerLink = true;
                        this.setErrorMessage("Now files are generated, you can download it by click on given link.");
                        this.fileGenerationDt = "";
                    }
                }
            } else {
                if (this.reconFileType.equalsIgnoreCase("RECON")) {
                    List<String> files = beanRemote.generateCbsAtmReconFiles(ymd.format(dmy.parse(this.fileGenerationDt)), getUserName());
                    if (files.isEmpty()) {
                        this.setErrorMessage("Files were not generated.");
                        handleFileGrid();
                    } else {
                        issuerFileName = files.get(0);
                        issuerLink = true;
                        if (files.size() == 2) {
                            onUsFileName = files.get(1);
                            onUsLink = true;
                        }
                        if (files.size() == 3) {
                            onUsFileName = files.get(1);
                            onUsLink = true;
                            acquireFileName = files.get(2);
                            acquireLink = true;
                        }
                        this.setErrorMessage("Now files are generated, you can download it by click on given link.");
                        this.setFileGenerationDt("");
                    }
                } else if (this.reconFileType.equalsIgnoreCase("POS")) {
                    List<String> files = beanRemote.generatePosAtmReconFiles(ymd.format(dmy.parse(this.fileGenerationDt)), getUserName());
                    if (files.isEmpty()) {
                        this.setErrorMessage("Files were not generated.");
                        handleFileGrid();
                    } else {
                        issuerFileName = files.get(0);
                        issuerLink = true;
                        this.setErrorMessage("Now files are generated, you can download it by click on given link.");
                        this.setFileGenerationDt("");
                    }
                } else if (this.reconFileType.equalsIgnoreCase("IMPS")) {
                    List<String> files = beanRemote.generateSarvatraImpsReconFile(ymd.format(dmy.parse(this.fileGenerationDt)), getUserName());
                    if (files.isEmpty()) {
                        this.setErrorMessage("Files were not generated.");
                        handleFileGrid();
                    } else {
                        acquireFileName = files.get(0);
                        acquireLink = true;
                        if (files.size() == 2) {
                            issuerFileName = files.get(1);
                            issuerLink = true;
                        }
                        if (files.size() == 3) {
                            issuerFileName = files.get(1);
                            issuerLink = true;
                            onUsFileName = files.get(2);
                            onUsLink = true;

                        }
                        this.setErrorMessage("Now files are generated, you can download it by click on given link.");
                        this.setFileGenerationDt("");
                    }
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        this.setReconFileType("SELECT");
        this.setFileGenerationDt(getTodayDate());
        this.setIssuerFileName("");
        this.setIssuerLink(false);
        this.setOnUsFileName("");
        this.setOnUsLink(false);
        this.setAcquireFileName("");
        this.setAcquireLink(false);
    }

    public void handleFileGrid() {
        this.setIssuerFileName("");
        this.setIssuerLink(false);
        this.setOnUsFileName("");
        this.setOnUsLink(false);
        this.setAcquireFileName("");
        this.setAcquireLink(false);
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    public String getAcquireFileName() {
        return acquireFileName;
    }

    public void setAcquireFileName(String acquireFileName) {
        this.acquireFileName = acquireFileName;
    }

    public boolean isAcquireLink() {
        return acquireLink;
    }

    public void setAcquireLink(boolean acquireLink) {
        this.acquireLink = acquireLink;
    }

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

    public String getIssuerFileName() {
        return issuerFileName;
    }

    public void setIssuerFileName(String issuerFileName) {
        this.issuerFileName = issuerFileName;
    }

    public boolean isIssuerLink() {
        return issuerLink;
    }

    public void setIssuerLink(boolean issuerLink) {
        this.issuerLink = issuerLink;
    }

    public String getOnUsFileName() {
        return onUsFileName;
    }

    public void setOnUsFileName(String onUsFileName) {
        this.onUsFileName = onUsFileName;
    }

    public boolean isOnUsLink() {
        return onUsLink;
    }

    public void setOnUsLink(boolean onUsLink) {
        this.onUsLink = onUsLink;
    }

    public String getSwitchVendor() {
        return switchVendor;
    }

    public void setSwitchVendor(String switchVendor) {
        this.switchVendor = switchVendor;
    }
}
