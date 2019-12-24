/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentSecurityMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.investment.GoiMasterTable;
import com.cbs.web.utils.Util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class GoiMaster extends BaseBean {

    private String message;
    private String function;
    private String securityName;
    private String maturityYear;
    private String firstIntDate;
    private String secondIntDate;
    private String gridOperation;
    private String processButtonValue;
    private String confirmationMsg;
    private double pageRoi;
    private boolean addFlag;
    private boolean selectFlag;
    private boolean processVisible;
    private boolean secondDtVisible;
    private boolean snoFlag;
    private List<SelectItem> functionList;
    private List<GoiMasterTable> gridDetail;
    private List<GoiMasterTable> tempGridDetail;
    private GoiMasterTable currentItem;
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Date dt = new Date();

    public List<GoiMasterTable> getTempGridDetail() {
        return tempGridDetail;
    }

    public void setTempGridDetail(List<GoiMasterTable> tempGridDetail) {
        this.tempGridDetail = tempGridDetail;
    }

    public boolean isSnoFlag() {
        return snoFlag;
    }

    public void setSnoFlag(boolean snoFlag) {
        this.snoFlag = snoFlag;
    }

    public boolean isSecondDtVisible() {
        return secondDtVisible;
    }

    public void setSecondDtVisible(boolean secondDtVisible) {
        this.secondDtVisible = secondDtVisible;
    }

    public boolean isAddFlag() {
        return addFlag;
    }

    public void setAddFlag(boolean addFlag) {
        this.addFlag = addFlag;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public GoiMasterTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(GoiMasterTable currentItem) {
        this.currentItem = currentItem;
    }

    public String getFirstIntDate() {
        return firstIntDate;
    }

    public void setFirstIntDate(String firstIntDate) {
        this.firstIntDate = firstIntDate;
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

    public String getGridOperation() {
        return gridOperation;
    }

    public void setGridOperation(String gridOperation) {
        this.gridOperation = gridOperation;
    }

    public String getMaturityYear() {
        return maturityYear;
    }

    public void setMaturityYear(String maturityYear) {
        this.maturityYear = maturityYear;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getPageRoi() {
        return pageRoi;
    }

    public void setPageRoi(double pageRoi) {
        this.pageRoi = pageRoi;
    }

    public String getProcessButtonValue() {
        return processButtonValue;
    }

    public void setProcessButtonValue(String processButtonValue) {
        this.processButtonValue = processButtonValue;
    }

    public boolean isProcessVisible() {
        return processVisible;
    }

    public void setProcessVisible(boolean processVisible) {
        this.processVisible = processVisible;
    }

    public String getSecondIntDate() {
        return secondIntDate;
    }

    public void setSecondIntDate(String secondIntDate) {
        this.secondIntDate = secondIntDate;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public boolean isSelectFlag() {
        return selectFlag;
    }

    public void setSelectFlag(boolean selectFlag) {
        this.selectFlag = selectFlag;
    }

    public List<GoiMasterTable> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<GoiMasterTable> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public GoiMaster() {
        resetForm();
        try {
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            addFunctionList();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void addFunctionList() {
        functionList = new ArrayList<SelectItem>();
        functionList.add(new SelectItem("A", "ADD"));

        functionList.add(new SelectItem("M", "MODIFY"));
        functionList.add(new SelectItem("D", "DELETE"));
    }

    public void onChangeFunction() {
        this.setMessage("");
        fieldReset();
        setProcessButtonCaption();

        gridDetail = new ArrayList<GoiMasterTable>();
        if (this.function.equals("M") || this.function.equals("D")) {
            this.selectFlag = true;
            this.addFlag = false;
            this.snoFlag = true;
            this.setGridOperation("Select");
            getGridForDeleteAndModify(onChangeMessage());
        } else if (this.function.equals("A")) {
            this.selectFlag = false;
            this.addFlag = true;
            this.snoFlag = false;
            this.setMessage("Fill all the required fields to add a security !");
            this.setGridOperation("Delete");
        }
    }

    public String onChangeMessage() {
        String onChangeMsg = "";
        if (this.function.equals("M")) {
            onChangeMsg = "Select a row from table to modify an entry !";
        } else if (this.function.equals("D")) {
            onChangeMsg = "Select a row from table to delete an entry !";
        }
        return onChangeMsg;
    }

    public void setProcessButtonCaption() {
        this.setProcessVisible(true);
        if (this.function.equals("A")) {
            this.setProcessButtonValue("Save");
        } else if (this.function.equals("M")) {
            this.setProcessButtonValue("Modify");
        } else if (this.function.equals("D")) {
            this.setProcessButtonValue("Delete");
        }
    }

    public void getGridForDeleteAndModify(String msg) {
        gridDetail = new ArrayList<GoiMasterTable>();
        List<InvestmentSecurityMaster> resultList = new ArrayList();
        try {
            if (this.function.equalsIgnoreCase("M")) {
                resultList = remoteObj.getInvestmentSecurityToModify(dmy.format(dt));
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        InvestmentSecurityMaster entity = resultList.get(i);
                        GoiMasterTable tableObj = new GoiMasterTable();
                        tableObj.setSno(entity.getSno());
                        tableObj.setDate(dmy.format(entity.getDt()));
                        tableObj.setSecurityName(entity.getSecurityName());
                        tableObj.setMaturityYear(entity.getMaturityYear());
                        tableObj.setFirstDate(entity.getIntPayableFirstDate());
                        tableObj.setSecondDate(entity.getIntPayableSecondDate());
                        tableObj.setRoi(entity.getRoi());
                        tableObj.setDelFlag(entity.getDelFlag());
                        tableObj.setEnterby(entity.getEnterBy());
                        tableObj.setAuthBy(entity.getAuthBy());
                        tableObj.setAuth(entity.getAuth());
                        tableObj.setLastUpdateBy(entity.getLastUpdateBy());
                        tableObj.setTranTime(dmy.format(entity.getTranTime()));
                        gridDetail.add(tableObj);
                    }
                    this.setMessage(msg);
                } else {
                    gridDetail = new ArrayList<GoiMasterTable>();
                    if (msg.contains("successfully")) {
                        msg = msg + "Now, There is no record to modify.";
                    } else {
                        msg = "Now, There is no record to modify.";
                    }
                    this.setMessage(msg);
                    return;
                }
            } else if (this.function.equalsIgnoreCase("D")) {
                resultList = remoteObj.getInvestmentSecurityToDelete(dmy.format(dt));
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        InvestmentSecurityMaster entity = resultList.get(i);
                        GoiMasterTable tableObj = new GoiMasterTable();
                        tableObj.setSno(entity.getSno());
                        tableObj.setDate(dmy.format(entity.getDt()));
                        tableObj.setSecurityName(entity.getSecurityName());
                        tableObj.setMaturityYear(entity.getMaturityYear());
                        tableObj.setFirstDate(entity.getIntPayableFirstDate());
                        tableObj.setSecondDate(entity.getIntPayableSecondDate());
                        tableObj.setRoi(entity.getRoi());
                        tableObj.setDelFlag(entity.getDelFlag());
                        tableObj.setEnterby(entity.getEnterBy());
                        tableObj.setAuthBy(entity.getAuthBy());
                        tableObj.setAuth(entity.getAuth());
                        tableObj.setLastUpdateBy(entity.getLastUpdateBy());
                        tableObj.setTranTime(dmy.format(entity.getTranTime()));
                        gridDetail.add(tableObj);
                    }
                    this.setMessage(msg);
                } else {
                    gridDetail = new ArrayList<GoiMasterTable>();
                    if (msg.contains("successfully")) {
                        msg = msg + "Now, There is no record to delete.";
                    } else {
                        msg = "Now, There is no record to delete.";
                    }
                    this.setMessage(msg);
                    return;
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void delete() {
        try {
            this.setMessage("");
            gridDetail.remove(currentItem);
            resetForm();
            this.setMessage("Record deleted successfully.");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void setTableDataToForm() {
        this.setMessage("");
        if (currentItem != null) {
            fieldReset();
            setDataInTempGrid();
            this.setSecurityName(currentItem.getSecurityName());
            this.setMaturityYear(currentItem.getMaturityYear());
            this.setPageRoi(currentItem.getRoi());
            this.setFirstIntDate(currentItem.getFirstDate());
            this.setSecondIntDate(currentItem.getSecondDate());
            if (this.function.equals("M")) {
                this.processVisible = true;
                this.setProcessButtonValue("Modify");
                this.setMessage("Now you can change your security detail !");
            } else if (this.function.equals("D")) {
                this.processVisible = false;
                this.setProcessButtonValue("Delete");
                this.setMessage("Now you can delete the selected row from table !");
            }
        } else {
            this.setMessage("Please select a row to operate!");
        }
    }

    public void setDataInTempGrid() {
        try {
            tempGridDetail = new ArrayList<GoiMasterTable>();
            GoiMasterTable tableObj = new GoiMasterTable();
            if (this.function.equals("M")) {
                tableObj.setSno(currentItem.getSno());
                tableObj.setDate(currentItem.getDate());
                tableObj.setDelFlag(currentItem.getDelFlag());
                tableObj.setEnterby(currentItem.getEnterby());
                tableObj.setAuthBy(currentItem.getAuthBy());
                tableObj.setAuth(currentItem.getAuth());
                tableObj.setLastUpdateBy(currentItem.getLastUpdateBy());
                tableObj.setTranTime(currentItem.getTranTime());
                tempGridDetail.add(tableObj);
            } else if (this.function.equals("D")) {
                gridDetail = new ArrayList<GoiMasterTable>();
                tableObj.setSno(currentItem.getSno());
                tableObj.setDate(currentItem.getDate());
                tableObj.setSecurityName(currentItem.getSecurityName());
                tableObj.setMaturityYear(currentItem.getMaturityYear());
                tableObj.setFirstDate(currentItem.getFirstDate());
                tableObj.setSecondDate(currentItem.getSecondDate());
                tableObj.setRoi(currentItem.getRoi());
                tableObj.setDelFlag(currentItem.getDelFlag());
                tableObj.setEnterby(currentItem.getEnterby());
                tableObj.setAuthBy(currentItem.getAuthBy());
                tableObj.setAuth(currentItem.getAuth());
                tableObj.setLastUpdateBy(currentItem.getLastUpdateBy());
                tableObj.setTranTime(currentItem.getTranTime());
                gridDetail.add(tableObj);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void populateMessage() {
        this.setConfirmationMsg("");
        if (this.function.equals("A")) {
            this.setConfirmationMsg("Are you sure to save this entry!");
            return;
        } else if (this.function.equals("M")) {
            this.setConfirmationMsg("Are you sure to modify this entry!");
            return;
        } else if (this.function.equals("D")) {
            this.setConfirmationMsg("Are you sure to delete this entry!");
            return;
        }
    }

    public void processAction() {
        this.setMessage("");
        if (this.function.equals("A")) {
            this.snoFlag = false;
            saveRecord();
        } else if (this.function.equals("M")) {
            this.snoFlag = true;
            modifyRecord();
        } else if (this.function.equals("D")) {
            this.snoFlag = true;
            deleteRecord();
        }
    }

    public void saveRecord() {
        this.setMessage("");
        try {
            if (gridDetail == null || gridDetail.isEmpty()) {
                this.setMessage("There should be data in the table to save.");
                return;
            }

            for (int i = 0; i < gridDetail.size(); i++) {
                GoiMasterTable tableObj = gridDetail.get(i);
                List<InvestmentSecurityMaster> duplicateEntity = remoteObj.checkDuplicate(tableObj.getMaturityYear(), tableObj.getRoi(), tableObj.getSecurityName());
                if (duplicateEntity.isEmpty()) {
//                    InvestmentSecurityMaster entity = new InvestmentSecurityMaster(ymd.parse(ymd.format(dt)), tableObj.getSecurityName(),
//                            tableObj.getMaturityYear(), tableObj.getFirstDate(), tableObj.getSecondDate(), tableObj.getRoi(), "N",
//                            getUserName(), "", "N", "", dt);
                    
                    
                    
                    
                    

                    InvestmentSecurityMaster entity = new InvestmentSecurityMaster(ymd.parse(ymd.format(dt)),
                            tableObj.getSecurityName(), tableObj.getMaturityYear(), tableObj.getFirstDate(),
                            tableObj.getSecondDate(), tableObj.getRoi(), "N", getUserName(),
                            "N", "", "", dt);

                    String result = remoteObj.saveInvestmentSecurityMaster(entity);
                    if (result.equalsIgnoreCase("true")) {
                        resetForm();
                        this.setMessage("Record has been saved succesfully !");
                    } else {
                        this.setMessage(result);
                        return;
                    }
                } else {
                    this.setMessage("This security is already exist !");
                    return;
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void deleteRecord() {
        this.setMessage("");
        try {
            if (gridDetail == null || gridDetail.isEmpty()) {
                this.setMessage("Please select table row to delete the entry !");
                return;
            }
            for (int i = 0; i < gridDetail.size(); i++) {
                GoiMasterTable tableObj = gridDetail.get(i);
                String result = remoteObj.deleteInvestmentSecurityMaster(tableObj.getSno(), this.getUserName(), dt);
                if (result.equalsIgnoreCase("true")) {
                    resetForm();
                    this.setMessage("Record has been deleted successfully !");                    
                    this.processVisible = true;
                } else {
                    this.setMessage(result);
                    return;
                }
            }
            getGridForDeleteAndModify("");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void modifyRecord() {
        this.setMessage("");
        try {
            if (gridDetail == null || gridDetail.isEmpty()) {
                this.setMessage("Please select table row to delete the entry !");
                return;
            }

            for (int i = 0; i < gridDetail.size(); i++) {
                GoiMasterTable tableObj = gridDetail.get(i);
//                InvestmentSecurityMaster entity = new InvestmentSecurityMaster(tableObj.getSno(), dmy.parse(tableObj.getDate()), tableObj.getSecurityName(),
//                        tableObj.getMaturityYear(), tableObj.getFirstDate(), tableObj.getSecondDate(), tableObj.getRoi(), tableObj.getDelFlag(),
//                        tableObj.getEnterby(), tableObj.getAuthBy(), tableObj.getAuth(), this.getUserName(), dt);

                InvestmentSecurityMaster entity = new InvestmentSecurityMaster(tableObj.getSno(),dmy.parse(tableObj.getDate()),
                        tableObj.getSecurityName(), tableObj.getMaturityYear(), tableObj.getFirstDate(),
                        tableObj.getSecondDate(), tableObj.getRoi(), tableObj.getDelFlag(), tableObj.getEnterby(),
                        tableObj.getAuth(), tableObj.getAuthBy(), this.getUserName(), dt);

                String result = remoteObj.modifyInvestmentSecurityMaster(entity);
                if (result.equalsIgnoreCase("true")) {
                    resetForm();
                    this.setMessage("Record has been modified successfully !");                    
                    this.processVisible = true;
                } else {
                    this.setMessage(result);
                    return;
                }
            }
            getGridForDeleteAndModify("");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onBlurSecurityName() {
        this.setMessage("");
        if (this.getSecurityName() == null || this.getSecurityName().equalsIgnoreCase("") || this.getSecurityName().length() == 0) {
            this.setMessage("Please fill security name !");
            return;
        }
    }

    public void onBlurMaturityYear() {
        this.setMessage("");
        if (this.getMaturityYear() == null || this.getMaturityYear().equalsIgnoreCase("") || this.getMaturityYear().length() == 0) {
            this.setMessage("Please fill maturity year !");
            return;
        }
        if (!this.getMaturityYear().matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20|21)\\d\\d)")) {
            this.setMessage("Please fill proper maturity year !");
            return;
        }
        if (this.getMaturityYear().length() < 10) {
            this.setMessage("Please fill proper maturity year !");
            return;
        }
        if (Integer.valueOf(this.maturityYear) < 2012 || Integer.valueOf(this.maturityYear) > 2099) {
            this.setMessage("Please enter correct year in maturity year field !");
            return;
        }
    }

    public void onBlurPageRoi() {
        this.setMessage("");
        if (String.valueOf(this.pageRoi) == null || String.valueOf(this.pageRoi).equalsIgnoreCase("") || String.valueOf(this.pageRoi).length() == 0) {
            this.setMessage("Please enter roi upto two decimal places !");
            return;
        }
        if (this.pageRoi == 0 || this.pageRoi == 0.0) {
            this.setMessage("Please enter roi upto two decimal places !");
            return;
        }
        if (this.pageRoi > 25) {
            this.setMessage("Please enter correct roi upto two decimal places !");
            return;
        }
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher limitCheck = p.matcher(String.valueOf(this.pageRoi));
        if (!limitCheck.matches()) {
            this.setPageRoi(0.0);
            this.setMessage("Please enter only numeric values in roi field upto two decimal places !");
            return;
        }
        if (String.valueOf(this.pageRoi).contains(".")) {
            if (String.valueOf(this.pageRoi).indexOf(".") != String.valueOf(this.pageRoi).lastIndexOf(".")) {
                this.setMessage("Please enter only numeric values in roi field !");
                return;
            } else if (String.valueOf(this.pageRoi).substring(String.valueOf(this.pageRoi).indexOf(".") + 1).length() > 2) {
                this.setMessage("Please enter roi upto two decimal places !");
                return;
            }
        }
    }

    public void onBlurFirstIntDate() {
        this.setMessage("");
        try {
            if (this.getFirstIntDate() == null || this.getFirstIntDate().equalsIgnoreCase("") || this.getFirstIntDate().length() == 0) {
                this.setMessage("Please enter first int.pay.date !");
                return;
            }
            if (this.getFirstIntDate().length() < 5) {
                this.setMessage("Please enter correct first int.pay.date !");
                return;
            }
            if (this.firstIntDate.indexOf("/") != 2) {
                this.setMessage("Please enter correct first int.pay.date !");
                return;
            }
            Integer month = Integer.valueOf(this.getFirstIntDate().substring(3));
            if (month <= 0 || month > 12) {
                this.setMessage("Please enter correct first int.pay.date !");
                return;
            }
            Date lastDt = Util.calculateMonthEndDate(month, Integer.valueOf(ymd.format(dt).substring(0, 4)));
            Integer lastDays = Integer.valueOf(dmy.format(lastDt).substring(0, 2));
            Integer day = Integer.valueOf(this.getFirstIntDate().substring(0, 2));
            if (day <= 0 || day > lastDays) {
                this.setMessage("Please enter correct first int.pay.date !");
                return;
            }

            String firstIntPayDate = this.getFirstIntDate() + "/" + ymd.format(dt).substring(0, 4);
            String secondIntPayDate = CbsUtil.monthAdd(ymd.format(dmy.parse(firstIntPayDate)), 6);
            Date sDt = ymd.parse(secondIntPayDate);
            this.setSecondIntDate(dmy.format(sDt).substring(0, 5));
            if (this.function.equals("A")) {
                this.setGridOperation("Delete");
                this.addFlag = true;
                this.selectFlag = false;
                this.setProcessButtonValue("Save");
                this.processVisible = false;
                this.snoFlag = false;
                this.setMessage("Now you can do either delete or save operation !");
            } else if (this.function.equals("M")) {
                //this.setGridOperation("Select");
                //this.addFlag = false;
                //this.selectFlag = false;
                this.setProcessButtonValue("Modify");
                this.processVisible = false;
                this.snoFlag = false;
                this.setMessage("Now you can modify your changed security detail !");
            } else if (this.function.equals("D")) {
                this.processVisible = true;
            }
            if (this.function.equals("A") || this.function.equals("M")) {
                gridDetail = new ArrayList<GoiMasterTable>();
                GoiMasterTable txnBean = createGridDetailObj();
                gridDetail.add(txnBean);
            }
            //fieldReset();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    private GoiMasterTable createGridDetailObj() {
        GoiMasterTable txnBean = new GoiMasterTable();
        try {
            if (validateFields()) {
                if (this.function.equals("A")) {
                    txnBean.setDate(dmy.format(dt));
                    txnBean.setSecurityName(this.getSecurityName());
                    txnBean.setMaturityYear(this.getMaturityYear());
                    txnBean.setFirstDate(this.getFirstIntDate());
                    txnBean.setSecondDate(this.getSecondIntDate());
                    txnBean.setRoi(this.getPageRoi());
                    txnBean.setEnterby(this.getUserName());
                } else if (this.function.equals("M")) {
                    if (!tempGridDetail.isEmpty()) {
                        for (GoiMasterTable tableObj : tempGridDetail) {
                            txnBean.setSno(tableObj.getSno());
                            txnBean.setDate(tableObj.getDate());
                            txnBean.setSecurityName(this.getSecurityName());
                            txnBean.setMaturityYear(this.getMaturityYear());
                            txnBean.setFirstDate(this.getFirstIntDate());
                            txnBean.setSecondDate(this.getSecondIntDate());
                            txnBean.setRoi(this.getPageRoi());
                            txnBean.setDelFlag(tableObj.getDelFlag());
                            txnBean.setEnterby(tableObj.getEnterby());
                            txnBean.setAuthBy(tableObj.getAuthBy());
                            txnBean.setAuth(tableObj.getAuth());
                            txnBean.setLastUpdateBy(tableObj.getLastUpdateBy());
                            txnBean.setTranTime(tableObj.getTranTime());
                        }
                    }
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return txnBean;
    }

    public boolean validateFields() {
        if (this.getSecurityName() == null || this.getSecurityName().equalsIgnoreCase("") || this.getSecurityName().length() == 0) {
            this.setMessage("Please fill security name !");
            return false;
        }
        if (this.getMaturityYear() == null || this.getMaturityYear().equalsIgnoreCase("") || this.getMaturityYear().length() == 0) {
            this.setMessage("Please fill maturity year !");
            return false;
        }

        if (String.valueOf(this.pageRoi) == null || String.valueOf(this.pageRoi).equalsIgnoreCase("") || String.valueOf(this.pageRoi).length() == 0) {
            this.setMessage("Please fill roi !");
            return false;
        }

        if (this.getFirstIntDate() == null || this.getFirstIntDate().equalsIgnoreCase("") || this.getFirstIntDate().length() == 0) {
            this.setMessage("Please enter first int.pay.date !");
            return false;
        }
        return true;
    }

    public void fieldReset() {
        this.setSecurityName("");
        this.setMaturityYear(dmy.format(dt).substring(6));
        this.setPageRoi(0.0);
        this.setFirstIntDate("");
        this.setSecondIntDate("");
    }

    public void resetForm() {
        try {
            this.setMessage("");
            this.setMessage("Fill all the required fields to add an entry !");
            this.setFunction("A");
            this.setProcessButtonValue("Save");
            this.setGridOperation("");
            this.processVisible = true;
            this.secondDtVisible = true;
            this.addFlag = false;
            this.selectFlag = false;
            this.snoFlag = false;
            this.setConfirmationMsg("");
            this.setFirstIntDate("");
            this.setSecondIntDate("");
            this.setMaturityYear(dmy.format(dt));
            gridDetail = new ArrayList<GoiMasterTable>();
            tempGridDetail = new ArrayList<GoiMasterTable>();
            this.setSecurityName("");
            this.setPageRoi(0.0);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
}
