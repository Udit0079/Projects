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
import com.cbs.constant.CbsConstant;
import com.cbs.dto.YearEndDatePojo;
import com.cbs.dto.report.ArfAccPojo;
import com.cbs.dto.report.ArfBatPojo;
import com.cbs.dto.report.ArfBrcPojo;
import com.cbs.dto.report.ArfInpPojo;
import com.cbs.dto.report.ArfLpePojo;
import com.cbs.dto.report.ArfRptPojo;
import com.cbs.dto.report.ArfTrnPojo;
import com.cbs.dto.report.CbaAccPojo;
import com.cbs.dto.report.CbaInpPojo;
import com.cbs.dto.report.CbaLpePojo;
import com.cbs.dto.report.CbaTrnPojo;
import com.cbs.dto.report.CtrArfPojo;
import com.cbs.dto.report.CtrPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.CtrStrAirReportFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.report.SFTReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.AirPojo;
import com.cbs.pojo.Form61APojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.component.UIViewRoot;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author sipl
 */
public class Ctr extends BaseBean {

    private String message;
    private String ddAcNature;
    private String lblAcNature;
    private String branch;
    private String hyperPanel;
    private List<SelectItem> branchList;
    List<SelectItem> ddAcNatureList = new ArrayList<SelectItem>();
    private String frAmt;
    private String toAmt;
    private String calFromDate;
    private String caltoDate;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private Boolean disableAmt;
    private Boolean disableCsvbuttom;
    private String multiplier;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dmy_Format = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private OtherReportFacadeRemote facadeRemote;
    private CtrStrAirReportFacadeRemote ctrStrRemote;
    private CommonReportMethodsRemote commonBeanRemote;
    private TdReceiptManagementFacadeRemote tdFacade;
    private RbiReportFacadeRemote rbiReportFacade;
    private SFTReportFacadeRemote sftReport;
    Date dt = new Date();
    CtrPojo ctrRep = new CtrPojo();
    CtrArfPojo arfRep = new CtrArfPojo();
    String ctrParameter;
    String strAmtParameter;
    private String commandLink;
    private String invisibleMultiplier;
    private String strName;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getHyperPanel() {
        return hyperPanel;
    }

