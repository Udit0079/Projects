/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.entity.hr.HrPersonnelDetails;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrPersonnelDetailsPK;
import com.hrms.entity.hr.HrTrainingPlan;
import com.hrms.exception.ApplicationException;
import java.math.BigInteger;
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
public class HrPersonnelDetailsDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrPersonnelDetailsDAO.class);

    /**
     * @param entityManager
     */
    public HrPersonnelDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrPersonnelDetailsDAO Initializing...");
    }

    public List<HrPersonnelDetails> findByCompCode(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByCompCode()");
        }
        try {
            String q = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode=:companyCode order by h.empName";
            Query query = entityManager.createQuery(q);
            query.setParameter("companyCode", compCode);
            List<HrPersonnelDetails> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findByCompCode() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
    }

    public List directRecruitmentInterviewerDetail(int compcode, String empId) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.DIRECT_RECRUITMENT_INTERVIEW_SEARCH);
            query.setParameter("value1", "%" + empId + "%");
            query.setParameter("value2", compcode);
            query.setParameter("value3", 'Y');
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method directRecruitmentInterviewerDetail()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentInterviewerDetail()");
            throw new DAOException(e);
        }
    }

    public List directRecruitmentInterviewerDetailByName(int compcode, String empName) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.DIRECT_RECRUITMENT_INTERVIEW_SEARCH_BY_NAME);
            query.setParameter("value1", "%" + empName + "%");
            query.setParameter("value2", compcode);
            query.setParameter("value3", 'Y');
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method directRecruitmentInterviewerDetailByName()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentInterviewerDetailByName()");
            throw new DAOException(e);
        }
    }

    public List transferEmpDetail(String empId, int compcode) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.TRANSFER_EMP_DETAIL);
            query.setParameter("value1", empId);
            query.setParameter("value2", compcode);
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method directRecruitmentInterviewerViewDetail()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentInterviewerViewDetail()");
            throw new DAOException(e);
        }
    }

    public List<HrPersonnelDetails> findEmpByCompCodeWithStatusY(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findEmpByCompCode()");
        }
        List<HrPersonnelDetails> hrPersonnelDetailsList = new ArrayList<HrPersonnelDetails>();
        try {
            Query q1 = entityManager.createQuery(NamedQueryConstant.FIND_EMP_BY_COMPCODE_STATUS_Y);
            q1.setParameter("compCode",compCode);
            q1.setParameter("empStatus",'Y');
            hrPersonnelDetailsList = q1.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findEmpByCompCode() - end - return value=" + hrPersonnelDetailsList);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findEmpByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEmpByCompCode()");
            throw new DAOException(e);
        }
        return hrPersonnelDetailsList;
    }

    public List<HrPersonnelDetails> findEmpByCompCodeTypeValue(int compCode, String type, String value) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findEmpByCompCodeTypeValue()");
        }
        List<HrPersonnelDetails> hrPersonnelDetailsList = new ArrayList<HrPersonnelDetails>();
        try {
            if (type.equalsIgnoreCase("ID")) {
                Query q1 = entityManager.createQuery(NamedQueryConstant.FIND_BY_COMPCODE_AND_LIKE_EMP_ID);
                q1.setParameter("compCode", compCode);
                q1.setParameter("empId", "%" + value + "%");
                hrPersonnelDetailsList = q1.getResultList();
            } else if (type.equalsIgnoreCase("Name")) {
                Query q2 = entityManager.createQuery(NamedQueryConstant.FIND_BY_COMPCODE_AND_LIKE_EMP_NAME);
                q2.setParameter("compCode", compCode);
                q2.setParameter("empName", "%" + value + "%");
                hrPersonnelDetailsList = q2.getResultList();
            }
            if (logger.isDebugEnabled()) {
                logger.debug("findEmpByCompCode() - end - return value=" + hrPersonnelDetailsList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method findEmpByCompCodeTypeValue()");
            throw new DAOException(e);
        }
        return hrPersonnelDetailsList;
    }

    public List getShiftTableData(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getShiftTableData()");
        }
        List shiftTableDataList = new ArrayList();
        try {
            String query =
                    " SELECT A.hrShiftMapPK.empCode, B.empId, B.empName, A.hrShiftMapPK.shiftCode, C.shiftDesc "
                    + " FROM HrShiftMap A, "
                    + " HrPersonnelDetails B, "
                    + " HrMstShift C "
                    + " WHERE A.hrShiftMapPK.compCode = :compCode "
                    + " AND A.hrShiftMapPK.compCode=B.hrPersonnelDetailsPK.compCode "
                    + " AND A.hrShiftMapPK.compCode=C.hrMstShiftPK.compCode "
                    + " AND A.hrShiftMapPK.empCode=B.hrPersonnelDetailsPK.empCode "
                    + " AND A.hrShiftMapPK.shiftCode=C.hrMstShiftPK.shiftCode "
                    + " order by A.hrShiftMapPK.empCode ";
            Query q1 = entityManager.createQuery(query);
            q1.setParameter("compCode", compCode);
            shiftTableDataList = q1.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findEmpByCompCode() - end - return value=" + shiftTableDataList);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getShiftTableData()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getShiftTableData()");
            throw new DAOException(e);
        }
        return shiftTableDataList;
    }

    public List<HrPersonnelDetails> getCategorizationBasedEmployees(int compCode, String type) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCategorizationBasedEmployees()");
        }
        List<HrPersonnelDetails> categorizationBasedEmployees = new ArrayList<HrPersonnelDetails>();
        try {
            String query = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode AND h.empStatus = 'Y' AND  h.desgCode = :desgCode ";
            Query q1 = entityManager.createQuery(/*NamedQueryConstant.FIND_CATEGORIZATION_BASED_EMPLOYEES*/query);
            String desgCode = type;
            q1.setParameter("compCode", compCode);
            q1.setParameter("desgCode", desgCode);
            categorizationBasedEmployees = q1.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findEmpByCompCode() - end - return value=" + categorizationBasedEmployees);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return categorizationBasedEmployees;
    }

    public String getLastEmployeeId(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCategorizationBasedEmployees()");
        }
        String ID = "";
        List list = new ArrayList();
        try {
            String query = "Select A.empId From HrPersonnelDetails A WHERE A.hrPersonnelDetailsPK.compCode = :compCode AND A.hrPersonnelDetailsPK.empCode = (SELECT MAX(B.hrPersonnelDetailsPK.empCode) FROM HrPersonnelDetails B where B.hrPersonnelDetailsPK.compCode = :compCode)";
            Query q1 = entityManager.createQuery(query);
            q1.setParameter("compCode", compCode);
            list = q1.getResultList();
            if (!list.isEmpty()) {
                ID = list.get(0).toString();
            } else {
                ID = "EMP00000";
            }
            if (logger.isDebugEnabled()) {
                logger.debug("getLastEmployeeId() - end - return value=" + ID);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return ID;
    }

    public long getMaxEmpCode() throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getTaxableComponent()");
        }
        long max = 0;
        try {
            Query q1 = entityManager.createQuery("SELECT MAX(B.hrPersonnelDetailsPK.empCode) FROM HrPersonnelDetails B");
            if (q1.getSingleResult() != null) {
                BigInteger big = (BigInteger) q1.getSingleResult();
                max = big.longValue();
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return max;
    }

    public HrPersonnelDetails getAllByCompCodeAndEmpCodeOrEMPID(int compCode, long empCode, String empId) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getCategorizationBasedEmployees()");
        }
        HrPersonnelDetails hrPersonnelDetailses = new HrPersonnelDetails();
        try {
            Query q1 = entityManager.createQuery(NamedQueryConstant.FIND_HR_PERSONNEL_DETAILS_ENTITY_BY_COMPCODE_AND_EMPCODE_OR_EMPID);
            q1.setParameter("compCode", compCode);
            q1.setParameter("empCode", empCode);
            q1.setParameter("empId", empId);
            hrPersonnelDetailses = (HrPersonnelDetails) q1.getSingleResult();
            if (logger.isDebugEnabled()) {
                logger.debug("getAllByCompCodeAndEmpCode() - end - return value=" + hrPersonnelDetailses);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
        return hrPersonnelDetailses;
    }

    public List searchEmployeeOnAddButton(int compCode, int searchFlag, String searchCriteria) throws DAOException {
        List gridDetail = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("searchEmployeeOnAddButton()");
        }
        try {
            if (searchFlag == 0) {
                Query query = entityManager.createQuery("SELECT A.empId,A.empName,A.empContAdd,A.empContTel FROM HrPersonnelDetails A"
                        + " WHERE A.empId LIKE '%" + searchCriteria + "%' AND A.hrPersonnelDetailsPK.compCode=:value1"
                        + " AND A.empStatus='Y'");
                query.setParameter("value1", compCode);
                gridDetail = query.getResultList();
            } else {
                Query query = entityManager.createQuery("SELECT A.empId,A.empName,A.empContAdd,A.empContTel FROM HrPersonnelDetails A"
                        + " WHERE A.empName LIKE '%" + searchCriteria + "%' AND A.hrPersonnelDetailsPK.compCode=:value1"
                        + " AND A.empStatus='Y'");
                query.setParameter("value1", compCode);
                gridDetail = query.getResultList();
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method searchEmployeeOnAddButton()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method searchEmployeeOnAddButton()");
            throw new DAOException(e);
        }
        return gridDetail;
    }

    public List empDetailFromGrid(int compCode, String empId) throws DAOException {
        List resultLt = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("empDetailFromGrid()");
        }
        try {
            Query query = entityManager.createQuery("SELECT A.hrPersonnelDetailsPK.empCode,A.empId,A.empName,A.block,B.description,"
                    + "A.unitName,C.description,A.deptCode,D.description,A.desgCode,E.description,A.gradeCode,F.description"
                    + " FROM HrPersonnelDetails A,HrMstStruct B,HrMstStruct C,HrMstStruct D,HrMstStruct E,HrMstStruct F"
                    + " WHERE A.empId=:value1 AND A.hrPersonnelDetailsPK.compCode=:value2 AND A.hrPersonnelDetailsPK.compCode=B.hrMstStructPK.compCode"
                    + " AND A.block=B.hrMstStructPK.structCode AND A.hrPersonnelDetailsPK.compCode=B.hrMstStructPK.compCode"
                    + " AND A.unitName=C.hrMstStructPK.structCode AND A.hrPersonnelDetailsPK.compCode=D.hrMstStructPK.compCode"
                    + " AND A.deptCode=D.hrMstStructPK.structCode AND A.hrPersonnelDetailsPK.compCode=E.hrMstStructPK.compCode"
                    + " AND A.desgCode=E.hrMstStructPK.structCode AND A.hrPersonnelDetailsPK.compCode=F.hrMstStructPK.compCode"
                    + " AND A.gradeCode=F.hrMstStructPK.structCode");
            query.setParameter("value1", empId);
            query.setParameter("value2", compCode);
            resultLt = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method empDetailFromGrid()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method empDetailFromGrid()");
            throw new DAOException(e);
        }
        return resultLt;
    }

    public List trainingSearchGrid(int compCode) throws DAOException {
        List gridDetail = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("trainingSearchGrid()");
        }
        try {
            String queryString = "SELECT A.hrMstStructPK.structCode,A.description FROM HrMstStruct A WHERE A.hrMstStructPK.compCode = :value1 AND A.hrMstStructPK.structCode LIKE 'TRA%'";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("value1", compCode);
            gridDetail = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method trainingSearchGrid()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method trainingSearchGrid()");
            throw new DAOException(e);
        }
        return gridDetail;
    }

    public List programSearchGrid(int compCode, String progCode) throws DAOException {
        List gridDetail = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("programSearchGrid()");
        }
        try {
            String queryString = "SELECT A.hrMstProgramPK.progCode,A.progName FROM HrMstProgram A,HrMstTrngProgram B WHERE B.hrMstTrngProgramPK.compCode = :value1"
                    + " AND B.hrMstTrngProgramPK.trngCode=:value2 AND A.hrMstProgramPK.progCode=B.hrMstTrngProgramPK.progCode";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("value1", compCode);
            query.setParameter("value2", progCode);
            gridDetail = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method programSearchGrid()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method programSearchGrid()");
            throw new DAOException(e);
        }
        return gridDetail;
    }

    public List trainingPlanGetDetail(int compCode, String trngExec) throws DAOException {
        List resultLt = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("trainingPlanGetDetail()");
        }
        try {
            Query query = entityManager.createQuery("SELECT A.hrPersonnelDetailsPK.empCode,"
                    + " A.empId,A.empName,"
                    + " B.hrTrainingPlanPK.trngCode,D.description,"
                    + " B.hrTrainingPlanPK.progCode,C.progName,"
                    + " A.desgCode,E.description,A.gradeCode,"
                    + " F.description,A.block,G.description,"
                    + " A.unitName,H.description,"
                    + " A.deptCode,I.description,"
                    + " B.hrTrainingPlanPK.dateFrom,"
                    + " B.hrTrainingPlanPK.dateTo,"
                    + " B.trngDur,B.apprDet"
                    + " FROM HrPersonnelDetails A,"
                    + " HrTrainingPlan B,"
                    + " HrMstProgram C,"
                    + " HrMstStruct D,"
                    + " HrMstStruct E,"
                    + " HrMstStruct F,"
                    + " HrMstStruct G,"
                    + " HrMstStruct H,"
                    + " HrMstStruct I"
                    + " WHERE B.trngExec=:value1 "
                    + " AND A.hrPersonnelDetailsPK.empCode=B.hrTrainingPlanPK.empCode "
                    + " AND A.hrPersonnelDetailsPK.compCode=B.hrTrainingPlanPK.compCode"
                    + " AND B.hrTrainingPlanPK.compCode=:value2 "
                    + " AND A.hrPersonnelDetailsPK.compCode=C.hrMstProgramPK.compCode"
                    + " AND B.hrTrainingPlanPK.progCode=C.hrMstProgramPK.progCode "
                    + " AND B.hrTrainingPlanPK.trngCode=D.hrMstStructPK.structCode"
                    + " AND A.desgCode=E.hrMstStructPK.structCode "
                    + " AND A.gradeCode=F.hrMstStructPK.structCode"
                    + " AND A.block=G.hrMstStructPK.structCode "
                    + " AND A.unitName=H.hrMstStructPK.structCode"
                    + " AND A.deptCode=I.hrMstStructPK.structCode"
                    + " AND B.hrTrainingPlanPK.compCode=C.hrMstProgramPK.compCode"
                    + " AND B.hrTrainingPlanPK.compCode=D.hrMstStructPK.compCode"
                    + " AND B.hrTrainingPlanPK.compCode=E.hrMstStructPK.compCode"
                    + " AND B.hrTrainingPlanPK.compCode=F.hrMstStructPK.compCode"
                    + " AND B.hrTrainingPlanPK.compCode=G.hrMstStructPK.compCode"
                    + " AND B.hrTrainingPlanPK.compCode=H.hrMstStructPK.compCode"
                    + " AND B.hrTrainingPlanPK.compCode=I.hrMstStructPK.compCode");
            query.setParameter("value1", trngExec.charAt(0));
            query.setParameter("value2", compCode);
            resultLt = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method trainingPlanGetDetail()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method trainingPlanGetDetail()");
            throw new DAOException(e);
        }
        return resultLt;
    }

    public List checkAlreadyExistance(int compCode, long empCode, String trngCode, String progCode, String dtFrom, String dtTo) throws DAOException {
        List resultLt = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("trainingPlanGetDetail()");
        }
        try {
            Query query = entityManager.createQuery("SELECT A.hrTrainingPlanPK.progCode FROM HrTrainingPlan A WHERE A.hrTrainingPlanPK.compCode = :value1 AND A.hrTrainingPlanPK.empCode=:value2 AND A.hrTrainingPlanPK.trngCode=:value3"
                    + " AND A.hrTrainingPlanPK.progCode=:value4 AND A.hrTrainingPlanPK.dateFrom=:value5 AND A.hrTrainingPlanPK.dateTo=:value6");
            query.setParameter("value1", compCode);
            query.setParameter("value2", empCode);
            query.setParameter("value3", trngCode);
            query.setParameter("value4", progCode);
            query.setParameter("value5", dtFrom);
            query.setParameter("value6", dtTo);
            resultLt = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method trainingPlanGetDetail()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method trainingPlanGetDetail()");
            throw new DAOException(e);
        }
        return resultLt;
    }

    public HrTrainingPlan findByPrimaryKey(int compCode, long empCode, String trngCode, String progCode, String fromDt, String toDt) throws DAOException {
        HrTrainingPlan instance = new HrTrainingPlan();
        if (logger.isDebugEnabled()) {
            logger.debug("trainingPlanGetDetail()");
        }
        try {
            final String queryString = "select A from HrTrainingPlan A where A.hrTrainingPlanPK.compCode = :value1 "
                    + " and A.hrTrainingPlanPK.empCode = :value2"
                    + " AND A.hrTrainingPlanPK.trngCode = :value3"
                    + " AND A.hrTrainingPlanPK.progCode = :value4"
                    + " AND A.hrTrainingPlanPK.dateFrom = :value5"
                    + " AND A.hrTrainingPlanPK.dateTo = :value6";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("value1", compCode);
            query.setParameter("value2", empCode);
            query.setParameter("value3", trngCode);
            query.setParameter("value4", progCode);
            query.setParameter("value5", fromDt);
            query.setParameter("value6", toDt);
            instance = (HrTrainingPlan) query.getSingleResult();
            return instance;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method trainingPlanGetDetail()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method trainingPlanGetDetail()");
            throw new DAOException(e);
        }
    }

    /**
     * This method is used in Training Execution Form*
     */
    public List trainingPlanGetDetailInExecution(int compCode, String trngExec) throws DAOException {
        List resultLt = new ArrayList();
        if (logger.isDebugEnabled()) {
            logger.debug("trainingPlanGetDetailInExecution()");
        }
        try {
            Query query = entityManager.createQuery("SELECT A.hrTrainingPlanPK.empCode,"
                    + " B.empName,B.empId,"
                    + " A.hrTrainingPlanPK.trngCode,C.description,"
                    + " A.hrTrainingPlanPK.progCode,E.progName,"
                    + " A.hrTrainingPlanPK.dateFrom,"
                    + " A.hrTrainingPlanPK.dateTo,"
                    + " A.trngDur,A.trngExec,A.apprDet,D.description,E.facuName,E.inextHouse"
                    + " FROM HrTrainingPlan A,"
                    + " HrPersonnelDetails B,"
                    + " HrMstStruct C,"
                    + " HrMstStruct D,"
                    + " HrMstProgram E"
                    + " WHERE A.trngExec=:value1 "
                    + " AND A.hrTrainingPlanPK.compCode=:value2 "
                    + " AND A.hrTrainingPlanPK.compCode=B.hrPersonnelDetailsPK.compCode"
                    + " AND A.hrTrainingPlanPK.empCode=B.hrPersonnelDetailsPK.empCode "
                    + " AND C.hrMstStructPK.structCode=A.hrTrainingPlanPK.trngCode"
                    + " AND C.hrMstStructPK.compCode=A.hrTrainingPlanPK.compCode"
                    + " AND D.hrMstStructPK.structCode=B.deptCode"
                    + " AND D.hrMstStructPK.compCode=B.hrPersonnelDetailsPK.compCode"
                    + " AND E.hrMstProgramPK.progCode=A.hrTrainingPlanPK.progCode"
                    + " AND E.hrMstProgramPK.compCode=A.hrTrainingPlanPK.compCode");
            query.setParameter("value1", trngExec.charAt(0));
            query.setParameter("value2", compCode);
            resultLt = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method trainingPlanGetDetailInExecution()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method trainingPlanGetDetailInExecution()");
            throw new DAOException(e);
        }
        return resultLt;
    }

    public List trainingExcutionEditDetail(int compcode) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.TRNG_EXC_EDIT_DATA);
            query.setParameter("value1", compcode);
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
//        return preList;
//        catch (PersistenceException e) {
//            logger.error("Exception occured while executing method trainingExcutionEditDetail()");
//            throw ExceptionTranslator.translateException(e);
//        } catch (Exception e) {
//            logger.error("Exception occured while executing method trainingExcutionEditDetail()");
//            throw new DAOException(e);
//        }
    }

    ///////////////////////////////
    public String checkuserexist(int compCode, String login, String password) throws DAOException {
        String s = null;

        try {
            Query q = entityManager.createNamedQuery(NamedQueryConstant.EMPCODE_FIND_BY_COMPANY_CODE_AND_EMPID);
            q.setParameter("compCode", compCode);
            q.setParameter("empStatus", 'Y');
            q.setParameter("empId", login);
            q.setParameter("password", password);
            List data = q.getResultList();
            if (data.size() > 0) {
                s = data.get(0).toString();
            }
        } catch (Exception e) {
        }
        return s;
    }

    public List<HrPersonnelDetails> findDataPersonalDetail(int compCode, String search, int seatchflag) throws ApplicationException {
        List<HrPersonnelDetails> hrpersonneldetailses = null;
        try {
            if (seatchflag == 0) {
                Query q = entityManager.createQuery(NamedQueryConstant.FIND_PERSONALDATA_BY_EMP_ID);
                q.setParameter("compCode", compCode);
                q.setParameter("empStatus", 'Y');
                q.setParameter("empId", search);
                hrpersonneldetailses = q.getResultList();
            } else {
                Query q = entityManager.createQuery(NamedQueryConstant.FIND_PERSONALDATA_BY_EMP_NAME);
                q.setParameter("compCode", compCode);
                q.setParameter("empStatus", 'Y');
                q.setParameter("empName", search);
                hrpersonneldetailses = q.getResultList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return hrpersonneldetailses;
    }

    //FIND_EMPCODE_BY_EMP_ID_COMPCODE_FLAG
    public HrPersonnelDetails findByEmpId(int compCode, String empId) throws DAOException {
        try {
            Query q = entityManager.createQuery(NamedQueryConstant.FIND_EMPCODE_BY_EMP_ID_COMPCODE_FLAG);
            q.setParameter("compCode", compCode);
            q.setParameter("empStatus", 'Y');
            q.setParameter("empId", empId);
            return (HrPersonnelDetails) q.getSingleResult();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
    }
    //FIND_EMPCODE_HRPERSONNAL_CHECK_ALL

    public List<HrPersonnelDetails> findByCategoryWise(int compCode, String category) throws DAOException {
        try {
            Query q = entityManager.createQuery(NamedQueryConstant.FIND_EMPCODE_HRPERSONNAL_CHECK_ALL);
            q.setParameter("compCode", compCode);
            q.setParameter("empStatus", 'Y');
            q.setParameter("value", category);
            return q.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
    }

//VIEW_DATA_ACCEPTANCE_LETTER
    public List viewDataAcceptance(int compCode) throws DAOException {
        List result = null;
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.VIEW_DATA_ACCEPTANCE_LETTER);
            query.setParameter("compCode", compCode);
            result = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
        return result;
    }
    //SELECT_DATA_ACCEPTANCE_LETTER

    public List selectDataEmpid(int compCode, String empId) throws DAOException {
        List result = null;
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.SELECT_DATA_ACCEPTANCE_LETTER);
            query.setParameter("compCode", compCode);
            query.setParameter("empId", empId);
            result = query.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
        return result;
    }

    //FIND_BY_COMPCOD_EMPID
    public String checkreturnEmpId(int compCode, String empId) throws DAOException {
        String result = null;
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.FIND_BY_COMPCOD_EMPID);
            query.setParameter("compCode", compCode);
            query.setParameter("empId", empId);
            result = query.getSingleResult().toString();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
        return result;
    }

//        PROMOTION_VIEW
    public HrPersonnelDetails findRefrence(HrPersonnelDetailsPK to) throws DAOException {
        try {
            HrPersonnelDetails entity = entityManager.getReference(HrPersonnelDetails.class, to);
            return entity;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLoanDetails()");
            throw new DAOException(e);
        }
    }
    //FIND_BY_EMP_NAME_FOR_EXIT_INTERVIEW

    public List<HrPersonnelDetails> findbyExitEmpSearch(int compCode, long empCode, char empStatus) throws DAOException {

        List<HrPersonnelDetails> data;
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.FIND_BY_EMP_NAME_FOR_EXIT_INTERVIEW);
            query.setParameter("compCode", compCode);
            query.setParameter("empCode", empCode);
            query.setParameter("empStatus", empStatus);
            data = query.getResultList();
            return data;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCode()");
            throw new DAOException(e);
        }
    }

    /**
     *
     * @param compCode
     * @param selectedvalues
     * @return
     * @throws DAOException
     */
    public List<HrPersonnelDetails> findEntityEmpStatusY(int compCode, String selectedvalues) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findEntityEmpStatusY()");
        }
        List<HrPersonnelDetails> hrPersonnelDetailsList = new ArrayList<HrPersonnelDetails>();
        try {
            Query q1 = entityManager.createQuery(NamedQueryConstant.PERSONNEL_DETAILS_WITH_EMP_STATUS);
            q1.setParameter("compCode", compCode);
            q1.setParameter("type", selectedvalues);
            q1.setParameter("empstatus", 'Y');
            hrPersonnelDetailsList = q1.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findEntityEmpStatusY() - end - return value=" + hrPersonnelDetailsList);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findEntityEmpStatusY()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityEmpStatusY()");
            throw new DAOException(e);
        }
        return hrPersonnelDetailsList;
    }

    /**
     * Get the employee details by compCode ,status and employee id
     *
     * @param compCode
     * @param empId
     * @param empStatus
     * @return
     * @throws DAOException
     */
    public HrPersonnelDetails findByEmpStatusAndEmpId(int compCode, String empId, char empStatus) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByEmpStatusAndEmpId()");
        }
        HrPersonnelDetails hrPersonnelDetailsEntity = new HrPersonnelDetails();
        List<HrPersonnelDetails> hrPersonnelDetailsList = new ArrayList<HrPersonnelDetails>();
        try {
            Query q1 = entityManager.createQuery(NamedQueryConstant.HR_PERSONNEL_BY_EMPID_EMPSTATUS_COMPCODE);
            q1.setParameter("compCode", compCode);
            q1.setParameter("empId", empId);
            q1.setParameter("empStatus", empStatus);
            hrPersonnelDetailsList = q1.getResultList();
            for (HrPersonnelDetails hrPersonnelDetails : hrPersonnelDetailsList) {
                hrPersonnelDetailsEntity = hrPersonnelDetails;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("findByEmpStatusAndEmpId() - end - return value=" + hrPersonnelDetailsEntity);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByEmpStatusAndEmpId()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByEmpStatusAndEmpId()");
            throw new DAOException(e);
        }
        return hrPersonnelDetailsEntity;
    }

    /**
     *
     * @param compCode
     * @param type
     * @param value
     * @return
     * @throws DAOException
     */
    public List<HrPersonnelDetails> findEmpByIdAndName(int compCode, String type, String value) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findEmpByCompCodeTypeValue()");
        }
        List<HrPersonnelDetails> hrPersonnelDetailsList = new ArrayList<HrPersonnelDetails>();
        try {
            if (type.equalsIgnoreCase("ID")) {
                Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_BY_COMPCODE_AND_LIKE_EMP_ID);
                q1.setParameter("compCode", compCode);
                q1.setParameter("empId", "%" + value + "%");
                q1.setParameter("empStatus", 'Y');
                hrPersonnelDetailsList = q1.getResultList();
            } else if (type.equalsIgnoreCase("Name")) {
                Query q2 = entityManager.createNamedQuery(NamedQueryConstant.FIND_BY_COMPCODE_AND_LIKE_EMP_NAME);
                q2.setParameter("compCode", compCode);
                q2.setParameter("empName", "%" + value + "%");
                q2.setParameter("empStatus", 'Y');
                hrPersonnelDetailsList = q2.getResultList();
            }
            if (logger.isDebugEnabled()) {
                logger.debug("findEmpByCompCode() - end - return value=" + hrPersonnelDetailsList);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findEmpByCompCodeTypeValue()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEmpByCompCodeTypeValue()");
            throw new DAOException(e);
        }
        return hrPersonnelDetailsList;
    }

    public List<HrPersonnelDetails> findByCustomerId(String customerId) throws Exception {
        List<HrPersonnelDetails> hrPersonnelDetailsList = new ArrayList<HrPersonnelDetails>();
        try {
            Query q = entityManager.createQuery(NamedQueryConstant.FIND_BY_CUSTOMER_ID);
            q.setParameter("customerId", customerId);
            hrPersonnelDetailsList = q.getResultList();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return hrPersonnelDetailsList;
    }
    
    
}
