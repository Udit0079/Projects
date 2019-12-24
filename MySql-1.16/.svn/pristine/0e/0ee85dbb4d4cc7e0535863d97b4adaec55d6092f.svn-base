package com.hrms.web.controller.personnel;

import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstBusPKTO;
import com.hrms.common.to.HrMstBusTO;
import com.hrms.common.to.HrMstRoutePKTO;
import com.hrms.common.to.HrMstRouteTO;
import com.hrms.common.to.HrPersonnelTransportPKTO;
import com.hrms.common.to.HrPersonnelTransportTO;
import com.hrms.common.utils.HrmsUtil;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.delegate.DefinitionsDelegate;
import com.hrms.web.exception.WebException;
import com.hrms.web.controller.personnel.PersonnelUtil;
import com.hrms.web.pojo.TransportGrid;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class TransportDetails {

    private static final Logger logger = Logger.getLogger(ExperienceDetails.class);
    PersonnelUtil personnelUtil;
    private int compCode,
            defaultComp,
            currentITransRow;
    private List<SelectItem> busList,
            routeList;
    private long empCode;
    private String mode,
            message,
            busNo,
            routeNo,
            pickUpTime,
            pickUpPoint,
            statFlag,
            statUpFlag,
            authBy,
            enteredBy;
    private Date pickUpTimeDt;
    private DefinitionsDelegate definitionsDelegate;
    private PersonnelDelegate personnelDelegate;
    private boolean disableSaveButton,
            disableEditButton,
            disableDeleteButton,
            disableRoute,
            disableBus,
            disableTime,
            disablePoint,
            showTransportGrid;
    private List<TransportGrid> transportTable;
    private TransportGrid transportGridItem;
    private boolean disableAddButton;
    private Validator validator;

    public TransportDetails() {
        try {
            mode = "save";
            statFlag = "Y";
            statUpFlag = "Y";
            personnelDelegate = new PersonnelDelegate();
            definitionsDelegate = new DefinitionsDelegate();
            personnelUtil = new PersonnelUtil();
            String user = (String) getRequest().getSession().getAttribute("USER_NAME");
            authBy = user;
            enteredBy = user;
            getInitialTransportData();
            onLoadMode();
            validator = new Validator();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method TransportDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method TransportDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getInitialTransportData() {
        try {
            compCode = (Integer) getRequest().getSession().getAttribute("COMP_CODE");
            defaultComp = (Integer) getRequest().getSession().getAttribute("DEFAULT_COMP");
            busList = new ArrayList<SelectItem>();
            busList.add(new SelectItem("0", "--Select--"));
            routeList = new ArrayList<SelectItem>();
            routeList.add(new SelectItem("0", "--Select--"));
            List<HrMstBusTO> hrMstBusTOs = definitionsDelegate.getAllByBusNo(compCode);
            List<HrMstRouteTO> hrMstRouteTOs = definitionsDelegate.getAllByRouteNo(compCode);
            if (!hrMstBusTOs.isEmpty()) {
                for (HrMstBusTO hrMstBusTO : hrMstBusTOs) {
                    busList.add(new SelectItem(hrMstBusTO.getHrMstBusPKTO().getBusNo()));
                }
            } else {
                busList.add(new SelectItem("S", "--No Buses--"));
            }
            if (!hrMstRouteTOs.isEmpty()) {
                for (HrMstRouteTO hrMstRouteTO : hrMstRouteTOs) {
                    routeList.add(new SelectItem(hrMstRouteTO.getHrMstRoutePKTO().getRouteCode()));
                }
            } else {
                routeList.add(new SelectItem("N", "--No Routes--"));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getInitialTransportData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getInitialTransportData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

 
    private boolean validate() {
        if (!validator.validate24HourTime(pickUpTime)) {
            setMessage("Pick Up Time Is In Wrong Format. It Should Be In hh:mm format !!");
            return false;
        }
        return true;
    }

    public void saveTransportDetails() {
        try {
            if (mode.equalsIgnoreCase("save")) {
            } else if (mode.equalsIgnoreCase("update")) {
            }
            if (validate()) {
                String result = personnelDelegate.saveHrPersonnelTransport(getHrPersonnelTransportTO(), mode);
                cancelTransportDetails();
                onLoadMode();
                setMessage(result);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveTransportDetails()", e);
            setMessage("Exception occured while saving data. You may be trying to save duplicate entity!!");
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveTransportDetails()", e);
            setMessage("Exception occured while saving data. You may be trying to save duplicate entity!!");
        }
    }

    public void editTransportDetails() {
        try {
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            compCode = (Integer) getRequest().getSession().getAttribute("COMP_CODE");
            defaultComp = (Integer) getRequest().getSession().getAttribute("DEFAULT_COMP");
            if (empCode != 0) {
                getTransportData();
            } else {
                this.setMessage("Please Select An Employee From General Details Page");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method editTransportDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteTransportDetails() {
        try {
            mode = "delete";
            routeNo = transportGridItem.getRouteCode();
            busNo = transportGridItem.getBusNo();
            String result = personnelDelegate.saveHrPersonnelTransport(getHrPersonnelTransportTO(), mode);
            cancelTransportDetails();
            setMessage(result);
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteTransportDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteTransportDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelTransportDetails() {
        try {
            setMode("save");
            setMessage("");
            setRouteNo("0");
            setBusNo("0");
            setPickUpTime("");
            setPickUpPoint("");
            onLoadMode();
        } catch (Exception e) {
            logger.error("Exception occured while executing method cancelTransportDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String exitTransportDetails() {
        try {
            cancelTransportDetails();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitTransportDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }

    public void setTransportRowValues() {
        try {
            setMode("update");
            setBusNo(transportGridItem.getBusNo());
            setRouteNo(transportGridItem.getRouteCode());
            setPickUpPoint(transportGridItem.getPickUpPoint());
            setPickUpTime(HrmsUtil.correct24HourTime(transportGridItem.getPickUpTime()));
            setMessage("");
            setDisableDeleteButton(false);
        } catch (Exception e) {
            logger.error("Exception occured while executing method setTransportRowValues()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getTransportData() {
        try {
            empCode = (Long) getRequest().getSession().getAttribute("EMP_CODE");
            transportTable = personnelUtil.getTransportData(empCode);
            if (!transportTable.isEmpty()) {
                setShowTransportGrid(true);
                setDisableSaveButton(false);
                setDisableDeleteButton(true);
                setMessage(transportTable.size() + " rows found");
            } else {
                setShowTransportGrid(false);
                setDisableSaveButton(false);
                setDisableDeleteButton(true);
                setMessage("No Transport Found!!");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getTransportData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTransportData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void onLoadMode() {
        setMode("save");
        setMessage("");
        setDisableSaveButton(false);
        setDisableDeleteButton(true);
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public HrPersonnelTransportTO getHrPersonnelTransportTO() {
        HrPersonnelTransportTO hrPersonnelTransportTO = new HrPersonnelTransportTO();
        HrPersonnelTransportPKTO hrPersonnelTransportPKTO = new HrPersonnelTransportPKTO();
        try {
            pickUpTimeDt = personnelUtil.hmmFormat.parse(pickUpTime);
            hrPersonnelTransportPKTO.setBusNo(busNo);
            hrPersonnelTransportPKTO.setCompCode(compCode);
            hrPersonnelTransportPKTO.setEmpCode(empCode);
            hrPersonnelTransportPKTO.setRouteCode(routeNo);
            hrPersonnelTransportTO.setAuthBy(authBy);
            hrPersonnelTransportTO.setDefaultComp(defaultComp);
            hrPersonnelTransportTO.setEnteredBy(enteredBy);
            hrPersonnelTransportTO.setHrMstBusTO(getHrMstBusTO(compCode, empCode));
            hrPersonnelTransportTO.setHrMstRouteTO(getHrMstRouteTO(compCode, empCode));
            hrPersonnelTransportTO.setHrPersonnelDetailsTO(personnelUtil.getHrPersonnelDetailsTO(compCode, empCode));
            hrPersonnelTransportTO.setHrPersonnelTransportPKTO(hrPersonnelTransportPKTO);
            hrPersonnelTransportTO.setModDate(Date.class.newInstance());
            hrPersonnelTransportTO.setPickPnt(pickUpPoint);
            hrPersonnelTransportTO.setPickTm(pickUpTimeDt);
            hrPersonnelTransportTO.setStatFlag(statFlag);
            hrPersonnelTransportTO.setStatUpFlag(statUpFlag);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrPersonnelTransportTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrPersonnelTransportTO;
    }

    public HrMstBusTO getHrMstBusTO(int compCode, long empCode) {
        HrMstBusTO hrMstBusTO = new HrMstBusTO();
        HrMstBusPKTO hrMstBusPKTO = new HrMstBusPKTO();
        try {
            hrMstBusPKTO.setCompCode(compCode);
            hrMstBusPKTO.setBusNo(busNo);
            hrMstBusTO.setHrMstBusPKTO(hrMstBusPKTO);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrMstBusTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrMstBusTO;
    }

    public HrMstRouteTO getHrMstRouteTO(int compCode, long empCode) {
        HrMstRouteTO hrMstRouteTO = new HrMstRouteTO();
        HrMstRoutePKTO hrMstRoutePKTO = new HrMstRoutePKTO();
        try {
            hrMstRoutePKTO.setCompCode(compCode);
            hrMstRoutePKTO.setRouteCode(routeNo);
            hrMstRouteTO.setHrMstRoutePKTO(hrMstRoutePKTO);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrMstRouteTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrMstRouteTO;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public List<SelectItem> getBusList() {
        return busList;
    }

    public void setBusList(List<SelectItem> busList) {
        this.busList = busList;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public int getCurrentITransRow() {
        return currentITransRow;
    }

    public void setCurrentITransRow(int currentITransRow) {
        this.currentITransRow = currentITransRow;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public DefinitionsDelegate getDefinitionsDelegate() {
        return definitionsDelegate;
    }

    public void setDefinitionsDelegate(DefinitionsDelegate definitionsDelegate) {
        this.definitionsDelegate = definitionsDelegate;
    }

    public boolean isDisableAddButton() {
        return disableAddButton;
    }

    public void setDisableAddButton(boolean disableAddButton) {
        this.disableAddButton = disableAddButton;
    }

    public boolean isDisableBus() {
        return disableBus;
    }

    public void setDisableBus(boolean disableBus) {
        this.disableBus = disableBus;
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

    public boolean isDisablePoint() {
        return disablePoint;
    }

    public void setDisablePoint(boolean disablePoint) {
        this.disablePoint = disablePoint;
    }

    public boolean isDisableRoute() {
        return disableRoute;
    }

    public void setDisableRoute(boolean disableRoute) {
        this.disableRoute = disableRoute;
    }

    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }

    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    public boolean isDisableTime() {
        return disableTime;
    }

    public void setDisableTime(boolean disableTime) {
        this.disableTime = disableTime;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
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

    public PersonnelDelegate getPersonnelDelegate() {
        return personnelDelegate;
    }

    public void setPersonnelDelegate(PersonnelDelegate personnelDelegate) {
        this.personnelDelegate = personnelDelegate;
    }

    public PersonnelUtil getPersonnelUtil() {
        return personnelUtil;
    }

    public void setPersonnelUtil(PersonnelUtil personnelUtil) {
        this.personnelUtil = personnelUtil;
    }

    public String getPickUpPoint() {
        return pickUpPoint;
    }

    public void setPickUpPoint(String pickUpPoint) {
        this.pickUpPoint = pickUpPoint;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public Date getPickUpTimeDt() {
        return pickUpTimeDt;
    }

    public void setPickUpTimeDt(Date pickUpTimeDt) {
        this.pickUpTimeDt = pickUpTimeDt;
    }

    public List<SelectItem> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<SelectItem> routeList) {
        this.routeList = routeList;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public boolean isShowTransportGrid() {
        return showTransportGrid;
    }

    public void setShowTransportGrid(boolean showTransportGrid) {
        this.showTransportGrid = showTransportGrid;
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

    public TransportGrid getTransportGridItem() {
        return transportGridItem;
    }

    public void setTransportGridItem(TransportGrid transportGridItem) {
        this.transportGridItem = transportGridItem;
    }

    public List<TransportGrid> getTransportTable() {
        return transportTable;
    }

    public void setTransportTable(List<TransportGrid> transportTable) {
        this.transportTable = transportTable;
    }
}
