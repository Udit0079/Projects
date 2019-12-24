/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.dao;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrMstZoneLocation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
public class HrMstZoneLocationDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrMstZoneLocationDAO.class);

    /**
     * @param entityManager
     */
    public HrMstZoneLocationDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrMstZoneLocationDAO Initializing...");
    }

    public List getAvailableLocations(int compCode) throws DAOException, ApplicationException {
        long begin = System.nanoTime();
        if (logger.isDebugEnabled()) {
            logger.debug("getAvailableLocations()");
        }
        List<HrMstZoneLocation> hrMstZoneLocationList = null;
        try {
            String query =
                    "SELECT h.hrMstStructPK.structCode, h.description FROM HrMstStruct h" +
                    " WHERE h.hrMstStructPK.compCode = :compCode AND" +
                    " h.hrMstStructPK.structCode like 'LOC%' AND" +
                    " h.hrMstStructPK.structCode NOT IN" +
                    " (SELECT k.hrMstZoneLocationPK.locationCode FROM HrMstZoneLocation k" +
                    " WHERE k.hrMstZoneLocationPK.compCode = :compCode)";
            Query q1 = entityManager.createQuery(query);
            q1.setParameter("compCode", compCode);
            hrMstZoneLocationList = q1.getResultList();
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrMstZoneLocationList;
    }

    public List getAssignedLocations(int compCode, String zoneCode) throws DAOException, ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getAllByDeptcodeSubdeptcode()");
        }
        List hrMstZoneLocationList = null;
        try {
            String query =
                    "select distinct A.hrMstZoneLocationPK.locationCode, B.description from HrMstZoneLocation A, HrMstStruct B" +
                    " where A.hrMstZoneLocationPK.compCode =:compCode AND" +
                    " A.hrMstZoneLocationPK.compCode = B.hrMstStructPK.compCode AND" +
                    " A.hrMstZoneLocationPK.locationCode = B.hrMstStructPK.structCode AND" +
                    " A.hrMstZoneLocationPK.zoneCode = :zoneCode";
            Query q1 = entityManager.createQuery(query);
            q1.setParameter("compCode", compCode);
            q1.setParameter("zoneCode", zoneCode);
            hrMstZoneLocationList = q1.getResultList();
            return hrMstZoneLocationList;
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
    }
}
