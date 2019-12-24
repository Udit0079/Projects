/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.pojo.AadharDetailSummaryPojo;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class AadharDetailSummary extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String repOption;
    private List<SelectItem> repOptionList;
    private String ftDt;
    private String toDt;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private DDSReportFacadeRemote ddsRepotRemote;

    public AadharDetailSummary() {
        try {
            ftDt = dmy.format(date);
            toDt = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsRepotRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");

            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
            repOptionList = new ArrayList<>();
            repOptionList.add(new SelectItem("0", "--Select--"));
            repOptionList.add(new SelectItem("D", "Detail"));
            repOptionList.add(new SelectItem("S", "Summary"));
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void viewReport() {
        setMessage("");
        try {
            String branchName = "", address = "", report = "Aadhar Link With Account Summary";
            List brnAddrList = new ArrayList();
            List<AadharDetailSummaryPojo> reportList = ddsRepotRemote.getAadharDetailSummaryData(branch, ymd.format(dmy.parse(ftDt)), ymd.format(dmy.parse(toDt)), repOption);
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            if (repOption == null || repOption.equalsIgnoreCase("0")) {
                setMessage("Please select Report Option!!!");
                return;
            }
            if (ftDt == null || ftDt.equalsIgnoreCase("")) {
                setMessage("Please fill from Date");
                return;
            }
            if (!Validator.validateDate(ftDt)) {
                setMessage("Please select Proper from date ");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill to Date");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setMessage("Please select Proper to Date");
                return;
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReprtName", report);
            fillParams.put("pReportDate", ftDt + " To " + toDt);
            fillParams.put("pPrintDate", getTodayDate());
            fillParams.put("pPrintedBy", getUserName());
            if (repOption.equalsIgnoreCase("S")) {
                new ReportBean().jasperReport("AadharLinkWithAcnoSummary", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "Aadhar Link With Account");
            } else {
                new ReportBean().jasperReport("AadharLinkingWithAcnoDetail", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "Aadhar Link With Account");
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void pdfDownLoad() {
        setMessage("");
        try {
            String branchName = "", address = "", report = "Aadhar Link With Account Summary";
            List brnAddrList = new ArrayList();
            List<AadharDetailSummaryPojo> reportList = ddsRepotRemote.getAadharDetailSummaryData(branch, ymd.format(dmy.parse(ftDt)), ymd.format(dmy.parse(toDt)), repOption);
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            if (repOption == null || repOption.equalsIgnoreCase("0")) {
                setMessage("Please select Report Option!!!");
                return;
            }
            if (ftDt == null || ftDt.equalsIgnoreCase("")) {
                setMessage("Please fill from Date");
                return;
            }
            if (!Validator.validateDate(ftDt)) {
                setMessage("Please select Proper from date ");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill to Date");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setMessage("Please select Proper to Date");
                return;
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReprtName", report);
            fillParams.put("pReportDate", ftDt + " To " + toDt);
            fillParams.put("pPrintDate", getTodayDate());
            fillParams.put("pPrintedBy", getUserName());

            if (repOption.equalsIgnoreCase("S")) {
                new ReportBean().openPdf("Aadhar Link With Account Summary", "AadharLinkWithAcnoSummary", new JRBeanCollectionDataSource(reportList), fillParams);
            } else {
                new ReportBean().openPdf("Aadhar Link With Account Summary", "AadharLinkingWithAcnoDetail", new JRBeanCollectionDataSource(reportList), fillParams);
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Aadhar Link With Account");


        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        setMessage("");
        setRepOption("0");
        ftDt = dmy.format(date);
        toDt = dmy.format(date);
    }

    public String btnExitAction() {
        return "case1";
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

    public String getFtDt() {
        return ftDt;
    }

    public void setFtDt(String ftDt) {
        this.ftDt = ftDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getRepOption() {
        return repOption;
    }

    public void setRepOption(String repOption) {
        this.repOption = repOption;
    }

    public List<SelectItem> getRepOptionList() {
        return repOptionList;
    }

    public void setRepOptionList(List<SelectItem> repOptionList) {
        this.repOptionList = repOptionList;
    }
}
