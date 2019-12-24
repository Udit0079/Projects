/**
 * 
 */
package com.cbs.dao;

import com.cbs.dao.exception.DAOException;
import com.cbs.dao.exception.ExceptionTranslator;
import com.cbs.dao.exception.NullEntityException;
import com.cbs.entity.BaseEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;



/**
 * @author Dhirendra Singh
 * 
 */
public class GenericDAO {

    private static final Logger logger = Logger.getLogger(GenericDAO.class);
    private static final String DELETE = "DELETE";
    /**
     * Entity Manager
     */
    protected EntityManager entityManager;

    /**
     * @param entityManager
     */
    public GenericDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * @param entity
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BaseEntity> getAll(BaseEntity entity) {
        String query = "select obj from " + entity.getClass().getName() + " obj";
        List resultList = this.entityManager.createQuery(query).getResultList();
        return resultList;
    }

    /**
     * @param entity
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BaseEntity> getAllDistinct(BaseEntity entity) {
        Collection result = new LinkedHashSet(getAll(entity));
        ArrayList resultList = new ArrayList(result);
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public Boolean isExists(BaseEntity baseEntity, Object id) {
        BaseEntity entity = this.entityManager.find(baseEntity.getClass(), id);
        if (entity != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * @param clazz
     * @param id
     * @return
     * @throws DAOException
     */
    public BaseEntity findById(BaseEntity clazz, Object id) throws DAOException {
        logger.debug("Called findById(Object id) id = " + id);
        try {
            return entityManager.find(clazz.getClass(), id);
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method save(entity)");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method save(entity)");
            throw new DAOException(e);
        }
    }

    /**
     *
     * NOTE : In a JTA entity manager, entityManager.getTransaction() calls are not permitted.
     *
     * @param entity
     * @throws NullEntityException
     * @throws DAOException
     */
    public void save(BaseEntity entity) throws NullEntityException, DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("save(BaseEntity entity=" + entity + ")");
        }

        long beginTime = System.nanoTime();
        if (entity == null) {
            logger.warn("Entity object is null..");
            throw new NullEntityException("Entity object is null.");
        }
        try {
            entityManager.persist(entity);
            if (logger.isDebugEnabled()) {
                logger.debug("Entity saved sucessfully!! entity =" + entity);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method save(entity)");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method save(entity)");
            throw new DAOException(e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for save is " + (System.nanoTime() - beginTime) * 0.000000001 + " seconds");
        }
    }

    /**
     * Method for updating any entity
     *
     * @param entity
     * @return
     * @throws NullEntityException
     * @throws DAOException
     */
    public BaseEntity update(BaseEntity entity) throws NullEntityException, DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("update(BaseEntity entity=" + entity + ")");
        }

        long beginTime = System.nanoTime();
        if (entity == null) {
            logger.warn("Entity object is null..");
            throw new NullEntityException("Entity object is null.");
        }
        try {
            entity = entityManager.merge(entity);
            if (logger.isDebugEnabled()) {
                logger.debug("Entity updated sucessfully!! entity =" + entity);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method update(entity)");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method update(entity)");
            throw new DAOException(e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for update is " + (System.nanoTime() - beginTime) * 0.000000001 + " seconds");
        }
        return entity;
    }

   
    /**
     * Delete entity from database
     *
     * @param entity
     * @return
     * @throws NullEntityException
     * @throws DAOException
     */
    public void delete(BaseEntity entity, Object id) throws NullEntityException, DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("delete(BaseEntity entity=" + entity + ")");
        }
        long beginTime = System.nanoTime();

        if (entity == null) {
            logger.warn("Entity object is null..");
            throw new NullEntityException("Entity object is null.");
        }
        try {
            entity = entityManager.getReference(entity.getClass(), id);
            entityManager.remove(entity);
            if (logger.isDebugEnabled()) {
                logger.debug("Entity updated sucessfully!! entity =" + entity);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method delete(entity)");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method delete(entity)");
            throw new DAOException(e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for delete is " + (System.nanoTime() - beginTime) * 0.000000001 + " seconds");
        }
    }
    
    public void merge(BaseEntity entity) throws NullEntityException, DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("save(BaseEntity entity=" + entity + ")");
        }

        long beginTime = System.nanoTime();
        if (entity == null) {
            logger.warn("Entity object is null..");
            throw new NullEntityException("Entity object is null.");
        }
        try {
            entityManager.merge(entity);
            if (logger.isDebugEnabled()) {
                logger.debug("Entity saved sucessfully!! entity =" + entity);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method save(entity)");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method save(entity)");
            throw new DAOException(e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for save is " + (System.nanoTime() - beginTime) * 0.000000001 + " seconds");
        }
    }

    public void remove(BaseEntity entity) throws NullEntityException, DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("delete(BaseEntity entity=" + entity + ")");
        }
        long beginTime = System.nanoTime();
        if (entity == null) {
            logger.warn("Entity object is null..");
            throw new NullEntityException("Entity object is null.");
        }
        try {
            entityManager.remove(entity);

            if (logger.isDebugEnabled()) {
                logger.debug("Entity updated sucessfully!! entity =" + entity);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method delete(entity)");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method delete(entity)");
            throw new DAOException(e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for delete is " + (System.nanoTime() - beginTime) * 0.000000001 + " seconds");
        }
    }

    
}