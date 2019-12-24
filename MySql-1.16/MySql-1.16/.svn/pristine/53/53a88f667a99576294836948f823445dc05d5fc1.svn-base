/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.personnel;

import com.hrms.web.pojo.EmpSearchTable;
import com.hrms.web.pojo.PromotionTable;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.to.HrPersonnelDetailsPKTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrPromotionDetailsPKTO;
import com.hrms.common.to.HrPromotionDetailsTO;
import com.hrms.web.delegate.PersonnelMasterManagementDelegate;
import java.util.Iterator;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.utils.LocalizationUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.web.utils.WebUtil;
import java.text.DateFormat;

/**
 *
 * @author admin
 */
public class Promotion extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(Promotion.class);
    private String empCode;
    private String empName;
    private String empId;
    private String department;
    private String designation;
    private String zonefrom;
    private String block;
    private String reportinto;
    private String oldlocation;
    private Date lastpromotion;
    private Date approvaldt;
    private Date effectivedt;
    private String arno;
    private String location;
    private String proposeddept;
    private String newblock;
    private String prosupervisor;
    private String prozone;
    private String prodeignation;
    private String overallrating;
    private String remark;
    private String budgetstatus;
    private String approvhrd;
    private String approvhead;
    private String approvmd;
    private String searchtext;
    private String searchflag;
    private String deptCode;
    private String blockCode;
    private String desgCode;
    private String gradeCode;
    private String locatCode;
    private String zoneCode;
    private boolean add;
    private boolean save;
    private boolean view;
    private boolean update;
    private boolean delete;
    private String prosupervisorId;
    private String compCode="0";
    private String errMsg;
    private String flag;
    private EmpSearchTable currentempdata;
    private List<EmpSearchTable> datagridemp;
    private List<PromotionTable> datagrid;
    private PromotionTable currentdata;
    private List<SelectItem> locationlist = new ArrayList<SelectItem>();
    private List<SelectItem> deptlist = new ArrayList<SelectItem>();
    private List<SelectItem> blocklist = new ArrayList<SelectItem>();
    private List<SelectItem> zonelist = new ArrayList<SelectItem>();
    private List<SelectItem> deslist = new ArrayList<SelectItem>();
    private List<SelectItem> ratlist = new ArrayList<SelectItem>();
    private List<SelectItem> budgetlist = new ArrayList<SelectItem>();
    private List<SelectItem> approve = new ArrayList<SelectItem>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
   
     SimpleDateFormat ymd = new SimpleDateFormat("MMM DDD hh:mm:ss yyyy");
     
    private int defaultComp;
     private String operation;
    private List<SelectItem> operationList;

    /** Creates a new instance of Promotion */
    public Promotion() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            initData();
            viewData();
            update = true;
            save = true;
            delete = true;
        } catch (Exception e) {
            logger.error("Exception occured while executing method Consultant()", e);
            logger.error("Consultant()", e);
        }
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getProsupervisorId() {
        return prosupervisorId;
    }

    public void setProsupervisorId(String prosupervisorId) {
        this.prosupervisorId = prosupervisorId;
    }

    public PromotionTable getCurrentdata() {
        return currentdata;
    }

    public void setCurrentdata(PromotionTable currentdata) {
        this.currentdata = currentdata;
    }

    public List<PromotionTable> getDatagrid() {
        return datagrid;
    }

    public void setDatagrid(List<PromotionTable> datagrid) {
        this.datagrid = datagrid;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getLocatCode() {
        return locatCode;
    }

    public void setLocatCode(String locatCode) {
        this.locatCode = locatCode;
    }

    public List<SelectItem> getApprove() {
        return approve;
    }

    public void setApprove(List<SelectItem> approve) {
        this.approve = approve;
    }

    public List<SelectItem> getBudgetlist() {
        return budgetlist;
    }

    public void setBudgetlist(List<SelectItem> budgetlist) {
        this.budgetlist = budgetlist;
    }

    public EmpSearchTable getCurrentempdata() {
        return currentempdata;
    }

    public void setCurrentempdata(EmpSearchTable currentempdata) {
        this.currentempdata = currentempdata;
    }

    public List<SelectItem> getRatlist() {
        return ratlist;
    }

    public void setRatlist(List<SelectItem> ratlist) {
        this.ratlist = ratlist;
    }

    public List<SelectItem> getBlocklist() {
        return blocklist;
    }

    public void setBlocklist(List<SelectItem> blocklist) {
        this.blocklist = blocklist;
    }

    public List<SelectItem> getDeptlist() {
        return deptlist;
    }

    public void setDeptlist(List<SelectItem> deptlist) {
        this.deptlist = deptlist;
    }

    public List<SelectItem> getDeslist() {
        return deslist;
    }

    public void setDeslist(List<SelectItem> deslist) {
        this.deslist = deslist;
    }

    public List<SelectItem> getLocationlist() {
        return locationlist;
    }

    public void setLocationlist(List<SelectItem> locationlist) {
        this.locationlist = locationlist;
    }

    public List<SelectItem> getZonelist() {
        return zonelist;
    }

    public void setZonelist(List<SelectItem> zonelist) {
        this.zonelist = zonelist;
    }

    public List<EmpSearchTable> getDatagridemp() {
        return datagridemp;
    }

    public void setDatagridemp(List<EmpSearchTable> datagridemp) {
        this.datagridemp = datagridemp;
    }

    public Date getApprovaldt() {
        return approvaldt;
    }

    public void setApprovaldt(Date approvaldt) {
        this.approvaldt = approvaldt;
    }

    public String getApprovhead() {
        return approvhead;
    }

    public void setApprovhead(String approvhead) {
        this.approvhead = approvhead;
    }

    public String getApprovhrd() {
        return approvhrd;
    }

    public void setApprovhrd(String approvhrd) {
        this.approvhrd = approvhrd;
    }

    public String getApprovmd() {
        return approvmd;
    }

    public void setApprovmd(String approvmd) {
        this.approvmd = approvmd;
    }

    public String getArno() {
        return arno;
    }

    public void setArno(String arno) {
        this.arno = arno;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBudgetstatus() {
        return budgetstatus;
    }

    public void setBudgetstatus(String budgetstatus) {
        this.budgetstatus = budgetstatus;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Date getEffectivedt() {
        return effectivedt;
    }

    public void setEffectivedt(Date effectivedt) {
        this.effectivedt = effectivedt;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getLastpromotion() {
        return lastpromotion;
    }

    public void setLastpromotion(Date lastpromotion) {
        this.lastpromotion = lastpromotion;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNewblock() {
        return newblock;
    }

    public void setNewblock(String newblock) {
        this.newblock = newblock;
    }

    public String getOldlocation() {
        return oldlocation;
    }

    public void setOldlocation(String oldlocation) {
        this.oldlocation = oldlocation;
    }

    public String getOverallrating() {
        return overallrating;
    }

    public void setOverallrating(String overallrating) {
        this.overallrating = overallrating;
    }

    public String getProdeignation() {
        return prodeignation;
    }

    public void setProdeignation(String prodeignation) {
        this.prodeignation = prodeignation;
    }

    public String getProposeddept() {
        return proposeddept;
    }

    public void setProposeddept(String proposeddept) {
        this.proposeddept = proposeddept;
    }

    public String getProsupervisor() {
        return prosupervisor;
    }

    public void setProsupervisor(String prosupervisor) {
        this.prosupervisor = prosupervisor;
    }

    public String getProzone() {
        return prozone;
    }

    public void setProzone(String prozone) {
        this.prozone = prozone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReportinto() {
        return reportinto;
    }

    public void setReportinto(String reportinto) {
        this.reportinto = reportinto;
    }

    public String getZonefrom() {
        return zonefrom;
    }

    public void setZonefrom(String zonefrom) {
        this.zonefrom = zonefrom;
    }

    public String getSearchflag() {
        return searchflag;
    }

    public void setSearchflag(String searchflag) {
        this.searchflag = searchflag;
    }

    public String getSearchtext() {
        return searchtext;
    }

    public void setSearchtext(String searchtext) {
        this.searchtext = searchtext;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<SelectItem> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<SelectItem> operationList) {
        this.operationList = operationList;
    }
    

    public void initData() {
        try {
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            List<HrMstStructTO> locationdata = managedelegate.getInitialData(Integer.parseInt(compCode), "LOC%");
            List<HrMstStructTO> deptdata = managedelegate.getInitialData(Integer.parseInt(compCode), "DEP%");
            List<HrMstStructTO> blockdata = managedelegate.getInitialData(Integer.parseInt(compCode), "BLO%");
            List<HrMstStructTO> zonedata = managedelegate.getInitialData(Integer.parseInt(compCode), "ZON%");
            List<HrMstStructTO> desdata = managedelegate.getInitialData(Integer.parseInt(compCode), "DES%");
            List<HrMstStructTO> ratdata = managedelegate.getInitialData(Integer.parseInt(compCode), "RAT%");
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","---Select---"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","Edit"));
            locationlist.add(new SelectItem("Select", "Select"));
            deptlist.add(new SelectItem("Select", "Select"));
            blocklist.add(new SelectItem("Select", "Select"));
            zonelist.add(new SelectItem("Select", "Select"));
            deslist.add(new SelectItem("Select", "Select"));
            ratlist.add(new SelectItem("Select", "Select"));

            for (HrMstStructTO to : locationdata) {
                locationlist.add(new SelectItem(to.getHrMstStructPKTO().getStructCode(), to.getDescription()));
            }
            for (HrMstStructTO to : deptdata) {
                deptlist.add(new SelectItem(to.getHrMstStructPKTO().getStructCode(), to.getDescription()));
            }
            for (HrMstStructTO to : blockdata) {
                blocklist.add(new SelectItem(to.getHrMstStructPKTO().getStructCode(), to.getDescription()));
            }
            for (HrMstStructTO to : zonedata) {
                zonelist.add(new SelectItem(to.getHrMstStructPKTO().getStructCode(), to.getDescription()));
            }
            for (HrMstStructTO to : desdata) {
                deslist.add(new SelectItem(to.getHrMstStructPKTO().getStructCode(), to.getDescription()));
            }
            for (HrMstStructTO to : ratdata) {
                ratlist.add(new SelectItem(to.getHrMstStructPKTO().getStructCode(), to.getDescription()));
            }
            budgetlist.add(new SelectItem("Select", "Select"));
            budgetlist.add(new SelectItem("Y", "REPLACEMENT"));
            budgetlist.add(new SelectItem("N", "ADDITIONAL"));
            approve.add(new SelectItem("Select", "Select"));
            approve.add(new SelectItem("Y", "Yes"));
            approve.add(new SelectItem("N", "No"));
        } catch (WebException e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method initData()", e1);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }

    }
    public void onfindMethod() {
        datagridemp = new ArrayList<EmpSearchTable>();
        try {
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            List<HrPersonnelDetailsTO> result = null;
            if (this.getSearchflag().equals("Emp-Id")) {
                result = managedelegate.findDataPersonalDetailDelegat(Integer.parseInt(compCode), this.getSearchtext() + "%", 0);
            } else {
                result = managedelegate.findDataPersonalDetailDelegat(Integer.parseInt(compCode), this.getSearchtext() + "%", 1);
            }
            for (HrPersonnelDetailsTO hr : result) {
                EmpSearchTable grid = new EmpSearchTable();
                grid.setEmpidD(hr.getEmpId());
                grid.setEmpnameD(hr.getEmpName());
                grid.setEmpphD(hr.getEmpContAdd());
                grid.setEmpaddD(hr.getEmpContAdd());
                datagridemp.add(grid);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method onfindMethod()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method onfindMethod()", e1);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method onfindMethod()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }

    }

    public void selectDataEmpMethod() {
        try {
            save = false;
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            List result = managedelegate.selectDataEmpidDelegate(Integer.parseInt(compCode), currentempdata.getEmpidD());
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] data = (Object[]) itr.next();
                setEmpCode(data[0] != null ? data[0].toString() : "");
                setEmpId(data[1] != null ? data[1].toString() : "");
                setEmpName(data[2] != null ? data[2].toString() : "");
                setBlockCode(data[3] != null ? data[3].toString() : "");
                setBlock(data[4] != null ? data[4].toString() : "");
                setDeptCode(data[7] != null ? data[7].toString() : "");
                setDepartment(data[8] != null ? data[8].toString() : "");
                setDesgCode(data[9] != null ? data[9].toString() : "");
                setDesignation(data[10] != null ? data[10].toString() : "");
                setGradeCode(data[11] != null ? data[11].toString() : "");
                setZoneCode(data[13] != null ? data[13].toString() : "");
                setZonefrom(data[14] != null ? data[14].toString() : "");
                setOldlocation(data[16] != null ? data[16].toString() : "");
                setLocatCode(data[15] != null ? data[15].toString() : "");
                setReportinto(data[20] != null ? data[20].toString() : "");

            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method selectDataEmpMethod()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method selectDataEmpMethod()", e1);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDataEmpMethod()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveData() {
        errMsg = validation();
        if (!errMsg.equalsIgnoreCase("Ok")) {
            return;
        }
        HrPersonnelDetailsTO hrper = new HrPersonnelDetailsTO();
        HrPersonnelDetailsPKTO hrperpk = new HrPersonnelDetailsPKTO();
        HrPromotionDetailsTO hrpro = new HrPromotionDetailsTO();
        HrPromotionDetailsPKTO hrpropk = new HrPromotionDetailsPKTO();
        hrpropk.setCompCode(Integer.parseInt(compCode));
        hrpropk.setArNo(getArno());
        hrpro.setEmpCode(Long.parseLong(empCode));
        hrpro.setArdate(sdf.format(approvaldt));
        hrpro.setDeptFrom(deptCode);
        hrpro.setDeptTo(proposeddept);
        hrpro.setBlockFrom(blockCode);
        hrpro.setBlockTo(newblock);
        hrpro.setZoneFrom(zoneCode);
        hrpro.setZoneTo(prozone);
        hrpro.setPresLocat(locatCode);
        hrpro.setProposLocat(location);
        hrpro.setPresDesig(desgCode);
        hrpro.setProposDesig(prodeignation);
        hrpro.setPresRepId(getReportinto());
        hrpro.setProposRepId(getProsupervisorId());
        hrpro.setBudgtStatus(budgetstatus.charAt(0));
        hrpro.setOverRating(overallrating);
        hrpro.setRemarks1(remark);
        hrpro.setPromWef(sdf.format(effectivedt));
        hrpro.setHeadApprov(approvhead.charAt(0));
        hrpro.setHrdApprov(approvhrd.charAt(0));
        hrpro.setMdApprov(approvmd.charAt(0));
        hrpro.setDefaultComp(defaultComp);
        hrpro.setStatFlag("N");
        hrpro.setStatUpFlag("Y");
        if(flag.equalsIgnoreCase("ADD"))
        {
            hrpro.setModDate(new Date());
        }
        else if(flag.equalsIgnoreCase("EDIT"))
        {
            hrpro.setModDate(lastpromotion);
        }
        hrpro.setAuthBy(getUserName());
        hrpro.setEnteredBy(getUserName());
        hrpro.setHrPromotionDetailsPKTO(hrpropk);
        hrperpk.setCompCode(Integer.parseInt(compCode));
        hrperpk.setEmpCode(Long.parseLong(empCode));
        hrper.setLocatCode(location);
        hrper.setBlock(newblock);
        hrper.setRepTo(getProsupervisorId());
        hrper.setZones(prozone);
        hrper.setDesgCode(prodeignation);
        hrper.setDeptCode(proposeddept);
        hrper.setHrPersonnelDetailsPKTO(hrperpk);
        try {
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            errMsg = managedelegate.performoperationPromotionDelegate(flag, hrpro, hrper);
            refreshData();
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveData()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveData()", e1);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveData()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }

    }

//    public void editData() {
//        flag = "EDIT";
//        errMsg = validation();
//        if (!errMsg.equalsIgnoreCase("Ok")) {
//            return;
//        }
//        HrPersonnelDetailsTO hrper = new HrPersonnelDetailsTO();
//        HrPersonnelDetailsPKTO hrperpk = new HrPersonnelDetailsPKTO();
//        HrPromotionDetailsTO hrpro = new HrPromotionDetailsTO();
//        HrPromotionDetailsPKTO hrpropk = new HrPromotionDetailsPKTO();
//        hrpropk.setCompCode(Integer.parseInt(compCode));
//        hrpropk.setArNo(getArno());
//        hrpro.setEmpCode(Long.parseLong(empCode));
//        hrpro.setArdate(sdf.format(approvaldt));
//        hrpro.setDeptFrom(deptCode);
//        hrpro.setDeptTo(proposeddept);
//        hrpro.setBlockFrom(blockCode);
//        hrpro.setBlockTo(newblock);
//        hrpro.setZoneFrom(zoneCode);
//        hrpro.setZoneTo(prozone);
//        hrpro.setPresLocat(locatCode);
//        hrpro.setProposLocat(location);
//        hrpro.setPresDesig(desgCode);
//        hrpro.setProposDesig(prodeignation);
//        hrpro.setPresRepId(getReportinto());
//        hrpro.setProposRepId(getProsupervisorId());
//        hrpro.setBudgtStatus(budgetstatus.charAt(0));
//        hrpro.setOverRating(overallrating);
//        hrpro.setRemarks1(remark);
//        hrpro.setPromWef(sdf.format(effectivedt));
//        hrpro.setHeadApprov(approvhead.charAt(0));
//        hrpro.setHrdApprov(approvhrd.charAt(0));
//        hrpro.setMdApprov(approvmd.charAt(0));
//        hrpro.setDefaultComp(defaultComp);
//        hrpro.setStatFlag("N");
//        hrpro.setStatUpFlag("Y");
//        hrpro.setModDate(new java.util.Date());
//        hrpro.setAuthBy(getUserName());
//        hrpro.setEnteredBy(getUserName());
//        hrpro.setHrPromotionDetailsPKTO(hrpropk);
//        hrperpk.setCompCode(Integer.parseInt(compCode));
//        hrperpk.setEmpCode(Long.parseLong(empCode));
//        hrper.setLocatCode(location);
//        hrper.setBlock(newblock);
//        hrper.setRepTo(getProsupervisorId());
//        hrper.setZones(prozone);
//        hrper.setDesgCode(prodeignation);
//        hrper.setDeptCode(proposeddept);
//        hrper.setHrPersonnelDetailsPKTO(hrperpk);
//        try {
//            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
//            errMsg = managedelegate.performoperationPromotionDelegate(flag, hrpro, hrper);
//        } catch (WebException e) {
//            logger.error("Exception occured while executing method editData()", e);
//            this.setErrMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
//        } catch (ServiceLocatorException e1) {
//            logger.error("Exception occured while executing method editData()", e1);
//            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//        } catch (Exception e) {
//            logger.error("Exception occured while executing method editData()", e);
//            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//        }
//        refreshData();
//    }

    public void deleteData() {
        errMsg = validation();
        if (!errMsg.equalsIgnoreCase("Ok")) {
            return;
        }
        flag = "DELETE";
        HrPersonnelDetailsTO hrper = new HrPersonnelDetailsTO();
        HrPersonnelDetailsPKTO hrperpk = new HrPersonnelDetailsPKTO();
        HrPromotionDetailsTO hrpro = new HrPromotionDetailsTO();
        HrPromotionDetailsPKTO hrpropk = new HrPromotionDetailsPKTO();
        hrpropk.setCompCode(Integer.parseInt(compCode));
        hrpropk.setArNo(getArno());
        hrpro.setEmpCode(Long.parseLong(empCode));
        hrpro.setArdate(sdf.format(approvaldt));
        hrpro.setDeptFrom(deptCode);
        hrpro.setDeptTo(proposeddept);
        hrpro.setBlockFrom(blockCode);
        hrpro.setBlockTo(newblock);
        hrpro.setZoneFrom(zoneCode);
        hrpro.setZoneTo(prozone);
        hrpro.setPresLocat(locatCode);
        hrpro.setProposLocat(location);
        hrpro.setPresDesig(desgCode);
        hrpro.setProposDesig(prodeignation);
        hrpro.setPresRepId(getReportinto());
        hrpro.setProposRepId(getProsupervisorId());
        hrpro.setBudgtStatus(budgetstatus.charAt(0));
        hrpro.setOverRating(overallrating);
        hrpro.setRemarks1(remark);
        hrpro.setPromWef(sdf.format(effectivedt));
        hrpro.setHeadApprov(approvhead.charAt(0));
        hrpro.setHrdApprov(approvhrd.charAt(0));
        hrpro.setMdApprov(approvmd.charAt(0));
        hrpro.setDefaultComp(defaultComp);
        hrpro.setStatFlag("N");
        hrpro.setStatUpFlag("Y");
        hrpro.setModDate(new java.util.Date());
        hrpro.setAuthBy(getUserName());
        hrpro.setEnteredBy(getUserName());
        hrpro.setHrPromotionDetailsPKTO(hrpropk);
        hrperpk.setCompCode(Integer.parseInt(compCode));
        hrperpk.setEmpCode(Long.parseLong(empCode));
        hrper.setLocatCode(location);
        hrper.setBlock(newblock);
        hrper.setRepTo(getProsupervisorId());
        hrper.setZones(prozone);
        hrper.setDesgCode(prodeignation);
        hrper.setDeptCode(proposeddept);
        hrper.setHrPersonnelDetailsPKTO(hrperpk);
        try {
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            errMsg = managedelegate.performoperationPromotionDelegate(flag, hrpro, hrper);
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteData()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method deleteData()", e1);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteData()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        refreshData();
    }

    public void viewData() {
        try {
            datagrid = new ArrayList<PromotionTable>();
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            List result = managedelegate.viewDataPromotionDelegate(Integer.parseInt(compCode));
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] data = (Object[]) itr.next();
                PromotionTable grid = new PromotionTable();
                grid.setCompCode(data[0] != null ? data[0].toString() : "");
                grid.setEmpCode(data[1] != null ? data[1].toString() : "");
                grid.setEmpId(data[2] != null ? data[2].toString() : "");
                grid.setArNo(data[3] != null ? data[3].toString() : "");
                grid.setArdate(data[4] != null ? data[4].toString() : "");
                grid.setDeptFrom(data[5] != null ? data[5].toString() : "");
                grid.setDeptTo(data[6] != null ? data[6].toString() : "");
                grid.setBlockTo(data[7] != null ? data[7].toString() : "");
                grid.setZoneFrom(data[8] != null ? data[8].toString() : "");
                grid.setZoneTo(data[9] != null ? data[9].toString() : "");
                grid.setPresLocat(data[10] != null ? data[10].toString() : "");
                grid.setProposRepId(data[11] != null ? data[11].toString() : "");
                grid.setBudgtStatus(data[12] != null ? data[12].toString() : "");
                grid.setOverRating(data[13] != null ? data[13].toString() : "");
                grid.setRemarks1(data[14] != null ? data[14].toString() : "");
                grid.setPromWef(data[15] != null ? data[15].toString() : "");
                grid.setHeadApprov(data[16] != null ? data[16].toString() : "");
                grid.setHrdApprov(data[17] != null ? data[17].toString() : "");
                grid.setMdApprov(data[18] != null ? data[18].toString() : "");
                grid.setEmpName(data[19] != null ? data[19].toString() : "");
                grid.setProposDesig(data[20] != null ? data[20].toString() : "");
                grid.setPresDesig(data[21] != null ? data[21].toString() : "");
                grid.setBlockFrom(data[22] != null ? data[22].toString() : "");
                grid.setProposLocate(data[23] != null ? data[23].toString() : "");
                grid.setPresRepId(data[24] != null ? data[24].toString() : "");
                grid.setLastPromotionDt(data[25]!=null?sdf.parse(sdf.format((Date)data[25])):null);
                datagrid.add(grid);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method viewData()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method viewData()", e1);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method viewData()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectData() throws Exception {
        delete = false;
        save = false;
        setEmpName(currentdata.getEmpName());
        setEmpCode(currentdata.getEmpCode());
        setEmpId(currentdata.getEmpId());
        setArno(currentdata.getArNo());
        setApprovhrd(currentdata.getHrdApprov());
        setApprovhead(currentdata.getHeadApprov());
        setApprovmd(currentdata.getMdApprov());
        setDesgCode(currentdata.getPresDesig());
        setDeptCode(currentdata.getDeptFrom());
        setLocatCode(currentdata.getPresLocat());
        setZoneCode(currentdata.getZoneFrom());
        setBlockCode(currentdata.getBlockFrom());
        setProsupervisor(currentdata.getProposRepId());
        setBudgetstatus(currentdata.getBudgtStatus());
        setRemark(currentdata.getRemarks1());
        setEffectivedt(sdf.parse(currentdata.getPromWef()));
        setApprovaldt(sdf.parse(currentdata.getArdate()));
        setProposeddept(currentdata.getDeptTo());
        setNewblock(currentdata.getBlockTo());
        setProzone(currentdata.getZoneTo());
        setProdeignation(currentdata.getProposDesig());
        setOverallrating(currentdata.getOverRating());
        setLocation(currentdata.getProposLocate());
        setReportinto(currentdata.getPresRepId());
        setLastpromotion(currentdata.getLastPromotionDt());
        
    }

    public void addArnno() {
        try {
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            setArno(managedelegate.generateArnCodeDelegate(Integer.parseInt(compCode)));

        } catch (WebException e) {
            logger.error("Exception occured while executing method selectData()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method selectData()", e1);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectData()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }

    }

    public void selectSupervisor() {
        setProsupervisor(currentempdata.getEmpnameD());
        setProsupervisorId(currentempdata.getEmpidD());
    }

    public void refreshData() {
        empName = "";
        empId = "";
        desgCode = "";
        deptCode = "";
        reportinto = "";
        locatCode = null;
        approvaldt = null;
        effectivedt = null;
        arno = "";
        location = "Select";
        proposeddept = "Select";
        newblock = "Select";
        prosupervisor = "";
        prozone = "Select";
        prodeignation = "Select";
        overallrating = "";
        remark = "";
        budgetstatus = "Select";
        approvhrd = "Select";
        approvhead = "Select";
        approvmd = "Select";
        setZoneCode("Select");
        setBlockCode("Select");
        update = true;
        save = true;
        delete = true;
        add = false;
        lastpromotion=null;
        setOperation("0");
        viewData();
    }

    public void refreshButtonAction() {
        errMsg = "";
        empName = "";
        empId = "";
        desgCode = "";
        deptCode = "";
        reportinto = "";
        locatCode = null;
        approvaldt = null;
        effectivedt = null;
        arno = "";
        location = "Select";
        proposeddept = "Select";
        newblock = "Select";
        prosupervisor = "";
        prozone = "Select";
        prodeignation = "Select";
        overallrating = "";
        remark = "";
        budgetstatus = "Select";
        approvhrd = "Select";
        approvhead = "Select";
        approvmd = "Select";
        setZoneCode("Select");
        setBlockCode("Select");
        update = true;
        save = true;
        delete = true;
        add = false;
        lastpromotion=null;
        setOperation("0");
        viewData();
    }

    public String validation() {
        if (empName == null) {
            return "Slect Data";
        }
        if (empId == null) {
            return "Slect Data";
        }
        if (desgCode == null) {
            return "Slect Data";
        }
        if (deptCode == null) {
            return "Slect Data";
        }
        if (reportinto == null) {
            return "Select Data";
        }
        if (locatCode == null) {
            return "Select Data";
        }
        if (approvaldt == null) {
            return "Enter the Apporoval Date";
        }
        if (effectivedt == null) {
            return "Enter the Efective Date";
        }
        if (arno == null) {
            return "";
        }
        if (location.equalsIgnoreCase("Select")) {
            return "Select Location";
        }
        if (proposeddept.equalsIgnoreCase("Select")) {
            return "Select Department";
        }
        if (newblock.equalsIgnoreCase("Select")) {
            return "Select New Block";
        }
        if (prosupervisor == null) {
            return "Enter the SuperVisor";
        }
        if (prozone.equalsIgnoreCase("Select")) {
            return "Select Proposed Zone";
        }
        if (prodeignation.equalsIgnoreCase("Select")) {
            return "Select Proposed Designation";
        }
        if (overallrating.equalsIgnoreCase("")) {
            return "Select Overall Rating";
        }
        if (remark == null) {
            return "Enter the Remarks";
        }
        if (budgetstatus.equalsIgnoreCase("Select")) {
            return "Select Budget Status";
        }
        if (approvhrd.equalsIgnoreCase("Select")) {
            return "Select Approved by Hrd";
        }
        if (approvhead.equalsIgnoreCase("Select")) {
            return "Select Approved by Head";
        }
        if (approvmd.equalsIgnoreCase("Select")) {
            return "Select Approved by MD";
        }
        try {
            sdf.format(approvaldt);
            sdf.format(effectivedt);
//            effectivedt
        } catch (Exception e) {
            return "Enter Valid Date";
        }
        return "ok";
    }

    public String btnExit() {
        return "case1";
    }
      public void setOperationOnBlur()
    {
        if(operation.equalsIgnoreCase("0"))
        {
            setErrMsg("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
            flag="ADD";
            addArnno();
            setErrMsg("");
            
        } else if (operation.equalsIgnoreCase("2")) {
            flag = "EDIT";
            setErrMsg("");
        }
    }
}
