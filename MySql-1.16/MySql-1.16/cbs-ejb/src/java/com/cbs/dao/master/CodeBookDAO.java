
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.dao.GenericDAO;
import com.cbs.dao.exception.DAOException;
import com.cbs.entity.ho.investment.Codebook;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

public class CodeBookDAO extends GenericDAO {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CodeBookDAO.class);

    public CodeBookDAO(EntityManager entityManager) {
        super(entityManager);
        logger.debug("CodeBookDAO Initializing...");
    }

    public Codebook getAssetsByDescription(String desc) throws DAOException {
        Codebook entity = new Codebook();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("Codebook.findByDescription");
            List resultList = createNamedQuery.setParameter("description", desc).getResultList();
            if (!resultList.isEmpty()) {
                entity = (Codebook) resultList.get(0);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }

        return entity;
    }

    public List<Codebook> getAssetType() throws DAOException {
        List<Codebook> resultList = new ArrayList<Codebook>();
        try {
            //resultList = entityManager.createNamedQuery("Codebook.getAssetsType").getResultList();
            if (!resultList.isEmpty()) {
                return resultList;
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return resultList;
    }

    public List<Codebook> getAssetOwnership(String assetType) throws DAOException {
        List<Codebook> resultList = new ArrayList<Codebook>();
        try {
//            Query createNamedQuery = entityManager.createNamedQuery("Codebook.findByAssetType");
//            resultList = createNamedQuery.setParameter("assetType", Short.parseShort(assetType)).getResultList();
            if (!resultList.isEmpty()) {
                return resultList;
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return resultList;
    }
}