    public void setHyperPanel(String hyperPanel) {
        this.hyperPanel = hyperPanel;
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

    public String getDdAcNature() {
        return ddAcNature;
    }

    public void setDdAcNature(String ddAcNature) {
        this.ddAcNature = ddAcNature;
    }

    public List<SelectItem> getDdAcNatureList() {
        return ddAcNatureList;
    }

    public void setDdAcTypeList(List<SelectItem> ddAcNatureList) {
        this.ddAcNatureList = ddAcNatureList;
    }

    public String getLblAcNature() {
        return lblAcNature;
    }

    public void setLblAcNature(String lblAcNature) {
        this.lblAcNature = lblAcNature;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToAmt() {
        return toAmt;
    }

    public void setToAmt(String toAmt) {
        this.toAmt = toAmt;
    }

    public String getFrAmt() {
        return frAmt;
    }

    public void setFrAmt(String frAmt) {
        this.frAmt = frAmt;
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

    public Boolean getDisableAmt() {
        return disableAmt;
    }

    public void setDisableAmt(Boolean disableAmt) {
        this.disableAmt = disableAmt;
    }

    public String getCommandLink() {
        return commandLink;
    }

    public void setCommandLink(String commandLink) {
        this.commandLink = commandLink;
    }

    public String getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }

    public String getInvisibleMultiplier() {
        return invisibleMultiplier;
    }

    public void setInvisibleMultiplier(String invisibleMultiplier) {
        this.invisibleMultiplier = invisibleMultiplier;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public Boolean getDisableCsvbuttom() {
        return disableCsvbuttom;
    }

    public void setDisableCsvbuttom(Boolean disableCsvbuttom) {
        this.disableCsvbuttom = disableCsvbuttom;
    }

    public Ctr() {
        try {
            setHyperPanel("none");
            setCommandLink("none");
            setInvisibleMultiplier("none");
            facadeRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            ctrStrRemote = (CtrStrAirReportFacadeRemote) ServiceLocator.getInstance().lookup("CtrStrAirReportFacade");
            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            tdFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            rbiReportFacade = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            sftReport = (SFTReportFacadeRemote) ServiceLocator.getInstance().lookup("SFTReportFacade");
            setCalFromDate(dmyFormat.format(dt));
            setCaltoDate(dmyFormat.format(dt));
            setMessage("");
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("S", "--Select--"));
            reportTypeList.add(new SelectItem("AIR", "AIR"));
            reportTypeList.add(new SelectItem("61A", "FORM 61A (DEMONETISATION PERIOD)"));
            reportTypeList.add(new SelectItem("AIR61A", "AIR(FORM 61A)"));
            reportTypeList.add(new SelectItem("CTR", "CTR"));
            reportTypeList.add(new SelectItem("NTR", "NTR"));
            reportTypeList.add(new SelectItem("STR", "STR"));
            reportTypeList.add(new SelectItem("STR DEM", "STR (DEMONETISATION PERIOD)"));

            List brnLst = new ArrayList();
            brnLst = tdFacade.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
//            List acNatureList = commonBeanRemote.getCASBAcNatureList();
//            ddAcNatureList = new ArrayList<SelectItem>();
//            ddAcNatureList.add(new SelectItem("ALL", "ALL"));
//            for (Object element : acNatureList) {
//                Vector vector = (Vector) element;
//                ddAcNatureList.add(new SelectItem(vector.get(0).toString(), vector.get(0).toString()));
//            }
            CtrPojo ctrRep = new CtrPojo();
            ctrParameter = facadeRemote.getCodeFromCbsParameterInfo("CTR");
            // strAmtParameter = facadeRemote.getCodeFromCbsParameterInfo("STR_AMT_BASED");
            this.setMessage("Please select Report Type !");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onReportAction() {
        setMessage("");
        this.frAmt = "0";
        this.toAmt = "0";
        try {
            if (reportType.equalsIgnoreCase("STR DEM")) {
                setInvisibleMultiplier("");
                setCalFromDate("08/11/2016");
                setCaltoDate("31/12/2016");
                this.disableAmt = true;
                this.disableCsvbuttom = true;
//            if (strAmtParameter.equalsIgnoreCase("Y")) {
//                this.disableAmt = false;
//            } else {
//                this.disableAmt = true;
//            }
            } else if (reportType.equalsIgnoreCase("61A")) {
                setInvisibleMultiplier("none");
                setCalFromDate("09/11/2016");
                setCaltoDate("30/12/2016");
                this.setFrAmt("1250000");
                this.setToAmt("99999999999");
                this.disableAmt = true;
                this.disableCsvbuttom = false;
            } else {
                setInvisibleMultiplier("none");
                this.disableAmt = false;
                this.disableCsvbuttom = true;
            }

            List acNatureList = commonBeanRemote.getCASBAcNatureList();
            ddAcNatureList = new ArrayList<SelectItem>();
            if (reportType.equalsIgnoreCase("AIR")) {
                ddAcNatureList.add(new SelectItem("CA", "CA"));
                ddAcNatureList.add(new SelectItem("Other", "Other AcNature"));
            } else if (reportType.equalsIgnoreCase("61A")) {
                ddAcNatureList.add(new SelectItem("CA", "CA"));
                ddAcNatureList.add(new SelectItem("Other", "Other AcNature"));
            } else if (reportType.equalsIgnoreCase("AIR61A")) {
                ddAcNatureList.add(new SelectItem("SFT001", "SFT001 (Purchase of bank drafts or pay orders in cash)"));
                ddAcNatureList.add(new SelectItem("SFT003", "SFT003 (Cash deposit in current account)"));
                ddAcNatureList.add(new SelectItem("SFT004", "SFT004 (Cash deposit in account other than current account)"));
                ddAcNatureList.add(new SelectItem("SFT005", "SFT005 (Time deposit)"));
                this.disableCsvbuttom = false;
            } else {
                ddAcNatureList.add(new SelectItem("ALL", "ALL"));
                for (Object element : acNatureList) {
                    Vector vector = (Vector) element;
                    ddAcNatureList.add(new SelectItem(vector.get(0).toString(), vector.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onNatureAction() {
        setMessage("");
        if (reportType.equalsIgnoreCase("61A") && ddAcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
            this.setFrAmt("1250000");
            this.setToAmt("99999999999");
            this.disableAmt = true;
        } else if (reportType.equalsIgnoreCase("61A") && ddAcNature.equalsIgnoreCase("Other")) {
            this.setFrAmt("250000");
            this.setToAmt("99999999999");
            this.disableAmt = true;
        } //        
        else if (reportType.equalsIgnoreCase("AIR61A") && ddAcNature.equalsIgnoreCase("SFT003")) {
            this.setFrAmt("5000000");
            this.setToAmt("999999999999");
//            this.disableAmt = true;
        } else if (reportType.equalsIgnoreCase("AIR61A") && ddAcNature.equalsIgnoreCase("SFT004")) {
            this.setFrAmt("1000000");
            this.setToAmt("999999999999");
//            this.disableAmt = true;
        } else if (reportType.equalsIgnoreCase("AIR61A") && ddAcNature.equalsIgnoreCase("SFT005")) {
            this.setFrAmt("1000000");
            this.setToAmt("999999999999");
//            this.disableAmt = true;
        } else if (reportType.equalsIgnoreCase("AIR61A") && ddAcNature.equalsIgnoreCase("SFT001")) {
            this.setFrAmt("1000000");
            this.setToAmt("999999999999");
//            this.disableAmt = true;SFT001
        } else {
            this.disableAmt = false;
        }
    }

    public boolean validate() {
        try {

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

            if (this.reportType.equalsIgnoreCase("S")) {
                this.setMessage("Please Select Report Type !");
                return false;
            }

            if (reportType.equalsIgnoreCase("STR DEM")) {
                if (multiplier == null || multiplier.equalsIgnoreCase("")) {
                    this.setMessage("Please fill X multiplier in digits");
                    return false;
                }
            }

            if (this.getDdAcNature().equalsIgnoreCase("0")) {
                this.setMessage("Please Select Account Nature");
                return false;
            }

            Matcher frAmtCheck = p.matcher(this.frAmt);
            if (!frAmtCheck.matches()) {
                this.setMessage("Invalid From Amount Entry.");
                return false;
            }

            Matcher toAmtCheck = p.matcher(this.toAmt);
            if (!toAmtCheck.matches()) {
                this.setMessage("Invalid To Amount Entry.");
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
            String brncode = "";
            if (branch.equalsIgnoreCase("0A")) {
                List dataList = commonBeanRemote.getAlphacodeExcludingHo();
                if (!dataList.isEmpty()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        Vector element = (Vector) dataList.get(i);
                        if (i == 0) {
                            brncode = element.get(0).toString().length() == 1 ? "'" + "0" + element.get(0).toString() + "'" : "'" + element.get(0).toString() + "'";
                        } else {
                            brncode = brncode.concat(element.get(0).toString().length() == 1 ? ",'" + "0" + element.get(0).toString() + "'" : ",'" + element.get(0).toString() + "'");
                        }
                    }
                }
            } else {
                brncode = "'" + branch + "'";
            }
            YearEndDatePojo yDate = new YearEndDatePojo();
            yDate = (YearEndDatePojo) rbiReportFacade.getYearEndDataAccordingToDate(brncode.substring(1, 3).equalsIgnoreCase("0A") ? "90" : brncode.substring(1, 3), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())));
            String toDateFYear = yDate.getfYear();
            yDate = (YearEndDatePojo) rbiReportFacade.getYearEndDataAccordingToDate(brncode.substring(1, 3).equalsIgnoreCase("0A") ? "90" : brncode.substring(1, 3), ymdFormat.format(dmyFormat.parse(this.getCalFromDate())));
            String frDateFYear = yDate.getfYear();
            if (!frDateFYear.equalsIgnoreCase(toDateFYear)) {
                this.setMessage("Please check From Date and To Date both are in same financial year?");
                return false;
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return true;
    }

    public void printAction() {
        try {
            if (validate()) {
                if (getHyperPanel().equalsIgnoreCase("")) {
                    setHyperPanel("none");
                    setCommandLink("none");
                    ctrRep = new CtrPojo();
                }
//                List<CtrPojo> ctrPojos = new ArrayList<CtrPojo>();
//                List<CbaAccPojo> cbaAccList = new ArrayList<CbaAccPojo>();
//                List<CbaTrnPojo> cbaTrnList = new ArrayList<CbaTrnPojo>();
//                List<CbaInpPojo> cbaInpList = new ArrayList<CbaInpPojo>();
//                List<CbaLpePojo> cbaLpeList = new ArrayList<CbaLpePojo>();
                //CtrPojo ctrRep = new CtrPojo();

//                List<CtrArfPojo> ctrArfPojos = new ArrayList<CtrArfPojo>();
//                List<ArfAccPojo> arfAccList = new ArrayList<ArfAccPojo>();
//                List<ArfTrnPojo> arfTrnList = new ArrayList<ArfTrnPojo>();
//                List<ArfInpPojo> arfInpList = new ArrayList<ArfInpPojo>();
//                List<ArfLpePojo> arfLpeList = new ArrayList<ArfLpePojo>();
                String bankName = "", bankAddress = "";
                String acNature = this.getDdAcNature();
                List acNatureList = null;
                String acType = "";
                String brncode = "";
                if (branch.equalsIgnoreCase("0A")) {
                    List dataList = commonBeanRemote.getAlphacodeExcludingHo();
                    if (!dataList.isEmpty()) {
                        for (int i = 0; i < dataList.size(); i++) {
                            Vector element = (Vector) dataList.get(i);
                            if (i == 0) {
                                brncode = element.get(0).toString().length() == 1 ? "'" + "0" + element.get(0).toString() + "'" : "'" + element.get(0).toString() + "'";
                            } else {
                                brncode = brncode.concat(element.get(0).toString().length() == 1 ? ",'" + "0" + element.get(0).toString() + "'" : ",'" + element.get(0).toString() + "'");
                            }
                        }
                    }
                } else {
                    brncode = "'" + branch + "'";
                }
                //System.out.println("BranchList:" + brncode);
                if (reportType.equalsIgnoreCase("air")) {
                    System.out.println("START: " + new Date());
                    List<AirPojo> aifRep = ctrStrRemote.airReport(acNature, this.getFrAmt(), this.getToAmt(),
                            ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), brncode, reportType, getUserName());
                    FastReportBuilder airReprot = genrateAirReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(aifRep), airReprot, "AIR_" + this.getFrAmt() + "_" + this.getToAmt() + "_" + ymdFormat.format(dmyFormat.parse(this.getCalFromDate())) + "_" + ymdFormat.format(dmyFormat.parse(this.getCaltoDate())));
                    System.out.println("END: " + new Date());
                } else if (reportType.equalsIgnoreCase("61A")) {
                    System.out.println("START: " + new Date());
                    List<Form61APojo> aifRep = ctrStrRemote.form61AReport(acNature, this.getFrAmt(), this.getToAmt(),
                            ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), brncode, reportType, getUserName());
                    FastReportBuilder airReprot = genrateAir61AReport("Y");
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(aifRep), airReprot, "FORM-61A_" + this.getFrAmt() + "_" + this.getToAmt() + "_" + ymdFormat.format(dmyFormat.parse(this.getCalFromDate())) + "_" + ymdFormat.format(dmyFormat.parse(this.getCaltoDate())));
                    System.out.println("END: " + new Date());
                } else if ((reportType.equalsIgnoreCase("AIR61A")) && (acNature.equalsIgnoreCase("SFT003") || acNature.equalsIgnoreCase("SFT004"))) {
                    System.out.println("START: " + new Date());
                    List<Form61APojo> aifRep = sftReport.form61AForSBAndCAReportData(acNature, this.getFrAmt(), this.getToAmt(),
                            ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), brncode, reportType, getUserName());
                    FastReportBuilder airReprot = genrateAir61AReport("Y");
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(aifRep), airReprot, acNature + "_" + this.getFrAmt() + "_" + this.getToAmt() + "_" + ymdFormat.format(dmyFormat.parse(this.getCalFromDate())) + "_" + ymdFormat.format(dmyFormat.parse(this.getCaltoDate())));
                    System.out.println("END: " + new Date());
                } else if ((reportType.equalsIgnoreCase("AIR61A")) && (acNature.equalsIgnoreCase("SFT005") || acNature.equalsIgnoreCase("SFT001"))) {
                    List<Form61APojo> aifRep = sftReport.form61AForTDAndPODDReportData(acNature, this.getFrAmt(), this.getToAmt(),
                            ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), brncode, reportType, getUserName());
                    FastReportBuilder airReprot = genrateAir61AForTDAndPODDReport("Y");
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(aifRep), airReprot, acNature + "_" + this.getFrAmt() + "_" + this.getToAmt() + "_" + ymdFormat.format(dmyFormat.parse(this.getCalFromDate())) + "_" + ymdFormat.format(dmyFormat.parse(this.getCaltoDate())));
                } else {
                    if (acNature.equalsIgnoreCase("ALL")) {
                        acNatureList = commonBeanRemote.getCASBAcNatureList();
                        for (int i = 0; i < acNatureList.size(); i++) {
                            Vector acnatureVect = (Vector) acNatureList.get(i);
                            List acTypeList = commonBeanRemote.getAccType(acnatureVect.get(0).toString());
                            if (acTypeList.size() > 0) {
                                for (int j = 0; j < acTypeList.size(); j++) {
                                    Vector actypevect = (Vector) acTypeList.get(j);
                                    if (i == 0 && j == 0) {
                                        acType = "'" + actypevect.get(0).toString() + "'";
                                    } else {
                                        acType = acType.concat(",'" + actypevect.get(0).toString() + "'");
                                    }
                                }
                            }
                        }
                    } else {
                        acNatureList = commonBeanRemote.getAcNatureByAcNat(acNature);
                        List acTypeList = commonBeanRemote.getAccType(acNature);
                        if (acTypeList.size() > 0) {
                            for (int i = 0; i < acTypeList.size(); i++) {
                                Vector actypevect = (Vector) acTypeList.get(i);
                                if (i == 0) {
                                    acType = "'" + actypevect.get(0).toString() + "'";
                                } else {
                                    acType = acType.concat(",'" + actypevect.get(0).toString() + "'");
                                }
                            }
                        }
                    }
                    //System.out.println("AcTypeList:" + acType);

                    if (ctrParameter.equalsIgnoreCase("CBA")) {
                        ctrRep = ctrStrRemote.ctrReport(acNatureList, acType, this.getFrAmt(), this.getToAmt(),
                                ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), brncode, reportType);

                    } else if (ctrParameter.equalsIgnoreCase("ARF")) {
                        if (reportType.equalsIgnoreCase("ctr") || reportType.equalsIgnoreCase("NTR")) {
                            arfRep = ctrStrRemote.arfReport(acNatureList, acType, this.getFrAmt(), this.getToAmt(),
                                    ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), brncode, reportType, getUserName());
                        } else {

                            arfRep = ctrStrRemote.arfReportStr(acNatureList, acType, this.getFrAmt(), this.getToAmt(),
                                    ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), brncode, reportType, getUserName(), multiplier);
                        }

                    }
                    singleExcel();
//                    if (ctrParameter.equalsIgnoreCase("CBA")) {
//                        setHyperPanel("");
//                        setCommandLink("none");
//                    } else {
//                        setCommandLink("");
//                        setHyperPanel("none");
//                    }
                }
                //setHyperPanel("");

//                cbaAccList = ctrRep.getCbaAcc();
//                cbaInpList = ctrRep.getCbaInp();
//                cbaLpeList = ctrRep.getCbaLpe();
//                cbaTrnList = ctrRep.getCbaTrn();
//                
//                FastReportBuilder cbaAccReprot = genrateCbaAccReport();
//                FastReportBuilder cbaInpReprot = genrateCbaInpReport();
//                FastReportBuilder cbaLpeReprot = genrateCbaLpeReport();
//                FastReportBuilder cbaTrnReprot = genrateCbaTrnReport();                
//                                
//                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(cbaAccList), cbaAccReprot, "CBAACC");             
//                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(cbaInpList), cbaInpReprot, "CBAINP");
//                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(cbaLpeList), cbaLpeReprot, "CBALPE");
//                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(cbaTrnList), cbaTrnReprot, "CBATRN");

            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void downloadCsv() {
        try {
            if (validate()) {
                String bankName = "", bankAddress = "";
                String acNature = this.getDdAcNature();
                List acNatureList = null;
                String acType = "";
                String brncode = "";
                if (branch.equalsIgnoreCase("0A")) {
                    List dataList = commonBeanRemote.getAlphacodeExcludingHo();
                    if (!dataList.isEmpty()) {
                        for (int i = 0; i < dataList.size(); i++) {
                            Vector element = (Vector) dataList.get(i);
                            if (i == 0) {
                                brncode = element.get(0).toString().length() == 1 ? "'" + "0" + element.get(0).toString() + "'" : "'" + element.get(0).toString() + "'";
                            } else {
                                brncode = brncode.concat(element.get(0).toString().length() == 1 ? ",'" + "0" + element.get(0).toString() + "'" : ",'" + element.get(0).toString() + "'");
                            }
                        }
                    }
                } else {
                    brncode = "'" + branch + "'";
                }
                if (reportType.equalsIgnoreCase("61A")) {
                    System.out.println("START: " + new Date());
                    List<Form61APojo> aifRep = ctrStrRemote.form61AReport(acNature, this.getFrAmt(), this.getToAmt(),
                            ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), brncode, reportType, getUserName());
                    FastReportBuilder airReprot = genrateAir61AReport("N");
                    new ReportBean().dynamicJasperCSV(new JRBeanCollectionDataSource(aifRep), airReprot, "FORM-61A_" + this.getFrAmt() + "_" + this.getToAmt() + "_" + ymdFormat.format(dmyFormat.parse(this.getCalFromDate())) + "_" + ymdFormat.format(dmyFormat.parse(this.getCaltoDate())));
                    System.out.println("END: " + new Date());
                } else if ((reportType.equalsIgnoreCase("AIR61A")) && (acNature.equalsIgnoreCase("SFT003") || acNature.equalsIgnoreCase("SFT004"))) {
                    System.out.println("START: " + new Date());
                    List<Form61APojo> aifRep = sftReport.form61AForSBAndCAReportData(acNature, this.getFrAmt(), this.getToAmt(),
                            ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), brncode, reportType, getUserName());
                    FastReportBuilder airReprot = genrateAir61AReport("N");
                    new ReportBean().dynamicJasperCSV(new JRBeanCollectionDataSource(aifRep), airReprot, acNature + "_" + this.getFrAmt() + "_" + this.getToAmt() + "_" + ymdFormat.format(dmyFormat.parse(this.getCalFromDate())) + "_" + ymdFormat.format(dmyFormat.parse(this.getCaltoDate())));
                    System.out.println("END: " + new Date());
                } else if ((reportType.equalsIgnoreCase("AIR61A")) && (acNature.equalsIgnoreCase("SFT005"))) {
                    List<Form61APojo> aifRep = sftReport.form61AForTDAndPODDReportData(acNature, this.getFrAmt(), this.getToAmt(),
                            ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), brncode, reportType, getUserName());
                    FastReportBuilder airReprot = genrateAir61AForTDAndPODDReport("N");
                    new ReportBean().dynamicJasperCSV(new JRBeanCollectionDataSource(aifRep), airReprot, acNature + "_" + this.getFrAmt() + "_" + this.getToAmt() + "_" + ymdFormat.format(dmyFormat.parse(this.getCalFromDate())) + "_" + ymdFormat.format(dmyFormat.parse(this.getCaltoDate())));
                } else if ((reportType.equalsIgnoreCase("AIR61A")) && (acNature.equalsIgnoreCase("SFT001"))) {
                    List<Form61APojo> aifRep = sftReport.form61AForTDAndPODDReportData(acNature, this.getFrAmt(), this.getToAmt(),
                            ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), brncode, reportType, getUserName());
                    FastReportBuilder airReprot = genrateAir61AForTDAndPODDReport("Y");
                    new ReportBean().dynamicJasperCSV(new JRBeanCollectionDataSource(aifRep), airReprot, acNature + "_" + this.getFrAmt() + "_" + this.getToAmt() + "_" + ymdFormat.format(dmyFormat.parse(this.getCalFromDate())) + "_" + ymdFormat.format(dmyFormat.parse(this.getCaltoDate())));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void cbaReport(String str) {
        try {
            UIViewRoot view = getFacesContext().getCurrentInstance().getViewRoot();
//            UIComponent com = view.findComponent("CBAACC");
//            String identifier = com.getId();
//             
//            System.out.println("Identifier is :" + identifier+"; "+str);
            if (reportType.equalsIgnoreCase("STR DEM")) {
                setStrName(multiplier + " X TIME");
            }

            List<CbaAccPojo> cbaAccList = new ArrayList<CbaAccPojo>();
            List<CbaTrnPojo> cbaTrnList = new ArrayList<CbaTrnPojo>();
            List<CbaInpPojo> cbaInpList = new ArrayList<CbaInpPojo>();
            List<CbaLpePojo> cbaLpeList = new ArrayList<CbaLpePojo>();
            List<ArfBatPojo> arfBatList = new ArrayList<ArfBatPojo>();
            List<ArfRptPojo> arfRptList = new ArrayList<ArfRptPojo>();
            List<ArfBrcPojo> arfBrcList = new ArrayList<ArfBrcPojo>();

            List<ArfAccPojo> arfAccList = new ArrayList<ArfAccPojo>();
            List<ArfTrnPojo> arfTrnList = new ArrayList<ArfTrnPojo>();
            List<ArfInpPojo> arfInpList = new ArrayList<ArfInpPojo>();
            List<ArfLpePojo> arfLpeList = new ArrayList<ArfLpePojo>();

            if (str.equalsIgnoreCase("ACC")) {
                if (ctrParameter.equalsIgnoreCase("CBA")) {
                    cbaAccList = ctrRep.getCbaAcc();
                    FastReportBuilder cbaAccReprot = genrateCbaAccReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(cbaAccList), cbaAccReprot, ctrParameter.concat("ACC_" + dmy_Format.format(dmyFormat.parse(this.getCalFromDate())) + "_" + dmy_Format.format(dmyFormat.parse(this.getCaltoDate())) + "_" + getBranch() + "_" + getDdAcNature()));
                } else if (ctrParameter.equalsIgnoreCase("ARF")) {
                    arfAccList = arfRep.getArfAcc();
                    FastReportBuilder arfAccReprot = genrateArfAccReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(arfAccList), arfAccReprot, ctrParameter.concat("ACC_" + dmy_Format.format(dmyFormat.parse(this.getCalFromDate())) + "_" + dmy_Format.format(dmyFormat.parse(this.getCaltoDate())) + "_" + getBranch() + "_" + getDdAcNature()) + getStrName());
                }
            } else if (str.equalsIgnoreCase("TRN")) {
                if (ctrParameter.equalsIgnoreCase("CBA")) {
                    cbaTrnList = ctrRep.getCbaTrn();
                    FastReportBuilder cbaTrnReprot = genrateCbaTrnReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(cbaTrnList), cbaTrnReprot, ctrParameter.concat("TRN_" + dmy_Format.format(dmyFormat.parse(this.getCalFromDate())) + "_" + dmy_Format.format(dmyFormat.parse(this.getCaltoDate())) + "_" + getBranch() + "_" + getDdAcNature()));
                } else if (ctrParameter.equalsIgnoreCase("ARF")) {
                    arfTrnList = arfRep.getArfTrn();
                    FastReportBuilder arfTrnReprot = genrateArfTrnReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(arfTrnList), arfTrnReprot, ctrParameter.concat("TRN_" + dmy_Format.format(dmyFormat.parse(this.getCalFromDate())) + "_" + dmy_Format.format(dmyFormat.parse(this.getCaltoDate())) + "_" + getBranch() + "_" + getDdAcNature()) + getStrName());
                }
            } else if (str.equalsIgnoreCase("INP")) {
                if (ctrParameter.equalsIgnoreCase("CBA")) {
                    cbaInpList = ctrRep.getCbaInp();
                    FastReportBuilder cbaInpReprot = genrateCbaInpReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(cbaInpList), cbaInpReprot, ctrParameter.concat("INP_" + dmy_Format.format(dmyFormat.parse(this.getCalFromDate())) + "_" + dmy_Format.format(dmyFormat.parse(this.getCaltoDate())) + "_" + getBranch() + "_" + getDdAcNature()));
                } else if (ctrParameter.equalsIgnoreCase("ARF")) {
                    arfInpList = arfRep.getArfInp();
                    FastReportBuilder arfInpReprot = genrateArfInpReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(arfInpList), arfInpReprot, ctrParameter.concat("INP_" + dmy_Format.format(dmyFormat.parse(this.getCalFromDate())) + "_" + dmy_Format.format(dmyFormat.parse(this.getCaltoDate())) + "_" + getBranch() + "_" + getDdAcNature()) + getStrName());
                }
            } else if (str.equalsIgnoreCase("LPE")) {
                if (ctrParameter.equalsIgnoreCase("CBA")) {
                    cbaLpeList = ctrRep.getCbaLpe();
                    FastReportBuilder cbaLpeReprot = genrateCbaLpeReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(cbaLpeList), cbaLpeReprot, ctrParameter.concat("LPE_" + dmy_Format.format(dmyFormat.parse(this.getCalFromDate())) + "_" + dmy_Format.format(dmyFormat.parse(this.getCaltoDate())) + "_" + getBranch() + "_" + getDdAcNature()));
                } else if (ctrParameter.equalsIgnoreCase("ARF")) {
                    arfLpeList = arfRep.getArfLpe();
                    FastReportBuilder arfLpeReprot = genrateArfLpeReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(arfLpeList), arfLpeReprot, ctrParameter.concat("LPE_" + dmy_Format.format(dmyFormat.parse(this.getCalFromDate())) + "_" + dmy_Format.format(dmyFormat.parse(this.getCaltoDate())) + "_" + getBranch() + "_" + getDdAcNature()) + getStrName());
                }
            } else if (str.equalsIgnoreCase("BAT")) {
                if (ctrParameter.equalsIgnoreCase("ARF")) {
                    arfBatList = arfRep.getArfBat();
                    FastReportBuilder arfBatReprot = genrateArfBatReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(arfBatList), arfBatReprot, ctrParameter.concat("BAT_" + dmy_Format.format(dmyFormat.parse(this.getCalFromDate())) + "_" + dmy_Format.format(dmyFormat.parse(this.getCaltoDate())) + "_" + getBranch() + "_" + getDdAcNature()));
                }
            } else if (str.equalsIgnoreCase("RPT")) {
                if (ctrParameter.equalsIgnoreCase("ARF")) {
                    arfRptList = arfRep.getArfRpt();
                    FastReportBuilder arfRptReprot = genrateArfRptReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(arfRptList), arfRptReprot, ctrParameter.concat("RPT_" + dmy_Format.format(dmyFormat.parse(this.getCalFromDate())) + "_" + dmy_Format.format(dmyFormat.parse(this.getCaltoDate())) + "_" + getBranch() + "_" + getDdAcNature()));
                }
            } else if (str.equalsIgnoreCase("BRC")) {
                if (ctrParameter.equalsIgnoreCase("ARF")) {
                    arfBrcList = arfRep.getArfBrc();
                    FastReportBuilder arfBrcReprot = genrateArfBrcReport();
                    new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(arfBrcList), arfBrcReprot, ctrParameter.concat("BRC_" + dmy_Format.format(dmyFormat.parse(this.getCalFromDate())) + "_" + dmy_Format.format(dmyFormat.parse(this.getCaltoDate())) + "_" + getBranch() + "_" + getDdAcNature()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void singleExcel() {
        try {
            UIViewRoot view = getFacesContext().getCurrentInstance().getViewRoot();
            if (reportType.equalsIgnoreCase("STR DEM")) {
                setStrName(multiplier + " X TIME");
            }
            List<CbaAccPojo> cbaAccList = new ArrayList<CbaAccPojo>();
            List<CbaTrnPojo> cbaTrnList = new ArrayList<CbaTrnPojo>();
            List<CbaInpPojo> cbaInpList = new ArrayList<CbaInpPojo>();
            List<CbaLpePojo> cbaLpeList = new ArrayList<CbaLpePojo>();
            List<ArfBatPojo> arfBatList = new ArrayList<ArfBatPojo>();
            List<ArfRptPojo> arfRptList = new ArrayList<ArfRptPojo>();
            List<ArfBrcPojo> arfBrcList = new ArrayList<ArfBrcPojo>();
            List<ArfAccPojo> arfAccList = new ArrayList<ArfAccPojo>();
            List<ArfTrnPojo> arfTrnList = new ArrayList<ArfTrnPojo>();
            List<ArfInpPojo> arfInpList = new ArrayList<ArfInpPojo>();
            List<ArfLpePojo> arfLpeList = new ArrayList<ArfLpePojo>();
            ArrayList<JasperPrint> list = new  ArrayList<JasperPrint>();
            if (ctrParameter.equalsIgnoreCase("CBA")) {
                cbaAccList = ctrRep.getCbaAcc();
                FastReportBuilder cbaAccReprot = genrateCbaAccReport();
                cbaTrnList = ctrRep.getCbaTrn();
                FastReportBuilder cbaTrnReprot = genrateCbaTrnReport();
                cbaInpList = ctrRep.getCbaInp();
                FastReportBuilder cbaInpReprot = genrateCbaInpReport();
                cbaLpeList = ctrRep.getCbaLpe();
                FastReportBuilder cbaLpeReprot = genrateCbaLpeReport();
                JasperPrint jp1 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(cbaAccList), cbaAccReprot);
                JasperPrint jp2 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(cbaTrnList), cbaTrnReprot);
                JasperPrint jp3 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(cbaInpList), cbaInpReprot);
                JasperPrint jp4 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(cbaLpeList), cbaLpeReprot);
                list.add(jp1);list.add(jp2);list.add(jp3);list.add(jp4);
                String[] sheetNames  = {"ACC","TRN","INP","LPE"};
                new ReportBean().cibilExporterMultipleSheets(list,"CBA"+ dmy_Format.format(dmyFormat.parse(this.getCaltoDate())),sheetNames);
            } else if (ctrParameter.equalsIgnoreCase("ARF")) {
                arfAccList = arfRep.getArfAcc();
                FastReportBuilder arfAccReprot = genrateArfAccReport();
                arfTrnList = arfRep.getArfTrn();
                FastReportBuilder arfTrnReprot = genrateArfTrnReport();
                arfInpList = arfRep.getArfInp();
                FastReportBuilder arfInpReprot = genrateArfInpReport();
                arfLpeList = arfRep.getArfLpe();
                FastReportBuilder arfLpeReprot = genrateArfLpeReport();
                arfBatList = arfRep.getArfBat();
                FastReportBuilder arfBatReprot = genrateArfBatReport();
                arfRptList = arfRep.getArfRpt();
                FastReportBuilder arfRptReprot = genrateArfRptReport();
                arfBrcList = arfRep.getArfBrc();
                FastReportBuilder arfBrcReprot = genrateArfBrcReport();
                JasperPrint jp1 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(arfAccList), arfAccReprot);
                JasperPrint jp2 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(arfTrnList), arfTrnReprot);
                JasperPrint jp3 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(arfInpList), arfInpReprot);
                JasperPrint jp4 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(arfLpeList), arfLpeReprot);
                JasperPrint jp5 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(arfBatList), arfBatReprot);
                JasperPrint jp6 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(arfRptList), arfRptReprot);
                JasperPrint jp7 = new ReportBean().buildReportNew(new JRBeanCollectionDataSource(arfBrcList), arfBrcReprot);
                list.add(jp5);list.add(jp6);list.add(jp7);list.add(jp1);list.add(jp2);list.add(jp3);list.add(jp4);
                String[] sheetNames  = {"BAT","RPT","BRC","ACC","TRN","INP","LPE"};
                new ReportBean().cibilExporterMultipleSheets(list,"ARF"+ dmy_Format.format(dmyFormat.parse(this.getCaltoDate())),sheetNames);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
