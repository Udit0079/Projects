/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.TdActiveReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class PrincipalWiseFdDetail extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String calFromDate;
    private String caltoDate;
    private String frAmt;
    private String toAmt;
    private String repType;
    private List<SelectItem> repList;
    private String dateType;
    private List<SelectItem> dateTypeList;
    private String actCatgory;
    private List<SelectItem> actCatgoryList;
    private boolean disDateType;
    Date dt = new Date();
    private CommonReportMethodsRemote commonBeanRemote;
    private DDSReportFacadeRemote DDSReportRemote;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    List<TdActiveReportPojo> reportList = new ArrayList<TdActiveReportPojo>();

    public PrincipalWiseFdDetail() {

        try {
            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            DDSReportRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");

            setCalFromDate(dmyFormat.format(dt));
            setCaltoDate(dmyFormat.format(dt));
            setMessage("");

            List brnLst = new ArrayList();
            brnLst = commonBeanRemote.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            dateTypeList = new ArrayList<SelectItem>();
            dateTypeList.add(new SelectItem("S", "--Select--"));
            dateTypeList.add(new SelectItem("B", "Date Between"));
            dateTypeList.add(new SelectItem("A", "Date As on"));

            repList = new ArrayList<SelectItem>();
            repList.add(new SelectItem("Se", "--Select--"));
            repList.add(new SelectItem("De", "Detail"));
            repList.add(new SelectItem("Su", "Summary"));

            actCatgoryList = new ArrayList<SelectItem>();
            actCatgoryList.add(new SelectItem("A", "ALL"));
            actCatgoryList.add(new SelectItem("M", "Member"));
            actCatgoryList.add(new SelectItem("N", "Not Member"));

            refreshAction();


        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void dateTypeOnBlur() {
        setMessage("");
        if (dateType.equalsIgnoreCase("A")) {
            this.setDisDateType(true);
        } else {
            this.setDisDateType(false);
        }
    }

    public void printAction() {
        this.setMessage("");
        String branchName = "", address = "", report = "";
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (dateType == null || dateType.equalsIgnoreCase("S")) {
                setMessage("Please select Report Option !");
                return;
            }

            if (!dateType.equalsIgnoreCase("A")) {
                if (calFromDate == null || calFromDate.equalsIgnoreCase("")) {
                    setMessage("Please fill from date !");
                    return;
                }

                if (!Validator.validateDate(calFromDate)) {
                    setMessage("Please fill proper from date ! ");
                    return;
                }
            }

            if (caltoDate == null || caltoDate.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(caltoDate)) {
                setMessage("Please fill proper to date ! ");
                return;
            }
            if (!dateType.equalsIgnoreCase("A")) {
                if (dmyFormat.parse(calFromDate).after(dmyFormat.parse(caltoDate))) {
                    setMessage("From date should be less than To date !");
                    return;
                }
            }

            Matcher frAmtCheck = p.matcher(this.frAmt);
            if (!frAmtCheck.matches()) {
                setMessage("Invalid From Amount fill up !");
                return;
            }

            Matcher toAmtCheck = p.matcher(this.toAmt);
            if (!toAmtCheck.matches()) {
                setMessage("Invalid To Amount fill up !");
                return;
            }

            if (Double.parseDouble(frAmt) > Double.parseDouble(toAmt)) {
                setMessage("To Amount should be greater than From Amount !");
                return;
            }
            if (repType.equalsIgnoreCase("Se")) {
                setMessage("Please select Report Type !");
                return;
            }
            
  
            List brnAddrList = new ArrayList();
            brnAddrList = commonBeanRemote.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            report = "Principal Wise Fd Detail";
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);
            fillParams.put("pReportDt", dmyFormat.format(dmyFormat.parse(calFromDate)) + " to " + dmyFormat.format(dmyFormat.parse(caltoDate)));
            fillParams.put("pPrintedBy", this.getUserName());

            reportList = DDSReportRemote.principalWiseFdDetailData(branch, ymdFormat.format(dmyFormat.parse(calFromDate)), ymdFormat.format(dmyFormat.parse(caltoDate)), frAmt, toAmt, repType, dateType,actCatgory);

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist!");
            }
            if (repType.equalsIgnoreCase("Su")) {
                new ReportBean().jasperReport("PrincipalWiseFdDetail_Summary", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, report);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

            } else {
                new ReportBean().jasperReport("PrincipalWiseFdDetail", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, report);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void pdfDownLoad() {
        this.setMessage("");
        String branchName = "", address = "", report = "";
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (dateType == null || dateType.equalsIgnoreCase("S")) {
                setMessage("Please select Report Option !");
                return;
            }

            if (!dateType.equalsIgnoreCase("A")) {
                if (calFromDate == null || calFromDate.equalsIgnoreCase("")) {
                    setMessage("Please fill from date !");
                    return;
                }

                if (!Validator.validateDate(calFromDate)) {
                    setMessage("Please fill proper from date ! ");
                    return;
                }
            }

            if (caltoDate == null || caltoDate.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(caltoDate)) {
                setMessage("Please fill proper to date ! ");
                return;
            }

            if (!dateType.equalsIgnoreCase("A")) {
                if (dmyFormat.parse(calFromDate).after(dmyFormat.parse(caltoDate))) {
                    setMessage("From date should be less than To date !");
                    return;
                }
            }

            Matcher frAmtCheck = p.matcher(this.frAmt);
            if (!frAmtCheck.matches()) {
                setMessage("Invalid From Amount fill up !");
                return;
            }

            Matcher toAmtCheck = p.matcher(this.toAmt);
            if (!toAmtCheck.matches()) {
                setMessage("Invalid To Amount fill up !");
                return;
            }

            if (Double.parseDouble(frAmt) > Double.parseDouble(toAmt)) {
                setMessage("To Amount should be greater than From Amount !");
                return;
            }
            if (repType.equalsIgnoreCase("Se")) {
                setMessage("Please select Report Type !");
                return;
            }

            List brnAddrList = new ArrayList();
            brnAddrList = commonBeanRemote.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            report = "Principal Wise Fd Detail";
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", branchName);
            fillParams.put("pBankAddress", address);
            fillParams.put("pReportDt", dmyFormat.format(dmyFormat.parse(calFromDate)) + " to " + dmyFormat.format(dmyFormat.parse(caltoDate)));
            fillParams.put("pPrintedBy", this.getUserName());

            reportList = DDSReportRemote.principalWiseFdDetailData(branch, ymdFormat.format(dmyFormat.parse(calFromDate)), ymdFormat.format(dmyFormat.parse(caltoDate)), frAmt, toAmt, repType, dateType,actCatgory);

            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist!");
            }
            if (repType.equalsIgnoreCase("Su")) {
                new ReportBean().openPdf("Principal Wise Fd Summary", "PrincipalWiseFdDetail_Summary", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else {
                new ReportBean().openPdf("Principal Wise Fd Detail", "PrincipalWiseFdDetail", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }

            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Principal Wise Fd Detail");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void refreshAction() {
        setMessage("");
        this.setCalFromDate(dmyFormat.format(dt));
        this.setCaltoDate(dmyFormat.format(dt));
        this.setFrAmt("");
        this.setToAmt("");
        this.setRepType("Se");
        this.setDateType("S");
        this.setDisDateType(false);
    }

    public String exitAction() {
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

    public String getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(String calFromDate) {
        this.calFromDate = calFromDate;
    }

    public String getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(String caltoDate) {
        this.caltoDate = caltoDate;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepList() {
        return repList;
    }

    public void setRepList(List<SelectItem> repList) {
        this.repList = repList;
    }

    public String getFrAmt() {
        return frAmt;
    }

    public void setFrAmt(String frAmt) {
        this.frAmt = frAmt;
    }

    public String getToAmt() {
        return toAmt;
    }

    public void setToAmt(String toAmt) {
        this.toAmt = toAmt;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public List<SelectItem> getDateTypeList() {
        return dateTypeList;
    }

    public void setDateTypeList(List<SelectItem> dateTypeList) {
        this.dateTypeList = dateTypeList;
    }

    public boolean isDisDateType() {
        return disDateType;
    }

    public void setDisDateType(boolean disDateType) {
        this.disDateType = disDateType;
    }

    public String getActCatgory() {
        return actCatgory;
    }

    public void setActCatgory(String actCatgory) {
        this.actCatgory = actCatgory;
    }

    public List<SelectItem> getActCatgoryList() {
        return actCatgoryList;
    }

    public void setActCatgoryList(List<SelectItem> actCatgoryList) {
        this.actCatgoryList = actCatgoryList;
    }
}
