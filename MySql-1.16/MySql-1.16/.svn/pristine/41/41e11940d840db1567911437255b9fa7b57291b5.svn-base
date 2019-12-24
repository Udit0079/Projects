/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.dds;

import com.cbs.dto.report.AgentCommissionPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
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
 * @author Athar Reza
 */
public class AgentCommission extends BaseBean {
    
    private String errorMsg;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<AgentCommissionPojo> resultlist = new ArrayList<AgentCommissionPojo>();
    private DDSReportFacadeRemote DDSReportRemote = null;
    private final String jndiHomeName = "DDSReportFacade";
    
    public String getBranchCode() {
        return branchCode;
    }
    
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
    
    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }
    
    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
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
    
    public AgentCommission() {
        try {
            DDSReportRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            frmDt = dmy.format(date);
            toDt = dmy.format(date);
            
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }
    
    public boolean validate() {
        try {
            if (!Validator.validateDate(frmDt)) {
                errorMsg = "Please check from date";
                return false;
            }
            if (!Validator.validateDate(frmDt)) {
                errorMsg = "Please check to date";
                return false;
            }
            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                errorMsg = "From date should be less then to date";
                return false;
            }
            
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        return true;
    }
    
    public void viewReport() {
        if (validate()) {
            String branchName = "";
            String address = "";
            try {
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                String report = "Agent Commission Report";
                List brNameAdd = new ArrayList();
                brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
                if (brNameAdd.size() > 0) {
                    branchName = (String) brNameAdd.get(0);
                    address = (String) brNameAdd.get(1);
                }
                
                String duration = CbsUtil.getMonthName(Integer.parseInt(frmDt.substring(3, 5)));
                String Yr = frmDt.substring(6);
                
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportDate", "From " + frmDt + " To " + toDt);
                fillParams.put("pDuration", duration.toUpperCase() + " OF " + Yr);
                fillParams.put("pPrintDate", dmy.format(date));
                fillParams.put("pbankName", branchName);
                fillParams.put("pbankAddress", address);
                
                resultlist = DDSReportRemote.getAgentCommission((fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]), branchCode);
                if (resultlist.isEmpty()) {
                    errorMsg = "Data does not exit";
                    return;
                }
                new ReportBean().jasperReport("AgentCommissionReport", "text/html",
                        new JRBeanCollectionDataSource(resultlist), fillParams, "Agent Commission Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
                
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMsg(e.getLocalizedMessage());
            }
        }
    }
    
    public void btnPdfAction() {
        if (validate()) {
            String branchName = "";
            String address = "";
            try {
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                String report = "Agent Commission Report";
                List brNameAdd = new ArrayList();
                brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
                if (brNameAdd.size() > 0) {
                    branchName = (String) brNameAdd.get(0);
                    address = (String) brNameAdd.get(1);
                }
                
                String duration = CbsUtil.getMonthName(Integer.parseInt(frmDt.substring(3, 5)));
                String Yr = frmDt.substring(6);
                
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportDate", "From " + frmDt + " To " + toDt);
                fillParams.put("pDuration", duration.toUpperCase() + " OF " + Yr);
                fillParams.put("pPrintDate", dmy.format(date));
                fillParams.put("pbankName", branchName);
                fillParams.put("pbankAddress", address);
                
                resultlist = DDSReportRemote.getAgentCommission((fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]), branchCode);
                if (resultlist.isEmpty()) {
                    errorMsg = "Data does not exit";
                    return;
                }
                new ReportBean().openPdf("Agent Commission Report_" + ymd.format(dmy.parse(getTodayDate())), "AgentCommissionReport", new JRBeanCollectionDataSource(resultlist), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
                
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMsg(e.getLocalizedMessage());
            }
        }
    }
    
    public void refresh() {
        try {
            this.setErrorMsg("");
            frmDt = dmy.format(new Date());
            toDt = dmy.format(new Date());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }
    
    public String exitAction() {
        return "case1";
    }
}
