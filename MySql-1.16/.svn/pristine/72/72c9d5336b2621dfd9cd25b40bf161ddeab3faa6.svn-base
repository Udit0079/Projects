/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.ExtensionSearchTable;
import com.hrms.web.pojo.ExtensionEditTable;
import com.cbs.web.controller.BaseBean;
import java.util.Iterator;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrInterviewDtPKTO;
import com.hrms.common.to.HrInterviewDtTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.InterviewActionPlanDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;
import java.text.ParseException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zeeshan Waris
 */
public class ExtensionOfAppointmentAdvice extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(ExtensionOfAppointmentAdvice.class);
    private String message;
    private String candidateCode;
    private String candidateName;
    private Date interviewDt;
    private String venue;
    private String interviewTime;
    private String post;
    private Date dtExpectedofJoining;
    private Date dtOfJoiningExtension;
    private String compCode = "0";
    private HttpServletRequest req;
    private List<SelectItem> postList;
    private String message1;
    private List<ExtensionSearchTable> searchExtension;
    private int currentRow1;
    private ExtensionSearchTable currentItem1 = new ExtensionSearchTable();
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String candidateSrno;
    private boolean saveDisable;
    private boolean disableUpdate;
    private boolean flag;
    private List<ExtensionEditTable> inteviewDetail;
    private int currentRow2;
    private ExtensionEditTable currentItem2 = new ExtensionEditTable();
    private String interviewCode;
    private String mode;
    private String intCodeHide;
    private String intCodeHide1;
    private String operation;
    private List<SelectItem> operationList;

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

    public String getIntCodeHide1() {
        return intCodeHide1;
    }

    public void setIntCodeHide1(String intCodeHide1) {
        this.intCodeHide1 = intCodeHide1;
    }

    public String getIntCodeHide() {
        return intCodeHide;
    }

    public void setIntCodeHide(String intCodeHide) {
        this.intCodeHide = intCodeHide;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getInterviewCode() {
        return interviewCode;
    }

    public void setInterviewCode(String interviewCode) {
        this.interviewCode = interviewCode;
    }

    public ExtensionEditTable getCurrentItem2() {
        return currentItem2;
    }

    public void setCurrentItem2(ExtensionEditTable currentItem2) {
        this.currentItem2 = currentItem2;
    }

    public int getCurrentRow2() {
        return currentRow2;
    }

    public void setCurrentRow2(int currentRow2) {
        this.currentRow2 = currentRow2;
    }

    public List<ExtensionEditTable> getInteviewDetail() {
        return inteviewDetail;
    }

    public void setInteviewDetail(List<ExtensionEditTable> inteviewDetail) {
        this.inteviewDetail = inteviewDetail;
    }

    public boolean isDisableUpdate() {
        return disableUpdate;
    }

    public void setDisableUpdate(boolean disableUpdate) {
        this.disableUpdate = disableUpdate;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public ExtensionSearchTable getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(ExtensionSearchTable currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public List<ExtensionSearchTable> getSearchExtension() {
        return searchExtension;
    }

    public void setSearchExtension(List<ExtensionSearchTable> searchExtension) {
        this.searchExtension = searchExtension;
    }

    public String getCandidateSrno() {
        return candidateSrno;
    }

    public void setCandidateSrno(String candidateSrno) {
        this.candidateSrno = candidateSrno;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDtExpectedofJoining() {
        return dtExpectedofJoining;
    }

    public void setDtExpectedofJoining(Date dtExpectedofJoining) {
        this.dtExpectedofJoining = dtExpectedofJoining;
    }

    public Date getDtOfJoiningExtension() {
        return dtOfJoiningExtension;
    }

    public void setDtOfJoiningExtension(Date dtOfJoiningExtension) {
        this.dtOfJoiningExtension = dtOfJoiningExtension;
    }

    public Date getInterviewDt() {
        return interviewDt;
    }

    public void setInterviewDt(Date interviewDt) {
        this.interviewDt = interviewDt;
    }

    public String getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(String interviewTime) {
        this.interviewTime = interviewTime;
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public ExtensionOfAppointmentAdvice() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            operationList = new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0", "---Select---"));
            operationList.add(new SelectItem("1", "Add"));
            operationList.add(new SelectItem("2", "Edit"));
            setTodayDate(sdf.format(date));
            this.setMessage("");
            dropDownPositionList();
            setSaveDisable(true);
            setFlag(true);
            mode = "update";
        } catch (Exception e) {
            logger.error("Exception occured while executing method ExtensionOfAppointmentAdvice()", e);
            logger.error("ExtensionOfAppointmentAdvice()", e);
        }
    }

    public void dropDownPositionList() {
        try {
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            List<HrMstStructTO> structMasterTOs = interviewActionPlanDelegate.getPositionExtensionList(Integer.parseInt(compCode), "DES%");
            postList = new ArrayList<SelectItem>();
            postList.add(new SelectItem("0", "--Select--"));
            for (HrMstStructTO structMasterTO : structMasterTOs) {
                postList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                        structMasterTO.getDescription()));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method dropDownPositionList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method dropDownPositionList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method dropDownPositionList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fetchCurrentRow1(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Advertisement No"));
        currentRow1 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (ExtensionSearchTable item : searchExtension) {
            if (item.getCandidateSrno().equals(accNo)) {
                currentItem1 = item;
                break;
            }
        }
    }

    public void extensionDetailTable() {
        setMessage1("");
        searchExtension = new ArrayList<ExtensionSearchTable>();
        try {
            List resultLt = new ArrayList();

            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            resultLt = interviewActionPlanDelegate.extensionOfAppointmentSearchList(Integer.parseInt(compCode), candidateSrno);

            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    ExtensionSearchTable rd = new ExtensionSearchTable();
                    rd.setIntCode(result[0].toString());
                    rd.setCandidateSrno(result[1].toString());
                    if (result[2] != null) {
                        Date test = (Date) result[2];
                        rd.setExpectJoin(sdf.format(test));
                    }
                    if (result[3] != null) {
                        Date test1 = (Date) result[3];
                        rd.setExtension(sdf.format(test1));
                    }
                    rd.setCandFirstName(result[4].toString() + ' ' + result[5].toString() + ' ' + result[6].toString());
                    if (result[7] != null) {
                        Date test2 = (Date) result[7];
                        rd.setIntDt(sdf.format(test2));
                    }
                    rd.setIntVenue(result[8].toString());
                    rd.setTimeIn(result[9].toString().substring(11, 16));
                    rd.setDesgCode(result[10].toString());
                    searchExtension.add(rd);
                }
            } else {
                setMessage1("No Data Exist");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method extensionDetailTable()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method extensionDetailTable()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method extensionDetailTable()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectAction() throws ParseException {
        saveDisable = false;
        setIntCodeHide(currentItem1.getIntCode());
        this.setCandidateCode(currentItem1.getCandidateSrno());
        this.setCandidateName(currentItem1.getCandFirstName());
        if (currentItem1.getIntDt() != null) {
            this.setInterviewDt(sdf.parse(currentItem1.getIntDt()));
        }
        this.setVenue(currentItem1.getIntVenue());
        this.setInterviewTime(currentItem1.getTimeIn());
        this.setPost(currentItem1.getDesgCode());
        if (currentItem1.getExpectJoin() != null) {
            this.setDtExpectedofJoining(sdf.parse(currentItem1.getExpectJoin()));
        }
        if (currentItem1.getExtension() != null) {
            this.setDtOfJoiningExtension(sdf.parse(currentItem1.getExtension()));
        }
    }

    public void closePanel() {
        setMessage1("");
        setCandidateSrno("");
        searchExtension = new ArrayList<ExtensionSearchTable>();
        mode = "Save";
    }

    public void fetchCurrentRow2(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Interview Code"));
        currentRow2 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (ExtensionEditTable item : inteviewDetail) {
            if (item.getIntCode().equals(accNo)) {
                currentItem2 = item;
                break;
            }
        }
    }

    public void closePanelSecond() {
        setMessage1("");
        setInterviewCode("");
        inteviewDetail = new ArrayList<ExtensionEditTable>();
        disableUpdate = true;
        mode = "update";
    }

    public void extensionDetailUpdate() {
        setMessage1("");
        inteviewDetail = new ArrayList<ExtensionEditTable>();
        try {
            List resultLt = new ArrayList();
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            resultLt = interviewActionPlanDelegate.extensionOfAppointmentEditList(Integer.parseInt(compCode), interviewCode);
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    ExtensionEditTable rd = new ExtensionEditTable();
                    rd.setIntCode(result[0].toString());
                    rd.setCandidateSrno(result[1].toString());
                    rd.setCandName(result[2].toString() + ' ' + result[3].toString() + ' ' + result[4].toString());
                    if (result[5] != null) {
                        Date test = (Date) result[5];
                        rd.setIntDt(sdf.format(test));
                    }
                    rd.setIntTime(result[6].toString());
                    rd.setIntVenue(result[7].toString());
                    rd.setDesgCode(result[8].toString());
                    if (result[9] != null) {
                        Date test1 = (Date) result[9];
                        rd.setExpectJoin(sdf.format(test1));
                    }
                    if (result[10] != null) {
                        Date test2 = (Date) result[10];
                        rd.setExtension(sdf.format(test2));
                    }
                    inteviewDetail.add(rd);
                }
            } else {
                setMessage1("No Data Found");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method extensionDetailUpdate()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method extensionDetailUpdate()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method extensionDetailUpdate()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void updateAction() throws ParseException {
        saveDisable = false;
        setIntCodeHide1(currentItem2.getIntCode());
        this.setCandidateCode(currentItem2.getCandidateSrno());
        this.setCandidateName(currentItem2.getCandName());

        if (currentItem2.getIntDt() != null) {
            this.setInterviewDt(sdf.parse(currentItem2.getIntDt()));
        }
        this.setVenue(currentItem2.getIntVenue());
        this.setInterviewTime(currentItem2.getIntTime().substring(11, 16));
        this.setPost(currentItem2.getDesgCode());
        if (currentItem2.getExpectJoin() != null) {
            this.setDtExpectedofJoining(sdf.parse(currentItem2.getExpectJoin()));
        }
        if (currentItem2.getExtension() != null) {
            this.setDtOfJoiningExtension(sdf.parse(currentItem2.getExtension()));
        }
    }

    public void refresh() {
        setMessage1("");
        setInterviewCode("");
        inteviewDetail = new ArrayList<ExtensionEditTable>();
        setCandidateSrno("");
        searchExtension = new ArrayList<ExtensionSearchTable>();
        saveDisable = true;
        disableUpdate = false;
        this.setCandidateCode("");
        this.setCandidateName("");
        this.setInterviewDt(null);
        this.setVenue("");
        this.setInterviewTime("");
        this.setPost("0");
        this.setDtExpectedofJoining(null);
        this.setDtOfJoiningExtension(null);
        setOperation("0");
    }

        public void claearAll() {
        setMessage("");
        setMessage1("");
        setInterviewCode("");
        inteviewDetail = new ArrayList<ExtensionEditTable>();
        setCandidateSrno("");
        searchExtension = new ArrayList<ExtensionSearchTable>();
        saveDisable = true;
        disableUpdate = false;
        this.setCandidateCode("");
        this.setCandidateName("");
        this.setInterviewDt(null);
        this.setVenue("");
        this.setInterviewTime("");
        this.setPost("0");
        this.setDtExpectedofJoining(null);
        this.setDtOfJoiningExtension(null);
        setOperation("0");
    }

    public void extensionOfAppointmentSaveUpdateAction() {
        try {
            if (operation.equalsIgnoreCase("0")) {
                setMessage("Please select an operation !");
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
            if (candidateCode.equalsIgnoreCase("")) {
                setMessage("please fill the Candidate code");
                return;
            }
            if (candidateCode == null) {
                setMessage("please fill the Candidate code");
                return;
            }
            if (dtOfJoiningExtension == null) {
                setMessage("please fill the Date Of Joining Extension");
                return;
            }
            if (mode.equalsIgnoreCase("Save")) {
                if (intCodeHide.equalsIgnoreCase("")) {
                    setMessage("please fill the interview Code");
                    return;
                }
                if (intCodeHide == null) {
                    setMessage("please fill the advertisementCode");
                    return;
                }
                String stateFlag = "Y";
                String stateUpflag = "Y";
                HrInterviewDtTO hrInt = new HrInterviewDtTO();
                HrInterviewDtPKTO hrIntPK = new HrInterviewDtPKTO();
                hrIntPK.setCompCode(Integer.parseInt(compCode));
                hrIntPK.setIntCode(intCodeHide);
                hrIntPK.setCandSrno(candidateCode);
                hrInt.setHrInterviewDtPKTO(hrIntPK);
                hrInt.setAdviceCancel('N');
                hrInt.setExpectJoin(ymd.parse(ymd.format(dtExpectedofJoining)));
                hrInt.setExtension(ymd.parse(ymd.format(dtOfJoiningExtension)));
                hrInt.setStatFlag(stateFlag);
                hrInt.setStatUpFlag(stateUpflag);
                hrInt.setModDate(getDate());
                hrInt.setAuthBy(getUserName());
                hrInt.setEnteredBy(getUserName());
                InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
                String resultSave = interviewActionPlanDelegate.extensionOfAppointmentSaveAction(hrInt);
                setMessage(resultSave);
            } /******************* update *******************/
            else if (mode.equalsIgnoreCase("update")) {
                if (intCodeHide1.equalsIgnoreCase("")) {
                    setMessage("please fill the int code");
                    return;
                }
                if (intCodeHide1 == null) {
                    setMessage("please fill the int code");
                    return;
                }
                String stateFlag = "Y";
                String stateUpflag = "Y";
                HrInterviewDtTO hrInt = new HrInterviewDtTO();
                HrInterviewDtPKTO hrIntPK = new HrInterviewDtPKTO();
                hrIntPK.setCompCode(Integer.parseInt(compCode));
                hrIntPK.setIntCode(intCodeHide1);
                hrIntPK.setCandSrno(candidateCode);
                hrInt.setHrInterviewDtPKTO(hrIntPK);
                hrInt.setCancel(null);
                hrInt.setAdviceCancel(null);
                hrInt.setExpectJoin(ymd.parse(ymd.format(dtExpectedofJoining)));
                hrInt.setExtension(ymd.parse(ymd.format(dtOfJoiningExtension)));
                hrInt.setStatFlag(stateFlag);
                hrInt.setStatUpFlag(stateUpflag);
                hrInt.setModDate(getDate());
                hrInt.setAuthBy(getUserName());
                hrInt.setEnteredBy(getUserName());
                InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
                String resultUpdate = interviewActionPlanDelegate.extensionOfAppointmentUpdateAction(hrInt);
                setMessage(resultUpdate);
            }
            refresh();
        } catch (WebException e) {
            logger.error("Exception occured while executing method updateButton()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method updateButton()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method updateButton()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void setOperationOnBlur() {
        setMessage("");
        if (operation.equalsIgnoreCase("0")) {
            setMessage("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
            mode = "Save";
        } else if (operation.equalsIgnoreCase("2")) {
            mode = "update";
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
}
