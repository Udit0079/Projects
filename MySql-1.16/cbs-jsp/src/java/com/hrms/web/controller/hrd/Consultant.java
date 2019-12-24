/*
Document   : consultant
Created on : 27 May, 2011, 3:19:01 PM
Author     : Zeeshan Waris
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.ConstutantTable;
import com.cbs.servlets.Init;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrConsultantPKTO;
import com.hrms.common.to.HrConsultantTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.RecruitmentDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import java.net.InetAddress;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zeeshan Waris
 */
public class Consultant extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(Consultant.class);
    private String message;
    private String code;
    private String frstName;
    private String midName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String pin;
    private String telephoneNo;
    private String faxNo;
    private String mobileNo;
    private String contactPerson;
    private String emailId;
    private String areaOfExpertise;
    private String commission;
    private String compCode="0";
    private HttpServletRequest req;
    private List<SelectItem> areaOfExpertiseList;
    private List<ConstutantTable> instrReg;
    private int currentRow;
    private ConstutantTable currentItem = new ConstutantTable();
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private int companycode;
    private String consCode;
    private String conName;
    private boolean disableUpdate;
    private boolean disableCode;
    private boolean disableSave;
    private String consultantName;
    private String message1;
    private String mode;
    Validator valid = new Validator();
    private int defaultComp;
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

    
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public boolean isDisableCode() {
        return disableCode;
    }

    public void setDisableCode(boolean disableCode) {
        this.disableCode = disableCode;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public boolean isDisableUpdate() {
        return disableUpdate;
    }

    public void setDisableUpdate(boolean disableUpdate) {
        this.disableUpdate = disableUpdate;
    }

    public int getCompanycode() {
        return companycode;
    }

    public void setCompanycode(int companycode) {
        this.companycode = companycode;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConsCode() {
        return consCode;
    }

    public void setConsCode(String consCode) {
        this.consCode = consCode;
    }

    public ConstutantTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ConstutantTable currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<ConstutantTable> getInstrReg() {
        return instrReg;
    }

    public void setInstrReg(List<ConstutantTable> instrReg) {
        this.instrReg = instrReg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaOfExpertise() {
        return areaOfExpertise;
    }

    public void setAreaOfExpertise(String areaOfExpertise) {
        this.areaOfExpertise = areaOfExpertise;
    }

    public List<SelectItem> getAreaOfExpertiseList() {
        return areaOfExpertiseList;
    }

    public void setAreaOfExpertiseList(List<SelectItem> areaOfExpertiseList) {
        this.areaOfExpertiseList = areaOfExpertiseList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getFrstName() {
        return frstName;
    }

    public void setFrstName(String frstName) {
        this.frstName = frstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    
    public Consultant() {

        try {
            compCode = getOrgnBrCode();
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","---Select---"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","View"));
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            instrReg = new ArrayList<ConstutantTable>();
            setTodayDate(sdf.format(date));
            this.setMessage("");
            getAreaOfExpertiseDropdownList();
            setMode("Save");
        } catch (Exception e) {
            logger.error("Exception occured while executing method Consultant()", e);
            logger.error("Consultant()", e);
        }
    }

    public void getAreaOfExpertiseDropdownList() {
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            List<HrMstStructTO> structMasterTOs = recruitmentDelegate.getInitialData(Integer.parseInt(compCode));
            areaOfExpertiseList = new ArrayList<SelectItem>();
            areaOfExpertiseList.add(new SelectItem("0", "--Select--"));
            for (HrMstStructTO structMasterTO : structMasterTOs) {
                areaOfExpertiseList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                        structMasterTO.getDescription()));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getAreaOfExpertiseDropdownList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getAreaOfExpertiseDropdownList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAreaOfExpertiseDropdownList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getTableDetails() {
        setMessage1("");
        instrReg = new ArrayList<ConstutantTable>();
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            List<HrConsultantTO> hrConsultantTOs = recruitmentDelegate.getTableDetails(Integer.parseInt(compCode), consultantName);
            for (HrConsultantTO hrConsultantTO : hrConsultantTOs) {
                ConstutantTable cd = new ConstutantTable();
                cd.setComCode(hrConsultantTO.getHrConsultantPKTO().getCompCode());
                cd.setConsCode(hrConsultantTO.getHrConsultantPKTO().getConsCode());
                cd.setName(hrConsultantTO.getConsFirName());
                instrReg.add(cd);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getTableDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void consultantDetails() {
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            List<HrConsultantTO> hrConsultantTOs = recruitmentDelegate.getConsultantsDetails(companycode, consCode, conName);
            for (HrConsultantTO hrConsultantTO : hrConsultantTOs) {
                setCode(hrConsultantTO.getHrConsultantPKTO().getConsCode());
                setFrstName(hrConsultantTO.getConsFirName());
                setMidName(hrConsultantTO.getConsMidName());
                setLastName(hrConsultantTO.getConsLastName());
                setAddress(hrConsultantTO.getConsAdd());
                setCity(hrConsultantTO.getConsCity());
                setPin(hrConsultantTO.getConsPin());
                setState(hrConsultantTO.getConsState());
                setFaxNo(hrConsultantTO.getFaxNumber());
                setMobileNo(hrConsultantTO.getMobileNumber());
                setTelephoneNo(hrConsultantTO.getTeleNumber());
                setContactPerson(hrConsultantTO.getContPerson());
                setEmailId(hrConsultantTO.getEmail());
                setAreaOfExpertise(hrConsultantTO.getContDesg());
                setCommission(hrConsultantTO.getConsFee().toString());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method consultantDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method consultantDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method consultantDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ConsCode"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (ConstutantTable item : instrReg) {
            if (item.getName().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void select() {
        mode = "Update";
        disableUpdate = true;
        //disableSave=true;
        disableCode = true;
        setCompanycode(0);
        setConsCode("");
        setConName("");
        setCompanycode(currentItem.getComCode());
        setConsCode(currentItem.getConsCode());
        setConName(currentItem.getName());
        consultantDetails();
    }

    public void clear() {
        disableUpdate = true;
        //disableSave=false;
        disableCode = false;
        setConsultantName("");
        setCode("");
        setFrstName("");
        setMidName("");
        setLastName("");
        setAddress("");
        setCity("");
        setPin("");
        setState("");
        setFaxNo("");
        setMobileNo("");
        setTelephoneNo("");
        setContactPerson("");
        setEmailId("");
        setAreaOfExpertise("");
        setCommission("");
        setOperation("0");
    }

    public void refresh() {
        mode = "Save";
        disableUpdate = false;
        //disableSave=false;
        disableCode = false;
        setConsultantName("");
        setMessage("");
        setCode("");
        setFrstName("");
        setMidName("");
        setLastName("");
        setAddress("");
        setCity("");
        setPin("");
        setState("");
        setFaxNo("");
        setMobileNo("");
        setTelephoneNo("");
        setContactPerson("");
        setEmailId("");
        setAreaOfExpertise("");
        setCommission("");
        instrReg = new ArrayList<ConstutantTable>();
    }

    public void closePanel() {
        setMessage1("");
        setConsultantName("");
        instrReg = new ArrayList<ConstutantTable>();
    }

    public void saveAction() {
        if (mode.equalsIgnoreCase("Save")) {
            saveConsultantAction();
        } else if (mode.equalsIgnoreCase("Update")) {
            updateconsultantAction();
        }
    }

    public void updateconsultantAction() {
        try {
            if (validations().equalsIgnoreCase("false")) {
                return;
            }
            String stateFlag = "Y";
            String stateUpflag = "Y";
            HrConsultantTO consultant = new HrConsultantTO();
            consultant.setConsFirName(frstName);
            consultant.setConsMidName(midName);
            consultant.setConsLastName(lastName);
            consultant.setConsAdd(address);
            consultant.setConsCity(city);
            consultant.setConsPin(pin);
            consultant.setConsState(state);
            consultant.setFaxNumber(faxNo);
            consultant.setMobileNumber(mobileNo);
            consultant.setTeleNumber(telephoneNo);
            consultant.setContPerson(contactPerson);
            consultant.setEmail(emailId);
            consultant.setContDesg(areaOfExpertise);
            consultant.setConsFee(Double.parseDouble(commission));
            consultant.setDefaultComp(defaultComp);
            consultant.setStatFlag(stateFlag);
            consultant.setStatUpFlag(stateUpflag);
            consultant.setDtModDate(getDate());
            consultant.setAuthBy(getUserName());
            consultant.setEnteredBy(getUserName());
            HrConsultantPKTO consultantPK = new HrConsultantPKTO();
            consultantPK.setCompCode(currentItem.getComCode());
            consultantPK.setConsCode(currentItem.getConsCode());
            consultant.setHrConsultantPKTO(consultantPK);
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            String result = recruitmentDelegate.updateConsultant(consultant);
            setMessage(result);
            clear();
            // getTableDetails();
        } catch (WebException e) {
            logger.error("Exception occured while executing method updateconsultantAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method updateconsultantAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method updateconsultantAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteConsultantAction() {
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            String rsmsg = recruitmentDelegate.ConsultantDelete(currentItem.getComCode(), currentItem.getConsCode());
            if (rsmsg.equalsIgnoreCase("true")) {
                setMessage("Data deleted successfuly");
            } else {
                setMessage(rsmsg);
            }
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteConsultantAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method deleteConsultantAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteConsultantAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveConsultantAction() {
        setMessage("");
        try {
            if (compCode.equalsIgnoreCase("") || compCode == null) {
                setMessage("please fill the company code");
                return;
            }
            if (code.equalsIgnoreCase("") || code == null) {
                setMessage("please fill the code");
                return;
            }
            if (frstName.equalsIgnoreCase("") || frstName == null) {
                setMessage("please fill the first Name");
                return;
            }
            if (validations().equalsIgnoreCase("false")) {
                return;
            }
            String stateFlag = "Y";
            String stateUpflag = "Y";
            HrConsultantTO consultant = new HrConsultantTO();
            consultant.setConsFirName(frstName);
            consultant.setConsMidName(midName);
            consultant.setConsLastName(lastName);
            consultant.setConsAdd(address);
            consultant.setConsCity(city);
            consultant.setConsPin(pin);
            consultant.setConsState(state);
            consultant.setFaxNumber(faxNo);
            consultant.setMobileNumber(mobileNo);
            consultant.setTeleNumber(telephoneNo);
            consultant.setContPerson(contactPerson);
            consultant.setEmail(emailId);
            consultant.setContDesg(areaOfExpertise);
            consultant.setConsFee(Double.parseDouble(commission));
            consultant.setDefaultComp(defaultComp);
            consultant.setStatFlag(stateFlag);
            consultant.setStatUpFlag(stateUpflag);
            consultant.setDtModDate(getDate());
            consultant.setAuthBy(getUserName());
            consultant.setEnteredBy(getUserName());
            HrConsultantPKTO consultantPK = new HrConsultantPKTO();
            consultantPK.setCompCode(Integer.parseInt(compCode));
            consultantPK.setConsCode(code);
            consultant.setHrConsultantPKTO(consultantPK);
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            String result = recruitmentDelegate.saveConsultant(consultant);
            setMessage(result);
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveConsultantAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveConsultantAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveConsultantAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String validations() {
        try {
            if (code.equalsIgnoreCase("")) {
                this.setMessage("Please fill Code");
                return "false";
            }
            if (code == null) {
                this.setMessage("Please fill Code");
                return "false";
            }
            if (frstName.equalsIgnoreCase("")) {
                this.setMessage("Please fill first Name");
                return "false";
            }
            if (frstName == null) {
                this.setMessage("Please fill first Name");
                return "false";
            }
            if (areaOfExpertise.equalsIgnoreCase("0")) {
                this.setMessage("Please select Area of Expertise");
                return "false";
            }
            if (pin.equalsIgnoreCase("") || pin == null) {
                this.setMessage("Please Fill Pin code");
                return "false";
            } else {
                if (pin.length() < 6) {
                    this.setMessage("Pin code can't be less than 6 char");
                    return "false";
                }
                if (!pin.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Pin code");
                    return "false";
                }
            }
            if (!telephoneNo.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in telephone No");
                return "false";
            }
            if (!mobileNo.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in telephone No");
                return "false";
            }
            if (!faxNo.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Fax No");
                return "false";
            }
            if (!valid.validateDoublePositive(commission)) {
                this.setMessage("Please Enter Numeric Value in Commission");
                return "false";
            }
            if (!valid.validateEmail(emailId)) {
                this.setMessage("Please Enter Valid Email Id");
                return "false";
            }
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "true";
    }
    public void setOperationOnBlur()
    {
        setMessage("");
        if(operation.equalsIgnoreCase("0"))
        {
            setMessage("Please select an operation !");
            return;
        }
        else if(operation.equalsIgnoreCase("2"))
        {
            closePanel();
        }
    }

    public String btnExit() {
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "case1";
    }
}
