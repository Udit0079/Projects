/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.ReportingStructureDataTable;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrOrgChartPKTO;
import com.hrms.common.to.HrOrgChartTO;
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
 * @author Zeeshan Waris
 */
public class ReportingStructure extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(ReportingStructure.class);
    private String message;
    private String designation;
    private String reportTo;
    private String noOfPost;
    private String jobSpecification;
    private String compCode = "0";
    private List<SelectItem> designationList;
    Date date = new Date();
    private List<ReportingStructureDataTable> contSearch;
    private ReportingStructureDataTable currentItem = new ReportingStructureDataTable();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private boolean disableUpdate;
    private boolean contCodeDisable;
    private boolean disableSave;
    private String desc;
    private int defaultComp;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ReportingStructureDataTable> getContSearch() {
        return contSearch;
    }

    public void setContSearch(List<ReportingStructureDataTable> contSearch) {
        this.contSearch = contSearch;
    }

    public ReportingStructureDataTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ReportingStructureDataTable currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isContCodeDisable() {
        return contCodeDisable;
    }

    public void setContCodeDisable(boolean contCodeDisable) {
        this.contCodeDisable = contCodeDisable;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<SelectItem> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<SelectItem> designationList) {
        this.designationList = designationList;
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

    public String getJobSpecification() {
        return jobSpecification;
    }

    public void setJobSpecification(String jobSpecification) {
        this.jobSpecification = jobSpecification;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNoOfPost() {
        return noOfPost;
    }

    public void setNoOfPost(String noOfPost) {
        this.noOfPost = noOfPost;
    }

    public String getReportTo() {
        return reportTo;
    }

    public void setReportTo(String reportTo) {
        this.reportTo = reportTo;
    }

    public ReportingStructure() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            this.setMessage("");
            dropdownList(Integer.parseInt(compCode), "DES%");
            disableUpdate = true;
            reportingEditDetails();
        } catch (Exception e) {
            logger.error("Exception occured while executing method ReportingStructure()", e);
            logger.error("ReportingStructure()", e);
        }
    }

    public void dropdownList(int compCode, String description) {
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            List<HrMstStructTO> structMasterTOs = recruitmentDelegate.getPositionList(compCode, description);
            if (description.equalsIgnoreCase("DES%")) {
                designationList = new ArrayList<SelectItem>();
                designationList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    designationList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
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
        for (int i = 0; i < designationList.size(); i++) {
            if (designationList.get(i).getValue().toString().equals(this.getDesignation())) {
                setDesc(designationList.get(i).getLabel());
                break;
            }
        }
    }

    public void reportingEditDetails() {
        try {
            contSearch = new ArrayList<ReportingStructureDataTable>();
            List resultLt = new ArrayList();
            OrganizationDelegate organizationDelegate = new OrganizationDelegate();
            resultLt = organizationDelegate.reportingStructureEditList(Integer.parseInt(compCode));
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    ReportingStructureDataTable rd = new ReportingStructureDataTable();
                    if (result[0] != null) {
                        rd.setDesgcode(result[0].toString());
                    }
                    if (result[1] != null) {
                        rd.setEmpDesgCode(result[1].toString());
                    }
                    if (result[2] != null) {
                        rd.setDesgCodeRp(result[2].toString());
                    }
                    if (result[3] != null) {
                        rd.setReportDesg(result[3].toString());
                    }
                    if (result[4] != null) {
                        rd.setPosts(result[4].toString());
                    }
                    if (result[5] != null) {
                        rd.setJobSpecification(result[5].toString());
                    }
                    if (result[6] != null) {
                        rd.setDescription(result[6].toString());
                    }
                    if (result[7] != null) {
                        rd.setFlag(result[7].toString());
                    }
                    if (result[8] != null) {
                        rd.setStructcode(result[8].toString());
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

    public void selectCurrentrowForEdit() {
        disableSave = true;
        contCodeDisable = true;
        disableUpdate = false;
        setMessage("");
        setDesignation(currentItem.getDesgcode());
        setReportTo(currentItem.getDesgCodeRp());
        setNoOfPost(currentItem.getPosts());
        setJobSpecification(currentItem.getJobSpecification());
    }

    public void deleteReportingStructureAction() {
        setMessage("");
        try {
            OrganizationDelegate organizationDelegate = new OrganizationDelegate();
            String rsmsg = organizationDelegate.reportingDetailsDelete(Integer.parseInt(compCode), currentItem.getDesgcode());
            if (rsmsg.equalsIgnoreCase("true")) {
                setMessage("Data deleted successfuly");
            } else {
                setMessage(rsmsg);
            }
            reportingEditDetails();
            //  clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteReportingStructureAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method deleteReportingStructureAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteReportingStructureAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveReportingStructureAction() {
        setMessage("");
        try {
            if (designation.equalsIgnoreCase("0")) {
                setMessage("Please select the Designation");
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
            if (reportTo.equalsIgnoreCase("0")) {
                setMessage("Please select the Report To(Designation)");
                return;
            }
            if (!noOfPost.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in No. of Posts");
                return;
            }
            String stateFlag = "Y";
            String stateUpflag = "Y";

            HrOrgChartTO hrOrgChartTO = new HrOrgChartTO();
            hrOrgChartTO.setDesgCodeRp(reportTo);
            hrOrgChartTO.setPosts(Integer.parseInt(noOfPost));
            hrOrgChartTO.setJobSpecification(jobSpecification);
            hrOrgChartTO.setDescription(desc);
            hrOrgChartTO.setFlag1('Y');
            hrOrgChartTO.setDefaultComp(defaultComp);
            hrOrgChartTO.setStatFlag(stateFlag);
            hrOrgChartTO.setStatUpFlag(stateUpflag);
            hrOrgChartTO.setModDate(getDate());
            hrOrgChartTO.setAuthBy(getUserName());
            hrOrgChartTO.setEnteredBy(getUserName());
            HrOrgChartPKTO contPK = new HrOrgChartPKTO();
            contPK.setCompCode(Integer.parseInt(compCode));
            contPK.setDesgCode(designation);
            hrOrgChartTO.setHrOrgChartPKTO(contPK);
            OrganizationDelegate organizationDelegate = new OrganizationDelegate();
            String result = organizationDelegate.reportingDetailsSave(hrOrgChartTO);
            setMessage(result);
            reportingEditDetails();
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveReportingStructureAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveReportingStructureAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveReportingStructureAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void updateReportingStructureAction() {
        setMessage("");
        try {
            if (currentItem.getDesgcode() == null) {
                setMessage("Please select the Designation");
                return;
            }
            if (compCode.equalsIgnoreCase("")) {
                setMessage("Please fill the company code");
                return;
            }
            if (reportTo.equalsIgnoreCase("0")) {
                setMessage("Please select the Report To(Designation)");
                return;
            }
            if (!noOfPost.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in No. of Posts");
                return;
            }
            String stateFlag = "Y";
            String stateUpflag = "Y";
            HrOrgChartTO hrOrgChartTO = new HrOrgChartTO();
            hrOrgChartTO.setDesgCodeRp(reportTo);
            hrOrgChartTO.setPosts(Integer.parseInt(noOfPost));
            hrOrgChartTO.setJobSpecification(jobSpecification);
            hrOrgChartTO.setDescription(currentItem.getEmpDesgCode());
            hrOrgChartTO.setFlag1('Y');
            hrOrgChartTO.setDefaultComp(defaultComp);
            hrOrgChartTO.setStatFlag(stateFlag);
            hrOrgChartTO.setStatUpFlag(stateUpflag);
            hrOrgChartTO.setModDate(getDate());
            hrOrgChartTO.setAuthBy(getUserName());
            hrOrgChartTO.setEnteredBy(getUserName());
            HrOrgChartPKTO contPK = new HrOrgChartPKTO();
            contPK.setCompCode(Integer.parseInt(compCode));
            contPK.setDesgCode(currentItem.getDesgcode());
            hrOrgChartTO.setHrOrgChartPKTO(contPK);
            OrganizationDelegate organizationDelegate = new OrganizationDelegate();
            String result = organizationDelegate.reportingDetailsUpdate(hrOrgChartTO);
            setMessage(result);
            reportingEditDetails();
            clear();
        } catch (WebException e) {
            logger.error("Exception occured while executing method updateReportingStructureAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method updateReportingStructureAction()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method updateReportingStructureAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void clear() {
        setDesignation("0");
        setReportTo("0");
        setNoOfPost("");
        setJobSpecification("");
    }

    public void refreshButtonAction() {
        setMessage("");
        contCodeDisable = false;
        disableUpdate = true;
        disableSave = false;
        setDesignation("0");
        setReportTo("0");
        setNoOfPost("");
        setJobSpecification("");
    }

    public String btnExit() {
        try {
            refreshButtonAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "case1";
    }
}
