/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.pojo.PostDatedChequePojo;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class PostDatedCheque extends BaseBean {

    String message;
    Date frcalDate;
    Date tocalDate;
    private String branch;
    private List<SelectItem> branchList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    Date dt = new Date();
    private CommonReportMethodsRemote common;
    private DDSReportFacadeRemote ddsReportRemote;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<PostDatedChequePojo> reportList = new ArrayList<PostDatedChequePojo>();

    public PostDatedCheque() {
        try {

            this.setFrcalDate(dt);
            this.setTocalDate(dt);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsReportRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());

            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("A", "ALL"));
            reportTypeList.add(new SelectItem("F", "Fresh"));
            reportTypeList.add(new SelectItem("U", "Used"));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnHtmlAction() {
        setMessage("");
        String report = "Post Dated Cheque Detail Report";
        try {
            if (this.frcalDate == null) {
                setMessage("Please Enter From Date.");
                return;
            }
            if (this.tocalDate == null) {
                setMessage("Please Enter To Date.");
                return;
            }
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", sdf.format(this.frcalDate) + " to " + sdf.format(this.tocalDate));
            fillParams.put("pReportName", report);

            reportList = ddsReportRemote.getPostDatedChequieData(branch, ymd.format(frcalDate), ymd.format(tocalDate), reportType);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().jasperReport("PostDatedCheque", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Post Dated Cheque Detail Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnPdfAction() {
        setMessage("");
        String report = "Post Dated Cheque Detail Report";
        try {

            if (this.frcalDate == null) {
                setMessage("Please Enter From Date.");
                return;
            }
            if (this.tocalDate == null) {
                setMessage("Please Enter To Date.");
                return;
            }
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", sdf.format(this.frcalDate) + " to " + sdf.format(this.tocalDate));
            fillParams.put("pReportName", report);

            reportList = ddsReportRemote.getPostDatedChequieData(branch, ymd.format(frcalDate), ymd.format(tocalDate), reportType);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().openPdf("Post Dated Cheque Detail Report_" + ymd.format(frcalDate) + " to " + ymd.format(tocalDate), "PostDatedCheque", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
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

    public Date getFrcalDate() {
        return frcalDate;
    }

    public void setFrcalDate(Date frcalDate) {
        this.frcalDate = frcalDate;
    }

    public Date getTocalDate() {
        return tocalDate;
    }

    public void setTocalDate(Date tocalDate) {
        this.tocalDate = tocalDate;
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

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }
}
