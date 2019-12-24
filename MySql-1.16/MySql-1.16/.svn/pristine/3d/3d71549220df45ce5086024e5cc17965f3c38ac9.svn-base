/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import ar.com.fdvs.dj.domain.Style;
import com.cbs.facade.report.ExperionLoanFacadeRemote;
import com.cbs.dto.report.ExperionPojo;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.CibilASPoJo;
import com.cbs.dto.report.CibilBSPoJo;
import com.cbs.dto.report.CibilCDPoJo;
import com.cbs.dto.report.CibilCRPoJo;
import com.cbs.dto.report.CibilComPoJo;
import com.cbs.dto.report.CibilGSPoJo;
import com.cbs.dto.report.CibilHDPoJo;
import com.cbs.dto.report.CibilRSPoJo;
import com.cbs.dto.report.CibilSSPoJo;
import com.cbs.dto.report.CibilTSPoJo;
import com.cbs.dto.report.EquifaxComercialPoJo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CibilReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.EquifaxReportFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class ExperionLoanReport extends BaseBean {

    String message;
    String memberCode;
    Date fromCalDate;
    Date toCalDate;
    private String cibilCompany;
    private String reportName;
    private final String jndiHomeName = "ExperionLoanFacade";
    private ExperionLoanFacadeRemote exprionBeanRemote = null;
    private final String jndiCibilHomeName = "CibilReportFacade";
    private CibilReportFacadeRemote cibilBeanRemote = null;
    private EquifaxReportFacadeRemote equifaxBeanRemote = null;
    private OtherReportFacadeRemote facadeRemote;
    private CommonReportMethodsRemote commonBeanRemote;
    List<SelectItem> ddCibilCompanyList = new ArrayList<SelectItem>();
    List<ExperionPojo> expDataList = new ArrayList<ExperionPojo>();
    List<EquifaxComercialPoJo> comDataList = new ArrayList<EquifaxComercialPoJo>();
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmmy = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat hms = new SimpleDateFormat("hhMMss");
    String cibilParameter;
    String cibilVerNo;
    private List<String> txtFileNameList;
    private String fileName;
    private boolean saveLink;
    private String cibilFile;
    private boolean disableSave;
    Date dt = new Date();
    /*For New format as per Comercial and Consumer*/
    private String reportType;
    List<SelectItem> reportTypeList = new ArrayList<SelectItem>();
    CibilComPoJo cibilCom = new CibilComPoJo();
    private String repType;
    private List<SelectItem> repTypeList;
    private String displayRepType = "none";

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

    public String getDisplayRepType() {
        return displayRepType;
    }

    public void setDisplayRepType(String displayRepType) {
        this.displayRepType = displayRepType;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Date getFromCalDate() {
        return fromCalDate;
    }

    public void setFromCalDate(Date fromCalDate) {
        this.fromCalDate = fromCalDate;
    }

    public Date getToCalDate() {
        return toCalDate;
    }

    public void setToCalDate(Date toCalDate) {
        this.toCalDate = toCalDate;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCibilCompany() {
        return cibilCompany;
    }

    public void setCibilCompany(String cibilCompany) {
        this.cibilCompany = cibilCompany;
    }

    public List<SelectItem> getDdCibilCompanyList() {
        return ddCibilCompanyList;
    }

    public void setDdCibilCompanyList(List<SelectItem> ddCibilCompanyList) {
        this.ddCibilCompanyList = ddCibilCompanyList;
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

    public List<String> getTxtFileNameList() {
        return txtFileNameList;
    }

    public void setTxtFileNameList(List<String> txtFileNameList) {
        this.txtFileNameList = txtFileNameList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isSaveLink() {
        return saveLink;
    }

    public void setSaveLink(boolean saveLink) {
        this.saveLink = saveLink;
    }

    public String getCibilFile() {
        return cibilFile;
    }

    public void setCibilFile(String cibilFile) {
        this.cibilFile = cibilFile;
    }
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ExperionLoanReport() {
        try {
            refreshForm();
            facadeRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            exprionBeanRemote = (ExperionLoanFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            cibilBeanRemote = (CibilReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiCibilHomeName);
            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            equifaxBeanRemote = (EquifaxReportFacadeRemote) ServiceLocator.getInstance().lookup("EquifaxReportFacade");
            List acNatureList = commonBeanRemote.getCibilCompanyList();
            cibilVerNo = commonBeanRemote.getCibilVerNo();
            ddCibilCompanyList = new ArrayList<SelectItem>();
            ddCibilCompanyList.add(new SelectItem("0", "--Select--"));
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("0", "--Select--"));
            reportTypeList.add(new SelectItem("1", "Consumer"));
            reportTypeList.add(new SelectItem("2", "Comercial"));
            repTypeList = new ArrayList();
            repTypeList.add(new SelectItem("0", "--Select--"));
            repTypeList.add(new SelectItem("1", "2016 FORMAT"));
            repTypeList.add(new SelectItem("2", "2018 FORMAT"));
            for (Object element : acNatureList) {
                Vector vector = (Vector) element;
                ddCibilCompanyList.add(new SelectItem(vector.get(0).toString(), vector.get(0).toString()));
            }
            txtFileNameList = new ArrayList<String>();
            this.setDisableSave(true);
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void disableSaveBnt() {
        setMessage("");
        if (cibilCompany.equalsIgnoreCase("CIBIL") && (reportType.equalsIgnoreCase("1") || reportType.equalsIgnoreCase("2"))) {
            this.disableSave = false;
        } else if (cibilCompany.equalsIgnoreCase("CRIF") && (reportType.equalsIgnoreCase("1") || reportType.equalsIgnoreCase("2"))) {
            this.disableSave = false;
        } else if (cibilCompany.equalsIgnoreCase("Equifax") && (reportType.equalsIgnoreCase("1"))) {
            this.disableSave = false;
        } else {
            this.disableSave = true;
        }

        if (cibilCompany.equalsIgnoreCase("CIBIL") && reportType.equalsIgnoreCase("2")) {
            this.displayRepType = "";
        } else {
            this.displayRepType = "none";
        }

    }

    public boolean validate() {
        try {
            setMessage("");
            if (this.memberCode == null || this.memberCode.equalsIgnoreCase("")) {
                setMessage("Please enter the member code.");
                return false;
            }
            if (!Validator.validateDate(dmyFormat.format(fromCalDate))) {
                this.setMessage("Please check from date");
                return false;
            }
            if (!Validator.validateDate(dmyFormat.format(toCalDate))) {
                this.setMessage("Please check to date");
                return false;
            }
            if (fromCalDate.after(toCalDate)) {
                this.setMessage("From date should be less then To date");
                return false;
            }
            if (toCalDate.after(dt)) {
                this.setMessage("To date should be less or Equal to Current date");
                return false;
            }
            if (this.reportType.equalsIgnoreCase("") || this.reportType.equalsIgnoreCase("0")) {
                this.setMessage("Please Select The Report Type.");
                return false;
            }
//            cibilParameter = facadeRemote.getCodeFromCbsParameterInfo("CIBIL");
            cibilParameter = this.getCibilCompany();
            if (this.cibilParameter.equalsIgnoreCase("0")) {
                setMessage("Please select the Company Name.");
                return false;
            }

            if (this.cibilParameter.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2")) {
                if (repType.equalsIgnoreCase("0")) {
                    setMessage("Please Select Report Format.");
                    return false;
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return true;
    }

    public void downloadFile() {
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String ctxPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            String dirName = "";
            String osName = System.getProperty("os.name");
            if (osName.equalsIgnoreCase("Linux")) {
                dirName = "/install/CibilFile/";
            } else {
                dirName = "C:\\CibilFile\\";
            }
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + fileName);
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void cibilTextGen() {
        try {
            this.getMemberCode();
            this.setSaveLink(false);
            txtFileNameList = new ArrayList<String>();
            File dir = null;
            if (System.getProperty("os.name").equalsIgnoreCase("Linux")) {
                dir = new File("/install/CibilFile/");
            } else {
                dir = new File("C:/CibilFile/");
            }
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String cibilTextFile = "";
            if (validate()) {
                if (reportType.equalsIgnoreCase("1")) {
                    cibilTextFile = cibilBeanRemote.cibilReportConsumerText(this.memberCode, ymd.format(fromCalDate), ymd.format(toCalDate), getTodayDate(), this.reportType, dir.getCanonicalPath(), memberCode + "-" + dmmy.format(toCalDate) + "-" + new SimpleDateFormat("HHmmss").format(new Date()), cibilParameter);
                } else {
                    cibilTextFile = cibilBeanRemote.cibilComercialDelimitedFormat(memberCode, ymd.format(fromCalDate), ymd.format(toCalDate), getTodayDate(), this.reportType, cibilParameter, dir.getCanonicalPath(), memberCode + "-" + ymd.format(toCalDate) + "-" + new SimpleDateFormat("HHmmss").format(new Date()) + "-01", this.repType);
                }

                cibilFile = cibilTextFile;
                txtFileNameList.add(cibilFile);
                this.setSaveLink(true);
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void btnPdfAction() {
        try {
            if (validate()) {
                cibilCom = new CibilComPoJo();
                if (cibilParameter.equalsIgnoreCase("Experian")) {
                    if (this.reportType.equalsIgnoreCase("1")) {
                        expDataList = exprionBeanRemote.experionReport(memberCode, ymd.format(fromCalDate), ymd.format(toCalDate), getTodayDate(), cibilParameter);
                        FastReportBuilder fastReportBuilder = genrateDaynamicReportExp();
                        new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(expDataList), fastReportBuilder, "EXPERIAN_REPORT_CONSUMER_" + ymd.format(fromCalDate) + "_" + ymd.format(toCalDate));
                    } else if (this.reportType.equalsIgnoreCase("2")) {
                        comDataList = exprionBeanRemote.comercialExperianReport(memberCode, ymd.format(fromCalDate), ymd.format(toCalDate), getTodayDate(), this.reportType);
                        FastReportBuilder fastReportBuilder = genrateDaynamicReportExperianComercial();
                        new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(comDataList), fastReportBuilder, "EXPERIAN_REPORT_Comercial_" + ymd.format(fromCalDate) + "_" + ymd.format(toCalDate));
                    }
                } else if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                    if (this.reportType.equalsIgnoreCase("1")) {
                        expDataList = cibilBeanRemote.cibilReport(memberCode, ymd.format(fromCalDate), ymd.format(toCalDate), getTodayDate(), this.reportType, cibilParameter);
                        if (cibilVerNo.equalsIgnoreCase("3.0")) {
                            FastReportBuilder fastReportBuilder = genrateDaynamicReportCibil3();
                            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(expDataList), fastReportBuilder, memberCode + "-" + dmmy.format(toCalDate) + "-" + new SimpleDateFormat("HHmmss").format(new Date()));
                        } else {
                            FastReportBuilder fastReportBuilder = genrateDaynamicReportCibil();
                            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(expDataList), fastReportBuilder, memberCode + "-" + dmmy.format(toCalDate) + "-" + new SimpleDateFormat("HHmmss").format(new Date()));
                        }
                    } else if (this.reportType.equalsIgnoreCase("2")) {

                        cibilCom = cibilBeanRemote.cibilComercial(memberCode, ymd.format(fromCalDate), ymd.format(toCalDate), getTodayDate(), this.reportType, cibilParameter);
                        cibirCrifComrReport();
                    }

                } else if (cibilParameter.equalsIgnoreCase("CRIF")) {
                    if (this.reportType.equalsIgnoreCase("1")) {
                        expDataList = cibilBeanRemote.cibilReport(memberCode, ymd.format(fromCalDate), ymd.format(toCalDate), getTodayDate(), this.reportType, cibilParameter);
                        FastReportBuilder fastReportBuilder = genrateDynamicCrif();
                        new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(expDataList), fastReportBuilder, memberCode + "-" + dmmy.format(toCalDate) + "-" + new SimpleDateFormat("HHmmss").format(new Date()));
                    } else if (this.reportType.equalsIgnoreCase("2")) {
                        cibilCom = cibilBeanRemote.cibilComercial(memberCode, ymd.format(fromCalDate), ymd.format(toCalDate), getTodayDate(), this.reportType, cibilParameter);
                        cibirCrifComrReport();
                    }
                } else if (cibilParameter.equalsIgnoreCase("Equifax")) {
                    if (this.reportType.equalsIgnoreCase("1")) {
                        expDataList = cibilBeanRemote.cibilReport(memberCode, ymd.format(fromCalDate), ymd.format(toCalDate), getTodayDate(), this.reportType, cibilParameter);
                        FastReportBuilder fastReportBuilder = genrateDaynamicReportCibil3();
                        new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(expDataList), fastReportBuilder, "CONSUMER_REPORT_EQUIFAX_" + ymd.format(fromCalDate) + "_" + ymd.format(toCalDate));
                    } else if (this.reportType.equalsIgnoreCase("2")) {
                        comDataList = equifaxBeanRemote.comercialEquifaxReport(memberCode, ymd.format(fromCalDate), ymd.format(toCalDate), getTodayDate(), this.reportType);
                        FastReportBuilder fastReportBuilder = genrateDaynamicReportComercial();
                        new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(comDataList), fastReportBuilder, "COMERCIAL_REPORT_EQUIFAX_" + ymd.format(fromCalDate) + "_" + ymd.format(toCalDate));
                        /*Old Format
                         //                        expDataList = exprionBeanRemote.cicReport(memberCode,ymd.format(fromCalDate),ymd.format(toCalDate),getTodayDate());
                         //                        FastReportBuilder fastReportBuilder = genrateDaynamicReportCic();
                         //                        new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(expDataList), fastReportBuilder,"CIC_Report_"+ymd.format(fromCalDate)+"_"+ymd.format(toCalDate));
                         */
                    }
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());

        }
    }

    public FastReportBuilder genrateDynamicCrif() {
        FastReportBuilder fast = new FastReportBuilder();
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        AbstractColumn custNameF1 = ReportBean.getColumn("custNameF1", String.class, "Consumer Name1 (First Name)", 150);
        AbstractColumn custNameF2 = ReportBean.getColumn("custNameF2", String.class, "Consumer Name2 (Middle Name)", 150);
        AbstractColumn custNameF3 = ReportBean.getColumn("custNameF3", String.class, "Consumer Name3 (Last Name)", 150);
        AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Date of Birth", 80);
        AbstractColumn gender = ReportBean.getColumn("gender", String.class, "Gender", 60);
        AbstractColumn incomeTaxId = ReportBean.getColumn("incomeTaxId", String.class, "Income Tax ID Number(PAN)", 100);
        AbstractColumn passportNo = ReportBean.getColumn("passportNo", String.class, "Passport Number", 100);
        AbstractColumn voterIdNo = ReportBean.getColumn("voterIdNo", String.class, "Voter ID Number", 100);
        AbstractColumn driLicenseNo = ReportBean.getColumn("driLicenseNo", String.class, "Driver’s License Number", 100);
        AbstractColumn rationCardNo = ReportBean.getColumn("rationCardNo", String.class, "Ration Card Number", 100);
        AbstractColumn universalIdNo = ReportBean.getColumn("universalIdNo", String.class, "Universal ID Number", 100);
        AbstractColumn addId1 = ReportBean.getColumn("addId1", String.class, "Additional ID #1", 100);
        AbstractColumn telephone = ReportBean.getColumn("telephone", String.class, "Telephone Number 1", 100);
        AbstractColumn telephonetype1 = ReportBean.getColumn("telephone", String.class, "Telephone Number 1 Type", 100);
        AbstractColumn telNoResidence = ReportBean.getColumn("telNoResidence", String.class, "Telephone Number 2", 100);
        AbstractColumn telephonetype2 = ReportBean.getColumn("telephone", String.class, "Telephone Number 2 Type", 100);
        AbstractColumn telNoOffice = ReportBean.getColumn("telNoOffice", String.class, "Telephone Number 3", 100);
        AbstractColumn telephonetype3 = ReportBean.getColumn("telephone", String.class, "Telephone Number 3 Type", 100);
        AbstractColumn extOffice = ReportBean.getColumn("extOffice", String.class, "Telephone Extension", 100);
        AbstractColumn emailId1 = ReportBean.getColumn("emailId1", String.class, "E-Mail ID", 100);
        AbstractColumn addressLine1 = ReportBean.getColumn("addressLine1", String.class, "Consumer Address 1", 220);
        AbstractColumn addressLine2 = ReportBean.getColumn("addressLine2", String.class, "City/town1", 150);
        AbstractColumn addressLine3 = ReportBean.getColumn("addressLine3", String.class, "District 1", 150);
        AbstractColumn stateCode = ReportBean.getColumn("stateCode", String.class, "State Code 1", 80);
        AbstractColumn pinCode = ReportBean.getColumn("pinCode", String.class, "PIN Code 1", 80);
        AbstractColumn addressCategory1 = ReportBean.getColumn("addressCategory1", String.class, "Address Category 1", 100);
        AbstractColumn residenceCode1 = ReportBean.getColumn("residenceCode1", String.class, "Residence Code 1", 100);
        AbstractColumn address2 = ReportBean.getColumn("address2", String.class, "Consumer Address 2", 200);
        AbstractColumn borcity2 = ReportBean.getColumn("borcity2", String.class, "City/town2", 80);
        AbstractColumn borDistrict2 = ReportBean.getColumn("borDistrict2", String.class, "District2", 80);
        AbstractColumn stateCode2 = ReportBean.getColumn("stateCode2", String.class, "State Code 2", 100);
        AbstractColumn pinCode2 = ReportBean.getColumn("pinCode2", String.class, "PIN Code 2", 100);
        AbstractColumn addressCategory2 = ReportBean.getColumn("addressCategory2", String.class, "Address Category 2", 200);
        AbstractColumn residenceCode2 = ReportBean.getColumn("residenceCode2", String.class, "Residence Code 2", 100);
        AbstractColumn reportingMemCode = ReportBean.getColumn("reportingMemCode", String.class, "Current/New Reporting Member Code", 150);
        AbstractColumn memberShortName = ReportBean.getColumn("memberShortName", String.class, "Current/New Member Short Name", 150);
        AbstractColumn currentAcno = ReportBean.getColumn("currentAcno", String.class, "Current/New Account Number", 150);
        AbstractColumn accountType = ReportBean.getColumn("accountType", String.class, "Account Type", 80);
        AbstractColumn accountHolderTypeCode = ReportBean.getColumn("accountHolderTypeCode", String.class, "Ownership Indicator", 100);
        AbstractColumn disbursDt = ReportBean.getColumn("disbursDt", String.class, "Date Opened/ Disbursed", 100);
        AbstractColumn lastPaymentdate = ReportBean.getColumn("lastPaymentdate", String.class, "Date of Last Payment", 100);
        AbstractColumn dateClose = ReportBean.getColumn("dateClose", String.class, "Date Closed", 100);
        AbstractColumn reportDate = ReportBean.getColumn("reportDate", String.class, "Date Reported and Certified", 100);
        AbstractColumn sanctAmt = ReportBean.getColumn("sanctAmt", String.class, "High Credit/ Sanctioned Amount", 150);
        AbstractColumn currentBalance = ReportBean.getColumn("currentBalance", String.class, "Current Balance", 150);
        AbstractColumn amountOverDue = ReportBean.getColumn("amountOverDue", String.class, "Amount Overdue", 150);
        AbstractColumn noOfDaysPast = ReportBean.getColumn("noOfDaysPast", String.class, "Number of Days Past Due", 150);
        AbstractColumn oldReportingMemCode = ReportBean.getColumn("oldReportingMemCode", String.class, "Old Reporting Member Code", 150);
        AbstractColumn oldMemShortName = ReportBean.getColumn("oldMemShortName", String.class, "Old Member Short Name", 150);
        AbstractColumn oldAccountNo = ReportBean.getColumn("oldAccountNo", String.class, "Old Account Number", 100);
        AbstractColumn oldAccType = ReportBean.getColumn("oldAccType", String.class, "Old Account Type", 100);
        AbstractColumn oldOwnershipIndicator = ReportBean.getColumn("oldOwnershipIndicator", String.class, "Old Ownership Indicator", 100);
        AbstractColumn writtenOffStatus = ReportBean.getColumn("writtenOffStatus", String.class, "Suit Filed/ Wilful Default", 150);
        AbstractColumn writtenSettledStatus = ReportBean.getColumn("writtenSettledStatus", String.class, "Written-off and Settled Status", 100);
        AbstractColumn assetClassif = ReportBean.getColumn("assetClassif", String.class, "Asset Classification", 150);
        AbstractColumn valueOfCollateral = ReportBean.getColumn("valueOfCollateral", String.class, "Value of Collateral", 100);
        AbstractColumn typeOfCollateral = ReportBean.getColumn("typeOfCollateral", String.class, "Type of Collateral", 100);
        AbstractColumn creditLimit = ReportBean.getColumn("creditLimit", String.class, "Credit Limit", 100);
        AbstractColumn cashLimit = ReportBean.getColumn("cashLimit", String.class, "Cash Limit", 100);
        AbstractColumn rateOfInterest = ReportBean.getColumn("rateOfInterest", String.class, "Rate Of Interest", 100);
        AbstractColumn repaymentTenure = ReportBean.getColumn("repaymentTenure", String.class, "Repayment Tenure", 100);
        AbstractColumn emiAmt = ReportBean.getColumn("emiAmt", String.class, "EMI Amount", 100);
        AbstractColumn writtenOffAmountTotal = ReportBean.getColumn("writtenOffAmountTotal", String.class, "Written-off Amount (Total)", 100);
        AbstractColumn writtenOffPrincipalAmount = ReportBean.getColumn("writtenOffPrincipalAmount", String.class, "Written-off Amount (Principal)", 100);
        AbstractColumn settlementAmt = ReportBean.getColumn("settlementAmt", String.class, "Settlement Amount", 100);
        AbstractColumn paymentFrequency = ReportBean.getColumn("paymentFrequency", String.class, "Payment Frequency", 100);
        AbstractColumn actualPaymentAmt = ReportBean.getColumn("actualPaymentAmt", String.class, "Actual Payment Amount", 100);
        AbstractColumn occupationCode = ReportBean.getColumn("occupationCode", String.class, "Occupation Code", 100);
        AbstractColumn income = ReportBean.getColumn("income", String.class, "Income", 100);
        AbstractColumn netGrossIncomeIndicator = ReportBean.getColumn("netGrossIncomeIndicator", String.class, "Net/Gross Income Indicator", 100);
        AbstractColumn monthlyAnnualIncomeIndicator = ReportBean.getColumn("monthlyAnnualIncomeIndicator", String.class, "Monthly/Annual Income Indicator", 100);

        fastReport.addColumn(custNameF1);
        fastReport.addColumn(custNameF2);
        fastReport.addColumn(custNameF3);
        fastReport.addColumn(dob);
        fastReport.addColumn(gender);
        fastReport.addColumn(incomeTaxId);
        fastReport.addColumn(passportNo);
        fastReport.addColumn(voterIdNo);
        fastReport.addColumn(driLicenseNo);
        fastReport.addColumn(rationCardNo);
        fastReport.addColumn(universalIdNo);
        fastReport.addColumn(addId1);
        fastReport.addColumn(telephone);
        fastReport.addColumn(telephonetype1);
        fastReport.addColumn(telNoResidence);
        fastReport.addColumn(telephonetype2);
        fastReport.addColumn(telNoOffice);
        fastReport.addColumn(telephonetype3);
        fastReport.addColumn(extOffice);
        fastReport.addColumn(emailId1);
        fastReport.addColumn(addressLine1);
        fastReport.addColumn(addressLine2);
        fastReport.addColumn(addressLine3);
        fastReport.addColumn(stateCode);
        fastReport.addColumn(pinCode);
        fastReport.addColumn(addressCategory1);
        fastReport.addColumn(residenceCode1);
        fastReport.addColumn(address2);
        fastReport.addColumn(borcity2);
        fastReport.addColumn(borDistrict2);
        fastReport.addColumn(stateCode2);
        fastReport.addColumn(pinCode2);
        fastReport.addColumn(addressCategory2);
        fastReport.addColumn(residenceCode2);
        fastReport.addColumn(reportingMemCode);
        fastReport.addColumn(memberShortName);
        fastReport.addColumn(currentAcno);
        fastReport.addColumn(accountType);
        fastReport.addColumn(accountHolderTypeCode);
        fastReport.addColumn(disbursDt);
        fastReport.addColumn(lastPaymentdate);
        fastReport.addColumn(dateClose);
        fastReport.addColumn(reportDate);
        fastReport.addColumn(sanctAmt);
        sanctAmt.setStyle(style);
        fastReport.addColumn(currentBalance);
        fastReport.addColumn(amountOverDue);
        amountOverDue.setStyle(style);
        fastReport.addColumn(noOfDaysPast);
        fastReport.addColumn(oldReportingMemCode);
        fastReport.addColumn(oldMemShortName);
        fastReport.addColumn(oldAccountNo);
        fastReport.addColumn(oldAccType);
        fastReport.addColumn(oldOwnershipIndicator);
        fastReport.addColumn(writtenOffStatus);
        fastReport.addColumn(writtenSettledStatus);
        fastReport.addColumn(assetClassif);
        fastReport.addColumn(valueOfCollateral);
        fastReport.addColumn(typeOfCollateral);
        fastReport.addColumn(creditLimit);
        fastReport.addColumn(cashLimit);
        fastReport.addColumn(rateOfInterest);
        fastReport.addColumn(repaymentTenure);
        fastReport.addColumn(emiAmt);
        fastReport.addColumn(writtenOffAmountTotal);
        fastReport.addColumn(writtenOffPrincipalAmount);
        fastReport.addColumn(settlementAmt);
        fastReport.addColumn(paymentFrequency);
        fastReport.addColumn(actualPaymentAmt);
        fastReport.addColumn(occupationCode);
        fastReport.addColumn(income);
        fastReport.addColumn(netGrossIncomeIndicator);
        fastReport.addColumn(monthlyAnnualIncomeIndicator);

        width = reportDate.getWidth() + custNameF1.getWidth() + custNameF2.getWidth() + custNameF3.getWidth() + dob.getWidth()
                + gender.getWidth() + incomeTaxId.getWidth() + passportNo.getWidth() + voterIdNo.getWidth() + driLicenseNo.getWidth()
                + rationCardNo.getWidth() + universalIdNo.getWidth() + addId1.getWidth() + telephone.getWidth() + telNoResidence.getWidth()
                + telNoOffice.getWidth() + extOffice.getWidth() + emailId1.getWidth() + addressLine1.getWidth() + addressLine2.getWidth()
                + addressLine3.getWidth() + stateCode.getWidth() + pinCode.getWidth() + addressCategory1.getWidth() + residenceCode1.getWidth() + address2.getWidth()
                + stateCode2.getWidth() + pinCode2.getWidth() + addressCategory2.getWidth() + residenceCode2.getWidth() + reportingMemCode.getWidth()
                + memberShortName.getWidth() + currentAcno.getWidth() + accountType.getWidth() + accountHolderTypeCode.getWidth()
                + disbursDt.getWidth() + lastPaymentdate.getWidth() + dateClose.getWidth() + sanctAmt.getWidth() + currentBalance.getWidth()
                + amountOverDue.getWidth() + noOfDaysPast.getWidth() + oldReportingMemCode.getWidth() + oldMemShortName.getWidth()
                + oldAccountNo.getWidth() + oldAccType.getWidth() + oldOwnershipIndicator.getWidth() + writtenOffStatus.getWidth()
                + writtenSettledStatus.getWidth() + assetClassif.getWidth() + valueOfCollateral.getWidth() + typeOfCollateral.getWidth()
                + creditLimit.getWidth() + cashLimit.getWidth() + rateOfInterest.getWidth() + repaymentTenure.getWidth() + emiAmt.getWidth()
                + writtenOffAmountTotal.getWidth() + writtenOffPrincipalAmount.getWidth() + settlementAmt.getWidth() + paymentFrequency.getWidth()
                + actualPaymentAmt.getWidth() + occupationCode.getWidth() + income.getWidth() + netGrossIncomeIndicator.getWidth()
                + telephonetype1.getWidth() + telephonetype2.getWidth() + telephonetype3.getWidth() + borcity2.getWidth() + borDistrict2.getWidth()
                + monthlyAnnualIncomeIndicator.getWidth();

        Page page = new Page(5330, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle(cibilParameter + "  REPORT CONSUMER ");
        return fastReport;
    }

    public void cibirCrifComrReport() {
        try {
            UIViewRoot view = getFacesContext().getCurrentInstance().getViewRoot();
            String reportName = "";
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                reportName = "CIBIL_COMERCIAL_REPORT_";
            } else if (cibilParameter.equalsIgnoreCase("CRIF")) {
                reportName = "CRIF_COMERCIAL_REPORT_";
            }
            List<CibilHDPoJo> hd = new ArrayList<>();
            List<CibilBSPoJo> bs = new ArrayList<>();
            List<CibilASPoJo> as = new ArrayList<>();
            List<CibilRSPoJo> rs = new ArrayList<>();
            List<CibilCRPoJo> cr = new ArrayList<>();
            List<CibilGSPoJo> gs = new ArrayList<>();
            List<CibilSSPoJo> ss = new ArrayList<>();
            List<CibilCDPoJo> cd = new ArrayList<>();
            List<CibilTSPoJo> ts = new ArrayList<>();
            hd = cibilCom.getHdPojo();
            bs = cibilCom.getBsPojo();
            as = cibilCom.getAsPojo();
            rs = cibilCom.getRsPojo();
            cr = cibilCom.getCrPojo();
            gs = cibilCom.getGsPojo();
            ss = cibilCom.getSsPojo();
            cd = cibilCom.getCdPojo();
            ts = cibilCom.getTsPojo();
            String[] sheetNames = {"HD", "BS", "AS", "RS", "CR", "GS", "SS", "CD", "TS"};
            FastReportBuilder hdRport = genrateDynamicCibilHD();
            FastReportBuilder bsRport = genrateDynamicCibilBS();
            FastReportBuilder asRport = genrateDynamicCibilAS();
            FastReportBuilder rsRport = genrateDynamicCibilRS();
            FastReportBuilder crRport = genrateDynamicCibilCR();
            FastReportBuilder gsRport = genrateDynamicCibilGS();
            FastReportBuilder ssRport = genrateDynamicCibilSS();
            FastReportBuilder cdRport = genrateDynamicCibilCD();
            FastReportBuilder tsRport = genrateDynamicCibilTS();
            JasperPrint jp1 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(hd), hdRport);
            JasperPrint jp2 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(bs), bsRport);
            JasperPrint jp3 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(as), asRport);
            JasperPrint jp4 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(rs), rsRport);
            JasperPrint jp5 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(cr), crRport);
            JasperPrint jp6 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(gs), gsRport);
            JasperPrint jp7 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(ss), ssRport);
            JasperPrint jp8 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(cd), cdRport);
            JasperPrint jp9 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(ts), tsRport);
            ArrayList<JasperPrint> list = new ArrayList<JasperPrint>();
            list.add(jp1);
            list.add(jp2);
            list.add(jp3);
            list.add(jp4);
            list.add(jp5);
            list.add(jp6);
            list.add(jp7);
            list.add(jp8);
            list.add(jp9);
            new ReportBean().cibilExporterMultipleSheets(list, reportName + ymd.format(toCalDate), sheetNames);
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String btnExitAction() {
        refreshForm();
        return "case1";
    }

    public void refreshForm() {
        try {
            this.setMessage("");
            this.setMemberCode("");
            this.setFromCalDate(sdf.parse(getTodayDate()));
            this.setToCalDate(sdf.parse(getTodayDate()));
            txtFileNameList = new ArrayList<String>();
            this.displayRepType = "none";
            this.repType = "0";
            this.setDisableSave(true);
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public FastReportBuilder genrateDynamicCibilHD() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        AbstractColumn segmentID = ReportBean.getColumn("segmentID", String.class, "Segment Identifer", 0);
        if (cibilParameter.equalsIgnoreCase("CIBIL")) {
            segmentID = ReportBean.getColumn("segmentID", String.class, "Segment Identifer", 80);
        }
//        AbstractColumn segmentID = ReportBean.getColumn("segmentID", String.class, "Segment Identifer", 80);
        AbstractColumn memberID = ReportBean.getColumn("memberID", String.class, "Member ID", 80);
        AbstractColumn preMembId = ReportBean.getColumn("preMembId", String.class, "Previous Member ID", 80);
        AbstractColumn reportGenDt = ReportBean.getColumn("reportGenDt", String.class, "Date of Creation & Certification of Input File", 100);
        AbstractColumn reportDt = ReportBean.getColumn("reportDt", String.class, "Reporting / Cycle Date", 80);
        AbstractColumn typeOfInfo = ReportBean.getColumn("typeOfInfo", String.class, "Information Type", 80);
        AbstractColumn filler = ReportBean.getColumn("filler", String.class, "Filler", 80);
        if (cibilParameter.equalsIgnoreCase("CIBIL")) {
            fastReport.addColumn(segmentID);
        }
        fastReport.addColumn(memberID);
        fastReport.addColumn(preMembId);
        fastReport.addColumn(reportGenDt);
        fastReport.addColumn(reportDt);
        fastReport.addColumn(typeOfInfo);
        fastReport.addColumn(filler);
        if (cibilParameter.equalsIgnoreCase("CIBIL")) {
            width = width + segmentID.getWidth();
        }
        width = width + memberID.getWidth();
        width = width + preMembId.getWidth();
        width = width + reportGenDt.getWidth();
        width = width + reportDt.getWidth();
        width = width + typeOfInfo.getWidth();
        width = width + filler.getWidth();
        Page page = new Page(5330, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle(cibilParameter + "  REPORT COMERCIAL HEADER SEGMENT");
        return fastReport;
    }

    public FastReportBuilder genrateDynamicCibilBS() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        String acNo = "";
        if (cibilParameter.equalsIgnoreCase("CIBIL")) {
            acNo = "A/c No.";
        } else if (cibilParameter.equalsIgnoreCase("CRIF")) {
            acNo = "BANK_ID";
        }
        AbstractColumn acno = null;
        AbstractColumn flag = null;
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            acno = ReportBean.getColumn("acno", String.class, acNo, 100);
            flag = ReportBean.getColumn("flag", String.class, "Flag", 0);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                flag = ReportBean.getColumn("flag", String.class, "Flag", 50);
            }
        }

        AbstractColumn borDunsNo = ReportBean.getColumn("borDunsNo", String.class, "Flag", 0);
        if (cibilParameter.equalsIgnoreCase("CRIF")) {
            borDunsNo = ReportBean.getColumn("borDunsNo", String.class, "DUNS_NUMBER", 100);
        }
        AbstractColumn segId = ReportBean.getColumn("segId", String.class, "Segment Identifier", 60);
        AbstractColumn memberBrnCode = ReportBean.getColumn("memberBrnCode", String.class, "Member Branch Code", 80);
        AbstractColumn prevMemBrnCode = ReportBean.getColumn("prevMemBrnCode", String.class, "Previous Member Branch Code", 80);
        AbstractColumn borName = ReportBean.getColumn("borName", String.class, "Borrower's Name", 150);
        AbstractColumn borShortName = ReportBean.getColumn("borShortName", String.class, "Borrower Short Name", 80);
        AbstractColumn comRegNo = ReportBean.getColumn("comRegNo", String.class, "Company Registration Number", 80);
        AbstractColumn dateOfIncorp = ReportBean.getColumn("dateOfIncorp", String.class, "Date of Incorporation", 80);
        AbstractColumn pan = ReportBean.getColumn("pan", String.class, "PAN", 80);
        AbstractColumn cin = ReportBean.getColumn("cin", String.class, "CIN", 80);
        AbstractColumn tin = ReportBean.getColumn("tin", String.class, "TIN", 80);
        AbstractColumn serviceTax = ReportBean.getColumn("serviceTax", String.class, "Service Tax #", 80);
        AbstractColumn otherId = ReportBean.getColumn("otherId", String.class, "Other ID", 80);
        AbstractColumn borLglConstitution = ReportBean.getColumn("borLglConstitution", String.class, "Borrowers Legal Constitution", 80);
        AbstractColumn bussCat = ReportBean.getColumn("bussCat", String.class, "Business Category", 80);
        AbstractColumn bussType = ReportBean.getColumn("bussType", String.class, "Business/ Industry Type", 80);
        AbstractColumn clsOfAct1 = ReportBean.getColumn("clsOfAct1", String.class, "Class of Activity 1", 80);
        AbstractColumn clsOfAct2 = ReportBean.getColumn("clsOfAct2", String.class, "Class of Activity 2", 80);
        AbstractColumn clsOfAct3 = ReportBean.getColumn("clsOfAct3", String.class, "Class of Activity 3", 80);
        AbstractColumn sicCode = ReportBean.getColumn("sicCode", String.class, "SIC Code", 80);
        AbstractColumn salesFigure = ReportBean.getColumn("salesFigure", String.class, "Sales Figure", 80);
        AbstractColumn finYear = ReportBean.getColumn("finYear", String.class, "Financial Year", 80);
        AbstractColumn numberOfEmp = ReportBean.getColumn("numberOfEmp", String.class, "Number of Employees", 80);
        AbstractColumn crRating = ReportBean.getColumn("crRating", String.class, "Credit Rating", 80);
        AbstractColumn assAuthority = ReportBean.getColumn("assAuthority", String.class, "Assessment Agency / Authority", 80);
        AbstractColumn crRatingAsOn = ReportBean.getColumn("crRatingAsOn", String.class, "Credit Rating As On", 80);
        AbstractColumn crRatingExpDt = ReportBean.getColumn("crRatingExpDt", String.class, "Credit Rating Expiry Date", 80);
        AbstractColumn filler = ReportBean.getColumn("filler", String.class, "Filler", 80);
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            fastReport.addColumn(acno);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                fastReport.addColumn(flag);
            }
        }

        fastReport.addColumn(segId);
        fastReport.addColumn(memberBrnCode);
        fastReport.addColumn(prevMemBrnCode);
        if (cibilParameter.equalsIgnoreCase("CRIF")) {
            fastReport.addColumn(borDunsNo);
        }
        fastReport.addColumn(borName);
        fastReport.addColumn(borShortName);
        fastReport.addColumn(comRegNo);
        fastReport.addColumn(dateOfIncorp);
        fastReport.addColumn(pan);
        fastReport.addColumn(cin);
        fastReport.addColumn(tin);
        fastReport.addColumn(serviceTax);
        fastReport.addColumn(otherId);
        fastReport.addColumn(borLglConstitution);
        fastReport.addColumn(bussCat);
        fastReport.addColumn(bussType);
        fastReport.addColumn(clsOfAct1);
        fastReport.addColumn(clsOfAct2);
        fastReport.addColumn(clsOfAct3);
        fastReport.addColumn(sicCode);
        fastReport.addColumn(salesFigure);
        fastReport.addColumn(finYear);
        fastReport.addColumn(numberOfEmp);
        fastReport.addColumn(crRating);
        fastReport.addColumn(assAuthority);
        fastReport.addColumn(crRatingAsOn);
        fastReport.addColumn(crRatingExpDt);
        fastReport.addColumn(filler);
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            width = width + acno.getWidth();
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                width = width + flag.getWidth();
            }
        }

        width = width + segId.getWidth();
        width = width + memberBrnCode.getWidth();
        width = width + prevMemBrnCode.getWidth();
        if (cibilParameter.equalsIgnoreCase("CRIF")) {
            width = width + borDunsNo.getWidth();
        }
        width = width + borName.getWidth();
        width = width + borShortName.getWidth();
        width = width + comRegNo.getWidth();
        width = width + dateOfIncorp.getWidth();
        width = width + pan.getWidth();
        width = width + cin.getWidth();
        width = width + tin.getWidth();
        width = width + serviceTax.getWidth();
        width = width + otherId.getWidth();
        width = width + borLglConstitution.getWidth();
        width = width + bussCat.getWidth();
        width = width + bussType.getWidth();
        width = width + clsOfAct1.getWidth();
        width = width + clsOfAct2.getWidth();
        width = width + clsOfAct3.getWidth();
        width = width + sicCode.getWidth();
        width = width + salesFigure.getWidth();
        width = width + finYear.getWidth();
        width = width + numberOfEmp.getWidth();
        width = width + crRating.getWidth();
        width = width + assAuthority.getWidth();
        width = width + crRatingAsOn.getWidth();
        width = width + crRatingExpDt.getWidth();
        width = width + filler.getWidth();
        Page page = new Page(5330, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle(cibilParameter + "  REPORT COMERCIAL BORROWER SEGMENT");
        return fastReport;
    }

    public FastReportBuilder genrateDynamicCibilAS() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        String acNo = "";
        if (cibilParameter.equalsIgnoreCase("CIBIL")) {
            acNo = "A/c No.";
        } else if (cibilParameter.equalsIgnoreCase("CRIF")) {
            acNo = "BANK_ID";
        }
        AbstractColumn acno = null;
        AbstractColumn flag = null;
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            acno = ReportBean.getColumn("acno", String.class, acNo, 80);
            flag = ReportBean.getColumn("flag", String.class, "Flag", 0);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                flag = ReportBean.getColumn("flag", String.class, "Flag", 80);
            }
        }
        AbstractColumn segId = ReportBean.getColumn("segId", String.class, "Segment Identifier", 80);
        AbstractColumn borOfycLocType = ReportBean.getColumn("borOfycLocType", String.class, "Borrower Office Location Type", 80);
        AbstractColumn borOfycDUNNo = ReportBean.getColumn("borOfycDUNNo", String.class, "Borrower Office DUNS Number", 150);
        AbstractColumn borAdd1 = ReportBean.getColumn("borAdd1", String.class, "Address Line 1", 150);
        AbstractColumn borAdd2 = ReportBean.getColumn("borAdd2", String.class, "Address Line 2", 150);
        AbstractColumn borAdd3 = ReportBean.getColumn("borAdd3", String.class, "Address Line 3", 150);
        AbstractColumn borCity = ReportBean.getColumn("borCity", String.class, "City/Town", 80);
        AbstractColumn borDistrict = ReportBean.getColumn("borDistrict", String.class, "District", 80);
        AbstractColumn borState = ReportBean.getColumn("borState", String.class, "State/Union Territory", 80);
        AbstractColumn borPinCode = ReportBean.getColumn("borPinCode", String.class, "Pin Code", 80);
        AbstractColumn borCountry = ReportBean.getColumn("borCountry", String.class, "Country", 80);
        AbstractColumn borMob = ReportBean.getColumn("borMob", String.class, "Mobile Number(s)", 80);
        AbstractColumn borTelAreaCode = ReportBean.getColumn("borTelAreaCode", String.class, "Telephone Area Code", 80);
        AbstractColumn borTelNo = ReportBean.getColumn("borTelNo", String.class, "Telephone Number(s)", 80);
        AbstractColumn borFaxAreaCode = ReportBean.getColumn("borFaxAreaCode", String.class, "Fax Area Code", 80);
        AbstractColumn borFaxNo = ReportBean.getColumn("borFaxNo", String.class, "Fax Number(s)", 80);
        AbstractColumn borFiller = ReportBean.getColumn("borFiller", String.class, "Filler", 80);
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            fastReport.addColumn(acno);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                fastReport.addColumn(flag);
            }
        }
        fastReport.addColumn(segId);
        fastReport.addColumn(borOfycLocType);
        fastReport.addColumn(borOfycDUNNo);
        fastReport.addColumn(borAdd1);
        fastReport.addColumn(borAdd2);
        fastReport.addColumn(borAdd3);
        fastReport.addColumn(borCity);
        fastReport.addColumn(borDistrict);
        fastReport.addColumn(borState);
        fastReport.addColumn(borPinCode);
        fastReport.addColumn(borCountry);
        fastReport.addColumn(borMob);
        fastReport.addColumn(borTelAreaCode);
        fastReport.addColumn(borTelNo);
        fastReport.addColumn(borFaxAreaCode);
        fastReport.addColumn(borFaxNo);
        fastReport.addColumn(borFiller);
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            width = width + acno.getWidth();
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                width = width + flag.getWidth();
            }
        }

        width = width + segId.getWidth();
        width = width + borOfycLocType.getWidth();
        width = width + borOfycDUNNo.getWidth();
        width = width + borAdd1.getWidth();
        width = width + borAdd2.getWidth();
        width = width + borAdd3.getWidth();
        width = width + borCity.getWidth();
        width = width + borDistrict.getWidth();
        width = width + borState.getWidth();
        width = width + borPinCode.getWidth();
        width = width + borCountry.getWidth();
        width = width + borMob.getWidth();
        width = width + borTelAreaCode.getWidth();
        width = width + borTelNo.getWidth();
        width = width + borFaxAreaCode.getWidth();
        width = width + borFaxNo.getWidth();
        width = width + borFiller.getWidth();
        Page page = new Page(5330, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle(cibilParameter + " REPORT COMERCIAL ADDRESS SEGMENT");
        return fastReport;
    }

    public FastReportBuilder genrateDynamicCibilRS() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        String acNo = "";
        if (cibilParameter.equalsIgnoreCase("CIBIL")) {
            acNo = "A/c No.";
        } else if (cibilParameter.equalsIgnoreCase("CRIF")) {
            acNo = "BANK_ID";
        }
        AbstractColumn acno = null;
        AbstractColumn flag = null;
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            acno = ReportBean.getColumn("acno", String.class, acNo, 80);
            flag = ReportBean.getColumn("flag", String.class, "Flag", 0);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                flag = ReportBean.getColumn("flag", String.class, "Flag", 80);
            }
        }
        AbstractColumn segId = ReportBean.getColumn("segId", String.class, "Segment Identifier", 80);
        AbstractColumn relationDunNo = ReportBean.getColumn("relationDunNo", String.class, "Relationship DUNS Number", 80);
        AbstractColumn relatedType = ReportBean.getColumn("relatedType", String.class, "Related Type", 80);
        AbstractColumn relationShip = ReportBean.getColumn("relationShip", String.class, "Relationship", 80);
        AbstractColumn businessEntityName = ReportBean.getColumn("businessEntityName", String.class, "Business Entity Name", 80);
        AbstractColumn bussCat = ReportBean.getColumn("bussCat", String.class, "Business Category", 80);
        AbstractColumn bussType = ReportBean.getColumn("bussType", String.class, "Business / Industry Type", 80);
        AbstractColumn prefix = ReportBean.getColumn("prefix", String.class, "Individual Name Prefix", 80);
        AbstractColumn fullName = ReportBean.getColumn("fullName", String.class, "Full Name", 150);
        AbstractColumn gender = ReportBean.getColumn("gender", String.class, "Gender", 80);
        AbstractColumn compRegNo = ReportBean.getColumn("compRegNo", String.class, "Company Registration Number", 80);
        AbstractColumn dateOfIncorp = ReportBean.getColumn("dateOfIncorp", String.class, "Date of Incorporation", 80);
        AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Date of Birth", 80);
        AbstractColumn pan = ReportBean.getColumn("pan", String.class, "PAN", 80);
        AbstractColumn voterId = ReportBean.getColumn("voterId", String.class, "Voter ID", 100);
        AbstractColumn passportNo = ReportBean.getColumn("passportNo", String.class, "Passport Number", 80);
        AbstractColumn dlNo = ReportBean.getColumn("dlNo", String.class, "Driving Licence ID", 80);
        AbstractColumn uid = ReportBean.getColumn("uid", String.class, "UID", 150);
        AbstractColumn rationCardNo = ReportBean.getColumn("rationCardNo", String.class, "Ration Card No", 80);
        AbstractColumn cin = ReportBean.getColumn("cin", String.class, "CIN", 80);
        AbstractColumn din = ReportBean.getColumn("din", String.class, "DIN", 80);
        AbstractColumn tin = ReportBean.getColumn("tin", String.class, "TIN", 80);
        AbstractColumn serviceTax = ReportBean.getColumn("serviceTax", String.class, "Service Tax #", 80);
        AbstractColumn otherId = ReportBean.getColumn("otherId", String.class, "Other ID", 80);
        AbstractColumn percOfCntrl = ReportBean.getColumn("percOfCntrl", String.class, "Percentage of Control", 80);
        AbstractColumn add1 = ReportBean.getColumn("add1", String.class, "Address Line 1", 150);
        AbstractColumn add2 = ReportBean.getColumn("add2", String.class, "Address Line 2", 150);
        AbstractColumn add3 = ReportBean.getColumn("add3", String.class, "Address Line 3", 150);
        AbstractColumn city = ReportBean.getColumn("city", String.class, "City/Town", 80);
        AbstractColumn district = ReportBean.getColumn("district", String.class, "District", 80);
        AbstractColumn state = ReportBean.getColumn("state", String.class, "State/Union Territory", 80);
        AbstractColumn pinCode = ReportBean.getColumn("pinCode", String.class, "Pin Code", 80);
        AbstractColumn country = ReportBean.getColumn("country", String.class, "Country", 80);
        AbstractColumn mobNo = ReportBean.getColumn("mobNo", String.class, "Mobile Number(s)", 80);
        AbstractColumn telNo = ReportBean.getColumn("telNo", String.class, "Telephone Number(s)", 80);
        AbstractColumn telAreaCode = ReportBean.getColumn("telAreaCode", String.class, "Telephone Area Code", 80);
        AbstractColumn faxNo = ReportBean.getColumn("faxNo", String.class, "Fax Number(s)", 80);
        AbstractColumn faxAreaCode = ReportBean.getColumn("faxAreaCode", String.class, "Fax Area Code", 80);
        AbstractColumn filler = ReportBean.getColumn("filler", String.class, "Filler", 80);
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            fastReport.addColumn(acno);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                fastReport.addColumn(flag);
            }
        }
        fastReport.addColumn(segId);
        fastReport.addColumn(relationDunNo);
        fastReport.addColumn(relatedType);
        fastReport.addColumn(relationShip);
        fastReport.addColumn(businessEntityName);
        fastReport.addColumn(bussCat);
        fastReport.addColumn(bussType);
        fastReport.addColumn(prefix);
        fastReport.addColumn(fullName);
        fastReport.addColumn(gender);
        fastReport.addColumn(compRegNo);
        fastReport.addColumn(dateOfIncorp);
        fastReport.addColumn(dob);
        fastReport.addColumn(pan);
        fastReport.addColumn(voterId);
        fastReport.addColumn(passportNo);
        fastReport.addColumn(dlNo);
        fastReport.addColumn(uid);
        fastReport.addColumn(rationCardNo);
        fastReport.addColumn(cin);
        fastReport.addColumn(din);
        fastReport.addColumn(tin);
        fastReport.addColumn(serviceTax);
        fastReport.addColumn(otherId);
        fastReport.addColumn(percOfCntrl);
        fastReport.addColumn(add1);
        fastReport.addColumn(add2);
        fastReport.addColumn(add3);
        fastReport.addColumn(city);
        fastReport.addColumn(district);
        fastReport.addColumn(state);
        fastReport.addColumn(pinCode);
        fastReport.addColumn(country);
        fastReport.addColumn(mobNo);
        fastReport.addColumn(telNo);
        fastReport.addColumn(telAreaCode);
        fastReport.addColumn(faxNo);
        fastReport.addColumn(faxAreaCode);
        fastReport.addColumn(filler);
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            width = width + acno.getWidth();
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                width = width + flag.getWidth();
            }
        }
        width = width + segId.getWidth();
        width = width + relationDunNo.getWidth();
        width = width + relatedType.getWidth();
        width = width + relationShip.getWidth();
        width = width + businessEntityName.getWidth();
        width = width + bussCat.getWidth();
        width = width + bussType.getWidth();
        width = width + prefix.getWidth();
        width = width + fullName.getWidth();
        width = width + gender.getWidth();
        width = width + compRegNo.getWidth();
        width = width + dateOfIncorp.getWidth();
        width = width + dob.getWidth();
        width = width + pan.getWidth();
        width = width + voterId.getWidth();
        width = width + passportNo.getWidth();
        width = width + dlNo.getWidth();
        width = width + uid.getWidth();
        width = width + rationCardNo.getWidth();
        width = width + cin.getWidth();
        width = width + din.getWidth();
        width = width + tin.getWidth();
        width = width + serviceTax.getWidth();
        width = width + otherId.getWidth();
        width = width + percOfCntrl.getWidth();
        width = width + add1.getWidth();
        width = width + add2.getWidth();
        width = width + add3.getWidth();
        width = width + city.getWidth();
        width = width + district.getWidth();
        width = width + state.getWidth();
        width = width + pinCode.getWidth();
        width = width + country.getWidth();
        width = width + mobNo.getWidth();
        width = width + telNo.getWidth();
        width = width + telAreaCode.getWidth();
        width = width + faxNo.getWidth();
        width = width + faxAreaCode.getWidth();
        width = width + filler.getWidth();
        Page page = new Page(5330, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle(cibilParameter + "  REPORT COMERCIAL RELATIONSHIP SEGMENT");
        return fastReport;
    }

    public FastReportBuilder genrateDynamicCibilCR() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        String acNo = "";
        if (cibilParameter.equalsIgnoreCase("CIBIL")) {
            acNo = "A/c No.";
        } else if (cibilParameter.equalsIgnoreCase("CRIF")) {
            acNo = "BANK_ID";
        }
        AbstractColumn acno = null;
        AbstractColumn flag = null;
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            acno = ReportBean.getColumn("acno", String.class, acNo, 80);
            flag = ReportBean.getColumn("flag", String.class, "Flag", 0);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                flag = ReportBean.getColumn("flag", String.class, "Flag", 40);
            }
        }
        AbstractColumn segId = ReportBean.getColumn("segId", String.class, "Segment Identifier", 80);
        AbstractColumn accountNumber = ReportBean.getColumn("accountNumber", String.class, "Account Number", 80);
        AbstractColumn preAcNo = ReportBean.getColumn("preAcNo", String.class, "Previous Account Number", 80);
        AbstractColumn sancDt = ReportBean.getColumn("sancDt", String.class, "Facility / Loan Activation / Sanction Date", 150);
        AbstractColumn sancAmt = ReportBean.getColumn("sancAmt", String.class, "Sanctioned Amount/ Notional Amount of Contract", 150);
        AbstractColumn currencyCode = ReportBean.getColumn("currencyCode", String.class, "Currency Code", 80);
        AbstractColumn crType = ReportBean.getColumn("crType", String.class, "Credit Type", 80);
        AbstractColumn period = ReportBean.getColumn("period", String.class, "Tenure / Weighted Average maturity period of Contracts", 150);
        AbstractColumn repaymentFreq = ReportBean.getColumn("repaymentFreq", String.class, "Repayment Frequency", 80);
        AbstractColumn dpLimit = ReportBean.getColumn("dpLimit", String.class, "Drawing Power", 80);
        AbstractColumn outstanding = ReportBean.getColumn("outstanding", String.class, "Current Balance / Limit Utilized /Mark to Market", 150);
        AbstractColumn outstandingRestructured = ReportBean.getColumn("outstandingRestructured", String.class, "Notional Amount of Out-standing Restructured Contracts", 150);
        AbstractColumn closingDt = ReportBean.getColumn("closingDt", String.class, "Loan Expiry / Maturity Date", 150);
        AbstractColumn renewalDt = ReportBean.getColumn("renewalDt", String.class, "Loan Renewal Date", 150);
        AbstractColumn assetClass = ReportBean.getColumn("assetClass", String.class, "Asset Classification/Number of days past due NDPD", 150);
        AbstractColumn assetClassDt = ReportBean.getColumn("assetClassDt", String.class, "Asset Classification Date", 150);
        AbstractColumn odAmt = ReportBean.getColumn("odAmt", String.class, "Amount Overdue / Limit Overdue", 150);
        AbstractColumn odBucket1 = ReportBean.getColumn("odBucket1", String.class, "Overdue Bucket 01 ( 1 – 30 days)", 150);
        AbstractColumn odBucket2 = ReportBean.getColumn("odBucket2", String.class, "Overdue Bucket 02 ( 31 – 60 days)", 150);
        AbstractColumn odBucket3 = ReportBean.getColumn("odBucket3", String.class, "Overdue Bucket 03 ( 61 – 90 days)", 150);
        AbstractColumn odBucket4 = ReportBean.getColumn("odBucket4", String.class, "Overdue Bucket 04 (91 – 180 days)", 150);
        AbstractColumn odBucket5 = ReportBean.getColumn("odBucket5", String.class, "Overdue Bucket 05 (Above 180 days)", 150);
        AbstractColumn highCredit = ReportBean.getColumn("highCredit", String.class, "High Credit", 80);
        AbstractColumn emiAmt = ReportBean.getColumn("emiAmt", String.class, "Installment Amount", 80);
        AbstractColumn lastCrAmt = ReportBean.getColumn("lastCrAmt", String.class, "Last Repaid Amount", 80);
        AbstractColumn acStatus = ReportBean.getColumn("acStatus", String.class, "Account Status", 80);
        AbstractColumn acStatusDt = ReportBean.getColumn("acStatusDt", String.class, "Account Status Date", 80);
        AbstractColumn writeOffAmt = ReportBean.getColumn("writeOffAmt", String.class, "Written Off Amount", 80);
        AbstractColumn settledAmt = ReportBean.getColumn("settledAmt", String.class, "Settled Amount", 80);
        AbstractColumn restructuredReason = ReportBean.getColumn("restructuredReason", String.class, "Major reasons for Restructuring", 150);
        AbstractColumn npaAmount = ReportBean.getColumn("npaAmount", BigDecimal.class, "Amount of Contracts Classified as NPA", 150);
        AbstractColumn assetSecCover = ReportBean.getColumn("assetSecCover", String.class, "Asset based Security coverage", 150);
        AbstractColumn guaranteeCover = ReportBean.getColumn("guaranteeCover", String.class, "Guarantee Coverage", 150);
        AbstractColumn bnkRemarkCode = ReportBean.getColumn("bnkRemarkCode", String.class, "Bank Remark Code", 80);
        AbstractColumn willFullStatus = ReportBean.getColumn("willFullStatus", String.class, "Wilful Default Status", 150);
        AbstractColumn willFullDt = ReportBean.getColumn("willFullDt", String.class, "Date Classified as Wilful Default", 150);
        AbstractColumn suitFiledStatus = ReportBean.getColumn("suitFiledStatus", String.class, "Suit Filed Status", 150);
        AbstractColumn suitRefNo = ReportBean.getColumn("suitRefNo", String.class, "Suit Reference Number", 150);
        AbstractColumn suitAmt = ReportBean.getColumn("suitAmt", String.class, "Suit Amount in Rupees", 150);
        AbstractColumn suitDt = ReportBean.getColumn("suitDt", String.class, "Date of Suit", 80);
        AbstractColumn disputeIdNo = ReportBean.getColumn("disputeIdNo", String.class, "Dispute ID No.", 80);
        AbstractColumn txnTypeCode = ReportBean.getColumn("txnTypeCode", String.class, "Transaction Type Code", 150);
        AbstractColumn otherBk = ReportBean.getColumn("otherBk", String.class, "OTHER_BK", 0);
        AbstractColumn uFCEAmt = ReportBean.getColumn("uFCEAmt", String.class, "UFCE (Amount)", 0);
        AbstractColumn uFCEDt = ReportBean.getColumn("uFCEDt", String.class, "UFCE Date", 0);
        AbstractColumn filler = ReportBean.getColumn("filler", String.class, "FILLER", 0);
        if (cibilParameter.equalsIgnoreCase("CRIF")) {
            filler = ReportBean.getColumn("filler", String.class, "FILLER", 80);
        }
        if (cibilParameter.equalsIgnoreCase("CIBIL")) {
            otherBk = ReportBean.getColumn("otherBk", String.class, "OTHER_BK", 80);
            uFCEAmt = ReportBean.getColumn("uFCEAmt", String.class, "UFCE (Amount)", 150);
            uFCEDt = ReportBean.getColumn("uFCEDt", String.class, "UFCE Date", 80);
        }
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            fastReport.addColumn(acno);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                fastReport.addColumn(flag);
            }
        }
        fastReport.addColumn(segId);
        fastReport.addColumn(accountNumber);
        fastReport.addColumn(preAcNo);
        fastReport.addColumn(sancDt);
        fastReport.addColumn(sancAmt);
        fastReport.addColumn(currencyCode);
        fastReport.addColumn(crType);
        fastReport.addColumn(period);
        fastReport.addColumn(repaymentFreq);
        fastReport.addColumn(dpLimit);
        fastReport.addColumn(outstanding);
        fastReport.addColumn(outstandingRestructured);
        fastReport.addColumn(closingDt);
        fastReport.addColumn(renewalDt);
        fastReport.addColumn(assetClass);
        fastReport.addColumn(assetClassDt);
        fastReport.addColumn(odAmt);
        fastReport.addColumn(odBucket1);
        fastReport.addColumn(odBucket2);
        fastReport.addColumn(odBucket3);
        fastReport.addColumn(odBucket4);
        fastReport.addColumn(odBucket5);
        fastReport.addColumn(highCredit);
        fastReport.addColumn(emiAmt);
        fastReport.addColumn(lastCrAmt);
        fastReport.addColumn(acStatus);
        fastReport.addColumn(acStatusDt);
        fastReport.addColumn(writeOffAmt);
        fastReport.addColumn(settledAmt);
        fastReport.addColumn(restructuredReason);
        fastReport.addColumn(npaAmount);
        fastReport.addColumn(assetSecCover);
        fastReport.addColumn(guaranteeCover);
        fastReport.addColumn(bnkRemarkCode);
        fastReport.addColumn(willFullStatus);
        fastReport.addColumn(willFullDt);
        fastReport.addColumn(suitFiledStatus);
        fastReport.addColumn(suitRefNo);
        fastReport.addColumn(suitAmt);
        fastReport.addColumn(suitDt);
        fastReport.addColumn(disputeIdNo);
        fastReport.addColumn(txnTypeCode);
        fastReport.addColumn(otherBk);
        fastReport.addColumn(uFCEAmt);
        fastReport.addColumn(uFCEDt);
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            width = width + acno.getWidth();
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                width = width + flag.getWidth();
            }
        }
        width = width + segId.getWidth();
        width = width + accountNumber.getWidth();
        width = width + preAcNo.getWidth();
        width = width + sancDt.getWidth();
        width = width + sancAmt.getWidth();
        width = width + currencyCode.getWidth();
        width = width + crType.getWidth();
        width = width + period.getWidth();
        width = width + repaymentFreq.getWidth();
        width = width + dpLimit.getWidth();
        width = width + outstanding.getWidth();
        width = width + outstandingRestructured.getWidth();
        width = width + closingDt.getWidth();
        width = width + renewalDt.getWidth();
        width = width + assetClass.getWidth();
        width = width + assetClassDt.getWidth();
        width = width + odAmt.getWidth();
        width = width + odBucket1.getWidth();
        width = width + odBucket2.getWidth();
        width = width + odBucket3.getWidth();
        width = width + odBucket4.getWidth();
        width = width + odBucket5.getWidth();
        width = width + highCredit.getWidth();
        width = width + emiAmt.getWidth();
        width = width + lastCrAmt.getWidth();
        width = width + acStatus.getWidth();
        width = width + acStatusDt.getWidth();
        width = width + writeOffAmt.getWidth();
        width = width + settledAmt.getWidth();
        width = width + restructuredReason.getWidth();
        width = width + npaAmount.getWidth();
        width = width + assetSecCover.getWidth();
        width = width + guaranteeCover.getWidth();
        width = width + bnkRemarkCode.getWidth();
        width = width + willFullStatus.getWidth();
        width = width + willFullDt.getWidth();
        width = width + suitFiledStatus.getWidth();
        width = width + suitRefNo.getWidth();
        width = width + suitAmt.getWidth();
        width = width + suitDt.getWidth();
        width = width + disputeIdNo.getWidth();
        width = width + txnTypeCode.getWidth();
        width = width + otherBk.getWidth();
        width = width + uFCEAmt.getWidth();
        width = width + uFCEDt.getWidth();
        if (cibilParameter.equalsIgnoreCase("CRIF")) {
            width = width + filler.getWidth();
        }
        Page page = new Page(5330, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle(cibilParameter + "  REPORT COMERCIAL CREDIT FACILITY SEGMENT");
        return fastReport;
    }

    public FastReportBuilder genrateDynamicCibilGS() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        String acNo = "";
        if (cibilParameter.equalsIgnoreCase("CIBIL")) {
            acNo = "A/c No.";
        } else if (cibilParameter.equalsIgnoreCase("CRIF")) {
            acNo = "BANK_ID";
        }
        AbstractColumn acno = null;
        AbstractColumn flag = null;
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            acno = ReportBean.getColumn("acno", String.class, acNo, 80);
            flag = ReportBean.getColumn("flag", String.class, "Flag", 0);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                flag = ReportBean.getColumn("flag", String.class, "Flag", 80);
            }
        }
        AbstractColumn segId = ReportBean.getColumn("segId", String.class, "Segment Identifier", 80);
        AbstractColumn guarantorDUNSNo = ReportBean.getColumn("guarantorDUNSNo", String.class, "Guarantor DUNS Number", 80);
        AbstractColumn guarantorType = ReportBean.getColumn("guarantorType", String.class, "Guarantor Type", 80);
        AbstractColumn bussCat = ReportBean.getColumn("bussCat", String.class, "Business Category", 80);
        AbstractColumn bussType = ReportBean.getColumn("bussType", String.class, "Business / Industry Type", 150);
        AbstractColumn guarEntityName = ReportBean.getColumn("guarEntityName", String.class, "Guarantor Entity Name", 80);
        AbstractColumn guarNamePrefix = ReportBean.getColumn("guarNamePrefix", String.class, "Individual Name Prefix", 80);
        AbstractColumn guarFullName = ReportBean.getColumn("guarFullName", String.class, "Full Name", 80);
        AbstractColumn guarGender = ReportBean.getColumn("guarGender", String.class, "Gender", 80);
        AbstractColumn guarCompRegNo = ReportBean.getColumn("guarCompRegNo", String.class, "Company Registration Number", 80);
        AbstractColumn guarDateOfIncorp = ReportBean.getColumn("guarDateOfIncorp", String.class, "Date of Incorporation", 80);
        AbstractColumn guarDOB = ReportBean.getColumn("guarDOB", String.class, "Date of Birth", 80);
        AbstractColumn guarPan = ReportBean.getColumn("guarPan", String.class, "PAN", 80);
        AbstractColumn guarVoterID = ReportBean.getColumn("guarVoterID", String.class, "Voter ID", 80);
        AbstractColumn guarPassport = ReportBean.getColumn("guarPassport", String.class, "Passport Number", 80);
        AbstractColumn guarDL = ReportBean.getColumn("guarDL", String.class, "Driving Licence ID", 80);
        AbstractColumn guarUID = ReportBean.getColumn("guarUID", String.class, "UID", 80);
        AbstractColumn guarRationCard = ReportBean.getColumn("guarRationCard", String.class, "Ration Card No", 80);
        AbstractColumn guarCIN = ReportBean.getColumn("guarCIN", String.class, "CIN", 80);
        AbstractColumn guarDIN = ReportBean.getColumn("guarDIN", String.class, "DIN", 80);
        AbstractColumn guarTIN = ReportBean.getColumn("guarTIN", String.class, "TIN", 80);
        AbstractColumn guarServiceTAx = ReportBean.getColumn("guarServiceTAx", String.class, "Service Tax #", 80);
        AbstractColumn guarOtherID = ReportBean.getColumn("guarOtherID", String.class, "Other ID", 80);
        AbstractColumn guarAdd1 = ReportBean.getColumn("guarAdd1", String.class, "Address Line 1", 150);
        AbstractColumn guarAdd2 = ReportBean.getColumn("guarAdd2", String.class, "Address Line 2", 150);
        AbstractColumn guarAdd3 = ReportBean.getColumn("guarAdd3", String.class, "Address Line 3", 150);
        AbstractColumn guarCity = ReportBean.getColumn("guarCity", String.class, "City/Town", 80);
        AbstractColumn guarDistrict = ReportBean.getColumn("guarDistrict", String.class, "District", 80);
        AbstractColumn guarState = ReportBean.getColumn("guarState", String.class, "State/Union Territory", 80);
        AbstractColumn guarPinCode = ReportBean.getColumn("guarPinCode", String.class, "Pin Code", 80);
        AbstractColumn guarCountry = ReportBean.getColumn("guarCountry", String.class, "Country", 80);
        AbstractColumn guarMob = ReportBean.getColumn("guarMob", String.class, "Mobile Number(s)", 80);
        AbstractColumn guarTelAreaCode = ReportBean.getColumn("guarTelAreaCode", String.class, "Telephone Area Code", 150);
        AbstractColumn guarTelNo = ReportBean.getColumn("guarTelNo", String.class, "Telephone Number(s)", 150);
        AbstractColumn guarFaxAreaCode = ReportBean.getColumn("guarFaxAreaCode", String.class, "Fax Area Code", 150);
        AbstractColumn guarFaxNo = ReportBean.getColumn("guarFaxNo", String.class, "Fax Number(s)", 150);
        AbstractColumn guarFiller = ReportBean.getColumn("guarFiller", String.class, "Filler", 80);
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            fastReport.addColumn(acno);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                fastReport.addColumn(flag);
            }
        }
        fastReport.addColumn(segId);
        fastReport.addColumn(guarantorDUNSNo);
        fastReport.addColumn(guarantorType);
        fastReport.addColumn(bussCat);
        fastReport.addColumn(bussType);
        fastReport.addColumn(guarEntityName);
        fastReport.addColumn(guarNamePrefix);
        fastReport.addColumn(guarFullName);
        fastReport.addColumn(guarGender);
        fastReport.addColumn(guarCompRegNo);
        fastReport.addColumn(guarDateOfIncorp);
        fastReport.addColumn(guarDOB);
        fastReport.addColumn(guarPan);
        fastReport.addColumn(guarVoterID);
        fastReport.addColumn(guarPassport);
        fastReport.addColumn(guarDL);
        fastReport.addColumn(guarUID);
        fastReport.addColumn(guarRationCard);
        fastReport.addColumn(guarCIN);
        fastReport.addColumn(guarDIN);
        fastReport.addColumn(guarTIN);
        fastReport.addColumn(guarServiceTAx);
        fastReport.addColumn(guarOtherID);
        fastReport.addColumn(guarAdd1);
        fastReport.addColumn(guarAdd2);
        fastReport.addColumn(guarAdd3);
        fastReport.addColumn(guarCity);
        fastReport.addColumn(guarDistrict);
        fastReport.addColumn(guarState);
        fastReport.addColumn(guarPinCode);
        fastReport.addColumn(guarCountry);
        fastReport.addColumn(guarMob);
        fastReport.addColumn(guarTelAreaCode);
        fastReport.addColumn(guarTelNo);
        fastReport.addColumn(guarFaxAreaCode);
        fastReport.addColumn(guarFaxNo);
        fastReport.addColumn(guarFiller);
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            width = width + acno.getWidth();
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                width = width + flag.getWidth();
            }
        }
        width = width + segId.getWidth();
        width = width + guarantorDUNSNo.getWidth();
        width = width + guarantorType.getWidth();
        width = width + bussCat.getWidth();
        width = width + bussType.getWidth();
        width = width + guarEntityName.getWidth();
        width = width + guarNamePrefix.getWidth();
        width = width + guarFullName.getWidth();
        width = width + guarGender.getWidth();
        width = width + guarCompRegNo.getWidth();
        width = width + guarDateOfIncorp.getWidth();
        width = width + guarDOB.getWidth();
        width = width + guarPan.getWidth();
        width = width + guarVoterID.getWidth();
        width = width + guarPassport.getWidth();
        width = width + guarDL.getWidth();
        width = width + guarUID.getWidth();
        width = width + guarRationCard.getWidth();
        width = width + guarCIN.getWidth();
        width = width + guarDIN.getWidth();
        width = width + guarTIN.getWidth();
        width = width + guarServiceTAx.getWidth();
        width = width + guarOtherID.getWidth();
        width = width + guarAdd1.getWidth();
        width = width + guarAdd2.getWidth();
        width = width + guarAdd3.getWidth();
        width = width + guarCity.getWidth();
        width = width + guarDistrict.getWidth();
        width = width + guarState.getWidth();
        width = width + guarPinCode.getWidth();
        width = width + guarCountry.getWidth();
        width = width + guarMob.getWidth();
        width = width + guarTelAreaCode.getWidth();
        width = width + guarTelNo.getWidth();
        width = width + guarFaxAreaCode.getWidth();
        width = width + guarFaxNo.getWidth();
        width = width + guarFiller.getWidth();
        Page page = new Page(5330, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle(cibilParameter + "  REPORT COMERCIAL GUARANTOR SEGMENT");
        return fastReport;
    }

    public FastReportBuilder genrateDynamicCibilSS() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        String acNo = "";
        if (cibilParameter.equalsIgnoreCase("CIBIL")) {
            acNo = "A/c No.";
        } else if (cibilParameter.equalsIgnoreCase("CRIF")) {
            acNo = "BANK_ID";
        }
        AbstractColumn acno = null;
        AbstractColumn flag = null;
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {

            acno = ReportBean.getColumn("acno", String.class, acNo, 80);
            flag = ReportBean.getColumn("flag", String.class, "Flag", 0);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                flag = ReportBean.getColumn("flag", String.class, "Flag", 80);
            }
        }
        AbstractColumn segId = ReportBean.getColumn("segId", String.class, "Segment Identifier", 80);
        AbstractColumn valOfSec = ReportBean.getColumn("valOfSec", BigDecimal.class, "Value of Security", 80);
        AbstractColumn currencyType = ReportBean.getColumn("currencyType", String.class, "Currency Type", 80);
        AbstractColumn typeOfSec = ReportBean.getColumn("typeOfSec", String.class, "Type of Security", 80);
        AbstractColumn secClassification = ReportBean.getColumn("secClassification", String.class, "Security Classification", 80);
        AbstractColumn dateOfValue = ReportBean.getColumn("dateOfValue", String.class, "Date of Valuation", 80);
        AbstractColumn filler = ReportBean.getColumn("filler", String.class, "Filler", 80);
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            fastReport.addColumn(acno);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                fastReport.addColumn(flag);
            }
        }
        fastReport.addColumn(segId);
        fastReport.addColumn(valOfSec);
        fastReport.addColumn(currencyType);
        fastReport.addColumn(typeOfSec);
        fastReport.addColumn(secClassification);
        fastReport.addColumn(dateOfValue);
        fastReport.addColumn(filler);
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            width = width + acno.getWidth();
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                width = width + flag.getWidth();
            }
        }
        width = width + segId.getWidth();
        width = width + valOfSec.getWidth();
        width = width + currencyType.getWidth();
        width = width + typeOfSec.getWidth();
        width = width + secClassification.getWidth();
        width = width + dateOfValue.getWidth();
        width = width + filler.getWidth();
        Page page = new Page(5330, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle(cibilParameter + "  REPORT COMERCIAL SECURITY SEGMENT");
        return fastReport;
    }

    public FastReportBuilder genrateDynamicCibilCD() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        String acNo = "";
        if (cibilParameter.equalsIgnoreCase("CIBIL")) {
            acNo = "A/c No.";
        } else if (cibilParameter.equalsIgnoreCase("CRIF")) {
            acNo = "BANK_ID";
        }
        AbstractColumn acno = null;
        AbstractColumn flag = null;
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            acno = ReportBean.getColumn("acno", String.class, acNo, 80);
            flag = ReportBean.getColumn("flag", String.class, "Flag", 0);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                flag = ReportBean.getColumn("flag", String.class, "Flag", 80);
            }
        }
        AbstractColumn segId = ReportBean.getColumn("segId", String.class, "Segment Identifier", 80);
        AbstractColumn dateOfDishonour = ReportBean.getColumn("dateOfDishonour", String.class, "Date of Dishonour", 80);
        AbstractColumn amt = ReportBean.getColumn("amt", String.class, "Amount", 80);
        AbstractColumn instNo = ReportBean.getColumn("instNo", String.class, "Instrument / Cheque Number", 80);
        AbstractColumn noOfTimeDisH = ReportBean.getColumn("noOfTimeDisH", String.class, "Number of times dishonoured", 80);
        AbstractColumn chqIssueDt = ReportBean.getColumn("chqIssueDt", String.class, "Cheque Issue Date", 80);
        AbstractColumn reasonForDisH = ReportBean.getColumn("reasonForDisH", String.class, "Reason for Dishonour", 80);
        AbstractColumn filler = ReportBean.getColumn("filler", String.class, "Filler", 80);
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            fastReport.addColumn(acno);
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                fastReport.addColumn(flag);
            }
        }
        fastReport.addColumn(segId);
        fastReport.addColumn(dateOfDishonour);
        fastReport.addColumn(amt);
        fastReport.addColumn(instNo);
        fastReport.addColumn(noOfTimeDisH);
        fastReport.addColumn(chqIssueDt);
        fastReport.addColumn(reasonForDisH);
        fastReport.addColumn(filler);
        if (this.cibilCompany.equalsIgnoreCase("CIBIL") && this.reportType.equalsIgnoreCase("2") && repType.equalsIgnoreCase("1")) {
            width = width + acno.getWidth();
            if (cibilParameter.equalsIgnoreCase("CIBIL")) {
                width = width + flag.getWidth();
            }
        }
        width = width + segId.getWidth();
        width = width + dateOfDishonour.getWidth();
        width = width + amt.getWidth();
        width = width + instNo.getWidth();
        width = width + noOfTimeDisH.getWidth();
        width = width + chqIssueDt.getWidth();
        width = width + reasonForDisH.getWidth();
        width = width + filler.getWidth();
        Page page = new Page(5330, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle(cibilParameter + "  REPORT COMERCIAL DISHONOUR OF CHEQUES SEGMENT");
        return fastReport;
    }

    public FastReportBuilder genrateDynamicCibilTS() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        AbstractColumn segId = ReportBean.getColumn("segId", String.class, "Segment identifier", 150);
        AbstractColumn noOfBor = ReportBean.getColumn("noOfBor", String.class, "Number of Borrower Segments", 150);
        AbstractColumn noOfCrFacility = ReportBean.getColumn("noOfCrFacility", String.class, "Number of Credit Facility Segments", 150);
        AbstractColumn filler = ReportBean.getColumn("filler", String.class, "Filler", 80);
        fastReport.addColumn(segId);
        fastReport.addColumn(noOfBor);
        fastReport.addColumn(noOfCrFacility);
        fastReport.addColumn(filler);
        width = width + segId.getWidth();
        width = width + noOfBor.getWidth();
        width = width + noOfCrFacility.getWidth();
        width = width + filler.getWidth();
        Page page = new Page(5330, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle(cibilParameter + "  REPORT COMERCIAL FILE CLOSURE SEGMENT");
        return fastReport;
    }

    public FastReportBuilder genrateDaynamicReportExp() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        AbstractColumn memberCode = ReportBean.getColumn("memberCode", String.class, "Member Code/Subscriber Code", 180);
        AbstractColumn memberName = ReportBean.getColumn("memberName", String.class, "Member Name", 200);
        AbstractColumn cycleIdent = ReportBean.getColumn("cycleIdent", String.class, "Cycle Identification", 100);
        AbstractColumn reportDate = ReportBean.getColumn("reportDate", String.class, "Date Report", 100);
        AbstractColumn reportPassword = ReportBean.getColumn("reportPassword", String.class, "Reporting Password", 100);
        AbstractColumn futureUse = ReportBean.getColumn("futureUse", String.class, "Future Use", 100);
        AbstractColumn memberData = ReportBean.getColumn("memberData", String.class, "Member Data", 100);
        AbstractColumn custNameF1 = ReportBean.getColumn("custNameF1", String.class, "Consumer Name Field #1", 150);
        AbstractColumn custNameF2 = ReportBean.getColumn("custNameF2", String.class, "Consumer Name Field #2", 150);
        AbstractColumn custNameF3 = ReportBean.getColumn("custNameF3", String.class, "Consumer Name Field #3", 150);
        AbstractColumn custNameF4 = ReportBean.getColumn("custNameF4", String.class, "Consumer Name Field #4", 150);
        AbstractColumn custNameF5 = ReportBean.getColumn("custNameF5", String.class, "Consumer Name Field #5", 150);

        AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Date of Birth", 80);
        AbstractColumn gender = ReportBean.getColumn("gender", String.class, "Gender", 60);
        AbstractColumn incomeTaxId = ReportBean.getColumn("incomeTaxId", String.class, "Income Tax ID Number", 100);
        AbstractColumn passportNo = ReportBean.getColumn("passportNo", String.class, "Passport Number", 100);
        AbstractColumn voterIdNo = ReportBean.getColumn("voterIdNo", String.class, "Voter ID Number", 100);
        AbstractColumn telephone = ReportBean.getColumn("telephone", String.class, "Telephone", 100);
        AbstractColumn addressLine1 = ReportBean.getColumn("addressLine1", String.class, "Address Line #1", 220);
        AbstractColumn addressLine2 = ReportBean.getColumn("addressLine2", String.class, "Address Line #2", 150);
        AbstractColumn addressLine3 = ReportBean.getColumn("addressLine3", String.class, "Address Line #3", 150);
        AbstractColumn addressLine4 = ReportBean.getColumn("addressLine4", String.class, "Address Line #4", 150);
        AbstractColumn addressLine5 = ReportBean.getColumn("addressLine5", String.class, "Address Line #5", 150);
        AbstractColumn stateCode = ReportBean.getColumn("stateCode", String.class, "State Code", 80);
        AbstractColumn pinCode = ReportBean.getColumn("pinCode", String.class, "PIN Code", 80);

        AbstractColumn reportingMemCode = ReportBean.getColumn("reportingMemCode", String.class, "Current/New Reporting Member Code", 150);
        AbstractColumn memberShortName = ReportBean.getColumn("memberShortName", String.class, "Current/New Member Short Name", 150);
        AbstractColumn currentAcno = ReportBean.getColumn("currentAcno", String.class, "Current/New Account Number", 150);
        AbstractColumn accountType = ReportBean.getColumn("accountType", String.class, "Account Type", 80);
        AbstractColumn accountHolderTypeCode = ReportBean.getColumn("accountHolderTypeCode", String.class, "Ownership Indicator / Account holder type code", 100);
        AbstractColumn disbursDt = ReportBean.getColumn("disbursDt", String.class, "Date Opened/Disbursed", 100);
        AbstractColumn lastPaymentdate = ReportBean.getColumn("lastPaymentdate", String.class, "Date of Last Payment", 100);
        AbstractColumn dateClose = ReportBean.getColumn("dateClose", String.class, "Date Closed", 100);
        AbstractColumn dateReported = ReportBean.getColumn("dateReported", String.class, "Date Reported", 100);
        AbstractColumn sanctAmt = ReportBean.getColumn("sanctAmt", String.class, "High Credit/Sanctioned Amount", 150);
        AbstractColumn currentBalance = ReportBean.getColumn("currentBalance", String.class, "Current Balance", 150);
        AbstractColumn amountOverDue = ReportBean.getColumn("amountOverDue", String.class, "Amount Overdue", 150);
        AbstractColumn noOfDaysPast = ReportBean.getColumn("noOfDaysPast", String.class, "Number of Days Past Due", 150);
        AbstractColumn oldReportingMemCode = ReportBean.getColumn("oldReportingMemCode", String.class, "Old Reporting Member Code", 150);
        AbstractColumn oldMemShortName = ReportBean.getColumn("oldMemShortName", String.class, "Old Member Short Name", 150);
        AbstractColumn oldAccountNo = ReportBean.getColumn("oldAccountNo", String.class, "Old Account Number", 100);
        AbstractColumn writtenOffStatus = ReportBean.getColumn("writtenOffStatus", String.class, "Suit Filed/Wilful Default / Written Off Status", 150);
        AbstractColumn assetClassif = ReportBean.getColumn("assetClassif", String.class, "Asset Classification", 150);


        fastReport.addColumn(memberCode);
        fastReport.addColumn(memberName);
        fastReport.addColumn(cycleIdent);
        fastReport.addColumn(reportDate);
        fastReport.addColumn(reportPassword);
        fastReport.addColumn(futureUse);
        fastReport.addColumn(memberData);
        fastReport.addColumn(custNameF1);
        fastReport.addColumn(custNameF2);
        fastReport.addColumn(custNameF3);
        fastReport.addColumn(custNameF4);
        fastReport.addColumn(custNameF5);
        fastReport.addColumn(dob);
        fastReport.addColumn(gender);
        fastReport.addColumn(incomeTaxId);
        fastReport.addColumn(passportNo);
        fastReport.addColumn(voterIdNo);
        fastReport.addColumn(telephone);
        fastReport.addColumn(addressLine1);
        fastReport.addColumn(addressLine2);
        fastReport.addColumn(addressLine3);
        fastReport.addColumn(addressLine4);
        fastReport.addColumn(addressLine5);
        fastReport.addColumn(stateCode);
        fastReport.addColumn(pinCode);
        fastReport.addColumn(reportingMemCode);
        fastReport.addColumn(memberShortName);
        fastReport.addColumn(currentAcno);
        fastReport.addColumn(accountType);
        fastReport.addColumn(accountHolderTypeCode);
        fastReport.addColumn(disbursDt);
        fastReport.addColumn(lastPaymentdate);
        fastReport.addColumn(dateClose);
        fastReport.addColumn(dateReported);
        fastReport.addColumn(sanctAmt);
        sanctAmt.setStyle(style);
        fastReport.addColumn(currentBalance);
        currentBalance.setStyle(style);
        fastReport.addColumn(amountOverDue);
        amountOverDue.setStyle(style);
        fastReport.addColumn(noOfDaysPast);
        fastReport.addColumn(oldReportingMemCode);
        fastReport.addColumn(oldMemShortName);
        fastReport.addColumn(oldAccountNo);
        fastReport.addColumn(writtenOffStatus);
        fastReport.addColumn(assetClassif);


        width = memberCode.getWidth() + memberName.getWidth() + cycleIdent.getWidth() + reportDate.getWidth() + reportPassword.getWidth()
                + futureUse.getWidth() + memberData.getWidth() + custNameF1.getWidth() + custNameF2.getWidth() + custNameF3.getWidth() + custNameF4.getWidth()
                + custNameF5.getWidth() + dob.getWidth() + gender.getWidth() + incomeTaxId.getWidth()
                + passportNo.getWidth() + voterIdNo.getWidth() + telephone.getWidth() + addressLine1.getWidth() + addressLine2.getWidth() + addressLine3.getWidth()
                + addressLine4.getWidth() + addressLine5.getWidth() + stateCode.getWidth() + pinCode.getWidth()
                + reportingMemCode.getWidth() + memberShortName.getWidth() + currentAcno.getWidth() + accountType.getWidth() + accountHolderTypeCode.getWidth() + disbursDt.getWidth()
                + lastPaymentdate.getWidth() + dateClose.getWidth() + dateReported.getWidth() + sanctAmt.getWidth() + currentBalance.getWidth() + amountOverDue.getWidth()
                + noOfDaysPast.getWidth() + oldReportingMemCode.getWidth() + oldMemShortName.getWidth() + oldAccountNo.getWidth() + writtenOffStatus.getWidth() + assetClassif.getWidth();

        Page page = new Page(5330, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle("EXPERIAN REPORT");
        return fastReport;
    }

    public FastReportBuilder genrateDaynamicReportCibil() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        AbstractColumn memberCode = ReportBean.getColumn("memberCode", String.class, "Member Code/Subscriber Code", 180);
        AbstractColumn memberName = ReportBean.getColumn("memberName", String.class, "Member Name", 200);
        AbstractColumn cycleIdent = ReportBean.getColumn("cycleIdent", String.class, "Cycle Identification", 100);
        AbstractColumn reportDate = ReportBean.getColumn("reportDate", String.class, "Date Report", 100);
        AbstractColumn reportPassword = ReportBean.getColumn("reportPassword", String.class, "Reporting Password", 100);
        AbstractColumn futureUse = ReportBean.getColumn("futureUse", String.class, "Future Use", 100);
        AbstractColumn memberData = ReportBean.getColumn("memberData", String.class, "Member Data", 100);
        AbstractColumn custNameF1 = ReportBean.getColumn("custNameF1", String.class, "Consumer Name Field #1", 150);
        AbstractColumn custNameF2 = ReportBean.getColumn("custNameF2", String.class, "Consumer Name Field #2", 150);
        AbstractColumn custNameF3 = ReportBean.getColumn("custNameF3", String.class, "Consumer Name Field #3", 150);
        AbstractColumn custNameF4 = ReportBean.getColumn("custNameF4", String.class, "Consumer Name Field #4", 150);
        AbstractColumn custNameF5 = ReportBean.getColumn("custNameF5", String.class, "Consumer Name Field #5", 150);

        AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Date of Birth", 80);
        AbstractColumn gender = ReportBean.getColumn("gender", String.class, "Gender", 60);
        AbstractColumn incomeTaxId = ReportBean.getColumn("incomeTaxId", String.class, "Income Tax ID Number", 100);
        AbstractColumn passportNo = ReportBean.getColumn("passportNo", String.class, "Passport Number", 100);
        /*CIBIL Start*/
        AbstractColumn passportIssueDt = ReportBean.getColumn("passportIssueDt", String.class, "Passport Issue Date", 100);
        AbstractColumn passportExpDt = ReportBean.getColumn("passportExpDt", String.class, "Passport Expiry Date", 100);
        /*CIBIL END*/
        AbstractColumn voterIdNo = ReportBean.getColumn("voterIdNo", String.class, "Voter ID Number", 100);

        /*CIBIL Start*/
        AbstractColumn driLicenseNo = ReportBean.getColumn("driLicenseNo", String.class, "Driving License Number", 100);
        AbstractColumn driLicenseIssueDt = ReportBean.getColumn("driLicenseIssueDt", String.class, "Driving License Issue Date", 100);
        AbstractColumn driLicenseExpDt = ReportBean.getColumn("driLicenseExpDt", String.class, "Driving License Expiry Date", 100);
        AbstractColumn rationCardNo = ReportBean.getColumn("rationCardNo", String.class, "Ration Card Number", 100);
        AbstractColumn universalIdNo = ReportBean.getColumn("universalIdNo", String.class, "Universal ID Number", 100);
        AbstractColumn addId1 = ReportBean.getColumn("addId1", String.class, "Additional ID #1", 100);
        AbstractColumn addId2 = ReportBean.getColumn("addId2", String.class, "Additional ID #2", 100);
        /*CIBIL End*/

        AbstractColumn telephone = ReportBean.getColumn("telephone", String.class, "Telephone", 100);

        /*CIBIL Start*/
        AbstractColumn telNoResidence = ReportBean.getColumn("telNoResidence", String.class, "Telephone No.Residence", 100);
        AbstractColumn telNoOffice = ReportBean.getColumn("telNoOffice", String.class, "Telephone No.Office", 100);
        AbstractColumn extOffice = ReportBean.getColumn("extOffice", String.class, "Extension Office", 100);
        AbstractColumn telNoOther = ReportBean.getColumn("telNoOther", String.class, "Telephone No.Other ", 100);
        AbstractColumn extOther = ReportBean.getColumn("extOther", String.class, "Extension Other", 100);
        AbstractColumn emailId1 = ReportBean.getColumn("emailId1", String.class, "Email ID 1", 100);
        AbstractColumn emailId2 = ReportBean.getColumn("emailId2", String.class, "Email ID 2", 100);
        /*CIBIL End*/


        AbstractColumn addressLine1 = ReportBean.getColumn("addressLine1", String.class, "Address Line #1", 220);
        AbstractColumn addressLine2 = ReportBean.getColumn("addressLine2", String.class, "Address Line #2", 150);
        AbstractColumn addressLine3 = ReportBean.getColumn("addressLine3", String.class, "Address Line #3", 150);
        AbstractColumn addressLine4 = ReportBean.getColumn("addressLine4", String.class, "Address Line #4", 150);
        AbstractColumn addressLine5 = ReportBean.getColumn("addressLine5", String.class, "Address Line #5", 150);
        AbstractColumn stateCode = ReportBean.getColumn("stateCode", String.class, "State Code 1", 80);
        AbstractColumn pinCode = ReportBean.getColumn("pinCode", String.class, "PIN Code 1", 80);

        /*CIBIL Start*/
        AbstractColumn addressCategory1 = ReportBean.getColumn("addressCategory1", String.class, "Address Category 1", 200);
        AbstractColumn residenceCode1 = ReportBean.getColumn("residenceCode1", String.class, "Residence Code 1", 100);
        AbstractColumn address2 = ReportBean.getColumn("address2", String.class, "Address 2", 200);
        AbstractColumn stateCode2 = ReportBean.getColumn("stateCode2", String.class, "State Code 2", 100);
        AbstractColumn pinCode2 = ReportBean.getColumn("pinCode2", String.class, "PIN Code 2", 100);
        AbstractColumn addressCategory2 = ReportBean.getColumn("addressCategory2", String.class, "Address Category 2", 200);
        AbstractColumn residenceCode2 = ReportBean.getColumn("residenceCode2", String.class, "Residence Code 2", 100);
        /*CIBIL End*/


        AbstractColumn reportingMemCode = ReportBean.getColumn("reportingMemCode", String.class, "Current/New Reporting Member Code", 150);
        AbstractColumn memberShortName = ReportBean.getColumn("memberShortName", String.class, "Current/New Member Short Name", 150);
        AbstractColumn currentAcno = ReportBean.getColumn("currentAcno", String.class, "Current/New Account Number", 150);
        AbstractColumn accountType = ReportBean.getColumn("accountType", String.class, "Account Type", 80);
        AbstractColumn accountHolderTypeCode = ReportBean.getColumn("accountHolderTypeCode", String.class, "Ownership Indicator / Account holder type code", 100);
        AbstractColumn disbursDt = ReportBean.getColumn("disbursDt", String.class, "Date Opened/Disbursed", 100);
        AbstractColumn lastPaymentdate = ReportBean.getColumn("lastPaymentdate", String.class, "Date of Last Payment", 100);
        AbstractColumn dateClose = ReportBean.getColumn("dateClose", String.class, "Date Closed", 100);
        AbstractColumn dateReported = ReportBean.getColumn("dateReported", String.class, "Date Reported", 100);
        AbstractColumn sanctAmt = ReportBean.getColumn("sanctAmt", String.class, "High Credit/Sanctioned Amount", 150);
        AbstractColumn currentBalance = ReportBean.getColumn("currentBalance", String.class, "Current Balance", 150);
        AbstractColumn amountOverDue = ReportBean.getColumn("amountOverDue", String.class, "Amount Overdue", 150);
        AbstractColumn noOfDaysPast = ReportBean.getColumn("noOfDaysPast", String.class, "Number of Days Past Due", 150);
        AbstractColumn oldReportingMemCode = ReportBean.getColumn("oldReportingMemCode", String.class, "Old Reporting Member Code", 150);
        AbstractColumn oldMemShortName = ReportBean.getColumn("oldMemShortName", String.class, "Old Member Short Name", 150);
        AbstractColumn oldAccountNo = ReportBean.getColumn("oldAccountNo", String.class, "Old Account Number", 100);

        /*CIBIL Start*/
        AbstractColumn oldAccType = ReportBean.getColumn("oldAccType", String.class, "Old Acc Type", 100);
        AbstractColumn oldOwnershipIndicator = ReportBean.getColumn("oldOwnershipIndicator", String.class, "Old Ownership Indicator", 100);
        /*CIBIL END*/

        AbstractColumn writtenOffStatus = ReportBean.getColumn("writtenOffStatus", String.class, "Suit Filed/Wilful Default", 150);

        /*CIBIL Start*/
        AbstractColumn writtenSettledStatus = ReportBean.getColumn("writtenSettledStatus", String.class, "SWritten-off and Settled Status", 100);
        /*CIBIL End*/

        AbstractColumn assetClassif = ReportBean.getColumn("assetClassif", String.class, "Asset Classification", 150);

        /*CIBIL Start*/
        AbstractColumn valueOfCollateral = ReportBean.getColumn("valueOfCollateral", String.class, "Value of Collateral", 100);
        AbstractColumn typeOfCollateral = ReportBean.getColumn("typeOfCollateral", String.class, "Type of Collateral", 100);
        AbstractColumn creditLimit = ReportBean.getColumn("creditLimit", String.class, "Credit Limit", 100);
        AbstractColumn cashLimit = ReportBean.getColumn("cashLimit", String.class, "Cash Limit", 100);
        AbstractColumn rateOfInterest = ReportBean.getColumn("rateOfInterest", String.class, "Rate of Interest", 100);
        AbstractColumn repaymentTenure = ReportBean.getColumn("repaymentTenure", String.class, "RepaymentTenure", 100);
        AbstractColumn emiAmt = ReportBean.getColumn("emiAmt", String.class, "EMI Amount", 100);
        AbstractColumn writtenOffAmountTotal = ReportBean.getColumn("writtenOffAmountTotal", String.class, "Written- off Amount (Total)", 100);
        AbstractColumn writtenOffPrincipalAmount = ReportBean.getColumn("writtenOffPrincipalAmount", String.class, "Written- off Principal Amount", 100);
        AbstractColumn settlementAmt = ReportBean.getColumn("settlementAmt", String.class, "Settlement Amt", 100);
        AbstractColumn paymentFrequency = ReportBean.getColumn("paymentFrequency", String.class, "Payment Frequency", 100);
        AbstractColumn actualPaymentAmt = ReportBean.getColumn("actualPaymentAmt", String.class, "Actual Payment Amt", 100);
        AbstractColumn occupationCode = ReportBean.getColumn("occupationCode", String.class, "Occupation Code", 100);
        AbstractColumn income = ReportBean.getColumn("income", String.class, "Income", 100);
        AbstractColumn netGrossIncomeIndicator = ReportBean.getColumn("netGrossIncomeIndicator", String.class, "Net/Gross Income Indicator", 100);
        AbstractColumn monthlyAnnualIncomeIndicator = ReportBean.getColumn("monthlyAnnualIncomeIndicator", String.class, "Monthly/Annual Income Indicator", 100);
        /*CIBIL End*/



        fastReport.addColumn(memberCode);
        fastReport.addColumn(memberName);
        fastReport.addColumn(cycleIdent);
        fastReport.addColumn(reportDate);
        fastReport.addColumn(reportPassword);
        fastReport.addColumn(futureUse);
        fastReport.addColumn(memberData);
        fastReport.addColumn(custNameF1);
        fastReport.addColumn(custNameF2);
        fastReport.addColumn(custNameF3);
        fastReport.addColumn(custNameF4);
        fastReport.addColumn(custNameF5);
        fastReport.addColumn(dob);
        fastReport.addColumn(gender);
        fastReport.addColumn(incomeTaxId);
        fastReport.addColumn(passportNo);
        /**
         * *
         */
        fastReport.addColumn(passportIssueDt);
        fastReport.addColumn(passportExpDt);
        /**
         * *******
         */
        fastReport.addColumn(voterIdNo);

        /**
         * *
         */
        fastReport.addColumn(driLicenseNo);
        fastReport.addColumn(driLicenseIssueDt);
        fastReport.addColumn(driLicenseExpDt);
        fastReport.addColumn(rationCardNo);
        fastReport.addColumn(universalIdNo);
        fastReport.addColumn(addId1);
        fastReport.addColumn(addId2);
        /**
         * *******
         */
        fastReport.addColumn(telephone);
        /**
         * *
         */
        fastReport.addColumn(telNoResidence);
        fastReport.addColumn(telNoOffice);
        fastReport.addColumn(extOffice);
        fastReport.addColumn(telNoOther);
        fastReport.addColumn(extOther);
        fastReport.addColumn(emailId1);
        fastReport.addColumn(emailId2);
        /**
         * *******
         */
        fastReport.addColumn(addressLine1);
        fastReport.addColumn(addressLine2);
        fastReport.addColumn(addressLine3);
        fastReport.addColumn(addressLine4);
        fastReport.addColumn(addressLine5);
        fastReport.addColumn(stateCode);
        fastReport.addColumn(pinCode);

        /**
         * *
         */
        fastReport.addColumn(addressCategory1);
        fastReport.addColumn(residenceCode1);
        fastReport.addColumn(address2);
        fastReport.addColumn(stateCode2);
        fastReport.addColumn(pinCode2);
        fastReport.addColumn(addressCategory2);
        fastReport.addColumn(residenceCode2);
        /**
         * ********
         */
        fastReport.addColumn(reportingMemCode);
        fastReport.addColumn(memberShortName);
        fastReport.addColumn(currentAcno);
        fastReport.addColumn(accountType);
        fastReport.addColumn(accountHolderTypeCode);
        fastReport.addColumn(disbursDt);
        fastReport.addColumn(lastPaymentdate);
        fastReport.addColumn(dateClose);
        fastReport.addColumn(dateReported);
        fastReport.addColumn(sanctAmt);
        sanctAmt.setStyle(style);
        fastReport.addColumn(currentBalance);
        sanctAmt.setStyle(style);
        fastReport.addColumn(amountOverDue);
        amountOverDue.setStyle(style);
        fastReport.addColumn(noOfDaysPast);
        fastReport.addColumn(oldReportingMemCode);
        fastReport.addColumn(oldMemShortName);
        fastReport.addColumn(oldAccountNo);
        /**
         * *
         */
        fastReport.addColumn(oldAccType);
        fastReport.addColumn(oldOwnershipIndicator);
        /**
         * ********
         */
        fastReport.addColumn(writtenOffStatus);
        /**
         * *
         */
        fastReport.addColumn(writtenSettledStatus);
        /**
         * ********
         */
        fastReport.addColumn(assetClassif);

        /**
         * *
         */
        fastReport.addColumn(valueOfCollateral);
        fastReport.addColumn(typeOfCollateral);
        fastReport.addColumn(creditLimit);
        fastReport.addColumn(cashLimit);
        fastReport.addColumn(rateOfInterest);
        fastReport.addColumn(repaymentTenure);
        fastReport.addColumn(emiAmt);
        fastReport.addColumn(writtenOffAmountTotal);
        fastReport.addColumn(writtenOffPrincipalAmount);
        fastReport.addColumn(settlementAmt);
        fastReport.addColumn(paymentFrequency);
        fastReport.addColumn(actualPaymentAmt);
        fastReport.addColumn(occupationCode);
        fastReport.addColumn(income);
        fastReport.addColumn(netGrossIncomeIndicator);
        fastReport.addColumn(monthlyAnnualIncomeIndicator);
        /**
         * *******
         */
        width = memberCode.getWidth() + memberName.getWidth() + cycleIdent.getWidth() + reportDate.getWidth() + reportPassword.getWidth()
                + futureUse.getWidth() + memberData.getWidth() + custNameF1.getWidth() + custNameF2.getWidth() + custNameF3.getWidth() + custNameF4.getWidth()
                + custNameF5.getWidth() + dob.getWidth() + gender.getWidth() + incomeTaxId.getWidth()
                + passportNo.getWidth()
                + /**
                 * *
                 */
                passportIssueDt.getWidth() + passportExpDt.getWidth()
                + /**
                 * *******
                 */
                voterIdNo.getWidth()
                + /**
                 * *
                 */
                driLicenseNo.getWidth() + driLicenseIssueDt.getWidth() + driLicenseExpDt.getWidth() + rationCardNo.getWidth()
                + universalIdNo.getWidth() + addId1.getWidth() + addId2.getWidth()
                + /**
                 * *******
                 */
                telephone.getWidth()
                + /**
                 * *
                 */
                telNoResidence.getWidth() + telNoOffice.getWidth() + extOffice.getWidth() + telNoOther.getWidth()
                + extOther.getWidth() + emailId1.getWidth() + emailId2.getWidth()
                + /**
                 * *******
                 */
                addressLine1.getWidth() + addressLine2.getWidth() + addressLine3.getWidth()
                + addressLine4.getWidth() + addressLine5.getWidth() + stateCode.getWidth() + pinCode.getWidth()
                + /**
                 * *
                 */
                addressCategory1.getWidth() + residenceCode1.getWidth() + address2.getWidth() + stateCode2.getWidth()
                + pinCode2.getWidth() + addressCategory2.getWidth() + residenceCode2.getWidth()
                + /**
                 * *******
                 */
                reportingMemCode.getWidth() + memberShortName.getWidth() + currentAcno.getWidth() + accountType.getWidth() + accountHolderTypeCode.getWidth() + disbursDt.getWidth()
                + lastPaymentdate.getWidth() + dateClose.getWidth() + dateReported.getWidth() + sanctAmt.getWidth() + currentBalance.getWidth() + amountOverDue.getWidth()
                + noOfDaysPast.getWidth() + oldReportingMemCode.getWidth() + oldMemShortName.getWidth() + oldAccountNo.getWidth()
                + /**
                 * *
                 */
                oldAccType.getWidth() + oldOwnershipIndicator.getWidth()
                + /**
                 * *******
                 */
                writtenOffStatus.getWidth()
                + /**
                 * *
                 */
                writtenSettledStatus.getWidth()
                + /**
                 * *******
                 */
                assetClassif.getWidth()
                + /**
                 * *
                 */
                valueOfCollateral.getWidth()
                + typeOfCollateral.getWidth()
                + creditLimit.getWidth()
                + cashLimit.getWidth()
                + rateOfInterest.getWidth()
                + repaymentTenure.getWidth()
                + emiAmt.getWidth()
                + writtenOffAmountTotal.getWidth()
                + writtenOffPrincipalAmount.getWidth()
                + settlementAmt.getWidth()
                + paymentFrequency.getWidth()
                + actualPaymentAmt.getWidth()
                + occupationCode.getWidth()
                + income.getWidth()
                + netGrossIncomeIndicator.getWidth()
                + monthlyAnnualIncomeIndicator.getWidth();
        /**
         * *******
         */
        Page page = new Page(9830, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle("CIBIL REPORT");
        return fastReport;
    }

    public FastReportBuilder genrateDaynamicReportCibil3() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
