/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.masters;

import com.hrms.web.pojo.ParameterTable;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrMstStructPKTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.ParametersTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.delegate.ParametersDelegate;
import com.hrms.web.exception.WebException;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Zeeshan Waris
 */
public class Parameters extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(Parameters.class);
    private String message;
    private String parameterName;
    private String description;
    private String paramType;
    private String compCode = "0";
    private String strCode;
    private boolean parameterNameDisable;
    private boolean saveDisable;
    private boolean updateNameDisable;
    private List<SelectItem> parameterList;
    private List<ParameterTable> instrReg;
    private int currentRow, defaultComp;
    private ParameterTable currentItem = new ParameterTable();
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public boolean isUpdateNameDisable() {
        return updateNameDisable;
    }

    public void setUpdateNameDisable(boolean updateNameDisable) {
        this.updateNameDisable = updateNameDisable;
    }

    public boolean isParameterNameDisable() {
        return parameterNameDisable;
    }

    public void setParameterNameDisable(boolean parameterNameDisable) {
        this.parameterNameDisable = parameterNameDisable;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public ParameterTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ParameterTable currentItem) {
        this.currentItem = currentItem;
    }

    public List<ParameterTable> getInstrReg() {
        return instrReg;
    }

    public void setInstrReg(List<ParameterTable> instrReg) {
        this.instrReg = instrReg;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<SelectItem> parameterList) {
        this.parameterList = parameterList;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Parameters() {
        try {
            setCompCode(getOrgnBrCode());
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            setParamType("Parameter");
            setTodayDate(sdf.format(date));
            this.setMessage("");
            updateNameDisable = true;
            parameterList();
        } catch (Exception e) {
            logger.error("Exception occured while executing method Parameters()", e);
            logger.error("Parameters()", e);
        }
    }

    public void parameterList() {
        try {
            ParametersDelegate parametersDelegate = new ParametersDelegate();
            List<ParametersTO> parametersTOs = parametersDelegate.getParameterData();
            parameterList = new ArrayList<SelectItem>();
            parameterList.add(new SelectItem("0", "--Select--"));
            for (ParametersTO structMasterTO : parametersTOs) {
                parameterList.add(new SelectItem(structMasterTO.getDescription(),
                        structMasterTO.getDescription()));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method parameterList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method parameterList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method parameterList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getDescriptionTableDetails() {
        strCode = "";
        int max = 0;
        instrReg = new ArrayList<ParameterTable>();
        try {
            ParametersDelegate parametersDelegate = new ParametersDelegate();
            if (parameterName.equalsIgnoreCase("0")) {
                setParamType("Parameter");
            } else {
                setParamType(parameterName);
            }
            String paramDescription = parameterName.substring(0, 3);
            List<HrMstStructTO> parameterTOs = parametersDelegate.parameterDesList(Integer.parseInt(compCode), paramDescription + "%");
            for (HrMstStructTO paramTO : parameterTOs) {
                ParameterTable param = new ParameterTable();
                param.setStructcode(paramTO.getHrMstStructPKTO().getStructCode());
                param.setCompCode(paramTO.getHrMstStructPKTO().getCompCode());
                int code = Integer.parseInt(paramTO.getHrMstStructPKTO().getStructCode().substring(3));
                if (code > max) {
                    max = code;
                }
                param.setDescription(paramTO.getDescription());
                instrReg.add(param);
            }
            String maxLength = Integer.toString(max + 1);
            int length = maxLength.length();
            int addedZero = 3 - length;
            for (int i = 1; i <= addedZero; i++) {
                maxLength = "0" + maxLength;
            }
            strCode = paramDescription + (maxLength);
        } catch (WebException e) {
            logger.error("Exception occured while executing method getDescriptionTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getDescriptionTableDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getDescriptionTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Description"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (ParameterTable item : instrReg) {
            if (item.getDescription().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void selectParameterCurrentRow() {
        setMessage("");
        updateNameDisable = false;
        saveDisable = true;
        parameterNameDisable = true;
        setDescription("");
        setDescription(currentItem.getDescription());
    }

    public void clear() {
        setDescription("");
    }

    public void refreshButtonAction() {
        updateNameDisable = true;
        saveDisable = false;
        parameterNameDisable = false;
        setMessage("");
        setParameterName("0");
        setDescription("");
        instrReg = new ArrayList<ParameterTable>();
    }

    public void deleteParamAction() {
        try {
            ParametersDelegate parametersDelegate = new ParametersDelegate();
            String rsmsg = parametersDelegate.parameterDelete(currentItem.getCompCode(), currentItem.getStructcode());
            if (rsmsg.equalsIgnoreCase("true")) {
                setMessage("Data deleted successfuly");
            } else {
                setMessage(rsmsg);
            }
            getDescriptionTableDetails();
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteParamAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method deleteParamAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteParamAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveParameterAction() {
        setMessage("");
        try {
            if (parameterName.equalsIgnoreCase("0")) {
                setMessage("Please select the Parameter Name");
                return;
            }
            if (description == null) {
                setMessage("Please fill the Description");
                return;
            }
            if (description.equalsIgnoreCase("")) {
                setMessage("Please fill the Description");
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
            if (strCode == null) {
                setMessage("Please fill the Struct Code");
                return;
            }
            if (strCode.equalsIgnoreCase("")) {
                setMessage("Please fill the Struct Code");
                return;
            }
            String stateFlag = "Y";
            String stateUpflag = "Y";
            HrMstStructTO struct = new HrMstStructTO();
            struct.setDescription(description);
            struct.setDefaultComp(defaultComp);
            struct.setStatFlag(stateFlag);
            struct.setStatUpFlag(stateUpflag);
            struct.setModDate(getDate());
            struct.setAuthBy(getUserName());
            struct.setEnteredBy(getUserName());
            HrMstStructPKTO structPK = new HrMstStructPKTO();
            structPK.setCompCode(Integer.parseInt(compCode));
            structPK.setStructCode(strCode);
            struct.setHrMstStructPKTO(structPK);
            ParametersDelegate parametersDelegate = new ParametersDelegate();
            String result = parametersDelegate.parameterSave(struct);
            setMessage(result);
            getDescriptionTableDetails();
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

    public void updateParameterAction() {
        setMessage("");
        try {
            if (parameterName.equalsIgnoreCase("0")) {
                setMessage("Please select the Parameter Name");
                return;
            }
            if (description == null) {
                setMessage("Please fill the Description");
                return;
            }
            if (description.equalsIgnoreCase("")) {
                setMessage("Please fill the Description");
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
            if (strCode == null) {
                setMessage("Please fill the Struct Code");
                return;
            }
            if (strCode.equalsIgnoreCase("")) {
                setMessage("Please fill the Struct Code");
                return;
            }
            String stateFlag = "Y";
            String stateUpflag = "Y";
            HrMstStructTO struct = new HrMstStructTO();
            struct.setDescription(description);
            struct.setDefaultComp(defaultComp);
            struct.setStatFlag(stateFlag);
            struct.setStatUpFlag(stateUpflag);
            struct.setModDate(getDate());
            struct.setAuthBy(getUserName());
            struct.setEnteredBy(getUserName());
            HrMstStructPKTO structPK = new HrMstStructPKTO();
            structPK.setCompCode(currentItem.getCompCode());
            structPK.setStructCode(currentItem.getStructcode());
            struct.setHrMstStructPKTO(structPK);
            ParametersDelegate parametersDelegate = new ParametersDelegate();
            String result = parametersDelegate.parametersUpdate(struct);
            setMessage(result);
            getDescriptionTableDetails();
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method updateParameterAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method updateParameterAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method updateParameterAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String btnExit() {
        try {
            refreshButtonAction();
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }
}
