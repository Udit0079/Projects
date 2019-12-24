/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.OutwardClearingManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.PrintFacadeRemote;
import com.cbs.facade.report.ClgReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

public class ClearingTextFilePrintBranch extends BaseBean {

    private String message;
    private String errorMessage;
    private String calDate;
    private boolean saveLink;
    private String clgTxtFileName;
    private String clgDirectoryName;
    private List<String> txtFileNameList;
    private String branch;
    private List<SelectItem> branchList;
    private String reportOption;
    private List<SelectItem> reportOptionList;
    private String clgType;
    private List<SelectItem> clgTypeList;
    private String fileName;
    private final String jndiHomeName = "PrintManagementFacade";
    private PrintFacadeRemote beanRemote = null;
    private final String jndiHomeNameFTS = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote beanR = null;
    private OutwardClearingManagementFacadeRemote outwardClgRemote = null;
    private ClgReportFacadeRemote clgRptRemote = null;
    private final String jndiHomeNameOutward = "OutwardClearingManagementFacade";
    private CommonReportMethodsRemote reportRemote;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public ClearingTextFilePrintBranch() {
        try {
            beanRemote = (PrintFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            outwardClgRemote = (OutwardClearingManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOutward);
            clgRptRemote = (ClgReportFacadeRemote) ServiceLocator.getInstance().lookup("ClgReportFacade");
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanR = (FtsPostingMgmtFacadeRemote)ServiceLocator.getInstance().lookup(jndiHomeNameFTS);
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

    public void reportOptionOnload() {
        try {
            List resultList = new ArrayList();
            resultList = clgRptRemote.getBranchList();
            branchList = new ArrayList<SelectItem>();
            branchList.add(new SelectItem("--Select--"));
            branchList.add(new SelectItem("ALL", "ALL"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                branchList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
            reportOptionList = new ArrayList<SelectItem>();
            reportOptionList.add(new SelectItem("--Select--"));
            reportOptionList.add(new SelectItem("owtf", "Outward  File"));

            clgTypeList = new ArrayList<SelectItem>();
            clgTypeList.add(new SelectItem("A", "CTS"));
            clgTypeList.add(new SelectItem("G", "Non CTS"));
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
            if (this.branch.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select branch.");
                return;
            }
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
            if (this.reportOption.equalsIgnoreCase("owtf")) {
                File dir = null;
                if (System.getProperty("os.name").equalsIgnoreCase("Linux")) {
                    dir = new File("/opt/CBS-TEMP/");
                } else {
                    dir = new File("C:/CBS-TEMP/");
                }
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String bankCode = beanR.getBankCode();
                if(bankCode.equalsIgnoreCase("INDR")){
                  String fileNameResponse = outwardClgRemote.generateclringOutwardTxtFile(dir.getCanonicalPath(), this.branch,
                        ymd.format(sdf.parse(this.calDate)), this.getOrgnBrCode(), this.getUserName(), clgType);
                  if (fileNameResponse.equalsIgnoreCase("") || fileNameResponse.equals(null)) {
                    this.setErrorMessage("Data does exist corresponding given input or not verified !");
                } else {
                    this.setErrorMessage("File generated successfully !");
                    clgTxtFileName = fileNameResponse;
                    txtFileNameList.add(clgTxtFileName);
                    this.setSaveLink(true);
                }
               }else{
                String fileNameRes = outwardClgRemote.generateOutwardTxtFile(dir.getCanonicalPath(), this.branch,
                        ymd.format(sdf.parse(this.calDate)), this.getOrgnBrCode(), this.getUserName(), clgType);
                if (fileNameRes.equalsIgnoreCase("") || fileNameRes.equals(null)) {
                    this.setErrorMessage("Data does exist corresponding given input or not verified !");
                } else {
                    this.setErrorMessage("File generated successfully !");
                    clgTxtFileName = fileNameRes;
                    txtFileNameList.add(clgTxtFileName);
                    this.setSaveLink(true);
                }
                }
            } else {
                String res = beanRemote.createFile(Integer.parseInt(this.reportOption), ymd.format(sdf.parse(this.calDate)), getOrgnBrCode());
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
                dirName = "/opt/CBS-TEMP/";
            } else {
                dirName = "C:\\CBS-TEMP\\";
            }
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + fileName);
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    //*------------------------------------- Getters And Setters ---------------------------------------------*//
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public boolean isSaveLink() {
        return saveLink;
    }

    public void setSaveLink(boolean saveLink) {
        this.saveLink = saveLink;
    }

    public String getClgTxtFileName() {
        return clgTxtFileName;
    }

    public void setClgTxtFileName(String clgTxtFileName) {
        this.clgTxtFileName = clgTxtFileName;
    }

    public String getClgDirectoryName() {
        return clgDirectoryName;
    }

    public void setClgDirectoryName(String clgDirectoryName) {
        this.clgDirectoryName = clgDirectoryName;
    }

    public List<String> getTxtFileNameList() {
        return txtFileNameList;
    }

    public void setTxtFileNameList(List<String> txtFileNameList) {
        this.txtFileNameList = txtFileNameList;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getClgType() {
        return clgType;
    }

    public void setClgType(String clgType) {
        this.clgType = clgType;
    }

    public List<SelectItem> getClgTypeList() {
        return clgTypeList;
    }

    public void setClgTypeList(List<SelectItem> clgTypeList) {
        this.clgTypeList = clgTypeList;
    }
}
