package com.cbs.web.controller.mis;

import com.cbs.dto.YearEndDatePojo;
import com.cbs.facade.mis.TaskManagementRemoteFacade;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.pojo.mis.TaskLeadTargetPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class TaskLeadTarget extends BaseBean {

    private String errorMsg;
    private String function;
    private String branch;
    private String frmDt;
    private String toDt;
    private String finacialYear;
    private int assignedTarget;
    private String purpose;
    private String purposeTypeData;
    private String msgvalue;
    private String finacialFrmDate;
    private String finacialToDate;
    private String timeLimit;
    private String sourceofMarketing;
    private String entryType;
    private String userAssigned;
    private String panelViewDisplay = "none";
    private String purposedisplay = "none";
    private String hoHistoryGridDisplay = "none";
    private String gridDisplay = "none";
    private String hogridDisplay = "none";
    private String dailyDisplay = "none";
    private String labelValue = "Purpose";
    private String panelAddDisplay = "none";
    private String entryTypeDisplay = "none";
    private String monthlygridDisplay = "none";
    private String userassignedDisplay = "none";
    private boolean disable = false;
    private String buttonValue = "Save";
    private List<TaskLeadTargetPojo> gridDetail;
    private List<TaskLeadTargetPojo> hoGridDetail;
    private List<TaskLeadTargetPojo> monthlyGridDetail;
    private List<TaskLeadTargetPojo> hoHistoryGridDetail;
    private TaskLeadTargetPojo tempCurrentItem;
    private List<TaskLeadTargetPojo> reportList;
    private List<SelectItem> purposeList;//423
    private List<SelectItem> purposeTypeDataList;//426
    private List<SelectItem> branchList;
    private List<SelectItem> finacialYearList;
    private List<SelectItem> functionList;
    private List<SelectItem> timeLimitList;
    private List<SelectItem> marketingSourceList;
    private List<SelectItem> entryTypeList;
    private List<SelectItem> userAssignedList;
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote otherFacade;
    private RbiReportFacadeRemote rbiFacade;
    private TaskManagementRemoteFacade taskManagement;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public TaskLeadTarget() {
        this.setErrorMsg("");
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            rbiFacade = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup("RbiReportFacade");
            taskManagement = (TaskManagementRemoteFacade) ServiceLocator.getInstance().lookup("TaskManagementFacade");
            functionList = new ArrayList<>();
            this.setFrmDt(getTodayDate());
            this.setToDt(getTodayDate());
            functionList.add(new SelectItem("0", "--Select--"));
            functionList.add(new SelectItem("A", "Add"));
            functionList.add(new SelectItem("V", "View"));
            gridDetail = new ArrayList<>();
            getAllTypeList();
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getAllTypeList() {
        this.setErrorMsg("");
        try {

            finacialYearList = new ArrayList<>();
            finacialYearList.add(new SelectItem("0", "--Select--"));

            YearEndDatePojo pojo = rbiFacade.getYearEndData(getOrgnBrCode());

            finacialYearList.add(new SelectItem(pojo.getfYear(), pojo.getfYear()));
            this.setFinacialYear(pojo.getfYear());
            finacialFrmDate = dmy.format(ymd.parse(pojo.getMinDate().toString()));
            finacialToDate = dmy.format(ymd.parse(pojo.getMaxDate().toString()));

            List purpList = otherFacade.getRefcodeDesc("423");
            purposeList = new ArrayList<>();
            purposeList.add(new SelectItem("0", "--Select--"));
            if (!purpList.isEmpty()) {
                for (int i = 0; i < purpList.size(); i++) {
                    Vector v = (Vector) purpList.get(i);
                    purposeList.add(new SelectItem(v.get(0).toString(), v.get(1).toString()));
                }
            }

            branchList = new ArrayList<>();
            branchList.add(new SelectItem("0", "--Select--"));
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getBranhList() {
        this.setErrorMsg("");
        try {
            branchList = new ArrayList<>();
            branchList.add(new SelectItem("0", "--Select--"));
            if (getOrgnBrCode().equalsIgnoreCase("90")) {
                if (function.equalsIgnoreCase("V")) {
                    branchList = new ArrayList<>();
                    branchList.add(new SelectItem("0A", "ALL"));
                }
            }
            List list = common.getAlphacodeBasedOnOrgnBranch(getOrgnBrCode());
            if (list.isEmpty()) {
                this.setErrorMsg("Please define branches.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector vec = (Vector) list.get(i);
                branchList.add(new SelectItem(vec.get(1).toString().length() < 2 ? "0" + vec.get(1).toString()
                        : vec.get(1).toString(), vec.get(0).toString()));
            }

            this.setBranch(getOrgnBrCode());

            if (!(getOrgnBrCode().equalsIgnoreCase("90"))) {
                userAssignedList = new ArrayList<>();
                userAssignedList.add(new SelectItem("0", "--Select--"));
                List userassigned = common.getUsernameandUserId(getOrgnBrCode());
                if (!userassigned.isEmpty()) {
                    for (int i = 0; i < userassigned.size(); i++) {
                        Vector v = (Vector) userassigned.get(i);
                        userAssignedList.add(new SelectItem(v.get(0).toString(), v.get(1).toString() + " [ " + v.get(0).toString() + " ]"));
                    }
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void functionMode() {
        this.setErrorMsg("");
        this.setEntryType("0");
        this.disable = false;
        this.setFrmDt(getTodayDate());
        this.setToDt(getTodayDate());
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select function");
                return;
            }
            if (function.equalsIgnoreCase("A")) {
                panelAddDisplay = "";
                getBranhList();
                if (getOrgnBrCode().equalsIgnoreCase("90")) {
                    purposedisplay = "";
                    dailyDisplay = "none";
                    panelViewDisplay = "none";
                    monthlygridDisplay = "none";
                    entryTypeDisplay = "";
                    hogridDisplay = "";
                    userassignedDisplay = "none";
                    this.setLabelValue("Purpose");
                    entryTypeList = new ArrayList();
                    entryTypeList.add(new SelectItem("0", "--Select--"));
                    String date[] = getTodayDate().split("/");
                    String day = date[0];
                    String datedAllowed = otherFacade.getCodeFromCbsParameterInfo("MKT-LEAD-TARGET-ENTRY-DATE-ALLOWED");
                    if ((Integer.parseInt(day) - Integer.parseInt(datedAllowed)) <= 0) {
                        entryTypeList.add(new SelectItem("Y", "Yearly"));
                        entryTypeList.add(new SelectItem("M", "Monthly"));
                    }
                    this.setFrmDt(finacialFrmDate);
                    this.setToDt(finacialToDate);
                    // onBlurGridDetail();
                }
                if (!(getOrgnBrCode().equalsIgnoreCase("90"))) {
                    purposedisplay = "";
                    entryTypeDisplay = "";
                    panelViewDisplay = "none";
                    this.setLabelValue("Purpose");
                    dailyDisplay = "none";
                    monthlygridDisplay = "none";
                    hoHistoryGridDisplay = "";
                    userassignedDisplay = "";
                    entryTypeList = new ArrayList();
                    entryTypeList.add(new SelectItem("0", "--Select--"));
                    String date[] = getTodayDate().split("/");
                    String day = date[0];
                    String datedAllowed = otherFacade.getCodeFromCbsParameterInfo("MKT-LEAD-TARGET-ENTRY-DATE-ALLOWED");
                    if ((Integer.parseInt(day) - Integer.parseInt(datedAllowed)) <= 0) {
                        entryTypeList.add(new SelectItem("M", "Monthly"));
                        entryTypeList.add(new SelectItem("D", "Daily"));
                    } else if ((Integer.parseInt(day) - Integer.parseInt(datedAllowed)) > 0) {
                        entryTypeList.add(new SelectItem("D", "Daily"));
                    }
                    List list = taskManagement.getYearlyTargetHistoryDetails(getOrgnBrCode());
                    if (list == null || list.isEmpty()) {
                        throw new Exception(" History Data does not exists");
                    }
                    int srno = 1;
                    hoHistoryGridDetail = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        Vector v = (Vector) list.get(i);
                        TaskLeadTargetPojo pojo = new TaskLeadTargetPojo();
                        pojo.setSrNo(srno++);
                        pojo.setAssignedTarget(v.get(2) == null ? 0 : Integer.parseInt(v.get(2).toString()));
                        pojo.setPurpose(v.get(0) == null ? "" : v.get(0).toString());
                        pojo.setAchievedTarget(v.get(3) == null ? 0 : Integer.parseInt(v.get(3).toString()));
                        hoHistoryGridDetail.add(pojo);
                    }
                }
            } else if (function.equalsIgnoreCase("V")) {
                panelViewDisplay = "";
                this.entryTypeDisplay = "";
                hogridDisplay = "none";
                hoHistoryGridDisplay = "none";
                gridDisplay = "none";
                userassignedDisplay = "none";
                this.panelAddDisplay = "none";
                this.dailyDisplay = "none";
                this.monthlygridDisplay = "none";
                getBranhList();
                entryTypeList = new ArrayList<>();
                entryTypeList.add(new SelectItem("0", "--Select--"));
                entryTypeList.add(new SelectItem("M", "Monthly"));
                entryTypeList.add(new SelectItem("D", "Daily"));
                if (getOrgnBrCode().equalsIgnoreCase("90")) {
                    this.setBranch("0A");
                    entryTypeList = new ArrayList<>();
                    entryTypeList.add(new SelectItem("0", "--Select--"));
                    entryTypeList.add(new SelectItem("Y", "Yearly"));
                    entryTypeList.add(new SelectItem("M", "Monthly"));
                    entryTypeList.add(new SelectItem("D", "Daily"));
                }
                if (!(getOrgnBrCode().equalsIgnoreCase("90") || getOrgnBrCode().equalsIgnoreCase("0A"))) {
                    entryTypeList = new ArrayList<>();
                    entryTypeList.add(new SelectItem("0", "--Select--"));
                    entryTypeList.add(new SelectItem("M", "Monthly"));
                    entryTypeList.add(new SelectItem("D", "Daily"));
                }
                this.setFrmDt(getTodayDate());
                this.setToDt(getTodayDate());
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void entryTypeMode() {
        this.setErrorMsg("");
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the function");
                return;
            }
            if (branch == null || branch.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the branch");
                return;
            }
            if (entryType == null || entryType.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the entry Type");
                return;
            }
            if (function.equalsIgnoreCase("A")) {
                if (entryType.equalsIgnoreCase("Y")) {
                    hogridDisplay = "";
                    userassignedDisplay = "none";
                    hoHistoryGridDisplay = "none";
                    monthlygridDisplay = "none";
                    onBlurGridDetail();
                } else if (entryType.equalsIgnoreCase("M")) {
                    this.setLabelValue("Purpose");
                    purposedisplay = "";
                    hogridDisplay = "none";
                    dailyDisplay = "none";
                    monthlygridDisplay = "";
                    hoHistoryGridDisplay = "none";
                    gridDisplay = "none";
                    userassignedDisplay = "";
                    if (getOrgnBrCode().equalsIgnoreCase("90")) {
                        userAssignedList = new ArrayList<>();
                        userAssignedList.add(new SelectItem("0", "--Select--"));
                        List userassigned = common.getUsernameandUserId(this.branch);
                        if (!userassigned.isEmpty()) {
                            for (int i = 0; i < userassigned.size(); i++) {
                                Vector v = (Vector) userassigned.get(i);
                                userAssignedList.add(new SelectItem(v.get(0).toString(), v.get(1).toString() + " [ " + v.get(0).toString() + " ]"));
                            }
                        }
                    }
                    String monthLastDt = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(ymd.format(dmy.parse(getTodayDate())).substring(4, 6).trim()),
                            Integer.parseInt(ymd.format(dmy.parse(getTodayDate())).substring(0, 4).trim())));
                    String firstdate = CbsUtil.getFirstDateOfGivenDate(new Date());
                    System.out.println(firstdate);
                    this.setFrmDt(dmy.format(ymd.parse(firstdate)));
                    this.setToDt(dmy.format(ymd.parse(monthLastDt)));
                    onBlurGridDetail();
                } else if (entryType.equalsIgnoreCase("D")) {
                    purposedisplay = "none";
                    dailyDisplay = "";
                    gridDisplay = "";
                    hogridDisplay = "none";
                    monthlygridDisplay = "none";
                    hoHistoryGridDisplay = "";
                    userassignedDisplay = "";
                    this.setLabelValue("Source of Marketing");
                    this.setFrmDt(getTodayDate());
                    this.setToDt(getTodayDate());
                    List sourceOfmktList = otherFacade.getRefcodeDesc("422");
                    marketingSourceList = new ArrayList<>();
                    marketingSourceList.add(new SelectItem("0", "--Select--"));
                    if (!sourceOfmktList.isEmpty()) {
                        for (int i = 0; i < sourceOfmktList.size(); i++) {
                            Vector v = (Vector) sourceOfmktList.get(i);
                            marketingSourceList.add(new SelectItem(v.get(0).toString(), v.get(1).toString()));
                        }
                    }
                    onBlurGridDetail();
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void onBlurGridDetail() {
        try {
            hoGridDetail = new ArrayList<>();
            gridDetail = new ArrayList<>();
            monthlyGridDetail = new ArrayList<>();
            if (getOrgnBrCode().equalsIgnoreCase("90")) {
                if (entryType.equalsIgnoreCase("Y")) {
                    List list = taskManagement.getTargetDetails(getOrgnBrCode(), this.branch, this.entryType);
                    if (list == null || list.isEmpty()) {
                        this.setErrorMsg("Data does not exists");
                    }
                    int srno = 1;
                    for (int i = 0; i < list.size(); i++) {
                        TaskLeadTargetPojo pojo = new TaskLeadTargetPojo();
                        Vector v = (Vector) list.get(i);
                        pojo.setSrNo(srno++);
                        pojo.setPurpose(v.get(2) == null ? "" : v.get(2).toString());
                        pojo.setAssignedTarget(v.get(6).toString() == null ? 0 : Integer.parseInt(v.get(6).toString()));
                        pojo.setBranch(v.get(5) == null ? "" : v.get(5).toString());
                        pojo.setAchievedTarget(v.get(7).toString() == null ? 0 : Integer.parseInt(v.get(7).toString()));
                        hoGridDetail.add(pojo);
                        pojo.setBrnCode(v.get(1) == null ? "" : v.get(1).toString());
                        pojo.setEntryType(v.get(3) == null ? "" : v.get(3).toString());
                        pojo.setPurposeId(v.get(4).toString() == null ? "" : v.get(4).toString());
                        pojo.setFrmDate(v.get(9).toString() == null ? "" : v.get(9).toString());
                        pojo.setToDate(v.get(10).toString() == null ? "" : v.get(10).toString());
                        pojo.setFinacialYear(v.get(8).toString() == null ? "" : v.get(8).toString());
                        pojo.setId(Long.parseLong(v.get(11).toString()));
                        pojo.setEntryDate(v.get(12).toString() == null ? "" : v.get(12).toString());

                    }
                } else if (entryType.equalsIgnoreCase("M")) {
                    List list1 = taskManagement.getTargetDetails(getOrgnBrCode(), this.branch, this.entryType);
                    if (list1 == null || list1.isEmpty()) {
                        throw new Exception("Data does not exists");
                    }

                    int srno = 1;
                    for (int i = 0; i < list1.size(); i++) {
                        TaskLeadTargetPojo pojo = new TaskLeadTargetPojo();
                        Vector v = (Vector) list1.get(i);
                        pojo.setSrNo(srno++);
                        pojo.setPurpose(v.get(2) == null ? "" : v.get(2).toString());
                        pojo.setAssignedTarget(v.get(10) == null ? 0 : Integer.parseInt(v.get(10).toString()));
                        pojo.setAssignedUser(v.get(5) == null ? "" : v.get(5).toString());
                        pojo.setEnterBy(v.get(7) == null ? "" : v.get(7).toString());
                        pojo.setEntryDate(v.get(8) == null ? "" : v.get(8).toString());
                        pojo.setBranch(v.get(9) == null ? "" : v.get(9).toString());
                        monthlyGridDetail.add(pojo);
                        pojo.setBrnCode(v.get(1).toString() == null ? "" : v.get(1).toString());
                        pojo.setEntryType(v.get(3).toString() == null ? "" : v.get(3).toString());
                        pojo.setPurposeId(v.get(4).toString() == null ? "" : v.get(4).toString());
                        pojo.setFinacialYear(v.get(11).toString() == null ? "" : v.get(11).toString());
                        pojo.setFrmDate(v.get(12).toString() == null ? "" : v.get(12).toString());
                        pojo.setToDate(v.get(13).toString() == null ? "" : v.get(13).toString());
                        pojo.setAssignedUserId(v.get(14).toString() == null ? "" : v.get(14).toString());
                        pojo.setId(Long.parseLong(v.get(15).toString()));
                    }
                }
            }

            if ((!getOrgnBrCode().equalsIgnoreCase("90"))) {
                if (entryType.equalsIgnoreCase("D")) {
                    List list1 = taskManagement.getTargetDetails(getOrgnBrCode(), this.branch, this.entryType);
                    if (list1 == null || list1.isEmpty()) {
                        throw new Exception("Data does not exists");
                    }
                    int srno = 1;
                    for (int i = 0; i < list1.size(); i++) {
                        TaskLeadTargetPojo pojo = new TaskLeadTargetPojo();
                        Vector v = (Vector) list1.get(i);
                        pojo.setSrNo(srno++);
                        pojo.setSourceOfMarketing(v.get(2) == null ? "" : v.get(2).toString());
                        pojo.setAssignedTarget(v.get(9).toString() == null ? 0 : Integer.parseInt(v.get(9).toString()));
                        pojo.setAssignedUser(v.get(10) == null ? "" : v.get(10).toString());
                        pojo.setEnterBy(v.get(6) == null ? "" : v.get(6).toString());
                        pojo.setEntryDate(v.get(7) == null ? "" : v.get(7).toString());
                        pojo.setBranch(v.get(8) == null ? "" : v.get(8).toString());
                        gridDetail.add(pojo);
                        pojo.setBrnCode(v.get(1).toString() == null ? "" : v.get(1).toString());
                        pojo.setEntryType(v.get(3).toString() == null ? "" : v.get(3).toString());
                        pojo.setSourceOfmktId(v.get(4).toString() == null ? "" : v.get(4).toString());
                        pojo.setFinacialYear(v.get(11).toString() == null ? "" : v.get(11).toString());
                        pojo.setFrmDate(v.get(12).toString() == null ? "" : v.get(12).toString());
                        pojo.setToDate(v.get(13).toString() == null ? "" : v.get(13).toString());
                        pojo.setAssignedUserId(v.get(14).toString() == null ? "" : v.get(14).toString());
                        pojo.setId(Long.parseLong(v.get(15).toString()));

                    }
                }
            }
            if ((!getOrgnBrCode().equalsIgnoreCase("90"))) {
                if (entryType.equalsIgnoreCase("M")) {
                    List list1 = taskManagement.getTargetDetails(getOrgnBrCode(), this.branch, this.entryType);
                    if (list1 == null || list1.isEmpty()) {
                        throw new Exception("Data does not exists");
                    }
                    int srno = 1;
                    for (int i = 0; i < list1.size(); i++) {
                        TaskLeadTargetPojo pojo = new TaskLeadTargetPojo();
                        Vector v = (Vector) list1.get(i);
                        pojo.setSrNo(srno++);
                        pojo.setPurpose(v.get(2) == null ? "" : v.get(2).toString());
                        pojo.setAssignedTarget(v.get(10) == null ? 0 : Integer.parseInt(v.get(10).toString()));
                        pojo.setAssignedUser(v.get(5) == null ? "" : v.get(5).toString());
                        pojo.setEnterBy(v.get(7) == null ? "" : v.get(7).toString());
                        pojo.setEntryDate(v.get(8) == null ? "" : v.get(8).toString());
                        pojo.setBranch(v.get(9) == null ? "" : v.get(9).toString());
                        monthlyGridDetail.add(pojo);
                        pojo.setBrnCode(v.get(1).toString() == null ? "" : v.get(1).toString());
                        pojo.setEntryType(v.get(3).toString() == null ? "" : v.get(3).toString());
                        pojo.setPurposeId(v.get(4).toString() == null ? "" : v.get(4).toString());
                        pojo.setFinacialYear(v.get(11).toString() == null ? "" : v.get(11).toString());
                        pojo.setFrmDate(v.get(12).toString() == null ? "" : v.get(12).toString());
                        pojo.setToDate(v.get(13).toString() == null ? "" : v.get(13).toString());
                        pojo.setAssignedUserId(v.get(14).toString() == null ? "" : v.get(14).toString());
                        pojo.setId(Long.parseLong(v.get(15).toString()));
                    }
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void onButtonClicked() {
        try {
            if (function.equals("A")) {
                this.setMsgvalue("Are you sure to insert the transcation");
            }
            if (isDisable()) {
                this.setMsgvalue("Are you sure to update the transcation");
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void buttonClick() {
        this.setErrorMsg("");
        try {

            if (!isDisable()) {
                if (this.function == null || this.function.equalsIgnoreCase("0")) {
                    this.setErrorMsg("Please select function");
                    return;
                }
                if (this.branch == null || this.branch.equalsIgnoreCase("S")) {
                    this.setErrorMsg("Please select branch");
                    return;
                }
                if (!(getOrgnBrCode().equalsIgnoreCase("90"))) {
                    if (entryType == null || entryType.equalsIgnoreCase("0")) {
                        this.setErrorMsg("Please select the entry type");
                        return;
                    }
                }
                if (this.finacialYear == null || this.finacialYear.equalsIgnoreCase("0")) {
                    this.setErrorMsg("Please select the finacial year");
                    return;
                }
                if (this.frmDt == null || this.frmDt.equalsIgnoreCase("")) {
                    this.setErrorMsg("Please enter the from date");
                    return;
                }
                if (this.toDt == null || this.toDt.equalsIgnoreCase("")) {
                    this.setErrorMsg("Please enter the to date");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(frmDt)) {
                    setErrorMsg("Please fill Proper From Date");
                    return;
                }
                if (toDt == null || toDt.equalsIgnoreCase("")) {
                    setErrorMsg("Please fill To Date!");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(toDt)) {
                    setErrorMsg("Please fill Proper To Date!");
                    return;
                }
                if (getOrgnBrCode().equalsIgnoreCase("90")) {
                    if (dmy.parse(frmDt).compareTo(dmy.parse(toDt)) > 0) {
                        setErrorMsg("From date can not be greater than to date!");
                        return;
                    }
                    if (purpose == null || purpose.equalsIgnoreCase("0")) {
                        this.setErrorMsg("Please select the purpose");
                        return;
                    }
                }
                if (!(getOrgnBrCode().equalsIgnoreCase("90"))) {
                    if (entryType == null || entryType.equalsIgnoreCase("0")) {
                        this.setErrorMsg("Please  select the entry type");
                        return;
                    }
                    if (entryType.equalsIgnoreCase("M")) {
                        int monthCompare = CbsUtil.monthDiff(ymd.parse(ymd.format(dmy.parse(frmDt))), ymd.parse(ymd.format(dmy.parse(toDt))));
                        if (monthCompare != 0) {
                            this.setErrorMsg("Please enter the same month date");
                            return;
                        }
                        if (purpose == null || purpose.equalsIgnoreCase("0")) {
                            this.setErrorMsg("Please select the purpose");
                            return;
                        }
                    }
                    if (entryType.equalsIgnoreCase("D")) {
                        if (!dmy.parse(frmDt).equals(dmy.parse(toDt))) {
                            this.setErrorMsg("Please enter the today date");
                        }
                        if (sourceofMarketing == null || sourceofMarketing.equalsIgnoreCase("0")) {
                            this.setErrorMsg("Please select the source of marketing");
                            return;
                        }
                        if (userAssigned == null || userAssigned.equalsIgnoreCase("0")) {
                            this.setErrorMsg("Please select the user to be Assigned");
                            return;
                        }
                    }
                }
                if (assignedTarget == 0) {
                    this.setErrorMsg("Please enter the proper assigned target");
                    return;
                }
                String result = taskManagement.insertDataLeadTarget(getOrgnBrCode(), this.branch, BigInteger.valueOf(Long.parseLong(finacialYear)),
                        this.purpose, this.sourceofMarketing, frmDt, toDt, BigInteger.valueOf(assignedTarget), this.userAssigned, entryType, getUserName());
                if (result.equalsIgnoreCase("true")) {
                    onBlurGridDetail();
                    this.setErrorMsg("Data is successfully inserted");
                }
            } else if (isDisable()) {
                if (assignedTarget == 0) {
                    this.setErrorMsg("Please enter the proper assigned target");
                    return;
                }
                if (!dmy.parse(tempCurrentItem.getEntryDate()).equals(dmy.parse(getTodayDate()))) {
                    setErrorMsg("You can only update the target on same date!");
                    return;
                }
                String result1 = taskManagement.updateTaskLeadTarget(tempCurrentItem, this.assignedTarget);
                if (result1.equalsIgnoreCase("true")) {
                    onBlurGridDetail();
                    this.setErrorMsg("Target has beeen updated successfully");
                } else {
                    this.setErrorMsg(result1);
                }
            }
            this.disable = false;
            this.setPurpose("0");
            this.setSourceofMarketing("0");
            this.setAssignedTarget(0);
            this.setUserAssigned("0");
            this.setFrmDt(getTodayDate());
            this.setToDt(getTodayDate());
            this.setButtonValue("Save");
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void targetUpdate() {
        this.disable = false;
        try {
            this.setBranch(tempCurrentItem.getBrnCode());
            this.setEntryType(tempCurrentItem.getEntryType());
            this.setFinacialYear(tempCurrentItem.getFinacialYear());
            this.setFrmDt(tempCurrentItem.getFrmDate());
            this.setToDt(tempCurrentItem.getToDate());
            this.setPurpose(tempCurrentItem.getPurposeId());
            this.setAssignedTarget(tempCurrentItem.getAssignedTarget());
            this.setUserAssigned(tempCurrentItem.getAssignedUserId());
            this.setSourceofMarketing(tempCurrentItem.getSourceOfmktId());
            this.disable = true;
            this.buttonValue = "Update";
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void printReport() {
        this.setErrorMsg("");
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setErrorMsg("please select the fucntion");
            }
            if (this.finacialYear == null || this.finacialYear.equals("0")) {
                this.setErrorMsg("please choose the finacial year");
            }
            if (this.frmDt == null || this.frmDt.equalsIgnoreCase("")) {
                this.setErrorMsg("please enter the from date");
            }
            if (this.toDt == null || this.toDt.equals("")) {
                this.setErrorMsg("please enter the to date");
            }
            if (!getOrgnBrCode().equalsIgnoreCase("90")) {
                if (entryType == null || entryType.equalsIgnoreCase("0")) {
                    this.setErrorMsg("please select the entry type");
                    return;
                }
            }
            String report = "Lead Target Report";
            if (entryType.equalsIgnoreCase("D")) {
                report = "Lead Target Daily  Branch Report";
            }

            String pdate = getTodayDate();
            List brList = null;
            String brnName = "", brnAddress = "";
            brList = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brList.size() > 0) {
                brnName = (String) brList.get(0);
                brnAddress = (String) brList.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", brnName);
            fillParams.put("pBnkAddr", brnAddress);
            fillParams.put("pRepName", report);
            fillParams.put("pRepDate", frmDt + " to " + toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintDate", pdate);
            reportList = new ArrayList<>();
            if (entryType.equalsIgnoreCase("M") || entryType.equalsIgnoreCase("Y")) {
                reportList = taskManagement.getTargetReportData(this.branch, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), getOrgnBrCode(), entryType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().jasperReport("Task_Lead_Target_Report", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Lead Target Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);

            } else if (entryType.equalsIgnoreCase("D")) {
                reportList = taskManagement.getTargetReportData(this.branch, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), getOrgnBrCode(), this.entryType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().jasperReport("Lead_Target_Branch_Daily_Report", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Lead Target Daily Branch Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void pdfDownload() {
        this.setErrorMsg("");
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setErrorMsg("please select the fucntion");
            }
            if (this.finacialYear == null || this.finacialYear.equals("0")) {
                this.setErrorMsg("please choose the finacial year");
            }
            if (this.frmDt == null || this.frmDt.equalsIgnoreCase("")) {
                this.setErrorMsg("please enter the from date");
            }
            if (this.toDt == null || this.toDt.equals("")) {
                this.setErrorMsg("please enter the to date");
            }
            if (entryType == null || entryType.equalsIgnoreCase("0")) {
                this.setErrorMsg("please select the entry type");
                return;
            }
            String report = "Lead Target Report";
            if (entryType.equalsIgnoreCase("D")) {
                report = "Lead Target Daily Branch Report";
            }
            String pdate = getTodayDate();
            List brList = null;
            String brnName = "", brnAddress = "";
            brList = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brList.size() > 0) {
                brnName = (String) brList.get(0);
                brnAddress = (String) brList.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", brnName);
            fillParams.put("pBnkAddr", brnAddress);
            fillParams.put("pRepName", report);
            fillParams.put("pRepDate", this.frmDt + " to " + this.toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintDate", pdate);
            reportList = new ArrayList<>();
            if (entryType.equalsIgnoreCase("M") || entryType.equalsIgnoreCase("Y")) {
                reportList = taskManagement.getTargetReportData(this.branch, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), getOrgnBrCode(), this.entryType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().openPdf("Task_Lead_Target_Report" + ymd.format(dmy.parse(this.frmDt)) + " to " + ymd.format(dmy.parse(this.toDt)), "Task_Lead_Target_Report", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);
            } else if (entryType.equalsIgnoreCase("D")) {
                reportList = taskManagement.getTargetReportData(this.branch, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), getOrgnBrCode(), this.entryType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().openPdf("Lead_Target_Branch_Daily_Report" + ymd.format(dmy.parse(this.frmDt)) + " to " + ymd.format(dmy.parse(this.toDt)), "Lead_Target_Branch_Daily_Report", new JRBeanCollectionDataSource(reportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getRequest().getSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void downloadExcel() {
        this.setErrorMsg("");
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setErrorMsg("please select the fucntion");
            }
            if (this.finacialYear == null || this.finacialYear.equals("0")) {
                this.setErrorMsg("please choose the finacial year");
            }
            if (this.frmDt == null || this.frmDt.equalsIgnoreCase("")) {
                this.setErrorMsg("please enter the from date");
            }
            if (this.toDt == null || this.toDt.equals("")) {
                this.setErrorMsg("please enter the to date");
            }
            if (entryType == null || entryType.equalsIgnoreCase("0")) {
                this.setErrorMsg("please select the entry type");
                return;
            }
            String report = "Lead Target Report";
            if (entryType.equalsIgnoreCase("D")) {
                report = "Lead Target Daily Branch Report";
            }

            String pdate = getTodayDate();
            List brList = null;
            String brnName = "", brnAddress = "";
            brList = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brList.size() > 0) {
                brnName = (String) brList.get(0);
                brnAddress = (String) brList.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", brnName);
            fillParams.put("pBnkAddr", brnAddress);
            fillParams.put("pRepName", report);
            fillParams.put("pRepDate", frmDt + " to " + toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintDate", pdate);
            fillParams.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);

            reportList = new ArrayList<>();
            if (entryType.equalsIgnoreCase("M") || entryType.equalsIgnoreCase("Y")) {
                reportList = taskManagement.getTargetReportData(this.branch, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), getOrgnBrCode(), entryType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().dynamicExcelJasper("Task_Lead_Target_Report", "excel",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Lead Target Report");
            } else if (entryType.equalsIgnoreCase("D")) {
                reportList = taskManagement.getTargetReportData(this.branch, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), getOrgnBrCode(), this.entryType);
                if (reportList.isEmpty()) {
                    this.setErrorMsg("Data does not exists");
                    return;
                }
                new ReportBean().dynamicExcelJasper("Lead_Target_Branch_Daily_Report", "excel",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Lead Target Daily Branch Report");
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }

    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void refresh() {
        try {
            this.setErrorMsg("");
            this.setBranch("0");
            this.setFunction("0");
            this.setFinacialYear("0");
            this.setFrmDt(getTodayDate());
            this.setToDt(getTodayDate());
            this.setPurpose("0");
            this.setPurposeTypeData("0");
            this.setEntryType("0");
            this.setAssignedTarget(0);
            this.setSourceofMarketing("0");
            this.setUserAssigned("0");
            this.gridDisplay = "none";
            this.hogridDisplay = "none";
            this.panelViewDisplay = "none";
            this.disable = false;
            gridDetail = new ArrayList<>();
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public String exit() {
        return "case1";
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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

    public String getFinacialYear() {
        return finacialYear;
    }

    public void setFinacialYear(String finacialYear) {
        this.finacialYear = finacialYear;
    }

    public int getAssignedTarget() {
        return assignedTarget;
    }

    public void setAssignedTarget(int assignedTarget) {
        this.assignedTarget = assignedTarget;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurposeTypeData() {
        return purposeTypeData;
    }

    public void setPurposeTypeData(String purposeTypeData) {
        this.purposeTypeData = purposeTypeData;
    }

    public List<TaskLeadTargetPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<TaskLeadTargetPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public TaskLeadTargetPojo getTempCurrentItem() {
        return tempCurrentItem;
    }

    public void setTempCurrentItem(TaskLeadTargetPojo tempCurrentItem) {
        this.tempCurrentItem = tempCurrentItem;
    }

    public String getMsgvalue() {
        return msgvalue;
    }

    public void setMsgvalue(String msgvalue) {
        this.msgvalue = msgvalue;
    }

    public List<SelectItem> getPurposeList() {
        return purposeList;
    }

    public void setPurposeList(List<SelectItem> purposeList) {
        this.purposeList = purposeList;
    }

    public List<SelectItem> getPurposeTypeDataList() {
        return purposeTypeDataList;
    }

    public void setPurposeTypeDataList(List<SelectItem> purposeTypeDataList) {
        this.purposeTypeDataList = purposeTypeDataList;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public List<SelectItem> getFinacialYearList() {
        return finacialYearList;
    }

    public void setFinacialYearList(List<SelectItem> finacialYearList) {
        this.finacialYearList = finacialYearList;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getPanelViewDisplay() {
        return panelViewDisplay;
    }

    public void setPanelViewDisplay(String panelViewDisplay) {
        this.panelViewDisplay = panelViewDisplay;
    }

    public String getGridDisplay() {
        return gridDisplay;
    }

    public void setGridDisplay(String gridDisplay) {
        this.gridDisplay = gridDisplay;
    }

    public List<SelectItem> getTimeLimitList() {
        return timeLimitList;
    }

    public void setTimeLimitList(List<SelectItem> timeLimitList) {
        this.timeLimitList = timeLimitList;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public List<TaskLeadTargetPojo> getReportList() {
        return reportList;
    }

    public void setReportList(List<TaskLeadTargetPojo> reportList) {
        this.reportList = reportList;
    }

    public List<SelectItem> getMarketingSourceList() {
        return marketingSourceList;
    }

    public void setMarketingSourceList(List<SelectItem> marketingSourceList) {
        this.marketingSourceList = marketingSourceList;
    }

    public String getSourceofMarketing() {
        return sourceofMarketing;
    }

    public void setSourceofMarketing(String sourceofMarketing) {
        this.sourceofMarketing = sourceofMarketing;
    }

    public String getPurposedisplay() {
        return purposedisplay;
    }

    public void setPurposedisplay(String purposedisplay) {
        this.purposedisplay = purposedisplay;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getDailyDisplay() {
        return dailyDisplay;
    }

    public void setDailyDisplay(String dailyDisplay) {
        this.dailyDisplay = dailyDisplay;
    }

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public List<SelectItem> getEntryTypeList() {
        return entryTypeList;
    }

    public void setEntryTypeList(List<SelectItem> entryTypeList) {
        this.entryTypeList = entryTypeList;
    }

    public String getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(String userAssigned) {
        this.userAssigned = userAssigned;
    }

    public List<SelectItem> getUserAssignedList() {
        return userAssignedList;
    }

    public void setUserAssignedList(List<SelectItem> userAssignedList) {
        this.userAssignedList = userAssignedList;
    }

    public String getPanelAddDisplay() {
        return panelAddDisplay;
    }

    public void setPanelAddDisplay(String panelAddDisplay) {
        this.panelAddDisplay = panelAddDisplay;
    }

    public String getEntryTypeDisplay() {
        return entryTypeDisplay;
    }

    public void setEntryTypeDisplay(String entryTypeDisplay) {
        this.entryTypeDisplay = entryTypeDisplay;
    }

    public String getHogridDisplay() {
        return hogridDisplay;
    }

    public void setHogridDisplay(String hogridDisplay) {
        this.hogridDisplay = hogridDisplay;
    }

    public List<TaskLeadTargetPojo> getHoGridDetail() {
        return hoGridDetail;
    }

    public void setHoGridDetail(List<TaskLeadTargetPojo> hoGridDetail) {
        this.hoGridDetail = hoGridDetail;
    }

    public String getHoHistoryGridDisplay() {
        return hoHistoryGridDisplay;
    }

    public void setHoHistoryGridDisplay(String hoHistoryGridDisplay) {
        this.hoHistoryGridDisplay = hoHistoryGridDisplay;
    }

    public List<TaskLeadTargetPojo> getHoHistoryGridDetail() {
        return hoHistoryGridDetail;
    }

    public void setHoHistoryGridDetail(List<TaskLeadTargetPojo> hoHistoryGridDetail) {
        this.hoHistoryGridDetail = hoHistoryGridDetail;
    }

    public List<TaskLeadTargetPojo> getMonthlyGridDetail() {
        return monthlyGridDetail;
    }

    public void setMonthlyGridDetail(List<TaskLeadTargetPojo> monthlyGridDetail) {
        this.monthlyGridDetail = monthlyGridDetail;
    }

    public String getMonthlygridDisplay() {
        return monthlygridDisplay;
    }

    public void setMonthlygridDisplay(String monthlygridDisplay) {
        this.monthlygridDisplay = monthlygridDisplay;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getButtonValue() {
        return buttonValue;
    }

    public void setButtonValue(String buttonValue) {
        this.buttonValue = buttonValue;
    }

    public String getUserassignedDisplay() {
        return userassignedDisplay;
    }

    public void setUserassignedDisplay(String userassignedDisplay) {
        this.userassignedDisplay = userassignedDisplay;
    }
}
