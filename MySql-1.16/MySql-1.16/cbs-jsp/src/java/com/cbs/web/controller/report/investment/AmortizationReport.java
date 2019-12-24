/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.investment;

import com.cbs.dto.report.AmortizationReportPojo;
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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class AmortizationReport extends BaseBean {

    public String errorMsg;
    public String asonDate;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote otherFacade;
    List <AmortizationReportPojo> reportList = new ArrayList<AmortizationReportPojo>();

    public String getAsonDate() {
        return asonDate;
    }

    public void setAsonDate(String asonDate) {
        this.asonDate = asonDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public AmortizationReport() {
        try {
            asonDate = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void viewReport() {
        String bnkName = "", bnkAddress = "";

        try {
            String asOnDt = ymd.format(dmy.parse(asonDate));
            String report = "Amortization Report";
            List bnkList = new ArrayList();
            bnkList = common.getBranchNameandAddress(getOrgnBrCode());
            if (bnkList.size() > 0) {
                bnkName = (String) bnkList.get(0);
                bnkAddress = (String) bnkList.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pbnkName", bnkName);
            fillParams.put("pbnkAddress", bnkAddress);
            fillParams.put("preportName", report);
            fillParams.put("preportDate", this.asonDate);
            fillParams.put("pPrintBy", getUserName());
            reportList = otherFacade.getAmortizationReport(asOnDt);
            new ReportBean().jasperReport("AmortizationReport", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Amortization Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        setErrorMsg("");
        asonDate = dmy.format(new Date());

    }

    public String exitAction() {
        return "case1";
    }
}
