/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.YearEndDatePojo;
import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class GlbEntry extends BaseBean {
    
    String message;
    String calDate;    
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeName = "BankProcessManagementFacade";
    private String reportType;
    private List<SelectItem> reportTypeList;
    private RbiReportFacadeRemote rbiRemote;
    private BankProcessManagementFacadeRemote bankProcessManagementFacade = null;
    
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
    public String getReportType() {
        return reportType;
    }
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }
    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public GlbEntry() {
        try {
            bankProcessManagementFacade = (BankProcessManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            rbiRemote = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("", "--Select--"));
            reportTypeList.add(new SelectItem("1", "MONTH END BALANCE ENTRY"));
            reportTypeList.add(new SelectItem("2", "FORTNIGHT BALANCE ENTRY"));
            this.setCalDate(dmy.format(new Date()));            
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void btnPdfAction() {
        try {
            String report = "Month End Balance Entry";
            this.setMessage("");
            if(this.reportType.equalsIgnoreCase("")){
                this.setMessage("Please select The Report Type.");
                return;
            }
            if (calDate.equalsIgnoreCase("") || this.calDate == null) {
                this.setMessage("Please Enter Date.");
                return;
            }
            YearEndDatePojo yDate = new YearEndDatePojo();
            yDate = (YearEndDatePojo)  rbiRemote.getYearEndDataAccordingToDate("90",ymd.format(dmy.parse(calDate)));            
            if (this.reportType.equalsIgnoreCase("2")){
                if(!ymd.format(dmy.parse(calDate)).equalsIgnoreCase(yDate.getMaxDate())){
                    this.setMessage("Please Enter Year End Date Only!!");
                    return;
                }
            }
            /**END**/
            String glbEntry ="";
            if(reportType.equalsIgnoreCase("1")){
                glbEntry = bankProcessManagementFacade.entryAsOnDateBalanceList(getOrgnBrCode().equalsIgnoreCase("90")?"0A":getOrgnBrCode(), ymd.format(dmy.parse(calDate)));
            } else {                
                glbEntry = bankProcessManagementFacade.insertFortnightBalAsPerGLB(getUserName(), yDate.getMinDate(), yDate.getMaxDate(), "0A");
            }
            if(glbEntry.equalsIgnoreCase("true")){
                setMessage("Data entered successfully");
            } else if (glbEntry.equalsIgnoreCase("false")){
                setMessage("Data already exist!!!");
            }else{
                setMessage(glbEntry);
            }
            

        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            this.setMessage(e.getMessage());
        }
        //return "report";
    }

    public String btnExitAction() {
        this.setCalDate(dmy.format(new Date()));
        return "case1";
    }
    
}
