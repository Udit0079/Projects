/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.payroll;

import com.hrms.web.pojo.SalaryStructureGrid;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstStructPKTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrSalaryStructurePKTO;
import com.hrms.common.to.HrSalaryStructureTO;
//import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir kr Bisht
 */
public class SalaryStructure extends BaseBean {

    private static final Logger logger = Logger.getLogger(SalaryStructure.class);
    private String function;
    private String message;
    private String fromDate;
    private String toDate;
    private String purpose;
    private Date applicableDate;
    private String description;
    private String nature;
    private char taxable;
    private int compCode;
    private String mode = "SAVE";
    private String slabCriteriaSelectList;
    private boolean disablePurpose;
    private boolean disableAppliDate;
    private boolean disableDesc;
    private boolean disableNature;
    private boolean disabletaxable;
    private boolean disableDelete;
    private boolean disableSave;
    private SalaryStructureGrid currentSalStrucItem;
    private List<SelectItem> purposeListBox;
    private List<SelectItem> natureSelectList;
    private List<SelectItem> taxableSelectList;
    private List<SelectItem> descriptionSelectList;
    private List<SelectItem> functionList;
    private List<SalaryStructureGrid> salaryStructureArrayList;
    private PayrollTransactionsFacadeRemote payrollRemote;
    private boolean disableCrGl;
    private String crGl;
    private int shCode;

    /** Creates a new instance of SalaryStructure */
    public SalaryStructure() {
        try {
            payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");
            this.setMessage("");
            compCode = Integer.parseInt(getOrgnBrCode());
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--SELECT--"));
            functionList.add(new SelectItem("1", "ADD"));
            functionList.add(new SelectItem("2", "VIEW"));
            getInitalData();
            disableDelete = true;
            disableCrGl = true;
        } catch (Exception e) {
            logger.error("Exception occured while executing method SalaryStructure()", e);
        }
    }