//        AbstractColumn memberCode = ReportBean.getColumn("memberCode", String.class, "Member Code/Subscriber Code", 180);
//        AbstractColumn memberName = ReportBean.getColumn("memberName", String.class, "Member Name", 200);
//        AbstractColumn cycleIdent = ReportBean.getColumn("cycleIdent", String.class, "Cycle Identification",100);        
//        AbstractColumn reportDate = ReportBean.getColumn("reportDate", String.class, "Date Report", 100);
//        AbstractColumn reportPassword = ReportBean.getColumn("reportPassword", String.class, "Reporting Password", 100);
//        AbstractColumn futureUse = ReportBean.getColumn("futureUse", String.class, "Future Use", 100);       
//        AbstractColumn memberData = ReportBean.getColumn("memberData", String.class, "Member Data",100);
        AbstractColumn custNameF1 = ReportBean.getColumn("custNameF1", String.class, "Consumer Name", 150);

        AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Date of Birth", 80);
        AbstractColumn gender = ReportBean.getColumn("gender", String.class, "Gender", 60);
        AbstractColumn incomeTaxId = ReportBean.getColumn("incomeTaxId", String.class, "Income Tax ID Number", 100);
        AbstractColumn passportNo = ReportBean.getColumn("passportNo", String.class, "Passport Number", 100);
        /*CIBIL Start*/
        AbstractColumn passportIssueDt = ReportBean.getColumn("passportIssueDt", String.class, "Passport Issue Date", 100);
        AbstractColumn passportExpDt = ReportBean.getColumn("passportExpDt", String.class, "Passport Expiry Date", 100);
        /*CIBIL END*/
        AbstractColumn voterIdNo = ReportBean.getColumn("voterIdNo", String.class, "Voter ID Number", 100);

        /*CIBIL Start*/
        AbstractColumn driLicenseNo = ReportBean.getColumn("driLicenseNo", String.class, "Driving License Number", 100);
        AbstractColumn driLicenseIssueDt = ReportBean.getColumn("driLicenseIssueDt", String.class, "Driving License Issue Date", 100);
        AbstractColumn driLicenseExpDt = ReportBean.getColumn("driLicenseExpDt", String.class, "Driving License Expiry Date", 100);
        AbstractColumn rationCardNo = ReportBean.getColumn("rationCardNo", String.class, "Ration Card Number", 100);
        AbstractColumn universalIdNo = ReportBean.getColumn("universalIdNo", String.class, "Universal ID Number", 100);
        AbstractColumn addId1 = ReportBean.getColumn("addId1", String.class, "Additional ID #1", 100);
        AbstractColumn addId2 = ReportBean.getColumn("addId2", String.class, "Additional ID #2", 100);
        /*CIBIL End*/

        AbstractColumn telephone = ReportBean.getColumn("telephone", String.class, "Telephone No.Mobile", 100);

        /*CIBIL Start*/
        AbstractColumn telNoResidence = ReportBean.getColumn("telNoResidence", String.class, "Telephone No.Residence", 100);
        AbstractColumn telNoOffice = ReportBean.getColumn("telNoOffice", String.class, "Telephone No.Office", 100);
        AbstractColumn extOffice = ReportBean.getColumn("extOffice", String.class, "Extension Office", 100);
        AbstractColumn telNoOther = ReportBean.getColumn("telNoOther", String.class, "Telephone No.Other ", 100);
        AbstractColumn extOther = ReportBean.getColumn("extOther", String.class, "Extension Other", 100);
        AbstractColumn emailId1 = ReportBean.getColumn("emailId1", String.class, "Email ID 1", 100);
        AbstractColumn emailId2 = ReportBean.getColumn("emailId2", String.class, "Email ID 2", 100);
        /*CIBIL End*/


        AbstractColumn addressLine1 = ReportBean.getColumn("addressLine1", String.class, "Address 1", 220);
        AbstractColumn stateCode = ReportBean.getColumn("stateCode", String.class, "State Code 1", 80);
        AbstractColumn pinCode = ReportBean.getColumn("pinCode", String.class, "PIN Code 1", 80);

        /*CIBIL Start*/
        AbstractColumn addressCategory1 = ReportBean.getColumn("addressCategory1", String.class, "Address Category 1", 200);
        AbstractColumn residenceCode1 = ReportBean.getColumn("residenceCode1", String.class, "Residence Code 1", 100);
        AbstractColumn address2 = ReportBean.getColumn("address2", String.class, "Address 2", 200);
        AbstractColumn stateCode2 = ReportBean.getColumn("stateCode2", String.class, "State Code 2", 100);
        AbstractColumn pinCode2 = ReportBean.getColumn("pinCode2", String.class, "PIN Code 2", 100);
        AbstractColumn addressCategory2 = ReportBean.getColumn("addressCategory2", String.class, "Address Category 2", 200);
        AbstractColumn residenceCode2 = ReportBean.getColumn("residenceCode2", String.class, "Residence Code 2", 100);
        /*CIBIL End*/


        AbstractColumn reportingMemCode = ReportBean.getColumn("reportingMemCode", String.class, "Current/New Member Code", 150);
        AbstractColumn memberShortName = ReportBean.getColumn("memberShortName", String.class, "Current/New Member Short Name", 150);
        AbstractColumn currentAcno = ReportBean.getColumn("currentAcno", String.class, "Curr/New Account No", 150);
        AbstractColumn accountType = ReportBean.getColumn("accountType", String.class, "Account Type", 80);
        AbstractColumn accountHolderTypeCode = ReportBean.getColumn("accountHolderTypeCode", String.class, "Ownership Indicator", 100);
        AbstractColumn disbursDt = ReportBean.getColumn("disbursDt", String.class, "Date Opened/Disbursed", 100);
        AbstractColumn lastPaymentdate = ReportBean.getColumn("lastPaymentdate", String.class, "Date of Last Payment", 100);
        AbstractColumn dateClose = ReportBean.getColumn("dateClose", String.class, "Date Closed", 100);
        AbstractColumn dateReported = ReportBean.getColumn("dateReported", String.class, "Date Reported", 100);
        AbstractColumn sanctAmt = ReportBean.getColumn("sanctAmt", String.class, "High Credit/Sanctioned Amt", 150);
        AbstractColumn currentBalance = ReportBean.getColumn("currentBalance", String.class, "Current  Balance", 150);
        AbstractColumn amountOverDue = ReportBean.getColumn("amountOverDue", String.class, "Amt Overdue", 150);
        AbstractColumn noOfDaysPast = ReportBean.getColumn("noOfDaysPast", String.class, "No of Days Past Due", 150);
        AbstractColumn oldReportingMemCode = ReportBean.getColumn("oldReportingMemCode", String.class, "Old Mbr Code", 150);
        AbstractColumn oldMemShortName = ReportBean.getColumn("oldMemShortName", String.class, "Old Mbr Short Name", 150);
        AbstractColumn oldAccountNo = ReportBean.getColumn("oldAccountNo", String.class, "Old Acc No", 100);

        /*CIBIL Start*/
        AbstractColumn oldAccType = ReportBean.getColumn("oldAccType", String.class, "Old Acc Type", 100);
        AbstractColumn oldOwnershipIndicator = ReportBean.getColumn("oldOwnershipIndicator", String.class, "Old Ownership Indicator", 100);
        /*CIBIL END*/

        AbstractColumn writtenOffStatus = ReportBean.getColumn("writtenOffStatus", String.class, "Suit Filed / Wilful Default", 150);

        /*CIBIL Start*/
        AbstractColumn writtenSettledStatus = ReportBean.getColumn("writtenSettledStatus", String.class, "Written-off and Settled Status", 100);
        /*CIBIL End*/

        AbstractColumn assetClassif = ReportBean.getColumn("assetClassif", String.class, "Asset Classification", 150);

        /*CIBIL Start*/
        AbstractColumn valueOfCollateral = ReportBean.getColumn("valueOfCollateral", String.class, "Value of Collateral", 100);
        AbstractColumn typeOfCollateral = ReportBean.getColumn("typeOfCollateral", String.class, "Type of Collateral", 100);
        AbstractColumn creditLimit = ReportBean.getColumn("creditLimit", String.class, "Credit Limit", 100);
        AbstractColumn cashLimit = ReportBean.getColumn("cashLimit", String.class, "Cash Limit", 100);
        AbstractColumn rateOfInterest = ReportBean.getColumn("rateOfInterest", String.class, "Rate of Interest", 100);
        AbstractColumn repaymentTenure = ReportBean.getColumn("repaymentTenure", String.class, "RepaymentTenure", 100);
        AbstractColumn emiAmt = ReportBean.getColumn("emiAmt", String.class, "EMI Amount", 100);
        AbstractColumn writtenOffAmountTotal = ReportBean.getColumn("writtenOffAmountTotal", String.class, "Written- off Amount (Total) ", 100);
        AbstractColumn writtenOffPrincipalAmount = ReportBean.getColumn("writtenOffPrincipalAmount", String.class, "Written- off Principal Amount", 100);
        AbstractColumn settlementAmt = ReportBean.getColumn("settlementAmt", String.class, "Settlement Amt", 100);
        AbstractColumn paymentFrequency = ReportBean.getColumn("paymentFrequency", String.class, "Payment Frequency", 100);
        AbstractColumn actualPaymentAmt = ReportBean.getColumn("actualPaymentAmt", String.class, "Actual Payment Amt", 100);
        AbstractColumn occupationCode = ReportBean.getColumn("occupationCode", String.class, "Occupation Code", 100);
        AbstractColumn income = ReportBean.getColumn("income", String.class, "Income", 100);
        AbstractColumn netGrossIncomeIndicator = ReportBean.getColumn("netGrossIncomeIndicator", String.class, "Net/Gross Income Indicator", 100);
        AbstractColumn monthlyAnnualIncomeIndicator = ReportBean.getColumn("monthlyAnnualIncomeIndicator", String.class, "Monthly/Annual Income Indicator", 100);
        /*CIBIL End*/



