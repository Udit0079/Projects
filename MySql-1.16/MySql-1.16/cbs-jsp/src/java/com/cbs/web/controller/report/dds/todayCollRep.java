/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.dds;

import com.cbs.dto.report.DdsTransactionGrid;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.ReportBean;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
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
 * @author sipl
 */
public class todayCollRep {

    String orgnBrCode;
    HttpServletRequest req;
    Date dt = new Date();
    private String todayDate;
    private String userName;
    private String message;
    private String allAcType;
    private List<SelectItem> allAcTpList;
    private String allAgCode;
    private List<SelectItem> allAgCodeList;
    Date tillDt;
    private String allSort;
    private List<SelectItem> allSortList;
    private final String jndiHomeName = "DDSReportFacade";
    private final String jndiName = "CommonReportMethods";
    private DDSReportFacadeRemote DDSReportRemote = null;
    private CommonReportMethodsRemote common = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<DdsTransactionGrid> repDataList = new ArrayList<DdsTransactionGrid>();

    public List<SelectItem> getAllAcTpList() {
        return allAcTpList;
    }

    public void setAllAcTpList(List<SelectItem> allAcTpList) {
        this.allAcTpList = allAcTpList;
    }

    public String getAllAcType() {
        return allAcType;
    }

    public void setAllAcType(String allAcType) {
        this.allAcType = allAcType;
    }

    public String getAllAgCode() {
        return allAgCode;
    }

    public void setAllAgCode(String allAgCode) {
        this.allAgCode = allAgCode;
    }

    public List<SelectItem> getAllAgCodeList() {
        return allAgCodeList;
    }

    public void setAllAgCodeList(List<SelectItem> allAgCodeList) {
        this.allAgCodeList = allAgCodeList;
    }

    public String getAllSort() {
        return allSort;
    }

    public void setAllSort(String allSort) {
        this.allSort = allSort;
    }

    public List<SelectItem> getAllSortList() {
        return allSortList;
    }

    public void setAllSortList(List<SelectItem> allSortList) {
        this.allSortList = allSortList;
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

    public Date getTillDt() {
        return tillDt;
    }

    public void setTillDt(Date tillDt) {
        this.tillDt = tillDt;
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

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public todayCollRep() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            setTodayDate(sdf.format(dt));

            DDSReportRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);

            List acTLst = new ArrayList();
            acTLst = DDSReportRemote.getAccountType();

            allAcTpList = new ArrayList<SelectItem>();
            if (!acTLst.isEmpty()) {
                for (int i = 0; i < acTLst.size(); i++) {
                    Vector ele7 = (Vector) acTLst.get(i);
                    allAcTpList.add(new SelectItem(ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            List agLst = new ArrayList();
            agLst = DDSReportRemote.getAgentCode(orgnBrCode);
            allAgCodeList = new ArrayList<SelectItem>();
            if (!agLst.isEmpty()) {
                for (int i = 0; i < agLst.size(); i++) {
                    Vector ele7 = (Vector) agLst.get(i);
                    allAgCodeList.add(new SelectItem(ele7.get(0).toString(), "("+ele7.get(0).toString()+")"+ele7.get(1).toString()));
                }
            }

            allSortList = new ArrayList<SelectItem>();
            allSortList.add(new SelectItem("receiptno", "Receipt No"));
            allSortList.add(new SelectItem("acno", "Account No"));
        } catch (Exception ex) {
            this.setMessage(ex.getLocalizedMessage());
        }
    }

    public void btnHtmlAction() {
        setMessage("");
        String bankName = "", bankAddress = "";
        if (this.tillDt == null) {
            setMessage("Please Enter Date.");
        }
        Map fillParams = new HashMap();
        try {
            Date date2 = this.tillDt;
            String report = "Agent Collection For HD";

            List dataList1 = common.getBranchNameandAddress(orgnBrCode);

            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            String agName = DDSReportRemote.getAgentName(this.getAllAgCode(), orgnBrCode);

            fillParams.put("pReportDate", sdf.format(date2));
            fillParams.put("SysDate", sdf.format(new Date()));
            fillParams.put("pPrintedBy", this.userName);
            fillParams.put("pReportName", report);
            fillParams.put("bankName", bankName);
            fillParams.put("bankAddr", bankAddress);
            fillParams.put("agName", agName);
            fillParams.put("agCode", this.getAllAgCode());

            repDataList = DDSReportRemote.getDailyCollData(ymd.format(date2), orgnBrCode, this.getAllAgCode(), this.getAllAcType(), this.getAllSort());

            if (repDataList.isEmpty()) {
                this.setMessage("No Detail To Print");
            } else {
                new ReportBean().jasperReport("todayCollection", "text/html",
                        new JRBeanCollectionDataSource(repDataList), fillParams, "Agent Collection For HD");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());

        }
    }

    public void btnPdfAction() {
        setMessage("");
        String bankName = "", bankAddress = "";
        if (this.tillDt == null) {
            setMessage("Please Enter Date.");
        }
        Map fillParams = new HashMap();
        try {
            Date date2 = this.tillDt;
            String report = "Agent Collection For HD";

            List dataList1 = common.getBranchNameandAddress(orgnBrCode);

            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }

            String agName = DDSReportRemote.getAgentName(this.getAllAgCode(), orgnBrCode);

            fillParams.put("pReportDate", sdf.format(date2));
            fillParams.put("SysDate", sdf.format(new Date()));
            fillParams.put("pPrintedBy", this.userName);
            fillParams.put("pReportName", report);
            fillParams.put("bankName", bankName);
            fillParams.put("bankAddr", bankAddress);
            fillParams.put("agName", agName);
            fillParams.put("agCode", this.getAllAgCode());
            
            repDataList = DDSReportRemote.getDailyCollData(ymd.format(date2), orgnBrCode, this.getAllAgCode(), this.getAllAcType(), this.getAllSort());

            if (repDataList.isEmpty()) {
                this.setMessage("No Detail To Print");
            } else {
                new ReportBean().openPdf("Agent Collection For HD_" + ymd.format(sdf.parse(getTodayDate())), "todayCollection", new JRBeanCollectionDataSource(repDataList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());

        }
    }

    public String btnExitAction() {
        refreshForm();
        return "case1";
    }

    public void refreshForm() {
        this.setMessage("");
        allAcTpList = new ArrayList<SelectItem>();
        allAgCodeList = new ArrayList<SelectItem>();
        this.setDt(new Date());
    }
}
