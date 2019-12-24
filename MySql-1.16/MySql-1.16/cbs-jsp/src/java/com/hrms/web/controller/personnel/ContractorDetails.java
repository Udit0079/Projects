/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.personnel;

import com.hrms.web.pojo.ContractorTable;
import com.cbs.servlets.Init;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrContractorDetailsPKTO;
import com.hrms.common.to.HrContractorDetailsTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.TransferDetailsDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import java.net.InetAddress;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.web.utils.WebUtil;

/**
 *
 * @author Zeeshan Waris
 */
public class ContractorDetails extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(ContractorDetails.class);
    private String message;
    private String contCode;
    private String firstName;
    private String midName;
    private String lastName;
    private String address;
    private String city;
    private String pin;
    private String telephone;
    private String residenaceTelephone;
    private String mobile;
    private String fax;
    private String compCode = "0";
    private String state;
    private String emailid;
    Date date = new Date();
    private List<ContractorTable> contSearch;
    private ContractorTable currentItem = new ContractorTable();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private boolean disableUpdate;
    private boolean contCodeDisable;
    private boolean disableSave;
    Validator valid = new Validator();
    private int defaultComp;

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public boolean isContCodeDisable() {
        return contCodeDisable;
    }

    public void setContCodeDisable(boolean contCodeDisable) {
        this.contCodeDisable = contCodeDisable;
    }

    public boolean isDisableUpdate() {
        return disableUpdate;
    }

    public void setDisableUpdate(boolean disableUpdate) {
        this.disableUpdate = disableUpdate;
    }

    public List<ContractorTable> getContSearch() {
        return contSearch;
    }

    public void setContSearch(List<ContractorTable> contSearch) {
        this.contSearch = contSearch;
    }

    public ContractorTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ContractorTable currentItem) {
        this.currentItem = currentItem;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContCode() {
        return contCode;
    }

    public void setContCode(String contCode) {
        this.contCode = contCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getResidenaceTelephone() {
        return residenaceTelephone;
    }

    public void setResidenaceTelephone(String residenaceTelephone) {
        this.residenaceTelephone = residenaceTelephone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public ContractorDetails() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            this.setMessage("");
            getContractorEditDetails();
            disableUpdate = true;
        } catch (Exception e) {
            logger.error("Exception occured while executing method ContractorDetails()", e);
            logger.error("ContractorDetails()", e);
        }
    }

    public void getContractorcode() {
        try {
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            String advtCodeGenerate = transferDetailsDelegate.getcontractorCode(Integer.parseInt(compCode));
            this.setContCode(advtCodeGenerate);
        } catch (WebException e) {
            logger.error("Exception occured while executing method getContractorcode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getContractorcode()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getContractorcode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getContractorEditDetails() {
        contSearch = new ArrayList<ContractorTable>();
        try {
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            List<HrContractorDetailsTO> hrContractor = transferDetailsDelegate.contractorEditList(Integer.parseInt(compCode));
            for (HrContractorDetailsTO structMasterTO : hrContractor) {
                ContractorTable rd = new ContractorTable();
                rd.setCompcode(structMasterTO.getHrContractorDetailsPKTO().getCompCode() + "");
                rd.setContcode(structMasterTO.getHrContractorDetailsPKTO().getContCode());
                if (structMasterTO.getContFirName() == null) {
                    rd.setContFirstName("");
                } else {
                    rd.setContFirstName(structMasterTO.getContFirName());
                }
                if (structMasterTO.getContMidName() == null) {
                    rd.setContMidName("");
                } else {
                    rd.setContMidName(structMasterTO.getContMidName());
                }
                if (structMasterTO.getContLastName() == null) {
                    rd.setContLastName("");
                } else {
                    rd.setContLastName(structMasterTO.getContLastName());
                }
                if (structMasterTO.getContAddress() == null) {
                    rd.setAddress("");
                } else {
                    rd.setAddress(structMasterTO.getContAddress());
                }
                if (structMasterTO.getContCity() == null) {
                    rd.setCity("");
                } else {
                    rd.setCity(structMasterTO.getContCity());
                }

                if (structMasterTO.getContPin() == null) {
                    rd.setPin("");
                } else {
                    rd.setPin(structMasterTO.getContPin());
                }
                if (structMasterTO.getContState() == null) {
                    rd.setState("");
                } else {
                    rd.setState(structMasterTO.getContState());
                }

                if (structMasterTO.getFaxNumber() == null) {
                    rd.setFax("");
                } else {
                    rd.setFax(structMasterTO.getFaxNumber());
                }
                if (structMasterTO.getMobileNumber() == null) {
                    rd.setMobileNo("");
                } else {
                    rd.setMobileNo(structMasterTO.getMobileNumber());
                }
                if (structMasterTO.getTeleNumber() == null) {
                    rd.setTel("");
                } else {
                    rd.setTel(structMasterTO.getTeleNumber());
                }
                if (structMasterTO.getResiNumber() == null) {
                    rd.setResiNo("");
                } else {
                    rd.setResiNo(structMasterTO.getResiNumber());
                }
                if (structMasterTO.getEmailNumber() == null) {
                    rd.setEmail("");
                } else {
                    rd.setEmail(structMasterTO.getEmailNumber());
                }
                contSearch.add(rd);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getContractorEditDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getContractorEditDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getContractorEditDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void SelectCurrentroewforEdit() {
        setMessage("");
        contCodeDisable = true;
        disableUpdate = false;
        disableSave = true;
        setContCode(currentItem.getContcode());
        setFirstName(currentItem.getContFirstName());
        setMidName(currentItem.getContMidName());
        setLastName(currentItem.getContLastName());
        setAddress(currentItem.getAddress());
        setCity(currentItem.getCity());
        setPin(currentItem.getPin());
        setTelephone(currentItem.getTel());
        setResidenaceTelephone(currentItem.getResiNo());
        setMobile(currentItem.getMobileNo());
        setFax(currentItem.getFax());
        setState(currentItem.getState());
        setEmailid(currentItem.getEmail());
    }

    public void deleteContractorDetailsAction() {
        setMessage("");
        try {
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            String rsmsg = transferDetailsDelegate.contractorDetailsDelete(Integer.parseInt(currentItem.getCompcode()), currentItem.getContcode());
            if (rsmsg.equalsIgnoreCase("true")) {
                setMessage("Data deleted successfuly");
            } else {
                setMessage(rsmsg);
            }
            getContractorEditDetails();
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteTemporaryStaffAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method deleteTemporaryStaffAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteTemporaryStaffAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveContractorDetailAction() {
        setMessage("");
        getContractorcode();
        try {
            if (contCode.equalsIgnoreCase("")) {
                setMessage("Please fill the Contractor Code");
                return;
            }
            if (contCode == null) {
                setMessage("Please fill the Contractor Code");
                return;
            }
            if (compCode == null) {
                setMessage("Please fill the company code");
                return;
            }
            if (compCode.equalsIgnoreCase("")) {
                setMessage("Please fill the company code");
                return;
            }
            if (firstName == null) {
                setMessage("Please fill the Contractor First Name");
                return;
            }
            if (firstName.equalsIgnoreCase("")) {
                setMessage("Please fill the Contractor First Name");
                return;
            }

            if (validations().equalsIgnoreCase("false")) {
                return;
            }
            String stateFlag = "N";
            String stateUpflag = "Y";
            HrContractorDetailsTO HrContractorDetailsTO = new HrContractorDetailsTO();
            HrContractorDetailsTO.setContFirName(firstName);
            HrContractorDetailsTO.setContMidName(midName);
            HrContractorDetailsTO.setContLastName(lastName);
            HrContractorDetailsTO.setContAddress(address);
            HrContractorDetailsTO.setContCity(city);
            HrContractorDetailsTO.setContPin(pin);
            HrContractorDetailsTO.setContState(state);
            HrContractorDetailsTO.setFaxNumber(fax);
            HrContractorDetailsTO.setMobileNumber(mobile);
            HrContractorDetailsTO.setTeleNumber(telephone);
            HrContractorDetailsTO.setResiNumber(residenaceTelephone);
            HrContractorDetailsTO.setEmailNumber(emailid);
            HrContractorDetailsTO.setDefaultComp(defaultComp);
            HrContractorDetailsTO.setStatFlag(stateFlag);
            HrContractorDetailsTO.setStatUpFlag(stateUpflag);
            HrContractorDetailsTO.setModDate(getDate());
            HrContractorDetailsTO.setAuthBy(getUserName());
            HrContractorDetailsTO.setEnteredBy(getUserName());
            HrContractorDetailsPKTO contPK = new HrContractorDetailsPKTO();
            contPK.setCompCode(Integer.parseInt(compCode));
            contPK.setContCode(contCode);
            HrContractorDetailsTO.setHrContractorDetailsPKTO(contPK);
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            String result = transferDetailsDelegate.contractorDetailsSave(HrContractorDetailsTO);
            setMessage(result+" generated contractor code is "+getContCode());
            getContractorEditDetails();
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveParameterAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveParameterAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveParameterAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void updateTemporaryStaffAction() {
        setMessage("");
        try {
            if (currentItem.getContcode().equalsIgnoreCase("")) {
                setMessage("Please fill the A /R No.");
                return;
            }
            if (currentItem.getContcode() == null) {
                setMessage("Please fill the A /R No.");
                return;
            }

            if (currentItem.getCompcode() == null) {
                setMessage("Please fill the company code");
                return;
            }
            if (currentItem.getCompcode().equalsIgnoreCase("")) {
                setMessage("Please fill the company code");
                return;
            }
            if (firstName == null) {
                setMessage("Please fill the Contractor First Name");
                return;
            }
            if (firstName.equalsIgnoreCase("")) {
                setMessage("Please fill the Contractor First Name");
                return;
            }
            if (validations().equalsIgnoreCase("false")) {
                return;
            }
            String stateFlag = "N";
            String stateUpflag = "Y";
            HrContractorDetailsTO HrContractorDetailsTO = new HrContractorDetailsTO();
            HrContractorDetailsTO.setContFirName(firstName);
            HrContractorDetailsTO.setContMidName(midName);
            HrContractorDetailsTO.setContLastName(lastName);
            HrContractorDetailsTO.setContAddress(address);
            HrContractorDetailsTO.setContCity(city);
            HrContractorDetailsTO.setContPin(pin);
            HrContractorDetailsTO.setContState(state);
            HrContractorDetailsTO.setFaxNumber(fax);
            HrContractorDetailsTO.setMobileNumber(mobile);
            HrContractorDetailsTO.setTeleNumber(telephone);
            HrContractorDetailsTO.setResiNumber(residenaceTelephone);
            HrContractorDetailsTO.setEmailNumber(emailid);
            HrContractorDetailsTO.setDefaultComp(defaultComp);
            HrContractorDetailsTO.setStatFlag(stateFlag);
            HrContractorDetailsTO.setStatUpFlag(stateUpflag);
            HrContractorDetailsTO.setModDate(getDate());
            HrContractorDetailsTO.setAuthBy(getUserName());
            HrContractorDetailsTO.setEnteredBy(getUserName());
            HrContractorDetailsPKTO contPK = new HrContractorDetailsPKTO();
            contPK.setCompCode(Integer.parseInt(currentItem.getCompcode()));
            contPK.setContCode(currentItem.getContcode());
            HrContractorDetailsTO.setHrContractorDetailsPKTO(contPK);
            TransferDetailsDelegate transferDetailsDelegate = new TransferDetailsDelegate();
            String result = transferDetailsDelegate.contractorDetailsUpdate(HrContractorDetailsTO);
            setMessage(result);
            getContractorEditDetails();
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method updateTemporaryStaffAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method updateTemporaryStaffAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method updateTemporaryStaffAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void clear() {
        setContCode("");
        setFirstName("");
        setMidName("");
        setLastName("");
        setAddress("");
        setCity("");
        setPin("");
        setTelephone("");
        setResidenaceTelephone("");
        setMobile("");
        setFax("");
        setState("");
        setEmailid("");
    }

    public void refreshButtonAction() {
        setMessage("");
        contCodeDisable = false;
        disableUpdate = true;
        disableSave = false;
        setContCode("");
        setFirstName("");
        setMidName("");
        setLastName("");
        setAddress("");
        setCity("");
        setPin("");
        setTelephone("");
        setResidenaceTelephone("");
        setMobile("");
        setFax("");
        setState("");
        setEmailid("");
    }

    public String validations() {
        try {
            if (!pin.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Pin");
                return "false";
            }
            if (!telephone.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Telephone");
                return "false";
            }
            if (!residenaceTelephone.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Resi. Tel. No.");
                return "false";
            }
            if (!mobile.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Mobile");
                return "false";
            }
            if (!fax.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Fax");
                return "false";
            }
            if (!valid.validateEmail(emailid)) {
                this.setMessage("Please Enter Valid Email Id");
                return "false";
            }
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "true";
    }

    public String btnExit() {
        try {
            refreshButtonAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "case1";
    }
}
