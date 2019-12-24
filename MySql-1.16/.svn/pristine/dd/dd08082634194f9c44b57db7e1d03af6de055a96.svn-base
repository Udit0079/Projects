/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.InterestFdDepositesRepPojo;
import com.cbs.facade.report.OtherReportFacadeRemote;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class TimeDepositExceeding extends BaseBean {

    private String message;
    private String branch;
    private String amt;
    private List<SelectItem> branchList;
    private String ddAcType;
    List<SelectItem> ddAcTypeList = new ArrayList<SelectItem>();
    private String calFromDate;
    private String caltoDate;
    Date dt = new Date();
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private OtherReportFacadeRemote facadeRemote;
    private CommonReportMethodsRemote commonBeanRemote;
    private TdReceiptManagementFacadeRemote tdFacade;

    public String getDdAcType() {
        return ddAcType;
    }

    public void setDdAcType(String ddAcType) {
        this.ddAcType = ddAcType;
    }

    public List<SelectItem> getDdAcTypeList() {
        return ddAcTypeList;
    }

    public void setDdAcTypeList(List<SelectItem> ddAcTypeList) {
        this.ddAcTypeList = ddAcTypeList;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TimeDepositExceeding() {
        try {

            facadeRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            tdFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            setCalFromDate(dmyFormat.format(dt));
            setCaltoDate(dmyFormat.format(dt));
            setMessage("");

            List brnLst = new ArrayList();
            brnLst = tdFacade.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            List acCodeList = commonBeanRemote.getAccTypeIncludeRDFDMS();
            ddAcTypeList = new ArrayList<SelectItem>();
            ddAcTypeList.add(new SelectItem("0", "--Select--"));
            for (Object element : acCodeList) {
                Vector vector = (Vector) element;
                ddAcTypeList.add(new SelectItem(vector.get(0).toString(), vector.get(0).toString()));
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public boolean validate() {
        try {

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher frAmtCheck = p.matcher(this.amt);
            if (!frAmtCheck.matches()) {
                this.setMessage("Invalid  Amount Entry.");
                return false;
            }
            if (!Validator.validateDate(calFromDate)) {
                this.setMessage("Please check from date");
                return false;
            }
            if (!Validator.validateDate(caltoDate)) {
                this.setMessage("Please check to date");
                return false;
            }
            if (dmyFormat.parse(calFromDate).after(dmyFormat.parse(caltoDate))) {
                this.setMessage("From date should be less then to date");
                return false;
            }
            if (dmyFormat.parse(caltoDate).after(dt)) {
                this.setMessage("To date should be less or Equal to Current date");
                return false;
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return true;
    }

    public void printAction() {
        try {
            if (validate()) {
                String acCode = this.getDdAcType();
                List<InterestFdDepositesRepPojo> interestRepList = facadeRemote.interestFdDepositesReport(acCode, amt, ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), this.branch);

                FastReportBuilder fastReportBuilder = genrateDaynamicReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(interestRepList), fastReportBuilder, "Time Deposit Exceeding report");

            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public FastReportBuilder genrateDaynamicReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);


            AbstractColumn jCnt = ReportBean.getColumn("jCnt", String.class, "Joint Transaction Partyaccount (Depositer)", 100);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Name of the Transaction Party (Depositer)", 200);
            AbstractColumn pType = ReportBean.getColumn("pType", String.class, "Whether Govt./Non-Govt.", 200);
            AbstractColumn pStatus = ReportBean.getColumn("pStatus", String.class, "status of the transacting Party (Depositer)", 200);
            AbstractColumn panNo = ReportBean.getColumn("panNo", String.class, "PAN of Transacting Party (Depositer)", 80);
            AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Date of Birth/Date of Incorporation of the Transacting Party (Depositer)", 60);
            AbstractColumn fatherName = ReportBean.getColumn("fatherName", String.class, "Name of Father of the Transacting Party (Depositer)", 200);
            AbstractColumn perAddr = ReportBean.getColumn("perAddr", String.class, "Address of transacting Party (Transfer/Seller)-Flat No./House No/Premises No.Bulding Name/FloorNo./Block/Sector/Road/Street/Locality/Colony/City/District/State/UT/Pin Code", 400);
            AbstractColumn prnAmt = ReportBean.getColumn("prnAmt", BigDecimal.class, "Transaction Amount (Deposit Amount)", 150);
            AbstractColumn pMode = ReportBean.getColumn("pMode", String.class, "Payment Mode(cash/cheque/transfer/reinvestment)", 100);
            AbstractColumn depDate = ReportBean.getColumn("depDate", String.class, "Date of Deposit", 60);
            AbstractColumn matDate = ReportBean.getColumn("matDate", String.class, "Date of Maturity", 60);
            AbstractColumn roi = ReportBean.getColumn("roi", String.class, "Rate of Interest", 150);
            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Deposit Account No.or Customer ID No.", 100);


            fastReport.addColumn(jCnt);
            width = width + jCnt.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(pType);
            width = width + pType.getWidth();

            fastReport.addColumn(pStatus);
            width = width + pStatus.getWidth();

            fastReport.addColumn(panNo);
            width = width + panNo.getWidth();

            fastReport.addColumn(dob);
            width = width + dob.getWidth();

            fastReport.addColumn(fatherName);
            width = width + fatherName.getWidth();

            fastReport.addColumn(perAddr);
            width = width + perAddr.getWidth();

            fastReport.addColumn(prnAmt);
            prnAmt.setStyle(style);
            width = width + 2 * prnAmt.getWidth();

            fastReport.addColumn(pMode);
            width = width + pMode.getWidth();

            fastReport.addColumn(depDate);
            width = width + depDate.getWidth();

            fastReport.addColumn(matDate);
            width = width + matDate.getWidth();

            fastReport.addColumn(roi);
            width = width + roi.getWidth();

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();
//            fastReport.addColumn(intAmt);
//            intAmt.setStyle(style);
            //width = width + 2 * intAmt.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Time Deposit Exceeding report");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

        return fastReport;
    }

    public void refreshAction() {
        setCalFromDate(dmyFormat.format(dt));
        setCaltoDate(dmyFormat.format(dt));
        this.setDdAcType("0");
        this.setAmt("0");
        this.setMessage("");
    }

    public String exitAction() {
        refreshAction();
        return "case1";
    }
}
