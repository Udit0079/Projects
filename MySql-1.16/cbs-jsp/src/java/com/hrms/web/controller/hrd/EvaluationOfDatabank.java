/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.EvaluationOfDatabankTable;
import com.cbs.servlets.Init;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrDatabankPKTO;
import com.hrms.common.to.HrDatabankTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.DatabankDelegate;
import com.hrms.web.delegate.InterviewActionPlanDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class EvaluationOfDatabank extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(EvaluationOfDatabank.class);
    private String message;
    private String message1;
    private String compCode = "0";
    private String post;
    private String organisationVariable;
    private String candidateId;
    private String advtCode;
    private String jobcode;
    private String organisationCode;
    private String flag;
    private String organisationSearch;
    private String mode = "";
    private boolean saveUpdate;
    private boolean flagDisable;
    private boolean addDisable;
    private boolean editDisable;
    private List<SelectItem> postList;
    private List<SelectItem> organisationList;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
     private String operation;
    private List<SelectItem> operationList;
    
    public boolean isAddDisable() {
        return addDisable;
    }

    public void setAddDisable(boolean addDisable) {
        this.addDisable = addDisable;
    }

    public boolean isEditDisable() {
        return editDisable;
    }

    public void setEditDisable(boolean editDisable) {
        this.editDisable = editDisable;
    }

    public boolean isFlagDisable() {
        return flagDisable;
    }

    public void setFlagDisable(boolean flagDisable) {
        this.flagDisable = flagDisable;
    }

    public boolean isSaveUpdate() {
        return saveUpdate;
    }

    public void setSaveUpdate(boolean saveUpdate) {
        this.saveUpdate = saveUpdate;
    }

    public String getOrganisationCode() {
        return organisationCode;
    }

    public void setOrganisationCode(String organisationCode) {
        this.organisationCode = organisationCode;
    }

    public String getOrganisationVariable() {
        return organisationVariable;
    }

    public void setOrganisationVariable(String organisationVariable) {
        this.organisationVariable = organisationVariable;
    }
    private List<EvaluationOfDatabankTable> evaluationDataList;
    private int currentRow;
    private EvaluationOfDatabankTable currentItem = new EvaluationOfDatabankTable();

    public String getAdvtCode() {
        return advtCode;
    }

    public void setAdvtCode(String advtCode) {
        this.advtCode = advtCode;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getJobcode() {
        return jobcode;
    }

    public void setJobcode(String jobcode) {
        this.jobcode = jobcode;
    }

    public String getOrganisationSearch() {
        return organisationSearch;
    }

    public void setOrganisationSearch(String organisationSearch) {
        this.organisationSearch = organisationSearch;
    }

    public EvaluationOfDatabankTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(EvaluationOfDatabankTable currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<EvaluationOfDatabankTable> getEvaluationDataList() {
        return evaluationDataList;
    }

    public void setEvaluationDataList(List<EvaluationOfDatabankTable> evaluationDataList) {
        this.evaluationDataList = evaluationDataList;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public List<SelectItem> getOrganisationList() {
        return organisationList;
    }

    public void setOrganisationList(List<SelectItem> organisationList) {
        this.organisationList = organisationList;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
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
    

    public EvaluationOfDatabank() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            evaluationDataList = new ArrayList<EvaluationOfDatabankTable>();
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","---Select---"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","Edit"));
            setTodayDate(sdf.format(date));
            this.setMessage("");
            this.setMessage1("");
            getEvaluationOfDatabankDDList(compCode, "DES%");
            getEvaluationOfDatabankDDList(compCode, "ORG%");
            setSaveUpdate(true);
            setFlagDisable(false);
            setEditDisable(false);
            setAddDisable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getEvaluationOfDatabankDDList(String compCode, String description) {
        try {
            InterviewActionPlanDelegate interviewActionPlanDelegate = new InterviewActionPlanDelegate();
            List<HrMstStructTO> structMasterTOs = interviewActionPlanDelegate.getInitialData(Integer.parseInt(compCode), description);
            if (description.equalsIgnoreCase("DES%")) {
                postList = new ArrayList<SelectItem>();
                postList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    postList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
            if (description.equalsIgnoreCase("ORG%")) {
                organisationList = new ArrayList<SelectItem>();
                organisationList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    organisationList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getEvaluationOfDatabankDDList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getEvaluationOfDatabankDDList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEvaluationOfDatabankDDList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("First Name"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (EvaluationOfDatabankTable item : evaluationDataList) {
            if (item.getFirstNameTab().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void evaluationOfDatabankDetailTable() {
        setMessage("");
        evaluationDataList = new ArrayList<EvaluationOfDatabankTable>();
        try {

            List resultLt = new ArrayList();

            DatabankDelegate evaluationOfDatabankDelegate = new DatabankDelegate();
            resultLt = evaluationOfDatabankDelegate.addListEvaluationOfDatabankDelegation(Integer.parseInt(compCode), organisationVariable, post);

            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    EvaluationOfDatabankTable rd = new EvaluationOfDatabankTable();
                    rd.setCandidateIdTab(result[0].toString());
                    rd.setAdvtCodeTab(result[1].toString());
                    rd.setJobCodeTab(result[2].toString());
                    rd.setFirstNameTab(result[3].toString());
                    rd.setMidNameTab(result[4].toString());
                    rd.setLastNameTab(result[5].toString());
                    rd.setExperienceTab(result[6].toString());
                    rd.setSpecialCodeTab(result[7].toString());
                    rd.setDescryptionTab(result[8].toString());
                    rd.setCurrentSalTab(result[9].toString());
                    rd.setExpectedSalTab(result[10].toString());
                    evaluationDataList.add(rd);
                    setEditDisable(true);
                }
            } else {
                setMessage("No record exist.");
            }

        } catch (WebException e) {
            logger.error("Exception occured while executing method evaluationOfDatabankDetailTable()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method evaluationOfDatabankDetailTable()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method evaluationOfDatabankDetailTable()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectEvaluationOfDatabankDetail() throws ParseException {

        this.setAdvtCode(currentItem.getAdvtCodeTab());
        this.setCandidateId(currentItem.getCandidateIdTab());
        this.setJobcode(currentItem.getJobCodeTab());
        //  this.setOrganisationVariable(currentItem.getOrgTypeTab());
    }

    public void fetchCurrentRowEdit(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("First Name"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (EvaluationOfDatabankTable item : evaluationDataList) {
            if (item.getFirstNameTab().equals(accNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void editEvaluationOfDatabankDetailTable() {
        setMessage1("");

        evaluationDataList = new ArrayList<EvaluationOfDatabankTable>();
        try {

            List resultLt = new ArrayList();
            DatabankDelegate evaluationOfDatabankDelegate = new DatabankDelegate();
            resultLt = evaluationOfDatabankDelegate.editEvaluationOfDatabankDelegation(Integer.parseInt(compCode), "ORG");
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    EvaluationOfDatabankTable rd = new EvaluationOfDatabankTable();
                    rd.setOrganisationCodeTab(result[0].toString());
                    rd.setOrgTypeTab(result[1].toString());
                    rd.setDesignationCodeTab(result[2].toString());
                    rd.setDesignationTab(result[3].toString());
                    evaluationDataList.add(rd);
                }
            } else {
                setMessage("No record exist.");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method editEvaluationOfDatabankDetailTable()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method editEvaluationOfDatabankDetailTable()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method editEvaluationOfDatabankDetailTable()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void selectEvaluationOfDatabankEdit() throws ParseException {

        this.setOrganisationVariable(currentItem.getOrganisationCodeTab());
        this.setPost(currentItem.getDesignationCodeTab());
        setFlagDisable(true);
        setSaveUpdate(false);
    }

    public void saveEvaluationOfDatabank() {
        try {

            HrDatabankTO hrDatabankTO = new HrDatabankTO();
            HrDatabankPKTO hrDatabankPKTO = new HrDatabankPKTO();
            hrDatabankTO.setHrDatabankPKTO(hrDatabankPKTO);
            hrDatabankPKTO.setCompCode(Integer.parseInt(compCode));
            hrDatabankPKTO.setAdvtCode(currentItem.getAdvtCodeTab());
            hrDatabankPKTO.setCandId(currentItem.getCandidateIdTab());
            hrDatabankPKTO.setJobCode(currentItem.getJobCodeTab());
            hrDatabankTO.setOrgType(currentItem.getOrganisationCodeTab());
            hrDatabankTO.setPostApplied1(currentItem.getDesignationCodeTab());

            hrDatabankTO.setPostApplied1(post);
            hrDatabankTO.setOrgType(organisationVariable);

            refreshEvaluation();

            if (mode.equalsIgnoreCase("save")) {
                hrDatabankTO.setEvalFlag('Y');
                DatabankDelegate evaluationOfDatabankDelegate = new DatabankDelegate();
                String queryresult = evaluationOfDatabankDelegate.saveEvaluationOfDatabankDelegation(hrDatabankTO);
                setMessage(queryresult);
            } else if (mode.equalsIgnoreCase("update")) {
                hrDatabankTO.setEvalFlag('N');
                DatabankDelegate evaluationOfDatabankDelegate = new DatabankDelegate();
                String queryresult = evaluationOfDatabankDelegate.updateEvaluationOfDatabankDelegation(hrDatabankTO);
                setMessage(queryresult);
            }
            evaluationDataList = new ArrayList<EvaluationOfDatabankTable>();

        } catch (WebException e) {
            logger.error("Exception occured while executing method getFinancialYear()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getFinancialYear()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getFinancialYear()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void saveMode() {
        setMessage1("");
        evaluationDataList = new ArrayList<EvaluationOfDatabankTable>();
        mode = "Save";
        setSaveUpdate(false);
    }

    public void updateMode() {
        setMessage1("");
        evaluationDataList = new ArrayList<EvaluationOfDatabankTable>();
        mode = "update";
        setAddDisable(true);
    }

    public void refreshEvaluation() {

        this.setPost("0");
        this.setOrganisationVariable("0");
        setMessage1("");
        evaluationDataList = new ArrayList<EvaluationOfDatabankTable>();
        setMessage("");
        setSaveUpdate(true);
        setFlagDisable(false);
        setEditDisable(false);
        setOperation("0");
    }
     public void setOperationOnBlur()
    {
        setMessage("");
        if(operation.equalsIgnoreCase("0"))
        {
            setMessage("Please select an operation !");
            return;
        } else if (operation.equalsIgnoreCase("1")) {
            addButton();
            mode = "Save";
        } else if (operation.equalsIgnoreCase("2")) {
            mode = "update";
        }
    }

    public void addButton() {
        setFlagDisable(false);
        setEditDisable(true);
        evaluationDataList = new ArrayList<EvaluationOfDatabankTable>();
        this.setPost("0");
        this.setOrganisationVariable("0");
    }

    public String btnExit() {
        try {
            refreshEvaluation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "case1";
    }
}
