/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.personnel;

import com.hrms.web.pojo.EmpSearchTable;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrExitInterviewDtPKTO;
import com.hrms.common.to.HrExitInterviewDtTO;
import com.hrms.common.to.HrExitInterviewHdPKTO;
import com.hrms.common.to.HrExitInterviewHdTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsPKTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.PersonnelMasterManagementDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.web.utils.WebUtil;

/**
 *
 * @author admin
 */
public class Exitinterview extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private String satisfact;
    private String dissatisfact;
    private String reason;
    private String empName;
    private String empCode;
    private String empId;
    private String block;
    private String desig;
    private String dept;
    private String errMsg;
    private boolean flagview;
    private boolean save;
    private boolean reasonflag;
    private Date join;
    private Date leave;
    private String compCode = "0";
    private List<SelectItem> reasonList;
    private List<EmpSearchTable> datagridemp;
    private EmpSearchTable currentempdata;
    private int defaultComp;
    private String operation;
    private List<SelectItem> operationList;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String searchflag;
    private String searchtext;
    private String flag;
    private static final Logger logger = Logger.getLogger(Exitinterview.class);
  

    /** Creates a new instance of Exitinterview */
    public Exitinterview() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            initData();
            setTodayDate(sdf.format(new java.util.Date()));
            save = true;
            setOperation("0");
        } catch (Exception e) {
            logger.error("Exception occured while executing method Consultant()", e);
            logger.error("Consultant()", e);
        }
    }

    public boolean isReasonflag() {
        return reasonflag;
    }

    public void setReasonflag(boolean reasonflag) {
        this.reasonflag = reasonflag;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getDesig() { 
        return desig;
    }

    public void setDesig(String desig) {
        this.desig = desig;
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

    public Date getJoin() {
        return join;
    }

    public void setJoin(Date join) {
        this.join = join;
    }

    public Date getLeave() {
        return leave;
    }

    public void setLeave(Date leave) {
        this.leave = leave;
    }

    public EmpSearchTable getCurrentempdata() {
        return currentempdata;
    }

    public void setCurrentempdata(EmpSearchTable currentempdata) {
        this.currentempdata = currentempdata;
    }

    public List<EmpSearchTable> getDatagridemp() {
        return datagridemp;
    }

    public void setDatagridemp(List<EmpSearchTable> datagridemp) {
        this.datagridemp = datagridemp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<SelectItem> getReasonList() {
        return reasonList;
    }

    public void setReasonList(List<SelectItem> reasonList) {
        this.reasonList = reasonList;
    }

    public String getDissatisfact() {
        return dissatisfact;
    }

    public void setDissatisfact(String dissatisfact) {
        this.dissatisfact = dissatisfact;
    }

    public String getSatisfact() {
        return satisfact;
    }

    public void setSatisfact(String satisfact) {
        this.satisfact = satisfact;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public void initData() {
        save=true;
        reasonList = new ArrayList<SelectItem>();
        operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","-Select-"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","Edit"));
        try {
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            List<HrMstStructTO> reasondata = managedelegate.getInitialData(Integer.parseInt(compCode), "RES%");
            reasonList.add(new SelectItem("Select", "Select"));
            for (HrMstStructTO to : reasondata) {
                reasonList.add(new SelectItem(to.getHrMstStructPKTO().getStructCode(), to.getDescription()));
            }
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
     public void setOperationOnBlur()
    {
        if(operation.equalsIgnoreCase("0"))
        {
            setErrMsg("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
           flag="ADD";
           setErrMsg("");
         } else if (operation.equalsIgnoreCase("2")) {
            flag="EDIT";
            setErrMsg("");
        
        }
    }
     
    public void findEmpMethod() {
        
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
            this.setErrMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method initData()", e1);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }
    public String checkvalidation() {
        if (empName == null || empId == null || block == null || dept == null || desig == null || join == null || leave == null || satisfact == null || dissatisfact == null || reason == null) {
            return "Select Data";

        }
        if (reason.equalsIgnoreCase("Select")) {
            return "Please Select Reason For Resignation";
        }
        if ((satisfact.equalsIgnoreCase("a") && dissatisfact.equalsIgnoreCase("e") || (satisfact.equalsIgnoreCase("b") && dissatisfact.equalsIgnoreCase("d")) || (satisfact.equalsIgnoreCase("c") && dissatisfact.equalsIgnoreCase("c")) || (satisfact.equalsIgnoreCase("d") && dissatisfact.equalsIgnoreCase("b")) || (satisfact.equalsIgnoreCase("e") && dissatisfact.equalsIgnoreCase("a")))) {
            return "Ok";
        } else {
            return "Sum of satisfaction and dissatisfaction % should be 100% !";
        }
    }

    public void viewSearchAdd() {
        datagridemp = new ArrayList<EmpSearchTable>();
        save=false;
        try {
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = managedelegate.addviewDatafacade(flag, Integer.parseInt(compCode));

            HashSet hashSet = new HashSet(hrPersonnelDetailsTOs);
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs1 = new ArrayList<HrPersonnelDetailsTO>(hashSet);
            for (HrPersonnelDetailsTO to : hrPersonnelDetailsTOs1) {
                EmpSearchTable grid = new EmpSearchTable();
                grid.setEmpnameD(to.getEmpName());
                grid.setEmpaddD(to.getEmpContAdd());
                grid.setEmpphD(to.getEmpContTel());
                grid.setEmpidD(to.getEmpId());
                grid.setEmpCode(to.getHrPersonnelDetailsPKTO().getEmpCode() + "");
                datagridemp.add(grid);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method viewSearchAdd()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method viewSearchAdd()", e1);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method viewSearchAdd()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

//    public void viewSearchEdit() {
//
//        flagview = true;
//        datagridemp = new ArrayList<EmpSearchTable>();
//        save=false;
//        try {
//            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
//            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = managedelegate.addviewDatafacade("VIEW", Integer.parseInt(compCode));
//            System.out.println("IN View search edit"+hrPersonnelDetailsTOs.isEmpty());
//            EmpSearchTable grid = new EmpSearchTable();
//            for (HrPersonnelDetailsTO to : hrPersonnelDetailsTOs) {
//                grid.setEmpnameD(to.getEmpName());
//                grid.setEmpaddD(to.getEmpContAdd());
//                grid.setEmpphD(to.getEmpContTel());
//                grid.setEmpidD(to.getEmpId());
//                grid.setEmpCode(to.getHrPersonnelDetailsPKTO().getEmpCode() + "");
//                datagridemp.add(grid);
//            }
//        } catch (WebException e) {
//            logger.error("Exception occured while executing method viewSearchEdit()", e);
//            this.setErrMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
//        } catch (ServiceLocatorException e1) {
//            logger.error("Exception occured while executing method viewSearchEdit()", e1);
//            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//        } catch (Exception e) {
//            logger.error("Exception occured while executing method viewSearchEdit()", e);
//            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//        }
//    }

    public void selectDataEmpMethodview() { 
        datagridemp = new ArrayList<EmpSearchTable>();
        try {
            PersonnelMasterManagementDelegate delegateobj = new PersonnelMasterManagementDelegate();
            List result = delegateobj.empDataSetelementDelegate(Integer.parseInt(compCode), currentempdata.getEmpidD());
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] data = (Object[]) itr.next();
                setEmpCode(data[0] != null ? data[0].toString() : "");
                setEmpId(data[1] != null ? data[1].toString() : "");
                setEmpName(data[2] != null ? data[2].toString() : "");
                setBlock(data[4] != null ? data[4].toString() : "");
                setDept(data[6] != null ? data[6].toString() : "");
                setDesig(data[8] != null ? data[8].toString() : "");
                setJoin(data[11] != null ? (Date) data[11] : null);
                setLeave(data[12] != null ? sdf.parse(data[12].toString()) : null);
            }
            if (flagview == true) {
                save = false;
                reasonflag = true;
                List resultview = delegateobj.viewDataExitInterviewDelegate(Integer.parseInt(compCode), Long.parseLong(empCode));
                Iterator itr1 = resultview.iterator();
                while (itr1.hasNext()) {
                    Object[] data = (Object[]) itr1.next();
                    setReason(data[0] != null ? data[0].toString() : "");
                    setSatisfact(data[1] != null ? data[1].toString() : "");
                    setDissatisfact(data[2] != null ? data[2].toString() : "");
                }
            } else {
                reasonflag = false;
                save = false;
               }
        } catch (WebException e) {
            logger.error("Exception occured while executing method selectDataEmpMethodview()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method selectDataEmpMethodview()", e1);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDataEmpMethodview()", e);
            this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveData() {
        errMsg = checkvalidation();
        if (errMsg.equalsIgnoreCase("Ok")) {
            try {
                PersonnelMasterManagementDelegate delegateobj = new PersonnelMasterManagementDelegate();
                HrExitInterviewHdTO exitInterviewHdTO = new HrExitInterviewHdTO();
                HrExitInterviewHdPKTO exitInterviewHdPKTO = new HrExitInterviewHdPKTO();
                exitInterviewHdPKTO.setCompCode(Integer.parseInt(compCode));
                exitInterviewHdPKTO.setEmpCode(Long.parseLong(empCode));
                exitInterviewHdTO.setSatisAsses(satisfact.charAt(0));
                exitInterviewHdTO.setDisatisAsses(dissatisfact.charAt(0));
                exitInterviewHdTO.setDefaultComp(defaultComp);
                exitInterviewHdTO.setAuthBy(getUserName());
                exitInterviewHdTO.setEnteredBy(getUserName());
                exitInterviewHdTO.setStatFlag("Y");
                exitInterviewHdTO.setStatUpFlag("N");
                exitInterviewHdTO.setModDate(new java.util.Date());
                exitInterviewHdTO.setHrExitInterviewHdPK(exitInterviewHdPKTO);
                HrPersonnelDetailsTO hrPersonnelDetailsTO = new HrPersonnelDetailsTO();
                HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO = new HrPersonnelDetailsPKTO();
                hrPersonnelDetailsPKTO.setCompCode(Integer.parseInt(compCode));
                hrPersonnelDetailsPKTO.setEmpCode(Long.parseLong(empCode));
                hrPersonnelDetailsTO.setEmpStatus('N');
                hrPersonnelDetailsTO.setHrPersonnelDetailsPKTO(hrPersonnelDetailsPKTO);
                HrExitInterviewDtTO hrExitInterviewDtTO = new HrExitInterviewDtTO();
                HrExitInterviewDtPKTO hrExitInterviewDtPKTO = new HrExitInterviewDtPKTO();
                hrExitInterviewDtPKTO.setCompCode(Integer.parseInt(compCode));
                hrExitInterviewDtPKTO.setEmpCode(Long.parseLong(empCode));
                hrExitInterviewDtPKTO.setReasonCode(reason);
                hrExitInterviewDtTO.setDefaultComp(defaultComp);
                hrExitInterviewDtTO.setModDate(new java.util.Date());
                hrExitInterviewDtTO.setAuthBy(getUserName());
                hrExitInterviewDtTO.setEnteredBy(getUserName());
                hrExitInterviewDtTO.setStatFlag("Y");
                hrExitInterviewDtTO.setStatUpFlag("N");
                hrExitInterviewDtTO.setHrExitInterviewDtPK(hrExitInterviewDtPKTO);
                errMsg = delegateobj.performOperationExitInterview(flag, hrPersonnelDetailsTO, hrExitInterviewDtTO, exitInterviewHdTO);
                clear();
            } catch (WebException e) {
                logger.error("Exception occured while executing method saveData()", e);
                this.setErrMsg("You may be trying to save duplicate entry!!");
            } catch (ServiceLocatorException e1) {
                logger.error("Exception occured while executing method saveData()", e1);
                this.setErrMsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
            } catch (Exception e) {
                logger.error("Exception occured while executing method saveData()", e);
                this.setErrMsg("You may be trying to save duplicate entry!!");
            }
        } else { 
            return;
        }
    }

    public void refreshData() {
        save = true;
        reasonflag = false;
        setEmpId("");
        setEmpName("");
        setBlock("");
        setDept("");
        setDesig("");
        setJoin(null);
        setLeave(null);
        setReason("");
        setSatisfact("");
        setDissatisfact("");
        setOperation("0");
        setErrMsg("");
    }
    public void clear(){
     setEmpId("");
        setEmpName("");
        setBlock("");
        setDept("");
        setDesig("");
        setJoin(null);
        setLeave(null);
        setReason("");
        setSatisfact("");
        setDissatisfact("");
        setOperation("0");
    }
    public String btnExit() {
        return "case1";
    }
}
