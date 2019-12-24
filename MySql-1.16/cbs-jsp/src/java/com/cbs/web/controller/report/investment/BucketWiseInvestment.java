/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.investment;

import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.BucketWiseInvestmentPojo;
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
 * @author Admin
 */
public class BucketWiseInvestment extends BaseBean {

    public String errorMsg;
    public String asOnDate;
    private Date date = new Date();
    private String reportType;
    private List<SelectItem> reportTypeList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentReportMgmtFacade";
    List<BucketWiseInvestmentPojo> reportList = new ArrayList<BucketWiseInvestmentPojo>();

    public BucketWiseInvestment() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);

            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("Select", "--Select--"));
            reportTypeList.add(new SelectItem("F", "FD"));
            reportTypeList.add(new SelectItem("S", "Security"));

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void viewReport() {
        String bnkName = "", bnkAddress = "";

        try {

            if (asOnDate.equalsIgnoreCase("") || asOnDate == null) {
                setErrorMsg("Plese fill As On Date");
                return;
            }
            if (reportType.equalsIgnoreCase("Select")) {
                setErrorMsg("Plese Select Report Type");
                return;
            }

            String report = "Bucket Wise Investment Report";
            List bnkList = new ArrayList();
            bnkList = common.getBranchNameandAddress(getOrgnBrCode());
            if (bnkList.size() > 0) {
                bnkName = (String) bnkList.get(0);
                bnkAddress = (String) bnkList.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pbnkName", bnkName);
            fillParams.put("pbnkAddress", bnkAddress);
            fillParams.put("pReportName", report);
            fillParams.put("pReportDate", this.asOnDate);
            fillParams.put("pPrintBy", getUserName());
            reportList = remoteObj.getBucketWiseInvestmentdata(reportType, ymd.format(dmy.parse(asOnDate)));
            new ReportBean().jasperReport("BucketWiseInvestment", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Bucket Wise Investment Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
//            HttpSession httpSession = getHttpSession();
//            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        setErrorMsg("");
        setReportType("Select");
        asOnDate = dmy.format(new Date());
    }

    public String exitAction() {
        return "case1";
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
