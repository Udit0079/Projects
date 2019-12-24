package com.cbs.exception;

import java.io.Serializable;

public final class ExceptionCode implements Serializable {

    private static final long serialVersionUID = 1327072126348432757L;
    // Default exception codes
    public static final String DEFAULT_EXCEPTION_CODE = "exception.default";
    public static final String DEFAULT_EXCEPTION_MESSAGE = "An exception has occured.";
    public static final String DUPLICATE_EMAIL_EXCEPTION = "ERROR: The email you have provided already exists.";
    public static final String SAVE_CHECKLIST_EXCEPTION = "Error in saving checklist details.";
    public static final String DEFAULT_PROPERTY_KEY = ExceptionCode.DEFAULT_EXCEPTION_CODE;
    public static final String DEFAULT_PROPERTY_VALUE = ExceptionCode.DEFAULT_EXCEPTION_MESSAGE;
    // user defined exception key codes.
    public static final String USER_OBJECT_NULL = "user.object.is.null";
    public static final String OBJECT_IS_NULL = "object.is.null";
    public static final String OBJECT_IS_NOT_NULL = "object.is.not.null";
    public static final String OBJECT_IS_EMPTY = "object.is.empty";
    public static final String OBJECT_IS_NOT_EMPTY = "object.is.not.empty";
    public static final String SYSTEM_EXCEPTION_OCCURED = "system.exception.occured";
    public static final String ERROR_USER_ALREADY_EXIST = "error.user.already.exist";
    public static final String ERROR_INVALID_PASSWORD = "error.invalid.password";
    public static final String ERROR_INVALID_USERID = "error.invalid.userid";
    public static final String ERROR_INACTIVE_USERID = "error.inactive.userid";
    public static final String ERROR_EMAILID_NOT_EXIST = "error.emailid.not.exist";
    public static String DUPLICATE_ENTITY_EXCEPTION_CODE = "duplicate.entity.exception.occured";
    public static String USER_NAME_IS_NULL = "username.is.null";
    public static String EMAIL_ALREADY_EXISTS = "error.email.already.exists";
    public static String NO_RESULT_FOUND = "no.result.found";
    public static String NO_ENTITY_FOUND = "no.entity.found";
    public static String DUPLICATE_ENTITY_EXISTS = "error.duplicate.entity.exists";
    /**
     * The exception code is the key for the property that we want.
     */
    protected String messageKey;
    /**
     * Holds the description for this exception message.
     */
    protected String exceptionMessage;

    protected ExceptionCode() {
        this(ExceptionCode.DEFAULT_PROPERTY_KEY);
    }

    public ExceptionCode(String messageKey) {
        this(messageKey, ExceptionCode.DEFAULT_PROPERTY_VALUE);
    }

    public ExceptionCode(String messageKey, String exceptionMessage) {
        this.setMessageKey(messageKey);
        this.setExceptionMessage(exceptionMessage);
    }

    /**
     * @return the exceptionCode
     */
    public String getMessageKey() {
        return this.messageKey;
    }

    /**
     * @param exceptionCode
     *            the exceptionCode to set
     */
    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    /**
     * @return the exceptionMessage
     */
    public String getExceptionMessage() {
        return this.exceptionMessage;
    }

    /**
     * @param exceptionMessage
     *            the exceptionMessage to set
     */
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
