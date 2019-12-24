/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.LoanIntDetailsMonthWisePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.OverDueReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.lang.reflect.Array;
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
 * @author SAMY
 */
public class LoanIntDetailMonthWise extends BaseBean {

    private String userName;
    private String errorMsg;
    private String acctType;
    private String frmDt;
    private String toDt;
    private LoanReportFacadeRemote local;
    private CommonReportMethodsRemote common;
    private OverDueReportFacadeRemote overDue;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<SelectItem> acctTypeList;
    private Date date = new Date();
    List tempList = null;
    Vector tempVector = null;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String schemeType;
    private List<SelectItem> schemeTypeList;

    public LoanIntDetailMonthWise() {
        try {
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            overDue = (OverDueReportFacadeRemote) ServiceLocator.getInstance().lookup("OverDueReportFacade");

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
            acctTypeList = new ArrayList<SelectItem>();
            tempList = local.getLoanTypeList();
            for (int i = 0; i < tempList.size(); i++) {
                tempVector = (Vector) tempList.get(i);
                acctTypeList.add(new SelectItem(tempVector.get(0), tempVector.get(0).toString()));
            }
            schemeTypeList = new ArrayList<>();
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }
    public void onBlurAcType () {
        this.setErrorMsg("");
        schemeTypeList = new ArrayList<>();
        try {
            if (this.acctType.equalsIgnoreCase("ALL")) {
                schemeTypeList.add(new SelectItem("ALL", "ALL"));
            } else {
                List schemeList = common.getAcctTypeSchemeWise(this.acctType);
                schemeTypeList.add(new SelectItem("ALL", "ALL"));
                for (int t=0;t<schemeList.size();t++) {
                    Vector Vect = (Vector) schemeList.get(t);
                    schemeTypeList.add(new SelectItem(Vect.get(0).toString(), Vect.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            setErrorMsg(ex.getLocalizedMessage());
        }
    }

    public String viewReport() throws ApplicationException {
        if (validate()) {
            try {
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                List<LoanIntDetailsMonthWisePojo> resultList = overDue.getAccountDetailsMonthWise(branchCode, acctType, (fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]),schemeType);
                if (!resultList.isEmpty()) {
                    String report = "Loan Interest Recovery Report Month Wise";
                    Map fillParams = new HashMap();
                    String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    fillParams.put("pReportName", report);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDate", toDt);
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    fillParams.put("pFROM", frmDt);
                    fillParams.put("pTo", toDt);
                    new ReportBean().jasperReport("Loan_Interest_Recovery", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
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

    public String viewPdfReport() throws ApplicationException {
        if (validate()) {
            try {
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                List<LoanIntDetailsMonthWisePojo> resultList = overDue.getAccountDetailsMonthWise(branchCode, acctType, (fromDt[2] + fromDt[1] + fromDt[0]), (toDate[2] + toDate[1] + toDate[0]),schemeType);
                if (!resultList.isEmpty()) {
                    String report = "Loan Interest Recovery Report Month Wise";
                    Map fillParams = new HashMap();
                    String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    fillParams.put("pReportName", report);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportDate", toDt);
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);
                    fillParams.put("pFROM", frmDt);
                    fillParams.put("pTo", toDt);
                    new ReportBean().openPdf("Loan Interest Recovery Report Month Wise_" + ymd.format(dmy.parse(getTodayDate())), "Loan_Interest_Recovery", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getRequest().getSession();
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

    public boolean validate() {
        try {
            if (!Validator.validateDate(toDt)) {
                errorMsg = "Please check to date";
                return false;
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        return true;
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

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
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

    public String getSchemeType() {
        return schemeType;
    }
    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }
    public List<SelectItem> getSchemeTypeList() {
        return schemeTypeList;
    }
    public void setSchemeTypeList(List<SelectItem> schemeTypeList) {
        this.schemeTypeList = schemeTypeList;
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

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
}
