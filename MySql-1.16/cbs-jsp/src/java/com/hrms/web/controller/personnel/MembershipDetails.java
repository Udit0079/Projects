package com.hrms.web.controller.personnel;

import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMembershipDetailPKTO;
import com.hrms.common.to.HrMembershipDetailTO;

import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.exception.WebException;
import com.hrms.web.controller.personnel.PersonnelUtil;
import com.hrms.web.pojo.MembershipGrid;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class MembershipDetails {

    private static final Logger logger = Logger.getLogger(MembershipDetails.class);
    private int compCode,
            memNo,
            defaultComp,
            currentIMemRow;
    private long empCode;
    private String mode,
            message,
            statFlag,
            statUpFlag,
            professional,
            individual,
            passportNo,
            passportDate,
            accomodationType,
            insuranceNo,
            authBy,
            enteredBy,
            travOverseasStatus;
    private PersonnelDelegate personnelDelegate;
    PersonnelUtil personnelUtil;
    private boolean showMembershipGrid,
            disableAddButton,
            disableSaveButton,
            disableEditButton,
            disableDeleteButton,
            disableProfessional,
            disableIndividual,
            disablePassportNo,
            disablePassportDate,
            disableAccomodationType,
            disableInsuranceNo,
            disableTravOverseasStatus;
    private List<MembershipGrid> membershipTable;
    private MembershipGrid membershipTableItem;
    private Validator validator;

    public MembershipDetails() {
        try {
            mode = "save";
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
            logger.error("Exception occured while executing method MembershipDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method MembershipDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

 

    private boolean validate() {
        if (!validator.validateDate_dd_mm_yyyy(passportDate)) {
            setMessage("Passport Date Is In Incorrect Format. It Should Be In dd/mm/yyyy Format!!");
            return false;
        }
        return true;
    }

    public void saveMembershipDetails() {
        try {
            if (mode.equalsIgnoreCase("save")) {
                memNo = personnelDelegate.getMaxMemNo() + 1;
            } else if (mode.equalsIgnoreCase("update")) {
                memNo = membershipTableItem.getMemNo();
            }
            if (validate()) {
                String result = personnelDelegate.saveHrMembershipDetail(getHrMembershipDetailTO(), mode);
                cancelMembershipDetails();
                onLoadMode();
                setMessage(result);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveMembershipDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveMembershipDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void editMembershipDetails() {
        try {
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            compCode = (Integer) getRequest().getSession().getAttribute("COMP_CODE");
            defaultComp = (Integer) getRequest().getSession().getAttribute("DEFAULT_COMP");
            if (empCode != 0) {
                getMembershipData();
            } else {
                this.setMessage("Please Select An Employee From General Details Page");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method editMembershipDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteMembershipDetails() {
        try {
            mode = "delete";
            memNo = membershipTableItem.getMemNo();
            String result = personnelDelegate.saveHrMembershipDetail(getHrMembershipDetailTO(), mode);
            cancelMembershipDetails();
            setMessage(result);
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteMembershipDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteMembershipDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelMembershipDetails() {
        try {
            setPassportDate("");
            setPassportNo("");
            setAccomodationType("0");
            setTravOverseasStatus("0");
            setProfessional("");
            setIndividual("");
            setInsuranceNo("");
            onLoadMode();
        } catch (Exception e) {
            logger.error("Exception occured while executing method cancelMembershipDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String exitMembershipDetails() {
        try {
            cancelMembershipDetails();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitMembershipDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }

    public void setMembershipDetailsRowValues() {
        try {
            setDisablePassportNo(true);
            setDisablePassportDate(true);
            setMode("update");
            setMessage("");
            setMemNo(membershipTableItem.getMemNo());
            setProfessional(membershipTableItem.getProfMember());
            setIndividual(membershipTableItem.getIndiMember());
            setPassportNo(membershipTableItem.getPassportNo());
            setPassportDate(membershipTableItem.getPassportDate());
            setAccomodationType(membershipTableItem.getAccomodationType());
            setInsuranceNo(membershipTableItem.getInsuranceNo());
            setTravOverseasStatus(membershipTableItem.getTravelledOverseas());
            setDisableDeleteButton(false);
        } catch (Exception e) {
            logger.error("Exception occured while executing method setMembershipDetailsRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getMembershipData() {
        try {
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            membershipTable = personnelUtil.getMembershipData(empCode);
            if (!membershipTable.isEmpty()) {
                setShowMembershipGrid(true);
                setDisableSaveButton(false);
                setDisableDeleteButton(true);
                setMessage(membershipTable.size() + " rows found");
            } else {
                setShowMembershipGrid(false);
                setDisableSaveButton(false);
                setDisableDeleteButton(true);
                setMessage("No Membership Found!!");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getMembershipData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMembershipData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public HrMembershipDetailTO getHrMembershipDetailTO() {
        HrMembershipDetailTO hrMembershipDetailTO = new HrMembershipDetailTO();
        HrMembershipDetailPKTO hrMembershipDetailPKTO = new HrMembershipDetailPKTO();
        try {
            hrMembershipDetailPKTO.setCompCode(compCode);
            hrMembershipDetailPKTO.setEmpCode(empCode);
            hrMembershipDetailPKTO.setMemNo(memNo);
            hrMembershipDetailTO.setAccomdType(accomodationType);
            hrMembershipDetailTO.setAuthBy(authBy);
            hrMembershipDetailTO.setDefaultComp(defaultComp);
            hrMembershipDetailTO.setEnteredBy(enteredBy);
            hrMembershipDetailTO.setHrMembershipDetailPKTO(hrMembershipDetailPKTO);
            hrMembershipDetailTO.setHrPersonnelDetailsTO(personnelUtil.getHrPersonnelDetailsTO(compCode, empCode));
            hrMembershipDetailTO.setIndivMember(individual);
            hrMembershipDetailTO.setInsuranceNo(insuranceNo);
            hrMembershipDetailTO.setModDate(Date.class.newInstance());
            hrMembershipDetailTO.setPassportDate(passportDate);
            hrMembershipDetailTO.setPassportNo(passportNo);
            hrMembershipDetailTO.setProfMember(professional);
            hrMembershipDetailTO.setStatFlag(statFlag);
            hrMembershipDetailTO.setStatUpFlag(statUpFlag);
            hrMembershipDetailTO.setTravelOver(travOverseasStatus);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrMembershipDetailTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrMembershipDetailTO;
    }

    public void onLoadMode() {
        setMode("save");
        setMessage("");
        setDisableSaveButton(true);
        setDisableDeleteButton(true);
    }
  
    public HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public String getAccomodationType() {
        return accomodationType;
    }

    public void setAccomodationType(String accomodationType) {
        this.accomodationType = accomodationType;
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

    public int getCurrentIMemRow() {
        return currentIMemRow;
    }

    public void setCurrentIMemRow(int currentIMemRow) {
        this.currentIMemRow = currentIMemRow;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public boolean isDisableAccomodationType() {
        return disableAccomodationType;
    }

    public void setDisableAccomodationType(boolean disableAccomodationType) {
        this.disableAccomodationType = disableAccomodationType;
    }

    public boolean isDisableAddButton() {
        return disableAddButton;
    }

    public void setDisableAddButton(boolean disableAddButton) {
        this.disableAddButton = disableAddButton;
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

    public boolean isDisableIndividual() {
        return disableIndividual;
    }

    public void setDisableIndividual(boolean disableIndividual) {
        this.disableIndividual = disableIndividual;
    }

    public boolean isDisableInsuranceNo() {
        return disableInsuranceNo;
    }

    public void setDisableInsuranceNo(boolean disableInsuranceNo) {
        this.disableInsuranceNo = disableInsuranceNo;
    }

    public boolean isDisablePassportDate() {
        return disablePassportDate;
    }

    public void setDisablePassportDate(boolean disablePassportDate) {
        this.disablePassportDate = disablePassportDate;
    }

    public boolean isDisablePassportNo() {
        return disablePassportNo;
    }

    public void setDisablePassportNo(boolean disablePassportNo) {
        this.disablePassportNo = disablePassportNo;
    }

    public boolean isDisableProfessional() {
        return disableProfessional;
    }

    public void setDisableProfessional(boolean disableProfessional) {
        this.disableProfessional = disableProfessional;
    }

    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }

    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    public boolean isDisableTravOverseasStatus() {
        return disableTravOverseasStatus;
    }

    public void setDisableTravOverseasStatus(boolean disableTravOverseasStatus) {
        this.disableTravOverseasStatus = disableTravOverseasStatus;
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

    public String getIndividual() {
        return individual;
    }

    public void setIndividual(String individual) {
        this.individual = individual;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public int getMemNo() {
        return memNo;
    }

    public void setMemNo(int memNo) {
        this.memNo = memNo;
    }

    public List<MembershipGrid> getMembershipTable() {
        return membershipTable;
    }

    public void setMembershipTable(List<MembershipGrid> membershipTable) {
        this.membershipTable = membershipTable;
    }

    public MembershipGrid getMembershipTableItem() {
        return membershipTableItem;
    }

    public void setMembershipTableItem(MembershipGrid membershipTableItem) {
        this.membershipTableItem = membershipTableItem;
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

    public String getPassportDate() {
        return passportDate;
    }

    public void setPassportDate(String passportDate) {
        this.passportDate = passportDate;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
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

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public boolean isShowMembershipGrid() {
        return showMembershipGrid;
    }

    public void setShowMembershipGrid(boolean showMembershipGrid) {
        this.showMembershipGrid = showMembershipGrid;
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

    public String getTravOverseasStatus() {
        return travOverseasStatus;
    }

    public void setTravOverseasStatus(String travOverseasStatus) {
        this.travOverseasStatus = travOverseasStatus;
    }
}
