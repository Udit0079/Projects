/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.TdActiveReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
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
 * @author root
 */
public class DuplicateReceiptReport extends BaseBean {

    public String message;
    private String branch;
    private String frDt;
    private String toDt;
    private List<SelectItem> branchList;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    Date dt = new Date();
    private OtherReportFacadeRemote facadeRemote;
    private CommonReportMethodsRemote commonBeanRemote;

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

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public DuplicateReceiptReport() {
        try {
            facadeRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            setFrDt(dmyFormat.format(dt));
            setToDt(dmyFormat.format(dt));
            setMessage("");
            List brnLst = new ArrayList();
            brnLst = commonBeanRemote.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele.get(0).toString().length() < 2 ? "0" + ele.get(0).toString() : ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }

    }

    public boolean validate() {
        try {
            if (!Validator.validateDate(frDt)) {
                this.setMessage("Please check from date");
                return false;
            }
            if (!Validator.validateDate(frDt)) {
                this.setMessage("Please check to date");
                return false;
            }
            if (dmyFormat.parse(frDt).after(dmyFormat.parse(toDt))) {
                this.setMessage("From date should be less then to date");
                return false;
            }
            if (dmyFormat.parse(toDt).after(dt)) {
                this.setMessage("To date should be less or Equal to Current date");
                return false;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return true;
    }

    public void printAction() {
        try {
            if (validate()) {
                String bankName = "", bankAddress = "";
                List<TdActiveReportPojo> detailList = facadeRemote.getDuplicateReceiptDetails(branch, ymdFormat.format(dmyFormat.parse(frDt)), ymdFormat.format(dmyFormat.parse(toDt)));
                if (!detailList.isEmpty()) {
                    List dataList1 = commonBeanRemote.getBranchNameandAddress(getOrgnBrCode());
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                    String repName = "DUPLICATE RECEIPT REPORT";
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", repName);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pFrDate", this.getFrDt());
                    fillParams.put("pToDate", this.getToDt());
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", bankAddress);
                    new ReportBean().jasperReport("duplicate_receipt_report", "text/html", new JRBeanCollectionDataSource(detailList), fillParams, "DUPLICATE RECEIPT REPORT");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", repName);
                } else {
                    setMessage("No data to print");
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void pdfDownLoad() {
        try {
            if (validate()) {
                String bankName = "", bankAddress = "";
                List<TdActiveReportPojo> detailList = facadeRemote.getDuplicateReceiptDetails(branch, ymdFormat.format(dmyFormat.parse(frDt)), ymdFormat.format(dmyFormat.parse(toDt)));
                if (!detailList.isEmpty()) {
                    List dataList1 = commonBeanRemote.getBranchNameandAddress(getOrgnBrCode());
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                    String repName = "DUPLICATE RECEIPT REPORT";
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", repName);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pFrDate", this.getFrDt());
                    fillParams.put("pToDate", this.getToDt());
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", bankAddress);
                    new ReportBean().openPdf("DUPLICATE RECEIPT REPORT_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "duplicate_receipt_report", new JRBeanCollectionDataSource(detailList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", repName);
                } else {
                    setMessage("No data to print");
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshAction() {
        this.setMessage("");
        setFrDt(dmyFormat.format(dt));
        setToDt(dmyFormat.format(dt));
    }

    public String exitAction() {
        refreshAction();
        return "case1";
    }
}
