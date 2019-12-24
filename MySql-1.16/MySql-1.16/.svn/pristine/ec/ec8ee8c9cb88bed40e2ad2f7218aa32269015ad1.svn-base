/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.AtmMasterGrid;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.ho.AnnualNPAStmtConsolidate;
import com.cbs.dto.report.ho.BankProfilePojo;
import com.cbs.dto.report.ho.OrganisationalPojo;
import com.cbs.dto.report.ho.Oss8BusinessPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.report.RbiQuarterlyReportFacadeRemote;
import com.cbs.pojo.ManagementDetailsPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.ho.Oss8JrxmlPojo;
import com.cbs.web.utils.Util;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class Oss8Controller extends BaseBean {

    private String message;
    private String reportType;
    private String reportDt;
    private String reportIn;
    private String reportOption;
    private int noOfBankOfficier;
    private int noOfOtherBankStaff;
    private double dividend;
    private List<SelectItem> reportTypeList;
    private List<SelectItem> reportInList;
    private List<SelectItem> reportOptionList;
    private RbiQuarterlyReportFacadeRemote quarterlyRemote;
    private CommonReportMethodsRemote common;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private HoReportFacadeRemote hoReportFacade;
    
    public Oss8Controller() {
        this.setMessage("");
        try {
            quarterlyRemote = (RbiQuarterlyReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiQuarterlyReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            hoReportFacade = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup("HoReportFacade");
            
            onLoadData();
            this.setReportDt(getTodayDate());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onLoadData() {
        reportTypeList = new ArrayList<SelectItem>();
        reportInList = new ArrayList<SelectItem>();
        reportOptionList = new ArrayList<SelectItem>();
        try {
            reportTypeList.add(new SelectItem("", "--Select--"));
            reportTypeList.add(new SelectItem("oss8", "Statement On Bank Profile(OSS)"));
            reportTypeList.add(new SelectItem("XBRLOSS8", "Statement On Bank Profile(XBRL) Part I"));
            reportTypeList.add(new SelectItem("XBRLOSS8partB", "Statement On Bank Profile(XBRL) Part II"));

            reportInList.add(new SelectItem("R", "Rs."));
            reportInList.add(new SelectItem("T", "Thousand"));
            reportInList.add(new SelectItem("L", "Lacs"));
            reportInList.add(new SelectItem("C", "Crore"));

            reportOptionList.add(new SelectItem("N", "In Detail"));
            reportOptionList.add(new SelectItem("Y", "In Summary"));
            
            setNoOfBankOfficier(common.getCodeByReportName("BANK-OFFICER-NO"));
            setNoOfOtherBankStaff(common.getCodeByReportName("BANK-OTHER-STAFF-NO"));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void dtProcessAction() {
        this.setMessage("");
        try {
            if (this.reportType == null || this.reportType.equals("")) {
                this.setMessage("Please select Report Type.");
                return;
            }
            if (this.reportDt == null || this.reportDt.equals("")) {
                this.setMessage("Please fill Report Date.");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.reportDt)) {
                this.setMessage("Please fill correct Report Date.");
                return;
            }
            if (dmy.parse(reportDt).compareTo(dmy.parse(getTodayDate())) > 0) {
                this.setMessage("Report date can not greater than today date.");
                return;
            }
            if (this.getNoOfBankOfficier() <= 0) {
                this.setMessage("Please fill the No of Bank Officier.");
                return;
            }
            if (this.getNoOfOtherBankStaff() <= 0) {
                this.setMessage("Please fill the No of Other Bank Staff.");
                return;
            }
            if (this.getDividend() <= 0) {
                this.setMessage("Please fill the Dividend in %.");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void downloadPdf() {
        this.setMessage("");
        try {
            String reportName = "Statement On Bank Profile";
            Map fillParams = new HashMap();

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("username", getUserName());
            fillParams.put("report", reportName);
            fillParams.put("pPrintedDate", this.reportDt);
            String[] arr = Util.getReportOptionAndDescription(this.reportIn);
            fillParams.put("pAmtIn", arr[1]);

            fillParams.put("bankOfficerNo", String.valueOf(getNoOfBankOfficier()));
            fillParams.put("bankOtherStaffNo", String.valueOf(getNoOfOtherBankStaff()));
            fillParams.put("bankTotalStaff", String.valueOf((getNoOfBankOfficier() + getNoOfOtherBankStaff())));
            
            if(reportType.equalsIgnoreCase("oss8")) {
                List<Oss8BusinessPojo> resultList = quarterlyRemote.getOss8Details(reportType, ymd.format(dmy.parse(this.reportDt)),
                        new BigDecimal(arr[0]), getOrgnBrCode(), getNoOfBankOfficier() + getNoOfOtherBankStaff(), dividend, getReportOption());
                if (resultList.isEmpty()) {
                    this.setMessage("There is no data to print the report.");
                    return;
                }
                List<Oss8JrxmlPojo> jrxmlList = new ArrayList<Oss8JrxmlPojo>();     //Actual list going to print.
                for (Oss8BusinessPojo businessPojo : resultList) {
                    Oss8JrxmlPojo jrxmlPojo = new Oss8JrxmlPojo();

                    List<AtmMasterGrid> atmProfileData = businessPojo.getAtmProfile();
                    List<BankProfilePojo> bankProfileData = businessPojo.getBankProfile();

                    List<OrganisationalPojo> organisationalProfileData = businessPojo.getOrganisationalProfile();
                    List<ManagementDetailsPojo> managementData = businessPojo.getManagementProfile();

                    List<RbiSossPojo> finOneList = businessPojo.getFinOneProfile();
                    List<RbiSossPojo> finTwoList = businessPojo.getFinTwoProfile();

                    jrxmlPojo.setAtmProfile(new JRBeanCollectionDataSource(atmProfileData));
                    jrxmlPojo.setBankProfile(new JRBeanCollectionDataSource(bankProfileData));

                    jrxmlPojo.setOrganisationalProfile(new JRBeanCollectionDataSource(organisationalProfileData));
                    jrxmlPojo.setManagementProfile(new JRBeanCollectionDataSource(managementData));

                    jrxmlPojo.setFinOneProfile(new JRBeanCollectionDataSource(finOneList));
                    jrxmlPojo.setFinTwoProfile(new JRBeanCollectionDataSource(finTwoList));
                    jrxmlList.add(jrxmlPojo);
                }
                /*Report Checking*/
                for (int i = 0; i < jrxmlList.size(); i++) {
                    Oss8JrxmlPojo obj = jrxmlList.get(i);
                    List<AtmMasterGrid> atm = (List<AtmMasterGrid>) obj.getAtmProfile().getData();
                    for (int k = 0; k < atm.size(); k++) {
                        AtmMasterGrid grid = atm.get(k);
        //                    System.out.println("ATM::" + grid);
                    }
                    List<BankProfilePojo> bank = (List<BankProfilePojo>) obj.getBankProfile().getData();
                    for (int k = 0; k < bank.size(); k++) {
                        BankProfilePojo grid = bank.get(k);
        //                    System.out.println("BANK::" + grid);
                    }
                    List<OrganisationalPojo> organ = (List<OrganisationalPojo>) obj.getOrganisationalProfile().getData();
                    for (int k = 0; k < organ.size(); k++) {
                        OrganisationalPojo grid = organ.get(k);
        //                    System.out.println("ORGAN::" + grid);
                    }
                    List<ManagementDetailsPojo> manage = (List<ManagementDetailsPojo>) obj.getManagementProfile().getData();
                    for (int k = 0; k < manage.size(); k++) {
                        ManagementDetailsPojo grid = manage.get(k);
        //                    System.out.println("MANAGE::" + grid.getName());
                    }
                }
                /*Report Printing*/
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                new ReportBean().downloadPdf("OSS8_" + ymd.format(dmy.parse(reportDt)), "soss8Cover",
                        new JRBeanCollectionDataSource(jrxmlList), fillParams);
            } else if(reportType.equalsIgnoreCase("XBRLOSS8")) {
                System.out.println("Start Time XBRL OSS8 Part 1"+new Date());
                List<Oss8BusinessPojo> resultList = quarterlyRemote.getXBRL8Details(reportType, ymd.format(dmy.parse(this.reportDt)),
                        new BigDecimal(arr[0]), getOrgnBrCode(), getNoOfBankOfficier() + getNoOfOtherBankStaff(), dividend, getReportOption());
                System.out.println("END Time XBRL OSS8 Part 1"+new Date());
                if (resultList.isEmpty()) {
                    this.setMessage("There is no data to print the report.");
                    return;
                }
                List<Oss8JrxmlPojo> jrxmlList = new ArrayList<Oss8JrxmlPojo>();     //Actual list going to print.
                for (Oss8BusinessPojo businessPojo : resultList) {
                    Oss8JrxmlPojo jrxmlPojo = new Oss8JrxmlPojo();

                    List<AtmMasterGrid> atmProfileData = businessPojo.getAtmProfile();
                    List<BankProfilePojo> bankProfileData = businessPojo.getBankProfile();

                    List<OrganisationalPojo> organisationalProfileData = businessPojo.getOrganisationalProfile();
                    List<ManagementDetailsPojo> managementData = businessPojo.getManagementProfile();

                    List<RbiSossPojo> finOneList = businessPojo.getFinOneProfile();
                    List<RbiSossPojo> finTwoList = businessPojo.getFinTwoProfile();

                    jrxmlPojo.setAtmProfile(new JRBeanCollectionDataSource(atmProfileData));
                    jrxmlPojo.setBankProfile(new JRBeanCollectionDataSource(bankProfileData));

                    jrxmlPojo.setOrganisationalProfile(new JRBeanCollectionDataSource(organisationalProfileData));
                    jrxmlPojo.setManagementProfile(new JRBeanCollectionDataSource(managementData));

                    jrxmlPojo.setFinOneProfile(new JRBeanCollectionDataSource(finOneList));
                    jrxmlPojo.setFinTwoProfile(new JRBeanCollectionDataSource(finTwoList));
                    
//                    jrxmlPojo.setMainList(new JRBeanCollectionDataSource(businessPojo.getMainList()));
//                    jrxmlPojo.setPartBList(new JRBeanCollectionDataSource(businessPojo.getPartBList()));
                    
                    jrxmlList.add(jrxmlPojo);
                }
                /*Report Printing*/
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                new ReportBean().downloadPdf("OSS8_" + ymd.format(dmy.parse(reportDt)), "xbrl8Cover",
                        new JRBeanCollectionDataSource(jrxmlList), fillParams);
            } else if (reportType.equalsIgnoreCase("XBRLOSS8partB")) {
                System.out.println("Start Time Of XBRLOSS8 part 2"+new Date());
                List<AnnualNPAStmtConsolidate> annualNPAList = hoReportFacade.getAnnualNPAStmt(getOrgnBrCode(), ymd.format(dmy.parse(this.reportDt)), new BigDecimal(arr[0]),"OSS8_NPA");
                System.out.println("End Time Of XBRLOSS8 part 2"+new Date());
                List<Oss8JrxmlPojo> jrxmlList = new ArrayList<Oss8JrxmlPojo>();
                for (AnnualNPAStmtConsolidate businessPojo : annualNPAList) {
                    Oss8JrxmlPojo jrxmlPojo = new Oss8JrxmlPojo();
                    
                    jrxmlPojo.setMainList(new JRBeanCollectionDataSource(businessPojo.getMainList()));
                    jrxmlPojo.setPartBList(new JRBeanCollectionDataSource(businessPojo.getPartBList()));
                    
                    jrxmlList.add(jrxmlPojo);
                }
                fillParams.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");
                new ReportBean().downloadPdf("OSS8_" + ymd.format(dmy.parse(reportDt)), "xbrl8Cover",
                        new JRBeanCollectionDataSource(jrxmlList), fillParams);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setReportType("");

        this.setReportDt(getTodayDate());
        this.setReportIn("L");

        this.setNoOfBankOfficier(0);
        this.setNoOfOtherBankStaff(0);
        this.setDividend(0);
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    /*Getter And Setter*/
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportDt() {
        return reportDt;
    }

    public void setReportDt(String reportDt) {
        this.reportDt = reportDt;
    }

    public String getReportIn() {
        return reportIn;
    }

    public void setReportIn(String reportIn) {
        this.reportIn = reportIn;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public List<SelectItem> getReportInList() {
        return reportInList;
    }

    public void setReportInList(List<SelectItem> reportInList) {
        this.reportInList = reportInList;
    }

    public double getDividend() {
        return dividend;
    }

    public void setDividend(double dividend) {
        this.dividend = dividend;
    }

    public int getNoOfBankOfficier() {
        return noOfBankOfficier;
    }

    public void setNoOfBankOfficier(int noOfBankOfficier) {
        this.noOfBankOfficier = noOfBankOfficier;
    }

    public int getNoOfOtherBankStaff() {
        return noOfOtherBankStaff;
    }

    public void setNoOfOtherBankStaff(int noOfOtherBankStaff) {
        this.noOfOtherBankStaff = noOfOtherBankStaff;
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
