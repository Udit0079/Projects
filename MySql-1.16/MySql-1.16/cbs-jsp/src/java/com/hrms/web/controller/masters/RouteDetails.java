/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.masters;

import com.cbs.servlets.Init;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.web.pojo.RouteDeatilsTable;
import com.hrms.common.to.HrMstRoutePKTO;
import com.hrms.common.to.HrMstRouteTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.delegate.DefinitionsDelegate;
import com.hrms.web.exception.WebException;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class RouteDetails {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(RouteDetails.class);
    private String routeStart,
            routeEnd,
            routeVia,
            statFlag,
            statUpFlag,
            todayDate,
            userName,
            message,
            calFromDate,
            calToDate,
            mode,
            enteredBy,
            authBy,
            routeCode;
    Calendar cal;
    private HttpServletRequest request;
    List<RouteDeatilsTable> routeDetailsTable;
    RouteDeatilsTable currentRouteRow;
    private RouteDeatilsTable currentRouteItem = new RouteDeatilsTable();
    private int defaultComp,
            compCode,
            totalRouteRecords,
            currentRow;
    DefinitionsDelegate definitionsDelegate;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private boolean disableAddButton,
            disableSaveButton,
            disableEditButton,
            disableDeleteButton,
            showRoutetable,
            disableRouteCode,
            disableRouteStart,
            disableRouteEnd,
            disableRouteVia,
            routeFound;
    private String operation;
    private List<SelectItem> operationList;

    public RouteDetails() {
        try {
            definitionsDelegate = new DefinitionsDelegate();
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","-Select-"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","View"));
            Date date = new Date();
            cal = new GregorianCalendar();
            cal.setTime(date);
            try {
                request = getRequest();
                userName = request.getRemoteUser();
                InetAddress iAddress = InetAddress.getByName(WebUtil.getClientIP(request));
                compCode = Integer.parseInt(Init.IP2BR.getBranch(iAddress));
            } catch (Exception e) {
            }
            companyMasterTOs = webUtil.getCompanyMasterTO(compCode);
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            todayDate = dmyFormat.format(date);
            authBy = userName;
            enteredBy = userName;
            statFlag = "Y";
            statUpFlag = "Y";
            onLoadMode();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method RouteDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method RouteDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }

    }

    public HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public void setRouteRowValues() {
        try {
            setMessage("");
            enableFields();
            setDisableAddButton(true);
            setDisableEditButton(true);
            setDisableSaveButton(false);
            setDisableDeleteButton(false);
            setDisableRouteCode(true);
            mode = "update";
            setRouteCode(currentRouteItem.getCode());
            setRouteStart(currentRouteItem.getStart());
            setRouteEnd(currentRouteItem.getEnd());
            setRouteVia(currentRouteItem.getVia());
        } catch (Exception e) {
            logger.error("Exception occured while executing method setRouteRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public boolean routeValidation() {
        try {
            List<HrMstRouteTO> hrMstRouteTOs = definitionsDelegate.getAllByRouteNo(compCode);
            for (HrMstRouteTO hrMstRouteTO : hrMstRouteTOs) {
                if (routeCode.equalsIgnoreCase(hrMstRouteTO.getHrMstRoutePKTO().getRouteCode())) {
                    routeFound = true;
                    routeCode = hrMstRouteTO.getHrMstRoutePKTO().getRouteCode();
                    routeStart = hrMstRouteTO.getRouteStart();
                    routeVia = hrMstRouteTO.getRouteVia();
                    routeEnd = hrMstRouteTO.getRouteEnd();
                    break;
                } else {
                    routeFound = false;
                }
            }
            if (routeFound == true) {
                setMessage("Route Number " + routeCode + " Is Already Assigned From " + routeStart + " via " + routeVia + " to " + routeEnd + " !!");
                return false;
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method routeValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method routeValidation()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return true;
    }

    public void getRouteDetailsTableData() {
        try {
            routeDetailsTable = new ArrayList<RouteDeatilsTable>();
            int i = 0;
            List<HrMstRouteTO> hrMstRouteTOs = definitionsDelegate.getAllByRouteNo(compCode);
            if (!hrMstRouteTOs.isEmpty()) {
                for (HrMstRouteTO hrMstRouteTO : hrMstRouteTOs) {
                    currentRouteRow = new RouteDeatilsTable();
                    currentRouteRow.setSno(++i);
                    currentRouteRow.setCode(hrMstRouteTO.getHrMstRoutePKTO().getRouteCode());
                    currentRouteRow.setStart(hrMstRouteTO.getRouteStart());
                    currentRouteRow.setEnd(hrMstRouteTO.getRouteEnd());
                    currentRouteRow.setVia(hrMstRouteTO.getRouteVia());
                    routeDetailsTable.add(currentRouteRow);
                }
                totalRouteRecords = i;
                setMessage(totalRouteRecords + " Row(s) Found !!");
                showRoutetable = true;
            } else {
                setMessage("No Route Details Found !!");
                showRoutetable = false;
                setDisableSaveButton(true);
                setDisableDeleteButton(true);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getRouteDetailsTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getRouteDetailsTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void performAction() {
        try {
            HrMstRouteTO hrMstRouteTO = new HrMstRouteTO();
            HrMstRoutePKTO hrMstRoutePKTO = new HrMstRoutePKTO();
            hrMstRoutePKTO.setRouteCode(routeCode);
            hrMstRoutePKTO.setCompCode(compCode);
            hrMstRouteTO.setHrMstRoutePKTO(hrMstRoutePKTO);
            hrMstRouteTO.setRouteStart(routeStart);
            hrMstRouteTO.setRouteEnd(routeEnd);
            hrMstRouteTO.setRouteVia(routeVia);
            hrMstRouteTO.setAuthBy(authBy);
            hrMstRouteTO.setDefaultComp(defaultComp);
            hrMstRouteTO.setEnteredBy(enteredBy);
            hrMstRouteTO.setModDate(Date.class.newInstance());
            hrMstRouteTO.setStatFlag(statFlag);
            hrMstRouteTO.setStatUpFlag(statUpFlag);
            String result = definitionsDelegate.saveRouteDetail(hrMstRouteTO, mode);
            cancelAction();
            onLoadMode();
            setMessage(result);
            setOperation("0");
        } catch (WebException e) {
            logger.error("Exception occured while executing method performAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method performAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public boolean validateData() {
        if (routeCode.equalsIgnoreCase("") || routeCode == null) {
            setMessage("Route code can not be blank");
            return false;
        }

        if (routeStart.equalsIgnoreCase("") || routeStart == null) {
            setMessage("Route start name can not be blank");
            return false;
        }
        if (routeEnd.equalsIgnoreCase("") || routeEnd == null) {
            setMessage("Route end can not be blank");
            return false;
        }
        if (routeVia.equalsIgnoreCase("") || routeVia == null) {
            setMessage("Route via can not be blank");
            return false;
        }
        return true;
    }

    public void addAction() {
        try {
            mode = "save";
            enableFields();
            setDisableSaveButton(false);
            setDisableDeleteButton(true);
            setMessage("Please Fill Details !!");
        } catch (Exception e) {
            logger.error("Exception occured while executing method addAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveAction() {
        try {
            authBy = userName;
            enteredBy = userName;
            if ((mode.equalsIgnoreCase("update") || mode.equalsIgnoreCase("delete"))) {
                routeCode = currentRouteRow.getCode();
                if (validateData()) {
                    performAction();
                }
            } else if (mode.equalsIgnoreCase("save") && routeValidation()) {
                if (validateData()) {
                    performAction();
                }
            }
            
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteAction() {
        try {
            HrMstRouteTO hrMstRouteTO = new HrMstRouteTO();
            HrMstRoutePKTO hrMstRoutePKTO = new HrMstRoutePKTO();
            hrMstRoutePKTO.setRouteCode(routeCode);
            hrMstRoutePKTO.setCompCode(compCode);
            hrMstRouteTO.setHrMstRoutePKTO(hrMstRoutePKTO);
            String result = definitionsDelegate.saveRouteDetail(hrMstRouteTO, "delete");
            cancelAction();
            onLoadMode();
            setMessage(result);
            setOperation("0");
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelAction() {
        onLoadMode();
        setRouteCode("");
        setRouteStart("");
        setRouteEnd("");
        setRouteVia("");
        setOperation("0");
    }

    public String exitAction() {
        try {
            cancelAction();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }

    public void onLoadMode() {
        setMode("save");
        setMessage("");
        setDisableSaveButton(true);
        setDisableDeleteButton(true);
        disableFields();
    }

    private void enableFields() {
        setDisableRouteCode(false);
        setDisableRouteStart(false);
        setDisableRouteEnd(false);
        setDisableRouteVia(false);
    }

    private void disableFields() {
        setDisableRouteCode(true);
        setDisableRouteStart(true);
        setDisableRouteEnd(true);
        setDisableRouteVia(true);
    }
    public void setOperation()
    {
        if(operation.equalsIgnoreCase("0"))
        {
            setMessage("Please select an operation !");
            return;
        }
        else if(operation.equalsIgnoreCase("1"))
        {
            addAction();
        }  
        else if(operation.equalsIgnoreCase("2"))
        {
            getRouteDetailsTableData();
        }
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public String getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(String calFromDate) {
        this.calFromDate = calFromDate;
    }

    public String getCalToDate() {
        return calToDate;
    }

    public void setCalToDate(String calToDate) {
        this.calToDate = calToDate;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public RouteDeatilsTable getCurrentRouteItem() {
        return currentRouteItem;
    }

    public void setCurrentRouteItem(RouteDeatilsTable currentRouteItem) {
        this.currentRouteItem = currentRouteItem;
    }

    public RouteDeatilsTable getCurrentRouteRow() {
        return currentRouteRow;
    }

    public void setCurrentRouteRow(RouteDeatilsTable currentRouteRow) {
        this.currentRouteRow = currentRouteRow;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public SimpleDateFormat getDmyFormat() {
        return dmyFormat;
    }

    public void setDmyFormat(SimpleDateFormat dmyFormat) {
        this.dmyFormat = dmyFormat;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
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

    public DefinitionsDelegate getDefinitionsDelegate() {
        return definitionsDelegate;
    }

    public void setDefinitionsDelegate(DefinitionsDelegate definitionsDelegate) {
        this.definitionsDelegate = definitionsDelegate;
    }

    public List<RouteDeatilsTable> getRouteDetailsTable() {
        return routeDetailsTable;
    }

    public void setRouteDetailsTable(List<RouteDeatilsTable> routeDetailsTable) {
        this.routeDetailsTable = routeDetailsTable;
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

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public int getTotalRouteRecords() {
        return totalRouteRecords;
    }

    public void setTotalRouteRecords(int totalRouteRecords) {
        this.totalRouteRecords = totalRouteRecords;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRouteEnd() {
        return routeEnd;
    }

    public void setRouteEnd(String routeEnd) {
        this.routeEnd = routeEnd;
    }

    public String getRouteStart() {
        return routeStart;
    }

    public void setRouteStart(String routeStart) {
        this.routeStart = routeStart;
    }

    public String getRouteVia() {
        return routeVia;
    }

    public void setRouteVia(String routeVia) {
        this.routeVia = routeVia;
    }

    public boolean isRouteFound() {
        return routeFound;
    }

    public void setRouteFound(boolean routeFound) {
        this.routeFound = routeFound;
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

    public boolean isDisableRouteCode() {
        return disableRouteCode;
    }

    public void setDisableRouteCode(boolean disableRouteCode) {
        this.disableRouteCode = disableRouteCode;
    }

    public boolean isDisableRouteEnd() {
        return disableRouteEnd;
    }

    public void setDisableRouteEnd(boolean disableRouteEnd) {
        this.disableRouteEnd = disableRouteEnd;
    }

    public boolean isDisableRouteStart() {
        return disableRouteStart;
    }

    public void setDisableRouteStart(boolean disableRouteStart) {
        this.disableRouteStart = disableRouteStart;
    }

    public boolean isDisableRouteVia() {
        return disableRouteVia;
    }

    public void setDisableRouteVia(boolean disableRouteVia) {
        this.disableRouteVia = disableRouteVia;
    }

    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }

    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    public boolean isShowRoutetable() {
        return showRoutetable;
    }

    public void setShowRoutetable(boolean showRoutetable) {
        this.showRoutetable = showRoutetable;
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
    
}