//        fastReport.addColumn(memberCode);
//        fastReport.addColumn(memberName);
//        fastReport.addColumn(cycleIdent);
//        fastReport.addColumn(reportDate);
//        fastReport.addColumn(reportPassword);
//        fastReport.addColumn(futureUse);
//        fastReport.addColumn(memberData);
        fastReport.addColumn(custNameF1);
//        fastReport.addColumn(custNameF2);
//        fastReport.addColumn(custNameF3);
//        fastReport.addColumn(custNameF4);
//        fastReport.addColumn(custNameF5);
        fastReport.addColumn(dob);
        fastReport.addColumn(gender);
        fastReport.addColumn(incomeTaxId);
        fastReport.addColumn(passportNo);
        /**
         * *
         */
        fastReport.addColumn(passportIssueDt);
        fastReport.addColumn(passportExpDt);
        /**
         * *******
         */
        fastReport.addColumn(voterIdNo);

        /**
         * *
         */
        fastReport.addColumn(driLicenseNo);
        fastReport.addColumn(driLicenseIssueDt);
        fastReport.addColumn(driLicenseExpDt);
        fastReport.addColumn(rationCardNo);
        fastReport.addColumn(universalIdNo);
        fastReport.addColumn(addId1);
        fastReport.addColumn(addId2);
        /**
         * *******
         */
        fastReport.addColumn(telephone);
        /**
         * *
         */
        fastReport.addColumn(telNoResidence);
        fastReport.addColumn(telNoOffice);
        fastReport.addColumn(extOffice);
        fastReport.addColumn(telNoOther);
        fastReport.addColumn(extOther);
        fastReport.addColumn(emailId1);
        fastReport.addColumn(emailId2);
        /**
         * *******
         */
        fastReport.addColumn(addressLine1);
