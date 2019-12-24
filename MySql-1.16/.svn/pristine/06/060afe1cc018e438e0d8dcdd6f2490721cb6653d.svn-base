/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.TransactionDetailsPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
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
 * @author Nishant Srivastava
 */
public class TransactionDetails extends BaseBean {

    private String message;
    private String brCode;
    private List<SelectItem> branchCodeList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String statusType;
    private List<SelectItem> statusTypeList;
    private String date;
    private String toDt;
    private String displaybnkNM="";
    private String neftBankName;
    private List<SelectItem> neftBankNameList;
    private CommonReportMethodsRemote common;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    MiscReportFacadeRemote beanRemote;
    List<TransactionDetailsPojo> reportList;
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "NeftRtgsMgmtFacade";

    public List<TransactionDetailsPojo> getReportList() {
        return reportList;
    }

    public void setReportList(List<TransactionDetailsPojo> reportList) {
        this.reportList = reportList;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBrCode() {
        return brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public List<SelectItem> getStatusTypeList() {
        return statusTypeList;
    }

    public void setStatusTypeList(List<SelectItem> statusTypeList) {
        this.statusTypeList = statusTypeList;
    }

    public String getNeftBankName() {
        return neftBankName;
    }

    public void setNeftBankName(String neftBankName) {
        this.neftBankName = neftBankName;
    }

    public String getDisplaybnkNM() {
        return displaybnkNM;
    }

    public void setDisplaybnkNM(String displaybnkNM) {
        this.displaybnkNM = displaybnkNM;
    }

    public List<SelectItem> getNeftBankNameList() {
        return neftBankNameList;
    }

    public void setNeftBankNameList(List<SelectItem> neftBankNameList) {
        this.neftBankNameList = neftBankNameList;
    }
    

    public TransactionDetails() {
        try {
            remoteInterface = (NeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List branchCode = new ArrayList();
            branchCode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!branchCode.isEmpty()) {
                for (int i = 0; i < branchCode.size(); i++) {
                    Vector brnVector = (Vector) branchCode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("GO", "General Outward"));
            reportTypeList.add(new SelectItem("CO", "CPSMS Outward"));
            /*
             * S       Paid
             U       Un-Paid
             I        Response Awaiting
             P       Pending (Based On Auth, Y- Verified, N-Pending)
             A       ALL
             */
            statusTypeList = new ArrayList<SelectItem>();
            statusTypeList.add(new SelectItem("A", "ALL"));
            statusTypeList.add(new SelectItem("S", "Paid"));
            statusTypeList.add(new SelectItem("U", "Un-Paid"));
            statusTypeList.add(new SelectItem("I", "Response Awaiting"));
            statusTypeList.add(new SelectItem("P", "Pending"));


            date = sdf.format(new Date());
            toDt = sdf.format(new Date());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    
    
     public void transferModeOperation(){
        try{
            if(this.reportType.equalsIgnoreCase("GO")){
                this.displaybnkNM = "";
                neftBankNameList = new ArrayList<>();
                neftBankNameList.add(new SelectItem("0","--Select--"));
                List list = remoteInterface.autoNeftBankNameList();
                for(int i=0;i<list.size();i++){
                    Vector vec = (Vector) list.get(i);
                    neftBankNameList.add(new SelectItem(vec.get(0).toString(),vec.get(0).toString()));
                }
                
            }else{
                this.displaybnkNM = "none";
                neftBankNameList = new ArrayList<>();
                this.setNeftBankName("");
            }
            
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }

    public String viewReport() {
        try {
            if(this.neftBankName==null){
                this.neftBankName ="";
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
            String reportName = null, headerField = "";
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            if (!brCode.equals("A")) {
                brCode = CbsUtil.lPadding(2, Integer.parseInt(brCode));
            }

            reportList = new ArrayList<TransactionDetailsPojo>();
            reportList = beanRemote.gettransDetailsReport(ymd.format(dmy.parse(date)), ymd.format(dmy.parse(toDt)), this.brCode, "", "", 0, reportType, statusType,this.neftBankName);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not Exist");
            }
            if (reportType.equalsIgnoreCase("GO")) {
                reportName = "General Outward Fund Transfer Report";
                headerField = "Payment Type";
            } else if (reportType.equalsIgnoreCase("CO")) {
                reportName = "CPSMS Outward Fund Transfer Report";
                headerField = "Unique Customer Ref No";
            }
            String bankName = "";
            String branchAddress = "";
            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());

            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                branchAddress = (String) dataList1.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("reportName", reportName);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDate", date + "-" + toDt);
            fillParams.put("pbankName", bankName);
            fillParams.put("pbankAddress", branchAddress);
            fillParams.put("pHeader", headerField);
            ReportBean rb = new ReportBean();
            rb.jasperReport("TransactionReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, reportName);
            return "report";
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return null;
    }

    public void btnPdfAction() {
        try {
            if(this.neftBankName==null){
                this.neftBankName ="";
            }
            //String report = "Outward Fund Transfer Report";
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
            String reportName = null, headerField = "";
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            if (!brCode.equals("A")) {
                brCode = CbsUtil.lPadding(2, Integer.parseInt(brCode));
            }

            reportList = new ArrayList<TransactionDetailsPojo>();
            reportList = beanRemote.gettransDetailsReport(ymd.format(dmy.parse(date)), ymd.format(dmy.parse(toDt)), this.brCode, "", "", 0, reportType, statusType,this.neftBankName);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not Exist");
            }
            if (reportType.equalsIgnoreCase("GO")) {
                reportName = "General Outward Fund Transfer Report";
                headerField = "Payment Type";
            } else if (reportType.equalsIgnoreCase("CO")) {
                headerField = "Unique Customer Ref No";
                reportName = "CPSMS Outward Fund Transfer Report";
            }
            String bankName = "";
            String branchAddress = "";
            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());

            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                branchAddress = (String) dataList1.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("reportName", reportName);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDate", date + "-" + toDt);
            fillParams.put("pbankName", bankName);
            fillParams.put("pbankAddress", branchAddress);
            fillParams.put("pHeader", headerField);

            new ReportBean().openPdf("TransactionReport_" + ymd.format(dmy.parse(getTodayDate())), "TransactionReport", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", reportName);

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        message = "";
        date = sdf.format(new Date());
        toDt = sdf.format(new Date());
        reportList = new ArrayList<TransactionDetailsPojo>();
    }

    public String exitAction() {
        return "case1";
    }
}
