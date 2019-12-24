/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.ho.HoReconPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
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
 * @author saurabhsipl
 */
public class BranchAdjustmentDiff extends BaseBean {
    private String asOnDt;
    private String message;
    private String repOpt;
    private List<SelectItem> repOptionList;
    Date dt = new Date();
    private final String jndiHomeName = "HoReportFacade";
    private HoReportFacadeRemote hoRemote = null;
    private CommonReportMethodsRemote common = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
    public BranchAdjustmentDiff(){
        try {
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);

            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            setMessage("");
            repOptionList = new ArrayList<SelectItem>();
            repOptionList.add(new SelectItem("", "--Select--"));
            repOptionList.add(new SelectItem("R", "Rs."));
            repOptionList.add(new SelectItem("T", "Thousand"));
            repOptionList.add(new SelectItem("L", "Lacs"));
            repOptionList.add(new SelectItem("C", "Crore"));
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void viewPdf() {
        Map map = new HashMap();
        try {
             if (this.asOnDt == null) {
                setMessage("Please Fill To Date");
                return;
            }
            if (sdf.parse(asOnDt).compareTo(new Date()) > 0) {
                setMessage("Date can not be greater than Current Date.");
                return;
            }
            if (this.repOpt.equalsIgnoreCase("")) {
                setMessage("Please Select the Report Option");
                return;
            }
            setMessage("");
            String bankName = "";
            String bankAddress = "";
            String report = "";
            List dataList1;
            dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }            
            String amtIn = null;
            if (repOpt.equalsIgnoreCase("T")) {
                amtIn = "Amount (Rs. in Thousand)";
            } else if (repOpt.equalsIgnoreCase("L")) {
                amtIn = "Amount (Rs. in Lac)";
            } else if (repOpt.equalsIgnoreCase("C")) {
                amtIn = "Amount (Rs. in Crore)";
            } else if (repOpt.equalsIgnoreCase("R")) {
                amtIn = "Amount (Rs.)";
            }
            String reportopt="";
            if (repOpt.equalsIgnoreCase("T")) {
                reportopt = "1000";
            } else if (repOpt.equalsIgnoreCase("L")) {
                reportopt = "100000";
            } else if (repOpt.equalsIgnoreCase("C")) {
                reportopt = "10000000";
            } else if (repOpt.equalsIgnoreCase("R")) {
                reportopt = "1";
            }            
            report = "Head Office Reconciliation";
            map.put("pPrintedBy", this.getUserName());
            map.put("pPrintedDate", this.asOnDt);
            map.put("pReportDate", this.asOnDt);
            map.put("pReportName", report);
            map.put("pbankName", bankName);
            map.put("pbankAddress", bankAddress);
            map.put("pAmtIn", amtIn);
            map.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
            //List<BranchAdjPojo> resultList = common.getBranchAdjustment(getOrgnBrCode(), ymd.format(sdf.parse(asOnDt)), reportopt);
            List<HoReconPojo> resultList = common. getHeadOfficeDetails(getOrgnBrCode(), ymd.format(sdf.parse(asOnDt)), reportopt);
            new ReportBean().openPdf("BranchAdjustment"+ymd.format(sdf.parse(asOnDt)), "dtWiseRecon", new JRBeanCollectionDataSource(resultList), map);

            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    } 

    public String getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(String asOnDt) {
        this.asOnDt = asOnDt;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRepOpt() {
        return repOpt;
    }

    public void setRepOpt(String repOpt) {
        this.repOpt = repOpt;
    }

    public List<SelectItem> getRepOptionList() {
        return repOptionList;
    }

    public void setRepOptionList(List<SelectItem> repOptionList) {
        this.repOptionList = repOptionList;
    }
      public void refresh(){
        setMessage("");
        setRepOpt("");
    }
    
    public String exit() {
        return "case1";
    }
    
    
}
