/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.OtherMasterFacadeRemote;
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
 * @author root
 */
public class OnlineAadharRegistrationDetail extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String repOption;
    private List<SelectItem> repOptionList;
    private String frdt;
    private String todt;
    private OtherMasterFacadeRemote remoteFacade;
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote RemoteCode;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<ThresoldTransactionInfoPojo> reportList = new ArrayList<ThresoldTransactionInfoPojo>();

    public OnlineAadharRegistrationDetail() {
        try {
            frdt = getTodayDate();
            todt = getTodayDate();
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");

            branchList = new ArrayList<SelectItem>();
            List list = RemoteCode.getBranchCodeList(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchList.add(new SelectItem(vtr.get(0).toString().length() < 2 ? "0" + vtr.get(0).toString() : vtr.get(0).toString(), vtr.get(1).toString()));
            }
            repOptionList = new ArrayList<SelectItem>();
            repOptionList.add(new SelectItem("0", "--Select--"));
            repOptionList.add(new SelectItem("R", "Received"));
            repOptionList.add(new SelectItem("S", "Secondary"));
            repOptionList.add(new SelectItem("P", "Primary"));

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void btnHtmlAction() {
        setMessage("");
        String report = "Online Aadhar Registration Detail";
        try {

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
            if (repOption == null || repOption.equalsIgnoreCase("0")) {
                setMessage("Please Select Report Option !");
                return;
            }
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintedDate", frdt + " to " + todt);
            if (repOption.equalsIgnoreCase("R")) {
                fillParams.put("pReportOpt", "Received");
            } else if (repOption.equalsIgnoreCase("S")) {
                fillParams.put("pReportOpt", "Secondary");
            } else if (repOption.equalsIgnoreCase("P")) {
                fillParams.put("pReportOpt", "Primary");
            }
            reportList = remoteFacade.getOlineAadharRegistrationData(ymd.format(dmy.parse(frdt)), ymd.format(dmy.parse(todt)), branch, repOption);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist  !!!");
            }
            new ReportBean().jasperReport("OnlineAadharRegistrationInfo", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Online Aadhar Registration Detail");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void viewPdfReport() {
        setMessage("");
        String report = "Online Aadhar Registration Detail";
        try {

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
            if (repOption == null || repOption.equalsIgnoreCase("0")) {
                setMessage("Please Select Report Option !");
                return;
            }
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintedDate", frdt + " to " + todt);
            if (repOption.equalsIgnoreCase("R")) {
                fillParams.put("pReportOpt", "Received");
            } else if (repOption.equalsIgnoreCase("S")) {
                fillParams.put("pReportOpt", "Secondary");
            } else if (repOption.equalsIgnoreCase("P")) {
                fillParams.put("pReportOpt", "Primary");
            }
            reportList = remoteFacade.getOlineAadharRegistrationData(ymd.format(dmy.parse(frdt)), ymd.format(dmy.parse(todt)), branch, repOption);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist  !!!");
            }

            new ReportBean().openPdf("Online Aadhar Registration Detail_" + ymd.format(dmy.parse(todt)), "OnlineAadharRegistrationInfo", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Online Aadhar Registration Detail");


        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void btnRefreshAction() {
        setMessage("");
        setFrdt(getTodayDate());
        setTodt(getTodayDate());
        setRepOption("0");
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

    public String getRepOption() {
        return repOption;
    }

    public void setRepOption(String repOption) {
        this.repOption = repOption;
    }

    public List<SelectItem> getRepOptionList() {
        return repOptionList;
    }

    public void setRepOptionList(List<SelectItem> repOptionList) {
        this.repOptionList = repOptionList;
    }
}
