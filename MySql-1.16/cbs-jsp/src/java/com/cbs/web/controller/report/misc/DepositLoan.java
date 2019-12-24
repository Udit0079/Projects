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
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.DepositLoanPojo;
import com.cbs.pojo.exchangeDetailPojo;
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
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class DepositLoan extends BaseBean {

    private String message;
    private String repType;
    private List<SelectItem> repTypeList;
    private String branch;
    private List<SelectItem> branchList;
    private String denominationType;
    private List<SelectItem> denominationTypeList;
    private String frDt;
    private String toDt;
    private Date date = new Date();
    private Boolean disableAcType;
    private Boolean disableDenoType;
    private TdReceiptManagementFacadeRemote RemoteCode;
    String ddAcType;
    List<SelectItem> ddAcTypeList;
    Map<String, String> acTypeMap;
    private CommonReportMethodsRemote common;
    private MiscReportFacadeRemote remoteFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<DepositLoanPojo> sbn1List = new ArrayList<DepositLoanPojo>();
    List<exchangeDetailPojo> exchangeList = new ArrayList<exchangeDetailPojo>();

    public DepositLoan() {

        try {

            setFrDt("08/11/2016");
            setToDt(dmy.format(date));
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            repTypeList = new ArrayList<SelectItem>();
            denominationTypeList = new ArrayList<SelectItem>();
            ddAcTypeList = new ArrayList<SelectItem>();

            repTypeList.add(new SelectItem("S", "--Select--"));
            repTypeList.add(new SelectItem("1", "Deposit & Loans"));
//            repTypeList.add(new SelectItem("2", "DD PO"));
            repTypeList.add(new SelectItem("4", "Exchange Money"));
//            repTypeList.add(new SelectItem("3", "Suspense"));

            denominationTypeList.add(new SelectItem("S", "--Select--"));
            denominationTypeList.add(new SelectItem("Y", "With Denomination"));
            denominationTypeList.add(new SelectItem("N", "Without Denomination"));

            branchList = new ArrayList<SelectItem>();
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            acTypeMap = new HashMap<String, String>();
            List acCodeList = common.getAccTypeExcludePO();
            ddAcTypeList = new ArrayList<SelectItem>();
            ddAcTypeList.add(new SelectItem("0", "--Select--"));
            for (Object element : acCodeList) {
                Vector vector = (Vector) element;
                ddAcTypeList.add(new SelectItem(vector.get(0).toString(), vector.get(0).toString()));
                acTypeMap.put(vector.get(0).toString(), vector.get(1).toString());
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onReportType() {

        if (repType.equalsIgnoreCase("4")) {
            this.disableAcType = true;
            this.disableDenoType = true;
        } else {
            this.disableAcType = false;
            this.disableDenoType = false;
        }
    }

    public void onDenoTypeAction() {

        if (denominationType.equalsIgnoreCase("Y")) {
            this.disableAcType = true;
        } else {
            this.disableAcType = false;
        }
    }

    public void printAction() {
        setMessage("");

        try {

            if (repType == null || repType.equalsIgnoreCase("S")) {
                setMessage("Please Select Report Type !");
                return;
            }
            if (!repType.equalsIgnoreCase("4")) {
                if (!denominationType.equalsIgnoreCase("Y")) {
                    if (ddAcType == null || ddAcType.equals("0")) {
                        setMessage("Please Select Account Type.");
                        return;
                    }
                }
            }

            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(frDt)) {
                setMessage("Please fill proper from date ! ");
                return;
            }

            if (dmy.parse(frDt).after(getDate())) {
                setMessage("From date should be less than current date !");
                return;
            }

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date !");
                return;
            }

            if (!Validator.validateDate(toDt)) {
                setMessage("Please fill proper to date ! ");
                return;
            }

            if (dmy.parse(toDt).after(getDate())) {
                setMessage("To date should be less than current date !");
                return;
            }

            if (dmy.parse(frDt).after(dmy.parse(toDt))) {
                setMessage("From date should be less than current To date !");
                return;
            }
            if (!repType.equalsIgnoreCase("4")) {
                if (denominationType == null || denominationType.equalsIgnoreCase("S")) {
                    setMessage("Please choose the Denomination Type value first");
                    return;
                }
            }

            if (repType.equalsIgnoreCase("1")) {

                sbn1List = remoteFacade.getDepositLoanData(repType, branch, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)), ddAcType, denominationType);
                if (sbn1List.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
                FastReportBuilder depositLoanReprot = genrateDepositLoanReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(sbn1List), depositLoanReprot, "SBN DEPOSIT_" + ymd.format(dmy.parse(toDt)));
            } else if (repType.equalsIgnoreCase("4")) {

                exchangeList = remoteFacade.getExchangeData(repType, branch, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)), ddAcType, denominationType);
                if (exchangeList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
                FastReportBuilder exchangeReprot = genrateExchangeReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(exchangeList), exchangeReprot, "EXCHANGE SBN REPORT_" + ymd.format(dmy.parse(toDt)));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public FastReportBuilder genrateDepositLoanReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn sNo = ReportBean.getColumn("sNo", Integer.class, "S. No.", 60);
            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account No", 100);
            AbstractColumn acType = ReportBean.getColumn("acType", String.class, "Type of A/c(SB, CA,RD, FD,PMJDY etc., loan)", 140);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Name", 200);
            AbstractColumn ucicUid = ReportBean.getColumn("ucicUid", String.class, "UCIC/UID", 120);
            AbstractColumn panNo = ReportBean.getColumn("panNo", String.class, "PAN if available", 100);
            AbstractColumn openingDt = ReportBean.getColumn("openingDt", String.class, "Date of opening account", 100);
            AbstractColumn lastOperationDt = ReportBean.getColumn("lastOperationDt", String.class, "Date of last operation before " + frDt, 100);
            AbstractColumn depositDt = ReportBean.getColumn("depositDt", String.class, "Date of deposit", 100);
            AbstractColumn depositedAmt = ReportBean.getColumn("depositedAmt", BigDecimal.class, "Amount deposited", 100);
            AbstractColumn amtInsbn500 = ReportBean.getColumn("amtInsbn500", BigDecimal.class, "Amount in SBN 500/-", 100);
            AbstractColumn amtInsbn1000 = ReportBean.getColumn("amtInsbn1000", BigDecimal.class, "Amount in SBN 1000/-", 100);
            AbstractColumn closingDt = ReportBean.getColumn("closingDt", String.class, "If A/c closed date of closing", 100);
            AbstractColumn amtWithdrwaInCash = ReportBean.getColumn("amtWithdrwaInCash", BigDecimal.class, "Amount Withdrwan in cash (if any)", 100);
            AbstractColumn withdrwalDt = ReportBean.getColumn("withdrwalDt", String.class, "Date of withdrwal", 100);
            AbstractColumn phoneNo = ReportBean.getColumn("phoneNo", String.class, "Phone No.", 100);


            fastReport.addColumn(sNo);
            width = width + sNo.getWidth();

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(acType);
            width = width + acType.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(ucicUid);
            width = width + ucicUid.getWidth();

            fastReport.addColumn(panNo);
            width = width + panNo.getWidth();

            fastReport.addColumn(openingDt);
            width = width + openingDt.getWidth();

            fastReport.addColumn(lastOperationDt);
            width = width + lastOperationDt.getWidth();

            fastReport.addColumn(depositDt);
            width = width + depositDt.getWidth();

            fastReport.addColumn(depositedAmt);
            depositedAmt.setStyle(style);
            width = width + depositedAmt.getWidth();

            fastReport.addColumn(amtInsbn500);
            amtInsbn500.setStyle(style);
            width = width + amtInsbn500.getWidth();

            fastReport.addColumn(amtInsbn1000);
            amtInsbn1000.setStyle(style);
            width = width + amtInsbn1000.getWidth();

            fastReport.addColumn(closingDt);
            width = width + closingDt.getWidth();

            fastReport.addColumn(amtWithdrwaInCash);
            amtWithdrwaInCash.setStyle(style);
            width = width + amtWithdrwaInCash.getWidth();

            fastReport.addColumn(withdrwalDt);
            width = width + withdrwalDt.getWidth();
            
            fastReport.addColumn(phoneNo);
            width = width + phoneNo.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);

            fastReport.setTitle("SBN Deposit");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateExchangeReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn sNo = ReportBean.getColumn("sNo", Integer.class, "S. No.", 60);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Name", 200);
            AbstractColumn annexV = ReportBean.getColumn("annexV", String.class, "Whether Annex-V obtained", 120);
            AbstractColumn exchangeDt = ReportBean.getColumn("exchangeDt", String.class, "Date of exchange", 100);
            AbstractColumn amt500 = ReportBean.getColumn("amt500", BigDecimal.class, "Amount in denomination of 500/-(SBN)", 100);
            AbstractColumn amt1000 = ReportBean.getColumn("amt1000", BigDecimal.class, "Amount in denomination of 1000/-(SBN)", 100);
            AbstractColumn panNo = ReportBean.getColumn("panNo", String.class, "PAN if available", 100);
            AbstractColumn totalAmt = ReportBean.getColumn("totalAmt", BigDecimal.class, "Total Amount", 100);

            fastReport.addColumn(sNo);
            width = width + sNo.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(annexV);
            width = width + annexV.getWidth();

            fastReport.addColumn(exchangeDt);
            width = width + exchangeDt.getWidth();

            fastReport.addColumn(amt500);
            amt500.setStyle(style);
            width = width + amt500.getWidth();

            fastReport.addColumn(amt1000);
            amt1000.setStyle(style);
            width = width + amt1000.getWidth();

            fastReport.addColumn(panNo);
            width = width + panNo.getWidth();

            fastReport.addColumn(totalAmt);
            totalAmt.setStyle(style);
            width = width + totalAmt.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);

            fastReport.setTitle("Exchnage SBN Report");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public void btnRefreshAction() {
        setMessage("");
        setRepType("S");
        setDdAcType("0");
        setDenominationType("S");
        setFrDt("08/11/2016");
        setToDt(dmy.format(date));
    }

    public String btnExitAction() {

        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDenominationType() {
        return denominationType;
    }

    public void setDenominationType(String denominationType) {
        this.denominationType = denominationType;
    }

    public List<SelectItem> getDenominationTypeList() {
        return denominationTypeList;
    }

    public void setDenominationTypeList(List<SelectItem> denominationTypeList) {
        this.denominationTypeList = denominationTypeList;
    }

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

    public Boolean getDisableAcType() {
        return disableAcType;
    }

    public void setDisableAcType(Boolean disableAcType) {
        this.disableAcType = disableAcType;
    }

    public Boolean getDisableDenoType() {
        return disableDenoType;
    }

    public void setDisableDenoType(Boolean disableDenoType) {
        this.disableDenoType = disableDenoType;
    }
}