//        fastReport.addColumn(addressLine2);
//        fastReport.addColumn(addressLine3);
//        fastReport.addColumn(addressLine4);
//        fastReport.addColumn(addressLine5);           
        fastReport.addColumn(stateCode);
        fastReport.addColumn(pinCode);

        /**
         * *
         */
        fastReport.addColumn(addressCategory1);
        fastReport.addColumn(residenceCode1);
        fastReport.addColumn(address2);
        fastReport.addColumn(stateCode2);
        fastReport.addColumn(pinCode2);
        fastReport.addColumn(addressCategory2);
        fastReport.addColumn(residenceCode2);
        /**
         * ********
         */
        fastReport.addColumn(reportingMemCode);
        fastReport.addColumn(memberShortName);
        fastReport.addColumn(currentAcno);
        fastReport.addColumn(accountType);
        fastReport.addColumn(accountHolderTypeCode);
        fastReport.addColumn(disbursDt);
        fastReport.addColumn(lastPaymentdate);
        fastReport.addColumn(dateClose);
        fastReport.addColumn(dateReported);
        fastReport.addColumn(sanctAmt);
        sanctAmt.setStyle(style);
        fastReport.addColumn(currentBalance);
        currentBalance.setStyle(style);
        fastReport.addColumn(amountOverDue);
        amountOverDue.setStyle(style);
        fastReport.addColumn(noOfDaysPast);
        fastReport.addColumn(oldReportingMemCode);
        fastReport.addColumn(oldMemShortName);
        fastReport.addColumn(oldAccountNo);
        /**
         * *
         */
        fastReport.addColumn(oldAccType);
        fastReport.addColumn(oldOwnershipIndicator);
        /**
         * ********
         */
        fastReport.addColumn(writtenOffStatus);
        /**
         * *
         */
        fastReport.addColumn(writtenSettledStatus);
        /**
         * ********
         */
        fastReport.addColumn(assetClassif);

        /**
         * *
         */
        fastReport.addColumn(valueOfCollateral);
        fastReport.addColumn(typeOfCollateral);
        fastReport.addColumn(creditLimit);
        fastReport.addColumn(cashLimit);
        fastReport.addColumn(rateOfInterest);
        fastReport.addColumn(repaymentTenure);
        fastReport.addColumn(emiAmt);
        fastReport.addColumn(writtenOffAmountTotal);
        fastReport.addColumn(writtenOffPrincipalAmount);
        fastReport.addColumn(settlementAmt);
        fastReport.addColumn(paymentFrequency);
        fastReport.addColumn(actualPaymentAmt);
        fastReport.addColumn(occupationCode);
        fastReport.addColumn(income);
        fastReport.addColumn(netGrossIncomeIndicator);
        fastReport.addColumn(monthlyAnnualIncomeIndicator);
        /**
         * *******
         */
        width =
                //                 memberCode.getWidth() + memberName.getWidth() + cycleIdent.getWidth() + reportDate.getWidth() + reportPassword.getWidth() 
                //                + futureUse.getWidth() + memberData.getWidth()  + 
                custNameF1.getWidth() + /*custNameF2.getWidth() + custNameF3.getWidth() + custNameF4.getWidth() +
                 custNameF5.getWidth() +*/ dob.getWidth() + gender.getWidth() + incomeTaxId.getWidth()
                + passportNo.getWidth()
                + /**
                 * *
                 */
                passportIssueDt.getWidth() + passportExpDt.getWidth()
                + /**
                 * *******
                 */
                voterIdNo.getWidth()
                + /**
                 * *
                 */
                driLicenseNo.getWidth() + driLicenseIssueDt.getWidth() + driLicenseExpDt.getWidth() + rationCardNo.getWidth()
                + universalIdNo.getWidth() + addId1.getWidth() + addId2.getWidth()
                + /**
                 * *******
                 */
                telephone.getWidth()
                + /**
                 * *
                 */
                telNoResidence.getWidth() + telNoOffice.getWidth() + extOffice.getWidth() + telNoOther.getWidth()
                + extOther.getWidth() + emailId1.getWidth() + emailId2.getWidth()
                + /**
                 * *******
                 */
                addressLine1.getWidth() /*+ addressLine2.getWidth() + addressLine3.getWidth() +
                 addressLine4.getWidth() + addressLine5.getWidth()*/ + stateCode.getWidth() + pinCode.getWidth()
                + /**
                 * *
                 */
                addressCategory1.getWidth() + residenceCode1.getWidth() + address2.getWidth() + stateCode2.getWidth()
                + pinCode2.getWidth() + addressCategory2.getWidth() + residenceCode2.getWidth()
                + /**
                 * *******
                 */
                reportingMemCode.getWidth() + memberShortName.getWidth() + currentAcno.getWidth() + accountType.getWidth() + accountHolderTypeCode.getWidth() + disbursDt.getWidth()
                + lastPaymentdate.getWidth() + dateClose.getWidth() + dateReported.getWidth() + sanctAmt.getWidth() + currentBalance.getWidth() + amountOverDue.getWidth()
                + noOfDaysPast.getWidth() + oldReportingMemCode.getWidth() + oldMemShortName.getWidth() + oldAccountNo.getWidth()
                + /**
                 * *
                 */
                oldAccType.getWidth() + oldOwnershipIndicator.getWidth()
                + /**
                 * *******
                 */
                writtenOffStatus.getWidth()
                + /**
                 * *
                 */
                writtenSettledStatus.getWidth()
                + /**
                 * *******
                 */
                assetClassif.getWidth()
                + /**
                 * *
                 */
                valueOfCollateral.getWidth()
                + typeOfCollateral.getWidth()
                + creditLimit.getWidth()
                + cashLimit.getWidth()
                + rateOfInterest.getWidth()
                + repaymentTenure.getWidth()
                + emiAmt.getWidth()
                + writtenOffAmountTotal.getWidth()
                + writtenOffPrincipalAmount.getWidth()
                + settlementAmt.getWidth()
                + paymentFrequency.getWidth()
                + actualPaymentAmt.getWidth()
                + occupationCode.getWidth()
                + income.getWidth()
                + netGrossIncomeIndicator.getWidth()
                + monthlyAnnualIncomeIndicator.getWidth();
        /**
         * *******
         */
        Page page = new Page(9830, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle("CIBIL REPORT");
        return fastReport;
    }

    public FastReportBuilder genrateDaynamicReportExperianComercial() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        /*        ECICI Field	
         * Member Code / Subscriber Code	
         * Previous Member Code / Subscriber Code	
         * Member Name	
         * Date Reported	
         * Future Use	
         * Branch Code	
         * Borrower's Name	
         * Short Name	
         * Borrower Office Location Type	
         * Address Line 1	
         * Address Line 2	
         * Address Line 3	
         * City/Town	
         * District	
         * State / Union Territory	
         * Pin Code	
         * Telephone number / s	
         * Telephone Area Code	
         * Fax Number/s	
         * Fax Area Code	
         * Registered Office Address Line 1	
         * Registered Office Address Line 2	
         * Registered Office Address Line 3	
         * Registered Office City/Town	
         * Registered Office District	
         * Registered Office State / Union Territory	
         * Registered Office Pin Code	
         * Registered Office Telephone number / s	
         * Registered Office Telephone Area Code	
         * Registered Office Fax Number/s	
         * Registered Office Fax Area Code	
         * PAN	
         * Borrower’s Legal Constitution	
         * Class_Of_Activity1	
         * Class_Of_Activity2	
         * Class_Of_Activity3	
         * Filler	
         * Relationship Data	
         * Related Type	
         * PAN	
         * Relationship	
         * Business Entity Name	
         * Individual Name Prefix	
         * Full Name	
         * Percentage of Control	
         * Address Line 1	
         * Address Line 2	
         * Address Line 3	
         * City/Town	
         * District	
         * State/Union Territory	
         * PIN Code	
         * Country
         * Telephone Number(s)	
         * Telephone Area Code
         * Filler	
         * Credit Account Data	
         * Account Number	
         * Previous Account Number	
         * Sanction Date	
         * Sanction Amount	
         * Currency Code	
         * Credit Type	
         * Drawing Power	
         * Current Balance / Outstanding Amount	
         * Asset Classification	
         * Bank Remark Code	
         * Willful Default Status	
         * Date Classified as Willful Default	
         * Suit Filed Status	
         * Suit Reference Number	
         * Suit Amount in Rupees	
         * Date of Suit	
         * Filler	
         * Guarantor data	
         * Guarantor Type	
         * Guarantor PAN	
         * Guarantor Name	
         * Individual Name Prefix	
         * Full Name	
         * Address Line 1	
         * Address Line 2	
         * Address Line 3	
         * City/Town	
         * District	
         * State / Union Territory	
         * Pin Code	
         * Country	
         * Telephone number / s	
         * Telephone Area Code	
         * Filler
         */
        AbstractColumn memberCode = ReportBean.getColumn("memberCode", String.class, "Member Code / Subscriber Code", 100);
        AbstractColumn preMemberCode = ReportBean.getColumn("preMemberCode", String.class, "Previous Member Code / Subscriber Code", 100);
        AbstractColumn memberName = ReportBean.getColumn("memberName", String.class, "Member Name", 60);
        AbstractColumn reportDate = ReportBean.getColumn("reportDate", String.class, "Date Reported", 100);
        AbstractColumn futureUse = ReportBean.getColumn("futureUse", String.class, "Future Use", 100);
        AbstractColumn brnCode = ReportBean.getColumn("brnCode", String.class, "Branch Code", 100);
        AbstractColumn borName = ReportBean.getColumn("borName", String.class, "Borrower's Name", 125);
        AbstractColumn shortName = ReportBean.getColumn("shortName", String.class, "Short Name", 100);
        AbstractColumn borOfficeLocation = ReportBean.getColumn("borOfficeLocation", String.class, "Borrower Office Location Type", 100);
        AbstractColumn borAdd1 = ReportBean.getColumn("borAdd1", String.class, "Address Line 1", 100);
        AbstractColumn borAdd2 = ReportBean.getColumn("borAdd2", String.class, "Address Line 2", 100);
        AbstractColumn borAdd3 = ReportBean.getColumn("borAdd3", String.class, "Address Line 3", 100);
        AbstractColumn borCity = ReportBean.getColumn("borCity", String.class, "City/Town", 100);
        AbstractColumn borDistrict = ReportBean.getColumn("borDistrict", String.class, "District", 100);
        AbstractColumn borState = ReportBean.getColumn("borState", String.class, "State / Union Territory", 100);
        AbstractColumn borPin = ReportBean.getColumn("borPin", String.class, "Pin Code", 100);
        AbstractColumn borTelNo = ReportBean.getColumn("borTelNo", String.class, "Telephone number / s", 100);
        AbstractColumn borTelAreaCode = ReportBean.getColumn("borTelAreaCode", String.class, "Telephone Area Code", 100);
        AbstractColumn borFaxNo = ReportBean.getColumn("borFaxNo", String.class, "Fax Number/s", 100);
        AbstractColumn borFaxAreaCode = ReportBean.getColumn("borFaxAreaCode", String.class, "Fax Area Code", 100);
        AbstractColumn roAdd1 = ReportBean.getColumn("roAdd1", String.class, "Registered Office Address Line 1", 100);
        AbstractColumn roAdd2 = ReportBean.getColumn("roAdd2", String.class, "Registered Office Address Line 2", 100);
        AbstractColumn roAdd3 = ReportBean.getColumn("roAdd3", String.class, "Registered Office Address Line 3", 100);
        AbstractColumn roCity = ReportBean.getColumn("roCity", String.class, "Registered Office City/Town", 100);
        AbstractColumn roDistrict = ReportBean.getColumn("roDistrict", String.class, "Registered Office District", 100);
        AbstractColumn roState = ReportBean.getColumn("roState", String.class, "Registered Office State / Union Territory", 100);
        AbstractColumn roPin = ReportBean.getColumn("roPin", String.class, "Registered Office Pin Code", 100);
        AbstractColumn roTelNo = ReportBean.getColumn("roTelNo", String.class, "Registered Office Telephone number / s", 100);
        AbstractColumn roTelAreaCode = ReportBean.getColumn("roTelAreaCode", String.class, "Registered Office Telephone Area Code", 100);
        AbstractColumn roFaxNo = ReportBean.getColumn("roFaxNo", String.class, "Registered Office Fax Number/s", 100);
        AbstractColumn roFaxAreaCode = ReportBean.getColumn("roFaxAreaCode", String.class, "Registered Office Fax Area Code", 100);
        AbstractColumn borPan = ReportBean.getColumn("borPan", String.class, "PAN", 100);
        AbstractColumn borLglConstitution = ReportBean.getColumn("borLglConstitution", String.class, "Borrower’s Legal Constitution", 100);
        AbstractColumn classOfAct1 = ReportBean.getColumn("classOfAct1", String.class, "Class_Of_Activity1", 100);
        AbstractColumn classOfAct2 = ReportBean.getColumn("classOfAct2", String.class, "Class_Of_Activity2", 100);
        AbstractColumn classOfAct3 = ReportBean.getColumn("classOfAct3", String.class, "Class_Of_Activity3", 100);
        AbstractColumn filler1 = ReportBean.getColumn("filler1", String.class, "Filler", 100);
        AbstractColumn relationshipData = ReportBean.getColumn("relationshipData", String.class, "Relationship Data", 100);
        AbstractColumn relType = ReportBean.getColumn("relType", String.class, "Related Type", 100);
        AbstractColumn relPan = ReportBean.getColumn("relPan", String.class, "PAN", 100);
        AbstractColumn relationship = ReportBean.getColumn("relationship", String.class, "Relationship", 100);
        AbstractColumn businessEntityName = ReportBean.getColumn("businessEntityName", String.class, "Business Entity Name", 100);
        AbstractColumn jtCustPrefix = ReportBean.getColumn("jtCustPrefix", String.class, "Individual Name Prefix", 100);
        AbstractColumn jtCustName = ReportBean.getColumn("jtCustName", String.class, "Full Name", 100);
        AbstractColumn percControl = ReportBean.getColumn("percControl", String.class, "Percentage of Control", 100);
        AbstractColumn jtAdd1 = ReportBean.getColumn("jtAdd1", String.class, "Address Line 1", 100);
        AbstractColumn jtAdd2 = ReportBean.getColumn("jtAdd2", String.class, "Address Line 2", 100);
        AbstractColumn jtAdd3 = ReportBean.getColumn("jtAdd1", String.class, "Address Line 3", 100);
        AbstractColumn jtCity = ReportBean.getColumn("jtCity", String.class, "City/Town", 100);
        AbstractColumn jtDistrict = ReportBean.getColumn("jtDistrict", String.class, "District", 100);
        AbstractColumn jtState = ReportBean.getColumn("jtState", String.class, "State/Union Territory", 100);
        AbstractColumn jtPin = ReportBean.getColumn("jtPin", String.class, "PIN Code", 100);
        AbstractColumn jtCountry = ReportBean.getColumn("jtCountry", String.class, "Country", 100);
        AbstractColumn jtTelNo = ReportBean.getColumn("jtTelNo", String.class, "Telephone Number(s)", 100);
        AbstractColumn jtTelAreaCode = ReportBean.getColumn("jtTelAreaCode", String.class, "Telephone Area Code", 100);
        AbstractColumn filler2 = ReportBean.getColumn("filler2", String.class, "Filler", 100);
        AbstractColumn crAcData = ReportBean.getColumn("crAcData", String.class, "Credit Account Data", 100);
        AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account Number", 100);
        AbstractColumn preAcno = ReportBean.getColumn("preAcno", String.class, "Previous Account Number", 100);
        AbstractColumn sanctDt = ReportBean.getColumn("sanctDt", String.class, "Sanction Date", 100);
        AbstractColumn sancAmt = ReportBean.getColumn("sancAmt", String.class, "Sanction Amount", 100);
        AbstractColumn currencyCode = ReportBean.getColumn("currencyCode", String.class, "Currency Code", 100);
        AbstractColumn creditType = ReportBean.getColumn("creditType", String.class, "Credit Type", 100);
        AbstractColumn drawingPower = ReportBean.getColumn("drawingPower", String.class, "Drawing Power", 100);
        AbstractColumn outstanding = ReportBean.getColumn("outstanding", String.class, "Current Balance / Outstanding Amount", 100);
        AbstractColumn assetClassif = ReportBean.getColumn("assetClassif", String.class, "Asset Classification", 100);
        AbstractColumn bnkRemarkCode = ReportBean.getColumn("bnkRemarkCode", String.class, "Bank Remark Code", 100);
        AbstractColumn defaultStatus = ReportBean.getColumn("defaultStatus", String.class, "Willful Default Status", 100);
        AbstractColumn defaultStatusDt = ReportBean.getColumn("defaultStatusDt", String.class, "Date Classified as Willful Default", 100);
        AbstractColumn suitStatus = ReportBean.getColumn("suitStatus", String.class, "Suit Filed Status", 100);
        AbstractColumn suitRefNo = ReportBean.getColumn("suitRefNo", String.class, "Suit Reference Number", 100);
        AbstractColumn suitAmt = ReportBean.getColumn("suitAmt", String.class, "Suit Amount in Rupees", 100);
        AbstractColumn suitDt = ReportBean.getColumn("suitDt", String.class, "Date of Suit", 100);
        AbstractColumn filler3 = ReportBean.getColumn("filler3", String.class, "Filler", 100);
        AbstractColumn guarantorData = ReportBean.getColumn("guarantorData", String.class, "Guarantor data", 100);
        AbstractColumn guarantorType = ReportBean.getColumn("guarantorType", String.class, "Guarantor Type", 100);
        AbstractColumn guarantorPan = ReportBean.getColumn("guarantorPan", String.class, "Guarantor PAN", 100);
        AbstractColumn guarantorName = ReportBean.getColumn("guarantorName", String.class, "Guarantor Name", 100);
        AbstractColumn guarantorPrefix = ReportBean.getColumn("guarantorPrefix", String.class, "Individual Name Prefix", 100);
        AbstractColumn guarantorFullName = ReportBean.getColumn("guarantorFullName", String.class, "Full Name", 100);
        AbstractColumn guarantorAdd1 = ReportBean.getColumn("guarantorAdd1", String.class, "Address Line 1", 100);
        AbstractColumn guarantorAdd2 = ReportBean.getColumn("guarantorAdd2", String.class, "Address Line 2", 100);
        AbstractColumn guarantorAdd3 = ReportBean.getColumn("guarantorAdd3", String.class, "Address Line 3", 100);
        AbstractColumn guarantorCity = ReportBean.getColumn("guarantorCity", String.class, "City/Town", 100);
        AbstractColumn guarantorDistrict = ReportBean.getColumn("guarantorDistrict", String.class, "District", 100);
        AbstractColumn guarantorState = ReportBean.getColumn("guarantorState", String.class, "State / Union Territory", 100);
        AbstractColumn guarantorPin = ReportBean.getColumn("guarantorPin", String.class, "Pin Code", 100);
        AbstractColumn guarantorCountry = ReportBean.getColumn("guarantorCountry", String.class, "Country", 100);
        AbstractColumn guarantorTelNo = ReportBean.getColumn("guarantorTelNo", String.class, "Telephone number / s", 100);
        AbstractColumn guarantorTelAreaCode = ReportBean.getColumn("guarantorTelAreaCode", String.class, "Telephone Area Code", 100);
        AbstractColumn filler4 = ReportBean.getColumn("filler4", String.class, "Filler", 100);
        fastReport.addColumn(memberCode);
        fastReport.addColumn(preMemberCode);
        fastReport.addColumn(memberName);
        fastReport.addColumn(reportDate);
        fastReport.addColumn(futureUse);
        fastReport.addColumn(brnCode);
        fastReport.addColumn(borName);
        fastReport.addColumn(shortName);
        fastReport.addColumn(borOfficeLocation);
        fastReport.addColumn(borAdd1);
        fastReport.addColumn(borAdd2);
        fastReport.addColumn(borAdd3);
        fastReport.addColumn(borCity);
        fastReport.addColumn(borDistrict);
        fastReport.addColumn(borState);
        fastReport.addColumn(borPin);
        fastReport.addColumn(borTelNo);
        fastReport.addColumn(borTelAreaCode);
        fastReport.addColumn(borFaxNo);
        fastReport.addColumn(borFaxAreaCode);
        fastReport.addColumn(roAdd1);
        fastReport.addColumn(roAdd2);
        fastReport.addColumn(roAdd3);
        fastReport.addColumn(roCity);
        fastReport.addColumn(roDistrict);
        fastReport.addColumn(roState);
        fastReport.addColumn(roPin);
        fastReport.addColumn(roTelNo);
        fastReport.addColumn(roTelAreaCode);
        fastReport.addColumn(roFaxNo);
        fastReport.addColumn(roFaxAreaCode);
        fastReport.addColumn(borPan);
        fastReport.addColumn(borLglConstitution);
        fastReport.addColumn(classOfAct1);
        fastReport.addColumn(classOfAct2);
        fastReport.addColumn(classOfAct3);
        fastReport.addColumn(filler1);
        fastReport.addColumn(relationshipData);
        fastReport.addColumn(relType);
        fastReport.addColumn(relPan);
        fastReport.addColumn(relationship);
        fastReport.addColumn(businessEntityName);
        fastReport.addColumn(jtCustPrefix);
        fastReport.addColumn(jtCustName);
        fastReport.addColumn(percControl);
        fastReport.addColumn(jtAdd1);
        fastReport.addColumn(jtAdd2);
        fastReport.addColumn(jtAdd3);
        fastReport.addColumn(jtCity);
        fastReport.addColumn(jtDistrict);
        fastReport.addColumn(jtState);
        fastReport.addColumn(jtPin);
        fastReport.addColumn(jtCountry);
        fastReport.addColumn(jtTelNo);
        fastReport.addColumn(jtTelAreaCode);
        fastReport.addColumn(filler2);
        fastReport.addColumn(crAcData);
        fastReport.addColumn(acNo);
        fastReport.addColumn(preAcno);
        fastReport.addColumn(sanctDt);
        fastReport.addColumn(sancAmt);
        fastReport.addColumn(currencyCode);
        fastReport.addColumn(creditType);
        fastReport.addColumn(drawingPower);
        fastReport.addColumn(outstanding);
        fastReport.addColumn(assetClassif);
        fastReport.addColumn(bnkRemarkCode);
        fastReport.addColumn(defaultStatus);
        fastReport.addColumn(defaultStatusDt);
        fastReport.addColumn(suitStatus);
        fastReport.addColumn(suitRefNo);
        fastReport.addColumn(suitAmt);
        fastReport.addColumn(suitDt);
        fastReport.addColumn(filler3);
        fastReport.addColumn(guarantorData);
        fastReport.addColumn(guarantorType);
        fastReport.addColumn(guarantorPan);
        fastReport.addColumn(guarantorName);
        fastReport.addColumn(guarantorPrefix);
        fastReport.addColumn(guarantorFullName);
        fastReport.addColumn(guarantorAdd1);
        fastReport.addColumn(guarantorAdd2);
        fastReport.addColumn(guarantorAdd3);
        fastReport.addColumn(guarantorCity);
        fastReport.addColumn(guarantorDistrict);
        fastReport.addColumn(guarantorState);
        fastReport.addColumn(guarantorPin);
        fastReport.addColumn(guarantorCountry);
        fastReport.addColumn(guarantorTelNo);
        fastReport.addColumn(guarantorTelAreaCode);
        fastReport.addColumn(filler4);
        width = memberCode.getWidth() + preMemberCode.getWidth() + memberName.getWidth() + reportDate.getWidth() + futureUse.getWidth()
                + brnCode.getWidth() + borName.getWidth() + shortName.getWidth() + borOfficeLocation.getWidth() + borAdd1.getWidth()
                + borAdd2.getWidth() + borAdd3.getWidth() + borCity.getWidth() + borDistrict.getWidth() + borState.getWidth()
                + borPan.getWidth() + borTelNo.getWidth() + borTelAreaCode.getWidth() + borFaxNo.getWidth() + borFaxAreaCode.getWidth()
                + roAdd1.getWidth() + roAdd2.getWidth() + roAdd3.getWidth() + roCity.getWidth() + roDistrict.getWidth() + roState.getWidth()
                + roPin.getWidth() + roTelNo.getWidth() + roTelAreaCode.getWidth() + roFaxNo.getWidth() + roFaxAreaCode.getWidth()
                + borPin.getWidth() + borLglConstitution.getWidth() + classOfAct1.getWidth() + classOfAct2.getWidth() + classOfAct3.getWidth()
                + filler1.getWidth() + relationshipData.getWidth() + relType.getWidth() + relPan.getWidth() + relationship.getWidth()
                + businessEntityName.getWidth() + jtCustPrefix.getWidth() + jtCustName.getWidth() + percControl.getWidth()
                + jtAdd1.getWidth() + jtAdd2.getWidth() + jtAdd3.getWidth() + jtCity.getWidth() + jtDistrict.getWidth() + jtState.getWidth()
                + jtPin.getWidth() + jtCountry.getWidth() + jtTelNo.getWidth() + jtTelAreaCode.getWidth() + filler2.getWidth()
                + crAcData.getWidth() + acNo.getWidth() + preAcno.getWidth() + sanctDt.getWidth() + sancAmt.getWidth()
                + currencyCode.getWidth() + creditType.getWidth() + drawingPower.getWidth()
                + outstanding.getWidth() + assetClassif.getWidth() + bnkRemarkCode.getWidth() + defaultStatus.getWidth()
                + defaultStatusDt.getWidth() + suitStatus.getWidth() + suitRefNo.getWidth() + suitAmt.getWidth() + suitDt.getWidth()
                + filler3.getWidth() + guarantorData.getWidth() + guarantorType.getWidth() + guarantorPan.getWidth() + guarantorName.getWidth()
                + guarantorPrefix.getWidth() + guarantorFullName.getWidth() + guarantorAdd1.getWidth() + guarantorAdd2.getWidth()
                + guarantorAdd3.getWidth() + guarantorCity.getWidth() + guarantorDistrict.getWidth() + guarantorState.getWidth()
                + guarantorPin.getWidth() + guarantorCountry.getWidth() + guarantorTelNo.getWidth() + guarantorTelAreaCode.getWidth() + filler4.getWidth();

        Page page = new Page(9830, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle("EXPERIAN COMERCIAL");
        return fastReport;
    }

    public FastReportBuilder genrateDaynamicReportCic() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        AbstractColumn memberCode = ReportBean.getColumn("memberCode", String.class, "Member Number", 180);
        AbstractColumn memberName = ReportBean.getColumn("memberName", String.class, "Client Name", 200);
