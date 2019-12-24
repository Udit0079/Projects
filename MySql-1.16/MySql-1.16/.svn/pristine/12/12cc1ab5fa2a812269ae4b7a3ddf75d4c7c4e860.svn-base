/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.DayActivityPojo;
import com.cbs.facade.other.DailyProcessManagementRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
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
 * @author Admin
 */
public class DayActivityNonFinancialDetail extends BaseBean {

    private String message;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    Date asOnDt = new Date();
    private TdReceiptManagementFacadeRemote RemoteCode;
    private final String jndiHomeName = "DailyProcessManagementFacade";
    private DailyProcessManagementRemote beanRemote = null;
    private CommonReportMethodsRemote common;
    //private List<DayActivityPojo> reportList = new ArrayList<DayActivityPojo>();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public DayActivityNonFinancialDetail() {
        try {

            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            beanRemote = (DailyProcessManagementRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List brncode = new ArrayList();
            brncode = common.getAlphacodeAllAndBranch(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(1).toString().length() < 2 ? "0" + brnVector.get(1).toString() : brnVector.get(1).toString(), brnVector.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }

    }

    public void viewReport() {
        try {
            List<DayActivityPojo> dayActivityReport = beanRemote.getDayActivityReport(this.branchCode, ymd.format(asOnDt));
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParam = new HashMap();
            fillParam.put("pReportName", "Day Actitvity Report");
            fillParam.put("pPrintedDate", dmy.format(this.asOnDt));
            fillParam.put("pPrintedBy", getUserName());
            fillParam.put("pBankName", branchNameandAddress.get(0));
            fillParam.put("pBankAdd", branchNameandAddress.get(1));

            new ReportBean().jasperReport("dayActivityReport", "text/html",
                    new JRBeanCollectionDataSource(dayActivityReport), fillParam, "Day Activity Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void pdfDownLoad() {
        try {
            List<DayActivityPojo> dayActivityReport = beanRemote.getDayActivityReport(this.branchCode, ymd.format(asOnDt));
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParam = new HashMap();
            fillParam.put("pReportName", "Day Actitvity Report");
            fillParam.put("pPrintedDate", dmy.format(this.asOnDt));
            fillParam.put("pPrintedBy", getUserName());
            fillParam.put("pBankName", branchNameandAddress.get(0));
            fillParam.put("pBankAdd", branchNameandAddress.get(1));

            new ReportBean().openPdf("Day Actitvity Report_" + ymd.format(asOnDt), "dayActivityReport", new JRBeanCollectionDataSource(dayActivityReport), fillParam);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Day Activity Report");

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void refresh() {
        setMessage("");
        setAsOnDt(asOnDt);
    }

    public String exitAction() {
        refresh();
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Date getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(Date asOnDt) {
        this.asOnDt = asOnDt;
    }
}
