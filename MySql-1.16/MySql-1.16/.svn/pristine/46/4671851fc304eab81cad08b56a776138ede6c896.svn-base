/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.CancellationOfAppointmentAdviceTable;
import com.cbs.web.controller.BaseBean;
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
public class CancellationOfAppointmentAdvice extends BaseBean {
    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(CancellationOfAppointmentAdvice.class);
    private String message;
    private String message1;
    private String candidateCode;
    private String candidateName;
    private Date interviewDate;
    private String venue;
    private String interviewTime;
    private String post;
    private Date dateOfExpJoin;
    private Date dateOfJoinExt;
    private Date dateOfCancel;
    private String candidateId;
    private String compCode="0";
    private String interviewCode;
    private String interviewCodeCond;
    private HttpServletRequest req;
    private List<SelectItem> postList;
    private List<CancellationOfAppointmentAdviceTable> candidateApp;
    private int currentRow, defaultComp;
    private CancellationOfAppointmentAdviceTable currentItem = new CancellationOfAppointmentAdviceTable();
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private String value5;
    private String value6;
    private String value7;
    private String value8;
    private String value9;
    private String value10;
    private String value11;
    private String mode;
    private boolean saveDisable;
    private boolean flag;
    private boolean flagCancel;
    private String operation;
    private List<SelectItem> operationList;

    
    public  String getOperation() {
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

    
    public String getInterviewCodeCond() {
        return interviewCodeCond;
    }

    public void setInterviewCodeCond(String interviewCodeCond) {
        this.interviewCodeCond = interviewCodeCond;
    }

    public boolean isFlagCancel() {
        return flagCancel;
    }

    public void setFlagCancel(boolean flagCancel) {
        this.flagCancel = flagCancel;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public String getInterviewCode() {
        return interviewCode;
    }

    public void setInterviewCode(String interviewCode) {
        this.interviewCode = interviewCode;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue10() {
        return value10;
    }

    public void setValue10(String value10) {
        this.value10 = value10;
    }

    public String getValue11() {
        return value11;
    }

    public void setValue11(String value11) {
        this.value11 = value11;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getValue4() {
        return value4;
    }

    public void setValue4(String value4) {
        this.value4 = value4;
    }

    public String getValue5() {
        return value5;
    }

    public void setValue5(String value5) {
        this.value5 = value5;
    }

    public String getValue6() {
        return value6;
    }

    public void setValue6(String value6) {
        this.value6 = value6;
    }

    public String getValue7() {
        return value7;
    }

    public void setValue7(String value7) {
        this.value7 = value7;
    }

    public String getValue8() {
        return value8;
    }

    public void setValue8(String value8) {
        this.value8 = value8;
    }

    public String getValue9() {
        return value9;
    }

    public void setValue9(String value9) {
        this.value9 = value9;
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

    public CancellationOfAppointmentAdviceTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CancellationOfAppointmentAdviceTable currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public Date getDateOfCancel() {
        return dateOfCancel;
    }

    public void setDateOfCancel(Date dateOfCancel) {
        this.dateOfCancel = dateOfCancel;
    }

    public Date getDateOfExpJoin() {
        return dateOfExpJoin;
    }

    public void setDateOfExpJoin(Date dateOfExpJoin) {
        this.dateOfExpJoin = dateOfExpJoin;
    }

    public Date getDateOfJoinExt() {
        return dateOfJoinExt;
    }

    public void setDateOfJoinExt(Date dateOfJoinExt) {
        this.dateOfJoinExt = dateOfJoinExt;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
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

    
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public List<SelectItem> getPostList() {
        return postList;
    }

    public void setPostList(List<SelectItem> postList) {
        this.postList = postList;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public List<CancellationOfAppointmentAdviceTable> getCandidateApp() {
        return candidateApp;
    }

    public void setCandidateApp(List<CancellationOfAppointmentAdviceTable> candidateApp) {
        this.candidateApp = candidateApp;
    }

    public CancellationOfAppointmentAdvice() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","---Select---"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","View"));
            candidateApp = new ArrayList<CancellationOfAppointmentAdviceTable>();
            setTodayDate(sdf.format(date));
            this.setMessage("");
            this.setMessage1("");
            getDDPostList(compCode, "DES%");
            refreshCencelApp();
            setFlag(true);
            setFlagCancel(true);
            setSaveDisable(true);
        } catch (Exception e) {
            logger.error("Exception occured while executing method CancellationOfAppointmentAdvice()", e);
            logger.error("CancellationOfAppointmentAdvice()", e);
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void getDDPostList(String compCode, String description) {
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
            logger.error("Exception occured while executing method getInitialEventData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getInitialEventData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getInitialEventData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Candidate Code"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (CancellationOfAppointmentAdviceTable item : candidateApp) {
            if (item.getCandidateCodeTab().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void appointmentDetailTable() {
        setMessage1("");
        candidateApp = new ArrayList<CancellationOfAppointmentAdviceTable>();
        try {
//            if (candidateId.equalsIgnoreCase("")) {
//                setMessage1("Please fill the Candidate Id");
//                return;
//            }
//            if (candidateId == null) {
//                setMessage1("Please fill the Candidate Id");
//                return;
//            }

            List resultLt = new ArrayList();
            
            InterviewActionPlanDelegate cancellationOfAppointmentAdviceDelegate = new InterviewActionPlanDelegate();
            resultLt = cancellationOfAppointmentAdviceDelegate.searchCencelAppointmentData(Integer.parseInt(compCode), candidateId);
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    CancellationOfAppointmentAdviceTable rd = new CancellationOfAppointmentAdviceTable();
                    Date test = (Date) result[2];
                    Date test1 = (Date) result[3];
                    Date test2 = (Date) result[7];
                    rd.setInterviewCodeCondTab(result[0].toString());
                    rd.setCandidateCodeTab(result[1].toString());
                    rd.setValueTab1(sdf.format(test)); //Expected joining date
                    rd.setValueTab2(sdf.format(test1));  //Extension Date
                    rd.setValueTab3(result[4].toString());   //Frist name
                    rd.setValueTab4(result[5].toString());   // Middle name
                    rd.setValueTab5(result[6].toString());   //Last Name
                    rd.setCandidateNameTab(result[4].toString() + ' ' + result[5].toString() + ' ' + result[6].toString());
                    rd.setInterviewDateTab(sdf.format(test2));  //Interview Date
                    rd.setValueTab6(result[8].toString());  // venue
                    rd.setValueTab7(result[9].toString());   //Interview Time
                    rd.setValueTab8(result[10].toString()); //Post
                    candidateApp.add(rd);
                }
            } else {
                setMessage1("Wrong code for Cancellation");
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

    public void selectAppointmentDetail() {
        try {
            this.setInterviewCodeCond(currentItem.getInterviewCodeCondTab());
            this.setCandidateCode(currentItem.getCandidateCodeTab());
            this.setDateOfExpJoin(sdf.parse(currentItem.getValueTab1()));
            this.setDateOfJoinExt(sdf.parse(currentItem.getValueTab2()));
            setCandidateName(currentItem.getCandidateNameTab());
            setInterviewDate(sdf.parse(currentItem.getInterviewDateTab()));
            this.setVenue(currentItem.getValueTab6());
            this.setInterviewTime(currentItem.getValueTab7().substring(11, 16));
            this.setPost(currentItem.getValueTab8());
            setFlagCancel(false);
            setSaveDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchCurrentRowView(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Interview Code"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (CancellationOfAppointmentAdviceTable item : candidateApp) {
            if (item.getCandidateCodeTab().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void appointmentDetailTableView() {
        setMessage1("");
        candidateApp = new ArrayList<CancellationOfAppointmentAdviceTable>();
        try {

//            if (interviewCode.equalsIgnoreCase("")) {
//                setMessage1("Please fill the Interview Code");
//                return;
//            }
//            if (interviewCode == null) {
//                setMessage1("Please fill the Interview Code");
//                return;
//            }
            List resultLt = new ArrayList();
            
            InterviewActionPlanDelegate cancellationOfAppointmentAdviceDelegate = new InterviewActionPlanDelegate();
            resultLt = cancellationOfAppointmentAdviceDelegate.viewCencelAppointmentData(Integer.parseInt(compCode), interviewCode);

            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    CancellationOfAppointmentAdviceTable rd = new CancellationOfAppointmentAdviceTable();
                    Date test = (Date) result[2];
                    Date test1 = (Date) result[3];
                    Date test2 = (Date) result[7];
                    Date test3 = (Date) result[11];
                    rd.setInterviewCodeTab(result[0].toString());
                    rd.setValueTab10(result[1].toString());  //Candidate Code
                    rd.setValueTab1(sdf.format(test)); //Expected joining date
                    rd.setValueTab2(sdf.format(test1));  //Extension Date
                    rd.setValueTab3(result[4].toString());   //Frist name
                    rd.setValueTab4(result[5].toString());   // Middle name
                    rd.setValueTab5(result[6].toString());   //Last Name
                    rd.setCandidateNameTab(result[4].toString() + ' ' + result[5].toString() + ' ' + result[6].toString());
                    rd.setValueTab11(sdf.format(test2));  //Interview Date
                    rd.setValueTab6(result[8].toString());  // venue
                    rd.setValueTab7(result[9].toString());   //Interview Time
                    rd.setValueTab8(result[10].toString()); //Post
                    rd.setValueTab9(sdf.format(test3)); // Cancel
                    candidateApp.add(rd);
                }
            } else {
                setMessage1("Please enter correct Interview Code");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method appointmentDetailTableView()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method appointmentDetailTableView()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method appointmentDetailTableView()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectAppointmentDetailView() throws ParseException {
        setFlagCancel(true);
        this.setCandidateCode(currentItem.getValueTab10());
        this.setDateOfJoinExt(sdf.parse(currentItem.getValueTab2()));
        this.setDateOfExpJoin(sdf.parse(currentItem.getValueTab1()));
        this.setDateOfJoinExt(sdf.parse(currentItem.getValueTab2()));
        setCandidateName(currentItem.getCandidateNameTab());
        setInterviewDate(sdf.parse(currentItem.getValueTab11()));
        this.setVenue(currentItem.getValueTab6());
        this.setInterviewTime(currentItem.getValueTab7().substring(11, 16));
        this.setPost(currentItem.getValueTab8());
        this.setDateOfCancel(sdf.parse(currentItem.getValueTab9()));

    }

    public void saveCancelOfAppointmentButton() {
        try {
            //          char reslt = 'A';
            String flag1 = "Y";

            if (dateOfCancel == null) {
                setMessage("Please fill the Date of Cancellation.");
                return;
            }
            HrInterviewDtTO hrInterviewDtTO = new HrInterviewDtTO();
            HrInterviewDtPKTO hrInterviewDtPKTO = new HrInterviewDtPKTO();
            hrInterviewDtPKTO.setCompCode(Integer.parseInt(compCode));
            hrInterviewDtPKTO.setIntCode(interviewCodeCond);
            hrInterviewDtPKTO.setCandSrno(candidateCode);
            hrInterviewDtTO.setHrInterviewDtPKTO(hrInterviewDtPKTO);
            hrInterviewDtTO.setDefaultCompCode(defaultComp);
            hrInterviewDtTO.setCancel((dateOfCancel));
            hrInterviewDtTO.setAdviceCancel('Y');
            hrInterviewDtTO.setStatFlag(flag1);
            hrInterviewDtTO.setStatUpFlag(flag1);
            hrInterviewDtTO.setModDate(date);
            hrInterviewDtTO.setAuthBy(getUserName());
            hrInterviewDtTO.setEnteredBy(getUserName());
            InterviewActionPlanDelegate cancellationOfAppointmentAdviceDelegate = new InterviewActionPlanDelegate();
            String result = cancellationOfAppointmentAdviceDelegate.updateCencellationOfAppointment(hrInterviewDtTO);
            refreshCencelApp();
            setMessage(result);
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveCancelOfAppointmentButton()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveCancelOfAppointmentButton()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveCancelOfAppointmentButton()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void refreshCencelApp() {
        setMessage1("");
        candidateApp = new ArrayList<CancellationOfAppointmentAdviceTable>();
        setMessage("");
        this.setSaveDisable(true);
        this.setCandidateCode("");
        this.setCandidateId("");
        this.setCandidateName("");
        this.setInterviewCode("");
        this.setInterviewDate(null);
        this.setVenue("");
        this.setInterviewTime("");
        this.setPost("0");
        this.setDateOfExpJoin(null);
        this.setDateOfJoinExt(null);
        this.setDateOfCancel(null);
        setOperation("0");

    }
      public void setOperationOnBlur()
    {
        if(operation.equalsIgnoreCase("0"))
        {
            setMessage("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
           
        } else if (operation.equalsIgnoreCase("2")) {
            
        }
    }
    public String btnExit() {
        try {
            refreshCencelApp();
        } catch (Exception e) {
            logger.error("Exception occured while executing method btnExit()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }
}
