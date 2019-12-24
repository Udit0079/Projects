/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.Closing_CashPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
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
 * @author root
 */
public class ClosingCashReport extends BaseBean {

    private String limit = "0";
    private String frmDt;
    private String toDt;
    private String branch;
    private String brncode;
    private String errorMsg;
    private Object date = new Date();
    private List<SelectItem> branchList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote otherFacade;
    List<Closing_CashPojo> reportList = new ArrayList<Closing_CashPojo>();
    private final CommonReportMethodsRemote RemoteCode;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public ClosingCashReport() throws ServiceLocatorException, ApplicationException {
        common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
        otherFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
        RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

        frmDt = dmy.format(date);
        toDt = dmy.format(date);
        brncode = getOrgnBrCode();
        branchList = new ArrayList<SelectItem>();
        List brncode = new ArrayList();
        brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
        branchList = new ArrayList<SelectItem>();
        if (!brncode.isEmpty()) {
            for (int i = 0; i < brncode.size(); i++) {
                Vector brnVector = (Vector) brncode.get(i);
                branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
            }
        }
    }

    public void viewReport() {
        String brnName = "";
        String brnAddress = "";
        try {
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill From Date!");
                return;
            }
            if (!Validator.validateDate(frmDt)) {
                setErrorMsg("Please fill Proper From Date");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill To Date!");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please fill Proper To Date!");
                return;
            }
            if (limit.equalsIgnoreCase("") || limit == null) {
                setErrorMsg("Please enter a valid Limit");
                return;
            }

            String fromDatet = ymd.format(dmy.parse(this.frmDt));
            String toDate = ymd.format(dmy.parse(this.toDt));
            String report = "Closing Cash Report";
            String pdate = getTodayDate();
            List brnnamead = new ArrayList();

            brnnamead = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brnnamead.size() > 0) {
                brnName = (String) brnnamead.get(0);
                brnAddress = (String) brnnamead.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", brnName);
            fillParams.put("pBnkAddr", brnAddress);
            fillParams.put("pRepName", report);
            fillParams.put("pRepDate", this.frmDt + " to " + this.toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintDate", pdate);
            reportList = otherFacade.get_ClosingCashDetails(this.branch, fromDatet, toDate, this.limit);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exits.");
            } else {
                new ReportBean().jasperReport("Closing_CASH", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Closing Cash");
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);


        }//try end
       catch (Exception e) {
            //e.printStackTrace();
            setErrorMsg(e.getMessage());
        }

    }

    public void btnPdfAction() {
        String brnName = "";
        String brnAddress = "";
        try {
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill From Date!");
                return;
            }
            if (!Validator.validateDate(frmDt)) {
                setErrorMsg("Please fill Proper From Date");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill To Date!");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please fill Proper To Date!");
                return;
            }
            if (limit.equalsIgnoreCase("") || limit == null) {
                setErrorMsg("Please enter a valid Limit");
                return;
            }

            String fromDatet = ymd.format(dmy.parse(this.frmDt));
            String toDate = ymd.format(dmy.parse(this.toDt));
            String report = "Closing Cash Report";
            String pdate = getTodayDate();
            List brnnamead = new ArrayList();

            brnnamead = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brnnamead.size() > 0) {
                brnName = (String) brnnamead.get(0);
                brnAddress = (String) brnnamead.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", brnName);
            fillParams.put("pBnkAddr", brnAddress);
            fillParams.put("pRepName", report);
            fillParams.put("pRepDate", this.frmDt + " to " + this.toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintDate", pdate);
            reportList = otherFacade.get_ClosingCashDetails(this.branch, ymd.format(dmy.parse(this.frmDt)), ymd.format(dmy.parse(this.toDt)), this.limit);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exits.");
            } else {
                new ReportBean().openPdf("Closing_Cash" + fromDatet + " to " + toDate, "Closing_CASH", new JRBeanCollectionDataSource(reportList), fillParams);
            }

            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        }//try end
       catch (Exception e) {
            //e.printStackTrace();
            setErrorMsg(e.getMessage());
        }

    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void refresh() {
        this.setErrorMsg("");
        this.setFrmDt(dmy.format(date));
        this.setToDt(dmy.format(date));
        this.setBranch("0A");
        this.setLimit("0");
        

    }

    public String exitAction() {
        return "case1";
    }
}
