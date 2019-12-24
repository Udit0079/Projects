/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.inventory;

import com.cbs.dto.report.InventoryTransferReceiptTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.InventoryReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Sudhir
 */
public class TransferReceiptRegister extends BaseBean {
    
    private String orgnBrCode;
    private String todayDate;
    private HttpServletRequest req;
    private String userName;
    private String message;
    private Date tillDate;
    private String inventoryType;
    private List<SelectItem> inventoryList = new ArrayList<SelectItem>();
    private String branch;
    private List<SelectItem> branchList = new ArrayList<SelectItem>();
    private final String jndiHomeName = "InventoryReportFacade";
    private InventoryReportFacadeRemote beanRemote = null;
    private final String commonReportFacadejndiName = "CommonReportMethods";
    private CommonReportMethodsRemote commonFacadeBeanRemote = null;
    private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
            dmyformatter = new SimpleDateFormat("dd/MM/yyyy");
    private String flag;
    
    public String getBranch() {
        return branch;
    }
    
    public void setBranch(String branch) {
        this.branch = branch;
    }
    
    public List<SelectItem> getBranchList() {
        return branchList;
    }
    
    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }
    
    public List<SelectItem> getInventoryList() {
        return inventoryList;
    }
    
    public void setInventoryList(List<SelectItem> inventoryList) {
        this.inventoryList = inventoryList;
    }
    
    public String getInventoryType() {
        return inventoryType;
    }
    
    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }
    
    public String getFlag() {
        return flag;
    }
    
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getOrgnBrCode() {
        return orgnBrCode;
    }
    
    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }
    
    public Date getTillDate() {
        return tillDate;
    }
    
    public void setTillDate(Date tillDate) {
        this.tillDate = tillDate;
    }
    
    public String getTodayDate() {
        return todayDate;
    }
    
    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public TransferReceiptRegister() {
        try {
            beanRemote = (InventoryReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            commonFacadeBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(commonReportFacadejndiName);
            req = getRequest();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            //   orgnBrCode = "06";
            //userName="Sudhir";
            setUserName(req.getRemoteUser());
            Date date = new Date();
            setTodayDate(dmyformatter.format(date));
            this.setMessage("");
            onLoadData();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }
    
    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
    
    public void onLoadData() {
        try {
            inventoryList.add(new SelectItem("0", "--SELECT--"));
            inventoryList.add(new SelectItem("1", "TRANSFER"));
            inventoryList.add(new SelectItem("2", "RECEIPT"));
            
            branchList = new ArrayList<SelectItem>();
            branchList.add(new SelectItem("ALL", "ALL"));
            
            List alphaCodeList = beanRemote.getAlphacode();
            if (!alphaCodeList.isEmpty()) {
                for (int i = 0; i < alphaCodeList.size(); i++) {
                    Vector vec = (Vector) alphaCodeList.get(i);
                    branchList.add(new SelectItem(vec.get(0).toString(), vec.get(1).toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            flag = "false";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            flag = "false";
        }
    }
    
    public void printReport() {
        try {
            if (inventoryType.equalsIgnoreCase("0")) {
                setMessage(" Error!! Inventory type is not selected");
                flag = "false";
                return;
            }
            if (branch.equalsIgnoreCase("") || branch == null) {
                setMessage("Error!! Branch is not selected");
                flag = "false";
                return;
            }
            if (tillDate == null) {
                setMessage("Error!! Date field is empty");
                flag = "false";
                return;
            }
            flag = "true";
            setMessage("");
            if (branch.length() < 2) {
                branch = "0" + branch;
            }
            List<InventoryTransferReceiptTable> inventoryTransferReceiptList = beanRemote.getInventoryTransferData(inventoryType, branch, orgnBrCode, ymdFormatter.format(tillDate));
            if (inventoryTransferReceiptList.isEmpty()) {
                setMessage("No proper data available!!!");
                flag = "false";
                return;
            }
            List bankdetails = commonFacadeBeanRemote.getBranchNameandAddress(orgnBrCode);
            if (bankdetails.isEmpty()) {
                setMessage("Bank details is not present!!!");
                flag = "false";
                return;
            }
            String bankName = bankdetails.get(0).toString();
            String bankAddress = bankdetails.get(1).toString();
            
            String report = "Transfer/Receipt Report";
            String fdt, tdt;
            Map fillParams = new HashMap();
            
            fillParams.put("pBankName", bankName);
            fillParams.put("pBankAdd", bankAddress);
            fillParams.put("pReportDate", dmyformatter.format(tillDate));
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", userName);
            fillParams.put("pPrintedDate", dmyformatter.format(tillDate));
            new ReportBean().jasperReport("TransferReceiptRegister", "text/html", new JRBeanCollectionDataSource(inventoryTransferReceiptList), fillParams, "Transfer/Receipt Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }
    
    public void pdfDownLoad() {
        try {
            if (inventoryType.equalsIgnoreCase("0")) {
                setMessage(" Error!! Inventory type is not selected");
                flag = "false";
                return;
            }
            
            if (branch.equalsIgnoreCase("") || branch == null) {
                setMessage("Error!! Branch is not selected");
                flag = "false";
                return;
            }
            if (tillDate == null) {
                setMessage("Error!! Date field is empty");
                flag = "false";
                return;
            }
            flag = "true";
            setMessage("");
            if (branch.length() < 2) {
                branch = "0" + branch;
            }
            List<InventoryTransferReceiptTable> inventoryTransferReceiptList = beanRemote.getInventoryTransferData(inventoryType, branch, orgnBrCode, ymdFormatter.format(tillDate));
            if (inventoryTransferReceiptList.isEmpty()) {
                setMessage("No proper data available!!!");
                flag = "false";
                return;
            }
            List bankdetails = commonFacadeBeanRemote.getBranchNameandAddress(orgnBrCode);
            if (bankdetails.isEmpty()) {
                setMessage("Bank details is not present!!!");
                flag = "false";
                return;
            }
            String bankName = bankdetails.get(0).toString();
            String bankAddress = bankdetails.get(1).toString();
            
            String report = "Transfer/Receipt Report";
            String fdt, tdt;
            Map fillParams = new HashMap();
            
            fillParams.put("pBankName", bankName);
            fillParams.put("pBankAdd", bankAddress);
            fillParams.put("pReportDate", dmyformatter.format(tillDate));
            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", userName);
            fillParams.put("pPrintedDate", dmyformatter.format(tillDate));
            new ReportBean().openPdf("Transfer Receipt Report_" + ymdFormatter.format(dmyformatter.parse(getTodayDate())), "TransferReceiptRegister", new JRBeanCollectionDataSource(inventoryTransferReceiptList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Transfer/Receipt Report");
            
        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }
    
    public String exitFrm() {
        return "case1";
    }
    
    public void refreshForm() {
        message = "";
        inventoryType = "0";
        tillDate = new Date();
    }
}
