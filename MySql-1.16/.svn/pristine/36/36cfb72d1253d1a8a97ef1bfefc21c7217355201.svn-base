/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.NpaReportFacadeRemote;
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
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class NpaRecovery extends BaseBean {
    
    private String message;
    private String acType;
    private String frdt;
    private String todt;
    private List<SelectItem> acTypeList;
    private String repType;
    private List<SelectItem> repTypeList;
    Date dt = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeName = "NpaReportFacade";
    private NpaReportFacadeRemote remote = null;
    private final String jndiName = "CommonReportMethods";
    private CommonReportMethodsRemote common = null;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    
    public String getAcType() {
        return acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }
    
    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }
    
    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }
    
    public String getFrdt() {
        return frdt;
    }
    
    public void setFrdt(String frdt) {
        this.frdt = frdt;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getTodt() {
        return todt;
    }
    
    public void setTodt(String todt) {
        this.todt = todt;
    }
    
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
    
    public NpaRecovery() {
        try {
            remote = (NpaReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);
            
            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("S", "--Select--"));
            repTypeList.add(new SelectItem("NR", "Npa Recovery"));
            repTypeList.add(new SelectItem("AN", "Auto Npa to Operative"));
            
            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            
            fillAccountType();
            this.setFrdt(getTodayDate());
            this.setTodt(getTodayDate());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void fillAccountType() {
        try {
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("ALL"));
            List resultList = remote.getAccountType();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector element = (Vector) resultList.get(i);
                    String acctcode = element.get(0).toString();
                    acTypeList.add(new SelectItem(acctcode));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void btnHtmlAction() {
        this.setMessage("");
        String bankName = "", bankAddress = "";
        try {
            if (repType == null || repType.equalsIgnoreCase("S")) {
                this.setMessage("Please select Report Type !");
                return;
            }
            if (this.frdt == null || this.frdt.equals("")) {
                this.setMessage("Please select from date !");
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.frdt);
            if (result == false) {
                this.setMessage("Please select proper from date !");
                return;
            }
            if (this.todt == null || this.todt.equals("")) {
                this.setMessage("Please select to date !");
                return;
            }
            result = new Validator().validateDate_dd_mm_yyyy(this.todt);
            if (result == false) {
                this.setMessage("Please select proper to date !");
                return;
            }
            
            int compareValue = dmy.parse(frdt).compareTo(dmy.parse(todt));
            if (compareValue > 0) {
                this.setMessage("Please check from date !");
                return;
            }
            String reportName = "";
            List resultList = new ArrayList();
            
            if (repType.equalsIgnoreCase("NR")) {
                reportName = "Npa Recovery Report";
                resultList = remote.getNpaRecoveryData(this.acType, ymd.format(dmy.parse(this.frdt)), ymd.format(dmy.parse(this.todt)), this.branchCode);
            } else {
                reportName = "Auto Npa to Operative Report";
                resultList = remote.getNpaAutoMarkingData(this.acType, ymd.format(dmy.parse(this.frdt)), ymd.format(dmy.parse(this.todt)), this.branchCode);
                
            }
            
            if (!resultList.isEmpty()) {
                
                if (!this.branchCode.equalsIgnoreCase("0A")) {
                    List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                } else {
                    List dataList1 = common.getBranchNameandAddress("90");
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                }
                
                Map fillParams = new HashMap();
                fillParams.put("pBankName", bankName);
                fillParams.put("pBankAdd", bankAddress);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", reportName);
                fillParams.put("pPrintedDate", getFrdt() + " to " + getTodt());
                
                if (repType.equalsIgnoreCase("NR")) {
                    new ReportBean().jasperReport("NpaRecovery", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
                } else {
                    new ReportBean().jasperReport("AutoNpaToOperative", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
                }
                
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", reportName);
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void pdfDownLoad() {
        this.setMessage("");
        String bankName = "", bankAddress = "";
        try {
            if (repType == null || repType.equalsIgnoreCase("S")) {
                this.setMessage("Please select Report Type !");
                return;
            }
            if (this.frdt == null || this.frdt.equals("")) {
                this.setMessage("Please select from date !");
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.frdt);
            if (result == false) {
                this.setMessage("Please select proper from date !");
                return;
            }
            if (this.todt == null || this.todt.equals("")) {
                this.setMessage("Please select to date !");
                return;
            }
            result = new Validator().validateDate_dd_mm_yyyy(this.todt);
            if (result == false) {
                this.setMessage("Please select proper to date !");
                return;
            }
            
            int compareValue = dmy.parse(frdt).compareTo(dmy.parse(todt));
            if (compareValue > 0) {
                this.setMessage("Please check from date !");
                return;
            }
            String reportName = "";
            List resultList = new ArrayList();
            if (repType.equalsIgnoreCase("NR")) {
                reportName = "Npa Recovery Report";
                resultList = remote.getNpaRecoveryData(this.acType, ymd.format(dmy.parse(this.frdt)), ymd.format(dmy.parse(this.todt)), this.branchCode);
            } else {
                resultList = remote.getNpaAutoMarkingData(this.acType, ymd.format(dmy.parse(this.frdt)), ymd.format(dmy.parse(this.todt)), this.branchCode);
                reportName = "Auto Npa to Operative Report";
            }
            
            if (!resultList.isEmpty()) {
                
                if (!this.branchCode.equalsIgnoreCase("0A")) {
                    List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                } else {
                    List dataList1 = common.getBranchNameandAddress("90");
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                }
                
                Map fillParams = new HashMap();
                fillParams.put("pBankName", bankName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", reportName);
                fillParams.put("pPrintedDate", getFrdt() + " to " + getTodt());
                if (repType.equalsIgnoreCase("NR")) {
                    new ReportBean().openPdf("Npa Recovery Report_" + ymd.format(dmy.parse(this.frdt)) + " to " + ymd.format(dmy.parse(this.todt)), "NpaRecovery", new JRBeanCollectionDataSource(resultList), fillParams);
                } else {
                    new ReportBean().openPdf("Auto Npa To Operative_" + ymd.format(dmy.parse(this.frdt)) + " to " + ymd.format(dmy.parse(this.todt)), "AutoNpaToOperative", new JRBeanCollectionDataSource(resultList), fillParams);
                }
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", reportName);
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void btnRefreshAction() {
        this.setMessage("");
        this.setRepType("S");
        this.setAcType("ALL");
        this.setFrdt(getTodayDate());
        this.setTodt(getTodayDate());
    }
    
    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
