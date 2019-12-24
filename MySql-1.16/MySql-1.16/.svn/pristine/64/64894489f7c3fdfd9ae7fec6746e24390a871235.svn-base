package com.cbs.dao.exception;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;

import org.hibernate.exception.ConstraintViolationException;

/**
 * @author Dhirendra Singh
 *
 */
public class ExceptionTranslator {

	// EntityExistsException,EntityNotFoundException,LockTimeoutException,NonUniqueResultException,NoResultException
	// OptimisticLockException,PessimisticLockException,QueryTimeoutException,RollbackException,TransactionRequiredException
	public static DAOException translateException(PersistenceException exception) {
		Throwable cause = exception;
		if (exception.getCause() != null) {
			cause = exception.getCause();
		}
		if (cause instanceof EntityExistsException) {
			return new DAOException("EntityExistsException", cause);
		} else if (cause instanceof EntityNotFoundException) {
			return new DAOException("EntityNotFoundException", cause);
		} else if (cause instanceof LockTimeoutException) {
			return new DAOException("LockTimeoutException", cause);
		} else if (cause instanceof NonUniqueResultException) {
			return new DAOException("NonUniqueResultException", cause);
		} else if (cause instanceof NoResultException) {
			return new DAOException("NoResultException", cause);
		} else if (cause instanceof OptimisticLockException) {
			return new DAOException("OptimisticLockException", cause);
		} else if (cause instanceof PessimisticLockException) {
			return new DAOException("PessimisticLockException", cause);
		} else if (cause instanceof QueryTimeoutException) {
			return new DAOException("QueryTimeoutException", cause);
		} else if (cause instanceof RollbackException) {
			return new DAOException("RollbackException", cause);
		} else if (cause instanceof TransactionRequiredException) {
			return new DAOException("TransactionRequiredException", cause);
		} else if (cause instanceof ConstraintViolationException) {
			return new DAOException("ConstraintViolationException", cause);
		}
		return new DAOException("UnKnownException", cause);
	}

}
