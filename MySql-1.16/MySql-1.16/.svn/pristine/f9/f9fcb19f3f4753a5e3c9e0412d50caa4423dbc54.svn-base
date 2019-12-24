/*
 * CREATED BY   :  ROHIT KRISHNA GUPTA
 * CREATION DATE:  16 JUNE 2011
 */
package com.hrms.facade.hrd;

import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrDetailsProgramTO;
import com.hrms.common.to.HrMstProgramTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.HrDetailsProgramDAO;
import com.hrms.dao.HrMstProgramDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrDetailsProgram;
import com.hrms.entity.hr.HrMstProgram;
import java.util.ArrayList;
import java.util.Date;
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
@Stateless(mappedName = "ProgramDetailsBean")
@Remote({ProgramDetailsRemote.class})
public class ProgramDetailsBean implements ProgramDetailsRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    private static final Logger logger = Logger.getLogger(ProgramDetailsBean.class);

    @Override
    public List<HrDetailsProgramTO> programDetailGridLoad(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<HrDetailsProgramTO> gridDetail = new ArrayList<HrDetailsProgramTO>();
        try {
            HrDetailsProgramDAO dtProg = new HrDetailsProgramDAO(em);
            gridDetail = dtProg.programDetailGridOnload(compCode);
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
        return gridDetail;
    }

    @Override
    public List minSkilSetList(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        List resultLt = new ArrayList();
        try {
            HrDetailsProgramDAO dtProg = new HrDetailsProgramDAO(em);
            resultLt = dtProg.minSkilSet(compCode);
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
    public List skilProgNameList(int compCode) throws ApplicationException {
        List resultLt = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrDetailsProgramDAO dtProg = new HrDetailsProgramDAO(em);
            resultLt = dtProg.skilProgName(compCode);
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
    public List instituteDeptName(String instDeptCode, String training) throws ApplicationException {
        List gridDetail = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrDetailsProgramDAO dtProg = new HrDetailsProgramDAO(em);
            gridDetail = dtProg.instDeptNameFromGrid(instDeptCode, training);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return gridDetail;
    }

    @Override
    public List skilGridDetail(int compCode) throws ApplicationException {
        List resultLt = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrDetailsProgramDAO dtProg = new HrDetailsProgramDAO(em);
            resultLt = dtProg.skillGrdLoad(compCode);
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
    public List instituteSearchGridLoad(int compCode, String training) throws ApplicationException {
        List gridDetail = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrDetailsProgramDAO dtProg = new HrDetailsProgramDAO(em);
            gridDetail = dtProg.institutesSearchGrid(compCode, training);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return gridDetail;
    }

    @Override
    public List facultySearchGridLoad(int compCode) throws ApplicationException {
        List gridDetail = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrDetailsProgramDAO dtProg = new HrDetailsProgramDAO(em);
            gridDetail = dtProg.facultySearchGrid(compCode);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return gridDetail;
    }

    @Override
    public List employeeSearchGridLoad(int compCode, String intDeptCode) throws ApplicationException {
        List gridDetail = new ArrayList();
        long begin = System.nanoTime();
        try {
            HrDetailsProgramDAO dtProg = new HrDetailsProgramDAO(em);
            gridDetail = dtProg.employeeSearchGrid(compCode, intDeptCode);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return gridDetail;
    }

    @Override
    public String getMaxProgCode(int compCode) throws ApplicationException {
        String code = "";
        long begin = System.nanoTime();
        try {
            HrDetailsProgramDAO dtProg = new HrDetailsProgramDAO(em);
            code = dtProg.getMaxProgramCode(compCode);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return code;
    }

    @Override
    public String saveRecord(HrMstProgramTO instObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(instObj);
        HrMstProgram obj = new HrMstProgram();
        try {
            HrMstProgramDAO hrMstProgDao = new HrMstProgramDAO(em);
            obj = ObjectAdaptorHr.adaptToHrMstProgramEntity(instObj);
            hrMstProgDao.save(obj);
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
    public String updateRecord(HrMstProgramTO instObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(instObj);
        HrMstProgram obj = new HrMstProgram();
        try {
            HrMstProgramDAO hrMstProgDao = new HrMstProgramDAO(em);
            obj = ObjectAdaptorHr.adaptToHrMstProgramEntity(instObj);
            hrMstProgDao.update(obj);
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
    public String deleteRecord(int compCode, String progCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstProgram mstProg = new HrMstProgram();
        try {
            HrDetailsProgramDAO hrMstProgDao = new HrDetailsProgramDAO(em);
            mstProg = hrMstProgDao.findByCompCodeAndProgramCodeMstProg(compCode, progCode);
            hrMstProgDao.delete(mstProg);
            List<HrDetailsProgram> instanceList = hrMstProgDao.findByCompCodeAndProgramCodeDetailsProg(compCode, progCode);
            if(!instanceList.isEmpty())
            {
                for(HrDetailsProgram hdp:instanceList)
                {
                     hrMstProgDao.delete(hdp);
                }
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
    public String saveSkillRecord(HrDetailsProgramTO detProgObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(detProgObj);
        HrDetailsProgram obj = new HrDetailsProgram();
        String result = "";
        try {
            HrDetailsProgramDAO hrDetProgDao = new HrDetailsProgramDAO(em);
            List resultLt = new ArrayList();
            resultLt = hrDetProgDao.checkAlreadyExistance(detProgObj.getHrDetailsProgramPKTO().getCompCode(), detProgObj.getHrDetailsProgramPKTO().getProgCode(), detProgObj.getHrDetailsProgramPKTO().getSkillCode());
            if (!resultLt.isEmpty() || resultLt.size() > 0) {
                result = "false1";
                return result;
            }
           // obj = ObjectAdaptorHr.adaptToHrDataReferenceEntity(detProgObj);
            obj = ObjectAdaptorHr.adaptToHrDetailsProgramEntity(detProgObj);
            hrDetProgDao.save(obj);
            result = "true";
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

//    public List programDetailGridForFindBtn(int compCode, String characters) {
//        List gridDetail = new ArrayList();
//        try {
//            //gridDetail = dao.programDetailOnFindButton(compCode, characters);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return gridDetail;
//    }
    @Override
    public String updateSkillRecord(int compCode, String progCode, String skillcode, String oldProgCode, String oldSkillCode)throws ApplicationException {
        String message = "";
        try {
            HrDetailsProgramDAO hrDetProgDao = new HrDetailsProgramDAO(em);
            List resultLt = new ArrayList();
            resultLt = hrDetProgDao.checkAlreadyExistance(compCode, progCode, skillcode);
            if (!resultLt.isEmpty() || resultLt.size() > 0) {
                message = "false1";
                return message;
            }
            Date date = new Date();
            Query query = em.createQuery("UPDATE HrDetailsProgram A SET A.hrDetailsProgramPK.progCode=:value1 , A.hrDetailsProgramPK.skillCode=:value2,A.statUpFlag=:value6,A.modDate=:value7 WHERE A.hrDetailsProgramPK.compCode=:value3 AND A.hrDetailsProgramPK.progCode=:value4 AND A.hrDetailsProgramPK.skillCode=:value5");
            query.setParameter("value1", progCode);
            query.setParameter("value2", skillcode);
            query.setParameter("value3", compCode);
            query.setParameter("value4", oldProgCode);
            query.setParameter("value5", oldSkillCode);
            query.setParameter("value6", "Y");
            query.setParameter("value7", date);
            int var = query.executeUpdate();
            if (var <= 0) {
                message = "false";
                return message;
            } else {
                message = "true";
                return message;
            }
//            HrDetailsProgram obj = new HrDetailsProgram();
//            HrDetailsProgramPK objpk = new HrDetailsProgramPK();
//            //obj = dao.findByCompCodeAndProgramCodeAndSkillCode(compCode, oldProgCode, oldSkillCode);
//            objpk.setProgCode(progCode);
//            objpk.setSkillCode(skillcode);
//            obj.setHrDetailsProgramPK(objpk);
//            obj.setDefaultComp(100);
//            obj.setStatFlag("Y");
//            obj.setStatUpFlag("Y");
//            //message = dao.updateSkill(obj);
//            if (message.equalsIgnoreCase("true")) {
//                //ut.commit();
//                message = "true";
//                return message;
//            } else {
//                //ut.rollback();
//                message = "false";
//                return message;
//            }
        }catch (DAOException e) {
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
        
    }

    @Override
    public String deleteSkillRecord(int compCode, String progCode, String skillCode)throws ApplicationException{
        try {
            HrDetailsProgramDAO hrDetProgDao = new HrDetailsProgramDAO(em);
            HrDetailsProgram obj = new HrDetailsProgram();
            obj = hrDetProgDao.findByCompCodeAndProgramCodeAndSkillCode(compCode, progCode, skillCode);
            hrDetProgDao.delete(obj);
            return "true";
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
        
    }
}
