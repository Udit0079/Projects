/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.IMPSTxnParameterPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
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
public class SarvatraIMPSTxnReprot extends BaseBean {

    private String errorMsg;
    private String branch;
    private List<SelectItem> branchList;
    private String process;
    private List<SelectItem> processList;
    private String mode;
    private List<SelectItem> modeList;
    private String frmDt;
    private String toDt;
    private MiscReportFacadeRemote remote;
    private CommonReportMethodsRemote common;
    private List<IMPSTxnParameterPojo> reportList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Date dt = new Date();

    public SarvatraIMPSTxnReprot() {

        try {
            setFrmDt(dmy.format(dt));
            setToDt(dmy.format(dt));
            remote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            processList = new ArrayList<SelectItem>();
            processList.add(new SelectItem("0", "--Select--"));
            processList.add(new SelectItem("1", "Balance Inquery"));
            processList.add(new SelectItem("2", "Mini Statement"));
            processList.add(new SelectItem("3", "Cheque Book"));
            processList.add(new SelectItem("4", "Statement Request"));
            processList.add(new SelectItem("5", "NEFT"));
            processList.add(new SelectItem("6", "Intra-transfer"));
            processList.add(new SelectItem("7", "Intra-IMPS P2P Transfer"));
            processList.add(new SelectItem("8", "Intra-IMPS P2A Transfer"));
            processList.add(new SelectItem("9", "Outward IMPS P2P Transfer"));
            processList.add(new SelectItem("10", "Inward IMPS P2P Transfer"));
            processList.add(new SelectItem("11", "Outward IMPS P2A Transfer"));
            processList.add(new SelectItem("12", "Inward IMPS P2A Transfer"));

            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("0", "Normal"));
            modeList.add(new SelectItem("1", "Reversal"));

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void viewReport() {
        try {
            if (validate()) {
                String report = "SARVATRA IMPS TXN REPORT";
                Map fillParams = new HashMap();
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                fillParams.put("pReportName", report);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", frmDt + " to " + toDt);
                fillParams.put("pPrintDate", getTodayDate());
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                String processType = "", modeType = "";
                if (process.equalsIgnoreCase("1")) {
                    processType = "Balance Inquery";
                } else if (process.equalsIgnoreCase("2")) {
                    processType = "Mini Statement";
                } else if (process.equalsIgnoreCase("3")) {
                    processType = "Cheque Book";
                } else if (process.equalsIgnoreCase("4")) {
                    processType = "Statement Request";
                } else if (process.equalsIgnoreCase("5")) {
                    processType = "NEFT";
                } else if (process.equalsIgnoreCase("6")) {
                    processType = "Intra-transfer";
                } else if (process.equalsIgnoreCase("7")) {
                    processType = "Intra-IMPS P2P Transfer";
                } else if (process.equalsIgnoreCase("8")) {
                    processType = "Intra-IMPS P2A Transfer";
                } else if (process.equalsIgnoreCase("9")) {
                    processType = "Outward IMPS P2P Transfer";
                } else if (process.equalsIgnoreCase("10")) {
                    processType = "Inward IMPS P2P Transfer";
                } else if (process.equalsIgnoreCase("11")) {
                    processType = "Outward IMPS P2A Transfer";
                } else if (process.equalsIgnoreCase("12")) {
                    processType = "Inward IMPS P2A Transfer";
                }
                if (mode.equalsIgnoreCase("0")) {
                    modeType = "Normal";
                } else {
                    modeType = "Reversal";
                }
                fillParams.put("pProcessType", processType);
                fillParams.put("pModeType", modeType);
                reportList = remote.getSarvatraImpsTxnParameter(process, mode, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), branch);
                if (reportList.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                new ReportBean().jasperReport("impsTxnReport", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "IMPS TXN REPORT");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void pdfDownLoad() {
        try {
            if (validate()) {
                String report = "SARVATRA IMPS TXN REPORT";
                Map fillParams = new HashMap();
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                fillParams.put("pReportName", report);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDate", frmDt + " to " + toDt);
                fillParams.put("pPrintDate", getTodayDate());
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                String processType = "", modeType = "";;
                if (process.equalsIgnoreCase("1")) {
                    processType = "Balance Inquery";
                } else if (process.equalsIgnoreCase("2")) {
                    processType = "Mini Statement";
                } else if (process.equalsIgnoreCase("3")) {
                    processType = "Cheque Book";
                } else if (process.equalsIgnoreCase("4")) {
                    processType = "Statement Request";
                } else if (process.equalsIgnoreCase("5")) {
                    processType = "NEFT";
                } else if (process.equalsIgnoreCase("6")) {
                    processType = "Intra-transfer";
                } else if (process.equalsIgnoreCase("7")) {
                    processType = "Intra-IMPS P2P Transfer";
                } else if (process.equalsIgnoreCase("8")) {
                    processType = "Intra-IMPS P2A Transfer";
                } else if (process.equalsIgnoreCase("9")) {
                    processType = "Outward IMPS P2P Transfer";
                } else if (process.equalsIgnoreCase("10")) {
                    processType = "Inward IMPS P2P Transfer";
                } else if (process.equalsIgnoreCase("11")) {
                    processType = "Outward IMPS P2A Transfer";
                } else if (process.equalsIgnoreCase("12")) {
                    processType = "Inward IMPS P2A Transfer";
                }
                if (mode.equalsIgnoreCase("0")) {
                    modeType = "Normal";
                } else {
                    modeType = "Reversal";
                }
                fillParams.put("pProcessType", processType);
                fillParams.put("pModeType", modeType);
                reportList = remote.getSarvatraImpsTxnParameter(process, mode, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), branch);
                if (reportList.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                new ReportBean().openPdf("IMPS TXN REPORT_" + ymd.format(dmy.parse(getTodayDate())), "impsTxnReport", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public boolean validate() {
        try {
            if (process.equalsIgnoreCase("0")) {
                errorMsg = "Please select Process.";
                return false;
            }
            if (!Validator.validateDate(frmDt)) {
                errorMsg = "Please check from date";
                return false;
            }
            if (!Validator.validateDate(frmDt)) {
                errorMsg = "Please check to date";
                return false;
            }
            if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                errorMsg = "From date should be less then to date";
                return false;
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
        return true;
    }

    public void refresh() {
        this.setErrorMsg("");
        this.setProcess("0");
        this.setMode("S");
        frmDt = dmy.format(new Date());
        toDt = dmy.format(new Date());
    }

    public String exitAction() {
        return "case1";
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public List<SelectItem> getProcessList() {
        return processList;
    }

    public void setProcessList(List<SelectItem> processList) {
        this.processList = processList;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public List<IMPSTxnParameterPojo> getReportList() {
        return reportList;
    }

    public void setReportList(List<IMPSTxnParameterPojo> reportList) {
        this.reportList = reportList;
    }
}
