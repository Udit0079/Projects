package com.cbs.web.controller.report.ledger;

import com.cbs.dto.report.SubDailyReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LedgerReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Zeeshan Waris
 */
public final class SubDailyReport extends BaseBean {

    private String message;
    private String calDate;
    LedgerReportFacadeRemote beanRemote;
    CommonReportMethodsRemote remote;
    private String orderBy;
    private List<SelectItem> orderByList;
    private String optTp;
    private List<SelectItem> optList;
    private String optDds;
    private List<SelectItem> optDdsList;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<SelectItem> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<SelectItem> orderByList) {
        this.orderByList = orderByList;
    }

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getOptList() {
        return optList;
    }

    public void setOptList(List<SelectItem> optList) {
        this.optList = optList;
    }

    public String getOptTp() {
        return optTp;
    }

    public void setOptTp(String optTp) {
        this.optTp = optTp;
    }

    public String getOptDds() {
        return optDds;
    }

    public void setOptDds(String optDds) {
        this.optDds = optDds;
    }

    public List<SelectItem> getOptDdsList() {
        return optDdsList;
    }

    public void setOptDdsList(List<SelectItem> optDdsList) {
        this.optDdsList = optDdsList;
    }

    public SubDailyReport() {
        try {
            beanRemote = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup("LedgerReportFacade");
            remote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            orderByList = new ArrayList<SelectItem>();
            optList = new ArrayList<SelectItem>();
            optDdsList = new ArrayList<SelectItem>();
            setCalDate(sdf.format(new Date()));
            orderByList.add(new SelectItem("0", "-- SELECT --"));
            orderByList.add(new SelectItem("ACNO", "A/C NO"));
            orderByList.add(new SelectItem("RECNO", "TRANSACTIONS"));

            optList.add(new SelectItem("Y", "WITH INC AND EXP HEAD"));
            optList.add(new SelectItem("N", "WITHOUT INC AND EXP HEAD"));

            optDdsList.add(new SelectItem("Y", "WITH DDS"));
            optDdsList.add(new SelectItem("N", "WITHOUT DDS"));

            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        setMessage("");
        try {
            if (calDate.equalsIgnoreCase("") || calDate == null) {
                message = "Please Fill Transaction Date";
                return null;
            }
            List<SubDailyReportPojo> subDailyReport = beanRemote.subDailyReport(getOrgnBrCode(), ymd.format(sdf.parse(calDate)), this.getOptTp(), this.getOptDds());
            if (!subDailyReport.isEmpty()) {
                String repName = "Subsidiary Day Book Report";
                String s[] = remote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                Map fillParams = new HashMap();
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", repName);
                fillParams.put("pReportDate", calDate);
                fillParams.put("pBankName", s[0]);
                fillParams.put("pBankAddress", s[1]);

                new ReportBean().jasperReport("subsidary_day_book", "text/html", new JRBeanCollectionDataSource(subDailyReport), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String pdfDownLoad() {
        setMessage("");
        try {
            if (calDate.equalsIgnoreCase("") || calDate == null) {
                message = "Please Fill Transaction Date";
                return null;
            }
            List<SubDailyReportPojo> subDailyReport = beanRemote.subDailyReport(getOrgnBrCode(), ymd.format(sdf.parse(calDate)), this.getOptTp(), this.getOptDds());
            if (!subDailyReport.isEmpty()) {
                String report = "Subsidiary Day Book Report";
                String s[] = remote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                Map fillParams = new HashMap();
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", calDate);
                fillParams.put("pBankName", s[0]);
                fillParams.put("pBankAddress", s[1]);

                new ReportBean().openPdf("subsidary_day_book_", "subsidary_day_book", new JRBeanCollectionDataSource(subDailyReport), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                setMessage("No data to print");
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
