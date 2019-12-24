/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.personnel;

import com.hrms.web.pojo.EmpSearchTable;
import com.hrms.web.pojo.AcceptanceletterTable;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrSeparationDetailsPKTO;
import com.hrms.common.to.HrSeparationDetailsTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.PersonnelMasterManagementDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.web.utils.WebUtil;

/**
 *
 * @author admin
 */
public class Acceptanceletter extends BaseBean implements Serializable {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(TransferDetails.class);
    private String empid;
    private String empname;
    private String emptype;
    private String deptcode;
    private String deptcodedesc;
    private String desgcode;
    private String desgcodedesc;
    private String gradecode;
    private String gradecodedesc;
    private Date joindate;
    private String noticeperiod;
    private String sepacode;
    private Date resignation;
    private String reason;
    private Date resigaccept;
    private Date sepration;
    private String searchtext;
    private String searchflag;
    private String msg;
    private String flag;
    private boolean save;
    private boolean delete;
    private String compCode = "0";
    private List<SelectItem> seprationtype = new ArrayList<SelectItem>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private AcceptanceletterTable selectdata;
    private EmpSearchTable selectdataEmpdetail;
    private List<EmpSearchTable> datagridemp;
    private List<AcceptanceletterTable> datagrid;
    private int defaultComp;
    private String operation;
    private List<SelectItem> operationList;
    
