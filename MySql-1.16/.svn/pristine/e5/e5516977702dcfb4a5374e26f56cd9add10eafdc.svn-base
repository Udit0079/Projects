/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.CrrSlrPojo;
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
 * @author Sanjay Khandelwal
 */
public class CrrSlr extends BaseBean {

    private String fromDate;
    private String toDate;
    private String message;
    private String outputType;
    private String repOpt;
    private List<SelectItem> outputTypeList;
    private List<SelectItem> repOptionList;
    Date dt = new Date();
    private final String jndiHomeName = "HoReportFacade";
    private HoReportFacadeRemote hoRemote = null;
    private CommonReportMethodsRemote common = null;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
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

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public List<SelectItem> getOutputTypeList() {
        return outputTypeList;
    }

    public void setOutputTypeList(List<SelectItem> outputTypeList) {
        this.outputTypeList = outputTypeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public CrrSlr() {
        try {
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);

            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            setMessage("");
            outputTypeList = new ArrayList<SelectItem>();
            outputTypeList.add(new SelectItem("", "--Select--"));
            outputTypeList.add(new SelectItem("0", "CRR"));
            outputTypeList.add(new SelectItem("1", "SLR"));

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

    public void view() {
        Map map = new HashMap();
        try {
            if (this.fromDate == null) {
                setMessage("Please Fill From Date");
                return;
            }
            if (this.toDate == null) {
                setMessage("Please Fill To Date");
                return;
            }
            if (sdf.parse(toDate).compareTo(sdf.parse(fromDate)) <= 0) {
                setMessage("From Date can not be greater than To Date.");
                return;
            }
            if (this.outputType.equalsIgnoreCase("")) {
                setMessage("Please Select the OutputType");
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
                amtIn = "Amounts (Rs. in Thousand)";
            } else if (repOpt.equalsIgnoreCase("L")) {
                amtIn = "Amounts (Rs. in Lac)";
            } else if (repOpt.equalsIgnoreCase("C")) {
                amtIn = "Amounts (Rs. in Crore)";
            } else if (repOpt.equalsIgnoreCase("R")) {
                amtIn = "Amounts (Rs.)";
            }
            map.put("repFromDate", this.fromDate);
            map.put("repToDate", this.toDate);
            map.put("sysDate", this.dt);
            map.put("username", this.getUserName());
            map.put("report", report);
            map.put("pBankName", bankName);
            map.put("pBankAddress", bankAddress);
            map.put("pAmtIn", amtIn);
            map.put("pPrintedDate", this.toDate);

            if (outputType.equals("0")) {
                List<CrrSlrPojo> crrList = hoRemote.getCrrDetails(getOrgnBrCode(),repOpt, fromDate, toDate,"ALL");
                report = "CRR REPORT";
                new ReportBean().jasperReport("CRR_TEXT", "text/html",
                        new JRBeanCollectionDataSource(crrList), map, report);                
            } else {
                List<CrrSlrPojo> crrList = hoRemote.getSlrDetails(getOrgnBrCode(), repOpt, fromDate, toDate,"ALL");
                report = "SLR REPORT";
                new ReportBean().jasperReport("SLR_TEXT", "text/html",
                        new JRBeanCollectionDataSource(crrList), map, report);
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }
    
    public void viewPdf() {
        Map map = new HashMap();
        try {
            if (this.fromDate == null) {
                setMessage("Please Fill From Date");
                return;
            }
            if (this.toDate == null) {
                setMessage("Please Fill To Date");
                return;
            }
            if (sdf.parse(toDate).before(sdf.parse(fromDate)) ) {
                setMessage("From Date can not be greater than To Date.");
                return;
            }
            if (this.outputType.equalsIgnoreCase("")) {
                setMessage("Please Select the OutputType");
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
            map.put("repFromDate", this.fromDate);
            map.put("repToDate", this.toDate);
            map.put("sysDate", this.dt);
            map.put("username", this.getUserName());
            map.put("report", report);
            map.put("pBankName", bankName);
            map.put("pBankAddress", bankAddress);
            map.put("pAmtIn", amtIn);
            map.put("pPrintedDate", this.toDate);

            if (outputType.equals("0")) {
                List<CrrSlrPojo> crrList = hoRemote.getCrrDetails(getOrgnBrCode(),repOpt, fromDate, toDate,"ALL");
                report = "CRR REPORT";
                //new ReportBean().downloadPdf("CRR_"+ymd.format(sdf.parse(toDate)), "CRR_PDF", new JRBeanCollectionDataSource(crrList), map);
               new ReportBean().openPdf("CRR_"+ymd.format(sdf.parse(toDate)), "CRR_PDF", new JRBeanCollectionDataSource(crrList), map);
            } else {
                List<CrrSlrPojo> crrList = hoRemote.getSlrDetails(getOrgnBrCode(), repOpt, fromDate, toDate,"ALL");
                report = "SLR REPORT";
                //new ReportBean().downloadPdf("SLR_"+ymd.format(sdf.parse(toDate)), "SLR_PDF", new JRBeanCollectionDataSource(crrList), map);
                new ReportBean().openPdf("SLR_"+ymd.format(sdf.parse(toDate)), "SLR_PDF", new JRBeanCollectionDataSource(crrList), map);
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void refresh(){
        setMessage("");
        setRepOpt("");
        setOutputType("");
    }
    
    public String exit() {
        return "case1";
    }
}
