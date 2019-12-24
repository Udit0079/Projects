package com.hrms.web.controller.personnel;

import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrPersonnelReferencePKTO;
import com.hrms.common.to.HrPersonnelReferenceTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.ReferenceGrid;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class ReferenceDetails  {

    private static final Logger logger = Logger.getLogger(ReferenceDetails.class);
    private long empCode, refCode, currentIRefRow;
    private int compCode, defaultComp;
    private String mode, message, name, address, city, state, pin, phone, occupation, statFlag, statUpFlag, enteredBy, authBy;
    protected PersonnelDelegate personnelDelegate;
    private List<ReferenceGrid> referenceGrids;
    ReferenceGrid currentRefItem = new ReferenceGrid();
    private PersonnelUtil personnelUtil;
    private boolean disableAddButton, showreferenceGrid, disableSaveButton, disableEditButton, disableDeleteButton, disableRefName, disableRefAddress, disableRefCity, disableRefState, disableRefPin, disableRefPhone, disableRefOccupation;
    protected HttpServletRequest request;
    private Validator validator;

    public ReferenceDetails() {
        try {
            personnelDelegate = new PersonnelDelegate();
            statFlag = "Y";
            statUpFlag = "N";
            String user = (String) getRequest().getSession().getAttribute("USER_NAME");
            authBy = user;
            enteredBy = user;
            personnelUtil = new PersonnelUtil();
            request = getRequest();
            onLoadMode();
            validator = new Validator();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method ReferenceDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method ReferenceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public boolean validate() {
        if (!validator.validateInteger(pin)) {
            setMessage("Pin Can Have Numeric Values Only !!");
            return false;
        }
        if (!validator.validateInteger(phone)) {
            setMessage("Phone Can Have Numeric Values Only !!");
            return false;
        }
        return true;
    }

    public void saveReferenceDetails() {
        try {
            if (mode.equalsIgnoreCase("save")) {
                refCode = getMaxRefCode() + 1;
                
            } else if (mode.equalsIgnoreCase("update")) {
                refCode = currentRefItem.getRefCode();
            }
            if (validate()) {
                String result = personnelDelegate.saveHrPersonnelReference(getHrPersonnelReferenceTO(), mode);
                cancelReferenceDetails();
                setMessage(result);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveReferenceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveReferenceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void editReferenceDetails() {
        try {
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            compCode = (Integer) getRequest().getSession().getAttribute("COMP_CODE");
            defaultComp = (Integer) getRequest().getSession().getAttribute("DEFAULT_COMP");
            if (empCode != 0) {
                getReferenceTableData();
            } else {
                this.setMessage("Please Select An Employee From General Details Page");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method editReferenceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteReferenceDetails() {
        try {
            refCode = currentRefItem.getRefCode();
            String result = personnelDelegate.saveHrPersonnelReference(getHrPersonnelReferenceTO(), "delete");
            cancelReferenceDetails();
            onLoadMode();
            setMessage(result);
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteReferenceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteReferenceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelReferenceDetails() {
        try {
            setMode("save");
            setMessage("");
            setName("");
            setAddress("");
            setCity("");
            setState("");
            setPin("");
            setPhone("");
            setOccupation("");
            onLoadMode();
        } catch (Exception e) {
            logger.error("Exception occured while executing method cancelReferenceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String exitReferenceDetails() {
        try {
            cancelReferenceDetails();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitReferenceDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }

    public HrPersonnelReferenceTO getHrPersonnelReferenceTO() {
        try {
            HrPersonnelReferenceTO hrPersonnelReferenceTO = new HrPersonnelReferenceTO();
            HrPersonnelReferencePKTO hrPersonnelReferencePKTO = new HrPersonnelReferencePKTO();
            hrPersonnelReferencePKTO.setCompCode(compCode);
            hrPersonnelReferencePKTO.setEmpCode(empCode);
            hrPersonnelReferencePKTO.setRefCode(refCode);
            hrPersonnelReferenceTO.setHrPersonnelReferencePKTO(hrPersonnelReferencePKTO);
            hrPersonnelReferenceTO.setHrPersonnelDetailsTO(personnelUtil.getHrPersonnelDetailsTO(compCode, empCode));
            hrPersonnelReferenceTO.setAuthBy(authBy);
            hrPersonnelReferenceTO.setDefaultComp(defaultComp);
            hrPersonnelReferenceTO.setEnteredBy(enteredBy);
            hrPersonnelReferenceTO.setModDate(Date.class.newInstance());
            hrPersonnelReferenceTO.setRefAdd(address);
            hrPersonnelReferenceTO.setRefCity(city);
            hrPersonnelReferenceTO.setRefName(name);
            hrPersonnelReferenceTO.setRefOcc(occupation);
            hrPersonnelReferenceTO.setRefPhone(phone);
            hrPersonnelReferenceTO.setRefPin(pin);
            hrPersonnelReferenceTO.setRefState(state);
            hrPersonnelReferenceTO.setStatFlag(statFlag);
            hrPersonnelReferenceTO.setStatUpFlag(statUpFlag);
            return hrPersonnelReferenceTO;
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrPersonnelReferenceTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return null;
    }

    public long getMaxRefCode() {
        try {
            return (personnelDelegate.getMaxRefCode());
        } catch (WebException e) {
            logger.error("Exception occured while executing method getMaxRefCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMaxRefCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return 0;
    }

    public void getReferenceTableData() {
        try {
            referenceGrids = personnelUtil.getReferenceTableData(empCode);
            if (!referenceGrids.isEmpty()) {
                showreferenceGrid = true;
                setDisableSaveButton(false);
                setMessage(referenceGrids.size() + " rows found");
            } else {
                showreferenceGrid = false;
              //  setDisableSaveButton(true);
                 setDisableDeleteButton(true);
                setMessage("No Reference Data");
            }

        } catch (WebException e) {
            logger.error("Exception occured while executing method getReferenceTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getReferenceTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setReferenceRowValues() {
        try {
            setMode("update");
            setMessage("");
            setName(currentRefItem.getName());
            setAddress(currentRefItem.getAddress());
            setCity(currentRefItem.getCity());
            setState(currentRefItem.getState());
            setPin(currentRefItem.getPin());
            setPhone(currentRefItem.getPhone());
            setOccupation(currentRefItem.getOccupation());
            setDisableSaveButton(false);
            setDisableDeleteButton(false);
        } catch (Exception e) {
            logger.error("Exception occured while executing method setReferenceRowValues()", e);
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
        setDisableSaveButton(false);
        setDisableDeleteButton(true);
    }

   
    /**
     * Getters and Setters
     *
     *
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public long getCurrentIRefRow() {
        return currentIRefRow;
    }

    public void setCurrentIRefRow(long currentIRefRow) {
        this.currentIRefRow = currentIRefRow;
    }

    public ReferenceGrid getCurrentRefItem() {
        return currentRefItem;
    }

    public void setCurrentRefItem(ReferenceGrid currentRefItem) {
        this.currentRefItem = currentRefItem;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
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

    public boolean isDisableRefAddress() {
        return disableRefAddress;
    }

    public void setDisableRefAddress(boolean disableRefAddress) {
        this.disableRefAddress = disableRefAddress;
    }

    public boolean isDisableRefCity() {
        return disableRefCity;
    }

    public void setDisableRefCity(boolean disableRefCity) {
        this.disableRefCity = disableRefCity;
    }

    public boolean isDisableRefName() {
        return disableRefName;
    }

    public void setDisableRefName(boolean disableRefName) {
        this.disableRefName = disableRefName;
    }

    public boolean isDisableRefOccupation() {
        return disableRefOccupation;
    }

    public void setDisableRefOccupation(boolean disableRefOccupation) {
        this.disableRefOccupation = disableRefOccupation;
    }

    public boolean isDisableRefPhone() {
        return disableRefPhone;
    }

    public void setDisableRefPhone(boolean disableRefPhone) {
        this.disableRefPhone = disableRefPhone;
    }

    public boolean isDisableRefPin() {
        return disableRefPin;
    }

    public void setDisableRefPin(boolean disableRefPin) {
        this.disableRefPin = disableRefPin;
    }

    public boolean isDisableRefState() {
        return disableRefState;
    }

    public void setDisableRefState(boolean disableRefState) {
        this.disableRefState = disableRefState;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public long getRefCode() {
        return refCode;
    }

    public void setRefCode(long refCode) {
        this.refCode = refCode;
    }

    public List<ReferenceGrid> getReferenceGrids() {
        return referenceGrids;
    }

    public void setReferenceGrids(List<ReferenceGrid> referenceGrids) {
        this.referenceGrids = referenceGrids;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isShowreferenceGrid() {
        return showreferenceGrid;
    }

    public void setShowreferenceGrid(boolean showreferenceGrid) {
        this.showreferenceGrid = showreferenceGrid;
    }

    public boolean isDisableAddButton() {
        return disableAddButton;
    }

    public void setDisableAddButton(boolean disableAddButton) {
        this.disableAddButton = disableAddButton;
    }
}
