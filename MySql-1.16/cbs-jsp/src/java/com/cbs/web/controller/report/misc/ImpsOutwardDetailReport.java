/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.misc.ImpsOutwardDetailPojo;
import com.cbs.facade.misc.MiscMgmtFacadeS1Remote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * @author root
 */
public class ImpsOutwardDetailReport extends BaseBean {
    private String errorMsg;
    private String serviceType;
    private List<SelectItem> serviceList;
    private String frdt;
    private String todt;
    private CommonReportMethodsRemote reportRemote = null;
    private MiscMgmtFacadeS1Remote miscRemoteS1;
    private List<ImpsOutwardDetailPojo> reportList;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public ImpsOutwardDetailReport() {
        try{
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            miscRemoteS1 = (MiscMgmtFacadeS1Remote) ServiceLocator.getInstance().lookup("MiscMgmtFacadeS1");
            
            serviceList = new ArrayList<>();
            serviceList.add(new SelectItem("0","--Select--"));
            serviceList.add(new SelectItem("G","Generate MMID"));
            serviceList.add(new SelectItem("C","Cancel MMID"));
            serviceList.add(new SelectItem("F","F2A Transfer"));
            
            
        }catch(Exception ex){
            this.setErrorMsg(ex.getMessage());
        }
      }
    
   public void viewReport(){
       try{
        String bankName = "", bankAddress = "";
        List dataList1;
           String report = "Imps Outward Detail Report";
            Map fillParams = new HashMap();
            dataList1 = reportRemote.getBranchNameandAddress(getOrgnBrCode());
            
          if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
                fillParams.put("pReporDate", this.getTodayDate());
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAddr", bankAddress);
                fillParams.put("pFunction", this.serviceType);
                
                reportList =miscRemoteS1.getImpsOutwardDetailReport(this.serviceType,ymd.format(dmy.parse(frdt)),ymd.format(dmy.parse(todt)));
                 new ReportBean().jasperReport("ImpsOutwardDetailReport", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Imps Outward Detail Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                  HttpSession httpSession = getHttpSession();
                  httpSession.setAttribute("ReportName", report); 
           
       }catch(Exception ex){
           this.setErrorMsg(ex.getMessage());
       }
   }
   
   public void viewPdfReport(){
       try{
            String bankName = "", bankAddress = "";
        List dataList1;
           String report = "Imps Outward Detail Report";
            Map fillParams = new HashMap();
            dataList1 = reportRemote.getBranchNameandAddress(getOrgnBrCode());
            
          if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
                fillParams.put("pReporDate", this.getTodayDate());
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAddr", bankAddress);
                fillParams.put("pFunction", this.serviceType);
                
                reportList =miscRemoteS1.getImpsOutwardDetailReport(this.serviceType,ymd.format(dmy.parse(frdt)),ymd.format(dmy.parse(todt)));
             
                new ReportBean().openPdf("Imps Outward Detail Report_"+ ymd.format(dmy.parse(getTodayDate())) , "ImpsOutwardDetailReport", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
       }catch(Exception ex){
           this.setErrorMsg(ex.getMessage());
       }
   }

    
    
    public void btnRefreshAction(){
        this.setServiceType("0");
        this.setTodt(getTodayDate());
        this.setFrdt(getTodayDate());
    }
    
    public String exitAction(){
        return "case1";
    }
    
    
    
    
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List<SelectItem> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<SelectItem> serviceList) {
        this.serviceList = serviceList;
    }

    public String getFrdt() {
        return frdt;
    }

    public void setFrdt(String frdt) {
        this.frdt = frdt;
    }

    public String getTodt() {
        return todt;
    }

    public void setTodt(String todt) {
        this.todt = todt;
    }

    public List<ImpsOutwardDetailPojo> getReportList() {
        return reportList;
    }

    public void setReportList(List<ImpsOutwardDetailPojo> reportList) {
        this.reportList = reportList;
    }
    
    
}
