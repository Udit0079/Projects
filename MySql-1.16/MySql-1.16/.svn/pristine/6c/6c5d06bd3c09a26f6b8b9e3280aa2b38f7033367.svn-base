package com.hrms.web.controller.personnel;

import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrPersonnelQualificationPKTO;
import com.hrms.common.to.HrPersonnelQualificationTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.exception.WebException;
import com.hrms.web.controller.personnel.PersonnelUtil;
import com.hrms.web.pojo.QualificationGrid;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class Qualifications {

    private static final Logger logger = Logger.getLogger(DependentDetails.class);
    private PersonnelDelegate personnelDelegate;
    private int compCode,
            defaultComp,
            qualificationsIRow;
    private long empCode;
    private String mode,
            message,
            inst_board,
            year,
            subjects,
            specialisation,
            marks,
            division,
            exam_course,
            authBy,
            enteredBy,
            duration,
            statFlag,
            statUpFlag;
    private HttpServletRequest request;
    private boolean disableAddButton,
            showQualificationGrid,
            disableSaveButton,
            disableEditButton,
            disableDeleteButton,
            disableExamCourse,
            disableInstBoard,
            disableYear,
            disableSubjects,
            disableSpecialization,
            disableMarks,
            disableDivision,
            disableDuration;
    private List<QualificationGrid> qualificationsTable;
    private QualificationGrid qualificationsTableItem = new QualificationGrid();
    private PersonnelUtil personnelUtil;
    private Validator validator;

    public Qualifications() {
        try {
            statFlag = "Y";
            statUpFlag = "Y";
            personnelDelegate = new PersonnelDelegate();
            personnelUtil = new PersonnelUtil();
            request = getRequest();
            String user = request.getServerName();
            onLoadMode();
            authBy = user;
            enteredBy = user;
            validator = new Validator();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method Qualifications()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method Qualifications()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    private boolean validate() {
        if (!validator.validateInteger(year)) {
            setMessage("Year Can Have Numeric Values Only !!");
            return false;
        }
        if (!validator.validateDoublePositive(marks)) {
            setMessage("Marks Can Have Numeric Values Only !!");
            return false;
        }
        if (!validator.validateDoublePositive(duration)) {
            setMessage("Duration Can Have Numeric Values Only !!");
            return false;
        }
        return true;
    }

    public void saveQualificationsDetails() {
        try {
            if (validate()) {
                String result = personnelDelegate.saveHrPersonnelQualification(getHrPersonnelQualificationTO(), mode);
                cancelQualificationsDetails();
                onLoadMode();
                setMessage(result);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveQualificationsDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveQualificationsDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void editQualificationsDetails() {
        try {
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            compCode = (Integer) getRequest().getSession().getAttribute("COMP_CODE");
            defaultComp = (Integer) getRequest().getSession().getAttribute("DEFAULT_COMP");
            if (empCode != 0) {
                getQualificationsTableData();
            } else {
                this.setMessage("Please Select An Employee From General Details Page");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method editQualificationsDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteQualificationsDetails() {
        mode = "delete";
        HrPersonnelQualificationTO hrPersonnelQualificationTO = new HrPersonnelQualificationTO();
        HrPersonnelQualificationPKTO hrPersonnelQualificationPKTO = new HrPersonnelQualificationPKTO();
        try {
            hrPersonnelQualificationPKTO.setCompCode(compCode);
            hrPersonnelQualificationPKTO.setEmpCode(empCode);
            hrPersonnelQualificationPKTO.setQualCode(exam_course);
            hrPersonnelQualificationTO.setHrPersonnelQualificationPKTO(hrPersonnelQualificationPKTO);
            hrPersonnelQualificationTO.setHrPersonnelDetailsTO(personnelUtil.getHrPersonnelDetailsTO(compCode, empCode));
            String result = personnelDelegate.saveHrPersonnelQualification(hrPersonnelQualificationTO, mode);
            cancelQualificationsDetails();
            onLoadMode();
            setMessage(result);
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteQualificationsDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteQualificationsDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelQualificationsDetails() {
        try {
            setMode("save");
            setMessage("");
            setDivision("");
            setDuration("");
            setExam_course("");
            setInst_board("");
            setMarks("");
            setSpecialisation("");
            setSubjects("");
            setYear("");
            showQualificationGrid = false;
            onLoadMode();
        } catch (Exception e) {
            logger.error("Exception occured while executing method cancelQualificationsDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String exitQualificationsDetails() {
        try {
            cancelQualificationsDetails();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitQualificationsDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }

    public void getQualificationsTableData() {
        try {
            qualificationsTable = personnelUtil.getQualificationTableData(empCode);
            if (!qualificationsTable.isEmpty()) {
                showQualificationGrid = true;
                setDisableSaveButton(false);
                setDisableDeleteButton(true);
                setMessage(qualificationsTable.size() + " rows found");
            } else {
                showQualificationGrid = false;
                setDisableSaveButton(false);
                setDisableDeleteButton(true);
                setMessage("No Qualification Data");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getQualificationsTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getQualificationsTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setQualificationRowValues() {
        try {
            setMode("update");
            setExam_course(qualificationsTableItem.getQualificationCode());
            setInst_board(qualificationsTableItem.getInstituteName());
            setYear(String.valueOf(qualificationsTableItem.getYear()));
            setSubjects(qualificationsTableItem.getSubjectsName());
            setSpecialisation(qualificationsTableItem.getSpecializationCode());
            setMarks(String.valueOf(qualificationsTableItem.getMarks()));
            setDivision(qualificationsTableItem.getDivision());
            setDuration(String.valueOf(qualificationsTableItem.getDuration()));
            setDisableSaveButton(false);
            setDisableDeleteButton(false);
        } catch (Exception e) {
            logger.error("Exception occured while executing method setQualificationRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public void onLoadMode() {
        setMode("save");
        setMessage("");
        showQualificationGrid = false;
        setDisableSaveButton(false);
        setDisableDeleteButton(true);
    }

    public HrPersonnelQualificationTO getHrPersonnelQualificationTO() {
        HrPersonnelQualificationTO hrPersonnelQualificationTO = new HrPersonnelQualificationTO();
        HrPersonnelQualificationPKTO hrPersonnelQualificationPKTO = new HrPersonnelQualificationPKTO();
        try {
            hrPersonnelQualificationPKTO.setCompCode(compCode);
            hrPersonnelQualificationPKTO.setEmpCode(empCode);
            hrPersonnelQualificationPKTO.setQualCode(exam_course);
            hrPersonnelQualificationTO.setAuthBy(authBy);
            hrPersonnelQualificationTO.setDefaultComp(defaultComp);
            hrPersonnelQualificationTO.setDivision(division);
            hrPersonnelQualificationTO.setDuration(Double.parseDouble(duration));
            hrPersonnelQualificationTO.setEnteredBy(enteredBy);
            hrPersonnelQualificationTO.setHrPersonnelDetailsTO((personnelUtil.getHrPersonnelDetailsTO(compCode, empCode)));
            hrPersonnelQualificationTO.setHrPersonnelQualificationPKTO(hrPersonnelQualificationPKTO);
            hrPersonnelQualificationTO.setInstituteName(inst_board);
            hrPersonnelQualificationTO.setModDate(Date.class.newInstance());
            hrPersonnelQualificationTO.setPercentageMarks(Double.parseDouble(marks));
            hrPersonnelQualificationTO.setSpecializationCode(specialisation);
            hrPersonnelQualificationTO.setStatFlag(statFlag);
            hrPersonnelQualificationTO.setStatUpFlag(statUpFlag);
            hrPersonnelQualificationTO.setSubName(subjects);
            hrPersonnelQualificationTO.setYear(Integer.parseInt(year));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrPersonnelQualificationTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrPersonnelQualificationTO;
    }

    public PersonnelUtil getPersonnelUtil() {
        return personnelUtil;
    }

    public void setPersonnelUtil(PersonnelUtil personnelUtil) {
        this.personnelUtil = personnelUtil;
    }

    public List<QualificationGrid> getQualificationsTable() {
        return qualificationsTable;
    }

    public void setQualificationsTable(List<QualificationGrid> qualificationsTable) {
        this.qualificationsTable = qualificationsTable;
    }

    public QualificationGrid getQualificationsTableItem() {
        return qualificationsTableItem;
    }

    public void setQualificationsTableItem(QualificationGrid qualificationsTableItem) {
        this.qualificationsTableItem = qualificationsTableItem;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getExam_course() {
        return exam_course;
    }

    public void setExam_course(String exam_course) {
        this.exam_course = exam_course;
    }

    public String getInst_board() {
        return inst_board;
    }

    public void setInst_board(String inst_board) {
        this.inst_board = inst_board;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PersonnelDelegate getPersonnelDelegate() {
        return personnelDelegate;
    }

    public void setPersonnelDelegate(PersonnelDelegate personnelDelegate) {
        this.personnelDelegate = personnelDelegate;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
    }

    public String getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(String statUpFlag) {
        this.statUpFlag = statUpFlag;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isDisableDeleteButton() {
        return disableDeleteButton;
    }

    public void setDisableDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
    }

    public boolean isDisableDivision() {
        return disableDivision;
    }

    public void setDisableDivision(boolean disableDivision) {
        this.disableDivision = disableDivision;
    }

    public boolean isDisableDuration() {
        return disableDuration;
    }

    public void setDisableDuration(boolean disableDuration) {
        this.disableDuration = disableDuration;
    }

    public boolean isDisableEditButton() {
        return disableEditButton;
    }

    public void setDisableEditButton(boolean disableEditButton) {
        this.disableEditButton = disableEditButton;
    }

    public boolean isDisableExamCourse() {
        return disableExamCourse;
    }

    public void setDisableExamCourse(boolean disableExamCourse) {
        this.disableExamCourse = disableExamCourse;
    }

    public boolean isDisableInstBoard() {
        return disableInstBoard;
    }

    public void setDisableInstBoard(boolean disableInstBoard) {
        this.disableInstBoard = disableInstBoard;
    }

    public int getQualificationsIRow() {
        return qualificationsIRow;
    }

    public void setQualificationsIRow(int qualificationsIRow) {
        this.qualificationsIRow = qualificationsIRow;
    }

    public boolean isDisableMarks() {
        return disableMarks;
    }

    public void setDisableMarks(boolean disableMarks) {
        this.disableMarks = disableMarks;
    }

    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }

    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    public boolean isDisableSpecialization() {
        return disableSpecialization;
    }

    public void setDisableSpecialization(boolean disableSpecialization) {
        this.disableSpecialization = disableSpecialization;
    }

    public boolean isDisableSubjects() {
        return disableSubjects;
    }

    public void setDisableSubjects(boolean disableSubjects) {
        this.disableSubjects = disableSubjects;
    }

    public boolean isDisableYear() {
        return disableYear;
    }

    public void setDisableYear(boolean disableYear) {
        this.disableYear = disableYear;
    }

    public boolean isShowQualificationGrid() {
        return showQualificationGrid;
    }

    public void setShowQualificationGrid(boolean showQualificationGrid) {
        this.showQualificationGrid = showQualificationGrid;
    }

    public boolean isDisableAddButton() {
        return disableAddButton;
    }

    public void setDisableAddButton(boolean disableAddButton) {
        this.disableAddButton = disableAddButton;
    }
}
