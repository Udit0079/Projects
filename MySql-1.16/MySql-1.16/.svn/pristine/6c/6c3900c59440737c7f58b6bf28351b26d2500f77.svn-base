/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;


import com.hrms.common.to.HrMstInstituteTO;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
public class HrMstInstituteDAO extends GenericDAO {
 private static final Logger logger = Logger.getLogger(HrMstInstituteDAO.class);
    /**
     * @param entityManager
     */
    public HrMstInstituteDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrMstInstituteDAO Initializing...");
    }

     public List<HrMstInstituteTO> instDetailGridOnload(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByStructCode()");
        }
        try {
            String queryString = "SELECT A.hrMstInstitutePK.compCode,A.hrMstInstitutePK.instCode,A.instName,A.instAdd,A.contPers,A.contNo" +
                    " FROM HrMstInstitute A WHERE A.hrMstInstitutePK.compCode = :value1";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("value1", compCode);
            List<HrMstInstituteTO> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("instDetailGridOnload() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method instDetailGridOnload()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method instDetailGridOnload()");
            throw new DAOException(e);
        }
    }

    public String getMaxInstitudeCode(int compCode) throws DAOException {
        String code = "";
        Integer tmpCode;
        try {
            String queryString = "SELECT MAX(A.hrMstInstitutePK.instCode) FROM HrMstInstitute A WHERE A.hrMstInstitutePK.compCode = :value1";
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return code;
    }

    

}
