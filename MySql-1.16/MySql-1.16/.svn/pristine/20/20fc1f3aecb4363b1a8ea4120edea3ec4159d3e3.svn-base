package com.hrms.dao;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrMembershipDetail;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class HrMembershipDetailDAO extends GenericDAO {

    private static final Logger logger = Logger.getLogger(HrMembershipDetailDAO.class);

    public HrMembershipDetailDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrMembershipDetailDAO Initializing...");
    }

    public int getMaxMemNo() throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("getTaxableComponent()");
        }
        int max = 0;
        try {
            Query q1 = entityManager.createQuery("SELECT MAX(h.hrMembershipDetailPK.memNo) FROM HrMembershipDetail h");
            if (q1.getSingleResult() != null) {
                max = (Integer) q1.getSingleResult();
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getMaxMemNo()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMaxMemNo()");
            throw new DAOException(e);
        }
        return max;
    }

    public List<HrMembershipDetail> getMembershipData(long empCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findEmpByCompCodeTypeValue()");
        }
        List<HrMembershipDetail> hrMembershipDetailList = new ArrayList<HrMembershipDetail>();
        try {
            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.FIND_HrMembershipDetail_BY_EMPCODE);
            q1.setParameter("empCode", empCode);
            hrMembershipDetailList = q1.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findEmpByCompCode() - end - return value=" + hrMembershipDetailList);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method getMembershipData()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMembershipData()");
            throw new DAOException(e);
        }
        return hrMembershipDetailList;
    }
}
