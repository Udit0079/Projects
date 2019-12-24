/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.MprPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Ankit Verma
 */
public class Mpr extends BaseBean{
    private String msg;
    private Date fromDate;
    private Date toDate;
    Date date;
    SimpleDateFormat ymd=new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    OtherReportFacadeRemote remoteFacade;
    public Mpr() {
        date=new Date();
        try {
            remoteFacade=(OtherReportFacadeRemote)ServiceLocator.getInstance().lookup("OtherReportFacade");
            fromDate=date;
            toDate=date;
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
    public void viewReport()
    {
        if(fromDate.after(toDate))
        {
            msg="From Date can not be greater than To Date !";
            return;
        }
        try {
            List<MprPojo> mprDetails = remoteFacade.getMprDetails(ymd.format(fromDate),ymd.format(toDate), getOrgnBrCode());
            CommonReportMethodsRemote common=(CommonReportMethodsRemote)ServiceLocator.getInstance().lookup("CommonReportMethods");
            List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
             Map fillParams = new HashMap();
            fillParams.put("pBankName", branchNameandAddress.get(0).toString());
            fillParams.put("pBankAdd", branchNameandAddress.get(1).toString());
            fillParams.put("pPrintedDate",sdf.format(fromDate) +" to "+sdf.format(toDate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", "Monthly Progress Report");

            new ReportBean().jasperReport("Mpr", "text/html",
                        new JRBeanCollectionDataSource(mprDetails), fillParams, "Monthly Progress Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            setMsg(e.getLocalizedMessage());
        }
        
    }
    public void refreshForm()
    {
        fromDate=date;
        toDate=date;
        msg="";
    }
    public String exitAction()
    {
        return "case1";
    }
    
}
