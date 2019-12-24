/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.AppointmentLetterTable;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrInterviewDtPKTO;
import com.hrms.common.to.HrInterviewDtSalPKTO;
import com.hrms.common.to.HrInterviewDtSalTO;
import com.hrms.common.to.HrInterviewDtTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.InterviewActionPlanDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class AppointmentLetter extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(AppointmentLetter.class);
    private String message;
    private String message1;
    private String advertisementCode;
    private String jobCode;
    private String candidateCode;
    private String candidateId;
    private String candidateIdView;
    private String candidateName;
    private String interviewCode;
    private String post;
    private String address;
    private String city;
    private String state;
    private String pin;
    private String expectedSalary;
    private String salaryOffered;
    private String negotiatedSalary;
    private String compCode = "0";
    private boolean flag;
    private boolean saveDisable;
    private String mode;
    private boolean searchDisable;
    private boolean editDisable;
    private boolean offFlag;
    private boolean negoFlag;
    private HttpServletRequest req;
    private List<SelectItem> postList;
    private List<AppointmentLetterTable> candidateApp;
    private int currentRow, defaultComp;
    private AppointmentLetterTable currentItem = new AppointmentLetterTable();
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String operation;
    private List<SelectItem> operationList;
    NumberFormat formatter=new DecimalFormat("0.00");

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

    public boolean isNegoFlag() {
        return negoFlag;
    }

    public void setNegoFlag(boolean negoFlag) {
        this.negoFlag = negoFlag;
    }

    public boolean isOffFlag() {
        return offFlag;
    }

    public void setOffFlag(boolean offFlag) {
        this.offFlag = offFlag;
    }

    public boolean isEditDisable() {
        return editDisable;
    }

    public void setEditDisable(boolean editDisable) {
        this.editDisable = editDisable;
    }

    public boolean isSearchDisable() {
        return searchDisable;
    }

    public void setSearchDisable(boolean searchDisable) {
        this.searchDisable = searchDisable;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateIdView() {
        return candidateIdView;
    }

    public void setCandidateIdView(String candidateIdView) {
        this.candidateIdView = candidateIdView;
    }

    public AppointmentLetterTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AppointmentLetterTable currentItem) {
        this.currentItem = currentItem;
    }

    public String getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(String expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public String getInterviewCode() {
        return interviewCode;
    }

    public void setInterviewCode(String interviewCode) {
        this.interviewCode = interviewCode;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getNegotiatedSalary() {
        return negotiatedSalary;
    }

    public void setNegotiatedSalary(String negotiatedSalary) {
        this.negotiatedSalary = negotiatedSalary;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public List<SelectItem> getPostList() {
        return postList;
    }

    public void setPostList(List<SelectItem> postList) {
        this.postList = postList;
    }

    public String getSalaryOffered() {
        return salaryOffered;
    }

    public void setSalaryOffered(String salaryOffered) {
        this.salaryOffered = salaryOffered;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdvertisementCode() {
        return advertisementCode;
    }

    public void setAdvertisementCode(String advertisementCode) {
        this.advertisementCode = advertisementCode;
    }

    public List<AppointmentLetterTable> getCandidateApp() {
        return candidateApp;
    }

    public void setCandidateApp(List<AppointmentLetterTable> candidateApp) {
        this.candidateApp = candidateApp;
    }

    public String getCandidateCode() {
        return candidateCode;
    }

    public void setCandidateCode(String candidateCode) {
        this.candidateCode = candidateCode;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public AppointmentLetter() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            operationList = new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0", "---Select---"));
            operationList.add(new SelectItem("1", "Add"));
            operationList.add(new SelectItem("2", "Edit"));
            candidateApp = new ArrayList<AppointmentLetterTable>();
            setTodayDate(sdf.format(date));
            this.setMessage("");
            this.setMessage1("");
            getAppointmentLetterDDList(compCode, "DES%");
            setFlag(true);
            this.setOffFlag(true);
            this.setNegoFlag(true);
            refreshAppLetter();
            setSaveDisable(true);
        } catch (Exception e) {
            logger.error("Exception occured while executing method AppointmentLetter()", e);
            logger.error("AppointmentLetter()", e);
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void getAppointmentLetterDDList(String compCode, String description) {
        try {
            InterviewActionPlanDelegate cancellationOfAppointmentAdviceDelegate = new InterviewActionPlanDelegate();
            List<HrMstStructTO> structMasterTOs = cancellationOfAppointmentAdviceDelegate.getInitialData(Integer.parseInt(compCode), description);
            if (description.equalsIgnoreCase("DES%")) {
                postList = new ArrayList<SelectItem>();
                postList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    postList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getAppointmentLetterDDList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getAppointmentLetterDDList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAppointmentLetterDDList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Candidate Code"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (AppointmentLetterTable item : candidateApp) {
            if (item.getAdvertisementCodeTab().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void appointmentDetailTable() {
        setMessage1("");
        candidateApp = new ArrayList<AppointmentLetterTable>();
        try {

            List resultLt = new ArrayList();
            InterviewActionPlanDelegate appointmentLetterDelegate = new InterviewActionPlanDelegate();
            resultLt = appointmentLetterDelegate.getSearchAppointmentLetterData(Integer.parseInt(compCode), candidateId);
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    AppointmentLetterTable rd = new AppointmentLetterTable();
                    rd.setAdvertisementCodeTab(result[0].toString());
                    rd.setJobCodeTab(result[1].toString());
                    rd.setInterviewCodeTab(result[2].toString());
                    rd.setCandidateSrTab(result[3].toString());
                    rd.setCandidateNameTab(result[4].toString() + ' ' + result[5].toString() + ' ' + result[6].toString());
                    rd.setPostAppliedTab(result[7].toString());
                    if (result[8] == null) {
                        rd.setAddressTab("");
                    } else {
                        rd.setAddressTab(result[8].toString());
                    }
                    if (result[9] == null) {
                        rd.setCityTab("");
                    } else {
                        rd.setCityTab(result[9].toString());
                    }
                    if (result[10] == null) {
                        rd.setStateTab("");
                    } else {
                        rd.setStateTab(result[10].toString());
                    }
                    if (result[11] == null) {
                        rd.setPinTab("");
                    } else {
                        rd.setPinTab(result[11].toString());
                    }
                    if (result[12] == null) {
                        rd.setExpSalTab("");
                    } else {
                        rd.setExpSalTab(formatter.format(Double.parseDouble(result[12].toString())));
                    }
                    candidateApp.add(rd);
                }
            } else {
                setMessage1("No record exist for save.");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method appointmentDetailTable()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method appointmentDetailTable()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method appointmentDetailTable()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectAppointmentDetail() throws ParseException {

        this.setAdvertisementCode(currentItem.getAdvertisementCodeTab());
        this.setJobCode(currentItem.getJobCodeTab());
        this.setInterviewCode(currentItem.getInterviewCodeTab());
        this.setCandidateCode(currentItem.getCandidateSrTab());
        this.setCandidateName(currentItem.getCandidateNameTab());
        this.setPost(currentItem.getPostAppliedTab());
        this.setAddress(currentItem.getAddressTab());
        this.setCity(currentItem.getCityTab());
        this.setState(currentItem.getStateTab());
        this.setPin(currentItem.getPinTab());
        this.setExpectedSalary(currentItem.getExpSalTab());

        this.setOffFlag(false);
        this.setNegoFlag(false);
        setSaveDisable(false);
        this.setEditDisable(true);
        this.setSearchDisable(true);
        setOperation("1");
    }

    public void fetchCurrentRowView(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Candidate Code"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (AppointmentLetterTable item : candidateApp) {
            if (item.getAdvertisementCodeTab().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void viewEditAppointmentDetailTable() {
        setMessage1("");

        candidateApp = new ArrayList<AppointmentLetterTable>();
        try {

            List resultLt = new ArrayList();
            InterviewActionPlanDelegate appointmentLetterDelegate = new InterviewActionPlanDelegate();
            resultLt = appointmentLetterDelegate.getViewEditAppointmentLetterData(Integer.parseInt(compCode), candidateIdView);
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    AppointmentLetterTable rd = new AppointmentLetterTable();

                    rd.setAdvertisementCodeTab(result[0].toString());
                    rd.setJobCodeTab(result[1].toString());
                    rd.setInterviewCodeTab(result[2].toString());
                    rd.setCandidateSrTab(result[3].toString());
                    if (result[4] == null) {
                        rd.setSalaryOffTab("");
                    } else {
                        rd.setSalaryOffTab(formatter.format(Double.parseDouble(result[4].toString())));
                    }
                    if (result[5] == null) {
                        rd.setNegotiatedSalaryTab("");
                    } else {
                        rd.setNegotiatedSalaryTab(formatter.format(Double.parseDouble(result[5].toString())));
                    }
                    if (result[6] == null) {
                        rd.setExpSalTab("");
                    } else {
                        rd.setExpSalTab(formatter.format(Double.parseDouble(result[6].toString())));
                    }
                    rd.setCandidateNameTab(result[7].toString() + ' ' + result[8].toString() + ' ' + result[9].toString());
                    rd.setPostAppliedTab(result[10].toString());
                    if (result[11] == null) {
                        rd.setAddressTab("");
                    } else {
                        rd.setAddressTab(result[11].toString());
                    }
                    if (result[12] == null) {
                        rd.setCityTab("");
                    } else {
                        rd.setCityTab(result[12].toString());
                    }
                    if (result[13] == null) {
                        rd.setStateTab("");
                    } else {
                        rd.setStateTab(result[13].toString());
                    }
                    if (result[14] == null) {
                        rd.setPinTab("");
                    } else {
                        rd.setPinTab(result[14].toString());
                    }
                    candidateApp.add(rd);
                }
            } else {
                setMessage1("No data for updation.");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method viewEditAppointmentDetailTable()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method viewEditAppointmentDetailTable()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method viewEditAppointmentDetailTable()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectAppointmentDetailViewEdit() throws ParseException {

        this.setAdvertisementCode(currentItem.getAdvertisementCodeTab());
        this.setJobCode(currentItem.getJobCodeTab());
        this.setInterviewCode(currentItem.getInterviewCodeTab());
        this.setCandidateCode(currentItem.getCandidateSrTab());
        this.setCandidateName(currentItem.getCandidateNameTab());
        this.setPost(currentItem.getPostAppliedTab());
        this.setAddress(currentItem.getAddressTab());
        this.setCity(currentItem.getCityTab());
        this.setState(currentItem.getStateTab());
        this.setPin(currentItem.getPinTab());
        this.setExpectedSalary(currentItem.getExpSalTab());
        this.setNegotiatedSalary(currentItem.getNegotiatedSalaryTab());
        this.setSalaryOffered(currentItem.getSalaryOffTab());
        this.setOffFlag(false);
        this.setNegoFlag(false);
        setSaveDisable(false);
        this.setEditDisable(true);
        this.setSearchDisable(true);
        setOperation("2");

    }

    public void saveAppLetter() {
        try {
            if (operation.equalsIgnoreCase("0")) {
                setMessage("Please select an operation !");
                return;
            }
            if (validationAppLetter().equalsIgnoreCase("false")) {
                return;
            }
            char result = 'A';
            String flag1 = "Y";
            String salCompCode = "SCC001";
            HrInterviewDtSalTO hrInterviewDtSalTO = new HrInterviewDtSalTO();
            HrInterviewDtSalPKTO hrInterviewDtSalPKTO = new HrInterviewDtSalPKTO();
            hrInterviewDtSalPKTO.setCompCode(Integer.parseInt(compCode));
            hrInterviewDtSalPKTO.setIntCode(interviewCode);
            hrInterviewDtSalPKTO.setAdvtCode(advertisementCode);
            hrInterviewDtSalPKTO.setJobCode(jobCode);
            hrInterviewDtSalPKTO.setCandSrno(candidateCode);

            hrInterviewDtSalTO.setHrInterviewDtSalPKTO(hrInterviewDtSalPKTO);
            hrInterviewDtSalTO.setSalCompCode(salCompCode);

            if (salaryOffered.equalsIgnoreCase("") || salaryOffered == null) {
                hrInterviewDtSalTO.setCompOff(Double.parseDouble("0.0"));
            } else {
                hrInterviewDtSalTO.setCompOff(Double.parseDouble(salaryOffered));
            }
            if (negotiatedSalary.equalsIgnoreCase("") || negotiatedSalary == null) {
                hrInterviewDtSalTO.setCompNegoit(Double.parseDouble("0.0"));
            } else {
                hrInterviewDtSalTO.setCompNegoit(Double.parseDouble(negotiatedSalary));
            }
            if (expectedSalary.equalsIgnoreCase("") || expectedSalary == null) {
                hrInterviewDtSalTO.setCompExpect(Double.parseDouble("0.0"));
            } else {
                hrInterviewDtSalTO.setCompExpect(Double.parseDouble(expectedSalary));
            }

            hrInterviewDtSalTO.setDefaultCompCode(defaultComp);
            hrInterviewDtSalTO.setStatFlag(flag1);
            hrInterviewDtSalTO.setStatUpFlag(flag1);
            hrInterviewDtSalTO.setModDate(date);
            hrInterviewDtSalTO.setAuthBy(getUserName());
            hrInterviewDtSalTO.setEnteredBy(getUserName());
            HrInterviewDtTO hrInterviewDtTO = new HrInterviewDtTO();
            HrInterviewDtPKTO hrInterviewDtPKTO = new HrInterviewDtPKTO();
            hrInterviewDtPKTO.setCompCode(Integer.parseInt(compCode));
            hrInterviewDtPKTO.setIntCode(interviewCode);
            hrInterviewDtPKTO.setAdvtCode(advertisementCode);
            hrInterviewDtPKTO.setJobCode(jobCode);
            hrInterviewDtPKTO.setCandSrno(candidateCode);
            hrInterviewDtTO.setHrInterviewDtPKTO(hrInterviewDtPKTO);
            hrInterviewDtTO.setIntResult(result);
            if (mode.equalsIgnoreCase("save")) {
                InterviewActionPlanDelegate appointmentLetterDelegate = new InterviewActionPlanDelegate();
                String queryresult = appointmentLetterDelegate.saveAppointmentLetterData(hrInterviewDtTO, hrInterviewDtSalTO);
                setMessage(queryresult);
                refresh();
            } else if (mode.equalsIgnoreCase("update")) {
                InterviewActionPlanDelegate appointmentLetterDelegate = new InterviewActionPlanDelegate();
                String queryresult = appointmentLetterDelegate.updateAppointmentLetterData(hrInterviewDtSalTO);
                setMessage(queryresult);
                refresh();
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveAppLetter()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveAppLetter()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAppLetter()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void searchPanel() {
        refreshAppLetter();
        setMessage1("");
        this.setCandidateId("");
        candidateApp = new ArrayList<AppointmentLetterTable>();
        mode = "Save";

    }

    public void editPanel() {
        refreshAppLetter();
        setMessage1("");
        this.setCandidateIdView("");
        candidateApp = new ArrayList<AppointmentLetterTable>();
        mode = "update";

    }

    public void refreshAppLetter() {
        setMessage1("");
        candidateApp = new ArrayList<AppointmentLetterTable>();
        setMessage("");
        this.setSaveDisable(true);
        this.setEditDisable(false);
        this.setSearchDisable(false);
        this.setOffFlag(true);
        this.setNegoFlag(true);
        this.setAdvertisementCode("");
        this.setCandidateCode("");
        this.setCandidateId("");
        this.setCandidateIdView("");
        this.setJobCode("");
        this.setCandidateName("");
        this.setInterviewCode("");
        this.setCity("");
        this.setAddress("");
        this.setState("");
        this.setPin("");
        this.setExpectedSalary("0");
        this.setNegotiatedSalary("");
        this.setSalaryOffered("");
        this.setPost("0");
        setOperation("0");

    }

    public void refresh() {
        setMessage1("");
        candidateApp = new ArrayList<AppointmentLetterTable>();
        this.setSaveDisable(true);
        this.setEditDisable(false);
        this.setSearchDisable(false);
        this.setOffFlag(true);
        this.setNegoFlag(true);
        this.setAdvertisementCode("");
        this.setCandidateCode("");
        this.setCandidateId("");
        this.setCandidateIdView("");
        this.setJobCode("");
        this.setCandidateName("");
        this.setInterviewCode("");
        this.setCity("");
        this.setAddress("");
        this.setState("");
        this.setPin("");
        this.setExpectedSalary("0");
        this.setNegotiatedSalary("");
        this.setSalaryOffered("");
        this.setPost("0");
        setOperation("0");

    }

    public String validationAppLetter() {
        try {
            if (!salaryOffered.matches("[0-9.]*")) {
                this.setMessage("Please enter numeric value in Salary Offered.");
                return "false";
            }

            if (!negotiatedSalary.matches("[0-9.]*")) {
                this.setMessage("Please enter numeric value in Negotiated Salary");
                return "false";
            }
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "true";
    }

    public void setOperationOnBlur() {
        setMessage("");
        if (operation.equalsIgnoreCase("0")) {
            setMessage("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
            mode = "save";
        } else if (operation.equalsIgnoreCase("2")) {
            mode = "update";
        }
    }

    public String btnExit() {
        try {
            refreshAppLetter();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "case1";
    }
}
