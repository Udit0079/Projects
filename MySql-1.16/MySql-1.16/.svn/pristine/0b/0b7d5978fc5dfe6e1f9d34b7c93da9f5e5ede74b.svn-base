/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import com.cbs.exception.ExceptionCode;
import com.cbs.exception.ServiceLocatorException;
import com.hrms.web.pojo.AdvertisementCodeListGrid;
import com.hrms.web.pojo.AdvertGridData;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrAdvertDtPKTO;
import com.hrms.common.to.HrAdvertDtTO;
import com.hrms.common.to.HrAdvertHdPKTO;
import com.hrms.common.to.HrAdvertHdTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.InterviewActionPlanDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author zeeshan waris
 */
public class Advertisement extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(Advertisement.class);
    //private InitialContext ctx;
    String message;
    public String advertisementCode;
    List<SelectItem> mediaList;
    char mediaString;
    String jobCode;
    String Dt;
    String jobVacancies;
    List<SelectItem> designationList;
    String Selectdesignation;
    List<SelectItem> departmentList;
    String selectDepartment;
    List<SelectItem> locationList;
    String selectLocation;
    Date advertisementDate;
    Date lastDate;
    private AdvertGridData authorized;
    String mod = "";
    private Iterator<AdvertGridData> gridIterator;
    public List<AdvertGridData> advGrid = new ArrayList<AdvertGridData>();
    private String updatePopUpText;
    boolean addBoolean;
    private boolean flag;
    private boolean updateDisable;
    private boolean addDisable;
    private boolean deleteDisable;
    List<AdvertisementCodeListGrid> advertCodeList = new ArrayList<AdvertisementCodeListGrid>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private AdvertisementCodeListGrid advtCode;
    private int compCode, defaultComp;
    private String operation;
    private List<SelectItem> operationList;

    public boolean isDeleteDisable() {
        return deleteDisable;
    }

    public void setDeleteDisable(boolean deleteDisable) {
        this.deleteDisable = deleteDisable;
    }

    public boolean isAddDisable() {
        return addDisable;
    }

    public void setAddDisable(boolean addDisable) {
        this.addDisable = addDisable;
    }

    public boolean isUpdateDisable() {
        return updateDisable;
    }

    public void setUpdateDisable(boolean updateDisable) {
        this.updateDisable = updateDisable;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    private boolean disableUpdate;

    public boolean isDisableUpdate() {
        return disableUpdate;
    }

    public void setDisableUpdate(boolean disableUpdate) {
        this.disableUpdate = disableUpdate;
    }

    public AdvertisementCodeListGrid getAdvtCode() {
        return advtCode;
    }

    public void setAdvtCode(AdvertisementCodeListGrid advtCode) {
        this.advtCode = advtCode;
    }

    public char getMediaString() {
        return mediaString;
    }

    public void setMediaString(char mediaString) {
        this.mediaString = mediaString;
    }

    public String getUpdatePopUpText() {
        return updatePopUpText;
    }

    public void setUpdatePopUpText(String updatePopUpText) {
        this.updatePopUpText = updatePopUpText;
    }

    public List<AdvertisementCodeListGrid> getAdvertCodeList() {
        return advertCodeList;
    }

    public void setAdvertCodeList(List<AdvertisementCodeListGrid> advertCodeList) {
        this.advertCodeList = advertCodeList;
    }

    public Iterator<AdvertGridData> getGridIterator() {
        return gridIterator;
    }

    public void setGridIterator(Iterator<AdvertGridData> gridIterator) {
        this.gridIterator = gridIterator;
    }

    public boolean isAddBoolean() {
        return addBoolean;
    }

    public void setAddBoolean(boolean addBoolean) {
        this.addBoolean = addBoolean;
    }

    public AdvertGridData getAuthorized() {
        return authorized;
    }

    public void setAuthorized(AdvertGridData authorized) {
        this.authorized = authorized;
    }

    public List<AdvertGridData> getAdvGrid() {
        return advGrid;
    }

    public void setAdvGrid(List<AdvertGridData> advGrid) {
        this.advGrid = advGrid;
    }

    public Date getAdvertisementDate() {
        return advertisementDate;
    }

    public void setAdvertisementDate(Date advertisementDate) {
        this.advertisementDate = advertisementDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public List<SelectItem> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<SelectItem> locationList) {
        this.locationList = locationList;
    }

    public String getSelectLocation() {
        return selectLocation;
    }

    public void setSelectLocation(String selectLocation) {
        this.selectLocation = selectLocation;
    }

    public List<SelectItem> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<SelectItem> departmentList) {
        this.departmentList = departmentList;
    }

    public String getSelectDepartment() {
        return selectDepartment;
    }

    public void setSelectDepartment(String selectDepartment) {
        this.selectDepartment = selectDepartment;
    }

    public String getSelectdesignation() {
        return Selectdesignation;
    }

    public void setSelectdesignation(String Selectdesignation) {
        this.Selectdesignation = Selectdesignation;
    }

    public List<SelectItem> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<SelectItem> designationList) {
        this.designationList = designationList;
    }

    public String getJobVacancies() {
        return jobVacancies;
    }

    public void setJobVacancies(String jobVacancies) {
        this.jobVacancies = jobVacancies;
    }

    public String getDt() {
        return Dt;
    }

    public void setDt(String Dt) {
        this.Dt = Dt;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public List<SelectItem> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<SelectItem> mediaList) {
        this.mediaList = mediaList;
    }

    public String getAdvertisementCode() {
        return advertisementCode;
    }

    public void setAdvertisementCode(String advertisementCode) {
        this.advertisementCode = advertisementCode;
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

    public Advertisement() {

        try {
            compCode = Integer.parseInt(getOrgnBrCode());
            companyMasterTOs = webUtil.getCompanyMasterTO(compCode);
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            this.setMessage("");
            operationList = new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0", "---Select---"));
            operationList.add(new SelectItem("1", "Add"));
            operationList.add(new SelectItem("2", "Edit"));
            mod = "ADD";
            addBoolean = true;
            deleteDisable = true;
            onLoadDataSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAdvertNo() {
        try {
            InterviewActionPlanDelegate hrMasterManagementDelegate = new InterviewActionPlanDelegate();
            String advtCodeGenerate = hrMasterManagementDelegate.getAdvetisementCode(compCode);
            this.setAdvertisementCode(advtCodeGenerate);
        } catch (WebException e) {
            logger.error("Exception occured while executing method onLoadDataSet()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method onLoadDataSet()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }

    }

    public void onLoadDataSet() {
        try {
            mediaList = new ArrayList<SelectItem>();
            mediaList.add(new SelectItem("0", "--Select--"));
            this.mediaList.add(new SelectItem("T", "TV"));
            this.mediaList.add(new SelectItem("N", "NewsPaper"));
            this.mediaList.add(new SelectItem("R", "Radio"));
            this.mediaList.add(new SelectItem("M", "Magazine"));
            InterviewActionPlanDelegate hrMasterManagementDelegate = new InterviewActionPlanDelegate();
            List<HrMstStructTO> hrMstStructTOs = hrMasterManagementDelegate.getDesignationList("DES%", compCode);
            designationList = new ArrayList<SelectItem>();
            designationList.add(new SelectItem("0", "--Select--"));
            for (HrMstStructTO hrMstStructTo : hrMstStructTOs) {
                designationList.add(new SelectItem(hrMstStructTo.getHrMstStructPKTO().getStructCode(),
                        hrMstStructTo.getDescription()));
            }
            hrMstStructTOs = hrMasterManagementDelegate.getDepartmentList("DEP%", compCode);
            departmentList = new ArrayList<SelectItem>();
            departmentList.add(new SelectItem("0", "--Select--"));
            for (HrMstStructTO hrMstStructTo : hrMstStructTOs) {
                departmentList.add(new SelectItem(hrMstStructTo.getHrMstStructPKTO().getStructCode(),
                        hrMstStructTo.getDescription()));
            }
            hrMstStructTOs = hrMasterManagementDelegate.getLocationList("LOC%", compCode);
            locationList = new ArrayList<SelectItem>();
            locationList.add(new SelectItem("0", "--Select--"));
            for (HrMstStructTO hrMstStructTo : hrMstStructTOs) {
                locationList.add(new SelectItem(hrMstStructTo.getHrMstStructPKTO().getStructCode(),
                        hrMstStructTo.getDescription()));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method onLoadDataSet()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method onLoadDataSet()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void addButtonData() {
        addBoolean = false;
        getAdvertNo();
        mod = "ADD";
    }

    public void onDblClickAdvertCode() {
        try {
            flag = true;
            updateDisable = true;
            addDisable = true;
            addBoolean = false;
            deleteDisable = false;
            mod = "UPDATE";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            InterviewActionPlanDelegate hrMasterManagementDelegate = new InterviewActionPlanDelegate();
            List<HrAdvertHdTO> advtCodeHeaderDetails = hrMasterManagementDelegate.getAdvtCodeHeaderDetails(compCode, advtCode.getAdvList());
            for (HrAdvertHdTO hrAdvertHdTOs : advtCodeHeaderDetails) {
                mediaString = hrAdvertHdTOs.getMediaType();
                setAdvertisementDate(sdf.parse(hrAdvertHdTOs.getHrAdvertHdPKTO().getAdvtDate()));
                setJobCode(hrAdvertHdTOs.getHrAdvertHdPKTO().getJobCode());
                setAdvertisementCode(hrAdvertHdTOs.getHrAdvertHdPKTO().getAdvtCode());
                getAdvtCodeDetails(compCode, advtCode.getAdvList());
            }
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(Advertisement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WebException e) {
            logger.error("Exception occured while executing method onDblClickAdvertCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method onDblClickAdvertCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }


    }

    public void getAdvtCodeDetails(int compCode, String advtCodePassed) {
        try {
            InterviewActionPlanDelegate hrMasterManagementDelegate = new InterviewActionPlanDelegate();
            List l1 = hrMasterManagementDelegate.getAdvertisementCodeDetails(compCode, advtCodePassed);
            Iterator i1 = l1.iterator();
            while (i1.hasNext()) {
                Object[] result = (Object[]) i1.next();
                AdvertGridData adv = new AdvertGridData();
                adv.setDepartment(result[1].toString());
                adv.setDesignation(result[0].toString());
                adv.setLocation(result[2].toString());
                adv.setVacancies(Integer.parseInt(result[3].toString()));
                adv.setLastDate(sdf.parse(result[4].toString()));
                advGrid.add(adv);
                addBoolean = false;
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getAdvtCodeDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvtCodeDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }

    }

    public void setRowValues() {
        setSelectDepartment(authorized.getDepartment());
        setSelectdesignation(authorized.getDesignation());
        setSelectLocation(authorized.getLocation());
        setJobVacancies(String.valueOf(authorized.getVacancies()));
        setLastDate(authorized.getLastDate());
        deleteDisable=true;
        advGrid.clear();
    }

    public void rowDelete() {
        advGrid.remove(authorized);
    }

    public void onBlurLastCalendar() {
        if (validations().equalsIgnoreCase("false")) {
            return;
        }
        if (operation.equalsIgnoreCase("2")) {
            advGrid.clear();
        }
        AdvertGridData adv = new AdvertGridData();
        adv.setDepartment(this.selectDepartment);
        adv.setDesignation(Selectdesignation);
        adv.setLocation(selectLocation);
        adv.setVacancies(Integer.parseInt(this.getJobVacancies()));
        advGrid.add(adv);
        int a = advGrid.size();
        gridIterator = advGrid.iterator();
        addBoolean = false;
    }

    public void saveAdvertisementCode() {
        try {
            if (getJobCode().equalsIgnoreCase("")) {
                this.setMessage("Error !! Job code cannot be blank");
                return;
            }
            if (getJobCode() == null) {
                this.setMessage("Error !! Job code cannot be blank");
            }
            if (advertisementDate == null) {
                this.setMessage("Error !! advertisement date cannot be blank");
                return;
            }
            if (Selectdesignation == null) {
                this.setMessage("Error !! please select the designation");
                return;
            }
            if (Selectdesignation.equalsIgnoreCase("0")) {
                this.setMessage("Error !! please select the designation");
                return;
            }
            String statFlag = "Y";
            String statupFlag = "Y";
            InterviewActionPlanDelegate hrMasterManagementDelegate = new InterviewActionPlanDelegate();
            if (mod.equalsIgnoreCase("ADD")) {
                getAdvertNo();
                String advertCode = this.getAdvertisementCode();
                String jobCode1 = getJobCode();
                Date advtDate = this.getAdvertisementDate();
                String advDate = sdf.format(advtDate);
                HrAdvertHdPKTO hrAdvertHdPKTO = new HrAdvertHdPKTO();
                hrAdvertHdPKTO.setAdvtCode(advertCode);
                hrAdvertHdPKTO.setAdvtDate(advDate);
                hrAdvertHdPKTO.setCompCode(compCode);
                hrAdvertHdPKTO.setJobCode(jobCode1);
                HrAdvertHdTO hrAdvertHdTO = new HrAdvertHdTO();
                hrAdvertHdTO.setAuthBy(getUserName());
                hrAdvertHdTO.setDefaultComp(defaultComp);
                hrAdvertHdTO.setEnteredBy(getUserName());
                hrAdvertHdTO.setHrAdvertHdPKTO(hrAdvertHdPKTO);
                hrAdvertHdTO.setMediaType(mediaString);
                hrAdvertHdTO.setModDate(new Date());
                hrAdvertHdTO.setStatFlag(statFlag);
                hrAdvertHdTO.setStatUpFlag(statupFlag);
                String msg = hrMasterManagementDelegate.saveAdvtCodeHeader(hrAdvertHdTO, mod);
                if (msg.equalsIgnoreCase("true")) {
                    int a = advGrid.size();
                    for (AdvertGridData gridData : advGrid) {
                        HrAdvertDtTO hrAdvertDtTO = new HrAdvertDtTO();
                        HrAdvertDtPKTO hrAdvertDtPKTO = new HrAdvertDtPKTO();
                        hrAdvertDtPKTO.setAdvtCode(advertCode);
                        hrAdvertDtPKTO.setCompCode(compCode);
                        hrAdvertDtPKTO.setDeptCode(gridData.getDepartment());
                        hrAdvertDtPKTO.setDesgCode(gridData.getDesignation());
                        hrAdvertDtPKTO.setLocatCode(gridData.getLocation());
                        hrAdvertDtPKTO.setJobCode(jobCode);
                        hrAdvertDtPKTO.setMediaType(mediaString);
                        hrAdvertDtTO.setAuthBy(getUserName());
                        hrAdvertDtTO.setDefaultComp(defaultComp);
                        hrAdvertDtTO.setEnteredBy(getUserName());
                        hrAdvertDtTO.setHrAdvertDtPKTO(hrAdvertDtPKTO);
                        hrAdvertDtTO.setLastDate(sdf.format(getLastDate()));
                        hrAdvertDtTO.setModDate(new Date());
                        hrAdvertDtTO.setStatFlag(statFlag);
                        hrAdvertDtTO.setStatUpFlag(statupFlag);
                        hrAdvertDtTO.setVaccant(gridData.getVacancies());
                        hrMasterManagementDelegate.saveAdvtCodeDetails(hrAdvertDtTO, mod);
                    }
                    this.setMessage("Data has been saved successfully");
                }
            } else if (mod.equalsIgnoreCase("UPDATE")) {
                HrAdvertDtTO hrAdvertDtTO = new HrAdvertDtTO();
                HrAdvertDtPKTO hrAdvertDtPKTO = new HrAdvertDtPKTO();
                hrAdvertDtPKTO.setAdvtCode(getAdvertisementCode());
                hrAdvertDtPKTO.setCompCode(compCode);
                hrAdvertDtPKTO.setDeptCode(selectDepartment);
                hrAdvertDtPKTO.setDesgCode(Selectdesignation);
                hrAdvertDtPKTO.setLocatCode(selectLocation);
                hrAdvertDtPKTO.setJobCode(jobCode);
                hrAdvertDtPKTO.setMediaType(mediaString);
                hrAdvertDtTO.setAuthBy(getUserName());
                hrAdvertDtTO.setDefaultComp(defaultComp);
                hrAdvertDtTO.setEnteredBy(getUserName());
                hrAdvertDtTO.setHrAdvertDtPKTO(hrAdvertDtPKTO);
                hrAdvertDtTO.setLastDate(sdf.format(getLastDate()));
                hrAdvertDtTO.setModDate(new Date());
                hrAdvertDtTO.setStatFlag(statFlag);
                hrAdvertDtTO.setStatUpFlag(statupFlag);
                hrAdvertDtTO.setVaccant(Integer.parseInt(jobVacancies));
                hrMasterManagementDelegate.saveAdvtCodeDetails(hrAdvertDtTO, mod);
                this.setMessage("Data has been updated successfully");
            }

            Refresh1();
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveAdvertisementCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAdvertisementCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void findAdvCodeOnPopUp() {
        try {
            advertCodeList = new ArrayList<AdvertisementCodeListGrid>();
            InterviewActionPlanDelegate hrMasterManagementDelegate = new InterviewActionPlanDelegate();
            List<HrAdvertHdTO> hrAdvertListTOs = hrMasterManagementDelegate.findAdvertisementCodeList(compCode, this.getUpdatePopUpText());
            for (HrAdvertHdTO hrAdvertHdTO : hrAdvertListTOs) {
                AdvertisementCodeListGrid adv1 = new AdvertisementCodeListGrid();
                adv1.setAdvList(hrAdvertHdTO.getHrAdvertHdPKTO().getAdvtCode());
                advertCodeList.add(adv1);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method findAdvCodeOnPopUp()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method findAdvCodeOnPopUp()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteAdvertisementCode() {
        try {
            InterviewActionPlanDelegate hrMasterManagementDelegate = new InterviewActionPlanDelegate();
            String rsmsg = hrMasterManagementDelegate.deleteAdvertisementCode(compCode, getAdvertisementCode());
            if (rsmsg.equalsIgnoreCase("true")) {
                setMessage("Data deleted successfuly");
            } else {
                setMessage(rsmsg);
            }
            Refresh1();
            advGrid.clear();

        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteAdvertisementCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteAdvertisementCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void refresh() {
        setMessage("");
        flag = false;
        updateDisable = false;
        addDisable = false;
        addBoolean = true;
        deleteDisable = true;
        setAdvertisementCode("");
        setDt("");
        setJobCode("");
        setAdvertisementDate(null);
        setSelectdesignation("0");
        setSelectDepartment("0");
        setSelectLocation("0");
        setMediaString('0');
        setJobVacancies("");
        setLastDate(null);
        setOperation("0");
        if (advGrid != null) {
            advGrid.clear();
        }
        if (advertCodeList != null) {
            advertCodeList.clear();
        }
    }

    public String validations() {
        try {
            if (operation.equalsIgnoreCase("0")) {
                setMessage("Please select an operation !");
                return "false";
            }
            if (Selectdesignation.equalsIgnoreCase("0")) {
                this.setMessage("Error !! Please select the correct designation field");
                return "false";
            }
            if (selectDepartment.equalsIgnoreCase("0")) {
                this.setMessage("Error !! Please select the department field");
                return "false";

            }
            if (selectLocation.equalsIgnoreCase("0")) {
                this.setMessage("Error !! please select the location code field");
                return "false";
            }
            if (jobVacancies == null || jobVacancies.equalsIgnoreCase("")) {
                this.setMessage("Please Enter Numeric Value in No Of Vacancies");
                return "false";
            }
            if (!jobVacancies.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in No Of Vacancies");
                return "false";
            }
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "true";
    }

    public void Refresh1() {
        flag = false;
        updateDisable = false;
        addDisable = false;
        addBoolean = true;
        deleteDisable = true;
        setAdvertisementCode("");
        setDt("");
        setJobCode("");
        setAdvertisementDate(null);
        setSelectdesignation("0");
        setSelectDepartment("0");
        setSelectLocation("0");
        setMediaString('0');
        setJobVacancies("");
        setLastDate(null);
        setOperation("0");
        if (advGrid != null) {
            advGrid.clear();
        }
        if (advertCodeList != null) {
            advertCodeList.clear();
        }
    }

    public String btnExit() {
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "case1";
    }

    public void closePanel() {
        setUpdatePopUpText("");
    }

    public void setOperationOnBlur() {
        if (operation.equalsIgnoreCase("0")) {
            setMessage("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
            mod = "ADD";
        } else if (operation.equalsIgnoreCase("2")) {
            mod = "UPDATE";
        }
    }
}
