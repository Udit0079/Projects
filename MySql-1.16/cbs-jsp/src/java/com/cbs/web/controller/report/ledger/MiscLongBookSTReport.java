package com.cbs.web.controller.report.ledger;

import com.cbs.dto.report.MiscLongBookSubTotalReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LedgerReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public final class MiscLongBookSTReport extends BaseBean {

    private String message;
    private String calDate;
    private boolean timeBased;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private List<SelectItem> fromHourList, fromMinuteList, toHourList, toMinuteList;
    private String fromHour, toHour, fromMinute, toMinute, fromMeridiem, toMeridiem;

    public MiscLongBookSTReport() {
        try {
            NumberFormat format = new DecimalFormat("00");
            fromHourList = new ArrayList<SelectItem>();
            fromMinuteList = new ArrayList<SelectItem>();
            toHourList = new ArrayList<SelectItem>();
            setCalDate(dmyFormat.format(new Date()));
            toMinuteList = new ArrayList<SelectItem>();
            for (int i = 0; i <= 12; i++) {
                fromHourList.add(new SelectItem(format.format(i), format.format(i)));
                toHourList.add(new SelectItem(format.format(i), format.format(i)));
            }
            for (int i = 0; i <= 60; i++) {
                fromMinuteList.add(new SelectItem(format.format(i), format.format(i)));
                toMinuteList.add(new SelectItem(format.format(i), format.format(i)));
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        setMessage("");
        String bankName = "";
        String branchAddress = "";
        String printTime1 = "";
        String printTime2 = "";
        String reportDate = calDate;
        String fromTime = "";
        String toTime = "";
        String repName = "Misc Long Book ST Report";
        Map fillParams = new HashMap();
        if (calDate.equalsIgnoreCase("")) {
            setMessage("Please enter date !!");
            return null;
        }
        try {
            String dt1[] = calDate.split("/");
            String frmDate = dt1[2] + "-" + dt1[1] + "-" + dt1[0];
            if (timeBased) {
                if (fromMeridiem.equalsIgnoreCase("AM")) {

                    fromTime = frmDate + " " + fromHour + ":" + fromMinute + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(fromHour) == 12) {
                        hour = Integer.parseInt(fromHour) + 11;
                    } else {
                        hour = Integer.parseInt(fromHour) + 12;
                    }
                    fromTime = frmDate + " " + hour + ":" + fromMinute + ":00";
                }
                if (toMeridiem.equalsIgnoreCase("AM")) {
                    toTime = frmDate + " " + toHour + ":" + toMinute + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(toHour) == 12) {
                        hour = Integer.parseInt(toHour) + 11;
                    } else {
                        hour = Integer.parseInt(toHour) + 12;
                    }
                    toTime = frmDate + " " + hour + ":" + toMinute + ":00";
                }
            }
            String dt2[] = calDate.split("/");
            String indate = dt2[2] + dt2[1] + dt2[0];
            LedgerReportFacadeRemote beanRemote = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup("LedgerReportFacade");
            List<MiscLongBookSubTotalReportPojo> miscLongBookSubTotalReport = beanRemote.getMiscLongBookSubTotalReport(indate, getOrgnBrCode(), fromTime, toTime, timeBased);
            if (miscLongBookSubTotalReport == null) {
                setMessage("System error occurred");
                return null;
            }
            if (!miscLongBookSubTotalReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                if (timeBased) {
                    if (fromHour.equalsIgnoreCase("00")) {
                        printTime1 = "12:" + fromMinute + " " + fromMeridiem;
                    } else {
                        printTime1 = fromHour + ":" + fromMinute + " " + fromMeridiem;
                    }
                    if (toHour.equalsIgnoreCase("00")) {
                        printTime2 = "12:" + toMinute + " " + toMeridiem;
                    } else {
                        printTime2 = toHour + ":" + toMinute + " " + toMeridiem;
                    }
                    reportDate = reportDate + " " + printTime1 + " To " + printTime2;
                }
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", repName);
                fillParams.put("pReportDate", reportDate);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                new ReportBean().jasperReport("misc_long_book_sub_report", "text/html", new JRBeanCollectionDataSource(miscLongBookSubTotalReport), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print !!");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String pdfDownLoad() {
        setMessage("");
        String bankName = "";
        String branchAddress = "";
        String printTime1 = "";
        String printTime2 = "";
        String reportDate = calDate;
        String fromTime = "";
        String toTime = "";
        String report = "Misc Long Book ST Report";
        Map fillParams = new HashMap();
        if (calDate.equalsIgnoreCase("")) {
            setMessage("Please enter date !!");
            return null;
        }
        try {
            String dt1[] = calDate.split("/");
            String frmDate = dt1[2] + "-" + dt1[1] + "-" + dt1[0];
            if (timeBased) {
                if (fromMeridiem.equalsIgnoreCase("AM")) {

                    fromTime = frmDate + " " + fromHour + ":" + fromMinute + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(fromHour) == 12) {
                        hour = Integer.parseInt(fromHour) + 11;
                    } else {
                        hour = Integer.parseInt(fromHour) + 12;
                    }
                    fromTime = frmDate + " " + hour + ":" + fromMinute + ":00";
                }
                if (toMeridiem.equalsIgnoreCase("AM")) {
                    toTime = frmDate + " " + toHour + ":" + toMinute + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(toHour) == 12) {
                        hour = Integer.parseInt(toHour) + 11;
                    } else {
                        hour = Integer.parseInt(toHour) + 12;
                    }
                    toTime = frmDate + " " + hour + ":" + toMinute + ":00";
                }
            }
            String dt2[] = calDate.split("/");
            String indate = dt2[2] + dt2[1] + dt2[0];
            LedgerReportFacadeRemote beanRemote = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup("LedgerReportFacade");
            List<MiscLongBookSubTotalReportPojo> miscLongBookSubTotalReport = beanRemote.getMiscLongBookSubTotalReport(indate, getOrgnBrCode(), fromTime, toTime, timeBased);
            if (miscLongBookSubTotalReport == null) {
                setMessage("System error occurred");
                return null;
            }
            if (!miscLongBookSubTotalReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                if (timeBased) {
                    if (fromHour.equalsIgnoreCase("00")) {
                        printTime1 = "12:" + fromMinute + " " + fromMeridiem;
                    } else {
                        printTime1 = fromHour + ":" + fromMinute + " " + fromMeridiem;
                    }
                    if (toHour.equalsIgnoreCase("00")) {
                        printTime2 = "12:" + toMinute + " " + toMeridiem;
                    } else {
                        printTime2 = toHour + ":" + toMinute + " " + toMeridiem;
                    }
                    reportDate = reportDate + " " + printTime1 + " To " + printTime2;
                }
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", reportDate);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                new ReportBean().openPdf("misc_long_book_sub_report_", "misc_long_book_sub_report", new JRBeanCollectionDataSource(miscLongBookSubTotalReport), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                setMessage("No data to print !!");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String exitAction() {
        return "case1";
    }

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromHour() {
        return fromHour;
    }

    public void setFromHour(String fromHour) {
        this.fromHour = fromHour;
    }

    public List<SelectItem> getFromHourList() {
        return fromHourList;
    }

    public void setFromHourList(List<SelectItem> fromHourList) {
        this.fromHourList = fromHourList;
    }

    public String getFromMeridiem() {
        return fromMeridiem;
    }

    public void setFromMeridiem(String fromMeridiem) {
        this.fromMeridiem = fromMeridiem;
    }

    public String getFromMinute() {
        return fromMinute;
    }

    public void setFromMinute(String fromMinute) {
        this.fromMinute = fromMinute;
    }

    public List<SelectItem> getFromMinuteList() {
        return fromMinuteList;
    }

    public void setFromMinuteList(List<SelectItem> fromMinuteList) {
        this.fromMinuteList = fromMinuteList;
    }

    public String getToHour() {
        return toHour;
    }

    public void setToHour(String toHour) {
        this.toHour = toHour;
    }

    public List<SelectItem> getToHourList() {
        return toHourList;
    }

    public void setToHourList(List<SelectItem> toHourList) {
        this.toHourList = toHourList;
    }

    public String getToMeridiem() {
        return toMeridiem;
    }

    public void setToMeridiem(String toMeridiem) {
        this.toMeridiem = toMeridiem;
    }

    public String getToMinute() {
        return toMinute;
    }

    public void setToMinute(String toMinute) {
        this.toMinute = toMinute;
    }

    public List<SelectItem> getToMinuteList() {
        return toMinuteList;
    }

    public void setToMinuteList(List<SelectItem> toMinuteList) {
        this.toMinuteList = toMinuteList;
    }

    public boolean isTimeBased() {
        return timeBased;
    }

    public void setTimeBased(boolean timeBased) {
        this.timeBased = timeBased;
    }
}
