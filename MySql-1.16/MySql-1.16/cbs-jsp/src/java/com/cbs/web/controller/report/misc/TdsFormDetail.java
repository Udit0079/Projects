/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.InterestFdReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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
 * @author Administrator
 */
public class TdsFormDetail extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String fdAcType;
    private List<SelectItem> fdAcTypeList;
    private String repType;
    private List<SelectItem> repTypeList;
    private String finYear;
    private String newFinYear;
    private Boolean finYearDisable;
    private CommonReportMethodsRemote commonBeanRemote;
    private MiscReportFacadeRemote remoteFacade;
    private OtherReportFacadeRemote otherBeanRemote;
    private List<InterestFdReportPojo> reportList = new ArrayList<InterestFdReportPojo>();

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

    public String getFdAcType() {
        return fdAcType;
    }

    public void setFdAcType(String fdAcType) {
        this.fdAcType = fdAcType;
    }

    public List<SelectItem> getFdAcTypeList() {
        return fdAcTypeList;
    }

    public void setFdAcTypeList(List<SelectItem> fdAcTypeList) {
        this.fdAcTypeList = fdAcTypeList;
    }

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

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public String getNewFinYear() {
        return newFinYear;
    }

    public void setNewFinYear(String newFinYear) {
        this.newFinYear = newFinYear;
    }

    public Boolean getFinYearDisable() {
        return finYearDisable;
    }

    public void setFinYearDisable(Boolean finYearDisable) {
        this.finYearDisable = finYearDisable;
    }

    public TdsFormDetail() {
        try {
            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            otherBeanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");

            List brnLst = new ArrayList();
            brnLst = commonBeanRemote.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            List acCodeList = otherBeanRemote.getAcctType();
            fdAcTypeList = new ArrayList<SelectItem>();
            fdAcTypeList.add(new SelectItem("0", "--Select--"));
            fdAcTypeList.add(new SelectItem("A", "ALL"));
            for (Object element : acCodeList) {
                Vector vector = (Vector) element;
                fdAcTypeList.add(new SelectItem(vector.get(0).toString(), vector.get(0).toString()));
            }

            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("0", "A/C With Form Submitted"));
            repTypeList.add(new SelectItem("1", "A/C Without Form Submitted"));
            repTypeList.add(new SelectItem("2", "A/C With PAN No."));

            this.finYearDisable = true;

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onReportOption() {
        if (repType.equalsIgnoreCase("0")) {
            this.finYearDisable = false;
        } else {
            this.finYearDisable = true;
        }
    }

    public void getNewfYear() {
        this.newFinYear = finYear;
    }

    public void printReport() {
        this.setMessage("");
        String branchName = "";
        String address = "";
        try {
            List brnAddrList = new ArrayList();
            brnAddrList = commonBeanRemote.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            if (fdAcType == null || fdAcType.equalsIgnoreCase("0")) {
                setMessage("Please select A/c. Type!!!");
                return;
            }
            if (repType.equalsIgnoreCase("0")) {
                if (finYear == null || finYear.equalsIgnoreCase("")) {
                    setMessage("Please fill proper 4 digits financial year !");
                    return;
                }
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pRepType", repType);
            if (repType.equalsIgnoreCase("0")) {
                fillParams.put("pReprtName", "A/C With TDS Form Submitted");
                fillParams.put("pFinYear", "Financial Year : " + finYear + " - " + (Integer.parseInt(finYear.substring(2, 4)) + 1));
            } else if (repType.equalsIgnoreCase("1")) {
                fillParams.put("pReprtName", "A/C Without TDS Form Submitted");
            } else {
                fillParams.put("pReprtName", "A/C With PAN No.");
            }
            reportList = remoteFacade.getTdsFormDetails(this.getBranch(), this.fdAcType, this.repType, finYear);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            if (repType.equalsIgnoreCase("0")) {
                new ReportBean().jasperReport("Tds15G15HDetailReport", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "TdsFormDetail");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else {
                new ReportBean().jasperReport("TdsFormDetailReport", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "TdsFormDetail");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }


        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void viewPdfReport() {
        this.setMessage("");
        String branchName = "";
        String address = "";
        try {
            List brnAddrList = new ArrayList();
            brnAddrList = commonBeanRemote.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            if (fdAcType == null || fdAcType.equalsIgnoreCase("0")) {
                setMessage("Please select A/c. Type!!!");
                return;
            }

            if (repType.equalsIgnoreCase("0")) {
                if (finYear == null || finYear.equalsIgnoreCase("")) {
                    setMessage("Please fill proper 4 digits financial year !");
                    return;
                }
            }

            Map fillParams = new HashMap();
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pRepType", repType);
            if (repType.equalsIgnoreCase("0")) {
                fillParams.put("pReprtName", "A/C With TDS Form Submitted");
                fillParams.put("pFinYear", "Financial Year : " + finYear + " - " + (Integer.parseInt(finYear.substring(2, 4)) + 1));
            } else if (repType.equalsIgnoreCase("1")) {
                fillParams.put("pReprtName", "A/C Without TDS Form Submitted");
            } else {
                fillParams.put("pReprtName", "A/C With PAN No.");
            }
            reportList = remoteFacade.getTdsFormDetails(this.getBranch(), this.fdAcType, this.repType, finYear);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }
            if (repType.equalsIgnoreCase("0")) {
                new ReportBean().openPdf("Tds 15G15H Detail Report", "Tds15G15HDetailReport", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else {
                new ReportBean().openPdf("TdsFormDetailReport", "TdsFormDetailReport", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "TdsFormDetail");

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshAction() {
        this.setFdAcType("0");
        this.setRepType("0");
        this.setMessage("");
        this.setFinYear("");
        this.setNewFinYear("");
        this.finYearDisable = true;
    }

    public String exitAction() {
        refreshAction();
        return "case1";
    }
}
