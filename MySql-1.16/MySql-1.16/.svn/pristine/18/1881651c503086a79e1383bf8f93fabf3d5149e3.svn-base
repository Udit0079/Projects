/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.BillSundryDetailsPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class BillSundrydetails extends BaseBean{
    private String message;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String tillDt;
    private Date date = new Date();
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote beanFacade;
    private MiscReportFacadeRemote remoteFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<BillSundryDetailsPojo> reportList = new ArrayList<BillSundryDetailsPojo>();

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTillDt() {
        return tillDt;
    }

    public void setTillDt(String tillDt) {
        this.tillDt = tillDt;
    }
    
    public BillSundrydetails(){
        try{
            tillDt = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");

            List brncode = new ArrayList();
            brncode = beanFacade.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            
        }catch(Exception ex){
            setMessage(ex.getMessage());
        }
    }
    
    public void viewReport() {
        String report = "Bill Sundry Detail Report";
        try{
             if (tillDt == null || tillDt.equalsIgnoreCase("")) {
                setMessage("Please fill from Date");
                return;
            }
            if (!Validator.validateDate(tillDt)) {
                setMessage("Please select Proper from date ");
                return;
            }

            if (dmy.parse(tillDt).after(getDate())) {
                setMessage("As on date should be less than current date !");
                return;
            }
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pReportDt", this.tillDt);
            
             String tilldate = ymd.format(dmy.parse(tillDt));

            reportList = remoteFacade.getBillSundryDetail(branchCode, tilldate);
            
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does exist !!!");
            }

            new ReportBean().jasperReport("BillSundryDetail", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Bill Sundry Detail Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

            
        }catch(Exception ex){
            setMessage(ex.getMessage());
        }
    }
    
     public void btnRefreshAction() {
        this.setMessage("");
        this.setTillDt(getTodayDate());
       
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
