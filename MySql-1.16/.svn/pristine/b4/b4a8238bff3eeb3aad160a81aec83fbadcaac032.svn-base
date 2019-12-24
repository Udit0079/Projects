package com.cbs.web.controller.report.locker;

import com.cbs.dto.report.LockerStatementReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LockerReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public final class LockerStatementReport extends BaseBean {

    private String message = "";
    private String branchCode;
    private List<SelectItem> branchCodeList;
    LockerReportFacadeRemote beanRemote;
    private String cabNo;
    private String lockerType;
    private List<SelectItem> cabList;
    private List<SelectItem> lockerTypeList;
    private String lockerNo;
    private CommonReportMethodsRemote common;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private GeneralMasterFacadeRemote genFacadeRemote;

    public LockerStatementReport() {
        try {
            beanRemote = (LockerReportFacadeRemote) ServiceLocator.getInstance().lookup("LockerReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            genFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");

            List brnCode = common.getAlphacodeBasedOnBranch(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brnCode.isEmpty()) {
                for (int i = 0; i < brnCode.size(); i++) {
                    Vector brnVec = (Vector) brnCode.get(i);
                    branchCodeList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(brnVec.get(1).toString())), brnVec.get(0).toString()));
                }
            }

            cabList = new ArrayList<SelectItem>();
            cabList.add(new SelectItem("0", "--Select--"));
            lockerTypeList = new ArrayList<SelectItem>();
            
            lockerTypeList.add(new SelectItem("0", "--SELECT--"));
            List glList = genFacadeRemote.getRefCodeAndDescByNo("1500");
            if (glList.isEmpty()) {
                this.setMessage("Please fill data in CBS REF REC TYPE for 1500");
                return;
            } else {
                for (int i = 0; i < glList.size(); i++) {
                    Vector v1 = (Vector) glList.get(i);
                    lockerTypeList.add(new SelectItem(v1.get(0).toString(),v1.get(0).toString()));
                }
            }
//            lockerTypeList.add(new SelectItem("0", "--Select--"));
//            lockerTypeList.add(new SelectItem("A", "A"));
//            lockerTypeList.add(new SelectItem("B", "B"));
//            lockerTypeList.add(new SelectItem("C", "C"));
//            lockerTypeList.add(new SelectItem("D", "D"));
//            lockerTypeList.add(new SelectItem("E", "E"));
//            lockerTypeList.add(new SelectItem("F", "F"));
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    private NumberFormat integerFormat = new DecimalFormat("0");

    public void onChangeLockerType() {
        try {
            cabList = new ArrayList<SelectItem>();
            List list = beanRemote.getLockerCabinetNumbersByLockerType(lockerType, branchCode);
            cabList.add(new SelectItem("0", "--Select--"));
            Vector ele = null;
            for (int i = 0; i < list.size(); i++) {
                ele = (Vector) list.get(i);
                cabList.add(new SelectItem(ele.get(0).toString(), integerFormat.format(ele.get(0))));
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    private boolean validations() {
        try {
            Validator validator = new Validator();
            setMessage("");
            if (lockerType.equalsIgnoreCase("0")) {
                setMessage("Please select Order By");
                return false;
            }
            if (cabNo.equalsIgnoreCase("0") || !validator.validateDoublePositive(cabNo)) {
                setMessage("Please select Cabinet number");
                return false;
            }
            if (!validator.validateInteger(lockerNo)) {
                setMessage("Please fill Locker number");
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
                List<LockerStatementReportPojo> lockerStatementReport = beanRemote.getLockerStatementReport(lockerType, Double.parseDouble(cabNo), Double.parseDouble(lockerNo), branchCode);
                if (lockerStatementReport == null) {
                    setMessage("System error occurred");
                    return null;
                }
                if (!lockerStatementReport.isEmpty()) {
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List ele = common.getBranchNameandAddress(getOrgnBrCode());
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        branchAddress = ele.get(1).toString();
                    }
                    String repName = "Locker Statement Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", branchAddress);
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().jasperReport("Locker_Statement", "text/html", new JRBeanCollectionDataSource(lockerStatementReport), fillParams, repName);
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
                List<LockerStatementReportPojo> lockerStatementReport = beanRemote.getLockerStatementReport(lockerType, Double.parseDouble(cabNo), Double.parseDouble(lockerNo), branchCode);
                if (lockerStatementReport == null) {
                    setMessage("System error occurred");
                    return null;
                }
                if (!lockerStatementReport.isEmpty()) {
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List ele = common.getBranchNameandAddress(getOrgnBrCode());
                    if (ele.get(0) != null) {
                        bankName = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        branchAddress = ele.get(1).toString();
                    }
                    String report = "Locker Statement Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", branchAddress);
                    fillParams.put("pPrintedBy", getUserName());
                    new ReportBean().openPdf("Locker Statement Report_" + ymd.format(dmy.parse(getTodayDate())), "Locker_Statement", new JRBeanCollectionDataSource(lockerStatementReport), fillParams);
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
        cabList = new ArrayList<SelectItem>();
        cabList.add(new SelectItem("0", "--Select--"));
        setLockerType("0");
        setLockerNo("");
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

    public String getLockerType() {
        return lockerType;
    }

    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
    }

    public List<SelectItem> getLockerTypeList() {
        return lockerTypeList;
    }

    public void setLockerTypeList(List<SelectItem> lockerTypeList) {
        this.lockerTypeList = lockerTypeList;
    }

    public String getLockerNo() {
        return lockerNo;
    }

    public void setLockerNo(String lockerNo) {
        this.lockerNo = lockerNo;
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
}
