/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ledger;

import com.cbs.dto.report.LedgerReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LedgerReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
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
 * @author Ankit Verma
 */
public class LedgerReport extends BaseBean {

    private String msg;
    private Date fromDate;
    private Date toDate;
    private Date date = new Date();
    private String brncode;
    private String fromAcNo;
    private String toAcNo;
    private String selectAcType;
    private String branch;
    private List<SelectItem> branchList;
    private List<SelectItem> selectAcTypeList = new ArrayList<SelectItem>();
    private LedgerReportFacadeRemote ledgerReportFacadeLocal;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote RemoteCode;

    /** Creates a new instance of LedgerReport */
    public LedgerReport() {
        try {
            ledgerReportFacadeLocal = (LedgerReportFacadeRemote) ServiceLocator.getInstance().lookup("LedgerReportFacade");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            
            initData();
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }

    }

    private void initData() {
        selectAcTypeList.clear();
        fromDate = date;
        toDate = date;
        List tempList;
        Vector tempVector;
        try {
            tempList = common.getAccTypeExcludePO();
            for (int i = 0; i < tempList.size(); i++) {
                tempVector = (Vector) tempList.get(i);
                selectAcTypeList.add(new SelectItem(tempVector.get(0).toString()));
            }
        } catch (Exception e) {
        }
    }

    public String getSelectAcType() {
        return selectAcType;
    }

    public void setSelectAcType(String selectAcType) {
        this.selectAcType = selectAcType;
    }

    public List<SelectItem> getSelectAcTypeList() {
        return selectAcTypeList;
    }

    public void setSelectAcTypeList(List<SelectItem> selectAcTypeList) {
        this.selectAcTypeList = selectAcTypeList;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getFromAcNo() {
        return fromAcNo;
    }

    public void setFromAcNo(String fromAcNo) {
        this.fromAcNo = fromAcNo;
    }

    public String getToAcNo() {
        return toAcNo;
    }

    public void setToAcNo(String toAcNo) {
        this.toAcNo = toAcNo;
    }

    public String getBrncode() {
        return brncode;
    }

    public void setBrncode(String brncode) {
        this.brncode = brncode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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
    

    public String viewReport() {
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            try {
                msg = "";
                List<LedgerReportPojo> reportList = ledgerReportFacadeLocal.getLedgerReport(selectAcType, fromAcNo, toAcNo, ymd.format(fromDate), ymd.format(toDate), branch);
                if (reportList.size() > 0) {
                    Map fillParams = new HashMap();
                    CommonReportMethodsRemote beanLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List vec = beanLocal.getBranchNameandAddress(getOrgnBrCode());
                    if (vec.size() > 0) {
                        fillParams.put("pReportName", "ACCOUNT STATEMENT");
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pBankName", vec.get(0).toString());
                        fillParams.put("pBankAdd", vec.get(1).toString());
                        fillParams.put("pFromDate", sdf.format(fromDate));
                        fillParams.put("pToDate", sdf.format(toDate));
                        fillParams.put("pAccountType", selectAcType);
                    }
                    new ReportBean().jasperReport("All_Ledger_Account_Statement", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "Ledger Report");
                    return "report";
                } else {
                    setMsg("No details exists !!");
                }
            } catch (Exception e) {
                setMsg(e.getMessage());
            }

        }
        return null;
    }

    public String btnPdfAction() {
        String report = "Ledger Report";
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            try {
                msg = "";
                List<LedgerReportPojo> reportList = ledgerReportFacadeLocal.getLedgerReport(selectAcType, fromAcNo, toAcNo, ymd.format(fromDate), ymd.format(toDate), branch);
                if (reportList.size() > 0) {
                    Map fillParams = new HashMap();
                    CommonReportMethodsRemote beanLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List vec = beanLocal.getBranchNameandAddress(getOrgnBrCode());
                    if (vec.size() > 0) {
                        fillParams.put("pReportName", "ACCOUNT STATEMENT");
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pBankName", vec.get(0).toString());
                        fillParams.put("pBankAdd", vec.get(1).toString());
                        fillParams.put("pFromDate", sdf.format(fromDate));
                        fillParams.put("pToDate", sdf.format(toDate));
                        fillParams.put("pAccountType", selectAcType);
                    }
                    new ReportBean().openPdf("All_Ledger_Account_Statement_"+ymd.format(sdf.parse(getTodayDate())), "All_Ledger_Account_Statement", new JRBeanCollectionDataSource(reportList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpRequest().getSession();
                    httpSession.setAttribute("ReportName", report);
                } else {
                    setMsg("No details exists !!");
                }
            } catch (Exception e) {
                setMsg(e.getMessage());
            }

        }
        return null;
    }

    public void refreshForm() {
        fromAcNo = "";
        toAcNo = "";
        fromDate = date;
        toDate = date;
        msg = "";
    }

    public String exitAction() {
        refreshForm();
        return "case1";
    }

    public String validation() {
        if (fromAcNo == null || fromAcNo.trim().equalsIgnoreCase("")) {
            return "Please insert From Account No.";
        } else if (toAcNo == null || toAcNo.trim().equalsIgnoreCase("")) {
            return "Please insert To Account No.";
        } else if (Integer.parseInt(fromAcNo) > Integer.parseInt(toAcNo)) {
            return "To Account No. must be greater than From Account No.";
        } else if (fromDate == null) {
            return "Please Insert From Date";
        } else if (toDate == null) {
            return "Please Insert To Date";
        } else if (fromDate.after(toDate)) {
            return "To Date must be greater than From Date";
        }
        return "ok";
    }
}
