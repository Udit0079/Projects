/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ledger;

import com.cbs.dto.report.CashierCashPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LedgerReportFacadeRemote;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class CashierCash extends BaseBean {

    private String message;
    private String enterBy;
    private List<SelectItem> enterByList;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String asOnDt;
    private String type;
    private List<SelectItem> typeList;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private LedgerReportFacadeRemote ledgerReport;
    private TdReceiptManagementFacadeRemote RemoteCode;
    List<CashierCashPojo> reportList = new ArrayList<CashierCashPojo>();

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

    public String getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(String asOnDt) {
        this.asOnDt = asOnDt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public List<SelectItem> getEnterByList() {
        return enterByList;
    }

    public void setEnterByList(List<SelectItem> enterByList) {
        this.enterByList = enterByList;
    }

    public CashierCash() {
        try {
            setAsOnDt(dmy.format(date));
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ledgerReport = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup("LedgerReportFacade");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("Select", "--Select--"));
            typeList.add(new SelectItem("0", "Receipt"));
            typeList.add(new SelectItem("1", "Payment"));

            enterByList = new ArrayList<SelectItem>();
            enterByList.add(new SelectItem("ALL", "ALL"));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getEnterByData() {
        try {
            String asOnDate = ymd.format(dmy.parse(asOnDt));
            List userId = common.getUserId(branchCode, asOnDate, type);
            enterByList = new ArrayList();
            enterByList.add(new SelectItem("ALL", "ALL"));
            for (Object element : userId) {
                Vector vector = (Vector) element;
                enterByList.add(new SelectItem(vector.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewReport() {
        this.setMessage("");
        String report = "Cashier Receipt Report";
        String report1 = " Cashier Payment Report";
        String tPaid = "Paid By", tReceipt = "Receipt By";
        String branchName = "", address = "";
        try {
            if (asOnDt == null || asOnDt.equalsIgnoreCase("")) {
                setMessage("Please fill As On Date!!!");
                return;
            }
            if (!Validator.validateDate(asOnDt)) {
                setMessage("Please select Proper from date ");
                return;
            }
            if (type.equalsIgnoreCase("Select")) {
                setMessage("Please Select Type!!!");
                return;
            }
            List bnkAddList = new ArrayList();
            bnkAddList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!bnkAddList.isEmpty()) {
                branchName = (String) bnkAddList.get(0);
                address = (String) bnkAddList.get(1);
            }
            Map fillParams = new HashMap();
            if (type.equalsIgnoreCase("0")) {
                fillParams.put("pCPaidReceiv", tReceipt);
            } else {
                fillParams.put("pCPaidReceiv", tPaid);
            }
            fillParams.put("pCPaidRcvVal", this.enterBy);
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReportDate", this.asOnDt);

            if (type.equalsIgnoreCase("0")) {
                fillParams.put("pReportName", report);
            } else {
                fillParams.put("pReportName", report1);
            }
            fillParams.put("pPrintedBy", getUserName());
            String asOnDate = ymd.format(dmy.parse(asOnDt));

            reportList = ledgerReport.getReceivePaymentData(enterBy, asOnDate, type, branchCode);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist!!!");
            }
            new ReportBean().jasperReport("CashierReceiptPayment", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Cashier Receipt Payment");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        this.setMessage("");
        String report = "Cashier Receipt Report";
        String report1 = " Cashier Payment Report";
        String tPaid = "Paid By", tReceipt = "Receipt By";
        String branchName = "", address = "";
        try {
            if (asOnDt == null || asOnDt.equalsIgnoreCase("")) {
                setMessage("Please fill As On Date!!!");
                return;
            }
            if (!Validator.validateDate(asOnDt)) {
                setMessage("Please select Proper from date ");
                return;
            }
            if (type.equalsIgnoreCase("Select")) {
                setMessage("Please Select Type!!!");
                return;
            }
            List bnkAddList = new ArrayList();
            bnkAddList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!bnkAddList.isEmpty()) {
                branchName = (String) bnkAddList.get(0);
                address = (String) bnkAddList.get(1);
            }
            Map fillParams = new HashMap();
            if (type.equalsIgnoreCase("0")) {
                fillParams.put("pCPaidReceiv", tReceipt);
            } else {
                fillParams.put("pCPaidReceiv", tPaid);
            }
            fillParams.put("pCPaidRcvVal", this.enterBy);
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReportDate", this.asOnDt);

            if (type.equalsIgnoreCase("0")) {
                fillParams.put("pReportName", report);
            } else {
                fillParams.put("pReportName", report1);
            }
            fillParams.put("pPrintedBy", getUserName());
            String asOnDate = ymd.format(dmy.parse(asOnDt));

            reportList = ledgerReport.getReceivePaymentData(enterBy, asOnDate, type, branchCode);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist!!!");
            }
            new ReportBean().openPdf("Cashier Receipt Payment_"+asOnDate, "CashierReceiptPayment", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Cashier Receipt Payment");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        setAsOnDt(dmy.format(date));
        setType("Select");
        setBranchCode("ALL");
        setEnterBy("ALL");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
