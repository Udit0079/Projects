/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
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
public class GuarantorOverdue extends BaseBean {

    String message;
    Date calDate;
    private String branch;
    private List<SelectItem> branchList;
    private LoanReportFacadeRemote loanReportRemort;
    private CommonReportMethodsRemote common;
    private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
            dmyFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private FtsPostingMgmtFacadeRemote ftsPosting;

    public GuarantorOverdue() {

        try {
            loanReportRemort = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsPosting = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            branchList = new ArrayList<SelectItem>();
            List list = common.getBranchCodeList(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector brnVector = (Vector) list.get(i);
                branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
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
        setMessage("");
        try {
            String report = "Guarantor Overdue Report";
            Map fillParams = new HashMap();
            List<OverdueEmiReportPojo> overDueDetails = loanReportRemort.getGuarantorOverdueData(branch, ymdFormatter.format(calDate));
            if (overDueDetails.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            for (OverdueEmiReportPojo pojo : overDueDetails) {
                System.out.println("Cust Id " + pojo.getCustId() + " Acno " + pojo.getAccountNumber());
            }
            String bnkIdenty = ftsPosting.getCodeFromCbsParameterInfo("GRACE_PD_APP_IN_OVERDUE");
            fillParams.put("pReportDate", dmyFormatter.format(calDate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pIsPrint", bnkIdenty);
            new ReportBean().jasperReport("OverDue", "text/html", new JRBeanCollectionDataSource(overDueDetails), fillParams, "Guarantor Overdue Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnPdfAction() {
        setMessage("");
        try {

            String report = "Guarantor Overdue Report";
            Map fillParams = new HashMap();
            List<OverdueEmiReportPojo> overDueDetails = loanReportRemort.getGuarantorOverdueData(branch, ymdFormatter.format(calDate));
            if (overDueDetails.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            for (OverdueEmiReportPojo pojo : overDueDetails) {
                System.out.println("Cust Id " + pojo.getCustId() + " Acno " + pojo.getAccountNumber());
            }
            String bnkIdenty = ftsPosting.getCodeFromCbsParameterInfo("GRACE_PD_APP_IN_OVERDUE");
            fillParams.put("pReportDate", dmyFormatter.format(calDate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pIsPrint", bnkIdenty);

            new ReportBean().openPdf("Guarantor Overdue Report", "OverDue", new JRBeanCollectionDataSource(overDueDetails), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshAction() {
        setMessage("");
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
}