    public void getInitalData() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            purposeListBox = new ArrayList<SelectItem>();
            purposeListBox.add(new SelectItem("0", "--SELECT--"));
            natureSelectList = new ArrayList<SelectItem>();
            natureSelectList.add(new SelectItem("0", "--SELECT--"));
            PayrollMasterManagementDelegate masterManagementDelegate = new PayrollMasterManagementDelegate();
            List<HrMstStructTO> HrMstStructTOs = masterManagementDelegate.getInitialData(compCode, "PUR%");
            if (!HrMstStructTOs.isEmpty()) {
                for (HrMstStructTO HrMstStructTO : HrMstStructTOs) {
                    purposeListBox.add(new SelectItem(HrMstStructTO.getHrMstStructPKTO().getStructCode(), HrMstStructTO.getDescription()));
                }
            }
            HrMstStructTOs = masterManagementDelegate.getInitialData(compCode, "ALL%");
            if (!HrMstStructTOs.isEmpty()) {
                for (HrMstStructTO HrMstStructTO : HrMstStructTOs) {
                    natureSelectList.add(new SelectItem(HrMstStructTO.getHrMstStructPKTO().getStructCode(), HrMstStructTO.getDescription()));
                }
            }
            taxableSelectList = new ArrayList<SelectItem>();
            taxableSelectList.add(new SelectItem("0", "--SELECT--"));
            taxableSelectList.add(new SelectItem("Y", "YES"));
            taxableSelectList.add(new SelectItem("N", "NO"));
            
//            List list = payrollRemote.getFinYears(getOrgnBrCode());
//            for (int i = 0; i < list.size(); i++) {
//                Object[] ele = (Object[]) list.get(0);                
//                this.setFromDate(ele[0].toString().trim());
//                this.setToDate(ele[1].toString().trim());
//            }
//            List<PayrollCalendarTO> payrollCalendarTOs = masterManagementDelegate.getFinancialYear(compCode);
//            if (!payrollCalendarTOs.isEmpty()) {
//                for (PayrollCalendarTO payrollCalendarTO : payrollCalendarTOs) {
//                    this.setFromDate(formatter.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateFrom()));
//                    this.setToDate(formatter.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateTo()));
//                }
//            }

        } catch (WebException e) {
            logger.error("Exception occured while executing method getInitalData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getInitalData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getInitalData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void onChangeFunction() {
        if (function.equals("0")) {
            setMessage("Please select a function");
            return;
        } else if (function.equals("1")) {
        } else if (function.equals("2")) {
            getSalaryStructureGridData();
        }
    }
    
    public void onChangePurpose() {
        disableCrGl = true;
        if (function.equals("0")) {
            setMessage("Please select a function");
            return;
        } else if (function.equals("1")) {
            if(purpose.equalsIgnoreCase("PUR001")){
                disableCrGl = true;
            }else{
                disableCrGl = false;
            }
        } else if (function.equals("2")) {            
        }
    }

    public void getSalaryStructureGridData() {
        try {
            salaryStructureArrayList = new ArrayList<SalaryStructureGrid>();
            PayrollMasterManagementDelegate payrolldelegate = new PayrollMasterManagementDelegate();
//            List hrSalaryStructureList = payrolldelegate.getHrSalaryStructure(compCode, getFromDate(), getToDate());
            List hrSalaryStructureList = payrolldelegate.getHrSalaryStructure(compCode);
            Iterator i1 = hrSalaryStructureList.iterator();
            while (i1.hasNext()) {
                SalaryStructureGrid ssg = new SalaryStructureGrid();
                Object[] result = (Object[]) i1.next();
                ssg.setPurposeCode(result[0].toString());
                ssg.setPurpose(result[1].toString());
                ssg.setDescription(result[2].toString());
                ssg.setTaxable(result[3].toString());
                ssg.setNatCode(result[4].toString());
                ssg.setNature(result[5].toString());
                ssg.setApplicableDate(result[6].toString());
                ssg.setGlCode(result[7]==null ? "" :result[7].toString());
                ssg.setShCode(Integer.parseInt(result[8].toString()));
//                ssg.setDateFrom(result[7].toString());
//                ssg.setToDate(result[8].toString());
                salaryStructureArrayList.add(ssg);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method gridData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method gridData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method gridData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setRowValues() {
        try {
            setMessage("");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            setApplicableDate(formatter.parse(currentSalStrucItem.getApplicableDate()));
//            setFromDate(currentSalStrucItem.getDateFrom());
//            setToDate(currentSalStrucItem.getToDate());

            setDescription(currentSalStrucItem.getDescription());
            setNature(currentSalStrucItem.getNatCode());
            setPurpose(currentSalStrucItem.getPurposeCode());
            setTaxable(currentSalStrucItem.getTaxable().charAt(0));
            setCrGl(currentSalStrucItem.getGlCode());
            shCode = currentSalStrucItem.getShCode();
            if(currentSalStrucItem.getPurposeCode().equalsIgnoreCase("PUR001")){
                disableCrGl = true;
            }else{
                disableCrGl = false;
            }
            disableDesc = true;
            disableSave = false;
            disableNature = true;
            disablePurpose = true;
            disableDelete = false;
            mode = "UPDATE";
        } catch (ParseException ex) {
            logger.error("Exception occured while executing method setRowValues()", ex);
        }
    }

    public void deleteSalaryStructure() {
        mode = "DELETE";
        saveSalaryStructure();
    }

    public void saveSalaryStructure() {
        try {
            if (function.equals("0")) {
                setMessage("Please select a function");
                return;
            }
            if (mode.equalsIgnoreCase("SAVE")) {
                String valResult = validations();
                if (!valResult.equalsIgnoreCase("true")) {
                    this.setMessage(valResult);
                    return;
                }
                
                PayrollMasterManagementDelegate payrollDelegate = new PayrollMasterManagementDelegate();
                List shCodeLst = payrollDelegate.maxSalaryStructShortCode(compCode, this.getPurpose());
                if (shCodeLst.isEmpty() || shCodeLst == null || shCodeLst.get(0) == null) {
                    shCode = 1;
                } else {
                    shCode = Integer.parseInt(shCodeLst.get(0).toString())+1;                    
                }
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            HrSalaryStructureTO hrSalaryStructureTO = new HrSalaryStructureTO();
            HrSalaryStructurePKTO hrSalaryStructurePKTO = new HrSalaryStructurePKTO();
            hrSalaryStructurePKTO.setAlDesc(getDescription());
            hrSalaryStructurePKTO.setCompCode(compCode);
//            hrSalaryStructurePKTO.setDateFrom(getFromDate());
//            hrSalaryStructurePKTO.setDateTo(getToDate());
            hrSalaryStructurePKTO.setNature(nature);
            hrSalaryStructurePKTO.setPurposeCode(purpose);
            HrMstStructTO hrMstStructTO = new HrMstStructTO();
            HrMstStructPKTO hrMstStructPKTO = new HrMstStructPKTO();
            hrMstStructPKTO.setCompCode(compCode);
            hrMstStructPKTO.setStructCode(purpose);
            hrMstStructTO.setHrMstStructPKTO(hrMstStructPKTO);
            HrMstStructTO hrMstStructTO1 = new HrMstStructTO();
            HrMstStructPKTO hrMstStructPKTO1 = new HrMstStructPKTO();
            hrMstStructPKTO1.setCompCode(compCode);
            hrMstStructPKTO1.setStructCode(nature);
            hrMstStructTO1.setHrMstStructPKTO(hrMstStructPKTO1);
            hrSalaryStructureTO.setHrMstStructTO(hrMstStructTO);
            hrSalaryStructureTO.setHrMstStruct1TO(hrMstStructTO1);
            hrSalaryStructureTO.setHrSalaryStructurePKTO(hrSalaryStructurePKTO);
            hrSalaryStructureTO.setApplicableDate(formatter.format(getApplicableDate()));
            hrSalaryStructureTO.setAuthBy(getUserName());
            hrSalaryStructureTO.setDefaultComp(compCode);
            hrSalaryStructureTO.setEnteredBy(getUserName());
            hrSalaryStructureTO.setModDate(new Date());
            hrSalaryStructureTO.setStatFlag('Y');
            hrSalaryStructureTO.setStatUpFlag('Y');
            hrSalaryStructureTO.setTaxable(taxable);
            hrSalaryStructureTO.setGlCode(crGl);
            hrSalaryStructureTO.setDescShCode(shCode);
            
            PayrollMasterManagementDelegate payrollDelegate = new PayrollMasterManagementDelegate();
            String result = payrollDelegate.saveHrSalaryStructure(hrSalaryStructureTO, mode);
            refresh();
            setMessage(result);
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveSalaryStructure()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveSalaryStructure()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveSalaryStructure()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void refresh() {
        this.setMessage("");
        this.setApplicableDate(null);
        this.setDescription("");
        this.setNature("0");
        this.setPurpose("0");
        this.setTaxable('0');
        this.setFunction("0");
        disableDelete = true;
        disableAppliDate = false;
        disableDesc = false;
        disableNature = false;
        disablePurpose = false;
        disableSave = false;
        disabletaxable = false;
        mode = "SAVE";
        disableCrGl=true;
        crGl ="";
    }

    public String validations() {
        if (purpose.equalsIgnoreCase("0")) {
            return "Purpose field cannot be empty!!!";
        }
        if (getApplicableDate() == null) {
            return "Applicable date cannot be empty!!!";

        }
        if (getDescription().equalsIgnoreCase("")) {
            return "Description field cannot be empty!!!";

        }
        if (nature.equalsIgnoreCase("0")) {
            return "Nature list box cannot be empty!!!";

        }
        if (taxable == '0') {
            return "Taxable field cannot be empty!!!";
        }
        return "true";
    }

    public String btnExitAction() {
        return "case1";
    }

    public SalaryStructureGrid getCurrentSalStrucItem() {
        return currentSalStrucItem;
    }

    public void setCurrentSalStrucItem(SalaryStructureGrid currentSalStrucItem) {
        this.currentSalStrucItem = currentSalStrucItem;
    }

    public boolean isDisableDelete() {
        return disableDelete;
    }

    public void setDisableDelete(boolean disableDelete) {
        this.disableDelete = disableDelete;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public boolean isDisableAppliDate() {
        return disableAppliDate;
    }

    public void setDisableAppliDate(boolean disableAppliDate) {
        this.disableAppliDate = disableAppliDate;
    }

    public boolean isDisableDesc() {
        return disableDesc;
    }

    public void setDisableDesc(boolean disableDesc) {
        this.disableDesc = disableDesc;
    }

    public boolean isDisableNature() {
        return disableNature;
    }

    public void setDisableNature(boolean disableNature) {
        this.disableNature = disableNature;
    }

    public boolean isDisablePurpose() {
        return disablePurpose;
    }

    public void setDisablePurpose(boolean disablePurpose) {
        this.disablePurpose = disablePurpose;
    }

    public boolean isDisabletaxable() {
        return disabletaxable;
    }

    public void setDisabletaxable(boolean disabletaxable) {
        this.disabletaxable = disabletaxable;
    }

    public Date getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(Date applicableDate) {
        this.applicableDate = applicableDate;
    }

    public char getTaxable() {
        return taxable;
    }

    public void setTaxable(char taxable) {
        this.taxable = taxable;
    }

    public List<SalaryStructureGrid> getSalaryStructureArrayList() {
        return salaryStructureArrayList;
    }

    public void setSalaryStructureArrayList(List<SalaryStructureGrid> salaryStructureArrayList) {
        this.salaryStructureArrayList = salaryStructureArrayList;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SelectItem> getDescriptionSelectList() {
        return descriptionSelectList;
    }

    public void setDescriptionSelectList(List<SelectItem> descriptionSelectList) {
        this.descriptionSelectList = descriptionSelectList;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public List<SelectItem> getNatureSelectList() {
        return natureSelectList;
    }

    public void setNatureSelectList(List<SelectItem> natureSelectList) {
        this.natureSelectList = natureSelectList;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getSlabCriteriaSelectList() {
        return slabCriteriaSelectList;
    }

    public void setSlabCriteriaSelectList(String slabCriteriaSelectList) {
        this.slabCriteriaSelectList = slabCriteriaSelectList;
    }

    public List<SelectItem> getTaxableSelectList() {
        return taxableSelectList;
    }

    public void setTaxableSelectList(List<SelectItem> taxableSelectList) {
        this.taxableSelectList = taxableSelectList;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getPurposeListBox() {
        return purposeListBox;
    }

    public void setPurposeListBox(List<SelectItem> purposeListBox) {
        this.purposeListBox = purposeListBox;
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

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public boolean isDisableCrGl() {
        return disableCrGl;
    }

    public void setDisableCrGl(boolean disableCrGl) {
        this.disableCrGl = disableCrGl;
    }   

    public String getCrGl() {
        return crGl;
    }

    public void setCrGl(String crGl) {
        this.crGl = crGl;
    } 

    public int getShCode() {
        return shCode;
    }

    public void setShCode(int shCode) {
        this.shCode = shCode;
    }   
}
