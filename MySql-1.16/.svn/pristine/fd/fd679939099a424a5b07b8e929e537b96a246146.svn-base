/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.clg;

import com.cbs.dto.report.ClearingOperationReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.ClgReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class ClearingOperationReport extends BaseBean {

    private String errorMsg;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private Date dt = new Date();
    private CommonReportMethodsRemote common;
    private ClgReportFacadeRemote beanRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
     List<ClearingOperationReportPojo> reportList = new ArrayList<ClearingOperationReportPojo>();

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }
     
     

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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
    
    public ClearingOperationReport(){
        try{
            frmDt = dmy.format(date);
            toDt = dmy.format(date);
            beanRemote = (ClgReportFacadeRemote) ServiceLocator.getInstance().lookup("ClgReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            
        }catch(Exception e){
            setErrorMsg(e.getMessage());
        } 
    }
    
    public void viewReport() {
         String branchName = "";
        String address = "";
        String report = "Clearing Operation Report";
        try{
            
             if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill from Date");
                return ;
            }
            if (!Validator.validateDate(frmDt)) {
                setErrorMsg("Please select Proper from date ");
                return ;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill to Date");
                return ;
            }
            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please select Proper to Date");
                return ;
            }

            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            Map fillParams = new HashMap();
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReprtName", report);
            fillParams.put("pReportDate", frmDt + " To " + toDt);
            fillParams.put("pPrintedBy", getUserName());
              
            String forDate = ymd.format(dmy.parse(frmDt));
            String toDate = ymd.format(dmy.parse(toDt));
            
            reportList = beanRemote.getClearingOperationData(forDate, toDate, getOrgnBrCode());
            if(reportList.isEmpty()){
                throw new ApplicationException("Data does not exist!!!");
            }
            
             new ReportBean().jasperReport("ClearingOperation", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Clearing Operation Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");   
            
        }catch(Exception e){
            setErrorMsg(e.getMessage());
        }
    }
    
     public void refresh() {
        this.setErrorMsg("");
        this.setFrmDt(getTodayDate());
        this.setToDt(getTodayDate());
    }

    public String exitAction() {
        refresh();
        return "case1";
    }
    
}
