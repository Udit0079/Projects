/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.SuspenseGeneralPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
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
 * @author Admin
 */
public class SuspenseGeneral extends BaseBean {

    String message;
    private String acNo;
    private List<SelectItem> acNoList;
    private String acName;
    Date calDate;
    private final String jndiHomeName = "GLReportFacade";
    private GLReportFacadeRemote glBeanRemote = null;
    List<SuspenseGeneralPojo> reportList = new ArrayList<SuspenseGeneralPojo>();
    private CommonReportMethodsRemote common;

    public SuspenseGeneral() {
        try {
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            this.setCalDate(new Date());

            acNoList = new ArrayList<SelectItem>();
            acNoList.add(new SelectItem("S", "--Select--"));
            List result = common.getSundryCrAcno();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    acNoList.add(new SelectItem(ele.get(0).toString()));
                }
            }

        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void onBlurAcnoName() {
        setMessage("");
        try {
            if (acNo == null || acNo.equalsIgnoreCase("S")) {
                setMessage("Please Select Account No.");
                return;
            }
            this.acName = common.getAcNameByAcno(this.acNo);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnPdfAction() {
        setMessage("");
        if (acNo == null || acNo.equalsIgnoreCase("S")) {
            setMessage("Please Select Account No.");
            return;
        }
        if (this.calDate == null) {
            setMessage("Please Enter Date.");
            return;
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date2 = this.calDate;
            String report = "SUNDRY CREDITORS OUTSTANDING REPORT";
            reportList = glBeanRemote.getSuspenseGeneralData(ymd.format(calDate), getOrgnBrCode(), this.acNo);
            List dataList = common.getBranchNameandAddress(getOrgnBrCode());

            Map fillParams = new HashMap();
            fillParams.put("pReportDt", sdf2.format(date2));
            fillParams.put("pPrintedBy", getUserName().toUpperCase());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", dataList.get(0).toString());
            fillParams.put("pBankAdd", dataList.get(0).toString());
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exists !");
            }
//            new ReportBean().jasperReport("Suspense_General1", "text/html",
//                    new JRBeanCollectionDataSource(reportList), fillParams, "Sundry Creditors Outstanding Report");
//            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            new ReportBean().openPdf("Suspense_General1_" + ymd.format(sdf2.parse(getTodayDate())), "Suspense_General1", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            e.printStackTrace();
            setMessage(e.getLocalizedMessage());
        }
    }

    public void btnRefreshAction() {
        setMessage("");
        setAcNo("S");
        setAcName("");
        this.setCalDate(new Date());
    }

    public String btnExitAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public List<SelectItem> getAcNoList() {
        return acNoList;
    }

    public void setAcNoList(List<SelectItem> acNoList) {
        this.acNoList = acNoList;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }
}
