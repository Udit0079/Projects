/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.locker;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.pojo.FolioLedgerPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class LockerOperationDetail extends BaseBean implements Serializable {

    private String todayDate;
    private String userName;
    private String errorMsg;
    private String branchCode;
    private String lockerNo;
    private List<SelectItem> branchCodeList;
    private String reportOption;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private List<SelectItem> reportOptionList;
    private Date frmDt;
    private String firstDisplay = "none";
    private boolean disableLetter;
    private boolean reportFlag;
    private Date toDt;
    private HttpServletRequest req;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private Date date = new Date();
    private String brnCode;
    private OtherReportFacadeRemote local;
    private CommonReportMethodsRemote common;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public Date getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(Date frmDt) {
        this.frmDt = frmDt;
    }

    public boolean isDisableLetter() {
        return disableLetter;
    }

    public void setDisableLetter(boolean disableLetter) {
        this.disableLetter = disableLetter;
    }

    public Date getToDt() {
        return toDt;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }

    public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
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

    public String getLockerNo() {
        return lockerNo;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public void setLockerNo(String lockerNo) {
        this.lockerNo = lockerNo;
    }

    public String getFirstDisplay() {
        return firstDisplay;
    }

    public void setFirstDisplay(String firstDisplay) {
        this.firstDisplay = firstDisplay;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public LockerOperationDetail() {
        try {
            todayDate = dmy.format(date);
            local = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            frmDt = date;
            toDt = date;
            setDisableLetter(true);
            List brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            reportOptionList = new ArrayList<>();
            reportOptionList.add(new SelectItem("0", "--Select--"));
            reportOptionList.add(new SelectItem("O", "Operate"));
            reportOptionList.add(new SelectItem("U", "Un Operate"));
            reportTypeList = new ArrayList<>();
            reportTypeList.add(new SelectItem("0", "--Select--"));
            reportTypeList.add(new SelectItem("A", "ALL"));
            reportTypeList.add(new SelectItem("I", "Individual"));
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void onTypeBlur() {
        try {
            if (reportType.equalsIgnoreCase("I")) {
                this.firstDisplay = " ";
            } else {
                this.firstDisplay = "none";
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void onBlur() {
        if (reportOption.equalsIgnoreCase("U")) {
            setDisableLetter(false);
            this.reportFlag = true;
        } else {
            setDisableLetter(true);
            this.reportFlag = false;
        }
    }

    public String PrintLetter() {
        if (validate()) {
            try {
                setDisableLetter(true);
                setReportFlag(true);
                List<FolioLedgerPojo> resultList = local.lockerOperationDetail(ymd.format(frmDt), ymd.format(toDt), branchCode, reportOption, lockerNo, reportType);
                if (!resultList.isEmpty()) {
                    Map fillParams = new HashMap();
                    fillParams.put("image", "/opt/images/logo.png");
                    fillParams.put("pRname", "Reminder");
                    fillParams.put("date", getTodayDate());
                    new ReportBean().openPdf("Reminder" + ymd.format(frmDt) + "" + ymd.format(toDt), "LockerReminderUnoperationLetter", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "Reminder");
                } else {
                    errorMsg = "Data does not lie";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String viewReport() {
        if (validate()) {
            try {
                List<FolioLedgerPojo> resultList = local.lockerOperationDetail(ymd.format(frmDt), ymd.format(toDt), branchCode, reportOption, lockerNo, reportType);
                if (!resultList.isEmpty()) {
                    List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                    Map fillParams = new HashMap();
                    fillParams.put("pPby", getUserName());
                    if (reportOption.equalsIgnoreCase("O")) {
                        fillParams.put("pRname", "Locker Operation Report");
                    } else {
                        fillParams.put("pRname", "Locker Un-Operation Report");
                    }
                    fillParams.put("pfromDate", dmy.format(frmDt) + " - " + dmy.format(toDt));
                    fillParams.put("toDate", dmy.format(toDt));
                    fillParams.put("pbankName", brNameAndAddList.get(0).toString());
                    fillParams.put("pbankAddress", brNameAndAddList.get(1).toString());
                    fillParams.put("prType", reportOption);  
                    if (reportOption.equalsIgnoreCase("O")) {
                        fillParams.put("pHeader", "PresenceOf");
                        fillParams.put("pOption", "Operate");
                    } else {
                        fillParams.put("pHeader", "Issue Date");
                        fillParams.put("pOption", "Un Operate");
                    }
                    new ReportBean().jasperReport("LockerOperationInfo", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Locker Operation Report");
                    return "report";
                } else {
                    errorMsg = "Data does not lie";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public void viewPdfReport() {
        if (validate()) {
            try {
                List<FolioLedgerPojo> resultList = local.lockerOperationDetail(ymd.format(frmDt), ymd.format(toDt), branchCode, reportOption, lockerNo, reportType);
                if (!resultList.isEmpty()) {
                    List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                    Map fillParams = new HashMap();
                    fillParams.put("pPby", getUserName());
                    if (reportOption.equalsIgnoreCase("O")) {
                        fillParams.put("pRname", "Locker Operation Report");
                    } else {
                        fillParams.put("pRname", "Locker Un-Operation Report");
                    }
                    fillParams.put("pfromDate", dmy.format(frmDt) + " - " + dmy.format(toDt));
                    fillParams.put("toDate", dmy.format(toDt));
                    fillParams.put("pbankName", brNameAndAddList.get(0).toString());
                    fillParams.put("pbankAddress", brNameAndAddList.get(1).toString());
                    fillParams.put("prType", reportOption);
                    if (reportOption.equalsIgnoreCase("O")) {
                        fillParams.put("pHeader", "PresenceOf");
                        fillParams.put("pOption", "Operate");
                    } else {
                        fillParams.put("pHeader", "Issue Date");
                        fillParams.put("pOption", "Un Operate");
                    }
                    new ReportBean().openPdf("LockerOperationInfo_" + ymd.format(frmDt) + "" + ymd.format(toDt), "LockerOperationInfo", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "Locker Operation Report");
                } else {
                    errorMsg = "Data does not lie";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validate() {
        if (frmDt == null) {
            errorMsg = "Please enter from date";
            return false;
        }
        if (toDt == null) {
            errorMsg = "Please enter to date";
            return false;
        }
        if (frmDt.after(toDt)) {
            errorMsg = "Please from date should be less then to date";
            return false;
        }

        if (reportOption == null || reportOption.equalsIgnoreCase("0")) {
            errorMsg = "Please Select Report Option";
            return false;
        }
        if (reportType == null || reportType.equalsIgnoreCase("0")) {
            errorMsg = "Please Select Report Type";
            return false;
        }
        if (reportType.equalsIgnoreCase("I") & lockerNo.equalsIgnoreCase("")) {
            errorMsg = "Please enter the Locker Number";
            return false;
        }
        return true;
    }

    public String exitAction() {
        return "case1";
    }

    public void refresh() {
        try {
            this.setReportFlag(false);
            setDisableLetter(true);
            this.setErrorMsg("");
            this.setReportOption("--Select--");
            this.setReportType("--Select--");
            setBranchCode("All");
            firstDisplay = "none";
            setLockerNo("");
            setToDt(sdf.parse(getTodayDate()));
            setFrmDt(sdf.parse(getTodayDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
