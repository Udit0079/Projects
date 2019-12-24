package com.hrms.web.controller.masters;

import com.cbs.servlets.Init;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrMstCompLoanPKTO;
import com.hrms.common.to.HrMstCompLoanTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.controller.Validator;
import com.hrms.web.delegate.DefinitionsDelegate;
import com.hrms.web.exception.WebException;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class LoanBudgetMaster {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(BusMaster.class);
    private int defaultComp,
            compCode;
    private String statUpFlag,
            todayDate,
            userName,
            message,
            calFromDate,
            calToDate,
            loanAmountString,
            mode,
            enteredBy,
            authBy,
            loanAvailableString,
            principleCollectionString,
            statFlag;
    private double loanAmount,
            loanAvailable,
            principleCollection;
    private Calendar cal;
    private HttpServletRequest request;
    private DefinitionsDelegate definitionsDelegate;
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private NumberFormat formatter = new DecimalFormat("#0.00");
    private Validator validator;
    private boolean disableLoanBudget,
            disableAddButton,
            disableSaveButton,
            disableEditButton,
            disableDeleteButton;
    private String operation;
    private List<SelectItem> operationList;
    private List<HrMstCompLoanTO> hrMstCompLoanTOs1;

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

    
    public LoanBudgetMaster() {
        try {
            definitionsDelegate = new DefinitionsDelegate();
            operationList=new ArrayList<SelectItem>();
            operationList.add(new SelectItem("0","-Select-"));
            operationList.add(new SelectItem("1","Add"));
            operationList.add(new SelectItem("2","View"));
            Date date = new Date();
            cal = new GregorianCalendar();
            cal.setTime(date);
            try {
                request = getRequest();
                userName = request.getRemoteUser();
                InetAddress iAddress = InetAddress.getByName(WebUtil.getClientIP(request));
                compCode = Integer.parseInt(Init.IP2BR.getBranch(iAddress));
            } catch (Exception e) {
            }
            companyMasterTOs = webUtil.getCompanyMasterTO(compCode);
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            todayDate = dmyFormat.format(date);
            authBy = userName;
            enteredBy = userName;
            statFlag = "Y";
            statUpFlag = "Y";
            onLoadMode();
            validator = new Validator();
            calFromDate = (WebUtil.getFinancialYear(compCode)).get(0);
            calToDate = (WebUtil.getFinancialYear(compCode)).get(1);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method LoanBudgetMaster()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method LoanBudgetMaster()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void addAction() {
        System.out.println("In add Action");
        try {
            getLoanBudgetMasterData();
            if (!hrMstCompLoanTOs1.isEmpty()) {
                mode = "update";
                setDisableLoanBudget(true);
                setDisableSaveButton(true);
                setDisableDeleteButton(true);
                setMessage("Loan Budget Is Not Empty !!");
            } else {
                mode = "save";
                setMessage("Please Fill Details !!");
                setDisableLoanBudget(false);
                setDisableSaveButton(false);
                setDisableDeleteButton(false);
            }

        } catch (Exception e) {
            logger.error("Exception occured while executing method addAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public boolean validate() {
        if (loanAmountString.equalsIgnoreCase("")) {
            setMessage("Loan Budget Cannot Be Blank !!");
            return false;
        }
        if (!validator.validateDoublePositive(loanAmountString)) {
            setMessage("Loan Budget Can Have Numeric Value Only !!");
            return false;
        }

        return true;
    }

    public void saveAction() {
      
        try {
            if (validate()) {
                loanAmount = Math.round(Double.parseDouble(loanAmountString));
                loanAvailable = loanAmount;
                principleCollection = 0;
                setLoanAvailableString(formatter.format(loanAmount));
                setLoanAmountString(formatter.format(loanAmount));
                setPrincipleCollectionString(formatter.format(principleCollection));
                String result = "";
                if (!hrMstCompLoanTOs1.isEmpty()) {
                    result = definitionsDelegate.saveLoanBudgetMasterDetail(getHrMstCompLoanTO(), "update");
                } else {
                    result = definitionsDelegate.saveLoanBudgetMasterDetail(getHrMstCompLoanTO(), "save");
                }
                cancelAction();
                setMessage(result);
                 setOperation("0");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }

    }

    public void editAction() {
        try {
            getLoanBudgetMasterData();
            if (!hrMstCompLoanTOs1.isEmpty()) {
                if (hrMstCompLoanTOs1.get(0).getLoanBudget() != 0.0) {
                    setDisableLoanBudget(false);
                    setDisableAddButton(true);
                    setDisableSaveButton(false);
                    setDisableDeleteButton(false);
                    setDisableEditButton(true);
                } else {
                    
                    setDisableLoanBudget(true);
                    setDisableAddButton(false);
                    setDisableSaveButton(true);
                    setDisableDeleteButton(true);
                    setDisableEditButton(true);
                    setMessage("No Budget Data !!");
                    setOperation("0");
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method editAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void deleteAction() {
        try {
            String result = definitionsDelegate.saveLoanBudgetMasterDetail(getHrMstCompLoanTO(), "delete");
            cancelAction();
            onLoadMode();
            setMessage(result);
            setOperation("0");
        } catch (WebException e) {
            logger.error("Exception occured while executing method deleteAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }

    }

    public void getLoanBudgetMasterData() {
        try {
            hrMstCompLoanTOs1 = definitionsDelegate.getHrMstCompLoanData(compCode);
            if (!hrMstCompLoanTOs1.isEmpty()) {
                setLoanAmountString("" + formatter.format(hrMstCompLoanTOs1.get(0).getLoanBudget()));
                setLoanAvailableString("" + formatter.format(hrMstCompLoanTOs1.get(0).getLoanAvailable()));
                setPrincipleCollectionString("" + formatter.format(hrMstCompLoanTOs1.get(0).getPrincipleCollection()));
            } else {
                setMessage("No Loan Budget Data Available !!");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getLoanBudgetMasterData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanBudgetMasterData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        mode = "update";
    }

    public void cancelAction() {
        setMessage("");
        setLoanAmountString("");
        setLoanAvailableString("");
        setPrincipleCollectionString("");
        onLoadMode();
        setOperation("0");
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

    public void onLoadMode() {
        setMode("save");
        setMessage("");
        setOperation("0");  
        setDisableSaveButton(true);
        setDisableDeleteButton(true);
        setDisableLoanBudget(true);
    }
     public void setOperation()
    {
        if(operation.equalsIgnoreCase("0"))
        {
            setMessage("Please select an operation !");
            return;
        }
        else if(operation.equalsIgnoreCase("1"))
        {
            addAction();
        }  
        else if(operation.equalsIgnoreCase("2"))
        {
            editAction();
        }
    }
     
     private HrMstCompLoanTO getHrMstCompLoanTO() {
        HrMstCompLoanTO hrMstCompLoanTO = new HrMstCompLoanTO();
        HrMstCompLoanPKTO hrMstCompLoanPKTO = new HrMstCompLoanPKTO();
        try {
            hrMstCompLoanPKTO.setCompCode(compCode);
            hrMstCompLoanPKTO.setFromDate(dmyFormat.parse(calFromDate));
            hrMstCompLoanPKTO.setToDate(dmyFormat.parse(calToDate));
            hrMstCompLoanTO.setHrMstCompLoanPKTO(hrMstCompLoanPKTO);
            hrMstCompLoanTO.setAuthBy(authBy);
            hrMstCompLoanTO.setDefaultComp(defaultComp);
            hrMstCompLoanTO.setEnteredBy(enteredBy);
            hrMstCompLoanTO.setLoanAvailable(loanAvailable);
            hrMstCompLoanTO.setLoanBudget(loanAmount);
            hrMstCompLoanTO.setPrincipleCollection(principleCollection);
            hrMstCompLoanTO.setModDate(Date.class.newInstance());
            hrMstCompLoanTO.setStatFlag(statFlag);
            hrMstCompLoanTO.setStatUpFlag(statUpFlag);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrMstCompLoanTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrMstCompLoanTO;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getLoanAvailable() {
        return loanAvailable;
    }

    public void setLoanAvailable(double loanAvailable) {
        this.loanAvailable = loanAvailable;
    }

    public String getLoanAvailableString() {
        return loanAvailableString;
    }

    public void setLoanAvailableString(String loanAvailableString) {
        this.loanAvailableString = loanAvailableString;
    }

    public double getPrincipleCollection() {
        return principleCollection;
    }

    public void setPrincipleCollection(double principleCollection) {
        this.principleCollection = principleCollection;
    }

    public String getPrincipleCollectionString() {
        return principleCollectionString;
    }

    public void setPrincipleCollectionString(String principleCollectionString) {
        this.principleCollectionString = principleCollectionString;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanAmountString() {
        return loanAmountString;
    }

    public void setLoanAmountString(String loanAmountString) {
        this.loanAmountString = loanAmountString;
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

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isDisableAddButton() {
        return disableAddButton;
    }

    public void setDisableAddButton(boolean disableAddButton) {
        this.disableAddButton = disableAddButton;
    }

    public boolean isDisableDeleteButton() {
        return disableDeleteButton;
    }

    public void setDisableDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
    }

    public boolean isDisableEditButton() {
        return disableEditButton;
    }

    public void setDisableEditButton(boolean disableEditButton) {
        this.disableEditButton = disableEditButton;
    }

    public boolean isDisableLoanBudget() {
        return disableLoanBudget;
    }

    public void setDisableLoanBudget(boolean disableLoanBudget) {
        this.disableLoanBudget = disableLoanBudget;
    }

    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }

    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }

    public NumberFormat getFormatter() {
        return formatter;
    }

    public void setFormatter(NumberFormat formatter) {
        this.formatter = formatter;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
    
}
