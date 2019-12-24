package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.DatedSecurityPojo;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.OtherLoanReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Zeeshan Waris
 */
public final class DatedSecurity extends BaseBean {

    private String message;
    OtherLoanReportFacadeRemote beanRemote;
    private String repType;
    private String days;
    private boolean daysDisable;
    private List<SelectItem> reportTypeList;
    private String securityType;
    private List<SelectItem> securityTypeList;
    private String acNature;
    private List<SelectItem> acNatureList;
    private String acType;
    private List<SelectItem> acTypeList;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    String calFromDate;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private TdReceiptManagementFacadeRemote beanFacade;
    private DDSReportFacadeRemote ddsRemote;

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

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public String getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(String calFromDate) {
        this.calFromDate = calFromDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public boolean isDaysDisable() {
        return daysDisable;
    }

    public void setDaysDisable(boolean daysDisable) {
        this.daysDisable = daysDisable;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public List<SelectItem> getSecurityTypeList() {
        return securityTypeList;
    }

    public void setSecurityTypeList(List<SelectItem> securityTypeList) {
        this.securityTypeList = securityTypeList;
    }

    public DatedSecurity() {
        try {
            daysDisable = true;
            beanRemote = (OtherLoanReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherLoanReportFacade");
            beanFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");

            securityTypeList = new ArrayList<SelectItem>();
            securityTypeList.add(new SelectItem("0", "--Select--"));
            securityTypeList.add(new SelectItem("DATED", "DATED"));
            securityTypeList.add(new SelectItem("NON-DATED", "NON-DATED"));

            List brncode = new ArrayList();
            brncode = beanFacade.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            
            acNatureList = new ArrayList<SelectItem>();
            acNatureList.add(new SelectItem("--Select--"));
            List list = ddsRemote.getAccountNatureClassification("'D','B'");
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                acNatureList.add(new SelectItem(element.get(0).toString()));
            }

            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onDatedBular() {

        if (securityType.equalsIgnoreCase("DATED")) {
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("0", "ALL ACTIVE"));
            reportTypeList.add(new SelectItem("1", "ALL EXPIRED"));
            reportTypeList.add(new SelectItem("2", "EXPIRED IN"));

        } else if (securityType.equalsIgnoreCase("NON-DATED")) {
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("0", "ALL ACTIVE"));
            reportTypeList.add(new SelectItem("1", "ALL EXPIRED"));

        }
    }

    public void getAcTypeByAcNature() {
        try {
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("All"));
            List actCodeList = ddsRemote.getAccountCodeByClassificationAndNature("'D','B'", this.acNature);
            if (!actCodeList.isEmpty()) {
                for (int i = 0; i < actCodeList.size(); i++) {
                    Vector vec = (Vector) actCodeList.get(i);
                    acTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    int day = 0;
    Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

    public String printAction() {
        setMessage("");
        try {
            if (!Validator.validateDate(calFromDate)) {
                message = "Please check As On Date";
                return null;
            }
            if (repType.equalsIgnoreCase("0")) {
                repType = "ALL ACTIVE";
                day = 0;
            } else if (repType.equalsIgnoreCase("1")) {
                repType = "ALL EXPIRED";
                day = 0;
            } else if (repType.equalsIgnoreCase("2")) {
                repType = "EXPIRED IN";
                try {
                    if (days == null || days.equalsIgnoreCase("") || days.length() == 0) {
                        this.setMessage("Please Enter Days");
                        return null;
                    }
                    Matcher limitCheck = p.matcher(days);
                    if (!limitCheck.matches()) {
                        this.setMessage("Please enter valid days.");
                        return null;
                    }
                    if (days.contains(".")) {
                        this.setMessage("Please enter valid days.");
                        return null;
                    }
                    if (Integer.parseInt(days) < 0) {
                        this.setMessage("Days cannot be less than zero.");
                        return null;
                    }
                    day = Integer.parseInt(days);
                } catch (Exception e) {
                    this.setMessage("Please Enter Numeric Value In Days");
                }
            }
            String dt[] = calFromDate.split("/");
            String fromDate = dt[2] + dt[1] + dt[0];
            List<DatedSecurityPojo> datedSecurity = beanRemote.datedSecurity(fromDate, day,securityType,repType, branchCode,acNature,acType);
            if (!datedSecurity.isEmpty()) {
                if (repType.equalsIgnoreCase("0")) {
                    repType = "ALL ACTIVE";
                } else if (repType.equalsIgnoreCase("1")) {
                    repType = "ALL EXPIRED";
                } else if (repType.equalsIgnoreCase("2")) {
                    repType = "EXPIRED IN";
                }
                String repName = "Dated Security Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", calFromDate);
                fillParams.put("pSecurityType", repType);
                fillParams.put("pSecType", securityType);
                fillParams.put("pDays", day + "");
                new ReportBean().jasperReport("CBS_REP_DATED_SECURITY", "text/html", new JRBeanCollectionDataSource(datedSecurity), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }
    
    public String printExcelReport() {
        setMessage("");
        try {
            if (!Validator.validateDate(calFromDate)) {
                message = "Please check As On Date";
                return null;
            }
            if (repType.equalsIgnoreCase("0")) {
                repType = "ALL ACTIVE";
                day = 0;
            } else if (repType.equalsIgnoreCase("1")) {
                repType = "ALL EXPIRED";
                day = 0;
            } else if (repType.equalsIgnoreCase("2")) {
                repType = "EXPIRED IN";
                try {
                    if (days == null || days.equalsIgnoreCase("") || days.length() == 0) {
                        this.setMessage("Please Enter Days");
                        return null;
                    }
                    Matcher limitCheck = p.matcher(days);
                    if (!limitCheck.matches()) {
                        this.setMessage("Please enter valid days.");
                        return null;
                    }
                    if (days.contains(".")) {
                        this.setMessage("Please enter valid days.");
                        return null;
                    }
                    if (Integer.parseInt(days) < 0) {
                        this.setMessage("Days cannot be less than zero.");
                        return null;
                    }
                    day = Integer.parseInt(days);
                } catch (Exception e) {
                    this.setMessage("Please Enter Numeric Value In Days");
                }
            }
            String dt[] = calFromDate.split("/");
            String fromDate = dt[2] + dt[1] + dt[0];
            List<DatedSecurityPojo> datedSecurity = beanRemote.datedSecurity(fromDate, day,securityType,repType, branchCode,acNature,acType);
            if (!datedSecurity.isEmpty()) {
                if (repType.equalsIgnoreCase("0")) {
                    repType = "ALL ACTIVE";
                } else if (repType.equalsIgnoreCase("1")) {
                    repType = "ALL EXPIRED";
                } else if (repType.equalsIgnoreCase("2")) {
                    repType = "EXPIRED IN";
                }
                String repName = "Dated Security Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", calFromDate);
                fillParams.put("pSecurityType", repType);
                fillParams.put("pSecType", securityType);
                fillParams.put("pDays", day + "");
                new ReportBean().dynamicExcelJasper("CBS_REP_DATED_SECURITY_Excel", "excel", new JRBeanCollectionDataSource(datedSecurity), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String pdfDownLoad() {
        setMessage("");
        try {
            if (!Validator.validateDate(calFromDate)) {
                message = "Please check As On Date";
                return null;
            }
            if (repType.equalsIgnoreCase("0")) {
                repType = "ALL ACTIVE";
                day = 0;
            } else if (repType.equalsIgnoreCase("1")) {
                repType = "ALL EXPIRED";
                day = 0;
            } else if (repType.equalsIgnoreCase("2")) {
                repType = "EXPIRED IN";
                try {
                    if (days == null || days.equalsIgnoreCase("") || days.length() == 0) {
                        this.setMessage("Please Enter Days");
                        return null;
                    }
                    Matcher limitCheck = p.matcher(days);
                    if (!limitCheck.matches()) {
                        this.setMessage("Please enter valid days.");
                        return null;
                    }
                    if (days.contains(".")) {
                        this.setMessage("Please enter valid days.");
                        return null;
                    }
                    if (Integer.parseInt(days) < 0) {
                        this.setMessage("Days cannot be less than zero.");
                        return null;
                    }
                    day = Integer.parseInt(days);
                } catch (Exception e) {
                    this.setMessage("Please Enter Numeric Value In Days");
                }
            }
            String dt[] = calFromDate.split("/");
            String fromDate = dt[2] + dt[1] + dt[0];
            List<DatedSecurityPojo> datedSecurity = beanRemote.datedSecurity(fromDate, day,securityType ,repType, branchCode,acNature,acType);
            if (!datedSecurity.isEmpty()) {
                if (repType.equalsIgnoreCase("0")) {
                    repType = "ALL ACTIVE";
                } else if (repType.equalsIgnoreCase("1")) {
                    repType = "ALL EXPIRED";
                } else if (repType.equalsIgnoreCase("2")) {
                    repType = "EXPIRED IN";
                }
                String report = "Dated Security Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", calFromDate);
                fillParams.put("pSecurityType", repType);
                fillParams.put("pSecType", securityType);
                fillParams.put("pDays", day + "");
                new ReportBean().openPdf("Dated Security Report_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "CBS_REP_DATED_SECURITY", new JRBeanCollectionDataSource(datedSecurity), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public void dropDownprocessValueChange() {
        try {
            if (repType.equalsIgnoreCase("2")) {
                daysDisable = false;
            } else if (repType.equalsIgnoreCase("1") || repType.equalsIgnoreCase("0")) {
                daysDisable = true;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshAction() {
        try {
            setMessage("");
            setDays("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}
