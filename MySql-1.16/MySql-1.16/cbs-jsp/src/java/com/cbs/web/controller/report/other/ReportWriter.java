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
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.io.Serializable;
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
 * @author admin
 */
public class ReportWriter extends BaseBean implements Serializable {

    private String errMsg;
    private Date frmDate;
    private Date toDate;
    private String reportName;
    private String orderBy;
    private String selectAcType;
    private String custStatus;
    private String status;
    private String branch;
    private String annualIncomeFrom;
    private String annualIncomeTo;
    private String areaSector;
    private String averageBalance;
    private String occupation;
    private String riskCategorization;
    private String fromAge;
    private String toAge;
    private String txtFromAcno;
    private String txtToAcno;
    private boolean customerIdBoolean;
    private boolean nameBoolean;
    private boolean fatherNameBoolean;
    private boolean acNoBoolean;
    private boolean custAddBoolean;
    private boolean occupationBoolean;
    private boolean jtNameBoolean;
    private boolean phoneNoBoolean;
    private boolean operatingBoolean;
    private boolean accOpenDtBoolean;
    private boolean perAddBoolean;
    private boolean dateofbrthBoolean;
    private boolean balanceBoolean;
    private boolean balanceAsonBoolean;
    private boolean genderBoolean;
    private boolean nomineNameBoolean;
    private boolean acctCategoryOption;
    private boolean disabledAcType = true;
    private boolean acnoUserChecked;
    private boolean annualIncomeBoolean;
    private boolean areaSectorBoolean;
    private boolean averageBalanceBoolean;
    private boolean riskCategorizationBoolean;
    private boolean ageWiseBoolean;
    private boolean panBoolean;
    private boolean kycBoolean;
    private boolean basicBoolean;
    private boolean daBoolean;
    private boolean aadharCardBoolean;
    private boolean disableAnnualIncome = true;
    private boolean disableArea = true;
    private boolean disableAverageBal = true;
    private boolean disableOccupation = true;
    private boolean disableRisk = true;
    private boolean disableAge = true;
    private boolean disableAcnoRange = true;
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote otherfacde;
    private List<SelectItem> statusList;
    private List<SelectItem> selectAcTypeList;
    private List<SelectItem> custStatusList;
    private List<SelectItem> orderByList;
    private List<SelectItem> branchList;
    private List<SelectItem> occupationList;
    private List<SelectItem> riskCategorizationList;
    private Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private boolean totalBoolean;
    private String reportOption;
    private List<SelectItem> reportOptionList;

    public ReportWriter() {
        try {
            /*Initialization of all selectitem list*/
            statusList = new ArrayList<SelectItem>();
            selectAcTypeList = new ArrayList<SelectItem>();
            custStatusList = new ArrayList<SelectItem>();
            orderByList = new ArrayList<SelectItem>();
            branchList = new ArrayList<SelectItem>();
            occupationList = new ArrayList<SelectItem>();
            riskCategorizationList = new ArrayList<SelectItem>();

            reportOptionList = new ArrayList<>();
            reportOptionList.add(new SelectItem("ALL", "ALL"));
            reportOptionList.add(new SelectItem("Atm", "Atm Holder"));

            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherfacde = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            initData();
        } catch (Exception e) {
            this.setErrMsg(e.getMessage());
        }
    }

