/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.sms;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.sms.MbSubscriberServices;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class MbSubscriberServicesDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(MbSubscriberTabDAO.class);

    public MbSubscriberServicesDAO(EntityManager entityManager) {

        super(entityManager);
        logger.debug("MbSubscriberServicesDAO Initializing...");
    }

    public List<MbSubscriberServices> getEntityListByAcno(String acno) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityListBySchemeCode()");
        }
        try {
            Query createNamedQuery = entityManager.createNamedQuery("MbSubscriberServices.findByAcno");
            createNamedQuery.setParameter("acno", acno);
            List<MbSubscriberServices> resulList = createNamedQuery.getResultList();

            if (logger.isDebugEnabled()) {
                logger.debug("findEntityListEvent() - end - return value=" + resulList);
            }
            return resulList;
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityEvent()");
            throw new ApplicationException(e);
        }
    }

    public MbSubscriberServices getEntityByAcnoAndService(String acno, int service) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("getEntityListBySchemeCode()");
        }
        MbSubscriberServices obj = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_BY_ACNO_AND_SERVICE);
            createNamedQuery.setParameter("acno", acno);
            createNamedQuery.setParameter("services", Short.parseShort(String.valueOf(service)));
            List<MbSubscriberServices> resulList = createNamedQuery.getResultList();
            if (resulList.size() > 0) {
                obj = (MbSubscriberServices) resulList.get(0);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("findEntityListEvent() - end - return value=" + resulList);
            }
            return obj;
        } catch (Exception e) {
            logger.error("Exception occured while executing method findEntityEvent()");
            throw new ApplicationException(e.getMessage());
        }
    }
}
