/*
 * CREATED BY     :    ROHIT KRISHNA GUPTA
 * CREATION DATE  :    04 JULY 2011
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.TrainingExcTable;
import com.hrms.web.pojo.TraninigPlanGridFile;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.to.HrTrainingExecutionPKTO;
import com.hrms.common.to.HrTrainingExecutionTO;
import com.hrms.common.to.HrTrainingPlanPKTO;
import com.hrms.common.to.HrTrainingPlanTO;
import com.hrms.web.delegate.TrainingExecutionDelegate;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.regex.Pattern;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ROHIT KRISHNA
 */
public class TrainingExecution extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(TrainingExecution.class);
    private String errorMessage;
    private String message;
    private String compCode = "0";
    private String empName;
    private String empId;
    private String traineeNeed;
    private String program;
    private String facultyName;
    private String department;
    private String commencingDate;
    private String endingDate;
    private String duration;
    private String actualDuration;
    private String cost;
    private String trainerAssesment;
    private String traineesAssesment;
    private boolean disFlag;
    private boolean saveFlag;
    private boolean editFlag;
    private boolean delFlag;
    private boolean addDisable;
    private List<TraninigPlanGridFile> gridDetail;
    int currentRow;
    private TraninigPlanGridFile currentItem = new TraninigPlanGridFile();
    private List<TrainingExcTable> trnExc;
    int currentRow1;
    private TrainingExcTable currentItem1 = new TrainingExcTable();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Pattern pm = Pattern.compile("[a-zA-z0-9,]+([ '-/][a-zA-Z0-9,]+)*");
    Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
    NumberFormat formatter = new DecimalFormat("#0.00");
    private Integer defaultComp;
     private String operation;
    private List<SelectItem> operationList;

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
    
    public boolean isAddDisable() {
        return addDisable;
    }

    public void setAddDisable(boolean addDisable) {
        this.addDisable = addDisable;
    }

    public TrainingExcTable getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(TrainingExcTable currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public List<TrainingExcTable> getTrnExc() {
        return trnExc;
    }

    public void setTrnExc(List<TrainingExcTable> trnExc) {
        this.trnExc = trnExc;
    }

    public String getCommencingDate() {
        return commencingDate;
    }

    public void setCommencingDate(String commencingDate) {
        this.commencingDate = commencingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public TraninigPlanGridFile getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TraninigPlanGridFile currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<TraninigPlanGridFile> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<TraninigPlanGridFile> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public boolean isEditFlag() {
        return editFlag;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }

    public boolean isSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(boolean saveFlag) {
        this.saveFlag = saveFlag;
    }

    public boolean isDisFlag() {
        return disFlag;
    }

    public void setDisFlag(boolean disFlag) {
        this.disFlag = disFlag;
    }

    public String getActualDuration() {
        return actualDuration;
    }

    public void setActualDuration(String actualDuration) {
        this.actualDuration = actualDuration;
    }

    public String getCompanyCode() {
        return compCode;
    }

    public void setCompanyCode(String compCode) {
        this.compCode = compCode;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getTraineeNeed() {
        return traineeNeed;
    }

    public void setTraineeNeed(String traineeNeed) {
        this.traineeNeed = traineeNeed;
    }

    public String getTraineesAssesment() {
        return traineesAssesment;
    }

    public void setTraineesAssesment(String traineesAssesment) {
        this.traineesAssesment = traineesAssesment;
    }

    public String getTrainerAssesment() {
        return trainerAssesment;
    }

    public void setTrainerAssesment(String trainerAssesment) {
        this.trainerAssesment = trainerAssesment;
    }

    
    /** Creates a new instance of TrainingExecution */
    public TrainingExecution() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","---Select---"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","View"));
            Date date = new Date();
            this.setCommencingDate(sdf.format(date));
            this.setEndingDate(sdf.format(date));
            setTodayDate(sdf.format(date));
            this.setErrorMessage("");
            this.setMessage("");
            saveFlag = true;
            editFlag = true;
            disFlag = true;
            gridEditButton();
        } catch (Exception e) {
            logger.error("Exception occured while executing method TrainingExecution()", e);
            logger.error("TrainingExecution()", e);
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
    /**Variables for code and there Getter and setters**/
    private String empCode;
    private String deptcode;
    private String trainingCode;
    private String progCode;

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(String progCode) {
        this.progCode = progCode;
    }

    public String getTrainingCode() {
        return trainingCode;
    }

    public void setTrainingCode(String trainingCode) {
        this.trainingCode = trainingCode;
    }

    /**End**/
    public void gridLoadSearchBtn() {
        try {
            gridDetail = new ArrayList<TraninigPlanGridFile>();
            List resultLt = new ArrayList();
            TrainingExecutionDelegate trngPlanDel = new TrainingExecutionDelegate();
            resultLt = trngPlanDel.trainingPlanGridLoad(Integer.parseInt(compCode), "N");
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    TraninigPlanGridFile rd = new TraninigPlanGridFile();
                    if (result[0] == null) {
                        rd.setEmpCode("");
                    } else {
                        rd.setEmpCode(result[0].toString());
                    }
                    if (result[1] == null) {
                        rd.setEmpName("");
                    } else {
                        rd.setEmpName(result[1].toString());
                    }
                    if (result[2] == null) {
                        rd.setEmpId("");
                    } else {
                        rd.setEmpId(result[2].toString());
                    }
                    if (result[3] == null) {
                        rd.setTrngCode("");
                    } else {
                        rd.setTrngCode(result[3].toString());
                    }
                    if (result[4] == null) {
                        rd.setTrngCodeDesc("");
                    } else {
                        rd.setTrngCodeDesc(result[4].toString());
                    }
                    if (result[5] == null) {
                        rd.setProgCode("");
                    } else {
                        rd.setProgCode(result[5].toString());
                    }
                    if (result[6] == null) {
                        rd.setProgName("");
                    } else {
                        rd.setProgName(result[6].toString());
                    }
                    if (result[7] == null) {
                        rd.setDateFrom("");
                    } else {
                        rd.setDateFrom(result[7].toString());
                    }
                    if (result[8] == null) {
                        rd.setDateTo("");
                    } else {
                        rd.setDateTo(result[8].toString());
                    }
                    if (result[9] == null) {
                        rd.setTrngDur("");
                    } else {
                        rd.setTrngDur(result[9].toString());
                    }
                    if (result[10] == null) {
                        rd.setTrngExec("");
                    } else {
                        rd.setTrngExec(result[10].toString());
                    }
                    if (result[11] == null) {
                        rd.setApprDetail("");
                    } else {
                        rd.setApprDetail(result[11].toString());
                    }
                    if (result[12] == null) {
                        rd.setDeptCodeDesc("");
                    } else {
                        rd.setDeptCodeDesc(result[12].toString());
                    }
                    if (result[13] == null) {
                        rd.setFacultyName("");
                    } else {
                        rd.setFacultyName(result[13].toString());
                    }
                    if (result[14] == null) {
                        rd.setInextHouse("");
                    } else {
                        rd.setInextHouse(result[14].toString());
                    }
                    gridDetail.add(rd);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method gridLoadSearchBtn()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method gridLoadSearchBtn()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method gridLoadSearchBtn()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void gridEditButton() {
        try {
            trnExc = new ArrayList<TrainingExcTable>();
            List resultLt = new ArrayList();
            TrainingExecutionDelegate trngPlanDel = new TrainingExecutionDelegate();
            resultLt = trngPlanDel.trainingExcEditList(Integer.parseInt(compCode));
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    TrainingExcTable rd = new TrainingExcTable();
                    if (result[0] == null) {
                        rd.setTrngExcCode("");
                    } else {
                        rd.setTrngExcCode(result[0].toString());
                    }
                    if (result[1] == null) {
                        rd.setEmpCode("");
                    } else {
                        rd.setEmpCode(result[1].toString());
                    }
                    if (result[2] == null) {
                        rd.setEmpName("");
                    } else {
                        rd.setEmpName(result[2].toString());
                    }
                    if (result[3] == null) {
                        rd.setEmpId("");
                    } else {
                        rd.setEmpId(result[3].toString());
                    }
                    if (result[4] == null) {
                        rd.setTrngCode("");
                    } else {
                        rd.setTrngCode(result[4].toString());
                    }
                    if (result[5] == null) {
                        rd.setTrngName("");
                    } else {
                        rd.setTrngName(result[5].toString());
                    }
                    if (result[6] == null) {
                        rd.setProgCode("");
                    } else {
                        rd.setProgCode(result[6].toString());
                    }
                    if (result[7] == null) {
                        rd.setProgName("");
                    } else {
                        rd.setProgName(result[7].toString());
                    }
                    if (result[8] == null) {
                        rd.setActDura("");
                    } else {
                        rd.setActDura(result[8].toString());
                    }
                    if (result[9] == null) {
                        rd.setFacuName("");
                    } else {
                        rd.setFacuName(result[9].toString());
                    }
                    if (result[10] == null) {
                        rd.setTrngCost("");
                    } else {
                        rd.setTrngCost(result[10].toString());
                    }
                    if (result[11] == null) {
                        rd.setCommence("");
                    } else {
                        rd.setCommence(result[11].toString());
                    }
                    if (result[12] == null) {
                        rd.setEndDate("");
                    } else {
                        rd.setEndDate(result[12].toString());
                    }
                    if (result[13] == null) {
                        rd.setApprDet("");
                    } else {
                        rd.setApprDet(result[13].toString());
                    }
                    if (result[14] == null) {
                        rd.setTrinerAss("");
                    } else {
                        rd.setTrinerAss(result[14].toString());
                    }
                    if (result[15] == null) {
                        rd.setTraineeAss("");
                    } else {
                        rd.setTraineeAss(result[15].toString());
                    }
                    if (result[16] == null) {
                        rd.setDatePlanFrom("");
                    } else {
                        rd.setDatePlanFrom(result[16].toString());
                    }
                    if (result[17] == null) {
                        rd.setDatePlanTo("");
                    } else {
                        rd.setDatePlanTo(result[17].toString());
                    }
                    if (result[18] == null) {
                        rd.setTrngDur("");
                    } else {
                        rd.setTrngDur(result[18].toString());
                    }
                    if (result[19] == null) {
                        rd.setNextHouse("");
                    } else {
                        rd.setNextHouse(result[19].toString());
                    }
                    if (result[20] == null) {
                        rd.setDeptName("");
                    } else {
                        rd.setDeptName(result[20].toString());
                    }
                    trnExc.add(rd);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method gridEditButton()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method gridEditButton()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method gridEditButton()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectcurrentrowEditAction() {
        try {
            setErrorMessage("");
            setMessage("");
            saveFlag = true;
            addDisable = true;
            editFlag = false;
            this.setEmpName(currentItem1.getEmpName());
            this.setEmpId(currentItem1.getEmpId());
            this.setTraineeNeed(currentItem1.getTrngName());
            this.setProgram(currentItem1.getProgName());
            this.setFacultyName(currentItem1.getFacuName());
            this.setDepartment(currentItem1.getDeptName());
            this.setCommencingDate(currentItem1.getCommence());
            this.setEndingDate(currentItem1.getEndDate());
            this.setDuration(currentItem1.getTrngDur());
            this.setActualDuration(currentItem1.getActDura());
            this.setCost(currentItem1.getTrngCost());
            this.setTrainerAssesment(currentItem1.getTrinerAss());
            this.setTraineesAssesment(currentItem1.getTraineeAss());
            setOperation("2");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fillValuesOfTrainingPlan() {
        try {
             setErrorMessage("");
            setMessage("");
            saveFlag = false;
            this.setEmpName(currentItem.getEmpName());
            this.setEmpId(currentItem.getEmpId());
            this.setEmpCode(currentItem.getEmpCode());
            this.setTraineeNeed(currentItem.getTrngCodeDesc());
            this.setTrainingCode(currentItem.getTrngCode());
            this.setProgram(currentItem.getProgName());
            this.setProgCode(currentItem.getProgCode());
            this.setFacultyName(currentItem.getFacultyName());
            this.setDepartment(currentItem.getDeptCodeDesc());
            this.setDuration(currentItem.getTrngDur());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveTransactionExectutionDetail() {
        try {
            
            Date date = new Date();
            this.setErrorMessage("");
            this.setMessage("");
            if (this.empName == null || this.empName.equalsIgnoreCase("")) {
                this.setErrorMessage("Employee name could not be blank !!!");
                return;
            }
            if (this.empId == null || this.empId.equalsIgnoreCase("")) {
                this.setErrorMessage("Employee ID could not be blank !!!");
                return;
            }
            if (this.traineeNeed == null || this.traineeNeed.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter training need !!!");
                return;
            }
            if (this.program == null || this.program.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter program !!!");
                return;
            }
            if (this.facultyName == null || this.facultyName.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter faculty name !!!");
                return;
            }
            if (this.department == null || this.department.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter department !!!");
                return;
            }
            if (this.commencingDate == null || this.commencingDate.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter commencing date !!!");
                return;
            }
            if (this.endingDate == null || this.endingDate.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter ending date !!!");
                return;
            }
            if (this.duration == null || this.duration.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter duration !!!");
                return;
            }
            if (this.actualDuration == null || this.actualDuration.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter actual Actual Duration !!!");
                return;
            }
            if (!actualDuration.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Actual Duration");
                return;
            }
            if (this.cost == null || this.cost.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter cost !!!");
                return;
            }
            if (!cost.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Cost");
                return;
            }
            if (this.trainerAssesment == null || this.trainerAssesment.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter trainer assesment !!!");
                return;
            }
            if (this.traineesAssesment == null || this.traineesAssesment.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter trainee assesment !!!");
                return;
            }
            String trngExecCode = "";
            TrainingExecutionDelegate trngExecDelegate = new TrainingExecutionDelegate();
            trngExecCode = trngExecDelegate.maxTrngExecutionCode();
            if (trngExecCode == null || trngExecCode.equalsIgnoreCase("")) {
                this.setErrorMessage("Training execution code could not be generated !!!");
                return;
            } else if (trngExecCode.contains("!")) {
                trngExecCode = "TRE00000001";
            } else {
                String tempTrngExecCode = trngExecCode.substring(3);
                int numTrngExecCode = Integer.parseInt(tempTrngExecCode) + 1;
                tempTrngExecCode = String.valueOf(numTrngExecCode);
                int length = tempTrngExecCode.length();
                int addedZero = 8 - length;
                for (int i = 1; i <= addedZero; i++) {
                    tempTrngExecCode = "0" + tempTrngExecCode;
                }
                trngExecCode = "TRE" + tempTrngExecCode;
            }
            HrTrainingExecutionTO hrTrngExec = new HrTrainingExecutionTO();
            HrTrainingExecutionPKTO hrTrngExecPK = new HrTrainingExecutionPKTO();
            hrTrngExecPK.setCompCode(Integer.parseInt(this.compCode));
            hrTrngExecPK.setEmpCode(Long.parseLong(this.empCode));
            hrTrngExecPK.setTrngexecCode(trngExecCode);
            hrTrngExec.setHrTrainingExecutionPKTO(hrTrngExecPK);
            hrTrngExec.setTrngCode(this.trainingCode);
            hrTrngExec.setProgCode(this.progCode);
            hrTrngExec.setFacuName(this.facultyName);
            hrTrngExec.setInextHouse('I');
            hrTrngExec.setDatePlanFrom(currentItem.getDateFrom());
            hrTrngExec.setDatePlanTo(currentItem.getDateTo());
            hrTrngExec.setCommence(this.commencingDate);
            hrTrngExec.setEndDate(this.endingDate);
            hrTrngExec.setActDura(Integer.parseInt(this.actualDuration));
            hrTrngExec.setTrngCost(Integer.parseInt(this.cost));
            hrTrngExec.setApprDet(currentItem.getApprDetail().charAt(0));
            hrTrngExec.setTrainerAss(this.trainerAssesment);
            hrTrngExec.setTraineeAss(this.traineesAssesment);
            hrTrngExec.setDefaultComp(defaultComp);
            hrTrngExec.setStatFlag("Y");
            hrTrngExec.setStatUpFlag("N");
            hrTrngExec.setModDate(date);
            hrTrngExec.setAuthBy(getUserName());
            hrTrngExec.setEnteredBy(getUserName());
            HrTrainingPlanTO hrTrngPlan = new HrTrainingPlanTO();
            HrTrainingPlanPKTO hrTrngPlanPK = new HrTrainingPlanPKTO();
            hrTrngPlanPK.setCompCode(Integer.parseInt(this.compCode));
            hrTrngPlanPK.setEmpCode(Long.parseLong(empCode));
            hrTrngPlanPK.setTrngCode(trainingCode);
            hrTrngPlanPK.setProgCode(progCode);
            hrTrngPlanPK.setDateFrom(currentItem.getDateFrom());
            hrTrngPlanPK.setDateTo(currentItem.getDateTo());
            hrTrngPlan.setHrTrainingPlanPKTO(hrTrngPlanPK);
            String result = trngExecDelegate.saveTrngExecDetail(hrTrngExec, hrTrngPlan);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
            } else if (result.equalsIgnoreCase("false")) {
                this.setErrorMessage("Data not saved !!!");
            } else {
                this.setMessage("Data saved succesfully.");
                clear();
                gridEditButton();
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveTransactionExectutionDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveTransactionExectutionDetail()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveTransactionExectutionDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void updateTransactionExectutionDetail() {
        try {
            Date date = new Date();
            this.setErrorMessage("");
            this.setMessage("");
            if (this.empName == null || this.empName.equalsIgnoreCase("")) {
                this.setErrorMessage("Employee name could not be blank !!!");
                return;
            }
            if (this.empId == null || this.empId.equalsIgnoreCase("")) {
                this.setErrorMessage("Employee ID could not be blank !!!");
                return;
            }
            if (this.traineeNeed == null || this.traineeNeed.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter training need !!!");
                return;
            }
            if (this.program == null || this.program.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter program !!!");
                return;
            }
            if (this.facultyName == null || this.facultyName.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter faculty name !!!");
                return;
            }
            if (this.department == null || this.department.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter department !!!");
                return;
            }
            if (this.commencingDate == null || this.commencingDate.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter commencing date !!!");
                return;
            }
            if (this.endingDate == null || this.endingDate.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter ending date !!!");
                return;
            }
            if (this.duration == null || this.duration.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter duration !!!");
                return;
            }
            if (!actualDuration.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Actual Duration");
                return;
            }
            if (this.cost == null || this.cost.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter cost !!!");
                return;
            }
            if (!cost.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Cost");
                return;
            }
            if (this.trainerAssesment == null || this.trainerAssesment.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter trainer assesment !!!");
                return;
            }
            if (this.traineesAssesment == null || this.traineesAssesment.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter trainee assesment !!!");
                return;
            }
            HrTrainingExecutionTO hrTrngExec = new HrTrainingExecutionTO();
            HrTrainingExecutionPKTO hrTrngExecPK = new HrTrainingExecutionPKTO();
            hrTrngExecPK.setCompCode(Integer.parseInt(this.compCode));
            hrTrngExecPK.setEmpCode(Long.parseLong(currentItem1.getEmpCode()));
            hrTrngExecPK.setTrngexecCode(currentItem1.getTrngExcCode());
            hrTrngExec.setHrTrainingExecutionPKTO(hrTrngExecPK);
            hrTrngExec.setTrngCode(currentItem1.getTrngCode());
            hrTrngExec.setProgCode(currentItem1.getProgCode());
            hrTrngExec.setFacuName(this.facultyName);
            hrTrngExec.setInextHouse('I');
            hrTrngExec.setDatePlanFrom(currentItem1.getDatePlanFrom());
            hrTrngExec.setDatePlanTo(currentItem1.getDatePlanTo());
            hrTrngExec.setCommence(this.commencingDate);
            hrTrngExec.setEndDate(this.endingDate);
            hrTrngExec.setActDura(Integer.parseInt(this.actualDuration));
            hrTrngExec.setTrngCost(Integer.parseInt(this.cost));
            hrTrngExec.setApprDet(currentItem1.getApprDet().charAt(0));
            hrTrngExec.setTrainerAss(this.trainerAssesment);
            hrTrngExec.setTraineeAss(this.traineesAssesment);
            hrTrngExec.setDefaultComp(defaultComp);
            hrTrngExec.setStatFlag("Y");
            hrTrngExec.setStatUpFlag("N");
            hrTrngExec.setModDate(date);
            hrTrngExec.setAuthBy(getUserName());
            hrTrngExec.setEnteredBy(getUserName());
            TrainingExecutionDelegate trngExecDelegate = new TrainingExecutionDelegate();
            String result = trngExecDelegate.updateTrngExecDetail(hrTrngExec);
            this.setMessage(result);
            clear();
            gridEditButton();
        } catch (WebException e) {
            logger.error("Exception occured while executing method updateTransactionExectutionDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method updateTransactionExectutionDetail()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method updateTransactionExectutionDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteTransactionExectutionDetail() {
        try {
            Date date = new Date();
            this.setErrorMessage("");
            this.setMessage("");
            if (currentItem1.getEmpCode() == null || currentItem1.getEmpCode().equalsIgnoreCase("")) {
                this.setErrorMessage("Employee code could not be blank !!!");
                return;
            }
            if (currentItem1.getTrngExcCode() == null || currentItem1.getTrngExcCode().equalsIgnoreCase("")) {
                this.setErrorMessage("Training Execution Code could not be blank !!!");
                return;
            }
            HrTrainingExecutionTO hrTrngExec = new HrTrainingExecutionTO();
            HrTrainingExecutionPKTO hrTrngExecPK = new HrTrainingExecutionPKTO();
            hrTrngExecPK.setCompCode(Integer.parseInt(this.compCode));
            hrTrngExecPK.setEmpCode(Long.parseLong(currentItem1.getEmpCode()));
            hrTrngExecPK.setTrngexecCode(currentItem1.getTrngExcCode());
            hrTrngExec.setHrTrainingExecutionPKTO(hrTrngExecPK);
            hrTrngExec.setTrngCode(currentItem1.getTrngCode());
            hrTrngExec.setProgCode(currentItem1.getProgCode());
            hrTrngExec.setFacuName(this.facultyName);
            hrTrngExec.setInextHouse('I');
            hrTrngExec.setDatePlanFrom(currentItem1.getDatePlanFrom());
            hrTrngExec.setDatePlanTo(currentItem1.getDatePlanTo());
            hrTrngExec.setCommence(this.commencingDate);
            hrTrngExec.setEndDate(this.endingDate);
            hrTrngExec.setActDura(Integer.parseInt(this.actualDuration));
            hrTrngExec.setTrngCost(Integer.parseInt(this.cost));
            hrTrngExec.setApprDet(currentItem1.getApprDet().charAt(0));
            hrTrngExec.setTrainerAss(this.trainerAssesment);
            hrTrngExec.setTraineeAss(this.traineesAssesment);
            hrTrngExec.setDefaultComp(defaultComp);
            hrTrngExec.setStatFlag("Y");
            hrTrngExec.setStatUpFlag("N");
            hrTrngExec.setModDate(date);
            hrTrngExec.setAuthBy(getUserName());
            hrTrngExec.setEnteredBy(getUserName());
            HrTrainingPlanTO hrTrngPlan = new HrTrainingPlanTO();
            HrTrainingPlanPKTO hrTrngPlanPK = new HrTrainingPlanPKTO();
            hrTrngPlanPK.setCompCode(Integer.parseInt(this.compCode));
            hrTrngPlanPK.setEmpCode(Long.parseLong(currentItem1.getEmpCode()));
            hrTrngPlanPK.setTrngCode(currentItem1.getTrngCode());
            hrTrngPlanPK.setProgCode(currentItem1.getProgCode());
            hrTrngPlanPK.setDateFrom(currentItem1.getDatePlanFrom());
            hrTrngPlanPK.setDateTo(currentItem1.getDatePlanTo());
            hrTrngPlan.setHrTrainingPlanPKTO(hrTrngPlanPK);
            TrainingExecutionDelegate trngExecDelegate = new TrainingExecutionDelegate();
            String result = trngExecDelegate.deleteTrngExecDetail(hrTrngExec, hrTrngPlan);
            this.setMessage(result);
            clear();
            gridEditButton();
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteTransactionExectutionDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method deleteTransactionExectutionDetail()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteTransactionExectutionDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void resetForm() {
        try {
            setErrorMessage("");
            setMessage("");
            saveFlag = true;
            addDisable = false;
            editFlag = true;
            this.setEmpName("");
            this.setEmpId("");
            this.setTraineeNeed("");
            this.setProgram("");
            this.setFacultyName("");
            this.setDepartment("");
            this.setCommencingDate("");
            this.setEndingDate("");
            this.setDuration("");
            this.setActualDuration("");
            this.setCost("");
            this.setTrainerAssesment("");
            this.setTraineesAssesment("");
            setOperation("0");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clear() {
        this.setEmpName("");
        this.setEmpId("");
        this.setTraineeNeed("");
        this.setProgram("");
        this.setFacultyName("");
        this.setDepartment("");
        this.setCommencingDate("");
        this.setEndingDate("");
        this.setDuration("");
        this.setActualDuration("");
        this.setCost("");
        this.setTrainerAssesment("");
        this.setTraineesAssesment("");
        setOperation("0");
    }
    public void setOperationOnBlur()
    {
        setMessage("");
        this.setEmpName("");
        this.setEmpId("");
        this.setTraineeNeed("");
        this.setProgram("");
        this.setFacultyName("");
        this.setDepartment("");
        this.setCommencingDate("");
        this.setEndingDate("");
        this.setDuration("");
        this.setActualDuration("");
        this.setCost("");
        this.setTrainerAssesment("");
        this.setTraineesAssesment("");
        setErrorMessage("");
        if(operation.equalsIgnoreCase("0"))
        {
            setErrorMessage("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
            gridLoadSearchBtn();
        }
    }

    public String exitBtnAction() {
      return "case1";
    }
}
