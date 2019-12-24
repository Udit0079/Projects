/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.payroll;

//import com.cbs.dto.sms.BulkSmsTo;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.pojo.ConsolidatedFinancialSalaryPojo;
import com.cbs.pojo.FinancialSalProjectionPojo;
import com.cbs.pojo.SalaryRegisterPojo;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.adaptor.PayrollObjectAdaptor;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.EmpSalaryAllocationGridTO;
import com.hrms.common.to.HrAttendanceDetailsTO;
import com.hrms.common.to.HrSalaryProcessingDetailPKTO;
import com.hrms.common.to.HrSalaryProcessingDetailTO;
import com.hrms.common.to.HrSalaryProcessingPKTO;
import com.hrms.common.to.HrSalaryProcessingTO;
import com.hrms.common.utils.HrmsUtil;
import com.hrms.constant.CbsConstant;
import com.hrms.dao.HrAttendanceDetailsDAO;
import com.hrms.dao.HrHolidayMasterDAO;
import com.hrms.dao.HrLeaveRegisterDAO;
import com.hrms.dao.HrPersonnelDetailsDAO;
import com.hrms.dao.HrSalaryAllocationDAO;
import com.hrms.dao.HrSalaryProcessingDAO;
import com.hrms.dao.HrSalaryProcessingDtlDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrPersonnelDetails;
import com.hrms.entity.hr.HrPersonnelDetailsPK;
import com.hrms.entity.payroll.HrAttendanceDetails;
import com.hrms.entity.payroll.HrAttendanceDetailsPK;
import com.hrms.entity.payroll.HrSalaryAllocation;
import com.hrms.entity.payroll.HrSalaryAllocationPK;
import com.hrms.entity.payroll.HrSalaryProcessing;
import com.hrms.entity.payroll.HrSalaryProcessingDtl;
//import com.hrms.entity.payroll.HrSalaryProcessingDtlPK;
import com.hrms.entity.payroll.HrSalaryProcessingPK;
import com.hrms.entity.payroll.HrTaxInvestmentCategory;
import com.hrms.entity.payroll.HrTaxSlabMaster;
import com.hrms.to.payroll.LoanDetailTo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr Bisht //modified by Ankit Verma
 */
