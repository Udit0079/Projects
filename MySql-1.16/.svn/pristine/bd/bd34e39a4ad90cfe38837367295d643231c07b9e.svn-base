/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.ThresoldTransactionInfoPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class ThresoldTransactionInfo extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String frdt;
    private String todt;
    private String dateOption;
    private List<SelectItem> dateOptionList;
    private boolean disDt;
    private MiscReportFacadeRemote remoteFacade;
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote RemoteCode;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<ThresoldTransactionInfoPojo> reportList = new ArrayList<ThresoldTransactionInfoPojo>();

    public ThresoldTransactionInfo() {
        try {
            frdt = getTodayDate();
            todt = getTodayDate();
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");

            branchList = new ArrayList<SelectItem>();
            List list = RemoteCode.getBranchCodeList(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchList.add(new SelectItem(vtr.get(0).toString().length() < 2 ? "0" + vtr.get(0).toString() : vtr.get(0).toString(), vtr.get(1).toString()));
            }

            dateOptionList = new ArrayList<SelectItem>();
            dateOptionList.add(new SelectItem("S", "--Select--"));
            dateOptionList.add(new SelectItem("B", "Date Between"));
            dateOptionList.add(new SelectItem("A", "Date As On"));

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void optionProcess() {
        setMessage("");
        if (dateOption.equalsIgnoreCase("A")) {
            this.disDt = true;
        } else {
            this.disDt = false;
        }
    }

    public void btnHtmlAction() {
        setMessage("");
        String report = "Threshold Detail Report";
        try {

            if (dateOption == null || dateOption.equalsIgnoreCase("S")) {
                setMessage("Please select Report Option");
                return;
            }

            if (!dateOption.equalsIgnoreCase("A")) {
                if (frdt == null || frdt.equalsIgnoreCase("")) {
                    setMessage("Please fill From Date");
                    return;
                }

                if (!Validator.validateDate(frdt)) {
                    setMessage("Please select Proper from date ");
                    return;
                }
                if (dmy.parse(frdt).after(dmy.parse(getTodayDate()))) {
                    setMessage("From Date should be less than current date !");
                    return;
                }
            }

            if (todt == null || todt.equalsIgnoreCase("")) {
                setMessage("Please fill To Date");
                return;
            }

            if (!Validator.validateDate(todt)) {
                setMessage("Please select Proper to date ");
                return;
            }
            if (dmy.parse(todt).after(dmy.parse(getTodayDate()))) {
                setMessage("To Date should be less than current date !");
                return;
            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintedDate", frdt + " to " + todt);
            String jrxmlName = "Thresold_Account_Detail";
            if (dateOption.equalsIgnoreCase("A")) {
                jrxmlName = "Thresold_Account_Detail";
            } else {
                jrxmlName = "Thresold_Detail";
            }
            reportList = remoteFacade.getThresoldData(branch, ymd.format(dmy.parse(frdt)), ymd.format(dmy.parse(todt)), dateOption);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist  !!!");
            }

            new ReportBean().jasperReport(jrxmlName, "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Threshold Detail");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void viewPdfReport() {
        setMessage("");
        String report = "Threshold Detail Report";
        try {

            if (dateOption == null || dateOption.equalsIgnoreCase("S")) {
                setMessage("Please select Report Option");
                return;
            }

            if (!dateOption.equalsIgnoreCase("A")) {
                if (frdt == null || frdt.equalsIgnoreCase("")) {
                    setMessage("Please fill From Date");
                    return;
                }

                if (!Validator.validateDate(frdt)) {
                    setMessage("Please fill Proper from date ");
                    return;
                }
                if (dmy.parse(frdt).after(dmy.parse(getTodayDate()))) {
                    setMessage("From Date should be less than current date !");
                    return;
                }
            }

            if (todt == null || todt.equalsIgnoreCase("")) {
                setMessage("Please fill To Date");
                return;
            }

            if (!Validator.validateDate(todt)) {
                setMessage("Please fill Proper to date ");
                return;
            }
            if (dmy.parse(todt).after(dmy.parse(getTodayDate()))) {
                setMessage("To Date should be less than current date !");
                return;
            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintedDate", frdt + " to " + todt);
            String jrxmlName = "Thresold_Account_Detail";
            if (dateOption.equalsIgnoreCase("A")) {
                jrxmlName = "Thresold_Account_Detail";
            } else {
                jrxmlName = "Thresold_Detail";
            }
            reportList = remoteFacade.getThresoldData(branch, ymd.format(dmy.parse(frdt)), ymd.format(dmy.parse(todt)), dateOption);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist  !!!");
            }

            new ReportBean().openPdf("Thresold Detail_" + ymd.format(dmy.parse(todt)), jrxmlName, new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Threshold Detail");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void btnRefreshAction() {
        setMessage("");
        setDateOption("S");
        setFrdt(getTodayDate());
        setTodt(getTodayDate());
    }

    public String btnExitAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrdt() {
        return frdt;
    }

    public void setFrdt(String frdt) {
        this.frdt = frdt;
    }

    public String getTodt() {
        return todt;
    }

    public void setTodt(String todt) {
        this.todt = todt;
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

    public String getDateOption() {
        return dateOption;
    }

    public void setDateOption(String dateOption) {
        this.dateOption = dateOption;
    }

    public List<SelectItem> getDateOptionList() {
        return dateOptionList;
    }

    public void setDateOptionList(List<SelectItem> dateOptionList) {
        this.dateOptionList = dateOptionList;
    }

    public boolean isDisDt() {
        return disDt;
    }

    public void setDisDt(boolean disDt) {
        this.disDt = disDt;
    }
}
