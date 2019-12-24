/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.neftrtgs;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.neftrtgs.NeftRtgsStatus;
import com.cbs.exception.ApplicationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author root
 */
public class NeftRtgsStatusDAO extends GenericDAO {

    public NeftRtgsStatusDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public List<NeftRtgsStatus> getStatusEntityByDateAndStatus(Date frDt, Date toDt, String status, String processType, String neftBank) throws ApplicationException {
        List<NeftRtgsStatus> entityList = new ArrayList<NeftRtgsStatus>();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_STATUS_ENTITY_BY_DATE_AND_STATUS_PROCESS);
            createNamedQuery.setParameter("frDt", frDt);
            createNamedQuery.setParameter("toDt", toDt);
            createNamedQuery.setParameter("status", status);
            createNamedQuery.setParameter("processType", processType);
            createNamedQuery.setParameter("neftBankName", neftBank);
            entityList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return entityList;
    }

    public List<NeftRtgsStatus> getUtrAlreadyProcessed(Date frDt, Date toDt, String status, String processType, String neftBank) throws ApplicationException {
        List<NeftRtgsStatus> entityList = new ArrayList<NeftRtgsStatus>();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_STATUS_ENTITY_BY_DATE_AND_DUPLICATE_PROCESS);
            createNamedQuery.setParameter("frDt", frDt);
            createNamedQuery.setParameter("toDt", toDt);
            createNamedQuery.setParameter("status", status);
            createNamedQuery.setParameter("processType", processType);
            createNamedQuery.setParameter("neftBankName", neftBank);
            entityList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return entityList;
    }

    public long getMaxTxnId() throws ApplicationException {
        long id = 0;
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.FIND_MAX_TXN_ID_NEFT_RTGS_STATUS);
            Object obj = createNamedQuery.getSingleResult();
            if (obj == null) {
                id = 1;
            } else {
                id = Long.parseLong(obj.toString()) + 1;
            }
            return id;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<NeftRtgsStatus> getStatusEntityByDate(Date frDt, Date toDt, String processType, String neftBank) throws ApplicationException {
        List<NeftRtgsStatus> entityList = new ArrayList<NeftRtgsStatus>();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_STATUS_ENTITY_BY_DATE_PROCESS);
            createNamedQuery.setParameter("frDt", frDt);
            createNamedQuery.setParameter("toDt", toDt);
            createNamedQuery.setParameter("processType", processType);
            createNamedQuery.setParameter("neftBankName", neftBank);
            createNamedQuery.setParameter("reason", "THIS UTR ALREADY PROCESSED.");
            entityList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return entityList;
    }

    public List<NeftRtgsStatus> getEntityByCurrentDateAndStatus(Date currentDt, String status,
            String processType, String neftBankName) throws ApplicationException {
        List<NeftRtgsStatus> entityList = new ArrayList<>();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ENTITY_BY_CURRENT_DATE_AND_STATUS);
            createNamedQuery.setParameter("curDt", currentDt);
            createNamedQuery.setParameter("status", status);
            createNamedQuery.setParameter("processType", processType);
            createNamedQuery.setParameter("neftBankName", neftBankName);
            entityList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return entityList;
    }

    public NeftRtgsStatus findByUtrNoAndStatus(String utrNo, String status) throws ApplicationException {
        NeftRtgsStatus entity = new NeftRtgsStatus();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ENTITY_BY_UTR_AND_STATUS);
            createNamedQuery.setParameter("utr", utrNo);
            createNamedQuery.setParameter("status", status);
            entity = (NeftRtgsStatus) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return entity;
    }

    public NeftRtgsStatus findByUtrNoStatusAndReason(String utrNo, String status) throws ApplicationException {
        NeftRtgsStatus entity = new NeftRtgsStatus();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ENTITY_BY_UTR_STATUS_AND_REASON);
            createNamedQuery.setParameter("utr", utrNo);
            createNamedQuery.setParameter("status", status);
            createNamedQuery.setParameter("reason", "THIS UTR ALREADY PROCESSED.");
            entity = (NeftRtgsStatus) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return entity;
    }

    public NeftRtgsStatus findByUtrNo(String utrNo) throws ApplicationException {
        NeftRtgsStatus entity = new NeftRtgsStatus();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("NeftRtgsStatus.findByUtr");
            createNamedQuery.setParameter("utr", utrNo);
            entity = (NeftRtgsStatus) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return entity;
    }

    public void updateNeftRtgsStatus(NeftRtgsStatus entity) throws ApplicationException {
        try {
            update(entity);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<NeftRtgsStatus> getEntityByTxnTypeAndStatus(String txnType, String process, String neftBankName) throws ApplicationException {
        List<NeftRtgsStatus> entityList = new ArrayList<>();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ENTITY_BY_TXN_TYPE_AND_STATUS);
            createNamedQuery.setParameter("txnType", txnType);
            createNamedQuery.setParameter("status", "Unsuccess");
            createNamedQuery.setParameter("reason", "THIS UTR ALREADY PROCESSED.");
            createNamedQuery.setParameter("process", process);
            createNamedQuery.setParameter("neftBankName", neftBankName);
            entityList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return entityList;
    }

    public List<NeftRtgsStatus> getUnSuccessEntity(String process, String neftBankName) throws ApplicationException {
        List<NeftRtgsStatus> entityList = new ArrayList<>();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_UNSUCCESS_ENTITY);
            createNamedQuery.setParameter("status", "Unsuccess");
            createNamedQuery.setParameter("reason", "THIS UTR ALREADY PROCESSED.");
            createNamedQuery.setParameter("process", process);
            createNamedQuery.setParameter("neftBankName", neftBankName);
            entityList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return entityList;
    }
    
    public List<NeftRtgsStatus> getProcessedMismatch(Date frDt, Date toDt, String status, String processType, String neftBank) throws ApplicationException {
        List<NeftRtgsStatus> entityList = new ArrayList<NeftRtgsStatus>();
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_STATUS_ENTITY_BY_DATE_AND_PROCESSED_MISMATCH);
            createNamedQuery.setParameter("frDt", frDt);
            createNamedQuery.setParameter("toDt", toDt);
            createNamedQuery.setParameter("status", "mismatch");
            createNamedQuery.setParameter("processType", processType);
            createNamedQuery.setParameter("neftBankName", neftBank);
            entityList = createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return entityList;
    }
    
    
}
