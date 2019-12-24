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
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.ClgReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.CtsinwardinfoPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class CtsInwardInfo extends BaseBean {

    String message;
    private String toDt;
    private Date date = new Date();
    private String branchCode;
    private List<SelectItem> branchCodeList;
    CommonReportMethodsRemote RemoteCode;
    private ClgReportFacadeRemote beanRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<CtsinwardinfoPojo> repotList = new ArrayList<CtsinwardinfoPojo>();

    public CtsInwardInfo() {
        try {
            this.setToDt(dmy.format(date));
            beanRemote = (ClgReportFacadeRemote) ServiceLocator.getInstance().lookup("ClgReportFacade");
            RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List branchCode = new ArrayList();
            branchCode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            // branchCodeList.add(new SelectItem("ALL"));
            if (!branchCode.isEmpty()) {
                for (int i = 0; i < branchCode.size(); i++) {
                    Vector brnVector = (Vector) branchCode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public CommonReportMethodsRemote getRemoteCode() {
        return RemoteCode;
    }

    public void setRemoteCode(CommonReportMethodsRemote RemoteCode) {
        this.RemoteCode = RemoteCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FastReportBuilder excelPrint() {
        setMessage("");
        FastReportBuilder fastReport = new FastReportBuilder();
        try {

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill date !");
                return null;
            }

            if (dmy.parse(toDt).after(ymd.parse(ymd.format(date)))) {
                setMessage("To date should be less than current date !");
                return null;
            }
            repotList = beanRemote.getctsinwardinfoData(branchCode, ymd.format(dmy.parse(toDt)));

            if (repotList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn srNO = ReportBean.getColumn("srNo", Integer.class, "SrNo", 100);
            AbstractColumn presentingBankCode = ReportBean.getColumn("presentingBankCode", String.class, "Presenting Bank Code", 200);
            AbstractColumn draweeBankCode = ReportBean.getColumn("draweeBankCode", String.class, "Drawee Bank Code", 150);
            AbstractColumn clearingDate = ReportBean.getColumn("clearingDate", String.class, "Clearing Date", 90);
            AbstractColumn chqNo = ReportBean.getColumn("chqNo", String.class, "Chq No", 150);
            AbstractColumn amount = ReportBean.getColumn("amount", Double.class, "Amount", 150);
            AbstractColumn sequenceNo = ReportBean.getColumn("sequenceNo", String.class, "Sequence No", 150);
            AbstractColumn tC = ReportBean.getColumn("tC", String.class, "Tc", 150);

            fastReport.addColumn(srNO);
            srNO.setStyle(style);
            width = width + srNO.getWidth();

            fastReport.addColumn(presentingBankCode);
            presentingBankCode.setStyle(style);
            width = width + presentingBankCode.getWidth();

            fastReport.addColumn(draweeBankCode);
            draweeBankCode.setStyle(style);
            width = width + draweeBankCode.getWidth();

            fastReport.addColumn(clearingDate);
            clearingDate.setStyle(style);
            width = width + clearingDate.getWidth();

            fastReport.addColumn(chqNo);
            chqNo.setStyle(style);
            width = width + chqNo.getWidth();

            fastReport.addColumn(amount);
            amount.setStyle(style);
            width = width + amount.getWidth();

            fastReport.addColumn(sequenceNo);
            sequenceNo.setStyle(style);
            width = width + sequenceNo.getWidth();

            fastReport.addColumn(tC);
            tC.setStyle(style);
            width = width + tC.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Cts Inward Report");

            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(repotList), fastReport, "Cts Inward Report_" + ymd.format(dmy.parse(toDt)));
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return fastReport;
    }

    public void btnRefreshAction() {
        this.message = "";
        this.setToDt(dmy.format(date));
    }

    public String btnExitAction() {
        return "case1";
    }
}