//        AbstractColumn cycleIdent = ReportBean.getColumn("cycleIdent", String.class, "Cycle Identification",100);        
//        AbstractColumn reportDate = ReportBean.getColumn("reportDate", String.class, "Date Report", 100);
//        AbstractColumn reportPassword = ReportBean.getColumn("reportPassword", String.class, "Reporting Password", 100);
//        AbstractColumn futureUse = ReportBean.getColumn("futureUse", String.class, "Future Use", 100);       
//        AbstractColumn memberData = ReportBean.getColumn("memberData", String.class, "Member Data",100);
        AbstractColumn custNameF1 = ReportBean.getColumn("custNameF1", String.class, "Primary Counsumer Name1", 150);
        AbstractColumn custNameF2 = ReportBean.getColumn("custNameF2", String.class, "Primary Counsumer Name2", 150);
        AbstractColumn custNameF3 = ReportBean.getColumn("custNameF3", String.class, "Primary Counsumer Name3", 150);
        AbstractColumn custNameF4 = ReportBean.getColumn("custNameF4", String.class, "Primary Counsumer Name4", 150);
        AbstractColumn custNameF5 = ReportBean.getColumn("custNameF5", String.class, "Primary Counsumer Name5", 150);

        AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Date of Birth", 80);
        AbstractColumn gender = ReportBean.getColumn("gender", String.class, "Gender", 60);
        AbstractColumn incomeTaxId = ReportBean.getColumn("incomeTaxId", String.class, "PAN Number", 100);
        AbstractColumn passportNo = ReportBean.getColumn("passportNo", String.class, "Passport Number", 100);
        /*CIBIL Start*/
