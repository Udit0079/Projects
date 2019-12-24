package com.cbs.web.controller.report.locker;

import com.cbs.dto.report.LockerRentReportPojo;
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

public final class LockerRentDueReport extends BaseBean {

    private String message = "";
    private String branchCode;
    private String todayDate;
    private List<SelectItem> branchCodeList;
    LockerReportFacadeRemote beanRemote;
    private String cabNo;
    private int ordBy;
    private List<SelectItem> cabList;
    private List<SelectItem> orderByList;
    private boolean disableLetter;
    private String status;
    private boolean reportFlag;
    private List<SelectItem> statusList;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private NumberFormat integerFormat = new DecimalFormat("0");
    private TdReceiptManagementFacadeRemote beanFacade;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
   
    public LockerRentDueReport() {
        try {
            frmDt = dmy.format(new Date());
            toDt = dmy.format(new Date());
            beanRemote = (LockerReportFacadeRemote) ServiceLocator.getInstance().lookup("LockerReportFacade");
            beanFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            setDisableLetter(true);
            List brncode = beanFacade.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("P", "Paid"));
            statusList.add(new SelectItem("U", "Unpaid"));

            orderByList = new ArrayList<SelectItem>();
            orderByList.add(new SelectItem(0, "--Select--"));
            orderByList.add(new SelectItem(1, "Cabinet Number"));
            orderByList.add(new SelectItem(2, "Locker Type"));
            orderByList.add(new SelectItem(3, "Locker No"));
            orderByList.add(new SelectItem(4, "Customer Name"));
            orderByList.add(new SelectItem(5, "Rent Due Date"));
            orderByList.add(new SelectItem(6, "Issue Date"));
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

    public void onBlur() {
        if (status.equalsIgnoreCase("U")) {
            setDisableLetter(false);
            this.reportFlag = true;
        } else {
            setDisableLetter(true);
            this.reportFlag = false;
        }
    }
    private boolean validations() {
        try {
            setMessage("");
            if (ordBy == 0) {
                setMessage("Please select Order By");
                return false;
            }
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
                List<LockerRentReportPojo> lockerRentReport = beanRemote.getLockerRentReport("", branchCode, cabNo, ordBy, status, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)));
                if (lockerRentReport == null) {
                    setMessage("System error occurred");
                    return null;
                }
                if (!lockerRentReport.isEmpty()) {
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List ele = common.getBranchNameandAddress(getOrgnBrCode());
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        branchAddress = ele.get(1).toString();
                    }
                    String repName = "Locker Rent Due Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", branchAddress);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDt", frmDt + " to " + toDt);
                    new ReportBean().jasperReport("Locker_Rent_Due_Rep", "text/html", new JRBeanCollectionDataSource(lockerRentReport), fillParams, repName);
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

    public String printLetter() {
        if (validations()) {
            try {
                setDisableLetter(true);
                setReportFlag(true);
                String bankName = "";
                String branchAddress = "";
                String branchPinCode = "";
                List<LockerRentReportPojo> lockerRentReport = beanRemote.getLockerRentReport("", branchCode, cabNo, ordBy, status, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)));
                if (lockerRentReport == null) {
                    setMessage("System error occurred");
                    return null;
                }
                if (!lockerRentReport.isEmpty()) {
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List ele = common.getBranchNameandAddress(getOrgnBrCode());
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        branchAddress = ele.get(1).toString();
                    }
                    if (ele.get(2) != null) {
                        branchPinCode = ele.get(2).toString();
                    }
                    String report = "Locker Rent Due Letter";
                    Map fillParams = new HashMap();
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", branchAddress);
                    fillParams.put("pBranchPinCode", branchPinCode);
                    fillParams.put("pReportDt", getTodayDate());
                    fillParams.put("image", "/opt/images/logo.png");
                    new ReportBean().openPdf("Locker Rent Due Letter_" + ymd.format(dmy.parse(getTodayDate())), "LockerRentDueLetter", new JRBeanCollectionDataSource(lockerRentReport), fillParams);
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

    public String viewPdfReport() {
        if (validations()) {
            try {
                String bankName = "";
                String branchAddress = "";
                List<LockerRentReportPojo> lockerRentReport = beanRemote.getLockerRentReport("", branchCode, cabNo, ordBy, status, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)));
                if (lockerRentReport == null) {
                    setMessage("System error occurred");
                    return null;
                }
                if (!lockerRentReport.isEmpty()) {
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List ele = common.getBranchNameandAddress(getOrgnBrCode());
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        branchAddress = ele.get(1).toString();
                    }
                    String report = "Locker Rent Due Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", branchAddress);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDt", frmDt + " to " + toDt);
                    new ReportBean().openPdf("Locker Rent Due Report_" + ymd.format(dmy.parse(getTodayDate())), "Locker_Rent_Due_Rep", new JRBeanCollectionDataSource(lockerRentReport), fillParams);
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
        setCabNo("ALL");
        setBranchCode("0A");
        this.setOrdBy(0);
        this.setFrmDt(dmy.format(new Date()));
        this.setToDt(dmy.format(new Date()));
        this.setReportFlag(false);
        setDisableLetter(true);
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

    public int getOrdBy() {
        return ordBy;
    }

    public void setOrdBy(int ordBy) {
        this.ordBy = ordBy;
    }

    public List<SelectItem> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<SelectItem> orderByList) {
        this.orderByList = orderByList;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
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

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public boolean isDisableLetter() {
        return disableLetter;
    }

    public void setDisableLetter(boolean disableLetter) {
        this.disableLetter = disableLetter;
    }
}
