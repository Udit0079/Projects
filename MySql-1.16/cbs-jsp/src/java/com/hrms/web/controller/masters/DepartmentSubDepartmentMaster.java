package com.hrms.web.controller.masters;

import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrMstDeptSubdeptPKTO;
import com.hrms.common.to.HrMstDeptSubdeptTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.delegate.DefinitionsDelegate;
import com.hrms.web.exception.WebException;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

public class DepartmentSubDepartmentMaster extends BaseBean{

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(BusMaster.class);
    private int defaultComp;
    private int compCode;
    private String statFlag;
    private String statUpFlag;
    private String message;
    private String calFromDate;
    private String calToDate;
    Calendar cal;
    private String mode = "";
    private String enteredBy;
    private String authBy;
    String structCode;
    List<SelectItem> deptList;
    List<SelectItem> subDeptAvailableList;
    List<SelectItem> subDeptAssignedList;
    private DefinitionsDelegate definitionsDelegate;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String deptValue;
    private String[] subDeptValues;
    private String[] subDeptAssignedValues;
    private String deptName;
    private String[] subDeptNames;
    List<HrMstStructTO> hrMstStructTOs;

    public DepartmentSubDepartmentMaster() {
        try {
            definitionsDelegate = new DefinitionsDelegate();
            Date date = new Date();
            cal = new GregorianCalendar();
            cal.setTime(date);
            compCode = Integer.parseInt(getOrgnBrCode());
            companyMasterTOs = webUtil.getCompanyMasterTO(compCode);
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            authBy = getUserName();
            enteredBy = getUserName();
            statFlag = "Y";
            statUpFlag = "Y";
            mode = "save";
            getDepartment();
            getSubDepartment();
            subDeptAssignedList = new ArrayList<SelectItem>();
            subDeptAssignedList.add(new SelectItem("--Select--"));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method DepartmentSubDepartmentMaster()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method DepartmentSubDepartmentMaster()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getDepartment() {
        try {
            deptList = new ArrayList<SelectItem>();
            deptList.add(new SelectItem("--Select--"));
            List<HrMstStructTO> HrMstStructTOs = definitionsDelegate.getInitialData(compCode, "DEP%");
            for (HrMstStructTO hrMstStructTO : HrMstStructTOs) {
                deptList.add(new SelectItem(hrMstStructTO.getHrMstStructPKTO().getStructCode(), hrMstStructTO.getDescription()));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getDepartment()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getDepartment()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getSubDepartment() {
        try {
            subDeptAvailableList = new ArrayList<SelectItem>();
            List data = definitionsDelegate.getSubDepartment(compCode);
            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) {
                    Object[] ob = (Object[]) data.get(i);
                    subDeptAvailableList.add(new SelectItem(ob[0].toString(), ob[1].toString()));
                }
            } else {
                subDeptAvailableList.add(new SelectItem("--Select--"));
            }
            getSubDepartmentsAssigned();
        } catch (WebException e) {
            logger.error("Exception occured while executing method getSubDepartment()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSubDepartment()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getSubDepartmentsAssigned() {
        try {
            subDeptAssignedList = new ArrayList<SelectItem>();
            List data = definitionsDelegate.getSubDepartmentsAssigned(compCode, deptValue);
            if (!data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) {
                    Object[] ob = (Object[]) data.get(i);
                    subDeptAssignedList.add(new SelectItem(ob[0].toString(), ob[1].toString()));
                }
            } else {
                subDeptAssignedList.add(new SelectItem("--Select--"));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getSubDepartmentsAssigned()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSubDepartmentsAssigned()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveAction() {
        try {
            mode = "save";
            if (mode.equalsIgnoreCase("save")) {
                if (subDeptValues.length > 0) {
                    for (int i = 0; i < subDeptValues.length; i++) {
                        if (!subDeptValues[i].equalsIgnoreCase("--Select--")) {
                            performAction(deptValue, subDeptValues[i]);
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

    public void performAction(String deptCodeNew, String subDeptCodeNew) {
        try {
            authBy = getUserName();
            enteredBy = getUserName();
            HrMstDeptSubdeptTO hrMstDeptSubdeptTO = new HrMstDeptSubdeptTO();
            HrMstDeptSubdeptPKTO hrMstDeptSubdeptPKTO = new HrMstDeptSubdeptPKTO();
            hrMstDeptSubdeptPKTO.setCompCode(compCode);
            hrMstDeptSubdeptPKTO.setDeptCode(deptCodeNew);
            hrMstDeptSubdeptPKTO.setSubDeptCode(subDeptCodeNew);
            hrMstDeptSubdeptTO.setHrMstDeptSubdeptPKTO(hrMstDeptSubdeptPKTO);
            hrMstDeptSubdeptTO.setAuthBy(authBy);
            hrMstDeptSubdeptTO.setDefaultComp(defaultComp);
            hrMstDeptSubdeptTO.setEnteredBy(enteredBy);
            hrMstDeptSubdeptTO.setModDate(Date.class.newInstance());
            hrMstDeptSubdeptTO.setStatFlag(statFlag);
            hrMstDeptSubdeptTO.setStatUpFlag(statUpFlag);
            String result = definitionsDelegate.saveDepartmentSubDepartmentMasterDetail(hrMstDeptSubdeptTO, mode);
            setMessage(result);
            getSubDepartment();
        } catch (WebException e) {
            logger.error("Exception occured while executing method performAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method performAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelAction() {
        setMessage("");
        setDeptValue("--Select--");
        getSubDepartment();
        subDeptAssignedList = new ArrayList<SelectItem>();
        subDeptAssignedList.add(new SelectItem("--Select--"));
    }

    public void deleteAction() {
        mode = "delete";
        try {
            if (mode.equalsIgnoreCase("delete")) {
                if (subDeptAssignedValues.length > 0) {
                    for (int i = 0; i < subDeptAssignedValues.length; i++) {
                        if (!subDeptAssignedValues[i].equalsIgnoreCase("--Select--")) {
                            performAction(deptValue, subDeptAssignedValues[i]);
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

    public String getDeptValue() {
        return deptValue;
    }

    public void setDeptValue(String deptValue) {
        this.deptValue = deptValue;
    }

    public String[] getSubDeptValues() {
        return subDeptValues;
    }

    public void setSubDeptValues(String[] subDeptValues) {
        this.subDeptValues = subDeptValues;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String[] getSubDeptNames() {
        return subDeptNames;
    }

    public void setSubDeptNames(String[] subDeptNames) {
        this.subDeptNames = subDeptNames;
    }

    public List<SelectItem> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<SelectItem> deptList) {
        this.deptList = deptList;
    }

    public List<SelectItem> getSubDeptAssignedList() {
        return subDeptAssignedList;
    }

    public void setSubDeptAssignedList(List<SelectItem> subDeptAssignedList) {
        this.subDeptAssignedList = subDeptAssignedList;
    }

    public List<SelectItem> getSubDeptAvailableList() {
        return subDeptAvailableList;
    }

    public void setSubDeptAvailableList(List<SelectItem> subDeptAvailableList) {
        this.subDeptAvailableList = subDeptAvailableList;
    }

    public String[] getSubDeptAssignedValues() {
        return subDeptAssignedValues;
    }

    public void setSubDeptAssignedValues(String[] subDeptAssignedValues) {
        this.subDeptAssignedValues = subDeptAssignedValues;
    }
}
