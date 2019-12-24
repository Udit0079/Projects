/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.neftrtgs;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.neftrtgs.CbsAutoNeftDetails;
import com.cbs.entity.neftrtgs.NeftOwDetails;
import com.cbs.exception.ApplicationException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class NeftOwDetailsDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(NeftOwDetailsDAO.class);

    public NeftOwDetailsDAO(EntityManager entityManager) {

        super(entityManager);
        logger.debug("NeftOwDetailsDAO Initializing...");
    }

    public List<NeftOwDetails> getNeftOwDetailsData(String neftBankName) throws ApplicationException {
        List<NeftOwDetails> resultList = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.ALL_NEFT_OW_DETAILS_DATA);
            createNamedQuery.setParameter("status", "P");
            createNamedQuery.setParameter("auth", "Y");
            createNamedQuery.setParameter("initiatedBankName", neftBankName);
            resultList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            System.out.println("Problem In getNeftOwDetailsData() Method " + ex.getMessage());
        }
        return resultList;
    }

    public NeftOwDetails getSingleNeftOwDetailsInstrument(String uniqueCustRefNo) throws ApplicationException {
        NeftOwDetails entity = null;
        try {
            Query createNamedQuery = entityManager.createNamedQuery("NeftOwDetails.findByUniqueCustomerRefNo");
            createNamedQuery.setParameter("uniqueCustomerRefNo", uniqueCustRefNo);
            entity = (NeftOwDetails) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            System.out.println("Problem In getSingleNeftOwDetailsInstrument() Method " + ex.getMessage());
        }
        return entity;
    }

    public NeftOwDetails getOutwardDetailBasedOnUtr(String utrNo) throws ApplicationException {
        NeftOwDetails entity = null;
        try {
            Query createNamedQuery = entityManager.createNamedQuery("NeftOwDetails.findByUtrNo");
            createNamedQuery.setParameter("utrNo", utrNo);
            entity = (NeftOwDetails) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            System.out.println("Problem In getOutwardDetailBasedOnUtr() Method " + ex.getMessage());
        }
        return entity;
    }

    public List<CbsAutoNeftDetails> getAutoNeftDetails() throws ApplicationException {
        List<CbsAutoNeftDetails> resultList = null;
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsAutoNeftDetails.findAll");
            resultList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            System.out.println("Problem In getAutoNeftDetails() Method " + ex.getMessage());
        }
        return resultList;
    }

    public List<NeftOwDetails> getNeftOwDetailsDataBasedOnFileName(String fileName) throws ApplicationException {
        List<NeftOwDetails> resultList = new ArrayList<NeftOwDetails>();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("NeftOwDetails.findByFileName");
            createNamedQuery.setParameter("fileName", fileName);
            resultList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            System.out.println("Problem In getNeftOwDetailsData() Method " + ex.getMessage());
        }
        return resultList;
    }

    public NeftOwDetails getNeftOwDetailsDataBasedOnFileNameAndCustRef(String fileName, String uniqueCustomerRefNo) throws ApplicationException {
        NeftOwDetails obj = new NeftOwDetails();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.ALL_NEFT_OW_DETAILS_DATA_BASED_ON_FILE_NAME_UCUST_REF);
            createNamedQuery.setParameter("fileName", fileName);
            createNamedQuery.setParameter("uniqueCustomerRefNo", uniqueCustomerRefNo);
            obj = (NeftOwDetails) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            System.out.println("Problem In getNeftOwDetailsData() Method " + ex.getMessage());
        }
        return obj;
    }

    public CbsAutoNeftDetails findAutoNeftDetailForOutward(String processType) throws ApplicationException {
        CbsAutoNeftDetails obj = new CbsAutoNeftDetails();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsAutoNeftDetails.findByProcessType");
            createNamedQuery.setParameter("processType", processType);
            obj = (CbsAutoNeftDetails) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            System.out.println("Problem In getAutoNeftDetails() Method " + ex.getMessage());
        }
        return obj;
    }

    public CbsAutoNeftDetails getNeftDetailsByNefBankName(String neftBankName) throws ApplicationException {
        CbsAutoNeftDetails obj = new CbsAutoNeftDetails();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsAutoNeftDetails.findByNeftBankName");
            createNamedQuery.setParameter("neftBankName", neftBankName);
            obj = (CbsAutoNeftDetails) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            System.out.println("Problem In getNeftDetailsByNefBankName() Method " + ex.getMessage());
        }
        return obj;
    }

    public List<CbsAutoNeftDetails> getAutoNeftDetailsByProcess(String process) throws ApplicationException {
        List<CbsAutoNeftDetails> resultList = null;
        try {
            Query createNamedQuery = entityManager.createNamedQuery("CbsAutoNeftDetails.findByProcess");
            createNamedQuery.setParameter("process", process);
            resultList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            System.out.println("Problem In getAutoNeftDetailsByProcess() Method " + ex.getMessage());
        }
        return resultList;
    }

    public List<CbsAutoNeftDetails> getNeftMisMatchBankName(String process) throws ApplicationException {
        List<CbsAutoNeftDetails> resultList = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.NEFT_MISMATCH_BANK_NAME);
            createNamedQuery.setParameter("process", process);
            resultList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            System.out.println("Problem In getAutoNeftDetailsByProcess() Method " + ex.getMessage());
        }
        return resultList;
    }

    public CbsAutoNeftDetails getNeftDetailsByNefBankNameAndProcess(String neftBankName, String process) throws ApplicationException {
        CbsAutoNeftDetails obj = new CbsAutoNeftDetails();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.NEFT_DETAILS_BY_NEFT_BANK_AND_PROCESS);
            createNamedQuery.setParameter("neftBankName", neftBankName);
            createNamedQuery.setParameter("process", process);
            obj = (CbsAutoNeftDetails) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            System.out.println("Problem In getNeftDetailsByNefBankNameAndProcess() Method " + ex.getMessage());
        }
        return obj;
    }

    public List<NeftOwDetails> getNeftOwPendingDetails(String initiatedBankName) throws ApplicationException {
        List<NeftOwDetails> resultList = null;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_PENDING_ENTITY);
            createNamedQuery.setParameter("fail", "U");
            createNamedQuery.setParameter("success", "S");
            createNamedQuery.setParameter("verify", "P");
            createNamedQuery.setParameter("auth", "Y");
            createNamedQuery.setParameter("initiatedBankName", initiatedBankName);
            resultList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            System.out.println("Problem In getNeftOwPendingDetails() Method " + ex.getMessage());
        }
        return resultList;
    }

    public CbsAutoNeftDetails getNeftDetailsByNefBankNameAndProcessAndProcessType(String neftBankName, String process, String processType) throws ApplicationException {
        CbsAutoNeftDetails obj = new CbsAutoNeftDetails();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.NEFT_DETAILS_BY_NEFT_BANK_AND_PROCESS_AND_PROCESS_TYPE);
            createNamedQuery.setParameter("neftBankName", neftBankName);
            createNamedQuery.setParameter("process", process);
            createNamedQuery.setParameter("processType", processType);
            obj = (CbsAutoNeftDetails) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            System.out.println("Problem In getNeftDetailsByNefBankNameAndProcessAndProcessType() Method " + ex.getMessage());
        }
        return obj;
    }
}
