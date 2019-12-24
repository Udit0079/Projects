/**
 * 
 */
package com.hrms.common.utils;

import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.exception.ValidationException;

/**
 * @author ahada
 * 
 */
public class Validator {

    /**
     * @param object
     * @throws ValidationException
     */
    public static void isNull(Object object) throws ValidationException {
        if (object == null) {
            throw new ValidationException(new ExceptionCode(ExceptionCode.OBJECT_IS_NULL, " Object is null"));
        }
    }

    /**
     * @param object
     * @param exceptionCode
     * @throws ValidationException
     */
    public static void isNull(Object object, ExceptionCode exceptionCode) throws ValidationException {
        if (object == null) {
            throw new ValidationException(exceptionCode);
        }
    }

    /**
     * @param object
     * @throws ValidationException
     */
    public static void isNotNull(Object object) throws ValidationException {
        if (object != null) {
            throw new ValidationException(new ExceptionCode(ExceptionCode.OBJECT_IS_NOT_NULL,
                    " Object is not null Object = " + object));
        }
    }

    /**
     * @param object
     * @param exceptionCode
     * @throws ValidationException
     */
    public static void isNotNull(Object object, ExceptionCode exceptionCode) throws ValidationException {
        if (object != null) {
            throw new ValidationException(exceptionCode);
        }
    }
}
