/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrSlabMaster;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class HrSlabMasterDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(LeaveMasterDAO.class);

    /**
     * @param entityManager
     */
    public HrSlabMasterDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrSlabMasterDAO Initializing...");
    }

    /**
     *
     * @param compCode
     * @param purposeCode
     * @param fromDate
     * @param toDate
     * @return
     */
    public List getSlabMasterStructure(int compCode, String purposeCode, Date fromDate, Date toDate) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getSlabMasterStructure()");
        }
        Date dt = new Date();
        try {
            Query q1 = entityManager.createQuery("select A.hrSlabMasterPK.purposeCode,B.description,"
                    + "A.hrSlabMasterPK.nature,C.description,A.hrSlabMasterPK.alDesc,"
                    + "A.hrSlabMasterPK.slabCode,A.rangeFrom,A.rangeTo,A.slabCriteria,"
                    + "A.slabCriteriaAmt,A.appFlg from HrSlabMaster A,HrMstStruct B,HrMstStruct C "
                    + "where A.hrSlabMasterPK.compCode=:compCode and A.hrSlabMasterPK.purposeCode = :purposeCode "
                    + "and A.modDate <= (select max(D.modDate) from HrSlabMaster D where D.modDate <=:dateFrom) "
                    + " and A.hrSlabMasterPK.compCode=B.hrMstStructPK.compCode and B.hrMstStructPK.compCode=C.hrMstStructPK.compCode"
                    + " and A.hrSlabMasterPK.purposeCode = B.hrMstStructPK.structCode and A.hrSlabMasterPK.nature=C.hrMstStructPK.structCode");
            q1.setParameter("compCode", compCode);
            q1.setParameter("purposeCode", purposeCode);
            q1.setParameter("dateFrom", dt);
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getSlabMasterStructure()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSlabMasterStructure()");
            throw new DAOException(e);
        }
    }

    /**
     *
     * @return @throws DAOException
     */
    public String calculateSlabCode() throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("calculateSlabCode()");
        }
        try {
            Query q1 = entityManager.createNamedQuery("HrSlabMaster.findAll");
            List<HrSlabMaster> hrSlabMasterSize = q1.getResultList();
            Long maxSlabCode = 0L;
            for (HrSlabMaster hrSlabMaster : hrSlabMasterSize) {
                String slabCode = hrSlabMaster.getHrSlabMasterPK().getSlabCode();
                if (Long.parseLong(slabCode.substring(3, 11)) > maxSlabCode) {
                    maxSlabCode = Long.parseLong(slabCode.substring(3, 11));
                }
            }
            maxSlabCode = maxSlabCode = maxSlabCode + 1;
            String slabCodeString = maxSlabCode.toString();
            while (slabCodeString.length() < 8) {
                slabCodeString = "0" + slabCodeString;
            }
            slabCodeString = "SLA" + slabCodeString;
            return slabCodeString;

        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method calculateSlabCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method calculateSlabCode()");
            throw new DAOException(e);
        }

    }

    /**
     * SLAB master with applied flag
     *
     * @param hrSlabMaster
     * @return
     * @throws DAOException
     */
    public List<HrSlabMaster> slabMasterFindEntities(HrSlabMaster hrSlabMaster) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("slabMasterFindEntities()");
        }
        List<HrSlabMaster> hrSlabMasterList = new ArrayList<HrSlabMaster>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.SLAB_MASTER_WITH_APPLIED_FLAG);
            q1.setParameter("compCode", hrSlabMaster.getHrSlabMasterPK().getCompCode());
            q1.setParameter("purposeCode", hrSlabMaster.getHrSlabMasterPK().getPurposeCode());
            q1.setParameter("nature", hrSlabMaster.getHrSlabMasterPK().getNature());
            q1.setParameter("slabCode", hrSlabMaster.getHrSlabMasterPK().getSlabCode());
            q1.setParameter("appFlg", hrSlabMaster.getAppFlg());
            hrSlabMasterList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method slabMasterFindEntities()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method slabMasterFindEntities()");
            throw new DAOException(e);
        }
        return hrSlabMasterList;
    }

    /**
     *
     * @param hrSlabMaster
     * @return
     * @throws DAOException
     */
    public List<HrSlabMaster> getSlabMasterByvariousParam(HrSlabMaster hrSlabMaster, double basicSalary) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getSlabMasterByvariousParam()");
        }
        List<HrSlabMaster> hrSlabMasterList = new ArrayList<HrSlabMaster>();
        try {
            Date dt = new Date();
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.SLABMASTER_BY_VARIOUS_PARAMETERS);
            q1.setParameter("compCode", hrSlabMaster.getHrSlabMasterPK().getCompCode());
            q1.setParameter("purposeCode", hrSlabMaster.getHrSlabMasterPK().getPurposeCode());
            q1.setParameter("nature", hrSlabMaster.getHrSlabMasterPK().getNature());
            q1.setParameter("dateFrom", dt);
