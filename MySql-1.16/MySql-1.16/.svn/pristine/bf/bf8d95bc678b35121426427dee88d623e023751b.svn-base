/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.other.MinimumBalSmsTo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherMiscReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class MinBalSmsReport extends BaseBean {

    private String message;
    private String branch;
    private String frDt;
    private String toDt;
    private List<SelectItem> branchList;
    private CommonReportMethodsRemote reportFacade;
    private OtherMiscReportFacadeRemote otherMiscRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public MinBalSmsReport() {
        try {
            reportFacade = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherMiscRemote = (OtherMiscReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherMiscReportFacade");

            branchList = new ArrayList<SelectItem>();
            branchList.add(new SelectItem("0", "--Select--"));

            String alphaCode = reportFacade.getAlphacodeByBrncode(getOrgnBrCode());
            if (alphaCode.equalsIgnoreCase("HO")) {
                branchList.add(new SelectItem("ALL", "ALL"));
            }

            List list = reportFacade.getAlphacodeAllAndBranch(getOrgnBrCode());
            if (list.isEmpty()) {
                this.setMessage("There is no branch to show the report.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                String brncode = ele.get(1).toString().trim().length() < 2 ? "0" + ele.get(1).toString().trim()
                        : ele.get(1).toString().trim();
                branchList.add(new SelectItem(brncode, ele.get(0).toString().trim()));
            }
            btnRefreshAction();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        this.setMessage("");
        String reportName = "Minimum Balance SMS Report";
        try {
            if (validateField()) {
                Map fillParams = new HashMap();
                List brNameAndAddList = reportFacade.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", reportName);
                fillParams.put("pReportDate", this.frDt + " to " + this.toDt);

                List<MinimumBalSmsTo> dataList = otherMiscRemote.getMinimumBalSmsReport(this.branch, ymd.format(dmy.parse(this.frDt)), ymd.format(dmy.parse(this.toDt)));
                if (dataList.isEmpty()) {
                    this.setMessage("There is no data to show the report");
                    return;
                }

                BigInteger totalSendSms = new BigInteger("0");
                BigInteger totalNotSendSms = new BigInteger("0");
                for (int i = 0; i < dataList.size(); i++) {
                    MinimumBalSmsTo to = dataList.get(i);
                    if (to.getSmsFlag().equalsIgnoreCase("Y")) {
                        totalSendSms = totalSendSms.add(new BigInteger("1"));
                    } else if (to.getSmsFlag().equalsIgnoreCase("N")) {
                        totalNotSendSms = totalNotSendSms.add(new BigInteger("1"));
                    }
                }
                fillParams.put("pTotalSendSms", totalSendSms.toString());
                fillParams.put("pTotalNotSendSms", totalNotSendSms.toString());

                new ReportBean().jasperReport("minimumbalsmsreport", "text/html", new JRBeanCollectionDataSource(dataList), fillParams, reportName);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    private boolean validateField() {
        try {
            if (this.branch == null || this.branch.equals("") || this.branch.equals("0")) {
                this.setMessage("Please select Branch.");
                return false;
            }
            if (this.frDt == null || this.toDt == null
                    || this.frDt.equals("") || this.toDt.equals("")) {
                this.setMessage("Please fill proper From Date and To Date.");
                return false;
            }
            Validator validator = new Validator();
            if (!validator.validateDate_dd_mm_yyyy(this.frDt)) {
                this.setMessage("Please fill proper From Date.");
                return false;
            }
            if (!validator.validateDate_dd_mm_yyyy(this.toDt)) {
                this.setMessage("Please fill proper To Date.");
                return false;
            }
            if (dmy.parse(this.frDt).compareTo(dmy.parse(this.toDt)) > 0) {
                this.setMessage("From Date can not be greater than To Date.");
                return false;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return true;
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setBranch("0");
        this.setFrDt(getTodayDate());
        this.setToDt(getTodayDate());
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    //Getter And Setter
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

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }
}
