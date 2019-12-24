package com.cbs.web.controller.report.misc;

import java.util.regex.Pattern;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.constants.Page;
import com.cbs.dto.report.DICGCDetailReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public final class DICGCReport extends BaseBean {

    private String branch;
    private List<SelectItem> branchList;
    private String message, acType, instCode, reportType, accBalType, deafAcType, accBalTypeDisplay, hyperLinkDisplay13, hyperLinkDisplay36, hyperLinkDisplay6last;
    private List<SelectItem> acTypeList, reportTypeList, accBalTypeList, deafAcTypeList;
    private String calDate;
    private String dispalyExcelDownload;
    private String dispalyPDFDownload;
    OtherReportFacadeRemote beanRemote;
    List<DICGCDetailReportPojo> dICGCReportExl;
    private CommonReportMethodsRemote tdFacade;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public DICGCReport() {
        try {
            this.setCalDate(getTodayDate());
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            tdFacade = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List acLst = new ArrayList();
            acLst = beanRemote.getDicgcAcTypeList();
            acTypeList = new ArrayList<SelectItem>();
            if (!acLst.isEmpty()) {
                for (int i = 0; i < acLst.size(); i++) {
                    Vector ele7 = (Vector) acLst.get(i);
                    acTypeList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(0).toString()));
                }
            }

            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("0", "--Select--"));
            reportTypeList.add(new SelectItem("Detail", "Detail"));
            reportTypeList.add(new SelectItem("DetailExcel", "Detail Excel"));
            reportTypeList.add(new SelectItem("Summary", "Summary"));

            List brnLst = new ArrayList();
            brnLst = tdFacade.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }


            accBalTypeList = new ArrayList<SelectItem>();
            accBalTypeList.add(new SelectItem("0", "--Select--"));
            accBalTypeList.add(new SelectItem("ALL", "ALL"));
            accBalTypeList.add(new SelectItem("WOTZERO", "WITHOUT ZERO BALANCE"));

            deafAcTypeList = new ArrayList<SelectItem>();
            deafAcTypeList.add(new SelectItem("0", "--Select--"));
            deafAcTypeList.add(new SelectItem("WTDEAF", "WITH DEAF A/C"));
            deafAcTypeList.add(new SelectItem("WOTDEAF", "WITHOUT DEAF A/C"));



            accBalType = "0";
            accBalTypeDisplay = "none";
            dispalyExcelDownload = "none";
            hyperLinkDisplay13 = "none";
            hyperLinkDisplay36 = "none";
            hyperLinkDisplay6last = "none";
            dispalyPDFDownload = "";
            dICGCReportExl = new ArrayList();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        String bankName = "";
        String branchAddress = "";
        if (acType.equalsIgnoreCase("0")) {
            this.setMessage("Please select Account Type");
            return null;
        }
        if (instCode == null || instCode.equalsIgnoreCase("")) {
            this.setMessage("Please select Institution Code No");
            return null;
        }
        if (calDate == null || calDate.equalsIgnoreCase("")) {
            this.setMessage("Please select As On Date");
            return null;
        }
        if (!Validator.validateDate(calDate)) {
            this.setMessage("Please select proper As On Date");
            return null;
        }
        if (reportType.equalsIgnoreCase("0")) {
            this.setMessage("Please select Report Type");
            return null;
        }

        String asOnDt = calDate.substring(6) + calDate.substring(3, 5) + calDate.substring(0, 2);
        try {
            List<DICGCDetailReportPojo> dICGCReport = beanRemote.getDICGCReport(asOnDt, acType, instCode, this.getBranch(), reportType, accBalType, deafAcType);
            if (!dICGCReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String repName = "DICGC Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", calDate);
                fillParams.put("pReportType", acType + "/" + reportType);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                if (reportType.equalsIgnoreCase("Detail")) {
                    new ReportBean().jasperReport("Cbs_Rep_Dicgc_Detail", "text/html", new JRBeanCollectionDataSource(dICGCReport), fillParams, repName);
                } else if (reportType.equalsIgnoreCase("Summary")) {
                    fillParams.put("pInstCode", instCode);
                    new ReportBean().jasperReport("Cbs_Rep_Dicgc_Summary", "text/html", new JRBeanCollectionDataSource(dICGCReport), fillParams, repName);
                }
            } else {
                setMessage("No data found");
                return null;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "report";
    }

    public void viewPdfReport() {

        String bankName = "";
        String branchAddress = "";
        if (acType.equalsIgnoreCase("0")) {
            this.setMessage("Please select Account Type");
            return;
        }
        if (instCode == null || instCode.equalsIgnoreCase("")) {
            this.setMessage("Please select Institution Code No");
            return;
        }
        if (calDate == null || calDate.equalsIgnoreCase("")) {
            this.setMessage("Please select As On Date");
            return;
        }
        if (!Validator.validateDate(calDate)) {
            this.setMessage("Please select proper As On Date");
            return;
        }
        if (reportType.equalsIgnoreCase("0")) {
            this.setMessage("Please select Report Type");
            return;
        }

        String asOnDt = calDate.substring(6) + calDate.substring(3, 5) + calDate.substring(0, 2);
        try {
            List<DICGCDetailReportPojo> dICGCReport = beanRemote.getDICGCReport(asOnDt, acType, instCode, this.getBranch(), reportType, accBalType, deafAcType);
            if (!dICGCReport.isEmpty()) {
                CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List ele = common.getBranchNameandAddress(getOrgnBrCode());
                if (ele.get(0) != null) {
                    bankName = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    branchAddress = ele.get(1).toString();
                }
                String report = "DICGC Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", report);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", calDate);
                fillParams.put("pReportType", acType + "/" + reportType);
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", branchAddress);
                if (reportType.equalsIgnoreCase("Detail")) {
                    new ReportBean().openPdf("DICGC Report_" + ymd.format(sdf.parse(getTodayDate())), "Cbs_Rep_Dicgc_Detail", new JRBeanCollectionDataSource(dICGCReport), fillParams);
                } else if (reportType.equalsIgnoreCase("Summary")) {
                    fillParams.put("pInstCode", instCode);
                    new ReportBean().openPdf("DICGC Report_" + ymd.format(sdf.parse(getTodayDate())), "Cbs_Rep_Dicgc_Summary", new JRBeanCollectionDataSource(dICGCReport), fillParams);
                }
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                setMessage("No data found");
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onBlurReportType() {
        if (reportType.equalsIgnoreCase("DetailExcel")) {
            accBalTypeDisplay = "";
            dispalyExcelDownload = "";
            dispalyPDFDownload = "none";
        } else {
            accBalTypeDisplay = "none";
            dispalyExcelDownload = "none";
            dispalyPDFDownload = "";
        }
    }

    public void splitReport(String str) {
        try {
            UIViewRoot view = getFacesContext().getCurrentInstance().getViewRoot();

            if (str.equalsIgnoreCase("FIRST")) {
                FastReportBuilder fastReprotTempl = genrateDicgcReport();
                if (dICGCReportExl.size() < 30000) {
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(dICGCReportExl), fastReprotTempl, instCode + "_Data_On_Depositors_Report_" + ymd.format(new Date()));
                } else {
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(dICGCReportExl.subList(0, 30000)), fastReprotTempl, instCode + "_Data_On_Depositors_Report_" + ymd.format(new Date()) + "_1_30000");
                }
            }
            if (str.equalsIgnoreCase("SECOND")) {
                FastReportBuilder fastReprotTempl = genrateDicgcReport();
                if (dICGCReportExl.size() < 60000) {
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(dICGCReportExl.subList(30000, (dICGCReportExl.size() - 1))), fastReprotTempl, instCode + "_Data_On_Depositors_Report_" + ymd.format(new Date()) + "_30000_60000");
                } else {
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(dICGCReportExl.subList(30000, 60000)), fastReprotTempl, instCode + "_Data_On_Depositors_Report_" + ymd.format(new Date()) + "_30000_60000");
                }
            }
            if (str.equalsIgnoreCase("THIRD")) {
                FastReportBuilder fastReprotTempl = genrateDicgcReport();
                if (dICGCReportExl.size() < 90000) {
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(dICGCReportExl.subList(60000, (dICGCReportExl.size() - 1))), fastReprotTempl, instCode + "_Data_On_Depositors_Report_" + ymd.format(new Date()) + "_60000_90000");
                } else {
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(dICGCReportExl.subList(60000, 90000)), fastReprotTempl, instCode + "_Data_On_Depositors_Report_" + ymd.format(new Date()) + "_60000_90000");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public FastReportBuilder genrateDicgcReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

