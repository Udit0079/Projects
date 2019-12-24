/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.LoanMprsPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherLoanReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class LoanMpr extends BaseBean {

    private String message;
    private String cmbReptype;
    private String cmbOption;
    private String cmbMon;
    private String cmbYear;
    private String fdt;
    private String tdt;
    private boolean chkOs1;
    private boolean chkOs2;
    private boolean chkTarac;
    private boolean chkOsbal1;
    private boolean chkOverdue1;
    private boolean chkDisbAmt1;
    private boolean chkAc1;
    private boolean chkTaramt;
    private boolean chkRec1;
    private boolean chkCurMon;
    private boolean chkAc2;
    private boolean chkOverDue2;
    private boolean chkLastMon;
    private boolean chkOsbal2;
    private boolean chkDisb2;
    private boolean chkRec2;
    private boolean chkIncrDcr;
    private String incrdcr;
    private boolean flag;
    private boolean flag1;
    private boolean flag2;
    private String orgnBrCode;
    private List<SelectItem> optionList;
    private List<SelectItem> yearList;
    private List<SelectItem> incrdcrlist;
    private List<SelectItem> reptypeList;
    private List<SelectItem> monList;
    HttpServletRequest req;
    String toDate;
    List<LoanMprsPojo> loanmprList = new ArrayList<LoanMprsPojo>();
    private CommonReportMethodsRemote common;
    private OtherLoanReportFacadeRemote loanMprFacade;

    public String getCmbMon() {
        return cmbMon;
    }

    public void setCmbMon(String cmbMon) {
        this.cmbMon = cmbMon;
    }

    public String getCmbOption() {
        return cmbOption;
    }

    public void setCmbOption(String cmbOption) {
        this.cmbOption = cmbOption;
    }

    public String getCmbReptype() {
        return cmbReptype;
    }

    public void setCmbReptype(String cmbReptype) {
        this.cmbReptype = cmbReptype;
    }

    public String getCmbYear() {
        return cmbYear;
    }

    public void setCmbYear(String cmbYear) {
        this.cmbYear = cmbYear;
    }

    public String getIncrdcr() {
        return incrdcr;
    }

    public void setIncrdcr(String incrdcr) {
        this.incrdcr = incrdcr;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<SelectItem> getIncrdcrlist() {
        return incrdcrlist;
    }

    public void setIncrdcrlist(List<SelectItem> incrdcrlist) {
        this.incrdcrlist = incrdcrlist;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getMonList() {
        return monList;
    }

    public void setMonList(List<SelectItem> monList) {
        this.monList = monList;
    }

    public List<SelectItem> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SelectItem> optionList) {
        this.optionList = optionList;
    }

    public List<SelectItem> getReptypeList() {
        return reptypeList;
    }

    public void setReptypeList(List<SelectItem> reptypeList) {
        this.reptypeList = reptypeList;
    }

    public List<SelectItem> getYearList() {
        return yearList;
    }

    public void setYearList(List<SelectItem> yearList) {
        this.yearList = yearList;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public boolean isChkAc1() {
        return chkAc1;
    }

    public void setChkAc1(boolean chkAc1) {
        this.chkAc1 = chkAc1;
    }

    public boolean isChkDisbAmt1() {
        return chkDisbAmt1;
    }

    public void setChkDisbAmt1(boolean chkDisbAmt1) {
        this.chkDisbAmt1 = chkDisbAmt1;
    }

    public boolean isChkOs1() {
        return chkOs1;
    }

    public void setChkOs1(boolean chkOs1) {
        this.chkOs1 = chkOs1;
    }

    public boolean isChkOsbal1() {
        return chkOsbal1;
    }

    public void setChkOsbal1(boolean chkOsbal1) {
        this.chkOsbal1 = chkOsbal1;
    }

    public boolean isChkOverdue1() {
        return chkOverdue1;
    }

    public void setChkOverdue1(boolean chkOverdue1) {
        this.chkOverdue1 = chkOverdue1;
    }

    public boolean isChkRec1() {
        return chkRec1;
    }

    public void setChkRec1(boolean chkRec1) {
        this.chkRec1 = chkRec1;
    }

    public boolean isChkTarac() {
        return chkTarac;
    }

    public void setChkTarac(boolean chkTarac) {
        this.chkTarac = chkTarac;
    }

    public boolean isChkTaramt() {
        return chkTaramt;
    }

    public void setChkTaramt(boolean chkTaramt) {
        this.chkTaramt = chkTaramt;
    }

    public boolean isChkAc2() {
        return chkAc2;
    }

    public void setChkAc2(boolean chkAc2) {
        this.chkAc2 = chkAc2;
    }

    public boolean isChkCurMon() {
        return chkCurMon;
    }

    public void setChkCurMon(boolean chkCurMon) {
        this.chkCurMon = chkCurMon;
    }

    public boolean isChkDisb2() {
        return chkDisb2;
    }

    public void setChkDisb2(boolean chkDisb2) {
        this.chkDisb2 = chkDisb2;
    }

    public boolean isChkIncrDcr() {
        return chkIncrDcr;
    }

    public void setChkIncrDcr(boolean chkIncrDcr) {
        this.chkIncrDcr = chkIncrDcr;
    }

    public boolean isChkLastMon() {
        return chkLastMon;
    }

    public void setChkLastMon(boolean chkLastMon) {
        this.chkLastMon = chkLastMon;
    }

    public boolean isChkOs2() {
        return chkOs2;
    }

    public void setChkOs2(boolean chkOs2) {
        this.chkOs2 = chkOs2;
    }

    public boolean isChkOsbal2() {
        return chkOsbal2;
    }

    public void setChkOsbal2(boolean chkOsbal2) {
        this.chkOsbal2 = chkOsbal2;
    }

    public boolean isChkOverDue2() {
        return chkOverDue2;
    }

    public void setChkOverDue2(boolean chkOverDue2) {
        this.chkOverDue2 = chkOverDue2;
    }

    public boolean isChkRec2() {
        return chkRec2;
    }

    public void setChkRec2(boolean chkRec2) {
        this.chkRec2 = chkRec2;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }
    SimpleDateFormat dmy = new SimpleDateFormat("dd/mm/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public LoanMpr() {
        setTodayDate(getTodayDate());
        setCmbYear(getCmbYear());
        getMonthQuarter();
        getAllMonth();
        getQuarter();
        getOption();
        getYear();
        incrDcr();
        req = getRequest();

        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            loanMprFacade = (OtherLoanReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherLoanReportFacade");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getMonthQuarter() {
        reptypeList = new ArrayList<SelectItem>();
        reptypeList.add(new SelectItem("S", "--Select--"));
        reptypeList.add(new SelectItem("Monthly", "Monthly"));
        reptypeList.add(new SelectItem("Quarterly", "Quarterly"));
    }

    public void getAllMonth() {
        monList = new ArrayList<SelectItem>();
        monList.add(new SelectItem("S", "--Select--"));
        monList.add(new SelectItem("1", "January"));
        monList.add(new SelectItem("2", "February"));
        monList.add(new SelectItem("3", "March"));
        monList.add(new SelectItem("4", "April"));
        monList.add(new SelectItem("5", "May"));
        monList.add(new SelectItem("6", "June"));
        monList.add(new SelectItem("7", "July"));
        monList.add(new SelectItem("8", "August"));
        monList.add(new SelectItem("9", "September"));
        monList.add(new SelectItem("10", "October"));
        monList.add(new SelectItem("11", "November"));
        monList.add(new SelectItem("12", "December"));
    }

    public void getQuarter() {
        monList = new ArrayList<SelectItem>();
        monList.add(new SelectItem("S", "--Select--"));
        monList.add(new SelectItem("4", "March"));
        monList.add(new SelectItem("6", "June"));
        monList.add(new SelectItem("9", "September"));
        monList.add(new SelectItem("12", "December"));
    }

    public void getOption() {
        optionList = new ArrayList<SelectItem>();
        optionList.add(new SelectItem("S", "--Select--"));
        optionList.add(new SelectItem("A/c Type wise", "A/c Type wise"));
        optionList.add(new SelectItem("SectorWise", "SectorWise"));
        optionList.add(new SelectItem("SchemesWise", "SchemesWise"));
        optionList.add(new SelectItem("SecurityWise", "SecurityWise"));
    }

    public void options() {
        if (cmbOption.equals("A/c Type wise")) {
            flag = true;
        } else if (cmbOption.equals("SectorWise")) {
            flag = true;
        } else if (cmbOption.equals("SchemesWise")) {
            flag = true;
        } else if (cmbOption.equals("SecurityWise")) {
            flag = true;
        } else {
            flag = false;
        }
    }

    public void selMnthQtr() {
        if (this.cmbReptype.equals("Monthly")) {
            this.getAllMonth();
            this.flag = true;
        } else {
            if (this.cmbReptype.equals("Quarterly")) {
                this.getQuarter();
                this.flag = true;
            } else {
                this.flag = false;
                setMessage("");
            }
        }
    }

    public void getYear() {
        yearList = new ArrayList<SelectItem>();
        yearList.add(new SelectItem("--Select--"));
        Calendar cal = Calendar.getInstance();
        for (int i = -20; i <= 0; i++) {
            int yr = cal.get(Calendar.YEAR) + i;
            yearList.add(new SelectItem(String.valueOf(yr)));
        }
    }

    public void fmtdmy() {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Integer.parseInt(cmbMon) - 1);
        cal.set(Calendar.YEAR, Integer.parseInt(cmbYear));
        int fd = cal.getActualMinimum(Calendar.DATE);
        cal.set(Calendar.DATE, fd);
        fdt = ymd.format(cal.getTime());
        int ldate = cal.getActualMaximum(Calendar.DATE);
        cal.set(Calendar.DATE, ldate);
        tdt = ymd.format(cal.getTime());
        String date = tdt;
        String yyyy = date.substring(0, 4);
        String mm = date.substring(4, 6);
        String dd = date.substring(6, 8);
        toDate = dd + "/" + mm + "/" + yyyy;
    }

    public void chkBoxValueA() {
        flag1 = true;
        if (chkOs1 == true) {
            if (chkTarac == false && chkTaramt == false && chkAc1 == false && chkOsbal1 == false && chkOverdue1 == false && chkRec1 == false && chkDisbAmt1 == false) {
                setMessage("Please Select At Least One Field");
                flag1 = false;
            }
        }

        if (chkOs1 == false) {
            if (chkTarac == true || chkTaramt == true || chkAc1 == true || chkOsbal1 == true || chkOverdue1 == true || chkRec1 == true || chkDisbAmt1 == true) {
                setMessage("Please Select Last Year Field");
                flag = false;
                chkTarac = false;
                chkTaramt = false;
                chkAc1 = false;
                chkOsbal1 = false;
                chkOverdue1 = false;
                chkRec1 = false;
                chkDisbAmt1 = false;
            }
        }
        flag1 = false;
    }

    public void chkBoxValueB() {
        if (chkOs2 == true) {
            if (chkCurMon == false && chkLastMon == false && chkAc2 == false && chkOverDue2 == false && chkRec2 == false && chkOsbal2 == false && chkDisb2 == false && chkIncrDcr == false) {
                setMessage("Please Select At Least One Field");
                flag1 = false;
            }
        }
        if (chkOs2 == false) {
            if (chkCurMon == true || chkLastMon == true || chkAc2 == true || chkOverDue2 == true || chkRec2 == true || chkOsbal2 == true || chkDisb2 == true || chkIncrDcr == true) {
                setMessage("Please Select OS Of Current Year");
                flag1 = false;
            }
        }
        flag1 = false;
    }

    public void incrDcr() {
        incrdcrlist = new ArrayList<SelectItem>();
        incrdcrlist.add(new SelectItem("S", "--Select--"));
        incrdcrlist.add(new SelectItem("1", "Inc/Dec With Last Month"));
        incrdcrlist.add(new SelectItem("2", "Inc/Dec With Last Year"));
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry.Got a null request from faces context");
        }
        return request;
    }

    public void print() {
        chkBoxValueA();
        chkBoxValueB();
        fmtdmy();
        String brnName = "";
        String brnAddress = "";
        Map map = new HashMap();
        try {
            List brnnamead = new ArrayList();
            brnnamead = common.getBranchNameandAddress(orgnBrCode);
            if (brnnamead.size() > 0) {
                brnName = (String) brnnamead.get(0);
                brnAddress = (String) brnnamead.get(1);
            }

            if (this.cmbReptype.equalsIgnoreCase("S")) {
                setMessage("Please Select the Report Type");
                return;
            }

            if (this.cmbMon.equalsIgnoreCase("S")) {
                setMessage("Please Select For The Month/Quarter");
                return;
            }

            if (this.cmbOption.equalsIgnoreCase("S")) {
                setMessage("Please Select Option");
                return;
            }

//            if (this.incrdcr.equalsIgnoreCase("S")) {
//                setMessage("Please Select the Increment/Decrement");
//                return;
//            }
//            
//            if(this.cmbYear.equalsIgnoreCase("--Select--")){
//             setMessage("Please Select Year");
//                return;
//            } 
            setMessage("");
            String report = "Loan Mpr Report";
            String head2 = "Amount In Lakhs";
            String os1 = "O/S As On 01/04/";
            String os2 = "O/S As On";
            String lm = "O/S As On Last";
            map.put("pos1", os1);
            map.put("pos2", os2);
            map.put("plm", lm);
            map.put("phead2", head2);
            map.put("pOption", cmbOption);
            map.put("pRrportType", cmbReptype);
            map.put("reportName", report);
            map.put("printedBy", this.getUserName());
            map.put("reportDate", this.toDate);
            map.put("printDate", this.getTodayDate());
            map.put("branchName", brnName);
            map.put("branchAddress", brnAddress);
            map.put("year", this.getCmbYear());

            loanmprList = loanMprFacade.getLoanMpr(fdt, tdt, cmbOption, chkTarac, chkTaramt, chkAc1, chkOsbal1, chkRec1, chkDisbAmt1, cmbReptype, chkDisb2, chkRec2, chkOsbal2, chkOs1, chkOs2, chkLastMon, chkCurMon, chkAc2, chkIncrDcr, incrdcr, getOrgnBrCode(), chkOverdue1, chkOverDue2);
            if (loanmprList.isEmpty()) {
                message = "No value found in databse";
                return;
            }
            new ReportBean().jasperReport("LoanMpr", "text/html",
                    new JRBeanCollectionDataSource(loanmprList), map, "Loan_Mpr_Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refresh() {
        message = "";
        cmbReptype = "S";
        cmbMon = "S";
        cmbOption = "S";
        incrdcr = "S";
        cmbYear = "S";
        chkOs1 = false;
        chkOs2 = false;
        chkTarac = false;
        chkOsbal1 = false;
        chkOverdue1 = false;
        chkDisbAmt1 = false;
        chkAc1 = false;
        chkTaramt = false;
        chkRec1 = false;
        chkCurMon = false;
        chkAc2 = false;
        chkOverDue2 = false;
        chkLastMon = false;
        chkOsbal2 = false;
        chkDisb2 = false;
        chkRec2 = false;
        chkIncrDcr = false;
    }

    public String exit() {
        return "case1";
    }
}
