package com.hrms.common.exception;

/**
 * @author AjayHada
 * 
 */
public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2597567843527683072L;
	private ExceptionCode exceptionCode;

	/**
	 * 
	 */
	public ApplicationException() {
		setExceptionCode(new ExceptionCode());
	}

	public ApplicationException(Throwable throwable) {
		super(throwable);
		setExceptionCode(new ExceptionCode());
	}

	/**
	 * @param message
	 */
	public ApplicationException(String message) {
		this();
		getExceptionCode().setExceptionMessage(message);
	}

	/**
	 * @param message
	 * @param throwable
	 */
	public ApplicationException(String message, Throwable throwable) {
		this(throwable);
		getExceptionCode().setExceptionMessage(message);
	}

	/**
	 * @param exceptionCode
	 */
	public ApplicationException(ExceptionCode exceptionCode) {
		this.setExceptionCode(exceptionCode);
	}

	/**
	 * @param exceptionCode
	 * @param throwable
	 */
	public ApplicationException(ExceptionCode exceptionCode, Throwable throwable) {
		this(throwable);
		this.setExceptionCode(exceptionCode);
	}

	/**
	 * @param exceptionCode
	 */
	public void setExceptionCode(ExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	/**
	 * @return
	 */
	public ExceptionCode getExceptionCode() {
		return exceptionCode;
	}
}
