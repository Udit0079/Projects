/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.OutwardClearingManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.PrintFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.hrms.web.utils.WebUtil;
import java.io.File;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ROHIT KRISHNA
 */
public class ClearingTextFilePrint {

    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private String calDate;
    private HttpServletRequest req;
    private boolean saveLink;
    private String clgTxtFileName;
    private String clgTxtFileNameHDFC;
    private String clgDirectoryName;
    private List<String> txtFileNameList;
    private String reportOption;
    private List<SelectItem> reportOptionList;
    private final String jndiHomeName = "PrintManagementFacade";
    private PrintFacadeRemote beanRemote = null;
    private OutwardClearingManagementFacadeRemote outwardClgRemote = null;
    private final String jndiHomeNameOutward = "OutwardClearingManagementFacade";
    private FtsPostingMgmtFacadeRemote ftsPosting;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public boolean isSaveLink() {
        return saveLink;
    }

    public void setSaveLink(boolean saveLink) {
        this.saveLink = saveLink;
    }

    public String getClgDirectoryName() {
        return clgDirectoryName;
    }

    public void setClgDirectoryName(String clgDirectoryName) {
        this.clgDirectoryName = clgDirectoryName;
    }

    public String getClgTxtFileName() {
        return clgTxtFileName;
    }

    public void setClgTxtFileName(String clgTxtFileName) {
        this.clgTxtFileName = clgTxtFileName;
    }

    public String getClgTxtFileNameHDFC() {
        return clgTxtFileNameHDFC;
    }

    public void setClgTxtFileNameHDFC(String clgTxtFileNameHDFC) {
        this.clgTxtFileNameHDFC = clgTxtFileNameHDFC;
    }

    public String getReportOption() {
        return reportOption;
    }

    public void setReportOption(String reportOption) {
        this.reportOption = reportOption;
    }

    public List<SelectItem> getReportOptionList() {
        return reportOptionList;
    }

    public void setReportOptionList(List<SelectItem> reportOptionList) {
        this.reportOptionList = reportOptionList;
    }

    public List<String> getTxtFileNameList() {
        return txtFileNameList;
    }

    public void setTxtFileNameList(List<String> txtFileNameList) {
        this.txtFileNameList = txtFileNameList;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    /**
     * Creates a new instance of ClearingTextFilePrint
     */
    public ClearingTextFilePrint() {
        try {
            req = getRequest();
            beanRemote = (PrintFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            outwardClgRemote = (OutwardClearingManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOutward);
            ftsPosting = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);

            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            Date date = new Date();
            txtFileNameList = new ArrayList<String>();
            this.setTodayDate(sdf.format(date));
            this.setCalDate(sdf.format(date));
            this.setSaveLink(false);
            this.setErrorMessage("");
            this.setMessage("");
            reportOptionOnload();
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void reportOptionOnload() {
        try {
            List resultList = new ArrayList();
            resultList = beanRemote.reportOption();
            if (resultList.isEmpty()) {
                reportOptionList = new ArrayList<SelectItem>();
                reportOptionList.add(new SelectItem("--Select--"));
            } else {
                reportOptionList = new ArrayList<SelectItem>();
                reportOptionList.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    reportOptionList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
                //reportOptionList.add(new SelectItem("owcts", "Outward CTS File"));
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void saveToFile() {
        try {
            this.setSaveLink(false);
            this.setErrorMessage("");
            this.setMessage("");
            txtFileNameList = new ArrayList<String>();
            System.out.println("Report Option is = " + this.reportOption);
            if (this.reportOption.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select report option.");
                return;
            }
            if (this.calDate == null) {
                this.setErrorMessage("Please enter date.");
                return;
            }
            if (!Validator.validateDate(this.calDate)) {
                this.setErrorMessage("Please select proper Date");
                return;
            }
            if (this.reportOption.equalsIgnoreCase("244") || this.reportOption.equalsIgnoreCase("224")) {
                File dir = null;
                if (System.getProperty("os.name").equalsIgnoreCase("Linux")) {
                    dir = new File("/install/ClgTxtFile/");
                } else {
                    dir = new File("C:/ClgTxtFile/");
                }
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String fileNameRes = "";
                if (ftsPosting.getBankCode().equalsIgnoreCase("kcbl")) {
                    fileNameRes = outwardClgRemote.generateOutwardCtsFileKcbl(dir.getCanonicalPath(), this.getOrgnBrCode(), ymd.format(sdf.parse(this.calDate)), this.getOrgnBrCode(), this.getUserName());
                } else {
                    fileNameRes = outwardClgRemote.generateOutwardCtsFile(dir.getCanonicalPath(), this.getOrgnBrCode(), ymd.format(sdf.parse(this.calDate)), this.getOrgnBrCode(), this.getUserName());
                }
                if (fileNameRes.equalsIgnoreCase("") || fileNameRes.equals(null)) {
                    this.setErrorMessage("Data does exist corresponding given input or not verified !");
                } else {
                    this.setErrorMessage("File generated successfully !");
                    clgTxtFileName = fileNameRes;
                    txtFileNameList.add(clgTxtFileName);
                    this.setSaveLink(true);
                }
            } else {
                String res = beanRemote.createFile(Integer.parseInt(this.reportOption), ymd.format(sdf.parse(this.calDate)), this.orgnBrCode);
                if (res == null || res.equalsIgnoreCase("")) {
                    this.setErrorMessage("System error occured.");
                    return;
                } else {
                    if (res.contains("!")) {
                        this.setErrorMessage(res);
                        return;
                    } else {
                        this.setMessage(res);
                        clgTxtFileName = res.substring(res.indexOf("-") + 1).trim();
                        String[] tmpArr = clgTxtFileName.split(",");
                        for (int i = 0; i < tmpArr.length; i++) {
                            System.out.println(tmpArr[i]);
                            txtFileNameList.add(tmpArr[i]);
                        }
                        this.setSaveLink(true);
                    }
                }
            }

        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        try {
            Date date = new Date();
            this.setErrorMessage("");
            this.setSaveLink(false);
            this.setMessage("");
            this.setCalDate(sdf.format(date));
            this.reportOption = "--Select--";
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception ex) {
            setErrorMessage(ex.getLocalizedMessage());
        }
        return "case1";
    }

    public void downloadFile() {
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String ctxPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            String dirName = "";
            String osName = System.getProperty("os.name");
            if (osName.equalsIgnoreCase("Linux")) {
                dirName = "/install/ClgTxtFile/";
            } else {
                dirName = "C:\\ClgTxtFile\\";
            }
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + fileName);
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }
}
