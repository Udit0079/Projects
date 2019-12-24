/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.AgentLedgerReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

/**
 *
 * @author Athar Reza
 */
public class AgentLedgerRep extends BaseBean {

    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String calFromDate;
    private String caltoDate;
    private boolean caltoDateDisable;
    private String acctType;
    private String agentCode;
    private String agentName;
    private String message;
    private String display = "";
    private CommonReportMethodsRemote common;
    private List<SelectItem> acctTypeList;
    private List<SelectItem> agentCodeList;
    Date dt = new Date();
    HttpServletRequest req;
    List<AgentLedgerReportPojo> repDataList = new ArrayList<AgentLedgerReportPojo>();
    private OtherReportFacadeRemote loanFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

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

    public List<AgentLedgerReportPojo> getRepDataList() {
        return repDataList;
    }

    public void setRepDataList(List<AgentLedgerReportPojo> repDataList) {
        this.repDataList = repDataList;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public List<SelectItem> getAgentCodeList() {
        return agentCodeList;
    }

    public void setAgentCodeList(List<SelectItem> agentCodeList) {
        this.agentCodeList = agentCodeList;
    }

    public String getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(String calFromDate) {
        this.calFromDate = calFromDate;
    }

    public String getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(String caltoDate) {
        this.caltoDate = caltoDate;
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public AgentLedgerRep() {
        req = getRequest();
        try {
            this.setCalFromDate(getTodayDate());
            this.setCaltoDate(getTodayDate());
            loanFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List acct = new ArrayList();
            acct = loanFacade.getActTypeList();
            acctTypeList = new ArrayList<SelectItem>();
            acctTypeList.add(new SelectItem("--Select--"));
            if (!acct.isEmpty()) {
                for (int i = 0; i < acct.size(); i++) {
                    Vector agVector = (Vector) acct.get(i);
                    acctTypeList.add(new SelectItem(agVector.get(0).toString(), agVector.get(1).toString()));
                }
            }

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    if (!brnVector.get(0).toString().equalsIgnoreCase("A")) {
                        branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                    }
                }
            }

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void ddbranchValueChange() {
        try {
            List agent = new ArrayList();
            agent = common.getAgentCode(this.getBranchCode());
            agentCodeList = new ArrayList<SelectItem>();
            agentCodeList.add(new SelectItem("--Select--"));
            if (!agent.isEmpty()) {
                for (int j = 0; j < agent.size(); j++) {
                    Vector acVector = (Vector) agent.get(j);
                    agentCodeList.add(new SelectItem(acVector.get(0).toString(),"("+acVector.get(0).toString()+")"+acVector.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onChangeAgcode() {
        try {
            this.agentName = common.getAgentName(agentCode, branchCode);
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

    public void PrintViwe() throws ParseException {

        setMessage("");
        String bnkName = "";
        String bnkAddr = "";
        String report = "Agent Ledger Report";
        if (this.calFromDate == null) {
            setMessage("Please Fill From Date.");
            return;
        }
        if (this.caltoDate == null) {
            setMessage("Please Fill To Date.");
            return;
        }
        if (dmy.parse(calFromDate).after(dmy.parse(caltoDate))) {
            setMessage("From Date should be less than To date!");
            return;
        }
        if (this.acctType.equalsIgnoreCase("--Select--")) {
            setMessage("Please Fill A/c Type!");
            return;
        }
        if (this.agentCode.equalsIgnoreCase("--Select--")) {
            setMessage("Please Fill Agent Code!");
            return;
        }

        try {
            List bnklist = new ArrayList();
            bnklist = common.getBranchNameandAddress(this.branchCode);
            if (bnklist.size() > 0) {
                bnkName = (String) bnklist.get(0);
                bnkAddr = (String) bnklist.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", bnkName);
            fillParams.put("pBnkAddr", bnkAddr);
            fillParams.put("pReportFrDate", this.calFromDate);
            fillParams.put("pReportToDate", this.caltoDate);
            fillParams.put("SysDate", dmy.format(new Date()));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("code", this.agentCode);
            fillParams.put("pBranchName", common.getBranchNameByBrncode(this.branchCode));
            String frmDt = ymd.format(dmy.parse(calFromDate));
            String toDt = ymd.format(dmy.parse(caltoDate));

            repDataList = loanFacade.getAgentLedgerAction(this.agentCode, this.acctType, frmDt, toDt, branchCode);
            if (repDataList.isEmpty()) {
                message = "No value found in databse";
                return;
            }
            new ReportBean().jasperReport("AgentLedgerReport", "text/html",
                    new JRBeanCollectionDataSource(repDataList), fillParams, "Agent Ledger Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnPdfAction() throws ParseException {
        setMessage("");
        String bnkName = "";
        String bnkAddr = "";
        String report = "Agent Ledger Report";
        if (this.calFromDate == null) {
            setMessage("Please Fill From Date.");
            return;
        }
        if (this.caltoDate == null) {
            setMessage("Please Fill To Date.");
            return;
        }
        if (dmy.parse(calFromDate).after(dmy.parse(caltoDate))) {
            setMessage("From Date should be less than To date!");
            return;
        }
        if (this.acctType.equalsIgnoreCase("--Select--")) {
            setMessage("Please Fill A/c Type!");
            return;
        }
        if (this.agentCode.equalsIgnoreCase("--Select--")) {
            setMessage("Please Fill Agent Code!");
            return;
        }

        try {
            List bnklist = new ArrayList();
            bnklist = common.getBranchNameandAddress(this.branchCode);
            if (bnklist.size() > 0) {
                bnkName = (String) bnklist.get(0);
                bnkAddr = (String) bnklist.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", bnkName);
            fillParams.put("pBnkAddr", bnkAddr);
            fillParams.put("pReportFrDate", this.calFromDate);
            fillParams.put("pReportToDate", this.caltoDate);
            fillParams.put("SysDate", dmy.format(new Date()));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("code", this.agentCode);
            fillParams.put("pBranchName", common.getBranchNameByBrncode(this.branchCode));
            String frmDt = ymd.format(dmy.parse(calFromDate));
            String toDt = ymd.format(dmy.parse(caltoDate));

            repDataList = loanFacade.getAgentLedgerAction(this.agentCode, this.acctType, frmDt, toDt, branchCode);
            if (repDataList.isEmpty()) {
                message = "No value found in databse";
                return;
            }
            new ReportBean().openPdf("Agent Ledger Report_" + ymd.format(dmy.parse(getTodayDate())), "AgentLedgerReport", new JRBeanCollectionDataSource(repDataList), fillParams);
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
            this.setAcctType("--Select--");
            this.setAgentCode("--Select--");
            this.setCalFromDate(getTodayDate());
            this.setCaltoDate(getTodayDate());
            this.agentName = "";
        } catch (Exception e) {

            setMessage(e.getMessage());
        }
    }

    public String closeAction() {
        return "case1";
    }
}
