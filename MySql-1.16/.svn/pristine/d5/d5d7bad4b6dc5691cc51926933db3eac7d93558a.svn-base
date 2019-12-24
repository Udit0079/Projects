/*
 * CREATED BY   :  ROHIT KRISHNA GUPTA
 * CREATION DATE:  13 JUNE 2011
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.InstituteDetailGrid;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrMstInstitutePKTO;
import com.hrms.common.to.HrMstInstituteTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.delegate.TrainingManagementDelegate;
import com.hrms.web.exception.WebException;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author ROHIT KRISHNA
 */
public class InstituteDetails extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private String errorMessage;
    private String message;
    private String instituteName;
    private String instituteAdd;
    private String contactPerson;
    private String contactNum;
    private String compCode = "0";
    private List<InstituteDetailGrid> gridDetail;
    int currentRow, defaultComp;
    private InstituteDetailGrid currentItem = new InstituteDetailGrid();
    private boolean saveFlag;
    private boolean editFlag;
    private boolean delFlag;
    private static final Logger logger = Logger.getLogger(InstituteDetails.class);
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

    public InstituteDetailGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(InstituteDetailGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<InstituteDetailGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<InstituteDetailGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getInstituteAdd() {
        return instituteAdd;
    }

    public void setInstituteAdd(String instituteAdd) {
        this.instituteAdd = instituteAdd;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Pattern pm = Pattern.compile("[a-zA-z0-9,]+([ '-][a-zA-Z0-9,]+)*");
    Pattern p = Pattern.compile("[0-9]*");

    /** Creates a new instance of InstituteDetails */
    public InstituteDetails() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            operationList = new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0", "---Select---"));
            operationList.add(new SelectItem("1", "Add"));
            operationList.add(new SelectItem("2", "View"));
            Date date = new Date();
            setTodayDate(sdf.format(date));
            this.setErrorMessage("");
            this.setMessage("");
            this.setSaveFlag(false);
            this.setDelFlag(true);
            this.setEditFlag(true);
            gridLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (operation.equalsIgnoreCase("0")) {
                setErrorMessage("Please select an operation!");
                return;
            }
            Date date = new Date();
            if (this.instituteName == null || this.instituteName.equalsIgnoreCase("") || this.instituteName.length() == 0) {
                this.setErrorMessage("Please enter institute name.");
                return;
            }
            Matcher instituteNameCheck = pm.matcher(instituteName);
            if (!instituteNameCheck.matches()) {
                this.setErrorMessage("Institute name should not contain special characters.");
                return;
            }
            if (this.instituteAdd == null || this.instituteAdd.equalsIgnoreCase("") || this.instituteAdd.length() == 0) {
                this.setErrorMessage("Please enter institute address.");
                return;
            }
            Matcher instituteAddCheck = pm.matcher(instituteAdd);
            if (!instituteAddCheck.matches()) {
                this.setErrorMessage("Institute address should not contain special characters.");
                return;
            }
            if (this.contactPerson == null || this.contactPerson.equalsIgnoreCase("") || this.contactPerson.length() == 0) {
                this.setErrorMessage("Please enter contact person name.");
                return;
            }
            Matcher contactPersonCheck = pm.matcher(contactPerson);
            if (!contactPersonCheck.matches()) {
                this.setErrorMessage("Contact person name should not contain special characters.");
                return;
            }
            if (this.contactNum == null || this.contactNum.equalsIgnoreCase("") || this.contactNum.length() == 0) {
                this.setErrorMessage("Please enter contact number.");
                return;
            }
            Matcher contactNumCheck = p.matcher(contactNum);
            if (!contactNumCheck.matches()) {
                this.setErrorMessage("Contact number should be numeric.");
                return;
            }
            String instCode = "";
           
            HrMstInstituteTO hrMstInstTO = new HrMstInstituteTO();
            HrMstInstitutePKTO hrMstInstPKTO = new HrMstInstitutePKTO();
            hrMstInstTO.setInstName(this.instituteName);
            hrMstInstTO.setInstAdd(this.instituteAdd);
            hrMstInstTO.setContPers(this.contactPerson);
            hrMstInstTO.setContNo(this.contactNum);
            hrMstInstTO.setDefaultComp(defaultComp);
            hrMstInstTO.setStatFlag("N");
            hrMstInstTO.setStatUpFlag("Y");
            hrMstInstTO.setModDate(date);
            hrMstInstTO.setAuthBy(getUserName());
            hrMstInstTO.setEnteredBy(getUserName());
            if (operation.equalsIgnoreCase("1")) {
                TrainingManagementDelegate trngManagementDelegate = new TrainingManagementDelegate();
                instCode = trngManagementDelegate.getMaxInstitute(Integer.parseInt(compCode));
                if (instCode == null || instCode.equalsIgnoreCase("") || instCode.length() == 0) {
                    this.setErrorMessage("Institute code not generated !!!");
                    return;
                }
                int length = instCode.length();
                int addedZero = 3 - length;
                for (int i = 1; i <= addedZero; i++) {
                    instCode = "0" + instCode;
                }
                instCode = "INS" + instCode;
                if (instCode.length() != 6) {
                    this.setErrorMessage("Proper Institute code is not generated !!!");
                    return;
                }
                hrMstInstPKTO.setCompCode(Integer.parseInt(this.compCode));
                hrMstInstPKTO.setInstCode(instCode);
                hrMstInstTO.setHrMstInstitutePKTO(hrMstInstPKTO);
                String result = trngManagementDelegate.saveInstituteDetail(hrMstInstTO);
                if (result == null || result.equalsIgnoreCase("")) {
                    this.setErrorMessage("System error occured !!!");
                } else if (result.equalsIgnoreCase("false")) {
                    this.setErrorMessage("Data not saved !!!");
                } else {
                    this.setMessage("Data saved succesfully.");
                    this.setSaveFlag(false);
                    this.setDelFlag(true);
                    this.setEditFlag(true);
                    this.setInstituteName("");
                    this.setInstituteAdd("");
                    this.setContactNum("");
                    this.setContactPerson("");
                    setOperation("0");
                }
            } else if (operation.equalsIgnoreCase("2")) {
                hrMstInstPKTO.setCompCode(Integer.parseInt(currentItem.getCompCode()));
                hrMstInstPKTO.setInstCode(currentItem.getInstituteCode());
                hrMstInstTO.setHrMstInstitutePKTO(hrMstInstPKTO);
                TrainingManagementDelegate trngManagementDelegate = new TrainingManagementDelegate();
                String result = trngManagementDelegate.updateInstituteDetail(hrMstInstTO);
                if (result == null || result.equalsIgnoreCase("")) {
                    this.setErrorMessage("System error occured !!!");
                    return;
                } else if (result.equalsIgnoreCase("false")) {
                    this.setErrorMessage("Record not updated.");
                    return;
                } else {
                    this.setMessage("Record updated succesfully.");
                    gridLoad();
                    this.setSaveFlag(false);
                    this.setDelFlag(true);
                    this.setEditFlag(true);
                    this.setInstituteName("");
                    this.setInstituteAdd("");
                    this.setContactNum("");
                    this.setContactPerson("");
                     setOperation("0");
                }
            }


            gridLoad();
        } catch (WebException e) {
            logger.error("Exception occured while executing method addDetail()", e);
            this.setErrorMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method addDetail()", e1);
            this.setErrorMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method addDetail()", e);
            this.setErrorMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void gridLoad() {
        try {
            gridDetail = new ArrayList<InstituteDetailGrid>();
            List resultLt = new ArrayList();
            TrainingManagementDelegate trngManagementDelegate = new TrainingManagementDelegate();
            resultLt = trngManagementDelegate.getInstituteDetail(Integer.parseInt(compCode));
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    InstituteDetailGrid rd = new InstituteDetailGrid();
                    rd.setCompCode(result[0].toString());
                    rd.setInstituteCode(result[1].toString());
                    rd.setInstituteName(result[2].toString());
                    rd.setInstituteAdd(result[3].toString());
                    rd.setContactPerson(result[4].toString());
                    rd.setContactNum(result[5].toString());
                    gridDetail.add(rd);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String ac = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("instituteCode"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (InstituteDetailGrid item : gridDetail) {
            if (item.getInstituteCode().equalsIgnoreCase(ac)) {
                currentItem = item;
            }
        }
    }

    public void fillValuesofGridInFields() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setSaveFlag(true);
            this.setDelFlag(false);
            this.setEditFlag(false);
            this.setInstituteName(currentItem.getInstituteName());
            this.setInstituteAdd(currentItem.getInstituteAdd());
            this.setContactPerson(currentItem.getContactPerson());
            this.setContactNum(currentItem.getContactNum());
            setOperation("2");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteInstituesRecord() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.instituteName == null || this.instituteName.equalsIgnoreCase("") || this.instituteName.length() == 0) {
                this.setErrorMessage("Please enter institute name.");
                return;
            }
            Matcher instituteNameCheck = pm.matcher(instituteName);
            if (!instituteNameCheck.matches()) {
                this.setErrorMessage("Institute name should not contain special characters.");
                return;
            }
            if (this.instituteAdd == null || this.instituteAdd.equalsIgnoreCase("") || this.instituteAdd.length() == 0) {
                this.setErrorMessage("Please enter institute address.");
                return;
            }
            Matcher instituteAddCheck = pm.matcher(instituteAdd);
            if (!instituteAddCheck.matches()) {
                this.setErrorMessage("Institute address should not contain special characters.");
                return;
            }
            if (this.contactPerson == null || this.contactPerson.equalsIgnoreCase("") || this.contactPerson.length() == 0) {
                this.setErrorMessage("Please enter contact person name.");
                return;
            }
            Matcher contactPersonCheck = pm.matcher(contactPerson);
            if (!contactPersonCheck.matches()) {
                this.setErrorMessage("Contact person name should not contain special characters.");
                return;
            }
            if (this.contactNum == null || this.contactNum.equalsIgnoreCase("") || this.contactNum.length() == 0) {
                this.setErrorMessage("Please enter contact number.");
                return;
            }
            Matcher contactNumCheck = p.matcher(contactNum);
            if (!contactNumCheck.matches()) {
                this.setErrorMessage("Contact number should be numeric.");
                return;
            }
            HrMstInstituteTO hrMstInstTO = new HrMstInstituteTO();
            HrMstInstitutePKTO hrMstInstPKTO = new HrMstInstitutePKTO();
            hrMstInstPKTO.setCompCode(Integer.parseInt(currentItem.getCompCode()));
            hrMstInstPKTO.setInstCode(currentItem.getInstituteCode());
            hrMstInstTO.setHrMstInstitutePKTO(hrMstInstPKTO);
            TrainingManagementDelegate trngManagementDelegate = new TrainingManagementDelegate();
            String result = trngManagementDelegate.deleteInstituteDetail(hrMstInstPKTO);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else if (result.equalsIgnoreCase("false")) {
                this.setErrorMessage("Record not deleted !!!");
                return;
            } else {
                this.setMessage("Record deleted succesfully.");
                gridLoad();
                this.setSaveFlag(false);
                this.setDelFlag(true);
                this.setEditFlag(true);
                this.setInstituteName("");
                this.setInstituteAdd("");
                this.setContactNum("");
                this.setContactPerson("");
                setOperation("0");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteInstituesRecord()", e);
            this.setErrorMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method deleteInstituesRecord()", e1);
            this.setErrorMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteInstituesRecord()", e);
            this.setErrorMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setInstituteName("");
            this.setInstituteAdd("");
            this.setContactNum("");
            this.setContactPerson("");
            this.setSaveFlag(false);
            this.setDelFlag(true);
            this.setEditFlag(true);
            setOperation("0");
        } catch (Exception e) {
            logger.error("Exception occured while executing method resetForm()", e);
            this.setErrorMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setOperationOnBlur() {
        if (operation.equalsIgnoreCase("0")) {
            setErrorMessage("Please select an operation !");
            return;
        } 
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitBtnAction()", e);
            this.setErrorMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }
}
