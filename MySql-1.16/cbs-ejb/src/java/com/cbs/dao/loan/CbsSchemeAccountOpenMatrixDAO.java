
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.loan;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.entity.loan.CbsSchemeAccountOpenMatrix;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CbsSchemeAccountOpenMatrixDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CbsSchemeAccountOpenMatrixDAO.class);

    public CbsSchemeAccountOpenMatrixDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CbsSchemeAccountOpenMatrixDAO Initializing...");
    }

    /*** It is also findByPK Method****/
    public CbsSchemeAccountOpenMatrix getEntityBySchemeCode(String schemeCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityBySchemeCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsSchemeAccountOpenMatrix.findBySchemeCode");
            createNamedQuery.setParameter("schemeCode", schemeCode);
            List resulList = createNamedQuery.getResultList();
            CbsSchemeAccountOpenMatrix obj = null;
            if (resulList.size() > 0) {
                obj = (CbsSchemeAccountOpenMatrix) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("findAllEvent() - end - return value=" + obj);
            }
            return obj;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method findAllEvent()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method findAllEvent()");
            throw new DAOException(e);
        }
    }
}
