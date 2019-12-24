/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.inventory.InventorySplitAndMergeFacadeRemote;
import com.cbs.facade.report.CommonReportMethods;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

@SuppressWarnings("UseOfObsoleteCollectionType")
public class BankLeadManagement extends BaseBean {

    private String function;
    private List<SelectItem> functionList;
    private boolean isCompanyDisable;
    private boolean isAccountNameDisable;
    private boolean isFristNAmeDisable;
    private boolean isEmailDisable;
    private boolean isMobileDisable;
    private boolean isbtnSave;
    private boolean isCustomerId;
    private boolean isAccountNo;
    private boolean isGST;
    private boolean isPan;
    private boolean isIFSC;
    private boolean isLastName;
    private String message;
    private String custId;
    private String bankName;
    private String bankIfsc;
    private String bankAccoNo;
    private String pan;
    private String businessGst;
    private String companyName;
    private String AccountName;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String businessCate;
    private String businessType;
    private String btnChangeName;
    private String othVisible = "none";
    private CommonReportMethodsRemote commonReportMethodsRemote;
    private InventorySplitAndMergeFacadeRemote inventorySplitAndMergeFacadeRemote;
    private List<SelectItem> businessCategoryList;
    private List<SelectItem> businessTypeList;
    private List<AirPayRecord> custBusinessDataList;
    private AirPayRecord currentAirPayRecord;
    private Pattern pattern;
    private Matcher matcher;
    private boolean checkDataValid;
    private String inValidMessage;
    private Validator validator;
    private String flag;

