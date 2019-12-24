package com.cbs.web.controller.report.ledger;

import com.cbs.dto.report.IncomeExpenditurePojo;
import com.cbs.facade.report.LedgerReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Zeeshan Waris
 */
public final class IncomeExpenditure extends BaseBean {

    private String message;
    private String calDate;
    LedgerReportFacadeRemote beanRemote;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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

    public IncomeExpenditure() {
        setCalDate(sdf.format(new Date()));

    }

    public String printAction() {
        setMessage("");
        try {
            if (calDate.equalsIgnoreCase("") || calDate == null) {
                message = "Please Fill Transaction Date";
                return null;
            }
            String dt2[] = calDate.split("/");
            String indate = dt2[2] + dt2[1] + dt2[0];
            beanRemote = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup("LedgerReportFacade");
            List<IncomeExpenditurePojo> incomeExpenditure = beanRemote.incomeExpenditure(indate, getOrgnBrCode());
            if (!incomeExpenditure.isEmpty()) {
                String repName = "INCOME EXPENDITURE REPORT";
                Map fillParams = new HashMap();
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", repName);
                fillParams.put("pReportDate", calDate);
                new ReportBean().jasperReport("income_exp_report", "text/html", new JRBeanCollectionDataSource(incomeExpenditure), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String btnPdfAction() {
        setMessage("");
        try {
            if (calDate.equalsIgnoreCase("") || calDate == null) {
                message = "Please Fill Transaction Date";
                return null;
            }
            String dt2[] = calDate.split("/");
            String indate = dt2[2] + dt2[1] + dt2[0];
            beanRemote = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup("LedgerReportFacade");
            List<IncomeExpenditurePojo> incomeExpenditure = beanRemote.incomeExpenditure(indate, getOrgnBrCode());
            if (!incomeExpenditure.isEmpty()) {
                String report = "INCOME EXPENDITURE REPORT";
                Map fillParams = new HashMap();
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", calDate);
                new ReportBean().openPdf("income_exp_report_", "income_exp_report", new JRBeanCollectionDataSource(incomeExpenditure), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpRequest().getSession();
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
