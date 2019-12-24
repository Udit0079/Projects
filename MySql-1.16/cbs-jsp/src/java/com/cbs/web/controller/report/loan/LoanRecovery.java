/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.LoanRecoveryTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
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
 * @author admin
 */
public class LoanRecovery extends BaseBean {

    private String userName;
    private String errorMsg;
    private String acctType;
    private String frmDt;
    private String toDt;
    private LoanReportFacadeRemote local;
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<SelectItem> acctTypeList;
    private Date date = new Date();
    List tempList = null;
    Vector tempVector = null;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String acNature;
    private List<SelectItem> acNatureList;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    /**
     * Creates a new instance of LoanRecovery
     */
    public LoanRecovery() {
        try {
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            frmDt = dmy.format(date);
            toDt = dmy.format(date);

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
         
            reportTypeList = new ArrayList();
            reportTypeList.add(new SelectItem("0", "ALL"));
            reportTypeList.add(new SelectItem("1", "NPA"));
            reportTypeList.add(new SelectItem("2", "Without NPA"));

            initData();
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    private void initData() {
        try {
            acNatureList = new ArrayList<SelectItem>();
            tempList = common.getAcctNatureOnlyDB();
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    acNatureList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                }
            }
         

        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

  public  void blurAcctNature(){
        try{
               acctTypeList = new ArrayList();
           tempList = common.getAcctTypeAsAcNatureOnlyDB(acNature);
            if (!tempList.isEmpty()) {
                acctTypeList.add(new SelectItem("ALL", "ALL"));
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    acctTypeList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                }
            }
        }catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }    
    }
    
    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public boolean validate() {
        try {
            if (!Validator.validateDate(frmDt)) {
                errorMsg = "Please check from date";
                return false;
            }
            if (!Validator.validateDate(frmDt)) {
                errorMsg = "Please check to date";
                return false;
            }
            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                errorMsg = "From date should be less then to date";
                return false;
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        return true;
    }

    public String viewReport() throws ApplicationException {
        if (validate()) {
            try {
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                List<LoanRecoveryTable> resultList = local.loanRecoveryReport(acctType, (fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]), branchCode, this.reportType,this.acNature);
                if (!resultList.isEmpty()) {
                    String report = "Loan Description Report";
                    Map fillParams = new HashMap();
                    String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    fillParams.put("pReportName", report);
                    fillParams.put("pPrintedBy", userName);
                    fillParams.put("pReportDate", frmDt + " to " + toDt);
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    fillParams.put("pFROM", frmDt);
                    fillParams.put("pTo", toDt);
                    new ReportBean().jasperReport("LoanRecoveryReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                    return "report";
                } else {
                    errorMsg = "No data exists !";
                    return null;
                }
            } catch (Exception e) {
                setErrorMsg(e.getLocalizedMessage());
            }
        }
        return null;
    }

    public String pdfDownLoad() {
        if (validate()) {
            try {
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                List<LoanRecoveryTable> resultList = local.loanRecoveryReport(acctType, (fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]), branchCode, this.reportType,this.acNature);
                if (!resultList.isEmpty()) {
                    String report = "Loan Description Report";
                    Map fillParams = new HashMap();
                    String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    fillParams.put("pReportName", report);
                    fillParams.put("pPrintedBy", userName);
                    fillParams.put("pReportDate", frmDt + " to " + toDt);
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    fillParams.put("pFROM", frmDt);
                    fillParams.put("pTo", toDt);
                    new ReportBean().openPdf("Loan Description Report_" + (fromDt[2] + fromDt[1] + fromDt[0]) + " to " + (toDate[2] + toDate[1] + toDate[0]), "LoanRecoveryReport", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", report);
                } else {
                    errorMsg = "No data exists !";
                    return null;
                }
            } catch (Exception e) {
                setErrorMsg(e.getLocalizedMessage());
            }
        }
        return null;
    }

    public void refresh() {
        try {
            errorMsg = "";
            frmDt = dmy.format(new Date());
            toDt = dmy.format(new Date());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        try {
            refresh();
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        return "case1";
    }
}
