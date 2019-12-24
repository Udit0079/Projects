package com.hrms.web.exception;


public class WebException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messageKey;

	public WebException() {

	}

	public WebException(String messageKey) {
		this.messageKey = messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessageKey() {
		return messageKey;
	}
}