//        AbstractColumn passportIssueDt = ReportBean.getColumn("passportIssueDt", String.class, "Passport Issue Date", 100);
//        AbstractColumn passportExpDt = ReportBean.getColumn("passportExpDt", String.class, "Passport Expiry Date", 100);
        /*CIBIL END*/
        AbstractColumn voterIdNo = ReportBean.getColumn("voterIdNo", String.class, "Voter ID Number", 100);

        /*CIBIL Start*/
//        AbstractColumn driLicenseNo = ReportBean.getColumn("driLicenseNo", String.class, "Driving License Number", 100);
//        AbstractColumn driLicenseIssueDt = ReportBean.getColumn("driLicenseIssueDt", String.class, "Driving License Issue Date", 100);
//        AbstractColumn driLicenseExpDt = ReportBean.getColumn("driLicenseExpDt", String.class, "Driving License Expiry Date", 100);
//        AbstractColumn rationCardNo = ReportBean.getColumn("rationCardNo", String.class, "Ration Card Number", 100);
        AbstractColumn universalIdNo = ReportBean.getColumn("universalIdNo", String.class, "Other ID", 100);
//        AbstractColumn addId1 = ReportBean.getColumn("addId1", String.class, "Additional ID #1", 100);
//        AbstractColumn addId2 = ReportBean.getColumn("addId2", String.class, "Additional ID #2", 100);
        /*CIBIL End*/

        AbstractColumn telephone = ReportBean.getColumn("telephone", String.class, "Telephone Number", 100);

        /*CIBIL Start*/
//        AbstractColumn telNoResidence = ReportBean.getColumn("telNoResidence", String.class, "Telephone No.Residence", 100);
//        AbstractColumn telNoOffice = ReportBean.getColumn("telNoOffice", String.class, "Telephone No.Office", 100);
//        AbstractColumn extOffice = ReportBean.getColumn("extOffice", String.class, "Extension Office", 100);
//        AbstractColumn telNoOther  = ReportBean.getColumn("telNoOther", String.class, "Telephone No.Other ", 100);
//        AbstractColumn extOther = ReportBean.getColumn("extOther", String.class, "Extension Other", 100);
//        AbstractColumn emailId1 = ReportBean.getColumn("emailId1", String.class, "Email ID 1", 100);
//        AbstractColumn emailId2 = ReportBean.getColumn("emailId2", String.class, "Email ID 2", 100);
        /*CIBIL End*/


        AbstractColumn addressLine1 = ReportBean.getColumn("addressLine1", String.class, "Address1", 220);
        AbstractColumn addressLine2 = ReportBean.getColumn("addressLine2", String.class, "Address2", 150);
        AbstractColumn addressLine3 = ReportBean.getColumn("addressLine3", String.class, "Address3", 150);
        AbstractColumn addressLine4 = ReportBean.getColumn("addressLine4", String.class, "Address4", 150);
        AbstractColumn addressLine5 = ReportBean.getColumn("addressLine5", String.class, "Address5", 150);
        AbstractColumn stateCode = ReportBean.getColumn("stateCode", String.class, "State", 80);
        AbstractColumn pinCode = ReportBean.getColumn("pinCode", String.class, "PIN Code", 80);

        /*CIBIL Start*/
//        AbstractColumn addressCategory1 = ReportBean.getColumn("addressCategory1", String.class, "Address Category 1", 200);
//        AbstractColumn residenceCode1 = ReportBean.getColumn("residenceCode1", String.class, "Residence Code 1", 100);
//        AbstractColumn address2 = ReportBean.getColumn("address2", String.class, "Address 2", 200);
//        AbstractColumn stateCode2 = ReportBean.getColumn("stateCode2", String.class, "State Code 2", 100);
//        AbstractColumn pinCode2 = ReportBean.getColumn("pinCode2", String.class, "PIN Code 2", 100);
//        AbstractColumn addressCategory2 = ReportBean.getColumn("addressCategory2", String.class, "Address Category 2", 200);
//        AbstractColumn residenceCode2 = ReportBean.getColumn("residenceCode2", String.class, "Residence Code 2", 100);
        /*CIBIL End*/


//        AbstractColumn reportingMemCode = ReportBean.getColumn("reportingMemCode", String.class, "Current/New Reporting Member Code", 150);
//        AbstractColumn memberShortName   = ReportBean.getColumn("memberShortName", String.class, "Current/New Member Short Name", 150);
        AbstractColumn currentAcno = ReportBean.getColumn("currentAcno", String.class, "Account Number", 150);
        AbstractColumn accountType = ReportBean.getColumn("accountType", String.class, "Account Type", 80);
        AbstractColumn accountHolderTypeCode = ReportBean.getColumn("accountHolderTypeCode", String.class, "Ownership ID", 100);
        AbstractColumn disbursDt = ReportBean.getColumn("disbursDt", String.class, "Date Opened", 100);
        AbstractColumn lastPaymentdate = ReportBean.getColumn("lastPaymentdate", String.class, "Date of Last Payment", 100);
        AbstractColumn dateClose = ReportBean.getColumn("dateClose", String.class, "Date Closed", 100);
        AbstractColumn dateReported = ReportBean.getColumn("dateReported", String.class, "Date Reported", 100);
        AbstractColumn sanctAmt = ReportBean.getColumn("sanctAmt", String.class, "Sanctioned Amount", 150);
        AbstractColumn currentBalance = ReportBean.getColumn("currentBalance", String.class, "Current Balance", 150);
        AbstractColumn amountOverDue = ReportBean.getColumn("amountOverDue", String.class, "Overdue Amount", 150);
        AbstractColumn noOfDaysPast = ReportBean.getColumn("noOfDaysPast", String.class, "Days Past Due", 150);
//        AbstractColumn oldReportingMemCode = ReportBean.getColumn("oldReportingMemCode", String.class, "Old Reporting Member Code", 150);
//        AbstractColumn oldMemShortName = ReportBean.getColumn("oldMemShortName", String.class, "Old Member Short Name", 150);
        AbstractColumn oldAccountNo = ReportBean.getColumn("oldAccountNo", String.class, "Previous Account Number", 100);

        /*CIBIL Start*/
//        AbstractColumn oldAccType = ReportBean.getColumn("oldAccType", String.class, "Old Acc Type", 100);
//        AbstractColumn oldOwnershipIndicator = ReportBean.getColumn("oldOwnershipIndicator", String.class, "Old Ownership Indicator", 100);
        /*CIBIL END*/

        AbstractColumn writtenOffStatus = ReportBean.getColumn("writtenOffStatus", String.class, "Suit Filed Status", 150);

        /*CIBIL Start*/
//        AbstractColumn writtenSettledStatus = ReportBean.getColumn("writtenSettledStatus", String.class, "SWritten-off and Settled Status", 100);
        /*CIBIL End*/

        AbstractColumn assetClassif = ReportBean.getColumn("assetClassif", String.class, "Asset Classification Code", 150);

        /*CIBIL Start*/
