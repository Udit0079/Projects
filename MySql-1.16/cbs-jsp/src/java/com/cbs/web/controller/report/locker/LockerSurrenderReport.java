package com.cbs.web.controller.report.locker;

import com.cbs.dto.report.LockerSurrenderReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LockerReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

public final class LockerSurrenderReport extends BaseBean {

    private String message = "";
    private String branchCode;
    private List<SelectItem> branchCodeList;
    LockerReportFacadeRemote beanRemote;
    private String option;
    private String cabNo;
    private List<SelectItem> cabList;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    //private boolean disableCabNo = true;
    private NumberFormat integerFormat = new DecimalFormat("0");
    private TdReceiptManagementFacadeRemote beanFacade;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public LockerSurrenderReport() {
        try {
            frmDt = dmy.format(new Date());
            toDt = dmy.format(new Date());
            beanRemote = (LockerReportFacadeRemote) ServiceLocator.getInstance().lookup("LockerReportFacade");
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

    public void onChangeOption() {
        try {
            cabList = new ArrayList<SelectItem>();
            cabList.add(new SelectItem("ALL", "ALL"));
            List list = beanRemote.getLockerCabinetNumbers(branchCode);
            Vector ele = null;
            for (int i = 0; i < list.size(); i++) {
                ele = (Vector) list.get(i);
                cabList.add(new SelectItem(ele.get(0).toString(), integerFormat.format(ele.get(0))));
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    private boolean validations() {
        try {
            setMessage("");

//            if (option == null) {
//                setMessage("Please select an option from Cabinet wise or All");
//                return false;
//            }
//            if (option.equalsIgnoreCase("Cabinet Wise") && cabNo.equalsIgnoreCase("0")) {
//                setMessage("Please select Cabinet number");
//                return false;
//            }
            
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
//            
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
                List<LockerSurrenderReportPojo> lockerSurrenderReport = beanRemote.getLockerSurrenderReport("", cabNo, branchCode, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)));
                if (lockerSurrenderReport == null) {
                    setMessage("System error occurred");
                    return null;
                }
                if (!lockerSurrenderReport.isEmpty()) {
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List ele = common.getBranchNameandAddress(getOrgnBrCode());
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        branchAddress = ele.get(1).toString();
                    }
                    String repName = "Locker Surrender Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", branchAddress);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", repName);
                    fillParams.put("pReportDt", frmDt + " to " + toDt);
                    new ReportBean().jasperReport("LockerSurrenderReport", "text/html", new JRBeanCollectionDataSource(lockerSurrenderReport), fillParams, repName);
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

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public String viewPdfReport() {
        if (validations()) {
            try {
                String bankName = "";
                String branchAddress = "";
                List<LockerSurrenderReportPojo> lockerSurrenderReport = beanRemote.getLockerSurrenderReport("", cabNo, branchCode, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)));
                if (lockerSurrenderReport == null) {
                    setMessage("System error occurred");
                    return null;
                }
                if (!lockerSurrenderReport.isEmpty()) {
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List ele = common.getBranchNameandAddress(getOrgnBrCode());
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        branchAddress = ele.get(1).toString();
                    }
                    String report = "Locker Surrender Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", branchAddress);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pReportDt", frmDt + " to " + toDt);
                    new ReportBean().openPdf("Locker Surrender Report_" + ymd.format(dmy.parse(getTodayDate())), "LockerSurrenderReport", new JRBeanCollectionDataSource(lockerSurrenderReport), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getRequest().getSession();
                    httpSession.setAttribute("ReportName", report);
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
        setCabNo("0");
        setOption(null);
        // setDisableCabNo(true);
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

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getCabNo() {
        return cabNo;
    }

    public void setCabNo(String cabNo) {
        this.cabNo = cabNo;
    }

    public List<SelectItem> getCabList() {
        return cabList;
    }

    public void setCabList(List<SelectItem> cabList) {
        this.cabList = cabList;
    }

//    public boolean isDisableCabNo() {
//        return disableCabNo;
//    }
//
//    public void setDisableCabNo(boolean disableCabNo) {
//        this.disableCabNo = disableCabNo;
//    }
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
