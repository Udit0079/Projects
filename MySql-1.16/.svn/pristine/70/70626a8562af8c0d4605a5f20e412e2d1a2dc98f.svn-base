package com.hrms.web.controller.personnel;

import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.HrEmpAdvanceHdPKTO;
import com.hrms.common.to.HrEmpAdvanceHdTO;
import com.hrms.common.to.HrEmpLoanHdPKTO;
import com.hrms.common.to.HrEmpLoanHdTO;
import com.hrms.common.to.HrMstStructPKTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsPKTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.AdvanceGrid;
import com.hrms.web.pojo.LoanGrid;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.utils.LocalizationUtil;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class LoanAdvanceAuthorization extends BaseBean{

    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(AdvanceActivity.class);
    private int compCode, defaultComp;
    private String message, authType;
    private boolean disableAuthorizeButton;
    List<AdvanceGrid> advanceTable;
    private AdvanceGrid currentAdvanceItem;
    List<LoanGrid> loanTable;
    private LoanGrid currentLoanItem;
    private List<SelectItem> typeList;
    private PersonnelDelegate personnelDelegate;

    public LoanAdvanceAuthorization() {
        try {
            personnelDelegate = new PersonnelDelegate();
            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("--Select--"));
            typeList.add(new SelectItem("Loan"));
            typeList.add(new SelectItem("Advance"));
            compCode = Integer.parseInt(getOrgnBrCode());
            companyMasterTOs = webUtil.getCompanyMasterTO(compCode);
            if (!companyMasterTOs.isEmpty()) {
                defaultComp = companyMasterTOs.get(0).getDefCompCode();
            }
            cancelAction();
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method LoanAdvanceAuthorization()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method LoanAdvanceAuthorization()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void onBlurAuthType() {
        try {
            setMessage("");
            if (authType.equalsIgnoreCase("Loan")) {
                getLoanTableData();
            } else if (authType.equalsIgnoreCase("Advance")) {
                getAdvanceTableData();
            } else {
                setMessage("Please Select Authorization Type !!");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method onBlurAuthType()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getAdvanceTableData() {
        try {
            advanceTable = new ArrayList<AdvanceGrid>();
            List advanceTableData = personnelDelegate.getAdvanceTableDataForAuthorization(compCode);
            if (!advanceTableData.isEmpty()) {
                for (int i = 0; i < advanceTableData.size(); i++) {
                    Object[] ob = (Object[]) advanceTableData.get(i);
                    AdvanceGrid currentAdvanceRow = new AdvanceGrid();
                    long empAdvNo = Long.parseLong(ob[0].toString());
                    long EmpCode = Long.parseLong(ob[1].toString());
                    String type = ob[2].toString();
                    String sanDate = dmyFormat.format(ob[3]);
                    String empIdd = ob[4].toString();
                    String empIName = ob[5].toString();
                    String desc = ob[6].toString();
                    double amount = Double.parseDouble(ob[7].toString());
                    currentAdvanceRow.setSno(i + 1);
                    currentAdvanceRow.setEmpId(empIdd);
                    currentAdvanceRow.setType(type);
                    currentAdvanceRow.setEmpCode(EmpCode);
                    currentAdvanceRow.setEmpAdvNo(empAdvNo);
                    currentAdvanceRow.setEmpName(empIName);
                    currentAdvanceRow.setAdvance(desc);
                    currentAdvanceRow.setSanctionDate(sanDate);
                    currentAdvanceRow.setDescription(desc);
                    currentAdvanceRow.setSanctionAmount(amount);
                    advanceTable.add(currentAdvanceRow);
                }
            } else {
                setMessage("No Authorization Data !!");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getAdvanceTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvanceTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void getLoanTableData() {
        try {
            loanTable = new ArrayList<LoanGrid>();
            List loanTableData = personnelDelegate.getLoanTableDataForAuthorization(compCode);
            if (!loanTableData.isEmpty()) {
                for (int i = 0; i < loanTableData.size(); i++) {
                    Object[] ob = (Object[]) loanTableData.get(i);
                    LoanGrid currentLoanRow = new LoanGrid();
                    long EmpLoanNo = Long.parseLong(ob[0].toString());
                    long EmpCode = Long.parseLong(ob[1].toString());
                    String type = ob[2].toString();
                    String sanDate = dmyFormat.format(ob[3]);
                    String empIdd = ob[4].toString();
                    String empIName = ob[5].toString();
                    String desc = ob[6].toString();
                    double amount = Double.parseDouble(ob[7].toString());
                    String instPlan = ob[8].toString();
                    currentLoanRow.setSno(i + 1);
                    currentLoanRow.setEmpLoanNo(EmpLoanNo);
                    currentLoanRow.setEmpId(empIdd);
                    currentLoanRow.setEmpName(empIName);
                    currentLoanRow.setSanctionDate(sanDate);
                    currentLoanRow.setSanctionAmount(amount);
                    currentLoanRow.setEmpCode(EmpCode);
                    currentLoanRow.setLoanType(type);
                    currentLoanRow.setLoanTypeDescription(desc);
                    currentLoanRow.setInstallmentPlan(instPlan);
                    currentLoanRow.setNoOfInstallment(defaultComp);
                    loanTable.add(currentLoanRow);
                }
            } else {
                setMessage("No Authorization Data !!");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getLoanTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void cancelAction() {
        try {
            setMessage("");
            setAuthType("--Select--");
        } catch (Exception e) {
            logger.error("Exception occured while executing method cancelAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void authorizeBtnAction() {
        try {
            if (authType.equalsIgnoreCase("Loan")) {
                HrEmpLoanHdTO hrEmpLoanHdTO = new HrEmpLoanHdTO();
                HrEmpLoanHdPKTO hrEmpLoanHdPKTO = new HrEmpLoanHdPKTO();
                hrEmpLoanHdPKTO.setCompCode(compCode);
                hrEmpLoanHdPKTO.setEmpLoanNo(currentLoanItem.getEmpLoanNo());
                hrEmpLoanHdTO.setAuthBy(getUserName());
                hrEmpLoanHdTO.setHrMstStructTO(getHrMstStructTO(compCode, currentLoanItem.getLoanType()));
                hrEmpLoanHdTO.setHrPersonnelDetailsTO(getHrPersonnelDetailsTO(compCode, currentLoanItem.getEmpCode()));
                hrEmpLoanHdTO.setHrEmpLoanHdPKTO(hrEmpLoanHdPKTO);
                String result = personnelDelegate.authorizeEmpLoanDetail(hrEmpLoanHdTO);
                setMessage(result);
            }
            if (authType.equalsIgnoreCase("Advance")) {
                HrEmpAdvanceHdTO hrEmpAdvanceHdTO = new HrEmpAdvanceHdTO();
                HrEmpAdvanceHdPKTO hrEmpAdvanceHdPKTO = new HrEmpAdvanceHdPKTO();
                hrEmpAdvanceHdPKTO.setCompCode(compCode);
                hrEmpAdvanceHdPKTO.setEmpAdvNo(currentAdvanceItem.getEmpAdvNo());
                hrEmpAdvanceHdTO.setAuthBy(getUserName());
                hrEmpAdvanceHdTO.setHrMstStructTO(getHrMstStructTO(compCode, currentAdvanceItem.getAdvance()));
                hrEmpAdvanceHdTO.setHrPersonnelDetailsTO(getHrPersonnelDetailsTO(compCode, currentAdvanceItem.getEmpCode()));
                hrEmpAdvanceHdTO.setHrEmpAdvanceHdPKTO(hrEmpAdvanceHdPKTO);
                String result = personnelDelegate.authorizeEmpAdvanceDetail(hrEmpAdvanceHdTO);
                setMessage(result);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method authorizeBtnAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (Exception e) {
            logger.error("Exception occured while executing method authorizeBtnAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public HrMstStructTO getHrMstStructTO(int compCode, String advanceType) {
        HrMstStructTO hrMstStructTO = new HrMstStructTO();
        HrMstStructPKTO hrMstStructPKTO = new HrMstStructPKTO();
        try {
            hrMstStructPKTO.setCompCode(compCode);
            hrMstStructPKTO.setStructCode(advanceType);
            hrMstStructTO.setHrMstStructPKTO(hrMstStructPKTO);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrMstStructTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrMstStructTO;
    }

    public HrPersonnelDetailsTO getHrPersonnelDetailsTO(int compCode, long empCode) {
        HrPersonnelDetailsTO hrPersonnelDetailsTO = new HrPersonnelDetailsTO();
        HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO = new HrPersonnelDetailsPKTO();
        try {
            hrPersonnelDetailsPKTO.setCompCode(compCode);
            hrPersonnelDetailsPKTO.setEmpCode(empCode);
            hrPersonnelDetailsTO.setHrPersonnelDetailsPKTO(hrPersonnelDetailsPKTO);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrPersonnelDetailsTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrPersonnelDetailsTO;
    }

    public String exitBtnAction() {
        try {
            cancelAction();
        } catch (Exception e) {
            logger.error("Exception occured while executing method exitBtnAction()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return "case1";
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDisableAuthorizeButton() {
        return disableAuthorizeButton;
    }

    public void setDisableAuthorizeButton(boolean disableAuthorizeButton) {
        this.disableAuthorizeButton = disableAuthorizeButton;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    public List<AdvanceGrid> getAdvanceTable() {
        return advanceTable;
    }

    public void setAdvanceTable(List<AdvanceGrid> advanceTable) {
        this.advanceTable = advanceTable;
    }

    public AdvanceGrid getCurrentAdvanceItem() {
        return currentAdvanceItem;
    }

    public void setCurrentAdvanceItem(AdvanceGrid currentAdvanceItem) {
        this.currentAdvanceItem = currentAdvanceItem;
    }

    public LoanGrid getCurrentLoanItem() {
        return currentLoanItem;
    }

    public void setCurrentLoanItem(LoanGrid currentLoanItem) {
        this.currentLoanItem = currentLoanItem;
    }

    public List<LoanGrid> getLoanTable() {
        return loanTable;
    }

    public void setLoanTable(List<LoanGrid> loanTable) {
        this.loanTable = loanTable;
    }
}
