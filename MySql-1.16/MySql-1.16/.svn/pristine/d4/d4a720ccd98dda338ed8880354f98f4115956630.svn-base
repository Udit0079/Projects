/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.common.to.HrHolidayMasterTO;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.payroll.HrHolidayMaster;
import com.hrms.entity.payroll.HrHolidayMasterPK;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Sudhir Kr BIsht
 */
public class HrHolidayMasterDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(LeaveMasterDAO.class);

    /**
     * @param entityManager
     */
    public HrHolidayMasterDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrHolidayMasterDAO Initializing...");
    }

    /**
     * Find Hr holiday master by company code and holiday date
     * @param compCode
     * @param holidayDate
     * @return
     * @throws DAOException
     */
    public List<HrHolidayMaster> findByCompCodeAndHolidaydate(int compCode, Date holidayDate) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByCompCodeAndHolidaydate()");
        }
        List<HrHolidayMaster> hrHolidayMasterList=new ArrayList<HrHolidayMaster>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.HOLIDAY_MASTER_BY_COMP_CODE_AND_HOLIDAY_DATE);
            q1.setParameter("compCode", compCode);
            q1.setParameter("holidayDate", holidayDate);
            hrHolidayMasterList = q1.getResultList();
           
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCodeAndHolidaydate()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCodeAndHolidaydate()");
            throw new DAOException(e);
        }
        return hrHolidayMasterList;
    }

    public List<HrHolidayMaster> getHolidayList(int compCode, Date date1, Date date2) throws DAOException {
        try {
            Query q = entityManager.createNamedQuery(NamedQueryConstant.FIND_HRHOLIDAYMASTER_COMPCODE);
            q.setParameter("compCode", compCode);
            q.setParameter("dateFrom", date1);
            q.setParameter("dateTo", date2);
            List<HrHolidayMaster> result = q.getResultList();
            return result;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCodeAndHolidaydate()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCodeAndHolidaydate()");
            throw new DAOException(e);
        }

    }
    //FIND_MAX_HOLIDAYCODE

    public long findMaxHolidayCode(int compCode) throws DAOException {
        try {
            Query q = entityManager.createNamedQuery(NamedQueryConstant.FIND_MAX_HOLIDAYCODE);
            q.setParameter("compCode", compCode);
            if(q.getSingleResult() == null){
                return 0;
            }else{
                return Long.parseLong(q.getSingleResult().toString());
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCodeAndHolidaydate()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCodeAndHolidaydate()");
            throw new DAOException(e);
        }
    }
    
    public HrHolidayMaster findByPrimaryKey(HrHolidayMasterPK hrHolidayMasterPK) throws DAOException {
        try {
            Query q = entityManager.createNamedQuery(NamedQueryConstant.HOLIDAY_MASTER_BY_PRIMARY_KEY);
            q.setParameter("hrHolidayMasterPK", hrHolidayMasterPK);
            return (HrHolidayMaster)q.getSingleResult();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCodeAndHolidaydate()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCodeAndHolidaydate()");
            throw new DAOException(e);
        }
    }
    
    public HrHolidayMaster findByCompCodeAndDesc(HrHolidayMasterTO holidayMasterTO) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByCompCodeAndHolidaydate()");
        }
        HrHolidayMaster hrHolidayMaster = new HrHolidayMaster();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.HOLIDAY_MASTER_BY_COMP_CODE_AND_HOLIDAY_DATE_AND_DESC);
            q1.setParameter("compCode", holidayMasterTO.getHrHolidayMasterPKTO().getCompCode());
            q1.setParameter("holidayDate", holidayMasterTO.getHolidayDate());

            q1.setParameter("desc", holidayMasterTO.getHolidayDate());
            q1.setParameter("calFromDate", holidayMasterTO.getHolidayDate());
            q1.setParameter("calToDate", holidayMasterTO.getHolidayDate());
            
            hrHolidayMaster = (HrHolidayMaster)q1.getSingleResult();
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findByCompCodeAndHolidaydate()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findByCompCodeAndHolidaydate()");
            throw new DAOException(e);
        }
        return hrHolidayMaster;
    }
}
