/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.ExceptionReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.io.Serializable;
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
public class ExceptionTransaction extends BaseBean implements Serializable {

    private String errorMsg;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private Date date = new Date();
    private Date toDt;
    private OtherReportFacadeRemote local;
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private String branch;
    private List<SelectItem> branchList;

    /** Creates a new instance of ExceptionTransaction */
    public ExceptionTransaction() {
        try {
            toDt = date;
            local = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
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

    public boolean validate() {
        if (toDt == null) {
            errorMsg = "Please enter to date";
            return false;
        }
        return true;
    }

    public String viewReport() {
        if (validate()) {
            try {
                List<ExceptionReportPojo> resultList = local.exceptionTrnasctionReport(ymd.format(toDt), branch);
                if (!resultList.isEmpty()) {
                    String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    String report = "Exception Transaction Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", report);
                    fillParams.put("pReportDate", dmy.format(toDt));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);

                    new ReportBean().jasperReport("CBS_REP_EXCEPTION_TRANSACTION_REPORT", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                    return "report";
                } else {
                    errorMsg = "Data does not lie";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String pdfReport() {
        if (validate()) {
            try {
                List<ExceptionReportPojo> resultList = local.exceptionTrnasctionReport(ymd.format(toDt), branch);
                if (!resultList.isEmpty()) {
                    String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    String report = "Exception Transaction Report";
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", report);
                    fillParams.put("pReportDate", dmy.format(toDt));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pbankName", s[0]);
                    fillParams.put("pbankAddress", s[1]);

                    new ReportBean().openPdf("EXCEPTION_TRANSACTION_REPORT_" + ymd.format(dmy.parse(getTodayDate())), "CBS_REP_EXCEPTION_TRANSACTION_REPORT", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpRequest().getSession();
                    httpSession.setAttribute("ReportName", report);
//                    new ReportBean().jasperReport("CBS_REP_EXCEPTION_TRANSACTION_REPORT", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                    return "report";
                } else {
                    errorMsg = "Data does not lie";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    } 
    
    public String exitAction() {
        return "case1";
    }
}
