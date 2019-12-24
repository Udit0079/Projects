/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.OtherMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.pojo.MinorToMajorPojo;
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

public class MinorToMajorCustomerDetail extends BaseBean {

    private String message = "";
    private String branchCode;
    private String todayDate;
    private List<SelectItem> branchCodeList;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private TdReceiptManagementFacadeRemote beanFacade;
    private OtherMasterFacadeRemote othMasterRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public MinorToMajorCustomerDetail() {
        try {
            frmDt = dmy.format(new Date());
            toDt = dmy.format(new Date());
            othMasterRemote = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");
            beanFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            List brncode = beanFacade.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    private boolean validations(){
        try {
            setMessage("");
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                message = "Please enter from date";
                return false;
            }

            if (!Validator.validateDate(frmDt)) {
                message = "Please select Proper from date ";
                return false;
            }

            if (dmy.parse(frmDt).after(getDate())) {
                message = "From date should be less than current date !";
                return false;
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                message = "Please enter to date";
                return false;
            }

            if (!Validator.validateDate(toDt)) {
                message = "Please select Proper to date ";
                return false;
            }

            if (dmy.parse(toDt).after(getDate())) {
                message = "To date should be less than current date !";
                return false;
            }

            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                message = "Please from date should be less than to date";
                return false;
            }

            return true;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return false;
        }
    }

    public String printAction() {
        if (validations()) {
            try {
                String bankName = "";
                String branchAddress = "";
                List<MinorToMajorPojo> minorToMajorReport = othMasterRemote.getMinorToMajorData(branchCode, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)));
                if (minorToMajorReport == null) {
                    setMessage("System error occurred");
                    return null;
                }
                if (!minorToMajorReport.isEmpty()) {
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List ele = common.getBranchNameandAddress(getOrgnBrCode());
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        branchAddress = ele.get(1).toString();
                    }
                    String repName = "Minor To Major Customer Detail Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", branchAddress);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDt", frmDt + " to " + toDt);
                    fillParams.put("pPrintedDate", getTodayDate());
                    fillParams.put("pReportName", "Minor To Major Customer Detail Report");
                    new ReportBean().jasperReport("Minor_To_Major_Report", "text/html", new JRBeanCollectionDataSource(minorToMajorReport), fillParams, repName);
                    return "report";
                } else {
                    setMessage("No data to print !!");
                }
            } catch (ApplicationException e) {
                setMessage(e.getLocalizedMessage());
            } catch (Exception e) {
                setMessage(e.getLocalizedMessage());
            }
        }
        return null;
    }

    public String viewPdfReport() {
        if (validations()) {
            try {
                String bankName = "";
                String branchAddress = "";
                List<MinorToMajorPojo> minorToMajorReport = othMasterRemote.getMinorToMajorData(branchCode, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)));
                if (minorToMajorReport == null) {
                    setMessage("System error occurred");
                    return null;
                }
                if (!minorToMajorReport.isEmpty()) {
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List ele = common.getBranchNameandAddress(getOrgnBrCode());
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        branchAddress = ele.get(1).toString();
                    }
                    String report = "Minor To Major Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", branchAddress);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDt", frmDt + " to " + toDt);
                    fillParams.put("pPrintedDate", getTodayDate());
                    fillParams.put("pReportName", "Minor To Major Customer Detail");
                    new ReportBean().openPdf("Minor To Major Report_" + ymd.format(dmy.parse(getTodayDate())), "Minor_To_Major_Report", new JRBeanCollectionDataSource(minorToMajorReport), fillParams);
                    getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    getHttpSession().setAttribute("ReportName", report);

                } else {
                    setMessage("No data to print !!");
                }
            } catch (ApplicationException e) {
                setMessage(e.getLocalizedMessage());
            } catch (Exception e) {
                setMessage(e.getLocalizedMessage());
            }
        }
        return null;
    }

    public void resetAction() {
        setMessage("");
        setBranchCode("0A");
        this.setFrmDt(dmy.format(new Date()));
        this.setToDt(dmy.format(new Date()));
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
