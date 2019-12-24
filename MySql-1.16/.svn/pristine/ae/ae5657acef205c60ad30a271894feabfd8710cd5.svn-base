        /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.PendingChargesReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
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
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class PendingChargesReport extends BaseBean {

    private String message;
    private String branch;
    private String tranType;
    private List<SelectItem> tranTypeList;
    private List<SelectItem> branchTypeList;
    private String fromDate;
    private String toDate;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private MiscReportFacadeRemote remoteFacade;
    private List<PendingChargesReportPojo> resultList;

    public PendingChargesReport() {
        try {
            setFromDate(dmy.format(date));
            setToDate(dmy.format(date));
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchTypeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchTypeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            tranTypeList = new ArrayList<SelectItem>();
            tranTypeList.add(new SelectItem("--Select--"));
            tranTypeList.add(new SelectItem("All", "ALL"));
            tranTypeList.add(new SelectItem("Y", "Recover"));
            tranTypeList.add(new SelectItem("N", "UnRecover"));


        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public List<SelectItem> getTranTypeList() {
        return tranTypeList;
    }

    public void setTranTypeList(List<SelectItem> tranTypeList) {
        this.tranTypeList = tranTypeList;
    }

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

    public List<SelectItem> getBranchTypeList() {
        return branchTypeList;
    }

    public void setBranchTypeList(List<SelectItem> branchTypeList) {
        this.branchTypeList = branchTypeList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void btnHtmlAction() throws ApplicationException {
        this.setMessage("");
        try {

            if (branch == null || branch.equalsIgnoreCase("")) {
                this.setMessage("Please Select branch");

            }

            if (tranType == null || tranType.equalsIgnoreCase("")) {
                this.setMessage("Please Select tranType Category");
                return;

            }
            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
            }

            resultList = remoteFacade.getPendingChargesData(branch, tranType, ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)));
            String pReportName = "", pReportDate = "", pPrintedBy = "", pbankName = "";

            if (!resultList.isEmpty()) {
                pReportName = "Pending Charges Report";
                List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
                pbankName = common.getBankName();
                pReportDate = getTodayDate();
                pPrintedBy = getUserName();
                Map fillParams = new HashMap();

                fillParams.put("pReportName", pReportName);
                fillParams.put("pReportDt", pReportDate);
                fillParams.put("pPrintedBy", pPrintedBy);
                fillParams.put("pBankName", branchNameandAddress.get(0));
                fillParams.put("pBankAddress", branchNameandAddress.get(1));

                new ReportBean().jasperReport("Pending Charges Report", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Pending Charges Report");
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", "Pending Charges Report");
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }

    }

    public void btnPdfDownload() {
        this.setMessage("");
        try {

            if (branch == null || branch.equalsIgnoreCase("")) {
                this.setMessage("Please Select branch");

            }

            if (tranType == null || tranType.equalsIgnoreCase("")) {
                this.setMessage("Please Select tranType Category");
                return;

            }
            if (this.fromDate == null || this.fromDate.equals("")) {
                this.setMessage("Please Fill From date !");
                return;
            }
            if (this.toDate == null || this.toDate.equals("")) {
                this.setMessage("Please Fill To Date !");
            }
            resultList = remoteFacade.getPendingChargesData(branch, tranType, ymd.format(dmy.parse(this.fromDate)), ymd.format(dmy.parse(this.toDate)));
            String pReportName = "", pReportDate = "", pPrintedBy = "", pbankName = "";

            if (!resultList.isEmpty()) {
                pReportName = "Pending Charges Report";
                List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
                pbankName = common.getBankName();
                pReportDate = getTodayDate();
                pPrintedBy = getUserName();

                Map fillParams = new HashMap();
                fillParams.put("pReportName", pReportName);
                fillParams.put("pReportDt", pReportDate);
                fillParams.put("pPrintedBy", pPrintedBy);
                fillParams.put("pBankName", branchNameandAddress.get(0));
                fillParams.put("pBankAddress", branchNameandAddress.get(1));

                new ReportBean().openPdf("Pending Charges Report", "Pending Charges Report", new JRBeanCollectionDataSource(resultList), fillParams);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", "Pending Charges Report");
            } else {
                this.setMessage("There is no data to print !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setBranch("");
        this.setTranType("");
        setFromDate(dmy.format(date));
        setToDate(dmy.format(date));

    }

    public String btnExitAction() {
        return "case1";
    }
}
