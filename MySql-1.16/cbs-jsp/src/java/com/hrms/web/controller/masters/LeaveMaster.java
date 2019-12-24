/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.masters;

import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.LeaveMasterPKTO;
import com.hrms.common.to.LeaveMasterTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.common.to.HrMstStructPKTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.delegate.MasterManagementDelegate;
import com.hrms.web.exception.WebException;
import com.hrms.web.utils.LocalizationUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class LeaveMaster extends BaseBean {

    private static final Logger logger = Logger.getLogger(LeaveMaster.class);
    private String message;
    private List<FinYearDetail> finYearDetailList;
    private int currentRow;
    private FinYearDetail currentItem;
    private String fromDate;
    private String toDate;
    private List<SelectItem> leaveNatureOption;
    private List<SelectItem> encashOption;
    private String leaveNature;
    private int compCode = 2;
    private char encash;
    private String leaveCode;
    private Date applicablDt;
    private String description;
    private String noOfDays;

    public Date getApplicablDt() {
        return applicablDt;
    }

    public void setApplicablDt(Date applicablDt) {
        this.applicablDt = applicablDt;
    }

    public FinYearDetail getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(FinYearDetail currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public char getEncash() {
        return encash;
    }

    public void setEncash(char encash) {
        this.encash = encash;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public String getLeaveNature() {
        return leaveNature;
    }

    public void setLeaveNature(String leaveNature) {
        this.leaveNature = leaveNature;
    }

    public List<SelectItem> getLeaveNatureOption() {
        return leaveNatureOption;
    }

    public void setLeaveNatureOption(List<SelectItem> leaveNatureOption) {
        this.leaveNatureOption = leaveNatureOption;
    }

    public List<SelectItem> getEncashOption() {
        return encashOption;
    }

    public void setEncashOption(List<SelectItem> encashOption) {
        this.encashOption = encashOption;
    }

    public List<FinYearDetail> getFinYearDetailList() {
        return finYearDetailList;
    }

    public void setFinYearDetailList(List<FinYearDetail> finYearDetailList) {
        this.finYearDetailList = finYearDetailList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public LeaveMaster() {
        try {
            encashOption = new ArrayList<SelectItem>();
            encashOption.add(new SelectItem("0", "--Select--"));
            encashOption.add(new SelectItem("Y", "Yes"));
            encashOption.add(new SelectItem("N", "No"));
            getInitalData();
        } catch (Exception e) {
            logger.error("Exception occured while executing method LeaveMaster()", e);
            logger.error("LeaveMaster()", e);
        }
    }

    public void getInitalData() {
        try {
            MasterManagementDelegate masterManagementDelegate = new MasterManagementDelegate();
            compCode = Integer.parseInt(getOrgnBrCode());
            List<HrMstStructTO> structMasterTOs = masterManagementDelegate.getInitialData(compCode);
            leaveNatureOption = new ArrayList<SelectItem>();
            leaveNatureOption.add(new SelectItem("0", "--Select--"));
            for (HrMstStructTO structMasterTO : structMasterTOs) {
                leaveNatureOption.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                        structMasterTO.getDescription()));
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

    public void getFinancialYear() {
        finYearDetailList = new ArrayList<FinYearDetail>();
        try {
            MasterManagementDelegate masterManagementDelegate = new MasterManagementDelegate();
            List<PayrollCalendarTO> payrollCalendarTOs = masterManagementDelegate.getFinancialYear(compCode);

            for (PayrollCalendarTO payrollCalendarTO : payrollCalendarTOs) {
                FinYearDetail finYearDetail = new FinYearDetail();
                finYearDetail.setFromDate(sdf.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateFrom()));
                finYearDetail.setToDate(sdf.format(payrollCalendarTO.getPayrollCalendarPKTO().getDateTo()));
                finYearDetailList.add(finYearDetail);
            }
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

    public void setRowValues() {
        this.setFromDate(currentItem.getFromDate());
        this.setToDate(currentItem.getToDate());
    }
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public void saveLeaveDetail() {
        try {
           LeaveMasterTO leavmstObj = new LeaveMasterTO();
            LeaveMasterPKTO leaveMasterPKTO = new LeaveMasterPKTO();

            leaveMasterPKTO.setCompCode(compCode);
            leaveMasterPKTO.setLeaveCode(this.getLeaveCode());
            leaveMasterPKTO.setDateFrom(this.getFromDate());
            leaveMasterPKTO.setDateTo(this.getToDate());

            leavmstObj.setLeaveMasterPKTO(leaveMasterPKTO);
            leavmstObj.setApplicableDate(sdf.format(this.getApplicablDt()));
            leavmstObj.setDescription(this.getDescription());
            leavmstObj.setEncash(this.getEncash());

            leavmstObj.setModDate(new Date());
            leavmstObj.setNumDays(Integer.parseInt(this.getNoOfDays()));
            leavmstObj.setStatUpFlag("Y");

            leavmstObj.setDefaultComp(1);
            leavmstObj.setStatFlag("N");
            leavmstObj.setAuthBy(this.getUserName());
            leavmstObj.setEnteredBy(this.getUserName());
            leavmstObj.setLeaveNature(this.getLeaveNature());
            
            HrMstStructTO structMasterTO = new HrMstStructTO();
            HrMstStructPKTO structMasterPKTO = new HrMstStructPKTO();
            structMasterPKTO.setCompCode(compCode);
            structMasterPKTO.setStructCode(this.getLeaveNature());
            structMasterTO.setHrMstStructPKTO(structMasterPKTO);
            leavmstObj.setStructMasterTO(structMasterTO);

            MasterManagementDelegate masterManagementDelegate = new MasterManagementDelegate();
            String result = masterManagementDelegate.saveLeaveDetail(leavmstObj);
            setMessage(result);
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

    public void refresh() {
        setFromDate("");
        setToDate("");
        setEncash('0');
        setLeaveNature("0");
        setMessage("");

    }
}
