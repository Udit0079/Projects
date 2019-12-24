/**
 * 
 */
package com.hrms.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import com.hrms.dao.exception.DAOException;
import com.hrms.dao.exception.ExceptionTranslator;
import com.hrms.dao.exception.NullEntityException;
import com.hrms.entity.BaseEntity;
import javax.persistence.Query;

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
    public Boolean isExists(BaseEntity baseEntity, Long id) {
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
    public BaseEntity findById(java.lang.Class<BaseEntity> clazz, Long id) throws DAOException {
        logger.debug("Called findById(Long id) id = " + id);
        try {
            return entityManager.find(clazz, id);
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method save(entity)");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method save(entity)");
            throw new DAOException(e);
        }
    }

    public BaseEntity findById(BaseEntity entity, Object id) throws DAOException {
        logger.debug("Called findById(Object id) id = " + id);
        try {
            return entityManager.find(entity.getClass(), id);
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
     * Method for soft delete. method will also mark the child objects deleted.
     *
     * @param entity
     * @return
     * @throws NullEntityException
     * @throws DAOException
     */
    public BaseEntity softDelete(BaseEntity entity) throws NullEntityException, DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("softDelete(BaseEntity entity=" + entity + ")");
        }

        long beginTime = System.nanoTime();
        if (entity == null) {
            logger.warn("Entity object is null..");
            throw new NullEntityException("Entity object is null.");
        }
        try {
            /**
             * TODO: Load fresh entity if child not exists
             */
            entity.setIsDelete(true);
            entity.setModifiedDate(new Date(System.currentTimeMillis()));
            updateChildRecords(entity, GenericDAO.DELETE);
            entity = entityManager.merge(entity);
            if (logger.isDebugEnabled()) {
                logger.debug("Entity deleted(soft) sucessfully!! entity =" + entity);
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method softDelete(entity)");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method softDelete(entity)");
            throw new DAOException(e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for softDelete is " + (System.nanoTime() - beginTime) * 0.000000001 + " seconds");
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
    public void delete(BaseEntity entity) throws NullEntityException, DAOException {
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

    /**
     * Method Iterates for all childs and setting delete flag true.
     *
     * @param entity
     * @param actionFlag
     */
    @SuppressWarnings("unused")
    private void updateChildRecords(BaseEntity entity, String actionFlag) {
        if (logger.isDebugEnabled()) {
            logger.debug("updateChildRecords(BaseEntity entity=" + entity + ", String actionFlag=" + actionFlag + ")");
        }

        long beginTime = System.nanoTime();
        Set setOfChildRecords = entity.fetchAllChildRecordSets();
        // We have got a set having zero or more sets.
        // An object can have more than one child.
        // each set is the set having zero or more actual child records.
        // We will also need the User who is responsible for performing this
        // operation.
        if (setOfChildRecords != null) {
            for (Iterator iterator = setOfChildRecords.iterator(); iterator.hasNext();) {
                Set setOfChild = (Set) iterator.next();
                // this is one of the child records set.
                if (setOfChild != null) {
                    for (Iterator childRecords = setOfChild.iterator(); childRecords.hasNext();) {
                        BaseEntity childRecord = (BaseEntity) childRecords.next();
                        if (actionFlag.equals(GenericDAO.DELETE)) {
                            childRecord.setIsDelete(true);
                            childRecord.setModifiedBy(entity.getModifiedBy());
                            childRecord.setModifiedDate(entity.getModifiedDate());
                        }
                    }
                }
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for updateChildRecords is " + (System.nanoTime() - beginTime) * 0.000000001 + " seconds");
        }
    }

    public String checkUserNamePassword(String userName, String userPwd, String forms) throws DAOException {
        if (logger.isDebugEnabled()) {
            logger.debug("checkUserNamePassword()");
        }
        try {
            Query q1 = entityManager.createQuery("SELECT a.userName,a.userPwd FROM AdminUser a WHERE a.userName = :userName AND a.userPwd = :userPwd");
            q1.setParameter("userName", userName);
            q1.setParameter("userPwd", userPwd);
            if (q1.getResultList().isEmpty()) {
                return "Incorrect Username or Password";
            } else {
                Query q2 = entityManager.createQuery("SELECT b.adminUserRolePK.tRoleName FROM AdminUserRole b WHERE b.adminUserRolePK.userName = :userName");
                q2.setParameter("userName", userName);
                if (q2.getResultList().isEmpty()) {
                    return "Role Has Not Been Assigned To The User";
                } else {
                    Query q3 = entityManager.createQuery("SELECT c FROM AdminRoleForms c WHERE c.adminRoleFormsPK.roleName = :roleName AND c.adminRoleFormsPK.forms = :forms AND c.authorizeOp = 'Y'");
                    q3.setParameter("roleName", q2.getResultList().get(0));
                    q3.setParameter("forms", forms);
                    if (q3.getResultList().isEmpty()) {
                        return "User Is Not Authorized";
                    } else {
                        return "Authorized";
                    }
                }
            }
        } catch (PersistenceException e) {
            logger.error("Exception occured while executing method delete(entity)");
            throw ExceptionTranslator.translateException(e);
        } catch (Exception e) {
            logger.error("Exception occured while executing method delete(entity)");
            throw new DAOException(e);
        }
    }
}
