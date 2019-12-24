/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.ho.ChallanPojo;
import com.cbs.dto.report.ho.DeducteeChallanPojo;
import com.cbs.dto.report.ho.DeducteePojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class ChallanDeducteeDetail extends BaseBean {
    
    private String errorMessage;
    private String branchCode;
    private List<SelectItem>branchCodeList;
    private String tdsOption;
    private List<SelectItem> tdsOptionList;
    private String month;
    private List<SelectItem> monthList;
    private String year;
    private String tillDate;
    private String bsrCode;
    private String totalAmt;
    private String submmitDt;
    private String srNo;
    private String voucherNo;
    private String custType;
    private List<SelectItem> custTypeList;
    private String surCharge;
    private String eduCess;
    private String interest;
    private String fee;
    private String pentalyOther;
    private String hyperPanel;
    private String finYear;
    private String newFinYear;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private HoReportFacadeRemote hoRemote = null;
    private CommonReportMethodsRemote common = null;
    DeducteeChallanPojo reportDataPojo = new DeducteeChallanPojo();
    
    public ChallanDeducteeDetail() {
        
        try {
            setHyperPanel("none");
            setBsrCode("0");
            setTotalAmt("0");
            setSubmmitDt(dmy.format(date));
            setSrNo("0");
            setVoucherNo("0");
            setSurCharge("0");
            setEduCess("0");
            setInterest("0");
            setFee("0");
            setPentalyOther("0");
            
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup("HoReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            
            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchCodeList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
            
            tdsOptionList = new ArrayList<SelectItem>();
            tdsOptionList.add(new SelectItem("S", "--Select--"));
            tdsOptionList.add(new SelectItem("M", "MONTHLY"));
            //tdsOptionList.add(new SelectItem("Q", "QUARTERLY"));
            
            custTypeList = new ArrayList<SelectItem>();
            custTypeList.add(new SelectItem("ALL", "ALL"));
            custTypeList.add(new SelectItem("01", "INDIVIDUAL"));
            custTypeList.add(new SelectItem("02", "COMPANY"));
            
            btnRefreshAction();
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
        
    }
    
    public void getNewfYear() {
        this.newFinYear = finYear;
    }
    
    public void setMonthEndQuarEnd() {
        setErrorMessage("");
        try {
            setHyperPanel("none");
            if (tdsOption.equalsIgnoreCase("M")) {
                monthList = new ArrayList<SelectItem>();
                monthList.add(new SelectItem("0", "--Select--"));
                Map<Integer, String> monthMap = CbsUtil.getAllMonths();
                Set<Map.Entry<Integer, String>> mapSet = monthMap.entrySet();
                Iterator<Map.Entry<Integer, String>> mapIt = mapSet.iterator();
                while (mapIt.hasNext()) {
                    Map.Entry<Integer, String> mapEntry = mapIt.next();
                    monthList.add(new SelectItem(mapEntry.getKey(), mapEntry.getValue()));
                }
            }
//            else {
//                monthList = new ArrayList<SelectItem>();
//                monthList.add(new SelectItem("--Select--"));
//                monthList.add(new SelectItem("1", "JUNE"));
//                monthList.add(new SelectItem("2", "SEPTEMBER"));
//                monthList.add(new SelectItem("3", "DECEMBER"));
//                monthList.add(new SelectItem("0", "MARCH"));
//            }
        } catch (Exception e) {
        }
    }
    
    public void processAction() {
        setErrorMessage("");
        try {
            setHyperPanel("none");
            
            if (tdsOption.equalsIgnoreCase("S")) {
                this.setErrorMessage("Please select Tds Option.");
                return;
            }
            
            if (this.month == null || this.month.equals("0")) {
                this.setErrorMessage("Please select Month.");
                return;
            }
            if (this.year == null || year.equals("") || year.trim().length() != 4) {
                this.setErrorMessage("Please fill proper Year.");
                return;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(this.year);
            if (m.matches() != true) {
                this.setErrorMessage("Please fill proper Year in numeric");
                return;
            }
            
            if (finYear == null || finYear.equalsIgnoreCase("") || finYear.trim().length() != 4) {
                setErrorMessage("Please fill proper 4 digits financial year !");
                return;
            }
            
            Matcher finm = p.matcher(this.finYear);
            if (finm.matches() != true) {
                this.setErrorMessage("Please fill proper Year in numeric");
                return;
            }
            
            if (submmitDt == null || submmitDt.equalsIgnoreCase("")) {
                this.setErrorMessage("Please fill proper Submission date.");
                return;
            }
            Matcher bsr = p.matcher(this.bsrCode);
            if (bsr.matches() != true) {
                this.setErrorMessage("Please fill proper Bsr Code in numeric");
                return;
            }
            Matcher tAmt = p.matcher(this.totalAmt);
            if (tAmt.matches() != true) {
                this.setErrorMessage("Please fill proper Total Amount in numeric");
                return;
            }
            Matcher cNo = p.matcher(this.srNo);
            if (cNo.matches() != true) {
                this.setErrorMessage("Please fill proper Challan serial no in numeric");
                return;
            }
            Matcher vNo = p.matcher(this.voucherNo);
            if (vNo.matches() != true) {
                this.setErrorMessage("Please fill proper Voucher/ receipt no in numeric");
                return;
            }
            Matcher suChg = p.matcher(this.surCharge);
            if (suChg.matches() != true) {
                this.setErrorMessage("Please fill proper Surcharge in numeric");
                return;
            }
            Matcher eCess = p.matcher(this.eduCess);
            if (eCess.matches() != true) {
                this.setErrorMessage("Please fill proper Edu.cess in numeric");
                return;
            }
            Matcher inte = p.matcher(this.interest);
            if (inte.matches() != true) {
                this.setErrorMessage("Please fill proper Interest in numeric");
                return;
            }
            Matcher fe = p.matcher(this.fee);
            if (fe.matches() != true) {
                this.setErrorMessage("Please fill proper Fee in numeric");
                return;
            }
            Matcher po = p.matcher(this.pentalyOther);
            if (po.matches() != true) {
                this.setErrorMessage("Please fill proper Penalty.others in numeric");
                return;
            }
            
            tillDate = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year)));
            String firstDt = tillDate.substring(6, 10) + tillDate.substring(3, 5) + "01";
            String minQuarterDt = CbsUtil.getQuarterFirstAndLastDate(this.month, year, "F");
            
            reportDataPojo = hoRemote.getDeducteeChallanReturnData(tdsOption, firstDt, ymd.format(dmy.parse(tillDate)), bsrCode,
                    totalAmt, submmitDt, srNo, voucherNo, custType, surCharge, eduCess,
                    interest, fee, pentalyOther, minQuarterDt, finYear,branchCode);
            
            setHyperPanel("");
            
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }
    
    public void printReport(String repType) {
        setErrorMessage("");
        try {
            List<DeducteePojo> deducteeList = new ArrayList<DeducteePojo>();
            List<ChallanPojo> challanList = new ArrayList<ChallanPojo>();
            
            if (repType.equalsIgnoreCase("CHALLAN")) {
                challanList = reportDataPojo.getChallanList();
                
                FastReportBuilder challanReprot = genrateChallanReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(challanList), challanReprot, repType + "_" + CbsUtil.getMonthName(Integer.parseInt(month)));
            } else {
                deducteeList = reportDataPojo.getDeducteeList();
                FastReportBuilder deducteeReprot = genrateDeducteeReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(deducteeList), deducteeReprot, repType + "_" + CbsUtil.getMonthName(Integer.parseInt(month)));
            }
            
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }
    
    public FastReportBuilder genrateDeducteeReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);
            
            AbstractColumn rowNo = ReportBean.getColumn("rowNo", Integer.class, "ROW NO.", 70);
            AbstractColumn challanSerial = ReportBean.getColumn("challanSerial", Integer.class, "CHALLAN SERIAL NO.", 70);
            AbstractColumn updateDeductee = ReportBean.getColumn("updateDeductee", String.class, "UPDATE MODE FOR DEDUCTEE", 70);
            AbstractColumn bsrCodeOfBranch = ReportBean.getColumn("bsrCodeOfBranch", String.class, "BSR CODE OF BRANCH", 70);
            AbstractColumn dateTaxDeposit = ReportBean.getColumn("dateTaxDeposit", String.class, "DATE ON WHICH TAX DEPOSIT", 70);
            AbstractColumn receiptNo = ReportBean.getColumn("receiptNo", String.class, "TRANSFER VOUCHER/SERIAL NO.", 70);
            AbstractColumn sectionUnderPaymentMade = ReportBean.getColumn("sectionUnderPaymentMade", String.class, "SECTION UNDER WHICH PAYMENT MADE", 60);
            AbstractColumn totalTaxDeductee = ReportBean.getColumn("totalTaxDeductee", BigDecimal.class, "TOTAL TAX TO BE ALLOCATED AMONG DEDUCTEES", 80);
            AbstractColumn interestAmt = ReportBean.getColumn("interestAmt", Double.class, "INTEREST", 70);
            AbstractColumn otherPenalty = ReportBean.getColumn("otherPenalty", Double.class, "OTHERS", 70);
            AbstractColumn total789 = ReportBean.getColumn("total789", BigDecimal.class, "TOTAL 7+8+9", 80);
            AbstractColumn srlNo = ReportBean.getColumn("srlNo", Integer.class, "SR.NO.", 70);
            AbstractColumn deducteeRefNo = ReportBean.getColumn("deducteeRefNo", String.class, "DEDUCTEE REFERENCE NO.", 80);
            AbstractColumn lastPanOfDeductee = ReportBean.getColumn("lastPanOfDeductee", String.class, "LAST PAN OF DEDUCTEE", 70);
            AbstractColumn deducteePan = ReportBean.getColumn("deducteePan", String.class, "PAN OF DEDUCTEE", 80);
            AbstractColumn nameOfDeductee = ReportBean.getColumn("nameOfDeductee", String.class, "NAME OF DEDUCTEE", 130);
            AbstractColumn creditAmtDate = ReportBean.getColumn("creditAmtDate", String.class, "DATE OF PAYMENT/CREDIT", 70);
            AbstractColumn paymentAmt = ReportBean.getColumn("paymentAmt", String.class, "AMT.PAID/CREDIT", 80);
            AbstractColumn tds = ReportBean.getColumn("tds", String.class, "TDS", 70);
            AbstractColumn surChargeAmt = ReportBean.getColumn("surChargeAmt", String.class, "SURCHARGE", 60);
            AbstractColumn eduCessAmt = ReportBean.getColumn("eduCessAmt", String.class, "EDU.CESS", 60);
            AbstractColumn totalTaxDeducted181920 = ReportBean.getColumn("totalTaxDeducted181920", String.class, "TOTAL TAX DEDUCTED 18+19+20", 80);
            AbstractColumn lastTotaltaxDeducted = ReportBean.getColumn("lastTotaltaxDeducted", String.class, "LAST TOTAL TAX DEDUCTED", 80);
            AbstractColumn totalTaxDeposited = ReportBean.getColumn("totalTaxDeposited", String.class, "TOTAL TAX DEPOSITED", 100);
            AbstractColumn lastTotalTaxDeposited = ReportBean.getColumn("lastTotalTaxDeposited", String.class, "LAST TOTAL TAX DEPOSITED", 80);
            AbstractColumn taxDeducteeDate = ReportBean.getColumn("taxDeducteeDate", String.class, "DATE OF DEDUCTION", 70);
            AbstractColumn reasonNonLowHigherDeduction = ReportBean.getColumn("reasonNonLowHigherDeduction", String.class, "REASON FOR NON/LOWER/HIGHER DEDUCTION", 120);
            AbstractColumn deducteeCode = ReportBean.getColumn("deducteeCode", String.class, "DEDUCTEE CODE (1-COMPANY,2-OTHER THAN COMPANY", 50);
            AbstractColumn taxDeducteeRate = ReportBean.getColumn("taxDeducteeRate", String.class, "RATE AT WHICH TAX DEDUCTED", 60);
            AbstractColumn paidByBookEntry = ReportBean.getColumn("paidByBookEntry", String.class, "PAID BY BOOK ENTRY", 70);
            AbstractColumn certificateNo = ReportBean.getColumn("certificateNo", String.class, "CERTIFICATE NO.ISSUED", 80);
            AbstractColumn custId = ReportBean.getColumn("custId", String.class, "Customer Id", 200);
            
            fastReport.addColumn(rowNo);
            width = width + rowNo.getWidth();
            
            fastReport.addColumn(challanSerial);
            width = width + challanSerial.getWidth();
            
            fastReport.addColumn(updateDeductee);
            width = width + updateDeductee.getWidth();
            
            fastReport.addColumn(bsrCodeOfBranch);
            width = width + bsrCodeOfBranch.getWidth();
            
            fastReport.addColumn(dateTaxDeposit);
            width = width + dateTaxDeposit.getWidth();
            
            fastReport.addColumn(receiptNo);
            width = width + receiptNo.getWidth();
            
            fastReport.addColumn(sectionUnderPaymentMade);
            width = width + sectionUnderPaymentMade.getWidth();
            
            fastReport.addColumn(totalTaxDeductee);
            width = width + totalTaxDeductee.getWidth();
            
            fastReport.addColumn(interestAmt);
            width = width + interestAmt.getWidth();
            
            fastReport.addColumn(otherPenalty);
            width = width + otherPenalty.getWidth();
            
            fastReport.addColumn(total789);
            width = width + total789.getWidth();
            
            fastReport.addColumn(srlNo);
            width = width + srlNo.getWidth();
            
            fastReport.addColumn(deducteeRefNo);
            width = width + deducteeRefNo.getWidth();
            
            fastReport.addColumn(lastPanOfDeductee);
            width = width + lastPanOfDeductee.getWidth();
            
            fastReport.addColumn(deducteePan);
            width = width + deducteePan.getWidth();
            
            fastReport.addColumn(nameOfDeductee);
            width = width + nameOfDeductee.getWidth();
            
            fastReport.addColumn(creditAmtDate);
            width = width + creditAmtDate.getWidth();
            
            fastReport.addColumn(paymentAmt);
            width = width + paymentAmt.getWidth();
            
            fastReport.addColumn(tds);
            width = width + tds.getWidth();
            
            fastReport.addColumn(surChargeAmt);
            width = width + surChargeAmt.getWidth();
            
            fastReport.addColumn(eduCessAmt);
            width = width + eduCessAmt.getWidth();
            
            fastReport.addColumn(totalTaxDeducted181920);
            width = width + totalTaxDeducted181920.getWidth();
            
            
            fastReport.addColumn(lastTotaltaxDeducted);
            width = width + lastTotaltaxDeducted.getWidth();
            
            fastReport.addColumn(totalTaxDeposited);
            width = width + totalTaxDeposited.getWidth();
            
            fastReport.addColumn(lastTotalTaxDeposited);
            width = width + lastTotalTaxDeposited.getWidth();
            
            fastReport.addColumn(taxDeducteeDate);
            width = width + taxDeducteeDate.getWidth();
            
            fastReport.addColumn(reasonNonLowHigherDeduction);
            width = width + reasonNonLowHigherDeduction.getWidth();
            
            fastReport.addColumn(deducteeCode);
            width = width + deducteeCode.getWidth();
            
            fastReport.addColumn(taxDeducteeRate);
            width = width + taxDeducteeRate.getWidth();
            
            fastReport.addColumn(paidByBookEntry);
            width = width + paidByBookEntry.getWidth();
            
            fastReport.addColumn(certificateNo);
            width = width + certificateNo.getWidth();
            
            fastReport.addColumn(custId);
            width = width + custId.getWidth();
            
            
            Page page = new Page(4200, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
        
    }
    
    public FastReportBuilder genrateChallanReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);
            
            AbstractColumn srlNo = ReportBean.getColumn("srlNo", Integer.class, "SR.NO.", 60);
            AbstractColumn updateDeductee = ReportBean.getColumn("updateDeductee", String.class, "UPDATE MODE OF CHALLAN", 60);
            AbstractColumn sectionCode = ReportBean.getColumn("sectionCode", String.class, "SECTION CODE", 60);
            AbstractColumn tds = ReportBean.getColumn("tds", BigDecimal.class, "TDS", 70);
            AbstractColumn surChargeAmt = ReportBean.getColumn("surChargeAmt", String.class, "SURCHARGE", 70);
            AbstractColumn eduCessAmt = ReportBean.getColumn("eduCessAmt", String.class, "EDU.CESS", 60);
            AbstractColumn interestAmt = ReportBean.getColumn("interestAmt", String.class, "INTEREST", 70);
            AbstractColumn fees = ReportBean.getColumn("fees", String.class, "FEE", 60);
            AbstractColumn otherPenalty = ReportBean.getColumn("otherPenalty", String.class, "PENALTY.OTHERS", 80);
            AbstractColumn lastTotalTaxDeposited = ReportBean.getColumn("lastTotalTaxDeposited", String.class, "LAST TOTAL TAX DEPOSITED", 70);
            AbstractColumn totalAmtDeposit456789 = ReportBean.getColumn("totalAmtDeposit456789", String.class, "TOTAL AMT. DEPOSIT 4+5+6+7+8+9", 80);
            AbstractColumn challanDDoNo = ReportBean.getColumn("challanDDoNo", String.class, "CHEQUE/DD NO.", 90);
            AbstractColumn lastBsrCode = ReportBean.getColumn("lastBsrCode", String.class, "LAST BSR CODE", 130);
            AbstractColumn bsrCodeRef = ReportBean.getColumn("bsrCodeRef", String.class, "BSR CODE/RECEIPT NO.", 100);
            AbstractColumn lastDateTaxDeposit = ReportBean.getColumn("lastDateTaxDeposit", String.class, "LAST DATE ON WHICH TAX DEPOSIT", 70);
            AbstractColumn dateOnWhichAmtDeposited = ReportBean.getColumn("dateOnWhichAmtDeposited", String.class, "DATE ON WHICH AMT.DEPOSITED", 70);
            AbstractColumn lastDdovoucherchallanSrno = ReportBean.getColumn("lastDdovoucherchallanSrno", String.class, "LAST DDO/TR.VOUCHER/CHALLAN SR.NO.", 70);
            AbstractColumn challanSerial = ReportBean.getColumn("challanSerial", Integer.class, "CHALLAN SERIAL NO.", 70);
            AbstractColumn modeOfDepositYesno = ReportBean.getColumn("modeOfDepositYesno", String.class, "MODE OF DEPOSIT th.book adjustment YES/NO", 60);
            AbstractColumn interestToBeAllocated = ReportBean.getColumn("interestToBeAllocated", String.class, "INTEREST TO BE ALLOCATED", 60);
            AbstractColumn others = ReportBean.getColumn("others", String.class, "OTHERS", 60);
            AbstractColumn minorNo = ReportBean.getColumn("minorNo", String.class, "MINOR HEAD OF CHALLAN 200/400", 60);
            AbstractColumn challanBalance = ReportBean.getColumn("challanBalance", String.class, "CHALLAN BALANCE AS PER CONSOLIDATED FILE", 80);
            
            fastReport.addColumn(srlNo);
            width = width + srlNo.getWidth();
            
            fastReport.addColumn(updateDeductee);
            width = width + updateDeductee.getWidth();
            
            fastReport.addColumn(sectionCode);
            width = width + sectionCode.getWidth();
            
            fastReport.addColumn(tds);
            width = width + tds.getWidth();
            
            fastReport.addColumn(surChargeAmt);
            width = width + surChargeAmt.getWidth();
            
            fastReport.addColumn(eduCessAmt);
            width = width + eduCessAmt.getWidth();
            
            fastReport.addColumn(interestAmt);
            width = width + interestAmt.getWidth();
            
            fastReport.addColumn(fees);
            width = width + fees.getWidth();
            
            fastReport.addColumn(otherPenalty);
            width = width + otherPenalty.getWidth();
            
            fastReport.addColumn(lastTotalTaxDeposited);
            width = width + lastTotalTaxDeposited.getWidth();
            
            fastReport.addColumn(totalAmtDeposit456789);
            width = width + totalAmtDeposit456789.getWidth();
            
            fastReport.addColumn(challanDDoNo);
            width = width + challanDDoNo.getWidth();
            
            fastReport.addColumn(lastBsrCode);
            width = width + lastBsrCode.getWidth();
            
            fastReport.addColumn(bsrCodeRef);
            width = width + bsrCodeRef.getWidth();
            
            fastReport.addColumn(lastDateTaxDeposit);
            width = width + lastDateTaxDeposit.getWidth();
            
            fastReport.addColumn(dateOnWhichAmtDeposited);
            width = width + dateOnWhichAmtDeposited.getWidth();
            
            fastReport.addColumn(lastDdovoucherchallanSrno);
            width = width + lastDdovoucherchallanSrno.getWidth();
            
            fastReport.addColumn(challanSerial);
            width = width + challanSerial.getWidth();
            
            fastReport.addColumn(modeOfDepositYesno);
            width = width + modeOfDepositYesno.getWidth();
            
            fastReport.addColumn(interestToBeAllocated);
            width = width + interestToBeAllocated.getWidth();
            
            fastReport.addColumn(others);
            width = width + others.getWidth();
            
            fastReport.addColumn(minorNo);
            width = width + minorNo.getWidth();
            
            fastReport.addColumn(challanBalance);
            width = width + challanBalance.getWidth();
            
            Page page = new Page(4200, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
        
    }
    
    public void btnRefreshAction() {
        setErrorMessage("");
        DeducteeChallanPojo reportDataPojo = new DeducteeChallanPojo();
        setHyperPanel("none");
        setBsrCode("0");
        setTotalAmt("0");
        setSubmmitDt(dmy.format(date));
        setSrNo("0");
        setVoucherNo("0");
        setSurCharge("0");
        setEduCess("0");
        setInterest("0");
        setFee("0");
        setPentalyOther("0");
        setMonth("0");
        setYear("");
        setFinYear("");
        setNewFinYear("");
    }
    
    public String btnExitAction() {
        return "case1";
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getTdsOption() {
        return tdsOption;
    }
    
    public void setTdsOption(String tdsOption) {
        this.tdsOption = tdsOption;
    }
    
    public List<SelectItem> getTdsOptionList() {
        return tdsOptionList;
    }
    
    public void setTdsOptionList(List<SelectItem> tdsOptionList) {
        this.tdsOptionList = tdsOptionList;
    }
    
    public String getMonth() {
        return month;
    }
    
    public void setMonth(String month) {
        this.month = month;
    }
    
    public List<SelectItem> getMonthList() {
        return monthList;
    }
    
    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }
    
    public String getYear() {
        return year;
    }
    
    public void setYear(String year) {
        this.year = year;
    }
    
    public String getTillDate() {
        return tillDate;
    }
    
    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getBsrCode() {
        return bsrCode;
    }
    
    public void setBsrCode(String bsrCode) {
        this.bsrCode = bsrCode;
    }
    
    public String getTotalAmt() {
        return totalAmt;
    }
    
    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }
    
    public String getSubmmitDt() {
        return submmitDt;
    }
    
    public void setSubmmitDt(String submmitDt) {
        this.submmitDt = submmitDt;
    }
    
    public String getSrNo() {
        return srNo;
    }
    
    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }
    
    public String getVoucherNo() {
        return voucherNo;
    }
    
    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }
    
    public String getCustType() {
        return custType;
    }
    
    public void setCustType(String custType) {
        this.custType = custType;
    }
    
    public List<SelectItem> getCustTypeList() {
        return custTypeList;
    }
    
    public void setCustTypeList(List<SelectItem> custTypeList) {
        this.custTypeList = custTypeList;
    }
    
    public String getSurCharge() {
        return surCharge;
    }
    
    public void setSurCharge(String surCharge) {
        this.surCharge = surCharge;
    }
    
    public String getEduCess() {
        return eduCess;
    }
    
    public void setEduCess(String eduCess) {
        this.eduCess = eduCess;
    }
    
    public String getInterest() {
        return interest;
    }
    
    public void setInterest(String interest) {
        this.interest = interest;
    }
    
    public String getFee() {
        return fee;
    }
    
    public void setFee(String fee) {
        this.fee = fee;
    }
    
    public String getPentalyOther() {
        return pentalyOther;
    }
    
    public void setPentalyOther(String pentalyOther) {
        this.pentalyOther = pentalyOther;
    }
    
    public String getHyperPanel() {
        return hyperPanel;
    }
    
    public void setHyperPanel(String hyperPanel) {
        this.hyperPanel = hyperPanel;
    }
    
    public String getFinYear() {
        return finYear;
    }
    
    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }
    
    public String getNewFinYear() {
        return newFinYear;
    }
    
    public void setNewFinYear(String newFinYear) {
        this.newFinYear = newFinYear;
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
    
}