//
//    public void cbaTrnReport(CtrPojo ctrRep) {
//        try {
//            List<CbaTrnPojo> cbaTrnList = new ArrayList<CbaTrnPojo>();
//            
//        } catch (ApplicationException e) {
//            setMessage(e.getMessage());
//        } catch (Exception e) {
//            setMessage(e.getMessage());
//        }
//    }
//
//    public void cbaInpReport(CtrPojo ctrRep) {
//        try {
//            List<CbaInpPojo> cbaInpList = new ArrayList<CbaInpPojo>();
//            
//        } catch (ApplicationException e) {
//            setMessage(e.getMessage());
//        } catch (Exception e) {
//            setMessage(e.getMessage());
//        }
//    }
//
//    public void cbaLpeReport(CtrPojo ctrRep) {
//        try {
//            List<CbaLpePojo> cbaLpeList = new ArrayList<CbaLpePojo>();
//            cbaLpeList = ctrRep.getCbaLpe();
//            FastReportBuilder cbaLpeReprot = genrateCbaLpeReport();
//            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(cbaLpeList), cbaLpeReprot, "CBALPE");
//        } catch (ApplicationException e) {
//            setMessage(e.getMessage());
//        } catch (Exception e) {
//            setMessage(e.getMessage());
//        }
//    }

    public FastReportBuilder genrateCbaAccReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn recordType = ReportBean.getColumn("recordType", String.class, "Record Type*", 70);
            AbstractColumn monthOfReport = ReportBean.getColumn("monthOfReport", String.class, "Month of Report*", 70);
            AbstractColumn yearOfReport = ReportBean.getColumn("yearOfReport", String.class, "Year of Report*", 70);
            AbstractColumn lineNumber = ReportBean.getColumn("lineNumber", Integer.class, "Line Number*", 70);
            AbstractColumn branchReferenceNumber = ReportBean.getColumn("branchReferenceNumber", String.class, "Branch Reference Number*", 70);
            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account Number*", 110);
            AbstractColumn name = ReportBean.getColumn("name", String.class, "Name of first/sole account holder", 500);
            AbstractColumn typeOfAccount = ReportBean.getColumn("typeOfAccount", String.class, "Type of Account*", 70);
            AbstractColumn typeOfAccountHolder = ReportBean.getColumn("typeOfAccountHolder", String.class, "Type of Account Holder*", 70);
            AbstractColumn dateOfAccountOpening = ReportBean.getColumn("dateOfAccountOpening", String.class, "Date of Account opening*", 70);
            AbstractColumn riskCategory = ReportBean.getColumn("riskCategory", String.class, "Risk Category", 60);
            AbstractColumn cumulativeCreditTurnover = ReportBean.getColumn("cumulativeCreditTurnover", BigDecimal.class, "Cumulative Credit Turnover*", 100);
            AbstractColumn cumulativeDebitTurnover = ReportBean.getColumn("cumulativeDebitTurnover", BigDecimal.class, "Cumulative Debit Turnover*", 100);
            AbstractColumn cumulativeCashDepositTurnover = ReportBean.getColumn("cumulativeCashDepositTurnover", BigDecimal.class, "Cumulative Cash Deposit Turnover*", 100);
            AbstractColumn cumulativeCashWithdrawlTurnover = ReportBean.getColumn("cumulativeCashWithdrawlTurnover", BigDecimal.class, "Cumulative Cash Withdrawl Turnover*", 100);

            fastReport.addColumn(recordType);
            width = width + recordType.getWidth();

            fastReport.addColumn(monthOfReport);
            width = width + monthOfReport.getWidth();

            fastReport.addColumn(yearOfReport);
            width = width + yearOfReport.getWidth();

            fastReport.addColumn(lineNumber);
            width = width + lineNumber.getWidth();

            fastReport.addColumn(branchReferenceNumber);
            width = width + branchReferenceNumber.getWidth();

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(name);
            width = width + name.getWidth();

            fastReport.addColumn(typeOfAccount);
            width = width + typeOfAccount.getWidth();

            fastReport.addColumn(typeOfAccountHolder);
            width = width + typeOfAccountHolder.getWidth();

            fastReport.addColumn(dateOfAccountOpening);
            width = width + dateOfAccountOpening.getWidth();

            fastReport.addColumn(riskCategory);
            width = width + riskCategory.getWidth();

            fastReport.addColumn(cumulativeCreditTurnover);
            cumulativeCreditTurnover.setStyle(style);
            width = width + cumulativeCreditTurnover.getWidth();

            fastReport.addColumn(cumulativeDebitTurnover);
            cumulativeDebitTurnover.setStyle(style);
            width = width + cumulativeDebitTurnover.getWidth();

            fastReport.addColumn(cumulativeCashDepositTurnover);
            cumulativeCashDepositTurnover.setStyle(style);
            width = width + cumulativeCashDepositTurnover.getWidth();

            fastReport.addColumn(cumulativeCashWithdrawlTurnover);
            cumulativeCashWithdrawlTurnover.setStyle(style);
            width = width + cumulativeCashWithdrawlTurnover.getWidth();

            Page page = new Page(1410, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("CBAACC (From Amount " + frAmt + " To " + toAmt + " )");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateArfAccReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);
            AbstractColumn blank = null;
            AbstractColumn reason = null;

            AbstractColumn lineNumber = ReportBean.getColumn("lineNumber", Integer.class, "LineNumber*", 70);
            AbstractColumn reportSerialNum = ReportBean.getColumn("reportSerialNum", String.class, "ReportSerialNum*", 90);
            AbstractColumn branchRefNum = ReportBean.getColumn("branchRefNum", String.class, "BranchRefNum*", 90);
            AbstractColumn accountNumber = ReportBean.getColumn("accountNumber", String.class, "AccountNumber*", 90);
            AbstractColumn accountType = ReportBean.getColumn("accountType", String.class, "AccountType*", 80);
            AbstractColumn holderName = ReportBean.getColumn("holderName", String.class, "HolderName*", 400);
            AbstractColumn accountHolderType = ReportBean.getColumn("accountHolderType", String.class, "AccountHolderType*", 70);
            AbstractColumn accountStatus = ReportBean.getColumn("accountStatus", String.class, "AccountStatus*", 70);
            AbstractColumn dateOfOpening = ReportBean.getColumn("dateOfOpening", String.class, "dateOfOpening", 70);
            AbstractColumn riskRating = ReportBean.getColumn("riskRating", String.class, "RiskRating*", 70);
            AbstractColumn cumulativeCreditTurnover = ReportBean.getColumn("cumulativeCreditTurnover", BigDecimal.class, "CumulativeCreditTurnover", 100);
            AbstractColumn cumulativeDebitTurnover = ReportBean.getColumn("cumulativeDebitTurnover", BigDecimal.class, "CumulativeDebitTurnover", 100);
            AbstractColumn cumulativeCashDepositTurnover = ReportBean.getColumn("cumulativeCashDepositTurnover", BigDecimal.class, "CumulativeCashDepositTurnover", 100);
            AbstractColumn cumulativeCashWithdrawalTurnover = ReportBean.getColumn("cumulativeCashWithdrawalTurnover", BigDecimal.class, "CumulativeCashWithdrawalTurnover", 100);
            AbstractColumn noTransactionsTobeReported = ReportBean.getColumn("noTransactionsTobeReported", String.class, "NoTransactionsTobeReported*", 100);
            if (reportType.equalsIgnoreCase("STR DEM")) {
                blank = ReportBean.getColumn("blank", String.class, " ", 50);
                reason = ReportBean.getColumn("reason", String.class, "Reason*(8th Nov to 31 Dec)-This column will not be  considered in FIU Reporting file.", 580);
            }

            fastReport.addColumn(lineNumber);
            width = width + lineNumber.getWidth();

            fastReport.addColumn(reportSerialNum);
            width = width + reportSerialNum.getWidth();

            fastReport.addColumn(branchRefNum);
            width = width + branchRefNum.getWidth();

            fastReport.addColumn(accountNumber);
            width = width + accountNumber.getWidth();

            fastReport.addColumn(accountType);
            width = width + accountType.getWidth();

            fastReport.addColumn(holderName);
            width = width + holderName.getWidth();

            fastReport.addColumn(accountHolderType);
            width = width + accountHolderType.getWidth();

            fastReport.addColumn(accountStatus);
            width = width + accountStatus.getWidth();

            fastReport.addColumn(dateOfOpening);
            width = width + dateOfOpening.getWidth();

            fastReport.addColumn(riskRating);
            width = width + riskRating.getWidth();

            fastReport.addColumn(cumulativeCreditTurnover);
            cumulativeCreditTurnover.setStyle(style);
            width = width + cumulativeCreditTurnover.getWidth();

            fastReport.addColumn(cumulativeDebitTurnover);
            cumulativeDebitTurnover.setStyle(style);
            width = width + cumulativeDebitTurnover.getWidth();

            fastReport.addColumn(cumulativeCashDepositTurnover);
            cumulativeCashDepositTurnover.setStyle(style);
            width = width + cumulativeCashDepositTurnover.getWidth();

            fastReport.addColumn(cumulativeCashWithdrawalTurnover);
            cumulativeCashWithdrawalTurnover.setStyle(style);
            width = width + cumulativeCashWithdrawalTurnover.getWidth();

            fastReport.addColumn(noTransactionsTobeReported);
            width = width + noTransactionsTobeReported.getWidth();

            if (reportType.equalsIgnoreCase("STR DEM")) {
                fastReport.addColumn(blank);
                width = width + blank.getWidth();

                fastReport.addColumn(reason);
                width = width + reason.getWidth();
            }



            Page page = new Page(1410, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("ARFACC (From Amount " + frAmt + " To " + toAmt + " )");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateCbaInpReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn recordType = ReportBean.getColumn("recordType", String.class, "Record Type*", 70);
            AbstractColumn monthOfReport = ReportBean.getColumn("monthOfReport", String.class, "Month of Report*", 70);
            AbstractColumn yearOfReport = ReportBean.getColumn("yearOfReport", String.class, "Year of Report*", 70);
            AbstractColumn lineNumber = ReportBean.getColumn("lineNumber", Integer.class, "Line Number*", 70);
            AbstractColumn branchReferenceNumber = ReportBean.getColumn("branchReferenceNumber", String.class, "Branch Reference Number*", 70);
            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account Number*", 90);
            AbstractColumn relationFlag = ReportBean.getColumn("relationFlag", String.class, "Relation Flag*", 60);
            AbstractColumn name = ReportBean.getColumn("name", String.class, "Full name of  Individual*", 300);
            AbstractColumn customerID = ReportBean.getColumn("customerID", String.class, "Customer ID/Number", 70);
            AbstractColumn fatherSpouseName = ReportBean.getColumn("fatherSpouseName", String.class, "Name of Father/Spouse", 400);
            AbstractColumn occupation = ReportBean.getColumn("occupation", String.class, "Occupation", 300);
            AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Date of Birth", 70);
            AbstractColumn sex = ReportBean.getColumn("sex", String.class, "Sex", 70);
            AbstractColumn nationality = ReportBean.getColumn("nationality", String.class, "Nationality", 70);
            AbstractColumn typeIOfIdentification = ReportBean.getColumn("typeIOfIdentification", String.class, "Type of Identification", 90);
            AbstractColumn idNo = ReportBean.getColumn("idNo", String.class, "Identification Number", 100);
            AbstractColumn issuingAuthority = ReportBean.getColumn("issuingAuthority", String.class, "Issuing Authority", 100);
            AbstractColumn placeOfIssue = ReportBean.getColumn("placeOfIssue", String.class, "Place of Issue", 150);
            AbstractColumn pan = ReportBean.getColumn("pan", String.class, "PAN", 100);
            AbstractColumn communicationAddress1 = ReportBean.getColumn("communicationAddress1", String.class, "Communication Address 1*", 150);
            AbstractColumn communicationAddress2 = ReportBean.getColumn("communicationAddress2", String.class, "Communication Address 2", 150);
            AbstractColumn communicationAddress3 = ReportBean.getColumn("communicationAddress3", String.class, "Communication Address 3", 150);
            AbstractColumn communicationAddress4 = ReportBean.getColumn("communicationAddress4", String.class, "Communication Address 4", 150);
            AbstractColumn communicationAddress5 = ReportBean.getColumn("communicationAddress5", String.class, "Communication Address 5", 150);
            AbstractColumn communicationAddressPinCode = ReportBean.getColumn("communicationAddressPinCode", String.class, "Communication Address Pin code*", 90);
            AbstractColumn contactTelephone = ReportBean.getColumn("contactTelephone", String.class, "Contact Telephone", 150);
            AbstractColumn contactMobilenumber = ReportBean.getColumn("contactMobilenumber", String.class, "Contact Mobile number", 100);
            AbstractColumn contactEMail = ReportBean.getColumn("contactEMail", String.class, "Contact E-mail", 150);
            AbstractColumn placeOfWork = ReportBean.getColumn("placeOfWork", String.class, "Place of Work", 100);
            AbstractColumn secondAddress1 = ReportBean.getColumn("secondAddress1", String.class, "Second Address 1", 150);
            AbstractColumn secondAddress2 = ReportBean.getColumn("secondAddress2", String.class, "Second Address 2", 150);
            AbstractColumn secondAddress3 = ReportBean.getColumn("secondAddress3", String.class, "Second Address 3", 150);
            AbstractColumn secondAddress4 = ReportBean.getColumn("secondAddress4", String.class, "Second Address 4", 150);
            AbstractColumn secondAddress5 = ReportBean.getColumn("secondAddress5", String.class, "Second Address 5", 150);
            AbstractColumn secondAddressPinCode = ReportBean.getColumn("secondAddressPinCode", String.class, "Second Address Pin code", 90);
            AbstractColumn secondTelephone = ReportBean.getColumn("secondTelephone", String.class, "Second Telephone", 150);

            fastReport.addColumn(recordType);
            width = width + recordType.getWidth();

            fastReport.addColumn(monthOfReport);
            width = width + monthOfReport.getWidth();

            fastReport.addColumn(yearOfReport);
            width = width + yearOfReport.getWidth();

            fastReport.addColumn(lineNumber);
            width = width + lineNumber.getWidth();

            fastReport.addColumn(branchReferenceNumber);
            width = width + branchReferenceNumber.getWidth();

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(relationFlag);
            width = width + relationFlag.getWidth();

            fastReport.addColumn(name);
            width = width + name.getWidth();

            fastReport.addColumn(customerID);
            width = width + customerID.getWidth();

            fastReport.addColumn(fatherSpouseName);
            width = width + fatherSpouseName.getWidth();

            fastReport.addColumn(occupation);
            width = width + occupation.getWidth();

            fastReport.addColumn(dob);
            width = width + dob.getWidth();

            fastReport.addColumn(sex);
            width = width + sex.getWidth();

            fastReport.addColumn(nationality);
            width = width + nationality.getWidth();

            fastReport.addColumn(typeIOfIdentification);
            width = width + typeIOfIdentification.getWidth();

            fastReport.addColumn(idNo);
            width = width + idNo.getWidth();

            fastReport.addColumn(issuingAuthority);
            width = width + issuingAuthority.getWidth();

            fastReport.addColumn(placeOfIssue);
            width = width + placeOfIssue.getWidth();

            fastReport.addColumn(pan);
            width = width + pan.getWidth();

            fastReport.addColumn(communicationAddress1);
            width = width + communicationAddress1.getWidth();

            fastReport.addColumn(communicationAddress2);
            width = width + communicationAddress2.getWidth();

            fastReport.addColumn(communicationAddress3);
            width = width + communicationAddress3.getWidth();

            fastReport.addColumn(communicationAddress4);
            width = width + communicationAddress4.getWidth();

            fastReport.addColumn(communicationAddress5);
            width = width + communicationAddress5.getWidth();

            fastReport.addColumn(communicationAddressPinCode);
            width = width + communicationAddressPinCode.getWidth();

            fastReport.addColumn(contactTelephone);
            width = width + contactTelephone.getWidth();

            fastReport.addColumn(contactMobilenumber);
            width = width + contactMobilenumber.getWidth();

            fastReport.addColumn(contactEMail);
            width = width + contactEMail.getWidth();

            fastReport.addColumn(placeOfWork);
            width = width + placeOfWork.getWidth();

            fastReport.addColumn(secondAddress1);
            width = width + secondAddress1.getWidth();

            fastReport.addColumn(secondAddress2);
            width = width + secondAddress2.getWidth();

            fastReport.addColumn(secondAddress3);
            width = width + secondAddress3.getWidth();

            fastReport.addColumn(secondAddress4);
            width = width + secondAddress4.getWidth();

            fastReport.addColumn(secondAddress5);
            width = width + secondAddress5.getWidth();

            fastReport.addColumn(secondAddressPinCode);
            width = width + secondAddressPinCode.getWidth();

            fastReport.addColumn(secondTelephone);
            width = width + secondTelephone.getWidth();



//            fastReport.addColumn(intAmt);
//            intAmt.setStyle(style);
//            //width = width + 2 * intAmt.getWidth();

            Page page = new Page(4200, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("CBAINP (From Amount " + frAmt + " To " + toAmt + " )");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;

    }

    public FastReportBuilder genrateArfInpReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn lineNumber = ReportBean.getColumn("lineNumber", Integer.class, "LineNumber*", 90);
            AbstractColumn reportSerialNum = ReportBean.getColumn("reportSerialNum", String.class, "ReportSerialNum*", 90);
            AbstractColumn branchRefNum = ReportBean.getColumn("branchRefNum", String.class, "BranchRefNum*", 90);
            AbstractColumn accountNumber = ReportBean.getColumn("accountNumber", String.class, "AccountNumber*", 90);
            AbstractColumn personName = ReportBean.getColumn("personName", String.class, "PersonName*", 400);
            AbstractColumn customerId = ReportBean.getColumn("customerId", String.class, "CustomerId", 70);
            AbstractColumn relationFlag = ReportBean.getColumn("relationFlag", String.class, "RelationFlag*", 80);
            AbstractColumn communicationAddress = ReportBean.getColumn("communicationAddress", String.class, "CommunicationAddress*", 400);
            AbstractColumn city = ReportBean.getColumn("city", String.class, "City", 100);
            AbstractColumn stateCode = ReportBean.getColumn("stateCode", String.class, "StateCode*", 70);
            AbstractColumn pinCode = ReportBean.getColumn("pinCode", String.class, "PinCode", 80);
            AbstractColumn countryCode = ReportBean.getColumn("countryCode", String.class, "CountryCode*", 50);
            AbstractColumn secondAddress = ReportBean.getColumn("secondAddress", String.class, "SecondAddress", 400);
            AbstractColumn secondCity = ReportBean.getColumn("secondCity", String.class, "City", 100);
            AbstractColumn secondStateCode = ReportBean.getColumn("secondStateCode", String.class, "StateCode*", 70);
            AbstractColumn secondPinCode = ReportBean.getColumn("secondPinCode", String.class, "PinCode", 80);
            AbstractColumn secondCountryCode = ReportBean.getColumn("secondCountryCode", String.class, "CountryCode*", 50);
            AbstractColumn telephone = ReportBean.getColumn("telephone", String.class, "Telephone", 200);
            AbstractColumn mobile = ReportBean.getColumn("mobile", String.class, "Mobile", 200);
            AbstractColumn fax = ReportBean.getColumn("fax", String.class, "Fax", 100);
            AbstractColumn email = ReportBean.getColumn("email", String.class, "Email", 250);
            AbstractColumn pan = ReportBean.getColumn("pan", String.class, "PAN", 100);
            AbstractColumn uin = ReportBean.getColumn("uin", String.class, "UIN", 100);
            AbstractColumn gender = ReportBean.getColumn("gender", String.class, "Gender*", 50);
            AbstractColumn dateOfBirth = ReportBean.getColumn("dateOfBirth", String.class, "DateOfBirth", 70);
            AbstractColumn identificationType = ReportBean.getColumn("identificationType", String.class, "IdentificationType*", 100);
            AbstractColumn identificationNumber = ReportBean.getColumn("identificationNumber", String.class, "IdentificationNumber", 100);
            AbstractColumn issuingAuthority = ReportBean.getColumn("issuingAuthority", String.class, "IssuingAuthority", 100);
            AbstractColumn placeOfIssue = ReportBean.getColumn("placeOfIssue", String.class, "PlaceOfIssue", 90);
            AbstractColumn nationality = ReportBean.getColumn("nationality", String.class, "Nationality*", 90);
            AbstractColumn placeOfWork = ReportBean.getColumn("placeOfWork", String.class, "PlaceOfWork", 80);
            AbstractColumn fatherOrSpouse = ReportBean.getColumn("fatherOrSpouse", String.class, "FatherOrSpouse", 300);
            AbstractColumn occupation = ReportBean.getColumn("occupation", String.class, "Occupation", 80);

            fastReport.addColumn(lineNumber);
            width = width + lineNumber.getWidth();

            fastReport.addColumn(reportSerialNum);
            width = width + reportSerialNum.getWidth();

            fastReport.addColumn(branchRefNum);
            width = width + branchRefNum.getWidth();

            fastReport.addColumn(accountNumber);
            width = width + accountNumber.getWidth();

            fastReport.addColumn(personName);
            width = width + personName.getWidth();

            fastReport.addColumn(customerId);
            width = width + customerId.getWidth();

            fastReport.addColumn(relationFlag);
            width = width + relationFlag.getWidth();

            fastReport.addColumn(communicationAddress);
            width = width + communicationAddress.getWidth();

            fastReport.addColumn(city);
            width = width + city.getWidth();

            fastReport.addColumn(stateCode);
            width = width + stateCode.getWidth();

            fastReport.addColumn(pinCode);
            width = width + pinCode.getWidth();

            fastReport.addColumn(countryCode);
            width = width + countryCode.getWidth();

            fastReport.addColumn(secondAddress);
            width = width + secondAddress.getWidth();

            fastReport.addColumn(secondCity);
            width = width + secondCity.getWidth();

            fastReport.addColumn(secondStateCode);
            width = width + secondStateCode.getWidth();

            fastReport.addColumn(secondPinCode);
            width = width + secondPinCode.getWidth();

            fastReport.addColumn(secondCountryCode);
            width = width + secondCountryCode.getWidth();

            fastReport.addColumn(telephone);
            width = width + telephone.getWidth();

            fastReport.addColumn(mobile);
            width = width + mobile.getWidth();

            fastReport.addColumn(fax);
            width = width + fax.getWidth();

            fastReport.addColumn(email);
            width = width + email.getWidth();

            fastReport.addColumn(pan);
            width = width + pan.getWidth();

            fastReport.addColumn(uin);
            width = width + uin.getWidth();

            fastReport.addColumn(gender);
            width = width + gender.getWidth();

            fastReport.addColumn(dateOfBirth);
            width = width + dateOfBirth.getWidth();

            fastReport.addColumn(identificationType);
            width = width + identificationType.getWidth();

            fastReport.addColumn(identificationNumber);
            width = width + identificationNumber.getWidth();

            fastReport.addColumn(issuingAuthority);
            width = width + issuingAuthority.getWidth();

            fastReport.addColumn(placeOfIssue);
            width = width + placeOfIssue.getWidth();

            fastReport.addColumn(nationality);
            width = width + nationality.getWidth();

            fastReport.addColumn(placeOfWork);
            width = width + placeOfWork.getWidth();

            fastReport.addColumn(fatherOrSpouse);
            width = width + fatherOrSpouse.getWidth();

            fastReport.addColumn(occupation);
            width = width + occupation.getWidth();

//            fastReport.addColumn(intAmt);
//            intAmt.setStyle(style);
//            //width = width + 2 * intAmt.getWidth();

            Page page = new Page(4200, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("ARFINP (From Amount " + frAmt + " To " + toAmt + " )");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;

    }

    public FastReportBuilder genrateCbaLpeReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn recordType = ReportBean.getColumn("recordType", String.class, "Record Type*", 70);
            AbstractColumn monthOfReport = ReportBean.getColumn("monthOfReport", String.class, "Month of Report*", 70);
            AbstractColumn yearOfReport = ReportBean.getColumn("yearOfReport", String.class, "Year of Report*", 70);
            AbstractColumn lineNumber = ReportBean.getColumn("lineNumber", Integer.class, "Line Number*", 70);
            AbstractColumn branchReferenceNumber = ReportBean.getColumn("branchReferenceNumber", String.class, "Branch Reference Number*", 70);
            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account Number*", 90);
            AbstractColumn relationFlag = ReportBean.getColumn("relationFlag", String.class, "Relation Flag*", 70);
            AbstractColumn nameOfLegalPerson = ReportBean.getColumn("nameOfLegalPerson", String.class, "Name of Legal Person/Entity*", 200);
            AbstractColumn customerID = ReportBean.getColumn("customerID", String.class, "Customer ID/Number", 70);
            AbstractColumn natureOfBusiness = ReportBean.getColumn("natureOfBusiness", String.class, "Nature of Business", 200);
            AbstractColumn dateOfIncorporation = ReportBean.getColumn("dateOfIncorporation", String.class, "Date of Incorporation", 100);
            AbstractColumn typeOfConstitution = ReportBean.getColumn("typeOfConstitution", String.class, "Type of Constitution*", 70);
            AbstractColumn registrationNumber = ReportBean.getColumn("registrationNumber", String.class, "Registration Number", 100);
            AbstractColumn registeringAuthority = ReportBean.getColumn("registeringAuthority", String.class, "Registering authority", 100);
            AbstractColumn placeOfRegistration = ReportBean.getColumn("placeOfRegistration", String.class, "Place of Registration", 100);
            AbstractColumn pan = ReportBean.getColumn("pan", String.class, "PAN", 100);
            AbstractColumn communicationAddress1 = ReportBean.getColumn("communicationAddress1", String.class, "Communication Address 1*", 300);
            AbstractColumn communicationAddress2 = ReportBean.getColumn("communicationAddress2", String.class, "Communication Address 2", 150);
            AbstractColumn communicationAddress3 = ReportBean.getColumn("communicationAddress3", String.class, "Communication Address 3", 150);
            AbstractColumn communicationAddress4 = ReportBean.getColumn("communicationAddress4", String.class, "Communication Address 4", 150);
            AbstractColumn communicationAddress5 = ReportBean.getColumn("communicationAddress5", String.class, "Communication Address 5", 150);
            AbstractColumn communicationAddressPinCode = ReportBean.getColumn("communicationAddressPinCode", String.class, "Communication Address Pin code*", 90);
            AbstractColumn contactTelephone = ReportBean.getColumn("contactTelephone", String.class, "Contact Telephone", 150);
            AbstractColumn contactFax = ReportBean.getColumn("contactFax", String.class, "Contact Fax", 100);
            AbstractColumn contactEmail = ReportBean.getColumn("contactEmail", String.class, "Contact Email", 150);
            AbstractColumn registeredAddress1 = ReportBean.getColumn("registeredAddress1", String.class, "Registered Address 1", 300);
            AbstractColumn registeredAddress2 = ReportBean.getColumn("registeredAddress2", String.class, "Registered Address 2", 150);
            AbstractColumn registeredAddress3 = ReportBean.getColumn("registeredAddress3", String.class, "Registered Address 3", 150);
            AbstractColumn registeredAddress4 = ReportBean.getColumn("registeredAddress4", String.class, "Registered Address 4", 150);
            AbstractColumn registeredAddress5 = ReportBean.getColumn("registeredAddress5", String.class, "Registered Address 5", 150);
            AbstractColumn registeredAddressPinCode = ReportBean.getColumn("registeredAddressPinCode", String.class, "Registered Address Pin code", 90);
            AbstractColumn registeredOfficeTelephone = ReportBean.getColumn("registeredOfficeTelephone", String.class, "Registered office Telephone", 150);
            AbstractColumn registeredOfficeFax = ReportBean.getColumn("registeredOfficeFax", String.class, "Registered office Fax", 150);

            fastReport.addColumn(recordType);
            width = width + recordType.getWidth();

            fastReport.addColumn(monthOfReport);
            width = width + monthOfReport.getWidth();

            fastReport.addColumn(yearOfReport);
            width = width + yearOfReport.getWidth();

            fastReport.addColumn(lineNumber);
            width = width + lineNumber.getWidth();

            fastReport.addColumn(branchReferenceNumber);
            width = width + branchReferenceNumber.getWidth();

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(relationFlag);
            width = width + relationFlag.getWidth();

            fastReport.addColumn(nameOfLegalPerson);
            width = width + nameOfLegalPerson.getWidth();

            fastReport.addColumn(customerID);
            width = width + customerID.getWidth();

            fastReport.addColumn(natureOfBusiness);
            width = width + natureOfBusiness.getWidth();

            fastReport.addColumn(dateOfIncorporation);
            width = width + dateOfIncorporation.getWidth();

            fastReport.addColumn(typeOfConstitution);
            width = width + typeOfConstitution.getWidth();

            fastReport.addColumn(registrationNumber);
            width = width + registrationNumber.getWidth();

            fastReport.addColumn(registeringAuthority);
            width = width + registeringAuthority.getWidth();

            fastReport.addColumn(placeOfRegistration);
            width = width + placeOfRegistration.getWidth();

            fastReport.addColumn(pan);
            width = width + pan.getWidth();

            fastReport.addColumn(communicationAddress1);
            width = width + communicationAddress1.getWidth();

            fastReport.addColumn(communicationAddress2);
            width = width + communicationAddress2.getWidth();

            fastReport.addColumn(communicationAddress3);
            width = width + communicationAddress3.getWidth();

            fastReport.addColumn(communicationAddress4);
            width = width + communicationAddress4.getWidth();

            fastReport.addColumn(communicationAddress5);
            width = width + communicationAddress5.getWidth();

            fastReport.addColumn(communicationAddressPinCode);
            width = width + communicationAddressPinCode.getWidth();

            fastReport.addColumn(contactTelephone);
            width = width + contactTelephone.getWidth();

            fastReport.addColumn(contactFax);
            width = width + contactFax.getWidth();

            fastReport.addColumn(registeredAddress1);
            width = width + registeredAddress1.getWidth();

            fastReport.addColumn(registeredAddress2);
            width = width + registeredAddress2.getWidth();

            fastReport.addColumn(registeredAddress3);
            width = width + registeredAddress3.getWidth();

            fastReport.addColumn(registeredAddress4);
            width = width + registeredAddress4.getWidth();

            fastReport.addColumn(registeredAddress5);
            width = width + registeredAddress5.getWidth();

            fastReport.addColumn(registeredAddressPinCode);
            width = width + registeredAddressPinCode.getWidth();

            fastReport.addColumn(registeredOfficeTelephone);
            width = width + registeredOfficeTelephone.getWidth();

            fastReport.addColumn(registeredOfficeFax);
            width = width + registeredOfficeFax.getWidth();

            Page page = new Page(5000, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("CBALPE (From Amount " + frAmt + " To " + toAmt + " )");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateArfLpeReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn lineNumber = ReportBean.getColumn("lineNumber", Integer.class, "LineNumber*", 70);
            AbstractColumn reportSerialNum = ReportBean.getColumn("reportSerialNum", String.class, "ReportSerialNum*", 70);
            AbstractColumn branchRefNum = ReportBean.getColumn("branchRefNum", String.class, "BranchRefNum*", 70);
            AbstractColumn accountNumber = ReportBean.getColumn("accountNumber", String.class, "AccountNumber*", 90);
            AbstractColumn personName = ReportBean.getColumn("personName", String.class, "PersonName*", 400);
            AbstractColumn customerId = ReportBean.getColumn("customerId", String.class, "CustomerId", 70);
            AbstractColumn relationFlag = ReportBean.getColumn("relationFlag", String.class, "RelationFlag*", 80);
            AbstractColumn communicationAddress = ReportBean.getColumn("communicationAddress", String.class, "CommunicationAddress*", 450);
            AbstractColumn city = ReportBean.getColumn("city", String.class, "City", 90);
            AbstractColumn stateCode = ReportBean.getColumn("stateCode", String.class, "StateCode*", 90);
            AbstractColumn pinCode = ReportBean.getColumn("pinCode", String.class, "PinCode", 90);
            AbstractColumn countryCode = ReportBean.getColumn("countryCode", String.class, "CountryCode*", 90);
            AbstractColumn secondAddress = ReportBean.getColumn("secondAddress", String.class, "SecondAddress", 450);
            AbstractColumn secondCity = ReportBean.getColumn("secondCity", String.class, "City", 90);
            AbstractColumn secondStateCode = ReportBean.getColumn("secondStateCode", String.class, "StateCode*", 90);
            AbstractColumn secondPinCode = ReportBean.getColumn("secondPinCode", String.class, "PinCode", 90);
            AbstractColumn secondCountryCode = ReportBean.getColumn("secondCountryCode", String.class, "SecondCountryCode", 90);
            AbstractColumn telephone = ReportBean.getColumn("telephone", String.class, "Telephone", 150);
            AbstractColumn mobile = ReportBean.getColumn("mobile", String.class, "Mobile", 150);
            AbstractColumn fax = ReportBean.getColumn("fax", String.class, "Fax", 100);
            AbstractColumn email = ReportBean.getColumn("email", String.class, "Email", 200);
            AbstractColumn pan = ReportBean.getColumn("pan", String.class, "PAN", 150);
            AbstractColumn uin = ReportBean.getColumn("uin", String.class, "UIN", 150);
            AbstractColumn constitutionType = ReportBean.getColumn("constitutionType", String.class, "constitutionType*", 150);
            AbstractColumn registrationNumber = ReportBean.getColumn("registrationNumber", String.class, "RegistrationNumber", 150);
            AbstractColumn dateOfIncorporation = ReportBean.getColumn("dateOfIncorporation", String.class, "DateOfIncorporation", 150);
            AbstractColumn placeOfRegistration = ReportBean.getColumn("placeOfRegistration", String.class, "PlaceOfRegistration", 150);
            AbstractColumn registeredCountryCode = ReportBean.getColumn("registeredCountryCode", String.class, "CountryCode*", 150);
            AbstractColumn natureOfBusiness = ReportBean.getColumn("natureOfBusiness", String.class, "NatureOfBusiness", 150);
            AbstractColumn registeredAddresPincode = ReportBean.getColumn("registeredAddresPincode", String.class, "RegisteredAddresPincode", 150);
            AbstractColumn registeredOfficeTelephone = ReportBean.getColumn("registeredOfficeTelephone", String.class, "RegisteredOfficeTelephone", 150);
            AbstractColumn registeredOfficeFax = ReportBean.getColumn("registeredOfficeFax", String.class, "RegisteredOfficeFax", 150);

            fastReport.addColumn(lineNumber);
            width = width + lineNumber.getWidth();

            fastReport.addColumn(reportSerialNum);
            width = width + reportSerialNum.getWidth();

            fastReport.addColumn(branchRefNum);
            width = width + branchRefNum.getWidth();

            fastReport.addColumn(accountNumber);
            width = width + accountNumber.getWidth();

            fastReport.addColumn(personName);
            width = width + personName.getWidth();

            fastReport.addColumn(customerId);
            width = width + customerId.getWidth();

            fastReport.addColumn(relationFlag);
            width = width + relationFlag.getWidth();

            fastReport.addColumn(communicationAddress);
            width = width + communicationAddress.getWidth();

            fastReport.addColumn(city);
            width = width + city.getWidth();

            fastReport.addColumn(stateCode);
            width = width + stateCode.getWidth();

            fastReport.addColumn(pinCode);
            width = width + pinCode.getWidth();

            fastReport.addColumn(countryCode);
            width = width + countryCode.getWidth();

            fastReport.addColumn(secondAddress);
            width = width + secondAddress.getWidth();

            fastReport.addColumn(secondCity);
            width = width + secondCity.getWidth();

            fastReport.addColumn(secondStateCode);
            width = width + secondStateCode.getWidth();

            fastReport.addColumn(secondPinCode);
            width = width + secondPinCode.getWidth();

            fastReport.addColumn(secondCountryCode);
            width = width + secondCountryCode.getWidth();

            fastReport.addColumn(telephone);
            width = width + telephone.getWidth();

            fastReport.addColumn(mobile);
            width = width + mobile.getWidth();

            fastReport.addColumn(fax);
            width = width + fax.getWidth();

            fastReport.addColumn(email);
            width = width + email.getWidth();

            fastReport.addColumn(pan);
            width = width + pan.getWidth();

            fastReport.addColumn(uin);
            width = width + uin.getWidth();

            fastReport.addColumn(constitutionType);
            width = width + constitutionType.getWidth();

            fastReport.addColumn(registrationNumber);
            width = width + registrationNumber.getWidth();

            fastReport.addColumn(dateOfIncorporation);
            width = width + dateOfIncorporation.getWidth();

            fastReport.addColumn(placeOfRegistration);
            width = width + placeOfRegistration.getWidth();

            fastReport.addColumn(registeredCountryCode);
            width = width + registeredCountryCode.getWidth();

            fastReport.addColumn(natureOfBusiness);
            width = width + natureOfBusiness.getWidth();

            fastReport.addColumn(registeredAddresPincode);
            width = width + registeredAddresPincode.getWidth();

            fastReport.addColumn(registeredOfficeTelephone);
            width = width + registeredOfficeTelephone.getWidth();

            fastReport.addColumn(registeredOfficeFax);
            width = width + registeredOfficeFax.getWidth();

            Page page = new Page(5000, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("ARFLPE (From Amount " + frAmt + " To " + toAmt + " )");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateCbaTrnReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn recordType = ReportBean.getColumn("recordType", String.class, "Record Type*", 70);
            AbstractColumn lineNumber = ReportBean.getColumn("lineNumber", Integer.class, "Line Number*", 70);
            AbstractColumn branchReferenceNumber = ReportBean.getColumn("branchReferenceNumber", String.class, "Branch Reference Number*", 70);
            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account Number*", 90);
            AbstractColumn transactionID = ReportBean.getColumn("transactionID", String.class, "Transaction ID", 70);
            AbstractColumn dateOfTransaction = ReportBean.getColumn("dateOfTransaction", String.class, "Date of Transaction*", 70);
            AbstractColumn modeOfTransaction = ReportBean.getColumn("modeOfTransaction", String.class, "Mode of Transaction*", 60);
            AbstractColumn drCr = ReportBean.getColumn("drCr", String.class, "Debit/Credit*", 150);
            AbstractColumn amount = ReportBean.getColumn("amount", BigDecimal.class, "Amount*", 150);
            AbstractColumn currencyOfTransaction = ReportBean.getColumn("currencyOfTransaction", String.class, "Currency of Transaction*", 150);
            AbstractColumn dispositionOfFunds = ReportBean.getColumn("dispositionOfFunds", String.class, "Disposition of Funds", 150);
            AbstractColumn remarks = ReportBean.getColumn("remarks", String.class, "Remarks", 200);

            fastReport.addColumn(recordType);
            width = width + recordType.getWidth();

            fastReport.addColumn(lineNumber);
            width = width + lineNumber.getWidth();

            fastReport.addColumn(branchReferenceNumber);
            width = width + branchReferenceNumber.getWidth();

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(transactionID);
            width = width + transactionID.getWidth();

            fastReport.addColumn(dateOfTransaction);
            width = width + dateOfTransaction.getWidth();

            fastReport.addColumn(modeOfTransaction);
            width = width + modeOfTransaction.getWidth();

            fastReport.addColumn(drCr);
            width = width + drCr.getWidth();

            fastReport.addColumn(amount);
            amount.setStyle(style);
            width = width + amount.getWidth();

            fastReport.addColumn(currencyOfTransaction);
            width = width + currencyOfTransaction.getWidth();

            fastReport.addColumn(dispositionOfFunds);
            width = width + dispositionOfFunds.getWidth();

            fastReport.addColumn(remarks);
            width = width + remarks.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("CBATRN (From Amount " + frAmt + " To " + toAmt + " )");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateArfTrnReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);



            AbstractColumn lineNumber = ReportBean.getColumn("lineNumber", Integer.class, "LineNumber*", 70);
            AbstractColumn reportSerialNum = ReportBean.getColumn("reportSerialNum", String.class, "ReportSerialNum*", 70);
            AbstractColumn branchRefNum = ReportBean.getColumn("branchRefNum", String.class, "BranchRefNum*", 70);
            AbstractColumn accountNumber = ReportBean.getColumn("accountNumber", String.class, "AccountNumber*", 90);
            AbstractColumn dateOfTransaction = ReportBean.getColumn("dateOfTransaction", String.class, "DateOfTransaction", 90);
            AbstractColumn transactionId = ReportBean.getColumn("transactionId", String.class, "TransactionID", 90);
            AbstractColumn transactionMode = ReportBean.getColumn("transactionMode", String.class, "TransactionMode*", 70);
            AbstractColumn debitCredit = ReportBean.getColumn("debitCredit", String.class, "Debit/Credit*", 70);
            AbstractColumn amount = ReportBean.getColumn("amount", BigDecimal.class, "Amount*", 100);
            AbstractColumn currency = ReportBean.getColumn("currency", String.class, "Currency*", 70);
            AbstractColumn productType = ReportBean.getColumn("productType", String.class, "ProductType", 70);
            AbstractColumn identifier = ReportBean.getColumn("identifier", String.class, "Identifier", 70);
            AbstractColumn transactionType = ReportBean.getColumn("transactionType", String.class, "TransactionType", 70);
            AbstractColumn units = ReportBean.getColumn("units", BigDecimal.class, "Units", 70);
            AbstractColumn rate = ReportBean.getColumn("rate", BigDecimal.class, "Rate", 70);
            AbstractColumn dispositionOfFunds = ReportBean.getColumn("dispositionOfFunds", String.class, "DispositionOfFunds", 90);
            AbstractColumn relatedAccountNum = ReportBean.getColumn("relatedAccountNum", String.class, "RelatedAccountNum", 90);
            AbstractColumn relatedInstitutionName = ReportBean.getColumn("relatedInstitutionName", String.class, "RelatedInstitutionName", 90);
            AbstractColumn relatedInstitutionRefNum = ReportBean.getColumn("relatedInstitutionRefNum", String.class, "RelatedInstitutionRefNum", 90);
            AbstractColumn remarks = ReportBean.getColumn("remarks", String.class, "remarks", 150);

            fastReport.addColumn(lineNumber);
            width = width + lineNumber.getWidth();

            fastReport.addColumn(reportSerialNum);
            width = width + reportSerialNum.getWidth();

            fastReport.addColumn(branchRefNum);
            width = width + branchRefNum.getWidth();

            fastReport.addColumn(accountNumber);
            width = width + accountNumber.getWidth();

            fastReport.addColumn(dateOfTransaction);
            width = width + dateOfTransaction.getWidth();

            fastReport.addColumn(transactionId);
            width = width + transactionId.getWidth();

            fastReport.addColumn(transactionMode);
            width = width + transactionMode.getWidth();

            fastReport.addColumn(debitCredit);
            width = width + debitCredit.getWidth();

            fastReport.addColumn(amount);
            amount.setStyle(style);
            width = width + amount.getWidth();

            fastReport.addColumn(currency);
            width = width + currency.getWidth();
//Integer lineNumber;       String reportSerialNum;     String branchRefNum;        String accountNumber;       String dateOfTransaction;
//    String transactionId; String transactionMode;     String debitCredit;         BigDecimal amount;          String currency;
//    String productType;   String identifier;          String transactionType;     BigDecimal units;           BigDecimal rate;
//    String dispositionOfFunds;                        String relatedAccountNum;   String relatedInstitutionName;
//    String relatedInstitutionRefNum;                  String remarks;
            fastReport.addColumn(productType);
            width = width + productType.getWidth();

            fastReport.addColumn(identifier);
            width = width + identifier.getWidth();

            fastReport.addColumn(transactionType);
            width = width + transactionType.getWidth();

            fastReport.addColumn(units);
            units.setStyle(style);
            width = width + units.getWidth();

            fastReport.addColumn(rate);
            rate.setStyle(style);
            width = width + rate.getWidth();

            fastReport.addColumn(dispositionOfFunds);
            width = width + dispositionOfFunds.getWidth();

            fastReport.addColumn(relatedAccountNum);
            width = width + relatedAccountNum.getWidth();

            fastReport.addColumn(relatedInstitutionName);
            width = width + relatedInstitutionName.getWidth();

            fastReport.addColumn(relatedInstitutionRefNum);
            width = width + relatedInstitutionRefNum.getWidth();

            fastReport.addColumn(remarks);
            width = width + remarks.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("ARFTRN (From Amount " + frAmt + " To " + toAmt + " )");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateArfBatReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn lineNumber = ReportBean.getColumn("description", String.class, "Description", 200);
            AbstractColumn reportSerialNum = ReportBean.getColumn("value", String.class, "Value", 300);

            fastReport.addColumn(lineNumber);
            width = width + lineNumber.getWidth();

            fastReport.addColumn(reportSerialNum);
            width = width + reportSerialNum.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("ARFBAT (From Amount " + frAmt + " To " + toAmt + " )");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateArfRptReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn lineNumber = ReportBean.getColumn("lineNumber", Integer.class, "Line Number*", 60);
            AbstractColumn reportSerialNum = ReportBean.getColumn("reportSerialNum", Integer.class, "ReportSerialNum*", 60);
            AbstractColumn originalReportSerialNum = ReportBean.getColumn("originalReportSerialNum", Integer.class, "OriginalReportSerialNum*", 60);
            AbstractColumn mainPersonName = ReportBean.getColumn("mainPersonName", String.class, "MainPersonName", 120);
            AbstractColumn sourceOfAlert = ReportBean.getColumn("sourceOfAlert", String.class, "SourceOfAlert*", 60);
            AbstractColumn alertIndicator1 = ReportBean.getColumn("alertIndicator1", String.class, "AlertIndicator1", 120);
            AbstractColumn alertIndicator2 = ReportBean.getColumn("alertIndicator2", String.class, "AlertIndicator2", 120);
            AbstractColumn alertIndicator3 = ReportBean.getColumn("alertIndicator3", String.class, "AlertIndicator3", 120);
            AbstractColumn suspicionDueToProceedsOfCrime = ReportBean.getColumn("suspicionDueToProceedsOfCrime", String.class, "SuspicionDueToProceedsOfCrime*", 120);
            AbstractColumn suspicionDueToComplexTrans = ReportBean.getColumn("suspicionDueToComplexTrans", String.class, "SuspicionDueToComplexTrans*", 120);
            AbstractColumn suspicionDueToNoEcoRationale = ReportBean.getColumn("suspicionDueToNoEcoRationale", String.class, "SuspicionDueToNoEcoRationale*", 120);
            AbstractColumn suspicionOfFinancingOfTerrorism = ReportBean.getColumn("suspicionOfFinancingOfTerrorism", String.class, "SuspicionOfFinancingOfTerrorism*", 120);
            AbstractColumn attemptedTransaction = ReportBean.getColumn("attemptedTransaction", String.class, "AttemptedTransaction*", 70);
            AbstractColumn groundsOfSuspicion = ReportBean.getColumn("groundsOfSuspicion", String.class, "GroundsOfSuspicion*", 120);
            AbstractColumn detailsOfInvestigations = ReportBean.getColumn("detailsOfInvestigations", String.class, "DetailsOfInvestigations", 125);
            AbstractColumn lEAInformed = ReportBean.getColumn("lEAInformed", String.class, "LEAInformed*", 65);
            AbstractColumn lEADetails = ReportBean.getColumn("lEADetails", String.class, "LEADetails", 60);
            AbstractColumn priorityRating = ReportBean.getColumn("priorityRating", String.class, "PriorityRating*", 70);
            AbstractColumn reportCoverage = ReportBean.getColumn("reportCoverage", String.class, "ReportCoverage*", 60);
            AbstractColumn additionalDocuments = ReportBean.getColumn("additionalDocuments", String.class, "AdditionalDocuments*", 60);

            fastReport.addColumn(lineNumber);
            width = width + lineNumber.getWidth();

            fastReport.addColumn(reportSerialNum);
            width = width + reportSerialNum.getWidth();

            fastReport.addColumn(originalReportSerialNum);
            width = width + originalReportSerialNum.getWidth();

            fastReport.addColumn(mainPersonName);
            width = width + mainPersonName.getWidth();

            fastReport.addColumn(sourceOfAlert);
            width = width + sourceOfAlert.getWidth();

            fastReport.addColumn(alertIndicator1);
            width = width + alertIndicator1.getWidth();

            fastReport.addColumn(alertIndicator2);
            width = width + alertIndicator2.getWidth();

            fastReport.addColumn(alertIndicator3);
            width = width + alertIndicator3.getWidth();

            fastReport.addColumn(suspicionDueToProceedsOfCrime);
            width = width + suspicionDueToProceedsOfCrime.getWidth();

            fastReport.addColumn(suspicionDueToComplexTrans);
            width = width + suspicionDueToComplexTrans.getWidth();

            fastReport.addColumn(suspicionDueToNoEcoRationale);
            width = width + suspicionDueToNoEcoRationale.getWidth();

            fastReport.addColumn(suspicionOfFinancingOfTerrorism);
            width = width + suspicionOfFinancingOfTerrorism.getWidth();

            fastReport.addColumn(attemptedTransaction);
            width = width + attemptedTransaction.getWidth();

            fastReport.addColumn(groundsOfSuspicion);
            width = width + groundsOfSuspicion.getWidth();

            fastReport.addColumn(detailsOfInvestigations);
            width = width + detailsOfInvestigations.getWidth();

            fastReport.addColumn(lEAInformed);
            width = width + lEAInformed.getWidth();

            fastReport.addColumn(lEADetails);
            width = width + lEADetails.getWidth();

            fastReport.addColumn(priorityRating);
            width = width + priorityRating.getWidth();

            fastReport.addColumn(reportCoverage);
            width = width + reportCoverage.getWidth();

            fastReport.addColumn(additionalDocuments);
            width = width + additionalDocuments.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("ARFRPT (From Amount " + frAmt + " To " + toAmt + " )");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateArfBrcReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn lineNumber = ReportBean.getColumn("lineNumber", Integer.class, "Line Number*", 60);
            AbstractColumn branchRefNumType = ReportBean.getColumn("branchRefNumType", String.class, "BranchRefNumType*", 120);
            AbstractColumn branchRefNum = ReportBean.getColumn("branchRefNum", String.class, "BranchRefNum*", 60);
            AbstractColumn branchName = ReportBean.getColumn("branchName", String.class, "BranchName", 120);
            AbstractColumn address = ReportBean.getColumn("address", String.class, "Address*", 180);
            AbstractColumn city = ReportBean.getColumn("city", String.class, "City", 120);
            AbstractColumn stateCode = ReportBean.getColumn("stateCode", String.class, "StateCode*", 120);
            AbstractColumn pinCode = ReportBean.getColumn("pinCode", String.class, "PinCode", 120);
            AbstractColumn countryCode = ReportBean.getColumn("countryCode", String.class, "CountryCode*", 60);
            AbstractColumn telephone = ReportBean.getColumn("telephone", String.class, "Telephone", 120);
            AbstractColumn mobile = ReportBean.getColumn("mobile", String.class, "Mobile", 60);
            AbstractColumn fax = ReportBean.getColumn("fax", String.class, "Fax", 120);
            AbstractColumn branchEmail = ReportBean.getColumn("branchEmail", String.class, "BranchEmail", 150);

            fastReport.addColumn(lineNumber);
            width = width + lineNumber.getWidth();

            fastReport.addColumn(branchRefNumType);
            width = width + branchRefNumType.getWidth();

            fastReport.addColumn(branchRefNum);
            width = width + branchRefNum.getWidth();

            fastReport.addColumn(branchName);
            width = width + branchName.getWidth();

            fastReport.addColumn(address);
            width = width + address.getWidth();

            fastReport.addColumn(city);
            width = width + city.getWidth();

            fastReport.addColumn(stateCode);
            width = width + stateCode.getWidth();

            fastReport.addColumn(pinCode);
            width = width + pinCode.getWidth();

            fastReport.addColumn(countryCode);
            width = width + countryCode.getWidth();

            fastReport.addColumn(telephone);
            width = width + telephone.getWidth();

            fastReport.addColumn(mobile);
            width = width + mobile.getWidth();

            fastReport.addColumn(fax);
            width = width + fax.getWidth();

            fastReport.addColumn(branchEmail);
            width = width + branchEmail.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("ARFBRC (From Amount " + frAmt + " To " + toAmt + " )");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateAirReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn uniqueSrNo = ReportBean.getColumn("uniqueSrNo", Integer.class, "Unique Sr.No", 70);
            AbstractColumn firstName = ReportBean.getColumn("firstName", String.class, "First Name", 150);
            AbstractColumn middleName = ReportBean.getColumn("middleName", String.class, "Middle Name", 100);
            AbstractColumn lastName = ReportBean.getColumn("lastName", String.class, "Last Name", 150);
            AbstractColumn pan = ReportBean.getColumn("pan", String.class, "Pan", 100);
            AbstractColumn governmentNonGovernment = ReportBean.getColumn("governmentNonGovernment", String.class, "Government/non-Government", 120);
            AbstractColumn form60Or61 = ReportBean.getColumn("form60Or61", String.class, "Form 60/61", 100);
            AbstractColumn flatNoOrHouseNo = ReportBean.getColumn("flatNoOrHouseNo", String.class, "Flat No./House No.", 250);
            AbstractColumn floorNo = ReportBean.getColumn("floorNo", String.class, "Floor No", 150);
            AbstractColumn buildingName = ReportBean.getColumn("buildingName", String.class, "Building Name", 150);
            AbstractColumn blockOrSector = ReportBean.getColumn("blockOrSector", String.class, "Block Sector", 150);
            AbstractColumn roadOrStreetOrLocalityColony = ReportBean.getColumn("roadOrStreetOrLocalityColony", String.class, "Road/Street/Locality Colony", 150);
            AbstractColumn city = ReportBean.getColumn("city", String.class, "City", 150);
            AbstractColumn districtCode = ReportBean.getColumn("districtCode", String.class, "District Code", 150);
            AbstractColumn stateCode = ReportBean.getColumn("stateCode", String.class, "State Code", 150);
            AbstractColumn pinCode = ReportBean.getColumn("pinCode", String.class, "PIN Code", 100);
            AbstractColumn jointTransactionPartyCount = ReportBean.getColumn("jointTransactionPartyCount", Integer.class, "Joint Transaction Party count", 70);
            AbstractColumn dateOfTransaction = ReportBean.getColumn("dateOfTransaction", String.class, "Date of Transaction", 70);
            AbstractColumn modeOfTransaction = ReportBean.getColumn("modeOfTransaction", String.class, "Mode of Transaction", 70);
            AbstractColumn transactionAmount = ReportBean.getColumn("transactionAmount", BigDecimal.class, "Transaction Amount", 150);
            AbstractColumn transactionCode = ReportBean.getColumn("transactionCode", String.class, "Transaction Code", 150);
            AbstractColumn branchCode = ReportBean.getColumn("branchCode", String.class, "Branch Code", 60);

            fastReport.addColumn(uniqueSrNo);
            width = width + uniqueSrNo.getWidth();

            fastReport.addColumn(firstName);
            width = width + firstName.getWidth();

            fastReport.addColumn(middleName);
            width = width + middleName.getWidth();

            fastReport.addColumn(lastName);
            width = width + lastName.getWidth();

            fastReport.addColumn(pan);
            width = width + pan.getWidth();

            fastReport.addColumn(governmentNonGovernment);
            width = width + governmentNonGovernment.getWidth();

            fastReport.addColumn(form60Or61);
            width = width + form60Or61.getWidth();

            fastReport.addColumn(flatNoOrHouseNo);
            width = width + flatNoOrHouseNo.getWidth();

            fastReport.addColumn(floorNo);
            width = width + floorNo.getWidth();

            fastReport.addColumn(buildingName);
            width = width + buildingName.getWidth();

            fastReport.addColumn(blockOrSector);
            width = width + blockOrSector.getWidth();

            fastReport.addColumn(roadOrStreetOrLocalityColony);
            width = width + roadOrStreetOrLocalityColony.getWidth();

            fastReport.addColumn(city);
            width = width + city.getWidth();

            fastReport.addColumn(districtCode);
            width = width + districtCode.getWidth();

            fastReport.addColumn(stateCode);
            width = width + stateCode.getWidth();

            fastReport.addColumn(pinCode);
            width = width + pinCode.getWidth();

            fastReport.addColumn(jointTransactionPartyCount);
            width = width + jointTransactionPartyCount.getWidth();

            fastReport.addColumn(dateOfTransaction);
            width = width + dateOfTransaction.getWidth();

            fastReport.addColumn(modeOfTransaction);
            width = width + modeOfTransaction.getWidth();

            fastReport.addColumn(transactionAmount);
            transactionAmount.setStyle(style);
            width = width + transactionAmount.getWidth();

            fastReport.addColumn(transactionCode);
            width = width + transactionCode.getWidth();

            fastReport.addColumn(branchCode);
            width = width + branchCode.getWidth();

            Page page = new Page(2760, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("AIR");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateAir61AReport(String parameter) {

        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn reportSerialNumber = ReportBean.getColumn("reportSerialNumber", Integer.class, "Report Serial Number", 70);
            AbstractColumn originalReportSerialNumber = ReportBean.getColumn("originalReportSerialNumber", Integer.class, "Original Report Serial Number", 70);
            AbstractColumn accountType = ReportBean.getColumn("accountType", String.class, "Account Type", 100);
            AbstractColumn accountNumber = ReportBean.getColumn("accountNumber", String.class, "Account Number", 150);
            AbstractColumn accountHolderName = ReportBean.getColumn("accountHolderName", String.class, "Account Holder Name", 200);
            AbstractColumn accountStatus = ReportBean.getColumn("accountStatus", String.class, "Account Status", 70);
            AbstractColumn branchReferenceNumber = ReportBean.getColumn("branchReferenceNumber", String.class, "Branch Reference Number", 70);
            AbstractColumn branchName = ReportBean.getColumn("branchName", String.class, "Branch Name", 150);
            AbstractColumn branchAddress = ReportBean.getColumn("branchAddress", String.class, "Branch Address", 150);
            AbstractColumn branchCityTown = ReportBean.getColumn("branchCityTown", String.class, "Branch City/Town", 150);
            AbstractColumn branchPostalCode = ReportBean.getColumn("branchPostalCode", String.class, "Branch Postal Code", 70);
            AbstractColumn branchState = ReportBean.getColumn("branchState", String.class, "Branch State", 50);
            AbstractColumn branchCountry = ReportBean.getColumn("branchCountry", String.class, "Branch Country", 50);
            AbstractColumn branchSTDCode = ReportBean.getColumn("branchSTDCode", String.class, "Branch STD Code", 50);
            AbstractColumn branchPhoneNumber = ReportBean.getColumn("branchPhoneNumber", String.class, "Branch Phone Number", 100);
            AbstractColumn branchMobileNumber = ReportBean.getColumn("branchMobileNumber", String.class, "Branch Mobile Number", 100);
            AbstractColumn branchbFaxSTDCode = ReportBean.getColumn("branchbFaxSTDCode", String.class, "Branch Fax STD Code", 50);
            AbstractColumn branchFaxPhoneNo = ReportBean.getColumn("branchFaxPhoneNo", String.class, "Branch Fax Phone No", 100);
            AbstractColumn branchEmail = ReportBean.getColumn("branchEmail", String.class, "Branch Email", 150);
            AbstractColumn branchRemarks = ReportBean.getColumn("branchRemarks", String.class, "Branch Remarks", 100);
            AbstractColumn aggGrossAmountCrCash = ReportBean.getColumn("aggGrossAmountCrCash", BigDecimal.class, "Aggregate gross amount credited to the account in cash", 150);
            AbstractColumn aggGrossAmountDrCash = ReportBean.getColumn("aggGrossAmountDrCash", BigDecimal.class, "Aggregate gross amount debited to the account in cash", 150);
            AbstractColumn amountCrBeforeDemonetization = ReportBean.getColumn("amountCrBeforeDemonetization", BigDecimal.class, "Amount credited to the account before demonetization", 150);
            AbstractColumn amountCrAfterDemonetization = ReportBean.getColumn("amountCrAfterDemonetization", BigDecimal.class, "Amount credited to the account after demonetization", 150);
            AbstractColumn accountRemarks = ReportBean.getColumn("accountRemarks", String.class, "Account Remarks", 50);
            AbstractColumn accountRelationship = ReportBean.getColumn("accountRelationship", String.class, "Account Relationship (PC)", 70);
            AbstractColumn personName = ReportBean.getColumn("personName", String.class, "Person Name (PC)", 200);
            AbstractColumn personType = ReportBean.getColumn("personType", String.class, "Person Type (PC)", 70);
            AbstractColumn customerIdentity = ReportBean.getColumn("customerIdentity", String.class, "Customer Identity (PC)", 100);
            AbstractColumn gender = ReportBean.getColumn("gender", String.class, "Gender (PC)", 50);
            AbstractColumn fatherName = ReportBean.getColumn("fatherName", String.class, "Father’s Name (PC)", 200);
            AbstractColumn pan = ReportBean.getColumn("pan", String.class, "PAN (PC)", 150);
            AbstractColumn aadhaarNumber = ReportBean.getColumn("aadhaarNumber", String.class, "Aadhaar Number (PC)", 150);
            AbstractColumn form60Acknowledgement = ReportBean.getColumn("form60Acknowledgement", String.class, "Form 60 Acknowledgement (PC)", 150);
            AbstractColumn identificationType = ReportBean.getColumn("identificationType", String.class, "Identification Type (PC)", 70);
            AbstractColumn identificationNumber = ReportBean.getColumn("identificationNumber", String.class, "Identification Number (PC)", 150);
            AbstractColumn dobOrIncorporation = ReportBean.getColumn("dobOrIncorporation", String.class, "Date of Birth/ Incorporation (PC)", 150);
            AbstractColumn nationalityOrCountryOfIncorporation = ReportBean.getColumn("nationalityOrCountryOfIncorporation", String.class, "Nationality/Country of Incorporation (PC)", 150);
            AbstractColumn businessOrOccupation = ReportBean.getColumn("businessOrOccupation", String.class, "Business or Occupation (PC)", 50);
            AbstractColumn addressType = ReportBean.getColumn("addressType", String.class, "Address Type (PC-L)", 50);
            AbstractColumn address = ReportBean.getColumn("address", String.class, "Address (PC-L)", 300);
            AbstractColumn cityOrTown = ReportBean.getColumn("cityOrTown", String.class, "City/Town (PC-L)", 150);
            AbstractColumn pinCode = ReportBean.getColumn("pinCode", String.class, "Pin Code (PC-L)", 70);
            AbstractColumn state = ReportBean.getColumn("state", String.class, "State (PC-L)", 70);
            AbstractColumn country = ReportBean.getColumn("country", String.class, "Country (PC-L)", 70);
            AbstractColumn primaryStdCode = ReportBean.getColumn("primaryStdCode", String.class, "Primary STD Code (PC)", 50);
            AbstractColumn primaryTelephoneNumber = ReportBean.getColumn("primaryTelephoneNumber", String.class, "Primary Telephone Number (PC)", 100);
            AbstractColumn primaryMobileNumber = ReportBean.getColumn("primaryMobileNumber", String.class, "Primary Mobile Number (PC)", 100);
            AbstractColumn secondaryStdCode = ReportBean.getColumn("secondaryStdCode", String.class, "Secondary STD Code (PC)", 50);
            AbstractColumn secondaryTelephoneNumber = ReportBean.getColumn("secondaryTelephoneNumber", String.class, "Secondary Telephone Number (PC)", 100);
            AbstractColumn secondaryMobileNumber = ReportBean.getColumn("secondaryMobileNumber", String.class, "Secondary Mobile Number (PC)", 100);
            AbstractColumn email = ReportBean.getColumn("email", String.class, "Email (PC)", 150);
            AbstractColumn remarks = ReportBean.getColumn("remarks", String.class, "Remarks (PC)", 150);

            fastReport.addColumn(reportSerialNumber);
            width = width + reportSerialNumber.getWidth();

            fastReport.addColumn(originalReportSerialNumber);
            width = width + originalReportSerialNumber.getWidth();

            fastReport.addColumn(accountType);
            width = width + accountType.getWidth();

            fastReport.addColumn(accountNumber);
            width = width + accountNumber.getWidth();

            fastReport.addColumn(accountHolderName);
            width = width + accountHolderName.getWidth();

            fastReport.addColumn(accountStatus);
            width = width + accountStatus.getWidth();

            fastReport.addColumn(branchReferenceNumber);
            width = width + branchReferenceNumber.getWidth();

            fastReport.addColumn(branchName);
            width = width + branchName.getWidth();

            fastReport.addColumn(branchAddress);
            width = width + branchAddress.getWidth();

            fastReport.addColumn(branchCityTown);
            width = width + branchCityTown.getWidth();

            fastReport.addColumn(branchPostalCode);
            width = width + branchPostalCode.getWidth();

            fastReport.addColumn(branchState);
            width = width + branchState.getWidth();

            fastReport.addColumn(branchCountry);
            width = width + branchCountry.getWidth();

            fastReport.addColumn(branchSTDCode);
            width = width + branchSTDCode.getWidth();

            fastReport.addColumn(branchPhoneNumber);
            width = width + branchPhoneNumber.getWidth();

            fastReport.addColumn(branchMobileNumber);
            width = width + branchMobileNumber.getWidth();

            fastReport.addColumn(branchbFaxSTDCode);
            branchbFaxSTDCode.setStyle(style);
            width = width + branchbFaxSTDCode.getWidth();

            fastReport.addColumn(branchFaxPhoneNo);
            width = width + branchFaxPhoneNo.getWidth();

            fastReport.addColumn(branchEmail);
            width = width + branchEmail.getWidth();

            fastReport.addColumn(branchRemarks);
            width = width + branchRemarks.getWidth();

            fastReport.addColumn(aggGrossAmountCrCash);
            aggGrossAmountCrCash.setStyle(style);
            width = width + aggGrossAmountCrCash.getWidth();

            fastReport.addColumn(aggGrossAmountDrCash);
            aggGrossAmountDrCash.setStyle(style);
            width = width + aggGrossAmountDrCash.getWidth();

            fastReport.addColumn(amountCrBeforeDemonetization);
            amountCrBeforeDemonetization.setStyle(style);
            width = width + amountCrBeforeDemonetization.getWidth();

            fastReport.addColumn(amountCrAfterDemonetization);
            amountCrAfterDemonetization.setStyle(style);
            width = width + amountCrAfterDemonetization.getWidth();

            fastReport.addColumn(accountRemarks);
            width = width + accountRemarks.getWidth();

            fastReport.addColumn(accountRelationship);
            width = width + accountRelationship.getWidth();

            fastReport.addColumn(personName);
            width = width + personName.getWidth();

            fastReport.addColumn(personType);
            width = width + personType.getWidth();

            fastReport.addColumn(customerIdentity);
            width = width + customerIdentity.getWidth();

            fastReport.addColumn(gender);
            width = width + gender.getWidth();

            fastReport.addColumn(fatherName);
            width = width + fatherName.getWidth();

            fastReport.addColumn(pan);
            width = width + pan.getWidth();

            fastReport.addColumn(aadhaarNumber);
            width = width + aadhaarNumber.getWidth();

            fastReport.addColumn(form60Acknowledgement);
            width = width + form60Acknowledgement.getWidth();

            fastReport.addColumn(identificationType);
            width = width + identificationType.getWidth();

            fastReport.addColumn(identificationNumber);
            width = width + identificationNumber.getWidth();

            fastReport.addColumn(dobOrIncorporation);
            width = width + dobOrIncorporation.getWidth();

            fastReport.addColumn(nationalityOrCountryOfIncorporation);
            width = width + nationalityOrCountryOfIncorporation.getWidth();

            fastReport.addColumn(businessOrOccupation);
            width = width + businessOrOccupation.getWidth();

            fastReport.addColumn(addressType);
            width = width + addressType.getWidth();

            fastReport.addColumn(address);
            width = width + address.getWidth();

            fastReport.addColumn(cityOrTown);
            width = width + cityOrTown.getWidth();

            fastReport.addColumn(pinCode);
            width = width + pinCode.getWidth();

            fastReport.addColumn(state);
            width = width + state.getWidth();

            fastReport.addColumn(country);
            width = width + country.getWidth();

            fastReport.addColumn(primaryStdCode);
            width = width + primaryStdCode.getWidth();

            fastReport.addColumn(primaryTelephoneNumber);
            width = width + primaryTelephoneNumber.getWidth();

            fastReport.addColumn(primaryMobileNumber);
            width = width + primaryMobileNumber.getWidth();

            fastReport.addColumn(secondaryStdCode);
            width = width + secondaryStdCode.getWidth();

            fastReport.addColumn(secondaryTelephoneNumber);
            width = width + secondaryTelephoneNumber.getWidth();

            fastReport.addColumn(secondaryMobileNumber);
            width = width + secondaryMobileNumber.getWidth();

            fastReport.addColumn(email);
            width = width + email.getWidth();

            fastReport.addColumn(remarks);
            width = width + remarks.getWidth();

            Page page = new Page(5870, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            if (parameter.equalsIgnoreCase("Y")) {
                fastReport.setTitle("FORM-61A_" + this.getFrAmt() + "_" + this.getToAmt() + "_" + ymdFormat.format(dmyFormat.parse(this.getCalFromDate())) + "_" + ymdFormat.format(dmyFormat.parse(this.getCaltoDate())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;

    }

    public FastReportBuilder genrateAir61AForTDAndPODDReport(String parameter) {

        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn reportSerialNumber = ReportBean.getColumn("reportSerialNumber", Integer.class, "Report Serial Number", 70);
            AbstractColumn originalReportSerialNumber = ReportBean.getColumn("originalReportSerialNumber", Integer.class, "Original Report Serial Number", 70);
            AbstractColumn personName = ReportBean.getColumn("personName", String.class, "Person Name", 200);
            AbstractColumn personType = ReportBean.getColumn("personType", String.class, "Person Type", 70);
            AbstractColumn customerIdentity = ReportBean.getColumn("customerIdentity", String.class, "Customer Identity", 100);
            AbstractColumn gender = ReportBean.getColumn("gender", String.class, "Gender", 50);
            AbstractColumn fatherName = ReportBean.getColumn("fatherName", String.class, "Father’s Name", 200);
            AbstractColumn pan = ReportBean.getColumn("pan", String.class, "PAN", 150);
            AbstractColumn aadhaarNumber = ReportBean.getColumn("aadhaarNumber", String.class, "Aadhaar Number", 150);
            AbstractColumn form60Acknowledgement = ReportBean.getColumn("form60Acknowledgement", String.class, "Form 60 Acknowledgment", 150);
            AbstractColumn identificationType = ReportBean.getColumn("identificationType", String.class, "Identification Type", 70);
            AbstractColumn identificationNumber = ReportBean.getColumn("identificationNumber", String.class, "Identification Number", 150);
            AbstractColumn dobOrIncorporation = ReportBean.getColumn("dobOrIncorporation", String.class, "Date of birth/Incorporation", 150);
            AbstractColumn nationalityOrCountryOfIncorporation = ReportBean.getColumn("nationalityOrCountryOfIncorporation", String.class, "Nationality/Country of Incorporation", 150);
            AbstractColumn addressType = ReportBean.getColumn("addressType", String.class, "AddressType", 50);
            AbstractColumn address = ReportBean.getColumn("address", String.class, "Fix Address", 300);
            AbstractColumn cityOrTown = ReportBean.getColumn("cityOrTown", String.class, "City/Town", 150);
            AbstractColumn pinCode = ReportBean.getColumn("pinCode", String.class, "PostCode", 70);
            AbstractColumn state = ReportBean.getColumn("state", String.class, "State", 70);
            AbstractColumn country = ReportBean.getColumn("country", String.class, "CountryCode", 70);
            AbstractColumn businessOrOccupation = ReportBean.getColumn("businessOrOccupation", String.class, "Business or occupation", 50);
            AbstractColumn primaryStdCode = ReportBean.getColumn("primaryStdCode", String.class, "Primary STD Code", 50);
            AbstractColumn primaryTelephoneNumber = ReportBean.getColumn("primaryTelephoneNumber", String.class, "Primary Phone No", 100);
            AbstractColumn primaryMobileNumber = ReportBean.getColumn("primaryMobileNumber", String.class, "Primary Mobile No", 100);
            AbstractColumn secondaryStdCode = ReportBean.getColumn("secondaryStdCode", String.class, "Secondary STD Code", 50);
            AbstractColumn secondaryTelephoneNumber = ReportBean.getColumn("secondaryTelephoneNumber", String.class, "Secondary Phone No", 100);
            AbstractColumn secondaryMobileNumber = ReportBean.getColumn("secondaryMobileNumber", String.class, "Secondary Mobile No", 100);
            AbstractColumn email = ReportBean.getColumn("email", String.class, "Email", 150);
            AbstractColumn remarks = ReportBean.getColumn("remarks", String.class, "Remarks", 150);
            AbstractColumn productType = ReportBean.getColumn("productType", String.class, "Product Type", 150);

            AbstractColumn aggGrossAmountReciveFromPer = ReportBean.getColumn("aggGrossAmountReciveFromPer", BigDecimal.class, "Aggregate gross amount received from the person", 150);
            AbstractColumn aggGrossAmtReciveFromPerInCash = ReportBean.getColumn("aggGrossAmtReciveFromPerInCash", BigDecimal.class, "Aggregate gross amount received from the person in cash", 150);
            AbstractColumn aggGrossAmtPaidToPer = ReportBean.getColumn("aggGrossAmtPaidToPer", BigDecimal.class, "Aggregate gross amount paid to the person", 150);

            AbstractColumn tranSummryRemarks = ReportBean.getColumn("tranSummryRemarks", String.class, "TransactionSummry Remarks", 150);
            AbstractColumn productIdentifier = ReportBean.getColumn("productIdentifier", String.class, "Product Identifier", 150);
            AbstractColumn lastDtofTran = ReportBean.getColumn("lastDtofTran", String.class, "Last Date of Transaction", 150);

            AbstractColumn amtReceivedFromPer = ReportBean.getColumn("aggGrossAmountReciveFromPer", BigDecimal.class, "Amount Received From Person", 150);
            AbstractColumn cashAmtRecvFrPer = ReportBean.getColumn("aggGrossAmtReciveFromPerInCash", BigDecimal.class, "Cash Amount Received From Person", 150);
            AbstractColumn amtPaidToPer = ReportBean.getColumn("aggGrossAmtPaidToPer", BigDecimal.class, "Amount Paid To Person", 150);

            AbstractColumn relatedAccNo = ReportBean.getColumn("relatedAccNo", String.class, "Related Account Number", 150);
            AbstractColumn relatedInstName = ReportBean.getColumn("relatedInstName", String.class, "Related Institute Name", 150);
            AbstractColumn relatedInstRefNo = ReportBean.getColumn("relatedInstRefNo", String.class, "Related Institute Ref Number", 150);
            AbstractColumn finTransactionDtlsRemarks = ReportBean.getColumn("finTransactionDtlsRemarks", String.class, "Financial TransactionDtls Remarks", 150);

            fastReport.addColumn(reportSerialNumber);
            width = width + reportSerialNumber.getWidth();

            fastReport.addColumn(originalReportSerialNumber);
            width = width + originalReportSerialNumber.getWidth();

            fastReport.addColumn(personName);
            width = width + personName.getWidth();

            fastReport.addColumn(personType);
            width = width + personType.getWidth();

            fastReport.addColumn(customerIdentity);
            width = width + customerIdentity.getWidth();

            fastReport.addColumn(gender);
            width = width + gender.getWidth();

            fastReport.addColumn(fatherName);
            width = width + fatherName.getWidth();

            fastReport.addColumn(pan);
            width = width + pan.getWidth();

            fastReport.addColumn(aadhaarNumber);
            width = width + aadhaarNumber.getWidth();

            fastReport.addColumn(form60Acknowledgement);
            width = width + form60Acknowledgement.getWidth();

            fastReport.addColumn(identificationType);
            width = width + identificationType.getWidth();

            fastReport.addColumn(identificationNumber);
            width = width + identificationNumber.getWidth();

            fastReport.addColumn(dobOrIncorporation);
            width = width + dobOrIncorporation.getWidth();

            fastReport.addColumn(nationalityOrCountryOfIncorporation);
            width = width + nationalityOrCountryOfIncorporation.getWidth();


            fastReport.addColumn(addressType);
            width = width + addressType.getWidth();

            fastReport.addColumn(address);
            width = width + address.getWidth();

            fastReport.addColumn(cityOrTown);
            width = width + cityOrTown.getWidth();

            fastReport.addColumn(pinCode);
            width = width + pinCode.getWidth();

            fastReport.addColumn(state);
            width = width + state.getWidth();

            fastReport.addColumn(country);
            width = width + country.getWidth();

            fastReport.addColumn(businessOrOccupation);
            width = width + businessOrOccupation.getWidth();

            fastReport.addColumn(primaryStdCode);
            width = width + primaryStdCode.getWidth();

            fastReport.addColumn(primaryTelephoneNumber);
            width = width + primaryTelephoneNumber.getWidth();

            fastReport.addColumn(primaryMobileNumber);
            width = width + primaryMobileNumber.getWidth();

            fastReport.addColumn(secondaryStdCode);
            width = width + secondaryStdCode.getWidth();

            fastReport.addColumn(secondaryTelephoneNumber);
            width = width + secondaryTelephoneNumber.getWidth();

            fastReport.addColumn(secondaryMobileNumber);
            width = width + secondaryMobileNumber.getWidth();

            fastReport.addColumn(email);
            width = width + email.getWidth();

            fastReport.addColumn(remarks);
            width = width + remarks.getWidth();

            fastReport.addColumn(productType);
            width = width + productType.getWidth();

            fastReport.addColumn(aggGrossAmountReciveFromPer);
            width = width + aggGrossAmountReciveFromPer.getWidth();

            fastReport.addColumn(aggGrossAmtReciveFromPerInCash);
            width = width + aggGrossAmtReciveFromPerInCash.getWidth();

            fastReport.addColumn(aggGrossAmtPaidToPer);
            width = width + aggGrossAmtPaidToPer.getWidth();



            fastReport.addColumn(tranSummryRemarks);
            width = width + tranSummryRemarks.getWidth();

            fastReport.addColumn(productIdentifier);
            width = width + productIdentifier.getWidth();

            fastReport.addColumn(lastDtofTran);
            width = width + lastDtofTran.getWidth();
//
//            fastReport.addColumn(aggGrossAmountReciveFromPer);
//            width = width + aggGrossAmountReciveFromPer.getWidth();
//
//            fastReport.addColumn(aggGrossAmtReciveFromPerInCash);
//            width = width + aggGrossAmtReciveFromPerInCash.getWidth();
//
//            fastReport.addColumn(aggGrossAmtPaidToPer);
//            width = width + aggGrossAmtPaidToPer.getWidth



            fastReport.addColumn(amtReceivedFromPer);
            width = width + amtReceivedFromPer.getWidth();

            fastReport.addColumn(cashAmtRecvFrPer);
            width = width + cashAmtRecvFrPer.getWidth();

            fastReport.addColumn(amtPaidToPer);
            width = width + amtPaidToPer.getWidth();


            fastReport.addColumn(relatedAccNo);
            width = width + relatedAccNo.getWidth();

            fastReport.addColumn(relatedInstName);
            width = width + relatedInstName.getWidth();

            fastReport.addColumn(relatedInstRefNo);
            width = width + relatedInstRefNo.getWidth();

            fastReport.addColumn(finTransactionDtlsRemarks);
            width = width + finTransactionDtlsRemarks.getWidth();

            Page page = new Page(5870, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            if (parameter.equalsIgnoreCase("Y")) {
                fastReport.setTitle("FORM-61A_" + this.getFrAmt() + "_" + this.getToAmt() + "_" + ymdFormat.format(dmyFormat.parse(this.getCalFromDate())) + "_" + ymdFormat.format(dmyFormat.parse(this.getCaltoDate())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;

    }

    public void refreshAction() {
        setHyperPanel("none");
        setCommandLink("none");
        setInvisibleMultiplier("none");
        CtrPojo ctrRep = new CtrPojo();
        this.setReportType("S");
        this.setDdAcNature("0");
        setCalFromDate(dmyFormat.format(dt));
        setCaltoDate(dmyFormat.format(dt));
        this.setFrAmt("0");
        this.setToAmt("0");
        this.disableAmt = false;
        this.disableCsvbuttom = false;
        this.setMessage("");
    }

    public String exitAction() {
        refreshAction();
        return "case1";
    }
}
