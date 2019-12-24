/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.common.to.HrDetailsProgramTO;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrDetailsProgram;
import com.hrms.entity.hr.HrMstProgram;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
public class HrDetailsProgramDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrDetailsProgramDAO.class);

    /**
     * @param entityManager
     */
    public HrDetailsProgramDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrDetailsProgramDAO Initializing...");
    }

    public List<HrDetailsProgramTO> programDetailGridOnload(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("programDetailGridOnload()");
        }
        try {
            String queryString = "SELECT A.hrMstProgramPK.compCode,A.hrMstProgramPK.progCode,A.progName,A.trngFrom,A.trngTo,A.trngCost,A.inextHouse,A.instCode,A.facuName FROM HrMstProgram A WHERE A.hrMstProgramPK.compCode=:value1";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("value1", compCode);
            List<HrDetailsProgramTO> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("instDetailGridOnload() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
    }

    public List minSkilSet(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("minSkilSet()");
        }
        List progNameLt = new ArrayList();
        try {
            Query query = entityManager.createQuery("SELECT A.hrMstStructPK.structCode,A.description FROM HrMstStruct A WHERE A.hrMstStructPK.compCode = :value1 AND A.hrMstStructPK.structCode LIKE 'SKI%' ");
            query.setParameter("value1", compCode);
            progNameLt = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
        return progNameLt;
    }

    public List skilProgName(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("skilProgName()");
        }
        List progNameLt = new ArrayList();
        try {
            Query query = entityManager.createQuery("SELECT A.hrMstProgramPK.progCode,A.progName FROM HrMstProgram A WHERE A.hrMstProgramPK.compCode = :value1");
            query.setParameter("value1", compCode);
            progNameLt = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
        return progNameLt;
    }

    public List instDeptNameFromGrid(String instDeptCode, String training) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("instDeptNameFromGrid()");
        }
        List gridDetail = new ArrayList();
        try {
            if (training.equalsIgnoreCase("E")) {
                String queryString = "SELECT A.instName FROM HrMstInstitute A WHERE A.hrMstInstitutePK.instCode = :value1";
                Query query = entityManager.createQuery(queryString);
                query.setParameter("value1", instDeptCode);
                gridDetail = query.getResultList();
            } else {
                String queryString = "SELECT A.description FROM HrMstStruct A WHERE A.hrMstStructPK.structCode = :value1";
                Query query = entityManager.createQuery(queryString);
                query.setParameter("value1", instDeptCode);
                gridDetail = query.getResultList();
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
        return gridDetail;
    }

    public List skillGrdLoad(int compCode) throws DAOException {
        List progNameLt = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("skillGrdLoad()");
        }
        try {
            Query query = entityManager.createQuery("SELECT A.hrDetailsProgramPK.progCode,B.progName," +
                    " A.hrDetailsProgramPK.skillCode,C.description " +
                    " FROM HrDetailsProgram A,HrMstProgram B,HrMstStruct C " +
                    " WHERE A.hrDetailsProgramPK.compCode=:value1 " +
                    " AND A.hrDetailsProgramPK.compCode = B.hrMstProgramPK.compCode " +
                    " AND B.hrMstProgramPK.compCode=C.hrMstStructPK.compCode " +
                    " AND A.hrDetailsProgramPK.progCode=B.hrMstProgramPK.progCode " +
                    " AND A.hrDetailsProgramPK.skillCode=C.hrMstStructPK.structCode");
            query.setParameter("value1", compCode);
            progNameLt = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
        return progNameLt;
    }

    public List institutesSearchGrid(int compCode, String training) throws DAOException {
        List gridDetail = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("institutesSearchGrid()");
        }
        try {
            if (training.equalsIgnoreCase("E")) {
                String queryString = "SELECT A.hrMstInstitutePK.instCode,A.instName FROM HrMstInstitute A WHERE A.hrMstInstitutePK.compCode = :value1 AND A.hrMstInstitutePK.instCode LIKE 'INS%'";
                Query query = entityManager.createQuery(queryString);
                query.setParameter("value1", compCode);
                gridDetail = query.getResultList();
            } else {
                String queryString = "SELECT A.hrMstStructPK.structCode,A.description FROM HrMstStruct A WHERE A.hrMstStructPK.compCode = :value1 AND A.hrMstStructPK.structCode LIKE 'DEP%'";
                Query query = entityManager.createQuery(queryString);
                query.setParameter("value1", compCode);
                gridDetail = query.getResultList();
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
        return gridDetail;
    }

    public List facultySearchGrid(int compCode) throws DAOException {
        List gridDetail = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("facultySearchGrid()");
        }
        try {
            String queryString = "SELECT A.hrMstStructPK.structCode,A.description FROM HrMstStruct A WHERE A.hrMstStructPK.compCode = :value1 AND A.hrMstStructPK.structCode LIKE 'FAC%'";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("value1", compCode);
            gridDetail = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
        return gridDetail;
    }

    public List employeeSearchGrid(int compCode, String deptCode) throws DAOException {
        List gridDetail = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("employeeSearchGrid()");
        }
        try {
            String queryString = "SELECT A.empName,A.empId,A.hrPersonnelDetailsPK.empCode FROM HrPersonnelDetails A WHERE A.hrPersonnelDetailsPK.compCode = :value1 AND A.deptCode = :value2";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("value1", compCode);
            query.setParameter("value2", deptCode);
            gridDetail = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
        return gridDetail;
    }

    public String getMaxProgramCode(int compCode) throws DAOException {
        String code = "";
        if (logger.isDebugEnabled()) {
            logger.debug("getMaxProgramCode()");
        }
        Integer tmpCode;
        try {
            String queryString = "SELECT MAX(A.hrMstProgramPK.progCode) FROM HrMstProgram A WHERE A.hrMstProgramPK.compCode = :value1";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("value1", compCode);
            List chk = query.getResultList();
            if (chk == null || chk.isEmpty()) {
                code = "001";
                return code;
            } else {
                if (chk.get(0) == null) {
                    code = "001";
                    return code;
                } else {
                    code = chk.get(0).toString();
                    tmpCode = Integer.parseInt(code.substring(3));
                    tmpCode = tmpCode + 1;
                    code = tmpCode.toString();
                    int length = code.length();
                    int addedZero = 3 - length;
                    for (int i = 1; i <= addedZero; i++) {
                        code = "0" + code;
                    }
                    return code;
                }
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
    }

    public HrMstProgram findByCompCodeAndProgramCodeMstProg(int compCode, String progCode) throws DAOException {
        HrMstProgram instance = new HrMstProgram();
        if (logger.isDebugEnabled()) {
            logger.debug("findByCompCodeAndProgramCodeMstProg()");
        }
        try {
            final String queryString = "select A from HrMstProgram A where A.hrMstProgramPK.compCode = :value1 " +
                    "and A.hrMstProgramPK.progCode = :value2";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("value1", compCode);
            query.setParameter("value2", progCode);
            instance = (HrMstProgram) query.getSingleResult();
            return instance;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
    }

    public List<HrDetailsProgram> findByCompCodeAndProgramCodeDetailsProg(int compCode, String progCode) throws DAOException {
        List<HrDetailsProgram> instanceList = new ArrayList<HrDetailsProgram>();
        if (logger.isDebugEnabled()) {
            logger.debug("findByCompCodeAndProgramCodeDetailsProg()");
        }
        try {
            final String queryString = "select A from HrDetailsProgram A where A.hrDetailsProgramPK.compCode = :value1 " +
                    "and A.hrDetailsProgramPK.progCode = :value2";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("value1", compCode);
            query.setParameter("value2", progCode);
            instanceList = query.getResultList();
            return instanceList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
    }

    public List checkAlreadyExistance(int compCode, String progCode, String skillCode) throws DAOException {
        List resultLt = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("checkAlreadyExistance()");
        }
        try {
            Query query = entityManager.createQuery("SELECT A.hrDetailsProgramPK.progCode FROM HrDetailsProgram A WHERE A.hrDetailsProgramPK.compCode = :value1 AND A.hrDetailsProgramPK.progCode=:value2 AND A.hrDetailsProgramPK.skillCode=:value3");
            query.setParameter("value1", compCode);
            query.setParameter("value2", progCode);
            query.setParameter("value3", skillCode);
            resultLt = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
        return resultLt;
    }

    public HrDetailsProgram findByCompCodeAndProgramCodeAndSkillCode(int compCode, String progCode, String skillCode) throws DAOException {
        HrDetailsProgram instance = new HrDetailsProgram();
        if (logger.isDebugEnabled()) {
            logger.debug("checkAlreadyExistance()");
        }
        try {
            final String queryString = "select A from HrDetailsProgram A where A.hrDetailsProgramPK.compCode = :value1 " +
                    "and A.hrDetailsProgramPK.progCode = :value2 and A.hrDetailsProgramPK.skillCode = :value3";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("value1", compCode);
            query.setParameter("value2", progCode);
            query.setParameter("value3", skillCode);
            List chk = query.getResultList();
            if (!chk.isEmpty()) {
                instance = (HrDetailsProgram) query.getSingleResult();
            }
            return instance;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programDetailGridOnload()");
            throw new DAOException(e);
        }
    }
}
