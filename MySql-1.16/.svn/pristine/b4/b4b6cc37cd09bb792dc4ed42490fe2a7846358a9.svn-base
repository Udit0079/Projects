/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.RbiSossPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
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
public class MisDataForMgmt extends BaseBean {
    private String asOnDt;
    private String message;
    private String repOpt;
    private List<SelectItem> repOptionList;
    private String repIn;
    private List<SelectItem> repInList;
    private int noOfEmpPreDate;
    private int noOfEmpAsOnDate;
    Date dt = new Date();
    private final String jndiHomeName = "HoReportFacade";
    private HoReportFacadeRemote hoRemote = null;
    private CommonReportMethodsRemote common = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
    public MisDataForMgmt() {
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
            repInList = new ArrayList<SelectItem>();
            repInList.add(new SelectItem("", "--Select--"));
            repInList.add(new SelectItem("N", "Detail"));
            repInList.add(new SelectItem("Y", "Summary"));
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
            BigDecimal amt= new BigDecimal("0");
            if (repOpt.equalsIgnoreCase("T")) {
                amtIn = "Amount (Rs. in Thousand)";
                amt=new BigDecimal("1000");
            } else if (repOpt.equalsIgnoreCase("L")) {
                amtIn = "Amount (Rs. in Lac)";
                amt=new BigDecimal("100000");
            } else if (repOpt.equalsIgnoreCase("C")) {
                amtIn = "Amount (Rs. in Crore)";
                amt=new BigDecimal("100000000");
            } else if (repOpt.equalsIgnoreCase("R")) {
                amtIn = "Amount (Rs.)";
                amt=new BigDecimal("1");
            }
            report = "MIS DATA FOR TOP MANAGEMENT";
            map.put("pPrintedBy", this.getUserName());
            map.put("pPrintedDate", this.asOnDt);
            map.put("report", report);
            map.put("pBankName", bankName);
            map.put("pBankAddress", bankAddress);
            map.put("username", getUserName());
            map.put("pAmtIn", amtIn);
            map.put("pCurFinDate", amtIn);
            map.put("pPreFinDate", amtIn);
            map.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
            String preDate= CbsUtil.dateAdd(ymd.format(sdf.parse(asOnDt)), -1);
            List<String> dates = new ArrayList<String>();
            dates.add(ymd.format(sdf.parse(asOnDt)));
            dates.add(preDate);
            map.put("pCurDate", asOnDt);
            map.put("pPreDate", sdf.format(ymd.parse(preDate)));
            List<RbiSossPojo> dtlRegList = hoRemote.getMisDataToMgmt("MIS_DATA", dates, "0A", amt, repIn,noOfEmpPreDate,noOfEmpAsOnDate);
            if(dtlRegList.isEmpty()){
                System.out.println("No data To Print!!!!!");
            } else {
                new ReportBean().openPdf("MIS_DATA", "MIS_DATA", new JRBeanCollectionDataSource(dtlRegList), map);                
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void refresh(){
        setMessage("");
        setRepOpt("");
        setRepIn("");
    }
    
    public String exit() {
        return "case1";
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

    public String getRepIn() {
        return repIn;
    }

    public void setRepIn(String repIn) {
        this.repIn = repIn;
    }

    public List<SelectItem> getRepInList() {
        return repInList;
    }

    public void setRepInList(List<SelectItem> repInList) {
        this.repInList = repInList;
    }
    public int getNoOfEmpAsOnDate() {
        return noOfEmpAsOnDate;
    }
    public void setNoOfEmpAsOnDate(int noOfEmpAsOnDate) {
        this.noOfEmpAsOnDate = noOfEmpAsOnDate;
    }
    public int getNoOfEmpPreDate() {
        return noOfEmpPreDate;
    }
    public void setNoOfEmpPreDate(int noOfEmpPreDate) {
        this.noOfEmpPreDate = noOfEmpPreDate;
    }
    
}