    private void initData() {
        try {
            frmDate = date;
            toDate = date;
            Vector vtr = new Vector();
            List list = common.getAccTypeExcludePO();
            selectAcTypeList.add(new SelectItem("ALL"));
            for (int i = 0; i < list.size(); i++) {
                vtr = (Vector) list.get(i);
                selectAcTypeList.add(new SelectItem(vtr.get(0).toString()));
            }
            custStatusList.add(new SelectItem("ALL", "ALL"));
            List l = otherfacde.getRefcodeDesc("025");
            for (int i = 0; i < l.size(); i++) {
                vtr = (Vector) l.get(i);
                custStatusList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
//            String loginBrAlphaCode = common.getAlphacodeByBrncode(getOrgnBrCode());
            list = common.getBranchCodeList(getOrgnBrCode());
//            if (loginBrAlphaCode.equalsIgnoreCase("HO")) {
//                branchList.add(new SelectItem("All"));
//            }
            for (int i = 0; i < list.size(); i++) {
                vtr = (Vector) list.get(i);
                // branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(0).toString())), vtr.get(1).toString()));
                branchList.add(new SelectItem(vtr.get(0).toString().length() < 2 ? "0" + vtr.get(0).toString() : vtr.get(0).toString(), vtr.get(1).toString()));
            }
            orderByList.add(0, new SelectItem(0, "Customer Id"));
            orderByList.add(1, new SelectItem(1, "Customer Name"));

            List acList = common.getAccountStatus();
            statusList.add(new SelectItem("ALL", "ALL"));
            for (int i = 0; i < acList.size(); i++) {
                vtr = (Vector) acList.get(i);
                statusList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }

//            statusList.add(new SelectItem("ALL", "ALL"));
//            statusList.add(new SelectItem("O", "Operative"));
//            statusList.add(new SelectItem("I", "Inoperative"));

            occupationList.add(new SelectItem("--Select--"));
            occupationList.add(new SelectItem("All"));
            l = otherfacde.getRefcodeDesc("021");
            for (int i = 0; i < l.size(); i++) {
                vtr = (Vector) l.get(i);
                occupationList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
            riskCategorizationList.add(new SelectItem("--Select--"));
            riskCategorizationList.add(new SelectItem("All"));
            l = otherfacde.getRefcodeDesc("024");
            for (int i = 0; i < l.size(); i++) {
                vtr = (Vector) l.get(i);
                riskCategorizationList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
        } catch (Exception e) {
            this.setErrMsg(e.getMessage());
        }
    }

    public void acnoUserCheckUncheckOperation() {
        System.out.println("Helo inside in the user checked");
        if (acNoBoolean) {
            acnoUserChecked = true;
            basicBoolean = false;
            daBoolean = false;
        } else {
            acnoUserChecked = false;
        }
        addRemoveOrderBy();
    }

    public void checkUncheckAcnoOption() {
        if (!acnoUserChecked && !acNoBoolean) {
            if (accOpenDtBoolean || operatingBoolean || jtNameBoolean
                    || balanceBoolean || nomineNameBoolean || averageBalanceBoolean) {
                acNoBoolean = true;
            } else {
                acNoBoolean = false;
            }
            addRemoveOrderBy();
        }
    }

    public void checkUncheckCustDetailOption() {
        if (!customerIdBoolean) {
            if (nameBoolean || custAddBoolean || perAddBoolean || dateofbrthBoolean
                    || phoneNoBoolean || genderBoolean || fatherNameBoolean || panBoolean || basicBoolean || daBoolean || aadharCardBoolean) {
                customerIdBoolean = true;
            } else {
                customerIdBoolean = false;
            }
        }
    }

    public void addRemoveOrderBy() {
        if (acNoBoolean) {
            orderByList.add(2, new SelectItem(2, "Account No"));
            setDisabledAcType(false);
        } else {
            if (orderByList.size() > 2) {
                orderByList.remove(2);
                setDisabledAcType(true);
            }
        }
    }

    public void checkUnCheckAnnualIncome() {
        this.annualIncomeFrom = "";
        this.annualIncomeTo = "";
        if (annualIncomeBoolean) {
            this.disableAnnualIncome = false;
            if (!customerIdBoolean) {
                customerIdBoolean = true;
            }
        } else {
            this.disableAnnualIncome = true;
        }
    }

    public void checkUnCheckArea() {
        this.areaSector = "";
        if (areaSectorBoolean) {
            this.disableArea = false;
            if (!customerIdBoolean) {
                customerIdBoolean = true;
            }
        } else {
            this.disableArea = true;
        }
    }

    public void checkUnCheckRiskCat() {
        this.riskCategorization = "--Select--";
        if (riskCategorizationBoolean) {
            this.disableRisk = false;
            if (!customerIdBoolean) {
                customerIdBoolean = true;
            }
        } else {
            this.disableRisk = true;
        }
    }

    public void checkUnCheckAge() {
        this.fromAge = "";
        this.toAge = "";
        if (ageWiseBoolean) {
            this.disableAge = false;
            if (!customerIdBoolean) {
                customerIdBoolean = true;
            }
        } else {
            this.disableAge = true;
        }
    }

    public void checkUnCheckOccupation() {
        this.occupation = "--Select--";
        if (occupationBoolean) {
            this.disableOccupation = false;
            if (!customerIdBoolean) {
                customerIdBoolean = true;
            }
        } else {
            this.disableOccupation = true;
        }
    }

    public void acTypeAction() {
        this.setTxtFromAcno("");
        this.setTxtToAcno("");
        this.disableAcnoRange = true;
        if (acNoBoolean) {
            if (!this.selectAcType.equalsIgnoreCase("ALL")) {
                this.disableAcnoRange = false;
            }
        }
    }

//    public void checkUnCheckAvgBal() {
//        this.averageBalance = "";
//        if (averageBalanceBoolean) {
//            this.disableAverageBal = false;
//            if (!acNoBoolean) {
//                acNoBoolean = true;
//            }
//        } else {
//            this.disableAverageBal = true;
//        }
//    }
    public void checkUnCheckKyc() {
        errMsg = "";
        customerIdBoolean = false;
        nameBoolean = false;
        custAddBoolean = false;
        perAddBoolean = false;
        dateofbrthBoolean = false;
        phoneNoBoolean = false;
        genderBoolean = false;
        fatherNameBoolean = false;
        occupationBoolean = false;
        annualIncomeBoolean = false;
        areaSectorBoolean = false;
        riskCategorizationBoolean = false;
        ageWiseBoolean = false;
        acNoBoolean = false;
        accOpenDtBoolean = false;
        operatingBoolean = false;
        jtNameBoolean = false;
        nomineNameBoolean = false;
        acctCategoryOption = false;
        balanceBoolean = false;
        averageBalanceBoolean = false;
        basicBoolean = false;
        daBoolean = false;
        totalBoolean = false;
        aadharCardBoolean = false;
        selectAcType = "ALL";
        status = "A";
        this.setAnnualIncomeFrom("");
        this.setAnnualIncomeTo("");
        this.setAreaSector("");
//        this.setAverageBalance("");
        this.setOccupation("--Select--");
        this.setRiskCategorization("--Select--");
        this.setFromAge("");
        this.setToAge("");

        disabledAcType = true;
        disableAnnualIncome = true;
        disableAge = true;
        disableArea = true;
//        disableAverageBal = true;
        disableOccupation = true;
        disableRisk = true;
        this.setTxtFromAcno("");
        this.setTxtToAcno("");
        this.disableAcnoRange = true;
        addRemoveOrderBy();
    }

    /*Excel Format*/
    public void viewReport() {
        if (validateField()) {
            try {
                List dataList = null;
                FastReportBuilder fastReportBuilder = null;
                boolean shareQuery = false;
                if (basicBoolean || daBoolean) {
                    shareQuery = true;
                }
                if (kycBoolean) {
                    dataList = otherfacde.getKycReport(branch);
                    fastReportBuilder = genrateDaynamicReportForKyc();
                } else if (acNoBoolean) {
                    dataList = otherfacde.getReportWriter(ymd.format(frmDate), ymd.format(toDate), selectAcType,
                            Integer.parseInt(orderBy), balanceBoolean, branch, status, custStatus, averageBalanceBoolean,
                            annualIncomeFrom, annualIncomeTo, areaSector, occupation, riskCategorization, fromAge,
                            toAge, txtFromAcno, txtToAcno, reportOption);
                    fastReportBuilder = genrateDaynamicReport();
                } else {
                    dataList = otherfacde.getCustomersData(branch, Integer.parseInt(orderBy), custStatus,
                            annualIncomeFrom, annualIncomeTo, areaSector, occupation, riskCategorization, fromAge, toAge, shareQuery);
                    fastReportBuilder = genrateDaynamicReport();
                }
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(dataList), fastReportBuilder, reportName);
            } catch (Exception ex) {
                this.setErrMsg(ex.getMessage());
            }
        }
    }

    /*Pdf Format*/
    public void pdfReport() {
        if (validateField()) {
            try {
                List dataList = null;
                FastReportBuilder fastReportBuilder = null;
                boolean shareQuery = false;
                if (basicBoolean || daBoolean) {
                    shareQuery = true;
                }
                if (kycBoolean) {
                    dataList = otherfacde.getKycReport(branch);
                    fastReportBuilder = genrateDaynamicReportForKyc();
                } else if (acNoBoolean) {
                    dataList = otherfacde.getReportWriter(ymd.format(frmDate), ymd.format(toDate), selectAcType,
                            Integer.parseInt(orderBy), balanceBoolean, branch, status, custStatus, averageBalanceBoolean,
                            annualIncomeFrom, annualIncomeTo, areaSector, occupation, riskCategorization, fromAge, toAge,
                            txtFromAcno, txtToAcno, reportOption);
                    fastReportBuilder = genrateDaynamicReport();
                } else {
                    dataList = otherfacde.getCustomersData(branch, Integer.parseInt(orderBy), custStatus,
                            annualIncomeFrom, annualIncomeTo, areaSector, occupation, riskCategorization, fromAge, toAge, shareQuery);
                    fastReportBuilder = genrateDaynamicReport();
                }
                new ReportBean().dynamicJasperPdf(new JRBeanCollectionDataSource(dataList), fastReportBuilder, reportName);
            } catch (Exception ex) {
                this.setErrMsg(ex.getMessage());
            }
        }
    }

    /*Html Format*/
    public void htmlReport() {
        if (validateField()) {
            try {
                List dataList = null;
                FastReportBuilder fastReportBuilder = null;
                boolean shareQuery = false;
                if (basicBoolean || daBoolean) {
                    shareQuery = true;
                }
                if (kycBoolean) {
                    dataList = otherfacde.getKycReport(branch);
                    fastReportBuilder = genrateDaynamicReportForKyc();
                } else if (acNoBoolean) {
                    dataList = otherfacde.getReportWriter(ymd.format(frmDate), ymd.format(toDate), selectAcType,
                            Integer.parseInt(orderBy), balanceBoolean, branch, status, custStatus, averageBalanceBoolean,
                            annualIncomeFrom, annualIncomeTo, areaSector, occupation, riskCategorization, fromAge, toAge,
                            txtFromAcno, txtToAcno, reportOption);
                    fastReportBuilder = genrateDaynamicReport();
                } else {
                    dataList = otherfacde.getCustomersData(branch, Integer.parseInt(orderBy), custStatus,
                            annualIncomeFrom, annualIncomeTo, areaSector, occupation, riskCategorization, fromAge, toAge, shareQuery);
                    fastReportBuilder = genrateDaynamicReport();
                }
                new ReportBean().dynamicJasperHTML(new JRBeanCollectionDataSource(dataList), fastReportBuilder);
            } catch (Exception ex) {
                this.setErrMsg(ex.getMessage());
            }
        }
    }

    public void refresh() {
        customerIdBoolean = false;
        nameBoolean = false;
        fatherNameBoolean = false;
        nomineNameBoolean = false;
        acctCategoryOption = false;
        acNoBoolean = false;
        custAddBoolean = false;
        occupationBoolean = false;
        jtNameBoolean = false;
        phoneNoBoolean = false;
        operatingBoolean = false;
        accOpenDtBoolean = false;
        perAddBoolean = false;
        dateofbrthBoolean = false;
        genderBoolean = false;
        balanceBoolean = false;
        balanceAsonBoolean = false;
        basicBoolean = false;
        daBoolean = false;
        aadharCardBoolean = false;
        reportName = "";
        errMsg = "";
        selectAcType = "ALL";
        status = "A";
        setDisabledAcType(true);
        addRemoveOrderBy();

        /*New Addition*/
        this.setAnnualIncomeFrom("");
        this.setAnnualIncomeTo("");
        this.setAreaSector("");
//        this.setAverageBalance("");
        this.setOccupation("--Select--");
        this.setRiskCategorization("--Select--");
        this.setFromAge("");
        this.setToAge("");
        annualIncomeBoolean = false;
        areaSectorBoolean = false;
        averageBalanceBoolean = false;
        riskCategorizationBoolean = false;
        ageWiseBoolean = false;
        panBoolean = false;
        kycBoolean = false;
        disableAnnualIncome = true;
        disableAge = true;
        disableArea = true;
//        disableAverageBal = true;
        disableOccupation = true;
        disableRisk = true;
        this.setTxtFromAcno("");
        this.setTxtToAcno("");
        this.disableAcnoRange = true;
    }

    public FastReportBuilder genrateDaynamicReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            AbstractColumn custId = ReportBean.getColumn("custId", String.class, "Customer Id", 50);
            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account No", 100);
            AbstractColumn name = ReportBean.getColumn("name", String.class, "Customer Name", 200);
            AbstractColumn faterName = ReportBean.getColumn("fatherName", String.class, "Father's Name", 200);
            AbstractColumn nomineName = ReportBean.getColumn("nomineName", String.class, "Nomine's Name", 200);
            AbstractColumn acctCategory = ReportBean.getColumn("actCategory", String.class, "Account Category", 200);

            AbstractColumn crrAdd = ReportBean.getColumn("crrAdd", String.class, "Communication Address", 400);
            AbstractColumn occupation = ReportBean.getColumn("occupation", String.class, "Occupation", 50);
            AbstractColumn acctype = ReportBean.getColumn("acctype", String.class, "A/C type", 22);

            AbstractColumn phoneNo = ReportBean.getColumn("phoneNo", String.class, "Phone No", 70);
            AbstractColumn OperationMode = ReportBean.getColumn("operationMode", String.class, "Operation Mode", 80);
            AbstractColumn accopenDt = ReportBean.getColumn("accopenDt", String.class, "Opening Date", 60);
            AbstractColumn perAdd = ReportBean.getColumn("perAdd", String.class, "Permanent Address", 200);

            AbstractColumn jtName = ReportBean.getColumn("jtName", String.class, "Joint Holder Name", 100);
            AbstractColumn dateofBirth = ReportBean.getColumn("dateofBirth", String.class, "Date of Birth", 60);
            AbstractColumn openBal = ReportBean.getColumn("openBal", Double.class, "Opening balance", 50);
            AbstractColumn closeBal = ReportBean.getColumn("closeBal", Double.class, "Closing Balance", 50);
            AbstractColumn gender = ReportBean.getColumn("gender", String.class, "Gender", 55);

            AbstractColumn annualIncome = ReportBean.getColumn("annualIncome", BigDecimal.class, "Annual Income", 50);
            AbstractColumn area = ReportBean.getColumn("area", String.class, "Area", 100);
            AbstractColumn averageBal = ReportBean.getColumn("averageBalance", BigDecimal.class, "Average Balance", 50);
            AbstractColumn riskCat = ReportBean.getColumn("riskCategorization", String.class, "Risk Categorization", 100);
            AbstractColumn age = ReportBean.getColumn("age", Integer.class, "Age", 30);
            AbstractColumn panNo = ReportBean.getColumn("panNo", String.class, "Pan No.", 80);
            AbstractColumn aadharCard = ReportBean.getColumn("aadharCard", String.class, "Aadhar Card", 80);
            AbstractColumn basic = ReportBean.getColumn("basic", Double.class, "Basic", 80);
            AbstractColumn da = ReportBean.getColumn("da", Double.class, "DA", 80);
            AbstractColumn total = ReportBean.getColumn("total", Double.class, "Total Salary", 80);


            if (customerIdBoolean) {
                fastReport.addColumn(custId);
                width = width + custId.getWidth();
            }
            if (acNoBoolean) {
                fastReport.addColumn(acNo);
                width = width + acNo.getWidth();
            }
            if (nameBoolean) {
                fastReport.addColumn(name);
                width = width + name.getWidth();
            }
            if (fatherNameBoolean) {
                fastReport.addColumn(faterName);
                width = width + faterName.getWidth();
            }
            if (nomineNameBoolean) {
                fastReport.addColumn(nomineName);
                width = width + nomineName.getWidth();
            }
            if (acctCategoryOption) {
                fastReport.addColumn(acctCategory);
                width = width + acctCategory.getWidth();
            }
            if (custAddBoolean) {
                fastReport.addColumn(crrAdd);
                width = width + crrAdd.getWidth();
            }
            if (occupationBoolean) {
                fastReport.addColumn(occupation);
                width = width + occupation.getWidth();
            }
            if (genderBoolean) {
                fastReport.addColumn(gender);
                width = width + gender.getWidth();
            }
            if (jtNameBoolean) {
                fastReport.addColumn(jtName);
                width = width + jtName.getWidth();
            }
            if (phoneNoBoolean) {
                fastReport.addColumn(phoneNo);
                width = width + phoneNo.getWidth();
            }
            if (operatingBoolean) {
                fastReport.addColumn(OperationMode);
                width = width + OperationMode.getWidth();
            }
            if (accOpenDtBoolean) {
                fastReport.addColumn(accopenDt);
                width = width + accopenDt.getWidth();
            }
            if (perAddBoolean) {
                fastReport.addColumn(perAdd);
                width = width + perAdd.getWidth();
            }
            if (dateofbrthBoolean) {
                fastReport.addColumn(dateofBirth);
                width = width + dateofBirth.getWidth();
            }
            if (balanceBoolean) {
                fastReport.addColumn(openBal);
                Style style = new Style();
                style.setHorizontalAlign(HorizontalAlign.RIGHT);
                style.setBorder(Border.THIN);
                style.setPattern("##0.00");
                closeBal.setStyle(style);
                openBal.setStyle(style);
                fastReport.addColumn(closeBal);
                width = width + 2 * openBal.getWidth();
            }
            if (annualIncomeBoolean) {
                fastReport.addColumn(annualIncome);
                width = width + acNo.getWidth();
            }
            if (areaSectorBoolean) {
                fastReport.addColumn(area);
                width = width + acNo.getWidth();
            }
            if (averageBalanceBoolean) {
                Style style = new Style();
                style.setHorizontalAlign(HorizontalAlign.RIGHT);
                style.setBorder(Border.THIN);
                style.setPattern("##0.00");
                averageBal.setStyle(style);
                fastReport.addColumn(averageBal);
                width = width + acNo.getWidth();
            }
            if (riskCategorizationBoolean) {
                fastReport.addColumn(riskCat);
                width = width + acNo.getWidth();
            }
            if (ageWiseBoolean) {
                fastReport.addColumn(age);
                width = width + acNo.getWidth();
            }
            if (panBoolean) {
                fastReport.addColumn(panNo);
                width = width + panNo.getWidth();
            }
            if (aadharCardBoolean) {
                fastReport.addColumn(aadharCard);
                width = width + aadharCard.getWidth();
            }
            if (basicBoolean) {
                fastReport.addColumn(basic);
                width = width + basic.getWidth();
            }
            if (daBoolean) {
                fastReport.addColumn(da);
                width = width + da.getWidth();
            }
            if (basicBoolean && daBoolean) {
                fastReport.addColumn(total);
                width = width + total.getWidth();

            }

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle(reportName);

        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
        return fastReport;
    }

    public FastReportBuilder genrateDaynamicReportForKyc() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            AbstractColumn custId = ReportBean.getColumn("custId", String.class, "Customer Id", 50);
            AbstractColumn dependents = ReportBean.getColumn("dependents", String.class, "Dependents", 90);
            AbstractColumn noChild = ReportBean.getColumn("noChild", Integer.class, "No.Child", 80);
            AbstractColumn noMales10 = ReportBean.getColumn("noMales10", Integer.class, "No.Males10", 80);

            AbstractColumn noMales20 = ReportBean.getColumn("noMales20", Integer.class, "No.Males20", 80);
            AbstractColumn noMales45 = ReportBean.getColumn("noMales45", Integer.class, "No.Males45", 80);
            AbstractColumn noMales60 = ReportBean.getColumn("noMales60", Integer.class, "No.Males60", 80);
            AbstractColumn noMalesAbove61 = ReportBean.getColumn("noMalesAbove61", Integer.class, "No.Males Above61", 80);

            AbstractColumn noFem10 = ReportBean.getColumn("noFem10", Integer.class, "No.Fem10", 80);
            AbstractColumn noFem20 = ReportBean.getColumn("noFem20", Integer.class, "No.Fem20", 80);
            AbstractColumn noFem45 = ReportBean.getColumn("noFem45", Integer.class, "No.Fem45", 80);
            AbstractColumn noFem60 = ReportBean.getColumn("noFem60", Integer.class, "No.Fem60", 80);
            AbstractColumn noFemAbove61 = ReportBean.getColumn("noFemAbove61", Integer.class, "No.Fem Above61", 80);



            if (kycBoolean) {
                fastReport.addColumn(custId);
                width = width + custId.getWidth();

                fastReport.addColumn(dependents);
                width = width + dependents.getWidth();

                fastReport.addColumn(noChild);
                width = width + noChild.getWidth();

                fastReport.addColumn(noMales10);
                width = width + noMales10.getWidth();

                fastReport.addColumn(noMales20);
                width = width + noMales20.getWidth();

                fastReport.addColumn(noMales45);
                width = width + noMales45.getWidth();

                fastReport.addColumn(noMales60);
                width = width + noMales60.getWidth();

                fastReport.addColumn(noMalesAbove61);
                width = width + noMalesAbove61.getWidth();

                fastReport.addColumn(noFem10);
                width = width + noFem10.getWidth();

                fastReport.addColumn(noFem20);
                width = width + noFem20.getWidth();

                fastReport.addColumn(noFem45);
                width = width + noFem45.getWidth();

                fastReport.addColumn(noFem60);
                width = width + noFem60.getWidth();

                fastReport.addColumn(noFemAbove61);
                width = width + noFemAbove61.getWidth();


            }

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle(reportName);
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
        return fastReport;
    }

