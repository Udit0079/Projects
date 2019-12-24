/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.payroll;

import com.hrms.web.pojo.LeaveTableGrid;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.to.HrLeavePostingTO;
import com.hrms.common.to.HrLeavePostingTOPK;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.LeaveMasterTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.delegate.MasterManagementDelegate;
import javax.faces.model.SelectItem;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Dhirendra Singh
 */
public class LeaveAllocation extends BaseBean {

    private static final Logger logger = Logger.getLogger(LeaveAllocation.class);
    private String allocationcat;
    private String allocationcatdetail;
    private String searchflag;
    private String searchcriteria;
    private boolean boolleavecode;
    private String errmsg;
    private int compCode;
    private String leaveCode;
    private Date postingDate;
    private String dateFrom;
    private String dateTo;
    private boolean updateDelete;
    private String function;
    private String focusId;
    private LeaveTableGrid selectdata;
    private LeaveTableGrid selectdata1;
    private List<SelectItem> functionList;
    private List<SelectItem> category;
    private List<SelectItem> categorydetails;
    private List<SelectItem> leavecode;
    private List<LeaveTableGrid> result;
    private List<LeaveTableGrid> gridresult;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private PayrollTransactionsFacadeRemote payrollRemote;

    /**
     * Creates a new instance of LeaveAllocation
     */
    public LeaveAllocation() {
        try {
            payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");
            compCode = Integer.parseInt(getOrgnBrCode());

            updateDelete = true;
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--SELECT--"));
            functionList.add(new SelectItem("1", "ADD"));
            functionList.add(new SelectItem("2", "VIEW"));
            initData();
            //gridData();

        } catch (Exception e) {
            logger.error("Exception occured while executing method leaveAllocation()", e);
            logger.error("leaveAllocation()", e);
        }
    }

    public void initData() {
        try {
//            MasterManagementDelegate masterManagementDelegate = new MasterManagementDelegate();
//            List<PayrollCalendarTO> payrollCalendarTOs = masterManagementDelegate.getFinancialYear(compCode);
//            if (!payrollCalendarTOs.isEmpty()) {
//                for (PayrollCalendarTO payrollCalendarTO : payrollCalendarTOs) {
//                    this.setDateFrom(sdf.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateFrom()));
//                    this.setDateTo(sdf.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateTo()));
//                }
//            }
            
            List list = payrollRemote.getFinYears(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Object[] ele = (Object[]) list.get(0);                
                this.setDateFrom(ele[0].toString().trim());
                this.setDateTo(ele[1].toString().trim());
            }
            
            PayrollMasterManagementDelegate obj = new PayrollMasterManagementDelegate();
            leavecode = new ArrayList<SelectItem>();
            leavecode.add(new SelectItem("0", "--SELECT--"));
            List<LeaveMasterTO> leaveMasterTOs = obj.getLeaveMasterData(compCode);
            for (LeaveMasterTO leaveMasterTO : leaveMasterTOs) {
                leavecode.add(new SelectItem(leaveMasterTO.getLeaveMasterPKTO().getLeaveCode(), leaveMasterTO.getDescription()));
            }

            category = new ArrayList<SelectItem>();
            category.add(new SelectItem("0", "--SELECT--"));
            List<HrMstStructTO> hrMstStructTOs = obj.getinitData(compCode, "CHO%");
            category.add(new SelectItem("EWE", "EMPLOYEE WISE"));
            for (HrMstStructTO hrMstStructTO : hrMstStructTOs) {
                category.add(new SelectItem(hrMstStructTO.getDescription().substring(0, 3), hrMstStructTO.getDescription()));
            }
            categorydetails = new ArrayList<SelectItem>();
            categorydetails.add(new SelectItem("0", "--SELECT--"));
        } catch (Exception e) {
            setErrmsg(e.getMessage());
        }
    }

    public void onChangeFunction() {
        setErrmsg("");
        if (function.equals("0")) {
            setErrmsg("Please select an function");
            return;
        } else if (function.equals("1")) {
            setFocusId("natureList");
        } else if (function.equals("2")) {
            setFocusId("calIntDate1");
            gridData();
        }
    }

