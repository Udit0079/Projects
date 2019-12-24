/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.ho.DepositProvisionCalPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class DepositProvisionCalculation extends BaseBean {

    private String errMsg;
    private String branch;
    private String reportType;
    private String acNature;
    private String acType;
    private String asOnDt;
    private List<SelectItem> branchList;
    private List<SelectItem> reportTypeList;
    private List<SelectItem> acNatureList;
    private List<SelectItem> acTypeList;
    private CommonReportMethodsRemote common;
    private DDSReportFacadeRemote ddsRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public DepositProvisionCalculation() {
        branchList = new ArrayList<SelectItem>();
        reportTypeList = new ArrayList<SelectItem>();
        acNatureList = new ArrayList<SelectItem>();
        acTypeList = new ArrayList<SelectItem>();
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            initData();
            refresh();
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public void initData() {
        try {
            reportTypeList.add(new SelectItem("--Select--"));
            reportTypeList.add(new SelectItem("S", "Summary"));
            reportTypeList.add(new SelectItem("D", "Detail"));

            List list = common.getAlphacodeBasedOnBranch(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
            }

            acNatureList.add(new SelectItem("--Select--"));
            list = ddsRemote.getDepositNatures();
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                acNatureList.add(new SelectItem(element.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public void reportTypeAction() {
        this.setErrMsg("");
        try {
            this.setAcNature("--Select--");
            acTypeList = new ArrayList<SelectItem>();
            this.setAsOnDt(getTodayDate());
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public void getAcTypeForNature() {
        this.setErrMsg("");
        acTypeList = new ArrayList<SelectItem>();
        try {
            acTypeList.add(new SelectItem("All"));
            List list = ddsRemote.getAccountCodeByNature(this.acNature);
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
                String reportName = "";
                Map fillParams = new HashMap();
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBranchAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pPrintedBy", getUserName());

                List<DepositProvisionCalPojo> reportList = new ArrayList<DepositProvisionCalPojo>();
                if (reportType.equalsIgnoreCase("D")) {
                    reportName = "Detail Provision Calculation";
                    fillParams.put("pReportName", reportName);

                    reportList = ddsRemote.getDetailProvisionCalculation(this.branch, this.acNature, this.acType, ymd.format(dmy.parse(this.asOnDt)));

                    new ReportBean().openPdf("Report_Provision_Calculation", "Report_Provission_Detail", new JRBeanCollectionDataSource(reportList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpRequest().getSession();
                    httpSession.setAttribute("ReportName", reportName);
                } else if (reportType.equalsIgnoreCase("S")) {
                    reportName = "Summary Provision Calculation";
                    fillParams.put("pReportName", reportName);

                    reportList = ddsRemote.getSummaryProvisionCalculation(this.branch, this.acNature, this.acType, ymd.format(dmy.parse(this.asOnDt)));
                    new ReportBean().openPdf("Report_Provision_Calculation", "Report_Procal_Summary", new JRBeanCollectionDataSource(reportList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpRequest().getSession();
                    httpSession.setAttribute("ReportName", reportName);
                }
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }
    /*Html Format*/

    public void htmlReport() {
        try{
            if (validateField()) {
                String reportName = "";
                Map fillParams = new HashMap();
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBranchAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pPrintedBy", getUserName());

                List<DepositProvisionCalPojo> reportList = new ArrayList<DepositProvisionCalPojo>();
                if (reportType.equalsIgnoreCase("D")) {
                    reportName = "Detail Provision Calculation";
                    fillParams.put("pReportName", reportName);

                    reportList = ddsRemote.getDetailProvisionCalculation(this.branch, this.acNature, this.acType, ymd.format(dmy.parse(this.asOnDt)));

                    new ReportBean().jasperReport("Report_Provission_Detail", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, reportName);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                } else if (reportType.equalsIgnoreCase("S")) {
                    reportName = "Summary Provision Calculation";
                    fillParams.put("pReportName", reportName);

                    reportList = ddsRemote.getSummaryProvisionCalculation(this.branch, this.acNature, this.acType, ymd.format(dmy.parse(this.asOnDt)));
                    
                    new ReportBean().jasperReport("Report_Procal_Summary", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, reportName);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                }
            }
        }catch(Exception ex){
            this.setErrMsg(ex.getMessage());
        }
    }

    public boolean validateField() {
        if (this.reportType == null || this.reportType.equals("--Select--")) {
            this.setErrMsg("Please select Report Type.");
            return false;
        }
        if (this.acNature == null || this.acNature.equals("--Select--")) {
            this.setErrMsg("Please select Account Nature.");
            return false;
        }
        if (this.acType == null || this.acType.equals("")) {
            this.setErrMsg("Please select Account Type.");
            return false;
        }
        if (this.asOnDt == null || this.asOnDt.equals("")) {
            this.setErrMsg("Please fill From Date.");
            return false;
        }
        if (!new Validator().validateDate_dd_mm_yyyy(this.asOnDt)) {
            this.setErrMsg("Please fill proper From Date.");
            return false;
        }
        return true;
    }

    public void refresh() {
        this.setErrMsg("");
        this.setReportType("--Select--");
        this.setAcNature("--Select--");
        acTypeList = new ArrayList<SelectItem>();
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

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getAsOnDt() {
        return asOnDt;
    }

    public void setAsOnDt(String asOnDt) {
        this.asOnDt = asOnDt;
    }
}
