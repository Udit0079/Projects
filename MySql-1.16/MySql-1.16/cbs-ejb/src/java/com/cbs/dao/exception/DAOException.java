package com.cbs.dao.exception;

import com.cbs.exception.ApplicationException;



/**
 * @author ahada
 *
 */
public class DAOException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3385506452165139163L;

	/**
	 * @param e
	 */
	public DAOException(Exception e) {
		super(e);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
}
