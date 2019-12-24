/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.PreInteviewTable;
import com.hrms.web.pojo.PreInterviewerSearch;
import com.hrms.web.pojo.PreInterviewCodeTable;
import com.cbs.web.controller.BaseBean;
import java.util.Calendar;
import java.util.Iterator;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrDatabankPKTO;
import com.hrms.common.to.HrDatabankTO;
import com.hrms.common.to.HrInterviewDtPKTO;
import com.hrms.common.to.HrInterviewDtTO;
import com.hrms.common.to.HrInterviewHdPKTO;
import com.hrms.common.to.HrInterviewHdTO;
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

/**
 *
 * @author Zeeshan Waris
 */
public class PreInterviewActionPlan extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(PreInterviewActionPlan.class);
    private String message;
    private String interviewCode;
    private Date interviewDt;
    private String venue;
    private String startingTime;
    private String avgtime;
    private String lunchTime;
    private String position;
    private String recruitmenttype;
    private String fare;
    private String interviewNameFrst;
    private String interviewNameSecnd;
    private String designationFrst;
    private String designationSecnd;
    private String compCode = "0";
    private List<SelectItem> positionList;
    private List<SelectItem> recruitmentTypeList;
    private List<SelectItem> fareList;
    private List<PreInteviewTable> instrReg;
    private int currentRow;
    private PreInteviewTable currentItem = new PreInteviewTable();
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String message1;
    private List<PreInterviewerSearch> interviewSearch;
    private int currentRow1;
    private PreInterviewerSearch currentItem1 = new PreInterviewerSearch();
    private String interviwerName;
    private String flag;
    private String flag1;
    private String interViewCode;
    private List<PreInterviewCodeTable> inteviewDetail;
    private int currentRow2;
    private PreInterviewCodeTable currentItem2 = new PreInterviewCodeTable();
    private boolean disableUpdate;
    private String nameSearch;
    private String desigsearch;
    private String mode;
    private Integer defaultComp;
    private String empIdInterviewer2 = "", empIdInterviewer1 = "", desgCode1 = "", desgCode2 = "";
    private List<SelectItem> selectItemList;
    private String[] selectItemResultList;
    private String operation;
    private boolean flagForFirstInterviewerName = false;
    private boolean flagForSecondInterviewerName = false;
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

    public String getDesigsearch() {
        return desigsearch;
    }

    public void setDesigsearch(String desigsearch) {
        this.desigsearch = desigsearch;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public boolean isDisableUpdate() {
        return disableUpdate;
    }

    public void setDisableUpdate(boolean disableUpdate) {
        this.disableUpdate = disableUpdate;
    }

    public PreInterviewCodeTable getCurrentItem2() {
        return currentItem2;
    }

    public void setCurrentItem2(PreInterviewCodeTable currentItem2) {
        this.currentItem2 = currentItem2;
    }

    public int getCurrentRow2() {
        return currentRow2;
    }

    public void setCurrentRow2(int currentRow2) {
        this.currentRow2 = currentRow2;
    }

    public String getInterViewCode() {
        return interViewCode;
    }

    public void setInterViewCode(String interViewCode) {
        this.interViewCode = interViewCode;
    }

    public List<PreInterviewCodeTable> getInteviewDetail() {
        return inteviewDetail;
    }

    public void setInteviewDetail(List<PreInterviewCodeTable> inteviewDetail) {
        this.inteviewDetail = inteviewDetail;
    }

    public String getInterviwerName() {
        return interviwerName;
    }

    public void setInterviwerName(String interviwerName) {
        this.interviwerName = interviwerName;
    }

    public PreInterviewerSearch getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(PreInterviewerSearch currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public List<PreInterviewerSearch> getInterviewSearch() {
        return interviewSearch;
    }

    public void setInterviewSearch(List<PreInterviewerSearch> interviewSearch) {
        this.interviewSearch = interviewSearch;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public PreInteviewTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(PreInteviewTable currentItem) {
        this.currentItem = currentItem;
    }

    public List<PreInteviewTable> getInstrReg() {
        return instrReg;
    }

    public void setInstrReg(List<PreInteviewTable> instrReg) {
        this.instrReg = instrReg;
    }

    public String getAvgtime() {
        return avgtime;
    }

    public void setAvgtime(String avgtime) {
        this.avgtime = avgtime;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDesignationFrst() {
        return designationFrst;
    }

    public void setDesignationFrst(String designationFrst) {
        this.designationFrst = designationFrst;
    }

    public String getDesignationSecnd() {
        return designationSecnd;
    }

    public void setDesignationSecnd(String designationSecnd) {
        this.designationSecnd = designationSecnd;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public List<SelectItem> getFareList() {
        return fareList;
    }

    public void setFareList(List<SelectItem> fareList) {
        this.fareList = fareList;
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

    public String getInterviewNameFrst() {
        return interviewNameFrst;
    }

    public void setInterviewNameFrst(String interviewNameFrst) {
        this.interviewNameFrst = interviewNameFrst;
    }

    public String getInterviewNameSecnd() {
        return interviewNameSecnd;
    }

    public void setInterviewNameSecnd(String interviewNameSecnd) {
        this.interviewNameSecnd = interviewNameSecnd;
    }

    public String getLunchTime() {
        return lunchTime;
    }

    public void setLunchTime(String lunchTime) {
        this.lunchTime = lunchTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<SelectItem> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<SelectItem> positionList) {
        this.positionList = positionList;
    }

    public List<SelectItem> getRecruitmentTypeList() {
        return recruitmentTypeList;
    }

    public void setRecruitmentTypeList(List<SelectItem> recruitmentTypeList) {
        this.recruitmentTypeList = recruitmentTypeList;
    }

    public String getRecruitmenttype() {
        return recruitmenttype;
    }

    public void setRecruitmenttype(String recruitmenttype) {
        this.recruitmenttype = recruitmenttype;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public List<SelectItem> getSelectItemList() {
        return selectItemList;
    }

    public void setSelectItemList(List<SelectItem> selectItemList) {
        this.selectItemList = selectItemList;
    }

    public String[] getSelectItemResultList() {
        return selectItemResultList;
    }

    public void setSelectItemResultList(String[] selectItemResultList) {
        this.selectItemResultList = selectItemResultList;
    }

    public PreInterviewActionPlan() {
        try {
            operationList = new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0", "---Select---"));
            operationList.add(new SelectItem("1", "Add"));
            operationList.add(new SelectItem("2", "Edit"));
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            instrReg = new ArrayList<PreInteviewTable>();
            setTodayDate(sdf.format(date));
            this.setMessage("");
            getTableDetails();
            dropDownPositionList();
            dropDownRecruitmentTypeList();
            dropDownFareList();
            interviewerDetails();
            mode = "save";
            setStartingTime("00:00");
            setLunchTime("00:00");
        } catch (Exception e) {
            logger.error("Exception occured while executing method PreInterviewActionPlan()", e);
            logger.error("PreInterviewActionPlan()", e);
        }
    }

    public void dropDownPositionList() {
        try {
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            List<HrMstStructTO> structMasterTOs = interviewActionPlanDelegate.getPositionList(Integer.parseInt(compCode), "DES%");
            positionList = new ArrayList<SelectItem>();
            positionList.add(new SelectItem("0", "--Select--"));
            for (HrMstStructTO structMasterTO : structMasterTOs) {
                positionList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
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

    public void dropDownRecruitmentTypeList() {
        try {
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            List<HrMstStructTO> structMasterTos = interviewActionPlanDelegate.getPositionList(Integer.parseInt(compCode), "REC%");
            recruitmentTypeList = new ArrayList<SelectItem>();
            recruitmentTypeList.add(new SelectItem("0", "--Select--"));
            for (HrMstStructTO structMasterTO : structMasterTos) {
                recruitmentTypeList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                        structMasterTO.getDescription()));
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

    public void dropDownFareList() {
        try {
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            List<HrMstStructTO> structMasterTOFair = interviewActionPlanDelegate.getPositionList(Integer.parseInt(compCode), "FAR%");
            fareList = new ArrayList<SelectItem>();
            fareList.add(new SelectItem("0", "--Select--"));
            for (HrMstStructTO structMasterTO : structMasterTOFair) {
                fareList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                        structMasterTO.getDescription()));
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method dropDownFareList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method dropDownFareList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method dropDownFareList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getTableDetails() {
        instrReg = new ArrayList<PreInteviewTable>();
        selectItemList = new ArrayList<SelectItem>();
        try {
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            List<HrDatabankTO> hrDatabankTOs = interviewActionPlanDelegate.getIntTableDetails(Integer.parseInt(compCode));
            for (HrDatabankTO hrDatabankTO : hrDatabankTOs) {
                PreInteviewTable rd = new PreInteviewTable();
                String id = hrDatabankTO.getHrDatabankPKTO().getCandId() + "/" + hrDatabankTO.getHrDatabankPKTO().getAdvtCode() + "/" + hrDatabankTO.getHrDatabankPKTO().getJobCode();
                String value = hrDatabankTO.getCandFirName() + " " + hrDatabankTO.getCandMidName() + " " + hrDatabankTO.getCandLastName() + " ,                 Exp-" + hrDatabankTO.getTotExpr();
                rd.setCandId(hrDatabankTO.getHrDatabankPKTO().getCandId());
                selectItemList.add(new SelectItem(id, value));

                rd.setAdvtCode(hrDatabankTO.getHrDatabankPKTO().getAdvtCode());
                rd.setJobCode(hrDatabankTO.getHrDatabankPKTO().getJobCode());
                rd.setFrstName(hrDatabankTO.getCandFirName());
                rd.setMidName(hrDatabankTO.getCandMidName());
                rd.setLastName(hrDatabankTO.getCandLastName());
                rd.setTotalExp(hrDatabankTO.getTotExpr());
                rd.setSex(hrDatabankTO.getSex());
                instrReg.add(rd);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getTableDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("First Name"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (PreInteviewTable item : instrReg) {
            if (item.getFrstName().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void getAdvertNo() {
        try {
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            String advtCodeGenerate = interviewActionPlanDelegate.getAdvtCode(Integer.parseInt(compCode));
            this.setInterviewCode(advtCodeGenerate);
            System.out.println(interViewCode);
        } catch (WebException e) {
            logger.error("Exception occured while executing method getAdvertNo()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getAdvertNo()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvertNo()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fetchCurrentRow1(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Employee Name"));
        currentRow1 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (PreInterviewerSearch item : interviewSearch) {
            if (item.getEmpName().equals(accNo)) {
                currentItem1 = item;
                break;
            }
        }
    }

    public void select() {
        if (flag.equalsIgnoreCase("true")) {
            selectFrst();
        } else {
            if (flag1.equalsIgnoreCase("true")) {
                selectSecond();
            }
        }
    }

    public void selectFrst() {
        setInterviewNameFrst(currentItem1.getEmpName());
        setDesignationFrst(currentItem1.getDescription());
        empIdInterviewer1 = currentItem1.getEmpId();
        desgCode1 = currentItem1.getDesgCode();
        flagForFirstInterviewerName = true;
    }

    public void selectSecond() {
        setInterviewNameSecnd(currentItem1.getEmpName());
        setDesignationSecnd(currentItem1.getDescription());
        empIdInterviewer2 = currentItem1.getEmpId();
        desgCode2 = currentItem1.getDesgCode();
        flagForSecondInterviewerName = true;
    }

    public void closePanel() {
        flag = "true";
        flag1 = "false";
        setMessage1("");
        setInterviwerName("");
        interviewSearch = new ArrayList<PreInterviewerSearch>();
    }

    public void closePanel1() {
        flag1 = "true";
        flag = "false";
        setMessage1("");
        setInterviwerName("");
        interviewSearch = new ArrayList<PreInterviewerSearch>();
    }

    public void interviewerDetails() {
        setMessage1("");
        interviewSearch = new ArrayList<PreInterviewerSearch>();
        try {
            List resultLt = new ArrayList();
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            resultLt = interviewActionPlanDelegate.preIntInterviewersName(Integer.parseInt(compCode), interviwerName);
            if (!resultLt.isEmpty()) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    PreInterviewerSearch rd = new PreInterviewerSearch();
                    setNameSearch(result[0].toString());
                    rd.setEmpId(result[0].toString());
                    rd.setEmpName(result[1].toString());
                    setDesigsearch(result[2].toString());
                    rd.setDesgCode(result[2].toString());
                    rd.setDescription(result[3].toString());
                    interviewSearch.add(rd);
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

    public void fetchCurrentRow2(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Interview Code"));
        currentRow2 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (PreInterviewCodeTable item : inteviewDetail) {
            if (item.getIntCode().equals(accNo)) {
                currentItem2 = item;
                break;
            }
        }
    }

    public void closePanel2() {
        setMessage("");
        if (operation.equalsIgnoreCase("0")) {
            setMessage("Please select an operation!");
            return;
        }
        if (operation.equalsIgnoreCase("1")) {
            mode = "save";
            disableUpdate = false;
        } else {
            mode = "update";
            disableUpdate = true;
        }
        setMessage1("");
        setInterviwerName("");
        inteviewDetail = new ArrayList<PreInterviewCodeTable>();

    }

    public void interviewDetailUpdate() {
        setMessage1("");
        inteviewDetail = new ArrayList<PreInterviewCodeTable>();
        try {
            List resultLt = new ArrayList();
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            resultLt = interviewActionPlanDelegate.preIntInterviewersUdateViewDetail(Integer.parseInt(compCode), interviewCode);
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    PreInterviewCodeTable rd = new PreInterviewCodeTable();
                    Date test = (Date) result[1];
                    rd.setIntCode(result[0].toString());
                    rd.setIntDt(sdf.format(test));
                    rd.setValue1(result[0].toString());
                    rd.setValue2(sdf.format(test));
                    rd.setValue3(result[2].toString());
                    rd.setValue4(result[3].toString().substring(11, 16));
                    rd.setValue5(result[4].toString());
                    rd.setValue6(result[5].toString().substring(11, 16));
                    rd.setValue7(result[7].toString());
                    rd.setEmpCode(result[8].toString());
                    rd.setValue8(result[9].toString());
                    rd.setEmpCode2(result[10].toString());
                    rd.setValue9(result[11].toString());
                    rd.setEmp1Desg(result[12].toString());
                    rd.setValue10(result[13].toString());
                    rd.setEmp2desg(result[14].toString());
                    rd.setValue11(result[15].toString());
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

    public void selectInterviewDetail() throws ParseException {
        this.setInterviewCode(currentItem2.getValue1());
        this.setInterviewDt(sdf.parse(currentItem2.getValue2()));
        //this.setInterviewDt(value2);
        setPosition(currentItem2.getValue3());
        setStartingTime(currentItem2.getValue4());
        setAvgtime(currentItem2.getValue5());
        setLunchTime(currentItem2.getValue6());
        setVenue(currentItem2.getValue7());
        setInterviewNameFrst(currentItem2.getValue8());
        setInterviewNameSecnd(currentItem2.getValue9());
        setDesignationFrst(currentItem2.getValue10());
        setDesignationSecnd(currentItem2.getValue11());
        interviewerDetailsBlur();
    }

    public void refresh() {
        setMessage("");
        instrReg = new ArrayList<PreInteviewTable>();
        inteviewDetail = new ArrayList<PreInterviewCodeTable>();
        interviewSearch = new ArrayList<PreInterviewerSearch>();
        setStartingTime("00:00");
        setLunchTime("00:00");
        setInterviewCode("");
        setVenue("");
        setInterviewDt(null);
        setAvgtime("");
        setPosition("0");
        setRecruitmenttype("0");
        setFare("0");
        setInterviewNameFrst("");
        setInterviewNameSecnd("");
        setDesignationFrst("");
        setDesignationSecnd("");
        flagForFirstInterviewerName = false;
        flagForSecondInterviewerName = false;
        disableUpdate = false;
        setOperation("0");
        if (selectItemResultList != null) {
            selectItemResultList = null;
        }
        getTableDetails();
    }

    public void refreshPage() {
        instrReg = new ArrayList<PreInteviewTable>();
        inteviewDetail = new ArrayList<PreInterviewCodeTable>();
        interviewSearch = new ArrayList<PreInterviewerSearch>();
        setInterviewCode("");
        setVenue("");
        setStartingTime("");
        setInterviewDt(null);
        setAvgtime("");
        setLunchTime("");
        setPosition("0");
        setRecruitmenttype("0");
        setFare("0");
        setInterviewNameFrst("");
        setInterviewNameSecnd("");
        setDesignationFrst("");
        setDesignationSecnd("");
        disableUpdate = false;
        setOperation("0");
        flagForFirstInterviewerName = false;
        flagForSecondInterviewerName = false;
        if (selectItemResultList != null) {
            selectItemResultList = null;
        }
        getTableDetails();
    }

    public void interviewerDetailsBlur() {
        setMessage1("");
        instrReg = new ArrayList<PreInteviewTable>();
        try {
            List resultLt = new ArrayList();
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            resultLt = interviewActionPlanDelegate.preIntInterviewerCodeDetail(Integer.parseInt(compCode), currentItem2.getValue1());
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    PreInteviewTable rd = new PreInteviewTable();
                    rd.setFrstName(result[3].toString());
                    rd.setMidName(result[4].toString());
                    rd.setLastName(result[5].toString());
                    rd.setTotalExp(Double.parseDouble(result[6].toString()));
                    char sex = result[7].toString().charAt(0);
                    rd.setSex(sex);
                    instrReg.add(rd);
                }
            } else {
                setMessage1("No Data Found");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method interviewerDetailsBlur()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method interviewerDetailsBlur()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method interviewerDetailsBlur()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveUpdatePreIntPlan() {
        try {
            if (compCode.equalsIgnoreCase("")) {
                setMessage("please fill the Company code");
                return;
            }
            if (compCode == null) {
                setMessage("please fill the Company code");
                return;
            }

            if (position.equalsIgnoreCase("")) {
                setMessage("please select the position");
                return;
            }
            if (position == null) {
                setMessage("please select the position");
                return;
            }
            if (operation.equalsIgnoreCase("0")) {
                setMessage("Please select an operation!");
                return;
            }
            if (interviewDt == null) {
                setMessage("please fill the interview date");
                return;
            }
            if (interviewDt.equals("")) {
                setMessage("please fill the interview date");
                return;
            }
            if (startingTime.equalsIgnoreCase("")) {
                setMessage("please enter the interview time");
                return;
            }
            if (startingTime == null) {
                setMessage("please enter the interview time");
                return;
            }
            if (validations().equalsIgnoreCase("false")) {
                return;
            }

            if (mode.equalsIgnoreCase("save")) {
                HrInterviewHdTO hrInt = new HrInterviewHdTO();
                HrInterviewHdPKTO hrIntPK = new HrInterviewHdPKTO();
                List<HrDatabankTO> hrDatabankTOs = new ArrayList<HrDatabankTO>();
                List<HrInterviewDtTO> interviewDtTOs = new ArrayList<HrInterviewDtTO>();
                if (selectItemResultList != null && selectItemResultList.length > 0) {
                    getAdvertNo();
                    String stateFlag = "Y";
                    String stateUpflag = "Y";
                    hrIntPK.setCompCode(Integer.parseInt(compCode));
                    hrIntPK.setIntCode(interviewCode);
                    hrInt.setHrInterviewHdPKTO(hrIntPK);
                    hrInt.setDesgCode(position);
                    hrInt.setIntDate(ymd.parse(ymd.format(interviewDt)));
                    Date tempDt = ymd.parse("19000101");
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(tempDt);
                    int hh = Integer.parseInt(startingTime.substring(0, 2));
                    int hhh = hh * 60;
                    int mm = Integer.parseInt(startingTime.substring(3));
                    int minutes = hhh + mm;
                    cal.add(Calendar.MINUTE, minutes);
                    hrInt.setIntTime(cal.getTime());
                    hrInt.setTimePerCand(Integer.parseInt(avgtime));
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(tempDt);
                    int hh1 = Integer.parseInt(lunchTime.substring(0, 2));
                    int hhh1 = hh1 * 60;
                    int mm1 = Integer.parseInt(lunchTime.substring(3));
                    int minutes1 = hhh1 + mm1;
                    cal1.add(Calendar.MINUTE, minutes1);
                    hrInt.setTimeBreak(cal1.getTime());
                    hrInt.setFareCatg(fare);
                    hrInt.setIntVenue(venue);
                    hrInt.setEmpCode(empIdInterviewer1);
                    hrInt.setEmpCode2(empIdInterviewer2);
                    hrInt.setEmp1Desg(desgCode1);
                    hrInt.setEmp2Desg(desgCode2);
                    hrInt.setRecType(recruitmenttype);
                    hrInt.setDefaultCompcode(defaultComp);
                    hrInt.setStatFlag(stateFlag);
                    hrInt.setStatUpFlag(stateUpflag);
                    hrInt.setModDate(getDate());
                    hrInt.setAuthBy(getUserName());
                    hrInt.setEnteredBy(getUserName());
                    for (int i = 0; i < selectItemResultList.length; i++) {
                        HrInterviewDtTO hrInterviewDt = new HrInterviewDtTO();
                        HrInterviewDtPKTO hrInterviewDtPK = new HrInterviewDtPKTO();
                        hrInterviewDtPK.setCompCode(Integer.parseInt(compCode));
                        hrInterviewDtPK.setIntCode(interviewCode);
                        String[] split = selectItemResultList[i].split("/");
                        hrInterviewDtPK.setAdvtCode(split[1]);
                        hrInterviewDtPK.setJobCode(split[2]);
                        hrInterviewDtPK.setCandSrno(split[0]);
                        hrInterviewDt.setHrInterviewDtPKTO(hrInterviewDtPK);
                        hrInterviewDt.setIntResult('Y');
                        Calendar cal11 = Calendar.getInstance();
                        cal11.setTime(tempDt);
                        int hh11 = Integer.parseInt(lunchTime.substring(0, 2));
                        int hhh11 = hh11 * 60;
                        int mm11 = Integer.parseInt(lunchTime.substring(3));
                        int minutes11 = hhh11 + mm11;
                        cal11.add(Calendar.MINUTE, minutes11);
                        hrInterviewDt.setTimeIn(cal11.getTime());
                        hrInterviewDt.setCancel(null);
                        hrInterviewDt.setAdviceCancel('N');
                        hrInterviewDt.setExpectJoin(null);
                        hrInterviewDt.setExtension(null);
                        hrInterviewDt.setIntRemarks("");
                        hrInterviewDt.setDefaultCompCode(defaultComp);
                        hrInterviewDt.setStatFlag(stateFlag);
                        hrInterviewDt.setStatUpFlag(stateUpflag);
                        hrInterviewDt.setModDate(getDate());
                        hrInterviewDt.setAuthBy(getUserName());
                        hrInterviewDt.setEnteredBy(getUserName());
                        interviewDtTOs.add(hrInterviewDt);
                        HrDatabankTO hrDatabank = new HrDatabankTO();
                        HrDatabankPKTO hrDatabankPK = new HrDatabankPKTO();
                        hrDatabank.setCallInt('I');
                        hrDatabankPK.setCompCode(Integer.parseInt(compCode));
                        hrDatabankPK.setCandId(split[0]);
                        hrDatabankPK.setAdvtCode(split[1]);
                        hrDatabankPK.setJobCode(split[2]);
                        hrDatabank.setHrDatabankPKTO(hrDatabankPK);
                        hrDatabankTOs.add(hrDatabank);
                    }
                } else {
                    setMessage("Please pick name of candidates from pick list.");
                    return;
                }
                InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
                String result = interviewActionPlanDelegate.savePreInterviewActionPlan(hrInt, interviewDtTOs, hrDatabankTOs);
                refreshPage();
                setMessage(result);
            } /******************* update *******************/
            else if (mode.equalsIgnoreCase("update")) {
                if (currentItem2.getEmpCode() == null) {
                    setMessage("Please fill first name  by selecting  the row from the table");
                    return;
                }
                if (currentItem2.getEmpCode().equalsIgnoreCase("")) {
                    setMessage("Please fill first name  by selecting  the row from the table");
                    return;
                }
                String stateFlag = "Y";
                String stateUpflag = "Y";
                HrInterviewHdTO hrInterviewHd = new HrInterviewHdTO();
                HrInterviewHdPKTO hrInterviewHdPK = new HrInterviewHdPKTO();
                hrInterviewHdPK.setCompCode(Integer.parseInt(compCode));
                hrInterviewHdPK.setIntCode(interviewCode);
                hrInterviewHd.setHrInterviewHdPKTO(hrInterviewHdPK);
                hrInterviewHd.setDesgCode(position);
                hrInterviewHd.setIntDate(ymd.parse(ymd.format(interviewDt)));
                Date tempDt = ymd.parse("19000101");
                Calendar cal = Calendar.getInstance();
                cal.setTime(tempDt);
                int hh = Integer.parseInt(startingTime.substring(0, 2));
                int hhh = hh * 60;
                int mm = Integer.parseInt(startingTime.substring(3));
                int minutes = hhh + mm;
                cal.set(Calendar.MINUTE, minutes);
                hrInterviewHd.setIntTime(cal.getTime());
                hrInterviewHd.setTimePerCand(Integer.parseInt(avgtime));
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(tempDt);
                int hh1 = Integer.parseInt(lunchTime.substring(0, 2));
                int hhh1 = hh1 * 60;
                int mm1 = Integer.parseInt(lunchTime.substring(3));
                int minutes1 = hhh1 + mm1;
                cal1.set(Calendar.MINUTE, minutes1);
                hrInterviewHd.setTimeBreak(cal1.getTime());
                hrInterviewHd.setFareCatg(fare);
                hrInterviewHd.setIntVenue(venue);
                if (flagForFirstInterviewerName) {
                    hrInterviewHd.setEmpCode(empIdInterviewer1);
                    hrInterviewHd.setEmp1Desg(desgCode1);
                } else {
                    hrInterviewHd.setEmpCode(currentItem2.getEmpCode());
                    hrInterviewHd.setEmp1Desg(currentItem2.getEmp1Desg());
                }
                if (flagForSecondInterviewerName) {
                    hrInterviewHd.setEmpCode2(empIdInterviewer2);
                    hrInterviewHd.setEmp2Desg(desgCode2);
                } else {
                    hrInterviewHd.setEmpCode2(currentItem2.getEmpCode2());
                    hrInterviewHd.setEmp2Desg(currentItem2.getEmp2desg());
                }
                hrInterviewHd.setRecType(recruitmenttype);
                hrInterviewHd.setDefaultCompcode(defaultComp);
                hrInterviewHd.setStatFlag(stateFlag);
                hrInterviewHd.setStatUpFlag(stateUpflag);
                hrInterviewHd.setModDate(getDate());
                hrInterviewHd.setAuthBy(getUserName());
                hrInterviewHd.setEnteredBy(getUserName());
                InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
                String result = interviewActionPlanDelegate.updatePreInterviewActionPlan(hrInterviewHd);
                refreshPage();
                setMessage(result);

            }

        } catch (WebException e) {
            logger.error("Exception occured while executing method saveUpdatePreIntPlan()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method saveUpdatePreIntPlan()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveUpdatePreIntPlan()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public String validations() {
        try {
            String h = startingTime.substring(0, 2);
            String m = startingTime.substring(3);
            if (startingTime.length() < 5) {
                this.setMessage("starting Time can't be less than 5 digit (fill like 10:10 )");
                return "false";
            }
            if (!startingTime.matches("[0-9:]*")) {
                this.setMessage("Please Enter proper value in starting Time like 10:10");
                return "false";
            }

            if (Integer.parseInt(h) > 24) {
                this.setMessage("hour must be less thaan 24 in starting Time");
                return "false";
            }

            if (Integer.parseInt(m) > 60) {
                this.setMessage("minute must be less thaan 60 in starting Time");
                return "false";
            }

            if (lunchTime.length() < 5) {
                this.setMessage("Lunch Break Time can't be less than 5 digit (fill like 10:10 )");
                return "false";
            }
            if (!lunchTime.matches("[0-9:]*")) {
                this.setMessage("Please Enter proper value in Lunch Break Time like 10:10");
                return "false";
            }

            if (!avgtime.matches("[0-9]*")) {
                this.setMessage("Please Enter Numeric Value in Average Interview Time(Min.)");
                return "false";
            }
            String h1 = lunchTime.substring(0, 2);
            String m1 = lunchTime.substring(3);

            if (Integer.parseInt(h1) > 24) {
                this.setMessage("hour must be less thaan 24 in Lunch Break Time");
                return "false";
            }

            if (Integer.parseInt(m1) > 60) {
                this.setMessage("minute must be less thaan 60 in Lunch Break Time");
                return "false";
            }
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "true";
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
