/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.hr.HrAdvertHd;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
public class HrAdvertHdDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrAdvertHdDAO.class);

    /**
     * @param entityManager
     */
    public HrAdvertHdDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrAdvertHdDAO Initializing...");
    }

    public List creationOfDatabankSearch(int compCode, String advtCode) throws DAOException {
        List preList = new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.CREATION_OF_DATABANK_SEARCH);
            query.setParameter("value1", compCode);
            query.setParameter("value2", "%" + advtCode);
            preList = query.getResultList();
            return preList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method creationOfDatabankSearch()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method creationOfDatabankSearch()");
            throw new DAOException(e);
        }
    }

    public String generateAdvtCode(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("generateAdvtCode()");
        }
        String max = "1";
        try {
            long maxCode = 0;
            Query q1 = entityManager.createNamedQuery("HrAdvertHd.findMaxAdvtCodeByCompCode");
            q1.setParameter("compCode", compCode);
            if (q1.getSingleResult() != null) {
                maxCode = Long.parseLong(q1.getSingleResult().toString());
                long val = maxCode + 1;
                max = Long.toString(val);
                int length = max.length();
                int addedZero = 7 - length;
                for (int i = 1; i <= addedZero; i++) {
                    max = "0" + max;
                }
            } else {
                max = "0000001";
            }
            max = "ADV" + max;            
            return max;

        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method generateAdvtCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method generateAdvtCode()");
            throw new DAOException(e);
        }

    }

    /**
     *
     * @param compCode
     * @param advCode
     * @return
     */
    /////////////////////////////////////////
    public List<HrAdvertHd> getAdvertisementCodeList(int compCode, String advCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAdvertisementCodeList()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_ADVERT_CODE);
            q1.setParameter("compCode", compCode);
            q1.setParameter("advtCode", advCode);
            if (logger.isDebugEnabled()) {
                logger.debug("getAdvertisementCodeList() - end - return value=" + q1.getResultList());
            }
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getAdvertisementCodeList()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvertisementCodeList()");
            throw new DAOException(e);
        }
    }

    /**
     *
     * @param compCode
     * @param advertCode
     * @return
     * @throws DAOException
     */
    public List<HrAdvertHd> getAdvtCodeHeaderDetails(int compCode, String advertCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAdvtCodeHeaderDetails()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_ADVT_CODE_HEADER_DETAILS);
            q1.setParameter("compCode", compCode);
            q1.setParameter("advtCode", advertCode);
            if (logger.isDebugEnabled()) {
                logger.debug("getAdvtCodeHeaderDetails() - end - return value=" + q1.getResultList());
            }
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getAdvtCodeHeaderDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvtCodeHeaderDetails()");
            throw new DAOException(e);
        }
    }

    /**
     *
     * @param compCode
     * @param advertCode
     * @throws DAOException
     */
    public void deleteByCompCodeAndAdvertCode(int compCode, String advertCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("deleteByCompCodeAndAdvertCode()");
        }
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.DELETE_BY_COMPANYCODE_AND_ADVERT_CODE_HEADER);
            q1.setParameter("compCode", compCode);
            q1.setParameter("advtCode", advertCode);
            q1.executeUpdate();

        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method deleteByCompCodeAndAdvertCode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteByCompCodeAndAdvertCode()");
            throw new DAOException(e);
        }
    }

    /**
     * Join Query for Advertisement code details
     * @param compCode
     * @param advtCode
     * @return
     */
    public List getAdvertisementCodeDetails(int compCode, String advtCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAdvertisementCodeDetails()");
        }
        try {
            Query q1 = entityManager.createQuery("select a.hrAdvertDtPK.desgCode,a.hrAdvertDtPK.deptCode,a.hrAdvertDtPK.locatCode,"
                    + "a.vaccant,a.lastDate,b.description,c.description,d.description from HrAdvertDt a,"
                    + "HrMstStruct b,HrMstStruct c,HrMstStruct d  where a.hrAdvertDtPK.compCode=b.hrMstStructPK.compCode  and "
                    + "a.hrAdvertDtPK.compCode=:compCode and a.hrAdvertDtPK.advtCode=:advtCode and a.hrAdvertDtPK.compCode=c.hrMstStructPK.compCode "
                    + "and a.hrAdvertDtPK.compCode=d.hrMstStructPK.compCode and a.hrAdvertDtPK.desgCode=b.hrMstStructPK.structCode "
                    + "and a.hrAdvertDtPK.deptCode=c.hrMstStructPK.structCode and a.hrAdvertDtPK.locatCode=d.hrMstStructPK.structCode");
            q1.setParameter("compCode", compCode);
            q1.setParameter("advtCode", advtCode);
            if (logger.isDebugEnabled()) {
                logger.debug("getAdvertisementCodeDetails() - end - return value=" + q1.getResultList());
            }
            return q1.getResultList();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getAdvertisementCodeDetails()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvertisementCodeDetails()");
            throw new DAOException(e);
        }
    }

    public HrAdvertHd deleteAdvertisementHd(int compCode, String consCode) throws DAOException {
        HrAdvertHd instance = new HrAdvertHd();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.ADV_DELETE_HD);
            query.setParameter("value1", compCode);
            query.setParameter("value2", consCode);
            instance = (HrAdvertHd) query.getSingleResult();
            return instance;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method deleteConsultant()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteConsultant()");
            throw new DAOException(e);
        }
    }
}
