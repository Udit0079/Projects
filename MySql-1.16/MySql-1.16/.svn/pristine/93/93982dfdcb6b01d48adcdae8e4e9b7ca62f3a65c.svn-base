/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.ho.AtmDisputePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class AtmDispute extends BaseBean {

    private String message;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    private CommonReportMethodsRemote common;
    MiscReportFacadeRemote beanRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<AtmDisputePojo> reportList = new ArrayList<>();

    public AtmDispute() {

        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void printAction() {
        setMessage("");
        try {
            String report = "Atm Dispute Report";
            String FromDt = dmy.format(calFromDate);
            String ToDate = dmy.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;

            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", duration);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);

            reportList = beanRemote.getAtmDisputeReportData(branchCode, ymd.format(calFromDate), ymd.format(caltoDate));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().jasperReport("AtmDisputeReport", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, report);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void viewPdfReport() {
        setMessage("");
        try {
            String report = "Atm Dispute Report";
            String FromDt = dmy.format(calFromDate);
            String ToDate = dmy.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;

            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", duration);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);

            reportList = beanRemote.getAtmDisputeReportData(branchCode, ymd.format(calFromDate), ymd.format(caltoDate));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().openPdf(report + "_" + ymd.format(dmy.parse(getTodayDate())), "AtmDisputeReport", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
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

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }

    public Date getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(Date caltoDate) {
        this.caltoDate = caltoDate;
    }
}
