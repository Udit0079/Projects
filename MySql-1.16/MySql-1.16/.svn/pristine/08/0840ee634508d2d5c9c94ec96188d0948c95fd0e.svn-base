/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.clg;

import com.cbs.dto.report.OwClgPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.OtherTransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class OwNpciReturnBean extends BaseBean {

    private String errorMessage;
    private String branch;
    private String clgMode;
    private String registerDt;
    private String status;
    private List<SelectItem> branchList;
    private List<SelectItem> clgModeList;
    private List<SelectItem> registerDtList;
    private List<SelectItem> statusList;
    private CommonReportMethodsRemote commonRemote;
    private OtherTransactionManagementFacadeRemote otherTxnRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public OwNpciReturnBean() {
        try {
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherTxnRemote = (OtherTransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup("OtherTransactionManagementFacade");

            branchList = new ArrayList<>();
            clgModeList = new ArrayList<>();
            registerDtList = new ArrayList<>();
            statusList = new ArrayList<>();

            List list = commonRemote.getAlphacodeBasedOnOrgnBranch(getOrgnBrCode());
            if (list.isEmpty()) {
                this.setErrorMessage("Please define branchmaster detail.");
                return;
            }
            branchList.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                branchList.add(new SelectItem(ele.get(1).toString().trim().length() < 2 ? "0"
                        + ele.get(1).toString().trim() : ele.get(1).toString().trim(), ele.get(0).toString()));
            }
            String alphaCode = commonRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (alphaCode.equalsIgnoreCase("HO")) {
                branchList.add(new SelectItem("ALL"));
            }

            clgModeList.add(new SelectItem("0", "--Select--"));
            list = otherTxnRemote.getClearingOption();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vecForcircleTyp = (Vector) list.get(i);
                    clgModeList.add(new SelectItem(vecForcircleTyp.get(0).toString(), vecForcircleTyp.get(1).toString()));
                }
            }

            registerDtList.add(new SelectItem("0", "--Select--"));

            statusList.add(new SelectItem("0", "--Select--"));
            statusList.add(new SelectItem("ALL"));
            statusList.add(new SelectItem("PASS"));
            statusList.add(new SelectItem("RETURN"));
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void populateRegisterDt() {
        this.setErrorMessage("");
        registerDtList = new ArrayList<>();
        if (this.branch == null || this.branch.equals("0") || this.branch.equals("")) {
            this.setErrorMessage("Please Select Branch.");
            return;
        }
        if (this.clgMode == null || this.clgMode.equals("0") || this.clgMode.equals("")) {
            this.setErrorMessage("Please Select Clearing Mode.");
            return;
        }
        try {
            List<String> dateList = otherTxnRemote.loadRegisterDate(this.clgMode, this.branch);
            registerDtList.add(new SelectItem("0", "--Select--"));
            for (String dt : dateList) {
                registerDtList.add(new SelectItem(dt));
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void btnHtmlAction() {
        this.setErrorMessage("");
        try {
            if (this.branch == null || this.branch.equals("0") || this.branch.equals("")) {
                this.setErrorMessage("Please Select Branch.");
                return;
            }
            if (this.clgMode == null || this.clgMode.equals("0") || this.clgMode.equals("")) {
                this.setErrorMessage("Please Select Clearing Mode.");
                return;
            }
            if (this.registerDt == null || this.registerDt.equals("0") || this.registerDt.equals("")) {
                this.setErrorMessage("Please Select Register Date.");
                return;
            }
            if (this.status == null || this.status.equals("0") || this.status.equals("")) {
                this.setErrorMessage("Please Select Status.");
                return;
            }
            List<OwClgPojo> dataList = otherTxnRemote.getOwNpciReturnData(ymd.format(dmy.parse(this.registerDt)),
                    this.clgMode, this.branch, this.status);
            if (dataList.isEmpty()) {
                this.setErrorMessage("There is no data to show the report.");
                return;
            }
            Map map = new HashMap();
            String reportName = "NPCI Outward Return Report";
            List brNameAndAddList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            map.put("pBankName", brNameAndAddList.get(0).toString());
            map.put("pBankAddress", brNameAndAddList.get(1).toString());
            map.put("pPrintedBy", getUserName());
            map.put("pReportName", reportName);
            map.put("pReportType", this.status);
            new ReportBean().jasperReport("npciowreturnreport", "text/html", new JRBeanCollectionDataSource(dataList), map, reportName);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void btRefreshAction() {
        this.setErrorMessage("");
        this.setBranch("0");
        this.setClgMode("0");
        this.setRegisterDt("0");
        this.setStatus("0");
    }

    public String btnExitAction() {
        btRefreshAction();
        return "case1";
    }

    //Getter And Setter
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getClgMode() {
        return clgMode;
    }

    public void setClgMode(String clgMode) {
        this.clgMode = clgMode;
    }

    public String getRegisterDt() {
        return registerDt;
    }

    public void setRegisterDt(String registerDt) {
        this.registerDt = registerDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public List<SelectItem> getClgModeList() {
        return clgModeList;
    }

    public void setClgModeList(List<SelectItem> clgModeList) {
        this.clgModeList = clgModeList;
    }

    public List<SelectItem> getRegisterDtList() {
        return registerDtList;
    }

    public void setRegisterDtList(List<SelectItem> registerDtList) {
        this.registerDtList = registerDtList;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }
}
