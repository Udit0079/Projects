/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.mis;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.mis.TaskManagementRemoteFacade;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import com.cbs.pojo.mis.TaskManagementPojo;
import com.cbs.web.controller.ReportBean;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class TaskManagement extends BaseBean {

    private String errorMsg;
    private String function;
    private String dataSource;
    private String originBy;
    private String marketingSource;
    private String originbyFilter;
    private String branch;
    private String remarks;
    private String frmDt;
    private String toDt;
    private String customerName;
    private String newUserAccountNo;
    private String fatherName;
    private String contactNo;
    private String alternateContactNo;
    private String emailId;
    private String address;
    private String city;
    private String newAccountNo;
    private String purpose;
    private String purposeTypeData;
    private String status;
    private String enquiryType;
    private String userAssigned;
    private String followDate;
    private String followTime;
    private String customerRemark;
    private String msgvalue;
    private boolean allSelected;
    private String branchHo;
    private String searchBy = "";
    private String searchByOption = "";
    private boolean disableNewUserAcNo = true;
    private List<SelectItem> functionList;
    private List<SelectItem> originByList;
    private List<SelectItem> originByFilterList;
    private List<SelectItem> dataSourceList;
    private List<SelectItem> marketingSourceList;
    private List<SelectItem> modeList;
    private List<SelectItem> branchList;
    private List<SelectItem> branchHoList;
    private List<SelectItem> purposeList;//423
    private List<SelectItem> purposeTypeDataList;//426
    private List<SelectItem> statusList; //424 cbs_ref_rec_type
    private List<SelectItem> enquiryTypeList;//425
    private List<SelectItem> userAssignedList;// securityinfo
    private List<SelectItem> searchByList;
    private List<SelectItem> searchByOptionList;
    private List<TaskManagementPojo> tempCurrentItemList;
    private List<TaskManagementPojo> gridDetail;
    private List<TaskManagementPojo> reportList;
    private TaskManagementPojo tempCurrentItem;
    private String panelDisplay1 = "none";
    private String panelDisplay2 = "none";
    private String onSelectFunctionPanel = "none";
    private String commonDisplay = "none";
    private String mainPanelForAdd = "none";
    private String panelByOrigin = "none";
    private String buttonValue = "Save";
    private String viewpdfbutton = "none";
    private String viewBranch = "none";
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote otherFacade;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private TaskManagementRemoteFacade taskManagement;
    private AdvancesInformationTrackingRemote aitr;
    private final String jndiFtsName = "FtsPostingMgmtFacade";
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public TaskManagement() {
        setErrorMsg("");
        try {
            setFrmDt(getTodayDate());
            setToDt(getTodayDate());
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiFtsName);
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            taskManagement = (TaskManagementRemoteFacade) ServiceLocator.getInstance().lookup("TaskManagementFacade");
            //functionList
            functionList = new ArrayList<>();
            functionList.add(new SelectItem("0", "--Select--"));
            functionList.add(new SelectItem("A", "ADD"));
            functionList.add(new SelectItem("V", "VIEW"));

            originByList = new ArrayList<>();
            originByList.add(new SelectItem("0", "--Select--"));

            dataSourceList = new ArrayList<>();
            dataSourceList.add(new SelectItem("0", "--Select--"));

            marketingSourceList = new ArrayList<>();
            marketingSourceList.add(new SelectItem("0", "--Select--"));

            originByFilterList = new ArrayList<>();
            originByFilterList.add(new SelectItem("0", "--Select--"));
            getAllTypeList();
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getCommonListforFunctionType() {
        this.setErrorMsg("");
        try {
            originByList = new ArrayList<>();
            originByList.add(new SelectItem("0", "--Select--"));
            if (function.equalsIgnoreCase("V")) {
                originByList.add(new SelectItem("All", "ALL"));
            }
            if (function.equalsIgnoreCase("A")) {
                List originlist = otherFacade.getRefcodeDesc("428");
                if (!originlist.isEmpty()) {
                    for (int i = 0; i < originlist.size(); i++) {
                        Vector v = (Vector) originlist.get(i);
                        originByList.add(new SelectItem(v.get(0).toString(), v.get(1).toString()));
                    }
                }
            }
            dataSourceList = new ArrayList<>();
            dataSourceList.add(new SelectItem("0", "--Select--"));
            if (function.equalsIgnoreCase("V")) {
                dataSourceList.add(new SelectItem("All", "ALL"));
            }
            List datasourcelist = otherFacade.getRefcodeDesc("421");
            if (!datasourcelist.isEmpty()) {
                for (int i = 0; i < datasourcelist.size(); i++) {
                    Vector v = (Vector) datasourcelist.get(i);
                    dataSourceList.add(new SelectItem(v.get(0).toString(), v.get(1).toString()));
                }
            }
            marketingSourceList = new ArrayList<>();
            marketingSourceList.add(new SelectItem("0", "--Select--"));
            if (function.equalsIgnoreCase("V")) {
                marketingSourceList.add(new SelectItem("All", "ALL"));
            }
            List marketsourcelist = otherFacade.getRefcodeDesc("422");
            if (!marketsourcelist.isEmpty()) {
                for (int i = 0; i < marketsourcelist.size(); i++) {
                    Vector v = (Vector) marketsourcelist.get(i);
                    marketingSourceList.add(new SelectItem(v.get(0).toString(), v.get(1).toString()));
                }
            }
            originByFilterList = new ArrayList<>();
            originByFilterList.add(new SelectItem("0", "--Select--"));
            List usertypelist = otherFacade.getRefcodeDesc("427");
            if (!usertypelist.isEmpty()) {
                for (int i = 0; i < usertypelist.size(); i++) {
                    Vector v = (Vector) usertypelist.get(i);
                    originByFilterList.add(new SelectItem(v.get(0).toString(), v.get(1).toString()));
                }
            }
            if (function.equalsIgnoreCase("V")) {
                this.setOriginBy("All");
                this.setDataSource("All");
                this.setMarketingSource("All");
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getAllTypeList() {
        this.setErrorMsg("");
        try {
            branchList = new ArrayList<>();
            branchList.add(new SelectItem("S", "--Select--"));
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
                    if ((v.get(0).toString().equalsIgnoreCase("N"))) {
                        statusList.add(new SelectItem(v.get(0).toString(), v.get(1).toString()));
                    }
                }
            }

            userAssignedList = new ArrayList<>();
            userAssignedList.add(new SelectItem("0", "--Select--"));

            List enquirylist = otherFacade.getRefcodeDesc("425");
            enquiryTypeList = new ArrayList<>();
            enquiryTypeList.add(new SelectItem("0", "--Select--"));
            if (!enquirylist.isEmpty()) {
                for (int i = 0; i < enquirylist.size(); i++) {
                    Vector v = (Vector) enquirylist.get(i);
                    enquiryTypeList.add(new SelectItem(v.get(0).toString(), v.get(1).toString()));
                }
            }
            setFollowDate(getTodayDate());


            List searchList = this.common.getRefRecList("429");
            searchByList = new ArrayList<SelectItem>();
            searchByList.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < searchList.size(); i++) {
                Vector ele = (Vector) searchList.get(i);
                searchByList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
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
            }
            purposeTypeDataList = new ArrayList();
            purposeTypeDataList.add(new SelectItem("0", "--Select--"));
            List purposetypeDataList = aitr.getListAsPerRequirement("426", getPurpose(), "0", "0", "0", "0", ymd.format(new Date()), 0);
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

    public void functionMode() {
        this.setErrorMsg("");
        this.setOriginBy("0");
        this.setOriginbyFilter("0");
        this.setDataSource("0");
        this.setMarketingSource("0");
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                setErrorMsg("Please select the function !!");
                return;
            }
            branchHoList = new ArrayList<>();
            branchHoList.add(new SelectItem("S", "--Select--"));
            if (function.equalsIgnoreCase("V") && getOrgnBrCode().equalsIgnoreCase("90")) {
                branchHoList.add(new SelectItem("0A", "All"));
            }
            List list = common.getAlphacodeBasedOnOrgnBranch(getOrgnBrCode());
            if (list.isEmpty()) {
                this.setErrorMsg("Please define branches.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector vec = (Vector) list.get(i);
                branchHoList.add(new SelectItem(vec.get(1).toString().length() < 2 ? "0" + vec.get(1).toString()
                        : vec.get(1).toString(), vec.get(0).toString()));
            }
            if (function.equalsIgnoreCase("A")) {
                this.setButtonValue("Save");
                mainPanelForAdd = "";
                viewpdfbutton = "none";
                commonDisplay = "none";
                panelDisplay1 = "none";
                panelDisplay2 = "none";
                viewBranch = "none";
                setFrmDt(getTodayDate());
                setToDt(getTodayDate());
                getCommonListforFunctionType();
            }
            if (function.equalsIgnoreCase("V")) {
                mainPanelForAdd = "none";
                this.setButtonValue("Print Report");
                this.commonDisplay = "";
                setFrmDt(getTodayDate());
                setToDt(getTodayDate());
                viewpdfbutton = "";
                panelDisplay2 = "none";
                panelDisplay1 = "none";
                panelByOrigin = "none";
                viewBranch = "";
                if (getOrgnBrCode().equalsIgnoreCase("90")) {
                    this.setBranchHo("0A");
                } else {
                    this.setBranchHo(getOrgnBrCode());
                }
                getCommonListforFunctionType();
            }

        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void originByMode() {
        setErrorMsg("");
        setOriginbyFilter("0");
        setDataSource("0");
        setMarketingSource("0");
        setBranch("S");
        setNewAccountNo("");
        setNewUserAccountNo("");
        setCustomerName("");
        setFatherName("");
        setEmailId("");
        setContactNo("");
        setAlternateContactNo("");
        setAddress("");
        setCity("");
        setRemarks("");
        setFrmDt(getTodayDate());
        setToDt(getTodayDate());
        this.panelDisplay1 = "none";
        this.panelByOrigin = "none";
        this.panelDisplay2 = "none";
        this.commonDisplay = "none";
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setErrorMsg("please select function");
                return;
            }
            if (originBy == null || originBy.equalsIgnoreCase("0")) {
                setErrorMsg("Please select origin by");
                return;
            }
            if (function.equalsIgnoreCase("A")) {
                if (this.originBy.equalsIgnoreCase("N")) {
                    this.panelDisplay1 = "";
                    this.panelByOrigin = "";
                    this.panelDisplay2 = "none";
                    this.commonDisplay = "none";
                    List userassigned = common.getUsernameandUserId(getOrgnBrCode());
                    userAssignedList = new ArrayList<>();
                    userAssignedList.add(new SelectItem("0", "--Select--"));
                    if (!userassigned.isEmpty()) {
                        for (int i = 0; i < userassigned.size(); i++) {
                            Vector v = (Vector) userassigned.get(i);
                            userAssignedList.add(new SelectItem(v.get(0).toString(), v.get(1).toString() + " [ " + v.get(0).toString() + " ]"));
                        }
                    }
                } else if (this.originBy.equalsIgnoreCase("U")) {
                    this.panelDisplay1 = "none";
                    this.panelByOrigin = "none";
                    this.panelDisplay2 = "";
                    this.commonDisplay = "";
                    this.setDataSource("0");
                    this.setMarketingSource("0");
                    userAssignedList = new ArrayList<>();
                    userAssignedList.add(new SelectItem("0", "--Select--"));
                }
            }
            if (function.equalsIgnoreCase("V")) {
                this.panelDisplay1 = "none";
                this.panelDisplay2 = "none";
                this.panelByOrigin = "none";
                this.commonDisplay = "";
                if (originBy.equalsIgnoreCase("All")) {
                    this.setDataSource("All");
                    this.setMarketingSource("All");
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void accountCheckMode() {
        setErrorMsg("");
        setNewAccountNo("");
        setCustomerName("");
        setContactNo("");
        setCity("");
        setAddress("");
        setFatherName("");
        try {
            if (originbyFilter == null || originbyFilter.equalsIgnoreCase("0")) {
                setErrorMsg("Please select account check");
                return;
            }
            if (originbyFilter.equalsIgnoreCase("E") || originbyFilter.equalsIgnoreCase("L")) {
                this.disableNewUserAcNo = false;
            } else if (originbyFilter.equalsIgnoreCase("N")) {
                this.disableNewUserAcNo = true;
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void searchByAction() {
        searchByOptionList = new ArrayList<SelectItem>();
        try {
            List serchByOptionList = aitr.getListAsPerRequirement("430", this.searchBy, "0", "0", "0", "0", ymd.format(new Date()), 0);

            searchByOptionList.add(new SelectItem("", "--Select--"));
            if (!serchByOptionList.isEmpty()) {
                for (int i = 0; i < serchByOptionList.size(); i++) {
                    Vector v = (Vector) serchByOptionList.get(i);
                    searchByOptionList.add(new SelectItem(v.get(1).toString(), v.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getLocalizedMessage());
        }
    }

    public void setAllBoxSelected() {
        this.setErrorMsg("");
        this.setPurpose("0");
        this.setPurposeTypeData("0");
        this.setEnquiryType("0");
        this.setUserAssigned("0");
        this.setFollowDate(getTodayDate());
        this.setRemarks("");
        try {
            tempCurrentItemList = new ArrayList<>();
            System.out.println("In All Selected CheckBox.");
            if (allSelected) {
                for (TaskManagementPojo obj : gridDetail) {
                    obj.setSelected(true);
                    tempCurrentItemList.add(obj);
                }
            } else {
                for (TaskManagementPojo obj : gridDetail) {
                    obj.setSelected(false);
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void onBlurGetGridtabel() {
        setErrorMsg("");
        try {
            if (function.equalsIgnoreCase("A")) {
                if (branch == null || branch.equals("S")) {
                    setErrorMsg("Please select branch !");
                    return;
                }
                if (remarks == null || remarks.equals("")) {
                    setErrorMsg("Please fill remarks !");
                    return;
                }
                if (this.searchBy == null || this.searchBy.equalsIgnoreCase("")) {
                    setErrorMsg("Please fill search by ");
                    return;
                }
                if (this.searchByOption == null || this.searchByOption.equalsIgnoreCase("")) {
                    setErrorMsg("Please fill search option");
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
                List dataList = taskManagement.getUtilityReportDetailsforTaskManagement(this.branch, this.remarks,
                        ymd.format(dmy.parse(this.frmDt)), ymd.format(dmy.parse(this.toDt)),
                        this.searchBy, this.searchByOption == null ? "" : searchByOption);
                if (dataList.isEmpty()) {
                    throw new ApplicationException("Data does not exists");
                }
                gridDetail = new ArrayList<>();
                int srno = 1;
                for (int i = 0; i < dataList.size(); i++) {
                    Vector v = (Vector) dataList.get(i);
                    TaskManagementPojo pojo = new TaskManagementPojo();
                    pojo.setSrNo(srno++);
                    pojo.setAccountNo(v.get(0) == null ? "" : v.get(0).toString().trim());
                    pojo.setCustomerName(v.get(1) == null ? "" : v.get(1).toString().trim());
                    pojo.setFatherName(v.get(7) == null ? "" : v.get(7).toString().trim() + " " + v.get(8) == null ? "" : v.get(8).toString().trim() + " " + v.get(9) == null ? "" : v.get(9).toString().trim());
                    pojo.setContactNo(v.get(2) == null ? "" : v.get(2).toString().trim());
                    pojo.setEmailId(v.get(3) == null ? "" : v.get(3).toString().trim());
                    pojo.setAddress(v.get(4).toString().trim() + " " + v.get(5) == null ? "" : v.get(5).toString().trim());
                    pojo.setCity(v.get(6) == null ? "" : v.get(6).toString().trim());
                    if (originBy.equalsIgnoreCase("N")) {
                        pojo.setAlternateContactNo(this.alternateContactNo);
                    } else {
                        pojo.setAlternateContactNo(" ");
                    }
                    gridDetail.add(pojo);
                }

            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void onBlurBranch() {
        this.setErrorMsg("");
        try {
            if (originBy.equalsIgnoreCase("U")) {
                if (this.branch == null || branch.equalsIgnoreCase("S")) {
                    this.setErrorMsg("Please select the branch");
                    return;
                }
                gridDetail = new ArrayList<>();
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
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void accountMode() {
        this.setErrorMsg("");
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                setErrorMsg("Please select the function");
                return;
            }
            if (originBy == null || originBy.equalsIgnoreCase("0")) {
                setErrorMsg("please select the origin by");
                return;
            }
            if (dataSource == null || dataSource.equalsIgnoreCase("0")) {
                setErrorMsg("Please select the source of data");
                return;
            }
            if (marketingSource == null || marketingSource.equalsIgnoreCase("0")) {
                setErrorMsg("Please select the source of marketing");
                return;
            }

            if (originbyFilter == null || this.originbyFilter.equalsIgnoreCase("")) {
                setErrorMsg("Please select user type !!");
                return;
            }
            if (this.newUserAccountNo == null || this.newUserAccountNo.equalsIgnoreCase("") || this.newUserAccountNo.length() == 0 || this.newUserAccountNo.length() != 12) {
                this.setErrorMsg("Please fill proper account no");
                return;
            }
            if (!newUserAccountNo.matches("[0-9]+")) {
                this.setErrorMsg("Please Enter Valid  Account No.");
                return;
            }
            if (originbyFilter.equalsIgnoreCase("E") || originbyFilter.equalsIgnoreCase("L")) {
                List list = taskManagement.getCustomerDetailsbyAccountno(this.newUserAccountNo);
                if (list.isEmpty()) {
                    setErrorMsg("Account number does not exists");
                    return;
                }
                Vector v = (Vector) list.get(0);
                setNewUserAccountNo(v.get(3) == null ? "" : v.get(3).toString().trim());
                setCustomerName(v.get(1) == null ? "" : v.get(1).toString().trim());
                setContactNo(v.get(2) == null ? "" : v.get(2).toString().trim());
                setFatherName(v.get(4) == null ? "" : v.get(4).toString().trim());
                setEmailId(v.get(5) == null ? "" : v.get(5).toString().trim());
                setAddress(v.get(6) == null ? "" : v.get(6).toString().trim() + "," + v.get(7) == null ? "" : v.get(7).toString().trim());
                setCity(v.get(8) == null ? "" : v.get(8).toString());
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void setDataInList() {
        try {
            tempCurrentItemList = new ArrayList<>();
            TaskManagementPojo pojo = new TaskManagementPojo();
            if (originbyFilter.equalsIgnoreCase("N")) {
                pojo.setAccountNo("");
            } else {
                pojo.setAccountNo(this.newUserAccountNo);
            }
            pojo.setCustomerName(this.customerName);
            pojo.setFatherName(this.fatherName);
            pojo.setContactNo(this.contactNo);
            pojo.setAlternateContactNo(this.alternateContactNo);
            pojo.setEmailId(this.emailId);
            pojo.setAddress(this.address);
            pojo.setCity(this.city);
            tempCurrentItemList.add(pojo);
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void onClickcheckBox() {
        setErrorMsg("");
        try {
            tempCurrentItemList = new ArrayList<>();
            for (TaskManagementPojo obj : gridDetail) {
                if (obj.isSelected() == true) {
                    tempCurrentItemList.add(obj);
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void buttonClick() {
        String result = "";
        this.setErrorMsg("");
        try {
            if (function == null || function.equalsIgnoreCase("") || function.equalsIgnoreCase("0")) {
                setErrorMsg("Please select Function");
                return;
            }
            if (dataSource == null || dataSource.equalsIgnoreCase("") || dataSource.equalsIgnoreCase("0")) {
                setErrorMsg("Please select source of data");
                return;
            }
            if (marketingSource == null || marketingSource.equalsIgnoreCase("") || marketingSource.equalsIgnoreCase("0")) {
                setErrorMsg("Please select source of marketing");
                return;
            }

            if (originBy == null || originBy.equalsIgnoreCase("0")) {
                setErrorMsg("Please select origin by");
                return;
            }
            if (originBy.equalsIgnoreCase("U")) {
                if (branch == null || branch.equals("S")) {
                    setErrorMsg("Please select branch !");
                    return;
                }
                if (remarks == null || remarks.equals(" ")) {
                    setErrorMsg("Please fill remarks !");
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
            }
            if (originBy.equalsIgnoreCase("N")) {
                if (originbyFilter.equalsIgnoreCase("E") || originbyFilter.equalsIgnoreCase("L")) {
                    if (newUserAccountNo == null || newUserAccountNo.equalsIgnoreCase("") || newUserAccountNo.length() == 0 || newUserAccountNo.length() != 12) {
                        setErrorMsg("please fill proper account Number");
                        return;
                    }
                    if (this.customerName == null || this.customerName.equalsIgnoreCase("")) {
                        this.setErrorMsg("Please fill the customer name");
                        return;
                    }
                }
                if (originbyFilter.equalsIgnoreCase("N")) {
                    if (this.customerName == null || this.customerName.equalsIgnoreCase("")) {
                        this.setErrorMsg("Please fill the customer name");
                        return;
                    }
                    if (this.contactNo == null || this.contactNo.equalsIgnoreCase("")) {
                        this.setErrorMsg("Please fill the contact number.");
                        return;
                    }
                    Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
                    Matcher mobileNo = p.matcher(contactNo);
                    if (!mobileNo.matches()) {
                        this.setErrorMsg("Please fill a valid  conatct number.");
                        return;
                    }
                    if (originBy.equalsIgnoreCase("N") && originbyFilter.equalsIgnoreCase("N")) {
                        List list = taskManagement.getCbsLeadMagmtDetails(contactNo);
                        if (!list.isEmpty()) {
                            this.setErrorMsg("This contact no is already registered");
                            return;
                        }
                    }
                }
            }
            if (this.purpose == null || this.purpose.equalsIgnoreCase("0")) {
                setErrorMsg("Please select purpose");
                return;
            }
            if (this.purposeTypeData == null || this.purposeTypeData.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please  select the purpose Desc");
                return;
            }
            if (this.status == null || status.equalsIgnoreCase("0")) {
                setErrorMsg("Please select status");
                return;
            }
            if (this.userAssigned == null || this.userAssigned.equalsIgnoreCase("0")) {
                setErrorMsg("Please select user to be assigned");
                return;
            }
            if (enquiryType == null || enquiryType.equalsIgnoreCase("0")) {
                setErrorMsg("Please select enquiry type");
                return;
            }

            if (followDate == null || followDate.equalsIgnoreCase("")) {
                setErrorMsg("Please enter the follow date");
                return;
            }

            if (dmy.parse(followDate).before(dmy.parse(getTodayDate()))) {
                setErrorMsg("Follow date can not be smaller than today date!");
                return;
            }

            if (customerRemark == null || customerRemark.equalsIgnoreCase("")) {
                setErrorMsg("please fill customer remark");
            }
            if (originBy.equalsIgnoreCase("U")) {
                result = taskManagement.insertDataTaskManagement(tempCurrentItemList, this.dataSource, this.marketingSource,
                        this.originBy, getOrgnBrCode(), this.remarks, this.frmDt, this.toDt, this.purpose, this.purposeTypeData,
                        this.status, this.userAssigned, this.enquiryType, this.followDate, customerRemark, getUserName(), this.branch, this.originbyFilter);
                if (!result.equalsIgnoreCase("true")) {
                    setErrorMsg(result);
                    return;
                }
                RefreshUtility();
                this.setErrorMsg("Data has been inserted successfully");
            }
            // new user
            if (originBy.equalsIgnoreCase("N")) {
                setDataInList();
                taskManagement.insertDataTaskManagement(tempCurrentItemList, this.dataSource, this.marketingSource, this.originBy,
                        getOrgnBrCode(), this.remarks, this.frmDt, this.toDt, this.purpose, this.purposeTypeData, this.status,
                        this.userAssigned, this.enquiryType, this.followDate, customerRemark, getUserName(), getOrgnBrCode(), this.originbyFilter);
                if (!result.equalsIgnoreCase("true")) {
                    setErrorMsg(result);
                }
                saveRefreshNewUser();
                this.setErrorMsg("Data has been inserted successfully");
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

    public void printReport() {
        this.setErrorMsg("");
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the function");
                return;
            }
            if (originBy == null || originBy.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the origin By");
                return;
            }
            if (branchHo == null || branchHo.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select the branch");
                return;
            }
            if (dataSource == null || dataSource.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the source of data");
                return;
            }
            if (marketingSource == null || marketingSource.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the source of marketing");
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
            String report = "Lead Assignment Report";
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
            reportList = taskManagement.getTaskManagementReport(this.dataSource, this.marketingSource, ymd.format(dmy.parse(this.frmDt)), ymd.format(dmy.parse(this.toDt)), getOrgnBrCode(), this.branchHo);
            if (reportList.isEmpty()) {
                this.setErrorMsg("Data does not exists");
                return;
            }
            new ReportBean().jasperReport("Task_Management_Report", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "Lead Assignment Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void pdfDownload() {
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the function");
                return;
            }
            if (originBy == null || originBy.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the origin By");
                return;
            }
            if (branchHo == null || branchHo.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select the branch");
                return;
            }
            if (dataSource == null || dataSource.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the source of data");
                return;
            }
            if (marketingSource == null || marketingSource.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the source of marketing");
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

            String report = "Lead Assignment Report";
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
            reportList = taskManagement.getTaskManagementReport(this.dataSource, this.marketingSource, ymd.format(dmy.parse(this.frmDt)), ymd.format(dmy.parse(this.toDt)), getOrgnBrCode(), this.branchHo);
            if (reportList.isEmpty()) {
                this.setErrorMsg("Data does not exists");
                return;
            }
            new ReportBean().openPdf("Task_Management_Report" + ymd.format(dmy.parse(this.frmDt)) + " to " + ymd.format(dmy.parse(this.toDt)), "Task_Management_Report", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void downloadExcel() {
        try {
            if (function == null || function.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the function");
                return;
            }
            if (originBy == null || originBy.equalsIgnoreCase("")) {
                this.setErrorMsg("Please select the origin By");
                return;
            }
            if (branchHo == null || branchHo.equalsIgnoreCase("S")) {
                this.setErrorMsg("Please select the branch");
                return;
            }
            if (dataSource == null || dataSource.equalsIgnoreCase("")) {
                this.setErrorMsg("Please select the source of data");
                return;
            }
            if (marketingSource == null || marketingSource.equalsIgnoreCase("")) {
                this.setErrorMsg("Please select the source of marketing");
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

            String report = "Lead Assignment Report";
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
            reportList = taskManagement.getTaskManagementReport(this.dataSource, this.marketingSource, ymd.format(dmy.parse(this.frmDt)), ymd.format(dmy.parse(this.toDt)), getOrgnBrCode(), this.branchHo);

            if (reportList.isEmpty()) {
                this.setErrorMsg("Data does not exists");
                return;
            }
            new ReportBean().dynamicExcelJasper("Task_Management_Report", "excel", new JRBeanCollectionDataSource(reportList), fillParams, "Lead Assignment Report");

        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void onbuttonClicked() {
        if (function.equalsIgnoreCase("A")) {
            this.setMsgvalue("Are you sure to add the transcation");
        }
    }

    public void validateCustomername() {
        if (this.customerName == null || this.customerName.equalsIgnoreCase("") || this.customerName.length() == 0) {
            this.setErrorMsg("Please fill the customer name");
            return;
        }

    }

    public void validateContactNo() {
        try {
            this.setErrorMsg("");
            if (this.contactNo == null || this.contactNo.equalsIgnoreCase("")) {
                this.setErrorMsg("Please fill the contact number.");
                return;
            }
            Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
            Matcher mobileNo = p.matcher(contactNo);
            if (!mobileNo.matches()) {
                this.setErrorMsg("Please fill a valid  conatct number.");
                return;
            }
            if (originBy.equalsIgnoreCase("N") && originbyFilter.equalsIgnoreCase("N")) {
                List list = taskManagement.getCbsLeadMagmtDetails(contactNo);
                if (!list.isEmpty()) {
                    this.setErrorMsg("This contact no is already registered");
                    return;
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void refresh() {
        try {
            setErrorMsg("");
            setFunction("0");
            setDataSource("0");
            setMarketingSource("0");
            setOriginBy("0");
            setOriginbyFilter("0");
            setNewAccountNo("");
            setNewUserAccountNo("");
            setCustomerName("");
            setFatherName("");
            setContactNo("");
            setEmailId("");
            setAlternateContactNo("");
            setAddress("");
            setCity("");
            setBranch("S");
            setRemarks("");
            setSearchBy("");
            setSearchByOption("");
            setFrmDt(getTodayDate());
            setToDt(getTodayDate());
            setPurpose("0");
            setPurposeTypeData("0");
            setUserAssigned("0");
            setStatus("0");
            setEnquiryType("0");
            setPanelDisplay1("none");
            setPanelDisplay2("none");
            setCommonDisplay("none");
            setMainPanelForAdd("none");
            setButtonValue("Save");
            setViewpdfbutton("none");
            setCustomerRemark("");
            setBranchHo("");
            this.viewBranch = "none";
            setPanelByOrigin("none");
            setFollowDate(getTodayDate());
            setAllSelected(false);
            gridDetail = new ArrayList<>();
            allSelected = false;
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void saveRefreshNewUser() {
        try {
            setErrorMsg("");
            setOriginBy("0");
            setOriginbyFilter("0");
            setDataSource("0");
            setMarketingSource("0");
            setNewUserAccountNo("");
            setCustomerName("");
            setFatherName("");
            setContactNo("");
            setEmailId("");
            setAlternateContactNo("");
            setAddress("");
            setCity("");
            setPurpose("0");
            setPurposeTypeData("0");
            setEnquiryType("0");
            setStatus("0");
            setUserAssigned("0");
            setFollowDate(getTodayDate());
            setCustomerRemark("");
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void RefreshUtility() {
        try {
            setErrorMsg("");
            setPurpose("0");
            setPurposeTypeData("0");
            setUserAssigned("0");
            setStatus("0");
            setEnquiryType("0");
            setCustomerRemark("");
            setFollowDate(getTodayDate());
            if (allSelected) {
                refreshField();
            }
            for (TaskManagementPojo pojo : tempCurrentItemList) {
                gridDetail.remove(pojo);
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void refreshField() {
        try {
            setDataSource("0");
            setMarketingSource("0");
            setOriginBy("0");
            setOriginbyFilter("0");
            setBranch("S");

            setNewAccountNo("");
            setRemarks("");
            setFrmDt(getTodayDate());
            setToDt(getTodayDate());
            allSelected = false;
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

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getNewAccountNo() {
        return newAccountNo;
    }

    public void setNewAccountNo(String newAccountNo) {
        this.newAccountNo = newAccountNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPanelDisplay1() {
        return panelDisplay1;
    }

    public void setPanelDisplay1(String panelDisplay1) {
        this.panelDisplay1 = panelDisplay1;
    }

    public String getPanelDisplay2() {
        return panelDisplay2;
    }

    public void setPanelDisplay2(String panelDisplay2) {
        this.panelDisplay2 = panelDisplay2;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getAlternateContactNo() {
        return alternateContactNo;
    }

    public void setAlternateContactNo(String alternateContactNo) {
        this.alternateContactNo = alternateContactNo;
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

    public String getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(String userAssigned) {
        this.userAssigned = userAssigned;
    }

    public List<SelectItem> getDataSourceList() {
        return dataSourceList;
    }

    public void setDataSourceList(List<SelectItem> dataSourceList) {
        this.dataSourceList = dataSourceList;
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

    public List<SelectItem> getUserAssignedList() {
        return userAssignedList;
    }

    public void setUserAssignedList(List<SelectItem> userAssignedList) {
        this.userAssignedList = userAssignedList;
    }

    public String getFollowDate() {
        return followDate;
    }

    public void setFollowDate(String followDate) {
        this.followDate = followDate;
    }

    public String getEnquiryType() {
        return enquiryType;
    }

    public void setEnquiryType(String enquiryType) {
        this.enquiryType = enquiryType;
    }

    public String getCustomerRemark() {
        return customerRemark;
    }

    public void setCustomerRemark(String customerRemark) {
        this.customerRemark = customerRemark;
    }

    public List<SelectItem> getEnquiryTypeList() {
        return enquiryTypeList;
    }

    public void setEnquiryTypeList(List<SelectItem> enquiryTypeList) {
        this.enquiryTypeList = enquiryTypeList;
    }

    public String getNewUserAccountNo() {
        return newUserAccountNo;
    }

    public void setNewUserAccountNo(String newUserAccountNo) {
        this.newUserAccountNo = newUserAccountNo;
    }

    public String getOriginBy() {
        return originBy;
    }

    public void setOriginBy(String originBy) {
        this.originBy = originBy;
    }

    public List<SelectItem> getOriginByList() {
        return originByList;
    }

    public void setOriginByList(List<SelectItem> originByList) {
        this.originByList = originByList;
    }

    public String getMarketingSource() {
        return marketingSource;
    }

    public void setMarketingSource(String marketingSource) {
        this.marketingSource = marketingSource;
    }

    public List<SelectItem> getMarketingSourceList() {
        return marketingSourceList;
    }

    public void setMarketingSourceList(List<SelectItem> marketingSourceList) {
        this.marketingSourceList = marketingSourceList;
    }

    public String getOriginbyFilter() {
        return originbyFilter;
    }

    public void setOriginbyFilter(String originbyFilter) {
        this.originbyFilter = originbyFilter;
    }

    public List<SelectItem> getOriginByFilterList() {
        return originByFilterList;
    }

    public void setOriginByFilterList(List<SelectItem> originbyFilterList) {
        this.originByFilterList = originbyFilterList;
    }

    public List<TaskManagementPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<TaskManagementPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getFollowTime() {
        return followTime;
    }

    public void setFollowTime(String followTime) {
        this.followTime = followTime;
    }

    public String getMsgvalue() {
        return msgvalue;
    }

    public void setMsgvalue(String msgvalue) {
        this.msgvalue = msgvalue;
    }

    public List<TaskManagementPojo> getTempCurrentItemList() {
        return tempCurrentItemList;
    }

    public void setTempCurrentItemList(List<TaskManagementPojo> tempCurrentItemList) {
        this.tempCurrentItemList = tempCurrentItemList;
    }

    public TaskManagementPojo getTempCurrentItem() {
        return tempCurrentItem;
    }

    public void setTempCurrentItem(TaskManagementPojo tempCurrentItem) {
        this.tempCurrentItem = tempCurrentItem;
    }

    public boolean isAllSelected() {
        return allSelected;
    }

    public void setAllSelected(boolean allSelected) {
        this.allSelected = allSelected;
    }

    public String getOnSelectFunctionPanel() {
        return onSelectFunctionPanel;
    }

    public void setOnSelectFunctionPanel(String onSelectFunctionPanel) {
        this.onSelectFunctionPanel = onSelectFunctionPanel;
    }

    public String getCommonDisplay() {
        return commonDisplay;
    }

    public void setCommonDisplay(String commonDisplay) {
        this.commonDisplay = commonDisplay;
    }

    public String getMainPanelForAdd() {
        return mainPanelForAdd;
    }

    public void setMainPanelForAdd(String mainPanelForAdd) {
        this.mainPanelForAdd = mainPanelForAdd;
    }

    public String getButtonValue() {
        return buttonValue;
    }

    public void setButtonValue(String buttonValue) {
        this.buttonValue = buttonValue;
    }

    public String getViewpdfbutton() {
        return viewpdfbutton;
    }

    public void setViewpdfbutton(String viewpdfbutton) {
        this.viewpdfbutton = viewpdfbutton;
    }

    public String getPanelByOrigin() {
        return panelByOrigin;
    }

    public void setPanelByOrigin(String panelByOrigin) {
        this.panelByOrigin = panelByOrigin;
    }

    public boolean isDisableNewUserAcNo() {
        return disableNewUserAcNo;
    }

    public void setDisableNewUserAcNo(boolean disableNewUserAcNo) {
        this.disableNewUserAcNo = disableNewUserAcNo;
    }

    public String getBranchHo() {
        return branchHo;
    }

    public void setBranchHo(String branchHo) {
        this.branchHo = branchHo;
    }

    public String getViewBranch() {
        return viewBranch;
    }

    public void setViewBranch(String viewBranch) {
        this.viewBranch = viewBranch;
    }

    public List<SelectItem> getBranchHoList() {
        return branchHoList;
    }

    public void setBranchHoList(List<SelectItem> branchHoList) {
        this.branchHoList = branchHoList;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getSearchByOption() {
        return searchByOption;
    }

    public void setSearchByOption(String searchByOption) {
        this.searchByOption = searchByOption;
    }

    public List<SelectItem> getSearchByList() {
        return searchByList;
    }

    public void setSearchByList(List<SelectItem> searchByList) {
        this.searchByList = searchByList;
    }

    public List<SelectItem> getSearchByOptionList() {
        return searchByOptionList;
    }

    public void setSearchByOptionList(List<SelectItem> searchByOptionList) {
        this.searchByOptionList = searchByOptionList;
    }
}
