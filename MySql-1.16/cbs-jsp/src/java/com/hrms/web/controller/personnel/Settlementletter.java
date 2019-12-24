/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.personnel;

import com.hrms.web.pojo.Settlementlettertable;
import com.hrms.web.pojo.EmpSearchTable;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.ClearSlipDetPKTO;
import com.hrms.common.to.ClearSlipDetTO;
import com.hrms.common.to.ClearSlipHdPKTO;
import com.hrms.common.to.ClearSlipHdTO;
import com.hrms.common.to.HrMstStructTO;
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
import org.apache.log4j.Logger;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.web.utils.WebUtil;

/**
 *
 * @author admin
 */
public class Settlementletter extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(Settlementletter.class);
    private String empName;
    private String empId;
    private String empCode;
    private String block;
    private String blockCode;
    private String dept;
    private String deptCode;
    private String desg;
    private String desgCode;
    private String grade;
    private String gradeCode;
    private Date joining;
    private Date leaveing;
    private Date settelement;
    private String amt;
    private String totamt;
    private String bankname;
    private String ddcheqno;
    private Date ddchequedt;
    private String ddchqamt;
    private String departmentas;
    private String dueamt;
    private String recovery;
    private String netamt;
    private String comments;
    private String errmsg;
    private boolean delete;
    private boolean save;
    private String deptDet;
    private String flag;
    private String compCode = "0";
    private boolean deptbtn;
    private List<SelectItem> banklist;
    private List<SelectItem> deptlist;
    private List<Settlementlettertable> griddata = new ArrayList<Settlementlettertable>();
    private List<EmpSearchTable> datagridemp = new ArrayList<EmpSearchTable>();
    private Settlementlettertable currentdata;
    private EmpSearchTable currentdataemp;
    private int defaultComp;
    private String operation;
    private List<SelectItem> operationList;

    /** Creates a new instance of Settlementletter */
    public Settlementletter() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            initData();
            viewData();
            delete = true;
            save = true;
            deptbtn = false;
        } catch (Exception e) {
            logger.error("Exception occured while executing method Consultant()", e);
            logger.error("Consultant()", e);
        }
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public boolean isDeptbtn() {
        return deptbtn;
    }

    public void setDeptbtn(boolean deptbtn) {
        this.deptbtn = deptbtn;
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

    public String getDeptDet() {
        return deptDet;
    }

    public void setDeptDet(String deptDet) {
        this.deptDet = deptDet;
    }

    public List<SelectItem> getDeptlist() {
        return deptlist;
    }

    public void setDeptlist(List<SelectItem> deptlist) {
        this.deptlist = deptlist;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public List<SelectItem> getBanklist() {
        return banklist;
    }

    public void setBanklist(List<SelectItem> banklist) {
        this.banklist = banklist;
    }

    public EmpSearchTable getCurrentdataemp() {
        return currentdataemp;
    }

    public void setCurrentdataemp(EmpSearchTable currentdataemp) {
        this.currentdataemp = currentdataemp;
    }

    public List<EmpSearchTable> getDatagridemp() {
        return datagridemp;
    }

    public void setDatagridemp(List<EmpSearchTable> datagridemp) {
        this.datagridemp = datagridemp;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Settlementlettertable getCurrentdata() {
        return currentdata;
    }

    public void setCurrentdata(Settlementlettertable currentdata) {
        this.currentdata = currentdata;
    }

    public List<Settlementlettertable> getGriddata() {
        return griddata;
    }

    public void setGriddata(List<Settlementlettertable> griddata) {
        this.griddata = griddata;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getDdcheqno() {
        return ddcheqno;
    }

    public void setDdcheqno(String ddcheqno) {
        this.ddcheqno = ddcheqno;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getDdchequedt() {
        return ddchequedt;
    }

    public void setDdchequedt(Date ddchequedt) {
        this.ddchequedt = ddchequedt;
    }

    public String getDdchqamt() {
        return ddchqamt;
    }

    public void setDdchqamt(String ddchqamt) {
        this.ddchqamt = ddchqamt;
    }

    public String getTotamt() {
        return totamt;
    }

    public void setTotamt(String totamt) {
        this.totamt = totamt;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getDepartmentas() {
        return departmentas;
    }

    public void setDepartmentas(String departmentas) {
        this.departmentas = departmentas;
    }

    public String getDesg() {
        return desg;
    }

    public void setDesg(String desg) {
        this.desg = desg;
    }

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public String getDueamt() {
        return dueamt;
    }

    public void setDueamt(String dueamt) {
        this.dueamt = dueamt;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Date getJoining() {
        return joining;
    }

    public void setJoining(Date joining) {
        this.joining = joining;
    }

    public Date getLeaveing() {
        return leaveing;
    }

    public void setLeaveing(Date leaveing) {
        this.leaveing = leaveing;
    }

    public String getNetamt() {
        return netamt;
    }

    public void setNetamt(String netamt) {
        this.netamt = netamt;
    }

    public String getRecovery() {
        return recovery;
    }

    public void setRecovery(String recovery) {
        this.recovery = recovery;
    }

    public Date getSettelement() {
        return settelement;
    }

    public void setSettelement(Date settelement) {
        this.settelement = settelement;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public void initData() {
        setOperation("0");
        banklist = new ArrayList<SelectItem>();
        deptlist = new ArrayList<SelectItem>();
        try {
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","-Select-"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","Edit"));
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            List<HrMstStructTO> bankdata = managedelegate.getInitialData(Integer.parseInt(compCode), "BAN%");
            List<HrMstStructTO> deptdata = managedelegate.getInitialData(Integer.parseInt(compCode), "DEP%");
            banklist.add(new SelectItem("Select", "Select"));
            deptlist.add(new SelectItem("Select", "Select"));
            for (HrMstStructTO to : bankdata) {
                banklist.add(new SelectItem(to.getHrMstStructPKTO().getStructCode(), to.getDescription()));
            }
            for (HrMstStructTO to : deptdata) {
                deptlist.add(new SelectItem(to.getHrMstStructPKTO().getStructCode(), to.getDescription()));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setErrmsg("You are trying to insert duplicate entry");
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method initData()", e1);
            this.setErrmsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setErrmsg("You are trying to insert duplicate entry");
        }
    }
      public void setOperationOnBlur()
    {
        if(operation.equalsIgnoreCase("0"))
        {
            setErrmsg("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
            setErrmsg("");
            addDataButton();
            setFlag("ADD");
            save=false;
            delete=true;
           
         } else if (operation.equalsIgnoreCase("2")) {
            setFlag("EDIT");
            setErrmsg("");
            save=false;
            delete=false;
         
        
        }
    }
    public void viewData() {
        try {
            PersonnelMasterManagementDelegate delegateobj = new PersonnelMasterManagementDelegate();
            List result = delegateobj.viewDataSettlement(Integer.parseInt(compCode));
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] data = (Object[]) itr.next();
                Settlementlettertable grid = new Settlementlettertable();
                grid.setEmpCode(data[0] != null ? data[0].toString() : "");
                grid.setEmpId(data[1] != null ? data[1].toString() : "");
                grid.setJoinDate(data[2] != null ? (Date) data[2] : null);
                grid.setSettlementDt(data[3] != null ? sdf.parse(data[3].toString()) : null);
                grid.setTotAmt(data[4] != null ? data[4].toString() : "");
                grid.setDdChequeNu(data[5] != null ? data[5].toString() : "");
                grid.setDdCheque(data[6] != null ? data[6].toString() : "");
                grid.setDdAmt(data[7] != null ? data[7].toString() : "");
                grid.setBankName(data[8] != null ? data[8].toString() : "");
                grid.setEmpName(data[9] != null ? data[9].toString() : "");
                grid.setDeptCode(data[10] != null ? data[10].toString() : "");
                grid.setDescription(data[11] != null ? data[11].toString() : "");
                grid.setDesgCode(data[12] != null ? data[12].toString() : "");
                grid.setDescriptiondesgcode(data[13] != null ? data[13].toString() : "");
                grid.setGradeCode(data[14] != null ? data[14].toString() : "");
                grid.setDescriptiongradecode(data[15] != null ? data[15].toString() : "");
                grid.setBlock(data[16] != null ? data[16].toString() : "");
                grid.setDescriptionblock(data[17] != null ? data[17].toString() : "");
                grid.setSeparation(data[18] != null ? sdf.parse(data[18].toString()) : null);
                grid.setDueAmt(data[19] != null ? data[19].toString() : "");
                grid.setRecoverableAmt(data[20] != null ? data[20].toString() : "");
                grid.setNetAmt(data[21] != null ? data[21].toString() : "");
                grid.setRemarks(data[22] != null ? data[22].toString() : "");
                grid.setDeptDet(data[23] != null ? data[23].toString() : "");
                griddata.add(grid);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setErrmsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method initData()", e1);
            this.setErrmsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setErrmsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void addDataButton() {
        datagridemp= new ArrayList<EmpSearchTable>();
        try {
            PersonnelMasterManagementDelegate delegateobj = new PersonnelMasterManagementDelegate();
            List result = delegateobj.viewSettelementSearch(Integer.parseInt(compCode));
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] data = (Object[]) itr.next();
                EmpSearchTable grid = new EmpSearchTable();
                grid.setEmpCode(data[0] != null ? data[0].toString() : "");
                grid.setEmpidD(data[2] != null ? data[2].toString() : "");
                grid.setEmpnameD(data[1] != null ? data[1].toString() : "");
                datagridemp.add(grid);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method addDataButton()", e);
            this.setErrmsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method addDataButton()", e1);
            this.setErrmsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method addDataButton()", e);
            this.setErrmsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectDataEmpMethod() {
        delete = true;
        save = false;
         List result = new ArrayList();
        try {
            PersonnelMasterManagementDelegate delegateobj = new PersonnelMasterManagementDelegate();
             result = delegateobj.empDataSetelementDelegate(Integer.parseInt(compCode), currentdataemp.getEmpidD());
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] data = (Object[]) itr.next();
                setEmpCode(data[0] != null ? data[0].toString() : "");
                setEmpId(data[1] != null ? data[1].toString() : "");
                setEmpName(data[2] != null ? data[2].toString() : "");
                setBlockCode(data[3] != null ? data[3].toString() : "");
                setBlock(data[4] != null ? data[4].toString() : "");
                setDeptCode(data[5] != null ? data[5].toString() : "");
                setDept(data[6] != null ? data[6].toString() : "");
                setDesgCode(data[7] != null ? data[7].toString() : "");
                setDesg(data[8] != null ? data[8].toString() : "");
                setGradeCode(data[9] != null ? data[9].toString() : "");
                setGrade(data[10] != null ? data[10].toString() : "");
                setJoining(data[11] != null ? (Date) data[11] : null);
                setLeaveing(data[12] != null ? sdf.parse(data[12].toString()) : null);

            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method selectDataEmpMethod()", e);
            this.setErrmsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method selectDataEmpMethod()", e1);
            this.setErrmsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method selectDataEmpMethod()", e);
            this.setErrmsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectDataCurrent() throws Exception {
       delete = false;
        save = false;
        deptbtn = true;
        setEmpCode(currentdata.getEmpCode());
        setEmpId(currentdata.getEmpId());
        setEmpName(currentdata.getEmpName());
        setJoining(currentdata.getJoinDate());
        setSettelement(currentdata.getSettlementDt());
        setTotamt(currentdata.getTotAmt());
        setDdchequedt(sdf.parse(currentdata.getDdChequeNu()));
        setDdcheqno(currentdata.getDdCheque());
        setDdchqamt(currentdata.getDdAmt());
        setBankname(currentdata.getBankName());
        setDeptCode(currentdata.getDeptCode());
        setDept(currentdata.getDescription());
        setDesgCode(currentdata.getDesgCode());
        setDesg(currentdata.getDescriptiondesgcode());
        setGradeCode(currentdata.getGradeCode());
        setGrade(currentdata.getDescriptiongradecode());
        setBlockCode(currentdata.getBlock());
        setBlock(currentdata.getDescriptionblock());
        setLeaveing(currentdata.getSeparation());
        setDueamt(currentdata.getDueAmt());
        setRecovery(currentdata.getRecoverableAmt());
        setNetamt(currentdata.getNetAmt());
        setComments(currentdata.getRemarks());
        setDeptDet(currentdata.getDeptDet());
    }

    public void saveData() {
        errmsg = validation();
        if (errmsg.equalsIgnoreCase("Ok")) {
            ClearSlipHdTO clearSlipHdTO = new ClearSlipHdTO();
            ClearSlipHdPKTO clearSlipHdPKTO = new ClearSlipHdPKTO();
            clearSlipHdPKTO.setCompCode(Integer.parseInt(compCode));
            clearSlipHdPKTO.setEmpCode(Long.parseLong(getEmpCode()));
            clearSlipHdTO.setSettlementDt(sdf.format(getSettelement()));
            clearSlipHdTO.setTotAmt(Double.parseDouble(getTotamt()));
            clearSlipHdTO.setDdChequeNu(sdf.format(ddchequedt));
            clearSlipHdTO.setDdCheque(getDdcheqno());
            clearSlipHdTO.setDdAmt(Double.parseDouble(getDdchqamt()));
            clearSlipHdTO.setBankName(getBankname());
            clearSlipHdTO.setDefaultComp(defaultComp);
            clearSlipHdTO.setStatFlag("Y");
            clearSlipHdTO.setStatUpFlag("Y");
            clearSlipHdTO.setModDate(new java.util.Date());
            clearSlipHdTO.setAuthBy(getUserName());
            clearSlipHdTO.setEnteredBy(getUserName());
            clearSlipHdTO.setClearSlipHdPK(clearSlipHdPKTO);
            ClearSlipDetTO clearSlipDetTO = new ClearSlipDetTO();
            ClearSlipDetPKTO clearSlipDetPKTO = new ClearSlipDetPKTO();
            clearSlipDetPKTO.setCompCode(Integer.parseInt(compCode));
            clearSlipDetPKTO.setEmpCode(Long.parseLong(getEmpCode()));
            clearSlipDetPKTO.setDeptCode(getDeptDet());
            clearSlipDetTO.setDueAmt(Double.parseDouble(getDueamt()));
            clearSlipDetTO.setRecoverableAmt(Double.parseDouble(getRecovery()));
            clearSlipDetTO.setNetAmt(Double.parseDouble(getNetamt()));
            clearSlipDetTO.setRemarks(getComments());
            clearSlipDetTO.setDefaultComp(defaultComp);
            clearSlipDetTO.setStatFlag("Y");
            clearSlipDetTO.setStatUpFlag("Y");
            clearSlipDetTO.setModDate(new java.util.Date());
            clearSlipDetTO.setClearSlipDetPK(clearSlipDetPKTO);
            try {
                PersonnelMasterManagementDelegate delegateobj = new PersonnelMasterManagementDelegate();
                errmsg = delegateobj.performOprationSettelement(flag, clearSlipHdTO, clearSlipDetTO);
            } catch (WebException e) {
                logger.error("Exception occured while executing method initData()", e);
                this.setErrmsg("You may be trying to save duplicate entry!!");
            } catch (ServiceLocatorException e1) {
                logger.error("Exception occured while executing method initData()", e1);
                this.setErrmsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
            } catch (Exception e) {
                logger.error("Exception occured while executing method initData()", e);
                this.setErrmsg("You may be trying to save duplicate entry!!");
            }
            refresh();
        }
    }

//    public void editData() {
//        errmsg = validation();
//        if (errmsg.equalsIgnoreCase("Ok")) {
//            flag = "EDIT";
//            ClearSlipHdTO clearSlipHdTO = new ClearSlipHdTO();
//            ClearSlipHdPKTO clearSlipHdPKTO = new ClearSlipHdPKTO();
//            clearSlipHdPKTO.setCompCode(Integer.parseInt(compCode));
//            clearSlipHdPKTO.setEmpCode(Long.parseLong(getEmpCode()));
//            clearSlipHdTO.setSettlementDt(sdf.format(getSettelement()));
//            clearSlipHdTO.setTotAmt(Double.parseDouble(getTotamt()));
//            clearSlipHdTO.setDdChequeNu(sdf.format(ddchequedt));
//            clearSlipHdTO.setDdCheque(getDdcheqno());
//            clearSlipHdTO.setDdAmt(Double.parseDouble(getDdchqamt()));
//            clearSlipHdTO.setBankName(getBankname());
//            clearSlipHdTO.setStatFlag("Y");
//            clearSlipHdTO.setStatUpFlag("Y");
//            clearSlipHdTO.setModDate(new java.util.Date());
//            clearSlipHdTO.setAuthBy(getUserName());
//            clearSlipHdTO.setEnteredBy(getUserName());
//            clearSlipHdTO.setClearSlipHdPK(clearSlipHdPKTO);
//            ClearSlipDetTO clearSlipDetTO = new ClearSlipDetTO();
//            ClearSlipDetPKTO clearSlipDetPKTO = new ClearSlipDetPKTO();
//            clearSlipDetPKTO.setCompCode(Integer.parseInt(compCode));
//            clearSlipDetPKTO.setEmpCode(Long.parseLong(getEmpCode()));
//            clearSlipDetPKTO.setDeptCode(getDeptDet());
//            clearSlipDetTO.setDueAmt(Double.parseDouble(getDueamt()));
//            clearSlipDetTO.setRecoverableAmt(Double.parseDouble(getRecovery()));
//            clearSlipDetTO.setNetAmt(Double.parseDouble(getNetamt()));
//            clearSlipDetTO.setRemarks(getComments());
//            clearSlipDetTO.setDefaultComp(defaultComp);
//            clearSlipDetTO.setStatFlag("Y");
//            clearSlipDetTO.setStatUpFlag("Y");
//            clearSlipDetTO.setModDate(new java.util.Date());
//            clearSlipDetTO.setClearSlipDetPK(clearSlipDetPKTO);
//            try {
//                PersonnelMasterManagementDelegate delegateobj = new PersonnelMasterManagementDelegate();
//                errmsg = delegateobj.performOprationSettelement(flag, clearSlipHdTO, clearSlipDetTO);
//            } catch (WebException e) {
//                logger.error("Exception occured while executing method initData()", e);
//                this.setErrmsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
//            } catch (ServiceLocatorException e1) {
//                logger.error("Exception occured while executing method initData()", e1);
//                this.setErrmsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//            } catch (Exception e) {
//                logger.error("Exception occured while executing method initData()", e);
//                this.setErrmsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//            }
//            refresh();
//        }
//    }

    public void deleteData() {
        flag = "DELETE";
        ClearSlipHdTO clearSlipHdTO = new ClearSlipHdTO();
        ClearSlipHdPKTO clearSlipHdPKTO = new ClearSlipHdPKTO();
        clearSlipHdPKTO.setCompCode(Integer.parseInt(compCode));
        clearSlipHdPKTO.setEmpCode(Long.parseLong(getEmpCode()));
        clearSlipHdTO.setClearSlipHdPK(clearSlipHdPKTO);
        ClearSlipDetTO clearSlipDetTO = new ClearSlipDetTO();
        ClearSlipDetPKTO clearSlipDetPKTO = new ClearSlipDetPKTO();
        clearSlipDetPKTO.setCompCode(Integer.parseInt(compCode));
        clearSlipDetPKTO.setEmpCode(Long.parseLong(getEmpCode()));
        clearSlipDetPKTO.setDeptCode(getDeptDet());
        clearSlipDetTO.setClearSlipDetPK(clearSlipDetPKTO);
        try {
            PersonnelMasterManagementDelegate delegateobj = new PersonnelMasterManagementDelegate();
            errmsg = delegateobj.performOprationSettelement(flag, clearSlipHdTO, clearSlipDetTO);
        } catch (WebException e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setErrmsg(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method initData()", e1);
            this.setErrmsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method initData()", e);
            this.setErrmsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        refresh();
    }

    public void refresh() {
        delete = true;
        save = true;
        deptbtn = false;
        setEmpCode("");
        setEmpId("");
        setEmpName("");
        setJoining(null);
        setSettelement(null);
        setTotamt("");
        setDdchequedt(null);
        setDdcheqno("");
        setDdchqamt("");
        setBankname("");
        setDeptCode("");
        setDept("");
        setDesgCode("");
        setDesg("");
        setGradeCode("");
        setGrade("");
        setBlockCode("");
        setBlock("");
        setLeaveing(null);
        setDueamt("");
        setRecovery("");
        setNetamt("");
        setComments("");
        setDeptDet("");
        setOperation("0");
        griddata.clear();
        viewData();
    }

    public void refreshButton() {
        delete = true;
        save = true;
        deptbtn = false;
        errmsg = "";
        setEmpCode("");
        setEmpId("");
        setEmpName("");
        setJoining(null);
        setSettelement(null);
        setTotamt("0");
        setDdchequedt(null);
        setDdcheqno("");
        setDdchqamt("0");
        setBankname("");
        setDeptCode("");
        setDept("");
        setDesgCode("");
        setDesg("");
        setGradeCode("");
        setGrade("");
        setBlockCode("");
        setBlock("");
        setLeaveing(null);
        setDueamt("");
        setRecovery("");
        setNetamt("");
        setComments("");
        setDeptDet("");
        setOperation("0");
        griddata.clear();
        viewData();
    }

    public String validation() {
        if (empCode == null) {
            return "Select Data";
        }
        if (settelement == null) {
            return "Enter Setlement Date";
        }
        if (bankname.equalsIgnoreCase("Select")) {
            return "Enter the Bank Name";
        }
        if (totamt == null) {
            return "Enter Total Amount";
        }
        if (ddchequedt == null) {
            return "Enter the Cheque Date";
        }
        if (ddcheqno == null) {
            return "Enter the Cheque No";
        }

        if (deptCode == null) {
            return "Select Department";
        }
        if (dueamt == null) {
            return "Enter Due Amount";
        }
        if (recovery == null) {
            return "Enter the recovery";
        }
        if (netamt == null) {
            return "Enter the Net Amount";
        }
        if (comments == null) {
            return "Enter the Comments";
        }
        if (deptDet.equalsIgnoreCase("Select")) {
            return "Select Department";
        }
        try {
            Double.parseDouble(totamt);
            Double.parseDouble(netamt);
            Double.parseDouble(recovery);
            Double.parseDouble(dueamt);

        } catch (Exception e) {
            return "Enter the Numeric value in amount";
        }
        return "ok";
    }

    public String btnExit() {
        return "case1";
    }
}
