/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.TransferBatchDeletionFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.deaf.PrizmCardHolderPojo;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PrizmReport extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String showAcno = "none";
    private String showDt = "none";
    private String acno;
    private String frdt;
    private String todt;
    private CommonReportMethodsRemote reportFacade;
    private TransferBatchDeletionFacadeRemote remote;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public PrizmReport() {
        try {
            remote = (TransferBatchDeletionFacadeRemote) ServiceLocator.getInstance().lookup("TransferBatchDeletionFacade");
            reportFacade = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            branchList = new ArrayList<SelectItem>();
            reportTypeList = new ArrayList<SelectItem>();

            reportTypeList.add(new SelectItem("0", "--Select--"));
            reportTypeList.add(new SelectItem("D", "Date Wise Registration"));
            reportTypeList.add(new SelectItem("I", "Individual A/c Detail"));

            branchList.add(new SelectItem("0", "--Select--"));

            List list = reportFacade.getAlphacodeAllAndBranch(getOrgnBrCode());
            if (list.isEmpty()) {
                this.setMessage("There is no branch to show the report.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                String brncode = ele.get(1).toString().trim().length() < 2 ? "0" + ele.get(1).toString().trim()
                        : ele.get(1).toString().trim();
                branchList.add(new SelectItem(brncode, ele.get(0).toString().trim()));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void reportTypeAction() {
        this.setMessage("");
        try {
            if (this.branch == null || this.branch.equals("0")) {
                this.setMessage("Please select Branch.");
                return;
            }
            if (this.reportType == null || this.reportType.equals("0")) {
                this.setMessage("Please select Report Type.");
                return;
            }
            this.showAcno = "none";
            this.showDt = "none";
            if (this.reportType.equals("I")) {
                this.showAcno = "";
            } else if (this.reportType.equals("D")) {
                this.showDt = "";
                this.setFrdt(getTodayDate());
                this.setTodt(getTodayDate());
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        this.setMessage("");
        String reportName = "Prizm Card Detail Report";
        try {
            if (validateField()) {
                Map fillParams = new HashMap();
                List brNameAndAddList = reportFacade.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", reportName);

                List<PrizmCardHolderPojo> dataList = new ArrayList<PrizmCardHolderPojo>();
                if (this.reportType.equals("I")) {
                    dataList = remote.getPrizmAcDetails(this.acno, "", "", this.reportType, this.branch);
                    fillParams.put("pReportDate", dmy.format(new Date()));
                    fillParams.put("pAcno", this.acno);
                } else if (this.reportType.equals("D")) {
                    dataList = remote.getPrizmAcDetails("", this.frdt, this.todt, this.reportType, this.branch);
                    fillParams.put("pReportDate", this.frdt + " to " + this.todt);
                    fillParams.put("pAcno", "");
                }
                if (dataList.isEmpty()) {
                    this.setMessage("There is no data to show.");
                    return;
                }
                if (this.reportType.equals("I")) {
                    new ReportBean().jasperReport("AtmCardDetailsForInd", "text/html", new JRBeanCollectionDataSource(dataList), fillParams, reportName);
                } else if (this.reportType.equals("D")) {
                    new ReportBean().jasperReport("AtmCardDetails", "text/html", new JRBeanCollectionDataSource(dataList), fillParams, reportName);
                }
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean validateField() {
        try {
            if (this.branch == null || this.branch.equals("0")) {
                this.setMessage("Please select Branch.");
                return false;
            }
            if (this.reportType == null || this.reportType.equals("0")) {
                this.setMessage("Please select Report Type.");
                return false;
            }
//            this.showAcno = "none";
//            this.showDt = "none";
            if (this.reportType.equals("I")) {
//                this.showAcno = "";
                if (this.acno == null || this.acno.equals("") || this.acno.length() != 12) {
                    this.setMessage("Please fill proper A/c Number.");
                    return false;
                }
                remote.checkPrizmRegisteredAc(this.acno);

//                String alphaCode = reportFacade.getAlphacodeByBrncode(getOrgnBrCode());
                if (!ftsRemote.getCurrentBrnCode(this.acno).equalsIgnoreCase(this.branch)) {
                    this.setMessage("Please fill proper branch A/c Number.");
                    return false;
                }
            } else if (this.reportType.equals("D")) {
//                this.showDt = "";
                Validator validator = new Validator();
                if (!validator.validateDate_dd_mm_yyyy(this.frdt)) {
                    this.setMessage("Please fill proper From Date.");
                    return false;
                }
                if (!validator.validateDate_dd_mm_yyyy(this.todt)) {
                    this.setMessage("Please fill proper To Date.");
                    return false;
                }
                if (dmy.parse(this.frdt).compareTo(dmy.parse(this.todt)) > 0) {
                    this.setMessage("From Date can not be greater than To Date.");
                    return false;
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setBranch("0");
        this.setReportType("0");
        this.setAcno("");
        this.setFrdt(getTodayDate());
        this.setTodt(getTodayDate());
        this.showAcno = "none";
        this.showDt = "none";
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    //Getter And Setter
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

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public String getShowAcno() {
        return showAcno;
    }

    public void setShowAcno(String showAcno) {
        this.showAcno = showAcno;
    }

    public String getShowDt() {
        return showDt;
    }

    public void setShowDt(String showDt) {
        this.showDt = showDt;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getFrdt() {
        return frdt;
    }

    public void setFrdt(String frdt) {
        this.frdt = frdt;
    }

    public String getTodt() {
        return todt;
    }

    public void setTodt(String todt) {
        this.todt = todt;
    }
}