//        AbstractColumn valueOfCollateral = ReportBean.getColumn("valueOfCollateral", String.class, "Value of Collateral", 100);
//        AbstractColumn typeOfCollateral   = ReportBean.getColumn("typeOfCollateral", String.class, "Type of Collateral", 100);
//        AbstractColumn creditLimit = ReportBean.getColumn("creditLimit", String.class, "Credit Limit",100);
//        AbstractColumn cashLimit = ReportBean.getColumn("cashLimit", String.class, "Cash Limit", 100);
//        AbstractColumn rateOfInterest = ReportBean.getColumn("rateOfInterest", String.class, "Rate of Interest",100);
//        AbstractColumn repaymentTenure = ReportBean.getColumn("repaymentTenure", String.class, "RepaymentTenure",100);
//        AbstractColumn emiAmt = ReportBean.getColumn("emiAmt", String.class, "EMI Amount", 100);
//        AbstractColumn writtenOffAmountTotal = ReportBean.getColumn("writtenOffAmountTotal", String.class, "Written- off Amount (Total)", 100);
//        AbstractColumn writtenOffPrincipalAmount = ReportBean.getColumn("writtenOffPrincipalAmount", String.class, "Written- off Principal Amount", 100);
//        AbstractColumn settlementAmt = ReportBean.getColumn("settlementAmt", String.class, "Settlement Amt", 100);
//        AbstractColumn paymentFrequency = ReportBean.getColumn("paymentFrequency", String.class, "Payment Frequency", 100);
//        AbstractColumn actualPaymentAmt = ReportBean.getColumn("actualPaymentAmt",String.class, "Actual Payment Amt", 100);
//        AbstractColumn occupationCode   = ReportBean.getColumn("occupationCode", String.class, "Occupation Code", 100);
//        AbstractColumn income = ReportBean.getColumn("income", String.class, "Income", 100);
//        AbstractColumn netGrossIncomeIndicator = ReportBean.getColumn("netGrossIncomeIndicator", String.class, "Net/Gross Income Indicator", 100);
//        AbstractColumn monthlyAnnualIncomeIndicator = ReportBean.getColumn("monthlyAnnualIncomeIndicator", String.class, "Monthly/Annual Income Indicator", 100);
        /*CIBIL End*/



        fastReport.addColumn(memberCode);
        fastReport.addColumn(memberName);
//        fastReport.addColumn(cycleIdent);
//        fastReport.addColumn(reportDate);
//        fastReport.addColumn(reportPassword);
//        fastReport.addColumn(futureUse);
//        fastReport.addColumn(memberData);
        fastReport.addColumn(custNameF1);
        fastReport.addColumn(custNameF2);
        fastReport.addColumn(custNameF3);
        fastReport.addColumn(custNameF4);
        fastReport.addColumn(custNameF5);
        fastReport.addColumn(dob);
        fastReport.addColumn(gender);
        fastReport.addColumn(incomeTaxId);
        fastReport.addColumn(passportNo);
        /**
         * *
         */
//        fastReport.addColumn(passportIssueDt);
//        fastReport.addColumn(passportExpDt);
        /**
         * *******
         */
        fastReport.addColumn(voterIdNo);

        /**
         * *
         */
//        fastReport.addColumn(driLicenseNo);
//        fastReport.addColumn(driLicenseIssueDt);
//        fastReport.addColumn(driLicenseExpDt);
//        fastReport.addColumn(rationCardNo);
        fastReport.addColumn(universalIdNo);
//        fastReport.addColumn(addId1);
//        fastReport.addColumn(addId2);
        /**
         * *******
         */
        fastReport.addColumn(telephone);
        /**
         * *
         */
//        fastReport.addColumn(telNoResidence);
//        fastReport.addColumn(telNoOffice);
//        fastReport.addColumn(extOffice);
//        fastReport.addColumn(telNoOther);
//        fastReport.addColumn(extOther);
//        fastReport.addColumn(emailId1);
//        fastReport.addColumn(emailId2);
        /**
         * *******
         */
        fastReport.addColumn(addressLine1);
        fastReport.addColumn(addressLine2);
        fastReport.addColumn(addressLine3);
        fastReport.addColumn(addressLine4);
        fastReport.addColumn(addressLine5);
        fastReport.addColumn(stateCode);
        fastReport.addColumn(pinCode);

        /**
         * *
         */
//        fastReport.addColumn(addressCategory1);
//        fastReport.addColumn(residenceCode1);
//        fastReport.addColumn(address2);
//        fastReport.addColumn(stateCode2);
//        fastReport.addColumn(pinCode2);
//        fastReport.addColumn(addressCategory2);
//        fastReport.addColumn(residenceCode2);
        /**
         * ********
         */
//        fastReport.addColumn(reportingMemCode);
//        fastReport.addColumn(memberShortName);
        fastReport.addColumn(currentAcno);
        fastReport.addColumn(accountType);
        fastReport.addColumn(accountHolderTypeCode);
        fastReport.addColumn(disbursDt);
        fastReport.addColumn(lastPaymentdate);
        fastReport.addColumn(dateClose);
        fastReport.addColumn(dateReported);
        fastReport.addColumn(sanctAmt);
        sanctAmt.setStyle(style);
        fastReport.addColumn(currentBalance);
        sanctAmt.setStyle(style);
        fastReport.addColumn(amountOverDue);
        amountOverDue.setStyle(style);
        fastReport.addColumn(noOfDaysPast);
//        fastReport.addColumn(oldReportingMemCode);
//        fastReport.addColumn(oldMemShortName);
        fastReport.addColumn(oldAccountNo);
        /**
         * *
         */
//        fastReport.addColumn(oldAccType);
//        fastReport.addColumn(oldOwnershipIndicator);
        /**
         * ********
         */
        fastReport.addColumn(writtenOffStatus);
        /**
         * *
         */
//        fastReport.addColumn(writtenSettledStatus);
        /**
         * ********
         */
        fastReport.addColumn(assetClassif);

        /**
         * *
         */
//        fastReport.addColumn(valueOfCollateral);
//        fastReport.addColumn(typeOfCollateral);
//        fastReport.addColumn(creditLimit);
//        fastReport.addColumn(cashLimit);
//        fastReport.addColumn(rateOfInterest);
//        fastReport.addColumn(repaymentTenure);
//        fastReport.addColumn(emiAmt);
//        fastReport.addColumn(writtenOffAmountTotal);
//        fastReport.addColumn(writtenOffPrincipalAmount);
//        fastReport.addColumn(settlementAmt);
//        fastReport.addColumn(paymentFrequency);
//        fastReport.addColumn(actualPaymentAmt);
//        fastReport.addColumn(occupationCode);
//        fastReport.addColumn(income);
//        fastReport.addColumn(netGrossIncomeIndicator);
//        fastReport.addColumn(monthlyAnnualIncomeIndicator);
        /**
         * *******
         */
        width = memberCode.getWidth() + memberName.getWidth()
                + //                 cycleIdent.getWidth() + reportDate.getWidth() + reportPassword.getWidth() 
                //                 + futureUse.getWidth() + memberData.getWidth()  + 
                custNameF1.getWidth() + custNameF2.getWidth() + custNameF3.getWidth() + custNameF4.getWidth()
                + custNameF5.getWidth() + dob.getWidth() + gender.getWidth() + incomeTaxId.getWidth()
                + passportNo.getWidth()
                + /**
                 * *
                 */
                //                 passportIssueDt.getWidth()+passportExpDt.getWidth()+
                /**
                 * *******
                 */
                voterIdNo.getWidth()
                + /**
                 * *
                 */
                //                 driLicenseNo.getWidth()+driLicenseIssueDt.getWidth()+driLicenseExpDt.getWidth()+rationCardNo.getWidth()+
                universalIdNo.getWidth()
                + //                 addId1.getWidth()+addId2.getWidth()+
                /**
                 * *******
                 */
                telephone.getWidth()
                + /**
                 * *
                 */
                //                 telNoResidence.getWidth()+telNoOffice.getWidth()+extOffice.getWidth()+telNoOther.getWidth()+
                //                 extOther.getWidth()+emailId1.getWidth()+emailId2.getWidth()+
                /**
                 * *******
                 */
                addressLine1.getWidth() + addressLine2.getWidth() + addressLine3.getWidth()
                + addressLine4.getWidth() + addressLine5.getWidth() + stateCode.getWidth() + pinCode.getWidth()
                + /**
                 * *
                 */
                //                 addressCategory1.getWidth()+residenceCode1.getWidth()+address2.getWidth()+stateCode2.getWidth()+
                //                 pinCode2.getWidth()+addressCategory2.getWidth()+residenceCode2.getWidth()+
                /**
                 * *******
                 */
                //                 reportingMemCode.getWidth() + memberShortName.getWidth() + 
                currentAcno.getWidth() + accountType.getWidth() + accountHolderTypeCode.getWidth() + disbursDt.getWidth()
                + lastPaymentdate.getWidth() + dateClose.getWidth() + dateReported.getWidth() + sanctAmt.getWidth() + currentBalance.getWidth() + amountOverDue.getWidth()
                + noOfDaysPast.getWidth()
                + //                 oldReportingMemCode.getWidth() + oldMemShortName.getWidth() + 
                oldAccountNo.getWidth()
                + /**
                 * *
                 */
                //                 oldAccType.getWidth()+oldOwnershipIndicator.getWidth()+
                /**
                 * *******
                 */
                writtenOffStatus.getWidth()
                + /**
                 * *
                 */
                //                 writtenSettledStatus.getWidth()+
                /**
                 * *******
                 */
                assetClassif.getWidth();
//                 +

        /**
         * *
         */
