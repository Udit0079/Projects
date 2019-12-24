package com.cbs.exception;

import java.util.HashMap;
import java.util.Map;

public class ApplicationException extends Exception {

    private String message;
    private static final long serialVersionUID = 2597567843527683072L;
    private ExceptionCode exceptionCode;
    private static final Map<String, String> messageMap = new HashMap<String, String>();

    static {
        messageMap.put("javax.naming.NamingException", "Bean not found");
        messageMap.put("com.cbs.exception.ServiceLocatorException", "Error in instantiating page");
        messageMap.put("java.sql.SQLException", "Exception occurred while performing operations in database");
        messageMap.put("java.lang.NumberFormatException", "Exception occurred while formatting number");
        messageMap.put("java.lang.IllegalArgumentException", "Illegal Argument passed to the method");
        messageMap.put("java.lang.ArithmeticException", "Exception occurred while evaluating an expression");
        messageMap.put("javax.persistence.EntityNotFoundException", "Data is not present in the table");
        messageMap.put("javax.persistence.NonUniqueResultException", "More than one result fetched in the table");
        messageMap.put("javax.persistence.NoResultException", "No result fetched in the table");
        messageMap.put("javax.persistence.QueryTimeoutException", "Query timeout");
        messageMap.put("javax.persistence.PersistenceException", "Exception occurred while performing operations in database");
        messageMap.put("javax.persistence.EntityExistsException", "Data is already present in the table");
        messageMap.put("javax.persistence.RollbackException", "Rollback failure");
        messageMap.put("javax.persistence.TransactionRequiredException", "Attempt to update the database with no active transaction");
        messageMap.put("javax.persistence.OptimisticLockException", "An optimistic locking conflict occurs");
        messageMap.put("javax.persistence.PessimisticLockException", "An pessimistic locking conflict occurs");
    }

    public ApplicationException() {
        setExceptionCode(new ExceptionCode());
    }

    public ApplicationException(Exception e) {
        super(e);
        try {
            String simpleName = e.getClass().getSimpleName();
            if (simpleName.equalsIgnoreCase("ArithmeticException")
                    || simpleName.equalsIgnoreCase("ParseException")
                    || simpleName.equalsIgnoreCase("NumberFormatException")
                    || simpleName.equalsIgnoreCase("StringIndexOutOfBoundsException")
                    || simpleName.equalsIgnoreCase("NullPointerException")) {
                setMessage(e.getMessage());
            } else {
                String exceptionMessage = messageMap.get(e.getClass().getCanonicalName());
                if (exceptionMessage == null) {
                    exceptionMessage = e.getMessage();
                }
                setMessage(exceptionMessage);
            }
        } catch (Exception e1) {
            setMessage("System error occurred-" + e1.getClass().getSimpleName());
        }
    }

    public ApplicationException(Throwable throwable) {
        super(throwable);
        setExceptionCode(new ExceptionCode());
    }

    public ApplicationException(String message, Throwable throwable) {
        this(throwable);
        getExceptionCode().setExceptionMessage(message);
    }

    public ApplicationException(ExceptionCode exceptionCode) {
        this.setExceptionCode(exceptionCode);
    }

    public ApplicationException(ExceptionCode exceptionCode, Throwable throwable) {
        this(throwable);
        this.setExceptionCode(exceptionCode);
    }

    public ApplicationException(String message) {
        setMessage(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    public final void setExceptionCode(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public final ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
