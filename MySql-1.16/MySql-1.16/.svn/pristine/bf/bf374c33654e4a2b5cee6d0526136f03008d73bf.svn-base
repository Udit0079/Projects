/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.hrd;

import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrTrainingPlanPKTO;
import com.hrms.common.to.HrTrainingPlanTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.HrPersonnelDetailsDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrTrainingPlan;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author ROHIT KRISHNA
 */
@Stateless(mappedName = "TrainingPlanBean")
@Remote({TrainingPlanRemote.class})
public class TrainingPlanBean implements TrainingPlanRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    private static final Logger logger = Logger.getLogger(InstituteDetailsBean.class);
//    @EJB
//    HrPersonnelDetailsLocal daoHrPeronalDet;
//    @EJB
//    HrTrainingPlanLocal daoTrngPlan;

    @Override
    public List gridLoadOnAddBtn(int compCode, int searchFlag, String searchCriteria) throws ApplicationException {
        List resultLt = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrPersonnelDetailsDAO hrPersonalDetdaoObj = new HrPersonnelDetailsDAO(em);
            resultLt = hrPersonalDetdaoObj.searchEmployeeOnAddButton(compCode, searchFlag, searchCriteria);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return resultLt;
    }

    @Override
    public List trainingSearchGridBtn(int compCode) throws ApplicationException {
        List resultLt = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrPersonnelDetailsDAO hrPersonalDetdaoObj = new HrPersonnelDetailsDAO(em);
            resultLt = hrPersonalDetdaoObj.trainingSearchGrid(compCode);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return resultLt;
    }

    @Override
    public List programSearchGridBtn(int compCode, String progCode) throws ApplicationException {
        List resultLt = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrPersonnelDetailsDAO hrPersonalDetdaoObj = new HrPersonnelDetailsDAO(em);
            resultLt = hrPersonalDetdaoObj.programSearchGrid(compCode, progCode);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return resultLt;
    }

    @Override
    public List fillValuesOfEmpSearch(int compCode, String empId) throws ApplicationException {
        List resultLt = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrPersonnelDetailsDAO hrPersonalDetdaoObj = new HrPersonnelDetailsDAO(em);
            resultLt = hrPersonalDetdaoObj.empDetailFromGrid(compCode, empId);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return resultLt;
    }

    @Override
    public String saveTrainingPlanRecord(HrTrainingPlanTO trngPlanObj, String mode) throws ApplicationException {
        String result = "";
        long begin = System.nanoTime();
        Validator.isNull(trngPlanObj);
        try {
            List resultLt = new ArrayList();
            HrTrainingPlan obj = new HrTrainingPlan();
            HrPersonnelDetailsDAO hrPersonalDetdaoObj = new HrPersonnelDetailsDAO(em);
            resultLt = hrPersonalDetdaoObj.checkAlreadyExistance(trngPlanObj.getHrTrainingPlanPKTO().getCompCode(), trngPlanObj.getHrTrainingPlanPKTO().getEmpCode(), trngPlanObj.getHrTrainingPlanPKTO().getTrngCode(), trngPlanObj.getHrTrainingPlanPKTO().getProgCode(), trngPlanObj.getHrTrainingPlanPKTO().getDateFrom(), trngPlanObj.getHrTrainingPlanPKTO().getDateTo());
            if (mode.equalsIgnoreCase("1")) {
                if (!resultLt.isEmpty() || resultLt.size() > 0) {
                    result = "Entry already exists!";
                    return result;
                }
                obj = ObjectAdaptorHr.adaptToHrTrainingPlanEntity(trngPlanObj);
                hrPersonalDetdaoObj.save(obj);
                result = "Record has been saved successfully!";

            } else if (mode.equalsIgnoreCase("2")) {
                if (!resultLt.isEmpty() || resultLt.size() > 0) {
                    obj = ObjectAdaptorHr.adaptToHrTrainingPlanEntity(trngPlanObj);
                    hrPersonalDetdaoObj.update(obj);
                    result = "Record has been updated successfully!";
                } else {
                    result = "No record exists to update!";
                }
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveLeaveDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveLeaveDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveLeaveDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return result;
    }

    @Override
    //public String deleteRecord(int compCode, int empCode, String trngCode, String progCode, String fromDt, String toDt) throws ApplicationException {
    public String deleteRecord(HrTrainingPlanPKTO trngPlanObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(trngPlanObj);
        try {
            HrTrainingPlan obj = new HrTrainingPlan();
            HrPersonnelDetailsDAO hrPersonalDetdaoObj = new HrPersonnelDetailsDAO(em);
            obj = hrPersonalDetdaoObj.findByPrimaryKey(trngPlanObj.getCompCode(), trngPlanObj.getEmpCode(), trngPlanObj.getTrngCode(), trngPlanObj.getProgCode(), trngPlanObj.getDateFrom(), trngPlanObj.getDateTo());
            hrPersonalDetdaoObj.delete(obj);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveLeaveDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveLeaveDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveLeaveDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "true";
    }

    @Override
    public List trainingPlanGridDetail(int compCode, String trngExec) throws ApplicationException {
        List resultLt = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrPersonnelDetailsDAO hrPersonalDetdaoObj = new HrPersonnelDetailsDAO(em);
            resultLt = hrPersonalDetdaoObj.trainingPlanGetDetail(compCode, trngExec);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return resultLt;
    }
//    public String updateTrainingPlanRecord(List<TrainingPlanData> trngObj, int compCode, int empCode, String trngCode, String progCode, String fromDt, String toDt) {
//        UserTransaction ut = context.getUserTransaction();
//        String message = "";
//        try {
//            ut.begin();
//            HrTrainingPlan obj = new HrTrainingPlan();
//            HrTrainingPlanPK objPk = new HrTrainingPlanPK();
//            obj = daoTrngPlan.findByPrimaryKey(compCode, empCode, trngCode, progCode, fromDt, toDt);
//            objPk.setTrngCode(trngObj.get(0).getTrainingCode());
//            objPk.setProgCode(trngObj.get(0).getProgCode());
//            objPk.setDateFrom(trngObj.get(0).getDateFrom());
//            objPk.setDateTo(trngObj.get(0).getDateTo());
//
//            String tempDtFrom = trngObj.get(0).getDateFrom();
//            String tempDtTo = trngObj.get(0).getDateTo();
//            System.out.println("tempDtFrom,tempDtTo:======="+tempDtFrom+"-"+tempDtTo);
//            String dtFrom = tempDtFrom.substring(6) + tempDtFrom.substring(3, 5) + tempDtFrom.substring(0, 2);
//            String dtTo = tempDtTo.substring(6) + tempDtTo.substring(3, 5) + tempDtTo.substring(0, 2);
//            int trngDurdays = (int) dayDiff(ymd.parse(dtFrom), ymd.parse(dtTo));
//            obj.setHrTrainingPlanPK(objPk);
//            obj.setTrngDur(trngDurdays);
//            obj.setApprDet(trngObj.get(0).getApprDet().charAt(0));
//            obj.setStatFlag(trngObj.get(0).getStatFlag());
//            obj.setStatUpFlag(trngObj.get(0).getStatUpFlag());
//            obj.setTrngExec('N');
//            obj.setModDate(trngObj.get(0).getModDt());
//            obj.setAuthBy(trngObj.get(0).getAuthCode());
//            obj.setEnteredBy(trngObj.get(0).getEnterBY());
//            message = daoTrngPlan.update(obj);
//            System.out.println("message:======="+message);
//            if (message.equalsIgnoreCase("true")) {
//                ut.commit();
//                message = "true";
//                return message;
//            } else {
//                ut.rollback();
//                message = "false";
//                return message;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return message;
//    }
}