//                    valueOfCollateral.getWidth()+
//                    typeOfCollateral.getWidth()+
//                    creditLimit.getWidth()+
//                    cashLimit.getWidth()+
//                    rateOfInterest.getWidth()+
//                    repaymentTenure.getWidth()+
//                    emiAmt.getWidth()+
//                    writtenOffAmountTotal.getWidth()+
//                    writtenOffPrincipalAmount.getWidth()+
//                    settlementAmt.getWidth()+
//                    paymentFrequency.getWidth()+
//                    actualPaymentAmt.getWidth()+
//                    occupationCode.getWidth()+
//                    income.getWidth()+
//                    netGrossIncomeIndicator.getWidth()+
//                    monthlyAnnualIncomeIndicator.getWidth();
        /**
         * *******
         */
        Page page = new Page(9830, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle(" Equifax India (CIC) Report");
        return fastReport;
    }

    public FastReportBuilder genrateDaynamicReportComercial() {
        int width = 0;
        Style style = new Style();
        style.setHorizontalAlign(HorizontalAlign.RIGHT);
        style.setBorder(Border.THIN);
        FastReportBuilder fastReport = new FastReportBuilder();
        /*MEMBER ID	REPORTING/ CYCLE DATE	BORROWER’S NAME	BORROWER SHORT NAME	COMPANY REGISTRATION NUMBER
         * DATE OF INCORPORATION	PAN	CIN	TIN	SERVICE TAX NUMBER	OTHER ID	BORROWER’S LEGAL CONSTITUTION	
         * BUSINESS CATEGORY	BUSINESS/ INDUSTRY TYPE	CLASS OF ACTIVITY 1	CLASS OF ACTIVITY 2	CLASS OF ACTIVITY 3
         */
        AbstractColumn memberCode = ReportBean.getColumn("memberCode", String.class, "MEMBER ID", 100);
        AbstractColumn reportDate = ReportBean.getColumn("reportDate", String.class, "REPORTING/ CYCLE DATE", 100);
        AbstractColumn memberName = ReportBean.getColumn("memberName", String.class, "BORROWER’S NAME", 200);
        AbstractColumn shortName = ReportBean.getColumn("shortName", String.class, "BORROWER SHORT NAME", 100);
        AbstractColumn regNo = ReportBean.getColumn("regNo", String.class, "COMPANY REGISTRATION NUMBER", 100);
        AbstractColumn dateOfIncorp = ReportBean.getColumn("dateOfIncorp", String.class, "DATE OF INCORPORATION", 100);
        AbstractColumn pan = ReportBean.getColumn("pan", String.class, "PAN Number", 100);
        AbstractColumn cin = ReportBean.getColumn("cin", String.class, "CIN", 100);
        AbstractColumn tin = ReportBean.getColumn("tin", String.class, "TIN", 100);
        AbstractColumn sTax = ReportBean.getColumn("sTax", String.class, "Service Tax Number", 100);
        AbstractColumn otherId = ReportBean.getColumn("otherId", String.class, "Other ID", 100);
        AbstractColumn constNo = ReportBean.getColumn("constNo", String.class, "BORROWER'S LEGAL CONSTITUTION", 100);
        AbstractColumn businessCat = ReportBean.getColumn("businessCat", String.class, "BUSINESS CATEGORY", 100);
        AbstractColumn businessType = ReportBean.getColumn("businessType", String.class, "BUSINESS/INDUSTRY TYPE", 100);
        AbstractColumn classOfAct1 = ReportBean.getColumn("classOfAct1", String.class, "CLASS OF ACTIVITY 1", 100);
        AbstractColumn classOfAct2 = ReportBean.getColumn("classOfAct2", String.class, "CLASS OF ACTIVITY 2", 100);
        AbstractColumn classOfAct3 = ReportBean.getColumn("classOfAct3", String.class, "CLASS OF ACTIVITY 3", 100);
        fastReport.addColumn(memberCode);
        fastReport.addColumn(reportDate);
        fastReport.addColumn(memberName);
        fastReport.addColumn(shortName);
        fastReport.addColumn(regNo);
        fastReport.addColumn(dateOfIncorp);
        fastReport.addColumn(pan);
        fastReport.addColumn(cin);
        fastReport.addColumn(tin);
        fastReport.addColumn(sTax);
        fastReport.addColumn(otherId);
        fastReport.addColumn(constNo);
        fastReport.addColumn(businessCat);
        fastReport.addColumn(businessType);
        fastReport.addColumn(classOfAct1);
        fastReport.addColumn(classOfAct2);
        fastReport.addColumn(classOfAct3);
        /*17*/
        /*
         * * SIC CODE	SALES FIGURE	FINANCIAL YEAR	NUMBER OF EMPLOYEES	CREDIT RATING	ASSESSMENT AUTHORITY/ AGENCY	CREDIT RATING AS ON	
         * CREDIT RATING EXPIRY DATE	BORROWER’S OFFICE/ LOCATION TYPE	BORROWER’S OFFICE DUNS NUMBER	ADDRESS LINE 1	ADDRESS LINE 2	ADDRESS LINE 3	
         * CITY/ TOWN	DISTRICT	STATE/ UNION TERRITORY	PIN CODE	COUNTRY	MOBILE NUMBER(s)	TELEPHONE AREA CODE	
         * TELEPHONE NUMBER(s)	FAX AREA CODE	FAX NUMBER(s)	RELATIONSHIP DUNS NUMBER	RELATED TYPE	RELATIONSHIP	
         */
        AbstractColumn sicCode = ReportBean.getColumn("sicCode", String.class, "SIC CODE", 100);
        AbstractColumn salesFigure = ReportBean.getColumn("salesFigure", String.class, "SALES FIGURE", 100);
        AbstractColumn finYear = ReportBean.getColumn("finYear", String.class, "FINANCIAL YEAR", 100);
        AbstractColumn noOfEmp = ReportBean.getColumn("noOfEmp", String.class, "NUMBER OF EMPLOYEES", 100);
        AbstractColumn creditRating = ReportBean.getColumn("creditRating", String.class, "CREDIT RATING", 100);
        AbstractColumn authority = ReportBean.getColumn("authority", String.class, "ASSESMENT AUTHORITY/AGENCY", 100);
        AbstractColumn creditRatingDt = ReportBean.getColumn("creditRatingDt", String.class, "CREDIT RATING AS ON", 100);
        AbstractColumn creditRatingExpDt = ReportBean.getColumn("creditRatingExpDt", String.class, "CREDIT RATING EXPIRY DATE", 100);
        AbstractColumn locationType = ReportBean.getColumn("locationType", String.class, "BORROWER’S OFFICE/ LOCATION TYPE", 100);
        AbstractColumn dunsNo = ReportBean.getColumn("dunsNo", String.class, "BORROWER’S OFFICE DUNS NUMBER", 100);
        AbstractColumn addressLine1 = ReportBean.getColumn("addressLine1", String.class, "ADDRESS LINE 1", 220);
        AbstractColumn addressLine2 = ReportBean.getColumn("addressLine2", String.class, "ADDRESS LINE 2", 220);
        AbstractColumn addressLine3 = ReportBean.getColumn("addressLine3", String.class, "ADDRESS LINE 3", 220);
        AbstractColumn city = ReportBean.getColumn("city", String.class, "CITY/ TOWN", 100);
        AbstractColumn district = ReportBean.getColumn("district", String.class, "DISTRICT", 100);
        AbstractColumn state = ReportBean.getColumn("state", String.class, "STATE/ UNION TERRITORY", 100);
        AbstractColumn pin = ReportBean.getColumn("pin", String.class, "PIN CODE", 100);
        AbstractColumn country = ReportBean.getColumn("country", String.class, "COUNTRY", 100);
        AbstractColumn mobile = ReportBean.getColumn("mobile", String.class, "MOBILE NUMBER(s)", 100);
        AbstractColumn telAreaCode = ReportBean.getColumn("telAreaCode", String.class, "TELEPHONE AREA CODE", 100);
        AbstractColumn telNo = ReportBean.getColumn("telNo", String.class, "TELEPHONE NUMBER(s)", 150);
        AbstractColumn faxArea = ReportBean.getColumn("faxArea", String.class, "FAX AREA CODE", 150);
        AbstractColumn faxNo = ReportBean.getColumn("faxNo", String.class, "FAX NUMBER(s)", 150);
        AbstractColumn relDunsNo = ReportBean.getColumn("relDunsNo", String.class, "RELATIONSHIP DUNS NUMBER", 150);
        AbstractColumn relatedType = ReportBean.getColumn("relatedType", String.class, "RELATED TYPE", 150);
        AbstractColumn relationShip = ReportBean.getColumn("relationShip", String.class, "RELATIONSHIP", 80);
        fastReport.addColumn(sicCode);
        fastReport.addColumn(salesFigure);
        fastReport.addColumn(finYear);
        fastReport.addColumn(noOfEmp);
        fastReport.addColumn(creditRating);
        fastReport.addColumn(authority);
        fastReport.addColumn(creditRatingDt);
        fastReport.addColumn(creditRatingExpDt);
        fastReport.addColumn(locationType);
        fastReport.addColumn(dunsNo);
        fastReport.addColumn(addressLine1);
        fastReport.addColumn(addressLine2);
        fastReport.addColumn(addressLine3);
        fastReport.addColumn(city);
        fastReport.addColumn(district);
        fastReport.addColumn(state);
        fastReport.addColumn(pin);
        fastReport.addColumn(country);
        fastReport.addColumn(mobile);
        fastReport.addColumn(telAreaCode);
        fastReport.addColumn(telNo);
        fastReport.addColumn(faxArea);
        fastReport.addColumn(faxNo);
        fastReport.addColumn(relDunsNo);
        fastReport.addColumn(relatedType);
        fastReport.addColumn(relationShip);
        /*26*/
        /*
         * BUSINESS ENTITY NAME	BUSINESS CATEGORY	BUSINESS/INDUSTRY TYPE	INDIVIDUAL NAME (PREFIX)	FULL NAME	GENDER	
         * COMPANY REGISTRATION NUMBER	DATE OF INCORPORATION	DATE OF BIRTH	PAN	VOTER ID	PASSPORT NUMBER	DRIVING LICENSE ID	
         * UID	RATION CARD NUMBER	CIN	DIN	TIN	SERVICE TAX	OTHER ID	PERCENTAGE OF CONTROL	
         */
        AbstractColumn businessEntityName = ReportBean.getColumn("businessEntityName", String.class, "BUSINESS ENTITY NAME", 80);
        AbstractColumn businessCat1 = ReportBean.getColumn("businessCat1", String.class, "BUSINESS CATEGORY", 150);
        AbstractColumn typeOfBusiness = ReportBean.getColumn("typeOfBusiness", String.class, "BUSINESS/INDUSTRY TYPE", 80);
        AbstractColumn indPreFix = ReportBean.getColumn("indPreFix", String.class, "INDIVIDUAL NAME (PREFIX)", 100);
        AbstractColumn fullName = ReportBean.getColumn("fullName", String.class, "FULL NAME", 100);
        AbstractColumn gender = ReportBean.getColumn("gender", String.class, "Gender", 100);
        AbstractColumn regNoCom = ReportBean.getColumn("regNoCom", String.class, "COMPANY REGISTRATION NUMBER", 100);
        AbstractColumn doi = ReportBean.getColumn("doi", String.class, "DATE OF INCORPORATION", 100);
        AbstractColumn dob = ReportBean.getColumn("dob", String.class, "DATE OF BIRTH", 150);
        AbstractColumn panNo = ReportBean.getColumn("panNo", String.class, "PAN", 150);
        AbstractColumn voterID = ReportBean.getColumn("voterID", String.class, "VOTER ID", 150);
        AbstractColumn passportNo = ReportBean.getColumn("passportNo", String.class, "PASSPORT NUMBER", 150);
        AbstractColumn dlID = ReportBean.getColumn("dlID", String.class, "DRIVING LICENSE ID", 100);
        AbstractColumn uid = ReportBean.getColumn("uid", String.class, "UID", 100);
        AbstractColumn rationCardNo = ReportBean.getColumn("rationCardNo", String.class, "RATION CARD NUMBER", 100);
        AbstractColumn cinNo = ReportBean.getColumn("cinNo", String.class, "CIN", 100);
        AbstractColumn dinNo = ReportBean.getColumn("dinNo", String.class, "DIN", 100);
        AbstractColumn tinNo = ReportBean.getColumn("tinNo", String.class, "TIN", 100);
        AbstractColumn sTaxNo = ReportBean.getColumn("sTaxNo", String.class, "SERVICE TAX", 100);
        AbstractColumn otherIdNo = ReportBean.getColumn("otherIdNo", String.class, "OTHER ID", 100);
        AbstractColumn percControl = ReportBean.getColumn("percControl", String.class, "PERCENTAGE OF CONTROL", 100);
        fastReport.addColumn(businessEntityName);
        fastReport.addColumn(businessCat1);
        fastReport.addColumn(typeOfBusiness);
        fastReport.addColumn(indPreFix);
        fastReport.addColumn(fullName);
        fastReport.addColumn(gender);
        fastReport.addColumn(regNoCom);
        fastReport.addColumn(doi);
        fastReport.addColumn(dob);
        fastReport.addColumn(panNo);
        fastReport.addColumn(voterID);
        fastReport.addColumn(passportNo);
        fastReport.addColumn(dlID);
        fastReport.addColumn(uid);
        fastReport.addColumn(rationCardNo);
        fastReport.addColumn(cinNo);
        fastReport.addColumn(dinNo);
        fastReport.addColumn(tinNo);
        fastReport.addColumn(sTaxNo);
        fastReport.addColumn(otherIdNo);
        fastReport.addColumn(percControl);
        /*21*/
        /*
         * ADDRESS LINE 1	ADDRESS LINE 2	ADDRESS LINE 3	CITY/TOWN	DISTRICT	STATE/ UNION TERRITORY	PIN CODE	
         * COUNTRY	MOBILE NUMBER(s)	TELEPHONE AREA CODE	TELEPHONE NUMBER(s)	FAX AREA CODE	FAX NUMBER(s)	
         * ACCOUNT NUMBER	PREVIOUS ACCOUNT NUMBER	FACILITY/LOAN/ACTIVATION/SANCTION DATE	SANCTIONED AMOUNT/ NOTIONAL AMOUNT OF CONTRACT	
         * CURENCY CODE	CREDIT TYPE	TENURE/ WEIGHTED AVERAGE MATURITY PERIOD OF CONTRACTS	REPAYMENT FREQUENCY	
         * DRAWNG POWER	CURRENT BALANCE/ LIMIT UTILIZED/ MARK TO MARKET	 NOTIONAL AMOUNT OF OUTSTANDING RESTRUCTURED CONTRACTS
         * 
         */
        AbstractColumn add1 = ReportBean.getColumn("add1", String.class, "ADDRESS LINE 1", 100);
        AbstractColumn add2 = ReportBean.getColumn("add2", String.class, "ADDRESS LINE 21", 100);
        AbstractColumn add3 = ReportBean.getColumn("add3", String.class, "ADDRESS LINE 3", 100);
        AbstractColumn cityTown = ReportBean.getColumn("cityTown", String.class, "CITY/TOWN", 100);
        AbstractColumn district1 = ReportBean.getColumn("district1", String.class, "DISTRICT", 100);
        AbstractColumn state1 = ReportBean.getColumn("state1", String.class, "STATE/ UNION TERRITORY", 100);
        AbstractColumn pin1 = ReportBean.getColumn("pin1", String.class, "PIN CODE", 100);
        AbstractColumn country1 = ReportBean.getColumn("country1", String.class, "COUNTRY", 100);
        AbstractColumn mobile1 = ReportBean.getColumn("mobile1", String.class, "MOBILE NUMBER(s)", 100);
        AbstractColumn telAreaCode1 = ReportBean.getColumn("telAreaCode1", String.class, "TELEPHONE AREA CODE", 100);
        AbstractColumn telNo1 = ReportBean.getColumn("telNo1", String.class, "TELEPHONE NUMBER(s)", 150);
        AbstractColumn faxArea1 = ReportBean.getColumn("faxArea1", String.class, "FAX AREA CODE", 150);
        AbstractColumn faxNo1 = ReportBean.getColumn("faxNo1", String.class, "FAX NUMBER(s)", 150);
        AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "ACCOUNT NUMBER", 150);
        AbstractColumn preAcno = ReportBean.getColumn("preAcno", String.class, "PREVIOUS ACCOUNT NUMBER", 150);
        AbstractColumn sanctDt = ReportBean.getColumn("sanctDt", String.class, "FACILITY/LOAN/ACTIVATION/SANCTION DATE", 80);
        AbstractColumn sancAmt = ReportBean.getColumn("sancAmt", String.class, "SANCTIONED AMOUNT/ NOTIONAL AMOUNT OF CONTRACT", 100);
        AbstractColumn currencyCode = ReportBean.getColumn("currencyCode", String.class, "CURENCY CODE", 100);
        AbstractColumn creditType = ReportBean.getColumn("creditType", String.class, "CREDIT TYPE", 100);
        AbstractColumn period = ReportBean.getColumn("period", String.class, "TENURE/ WEIGHTED AVERAGE MATURITY PERIOD OF CONTRACTS", 100);
        AbstractColumn repaymentFreq = ReportBean.getColumn("repaymentFreq", String.class, "REPAYMENT FREQUENCY", 100);
        AbstractColumn drawingPower = ReportBean.getColumn("drawingPower", String.class, "DRAWNG POWER", 100);
        AbstractColumn outstanding = ReportBean.getColumn("outstanding", String.class, "CURRENT BALANCE/ LIMIT UTILIZED/ MARK TO MARKET", 100);
        AbstractColumn amtRestructured = ReportBean.getColumn("amtRestructured", String.class, "NOTIONAL AMOUNT OF OUTSTANDING RESTRUCTURED CONTRACTS", 100);
        fastReport.addColumn(add1);
        fastReport.addColumn(add2);
        fastReport.addColumn(add3);
        fastReport.addColumn(cityTown);
        fastReport.addColumn(district1);
        fastReport.addColumn(state1);
        fastReport.addColumn(pin1);
        fastReport.addColumn(country1);
        fastReport.addColumn(mobile1);
        fastReport.addColumn(telAreaCode1);
        fastReport.addColumn(telNo1);
        fastReport.addColumn(faxArea1);
        fastReport.addColumn(faxNo1);
        fastReport.addColumn(acNo);
        fastReport.addColumn(preAcno);
        fastReport.addColumn(sanctDt);
        fastReport.addColumn(sancAmt);
        fastReport.addColumn(currencyCode);
        fastReport.addColumn(creditType);
        fastReport.addColumn(period);
        fastReport.addColumn(repaymentFreq);
        fastReport.addColumn(drawingPower);
        fastReport.addColumn(outstanding);
        fastReport.addColumn(amtRestructured);
        /*24*
         width = ;
         /*
         * LOAN EXPIRY/ MATURITY DATE	LOAN RENEWAL DATE	ASSET CLASSIFICATON	ASSET CLASSIFICATION DATE	
         * AMOUNT OVERDUE/ LIMIT OVERDUE	OVERDUE BUCKET 01 (1-30 DAYS)	OVERDUE BUCKET 02 (31-60 DAYS)	OVERDUE BUCKET 03 (61-90 DAYS)	
         * OVERDUE BUCKET 04 (91-180 DAYS)	OVERDUE BUCKET 05 (ABOVE 180 DAYS)	HIGH CREDIT	INSTALLMENT AMOUNT	
         * LAST REPAID AMOUNT	ACCOUNT STATUS	ACCOUNT STATUS DATE	WRITTEN OFF AMOUNT	SETTLED AMOUNT	MAJOR REASONS OF RESTRUCTURING	
         * AMOUNT OF CONTRACTS CLASSIFIED AS NPA	ASSET BASED SECURITY COVERAGE	GUARANTEE COVERAGE	BANK REMARK CODE	
         * WILFUL DEFAULT STATUS	DATE CLASSIFIED AS WILFUL DEFAULT	SUIT FILED STATUS	SUIT REFERENCE NUMBER	SUIT AMOUNT (INR)
         * DATE OF SUIT	DISPUTE ID NO.	TRANSACTION TYPE CODE
         */
        AbstractColumn closeDt = ReportBean.getColumn("closeDt", String.class, "LOAN EXPIRY/ MATURITY DATE", 100);
        AbstractColumn renewalDt = ReportBean.getColumn("renewalDt", String.class, "LOAN RENEWAL DATE", 100);
        AbstractColumn assetClassif = ReportBean.getColumn("assetClassif", String.class, "ASSET CLASSIFICATON", 150);
        AbstractColumn assetClassDt = ReportBean.getColumn("assetClassDt", String.class, "ASSET CLASSIFICATON DATE", 150);
        AbstractColumn amtOverDue = ReportBean.getColumn("amtOverDue", String.class, "AMOUNT OVERDUE/ LIMIT OVERDUE", 150);
        AbstractColumn odBuct1 = ReportBean.getColumn("odBuct1", String.class, "OVERDUE BUCKET 01 (1-30 DAYS)", 150);
        AbstractColumn odBuct2 = ReportBean.getColumn("odBuct2", String.class, "OVERDUE BUCKET 02 (31-60 DAYS)", 150);
        AbstractColumn odBuct3 = ReportBean.getColumn("odBuct3", String.class, "OVERDUE BUCKET 03 (61-90 DAYS)", 150);
        AbstractColumn odBuct4 = ReportBean.getColumn("odBuct4", String.class, "OVERDUE BUCKET 04 (91-180 DAYS)", 150);
        AbstractColumn odBuct5 = ReportBean.getColumn("odBuct5", String.class, "OVERDUE BUCKET 05 (ABOVE 180 DAYS)", 150);
        AbstractColumn highCredit = ReportBean.getColumn("highCredit", String.class, "HIGH CREDIT", 150);
        AbstractColumn emiAmt = ReportBean.getColumn("emiAmt", String.class, "INSTALLMENT AMOUNT", 150);
        AbstractColumn lastCrAmt = ReportBean.getColumn("lastCrAmt", String.class, "LAST REPAID AMOUNT", 150);
        AbstractColumn acStatus = ReportBean.getColumn("acStatus", String.class, "ACCOUNT STATUS", 150);
        AbstractColumn acStatusDt = ReportBean.getColumn("acStatusDt", String.class, "ACCOUNT STATUS DATE", 150);
        AbstractColumn writeOffAmt = ReportBean.getColumn("writeOffAmt", String.class, "WRITTEN OFF AMOUNT", 150);
        AbstractColumn settledAmt = ReportBean.getColumn("settledAmt", String.class, "SETTLED AMOUNT", 150);
        AbstractColumn restureReason = ReportBean.getColumn("restureReason", String.class, "MAJOR REASONS OF RESTRUCTURING", 150);
        AbstractColumn npaAmt = ReportBean.getColumn("npaAmt", String.class, "AMOUNT OF CONTRACTS CLASSIFIED AS NPA", 150);
        AbstractColumn asstSecCover = ReportBean.getColumn("asstSecCover", String.class, "ASSET BASED SECURITY COVERAGE", 150);
        AbstractColumn guaranteeCover = ReportBean.getColumn("guaranteeCover", String.class, "GUARANTEE COVERAGE", 150);
        AbstractColumn bnkRemarkCode = ReportBean.getColumn("bnkRemarkCode", String.class, "BANK REMARK CODE", 150);
        AbstractColumn defaultStatus = ReportBean.getColumn("defaultStatus", String.class, "WILFUL DEFAULT STATUS", 150);
        AbstractColumn defaultStatusDt = ReportBean.getColumn("defaultStatusDt", String.class, "DATE CLASSIFIED AS WILFUL DEFAULT", 150);
        AbstractColumn suitStatus = ReportBean.getColumn("suitStatus", String.class, "SUIT FILED STATUS", 150);
        AbstractColumn suitRefNo = ReportBean.getColumn("suitRefNo", String.class, "SUIT REFERENCE NUMBER", 150);
        AbstractColumn suitAmt = ReportBean.getColumn("suitAmt", String.class, "SUIT AMOUNT (INR)", 150);
        AbstractColumn suitDt = ReportBean.getColumn("suitDt", String.class, "DATE OF SUIT", 150);
        AbstractColumn disputeIdNo = ReportBean.getColumn("disputeIdNo", String.class, "DISPUTE ID NO.", 150);
        AbstractColumn txnTypeCode = ReportBean.getColumn("txnTypeCode", String.class, "TRANSACTION TYPE CODE", 150);
        fastReport.addColumn(closeDt);
        fastReport.addColumn(renewalDt);
        fastReport.addColumn(assetClassif);
        fastReport.addColumn(assetClassDt);
        fastReport.addColumn(amtOverDue);
        fastReport.addColumn(odBuct1);
        fastReport.addColumn(odBuct2);
        fastReport.addColumn(odBuct3);
        fastReport.addColumn(odBuct4);
        fastReport.addColumn(odBuct5);
        fastReport.addColumn(highCredit);
        fastReport.addColumn(emiAmt);
        fastReport.addColumn(lastCrAmt);
        fastReport.addColumn(acStatus);
        fastReport.addColumn(acStatusDt);
        fastReport.addColumn(writeOffAmt);
        fastReport.addColumn(settledAmt);
        fastReport.addColumn(restureReason);
        fastReport.addColumn(npaAmt);
        fastReport.addColumn(asstSecCover);
        fastReport.addColumn(guaranteeCover);
        fastReport.addColumn(bnkRemarkCode);
        fastReport.addColumn(defaultStatus);
        fastReport.addColumn(defaultStatusDt);
        fastReport.addColumn(suitStatus);
        fastReport.addColumn(suitRefNo);
        fastReport.addColumn(suitAmt);
        fastReport.addColumn(suitDt);
        fastReport.addColumn(disputeIdNo);
        fastReport.addColumn(txnTypeCode);
        /*30*/
        /*
         * GUARANTOR DUNS	GUARANTOR TYPE	BUSINESS CATEGORY	
         * BUSINESS/ INDUSTRY TYPE	GUARANTOR ENTITY NAME	INDIVIDUAL NAME (PREFIX)	FULL NAME	GENDER	COMPANY REGISTRATION NUMBER	
         * DATE OF INCORPORATION	DATE OF BIRTH	PAN	VOTER ID	PASSPORT NUMBER	DRIVING LICESE ID	UID	RATION CARD NUMBER	
         * CIN	DIN	TIN	SERVICE TAX NUMBER	OTHER ID	ADDRESS LINE 1	ADDRESS LINE 2	ADDRESS LINE 3	CITY/TOWN	DISTRICT	
         */
        AbstractColumn guarantorDUNs = ReportBean.getColumn("guarantorDUNs", String.class, "GUARANTOR DUNS", 150);
        AbstractColumn guarantorType = ReportBean.getColumn("guarantorType", String.class, "GUARANTOR TYPE", 150);
        AbstractColumn businessCatGuarantor = ReportBean.getColumn("businessCatGuarantor", String.class, "BUSINESS CATEGORY", 150);
        AbstractColumn businessTypeGuarantor = ReportBean.getColumn("businessTypeGuarantor", String.class, "BUSINESS/ INDUSTRY TYPE", 150);
        AbstractColumn guarantorEntityName = ReportBean.getColumn("guarantorEntityName", String.class, "GUARANTOR ENTITY NAME", 150);
        AbstractColumn guarantorPrefix = ReportBean.getColumn("guarantorPrefix", String.class, "INDIVIDUAL NAME (PREFIX)", 150);
        AbstractColumn guarantorFullName = ReportBean.getColumn("guarantorFullName", String.class, "FULL NAME", 150);
        AbstractColumn guarantorGender = ReportBean.getColumn("guarantorGender", String.class, "GENDER", 150);
        AbstractColumn guarantorComRegNo = ReportBean.getColumn("guarantorComRegNo", String.class, "COMPANY REGISTRATION NUMBER", 150);
        AbstractColumn guarantorDOI = ReportBean.getColumn("guarantorDOI", String.class, "DATE OF INCORPORATION", 150);
        AbstractColumn guarantorDob = ReportBean.getColumn("guarantorDob", String.class, "DATE OF BIRTH", 150);
        AbstractColumn guarantorPan = ReportBean.getColumn("guarantorPan", String.class, "PAN", 150);
        AbstractColumn guarantorVotedID = ReportBean.getColumn("guarantorVotedID", String.class, "VOTER ID", 150);
        AbstractColumn guarantorPassport = ReportBean.getColumn("guarantorPassport", String.class, "PASSPORT NUMBER", 150);
        AbstractColumn guarantorDLId = ReportBean.getColumn("guarantorDLId", String.class, "DRIVING LICESE ID", 150);
        AbstractColumn guarantorUID = ReportBean.getColumn("guarantorUID", String.class, "UID", 150);
        AbstractColumn guarantorRationCard = ReportBean.getColumn("guarantorRationCard", String.class, "RATION CARD NUMBER", 150);
        AbstractColumn guarantorCIN = ReportBean.getColumn("guarantorCIN", String.class, "CIN", 150);
        AbstractColumn guarantorDIN = ReportBean.getColumn("guarantorDIN", String.class, "DIN", 150);
        AbstractColumn guarantorTIN = ReportBean.getColumn("guarantorTIN", String.class, "TIN", 150);
        AbstractColumn guarantorSTax = ReportBean.getColumn("guarantorSTax", String.class, "SERVICE TAX NUMBER", 150);
        AbstractColumn guarantorOthId = ReportBean.getColumn("guarantorOthId", String.class, "OTHER ID", 150);
        AbstractColumn guarantorAdd1 = ReportBean.getColumn("guarantorAdd1", String.class, "ADDRESS LINE 1", 150);
        AbstractColumn guarantorAdd2 = ReportBean.getColumn("guarantorAdd1", String.class, "ADDRESS LINE 2", 150);
        AbstractColumn guarantorAdd3 = ReportBean.getColumn("guarantorAdd3", String.class, "ADDRESS LINE 3", 150);
        AbstractColumn guarantorCity = ReportBean.getColumn("guarantorCity", String.class, "CITY/TOWN", 150);
        AbstractColumn guarantorDistrict = ReportBean.getColumn("guarantorDistrict", String.class, "DISTRICT", 150);
        fastReport.addColumn(guarantorDUNs);
        fastReport.addColumn(guarantorType);
        fastReport.addColumn(businessCatGuarantor);
        fastReport.addColumn(businessTypeGuarantor);
        fastReport.addColumn(guarantorEntityName);
        fastReport.addColumn(guarantorPrefix);
        fastReport.addColumn(guarantorFullName);
        fastReport.addColumn(guarantorGender);
        fastReport.addColumn(guarantorComRegNo);
        fastReport.addColumn(guarantorDOI);
        fastReport.addColumn(guarantorDob);
        fastReport.addColumn(guarantorPan);
        fastReport.addColumn(guarantorVotedID);
        fastReport.addColumn(guarantorPassport);
        fastReport.addColumn(guarantorDLId);
        fastReport.addColumn(guarantorUID);
        fastReport.addColumn(guarantorRationCard);
        fastReport.addColumn(guarantorCIN);
        fastReport.addColumn(guarantorDIN);
        fastReport.addColumn(guarantorTIN);
        fastReport.addColumn(guarantorSTax);
        fastReport.addColumn(guarantorOthId);
        fastReport.addColumn(guarantorAdd1);
        fastReport.addColumn(guarantorAdd2);
        fastReport.addColumn(guarantorAdd3);
        fastReport.addColumn(guarantorCity);
        fastReport.addColumn(guarantorDistrict);
        /*27*/
        /*
         * STATE/ UNION TERRITORY	PIN CODE	COUNTRY	MOBILE NUMBER(s)	TELEPHONE AREA CODE	TELEPHONE NUMBER(s)	
         * FAX AREA CODE	FAX NUMBER(s)	VALUE OF SECURITY	CURRENCY TYPE	TYPE OF SECURITY	
         * SECURITY CLASSIFICATION	DATE OF VALUATION	SEGMENT IDENTIFIER	DATE OF DISHONOUR	AMOUNT	INSTRUMENT/ CHEQUE NUMBER	
         * NUMBER OF TIMES DISHONOURED	CHEQUE ISSUE DATE	REASON FOR DISHONOUR */
        AbstractColumn guarantorState = ReportBean.getColumn("guarantorState", String.class, "STATE/ UNION TERRITORY", 150);
        AbstractColumn guarantorPin = ReportBean.getColumn("guarantorPin", String.class, "PIN CODE", 150);
        AbstractColumn guarantorCountry = ReportBean.getColumn("guarantorCountry", String.class, "COUNTRY", 150);
        AbstractColumn guarantorMobile = ReportBean.getColumn("guarantorMobile", String.class, "MOBILE NUMBER(s)", 150);
        AbstractColumn guarantorTelAreaCode = ReportBean.getColumn("guarantorTelAreaCode", String.class, "TELEPHONE AREA CODE", 150);
        AbstractColumn guarantorTelNo = ReportBean.getColumn("guarantorTelNo", String.class, "TELEPHONE NUMBER(s)", 150);
        AbstractColumn guarantorFaxAreaCode = ReportBean.getColumn("guarantorFaxAreaCode", String.class, "FAX AREA CODE", 150);
        AbstractColumn guarantorFaxNo = ReportBean.getColumn("guarantorFaxNo", String.class, "FAX NUMBER(s)", 150);
        AbstractColumn guarantorValueOfSec = ReportBean.getColumn("guarantorValueOfSec", String.class, "VALUE OF SECURITY", 150);
        AbstractColumn guarantorCurrencyType = ReportBean.getColumn("guarantorCurrencyType", String.class, "CURRENCY TYPE", 150);
        AbstractColumn guarantorTypeOfSec = ReportBean.getColumn("guarantorTypeOfSec", String.class, "TYPE OF SECURITY", 150);
        AbstractColumn guarantorSecurityClass = ReportBean.getColumn("guarantorSecurityClass", String.class, "SECURITY CLASSIFICATION", 150);
        AbstractColumn guarantorDateOfValuation = ReportBean.getColumn("guarantorDateOfValuation", String.class, "DATE OF VALUATION", 150);
        AbstractColumn guarantorSegmentIdentifier = ReportBean.getColumn("guarantorSegmentIdentifier", String.class, "SEGMENT IDENTIFIER", 150);
        AbstractColumn guarantorDateOfDishonour = ReportBean.getColumn("guarantorDateOfDishonour", String.class, "DATE OF DISHONOUR", 150);
        AbstractColumn guarantorAmt = ReportBean.getColumn("guarantorAmt", String.class, "AMOUNT", 150);
        AbstractColumn guarantorChqNo = ReportBean.getColumn("guarantorChqNo", String.class, "INSTRUMENT/ CHEQUE NUMBER", 150);
        AbstractColumn guarantorNoOfDisHonour = ReportBean.getColumn("guarantorNoOfDisHonour", String.class, "NUMBER OF TIMES DISHONOURED", 150);
        AbstractColumn guarantorChqIssueDt = ReportBean.getColumn("guarantorChqIssueDt", String.class, "CHEQUE ISSUE DATE", 150);
        AbstractColumn guarantorReasonForDisHonour = ReportBean.getColumn("guarantorReasonForDisHonour", String.class, "REASON FOR DISHONOUR", 150);
        fastReport.addColumn(guarantorState);
        fastReport.addColumn(guarantorPin);
        fastReport.addColumn(guarantorCountry);
        fastReport.addColumn(guarantorMobile);
        fastReport.addColumn(guarantorTelAreaCode);
        fastReport.addColumn(guarantorTelNo);
        fastReport.addColumn(guarantorFaxAreaCode);
        fastReport.addColumn(guarantorFaxNo);
        fastReport.addColumn(guarantorValueOfSec);
        fastReport.addColumn(guarantorCurrencyType);
        fastReport.addColumn(guarantorTypeOfSec);
        fastReport.addColumn(guarantorSecurityClass);
        fastReport.addColumn(guarantorDateOfValuation);
        fastReport.addColumn(guarantorSegmentIdentifier);
        fastReport.addColumn(guarantorDateOfDishonour);
        fastReport.addColumn(guarantorAmt);
        fastReport.addColumn(guarantorChqNo);
        fastReport.addColumn(guarantorNoOfDisHonour);
        fastReport.addColumn(guarantorChqIssueDt);
        fastReport.addColumn(guarantorReasonForDisHonour);
        /*20*/
        width = memberCode.getWidth() + reportDate.getWidth() + memberName.getWidth() + shortName.getWidth() + regNo.getWidth() + dateOfIncorp.getWidth()
                + pan.getWidth() + cin.getWidth() + tin.getWidth() + sTax.getWidth() + otherId.getWidth() + constNo.getWidth() + businessCat.getWidth()
                + businessType.getWidth() + classOfAct1.getWidth() + classOfAct2.getWidth() + classOfAct3.getWidth()
                + sicCode.getWidth() + salesFigure.getWidth() + finYear.getWidth() + noOfEmp.getWidth() + creditRating.getWidth() + authority.getWidth()
                + creditRatingDt.getWidth() + creditRatingExpDt.getWidth() + locationType.getWidth() + dunsNo.getWidth() + addressLine1.getWidth()
                + addressLine2.getWidth() + addressLine3.getWidth() + city.getWidth() + district.getWidth() + state.getWidth() + pin.getWidth() + country.getWidth()
                + mobile.getWidth() + telAreaCode.getWidth() + telNo.getWidth() + faxArea.getWidth() + faxNo.getWidth() + relDunsNo.getWidth()
                + relatedType.getWidth() + relationShip.getWidth()
                + businessEntityName.getWidth() + businessCat1.getWidth() + typeOfBusiness.getWidth() + indPreFix.getWidth() + fullName.getWidth()
                + gender.getWidth() + regNoCom.getWidth() + doi.getWidth() + dob.getWidth() + panNo.getWidth() + voterID.getWidth() + passportNo.getWidth()
                + dlID.getWidth() + uid.getWidth() + rationCardNo.getWidth() + cinNo.getWidth() + dinNo.getWidth() + tinNo.getWidth()
                + sTaxNo.getWidth() + otherIdNo.getWidth() + percControl.getWidth()
                + add1.getWidth() + add2.getWidth() + add3.getWidth() + cityTown.getWidth() + district1.getWidth() + state1.getWidth() + pin1.getWidth()
                + country1.getWidth() + mobile1.getWidth() + telAreaCode1.getWidth() + telNo1.getWidth() + faxArea1.getWidth() + faxNo1.getWidth()
                + acNo.getWidth() + preAcno.getWidth() + sanctDt.getWidth() + sancAmt.getWidth() + currencyCode.getWidth() + creditType.getWidth()
                + period.getWidth() + repaymentFreq.getWidth() + drawingPower.getWidth() + outstanding.getWidth() + amtRestructured.getWidth()
                + closeDt.getWidth() + renewalDt.getWidth() + assetClassif.getWidth() + assetClassDt.getWidth() + amtOverDue.getWidth() + odBuct1.getWidth()
                + odBuct2.getWidth() + odBuct3.getWidth() + odBuct4.getWidth() + odBuct5.getWidth() + highCredit.getWidth() + emiAmt.getWidth() + lastCrAmt.getWidth()
                + acStatus.getWidth() + acStatusDt.getWidth() + writeOffAmt.getWidth() + settledAmt.getWidth() + restureReason.getWidth() + npaAmt.getWidth()
                + asstSecCover.getWidth() + guaranteeCover.getWidth() + bnkRemarkCode.getWidth() + defaultStatus.getWidth() + defaultStatusDt.getWidth()
                + suitStatus.getWidth() + suitRefNo.getWidth() + suitAmt.getWidth() + suitDt.getWidth() + disputeIdNo.getWidth() + txnTypeCode.getWidth()
                + guarantorDUNs.getWidth() + guarantorType.getWidth() + businessCatGuarantor.getWidth() + businessTypeGuarantor.getWidth()
                + guarantorEntityName.getWidth() + guarantorPrefix.getWidth() + guarantorFullName.getWidth() + guarantorGender.getWidth()
                + guarantorComRegNo.getWidth() + guarantorDOI.getWidth() + guarantorDob.getWidth() + guarantorPan.getWidth() + guarantorVotedID.getWidth()
                + guarantorPassport.getWidth() + guarantorDLId.getWidth() + guarantorUID.getWidth() + guarantorRationCard.getWidth() + guarantorCIN.getWidth()
                + guarantorDIN.getWidth() + guarantorTIN.getWidth() + guarantorSTax.getWidth() + guarantorOthId.getWidth() + guarantorAdd1.getWidth()
                + guarantorAdd2.getWidth() + guarantorAdd3.getWidth() + guarantorCity.getWidth() + guarantorDistrict.getWidth()
                + guarantorState.getWidth() + guarantorPin.getWidth() + guarantorCountry.getWidth() + guarantorMobile.getWidth() + guarantorTelAreaCode.getWidth()
                + guarantorTelNo.getWidth() + guarantorFaxAreaCode.getWidth() + guarantorFaxNo.getWidth() + guarantorValueOfSec.getWidth()
                + guarantorCurrencyType.getWidth() + guarantorTypeOfSec.getWidth() + guarantorSecurityClass.getWidth() + guarantorDateOfValuation.getWidth()
                + guarantorSegmentIdentifier.getWidth() + guarantorDateOfDishonour.getWidth() + guarantorAmt.getWidth() + guarantorChqNo.getWidth()
                + guarantorNoOfDisHonour.getWidth() + guarantorChqIssueDt.getWidth() + guarantorReasonForDisHonour.getWidth();
        System.out.println("Width" + width);
        Page page = new Page(18000, width, false);
        fastReport.setMargins(0, 0, 0, 0);
        fastReport.setPageSizeAndOrientation(page);
        fastReport.setTitle("EQUIFAX COMERCIAL");
        return fastReport;
    }
}
