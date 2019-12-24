package com.hrms.web.controller.personnel;

import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrPersonnelDependentPKTO;
import com.hrms.common.to.HrPersonnelDependentTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.DependentGrid;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class DependentDetails {

    private static final Logger logger = Logger.getLogger(DependentDetails.class);
    private PersonnelDelegate personnelDelegate;
    private PersonnelUtil personnelUtil;
    private int compCode,
            defaultComp,
            currentIDepRow;
    private long empCode,
            dependentCode;
    private String mode,
            message,
            name,
            age,
            petName,
            relationship,
            occupation,
            statFlag,
            statUpFlag,
            authBy,
            enteredBy;
    private boolean disableAddButton,
            disablename,
            disablePetName,
            disableAge,
            disableRelationship,
            disableOccupation,
            showDependentGrid,
            disableSaveButton,
            disableEditButton,
            disableDeleteButton;
    private List<DependentGrid> dependentTable;
    public DependentGrid dependentGridItem = new DependentGrid();
    private Validator validator;

    public DependentDetails() {
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
            logger.error("Exception occured while executing method DependentDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method DependentDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void addAction() {
        try {
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            compCode = (Integer) getRequest().getSession().getAttribute("COMP_CODE");
            defaultComp = (Integer) getRequest().getSession().getAttribute("DEFAULT_COMP");
            mode = "save";
            if (empCode != 0) {
                setDisableAddButton(true);
                setDisableSaveButton(false);
                setDisableEditButton(true);
                setDisableDeleteButton(true);
                setMessage("Please Fill Details !!");
            } else {
                setMessage("Please Select An Employee From General Details Page !!");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method addAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    private boolean validate() {
        if (!validator.validateInteger(age)) {
            setMessage("Age Can Have Numeric Values Only !!");
            return false;
        }
        return true;
    }

    public void saveDependentDetails() {
        try {
            if (mode.equalsIgnoreCase("save")) {
                dependentCode = getMaxDependentCode() + 1;
            } else if (mode.equalsIgnoreCase("update")) {
                dependentCode = dependentGridItem.getDependentCode();
            }
            if (validate()) {
                String result = personnelDelegate.saveHrPersonnelDependent(getHrPersonnelDependentTO(), mode);
                cancelDependentDetails();
                onLoadMode();
                setMessage(result);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveDependentDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveDependentDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public HrPersonnelDependentTO getHrPersonnelDependentTO() {
        HrPersonnelDependentTO hrPersonnelDependentTO = new HrPersonnelDependentTO();
        HrPersonnelDependentPKTO hrPersonnelDependentPKTO = new HrPersonnelDependentPKTO();
        try {
            hrPersonnelDependentPKTO.setCompCode(compCode);
            hrPersonnelDependentPKTO.setEmpCode(empCode);
            hrPersonnelDependentPKTO.setDependentCode(dependentCode);
            hrPersonnelDependentTO.setAuthBy(authBy);
            hrPersonnelDependentTO.setDefaultComp(defaultComp);
            hrPersonnelDependentTO.setDepAge(Integer.parseInt(age));
            hrPersonnelDependentTO.setDepName(name);
            hrPersonnelDependentTO.setDepRel(relationship);
            hrPersonnelDependentTO.setEnteredBy(enteredBy);
            hrPersonnelDependentTO.setHrPersonnelDetailsTO((personnelUtil.getHrPersonnelDetailsTO(compCode, empCode)));
            hrPersonnelDependentTO.setHrPersonnelDependentPKTO(hrPersonnelDependentPKTO);
            hrPersonnelDependentTO.setModDate(Date.class.newInstance());
            hrPersonnelDependentTO.setOccupation(occupation);
            hrPersonnelDependentTO.setPetName(petName);
            hrPersonnelDependentTO.setStatFlag(statFlag);
            hrPersonnelDependentTO.setStatUpFlag(statUpFlag);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrPersonnelDependentTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrPersonnelDependentTO;
    }

    public void editDependentDetails() {
        try {
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            compCode = (Integer) getRequest().getSession().getAttribute("COMP_CODE");
            defaultComp = (Integer) getRequest().getSession().getAttribute("DEFAULT_COMP");
            if (empCode != 0) {
                getDependentTableData();
            } else {
                this.setMessage("Please Select An Employee From General Details Page");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method editDependentDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteDependentDetails() {
        try {
            dependentCode = dependentGridItem.getDependentCode();
            String result = personnelDelegate.saveHrPersonnelDependent(getHrPersonnelDependentTO(), "delete");
            cancelDependentDetails();
            onLoadMode();
            setMessage(result);
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteDependentDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteDependentDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelDependentDetails() {
        try {
            setName("");
            setOccupation("");
            setPetName("");
            setAge("");
            setRelationship("");
            onLoadMode();
        } catch (Exception e) {
            logger.error("Exception occured while executing method cancelDependentDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String exitDependentDetails() {
        try {
            cancelDependentDetails();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitDependentDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }

    public long getMaxDependentCode() {
        try {
            return (personnelDelegate.getMaxDependentCode());
        } catch (WebException e) {
            logger.error("Exception occured while executing method getMaxDependentCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMaxDependentCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return 0;
    }

    public void getDependentTableData() {
        try {
            dependentTable = personnelUtil.getDependentTableData(empCode);
            if (!dependentTable.isEmpty()) {
                showDependentGrid = true;
                setDisableSaveButton(false);
                setDisableDeleteButton(true);
                setMessage(dependentTable.size() + " rows found");
            } else {
                showDependentGrid = false;
                setDisableSaveButton(false);
                setDisableDeleteButton(true);
                setMessage("No Dependent Data");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setDependentRowValues() {
        try {
            setMode("update");
            setName(dependentGridItem.getName());
            setPetName(dependentGridItem.getPetName());
            setAge(String.valueOf(dependentGridItem.getAge()));
            setRelationship(dependentGridItem.getRelation());
            setOccupation(dependentGridItem.getOccupation());
            setDisableSaveButton(false);
            setDisableDeleteButton(false);
        } catch (Exception e) {
            logger.error("Exception occured while executing method setDependentRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void onLoadMode() {
        try {
            setMode("save");
            setMessage("");
           setDisableSaveButton(false);
            setDisableDeleteButton(true);
        } catch (Exception e) {
            logger.error("Exception occured while executing method onLoadMode()", e);
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

 
   public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public long getDependentCode() {
        return dependentCode;
    }

    public void setDependentCode(long dependentCode) {
        this.dependentCode = dependentCode;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
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

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
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

    public List<DependentGrid> getDependentTable() {
        return dependentTable;
    }

    public void setDependentTable(List<DependentGrid> dependentTable) {
        this.dependentTable = dependentTable;
    }

    public boolean isShowDependentGrid() {
        return showDependentGrid;
    }

    public void setShowDependentGrid(boolean showDependentGrid) {
        this.showDependentGrid = showDependentGrid;
    }

    public DependentGrid getDependentGridItem() {
        return dependentGridItem;
    }

    public void setDependentGridItem(DependentGrid dependentGridItem) {
        this.dependentGridItem = dependentGridItem;
    }

    public boolean isDisableAge() {
        return disableAge;
    }

    public void setDisableAge(boolean disableAge) {
        this.disableAge = disableAge;
    }

    public boolean isDisableOccupation() {
        return disableOccupation;
    }

    public void setDisableOccupation(boolean disableOccupation) {
        this.disableOccupation = disableOccupation;
    }

    public boolean isDisablePetName() {
        return disablePetName;
    }

    public void setDisablePetName(boolean disablePetName) {
        this.disablePetName = disablePetName;
    }

    public boolean isDisableRelationship() {
        return disableRelationship;
    }

    public void setDisableRelationship(boolean disableRelationship) {
        this.disableRelationship = disableRelationship;
    }

    public boolean isDisablename() {
        return disablename;
    }

    public void setDisablename(boolean disablename) {
        this.disablename = disablename;
    }

    public int getCurrentIDepRow() {
        return currentIDepRow;
    }

    public void setCurrentIDepRow(int currentIDepRow) {
        this.currentIDepRow = currentIDepRow;
    }

    public boolean isDisableAddButton() {
        return disableAddButton;
    }

    public void setDisableAddButton(boolean disableAddButton) {
        this.disableAddButton = disableAddButton;
    }
}
