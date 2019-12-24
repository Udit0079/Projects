package com.cbs.web.controller.report.clg;

import com.cbs.dto.report.InwardClearingPojo;
import com.cbs.dto.report.InwardClearingTodayDatePojo;
import com.cbs.facade.report.ClgReportFacadeRemote;
import com.cbs.facade.txn.OtherTransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
public final class InwardClearingReport extends BaseBean {

    private String message;
    Date calFromDate = new Date();
    private ClgReportFacadeRemote beanRemote;
    private String report, type, clear;
    private List<SelectItem> typeList;
    private List<SelectItem> reportList;
    private List<SelectItem> clearingList;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private OtherTransactionManagementFacadeRemote otherRemote = null;

    public String getClear() {
        return clear;
    }

    public void setClear(String clear) {
        this.clear = clear;
    }

    public List<SelectItem> getClearingList() {
        return clearingList;
    }

    public void setClearingList(List<SelectItem> clearingList) {
        this.clearingList = clearingList;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public List<SelectItem> getReportList() {
        return reportList;
    }

    public void setReportList(List<SelectItem> reportList) {
        this.reportList = reportList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
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

    public InwardClearingReport() {
        try {
            beanRemote = (ClgReportFacadeRemote) ServiceLocator.getInstance().lookup("ClgReportFacade");
            otherRemote = (OtherTransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup("OtherTransactionManagementFacade");
            getCircleType();
            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("NON CTS", "NON CTS"));
            clearingList = new ArrayList<SelectItem>();
            clearingList.add(new SelectItem("I/W CLG PASSED", "I/W CLG PASSED"));
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public List getCircleType() {
        List circleTyp = new ArrayList();
        this.setMessage("");
        reportList = new ArrayList<SelectItem>();
        try {
            circleTyp = otherRemote.getClearingOption();
            if (!circleTyp.isEmpty()) {
                for (int i = 0; i < circleTyp.size(); i++) {
                    Vector vecForcircleTyp = (Vector) circleTyp.get(i);
                    reportList.add(new SelectItem(vecForcircleTyp.get(0).toString(), vecForcircleTyp.get(1).toString()));
                }
            } else {
                reportList.add(new SelectItem("0", "--Select--"));
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return circleTyp;
    }

    public String printAction() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (report.equalsIgnoreCase("0")) {
                message = "Please select Report";
                return null;
            }
            String repName = "INWARD CLEARING REPORT";
            if (sdf.format(calFromDate).equals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))) {
                if (clear.equalsIgnoreCase("I/W Clg Entered") || clear.equalsIgnoreCase("I/W CLG PASSED")) {
                    List<InwardClearingTodayDatePojo> inwardClearingTodayDate = beanRemote.inwardClearingTodayDate(report, ymdFormat.format(calFromDate), getOrgnBrCode(), clear, type);
                    if (inwardClearingTodayDate == null) {
                        setMessage("System error occurred");
                        return null;
                    }
                    if (!inwardClearingTodayDate.isEmpty()) {
                        //      Collections.sort(inwardClearingTodayDate);
                        Map fillParams = new HashMap();
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pReportName", repName);
                        fillParams.put("pReportDate", sdf.format(calFromDate));
                        new ReportBean().jasperReport("IW_CLG", "text/html", new JRBeanCollectionDataSource(inwardClearingTodayDate), fillParams, repName);
                        return "report";
                    } else {
                        setMessage("No data to print");
                    }
                }
            } else {
                if (clear.equalsIgnoreCase("I/W Clg Entered") || clear.equalsIgnoreCase("I/W CLG PASSED")) {
                    List<InwardClearingPojo> inwardClearing = beanRemote.inwardClearing(report, ymdFormat.format(calFromDate), getOrgnBrCode(), clear, type);
                    if (inwardClearing == null) {
                        setMessage("System error occurred");
                        return null;
                    }
                    if (!inwardClearing.isEmpty()) {
                        Collections.sort(inwardClearing);
                        Map fillParams = new HashMap();
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pReportName", repName);
                        fillParams.put("pReportDate", sdf.format(calFromDate));
                        new ReportBean().jasperReport("IW_CLG", "text/html", new JRBeanCollectionDataSource(inwardClearing), fillParams, repName);
                        return "report";
                    } else {
                        setMessage("No data to print");
                    }
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String btnPdfAction() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (report.equalsIgnoreCase("0")) {
                message = "Please select Report";
                return null;
            }
            String repName = "INWARD CLEARING REPORT";
            if (sdf.format(calFromDate).equals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))) {
                if (clear.equalsIgnoreCase("I/W Clg Entered") || clear.equalsIgnoreCase("I/W CLG PASSED")) {
                    List<InwardClearingTodayDatePojo> inwardClearingTodayDate = beanRemote.inwardClearingTodayDate(report, ymdFormat.format(calFromDate), getOrgnBrCode(), clear, type);
                    if (inwardClearingTodayDate == null) {
                        setMessage("System error occurred");
                        return null;
                    }
                    if (!inwardClearingTodayDate.isEmpty()) {
                        //      Collections.sort(inwardClearingTodayDate);
                        Map fillParams = new HashMap();
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pReportName", repName);
                        fillParams.put("pReportDate", sdf.format(calFromDate));
                        new ReportBean().openPdf("INWARD CLEARING REPORT_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "IW_CLG", new JRBeanCollectionDataSource(inwardClearingTodayDate), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", repName);
                    } else {
                        setMessage("No data to print");
                    }
                }
            } else {
                if (clear.equalsIgnoreCase("I/W Clg Entered") || clear.equalsIgnoreCase("I/W CLG PASSED")) {
                    List<InwardClearingPojo> inwardClearing = beanRemote.inwardClearing(report, ymdFormat.format(calFromDate), getOrgnBrCode(), clear, type);
                    if (inwardClearing == null) {
                        setMessage("System error occurred");
                        return null;
                    }
                    if (!inwardClearing.isEmpty()) {
                        Collections.sort(inwardClearing);
                        Map fillParams = new HashMap();
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pReportName", repName);
                        fillParams.put("pReportDate", sdf.format(calFromDate));
                        new ReportBean().openPdf("INWARD CLEARING REPORT_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "IW_CLG", new JRBeanCollectionDataSource(inwardClearing), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getHttpSession();
                        httpSession.setAttribute("ReportName", repName);
                    } else {
                        setMessage("No data to print");
                    }
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public void refreshAction() {
        try {
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
