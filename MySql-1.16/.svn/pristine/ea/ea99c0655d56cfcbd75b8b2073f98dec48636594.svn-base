/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.ho.ReportProfilePojo;
import com.cbs.dto.report.ho.SortedByAmount;
import com.cbs.dto.report.ho.SortedByPeriod;
import com.cbs.dto.report.ho.SortedByRoi;
import com.cbs.dto.report.ho.SortedBySlabDescription;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.SortedByAcType;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class ReportProfile extends BaseBean {

    private String errMsg;
    private String branch;
    private String reportBasedOn;
    private String from;
    private String to;
    private String noOfSlab;
    private String acNature;
    private String acType;
    private String reportType;
    private String reportBase;
    private String asOnDt;
    private String classification;
    private List<SelectItem> branchList;
    private List<SelectItem> reportBasedOnList;
    private List<SelectItem> acNatureList;
    private List<SelectItem> acTypeList;
    private List<SelectItem> reportTypeList;
    private List<SelectItem> classificationList;
    private List<SelectItem> reportBaseList;
    private CommonReportMethodsRemote common;
    private DDSReportFacadeRemote ddsRemote;
    private TdReceiptManagementFacadeRemote RemoteCode;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public ReportProfile() {
        branchList = new ArrayList<SelectItem>();
        reportBasedOnList = new ArrayList<SelectItem>();
        acNatureList = new ArrayList<SelectItem>();
        acTypeList = new ArrayList<SelectItem>();
        reportTypeList = new ArrayList<SelectItem>();
        classificationList = new ArrayList<SelectItem>();
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            reportBaseList = new ArrayList<SelectItem>();
            reportBaseList.add(new SelectItem("S", "--Select--"));
            reportBaseList.add(new SelectItem("Ob", "Outstanding Balance"));
            reportBaseList.add(new SelectItem("Sa", "Sanction Amount"));
            initData();
            refresh();
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    private void initData() {
        try {
            reportBasedOnList.add(new SelectItem("R", "Roi"));
            reportBasedOnList.add(new SelectItem("P", "Period"));
            reportBasedOnList.add(new SelectItem("A", "Amount"));

            reportTypeList.add(new SelectItem("0", "--Select--"));
            reportTypeList.add(new SelectItem("S", "Summary"));
            reportTypeList.add(new SelectItem("D", "Detail"));

            classificationList.add(new SelectItem("0", "--Select--"));
            classificationList.add(new SelectItem("'C','B','D'", "All"));
            classificationList.add(new SelectItem("'C','B'", "Deposits"));
            classificationList.add(new SelectItem("'D','B'", "Advances"));

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

//            List list = common.getAlphacodeBasedOnBranch(getOrgnBrCode());
//            for (int i = 0; i < list.size(); i++) {
//                Vector vtr = (Vector) list.get(i);
//                branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
//            }

        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public void reportTypeAction() {
        this.setErrMsg("");
        try {
            if (this.reportType == null
                    || this.reportType.equalsIgnoreCase("0")) {
                this.setErrMsg("Please Select Report Type.");
                return;
            }
            this.setFrom("");
            this.setTo("");
            this.setNoOfSlab("");
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public void classificationAction() {
        this.setErrMsg("");
        acNatureList = new ArrayList<SelectItem>();
        try {
            if (this.classification == null
                    || this.classification.equalsIgnoreCase("0")) {
                this.setErrMsg("Please Select A/c Classification.");
                return;
            } else {
                acNatureList.add(new SelectItem("ALL"));
                List list = ddsRemote.getAccountNatureClassification(this.classification);
                for (int i = 0; i < list.size(); i++) {
                    Vector element = (Vector) list.get(i);
                    acNatureList.add(new SelectItem(element.get(0).toString()));
                }
                getAcTypeForNature();
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public void getAcTypeForNature() {
        acTypeList = new ArrayList<SelectItem>();
        try {
            acTypeList.add(new SelectItem("All"));
            List list = ddsRemote.getAccountCodeByClassificationAndNature(this.classification, this.acNature);
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                acTypeList.add(new SelectItem(element.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    /*Pdf Format*/
    public void pdfReport() {
        this.setErrMsg("");
        try {
            if (validateField()) {
                String reportName = "", pRepType = "";
                if (reportBase.equalsIgnoreCase("Ob")) {
                    pRepType = "Outstanding Balance";
                } else {
                    pRepType = "Sanction Amount";
                }
                Map fillParams = new HashMap();
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBranchAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDt", this.asOnDt);
                fillParams.put("pColumn", this.reportBasedOn);
                fillParams.put("pHeader", pRepType);
                fillParams.put("pClacification", this.classification);

                List<ReportProfilePojo> reportList = new ArrayList<ReportProfilePojo>();
                if (reportType.equalsIgnoreCase("D")) {
                    reportName = "Detail Profile";
                    fillParams.put("pReportName", reportName);

                    reportList = ddsRemote.getDetailReportProfile(this.branch, this.classification, this.acNature, this.acType, this.reportBasedOn,
                            Double.parseDouble(this.from), Double.parseDouble(this.to), Double.parseDouble(this.noOfSlab),
                            ymd.format(dmy.parse(this.asOnDt)), reportBase);
                    if (reportList.isEmpty()) {
                        this.setErrMsg("There is no data to print.");
                        return;
                    }
//                    for (int i = 0; i < reportList.size(); i++) {
//                        ReportProfilePojo pojo = reportList.get(i);
//                        System.out.println("Acno is-->" + pojo.getAcno());
//                        System.out.println("Name is-->" + pojo.getName());
//                        System.out.println("Out is-->" + pojo.getOutstanding());
//                        System.out.println("Roi is-->" + pojo.getRoi());
//                        System.out.println("Period is-->" + pojo.getPeriod());
//                        System.out.println("Slab Desc is-->" + pojo.getSlabDescription() + "\n");
//                    }
                } else if (reportType.equalsIgnoreCase("S")) {
                    reportName = "Summary Profile";
                    fillParams.put("pReportName", reportName);

                    reportList = ddsRemote.getSummaryReportProfile(this.branch, this.classification, this.acNature, this.acType, this.reportBasedOn,
                            Double.parseDouble(this.from), Double.parseDouble(this.to), Double.parseDouble(this.noOfSlab),
                            ymd.format(dmy.parse(this.asOnDt)), reportBase);
                    if (reportList.isEmpty()) {
                        this.setErrMsg("There is no data to print.");
                        return;
                    }
//                    for (int i = 0; i < reportList.size(); i++) {
//                        ReportProfilePojo pojo = reportList.get(i);
//                        System.out.println("Slab Description-->" + pojo.getSlabDescription());
//                        System.out.println("AcType-->" + pojo.getAcType());
//                        System.out.println("AcType Desc is-->" + pojo.getAcCodeDesc());
//                        System.out.println("No Of A/c-->" + pojo.getNoOfAc());
//                        System.out.println("Total Outstand-->" + pojo.getOutstanding() + "\n");
//                    }
                }
                if (reportType.equalsIgnoreCase("D")) {
                    if (reportBasedOn.equalsIgnoreCase("R")) {
                        ComparatorChain chObj = new ComparatorChain();
                        chObj.addComparator(new SortedByRoi());
                        Collections.sort(reportList, chObj);
                    } else if (reportBasedOn.equalsIgnoreCase("P")) {
                        ComparatorChain chObj = new ComparatorChain();
                        chObj.addComparator(new SortedBySlabDescription());
                        chObj.addComparator(new SortedByPeriod());
                        Collections.sort(reportList, chObj);

                    } else if (reportBasedOn.equalsIgnoreCase("A")) {
                        ComparatorChain chObj = new ComparatorChain();
                        chObj.addComparator(new SortedBySlabDescription());
                        chObj.addComparator(new SortedByAmount());
                        Collections.sort(reportList, chObj);
                    }

                    new ReportBean().openPdf("Report_Profile_Detail_" + ymd.format(dmy.parse(getTodayDate())), "Report_Profile_Detail", new JRBeanCollectionDataSource(reportList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpRequest().getSession();
                    httpSession.setAttribute("ReportName", reportName);
                } else if (reportType.equalsIgnoreCase("S")) {
                    if (reportBasedOn.equalsIgnoreCase("R")) {
                        ComparatorChain chObj = new ComparatorChain();
                        chObj.addComparator(new SortedBySlabDescription());
                        chObj.addComparator(new SortedByRoi());
                        Collections.sort(reportList, chObj);
                    } else if (reportBasedOn.equalsIgnoreCase("P")) {
                        ComparatorChain chObj = new ComparatorChain();
                        chObj.addComparator(new SortedBySlabDescription());
                        chObj.addComparator(new SortedByPeriod());
                        Collections.sort(reportList, chObj);

                    } else if (reportBasedOn.equalsIgnoreCase("A")) {
                        ComparatorChain chObj = new ComparatorChain();
                        chObj.addComparator(new SortedBySlabDescription());
                        Collections.sort(reportList, chObj);
                    }
                    new ReportBean().openPdf("Report_ProFiles_" + ymd.format(dmy.parse(getTodayDate())), "Report_ProFiles", new JRBeanCollectionDataSource(reportList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpRequest().getSession();
                    httpSession.setAttribute("ReportName", reportName);
                }
                refresh();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setErrMsg(ex.getMessage());
        }
    }

    /*Html Format*/
    public void htmlReport() {
        this.setErrMsg("");
        try {
            if (validateField()) {
                String reportName = "", pRepType = "";
                if (reportBase.equalsIgnoreCase("Ob")) {
                    pRepType = "Outstanding Balance";
                } else {
                    pRepType = "Sanction Amount";
                }
                Map fillParams = new HashMap();
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBranchAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDt", this.asOnDt);
                fillParams.put("pColumn", this.reportBasedOn);
                fillParams.put("pHeader", pRepType);
                fillParams.put("pClacification", this.classification);

                List<ReportProfilePojo> reportList = new ArrayList<ReportProfilePojo>();
                if (reportType.equalsIgnoreCase("D")) {
                    reportName = "Detail Profile";
                    fillParams.put("pReportName", reportName);

                    reportList = ddsRemote.getDetailReportProfile(this.branch, this.classification, this.acNature, this.acType, this.reportBasedOn,
                            Double.parseDouble(this.from), Double.parseDouble(this.to), Double.parseDouble(this.noOfSlab),
                            ymd.format(dmy.parse(this.asOnDt)), reportBase);
                    if (reportList.isEmpty()) {
                        this.setErrMsg("There is no data to print.");
                        return;
                    }
                } else if (reportType.equalsIgnoreCase("S")) {
                    reportName = "Summary Profile";
                    fillParams.put("pReportName", reportName);

                    reportList = ddsRemote.getSummaryReportProfile(this.branch, this.classification, this.acNature, this.acType, this.reportBasedOn,
                            Double.parseDouble(this.from), Double.parseDouble(this.to), Double.parseDouble(this.noOfSlab),
                            ymd.format(dmy.parse(this.asOnDt)), reportBase);
                    if (reportList.isEmpty()) {
                        this.setErrMsg("There is no data to print.");
                        return;
                    }
                }
                if (reportType.equalsIgnoreCase("D")) {
                    if (reportBasedOn.equalsIgnoreCase("R")) {
                        ComparatorChain chObj = new ComparatorChain();
                        chObj.addComparator(new SortedByRoi());
                        Collections.sort(reportList, chObj);
                    } else if (reportBasedOn.equalsIgnoreCase("P")) {
                        ComparatorChain chObj = new ComparatorChain();
                        chObj.addComparator(new SortedBySlabDescription());
                        chObj.addComparator(new SortedByPeriod());
                        Collections.sort(reportList, chObj);

                    } else if (reportBasedOn.equalsIgnoreCase("A")) {
                        ComparatorChain chObj = new ComparatorChain();
                        chObj.addComparator(new SortedBySlabDescription());
                        chObj.addComparator(new SortedByAmount());
                        Collections.sort(reportList, chObj);
                    }

                    new ReportBean().jasperReport("Report_Profile_Detail", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, reportName);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                } else if (reportType.equalsIgnoreCase("S")) {
                    if (reportBasedOn.equalsIgnoreCase("R")) {
                        ComparatorChain chObj = new ComparatorChain();
                        chObj.addComparator(new SortedBySlabDescription());
                        chObj.addComparator(new SortedByRoi());
                        Collections.sort(reportList, chObj);
                    } else if (reportBasedOn.equalsIgnoreCase("P")) {
                        ComparatorChain chObj = new ComparatorChain();
                        chObj.addComparator(new SortedBySlabDescription());
                        chObj.addComparator(new SortedByPeriod());
                        Collections.sort(reportList, chObj);

                    } else if (reportBasedOn.equalsIgnoreCase("A")) {
                        ComparatorChain chObj = new ComparatorChain();
                        chObj.addComparator(new SortedBySlabDescription());
                        Collections.sort(reportList, chObj);
                    }
                    new ReportBean().jasperReport("Report_Profile_Detail_Summary", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, reportName);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
//                    new ReportBean().jasperReport("Report_ProFiles", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, reportName);
//                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                }
                refresh();
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public boolean validateField() {
        Pattern intPattern = Pattern.compile("[0-9]*");
        if (this.reportType == null || this.reportType.equalsIgnoreCase("0")) {
            this.setErrMsg("Please Select Report Type.");
            return false;
        }
        if (this.reportBase == null || this.reportBase.equalsIgnoreCase("S")) {
            this.setErrMsg("Please Select Report Base.");
            return false;
        }
        if (this.classification == null || this.classification.equalsIgnoreCase("0")) {
            this.setErrMsg("Please Select A/c Classification.");
            return false;
        }
        if (this.acNature == null || this.acNature.equalsIgnoreCase("--Select--")) {
            this.setErrMsg("Please Select Account Nature.");
            return false;
        }
        if (this.acType == null || this.acType.equals("") || this.acType.length() == 0) {
            this.setErrMsg("Please Select Account Type.");
            return false;
        }
        if (this.asOnDt == null || this.asOnDt.equals("")) {
            this.setErrMsg("Please Fill As On Date.");
            return false;
        }
        if (!new Validator().validateDate_dd_mm_yyyy(this.asOnDt)) {
            this.setErrMsg("Please Fill Proper As On Date.");
            return false;
        }
        if (this.from == null || this.to == null || this.from.equals("") || this.to.equals("")
                || this.from.length() == 0 || this.to.length() == 0) {
            this.setErrMsg("Please Fill From And/OR To.");
            return false;
        }
        Matcher m = intPattern.matcher(this.from);
//        if (!m.matches()) {
//            this.setErrMsg("Only numeric values are allowed for From.");
//            return false;
//        }
        if (Double.parseDouble(this.from) < 0.0) {
            this.setErrMsg("From can not be less than or equal to zero.");
            return false;
        }
//            m = intPattern.matcher(this.to);
//            if (!m.matches()) {
//                this.setErrMsg("Only numeric values are allowed for To.");
//                return false;
//            }
        if (Double.parseDouble(this.to) <= 0.0) {
            this.setErrMsg("To can not be less than or equal to zero.");
            return false;
        }
        if (Double.parseDouble(this.from) > Double.parseDouble(this.to)) {
            this.setErrMsg("From can not be greater than To.");
            return false;
        }
        if (Double.parseDouble(this.to) <= Double.parseDouble(this.from)) {
            this.setErrMsg("To can not be less than or equal to From.");
            return false;
        }
        if (this.noOfSlab == null || this.noOfSlab.equals("")) {
            this.setErrMsg("Please Fill No Of Slab.");
            return false;
        }
        m = intPattern.matcher(this.noOfSlab);
        if (!m.matches()) {
            this.setErrMsg("Only numeric values are allowed for No Of Slab.");
            return false;
        }
//            int divisionResult = (Integer.parseInt(this.to) - Integer.parseInt(this.from)) % Integer.parseInt(this.noOfSlab);
//        if (divisionResult != 0) {
//            this.setErrMsg("Difference of To and From should be divided by No Of Slab.");
//            return false;
//        }

        if (this.acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                || (this.acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) && this.classification.equals("C"))) {
            if (this.reportBasedOn.equals("P")) {
                this.setErrMsg("Period filter is not allowed for Saving And Current(Deposit).");
                return false;
            }
        }
        return true;
    }

    public void refresh() {
        this.setErrMsg("");
        this.setClassification("0");
        this.setReportType("0");
        this.setAcNature("ALL");
        acTypeList = new ArrayList<SelectItem>();
        this.setReportBasedOn("R");
        this.setFrom("");
        this.setTo("");
        this.setNoOfSlab("");
        this.setAsOnDt(getTodayDate());
    }

    public String exit() {
        refresh();
        return "case1";
    }

    /*Getter And Setter*/
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
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

    public String getReportBasedOn() {
        return reportBasedOn;
    }

    public void setReportBasedOn(String reportBasedOn) {
        this.reportBasedOn = reportBasedOn;
    }

    public List<SelectItem> getReportBasedOnList() {
        return reportBasedOnList;
    }

    public void setReportBasedOnList(List<SelectItem> reportBasedOnList) {
        this.reportBasedOnList = reportBasedOnList;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(String asOnDt) {
        this.asOnDt = asOnDt;
    }

    public String getNoOfSlab() {
        return noOfSlab;
    }

    public void setNoOfSlab(String noOfSlab) {
        this.noOfSlab = noOfSlab;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public List<SelectItem> getClassificationList() {
        return classificationList;
    }

    public void setClassificationList(List<SelectItem> classificationList) {
        this.classificationList = classificationList;
    }

    public String getReportBase() {
        return reportBase;
    }

    public void setReportBase(String reportBase) {
        this.reportBase = reportBase;
    }

    public List<SelectItem> getReportBaseList() {
        return reportBaseList;
    }

    public void setReportBaseList(List<SelectItem> reportBaseList) {
        this.reportBaseList = reportBaseList;
    }
}
