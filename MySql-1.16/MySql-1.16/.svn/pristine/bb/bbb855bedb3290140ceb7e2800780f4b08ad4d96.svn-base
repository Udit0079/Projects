package com.cbs.web.controller.mis;

import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.mis.TaskManagementRemoteFacade;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.pojo.mis.MarketingStatusPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import com.cbs.pojo.mis.TaskUpdationPojo;
import com.cbs.utils.Validator;
import com.cbs.web.controller.ReportBean;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class TaskUpdation extends BaseBean {

    private String errorMsg;
    private String function;
    private String purpose;
    private String status;
    private String purposeTypeData;
    private String enquiryType;
    private String userAssigned;
    private String followDate;
    private String followTime;
    private String customerRemark;
    private String selectHH1;
    private String selectMM1;
    private String selectSS1;
    private String msgvalue;
    private String frmDt;
    private String toDt;
    private String levelId;
    private String accountOpeningDate = "";
    private String referredAccountNo;
    private String refAcno;
    private String detailId = " ";
    private String followUpId = "";
    private String referredAccountNoLabel;
    private String lockerCabinate;
    private String lockerType;
    private String lockerNo;
    private String previousFollowUpTime = "";
    private String personalVisitStaus = "";
    private String personalVisitDate;
    private String selectHH11;
    private String selectMM11;
    private String selectSS11;
    private String branch;
    private String userAssignedFilter;
    private boolean disableReferredAccountNo = true;
    private boolean disableUsertoBeAssigned = true;
    private boolean disablePurpose = true;
    private boolean disablefollowUpData = false;
    private boolean checkBox = false;
    private boolean disableforBrnManager = false;
    private List<SelectItem> functionList;
    private List<SelectItem> purposeList;//423
    private List<SelectItem> purposeTypeDataList;//426
    private List<SelectItem> statusList; //424 cbs_ref_rec_type
    private List<SelectItem> enquiryTypeList;//425
    private List<SelectItem> userAssignedList;// securityinfo
    private List<TaskUpdationPojo> gridDetail;
    private List<SelectItem> branchList;
    private List<TaskUpdationPojo> historyGridDetail;
    private List<MarketingStatusPojo> totalTargetGridDetail;
    private List<SelectItem> selectHH1List;
    private List<SelectItem> selectMM1List;
    private List<SelectItem> selectSS1List;
    private List<SelectItem> selectHH1List1;
    private List<SelectItem> selectMM1List1;
    private List<SelectItem> selectSS1List1;
    private List<TaskUpdationPojo> tempList;
    private List<TaskUpdationPojo> reportList;
    private List<SelectItem> userAssginedFilter;
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote otherFacade;
    private TaskManagementRemoteFacade taskManagement;
    private AdvancesInformationTrackingRemote aitr;
    private String refferedAcDisplay = "none";
    private String refferedAcLabelDisplay = "none";
    private String lockerDisplay = "none";
    private String gridDisplay = "none";
    private String historyGridDisplay = "none";
    private String paneldisplay = "none";
    private String panelViewDisplay = "none"; //for view 
    private String personalVisitDisplay = "none";
    private String viewBranch = "none";
    private final String jndiFtsName = "FtsPostingMgmtFacade";
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dmy_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date dt = new Date();

    public TaskUpdation() {
        this.setErrorMsg(errorMsg);
        try {
            this.setFollowDate(getTodayDate());
            this.setFrmDt(getTodayDate());
            this.setToDt(getTodayDate());
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            taskManagement = (TaskManagementRemoteFacade) ServiceLocator.getInstance().lookup("TaskManagementFacade");
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            functionList = new ArrayList<>();
            functionList.add(new SelectItem("0", "--Select--"));
            functionList.add(new SelectItem("M", "Modify"));
            functionList.add(new SelectItem("V", "View"));
            getAllTypeList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getAllTypeList() {
        try {
            purposeList = new ArrayList<>();
            purposeList.add(new SelectItem("0", "--Select--"));
            List purpList = otherFacade.getRefcodeDesc("423");
            if (!purpList.isEmpty()) {
                for (int i = 0; i < purpList.size(); i++) {
                    Vector v = (Vector) purpList.get(i);
                    purposeList.add(new SelectItem(v.get(0).toString(), v.get(1).toString()));
                }
            }
            purposeTypeDataList = new ArrayList<>();
            purposeTypeDataList.add(new SelectItem("0", "--Select--"));

            statusList = new ArrayList<>();
            statusList.add(new SelectItem("0", "--Select--"));
            List statuslist = otherFacade.getRefcodeDesc("424");
            if (!statuslist.isEmpty()) {
                for (int i = 0; i < statuslist.size(); i++) {
                    Vector v = (Vector) statuslist.get(i);
                    statusList.add(new SelectItem(v.get(0).toString(), v.get(1).toString()));
                }
            }
            userAssignedList = new ArrayList<>();
            userAssignedList.add(new SelectItem("0", "--Select--"));
            List userassigned = null;
            if (getOrgnBrCode().equalsIgnoreCase("90")) {
                userassigned = common.getAllUsernameandUserId();
            } else {
                userassigned = common.getUsernameandUserId(getOrgnBrCode());
            }
            if ((!userassigned.isEmpty()) && (userassigned != null)) {
                for (int i = 0; i < userassigned.size(); i++) {
                    Vector v = (Vector) userassigned.get(i);
                    userAssignedList.add(new SelectItem(v.get(0).toString(), v.get(1).toString() + " [ " + v.get(0).toString() + " ]"));
                }
            }

            enquiryTypeList = new ArrayList<>();
            enquiryTypeList.add(new SelectItem("0", "--Select--"));
            List enquirylist = otherFacade.getRefcodeDesc("425");
            if (!enquirylist.isEmpty()) {
                for (int i = 0; i < enquirylist.size(); i++) {
                    Vector v = (Vector) enquirylist.get(i);
                    enquiryTypeList.add(new SelectItem(v.get(0).toString(), v.get(1).toString()));
                }
            }
            selectHH1List = new ArrayList<>();
            selectMM1List = new ArrayList<>();
            selectSS1List = new ArrayList<>();
            selectSS1List.add(new SelectItem("AM"));
            selectSS1List.add(new SelectItem("PM"));
            for (Integer i = 0; i <= 12; i++) {
                String hour = i.toString();
                int length = hour.length();
                if (length < 2) {
                    hour = "0" + hour;
                }
                selectHH1List.add(new SelectItem(hour));
            }
            for (Integer j = 0; j <= 60; j++) {
                String minute = j.toString();
                int length = minute.length();
                if (length < 2) {
                    minute = "0" + minute;
                }
                selectMM1List.add(new SelectItem(minute));
            }
            selectHH1List1 = new ArrayList<>();
            selectMM1List1 = new ArrayList<>();
            selectSS1List1 = new ArrayList<>();
            selectSS1List1.add(new SelectItem("AM"));
            selectSS1List1.add(new SelectItem("PM"));
            for (Integer i = 0; i <= 12; i++) {
                String hour = i.toString();
                int length = hour.length();
                if (length < 2) {
                    hour = "0" + hour;
                }
                selectHH1List1.add(new SelectItem(hour));
            }
            for (Integer j = 0; j <= 60; j++) {
                String minute = j.toString();
                int length = minute.length();
                if (length < 2) {
                    minute = "0" + minute;
                }
                selectMM1List1.add(new SelectItem(minute));
            }

            branchList = new ArrayList<>();
            branchList.add(new SelectItem("S", "--Select--"));
            userAssginedFilter = new ArrayList<>();
            userAssginedFilter.add(new SelectItem("0", "--Select--"));

            levelId = taskManagement.getLevelIdOfBranch(getOrgnBrCode(), getUserName());

            gridDisplay = "none";
            paneldisplay = "none";
            panelViewDisplay = "none";
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void functionMode() {
        this.setErrorMsg("");
        this.setBranch("S");
        this.setUserAssignedFilter("0");
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                setErrorMsg("please select function");
                return;
            }
            branchList = new ArrayList<>();
            branchList.add(new SelectItem("S", "--Select--"));
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

            if (function.equalsIgnoreCase("M")) {
                paneldisplay = "";
                gridDisplay = "";
                historyGridDisplay = "";
                panelViewDisplay = "none";
                if ((!getOrgnBrCode().equalsIgnoreCase("90")) && (levelId.equalsIgnoreCase("1") || levelId.equalsIgnoreCase("2"))) {
//                    disableUsertoBeAssigned = true;
//                    disablePurpose = true;
//                    disableforBrnManager = true;
                    this.setBranch(getOrgnBrCode());
                    onblurBranchMode();
                } else if ((!(getOrgnBrCode().equalsIgnoreCase("90"))) && (!(levelId.equalsIgnoreCase("1") || levelId.equalsIgnoreCase("2")))) {
                    this.setBranch(getOrgnBrCode());
                    onblurBranchMode();
                    getGridData();
                } else if (getOrgnBrCode().equalsIgnoreCase("90")) {
                    this.setBranch(getOrgnBrCode());
                    onblurBranchMode();
                }

            } else if (function.equalsIgnoreCase("V")) {
                paneldisplay = "none";
                gridDisplay = "none";
                panelViewDisplay = "";
                historyGridDisplay = "none";
                this.setFrmDt(getTodayDate());
                this.setToDt(getTodayDate());
                if (getOrgnBrCode().equalsIgnoreCase("90")) {
                    this.setBranch("0A");
                } else if ((!getOrgnBrCode().equalsIgnoreCase("90")) && (levelId.equalsIgnoreCase("1") || levelId.equalsIgnoreCase("2"))) {
                    this.setBranch(getOrgnBrCode());
                    onblurBranchMode();
                } else if ((!(getOrgnBrCode().equalsIgnoreCase("90"))) && (!(levelId.equalsIgnoreCase("1") || levelId.equalsIgnoreCase("2")))) {
                    this.setBranch(getOrgnBrCode());
                    onblurBranchMode();
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void onblurBranchMode() {
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the function ");
                return;
            }
            if (branch == null || branch.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select the branch");
                return;
            }
            userAssginedFilter = new ArrayList<>();
            userAssginedFilter.add(new SelectItem("0", "--Select--"));
            List userassignedfilter = null;

            if (getOrgnBrCode().equalsIgnoreCase("90")) {
                if (function.equalsIgnoreCase("V") && branch.equalsIgnoreCase("0A")) {
                    userassignedfilter = common.getAllUsernameandUserId();
                } else {
                    userassignedfilter = common.getUsernameandUserId(this.branch);
                }
            } else if ((!(getOrgnBrCode().equalsIgnoreCase("90"))) && (this.levelId.equalsIgnoreCase("1") || this.levelId.equalsIgnoreCase("2"))) {
                userassignedfilter = common.getUsernameandUserId(this.branch);
            } else if ((!(getOrgnBrCode().equalsIgnoreCase("90"))) && (!(this.levelId.equalsIgnoreCase("1") || this.levelId.equalsIgnoreCase("2")))) {
                userassignedfilter = common.getUsernameandUserId(this.branch);
            }
            if (userassignedfilter != null || !(userassignedfilter.isEmpty())) {
                for (int i = 0; i < userassignedfilter.size(); i++) {
                    Vector v = (Vector) userassignedfilter.get(i);
                    if ((!(getOrgnBrCode().equalsIgnoreCase("90"))) && (!(this.levelId.equalsIgnoreCase("1") || this.levelId.equalsIgnoreCase("2")))) {
                        if (getUserName().equalsIgnoreCase(v.get(0).toString())) {
                            userAssginedFilter.add(new SelectItem(v.get(0).toString(), v.get(1).toString() + " [ " + v.get(0).toString() + " ]"));
                            this.setUserAssignedFilter(v.get(0).toString());
                        }
                    } else {
                        userAssginedFilter.add(new SelectItem(v.get(0).toString(), v.get(1).toString() + " [ " + v.get(0).toString() + " ]"));
                    }
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getGridData() {
        this.setErrorMsg("");
        gridDetail = new ArrayList<>();
        refreshfields();
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the function");
                return;
            }

            if (branch == null || branch.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select the branch");
                return;
            }

            if (userAssignedFilter == null || userAssignedFilter.equalsIgnoreCase("")) {
                this.setErrorMsg("Please select the user Assigned");
                return;
            }
            if (function.equalsIgnoreCase("M")) {
                if ((!getOrgnBrCode().equalsIgnoreCase("90")) && (levelId.equalsIgnoreCase("1") || levelId.equalsIgnoreCase("2"))) {
                    if (!(getUserName().equalsIgnoreCase(userAssignedFilter))) {
                        disableUsertoBeAssigned = true;
                        disablePurpose = true;
                        disableforBrnManager = true;
                    } else {
                        disableUsertoBeAssigned = false;
                        disableforBrnManager = false;
                    }
                }

                List datalist = taskManagement.getDataByUserName(getUserName(), getOrgnBrCode(), this.branch, this.userAssignedFilter, this.levelId);
                if (datalist.isEmpty()) {
                    throw new Exception("Data does not exists");
                }
                int srno = 1;
                for (int i = 0; i < datalist.size(); i++) {
                    TaskUpdationPojo pojo = new TaskUpdationPojo();
                    Vector v = (Vector) datalist.get(i);
                    pojo.setSrNo(srno++);
                    pojo.setDetailId(v.get(0) == null ? "" : v.get(0).toString());
                    pojo.setLeadId(v.get(1) == null ? "" : v.get(1).toString());
                    pojo.setAccountNo(v.get(2) == null ? "" : v.get(2).toString().trim());
                    pojo.setCustomerName(v.get(3) == null ? "" : v.get(3).toString().replaceAll("( )+", " "));
                    pojo.setFatherName(v.get(4) == null ? "" : v.get(4).toString().replaceAll("( )+", " "));
                    pojo.setAddress(v.get(5) == null ? "" : v.get(5).toString().replaceAll("( )+", " ") + "," + v.get(6) == null ? "" : v.get(6).toString().trim());
                    pojo.setEmailId(v.get(7) == null ? "" : v.get(7).toString().trim());
                    if ((v.get(8).toString() == null || v.get(8).toString().equalsIgnoreCase("")) && (v.get(9).toString() == null || v.get(9).toString().equalsIgnoreCase(""))) {
                        pojo.setContactNo("");
                    }
                    if (v.get(9).toString() == null || v.get(9).toString().equalsIgnoreCase(" ")) {
                        pojo.setContactNo(v.get(8).toString());
                    }
                    if ((v.get(8).toString() != null || (!v.get(8).toString().equalsIgnoreCase(" "))) && (!v.get(9).toString().equalsIgnoreCase(" "))) {
                        pojo.setContactNo(v.get(8).toString() + " [ " + v.get(9).toString().trim() + " ]");
                    }
                    if (v.get(9).toString() == null || v.get(9).toString().equalsIgnoreCase("")) {
                        pojo.setContactNo(v.get(8).toString());
                    }
                    pojo.setStatus(v.get(10) == null ? "" : v.get(10).toString());
                    String time[] = v.get(12).toString().split(":");
                    if (!(v.get(14).toString().equalsIgnoreCase("N"))) {
                        String followUpTime = "";
                        if (Integer.parseInt(time[0].toString()) == 00) {
                            followUpTime = "12" + ":" + time[1].toString() + " " + "AM";
                        } else {
                            int hour;
                            if (Integer.parseInt(time[0].toString()) < 12) {
                                followUpTime = time[0].toString() + ":" + time[1].toString() + " " + "AM";

                            }
                            if (Integer.parseInt(time[0].toString()) == 12) {
                                followUpTime = "12" + ":" + time[1].toString() + " " + "PM";
                            }
                            if (Integer.parseInt(time[0].toString()) > 12) {
                                hour = Integer.parseInt(time[0]) - 12;
                                followUpTime = String.valueOf(hour) + ":" + time[1].toString() + " " + "PM";
                            }
                        }
                        pojo.setFollowUpDatetime(v.get(11) == null ? "" : v.get(11).toString() + " " + followUpTime);
                    } else {
                        pojo.setFollowUpDatetime(v.get(11) == null ? "" : v.get(11).toString());
                    }
                    pojo.setFollowedDateTime(v.get(13) == null ? "" : v.get(13).toString());
                    gridDetail.add(pojo);
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getGrid() {
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the function");
                return;
            }
            if (function.equalsIgnoreCase("V")) {
                if (branch == null || branch.equalsIgnoreCase("S")) {
                    this.setErrorMsg("Please select the branch");
                    return;
                }
                if (userAssignedFilter == null || userAssignedFilter.equalsIgnoreCase("0")) {
                    this.setErrorMsg("Please select the user assigned");
                    return;
                }
                if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                    setErrorMsg("Please fill From Date!");
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
                if (dmy.parse(frmDt).compareTo(dmy.parse(toDt)) > 0) {
                    setErrorMsg("From date can not be greater than to date!");
                    return;
                }
                List list = taskManagement.getTotalTargetForTaskUpdation(this.frmDt, this.toDt, this.userAssignedFilter, this.branch);
                if (list.isEmpty()) {
                    this.setErrorMsg("Data does not Exists for total target");
                    return;
                }
                totalTargetGridDetail = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    int srno = 1;
                    MarketingStatusPojo pojo = new MarketingStatusPojo();
                    Vector v = (Vector) list.get(i);
                    pojo.setSrno(srno++);
                    pojo.setAssignedUser(v.get(1).toString() == null ? "" : v.get(1).toString());
                    pojo.setNewS(v.get(4).toString() == null ? 0 : Integer.parseInt(v.get(4).toString()));
                    pojo.setFollowup(v.get(5).toString() == null ? 0 : Integer.parseInt(v.get(5).toString()));
                    pojo.setTransfer(v.get(8).toString() == null ? 0 : Integer.parseInt(v.get(8).toString()));
                    pojo.setBooked(v.get(6).toString() == null ? 0 : Integer.parseInt(v.get(6).toString()));
                    pojo.setReject(v.get(7).toString() == null ? 0 : Integer.parseInt(v.get(7).toString()));
                    pojo.setPending(v.get(9).toString() == null ? 0 : Integer.parseInt(v.get(9).toString()));
                    totalTargetGridDetail.add(pojo);
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void onClickcheckBox() {
        this.setPurpose("0");
        this.setPurposeTypeData("0");
        this.setErrorMsg("");
        this.setStatus("0");
        this.setEnquiryType("0");
        this.setCustomerRemark("");
        this.setUserAssigned("0");
        this.personalVisitDisplay = "none";
        this.lockerDisplay = "none";
        this.refferedAcLabelDisplay = "none";
        this.refferedAcDisplay = "none";
        this.setSelectHH1("00");
        this.setFollowDate(getTodayDate());
        this.setSelectMM1("00");
        this.setSelectSS1("AM");
        this.historyGridDetail = new ArrayList<>();
        try {
            int srno = 1;
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the function");
                return;
            }
            tempList = new ArrayList<>();
            for (TaskUpdationPojo pojo : gridDetail) {
                if (pojo.isSelected() == true) {
                    tempList.add(pojo);
                    detailId = pojo.getDetailId();
                }
            }
            if (tempList == null || tempList.isEmpty()) {
                setErrorMsg("Please choose any transcation which you want to update");
                return;
            }
            if (tempList.size() > 1) {
                setErrorMsg("Please choose one transcation at a time");
                return;
            }
            List list = taskManagement.getTaskInformationByDetail(detailId);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector v = (Vector) list.get(0);
                    followUpId = v.get(0).toString();
                    if (v.get(4).toString().equalsIgnoreCase("F") || v.get(4).toString().equalsIgnoreCase("N")) {
                        personalVisitDisplay = "";
                    }
                    setStatus(v.get(4) == null ? "" : v.get(4).toString());
                    setPurpose(v.get(2) == null ? "" : v.get(2).toString());
                    getpurposeTyedata();
                    if (v.get(2).toString().equalsIgnoreCase("LO")) {
                        this.refferedAcLabelDisplay = "";
                        setReferredAccountNoLabel("Locker No. \t " + " " + "  Cabinate / Type / No.");
                        this.lockerDisplay = "";
                        this.refferedAcDisplay = "none";
                        this.refferedAcLabelDisplay = "";
                    }
                    if (!v.get(2).toString().equalsIgnoreCase("LO")) {
                        this.refferedAcLabelDisplay = "";
                        setReferredAccountNoLabel("Conversion A/c No.");
                        this.lockerDisplay = "none";
                        this.refferedAcDisplay = "";
                        setPurposeTypeData(v.get(3).toString());
                    }

                    disableUsertoBeAssigned = false;
                    setUserAssigned(v.get(7) == null ? "" : v.get(7).toString());
                    disableUsertoBeAssigned = true;
                    setEnquiryType(v.get(6) == null ? "" : v.get(6).toString());
                    setFollowDate(v.get(8) == null ? "" : v.get(8).toString());
                    if (!v.get(4).toString().equalsIgnoreCase("N")) {
                        previousFollowUpTime = v.get(9) == null ? "" : v.get(9).toString();
                        String time[] = v.get(9).toString().split(":");
                        setSelectMM1(time[1].toString());
                        if (Integer.parseInt(time[0].toString()) == 00) {
                            setSelectHH1("00");
                            setSelectSS1("AM");
                        } else {
                            int hour;
                            if (Integer.parseInt(time[0].toString()) < 12) {
                                hour = Integer.parseInt(time[0]);
                                setSelectHH1(String.valueOf(hour));
                                setSelectSS1("AM");
                            }
                            if (Integer.parseInt(time[0].toString()) == 12) {
                                setSelectHH1("12");
                                setSelectSS1("PM");
                            }

                            if (Integer.parseInt(time[0].toString()) > 12) {
                                hour = Integer.parseInt(time[0]) - 12;
                                String hours = "";
                                hours = String.valueOf(hour).length() < 2 ? "0" + String.valueOf(hour).toString() : String.valueOf(hour).toString();
                                setSelectHH1(hours);
                                setSelectSS1("PM");
                            }

                        }
                    } else {
                        setSelectHH1("00");
                        setSelectMM1("00");
                        setSelectSS1("AM");
                    }
                }
            }
            historyGridDetail = new ArrayList<>();
            List pojolist = taskManagement.getTaskUpdationHistoryDetails(detailId, userAssignedFilter);
            if (!pojolist.isEmpty()) {
                for (int i = 0; i < pojolist.size(); i++) {
                    Vector v = (Vector) pojolist.get(i);
                    TaskUpdationPojo pojo = new TaskUpdationPojo();
                    pojo.setSrNo(srno++);
                    pojo.setAccountNo(v.get(0) == null ? "" : v.get(0).toString());
                    pojo.setCustomerName(v.get(1) == null ? "" : v.get(1).toString());
                    pojo.setStatus(v.get(2) == null ? "" : v.get(2).toString());
                    pojo.setEnquiryType(v.get(3) == null ? "" : v.get(3).toString());
                    pojo.setFollowUpRemarks(v.get(4) == null ? "" : v.get(4).toString());
                    String time[] = v.get(6).toString().split(":");
                    if (v.get(10).toString().equalsIgnoreCase("N")) {
                        pojo.setFollowUpDatetime(v.get(7) == null ? "" : v.get(7).toString());
                    } else {
                        String followUpTime = "";
                        if (Integer.parseInt(time[0].toString()) == 00) {
                            followUpTime = "12" + ":" + time[1].toString() + " " + "AM";
                        } else {
                            int hour;
                            if (Integer.parseInt(time[0].toString()) < 12) {
                                followUpTime = time[0].toString() + ":" + time[1].toString() + " " + "AM";
                            }
                            if (Integer.parseInt(time[0].toString()) == 12) {
                                followUpTime = "12" + ":" + time[1].toString() + " " + "PM";
                            }
                            if (Integer.parseInt(time[0].toString()) > 12) {
                                hour = Integer.parseInt(time[0]) - 12;
                                String hours = "";
                                hours = String.valueOf(hour).length() < 2 ? "0" + String.valueOf(hour).toString() : String.valueOf(hour).toString();
                                followUpTime = hours + ":" + time[1].toString() + " " + "PM";
                            }
                        }
                        pojo.setFollowUpDatetime(v.get(7) == null ? "" : v.get(7).toString() + " " + followUpTime);
                    }
                    pojo.setFollowedDateTime(v.get(5) == null ? "" : v.get(5).toString());
                    historyGridDetail.add(pojo);
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getpurposeTyedata() {
        this.setErrorMsg("");
        try {
            if (this.purpose == null || this.purpose.equalsIgnoreCase("0")) {
                setErrorMsg("Please select Purpose");
                return;
            }
            List purposetypeDataList = aitr.getListAsPerRequirement("426", getPurpose(), "0", "0", "0", "0",
                    ymd.format(new Date()), 0);
            purposeTypeDataList = new ArrayList();
            purposeTypeDataList.add(new SelectItem("0", "--Select--"));
            if (!purposetypeDataList.isEmpty()) {
                for (int i = 0; i < purposetypeDataList.size(); i++) {
                    Vector v = (Vector) purposetypeDataList.get(i);
                    purposeTypeDataList.add(new SelectItem(v.get(0).toString(), v.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void buttonClick() {
        setErrorMsg("");
        try {
            String followUpTime = "";
            String personalVisitTime = "";
            if (!isDisableforBrnManager()) {
                if (function == null || function.equalsIgnoreCase("0")) {
                    this.setErrorMsg("Please select the function");
                    return;
                }

                if (this.enquiryType == null || this.enquiryType.equalsIgnoreCase("0")) {
                    setErrorMsg("Please select enquiry type");
                    return;
                }
                if (this.status == null || status.equalsIgnoreCase("0")) {
                    setErrorMsg("Please select the status");
                    return;
                }
                if (status.equalsIgnoreCase("N")) {
                    this.setErrorMsg("Please select the another status");
                    return;
                }
                if (status.equalsIgnoreCase("T")) {
                    if (userAssigned == null || userAssigned.equalsIgnoreCase("0")) {
                        setErrorMsg("Please select any user ");
                        return;
                    }
                    if (userAssigned.equalsIgnoreCase(getUserName())) {
                        this.setErrorMsg("Please select another user");
                        return;
                    }
                }
                if (status.equalsIgnoreCase("B")) {
                    if (refAcno == null || refAcno.equalsIgnoreCase("")) {
                        setErrorMsg("Please enter the valid conversion  Account number");
                        return;
                    }
//                if (purpose.equalsIgnoreCase("LO")) {
//                    if (lockerCabinate == null || lockerCabinate.equalsIgnoreCase("")) {
//                        setErrorMsg("Please enter the locker cabinate");
//                        return;
//                    }
//                    if (lockerType == null || lockerType.equalsIgnoreCase("")) {
//                        setErrorMsg("Please enter the locker Type");
//                        return;
//                    }
//                    if (lockerNo == null || lockerNo.equalsIgnoreCase("")) {
//                        setErrorMsg("Please enter the locker no");
//                        return;
//                    }
//                }
                }
                if (this.status.equalsIgnoreCase("F")) {
                    if (isCheckBox() == true) {
                        if (this.selectHH11 == null || this.selectMM11 == null || this.selectSS11 == null || this.selectHH11.equalsIgnoreCase("")
                                || this.selectMM11.equalsIgnoreCase("")) {
                            setErrorMsg("Please enter the proper follow up time");
                            return;
                        }
                        if (selectSS11.equalsIgnoreCase("AM")) {
                            personalVisitTime = selectHH11 + ":" + selectMM11 + ":00";
                        } else {
                            int hour;
                            if (Integer.parseInt(selectHH11) == 12) {
                                hour = Integer.parseInt(selectHH11) + 0;
                            } else {
                                hour = Integer.parseInt(selectHH11) + 12;
                            }
                            personalVisitTime = hour + ":" + selectMM11 + ":00";
                        }
                    }
                }

                if (this.followDate == null || this.followDate.equalsIgnoreCase("")) {
                    setErrorMsg("Please enter follow up date");
                    return;
                }
                if (this.selectHH1 == null || this.selectMM1 == null || this.selectSS1 == null || this.selectHH1.equalsIgnoreCase("")
                        || this.selectMM1.equalsIgnoreCase("") || this.selectSS1.equalsIgnoreCase("")) {
                    setErrorMsg("Please enter the proper follow up time");
                    return;
                }
                if (selectSS1.equalsIgnoreCase("AM")) {
                    followUpTime = selectHH1 + ":" + selectMM1 + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(selectHH1) == 12) {
                        hour = Integer.parseInt(selectHH1) + 0;
                    } else {
                        hour = Integer.parseInt(selectHH1) + 12;
                    }
                    followUpTime = hour + ":" + selectMM1 + ":00";
                }
//                if (!(status.equalsIgnoreCase("B") || this.status.equalsIgnoreCase("R"))) {
//                    if (dmy.parse(followDate).before(dmy.parse(getTodayDate()))) {
//                        setErrorMsg("Follow date can not be smaller than today date!");
//                        return;
//                    }
//                }
                String result = taskManagement.updateDataTaskUpdation(tempList, this.purpose, this.purposeTypeData, this.status,
                        this.userAssigned, this.enquiryType, this.followDate, followUpTime, this.customerRemark, getUserName(),
                        this.refAcno, accountOpeningDate, personalVisitStaus, personalVisitTime, personalVisitDate, checkBox);
                if (result.equalsIgnoreCase("true")) {
                    getGridData();
                    setErrorMsg("Your Transcation has been updated Sucessfully");

                }
                refreshfields();
            } else if (isDisableforBrnManager()) {
                if (this.followDate == null || this.followDate.equalsIgnoreCase("")) {
                    setErrorMsg("Please enter follow up date");
                    return;
                }
                if (this.selectHH1 == null || this.selectMM1 == null || this.selectSS1 == null || this.selectHH1.equalsIgnoreCase("")
                        || this.selectMM1.equalsIgnoreCase("") || this.selectSS1.equalsIgnoreCase("")) {
                    setErrorMsg("Please enter the proper follow up time");
                    return;
                }
                if (selectSS1.equalsIgnoreCase("AM")) {
                    followUpTime = selectHH1 + ":" + selectMM1 + ":00";
                } else {
                    int hour;
                    if (Integer.parseInt(selectHH1) == 12) {
                        hour = Integer.parseInt(selectHH1) + 0;
                    } else {
                        hour = Integer.parseInt(selectHH1) + 12;
                    }
                    followUpTime = hour + ":" + selectMM1 + ":00";
                }
                String result1 = taskManagement.updateFollowUpDateByBranchManager(followUpId, detailId, this.followDate, followUpTime, getUserName());
                if (result1.equalsIgnoreCase("true")) {
                    refreshfields();
                    setErrorMsg("Your follow Up time is Sucessfully updated");
                } else {
                    setErrorMsg(result1);
                    return;
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void printReport() {
        this.setErrorMsg("");
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                setErrorMsg("Please select the function");
                return;
            }

            if (this.branch == null || this.branch.equalsIgnoreCase("S")) {
                setErrorMsg("Please select the branch");
                return;
            }
            if (this.userAssignedFilter == null || this.userAssignedFilter.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the user assigned");
                return;
            }

            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill From Date!");
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
            if (dmy.parse(frmDt).compareTo(dmy.parse(toDt)) > 0) {
                setErrorMsg("From date can not be greater than to date!");
                return;
            }
            String report = "Lead Updation Report";
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
            if (getOrgnBrCode().equalsIgnoreCase("90")) {
                reportList = taskManagement.getUpdationReportDataList(this.frmDt, this.toDt, getOrgnBrCode(), getUserName(), levelId, this.branch, this.userAssignedFilter);
            } else {
                reportList = taskManagement.getUpdationReportDataList(this.frmDt, this.toDt, getOrgnBrCode(), getUserName(), levelId, this.branch, this.userAssignedFilter);
            }
            if (reportList.isEmpty()) {
                this.setErrorMsg("Data does not exists");
                return;
            }
            new ReportBean().jasperReport("Task_Updation_Report", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "Lead Updation Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void btnPdfAction() {
        this.setErrorMsg("");
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                setErrorMsg("Please select the function");
                return;
            }

            if (this.branch == null || this.branch.equalsIgnoreCase("S")) {
                setErrorMsg("Please select the branch");
                return;
            }
            if (this.userAssignedFilter == null || this.userAssignedFilter.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the user assigned");
                return;
            }
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill From Date!");
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
            if (dmy.parse(frmDt).compareTo(dmy.parse(toDt)) > 0) {
                setErrorMsg("From date can not be greater than to date!");
                return;
            }
            String report = "Lead Updation Report";
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
            if (getOrgnBrCode().equalsIgnoreCase("90")) {
                reportList = taskManagement.getUpdationReportDataList(this.frmDt, this.toDt, getOrgnBrCode(), getUserName(), levelId, this.branch, this.userAssignedFilter);
            } else {
                reportList = taskManagement.getUpdationReportDataList(this.frmDt, this.toDt, getOrgnBrCode(), getUserName(), levelId, this.branch, this.userAssignedFilter);
            }
            if (reportList.isEmpty()) {
                this.setErrorMsg("Data does not exists");
                return;
            }
            new ReportBean().openPdf("Task_Updation_Report" + ymd.format(dmy.parse(this.frmDt)) + " to " + ymd.format(dmy.parse(this.toDt)),
                    "Task_Updation_Report", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
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

    public void downloadExcel() {
        this.setErrorMsg("");
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                setErrorMsg("Please select the function");
                return;
            }

            if (this.branch == null || this.branch.equalsIgnoreCase("S")) {
                setErrorMsg("Please select the branch");
                return;
            }
            if (this.userAssignedFilter == null || this.userAssignedFilter.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the user assigned");
                return;
            }
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill From Date!");
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
            if (dmy.parse(frmDt).compareTo(dmy.parse(toDt)) > 0) {
                setErrorMsg("From date can not be greater than to date!");
                return;
            }
            String report = "Lead Updation Report";
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
            fillParams.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
            reportList = new ArrayList<>();
            if (getOrgnBrCode().equalsIgnoreCase("90")) {
                reportList = taskManagement.getUpdationReportDataList(this.frmDt, this.toDt, getOrgnBrCode(), getUserName(), levelId, this.branch, this.userAssignedFilter);
            } else {
                reportList = taskManagement.getUpdationReportDataList(this.frmDt, this.toDt, getOrgnBrCode(), getUserName(), levelId, this.branch, this.userAssignedFilter);
            }
            if (reportList.isEmpty()) {
                this.setErrorMsg("Data does not exists");
                return;
            }
            new ReportBean().dynamicExcelJasper("Task_Updation_Report", "excel", new JRBeanCollectionDataSource(reportList), fillParams, "Lead Updation Report");

        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void validateReferredAccountNo() {
        this.setErrorMsg("");
        try {
            if (!purpose.equalsIgnoreCase("LO")) {
                if (this.referredAccountNo == null || this.referredAccountNo.equalsIgnoreCase("")) {
                    this.setErrorMsg("Please enter the proper conversion account no");
                    return;
                }
                //
                String referredCheckAllowed = otherFacade.getCodeFromCbsParameterInfo("MKT-ACCOUNT-CHECK-NOT-ALLOWED");
                if (!referredCheckAllowed.contains(this.purpose)) {
                    List list = taskManagement.getRefferedAccountNoDetail(this.referredAccountNo);
                    if (!list.isEmpty()) {
                        this.setErrorMsg("This account no is already exists");
                        return;
                    }
                    if (this.referredAccountNo.length() == 0 || this.referredAccountNo.length() != 12) {
                        this.setErrorMsg("Please enter the proper conversion account no");
                        return;
                    }
                    if (!referredAccountNo.matches("[0-9]+")) {
                        this.setErrorMsg("Please enter valid  account no.");
                        return;
                    }
                    String datedAllowed = otherFacade.getCodeFromCbsParameterInfo("MKT-BACK-DATED-ENTRY-ALLOWED");
                    accountOpeningDate = common.getAccountOpeningDate(referredAccountNo);
                    if (accountOpeningDate.equalsIgnoreCase("")) {
                        this.setErrorMsg("Please check your account no");
                        return;
                    }
                    long dayDiff = common.dayDifference(accountOpeningDate, ymd.format(dt));
                    if (dayDiff <= Long.parseLong(datedAllowed)) {
                        this.setErrorMsg("Conversion account no. has been validated");
                        refAcno = referredAccountNo;
                    } else {
                        this.setErrorMsg("Conversion account no. has not been validated");
                        return;
                    }
                }
            } else {
                if (this.lockerCabinate == null || this.lockerCabinate.equalsIgnoreCase("") || this.lockerCabinate.length() == 0) {
                    this.setErrorMsg("Please check the locker cabinate");
                    return;
                }
                if (!lockerCabinate.matches("[0-9]+")) {
                    this.setErrorMsg("Please Enter Valid locker cabinate");
                    return;
                }
                if (this.lockerType == null || lockerType.equalsIgnoreCase("") || lockerType.length() == 0) {
                    this.setErrorMsg("Please check the locker Type");
                    return;
                }
                if (!lockerType.matches("[a-zA-Z_]+")) {
                    this.setErrorMsg("please fill Valid locker Type");
                    return;
                }
                if (this.lockerNo == null || lockerNo.equalsIgnoreCase("") || lockerNo.length() == 0) {
                    this.setErrorMsg("Please check the locker no");
                    return;
                }
                if (!lockerNo.matches("[0-9]+")) {
                    this.setErrorMsg("Please Enter Valid  locker no");
                    return;
                }
                refAcno = lockerCabinate + "/" + lockerType + "/" + lockerNo;
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void onClickedcheckBox() {
        try {
            if (isCheckBox() == true) {
                personalVisitStaus = "Y";
                this.setPersonalVisitDate(getTodayDate());
            }
            if (isCheckBox() == false) {
                personalVisitStaus = "N";
                this.setPersonalVisitDate("");
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void checkUserAssgined() {
        if (userAssigned.equalsIgnoreCase(getUserName())) {
            this.setErrorMsg("Please select another user");
            return;
        }
    }

    public void getUserAssignedEnabled() {
        this.setErrorMsg("");
        try {
            referredAccountNo = "";
            lockerNo = "";
            lockerCabinate = "";
            lockerType = "";
            this.disableUsertoBeAssigned = true;
            this.personalVisitDisplay = "none";
            this.setCheckBox(false);
            this.setPersonalVisitDate("");
            this.setSelectHH11("00");
            this.setSelectMM11("00");
            this.setSelectSS11("AM");
            if (status == null || status.equalsIgnoreCase("")) {
                setErrorMsg("Please select status");
                return;
            }
            if (status.equalsIgnoreCase("N")) {
                this.setErrorMsg("Please select the another status");
                return;
            }
            if (status.equalsIgnoreCase("F")) {
                personalVisitDisplay = "";
            }
            if (status.equalsIgnoreCase("T")) {
                this.disableUsertoBeAssigned = false;
            }
            if (status.equalsIgnoreCase("B")) {
                this.disableReferredAccountNo = false;
                this.disablefollowUpData = true;
            }
            if (status.equalsIgnoreCase("R")) {
                this.disablefollowUpData = true;
            }
            if (!status.equalsIgnoreCase("B")) {
                this.disableReferredAccountNo = true;
            }
            if (!(status.equalsIgnoreCase("B") || status.equalsIgnoreCase("R"))) {
                this.disablefollowUpData = false;
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void onButtonClick() {
        try {
            if (function.equalsIgnoreCase("M")) {
                this.setMsgvalue("Are you sure to update the transcation.");
            }
            if (isDisableforBrnManager()) {
                this.setMsgvalue("Are you sure for update the follow Up date");
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void refreshfields() {
        try {
            for (TaskUpdationPojo pojo : tempList) {
                if (pojo.isSelected()) {
                    gridDetail.remove(pojo);
                }
            }
            historyGridDetail = new ArrayList<>();
            this.setPurpose("0");
            this.setPurposeTypeData("0");
            this.setEnquiryType("0");
            this.setStatus("0");
            this.setUserAssigned("0");
            this.accountOpeningDate = "";
            this.referredAccountNo = "";
            this.setReferredAccountNo(" ");
            this.setFollowDate(getTodayDate());
            this.setSelectHH1("00");
            this.setSelectMM1("00");
            this.setSelectSS1("AM");
            this.setCustomerRemark("");
            this.setLockerCabinate("");
            this.setLockerType("");
            this.setLockerNo("");
            this.setCheckBox(false);
            this.setPersonalVisitDate("");
            this.setSelectHH11("00");
            this.setSelectMM11("00");
            this.setSelectSS11("AM");
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void refresh() {
        try {
            setErrorMsg("");
            setFunction("0");
            setPurpose("0");
            setPurposeTypeData("0");
            setUserAssigned("0");
            setBranch("S");
            setUserAssignedFilter("0");
            setStatus("0");
            setEnquiryType("0");
            setCustomerRemark("");
            setFollowDate(getTodayDate());
            setSelectHH1("00");
            setSelectMM1("00");
            setSelectMM1("AM");
            this.setCheckBox(false);
            this.setPersonalVisitDate("");
            this.setSelectHH11("00");
            this.setSelectMM11("00");
            this.setSelectSS11("AM");
            this.setReferredAccountNo("");
            this.disablePurpose = true;
            this.disableReferredAccountNo = true;
            this.refferedAcLabelDisplay = "none";
            this.viewBranch = "none";
            this.refferedAcDisplay = "none";
            this.lockerDisplay = "none";
            this.paneldisplay = "none";
            this.gridDisplay = "none";
            this.panelViewDisplay = "none";
            this.historyGridDisplay = "none";
            gridDetail = new ArrayList<>();
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public String exit() {
        return "case1";
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPurposeTypeData() {
        return purposeTypeData;
    }

    public void setPurposeTypeData(String purposeTypeData) {
        this.purposeTypeData = purposeTypeData;
    }

    public String getEnquiryType() {
        return enquiryType;
    }

    public void setEnquiryType(String enquiryType) {
        this.enquiryType = enquiryType;
    }

    public String getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(String userAssigned) {
        this.userAssigned = userAssigned;
    }

    public String getFollowDate() {
        return followDate;
    }

    public void setFollowDate(String followDate) {
        this.followDate = followDate;
    }

    public String getFollowTime() {
        return followTime;
    }

    public void setFollowTime(String followTime) {
        this.followTime = followTime;
    }

    public String getCustomerRemark() {
        return customerRemark;
    }

    public void setCustomerRemark(String customerRemark) {
        this.customerRemark = customerRemark;
    }

    public String getSelectHH1() {
        return selectHH1;
    }

    public void setSelectHH1(String selectHH1) {
        this.selectHH1 = selectHH1;
    }

    public String getSelectMM1() {
        return selectMM1;
    }

    public void setSelectMM1(String selectMM1) {
        this.selectMM1 = selectMM1;
    }

    public String getSelectSS1() {
        return selectSS1;
    }

    public void setSelectSS1(String selectSS1) {
        this.selectSS1 = selectSS1;
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

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public List<SelectItem> getEnquiryTypeList() {
        return enquiryTypeList;
    }

    public void setEnquiryTypeList(List<SelectItem> enquiryTypeList) {
        this.enquiryTypeList = enquiryTypeList;
    }

    public List<SelectItem> getUserAssignedList() {
        return userAssignedList;
    }

    public void setUserAssignedList(List<SelectItem> userAssignedList) {
        this.userAssignedList = userAssignedList;
    }

    public List<TaskUpdationPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<TaskUpdationPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public List<SelectItem> getSelectHH1List() {
        return selectHH1List;
    }

    public void setSelectHH1List(List<SelectItem> selectHH1List) {
        this.selectHH1List = selectHH1List;
    }

    public List<SelectItem> getSelectMM1List() {
        return selectMM1List;
    }

    public void setSelectMM1List(List<SelectItem> selectMM1List) {
        this.selectMM1List = selectMM1List;
    }

    public List<SelectItem> getSelectSS1List() {
        return selectSS1List;
    }

    public void setSelectSS1List(List<SelectItem> selectSS1List) {
        this.selectSS1List = selectSS1List;
    }

    public boolean isDisableUsertoBeAssigned() {
        return disableUsertoBeAssigned;
    }

    public void setDisableUsertoBeAssigned(boolean disableUsertoBeAssigned) {
        this.disableUsertoBeAssigned = disableUsertoBeAssigned;
    }

    public boolean isDisablePurpose() {
        return disablePurpose;
    }

    public void setDisablePurpose(boolean disablePurpose) {
        this.disablePurpose = disablePurpose;
    }

    public List<TaskUpdationPojo> getTempList() {
        return tempList;
    }

    public void setTempList(List<TaskUpdationPojo> tempList) {
        this.tempList = tempList;
    }

    public String getGridDisplay() {
        return gridDisplay;
    }

    public void setGridDisplay(String gridDisplay) {
        this.gridDisplay = gridDisplay;
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

    public String getPanelViewDisplay() {
        return panelViewDisplay;
    }

    public void setPanelViewDisplay(String panelViewDisplay) {
        this.panelViewDisplay = panelViewDisplay;
    }

    public String getPaneldisplay() {
        return paneldisplay;
    }

    public void setPaneldisplay(String paneldisplay) {
        this.paneldisplay = paneldisplay;
    }

    public String getReferredAccountNo() {
        return referredAccountNo;
    }

    public void setReferredAccountNo(String referredAccountNo) {
        this.referredAccountNo = referredAccountNo;
    }

    public boolean isDisableReferredAccountNo() {
        return disableReferredAccountNo;
    }

    public void setDisableReferredAccountNo(boolean disableReferredAccountNo) {
        this.disableReferredAccountNo = disableReferredAccountNo;
    }

    public List<TaskUpdationPojo> getHistoryGridDetail() {
        return historyGridDetail;
    }

    public void setHistoryGridDetail(List<TaskUpdationPojo> historyGridDetail) {
        this.historyGridDetail = historyGridDetail;
    }

    public String getHistoryGridDisplay() {
        return historyGridDisplay;
    }

    public void setHistoryGridDisplay(String historyGridDisplay) {
        this.historyGridDisplay = historyGridDisplay;
    }

    public boolean isDisablefollowUpData() {
        return disablefollowUpData;
    }

    public void setDisablefollowUpData(boolean disablefollowUpData) {
        this.disablefollowUpData = disablefollowUpData;
    }

    public String getReferredAccountNoLabel() {
        return referredAccountNoLabel;
    }

    public void setReferredAccountNoLabel(String referredAccountNoLabel) {
        this.referredAccountNoLabel = referredAccountNoLabel;
    }

    public String getLockerCabinate() {
        return lockerCabinate;
    }

    public void setLockerCabinate(String lockerCabinate) {
        this.lockerCabinate = lockerCabinate;
    }

    public String getLockerType() {
        return lockerType;
    }

    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
    }

    public String getLockerNo() {
        return lockerNo;
    }

    public void setLockerNo(String lockerNo) {
        this.lockerNo = lockerNo;
    }

    public String getRefferedAcDisplay() {
        return refferedAcDisplay;
    }

    public void setRefferedAcDisplay(String refferedAcDisplay) {
        this.refferedAcDisplay = refferedAcDisplay;
    }

    public String getLockerDisplay() {
        return lockerDisplay;
    }

    public void setLockerDisplay(String lockerDisplay) {
        this.lockerDisplay = lockerDisplay;
    }

    public String getRefferedAcLabelDisplay() {
        return refferedAcLabelDisplay;
    }

    public void setRefferedAcLabelDisplay(String refferedAcLabelDisplay) {
        this.refferedAcLabelDisplay = refferedAcLabelDisplay;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public String getPersonalVisitDate() {
        return personalVisitDate;
    }

    public void setPersonalVisitDate(String personalVisitDate) {
        this.personalVisitDate = personalVisitDate;
    }

    public String getSelectHH11() {
        return selectHH11;
    }

    public void setSelectHH11(String selectHH11) {
        this.selectHH11 = selectHH11;
    }

    public String getSelectMM11() {
        return selectMM11;
    }

    public void setSelectMM11(String selectMM11) {
        this.selectMM11 = selectMM11;
    }

    public String getSelectSS11() {
        return selectSS11;
    }

    public void setSelectSS11(String selectSS11) {
        this.selectSS11 = selectSS11;
    }

    public List<SelectItem> getSelectHH1List1() {
        return selectHH1List1;
    }

    public void setSelectHH1List1(List<SelectItem> selectHH1List1) {
        this.selectHH1List1 = selectHH1List1;
    }

    public List<SelectItem> getSelectMM1List1() {
        return selectMM1List1;
    }

    public void setSelectMM1List1(List<SelectItem> selectMM1List1) {
        this.selectMM1List1 = selectMM1List1;
    }

    public List<SelectItem> getSelectSS1List1() {
        return selectSS1List1;
    }

    public void setSelectSS1List1(List<SelectItem> selectSS1List1) {
        this.selectSS1List1 = selectSS1List1;
    }

    public String getPersonalVisitDisplay() {
        return personalVisitDisplay;
    }

    public void setPersonalVisitDisplay(String personalVisitDisplay) {
        this.personalVisitDisplay = personalVisitDisplay;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getViewBranch() {
        return viewBranch;
    }

    public void setViewBranch(String viewBranch) {
        this.viewBranch = viewBranch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getUserAssignedFilter() {
        return userAssignedFilter;
    }

    public void setUserAssignedFilter(String userAssignedFilter) {
        this.userAssignedFilter = userAssignedFilter;
    }

    public List<SelectItem> getUserAssginedFilter() {
        return userAssginedFilter;
    }

    public void setUserAssginedFilter(List<SelectItem> userAssginedFilter) {
        this.userAssginedFilter = userAssginedFilter;
    }

    public boolean isDisableforBrnManager() {
        return disableforBrnManager;
    }

    public void setDisableforBrnManager(boolean disableforBrnManager) {
        this.disableforBrnManager = disableforBrnManager;
    }

    public List<MarketingStatusPojo> getTotalTargetGridDetail() {
        return totalTargetGridDetail;
    }

    public void setTotalTargetGridDetail(List<MarketingStatusPojo> totalTargetGridDetail) {
        this.totalTargetGridDetail = totalTargetGridDetail;
    }
}
