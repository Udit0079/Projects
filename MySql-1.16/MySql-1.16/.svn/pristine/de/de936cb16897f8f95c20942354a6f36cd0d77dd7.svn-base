/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.dao;
import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.entity.personnel.HrContractorDetails;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import java.util.Iterator;

/**
 *
 * @author Zeeshan Waris
 */
public class HrContractorDetailsDAO  extends GenericDAO {

  private static final Logger logger = Logger.getLogger(HrContractorDetailsDAO.class);

  public HrContractorDetailsDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("HrTransferDetailsDAO Initializing...");
    }
  public List temporaryContractorName(int compcode) throws DAOException {
        List preList =new ArrayList();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.TEMPORARY_STAFF_CONTRACTOR_DETAILS);
            query.setParameter("value1", compcode);
            preList = query.getResultList();
            return preList;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method temporaryContractorName()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method temporaryContractorName()");
                throw new DAOException(e);
        }
    }

   public String contractorDetailsContcode(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("generateAdvtCode()");
        }
        try {
            //            Query q1 = entityManager.createNamedQuery(NamedQueryConstant.CONTRACTOR_ENTITY_BY_COMPANYCODE_AND_ADVCODE);
            //            q1.setParameter("compCode", compCode);
            //            List advCodeList = q1.getResultList();
            //            Long advCode = null;
            //            if (advCodeList.size() > 0) {
            //                Query q2 = entityManager.createNamedQuery(NamedQueryConstant.CONTRACTOR_FIND_MAX_ENDNUMBER_BY_COMPCODE__AND_ADVCODE);
            //                q2.setParameter("compCode", compCode);
            //                List endNumbList = q2.getResultList();
            //                Iterator ite = endNumbList.iterator();
            //                while (ite.hasNext()) {
            //                    advCode = Long.parseLong(ite.next().toString());
            //                    advCode = advCode + 1;
            //                }
            //                Query q3 = entityManager.createNamedQuery(NamedQueryConstant.CONTRACTOR_UPDATE_ADVERTISEMENT_CODE);
            //                q3.setParameter("compCode", compCode);
            //                q3.executeUpdate();
            //            } else {
            //                advCode = 1L;
            //            }
            //            String advtCodeString = advCode.toString();
            //            while (advtCodeString.length() < 7) {
            //                advtCodeString = "0" + advtCodeString;
            //            }
            //            advtCodeString = "CNT" + advtCodeString;
            //            return advtCodeString;
            Query createNamedQuery = entityManager.createNamedQuery("HrContractorDetails.findMaxCodeByCompCode");
            List resultList = createNamedQuery.setParameter("compCode", compCode).getResultList();
             return String.valueOf(resultList.get(0)==null?"1":Integer.parseInt(resultList.get(0).toString())+1);
     
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method contractorDetailsContcode()");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method contractorDetailsContcode()");
            throw new DAOException(e);
        }

    }

    public List<HrContractorDetails> findByCompCodeContractorDetail(int compCode) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("findByCompCode()");
        }
        try {
            Query query = entityManager.createNamedQuery(NamedQueryConstant.CONTRACTOR_EDIT_DETAILS);
            query.setParameter("compCode", compCode);
            List<HrContractorDetails> resultList = query.getResultList();
            if (logger.isDebugEnabled()) {
                logger.debug("findByCompCode() - end - return value=" + resultList);
            }
            return resultList;
        } catch (PersistenceException e) {
                logger.error("Exception occured while executing method findByCompCodeContractorDetail()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method findByCompCodeContractorDetail()");
                throw new DAOException(e);
        }
}
   public HrContractorDetails deleteContractoerDetails(int compCode, String contCode) throws DAOException {
        HrContractorDetails instance = new HrContractorDetails();
        try {
            Query query = entityManager.createQuery(NamedQueryConstant.CONTRACTOR_DETAILS_DELETE_QUERY);
            query.setParameter("value1", compCode);
            query.setParameter("value2", contCode);
            instance = (HrContractorDetails)query.getSingleResult();
            return instance;
       } catch (PersistenceException e) {
                logger.error("Exception occured while executing method deleteContractoerDetails()");
                throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
                logger.error("Exception occured while executing method deleteContractoerDetails()");
                throw new DAOException(e);
        }
    }





}
