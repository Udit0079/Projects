/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.SundrySuspencePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.ReportBean;
import com.hrms.web.utils.WebUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author admin
 */
public class BillReport {

    private String todayDate;
    private String userName;
    private String msg;
    private String typereport;
    private String modereport;
    private String brncode;
    private Date fromdt;
    private Date todt;
    private HttpServletRequest req;
    InitialContext ctx;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    OtherReportFacadeRemote remote;
    CommonReportMethodsRemote commonReportMethodsRemote;

    /** Creates a new instance of BillReport */
    public BillReport() {
        try {
            req = getRequest();
            remote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            commonReportMethodsRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            brncode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            setTodayDate(dmy.format(date));
            ctx = new InitialContext();
            fromdt = date;
            todt = date;
        } catch (Exception e) {
        }

    }

    public String getModereport() {
        return modereport;
    }

    public void setModereport(String modereport) {
        this.modereport = modereport;
    }

    public String getTypereport() {
        return typereport;
    }

    public void setTypereport(String typereport) {
        this.typereport = typereport;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getFromdt() {
        return fromdt;
    }

    public void setFromdt(Date fromdt) {
        this.fromdt = fromdt;
    }

    public Date getTodt() {
        return todt;
    }

    public void setTodt(Date todt) {
        this.todt = todt;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public String showReport() throws IOException {
        if (fromdt == null) {
            msg = "Please enter from date ";
            return null;
        }
        if (todt == null) {
            msg = "Please enter to date ";
            return null;
        }
        if (fromdt.after(todt)) {
            msg = "From date should be less than to date ";
            return null;
        }

        try {
            List<SundrySuspencePojo> result = remote.getSundryData(brncode, Integer.parseInt(typereport), ymd.format(todt), ymd.format(fromdt));
            if (!result.isEmpty()) {
                String temp[] = commonReportMethodsRemote.getBranchNameandAddressString(brncode).split("!");
                String repName = "Sundry Suspence Bill Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", userName);
                fillParams.put("pReportType", typereport.equalsIgnoreCase("0") ? "Sundry Report" : "Suspence Report");
                fillParams.put("pReportDate", dmy.format(fromdt) + " to " + dmy.format(todt));
                fillParams.put("pBankName", temp[0]);
                fillParams.put("pBankAddress", temp[1]);
                new ReportBean().jasperReport("Sundry_Report", "text/html", new JRBeanCollectionDataSource(result), fillParams, repName);
                return "report";
            } else {

                setMsg("Report data does not exists !!");
                return null;
            }
        } catch (NumberFormatException numberFormatException) {
        } catch (ApplicationException applicationException) {
        }
        return null;
    }

    public void refreshBtn() {
        typereport = "00";
        fromdt = date;
        todt = date;
        msg = "";

    }
}
