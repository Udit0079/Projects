/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.PostIntSearch;
import com.hrms.web.pojo.PostIntInterviewDetailSearch;
import com.cbs.servlets.Init;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.to.HrPersonnelDetailsTO;
import java.util.Calendar;
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
import java.net.InetAddress;
import java.text.ParseException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zeeshan Waris
 */
public class PostInterviewActionPlan extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(PostInterviewActionPlan.class);
    private String message;
    private String advertisementCode;
    private String jobCode;
    private String interviewCode;
    private String candidateCode;
    private String candidateName;
    private String candidateNameFrst;
    private String candidateNameLast;
    private String interviewerName;
    private String interviewerNameFrst;
    private String venue;
    private String post;
    private Date interviewDt;
    private String interviewTime;
    private String result;
    private Date expectedDtofJoining;
    private String remarks;
    private String compCode = "0";
    private List<SelectItem> interviewerNameList;
    private List<SelectItem> interviewerNameListSecond;
    private List<SelectItem> postList;
    private List<SelectItem> postresult;
    private String message1;
    private List<PostIntSearch> searchPost;
    private int currentRow1;
    private PostIntSearch currentItem1 = new PostIntSearch();
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String interviwerName;
    private String mode;
    private boolean saveDisable;
    private boolean flag;
    private boolean disableUpdate;
    private List<PostIntInterviewDetailSearch> inteviewDetail;
    private int currentRow2, defaultComp;
    private PostIntInterviewDetailSearch currentItem2 = new PostIntInterviewDetailSearch();
    private String operation;
    private List<SelectItem> operationList;

    public PostIntInterviewDetailSearch getCurrentItem2() {
        return currentItem2;
    }

    public void setCurrentItem2(PostIntInterviewDetailSearch currentItem2) {
        this.currentItem2 = currentItem2;
    }

    public int getCurrentRow2() {
        return currentRow2;
    }

    public void setCurrentRow2(int currentRow2) {
        this.currentRow2 = currentRow2;
    }

    public List<PostIntInterviewDetailSearch> getInteviewDetail() {
        return inteviewDetail;
    }

    public void setInteviewDetail(List<PostIntInterviewDetailSearch> inteviewDetail) {
        this.inteviewDetail = inteviewDetail;
    }

    public boolean isDisableUpdate() {
        return disableUpdate;
    }

    public void setDisableUpdate(boolean disableUpdate) {
        this.disableUpdate = disableUpdate;
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

    public String getInterviwerName() {
        return interviwerName;
    }

    public void setInterviwerName(String interviwerName) {
        this.interviwerName = interviwerName;
    }

    public List<PostIntSearch> getSearchPost() {
        return searchPost;
    }

    public void setSearchPost(List<PostIntSearch> searchPost) {
        this.searchPost = searchPost;
    }

    public PostIntSearch getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(PostIntSearch currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
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

    public Date getExpectedDtofJoining() {
        return expectedDtofJoining;
    }

    public void setExpectedDtofJoining(Date expectedDtofJoining) {
        this.expectedDtofJoining = expectedDtofJoining;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAdvertisementCode() {
        return advertisementCode;
    }

    public void setAdvertisementCode(String advertisementCode) {
        this.advertisementCode = advertisementCode;
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

    public String getCandidateNameFrst() {
        return candidateNameFrst;
    }

    public void setCandidateNameFrst(String candidateNameFrst) {
        this.candidateNameFrst = candidateNameFrst;
    }

    public String getCandidateNameLast() {
        return candidateNameLast;
    }

    public void setCandidateNameLast(String candidateNameLast) {
        this.candidateNameLast = candidateNameLast;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInterviewCode() {
        return interviewCode;
    }

    public void setInterviewCode(String interviewCode) {
        this.interviewCode = interviewCode;
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

    public String getInterviewerName() {
        return interviewerName;
    }

    public void setInterviewerName(String interviewerName) {
        this.interviewerName = interviewerName;
    }

    public String getInterviewerNameFrst() {
        return interviewerNameFrst;
    }

    public void setInterviewerNameFrst(String interviewerNameFrst) {
        this.interviewerNameFrst = interviewerNameFrst;
    }

    public List<SelectItem> getInterviewerNameList() {
        return interviewerNameList;
    }

    public void setInterviewerNameList(List<SelectItem> interviewerNameList) {
        this.interviewerNameList = interviewerNameList;
    }

    public List<SelectItem> getInterviewerNameListSecond() {
        return interviewerNameListSecond;
    }

    public void setInterviewerNameListSecond(List<SelectItem> interviewerNameListSecond) {
        this.interviewerNameListSecond = interviewerNameListSecond;
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

    public List<SelectItem> getPostList() {
        return postList;
    }

    public void setPostList(List<SelectItem> postList) {
        this.postList = postList;
    }

    public List<SelectItem> getPostresult() {
        return postresult;
    }

    public void setPostresult(List<SelectItem> postresult) {
        this.postresult = postresult;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    
    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
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
    
    public PostInterviewActionPlan() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            setTodayDate(sdf.format(date));
            this.setMessage("");
            dropDownPositionList();
            dropDownNameList();
            
            postresult = new ArrayList<SelectItem>();
            postresult.add(new SelectItem("0", "--Select--"));
            postresult.add(new SelectItem("S", "Selected"));
            postresult.add(new SelectItem("R", "Rejected"));
            postresult.add(new SelectItem("H", "Hold"));
            
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","-Select-"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","Edit"));
            
            setSaveDisable(true);
            setDisableUpdate(true);
            setFlag(true);
            mode = "update";
            setOperation("0");
        } catch (Exception e) {
            logger.error("Exception occured while executing method PostInterviewActionPlan()", e);
            logger.error("PostInterviewActionPlan()", e);
        }
    }
      public void setOperationOnBlur()
    {
        if(operation.equalsIgnoreCase("0"))
        {
            setMessage("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
           setMessage("");
           setSaveDisable(false);
           setDisableUpdate(true);
           
         } else if (operation.equalsIgnoreCase("2")) {
            setMessage("");
            setSaveDisable(false);
            setDisableUpdate(false);
        
        }
    }
    public void dropDownPositionList() {
        try {
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            List<HrMstStructTO> structMasterTOs = interviewActionPlanDelegate.getPositionPostList(Integer.parseInt(compCode), "DES%");
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

    public void dropDownNameList() {
        try {
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            List<HrPersonnelDetailsTO> personelTOs = interviewActionPlanDelegate.getintrviewersNameList(Integer.parseInt(compCode));
            interviewerNameList = new ArrayList<SelectItem>();
            interviewerNameListSecond = new ArrayList<SelectItem>();
            interviewerNameList.add(new SelectItem("0", "--Select--"));
            interviewerNameListSecond.add(new SelectItem("0", "--Select--"));
            for (HrPersonnelDetailsTO personelTO : personelTOs) {
                interviewerNameList.add(new SelectItem(personelTO.getEmpId(), personelTO.getEmpName()));
                interviewerNameListSecond.add(new SelectItem(personelTO.getEmpId(), personelTO.getEmpName()));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method dropDownRecruitmentTypeList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method dropDownRecruitmentTypeList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method dropDownRecruitmentTypeList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fetchCurrentRow1(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Advertisement No"));
        currentRow1 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (PostIntSearch item : searchPost) {
            if (item.getAdvNo().equals(accNo)) {
                currentItem1 = item;
                break;
            }
        }
    }

    public void interviewerDetails() {
        setMessage1("");
        searchPost = new ArrayList<PostIntSearch>();
        try {
            List resultLt = new ArrayList();
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            resultLt = interviewActionPlanDelegate.postIntInterviewersDetail(Integer.parseInt(compCode), interviwerName);
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] postResult = (Object[]) i1.next();
                    PostIntSearch rd = new PostIntSearch();
                    rd.setAdvNo(postResult[0].toString());
                    rd.setValue1(postResult[0].toString());
                    rd.setValue2(postResult[1].toString());
                    rd.setValue3(postResult[2].toString());
                    rd.setValue4(postResult[3].toString());
                    rd.setValue13(postResult[4].toString());
                    rd.setValue5(postResult[5].toString());
                    rd.setValue6(postResult[6].toString());
                    rd.setValue7(postResult[7].toString());
                    if (postResult[8] == null) {
                        rd.setValue8("");
                    } else {
                        rd.setValue8(postResult[8].toString());
                    }
                    if (postResult[9] == null) {
                        rd.setValue9("");
                    } else {
                        rd.setValue9(postResult[9].toString());
                    }
                    Date test = (Date) postResult[10];
                    rd.setValue12(sdf.format(test));
                    //setValue12(result[10].toString());  
                    rd.setValue10(postResult[11].toString());
                    rd.setValue11(postResult[12].toString());
                    rd.setSerialNo(postResult[3].toString());
                    rd.setFrstName(postResult[5].toString());
                    rd.setMidName(postResult[6].toString());
                    rd.setLastName(postResult[7].toString());
                    searchPost.add(rd);
                }
            } else {
                setMessage1("No Data Found");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method interviewerDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method interviewerDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method interviewerDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void select() throws ParseException {
        saveDisable = false;
        this.setAdvertisementCode(currentItem1.getValue1());
        this.setJobCode(currentItem1.getValue2());
        this.setInterviewCode(currentItem1.getValue3());
        this.setCandidateCode(currentItem1.getValue4());
        this.setCandidateName(currentItem1.getValue5());
        this.setCandidateNameFrst(currentItem1.getValue6());
        this.setCandidateNameLast(currentItem1.getValue7());
        this.setInterviewerName(currentItem1.getValue8());
        this.setInterviewerNameFrst(currentItem1.getValue9());
        this.setVenue(currentItem1.getValue10());
        this.setPost(currentItem1.getValue11());
        this.setInterviewDt(sdf.parse(currentItem1.getValue12()));
        this.setInterviewTime(currentItem1.getValue13().substring(11, 16));
    }

    public void closePanel2() {
        setMessage1("");
        setInterviwerName("");
        searchPost = new ArrayList<PostIntSearch>();
        mode = "Save";
    }

    public void refresh() {
        setOperation("0");
        setMessage1("");
        setInterviwerName("");
        searchPost = new ArrayList<PostIntSearch>();
        saveDisable = true;
        disableUpdate = false;
        this.setAdvertisementCode("");
        this.setJobCode("");
        this.setInterviewCode("");
        this.setCandidateCode("");
        this.setCandidateName("");
        this.setCandidateNameFrst("");
        this.setCandidateNameLast("");
        this.setInterviewerName("0");
        this.setInterviewerNameFrst("0");
        this.setVenue("");
        this.setPost("0");
        this.setInterviewDt(null);
        this.setInterviewTime("");
        this.setResult("--Select--");
        this.setExpectedDtofJoining(null);
        this.setRemarks("");
    }

    public void clearAll() {
        setMessage("");
        setMessage1("");
        setInterviwerName("");
        searchPost = new ArrayList<PostIntSearch>();
        saveDisable = true;
        disableUpdate = true;
        this.setAdvertisementCode("");
        this.setJobCode("");
        this.setInterviewCode("");
        this.setCandidateCode("");
        this.setCandidateName("");
        this.setCandidateNameFrst("");
        this.setCandidateNameLast("");
        this.setInterviewerName("0");
        this.setInterviewerNameFrst("0");
        this.setVenue("");
        this.setPost("0");
        this.setInterviewDt(null);
        this.setInterviewTime("");
        this.setResult("--Select--");
        this.setExpectedDtofJoining(null);
        this.setRemarks("");
        this.setOperation("0");
    }

    public void saveButton() {
        try {
            char reslt = 'A';
            if (compCode.equalsIgnoreCase("")||compCode == null) {
                setMessage("Fill the Comany code");
                return;
            }
            
            if (interviewCode.equalsIgnoreCase("")||interviewCode == null) {
                setMessage("Fill the Interview code");
                return;
            }
            if (advertisementCode.equalsIgnoreCase("")||advertisementCode == null) {
                setMessage("Fill the Advertisement Code");
                return;
            }
            if (jobCode.equalsIgnoreCase("")||jobCode == null) {
                setMessage("Fill the Job Code");
                return;
            }
            if (candidateCode == null||candidateCode.equalsIgnoreCase("")) {
                setMessage("Fill the Candidate Code");
                return;
            }
            
            if (result.equalsIgnoreCase("0")) {
                setMessage("Select the result");
                return;
            }
            if(result.equalsIgnoreCase("R")){
             setMessage("Save data of selected or on hold candidate!");            
            }
            if (mode.equalsIgnoreCase("Save")) {
                String stateFlag = "Y";
                String stateUpflag = "Y";
                HrInterviewDtTO hrinterviewDt = new HrInterviewDtTO();
                HrInterviewDtPKTO hrinterviewDtPK = new HrInterviewDtPKTO();
                hrinterviewDtPK.setCompCode(Integer.parseInt(compCode));
                hrinterviewDtPK.setIntCode(interviewCode);
                hrinterviewDtPK.setAdvtCode(advertisementCode);
                hrinterviewDtPK.setJobCode(jobCode);
                hrinterviewDtPK.setCandSrno(candidateCode);
                hrinterviewDt.setHrInterviewDtPKTO(hrinterviewDtPK);
                if (result.equalsIgnoreCase("S")) {
                    reslt = 'S';
                }
                if (result.equalsIgnoreCase("R")) {
                    reslt = 'R';
                }
                if (result.equalsIgnoreCase("H")) {
                    reslt = 'H';
                }
                hrinterviewDt.setIntResult(reslt);

                Date tempDt = ymd.parse("19000101");
                Calendar cal = Calendar.getInstance();
                cal.setTime(tempDt);
                int hh = Integer.parseInt(interviewTime.substring(0, 2));
                int hhh = hh * 60;
                int mm = Integer.parseInt(interviewTime.substring(3));
                int minutes = hhh + mm;
                cal.add(Calendar.MINUTE, minutes);
                hrinterviewDt.setTimeIn(cal.getTime());
                hrinterviewDt.setCancel(null);
                hrinterviewDt.setAdviceCancel('N');
                hrinterviewDt.setExpectJoin(ymd.parse(ymd.format(expectedDtofJoining)));
                hrinterviewDt.setExtension(null);
                hrinterviewDt.setIntRemarks(remarks);
                hrinterviewDt.setDefaultCompCode(defaultComp);
                hrinterviewDt.setStatFlag(stateFlag);
                hrinterviewDt.setStatUpFlag(stateUpflag);
                hrinterviewDt.setModDate(getDate());
                hrinterviewDt.setAuthBy(getUserName());
                hrinterviewDt.setEnteredBy(getUserName());
                InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
                String resultSave = interviewActionPlanDelegate.savePostInterviewActionPlan(hrinterviewDt);
                setMessage(resultSave);
            } /******************* update *******************/
            else if (mode.equalsIgnoreCase("update")) {
                HrInterviewDtTO hrinterviewDt = new HrInterviewDtTO();
                HrInterviewDtPKTO hrinterviewDtPK = new HrInterviewDtPKTO();
                hrinterviewDtPK.setCompCode(Integer.parseInt(compCode));
                hrinterviewDtPK.setIntCode(interviewCode);
                hrinterviewDtPK.setAdvtCode(advertisementCode);
                hrinterviewDtPK.setJobCode(jobCode);
                hrinterviewDtPK.setCandSrno(candidateCode);
                hrinterviewDt.setHrInterviewDtPKTO(hrinterviewDtPK);

                if (result.equalsIgnoreCase("S")) {
                    reslt = 'S';
                }
                if (result.equalsIgnoreCase("R")) {
                    reslt = 'R';
                }
                if (result.equalsIgnoreCase("H")) {
                    reslt = 'H';
                }
                hrinterviewDt.setIntResult(reslt);
                hrinterviewDt.setExpectJoin(ymd.parse(ymd.format(expectedDtofJoining)));
                hrinterviewDt.setIntRemarks(remarks);
                InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
                String resultUpdate = interviewActionPlanDelegate.updatePostInterviewActionPlan(hrinterviewDt);
                setMessage(resultUpdate);
            }
            refresh();
        } catch (WebException e) {
            logger.error("Exception occured while executing method updateButton()", e);
            this.setMessage( "Duplicate entity exists.");
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method updateButton()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method updateButton()", e);
            this.setMessage( "Duplicate entity exists!");
        }
    }

    public void fetchCurrentRow2(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Interview Code"));
        currentRow2 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (PostIntInterviewDetailSearch item : inteviewDetail) {
            if (item.getIntCode().equals(accNo)) {
                currentItem2 = item;
                break;
            }
        }
    }

    public void closePanel() {
        setMessage1("");
        setInterviwerName("");
        inteviewDetail = new ArrayList<PostIntInterviewDetailSearch>();
        disableUpdate = true;
        mode = "update";
    }

    public void interviewDetailUpdate() {
        setMessage1("");
        inteviewDetail = new ArrayList<PostIntInterviewDetailSearch>();
        try {
            List resultLt = new ArrayList();
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            resultLt = interviewActionPlanDelegate.postIntViewDetail(Integer.parseInt(compCode), interviwerName);
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] resultView = (Object[]) i1.next();
                    PostIntInterviewDetailSearch rd = new PostIntInterviewDetailSearch();
                    rd.setIntCode(resultView[0].toString());
                    rd.setAdvmntCode(resultView[1].toString());
                    rd.setJobdtlCode(resultView[2].toString());
                    rd.setCandSrno(resultView[3].toString());
                    rd.setFrstName(resultView[4].toString());
                    rd.setMidName(resultView[5].toString());
                    rd.setLastName(resultView[6].toString());
                    //rd.setIntDt(sdf.format(test));
                    inteviewDetail.add(rd);
                }
            } else {
                setMessage1("No Data Found");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method interviewDetailUpdate()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method interviewDetailUpdate()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method interviewDetailUpdate()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectSearch() {
        saveDisable = false;
        setInterviewCode(currentItem2.getIntCode());
        setAdvertisementCode(currentItem2.getAdvmntCode());
        setJobCode(currentItem2.getJobdtlCode());
        setCandidateCode(currentItem2.getCandSrno());
        setCandidateName(currentItem2.getFrstName());
        setCandidateNameFrst(currentItem2.getMidName());
        setCandidateNameLast(currentItem2.getLastName());
        exitOnblur();
    }

    public void exitOnblur() {
        setMessage1("");
        inteviewDetail = new ArrayList<PostIntInterviewDetailSearch>();
        try {
            List resultLt = new ArrayList();
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            resultLt = interviewActionPlanDelegate.postIntEditDetail(Integer.parseInt(compCode), currentItem2.getIntCode(), currentItem2.getAdvmntCode(), currentItem2.getJobdtlCode(), currentItem2.getCandSrno());
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] resultEdit = (Object[]) i1.next();
                    setResult(resultEdit[0].toString());
                    setInterviewTime(resultEdit[1].toString().substring(11, 16));
                    Date test = (Date) resultEdit[2];
                    String dt = sdf.format(test);
                    setExpectedDtofJoining(sdf.parse(dt));
                    setRemarks(resultEdit[4].toString());
                    setPost(resultEdit[5].toString());
                    if (resultEdit[6] == null) {
                        setInterviewerName("0");
                    } else {
                        setInterviewerName(resultEdit[6].toString());
                    }
                    if (resultEdit[7] == null) {
                        setInterviewerNameFrst("0");
                    } else {
                        setInterviewerNameFrst(resultEdit[7].toString());
                    }
                    setVenue(resultEdit[8].toString());
                    Date test1 = (Date) resultEdit[9];
                    String dt1 = sdf.format(test1);
                    setInterviewDt(sdf.parse(dt1));
                }
            } else {
                setMessage1("No Data Found");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method exitOnblur()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method exitOnblur()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitOnblur()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
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
