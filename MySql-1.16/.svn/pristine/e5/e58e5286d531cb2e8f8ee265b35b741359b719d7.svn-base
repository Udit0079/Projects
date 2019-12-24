package com.cbs.web.controller.admin;

import com.cbs.dto.npa.NPAReminderPojo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.dto.report.OverdueNonEmiResultList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.npa.NPAFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.OverDueReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
 * @author Manish kumar
 */
public class NPAOverdueReminderCharges extends BaseBean {

    private String msg;
    private String msgStyle;
    private int currentIndex;
    private String mode;
    private String acNature;
    private String acType;
    private String overDueRem;
    private String dt;
    private boolean checkAll;
    private boolean disableSave;
    private boolean disablePrint;
    private boolean disableShow;
    private boolean disableCheckAll;
    private boolean disableCheckBox;
    private List<SelectItem> modeOption;
    private List<SelectItem> acNatureOption;
    private List<SelectItem> acTypeOption;
    private List<SelectItem> overDueRemOption;
    private CommonReportMethodsRemote common;
    private NPAFacadeRemote npaFacadeRemote;
    private LoanReportFacadeRemote beanRemote;
    private List<OverdueEmiReportPojo> overDueList;
    private List<OverdueNonEmiResultList> overdueNonEmiList;
    private boolean  isBankAddress;
    private String btnName;
    private List<NPAReminderPojo> npaRemForPrint;
    private OverDueReportFacadeRemote overDueReport;
    private String viewID = "/pages/master/sm/test.jsp";
    private String reportName;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgStyle() {
        return msgStyle;
    }

