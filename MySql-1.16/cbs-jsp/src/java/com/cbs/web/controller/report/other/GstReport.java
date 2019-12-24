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
import com.cbs.facade.master.OtherMasterFacadeRemote;
import com.cbs.facade.master.TermDepositMasterFacadeRemote;
import com.cbs.facade.other.OtherMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.pojo.GstReportPojo;
import com.cbs.pojo.SortedByInvoiceNo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author root
 */
public class GstReport extends BaseBean {

    private String report;
    private List<SelectItem> reportList;
    private String option;
    private List<SelectItem> optionList;
    private String months;
    private List<SelectItem> monthsList;
    private String year;
    private String acNo;
    private boolean disableAcno;
    private boolean disablebntPdf;
    private boolean disablebntLetter;
    private boolean disableBntGen;
    private String branch;
    private List<SelectItem> branchList;
    private String gstPanelShow = "none";
    private String gstNo;
    private OtherMasterFacadeRemote othMasterRemote;
    private CommonReportMethodsRemote commonBeanRemote;
    private ShareReportFacadeRemote shareRemote;
    private TermDepositMasterFacadeRemote tdRemote;
    private OtherMgmtFacadeRemote othermgmtRemote;
    private String message;
    private String fromDate;
    private String toDate;
    private String finalDate;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    List<GstReportPojo> repList = new ArrayList<GstReportPojo>();

