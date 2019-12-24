/*
 * CREATED BY   :   ROHIT KRISHNA GUPTA
 * CREATION DATE:   21 JUNE 2011
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.TraninigPlanGridFile;
import com.cbs.servlets.Init;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.complexTO.TrainingPlanData;
import com.hrms.web.delegate.TrainingPlanDelegate;
import java.util.Calendar;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import com.hrms.common.to.HrTrainingPlanPKTO;
import com.hrms.common.to.HrTrainingPlanTO;
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
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ROHIT KRISHNA
 */
public class TrainingPlan extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(TrainingPlan.class);
    private String errorMessage;
    private String message;
    private String compCode = "0";
    private String empName;
    private String empId;
    private String designation;
    private String grade;
    private String block;
    private String unit;
    private String department;
    private String training;
    private String program;
    private String duration;
    private String appraisal;
    private List<SelectItem> appraisalList;
    private Date fromDt;
    private Date toDt;
    private boolean saveFlag;
    private boolean editFlag;
    private boolean delFlag;
    private List<TraninigPlanGridFile> gridDetail;
    int currentRow;
    private TraninigPlanGridFile currentItem = new TraninigPlanGridFile();
    private String empSearchMsg;
    private String empSearchErrorMsg;
    private String findBy;
    private List<SelectItem> findByList;
    private String searchCriteria;
    private List<TraninigPlanGridFile> gridDetail1;
    int currentRow1;
    private TraninigPlanGridFile currentItem1 = new TraninigPlanGridFile();
    private List<TraninigPlanGridFile> gridDetail2;
    int currentRow2;
    private TraninigPlanGridFile currentItem2 = new TraninigPlanGridFile();
    private List<TrainingPlanData> trainingTransObj;
    private List<TraninigPlanGridFile> gridDetail4;
    int currentRow4;
    private TraninigPlanGridFile currentItem4 = new TraninigPlanGridFile();
    private boolean disFlag;
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

    public boolean isDisFlag() {
        return disFlag;
    }

    public void setDisFlag(boolean disFlag) {
        this.disFlag = disFlag;
    }

    public TraninigPlanGridFile getCurrentItem4() {
        return currentItem4;
    }

    public void setCurrentItem4(TraninigPlanGridFile currentItem4) {
        this.currentItem4 = currentItem4;
    }

    public int getCurrentRow4() {
        return currentRow4;
    }

    public void setCurrentRow4(int currentRow4) {
        this.currentRow4 = currentRow4;
    }

    public List<TraninigPlanGridFile> getGridDetail4() {
        return gridDetail4;
    }

    public void setGridDetail4(List<TraninigPlanGridFile> gridDetail4) {
        this.gridDetail4 = gridDetail4;
    }

    public List<TrainingPlanData> getTrainingTransObj() {
        return trainingTransObj;
    }

    public void setTrainingTransObj(List<TrainingPlanData> trainingTransObj) {
        this.trainingTransObj = trainingTransObj;
    }

    public TraninigPlanGridFile getCurrentItem2() {
        return currentItem2;
    }

    public void setCurrentItem2(TraninigPlanGridFile currentItem2) {
        this.currentItem2 = currentItem2;
    }

    public int getCurrentRow2() {
        return currentRow2;
    }

    public void setCurrentRow2(int currentRow2) {
        this.currentRow2 = currentRow2;
    }

    public List<TraninigPlanGridFile> getGridDetail2() {
        return gridDetail2;
    }

    public void setGridDetail2(List<TraninigPlanGridFile> gridDetail2) {
        this.gridDetail2 = gridDetail2;
    }

    public TraninigPlanGridFile getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(TraninigPlanGridFile currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public List<TraninigPlanGridFile> getGridDetail1() {
        return gridDetail1;
    }

    public void setGridDetail1(List<TraninigPlanGridFile> gridDetail1) {
        this.gridDetail1 = gridDetail1;
    }

    public String getFindBy() {
        return findBy;
    }

    public void setFindBy(String findBy) {
        this.findBy = findBy;
    }

    public List<SelectItem> getFindByList() {
        return findByList;
    }

    public void setFindByList(List<SelectItem> findByList) {
        this.findByList = findByList;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
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

    public String getEmpSearchErrorMsg() {
        return empSearchErrorMsg;
    }

    public void setEmpSearchErrorMsg(String empSearchErrorMsg) {
        this.empSearchErrorMsg = empSearchErrorMsg;
    }

    public String getEmpSearchMsg() {
        return empSearchMsg;
    }

    public void setEmpSearchMsg(String empSearchMsg) {
        this.empSearchMsg = empSearchMsg;
    }

    public List<TraninigPlanGridFile> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<TraninigPlanGridFile> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal;
    }

    public List<SelectItem> getAppraisalList() {
        return appraisalList;
    }

    public void setAppraisalList(List<SelectItem> appraisalList) {
        this.appraisalList = appraisalList;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getCompanyCode() {
        return compCode;
    }

    public void setCompanyCode(String compCode) {
        this.compCode = compCode;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isEditFlag() {
        return editFlag;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
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

    public NumberFormat getFormatter() {
        return formatter;
    }

    public void setFormatter(NumberFormat formatter) {
        this.formatter = formatter;
    }

    public Date getFromDt() {
        return fromDt;
    }

    public void setFromDt(Date fromDt) {
        this.fromDt = fromDt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Pattern getP() {
        return p;
    }

    public void setP(Pattern p) {
        this.p = p;
    }

    public Pattern getPm() {
        return pm;
    }

    public void setPm(Pattern pm) {
        this.pm = pm;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public boolean isSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(boolean saveFlag) {
        this.saveFlag = saveFlag;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    /** Creates a new instance of TrainingPlan */
    public TrainingPlan() {
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
            this.setErrorMessage("");
            this.setMessage("");
            this.setSaveFlag(false);
            this.setEditFlag(true);
            this.setDelFlag(true);
            appraisalList = new ArrayList<SelectItem>();
            appraisalList.add(new SelectItem("--SELECT--"));
            appraisalList.add(new SelectItem("W", "WITHIN APPRAISAL"));
            appraisalList.add(new SelectItem("E", "EXTERNAL APPRAISAL"));
            findByList = new ArrayList<SelectItem>();
            findByList.add(new SelectItem("--SELECT--"));
            findByList.add(new SelectItem("1", "BY NAME"));
            findByList.add(new SelectItem("0", "BY ID"));
            this.setDisFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Pattern pm = Pattern.compile("[a-zA-z0-9,]+([ '-/][a-zA-Z0-9,]+)*");
    Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
    NumberFormat formatter = new DecimalFormat("#0.00");

    public void trainingMasterGridLoad() {
        try {
            gridDetail = new ArrayList<TraninigPlanGridFile>();
            if (this.findBy.equalsIgnoreCase("--SELECT--")) {
                this.setEmpSearchErrorMsg("Please select Search.");
                return;
            }
            if (this.searchCriteria == null || this.searchCriteria.equalsIgnoreCase("") || this.searchCriteria.length() == 0) {
                this.searchCriteria = "";
            }
            List resultLt = new ArrayList();
            TrainingPlanDelegate trngPlanDel = new TrainingPlanDelegate();
            resultLt = trngPlanDel.addBtnGridLoad(Integer.parseInt(compCode), Integer.parseInt(this.findBy), this.searchCriteria);
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    TraninigPlanGridFile rd = new TraninigPlanGridFile();
                    if (result[0] == null) {
                        rd.setEmpId("");
                    } else {
                        rd.setEmpId(result[0].toString());
                    }
                    if (result[1] == null) {
                        rd.setEmpName("");
                    } else {
                        rd.setEmpName(result[1].toString());
                    }
                    if (result[2] == null) {
                        rd.setEmpAddress("");
                    } else {
                        rd.setEmpAddress(result[2].toString());
                    }
                    if (result[3] == null) {
                        rd.setEmpTelephone("");
                    } else {
                        rd.setEmpTelephone(result[3].toString());
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

    public void resetEmpSearchRecord() {
        try {
            this.setEmpSearchErrorMsg("");
            this.setEmpSearchMsg("");
            this.setFindBy("--SELECT--");
            this.setSearchCriteria("");
            gridDetail = new ArrayList<TraninigPlanGridFile>();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**Variables for code**/
    private String empCode;
    private String blockCode;
    private String unitCode;
    private String deptcode;
    private String desgCode;
    private String gradecode;
    private String trainingCode;
    private String progCode;

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

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
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

    public String getGradecode() {
        return gradecode;
    }

    public void setGradecode(String gradecode) {
        this.gradecode = gradecode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    /****/
    public void fillValuesOfEmpSearchGrid() {
        try {
            List resultLt = new ArrayList();
            TrainingPlanDelegate trngPlanDel = new TrainingPlanDelegate();
            resultLt = trngPlanDel.empSearchBtnGridLoad(Integer.parseInt(compCode), currentItem.getEmpId());
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    this.setEmpCode(result[0].toString());
                    this.setEmpId(result[1].toString());
                    this.setEmpName(result[2].toString());
                    this.setBlockCode(result[3].toString());
                    this.setBlock(result[4].toString());
                    this.setUnitCode(result[5].toString());
                    this.setUnit(result[6].toString());
                    this.setDeptcode(result[7].toString());
                    this.setDepartment(result[8].toString());
                    this.setDesgCode(result[9].toString());
                    this.setDesignation(result[10].toString());
                    this.setGradecode(result[11].toString());
                    this.setGrade(result[12].toString());
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method fillValuesOfEmpSearchGrid()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method fillValuesOfEmpSearchGrid()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method fillValuesOfEmpSearchGrid()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void trainingGridPanelLoad() {
        try {
            gridDetail1 = new ArrayList<TraninigPlanGridFile>();
            List resultLt = new ArrayList();
            TrainingPlanDelegate trngPlanDel = new TrainingPlanDelegate();
            resultLt = trngPlanDel.trainingSearchBtnGridLoad(Integer.parseInt(compCode));
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    TraninigPlanGridFile rd = new TraninigPlanGridFile();
                    if (result[0] == null) {
                        rd.setTrainingCode("");
                    } else {
                        rd.setTrainingCode(result[0].toString());
                    }
                    if (result[1] == null) {
                        rd.setTrainingDesc("");
                    } else {
                        rd.setTrainingDesc(result[1].toString());
                    }
                    gridDetail1.add(rd);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method trainingGridPanelLoad()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method trainingGridPanelLoad()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method trainingGridPanelLoad()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fillValuesOfTrainingGrid() {
        try {
            this.setTraining(currentItem1.getTrainingDesc());
            this.setTrainingCode(currentItem1.getTrainingCode());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void programGridPanelLoad() {
        try {
            gridDetail2 = new ArrayList<TraninigPlanGridFile>();
            List resultLt = new ArrayList();
            TrainingPlanDelegate trngPlanDel = new TrainingPlanDelegate();
            resultLt = trngPlanDel.programSearchBtnGridLoad(Integer.parseInt(compCode), this.trainingCode);
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    TraninigPlanGridFile rd = new TraninigPlanGridFile();
                    if (result[0] == null) {
                        rd.setProgCode("");
                    } else {
                        rd.setProgCode(result[0].toString());
                    }
                    if (result[1] == null) {
                        rd.setProgName("");
                    } else {
                        rd.setProgName(result[1].toString());
                    }
                    gridDetail2.add(rd);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method programGridPanelLoad()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method programGridPanelLoad()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method programGridPanelLoad()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fillValuesOfProgramGrid() {
        try {
            this.setProgram(currentItem2.getProgName());
            this.setProgCode(currentItem2.getProgCode());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static long dayDiff(Date fromDate, Date toDate) {
        long diffDays = 0;
        try {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(fromDate);
            cal2.setTime(toDate);
            long fromMiliSecond = cal1.getTimeInMillis();
            long toMiliSecond = cal2.getTimeInMillis();
            long diff = toMiliSecond - fromMiliSecond;
            diffDays = diff / (24 * 60 * 60 * 1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return diffDays;
    }

    public void saveTrainingPlanDetail() {
        try {
            trainingTransObj = new ArrayList<TrainingPlanData>();
            Date date = new Date();
            this.setErrorMessage("");
            this.setMessage("");
            if (operation.equalsIgnoreCase("0")) {
                setErrorMessage("Please select an operation!");
                return;
            }
            if (this.empName == null || this.empName.equalsIgnoreCase("") || this.empName.length() == 0) {
                this.setErrorMessage("Employee name could not be blank !!!");
                return;
            }
            if (this.empId == null || this.empId.equalsIgnoreCase("") || this.empId.length() == 0) {
                this.setErrorMessage("Employee ID could not be blank !!!");
                return;
            }
            if (this.designation == null || this.desgCode == null || this.designation.equalsIgnoreCase("") || this.desgCode.equalsIgnoreCase("") || this.designation.length() == 0) {
                this.desgCode = "";
            }
            if (this.grade == null || this.gradecode == null || this.grade.equalsIgnoreCase("") || this.gradecode.equalsIgnoreCase("") || this.grade.length() == 0) {
                this.gradecode = "";
            }
            if (this.block == null || this.blockCode == null || this.block.equalsIgnoreCase("") || this.blockCode.equalsIgnoreCase("") || this.block.length() == 0) {
                this.blockCode = "";
            }
            if (this.unit == null || this.unitCode == null || this.unit.equalsIgnoreCase("") || this.unitCode.equalsIgnoreCase("") || this.unit.length() == 0) {
                this.unitCode = "";
            }
            if (this.department == null || this.deptcode == null || this.department.equalsIgnoreCase("") || this.deptcode.equalsIgnoreCase("") || this.department.length() == 0) {
                this.deptcode = "";
            }
            if (this.training == null || this.trainingCode == null || this.training.equalsIgnoreCase("") || this.trainingCode.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter training.");
                return;
            }
            if (this.program == null || this.progCode == null || this.program.equalsIgnoreCase("") || this.progCode.equalsIgnoreCase("")) {
                this.setErrorMessage("Please enter program.");
                return;
            }
            if (this.fromDt == null) {
                this.setErrorMessage("Please enter date from.");
                return;
            }
            if (this.toDt == null) {
                this.setErrorMessage("Please enter date to.");
                return;
            }
          
            if (this.appraisal.equalsIgnoreCase("--SELECT--")) {
                this.setErrorMessage("Please select appraisal.");
                return;
            }
            HrTrainingPlanTO trngPlanObj = new HrTrainingPlanTO();
            HrTrainingPlanPKTO trngPlanObjPK = new HrTrainingPlanPKTO();
            trngPlanObjPK.setCompCode(Integer.parseInt(this.compCode));
            trngPlanObjPK.setEmpCode(Long.parseLong(this.empCode));
            trngPlanObjPK.setTrngCode(trainingCode);
            trngPlanObjPK.setProgCode(progCode);
            trngPlanObjPK.setDateFrom(sdf.format(this.fromDt));
            trngPlanObjPK.setDateTo(sdf.format(this.toDt));
            trngPlanObj.setHrTrainingPlanPKTO(trngPlanObjPK);
            int trngDurdays = (int) dayDiff(ymd.parse(ymd.format(this.fromDt)), ymd.parse(ymd.format(this.toDt)));
            trngPlanObj.setTrngDur(trngDurdays);
            trngPlanObj.setTrngExec('N');
            trngPlanObj.setDefaultComp(defaultComp);
            trngPlanObj.setApprDet(this.appraisal.charAt(0));
            trngPlanObj.setStatFlag("Y");
            trngPlanObj.setStatUpFlag("Y");
            trngPlanObj.setModDate(date);
            trngPlanObj.setAuthBy(getUserName());
            trngPlanObj.setEnteredBy(getUserName());
            String result = "";
            TrainingPlanDelegate trngPlanDel = new TrainingPlanDelegate();
            result = trngPlanDel.saveTrainingPlanDetail(trngPlanObj, operation);
            setMessage(result);
            this.setEmpName("");
            this.setEmpId("");
            this.setDesignation("");
            this.setGrade("");
            this.setBlock("");
            this.setUnit("");
            this.setDepartment("");
            this.setTraining("");
            this.setProgram("");
            this.setFromDt(null);
            this.setToDt(null);
            this.setDuration("");
            this.setAppraisal("--SELECT--");
            this.setSaveFlag(false);
            this.setEditFlag(true);
            this.setDelFlag(true);
            setOperation("0");
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveTrainingPlanDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveTrainingPlanDetail()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveTrainingPlanDetail()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteTrainingPlan() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            HrTrainingPlanPKTO trngPlanObjPK = new HrTrainingPlanPKTO();
            trngPlanObjPK.setCompCode(Integer.parseInt(this.compCode));
            trngPlanObjPK.setEmpCode(Long.parseLong(currentItem4.getEmpCode()));
            trngPlanObjPK.setTrngCode(currentItem4.getTrngCode());
            trngPlanObjPK.setProgCode(currentItem4.getProgCode());
            trngPlanObjPK.setDateFrom(currentItem4.getDateFrom());
            trngPlanObjPK.setDateTo(currentItem4.getDateTo());
            TrainingPlanDelegate trngPlanDel = new TrainingPlanDelegate();
            String result = trngPlanDel.deleteTrainingPlanDetail(trngPlanObjPK);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.equalsIgnoreCase("false")) {
                this.setErrorMessage("Record not deleted.");
                return;
            } else {
                this.setMessage("Record deleted succesfully.");
                this.setEmpName("");
                this.setEmpId("");
                this.setDesignation("");
                this.setGrade("");
                this.setBlock("");
                this.setUnit("");
                this.setDepartment("");
                this.setTraining("");
                this.setProgram("");
                this.setFromDt(null);
                this.setToDt(null);
                this.setDuration("");
                this.setAppraisal("--SELECT--");
                this.setSaveFlag(false);
                this.setEditFlag(true);
                this.setDelFlag(true);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteTrainingPlan()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method deleteTrainingPlan()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteTrainingPlan()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void gridLoadtrainingPlan() {
        try {
            gridDetail4 = new ArrayList<TraninigPlanGridFile>();
            List resultLt = new ArrayList();
            TrainingPlanDelegate trngPlanDel = new TrainingPlanDelegate();
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
                        rd.setEmpId("");
                    } else {
                        rd.setEmpId(result[1].toString());
                    }
                    if (result[2] == null) {
                        rd.setEmpName("");
                    } else {
                        rd.setEmpName(result[2].toString());
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
                        rd.setDesgCode("");
                    } else {
                        rd.setDesgCode(result[7].toString());
                    }
                    if (result[8] == null) {
                        rd.setDesgCodeDesc("");
                    } else {
                        rd.setDesgCodeDesc(result[8].toString());
                    }
                    if (result[9] == null) {
                        rd.setGradeCode("");
                    } else {
                        rd.setGradeCode(result[9].toString());
                    }
                    if (result[10] == null) {
                        rd.setGradeDesc("");
                    } else {
                        rd.setGradeDesc(result[10].toString());
                    }
                    if (result[11] == null) {
                        rd.setBlockCode("");
                    } else {
                        rd.setBlockCode(result[11].toString());
                    }
                    if (result[12] == null) {
                        rd.setBlockCodeDesc("");
                    } else {
                        rd.setBlockCodeDesc(result[12].toString());
                    }
                    if (result[13] == null) {
                        rd.setUnitCode("");
                    } else {
                        rd.setUnitCode(result[13].toString());
                    }
                    if (result[14] == null) {
                        rd.setUnitCodeDesc("");
                    } else {
                        rd.setUnitCodeDesc(result[14].toString());
                    }
                    if (result[15] == null) {
                        rd.setDeptCode("");
                    } else {
                        rd.setDeptCode(result[15].toString());
                    }
                    if (result[16] == null) {
                        rd.setDeptCodeDesc("");
                    } else {
                        rd.setDeptCodeDesc(result[16].toString());
                    }
                    if (result[17] == null) {
                        rd.setDateFrom("");
                    } else {
                        rd.setDateFrom(result[17].toString());
                    }
                    if (result[18] == null) {
                        rd.setDateTo("");
                    } else {
                        rd.setDateTo(result[18].toString());
                    }
                    if (result[19] == null) {
                        rd.setTrngDur("");
                    } else {
                        rd.setTrngDur(result[19].toString());
                    }
                    if (result[20] == null) {
                        rd.setApprDetail("");
                    } else {
                        rd.setApprDetail(result[20].toString());
                    }
                    gridDetail4.add(rd);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method gridLoadtrainingPlan()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method gridLoadtrainingPlan()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method gridLoadtrainingPlan()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fillValuesOfTrainingPlan() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setEmpName(currentItem4.getEmpName());
            this.setEmpId(currentItem4.getEmpId());
            this.setDesignation(currentItem4.getDesgCodeDesc());
            this.setGrade(currentItem4.getGradeDesc());
            this.setBlock(currentItem4.getBlockCodeDesc());
            this.setUnit(currentItem4.getUnitCodeDesc());
            this.setDepartment(currentItem4.getDeptCodeDesc());
            this.setTraining(currentItem4.getTrngCodeDesc());
            this.setProgram(currentItem4.getProgName());
            this.setFromDt(ymd.parse(currentItem4.getDateFrom().substring(6) + currentItem4.getDateFrom().substring(3, 5) + currentItem4.getDateFrom().substring(0, 2)));
            this.setToDt(ymd.parse(currentItem4.getDateTo().substring(6) + currentItem4.getDateTo().substring(3, 5) + currentItem4.getDateTo().substring(0, 2)));
            this.setDuration(currentItem4.getTrngDur());
            this.setAppraisal(currentItem4.getApprDetail());
            /**Below code values are set in the setters of code variable**/
            this.setEmpCode(currentItem4.getEmpCode());
            this.setDesgCode(currentItem4.getDesgCode());
            this.setGradecode(currentItem4.getGradeCode());
            this.setBlockCode(currentItem4.getBlockCode());
            this.setUnitCode(currentItem4.getUnitCode());
            this.setDeptcode(currentItem4.getDeptCode());
            this.setTrainingCode(currentItem4.getTrngCode());
            this.setProgCode(currentItem4.getProgCode());
            this.setOperation("2");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setEmpName("");
            this.setEmpId("");
            this.setDesignation("");
            this.setGrade("");
            this.setBlock("");
            this.setUnit("");
            this.setDepartment("");
            this.setTraining("");
            this.setProgram("");
            this.setFromDt(null);
            this.setToDt(null);
            this.setDuration("");
            this.setAppraisal("--SELECT--");
            this.setSaveFlag(false);
            this.setEditFlag(true);
            this.setDelFlag(true);
            this.setEmpSearchErrorMsg("");
            this.setEmpSearchMsg("");
            this.setFindBy("--SELECT--");
            this.setSearchCriteria("");
            gridDetail = new ArrayList<TraninigPlanGridFile>();
            gridDetail1 = new ArrayList<TraninigPlanGridFile>();
            gridDetail2 = new ArrayList<TraninigPlanGridFile>();
            trainingTransObj = new ArrayList<TrainingPlanData>();
            gridDetail4 = new ArrayList<TraninigPlanGridFile>();
            setOperation("0");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setOperationOnBlur() {
        if (operation.equalsIgnoreCase("0")) {
            setMessage("Please select an operation !");
            return;
        }
        if(operation.equalsIgnoreCase("2"))
        {
            gridLoadtrainingPlan();
        }
    }

    public String exitBtnAction() {
         resetForm();
         return "case1";
    }
}
