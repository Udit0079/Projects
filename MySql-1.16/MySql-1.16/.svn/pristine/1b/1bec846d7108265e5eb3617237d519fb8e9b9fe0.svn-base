/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.AccountEditHistoryPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
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
 * @author Ankit Verma
 */
public class AccountEditHistory extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String reportOpt;
    private List<SelectItem> reportOptList;
    private String acno, acNoLen;
    private Date calFromDate;
    private Date caltoDate;
    MiscReportFacadeRemote miscFacade;
    private CommonReportMethodsRemote common;

    /**
     * Creates a new instance of AccountEditHistory
     */
    public AccountEditHistory() {
        try {
            miscFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            this.setAcNoLen(getAcNoLength());
            branchList = new ArrayList<SelectItem>();
            List list = common.getAlphacodeBasedOnBranch(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
            }
            reportOptList = new ArrayList<SelectItem>();
            reportOptList.add(new SelectItem("S", "--Select--"));
            reportOptList.add(new SelectItem("C", "Column Wise"));
            reportOptList.add(new SelectItem("R", "Row Wise"));
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

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

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getReportOpt() {
        return reportOpt;
    }

    public void setReportOpt(String reportOpt) {
        this.reportOpt = reportOpt;
    }

    public List<SelectItem> getReportOptList() {
        return reportOptList;
    }

    public void setReportOptList(List<SelectItem> reportOptList) {
        this.reportOptList = reportOptList;
    }

    public String printAction() {
        Validator validator = new Validator();
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (acno.equalsIgnoreCase("")) {
                message = "Please insert Account no.";
                return null;
            }
            //if (acno.length() < 12) {
            if (!this.acno.equalsIgnoreCase("") && ((this.acno.length() != 12) && (this.acno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                message = "Please insert valid Account no.";
                return null;
            }
            if (!validator.validateStringAllNoSpace(acno)) {
                message = "Please insert valid Account no.";
                return null;
            }
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (caltoDate.before(calFromDate)) {
                setMessage("From Date can not be greater than To Date.");;
                return null;
            }

            if (reportOpt == null || reportOpt.equalsIgnoreCase("S")) {
                setMessage("Please Select Report Option");;
                return null;
            }
            FtsPostingMgmtFacadeRemote fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String currentBrnCode = fts.getCurrentBrnCode(acno);
            if (!currentBrnCode.equalsIgnoreCase(branch)) {
                setMessage("Please enter your own branch account no.");;
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String FromDt = sdf.format(calFromDate);
            String ToDate = sdf.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;
            List<AccountEditHistoryPojo> accountEditHistory = miscFacade.getAccountEditHistoryReport(acno, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate));
            if (!accountEditHistory.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
                String repName = "Account Edit History";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pBankName", branchNameandAddress.get(0).toString());
                fillParams.put("pBankAdd", branchNameandAddress.get(1).toString());
                fillParams.put("pPrintedDate", duration);
                fillParams.put("pPrintedBy", getUserName());
                String jrxmlName = "AccEditHistoryRow";
                if (reportOpt.equalsIgnoreCase("R")) {
                    jrxmlName = "AccEditHistoryRow";
                } else {
                    jrxmlName = "AccEditHistory";
                }
                new ReportBean().jasperReport(jrxmlName, "text/html", new JRBeanCollectionDataSource(accountEditHistory), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void viewPdfReport() {
        Validator validator = new Validator();
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (acno.equalsIgnoreCase("")) {
                message = "Please insert Account no.";
                return;
            }
            //if (acno.length() < 12) {
            if (!this.acno.equalsIgnoreCase("") && ((this.acno.length() != 12) && (this.acno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                message = "Please insert valid Account no.";
                return;
            }
            if (!validator.validateStringAllNoSpace(acno)) {
                message = "Please insert valid Account no.";
                return;
            }
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return;
            }
            if (caltoDate.before(calFromDate)) {
                setMessage("From Date can not be greater than To Date.");;
                return;
            }
            if (reportOpt == null || reportOpt.equalsIgnoreCase("S")) {
                setMessage("Please Select Report Option");;
                return;
            }
            FtsPostingMgmtFacadeRemote fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String currentBrnCode = fts.getCurrentBrnCode(acno);
            if (!currentBrnCode.equalsIgnoreCase(branch)) {
                setMessage("Please enter your own branch account no.");;
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String FromDt = sdf.format(calFromDate);
            String ToDate = sdf.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;
            List<AccountEditHistoryPojo> accountEditHistory = miscFacade.getAccountEditHistoryReport(acno, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate));
            if (!accountEditHistory.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
                String report = "Account Edit History";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pBankName", branchNameandAddress.get(0).toString());
                fillParams.put("pBankAdd", branchNameandAddress.get(1).toString());
                fillParams.put("pPrintedDate", duration);
                fillParams.put("pPrintedBy", getUserName());
                String jrxmlName = "AccEditHistoryRow";
                if (reportOpt.equalsIgnoreCase("R")) {
                    jrxmlName = "AccEditHistoryRow";
                } else {
                    jrxmlName = "AccEditHistory";
                }
                new ReportBean().openPdf("Account Edit History_" + ymdFormat.format(sdf.parse(getTodayDate())), jrxmlName, new JRBeanCollectionDataSource(accountEditHistory), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshAction() {
        setAcno("");
        setCalFromDate(null);
        setCaltoDate(null);
        setMessage("");
        setReportOpt("S");
    }

    public String exitAction() {
        return "case1";
    }
}
