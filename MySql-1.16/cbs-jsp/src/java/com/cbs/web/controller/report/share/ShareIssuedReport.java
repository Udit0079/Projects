/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import com.cbs.dto.report.ShareIssuedPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Nishant Srivastava
 */
public class ShareIssuedReport extends BaseBean {

    private String errorMsg;
    private ShareReportFacadeRemote horfr = null;
    private CommonReportMethodsRemote commonRemote = null;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private Date frDt, toDt;
    HttpServletRequest req;
    private String message;

    public HttpServletRequest getReq() {
        return req;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public CommonReportMethodsRemote getCommonRemote() {
        return commonRemote;
    }

    public void setCommonRemote(CommonReportMethodsRemote commonRemote) {
        this.commonRemote = commonRemote;
    }

    public SimpleDateFormat getDmy() {
        return dmy;
    }

    public void setDmy(SimpleDateFormat dmy) {
        this.dmy = dmy;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getFrDt() {
        return frDt;
    }

    public void setFrDt(Date frDt) {
        this.frDt = frDt;
    }

    public ShareReportFacadeRemote getHorfr() {
        return horfr;
    }

    public void setHorfr(ShareReportFacadeRemote horfr) {
        this.horfr = horfr;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public ShareIssuedReport() {
        try {
            setFrDt(frDt);
            setToDt(toDt);
            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
        } catch (Exception e) {
        }

    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void viewReport() {
        if (validate()) {
            try {
                List<ShareIssuedPojo> resultList = horfr.getTransfererReportWise(ymd.format(frDt), ymd.format(toDt));
                if (!resultList.isEmpty()) {
                    String report = "Share Issued Report";
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");

                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate", dmy.format(frDt));
                    fillParams.put("pReportdate", dmy.format(toDt));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);

                    new ReportBean().jasperReport("ShareIssuedReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Share Issued Report");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getRequest().getSession();
                    httpSession.setAttribute("ReportName", report);

                }
            } catch (Exception e) {
                setMessage(e.getMessage());
            }
        }
    }

    public void pdfDownLoad() {
        if (validate()) {
            try {
                List<ShareIssuedPojo> resultList = horfr.getTransfererReportWise(ymd.format(frDt), ymd.format(toDt));
                if (!resultList.isEmpty()) {
                    String report = "Share Issued Report";
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");

                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate", dmy.format(frDt));
                    fillParams.put("pReportdate", dmy.format(toDt));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", s[1]);

                    new ReportBean().openPdf("Share Issued Report", "ShareIssuedReport", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getRequest().getSession();
                    httpSession.setAttribute("ReportName", report);

                }
            } catch (Exception e) {
                setMessage(e.getMessage());
            }
        }
    }

    private boolean validate() {
        if (frDt == null) {
            errorMsg = "Please enter from date";
            return false;
        } else if (toDt == null) {
            errorMsg = "Please enter to date";
            return false;
        } else if (frDt.after(toDt)) {
            errorMsg = "Please from date is less then to date";
            return false;
        }
        return true;
    }

    public String exitAction() {
        return "case1";
    }
}
