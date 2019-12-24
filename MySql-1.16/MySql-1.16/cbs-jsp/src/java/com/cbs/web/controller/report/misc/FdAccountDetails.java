/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.FdAccountDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class FdAccountDetails extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String fdAcType;
    private List<SelectItem> fdAcTypeList;
    private String frAmt;
    private String toAmt;
    private String calFromDate;
    private String caltoDate;
    Date dt = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote commonBeanRemote;
    private MiscReportFacadeRemote remoteFacade;
    List<FdAccountDetailPojo> repList = new ArrayList<FdAccountDetailPojo>();

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

    public String getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(String calFromDate) {
        this.calFromDate = calFromDate;
    }

    public String getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(String caltoDate) {
        this.caltoDate = caltoDate;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getFdAcType() {
        return fdAcType;
    }

    public void setFdAcType(String fdAcType) {
        this.fdAcType = fdAcType;
    }

    public List<SelectItem> getFdAcTypeList() {
        return fdAcTypeList;
    }

    public void setFdAcTypeList(List<SelectItem> fdAcTypeList) {
        this.fdAcTypeList = fdAcTypeList;
    }

    public String getFrAmt() {
        return frAmt;
    }

    public void setFrAmt(String frAmt) {
        this.frAmt = frAmt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToAmt() {
        return toAmt;
    }

    public void setToAmt(String toAmt) {
        this.toAmt = toAmt;
    }

    public FdAccountDetails() {
        try {
            setCalFromDate(dmy.format(dt));
            setCaltoDate(dmy.format(dt));
            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");

            List brnLst = new ArrayList();
            brnLst = commonBeanRemote.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            List acCodeList = commonBeanRemote.getFDMSAcTypeList();
            fdAcTypeList = new ArrayList<SelectItem>();
            fdAcTypeList.add(new SelectItem("0", "--Select--"));
            for (Object element : acCodeList) {
                Vector vector = (Vector) element;
                fdAcTypeList.add(new SelectItem(vector.get(0).toString(), "["+vector.get(0).toString()+"] " + vector.get(1).toString()));
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public FastReportBuilder excelPrint() {

        FastReportBuilder fastReport = new FastReportBuilder();
        String fromDate = "";
        String toDate = "";
        try {

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

            if (this.getFdAcType().equalsIgnoreCase("0")) {
                this.setMessage("Please Select Account Type");
                return null;
            }

            Matcher frAmtCheck = p.matcher(this.frAmt);
            if (!frAmtCheck.matches()) {
                this.setMessage("Invalid From Amount Entry.");
                return null;
            }

            Matcher toAmtCheck = p.matcher(this.toAmt);
            if (!toAmtCheck.matches()) {
                this.setMessage("Invalid To Amount Entry.");
                return null;
            }

            if (!Validator.validateDate(calFromDate)) {
                this.setMessage("Please check from date");
                return null;
            }
            if (!Validator.validateDate(caltoDate)) {
                this.setMessage("Please check to date");
                return null;
            }
            if (dmy.parse(calFromDate).after(dmy.parse(caltoDate))) {
                this.setMessage("From date should be less then to date");
                return null;
            }
            if (dmy.parse(caltoDate).after(dt)) {
                this.setMessage("To date should be less or Equal to Current date");
                return null;
            }

            fromDate = ymd.format(dmy.parse(calFromDate));
            toDate = ymd.format(dmy.parse(caltoDate));

            repList = remoteFacade.getFdAccountDetail(fdAcType, frAmt, toAmt, fromDate, toDate, branch);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }

            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account.No", 100);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Customer . Name", 200);
            AbstractColumn fatherName = ReportBean.getColumn("fatherName", String.class, "Father's Name", 200);
            AbstractColumn perAddr = ReportBean.getColumn("perAddr", String.class, "Customer . Address", 400);
            AbstractColumn panNo = ReportBean.getColumn("panNo", String.class, "Pan . No", 80);
            AbstractColumn prnAmt = ReportBean.getColumn("prnAmt", BigDecimal.class, "Face . Value", 150);
            AbstractColumn matAmt = ReportBean.getColumn("matAmt", BigDecimal.class, "Maturity . Amount ", 150);
            AbstractColumn roiAmt = ReportBean.getColumn("roiAmt", BigDecimal.class, "ROI", 150);
            AbstractColumn intAmt = ReportBean.getColumn("intAmt", BigDecimal.class, "Interest . Amount", 150);

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(fatherName);
            width = width + fatherName.getWidth();

            fastReport.addColumn(perAddr);
            width = width + perAddr.getWidth();

            fastReport.addColumn(panNo);
            width = width + panNo.getWidth();

            fastReport.addColumn(prnAmt);
            prnAmt.setStyle(style);
            width = width + 2 * prnAmt.getWidth();

            fastReport.addColumn(matAmt);
            matAmt.setStyle(style);

            fastReport.addColumn(roiAmt);
            roiAmt.setStyle(style);

            fastReport.addColumn(intAmt);
            intAmt.setStyle(style);

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Fd Account Detail Report");

            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(repList), fastReport, "Fd Account Detail Report");


        } catch (Exception e) {
            setMessage(e.getMessage());
        }

        return fastReport;
    }

    public void refreshAction() {
        this.setFdAcType("0");
        setCalFromDate(dmy.format(dt));
        setCaltoDate(dmy.format(dt));
        this.setFrAmt("0");
        this.setToAmt("0");
        this.setMessage("");
    }

    public String exitAction() {
        refreshAction();
        return "case1";
    }
}
