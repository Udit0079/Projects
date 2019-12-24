/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.ProfitAndLossPojo;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.ReportBean;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class ProfitAndLoss {

    String orgnBrCode;
    HttpServletRequest req;
    String message;
    String stxtDate;
    String stxtUser;
    String ddRep;
    private String showPanel;
    private String selectOption;
    private List<SelectItem> selectOptionList;
    List<SelectItem> ddRepList;
    Date calDate;
    private final String jndiHomeName = "GLReportFacade";
    private GLReportFacadeRemote glBeanRemote = null;
    List<ProfitAndLossPojo> profitLossDataList = new ArrayList<ProfitAndLossPojo>();

    public String getSelectOption() {
        return selectOption;
    }

    public void setSelectOption(String selectOption) {
        this.selectOption = selectOption;
    }

    public List<SelectItem> getSelectOptionList() {
        return selectOptionList;
    }

    public void setSelectOptionList(List<SelectItem> selectOptionList) {
        this.selectOptionList = selectOptionList;
    }

    public String getShowPanel() {
        return showPanel;
    }

    public void setShowPanel(String showPanel) {
        this.showPanel = showPanel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getStxtDate() {
        return stxtDate;
    }

    public void setStxtDate(String stxtDate) {
        this.stxtDate = stxtDate;
    }

    public String getStxtUser() {
        return stxtUser;
    }

    public void setStxtUser(String stxtUser) {
        this.stxtUser = stxtUser;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getDdRep() {
        return ddRep;
    }

    public void setDdRep(String ddRep) {
        this.ddRep = ddRep;
    }

    public List<SelectItem> getDdRepList() {
        return ddRepList;
    }

    public void setDdRepList(List<SelectItem> ddRepList) {
        this.ddRepList = ddRepList;
    }

    public ProfitAndLoss() {

        try {
            req = getRequest();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setStxtUser(req.getRemoteUser());
            // setStxtUser("Nishant");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setStxtDate(sdf.format(date));
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ddRepList = new ArrayList<SelectItem>();
            ddRepList.add(new SelectItem("0", "--Select--"));
            ddRepList.add(new SelectItem("PL", "PROFIT AND LOSS ACCOUNT"));
            selectOptionList = new ArrayList<SelectItem>();
            selectOptionList.add(new SelectItem("0", "Before Year End"));
            selectOptionList.add(new SelectItem("1", "After Year End"));
            selectOption = "0";
            this.setCalDate(new Date());
            showPanel = "none";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void btnHtmlAction() {
        setMessage("");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        if (calDate == null) {
            setMessage("Please Enter The AS On Date.");
            //return "";
        }
        Date date = (Date) this.calDate;
        String date2 = sdf1.format(date);
        String user = (String) this.stxtUser;
        String report = "Profit And Loss Report";
        Map fillParams = new HashMap();
        try {
            fillParams.put("pPrintedDate", date2);
            fillParams.put("pPrintedBy", user);
            fillParams.put("pReportName", report);

            profitLossDataList = glBeanRemote.getProfitAndLossDetails(ymd.format(this.calDate), orgnBrCode, selectOption);

            new ReportBean().jasperReport("PROFIT_LOSS_REPORT", "text/html",
                    new JRBeanCollectionDataSource(profitLossDataList), fillParams, "Final Balance Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            e.printStackTrace();

        }
        //return "report";
    }

    public void onBlurOfDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(calDate);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
//        if (day == 31 && month == 3) {
        if ((day == 28 || day == 29 || day == 30 || day == 31) && month == 3) {
            showPanel = "''";
        } else {
            showPanel = "none";
            selectOption = "0";
        }
    }

    public String btnPdfAction() {
        setMessage("");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        if (calDate == null) {
            setMessage("Please Enter The AS On Date.");
            //return "";
        }
        Date date = (Date) this.calDate;
        String date2 = sdf1.format(date);
        String user = (String) this.stxtUser;
        String report = "Profit And Loss Report";
        Map fillParams = new HashMap();
        try {
            fillParams.put("pPrintedDate", date2);
            fillParams.put("pPrintedBy", user);
            fillParams.put("pReportName", report);

            profitLossDataList = glBeanRemote.getProfitAndLossDetails(ymd.format(this.calDate), orgnBrCode, selectOption);

            new ReportBean().openPdf("PROFIT_LOSS_REPORT_", "PROFIT_LOSS_REPORT", new JRBeanCollectionDataSource(profitLossDataList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public void refreshForm() {
        this.setMessage("");
        this.setCalDate(new Date());
        this.setDdRep("0");
        selectOption = "0";
    }

    public String btnExitAction() {
        refreshForm();
        return "case1";
    }
}
