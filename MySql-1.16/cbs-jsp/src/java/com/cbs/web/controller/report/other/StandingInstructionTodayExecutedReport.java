package com.cbs.web.controller.report.other;

import com.cbs.dto.report.StandingInstructionTodayExecutedReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
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
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Zeeshan Waris
 */
public final class StandingInstructionTodayExecutedReport extends BaseBean {

    private String message, instructionType, transactionType;
    Date calFromDate = new Date();
    private String branchCode;
    private List<SelectItem> branchCodeList;
    OtherReportFacadeRemote beanRemote;
    private boolean acTypeDisable;
    private List<SelectItem> instructionTypeList;
    private List<SelectItem> transactionTypeList;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private CommonReportMethodsRemote common;

    public boolean isAcTypeDisable() {
        return acTypeDisable;
    }

    public void setAcTypeDisable(boolean acTypeDisable) {
        this.acTypeDisable = acTypeDisable;
    }

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(String instructionType) {
        this.instructionType = instructionType;
    }

    public List<SelectItem> getInstructionTypeList() {
        return instructionTypeList;
    }

    public void setInstructionTypeList(List<SelectItem> instructionTypeList) {
        this.instructionTypeList = instructionTypeList;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public List<SelectItem> getTransactionTypeList() {
        return transactionTypeList;
    }

    public void setTransactionTypeList(List<SelectItem> transactionTypeList) {
        this.transactionTypeList = transactionTypeList;
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

    public StandingInstructionTodayExecutedReport() {
        try {
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            instructionTypeList = new ArrayList<SelectItem>();
            instructionTypeList.add(new SelectItem("TRANSACTION", "TRANSACTION"));
            transactionTypeList = new ArrayList<SelectItem>();
            transactionTypeList.add(new SelectItem("DEBIT", "DEBIT"));
            transactionTypeList.add(new SelectItem("CREDIT", "CREDIT"));

            List brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            List<StandingInstructionTodayExecutedReportPojo> StandingInstructionTodayExecutedReport = beanRemote.StandingInstructionTodayExecutedReport(ymdFormat.format(calFromDate), "TRAN", branchCode);
            if (StandingInstructionTodayExecutedReport == null) {
                setMessage("System error occurred");
                return null;
            }
            if (!StandingInstructionTodayExecutedReport.isEmpty()) {
                String repName = "Standing Instruction Today Executed Report";
                Map fillParams = new HashMap();
                fillParams.put("pAsOnDate", sdf.format(calFromDate));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", "Today Executed Report");
                fillParams.put("pReportDate", sdf.format(calFromDate));
                if (transactionType.equalsIgnoreCase("CREDIT")) {
                    new ReportBean().jasperReport("TodayExecutedReportCr", "text/html", new JRBeanCollectionDataSource(StandingInstructionTodayExecutedReport), fillParams, repName);
                    return "report";
                } else {
                    new ReportBean().jasperReport("TodayExecutedReportDr", "text/html", new JRBeanCollectionDataSource(StandingInstructionTodayExecutedReport), fillParams, repName);
                    return "report";
                }
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public void viewPdfReport() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            List<StandingInstructionTodayExecutedReportPojo> StandingInstructionTodayExecutedReport = beanRemote.StandingInstructionTodayExecutedReport(ymdFormat.format(calFromDate), "TRAN", branchCode);
            if (StandingInstructionTodayExecutedReport == null) {
                setMessage("System error occurred");
                return;
            }
            if (!StandingInstructionTodayExecutedReport.isEmpty()) {
                String repName = "Standing Instruction Today Executed Report";
                Map fillParams = new HashMap();
                fillParams.put("pAsOnDate", sdf.format(calFromDate));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", "Today Executed Report");
                fillParams.put("pReportDate", sdf.format(calFromDate));
                if (transactionType.equalsIgnoreCase("CREDIT")) {
                    new ReportBean().openPdf("Standing Instruction Today Executed Report_" + ymdFormat.format(calFromDate), "TodayExecutedReportCr", new JRBeanCollectionDataSource(StandingInstructionTodayExecutedReport), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "Standing Instruction Today Executed Report");
                } else {
                    new ReportBean().openPdf("Standing Instruction Today Executed Report_" + ymdFormat.format(calFromDate), "TodayExecutedReportDr", new JRBeanCollectionDataSource(StandingInstructionTodayExecutedReport), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "Standing Instruction Today Executed Report");
                }
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshAction() {
        try {
            setMessage("");
            calFromDate = new Date();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
