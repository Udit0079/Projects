/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.dds;

import com.cbs.pojo.AgentWiseMonthlyCollectionPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.pojo.SortedByAgCode;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Nishant
 */
public class AgentWiseMonthlyCollectionReport extends BaseBean {

    private String message;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    Date frdt;
    HttpServletRequest req;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Date dt = new Date();
    private String status = "none";
    Date todt;
    Date todtDisable;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String acType;
    private List<SelectItem> selectActTypeList;
    private String display = "";
    private boolean caltoDateDisable;
    private String orgnBrCode;
    private boolean disableAbs = true;
    private OtherReportFacadeRemote loanFacade;
    private CommonReportMethodsRemote common;
    List<AgentWiseMonthlyCollectionPojo> repLoansList = new ArrayList<AgentWiseMonthlyCollectionPojo>();

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

    public SimpleDateFormat getDmyFormat() {
        return dmyFormat;
    }

    public void setDmyFormat(SimpleDateFormat dmyFormat) {
        this.dmyFormat = dmyFormat;
    }

    public Date getTodtDisable() {
        return todtDisable;
    }

    public void setTodtDisable(Date todtDisable) {
        this.todtDisable = todtDisable;
    }
    private Collection repDataList;

    public boolean isDisableAbs() {
        return disableAbs;
    }

    public void setDisableAbs(boolean disableAbs) {
        this.disableAbs = disableAbs;
    }

    public boolean isCaltoDateDisable() {
        return caltoDateDisable;
    }

    public void setCaltoDateDisable(boolean caltoDateDisable) {
        this.caltoDateDisable = caltoDateDisable;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public SimpleDateFormat getDmy() {
        return dmy;
    }

    public void setDmy(SimpleDateFormat dmy) {
        this.dmy = dmy;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getSelectActTypeList() {
        return selectActTypeList;
    }

    public void setSelectActTypeList(List<SelectItem> selectActTypeList) {
        this.selectActTypeList = selectActTypeList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFrdt() {
        return frdt;
    }

    public void setFrdt(Date frdt) {
        this.frdt = frdt;
    }

    public Date getTodt() {
        return todt;
    }

    public void setTodt(Date todt) {
        this.todt = todt;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public AgentWiseMonthlyCollectionReport() {
        req = getRequest();
        try {
            disableAbs = false;
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setFrdt(dt);
            setTodt(dt);
            loanFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            List acType = new ArrayList();
            acType = loanFacade.getActTypeList();
            selectActTypeList = new ArrayList<SelectItem>();
            if (!acType.isEmpty()) {
                selectActTypeList.add(new SelectItem("--Select--"));
                for (int i = 0; i < acType.size(); i++) {
                    Vector actVector = (Vector) acType.get(i);
                    selectActTypeList.add(new SelectItem(actVector.get(0), actVector.get(1).toString()));
                }
            }
            setMessage("");

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry.Got a null request from faces context");
        }
        return request;
    }

    public void btnHtmlAction() {
        System.out.println("Hi-----------------------------------");
        setMessage("");
        String branchName = "", branchAddress = "";
        if (this.acType.equalsIgnoreCase("...Select...")) {
            setMessage("please select Account Type");
            return;
        }
        if (this.frdt == null) {
            setMessage("Please Fill From Date.");
            return;
        }
        if (this.todt == null) {
            setMessage("Please Fill To Date.");
            return;
        }
        Map fillParams = new HashMap();
        try {
            List dataList1 = common.getBranchNameandAddress(orgnBrCode);
            if (dataList1.size() > 0) {
                branchName = (String) dataList1.get(0);
                branchAddress = (String) dataList1.get(1);
            }
            String report = "Agent Wise Monthly collection Report";
            fillParams.put("pReportFrDate", dmy.format(this.frdt));
            fillParams.put("pReportToDate", dmy.format(this.todt));
            fillParams.put("SysDate", ymd.format(new Date()));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBranchName", branchName);
            fillParams.put("branchAddr", branchAddress);

            repLoansList = loanFacade.getagentWiseMonthlyReport(branchCode, this.acType, ymd.format(this.getFrdt()), ymd.format(this.getTodt()));

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortedByAgCode());
            Collections.sort(repLoansList, chObj);

            new ReportBean().jasperReport("AgentWiseMonthlycollectionReport", "text/html",
                    new JRBeanCollectionDataSource(repLoansList), fillParams, "Agent Wise Monthly collection Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnPdfAction() {

        setMessage("");
        String branchName = "", branchAddress = "";
        if (this.acType.equalsIgnoreCase("...Select...")) {
            setMessage("please select Account Type");
            return;
        }
        if (this.frdt == null) {
            setMessage("Please Fill From Date.");
            return;
        }
        if (this.todt == null) {
            setMessage("Please Fill To Date.");
            return;
        }
        Map fillParams = new HashMap();
        try {
            List dataList1 = common.getBranchNameandAddress(orgnBrCode);
            if (dataList1.size() > 0) {
                branchName = (String) dataList1.get(0);
                branchAddress = (String) dataList1.get(1);
            }
            String report = "Agent Wise Monthly collection Report";
            fillParams.put("pReportFrDate", dmy.format(this.frdt));
            fillParams.put("pReportToDate", dmy.format(this.todt));
            fillParams.put("SysDate", ymd.format(new Date()));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBranchName", branchName);
            fillParams.put("branchAddr", branchAddress);

            repLoansList = loanFacade.getagentWiseMonthlyReport(branchCode, this.acType, ymd.format(this.getFrdt()), ymd.format(this.getTodt()));

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortedByAgCode());
            Collections.sort(repLoansList, chObj);

            new ReportBean().openPdf("Agent Wise Monthly collection Report_"+ ymd.format(dmy.parse(getTodayDate())), "AgentWiseMonthlycollectionReport", new JRBeanCollectionDataSource(repLoansList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refresh() {
        try {
            this.setMessage("");
            setFrdt(dt);
            setTodt(dt);
            setAcType("--Select--");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
