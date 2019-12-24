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
import com.cbs.dto.report.DemandDraftPayOrderPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.ClgReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
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
public class DemandDraftPayOrder extends BaseBean {

    private String errorMsg;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String modeType;
    private List<SelectItem> modeTypeList;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private TdReceiptManagementFacadeRemote RemoteCode;
    private CommonReportMethodsRemote common;
    private ClgReportFacadeRemote beanRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<DemandDraftPayOrderPojo> repList = new ArrayList<DemandDraftPayOrderPojo>();

    public DemandDraftPayOrder() {
        try {
            frmDt = dmy.format(date);
            toDt = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            beanRemote = (ClgReportFacadeRemote) ServiceLocator.getInstance().lookup("ClgReportFacade");

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            modeTypeList = new ArrayList<SelectItem>();
            modeTypeList.add(new SelectItem("ALL", "ALL"));
            modeTypeList.add(new SelectItem("0", "Cash"));
            modeTypeList.add(new SelectItem("1", "Clearing"));
            modeTypeList.add(new SelectItem("2", "Transfer"));      
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
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

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public List<SelectItem> getModeTypeList() {
        return modeTypeList;
    }

    public void setModeTypeList(List<SelectItem> modeTypeList) {
        this.modeTypeList = modeTypeList;
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
        String report = "Demand Draft/Payorder Detail Report";
        try {

            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill from Date");
                return;
            }
            if (!Validator.validateDate(frmDt)) {
                setErrorMsg("Please select Proper from date ");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill to Date");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setErrorMsg("Please select Proper to Date");
                return;
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
            fillParams.put("pModeType", modeType.equalsIgnoreCase("0") ? "CASH" : modeType.equalsIgnoreCase("1") ? "CLEARING" : modeType.equalsIgnoreCase("2") ? "TRANSFER" : "ALL");
            String forDate = ymd.format(dmy.parse(frmDt));
            String toDate = ymd.format(dmy.parse(toDt));

            repList = beanRemote.getDemandDraftPayOrderDetail(forDate, toDate, branchCode, modeType);
            if (repList.isEmpty()) {
                errorMsg = "Data does not exit";
                return;
            }
            new ReportBean().jasperReport("DemandDraftPoOrder", "text/html",
                    new JRBeanCollectionDataSource(repList), fillParams, "Demand Draft/Payorder Detail Report");
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

            repList = beanRemote.getDemandDraftPayOrderDetail(forDate, toDate, branchCode, modeType);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }

            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn date = ReportBean.getColumn("date", String.class, "Date", 100);
            AbstractColumn ddNoPoNo = ReportBean.getColumn("ddNoPoNo", String.class, "Demand Draft No. / Pay Order No.", 200);
            AbstractColumn chequeFavo = ReportBean.getColumn("chequeFavo", String.class, "In Favour Of", 200);
            AbstractColumn amount = ReportBean.getColumn("amount", BigDecimal.class, "Amount", 100);
            AbstractColumn payable = ReportBean.getColumn("payable", String.class, "Payable At", 200);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Customer . Name", 200);
            //AbstractColumn address = ReportBean.getColumn("address", null, "Customer Address", 200);
            AbstractColumn mode = ReportBean.getColumn("mode", String.class, "Mode . Type", 200);

            fastReport.addColumn(date);
            width = width + date.getWidth();

            fastReport.addColumn(ddNoPoNo);
            width = width + ddNoPoNo.getWidth();

            fastReport.addColumn(chequeFavo);
            width = width + chequeFavo.getWidth();

            fastReport.addColumn(amount);
            amount.setStyle(style);
            width = width + 2 * amount.getWidth();

            fastReport.addColumn(payable);
            width = width + payable.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

//            fastReport.addColumn(address);
//            width = width + address.getWidth();

            fastReport.addColumn(mode);
            width = width + mode.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Demand Draft/Pay Order Detail Report");

            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(repList), fastReport, "Demand Draft/Pay Order Detail Report");

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }

        return null;
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
