/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ledger;

import com.cbs.dto.report.DayBookPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.facade.report.LedgerReportFacadeRemote;
//import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class DayBook extends BaseBean {

    String message;
    String calDate;
    private String exCounter;
    String branchOption;
    private String displayExCtr;
     private String focusId;
    List<SelectItem> branchOptionList;
    private List<SelectItem> exCounterList;
    private GLReportFacadeRemote glBeanRemote;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final String jndiHomeName = "LedgerReportFacade";
    private LedgerReportFacadeRemote ledgerReportRemote = null;
    private CommonReportMethodsRemote common;
    List<DayBookPojo> dayBookDataList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getBranchOption() {
        return branchOption;
    }

    public void setBranchOption(String branchOption) {
        this.branchOption = branchOption;
    }

    public List<SelectItem> getBranchOptionList() {
        return branchOptionList;
    }

    public void setBranchOptionList(List<SelectItem> branchOptionList) {
        this.branchOptionList = branchOptionList;
    }

    public String getDisplayExCtr() {
        return displayExCtr;
    }

    public void setDisplayExCtr(String displayExCtr) {
        this.displayExCtr = displayExCtr;
    }

    public String getExCounter() {
        return exCounter;
    }

    public void setExCounter(String exCounter) {
        this.exCounter = exCounter;
    }

    public List<SelectItem> getExCounterList() {
        return exCounterList;
    }

    public void setExCounterList(List<SelectItem> exCounterList) {
        this.exCounterList = exCounterList;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }
    
    

    public DayBook() {
        try {
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup("GLReportFacade");
            ledgerReportRemote = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
           

            branchOptionList = new ArrayList<SelectItem>();
            List allBranchCodeList = common.getBranchCodeList(getOrgnBrCode());
            if (!allBranchCodeList.isEmpty()) {
                for (int i = 0; i < allBranchCodeList.size(); i++) {
                    Vector vec = (Vector) allBranchCodeList.get(i);
                     branchOptionList.add(new SelectItem(vec.get(0).toString().length() < 2 ? "0" + vec.get(0).toString() : vec.get(0).toString(), vec.get(1).toString()));
                }
            }
            exCounterList = new ArrayList<>();
            exCounterList.add(new SelectItem("N", "No"));
            exCounterList.add(new SelectItem("Y", "Yes"));
            setExCounter("N");
            setDisplayExCtr("none");
            setFocusId("listbranch");
            this.setCalDate(sdf.format(new Date()));

        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }
    
    public void checkExtCounter(){
        try {
            setMessage("");
            if(!branchOption.equals("0A") && common.isExCounterExit(branchOption)){
                setDisplayExCtr("");
                setFocusId("exddl");
            }
            else{
                setDisplayExCtr("none");
                setFocusId("txtDate");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnHtmlAction() {
        try {
            String report = "Day Book Report";
            this.setMessage("");
            if (calDate.equalsIgnoreCase("") || this.calDate == null) {
                this.setMessage("Please Enter Date.");
                return;
                //return null;
            }
            Map fillParams = new HashMap();
            /**
             * END*
             */
            fillParams.put("pReportDate", calDate);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            dayBookDataList = new ArrayList<DayBookPojo>();
            String dt2[] = calDate.split("/");
            String indate = dt2[2] + dt2[1] + dt2[0];
            //  dayBookDataList = ledgerReportRemote.getDayBookReportData(indate, branchOption);

            dayBookDataList = ledgerReportRemote.getDayBookReportData(indate, branchOption,exCounter);
            if (!dayBookDataList.isEmpty()) {
                new ReportBean().jasperReport("day_book", "text/html",
                        new JRBeanCollectionDataSource(dayBookDataList), fillParams, "Day Book Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpRequest().getSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                setMessage("No detail exists !");
            }

        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            this.setMessage(e.getMessage());
        }
        //return "report";
    }

    public void btnPdfAction() {
        try {
            String report = "Day Book Report";
            this.setMessage("");
            if (calDate.equalsIgnoreCase("") || this.calDate == null) {
                this.setMessage("Please Enter Date.");
                return;
            }
            Map fillParams = new HashMap();
            /**
             * END*
             */
            fillParams.put("pReportDate", calDate);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            dayBookDataList = new ArrayList<DayBookPojo>();
            String dt2[] = calDate.split("/");
            String indate = dt2[2] + dt2[1] + dt2[0];
            dayBookDataList = ledgerReportRemote.getDayBookReportData(indate,branchOption,exCounter);
            if (!dayBookDataList.isEmpty()) {
                new ReportBean().openPdf("day_book_", "day_book", new JRBeanCollectionDataSource(dayBookDataList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpRequest().getSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                setMessage("No detail exists !");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            this.setMessage(e.getMessage());
        }
        //return "report";
    }

    public String btnExitAction() {
        this.setCalDate(sdf.format(new Date()));
        dayBookDataList = new ArrayList<DayBookPojo>();
        return "case1";
    }
}
