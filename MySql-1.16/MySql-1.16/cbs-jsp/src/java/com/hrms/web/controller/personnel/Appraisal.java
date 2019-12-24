/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.personnel;

import com.hrms.web.pojo.EmpSearchTable;
import com.hrms.web.pojo.AppraisalDetailTable;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrApprisalDetailsTO;
import com.hrms.common.to.HrApprisalDetailsPKTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.PersonnelMasterManagementDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.web.utils.WebUtil;

/**
 *
 * @author admin
 */
public class Appraisal extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(Appraisal.class);
    private String searchtext;
    private String searchflag;
    private String empname;
    private String empid;
    private String empCode;
    private String department;
    private String designation;
    private String joindate;
    private String unit;
    private String grade;
    private String block;
    private String expr;
    private Date apprisaldt;
    private Date effectivedt;
    private String overallrating;
    private String trainingneed;
    private String incementamt;
    private String slcnhd;
    private String hdpdt;
    private String hdhrd;
    private String prsnlhead;
    private String performancefct;
    private String rating;
    private boolean add;
    private boolean view;
    private boolean save;
    private boolean update;
    private boolean delete;
    private String flag;
    private String compCode = "0";
    private String errorMsg;
    private AppraisalDetailTable currentrow;
    private int defaultComp;
    private List<AppraisalDetailTable> griddata;
    private List<SelectItem> overallratingdata;
    private List<SelectItem> trainingneeddata;
    private List<SelectItem> performancefctdata;
    private List<EmpSearchTable> datagridemp;
    private EmpSearchTable selectdataEmpdetail;
    private String operation;
    private List<SelectItem> operationList;
    
    /** Creates a new instance of Appraisal */
    public Appraisal() {

        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            initData();
            viewData();
            update = true;
            delete = true;
            save = true;
        } catch (Exception e) {
            logger.error("Exception occured while executing method Consultant()", e);
            logger.error("Consultant()", e);
        }
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
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

    public AppraisalDetailTable getCurrentrow() {
        return currentrow;
    }

    public void setCurrentrow(AppraisalDetailTable currentrow) {
        this.currentrow = currentrow;
    }

    public List<AppraisalDetailTable> getGriddata() {
        return griddata;
    }

    public void setGriddata(List<AppraisalDetailTable> griddata) {
        this.griddata = griddata;
    }

    public List<SelectItem> getOverallratingdata() {
        return overallratingdata;
    }

    public void setOverallratingdata(List<SelectItem> overallratingdata) {
        this.overallratingdata = overallratingdata;
    }

    public List<SelectItem> getPerformancefctdata() {
        return performancefctdata;
    }

    public void setPerformancefctdata(List<SelectItem> performancefctdata) {
        this.performancefctdata = performancefctdata;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<SelectItem> getTrainingneeddata() {
        return trainingneeddata;
    }

    public void setTrainingneeddata(List<SelectItem> trainingneeddata) {
        this.trainingneeddata = trainingneeddata;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    public Date getApprisaldt() {
        return apprisaldt;
    }

    public void setApprisaldt(Date apprisaldt) {
        this.apprisaldt = apprisaldt;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
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

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getHdhrd() {
        return hdhrd;
    }

    public void setHdhrd(String hdhrd) {
        this.hdhrd = hdhrd;
    }

    public String getHdpdt() {
        return hdpdt;
    }

    public void setHdpdt(String hdpdt) {
        this.hdpdt = hdpdt;
    }

    public String getIncementamt() {
        return incementamt;
    }

    public void setIncementamt(String incementamt) {
        this.incementamt = incementamt;
    }

    public String getOverallrating() {
        return overallrating;
    }

    public void setOverallrating(String overallrating) {
        this.overallrating = overallrating;
    }

    public String getPerformancefct() {
        return performancefct;
    }

    public void setPerformancefct(String performancefct) {
        this.performancefct = performancefct;
    }

    public String getPrsnlhead() {
        return prsnlhead;
    }

    public void setPrsnlhead(String prsnlhead) {
        this.prsnlhead = prsnlhead;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSlcnhd() {
        return slcnhd;
    }

    public void setSlcnhd(String slcnhd) {
        this.slcnhd = slcnhd;
    }

    public String getTrainingneed() {
        return trainingneed;
    }

    public void setTrainingneed(String trainingneed) {
        this.trainingneed = trainingneed;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<EmpSearchTable> getDatagridemp() {
        return datagridemp;
    }

    public EmpSearchTable getSelectdataEmpdetail() {
        return selectdataEmpdetail;
    }

    public void setSelectdataEmpdetail(EmpSearchTable selectdataEmpdetail) {
        this.selectdataEmpdetail = selectdataEmpdetail;
    }

    public void setDatagridemp(List<EmpSearchTable> datagridemp) {
        this.datagridemp = datagridemp;
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

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void initData() {
        try {
            overallratingdata = new ArrayList<SelectItem>();
            trainingneeddata = new ArrayList<SelectItem>();
            performancefctdata = new ArrayList<SelectItem>();
            overallratingdata.add(new SelectItem("Select", "Select"));
            trainingneeddata.add(new SelectItem("Select", "Select"));
            performancefctdata.add(new SelectItem("Select", "Select"));
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","---Select---"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","Edit"));
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            List<HrMstStructTO> ratingdata = managedelegate.getInitialData(Integer.parseInt(compCode), "RAT%");
            List<HrMstStructTO> traingdata = managedelegate.getInitialData(Integer.parseInt(compCode), "TRA%");
            List<HrMstStructTO> performdata = managedelegate.getInitialData(Integer.parseInt(compCode), "PER%");
            for (HrMstStructTO to : ratingdata) {
                overallratingdata.add(new SelectItem(to.getHrMstStructPKTO().getStructCode(), to.getDescription()));
            }
            for (HrMstStructTO to : traingdata) {
                trainingneeddata.add(new SelectItem(to.getHrMstStructPKTO().getStructCode(), to.getDescription()));
            }
            for (HrMstStructTO to : performdata) {
                performancefctdata.add(new SelectItem(to.getHrMstStructPKTO().getStructCode(), to.getDescription()));
            }

        } catch (Exception e) {
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
                grid.setEmpphD(hr.getEmpContTel());
                grid.setEmpaddD(hr.getEmpContAdd());
                datagridemp.add(grid);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setErrorMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method initData()", e1);
            this.setErrorMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setErrorMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectDataEmpMethod() {
        save = false;
        delete = true;
        setErrorMsg("");
        try {
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            List result = managedelegate.selectDataEmpidDelegate(Integer.parseInt(compCode), selectdataEmpdetail.getEmpidD());
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] data = (Object[]) itr.next();
                setEmpCode(data[0].toString());
                setEmpid(data[1].toString());
                setEmpname(data[2].toString());
                setBlock(data[4].toString());
                setDepartment(data[8].toString());
                setDesignation(data[10].toString());
                setGrade(data[12].toString());
                setUnit(data[6].toString());
                setJoindate(sdf.format((Date) data[18]));
                setExpr(data[19]==null?"0":data[19].toString());
            }
        } catch (ServiceLocatorException e1) {
            e1.printStackTrace();
            logger.error("Exception occured while executing method selectDataEmpMethod()", e1);
            this.setErrorMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method selectDataEmpMethod()", e);
            this.setErrorMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectUpdate() throws Exception {
        save = false;
        delete = false;
        try {
            setEmpCode(currentrow.getEmpCode());
            setEmpid(currentrow.getEmpId());
            setEmpname(currentrow.getEmpName());
            setJoindate(currentrow.getJoinDate());
            setApprisaldt(sdf.parse(currentrow.getAppraisalDt()));
            setEffectivedt(sdf.parse(currentrow.getPromWef()));
            setOverallrating(currentrow.getRatingCode());
            setTrainingneed(currentrow.getTrngCode());
            setIncementamt(currentrow.getIncrAmt());
            setSlcnhd(currentrow.getRecHead());
            setHdpdt(currentrow.getRecHod());
            setHdhrd(currentrow.getRecHrd());
            setPrsnlhead(currentrow.getRecPersonnel());
            setExpr(currentrow.getTotExpr());
            setUnit(currentrow.getUnitNameDescription());
            setGrade(currentrow.getGradeCodeDescription());
            setDepartment(currentrow.getDeptCodeDescription());
            setDesignation(currentrow.getDesgCodeDescription());
            setBlock(currentrow.getBlockDescription());
            setRating(currentrow.getRatingCode());
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectUpdate()", e);
            this.setErrorMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveData() {
        errorMsg = validation();
        if (!errorMsg.equalsIgnoreCase("Ok")) {
            return;
        }
        HrApprisalDetailsTO hrApprisalDetailsTo = new HrApprisalDetailsTO();
        HrApprisalDetailsPKTO hrApprisalDetailsPKTO = new HrApprisalDetailsPKTO();
        hrApprisalDetailsPKTO.setAppraisalDt(sdf.format(getApprisaldt()));
        hrApprisalDetailsPKTO.setCompCode(Integer.parseInt(compCode));
        hrApprisalDetailsPKTO.setEmpCode(Long.parseLong(getEmpCode()));
        hrApprisalDetailsTo.setRatingCode(getOverallrating());
        hrApprisalDetailsTo.setApprResult('Y');
        hrApprisalDetailsTo.setTrngCode(getTrainingneed());
        hrApprisalDetailsTo.setRecHead(getSlcnhd());
        hrApprisalDetailsTo.setRecHod(getHdpdt());
        hrApprisalDetailsTo.setRecHrd(getHdhrd());
        hrApprisalDetailsTo.setRecPersonnel(getPrsnlhead());
        hrApprisalDetailsTo.setIncrAmt(Double.parseDouble(getIncementamt()));
        hrApprisalDetailsTo.setPromWef(sdf.format(getEffectivedt()));
        hrApprisalDetailsTo.setDefaultComp(defaultComp);
        hrApprisalDetailsTo.setStatFlag("Y");
        hrApprisalDetailsTo.setStatUpFlag("Y");
        hrApprisalDetailsTo.setModDate(new java.util.Date());
        hrApprisalDetailsTo.setAuthBy(getUserName());
        hrApprisalDetailsTo.setEnteredBy(getUserName());
        hrApprisalDetailsTo.setModDate(new java.util.Date());
        hrApprisalDetailsTo.setHrApprisalDetailsPKTO(hrApprisalDetailsPKTO);
        try {
            PersonnelMasterManagementDelegate delegate = new PersonnelMasterManagementDelegate();
            errorMsg = delegate.performOperationAppraisalDelegate(flag, hrApprisalDetailsTo);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveData()", e1);
            this.setErrorMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveData()", e);
            this.setErrorMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        refreshData();
    }

    public void deleteData() {
        System.out.println("delete data");

        flag = "Delete";
        errorMsg = validation();
        if (!errorMsg.equalsIgnoreCase("Ok")) {
            return;
        }
        HrApprisalDetailsPKTO hrApprisalDetailsPKTO = new HrApprisalDetailsPKTO();
        HrApprisalDetailsTO hrApprisalDetailsTO = new HrApprisalDetailsTO();
        hrApprisalDetailsPKTO.setAppraisalDt(sdf.format(getApprisaldt()));
        hrApprisalDetailsPKTO.setCompCode(Integer.parseInt(compCode));
        hrApprisalDetailsPKTO.setEmpCode(Long.parseLong(getEmpCode()));
        hrApprisalDetailsTO.setHrApprisalDetailsPKTO(hrApprisalDetailsPKTO);
        try {
            PersonnelMasterManagementDelegate delegate = new PersonnelMasterManagementDelegate();
            errorMsg = delegate.performOperationAppraisalDelegate(flag, hrApprisalDetailsTO);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method deleteData", e1);
            this.setErrorMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteData", e);
            this.setErrorMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        refreshData();
    }

//    public void editData() {
//        errorMsg = validation();
//        if (!errorMsg.equalsIgnoreCase("Ok")) {
//            return;
//        }
//        flag = "Edit";
//        HrApprisalDetailsTO hrApprisalDetailsTo = new HrApprisalDetailsTO();
//        HrApprisalDetailsPKTO hrApprisalDetailsPKTO = new HrApprisalDetailsPKTO();
//        hrApprisalDetailsPKTO.setAppraisalDt(sdf.format(getApprisaldt()));
//        hrApprisalDetailsPKTO.setCompCode(Integer.parseInt(compCode));
//        hrApprisalDetailsPKTO.setEmpCode(Long.parseLong(getEmpCode()));
//        hrApprisalDetailsTo.setRatingCode(getOverallrating());
//        hrApprisalDetailsTo.setApprResult('Y');
//        hrApprisalDetailsTo.setTrngCode(getTrainingneed());
//        hrApprisalDetailsTo.setRecHead(getSlcnhd());
//        hrApprisalDetailsTo.setRecHod(getHdpdt());
//        hrApprisalDetailsTo.setRecHrd(getHdhrd());
//        hrApprisalDetailsTo.setRecPersonnel(getPrsnlhead());
//        hrApprisalDetailsTo.setIncrAmt(Double.parseDouble(getIncementamt()));
//        hrApprisalDetailsTo.setPromWef(sdf.format(getEffectivedt()));
//        hrApprisalDetailsTo.setDefaultComp(defaultComp);
//        hrApprisalDetailsTo.setStatFlag("Y");
//        hrApprisalDetailsTo.setStatUpFlag("Y");
//        hrApprisalDetailsTo.setModDate(new java.util.Date());
//        hrApprisalDetailsTo.setAuthBy(getUserName());
//        hrApprisalDetailsTo.setEnteredBy(getUserName());
//        hrApprisalDetailsTo.setModDate(new java.util.Date());
//        hrApprisalDetailsTo.setHrApprisalDetailsPKTO(hrApprisalDetailsPKTO);
//        try {
//            PersonnelMasterManagementDelegate delegate = new PersonnelMasterManagementDelegate();
//            errorMsg = delegate.performOperationAppraisalDelegate(flag, hrApprisalDetailsTo);
//        } catch (ServiceLocatorException e1) {
//            logger.error("Exception occured while executing method editData()", e1);
//            this.setErrorMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//        } catch (Exception e) {
//            logger.error("Exception occured while executing method editData()", e);
//            this.setErrorMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//        }
//        refreshData();
//    }

    public void viewData() {
        try {
            PersonnelMasterManagementDelegate delegate = new PersonnelMasterManagementDelegate();
            griddata = new ArrayList<AppraisalDetailTable>();
            List result = delegate.viewDataAppraisalDelegate(Integer.parseInt(compCode));
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] data = (Object[]) itr.next();
                AppraisalDetailTable grid = new AppraisalDetailTable();
                grid.setEmpCode(data[0] != null ? data[0].toString() : "");
                grid.setEmpId(data[1] != null ? data[1].toString() : "");
                grid.setEmpName(data[2] != null ? data[2].toString() : "");
                grid.setJoinDate(data[3] != null ? sdf.format((Date) data[3]) : "");
                grid.setRatingCode(data[4] != null ? data[4].toString() : "");
                grid.setApprResult(data[5] != null ? data[5].toString() : "");
                grid.setTrngCode(data[6] != null ? data[6].toString() : "");
                grid.setRecHead(data[7] != null ? data[7].toString() : "");
                grid.setRecHod(data[8] != null ? data[8].toString() : "");
                grid.setRecHrd(data[9] != null ? data[9].toString() : "");
                grid.setRecPersonnel(data[10] != null ? data[10].toString() : "");
                grid.setIncrAmt(data[11] != null ? data[11].toString() : "");
                grid.setAppraisalDt(data[12] != null ? data[12].toString() : "");
                grid.setPromWef(data[13] != null ? data[13].toString() : "");
                grid.setDeptCode(data[14] != null ? data[14].toString() : "");
                grid.setDeptCodeDescription(data[15] != null ? data[15].toString() : "");
                grid.setDesgCode(data[16] != null ? data[16].toString() : "");
                grid.setDesgCodeDescription(data[17] != null ? data[17].toString() : "");
                grid.setGradeCode(data[18] != null ? data[18].toString() : "");
                grid.setGradeCodeDescription(data[19] != null ? data[19].toString() : "");
                grid.setBlock(data[20] != null ? data[20].toString() : "");
                grid.setBlockDescription(data[21] != null ? data[21].toString() : "");
                grid.setUnitName(data[22] != null ? data[22].toString() : "");
                grid.setUnitNameDescription(data[23] != null ? data[23].toString() : "");
                grid.setTotExpr(data[24] != null ? data[24].toString() : "");
                griddata.add(grid);
            }
        } catch (ServiceLocatorException e1) {
            e1.printStackTrace();
            logger.error("Exception occured while executing method viewData()", e1);
            this.setErrorMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method viewData()", e);
            this.setErrorMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void refreshData() {
        setEmpCode("");
        setEmpid("");
        setEmpname("");
        setJoindate("");
        setApprisaldt(null);
        setEffectivedt(null);
        setOverallrating("");
        setTrainingneed("");
        setIncementamt("");
        setSlcnhd("");
        setHdpdt("");
        setHdhrd("");
        setPrsnlhead("");
        setExpr("");
        setUnit("");
        setGrade("");
        setDepartment("");
        setDesignation("");
        setBlock("");
        setRating("");
        setOperation("0");
        viewData();
    }

    public void refreshbutton() {
        add = false;
        update = true;
        save = true;
        delete = true;
        setEmpCode("");
        setEmpid("");
        setEmpname("");
        setJoindate("");
        setApprisaldt(null);
        setEffectivedt(null);
        setOverallrating("");
        setTrainingneed("");
        setIncementamt("");
        setSlcnhd("");
        setHdpdt("");
        setHdhrd("");
        setPrsnlhead("");
        setExpr("");
        setUnit("");
        setGrade("");
        setDepartment("");
        setDesignation("");
        setBlock("");
        setRating("");
        setErrorMsg("");
        setOperation("0");
        viewData();
    }

    public String validation() {
        if (empname == null) {
            return "Select Data";
        }
        if (apprisaldt == null) {
            return "Enter the Apraisal date";
        }
        if (effectivedt == null) {
            return "Enter the Effective date";
        }
        if (overallrating.equalsIgnoreCase("Select")) {
            return "Select OverAll Rating";
        }
        if (trainingneed.equalsIgnoreCase("Select")) {
            return "Select Training Need";
        }
        if (incementamt == null) {
            return "Enter the Increment Amount";
        }
        if (slcnhd == null) {
            return "Enter Advice";
        }
        if (hdpdt == null) {
            return "Enter Advice";
        }
        if (hdhrd == null) {
            return "Enter Advice";
        }
        if (prsnlhead == null) {
            return "Enter Advice";
        }
        if (performancefct == null) {
            return "Select Performance factor";
        }
        if (rating.equalsIgnoreCase("Select")) {
            return "Select Rating";
        }
        if (!incementamt.matches("[0-9.]*")) {
            return "Please Enter Numeric Value in Increment Amount";
        }
        try {
            Double.parseDouble(incementamt);
        } catch (Exception e) {
            return "Enter the Numeric Value";
        }
        return "ok";
    }
    public void setOperationOnBlur()
    {
        if(operation.equalsIgnoreCase("0"))
        {
            setErrorMsg("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
            flag = "Add";
            setErrorMsg("");
            
        } else if (operation.equalsIgnoreCase("2")) {
            flag = "Edit";
            setErrorMsg("");
        }
    }

    public String btnExit() {
        return "case1";
    }
}
