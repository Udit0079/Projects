/*
 * CREATED BY   :  ROHIT KRISHNA GUPTA
 * CREATION DATE:  13 JUNE 2011
 */
package com.hrms.facade.hrd;

import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstInstitutePKTO;
import com.hrms.common.to.HrMstInstituteTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.HrMstInstituteDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrMstInstitute;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author ROHIT KRISHNA
 */
@Stateless(mappedName = "InstituteDetailsBean")
@Remote({InstituteDetailsRemote.class})
public class InstituteDetailsBean implements InstituteDetailsRemote {

    private static final Logger logger = Logger.getLogger(InstituteDetailsBean.class);
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;

    @Override
    public List<HrMstInstituteTO> instDetailGridLoad(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<HrMstInstituteTO> resultLt = new ArrayList<HrMstInstituteTO>();
        try {
            HrMstInstituteDAO structMasterDao = new HrMstInstituteDAO(em);
            resultLt = structMasterDao.instDetailGridOnload(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
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
    public String saveRecord(HrMstInstituteTO instObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(instObj);
        HrMstInstitute obj = new HrMstInstitute();
        try {
            HrMstInstituteDAO hrMstInstDao = new HrMstInstituteDAO(em);
            obj = ObjectAdaptorHr.adaptToHrMstInstituteEntity(instObj);
            hrMstInstDao.save(obj);

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
    public String getMaxInstCode(int compCode) {
        String code = "";
        try {
            HrMstInstituteDAO hrMstInstituteDao = new HrMstInstituteDAO(em);
            code = hrMstInstituteDao.getMaxInstitudeCode(compCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return code;
    }

    @Override
    public String updateInstituteRecord(HrMstInstituteTO instObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(instObj);
        HrMstInstitute obj = new HrMstInstitute();
        try {
            HrMstInstituteDAO hrMstInstDao = new HrMstInstituteDAO(em);
            obj = ObjectAdaptorHr.adaptToHrMstInstituteEntity(instObj);
            hrMstInstDao.update(obj);
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

//    @Override
//    public String deleteRecordOfInstitues(int compCode, String instCode) throws ApplicationException {
//        long begin = System.nanoTime();
////        Validator.isNull(instObj);
////        HrMstInstitutePK objpk = new HrMstInstitutePK();
////        HrMstInstitute obj = new HrMstInstitute();
//        try {
//            HrMstInstituteDAO hrMstInstDao = new HrMstInstituteDAO(em);
////            objpk = ObjectAdaptorHr.adaptHrMstInstitutePKEntity(instObj);
////            obj.setHrMstInstitutePK(objpk);
//            HrMstInstitute obj = new HrMstInstitute();
//            HrMstInstitutePK objPk = new HrMstInstitutePK();
//            objPk.setCompCode(compCode);
//            objPk.setInstCode(instCode);
//            obj.setHrMstInstitutePK(objPk);
//            hrMstInstDao.delete(obj);
//        } catch (DAOException e) {
//            logger.error("Exception occured while executing method saveLeaveDetail()", e);
//            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
//                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
//                        "Duplicate entity exists."), e);
//            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
//                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
//            } else {
//                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
//                        "System exception has occured"), e);
//            }
//        } catch (Exception e) {
//            logger.error("Exception occured while executing method saveLeaveDetail()", e);
//            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
//                    "System exception has occured"));
//        }
//        if (logger.isDebugEnabled()) {
//            logger.debug("Execution time for saveLeaveDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
//        }
//        return "true";
//    }
    @Override
    public String deleteInstituteDetail(HrMstInstitutePKTO instObj) throws ApplicationException {
        String message = "";
        Validator.isNull(instObj);
        try {
            Query query = em.createQuery("DELETE FROM HrMstInstitute A WHERE A.hrMstInstitutePK.compCode=:value1 AND A.hrMstInstitutePK.instCode=:value2");
            query.setParameter("value1", instObj.getCompCode());
            query.setParameter("value2", instObj.getInstCode());
            int var = query.executeUpdate();
            if (var <= 0) {
                message = "false";
                return message;
            } else {
                message = "true";
                return message;
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteInstituteDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
    }
}