    private boolean validateField() {
        Pattern amtPattern = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Pattern intPattern = Pattern.compile("[0-9]*");
        if (reportName == null || reportName.trim().equals("")) {
            errMsg = "Please Enter Report Name";
            return false;
        }
        if (frmDate == null) {
            errMsg = "Please Enter from date";
            return false;
        }
        if (toDate == null) {
            errMsg = "Please Enter to date";
            return false;
        }
        if (frmDate.after(toDate)) {
            errMsg = "Please from date is less than to date";
            return false;
        }
        if (this.occupationBoolean) {
            if (this.occupation.equalsIgnoreCase("--Select--")) {
                errMsg = "Please select occupation.";
                return false;
            }
        }
        if (this.annualIncomeBoolean) {
            if (this.annualIncomeFrom == null || this.annualIncomeFrom.equals("")) {
                errMsg = "Please fill From Annual Income.";
                return false;
            }
            if (this.annualIncomeTo == null || this.annualIncomeTo.equals("")) {
                errMsg = "Please fill To Annual Income.";
                return false;
            }
            Matcher m = amtPattern.matcher(this.annualIncomeFrom);
            if (!m.matches()) {
                errMsg = "Please fill proper From Annual Income.";
                return false;
            }
            if (new BigDecimal(this.annualIncomeFrom).compareTo(new BigDecimal("0")) == -1) {
                errMsg = "From Annual Income can not be negative.";
                return false;
            }
            if (this.annualIncomeFrom.contains(".")) {
                if (this.annualIncomeFrom.indexOf(".") != this.annualIncomeFrom.lastIndexOf(".")) {
                    errMsg = "Invalid amount. Please fill the From Annual Income correctly.";
                    return false;
                } else if (this.annualIncomeFrom.substring(annualIncomeFrom.indexOf(".") + 1).length() > 2) {
                    errMsg = "Please fill the From Annual Income upto two decimal places.";
                    return false;
                }
            }
            m = amtPattern.matcher(this.annualIncomeTo);
            if (!m.matches()) {
                errMsg = "Please fill proper To Annual Income.";
                return false;
            }
            if (this.annualIncomeTo.equalsIgnoreCase("0") || this.annualIncomeTo.equalsIgnoreCase("0.0")) {
                errMsg = "To Annual Income can not be zero";
                return false;
            }
            if (new BigDecimal(this.annualIncomeTo).compareTo(new BigDecimal("0")) == -1) {
                errMsg = "To Annual Income can not be negative.";
                return false;
            }
            if (this.annualIncomeTo.contains(".")) {
                if (this.annualIncomeTo.indexOf(".") != this.annualIncomeTo.lastIndexOf(".")) {
                    errMsg = "Invalid amount. Please fill the To Annual Income correctly.";
                    return false;
                } else if (this.annualIncomeTo.substring(annualIncomeTo.indexOf(".") + 1).length() > 2) {
                    errMsg = "Please fill the To Annual Income upto two decimal places.";
                    return false;
                }
            }
            if (new BigDecimal(this.annualIncomeFrom).compareTo(new BigDecimal(this.annualIncomeTo)) >= 0) {
                errMsg = "To Annual Income should be greater than From Annual Income.";
                return false;
            }
        }
//        if (this.areaSectorBoolean) {
//            if (this.areaSector == null || this.areaSector.equals("")) {
//                errMsg = "Please fill Area/Sector.";
//                return false;
//            }
//        }
//        if (this.averageBalanceBoolean) {
//            if (this.averageBalance == null || this.averageBalance.equals("")) {
//                errMsg = "Please fill Average Balance.";
//                return false;
//            }
//            Matcher m = amtPattern.matcher(this.averageBalance);
//            if (!m.matches()) {
//                errMsg = "Please fill proper Average Balance.";
//                return false;
//            }
//            if (this.averageBalance.equalsIgnoreCase("0") || this.averageBalance.equalsIgnoreCase("0.0")) {
//                errMsg = "Average Balance can not be zero";
//                return false;
//            }
//            if (new BigDecimal(this.averageBalance).compareTo(new BigDecimal("0")) == -1) {
//                errMsg = "Average Balance can not be negative.";
//                return false;
//            }
//            if (this.averageBalance.contains(".")) {
//                if (this.averageBalance.indexOf(".") != this.averageBalance.lastIndexOf(".")) {
//                    errMsg = "Invalid amount. Please fill the Average Balance correctly.";
//                    return false;
//                } else if (this.averageBalance.substring(averageBalance.indexOf(".") + 1).length() > 2) {
//                    errMsg = "Please fill the Average Balance upto two decimal places.";
//                    return false;
//                }
//            }
//        }
        if (this.riskCategorizationBoolean) {
            if (this.riskCategorization.equalsIgnoreCase("--Select--")) {
                errMsg = "Please select Risk Categorization.";
                return false;
            }
        }
        if (this.ageWiseBoolean) {
            if (this.fromAge == null || this.fromAge.equals("")) {
                errMsg = "Please fill From Age.";
                return false;
            }
            if (this.toAge == null || this.toAge.equals("")) {
                errMsg = "Please fill To Age.";
                return false;
            }
            Matcher m = intPattern.matcher(this.fromAge);
            if (!m.matches()) {
                errMsg = "Only numeric values are allowed for From Age.";
                return false;
            }
            if (this.fromAge.equals("0") || this.fromAge.equals("0.0")) {
                errMsg = "From Age can not be zero";
                return false;
            }
            if (new BigDecimal(this.fromAge).compareTo(new BigDecimal("0")) == -1) {
                errMsg = "From Age can not be negative.";
                return false;
            }
            m = intPattern.matcher(this.toAge);
            if (!m.matches()) {
                errMsg = "Only numeric values are allowed for To Age.";
                return false;
            }
            if (this.toAge.equals("0") || this.toAge.equals("0.0")) {
                errMsg = "To Age can not be zero";
                return false;
            }
            if (new BigDecimal(this.toAge).compareTo(new BigDecimal("0")) == -1) {
                errMsg = "To Age can not be negative.";
                return false;
            }
            if (Integer.parseInt(this.fromAge) >= Integer.parseInt(this.toAge)) {
                errMsg = "To Age should be greater than From Age.";
                return false;
            }
        }
        return true;
    }

