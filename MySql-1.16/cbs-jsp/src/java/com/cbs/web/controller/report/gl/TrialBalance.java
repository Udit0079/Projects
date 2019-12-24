/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.gl;

import com.cbs.dto.report.TrialBalancePojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
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
public class TrialBalance extends BaseBean{

    String message;
    Date calDate;
    private String branch;
    private String exCounter;
    private String displayExCtr;
    private List<SelectItem> exCounterList;
    private List<SelectItem> branchList;
    private String focusId;
    private final String jndiHomeName = "GLReportFacade";
    private CommonReportMethodsRemote common;
    private GLReportFacadeRemote glBeanRemote = null;
    List<TrialBalancePojo> trialBalanceDataList = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getExCounter() {
        return exCounter;
    }

    public void setExCounter(String exCounter) {
        this.exCounter = exCounter;
    }

    public String getDisplayExCtr() {
        return displayExCtr;
    }

    public void setDisplayExCtr(String displayExCtr) {
        this.displayExCtr = displayExCtr;
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
    
    
    public TrialBalance() {
        try {
            glBeanRemote = (GLReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            
            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(this.getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
            exCounterList = new ArrayList<>();
            exCounterList.add(new SelectItem("N", "No"));
            exCounterList.add(new SelectItem("Y", "Yes"));
            setExCounter("N");
            setDisplayExCtr("none");
            setFocusId("branchddl");
            
            this.setCalDate(new Date());
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    
    public void checkExtCounter(){
        try {
            setMessage("");
            if(!branch.equals("0A") && common.isExCounterExit(branch)){
                setDisplayExCtr("");
                setFocusId("exddl");
            }
            else{
                setDisplayExCtr("none");
                setFocusId("date");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    

    public void btnHtmlAction() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        Date date = this.calDate;
        if (date == null) {
            this.setMessage("Please Enter Date");
            //return "Please Enter Date";
        }
        String date2 = sdf1.format(date);
        String user = getUserName();
        String report = "Trial Balance Report";
        Map fillParams = new HashMap();
        try {
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedDate", date2);
            fillParams.put("SysDate", sdf1.format(new Date()));
            fillParams.put("pPrintedBy", user);

            trialBalanceDataList = glBeanRemote.getNewTrialBalanceReport(ymd.format(this.calDate), this.getBranch(),this.exCounter);
           // trialBalanceDataList = glBeanRemote.getTrialBalanceReportData(ymd.format(this.calDate), this.getBranch());

            new ReportBean().jasperReport("Trial_Balance", "text/html",
                    new JRBeanCollectionDataSource(trialBalanceDataList), fillParams, "Trial Balance Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return "report";
    }

    public void btnPdfAction() {  
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        Date date = this.calDate;
        if (date == null) {
            this.setMessage("Please Enter Date");
            //return "Please Enter Date";
        }
        String date2 = sdf1.format(date);
        String user = getUserName();
        String report = "Trial Balance Report";
        Map fillParams = new HashMap();
        try {
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedDate", date2);
            fillParams.put("SysDate", sdf1.format(new Date()));
            fillParams.put("pPrintedBy", user);
            
            trialBalanceDataList = glBeanRemote.getNewTrialBalanceReport(ymd.format(this.calDate), this.getBranch(),this.exCounter);
            //trialBalanceDataList = glBeanRemote.getTrialBalanceReportData(ymd.format(this.calDate), this.getBranch());
            new ReportBean().openPdf("Trial_Balance_", "Trial_Balance", new JRBeanCollectionDataSource(trialBalanceDataList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return "report";  
    }

    public String btnExitAction() {
        return "case1";
    }

    public void refreshForm() {
        this.setMessage("");
        this.setCalDate(new Date());
        this.setBranch("A");
    }
}