    public BankLeadManagement() throws ApplicationException {
        validator = new Validator();
        functionList = new ArrayList();
        functionList.add(new SelectItem("0", "--Select--"));
        functionList.add(new SelectItem("1", "Add"));
        functionList.add(new SelectItem("2", "Modify"));
        functionList.add(new SelectItem("3", "Verify"));
        setOthVisible("none;");
        this.isCompanyDisable = true;
        this.isAccountNameDisable = true;
        this.isEmailDisable = true;
        this.isFristNAmeDisable = true;
        this.businessTypeList = new Vector<>();
        this.businessCategoryList = new Vector<>();
        this.isbtnSave = true;

        try {

            /**
             * Create a instance of CommonReportMethods
             */
            commonReportMethodsRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            inventorySplitAndMergeFacadeRemote = (InventorySplitAndMergeFacadeRemote) ServiceLocator.getInstance().lookup("InventorySplitAndMergeFacade");
            //txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup("TransactionManagementFacade");
            /**
             * Create a List instance and fetch the all Business Category List
             */
            List businessCates = commonReportMethodsRemote.getRefRecList("450");
            if (!businessCates.isEmpty()) {
                businessCategoryList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < businessCates.size(); i++) {
                    Vector vector = (Vector) businessCates.get(i);
                    businessCategoryList.add(new SelectItem(vector.get(0).toString(), vector.get(1).toString())); //get the businessCatefory
                }
            }

            /**
             * Create a List instance and fetch the all Business Types List
             */
            List businessTypess = commonReportMethodsRemote.getRefRecList("451");
            if (!businessCates.isEmpty()) {
                businessTypeList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < businessTypess.size(); i++) {
                    Vector vector = (Vector) businessTypess.get(i);
                    businessTypeList.add(new SelectItem(vector.get(0).toString(), vector.get(1).toString())); //get the businesstype
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public boolean isIsCompanyDisable() {
        return isCompanyDisable;
    }

    public void setIsCompanyDisable(boolean isCompanyDisable) {
        this.isCompanyDisable = isCompanyDisable;
    }

    public boolean isIsAccountNameDisable() {
        return isAccountNameDisable;
    }

    public void setIsAccountNameDisable(boolean isAccountNameDisable) {
        this.isAccountNameDisable = isAccountNameDisable;
    }

    public boolean isIsFristNAmeDisable() {
        return isFristNAmeDisable;
    }

    public void setIsFristNAmeDisable(boolean isFristNAmeDisable) {
        this.isFristNAmeDisable = isFristNAmeDisable;
    }

    public boolean isIsEmailDisable() {
        return isEmailDisable;
    }

    public void setIsEmailDisable(boolean isEmailDisable) {
        this.isEmailDisable = isEmailDisable;
    }

    public boolean isIsMobileDisable() {
        return isMobileDisable;
    }

    public void setIsMobileDisable(boolean isMobileDisable) {
        this.isMobileDisable = isMobileDisable;
    }

    public String getMessage() {
        return message;
    }

    public boolean isIsbtnSave() {
        return isbtnSave;
    }

    public String getBtnChangeName() {
        return btnChangeName;
    }

    public void setBtnChangeName(String btnChangeName) {
        this.btnChangeName = btnChangeName;
    }

    public void setIsbtnSave(boolean isbtnSave) {
        this.isbtnSave = isbtnSave;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankIfsc() {
        return bankIfsc;
    }

    public void setBankIfsc(String bankIfsc) {
        this.bankIfsc = bankIfsc;
    }

    public String getBankAccoNo() {
        return bankAccoNo;
    }

    public void setBankAccoNo(String bankAccoNo) {
        this.bankAccoNo = bankAccoNo;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getBusinessGst() {
        return businessGst;
    }

    public void setBusinessGst(String businessGst) {
        this.businessGst = businessGst;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        this.AccountName = accountName;
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

    public String getEmail() {
        return email;
    }

    public CommonReportMethodsRemote getCommonReportMethodsRemote() {
        return commonReportMethodsRemote;
    }

    public void setCommonReportMethods(CommonReportMethodsRemote commonReportMethods) {
        this.commonReportMethodsRemote = commonReportMethods;
    }

    public InventorySplitAndMergeFacadeRemote getInventorySplitAndMergeFacadeRemote() {
        return inventorySplitAndMergeFacadeRemote;
    }

    public void setInventorySplitAndMergeFacadeRemote(InventorySplitAndMergeFacadeRemote inventorySplitAndMergeFacadeRemote) {
        this.inventorySplitAndMergeFacadeRemote = inventorySplitAndMergeFacadeRemote;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBusinessCate() {
        return businessCate;
    }

    public void setBusinessCate(String businessCate) {
        this.businessCate = businessCate;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public void setCommonReportMethods(CommonReportMethods commonReportMethods) {
        this.commonReportMethodsRemote = commonReportMethods;
    }

    public String getOthVisible() {
        return othVisible;
    }

    public void setOthVisible(String othVisible) {
        this.othVisible = othVisible;
    }

    public List<AirPayRecord> getCustBusinessDataList() {
        return custBusinessDataList;
    }

    public void setCustBusinessDataList(List<AirPayRecord> custBusinessDataList) {
        this.custBusinessDataList = custBusinessDataList;
    }

    public AirPayRecord getCurrentAirPayRecord() {
        return currentAirPayRecord;
    }

    public void setCurrentAirPayRecord(AirPayRecord currentAirPayRecord) {
        this.currentAirPayRecord = currentAirPayRecord;
    }

    public boolean isIsCustomerId() {
        return isCustomerId;
    }

    public void setIsCustomerId(boolean isCustomerId) {
        this.isCustomerId = isCustomerId;
    }

    public boolean isIsAccountNo() {
        return isAccountNo;
    }

    public void setIsAccountNo(boolean isAccountNo) {
        this.isAccountNo = isAccountNo;
    }

    public boolean isIsGST() {
        return isGST;
    }

    public void setIsGST(boolean isGST) {
        this.isGST = isGST;
    }

    public boolean isIsPan() {
        return isPan;
    }

    public void setIsPan(boolean isPan) {
        this.isPan = isPan;
    }

    public boolean isIsIFSC() {
        return isIFSC;
    }

    public void setIsIFSC(boolean isIFSC) {
        this.isIFSC = isIFSC;
    }

    public boolean isIsLastName() {
        return isLastName;
    }

    public void setIsLastName(boolean isLastName) {
        this.isLastName = isLastName;
    }

    public String getInValidMessage() {
        return inValidMessage;
    }

    public void setInValidMessage(String inValidMessage) {
        this.inValidMessage = inValidMessage;
    }

    /**
     * Selected Operation
     */
    public void setFnOption() throws ApplicationException {
        switch (function) {
            case "0": //select case
                disableFiled();
                setBtnChangeName(null);
                setBtnChangeName("");
                setOthVisible("none;");
                resetOnFunction();
                break;
            case "1": //add case
                editableFiled();
                setOthVisible("none;");
                resetOnFunction();
                setBtnChangeName("Add");
                break;
            case "2": //modify case
                editableFiled();
                setOthVisible("");
                resetOnFunction();
                setBtnChangeName("Modify");
                setIsCustomerId(true);
                fetchLeadData();
                break;
            case "3"://verify case
                disableFiled();
                setOthVisible("");
                resetOnFunction();
                setBtnChangeName("Verify");
                fetchLeadData();
                setIsCustomerId(true);
                setIsbtnSave(false);
                break;
        }
    }

    public void fetchLeadData() throws ApplicationException {
        try {
            List list = inventorySplitAndMergeFacadeRemote.getAddAndModifyData();
            if (!list.isEmpty()) {
                //Create a Customer Business Data List instance
                custBusinessDataList = new ArrayList();
                //System.out.println(list);
                for (int i = 0; i < list.size(); i++) {
                    Vector vector = (Vector) list.get(i);
                    AirPayRecord obj = new AirPayRecord();
                    obj.setCustomerId(vector.get(0).toString());
                    obj.setCompanyName(vector.get(1).toString());
                    obj.setFirstName(vector.get(3).toString());
                    obj.setLastName(vector.get(4).toString());
                    obj.setEmail(vector.get(5).toString());
                    obj.setMobile(vector.get(6).toString());
                    obj.setBusinessCatId(vector.get(8).toString());
                    obj.setBusinessCatDescription(vector.get(21).toString());
                    obj.setBusinessTypeId(vector.get(9).toString());
                    obj.setBusinessTypeDescription(vector.get(22).toString());
                    obj.setBankName(vector.get(10).toString());
                    obj.setBankIfsc(vector.get(11).toString());
                    obj.setBankAccountNo(vector.get(12).toString());
                    obj.setBankPanNo(vector.get(14).toString());
                    obj.setBusinessGSt(vector.get(15).toString());
                    obj.setUsername(vector.get(19).toString());

                    custBusinessDataList.add(obj);
                }
            } else {
                setMessage("Customer Record Doesn't Exist");
                custBusinessDataList = new ArrayList<>();
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    /**
     * enable Method Functionality
     */
    public void editableFiled() {
        setIsCustomerId(false);
        setIsIFSC(false);
        setIsAccountNo(false);
        setIsPan(false);
        setIsGST(false);
        setIsAccountNameDisable(false);
        setIsCompanyDisable(false);
        setIsEmailDisable(false);
        setIsFristNAmeDisable(false);
        setIsLastName(false);
        setIsMobileDisable(false);
        setIsbtnSave(false);
        setIsCustomerId(false);
    }

    /**
     * Disable Method Functionality
     */
    public void disableFiled() {
        setIsLastName(true);
        setIsIFSC(true);
        setIsAccountNo(true);
        setIsPan(true);
        setIsGST(true);
        setIsAccountNameDisable(true);
        setIsCompanyDisable(true);
        setIsEmailDisable(true);
        setIsFristNAmeDisable(true);
        setIsMobileDisable(true);
        setIsbtnSave(true);
    }

    /**
     * get the Bank Business Details, Name, IFSC, Account No. and GST No.
     */
    public void bankBusinessDetails() throws ApplicationException {
        /**
         * Validate the customer -ID
         */
        checkDataValid = true;

        if (isValidCustomerId(getCustId())) {
            try {
                setMessage("");
                List list = inventorySplitAndMergeFacadeRemote.getBusinessDetails(getCustId()); //fetching customer business detailes 
                if (list.isEmpty()) {
                    setMessage("Record Not Found");
                } else {
                    Vector vector = (Vector) list.get(0);
                    setBankName(vector.get(0).toString());
                    setBankIfsc(vector.get(1).toString());
                    setBankAccoNo(vector.get(2).toString());
                    setCustId(vector.get(3).toString());
                    setPan(vector.get(4).toString());
                    setBusinessGst(vector.get(5).toString());
                    setCompanyName(vector.get(6).toString());
                    setAccountName(vector.get(6).toString());
                }
            } catch (Exception ex) {
//                throw new ApplicationException(ex.getMessage());
                this.setMessage(ex.getMessage());
            }
        }
    }

    /**
     * save data to the database
     */
    public void save() throws Exception {
        /**
         * save company Name Account No. FristName, LastName, email and mobile
         */
        try {
            checkDataValid = true;
            setMessage("");
            setInValidMessage("");
            //validate the data
            isValidCustomerId(custId);
            if (checkDataValid) {
                isValidBankAccountNo();
            } else {
                setMessage(getInValidMessage());
            }
            if (checkDataValid) {
                validateBankIfsc();
            } else {
                setMessage(getInValidMessage());
            }
            if (checkDataValid) {
                validateBankAccountNumber();
            } else {
                setMessage(getInValidMessage());
            }
            if (checkDataValid) {
                validateBusinessPANNumber();
            } else {
                setMessage(getInValidMessage());
            }
            if (checkDataValid) {
                validateBusinessGSTNumber();
            } else {
                setMessage(getInValidMessage());
            }
            if (checkDataValid) {
                validateCompanyName();
            } else {
                setMessage(getInValidMessage());
            }
            if (checkDataValid) {
                validateAccountName();
            } else {
                setMessage(getInValidMessage());
            }
            if (checkDataValid) {
                validateBusinessCat();
            } else {
                setMessage(getInValidMessage());
            }
            if (checkDataValid) {
                validateBusinessType();
            } else {
                setMessage(getInValidMessage());
            }
            if (checkDataValid) {
                validateFirstName();
            } else {
                setMessage(getInValidMessage());
            }
            if (checkDataValid) {
                validateLastName();
            } else {
                setMessage(getInValidMessage());
            }
            if (checkDataValid) {
                validateEmail();
            } else {
                setMessage(getInValidMessage());
            }
            if (checkDataValid) {
                validateMobilNumber();
            } else {
                setMessage(getInValidMessage());
            }

            switch (function) {
                case "1": //Add operation
                    if (checkDataValid) {
                        setFlag("A");
                        //save data
                        int i = addData(custId, bankName, bankIfsc, bankAccoNo, pan, businessGst, companyName,
                                AccountName, firstName, lastName, email, mobile);
                        if (i != 0) {
                            resetOnModifyAndVerify();
                            setMessage("Record Sccessfully Insert");

                        } else {
                            setMessage("Record Not Insert Sccessfully ");
                        }
                    } else {
                        setMessage(getInValidMessage());
                    }
                    break;
                case "2"://Modify operation
                    if (checkDataValid) {
                        setFlag("M");
                        if (modifyOperation() != 0) {
                            resetOnModifyAndVerify();
                            fetchLeadData();
                            setMessage("Successfully Modify");
                        } else {
                            setMessage("Not Successfully Modify");
                        }
                    } else {
//                        setMessage("Please select Status.");
                    }

                    break;
                case "3": //Verify operation
                    if (checkDataValid) {
                        setFlag("V");
                        if (verifyOperation() != 0) {
                            resetOnModifyAndVerify();
                            fetchLeadData();
                            setMessage("Successfully Verify");
                        } else {
                            setMessage("NotSuccessfully Verify");
                        }
                    } else {
//                        setMessage("Please select Status.");
                    }
                    break; //not Necessary
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    //get A flag
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * add customer Business related data
     */
    private int addData(String custId, String bankName, String bankIfsc, String bankAccoNo, String pan, String businessGst,
            String companyName, String AccountName, String firstName, String lastName, String email,
            String mobile) throws ApplicationException {
        int insertInfo = 0;
        try {
            insertInfo = inventorySplitAndMergeFacadeRemote.addCustomerBusinessData(custId, bankName, bankIfsc.toUpperCase(), bankAccoNo,
                    pan, businessGst, companyName, AccountName, firstName, lastName, email, mobile, getUserName(),
                    businessCate, businessType, getFlag());
        } catch (Exception ex) {
            //System.out.println("Testing : " + ex.getMessage());
            throw new ApplicationException(ex.getMessage());
        }
        return insertInfo;
    }

    /**
     * modify customer Business related data
     */
    private int modifyOperation() throws ApplicationException {
        int modifyNotification = 0;
        try {
            //add the current table
            modifyNotification = inventorySplitAndMergeFacadeRemote.updateCustomerById(getCustId(), getBankName(), getBankIfsc(), getBankAccoNo(), getPan(),
                    getBusinessGst(), getCompanyName(), getCompanyName(), getFirstName(), getLastName(), getEmail(),
                    getMobile(), getUserName(), getBusinessCate(), getBusinessType(), getFlag());

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

        return modifyNotification;
    }

    /**
     * Verify Operation
     */
    private int verifyOperation() throws ApplicationException {
        int notifiation = 0;
        try {
            notifiation = inventorySplitAndMergeFacadeRemote.verfiyCustomerBusinessData(getCustId(), getFlag(), getUserName());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return notifiation;
    }

    public void resetOnFunction() {
        setMessage("");
        setCustId("");
        setBankName("");
        setBankIfsc("");
        setAccountName("");
        setBusinessGst("");
        setCompanyName("");
        setFirstName("");
        setLastName("");
        setEmail("");
        setMobile("");
        setBankAccoNo("");
        setPan("");
        setBusinessCate("0");
        setBusinessType("0");
        custBusinessDataList = new ArrayList<>();
        currentAirPayRecord = null;
    }

    public void resetOnModifyAndVerify() {
        setCustId("");
        setBankName("");
        setBankIfsc("");
        setAccountName("");
        setBusinessGst("");
        setCompanyName("");
        setFirstName("");
        setLastName("");
        setEmail("");
        setMobile("");
        setBankAccoNo("");
        setPan("");
        setBusinessCate("0");
        setBusinessType("0");
    }

    public void resetForm() {
        setMessage("");
        setFunction("0");
        setCustId("");
        setBankName("");
        setBankIfsc("");
        setAccountName("");
        setBusinessGst("");
        setCompanyName("");
        setFirstName("");
        setLastName("");
        setEmail("");
        setMobile("");
        setBankAccoNo("");
        setPan("");
        setBusinessCate("0");
        setBusinessType("0");
        custBusinessDataList = new ArrayList<>();
        currentAirPayRecord = null;
    }

    public String exitFrm() {
        resetForm();
        return "case1";
    }

    /**
     * Get All Business CategoryList
     */
    public List<SelectItem> getBusinessCategoryList() {
        return businessCategoryList;
    }

    /**
     * Get All Business Type List
     */
    public List<SelectItem> getBusinessTypeList() {
        return businessTypeList;
    }

    /**
     * Get the CustomerInfo according to company Name, Account No., business
     * Category and BusinessTypeID
     */
    public void getCustomerInfo() {
    }

    /**
     * validate the Customer id
     */
    private boolean isValidCustomerId(String customerId) {
        if (customerId.trim().length() != 0) {
            pattern = Pattern.compile("[\\d]{1,10}");
            matcher = pattern.matcher(customerId.trim());
            if (!matcher.matches()) {
                setMessage("Only Digit Allowed");
                setInValidMessage("Only Digit Allowed");
                checkDataValid = false;
            }

        } else {
            setMessage("Customer Id Is Mandatory");
            setInValidMessage("Customer Id Is Mandatory");
            checkDataValid = false;
        }
        return checkDataValid;
    }

    private boolean isValidBankAccountNo() {
        if (this.custId != null && !custId.trim().equals("") && custId.trim().length() != 0) {
            if (this.bankAccoNo == null || this.bankAccoNo.trim().equals("")) {
                setMessage("Bank account no can not be blank.");
                setInValidMessage("Bank account no can not be blank.");
                checkDataValid = false;
            }
        }
        return checkDataValid;
    }

    /**
     * validate the Bank IFSC
     */
    public void validateBankIfsc() {
        switch (getBankIfsc().length()) {
            case 0:
//                setMessage("Bank IFSC Field Is Blank");
//                setInValidMessage("Bank IFSC Field Is Blank");
                checkDataValid = true;
                break;
            case 11:
                pattern = Pattern.compile("[\\d\\w]{11}");
                matcher = pattern.matcher(bankIfsc.trim());
                if (!matcher.matches()) {
                    setMessage("InValid Bank IFSC.");
                    setInValidMessage("InValid Bank IFSC.");
                    checkDataValid = false;
                } else {
                    setMessage("");
                    setInValidMessage("");
                    checkDataValid = true;
                }
                break;
            default:
                setMessage("Too Short Bank IFSC Code, Please check Your Bank IFSC Code");
                setInValidMessage("Too Short Bank IFSC Code, Please check Your Bank IFSC Code");
                checkDataValid = false;
        }
    }

    /**
     * validate the Bank Account Number
     */
    public void validateBankAccountNumber() {

        switch (getBankAccoNo().length()) {
            case 0:
//                setMessage("Bank Account Field Is Blank");
//                setInValidMessage("Bank Account Field Is Blank");
                checkDataValid = true;
                break;
            case 12:
                pattern = Pattern.compile("[\\d]{12}");
                matcher = pattern.matcher(bankAccoNo.trim());
                if (!matcher.matches()) {
                    setMessage("InValid Account Number");
                    setInValidMessage("Invalid Account Number");
                    checkDataValid = false;
                } else {
                    setMessage("");
                    setInValidMessage("");
                    checkDataValid = true;
                }
                break;
            default:
                setMessage("Too Short Bank Account Number, Please Check");
                setInValidMessage("Too Short Bank Account Number, Please Check");
                checkDataValid = false;
        }
    }

    /**
     * validate the business PAN Number
     */
    public void validateBusinessPANNumber() {
        switch (getPan().length()) {
            case 0:
//                setMessage("Bank PAN Field Is Blank");
//                setInValidMessage("Bank PAN Field Is Blank");
                checkDataValid = true;
                break;
            case 10:
                pattern = Pattern.compile("[\\d\\w]{10}");
                matcher = pattern.matcher(pan.trim());
                if (!matcher.matches()) {
                    setMessage("InValid PAN Number");
                    setInValidMessage("InValid PAN Number");
                    checkDataValid = false;
                } else {
                    setMessage("");
                    setInValidMessage("");
                    checkDataValid = true;
                }
                break;
            default:
                setMessage("Too Short PAN Number, Please Check");
                setInValidMessage("Too Short PAN Number, Please Check");
                checkDataValid = false;
        }
    }

    /**
     * validate the business GST number
     */
    public void validateBusinessGSTNumber() {

        switch (getBusinessGst().length()) {
            case 0:
//                setMessage("Business GST Field Is Blank");
//                setInValidMessage("Business GST Field Is Blank");
                checkDataValid = true;
                break;
            case 15:
                pattern = Pattern.compile("[\\d\\w]{15}");
                matcher = pattern.matcher(businessGst.trim());
                if (!matcher.matches()) {
                    setMessage("InValid GST Number");
                    setInValidMessage("InValid GST Number");
                    checkDataValid = false;
                } else {
                    setMessage("");
                    setInValidMessage("");
                    checkDataValid = true;
                }
                break;
            default:
                setMessage("Too Short GST Number, Please Check");
                setInValidMessage("Too Short GST Number, Please Check");
                checkDataValid = false;
        }
    }

    /**
     * validate the company Name
     */
    public void validateCompanyName() {

        switch (getCompanyName().length()) {
            case 0:
                setMessage("Company Name Field Is Blank");
                setInValidMessage("Company Name Field Is Blank");
                checkDataValid = false;
                break;
            case 1:
                setMessage("Too Short Company Name Atleast 2 character");
                setInValidMessage("Too Short Company Name Atleast 2 character");
                checkDataValid = false;
                break;
            default:
                if ((getCompanyName().length() >= 2) && (getCompanyName().length() <= 50)) {
                    pattern = Pattern.compile("[\\d\\w\\s]{2,50}");
                    matcher = pattern.matcher(companyName.trim());
                    if (!matcher.matches()) {
                        setMessage("Special character not allowed In Company Name");
                        setInValidMessage("Special character not allowed In Company Name");
                        checkDataValid = false;
                    } else {
                        setMessage("");
                        setInValidMessage("");
                        checkDataValid = true;
                    }
                } else {
                    setMessage("Too Long Company Name, Atleast 2-50 character");
                    setInValidMessage("Too Long Company Name, Atleast 2-50 character");
                    checkDataValid = false;
                }
        }
    }

    /**
     * validate the Account Name
     */
    public void validateAccountName() {

        if (!getCompanyName().equalsIgnoreCase(getAccountName())) {
            setMessage("Company and account name are not same.");
            setInValidMessage("Company and account name are not same.");
            checkDataValid = true;
        } else {
            setMessage("");
            setInValidMessage("");
            checkDataValid = true;
        }
    }

    /**
     * Validate the Business category
     */
    public void validateBusinessCat() {
        if (businessCate.equals("0")) {
            setMessage("Please select the Business Category");
            setInValidMessage("Please select the Business Category");
            checkDataValid = false;
        } else {
            setMessage("");
            setInValidMessage("");
            checkDataValid = true;
        }
    }

    /**
     * validate the Business Type
     */
    public void validateBusinessType() {

        if (businessType.equals("0")) {
            setMessage("Please select the Business Type");
            setInValidMessage("Please select the Business Type");
            checkDataValid = false;
        } else {
            setMessage("");
            setInValidMessage("");
            checkDataValid = true;
        }
    }

    /**
     * validate the first Name
     */
    public void validateFirstName() {
        setInValidMessage("");
        switch (getFirstName().length()) {
            case 0:
                setMessage("First Name Field Is Blank");
                setInValidMessage("First Name Field Is Blank");
                checkDataValid = false;
                break;
            case 1:
                setMessage("At Least Enter The 2 character In First Name");
                setInValidMessage("At Least Enter The 2 character In First Name");
                checkDataValid = false;
                break;
            default:
                if ((getFirstName().length() >= 2) && (getFirstName().length() <= 15)) {
                    pattern = Pattern.compile("^[\\w]{2,15}$");
                    matcher = pattern.matcher(firstName.trim());
                    if (!matcher.matches()) {
                        setMessage("Special character or Digit not allowed In First Name");
                        setInValidMessage("Special character or Digit not allowed In First Name");
                        checkDataValid = false;
                    } else {
                        setMessage("");
                        checkDataValid = true;
                    }
                } else {
                    setMessage("Your Fist Name Is Too Long, Please Enter The 2-15 character Between Atleast");
                    setInValidMessage("Your Fist Name Is Too Long, Please Enter The 2-15 character Between Atleast");
                    checkDataValid = false;
                }
        }
    }

    /**
     * validate the lastName
     */
    public void validateLastName() {

        switch (getLastName().length()) {
            case 0:
                setMessage("");
                setInValidMessage("");
                break;
            case 1:
                setMessage("At Least Enter The 2 character In Last Name");
                setInValidMessage("At Least Enter The 2 character In Last Name");
                checkDataValid = false;
                break;
            default:
                if ((getLastName().length() >= 2) && (getLastName().length() <= 15)) {
                    pattern = Pattern.compile("^[\\w]{2,15}$");
                    matcher = pattern.matcher(lastName.trim());
                    if (!matcher.matches()) {
                        setMessage("Special character or Digit not allowed");
                        setInValidMessage("Special character or Digit not allowed");
                        checkDataValid = false;
                    } else {
                        setMessage("");
                        setInValidMessage("");
                        checkDataValid = true;
                    }
                } else {
                    setMessage("Your Last Name Is Too Long, Please Enter The 2-15 character Between Atleast");
                    setInValidMessage("Your Last Name Is Too Long, Please Enter The 2-15 character Between Atleast");
                    checkDataValid = false;
                }

        }
    }

    /**
     * validate the email
     */
    public void validateEmail() {
        if (email.trim().length() != 0) {
            if (!validator.validateEmail(email.trim())) {
                setMessage("Please enter proper email id.");
                setInValidMessage("Please enter proper email id.");
                checkDataValid = false;
            } else {
                setMessage("");
                setInValidMessage("");
                checkDataValid = true;
            }
        } else {
            setMessage("Email Field Is Blank");
            setInValidMessage("Email Field Is Blank");
            checkDataValid = false;
        }
    }

    /**
     * validate the mobile number
     */
    public void validateMobilNumber() {
        switch (getMobile().length()) {
            case 0:
                setMessage("Mobile Field Is Blank");
                setInValidMessage("Mobile Field Is Blank");
                checkDataValid = false;
                break;
            case 10:
                pattern = Pattern.compile("^[6789]\\d{9}$");
                matcher = pattern.matcher(getMobile().trim());
                if (!matcher.matches()) {
                    setMessage("This phone number format is not recognised. Please check the number.");
                    setInValidMessage("This phone number format is not recognised. Please check the number.");
                } else {
                    setMessage("");
                    setInValidMessage("");
                    checkDataValid = true;
                }
                break;

            default:
                setMessage("Please enter proper 10 digit mobile Number");
                setInValidMessage("Please enter proper 10 digit mobile Number");
                checkDataValid = false;
        }

    }

    /**
     * Check all criteria are valid when return true otherwise return false
     */
    public boolean isCheckDataValid() {
        return checkDataValid;
    }

    public void setCheckDataValid(boolean checkDataValid) {
        this.checkDataValid = checkDataValid;
    }

    /**
     * Set modify Data
     */
    public void setModifyData() {
        resetOnModifyAndVerify();
        setCustId(currentAirPayRecord.getCustomerId());
        setBankName(currentAirPayRecord.getBankName());
        setBankIfsc(currentAirPayRecord.getBankIfsc());
        setBankAccoNo(currentAirPayRecord.getBankAccountNo());
        setPan(currentAirPayRecord.getBankPanNo());
        setBusinessGst(currentAirPayRecord.getBusinessGSt());
        setCompanyName(currentAirPayRecord.getCompanyName());
        setAccountName(currentAirPayRecord.getCompanyName());
        setFirstName(currentAirPayRecord.getFirstName());
        setLastName(currentAirPayRecord.getLastName());
        setEmail(currentAirPayRecord.getEmail());
        setMobile(currentAirPayRecord.getMobile());
        setBusinessCate(currentAirPayRecord.getBusinessCatId());
        setBusinessType(currentAirPayRecord.getBusinessTypeId());
    }
}