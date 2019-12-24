/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.pojo.OverDueListPojo;
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
public class OverDueAcWise extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String acType;
    private List<SelectItem> acTypeList;
    private Date asOnDate;
    private Date date = new Date();
    private DDSReportFacadeRemote ddsRemote;
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<OverDueListPojo> reportList = new ArrayList<OverDueListPojo>();

    public OverDueAcWise() {
        try {
            setAsOnDate(date);
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            branchList = new ArrayList<SelectItem>();
            List list = common.getBranchCodeList(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector brnVector = (Vector) list.get(i);
                branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
            }
            List acList = common.getActCodeByAcNature(CbsConstant.TERM_LOAN);
            acTypeList = new ArrayList<SelectItem>();
            for (int j = 0; j < acList.size(); j++) {
                Vector acVector = (Vector) acList.get(j);
                acTypeList.add(new SelectItem(acVector.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
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
        String report = "Over Due List";
        try {

            if (asOnDate == null) {
                setMessage("Please fill As on date");
                return;
            }

//            if (asOnDate.after(getDate())) {
//                setMessage("As on date should be less than current date !");
//                return;
//            }

            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", dmy.format(this.asOnDate));
            fillParams.put("pReportName", report);

            reportList = ddsRemote.getOverDueListData(branch, acType, ymd.format(asOnDate), "");
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            new ReportBean().jasperReport("overDueList", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Over Due List");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void btnPdfAction() {
        String report = "Over Due List";
        try {

            if (asOnDate == null) {
                setMessage("Please fill As on date");
                return;
            }

//            if (asOnDate.after(getDate())) {
//                setMessage("As on date should be less than current date !");
//                return;
//            }

            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", dmy.format(this.asOnDate));
            fillParams.put("pReportName", report);
            //String asOnDt = ymd.format(dmy.parse(asOnDate));

            reportList = ddsRemote.getOverDueListData(branch, acType, ymd.format(asOnDate), "");
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            new ReportBean().openPdf("Over Due", "overDueList", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshAction() {
        this.setMessage("");
        setAsOnDate(date);
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public Date getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(Date asOnDate) {
        this.asOnDate = asOnDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