    /*Getter And Setter*/
    public boolean isAcctCategoryOption() {
        return acctCategoryOption;
    }

    public void setAcctCategoryOption(boolean acctCategoryOption) {
        this.acctCategoryOption = acctCategoryOption;
    }

    public boolean isNomineNameBoolean() {
        return nomineNameBoolean;
    }

    public void setNomineNameBoolean(boolean nomineNameBoolean) {
        this.nomineNameBoolean = nomineNameBoolean;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public boolean isGenderBoolean() {
        return genderBoolean;
    }

    public void setGenderBoolean(boolean genderBoolean) {
        this.genderBoolean = genderBoolean;
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<SelectItem> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<SelectItem> orderByList) {
        this.orderByList = orderByList;
    }

    public String getSelectAcType() {
        return selectAcType;
    }

    public void setSelectAcType(String selectAcType) {
        this.selectAcType = selectAcType;
    }

    public String getCustStatus() {
        return custStatus;
    }

    public void setCustStatus(String custStatus) {
        this.custStatus = custStatus;
    }

    public List<SelectItem> getSelectAcTypeList() {
        return selectAcTypeList;
    }

    public void setSelectAcTypeList(List<SelectItem> selectAcTypeList) {
        this.selectAcTypeList = selectAcTypeList;
    }

    public List<SelectItem> getCustStatusList() {
        return custStatusList;
    }

    public void setCustStatusList(List<SelectItem> custStatusList) {
        this.custStatusList = custStatusList;
    }

    public boolean isAcNoBoolean() {
        return acNoBoolean;
    }

    public void setAcNoBoolean(boolean acNoBoolean) {
        this.acNoBoolean = acNoBoolean;
    }

    public boolean isAccOpenDtBoolean() {
        return accOpenDtBoolean;
    }

    public void setAccOpenDtBoolean(boolean accOpenDtBoolean) {
        this.accOpenDtBoolean = accOpenDtBoolean;
    }

    public boolean isJtNameBoolean() {
        return jtNameBoolean;
    }

    public void setJtNameBoolean(boolean jtNameBoolean) {
        this.jtNameBoolean = jtNameBoolean;
    }

    public boolean isBalanceAsonBoolean() {
        return balanceAsonBoolean;
    }

    public void setBalanceAsonBoolean(boolean balanceAsonBoolean) {
        this.balanceAsonBoolean = balanceAsonBoolean;
    }

    public boolean isBalanceBoolean() {
        return balanceBoolean;
    }

    public void setBalanceBoolean(boolean balanceBoolean) {
        this.balanceBoolean = balanceBoolean;
    }

    public boolean isCustAddBoolean() {
        return custAddBoolean;
    }

    public void setCustAddBoolean(boolean custAddBoolean) {
        this.custAddBoolean = custAddBoolean;
    }

    public boolean isCustomerIdBoolean() {
        return customerIdBoolean;
    }

    public void setCustomerIdBoolean(boolean customerIdBoolean) {
        this.customerIdBoolean = customerIdBoolean;
    }

    public boolean isDateofbrthBoolean() {
        return dateofbrthBoolean;
    }

    public void setDateofbrthBoolean(boolean dateofbrthBoolean) {
        this.dateofbrthBoolean = dateofbrthBoolean;
    }

    public boolean isNameBoolean() {
        return nameBoolean;
    }

    public void setNameBoolean(boolean nameBoolean) {
        this.nameBoolean = nameBoolean;
    }

    public boolean isFatherNameBoolean() {
        return fatherNameBoolean;
    }

    public void setFatherNameBoolean(boolean fatherNameBoolean) {
        this.fatherNameBoolean = fatherNameBoolean;
    }

    public boolean isOccupationBoolean() {
        return occupationBoolean;
    }

    public void setOccupationBoolean(boolean occupationBoolean) {
        this.occupationBoolean = occupationBoolean;
    }

    public boolean isOperatingBoolean() {
        return operatingBoolean;
    }

    public void setOperatingBoolean(boolean operatingBoolean) {
        this.operatingBoolean = operatingBoolean;
    }

    public boolean isPerAddBoolean() {
        return perAddBoolean;
    }

    public void setPerAddBoolean(boolean perAddBoolean) {
        this.perAddBoolean = perAddBoolean;
    }

    public boolean isPhoneNoBoolean() {
        return phoneNoBoolean;
    }

    public void setPhoneNoBoolean(boolean phoneNoBoolean) {
        this.phoneNoBoolean = phoneNoBoolean;
    }

    public boolean isDisabledAcType() {
        return disabledAcType;
    }

    public void setDisabledAcType(boolean disabledAcType) {
        this.disabledAcType = disabledAcType;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Date getFrmDate() {
        return frmDate;
    }

    public void setFrmDate(Date frmDate) {
        this.frmDate = frmDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getAnnualIncomeFrom() {
        return annualIncomeFrom;
    }

    public void setAnnualIncomeFrom(String annualIncomeFrom) {
        this.annualIncomeFrom = annualIncomeFrom;
    }

    public String getAnnualIncomeTo() {
        return annualIncomeTo;
    }

    public void setAnnualIncomeTo(String annualIncomeTo) {
        this.annualIncomeTo = annualIncomeTo;
    }

    public String getAreaSector() {
        return areaSector;
    }

    public void setAreaSector(String areaSector) {
        this.areaSector = areaSector;
    }

    public String getAverageBalance() {
        return averageBalance;
    }

    public void setAverageBalance(String averageBalance) {
        this.averageBalance = averageBalance;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getRiskCategorization() {
        return riskCategorization;
    }

    public void setRiskCategorization(String riskCategorization) {
        this.riskCategorization = riskCategorization;
    }

    public String getFromAge() {
        return fromAge;
    }

    public void setFromAge(String fromAge) {
        this.fromAge = fromAge;
    }

    public String getToAge() {
        return toAge;
    }

    public void setToAge(String toAge) {
        this.toAge = toAge;
    }

    public boolean isAnnualIncomeBoolean() {
        return annualIncomeBoolean;
    }

    public void setAnnualIncomeBoolean(boolean annualIncomeBoolean) {
        this.annualIncomeBoolean = annualIncomeBoolean;
    }

    public boolean isAreaSectorBoolean() {
        return areaSectorBoolean;
    }

    public void setAreaSectorBoolean(boolean areaSectorBoolean) {
        this.areaSectorBoolean = areaSectorBoolean;
    }

    public boolean isAverageBalanceBoolean() {
        return averageBalanceBoolean;
    }

    public void setAverageBalanceBoolean(boolean averageBalanceBoolean) {
        this.averageBalanceBoolean = averageBalanceBoolean;
    }

    public boolean isRiskCategorizationBoolean() {
        return riskCategorizationBoolean;
    }

    public void setRiskCategorizationBoolean(boolean riskCategorizationBoolean) {
        this.riskCategorizationBoolean = riskCategorizationBoolean;
    }

    public boolean isAgeWiseBoolean() {
        return ageWiseBoolean;
    }

    public void setAgeWiseBoolean(boolean ageWiseBoolean) {
        this.ageWiseBoolean = ageWiseBoolean;
    }

    public boolean isPanBoolean() {
        return panBoolean;
    }

    public void setPanBoolean(boolean panBoolean) {
        this.panBoolean = panBoolean;
    }

    public List<SelectItem> getOccupationList() {
        return occupationList;
    }

    public void setOccupationList(List<SelectItem> occupationList) {
        this.occupationList = occupationList;
    }

    public List<SelectItem> getRiskCategorizationList() {
        return riskCategorizationList;
    }

    public void setRiskCategorizationList(List<SelectItem> riskCategorizationList) {
        this.riskCategorizationList = riskCategorizationList;
    }

    public boolean isDisableAnnualIncome() {
        return disableAnnualIncome;
    }

    public void setDisableAnnualIncome(boolean disableAnnualIncome) {
        this.disableAnnualIncome = disableAnnualIncome;
    }

    public boolean isDisableArea() {
        return disableArea;
    }

    public void setDisableArea(boolean disableArea) {
        this.disableArea = disableArea;
    }

    public boolean isDisableAverageBal() {
        return disableAverageBal;
    }

    public void setDisableAverageBal(boolean disableAverageBal) {
        this.disableAverageBal = disableAverageBal;
    }

    public boolean isDisableOccupation() {
        return disableOccupation;
    }

    public void setDisableOccupation(boolean disableOccupation) {
        this.disableOccupation = disableOccupation;
    }

    public boolean isDisableRisk() {
        return disableRisk;
    }

    public void setDisableRisk(boolean disableRisk) {
        this.disableRisk = disableRisk;
    }

    public boolean isDisableAge() {
        return disableAge;
    }

    public void setDisableAge(boolean disableAge) {
        this.disableAge = disableAge;
    }

    public boolean isKycBoolean() {
        return kycBoolean;
    }

    public void setKycBoolean(boolean kycBoolean) {
        this.kycBoolean = kycBoolean;
    }

    public String getTxtFromAcno() {
        return txtFromAcno;
    }

    public void setTxtFromAcno(String txtFromAcno) {
        this.txtFromAcno = txtFromAcno;
    }

    public String getTxtToAcno() {
        return txtToAcno;
    }

    public void setTxtToAcno(String txtToAcno) {
        this.txtToAcno = txtToAcno;
    }

    public boolean isDisableAcnoRange() {
        return disableAcnoRange;
    }

    public void setDisableAcnoRange(boolean disableAcnoRange) {
        this.disableAcnoRange = disableAcnoRange;
    }

    public boolean isBasicBoolean() {
        return basicBoolean;
    }

    public void setBasicBoolean(boolean basicBoolean) {
        this.basicBoolean = basicBoolean;
    }

    public boolean isDaBoolean() {
        return daBoolean;
    }

    public void setDaBoolean(boolean daBoolean) {
        this.daBoolean = daBoolean;

    }

    public boolean isAadharCardBoolean() {
        return aadharCardBoolean;
    }

    public void setAadharCardBoolean(boolean aadharCardBoolean) {
        this.aadharCardBoolean = aadharCardBoolean;
    }

    public String getReportOption() {
        return reportOption;
    }

    public void setReportOption(String reportOption) {
        this.reportOption = reportOption;
    }

    public List<SelectItem> getReportOptionList() {
        return reportOptionList;
    }

    public void setReportOptionList(List<SelectItem> reportOptionList) {
        this.reportOptionList = reportOptionList;
    }
}