@Stateless(mappedName = "PayrollTransactionsFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
//@Remote({PayrollTransactionsFacadeRemote.class})
public class PayrollTransactionsFacade implements PayrollTransactionsFacadeRemote {

    private static final Logger logger = Logger.getLogger(PayrollTransactionsFacade.class);
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private PayrollMasterFacadeRemote masterFacadeRemote;
    @EJB
    private InterBranchTxnFacadeRemote ibtFacade;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsFacade;
    @EJB
    private PayrollOtherMgmFacadeRemote otherMgmFacadeRem;
    @EJB
    private SmsManagementFacadeRemote smsRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
  

    @Override
    public String saveAttendanceProcessing(HrAttendanceDetailsTO hrAttendanceDetailsTO,
            String selectionCategory, String categorizationDetails, String mode) throws ApplicationException {
        long begin = System.nanoTime();
        int compCode = hrAttendanceDetailsTO.getDefaultComp(), defaultComp = hrAttendanceDetailsTO.getDefaultComp();
        String selectionCriteria = selectionCategory, selectedValues = categorizationDetails, minWorkingUnit = hrAttendanceDetailsTO.getOverTimeUnit(), authBy = hrAttendanceDetailsTO.getAuthBy(), enteredBy = hrAttendanceDetailsTO.getEnteredBy(), modeFlag = mode, message = null;
        char statFlag = 'Y', statUpFlag = 'Y';
        int holiDays = 0, leave = 0, monthOfYear = 0, workingDays = 0, paidLeave = 0, unPaidLeave = 0;
        long empCode;
        Date varFromDate, processingDateFrom = hrAttendanceDetailsTO.getProcessDateFrom(), processingDateTo = hrAttendanceDetailsTO.getProcessDateTo();
        String month = "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        int count = 0;
        message = "false";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            HrPersonnelDetailsDAO hrPersonnelDao = new HrPersonnelDetailsDAO(em);
            HrAttendanceDetailsDAO hrAttendanceDao = new HrAttendanceDetailsDAO(em);
            HrHolidayMasterDAO hrHolidayDao = new HrHolidayMasterDAO(em);
            HrLeaveRegisterDAO hrLeaveRegisteDao = new HrLeaveRegisterDAO(em);
            HrPersonnelDetails hrPersonnelDetails;
            boolean holidayFlg = false;
            if (modeFlag.equalsIgnoreCase("ADD") || modeFlag.equalsIgnoreCase("EDIT")) {
                if (selectionCriteria.equalsIgnoreCase("EWE")) {
                    hrPersonnelDetails = hrPersonnelDao.findByEmpStatusAndEmpId(compCode, selectedValues, 'Y');
                    if (hrPersonnelDetails.getHrPersonnelDetailsPK() != null) {
                        empCode = hrPersonnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue();
                        varFromDate = processingDateFrom;
                        while (varFromDate.before(processingDateTo) || varFromDate.equals(processingDateTo)) {
                            monthOfYear = Integer.parseInt(formatter.format(varFromDate).substring(3, 5));
                            month = HrmsUtil.getMonthName(monthOfYear - 1);

                            List hList = em.createNativeQuery("SELECT * FROM lstholidays WHERE cast(brncode as unsigned) = '" + compCode + "' "
                                    + "and holidaydate = '" + ymmd.format(varFromDate) + "'").getResultList();
                            if (hList.isEmpty()) {
                                workingDays += 1;
                            } else {
                                holiDays += 1;
                                holidayFlg = true;
                            }

                            List list = em.createNativeQuery("SELECT days_taken,paid FROM hr_leave_register h WHERE comp_Code = '" + compCode + "' "
                                    + "and emp_Code = '" + empCode + "' and h.depart_Recom = 'Y' and '" + ymd.format(varFromDate) + "' "
                                    + "between str_to_date(h.from_Date,'%d/%m/%Y') and str_to_date(h.to_Date,'%d/%m/%Y')").getResultList();
                            if (!list.isEmpty()) {
                                if (holidayFlg != true) {
                                    leave += 1;
                                    for (int z = 0; z < list.size(); z++) {
                                        Object[] obj = (Object[]) list.get(z);
                                        if (obj[1].toString().trim().equalsIgnoreCase("N")) {
                                            unPaidLeave = unPaidLeave + Integer.parseInt(obj[0].toString());
                                        }
                                    }
                                }
                            }
                            Calendar c1 = Calendar.getInstance();
                            c1.setTime(varFromDate);
                            c1.add(Calendar.DATE, 1);
                            varFromDate = c1.getTime();
                            holidayFlg = false;
                        }

                        paidLeave = leave - unPaidLeave;

                        int totalWorkingDays = workingDays - leave;
                        HrAttendanceDetailsPK hrAttendanceDetailsPK = new HrAttendanceDetailsPK();
                        HrAttendanceDetails hrAttendetails = new HrAttendanceDetails();
                        //    String year = formatter.format(varFromDate);
                        String year = formatter.format(processingDateFrom);
                        int attenYear = Integer.parseInt(year.substring(6, 10));
                        hrAttendanceDetailsPK.setAttenMonth(month);
                        hrAttendanceDetailsPK.setAttenYear(attenYear);
                        hrAttendanceDetailsPK.setCompCode(compCode);
                        hrAttendanceDetailsPK.setEmpCode(empCode);
                        hrAttendetails.setPostFlag('Y');
                        hrAttendetails.setHrAttendanceDetailsPK(hrAttendanceDetailsPK);
                        BaseEntity findById = hrAttendanceDao.findById(hrAttendetails, hrAttendetails.getHrAttendanceDetailsPK());
                        if (findById == null) {
                            HrAttendanceDetails hrAttendanceDetailsEntity = new HrAttendanceDetails();
                            HrAttendanceDetailsPK hrAttendanceDetailsPkEntity = new HrAttendanceDetailsPK();
                            hrAttendanceDetailsPkEntity.setCompCode(compCode);
                            hrAttendanceDetailsPkEntity.setEmpCode(empCode);
                            hrAttendanceDetailsPkEntity.setAttenMonth(month);
                            hrAttendanceDetailsPkEntity.setAttenYear(attenYear);
                            hrAttendanceDetailsEntity.setHrAttendanceDetailsPK(hrAttendanceDetailsPkEntity);
                            hrAttendanceDetailsEntity.setProcessDateFrom(processingDateFrom);
                            hrAttendanceDetailsEntity.setProcessDateTo(processingDateTo);
                            hrAttendanceDetailsEntity.setWorkingDays(workingDays);
                            hrAttendanceDetailsEntity.setPresentDays(totalWorkingDays);
                            hrAttendanceDetailsEntity.setAbsentDays(unPaidLeave);
                            hrAttendanceDetailsEntity.setPaidLeave(paidLeave);
                            hrAttendanceDetailsEntity.setOverTimePeriod(0);
                            hrAttendanceDetailsEntity.setOverTimeUnit(hrAttendanceDetailsTO.getOverTimeUnit());
                            hrAttendanceDetailsEntity.setPostFlag('N');
                            hrAttendanceDetailsEntity.setDefaultComp(defaultComp);
                            hrAttendanceDetailsEntity.setStatFlag(String.valueOf(statFlag));
                            hrAttendanceDetailsEntity.setStatUpFlag(String.valueOf(statUpFlag));
                            hrAttendanceDetailsEntity.setModDate(new Date());
                            hrAttendanceDetailsEntity.setAuthBy(authBy);
                            hrAttendanceDetailsEntity.setEnteredBy(enteredBy);
                            hrAttendanceDetailsEntity.setDeductDays(0);
                            HrPersonnelDetails hrPersonnelDetailsEntity = new HrPersonnelDetails();
                            HrPersonnelDetailsPK hrPersonnelDetailsPKEntity = new HrPersonnelDetailsPK();
                            hrPersonnelDetailsPKEntity.setCompCode(compCode);
                            hrPersonnelDetailsPKEntity.setEmpCode(BigInteger.valueOf(empCode));
                            hrPersonnelDetailsEntity.setHrPersonnelDetailsPK(hrPersonnelDetailsPKEntity);
                            hrAttendanceDetailsEntity.setHrPersonnelDetails(hrPersonnelDetailsEntity);
                            HrAttendanceDetailsDAO hrAttenDetailsDAO = new HrAttendanceDetailsDAO(em);
                            hrAttenDetailsDAO.save(hrAttendanceDetailsEntity);
                            count++;
                        } else {
                            ut.rollback();
                            return "Attendance already processed for the selected employee.";
                        }
                    } else {
                        ut.rollback();
                        return "Not an active employee.";
                    }
                    if (count != 0) {
                        ut.commit();
                        return "Attendance processed successfully for the selected employee.";
                    } else {
                        ut.rollback();
                        return "Attendance already processed for the selected employee.";
                    }
                } else {
                    List<HrPersonnelDetails> hrPersonnelDetailsList = new ArrayList<HrPersonnelDetails>();
                    if (selectionCategory.equalsIgnoreCase("All")) {
                        hrPersonnelDetailsList = hrPersonnelDao.findEmpByCompCodeWithStatusY(compCode);
                    }

                    if (!hrPersonnelDetailsList.isEmpty()) {
                        count = 0;
                        for (HrPersonnelDetails hrPersonnelDetail : hrPersonnelDetailsList) {
                            empCode = hrPersonnelDetail.getHrPersonnelDetailsPK().getEmpCode().longValue();
                            leave = 0;
                            holiDays = 0;
                            workingDays = 0;
                            Date holidayDate = new Date();
                            varFromDate = processingDateFrom;
//                            while (varFromDate.before(processingDateTo) || varFromDate.equals(processingDateTo)) {
//                                monthOfYear = Integer.parseInt(formatter.format(varFromDate).substring(3, 5));
//                                month = HrmsUtil.getMonthName(monthOfYear - 1);
//                                List<HrHolidayMaster> hrHolidayMasterList = hrHolidayDao.findByCompCodeAndHolidaydate(compCode, varFromDate);
//                                if (hrHolidayMasterList.isEmpty()) {
//                                    workingDays += 1;
//                                } else {
//                                    for (HrHolidayMaster hhm : hrHolidayMasterList) {
//                                        holidayDate = hhm.getHolidayDate();
//                                    }
//                                    holiDays += 1;
//                                }
//                                List<HrLeaveRegister> hrLeaveRegisterList = hrLeaveRegisteDao.getLeaveDataByEmpCodeAndDepartRecom(compCode, empCode, 'Y', sdf.format(varFromDate));
//                                if (!hrLeaveRegisterList.isEmpty()) {
//                                    if (varFromDate.compareTo(holidayDate) != 0) {
//                                        leave += 1;
//                                    }
//                                }
//                                Calendar c1 = Calendar.getInstance();
//                                c1.setTime(varFromDate);
//                                c1.add(Calendar.DATE, 1);
//                                varFromDate = c1.getTime();
//                            }

                            while (varFromDate.before(processingDateTo) || varFromDate.equals(processingDateTo)) {
                                monthOfYear = Integer.parseInt(formatter.format(varFromDate).substring(3, 5));
                                month = HrmsUtil.getMonthName(monthOfYear - 1);

                                List hList = em.createNativeQuery("SELECT * FROM lstholidays WHERE cast(brncode as unsigned) = '" + compCode + "' "
                                        + "and holidaydate = '" + ymmd.format(varFromDate) + "'").getResultList();
                                if (hList.isEmpty()) {
                                    workingDays += 1;
                                } else {
                                    holiDays += 1;
                                    holidayFlg = true;
                                }

                                List list = em.createNativeQuery("SELECT days_taken,paid FROM hr_leave_register h WHERE comp_Code = '" + compCode + "' "
                                        + "and emp_Code = '" + empCode + "' and h.depart_Recom = 'Y' and '" + ymd.format(varFromDate) + "' "
                                        + "between str_to_date(h.from_Date,'%d/%m/%Y') and str_to_date(h.to_Date,'%d/%m/%Y')").getResultList();
                                if (!list.isEmpty()) {
                                    if (holidayFlg != true) {
                                        leave += 1;
                                        for (int z = 0; z < list.size(); z++) {
                                            Object[] obj = (Object[]) list.get(z);
                                            if (obj[1].toString().trim().equalsIgnoreCase("N")) {
                                                unPaidLeave = unPaidLeave + Integer.parseInt(obj[0].toString());
                                            }
                                        }
                                    }
                                }
                                Calendar c1 = Calendar.getInstance();
                                c1.setTime(varFromDate);
                                c1.add(Calendar.DATE, 1);
                                varFromDate = c1.getTime();
                                holidayFlg = false;
                            }
                            paidLeave = leave - unPaidLeave;
                            int totalWorkingDays = workingDays - leave;
                            HrAttendanceDetailsPK hrAttendanceDetailsPK = new HrAttendanceDetailsPK();
                            HrAttendanceDetails hrAttendetails = new HrAttendanceDetails();
                            //  String year = formatter.format(varFromDate);
                            String year = formatter.format(processingDateFrom);

                            int attenYear = Integer.parseInt(year.substring(6, 10));
                            hrAttendanceDetailsPK.setAttenMonth(month);
                            hrAttendanceDetailsPK.setAttenYear(attenYear);
                            hrAttendanceDetailsPK.setCompCode(compCode);
                            hrAttendanceDetailsPK.setEmpCode(empCode);
                            hrAttendetails.setPostFlag('Y');
                            hrAttendetails.setHrAttendanceDetailsPK(hrAttendanceDetailsPK);
                            BaseEntity findById = hrAttendanceDao.findById(hrAttendetails, hrAttendetails.getHrAttendanceDetailsPK());
                            if (findById == null) {
                                HrAttendanceDetails hrAttendanceDetailsEntity = new HrAttendanceDetails();
                                HrAttendanceDetailsPK hrAttendanceDetailsPkEntity = new HrAttendanceDetailsPK();
                                hrAttendanceDetailsPkEntity.setCompCode(compCode);
                                hrAttendanceDetailsPkEntity.setEmpCode(empCode);
                                hrAttendanceDetailsPkEntity.setAttenMonth(month);
                                hrAttendanceDetailsPkEntity.setAttenYear(attenYear);
                                hrAttendanceDetailsEntity.setHrAttendanceDetailsPK(hrAttendanceDetailsPkEntity);
                                hrAttendanceDetailsEntity.setProcessDateFrom(processingDateFrom);
                                hrAttendanceDetailsEntity.setProcessDateTo(processingDateTo);
//                                hrAttendanceDetailsEntity.setWorkingDays(totalWorkingDays);
                                hrAttendanceDetailsEntity.setWorkingDays(workingDays);
                                hrAttendanceDetailsEntity.setPresentDays(totalWorkingDays);
                                //hrAttendanceDetailsEntity.setAbsentDays(0);
                                hrAttendanceDetailsEntity.setAbsentDays(unPaidLeave);
                                hrAttendanceDetailsEntity.setPaidLeave(paidLeave);
                                hrAttendanceDetailsEntity.setOverTimePeriod(0);
                                hrAttendanceDetailsEntity.setOverTimeUnit(hrAttendanceDetailsTO.getOverTimeUnit());
                                hrAttendanceDetailsEntity.setPostFlag('N');
                                hrAttendanceDetailsEntity.setDefaultComp(defaultComp);
                                hrAttendanceDetailsEntity.setStatFlag(String.valueOf(statFlag));
                                hrAttendanceDetailsEntity.setStatUpFlag(String.valueOf(statUpFlag));
                                hrAttendanceDetailsEntity.setModDate(new Date());
                                hrAttendanceDetailsEntity.setAuthBy(authBy);
                                hrAttendanceDetailsEntity.setEnteredBy(enteredBy);
                                HrPersonnelDetails hrPersonnelDetailsEntity = new HrPersonnelDetails();
                                HrPersonnelDetailsPK hrPersonnelDetailsPKEntity = new HrPersonnelDetailsPK();
                                hrPersonnelDetailsPKEntity.setCompCode(compCode);
                                hrPersonnelDetailsPKEntity.setEmpCode(BigInteger.valueOf(empCode));
                                hrPersonnelDetailsEntity.setHrPersonnelDetailsPK(hrPersonnelDetailsPKEntity);
                                hrAttendanceDetailsEntity.setHrPersonnelDetails(hrPersonnelDetailsEntity);
                                HrAttendanceDetailsDAO hrAttenDetailsDAO = new HrAttendanceDetailsDAO(em);
                                hrAttenDetailsDAO.save(hrAttendanceDetailsEntity);
                                count++;
                            } else {
                                continue;
                            }
                        }
                    } else {
                        ut.rollback();
                        return "There is no active employee in the category for the selected category";
                    }
                    if (count != 0) {
                        ut.commit();
                        return "Attendance successfully processed for " + count + " employee[s] of the selected category";
                    } else {
                        ut.rollback();
                        return "Attendance already processed for selected category!";
                    }
                }
            }


            //ut.commit();
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveAttendanceProcessing()", e);
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveAttendanceProcessing is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return message;
    }

    /**
     * function to get the all Employee attendance details for the company and
     * year passed
     *
     * @param compCode
     * @param year
     * @return
     * @throws ApplicationException
     */
    @Override
    public List getAttendanceDetails(int compCode, int year ) throws ApplicationException {
        long begin = System.nanoTime();
        HrAttendanceDetailsDAO hrAttendanceDao = new HrAttendanceDetailsDAO(em);
        List attendanceList = new ArrayList();
        try {
            attendanceList = hrAttendanceDao.getAttenDataByComCodeAndCurrentYr(compCode, year);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAttendanceDetails()", e);
            throw new ApplicationException(e.getMessage());
//            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
//                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getAttendanceDetails is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return attendanceList;
    }

    /**
     * Post employee attendance between processing date from and processing date
     * to , either by employee wise or category wise
     *
     * @param hrAttendanceDetailsTO
     * @param category
     * @param categoryDetails
     * @return
     * @throws ApplicationException
     */
    @Override
    public String postAttendanceProcessing(HrAttendanceDetailsTO hrAttendanceDetailsTO, String selectionCriteria, String selectedValues, String mode,String fromdate,String todate ) throws ApplicationException {
        long begin = System.nanoTime();
        int compCode = hrAttendanceDetailsTO.getHrAttendanceDetailsPKTO().getCompCode(), defaultComp;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymdformatter = new SimpleDateFormat("yyyyMMdd");
        Date processingDateFrom = hrAttendanceDetailsTO.getProcessDateFrom(), processingDateTo = hrAttendanceDetailsTO.getProcessDateTo();
        String message = "false";
        String statFlag = hrAttendanceDetailsTO.getStatFlag(), statUpFlag = hrAttendanceDetailsTO.getStatUpFlag();
        long empCode;
        String authBy = hrAttendanceDetailsTO.getAuthBy();
        String month ;
        int count = 0 ;        
        int deletionCount = 0;
        HrPersonnelDetailsDAO hrPersonnelDao = new HrPersonnelDetailsDAO(em);
        HrAttendanceDetailsDAO hrAttendanDAO = new HrAttendanceDetailsDAO(em);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (selectionCriteria.equalsIgnoreCase("EWE") || selectionCriteria.equalsIgnoreCase("ALL")) {
                HrPersonnelDetails hrPersonnelDetails = hrPersonnelDao.findByEmpStatusAndEmpId(compCode, selectedValues, 'Y');
                if (hrPersonnelDetails.getHrPersonnelDetailsPK() != null) {
                    empCode = hrPersonnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue();
                    HrAttendanceDetails hrAttendanceDetails = new HrAttendanceDetails();
                    HrAttendanceDetailsPK hrAttendanceDetailsPK = new HrAttendanceDetailsPK();
                    hrAttendanceDetailsPK.setCompCode(compCode);
                    hrAttendanceDetailsPK.setEmpCode(empCode);
                    if(selectionCriteria.equalsIgnoreCase("EWE")){
                    hrAttendanceDetails.setHrAttendanceDetailsPK(hrAttendanceDetailsPK);
                    hrAttendanceDetails.setProcessDateFrom(processingDateFrom);
                    hrAttendanceDetails.setProcessDateTo(processingDateTo);
                    hrAttendanceDetails.setPostFlag('N');
                    }else if(selectionCriteria.equalsIgnoreCase("ALL")){
                    hrAttendanceDetails.setHrAttendanceDetailsPK(hrAttendanceDetailsPK);
                    hrAttendanceDetails.setProcessDateFrom(formatter.parse(fromdate));
                    hrAttendanceDetails.setProcessDateTo(formatter.parse(todate));
                    hrAttendanceDetails.setPostFlag('N');    
                    }
                    List<HrAttendanceDetails> hrAttendanceDetailsList = hrAttendanDAO.getAttendanceDetailsWithPostFlag(hrAttendanceDetails);
                    if (!hrAttendanceDetailsList.isEmpty()) {
                        for (HrAttendanceDetails hrAttenDetailsEntity : hrAttendanceDetailsList) {
                            if (mode.equalsIgnoreCase("DELETE")) {
                                hrAttendanDAO.delete(hrAttenDetailsEntity, hrAttenDetailsEntity.getHrAttendanceDetailsPK());
                                ut.commit();
                                return "Deletion successfull for selected employee!!";
                            }
                            hrAttenDetailsEntity.setAuthBy(authBy);
                           hrAttenDetailsEntity.setPostFlag('Y');
                            hrAttendanDAO.update(hrAttenDetailsEntity);
                            count++;          
                        }
                    } else {
                        ut.rollback();
                        return "Attendance details  has been already posted for selected employee Or no data available for processing!!";
                    }
                    if (count != 0) {
                        ut.commit();
                        return "Attendance posted successfully for selected employee ";
                    } else {
                        ut.rollback();
                        return "Attendance could not be posted for selected employee!!";
                    }
                } else {
                    ut.commit();
                    return "Employee not active..";
                }
            } else {
                List<HrPersonnelDetails> hrPersonnelDetailsList = hrPersonnelDao.findEntityEmpStatusY(compCode, selectedValues);
                if (!hrPersonnelDetailsList.isEmpty()) {
                    for (HrPersonnelDetails hrPersonnelDetails : hrPersonnelDetailsList) {
                        empCode = hrPersonnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue();
                        HrAttendanceDetails hrAttendanceDetails = new HrAttendanceDetails();
                        HrAttendanceDetailsPK hrAttendanceDetailsPK = new HrAttendanceDetailsPK();
                        hrAttendanceDetailsPK.setCompCode(compCode);
                        hrAttendanceDetailsPK.setEmpCode(empCode);
                        hrAttendanceDetails.setHrAttendanceDetailsPK(hrAttendanceDetailsPK);
                        hrAttendanceDetails.setProcessDateFrom(processingDateFrom);
                        hrAttendanceDetails.setProcessDateTo(processingDateTo);
                        hrAttendanceDetails.setPostFlag('N');
                        List<HrAttendanceDetails> hrAttendanceDetailsList = hrAttendanDAO.getAttendanceDetailsWithPostFlag(hrAttendanceDetails);
                        if (!hrAttendanceDetailsList.isEmpty()) {
                            for (HrAttendanceDetails hrAttenDetailsEntity : hrAttendanceDetailsList) {
                                if (mode.equalsIgnoreCase("DELETE")) {
                                    hrAttendanDAO.delete(hrAttenDetailsEntity, hrAttenDetailsEntity.getHrAttendanceDetailsPK());
                                    deletionCount++;
                                } else {
                                    hrAttenDetailsEntity.setAuthBy(authBy);
                                    hrAttenDetailsEntity.setPostFlag('Y');
                                    hrAttendanDAO.update(hrAttenDetailsEntity);
                                    count++;
                                }
                            }
                        }
                    }
                    if (mode.equalsIgnoreCase("DELETE")) {
                        if (deletionCount != 0) {
                            ut.commit();
                            return "Deletion successfully posted for the selected category";
                        } else {
                            ut.rollback();
                            return "Deletion not processed!!!";
                        }
                    } else {
                        if (count != 0) {
                            ut.commit();
                            return "Attendance successfully posted for the selected category";
                        } else {
                            ut.rollback();
                            return "Attendance not posted!!";
                        }
                    }
                } else {
                    ut.rollback();
                    return "There is no active employee in the selected category..";
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method postAttendanceProcessing()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                try {
                    ut.rollback();
                    throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
                } catch (Exception ex) {
                    throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), ex);
                }
            } else {
                try {
                    ut.rollback();
                    throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                            "System exception has occured"), e);
                } catch (Exception ex) {
                    throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                            "System exception has occured"), ex);
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method postAttendanceProcessing()", e);
            try {
                ut.rollback();
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"));
            } catch (Exception ex) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"));
            }
        }
         
    }

    /**
     * edit the employee attendance details selected
     *
     * @param hrAttendanceDetailsTO
     * @param empId
     * @return
     * @throws ApplicationException
     */
    @Override
    public String editAttendanceDetails(HrAttendanceDetailsTO hrAttendanceDetailsTO, String empId) throws ApplicationException {
        long begin = System.nanoTime();
        String message = "false";
        int count = 0, compCode = hrAttendanceDetailsTO.getHrAttendanceDetailsPKTO().getCompCode();
        String month = hrAttendanceDetailsTO.getHrAttendanceDetailsPKTO().getAttenMonth(), authBy = hrAttendanceDetailsTO.getAuthBy(), enteredBy = hrAttendanceDetailsTO.getEnteredBy();
        int workingDays = hrAttendanceDetailsTO.getWorkingDays(), presentDays = hrAttendanceDetailsTO.getPresentDays(), absentDays = hrAttendanceDetailsTO.getAbsentDays(),
                attenYear = hrAttendanceDetailsTO.getHrAttendanceDetailsPKTO().getAttenYear();
        double dedDays = hrAttendanceDetailsTO.getDeductDays();
        long overTime = hrAttendanceDetailsTO.getOverTimePeriod();
        HrAttendanceDetailsDAO hrAttendanceDetailsDAO = new HrAttendanceDetailsDAO(em);
        HrPersonnelDetailsDAO hrPersonnelDAO = new HrPersonnelDetailsDAO(em);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            HrPersonnelDetails hrPersonnelDetails = hrPersonnelDAO.findByEmpStatusAndEmpId(compCode, empId, 'Y');
            if (hrPersonnelDetails.getHrPersonnelDetailsPK() != null) {
                HrAttendanceDetails hrAttendanceDetails = new HrAttendanceDetails();
                HrAttendanceDetailsPK hrAttendanceDetailsPK = new HrAttendanceDetailsPK();
                hrAttendanceDetailsPK.setCompCode(compCode);
                hrAttendanceDetailsPK.setEmpCode(hrPersonnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());
                hrAttendanceDetailsPK.setAttenMonth(month);
                hrAttendanceDetailsPK.setAttenYear(attenYear);
                hrAttendanceDetails.setHrAttendanceDetailsPK(hrAttendanceDetailsPK);
                hrAttendanceDetails.setPostFlag('N');
                List<HrAttendanceDetails> hrAttendanceList = hrAttendanceDetailsDAO.findAttendanceDetailsPostedForMonth(hrAttendanceDetails);
                if (!hrAttendanceList.isEmpty()) {
                    for (HrAttendanceDetails hrAttenDetailsEntity : hrAttendanceList) {
                        hrAttenDetailsEntity.setAbsentDays(absentDays);
                        hrAttenDetailsEntity.setEnteredBy(enteredBy);
                        hrAttenDetailsEntity.setWorkingDays(workingDays);
                        hrAttenDetailsEntity.setPresentDays(presentDays);
                        hrAttenDetailsEntity.setModDate(new Date());
                        hrAttenDetailsEntity.setOverTimePeriod(overTime);
                        hrAttenDetailsEntity.setAuthBy(authBy);
                        hrAttenDetailsEntity.setEnteredBy(enteredBy);
                        hrAttenDetailsEntity.setDeductDays(dedDays);
                        hrAttendanceDetailsDAO.update(hrAttenDetailsEntity);
                        count++;
                    }
                } else {
                    ut.rollback();
                    return "Attendance details already posted so can not be updated !";
                }
                if (count != 0) {
                    ut.commit();
                    return "Attendance details Successfully Updated for selected employee. ";
                }
            } else {
                ut.rollback();
                return "There is no active employee in the company";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method editAttendanceDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                try {
                    ut.rollback();
                    throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
                } catch (Exception ex) {
                    throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), ex);
                }
            } else {
                try {
                    ut.rollback();
                    throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                            "System exception has occured"), e);
                } catch (Exception ex) {
                    throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                            "System exception has occured"), ex);
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method editAttendanceDetails()", e);
            try {
                ut.rollback();
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"));
            } catch (Exception ex) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"));
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for editAttendanceDetails is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return message;
    }

    /**
     * Post salary processed for employee
     *
     * @param hrSalaryProcessingTO
     * @param selectionCriteria
     * @param selectedValues
     * @return
     * @throws ApplicationException
     */
    @Override
    public String salaryPosting(List<HrSalaryProcessingTO> hrSalaryProcessingTOs, String selectionCriteria,
            String selectedValues, String authBy, Date fstDt, Date lastDt) throws Exception {
        long begin = System.nanoTime();
        UserTransaction ut = context.getUserTransaction();
        List<TransferSmsRequestTo> smsList = new ArrayList();
//        String smsAccountNo = "";
//        double smsAmount = 0.0;
        try {
            ut.begin();
            //GROOS POSTING IN ACCOUNT OR NET POSTING 0 : GROSS , 1: NET
            int grossOrNet = ftsFacade.getCodeForReportName("GROSS_OR_NET");
            if (grossOrNet == 0) {
       
                ibtFacade = (InterBranchTxnFacadeRemote) ServiceLocator.getInstance().lookup("InterBranchTxnFacade");
                ftsFacade = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
                SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");

                //GET THE SALARY HEAD TO DEBIT
                String debitGlSalary = "";
                List salGlList = em.createNativeQuery("select acno from abb_parameter_info where purpose = 'SAL_GL_HEAD'").getResultList();
                if (!salGlList.isEmpty()) {
                    debitGlSalary = salGlList.get(0).toString();
                } else {
                    throw new Exception("Please Define Salary Debit GL Head");
                }

                //LOAN RECOVERED FROM SALARY OR NOT   0 : NO , 1: YES
                int loanRecInSal = ftsFacade.getCodeForReportName("LOAN_REC_IN_SAL");

                //PF IN GL OR IN ACCOUNT  0 : IN ACCOUNT , 1: IN GL
                int pfInGl = ftsFacade.getCodeForReportName("PF_IN_GL");


                //PF EMPLOYER CONTRIBUTION  0 : NO , 1: YES
                int pfEmpCont = ftsFacade.getCodeForReportName("PF_EMPLOYER_CONT");

                String pfContHead = "";
                if (pfEmpCont == 1) {
                    //GET THE PF CONTRIBUTION HEAD
                    List pfContHeadList = em.createNativeQuery("select acno from abb_parameter_info where purpose = 'PF_CONT_HEAD'").getResultList();
                    if (!pfContHeadList.isEmpty()) {
                        pfContHead = pfContHeadList.get(0).toString();
                    } else {
                        throw new Exception("Please Define PF Contribution Head");
                    }
                }

                //TAX ON SALARY 0 : NO , 1: YES
                int taxOnSal = ftsFacade.getCodeForReportName("TAX_ON_SAL");

                //GET THE SALARY HEAD TO DEBIT
                String taxGlHead = "";
                List taxGlList = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab"
                        + " where TDS_Applicabledate<='" + ymd.format(new Date()) + "')").getResultList();
                if (!taxGlList.isEmpty()) {
                    taxGlHead = taxGlList.get(0).toString();
                } else {
                    throw new Exception("Please Define TDS GL Head");
                }

                if (selectionCriteria.equalsIgnoreCase("BRN")) {
                    debitGlSalary = selectedValues + debitGlSalary;
                    pfContHead = selectedValues + pfContHead;
                    taxGlHead = selectedValues + taxGlHead;
                } else {
                    String empBrn = "";
                    for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                        empBrn = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                    }
                    debitGlSalary = empBrn + debitGlSalary;
                    pfContHead = empBrn + pfContHead;
                    taxGlHead = empBrn + taxGlHead;
                }

                double totDebitAmt = 0;
                double pfTotAmt = 0;
                double dedTotAmt = 0;
                double dedTotTax = 0;
                String remoteIden = remoteBranchIdentification(hrSalaryProcessingTOs, "S");

                //To Save The Details of The Employee Salary
                for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                    HrSalaryProcessing hrSalProcessing = new HrSalaryProcessing();
                    HrSalaryProcessingPK hrSalProcessPk = new HrSalaryProcessingPK();
                    for (int i = 0; i < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); i++) {
                        HrSalaryProcessingDtl hrSalDtl = new HrSalaryProcessingDtl();
                        //HrSalaryProcessingDtlPK hrSalDtlPk = new HrSalaryProcessingDtlPK();

                        // hrSalDtlPk.setRefNo(salaryProcessingTO.getRefNo());
                        //hrSalDtlPk.setComponentType(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getComponentType());
                        // hrSalDtlPk.setEmpCode(BigInteger.valueOf(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getEmpCode()));
                        //  hrSalDtlPk.setCompCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getCompCode());
                        // hrSalDtl.setHrSalaryProcessingDtlPK(hrSalDtlPk);
                        hrSalDtl.setRefNo(salaryProcessingTO.getRefNo());
                        hrSalDtl.setComponentType(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getComponentType());
                        hrSalDtl.setEmpCode(BigInteger.valueOf(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getEmpCode()));
                        hrSalDtl.setCompCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getCompCode());
                        hrSalDtl.setAmount((float) salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary());
                        hrSalDtl.setDefaultComp(salaryProcessingTO.getDefaultComp());
                        hrSalDtl.setDescShortCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode());
                        hrSalDtl.setPurposeCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode());
                        hrSalDtl.setMonth(salaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                        hrSalDtl.setEnteredDate(new Date());
                        hrSalDtl.setMonthStartDate(fstDt);
                        hrSalDtl.setMonthLastDate(lastDt);
                        hrSalDtl.setGlCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getGlCode());
                        HrSalaryProcessingDtlDAO salarydtlDAO = new HrSalaryProcessingDtlDAO(em);
                        salarydtlDAO.save(hrSalDtl);

                        if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode().equalsIgnoreCase("PUR002")) {
                            if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode() == 1) {
                                if (pfEmpCont == 1) {
                                    HrSalaryProcessingDtl hrSalDtl1 = new HrSalaryProcessingDtl();
                                    //HrSalaryProcessingDtlPK hrSalDtlPk1 = new HrSalaryProcessingDtlPK();
                                    //  hrSalDtlPk1.setRefNo(salaryProcessingTO.getRefNo());
                                    //  hrSalDtlPk1.setComponentType("EMPLOYER PF CONTRIBUTION ");
                                    //   hrSalDtlPk1.setEmpCode(BigInteger.valueOf(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getEmpCode()));
                                    //    hrSalDtlPk1.setCompCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getCompCode());
                                    //     hrSalDtl1.setHrSalaryProcessingDtlPK(hrSalDtlPk1);
                                    hrSalDtl1.setRefNo(salaryProcessingTO.getRefNo());
                                    hrSalDtl1.setComponentType("EMPLOYER PF CONTRIBUTION ");
                                    hrSalDtl1.setEmpCode(BigInteger.valueOf(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getEmpCode()));
                                    hrSalDtl1.setCompCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getCompCode());
                                    hrSalDtl1.setAmount((float) salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary());
                                    hrSalDtl1.setDefaultComp(salaryProcessingTO.getDefaultComp());
                                    hrSalDtl1.setDescShortCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode());
                                    hrSalDtl1.setPurposeCode("PUR004");
                                    hrSalDtl1.setMonth(salaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                                    hrSalDtl1.setEnteredDate(new Date());
                                    hrSalDtl1.setMonthStartDate(fstDt);
                                    hrSalDtl1.setMonthLastDate(lastDt);

                                    hrSalDtl1.setGlCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getGlCode());
                                    HrSalaryProcessingDtlDAO salarydtlDAO1 = new HrSalaryProcessingDtlDAO(em);
                                    salarydtlDAO1.save(hrSalDtl1);

                                    pfTotAmt = pfTotAmt + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary();
                                }
                            }
                            dedTotAmt = dedTotAmt + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary();
                        }
                    }

                    hrSalProcessPk.setEmpCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(0).getHrSalaryProcessingDetailPK().getEmpCode());
                    hrSalProcessPk.setMonths(salaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                    hrSalProcessPk.setCompCode(salaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                    hrSalProcessPk.setCalDateFrom(salaryProcessingTO.getHrSalaryProcessingPK().getCalDateFrom());
                    hrSalProcessPk.setCalDateTo(salaryProcessingTO.getHrSalaryProcessingPK().getCalDateTo());
                    hrSalProcessing.setHrSalaryProcessingPK(hrSalProcessPk);
                    hrSalProcessing.setAuthBy(salaryProcessingTO.getAuthBy());
                    hrSalProcessing.setDeductiveTax(salaryProcessingTO.getDeductiveTax());
                    hrSalProcessing.setDefaultComp(salaryProcessingTO.getDefaultComp());
                    hrSalProcessing.setEnteredBy(salaryProcessingTO.getEnteredBy());
                    hrSalProcessing.setGrossSalary(salaryProcessingTO.getGrossSalary());
                    hrSalProcessing.setModDate(salaryProcessingTO.getModDate());
                    hrSalProcessing.setPostFlag(salaryProcessingTO.getPostFlag());
                    hrSalProcessing.setReceiptFlag(salaryProcessingTO.getReceiptFlag());
                    hrSalProcessing.setRefNo(salaryProcessingTO.getRefNo());
                    hrSalProcessing.setSalary(salaryProcessingTO.getSalary());
                    hrSalProcessing.setStatFlag(salaryProcessingTO.getStatFlag());
                    hrSalProcessing.setStatUpFlag(salaryProcessingTO.getStatUpFlag());

                    HrSalaryProcessingDAO salaryDAO = new HrSalaryProcessingDAO(em);
                    salaryDAO.save(hrSalProcessing);
                    totDebitAmt = totDebitAmt + salaryProcessingTO.getGrossSalary();
                    dedTotTax = dedTotTax + salaryProcessingTO.getDeductiveTax();
                }

                //TranDesc = 81
                float trsNo = ftsFacade.getTrsNo();
                String msg = "";

                //Map<Integer, List<String>> deductMap = new HashMap<Integer, List<String>>();
                Map<Integer, List<String>> deductMap = new ConcurrentHashMap<Integer, List<String>>();

                if (remoteIden.equalsIgnoreCase("TRUE")) {
                    for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                        float recNo = ftsFacade.getRecNo();

                        String appBranch = "";
                        if (selectionCriteria.equalsIgnoreCase("BRN")) {
                            appBranch = selectedValues;
                        } else {
                            appBranch = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                        }

                        if (salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2).equalsIgnoreCase(appBranch)) {
                            TransferSmsRequestTo to = new TransferSmsRequestTo();
                            to.setAcno(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                            to.setAmount(salaryProcessingTO.getGrossSalary());
                            smsList.add(to);

                            msg = ibtFacade.cbsPostingCx(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0, ymd.format(new Date()), salaryProcessingTO.getGrossSalary(), 0.0, 2,
                                    "Salary For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                    salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2), salaryProcessingTO.getEnteredBy(), authBy, trsNo, "0", "");
                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                throw new Exception(msg);
                            }

                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                    + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "',"
                                    + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "','" + ymd.format(new Date()) + "',"
                                    + "'" + ymd.format(new Date()) + "',0," + salaryProcessingTO.getGrossSalary() + ",1,2,"
                                    + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                    + "81 ,0,'A','Salary Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                    + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',"
                                    + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();
   
                            if (insertResult <= 0) {
                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                            }
                        } else {
                            TransferSmsRequestTo to = new TransferSmsRequestTo();
                            to.setAcno(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                            to.setAmount(salaryProcessingTO.getGrossSalary());
                            smsList.add(to);

                            msg = ibtFacade.cbsPostingSx(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0, ymd.format(new Date()), salaryProcessingTO.getGrossSalary(), 0.0, 2,
                                    "Salary For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                    appBranch, salaryProcessingTO.getEnteredBy(), authBy, trsNo, "0", "");
                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                throw new Exception(msg);
                            }

                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                    + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "',"
                                    + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "','" + ymd.format(new Date()) + "',"
                                    + "'" + ymd.format(new Date()) + "',0," + salaryProcessingTO.getGrossSalary() + ",1,2,"
                                    + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                    + "81 ,0,'A','Salary Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                    + "'','" + appBranch + "',"
                                    + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();

                            if (insertResult <= 0) {
                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                            }
                        }

                        for (int m = 0; m < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); m++) {
                            if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getPurCode().equalsIgnoreCase("PUR002")) {
                                if (deductMap.containsKey(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode())) {
                                    for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {
                                        Map.Entry entry = (Map.Entry) it.next();
                                        int sKey = (Integer) entry.getKey();
                                        if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode() == sKey) {
                                            List kLst = (List) entry.getValue();
                                            String sGl = kLst.get(0).toString();
                                            double nVal = Double.parseDouble(kLst.get(1).toString()) + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getSalary();
                                            List glVal = new ArrayList();
                                            glVal.add(sGl);
                                            glVal.add(nVal);

                                            deductMap.remove(sKey);
                                            deductMap.put(sKey, glVal);
                                        }
                                    }
                                } else {
                                    List glVal = new ArrayList();
                                    glVal.add(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getGlCode());
                                    glVal.add(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getSalary());
                                    deductMap.put(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode(), glVal);
                                }
                            }
                        }
                    }
                } else {
                    for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                        float recNo = ftsFacade.getRecNo();
                        String acNature = ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());

                        TransferSmsRequestTo to = new TransferSmsRequestTo();
                        to.setAcno(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                        to.setAmount(salaryProcessingTO.getGrossSalary());
                        smsList.add(to);

                        String lResult = ftsFacade.insertRecons(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0,
                                salaryProcessingTO.getGrossSalary(), ymd.format(new Date()), ymd.format(new Date()), 2,
                                "Salary For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), salaryProcessingTO.getEnteredBy(),
                                trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch(), 0, null, "", "");
                        if (lResult.equalsIgnoreCase("True")) {
                            msg = ftsFacade.updateBalance(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), salaryProcessingTO.getSalary(), 0, "Y", "Y");
                            if (msg.equalsIgnoreCase("True")) {
                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                        + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "',"
                                        + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "','" + ymd.format(new Date()) + "',"
                                        + "'" + ymd.format(new Date()) + "',0," + salaryProcessingTO.getGrossSalary() + ",1,2,"
                                        + "" + recNo + ",'',null,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                        + "81 ,0,'A','Salary Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                        + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                        + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();
                                    
                                if (insertResult <= 0) {
                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                }
                            } else {
                                throw new Exception(msg);
                            }

                            for (int m = 0; m < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); m++) {
                                if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getPurCode().equalsIgnoreCase("PUR002")) {
                                    if (deductMap.containsKey(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode())) {
                                        for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {
                                            Map.Entry entry = (Map.Entry) it.next();
                                            int sKey = (Integer) entry.getKey();
                                            if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode() == sKey) {
                                                List kLst = (List) entry.getValue();
                                                String sGl = kLst.get(0).toString();
                                                double nVal = Double.parseDouble(kLst.get(1).toString()) + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getSalary();
                                                List glVal = new ArrayList();
                                                glVal.add(sGl);
                                                glVal.add(nVal);

                                                deductMap.remove(sKey);
                                                deductMap.put(sKey, glVal);
                                            }
                                        }
                                    } else {
                                        List glVal = new ArrayList();
                                        glVal.add(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getGlCode());
                                        glVal.add(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getSalary());
                                        deductMap.put(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode(), glVal);
                                    }
                                }
                            }
                        } else {
                            throw new Exception(lResult);
                        }
                    }
                }

                if (pfInGl == 0) {
                    String remoteIdenP = remoteBranchIdentification(hrSalaryProcessingTOs, "P");

                    if (remoteIdenP.equalsIgnoreCase("TRUE")) {
                        double pfAmt = 0d;
                        for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                            for (int i = 0; i < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); i++) {
                                if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode().equalsIgnoreCase("PUR002")) {
                                    if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode() == 1) {
                                        pfAmt = salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary();

                                        String appBranch = "";
                                        if (selectionCriteria.equalsIgnoreCase("BRN")) {
                                            appBranch = selectedValues;
                                        } else {
                                            appBranch = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                                        }

                                        float recNo = ftsFacade.getRecNo();
                                        if (salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2).equalsIgnoreCase(appBranch)) {
                                            msg = ibtFacade.cbsPostingCx(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 1, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                    "PF Deduction For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2), salaryProcessingTO.getEnteredBy(),
                                                    authBy, trsNo, "0", "");
                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }
                                            msg = ftsFacade.updateBalance(ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()),
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0, pfAmt, "Y", "Y");

                                            if (msg.equalsIgnoreCase("true")) {
                                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                        + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "',"
                                                        + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "','" + ymd.format(new Date()) + "',"
                                                        + "'" + ymd.format(new Date()) + "',0," + pfAmt + ",1,2,"
                                                        + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                        + "81 ,0,'A','PF Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                        + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',"
                                                        + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();

                                                if (insertResult <= 0) {
                                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                }
                                            } else {
                                                throw new Exception(msg);
                                            }
                                        } else {

                                            msg = ibtFacade.cbsPostingSx(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 1, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                    "PF Deduction For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                                    appBranch, salaryProcessingTO.getEnteredBy(),
                                                    authBy, trsNo, "0", "");
                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }

                                            msg = ftsFacade.updateBalance(ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()),
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0, pfAmt, "Y", "Y");

                                            if (msg.equalsIgnoreCase("true")) {
                                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                        + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "',"
                                                        + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "','" + ymd.format(new Date()) + "',"
                                                        + "'" + ymd.format(new Date()) + "',0," + pfAmt + ",1,2,"
                                                        + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                        + "81 ,0,'A','PF Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                        + "'','" + appBranch + "',"
                                                        + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();
                   
                                                if (insertResult <= 0) {
                                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                }
                                            } else {
                                                throw new Exception(msg);
                                            }
                                        }

                                        if (salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2).equalsIgnoreCase(appBranch)) {
                                            recNo = ftsFacade.getRecNo();
                                            msg = ibtFacade.cbsPostingCx(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                    "PF For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2), salaryProcessingTO.getEnteredBy(),
                                                    authBy, trsNo, "0", "");
                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }

                                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                    + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                    + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                    + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                    + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                    + "81 ,0,'A','PF Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                    + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',"
                                                    + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();
                                        
                                            if (insertResult <= 0) {
                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                            }
                                        } else {
                                            recNo = ftsFacade.getRecNo();
                                            msg = ibtFacade.cbsPostingSx(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                    "PF For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                    appBranch, salaryProcessingTO.getEnteredBy(),
                                                    authBy, trsNo, "0", "");
                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }

                                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                    + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                    + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                    + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                    + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                    + "81 ,0,'A','PF Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                    + "'','" + appBranch + "',"
                                                    + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();
                                         
                                            if (insertResult <= 0) {
                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                            }
                                        }

                                        if (pfEmpCont == 1) {
                                            recNo = ftsFacade.getRecNo();

                                            if (salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2).equalsIgnoreCase(appBranch)) {
                                                msg = ibtFacade.cbsPostingCx(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                        "PF Employer Contribution For The Month" + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                        0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                        salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2), salaryProcessingTO.getEnteredBy(),
                                                        authBy, trsNo, "0", "");
                                                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                    throw new Exception(msg);
                                                }

                                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                        + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                        + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                        + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                        + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                        + "81 ,0,'A','PF Employer Contribution For The Month Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                        + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',"
                                                        + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();
                                                 
                                                if (insertResult <= 0) {
                                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                }
                                            } else {
                                                msg = ibtFacade.cbsPostingSx(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                        "PF Employer Contribution For The Month" + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                        0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                        appBranch, salaryProcessingTO.getEnteredBy(),
                                                        authBy, trsNo, "0", "");
                                                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                    throw new Exception(msg);
                                                }

                                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                        + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                        + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                        + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                        + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                        + "81 ,0,'A','PF Employer Contribution For The Month Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                        + "'','" + appBranch + "',"
                                                        + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();
            
                                                if (insertResult <= 0) {
                                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        double pfAmt = 0d;
                        for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                            for (int i = 0; i < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); i++) {
                                if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode().equalsIgnoreCase("PUR002")) {
                                    if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode() == 1) {
                                        pfAmt = salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary();

                                        float recNo = ftsFacade.getRecNo();
                                        String acNature = ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount());

                                        String lResult = ftsFacade.insertRecons(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 1,
                                                pfAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                                                "PF Deduction For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), salaryProcessingTO.getEnteredBy(),
                                                trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                                salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                                salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch(), 0, null, "", "");
                                        if (lResult.equalsIgnoreCase("True")) {
                                            msg = ftsFacade.updateBalance(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0, pfAmt, "Y", "Y");
                                            if (msg.equalsIgnoreCase("True")) {
                                                lResult = ftsFacade.insertRecons(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0,
                                                        pfAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                                                        "PF For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), salaryProcessingTO.getEnteredBy(),
                                                        trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                                        salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                        salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch(), 0, null, "", "");
                                                if (lResult.equalsIgnoreCase("True")) {
                                                    msg = ftsFacade.updateBalance(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), pfAmt, 0, "Y", "Y");
                                                    if (msg.equalsIgnoreCase("True")) {
                                                        int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                                + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                                + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                                + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "',"
                                                                + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "','" + ymd.format(new Date()) + "',"
                                                                + "'" + ymd.format(new Date()) + "',0," + pfAmt + ",1,2,"
                                                                + "" + recNo + ",'',null,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                                + "81 ,0,'A','PF Deducted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                                + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                                                + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();
                                                        
                                                        if (insertResult <= 0) {
                                                            throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                        }

                                                        insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                                + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                                + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                                + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                                + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                                + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                                + "" + recNo + ",'',null,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                                + "81 ,0,'A','PF Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                                + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                                                + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();
                                     
                                                        if (insertResult <= 0) {
                                                            throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                        }
                                                    }
                                                } else {
                                                    throw new Exception(lResult);
                                                }
                                            } else {
                                                throw new Exception(msg);
                                            }
                                        } else {
                                            throw new Exception(lResult);
                                        }

                                        if (pfEmpCont == 1) {
                                            recNo = ftsFacade.getRecNo();
                                            acNature = ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount());

                                            lResult = ftsFacade.insertRecons(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0,
                                                    pfAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                                                    "PF Employer Contribution For The Month" + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), salaryProcessingTO.getEnteredBy(),
                                                    trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch(), 0, null, "", "");
                                            if (lResult.equalsIgnoreCase("True")) {
                                                msg = ftsFacade.updateBalance(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), pfAmt, 0, "Y", "Y");
                                                if (msg.equalsIgnoreCase("True")) {
                                                    int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                            + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                            + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                            + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                            + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                            + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                            + "" + recNo + ",'',null,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                            + "81 ,0,'A','PF Employer Contribution For The Month Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                            + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                                            + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();
                    
                                                    if (insertResult <= 0) {
                                                        throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                    }
                                                } else {
                                                    throw new Exception(msg);
                                                }
                                            } else {
                                                throw new Exception(lResult);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                float recNo = ftsFacade.getRecNo();

                String lResult = ftsFacade.insertRecons("PO", debitGlSalary, 1, totDebitAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                        "Salary For The Month", authBy, trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                        debitGlSalary.substring(0, 2), debitGlSalary.substring(0, 2), 0, null, "", "");

                if (lResult.equalsIgnoreCase("true")) {
                    msg = ftsFacade.updateBalance("PO", debitGlSalary, 0, totDebitAmt, "Y", "Y");
                    if (msg.equalsIgnoreCase("true")) {
                        int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                + "adviceno,advicebrncode) values('" + debitGlSalary + "','" + ftsFacade.ftsGetCustName(debitGlSalary) + "',"
                                + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "'," + totDebitAmt + ","
                                + "0,0,2," + recNo + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + authBy + "',81 ,0,'A',"
                                + "'Salary Posting. Entry','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + debitGlSalary.substring(0, 2) + "',"
                                + "'" + debitGlSalary.substring(0, 2) + "',0,'','')").executeUpdate();
               
                        if (insertResult <= 0) {
                            throw new Exception("Data insertion problem in transfer scroll for account number: " + debitGlSalary);
                        }
                    } else {
                        throw new Exception(msg);
                    }
                } else {
                    throw new Exception(lResult);
                }

                if (pfEmpCont == 1) {
                    recNo = ftsFacade.getRecNo();

                    lResult = ftsFacade.insertRecons("PO", pfContHead, 1, pfTotAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                            "Employer PF Contribution Entry", authBy, trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                            pfContHead.substring(0, 2), pfContHead.substring(0, 2), 0, null, "", "");

                    if (lResult.equalsIgnoreCase("true")) {
                        msg = ftsFacade.updateBalance("PO", pfContHead, 0, pfTotAmt, "Y", "Y");
                        if (msg.equalsIgnoreCase("true")) {
                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                    + "adviceno,advicebrncode) values('" + pfContHead + "','" + ftsFacade.ftsGetCustName(pfContHead) + "',"
                                    + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "',0,"
                                    + "" + pfTotAmt + ",1,2," + recNo + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + authBy + "',81 ,0,'A',"
                                    + "'Employer PF Contribution Entry','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + pfContHead.substring(0, 2) + "',"
                                    + "'" + pfContHead.substring(0, 2) + "',0,'','')").executeUpdate();
                    
                            if (insertResult <= 0) {
                                throw new Exception("Data insertion problem in transfer scroll for account number: " + pfContHead);
                            }
                        } else {
                            throw new Exception(msg);
                        }
                    } else {
                        throw new Exception(lResult);
                    }
                }

                if (taxOnSal == 1) {
                    recNo = ftsFacade.getRecNo();

                    lResult = ftsFacade.insertRecons("PO", taxGlHead, 0, dedTotTax, ymd.format(new Date()), ymd.format(new Date()), 2,
                            "Total Tds Entery On Salary", authBy, trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                            taxGlHead.substring(0, 2), taxGlHead.substring(0, 2), 0, null, "", "");

                    if (lResult.equalsIgnoreCase("true")) {
                        msg = ftsFacade.updateBalance("PO", taxGlHead, dedTotTax, 0, "Y", "Y");
                        if (msg.equalsIgnoreCase("true")) {
                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                    + "adviceno,advicebrncode) values('" + taxGlHead + "','" + ftsFacade.ftsGetCustName(taxGlHead) + "',"
                                    + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "'," + dedTotTax + ","
                                    + "0,0,2," + recNo + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + authBy + "',81 ,0,'A',"
                                    + "'Total Tds Entery On Salary','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + taxGlHead.substring(0, 2) + "',"
                                    + "'" + taxGlHead.substring(0, 2) + "',0,'','')").executeUpdate();
                                   
                            if (insertResult <= 0) {
                                throw new Exception("Data insertion problem in transfer scroll for account number: " + taxGlHead);
                            }
                        } else {
                            throw new Exception(msg);
                        }
                    } else {
                        throw new Exception(lResult);
                    }
                }

                for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();
                    List kLst = (List) entry.getValue();
                    String sGl = kLst.get(0).toString();
                    String sSGl = kLst.get(0).toString();
                    double nVal = Double.parseDouble(kLst.get(1).toString());
                    int sKey = (Integer) entry.getKey();
                    if (pfInGl == 1 && sKey == 1) {
                        if ((sGl == null || sGl.equalsIgnoreCase(""))) {
                            throw new Exception("PF GL NOT DEFINED");
                        }
                        nVal = nVal + pfTotAmt;
                    }

                    if (!(sGl == null || sGl.equalsIgnoreCase(""))) {
                        recNo = ftsFacade.getRecNo();

                        if (selectionCriteria.equalsIgnoreCase("BRN")) {
                            sGl = selectedValues + sGl + "01";
                        } else {
                            String empBrn = "";
                            for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                                empBrn = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                            }
                            sGl = empBrn + sGl + "01";
                        }

                        lResult = ftsFacade.insertRecons("PO", sGl, 0, nVal, ymd.format(new Date()), ymd.format(new Date()), 2,
                                "Salary Deduction", authBy, trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                sGl.substring(0, 2), sGl.substring(0, 2), 0, null, "", "");

                        if (lResult.equalsIgnoreCase("true")) {
                            msg = ftsFacade.updateBalance("PO", sGl, nVal, 0, "Y", "Y");
                            if (msg.equalsIgnoreCase("true")) {
                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                        + "adviceno,advicebrncode) values('" + sGl + "','" + ftsFacade.ftsGetCustName(sGl) + "',"
                                        + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "'," + nVal + ","
                                        + "0,0,2," + recNo + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + authBy + "',81 ,0,'A',"
                                        + "'Salary Deduction','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + sGl.substring(0, 2) + "',"
                                        + "'" + sGl.substring(0, 2) + "',0,'','')").executeUpdate();
                                
                                if (insertResult <= 0) {
                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + sGl);
                                }
                            } else {
                                throw new Exception(msg);
                            }
                        } else {
                            throw new Exception(lResult);
                        }

                        if (remoteIden.equalsIgnoreCase("true")) {
                            for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                                for (int z = 0; z < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); z++) {
                                    if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getPurCode().equalsIgnoreCase("PUR002")) {
                                        if ((salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getGlCode().equalsIgnoreCase(sSGl))
                                                && (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getShCode() == sKey)) {

                                            recNo = ftsFacade.getRecNo();

                                            String appBranch = "";
                                            if (selectionCriteria.equalsIgnoreCase("BRN")) {
                                                appBranch = selectedValues;
                                            } else {
                                                appBranch = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                                            }

                                            if (salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2).equalsIgnoreCase(appBranch)) {
                                                msg = ibtFacade.cbsPostingCx(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 1, ymd.format(new Date()), salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getSalary(), 0.0, 2,
                                                        "Deduction For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                        0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                                        salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2), salaryProcessingTO.getEnteredBy(),
                                                        authBy, trsNo, "0", "");
                                                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                    throw new Exception(msg);
                                                }

                                                msg = ftsFacade.updateBalance(ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()),
                                                        salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0, salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getSalary(), "Y", "Y");

                                                if (msg.equalsIgnoreCase("true")) {
                                                    int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                            + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                            + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                            + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "',"
                                                            + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "','" + ymd.format(new Date()) + "',"
                                                            + "'" + ymd.format(new Date()) + "',0," + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getSalary() + ",1,2,"
                                                            + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                            + "81 ,0,'A','Deduction','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                            + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',"
                                                            + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();
                                             
                                                    if (insertResult <= 0) {
                                                        throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                    }
                                                } else {
                                                    throw new Exception(msg);
                                                }
                                            } else {
                                                msg = ibtFacade.cbsPostingSx(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 1, ymd.format(new Date()), salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getSalary(), 0.0, 2,
                                                        "Deduction For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                        0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                                        appBranch, salaryProcessingTO.getEnteredBy(),
                                                        authBy, trsNo, "0", "");
                                                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                    throw new Exception(msg);
                                                }

                                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                        + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "',"
                                                        + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "','" + ymd.format(new Date()) + "',"
                                                        + "'" + ymd.format(new Date()) + "',0," + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getSalary() + ",1,2,"
                                                        + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                        + "81 ,0,'A','Deduction','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                        + "'','" + appBranch + "',"
                                                        + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();
                          
                                                if (insertResult <= 0) {
                                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                }
                                                ftsFacade.updateBalance(ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()), salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0, salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getSalary(), "Y", "Y");
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                                for (int z = 0; z < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); z++) {
                                    if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getPurCode().equalsIgnoreCase("PUR002")) {
                                        if ((salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getGlCode().equalsIgnoreCase(sSGl))
                                                && (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getShCode() == sKey)) {

                                            recNo = ftsFacade.getRecNo();
                                            String acNat = ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                            lResult = ftsFacade.insertRecons(acNat, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 1,
                                                    salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getSalary(), ymd.format(new Date()), ymd.format(new Date()), 2,
                                                    "Deduction From Salary", authBy, trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2), 0, null, "", "");

                                            if (lResult.equalsIgnoreCase("true")) {
                                                msg = ftsFacade.updateBalance(acNat, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0, salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getSalary(), "Y", "Y");
                                                if (msg.equalsIgnoreCase("true")) {
                                                    int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                            + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                            + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                                            + "adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "','" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "',"
                                                            + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "',0"
                                                            + "," + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(z).getSalary() + ",1,2," + recNo + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + authBy + "',81 ,0,'A',"
                                                            + "'Deduction  Salary','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',"
                                                            + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();
                                                    
                                                    if (insertResult <= 0) {
                                                        throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                    }
                                                } else {
                                                    throw new Exception(msg);
                                                }
                                            } else {
                                                throw new Exception(lResult);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (loanRecInSal == 1) {
                    String remoteIdenLoan = remoteLoanBranchIdentification(hrSalaryProcessingTOs, selectionCriteria, selectedValues);
                    //float trsLoan = ftsFacade.getTrsNo();
                    if (remoteIden.equalsIgnoreCase("False") && remoteIdenLoan.equalsIgnoreCase("False")) {
                        String prevEmpId = "";
                        for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                            String empId = salaryProcessingTO.getHrPersonnelDetailsTO().getEmpId();
                            if (!prevEmpId.equalsIgnoreCase(empId)) {
                                prevEmpId = empId;
                                String salAc = salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode();
                                List acNoLst = getLoanAcList(salaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), empId);
                                if (!acNoLst.isEmpty()) {
                                    for (int l = 0; l < acNoLst.size(); l++) {
                                        Object[] lLsVec = (Object[]) acNoLst.get(l);
                                        String lAcNo = lLsVec[0].toString();
                                        double emiAmt = Double.parseDouble(lLsVec[1].toString());
                                        float lRecNo = ftsFacade.getRecNo();
                                        String acNat = ftsFacade.getAccountNature(salAc);
                                        String loanResult = ftsFacade.insertRecons(acNat, salAc, 1, emiAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                                                "Recovery for loan " + lAcNo, authBy, trsNo, null, lRecNo, "Y", authBy, 0, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                                salAc.substring(0, 2), lAcNo.substring(0, 2), 0, null, "", "");
                                        if (loanResult.equalsIgnoreCase("true")) {
                                            msg = ftsFacade.updateBalance(acNat, salAc, 0, emiAmt, "Y", "Y");
                                            if (msg.equalsIgnoreCase("true")) {
                                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                                        + "adviceno,advicebrncode) values('" + salAc + "','" + ftsFacade.ftsGetCustName(salAc) + "',"
                                                        + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "',0," + emiAmt + ","
                                                        + "1,2," + lRecNo + ",'',null,3,0,'Y','" + authBy + "',0 ,0,'A',"
                                                        + "'Recovery For Loan','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + salAc.substring(0, 2) + "',"
                                                        + "'" + lAcNo.substring(0, 2) + "',0,'','')").executeUpdate();
                                                
                                                if (insertResult <= 0) {
                                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salAc);
                                                }

                                                lRecNo = ftsFacade.getRecNo();
                                                String lacNat = ftsFacade.getAccountNature(lAcNo);

                                                loanResult = ftsFacade.insertRecons(lacNat, lAcNo, 0, emiAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                                                        "loan Recovery from salary" + salAc, authBy, trsNo, null, lRecNo, "Y", authBy, 0, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                                        salAc.substring(0, 2), lAcNo.substring(0, 2), 0, null, "", "");

                                                msg = ftsFacade.updateBalance(lacNat, lAcNo, emiAmt, 0, "Y", "Y");
                                                if (msg.equalsIgnoreCase("true")) {
                                                    if (loanResult.equalsIgnoreCase("True")) {
                                                        msg = ftsFacade.loanDisbursementInstallment(lAcNo, emiAmt, 0, authBy, ymd.format(new Date()), lRecNo, 0, "");
                                                        if (msg.equalsIgnoreCase("true")) {
                                                            insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                                                    + "adviceno,advicebrncode) values('" + lAcNo + "','" + ftsFacade.ftsGetCustName(lAcNo) + "',"
                                                                    + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "'," + emiAmt + ","
                                                                    + "0,0,2," + lRecNo + ",'',null,3,0,'Y','" + authBy + "',0 ,0,'A',"
                                                                    + "'Recovery For Loan','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + salAc.substring(0, 2) + "',"
                                                                    + "'" + lAcNo.substring(0, 2) + "',0,'','')").executeUpdate();
                                                      
                                                            if (insertResult <= 0) {
                                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + lAcNo);
                                                            }
                                                        } else {
                                                            throw new Exception(msg);
                                                        }
                                                    } else {
                                                        throw new Exception(loanResult);
                                                    }
                                                } else {
                                                    throw new Exception(msg);
                                                }
                                            } else {
                                                throw new Exception(msg);
                                            }
                                        } else {
                                            throw new Exception(loanResult);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        String prevEmpId = "";
                        for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                            String empId = salaryProcessingTO.getHrPersonnelDetailsTO().getEmpId();
                            if (!prevEmpId.equalsIgnoreCase(empId)) {
                                String salAc = salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode();
                                List acNoLst = getLoanAcList(salaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), empId);
                                if (!acNoLst.isEmpty()) {
                                    for (int l = 0; l < acNoLst.size(); l++) {
                                        Object[] lLsVec = (Object[]) acNoLst.get(l);
                                        String lAcNo = lLsVec[0].toString();
                                        double emiAmt = Double.parseDouble(lLsVec[1].toString());
                                        String appBranch = "";
                                        if (selectionCriteria.equalsIgnoreCase("BRN")) {
                                            appBranch = selectedValues;
                                        } else {
                                            appBranch = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                                        }

                                        recNo = ftsFacade.getRecNo();
                                        if (appBranch.equalsIgnoreCase(salAc.substring(0, 2))) {
                                            msg = ibtFacade.cbsPostingCx(salAc, 1, ymd.format(new Date()), emiAmt, emiAmt, 2, "", 0f, "", "",
                                                    null, 3, 0f, recNo, 0, salAc.substring(0, 2), salAc.substring(0, 2), authBy, authBy, trsNo, "", "");

                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }

                                            msg = ftsFacade.updateBalance(ftsFacade.getAccountNature(salAc), salAc, 0, emiAmt, "Y", "Y");
                                            if (msg.equalsIgnoreCase("true")) {
                                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                        + "tran_id,adviceno,advicebrncode) values('" + salAc + "',"
                                                        + "'" + ftsFacade.ftsGetCustName(salAc) + "','" + ymd.format(new Date()) + "',"
                                                        + "'" + ymd.format(new Date()) + "',0," + emiAmt + ",1,2," + recNo + ",'',null,3,0,'Y',"
                                                        + "'" + authBy + "',81 ,0,'A','EMI Recovered','" + authBy + "'," + trsNo + ","
                                                        + "CURRENT_TIMESTAMP,'','" + salAc.substring(0, 2) + "','" + salAc.substring(0, 2) + "',0,'','')").executeUpdate();
                                             
                                               if (insertResult <= 0) {
                                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                }
                                            } else {
                                                throw new Exception(msg);
                                            }
                                        } else {
                                            msg = ibtFacade.cbsPostingSx(salAc, 1, ymd.format(new Date()), emiAmt, emiAmt, 2, "", 0f, "", "", null, 3, 0f,
                                                    recNo, 0, salAc.substring(0, 2), appBranch, authBy, authBy, trsNo, "", "");

                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }

                                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                    + "tran_id,adviceno,advicebrncode) values('" + salAc + "',"
                                                    + "'" + ftsFacade.ftsGetCustName(salAc) + "','" + ymd.format(new Date()) + "',"
                                                    + "'" + ymd.format(new Date()) + "',0," + emiAmt + ",1,2," + recNo + ",'',null,3,0,'Y',"
                                                    + "'" + authBy + "',81 ,0,'A','EMI Recovered','" + authBy + "'," + trsNo + ","
                                                    + "CURRENT_TIMESTAMP,'','" + appBranch + "','" + salAc.substring(0, 2) + "',0,'','')").executeUpdate();
                                                    
                                            if (insertResult <= 0) {
                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                            }
                                            ftsFacade.updateBalance(ftsFacade.getAccountNature(salAc), salAc, 0, emiAmt, "Y", "Y");
                                        }

                                        recNo = ftsFacade.getRecNo();
                                        if (appBranch.equalsIgnoreCase(lAcNo.substring(0, 2))) {
                                            msg = ibtFacade.cbsPostingCx(lAcNo, 0, ymd.format(new Date()), emiAmt, emiAmt, 2, "Installment deduct from the salary for the " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), 0f, "", "", null, 3,
                                                    0f, recNo, 0, lAcNo.substring(0, 2), lAcNo.substring(0, 2), authBy, authBy, trsNo, "", "");

                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }

                                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                    + "tran_id,adviceno,advicebrncode) values('" + lAcNo + "',"
                                                    + "'" + ftsFacade.ftsGetCustName(lAcNo) + "','" + ymd.format(new Date()) + "',"
                                                    + "'" + ymd.format(new Date()) + "'," + emiAmt + ",0,0,2," + recNo + ",'',null,3,0,'Y',"
                                                    + "'" + authBy + "',81 ,0,'A','EMI Recovered','" + authBy + "'," + trsNo + ","
                                                    + "CURRENT_TIMESTAMP,'','" + lAcNo.substring(0, 2) + "','" + lAcNo.substring(0, 2) + "',0,'','')").executeUpdate();
                                         
                                            if (insertResult <= 0) {
                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                            }
                                        } else {
                                            msg = ibtFacade.cbsPostingSx(lAcNo, 0, ymd.format(new Date()), emiAmt, emiAmt, 2, "Installment deduct from the salary for the " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), 0f, "", "", null, 3, 0f,
                                                    recNo, 0, lAcNo.substring(0, 2), appBranch, authBy, authBy, trsNo, "", "");

                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }

                                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                    + "tran_id,adviceno,advicebrncode) values('" + lAcNo + "',"
                                                    + "'" + ftsFacade.ftsGetCustName(lAcNo) + "','" + ymd.format(new Date()) + "',"
                                                    + "'" + ymd.format(new Date()) + "'," + emiAmt + ",0,0,2," + recNo + ",'',null,3,0,'Y',"
                                                    + "'" + authBy + "',81 ,0,'A','EMI Recovered','" + authBy + "'," + trsNo + ","
                                                    + "CURRENT_TIMESTAMP,'','" + appBranch + "','" + lAcNo.substring(0, 2) + "',0,'','')").executeUpdate();
                                            
                                            if (insertResult <= 0) {
                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (grossOrNet == 1) {
                ibtFacade = (InterBranchTxnFacadeRemote) ServiceLocator.getInstance().lookup("InterBranchTxnFacade");
                ftsFacade = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
                SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");

                //GET THE SALARY HEAD TO DEBIT
                String debitGlSalary = "";
                List salGlList = em.createNativeQuery("select acno from abb_parameter_info where purpose = 'SAL_GL_HEAD'").getResultList();
                if (!salGlList.isEmpty()) {
                    debitGlSalary = salGlList.get(0).toString();
                } else {
                    throw new Exception("Please Define Salary Debit GL Head");
                }

                //LOAN RECOVERED FROM SALARY OR NOT   0 : NO , 1: YES
                int loanRecInSal = ftsFacade.getCodeForReportName("LOAN_REC_IN_SAL");

                //PF IN GL OR IN ACCOUNT  0 : IN ACCOUNT , 1: IN GL
                int pfInGl = ftsFacade.getCodeForReportName("PF_IN_GL");

                //PF EMPLOYER CONTRIBUTION  0 : NO , 1: YES
                int pfEmpCont = ftsFacade.getCodeForReportName("PF_EMPLOYER_CONT");

                String pfContHead = "";
                if (pfEmpCont == 1) {
                    //GET THE PF CONTRIBUTION HEAD
                    List pfContHeadList = em.createNativeQuery("select acno from abb_parameter_info where purpose = 'PF_CONT_HEAD'").getResultList();
                    if (!pfContHeadList.isEmpty()) {
                        pfContHead = pfContHeadList.get(0).toString();
                    } else {
                        throw new Exception("Please Define PF Contribution Head");
                    }
                }

                //TAX ON SALARY 0 : NO , 1: YES
                int taxOnSal = ftsFacade.getCodeForReportName("TAX_ON_SAL");

                //GET THE SALARY HEAD TO DEBIT
                String taxGlHead = "";
                List taxGlList = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab"
                        + " where TDS_Applicabledate<='" + ymd.format(new Date()) + "')").getResultList();
                if (!taxGlList.isEmpty()) {
                    taxGlHead = taxGlList.get(0).toString();
                } else {
                    throw new Exception("Please Define TDS GL Head");
                }

                if (selectionCriteria.equalsIgnoreCase("BRN")) {
                    debitGlSalary = selectedValues + debitGlSalary;
                    pfContHead = selectedValues + pfContHead;
                    taxGlHead = selectedValues + taxGlHead;
                } else {
                    String empBrn = "";
                    for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                        empBrn = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                    }
                    debitGlSalary = empBrn + debitGlSalary;
                    pfContHead = empBrn + pfContHead;
                    taxGlHead = empBrn + taxGlHead;
                }

                double totDebitAmt = 0;
                double pfTotAmt = 0;
                double dedTotAmt = 0;
                double dedTotTax = 0;
                String remoteIden = remoteBranchIdentification(hrSalaryProcessingTOs, "S");

                //To Save The Details of The Employee Salary
                for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                    HrSalaryProcessing hrSalProcessing = new HrSalaryProcessing();
                    HrSalaryProcessingPK hrSalProcessPk = new HrSalaryProcessingPK();
                    for (int i = 0; i < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); i++) {
                        HrSalaryProcessingDtl hrSalDtl = new HrSalaryProcessingDtl();
                        //HrSalaryProcessingDtlPK hrSalDtlPk = new HrSalaryProcessingDtlPK();

//                        hrSalDtlPk.setRefNo(salaryProcessingTO.getRefNo());
//                        hrSalDtlPk.setComponentType(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getComponentType());
//                        hrSalDtlPk.setEmpCode(BigInteger.valueOf(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getEmpCode()));
//                        hrSalDtlPk.setCompCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getCompCode());
//                        hrSalDtl.setHrSalaryProcessingDtlPK(hrSalDtlPk);
                        hrSalDtl.setRefNo(salaryProcessingTO.getRefNo());
                        hrSalDtl.setComponentType(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getComponentType());
                        hrSalDtl.setEmpCode(BigInteger.valueOf(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getEmpCode()));
                        hrSalDtl.setCompCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getCompCode());

                        hrSalDtl.setAmount((float) salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary());
                        hrSalDtl.setDefaultComp(salaryProcessingTO.getDefaultComp());
                        hrSalDtl.setDescShortCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode());
                        hrSalDtl.setPurposeCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode());
                        hrSalDtl.setMonth(salaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                        hrSalDtl.setEnteredDate(new Date());
                        hrSalDtl.setMonthStartDate(fstDt);
                        hrSalDtl.setMonthLastDate(lastDt);

                        hrSalDtl.setGlCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getGlCode());
                        HrSalaryProcessingDtlDAO salarydtlDAO = new HrSalaryProcessingDtlDAO(em);
                        salarydtlDAO.save(hrSalDtl);

                        if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode().equalsIgnoreCase("PUR002")) {
                            if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode() == 1) {
                                if (pfEmpCont == 1) {
                                    HrSalaryProcessingDtl hrSalDtl1 = new HrSalaryProcessingDtl();
                                    //HrSalaryProcessingDtlPK hrSalDtlPk1 = new HrSalaryProcessingDtlPK();

//                                    hrSalDtlPk1.setRefNo(salaryProcessingTO.getRefNo());
//                                    hrSalDtlPk1.setComponentType("EMPLOYER PF CONTRIBUTION ");
//                                    hrSalDtlPk1.setEmpCode(BigInteger.valueOf(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getEmpCode()));
//                                    hrSalDtlPk1.setCompCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getCompCode());
//                                    hrSalDtl1.setHrSalaryProcessingDtlPK(hrSalDtlPk1);
                                    hrSalDtl1.setRefNo(salaryProcessingTO.getRefNo());
                                    hrSalDtl1.setComponentType("EMPLOYER PF CONTRIBUTION ");
                                    hrSalDtl1.setEmpCode(BigInteger.valueOf(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getEmpCode()));
                                    hrSalDtl1.setCompCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getCompCode());

                                    hrSalDtl1.setAmount((float) salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary());
                                    hrSalDtl1.setDefaultComp(salaryProcessingTO.getDefaultComp());
                                    hrSalDtl1.setDescShortCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode());
                                    hrSalDtl1.setPurposeCode("PUR004");
                                    hrSalDtl1.setMonth(salaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                                    hrSalDtl1.setEnteredDate(new Date());
                                    hrSalDtl1.setMonthStartDate(fstDt);
                                    hrSalDtl1.setMonthLastDate(lastDt);

                                    hrSalDtl1.setGlCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getGlCode());
                                    HrSalaryProcessingDtlDAO salarydtlDAO1 = new HrSalaryProcessingDtlDAO(em);
                                    salarydtlDAO1.save(hrSalDtl1);

                                    pfTotAmt = pfTotAmt + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary();
                                }
                            }
                            dedTotAmt = dedTotAmt + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary();
                        }
                    }

                    hrSalProcessPk.setEmpCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(0).getHrSalaryProcessingDetailPK().getEmpCode());
                    hrSalProcessPk.setMonths(salaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                    hrSalProcessPk.setCompCode(salaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                    hrSalProcessPk.setCalDateFrom(salaryProcessingTO.getHrSalaryProcessingPK().getCalDateFrom());
                    hrSalProcessPk.setCalDateTo(salaryProcessingTO.getHrSalaryProcessingPK().getCalDateTo());

                    hrSalProcessing.setHrSalaryProcessingPK(hrSalProcessPk);
                    hrSalProcessing.setAuthBy(salaryProcessingTO.getAuthBy());
                    hrSalProcessing.setDeductiveTax(salaryProcessingTO.getDeductiveTax());
                    hrSalProcessing.setDefaultComp(salaryProcessingTO.getDefaultComp());
                    hrSalProcessing.setEnteredBy(salaryProcessingTO.getEnteredBy());
                    hrSalProcessing.setGrossSalary(salaryProcessingTO.getGrossSalary());
                    hrSalProcessing.setModDate(salaryProcessingTO.getModDate());
                    hrSalProcessing.setPostFlag(salaryProcessingTO.getPostFlag());
                    hrSalProcessing.setReceiptFlag(salaryProcessingTO.getReceiptFlag());
                    hrSalProcessing.setRefNo(salaryProcessingTO.getRefNo());
                    hrSalProcessing.setSalary(salaryProcessingTO.getSalary());
                    hrSalProcessing.setStatFlag(salaryProcessingTO.getStatFlag());
                    hrSalProcessing.setStatUpFlag(salaryProcessingTO.getStatUpFlag());

                    HrSalaryProcessingDAO salaryDAO = new HrSalaryProcessingDAO(em);
                    salaryDAO.save(hrSalProcessing);
                    totDebitAmt = totDebitAmt + salaryProcessingTO.getGrossSalary();
                    dedTotTax = dedTotTax + salaryProcessingTO.getDeductiveTax();
                }

                //TranDesc = 81
                float trsNo = ftsFacade.getTrsNo();
                String msg = "";

                //Map<Integer, List<String>> deductMap = new HashMap<Integer, List<String>>();
                Map<Integer, List<String>> deductMap = new ConcurrentHashMap<Integer, List<String>>();

                if (remoteIden.equalsIgnoreCase("TRUE")) {
                    for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                        float recNo = ftsFacade.getRecNo();

                        String appBranch = "";
                        if (selectionCriteria.equalsIgnoreCase("BRN")) {
                            appBranch = selectedValues;
                        } else {
                            appBranch = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                        }

                        if (salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2).equalsIgnoreCase(appBranch)) {
                            TransferSmsRequestTo to = new TransferSmsRequestTo();
                            to.setAcno(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                            to.setAmount(salaryProcessingTO.getSalary());
                            smsList.add(to);

                            msg = ibtFacade.cbsPostingCx(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0, ymd.format(new Date()), salaryProcessingTO.getSalary(), 0.0, 2,
                                    "Salary For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                    salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2), salaryProcessingTO.getEnteredBy(),
                                    authBy, trsNo, "0", "");
                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                throw new Exception(msg);
                            }

                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                    + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "',"
                                    + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "','" + ymd.format(new Date()) + "',"
                                    + "'" + ymd.format(new Date()) + "'," + salaryProcessingTO.getSalary() + ",0,0,2,"
                                    + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                    + "81 ,0,'A','Salary Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                    + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                    + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();
                                    
                            if (insertResult <= 0) {
                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                            }

                        } else {
                            TransferSmsRequestTo to = new TransferSmsRequestTo();
                            to.setAcno(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                            to.setAmount(salaryProcessingTO.getSalary());
                            smsList.add(to);

                            msg = ibtFacade.cbsPostingSx(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0, ymd.format(new Date()), salaryProcessingTO.getSalary(), 0.0, 2,
                                    "Salary For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                    appBranch, salaryProcessingTO.getEnteredBy(),
                                    authBy, trsNo, "0", "");
                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                throw new Exception(msg);
                            }

                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                    + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "',"
                                    + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "','" + ymd.format(new Date()) + "',"
                                    + "'" + ymd.format(new Date()) + "'," + salaryProcessingTO.getSalary() + ",0,0,2,"
                                    + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                    + "81 ,0,'A','Salary Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                    + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                    + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();
                             
                            if (insertResult <= 0) {
                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                            }
                        }

                        for (int m = 0; m < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); m++) {
                            if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getPurCode().equalsIgnoreCase("PUR002")) {
                                if (deductMap.containsKey(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode())) {
                                    for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {
                                        Map.Entry entry = (Map.Entry) it.next();
                                        int sKey = (Integer) entry.getKey();
                                        if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode() == sKey) {
                                            List kLst = (List) entry.getValue();
                                            String sGl = kLst.get(0).toString();
                                            double nVal = Double.parseDouble(kLst.get(1).toString()) + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getSalary();
                                            List glVal = new ArrayList();
                                            glVal.add(sGl);
                                            glVal.add(nVal);

                                            deductMap.remove(sKey);
                                            deductMap.put(sKey, glVal);
                                        }
                                    }
                                } else {
                                    List glVal = new ArrayList();
                                    glVal.add(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getGlCode());
                                    glVal.add(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getSalary());
                                    deductMap.put(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode(), glVal);
                                }
                            }
                        }
                    }
                } else {
                    for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                        float recNo = ftsFacade.getRecNo();
                        String acNature = ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());

                        TransferSmsRequestTo to = new TransferSmsRequestTo();
                        to.setAcno(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                        to.setAmount(salaryProcessingTO.getSalary());
                        smsList.add(to);

                        String lResult = ftsFacade.insertRecons(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0,
                                salaryProcessingTO.getSalary(), ymd.format(new Date()), ymd.format(new Date()), 2,
                                "Salary For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), salaryProcessingTO.getEnteredBy(),
                                trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch(), 0, null, "", "");
                        if (lResult.equalsIgnoreCase("True")) {
                            msg = ftsFacade.updateBalance(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), salaryProcessingTO.getSalary(), 0, "Y", "Y");
                            if (msg.equalsIgnoreCase("True")) {
                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                        + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "',"
                                        + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "','" + ymd.format(new Date()) + "',"
                                        + "'" + ymd.format(new Date()) + "'," + salaryProcessingTO.getSalary() + ",0,0,2,"
                                        + "" + recNo + ",'',null,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                        + "81 ,0,'A','Salary Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                        + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                        + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();
                                 
                                if (insertResult <= 0) {
                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                }
                            } else {
                                throw new Exception(msg);
                            }

                            for (int m = 0; m < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); m++) {
                                if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getPurCode().equalsIgnoreCase("PUR002")) {
                                    if (deductMap.containsKey(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode())) {
                                        for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {
                                            Map.Entry entry = (Map.Entry) it.next();
                                            int sKey = (Integer) entry.getKey();
                                            if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode() == sKey) {
                                                List kLst = (List) entry.getValue();
                                                String sGl = kLst.get(0).toString();
                                                double nVal = Double.parseDouble(kLst.get(1).toString()) + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getSalary();
                                                List glVal = new ArrayList();
                                                glVal.add(sGl);
                                                glVal.add(nVal);

                                                deductMap.remove(sKey);
                                                deductMap.put(sKey, glVal);
                                            }
                                        }
                                    } else {
                                        List glVal = new ArrayList();
                                        glVal.add(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getGlCode());
                                        glVal.add(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getSalary());
                                        deductMap.put(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode(), glVal);
                                    }
                                }
                            }
                        } else {
                            throw new Exception(lResult);
                        }
                    }
                }

                if (pfInGl == 0) {
                    String remoteIdenP = remoteBranchIdentification(hrSalaryProcessingTOs, "P");

                    if (remoteIdenP.equalsIgnoreCase("TRUE")) {
                        double pfAmt = 0d;
                        for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                            String appBranch = "";
                            if (selectionCriteria.equalsIgnoreCase("BRN")) {
                                appBranch = selectedValues;
                            } else {
                                appBranch = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                            }
                            for (int i = 0; i < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); i++) {
                                if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode().equalsIgnoreCase("PUR002")) {
                                    if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode() == 1) {
                                        pfAmt = salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary();

                                        float recNo = ftsFacade.getRecNo();

                                        if (salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2).equalsIgnoreCase(appBranch)) {
                                            msg = ibtFacade.cbsPostingCx(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                    "PF For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2), salaryProcessingTO.getEnteredBy(),
                                                    authBy, trsNo, "0", "");
                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }
                                        } else {
                                            msg = ibtFacade.cbsPostingSx(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                    "PF For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                    appBranch, salaryProcessingTO.getEnteredBy(),
                                                    authBy, trsNo, "0", "");
                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }
                                        }

                                        int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                + "81 ,0,'A','PF Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                                + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();
                                             
                                        if (insertResult <= 0) {
                                            throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                        }

                                        if (pfEmpCont == 1) {
                                            recNo = ftsFacade.getRecNo();

                                            if (salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2).equalsIgnoreCase(appBranch)) {
                                                msg = ibtFacade.cbsPostingCx(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                        "PF Employer Contribution For The Month" + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                        0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                        salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2), salaryProcessingTO.getEnteredBy(),
                                                        authBy, trsNo, "0", "");
                                                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                    throw new Exception(msg);
                                                }
                                            } else {
                                                msg = ibtFacade.cbsPostingSx(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                        "PF Employer Contribution For The Month" + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                        0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                        appBranch, salaryProcessingTO.getEnteredBy(),
                                                        authBy, trsNo, "0", "");
                                                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                    throw new Exception(msg);
                                                }
                                            }

                                            insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                    + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                    + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                    + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                    + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                    + "81 ,0,'A','PF Employer Contribution For The Month Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                    + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                                    + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();
                                        
                                            if (insertResult <= 0) {
                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        double pfAmt = 0d;
                        for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                            for (int i = 0; i < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); i++) {
                                if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode().equalsIgnoreCase("PUR002")) {
                                    if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode() == 1) {
                                        pfAmt = salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary();

                                        float recNo = ftsFacade.getRecNo();
                                        String acNature = ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount());

                                        String lResult = ftsFacade.insertRecons(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0,
                                                pfAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                                                "PF For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), salaryProcessingTO.getEnteredBy(),
                                                trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                                salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch(), 0, null, "", "");
                                        if (lResult.equalsIgnoreCase("True")) {
                                            msg = ftsFacade.updateBalance(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), pfAmt, 0, "Y", "Y");
                                            if (msg.equalsIgnoreCase("True")) {
                                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                        + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                        + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                        + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                        + "" + recNo + ",'',null,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                        + "81 ,0,'A','PF Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                        + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                                        + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();
                                                 
                                               if (insertResult <= 0) {
                                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                }
                                            } else {
                                                throw new Exception(msg);
                                            }
                                        } else {
                                            throw new Exception(lResult);
                                        }

                                        if (pfEmpCont == 1) {
                                            recNo = ftsFacade.getRecNo();
                                            acNature = ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount());

                                            lResult = ftsFacade.insertRecons(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0,
                                                    pfAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                                                    "PF Employer Contribution For The Month" + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), salaryProcessingTO.getEnteredBy(),
                                                    trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch(), 0, null, "", "");
                                            if (lResult.equalsIgnoreCase("True")) {
                                                msg = ftsFacade.updateBalance(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), pfAmt, 0, "Y", "Y");
                                                if (msg.equalsIgnoreCase("True")) {
                                                    int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                            + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                            + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                            + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                            + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                            + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                            + "" + recNo + ",'',null,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                            + "81 ,0,'A','PF Employer Contribution For The Month Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                            + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                                            + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();
                                                           
                                                    if (insertResult <= 0) {
                                                        throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                    }
                                                } else {
                                                    throw new Exception(msg);
                                                }
                                            } else {
                                                throw new Exception(lResult);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                float recNo = ftsFacade.getRecNo();

                String lResult = ftsFacade.insertRecons("PO", debitGlSalary, 1, (totDebitAmt + dedTotAmt), ymd.format(new Date()), ymd.format(new Date()), 2,
                        "Salary For The Month", authBy, trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                        debitGlSalary.substring(0, 2), debitGlSalary.substring(0, 2), 0, null, "", "");

                if (lResult.equalsIgnoreCase("true")) {
                    msg = ftsFacade.updateBalance("PO", debitGlSalary, 0, totDebitAmt, "Y", "Y");
                    if (msg.equalsIgnoreCase("true")) {
                        int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                + "adviceno,advicebrncode) values('" + debitGlSalary + "','" + ftsFacade.ftsGetCustName(debitGlSalary) + "',"
                                + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "',0," + (totDebitAmt + dedTotAmt) + ","
                                + "1,2," + recNo + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + authBy + "',81 ,0,'A',"
                                + "'Salary Posting. Entry','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + debitGlSalary.substring(0, 2) + "',"
                                + "'" + debitGlSalary.substring(0, 2) + "',0,'','')").executeUpdate();
                       
                        if (insertResult <= 0) {
                            throw new Exception("Data insertion problem in transfer scroll for account number: " + debitGlSalary);
                        }
                    } else {
                        throw new Exception(msg);
                    }
                } else {
                    throw new Exception(lResult);
                }

                if (pfEmpCont == 1) {
                    recNo = ftsFacade.getRecNo();

                    lResult = ftsFacade.insertRecons("PO", pfContHead, 1, pfTotAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                            "Employer PF Contribution Entry", authBy, trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                            pfContHead.substring(0, 2), pfContHead.substring(0, 2), 0, null, "", "");

                    if (lResult.equalsIgnoreCase("true")) {
                        msg = ftsFacade.updateBalance("PO", pfContHead, 0, pfTotAmt, "Y", "Y");
                        if (msg.equalsIgnoreCase("true")) {
                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                    + "adviceno,advicebrncode) values('" + pfContHead + "','" + ftsFacade.ftsGetCustName(pfContHead) + "',"
                                    + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "',0," + pfTotAmt + ","
                                    + "1,2," + recNo + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + authBy + "',81 ,0,'A',"
                                    + "'Employer PF Contribution Entry','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + pfContHead.substring(0, 2) + "',"
                                    + "'" + pfContHead.substring(0, 2) + "',0,'','')").executeUpdate();
                              
                            if (insertResult <= 0) {
                                throw new Exception("Data insertion problem in transfer scroll for account number: " + pfContHead);
                            }
                        } else {
                            throw new Exception(msg);
                        }
                    } else {
                        throw new Exception(lResult);
                    }
                }

                if (taxOnSal == 1) {
                    recNo = ftsFacade.getRecNo();

                    lResult = ftsFacade.insertRecons("PO", taxGlHead, 0, dedTotTax, ymd.format(new Date()), ymd.format(new Date()), 2,
                            "Total Tds Entery On Salary", authBy, trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                            taxGlHead.substring(0, 2), taxGlHead.substring(0, 2), 0, null, "", "");

                    if (lResult.equalsIgnoreCase("true")) {
                        msg = ftsFacade.updateBalance("PO", taxGlHead, dedTotTax, 0, "Y", "Y");
                        if (msg.equalsIgnoreCase("true")) {
                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                    + "adviceno,advicebrncode) values('" + taxGlHead + "','" + ftsFacade.ftsGetCustName(taxGlHead) + "',"
                                    + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "'," + dedTotTax + ","
                                    + "0,0,2," + recNo + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + authBy + "',81 ,0,'A',"
                                    + "'Total Tds Entery On Salary','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + taxGlHead.substring(0, 2) + "',"
                                    + "'" + taxGlHead.substring(0, 2) + "',0,'','')").executeUpdate();
                           
                            if (insertResult <= 0) {
                                throw new Exception("Data insertion problem in transfer scroll for account number: " + taxGlHead);
                            }
                        } else {
                            throw new Exception(msg);
                        }
                    } else {
                        throw new Exception(lResult);
                    }
                }

                for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {

                    Map.Entry entry = (Map.Entry) it.next();
                    List kLst = (List) entry.getValue();
                    String sGl = kLst.get(0).toString();
                    double nVal = Double.parseDouble(kLst.get(1).toString());
                    int sKey = (Integer) entry.getKey();
                    if (pfInGl == 1 && sKey == 1) {
                        if ((sGl == null || sGl.equalsIgnoreCase(""))) {
                            throw new Exception("PF GL NOT DEFINED");
                        }
                        nVal = nVal + pfTotAmt;
                    }

                    if (!(sGl == null || sGl.equalsIgnoreCase(""))) {
                        recNo = ftsFacade.getRecNo();

                        if (selectionCriteria.equalsIgnoreCase("BRN")) {
                            sGl = selectedValues + sGl + "01";
                        } else {
                            String empBrn = "";
                            for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                                empBrn = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                            }
                            sGl = empBrn + sGl + "01";
                        }

                        lResult = ftsFacade.insertRecons("PO", sGl, 0, nVal, ymd.format(new Date()), ymd.format(new Date()), 2,
                                "Salary Deduction", authBy, trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                sGl.substring(0, 2), sGl.substring(0, 2), 0, null, "", "");

                        if (lResult.equalsIgnoreCase("true")) {
                            msg = ftsFacade.updateBalance("PO", sGl, nVal, 0, "Y", "Y");
                            if (msg.equalsIgnoreCase("true")) {
                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                        + "adviceno,advicebrncode) values('" + sGl + "','" + ftsFacade.ftsGetCustName(sGl) + "',"
                                        + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "'," + nVal + ","
                                        + "0,0,2," + recNo + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + authBy + "',81 ,0,'A',"
                                        + "'Salary Deduction','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + sGl.substring(0, 2) + "',"
                                        + "'" + sGl.substring(0, 2) + "',0,'','')").executeUpdate();
                               
                                if (insertResult <= 0) {
                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + sGl);
                                }
                            } else {
                                throw new Exception(msg);
                            }
                        } else {
                            throw new Exception(lResult);
                        }
                    }
                }

                if (loanRecInSal == 1) {
                    String remoteIdenLoan = remoteLoanBranchIdentification(hrSalaryProcessingTOs, selectionCriteria, selectedValues);
                    //float trsLoan = ftsFacade.getTrsNo();
                    if (remoteIden.equalsIgnoreCase("False") && remoteIdenLoan.equalsIgnoreCase("False")) {
                        String prevEmpId = "";
                        for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                            String empId = salaryProcessingTO.getHrPersonnelDetailsTO().getEmpId();
                            if (!prevEmpId.equalsIgnoreCase(empId)) {
                                prevEmpId = empId;
                                String salAc = salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode();
                                List acNoLst = getLoanAcList(salaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), empId);
                                if (!acNoLst.isEmpty()) {
                                    for (int l = 0; l < acNoLst.size(); l++) {
                                        Object[] lLsVec = (Object[]) acNoLst.get(l);
                                        String lAcNo = lLsVec[0].toString();
                                        double emiAmt = Double.parseDouble(lLsVec[1].toString());
//                                    String comType = lLsVec[2].toString();
                                        float lRecNo = ftsFacade.getRecNo();
                                        String acNat = ftsFacade.getAccountNature(salAc);
                                        String loanResult = ftsFacade.insertRecons(acNat, salAc, 1, emiAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                                                "Recovery for loan " + lAcNo, authBy, trsNo, null, lRecNo, "Y", authBy, 0, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                                salAc.substring(0, 2), lAcNo.substring(0, 2), 0, null, "", "");
                                        if (loanResult.equalsIgnoreCase("true")) {
                                            msg = ftsFacade.updateBalance(acNat, salAc, 0, emiAmt, "Y", "Y");
                                            if (msg.equalsIgnoreCase("true")) {
                                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                                        + "adviceno,advicebrncode) values('" + salAc + "','" + ftsFacade.ftsGetCustName(salAc) + "',"
                                                        + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "',0," + emiAmt + ","
                                                        + "0,2," + lRecNo + ",'',null,3,0,'Y','" + authBy + "',0 ,0,'A',"
                                                        + "'Recovery For Loan','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + salAc.substring(0, 2) + "',"
                                                        + "'" + lAcNo.substring(0, 2) + "',0,'','')").executeUpdate();
                                           
                                                if (insertResult <= 0) {
                                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salAc);
                                                }

                                                lRecNo = ftsFacade.getRecNo();
                                                String lacNat = ftsFacade.getAccountNature(lAcNo);

                                                loanResult = ftsFacade.insertRecons(lacNat, lAcNo, 0, emiAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                                                        "loan Recovery from salary" + salAc, authBy, trsNo, null, lRecNo, "Y", authBy, 0, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                                        salAc.substring(0, 2), lAcNo.substring(0, 2), 0, null, "", "");

                                                msg = ftsFacade.updateBalance(lacNat, lAcNo, emiAmt, 0, "Y", "Y");
                                                if (msg.equalsIgnoreCase("true")) {
                                                    if (loanResult.equalsIgnoreCase("True")) {
                                                        msg = ftsFacade.loanDisbursementInstallment(lAcNo, emiAmt, 0, authBy, ymd.format(new Date()), lRecNo, 0, "");
                                                        if (msg.equalsIgnoreCase("true")) {
                                                            insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                                                    + "adviceno,advicebrncode) values('" + lAcNo + "','" + ftsFacade.ftsGetCustName(lAcNo) + "',"
                                                                    + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "'," + emiAmt + ","
                                                                    + "0,0,2," + lRecNo + ",'',null,3,0,'Y','" + authBy + "',0 ,0,'A',"
                                                                    + "'Recovery For Loan','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + salAc.substring(0, 2) + "',"
                                                                    + "'" + lAcNo.substring(0, 2) + "',0,'','')").executeUpdate();
                                                             
                                                            if (insertResult <= 0) {
                                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + lAcNo);
                                                            }
                                                        } else {
                                                            throw new Exception(msg);
                                                        }
                                                    } else {
                                                        throw new Exception(loanResult);
                                                    }
                                                } else {
                                                    throw new Exception(msg);
                                                }
                                            } else {
                                                throw new Exception(msg);
                                            }
                                        } else {
                                            throw new Exception(loanResult);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        String prevEmpId = "";
                        for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                            String empId = salaryProcessingTO.getHrPersonnelDetailsTO().getEmpId();
                            if (!prevEmpId.equalsIgnoreCase(empId)) {
                                String salAc = salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode();
                                List acNoLst = getLoanAcList(salaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), empId);
                                if (!acNoLst.isEmpty()) {
                                    for (int l = 0; l < acNoLst.size(); l++) {
                                        Object[] lLsVec = (Object[]) acNoLst.get(l);
                                        String lAcNo = lLsVec[0].toString();
                                        double emiAmt = Double.parseDouble(lLsVec[1].toString());
                                        String appBranch = "";
                                        if (selectionCriteria.equalsIgnoreCase("BRN")) {
                                            appBranch = selectedValues;
                                        } else {
                                            appBranch = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                                        }

                                        recNo = ftsFacade.getRecNo();
                                        if (appBranch.equalsIgnoreCase(salAc.substring(0, 2))) {
                                            msg = ibtFacade.cbsPostingCx(salAc, 1, ymd.format(new Date()), emiAmt, emiAmt, 2, "", 0f, "", "",
                                                    null, 3, 0f, recNo, 0, salAc.substring(0, 2), salAc.substring(0, 2), authBy, authBy, trsNo, "", "");

                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }

                                            msg = ftsFacade.updateBalance(ftsFacade.getAccountNature(salAc), salAc, 0, emiAmt, "Y", "Y");
                                            if (msg.equalsIgnoreCase("true")) {
                                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                        + "tran_id,adviceno,advicebrncode) values('" + salAc + "',"
                                                        + "'" + ftsFacade.ftsGetCustName(salAc) + "','" + ymd.format(new Date()) + "',"
                                                        + "'" + ymd.format(new Date()) + "',0," + emiAmt + ",1,2," + recNo + ",'',null,3,0,'Y',"
                                                        + "'" + authBy + "',81 ,0,'A','EMI Recovered','" + authBy + "'," + trsNo + ","
                                                        + "CURRENT_TIMESTAMP,'','" + salAc.substring(0, 2) + "','" + salAc.substring(0, 2) + "',0,'','')").executeUpdate();
                                                   
                                                if (insertResult <= 0) {
                                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                }
                                            } else {
                                                throw new Exception(msg);
                                            }
                                        } else {
                                            msg = ibtFacade.cbsPostingSx(salAc, 1, ymd.format(new Date()), emiAmt, emiAmt, 2, "", 0f, "", "", null, 3, 0f,
                                                    recNo, 0, salAc.substring(0, 2), appBranch, authBy, authBy, trsNo, "", "");

                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }

                                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                    + "tran_id,adviceno,advicebrncode) values('" + salAc + "',"
                                                    + "'" + ftsFacade.ftsGetCustName(salAc) + "','" + ymd.format(new Date()) + "',"
                                                    + "'" + ymd.format(new Date()) + "',0," + emiAmt + ",1,2," + recNo + ",'',null,3,0,'Y',"
                                                    + "'" + authBy + "',81 ,0,'A','EMI Recovered','" + authBy + "'," + trsNo + ","
                                                    + "CURRENT_TIMESTAMP,'','" + appBranch + "','" + salAc.substring(0, 2) + "',0,'','')").executeUpdate();
                                                
                                            if (insertResult <= 0) {
                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                            }
                                            ftsFacade.updateBalance(ftsFacade.getAccountNature(salAc), salAc, 0, emiAmt, "Y", "Y");
                                        }

                                        recNo = ftsFacade.getRecNo();
                                        if (appBranch.equalsIgnoreCase(lAcNo.substring(0, 2))) {
                                            msg = ibtFacade.cbsPostingCx(lAcNo, 0, ymd.format(new Date()), emiAmt, emiAmt, 2, "Installment deduct from the salary for the " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), 0f, "", "", null, 3,
                                                    0f, recNo, 0, lAcNo.substring(0, 2), lAcNo.substring(0, 2), authBy, authBy, trsNo, "", "");

                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }

                                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                    + "tran_id,adviceno,advicebrncode) values('" + lAcNo + "',"
                                                    + "'" + ftsFacade.ftsGetCustName(lAcNo) + "','" + ymd.format(new Date()) + "',"
                                                    + "'" + ymd.format(new Date()) + "'," + emiAmt + ",0,0,2," + recNo + ",'',null,3,0,'Y',"
                                                    + "'" + authBy + "',81 ,0,'A','EMI Recovered','" + authBy + "'," + trsNo + ","
                                                    + "CURRENT_TIMESTAMP,'','" + lAcNo.substring(0, 2) + "','" + lAcNo.substring(0, 2) + "',0,'','')").executeUpdate();
                                                
                                            if (insertResult <= 0) {
                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                            }
                                        } else {
                                            msg = ibtFacade.cbsPostingSx(lAcNo, 0, ymd.format(new Date()), emiAmt, emiAmt, 2, "Installment deduct from the salary for the " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), 0f, "", "", null, 3, 0f,
                                                    recNo, 0, lAcNo.substring(0, 2), appBranch, authBy, authBy, trsNo, "", "");

                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }

                                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                    + "tran_id,adviceno,advicebrncode) values('" + lAcNo + "',"
                                                    + "'" + ftsFacade.ftsGetCustName(lAcNo) + "','" + ymd.format(new Date()) + "',"
                                                    + "'" + ymd.format(new Date()) + "'," + emiAmt + ",0,0,2," + recNo + ",'',null,3,0,'Y',"
                                                    + "'" + authBy + "',81 ,0,'A','EMI Recovered','" + authBy + "'," + trsNo + ","
                                                    + "CURRENT_TIMESTAMP,'','" + appBranch + "','" + lAcNo.substring(0, 2) + "',0,'','')").executeUpdate();
                                                       
                                            if (insertResult <= 0) {
                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                ibtFacade = (InterBranchTxnFacadeRemote) ServiceLocator.getInstance().lookup("InterBranchTxnFacade");
                ftsFacade = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
                SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");

                //GET THE SALARY HEAD TO DEBIT
                String debitGlSalary = "";
                List salGlList = em.createNativeQuery("select acno from abb_parameter_info where purpose = 'SAL_GL_HEAD'").getResultList();
                if (!salGlList.isEmpty()) {
                    debitGlSalary = salGlList.get(0).toString();
                } else {
                    throw new Exception("Please Define Salary Debit GL Head");
                }

                //LOAN RECOVERED FROM SALARY OR NOT   0 : NO , 1: YES
                int loanRecInSal = ftsFacade.getCodeForReportName("LOAN_REC_IN_SAL");

                //PF IN GL OR IN ACCOUNT  0 : IN ACCOUNT , 1: IN GL
                int pfInGl = ftsFacade.getCodeForReportName("PF_IN_GL");

                //PF EMPLOYER CONTRIBUTION  0 : NO , 1: YES
                int pfEmpCont = ftsFacade.getCodeForReportName("PF_EMPLOYER_CONT");

                String pfContHead = "";
                if (pfEmpCont == 1) {
                    //GET THE PF CONTRIBUTION HEAD
                    List pfContHeadList = em.createNativeQuery("select acno from abb_parameter_info where purpose = 'PF_CONT_HEAD'").getResultList();
                    if (!pfContHeadList.isEmpty()) {
                        pfContHead = pfContHeadList.get(0).toString();
                    } else {
                        throw new Exception("Please Define PF Contribution Head");
                    }
                }

                //TAX ON SALARY 0 : NO , 1: YES
                int taxOnSal = ftsFacade.getCodeForReportName("TAX_ON_SAL");

                //GET THE SALARY HEAD TO DEBIT
                String taxGlHead = "";
                List taxGlList = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab"
                        + " where TDS_Applicabledate<='" + ymd.format(new Date()) + "')").getResultList();
                if (!taxGlList.isEmpty()) {
                    taxGlHead = taxGlList.get(0).toString();
                } else {
                    throw new Exception("Please Define TDS GL Head");
                }

                if (selectionCriteria.equalsIgnoreCase("BRN")) {
                    debitGlSalary = selectedValues + debitGlSalary;
                    pfContHead = selectedValues + pfContHead;
                    taxGlHead = selectedValues + taxGlHead;
                } else {
                    String empBrn = "";
                    for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                        empBrn = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                    }
                    debitGlSalary = empBrn + debitGlSalary;
                    pfContHead = empBrn + pfContHead;
                    taxGlHead = empBrn + taxGlHead;
                }

                double totDebitAmt = 0;
                double pfTotAmt = 0;
                double dedTotAmt = 0;
                double dedTotTax = 0;
                String remoteIden = remoteBranchIdentification(hrSalaryProcessingTOs, "S");

                //To Save The Details of The Employee Salary
                for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                    HrSalaryProcessing hrSalProcessing = new HrSalaryProcessing();
                    HrSalaryProcessingPK hrSalProcessPk = new HrSalaryProcessingPK();
                    for (int i = 0; i < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); i++) {
                        HrSalaryProcessingDtl hrSalDtl = new HrSalaryProcessingDtl();
                        //HrSalaryProcessingDtlPK hrSalDtlPk = new HrSalaryProcessingDtlPK();

//                        hrSalDtlPk.setRefNo(salaryProcessingTO.getRefNo());
//                        hrSalDtlPk.setComponentType(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getComponentType());
//                        hrSalDtlPk.setEmpCode(BigInteger.valueOf(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getEmpCode()));
//                        hrSalDtlPk.setCompCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getCompCode());
//                        hrSalDtl.setHrSalaryProcessingDtlPK(hrSalDtlPk);
                        hrSalDtl.setRefNo(salaryProcessingTO.getRefNo());
                        hrSalDtl.setComponentType(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getComponentType());
                        hrSalDtl.setEmpCode(BigInteger.valueOf(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getEmpCode()));
                        hrSalDtl.setCompCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getCompCode());

                        hrSalDtl.setAmount((float) salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary());
                        hrSalDtl.setDefaultComp(salaryProcessingTO.getDefaultComp());
                        hrSalDtl.setDescShortCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode());
                        hrSalDtl.setPurposeCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode());
                        hrSalDtl.setMonth(salaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                        hrSalDtl.setEnteredDate(new Date());
                        hrSalDtl.setMonthStartDate(fstDt);
                        hrSalDtl.setMonthLastDate(lastDt);

                        hrSalDtl.setGlCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getGlCode());
                        HrSalaryProcessingDtlDAO salarydtlDAO = new HrSalaryProcessingDtlDAO(em);
                        salarydtlDAO.save(hrSalDtl);

                        if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode().equalsIgnoreCase("PUR002")) {
                            if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode() == 1) {
                                if (pfEmpCont == 1) {
                                    HrSalaryProcessingDtl hrSalDtl1 = new HrSalaryProcessingDtl();
                                    //HrSalaryProcessingDtlPK hrSalDtlPk1 = new HrSalaryProcessingDtlPK();

//                                    hrSalDtlPk1.setRefNo(salaryProcessingTO.getRefNo());
//                                    hrSalDtlPk1.setComponentType("EMPLOYER PF CONTRIBUTION ");
//                                    hrSalDtlPk1.setEmpCode(BigInteger.valueOf(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getEmpCode()));
//                                    hrSalDtlPk1.setCompCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getCompCode());
//                                    hrSalDtl1.setHrSalaryProcessingDtlPK(hrSalDtlPk1);
                                    hrSalDtl1.setRefNo(salaryProcessingTO.getRefNo());
                                    hrSalDtl1.setComponentType("EMPLOYER PF CONTRIBUTION ");
                                    hrSalDtl1.setEmpCode(BigInteger.valueOf(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getEmpCode()));
                                    hrSalDtl1.setCompCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getHrSalaryProcessingDetailPK().getCompCode());
                                    hrSalDtl1.setAmount((float) salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary());
                                    hrSalDtl1.setDefaultComp(salaryProcessingTO.getDefaultComp());
                                    hrSalDtl1.setDescShortCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode());
                                    hrSalDtl1.setPurposeCode("PUR004");
                                    hrSalDtl1.setMonth(salaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                                    hrSalDtl1.setEnteredDate(new Date());
                                    hrSalDtl1.setMonthStartDate(fstDt);
                                    hrSalDtl1.setMonthLastDate(lastDt);

                                    hrSalDtl1.setGlCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getGlCode());
                                    HrSalaryProcessingDtlDAO salarydtlDAO1 = new HrSalaryProcessingDtlDAO(em);
                                    salarydtlDAO1.save(hrSalDtl1);

                                    pfTotAmt = pfTotAmt + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary();
                                }
                            }
                            dedTotAmt = dedTotAmt + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary();
                        }
                    }

                    hrSalProcessPk.setEmpCode(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(0).getHrSalaryProcessingDetailPK().getEmpCode());
                    hrSalProcessPk.setMonths(salaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                    hrSalProcessPk.setCompCode(salaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                    hrSalProcessPk.setCalDateFrom(salaryProcessingTO.getHrSalaryProcessingPK().getCalDateFrom());
                    hrSalProcessPk.setCalDateTo(salaryProcessingTO.getHrSalaryProcessingPK().getCalDateTo());
                    hrSalProcessing.setHrSalaryProcessingPK(hrSalProcessPk);
                    hrSalProcessing.setAuthBy(salaryProcessingTO.getAuthBy());
                    hrSalProcessing.setDeductiveTax(salaryProcessingTO.getDeductiveTax());
                    hrSalProcessing.setDefaultComp(salaryProcessingTO.getDefaultComp());
                    hrSalProcessing.setEnteredBy(salaryProcessingTO.getEnteredBy());
                    hrSalProcessing.setGrossSalary(salaryProcessingTO.getGrossSalary());
                    hrSalProcessing.setModDate(salaryProcessingTO.getModDate());
                    hrSalProcessing.setPostFlag(salaryProcessingTO.getPostFlag());
                    hrSalProcessing.setReceiptFlag(salaryProcessingTO.getReceiptFlag());
                    hrSalProcessing.setRefNo(salaryProcessingTO.getRefNo());
                    hrSalProcessing.setSalary(salaryProcessingTO.getSalary());
                    hrSalProcessing.setStatFlag(salaryProcessingTO.getStatFlag());
                    hrSalProcessing.setStatUpFlag(salaryProcessingTO.getStatUpFlag());

                    HrSalaryProcessingDAO salaryDAO = new HrSalaryProcessingDAO(em);
                    salaryDAO.save(hrSalProcessing);
                    totDebitAmt = totDebitAmt + salaryProcessingTO.getGrossSalary();
                    dedTotTax = dedTotTax + salaryProcessingTO.getDeductiveTax();
                }

                //TranDesc = 81
                float trsNo = ftsFacade.getTrsNo();
                String msg = "";

                //Map<Integer, List<String>> deductMap = new HashMap<Integer, List<String>>();
                Map<Integer, List<String>> deductMap = new ConcurrentHashMap<Integer, List<String>>();

                if (remoteIden.equalsIgnoreCase("TRUE")) {
                    for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                        float recNo = ftsFacade.getRecNo();

                        String appBranch = "";
                        if (selectionCriteria.equalsIgnoreCase("BRN")) {
                            appBranch = selectedValues;
                        } else {
                            appBranch = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                        }

                        if (salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2).equalsIgnoreCase(appBranch)) {
                            TransferSmsRequestTo to = new TransferSmsRequestTo();
                            to.setAcno(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                            to.setAmount(salaryProcessingTO.getSalary());
                            smsList.add(to);

                            msg = ibtFacade.cbsPostingCx(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0, ymd.format(new Date()), salaryProcessingTO.getSalary(), 0.0, 2,
                                    "Salary For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                    salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2), salaryProcessingTO.getEnteredBy(),
                                    authBy, trsNo, "0", "");
                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                throw new Exception(msg);
                            }
                        } else {
                            TransferSmsRequestTo to = new TransferSmsRequestTo();
                            to.setAcno(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                            to.setAmount(salaryProcessingTO.getSalary());
                            smsList.add(to);

                            msg = ibtFacade.cbsPostingSx(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0, ymd.format(new Date()), salaryProcessingTO.getSalary(), 0.0, 2,
                                    "Salary For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                    appBranch, salaryProcessingTO.getEnteredBy(),
                                    authBy, trsNo, "0", "");
                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                throw new Exception(msg);
                            }
                        }

                        int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "',"
                                + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "','" + ymd.format(new Date()) + "',"
                                + "'" + ymd.format(new Date()) + "'," + salaryProcessingTO.getSalary() + ",0,0,2,"
                                + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                + "81 ,0,'A','Salary Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();
                              
                        if (insertResult <= 0) {
                            throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                        }

                        for (int m = 0; m < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); m++) {
                            if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getPurCode().equalsIgnoreCase("PUR002")) {
                                if (deductMap.containsKey(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode())) {
                                    for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {
                                        Map.Entry entry = (Map.Entry) it.next();
                                        int sKey = (Integer) entry.getKey();
                                        if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode() == sKey) {
                                            List kLst = (List) entry.getValue();
                                            String sGl = kLst.get(0).toString();
                                            double nVal = Double.parseDouble(kLst.get(1).toString()) + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getSalary();
                                            List glVal = new ArrayList();
                                            glVal.add(sGl);
                                            glVal.add(nVal);

                                            deductMap.remove(sKey);
                                            deductMap.put(sKey, glVal);
                                        }
                                    }
                                } else {
                                    List glVal = new ArrayList();
                                    glVal.add(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getGlCode());
                                    glVal.add(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getSalary());
                                    deductMap.put(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode(), glVal);
                                }
                            }
                        }
                    }
                } else {
                    for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                        float recNo = ftsFacade.getRecNo();
                        String acNature = ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());

                        TransferSmsRequestTo to = new TransferSmsRequestTo();
                        to.setAcno(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                        to.setAmount(salaryProcessingTO.getSalary());
                        smsList.add(to);

                        String lResult = ftsFacade.insertRecons(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), 0,
                                salaryProcessingTO.getSalary(), ymd.format(new Date()), ymd.format(new Date()), 2,
                                "Salary For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), salaryProcessingTO.getEnteredBy(),
                                trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2),
                                salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch(), 0, null, "", "");
                        if (lResult.equalsIgnoreCase("True")) {
                            msg = ftsFacade.updateBalance(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode(), salaryProcessingTO.getSalary(), 0, "Y", "Y");
                            if (msg.equalsIgnoreCase("True")) {
                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                        + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode() + "',"
                                        + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode()) + "','" + ymd.format(new Date()) + "',"
                                        + "'" + ymd.format(new Date()) + "'," + salaryProcessingTO.getSalary() + ",0,0,2,"
                                        + "" + recNo + ",'',null,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                        + "81 ,0,'A','Salary Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                        + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                        + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2) + "',0,'','')").executeUpdate();
                                    
                                if (insertResult <= 0) {
                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                }
                            } else {
                                throw new Exception(msg);
                            }

                            for (int m = 0; m < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); m++) {
                                if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getPurCode().equalsIgnoreCase("PUR002")) {
                                    if (deductMap.containsKey(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode())) {
                                        for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {
                                            Map.Entry entry = (Map.Entry) it.next();
                                            int sKey = (Integer) entry.getKey();
                                            if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode() == sKey) {
                                                List kLst = (List) entry.getValue();
                                                String sGl = kLst.get(0).toString();
                                                double nVal = Double.parseDouble(kLst.get(1).toString()) + salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getSalary();
                                                List glVal = new ArrayList();
                                                glVal.add(sGl);
                                                glVal.add(nVal);

                                                deductMap.remove(sKey);
                                                deductMap.put(sKey, glVal);
                                            }
                                        }
                                    } else {
                                        List glVal = new ArrayList();
                                        glVal.add(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getGlCode());
                                        glVal.add(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getSalary());
                                        deductMap.put(salaryProcessingTO.getHrSalaryProcessingDetailTO().get(m).getShCode(), glVal);
                                    }
                                }
                            }
                        } else {
                            throw new Exception(lResult);
                        }
                    }
                }

                if (pfInGl == 0) {
                    String remoteIdenP = remoteBranchIdentification(hrSalaryProcessingTOs, "P");

                    if (remoteIdenP.equalsIgnoreCase("TRUE")) {
                        double pfAmt = 0d;
                        for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                            String appBranch = "";
                            if (selectionCriteria.equalsIgnoreCase("BRN")) {
                                appBranch = selectedValues;
                            } else {
                                appBranch = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                            }
                            for (int i = 0; i < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); i++) {
                                if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode().equalsIgnoreCase("PUR002")) {
                                    if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode() == 1) {
                                        pfAmt = salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary();

                                        float recNo = ftsFacade.getRecNo();

                                        if (salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2).equalsIgnoreCase(appBranch)) {
                                            msg = ibtFacade.cbsPostingCx(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                    "PF For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2), salaryProcessingTO.getEnteredBy(),
                                                    authBy, trsNo, "0", "");
                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }
                                        } else {
                                            msg = ibtFacade.cbsPostingSx(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                    "PF For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                    0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                    appBranch, salaryProcessingTO.getEnteredBy(),
                                                    authBy, trsNo, "0", "");
                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }
                                        }

                                        int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                + "81 ,0,'A','PF Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                                + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();
                                              
                                        if (insertResult <= 0) {
                                            throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                        }

                                        if (pfEmpCont == 1) {
                                            recNo = ftsFacade.getRecNo();

                                            if (salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2).equalsIgnoreCase(appBranch)) {
                                                msg = ibtFacade.cbsPostingCx(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                        "PF Employer Contribution For The Month" + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                        0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                        salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2), salaryProcessingTO.getEnteredBy(),
                                                        authBy, trsNo, "0", "");
                                                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                    throw new Exception(msg);
                                                }
                                            } else {
                                                msg = ibtFacade.cbsPostingSx(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0, ymd.format(new Date()), pfAmt, 0.0, 2,
                                                        "PF Employer Contribution For The Month" + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(),
                                                        0f, "", "", "", 3, 0f, recNo, 81, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                        appBranch, salaryProcessingTO.getEnteredBy(),
                                                        authBy, trsNo, "0", "");
                                                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                    throw new Exception(msg);
                                                }
                                            }

                                            insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                    + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                    + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                    + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                    + "" + recNo + ",'',NULL,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                    + "81 ,0,'A','PF Employer Contribution For The Month Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                    + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                                    + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();
                                            
                                            if (insertResult <= 0) {
                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        double pfAmt = 0d;
                        for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                            for (int i = 0; i < salaryProcessingTO.getHrSalaryProcessingDetailTO().size(); i++) {
                                if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getPurCode().equalsIgnoreCase("PUR002")) {
                                    if (salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getShCode() == 1) {
                                        pfAmt = salaryProcessingTO.getHrSalaryProcessingDetailTO().get(i).getSalary();

                                        float recNo = ftsFacade.getRecNo();
                                        String acNature = ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount());

                                        String lResult = ftsFacade.insertRecons(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0,
                                                pfAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                                                "PF For The Month " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), salaryProcessingTO.getEnteredBy(),
                                                trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                                salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch(), 0, null, "", "");
                                        if (lResult.equalsIgnoreCase("True")) {
                                            msg = ftsFacade.updateBalance(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), pfAmt, 0, "Y", "Y");
                                            if (msg.equalsIgnoreCase("True")) {
                                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                        + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                        + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                        + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                        + "" + recNo + ",'',null,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                        + "81 ,0,'A','PF Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                        + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                                        + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();

                                                if (insertResult <= 0) {
                                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                }
                                            } else {
                                                throw new Exception(msg);
                                            }
                                        } else {
                                            throw new Exception(lResult);
                                        }

                                        if (pfEmpCont == 1) {
                                            recNo = ftsFacade.getRecNo();
                                            acNature = ftsFacade.getAccountNature(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount());

                                            lResult = ftsFacade.insertRecons(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), 0,
                                                    pfAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                                                    "PF Employer Contribution For The Month" + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), salaryProcessingTO.getEnteredBy(),
                                                    trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2),
                                                    salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch(), 0, null, "", "");
                                            if (lResult.equalsIgnoreCase("True")) {
                                                msg = ftsFacade.updateBalance(acNature, salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount(), pfAmt, 0, "Y", "Y");
                                                if (msg.equalsIgnoreCase("True")) {
                                                    int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                            + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                            + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                            + "tran_id,adviceno,advicebrncode) values('" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount() + "',"
                                                            + "'" + ftsFacade.ftsGetCustName(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount()) + "','" + ymd.format(new Date()) + "',"
                                                            + "'" + ymd.format(new Date()) + "'," + pfAmt + ",0,0,2,"
                                                            + "" + recNo + ",'',null,3,0,'Y','" + salaryProcessingTO.getEnteredBy() + "',"
                                                            + "81 ,0,'A','PF Employer Contribution For The Month Posted','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                                                            + "'','" + salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch() + "',"
                                                            + "'" + salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2) + "',0,'','')").executeUpdate();
                                                    
                                                    if (insertResult <= 0) {
                                                        throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                                    }
                                                } else {
                                                    throw new Exception(msg);
                                                }
                                            } else {
                                                throw new Exception(lResult);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                float recNo = ftsFacade.getRecNo();

                String lResult = ftsFacade.insertRecons("PO", debitGlSalary, 1, (totDebitAmt + dedTotAmt), ymd.format(new Date()), ymd.format(new Date()), 2,
                        "Salary For The Month", authBy, trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                        debitGlSalary.substring(0, 2), debitGlSalary.substring(0, 2), 0, null, "", "");

                if (lResult.equalsIgnoreCase("true")) {
                    msg = ftsFacade.updateBalance("PO", debitGlSalary, 0, totDebitAmt, "Y", "Y");
                    if (msg.equalsIgnoreCase("true")) {
                        int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                + "adviceno,advicebrncode) values('" + debitGlSalary + "','" + ftsFacade.ftsGetCustName(debitGlSalary) + "',"
                                + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "',0," + (totDebitAmt + dedTotAmt) + ","
                                + "1,2," + recNo + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + authBy + "',81 ,0,'A',"
                                + "'Salary Posting. Entry','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + debitGlSalary.substring(0, 2) + "',"
                                + "'" + debitGlSalary.substring(0, 2) + "',0,'','')").executeUpdate();
                                     
                        if (insertResult <= 0) {
                            throw new Exception("Data insertion problem in transfer scroll for account number: " + debitGlSalary);
                        }
                    } else {
                        throw new Exception(msg);
                    }
                } else {
                    throw new Exception(lResult);
                }

                if (pfEmpCont == 1) {
                    recNo = ftsFacade.getRecNo();

                    lResult = ftsFacade.insertRecons("PO", pfContHead, 1, pfTotAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                            "Employer PF Contribution Entry", authBy, trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                            pfContHead.substring(0, 2), pfContHead.substring(0, 2), 0, null, "", "");

                    if (lResult.equalsIgnoreCase("true")) {
                        msg = ftsFacade.updateBalance("PO", pfContHead, 0, pfTotAmt, "Y", "Y");
                        if (msg.equalsIgnoreCase("true")) {
                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                    + "adviceno,advicebrncode) values('" + pfContHead + "','" + ftsFacade.ftsGetCustName(pfContHead) + "',"
                                    + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "',0," + pfTotAmt + ","
                                    + "1,2," + recNo + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + authBy + "',81 ,0,'A',"
                                    + "'Employer PF Contribution Entry','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + pfContHead.substring(0, 2) + "',"
                                    + "'" + pfContHead.substring(0, 2) + "',0,'','')").executeUpdate();
                                       
                            if (insertResult <= 0) {
                                throw new Exception("Data insertion problem in transfer scroll for account number: " + pfContHead);
                            }
                        } else {
                            throw new Exception(msg);
                        }
                    } else {
                        throw new Exception(lResult);
                    }
                }

                if (taxOnSal == 1) {
                    recNo = ftsFacade.getRecNo();

                    lResult = ftsFacade.insertRecons("PO", taxGlHead, 0, dedTotTax, ymd.format(new Date()), ymd.format(new Date()), 2,
                            "Total Tds Entery On Salary", authBy, trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                            taxGlHead.substring(0, 2), taxGlHead.substring(0, 2), 0, null, "", "");

                    if (lResult.equalsIgnoreCase("true")) {
                        msg = ftsFacade.updateBalance("PO", taxGlHead, dedTotTax, 0, "Y", "Y");
                        if (msg.equalsIgnoreCase("true")) {
                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                    + "adviceno,advicebrncode) values('" + taxGlHead + "','" + ftsFacade.ftsGetCustName(taxGlHead) + "',"
                                    + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "'," + dedTotTax + ","
                                    + "0,0,2," + recNo + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + authBy + "',81 ,0,'A',"
                                    + "'Total Tds Entery On Salary','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + taxGlHead.substring(0, 2) + "',"
                                    + "'" + taxGlHead.substring(0, 2) + "',0,'','')").executeUpdate();
                                   
                            if (insertResult <= 0) {
                                throw new Exception("Data insertion problem in transfer scroll for account number: " + taxGlHead);
                            }
                        } else {
                            throw new Exception(msg);
                        }
                    } else {
                        throw new Exception(lResult);
                    }
                }

                for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {

                    Map.Entry entry = (Map.Entry) it.next();
                    List kLst = (List) entry.getValue();
                    String sGl = kLst.get(0).toString();
                    double nVal = Double.parseDouble(kLst.get(1).toString());
                    int sKey = (Integer) entry.getKey();
                    if (pfInGl == 1 && sKey == 1) {
                        if ((sGl == null || sGl.equalsIgnoreCase(""))) {
                            throw new Exception("PF GL NOT DEFINED");
                        }
                        nVal = nVal + pfTotAmt;
                    }

                    if (!(sGl == null || sGl.equalsIgnoreCase(""))) {
                        recNo = ftsFacade.getRecNo();

                        if (selectionCriteria.equalsIgnoreCase("BRN")) {
                            sGl = selectedValues + sGl + "01";
                        } else {
                            String empBrn = "";
                            for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                                empBrn = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                            }
                            sGl = empBrn + sGl + "01";
                        }

                        lResult = ftsFacade.insertRecons("PO", sGl, 0, nVal, ymd.format(new Date()), ymd.format(new Date()), 2,
                                "Salary Deduction", authBy, trsNo, null, recNo, "Y", authBy, 81, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                sGl.substring(0, 2), sGl.substring(0, 2), 0, null, "", "");

                        if (lResult.equalsIgnoreCase("true")) {
                            msg = ftsFacade.updateBalance("PO", sGl, nVal, 0, "Y", "Y");
                            if (msg.equalsIgnoreCase("true")) {
                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                        + "adviceno,advicebrncode) values('" + sGl + "','" + ftsFacade.ftsGetCustName(sGl) + "',"
                                        + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "'," + nVal + ","
                                        + "0,0,2," + recNo + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + authBy + "',81 ,0,'A',"
                                        + "'Salary Deduction','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + sGl.substring(0, 2) + "',"
                                        + "'" + sGl.substring(0, 2) + "',0,'','')").executeUpdate();
                                  
                                if (insertResult <= 0) {
                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + sGl);
                                }
                            } else {
                                throw new Exception(msg);
                            }
                        } else {
                            throw new Exception(lResult);
                        }
                    }
                }

                if (loanRecInSal == 1) {
                    String remoteIdenLoan = remoteLoanBranchIdentification(hrSalaryProcessingTOs, selectionCriteria, selectedValues);
                    //float trsLoan = ftsFacade.getTrsNo();
                    if (remoteIden.equalsIgnoreCase("False") && remoteIdenLoan.equalsIgnoreCase("False")) {
                        String prevEmpId = "";
                        for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                            String empId = salaryProcessingTO.getHrPersonnelDetailsTO().getEmpId();
                            if (!prevEmpId.equalsIgnoreCase(empId)) {
                                prevEmpId = empId;
                                String salAc = salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode();
                                List acNoLst = getLoanAcList(salaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), empId);
                                if (!acNoLst.isEmpty()) {
                                    for (int l = 0; l < acNoLst.size(); l++) {
                                        Object[] lLsVec = (Object[]) acNoLst.get(l);
                                        String lAcNo = lLsVec[0].toString();
                                        double emiAmt = Double.parseDouble(lLsVec[1].toString());
//                                    String comType = lLsVec[2].toString();
                                        float lRecNo = ftsFacade.getRecNo();
                                        //String acNat = ftsFacade.getAccountNature(salAc);
//                                        String loanResult = ftsFacade.insertRecons(acNat, salAc, 1, emiAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
//                                                "Recovery for loan " + lAcNo, authBy, trsNo, null, lRecNo, "Y", authBy, 0, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
//                                                salAc.substring(0, 2), lAcNo.substring(0, 2), 0, null, "", "");
//                                        if (loanResult.equalsIgnoreCase("true")) {
//                                            msg = ftsFacade.updateBalance(acNat, salAc, 0, emiAmt, "Y", "Y");
//                                            if (msg.equalsIgnoreCase("true")) {
//                                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
//                                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
//                                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
//                                                        + "adviceno,advicebrncode) values('" + salAc + "','" + ftsFacade.ftsGetCustName(salAc) + "',"
//                                                        + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "',0," + emiAmt + ","
//                                                        + "0,2," + lRecNo + ",'',null,3,0,'Y','" + authBy + "',0 ,0,'A',"
//                                                        + "'Recovery For Loan','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + salAc.substring(0, 2) + "',"
//                                                        + "'" + lAcNo.substring(0, 2) + "',0,'','')").executeUpdate();
//
//                                                if (insertResult <= 0) {
//                                                    throw new Exception("Data insertion problem in transfer scroll for account number: " + salAc);
//                                                }

//                                                lRecNo = ftsFacade.getRecNo();
                                        String lacNat = ftsFacade.getAccountNature(lAcNo);

                                        String loanResult = ftsFacade.insertRecons(lacNat, lAcNo, 0, emiAmt, ymd.format(new Date()), ymd.format(new Date()), 2,
                                                "loan Recovery from salary" + salAc, authBy, trsNo, null, lRecNo, "Y", authBy, 0, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                                salAc.substring(0, 2), lAcNo.substring(0, 2), 0, null, "", "");
                                        if (loanResult.equalsIgnoreCase("True")) {
                                            msg = ftsFacade.updateBalance(lacNat, lAcNo, emiAmt, 0, "Y", "Y");
                                            if (msg.equalsIgnoreCase("true")) {
                                                msg = ftsFacade.loanDisbursementInstallment(lAcNo, emiAmt, 0, authBy, ymd.format(new Date()), lRecNo, 0, "");
                                                if (msg.equalsIgnoreCase("true")) {
                                                    int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                            + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                            + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                                                            + "adviceno,advicebrncode) values('" + lAcNo + "','" + ftsFacade.ftsGetCustName(lAcNo) + "',"
                                                            + "'" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "'," + emiAmt + ","
                                                            + "0,0,2," + lRecNo + ",'',null,3,0,'Y','" + authBy + "',0 ,0,'A',"
                                                            + "'Recovery For Loan','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'','" + salAc.substring(0, 2) + "',"
                                                            + "'" + lAcNo.substring(0, 2) + "',0,'','')").executeUpdate();
                                                    
                                                    if (insertResult <= 0) {
                                                        throw new Exception("Data insertion problem in transfer scroll for account number: " + lAcNo);
                                                    }
                                                } else {
                                                    throw new Exception(msg);
                                                }
                                            } else {
                                                throw new Exception(msg);
                                            }
                                        } else {
                                            throw new Exception(loanResult);
                                        }
//                                            } else {
//                                                throw new Exception(msg);
//                                            }
//                                        } else {
//                                            throw new Exception(loanResult);
//                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        String prevEmpId = "";
                        for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                            String empId = salaryProcessingTO.getHrPersonnelDetailsTO().getEmpId();
                            if (!prevEmpId.equalsIgnoreCase(empId)) {
                                String salAc = salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode();
                                List acNoLst = getLoanAcList(salaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), empId);
                                if (!acNoLst.isEmpty()) {
                                    for (int l = 0; l < acNoLst.size(); l++) {
                                        Object[] lLsVec = (Object[]) acNoLst.get(l);
                                        String lAcNo = lLsVec[0].toString();
                                        double emiAmt = Double.parseDouble(lLsVec[1].toString());
                                        String appBranch = "";
                                        if (selectionCriteria.equalsIgnoreCase("BRN")) {
                                            appBranch = selectedValues;
                                        } else {
                                            appBranch = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                                        }

//                                        recNo = ftsFacade.getRecNo();
//                                        if (appBranch.equalsIgnoreCase(salAc.substring(0, 2))) {
//                                            msg = ibtFacade.cbsPostingCx(salAc, 1, ymd.format(new Date()), emiAmt, emiAmt, 2, "", 0f, "", "",
//                                                    null, 3, 0f, recNo, 0, salAc.substring(0, 2), salAc.substring(0, 2), authBy, authBy, trsNo, "", "");
//
//                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
//                                                throw new Exception(msg);
//                                            }
//
//                                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
//                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
//                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
//                                                    + "tran_id,adviceno,advicebrncode) values('" + salAc + "',"
//                                                    + "'" + ftsFacade.ftsGetCustName(salAc) + "','" + ymd.format(new Date()) + "',"
//                                                    + "'" + ymd.format(new Date()) + "',0," + emiAmt + ",1,2," + recNo + ",'',null,3,0,'Y',"
//                                                    + "'" + authBy + "',81 ,0,'A','EMI Recovered','" + authBy + "'," + trsNo + ","
//                                                    + "CURRENT_TIMESTAMP,'','" + salAc.substring(0, 2) + "','" + salAc.substring(0, 2) + "',0,'','')").executeUpdate();
//
//                                            if (insertResult <= 0) {
//                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
//                                            }
//                                        } else {
//                                            msg = ibtFacade.cbsPostingSx(salAc, 1, ymd.format(new Date()), emiAmt, emiAmt, 2, "", 0f, "", "", null, 3, 0f,
//                                                    recNo, 0, salAc.substring(0, 2), appBranch, authBy, authBy, trsNo, "", "");
//
//                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
//                                                throw new Exception(msg);
//                                            }
//
//                                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
//                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
//                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
//                                                    + "tran_id,adviceno,advicebrncode) values('" + salAc + "',"
//                                                    + "'" + ftsFacade.ftsGetCustName(salAc) + "','" + ymd.format(new Date()) + "',"
//                                                    + "'" + ymd.format(new Date()) + "',0," + emiAmt + ",1,2," + recNo + ",'',null,3,0,'Y',"
//                                                    + "'" + authBy + "',81 ,0,'A','EMI Recovered','" + authBy + "'," + trsNo + ","
//                                                    + "CURRENT_TIMESTAMP,'','" + appBranch + "','" + salAc.substring(0, 2) + "',0,'','')").executeUpdate();
//
//                                            if (insertResult <= 0) {
//                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
//                                            }
//                                        }

                                        recNo = ftsFacade.getRecNo();
                                        if (appBranch.equalsIgnoreCase(lAcNo.substring(0, 2))) {
                                            msg = ibtFacade.cbsPostingCx(lAcNo, 0, ymd.format(new Date()), emiAmt, emiAmt, 2, "Installment deduct from the salary for the " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), 0f, "", "", null, 3,
                                                    0f, recNo, 0, lAcNo.substring(0, 2), lAcNo.substring(0, 2), authBy, authBy, trsNo, "", "");

                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }

                                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                    + "tran_id,adviceno,advicebrncode) values('" + lAcNo + "',"
                                                    + "'" + ftsFacade.ftsGetCustName(lAcNo) + "','" + ymd.format(new Date()) + "',"
                                                    + "'" + ymd.format(new Date()) + "'," + emiAmt + ",0,0,2," + recNo + ",'',null,3,0,'Y',"
                                                    + "'" + authBy + "',81 ,0,'A','EMI Recovered','" + authBy + "'," + trsNo + ","
                                                    + "CURRENT_TIMESTAMP,'','" + lAcNo.substring(0, 2) + "','" + lAcNo.substring(0, 2) + "',0,'','')").executeUpdate();
                                                        
                                            if (insertResult <= 0) {
                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                            }
                                        } else {
                                            msg = ibtFacade.cbsPostingSx(lAcNo, 0, ymd.format(new Date()), emiAmt, emiAmt, 2, "Installment deduct from the salary for the " + salaryProcessingTO.getHrSalaryProcessingPK().getMonths(), 0f, "", "", null, 3, 0f,
                                                    recNo, 0, lAcNo.substring(0, 2), appBranch, authBy, authBy, trsNo, "", "");

                                            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                throw new Exception(msg);
                                            }

                                            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                                    + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                                    + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                                    + "tran_id,adviceno,advicebrncode) values('" + lAcNo + "',"
                                                    + "'" + ftsFacade.ftsGetCustName(lAcNo) + "','" + ymd.format(new Date()) + "',"
                                                    + "'" + ymd.format(new Date()) + "'," + emiAmt + ",0,0,2," + recNo + ",'',null,3,0,'Y',"
                                                    + "'" + authBy + "',81 ,0,'A','EMI Recovered','" + authBy + "'," + trsNo + ","
                                                    + "CURRENT_TIMESTAMP,'','" + appBranch + "','" + lAcNo.substring(0, 2) + "',0,'','')").executeUpdate();
                                                     
                                            if (insertResult <= 0) {
                                                throw new Exception("Data insertion problem in transfer scroll for account number: " + salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method postSalaryProcessing()", e);
            ut.rollback();
            throw new Exception(e.getMessage());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for postSalaryProcessing is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        
        ut.commit();
         try {
            for (TransferSmsRequestTo to : smsList) {
//                System.out.println("Account No Is-->" + to.getAcno());
                smsRemote.sendTransactionalSms(SmsType.SALARY_DEPOSIT, to.getAcno(), 2, 0, to.getAmount(), dmy.format(new Date()), "");
            }
        } catch (Exception ex) {
            System.out.println("Problem In Sending The Salary SMS For:-");
        }
        
        //SMS sending of salary
       return "True";
    }

    public String remoteBranchIdentification(List<HrSalaryProcessingTO> hrSalaryProcessingTOs, String opt) {
        String msg = "FALSE";
        if (opt.equalsIgnoreCase("P")) {
            for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                if (!salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().equalsIgnoreCase("")) {
                    if (!(salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch().equalsIgnoreCase(salaryProcessingTO.getHrPersonnelDetailsTO().getPfAccount().substring(0, 2)))) {
                        msg = "TRUE";
                        return msg;
                    } else {
                        msg = "FALSE";
                    }
                }
            }
        } else {
            for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                if (!salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().equalsIgnoreCase("")) {
                    if (!(salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch().equalsIgnoreCase(salaryProcessingTO.getHrPersonnelDetailsTO().getBankAccountCode().substring(0, 2)))) {
                        msg = "TRUE";
                        return msg;
                    } else {
                        msg = "FALSE";
                    }
                }
            }
        }
        return msg;
    }

    public String remoteLoanBranchIdentification(List<HrSalaryProcessingTO> hrSalaryProcessingTOs, String selectionCriteria, String selectedValues) {
        String msg = "FALSE";
        try {
            for (HrSalaryProcessingTO salaryProcessingTO : hrSalaryProcessingTOs) {
                String empId = salaryProcessingTO.getHrPersonnelDetailsTO().getEmpId();
                List acNoLst = getLoanAcList(salaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), empId);
                String appBranch = "";
                if (selectionCriteria.equalsIgnoreCase("BRN")) {
                    appBranch = selectedValues;
                } else {
                    appBranch = salaryProcessingTO.getHrPersonnelDetailsTO().getBaseBranch();
                }
                for (int i = 0; i < acNoLst.size(); i++) {
                    Object[] lLsVec = (Object[]) acNoLst.get(i);
                    String lAcno = lLsVec[0].toString();
                    if (!(appBranch.equalsIgnoreCase(lAcno.substring(0, 2)))) {
                        msg = "TRUE";
                        return msg;
                    } else {
                        msg = "FALSE";
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method postSalaryProcessing()", e);
        }
        return msg;
    }

    /**
     * Salary processed for the employees .
     *
     * @param hrSalaryProcessingTO
     * @param selectionCriteria
     * @param selectedValues
     * @param mode
     * @return
     * @throws ApplicationException
     */
//    @Override
//    public List<HrSalaryProcessingTO> salaryCalculation(HrSalaryProcessingTO hrSalaryProcessingTO, String selectionCriteria, String selectedValues, String month) throws ApplicationException {
//        long begin = System.nanoTime();
//        List<HrSalaryProcessingTO> salaryList = new ArrayList<HrSalaryProcessingTO>();
//        try {
//            HrPersonnelDetailsDAO personnelDAO = new HrPersonnelDetailsDAO(em);
//            HrSalaryProcessingDAO salaryDAO = new HrSalaryProcessingDAO(em);
//            
//            HrAttendanceDetailsDAO attendanceDetailsDAO = new HrAttendanceDetailsDAO(em);
//            HrSalaryAllocationDAO salaryAllocationDAO = new HrSalaryAllocationDAO(em);
//            
//            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
//            
//            List<HrPersonnelDetails> empList = new ArrayList<HrPersonnelDetails>();
//            if (selectionCriteria.equalsIgnoreCase("EWE")) {
//                HrPersonnelDetails personnelDetails = personnelDAO.findByEmpStatusAndEmpId(hrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), selectedValues, 'Y');
//                empList.add(personnelDetails);
//            } else {
//                empList = personnelDAO.findEntityEmpStatusY(hrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), selectedValues);
//            }
//            if (empList.isEmpty()) {
//                throw new ApplicationException("Either Employee does not exist or Employee is not active.");
//            }
//            HrPayrollCalendar hrPayrollCalendar = new HrPayrollCalendar();
//            HrPayrollCalendarPK hrPayrollCalendarPK = new HrPayrollCalendarPK();
//            
//            hrPayrollCalendarPK.setCompCode(hrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
//            hrPayrollCalendarPK.setDateFrom(hrSalaryProcessingTO.getHrSalaryProcessingPK().getCalDateFrom()); //Set 1 April of Financial Year Start
//
//            hrPayrollCalendarPK.setDateTo(hrSalaryProcessingTO.getHrSalaryProcessingPK().getCalDateTo()); //Set 31 March of Financial Year End
//            hrPayrollCalendar.setHrPayrollCalendarPK(hrPayrollCalendarPK);
//            hrSalaryProcessingTO.setHrPayrollCalendarTO(PayrollObjectAdaptor.adaptToPayrollCalendarTO(hrPayrollCalendar));
//            
//            for (HrPersonnelDetails personnelDetails : empList) {
//                
//                hrSalaryProcessingTO.getHrSalaryProcessingPK().setEmpCode(personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());
//                hrSalaryProcessingTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(personnelDetails));
//                
//                List<HrSalaryProcessing> hrSalaryProcessingList = salaryDAO.getSalaryForTheMonthWithPostFlag(PayrollObjectAdaptor.adaptToHrSalaryProcessingEntity(hrSalaryProcessingTO));
//                if (!hrSalaryProcessingList.isEmpty()) {
//                    throw new ApplicationException("Salary already posted for month " + hrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths() + " and for employee " + personnelDetails.getEmpName());
//                }
//                HrSalaryAllocationPK salaryAllocationPK = new HrSalaryAllocationPK();
//                
////                salaryAllocationPK.setCalDateFrom(dmy.format(hrSalaryProcessingTO.getHrSalaryProcessingPK().getCalDateFrom())); //Set 1 April of Financial Year Start
////                salaryAllocationPK.setCalDateTo(dmy.format(hrSalaryProcessingTO.getHrSalaryProcessingPK().getCalDateTo())); //Set 31 March of Financial Year End
//                salaryAllocationPK.setCompCode(hrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode()); //90
//
//                salaryAllocationPK.setEmpCode(personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());
////                salaryAllocationPK.setMonths(hrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths());
//                
//                List<HrSalaryAllocation> salaryAllocationList = salaryAllocationDAO.getSalaryAllocateForMonth(salaryAllocationPK);
//                if (salaryAllocationList.isEmpty()) {
//                    throw new ApplicationException("you have not allocated the salary for month " + hrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths() + " and for employee " + personnelDetails.getEmpName());
//                }
//                HrAttendanceDetailsPK hrAttendetailsPK = new HrAttendanceDetailsPK();
//                HrAttendanceDetails hrAttenDetails = new HrAttendanceDetails();
////                hrAttendetailsPK.setAttenMonth(salaryAllocationPK.getMonths());
//
////                hrAttendetailsPK.setAttenYear(Integer.parseInt(dmy.format(new Date()).substring(6, 10)));
//                hrAttendetailsPK.setAttenYear(Integer.parseInt(hrSalaryProcessingTO.getYear()));
//                hrAttendetailsPK.setCompCode(salaryAllocationPK.getCompCode());
//                hrAttendetailsPK.setEmpCode(salaryAllocationPK.getEmpCode());
//                
//                hrAttenDetails.setPostFlag('Y');
//                hrAttenDetails.setHrAttendanceDetailsPK(hrAttendetailsPK);
//                
//                List<HrAttendanceDetails> hrAttendanceDetailsList = attendanceDetailsDAO.findAttendanceDetailsPostedForMonth(hrAttenDetails);
//                if (hrAttendanceDetailsList.isEmpty()) {
//                    throw new ApplicationException("Attendance is not posted for month " + hrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths() + " and for employee " + personnelDetails.getEmpName());
//                }
//                HrAttendanceDetails attendanceDetails = hrAttendanceDetailsList.get(0);
//                int absentDays = attendanceDetails.getAbsentDays();
//                long overTimePd = attendanceDetails.getOverTimePeriod();
//                String overTimeunit = attendanceDetails.getOverTimeUnit();
//                
//                List salaryAmtList = salaryAllocationDAO.getSalaryAmtOfEmpForMonth(salaryAllocationPK, "PUR001");
//                double allowancesAmt = 0d;
//                for (int i = 0; i < salaryAmtList.size(); i++) {
//                    allowancesAmt = allowancesAmt + Double.parseDouble(salaryAmtList.get(i).toString());
//                }
//                
//                salaryAmtList = salaryAllocationDAO.getSalaryAmtOfEmpForMonth(salaryAllocationPK, "PUR002");
//                double deductionAmt = 0d;
//                for (int i = 0; i < salaryAmtList.size(); i++) {
//                    deductionAmt = deductionAmt + Double.parseDouble(salaryAmtList.get(i).toString());
//                }
//                
//                List<HrSalaryProcessing> preTaxAndSalaryList = salaryDAO.getTaxProcessEmpForYearWithPostFlag(salaryAllocationPK);
//                int salaryPostedMonths = 0;
//                if (!preTaxAndSalaryList.isEmpty()) {
//                    salaryPostedMonths = preTaxAndSalaryList.size();
//                }
//                double preSalaryAmt = 0d;
//                double deductedTaxAmt = 0d;
//                for (HrSalaryProcessing taxObj : preTaxAndSalaryList) {
//                    preSalaryAmt = preSalaryAmt + taxObj.getGrossSalary();
//                    deductedTaxAmt = deductedTaxAmt + taxObj.getDeductiveTax();
//                }
//                List<HrTaxInvestmentCategory> taxInvCatList = salaryDAO.findByCompCodeAndEmpCode(personnelDetails.getHrPersonnelDetailsPK().getCompCode(), personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());
//                double totalInvestmentAmt = 0d;
//                for (HrTaxInvestmentCategory taxInvCat : taxInvCatList) {
//                    if (taxInvCat.getCategoryAmt() > taxInvCat.getCategoryMaxLimit()) {
//                        totalInvestmentAmt = totalInvestmentAmt + taxInvCat.getCategoryMaxLimit();
//                    } else {
//                        totalInvestmentAmt = totalInvestmentAmt + taxInvCat.getCategoryAmt();
//                    }
//                }
//                //Math.min(Math.min(a, b), c);
//                List<HrTaxSlabMaster> taxSlabList = salaryDAO.findByCompCodeAndTaxFor(personnelDetails.getHrPersonnelDetailsPK().getCompCode(), String.valueOf(personnelDetails.getSex()));
//                
//                //Date dt = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(salaryAllocationPK.getMonths());
//                Date dt = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(dt);
//                
//                int monthDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//                double monthSalary = (allowancesAmt - deductionAmt) - (((allowancesAmt - deductionAmt) / monthDays) * absentDays);
//                double overTimeSalary = 0d;
//                
//                if (overTimeunit.equalsIgnoreCase("HOUR")) {
//                    overTimeSalary = (((allowancesAmt - deductionAmt) / monthDays) * (overTimePd / 24));
//                    monthSalary = monthSalary + overTimeSalary;
//                } else {
//                    overTimeSalary = (((allowancesAmt - deductionAmt) / monthDays) * (overTimePd / (24 * 60)));
//                    monthSalary = monthSalary + overTimeSalary;
//                }
//                
//                double totalAnnualSalary = preSalaryAmt + monthSalary + (allowancesAmt - deductionAmt) * (11 - salaryPostedMonths);
//                double taxableSalary = totalAnnualSalary - totalInvestmentAmt;
//                double totalIncomeTax = 0d;
//                for (HrTaxSlabMaster taxSlab : taxSlabList) {
//                    if (taxableSalary > taxSlab.getRangeTo()) {
//                        totalIncomeTax = totalIncomeTax + (taxSlab.getRangeTo() * taxSlab.getApplicableTax()) / 100;
//                        taxableSalary = taxableSalary - taxSlab.getRangeTo();
//                    } else {
//                        totalIncomeTax = totalIncomeTax + (taxableSalary * taxSlab.getApplicableTax()) / 100;
//                        break;
//                    }
//                }
//                double remaingTaxAmt = totalIncomeTax - deductedTaxAmt;
//                double monthlyTaxAmt = Math.ceil(remaingTaxAmt / (12 - salaryPostedMonths));
//                double monthlyNetSalary = monthSalary - monthlyTaxAmt;
//                
//                NumberFormat formatter = new DecimalFormat("#.##");
//                
//                HrSalaryProcessing hrSalaryProcessing = PayrollObjectAdaptor.adaptToHrSalaryProcessingEntity(hrSalaryProcessingTO);
//                hrSalaryProcessing.setSalary(Double.parseDouble(formatter.format(monthlyNetSalary)));
//                hrSalaryProcessing.setGrossSalary(Double.parseDouble(formatter.format(monthSalary)));
//                hrSalaryProcessing.setDeductiveTax(Double.parseDouble(formatter.format(monthlyTaxAmt)));
//                hrSalaryProcessing.setPostFlag('Y');
//                
//                salaryList.add(PayrollObjectAdaptor.adaptToHrSalaryProcessingTO(hrSalaryProcessing));
//            }
//        } catch (Exception e) {
//            logger.error("Exception occured while executing method insertSalaryProcessing()", e);
//            throw new ApplicationException(e.getMessage());
//        }
//        if (logger.isDebugEnabled()) {
//            logger.debug("Execution time for insertSalaryProcessing is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
//        }
//        return salaryList;
//    }
    @Override
    public List<HrSalaryProcessingTO> salaryCalculation(HrSalaryProcessingTO comingHrSalaryProcessingTO, String selectionCriteria, String selectedValues, String month) throws Exception {
        long begin = System.nanoTime();
        List<HrSalaryProcessingTO> salaryList = new ArrayList<HrSalaryProcessingTO>();
        try {
            HrPersonnelDetailsDAO personnelDAO = new HrPersonnelDetailsDAO(em);
            HrSalaryProcessingDAO salaryDAO = new HrSalaryProcessingDAO(em);
            HrAttendanceDetailsDAO attendanceDetailsDAO = new HrAttendanceDetailsDAO(em);
            HrSalaryAllocationDAO salaryAllocationDAO = new HrSalaryAllocationDAO(em);

            int dayInMonth = 0;
            List dateCntList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report "
                    + " where reportname = 'DAYS_IN_MONTH'").getResultList();
            if (!dateCntList.isEmpty()) {
                Vector vecMonth = (Vector) dateCntList.get(0);
                dayInMonth = Integer.parseInt(vecMonth.get(0).toString());
            }

            int taxOnSal = 0;
            List taxSalList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report "
                    + " where reportname = 'TAX_ON_SAL'").getResultList();
            if (!taxSalList.isEmpty()) {
                taxOnSal = Integer.parseInt(taxSalList.get(0).toString());
            }

            //GROOS POSTING IN ACCOUNT OR NET POSTING 0 : GROSS , 1: NET 
            int grossOrNet = ftsFacade.getCodeForReportName("GROSS_OR_NET");

            List<HrPersonnelDetails> empList = new ArrayList<>();
            if (selectionCriteria.equalsIgnoreCase("EWE")) {
                HrPersonnelDetails personnelDetails = personnelDAO.findByEmpStatusAndEmpId(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), selectedValues, 'Y');
                empList.add(personnelDetails);
            } else {
                empList = personnelDAO.findEntityEmpStatusY(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), selectedValues);
            }
            if (empList.isEmpty()) {
                throw new Exception("Either Employee does not exist or Employee is not active.");
            }

            String prevEmpId = "";
            for (HrPersonnelDetails personnelDetails : empList) {

                HrSalaryProcessingTO goingHrSalaryProcessingTO = new HrSalaryProcessingTO();

                HrSalaryProcessingPKTO goingHrsalaryProcessingPKTO = new HrSalaryProcessingPKTO();
                goingHrsalaryProcessingPKTO.setCalDateFrom(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCalDateFrom());
                goingHrsalaryProcessingPKTO.setCalDateTo(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCalDateTo());
                goingHrsalaryProcessingPKTO.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                goingHrsalaryProcessingPKTO.setMonths(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                goingHrSalaryProcessingTO.setHrSalaryProcessingPK(goingHrsalaryProcessingPKTO);

                goingHrSalaryProcessingTO.setAuthBy(comingHrSalaryProcessingTO.getAuthBy());
                goingHrSalaryProcessingTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                goingHrSalaryProcessingTO.setEnteredBy(comingHrSalaryProcessingTO.getEnteredBy());

                goingHrSalaryProcessingTO.setStatFlag("Y");
                goingHrSalaryProcessingTO.setStatUpFlag("Y");
                goingHrSalaryProcessingTO.setModDate(new Date());
                goingHrSalaryProcessingTO.setYear(comingHrSalaryProcessingTO.getYear());

                List refNoList = em.createNativeQuery("select ifnull(max(ref_no),0)+1 from hr_salary_processing").getResultList();
                int refNo = Integer.valueOf(refNoList.get(0).toString());

                comingHrSalaryProcessingTO.getHrSalaryProcessingPK().setEmpCode(personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());
                goingHrSalaryProcessingTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(personnelDetails));

                List<HrSalaryProcessing> hrSalaryProcessingList = salaryDAO.getSalaryForTheMonthWithPostFlag(PayrollObjectAdaptor.adaptToHrSalaryProcessingEntity(goingHrSalaryProcessingTO));
                if (!hrSalaryProcessingList.isEmpty()) {
                    throw new Exception("Salary already posted for month " + comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths() + " and for employee " + personnelDetails.getEmpName());
                }
                HrSalaryAllocationPK salaryAllocationPK = new HrSalaryAllocationPK();
                salaryAllocationPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode()); //90
                salaryAllocationPK.setEmpCode(personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());

                List<HrSalaryAllocation> salaryAllocationList = salaryAllocationDAO.getSalaryAllocateForMonth(salaryAllocationPK);
                if (salaryAllocationList.isEmpty()) {
                    throw new Exception("you have not allocated the salary for month " + comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths() + " and for employee " + personnelDetails.getEmpName());
                }
                HrAttendanceDetailsPK hrAttendetailsPK = new HrAttendanceDetailsPK();
                HrAttendanceDetails hrAttenDetails = new HrAttendanceDetails();

                hrAttendetailsPK.setAttenMonth(month);
                hrAttendetailsPK.setAttenYear(Integer.parseInt(comingHrSalaryProcessingTO.getYear()));
                hrAttendetailsPK.setCompCode(salaryAllocationPK.getCompCode());
                hrAttendetailsPK.setEmpCode(salaryAllocationPK.getEmpCode());

                hrAttenDetails.setPostFlag('Y');
                hrAttenDetails.setHrAttendanceDetailsPK(hrAttendetailsPK);

                List<HrAttendanceDetails> hrAttendanceDetailsList = attendanceDetailsDAO.findAttendanceDetailsPostedForMonth(hrAttenDetails);
                if (hrAttendanceDetailsList.isEmpty()) {
                    throw new Exception("Attendance is not posted for month " + comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths() + " and for employee " + personnelDetails.getEmpName());
                }

                HrAttendanceDetails attendanceDetails = hrAttendanceDetailsList.get(0);
                int absentDays = attendanceDetails.getAbsentDays();
                long overTimePd = attendanceDetails.getOverTimePeriod();
                String overTimeunit = attendanceDetails.getOverTimeUnit();
                double deductDays = attendanceDetails.getDeductDays();

                int monthDays;
                if (dayInMonth == 0) {
                    monthDays = 30;
                } else {
                    Date dt = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dt);
                    monthDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                }

                double actualDays = monthDays - (absentDays + deductDays);

                List<HrSalaryProcessingDetailTO> hrSalaryProcessingFinalDetailTO = new ArrayList<HrSalaryProcessingDetailTO>();

                List salaryAmtList = salaryAllocationDAO.getSalaryAmtOfEmpForMonth(salaryAllocationPK, "PUR001");
                double allowancesAmt = 0d;
                for (int i = 0; i < salaryAmtList.size(); i++) {
                    Object[] objArray = (Object[]) salaryAmtList.get(i);
                    double totAmt = (Double.parseDouble(objArray[0].toString()) * actualDays) / monthDays;
                    String comType = objArray[1].toString().toString();
                    String glCode = objArray[2] == null ? "" : objArray[2].toString().toString();
                    int shCode = Integer.parseInt(objArray[3].toString().toString());
                    HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                    HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                    hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                    hrSalaryProcessingDetailPK.setComponentType(comType);
                    hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                    hrSalaryProcessingDetailPK.setRefNo(refNo);

                    hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                    hrSalaryProcessingDetailTO.setSalary(Math.round(totAmt));
                    hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                    hrSalaryProcessingDetailTO.setGlCode(glCode);
                    hrSalaryProcessingDetailTO.setShCode(shCode);
                    hrSalaryProcessingDetailTO.setPurCode("PUR001");

                    hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                    allowancesAmt = allowancesAmt + Math.round(totAmt);
                }

                salaryAmtList = salaryAllocationDAO.getSalaryAmtOfEmpForMonth(salaryAllocationPK, "PUR002");
                double deductionAmt = 0d;
                for (int i = 0; i < salaryAmtList.size(); i++) {
                    Object[] objArray = (Object[]) salaryAmtList.get(i);
                    double totDedAmt = (Double.parseDouble(objArray[0].toString()) * actualDays) / monthDays;
                    String comType = objArray[1].toString().toString();
                    String glCode = objArray[2] == null ? "" : objArray[2].toString().toString();
                    int shCode = Integer.parseInt(objArray[3].toString().toString());

                    HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                    HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                    hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                    hrSalaryProcessingDetailPK.setComponentType(comType);
                    hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                    hrSalaryProcessingDetailPK.setRefNo(refNo);
                    hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                    hrSalaryProcessingDetailTO.setSalary(Math.round(totDedAmt));
                    hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                    hrSalaryProcessingDetailTO.setGlCode(glCode);
                    hrSalaryProcessingDetailTO.setShCode(shCode);
                    hrSalaryProcessingDetailTO.setPurCode("PUR002");

                    hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);

                    deductionAmt = deductionAmt + Math.round(totDedAmt);
                }

                int loanRecInSal = ftsFacade.getCodeForReportName("LOAN_REC_IN_SAL");
                if (loanRecInSal == 1) {
                    if (!prevEmpId.equalsIgnoreCase(personnelDetails.getEmpId())) {
                        prevEmpId = personnelDetails.getEmpId();

                        List acNoLst = getLoanAcList(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(),
                                personnelDetails.getEmpId());
                        if (!acNoLst.isEmpty()) {
                            for (int l = 0; l < acNoLst.size(); l++) {
                                Object[] lLsVec = (Object[]) acNoLst.get(l);

                                String lAcNo = lLsVec[0].toString();
                                double emiAmt = Double.parseDouble(lLsVec[1].toString());
                                String compType = lLsVec[2].toString();

                                HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                                HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                                hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                hrSalaryProcessingDetailPK.setComponentType(compType);
                                hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                                hrSalaryProcessingDetailPK.setRefNo(refNo);
                                hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                hrSalaryProcessingDetailTO.setSalary(Math.round(emiAmt));
                                hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                                hrSalaryProcessingDetailTO.setGlCode("");
                                hrSalaryProcessingDetailTO.setShCode(0);

                                if (grossOrNet == 1) {
                                    hrSalaryProcessingDetailTO.setPurCode("PUR003");
                                } else if ((grossOrNet == 2 || (grossOrNet == 0))) {
                                } else if ((grossOrNet == 2 || (grossOrNet == 0))) {
                                    hrSalaryProcessingDetailTO.setPurCode("PUR002");
                                    deductionAmt = deductionAmt + Math.round(emiAmt);
                                }
                                hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                            }
                        }
                    }
                }

                List<HrSalaryProcessing> preTaxAndSalaryList = salaryDAO.getTaxProcessEmpForYearWithPostFlag(salaryAllocationPK);
                int salaryPostedMonths = 0;
                if (!preTaxAndSalaryList.isEmpty()) {
                    salaryPostedMonths = preTaxAndSalaryList.size();
                }
                double preSalaryAmt = 0d;
                double deductedTaxAmt = 0d;
                for (HrSalaryProcessing taxObj : preTaxAndSalaryList) {
                    preSalaryAmt = preSalaryAmt + taxObj.getGrossSalary();
                    deductedTaxAmt = deductedTaxAmt + taxObj.getDeductiveTax();
                }
                List<HrTaxInvestmentCategory> taxInvCatList = salaryDAO.findByCompCodeAndEmpCode(personnelDetails.getHrPersonnelDetailsPK().getCompCode(), personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());
                double totalInvestmentAmt = 0d;
                for (HrTaxInvestmentCategory taxInvCat : taxInvCatList) {
                    if (taxInvCat.getCategoryAmt() > taxInvCat.getCategoryMaxLimit()) {
                        totalInvestmentAmt = totalInvestmentAmt + taxInvCat.getCategoryMaxLimit();
                    } else {
                        totalInvestmentAmt = totalInvestmentAmt + taxInvCat.getCategoryAmt();
                    }
                }

                List<HrTaxSlabMaster> taxSlabList = salaryDAO.findByCompCodeAndTaxFor(personnelDetails.getHrPersonnelDetailsPK().getCompCode(), String.valueOf(personnelDetails.getSex()));
                double overTimeSalary = 0d, totalAnnualSalary = 0d, monthSalary = 0d;
                if (grossOrNet == 0) {
                    monthSalary = (allowancesAmt + deductionAmt);
                    if (overTimeunit.equalsIgnoreCase("HOUR")) {
                        overTimeSalary = (((allowancesAmt + deductionAmt) / monthDays) * (overTimePd / 24));
                        monthSalary = monthSalary + overTimeSalary;
                    } else {
                        overTimeSalary = (((allowancesAmt + deductionAmt) / monthDays) * (overTimePd / (24 * 60)));
                        monthSalary = monthSalary + overTimeSalary;
                    }
                    totalAnnualSalary = preSalaryAmt + monthSalary + (allowancesAmt + deductionAmt) * (11 - salaryPostedMonths);
                } else {
                    monthSalary = (allowancesAmt - deductionAmt);
                    if (overTimeunit.equalsIgnoreCase("HOUR")) {
                        overTimeSalary = (((allowancesAmt - deductionAmt) / monthDays) * (overTimePd / 24));
                        monthSalary = monthSalary + overTimeSalary;
                    } else {
                        overTimeSalary = (((allowancesAmt - deductionAmt) / monthDays) * (overTimePd / (24 * 60)));
                        monthSalary = monthSalary + overTimeSalary;
                    }
                    totalAnnualSalary = preSalaryAmt + monthSalary + (allowancesAmt - deductionAmt) * (11 - salaryPostedMonths);
                }

                double taxableSalary = totalAnnualSalary - totalInvestmentAmt;
                double totalIncomeTax = 0d;
                for (HrTaxSlabMaster taxSlab : taxSlabList) {
                    if (taxableSalary > taxSlab.getRangeTo()) {
                        totalIncomeTax = totalIncomeTax + (taxSlab.getRangeTo() * taxSlab.getApplicableTax()) / 100;
                        taxableSalary = taxableSalary - taxSlab.getRangeTo();
                    } else {
                        totalIncomeTax = totalIncomeTax + (taxableSalary * taxSlab.getApplicableTax()) / 100;
                        break;
                    }
                }
                double remaingTaxAmt = totalIncomeTax - deductedTaxAmt;
                double monthlyTaxAmt = 0d;
                if (taxOnSal != 0) {
                    monthlyTaxAmt = Math.ceil(remaingTaxAmt / (12 - salaryPostedMonths));
                }
                double monthlyNetSalary = monthSalary - monthlyTaxAmt;

                goingHrSalaryProcessingTO.setRefNo(refNo);
                goingHrSalaryProcessingTO.setGrossSalary(Math.round(monthSalary));
                goingHrSalaryProcessingTO.setSalary(Math.round(monthlyNetSalary));
                goingHrSalaryProcessingTO.setDeductiveTax(Math.round(monthlyTaxAmt));
                goingHrSalaryProcessingTO.setPostFlag('Y');
                goingHrSalaryProcessingTO.setHrSalaryProcessingDetailTO(hrSalaryProcessingFinalDetailTO);
                salaryList.add(goingHrSalaryProcessingTO);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method insertSalaryProcessing()", e);
            throw new Exception(e.getMessage());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for insertSalaryProcessing is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return salaryList;
    }

    @Override
    public List getAllLoanAccountForCustomer(String customerId) throws ApplicationException {
        try {
            return em.createNativeQuery("select ci.custid,ci.acno from customerid ci,cbs_customer_master_detail cd,"
                    + "accountmaster am,accounttypemaster ap where ci.custid=cd.customerid and am.acno=ci.acno "
                    + "and am.accttype=ap.acctcode and am.accstatus<>9 and ap.acctcode in(select acctcode from "
                    + "accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "',"
                    + "'" + CbsConstant.DEMAND_LOAN + "') or (acctnature='" + CbsConstant.CURRENT_AC + "' "
                    + "and accttype<>'CA')) and ci.custid='" + customerId + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public boolean isSalaryAccountExists(String mode, String salaryAccount, String empCode) throws ApplicationException {
        try {
            List list = null;
            if (mode.equalsIgnoreCase("save")) {
                list = em.createNativeQuery("select emp_code from hr_personnel_details where "
                        + "bank_account_code='" + salaryAccount + "'").getResultList();
                if (!list.isEmpty()) {
                    return true;
                }
            } else if (mode.equalsIgnoreCase("update")) {
                list = em.createNativeQuery("select emp_code from hr_personnel_details where "
                        + "bank_account_code='" + salaryAccount + "' and "
                        + "emp_code<> " + new BigInteger(empCode) + "").getResultList();
                if (!list.isEmpty()) {
                    return true;
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return false;
    }

    @Override
    public List getEmiOnEmpIdAndLoanAc(String empId, String loanAc) throws ApplicationException {
        List list = new ArrayList();
        try {
            list = em.createNativeQuery("select loan_ac,enter_by,last_update_by from hr_emp_loan_detail "
                    + "where emp_id='" + empId + "' and loan_ac='" + loanAc + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    @Override
    public String loanAcOperation(List<LoanDetailTo> list, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int n = 0;
            for (LoanDetailTo to : list) {
                String status = to.getStatus();
                if (status.equalsIgnoreCase("add")) {
                    n = em.createNativeQuery("insert into hr_emp_loan_detail(emp_id,loan_ac,default_comp,comp_code,"
                            + "enter_by,enter_date,last_update_by,last_update_date) values('" + to.getEmpId() + "',"
                            + "'" + to.getLoanAc() + "'," + to.getDefCompCode() + "," + to.getCompCode() + ","
                            + "'" + userName + "',now(),'',now())").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in data insertion of loan a/c detail:Error-001");
                    }
                } else if (status.equalsIgnoreCase("modify") || status.equalsIgnoreCase("delete")) {
                    n = em.createNativeQuery("insert into hr_emp_loan_detail_his(emp_id,loan_ac,default_comp,comp_code,"
                            + "enter_by,enter_date,last_update_by,last_update_date) select emp_id,loan_ac,default_comp,"
                            + "comp_code,enter_by,enter_date,last_update_by,last_update_date from "
                            + "hr_emp_loan_detail where emp_id='" + to.getEmpId() + "' and "
                            + "loan_ac='" + to.getLoanAc() + "' and default_comp=" + to.getDefCompCode() + " and comp_code=" + to.getCompCode() + "").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in data insertion of loan a/c detail:Error-002");
                    }
                    if (status.equalsIgnoreCase("modify")) {
                        n = em.createNativeQuery("update hr_emp_loan_detail set emi=" + new BigDecimal(to.getEmi()) + ",status='modify',last_update_by='" + userName + "',last_update_date=now() where emp_id='" + to.getEmpId() + "' and loan_ac='" + to.getLoanAc() + "'").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("Problem in data insertion of loan a/c detail:Error-004");
                        }
                    } else if (status.equalsIgnoreCase("delete")) {
                        n = em.createNativeQuery("delete from hr_emp_loan_detail where "
                                + "emp_id='" + to.getEmpId() + "' and loan_ac='" + to.getLoanAc() + "' "
                                + "and default_comp=" + to.getDefCompCode() + " and comp_code=" + to.getCompCode() + "").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("Problem in data insertion of loan a/c detail:Error-005");
                        }
                    }
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public List getYears(String compCode) throws ApplicationException {
        List yearList = new ArrayList();
        try {
            yearList = em.createNativeQuery("select distinct substring(mindate,1,4),substring(maxdate,1,4) "
                    + "from yearend where brncode = '" + compCode + "' and yearendflag ='N'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return yearList;
    }

    public List getFinYears(String compCode) throws ApplicationException {
        List finYearList = new ArrayList();
        try {
            finYearList = em.createNativeQuery("select date_format(str_to_date(mindate, '%Y%m%d'),'%d/%m/%Y'),"
                    + " date_format(str_to_date(maxdate, '%Y%m%d'),'%d/%m/%Y') from yearend "
                    + " where brncode = '" + compCode + "' and yearendflag ='N'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return finYearList;
    }

    public List getYearList() throws ApplicationException {
        List ylList = new ArrayList();
        List yList = em.createNativeQuery("select distinct Date_format(ENTERED_DATE,\"%Y\") from hr_salary_processing_dtl ").getResultList();

        for (int i = 0; i < yList.size(); i++) {
            ylList.add(new String(yList.get(i).toString()));
        }
        return ylList;
    }

    public List getLoanAcList(int compCode, String empCode) throws ApplicationException {
        List acNoList = new ArrayList();
        try {
            acNoList = em.createNativeQuery("select distinct h.loan_ac,e.INSTALLAMT,am.AcctType from hr_emp_loan_detail h, emidetails e, accountmaster a, accounttypemaster am "
                    + "where h.emp_id = '" + empCode + "' and h.comp_code =" + compCode + " and h.loan_ac = e.acno and e.status ='UNPAID' and e.acno = a.acno and a.Accttype = am.AcctCode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return acNoList;
    }

    @Override
    public List<HrSalaryProcessingTO> salaryCalculationProjection(HrSalaryProcessingTO comingHrSalaryProcessingTO, String selectionCriteria, String selectedValues, String month) throws Exception {
//        long begin = System.nanoTime();
//        List<HrSalaryProcessingTO> salaryList = new ArrayList<HrSalaryProcessingTO>();
//        try {
//            HrPersonnelDetailsDAO personnelDAO = new HrPersonnelDetailsDAO(em);
//            HrSalaryProcessingDAO salaryDAO = new HrSalaryProcessingDAO(em);
//            HrAttendanceDetailsDAO attendanceDetailsDAO = new HrAttendanceDetailsDAO(em);
//            HrSalaryAllocationDAO salaryAllocationDAO = new HrSalaryAllocationDAO(em);
//
//            int dayInMonth = 0;
//            List dateCntList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report "
//                    + " where reportname = 'DAYS_IN_MONTH'").getResultList();
//            if (!dateCntList.isEmpty()) {
//                Vector vecMonth = (Vector) dateCntList.get(0);
//                dayInMonth = Integer.parseInt(vecMonth.get(0).toString());
//            }
//
//            int taxOnSal = 0;
//            List taxSalList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report "
//                    + " where reportname = 'TAX_ON_SAL'").getResultList();
//            if (!taxSalList.isEmpty()) {
//                taxOnSal = Integer.parseInt(taxSalList.get(0).toString());
//            }
//
//            List<HrPersonnelDetails> empList = new ArrayList<HrPersonnelDetails>();
//            if (selectionCriteria.equalsIgnoreCase("EWE")) {
//                HrPersonnelDetails personnelDetails = personnelDAO.findByEmpStatusAndEmpId(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), selectedValues, 'Y');
//                empList.add(personnelDetails);
//            } else {
//                empList = personnelDAO.findEntityEmpStatusY(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), selectedValues);
//            }
//            if (empList.isEmpty()) {
//                throw new Exception("Either Employee does not exist or Employee is not active.");
//            }
//
//            String prevEmpId = "";
//            for (HrPersonnelDetails personnelDetails : empList) {
//
//                HrSalaryProcessingTO goingHrSalaryProcessingTO = new HrSalaryProcessingTO();
//
//                HrSalaryProcessingPKTO goingHrsalaryProcessingPKTO = new HrSalaryProcessingPKTO();
//                goingHrsalaryProcessingPKTO.setCalDateFrom(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCalDateFrom());
//                goingHrsalaryProcessingPKTO.setCalDateTo(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCalDateTo());
//                goingHrsalaryProcessingPKTO.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
//                goingHrsalaryProcessingPKTO.setMonths(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths());
//                goingHrSalaryProcessingTO.setHrSalaryProcessingPK(goingHrsalaryProcessingPKTO);
//
//                goingHrSalaryProcessingTO.setAuthBy(comingHrSalaryProcessingTO.getAuthBy());
//                goingHrSalaryProcessingTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
//                goingHrSalaryProcessingTO.setEnteredBy(comingHrSalaryProcessingTO.getEnteredBy());
//
//                goingHrSalaryProcessingTO.setStatFlag("Y");
//                goingHrSalaryProcessingTO.setStatUpFlag("Y");
//                goingHrSalaryProcessingTO.setModDate(new Date());
//                goingHrSalaryProcessingTO.setYear(comingHrSalaryProcessingTO.getYear());
//
//                List refNoList = em.createNativeQuery("select ifnull(max(ref_no),0)+1 from hr_salary_processing").getResultList();
//                int refNo = Integer.valueOf(refNoList.get(0).toString());
//
//                comingHrSalaryProcessingTO.getHrSalaryProcessingPK().setEmpCode(personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());
//                goingHrSalaryProcessingTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(personnelDetails));
//
//                HrSalaryAllocationPK salaryAllocationPK = new HrSalaryAllocationPK();
//                salaryAllocationPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode()); //90
//                salaryAllocationPK.setEmpCode(personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());
//
//                List<HrSalaryAllocation> salaryAllocationList = salaryAllocationDAO.getSalaryAllocateForMonth(salaryAllocationPK);
//                if (salaryAllocationList.isEmpty()) {
//                    throw new Exception("you have not allocated the salary for month " + comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths() + " and for employee " + personnelDetails.getEmpName());
//                }
//                HrAttendanceDetailsPK hrAttendetailsPK = new HrAttendanceDetailsPK();
//                HrAttendanceDetails hrAttenDetails = new HrAttendanceDetails();
//
//                hrAttendetailsPK.setAttenMonth(month);
//                hrAttendetailsPK.setAttenYear(Integer.parseInt(comingHrSalaryProcessingTO.getYear()));
//                hrAttendetailsPK.setCompCode(salaryAllocationPK.getCompCode());
//                hrAttendetailsPK.setEmpCode(salaryAllocationPK.getEmpCode());
//
//                hrAttenDetails.setPostFlag('Y');
//                hrAttenDetails.setHrAttendanceDetailsPK(hrAttendetailsPK);
//
//                int absentDays = 0;
//                long overTimePd = 0;
//                String overTimeunit = "0";
//                int deductDays = 0;
//                List<HrAttendanceDetails> hrAttendanceDetailsList = attendanceDetailsDAO.findAttendanceDetailsPostedForMonth(hrAttenDetails);
//                if (!hrAttendanceDetailsList.isEmpty()) {
//                    HrAttendanceDetails attendanceDetails = hrAttendanceDetailsList.get(0);
//
//                    absentDays = attendanceDetails.getAbsentDays();
//                    overTimePd = attendanceDetails.getOverTimePeriod();
//                    overTimeunit = attendanceDetails.getOverTimeUnit();
//                    deductDays = attendanceDetails.getDeductDays();
//                }
//
//                int monthDays;
//                if (dayInMonth == 0) {
//                    monthDays = 30;
//                } else {
//                    Date dt = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(dt);
//                    monthDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//                }
//
//                int actualDays = monthDays - (absentDays + deductDays);
//
//                List<HrSalaryProcessingDetailTO> hrSalaryProcessingFinalDetailTO = new ArrayList<HrSalaryProcessingDetailTO>();
//
//                List salaryAmtList = salaryAllocationDAO.getSalaryAmtOfEmpForMonth(salaryAllocationPK, "PUR001");
//                double allowancesAmt = 0d;
//                for (int i = 0; i < salaryAmtList.size(); i++) {
//                    Object[] objArray = (Object[]) salaryAmtList.get(i);
//                    double totAmt = (Double.parseDouble(objArray[0].toString()) * actualDays) / monthDays;
//                    String comType = objArray[1].toString().toString();
//                    String glCode = objArray[2] == null ? "" : objArray[2].toString().toString();
//                    int shCode = Integer.parseInt(objArray[3].toString().toString());
//                    HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
//                    HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
//                    hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
//                    hrSalaryProcessingDetailPK.setComponentType(comType);
//                    hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
//                    hrSalaryProcessingDetailPK.setRefNo(refNo);
//                    hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
//                    hrSalaryProcessingDetailTO.setSalary(Math.round(totAmt));
//                    hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
//                    hrSalaryProcessingDetailTO.setGlCode(glCode);
//                    hrSalaryProcessingDetailTO.setShCode(shCode);
//                    hrSalaryProcessingDetailTO.setPurCode("PUR001");
//
//                    hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
//                    allowancesAmt = allowancesAmt + Math.round(totAmt);
//                }
//
//                salaryAmtList = salaryAllocationDAO.getSalaryAmtOfEmpForMonth(salaryAllocationPK, "PUR002");
//                double deductionAmt = 0d;
//                for (int i = 0; i < salaryAmtList.size(); i++) {
//                    Object[] objArray = (Object[]) salaryAmtList.get(i);
//                    double totDedAmt = (Double.parseDouble(objArray[0].toString()) * actualDays) / monthDays;
//                    String comType = objArray[1].toString().toString();
//                    String glCode = objArray[2] == null ? "" : objArray[2].toString().toString();
//                    int shCode = Integer.parseInt(objArray[3].toString().toString());
//
//                    HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
//                    HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
//                    hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
//                    hrSalaryProcessingDetailPK.setComponentType(comType);
//                    hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
//                    hrSalaryProcessingDetailPK.setRefNo(refNo);
//                    hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
//                    hrSalaryProcessingDetailTO.setSalary(Math.round(totDedAmt));
//                    hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
//                    hrSalaryProcessingDetailTO.setGlCode(glCode);
//                    hrSalaryProcessingDetailTO.setShCode(shCode);
//                    hrSalaryProcessingDetailTO.setPurCode("PUR002");
//
//                    hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
//
//                    deductionAmt = deductionAmt + Math.round(totDedAmt);
//                }
//
//                int loanRecInSal = ftsFacade.getCodeForReportName("LOAN_REC_IN_SAL");
//                if (loanRecInSal == 1) {
//                    if (!prevEmpId.equalsIgnoreCase(personnelDetails.getEmpId())) {
//                        prevEmpId = personnelDetails.getEmpId();
//
//                        List acNoLst = getLoanAcList(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(),
//                                personnelDetails.getEmpId());
//                        if (!acNoLst.isEmpty()) {
//                            for (int l = 0; l < acNoLst.size(); l++) {
//                                Object[] lLsVec = (Object[]) acNoLst.get(l);
//
//                                String lAcNo = lLsVec[0].toString();
//                                double emiAmt = Double.parseDouble(lLsVec[1].toString());
//                                String compType = lLsVec[2].toString();
//
//                                HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
//                                HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
//                                hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
//                                hrSalaryProcessingDetailPK.setComponentType(compType);
//                                hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
//                                hrSalaryProcessingDetailPK.setRefNo(refNo);
//
//                                hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
//                                hrSalaryProcessingDetailTO.setSalary(Math.round(emiAmt));
//                                hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
//                                hrSalaryProcessingDetailTO.setGlCode(lAcNo);
//                                hrSalaryProcessingDetailTO.setShCode(0);
//                                hrSalaryProcessingDetailTO.setPurCode("PUR002");
//
//                                hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
//
//                                deductionAmt = deductionAmt + Math.round(emiAmt);
//                            }
//                        }
//                    }
//                }
//
//                List<HrSalaryProcessing> preTaxAndSalaryList = salaryDAO.getTaxProcessEmpForYearWithPostFlag(salaryAllocationPK);
//                int salaryPostedMonths = 0;
//                if (!preTaxAndSalaryList.isEmpty()) {
//                    salaryPostedMonths = preTaxAndSalaryList.size();
//                }
//                double preSalaryAmt = 0d;
//                double deductedTaxAmt = 0d;
//                for (HrSalaryProcessing taxObj : preTaxAndSalaryList) {
//                    preSalaryAmt = preSalaryAmt + taxObj.getGrossSalary();
//                    deductedTaxAmt = deductedTaxAmt + taxObj.getDeductiveTax();
//                }
//                List<HrTaxInvestmentCategory> taxInvCatList = salaryDAO.findByCompCodeAndEmpCode(personnelDetails.getHrPersonnelDetailsPK().getCompCode(), personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());
//                double totalInvestmentAmt = 0d;
//                for (HrTaxInvestmentCategory taxInvCat : taxInvCatList) {
//                    if (taxInvCat.getCategoryAmt() > taxInvCat.getCategoryMaxLimit()) {
//                        totalInvestmentAmt = totalInvestmentAmt + taxInvCat.getCategoryMaxLimit();
//                    } else {
//                        totalInvestmentAmt = totalInvestmentAmt + taxInvCat.getCategoryAmt();
//                    }
//                }
//
//                List<HrTaxSlabMaster> taxSlabList = salaryDAO.findByCompCodeAndTaxFor(personnelDetails.getHrPersonnelDetailsPK().getCompCode(), String.valueOf(personnelDetails.getSex()));
//                double monthSalary = (allowancesAmt - deductionAmt);
//                double overTimeSalary = 0d;
//
//                if (overTimeunit.equalsIgnoreCase("HOUR")) {
//                    overTimeSalary = (((allowancesAmt - deductionAmt) / monthDays) * (overTimePd / 24));
//                    monthSalary = monthSalary + overTimeSalary;
//                } else {
//                    overTimeSalary = (((allowancesAmt - deductionAmt) / monthDays) * (overTimePd / (24 * 60)));
//                    monthSalary = monthSalary + overTimeSalary;
//                }
//
//                double totalAnnualSalary = preSalaryAmt + monthSalary + (allowancesAmt - deductionAmt) * (11 - salaryPostedMonths);
//                double taxableSalary = totalAnnualSalary - totalInvestmentAmt;
//                double totalIncomeTax = 0d;
//                for (HrTaxSlabMaster taxSlab : taxSlabList) {
//                    if (taxableSalary > taxSlab.getRangeTo()) {
//                        totalIncomeTax = totalIncomeTax + (taxSlab.getRangeTo() * taxSlab.getApplicableTax()) / 100;
//                        taxableSalary = taxableSalary - taxSlab.getRangeTo();
//                    } else {
//                        totalIncomeTax = totalIncomeTax + (taxableSalary * taxSlab.getApplicableTax()) / 100;
//                        break;
//                    }
//                }
//                double remaingTaxAmt = totalIncomeTax - deductedTaxAmt;
//                double monthlyTaxAmt = 0d;
//                if (taxOnSal != 0) {
//                    monthlyTaxAmt = Math.ceil(remaingTaxAmt / (12 - salaryPostedMonths));
//                }
//                double monthlyNetSalary = monthSalary - monthlyTaxAmt ;
//
//                goingHrSalaryProcessingTO.setRefNo(refNo);
//                goingHrSalaryProcessingTO.setGrossSalary(Math.round(monthSalary));
//                goingHrSalaryProcessingTO.setSalary(Math.round(monthlyNetSalary));
//                goingHrSalaryProcessingTO.setDeductiveTax(Math.round(monthlyTaxAmt));
//                goingHrSalaryProcessingTO.setPostFlag('Y');
//                goingHrSalaryProcessingTO.setHrSalaryProcessingDetailTO(hrSalaryProcessingFinalDetailTO);
//                salaryList.add(goingHrSalaryProcessingTO);
//            }
//        } catch (Exception e) {
//            logger.error("Exception occured while executing method insertSalaryProcessing()", e);
//            e.printStackTrace();
//            throw new Exception(e.getMessage());
//        }
//        if (logger.isDebugEnabled()) {
//            logger.debug("Execution time for insertSalaryProcessing is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
//        }
//        return salaryList;

        long begin = System.nanoTime();
        List<HrSalaryProcessingTO> salaryList = new ArrayList<>();
        try {
            HrPersonnelDetailsDAO personnelDAO = new HrPersonnelDetailsDAO(em);
            HrSalaryProcessingDAO salaryDAO = new HrSalaryProcessingDAO(em);
            HrAttendanceDetailsDAO attendanceDetailsDAO = new HrAttendanceDetailsDAO(em);
            HrSalaryAllocationDAO salaryAllocationDAO = new HrSalaryAllocationDAO(em);

            int dayInMonth = 0;
            List dateCntList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report "
                    + " where reportname = 'DAYS_IN_MONTH'").getResultList();
            if (!dateCntList.isEmpty()) {
                Vector vecMonth = (Vector) dateCntList.get(0);
                dayInMonth = Integer.parseInt(vecMonth.get(0).toString());
            }

            int taxOnSal = 0;
            List taxSalList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report "
                    + " where reportname = 'TAX_ON_SAL'").getResultList();
            if (!taxSalList.isEmpty()) {
                taxOnSal = Integer.parseInt(taxSalList.get(0).toString());
            }

            //GROOS POSTING IN ACCOUNT OR NET POSTING 0 : GROSS , 1: NET 
            int grossOrNet = ftsFacade.getCodeForReportName("GROSS_OR_NET");

            List<HrPersonnelDetails> empList = new ArrayList<HrPersonnelDetails>();
            if (selectionCriteria.equalsIgnoreCase("EWE")) {
                HrPersonnelDetails personnelDetails = personnelDAO.findByEmpStatusAndEmpId(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), selectedValues, 'Y');
                empList.add(personnelDetails);
            } else {
                empList = personnelDAO.findEntityEmpStatusY(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), selectedValues);
            }
            if (empList.isEmpty()) {
                throw new Exception("Either Employee does not exist or Employee is not active.");
            }

            String prevEmpId = "";
            for (HrPersonnelDetails personnelDetails : empList) {

                HrSalaryProcessingTO goingHrSalaryProcessingTO = new HrSalaryProcessingTO();

                HrSalaryProcessingPKTO goingHrsalaryProcessingPKTO = new HrSalaryProcessingPKTO();
                goingHrsalaryProcessingPKTO.setCalDateFrom(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCalDateFrom());
                goingHrsalaryProcessingPKTO.setCalDateTo(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCalDateTo());
                goingHrsalaryProcessingPKTO.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                goingHrsalaryProcessingPKTO.setMonths(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                goingHrSalaryProcessingTO.setHrSalaryProcessingPK(goingHrsalaryProcessingPKTO);
                goingHrSalaryProcessingTO.setAuthBy(comingHrSalaryProcessingTO.getAuthBy());
                goingHrSalaryProcessingTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                goingHrSalaryProcessingTO.setEnteredBy(comingHrSalaryProcessingTO.getEnteredBy());
                goingHrSalaryProcessingTO.setStatFlag("Y");
                goingHrSalaryProcessingTO.setStatUpFlag("Y");
                goingHrSalaryProcessingTO.setModDate(new Date());
                goingHrSalaryProcessingTO.setYear(comingHrSalaryProcessingTO.getYear());

                List refNoList = em.createNativeQuery("select ifnull(max(ref_no),0)+1 from hr_salary_processing").getResultList();
                int refNo = Integer.valueOf(refNoList.get(0).toString());

                comingHrSalaryProcessingTO.getHrSalaryProcessingPK().setEmpCode(personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());
                goingHrSalaryProcessingTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(personnelDetails));

//                List<HrSalaryProcessing> hrSalaryProcessingList = salaryDAO.getSalaryForTheMonthWithPostFlag(PayrollObjectAdaptor.adaptToHrSalaryProcessingEntity(goingHrSalaryProcessingTO));
//                if (!hrSalaryProcessingList.isEmpty()) {
//                    throw new Exception("Salary already posted for month " + comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths() + " and for employee " + personnelDetails.getEmpName());
//                }
                HrSalaryAllocationPK salaryAllocationPK = new HrSalaryAllocationPK();
                salaryAllocationPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode()); //90
                salaryAllocationPK.setEmpCode(personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());

                List basicSal = em.createNativeQuery("select distinct BASIC_SALARY from  hr_salary_allocation where EMP_CODE='" + personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue() + "' ").getResultList();
                String basicSalary = basicSal.get(0).toString();
                goingHrSalaryProcessingTO.setBasicSalary(basicSalary);

                int basicsalInc = ftsFacade.getCodeForReportName("BASIC-INCREMENT-DT");
                if (basicsalInc == 1) {
                    List basicSalIncreamentDate = em.createNativeQuery("select DATE_FORMAT(Increment_Date, \"%d/%m/%Y\") FROM basicincrement where Emp_Code='" + personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue() + "'").getResultList();
                    goingHrSalaryProcessingTO.setBasicSalIncDate(basicSalIncreamentDate.get(0).toString());
                }



                HrAttendanceDetailsPK hrAttendetailsPK = new HrAttendanceDetailsPK();
                HrAttendanceDetails hrAttenDetails = new HrAttendanceDetails();

                hrAttendetailsPK.setAttenMonth(month);
                hrAttendetailsPK.setAttenYear(Integer.parseInt(comingHrSalaryProcessingTO.getYear()));
                hrAttendetailsPK.setCompCode(salaryAllocationPK.getCompCode());
                hrAttendetailsPK.setEmpCode(salaryAllocationPK.getEmpCode());

                hrAttenDetails.setPostFlag('Y');
                hrAttenDetails.setHrAttendanceDetailsPK(hrAttendetailsPK);

//                List<HrAttendanceDetails> hrAttendanceDetailsList = attendanceDetailsDAO.findAttendanceDetailsPostedForMonth(hrAttenDetails);
//                if (hrAttendanceDetailsList.isEmpty()) {
//                    throw new Exception("Attendance is not posted for month " + comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths() + " and for employee " + personnelDetails.getEmpName());
//                }
//               HrAttendanceDetails attendanceDetails = hrAttendanceDetailsList.get(0);
                int absentDays = 0;
                long overTimePd = 0;
                String overTimeunit = "0";
                int deductDays = 0;

                int monthDays;
                if (dayInMonth == 0) {
                    monthDays = 30;
                } else {
                    Date dt = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dt);
                    monthDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                }

                int actualDays = monthDays - (absentDays + deductDays);

                List<HrSalaryProcessingDetailTO> hrSalaryProcessingFinalDetailTO = new ArrayList<HrSalaryProcessingDetailTO>();
//                baseAmtMap.clear();
//                restMap.clear();
                // aditya added
                List basicSalCompDetails = em.createNativeQuery("select distinct a.EMP_CODE,h.COMP_CODE,a.BASIC_SALARY,h.NATURE,h.COMP_CODE,h.PURPOSE_CODE,ifnull(h.GL_CODE,''),h.DESC_SHORT_CODE  from hr_salary_structure h, hr_salary_allocation a where h.AL_DESC='basic' and a.COMP_CODE= h.COMP_CODE and  EMP_CODE='" + personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue() + "'").getResultList();
                if (!basicSalCompDetails.isEmpty()) {
                    for (Object obj : basicSalCompDetails) {
                        Object[] ele = (Object[]) obj;
                        HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK1 = new HrSalaryProcessingDetailPKTO();
                        HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO1 = new HrSalaryProcessingDetailTO();
                        hrSalaryProcessingDetailPK1.setCompCode(Integer.valueOf(ele[1].toString()));
                        hrSalaryProcessingDetailPK1.setComponentType("BASIC");
                        hrSalaryProcessingDetailPK1.setEmpCode(Long.decode(ele[0].toString()));
                        hrSalaryProcessingDetailPK1.setRefNo(refNo);
                        hrSalaryProcessingDetailTO1.setDefaultComp(Integer.valueOf(ele[1].toString()));
                        hrSalaryProcessingDetailTO1.setSalary(Double.valueOf(ele[2].toString()));
                        hrSalaryProcessingDetailTO1.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK1);
                        hrSalaryProcessingDetailTO1.setGlCode(ele[6].toString());
                        hrSalaryProcessingDetailTO1.setShCode(Integer.valueOf(ele[7].toString()));
                        hrSalaryProcessingDetailTO1.setPurCode("PUR001");
                        hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO1);

                    }
                }
                 // HashMap<String,Double> pfBaseMap = new HashMap<String,Double>();
                  //  List<String> pfComp = getPFBaseComponent(personnelDetails.getHrPersonnelDetailsPK().getEmpCode()) ;
                List<EmpSalaryAllocationGridTO> salaryAmtList = getNewSalaryBreakUp(salaryAllocationPK.getCompCode(), String.valueOf(salaryAllocationPK.getEmpCode()), "PUR001");
                double allowancesAmt = 0d;
                if (!salaryAmtList.isEmpty()) {
                    for (EmpSalaryAllocationGridTO ele : salaryAmtList) {
                        if((!ele.getComponent().equalsIgnoreCase("BASIC")) && ele.getPurposeCode().equalsIgnoreCase("PUR001")){
                          if((ele.getComponent().contains("SPL") || ele.getComponent().contains("Spl") || ele.getComponent().contains("spl")) && ele.getPurposeCode().equalsIgnoreCase("PUR001")){
                          String compName="SPL";
                          double totAmt = (ele.getAmount() * actualDays) / monthDays;
                        int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                        HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                        HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                        hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailPK.setComponentType(compName);
                        hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                        hrSalaryProcessingDetailPK.setRefNo(refNo);
                        hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailTO.setSalary(Math.round(totAmt));
                        hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                        hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                        hrSalaryProcessingDetailTO.setShCode(shCode);
                        hrSalaryProcessingDetailTO.setPurCode("PUR001");
                        hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                        allowancesAmt = allowancesAmt + Math.round(totAmt);
           
                   //     if(pfComp.contains(ele.getComponent()) && !ele.getComponent().equalsIgnoreCase("BASIC")){
                   //        pfBaseMap.put(ele.getComponent(), totAmt);
                   //     }
                      }else{
                        double totAmt = (ele.getAmount() * actualDays) / monthDays;
                        int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                        HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                        HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                        hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailPK.setComponentType(ele.getComponent());
                        hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                        hrSalaryProcessingDetailPK.setRefNo(refNo);
                        hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailTO.setSalary(Math.round(totAmt));
                        hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                        hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                        hrSalaryProcessingDetailTO.setShCode(shCode);
                        hrSalaryProcessingDetailTO.setPurCode("PUR001");
                        hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                        allowancesAmt = allowancesAmt + Math.round(totAmt);
           
                     //   if(pfComp.contains(ele.getComponent()) && !ele.getComponent().contains("BASIC")){
                     //      pfBaseMap.put(ele.getComponent(), totAmt);
                     //   }
           
                      }
                     }}
                
                    // List<EmpSalaryAllocationGridTO> salaryAmtListPUR002 = getSalaryBreakUp(salaryAllocationPK.getCompCode(), String.valueOf(salaryAllocationPK.getEmpCode()), "PUR002");

                    double deductionAmt = 0d;
                    Map<String, Long> dedMap = new HashMap<String, Long>();
                    if (!salaryAmtList.isEmpty()) {
                        int a = 0, b = 0, c = 0;
                        double totDedAmt = 0d;
                        for (EmpSalaryAllocationGridTO ele : salaryAmtList) {
                            if (!ele.getComponent().equalsIgnoreCase("BASIC") && ele.getPurposeCode().equalsIgnoreCase("PUR002")) {
                                if (ele.getComponent().equalsIgnoreCase("PF")) {

                                    totDedAmt = (ele.getAmount() * actualDays) / monthDays;
                                    int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                                    HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                                    HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                                    hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                    hrSalaryProcessingDetailPK.setComponentType(ele.getComponent());
                                    hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                                    hrSalaryProcessingDetailPK.setRefNo(refNo);
                                    hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                    hrSalaryProcessingDetailTO.setSalary(Math.round(totDedAmt));
                                    hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                                    hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                                    hrSalaryProcessingDetailTO.setShCode(shCode);
                                    hrSalaryProcessingDetailTO.setPurCode("PUR002");
                                    hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                                    if (!dedMap.containsKey(ele.getComponent())) {
                                        dedMap.put(ele.getComponent(), Math.round(totDedAmt));
                                    }
                                } else if (ele.getComponent().contains("INC")) {
                                    totDedAmt = Math.round(ele.getAmount());
                                    String compName = "INCOME TAX";
                                    int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                                    HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                                    HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                                    hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                    hrSalaryProcessingDetailPK.setComponentType(compName);
                                    hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                                    hrSalaryProcessingDetailPK.setRefNo(refNo);
                                    hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                    hrSalaryProcessingDetailTO.setSalary(Math.round(ele.getAmount()));
                                    hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                                    hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                                    hrSalaryProcessingDetailTO.setShCode(shCode);
                                    hrSalaryProcessingDetailTO.setPurCode("PUR002");
                                    hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                                    if (!dedMap.containsKey(ele.getComponent())) {
                                        dedMap.put(ele.getComponent(), Math.round(totDedAmt));
                                    }
                                } else if (ele.getComponent().contains("LIC")) {
                                    totDedAmt = Math.round(ele.getAmount());
                                    String CompName = "LIC";
                                    int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                                    HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                                    HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                                    hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                    hrSalaryProcessingDetailPK.setComponentType(CompName);
                                    hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                                    hrSalaryProcessingDetailPK.setRefNo(refNo);
                                    hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                    hrSalaryProcessingDetailTO.setSalary(Math.round(ele.getAmount()));
                                    hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                                    hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                                    hrSalaryProcessingDetailTO.setShCode(shCode);
                                    hrSalaryProcessingDetailTO.setPurCode("PUR002");
                                    hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                                    if (!dedMap.containsKey(ele.getComponent())) {
                                        dedMap.put(ele.getComponent(), Math.round(totDedAmt));
                                    }
                                } else if (ele.getComponent().contains("TDS")) {
                                    totDedAmt = Math.round(ele.getAmount());
                                    String CompName = "TDS";
                                    int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                                    HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                                    HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                                    hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                    hrSalaryProcessingDetailPK.setComponentType(CompName);
                                    hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                                    hrSalaryProcessingDetailPK.setRefNo(refNo);
                                    hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                    hrSalaryProcessingDetailTO.setSalary(Math.round(ele.getAmount()));
                                    hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                                    hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                                    hrSalaryProcessingDetailTO.setShCode(shCode);
                                    hrSalaryProcessingDetailTO.setPurCode("PUR002");
                                    hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                                    if (!dedMap.containsKey(ele.getComponent())) {
                                        dedMap.put(ele.getComponent(), Math.round(totDedAmt));
                                    }
                                } else {
                                    totDedAmt = Math.round(ele.getAmount());
                                    int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                                    HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                                    HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                                    hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                    hrSalaryProcessingDetailPK.setComponentType(ele.getComponent());
                                    hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                                    hrSalaryProcessingDetailPK.setRefNo(refNo);
                                    hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                    hrSalaryProcessingDetailTO.setSalary(Math.round(ele.getAmount()));
                                    hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                                    hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                                    hrSalaryProcessingDetailTO.setShCode(shCode);
                                    hrSalaryProcessingDetailTO.setPurCode("PUR002");
                                    hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                                    if (!dedMap.containsKey(ele.getComponent())) {
                                        dedMap.put(ele.getComponent(), Math.round(totDedAmt));
                                    }

                                }

                                // deductionAmt = deductionAmt + Math.round(totDedAmt);  


                            }
                        }
                    }


                    for (Map.Entry<String, Long> entry : dedMap.entrySet()) {
                        deductionAmt = deductionAmt + entry.getValue();
                    }

                    int loanRecInSal = ftsFacade.getCodeForReportName("LOAN_REC_IN_SAL");
                    if (loanRecInSal == 1) {
                        if (!prevEmpId.equalsIgnoreCase(personnelDetails.getEmpId())) {
                            prevEmpId = personnelDetails.getEmpId();

                            List acNoLst = getLoanAcList(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(),
                                    personnelDetails.getEmpId());
                            if (!acNoLst.isEmpty()) {
                                for (int l = 0; l < acNoLst.size(); l++) {
                                    Object[] lLsVec = (Object[]) acNoLst.get(l);

                                    String lAcNo = lLsVec[0].toString();
                                    double emiAmt = Double.parseDouble(lLsVec[1].toString());
                                    String compType = lLsVec[2].toString();

                                    HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                                    HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                                    hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                    hrSalaryProcessingDetailPK.setComponentType(compType);
                                    hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                                    hrSalaryProcessingDetailPK.setRefNo(refNo);
                                    hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                    hrSalaryProcessingDetailTO.setSalary(Math.round(emiAmt));
                                    hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                                    hrSalaryProcessingDetailTO.setGlCode(lAcNo);
                                    hrSalaryProcessingDetailTO.setShCode(0);

                                    if (grossOrNet == 1) {
                                        hrSalaryProcessingDetailTO.setPurCode("PUR003");
                                    } else if ((grossOrNet == 2 || (grossOrNet == 0))) {
                                        hrSalaryProcessingDetailTO.setPurCode("PUR002");
                                        deductionAmt = deductionAmt + Math.round(emiAmt);
                                    }

                                    hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                                }
                            }
                        }
                    }

                    List<HrSalaryProcessing> preTaxAndSalaryList = salaryDAO.getTaxProcessEmpForYearWithPostFlag(salaryAllocationPK);
                    int salaryPostedMonths = 0;
                    if (!preTaxAndSalaryList.isEmpty()) {
                        salaryPostedMonths = preTaxAndSalaryList.size();
                    }
                    double preSalaryAmt = 0d;
                    double deductedTaxAmt = 0d;
                    for (HrSalaryProcessing taxObj : preTaxAndSalaryList) {
                        preSalaryAmt = preSalaryAmt + taxObj.getGrossSalary();
                        deductedTaxAmt = deductedTaxAmt + taxObj.getDeductiveTax();
                    }
                    List<HrTaxInvestmentCategory> taxInvCatList = salaryDAO.findByCompCodeAndEmpCode(personnelDetails.getHrPersonnelDetailsPK().getCompCode(), personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());
                    double totalInvestmentAmt = 0d;
                    for (HrTaxInvestmentCategory taxInvCat : taxInvCatList) {
                        if (taxInvCat.getCategoryAmt() > taxInvCat.getCategoryMaxLimit()) {
                            totalInvestmentAmt = totalInvestmentAmt + taxInvCat.getCategoryMaxLimit();
                        } else {
                            totalInvestmentAmt = totalInvestmentAmt + taxInvCat.getCategoryAmt();
                        }
                    }

                    List<HrTaxSlabMaster> taxSlabList = salaryDAO.findByCompCodeAndTaxFor(personnelDetails.getHrPersonnelDetailsPK().getCompCode(), String.valueOf(personnelDetails.getSex()));
                    double overTimeSalary = 0d, totalAnnualSalary = 0d, monthSalary = 0d;
                    if (grossOrNet == 0) {
                        monthSalary = (allowancesAmt + deductionAmt);
                        if (overTimeunit.equalsIgnoreCase("HOUR")) {
                            overTimeSalary = (((allowancesAmt + deductionAmt) / monthDays) * (overTimePd / 24));
                            monthSalary = monthSalary + overTimeSalary + Double.valueOf(basicSalary);
                        } else {
                            overTimeSalary = (((allowancesAmt + deductionAmt) / monthDays) * (overTimePd / (24 * 60)));
                            monthSalary = monthSalary + overTimeSalary + Double.valueOf(basicSalary);
                        }
                        totalAnnualSalary = preSalaryAmt + monthSalary + (allowancesAmt + deductionAmt) * (11 - salaryPostedMonths);
                    } else {
                        monthSalary = (allowancesAmt - deductionAmt);
                        if (overTimeunit.equalsIgnoreCase("HOUR")) {
                            overTimeSalary = (((allowancesAmt - deductionAmt) / monthDays) * (overTimePd / 24));
                            monthSalary = monthSalary + overTimeSalary + Double.valueOf(basicSalary);
                        } else {
                            overTimeSalary = (((allowancesAmt - deductionAmt) / monthDays) * (overTimePd / (24 * 60)));
                            monthSalary = monthSalary + overTimeSalary + Double.valueOf(basicSalary);
                        }
                        totalAnnualSalary = preSalaryAmt + monthSalary + (allowancesAmt - deductionAmt) * (11 - salaryPostedMonths);
                    }

                    double taxableSalary = totalAnnualSalary - totalInvestmentAmt;
                    double totalIncomeTax = 0d;
                    for (HrTaxSlabMaster taxSlab : taxSlabList) {
                        if (taxableSalary > taxSlab.getRangeTo()) {
                            totalIncomeTax = totalIncomeTax + (taxSlab.getRangeTo() * taxSlab.getApplicableTax()) / 100;
                            taxableSalary = taxableSalary - taxSlab.getRangeTo();
                        } else {
                            totalIncomeTax = totalIncomeTax + (taxableSalary * taxSlab.getApplicableTax()) / 100;
                            break;
                        }
                    }
                    double remaingTaxAmt = totalIncomeTax - deductedTaxAmt;
                    double monthlyTaxAmt = 0d;
                    if (taxOnSal != 0) {
                        monthlyTaxAmt = Math.ceil(remaingTaxAmt / (12 - salaryPostedMonths));
                    }
                    double monthlyNetSalary = monthSalary - monthlyTaxAmt;

                    goingHrSalaryProcessingTO.setRefNo(refNo);
                    goingHrSalaryProcessingTO.setGrossSalary(Math.round(monthSalary));
                    goingHrSalaryProcessingTO.setSalary(Math.round(monthlyNetSalary));
                    goingHrSalaryProcessingTO.setDeductiveTax(Math.round(monthlyTaxAmt));
                    goingHrSalaryProcessingTO.setPostFlag('Y');
                    goingHrSalaryProcessingTO.setHrSalaryProcessingDetailTO(hrSalaryProcessingFinalDetailTO);
                    salaryList.add(goingHrSalaryProcessingTO);
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return salaryList;

    }

    @Override
    public String getSaveData(String authBy, String enterBy, String salaryFromAccount, String salaryToAccount, String effectivedt, String modDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String salaryTabId = "";

            String query1 = "";

            query1 = "select ifnull(max(cast(substring(salary_slab_id from 4) as unsigned)),0) \n"
                    + "  from hr_basic_salary_structure";

            List list = em.createNativeQuery(query1).getResultList();
            Long salarySalId = Long.parseLong(list.get(0).toString()) + 1;
            salaryTabId = "SAL" + ParseFileUtil.addTrailingZeros(String.valueOf(salarySalId), 4);
            int tdInsert = em.createNativeQuery("INSERT INTO hr_basic_salary_structure(salary_slab_id, salary_from_amt, salary_to_amt,auth_by,entered_by,effective_date,MOD_DATE) values('" + salaryTabId + "' ,'" + salaryFromAccount + "','" + salaryToAccount + "','" + authBy + "','" + enterBy + "','" + effectivedt + "','" + modDate + "')").executeUpdate();
            if (tdInsert <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }


            ut.commit();
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                ut.rollback();
                throw new com.cbs.exception.ApplicationException(ex.getMessage());
            } catch (Exception e) {
                try {
                    throw new ApplicationException(e.getMessage());
                } catch (ApplicationException ex1) {
                    java.util.logging.Logger.getLogger(PayrollTransactionsFacade.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        return "true";

    }

//    public List<EmpSalaryAllocationGridTO> getSalaryBreakUp(int compCode, String empCd, String purposeCode) throws ApplicationException {
//        List<EmpSalaryAllocationGridTO> empsalaryBreakUpLst = new ArrayList<EmpSalaryAllocationGridTO>();
//
//        double finalCompAmt = 0.0;
//
//
//        try {
//            int pfFixParam = ftsFacade.getCodeForReportName("PFFIX");
//            // This Map define dependent on basic component amount
//            List<EmpSalaryAllocationGridTO> baseComponentEmpWiseList = getSalaryComponent(compCode, empCd, purposeCode, "BASIC");
//            if (!baseComponentEmpWiseList.isEmpty()) {
//                double CompAmt;
//                for (EmpSalaryAllocationGridTO elem : baseComponentEmpWiseList) {
//                    CompAmt = 0.0;
//                    if (elem.getSlabCriteria().equalsIgnoreCase("PERCENTAGE")) {
//                        CompAmt += (elem.getSlabCriteriaAmt() / 100) * elem.getBasicSalary();
//                    } else if (elem.getSlabCriteria().equalsIgnoreCase("AMOUNT")) {
//                        CompAmt += elem.getSlabCriteriaAmt();
//                    }
//                    // To restrict component amount not greater than max component amount
//                    if ((elem.getSlabCriteriaMaxAmt() != 0.0) && (CompAmt > elem.getSlabCriteriaMaxAmt())) {
//                        CompAmt = elem.getSlabCriteriaMaxAmt();
//                    }
//                    elem.setAmount(CompAmt);
//                    if (purposeCode.equalsIgnoreCase("PUR001")) {
//                        baseAmtMap.put(elem.getDependentComponent(), elem.getAmount());
//
//                    }
//                    if (purposeCode.equalsIgnoreCase("PUR002")) {
//                        baseAmtMap.put(elem.getDependentComponent(), elem.getAmount());
//
//                    }
//
//                }
//
//                List listOfDistinctComponent = em.createNativeQuery("select distinct DEPEND_COMPONENT,ifnull(ss.GL_CODE,''),ifnull(ss.DESC_SHORT_CODE,'') from "
//                        + "hr_salary_allocation sa,hr_basic_salary_structure bs,hr_slab_master sm,hr_pe"
//                        + "rsonnel_details pd,hr_salary_structure ss where "
//                        + "sa.EMP_CODE=pd.EMP_CODE and sa.SLAB_CODE=sm.SLAB_CODE and bs.salary_slab_id=sm.SALARY_SLAB_ID and ss.AL_DESC=sm.DEPEND_COMPONENT and "
//                        + "sa.EMP_CODE='" + empCd + "'/* and pd.BASE_BRANCH='" + compCode + "' */ and sm.PURPOSE_CODE='" + purposeCode + "' "
//                        + "and (sm.RANGE_FROM<=sa.BASIC_SALARY and  sm.RANGE_TO>=sa.BASIC_SALARY ) and sm.APP_FLG='Y' ORDER by sm.DEPEND_COMPONENT desc").getResultList();
//
//                List<EmpSalaryAllocationGridTO> componentEmpWiseList = getSalaryComponent(compCode, empCd, purposeCode, "ALL");
//
//                Map componetAmtMap = new HashMap();
//
//                if ((!componentEmpWiseList.isEmpty()) || (!listOfDistinctComponent.isEmpty())) {
//
//                    for (Object compObj : listOfDistinctComponent) {
//                        Object[] ele = (Object[]) compObj;
//
//                        String currComponent = ele[0].toString();
//                        double pfamt = 0.0;
//                        double componentAmnt = 0.0;
//                        String shCode = "";
//                        String glCode = "";
//                        glCode = ele[1].toString();
//                        shCode = ele[2].toString();
//
//                        for (EmpSalaryAllocationGridTO componentData : componentEmpWiseList) {
//
//                            String slabCriteria = componentData.getSlabCriteria();
//                            double slabCriteriaAmount = componentData.getSlabCriteriaAmt();
//
//
//                            if (componentData.getDependentComponent().equalsIgnoreCase(currComponent)) {
//                                // To define Base Amount for calculation
//                                double baseAmount = 0.0;
//                                double compAmt = 0.0;
//
//                                if (componentData.getBaseComponent().equalsIgnoreCase("BASIC")) {
//                                    baseAmount = componentData.getBasicSalary();
//                                } else if (baseAmtMap.containsKey(componentData.getBaseComponent())) {
//                                    baseAmount = (Double) baseAmtMap.get(componentData.getBaseComponent());
//                                } else if (restMap.containsKey(componentData.getBaseComponent())) {
//                                    baseAmount = (Double) restMap.get(componentData.getBaseComponent());
//                                }
//
//                                if (slabCriteria.equalsIgnoreCase("PERCENTAGE")) {
//                                    compAmt += (slabCriteriaAmount / 100) * baseAmount;
//                                    componentData.setAmount(compAmt);
//
//                                    if (baseAmtMap.containsKey(componentData.getDependentComponent())) {
//                                        double amnt = compAmt + (Double) baseAmtMap.get(componentData.getDependentComponent());;
//                                        baseAmtMap.put(componentData.getDependentComponent(), amnt);
//                                    } else if (!(baseAmtMap.containsKey(componentData.getDependentComponent())) && !(restMap.containsKey(componentData.getDependentComponent()))) {
//                                        restMap.put(componentData.getDependentComponent(), compAmt);
//                                    } else {
//                                        double amnt = compAmt + (Double) restMap.get(componentData.getDependentComponent());
//                                        restMap.put(componentData.getDependentComponent(), amnt);
//                                    }
//
//                                    baseComponentEmpWiseList.add(componentData);
//                                } else if (slabCriteria.equalsIgnoreCase("AMOUNT")) {
//                                    compAmt += slabCriteriaAmount;
//                                    componentData.setAmount(compAmt);
//
//                                    if (baseAmtMap.containsKey(componentData.getDependentComponent())) {
//                                        double amnt = compAmt + (Double) baseAmtMap.get(componentData.getDependentComponent());;
//                                        baseAmtMap.put(componentData.getDependentComponent(), amnt);
//                                    } else if (!(baseAmtMap.containsKey(componentData.getDependentComponent())) && !(restMap.containsKey(componentData.getDependentComponent()))) {
//                                        restMap.put(componentData.getDependentComponent(), compAmt);
//                                    } else {
//                                        double amnt = compAmt + (Double) restMap.get(componentData.getDependentComponent());;
//                                        restMap.put(componentData.getDependentComponent(), amnt);
//                                    }
//
//                                    baseComponentEmpWiseList.add(componentData);
//                                }
//                                // To restrict component amount not greater than max component amount
//                                if ((componentData.getSlabCriteriaMaxAmt() != 0.0) && (compAmt > componentData.getSlabCriteriaMaxAmt())) {
//                                    compAmt = componentData.getSlabCriteriaMaxAmt();
//                                    componentData.setAmount(compAmt);
//
//                                    if (baseAmtMap.containsKey(componentData.getDependentComponent())) {
//                                        double amnt = compAmt + (Double) baseAmtMap.get(componentData.getDependentComponent());;
//                                        baseAmtMap.put(componentData.getDependentComponent(), amnt);
//                                    } else if (!(baseAmtMap.containsKey(componentData.getDependentComponent())) && !(restMap.containsKey(componentData.getDependentComponent()))) {
//                                        restMap.put(componentData.getDependentComponent(), compAmt);
//                                    } else {
//                                        double amnt = compAmt + (Double) restMap.get(componentData.getDependentComponent());;
//                                        restMap.put(componentData.getDependentComponent(), amnt);
//                                    }
//
//                                    baseComponentEmpWiseList.add(componentData);
//                                }
//
//                                componentAmnt = componentAmnt + compAmt;
//
//                                if (purposeCode.equalsIgnoreCase("PUR002")) {
//                                    if (baseAmtMap.containsKey(componentData.getDependentComponent())) {
//                                        double ant = (Double) baseAmtMap.get(componentData.getDependentComponent());
//                                        baseAmtMap.put(componentData.getDependentComponent(), ant);
//                                    } else {
//                                        double ant = (Double) restMap.get(componentData.getDependentComponent());
//                                        restMap.put(componentData.getDependentComponent(), ant);
//                                    }
//                                }
//
//                            }
//
//                        }
//
//                        // add here EmpSalaryAllocationGridTO object and get the base from that object component amout as
//                        if (purposeCode.equalsIgnoreCase("PUR001")) {
//                            EmpSalaryAllocationGridTO empCompDtl = new EmpSalaryAllocationGridTO();
//                            empCompDtl.setComponent(currComponent);
//
//                            if (baseAmtMap.containsKey(currComponent)) {
//
//                                double compAmount = (Double) baseAmtMap.get(ele[0].toString());
//                                empCompDtl.setAmount(compAmount);
//                            } else {
//                                double compAmount = componentAmnt;
//                                empCompDtl.setAmount(compAmount);
//                            }
//
//                            empCompDtl.setGlCode(glCode);
//                            empCompDtl.setShCode(shCode);
//                            componetAmtMap.put(currComponent, empCompDtl);
//                            empsalaryBreakUpLst.add(empCompDtl);
//                        } else {
//                            EmpSalaryAllocationGridTO empCompDtl = new EmpSalaryAllocationGridTO();
//                            empCompDtl.setComponent(currComponent);
//                            if (baseAmtMap.containsKey(currComponent)) {
//                                // add parameter PFFIX for khattri pf=1800    
//                                if (pfFixParam == 1) {
//                                    if (purposeCode.equalsIgnoreCase("PUR002") && shCode.equalsIgnoreCase("1")) {
//
//                                        for (int i = 0; i < baseComponentEmpWiseList.size(); i++) {
//                                            if (baseComponentEmpWiseList.get(i).getPurposeCode().equalsIgnoreCase("PUR002") && baseComponentEmpWiseList.get(i).getShCode().equalsIgnoreCase("1")) {
//                                                pfamt = baseComponentEmpWiseList.get(i).getSlabCriteriaMaxAmt();
//                                            }
//                                        }
//
//                                        if ((Double) baseAmtMap.get(currComponent) >= pfamt && pfamt != 0.0) {
//                                            empCompDtl.setAmount(pfamt);
//                                        } else {
//                                            empCompDtl.setAmount((Double) baseAmtMap.get(currComponent));
//                                        }
//                                    } else {
//                                        empCompDtl.setAmount((Double) baseAmtMap.get(currComponent));
//
//                                    }
//                                } else if (pfFixParam == 0) {
//                                    empCompDtl.setAmount((Double) baseAmtMap.get(currComponent));
//                                }
//                            } else if (pfFixParam == 0 && !(baseAmtMap.containsKey(currComponent))) {
//                                empCompDtl.setAmount((Double) restMap.get(currComponent));
//                            }
//
//                            empCompDtl.setGlCode(glCode);
//                            empCompDtl.setShCode(shCode);
//                            componetAmtMap.put(currComponent, empCompDtl);
//                            empsalaryBreakUpLst.add(empCompDtl);
//                        }
//                    }
//                }
//
//
//                finalCompAmt = 0.0;
//
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return empsalaryBreakUpLst;
//    }
//
//    public List<EmpSalaryAllocationGridTO> getSalaryComponent(int compCode, String empCd, String purposeCode, String baseComp) throws ApplicationException {
//        List<EmpSalaryAllocationGridTO> empsalaryBreakUpLst = new ArrayList<EmpSalaryAllocationGridTO>();
//
//        String condn = "";
//        if (baseComp.equalsIgnoreCase("BASIC")) {
//            condn = "and  BASE_COMPONENT='BASIC'";
//        } else if (baseComp.equalsIgnoreCase("NONBASIC")) {
//            condn = "and  BASE_COMPONENT<>'BASIC'";
//        } else if (baseComp.equalsIgnoreCase("ALL")) {
//            condn = "and  BASE_COMPONENT<>'BASIC'";
//        } else if (baseComp.equalsIgnoreCase("ALL")) {
//            condn = "";
//        }
//        List list = em.createNativeQuery("select pd.EMP_CODE,pd.BASE_BRANCH,BASE_COMPONENT,DEPEND_COMPONENT,BASIC_SALARY,SLAB_CRITERIA,SLAB_CRITERIA_AMT,MAX_CRITERIA_AMT,\n"
//                + "ss.NATURE,ss.PURPOSE_CODE,ifnull(GL_CODE,''),ifnull(DESC_SHORT_CODE,''),COMPONENT_ORDER from \n"
//                + "hr_salary_allocation sa,hr_basic_salary_structure bs,hr_slab_master sm,hr_personnel_details pd,hr_salary_structure ss where \n"
//                + "sa.EMP_CODE=pd.EMP_CODE and sa.SLAB_CODE=sm.SLAB_CODE and bs.salary_slab_id=sm.SALARY_SLAB_ID and ss.AL_DESC=sm.DEPEND_COMPONENT and "
//                + "sa.EMP_CODE='" + empCd + "' and sm.PURPOSE_CODE='" + purposeCode + "'   " + condn + ""
//                + "and (sm.RANGE_FROM<=sa.BASIC_SALARY and  sm.RANGE_TO>=sa.BASIC_SALARY ) and sm.APP_FLG='Y' ORDER by COMPONENT_ORDER ASC").getResultList();
//        if (!list.isEmpty()) {
//            for (Object obj : list) {
//                Object[] ele = (Object[]) obj;
//                EmpSalaryAllocationGridTO pojo = new EmpSalaryAllocationGridTO();
//                pojo.setEmpid(ele[0].toString());
//                pojo.setBaseComponent(ele[2].toString());
//                pojo.setDependentComponent(ele[3].toString());
//                pojo.setBasicSalary(Double.valueOf(ele[4].toString()));
//                pojo.setSlabCriteria(ele[5].toString());
//                pojo.setSlabCriteriaAmt(Double.valueOf(ele[6].toString()));
//                pojo.setSlabCriteriaMaxAmt(Double.valueOf(ele[7].toString()));
//                pojo.setNature(ele[8].toString());
//                pojo.setPurposeCode(ele[9].toString());
//                pojo.setGlCode(ele[10].toString());
//                pojo.setShCode(ele[11].toString());
//                pojo.setCompOrder(Integer.valueOf(ele[12].toString()));
//                empsalaryBreakUpLst.add(pojo);
//            }
//        }
//        return empsalaryBreakUpLst;
//    }
    // after changes in payroll calculation
    public List<EmpSalaryAllocationGridTO> getSalaryComponent(int compCode, String empCd, String purposeCode) throws ApplicationException {
        List<EmpSalaryAllocationGridTO> empsalaryBreakUpLst = new ArrayList<>();

        List list = em.createNativeQuery("select pd.EMP_CODE,pd.BASE_BRANCH,sm.BASE_COMPONENT,sm.DEPEND_COMPONENT,sa.BASIC_SALARY,sm.SLAB_CRITERIA,sm.SLAB_CRITERIA_AMT,sm.MAX_CRITERIA_AMT,\n"
                + "ss.NATURE,ss.PURPOSE_CODE,ifnull(GL_CODE,''),ifnull(DESC_SHORT_CODE,''),COMPONENT_ORDER,ifnull(sm.MIN_CRITERIA_AMT,0.0) as MIN_CRITERIA_AMT  from \n"
                + "hr_salary_allocation sa,hr_basic_salary_structure bs,hr_slab_master sm,hr_personnel_details pd,hr_salary_structure ss where \n"
                + "sa.EMP_CODE=pd.EMP_CODE and sa.SLAB_CODE=sm.SLAB_CODE and bs.salary_slab_id=sm.SALARY_SLAB_ID and ss.AL_DESC=sm.DEPEND_COMPONENT and "
                + "sa.EMP_CODE='" + empCd + "'"
                + "and (sm.RANGE_FROM<=sa.BASIC_SALARY and  sm.RANGE_TO>=sa.BASIC_SALARY ) and sm.APP_FLG='Y' ORDER by COMPONENT_ORDER ASC").getResultList();
        if (!list.isEmpty()) {
            for (Object obj : list) {
                Object[] ele = (Object[]) obj;
                EmpSalaryAllocationGridTO pojo = new EmpSalaryAllocationGridTO();
                pojo.setEmpid(ele[0].toString());
                pojo.setBaseComponent(ele[2].toString());
                pojo.setDependentComponent(ele[3].toString());
                pojo.setBasicSalary(Double.valueOf(ele[4].toString()));
                pojo.setSlabCriteria(ele[5].toString());
                pojo.setSlabCriteriaAmt(Double.valueOf(ele[6].toString()));
                pojo.setSlabCriteriaMaxAmt(Double.valueOf(ele[7].toString()));
                pojo.setNature(ele[8].toString());
                pojo.setPurposeCode(ele[9].toString());
                pojo.setGlCode(ele[10].toString());
                pojo.setShCode(ele[11].toString());
                pojo.setCompOrder(Integer.valueOf(ele[12].toString()));
                pojo.setSlabCriteriaMinAmt(Double.valueOf(ele[13].toString()));
                empsalaryBreakUpLst.add(pojo);
            }
        }
        return empsalaryBreakUpLst;
    }

    public List<EmpSalaryAllocationGridTO> getNewSalaryBreakUp(int compCode, String empCd, String purposeCode) throws ApplicationException {
        List<EmpSalaryAllocationGridTO> list1 = new ArrayList();
        List<EmpSalaryAllocationGridTO> list = getSalaryComponent(compCode, empCd, purposeCode);

        List listofCompNonbasic = new ArrayList();
        List listOfCompNonBasicDep = new ArrayList();

        Map<String, List<String>> map = new HashMap<String, List<String>>();
        Map<String, List<String>> restMap = new HashMap<String, List<String>>();
        Map<String, Boolean> EnableComp = new HashMap<String, Boolean>();
        Map<String, Double> amountMap = new HashMap<String, Double>();
        amountMap.put("BASIC", list.get(0).getBasicSalary());
        Map<String, Double> amountMapfinal = new HashMap<String, Double>();

        for (EmpSalaryAllocationGridTO p11 : list) {
            if (!(p11.getBaseComponent().equalsIgnoreCase("BASIC"))) {
                if (!listofCompNonbasic.contains(p11.getBaseComponent())) {
                    listofCompNonbasic.add(p11.getBaseComponent());
                }
            }
        }

        for (Object s : listofCompNonbasic) {
            String str = s.toString();
            List<String> dependencyList = new ArrayList();
            for (EmpSalaryAllocationGridTO p12 : list) {
                if (p12.getDependentComponent().toString().equalsIgnoreCase(str)) {
                    dependencyList.add(p12.getBaseComponent());
                }
            }
            map.put(str, dependencyList);
        }

        for (EmpSalaryAllocationGridTO p12 : list) {
            if (!listofCompNonbasic.contains(p12.getDependentComponent())) {
                listOfCompNonBasicDep.add(p12.getDependentComponent());
            }
        }

        for (Object s : listOfCompNonBasicDep) {
            String str = s.toString();
            List<String> dependencyList = new ArrayList();
            for (EmpSalaryAllocationGridTO p12 : list) {
                if (p12.getDependentComponent().toString().equalsIgnoreCase(str)) {
                    dependencyList.add(p12.getBaseComponent());
                }
            }
            map.put(str, dependencyList);
        }

        List<String> calList = new ArrayList();
        for (EmpSalaryAllocationGridTO p13 : list) {
            if (!calList.contains(p13.getBaseComponent())) {
                calList.add(p13.getBaseComponent());
            }
            if (!calList.contains(p13.getDependentComponent())) {
                calList.add(p13.getDependentComponent());
            }
        }

        for (Object o : calList) {
            if (o.toString().equalsIgnoreCase("BASIC")) {
                EnableComp.put(o.toString(), true);
            } else {
                EnableComp.put(o.toString(), false);
            }
        }

        while ((map.size()) > 0 || (restMap.size()) > 0) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String dependComp = entry.getKey().toString();
                List<String> list3 = new ArrayList<String>();
                list3 = entry.getValue();
                boolean b = mapDecider(list3, EnableComp);
                if (b) {
                    slabCalculation(dependComp, list3, amountMap, EnableComp, list);
                } else {
                    restMap.put(dependComp, list3);
                }
            }
            map.clear();
            if (restMap.isEmpty()) {
                break;
            } else {
                for (Map.Entry<String, List<String>> entry : restMap.entrySet()) {
                    map.put(entry.getKey(), entry.getValue());
                }
                restMap.clear();
            }
        }

        for (Map.Entry<String, Double> ent : amountMap.entrySet()) {
            System.out.print(ent.getKey() + "\t");
            System.out.println(ent.getValue());
        }



        for (String strr : calList) {

            EmpSalaryAllocationGridTO esag = new EmpSalaryAllocationGridTO();
            esag.setComponent(strr);
            esag.setAmount(amountMap.get(strr));
            for (EmpSalaryAllocationGridTO empSalAlloc : list) {
                int counter = 0;

                if (empSalAlloc.getDependentComponent().equalsIgnoreCase(strr)) {
                    counter++;
                    esag.setGlCode(empSalAlloc.getGlCode());
                    esag.setCompOrder(empSalAlloc.getCompOrder());
                    esag.setCompCode(empSalAlloc.getCompCode());
                    esag.setShCode(empSalAlloc.getShCode());
                    esag.setPurposeCode(empSalAlloc.getPurposeCode());

                }
                if (counter > 0) {
                    break;
                }
            }
            list1.add(esag);
        }
        return list1;
    }

    public boolean mapDecider(List<String> str3, Map<String, Boolean> EnableComp) {
        for (String com : str3) {
            if (!EnableComp.get(com)) {
                return false;
            }
        }
        return true;
    }

    public void slabCalculation(String str1, List<String> str2, Map<String, Double> amountMap, Map<String, Boolean> EnableComp, List<EmpSalaryAllocationGridTO> list) {

        for (String st3 : str2) {
            double compAmt = 0.0;
            for (EmpSalaryAllocationGridTO p14 : list) {
                if (p14.getBaseComponent().equalsIgnoreCase(st3) && p14.getDependentComponent().equalsIgnoreCase(str1)) {
                    if (p14.getSlabCriteria().equalsIgnoreCase("PERCENTAGE")) {
                        Double dvar = (Double) ((amountMap.get(st3)) * p14.getSlabCriteriaAmt() * (.01));
                        if (dvar >= p14.getSlabCriteriaMaxAmt() && p14.getSlabCriteriaMaxAmt() != 0.0) {
                            dvar = p14.getSlabCriteriaMaxAmt();
                            if (!amountMap.containsKey(str1)) {
                                compAmt = Double.valueOf(dvar);
                            } else if (amountMap.containsKey(str1) && Double.valueOf(amountMap.get(str1).toString()) > 0.0) {
                                compAmt = Double.valueOf(amountMap.get(str1).toString()) + dvar;
                            }
                        } else if (dvar <= p14.getSlabCriteriaMinAmt() && p14.getSlabCriteriaMinAmt() != 0.0) {
                            dvar = p14.getSlabCriteriaMinAmt();
                            if (!amountMap.containsKey(str1)) {
                                compAmt = Double.valueOf(dvar);
                            } else if (amountMap.containsKey(str1) && Double.valueOf(amountMap.get(str1).toString()) > 0.0) {
                                compAmt = Double.valueOf(amountMap.get(str1).toString()) + dvar;
                            }
                        } else {
                            dvar = dvar;
                            if (!amountMap.containsKey(str1)) {
                                compAmt = Double.valueOf(dvar);
                            } else if (amountMap.containsKey(str1) && Double.valueOf(amountMap.get(str1).toString()) > 0.0) {
                                compAmt = (Double) (Double.valueOf(amountMap.get(str1).toString()) + dvar);
                            }
                        }
                        amountMap.put(str1, compAmt);
                        if (EnableComp.containsKey(str1) && EnableComp.get(str1) != true) {
                            EnableComp.put(str1, true);
                        }

                    } else if (p14.getSlabCriteria().equalsIgnoreCase("AMOUNT")) {
                        Double dvar = p14.getSlabCriteriaAmt();
                        if (dvar >= p14.getSlabCriteriaMaxAmt() && p14.getSlabCriteriaMaxAmt() != 0.0) {
                            dvar = p14.getSlabCriteriaMaxAmt();
                            if (!amountMap.containsKey(str1)) {
                                compAmt = Double.valueOf(dvar);
                            } else if (amountMap.containsKey(str1) && Double.valueOf(amountMap.get(str1).toString()) > 0.0) {
                                compAmt = Double.valueOf(amountMap.get(str1).toString()) + dvar;
                            }
                        } else if (dvar <= p14.getSlabCriteriaMinAmt() && p14.getSlabCriteriaMinAmt() != 0.0) {
                            dvar = p14.getSlabCriteriaMinAmt();
                            if (!amountMap.containsKey(str1)) {
                                compAmt = Double.valueOf(dvar);
                            } else if (amountMap.containsKey(str1) && Double.valueOf(amountMap.get(str1)) > 0.0) {
                                compAmt = Double.valueOf(amountMap.get(str1).toString()) + dvar;
                            }
                        } else {
                            dvar = dvar;
                            if (!amountMap.containsKey(str1)) {
                                compAmt = Double.valueOf(dvar);
                            } else if (amountMap.containsKey(str1) && Double.valueOf(amountMap.get(str1).toString()) > 0.0) {
                                compAmt = Double.valueOf(amountMap.get(str1).toString()) + dvar;
                            }
                        }
                        amountMap.put(str1, compAmt);
                        if (EnableComp.containsKey(str1) && EnableComp.get(str1) != true) {
                            EnableComp.put(str1, true);
                        }
                    }
                }
            }
        }
        if (amountMap.containsKey(str1)) {
            String compName = str1;
            Double compAmt = (Double) amountMap.get(str1);
            int count = 0;
            for (EmpSalaryAllocationGridTO p136 : list) {
                if (count > 0) {
                    break;
                }
                if (p136.getDependentComponent().equalsIgnoreCase(compName)) {
                    if (compAmt >= p136.getSlabCriteriaMaxAmt() && p136.getSlabCriteriaMaxAmt() != 0.0) {
                        compAmt = p136.getSlabCriteriaMaxAmt();
                    } else if (compAmt <= p136.getSlabCriteriaMinAmt() && p136.getSlabCriteriaMinAmt() != 0.0) {
                        compAmt = p136.getSlabCriteriaMinAmt();
                    } else {
                        compAmt = compAmt;
                    }
                    count++;
                }
                amountMap.put(compName, compAmt);
            }
        }
    }

    public List<FinancialSalProjectionPojo> getErnFinancialPrjnData(String empId, String frmDate, String toDate, String purposeType) throws ApplicationException {

        List currentcomponentList = new ArrayList();
        List monthList = new ArrayList();
        monthList.add("01:APRIL");
        monthList.add("02:MAY");
        monthList.add("03:JUNE");
        monthList.add("04:JULY");
        monthList.add("05:AUGUST");
        monthList.add("06:SEPTEMBER");
        monthList.add("07:OCTOBER");  
        monthList.add("08:NOVEMBER");
        monthList.add("09:DECEMBER");
        monthList.add("10:JANUARY");
        monthList.add("11:FEBRUARY");
        monthList.add("12:MARCH");

        List<FinancialSalProjectionPojo> dataList = new ArrayList<FinancialSalProjectionPojo>();

        int var = 0;
        String ErnMonth = "";
        String DedMonth = "";
        List maxCompTypeMonthErn = null;
        List EarningCompType = null;
        List EarningSalHeaddata = null;
        List componentList = null;
        List empCodeLst = em.createNativeQuery("select EMP_CODE from hr_personnel_details where EMP_ID='" + empId + "'").getResultList();
        String empCode = empCodeLst.get(0).toString();
        List getMonthList = em.createNativeQuery(" select distinct(MONTH) from hr_salary_processing_dtl where EMP_CODE='" + empCode + "' and Date_FORMAT(MONTH_START_DATE,'%Y%m') between '" + frmDate.substring(0, 6) + "' and '" + toDate.substring(0, 6) + "' order by MONTH_START_DATE").getResultList();

        String MaxOfGetMonthList = "";
        String MinOfGetMonthList = getMonthList.get(0).toString();
        if (getMonthList.size() > 1) {
            MaxOfGetMonthList = getMonthList.get(getMonthList.size() - 1).toString();
        } else {
            MaxOfGetMonthList = MinOfGetMonthList;
        }

        if (purposeType.equalsIgnoreCase("1:Gross Earning")) {
            EarningCompType = em.createNativeQuery("select distinct COMPONENT_TYPE from hr_salary_processing_dtl where PURPOSE_CODE='PUR001'  and EMP_CODE='" + empCode + "' and Date_FORMAT(MONTH_START_DATE,'%Y%m') between '" + frmDate.substring(0, 6) + "' and '" + toDate.substring(0, 6) + "'  order by COMPONENT_TYPE").getResultList();
        } else if (purposeType.equalsIgnoreCase("2:Gross Deduction")) {
            EarningCompType = em.createNativeQuery("select distinct COMPONENT_TYPE from hr_salary_processing_dtl where PURPOSE_CODE in('PUR002','PUR003','PUR004')  and EMP_CODE='" + empCode + "' and Date_FORMAT(MONTH_START_DATE,'%Y%m') between '" + frmDate.substring(0, 6) + "' and '" + toDate.substring(0, 6) + "' order by COMPONENT_TYPE").getResultList();
        }

        for (int i = 0; i < monthList.size(); i++) {
            for (int j = 0; j < EarningCompType.size(); j++) {

                if (!getMonthList.contains(monthList.get(i).toString().substring(3)) && var == 0) {
                    if (purposeType.equalsIgnoreCase("1:Gross Earning")) {
                        componentList = em.createNativeQuery("select COMPONENT_TYPE from hr_salary_processing_dtl where PURPOSE_CODE='PUR001' and MONTH='" + MinOfGetMonthList + "' and EMP_CODE='" + empCode + "' ").getResultList();
                    } else if (purposeType.equalsIgnoreCase("2:Gross Deduction")) {
                        componentList = em.createNativeQuery("select COMPONENT_TYPE from hr_salary_processing_dtl where  PURPOSE_CODE in('PUR002','PUR003','PUR004') and MONTH='" + MinOfGetMonthList + "' and EMP_CODE='" + empCode + "' ").getResultList();
                    }
                    for (int d = 0; d < componentList.size(); d++) {
                        currentcomponentList.add(componentList.get(d).toString());
                    }
                    FinancialSalProjectionPojo fspErn = new FinancialSalProjectionPojo();
                    if (!EarningCompType.get(j).toString().contains("PF CONTRIBUTION")) {
                        fspErn.setComponentType(EarningCompType.get(j).toString());
                        fspErn.setComponentAmt(BigDecimal.ZERO);
                        fspErn.setMonth(monthList.get(i).toString());
                        fspErn.setPurposeType(purposeType);
                        dataList.add(fspErn);
                    }
                    currentcomponentList.clear();
                } else if (getMonthList.contains(monthList.get(i).toString().substring(3))) {
                    var = 1;
                    if (purposeType.equalsIgnoreCase("1:Gross Earning")) {
                        EarningSalHeaddata = em.createNativeQuery("select AMOUNT,PURPOSE_CODE  from hr_salary_processing_dtl where PURPOSE_CODE='PUR001' and MONTH='" + monthList.get(i).toString().substring(3) + "' and EMP_CODE='" + empCode + "' and COMPONENT_TYPE='" + EarningCompType.get(j).toString() + "' and Date_FORMAT(MONTH_START_DATE,'%Y%m') between '" + frmDate.substring(0, 6) + "' and '" + toDate.substring(0, 6) + "' order by COMPONENT_TYPE  ").getResultList();
                    } else if (purposeType.equalsIgnoreCase("2:Gross Deduction")) {
                        EarningSalHeaddata = em.createNativeQuery("select AMOUNT ,PURPOSE_CODE from hr_salary_processing_dtl where PURPOSE_CODE in('PUR002','PUR003','PUR004') and MONTH='" + monthList.get(i).toString().substring(3) + "' and EMP_CODE='" + empCode + "'and COMPONENT_TYPE='" + EarningCompType.get(j).toString() + "' and  Date_FORMAT(MONTH_START_DATE,'%Y%m') between '" + frmDate.substring(0, 6) + "' and '" + toDate.substring(0, 6) + "' order by COMPONENT_TYPE").getResultList();
                    }

                    if (purposeType.equalsIgnoreCase("1:Gross Earning")) {
                        componentList = em.createNativeQuery("select distinct COMPONENT_TYPE from hr_salary_processing_dtl where PURPOSE_CODE='PUR001' and MONTH='" + monthList.get(i).toString().substring(3) + "' and EMP_CODE='" + empCode + "'and Date_FORMAT(MONTH_START_DATE,'%Y%m') between '" + frmDate.substring(0, 6) + "' and '" + toDate.substring(0, 6) + "' order by COMPONENT_TYPE ").getResultList();
                    } else if (purposeType.equalsIgnoreCase("2:Gross Deduction")) {
                        componentList = em.createNativeQuery("select distinct COMPONENT_TYPE from hr_salary_processing_dtl where  PURPOSE_CODE in('PUR002','PUR003','PUR004') and MONTH='" + monthList.get(i).toString().substring(3) + "' and EMP_CODE='" + empCode + "' and Date_FORMAT(MONTH_START_DATE,'%Y%m') between '" + frmDate.substring(0, 6) + "' and '" + toDate.substring(0, 6) + "' order by COMPONENT_TYPE ").getResultList();
                    }

                    for (int d = 0; d < componentList.size(); d++) {

                        currentcomponentList.add(componentList.get(d).toString());
                    }

                    if (!EarningSalHeaddata.isEmpty()) {
                        for (Object obj : EarningSalHeaddata) {
                            Object[] ele = (Object[]) obj;
                            FinancialSalProjectionPojo fspErn = new FinancialSalProjectionPojo();
                            if (currentcomponentList.contains(EarningCompType.get(j).toString())) {
                                if (!EarningCompType.get(j).toString().contains("PF CONTRIBUTION")) {
                                    fspErn.setComponentType(EarningCompType.get(j).toString());
                                    fspErn.setComponentAmt(BigDecimal.valueOf(Double.valueOf(ele[0].toString())));
                                    fspErn.setMonth(monthList.get(i).toString());
                                    fspErn.setPurposeType(purposeType);
                                    dataList.add(fspErn);
                                }
                            } else {
                                if (!EarningCompType.get(j).toString().contains("PF CONTRIBUTION")) {
                                    fspErn.setComponentType(EarningCompType.get(j).toString());
                                    fspErn.setComponentAmt(BigDecimal.ZERO);
                                    fspErn.setMonth(monthList.get(i).toString());
                                    fspErn.setPurposeType(purposeType);
                                    dataList.add(fspErn);
                                }

                            }
                        }
                    } else {
                        FinancialSalProjectionPojo fspErn = new FinancialSalProjectionPojo();
                        if (!EarningCompType.get(j).toString().contains("PF CONTRIBUTION")) {
                            fspErn.setComponentType(EarningCompType.get(j).toString());
                            fspErn.setComponentAmt(BigDecimal.ZERO);
                            fspErn.setMonth(monthList.get(i).toString());
                            fspErn.setPurposeType(purposeType);
                            dataList.add(fspErn);
                        }
                    }
                    currentcomponentList.clear();
                } else if (!getMonthList.contains(monthList.get(i).toString().substring(3)) && var == 1) {

                    if (purposeType.equalsIgnoreCase("1:Gross Earning")) {
                        EarningSalHeaddata = em.createNativeQuery("Select distinct AMOUNT,PURPOSE_CODE  from hr_salary_processing_dtl where PURPOSE_CODE='PUR001' and MONTH='" + MaxOfGetMonthList + "' and EMP_CODE='" + empCode + "' and COMPONENT_TYPE='" + EarningCompType.get(j).toString() + "' and Date_FORMAT(MONTH_START_DATE,'%Y%m') between '" + frmDate.substring(0, 6) + "' and '" + toDate.substring(0, 6) + "' order by COMPONENT_TYPE").getResultList();
                    } else if (purposeType.equalsIgnoreCase("2:Gross Deduction")) {
                        EarningSalHeaddata = em.createNativeQuery("Select distinct AMOUNT ,PURPOSE_CODE from hr_salary_processing_dtl where PURPOSE_CODE in('PUR002','PUR003','PUR004') and MONTH='" + MaxOfGetMonthList + "' and EMP_CODE='" + empCode + "'and COMPONENT_TYPE='" + EarningCompType.get(j).toString() + "'  and Date_FORMAT(MONTH_START_DATE,'%Y%m') between '" + frmDate.substring(0, 6) + "' and '" + toDate.substring(0, 6) + "' order by COMPONENT_TYPE").getResultList();
                    }
                    if (purposeType.equalsIgnoreCase("1:Gross Earning")) {
                        componentList = em.createNativeQuery("select distinct COMPONENT_TYPE from hr_salary_processing_dtl where PURPOSE_CODE='PUR001' and MONTH='" + MaxOfGetMonthList + "' and EMP_CODE='" + empCode + "' and Date_FORMAT(MONTH_START_DATE,'%Y%m') between '" + frmDate.substring(0, 6) + "' and '" + toDate.substring(0, 6) + "'").getResultList();
                    } else if (purposeType.equalsIgnoreCase("2:Gross Deduction")) {
                        componentList = em.createNativeQuery("select distinct COMPONENT_TYPE from hr_salary_processing_dtl where  PURPOSE_CODE in('PUR002','PUR003','PUR004') and MONTH='" + MaxOfGetMonthList + "' and EMP_CODE='" + empCode + "' and Date_FORMAT(MONTH_START_DATE,'%Y%m') between '" + frmDate.substring(0, 6) + "' and '" + toDate.substring(0, 6) + "'  ").getResultList();
                    }

                    for (int d = 0; d < componentList.size(); d++) {

                        currentcomponentList.add(componentList.get(d).toString());
                    }

                    if (!EarningSalHeaddata.isEmpty()) {
                        for (Object obj : EarningSalHeaddata) {
                            Object[] ele = (Object[]) obj;
                            FinancialSalProjectionPojo fspErn = new FinancialSalProjectionPojo();
                            if (currentcomponentList.contains(EarningCompType.get(j).toString())) {
                                if (!EarningCompType.get(j).toString().contains("PF CONTRIBUTION")) {
                                    fspErn.setComponentType(EarningCompType.get(j).toString());
                                    fspErn.setComponentAmt(BigDecimal.valueOf(Double.valueOf(ele[0].toString())));
                                    fspErn.setMonth(monthList.get(i).toString());
                                    fspErn.setPurposeType(purposeType);
                                    dataList.add(fspErn);
                                }
                            } else {
                                if (!EarningCompType.get(j).toString().contains("PF CONTRIBUTION")) {
                                    fspErn.setComponentType(EarningCompType.get(j).toString());
                                    fspErn.setComponentAmt(BigDecimal.ZERO);
                                    fspErn.setMonth(monthList.get(i).toString());
                                    fspErn.setPurposeType(purposeType);
                                    dataList.add(fspErn);
                                }
                            }
                        }
                    } else {
                        FinancialSalProjectionPojo fspErn = new FinancialSalProjectionPojo();
                        if (!EarningCompType.get(j).toString().contains("PF CONTRIBUTION")) {
                            fspErn.setComponentType(EarningCompType.get(j).toString());
                            fspErn.setComponentAmt(BigDecimal.ZERO);
                            fspErn.setMonth(monthList.get(i).toString());
                            fspErn.setPurposeType(purposeType);
                            dataList.add(fspErn);
                        }
                    }
                    currentcomponentList.clear();
                }
            }
        }
        return dataList;
    }

    
     public List<ConsolidatedFinancialSalaryPojo> consolidatedFinancialSalaryReport(String frdt ,String todt) throws ApplicationException{
        List<ConsolidatedFinancialSalaryPojo> salRegPojoList = new ArrayList<ConsolidatedFinancialSalaryPojo>();     
      try{    
        double compDefaultAmt = 0.00;
        List earningCompList = new ArrayList();
        List empComponentList = new ArrayList();
        List empDtl = new ArrayList();
        List empListData = new ArrayList();       
        int count=0;
        List empDetailsList = em.createNativeQuery("select distinct p.EMP_CODE from  hr_personnel_details p ,branchmaster b where b.BrnCode= p.BASE_BRANCH ").getResultList();
         if(!empDetailsList.isEmpty()){                        
           for(int i=0;i<empDetailsList.size();i++){                   
            empListData.add(empDetailsList.get(i).toString());                   
         }}
 
        List earningComp= em.createNativeQuery("select distinct COMPONENT_TYPE from hr_salary_processing_dtl where date_format(MONTH_START_DATE,'%Y%m%d') between '"+frdt+"' and '"+todt+"' ").getResultList();
         if(!earningComp.isEmpty()){
             for(int a=0;a<earningComp.size();a++){
                  if(!(earningComp.get(a).toString().contains("LIC")) && !(earningComp.get(a).toString().contains("INC")) ){
                    earningCompList.add(earningComp.get(a).toString());  
                    count++;
                  }
             } 
             earningCompList.add(new String("LIC"));
             earningCompList.add(new String("INC"));
             count = count + 2;
         }
         
         for(int i=0;i<empListData.size();i++){  
             for(int j=0;j<earningCompList.size();j++){
            empDtl = em.createNativeQuery("select hp.EMP_ID,hp.EMP_NAME,sum(hsp.SALARY) from hr_personnel_details hp ,hr_salary_processing hsp\n" +
            " where hp.EMP_CODE= hsp.EMP_CODE and hp.EMP_CODE='"+empListData.get(i).toString()+"' and Date_format(hsp.MOD_DATE,'%Y%m%d') between '"+frdt+"' and '"+todt+"'").getResultList();
              if(!empDtl.isEmpty()){          
             for(Object obj : empDtl) { 
              Object[] ele1 = (Object[]) obj;
            }}
           
         List empCompList = em.createNativeQuery("select ifnull(component_type,''),ifnull(sum(AMOUNT),0.0),ifnull(PURPOSE_CODE,'')  from hr_salary_processing_dtl where  date_format(MONTH_START_DATE,'%Y%m%d') between '"+frdt+"' and '"+todt+"' and \n" +
         "COMPONENT_TYPE like '%"+earningCompList.get(j).toString()+"%' and emp_code = '"+empListData.get(i).toString()+"'").getResultList();
          for (Object obj : empCompList) {
             Object[] ele = (Object[]) obj;
             empComponentList.add(ele[0].toString());
          }
            
           for (Object obj : empCompList) {
                ConsolidatedFinancialSalaryPojo srPojo = new ConsolidatedFinancialSalaryPojo();
              for(Object obj1 : empDtl) { 
              Object[] ele1 = (Object[]) obj1;
                srPojo.setEmpCode(empListData.get(i).toString());
                srPojo.setEmpId(ele1[0].toString());
                srPojo.setEmpName(ele1[1].toString());                                                     
                srPojo.setNetpayAmt(Double.valueOf(ele1[2].toString()));
                srPojo.setCompCount(count);
              }
              Object[] ele = (Object[]) obj;
      
              if(empComponentList.contains(ele[0].toString())){
               if(ele[2].toString().equalsIgnoreCase("PUR001")){
                srPojo.setComponentName(ele[0].toString());
                srPojo.setComponentAmount(Double.valueOf(ele[1].toString()));
                srPojo.setPurposeCode(ele[2].toString() == null ? "": ele[2].toString());
                srPojo.setPurposeType("1.E");               
               }else if(ele[2].toString().equalsIgnoreCase("PUR002") || ele[2].toString().equalsIgnoreCase("PUR003")){
              if(ele[0].toString().contains("ITAX") ||ele[0].toString().contains("INCOMETAX_") || ele[0].toString().contains("INCOME TAX") || ele[0].toString().contains("INCTAX_")|| ele[0].toString().contains("INC")){
                srPojo.setComponentName("INCOME TAX");
                srPojo.setComponentAmount(0.00-Double.valueOf(ele[1].toString()));
                srPojo.setPurposeCode(ele[2].toString() == null ? "": ele[2].toString());
                srPojo.setPurposeType("2.D");  
                
             }else if(ele[0].toString().contains("LIC") || ele[0].toString().contains("LIC_")){
                srPojo.setComponentName("LIC");
                srPojo.setComponentAmount(0.00-Double.valueOf(ele[1].toString()));
                srPojo.setPurposeCode(ele[2].toString() == null ? "": ele[2].toString());
                srPojo.setPurposeType("2.D");    
              
             }else{
                srPojo.setComponentName(ele[0].toString());
                srPojo.setComponentAmount(0.00-Double.valueOf(ele[1].toString()));
                srPojo.setPurposeCode(ele[2].toString() == null ? "": ele[2].toString());
                srPojo.setPurposeType("2.D");              
                }            
             }}else{
               if(ele[2].toString().equalsIgnoreCase("PUR001")){
                srPojo.setComponentName(ele[0].toString());
                srPojo.setComponentAmount(compDefaultAmt);
                srPojo.setPurposeCode(ele[2].toString() == null ? "": ele[2].toString());
                srPojo.setPurposeType("1.E");  
             } else if(ele[2].toString().equalsIgnoreCase("PUR002") || ele[2].toString().equalsIgnoreCase("PUR003")){
                srPojo.setComponentName(ele[0].toString());
                srPojo.setComponentAmount(compDefaultAmt);
                srPojo.setPurposeCode(ele[2].toString() == null ? "": ele[2].toString());
                srPojo.setPurposeType("2.D");   
             }}  
                salRegPojoList.add(srPojo);             
         }
         
         }
     }}catch(Exception e){
         new ApplicationException(e.getMessage());
     }
        return  salRegPojoList;
     }
    
}
