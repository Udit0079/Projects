/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.pojo.CurrencyExchangePojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
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
 * @author Admin
 */
public class CurrencyExchange extends BaseBean {

    String message;
    private String branch;
    private List<SelectItem> branchList;
    Date calDate;
    Date dt = new Date();
    private CommonReportMethodsRemote common;
    private String reportFormat;
    private List<SelectItem> reportFormatIn;
    MiscReportFacadeRemote beanRemote;
    List <CurrencyExchangePojo> reportList = new ArrayList<CurrencyExchangePojo>();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public CurrencyExchange() {
        try {
            reportFormatIn = new ArrayList<SelectItem>();
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");

            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();

            for (int i = 0; i < brnLst.size(); i++) {
                Vector ele7 = (Vector) brnLst.get(i);
                branchList.add(new SelectItem(ele7.get(0).toString(), ele7.get(1).toString()));
            }
            this.setCalDate(calDate);
            reportFormatIn.add(new SelectItem("N", "In Detail"));
            reportFormatIn.add(new SelectItem("Y", "In Summary"));
            
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    
     public void viewPdfReport() {
        setMessage("");
        try {   
            if (calDate == null ) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(dmy.format(calDate))) {
                setMessage("Please fill proper to date ! ");
                return;
            }

            if (calDate.after(getDt())) {
                setMessage("to date should be greater than current date !");
                return;
            }

          
            String report = "Currency Exchange Report";
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            reportList = beanRemote.getCurrencyExchangrData(branch, ymd.format(calDate), reportFormat);
           
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().openPdf("CurrencyExchangeReport_"+ymd.format(calDate), reportFormat.equalsIgnoreCase("Y")?"CurrencyExchange":"CurrencyExchangeDetailed", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Currency Exchange Report");

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
     
     

    public void btnRefreshAction() {
        setMessage("");
        this.setReportFormat("N");
        
    }

    public String btnExitAction() {
        return "case1";
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }       

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    public List<SelectItem> getReportFormatIn() {
        return reportFormatIn;
    }

    public void setReportFormatIn(List<SelectItem> reportFormatIn) {
        this.reportFormatIn = reportFormatIn;
    }
    
}
