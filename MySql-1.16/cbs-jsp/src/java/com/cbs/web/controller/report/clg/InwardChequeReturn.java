/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.clg;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.InwardChequeReturnPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.ClgReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
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
public class InwardChequeReturn extends BaseBean {

    private String errorMsg;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private String branchCode;
    private List<SelectItem> branchCodeList;
    //private TdReceiptManagementFacadeRemote RemoteCode;
    private CommonReportMethodsRemote common;
    private ClgReportFacadeRemote beanRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<InwardChequeReturnPojo> replist = new ArrayList<InwardChequeReturnPojo>();

    public InwardChequeReturn() {
        try {
            frmDt = dmy.format(date);
            toDt = dmy.format(date);
            beanRemote = (ClgReportFacadeRemote) ServiceLocator.getInstance().lookup("ClgReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            //RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public void viewReport() {

        String branchName = "";
        String address = "";
        String report = "Inward Cheque Return Report";

        try {
            
             if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill from Date");
                return ;
            }
            if (!Validator.validateDate(frmDt)) {
                setErrorMsg("Please select Proper from date ");
                return ;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill to Date");
                return ;
            }
            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please select Proper to Date");
                return ;
            }

            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getBranchCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            Map fillParams = new HashMap();
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReprtName", report);
            fillParams.put("pReportDate", frmDt + " To " + toDt);
            fillParams.put("pPrintedBy", getUserName());
            String forDate = ymd.format(dmy.parse(frmDt));
            String toDate = ymd.format(dmy.parse(toDt));

            replist = beanRemote.getInwardChequeReturn(forDate, toDate, branchCode);
            if (replist.isEmpty()) {
                errorMsg = "Data does not exit";
                return;
            }
            //     new ReportBean().jasperReport("InwardChequeReturn", "text/html", new JRBeanCollectionDataSource(replist), fillParams, report);
            new ReportBean().jasperReport("InwardChequeReturn", "text/html",
                    new JRBeanCollectionDataSource(replist), fillParams, "Inward Cheque Return Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public FastReportBuilder excelPrint() {
        FastReportBuilder fastReport = new FastReportBuilder();

        try {
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill from Date");
                return null;
            }
            if (!Validator.validateDate(frmDt)) {
                setErrorMsg("Please select Proper from date ");
                return null;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill to Date");
                return null;
            }
            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please select Proper to Date");
                return null;
            }
            String forDate = ymd.format(dmy.parse(frmDt));
            String toDate = ymd.format(dmy.parse(toDt));

            replist = beanRemote.getInwardChequeReturn(forDate, toDate, branchCode);
            if (replist.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }

            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn date = ReportBean.getColumn("date", String.class, "Date", 100);
            AbstractColumn acno = ReportBean.getColumn("acno", String.class, "A/c . Number", 100);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Customer . Name", 200);
            AbstractColumn chequeNo = ReportBean.getColumn("chequeNo", String.class, "Cheque . Number", 100);
            AbstractColumn chequeFavo = ReportBean.getColumn("chequeFavo", String.class, "Cheque . Favouring", 200);

            fastReport.addColumn(date);
            width = width + date.getWidth();

            fastReport.addColumn(acno);
            width = width + acno.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(chequeNo);
            width = width + chequeNo.getWidth();

            fastReport.addColumn(chequeFavo);
            width = width + chequeFavo.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Inward Cheque Return Report");

            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(replist), fastReport, "Inward Cheque Return Report");

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
        return fastReport;
    }

    public void refresh() {
        this.setErrorMsg("");
        this.setFrmDt(getTodayDate());
        this.setToDt(getTodayDate());
    }

    public String exitAction() {
        refresh();
        return "case1";
    }
}
