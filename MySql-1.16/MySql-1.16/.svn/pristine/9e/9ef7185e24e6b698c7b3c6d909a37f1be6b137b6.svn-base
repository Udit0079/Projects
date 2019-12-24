/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.ho.NoticeToBorrowerPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountStatusSecurityFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Raza
 */
public class NoticeToBorrower extends BaseBean {

    private String msg;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String date;
    private Date dt = new Date();
    private String repType;
    private List<SelectItem> repTypeList;
    private String acType;
    private List<SelectItem> acTypeList;
    private String day;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String slabDay;
    private String displayAcNo;
    private String displayAcType;
    private String output;
    private String acNo;
    private String newAcNo;
    private boolean brCodeDisable;
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote beanFacade;
    private AccountStatusSecurityFacadeRemote statusMaintenanceFacade;
    private LoanReportFacadeRemote local;
    private FtsPostingMgmtFacadeRemote ftsPostRemote;
    private List<NoticeToBorrowerPojo> reportList = new ArrayList<NoticeToBorrowerPojo>();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public NoticeToBorrower() {
        setDate(dmy.format(new Date()));
        try {
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("Select", "--Select--"));
            reportTypeList.add(new SelectItem("C", "Consolidate"));
            reportTypeList.add(new SelectItem("L", "Letter Wise"));

            beanFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            statusMaintenanceFacade = (AccountStatusSecurityFacadeRemote) ServiceLocator.getInstance().lookup("AccountStatusSecurityFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            List brncode = new ArrayList();
            brncode = beanFacade.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("Select", "--Select--"));
            repTypeList.add(new SelectItem("A", "Account Type"));
            repTypeList.add(new SelectItem("I", "Individul"));

            output = "Account Type";
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("S", "--Select--"));
            displayAcNo = "none";

            String codeDay = common.getNoticeToBorrowerCode();
            slabDay = "No of days in Slab : " + codeDay;
            setSlabDay(slabDay);
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void changeFunction() {
        try {
            if (repType.equalsIgnoreCase("A")) {
                this.setOutput("Account Type");
                displayAcType = "";
                displayAcNo = "none";
                getAcTypeByAcNature();
            } else {
                this.setOutput("Account No.");
                displayAcNo = "";
                displayAcType = "none";
            }
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void acnoBlur() {
        try {
            String acno = ftsPostRemote.getNewAccountNumber(acNo);
            String acctype = ftsPostRemote.getAccountCode(acno);
            newAcNo = acno;
            List list = statusMaintenanceFacade.getCustNameAndStatus(acNo, acctype);
            if (list.isEmpty()) {
                throw new ApplicationException("Data does not exists");
            }
            Vector values = (Vector) list.get(0);
            String acStatus = values.get(1).toString();
            if (acStatus.contains("9") || acStatus.contains("11") || acStatus.contains("12") || acStatus.contains("13")) {
                setMsg("Account No. should not be npa and closed.");
                return;
            }
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void getAcTypeByAcNature() {
        try {
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("S", "--Select--"));
            List aTypeList = local.getDistinctAcCodeByAcNature();
            if (!aTypeList.isEmpty()) {
                for (int i = 0; i < aTypeList.size(); i++) {
                    Vector vec = (Vector) aTypeList.get(i);
                    acTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getSlabDay() {
        return slabDay;
    }

    public void setSlabDay(String slabDay) {
        this.slabDay = slabDay;
    }

    public boolean isBrCodeDisable() {
        return brCodeDisable;
    }

    public void setBrCodeDisable(boolean brCodeDisable) {
        this.brCodeDisable = brCodeDisable;
    }

    public String getDisplayAcNo() {
        return displayAcNo;
    }

    public void setDisplayAcNo(String displayAcNo) {
        this.displayAcNo = displayAcNo;
    }

    public String getDisplayAcType() {
        return displayAcType;
    }

    public void setDisplayAcType(String displayAcType) {
        this.displayAcType = displayAcType;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getNewAcNo() {
        return newAcNo;
    }

    public void setNewAcNo(String newAcNo) {
        this.newAcNo = newAcNo;
    }

    public void viewReport() {
        String report = "Notice to borrower";
        try {

            if (repType.equalsIgnoreCase("S")) {
                msg = "Please select report type !";
                return;
            }

            if (repType.equalsIgnoreCase("I")) {
                if (acNo == null || acNo.equalsIgnoreCase("")) {
                    msg = "Please enter A/c Number";
                    return;
                }
                if (acNo.length() != 12) {
                    msg = "Please Enter 12 Digit Account No";
                    return;
                }
            }

            if (repType.equalsIgnoreCase("A")) {
                if (this.getAcType().equalsIgnoreCase("S")) {
                    msg = "Please select A/c. Type";
                    return;
                }
            }

            if (date == null || date.equalsIgnoreCase("")) {
                setMsg("Please fill As on date");
                return;
            }
            if (!Validator.validateDate(date)) {
                setMsg("Please select Proper As on date ");
                return;
            }
            if (dmy.parse(date).after(getDt())) {
                setMsg("As on date should be less than current date !");
                return;
            }
            if (day == null || day.equalsIgnoreCase("")) {
                setMsg("Please fill days !");
                return;
            }
            if (reportType.equalsIgnoreCase("Select")) {
                setMsg("Please select Report Base !");
                return;
            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", this.date);
            fillParams.put("pPrintedBy", getUserName());

            reportList = local.getNoticeToBorrowerData(branchCode, repType, acNo, acType, ymd.format(dmy.parse(date)), day);

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exists !");
            }
            if (reportType.equalsIgnoreCase("C")) {
                new ReportBean().jasperReport("NoticeToBorrower", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Notice to borrower");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else {
                new ReportBean().jasperReport("NoticeToCustomer", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Notice to borrower");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
            
            refreshForm();
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void refreshForm() {
        setMsg("");
        setDay("");
        setReportType("Select");
        setDate(dmy.format(new Date()));
        setAcNo("");
        setAcType("S");
        setNewAcNo("");

    }

    public String exitAction() {
        return "case1";
    }
}