//            AbstractColumn slNo = ReportBean.getColumn("slNo", Integer.class, "Serial number", 100);
            AbstractColumn slNo = ReportBean.getColumn("claimNo", String.class, "Claim number", 100);
            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account holder number", 100);
            AbstractColumn uidAadharNo = ReportBean.getColumn("customerId", String.class, "Unique identification number", 200);
            AbstractColumn salutation = ReportBean.getColumn("salutation", String.class, "Salutation", 200);
            AbstractColumn firstName = ReportBean.getColumn("firstName", String.class, "First name", 200);
            AbstractColumn middleName = ReportBean.getColumn("middleName", String.class, "Middle name", 200);
            AbstractColumn lastName = ReportBean.getColumn("lastName", String.class, "Last name", 200);
            AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Date of birth", 200);
            AbstractColumn nationality = ReportBean.getColumn("nationality", String.class, "Nationality", 200);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Name of Customer", 200);
            AbstractColumn accno = ReportBean.getColumn("acNo", String.class, "A/c No.", 200);
            AbstractColumn dateOfAccopen = ReportBean.getColumn("dateOfAccopen", String.class, "Date of opening of A/c", 200);
            AbstractColumn accountType = ReportBean.getColumn("accountType", String.class, "Type of A/c", 200);
            AbstractColumn aadharNo1 = ReportBean.getColumn("uidAadharNo", String.class, "Aadhaar Number", 200);
            AbstractColumn passportNo = ReportBean.getColumn("passportNo", String.class, "Passport Number", 200);
            AbstractColumn panNo = ReportBean.getColumn("panNo", String.class, "PAN Number", 200);
            AbstractColumn voterIdNo = ReportBean.getColumn("voterIdNo", String.class, "Voter ID Number-1", 200);
            AbstractColumn drivingLicenNo = ReportBean.getColumn("drivingLicenNo", String.class, "Driving License Number", 200);
            AbstractColumn mobileNo = ReportBean.getColumn("mobileNo", String.class, "Mobile Number", 200);
            AbstractColumn emailId = ReportBean.getColumn("emailId", String.class, "Email ID", 200);
            AbstractColumn whetherKYCComplied = ReportBean.getColumn("whetherKYCComplied", String.class, "Whether KYC complied", 200);
            AbstractColumn houseNumber = ReportBean.getColumn("houseNumber", String.class, "Building apt house number", 200);
            AbstractColumn add1 = ReportBean.getColumn("add1", String.class, "Address1", 200);
            AbstractColumn add2 = ReportBean.getColumn("add2", String.class, "Address2", 200);
            AbstractColumn add3 = ReportBean.getColumn("add3", String.class, "Address3", 200);
            AbstractColumn cityVillage = ReportBean.getColumn("cityVillage", String.class, "City Village", 200);
            AbstractColumn pincode = ReportBean.getColumn("pincode", String.class, "Pincode", 200);
            AbstractColumn state = ReportBean.getColumn("state", String.class, "State", 200);
            AbstractColumn addProffVerified = ReportBean.getColumn("addProffVerified", String.class, "Whether Support document verified for address proof?", 200);
            AbstractColumn bankHasSupportingDocForAddrProof = ReportBean.getColumn("bankHasSupportingDocForAddrProof", String.class, "Bank has Supporting document for address proof", 200);
            AbstractColumn alternateAccNo = ReportBean.getColumn("alternateAccNo", String.class, "Alternate Bank a/c if any", 200);

            fastReport.addColumn(slNo);
            width = width + slNo.getWidth();

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(uidAadharNo);
            width = width + uidAadharNo.getWidth();

            fastReport.addColumn(salutation);
            width = width + salutation.getWidth();

            fastReport.addColumn(firstName);
            width = width + firstName.getWidth();

            fastReport.addColumn(middleName);
            width = width + middleName.getWidth();

            fastReport.addColumn(lastName);
            width = width + lastName.getWidth();

            fastReport.addColumn(dob);
            width = width + dob.getWidth();

            fastReport.addColumn(nationality);
            width = width + nationality.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(accno);
            width = width + accno.getWidth();

            fastReport.addColumn(dateOfAccopen);
            width = width + dateOfAccopen.getWidth();

            fastReport.addColumn(accountType);
            width = width + accountType.getWidth();

            fastReport.addColumn(aadharNo1);
            width = width + aadharNo1.getWidth();

            fastReport.addColumn(passportNo);
            width = width + passportNo.getWidth();

            fastReport.addColumn(panNo);
            width = width + panNo.getWidth();


            fastReport.addColumn(voterIdNo);
            width = width + voterIdNo.getWidth();


            fastReport.addColumn(drivingLicenNo);
            width = width + drivingLicenNo.getWidth();

            fastReport.addColumn(mobileNo);
            width = width + mobileNo.getWidth();

            fastReport.addColumn(emailId);
            width = width + emailId.getWidth();

            fastReport.addColumn(whetherKYCComplied);
            width = width + whetherKYCComplied.getWidth();

            fastReport.addColumn(houseNumber);
            width = width + houseNumber.getWidth();

            fastReport.addColumn(add1);
            width = width + add1.getWidth();

            fastReport.addColumn(add2);
            width = width + add2.getWidth();

            fastReport.addColumn(add3);
            width = width + add3.getWidth();

            fastReport.addColumn(cityVillage);
            width = width + cityVillage.getWidth();

            fastReport.addColumn(pincode);
            width = width + pincode.getWidth();

            fastReport.addColumn(state);
            width = width + state.getWidth();

            fastReport.addColumn(addProffVerified);
            width = width + addProffVerified.getWidth();

            fastReport.addColumn(bankHasSupportingDocForAddrProof);
            width = width + bankHasSupportingDocForAddrProof.getWidth();

            fastReport.addColumn(alternateAccNo);
            width = width + alternateAccNo.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Data On Depositors Report");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public void excelPrint() {
        hyperLinkDisplay13 = "none";
        hyperLinkDisplay36 = "none";
        hyperLinkDisplay6last = "none";

        if (acType.equalsIgnoreCase("0")) {
            this.setMessage("Please select Account Type");
            return;
        }
        if (instCode == null || instCode.equalsIgnoreCase("")) {
            this.setMessage("Please select Institution Code No");
            return;
        }
        if (calDate == null || calDate.equalsIgnoreCase("")) {
            this.setMessage("Please select As On Date");
            return;
        }
        if (!Validator.validateDate(calDate)) {
            this.setMessage("Please select proper As On Date");
            return;
        }
        if (reportType.equalsIgnoreCase("0")) {
            this.setMessage("Please select Report Type");
            return;
        }
        if (accBalType.equalsIgnoreCase("0")) {
            this.setMessage("Please select A/C Balance Type");
            return;
        }
        if (deafAcType.equalsIgnoreCase("0")) {
            this.setMessage("Please select Deaf A/C Consider");
            return;
        }


        FastReportBuilder fastReport = new FastReportBuilder();
        String fromDate = "";
        String toDate = "";
        try {
            System.out.println("Statrt Time---->" + new Date());
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            String asOnDt = calDate.substring(6) + calDate.substring(3, 5) + calDate.substring(0, 2);
            dICGCReportExl = beanRemote.getDICGCReport(asOnDt, acType, instCode, this.getBranch(), reportType, accBalType, deafAcType);

            if (dICGCReportExl.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }

            if (dICGCReportExl.size() <= 30000) {
                hyperLinkDisplay13 = "";
                hyperLinkDisplay36 = "none";
                hyperLinkDisplay6last = "none";
            }
            if (dICGCReportExl.size() > 30000 && dICGCReportExl.size() < 60000) {
                hyperLinkDisplay13 = "";
                hyperLinkDisplay36 = "";
                hyperLinkDisplay6last = "none";
            }
            if (dICGCReportExl.size() > 60000) {
                hyperLinkDisplay13 = "";
                hyperLinkDisplay36 = "";
                hyperLinkDisplay6last = "";
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        System.out.println("stop Time---->" + new Date());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getDispalyExcelDownload() {
        return dispalyExcelDownload;
    }

    public void setDispalyExcelDownload(String dispalyExcelDownload) {
        this.dispalyExcelDownload = dispalyExcelDownload;
    }

    public String getDispalyPDFDownload() {
        return dispalyPDFDownload;
    }

    public void setDispalyPDFDownload(String dispalyPDFDownload) {
        this.dispalyPDFDownload = dispalyPDFDownload;
    }

    public String getAccBalType() {
        return accBalType;
    }

    public void setAccBalType(String accBalType) {
        this.accBalType = accBalType;
    }

    public List<SelectItem> getAccBalTypeList() {
        return accBalTypeList;
    }

    public void setAccBalTypeList(List<SelectItem> accBalTypeList) {
        this.accBalTypeList = accBalTypeList;
    }

    public String getAccBalTypeDisplay() {
        return accBalTypeDisplay;
    }

    public void setAccBalTypeDisplay(String accBalTypeDisplay) {
        this.accBalTypeDisplay = accBalTypeDisplay;
    }

    public String getDeafAcType() {
        return deafAcType;
    }

    public void setDeafAcType(String deafAcType) {
        this.deafAcType = deafAcType;
    }

    public List<SelectItem> getDeafAcTypeList() {
        return deafAcTypeList;
    }

    public void setDeafAcTypeList(List<SelectItem> deafAcTypeList) {
        this.deafAcTypeList = deafAcTypeList;
    }

    public String getHyperLinkDisplay13() {
        return hyperLinkDisplay13;
    }

    public void setHyperLinkDisplay13(String hyperLinkDisplay13) {
        this.hyperLinkDisplay13 = hyperLinkDisplay13;
    }

    public String getHyperLinkDisplay36() {
        return hyperLinkDisplay36;
    }

    public void setHyperLinkDisplay36(String hyperLinkDisplay36) {
        this.hyperLinkDisplay36 = hyperLinkDisplay36;
    }

    public String getHyperLinkDisplay6last() {
        return hyperLinkDisplay6last;
    }

    public void setHyperLinkDisplay6last(String hyperLinkDisplay6last) {
        this.hyperLinkDisplay6last = hyperLinkDisplay6last;
    }

    public void resetAction() {
        this.setMessage("");
        this.setAcType("--Select--");
        this.setInstCode("");
        this.setCalDate(getTodayDate());
        this.setReportType("--Select--");
        this.setAccBalType("0");
        this.setDeafAcType("0");
        dICGCReportExl = new ArrayList();
        accBalTypeDisplay = "none";
        dispalyExcelDownload = "none";
        dispalyPDFDownload = "none";
        hyperLinkDisplay13 = "none";
        hyperLinkDisplay36 = "none";
        hyperLinkDisplay6last = "none";
    }

    public String exitAction() {
        resetAction();
        return "case1";
    }
}
