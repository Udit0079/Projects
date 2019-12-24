/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.PreOrgnTable;
import com.hrms.web.pojo.PrepareOrgnTable;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstDesgPKTO;
import com.hrms.common.to.HrMstDesgTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.RecruitmentDelegate;
import com.hrms.web.delegate.OrganizationDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import javax.faces.model.SelectItem;
import java.util.Iterator;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.web.utils.WebUtil;

/**
 *
 * @author Zeeshan waris
 */
public class PrepareOrganizationView extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(PrepareOrganizationView.class);
    private String message;
    private String grade;
    private String compCode = "0";
    private List<SelectItem> gradeList;
    Date date = new Date();
    private List<PreOrgnTable> contSearch = new ArrayList<PreOrgnTable>();
    private PreOrgnTable currentItem = new PreOrgnTable();
    private List<PrepareOrgnTable> preSearch;
    private PrepareOrgnTable currentItem1 = new PrepareOrgnTable();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private boolean disableUpdate;
    private boolean disableGrade;
    private boolean disableSave;
    private String mode;
    private String desgCode;
    private int defaultComp;

    public String getDesgCode() {
        return desgCode;
    }

    public void setDesgCode(String desgCode) {
        this.desgCode = desgCode;
    }

    public PrepareOrgnTable getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(PrepareOrgnTable currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public List<PrepareOrgnTable> getPreSearch() {
        return preSearch;
    }

    public void setPreSearch(List<PrepareOrgnTable> preSearch) {
        this.preSearch = preSearch;
    }

    public List<PreOrgnTable> getContSearch() {
        return contSearch;
    }

    public void setContSearch(List<PreOrgnTable> contSearch) {
        this.contSearch = contSearch;
    }

    public PreOrgnTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(PreOrgnTable currentItem) {
        this.currentItem = currentItem;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDisableGrade() {
        return disableGrade;
    }

    public void setDisableGrade(boolean disableGrade) {
        this.disableGrade = disableGrade;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public boolean isDisableUpdate() {
        return disableUpdate;
    }

    public void setDisableUpdate(boolean disableUpdate) {
        this.disableUpdate = disableUpdate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<SelectItem> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<SelectItem> gradeList) {
        this.gradeList = gradeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PrepareOrganizationView() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            setTodayDate(sdf.format(date));
            this.setMessage("");
            dropdownList(Integer.parseInt(compCode), "GRA%");
            mode = "Save";
        } catch (Exception e) {
            logger.error("Exception occured while executing method PrepareOrganizationView()", e);
            logger.error("PrepareOrganizationView()", e);
        }
    }

    public void dropdownList(int compCode, String description) {
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            List<HrMstStructTO> structMasterTOs = recruitmentDelegate.getPositionList(compCode, description);
            if (description.equalsIgnoreCase("GRA%")) {
                gradeList = new ArrayList<SelectItem>();
                gradeList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    gradeList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method dropdownList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method dropdownList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method dropdownList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectDesignation() {
        try {
            if (grade.equalsIgnoreCase("0")) {
                setMessage("Please select Grade");
                return;
            }
            contSearch = new ArrayList<PreOrgnTable>();
            List resultLt = new ArrayList();
            OrganizationDelegate organizationDelegate = new OrganizationDelegate();
            resultLt = organizationDelegate.prepareOrgnEditList(Integer.parseInt(compCode), "DES%");
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    PreOrgnTable rd = new PreOrgnTable();
                    if (result[0] != null) {
                        rd.setStructCode(result[0].toString());
                    }
                    if (result[1] != null) {
                        rd.setDesscription(result[1].toString());
                    }
                    contSearch.add(rd);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method reportingEditDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method reportingEditDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method reportingEditDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void prepareEditDetail() {
        mode = "Update";
        try {
            preSearch = new ArrayList<PrepareOrgnTable>();
            List resultLt = new ArrayList();
            OrganizationDelegate organizationDelegate = new OrganizationDelegate();
            resultLt = organizationDelegate.prepareOrgnSaveList(Integer.parseInt(compCode));
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    PrepareOrgnTable rd = new PrepareOrgnTable();
                    if (result[0] != null) {
                        rd.setGradeCode(result[0].toString());
                    }
                    if (result[1] != null) {
                        rd.setDescription(result[1].toString());
                    }
                    preSearch.add(rd);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method reportingEditDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method reportingEditDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method reportingEditDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveUpdatePrepareOrgnDetails() {
        try {
            if (grade.equalsIgnoreCase("0")) {
                setMessage("Please select Grade");
                return;
            }
            if (compCode.equalsIgnoreCase("")) {
                setMessage("please fill the Comany code");
                return;
            }
            if (compCode == null) {
                setMessage("please fill the Comany code");
                return;
            }
            if (mode.equalsIgnoreCase("Save")) {
                if (currentItem.getStructCode() == null) {
                    setMessage("Please Select the current Row from the Description Table and save the Data");
                    return;
                }
                if (currentItem.getStructCode().equalsIgnoreCase("")) {
                    setMessage("Please Select the current Row from the Description Table and save the Data");
                    return;
                }
                String stateFlag = "Y";
                String stateUpflag = "Y";
                HrMstDesgTO hrDesg = new HrMstDesgTO();
                HrMstDesgPKTO hrDesgPK = new HrMstDesgPKTO();
                hrDesgPK.setCompCode(Integer.parseInt(compCode));
                hrDesgPK.setDesgCode(currentItem.getStructCode());
                hrDesg.setHrMstDesgPKTO(hrDesgPK);
                hrDesg.setGradeCode(grade);
                hrDesg.setDefaultComp(defaultComp);
                hrDesg.setStatFlag(stateFlag);
                hrDesg.setStatUpFlag(stateUpflag);
                hrDesg.setModDate(getDate());
                hrDesg.setAuthBy(getUserName());
                hrDesg.setEnteredBy(getUserName());
                OrganizationDelegate organizationDelegate = new OrganizationDelegate();
                String result = organizationDelegate.prepareOrgnSave(hrDesg);
                setMessage(result);
            } /******************* update *******************/
            else if (mode.equalsIgnoreCase("Update")) {
                if (currentItem.getStructCode() == null) {
                    setMessage("Please Select the current Row from the Description Table and save the Data");
                    return;
                }
                if (currentItem.getStructCode().equalsIgnoreCase("")) {
                    setMessage("Please Select the current Row from the Description Table and save the Data");
                    return;
                }
                String stateFlag = "Y";
                String stateUpflag = "Y";
                HrMstDesgTO hrDesg = new HrMstDesgTO();
                HrMstDesgPKTO hrDesgPK = new HrMstDesgPKTO();
                hrDesgPK.setCompCode(Integer.parseInt(compCode));
                prepareDesgCode(Integer.parseInt(compCode), currentItem.getStructCode());
                if (desgCode == null) {
                    setMessage("Please Select the current Row and Update the Data");
                    return;
                }
                hrDesgPK.setDesgCode(desgCode);
                hrDesg.setHrMstDesgPKTO(hrDesgPK);
                hrDesg.setGradeCode(grade);
                hrDesg.setDefaultComp(defaultComp);
                hrDesg.setStatFlag(stateFlag);
                hrDesg.setStatUpFlag(stateUpflag);
                hrDesg.setModDate(getDate());
                hrDesg.setAuthBy(getUserName());
                hrDesg.setEnteredBy(getUserName());
                OrganizationDelegate organizationDelegate = new OrganizationDelegate();
                String result = organizationDelegate.prepareOrgnUpdate(hrDesg);
                setMessage(result);

            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveUpdatePrepareOrgnDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveUpdatePrepareOrgnDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveUpdatePrepareOrgnDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void prepareDesgCode(int CompCode, String gradecode) {
        try {
            List resultLt = new ArrayList();
            OrganizationDelegate organizationDelegate = new OrganizationDelegate();
            resultLt = organizationDelegate.prepareUpdateDesgCodeList(CompCode, gradecode);
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    if (result[0] != null) {
                        setDesgCode(result[0].toString());
                    }
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method prepareDesgCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method prepareDesgCode()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectCurrentrowForEdit() {
        contSearch = new ArrayList<PreOrgnTable>();
        disableUpdate = true;
        setMessage("");
        if (mode.equalsIgnoreCase("Update")) {
            disableGrade = true;
            setGrade(currentItem1.getGradeCode());
            PreOrgnTable rd = new PreOrgnTable();
            rd.setStructCode(currentItem1.getGradeCode());
            rd.setDesscription(currentItem1.getDescription());
            contSearch.add(rd);
        }
    }

    public void refreshButtonAction() {
        disableGrade = false;
        disableUpdate = false;
        setMessage("");
        setGrade("0");
        contSearch = new ArrayList<PreOrgnTable>();
        preSearch = new ArrayList<PrepareOrgnTable>();
    }
    public String btnExit()
    {
        refreshButtonAction();
        return "case1";
    }
}
