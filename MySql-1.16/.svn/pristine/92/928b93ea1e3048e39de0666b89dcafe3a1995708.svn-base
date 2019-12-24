/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.TdReceiptIntDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.utils.AmtToWords;
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
 * @author Athar Reza
 */
public class TdReceiptIntDetail extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String repType;
    private List<SelectItem> repTypeList;
    private String acNo;
    private String newAcNo;
    private String receiptNo;
    private List<SelectItem> receiptNoList;
    private String frmDt;
    private String toDt;
    private String recFrmDt;
    private String recToDt;
    private String intFrmAmt;
    private String intToAmt;
    private Date dt = new Date();
    private String ViewFrmAmt;
    private String ViewToAmt;
    private boolean disableAcctNo;
    private boolean disableAcType;
    private boolean disableOption;
    private boolean disableRecFrmDt;
    private boolean disableRecToDt;
    private boolean disableTdsOption;
    private boolean disableFlag;
    private boolean disableActCatg;
    private String focusId;
    private String acType;
    private String option;
    private String tdsOption;
    private String actCatgory;
    private List<SelectItem> acTypeList;
    private List<SelectItem> optionList;
    private List<SelectItem> tdsOptionList;
    private List<SelectItem> actCatgoryList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote otherReportFacade;
    List<TdReceiptIntDetailPojo> reportList = new ArrayList<TdReceiptIntDetailPojo>();

    public String getViewFrmAmt() {
        return ViewFrmAmt;
    }

    public void setViewFrmAmt(String ViewFrmAmt) {
        this.ViewFrmAmt = ViewFrmAmt;
    }

    public String getViewToAmt() {
        return ViewToAmt;
    }

    public void setViewToAmt(String ViewToAmt) {
        this.ViewToAmt = ViewToAmt;
    }
   
    public String getIntFrmAmt() {
        return intFrmAmt;
    }

    public void setIntFrmAmt(String intFrmAmt) {
        this.intFrmAmt = intFrmAmt;
    }

    public String getIntToAmt() {
        return intToAmt;
    }

    public void setIntToAmt(String intToAmt) {
        this.intToAmt = intToAmt;
    }

   public String getTdsOption() {
        return tdsOption;
    }

    public void setTdsOption(String tdsOption) {
        this.tdsOption = tdsOption;
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

    public List<SelectItem> getTdsOptionList() {
        return tdsOptionList;
    }

    public boolean isDisableRecFrmDt() {
        return disableRecFrmDt;
    }

    public void setDisableRecFrmDt(boolean disableRecFrmDt) {
        this.disableRecFrmDt = disableRecFrmDt;
    }

    public boolean isDisableRecToDt() {
        return disableRecToDt;
    }

    public void setDisableRecToDt(boolean disableRecToDt) {
        this.disableRecToDt = disableRecToDt;
    }

    public void setTdsOptionList(List<SelectItem> tdsOptionList) {
        this.tdsOptionList = tdsOptionList;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
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

    public boolean isDisableAcctNo() {
        return disableAcctNo;
    }

    public void setDisableAcctNo(boolean disableAcctNo) {
        this.disableAcctNo = disableAcctNo;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getRecFrmDt() {
        return recFrmDt;
    }

    public void setRecFrmDt(String recFrmDt) {
        this.recFrmDt = recFrmDt;
    }

    public String getRecToDt() {
        return recToDt;
    }

    public void setRecToDt(String recToDt) {
        this.recToDt = recToDt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNewAcNo() {
        return newAcNo;
    }

    public void setNewAcNo(String newAcNo) {
        this.newAcNo = newAcNo;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public List<SelectItem> getReceiptNoList() {
        return receiptNoList;
    }

    public void setReceiptNoList(List<SelectItem> receiptNoList) {
        this.receiptNoList = receiptNoList;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
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

    public boolean isDisableAcType() {
        return disableAcType;
    }

    public void setDisableAcType(boolean disableAcType) {
        this.disableAcType = disableAcType;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<SelectItem> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SelectItem> optionList) {
        this.optionList = optionList;
    }

    public boolean isDisableOption() {
        return disableOption;
    }

    public void setDisableOption(boolean disableOption) {
        this.disableOption = disableOption;
    }

    public boolean isDisableTdsOption() {
        return disableTdsOption;
    }

    public void setDisableTdsOption(boolean disableTdsOption) {
        this.disableTdsOption = disableTdsOption;
    }

    public boolean isDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(boolean disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getActCatgory() {
        return actCatgory;
    }

    public void setActCatgory(String actCatgory) {
        this.actCatgory = actCatgory;
    }

    public List<SelectItem> getActCatgoryList() {
        return actCatgoryList;
    }

    public void setActCatgoryList(List<SelectItem> actCatgoryList) {
        this.actCatgoryList = actCatgoryList;
    }

    public boolean isDisableActCatg() {
        return disableActCatg;
    }

    public void setDisableActCatg(boolean disableActCatg) {
        this.disableActCatg = disableActCatg;
    }

    public TdReceiptIntDetail() {
        try {
            btnRefreshAction();
            ViewFrmAmt="";
            ViewToAmt="";
            setDisableFlag(true);
            setFrmDt(dmy.format(dt));
            setToDt(dmy.format(dt));
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherReportFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");

            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("Select", "--Select--"));
            repTypeList.add(new SelectItem("A", "Account Type"));
            repTypeList.add(new SelectItem("I", "Individul"));
            repTypeList.add(new SelectItem("C", "Customer ID"));
            repTypeList.add(new SelectItem("IC", "Interest Consolidated"));

            optionList = new ArrayList<SelectItem>();
            optionList.add(new SelectItem("D", "Detailed"));
            optionList.add(new SelectItem("S", "Summary"));

            tdsOptionList = new ArrayList<SelectItem>();
            tdsOptionList.add(new SelectItem("RS", "Reserved"));
            tdsOptionList.add(new SelectItem("RC", "Recovered"));
            tdsOptionList.add(new SelectItem("UR", "Un Recovered"));

            actCatgoryList = new ArrayList<SelectItem>();
            actCatgoryList.add(new SelectItem("A", "ALL"));
            actCatgoryList.add(new SelectItem("01", "Individual"));
            actCatgoryList.add(new SelectItem("02", "Company"));


            acTypeList = new ArrayList<SelectItem>();
            List listAccTy = otherReportFacade.getAcctType();
            Vector vtr = new Vector();
            if (!listAccTy.isEmpty()) {
                acTypeList.add(new SelectItem("S", "--Select--"));
                for (int i = 0; i < listAccTy.size(); i++) {
                    vtr = (Vector) listAccTy.get(i);
                    acTypeList.add(new SelectItem(vtr.get(0).toString()));
                }
            }
            setDisableRecFrmDt(true);
            setDisableRecToDt(true);
            setIntFrmAmt("0");
            setIntToAmt("9999999999");
            

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getdisableAccountNo() {
        if (repType.equalsIgnoreCase("A")) {
            setDisableAcctNo(true);
            this.setAcNo("");
            this.setNewAcNo("");
            receiptNoList = new ArrayList<SelectItem>();
            this.setMessage("");
            this.setFocusId("branchddl");
            setDisableAcType(false);
            setOption("D");
            setDisableOption(true);
            setDisableTdsOption(false);
            setActCatgory("A");
            setDisableActCatg(true);
            ViewFrmAmt="";
            ViewToAmt="";
        } else if (repType.equalsIgnoreCase("C")) {
            setDisableAcctNo(true);
            this.setAcNo("");
            this.setNewAcNo("");
            receiptNoList = new ArrayList<SelectItem>();
            this.setMessage("");
            this.setFocusId("branchddl");
            this.setAcType("S");
            setDisableAcType(true);
            setOption("D");
            setDisableOption(false);
            setDisableTdsOption(false);
            setDisableActCatg(false);
            ViewFrmAmt="";
            ViewToAmt="";
        } else if (repType.equalsIgnoreCase("IC")) {
            setDisableAcctNo(true);
            this.setAcNo("");
            this.setNewAcNo("");
            receiptNoList = new ArrayList<SelectItem>();
            this.setMessage("");
            this.setFocusId("branchddl");
            this.setAcType("S");
            setDisableAcType(true);
            setOption("D");
            setDisableOption(true);
            setDisableTdsOption(true);
            setActCatgory("A");
            setDisableActCatg(true);
            ViewFrmAmt="none";
            ViewToAmt="none";
        } else {
            setDisableAcctNo(false);
            this.setMessage("");
            this.setAcType("S");
            setDisableAcType(true);
            this.setFocusId("branchddl");
            setOption("D");
            setDisableOption(false);
            setDisableTdsOption(false);
            setActCatgory("A");
            setDisableActCatg(true);
           ViewFrmAmt="";
           ViewToAmt="";
        }
        if (this.tdsOption.equalsIgnoreCase("RC") && this.repType.equalsIgnoreCase("I")) {
            System.err.println("RT :setDisableFlag(false);");
            setDisableFlag(false);
        } else {
            System.err.println("RT :setDisableFlag(true);");
            setDisableFlag(true);
        }
    }

    public void getOnBlurTdsOption() {
        if (this.tdsOption.equalsIgnoreCase("RC") || this.tdsOption.equalsIgnoreCase("UR")) {
            setDisableRecFrmDt(false);
            setDisableRecToDt(false);
            setRecFrmDt(getFrmDt());
            setRecToDt(getToDt());
        } else {
            setDisableRecFrmDt(true);
            setDisableRecToDt(true);
            setRecFrmDt("");
            setRecToDt("");
        }
        if (this.tdsOption.equalsIgnoreCase("RC") && this.repType.equalsIgnoreCase("I")) {
            System.err.println("TDS :setDisableFlag(false);");
            setDisableFlag(false);
        } else {
            System.err.println("TDS :setDisableFlag(false);");
            setDisableFlag(true);
        }
    }

    public void onBlurAccountNumber() {
        try {
            if (this.getRepType().equalsIgnoreCase("Select")) {
                message = "Please select report type !";
                return;
            }

            if (acNo == null || acNo.equalsIgnoreCase("")) {
                message = "Please enter Customer Id";
                return;
            }
//            if (acNo.length() != 12) {
//                message = "Please Enter 12 Digit Account No";
//                return;
//            }
//            acNo = ftsPostRemote.getNewAccountNumber(acNo);
//            this.newAcNo = acNo;

//            List receiptList = new ArrayList();
//            receiptList = common.getReceiptNoByAcNo(acNo);
//            if (!receiptList.isEmpty()) {
//                receiptNoList = new ArrayList<SelectItem>();
//                receiptNoList.add(new SelectItem("ALL", "ALL"));
//                for (int i = 0; i < receiptList.size(); i++) {
//                    Vector ele = (Vector) receiptList.get(i);
//                    receiptNoList.add(new SelectItem(ele.get(0).toString()));
//                }
//            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewReport() {
        String report = "Td Receipt Detail Report";
        String branchName = "";
        String address = "";
        List<RbiSossPojo> resultList = new ArrayList<RbiSossPojo>();
        try {
            if (repType.equalsIgnoreCase("Select")) {
                message = "Please select report type !";
                return;
            }

            if (repType.equalsIgnoreCase("I")) {
                if (acNo == null || acNo.equalsIgnoreCase("")) {
                    message = "Please enter Customer Id";
                    return;
                }
//                if (acNo.length() != 12) {
//                    message = "Please Enter 12 Digit Account No";
//                    return;
//                }
            }

            if (repType.equalsIgnoreCase("A")) {
                if (this.getAcType().equalsIgnoreCase("S")) {
                    message = "Please select A/c. Type";
                    return;
                }
            }

            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                message = "Please enter from date";
                return;
            }
            if (!Validator.validateDate(frmDt)) {
                message = "Please select proper From Date";
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                message = "Please enter to date";
                return;
            }
            if (!Validator.validateDate(toDt)) {
                message = "Please select proper To Date";
                return;
            }

            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }

            Map fillmap = new HashMap();
            fillmap.put("pBranchName", branchName);
            fillmap.put("pAddress", address);
            fillmap.put("pRepName", report);
            fillmap.put("pReportDate", frmDt + " To " + toDt);
            fillmap.put("pPrintedDate", frmDt + " To " + toDt);
            fillmap.put("pPrintedBy", this.getUserName());
            fillmap.put("pFlag", getOption());
            fillmap.put("pReportName", report);
            fillmap.put("report", "Interest and TDS Report");
            fillmap.put("pPrintedBy", getUserName());
            fillmap.put("pBankName", branchName);
            fillmap.put("pBranchAddress", address);
            fillmap.put("pBankAddress", address);
            fillmap.put("pAmtIn", "(In Rs.)");

            String fDt = ymd.format(dmy.parse(frmDt));
            String tDt = ymd.format(dmy.parse(toDt));
            if (repType.equalsIgnoreCase("IC")) {
                resultList = otherReportFacade.getTdsIntSummary(branch, "ALL", ymd.format(dmy.parse(this.getFrmDt())), ymd.format(dmy.parse(this.getToDt())));
            } else {
                if (tdsOption.equalsIgnoreCase("RS")) {
                    recFrmDt = frmDt;
                    recToDt = toDt;
                }
                reportList = otherReportFacade.getTdReceiptIntData(repType, acNo, fDt, tDt, receiptNo, branch, this.getAcType(), getTdsOption(), getOption(), ymd.format(dmy.parse(this.getRecFrmDt())), ymd.format(dmy.parse(this.getRecToDt())), actCatgory, Double.parseDouble(intFrmAmt), Double.parseDouble(intToAmt));

            }
//            for(int i=0;i<reportList.size();i++){
//                System.out.println("i:"+i+"; report:"+reportList.get(i).getCustId());
//            }
//            ComparatorChain chObj = new ComparatorChain();
//            chObj.addComparator(new TdSortedByAcno());
//            chObj.addComparator(new TdSortedByReceiptNo());
//            chObj.addComparator(new TdSortedByDate());
//                
//            Collections.sort(reportList, chObj);

            if (repType.equalsIgnoreCase("IC")) {
                if (resultList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !!!");
                }
            } else {
                if (reportList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !!!");
                }
            }
            String reportName = "TdReceiptInterestTdsRecover";
            if (repType.equalsIgnoreCase("IC")) {
                reportName = "Tds_Int_Summary";
                new ReportBean().jasperReport(reportName, "text/html",
                        new JRBeanCollectionDataSource(resultList), fillmap, "Td Receipt Detail Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
//                new ReportBean().openPdf("INTEREST REPORT_" + ymd.format(dmy.parse(getTodayDate())), "Tds_Int_Summary", new JRBeanCollectionDataSource(resultList), fillmap);
            } else {
                if (repType.equalsIgnoreCase("C")) {
                    if (getOption().equalsIgnoreCase("S")) {
                        reportName = "TdReceiptInterestTdsRecoverCustIdSummary";
                    } else {
                        reportName = "TdReceiptInterestTdsRecoverCustId";
                    }
                } else if (repType.equalsIgnoreCase("I")) {
                    if (getOption().equalsIgnoreCase("S")) {
                        reportName = "TdReceiptInterestTdsRecoverCustIdSummary";
                    } else {
                        reportName = "TdReceiptInterestTdsRecoverCustId";
                    }
                } else {
                    reportName = "TdReceiptInterestTdsRecover";
                }
                new ReportBean().jasperReport(reportName, "text/html",
                        new JRBeanCollectionDataSource(reportList), fillmap, "Td Receipt Detail Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        String report = "Td Receipt Detail Report";
        String branchName = "";
        String address = "";
        List<RbiSossPojo> resultList = new ArrayList<RbiSossPojo>();
        try {
            if (repType.equalsIgnoreCase("Select")) {
                message = "Please select report type !";
                return;
            }

            if (repType.equalsIgnoreCase("I")) {
                if (acNo == null || acNo.equalsIgnoreCase("")) {
                    message = "Please enter Customer Id";
                    return;
                }
//                if (acNo.length() != 12) {
//                    message = "Please Enter 12 Digit Account No";
//                    return;
//                }
            }

            if (repType.equalsIgnoreCase("A")) {
                if (this.getAcType().equalsIgnoreCase("S")) {
                    message = "Please select A/c. Type";
                    return;
                }
            }

            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                message = "Please enter from date";
                return;
            }
            if (!Validator.validateDate(frmDt)) {
                message = "Please select proper From Date";
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                message = "Please enter to date";
                return;
            }
            if (!Validator.validateDate(toDt)) {
                message = "Please select proper To Date";
                return;
            }

            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }

            Map fillmap = new HashMap();
            fillmap.put("pBranchName", branchName);
            fillmap.put("pAddress", address);
            fillmap.put("pRepName", report);
            fillmap.put("pReportDate", frmDt + " To " + toDt);
            fillmap.put("pPrintedDate", frmDt + " To " + toDt);
            fillmap.put("pPrintedBy", this.getUserName());
            fillmap.put("pFlag", getOption());
            fillmap.put("pReportName", report);
            fillmap.put("report", "Interest and TDS Report");
            fillmap.put("pPrintedBy", getUserName());
            fillmap.put("pBankName", branchName);
            fillmap.put("pBranchAddress", address);
            fillmap.put("pBankAddress", address);
            fillmap.put("pAmtIn", "(In Rs.)");



            String fDt = ymd.format(dmy.parse(frmDt));
            String tDt = ymd.format(dmy.parse(toDt));
            if (repType.equalsIgnoreCase("IC")) {
                resultList = otherReportFacade.getTdsIntSummary(branch, "ALL", ymd.format(dmy.parse(this.getFrmDt())), ymd.format(dmy.parse(this.getToDt())));
            } else {
                reportList = otherReportFacade.getTdReceiptIntData(repType, acNo, fDt, tDt, receiptNo, branch, this.getAcType(), getTdsOption(), getOption(), ymd.format(dmy.parse(this.getRecFrmDt())), ymd.format(dmy.parse(this.getRecToDt())), actCatgory, Double.parseDouble(intFrmAmt), Double.parseDouble(intToAmt));
            }
//            for(int i=0;i<reportList.size();i++){
//                System.out.println("i:"+i+"; report:"+reportList.get(i).getCustId());
//            }
//            ComparatorChain chObj = new ComparatorChain();
//            chObj.addComparator(new TdSortedByAcno());
//            chObj.addComparator(new TdSortedByReceiptNo());
//            chObj.addComparator(new TdSortedByDate());
//                
//            Collections.sort(reportList, chObj);

            if (repType.equalsIgnoreCase("IC")) {
                if (resultList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !!!");
                }
            } else {
                if (reportList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !!!");
                }
            }
            String reportName = "TdReceiptInterestTdsRecover";
            if (repType.equalsIgnoreCase("IC")) {
                reportName = "Tds_Int_Summary";
                new ReportBean().openPdf("INTEREST_REPORT_" + ymd.format(dmy.parse(getTodayDate())), "Tds_Int_Summary", new JRBeanCollectionDataSource(resultList), fillmap);
            } else {
                if (repType.equalsIgnoreCase("C")) {
                    if (getOption().equalsIgnoreCase("S")) {
                        reportName = "TdReceiptInterestTdsRecoverCustIdSummary";
                    } else {
                        reportName = "TdReceiptInterestTdsRecoverCustId";
                    }
                } else if (repType.equalsIgnoreCase("I")) {
                    if (getOption().equalsIgnoreCase("S")) {
                        reportName = "TdReceiptInterestTdsRecoverCustIdSummary";
                    } else {
                        reportName = "TdReceiptInterestTdsRecoverCustId";
                    }
                } else {
                    reportName = "TdReceiptInterestTdsRecover";
                }
                new ReportBean().openPdf("Td_Receipt_Detail_Report_" + ymd.format(dmy.parse(getTodayDate())), reportName, new JRBeanCollectionDataSource(reportList), fillmap);
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            e.printStackTrace();
            setMessage(e.getMessage());
        }
    }

    public void interestCertificate() {
        String report = "Td Receipt Detail Report";
        String branchName = "";
        String address = "";
        String custAddress = "";
        double interest = 0, totalTds = 0;
        String acHolderName = "";
        List<TdReceiptIntDetailPojo> interestReportList = new ArrayList<TdReceiptIntDetailPojo>();
        try {
            if (repType.equalsIgnoreCase("Select")) {
                message = "Please select report type !";
                return;
            }

            if (repType.equalsIgnoreCase("I")) {
                if (acNo == null || acNo.equalsIgnoreCase("")) {
                    message = "Please enter Customer Id";
                    return;
                }
            }

            if (repType.equalsIgnoreCase("A")) {
                if (this.getAcType().equalsIgnoreCase("S")) {
                    message = "Please select A/c. Type";
                    return;
                }
            }

            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                message = "Please enter from date";
                return;
            }
            if (!Validator.validateDate(frmDt)) {
                message = "Please select proper From Date";
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                message = "Please enter to date";
                return;
            }
            if (!Validator.validateDate(toDt)) {
                message = "Please select proper To Date";
                return;
            }
            List brNameAdd = new ArrayList();
            brNameAdd = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            String fDt = ymd.format(dmy.parse(frmDt));
            String tDt = ymd.format(dmy.parse(toDt));
            if (!repType.equalsIgnoreCase("IC")) {
                if (tdsOption.equalsIgnoreCase("RS")) {
                    recFrmDt = frmDt;
                    recToDt = toDt;
                }
                reportList = otherReportFacade.getTdReceiptIntData(repType, acNo, fDt, tDt, receiptNo, branch, this.getAcType(), getTdsOption(), getOption(), ymd.format(dmy.parse(this.getRecFrmDt())), ymd.format(dmy.parse(this.getRecToDt())), actCatgory, Double.parseDouble(intFrmAmt), Double.parseDouble(intToAmt));
                if(reportList.isEmpty()){
                    throw new ApplicationException("Data does not exist !!!");
                }
                acHolderName = reportList.get(0).getName();
                interestReportList = otherReportFacade.getInterestCertificateDate(acNo, reportList,fDt, tDt);
                custAddress = interestReportList.get(0).getCustAdd().replaceAll(",", ", ").toUpperCase();
            }


            if (interestReportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !!!");
            } else {
                for (int i = 0; i < interestReportList.size(); i++) {
                    interest += interestReportList.get(i).getInterest();
                    totalTds += interestReportList.get(i).getTdsAmount();
                }
                System.out.println("Total Interest : " + interest);
                String InterestInWords = new AmtToWords().calculate(interest);
                String repName = "CERTIFICATE OF INTEREST";
                Map fillParams = new HashMap();
                fillParams.put("pPrintedBy", this.getUserName());
                fillParams.put("pReportName", repName);
                fillParams.put("pCustAdd", custAddress);
                fillParams.put("pTotalInterest", interest);
                fillParams.put("pTotalInWord", InterestInWords);
                fillParams.put("pTotalTds", totalTds);
                String todayDate = ymd.format(dmy.parse(getTodayDate()));
                todayDate = todayDate.substring(0, 4) + "-" + todayDate.substring(4, 6) + "-" + todayDate.substring(6, 8);
                fillParams.put("pBankName", branchName);
                fillParams.put("pBranchAddress", address);
                fillParams.put("pAcHolderName", acHolderName);
                String frmToDt = frmDt.substring(0, 2) + " " + CbsUtil.getMonthName(Integer.parseInt(frmDt.substring(3, 5))) + " " + frmDt.substring(6, 10) + " to " + toDt.substring(0, 2) + " " + CbsUtil.getMonthName(Integer.parseInt(toDt.substring(3, 5))) + " " + toDt.substring(6, 10);
                fillParams.put("pReportDate", frmToDt);
                fillParams.put("image","/opt/images/logo.png");
                new ReportBean().openPdf("INTEREST CERTIFICATE_" + todayDate, "Interest_Certificate_Report", new JRBeanCollectionDataSource(interestReportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        setMessage("");
        setAcNo("");
        setNewAcNo("");
        setDisableAcctNo(false);
        setDisableAcType(true);
        setRepType("Select");
        setReceiptNo("ALL");
        setFrmDt(dmy.format(dt));
        setToDt(dmy.format(dt));
        setRecFrmDt("");
        setRecToDt("");
        setViewFrmAmt("");
        setViewToAmt("");
    }

    public String btnExitAction() {
        return "case1";
    }
}