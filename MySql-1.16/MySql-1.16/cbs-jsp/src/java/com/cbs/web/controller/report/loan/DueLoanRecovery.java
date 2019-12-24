/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DemandReportFacadeRemote;
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
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class DueLoanRecovery extends BaseBean {

    private String message;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    String frDt;
    private String days;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private DemandReportFacadeRemote dmdRemote;

    public DueLoanRecovery() {

        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            dmdRemote = (DemandReportFacadeRemote) ServiceLocator.getInstance().lookup("DemandReportFacade");

            branchCodeList = new ArrayList<SelectItem>();
            List allBranchCodeList = common.getBranchCodeList(getOrgnBrCode());
            if (!allBranchCodeList.isEmpty()) {
                for (int i = 0; i < allBranchCodeList.size(); i++) {
                    Vector vec = (Vector) allBranchCodeList.get(i);
                    branchCodeList.add(new SelectItem(vec.get(0).toString().length() < 2 ? "0" + vec.get(0).toString() : vec.get(0).toString(), vec.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
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

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void printAction() {
        setMessage("");
        try {

            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setMessage("Please fill As On Date !");
                return;
            }
            if (days == null || days.equalsIgnoreCase("")) {
                setMessage("Please fill Days !");
                return;
            }

            String reportName = "Loan Recovery System";
            List<OverdueEmiReportPojo> reportList = dmdRemote.getDueLoanRecoveryData(branchCode, ymd.format(dmy.parse(frDt)), days);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", reportName);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", frDt);
            fillParams.put("pPrintedBy", getUserName());
            new ReportBean().jasperReport("LoanRecoverySystem", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, reportName);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", reportName);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        setMessage("");
        try {

            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setMessage("Please fill As On Date !");
                return;
            }
            if (days == null || days.equalsIgnoreCase("")) {
                setMessage("Please fill Days !");
                return;
            }

            String reportName = "Loan Recovery System";
            List<OverdueEmiReportPojo> reportList = dmdRemote.getDueLoanRecoveryData(branchCode, ymd.format(dmy.parse(frDt)), days);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", reportName);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", frDt);
            fillParams.put("pPrintedBy", getUserName());
            new ReportBean().openPdf(reportName, "LoanRecoverySystem", new JRBeanCollectionDataSource(reportList), fillParams);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", reportName);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshAction() {
        setMessage("");
        setFrDt(dmy.format(new Date()));
        setDays("");
    }

    public String exitAction() {
        return "case1";
    }
}
