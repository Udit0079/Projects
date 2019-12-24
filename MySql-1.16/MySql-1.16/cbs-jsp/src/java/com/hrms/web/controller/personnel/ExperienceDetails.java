package com.hrms.web.controller.personnel;

import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrPersonnelPreviousCompanyPKTO;
import com.hrms.common.to.HrPersonnelPreviousCompanyTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.exception.WebException;
import com.hrms.web.controller.personnel.PersonnelUtil;
import com.hrms.web.pojo.PreviousCompanyGrid;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

public class ExperienceDetails {

    private static final Logger logger = Logger.getLogger(ExperienceDetails.class);
    private PersonnelDelegate personnelDelegate;
    PersonnelUtil personnelUtil;
    private int compCode,
            defaultComp,
            currentIExpRow;
    private long empCode,
            prevCompCode;
    private String mode,
            message,
            statFlag,
            statUpFlag,
            annualTurnOver,
            prevCompAddress,
            reasonOfLeaving,
            durationFrom,
            durationTo,
            designationOnJoining,
            designationOnLeaving,
            strengthUnder,
            strengthTotal,
            preCompName,
            authBy,
            enteredBy;
    private Double salaryOnJoining,
            salaryOnLeaving;
    private boolean showExperienceGrid,
            disableAddButton,
            disableSaveButton,
            disableEditButton,
            disableDeleteButton,
            disablePrevCOmpName,
            disableAnnualTurnOver,
            disablePrevCompAddress,
            disablereasonOfLeaving,
            disableDurationFrom,
            disableDurationTo,
            disableDesignationOnJoining,
            disableDesignationOnLeaving,
            disableStrengthUnder,
            disableStrengthTotal,
            disableSalaryOnJoining,
            disableSalaryOnLeaving;
    private List<PreviousCompanyGrid> previousCompanyTable;
    private PreviousCompanyGrid previousCompanyTableItem;
    private Validator validator;

