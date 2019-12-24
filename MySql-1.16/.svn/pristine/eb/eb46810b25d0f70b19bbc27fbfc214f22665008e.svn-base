/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.OnUsReportTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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

/**
 *
 * @author Sudhir
 */
public class OffUs extends BaseBean {

    private String message;
    private Date trandate;
    private String transactionType, fromHour, fromMinutes, fromTime, toHour, toMinutes, toTime;
    private List<SelectItem> transactionTypeList = new ArrayList<SelectItem>();
    private String flag;
    private List<SelectItem> fromHourList, fromMinuteList, fromTimeList;
    private List<SelectItem> toHourList, toMinuteList, toTimeList;
    private boolean checkBox;
    private final String othersReportjndiName = "OtherReportFacade";
    private OtherReportFacadeRemote otherFacadeRemote = null;
    private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
            dmyFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public List<SelectItem> getToMinuteList() {
        return toMinuteList;
    }

    public void setToMinuteList(List<SelectItem> toMinuteList) {
        this.toMinuteList = toMinuteList;
    }

    public String getFromMinutes() {
        return fromMinutes;
    }

    public void setFromMinutes(String fromMinutes) {
        this.fromMinutes = fromMinutes;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public List<SelectItem> getFromTimeList() {
        return fromTimeList;
    }

    public void setFromTimeList(List<SelectItem> fromTimeList) {
        this.fromTimeList = fromTimeList;
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

    public String getToMinutes() {
        return toMinutes;
    }

    public void setToMinutes(String toMinutes) {
        this.toMinutes = toMinutes;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public List<SelectItem> getToTimeList() {
        return toTimeList;
    }

    public void setToTimeList(List<SelectItem> toTimeList) {
        this.toTimeList = toTimeList;
    }

    public List<SelectItem> getFromHourList() {
        return fromHourList;
    }

    public void setFromHourList(List<SelectItem> fromHourList) {
        this.fromHourList = fromHourList;
    }

    public String getFromHour() {
        return fromHour;
    }

    public void setFromHour(String fromHour) {
        this.fromHour = fromHour;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTrandate() {
        return trandate;
    }

    public void setTrandate(Date trandate) {
        this.trandate = trandate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public List<SelectItem> getTransactionTypeList() {
        return transactionTypeList;
    }

    public void setTransactionTypeList(List<SelectItem> transactionTypeList) {
        this.transactionTypeList = transactionTypeList;
    }

    public List<SelectItem> getFromMinuteList() {
        return fromMinuteList;
    }

    public void setFromMinuteList(List<SelectItem> fromMinuteList) {
        this.fromMinuteList = fromMinuteList;
    }

    /**
     * Creates a new instance of OffUs
     */
    public OffUs() {
        try {
            otherFacadeRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup(othersReportjndiName);
            this.setMessage("");
            onLoadData();
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void onLoadData() {
        fromHourList = new ArrayList<SelectItem>();
        fromTimeList = new ArrayList<SelectItem>();
        fromMinuteList = new ArrayList<SelectItem>();
        toHourList = new ArrayList<SelectItem>();
        toMinuteList = new ArrayList<SelectItem>();
        toTimeList = new ArrayList<SelectItem>();
        transactionTypeList = new ArrayList<SelectItem>();

        transactionTypeList.add(new SelectItem("0", "--SELECT--"));
        transactionTypeList.add(new SelectItem("1", "CASH"));
        transactionTypeList.add(new SelectItem("2", "TRANSFER"));
        transactionTypeList.add(new SelectItem("3", "CLEARING"));

        fromTimeList.add(new SelectItem("AM", "AM"));
        fromTimeList.add(new SelectItem("PM", "PM"));

        toTimeList.add(new SelectItem("AM", "AM"));
        toTimeList.add(new SelectItem("PM", "PM"));

        for (int i = 0; i <= 60; i++) {
            String param = String.valueOf(i);
            if (param.length() < 2) {
                param = "0" + param;
            }
            fromMinuteList.add(new SelectItem(param, param));
            toMinuteList.add(new SelectItem(param, param));
        }
        for (int i = 0; i <= 12; i++) {
            String param = String.valueOf(i);
            if (param.length() < 2) {
                param = "0" + param;
            }
            fromHourList.add(new SelectItem(param, param));
            toHourList.add(new SelectItem(param, param));
        }
    }

    public void onClickCheckBox() {
    }

    public void printReport() {
        try {
            String valResult = validations();
            if (!valResult.equalsIgnoreCase("true")) {
                setMessage(valResult);
                flag = "false";
                return;
            }
            flag = "true";
            this.setMessage("");
            String report = "Off-Us Report";
            String fromTimeGenerate = "";
            String timeAllowed = "";
            String toTimeGenerate = "";
            String tranType = "";
            if (checkBox == true) {
                timeAllowed = "Y";
                SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd");
                if (fromTime.equalsIgnoreCase("AM")) {
                    fromTimeGenerate = sdfTime.format(trandate) + " " + fromHour + ":" + fromMinutes + ":00";

                } else {
                    int hour;
                    if (Integer.parseInt(fromHour) == 12) {
                        hour = Integer.parseInt(fromHour) + 0;
                    } else {
                        hour = Integer.parseInt(fromHour) + 12;
                    }
                    fromTimeGenerate = sdfTime.format(trandate) + " " + hour + ":" + fromMinutes + ":00";
                }
                if (toTime.equalsIgnoreCase("AM")) {
                    toTimeGenerate = sdfTime.format(trandate) + " " + toHour + ":" + toMinutes + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(toHour) == 12) {
                        hour = Integer.parseInt(toHour) + 0;
                    } else {
                        hour = Integer.parseInt(toHour) + 12;
                    }
                    toTimeGenerate = sdfTime.format(trandate) + " " + hour + ":" + toMinutes + ":00";
                }
            } else {
                timeAllowed = "N";
            }
            List<OnUsReportTable> onUsReportrDetails = new ArrayList<OnUsReportTable>();
            if (transactionType.equals("1")) {
                tranType = "CASH";
            } else if (transactionType.equals("2")) {
                tranType = "TRANSFER";
            } else if (transactionType.equals("3")) {
                tranType = "CLEARING";
            }
            onUsReportrDetails = otherFacadeRemote.getOffUsReport(ymdFormatter.format(getTrandate()), tranType, getOrgnBrCode(), fromTimeGenerate, toTimeGenerate, timeAllowed);
            if (onUsReportrDetails.isEmpty()) {
                flag = "false";
                setMessage("No data found");
                return;
            }
            Map fillParams = new HashMap();
            fillParams.put("pReportDate", dmyFormatter.format(trandate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            if (transactionType.equals("1")) {
                new ReportBean().jasperReport("OfUsCashReport", "text/html", new JRBeanCollectionDataSource(onUsReportrDetails), fillParams, "Off-Us Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else {
                new ReportBean().jasperReport("OfUsTransferReport", "text/html", new JRBeanCollectionDataSource(onUsReportrDetails), fillParams, "Off-Us Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void pdfDownLoad() {
        try {
            String valResult = validations();
            if (!valResult.equalsIgnoreCase("true")) {
                setMessage(valResult);
                flag = "false";
                return;
            }
            flag = "true";
            this.setMessage("");
            String report = "Off-Us Report";
            String fromTimeGenerate = "";
            String timeAllowed = "";
            String toTimeGenerate = "";
            String tranType = "";
            if (checkBox == true) {
                timeAllowed = "Y";
                SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd");
                if (fromTime.equalsIgnoreCase("AM")) {
                    fromTimeGenerate = sdfTime.format(trandate) + " " + fromHour + ":" + fromMinutes + ":00";

                } else {
                    int hour;
                    if (Integer.parseInt(fromHour) == 12) {
                        hour = Integer.parseInt(fromHour) + 0;
                    } else {
                        hour = Integer.parseInt(fromHour) + 12;
                    }
                    fromTimeGenerate = sdfTime.format(trandate) + " " + hour + ":" + fromMinutes + ":00";
                }
                if (toTime.equalsIgnoreCase("AM")) {
                    toTimeGenerate = sdfTime.format(trandate) + " " + toHour + ":" + toMinutes + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(toHour) == 12) {
                        hour = Integer.parseInt(toHour) + 0;
                    } else {
                        hour = Integer.parseInt(toHour) + 12;
                    }
                    toTimeGenerate = sdfTime.format(trandate) + " " + hour + ":" + toMinutes + ":00";
                }
            } else {
                timeAllowed = "N";
            }
            List<OnUsReportTable> onUsReportrDetails = new ArrayList<OnUsReportTable>();
            if (transactionType.equals("1")) {
                tranType = "CASH";
            } else if (transactionType.equals("2")) {
                tranType = "TRANSFER";
            } else if (transactionType.equals("3")) {
                tranType = "CLEARING";
            }
            onUsReportrDetails = otherFacadeRemote.getOffUsReport(ymdFormatter.format(getTrandate()), tranType, getOrgnBrCode(), fromTimeGenerate, toTimeGenerate, timeAllowed);
            if (onUsReportrDetails.isEmpty()) {
                flag = "false";
                setMessage("No data found");
                return;
            }
            Map fillParams = new HashMap();
            fillParams.put("pReportDate", dmyFormatter.format(trandate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);

            if (transactionType.equals("1")) {
                new ReportBean().openPdf("Off-Us Report_" + ymdFormatter.format(dmyFormatter.parse(getTodayDate())), "OfUsCashReport", new JRBeanCollectionDataSource(onUsReportrDetails), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else {
                new ReportBean().openPdf("Off-Us Report_" + ymdFormatter.format(dmyFormatter.parse(getTodayDate())), "OfUsTransferReport", new JRBeanCollectionDataSource(onUsReportrDetails), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }

            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Off-Us Report");

        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public String validations() {
        try {
            if (getTransactionType().equalsIgnoreCase("0")) {
                return "Transaction type is not selected";
            }
            if (trandate == null) {
                return "Whether date is not selected or in proper format";
            }
        } catch (Exception e) {
            return "Error occurred while validating field";
        }
        return "true";
    }

    public String exitFrm() {
        return "case1";
    }
}
