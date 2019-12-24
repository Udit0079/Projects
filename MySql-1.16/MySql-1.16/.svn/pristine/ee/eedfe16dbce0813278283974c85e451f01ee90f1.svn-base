/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.NpaReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.SortSummaryByNPA;
import com.cbs.pojo.SortedByProductCode;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author root
 */
public class NpaSummary extends BaseBean {

    private String errorMsg;
    private String message;
    private String toDt;
    Date dt = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeName = "NpaReportFacade";
    private NpaReportFacadeRemote remote = null;
    private final String jndiName = "CommonReportMethods";
    private CommonReportMethodsRemote common = null;
    private String branchCode;
    private List<SelectItem> branchCodeList;

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    /**
     * Creates a new instance of NpaSummary
     */
    public NpaSummary() {
        try {
            remote = (NpaReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnViewReport() {
        this.setMessage("");
        String bankName = "", bankAddress = "";
        try {
            toDt = ymd.format(dmy.parse(toDt));
            List resultList = remote.getNpaSummary(this.branchCode, toDt);
            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortedByProductCode());
            chObj.addComparator(new SortSummaryByNPA());
            Collections.sort(resultList, chObj);
            //Collections.sort(resultList, new SortSummaryByNPA());

            if (!resultList.isEmpty()) {
                String reportName = "NPA LEGAL ACTION REPORT";
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
                fillParams.put("pPrintedDate", dmy.format(ymd.parse(getToDt())));

                new ReportBean().jasperReport("npaSummary1", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
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
            toDt = ymd.format(dmy.parse(toDt));
            List resultList = remote.getNpaSummary(this.branchCode, toDt);
            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortedByProductCode());
            chObj.addComparator(new SortSummaryByNPA());
            Collections.sort(resultList, chObj);
            //Collections.sort(resultList, new SortSummaryByNPA());

            if (!resultList.isEmpty()) {
                String reportName = "NPA LEGAL ACTION REPORT";
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
                fillParams.put("pPrintedDate", dmy.format(ymd.parse(getToDt())));

                new ReportBean().openPdf("NPA LEGAL ACTION REPORT ", "npaSummary1", new JRBeanCollectionDataSource(resultList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", reportName);
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setErrorMsg("");

    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
