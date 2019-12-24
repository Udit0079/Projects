/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.payroll;

import com.cbs.web.controller.BaseBean;
import com.hrms.common.complexTO.TaxInvestmentCategoryTO;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrTaxInvestmentCategoryPKTO;
import com.hrms.common.to.HrTaxInvestmentCategoryTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import com.hrms.web.delegate.PersonnelMasterManagementDelegate;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.EmpSearchTable;
import com.hrms.web.pojo.TaxInvestmentCategoryGrid;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class TaxInvestmentCategory extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private String compCode = "0";
    private int defaultComp;
    private String errmsg;
    private String operation;
    private List<SelectItem> operationList;
    private boolean disablesave;
    private boolean disabledelete;
    private boolean disableflag;
    private String empName;
    private String empCode;
    private String empId;
    private Double categoryAmount;
    private Double categoryLimit;
    private String categoryCode;
    private String flag;
    private List<SelectItem> categoryNamelist;
    private List<EmpSearchTable> datagridemp = new ArrayList<EmpSearchTable>();
    private List<TaxInvestmentCategoryGrid> griddata = new ArrayList<TaxInvestmentCategoryGrid>();
    private EmpSearchTable currentItem;
    private TaxInvestmentCategoryGrid currentdata;
    private static final Logger logger = Logger.getLogger(TaxInvestmentCategory.class);
    private ArrayList<SelectItem> resultList;

    /** Creates a new instance of TaxInvestmentCategory */
    public TaxInvestmentCategory() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            initData();
            editGrid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
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

    public List<EmpSearchTable> getDatagridemp() {
        return datagridemp;
    }

    public void setDatagridemp(List<EmpSearchTable> datagridemp) {
        this.datagridemp = datagridemp;
    }

    public EmpSearchTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(EmpSearchTable currentItem) {
        this.currentItem = currentItem;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Double getCategoryAmount() {
        return categoryAmount;
    }

    public void setCategoryAmount(Double categoryAmount) {
        this.categoryAmount = categoryAmount;
    }

    public Double getCategoryLimit() {
        return categoryLimit;
    }

    public void setCategoryLimit(Double categoryLimit) {
        this.categoryLimit = categoryLimit;
    }

    public List<SelectItem> getCategoryNamelist() {
        return categoryNamelist;
    }

    public void setCategoryNamelist(List<SelectItem> categoryNamelist) {
        this.categoryNamelist = categoryNamelist;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public boolean isDisabledelete() {
        return disabledelete;
    }

    public void setDisabledelete(boolean disabledelete) {
        this.disabledelete = disabledelete;
    }

    public boolean isDisablesave() {
        return disablesave;
    }

    public void setDisablesave(boolean disablesave) {
        this.disablesave = disablesave;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isDisableflag() {
        return disableflag;
    }

    public void setDisableflag(boolean disableflag) {
        this.disableflag = disableflag;
    }

    public List<TaxInvestmentCategoryGrid> getGriddata() {
        return griddata;
    }

    public void setGriddata(List<TaxInvestmentCategoryGrid> griddata) {
        this.griddata = griddata;
    }

    public TaxInvestmentCategoryGrid getCurrentdata() {
        return currentdata;
    }

    public void setCurrentdata(TaxInvestmentCategoryGrid currentdata) {
        this.currentdata = currentdata;
    }

    //Initialise data onload
    private void initData() {
        categoryNamelist = new ArrayList<SelectItem>();
        resultList = new ArrayList<SelectItem>();
        try {
            operationList = new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0", "-Select-"));
            operationList.add(new SelectItem("1", "Add"));
            operationList.add(new SelectItem("2", "Edit"));
            PersonnelMasterManagementDelegate managedelegate = new PersonnelMasterManagementDelegate();
            List<HrMstStructTO> categoryNamedata = managedelegate.getInitialData(Integer.parseInt(compCode), "INV%");
            categoryNamelist.add(new SelectItem("Select", "--Select--"));
            for (HrMstStructTO to : categoryNamedata) {
                categoryNamelist.add(new SelectItem(to.getHrMstStructPKTO().getStructCode(), to.getDescription()));
            }
            PayrollMasterManagementDelegate delegateob = new PayrollMasterManagementDelegate();
            setErrmsg("");
            setOperation("0");
            disableflag = true;
            disablesave = true;
            disabledelete = true;

        } catch (Exception e) {
        }
    }

    //Set operation for operationList
    public void setOperationOnBlur() {
        if (operation.equalsIgnoreCase("0")) {
            setErrmsg("Select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
            flag = "SAVE";
            setErrmsg("");
            employeeData();
            disablesave = false;
            disabledelete = true;
            disableflag = false;
        } else if (operation.equalsIgnoreCase("2")) {
            setErrmsg("");
            flag = "EDIT";
            editGrid();
            disablesave = false;
            disabledelete = false;
            disableflag = false;
        }
    }

    public String validation() {
        if (empCode == null) {
            return "Select Data";
        }
        if (empName.equalsIgnoreCase("")) {
            return "Enter Employee Name!";
        }
        if (categoryCode.equalsIgnoreCase("")) {
            return "Select category!";
        }

        if (categoryAmount == null || categoryAmount == 0.0) {
            return "Enter category amount!";
        }
        if (categoryLimit == null || categoryLimit == 0.0) {
            return "Enter category limit!";
        }
        if (categoryAmount > categoryLimit) {
            return "Category amount can not be greater than category limit!";
        }

        return "ok";
    }

    // To refresh the page
    public void refreshButton() {
        setErrmsg("");
        disablesave = true;
        disabledelete = true;
        setOperation("0");
        griddata.clear();
        setEmpName("");
        setCategoryCode("");
        setCategoryAmount(0.0);
        setCategoryLimit(0.0);
        griddata = new ArrayList<TaxInvestmentCategoryGrid>();
    }

    public void clear() {

        disablesave = true;
        disabledelete = true;
        setOperation("0");
        griddata.clear();
        setEmpName("");
        setCategoryCode("");
        setCategoryAmount(0.0);
        setCategoryLimit(0.0);
        griddata = new ArrayList<TaxInvestmentCategoryGrid>();
    }

    public String btnExit() {
        return "case1";
    }

    // Show Employee Grid
    public void employeeData() {
        datagridemp.clear();
        try {
            PersonnelMasterManagementDelegate delegateobj = new PersonnelMasterManagementDelegate();
            List result = delegateobj.viewEmployeeForTaxSearch();
            Iterator itr = result.iterator();
            while (itr.hasNext()) {
                Object[] data = (Object[]) itr.next();
                EmpSearchTable grid = new EmpSearchTable();
                grid.setEmpCode(data[0] != null ? data[0].toString() : "");
                grid.setEmpidD(data[1] != null ? data[1].toString() : "");
                grid.setEmpnameD(data[2] != null ? data[2].toString() : "");
                datagridemp.add(grid);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Select Employee Details
    public void selectDataEmpMethod() {
        datagridemp.clear();
        try {
            setEmpCode(currentItem.getEmpCode());
            setEmpName(currentItem.getEmpidD());
            setEmpId(currentItem.getEmpnameD());

        } catch (Exception e3) {
            e3.printStackTrace();
        }

    }
    // Edit Grid Data

    public void editGrid() {
        try {
            PayrollMasterManagementDelegate delegateobj = new PayrollMasterManagementDelegate();
            List<TaxInvestmentCategoryTO> result = delegateobj.viewDataTaxInvestmentCategory(Integer.parseInt(compCode));
            if (result.isEmpty()) {
                setErrmsg("");
            }

            griddata = new ArrayList<TaxInvestmentCategoryGrid>();
            TaxInvestmentCategoryGrid item;
            for (TaxInvestmentCategoryTO obj : result) {
                item = new TaxInvestmentCategoryGrid();
                item.setEmpIds(obj.getToObj().getHrTaxInvestmentCategoryPKTO().getEmpCode());
                item.setEmpnameT(obj.getEmpName());
                item.setCategoryCode(obj.getToObj().getHrTaxInvestmentCategoryPKTO().getCategoryCode());
                item.setCategoryAmt(obj.getToObj().getCategoryAmt());
                item.setCategoryMaxLimit(obj.getToObj().getCategoryMaxLimit());
                griddata.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    // Select data to perform edit
    public void selectDataCurrent() throws Exception {
        setErrmsg("");
        disabledelete = false;
        disablesave = false;
        setEmpCode(String.valueOf(currentdata.getEmpIds()));
        setEmpName(currentdata.getEmpnameT());
        setCategoryCode(currentdata.getCategoryCode());
        setCategoryAmount(currentdata.getCategoryAmt());
        setCategoryLimit(currentdata.getCategoryMaxLimit());

    }

    // Perform save and update
    public void save() {
        errmsg = validation();
        if (errmsg.equalsIgnoreCase("Ok")) {
            HrTaxInvestmentCategoryTO hrTaxInvestmentCategoryTO = new HrTaxInvestmentCategoryTO();
            HrTaxInvestmentCategoryPKTO hrTaxInvestmentCategoryPKTO = new HrTaxInvestmentCategoryPKTO();
            hrTaxInvestmentCategoryPKTO.setCompCode(Integer.parseInt(compCode));
            hrTaxInvestmentCategoryPKTO.setEmpCode(new BigInteger(getEmpCode()));
            hrTaxInvestmentCategoryPKTO.setCategoryCode(categoryCode);
            hrTaxInvestmentCategoryTO.setAuthBy(getUserName());
            hrTaxInvestmentCategoryTO.setCategoryAmt(getCategoryAmount());
            hrTaxInvestmentCategoryTO.setCategoryMaxLimit(getCategoryLimit());
            hrTaxInvestmentCategoryTO.setDefaultComp(Integer.parseInt(compCode));
            hrTaxInvestmentCategoryTO.setEnteredBy(getUserName());
            hrTaxInvestmentCategoryTO.setModDate(new java.util.Date());
            hrTaxInvestmentCategoryTO.setHrTaxInvestmentCategoryPKTO(hrTaxInvestmentCategoryPKTO);
            try {
                PayrollMasterManagementDelegate payrollDelegate = new PayrollMasterManagementDelegate();

                String result = payrollDelegate.performOprationSaveTaxinvestment(flag, hrTaxInvestmentCategoryTO);
                clear();
                setErrmsg(result);

            } catch (ApplicationException e) {
                logger.error("Exception occured while executing method saveData()", e);
                this.setErrmsg("You may be trying to save duplicate entry!!");
            }  catch (Exception e) {
                logger.error("Exception occured while executing method saveData()", e);
                this.setErrmsg("You may be trying to save duplicate entry!!");
            }

        }
    }

    //Perform Delete operation
    public void delete() {
        flag = "DELETE";
        HrTaxInvestmentCategoryTO hrTaxInvestmentCategoryTO = new HrTaxInvestmentCategoryTO();
        HrTaxInvestmentCategoryPKTO hrTaxInvestmentCategoryPKTO = new HrTaxInvestmentCategoryPKTO();
        hrTaxInvestmentCategoryPKTO.setCompCode(Integer.parseInt(compCode));
        hrTaxInvestmentCategoryPKTO.setEmpCode(new BigInteger(getEmpCode()));
        hrTaxInvestmentCategoryPKTO.setCategoryCode(String.valueOf(getCategoryCode()));
        hrTaxInvestmentCategoryTO.setHrTaxInvestmentCategoryPKTO(hrTaxInvestmentCategoryPKTO);
        try {
            PayrollMasterManagementDelegate payrollDelegate = new PayrollMasterManagementDelegate();
            String result = payrollDelegate.performOprationSaveTaxinvestment(flag, hrTaxInvestmentCategoryTO);
            clear();
            setErrmsg(result);
        } catch (ApplicationException e) {
            logger.error("Exception occured while executing method selectDataEmpMethodview()", e);
            this.setErrmsg("");
        }  catch (Exception e) {
            logger.error("Exception occured while executing method selectDataEmpMethodview()", e);
            this.setErrmsg(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }
}
