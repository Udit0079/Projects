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
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.ChallanDeducteePojo;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class FdChallanDeducteeDetail extends BaseBean {

    private String message;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    private String reportType;
    private List<SelectItem> reportTypeList;
    private DDSReportFacadeRemote ddsRemote;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    List<ChallanDeducteePojo> reportList = new ArrayList<ChallanDeducteePojo>();

    public FdChallanDeducteeDetail() {

        try {
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("S", "--Select--"));
            reportTypeList.add(new SelectItem("C", "Challan Detail"));
            reportTypeList.add(new SelectItem("D", "Deductee Detail"));
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }

    public Date getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(Date caltoDate) {
        this.caltoDate = caltoDate;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public void excelPrint() {
        setMessage("");
        try {
            if (reportType.equalsIgnoreCase("S")) {
                setMessage("Please select report type !");
                return;
            }
            reportList = ddsRemote.getChallanDeducteeData(reportType, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exits !");
            }
            if (reportType.equalsIgnoreCase("C")) {
                FastReportBuilder challanDetail = genrateChallanDetail();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(reportList), challanDetail, "Challan Details_" + ymdFormat.format(calFromDate) + " to " + ymdFormat.format(caltoDate));
            } else {
                FastReportBuilder challanDetail = genrateDeducteeDetail();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(reportList), challanDetail, "Deductee Details_" + ymdFormat.format(calFromDate) + " to " + ymdFormat.format(caltoDate));
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public FastReportBuilder genrateChallanDetail() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn challanSerial = ReportBean.getColumn("challanSerial", Integer.class, "Challan Serial No. (401)", 60);
            AbstractColumn incomeTax = ReportBean.getColumn("incomeTax", Double.class, "Income Tax (402)", 80);
            AbstractColumn interest = ReportBean.getColumn("interest", Double.class, "Interest (403)", 80);
            AbstractColumn fees = ReportBean.getColumn("fees", Double.class, "Fees (404)", 80);
            AbstractColumn otherPenalty = ReportBean.getColumn("otherPenalty", Double.class, "Others/Penalty (405)", 80);
            AbstractColumn totalTax = ReportBean.getColumn("totalTax", BigDecimal.class, "Total Tax Deposited/ Book Adjustment (406)", 80);
            AbstractColumn depositBook = ReportBean.getColumn("depositBook", String.class, "Whether deposited by book Adjustment Yes/No (407)", 120);
            AbstractColumn bsrCode = ReportBean.getColumn("bsrCode", String.class, "BSR Code (408)", 80);
            AbstractColumn taxDeposit = ReportBean.getColumn("taxDeposit", String.class, "Date on which Tax Deposited/ Date of Transfer voucher (410)", 120);
            AbstractColumn challanDDoNo = ReportBean.getColumn("challanDDoNo", String.class, "Challan Serial No./DDO Serial No. of Form no. 24G (409)", 120);

            AbstractColumn receiptNo = ReportBean.getColumn("receiptNo", String.class, "Receipt No. of form 24G (408)", 100);
            AbstractColumn minorNo = ReportBean.getColumn("minorNo", String.class, "Minor Head of Challan (411)", 100);
            AbstractColumn chequeDDNo = ReportBean.getColumn("chequeDDNo", String.class, "Cheque/DD No.", 100);
            AbstractColumn sectionCode = ReportBean.getColumn("sectionCode", String.class, "Section Code For Own Record (It will be import in Remark)", 120);
            AbstractColumn brCode = ReportBean.getColumn("brCode", String.class, "Branch Code", 60);

            fastReport.addColumn(challanSerial);
            challanSerial.setStyle(style);
            width = width + challanSerial.getWidth();

            fastReport.addColumn(incomeTax);
            incomeTax.setStyle(style);
            width = width + incomeTax.getWidth();

            fastReport.addColumn(interest);
            interest.setStyle(style);
            width = width + interest.getWidth();

            fastReport.addColumn(fees);
            fees.setStyle(style);
            width = width + fees.getWidth();

            fastReport.addColumn(otherPenalty);
            otherPenalty.setStyle(style);
            width = width + otherPenalty.getWidth();

            fastReport.addColumn(totalTax);
            totalTax.setStyle(style);
            width = width + totalTax.getWidth();

            fastReport.addColumn(depositBook);
            depositBook.setStyle(style);
            width = width + depositBook.getWidth();

            fastReport.addColumn(bsrCode);
            bsrCode.setStyle(style);
            width = width + bsrCode.getWidth();

            fastReport.addColumn(taxDeposit);
            taxDeposit.setStyle(style);
            width = width + taxDeposit.getWidth();

            fastReport.addColumn(challanDDoNo);
            challanDDoNo.setStyle(style);
            width = width + challanDDoNo.getWidth();

            fastReport.addColumn(receiptNo);
            receiptNo.setStyle(style);
            width = width + receiptNo.getWidth();

            fastReport.addColumn(minorNo);
            minorNo.setStyle(style);
            width = width + minorNo.getWidth();

            fastReport.addColumn(chequeDDNo);
            chequeDDNo.setStyle(style);
            width = width + chequeDDNo.getWidth();

            fastReport.addColumn(sectionCode);
            sectionCode.setStyle(style);
            width = width + sectionCode.getWidth();
            
            fastReport.addColumn(brCode);
            brCode.setStyle(style);
            width = width + brCode.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Challan Details");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateDeducteeDetail() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn challanSerial = ReportBean.getColumn("challanSerial", Integer.class, "Challan Serial No.", 60);
            AbstractColumn sectionCode = ReportBean.getColumn("sectionCode", String.class, "Section Code", 120);
            AbstractColumn typeRent = ReportBean.getColumn("typeRent", String.class, "Type of Rent", 80);
            AbstractColumn deducteeIdentificationNo = ReportBean.getColumn("deducteeIdentificationNo", String.class, "Deductee Identification No.", 80);
            AbstractColumn deducteeCode = ReportBean.getColumn("deducteeCode", String.class, "Deductee Code (414)", 80);
            AbstractColumn deducteePan = ReportBean.getColumn("deducteePan", String.class, "Permanent Account Number (PAN) of deductee (415)", 80);
            AbstractColumn deducteeRefNo = ReportBean.getColumn("deducteeRefNo", String.class, "Deductee reference No. (In case of Invalid PAN)(413)", 80);
            AbstractColumn nameOfDeductee = ReportBean.getColumn("nameOfDeductee", String.class, "Name of Deductee (416)", 180);
            AbstractColumn creditAmtDate = ReportBean.getColumn("creditAmtDate", String.class, "Date on which Amount paid / credited (418)", 80);
            AbstractColumn taxDeducteeDate = ReportBean.getColumn("taxDeducteeDate", String.class, "Date on which tax deducted (422)", 80);
            AbstractColumn paymentAmt = ReportBean.getColumn("paymentAmt", Double.class, "Amount of Payment ( Rs.) (419)", 80);
            AbstractColumn taxDeducteeRate = ReportBean.getColumn("taxDeducteeRate", Double.class, "Rate at which tax deducted (423)", 80);
            AbstractColumn taxDeducteeAmt = ReportBean.getColumn("taxDeducteeAmt", Double.class, "Amount of tax deducted", 80);
            AbstractColumn totalTaxDeposit = ReportBean.getColumn("totalTaxDeposit", BigDecimal.class, "Total Tax Deposited (421)", 80);
            AbstractColumn reasonForDeduction = ReportBean.getColumn("reasonForDeduction", String.class, "Reason for Non-deduction / Lower Deduction, if any (424)", 80);
            AbstractColumn certificateNo = ReportBean.getColumn("certificateNo", String.class, "Certificate No .u/s 197 issued by the AO for non deduction/ lower deduction (425)", 80);
            AbstractColumn flatNo = ReportBean.getColumn("flatNo", String.class, "Deductee Flat No.", 80);
            AbstractColumn deducteeBuildingName = ReportBean.getColumn("deducteeBuildingName", String.class, "Deductee Building Name", 80);
            AbstractColumn deducteeStreetName = ReportBean.getColumn("deducteeStreetName", String.class, "Deductee Street Name", 220);
            AbstractColumn deducteeArea = ReportBean.getColumn("deducteeArea", String.class, "Deductee Area", 220);
            AbstractColumn deducteeCityTown = ReportBean.getColumn("deducteeCityTown", String.class, "Deductee City/Town", 80);
            AbstractColumn deducteePin = ReportBean.getColumn("deducteePin", String.class, "Deductee PIN", 80);
            AbstractColumn deducteeState = ReportBean.getColumn("deducteeState", String.class, "Deductee STATE", 80);
            AbstractColumn brCode = ReportBean.getColumn("brCode", String.class, "Branch Code", 60);

            fastReport.addColumn(challanSerial);
            challanSerial.setStyle(style);
            width = width + challanSerial.getWidth();

            fastReport.addColumn(sectionCode);
            sectionCode.setStyle(style);
            width = width + sectionCode.getWidth();

            fastReport.addColumn(typeRent);
            width = width + typeRent.getWidth();

            fastReport.addColumn(deducteeIdentificationNo);
            deducteeIdentificationNo.setStyle(style);
            width = width + deducteeIdentificationNo.getWidth();

            fastReport.addColumn(deducteeCode);
            deducteeCode.setStyle(style);
            width = width + deducteeCode.getWidth();

            fastReport.addColumn(deducteePan);
            width = width + deducteePan.getWidth();

            fastReport.addColumn(deducteeRefNo);
            deducteeRefNo.setStyle(style);
            width = width + deducteeRefNo.getWidth();

            fastReport.addColumn(nameOfDeductee);
            width = width + nameOfDeductee.getWidth();

            fastReport.addColumn(creditAmtDate);
            width = width + creditAmtDate.getWidth();

            fastReport.addColumn(taxDeducteeDate);
            width = width + taxDeducteeDate.getWidth();

            fastReport.addColumn(paymentAmt);
            paymentAmt.setStyle(style);
            width = width + paymentAmt.getWidth();

            fastReport.addColumn(taxDeducteeRate);
            taxDeducteeRate.setStyle(style);
            width = width + taxDeducteeRate.getWidth();

            fastReport.addColumn(taxDeducteeAmt);
            taxDeducteeAmt.setStyle(style);
            width = width + taxDeducteeAmt.getWidth();

            fastReport.addColumn(totalTaxDeposit);
            totalTaxDeposit.setStyle(style);
            width = width + totalTaxDeposit.getWidth();

            fastReport.addColumn(reasonForDeduction);
            width = width + reasonForDeduction.getWidth();

            fastReport.addColumn(certificateNo);
            width = width + certificateNo.getWidth();

            fastReport.addColumn(flatNo);
            width = width + flatNo.getWidth();

            fastReport.addColumn(deducteeBuildingName);
            width = width + deducteeBuildingName.getWidth();

            fastReport.addColumn(deducteeStreetName);
            width = width + deducteeStreetName.getWidth();

            fastReport.addColumn(deducteeArea);
            width = width + deducteeArea.getWidth();

            fastReport.addColumn(deducteeCityTown);
            width = width + deducteeCityTown.getWidth();

            fastReport.addColumn(deducteePin);
            deducteePin.setStyle(style);
            width = width + deducteePin.getWidth();

            fastReport.addColumn(deducteeState);
            width = width + deducteeState.getWidth();
            
            fastReport.addColumn(brCode);
            brCode.setStyle(style);
            width = width + brCode.getWidth();



            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Deductee Details");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public void refreshAction() {
        setMessage("");
    }

    public String exitAction() {
        return "case1";
    }
}
