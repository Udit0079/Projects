/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DemandReportFacadeRemote;
import com.cbs.pojo.DemandOefPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class DemandOef extends BaseBean {

    private String message;
    private String area;
    private List<SelectItem> areaList;
    private String personalNoRange;
    private List<SelectItem> personalNoRangeList;
    private String tillDate;
    private String month;
    private List<SelectItem> monthList;
    private String year;
    private boolean disableRange;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private DemandReportFacadeRemote dmdRemote;
    List<DemandOefPojo> reportList = new ArrayList<DemandOefPojo>();

    public DemandOef() {

        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            dmdRemote = (DemandReportFacadeRemote) ServiceLocator.getInstance().lookup("DemandReportFacade");

            areaList = new ArrayList<SelectItem>();
            areaList.add(new SelectItem("S", "--Select--"));
            areaList.add(new SelectItem("IECP", "IECP"));
            areaList.add(new SelectItem("STCP", "STCP"));

            personalNoRangeList = new ArrayList<SelectItem>();
            personalNoRangeList.add(new SelectItem("S", "--Select--"));
            personalNoRangeList.add(new SelectItem("100001 to 104444", "100001 to 104444"));
            personalNoRangeList.add(new SelectItem("104445 to 106666", "104445 to 106666"));
            personalNoRangeList.add(new SelectItem("106667 to 109999", "106667 to 109999"));

            monthList = new ArrayList<SelectItem>();
            //Set all months
            monthList.add(new SelectItem("0", "--Select--"));
            Map<Integer, String> monthMap = CbsUtil.getAllMonths();
            System.out.println("Month List Size-->" + monthMap.size());
            Set<Map.Entry<Integer, String>> mapSet = monthMap.entrySet();
            Iterator<Map.Entry<Integer, String>> mapIt = mapSet.iterator();
            while (mapIt.hasNext()) {
                Map.Entry<Integer, String> mapEntry = mapIt.next();
                monthList.add(new SelectItem(mapEntry.getKey(), mapEntry.getValue()));
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onProcessRange() {
        setMessage("");
        if (area.equalsIgnoreCase("IECP")) {
            this.setDisableRange(false);
        } else {
            this.setPersonalNoRange("S");
            this.setDisableRange(true);
        }
    }

    public void viewReport() {
        setMessage("");
        String branchName = "", address = "";
        String report = "Demand Genetation Report";
        try {

            if (area.equalsIgnoreCase("S")) {
                setMessage("Please select Area !");
                return;
            }

            if (this.month == null || this.month.equals("0")) {
                this.setMessage("Please select Month.");
                return;
            }
            if (this.year == null || year.equals("") || year.trim().length() != 4) {
                this.setMessage("Please fill proper Year.");
                return;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(this.year);
            if (m.matches() != true) {
                this.setMessage("Please fill proper Year in numeric");
                return;
            }

            List brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            tillDate = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year)));
            String month = CbsUtil.getMonthName(Integer.parseInt(ymd.format(dmy.parse(tillDate)).substring(4, 6)));
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReportDt", tillDate);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pArea", "[" + this.area + "] " + personalNoRange);
            fillParams.put("pMonth", month + " / " + year);

            reportList = dmdRemote.getOefDemandRecoveriesData(area, ymd.format(dmy.parse(tillDate)), getOrgnBrCode(), personalNoRange);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does exist !!!");
            }
            new ReportBean().jasperReport("OefDemandGenReport", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Demand Genetation Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void pdfDownLoad() {

        setMessage("");
        String branchName = "", address = "";
        String report = "Demand Genetation Report";
        try {

            if (area.equalsIgnoreCase("S")) {
                setMessage("Please select Area !");
                return;
            }

            if (this.month == null || this.month.equals("0")) {
                this.setMessage("Please select Month.");
                return;
            }
            if (this.year == null || year.equals("") || year.trim().length() != 4) {
                this.setMessage("Please fill proper Year.");
                return;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(this.year);
            if (m.matches() != true) {
                this.setMessage("Please fill proper Year in numeric");
                return;
            }

            List brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            tillDate = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year)));
            String month = CbsUtil.getMonthName(Integer.parseInt(ymd.format(dmy.parse(tillDate)).substring(4, 6)));
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReportDt", tillDate);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pArea", "[" + this.area + "] " + personalNoRange);
            fillParams.put("pMonth", month + " / " + year);

            reportList = dmdRemote.getOefDemandRecoveriesData(area, ymd.format(dmy.parse(tillDate)), getOrgnBrCode(), personalNoRange);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does exist !!!");
            }
            new ReportBean().openPdf("Demand Genetation Report", "OefDemandGenReport", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Demand Genetation Report");

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void refreshPage() {

        this.setMessage("");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<SelectItem> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<SelectItem> areaList) {
        this.areaList = areaList;
    }

    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPersonalNoRange() {
        return personalNoRange;
    }

    public void setPersonalNoRange(String personalNoRange) {
        this.personalNoRange = personalNoRange;
    }

    public List<SelectItem> getPersonalNoRangeList() {
        return personalNoRangeList;
    }

    public void setPersonalNoRangeList(List<SelectItem> personalNoRangeList) {
        this.personalNoRangeList = personalNoRangeList;
    }

    public boolean isDisableRange() {
        return disableRange;
    }

    public void setDisableRange(boolean disableRange) {
        this.disableRange = disableRange;
    }
}