    /** Creates a new instance of Acceptanceletter */
    public Acceptanceletter() throws Exception {

        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","-Select-"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","Edit"));
            save = true;
            delete = true;
            setOperation("0");
            initData();
            seprationtype.add(new SelectItem("SEP001", "Type1"));
            seprationtype.add(new SelectItem("SEP002", "Type2"));
            setTodayDate(sdf.format(new java.util.Date()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method Consultant()", e);
            logger.error("Consultant()", e);
        }
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
    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public AcceptanceletterTable getSelectdata() {
        return selectdata;
    }

    public void setSelectdata(AcceptanceletterTable selectdata) {
        this.selectdata = selectdata;
    }

    public EmpSearchTable getSelectdataEmpdetail() {
        return selectdataEmpdetail;
    }

    public void setSelectdataEmpdetail(EmpSearchTable selectdataEmpdetail) {
        this.selectdataEmpdetail = selectdataEmpdetail;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<SelectItem> getSeprationtype() {
        return seprationtype;
    }

    public void setSeprationtype(List<SelectItem> seprationtype) {
        this.seprationtype = seprationtype;
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

    public List<EmpSearchTable> getDatagridemp() {
        return datagridemp;
    }

    public void setDatagridemp(List<EmpSearchTable> datagridemp) {
        this.datagridemp = datagridemp;
    }

    public List<AcceptanceletterTable> getDatagrid() {
        return datagrid;
    }

    public void setDatagrid(List<AcceptanceletterTable> datagrid) {
        this.datagrid = datagrid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    public String getDeptcodedesc() {
        return deptcodedesc;
    }

    public void setDeptcodedesc(String deptcodedesc) {
        this.deptcodedesc = deptcodedesc;
    }

    public String getDesgcode() {
        return desgcode;
    }

    public void setDesgcode(String desgcode) {
        this.desgcode = desgcode;
    }

    public String getDesgcodedesc() {
        return desgcodedesc;
    }

    public void setDesgcodedesc(String desgcodedesc) {
        this.desgcodedesc = desgcodedesc;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmptype() {
        return emptype;
    }

    public void setEmptype(String emptype) {
        this.emptype = emptype;
    }

    public String getGradecode() {
        return gradecode;
    }

    public void setGradecode(String gradecode) {
        this.gradecode = gradecode;
    }

    public String getGradecodedesc() {
        return gradecodedesc;
    }

    public void setGradecodedesc(String gradecodedesc) {
        this.gradecodedesc = gradecodedesc;
    }

    public Date getJoindate() {
        return joindate;
    }

    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }

    public String getNoticeperiod() {
        return noticeperiod;
    }

    public void setNoticeperiod(String noticeperiod) {
        this.noticeperiod = noticeperiod;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getResigaccept() {
        return resigaccept;
    }

    public void setResigaccept(Date resigaccept) {
        this.resigaccept = resigaccept;
    }

    public Date getResignation() {
        return resignation;
    }

    public void setResignation(Date resignation) {
        this.resignation = resignation;
    }

    public String getSepacode() {
        return sepacode;
    }

    public void setSepacode(String sepacode) {
        this.sepacode = sepacode;
    }

    public Date getSepration() {
        return sepration;
    }

    public void setSepration(Date sepration) {
        this.sepration = sepration;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public String validation() {
        if (empid == null) {
            return "Enter Emp Id";
        }
        if (empname == null) {
            return "Enter Employe Name";
        }
        if (emptype == null) {
            return "Select Employee Type";
        }
        if (deptcodedesc == null) {
            return "";
        }
        if (desgcodedesc == null) {
            return "";
        }
        if (sepacode == null) {
            return "Select";
        }
        if (resignation == null) {
            return "Enter the Resignation Date";
        }
        if (reason == null) {
            return "Enter the Reason";
        }
        if (resigaccept == null) {
            return "Enter Resignation Accept Date";
        }
        if (sepration == null) {
            return "Enter the Sepration Date";
        }
        return "ok";
    }
     public void setOperationOnBlur()
    {
        if(operation.equalsIgnoreCase("0"))
        {
            setMsg("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
           setFlag("ADD");
           setMsg("");
           save=false;
           delete=true;
           
         } else if (operation.equalsIgnoreCase("2")) {
            setFlag("EDIT");
            setMsg("");
            save=false;
            delete=false;
        
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectDataEmpMethod() {
        save = false;
        delete = true;
        List result = new ArrayList();
        try {
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            result = managedelegate.selectDataEmpidDelegate(Integer.parseInt(compCode), selectdataEmpdetail.getEmpidD());
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] data = (Object[]) itr.next();
                setEmpid(data[1] != null ? data[1].toString() : "");
                setEmpname(data[2] != null ? data[2].toString() : "");
                setEmptype(data[2] != null ? data[2].toString() : "");
                setDeptcode(data[7] != null ? data[7].toString() : "");
                setDeptcodedesc(data[8] != null ? data[8].toString() : "");
                setDesgcode(data[9] != null ? data[9].toString() : "");
                setDesgcodedesc(data[10] != null ? data[10].toString() : "");
                setGradecode(data[11] != null ? data[11].toString() : "");
                setGradecodedesc(data[12] != null ? data[12].toString() : "");
                setNoticeperiod(data[17] != null ? data[17].toString() : "");
                setJoindate(data[18] != null ? (Date) data[18] : null);

            }

        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method initData()", e1);
            this.setMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void initData() {
        try {

            datagrid = new ArrayList<AcceptanceletterTable>();
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            List result = managedelegate.viewDataAcceptance(Integer.parseInt(compCode));
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] data = (Object[]) itr.next();
                AcceptanceletterTable grid = new AcceptanceletterTable();
                grid.setEmpid(data[0] != null ? data[0].toString() : "");
                grid.setEmpname(data[1] != null ? data[1].toString() : "");
                grid.setEmptype(data[2] != null ? data[2].toString() : "");
                grid.setDeptcode(data[3] != null ? data[3].toString() : "");
                grid.setDeptcodedesc(data[4] != null ? data[4].toString() : "");
                grid.setDesgcode(data[5] != null ? data[5].toString() : "");
                grid.setDesgcodedesc(data[6] != null ? data[6].toString() : "");
                grid.setGradecode(data[7] != null ? data[7].toString() : "");
                grid.setGradecodedesc(data[8] != null ? data[8].toString() : "");
                grid.setJoindate(data[9] != null ? sdf.parse(data[12].toString()) : null);
                grid.setNoticeperiod(data[10] != null ? data[10].toString() : "");
                grid.setSepacode(data[11] != null ? data[11].toString() : "");
                grid.setResignation(data[12] != null ? data[12].toString() : "");
                grid.setReason(data[13] != null ? data[13].toString() : "");
                grid.setResigaccept(data[14] != null ? data[14].toString() : "");
                grid.setSepration(data[15] != null ? data[15].toString() : "");
                datagrid.add(grid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectData() throws Exception {
        setMsg("");
        save = false;
        delete = false;
        this.setEmpid(selectdata.getEmpid());
        this.setEmpname(selectdata.getEmpname());
        this.setEmptype(selectdata.getEmptype());
        this.setDeptcode(selectdata.getDeptcode());
        this.setDeptcodedesc(selectdata.getDeptcodedesc());
        this.setDesgcode(selectdata.getDesgcode());
        this.setDesgcodedesc(selectdata.getDesgcodedesc());
        this.setGradecode(selectdata.getGradecode());
        this.setGradecodedesc(selectdata.getGradecodedesc());
        this.setJoindate(selectdata.getJoindate());
        this.setNoticeperiod(selectdata.getNoticeperiod());
        this.setSepacode(selectdata.getSepacode());
        this.setResignation(sdf.parse(selectdata.getResignation()));
        this.setReason(selectdata.getReason());
        this.setResigaccept(sdf.parse(selectdata.getResigaccept()));
        this.setSepration(sdf.parse(selectdata.getSepration()));
    }

    public void saveData() {
        msg = validation();
        if (!msg.equalsIgnoreCase("Ok")) {
            return;
        }
        HrSeparationDetailsTO to = new HrSeparationDetailsTO();
        HrSeparationDetailsPKTO pkto = new HrSeparationDetailsPKTO();
        try {
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            pkto.setCompCode(Integer.parseInt(compCode));
            to.setAuthBy(getUserName());
            to.setEnteredBy(getUserName());
            to.setDefaultComp(defaultComp + "");
            to.setModDate(new java.util.Date());
            to.setReason(this.getReason());
            to.setSepaCode(this.getSepacode());
            to.setStatUpFlag("Y");
            to.setStatFlag("N");
            to.setSeparation(sdf.format(getSepration()));
            to.setResigAccept(sdf.format(getResigaccept()));
            to.setResignation(sdf.format(getResignation()));
            to.setHrSeparationDetailsPK(pkto);
            msg = managedelegate.performoperationAcceptLetterDelegate(Integer.parseInt(compCode), this.getEmpid(), flag, to);

        } catch (WebException e) {
            logger.error("Exception occured while executing method saveData()", e);
            this.setMsg("You may be trying to save duplicate entry!!");
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveData()", e1);
            this.setMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveData()", e);
            this.setMsg("You may be trying to save duplicate entry!!");
        }
        refreshData();
    }

//    public void updateData() {
//        msg = validation();
//        if (!msg.equalsIgnoreCase("ok")) {
//            return;
//        }
//        HrSeparationDetailsTO to = new HrSeparationDetailsTO();
//        HrSeparationDetailsPKTO pkto = new HrSeparationDetailsPKTO();
//
//        try {
//            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
//            pkto.setCompCode(Integer.parseInt(compCode));
//            to.setAuthBy(getUserName());
//            to.setEnteredBy(getUserName());
//            to.setDefaultComp(defaultComp + "");
//            to.setModDate(new java.util.Date());
//            to.setReason(this.getReason());
//            to.setSepaCode(this.getSepacode());
//            to.setStatUpFlag("Y");
//            to.setStatFlag("N");
//            to.setSeparation(sdf.format(getSepration()));
//            to.setResigAccept(sdf.format(getResigaccept()));
//            to.setResignation(sdf.format(getResignation()));
//            to.setHrSeparationDetailsPK(pkto);
//            msg = managedelegate.performoperationAcceptLetterDelegate(Integer.parseInt(compCode), this.getEmpid(), "EDIT", to);
//
//        } catch (WebException e) {
//            logger.error("Exception occured while executing method updateData()", e);
//            this.setMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
//        } catch (ServiceLocatorException e1) {
//            logger.error("Exception occured while executing method updateData()", e1);
//            this.setMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//        } catch (Exception e) {
//            logger.error("Exception occured while executing method updateData()", e);
//            this.setMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//        }
//        refreshData();
//    }

    public void deleteData() {
        msg = validation();
        if (!msg.equalsIgnoreCase("ok")) {
            return;
        }
        HrSeparationDetailsTO to = new HrSeparationDetailsTO();
        HrSeparationDetailsPKTO pkto = new HrSeparationDetailsPKTO();
        try {
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            pkto.setCompCode(Integer.parseInt(compCode));
            to.setSepaCode(this.getSepacode());
            to.setResignation(this.getResignation().toString());
            to.setReason(this.getReason());
            to.setResigAccept(this.getResigaccept().toString());
            to.setSeparation(this.getSepration().toString());
            to.setHrSeparationDetailsPK(pkto);
            msg = managedelegate.performoperationAcceptLetterDelegate(Integer.parseInt(compCode), this.getEmpid(), "DELETE", to);
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteData()", e);
            this.setMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method deleteData()", e1);
            this.setMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteData()", e);
            this.setMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        refreshData();
    }

    public void refreshData() {
        setOperation("0");
        save = true;
        delete = true;
        this.setEmpid("");
        this.setEmpname("");
        this.setEmptype("");
        this.setDeptcode("");
        this.setDeptcodedesc("");
        this.setDesgcode("");
        this.setDesgcodedesc("");
        this.setGradecode("");
        this.setGradecodedesc("");
        this.setJoindate(null);
        this.setNoticeperiod("");
        this.setSepacode("");
        this.setResignation(null);
        this.setReason("");
        this.setResigaccept(null);
        this.setSepration(null);
        initData();
    }

    public void refreshbutton() {
        save = true;
        delete = true;
        this.setMsg("");
        this.setEmpid("");
        this.setEmpname("");
        this.setEmptype("");
        this.setDeptcode("");
        this.setDeptcodedesc("");
        this.setDesgcode("");
        this.setDesgcodedesc("");
        this.setGradecode("");
        this.setGradecodedesc("");
        this.setJoindate(null);
        this.setNoticeperiod("");
        this.setSepacode("");
        this.setResignation(null);
        this.setReason("");
        this.setResigaccept(null);
        this.setSepration(null);
        initData();
    }

    public String btnExit() {
        return "case1";
    }
}
