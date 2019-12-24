/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.ho.StmtOfOpenCloseOfficePojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
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
 * @author saurabhsipl
 */
public class StmtOfOpenCloseOffices extends BaseBean {
    private String fromDt;
    private String message;
    private String toDt;    
    private List<SelectItem> repOptionList;
    Date dt = new Date();
    private final String jndiHomeName = "HoReportFacade";
    private HoReportFacadeRemote hoRemote = null;
    private CommonReportMethodsRemote common = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
   

    public StmtOfOpenCloseOffices() {
        try {
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void viewPdf() {
        Map map = new HashMap();
        try {
             if (this.fromDt == null) {
                setMessage("Please Fill From Date");
                return;
            }
            if (this.toDt == null) {
                setMessage("Please Fill To Date");
                return;
            }
            if (sdf.parse(toDt).compareTo(new Date()) > 0) {
                setMessage("Date can not be greater than Current Date.");
                return;
            }            
            setMessage("");
            String bankName = "";
            String bankAddress = "";
            String report = "";
            String reportName1 = "Form VI - Statement of Offices in India";
            report = "Form VI - Statement of Offices in India";
            List dataList1= common.getBranchNameandAddress(getOrgnBrCode());
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
            map.put("repFromDate", this.fromDt);
            map.put("repToDate", this.toDt);
            map.put("sysDate", this.dt);
            map.put("pPrintedBy", this.getUserName());
            map.put("pReportName", report);
            map.put("pBankName", bankName);
            map.put("pBankAddress", bankAddress);
            List<StmtOfOpenCloseOfficePojo> mainList = hoRemote.getStmtOfOpenCloseOffice("0A",fromDt, toDt);
            map.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
            new ReportBean().openPdf("Form VI - Statement of Offices in India"+ymd.format(sdf.parse(toDt)), "StmtOfOpenCloseOffice", new JRBeanCollectionDataSource(mainList), map);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            e.printStackTrace();
            setMessage(e.getMessage());
        }
    }
    public void refresh(){
        setMessage("");
        setFromDt("");
        setToDt("");
    }
    
    public String exit() {
        return "case1";
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getFromDt() {
        return fromDt;
    }

    public void setFromDt(String fromDt) {
        this.fromDt = fromDt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getRepOptionList() {
        return repOptionList;
    }

    public void setRepOptionList(List<SelectItem> repOptionList) {
        this.repOptionList = repOptionList;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }
    
}