//            q1.setParameter("dateTo", hrSlabMaster.getHrSlabMasterPK().getDateTo());
            q1.setParameter("appFlg", hrSlabMaster.getAppFlg());
            q1.setParameter("basicSalary", basicSalary * 12);
            hrSlabMasterList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getSlabMasterByvariousParam()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSlabMasterByvariousParam()");
            throw new DAOException(e);
        }
        return hrSlabMasterList;
    }

    /**
     *
     * @param hrSlabMaster
     * @return
     * @throws DAOException
     */
    public List<HrSlabMaster> getSlabMasterByKey(HrSlabMaster hrSlabMaster) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getSlabMasterByKey()");
        }
        List<HrSlabMaster> hrSlabMasterList = new ArrayList<HrSlabMaster>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.SLAB_MASTER_BY_PRIMARY_KEY);
            q1.setParameter("compCode", hrSlabMaster.getHrSlabMasterPK().getCompCode());
            q1.setParameter("purposeCode", hrSlabMaster.getHrSlabMasterPK().getPurposeCode());
            q1.setParameter("nature", hrSlabMaster.getHrSlabMasterPK().getNature());
            hrSlabMasterList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getSlabMasterByKey()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSlabMasterByKey()");
            throw new DAOException(e);
        }
        return hrSlabMasterList;
    }

    /**
     * slab master without applied flag
     *
     * @param hrSlabMaster
     * @return
     * @throws DAOException
     */
    public List<HrSlabMaster> slabMasterFindEntitiesWithourAPpflag(HrSlabMaster hrSlabMaster) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("slabMasterFindEntities()");
        }
        List<HrSlabMaster> hrSlabMasterList = new ArrayList<HrSlabMaster>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.SLAB_MASTER_WITHOUT_APP_FLAG);
            q1.setParameter("compCode", hrSlabMaster.getHrSlabMasterPK().getCompCode());
            q1.setParameter("purposeCode", hrSlabMaster.getHrSlabMasterPK().getPurposeCode());
            q1.setParameter("nature", hrSlabMaster.getHrSlabMasterPK().getNature());
            q1.setParameter("slabCode", hrSlabMaster.getHrSlabMasterPK().getSlabCode());
            hrSlabMasterList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method slabMasterFindEntities()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method slabMasterFindEntities()");
            throw new DAOException(e);
        }
        return hrSlabMasterList;
    }

    public List<HrSlabMaster> getSlabMasterByParam(HrSlabMaster hrSlabMaster) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getSlabMasterByvariousParam()");
        }
        List<HrSlabMaster> hrSlabMasterList = new ArrayList<HrSlabMaster>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.SLABMASTER_BY_VARIOUS_PARAMETERS1);
            q1.setParameter("compCode", hrSlabMaster.getHrSlabMasterPK().getCompCode());
            q1.setParameter("purposeCode", hrSlabMaster.getHrSlabMasterPK().getPurposeCode());
            q1.setParameter("nature", hrSlabMaster.getHrSlabMasterPK().getNature());
            q1.setParameter("component", hrSlabMaster.getHrSlabMasterPK().getBaseComponent());
            hrSlabMasterList = q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getSlabMasterByvariousParam()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getSlabMasterByvariousParam()");
            throw new DAOException(e);
        }
        return hrSlabMasterList;
    }
}