    public void setMsgStyle(String msgStyle) {
        this.msgStyle = msgStyle;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    public String getOverDueRem() {
        return overDueRem;
    }

    public void setOverDueRem(String overDueRem) {
        this.overDueRem = overDueRem;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public boolean isCheckAll() {
        return checkAll;
    }

    public void setCheckAll(boolean checkAll) {
        this.checkAll = checkAll;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public boolean isDisablePrint() {
        return disablePrint;
    }

    public void setDisablePrint(boolean disablePrint) {
        this.disablePrint = disablePrint;
    }

    public boolean isDisableShow() {
        return disableShow;
    }

    public void setDisableShow(boolean disableShow) {
        this.disableShow = disableShow;
    }

    public List<SelectItem> getModeOption() {
        return modeOption;
    }

    public void setModeOption(List<SelectItem> modeOption) {
        this.modeOption = modeOption;
    }

    public List<SelectItem> getAcNatureOption() {
        return acNatureOption;
    }

    public void setAcNatureOption(List<SelectItem> acNatureOption) {
        this.acNatureOption = acNatureOption;
    }

    public List<SelectItem> getAcTypeOption() {
        return acTypeOption;
    }

    public void setAcTypeOption(List<SelectItem> acTypeOption) {
        this.acTypeOption = acTypeOption;
    }

    public List<SelectItem> getOverDueRemOption() {
        return overDueRemOption;
    }

    public void setOverDueRemOption(List<SelectItem> overDueRemOption) {
        this.overDueRemOption = overDueRemOption;
    }

    public List<OverdueEmiReportPojo> getOverDueList() {
        return overDueList;
    }

    public void setOverDueList(List<OverdueEmiReportPojo> overDueList) {
        this.overDueList = overDueList;
    }

    public boolean isDisableCheckAll() {
        return disableCheckAll;
    }

    public void setDisableCheckAll(boolean disableCheckAll) {
        this.disableCheckAll = disableCheckAll;
    }

    public boolean isDisableCheckBox() {
        return disableCheckBox;
    }

    public void setDisableCheckBox(boolean disableCheckBox) {
        this.disableCheckBox = disableCheckBox;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public List<OverdueNonEmiResultList> getOverdueNonEmiList() {
        return overdueNonEmiList;
    }

    public void setOverdueNonEmiList(List<OverdueNonEmiResultList> overdueNonEmiList) {
        this.overdueNonEmiList = overdueNonEmiList;
    }

    public boolean isIsBankAddress() {
        return isBankAddress;
    }

    public void setIsBankAddress(boolean isBankAddress) {
        this.isBankAddress = isBankAddress;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }
    
    
    
    

    public NPAOverdueReminderCharges() {
        try {
            this.disableCheckAll = true;
            beanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            npaFacadeRemote = (NPAFacadeRemote) ServiceLocator.getInstance().lookup("NPAFacade");
            overDueReport = (OverDueReportFacadeRemote) ServiceLocator.getInstance().lookup("OverDueReportFacade");
            modeOption = new ArrayList<SelectItem>();
            modeOption.add(new SelectItem("--Select--"));
            modeOption.add(new SelectItem("add", "Add"));
            modeOption.add(new SelectItem("show", "Show"));
        } catch (ApplicationException ex) {
            this.msgStyle = "error";
            this.setMsg(ex.getMessage());
        }
    }

    public void intMode() {
    }

    public void intAcountNature() {
        try {
            if (!mode.equalsIgnoreCase("--Select--")) {
                if (mode.equalsIgnoreCase("add")) {
                    this.disableSave = false;
                    this.disablePrint = true;
                    this.setBtnName("Add");
                }
                if (mode.equalsIgnoreCase("show")) {
                    this.disableSave = true;
                    this.disablePrint = false;
                    this.setBtnName("Show");
                }
                List tempList = common.getAcctNatureOnlyDB();
                Vector tempVector;
                if (!tempList.isEmpty()) {
                    acNatureOption = new ArrayList<SelectItem>();
                    acNatureOption.add(new SelectItem("--Select--"));
                    acNatureOption.add(new SelectItem("0", "ALL"));
                    for (int i = 0; i < tempList.size(); i++) {
                        tempVector = (Vector) tempList.get(i);
                        acNatureOption.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                    }
                }
            }
            this.overDueList = null;
        } catch (ApplicationException ex) {
            this.msgStyle = "error";
            this.setMsg(ex.getMessage());
        }
    }

    public void intAcountType() {
        try {
            if (!mode.equalsIgnoreCase("--Select--") && !acNature.equalsIgnoreCase("--Select--")) {
                acTypeOption = new ArrayList<SelectItem>();
                List tempList = common.getAcctTypeAsAcNatureOnlyDB(acNature);
                if (!tempList.isEmpty()) {
                    acTypeOption.add(new SelectItem("--Select--"));
                    acTypeOption.add(new SelectItem("0", "ALL"));
                    for (int i = 0; i < tempList.size(); i++) {
                        Vector vtr = (Vector) tempList.get(i);
                        acTypeOption.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));
                    }
                } else {
                    if (this.acNature.endsWith("0")) {
                        acTypeOption.add(new SelectItem("--Select--"));
                        acTypeOption.add(new SelectItem("0", "ALL"));
                    } else {
                        this.msgStyle = "error";
                        this.setMsg("Acount type not found corresponding Acount nature !");
                    }
                }
            }
        } catch (ApplicationException ex) {
            this.msgStyle = "error";
            this.setMsg(ex.getMessage());
        }
    }

    public void initData() {
        try {
            if (!mode.equalsIgnoreCase("--Select--") && !acNature.equalsIgnoreCase("--Select--") && !acType.equalsIgnoreCase("--Select--")) {
                List resultList2 = common.getRefRecList("402");
                if (!resultList2.isEmpty()) {
                    overDueRemOption = new ArrayList<SelectItem>();
                    overDueRemOption.add(new SelectItem("--Select--"));
                    for (int i = 0; i < resultList2.size(); i++) {
                        Vector ele1 = (Vector) resultList2.get(i);
                        overDueRemOption.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                    }
                }
            }
        } catch (Exception ex) {
            this.msgStyle = "error";
            this.setMsg(ex.getMessage());
        }

    }

//    public void getOverDue() {
//        int count = 0, listSize = 0;
//        try {
//            NumberFormat formatter = new DecimalFormat("0.00");
//            if (this.mode.equalsIgnoreCase("--Select--")) {
//                this.msgStyle = "error";
//                this.setMsg("Please select Mode!");
//                return;
//            }
//            if (this.acNature.equalsIgnoreCase("--Select--")) {
//                this.msgStyle = "error";
//                this.setMsg("Please select Account nature!");
//                return;
//            }
//            if (this.acType.equalsIgnoreCase("--Select--")) {
//                this.msgStyle = "error";
//                this.setMsg("Please select Account Type!");
//                return;
//            }
//            if (this.overDueRem.equalsIgnoreCase("--Select--")) {
//                this.msgStyle = "error";
//                this.setMsg("Please select Over due reminder!");
//                return;
//            }
//            if (!Validator.validateDate(this.dt)) {
//                this.msgStyle = "error";
//                this.setMsg("Please select proper Date");
//                return;
//            }
//            String acountType = "ALL";
//            if (!this.acType.equalsIgnoreCase("0")) {
//                acountType = this.acType;
//            }
//            int pendingEmiFrom = 1, pendingEmiTo = 1;
//            if (this.overDueRem.equalsIgnoreCase("1")) {
//                pendingEmiFrom  = 1;
//                pendingEmiTo    = 1;
//            } else if (this.overDueRem.equalsIgnoreCase("2")) {
//                pendingEmiFrom  = 2;
//                pendingEmiTo    = 2;
//            } else {
//                pendingEmiFrom  = 3;
//                pendingEmiTo    = 999;
//            }
//            String date = this.dt.substring(6, 10) + "-" + this.dt.substring(3, 5) + "-" + this.dt.substring(0, 2);
//            if (acNature.equalsIgnoreCase("CA")) {
//                overDueList = overDueReport.getOverDueNonEmi(acNature, acountType, date, (this.getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : this.getOrgnBrCode()),"N");
//            } else if (acNature.equalsIgnoreCase("TL")) {
//                overDueList = overDueReport.getOverdueEmiReport(1, 0, "", acountType, pendingEmiFrom, pendingEmiTo, ymd.format(ymdFormat.parse(date)), (this.getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : this.getOrgnBrCode()), "S",true,"","N");
//            }
//            String monthStartDt = ymdFormat.format(ymd.parse(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(date))));
//            String monthEndDt = ymdFormat.format(CbsUtil.getLastDateOfMonth(ymdFormat.parse(date)));
//            if (overDueList.isEmpty()) {
//                this.msgStyle = "error";
//                this.setMsg("Data does not exist !");
//            } else {
//                if (this.mode.equalsIgnoreCase("Add")) {
//                    listSize = overDueList.size();
//                    this.msgStyle = "";
//                    this.setMsg("");
//                    for (int i = 0; i < overDueList.size(); i++) {
//                        if (true == npaFacadeRemote.isExistOverDueReminder(overDueList.get(i).getAccountNumber(), this.overDueRem, monthStartDt, monthEndDt)) {
//                            overDueList.remove(i);
//                            i--;
//                            count++;
//                        } else {
//                            overDueList.get(i).setAmount(npaFacadeRemote.getAmount(overDueList.get(i).getAccountNumber(), this.overDueRem, date, ""));
//                            overDueList.get(i).setGlHead(npaFacadeRemote.getGlHead(overDueList.get(i).getAccountNumber(), this.overDueRem, date));
//                            overDueList.get(i).setNoOfActualEmiOverdue(Double.parseDouble(formatter.format(overDueList.get(i).getNoOfActualEmiOverdue())));
//                            overDueList.get(i).setCheckBoxFlag(true);
//                            overDueList.get(i).setsNo((i + 1) + "");
//                        }
//                    }
//                    this.checkAll = true;
//                    this.disableCheckAll = false;
//                    this.disableCheckBox = true;
//                    this.msgStyle = "";
//                    this.setMsg("");
//                }
//                if (this.mode.equalsIgnoreCase("Show")) {
//                    listSize = overDueList.size();
//                    this.msgStyle = "";
//                    this.setMsg("");
//                    for (int i = 0; i < overDueList.size(); i++) {
//                        if (false == npaFacadeRemote.isExistOverDueReminder(overDueList.get(i).getAccountNumber(), this.overDueRem, monthStartDt, monthEndDt)) {
//                            overDueList.remove(i);
//                            i--;
//                            count++;
//                        } else {
//                            overDueList.get(i).setAmount(npaFacadeRemote.getAmount(overDueList.get(i).getAccountNumber(), this.overDueRem, date, ""));
//                            overDueList.get(i).setGlHead(npaFacadeRemote.getGlHead(overDueList.get(i).getAccountNumber(), this.overDueRem, date));
//                            overDueList.get(i).setNoOfActualEmiOverdue(Double.parseDouble(formatter.format(overDueList.get(i).getNoOfActualEmiOverdue())));
//                            overDueList.get(i).setBalance(new BigDecimal(formatter.format(overDueList.get(i).getAmount())));
//                            overDueList.get(i).setCheckBoxFlag(true);
//                            overDueList.get(i).setsNo((i + 1) + "");
//                        }
//                    }
//                    this.checkAll = true;
//                    this.disableCheckAll = false;
//                    this.disableCheckBox = true;
//                    this.msgStyle = "";
//                    this.setMsg("");
//                }
//                if (listSize == count) {
//                    this.disableCheckAll = true;
//                    this.msgStyle = "error";
//                    this.setMsg("Reminder already posted corresponding input !");
//                }
//            }            
//        } catch (Exception ex) {
//            this.msgStyle = "error";
//            this.setMsg(ex.getMessage());
//        }
//    }
    public void getOverDue() {
        int count = 0, listSize = 0;
        try {
            NumberFormat formatter = new DecimalFormat("0.00");
            if (this.mode.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Mode!");
                return;
            }
            if (this.acNature.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Account nature!");
                return;
            }
            if (this.acType.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Account Type!");
                return;
            }
            if (this.overDueRem.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Over due reminder!");
                return;
            }
            if (!Validator.validateDate(this.dt)) {
                this.msgStyle = "error";
                this.setMsg("Please select proper Date");
                return;
            }
            String acountType = "ALL";
            if (!this.acType.equalsIgnoreCase("0")) {
                acountType = this.acType;
            }
            int pendingEmiFrom = 1, pendingEmiTo = 1;
            if (this.overDueRem.equalsIgnoreCase("1")) {
                pendingEmiFrom = 1;
                pendingEmiTo = 1;
            } else if (this.overDueRem.equalsIgnoreCase("2")) {
                pendingEmiFrom = 2;
                pendingEmiTo = 2;
            } else {
                pendingEmiFrom = 3;
                pendingEmiTo = 999;
            }
            String date = this.dt.substring(6, 10) + "-" + this.dt.substring(3, 5) + "-" + this.dt.substring(0, 2);
            if (acNature.equalsIgnoreCase("CA")) {
                overdueNonEmiList = overDueReport.getOverDueNonEmi(acNature, acountType, date, (this.getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : this.getOrgnBrCode()), "N");

                String monthStartDt = ymdFormat.format(ymd.parse(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(date))));
                String monthEndDt = ymdFormat.format(CbsUtil.getLastDateOfMonth(ymdFormat.parse(date)));
                if (overdueNonEmiList.isEmpty()) {
                    this.msgStyle = "error";
                    this.setMsg("Data does not exist !");
                } else {
                    overDueList = convertNonEmiToEmiList(overdueNonEmiList);
                    if (this.mode.equalsIgnoreCase("Add")) {
                        listSize = overDueList.size();
                        this.msgStyle = "";
                        this.setMsg("");
                        for (int i = 0; i < overDueList.size(); i++) {
                            if (true == npaFacadeRemote.isExistOverDueReminder(overDueList.get(i).getAccountNumber(), this.overDueRem, monthStartDt, monthEndDt)) {
                                overDueList.remove(i);
                                i--;
                                count++;
                            } else {
                                
                                overDueList.get(i).setAmount(npaFacadeRemote.getAmount(overDueList.get(i).getAccountNumber(), this.overDueRem, date, ""));
                                overDueList.get(i).setGlHead(npaFacadeRemote.getGlHead(overDueList.get(i).getAccountNumber(), this.overDueRem, date));
                                overDueList.get(i).setNoOfActualEmiOverdue(Double.parseDouble(formatter.format(overDueList.get(i).getNoOfActualEmiOverdue())));
                                overDueList.get(i).setBalance(new BigDecimal(formatter.format(overDueList.get(i).getOutstandingBalance())));
                                overDueList.get(i).setSanctionedamt(new BigDecimal(formatter.format(overDueList.get(i).getSanctionedamt())));
                                overDueList.get(i).setCheckBoxFlag(true);
                                overDueList.get(i).setsNo((i + 1) + "");
                            }
                        }
                        this.checkAll = true;
                        this.disableCheckAll = false;
                        this.disableCheckBox = true;
                        this.msgStyle = "";
                        this.setMsg("");
                    }
                    if (this.mode.equalsIgnoreCase("Show")) {
                        listSize = overDueList.size();
                        this.msgStyle = "";
                        this.setMsg("");
                        for (int i = 0; i < overDueList.size(); i++) {
                            if (false == npaFacadeRemote.isExistOverDueReminder(overDueList.get(i).getAccountNumber(), this.overDueRem, monthStartDt, monthEndDt)) {
                                overDueList.remove(i);
                                i--;
                                count++;
                            } else {
                                overDueList.get(i).setAmount(npaFacadeRemote.getAmount(overDueList.get(i).getAccountNumber(), this.overDueRem, date, ""));
                                overDueList.get(i).setGlHead(npaFacadeRemote.getGlHead(overDueList.get(i).getAccountNumber(), this.overDueRem, date));
                                overDueList.get(i).setNoOfActualEmiOverdue(Double.parseDouble(formatter.format(overDueList.get(i).getNoOfActualEmiOverdue())));
                                overDueList.get(i).setBalance(new BigDecimal(formatter.format(overDueList.get(i).getOutstandingBalance())));
                                overDueList.get(i).setSanctionedamt(new BigDecimal(formatter.format(overDueList.get(i).getSanctionedamt())));
                                overDueList.get(i).setCheckBoxFlag(true);
                                overDueList.get(i).setsNo((i + 1) + "");
                            }
                        }
                        this.checkAll = true;
                        this.disableCheckAll = false;
                        this.disableCheckBox = true;
                        this.msgStyle = "";
                        this.setMsg("");
                    }
                    if (listSize == count) {
                        this.disableCheckAll = true;
                        this.msgStyle = "error";
                        if (this.mode.equalsIgnoreCase("Add")) {
                            this.setMsg("Reminder already posted corresponding input !");
                        } else {
                            this.setMsg("Reminder not found corresponding input !");
                        }                        
                    }
                }

             } else if (acNature.equalsIgnoreCase("TL")) {
                overDueList = overDueReport.getOverdueEmiReport(1, 0, "", acountType, pendingEmiFrom, pendingEmiTo, ymd.format(ymdFormat.parse(date)), (this.getOrgnBrCode().equalsIgnoreCase("90") ? "0A" : this.getOrgnBrCode()), "S", true, "", "N");

                String monthStartDt = ymdFormat.format(ymd.parse(CbsUtil.getFirstDateOfGivenDate(ymdFormat.parse(date))));
                String monthEndDt = ymdFormat.format(CbsUtil.getLastDateOfMonth(ymdFormat.parse(date)));
                if (overDueList.isEmpty()) {
                    this.msgStyle = "error";
                    this.setMsg("Data does not exist !");
                } else {
                    if (this.mode.equalsIgnoreCase("Add")) {
                        listSize = overDueList.size();
                        this.msgStyle = "";
                        this.setMsg("");
                        for (int i = 0; i < overDueList.size(); i++) {
                            if (true == npaFacadeRemote.isExistOverDueReminder(overDueList.get(i).getAccountNumber(), this.overDueRem, monthStartDt, monthEndDt)) {
                                overDueList.remove(i);
                                i--;
                                count++;
                            } else {
                                overDueList.get(i).setAmount(npaFacadeRemote.getAmount(overDueList.get(i).getAccountNumber(), this.overDueRem, date, ""));
                                overDueList.get(i).setGlHead(npaFacadeRemote.getGlHead(overDueList.get(i).getAccountNumber(), this.overDueRem, date));
                                overDueList.get(i).setNoOfActualEmiOverdue(Double.parseDouble(formatter.format(overDueList.get(i).getNoOfActualEmiOverdue())));
                                overDueList.get(i).setBalance(new BigDecimal(formatter.format(overDueList.get(i).getOutstandingBalance())));
                                overDueList.get(i).setSanctionedamt(new BigDecimal(formatter.format(overDueList.get(i).getSanctionedamt())));
                                overDueList.get(i).setCheckBoxFlag(true);
                                overDueList.get(i).setsNo((i + 1) + "");
                            }
                        }
                        this.checkAll = true;
                        this.disableCheckAll = false;
                        this.disableCheckBox = true;
                        this.msgStyle = "";
                        this.setMsg("");
                    }
                    if (this.mode.equalsIgnoreCase("Show")) {
                        listSize = overDueList.size();
                        this.msgStyle = "";
                        this.setMsg("");
                        for (int i = 0; i < overDueList.size(); i++) {
                            if (false == npaFacadeRemote.isExistOverDueReminder(overDueList.get(i).getAccountNumber(), this.overDueRem, monthStartDt, monthEndDt)) {
                                overDueList.remove(i);
                                i--;
                                count++;
                            } else {
                                overDueList.get(i).setAmount(npaFacadeRemote.getAmount(overDueList.get(i).getAccountNumber(), this.overDueRem, date, ""));
                                overDueList.get(i).setGlHead(npaFacadeRemote.getGlHead(overDueList.get(i).getAccountNumber(), this.overDueRem, date));
                                overDueList.get(i).setNoOfActualEmiOverdue(Double.parseDouble(formatter.format(overDueList.get(i).getNoOfActualEmiOverdue())));
                                overDueList.get(i).setBalance(new BigDecimal(formatter.format(overDueList.get(i).getOutstandingBalance())));
                                overDueList.get(i).setSanctionedamt(new BigDecimal(formatter.format(overDueList.get(i).getSanctionedamt())));
                                overDueList.get(i).setCheckBoxFlag(true);
                                overDueList.get(i).setsNo((i + 1) + "");
                            }
                        }
                        this.checkAll = true;
                        this.disableCheckAll = false;
                        this.disableCheckBox = true;
                        this.msgStyle = "";
                        this.setMsg("");
                    }
                    if (listSize == count) {
                        this.disableCheckAll = true;
                        this.msgStyle = "error";
                        if (this.mode.equalsIgnoreCase("Add")) {
                            this.setMsg("Reminder already posted corresponding input !");
                        } else {
                            this.setMsg("Reminder not found corresponding input !");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            this.msgStyle = "error";
            this.setMsg(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<OverdueEmiReportPojo> convertNonEmiToEmiList(List<OverdueNonEmiResultList> nonEmiList) {
        String surety1Id = "", surety1Name = "", surety1Add = "";
        String surety2Id = "", surety2Name = "", surety2Add = "";
        String surety3Id = "", surety3Name = "", surety3Add = "";
        List<OverdueEmiReportPojo> overDueEmiList = new ArrayList<>();
        NumberFormat formatter = new DecimalFormat("0.00");
        for (int i = 0; i < nonEmiList.size(); i++) {
            OverdueEmiReportPojo pojo = new OverdueEmiReportPojo();
            pojo.setAccountNumber(nonEmiList.get(i).getAccountNo());
            pojo.setAmountOverdue(new BigDecimal(formatter.format(nonEmiList.get(i).getOverDue())));
             pojo.setFlag("0");
            pojo.setMoratoriumFlag("A");
            pojo.setOutstandingBalance(nonEmiList.get(i).getBalance());
            pojo.setCustName(nonEmiList.get(i).getCustName()== null ? "": nonEmiList.get(i).getCustName());
            pojo.setInstallmentamt(new BigDecimal(nonEmiList.get(i).getOverDue()));
            pojo.setAmount(new BigDecimal(nonEmiList.get(i).getOverDue()));
            pojo.setCustAdd("");
            pojo.setCustId("");
            pojo.setCustState("");
            pojo.setCustomerId("");
            pojo.setBankName(nonEmiList.get(i).getBankName()== null ? "":nonEmiList.get(i).getBankName() );
            pojo.setBranchAddress(nonEmiList.get(i).getBankAddress()== null ? "":nonEmiList.get(i).getBankAddress());
            pojo.setBranchState("");
            pojo.setDueDate(nonEmiList.get(i).getRdDate() == null ? "" : nonEmiList.get(i).getRdDate());
            pojo.setSurety1Name(surety1Name);
            pojo.setSurety1Id(surety1Id);
            pojo.setSurety1Add(surety1Add);
            pojo.setSurety2Name(surety2Name);
            pojo.setSurety2Id(surety2Id);
            pojo.setSurety2Add(surety2Add);
            pojo.setSurety3Name(surety3Name);
            pojo.setSurety3Id(surety3Id);
            pojo.setSurety3Add(surety3Add);
            pojo.setSanctionDate(nonEmiList.get(i).getSanctionDt() == null ? "":nonEmiList.get(i).getSanctionDt() );
            pojo.setSanctionedamt(new BigDecimal(nonEmiList.get(i).getLimitBal())== null ? BigDecimal.ZERO :new BigDecimal(nonEmiList.get(i).getLimitBal()));


           overDueEmiList.add(pojo);
           
            }



        return overDueEmiList;
    }

    public void saveOverDue() {
        try {
            if (this.mode.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Mode!");
                return;
            }
            if (this.acNature.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Account nature!");
                return;
            }
            if (this.acType.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Account Type!");
                return;
            }
            if (this.overDueRem.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Over due reminder!");
                return;
            }
            if (!Validator.validateDate(this.dt)) {
                this.msgStyle = "error";
                this.setMsg("Please select proper To Date");
                return;
            }
            int flag = npaFacadeRemote.insertNPAOverDue(overDueList, 0, this.overDueRem, this.dt, this.checkAll, this.getUserName(), this.getOrgnBrCode());
            if (flag != 0) {
                refreshForm();
                this.msgStyle = "msg";
                this.setMsg("Reminder " + this.overDueRem + " Successfully posted. Batch No:" + flag);
            }
        } catch (Exception ex) {
            this.msgStyle = "error";
            this.setMsg(ex.getMessage());
        }
    }

    public void printReport() {
        try {
            this.setMsg("");
            npaRemForPrint = null;
            if (this.mode.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Mode!");
                return;
            }
            if (this.acNature.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Account nature!");
                return;
            }
            if (this.acType.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Account Type!");
                return;
            }
            if (this.overDueRem.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Over due reminder!");
                return;
            }
            if (!Validator.validateDate(this.dt)) {
                this.msgStyle = "error";
                this.setMsg("Please select proper To Date");
                return;
            }
            npaRemForPrint = npaFacadeRemote.printOverDueReminder(overDueList, this.overDueRem, this.dt, this.checkAll, this.getUserName(), this.getOrgnBrCode());
            if (npaRemForPrint.isEmpty()) {
                this.msgStyle = "error";
                this.setMsg("Data not found !");
            }
            String jrxmlRemportName = "";
            this.reportName = "NPA REMINDER";
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List branchNameandAddress = common.getBranchNameandAddress(this.getOrgnBrCode());
            List branchDistCityPin = npaFacadeRemote.getBankDistCityPin(this.getOrgnBrCode());//branchDistCityPin
            Map fillParam = new HashMap();
            if (this.overDueRem.equalsIgnoreCase("1")) {
                fillParam.put("pReminder", "REMINDER");
            } else if (this.overDueRem.equalsIgnoreCase("2")) {
                fillParam.put("pReminder", "SPEED POST");
            } else {
                fillParam.put("pReminder", "SPEED POST");
            }

            fillParam.put("pRefNoLabel", "Ref. No.  R" + this.overDueRem + "/");
            fillParam.put("pBankName", branchNameandAddress.get(0));
            fillParam.put("pBranchAddress", branchNameandAddress.get(1));
            fillParam.put("pIsBankAddress",isBankAddress);
            //fillParam.put("pBankAddress", branchNameandAddress.get(1));
            if (!branchDistCityPin.isEmpty()) {
                fillParam.put("pBankCity", branchDistCityPin.get(0) + "");
                fillParam.put("pBankState", branchDistCityPin.get(1) + "");
                fillParam.put("pBankPin", branchDistCityPin.get(2) + "");
            }
            if (this.overDueRem.equalsIgnoreCase("3")) {
                jrxmlRemportName = "NPAReminder3";
                fillParam.put("pRegards", " Yours faithfully,\n \n \n \n (SENIOR MANAGER)");
                fillParam.put("pNoteDesc", "If you have already made payment of above stated dues, please ignore this Letter.");
            } else if (this.overDueRem.equalsIgnoreCase("1")) {
                jrxmlRemportName = "NPAReminder1";
                fillParam.put("pRegards", " Yours faithfully,\n \n \n \n(SENIOR MANAGER)");
                fillParam.put("pNoteDesc", "(1) If you have already made the payment, please ignore this letter.\n"
                        + "(2) In case of Borrower/Surety(ies) in service, recovery may be initiated through "
                        + "their employer(s) in terms of provisions of DCS Act & Rules.");
            } else {
                jrxmlRemportName = "NPAReminder12";
                fillParam.put("pRegards", " Yours faithfully,\n \n \n \n (SENIOR MANAGER)");
                fillParam.put("pNoteDesc", "(1) If you have already made the payment, please ignore this letter.\n"
                        + "(2) In case of Borrower/Surety(ies) in service, recovery may be initiated through "
                        + "their employer(s) in terms of provisions of DCS Act & Rules.");
            }
            new ReportBean().jasperReport(jrxmlRemportName.trim(), "text/html", new JRBeanCollectionDataSource(npaRemForPrint), fillParam, reportName);
//            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
//            HttpSession httpSession = getHttpSession();
//            httpSession.setAttribute("ReportName", reportName);
            this.setViewID("/report/ReportPagePopUp.jsp");
        } catch (Exception ex) {
            this.msgStyle = "error";
            this.setMsg(ex.getMessage());
        }
    }

    public void printPDF() {
        try {
            this.setMsg("");
            npaRemForPrint = null;
            if (this.mode.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Mode!");
                return;
            }
            if (this.acNature.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Account nature!");
                return;
            }
            if (this.acType.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Account Type!");
                return;
            }
            if (this.overDueRem.equalsIgnoreCase("--Select--")) {
                this.msgStyle = "error";
                this.setMsg("Please select Over due reminder!");
                return;
            }
            if (!Validator.validateDate(this.dt)) {
                this.msgStyle = "error";
                this.setMsg("Please select proper To Date");
                return;
            }
            npaRemForPrint = npaFacadeRemote.printOverDueReminder(overDueList, this.overDueRem, this.dt, this.checkAll, this.getUserName(), this.getOrgnBrCode());
            if (npaRemForPrint.isEmpty()) {
                this.msgStyle = "error";
                this.setMsg("Data not found !");
            }
            String jrxmlRemportName = "";
            this.reportName = "NPA REMINDER";
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List branchNameandAddress = common.getBranchNameandAddress(this.getOrgnBrCode());
            List branchDistCityPin = npaFacadeRemote.getBankDistCityPin(this.getOrgnBrCode());//branchDistCityPin
            Map fillParam = new HashMap();
            if (this.overDueRem.equalsIgnoreCase("1")) {
                fillParam.put("pReminder", "REMINDER");
            } else if (this.overDueRem.equalsIgnoreCase("2")) {
                fillParam.put("pReminder", "SPEED POST");
            } else {
                fillParam.put("pReminder", "SPEED POST");
            }

            fillParam.put("pRefNoLabel", "Ref. No.  R" + this.overDueRem + "/");
            fillParam.put("pBankName", branchNameandAddress.get(0));
            fillParam.put("pBranchAddress", branchNameandAddress.get(1));
            fillParam.put("pIsBankAddress",isBankAddress);
            //fillParam.put("pBankAddress", branchNameandAddress.get(1));
            if (!branchDistCityPin.isEmpty()) {
                fillParam.put("pBankCity", branchDistCityPin.get(0) + "");
                fillParam.put("pBankState", branchDistCityPin.get(1) + "");
                fillParam.put("pBankPin", branchDistCityPin.get(2) + "");
            }
            if (this.overDueRem.equalsIgnoreCase("3")) {
                jrxmlRemportName = "NPAReminder3";
                fillParam.put("pRegards", " Yours faithfully,\n\n\n\n (SENIOR MANAGER)");
                fillParam.put("pNoteDesc", "If you have already made payment of above stated dues, please ignore this Letter.");
            } else if (this.overDueRem.equalsIgnoreCase("1")) {
                jrxmlRemportName = "NPAReminder1";
                fillParam.put("pRegards", " Yours faithfully,\n\n\n\n (SENIOR MANAGER)");
                fillParam.put("pNoteDesc", "(1) If you have already made the payment, please ignore this letter.\n"
                        + "(2) In case of Borrower/Surety(ies) in service, recovery may be initiated through "
                        + "their employer(s) in terms of provisions of DCS Act & Rules.");
            } else {
                jrxmlRemportName = "NPAReminder12";
                fillParam.put("pRegards", " Yours faithfully,\n\n\n\n (SENIOR MANAGER)");
                fillParam.put("pNoteDesc", "(1) If you have already made the payment, please ignore this letter.\n"
                        + "(2) In case of Borrower/Surety(ies) in service, recovery may be initiated "
                        + "through their employer(s) in terms of provisions of DCS Act & Rules.");
            }
            new ReportBean().openPdf(reportName, jrxmlRemportName, new JRBeanCollectionDataSource(npaRemForPrint), fillParam);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", reportName);
        } catch (Exception ex) {
            this.msgStyle = "error";
            this.setMsg(ex.getMessage());
        }
    }

    /* ---Checked or Unchecked the Grid row--- */
    public void checkAllBox() {
        System.out.println("checkAll method");
        if (this.checkAll == true) {
            for (int i = 0; i < overDueList.size(); i++) {
                overDueList.get(i).setCheckBoxFlag(true);
            }
            this.disableCheckBox = true;
        } else {
            this.disableCheckBox = false;
        }

    }

    public void checkNotAllBox() {
        // This method is required for grid check box. 
        // It is using by NPAOverdueReminderCharges.jsp, in grid.
    }

    public void refreshForm() {
        this.acTypeOption = null;
        this.overDueRemOption = null;
        this.acNatureOption = null;
        this.mode = "--Select--";
        this.dt = "";
        this.msgStyle = "";
        this.overDueList = null;
        this.btnName ="Show";
        this.isBankAddress =false;
        this.setMsg("");
    }

    public void clear() {
    }

    public String exitForm() {
        return "case1";
    }
}
