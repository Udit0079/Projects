/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.LoanAcDetailsTable;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.constant.CbsConstant;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author admin
 */
public class LoanAcDetails extends BaseBean {

    private String userName;
    private String errorMsg;
    private String acctType;
    private String frmDt;
    private String toDt;
    private LoanReportFacadeRemote local;
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<SelectItem> acctTypeList;
    private Date date = new Date();
    List tempList = null;
    Vector tempVector = null;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private Boolean disableButton;

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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

    public Boolean getDisableButton() {
        return disableButton;
    }

    public void setDisableButton(Boolean disableButton) {
        this.disableButton = disableButton;
    }

    /**
     * Creates a new instance of LoanRecovery
     */
    public LoanAcDetails() {
        try {
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            frmDt = dmy.format(date);
            toDt = dmy.format(date);

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            acctTypeList = new ArrayList<SelectItem>();
            reportTypeList = new ArrayList<>();
            initData();
            reportTypeList.add(new SelectItem("0", "--Select--"));
            reportTypeList.add(new SelectItem("1", "Loan Account Details"));
            reportTypeList.add(new SelectItem("2", "RBI INSPECTION REPORT"));
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    private void initData() {
        try {
            tempList = local.getLoanTypeList();
            for (int i = 0; i < tempList.size(); i++) {
                tempVector = (Vector) tempList.get(i);
                acctTypeList.add(new SelectItem(tempVector.get(0), tempVector.get(0).toString()));
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
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

    public boolean validate() {
        try {
            if (!Validator.validateDate(toDt)) {
                errorMsg = "Please check to date";
                return false;
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        return true;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void excelDownload() throws ApplicationException {
        if (validate()) {
            try {
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                if (reportType.equalsIgnoreCase("1")) {
                    List<LoanAcDetailsTable> resultList = local.loanAcDetailsReport(acctType, (toDate[2] + toDate[1] + toDate[0]), branchCode);
                    String report = "Loan Accounts Detail Report";
                    FastReportBuilder fastReportBuilder = genrateDaynamicReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(resultList), fastReportBuilder, "Loan Accounts Detail Report");
                } else if (reportType.equalsIgnoreCase("2")) {
                    List<LoanMisCellaniousPojo> resultList = local.cbsLoanMisReport((branchCode.equalsIgnoreCase("90") ? "0A" : branchCode), acctType.equalsIgnoreCase("ALL") ? "0" : common.getAcNatureByAcType(acctType), acctType.equalsIgnoreCase("ALL") ? "0" : acctType, (toDate[2] + toDate[1] + toDate[0]), "A", 0, 999999999.00, "S", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0",
                            "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "ACTIVE", "0", "0", "Y", "0", "Y", "0", "Y", "Y", "0", "0", "0", "0");
                    String report = "";
                    FastReportBuilder fastReportBuilder = generateDynamicReportProfile();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(resultList), fastReportBuilder, "LOAN REPORT PROFILE ");
                }
            } catch (Exception e) {
                setErrorMsg(e.getLocalizedMessage());
            }
        }
    }

    public FastReportBuilder generateDynamicReportProfile() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);
            AbstractColumn srNo = ReportBean.getColumn("srNo", String.class, "Serial No.", 100);
            AbstractColumn brnDesc = ReportBean.getColumn("brnDesc", String.class, "Branch Wise", 100);
            AbstractColumn acTypeDesc = ReportBean.getColumn("acTypeDesc", String.class, "Account Type", 100);

            AbstractColumn custId = ReportBean.getColumn("custId", String.class, "ID Number", 90);
            AbstractColumn folioNo = ReportBean.getColumn("folioNo", String.class, "Membership No.", 90);
            AbstractColumn membershipDate = ReportBean.getColumn("membershipDate", String.class, "Date of Membership", 90);
            AbstractColumn shareBal = ReportBean.getColumn("shareBal", BigDecimal.class, "Share Balance", 80);
            AbstractColumn thriftBal = ReportBean.getColumn("thriftBal", BigDecimal.class, "Thrift Balance", 80);



            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account Number", 100);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Name of applicant", 200);
            AbstractColumn applicantCategoryDesc = ReportBean.getColumn("applicantCategoryDesc", String.class, "Applicant Category", 200);
            AbstractColumn fatherName = ReportBean.getColumn("fatherName", String.class, "Father's Name", 200);
            AbstractColumn panNo = ReportBean.getColumn("panNo", String.class, "Pan No.", 200);
            AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Date of Birth", 200);
            AbstractColumn address = ReportBean.getColumn("address", String.class, "Address", 200);
            AbstractColumn mobile = ReportBean.getColumn("mobile", String.class, "Mobile No", 200);
            AbstractColumn email = ReportBean.getColumn("email", String.class, "Email Id", 200);
            AbstractColumn sanctionDt = ReportBean.getColumn("sanctionDt", String.class, "Date of Sanction", 200);
            AbstractColumn sanctionAmt = ReportBean.getColumn("sanctionAmt", BigDecimal.class, "Sanction Amount", 200);
            AbstractColumn duration = ReportBean.getColumn("duration", Integer.class, "Period", 200);
            AbstractColumn roi = ReportBean.getColumn("roi", BigDecimal.class, "Rate of Interest", 200);
            AbstractColumn intTypeDesc = ReportBean.getColumn("intTypeDesc", String.class, "Interest Type", 200);
            AbstractColumn disbDt = ReportBean.getColumn("disbDt", String.class, "Date of Disbursement", 200);
            AbstractColumn disbAmt = ReportBean.getColumn("disbAmt", BigDecimal.class, "Amount of Disbursement", 200);
            AbstractColumn outstanding = ReportBean.getColumn("outstanding", BigDecimal.class, "Outstanding Balance", 200);
            AbstractColumn disbType = ReportBean.getColumn("disbType", String.class, "(Single or Multiple Dates)", 200);
            AbstractColumn loanExpiryDt = ReportBean.getColumn("loanExpiryDt", String.class, "Loan Expiry", 200);
            AbstractColumn purOfAdvDesc = ReportBean.getColumn("purOfAdvDesc", String.class, "Purpose", 200);
            AbstractColumn schemeCodeDesc = ReportBean.getColumn("schemeCodeDesc", String.class, "Scheme", 200);
            AbstractColumn sectorDesc = ReportBean.getColumn("sectorDesc", String.class, "Priority/ Non Priority", 200);
            AbstractColumn subSectorDesc = ReportBean.getColumn("subSectorDesc", String.class, "Category of Priority/ Non Priority", 200);
            AbstractColumn industryDesc = ReportBean.getColumn("industryDesc", String.class, "Industry", 200);
            AbstractColumn exposureDesc = ReportBean.getColumn("exposureDesc", String.class, "Real State Exposure", 200);
            AbstractColumn exposureCategoryDesc = ReportBean.getColumn("exposureCategoryDesc", String.class, "Residential/Commercial", 200);
            AbstractColumn securedDesc = ReportBean.getColumn("securedDesc", String.class, "Securd / Unsecured", 200);
            AbstractColumn security = ReportBean.getColumn("security", String.class, "Security", 300);
            AbstractColumn securityDesc = ReportBean.getColumn("securityDesc", String.class, "Security Description", 200);
            AbstractColumn securityValue = ReportBean.getColumn("securityValue", BigDecimal.class, "Value of Security", 200);
            AbstractColumn securityExpiry = ReportBean.getColumn("securityExpiry", String.class, "Security Expiry", 200);
            AbstractColumn insurance = ReportBean.getColumn("insurance", String.class, "Insurance", 200);
            AbstractColumn insuranceAmt = ReportBean.getColumn("insuranceAmt", BigDecimal.class, "Insurance Amount", 200);
            AbstractColumn insuranceExpiry = ReportBean.getColumn("insuranceExpiry", String.class, "Insurance Expiry", 200);
            AbstractColumn overdueAmt = ReportBean.getColumn("overdueAmt", BigDecimal.class, "Overdue", 200);
            AbstractColumn status = ReportBean.getColumn("status", String.class, "Status (Standard/Sub-standard/Doubtful)", 200);
            AbstractColumn categoryOptDesc = ReportBean.getColumn("categoryOptDesc", String.class, "Applicant Sub Categroy", 200);
            AbstractColumn typeOfAdvanceDesc = ReportBean.getColumn("typeOfAdvanceDesc", String.class, "Term Of Advance", 200);
            AbstractColumn renewDt = ReportBean.getColumn("renewDt", String.class, "CA Renewal Date", 0);
            if (acctType.equalsIgnoreCase("ALL")) {
                renewDt = ReportBean.getColumn("renewDt", String.class, "CA Renewal Date", 200);
            } else if (!(common.getAcNatureByAcType(acctType).equalsIgnoreCase(CbsConstant.TERM_LOAN) || common.getAcNatureByAcType(acctType).equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                renewDt = ReportBean.getColumn("renewDt", String.class, "CA Renewal Date", 200);
            }
            AbstractColumn emiAmt = ReportBean.getColumn("emiAmt", BigDecimal.class, "Emi", 80);

            fastReport.addColumn(srNo);
            width = width + srNo.getWidth();

            fastReport.addColumn(brnDesc);
            width = width + brnDesc.getWidth();

            fastReport.addColumn(acTypeDesc);
            width = width + acTypeDesc.getWidth();

            fastReport.addColumn(custId);
            width = width + custId.getWidth();

            fastReport.addColumn(folioNo);
            width = width + folioNo.getWidth();

            fastReport.addColumn(membershipDate);
            width = width + membershipDate.getWidth();

            fastReport.addColumn(shareBal);
            width = width + shareBal.getWidth();

            fastReport.addColumn(thriftBal);
            width = width + thriftBal.getWidth();

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(applicantCategoryDesc);
            width = width + applicantCategoryDesc.getWidth();

            fastReport.addColumn(fatherName);
            width = width + fatherName.getWidth();

            fastReport.addColumn(panNo);
            width = width + panNo.getWidth();

            fastReport.addColumn(dob);
            width = width + dob.getWidth();

            fastReport.addColumn(address);
            width = width + address.getWidth();

            fastReport.addColumn(mobile);
            width = width + mobile.getWidth();

            fastReport.addColumn(email);
            width = width + email.getWidth();

            fastReport.addColumn(sanctionDt);
            width = width + sanctionDt.getWidth();

            fastReport.addColumn(sanctionAmt);
            width = width + sanctionAmt.getWidth();

            fastReport.addColumn(duration);
            width = width + duration.getWidth();

            fastReport.addColumn(roi);
            width = width + roi.getWidth();

            fastReport.addColumn(intTypeDesc);
            width = width + intTypeDesc.getWidth();

            fastReport.addColumn(disbDt);
            width = width + disbDt.getWidth();

            fastReport.addColumn(disbAmt);
            width = width + disbAmt.getWidth();

            fastReport.addColumn(outstanding);
            width = width + outstanding.getWidth();

            fastReport.addColumn(disbType);
            width = width + disbType.getWidth();

            fastReport.addColumn(loanExpiryDt);
            width = width + loanExpiryDt.getWidth();

            fastReport.addColumn(purOfAdvDesc);
            width = width + purOfAdvDesc.getWidth();

            fastReport.addColumn(schemeCodeDesc);
            width = width + schemeCodeDesc.getWidth();

            fastReport.addColumn(sectorDesc);
            width = width + sectorDesc.getWidth();

            fastReport.addColumn(subSectorDesc);
            width = width + subSectorDesc.getWidth();

            fastReport.addColumn(industryDesc);
            width = width + industryDesc.getWidth();

            fastReport.addColumn(exposureDesc);
            width = width + exposureDesc.getWidth();

            fastReport.addColumn(exposureCategoryDesc);
            width = width + exposureCategoryDesc.getWidth();

            fastReport.addColumn(securedDesc);
            width = width + securedDesc.getWidth();

            fastReport.addColumn(security);
            width = width + security.getWidth();

            fastReport.addColumn(securityDesc);
            width = width + securityDesc.getWidth();

            fastReport.addColumn(securityValue);
            width = width + securityValue.getWidth();

            fastReport.addColumn(securityExpiry);
            width = width + securityExpiry.getWidth();

            fastReport.addColumn(insurance);
            width = width + insurance.getWidth();

            fastReport.addColumn(insuranceAmt);
            width = width + insuranceAmt.getWidth();

            fastReport.addColumn(insuranceExpiry);
            width = width + insuranceExpiry.getWidth();

            fastReport.addColumn(overdueAmt);
            width = width + overdueAmt.getWidth();

            fastReport.addColumn(status);
            width = width + status.getWidth();

            fastReport.addColumn(categoryOptDesc);
            width = width + categoryOptDesc.getWidth();

            fastReport.addColumn(typeOfAdvanceDesc);
            width = width + typeOfAdvanceDesc.getWidth();

            if (acctType.equalsIgnoreCase("ALL")) {
                fastReport.addColumn(renewDt);
                width = width + renewDt.getWidth();
            } else if (!(common.getAcNatureByAcType(acctType).equalsIgnoreCase(CbsConstant.TERM_LOAN) || common.getAcNatureByAcType(acctType).equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                fastReport.addColumn(renewDt);
                width = width + renewDt.getWidth();
            }

            fastReport.addColumn(emiAmt);
            width = width + emiAmt.getWidth();

            Page page = new Page(842, width, false);

            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("LOAN REPORT PROFILE");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateDaynamicReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn accountNumber = ReportBean.getColumn("accountNumber", String.class, "Account Number", 100);
            AbstractColumn custId = ReportBean.getColumn("custId", String.class, "Customer Id", 60);
            AbstractColumn name = ReportBean.getColumn("name", String.class, "Customer Name", 200);
            AbstractColumn brnCode = ReportBean.getColumn("brnCode", String.class, "Branch Code", 60);
            AbstractColumn productCode = ReportBean.getColumn("productCode", String.class, "Product Code", 60);
            AbstractColumn sanctionDt = ReportBean.getColumn("sanctionDt", String.class, "Sanction/Open date", 100);
            AbstractColumn roi = ReportBean.getColumn("roi", Float.class, "Rate Of Interest", 80);
            AbstractColumn amountSanc = ReportBean.getColumn("amountSanc", BigDecimal.class, "Sanction Limit", 60);
            AbstractColumn balance = ReportBean.getColumn("balance", BigDecimal.class, "Balance", 150);
            AbstractColumn sector = ReportBean.getColumn("sector", String.class, "Sector", 100);
            AbstractColumn subSector = ReportBean.getColumn("subSector", String.class, "Sub Sector", 100);
            AbstractColumn termOfAdvnc = ReportBean.getColumn("termOfAdvnc", String.class, "Term Of Advance", 100);
            AbstractColumn installAmt = ReportBean.getColumn("installAmt", BigDecimal.class, "EMI", 80);
            AbstractColumn status = ReportBean.getColumn("status", String.class, "Status", 90);
            AbstractColumn loanDuration = ReportBean.getColumn("loanDuration", Integer.class, "Loan Duration", 60);
            AbstractColumn odLimit = ReportBean.getColumn("odLimit", BigDecimal.class, "OD Limit", 60);
            AbstractColumn category = ReportBean.getColumn("category", String.class, "Category", 80);
            AbstractColumn categroyOpt = ReportBean.getColumn("categroyOpt", String.class, "Category", 80);
            AbstractColumn lastCrDt = ReportBean.getColumn("lastCrDt", String.class, "Last Credit Date", 80);
            AbstractColumn lastCrAmt = ReportBean.getColumn("lastCrAmt", BigDecimal.class, "Last Credit Amount", 80);
            AbstractColumn docExpDt = ReportBean.getColumn("docExpDt", String.class, "Document Expiry Date", 80);
            AbstractColumn npaOir = ReportBean.getColumn("npaOir", BigDecimal.class, "OIR", 80);
            AbstractColumn emiStrtDt = ReportBean.getColumn("emiStrtDt", String.class, "EMI Start Date", 80);
            AbstractColumn expiryDt = ReportBean.getColumn("expiryDt", String.class, "CA:Renewal Dt or TL/DL:Expiry Dt", 80);
            AbstractColumn jointDetails = ReportBean.getColumn("jointDetails", String.class, "Co-Borrower/JointDetails", 200);

            fastReport.addColumn(accountNumber);
            width = width + accountNumber.getWidth();

            fastReport.addColumn(custId);
            width = width + custId.getWidth();

            fastReport.addColumn(name);
            width = width + name.getWidth();

            fastReport.addColumn(brnCode);
            width = width + brnCode.getWidth();

            fastReport.addColumn(productCode);
            width = width + productCode.getWidth();

            fastReport.addColumn(sanctionDt);
            width = width + sanctionDt.getWidth();

            fastReport.addColumn(roi);
            width = width + roi.getWidth();

            fastReport.addColumn(amountSanc);
            width = width + amountSanc.getWidth();

            fastReport.addColumn(balance);
            width = width + balance.getWidth();

            fastReport.addColumn(sector);
            width = width + sector.getWidth();

            fastReport.addColumn(subSector);
            width = width + subSector.getWidth();

            fastReport.addColumn(termOfAdvnc);
            width = width + termOfAdvnc.getWidth();

            fastReport.addColumn(installAmt);
            width = width + installAmt.getWidth();

            fastReport.addColumn(status);
            width = width + status.getWidth();

            fastReport.addColumn(loanDuration);
            width = width + loanDuration.getWidth();

            fastReport.addColumn(odLimit);
            width = width + odLimit.getWidth();

            fastReport.addColumn(category);
            width = width + category.getWidth();

            fastReport.addColumn(categroyOpt);
            width = width + categroyOpt.getWidth();

            fastReport.addColumn(lastCrDt);
            width = width + lastCrDt.getWidth();

            fastReport.addColumn(lastCrAmt);
            width = width + lastCrAmt.getWidth();

            fastReport.addColumn(docExpDt);
            width = width + docExpDt.getWidth();

            fastReport.addColumn(npaOir);
            width = width + npaOir.getWidth();

            fastReport.addColumn(emiStrtDt);
            width = width + emiStrtDt.getWidth();

            fastReport.addColumn(expiryDt);
            width = width + expiryDt.getWidth();
            
            fastReport.addColumn(jointDetails);
            width = width + jointDetails.getWidth();

            Page page = new Page(1042, width, false);
//          fastReport.setPrintBackgroundOnOddRows(true);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Loan Accounts Detail Report");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public void onReportAction() {
        setErrorMsg("");
        try {
            if (reportType.equalsIgnoreCase("1")) {
                this.disableButton = false;
            } else if (reportType.equalsIgnoreCase("2")) {
                this.disableButton = true;
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public String viewReport() throws ApplicationException {
        if (validate()) {
            try {
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                if (reportType.equalsIgnoreCase("1")) {
                    List<LoanAcDetailsTable> resultList = local.loanAcDetailsReport(acctType, (toDate[2] + toDate[1] + toDate[0]), branchCode);
                    if (!resultList.isEmpty()) {
                        String report = "Loan Accounts Detail Report";
                        Map fillParams = new HashMap();
                        String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                        fillParams.put("pReportName", report);
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pReportDate", toDt);
                        fillParams.put("pbankName", s[0]);
                        fillParams.put("pbankAddress", s[1]);
                        fillParams.put("pFROM", frmDt);
                        fillParams.put("pTo", toDt);
                        new ReportBean().jasperReport("LoanAcDetailsReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                        return "report";
                    } else {
                        errorMsg = "No data exists !";
                        return null;
                    }
                } else {
                    List<LoanMisCellaniousPojo> resultList = local.cbsLoanMisReport((branchCode.equalsIgnoreCase("90") ? "0A" : branchCode), acctType.equalsIgnoreCase("ALL") ? "0" : common.getAcNatureByAcType(acctType), acctType.equalsIgnoreCase("ALL") ? "0" : acctType, (toDate[2] + toDate[1] + toDate[0]), "A", 0, 999999999.00, "S", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0",
                            "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "0", "Y", "0", "Y", "0", "Y", "Y", "0", "0", "0", "0");
                    if (!resultList.isEmpty()) {
                        String report = "Loan Accounts Detail Report";
                        Map fillParams = new HashMap();
                        String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                        fillParams.put("pReportName", "REPORT PROFILE OF LOAN ACCOUNTS");
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pReportDate", toDt);
                        fillParams.put("pbankName", s[0]);
                        fillParams.put("pbankAddress", s[1]);
                        fillParams.put("pFROM", frmDt);
                        fillParams.put("pTo", toDt);
                        new ReportBean().jasperReport("LoanReportProfile", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                        return "report";
                    } else {
                        errorMsg = "No data exists !";
                        return null;
                    }
                }
            } catch (Exception e) {
                setErrorMsg(e.getLocalizedMessage());
            }
        }
        return null;
    }

    public String viewPdfReport() throws ApplicationException {
        if (validate()) {
            try {
                String fromDt[] = frmDt.split("/");
                String toDate[] = toDt.split("/");
                if (reportType.equalsIgnoreCase("1")) {
                    List<LoanAcDetailsTable> resultList = local.loanAcDetailsReport(acctType, (toDate[2] + toDate[1] + toDate[0]), branchCode);
                    if (!resultList.isEmpty()) {
                        String report = "Loan Accounts Detail Report";
                        Map fillParams = new HashMap();
                        String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                        fillParams.put("pReportName", report);
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pReportDate", toDt);
                        fillParams.put("pbankName", s[0]);
                        fillParams.put("pbankAddress", s[1]);
                        fillParams.put("pFROM", frmDt);
                        fillParams.put("pTo", toDt);
                        new ReportBean().openPdf("Loan Accounts Detail Report_" + ymd.format(dmy.parse(getTodayDate())), "LoanAcDetailsReport", new JRBeanCollectionDataSource(resultList), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getRequest().getSession();
                        httpSession.setAttribute("ReportName", report);
                    } else {
                        errorMsg = "No data exists !";
                        return null;
                    }
                } else {
                    List<LoanMisCellaniousPojo> resultList = local.cbsLoanMisReport((branchCode.equalsIgnoreCase("90") ? "0A" : branchCode), acctType.equalsIgnoreCase("ALL") ? "0" : common.getAcNatureByAcType(acctType), acctType.equalsIgnoreCase("ALL") ? "0" : acctType, (toDate[2] + toDate[1] + toDate[0]), "A", 0, 999999999.00, "S", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0",
                            "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "Active", "0", "0", "Y", "0", "Y", "0", "Y", "Y", "0", "0", "0", "0");
                    if (!resultList.isEmpty()) {
                        String report = "Loan Accounts Detail Report";
                        Map fillParams = new HashMap();
                        String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                        fillParams.put("pReportName", "REPORT PROFILE OF LOAN ACCOUNTS");
                        fillParams.put("pPrintedBy", getUserName());
                        fillParams.put("pReportDate", toDt);
                        fillParams.put("pbankName", s[0]);
                        fillParams.put("pbankAddress", s[1]);
                        fillParams.put("pFROM", frmDt);
                        fillParams.put("pTo", toDt);
                        new ReportBean().openPdf("REPORT PROFILE OF LOAN ACCOUNTS REPORT_" + ymd.format(dmy.parse(getTodayDate())), "LoanReportProfile", new JRBeanCollectionDataSource(resultList), fillParams);
                        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                        HttpSession httpSession = getRequest().getSession();
                        httpSession.setAttribute("ReportName", report);
                    } else {
                        errorMsg = "No data exists !";
                        return null;
                    }
                }
            } catch (Exception e) {
                setErrorMsg(e.getLocalizedMessage());
            }
        }
        return null;
    }

    public void refresh() {
        try {
            errorMsg = "";
            frmDt = dmy.format(new Date());
            toDt = dmy.format(new Date());
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        try {
            refresh();
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        return "case1";
    }
}
