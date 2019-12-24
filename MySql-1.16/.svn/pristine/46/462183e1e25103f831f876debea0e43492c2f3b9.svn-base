/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.pojo.ExpenditureBalDayWisePojo;
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
public class ExpenditureBalDayWise extends BaseBean {

    private String message;
    private Date fromDate = new Date();
    private Date toDate = new Date();
    private String branchCode;
    private String amt;
    private List<SelectItem> branchCodeList;
    private String amtReqOption;
    private List<SelectItem> amtReqOptionList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private CommonReportMethodsRemote RemoteCode;
    private GLReportFacadeRemote glBeanRemote;
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    List<ExpenditureBalDayWisePojo> repList = new ArrayList<ExpenditureBalDayWisePojo>();

    public ExpenditureBalDayWise() {

        try {
            RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            amtReqOptionList = new ArrayList<SelectItem>();
            amtReqOptionList.add(new SelectItem("0", "--Select--"));
            amtReqOptionList.add(new SelectItem("G", "Greater than"));
            amtReqOptionList.add(new SelectItem("L", "Less than"));
            amtReqOptionList.add(new SelectItem("E", "Equal to"));

            reportTypeList = new ArrayList<>();
            reportTypeList.add(new SelectItem("0", "--Select--"));
            reportTypeList.add(new SelectItem("1", "Without Voucher Wise"));
            reportTypeList.add(new SelectItem("2", "With Voucher Wise"));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewPdfReport() {
        setMessage("");
        try {
            String bankName = "", branchAddress = "";
            List ele = RemoteCode.getBranchNameandAddress(getOrgnBrCode());
            if (ele.get(0) != null) {
                bankName = ele.get(0).toString();
            }
            if (ele.get(1) != null) {
                branchAddress = ele.get(1).toString();
            }
            String repName = "Daily Wise Expense Report";
            Map fillParams = new HashMap();
            fillParams.put("pReportName", repName);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDate", dmyFormat.format(fromDate) + " to " + dmyFormat.format(toDate));
            fillParams.put("pbankName", bankName);
            fillParams.put("pbankAddress", branchAddress);
            repList = glBeanRemote.getExpenditureData(branchCode, ymdFormat.format(fromDate), ymdFormat.format(toDate), amt, amtReqOption,reportType);
            new ReportBean().openPdf("Daily Wise Expense Report_" + ymdFormat.format(fromDate) + " to " + ymdFormat.format(toDate), "Exp_Details", new JRBeanCollectionDataSource(repList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", repName);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }

    public void refresh() {
        try {
            this.setMessage("");
            this.setFromDate(new Date());
            this.setToDate(new Date());
            this.setAmt("");

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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
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

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getAmtReqOption() {
        return amtReqOption;
    }

    public void setAmtReqOption(String amtReqOption) {
        this.amtReqOption = amtReqOption;
    }

    public List<SelectItem> getAmtReqOptionList() {
        return amtReqOptionList;
    }

    public void setAmtReqOptionList(List<SelectItem> amtReqOptionList) {
        this.amtReqOptionList = amtReqOptionList;
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
