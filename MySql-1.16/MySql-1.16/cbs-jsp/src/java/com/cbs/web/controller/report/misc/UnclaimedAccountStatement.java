/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.UnclaimedAccountStatementPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class UnclaimedAccountStatement extends BaseBean {

    private String errorMsg;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String acNature;
    private List<SelectItem> acNatureList;
    private String acType;
    private List<SelectItem> acTypeList;
    private String asOnDate;
    private String frYear;
    private String toYear;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private boolean disableMarkBtn;
    private String roiVisible = "none";
    private String savingRoi;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private MiscReportFacadeRemote remoteFacade;
    private FtsPostingMgmtFacadeRemote fts = null;
    private DDSReportFacadeRemote ddsRemote;
    List<UnclaimedAccountStatementPojo> reportList;

    public UnclaimedAccountStatement() {
        try {
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("Select", "--Select--"));
            reportTypeList.add(new SelectItem("S", "Summary"));
            reportTypeList.add(new SelectItem("D", "Detail"));

            asOnDate = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            getacNature();
            reportList = new ArrayList<UnclaimedAccountStatementPojo>();
            this.disableMarkBtn = true;
            this.savingRoi = "";
            this.roiVisible = "none";
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void getacNature() {
        try {
            acNatureList = new ArrayList<SelectItem>();
            acNatureList.add(new SelectItem("0", "--Select--"));
            List acNtureList = ddsRemote.getAccountNatureClassification("'C','B'");
            if (!acNtureList.isEmpty()) {
                for (int i = 0; i < acNtureList.size(); i++) {
                    Vector vec = (Vector) acNtureList.get(i);
                    acNatureList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void getAcTypeByAcNature() {
        try {
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("ALL", "ALL"));
            //List actCodeList = ddsRemote.getAccountCodeByClassificationAndNature("C", this.acNature);
            List actCodeList = ddsRemote.getAcctCodeByNature(this.acNature);
            if (!actCodeList.isEmpty()) {
                for (int i = 0; i < actCodeList.size(); i++) {
                    Vector vec = (Vector) actCodeList.get(i);
                    acTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void viewReport() {
        String report = "Unclaimed Account Detail Report";
        String report1 = "Unclaimed Account Summary Report";
        this.disableMarkBtn = true;
        this.savingRoi = "";
        this.roiVisible = "none";
        reportList = new ArrayList<UnclaimedAccountStatementPojo>();
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (acType.equalsIgnoreCase("S")) {
                setErrorMsg("Please select account type !");
                return;
            }
            if (frYear.equalsIgnoreCase("") || frYear == null) {
                setErrorMsg("Please fill from year !");
                return;
            }
            if (toYear.equalsIgnoreCase("") || toYear == null) {
                setErrorMsg("Please fill to year !");
                return;
            }
            Matcher fYearCheck = p.matcher(this.frYear);
            if (!fYearCheck.matches()) {
                setErrorMsg("Enter the numeric values for account no (0-9) !");
                return;
            }
            Matcher toYearCheck = p.matcher(this.toYear);
            if (!toYearCheck.matches()) {
                setErrorMsg("Enter the numeric values for account no (0-9) !");
                return;
            }
            if (asOnDate == null || asOnDate.equalsIgnoreCase("")) {
                setErrorMsg("Please fill As on date");
                return;
            }
            if (!Validator.validateDate(asOnDate)) {
                setErrorMsg("Please select Proper As on date ");
                return;
            }
            if (dmy.parse(asOnDate).after(getDate())) {
                setErrorMsg("As on date should be less than current date !");
                return;
            }
            if (Integer.parseInt(toYear) < Integer.parseInt(frYear)) {
                setErrorMsg("To Year Should be greater than from Year !");
                return;
            }

            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            if (reportType.equalsIgnoreCase("D")) {
                fillParams.put("pReportName", report);
            } else {
                fillParams.put("pReportName", report1);
            }
            fillParams.put("pReportDt", this.asOnDate);
            String asOnDt = ymd.format(dmy.parse(asOnDate));
            if (reportType.equalsIgnoreCase("D")) {
                reportList = remoteFacade.getUnclaimedAccountStatement(branchCode, acType, asOnDt, frYear, toYear, acNature);
            } else {
                reportList = remoteFacade.getUnclaimedAccountDetails(branchCode, acType, asOnDt, frYear, toYear, acNature);
            }
            if (reportList.isEmpty()) {
                this.disableMarkBtn = true;
                this.savingRoi = "";
                this.roiVisible = "none";
                throw new ApplicationException("Data does not exist !");
            }
            if (reportType.equalsIgnoreCase("D")) {
                if (acNature.equalsIgnoreCase("FD") || acNature.equalsIgnoreCase("MS")) {
                    new ReportBean().jasperReport("UnclaimedAccountStatementfdms", "text/html",
                            new JRBeanCollectionDataSource(reportList), fillParams, "Unclaimed Account Statement");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                } else {
                    new ReportBean().jasperReport("UnclaimedAccountStatement", "text/html",
                            new JRBeanCollectionDataSource(reportList), fillParams, "Unclaimed Account Statement");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                }
            } else {
                new ReportBean().jasperReport("UnclaimedAccountSummary", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Unclaimed Account Statement");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
//            refresh();
            if (fts.existInParameterInfoReport("UNCLAIMED-POSTING") && this.reportType.equalsIgnoreCase("D")
                    && !this.branchCode.equalsIgnoreCase("0A") && !this.acType.equals("ALL")) {
                this.disableMarkBtn = false;
                this.savingRoi = "";
                this.roiVisible = "none";
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    this.roiVisible = "";
                }
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void markUnClaimedAction() {
        try {
            if (fts.existInParameterInfoReport("UNCLAIMED-POSTING") && this.reportType.equalsIgnoreCase("D")
                    && !this.branchCode.equalsIgnoreCase("0A") && !this.acType.equals("ALL")) {
                if (reportList.isEmpty()) {
                    this.setErrorMsg("There is no a/c to post.");
                    return;
                }
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    String result = validateRoi();
                    if (!result.equals("true")) {
                        this.setErrorMsg(result);
                        return;
                    }
                }
                //Unclaimed Posting.
                String msg = remoteFacade.markUnClaimed(this.branchCode, this.acNature, reportList,
                        getTodayDate(), getUserName(), "R", this.acType, this.savingRoi);
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                this.setErrorMsg("Un-Claimed marking has been done successfully.");
                this.disableMarkBtn = true;
                this.savingRoi = "";
                this.roiVisible = "none";
            } else {
                this.setErrorMsg("Unclaimed posting is not allowed.");
                return;
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public String validateRoi() {
        String result = "true";
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            Matcher m = p.matcher(this.savingRoi);
            if (this.savingRoi == null || this.savingRoi.equals("")) {
                return result = "Saving roi can not be blank.";
            } else if (m.matches() != true) {
                return result = "Please fill proper Saving Roi.";
            } else if (this.savingRoi.equalsIgnoreCase("0") || this.savingRoi.equalsIgnoreCase("0.0")) {
                return result = "Saving roi can not be zero.";
            } else if (new BigDecimal(this.savingRoi).compareTo(new BigDecimal("0")) == -1) {
                return result = "Saving roi can not be negative.";
            }
            if (this.savingRoi.contains(".")) {
                if (this.savingRoi.indexOf(".") != this.savingRoi.lastIndexOf(".")) {
                    return result = "Invalid Saving Roi. Please fill the Saving Roi correctly.";
                } else if (this.savingRoi.substring(savingRoi.indexOf(".") + 1).length() > 2) {
                    return result = "Please fill the Saving Roi upto two decimal places.";
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
        return result;
    }

    public void refresh() {
        setErrorMsg("");
        asOnDate = dmy.format(new Date());
        setAcType("S");
        setFrYear("");
        setToYear("");
        setReportType("Select");
        this.disableMarkBtn = true;
        this.savingRoi = "";
        this.roiVisible = "none";
    }

    public String exitAction() {
        refresh();
        return "case1";
    }

    //Getter And Setter
    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
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

    public String getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFrYear() {
        return frYear;
    }

    public void setFrYear(String frYear) {
        this.frYear = frYear;
    }

    public String getToYear() {
        return toYear;
    }

    public void setToYear(String toYear) {
        this.toYear = toYear;
    }

    public boolean isDisableMarkBtn() {
        return disableMarkBtn;
    }

    public void setDisableMarkBtn(boolean disableMarkBtn) {
        this.disableMarkBtn = disableMarkBtn;
    }

    public String getRoiVisible() {
        return roiVisible;
    }

    public void setRoiVisible(String roiVisible) {
        this.roiVisible = roiVisible;
    }

    public String getSavingRoi() {
        return savingRoi;
    }

    public void setSavingRoi(String savingRoi) {
        this.savingRoi = savingRoi;
    }
}