    public void gridData() {
        try {
            result = new ArrayList<LeaveTableGrid>();
            PayrollMasterManagementDelegate obj = new PayrollMasterManagementDelegate();
            List gridlist = obj.gridviewdata(compCode, getDateFrom(), getDateTo());
            Iterator itr1 = gridlist.iterator();
            while (itr1.hasNext()) {
                Object[] object = (Object[]) itr1.next();
                LeaveTableGrid grid = new LeaveTableGrid();
                grid.setEmpid(object[0].toString());
                grid.setEmpname(object[1].toString());

                grid.setFromdate(object[4].toString());
                grid.setTodate(object[5].toString());
                grid.setLeavecode(object[2].toString());
                grid.setPostingdate(object[3].toString());
                result.add(grid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCategoryDetail() {
        categorydetails = new ArrayList<SelectItem>();
        categorydetails.add(new SelectItem("0", "--SELECT--"));
        if (!getAllocationcat().equalsIgnoreCase("0")) {
            if (this.getAllocationcat().equals("EWE")) {
                gridresult = new ArrayList<LeaveTableGrid>();
                setFocusId("searchcriteria");
            } else {
                try {
                    PayrollMasterManagementDelegate obj = new PayrollMasterManagementDelegate();
                    List<HrMstStructTO> l = obj.getinitData(compCode, this.getAllocationcat().substring(0, 3) + "%");
                    for (HrMstStructTO h : l) {
                        categorydetails.add(new SelectItem(h.getHrMstStructPKTO().getStructCode(), h.getDescription()));
                    }
                    setFocusId("categorydetail");
                } catch (Exception e) {
                    setErrmsg(e.getMessage());
                }
            }
        }

    }

    public void categorizationDetails() {
        try {
            gridresult = new ArrayList<LeaveTableGrid>();
            PayrollMasterManagementDelegate obj = new PayrollMasterManagementDelegate();
            List<HrPersonnelDetailsTO> listpersonal = new ArrayList<HrPersonnelDetailsTO>();
            if (this.getSearchflag().equals("Emp Id")) {
                listpersonal = obj.findDataPersonalDetailDelegat(compCode, "%" + this.getSearchcriteria(), 0);
            } else {
                listpersonal = obj.findDataPersonalDetailDelegat(compCode, this.getSearchcriteria() + "%", 1);
            }
            for (HrPersonnelDetailsTO hr : listpersonal) {
                LeaveTableGrid grid = new LeaveTableGrid();
                grid.setEmpidD(hr.getEmpId() != null ? hr.getEmpId().toString() : "");
                grid.setEmpnameD(hr.getEmpName() != null ? hr.getEmpName().toString() : "");
                grid.setEmpaddD(hr.getEmpContAdd() != null ? hr.getEmpContAdd().toString() : "");
                grid.setEmpphD(hr.getEmpContTel() != null ? hr.getEmpContTel().toString() : "");
                gridresult.add(grid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectvalueforupdate() {
        try {
            categorydetails.add(new SelectItem(selectdata.getEmpidD(), selectdata.getEmpnameD()));
            this.setAllocationcatdetail(selectdata.getEmpidD());
            setFocusId("categorydetail");
        } catch (Exception e) {
            setErrmsg(e.getMessage());
        }
    }

    public void selectdataOperation() {
        boolleavecode = true;
        updateDelete = false;
        try {
            this.setAllocationcat("EWE");
            this.setLeaveCode(selectdata1.getLeavecode());
            this.setPostingDate(sdf.parse(selectdata1.getPostingdate()));
            categorydetails.add(new SelectItem(selectdata1.getEmpid(), selectdata1.getEmpname()));
            this.setAllocationcatdetail(selectdata1.getEmpid());
            setFocusId("calIntDate1");
        } catch (Exception e) {
            setErrmsg(e.getMessage());
        }
    }

    public void saveData() {
        errmsg = valid();
        if (errmsg.equalsIgnoreCase("ok")) {
            try {
                HrLeavePostingTO hrLeavePostingTO = getHrLeavePostingTO();
                PayrollMasterManagementDelegate obj = new PayrollMasterManagementDelegate();
                errmsg = obj.performOperationLeaveAllocation(compCode, this.getAllocationcat(), this.getAllocationcatdetail(), getFunction(), hrLeavePostingTO);
                setErrmsg(errmsg);
            } catch (Exception e) {
                setErrmsg(e.getMessage());
            }
            refreshData();
        } else {
            return;
        }

    }

    public void deleteData() {
        if (valid().equalsIgnoreCase("ok")) {
            try {
                HrLeavePostingTO hrLeavePostingTO = getHrLeavePostingTO();
                PayrollMasterManagementDelegate obj = new PayrollMasterManagementDelegate();
                errmsg = obj.performOperationLeaveAllocation(compCode, this.getAllocationcat(), this.getAllocationcatdetail(), "3", hrLeavePostingTO);
                setErrmsg(errmsg);
            } catch (Exception e) {
                setErrmsg(e.getMessage());
            }
            refreshData();
        }
    }

    private HrLeavePostingTO getHrLeavePostingTO() {
        HrLeavePostingTO hrLeavePostingTO = new HrLeavePostingTO();
        HrLeavePostingTOPK hrLeavePostingTOPK = new HrLeavePostingTOPK();

        hrLeavePostingTOPK.setCompCode(compCode);
        hrLeavePostingTOPK.setLeaveCode(this.getLeaveCode());
        hrLeavePostingTOPK.setDateFrom(this.getDateFrom());
        hrLeavePostingTOPK.setDateTo(this.getDateTo());

        hrLeavePostingTO.setModDate(new Date());
        hrLeavePostingTO.setAuthBy(getUserName());
        hrLeavePostingTO.setEnteredBy(getUserName());
        hrLeavePostingTO.setDefaultComp(compCode);

        hrLeavePostingTO.setPostingDate(sdf.format(this.getPostingDate()));
        hrLeavePostingTO.setStatFlag("Y");
        hrLeavePostingTO.setStatUpFlag("Y");
        hrLeavePostingTO.setHrLeavePostingPK(hrLeavePostingTOPK);
        return hrLeavePostingTO;
    }

    public void refreshData() {
        this.allocationcatdetail = "0";
        categorydetails = new ArrayList<SelectItem>();
        categorydetails.add(new SelectItem("0", "--SELECT--"));
        this.setAllocationcat("0");
        this.setLeaveCode("0");
        this.setPostingDate(null);
        setFunction("0");
    }

    public String btn_exit() {
        return "case1";
    }

    public void refreshDatabtn() {
        boolleavecode = false;
        this.allocationcatdetail = "0";
        categorydetails = new ArrayList<SelectItem>();
        categorydetails.add(new SelectItem("0", "--SELECT--"));
        this.setAllocationcat("0");
        this.setLeaveCode("0");
        this.setPostingDate(null);
        setFunction("0");
        setErrmsg("");
    }

    public String valid() {
        if (allocationcat.equalsIgnoreCase("0")) {
            return "Please Select Allocation Category";
        }

        if (allocationcatdetail.equalsIgnoreCase("0")) {
            return "Plese select Categorization Detail";
        }

        if (postingDate == null) {
            return "Please Enter Allocation Date";
        }

        if (leaveCode.equalsIgnoreCase("0")) {
            return "Please select Leave Code";
        }
        return "ok";
    }

    public boolean isUpdateDelete() {
        return updateDelete;
    }

    public void setUpdateDelete(boolean updateDelete) {
        this.updateDelete = updateDelete;
    }

    public boolean isBoolleavecode() {
        return boolleavecode;
    }

    public void setBoolleavecode(boolean boolleavecode) {
        this.boolleavecode = boolleavecode;
    }

    public LeaveTableGrid getSelectdata1() {
        return selectdata1;
    }

    public void setSelectdata1(LeaveTableGrid selectdata1) {
        this.selectdata1 = selectdata1;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<SelectItem> getLeavecode() {
        return leavecode;
    }

    public void setLeavecode(List<SelectItem> leavecode) {
        this.leavecode = leavecode;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public LeaveTableGrid getSelectdata() {
        return selectdata;
    }

    public void setSelectdata(LeaveTableGrid selectdata) {
        this.selectdata = selectdata;
    }

    public String getSearchcriteria() {
        return searchcriteria;
    }

    public void setSearchcriteria(String searchcriteria) {
        this.searchcriteria = searchcriteria;
    }

    public String getSearchflag() {
        return searchflag;
    }

    public void setSearchflag(String searchflag) {
        this.searchflag = searchflag;
    }

    public String getAllocationcatdetail() {
        return allocationcatdetail;
    }

    public void setAllocationcatdetail(String allocationcatdetail) {
        this.allocationcatdetail = allocationcatdetail;
    }

    public List<SelectItem> getCategorydetails() {
        return categorydetails;
    }

    public void setCategorydetails(List<SelectItem> categorydetails) {
        this.categorydetails = categorydetails;
    }

    public String getAllocationcat() {
        return allocationcat;
    }

    public void setAllocationcat(String allocationcat) {
        this.allocationcat = allocationcat;
    }

    public List<LeaveTableGrid> getGridresult() {
        return gridresult;
    }

    public void setGridresult(List<LeaveTableGrid> gridresult) {
        this.gridresult = gridresult;
    }

    public List<LeaveTableGrid> getResult() {
        return result;
    }

    public void setResult(List<LeaveTableGrid> result) {
        this.result = result;
    }

    public List<SelectItem> getCategory() {
        return category;
    }

    public void setCategory(List<SelectItem> category) {
        this.category = category;
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

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }
}
