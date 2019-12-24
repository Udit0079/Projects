/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.NpaReportFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.pojo.AllSmsPojo;
import com.cbs.pojo.IndividualSmsPojo;
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
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class SMSReport extends BaseBean {

    private String message;
    private String reportType;
    private String brAccountNo;
    private String branch;
    private String hoAccountNo, acNoLen;
    private String hoAccountDisable = "true";
    private String frdt;
    private String todt;
    private String brAcnoLabelFlag = "none";
    private String brAcnoTxtFlag = "none";
    private String branchLabelFlag = "none";
    private String branchComboFlag = "none";
    private String hoAcnoLabelFlag = "none";
    private String hoAcnoTxtFlag = "none";
    private String alphacode;
    private String focusId = "";
    private List<SelectItem> reportTypeList;
    private List<SelectItem> branchList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeName = "NpaReportFacade";
    private NpaReportFacadeRemote remote = null;
    private final String jndiName = "CommonReportMethods";
    private CommonReportMethodsRemote common = null;
    private TransactionManagementFacadeRemote txnRemote = null;
    private final String jndiHomeNameTxnAuth = "TransactionManagementFacade";
    private FtsPostingMgmtFacadeRemote ftsFacade = null;
    private final String ftsHome = "FtsPostingMgmtFacade";

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
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

    public String getBrAccountNo() {
        return brAccountNo;
    }

    public void setBrAccountNo(String brAccountNo) {
        this.brAccountNo = brAccountNo;
    }

    public String getBrAcnoLabelFlag() {
        return brAcnoLabelFlag;
    }

    public void setBrAcnoLabelFlag(String brAcnoLabelFlag) {
        this.brAcnoLabelFlag = brAcnoLabelFlag;
    }

    public String getBrAcnoTxtFlag() {
        return brAcnoTxtFlag;
    }

    public void setBrAcnoTxtFlag(String brAcnoTxtFlag) {
        this.brAcnoTxtFlag = brAcnoTxtFlag;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranchComboFlag() {
        return branchComboFlag;
    }

    public void setBranchComboFlag(String branchComboFlag) {
        this.branchComboFlag = branchComboFlag;
    }

    public String getBranchLabelFlag() {
        return branchLabelFlag;
    }

    public void setBranchLabelFlag(String branchLabelFlag) {
        this.branchLabelFlag = branchLabelFlag;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getFrdt() {
        return frdt;
    }

    public void setFrdt(String frdt) {
        this.frdt = frdt;
    }

    public String getHoAccountNo() {
        return hoAccountNo;
    }

    public void setHoAccountNo(String hoAccountNo) {
        this.hoAccountNo = hoAccountNo;
    }

    public String getHoAcnoLabelFlag() {
        return hoAcnoLabelFlag;
    }

    public void setHoAcnoLabelFlag(String hoAcnoLabelFlag) {
        this.hoAcnoLabelFlag = hoAcnoLabelFlag;
    }

    public String getHoAcnoTxtFlag() {
        return hoAcnoTxtFlag;
    }

    public void setHoAcnoTxtFlag(String hoAcnoTxtFlag) {
        this.hoAcnoTxtFlag = hoAcnoTxtFlag;
    }

    public String getTodt() {
        return todt;
    }

    public void setTodt(String todt) {
        this.todt = todt;
    }

    public String getAlphacode() {
        return alphacode;
    }

    public void setAlphacode(String alphacode) {
        this.alphacode = alphacode;
    }

    public String getHoAccountDisable() {
        return hoAccountDisable;
    }

    public void setHoAccountDisable(String hoAccountDisable) {
        this.hoAccountDisable = hoAccountDisable;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    /** Creates a new instance of SMSReport */
    public SMSReport() {
        this.setMessage("");
        this.setHoAccountNo("");
        this.setBrAccountNo("");
        try {
            this.brAcnoLabelFlag = "none";
            this.brAcnoTxtFlag = "none";
            this.branchLabelFlag = "none";
            this.branchComboFlag = "none";
            this.hoAcnoLabelFlag = "none";
            this.hoAcnoTxtFlag = "none";
            this.hoAccountDisable = "true";
            this.setFrdt(getTodayDate());
            this.setTodt(getTodayDate());
            remote = (NpaReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxnAuth);
            ftsFacade = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(ftsHome);
            this.setAcNoLen(getAcNoLength());
            fillDataList();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fillDataList() {
        reportTypeList = new ArrayList<SelectItem>();
        branchList = new ArrayList<SelectItem>();
        try {
            reportTypeList.add(new SelectItem("ALL"));
            reportTypeList.add(new SelectItem("Head-Office"));
            reportTypeList.add(new SelectItem("INDIVIDUAL"));

            branchList.add(new SelectItem("Individual Account"));
            List dataList = common.getAlphacodeExcludingHo();
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    String brncode = element.get(0).toString();
                    if (brncode.length() == 1) {
                        brncode = "0" + brncode;
                    }
                    branchList.add(new SelectItem(brncode, element.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void reportTypeAction() {
        this.setMessage("");
        this.setHoAccountNo("");
        this.setBrAccountNo("");
        try {
            alphacode = common.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphacode.equals("")) {
                if (alphacode.equalsIgnoreCase("HO")) {
                    if (this.reportType.equalsIgnoreCase("ALL") || this.reportType.equalsIgnoreCase("Head-Office")) {
                        disableAll();
                        setFocusId("frDate");
                    } else {
                        this.brAcnoLabelFlag = "none";
                        this.brAcnoTxtFlag = "none";
                        this.branchLabelFlag = "";
                        this.branchComboFlag = "";
                        this.hoAcnoLabelFlag = "";
                        this.hoAcnoTxtFlag = "";
                        this.hoAccountDisable = "true";
                        setFocusId("ddBranchType");
                    }
                } else {
                    if (this.reportType.equalsIgnoreCase("ALL") || this.reportType.equalsIgnoreCase("Head-Office")) {
                        disableAll();
                        setFocusId("frDate");
                    } else {
                        this.brAcnoLabelFlag = "";
                        this.brAcnoTxtFlag = "";
                        this.branchLabelFlag = "none";
                        this.branchComboFlag = "none";
                        this.hoAcnoLabelFlag = "none";
                        this.hoAcnoTxtFlag = "none";
                        this.hoAccountDisable = "true";
                        setFocusId("txtBrAcno");
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void branchAction() {
        this.setMessage("");
        this.setHoAccountNo("");
        this.setBrAccountNo("");
        try {
            if (this.branch.equalsIgnoreCase("Individual Account")) {
                this.hoAcnoLabelFlag = "";
                this.hoAcnoTxtFlag = "";
                this.hoAccountDisable = "false";
                setFocusId("txtHoAcno");
            } else {
                this.hoAcnoLabelFlag = "";
                this.hoAcnoTxtFlag = "";
                this.hoAccountDisable = "true";
                setFocusId("frDate");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        this.setMessage("");
        try {
            if (!alphacode.equals("")) {
                if (this.frdt == null || this.frdt.equals("")) {
                    this.setMessage("Please select from date !");
                    return;
                }
                boolean result = new Validator().validateDate_dd_mm_yyyy(this.frdt);
                if (result == false) {
                    this.setMessage("Please select proper from date !");
                    return;
                }
                if (this.todt == null || this.todt.equals("")) {
                    this.setMessage("Please select to date !");
                    return;
                }
                result = new Validator().validateDate_dd_mm_yyyy(this.todt);
                if (result == false) {
                    this.setMessage("Please select proper to date !");
                    return;
                }

                int compareValue = dmy.parse(frdt).compareTo(dmy.parse(todt));
                if (compareValue > 0) {
                    this.setMessage("Please check from date !");
                    return;
                }

                if (alphacode.equalsIgnoreCase("HO")) {
                    if (this.reportType.equalsIgnoreCase("ALL") || this.reportType.equalsIgnoreCase("Head-Office")) {
                        /**
                         * ALL HO CASE
                         */                    
                        List<AllSmsPojo> resultList = remote.getAllMessageAtHo(ymd.format(dmy.parse(this.frdt)),ymd.format(dmy.parse(this.todt)),reportType);
                        if (!resultList.isEmpty()) {
                            printReport("SMS Report", this.frdt + "-" + this.todt, "allMessageReport", resultList);
                        } else {
                            this.setMessage("There is no data to print.");
                            return;
                        }
                    } else {
                        /**
                         * INDIVIDUAL HO CASE
                         */
                        if (this.branch == null || this.branch.equalsIgnoreCase("")) {
                            this.setMessage("Please select branch.");
                            return;
                        }
                        if (this.branch.equalsIgnoreCase("Individual Account")) {
                            if (this.hoAccountNo == null || this.hoAccountNo.equals("")) {
                                this.setMessage("Please fill account no.");
                                return;
                            }
                            //if (this.hoAccountNo.length() < 12) {
                            if (!this.hoAccountNo.equalsIgnoreCase("") && ((this.hoAccountNo.length() != 12) && (this.hoAccountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                                this.setMessage("Please fill proper account no.");
                                return;
                            }
                            String acnoMsg = txnRemote.checkForAccountNo(this.hoAccountNo);
                            if (!acnoMsg.equalsIgnoreCase("true")) {
                                this.setMessage(acnoMsg);
                                return;
                            }
                            List<IndividualSmsPojo> resultList = remote.getIndividualMessageAtHoByAcno(this.hoAccountNo, ymd.format(dmy.parse(this.frdt)), CbsUtil.dateAdd(ymd.format(dmy.parse(this.todt)), 1));
                            if (!resultList.isEmpty()) {
                                printReport("SMS Report", this.frdt + "-" + this.todt, "individualMessageReport", resultList);
                            } else {
                                this.setMessage("There is no data to print.");
                                return;
                            }
                        } else {
                            List<AllSmsPojo> resultList = remote.getIndividualMessageAtHoByBranch(this.branch, ymd.format(dmy.parse(this.frdt)), CbsUtil.dateAdd(ymd.format(dmy.parse(this.todt)), 1));
                            if (!resultList.isEmpty()) {
                                printReport("SMS Report", this.frdt + "-" + this.todt, "allMessageReport", resultList);
                            } else {
                                this.setMessage("There is no data to print.");
                                return;
                            }
                        }
                    }
                } else {
                    if (this.reportType.equalsIgnoreCase("ALL")) {
                        /**
                         * ALL BRANCH CASE
                         */
                        List<AllSmsPojo> resultList = remote.getIndividualMessageAtHoByBranch(getOrgnBrCode(), ymd.format(dmy.parse(this.frdt)), CbsUtil.dateAdd(ymd.format(dmy.parse(this.todt)), 1));
                        if (!resultList.isEmpty()) {
                            printReport("SMS Report", this.frdt + "-" + this.todt, "allMessageReport", resultList);
                        } else {
                            this.setMessage("There is no data to print.");
                            return;
                        }
                    } else {
                        /**
                         * INDIVIDUAL BRANCH CASE
                         */
                        if (this.brAccountNo == null || this.brAccountNo.equalsIgnoreCase("")) {
                            this.setMessage("Please fill Account No.");
                            return;
                        }
                        //if (this.brAccountNo.length() < 12) {
                        if (!this.brAccountNo.equalsIgnoreCase("") && ((this.brAccountNo.length() != 12) && (this.brAccountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                            this.setMessage("Please fill proper account no.");
                            return;
                        }
                        if (!ftsFacade.getCurrentBrnCode(this.brAccountNo).equalsIgnoreCase(getOrgnBrCode())) {
                            this.setMessage("Please fill self branch account number.");
                            return;
                        }

                        String acnoMsg = txnRemote.checkForAccountNo(this.brAccountNo);
                        if (!acnoMsg.equalsIgnoreCase("true")) {
                            this.setMessage(acnoMsg);
                            return;
                        }
                        List<IndividualSmsPojo> resultList = remote.getIndividualMessageAtHoByAcno(this.brAccountNo, ymd.format(dmy.parse(this.frdt)), CbsUtil.dateAdd(ymd.format(dmy.parse(this.todt)), 1));
                        if (!resultList.isEmpty()) {
                            printReport("SMS Report", this.frdt + "-" + this.todt, "individualMessageReport", resultList);
                        } else {
                            this.setMessage("There is no data to print.");
                            return;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void printReport(String reportName, String reportDt, String jrxmlName, List resultList) {
        try {
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAdd", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", reportName);
            fillParams.put("pPrintedDate", reportDt);

            new ReportBean().jasperReport(jrxmlName, "text/html", new JRBeanCollectionDataSource(resultList), fillParams, reportName);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", reportName);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void pdfDownLoad(){
        this.setMessage("");
        try {
            if (!alphacode.equals("")) {
                if (this.frdt == null || this.frdt.equals("")) {
                    this.setMessage("Please select from date !");
                    return;
                }
                boolean result = new Validator().validateDate_dd_mm_yyyy(this.frdt);
                if (result == false) {
                    this.setMessage("Please select proper from date !");
                    return;
                }
                if (this.todt == null || this.todt.equals("")) {
                    this.setMessage("Please select to date !");
                    return;
                }
                result = new Validator().validateDate_dd_mm_yyyy(this.todt);
                if (result == false) {
                    this.setMessage("Please select proper to date !");
                    return;
                }

                int compareValue = dmy.parse(frdt).compareTo(dmy.parse(todt));
                if (compareValue > 0) {
                    this.setMessage("Please check from date !");
                    return;
                }

                if (alphacode.equalsIgnoreCase("HO")) {
                    if (this.reportType.equalsIgnoreCase("ALL") || this.reportType.equalsIgnoreCase("Head-Office")) {
                        /**
                         * ALL HO CASE
                         */
                        List<AllSmsPojo> resultList = remote.getAllMessageAtHo(ymd.format(dmy.parse(this.frdt)),ymd.format(dmy.parse(this.todt)),reportType);
                        if (!resultList.isEmpty()) {
                            printPDF("SMS Report", this.frdt + "-" + this.todt, "allMessageReport", resultList);
                        } else {
                            this.setMessage("There is no data to print.");
                            return;
                        }
                    } else {
                        /**
                         * INDIVIDUAL HO CASE
                         */
                        if (this.branch == null || this.branch.equalsIgnoreCase("")) {
                            this.setMessage("Please select branch.");
                            return;
                        }
                        if (this.branch.equalsIgnoreCase("Individual Account")) {
                            if (this.hoAccountNo == null || this.hoAccountNo.equals("")) {
                                this.setMessage("Please fill account no.");
                                return;
                            }
                            //if (this.hoAccountNo.length() < 12) {
                            if (!this.hoAccountNo.equalsIgnoreCase("") && ((this.hoAccountNo.length() != 12) && (this.hoAccountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                                this.setMessage("Please fill proper account no.");
                                return;
                            }
                            String acnoMsg = txnRemote.checkForAccountNo(this.hoAccountNo);
                            if (!acnoMsg.equalsIgnoreCase("true")) {
                                this.setMessage(acnoMsg);
                                return;
                            }
                            List<IndividualSmsPojo> resultList = remote.getIndividualMessageAtHoByAcno(this.hoAccountNo, ymd.format(dmy.parse(this.frdt)), CbsUtil.dateAdd(ymd.format(dmy.parse(this.todt)), 1));
                            if (!resultList.isEmpty()) {
                                printPDF("SMS Report", this.frdt + "-" + this.todt, "individualMessageReport", resultList);
                            } else {
                                this.setMessage("There is no data to print.");
                                return;
                            }
                        } else {
                            List<AllSmsPojo> resultList = remote.getIndividualMessageAtHoByBranch(this.branch, ymd.format(dmy.parse(this.frdt)), CbsUtil.dateAdd(ymd.format(dmy.parse(this.todt)), 1));
                            if (!resultList.isEmpty()) {
                                printPDF("SMS Report", this.frdt + "-" + this.todt, "allMessageReport", resultList);
                            } else {
                                this.setMessage("There is no data to print.");
                                return;
                            }
                        }
                    }
                } else {
                    if (this.reportType.equalsIgnoreCase("ALL")) {
                        /**
                         * ALL BRANCH CASE
                         */
                        List<AllSmsPojo> resultList = remote.getIndividualMessageAtHoByBranch(getOrgnBrCode(), ymd.format(dmy.parse(this.frdt)), CbsUtil.dateAdd(ymd.format(dmy.parse(this.todt)), 1));
                        if (!resultList.isEmpty()) {
                            printPDF("SMS Report", this.frdt + "-" + this.todt, "allMessageReport", resultList);
                        } else {
                            this.setMessage("There is no data to print.");
                            return;
                        }
                    } else {
                        /**
                         * INDIVIDUAL BRANCH CASE
                         */
                        if (this.brAccountNo == null || this.brAccountNo.equalsIgnoreCase("")) {
                            this.setMessage("Please fill Account No.");
                            return;
                        }
                        //if (this.brAccountNo.length() < 12) {
                        if (!this.brAccountNo.equalsIgnoreCase("") && ((this.brAccountNo.length() != 12) && (this.brAccountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                            this.setMessage("Please fill proper account no");
                            return;
                        }
                        if (!ftsFacade.getCurrentBrnCode(this.brAccountNo).equalsIgnoreCase(getOrgnBrCode())) {
                            this.setMessage("Please fill self branch account number.");
                            return;
                        }

                        String acnoMsg = txnRemote.checkForAccountNo(this.brAccountNo);
                        if (!acnoMsg.equalsIgnoreCase("true")) {
                            this.setMessage(acnoMsg);
                            return;
                        }
                        List<IndividualSmsPojo> resultList = remote.getIndividualMessageAtHoByAcno(this.brAccountNo, ymd.format(dmy.parse(this.frdt)), CbsUtil.dateAdd(ymd.format(dmy.parse(this.todt)), 1));
                        if (!resultList.isEmpty()) {
                            printPDF("SMS Report", this.frdt + "-" + this.todt, "individualMessageReport", resultList);
                        } else {
                            this.setMessage("There is no data to print.");
                            return;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        } 
    }
    
     public void printPDF(String reportName, String reportDt, String jrxmlName, List resultList) {
        try {
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAdd", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", reportName);
            fillParams.put("pPrintedDate", reportDt);

            new ReportBean().openPdf(reportName,jrxmlName, new JRBeanCollectionDataSource(resultList), fillParams);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", reportName);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    

    public void disableAll() {
        this.brAcnoLabelFlag = "none";
        this.brAcnoTxtFlag = "none";
        this.branchLabelFlag = "none";
        this.branchComboFlag = "none";
        this.hoAcnoLabelFlag = "none";
        this.hoAcnoTxtFlag = "none";
        this.hoAccountDisable = "true";
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setReportType("ALL");
        disableAll();
        this.setFrdt(getTodayDate());
        this.setTodt(getTodayDate());
        this.setBrAccountNo("");
        this.setHoAccountNo("");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
