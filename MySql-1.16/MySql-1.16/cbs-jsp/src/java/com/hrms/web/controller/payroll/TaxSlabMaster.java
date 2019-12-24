/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.payroll;

import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.to.HrTaxSlabMasterPKTO;
import com.hrms.common.to.HrTaxSlabMasterTO;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.pojo.TaxSlabMasterPojo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author Ankit Verma
 */
public class TaxSlabMaster extends BaseBean {

    private String message;
    private String operation;
    private List<SelectItem> operationList;
    private String slabFromAmount;
    private String slabToAmount;
    private String aplicableTax;
    private String taxFor;
    private List<SelectItem> taxForList;
    List<TaxSlabMasterPojo> slabMasterGrid;
    TaxSlabMasterPojo currentItem;
    private int compCode;
    String mode = "save";
    PayrollMasterManagementDelegate delegate;
    private boolean disableTaxFor;
    private boolean  disableDelete;

    public boolean isDisableDelete() {
        return disableDelete;
    }

    public void setDisableDelete(boolean disableDelete) {
        this.disableDelete = disableDelete;
    }

    
    public boolean isDisableTaxFor() {
        return disableTaxFor;
    }

    public void setDisableTaxFor(boolean disableTaxFor) {
        this.disableTaxFor = disableTaxFor;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    
    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getAplicableTax() {
        return aplicableTax;
    }

    public void setAplicableTax(String aplicableTax) {
        this.aplicableTax = aplicableTax;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public String getSlabFromAmount() {
        return slabFromAmount;
    }

    public void setSlabFromAmount(String slabFromAmount) {
        this.slabFromAmount = slabFromAmount;
    }

    public String getSlabToAmount() {
        return slabToAmount;
    }

    public void setSlabToAmount(String slabToAmount) {
        this.slabToAmount = slabToAmount;
    }

    public String getTaxFor() {
        return taxFor;
    }

    public void setTaxFor(String taxFor) {
        this.taxFor = taxFor;
    }

    public List<SelectItem> getTaxForList() {
        return taxForList;
    }

    public void setTaxForList(List<SelectItem> taxForList) {
        this.taxForList = taxForList;
    }

    public TaxSlabMasterPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TaxSlabMasterPojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<TaxSlabMasterPojo> getSlabMasterGrid() {
        return slabMasterGrid;
    }

    public void setSlabMasterGrid(List<TaxSlabMasterPojo> slabMasterGrid) {
        this.slabMasterGrid = slabMasterGrid;
    }

    /** Creates a new instance of TaxSlabMaster */
    public TaxSlabMaster() {
        compCode = Integer.parseInt(getOrgnBrCode());
        operationList = new ArrayList<SelectItem>();
        operationList.add(new SelectItem("0", "--Select--"));
        operationList.add(new SelectItem("1", "Add"));
        operationList.add(new SelectItem("2", "Edit"));
        taxForList = new ArrayList<SelectItem>();
        taxForList.add(new SelectItem("0", "--Select--"));
        taxForList.add(new SelectItem("M", "Male"));
        taxForList.add(new SelectItem("F", "Female"));
        slabFromAmount = "0.0";
        slabToAmount = "0.0";
        aplicableTax = "0";
        
        try {
            delegate = new PayrollMasterManagementDelegate();
            viewTaxSlabMasterDetails();
        } catch (Exception e) {
        }

    }

    public void saveSlabMaster() {
        try {
            message = validation();
            disableDelete=true;
            if (message.equalsIgnoreCase("ok")) {
                HrTaxSlabMasterPKTO slabMasterPKTO = new HrTaxSlabMasterPKTO();
                slabMasterPKTO.setCompCode(compCode);
                if (mode.equalsIgnoreCase("save")) {
                    slabMasterPKTO.setTaxSlabCode(getMaxTaxSlabCode());
                } else {
                    slabMasterPKTO.setTaxSlabCode(currentItem.getTaxSlabCode());
                }
                slabMasterPKTO.setTaxFor(taxFor);
                HrTaxSlabMasterTO slabMasterTO = new HrTaxSlabMasterTO();
                slabMasterTO.setHrTaxSlabMasterPKTO(slabMasterPKTO);
                slabMasterTO.setApplicableTax(Double.parseDouble(getAplicableTax()));
                slabMasterTO.setAuthBy(getUserName());
                slabMasterTO.setDefaultComp(compCode);
                slabMasterTO.setEnteredBy(getUserName());
                slabMasterTO.setModDate(new Date());
                slabMasterTO.setRangeFrom(Double.parseDouble(slabFromAmount));
                slabMasterTO.setRangeTo(Double.parseDouble(slabToAmount));
                slabMasterTO.setStatFlag("Y");
                slabMasterTO.setStatUpFlag("N");
                String result = delegate.saveUpdateTaxSlabMasterDetails(slabMasterTO, mode);
                refreshSlabMasterForm();
                setMessage(result);
                
                
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setRowValuesSlabMasterGrid() {
        setSlabFromAmount(String.valueOf(currentItem.getRangeFrom()));
        setSlabToAmount(String.valueOf(currentItem.getRangeTo()));
        setAplicableTax(String.valueOf(currentItem.getApplicableTax()));
        setTaxFor(currentItem.getTaxFor());
        disableDelete=false;
        disableTaxFor=true;
    }

    public void viewTaxSlabMasterDetails() {
        try {
            List<HrTaxSlabMasterTO> taxSlabMasterDetails = delegate.getTaxSlabMasterDetails(compCode);
            if (!taxSlabMasterDetails.isEmpty()) {
                slabMasterGrid=new ArrayList<TaxSlabMasterPojo>();
                for (HrTaxSlabMasterTO slabMasterTO : taxSlabMasterDetails) {
                    TaxSlabMasterPojo masterPojo = new TaxSlabMasterPojo();
                    masterPojo.setRangeFrom(String.valueOf(slabMasterTO.getRangeFrom()));
                    masterPojo.setRangeTo(String.valueOf(slabMasterTO.getRangeTo()));
                    masterPojo.setApplicableTax(String.valueOf(slabMasterTO.getApplicableTax()));
                    masterPojo.setEnterBy(slabMasterTO.getEnteredBy());
                    masterPojo.setTaxFor(slabMasterTO.getHrTaxSlabMasterPKTO().getTaxFor());
                    masterPojo.setTaxSlabCode(slabMasterTO.getHrTaxSlabMasterPKTO().getTaxSlabCode());
                    slabMasterGrid.add(masterPojo);
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }


    }

    public void deleteSlabMaster() {
        try {
            message=validation();
            if (message.equalsIgnoreCase("ok")) {
                HrTaxSlabMasterPKTO slabMasterPKTO = new HrTaxSlabMasterPKTO();
                slabMasterPKTO.setCompCode(compCode);
                slabMasterPKTO.setTaxSlabCode(currentItem.getTaxSlabCode());
                slabMasterPKTO.setTaxFor(currentItem.getTaxFor());
                HrTaxSlabMasterTO slabMasterTO = new HrTaxSlabMasterTO();
                slabMasterTO.setHrTaxSlabMasterPKTO(slabMasterPKTO);
                String result = delegate.saveUpdateTaxSlabMasterDetails(slabMasterTO, "delete");
                 refreshSlabMasterForm();
                 setMessage(result);
                 
         }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
         
        
    }

    public void refreshSlabMasterForm() {
        setMessage("");
        setOperation("0");
        setTaxFor("0");
        slabFromAmount = "0.0";
        slabToAmount = "0.0";
        aplicableTax = "0";
        disableDelete=true;
        disableTaxFor=false;
        viewTaxSlabMasterDetails();
    }

    public String btnExitAction() {
        return "case 1";
    }

    public String validation() {
        Validator validator = new Validator();
        if (operation.equalsIgnoreCase("0")) {
            return "Please select an operation !";
        }
        if (slabFromAmount.equalsIgnoreCase("")) {
            return "Please insert From Amount.";
        }
        if (!validator.validateDoubleAll(slabFromAmount)) {
            return "Please insert valid From Amount.";
        }
        if (slabToAmount.equalsIgnoreCase("")) {
            return "Please insert To Amount.";
        }
        if (!validator.validateDoubleAll(slabToAmount)) {
            return "Please insert valid to Amount.";
        }
        if (aplicableTax.equalsIgnoreCase("")) {
            return "Please insert Applicable Tax";
        }
        if (!validator.validateDoubleAll(aplicableTax)) {
            return "Please insert valid applicable tax percent.";
        }
        if (taxFor.equalsIgnoreCase("0")) {
            return "Please select Tax for.";
        }
        if (Double.parseDouble(slabFromAmount) > Double.parseDouble(slabToAmount)) {
            return "From amount can not be greater than To amount.";
        }
        return "ok";
    }

    public String getMaxTaxSlabCode() {
        String maxCode = "0";
        try {
            maxCode = String.valueOf(Integer.parseInt(delegate.getMaxTaxSlabCode(compCode)) + 1);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return maxCode;
    }

    public void setOperationOnBlur() {
        setMessage("");
        if (operation.equalsIgnoreCase("0")) {
            setMessage("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
            mode = "save";
        } else if (operation.equalsIgnoreCase("2")) {
            mode = "edit";
        }
    }
}