    public GstReport() {
        try {
            this.acNo = "";
            this.gstNo = "";
            othMasterRemote = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");
            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            shareRemote = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            tdRemote = (TermDepositMasterFacadeRemote) ServiceLocator.getInstance().lookup("TermDepositMasterFacade");
            othermgmtRemote = (OtherMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherMgmtFacade");

            List brnLst = new ArrayList();
            brnLst = commonBeanRemote.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            reportList = new ArrayList<SelectItem>();
            reportList.add(new SelectItem("0", "--SELECT--"));
            reportList.add(new SelectItem("GSTR-B", "GSTR-B"));
            reportList.add(new SelectItem("GSTR-C", "GSTR-C"));

            optionList = new ArrayList<SelectItem>();
            optionList.add(new SelectItem("0", "--SELECT--"));
            optionList.add(new SelectItem("OutwardSupply", "Outward Supply(Detail)"));
            optionList.add(new SelectItem("Individual Account", "Individual Account"));
            optionList.add(new SelectItem("Summary", "Summary"));
            optionList.add(new SelectItem("Individual Gst", "Individual Gst"));

            monthsList = new ArrayList<SelectItem>();
            monthsList.add(new SelectItem("0", "--SELECT--"));
            monthsList.add(new SelectItem("1", "January"));
            monthsList.add(new SelectItem("2", "Febuary"));
            monthsList.add(new SelectItem("3", "March"));
            monthsList.add(new SelectItem("4", "April"));
            monthsList.add(new SelectItem("5", "May"));
            monthsList.add(new SelectItem("6", "June"));
            monthsList.add(new SelectItem("7", "July"));
            monthsList.add(new SelectItem("8", "August"));
            monthsList.add(new SelectItem("9", "September"));
            monthsList.add(new SelectItem("10", "October"));
            monthsList.add(new SelectItem("11", "November"));
            monthsList.add(new SelectItem("12", "December"));

            setDisableBntGen(true);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void actionOnOption() {
        setAcNo("");

        try {
//            List fYearList = tdRemote.fYearData(getOrgnBrCode());
//            Vector fVector = (Vector) fYearList.get(0);        
//            this.setYear(fVector.get(0).toString());

            this.setYear(ymdFormat.format(new Date()).substring(0, 4));
            if (option.equalsIgnoreCase("Individual Account")) {
                this.disableAcno = false;
                this.disablebntPdf = false;
                this.disablebntLetter = false;
                this.gstPanelShow = "none";
            } else if (option.equalsIgnoreCase("Individual Gst")) {
                this.disableAcno = true;
                this.disablebntPdf = false;
                this.disablebntLetter = false;
                this.gstPanelShow = "";
                this.setMessage("Please ignore the Account no.");
            } else {
                this.disableAcno = true;
                this.disablebntPdf = true;
                this.disablebntLetter = true;
                this.gstPanelShow = "none";
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public String validateForm() {
        if (this.report.equalsIgnoreCase("0") || this.report.equalsIgnoreCase("") || this.report == null) {
            this.setMessage("Please Select Report");
            return "false";
        }
        if (this.option.equalsIgnoreCase("0") || this.option.equalsIgnoreCase("") || this.option == null) {
            setMessage("Please Select Option");
            return "false";
        }
        if (this.months.equalsIgnoreCase("0") || this.months.equalsIgnoreCase("") || this.months == null) {
            setMessage("Please Select Month");
            return "false";
        }
        if (this.year == null || this.year.equalsIgnoreCase("")) {
            setMessage("Please Enter Year");
            return "false";
        }
        if (!(this.year.matches("[0-9]+"))) {
            setMessage("Please Enter Valid Year");
            return "false";
        } else if (Integer.parseInt(getYear()) < 999 || Integer.parseInt(getYear()) > 10000) {
            setMessage("Please Enter Valid Year");
            return "false";
        }

        if (option.equalsIgnoreCase("Individual Account")) {
            if (acNo == null || acNo.equalsIgnoreCase("")) {
                setMessage("Please fill Account Number !!!");
                return "false";
            }
        }
        //     Pattern p = Pattern.compile("^(0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])[/](19|[2-9][0-9])[0-9]{2}$") ;
        //     Matcher enteredYear =p.matcher(this.year);
        //     if(!enteredYear.matches()){
        //         setMessage("Please Enter Valid Year");
        //        return "false";
        //    }
        return "true";
    }

    public void actionOnYear() {
        setMessage("");

        try {
            setDisableBntGen(true);
            this.toDate = ymdFormat.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.months), Integer.parseInt(year)));
            this.fromDate = toDate.substring(0, 6) + "01";
            if (report.equalsIgnoreCase("GSTR-C")) {
                setDisableBntGen(true);
            } else {
//                String firstDt = CbsUtil.getFirstDateOfGivenDate(new Date());
//                Date monthEndDt = CbsUtil.getLastDateOfMonth(new Date());
                if (option.equalsIgnoreCase("Summary")) {
                    setDisableBntGen(true);
                } else {
                    if (option.equalsIgnoreCase("Individual Account") || option.equalsIgnoreCase("Individual Gst")) {
                        setDisableBntGen(true);
                    } else {
                        List monthList = commonBeanRemote.getMonthEndList(toDate);
                        if (!monthList.isEmpty()) {
                            setMessage("You have not completed your month end process. Please check it.");
                            return;
                        }
                        List isVoucherList = othMasterRemote.isVoucherGen("B", fromDate, toDate);
                        if (isVoucherList.isEmpty()) {
                            setDisableBntGen(false);
                        } else {
                            setDisableBntGen(true);
                        }
                    }
                }
                if (!branch.equalsIgnoreCase("0A")) {
                    setDisableBntGen(true);
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void GenerateInvoiceNoProcess() {
        setMessage("");
        try {
            this.toDate = ymdFormat.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.months), Integer.parseInt(year)));
            this.fromDate = toDate.substring(0, 6) + "01";

            List monthList = commonBeanRemote.getMonthEndList(toDate);
            if (!monthList.isEmpty()) {
                setMessage("You have not completed your month end process. Please check it.");
                return;
            }
            String result = othMasterRemote.savedGenerateInvoiceData(acNo, year, getOrgnBrCode(), getUserName(), "B", fromDate, toDate);
            if (result.contains("true")) {
                String v[] = result.split(":");
                setMessage("Invoice No. Generate Sucessfully.");
                setDisableBntGen(true);
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void viewReport() {
        setMessage("");
        try {
            String isValidate = validateForm();
            if (isValidate.equalsIgnoreCase("true")) {
                this.toDate = ymdFormat.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.months), Integer.parseInt(year)));
                this.fromDate = toDate.substring(0, 6) + "01";

                repList = othMasterRemote.getGstrData(report, option, fromDate, toDate, branch, acNo, gstNo);
                if (repList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
                Map fillParams = new HashMap();
                List branchInfo = othMasterRemote.getBranchInfoByBrCode(branch.equalsIgnoreCase("0A") ? "90" : branch);
                Vector vtr = (Vector) branchInfo.get(0);

                fillParams.put("pBnkName", vtr.get(0).toString().toUpperCase());
                fillParams.put("pBnkAdd", vtr.get(1).toString().toUpperCase());
                fillParams.put("pBrPhone", vtr.get(3).toString().toUpperCase());
                fillParams.put("pBnkGstn", vtr.get(5).toString().toUpperCase());
                fillParams.put("pState", vtr.get(6).toString().toUpperCase());
                if (option.equalsIgnoreCase("Individual Account")) {
                    List custInfoList = othMasterRemote.getCustomerInfoByCustNo(acNo);
                    Vector vtr2 = (Vector) custInfoList.get(0);
                    fillParams.put("pAcno", acNo);
                    fillParams.put("pCustName", vtr2.get(0).toString());
                    fillParams.put("pAdd1", vtr2.get(1).toString());
                    fillParams.put("pAdd2", vtr2.get(2).toString());
                    fillParams.put("pDist", shareRemote.refDesc("001", vtr2.get(4).toString()));
                    fillParams.put("pMob", vtr2.get(5).toString());
                    fillParams.put("pPin", vtr2.get(3).toString());
                    fillParams.put("pSac", othMasterRemote.getCodeCbsparameterInfo("SAC-Code"));
                } else if (option.equalsIgnoreCase("Individual Gst")) {
                    List listvender = othermgmtRemote.getVenderDetail(this.gstNo);
                    Vector vec = (Vector) listvender.get(0);
                    fillParams.put("pAcno", "");
                    fillParams.put("pCustName", vec.get(1).toString());
                    fillParams.put("pAdd1", vec.get(2).toString());
                    fillParams.put("pAdd2", "");
                    fillParams.put("pDist", "");
                    fillParams.put("pMob", "");
                    fillParams.put("pPin", "");
                }
                fillParams.put("pAlphaCode", commonBeanRemote.getAlphacodeByBrncode(branch.equalsIgnoreCase("0A") ? "90" : branch));
                new ReportBean().openPdf("Gst Report", "Gst_Report", new JRBeanCollectionDataSource(repList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", "GST Report");
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void printLetter() {
        setMessage("");
        String isValidate = validateForm();
        if (isValidate.equalsIgnoreCase("true")) {
            try {
                this.toDate = ymdFormat.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.months), Integer.parseInt(year)));
                this.fromDate = toDate.substring(0, 6) + "01";
                repList = othMasterRemote.getGstrData(report, option, fromDate, toDate, branch, acNo, gstNo);
                if (repList.isEmpty()) {
                    throw new ApplicationException("Data does not exist!");
                }
                Map fillParams = new HashMap();
                List branchInfo = othMasterRemote.getBranchInfoByBrCode(branch.equalsIgnoreCase("0A") ? "90" : branch);
                Vector vtr = (Vector) branchInfo.get(0);
                fillParams.put("pBnkName", vtr.get(0).toString().toUpperCase());
                fillParams.put("pBnkAdd", vtr.get(1).toString().toUpperCase());
                fillParams.put("pBrPhone", vtr.get(3).toString().toUpperCase());
                fillParams.put("pBnkGstn", vtr.get(5).toString().toUpperCase());
                fillParams.put("pState", shareRemote.refDesc("002", vtr.get(6).toString()));
                if (option.equalsIgnoreCase("Individual Account")) {
                    List custInfoList = othMasterRemote.getCustomerInfoByCustNo(acNo);
                    Vector vtr2 = (Vector) custInfoList.get(0);

                    fillParams.put("pAcno", acNo);
                    fillParams.put("pCustName", vtr2.get(0).toString());
                    fillParams.put("pAdd1", vtr2.get(1).toString());
                    fillParams.put("pAdd2", vtr2.get(2).toString());
                    fillParams.put("pDist", shareRemote.refDesc("001", vtr2.get(4).toString()));
                    fillParams.put("pMob", vtr2.get(5).toString());
                    fillParams.put("pPin", vtr2.get(3).toString());
                } else if (option.equalsIgnoreCase("Individual Gst")) {
                    List listvender = othermgmtRemote.getVenderDetail(this.gstNo);
                    Vector vec = (Vector) listvender.get(0);

                    fillParams.put("pAcno", "");
                    fillParams.put("pCustName", vec.get(1).toString());
                    fillParams.put("pAdd1", vec.get(2).toString());
                    fillParams.put("pAdd2", "");
                    fillParams.put("pDist", "");
                    fillParams.put("pMob", "");
                    fillParams.put("pPin", "");
                }
                fillParams.put("pAlphaCode", commonBeanRemote.getAlphacodeByBrncode(branch.equalsIgnoreCase("0A") ? "90" : branch));
                new ReportBean().openPdf("GST Letter", "Gst_Letter", new JRBeanCollectionDataSource(repList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", "GST Letter");
            } catch (Exception e) {
                this.setMessage(e.getMessage());
            }
        }
    }

    public void downloadExcel() {
        setMessage("");
        String isValidate = validateForm();
        if (isValidate.equalsIgnoreCase("true")) {
            try {

                this.toDate = ymdFormat.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.months), Integer.parseInt(year)));
                this.fromDate = toDate.substring(0, 6) + "01";

                repList = othMasterRemote.getGstrData(report, option, this.fromDate, toDate, branch, acNo, gstNo);
                if (repList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }

                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByInvoiceNo());
                Collections.sort(repList, chObj);

                FastReportBuilder gstrFormat = genrateOutwardSupplyReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(repList), gstrFormat, report + "_" + fromDate + "_" + toDate);

            } catch (Exception e) {
                this.setMessage(e.getMessage());
            }
        }
    }

    public FastReportBuilder genrateOutwardSupplyReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            //AbstractColumn acno = ReportBean.getColumn("acno", String.class, "Account No.", 120);
            AbstractColumn nameofreceipient = ReportBean.getColumn("nameofreceipient", String.class, "Name of the receipient*(Mandatory in case of unregistered person)", 200);
            AbstractColumn gstinUin = ReportBean.getColumn("gstinUin", String.class, "GSTIN UIN Mandatory in case of Registered person", 120);
            AbstractColumn stateName = ReportBean.getColumn("stateName", String.class, "State Name*", 100);
            AbstractColumn pos = ReportBean.getColumn("pos", String.class, "POS only if different from the location of recipient", 120);
            //Invoice Details
            AbstractColumn no = ReportBean.getColumn("no", String.class, "Invoice No.*", 120);
            AbstractColumn date = ReportBean.getColumn("date", String.class, "Invoice Date*", 70);
            AbstractColumn invoiceValue = ReportBean.getColumn("invoiceValue", BigDecimal.class, "Invoice Value", 70);
            AbstractColumn hsnSac = ReportBean.getColumn("hsnSac", String.class, "Invoice HSN/SAC*", 70);
            AbstractColumn goodsServicedescription = ReportBean.getColumn("goodsServicedescription", String.class, "Invoice - Goods/Service description", 200);
            AbstractColumn taxableValue = ReportBean.getColumn("taxableValue", BigDecimal.class, "Invoice-Taxable value*", 70);
            //End of Invoice Details
            // Quantity
            AbstractColumn qty = ReportBean.getColumn("qty", String.class, "Quantity-Qty", 60);
            AbstractColumn unit = ReportBean.getColumn("unit", String.class, "Quantity-Unit", 60);
            //End of Quantitys
            AbstractColumn igstRate = ReportBean.getColumn("igstRate", Double.class, "IGST Rate*", 60);
            AbstractColumn igstAmt = ReportBean.getColumn("igstAmt", Double.class, "IGST Amt*", 60);
            AbstractColumn cgstRate = ReportBean.getColumn("cgstRate", Double.class, "CGST Rate*", 60);
            AbstractColumn cgstAmt = ReportBean.getColumn("cgstAmt", Double.class, "CGST Amt*", 60);
            AbstractColumn sgstRate = ReportBean.getColumn("sgstRate", Double.class, "SGST Rate*", 60);
            AbstractColumn sgstAmt = ReportBean.getColumn("sgstAmt", Double.class, "SGST Amt*", 60);
            AbstractColumn cessRate = ReportBean.getColumn("cessRate", Double.class, "CESS Rate*", 60);
            AbstractColumn cessAmt = ReportBean.getColumn("cessAmt", Double.class, "CESS Amt*", 60);
            AbstractColumn supplyAttractsReverseCharge = ReportBean.getColumn("supplyAttractsReverseCharge", String.class, "Indicate if supply attracts reverse charge", 180);
            AbstractColumn taxonassessment = ReportBean.getColumn("taxonassessment", String.class, "Tax on this Invoice is paid under provisional assessment", 180);
            AbstractColumn assessmentOrderDetailsNo = ReportBean.getColumn("assessmentOrderDetailsNo", String.class, "Provisional Assessment Order Details No.", 180);
            AbstractColumn assessmentOrderDetailsDt = ReportBean.getColumn("assessmentOrderDetailsDt", String.class, "Provisional Assessment Order Details Date", 180);
            AbstractColumn nameofecommerceoperator = ReportBean.getColumn("nameofecommerceoperator", String.class, "Name of ecommerce operator", 180);
            AbstractColumn gSTINofecommerceOperator = ReportBean.getColumn("gSTINofecommerceOperator", String.class, "GSTIN of ecommerce operator", 180);
            //Shipping Bill  
            AbstractColumn exportType = ReportBean.getColumn("exportType", String.class, "Shipping Bill(Export Type)", 180);
            AbstractColumn shNo = ReportBean.getColumn("shNo", String.class, "Shipping Bill(No.)", 180);
            AbstractColumn shDate = ReportBean.getColumn("shDate", String.class, "Shipping Bill(Date)", 180);
            AbstractColumn shPortCode = ReportBean.getColumn("shPortCode", String.class, "Shipping Bill(Port Code )", 180);
            //End of Shipping Bill  
            AbstractColumn receipientCategory = ReportBean.getColumn("receipientCategory", String.class, "Select Receipient Category if different from Regular", 180);
            AbstractColumn itemType = ReportBean.getColumn("itemType", String.class, "Item Type", 180);

//            fastReport.addColumn(acno);
//            width = width + acno.getWidth();

            fastReport.addColumn(nameofreceipient);
            width = width + nameofreceipient.getWidth();



            fastReport.addColumn(gstinUin);
            width = width + gstinUin.getWidth();

            fastReport.addColumn(stateName);
            width = width + stateName.getWidth();

            fastReport.addColumn(pos);
            width = width + pos.getWidth();

            fastReport.addColumn(no);
            width = width + no.getWidth();

            fastReport.addColumn(date);
            width = width + date.getWidth();

            fastReport.addColumn(invoiceValue);
            invoiceValue.setStyle(style);
            width = width + invoiceValue.getWidth();

            fastReport.addColumn(hsnSac);
            width = width + hsnSac.getWidth();

            fastReport.addColumn(goodsServicedescription);
            width = width + goodsServicedescription.getWidth();

            fastReport.addColumn(taxableValue);
            taxableValue.setStyle(style);
            width = width + taxableValue.getWidth();

            fastReport.addColumn(qty);
            width = width + qty.getWidth();

            fastReport.addColumn(unit);
            width = width + unit.getWidth();

            fastReport.addColumn(igstRate);
            igstRate.setStyle(style);
            width = width + igstRate.getWidth();

            fastReport.addColumn(igstAmt);
            igstAmt.setStyle(style);
            width = width + igstAmt.getWidth();

            fastReport.addColumn(cgstRate);
            cgstRate.setStyle(style);
            width = width + cgstRate.getWidth();

            fastReport.addColumn(cgstAmt);
            cgstAmt.setStyle(style);
            width = width + cgstAmt.getWidth();


            fastReport.addColumn(sgstRate);
            sgstRate.setStyle(style);
            width = width + sgstRate.getWidth();

            fastReport.addColumn(sgstAmt);
            sgstAmt.setStyle(style);
            width = width + sgstAmt.getWidth();

            fastReport.addColumn(cessRate);
            cessRate.setStyle(style);
            width = width + cessRate.getWidth();

            fastReport.addColumn(cessAmt);
            cessAmt.setStyle(style);
            width = width + cessAmt.getWidth();

            fastReport.addColumn(supplyAttractsReverseCharge);
            width = width + supplyAttractsReverseCharge.getWidth();

            fastReport.addColumn(taxonassessment);
            width = width + taxonassessment.getWidth();

            fastReport.addColumn(assessmentOrderDetailsNo);
            width = width + assessmentOrderDetailsNo.getWidth();

            fastReport.addColumn(assessmentOrderDetailsDt);
            width = width + assessmentOrderDetailsDt.getWidth();

            fastReport.addColumn(nameofecommerceoperator);
            width = width + nameofecommerceoperator.getWidth();

            fastReport.addColumn(gSTINofecommerceOperator);
            width = width + gSTINofecommerceOperator.getWidth();

            fastReport.addColumn(exportType);
            width = width + exportType.getWidth();

            fastReport.addColumn(shNo);
            width = width + shNo.getWidth();

            fastReport.addColumn(shDate);
            width = width + shDate.getWidth();

            fastReport.addColumn(shPortCode);
            width = width + shPortCode.getWidth();

            fastReport.addColumn(receipientCategory);
            width = width + receipientCategory.getWidth();

            fastReport.addColumn(itemType);
            width = width + itemType.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Outward Supply");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public void refreshForm() {
        try {
            this.setReport("0");
            this.setOption("0");
            this.setMonths("0");
            this.setYear("");
            this.setAcNo("");
            this.setMessage("");
            setDisableBntGen(true);
            this.setGstPanelShow("none");
            this.setGstNo("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrom() {
        try {
            refreshForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public List<SelectItem> getReportList() {
        return reportList;
    }

    public void setReportList(List<SelectItem> reportList) {
        this.reportList = reportList;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<SelectItem> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SelectItem> optionList) {
        this.optionList = optionList;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public List<SelectItem> getMonthsList() {
        return monthsList;
    }

    public void setMonthsList(List<SelectItem> monthsList) {
        this.monthsList = monthsList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public boolean isDisableAcno() {
        return disableAcno;
    }

    public void setDisableAcno(boolean disableAcno) {
        this.disableAcno = disableAcno;
    }

    public boolean isDisablebntPdf() {
        return disablebntPdf;
    }

    public void setDisablebntPdf(boolean disablebntPdf) {
        this.disablebntPdf = disablebntPdf;
    }

    public boolean isDisablebntLetter() {
        return disablebntLetter;
    }

    public void setDisablebntLetter(boolean disablebntLetter) {
        this.disablebntLetter = disablebntLetter;
    }

    public boolean isDisableBntGen() {
        return disableBntGen;
    }

    public void setDisableBntGen(boolean disableBntGen) {
        this.disableBntGen = disableBntGen;
    }

    public String getGstPanelShow() {
        return gstPanelShow;
    }

    public void setGstPanelShow(String gstPanelShow) {
        this.gstPanelShow = gstPanelShow;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }
}
