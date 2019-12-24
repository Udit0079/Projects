package com.hrms.web.controller.masters;

import com.cbs.servlets.Init;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrMstStructPKTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrMstZoneLocationPKTO;
import com.hrms.common.to.HrMstZoneLocationTO;
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

public class ZoneLocationMaster {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(ZoneLocationMaster.class);
    private int defaultComp;
    private int compCode;
    private String statFlag;
    private String statUpFlag;
    private String todayDate;
    private String userName;
    private String message;
    private String calFromDate;
    private String calToDate;
    Calendar cal;
    private HttpServletRequest request;
    private String mode = "";
    private String enteredBy;
    private String authBy;
    String structCode;
    List<SelectItem> zoneList;
    List<SelectItem> locationAvailableList;
    List<SelectItem> locationAssignedList;
    private DefinitionsDelegate definitionsDelegate;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String zoneValue;
    private String[] locationValues;
    private String[] locationAssignedValues;
    List<HrMstStructTO> hrMstStructTOs;

    public ZoneLocationMaster() {
        try {
            definitionsDelegate = new DefinitionsDelegate();
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
            mode = "save";
            locationAssignedList = new ArrayList<SelectItem>();
            locationAssignedList.add(new SelectItem("--Select--"));
            getZones();
            getAvailableLocations();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method ZoneLocationMaster()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method ZoneLocationMaster()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getZones() {
        try {
            zoneList = new ArrayList<SelectItem>();
            zoneList.add(new SelectItem("--Select--"));
            List<HrMstStructTO> HrMstStructTOs = definitionsDelegate.getInitialData(compCode, "ZON%");
            for (HrMstStructTO hrMstStructTO : HrMstStructTOs) {
                zoneList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getZones()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getZones()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getAvailableLocations() {
        try {
            locationAvailableList = new ArrayList<SelectItem>();
            List data = definitionsDelegate.getAvailableLocations(compCode);
            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) {
                    Object[] ob = (Object[]) data.get(i);
                    locationAvailableList.add(new SelectItem(ob[0].toString(), ob[1].toString()));
                }
            } else {
                locationAvailableList.add(new SelectItem("--Select--"));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getAvailableLocations()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAvailableLocations()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getAssignedLocations() {
        try {

            locationAssignedList = new ArrayList<SelectItem>();
            List data = definitionsDelegate.getAssignedLocations(compCode, zoneValue);
            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) {
                    Object[] ob = (Object[]) data.get(i);
                    locationAssignedList.add(new SelectItem(ob[0].toString(), ob[1].toString()));
                }
            } else {
                locationAssignedList.add(new SelectItem("--Select--"));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getAssignedLocations()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAssignedLocations()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void performAction(String zoneCodeNew, String locationCodeNew) {
        try {
            authBy = userName;
            enteredBy = userName;
            HrMstZoneLocationTO hrMstZoneLocationTO = new HrMstZoneLocationTO();
            HrMstZoneLocationPKTO hrMstZoneLocationPKTO = new HrMstZoneLocationPKTO();
            hrMstZoneLocationPKTO.setCompCode(compCode);
            hrMstZoneLocationPKTO.setZoneCode(zoneCodeNew);
            hrMstZoneLocationPKTO.setLocationCode(locationCodeNew);
            hrMstZoneLocationTO.setHrMstZoneLocationPKTO(hrMstZoneLocationPKTO);
            hrMstZoneLocationTO.setAuthBy(authBy);
            hrMstZoneLocationTO.setDefaultComp(defaultComp);
            hrMstZoneLocationTO.setEnteredBy(enteredBy);
            hrMstZoneLocationTO.setModDate(Date.class.newInstance());
            hrMstZoneLocationTO.setStatFlag(statFlag);
            hrMstZoneLocationTO.setStatUpFlag(statUpFlag);
            HrMstStructTO hrMstStructTO = new HrMstStructTO();
            HrMstStructPKTO hrMstStructPKTO = new HrMstStructPKTO();
            hrMstStructPKTO.setCompCode(compCode);
            hrMstStructPKTO.setStructCode(zoneCodeNew);
            hrMstStructTO.setHrMstStructPKTO(hrMstStructPKTO);
            HrMstStructTO hrMstStruct1TO = new HrMstStructTO();
            HrMstStructPKTO hrMstStruct1PKTO = new HrMstStructPKTO();
            hrMstStruct1PKTO.setCompCode(compCode);
            hrMstStruct1PKTO.setStructCode(locationCodeNew);
            hrMstStruct1TO.setHrMstStructPKTO(hrMstStruct1PKTO);
            hrMstZoneLocationTO.setHrMstStructTO(hrMstStructTO);
            hrMstZoneLocationTO.setHrMstStruct1TO(hrMstStruct1TO);
            String result = definitionsDelegate.saveZoneLocationMasterDetail(hrMstZoneLocationTO, mode);
            setMessage(result);
            getAvailableLocations();
            getAssignedLocations();
        } catch (WebException e) {
            logger.error("Exception occured while executing method performAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method performAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveAction() {
        try {
            mode = "save";
            if (mode.equalsIgnoreCase("save")) {
                if (locationValues.length > 0) {
                    for (int i = 0; i < locationValues.length; i++) {
                        if (!locationValues[i].equalsIgnoreCase("--Select--")) {
                            performAction(zoneValue, locationValues[i]);
                        }
                    }
                } else {
                    setMessage("No Appropriate Data To Save !!");
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelAction() {
        try {
            setMessage("");
            setZoneValue("--Select--");
            locationAssignedList = new ArrayList<SelectItem>();
            locationAssignedList.add(new SelectItem("--Select--"));
            getAvailableLocations();
        } catch (Exception e) {
            logger.error("Exception occured while executing method cancelAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteAction() {
        mode = "delete";
        try {
            if (mode.equalsIgnoreCase("delete")) {
                if (locationAssignedValues.length > 0) {
                    for (int i = 0; i < locationAssignedValues.length; i++) {
                        if (!locationAssignedValues[i].equalsIgnoreCase("--Select--")) {
                            performAction(zoneValue, locationAssignedValues[i]);
                        }
                    }
                } else {
                    setMessage("No Appropriate Data To Delete !!");
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
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

    public HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
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

    public List<HrMstStructTO> getHrMstStructTOs() {
        return hrMstStructTOs;
    }

    public void setHrMstStructTOs(List<HrMstStructTO> hrMstStructTOs) {
        this.hrMstStructTOs = hrMstStructTOs;
    }

    public List<SelectItem> getLocationAssignedList() {
        return locationAssignedList;
    }

    public void setLocationAssignedList(List<SelectItem> locationAssignedList) {
        this.locationAssignedList = locationAssignedList;
    }

    public String[] getLocationAssignedValues() {
        return locationAssignedValues;
    }

    public void setLocationAssignedValues(String[] locationAssignedValues) {
        this.locationAssignedValues = locationAssignedValues;
    }

    public List<SelectItem> getLocationAvailableList() {
        return locationAvailableList;
    }

    public void setLocationAvailableList(List<SelectItem> locationAvailableList) {
        this.locationAvailableList = locationAvailableList;
    }

    public String[] getLocationValues() {
        return locationValues;
    }

    public void setLocationValues(String[] locationValues) {
        this.locationValues = locationValues;
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

    public String getStructCode() {
        return structCode;
    }

    public void setStructCode(String structCode) {
        this.structCode = structCode;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<SelectItem> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<SelectItem> zoneList) {
        this.zoneList = zoneList;
    }

    public String getZoneValue() {
        return zoneValue;
    }

    public void setZoneValue(String zoneValue) {
        this.zoneValue = zoneValue;
    }
}