    public ExperienceDetails() {
        try {
            statFlag = "Y";
            statUpFlag = "Y";
            personnelDelegate = new PersonnelDelegate();
            personnelUtil = new PersonnelUtil();
            String user = (String) getRequest().getSession().getAttribute("USER_NAME");
            authBy = user;
            enteredBy = user;
            onLoadMode();
            validator = new Validator();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method ExperienceDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method ExperienceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

  
    private boolean validate() {
        if (!validator.validateInteger(strengthTotal)) {
            setMessage("Strength Total Can Have Numeric Values Only !!");
            return false;
        }
        if (!validator.validateInteger(strengthUnder)) {
            setMessage("Strength Under Can Have Numeric Values Only !!");
            return false;
        }
        if (!validator.validateDoublePositive(annualTurnOver)) {
            setMessage("Annual Turn Over Can Have Numeric Values Only !!");
            return false;
        }
        if (!validator.validateDate_dd_mm_yyyy(durationFrom)) {
            setMessage("Duration From Is In Incorrect Format. It Should Be In dd/mm/yyyy Format!!");
            return false;
        }
        if (!validator.validateDate_dd_mm_yyyy(durationTo)) {
            setMessage("Duration To Is In Incorrect Format. It Should Be In dd/mm/yyyy Format!!");
            return false;
        }
        if (!validator.validateDoublePositive(String.valueOf(salaryOnJoining))) {
            setMessage("Salary On Joining Can Have Numeric Values Only !!");
            return false;
        }
        if (!validator.validateDoublePositive(String.valueOf(salaryOnLeaving))) {
            setMessage("Salary On Leaving Can Have Numeric Values Only !!");
            return false;
        }
        return true;
    }

    public void saveExperienceDetails() {
        try {
            if (mode.equalsIgnoreCase("save")) {
                prevCompCode = personnelDelegate.getMaxPrevCompCode() + 1;
            } else if (mode.equalsIgnoreCase("update")) {
                prevCompCode = previousCompanyTableItem.getPrevCompCode();
            }
            if (validate()) {
                String result = personnelDelegate.saveHrPersonnelPreviousCompany(getHrPersonnelPreviousCompanyTO(), mode);
                cancelExperienceDetails();
                setMessage(result);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveExperienceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveExperienceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void editExperienceDetails() {
        try {
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            compCode = (Integer) getRequest().getSession().getAttribute("COMP_CODE");
            defaultComp = (Integer) getRequest().getSession().getAttribute("DEFAULT_COMP");
            if (empCode != 0) {
                getExperienceData();
            } else {
                this.setMessage("Please Select An Employee From General Details Page");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method editExperienceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteExperienceDetails() {
        try {
            mode = "delete";
            prevCompCode = previousCompanyTableItem.getPrevCompCode();
            String result = personnelDelegate.saveHrPersonnelPreviousCompany(getHrPersonnelPreviousCompanyTO(), mode);
            cancelExperienceDetails();
            setMessage(result);
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteExperienceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteExperienceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelExperienceDetails() {
        try {
            onLoadMode();
            setPreCompName("");
            setPrevCompAddress("");
            setAnnualTurnOver("");
            setReasonOfLeaving("");
            setDurationFrom("");
            setDurationTo("");
            setDesignationOnJoining("0");
            setDesignationOnLeaving("0");
            setStrengthUnder("");
            setStrengthTotal("");
            setSalaryOnJoining(null);
            setSalaryOnLeaving(null);
        } catch (Exception e) {
            logger.error("Exception occured while executing method cancelExperienceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String exitExperienceDetails() {
        try {
            cancelExperienceDetails();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitGeneralDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }

    public void onLoadMode() {
        setMode("save");
        setMessage("");
        setDisableSaveButton(false);
        setDisableDeleteButton(true);
    }

    public HrPersonnelPreviousCompanyTO getHrPersonnelPreviousCompanyTO() {
        empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
        compCode = (Integer) getRequest().getSession().getAttribute("COMP_CODE");
        if (durationFrom == null || durationFrom.equalsIgnoreCase("")) {
            setDurationFrom("01/01/1900");
        }
        if (durationTo == null || durationTo.equalsIgnoreCase("")) {
            setDurationTo("01/01/1900");
        }
        if (strengthUnder == null || durationTo.equalsIgnoreCase("")) {
            setStrengthUnder("0");
        }
        if (strengthTotal == null || strengthTotal.equalsIgnoreCase("")) {
            setStrengthTotal("0");
        }

        HrPersonnelPreviousCompanyTO hrPersonnelPreviousCompanyTO = new HrPersonnelPreviousCompanyTO();
        HrPersonnelPreviousCompanyPKTO hrPersonnelPreviousCompanyPKTO = new HrPersonnelPreviousCompanyPKTO();
        try {
            hrPersonnelPreviousCompanyPKTO.setCompCode(compCode);
            hrPersonnelPreviousCompanyPKTO.setPrevCompCode(prevCompCode);
            hrPersonnelPreviousCompanyPKTO.setEmpCode(empCode);
            hrPersonnelPreviousCompanyPKTO.setDurFrom(personnelUtil.dmyFormat.parse(durationFrom));
            hrPersonnelPreviousCompanyPKTO.setDurTo(personnelUtil.dmyFormat.parse(durationTo));
            hrPersonnelPreviousCompanyTO.setHrPersonnelPreviousCompanyPKTO(hrPersonnelPreviousCompanyPKTO);
            hrPersonnelPreviousCompanyTO.setHrPersonnelDetailsTO(personnelUtil.getHrPersonnelDetailsTO(compCode, empCode));
            hrPersonnelPreviousCompanyTO.setAnnualTurn(Double.parseDouble(annualTurnOver));
            hrPersonnelPreviousCompanyTO.setAuthBy(authBy);
            hrPersonnelPreviousCompanyTO.setCompAdd(prevCompAddress);
            hrPersonnelPreviousCompanyTO.setCompName(preCompName);
            hrPersonnelPreviousCompanyTO.setDefaultComp(defaultComp);
            hrPersonnelPreviousCompanyTO.setDesgJoin(designationOnJoining);
            hrPersonnelPreviousCompanyTO.setDesgLeave(designationOnLeaving);
            hrPersonnelPreviousCompanyTO.setEmpUnder(Integer.parseInt(strengthUnder));
            hrPersonnelPreviousCompanyTO.setEnteredBy(enteredBy);
            hrPersonnelPreviousCompanyTO.setModDate(Date.class.newInstance());
            hrPersonnelPreviousCompanyTO.setReasonLeaving(reasonOfLeaving);
            hrPersonnelPreviousCompanyTO.setSalJoin(salaryOnJoining);
            hrPersonnelPreviousCompanyTO.setSalLeave(salaryOnLeaving);
            hrPersonnelPreviousCompanyTO.setStatFlag(statFlag);
            hrPersonnelPreviousCompanyTO.setStatUpFlag(statUpFlag);
            hrPersonnelPreviousCompanyTO.setTotEmp(Integer.parseInt(strengthTotal));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrPersonnelPreviousCompanyTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrPersonnelPreviousCompanyTO;
    }

    public void setExperienceDetailsRowValues() {
        try {
            setMode("update");
            setMessage("");
            setPreCompName(previousCompanyTableItem.getPreCompName());
            setPrevCompAddress(previousCompanyTableItem.getPrevCompAddress());
            setAnnualTurnOver(previousCompanyTableItem.getAnnualTurnOver());
            setReasonOfLeaving(previousCompanyTableItem.getReasonOfLeaving());
            setDurationFrom(previousCompanyTableItem.getDurationFrom());
            setDurationTo(previousCompanyTableItem.getDurationTo());
            setDesignationOnJoining(previousCompanyTableItem.getDesignationOnJoining());
            setDesignationOnLeaving(previousCompanyTableItem.getDesignationOnLeaving());
            setStrengthUnder(previousCompanyTableItem.getStrengthUnder());
            setStrengthTotal(previousCompanyTableItem.getStrengthTotal());
            setSalaryOnJoining(previousCompanyTableItem.getSalaryOnJoining());
            setSalaryOnLeaving(previousCompanyTableItem.getSalaryOnLeaving());
            setDisableDeleteButton(false);
        } catch (Exception e) {
            logger.error("Exception occured while executing method setExperienceDetailsRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getExperienceData() {
        try {
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            previousCompanyTable = personnelUtil.getExperienceData(empCode);
            if (!previousCompanyTable.isEmpty()) {
                showExperienceGrid = true;
                setDisableSaveButton(false);
                setDisableDeleteButton(true);
                setMessage(previousCompanyTable.size() + " rows found");
            } else {
                showExperienceGrid = false;
                setDisableSaveButton(false);
                setDisableDeleteButton(true);
                setMessage("No Experience Found!!");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getExperienceData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getExperienceData()", e);
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

    public String getAnnualTurnOver() {
        return annualTurnOver;
    }

    public void setAnnualTurnOver(String annualTurnOver) {
        this.annualTurnOver = annualTurnOver;
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

    public String getDesignationOnJoining() {
        return designationOnJoining;
    }

    public void setDesignationOnJoining(String designationOnJoining) {
        this.designationOnJoining = designationOnJoining;
    }

    public String getDesignationOnLeaving() {
        return designationOnLeaving;
    }

    public void setDesignationOnLeaving(String designationOnLeaving) {
        this.designationOnLeaving = designationOnLeaving;
    }

    public boolean isDisableDeleteButton() {
        return disableDeleteButton;
    }

    public void setDisableDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
    }

    public boolean isDisableEditButton() {
        return disableEditButton;
    }

    public void setDisableEditButton(boolean disableEditButton) {
        this.disableEditButton = disableEditButton;
    }

    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }

    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    public String getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(String durationFrom) {
        this.durationFrom = durationFrom;
    }

    public String getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(String durationTo) {
        this.durationTo = durationTo;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public PersonnelDelegate getPersonnelDelegate() {
        return personnelDelegate;
    }

    public void setPersonnelDelegate(PersonnelDelegate personnelDelegate) {
        this.personnelDelegate = personnelDelegate;
    }

    public PersonnelUtil getPersonnelUtil() {
        return personnelUtil;
    }

    public void setPersonnelUtil(PersonnelUtil personnelUtil) {
        this.personnelUtil = personnelUtil;
    }

    public String getPreCompName() {
        return preCompName;
    }

    public void setPreCompName(String preCompName) {
        this.preCompName = preCompName;
    }

    public String getPrevCompAddress() {
        return prevCompAddress;
    }

    public void setPrevCompAddress(String prevCompAddress) {
        this.prevCompAddress = prevCompAddress;
    }

    public long getPrevCompCode() {
        return prevCompCode;
    }

    public void setPrevCompCode(long prevCompCode) {
        this.prevCompCode = prevCompCode;
    }

    public String getReasonOfLeaving() {
        return reasonOfLeaving;
    }

    public void setReasonOfLeaving(String reasonOfLeaving) {
        this.reasonOfLeaving = reasonOfLeaving;
    }

    public Double getSalaryOnJoining() {
        return salaryOnJoining;
    }

    public void setSalaryOnJoining(Double salaryOnJoining) {
        this.salaryOnJoining = salaryOnJoining;
    }

    public Double getSalaryOnLeaving() {
        return salaryOnLeaving;
    }

    public void setSalaryOnLeaving(Double salaryOnLeaving) {
        this.salaryOnLeaving = salaryOnLeaving;
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

    public String getStrengthTotal() {
        return strengthTotal;
    }

    public void setStrengthTotal(String strengthTotal) {
        this.strengthTotal = strengthTotal;
    }

    public String getStrengthUnder() {
        return strengthUnder;
    }

    public void setStrengthUnder(String strengthUnder) {
        this.strengthUnder = strengthUnder;
    }

    public boolean isDisableAnnualTurnOver() {
        return disableAnnualTurnOver;
    }

    public void setDisableAnnualTurnOver(boolean disableAnnualTurnOver) {
        this.disableAnnualTurnOver = disableAnnualTurnOver;
    }

    public boolean isDisableDesignationOnJoining() {
        return disableDesignationOnJoining;
    }

    public void setDisableDesignationOnJoining(boolean disableDesignationOnJoining) {
        this.disableDesignationOnJoining = disableDesignationOnJoining;
    }

    public boolean isDisableDesignationOnLeaving() {
        return disableDesignationOnLeaving;
    }

    public void setDisableDesignationOnLeaving(boolean disableDesignationOnLeaving) {
        this.disableDesignationOnLeaving = disableDesignationOnLeaving;
    }

    public boolean isDisableDurationFrom() {
        return disableDurationFrom;
    }

    public void setDisableDurationFrom(boolean disableDurationFrom) {
        this.disableDurationFrom = disableDurationFrom;
    }

    public boolean isDisableDurationTo() {
        return disableDurationTo;
    }

    public void setDisableDurationTo(boolean disableDurationTo) {
        this.disableDurationTo = disableDurationTo;
    }

    public boolean isDisablePrevCOmpName() {
        return disablePrevCOmpName;
    }

    public void setDisablePrevCOmpName(boolean disablePrevCOmpName) {
        this.disablePrevCOmpName = disablePrevCOmpName;
    }

    public boolean isDisablePrevCompAddress() {
        return disablePrevCompAddress;
    }

    public void setDisablePrevCompAddress(boolean disablePrevCompAddress) {
        this.disablePrevCompAddress = disablePrevCompAddress;
    }

    public boolean isDisableSalaryOnJoining() {
        return disableSalaryOnJoining;
    }

    public void setDisableSalaryOnJoining(boolean disableSalaryOnJoining) {
        this.disableSalaryOnJoining = disableSalaryOnJoining;
    }

    public boolean isDisableSalaryOnLeaving() {
        return disableSalaryOnLeaving;
    }

    public void setDisableSalaryOnLeaving(boolean disableSalaryOnLeaving) {
        this.disableSalaryOnLeaving = disableSalaryOnLeaving;
    }

    public boolean isDisableStrengthTotal() {
        return disableStrengthTotal;
    }

    public void setDisableStrengthTotal(boolean disableStrengthTotal) {
        this.disableStrengthTotal = disableStrengthTotal;
    }

    public boolean isDisableStrengthUnder() {
        return disableStrengthUnder;
    }

    public void setDisableStrengthUnder(boolean disableStrengthUnder) {
        this.disableStrengthUnder = disableStrengthUnder;
    }

    public boolean isDisablereasonOfLeaving() {
        return disablereasonOfLeaving;
    }

    public void setDisablereasonOfLeaving(boolean disablereasonOfLeaving) {
        this.disablereasonOfLeaving = disablereasonOfLeaving;
    }

    public int getCurrentIExpRow() {
        return currentIExpRow;
    }

    public void setCurrentIExpRow(int currentIExpRow) {
        this.currentIExpRow = currentIExpRow;
    }

    public List<PreviousCompanyGrid> getPreviousCompanyTable() {
        return previousCompanyTable;
    }

    public void setPreviousCompanyTable(List<PreviousCompanyGrid> previousCompanyTable) {
        this.previousCompanyTable = previousCompanyTable;
    }

    public PreviousCompanyGrid getPreviousCompanyTableItem() {
        return previousCompanyTableItem;
    }

    public void setPreviousCompanyTableItem(PreviousCompanyGrid previousCompanyTableItem) {
        this.previousCompanyTableItem = previousCompanyTableItem;
    }

    public boolean isShowExperienceGrid() {
        return showExperienceGrid;
    }

    public void setShowExperienceGrid(boolean showExperienceGrid) {
        this.showExperienceGrid = showExperienceGrid;
    }

    public boolean isDisableAddButton() {
        return disableAddButton;
    }

    public void setDisableAddButton(boolean disableAddButton) {
        this.disableAddButton = disableAddButton;
    }
}
