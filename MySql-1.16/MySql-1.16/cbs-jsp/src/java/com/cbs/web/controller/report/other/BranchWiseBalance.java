/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.controller.report.other;

import com.cbs.dto.report.LoanIntCertPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


/**
 *
 * @author sipl
 */
public class BranchWiseBalance extends BaseBean {
    Date dt = new Date();    
    private String errorMsg;    
    Date asOnDt;    
    private OtherReportFacadeRemote loanFacade;
    private CommonReportMethodsRemote common; 
    
    List<LoanIntCertPojo> repDataList = new ArrayList<LoanIntCertPojo>();   
    
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(Date asOnDt) {
        this.asOnDt = asOnDt;
    }   
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
    public BranchWiseBalance(){
        try{
            this.setAsOnDt(dt);            
            loanFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");            
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        } 
    }
    
    public void viewReport(){
        setErrorMsg("");
        String bankName="",bankAddress="";
        if (this.asOnDt == null) {
            setErrorMsg("Please Fill From Date.");
        }         
        
        Map fillParams = new HashMap();
        try{
            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            String report = "Branch Wise Balance";
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }      
                       
            fillParams.put("pReportFrDate", sdf.format(this.asOnDt));            
            fillParams.put("SysDate", sdf.format(new Date()));
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("bankName",bankName);
            fillParams.put("bankAddr", bankAddress);             
            
            repDataList = loanFacade.getBranchWiseBal(this.getOrgnBrCode(), ymd.format(this.asOnDt));       
                new ReportBean().jasperReport("branchWiseBalRep", "text/html",
                        new JRBeanCollectionDataSource(repDataList), fillParams, "Branch Wise Balance");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
        }catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }
    
    public String exitAction() {
        refresh();
        return "case1";
    }

    public void refresh() {
        this.setErrorMsg("");
        this.setAsOnDt(dt);        
    }  
}