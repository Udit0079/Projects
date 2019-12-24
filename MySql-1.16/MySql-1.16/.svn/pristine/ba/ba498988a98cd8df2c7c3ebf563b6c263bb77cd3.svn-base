/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.locker;

import com.cbs.dto.report.MinBalanceChargesPostPojo;
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
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author sipl
 */
public class LockerExeErrReport extends BaseBean {
    
    private String message, repType;
    Date calFromDate = new Date();
    private List<SelectItem> repTypeList;
    OtherReportFacadeRemote beanRemote;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
    }

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }
    
    public LockerExeErrReport() {
        try {
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("P", "POSTED"));
            repTypeList.add(new SelectItem("E", "ERROR"));            
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public String printAction() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            
            List<MinBalanceChargesPostPojo> LockerExeErrRep = beanRemote.lockerExeErrReport(this.getRepType(), ymdFormat.format(calFromDate), getOrgnBrCode());
            if (LockerExeErrRep == null) {
                setMessage("System error occurred");
                return null;
            }
            
            if (!LockerExeErrRep.isEmpty()) {
                String repName = "";
                if(this.getRepType().equalsIgnoreCase("E")){
                    repName = "Locker Error Report";
                }else{
                    repName = "Locker Executed Report";
                }
                
                Map fillParams = new HashMap();
                fillParams.put("pAsOnDate", sdf.format(calFromDate));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", repName);
                
                new ReportBean().jasperReport("LockerExecutiveReport", "text/html",
                        new JRBeanCollectionDataSource(LockerExeErrRep), fillParams,repName);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", repName);                
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }
    
    public void refreshAction() {
        try {
            setMessage("");
            calFromDate = new Date();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }
}